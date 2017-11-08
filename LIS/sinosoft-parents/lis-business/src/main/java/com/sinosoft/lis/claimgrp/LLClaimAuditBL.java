/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LLAppClaimReasonDB;
import com.sinosoft.lis.db.LLBnfGatherDB;
import com.sinosoft.lis.db.LLCaseDB;
import com.sinosoft.lis.db.LLClaimDB;
import com.sinosoft.lis.db.LLClaimDetailDB;
import com.sinosoft.lis.db.LLClaimUWMainDB;
import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.Arith;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LLBnfGatherSchema;
import com.sinosoft.lis.schema.LLBnfSchema;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.lis.schema.LLClaimUWMDetailSchema;
import com.sinosoft.lis.schema.LLClaimUWMainSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LLAppClaimReasonSet;
import com.sinosoft.lis.vschema.LLBnfSet;
import com.sinosoft.lis.vschema.LLCaseSet;
import com.sinosoft.lis.vschema.LLClaimDetailSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 审核结论逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author: zl
 * @version 1.0
 */
public class LLClaimAuditBL {
private static Logger logger = Logger.getLogger(LLClaimAuditBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	private MMap map = new MMap();

	private String CurrentDate = PubFun.getCurrentDate();

	private String CurrentTime = PubFun.getCurrentTime();

	private TransferData mTransferData = new TransferData();

	private TransferData tTransferData = new TransferData();

	private TransferData mReturnData = new TransferData();

	AccountManage tAccountManage = new AccountManage();

	private LLClaimUWMainSchema mLLClaimUWMainSchema = new LLClaimUWMainSchema();

	LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema();

	private LLClaimSchema mLLClaimSchema = new LLClaimSchema();

	private LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();

	private String mClmNo = "";

	private LLBnfSet mLLBnfSet = new LLBnfSet();

	private LLClaimUWMDetailSchema tLLClaimUWMDetailSchema = null;// 案件核赔履历表

	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();

	private Reflections ref = null;

	private GlobalInput mG = new GlobalInput();

	private ExeSQL mExeSQL = new ExeSQL();

	private SSRS mSSRS = new SSRS();

	private String tMissionID = "";

	private String tSubMissionID = "";
	/** 批量案件标志 */
	private String mPLFlag = "";

	private String tMngCom = "";

	public LLClaimAuditBL() {
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
		logger.debug("----------LLClaimAuditBL.submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("----------after getInputData----------");

		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------after checkInputData----------");
		// 进行业务处理
		if (!dealData(cOperate)) {
			return false;
		}
		logger.debug("----------after dealData----------");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------after prepareOutputData----------");

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "ClaimWorkFlowBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("---LLClaimAuditBL start getInputData()");
		// 获取页面信息
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); // 按类取值
		mLLClaimUWMainSchema = (LLClaimUWMainSchema) mInputData.getObjectByObjectName("LLClaimUWMainSchema",
				0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData", 0);

		tMissionID = (String) mTransferData.getValueByName("MissionID");
		tSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		mPLFlag = (String) mTransferData.getValueByName("SimpleFlag");
		mClmNo = mLLClaimUWMainSchema.getClmNo();

		if (mLLClaimUWMainSchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimAuditBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接受的信息全部为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String tavalireason = null;
		String triskcode = null;
		String tgrpcontno = null;
		String trgtobj = null;

		// tongmeng 2009-01-08 modify
		// 报案类型取RgtState
		String rSql = "select RgtState,riskcode,grpcontno,rgtobj from llreport where rptno='" + mClmNo + "' ";
		tSSRS = tExeSQL.execSQL(rSql);
		if (tSSRS.getMaxRow() > 0) {
			tavalireason = tSSRS.GetText(1, 1);
			triskcode = tSSRS.GetText(1, 2);
			tgrpcontno = tSSRS.GetText(1, 3);
			trgtobj = tSSRS.GetText(1, 4);
		}
		logger.debug("报案类型：" + tavalireason);
		if (tavalireason.equals("02") || tavalireason == "02") {// 02 帐户案件
			String tConclusion = mLLClaimUWMainSchema.getAuditConclusion();
			if (tConclusion.equals("0")) {
				double tSumPay1 = 0.0; // 团体帐户金额
				double tSumPay2 = 0.0; // 个人帐户金额
				String tBalaDate = PubFun.getCurrentDate(); // 当前截息日期（系统日期）
				String tRateType = "Y"; // 原始利率类型（）
				String tIntvType = "D"; // 目标利率类型（日利率）
				int tPerio = 0; // 银行利率期间
				String tType = "F"; // 截息计算类型（单利还是复利）
				String tDepst = "D"; // 贷存款标志（贷款还是存款）
				// -----------公共帐户结息部分
				LCInsureAccClassSchema aLCInsureAccClassSchema = new LCInsureAccClassSchema();
				LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
				LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();

				String tSql = "select nvl(sum(AccPayMoney),0) from llclaimaccount where clmno='" + mClmNo
						+ "' and OtherType='P' ";
				String tAccPayMoney = tExeSQL.getOneValue(tSql);

				String aSql = "select * from lcinsureaccclass where grpcontno='" + tgrpcontno
						+ "' and riskcode='" + triskcode + "' and acctype='001' ";
				tLCInsureAccClassSet = tLCInsureAccClassDB.executeQuery(aSql);
				aLCInsureAccClassSchema = tLCInsureAccClassSet.get(1);

				mReturnData = tAccountManage.getAccClassInterestNew(aLCInsureAccClassSchema, tBalaDate,
						tRateType, tIntvType, tPerio, tType, tDepst);
				if (mReturnData != null) {
					String tempmoney = String.valueOf(mReturnData.getValueByName("aAccClassSumPay"));
					tSumPay1 = Double.parseDouble(tempmoney);
					tSumPay1 = Arith.round(tSumPay1, 2);
					if (tSumPay1 < 0) {
						tSumPay1 = 0.00;
					}
					logger.debug("===团体帐户结息余额==" + tSumPay1);
					logger.debug("===团体帐户赔付金额==" + Double.parseDouble(tAccPayMoney));
				} else {
					tSumPay1 = 0.0;
				}

				if (Double.parseDouble(tAccPayMoney) > tSumPay1) {
					CError.buildErr(this, "团体帐户赔付金额大于团体帐户结息余额，不能结案！");
					return false;
				}

				// -----------个人帐户结息部分
				String accSSql = "select polno,AccPayMoney,declineno from llclaimaccount where clmno='"
						+ mClmNo + "' and othertype='S' ";
				tSSRS = tExeSQL.execSQL(accSSql);
				LCInsureAccClassSchema aaLCInsureAccClassSchema = new LCInsureAccClassSchema();
				LCInsureAccClassDB ttLCInsureAccClassDB = new LCInsureAccClassDB();
				LCInsureAccClassSet ttLCInsureAccClassSet = new LCInsureAccClassSet();
				for (int k = 1; k <= tSSRS.getMaxRow(); k++) {
					aaLCInsureAccClassSchema = new LCInsureAccClassSchema();
					String tPolNo2 = tSSRS.GetText(k, 1);
					String tAccPayMoney2 = tSSRS.GetText(k, 2);
					String tInsuredNo2 = tSSRS.GetText(k, 3);
					/*
					 * 在立案确认jsp中校验
					 * //个人赔付限额的校验。新规定：如果从公共帐户赔付，需校验个人限额。如果个人有保额就以保额做为限额，如果没有就以限额(字段:lcduty-peakline)来限定。
					 * String tAccPayMoney1=null;//个人从公共帐户中赔的钱 String
					 * tAmnt=null; String tPeakline=null; String accPSql="select
					 * sum(AccPayMoney) from llclaimaccount where
					 * clmno='"+mClmNo+"' and declineno='"+tInsuredNo2+"' and
					 * othertype='P' ";
					 * tAccPayMoney1=tExeSQL.getOneValue(accPSql); String
					 * tdutySql="select sum(amnt),sum(peakline) from lcduty
					 * where polno='"+tPolNo2+"' "; tSSRS =
					 * tExeSQL.execSQL(tdutySql); if (tSSRS.getMaxRow()>0) {
					 * tAmnt = tSSRS.GetText(1, 1); tPeakline = tSSRS.GetText(1,
					 * 2);
					 * 
					 * if (!tAmnt.equals("0") && (Double.parseDouble(tAmnt) <
					 * Double.parseDouble(tAccPayMoney1))) { CError tError = new
					 * CError(); tError.moduleName = "LLClaimAuditBL";
					 * tError.functionName = "checkInputData";
					 * tError.errorMessage = "客户号" + tInsuredNo2 +
					 * "的帐户赔付金额大于个人帐户限额，不能结案！";
					 * this.mErrors.addOneError(tError); return false; } else if
					 * (!tPeakline.equals("0") && (Double.parseDouble(tPeakline) <
					 * Double.parseDouble(tAccPayMoney1))) { CError tError = new
					 * CError(); tError.moduleName = "LLClaimAuditBL";
					 * tError.functionName = "checkInputData";
					 * tError.errorMessage = "客户号" + tInsuredNo2 +
					 * "的帐户赔付金额大于个人帐户限额，不能结案！";
					 * this.mErrors.addOneError(tError); return false; } }
					 * //个人赔付限额的校验。
					 */
					ttLCInsureAccClassDB.setPolNo(tPolNo2);
					ttLCInsureAccClassSet = ttLCInsureAccClassDB.query();
					aaLCInsureAccClassSchema = ttLCInsureAccClassSet.get(1);
					mReturnData = tAccountManage.getAccClassInterestNew(aaLCInsureAccClassSchema, tBalaDate,
							tRateType, tIntvType, tPerio, tType, tDepst);
					if (mReturnData != null) {
						String tempmoney = String.valueOf(mReturnData.getValueByName("aAccClassSumPay"));
						tSumPay2 = Double.parseDouble(tempmoney);
						tSumPay2 = Arith.round(tSumPay2, 2);
						if (tSumPay2 < 0) {
							tSumPay2 = 0.00;
						}
						logger.debug("===个人帐户结息余额==" + tSumPay2);
						logger.debug("===个人帐户赔付金额==" + Double.parseDouble(tAccPayMoney2));
					} else {
						tSumPay2 = 0.0;
					}
					if (Double.parseDouble(tAccPayMoney2) > tSumPay2) {
						CError.buildErr(this, "客户号" + tInsuredNo2 + "的个人帐户赔付金额大于个人帐户结息余额，不能结案！");
						return false;
					}
				}
			}
		}

		// 校验是否为简易案件，团体理赔只有为简易案件时审核结论才能为0
		if ("0".equals(mLLClaimUWMainSchema.getAuditConclusion())) {// 0-给付通过
			// 赔案表,取核赔赔付金额RealPay
			LLClaimDB tLLClaimDB = new LLClaimDB();
			tLLClaimDB.setClmNo(mLLClaimUWMainSchema.getClmNo());
			tLLClaimDB.getInfo();
			mLLClaimSchema.setSchema(tLLClaimDB.getSchema());
			if (mLLClaimSchema.getMakeDate() == null) {
				CError.buildErr(this, "查询赔案表出错,赔案表无此立案信息!");
				return false;
			} else {
				double tRealPay = mLLClaimSchema.getRealPay();
				mTransferData.setNameAndValue("adjpay", Double.toString(tRealPay));
			}

			LLClaimSimpleSetBL tLLClaimSimpleSetBL = new LLClaimSimpleSetBL();
			if (!tLLClaimSimpleSetBL.submitData(mInputData, "")) {
				CError.buildErr(this, "判断案件是否为简易案件失败," + tLLClaimSimpleSetBL.mErrors.getLastError());
				return false;
			} else {
				logger.debug("-----start Service getData from LLClaimSimpleSetBL");
				VData tVData = new VData();
				tVData = tLLClaimSimpleSetBL.getResult();

				TransferData tTransferData = new TransferData();
				tTransferData = (TransferData) tVData.getObjectByObjectName("TransferData", 0);

				String mSimpleFlag = (String) tTransferData.getValueByName("SimpleFlag");
				logger.debug("案件:" + mClmNo + "判断简易案件条件结果:" + mSimpleFlag);

				// 更新立案表的案件类型:01-简易案件
				if ("1".equals(mSimpleFlag)) {
					map.put("update LLRegister a set a.rgtstate = '01' where a.rgtno = '" + mClmNo + "'",
							"UPDATE");
				} else {
					CError.buildErr(this, "该案件不是简易案件，审核结论不能为给付或部分给付");
					return false;
				}
			}
		} else {
		}

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(String cOperate) {

		logger.debug("------start dealData-----");
		boolean tReturn = false;

		String tAuditConclusion = mLLClaimUWMainSchema.getAuditConclusion();

		// 更新案件核赔表，同时插入轨迹
		if (cOperate.equals("INSERT")) {
			// 查询立案信息llregister
			LLRegisterDB tLLRegisterDB = new LLRegisterDB();
			LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
			tLLRegisterDB.setRgtNo(mLLClaimUWMainSchema.getClmNo());
			tLLRegisterDB.getInfo();
			tLLRegisterSchema.setSchema(tLLRegisterDB.getSchema());
			if (tLLRegisterSchema.getMngCom() == null) {
				CError.buildErr(this, "未查询到立案信息!");
				return false;
			}
			tMngCom = tLLRegisterSchema.getMngCom();

			// 报案表
			String trptSql = "select operator,nvl(ReturnMode,0) from llreport where rptno='"
					+ mLLClaimUWMainSchema.getClmNo() + "' ";
			mSSRS = mExeSQL.execSQL(trptSql);
			String tRptOperator = null;
			String tReturnMode = null;
			if (mSSRS.getMaxRow() > 0) {
				tRptOperator = mSSRS.GetText(1, 1); // 报案操作员
				tReturnMode = mSSRS.GetText(1, 2); // 延迟立案回退次数
			}

			// 立案表
			String trgtSql = "select operator from llregister where rgtno='"
					+ mLLClaimUWMainSchema.getClmNo() + "' ";
			String tRgtOperator = mExeSQL.getOneValue(trgtSql);// 立案操作员

			// 案件核赔表
			LLClaimUWMainDB tLLClaimUWMainDB = new LLClaimUWMainDB();
			tLLClaimUWMainDB.setClmNo(mLLClaimUWMainSchema.getClmNo());

			// 查询到记录表示不是第一次审核,需要同时记录轨迹
			if (tLLClaimUWMainDB.getInfo()) {
				tLLClaimUWMainSchema.setSchema(tLLClaimUWMainDB.getSchema());

				// 更新审核结论
				tLLClaimUWMainSchema.setAuditConclusion(mLLClaimUWMainSchema.getAuditConclusion());// 审核结论
				tLLClaimUWMainSchema.setAuditIdea(mLLClaimUWMainSchema.getAuditIdea());// 审核意见
				tLLClaimUWMainSchema.setSpecialRemark(mLLClaimUWMainSchema.getSpecialRemark());// 特殊备注
				tLLClaimUWMainSchema.setAuditNoPassReason(mLLClaimUWMainSchema.getAuditNoPassReason());// 审核不通过原因
				tLLClaimUWMainSchema.setAuditNoPassDesc(mLLClaimUWMainSchema.getAuditNoPassDesc());// 审核不通过依据
				tLLClaimUWMainSchema.setAuditPer(mG.Operator);// 审核人
				tLLClaimUWMainSchema.setAuditDate(CurrentDate);// 审核日期

				tLLClaimUWMainSchema.setOperator(mG.Operator);
				tLLClaimUWMainSchema.setMngCom(mG.ManageCom);
				tLLClaimUWMainSchema.setMakeDate(CurrentDate);
				tLLClaimUWMainSchema.setMakeTime(CurrentTime);
				tLLClaimUWMainSchema.setModifyDate(CurrentDate);
				tLLClaimUWMainSchema.setModifyTime(CurrentTime);

				// 直接结案时给审批赋值：0-给付通过，2-客户撤案，3-公司撤案
				if (tAuditConclusion.equals("0") || tAuditConclusion.equals("2")
						|| tAuditConclusion.equals("3")) {
					tLLClaimUWMainSchema.setExamConclusion(mLLClaimUWMainSchema.getAuditConclusion());// 审批结论
					tLLClaimUWMainSchema.setExamIdea(mLLClaimUWMainSchema.getAuditIdea());// 审批意见
					tLLClaimUWMainSchema.setExamNoPassDesc(mLLClaimUWMainSchema.getAuditNoPassReason());// 审批不通过原因
					tLLClaimUWMainSchema.setExamNoPassReason(mLLClaimUWMainSchema.getAuditNoPassDesc());// 审批不通过依据
					tLLClaimUWMainSchema.setExamDate(CurrentDate);
					tLLClaimUWMainSchema.setExamPer(mG.Operator);
				} else {
					tLLClaimUWMainSchema.setExamConclusion("");// 审批结论
					tLLClaimUWMainSchema.setExamIdea("");// 审批意见
					tLLClaimUWMainSchema.setExamNoPassDesc("");// 审批不通过原因
					tLLClaimUWMainSchema.setExamNoPassReason("");// 审批不通过依据
					tLLClaimUWMainSchema.setExamDate("");
					tLLClaimUWMainSchema.setExamPer("");
				}

				// 打包提交数据
				map.put("delete from LLClaimUWMain where clmno='" + mLLClaimUWMainSchema.getClmNo() + "'",
						"DELETE");
				tLLClaimUWMainSchema.setDSClaimFlag("");
				map.put(tLLClaimUWMainSchema, "INSERT");
			} else {
				// 第一次审核
				tLLClaimUWMainSchema.setSchema(mLLClaimUWMainSchema);

				tLLClaimUWMainSchema.setAuditConclusion(mLLClaimUWMainSchema.getAuditConclusion());// 审核结论
				tLLClaimUWMainSchema.setAuditIdea(mLLClaimUWMainSchema.getAuditIdea());// 审核意见
				tLLClaimUWMainSchema.setSpecialRemark(mLLClaimUWMainSchema.getSpecialRemark());// 特殊备注
				tLLClaimUWMainSchema.setAuditNoPassReason(mLLClaimUWMainSchema.getAuditNoPassReason());// 审核不通过原因
				tLLClaimUWMainSchema.setAuditNoPassDesc(mLLClaimUWMainSchema.getAuditNoPassDesc());// 审核不通过依据
				tLLClaimUWMainSchema.setAuditPer(mG.Operator);// 审核人
				tLLClaimUWMainSchema.setAuditDate(CurrentDate);// 审核日期

				tLLClaimUWMainSchema.setOperator(mG.Operator);
				tLLClaimUWMainSchema.setMngCom(mG.ManageCom);
				tLLClaimUWMainSchema.setMakeDate(CurrentDate);
				tLLClaimUWMainSchema.setMakeTime(CurrentTime);
				tLLClaimUWMainSchema.setModifyDate(CurrentDate);
				tLLClaimUWMainSchema.setModifyTime(CurrentTime);

				// 直接结案时给审批赋值：0-给付通过，2-客户撤案，3-公司撤案
				if (tAuditConclusion.equals("0") || tAuditConclusion.equals("2")
						|| tAuditConclusion.equals("3")) {
					tLLClaimUWMainSchema.setExamConclusion(mLLClaimUWMainSchema.getAuditConclusion());// 审批结论
					tLLClaimUWMainSchema.setExamIdea(mLLClaimUWMainSchema.getAuditIdea());// 审批意见
					tLLClaimUWMainSchema.setExamNoPassDesc(mLLClaimUWMainSchema.getAuditNoPassReason());// 审批不通过原因
					tLLClaimUWMainSchema.setExamNoPassReason(mLLClaimUWMainSchema.getAuditNoPassDesc());// 审批不通过依据
					tLLClaimUWMainSchema.setExamDate(CurrentDate);
					tLLClaimUWMainSchema.setExamPer(mG.Operator);
				} else {
					tLLClaimUWMainSchema.setExamConclusion("");// 审批结论
					tLLClaimUWMainSchema.setExamIdea("");// 审批意见
					tLLClaimUWMainSchema.setExamNoPassDesc("");// 审批不通过原因
					tLLClaimUWMainSchema.setExamNoPassReason("");// 审批不通过依据

					tLLClaimUWMainSchema.setExamDate("");
					tLLClaimUWMainSchema.setExamPer("");
				}
				tLLClaimUWMainSchema.setDSClaimFlag("");
				// 打包提交数据
				map.put(tLLClaimUWMainSchema, "INSERT");
			}

			// 查询LLClaimUWMDetail核赔次数
			String strSQL = "";
			strSQL = " select Max(ClmUWNo/1) from LLClaimUWMDetail where " + " ClmNO='"
					+ mLLClaimUWMainSchema.getClmNo() + "'";
			ExeSQL exesql = new ExeSQL();
			String tMaxNo = exesql.getOneValue(strSQL);
			if (tMaxNo.length() == 0) {
				tMaxNo = "1";
			} else {
				int tInt = Integer.parseInt(tMaxNo) + 1;
				tMaxNo = String.valueOf(tInt);
			}

			// 同步轨迹表
			tLLClaimUWMDetailSchema = new LLClaimUWMDetailSchema();
			ref = new Reflections();
			ref.transFields(tLLClaimUWMDetailSchema, tLLClaimUWMainSchema);
			tLLClaimUWMDetailSchema.setClmUWNo(tMaxNo);

			// 打包提交数据
			map.put(tLLClaimUWMDetailSchema, "INSERT");

			tLLClaimUWMDetailSchema = null;
			ref = null;
			tMaxNo = null;
			exesql = null;

			// 赔案表
			LLClaimDB tLLClaimDB = new LLClaimDB();
			tLLClaimDB.setClmNo(mLLClaimUWMainSchema.getClmNo());
			tLLClaimDB.getInfo();
			mLLClaimSchema.setSchema(tLLClaimDB.getSchema());
			if (mLLClaimSchema.getMakeDate() == null) {
				CError.buildErr(this, "查询赔案表出错,赔案表无此立案信息!");
				return false;
			}
			mLLClaimSchema.setGiveType(mLLClaimUWMainSchema.getAuditConclusion());
			mLLClaimSchema.setGiveTypeDesc(mLLClaimUWMainSchema.getAuditIdea());
			mLLClaimSchema.setMngCom(tMngCom);
			mLLClaimSchema.setCasePayType(mLLClaimPubFunBL.getCheckCasePayType(mLLClaimUWMainSchema
					.getClmNo()));
			//批量案件不进行是否需要进行强制调查的校验  04-11
			if(!"1".equals(mPLFlag)){
				//审核结论只有为0-给付通过 1-全部拒付 4-审批管理时才校验是否该进行强制调查
				if("0".equals(tAuditConclusion)||"1".equals(tAuditConclusion)||"4".equals(tAuditConclusion)){
					if (!"0".equals(mLLClaimSchema.getCasePayType())) {
						String tinqSQL = "select count(1) from LLInqApply where clmno='"
								+ mLLClaimUWMainSchema.getClmNo() + "' and InqState='1'";
						ExeSQL tExeSQL = new ExeSQL();
						String tResult = tExeSQL.getOneValue(tinqSQL);
						if ("0".equals(tResult)) {
							CError.buildErr(this, "该案件为强制调查案件，请先进行调查处理！");
							return false;
						}
					}
				}
			}
			else
			{
				//批次案件的案件类型
				mLLClaimSchema.setCasePayType("2");
			}
			
			//99代表案件超过某种理赔类型的最大给付金,需要发起强制调查,但案件类型依然是一般给付件
			if(mLLClaimSchema.getCasePayType().equals("99"))
			{
				mLLClaimSchema.setCasePayType("0");
			}
			
			map.put(mLLClaimSchema, "DELETE&INSERT");
			
			// No.2.2 同步案件类型
			String sql1 = " update LLRegister set casePayType='"+mLLClaimSchema.getCasePayType()+"' where"
						+ " RgtNo = '" + mLLClaimSchema.getClmNo() + "'";
			map.put(sql1, "UPDATE");
	
			String sql2 = " update llclaimpolicy set casePayType='"+mLLClaimSchema.getCasePayType()+"' where"
		    + " clmno = '" + mLLClaimSchema.getClmNo() + "'";
			map.put(sql2, "UPDATE");

			/**
			 * 不等于 4 审批管理 直接结案
			 */
			mInputData.add(mLLClaimUWMainSchema);
			mInputData.add(mLLClaimSchema);

			// tongmeng 2009-01-06 modify
			// tAuditConclusion.equals("4") 进入审批流程
			if (tAuditConclusion != null
			// && !tAuditConclusion.equals("4")
			// &&
			// !tAuditConclusion.equals("5")
			) {

				// LLClaimAuditAfterDealBL tLLClaimAuditAfterDealBL = new
				// LLClaimAuditAfterDealBL();
				// if (!tLLClaimAuditAfterDealBL.submitData(mInputData,
				// "INSERT"))
				// {
				// // @@错误处理
				// this.mErrors.copyAllErrors(tLLClaimAuditAfterDealBL.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "LLClaimAuditAfterInitService";
				// tError.functionName = "submitData";
				// tError.errorMessage = "数据提交失败!";
				// this.mErrors.addOneError(tError);
				// mResult.clear();
				// mInputData = null;
				// return false;
				// }
				// else
				// {
				// VData tVData = new VData();
				// MMap tMap = new MMap();
				// tVData = tLLClaimAuditAfterDealBL.getResult();
				// tMap = (MMap) tVData.getObjectByObjectName("MMap", 0);
				// map.add(tMap);
				// //赔案状态已在LLClaimAuditAfterDealBL中处理
				// }

				/**
				 * -------------------------------------------------- 0 给付或部分给付
				 * 1 全部拒付 --------------------------------------------------
				 */
				if (tAuditConclusion != null
						&& (tAuditConclusion.equals("0") || tAuditConclusion.equals("1"))) {

					/**
					 * 2009-02-10 zhangzheng 界面上已经添加了受益人分配功能,此处不需要再自动运行受益人分配功能
					 */

					// 因为拒付项已经去掉,所以赔付结论只能从detail里取
					// String DetailSql="select distinct givetype from
					// llclaimdetail where
					// clmno='"+mLLClaimUWMainSchema.getClmNo()+"' ";
					// ExeSQL tExeSQL = new ExeSQL();
					// String tgivetype = tExeSQL.getOneValue(DetailSql);
					// if (tgivetype.equals("1"))
					// {
					// tAuditConclusion="1";
					// }
					// if (!tAuditConclusion.equals("1")) {
					// /*加锁，准备挂起，防止中间点2次结案按钮*/
					// String tGetDutyKind = mLLClaimSchema.getGetDutyKind();
					// if (tGetDutyKind == null) {
					// tGetDutyKind = "";
					// }
					// if (tGetDutyKind.equals("1")) {
					// CError tError = new CError();
					// tError.moduleName = "CardBL";
					// tError.functionName = "getInputData";
					// tError.errorMessage = "该赔案正在做复核结案，请稍等!";
					// this.mErrors.addOneError(tError);
					// return false;
					// } else {
					// map.put("update LLClaim set GetDutyKind='1' where
					// clmno='"+mLLClaimSchema.getClmNo()+"' ", "UPDATE");
					// VData tInputData = new VData();
					// tInputData.add(map);
					// PubSubmit tPubSubmit = new PubSubmit();
					// if (!tPubSubmit.submitData(tInputData, mOperate)) {
					// // @@错误处理
					// this.mErrors.copyAllErrors(tPubSubmit.mErrors);
					// CError tError = new CError();
					// tError.moduleName = "LCNewCardChcekBL";
					// tError.functionName = "submitData";
					// tError.errorMessage = "数据提交失败!";
					// this.mErrors.addOneError(tError);
					// return false;
					// }
					// }
					/*
					 * if (!getLLBnf()) { return false; } //准备传输数据 VData VData
					 * tVData = new VData(); tVData.add(mG);
					 * tVData.add(tTransferData); tVData.add(mLLBnfSet); LLBnfBL
					 * tLLBnfBL = new LLBnfBL(); if
					 * (!tLLBnfBL.submitData(tVData, "INSERT")) { // @@错误处理
					 * this.mErrors.copyAllErrors(tLLBnfBL.mErrors); CError
					 * tError = new CError(); tError.moduleName =
					 * "LLClaimConfirmAfterInitService"; tError.functionName =
					 * "submitData"; tError.errorMessage = "数据提交失败!";
					 * this.mErrors.addOneError(tError); mResult.clear();
					 * mInputData = null; return false; } else { VData tVDate =
					 * new VData(); tVDate = tLLBnfBL.getResult();
					 * logger.debug( "-----start Service getData from
					 * BL"); MMap tMap = new MMap(); tMap = (MMap)
					 * tVDate.getObjectByObjectName("MMap", 0); map.add(tMap);
					 * tReturn = true; }
					 */
				}
				// ----------------------------------------------------------------------BEG
				// 功能：处理业务层面数据
				// 处理：1 审批结论保存
				// 2 审批不通过时向案件核赔履历表中写入数据
				// 3 赔案状态更改
				/**
				 * zhangzheng 2008-02-10 直接在保存审核结论处保存审批结论,无需再调用这个处理类
				 */
				// ----------------------------------------------------------------------
				// LLClaimConfirmBL tLLClaimConfirmBL = new LLClaimConfirmBL();
				// if (!tLLClaimConfirmBL.submitData(mInputData, "INSERT")) {
				// // @@错误处理
				// this.mErrors.copyAllErrors(tLLClaimConfirmBL.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "LLClaimConfirmAfterInitService";
				// tError.functionName = "submitData";
				// tError.errorMessage = "数据提交失败!";
				// this.mErrors.addOneError(tError);
				// mResult.clear();
				// mInputData = null;
				// return false;
				// } else {
				// VData tVDate = new VData();
				// tVDate = tLLClaimConfirmBL.getResult();
				// logger.debug("-----start Service getData from BL");
				// MMap tMap = new MMap();
				// tMap = (MMap) tVDate.getObjectByObjectName("MMap", 0);
				// map.add(tMap);
				// tReturn = true;
				// }
				/**
				 * zhangzheng 2009-02-10 将给付确认功能迁移到理赔结案处理
				 */
				// //审批通过
				// LLClaimConfirmPassBL tLLClaimConfirmPassBL = new
				// LLClaimConfirmPassBL();
				// if (!tLLClaimConfirmPassBL.submitData(mInputData, "")) {
				// // @@错误处理
				// this.mErrors.copyAllErrors(tLLClaimConfirmPassBL.
				// mErrors);
				// CError tError = new CError();
				// tError.moduleName = "LLClaimConfirmAfterInitService";
				// tError.functionName = "dealData";
				// tError.errorMessage = "数据提交失败!";
				// this.mErrors.addOneError(tError);
				// mResult.clear();
				// mInputData = null;
				// return false;
				// } else {
				// VData tVDate = new VData();
				// tVDate = tLLClaimConfirmPassBL.getResult();
				// logger.debug("-----start Service getData from BL");
				// MMap tMap = new MMap();
				// tMap = (MMap) tVDate.getObjectByObjectName("MMap", 0);
				// map.add(tMap);
				// tReturn = true;
				// }
				/**
				 * 2009-02-11 zhangzheng 解除保单挂起和更改死亡标记工作放在审核确认时做
				 * 
				 */
				// //解除保单挂起
				// LLLcContReleaseBL tLLLcContReleaseBL = new
				// LLLcContReleaseBL();
				// if (!tLLLcContReleaseBL.submitData(mInputData, "")) {
				// // @@错误处理
				// this.mErrors.copyAllErrors(tLLLcContReleaseBL.mErrors);
				// CError tError = new CError();
				// tError.moduleName = "LLClaimConfirmAfterInitService";
				// tError.functionName = "dealData";
				// tError.errorMessage = "解除保单挂起失败!";
				// this.mErrors.addOneError(tError);
				// return false;
				// } else {
				// VData tempVData = new VData();
				// tempVData = tLLLcContReleaseBL.getResult();
				// MMap tMap = new MMap();
				// tMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
				// map.add(tMap);
				// }
				//
				// //更改死亡标志
				// if (!dealDeath()) {
				// return false;
				// }
			}
			if (tAuditConclusion.equals("6")) {
				LWMissionDB tLWMissionDB = new LWMissionDB();
				LWMissionSchema tLWMissionSchema = new LWMissionSchema();
				tLWMissionDB.setActivityID("0000009035");
				tLWMissionDB.setSubMissionID(tSubMissionID);
				tLWMissionDB.setMissionID(tMissionID);

				if (tLWMissionDB.getInfo()) {
					tLWMissionSchema = tLWMissionDB.getSchema();
				} else {
					// @@错误处理
					this.mErrors.copyAllErrors(tLWMissionDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "LLClaimConfirmAfterInitService";
					tError.functionName = "dealData";
					tError.errorMessage = "未查询到相关的工作流!";
					this.mErrors.addOneError(tError);
					return false;
				}
				tLWMissionSchema.setMissionProp19("0");
				tLWMissionSchema.setMissionProp10("上报");
				tLWMissionSchema.setDefaultOperator("");
				// map.put(tLWMissionSchema, "DELETE&INSERT");
				map.put(tLWMissionSchema, "UPDATE");
			}
			
			//批量案件审核结论为1-全部拒付或4-审批管理时进行受益人自动分配
			if("1".equals(mPLFlag)){
				if("1".equals(tAuditConclusion)||"4".equals(tAuditConclusion)){
					
					 //String使用TransferData打包后提交
				    TransferData pTransferData = new TransferData();
				    pTransferData.setNameAndValue("ClmNo", mClmNo);
				    pTransferData.setNameAndValue("BnfKind", "A");
				    
				    //准备传输数据 VData
				    VData pVData = new VData();
				    pVData.add(mG);
				    pVData.add(pTransferData);

				    //先删除已保存到饿受益人信息
				    try
				    {
				        LLBnfAllRepealBL tLLBnfAllRepealBL = new LLBnfAllRepealBL();
				        //数据提交
				        if (!tLLBnfAllRepealBL.submitData(pVData,"REPEAL"))
				        {
				    		CError.buildErr(this, "受益人自动分配时发生错误！");
							return false;
				        }
				    }
				    catch(Exception ex)
				    {
			    		CError.buildErr(this, "受益人自动分配时发生错误！");
						return false;
				    }
				    
					VData tVData = new VData();
					tVData.add(mG);
					mTransferData.setNameAndValue("ClmNo", mClmNo);
					tVData.add(mTransferData);
					LLGrpBnf tLLGrpBnf = new LLGrpBnf();
					if(!tLLGrpBnf.submitData(mInputData, cOperate)){
						CError.buildErr(this, "受益人自动分配时发生错误！");
						return false;
					}
					MMap tempMMap = new MMap();
					VData tempVData = new VData();
					tempVData = tLLGrpBnf.getResult();
					tempMMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
					map.add(tempMMap);
				}
			}
			// //节点删除,6超权限上报
			// if (!tAuditConclusion.equals("6")&&!tAuditConclusion.equals("4")
			// &&!tAuditConclusion.equals("5")
			// ) {
			// String wFlag = "0000009075"; //借用该节点的删除方法
			// TransferData STransferData = new TransferData();
			// VData SVData = new VData();
			// STransferData.setNameAndValue("ClmNo", mClmNo);
			// STransferData.setNameAndValue("MissionID", tMissionID);
			// STransferData.setNameAndValue("SubMissionID", tSubMissionID);
			// STransferData.setNameAndValue("ActivityID", "0000009035");
			// SVData.add(mG);
			// SVData.add(STransferData);
			// ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI();
			// if (!tClaimWorkFlowUI.submitData(SVData, wFlag)) {
			// // @@错误处理
			// this.mErrors.copyAllErrors(tClaimWorkFlowUI.mErrors);
			// CError tError = new CError();
			// tError.moduleName = "LLClaimConfirmAfterInitService";
			// tError.functionName = "dealData";
			// tError.errorMessage = "节点删除失败!";
			// this.mErrors.addOneError(tError);
			// return false;
			// } else {
			// VData tempVData = new VData();
			// tempVData = tClaimWorkFlowUI.getResult();
			// MMap tMap = new MMap();
			// tMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
			// map.add(tMap);
			// }
			// }
			/**
			 * zhangzheng 2009-02-10 保存审核结论时不能进行工作流的扭转,必须到审核确认时才能扭转
			 */
			// tongmeng 2009-01-06 add
			// 审批流程
			// if (tAuditConclusion.equals("4")||tAuditConclusion.equals("5")) {
			//                
			// String wFlag = "0000009035"; //借用该节点的删除方法
			// TransferData STransferData = new TransferData();
			// VData SVData = new VData();
			// Vector tVector = this.mTransferData.getValueNames();
			// for(int i=0;i<tVector.size();i++)
			// {
			// String tName = (String)tVector.get(i);
			// String tValue = (String)mTransferData.getValueByName(tName);
			// if(!tName.equals("ActivityID")&&
			// !tName.equals("BudgetFlag")
			// &&!tName.equals("ActivityID")
			// //&&!tName.equals("ActivityID")
			// )
			// {
			// logger.debug("tName:"+tName+":tValue:"+tValue);
			// STransferData.setNameAndValue(tName, tValue);
			// }
			// }
			// //tongmeng 2009-01-09 modify
			// //报案类型取RgtState
			// String rSql = "select RgtState,grpcontno from llreport where
			// rptno='" + mClmNo + "' ";
			// ExeSQL tempExeSQL = new ExeSQL();
			// SSRS tSSRS = new SSRS();
			// tSSRS = tempExeSQL.execSQL(rSql);
			// if(tSSRS.getMaxRow()>0)
			// {
			// STransferData.setNameAndValue("RgtState", tSSRS.GetText(1,1));
			// STransferData.setNameAndValue("GrpContNo", tSSRS.GetText(1,2));
			// }
			//                   
			// SVData.add(mLLClaimUWMainSchema);
			// SVData.add(mLLClaimSchema);
			// //IsRunBL
			// STransferData.setNameAndValue("ClmNo", mClmNo);
			// STransferData.setNameAndValue("IsRunBL", "1");
			// STransferData.setNameAndValue("RptNo", this.mClmNo);
			// //AuditConclusion
			// STransferData.setNameAndValue("AuditConclusion",
			// tAuditConclusion);
			// if(tAuditConclusion.equals("4"))
			// {
			// STransferData.setNameAndValue("BudgetFlag", "0");
			// }
			// else
			// {
			// STransferData.setNameAndValue("BudgetFlag", "1");
			// //RptorState
			// STransferData.removeByName("RptorState");
			// STransferData.setNameAndValue("RptorState", "20");
			// }
			// STransferData.setNameAndValue("MissionID", tMissionID);
			// STransferData.setNameAndValue("SubMissionID", tSubMissionID);
			// STransferData.setNameAndValue("ActivityID", "0000009035");
			// SVData.add(mG);
			// SVData.add(STransferData);
			// ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI();
			// if (!tClaimWorkFlowUI.submitData(SVData, wFlag)) {
			// // @@错误处理
			// this.mErrors.copyAllErrors(tClaimWorkFlowUI.mErrors);
			// CError tError = new CError();
			// tError.moduleName = "LLClaimConfirmAfterInitService";
			// tError.functionName = "dealData";
			// tError.errorMessage = "节点删除失败!";
			// this.mErrors.addOneError(tError);
			// return false;
			// } else {
			// VData tempVData = new VData();
			// tempVData = tClaimWorkFlowUI.getResult();
			// MMap tMap = new MMap();
			// tMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
			// map.add(tMap);
			// }
			// }
		}
		// else {
		// map.put(mLLClaimUWMainSchema, "DELETE&INSERT");
		// map.put(mLLClaimSchema, "DELETE&INSERT");
		// }
		//
		// if (tAuditConclusion != null && tAuditConclusion.equals("0")) {
		//
		// //更改赔案状态为结案
		// String sql3 =
		// " update LLRegister set ClmState = '60' ,EndCaseFlag = '1'
		// ,EndCaseDate = '" +
		// PubFun.getCurrentDate() + "'" //结案日期
		// + " where RgtNo = '" + mClmNo + "'";
		// map.put(sql3, "UPDATE");
		//                
		// String sql4 =
		// " update llclaim set ClmState = '60' , EndCaseDate = '" +
		// PubFun.getCurrentDate() + "'" //结案日期
		// + " , GiveType = '" +
		// mLLClaimUWMainSchema.getAuditConclusion() + "'" //赔付结论
		// + " , ClmUWer = '" + mG.Operator + "'" //理赔员
		// + " where clmno = '" + mClmNo + "'";
		// map.put(sql4, "UPDATE");
		//                
		//
		// String sql7 =
		// " update llclaimpolicy set clmstate = '60' ,EndCaseDate = '" +
		// PubFun.getCurrentDate() + "'" //结案日期
		// + " where clmno = '" + mClmNo + "'";
		//
		// map.put(sql7, "UPDATE");
		//
		// //删除账单表帐户理赔生成的mainfeeno = '0000000000'的数据
		// String sql5 =
		// "delete llcasereceipt where mainfeeno = '0000000000' and clmno = '" +
		// mClmNo + "'";
		// String sql6 =
		// "delete llfeemain where mainfeeno = '0000000000' and clmno = '" +
		// mClmNo + "'";
		// map.put(sql5, "DELETE");
		// map.put(sql6, "DELETE");
		// }
		//            
		// // else if (tAuditConclusion != null && tAuditConclusion.equals("1"))
		// {
		// // map.put(mLLClaimUWMainSchema, "DELETE&INSERT");
		// // //更改赔案状态为80拒赔
		// // String sql3 =
		// // " update LLRegister set ClmState = '80', EndCaseFlag = '1',
		// EndCaseDate = '"
		// // + PubFun.getCurrentDate() + "'" //拒赔日期
		// // + " where RgtNo = '" + mClmNo + "'";
		// // map.put(sql3, "UPDATE");
		// //
		// // String sql4 =
		// // " update llclaim set ClmState = '80', EndCaseDate = '"
		// // + PubFun.getCurrentDate() + "'" //拒赔日期
		// // + " , GiveType = '" +
		// // mLLClaimUWMainSchema.getAuditConclusion() + "'" //赔付结论
		// // + " , ClmUWer = '" + mG.Operator + "'" //理赔员
		// // + " where clmno = '" + mClmNo + "'";
		// // map.put(sql4, "UPDATE");
		// //
		// // String sql5 =
		// // " update llclaimpolicy set ClmState = '80', EndCaseDate = '"
		// // + PubFun.getCurrentDate() + "'" //拒赔日期
		// // + " , GiveType = '" +
		// // mLLClaimUWMainSchema.getAuditConclusion() + "'" //赔付结论
		// // + " , ClmUWer = '" + mG.Operator + "'" //理赔员
		// // + " where clmno = '" + mClmNo + "'";
		// // map.put(sql5, "UPDATE");
		// //
		// // }
		tReturn = true;
		// }
		return tReturn;

	}

	/**
	 * 针对死亡案件更改死亡日期和标志 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean dealDeath() {
		LLCaseDB tLLCaseDB = new LLCaseDB();
		tLLCaseDB.setCaseNo(mClmNo);
		LLCaseSet tLLCaseSet = tLLCaseDB.query();
		if (tLLCaseSet == null && tLLCaseSet.size() < 1) {
			CError tError = new CError();
			tError.moduleName = "LLClaimConfirmAfterInitService";
			tError.functionName = "dealData";
			tError.errorMessage = "查询分案信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		for (int i = 1; i <= tLLCaseSet.size(); i++) {
			String tCNo = tLLCaseSet.get(i).getCustomerNo();
			LLAppClaimReasonDB tLLAppClaimReasonDB = new LLAppClaimReasonDB();
			tLLAppClaimReasonDB.setCaseNo(mClmNo);
			tLLAppClaimReasonDB.setRgtNo(mClmNo);
			tLLAppClaimReasonDB.setCustomerNo(tCNo);
			LLAppClaimReasonSet tLLAppClaimReasonSet = tLLAppClaimReasonDB.query();
			if (tLLAppClaimReasonSet == null && tLLAppClaimReasonSet.size() < 1) {
				CError tError = new CError();
				tError.moduleName = "LLClaimConfirmAfterInitService";
				tError.functionName = "dealData";
				tError.errorMessage = "查询赔案理赔类型信息失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			for (int j = 1; j <= tLLAppClaimReasonSet.size(); j++) {
				String tCode = tLLAppClaimReasonSet.get(j).getReasonCode().substring(2, 3);
				if (tCode.equals("02")) { // 死亡
					// 更改立案分案表
					String sql3 = " update llcase set DieFlag = '1'," + " DeathDate = AccDate where"
							+ " CaseNo = '" + mClmNo + "'" + " and CustomerNo = '" + tCNo + "'";
					map.put(sql3, "UPDATE");
					// 更改报案分案表
					String sql4 = " update LLSubReport set DieFlag = '1'," + " DieDate = AccDate where"
							+ " CaseNo = '" + mClmNo + "'" + " and CustomerNo = '" + tCNo + "'";
					map.put(sql4, "UPDATE");
					// 更改客户表
					String sql5 = " update LDPerson set DeathDate = to_date('"
							+ tLLCaseSet.get(i).getAccDate() + "','yyyy-mm-dd') where" + " CustomerNo = '"
							+ tCNo + "'";
					map.put(sql5, "UPDATE");

					break;
				}
			}
		}
		return true;
	}

	/**
	 * 取到LLBNF表相关数据
	 * 
	 * @return boolean
	 */
	private boolean getLLBnf() {
		LLCaseDB mLLCaseDB = new LLCaseDB();
		mLLCaseDB.setCaseNo(mClmNo);
		LLCaseSet tLLCaseSet = mLLCaseDB.query();
		if (tLLCaseSet == null && tLLCaseSet.size() < 1) {
			CError tError = new CError();
			tError.moduleName = "LLClaimConfirmAfterInitService";
			tError.functionName = "getLLBnf";
			tError.errorMessage = "查询分案信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		String strSql = " select polno,sum(pay),GrpContNo,GrpPolNo,ContNo from LLBalance where"
				+ " ClmNo = '" + mClmNo + "' group by polno,GrpContNo,GrpPolNo,ContNo";
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(strSql);
		if (tExeSQL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tExeSQL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimAuditBL";
			tError.functionName = "getLLBnf";
			tError.errorMessage = "未查询到LLBNF表的信息!";
			logger.debug("------------------------------------------------------");
			logger.debug("--LLClaimCalAutoBL.dealData()--理赔计算时发生错误!" + strSql);
			logger.debug("------------------------------------------------------");
			this.mErrors.addOneError(tError);
			return false;
		}
		for (int a = 1; a <= tSSRS.getMaxRow(); a++) {
			String PolNo = tSSRS.GetText(a, 1);
			String pay = tSSRS.GetText(a, 2);
			String GrpContNo = tSSRS.GetText(a, 3);
			String GrpPolNo = tSSRS.GetText(a, 4);
			String ContNo = tSSRS.GetText(a, 5);

			String bnfsql = "select distinct insuredno from llclaimpolicy where clmno='" + mClmNo
					+ "' and polno='" + PolNo + "' ";
			// for (int i = 1; i <= tLLCaseSet.size(); i++) { //这样取客户号效率太低
			LDPersonSchema mLDPersonSchema = new LDPersonSchema();
			LDPersonDB mLDPersonDB = new LDPersonDB();
			LLBnfSchema mLLBnfSchema = new LLBnfSchema();
			// String tCNo = tLLCaseSet.get(i).getCustomerNo();
			String tCNo = tExeSQL.getOneValue(bnfsql);
			mLDPersonDB.setCustomerNo(tCNo);
			if (mLDPersonDB.getInfo()) {
				mLDPersonSchema = mLDPersonDB.getSchema();
			}
			// String strSql2 = " select * from lcpol where polno = '" + PolNo +
			// "' and insuredno = '" + tCNo + "'";
			String strSql2 = " select * from lcpol where polno = '" + PolNo + "'";

			SSRS tSSRS2 = tExeSQL.execSQL(strSql2);
			if (tSSRS2.getMaxRow() > 0) { // 判断赔付的金额是否是该出险人的
				mLLBnfSchema.setClmNo(mClmNo);
				mLLBnfSchema.setCaseNo(mClmNo);
				mLLBnfSchema.setBatNo("0");
				mLLBnfSchema.setGrpContNo(GrpContNo);
				mLLBnfSchema.setGrpPolNo(GrpPolNo);
				mLLBnfSchema.setContNo(ContNo);
				mLLBnfSchema.setBnfKind("A");
				mLLBnfSchema.setPolNo(PolNo);
				mLLBnfSchema.setInsuredNo(tCNo);
				mLLBnfSchema.setBnfNo("1");
				mLLBnfSchema.setCustomerNo(tCNo);
				mLLBnfSchema.setName(mLDPersonSchema.getName());
				mLLBnfSchema.setPayeeNo(tCNo);
				mLLBnfSchema.setPayeeName(mLDPersonSchema.getName());
				mLLBnfSchema.setBnfType("0");
				mLLBnfSchema.setBnfGrade("0");
				mLLBnfSchema.setRelationToInsured("00");
				mLLBnfSchema.setSex(mLDPersonSchema.getSex());
				mLLBnfSchema.setBirthday(mLDPersonSchema.getBirthday());
				mLLBnfSchema.setIDType(mLDPersonSchema.getIDType());
				mLLBnfSchema.setIDNo(mLDPersonSchema.getIDNo());
				mLLBnfSchema.setRelationToPayee("00");
				mLLBnfSchema.setPayeeSex(mLDPersonSchema.getSex());
				mLLBnfSchema.setPayeeBirthday(mLDPersonSchema.getBirthday());
				mLLBnfSchema.setPayeeIDType(mLDPersonSchema.getIDType());
				mLLBnfSchema.setPayeeIDNo(mLDPersonSchema.getIDNo());
				mLLBnfSchema.setGetMoney(pay);
				mLLBnfSchema.setBnfLot("100");
				mLLBnfSchema.setCasePayMode("");
				mLLBnfSchema.setCasePayFlag("0"); // 保险金支付标志
				mLLBnfSchema.setBankCode("");
				mLLBnfSchema.setBankAccNo("");
				mLLBnfSchema.setAccName("");
				mLLBnfSet.add(mLLBnfSchema);
			}
			// }
			// String使用TransferData打包后提交
			tTransferData.setNameAndValue("ClmNo", mClmNo);
			tTransferData.setNameAndValue("PolNo", PolNo);
			tTransferData.setNameAndValue("BnfKind", "A");
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
			mResult.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimAuditBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

}
