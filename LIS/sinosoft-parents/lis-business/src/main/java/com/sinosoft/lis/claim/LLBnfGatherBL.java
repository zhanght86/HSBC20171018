/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.LLBalanceDB;
import com.sinosoft.lis.db.LLBnfDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LJSGetClaimSchema;
import com.sinosoft.lis.schema.LJSGetSchema;
import com.sinosoft.lis.schema.LLBnfGatherSchema;
import com.sinosoft.lis.schema.LLBnfSchema;
import com.sinosoft.lis.vschema.LJSGetClaimSet;
import com.sinosoft.lis.vschema.LJSGetSet;
import com.sinosoft.lis.vschema.LLBalanceSet;
import com.sinosoft.lis.vschema.LLBnfGatherSet;
import com.sinosoft.lis.vschema.LLBnfSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 理赔立案结论逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zl
 * @version 1.0
 */
public class LLBnfGatherBL {
private static Logger logger = Logger.getLogger(LLBnfGatherBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	// 全局变量
	private MMap map = new MMap();

	private String CurrentDate = PubFun.getCurrentDate();

	private String CurrentTime = PubFun.getCurrentTime();

	private GlobalInput mG = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	private LLBnfSet mLLBnfSet = new LLBnfSet();// 受益人明细集合

	private LLBnfGatherSet mLLBnfGatherSet = new LLBnfGatherSet();;// 保存到数据库中的受益人汇总集合

	private LJSGetSet mLJSGetSet = new LJSGetSet();// 应付总表

	private LJSGetClaimSet mLJSGetClaimSet = new LJSGetClaimSet();// 应付明细表

	private String mPrepayFlag = "";// 是否预付，0-非预付，1-预付

	private String mClmNo = "";

	private String mBnfKind = "";

	private double mBalancePay = 0.0;// 本次案件总分配金额(结算金额+合同处理金额+理算金额)

	public LLBnfGatherBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------LLBnfBL begin submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("----------LLBnfBL after getInputData----------");

		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------LLBnfBL after checkInputData----------");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("----------LLBnfBL after dealData----------");

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------LLBnfBL after prepareOutputData----------");

		// PubSubmit tPubSubmit = new PubSubmit();
		// if (!tPubSubmit.submitData(mResult, cOperate)) {
		// CError.buildErr(this, "数据提交失败," + tPubSubmit.mErrors.getLastError());
		// return false;
		// }

		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("---LLBnfBL start getInputData()...");
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); // 按类取值
		mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData", 0);

		mClmNo = (String) mTransferData.getValueByName("RptNo");
		mPrepayFlag = (String) mTransferData.getValueByName("PrepayFlag");
		if (mPrepayFlag != null && "1".equals(mPrepayFlag)) {
			mBnfKind = "B";
		} else {
			mBnfKind = "A";
		}

		return true;
	}

	/**
	 * 校验传入的报案信息是否合法
	 * 
	 * @return：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {

		if (mClmNo == null || mClmNo.equals("")) {
			CError.buildErr(this, "传入的赔案号为空!");
			return false;
		}

		if (mBnfKind == null || mBnfKind.equals("")) {
			CError.buildErr(this, "传入的受益人分配类型为空!");
			return false;
		}

		String strSQL = "";
		ExeSQL tExeSQL = new ExeSQL();

		if (mBnfKind.equals("B")) {
			strSQL = "select (case when abs(sum(pay)) is null then 0 else abs(sum(pay)) end) from LLBalance a where 1=1 "
					+ " and Feeoperationtype='B'" + " and clmno='" + "?clmno?" + "'";

		} else {
			strSQL = "select (case when sum(pay) is null then 0 else sum(pay) end) from LLBalance a where 1=1 "
					+ " and Feeoperationtype!='B'" + " and clmno='" + "?clmno?" + "'";
		}
		logger.debug("--查询赔案:" + mClmNo + ",待分配金额的SQL:" + strSQL);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strSQL);
		sqlbv.put("clmno", mClmNo);
		mBalancePay = Double.parseDouble(tExeSQL.getOneValue(sqlbv));
		logger.debug("--查询赔案:" + mClmNo + ",待分配金额:" + mBalancePay);

		if (mBalancePay < 0) {
			CError.buildErr(this, "赔案:" + mClmNo + ",待分配总金额<0,无法进行受益人分配金额!");
			return false;
		}
		
		//zy 2009-11-05 先删除之前的应付记录，然后在产生新的应付记录
		String dSql = "delete from ljsgetclaim where otherno='"+"?clmno?"+"'";
		String dTSql = "delete from ljsget where otherno='"+"?clmno?"+"'"; 
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(dSql);
		sqlbv1.put("clmno", mClmNo);
		map.put(sqlbv1, "DELETE");
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(dTSql);
		sqlbv2.put("clmno", mClmNo);
		map.put(sqlbv2, "DELETE");

		return true;
	}

	/**
	 * 数据操作类业务处理
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 合并受益人金额
		if (!gatherLLBnf()) {
			return false;
		}

		// 生成财务总表信息(ljsGetclaim)
		if (!dealLJSGetClaim()) {
			return false;
		}

		// 生成财务总表信息(ljsGet)
		if (!dealLJSGet()) {
			return false;
		}

		return true;
	}

	/**
	 * 汇总LLBnf生成LLBnfGather表信息
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean gatherLLBnf() {
		String tSql = "select a.clmno, a.caseno, a.batno, a.bnfkind, a.insuredno, a.bnfno, sum(a.getmoney)"
				+ " from llbnf a where a.clmno = '"
				+ "?clmno?"
				+ "' and a.bnfkind = '"
				+ "?bnfkind?"
				+ "' group by a.clmno, a.caseno, a.batno, a.bnfkind, a.insuredno, a.bnfno";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSql);
		sqlbv3.put("clmno", mClmNo);
		sqlbv3.put("bnfkind", mBnfKind);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv3);
		if (tSSRS == null || tSSRS.MaxRow <= 0) {
			CError.buildErr(this, "未查询到此案件的受益人分配信息，请核实！");
			return false;
		}

		LLBnfSet tLLBnfSet = null;// 受益人明细表
		LLBnfGatherSchema tLLBnfGatherSchema = null;// 受益人分配汇总表
		for (int i = 1; i <= tSSRS.MaxRow; i++) {
			LLBnfDB tLLBnfDB = new LLBnfDB();
			tLLBnfDB.setClmNo(tSSRS.GetText(i, 1));
			tLLBnfDB.setCaseNo(tSSRS.GetText(i, 2));
			tLLBnfDB.setBatNo(tSSRS.GetText(i, 3));
			tLLBnfDB.setBnfKind(tSSRS.GetText(i, 4));
			tLLBnfDB.setInsuredNo(tSSRS.GetText(i, 5));
			tLLBnfDB.setBnfNo(tSSRS.GetText(i, 6));
			tLLBnfSet = tLLBnfDB.query();

			tLLBnfGatherSchema = new LLBnfGatherSchema();
			tLLBnfGatherSchema = this.setLLBnfGather(tLLBnfSet.get(1));
			tLLBnfGatherSchema.setGetMoney(tSSRS.GetText(i, 7));// 汇总的金额
			tLLBnfGatherSchema.setCurrency("01");// add liupeng by 20100910
			
			if (mBalancePay == 0.0) {
				tLLBnfGatherSchema.setBnfLot(100);
			} else {
				tLLBnfGatherSchema.setBnfLot(PubFun.round(
						(tLLBnfGatherSchema.getGetMoney() / mBalancePay) * 100, 2));// 计算比例
			}
			mLLBnfGatherSet.add(tLLBnfGatherSchema);

			for (int j = 1; j <= tLLBnfSet.size(); j++) {// 将tLLBnfGatherSchema中GetNoticeNo更新到tLLBnfSet
				tLLBnfSet.get(j).setOtherNo(tLLBnfGatherSchema.getOtherNo());
				tLLBnfSet.get(j).setOtherNoType("5");
			}
			mLLBnfSet.add(tLLBnfSet);
		}

		map.put(mLLBnfSet, "UPDATE");
		map.put(mLLBnfGatherSet, "DELETE&INSERT");
		return true;
	}

	/**
	 * 根据结算信息和受益人分配明细信息生成ljsgetclaim信息
	 */
	private boolean dealLJSGetClaim() {
		LJSGetClaimSchema tLJSGetClaimSchema;

		// 查询理赔结算表
		String sql_llbalance;
		if (mBnfKind.equals("B")) {
			sql_llbalance = "select * from llbalance where clmno='" + "?clmno?"
					+ "' and FeeOperationType='B'";
		} else {
			sql_llbalance = " select clmno,feeoperationtype,subfeeoperationtype,feefinatype,batno,otherno,othernotype,salechnl,grpcontno,contno,"
					+ " grppolno,polno,dutycode,getdutycode,getdutykind,kindcode,riskcode,riskversion,agentcode,agentgroup,getdate,"
					+ " (case a.feeoperationtype when 'A' then "
					+ " (a.pay + (select (case when sum(b.pay) is null then 0 else sum(b.pay) end) from llbalance b where b.clmno = a.clmno and b.polno = a.polno and b.dutycode = a.dutycode and b.getdutykind = a.getdutykind and b.getdutycode = a.getdutycode and FeeOperationType = 'B')) "
					+ " else a.pay end) pay,"
					+ " payflag,state,dealflag,managecom,agentcom,agenttype,operator,makedate,maketime,modifydate,modifytime,nbpolno,oripay,adjreason,adjremark,remark,customerno,currency,comCode,modifyOperator"
					+ " from llbalance a where clmno='" + "?clmno?" + "' and FeeOperationType!='B'";
		}
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(sql_llbalance);
		sqlbv4.put("clmno", mClmNo);
		logger.debug("查询理赔结算信息:" + sql_llbalance);
		LLBalanceDB tLLBalanceDB = new LLBalanceDB();
		LLBalanceSet tLLBalanceSet = new LLBalanceSet();
		tLLBalanceSet = tLLBalanceDB.executeQuery(sqlbv4);
		if (tLLBalanceSet == null || tLLBalanceSet.size() == 0) {
			CError.buildErr(this, "查询理赔结算信息失败!");
			return false;
		}

		for (int i = 1; i <= tLLBalanceSet.size(); i++) {// 对结算表循环
			LLBnfDB tLLBnfDB = new LLBnfDB();
			tLLBnfDB.setClmNo(mClmNo);
			tLLBnfDB.setPolNo(tLLBalanceSet.get(i).getPolNo());
			tLLBnfDB.setInsuredNo(tLLBalanceSet.get(i).getCustomerNo());
			tLLBnfDB.setFeeOperationType(tLLBalanceSet.get(i).getFeeOperationType());
			LLBnfSet tLLBnfSet = tLLBnfDB.query();

			double tPay = 0;
			double tSumpay = 0;
			if ("B".equals(tLLBalanceSet.get(i).getFeeOperationType())) {// 预付取正值
				tPay = Math.abs(tLLBalanceSet.get(i).getPay());// 结算总金额
			} else {
				tPay = tLLBalanceSet.get(i).getPay();// 结算总金额
			}
			logger.debug("结算总金额:" + tPay);

			for (int j = 1; j <= tLLBnfSet.size(); j++) {// 对受益人循环
				double tBnfLot = tLLBnfSet.get(j).getBnfLot();// 分配比例
				double tSubpay = 0;// 每个受益人金额
				// 为防止小数点误差,对最后一受益人进行搂底处理
				if (j < tLLBnfSet.size()) {
					tSubpay = PubFun.round(tBnfLot * tPay * 0.01, 2);
					tSumpay += tSubpay;
					logger.debug("第" + j + "个人分配金额:" + tSubpay);
				} else {
					tSubpay = tPay - tSumpay;
					logger.debug("最后一笔分配金额:" + tSubpay);
				}

				// 获取GetNoticeNo
				String tGetNoticeNo = "";
				for (int k = 1; k <= mLLBnfSet.size(); k++) {
					if (tLLBnfSet.get(j).getClmNo().equals(mLLBnfSet.get(k).getClmNo())
							&& tLLBnfSet.get(j).getCaseNo().equals(mLLBnfSet.get(k).getCaseNo())
							&& tLLBnfSet.get(j).getBatNo().equals(mLLBnfSet.get(k).getBatNo())
							&& tLLBnfSet.get(j).getBnfKind().equals(mLLBnfSet.get(k).getBnfKind())
							&& tLLBnfSet.get(j).getPolNo().equals(mLLBnfSet.get(k).getPolNo())
							&& tLLBnfSet.get(j).getInsuredNo().equals(
									mLLBnfSet.get(k).getInsuredNo())
							&& tLLBnfSet.get(j).getBnfNo().equals(mLLBnfSet.get(k).getBnfNo())
							&& tLLBnfSet.get(j).getFeeOperationType().equals(
									mLLBnfSet.get(k).getFeeOperationType())) {
						tGetNoticeNo = mLLBnfSet.get(k).getOtherNo();
					}
				}

				tLJSGetClaimSchema = new LJSGetClaimSchema();
				tLJSGetClaimSchema.setGetNoticeNo(tGetNoticeNo);
				tLJSGetClaimSchema.setFeeOperationType(tLLBalanceSet.get(i).getFeeOperationType());// 业务类型
				tLJSGetClaimSchema.setSubFeeOperationType(tLLBalanceSet.get(i)
						.getSubFeeOperationType());// 子业务类型
				tLJSGetClaimSchema.setFeeFinaType(tLLBalanceSet.get(i).getFeeFinaType());// 财务类型
				tLJSGetClaimSchema.setOtherNo(mClmNo);
				tLJSGetClaimSchema.setOtherNoType("5");
				tLJSGetClaimSchema.setPolNo(tLLBalanceSet.get(i).getPolNo());
				tLJSGetClaimSchema.setGrpContNo(tLLBalanceSet.get(i).getGrpContNo());
				tLJSGetClaimSchema.setGrpPolNo(tLLBalanceSet.get(i).getGrpPolNo());
				tLJSGetClaimSchema.setContNo(tLLBalanceSet.get(i).getContNo());
				tLJSGetClaimSchema.setPay(tSubpay);
				tLJSGetClaimSchema.setDutyCode(tLLBalanceSet.get(i).getDutyCode());
				tLJSGetClaimSchema.setGetDutyCode(tLLBalanceSet.get(i).getGetDutyCode());
				tLJSGetClaimSchema.setGetDutyKind(tLLBalanceSet.get(i).getGetDutyKind());
				tLJSGetClaimSchema.setRiskCode(tLLBalanceSet.get(i).getRiskCode());
				tLJSGetClaimSchema.setRiskVersion(tLLBalanceSet.get(i).getRiskVersion());
				tLJSGetClaimSchema.setKindCode(tLLBalanceSet.get(i).getKindCode());
				tLJSGetClaimSchema.setSerialNo(tLLBnfSet.get(j).getBnfNo());// 受益人序号

				tLJSGetClaimSchema.setAgentCode(tLLBalanceSet.get(i).getAgentCode());
				tLJSGetClaimSchema.setAgentGroup(tLLBalanceSet.get(i).getAgentGroup());
				tLJSGetClaimSchema.setAgentCom(tLLBalanceSet.get(i).getAgentCom());
				tLJSGetClaimSchema.setAgentType(tLLBalanceSet.get(i).getAgentType());

				tLJSGetClaimSchema.setGetDate(tLLBalanceSet.get(i).getGetDate());
				tLJSGetClaimSchema.setSaleChnl(tLLBalanceSet.get(i).getSaleChnl());
				tLJSGetClaimSchema.setOperator(mG.Operator);
				tLJSGetClaimSchema.setMakeDate(CurrentDate);
				tLJSGetClaimSchema.setMakeTime(CurrentTime);
				tLJSGetClaimSchema.setModifyDate(CurrentDate);
				tLJSGetClaimSchema.setModifyTime(CurrentTime);
				// 取得承保机构
				String tMCom = getPolManageCom(tLLBalanceSet.get(i).getPolNo());
				if (tMCom.equals("false")) {
					return false;
				}
				tLJSGetClaimSchema.setManageCom(tMCom);

				mLJSGetClaimSet.add(tLJSGetClaimSchema);
			}
		}
		TaxCalculator.calBySchemaSet(mLJSGetClaimSet);
		map.put(mLJSGetClaimSet, "INSERT");

		return true;
	}

	/**
	 * 根据结算信息和受益人分配明细信息生成ljsgetclaim信息
	 */
	private boolean dealLJSGetClaim_1() {
		LJSGetClaimSchema tLJSGetClaimSchema;
		for (int i = 1; i <= mLLBnfSet.size(); i++) {
			// 查询理赔结算表
			LLBalanceDB tLLBalanceDB = new LLBalanceDB();
			LLBalanceSet tLLBalanceSet = new LLBalanceSet();
			if (mBnfKind.equals("B")) // 如果为预付则只处理预付
			{
				tLLBalanceDB.setClmNo(mLLBnfSet.get(i).getClmNo());
				tLLBalanceDB.setPolNo(mLLBnfSet.get(i).getPolNo());
				tLLBalanceDB.setFeeOperationType(mBnfKind);
				tLLBalanceSet = tLLBalanceDB.query();
			} else {// 正常赔付或预付后的二次赔付
				String sql = " select clmno,feeoperationtype,subfeeoperationtype,feefinatype,batno,otherno,othernotype,salechnl,grpcontno,contno,grppolno,"
						+ " polno,dutycode,getdutycode,getdutykind,kindcode,riskcode,riskversion,agentcode,agentgroup,getdate,"
						+ " (pay+(select (case when sum(pay) is null then 0 else sum(pay) end) from llbalance  where clmno = '"
						+ "?clmno?"
						+ "' and polno = '"
						+ "?polno?"
						+ "' and FeeOperationType = 'B')) pay,"
						+ " payflag,state,dealflag,managecom,agentcom,agenttype,operator,makedate,maketime,modifydate,modifytime,nbpolno,oripay,adjreason,adjremark,remark,customerno"
						+ " from llbalance "
						+ " where clmno='"
						+ "?clmno?"
						+ "' and polno='"
						+ "?polno?"
						+ "' and FeeOperationType='" + "?FeeOperationType?" + "'";
				logger.debug("--查询ClmNo:" + mLLBnfSet.get(i).getClmNo() + ",PolNo:"
						+ mLLBnfSet.get(i).getPolNo() + ",分配项目:"
						+ mLLBnfSet.get(i).getFeeOperationType() + "的sql:" + sql);
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				sqlbv5.sql(sql);
				sqlbv5.put("clmno", mLLBnfSet.get(i).getClmNo());
				sqlbv5.put("polno", mLLBnfSet.get(i).getPolNo());
				sqlbv5.put("FeeOperationType", mLLBnfSet.get(i).getFeeOperationType());
				tLLBalanceSet = tLLBalanceDB.executeQuery(sqlbv5);
			}
			if (tLLBalanceSet == null || tLLBalanceSet.size() == 0) {
				CError.buildErr(this, "查询理赔结算信息失败!");
				return false;
			}

			for (int j = 1; j <= tLLBalanceSet.size(); j++) {
				// 计算受益人赔付金额 注：是预付存正值,理算金额存原值
				double tBnfLot = new Double(mLLBnfSet.get(i).getBnfLot()).doubleValue();// 分配比例
				double tPay = Math.abs(new Double(tLLBalanceSet.get(j).getPay()).doubleValue());// 结算金额
				double tSumpay = 0;

				// 为防止小数点误差,对最后一笔进行搂底处理
				// if (j < tLLBalanceSet.size()) {
				tPay = tBnfLot * tPay * 0.01;
				tSumpay += tPay;
				logger.debug("中间分配:" + tPay);
				// } else {
				// tPay = tPay - tSumpay;
				// logger.debug("最后一笔分配:" + tPay);
				// }
				tPay = Double.parseDouble(new DecimalFormat("0.00").format(tPay));

				tLJSGetClaimSchema = new LJSGetClaimSchema();
				tLJSGetClaimSchema.setGetNoticeNo(mLLBnfSet.get(i).getOtherNo());
				tLJSGetClaimSchema.setFeeOperationType(tLLBalanceSet.get(j).getFeeOperationType());// 业务类型
				tLJSGetClaimSchema.setSubFeeOperationType(tLLBalanceSet.get(j)
						.getSubFeeOperationType());// 子业务类型
				tLJSGetClaimSchema.setFeeFinaType(tLLBalanceSet.get(j).getFeeFinaType());// 财务类型
				tLJSGetClaimSchema.setOtherNo(mClmNo);
				tLJSGetClaimSchema.setOtherNoType("5");
				tLJSGetClaimSchema.setPolNo(tLLBalanceSet.get(j).getPolNo());
				tLJSGetClaimSchema.setGrpContNo(tLLBalanceSet.get(j).getGrpContNo());
				tLJSGetClaimSchema.setGrpPolNo(tLLBalanceSet.get(j).getGrpPolNo());
				tLJSGetClaimSchema.setContNo(tLLBalanceSet.get(j).getContNo());
				tLJSGetClaimSchema.setPay(tPay);
				tLJSGetClaimSchema.setDutyCode(tLLBalanceSet.get(j).getDutyCode());
				tLJSGetClaimSchema.setGetDutyCode(tLLBalanceSet.get(j).getGetDutyCode());
				tLJSGetClaimSchema.setGetDutyKind(tLLBalanceSet.get(j).getGetDutyKind());
				tLJSGetClaimSchema.setRiskCode(tLLBalanceSet.get(j).getRiskCode());
				tLJSGetClaimSchema.setRiskVersion(tLLBalanceSet.get(j).getRiskVersion());
				tLJSGetClaimSchema.setKindCode(tLLBalanceSet.get(j).getKindCode());
				tLJSGetClaimSchema.setSerialNo(mLLBnfSet.get(i).getBnfNo());// 受益人序号
				tLJSGetClaimSchema.setAgentCode(tLLBalanceSet.get(j).getAgentCode());
				tLJSGetClaimSchema.setAgentGroup(tLLBalanceSet.get(j).getAgentGroup());
				tLJSGetClaimSchema.setAgentCom(tLLBalanceSet.get(j).getAgentCom());
				tLJSGetClaimSchema.setAgentType(tLLBalanceSet.get(j).getAgentType());
				tLJSGetClaimSchema.setGetDate(tLLBalanceSet.get(j).getGetDate());
				tLJSGetClaimSchema.setSaleChnl(tLLBalanceSet.get(j).getSaleChnl());
				tLJSGetClaimSchema.setOperator(mG.Operator);
				tLJSGetClaimSchema.setMakeDate(CurrentDate);
				tLJSGetClaimSchema.setMakeTime(CurrentTime);
				tLJSGetClaimSchema.setModifyDate(CurrentDate);
				tLJSGetClaimSchema.setModifyTime(CurrentTime);
				tLJSGetClaimSchema.setCurrency("01");//add liupeng by 20100910
				// 取得承保机构
				String tMCom = getPolManageCom(tLLBalanceSet.get(j).getPolNo());
				if (tMCom.equals("false")) {
					return false;
				}
				tLJSGetClaimSchema.setManageCom(tMCom);

				mLJSGetClaimSet.add(tLJSGetClaimSchema);
			}
		}
		TaxCalculator.calBySchemaSet(mLJSGetClaimSet);
		map.put(mLJSGetClaimSet, "INSERT");

		return true;
	}

	/**
	 * 根据受益人汇总表生成ljsget信息
	 */
	private boolean dealLJSGet() {
		LJSGetSchema tLJSGetSchema = null;
		for (int i = 1; i <= mLLBnfGatherSet.size(); i++) {
			tLJSGetSchema = new LJSGetSchema();// 添加应付总表
			tLJSGetSchema.setGetNoticeNo(mLLBnfGatherSet.get(i).getOtherNo());
			tLJSGetSchema.setOtherNo(mClmNo);
			tLJSGetSchema.setOtherNoType("5");
			tLJSGetSchema.setSerialNo(mLLBnfGatherSet.get(i).getBnfNo());// 受益人序号
			tLJSGetSchema.setInsuredNo(mLLBnfGatherSet.get(i).getInsuredNo());// 被保险人序号
			tLJSGetSchema.setPayMode(mLLBnfGatherSet.get(i).getCasePayMode()); // 支付方式

			// tLJSGetSchema.setSumGetMoney(mLLBnfGatherSet.get(i).getGetMoney());
			// 金额从应付明细表取值
			double tax = 0;   //税率 （获取一次）
			double taxAmount = 0;   //税费
			double netAmount = 0;   //净值
			for (int j = 1; j <= mLJSGetClaimSet.size(); j++) {// 按照GetNoticeNo汇总LJSGetClaim中的金额之和
				if (mLLBnfGatherSet.get(i).getOtherNo().equals(
						mLJSGetClaimSet.get(j).getGetNoticeNo())) {
					if(j == i){
						tax = mLJSGetClaimSet.get(1).getTax();
					}
					taxAmount += mLJSGetClaimSet.get(j).getTaxAmount();
					netAmount += mLJSGetClaimSet.get(j).getNetAmount();
					tLJSGetSchema.setSumGetMoney(tLJSGetSchema.getSumGetMoney()
							+ mLJSGetClaimSet.get(j).getPay());
					tLJSGetSchema.setTax(tax);
					tLJSGetSchema.setTaxAmount(taxAmount);
					tLJSGetSchema.setNetAmount(netAmount);
					tLJSGetSchema.setAgentCode(mLJSGetClaimSet.get(j).getAgentCode());
					tLJSGetSchema.setAgentGroup(mLJSGetClaimSet.get(j).getAgentGroup());
					tLJSGetSchema.setAgentCom(mLJSGetClaimSet.get(j).getAgentCom());
					tLJSGetSchema.setAgentType(mLJSGetClaimSet.get(j).getAgentType());
					tLJSGetSchema.setManageCom(getPolManageCom(mLJSGetClaimSet.get(j).getPolNo()));
				}
			}
			tLJSGetSchema.setDrawer(mLLBnfGatherSet.get(i).getPayeeName());
			tLJSGetSchema.setSerialNo(mLLBnfGatherSet.get(i).getBnfNo());// 受益人序号
			tLJSGetSchema.setDrawerID(mLLBnfGatherSet.get(i).getPayeeIDNo());// 领款人证件号码
			tLJSGetSchema.setDrawerType(mLLBnfGatherSet.get(i).getPayeeIDType());// 领款人证件类型
			tLJSGetSchema.setBankCode(mLLBnfGatherSet.get(i).getBankCode());
			tLJSGetSchema.setBankAccNo(mLLBnfGatherSet.get(i).getBankAccNo());
			tLJSGetSchema.setAccName(mLLBnfGatherSet.get(i).getAccName());
			tLJSGetSchema.setOperator(mG.Operator);
			tLJSGetSchema.setMakeDate(CurrentDate);
			tLJSGetSchema.setMakeTime(CurrentTime);
			tLJSGetSchema.setModifyDate(CurrentDate);
			tLJSGetSchema.setModifyTime(CurrentTime);
			tLJSGetSchema.setCurrency("01");//add liupeng by 20100910
			mLJSGetSet.add(tLJSGetSchema);
		}
		
		map.put(mLJSGetSet, "INSERT");
		return true;
	}

	/**
	 * 校验支付方式必须一致,且为4-银行转账时主表保存的受益人帐户信息和已经保存的主表的受益人账户信息是否一致
	 * 
	 * @param pLLBnfSchema:受益人明细记录
	 * @param pLLBnfGatherSchema:受益人汇总记录
	 * @return true,支付方式一致;false,支付方式不一致;
	 */
	private boolean checkLLBnfAccount(LLBnfSchema pLLBnfSchema, LLBnfGatherSchema pLLBnfGatherSchema) {
		if (pLLBnfGatherSchema.getCasePayMode() == null
				|| pLLBnfGatherSchema.getCasePayMode().trim().equals("")
				|| pLLBnfSchema.getCasePayMode() == null
				|| pLLBnfSchema.getCasePayMode().trim().equals("")) {
			CError.buildErr(this, "受益人" + pLLBnfGatherSchema.getName() + "选择的支付方式不能为空!");
			return false;
		}

		if (!pLLBnfGatherSchema.getCasePayMode().trim()
				.equals(pLLBnfSchema.getCasePayMode().trim())) {
			CError.buildErr(this, "受益人" + pLLBnfGatherSchema.getName() + "选择的支付方式必须保持一致!");
			return false;
		}

		// 银行转账需要校验编码,账号,户名三项信息
		if (pLLBnfGatherSchema.getCasePayMode().trim().equals("4")) {
			// 银行编码必须保持一致
			if (pLLBnfSchema.getBankCode() == null || pLLBnfSchema.getBankCode().trim().equals("")
					|| pLLBnfGatherSchema.getBankCode() == null
					|| pLLBnfGatherSchema.getBankCode().trim().equals("")) {
				CError.buildErr(this, "受益人" + pLLBnfGatherSchema.getName()
						+ "选择的支付方式为银行转账时银行编码不能为空!");
				return false;
			} else {
				if (!pLLBnfSchema.getBankCode().trim().equals(
						pLLBnfGatherSchema.getBankCode().trim())) {
					CError.buildErr(this, "受益人" + pLLBnfGatherSchema.getName() + "录入的银行编码与必须保持一致!");
					return false;
				}
			}

			// 银行账号必须保持一致
			if (pLLBnfSchema.getBankAccNo() == null
					|| pLLBnfSchema.getBankAccNo().trim().equals("")
					|| pLLBnfGatherSchema.getBankAccNo() == null
					|| pLLBnfGatherSchema.getBankAccNo().trim().equals("")) {
				CError.buildErr(this, "受益人" + pLLBnfGatherSchema.getName()
						+ "选择的支付方式为银行转账时银行账号不能为空!");
				return false;
			} else {
				if (!pLLBnfSchema.getBankAccNo().trim().equals(
						pLLBnfGatherSchema.getBankAccNo().trim())) {
					CError.buildErr(this, "受益人" + pLLBnfGatherSchema.getName() + "录入的银行账号与必须保持一致!");
					return false;
				}
			}

			// 银行户名必须保持一致
			if (pLLBnfSchema.getAccName() == null || pLLBnfSchema.getAccName().trim().equals("")
					|| pLLBnfGatherSchema.getAccName() == null
					|| pLLBnfGatherSchema.getAccName().trim().equals("")) {
				CError.buildErr(this, "受益人" + pLLBnfGatherSchema.getName()
						+ "选择的支付方式为银行转账时银行户名不能为空!");
				return false;
			} else {
				if (!pLLBnfSchema.getAccName().trim()
						.equals(pLLBnfGatherSchema.getAccName().trim())) {
					CError.buildErr(this, "受益人" + pLLBnfGatherSchema.getName() + "录入的银行户名与必须保持一致!");
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * 校验是否是同一人信息
	 * 
	 * @param pLLBnfSchema:受益人明细记录
	 * @param pLLBnfGatherSchema:受益人汇总记录
	 * @return true,是同一人;false,不是同一人;
	 */
	private boolean checkSameLLBnf(LLBnfSchema pLLBnfSchema, LLBnfGatherSchema pLLBnfGatherSchema) {
		boolean flag = true;// 同一人标志,默认是同一人

		// 比较被保险人客户号是否一致,不一致则不是同一人
		if ((flag == true)
				&& (!pLLBnfSchema.getInsuredNo().equals(pLLBnfGatherSchema.getInsuredNo()))) {
			flag = false;
		}

		// 比较姓名是否一致,不一致则不是同一人
		if ((flag == true) && (!pLLBnfSchema.getName().equals(pLLBnfGatherSchema.getName()))) {
			flag = false;
		}

		// 比较性别是否一致,不一致则不是同一人
		if ((flag == true) && (!pLLBnfSchema.getSex().equals(pLLBnfGatherSchema.getSex()))) {
			flag = false;
		}

		// 比较出生日期是否一致,不一致则不是同一人
		if ((flag == true)
				&& (!pLLBnfSchema.getBirthday().equals(pLLBnfGatherSchema.getBirthday()))) {
			flag = false;
		}

		// 比较证件类型是否一致,不一致则不是同一人
		if ((flag == true) && (!pLLBnfSchema.getIDType().equals(pLLBnfGatherSchema.getIDType()))) {
			flag = false;
		}

		// 比较证件号码是否一致,不一致则不是同一人
		if ((flag == true) && (!pLLBnfSchema.getIDNo().equals(pLLBnfGatherSchema.getIDNo()))) {
			flag = false;
		}

		// 比较与被保险人关系是否一致,不一致则不是同一人
		if ((flag == true)
				&& (!pLLBnfSchema.getRelationToInsured().equals(
						pLLBnfGatherSchema.getRelationToInsured()))) {
			flag = false;
		}

		return flag;
	}

	/**
	 * 汇总受益人信息
	 * 
	 * @param pLLBnfSchema:受益人明细记录
	 * @return LLBnfGatherSchema:受益人汇总记录
	 */
	private LLBnfGatherSchema setLLBnfGather(LLBnfSchema pLLBnfSchema) {
		LLBnfGatherSchema tLLBnfGatherSchema = new LLBnfGatherSchema();

		tLLBnfGatherSchema.setClmNo(pLLBnfSchema.getClmNo());
		tLLBnfGatherSchema.setCaseNo(pLLBnfSchema.getClmNo());
		tLLBnfGatherSchema.setBatNo(pLLBnfSchema.getBatNo());
		tLLBnfGatherSchema.setBnfKind(pLLBnfSchema.getBnfKind());
		tLLBnfGatherSchema.setInsuredNo(pLLBnfSchema.getInsuredNo());
		tLLBnfGatherSchema.setBnfNo(pLLBnfSchema.getBnfNo());
		tLLBnfGatherSchema.setCustomerNo(pLLBnfSchema.getCustomerNo());
		tLLBnfGatherSchema.setName(pLLBnfSchema.getName());
		tLLBnfGatherSchema.setPayeeNo(pLLBnfSchema.getPayeeNo());
		tLLBnfGatherSchema.setPayeeName(pLLBnfSchema.getPayeeName());
		tLLBnfGatherSchema.setBnfType(pLLBnfSchema.getBnfType());
		tLLBnfGatherSchema.setBnfGrade(pLLBnfSchema.getBnfGrade());
		tLLBnfGatherSchema.setRelationToInsured(pLLBnfSchema.getRelationToInsured());
		tLLBnfGatherSchema.setSex(pLLBnfSchema.getSex());
		tLLBnfGatherSchema.setBirthday(pLLBnfSchema.getBirthday());
		tLLBnfGatherSchema.setIDType(pLLBnfSchema.getIDType());
		tLLBnfGatherSchema.setIDNo(pLLBnfSchema.getIDNo());
		tLLBnfGatherSchema.setRelationToPayee(pLLBnfSchema.getRelationToPayee());
		tLLBnfGatherSchema.setPayeeSex(pLLBnfSchema.getPayeeSex());
		tLLBnfGatherSchema.setPayeeBirthday(pLLBnfSchema.getPayeeBirthday());
		tLLBnfGatherSchema.setPayeeIDType(pLLBnfSchema.getIDType());
		tLLBnfGatherSchema.setPayeeIDNo(pLLBnfSchema.getPayeeIDNo());
		tLLBnfGatherSchema.setGetMoney(pLLBnfSchema.getGetMoney());
		tLLBnfGatherSchema.setCasePayMode(pLLBnfSchema.getCasePayMode());
		tLLBnfGatherSchema.setBankCode(pLLBnfSchema.getBankCode());
		tLLBnfGatherSchema.setBankAccNo(pLLBnfSchema.getBankAccNo());
		tLLBnfGatherSchema.setAccName(pLLBnfSchema.getAccName());
		tLLBnfGatherSchema.setCasePayFlag("0");// 保险金支付标志
		tLLBnfGatherSchema.setOBankCode(tLLBnfGatherSchema.getBnfNo());// 备份受益人序号
		tLLBnfGatherSchema.setOperator(mG.Operator);
		tLLBnfGatherSchema.setMakeDate(CurrentDate);
		tLLBnfGatherSchema.setMakeTime(CurrentTime);
		tLLBnfGatherSchema.setModifyDate(CurrentDate);
		tLLBnfGatherSchema.setModifyTime(CurrentTime);

		// 生成给付通知书号码
		String tGetNoticeNo = PubFun1.CreateMaxNo("GetNoticeNo", PubFun.getNoLimit(mG.ManageCom));
		tLLBnfGatherSchema.setOtherNo(tGetNoticeNo);
		tLLBnfGatherSchema.setOtherNoType("5");

		return tLLBnfGatherSchema;

	}

	/**
	 * 准备保单管理机构
	 * 
	 * @return boolean
	 */
	private String getPolManageCom(String PolNo) {
		// 查询管理机构(来自承保)
		String tSql = " select managecom from lcpol where " + " PolNo = '" + "?PolNo?" + "'";
		SQLwithBindVariables sqlbv6 =new SQLwithBindVariables();
		sqlbv6.sql(tSql);
		sqlbv6.put("PolNo", PolNo);
		ExeSQL tExeSQL = new ExeSQL();
		String tManageCom = tExeSQL.getOneValue(sqlbv6);
		if (tManageCom == null) {
			CError.buildErr(this, "查询保单管理机构失败!");
			return "false";
		}
		return tManageCom;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mResult.clear();
			mResult.add(map);
		} catch (Exception ex) {
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错。");
			return false;
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "002";
		tGI.ManageCom = "8611";

		TransferData mTransferData = new TransferData();
		mTransferData.setNameAndValue("RptNo", "86000020090510000019");
		mTransferData.setNameAndValue("BudgetFlag", "0"); // 是否预付，传'0'进去是要工作流流转到审批阶段;

		VData tVData = new VData();
		tVData.add(tGI);
		tVData.add(mTransferData);

		LLBnfGatherBL tLLBnfBL_1 = new LLBnfGatherBL();
		if (!tLLBnfBL_1.submitData(tVData, "")) {
			logger.debug("失败----------------------------");
		} else {
			logger.debug("成功----------------------------");
		}
	}
}
