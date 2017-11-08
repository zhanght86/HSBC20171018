package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.LLBalanceDB;
import com.sinosoft.lis.db.LLBnfGatherDB;
import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LJSGetClaimSchema;
import com.sinosoft.lis.schema.LJSGetSchema;
import com.sinosoft.lis.schema.LLBnfGatherSchema;
import com.sinosoft.lis.schema.LLBnfSchema;
import com.sinosoft.lis.vschema.LJSGetClaimSet;
import com.sinosoft.lis.vschema.LJSGetSet;
import com.sinosoft.lis.vschema.LLBalanceSet;
import com.sinosoft.lis.vschema.LLBnfGatherSet;
import com.sinosoft.lis.vschema.LLBnfSet;
import com.sinosoft.lis.vschema.LLRegisterSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class LLGrpBnfBL {
private static Logger logger = Logger.getLogger(LLGrpBnfBL.class);
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
	private MMap tMap = new MMap();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private GlobalInput mG = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	private LLBnfSet mLLBnfSet = new LLBnfSet();// 受益人明细集合
	private LLBnfGatherSet tLLBnfGatherSet = null;// 受益人汇总集合

	private LJSGetSet mLJSGetSet = null;
	private LJSGetClaimSet mLJSGetClaimSet = null;
	private LLBnfGatherSet mLLBnfGatherSet = null;// 保存到数据库中的受益人汇总集合

	private String mPolNo = "";
	private String mClmNo = "";
	private String mBnfKind = "";
	private String mCustomerNo = "";
	private String mFeeOperationType = "";
	private ExeSQL tExeSQL = null;
	private SSRS tSSRS = null;
	private double mBalancePay = 0.0;// 本次案件总分配金额(结算金额+合同处理金额+理算金额)

	public LLGrpBnfBL() {
	}

	public static void main(String[] args) {
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
		if (!dealData(cOperate)) {
			return false;
		}
		logger.debug("----------LLBnfBL after dealData----------");

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------LLBnfBL after prepareOutputData----------");

		//保持一个事务，统一提交
//		PubSubmit tPubSubmit = new PubSubmit();
//		if (!tPubSubmit.submitData(mInputData, cOperate)) {
//			// @@错误处理
//			CError
//					.buildErr(this, "数据提交失败,"
//							+ tPubSubmit.mErrors.getLastError());
//			return false;
//		}
		mInputData = null;
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("---LLBnfBL start getInputData()...");
		// 获取页面报案信息
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); // 按类取值
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mLLBnfSet = (LLBnfSet) mInputData.getObjectByObjectName("LLBnfSet", 0);
		mClmNo = (String) mTransferData.getValueByName("ClmNo");
		mPolNo = (String) mTransferData.getValueByName("PolNo");
		mBnfKind = (String) mTransferData.getValueByName("BnfKind");
		mCustomerNo = (String) mTransferData.getValueByName("CustomerNo");
		mFeeOperationType = (String) mTransferData
				.getValueByName("FeeOperationType");

//		tMap = (MMap) mInputData.getObjectByObjectName("MMap", 0);

//		map.add(tMap);

		return true;
	}

	/**
	 * 校验传入的报案信息是否合法
	 * 
	 * @return：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {

		if (mClmNo == null || mClmNo.equals("")) {

			// @@错误处理
			CError.buildErr(this, "传入的赔案号为空!");
			return false;
		}

		if (mCustomerNo == null || mCustomerNo.equals("")) {

			// @@错误处理
			CError.buildErr(this, "传入的客户号为空!");
			return false;
		}

		if (mBnfKind == null || mBnfKind.equals("")) {

			// @@错误处理
			CError.buildErr(this, "传入的受益人分配类型为空!");
			return false;
		}

		String strSQL = "";
		ExeSQL tExeSQL = new ExeSQL();

		if (mBnfKind.equals("B")) {
			strSQL = "select nvl(abs(sum(pay)),0) from LLBalance a where 1=1 "
					+ " and Feeoperationtype='B'" + " and clmno='" + mClmNo
					+ "'";

		} else {
			strSQL = "select nvl(sum(pay),0) from LLBalance a where 1=1 "
					+ " and Feeoperationtype!='B'" + " and clmno='" + mClmNo
					+ "'";
		}

		logger.debug("--查询赔案:" + mClmNo + ",待分配金额的SQL:" + strSQL);

		mBalancePay = Double.parseDouble(tExeSQL.getOneValue(strSQL));

		logger.debug("--查询赔案:" + mClmNo + ",待分配金额:" + mBalancePay);

		if (mBalancePay < 0) {
			// @@错误处理
			CError.buildErr(this, "赔案:" + mClmNo + ",待分配总金额<0,无法进行受益人分配金额!");
			return false;
		}

		tExeSQL = null;
		strSQL = null;

		return true;
	}

	/**
	 * 数据操作类业务处理
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(String cOperate) {

		// 更新立案结论
		if (cOperate.equals("INSERT")) {

			// 增加对转年金的处理 周磊 2006-3-3 18:08
			if (!dealPesionData()) {
				return false;
			}

			// 合并受益人金额
			if (!gatherLLBnf()) {
				return false;
			}

			// 生成财务总表信息(ljsGet)
			if (!dealFinancial()) {
				return false;
			}

			//------------------------------------------------------------------
			// END
		}
		return true;
	}

	/**
	 * 根据受益人汇总表生成ljsget信息 根据结算信息生成ljsgetclaim信息
	 */
	private boolean dealFinancial() {

		// 生成总表信息
		LJSGetSchema tLJSGetSchema = null;
		mLJSGetSet = new LJSGetSet();

		// 根据总表生成ljsget
		for (int i = 1; i <= mLLBnfGatherSet.size(); i++) {
			// 最后统一更新收益比例
			if (mBalancePay == 0.0) {
				mLLBnfGatherSet.get(i).setBnfLot(100);
			} else {
				mLLBnfGatherSet
						.get(i)
						.setBnfLot(
								PubFun.round((mLLBnfGatherSet.get(i)
										.getGetMoney() / mBalancePay) * 100, 2));
			}

			mLLBnfGatherSet.get(i).setOperator(mG.Operator);
			mLLBnfGatherSet.get(i).setMakeDate(CurrentDate);
			mLLBnfGatherSet.get(i).setMakeTime(CurrentTime);
			mLLBnfGatherSet.get(i).setModifyDate(CurrentDate);
			mLLBnfGatherSet.get(i).setModifyTime(CurrentTime);

			// 取得承保机构
			String tManageCom = getPolManageCom(mPolNo);
			if (tManageCom.equals("false")) {
				return false;
			}

			tLJSGetSchema = new LJSGetSchema();
			// 添加应付总表
			tLJSGetSchema.setGetNoticeNo(mLLBnfGatherSet.get(i).getOtherNo());
			tLJSGetSchema.setOtherNo(mClmNo);
			tLJSGetSchema.setOtherNoType("5");
			tLJSGetSchema.setSerialNo(mLLBnfGatherSet.get(i).getBnfNo());// 受益人序号
			tLJSGetSchema.setInsuredNo(mLLBnfGatherSet.get(i).getInsuredNo());// 被保险人序号
			tLJSGetSchema.setPayMode(mLLBnfGatherSet.get(i).getCasePayMode()); // 支付方式
			tLJSGetSchema.setManageCom(tManageCom);
			tLJSGetSchema.setSumGetMoney(mLLBnfGatherSet.get(i).getGetMoney());
			tLJSGetSchema.setDrawer(mLLBnfGatherSet.get(i).getPayeeName());
			tLJSGetSchema.setSerialNo(mLLBnfGatherSet.get(i).getBnfNo());// 受益人序号
			tLJSGetSchema.setDrawerID(mLLBnfGatherSet.get(i).getPayeeIDNo());// 领款人证件号码
			tLJSGetSchema
					.setDrawerType(mLLBnfGatherSet.get(i).getPayeeIDType());// 领款人证件类型
			tLJSGetSchema.setBankCode(mLLBnfGatherSet.get(i).getBankCode());
			tLJSGetSchema.setBankAccNo(mLLBnfGatherSet.get(i).getBankAccNo());
			tLJSGetSchema.setAccName(mLLBnfGatherSet.get(i).getAccName());
			tLJSGetSchema.setOperator(mG.Operator);
			tLJSGetSchema.setMakeDate(CurrentDate);
			tLJSGetSchema.setMakeTime(CurrentTime);
			tLJSGetSchema.setModifyDate(CurrentDate);
			tLJSGetSchema.setModifyTime(CurrentTime);
			mLJSGetSet.add(tLJSGetSchema);

			tLJSGetSchema = null;

		}

		LJSGetClaimSchema tLJSGetClaimSchema = null;
		LJSGetClaimSet tLJSGetClaimSet = null;

		mLJSGetClaimSet = new LJSGetClaimSet();

		// 根据结算信息生成ljsgetclaim信息
		for (int i = 1; i <= mLLBnfSet.size(); i++) {

			// 根据受益人序号找到主表记录,取得给付通知书号
			for (int k = 1; k <= mLLBnfGatherSet.size(); k++) {
				if (mLLBnfGatherSet.get(k).getBnfNo().equals(
						mLLBnfSet.get(i).getBnfNo())) {
					mLLBnfSet.get(i).setOtherNo(
							mLLBnfGatherSet.get(k).getOtherNo());
					mLLBnfSet.get(i).setOtherNoType("5");
					break;
				}
			}

			tLJSGetClaimSchema = new LJSGetClaimSchema();
			tLJSGetClaimSet = new LJSGetClaimSet();

			// 添加理赔结算表
			LLBalanceDB tLLBalanceDB = new LLBalanceDB();
			LLBalanceSet tLLBalanceSet = new LLBalanceSet();

			if (mBnfKind.equals("B")) // 如果为预付则只处理预付
			{
				tLLBalanceDB.setClmNo(mLLBnfSet.get(i).getClmNo());
				tLLBalanceDB.setPolNo(mLLBnfSet.get(i).getPolNo());
				tLLBalanceDB.setFeeOperationType(mBnfKind);
				//zy 2009-08-21 修改到受益人级别
				tLLBalanceDB.setCustomerNo(mLLBnfSet.get(i).getInsuredNo());
				tLLBalanceSet = tLLBalanceDB.query();
			} else {
				// 正常赔付或预付后的二次赔付
				String sql = " select clmno,feeoperationtype,subfeeoperationtype,feefinatype,batno,otherno,othernotype,salechnl,grpcontno,contno,grppolno,"
						+ " polno,dutycode,getdutycode,getdutykind,kindcode,riskcode,riskversion,agentcode,agentgroup,getdate,"
						+ " (pay+(select nvl(sum(pay),0) from llbalance  where clmno = '"
						+ mLLBnfSet.get(i).getClmNo()
						+ "' and polno = '"
						+ mLLBnfSet.get(i).getPolNo()
						+ "' and FeeOperationType = 'B')) pay,"
						+ " payflag,state,dealflag,managecom,agentcom,agenttype,operator,makedate,maketime,modifydate,modifytime,nbpolno,oripay,adjreason,adjremark,remark,CustomerNo"
						+ " from llbalance "
						+ " where clmno='"
						+ mLLBnfSet.get(i).getClmNo()
						+ "' and polno='"
						+ mLLBnfSet.get(i).getPolNo()
						+ "'"
						+ " and FeeOperationType='"
						+ mLLBnfSet.get(i).getFeeOperationType() + "' and customerno='"+mLLBnfSet.get(i).getInsuredNo()+"'";//zy 2009-08-21 修改到受益人级别

				logger.debug("--查询案件:" + mLLBnfSet.get(i).getClmNo()
						+ "险种:" + mLLBnfSet.get(i).getPolNo() + ",分配项目:"
						+ mLLBnfSet.get(i).getFeeOperationType() + "的待分配金额sql:"
						+ sql);
				tLLBalanceSet = tLLBalanceDB.executeQuery(sql);
			}

			if (tLLBalanceSet == null || tLLBalanceSet.size() == 0) {
				// @@错误处理
				CError.buildErr(this, "查询理赔结算信息失败!");
				return false;

			} else {
				for (int j = 1; j <= tLLBalanceSet.size(); j++) {
					/**
					 * -----------------------------------------------------
					 * 计算受益人赔付金额 注：是预付存正值,理算金额存原值
					 * ----------------------------------------------------
					 */
					Double tDouble1 = new Double(tLLBalanceSet.get(j).getPay());
					double tPay = tDouble1.doubleValue();
					Double tDouble2 = new Double(mLLBnfSet.get(i).getBnfLot());
					double tBnfLot = tDouble2.doubleValue();

					// 预付时为负，要取正值
					if (mBnfKind.equals("B")) {
						tPay = Math.abs(tPay);
					}
					logger.debug("保单分配总额:" + tPay);

					// 为防止小数点误差,对最后一笔进行搂底处理
					if (i == mLLBnfSet.size()) {
						double tSumpay = 0;
						for (int m = 1; m <= mLJSGetClaimSet.size(); m++) {
							if (mLJSGetClaimSet.get(m).getFeeFinaType().equals(
									tLLBalanceSet.get(j).getFeeFinaType())
									&& mLJSGetClaimSet
											.get(m)
											.getSubFeeOperationType()
											.equals(
													tLLBalanceSet
															.get(j)
															.getSubFeeOperationType())
									&& mLJSGetClaimSet.get(m).getFeeFinaType()
											.equals(
													tLLBalanceSet.get(j)
															.getFeeFinaType())
									&& mLJSGetClaimSet.get(m).getPolNo()
											.equals(
													tLLBalanceSet.get(j)
															.getPolNo())
									&& 	mLJSGetClaimSet.get(m).getGetNoticeNo().equals(tLLBalanceSet.get(j).getOtherNo())					) {
								//zy 2009-08-21 修改到受益人级别
								tSumpay = tSumpay
										+ mLJSGetClaimSet.get(m).getPay();
							}
						}
		
						if(tPay!=0)
						{
							tPay = tPay - tSumpay;
						}
						else
						{
							tPay = 0;
						}
						
						
						logger.debug("已分配:" + tSumpay);
						logger.debug("最后一笔分配:" + tPay);
					} else {
						tPay = tBnfLot * tPay * 0.01;
						logger.debug("中间分配:" + tPay);
					}

					// 对每个给付通知书号码进行搂底处理---------------------------2006-2-9
					// 14:21 周磊
					// if (j == tLLBalanceSet.size()) {
					// double tSumpay = 0;
					// for (int m = 1; m <= tLJSGetClaimSet.size(); m++) {
					// if (tLJSGetClaimSet.get(m).getGetNoticeNo()
					// .equals(mLLBnfSet.get(i).getOtherNo())) {
					// tSumpay = tSumpay
					// + tLJSGetClaimSet.get(m).getPay();
					// }
					// }
					// tPay = mLLBnfSet.get(i).getGetMoney() - tSumpay;
					// logger.debug("给付通知书号码为["
					// + mLLBnfSet.get(i).getOtherNo()
					// + "]的明细搂底金额:" + tPay);
					// }

					tPay = Double.parseDouble(new DecimalFormat("0.00")
							.format(tPay));

					tLJSGetClaimSchema = new LJSGetClaimSchema();
					tLJSGetClaimSchema.setGetNoticeNo(mLLBnfSet.get(i)
							.getOtherNo());

					tLJSGetClaimSchema.setFeeOperationType(tLLBalanceSet.get(j)
							.getFeeOperationType());// 业务类型
					tLJSGetClaimSchema.setSubFeeOperationType(tLLBalanceSet
							.get(j).getSubFeeOperationType());// 子业务类型
					tLJSGetClaimSchema.setFeeFinaType(tLLBalanceSet.get(j)
							.getFeeFinaType());// 财务类型

					tLJSGetClaimSchema.setOtherNo(mClmNo);
					tLJSGetClaimSchema.setOtherNoType("5");
					tLJSGetClaimSchema
							.setPolNo(tLLBalanceSet.get(j).getPolNo());
					tLJSGetClaimSchema.setGrpContNo(tLLBalanceSet.get(j)
							.getGrpContNo());
					tLJSGetClaimSchema.setGrpPolNo(tLLBalanceSet.get(j)
							.getGrpPolNo());
					tLJSGetClaimSchema.setContNo(tLLBalanceSet.get(j)
							.getContNo());
					tLJSGetClaimSchema.setPay(tPay);
					tLJSGetClaimSchema.setDutyCode(tLLBalanceSet.get(j)
							.getDutyCode());
					tLJSGetClaimSchema.setGetDutyCode(tLLBalanceSet.get(j)
							.getGetDutyCode());
					tLJSGetClaimSchema.setGetDutyKind(tLLBalanceSet.get(j)
							.getGetDutyKind());
					tLJSGetClaimSchema.setRiskCode(tLLBalanceSet.get(j)
							.getRiskCode());
					tLJSGetClaimSchema.setRiskVersion(tLLBalanceSet.get(j)
							.getRiskVersion());
					tLJSGetClaimSchema.setKindCode(tLLBalanceSet.get(j)
							.getKindCode());
					tLJSGetClaimSchema.setSerialNo(mLLBnfSet.get(i).getBnfNo());// 受益人序号

					tLJSGetClaimSchema.setAgentCode(tLLBalanceSet.get(j)
							.getAgentCode());
					tLJSGetClaimSchema.setAgentGroup(tLLBalanceSet.get(j)
							.getAgentCode());
					tLJSGetClaimSchema.setGetDate(tLLBalanceSet.get(j)
							.getGetDate());
					tLJSGetClaimSchema.setSaleChnl(tLLBalanceSet.get(j)
							.getSaleChnl());

					tLJSGetClaimSchema.setOperator(mG.Operator);
					tLJSGetClaimSchema.setMakeDate(CurrentDate);
					tLJSGetClaimSchema.setMakeTime(CurrentTime);
					tLJSGetClaimSchema.setModifyDate(CurrentDate);
					tLJSGetClaimSchema.setModifyTime(CurrentTime);
					// 取得承保机构
					String tMCom = getPolManageCom(tLLBalanceSet.get(j)
							.getPolNo());
					if (tMCom.equals("false")) {
						return false;
					}
					tLJSGetClaimSchema.setManageCom(tMCom);
					// 待添加其他字段信息----------------------------------------
					tLJSGetClaimSet.add(tLJSGetClaimSchema);

					// 补充总表的AgentCode，AgentGroup，GetDate，SaleChnl字段
					for (int k = 1; k <= mLJSGetSet.size(); k++) {
						if (mLJSGetSet.get(k).getGetNoticeNo().equals(
								tLJSGetClaimSchema.getGetNoticeNo())) {
							// 取得子表中同一个实付号的第一条记录进行赋值
							if (mLJSGetSet.get(k).getAgentCode() == null
									|| mLJSGetSet.get(k).getAgentCode().equals(
											"")) {
								mLJSGetSet.get(k).setAgentCode(
										tLJSGetClaimSchema.getAgentCode());
								mLJSGetSet.get(k).setAgentGroup(
										tLJSGetClaimSchema.getAgentGroup());
								mLJSGetSet.get(k).setGetDate(
										tLJSGetClaimSchema.getGetDate());
								mLJSGetSet.get(k).setSaleChnl(
										tLJSGetClaimSchema.getSaleChnl());
							}
						}
					}
				}

				mLLBnfSet.get(i).setOperator(mG.Operator);
				mLLBnfSet.get(i).setMakeDate(CurrentDate);
				mLLBnfSet.get(i).setMakeTime(CurrentTime);
				mLLBnfSet.get(i).setModifyDate(CurrentDate);
				mLLBnfSet.get(i).setModifyTime(CurrentTime);
			}
			mLJSGetClaimSet.add(tLJSGetClaimSet);

			tLJSGetClaimSet = null;
		}

//		map.put(mLLBnfSet, "DELETE&INSERT");
//		map.put(mLLBnfGatherSet, "DELETE&INSERT");
//		map.put(mLJSGetSet, "DELETE&INSERT");
//		map.put(mLJSGetClaimSet, "DELETE&INSERT");

		return true;
	}

	/**
	 * 数据操作类业务处理 1 查询赔案下已经保存的总表(LLBnfGather)数据; 2.1 没有查询到记录 2.1.1
	 * Save传入的子表(llbnf)数据的第1条记录插入总表; 2.1.2
	 * 循环剩余子表记录,根据姓名,性别,出生日期,与被保险人关系,分配类型(A或B),证件类型,证件号进行比较; 2.1.3
	 * 如果是同一人则分别校验支付方式,银行编码,银行账号,银行账户名如果有一项不一样,则return false,并提示错误信息,; 2.1.4
	 * 是同一人则校验合并后金额是否为负，如果是负值则提示错误,不是则更新受益人分配金额和总金额比例,同时更新子表(llbnf)的受益人序号; 2.1.5
	 * 通过不是同一人则增加总表(LLBnfGather)数据; 2.2 查询到记录 2.2.1
	 * 循环子表记录,根据姓名,性别,出生日期,与被保险人关系,分配类型(A或B),证件类型,证件号进行比较; 2.2.2
	 * 如果是同一人则分别校验支付方式,银行编码,银行账号,银行账户名如果有一项不一样,则return false,并提示错误信息,; 2.2.3
	 * 是同一人则校验合并后金额是否为负，如果是负值则提示错误,不是则更新受益人分配金额和总金额比例,同时更新子表(llbnf)的受益人序号; 2.2.4
	 * 通过不是同一人则增加总表(LLBnfGather)数据;
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean gatherLLBnf() {

		LLBnfGatherSchema tLLBnfGatherSchema = null;// 受益人分配汇总表
		LLBnfSchema tLLBnfSchema = null;// 受益人明细表

		LLBnfGatherDB tLLBnfGatherDB = new LLBnfGatherDB();
		tLLBnfGatherDB.setClmNo(this.mClmNo);
		tLLBnfGatherDB.setBnfKind(mBnfKind);
		tLLBnfGatherSet = tLLBnfGatherDB.query();

		mLLBnfGatherSet = new LLBnfGatherSet();

		boolean flag = false;// 同一人标志,默认不是同一人
		// int mBnfNo=1;//受益人序号,默认从1开始

		int time = tLLBnfGatherSet.size();
		int tStation = 1;// 记录匹配到同一人的集合位置

		// 查询不到记录
		if (tLLBnfGatherSet == null || tLLBnfGatherSet.size() <= 0) {

			// 根据子表记录增加受益人主表信息,并同步更新子表的受益人序号
			mLLBnfGatherSet.add(setLLBnfGather(mLLBnfSet.get(1).getSchema()));
			// mLLBnfSet.get(1).setBnfNo(String.valueOf(mBnfNo));
			mLLBnfSet.get(1).setOtherNo(mLLBnfGatherSet.get(1).getOtherNo());
			mLLBnfSet.get(1).setOtherNoType("5");

			if (mLLBnfSet.size() == 1) {
				return true;
			} else {
				// 循环集合进行受益人合并或增加
				for (int i = 2; i <= mLLBnfSet.size(); i++) {
					tLLBnfSchema = mLLBnfSet.get(i);

					// 重新初始化
					flag = false;

					time = mLLBnfGatherSet.size();

					for (int k = 1; k <= time; k++) {
						tLLBnfGatherSchema = mLLBnfGatherSet.get(k);

						// 校验是否是同一人
						if (checkSameLLBnf(tLLBnfSchema, tLLBnfGatherSchema)) {
							if (!checkLLBnfAccount(tLLBnfSchema,
									tLLBnfGatherSchema)) {
								return false;
							} else {
								// 通过同一受益人的所有校验,合并赔付金额,但需要保证合并后的金额不能<0
								if (tLLBnfGatherSchema.getGetMoney() < 0) {
									// @@错误处理
									CError.buildErr(this, "受益人"
											+ tLLBnfSchema.getName()
											+ "合并后的金额为负,不能保存受益人分配记录!");
									return false;
								}

								flag = true;
								tStation = k;
							}

						}

						tLLBnfGatherSchema = null;
					}

					// 如果是最后一条记录,并且不是同一人,则表示比较完毕,增加受益人
					if (flag == false) {
						
						mLLBnfGatherSet.add(setLLBnfGather(tLLBnfSchema));
						
						//当受益人明细表中记录超过主表时,取主表的位置应是主表记录的最大值，否则会导致数据越界
						if(i>mLLBnfGatherSet.size())
						{
							mLLBnfSet.get(i).setOtherNo(mLLBnfGatherSet.get(mLLBnfGatherSet.size()).getOtherNo());
						}
						else
						{
							mLLBnfSet.get(i).setOtherNo(mLLBnfGatherSet.get(i).getOtherNo());
						}

						mLLBnfSet.get(i).setOtherNoType("5");
					} 
					else 
					{
						
						// 将已经汇总好的金额赋值赋值到汇总表中
						double temp=mLLBnfSet.get(i).getGetMoney()+mLLBnfGatherSet.get(tStation).getGetMoney();
						mLLBnfGatherSet.get(tStation).setGetMoney(temp);
		   				//mLLBnfSet.get(i).setGetMoney(temp);
		   				mLLBnfSet.get(i).setBnfNo(mLLBnfGatherSet.get(tStation).getBnfNo());
					}

					tLLBnfSchema = null;
				}

			}

		} else {
			// 直接从第1条记录循环集合进行受益人合并或增加
			for (int i = 1; i <= mLLBnfSet.size(); i++) {
				tLLBnfSchema = mLLBnfSet.get(i);

				// 重新初始化
				flag = false;

				for (int k = 1; k <= time; k++) {
					tLLBnfGatherSchema = tLLBnfGatherSet.get(k);

					// 校验是否是同一人
					if (checkSameLLBnf(tLLBnfSchema, tLLBnfGatherSchema)) {
						if (!checkLLBnfAccount(tLLBnfSchema, tLLBnfGatherSchema)) {
							return false;
						} else {
							// 通过同一受益人的所有校验,合并赔付金额,但需要保证合并后的金额不能<0,传入的金额已经合并后的金额
							if (tLLBnfSchema.getGetMoney() < 0) {
								// @@错误处理
								CError.buildErr(this, "受益人"
										+ tLLBnfSchema.getName()
										+ "合并后的金额为负,不能保存受益人分配记录!");
								return false;
							}

							flag = true;
							tStation = k;
						}

					}

					tLLBnfGatherSchema = null;
				}

				// 如果是最后一条记录,并且不是同一人,则表示比较完毕,增加受益人
				if (flag == false) 
				{
					mLLBnfGatherSet.add(setLLBnfGather(tLLBnfSchema));
					
					//当受益人明细表中记录超过主表时,取主表的位置应是主表记录的最大值，否则会导致数据越界
					if(i>mLLBnfGatherSet.size())
					{
						mLLBnfSet.get(i).setOtherNo(mLLBnfGatherSet.get(mLLBnfGatherSet.size()).getOtherNo());
					}
					else
					{
						mLLBnfSet.get(i).setOtherNo(mLLBnfGatherSet.get(i).getOtherNo());
					}
					mLLBnfSet.get(i).setOtherNoType("5");
				} 
				else
				{
					
					// 将已经汇总好的金额赋值赋值到汇总表中
					double temp=mLLBnfSet.get(i).getGetMoney()+tLLBnfGatherSet.get(tStation).getGetMoney();
					tLLBnfGatherSet.get(tStation).setGetMoney(temp);
					mLLBnfGatherSet.add(tLLBnfGatherSet.get(tStation));
					//LLBnfSet.get(i).setGetMoney(temp);
					mLLBnfSet.get(i).setBnfNo(mLLBnfGatherSet.get(tStation).getBnfNo());
				}

				tLLBnfSchema = null;
			}
		}

		return true;
	}

	/**
	 * 校验支付方式必须一致,且为4-银行转账时主表保存的受益人帐户信息和已经保存的主表的受益人账户信息是否一致
	 * 
	 * @param pLLBnfSchema
	 *            :受益人明细记录
	 * @param pLLBnfGatherSchema
	 *            :受益人汇总记录
	 * @return true,是同一人;false,不是同一人;
	 */
	private boolean checkLLBnfAccount(LLBnfSchema pLLBnfSchema,
			LLBnfGatherSchema pLLBnfGatherSchema) {

		if (pLLBnfGatherSchema.getCasePayMode() == null
				|| pLLBnfGatherSchema.getCasePayMode().trim().equals("")
				|| pLLBnfSchema.getCasePayMode() == null
				|| pLLBnfSchema.getCasePayMode().trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "受益人" + pLLBnfGatherSchema.getName()
					+ "选择的支付方式不能为空!");
			return false;
		}

		if (!pLLBnfGatherSchema.getCasePayMode().trim().equals(
				pLLBnfSchema.getCasePayMode().trim())) {
			// @@错误处理
			CError.buildErr(this, "受益人" + pLLBnfGatherSchema.getName()
					+ "选择的支付方式必须保持一致!");
			return false;
		}

		// 银行转账需要校验编码,账号,户名三项信息
		if (pLLBnfGatherSchema.getCasePayMode().trim().equals("4")) {

			// 银行编码必须保持一致
			if (pLLBnfSchema.getBankCode() == null
					|| pLLBnfSchema.getBankCode().trim().equals("")
					|| pLLBnfGatherSchema.getBankCode() == null
					|| pLLBnfGatherSchema.getBankCode().trim().equals("")) {
				// @@错误处理
				CError.buildErr(this, "受益人" + pLLBnfGatherSchema.getName()
						+ "选择的支付方式为银行转账时银行编码不能为空!");
				return false;
			} else {
				if (!pLLBnfSchema.getBankCode().trim().equals(
						pLLBnfGatherSchema.getBankCode().trim())) {
					// @@错误处理
					CError.buildErr(this, "受益人" + pLLBnfGatherSchema.getName()
							+ "录入的银行编码与必须保持一致!");
					return false;
				}
			}

			// 银行账号必须保持一致
			if (pLLBnfSchema.getBankAccNo() == null
					|| pLLBnfSchema.getBankAccNo().trim().equals("")
					|| pLLBnfGatherSchema.getBankAccNo() == null
					|| pLLBnfGatherSchema.getBankAccNo().trim().equals("")) {
				// @@错误处理
				CError.buildErr(this, "受益人" + pLLBnfGatherSchema.getName()
						+ "选择的支付方式为银行转账时银行账号不能为空!");
				return false;
			} else {
				if (!pLLBnfSchema.getBankAccNo().trim().equals(
						pLLBnfGatherSchema.getBankAccNo().trim())) {
					// @@错误处理
					CError.buildErr(this, "受益人" + pLLBnfGatherSchema.getName()
							+ "录入的银行账号与必须保持一致!");
					return false;
				}
			}

			// 银行户名必须保持一致
			if (pLLBnfSchema.getAccName() == null
					|| pLLBnfSchema.getAccName().trim().equals("")
					|| pLLBnfGatherSchema.getAccName() == null
					|| pLLBnfGatherSchema.getAccName().trim().equals("")) {
				// @@错误处理
				CError.buildErr(this, "受益人" + pLLBnfGatherSchema.getName()
						+ "选择的支付方式为银行转账时银行户名不能为空!");
				return false;
			} else {
				if (!pLLBnfSchema.getAccName().trim().equals(
						pLLBnfGatherSchema.getAccName().trim())) {
					// @@错误处理
					CError.buildErr(this, "受益人" + pLLBnfGatherSchema.getName()
							+ "录入的银行户名与必须保持一致!");
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * 校验是否是同一人信息
	 * 
	 * @param pLLBnfSchema
	 *            :受益人明细记录
	 * @param pLLBnfGatherSchema
	 *            :受益人汇总记录
	 * @return true,是同一人;false,不是同一人;
	 */
	private boolean checkSameLLBnf(LLBnfSchema pLLBnfSchema,
			LLBnfGatherSchema pLLBnfGatherSchema) {

		boolean flag = true;// 同一人标志,默认是同一人

		// 比较被保险人客户号是否一致,不一致则不是同一人
		if ((flag == true)
				&& (!pLLBnfSchema.getInsuredNo().equals(
						pLLBnfGatherSchema.getInsuredNo()))) {
			flag = false;
		}

		// 比较姓名是否一致,不一致则不是同一人
		if ((flag == true)
				&& (!pLLBnfSchema.getName()
						.equals(pLLBnfGatherSchema.getName()))) {
			flag = false;
		}

		// 比较性别是否一致,不一致则不是同一人
		if ((flag == true)
				&& (!pLLBnfSchema.getSex().equals(pLLBnfGatherSchema.getSex()))) {
			flag = false;
		}

		// 比较出生日期是否一致,不一致则不是同一人
		if ((flag == true)
				&& (!pLLBnfSchema.getBirthday().equals(
						pLLBnfGatherSchema.getBirthday()))) {
			flag = false;
		}

		// 比较证件类型是否一致,不一致则不是同一人
		if ((flag == true)
				&& (!pLLBnfSchema.getIDType().equals(
						pLLBnfGatherSchema.getIDType()))) {
			flag = false;
		}

		// 比较证件号码是否一致,不一致则不是同一人
		if ((flag == true)
				&& (!pLLBnfSchema.getIDNo()
						.equals(pLLBnfGatherSchema.getIDNo()))) {
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
	 * @param pLLBnfSchema
	 *            :受益人明细记录
	 * @param pBnfNo
	 *            :受益人序号
	 * @return LLBnfGatherSchema
	 */
	private LLBnfGatherSchema setLLBnfGather(LLBnfSchema pLLBnfSchema) {

		LLBnfGatherSchema tLLBnfGatherSchema = new LLBnfGatherSchema();

		tLLBnfGatherSchema.setClmNo(pLLBnfSchema.getClmNo());
		tLLBnfGatherSchema.setCaseNo(pLLBnfSchema.getClmNo());
		tLLBnfGatherSchema.setBatNo("0");
		tLLBnfGatherSchema.setBnfKind(pLLBnfSchema.getBnfKind());
		tLLBnfGatherSchema.setInsuredNo(pLLBnfSchema.getInsuredNo());
		tLLBnfGatherSchema.setBnfNo(pLLBnfSchema.getBnfNo());
		tLLBnfGatherSchema.setCustomerNo(pLLBnfSchema.getCustomerNo());
		tLLBnfGatherSchema.setName(pLLBnfSchema.getName());
		tLLBnfGatherSchema.setPayeeNo(pLLBnfSchema.getPayeeNo());
		tLLBnfGatherSchema.setPayeeName(pLLBnfSchema.getPayeeName());
		tLLBnfGatherSchema.setBnfType(pLLBnfSchema.getBnfType());
		tLLBnfGatherSchema.setBnfGrade(pLLBnfSchema.getBnfGrade());
		tLLBnfGatherSchema.setRelationToInsured(pLLBnfSchema
				.getRelationToInsured());
		tLLBnfGatherSchema.setSex(pLLBnfSchema.getSex());
		tLLBnfGatherSchema.setBirthday(pLLBnfSchema.getBirthday());
		tLLBnfGatherSchema.setIDType(pLLBnfSchema.getIDType());
		tLLBnfGatherSchema.setIDNo(pLLBnfSchema.getIDNo());
		tLLBnfGatherSchema
				.setRelationToPayee(pLLBnfSchema.getRelationToPayee());
		tLLBnfGatherSchema.setPayeeSex(pLLBnfSchema.getPayeeSex());
		tLLBnfGatherSchema.setPayeeBirthday(pLLBnfSchema.getPayeeBirthday());
		tLLBnfGatherSchema.setPayeeIDType(pLLBnfSchema.getIDType());
		tLLBnfGatherSchema.setPayeeIDNo(pLLBnfSchema.getPayeeIDNo());
		tLLBnfGatherSchema.setGetMoney(pLLBnfSchema.getGetMoney());

		if (mBalancePay == 0.0) {
			tLLBnfGatherSchema.setBnfLot(100);
		} else {
			tLLBnfGatherSchema.setBnfLot(PubFun.round((pLLBnfSchema
					.getGetMoney() / mBalancePay) * 100, 2));
		}

		tLLBnfGatherSchema.setCasePayMode(pLLBnfSchema.getCasePayMode());
		tLLBnfGatherSchema.setCasePayFlag("0");// 保险金支付标志
		tLLBnfGatherSchema.setBankCode(pLLBnfSchema.getBankCode());
		tLLBnfGatherSchema.setBankAccNo(pLLBnfSchema.getBankAccNo());
		tLLBnfGatherSchema.setAccName(pLLBnfSchema.getAccName());
		tLLBnfGatherSchema.setOtherNoType("5");
		tLLBnfGatherSchema.setOBankCode(tLLBnfGatherSchema.getBnfNo());// 备份受益人序号

		// 生成给付通知书号码
		String tGetNoticeNo = PubFun1.CreateMaxNo("GetNoticeNo", PubFun
				.getNoLimit(mG.ManageCom));
		tLLBnfGatherSchema.setOtherNo(tGetNoticeNo);

		tGetNoticeNo = null;

		return tLLBnfGatherSchema;

	}

	/**
	 * 数据操作类业务处理
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealPesionData() {
		LLRegisterDB tLLRegisterDB = new LLRegisterDB();
		tLLRegisterDB.setRgtNo(this.mClmNo);
		LLRegisterSet tLLRegisterSet = tLLRegisterDB.query();
		if (tLLRegisterSet == null || tLLRegisterSet.size() <= 0) {
			// @@错误处理
			CError.buildErr(this, "查询赔案信息失败!");
			return false;
		}

		if (!"2".equals(tLLRegisterSet.get(1).getGetMode())) {
			return true;
		}

		// 删除转年金相关信息
		String tSql1 = " delete from llget where " + " ClmNo = '" + mClmNo
				+ "'" + " and PolNo = '" + mPolNo + "'";
		map.put(tSql1, "DELETE");

		String tSql2 = " delete from llduty where " + " ClmNo = '" + mClmNo
				+ "'" + " and PolNo = '" + mPolNo + "'";
		map.put(tSql2, "DELETE");

		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private String getPolManageCom(String PolNo) {
		// 查询管理机构(来自承保)
		String tSql = " select managecom from lcpol where " + " PolNo = '"
				+ PolNo + "'";
		ExeSQL tExeSQL = new ExeSQL();
		String tManageCom = tExeSQL.getOneValue(tSql);
		if (tManageCom == null) {
			// @@错误处理
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
			mResult.add(mLLBnfSet);
			mResult.add(mLLBnfGatherSet);
			mResult.add(mLJSGetSet);
			mResult.add(mLJSGetClaimSet);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLBnfBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

}
