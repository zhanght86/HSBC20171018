package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 自垫清偿回退BL
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Nicholas
 * @version 1.0
 */

public class PEdorTRBackDetailBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorTRBackDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	public PEdorTRBackDetailBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括""和""
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 得到外部传入的数据,将数据备份到本类中
		if (!checkData()) {
			return false;
		}
		logger.debug("---End checkData---");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("---End dealData---");

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return this.mErrors;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			// 需要回退的保全项目
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
		} catch (Exception e) {
			e.printStackTrace();
			return this.makeError("getInputData", "接收前台数据失败！");
		}
		if (mGlobalInput == null || mLPEdorItemSchema == null) {
			return this.makeError("getInputData", "传入数据有误！");
		}

		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(mLPEdorItemSchema);
		if (!tLPEdorItemDB.getInfo()) {
			// @@错误处理
			return this.makeError("checkData", "无保全数据！");
		}

		mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());
		if (!"0".equals(tLPEdorItemDB.getEdorState())) {
			// @@错误处理
			return this.makeError("checkData", "此项目尚未确认生效！");
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		try {
			logger.debug("---自垫清偿保全回退处理---");

			// ***说明：LJAGetDraw、LJAGetEndorse、LJAPayPerson 在公共部分处理，因此以下注掉
			/*
			 * //获得此时的日期和时间 String strCurrentDate = PubFun.getCurrentDate();
			 * String strCurrentTime = PubFun.getCurrentTime();
			 * 
			 * //生成“批改补退费表（应收/应付）”数据用 LJSGetEndorseSchema tLJSGetEndorseSchema =
			 * null; LJSGetEndorseSet tLJSGetEndorseSet = new
			 * LJSGetEndorseSet(); StringBuffer tSB = new StringBuffer();
			 * //查询“单位名称” String tGrpName = ""; String tSql = "SELECT a.GrpName" + "
			 * FROM LCAddress a,LCInsured b,LCPol c" + " WHERE
			 * a.CustomerNo=b.InsuredNo" + " and a.AddressNo=b.AddressNo" + "
			 * and b.InsuredNo=c.InsuredNo" + " and b.ContNo=c.ContNo" + " and
			 * c.PolNo='" + mLPEdorItemSchema.getPolNo() + "'"; ExeSQL tExeSQL =
			 * new ExeSQL(); SSRS tSSRS = tExeSQL.execSQL(tSql); if (tSSRS !=
			 * null && tSSRS.MaxRow > 0) { tGrpName = tSSRS.GetText(1,1); }
			 * 
			 * LPReturnLoanDB tLPReturnLoanDB = new LPReturnLoanDB();
			 * tLPReturnLoanDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			 * tLPReturnLoanDB.setEdorType(mLPEdorItemSchema.getEdorType());
			 * LPReturnLoanSet tLPReturnLoanSet = tLPReturnLoanDB.query(); if
			 * (tLPReturnLoanSet == null && tLPReturnLoanSet.size() <= 0) { //
			 * @@错误处理 return this.makeError("dealData", "查询保全还款信息失败！"); }
			 * LCPolDB tLCPolDB = new LCPolDB();
			 * tLCPolDB.setPolNo(mLPEdorItemSchema.getPolNo()); if
			 * (!tLCPolDB.getInfo()) { // @@错误处理 return
			 * this.makeError("dealData", "查询自垫险种信息失败！"); } LCPolSchema
			 * tLCPolSchema = tLCPolDB.getSchema(); for (int i=1;i<=tLPReturnLoanSet.size();i++) {
			 * //生成“批改补退费表（应收/应付）”数据（本金） tLJSGetEndorseSchema = new
			 * LJSGetEndorseSchema();
			 * tLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorAcceptNo());
			 * tLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
			 * tLJSGetEndorseSchema.setFeeOperationType("TR");
			 * 
			 * //查询“补/退费财务类型” //清空缓冲区 tSB.replace(0, tSB.length(), "");
			 * //组织SQL语句 tSB.append("SELECT CodeName" + " FROM ((select '1' as
			 * flag,CodeName" + " from LDCode1" + " where CodeType='TR' and
			 * Code='HK' and Code1='" + tLCPolSchema.getRiskCode() + "')" + "
			 * union" + " (select '2' as flag,CodeName" + " from LDCode1" + "
			 * where CodeType='TR' and Code='HK' and Code1='000000'))" + " WHERE
			 * rownum=1" + " ORDER BY flag"); //查询 //tSSRS = new SSRS(); tSSRS =
			 * tExeSQL.execSQL(tSB.toString()); if (tSSRS == null &&
			 * tSSRS.MaxRow <= 0) { // @@错误处理 return this.makeError("dealData",
			 * "查询还款的“补/退费财务类型”时产生错误！"); }
			 * tLJSGetEndorseSchema.setFeeFinaType(tSSRS.GetText(1,1));
			 * tLJSGetEndorseSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
			 * tLJSGetEndorseSchema.setContNo(tLCPolSchema.getContNo());
			 * tLJSGetEndorseSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
			 * tLJSGetEndorseSchema.setPolNo(tLCPolSchema.getPolNo());
			 * tLJSGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo());
			 * tLJSGetEndorseSchema.setOtherNoType("3");
			 * tLJSGetEndorseSchema.setDutyCode("000000");
			 * tLJSGetEndorseSchema.setPayPlanCode("000000");
			 * tLJSGetEndorseSchema.setAppntNo(tLCPolSchema.getAppntNo());
			 * tLJSGetEndorseSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
			 * tLJSGetEndorseSchema.setGetDate(strCurrentDate);
			 * tLJSGetEndorseSchema.setGetMoney(-tLPReturnLoanSet.get(i).getReturnMoney());
			 * tLJSGetEndorseSchema.setKindCode(tLCPolSchema.getKindCode());
			 * tLJSGetEndorseSchema.setRiskCode(tLCPolSchema.getRiskCode());
			 * tLJSGetEndorseSchema.setRiskVersion(tLCPolSchema.getRiskVersion());
			 * tLJSGetEndorseSchema.setManageCom(mGlobalInput.ManageCom);
			 * tLJSGetEndorseSchema.setAgentCom(tLCPolSchema.getAgentCom());
			 * tLJSGetEndorseSchema.setAgentType(tLCPolSchema.getAgentType());
			 * tLJSGetEndorseSchema.setAgentCode(tLCPolSchema.getAgentCode());
			 * tLJSGetEndorseSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
			 * tLJSGetEndorseSchema.setGrpName(tGrpName);
			 * tLJSGetEndorseSchema.setHandler(""); //暂时不知道如何处理
			 * tLJSGetEndorseSchema.setPolType("1");
			 * //tLJSGetEndorseSchema.setApproveCode(""); //暂时不知道如何处理
			 * //tLJSGetEndorseSchema.setApproveDate(""); //暂时不知道如何处理
			 * //tLJSGetEndorseSchema.setApproveTime(""); //暂时不知道如何处理
			 * tLJSGetEndorseSchema.setGetFlag("0");
			 * tLJSGetEndorseSchema.setSerialNo(""); //暂时不知道如何处理
			 * tLJSGetEndorseSchema.setOperator(this.mGlobalInput.Operator);
			 * tLJSGetEndorseSchema.setMakeDate(strCurrentDate);
			 * tLJSGetEndorseSchema.setMakeTime(strCurrentTime);
			 * tLJSGetEndorseSchema.setModifyDate(strCurrentDate);
			 * tLJSGetEndorseSchema.setModifyTime(strCurrentTime);
			 * tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_AutoPayPrem +
			 * String.valueOf(i));
			 * 
			 * tLJSGetEndorseSet.add(tLJSGetEndorseSchema);
			 * 
			 * //生成“批改补退费表（应收/应付）”数据（利息） tLJSGetEndorseSchema = new
			 * LJSGetEndorseSchema();
			 * tLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorAcceptNo());
			 * tLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
			 * tLJSGetEndorseSchema.setFeeOperationType("TR");
			 * 
			 * //查询“补/退费财务类型” //清空缓冲区 tSB.replace(0, tSB.length(), "");
			 * //组织SQL语句 tSB.append("SELECT CodeName" + " FROM ((select '1' as
			 * flag,CodeName" + " from LDCode1" + " where CodeType='TR' and
			 * Code='LX' and Code1='" + tLCPolSchema.getRiskCode() + "')" + "
			 * union" + " (select '2' as flag,CodeName" + " from LDCode1" + "
			 * where CodeType='TR' and Code='LX' and Code1='000000'))" + " WHERE
			 * rownum=1" + " ORDER BY flag"); //查询 tSSRS = new SSRS(); tSSRS =
			 * tExeSQL.execSQL(tSB.toString()); if (tSSRS == null &&
			 * tSSRS.MaxRow <= 0) { // @@错误处理 return this.makeError("dealData",
			 * "查询利息的“补/退费财务类型”时产生错误！"); }
			 * tLJSGetEndorseSchema.setFeeFinaType(tSSRS.GetText(1,1));
			 * 
			 * tLJSGetEndorseSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
			 * tLJSGetEndorseSchema.setContNo(tLCPolSchema.getContNo());
			 * tLJSGetEndorseSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
			 * tLJSGetEndorseSchema.setPolNo(tLCPolSchema.getPolNo());
			 * tLJSGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo());
			 * tLJSGetEndorseSchema.setOtherNoType("3");
			 * tLJSGetEndorseSchema.setDutyCode("000000");
			 * tLJSGetEndorseSchema.setPayPlanCode("000000");
			 * tLJSGetEndorseSchema.setAppntNo(tLCPolSchema.getAppntNo());
			 * tLJSGetEndorseSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
			 * tLJSGetEndorseSchema.setGetDate(strCurrentDate);
			 * tLJSGetEndorseSchema.setGetMoney(-tLPReturnLoanSet.get(i).getReturnInterest());
			 * tLJSGetEndorseSchema.setKindCode(tLCPolSchema.getKindCode());
			 * tLJSGetEndorseSchema.setRiskCode(tLCPolSchema.getRiskCode());
			 * tLJSGetEndorseSchema.setRiskVersion(tLCPolSchema.getRiskVersion());
			 * tLJSGetEndorseSchema.setManageCom(mGlobalInput.ManageCom);
			 * tLJSGetEndorseSchema.setAgentCom(tLCPolSchema.getAgentCom());
			 * tLJSGetEndorseSchema.setAgentType(tLCPolSchema.getAgentType());
			 * tLJSGetEndorseSchema.setAgentCode(tLCPolSchema.getAgentCode());
			 * tLJSGetEndorseSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
			 * tLJSGetEndorseSchema.setGrpName(tGrpName);
			 * tLJSGetEndorseSchema.setHandler(""); //暂时不知道如何处理
			 * tLJSGetEndorseSchema.setPolType("1");
			 * //tLJSGetEndorseSchema.setApproveCode(""); //暂时不知道如何处理
			 * //tLJSGetEndorseSchema.setApproveDate(""); //暂时不知道如何处理
			 * //tLJSGetEndorseSchema.setApproveTime(""); //暂时不知道如何处理
			 * tLJSGetEndorseSchema.setGetFlag("0");
			 * tLJSGetEndorseSchema.setSerialNo(""); //暂时不知道如何处理
			 * tLJSGetEndorseSchema.setOperator(this.mGlobalInput.Operator);
			 * tLJSGetEndorseSchema.setMakeDate(strCurrentDate);
			 * tLJSGetEndorseSchema.setMakeTime(strCurrentTime);
			 * tLJSGetEndorseSchema.setModifyDate(strCurrentDate);
			 * tLJSGetEndorseSchema.setModifyTime(strCurrentTime);
			 * tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_AutoPayPremInterest +
			 * String.valueOf(i));
			 * 
			 * tLJSGetEndorseSet.add(tLJSGetEndorseSchema); }
			 * mMap.put(tLJSGetEndorseSet,"DELETE&INSERT");
			 */

			mResult.clear();
			mResult.add(mMap);
		} catch (Exception e) {
			// @@错误处理
			return this.makeError("dealData", "数据处理错误！" + e.getMessage());
		}
		return true;
	}

	/**
	 * 创建一个错误
	 * 
	 * @param vFuncName
	 *            发生错误的函数名
	 * @param vErrMsg
	 *            错误信息
	 * @return 布尔值（false--永远返回此值）
	 */
	private boolean makeError(String vFuncName, String vErrMsg) {
		CError tError = new CError();
		tError.moduleName = "PEdorTRBackDetailBL";
		tError.functionName = vFuncName;
		tError.errorMessage = vErrMsg;
		this.mErrors.addOneError(tError);
		return false;
	}

	public static void main(String[] args) {
		PEdorTRBackDetailBL tPEdorTRBackDetailBL = new PEdorTRBackDetailBL();
	}
}
