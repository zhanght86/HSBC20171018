package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LJSPayGrpSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LOPRTManagerSubSchema;
import com.sinosoft.lis.vschema.LASPayPersonSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJSPayGrpSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LOPRTManagerSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author Gaoht
 * @version 1.0
 */
public class GrpFeePeriodicSingBL {
private static Logger logger = Logger.getLogger(GrpFeePeriodicSingBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类* */
	public CErrors mErrors = new CErrors();
	/** 往后台传输数据容器 */
	private GlobalInput tGI = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	MMap map = new MMap();
	/** 数据操作字符串 */
	private String mOperate = "";
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	/** 本逻辑入口保单层全局变量* */
	private String mGrpContNo = "";
	private String mGetNoticeNo = "";
	private String mPrtSeqNo = "";
	private double SumPaymoney = 0;
	private String mPayDate = "";
	private Date mLastPaytoDate = new Date(); // 现在的缴费对应日
	private Date mCurPayToDate = new Date(); // 下一次的缴费对应日

	/** EFT操作时用到的银行信息* */
	private String mBankCode = "";
	private String mBankAccNo = "";
	private String mBankAccName = "";

	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	/** 定义返回数据容器* */
	private VData mResult = new VData();
	private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
	private LOPRTManagerSubSet mLOPRTManagerSubSet = new LOPRTManagerSubSet();
	private LJSPaySet mLJSPaySet = new LJSPaySet();
	private LJSPaySchema mLJSPaySchema = new LJSPaySchema();
	private LASPayPersonSet mLASPayPersonSet = new LASPayPersonSet();
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	private LJSPayGrpSet mLJSPayGrpSet = new LJSPayGrpSet();
	/** 自动续保的数据容器* */
	int AutoInt = 0;
	int NorInt = 0;
	private LCPolSet mLCPolSet = new LCPolSet();
	private LCPremSet mLCPremSet = new LCPremSet();
	private LCDutySet mLCDutySet = new LCDutySet();
	private LCGetSet mLCGetSet = new LCGetSet();
	private Hashtable mHashTable = new Hashtable();// 保存没个险种的保费
	private String[] mRiskStr = null;// 保存每个险种

	public GrpFeePeriodicSingBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public boolean submitData(VData cInputData, String cOperate)

	{
		this.mOperate = cOperate;
		if (!getInputData(cInputData)) {
			return false;
		}

		if (!checkdata()) {
			return false;
		}
		// 去寻找多找团单下的个人的保单 ，然后分别的进行提交，最后需要做的是统一的处理ljspay的数据
		if (!dealAllData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}
		// 如果发现是在做的保单抽挡就需要在此处直接提交。如果是"All"就需要返回结果
		if (cOperate.equals("sing")) {
			// 提交数据
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(mResult, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tSubmit.mErrors);
				return false;
			}

		}
		return true;
	}

	private boolean getInputData(VData mInputData) {

		tGI = ((GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0));

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mLCGrpContSchema = (LCGrpContSchema) mInputData.getObjectByObjectName(
				"LCGrpContSchema", 0);

		if (tGI == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IndiDueFeePartBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的数据，请您确认!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mOperate.equals("sing")) {
			mGrpContNo = (String) mTransferData.getValueByName("GrpContNo");
			String tSql = "select * from lcgrpcont where grpcontno = '"
					+ mGrpContNo + "'";
			logger.debug(tSql);
			LCGrpContDB tLCGrpContDB = new LCGrpContDB();
			LCGrpContSet tLCGrpContSet = new LCGrpContSet();
			tLCGrpContSet = tLCGrpContDB.executeQuery(tSql);

			mLCGrpContSchema = tLCGrpContSet.get(1);
		} else {

			/** @todo 1----本次处理保单号* */
			mGrpContNo = mLCGrpContSchema.getGrpContNo();
		}
		if (mLCGrpContSchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IndiDueFeePartBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取必要保单信息失败";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("开始处理保单:::::::::::::::::::::" + mGrpContNo);
		/** @todo 2----本次处理交费号、印刷号* */
		String tLimit = PubFun.getNoLimit(tGI.ManageCom);
		mGetNoticeNo = PubFun1.CreateMaxNo("PAYNOTICENO", tLimit);
		mPrtSeqNo = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
		if (mGetNoticeNo == null || mPrtSeqNo == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IndiDueFeePartBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "生成交费通知号与打印流水号失败";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			logger.debug("本次处理交费号:::::::::::::::::::" + mGetNoticeNo);
			logger.debug("本次处理打印流水号::::::::::::::::" + mPrtSeqNo);
		}

		/** @todo 3----暂留，为以后处理交费方式的时候做处理。现在全都默认为现金交费* */
		// if
		// (mLCGrpContSchema.getPayLocation()!=null&&mLCGrpContSchema.getPayLocation().equals("0"))
		// {
		// mBankCode = mLCContSchema.getBankCode();
		// mBankAccNo = mLCContSchema.getBankAccNo();
		// mBankAccName = mLCContSchema.getAccName();
		// logger.debug("本次处理进入EFT操作以下为银行信息");
		// logger.debug("银行代码:::::::::::::" + mBankCode);
		// logger.debug("银行账号:::::::::::::" + mBankAccNo);
		// logger.debug("户名:::::::::::::::::" + mBankAccName);
		//
		// if (mBankCode == null || mBankAccNo == null)
		// {
		// CError tError = new CError();
		// tError.moduleName = "IndiDueFeePartBL";
		// tError.functionName = "getInputData";
		// tError.errorMessage = "未得到首期承保时的银行划款信息";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// }
		return true;
	}

	private boolean checkdata() {
		/** @todo 4----校验保单级保单状态* */
		String DateSql = "select min(paytodate) from lcgrppol where GrpContNo='"
				+ mGrpContNo + "' and appflag='1'";
		SSRS nSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		nSSRS = tExeSQL.execSQL(DateSql);

		if (nSSRS.MaxRow <= 0) {
			CError tError = new CError();
			tError.moduleName = "GetChequeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "未找到交费对应日信息";
			this.mErrors.addOneError(tError);
			return false;
		}
		String PaytoDate = nSSRS.GetText(1, 1);

		String StateSql = "select * from LCContState where StateType in ('Lost') and State='1' and contno='"
				+ mGrpContNo
				+ "' and StartDate<='"
				+ PaytoDate
				+ "' and enddate is null";
		LCContStateDB tLCContStateDB = new LCContStateDB();
		LCContStateSet tLCContStateSet = new LCContStateSet();
		tLCContStateSet = tLCContStateDB.executeQuery(StateSql);
		if (tLCContStateSet.size() > 0) {
			this.mErrors.addOneError("保单号:" + mGrpContNo + "已经合同挂失,不能抽档");
			return false;
		}

		/** @todo 5----检验保单是否被挂起* */
		RNHangUp tRNHangUp = new RNHangUp(tGI);
		if (!tRNHangUp.checkHangUP(mGrpContNo)) {
			this.mErrors.copyAllErrors(tRNHangUp.mErrors);
			return false;
		}
		/** @todo 校验是否发过催收* */
		String tSql = "select * from ljspay where 1=1 and othernotype in ('1','2','3','01','02','03') otherno='"
				+ mGrpContNo + "'";
		LJSPaySet tLJSPaySet = new LJSPaySet();
		LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPaySet = tLJSPayDB.executeQuery(tSql);
		if (tLJSPaySet.size() > 0) {
			this.mErrors.addOneError("保单号:" + mGrpContNo + "已经催收");
			return false;
		}

		return true;

	}

	// 将返回的金额进行计算
	private String setCHIMoney(String frMoney, String sePayMoney) {
		double a = Double.parseDouble(frMoney);
		double b = Double.parseDouble(sePayMoney);
		double c = a + b;
		return new DecimalFormat("0.00").format(c);
	}

	// 得到每个险种的金额.通过在每个保费的计算中
	private void setRiskMoney(String tRiskCode, String PayMoney) {
		if (mHashTable.get(tRiskCode) == null) {
			mHashTable.put(tRiskCode, PayMoney);
		} else {
			String str = setCHIMoney((String) mHashTable.get(tRiskCode),
					PayMoney);
			mHashTable.put(tRiskCode, str);
		}
	}

	private boolean dealAllData() {
		ArrayList tArrayList = new ArrayList();
		logger.debug("==========查询此团单下的全部的险种单子=================");
		String tSql = "select * from lcgrppol where grpcontno = '" + mGrpContNo
				+ "'";
		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolSet = tLCGrpPolDB.executeQuery(tSql);
		printStr("共发现此团单下有" + tLCGrpPolSet.size() + "张险种保单");
		for (int j = 0; j < tLCGrpPolSet.size(); j++) {
			tArrayList.add(tLCGrpPolSet.get(j + 1).getRiskCode());
		}
		mRiskStr = new String[tArrayList.size()];
		tArrayList.toArray(mRiskStr);
		for (int i = 0; i < mRiskStr.length; i++) {
			logger.debug("险种的名称为" + mRiskStr[i]);
		}
		logger.debug("==========开始查询此保单下的全部的个人单子做分别的处理==========");
		tSql = "select * from lccont where grpcontno = '" + mGrpContNo + "'";
		LCContSet tLCContSet = new LCContSet();
		LCContDB tLCContDB = new LCContDB();
		tLCContSet = tLCContDB.executeQuery(tSql);
		for (int i = 0; i < tLCContSet.size(); i++) {
			printStr("处理团单" + mGrpContNo + "其中的一张保单"
					+ tLCContSet.get(i + 1).getContNo());
			if (!dealData(tLCContSet.get(i + 1).getContNo())) {
				return false;
			}
		}

		printStr("准备团单险种应收表的数据");
		FDate tD = new FDate();
		LJSPayGrpSchema tLJSPayGrpSchema = null;
		for (int j = 0; j < tLCGrpPolSet.size(); j++) {
			Date tLastPaytoDate = tD.getDate(tLCGrpPolSet.get(j + 1)
					.getPaytoDate());

			Date tCurPayToDate = PubFun.calDate(tLastPaytoDate, tLCGrpPolSet
					.get(j + 1).getPayIntv(), "M", null);
			tLJSPayGrpSchema = new LJSPayGrpSchema();
			tLJSPayGrpSchema.setGrpContNo(mGrpContNo);
			tLJSPayGrpSchema.setPayCount("");
			tLJSPayGrpSchema.setGrpPolNo(tLCGrpPolSet.get(j + 1).getGrpPolNo());
			tLJSPayGrpSchema.setAgentCode(tLCGrpPolSet.get(j + 1)
					.getAgentCode());
			tLJSPayGrpSchema.setAgentCom(tLCGrpPolSet.get(j + 1).getAgentCom());
			tLJSPayGrpSchema.setAgentGroup(tLCGrpPolSet.get(j + 1)
					.getAgentGroup());
			tLJSPayGrpSchema.setAppntNo(mLCGrpContSchema.getAppntNo());
			tLJSPayGrpSchema.setGetNoticeNo(mGetNoticeNo);
			tLJSPayGrpSchema.setSumDuePayMoney((String) mHashTable
					.get(tLCGrpPolSet.get(j + 1).getRiskCode()));
			tLJSPayGrpSchema.setSumActuPayMoney((String) mHashTable
					.get(tLCGrpPolSet.get(j + 1).getRiskCode()));
			tLJSPayGrpSchema.setPayDate(mPayDate);
			tLJSPayGrpSchema.setLastPayToDate(tLastPaytoDate);
			tLJSPayGrpSchema.setPayType("ZC");
			tLJSPayGrpSchema.setCurPayToDate(tCurPayToDate);
			tLJSPayGrpSchema.setApproveCode(tLCGrpPolSet.get(j + 1)
					.getApproveCode());
			tLJSPayGrpSchema.setApproveDate(tLCGrpPolSet.get(j + 1)
					.getApproveDate());
			tLJSPayGrpSchema.setApproveTime(tLCGrpPolSet.get(j + 1)
					.getApproveTime());
			// tLJSPayGrpSchema.setSerialNo(tLCGrpPolSet.get(j).getSerialNo());
			tLJSPayGrpSchema.setOperator(tGI.Operator);
			tLJSPayGrpSchema.setMakeDate(PubFun.getCurrentDate());
			tLJSPayGrpSchema.setMakeTime(PubFun.getCurrentTime());
			tLJSPayGrpSchema.setModifyDate(PubFun.getCurrentDate());
			tLJSPayGrpSchema.setModifyTime(PubFun.getCurrentTime());
			mLJSPayGrpSet.add(tLJSPayGrpSchema);

		}

		// /**@todo 渠道的应手备份表**/
		// mLASPayPersonSet.add(PreperaLAPay(mLJSPayPersonSet));

		// /**@todo 生成打印管理子表数据**/
		// mLOPRTManagerSubSet = PreperaLOPRTManagerSub(tLCPolSet,
		// mLJSPayPersonSet);
		printStr("准备团单应收表的数据");
		mLJSPaySchema.setGetNoticeNo(mGetNoticeNo); // 通知书号
		mLJSPaySchema.setOtherNo(mGrpContNo);
		mLJSPaySchema.setOtherNoType("1"); // 设定个人的渠道
		mLJSPaySchema.setAppntNo(mLCGrpContSchema.getAppntNo());
		mLJSPaySchema.setPayDate(mPayDate);
		logger.debug("^^^^^^^^^^^^^^^^^^^^^^^^^^"
				+ tLCGrpPolSet.get(1).getPaytoDate());
		mLJSPaySchema.setStartPayDate(tLCGrpPolSet.get(1).getPaytoDate()); // 交费最早应缴日期保存上次交至日期
		mLJSPaySchema.setBankOnTheWayFlag("0");
		mLJSPaySchema.setBankSuccFlag("0");
		mLJSPaySchema.setSendBankCount(0); // 送银行次数
		mLJSPaySchema.setApproveCode(mLCGrpContSchema.getApproveCode());
		mLJSPaySchema.setApproveDate(mLCGrpContSchema.getApproveDate());
		mLJSPaySchema.setRiskCode("000000");
		mLJSPaySchema.setBankAccNo(mBankAccNo);
		mLJSPaySchema.setBankCode(mBankCode);
		mLJSPaySchema.setSendBankCount(0);
		mLJSPaySchema.setSerialNo(""); // 流水号
		mLJSPaySchema.setOperator(tGI.Operator);
		mLJSPaySchema.setManageCom(mLCGrpContSchema.getManageCom());
		mLJSPaySchema.setAgentCom(mLCGrpContSchema.getAgentCom());
		mLJSPaySchema.setAgentCode(mLCGrpContSchema.getAgentCode());
		mLJSPaySchema.setAgentType(mLCGrpContSchema.getAgentType());
		mLJSPaySchema.setAgentGroup(mLCGrpContSchema.getAgentGroup());
		mLJSPaySchema.setAccName(mBankAccName);
		mLJSPaySchema.setMakeDate(CurrentDate);
		mLJSPaySchema.setMakeTime(CurrentTime);
		mLJSPaySchema.setModifyDate(CurrentDate);
		mLJSPaySchema.setModifyTime(CurrentTime);
		mLJSPaySchema.setSumDuePayMoney(SumPaymoney);

		/** @todo 生成打印管理表数据* */
		VData prtData = getPrintData(mLJSPaySchema, mPrtSeqNo);
		mLOPRTManagerSet = (LOPRTManagerSet) prtData.getObjectByObjectName(
				"LOPRTManagerSet", 0);
		if (mLOPRTManagerSet.size() == 0) {
			this.mErrors.addOneError("保单号:" + mGrpContNo + "生成通知书&发票数据失败");
			return false;
		}

		return true;
	}

	private boolean dealData(String tempContno) {
		String tPolSql = "select * from lcpol where contno='" + tempContno
				+ "' and appflag='1'";
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSet = tLCPolDB.executeQuery(tPolSql);

		if (tLCPolSet.size() == 0) {
			this.mErrors.addOneError("保单号:" + tempContno + "未找到其险种信息");
			return false;
		}

		// 错误采集
		int nErrNo = 0;
		int aErrNo = 0;
		/** @todo 6----进入险种级处理* */
		for (int i = 1; i <= tLCPolSet.size(); i++) {
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = tLCPolSet.get(i);
			/** @todo 7----定义险种的处理类型* */
			/** 多险种处理不能返回false 要continue跳出循环* */

			String PolNoDealType = DealPolNo(tLCPolSchema);

			/** @todo 8----续期处理* */
			if (PolNoDealType.equals("NormalDeal")) {
				NorInt++;
				logger.debug("-----------------开始续期抽档处理---------------");
				if (!DealRndata(tLCPolSchema)) {
					nErrNo++;
					continue;
				}

			}
			/** @todo 9----自动续保处理* */
			else if (PolNoDealType.equals("RenewDeal")) {
				AutoInt++;
				if (!DealAutoRenewsdata(tLCPolSchema)) {
					aErrNo++;
					continue;
				}
				logger.debug("-----------------开始自动续保处理---------------");
			}
			/** 错误处理* */
			else if (PolNoDealType.equals("UnKnowType")) {
				this.mErrors.addOneError("保单号:" + tempContno + "险种信息不符合业务处理要求");
				aErrNo++;
				nErrNo++;
				continue;
			}
			/** 特殊处理* */
			else {
				continue;
			}
		}

		logger.debug("处理保单" + tempContno + "采集如下信息");
		logger.debug("==================================");
		logger.debug("处理续期::::::::::::::::::" + NorInt);
		logger.debug("其中错误::::::::::::::::::" + nErrNo);
		logger.debug("==================================");
		logger.debug("续保处理::::::::::::::::::" + AutoInt);
		logger.debug("其中错误::::::::::::::::::" + aErrNo);
		logger.debug("==================================");

		if ((AutoInt != 0 && NorInt != 0) && (aErrNo != 0 && nErrNo != 0)
				&& (AutoInt == aErrNo)) {
			return false;
		} else if ((AutoInt != 0 && NorInt == 0) && aErrNo != 0
				&& (AutoInt == aErrNo)) {
			return false;
		} else if ((AutoInt == 0 && NorInt != 0) && nErrNo != 0) {
			return false;
		} else if (AutoInt == 0 && NorInt == 0) {
			return false;
		}

		return true;
	}

	// 准备往后层输出所需要的数据
	// 输出：如果准备数据时发生错误则返回false,否则返回true
	private boolean prepareOutputData() {
		map.put(mLJSPaySchema, "INSERT");
		// map.put(mLASPayPersonSet, "DELETE&INSERT");
		map.put(mLJSPayPersonSet, "INSERT");
		map.put(mLOPRTManagerSet, "INSERT");
		// map.put(mLOPRTManagerSubSet, "INSERT");
		map.put(mLJSPayGrpSet, "INSERT");
		if (AutoInt > 0) {
			map.put(mLCPolSet, "INSERT");
			map.put(mLCPremSet, "INSERT");
			map.put(mLCGetSet, "INSERT");
			map.put(mLCDutySet, "INSERT");
		}
		mResult.add(map);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	private String DealPolNo(LCPolSchema tLCPolSchema) {
		String PolNoDealType = "";
		/** @todo 7.1--处理主险* */
		if (tLCPolSchema.getPolNo().equals(tLCPolSchema.getMainPolNo())) {
			/** @todo 7.1.1--主险趸交* */
			if (tLCPolSchema.getPayIntv() == 0) {
				PolNoDealType = "RenewDeal";
			}
			/** @todo 7.1.2--主险交费期满* */
			else if (tLCPolSchema.getPayEndDate().compareTo(
					tLCPolSchema.getPaytoDate()) <= 0) {
				PolNoDealType = "RenewDeal";
			} else {
				PolNoDealType = "NormalDeal";
			}
		} else {
			/** @todo 7.2--处理附加险* */
			if (tLCPolSchema.getPayIntv() == 12) {
				/** @todo 7.2.1--附加险期交* */
				PolNoDealType = "NormalDeal";
			}
			/** @todo 7.2.2--附加险自动续保* */
			else if (tLCPolSchema.getRnewFlag() == -1) {
				PolNoDealType = "RenewDeal";
			} else if (tLCPolSchema.getRnewFlag() == -2) {
				PolNoDealType = "EdorForEN";
			} else {
				PolNoDealType = "UnKnowType";
			}

		}

		return PolNoDealType;
	}

	private boolean DealRndata(LCPolSchema tLCPolSchema) {
		String tPolNo = tLCPolSchema.getPolNo();

		logger.debug("开始处理保险下险种::::::::::" + tLCPolSchema.getRiskCode());
		logger.debug("险种号:::::::::::::::::::::" + tPolNo);

		/** @todo 校验险种状态* */
		if (!CheckPolNoState(tPolNo, tLCPolSchema.getPaytoDate())) {
			this.mErrors.addOneError("保单号:" + tLCPolSchema.getContNo() + "险种"
					+ tLCPolSchema.getRiskCode() + "已经失效或者终止");
			return false;
		}

		// 计算保单交费日期
		logger.debug("计算新的保单交费日期");

		FDate tD = new FDate();
		Date newBaseDate = new Date();
		String CurrentPayToDate = tLCPolSchema.getPaytoDate();

		int PayInterval = tLCPolSchema.getPayIntv();
		mLastPaytoDate = tD.getDate(CurrentPayToDate);

		mCurPayToDate = PubFun.calDate(mLastPaytoDate, PayInterval, "M", null);
		logger.debug("原交费日期::::::::::::::"
				+ String.valueOf(mLastPaytoDate));
		String strNewPayToDate = tD.getString(mCurPayToDate);
		logger.debug("现交至日期::::::::::::::" + strNewPayToDate);

		/** @todo 8.2交费日期=失效日期=原交至日期+2个月* */
		Date tPayDate = PubFun.calDate(mLastPaytoDate, 2, "M", null);
		mPayDate = tD.getString(tPayDate);
		logger.debug("交费截止日期::::::::::::::" + mPayDate);

		// /**@todo 进入责任层**/ 不走责任了
		// LCDutyDB tLCDutyDB = new LCDutyDB();
		// tLCDutyDB.setPolNo(tPolNo);
		// LCDutySet tLCDutySet = tLCDutyDB.query();

		// for (int i = 1; i <= tLCDutySet.size(); i++)
		// {
		// LCDutySchema tLCDutySchema = new LCDutySchema();
		// tLCDutySchema = tLCDutySet.get(i);
		// String DutyCode = tLCDutySchema.getDutyCode();
		// String nPolNo = tLCDutySchema.getPolNo();
		/** @todo 进入保费层* */
		String LCPremSql = "select * From lcprem where polno = '" + tPolNo
				+ "' and UrgePayFlag='Y' and PayStartDate<='"
				+ tLCPolSchema.getPaytoDate() + "' and PayEndDate>'"
				+ tLCPolSchema.getPaytoDate() + "'";
		logger.debug("LCPremSql=================" + LCPremSql);
		LCPremDB tLCPremDB = new LCPremDB();
		LCPremSet tLCPremSet = new LCPremSet();
		tLCPremSet = tLCPremDB.executeQuery(LCPremSql);
		if (tLCPremSet.size() == 0) {
			this.mErrors.addOneError("未找到该保单下的催缴保费信息");
			return false;
		}
		mLJSPayPersonSet.add(DealPrem(tLCPolSchema, tLCPremSet));
		// }

		return true;
	}

	private boolean DealAutoRenewsdata(LCPolSchema tLCPolSchema) {
		VData tVData = new VData();
		tVData.add(tLCPolSchema);
		tVData.add(tGI);
		// 如果主险传入返回
		if (tLCPolSchema.getPolNo().equals(tLCPolSchema.getMainPolNo())) {
			return false;
		}

		// 检验主险险种状态
		if (!CheckPolNoState(tLCPolSchema.getMainPolNo(), tLCPolSchema
				.getPaytoDate())) {
			this.mErrors.addOneError("保单号:" + tLCPolSchema.getContNo()
					+ "主险已经失效或者终止，附加险不能自动续保");
			return false;
		}

		// 检验附加险险种状态
		if (!CheckPolNoState(tLCPolSchema.getPolNo(), tLCPolSchema
				.getPaytoDate())) {
			this.mErrors.addOneError("保单号:" + tLCPolSchema.getContNo() + "险种"
					+ tLCPolSchema.getRiskCode() + "已经失效或者终止");
			return false;
		}

		// 如果主险为月交或者季交的情况，并不是次抽档都会存在自动续保的情况，
		// 目前的处理方法是比较主险的缴费对应日中的年、月是否到了附加险的终止日期
		// 注意：主险交费期满附加险自动续保的情况不比较
		String Sql = "select * from lcpol where polno='"
				+ tLCPolSchema.getMainPolNo()
				+ "' and paytodate<enddate and appflag='1' and payintv<>0";
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSet = tLCPolDB.executeQuery(Sql);
		if (tLCPolSet.size() > 0) {
			if (tLCPolSet.get(1).getPayIntv() > 0
					&& tLCPolSet.get(1).getPayIntv() != 12) {
				logger.debug("主险" + tLCPolSet.get(1).getRiskCode()
						+ "险种，交费对应日::::::::" + tLCPolSet.get(1).getPaytoDate());
				logger.debug("附加险" + tLCPolSchema.getRiskCode()
						+ "险种，交费对应日::::::::" + tLCPolSchema.getEndDate());
				if (PubFun.calInterval(tLCPolSet.get(1).getPaytoDate(),
						tLCPolSchema.getEndDate(), "M") != 0
						|| PubFun.calInterval(tLCPolSet.get(1).getPaytoDate(),
								tLCPolSchema.getEndDate(), "Y") != 0) {
					this.mErrors.addOneError("保单号:" + tLCPolSchema.getContNo()
							+ "险种" + tLCPolSchema.getRiskCode() + "未到自动续保日期");
					return false;
				}

			}
		}
		AutoReNewDeal tAutoReNewDeal = new AutoReNewDeal();
		if (!tAutoReNewDeal.submitData(tVData, "Deal")) {
			this.mErrors.copyAllErrors(tAutoReNewDeal.mErrors);
			return false;
		}
		/** @todo 生成自动续保新数据* */
		LCPolSchema nLCPolSchema = new LCPolSchema();
		LCPremSet nLCPremSet = new LCPremSet();
		LCGetSet nLCGetSet = new LCGetSet();
		LCDutySet nLCDutySet = new LCDutySet();

		nLCPolSchema = (LCPolSchema) tAutoReNewDeal.getResult()
				.getObjectByObjectName("LCPolSchema", 0);
		nLCPremSet = (LCPremSet) tAutoReNewDeal.getResult()
				.getObjectByObjectName("LCPremBLSet", 0);
		nLCGetSet = (LCGetSet) tAutoReNewDeal.getResult()
				.getObjectByObjectName("LCGetBLSet", 0);
		nLCDutySet = (LCDutySet) tAutoReNewDeal.getResult()
				.getObjectByObjectName("LCDutyBLSet", 0);
		/**
		 * 得到lcprem 的值是从外部传入的值
		 */
		mLJSPayPersonSet.add(DealPrem(tLCPolSchema, nLCPremSet));
		mLCPremSet.add(nLCPremSet);
		mLCPolSet.add(nLCPolSchema);
		mLCGetSet.add(nLCGetSet);
		mLCDutySet.add(nLCDutySet);

		return true;
	}

	/**
	 * 8888888888888888888888888888888888888888888888888888888888888
	 * 传入lcprem的值，这个值是在之前已经做好的值
	 * 
	 * @param tLCPolSchema
	 * @param tLCPremSet
	 * @return
	 */
	private LJSPayPersonSet DealPrem(LCPolSchema tLCPolSchema,
			LCPremSet tLCPremSet) {
		/** @todo 根据保费信息生成应收个人纪录* */
		LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
		FDate tFDate = new FDate();

		for (int i = 1; i <= tLCPremSet.size(); i++) {
			LCPremSchema tLCPremSchema = new LCPremSchema();
			tLCPremSchema = tLCPremSet.get(i);
			String tOperator = "";
			/** @todo 豁免保单的特殊处理* */
			if (tLCPremSchema.getFreeFlag() != null) {
				String PaytoDate = tFDate.getString(mLastPaytoDate);
				logger.debug("::::::::::::::::::豁免日期校对:::::::::::::::::::::::");
				logger.debug("交费对应日:::::::::::::::::::"
						+ tFDate.getString(mLastPaytoDate)
						+ "::::::::::::::::::::::");
				logger.debug("豁免起期:::::::::::::::::::"
						+ tLCPremSchema.getFreeStartDate()
						+ "::::::::::::::::::::::");
				logger.debug("豁免止期:::::::::::::::::::"
						+ tLCPremSchema.getFreeEndDate()
						+ "::::::::::::::::::::::");

				logger.debug("");
				if (tLCPremSchema.getFreeFlag().equals("1")
						&& tLCPremSchema.getFreeStartDate()
								.compareTo(PaytoDate) <= 0
						&& tLCPremSchema.getFreeEndDate().compareTo(PaytoDate) >= 0) {
					tOperator = "FREE";
				} else {
					tOperator = "ZC";
					SumPaymoney = SumPaymoney + tLCPremSchema.getPrem();
				}
			} else {
				tOperator = "ZC";
				SumPaymoney = SumPaymoney + tLCPremSchema.getPrem();
			}
			/*******************************************************************
			 * 开始准备应收字表的数据 ，此处的保费lcprem中提取*********** *
			 * 
			 */
			tLJSPayPersonSet.add(PreperaLjspayperson(tLCPolSchema,
					tLCPremSchema, tOperator));
		}
		return tLJSPayPersonSet;
	}

	private LJSPayPersonSet PreperaLjspayperson(LCPolSchema tLCPolSchema,
			LCPremSchema tLCPremSchema, String tOperator) {
		LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
		String GrpPolNo = tLCPolSchema.getGrpPolNo();
		String AgentCode = tLCPolSchema.getAgentCode();
		String AgentGroup = tLCPolSchema.getAgentGroup();
		String AgentCom = tLCPolSchema.getAgentCom();
		String AgentType = tLCPolSchema.getAgentType();
		String ManageCom = tLCPolSchema.getManageCom();
		String ApproveCode = tLCPolSchema.getApproveCode();
		String ApproveDate = tLCPolSchema.getApproveDate();
		String ApproveTime = tLCPolSchema.getApproveTime();
		String RiskCode = tLCPolSchema.getRiskCode();

		/** @todo 8.1计算新的交至日期 */
		logger.debug("计算新的交费日期");
		logger.debug("原交费日期::::::::::::::" + tLCPolSchema.getPaytoDate());
		FDate tD = new FDate();
		Date newBaseDate = new Date();
		String CurrentPayToDate = tLCPolSchema.getPaytoDate();

		int PayInterval = 0;
		if (tLCPolSchema.getPayIntv() > 0) {
			PayInterval = tLCPolSchema.getPayIntv();
		} else {
			// 目前短期附加险都为一年期，应该用lcpol中的InsuYearFlag,InsuYear判断
			PayInterval = 12;
		}

		Date tLastPaytoDate = tD.getDate(CurrentPayToDate);

		Date tCurPayToDate = PubFun.calDate(tLastPaytoDate, PayInterval, "M",
				null);

		String strNewPayToDate = tD.getString(tCurPayToDate);
		logger.debug("现交至日期::::::::::::::" + strNewPayToDate);

		/** @todo 8.2交费日期=失效日期=原交至日期+2个月* */
		if (mPayDate == null || mPayDate.equals("")) {
			Date tPayDate = PubFun.calDate(mLastPaytoDate, 2, "M", null);
			mPayDate = tD.getString(tPayDate);
			logger.debug("交费截止日期::::::::::::::" + mPayDate);
		}

		// 正常交费
		if (tOperator.equals("ZC")) {
			LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
			tLJSPayPersonSchema.setGrpContNo(mGrpContNo);
			tLJSPayPersonSchema.setContNo(tLCPremSchema.getContNo());
			tLJSPayPersonSchema.setPolNo(tLCPremSchema.getPolNo());
			tLJSPayPersonSchema.setAppntNo(tLCPremSchema.getAppntNo());
			tLJSPayPersonSchema.setGetNoticeNo(mGetNoticeNo);
			tLJSPayPersonSchema.setPayCount(tLCPremSchema.getPayTimes() + 1);
			tLJSPayPersonSchema.setDutyCode(tLCPremSchema.getDutyCode());
			tLJSPayPersonSchema.setPayPlanCode(tLCPremSchema.getPayPlanCode());
			tLJSPayPersonSchema.setManageCom(ManageCom);
			tLJSPayPersonSchema.setPayIntv(tLCPolSchema.getPayIntv());
			tLJSPayPersonSchema.setAgentCode(AgentCode);
			tLJSPayPersonSchema.setGrpPolNo(GrpPolNo);
			tLJSPayPersonSchema.setAgentCom(AgentCom);
			tLJSPayPersonSchema.setAgentGroup(AgentGroup);
			tLJSPayPersonSchema.setAgentType(AgentType);
			tLJSPayPersonSchema.setPayAimClass("1");
			tLJSPayPersonSchema.setSumActuPayMoney(tLCPremSchema.getPrem());
			tLJSPayPersonSchema.setSumDuePayMoney(tLCPremSchema.getPrem());
			// 准备团险的险种金额
			setRiskMoney(RiskCode, "" + tLCPremSchema.getPrem());
			tLJSPayPersonSchema.setLastPayToDate(tLastPaytoDate);
			tLJSPayPersonSchema.setCurPayToDate(tCurPayToDate);
			tLJSPayPersonSchema.setPayType("ZC");
			tLJSPayPersonSchema.setPayDate(mPayDate);
			tLJSPayPersonSchema.setBankCode(mBankCode);
			tLJSPayPersonSchema.setBankAccNo(mBankAccNo);
			tLJSPayPersonSchema.setBankOnTheWayFlag("0");
			tLJSPayPersonSchema.setBankSuccFlag("0");
			tLJSPayPersonSchema.setApproveCode(ApproveCode);
			tLJSPayPersonSchema.setApproveDate(ApproveDate);
			tLJSPayPersonSchema.setApproveTime(ApproveTime);
			tLJSPayPersonSchema.setRiskCode(RiskCode);
			tLJSPayPersonSchema.setOperator(tGI.Operator);
			tLJSPayPersonSchema.setMakeDate(CurrentDate);
			tLJSPayPersonSchema.setMakeTime(CurrentTime);
			tLJSPayPersonSchema.setModifyDate(CurrentDate);
			tLJSPayPersonSchema.setModifyTime(CurrentTime);
			tLJSPayPersonSchema.setGrpPolNo(GrpPolNo);
			tLJSPayPersonSet.add(tLJSPayPersonSchema);
		}
		// 豁免
		else if (tOperator.equals("FREE")) {
			LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
			tLJSPayPersonSchema.setGrpContNo(mGrpContNo);
			tLJSPayPersonSchema.setContNo(tLCPremSchema.getContNo());
			tLJSPayPersonSchema.setPayIntv(tLCPolSchema.getPayIntv());
			tLJSPayPersonSchema.setPolNo(tLCPremSchema.getPolNo());
			tLJSPayPersonSchema.setAppntNo(tLCPremSchema.getAppntNo());
			tLJSPayPersonSchema.setGetNoticeNo(mGetNoticeNo);
			tLJSPayPersonSchema.setPayCount(tLCPremSchema.getPayTimes() + 1);
			tLJSPayPersonSchema.setDutyCode(tLCPremSchema.getDutyCode());
			tLJSPayPersonSchema.setPayPlanCode(tLCPremSchema.getPayPlanCode());
			tLJSPayPersonSchema.setManageCom(ManageCom);
			tLJSPayPersonSchema.setAgentCode(AgentCode);
			tLJSPayPersonSchema.setGrpPolNo(GrpPolNo);
			tLJSPayPersonSchema.setAgentCom(AgentCom);
			tLJSPayPersonSchema.setAgentGroup(AgentGroup);
			tLJSPayPersonSchema.setAgentType(AgentType);
			tLJSPayPersonSchema.setPayAimClass("1");
			tLJSPayPersonSchema.setSumActuPayMoney(tLCPremSchema.getPrem());
			tLJSPayPersonSchema.setSumDuePayMoney(tLCPremSchema.getPrem());
			tLJSPayPersonSchema.setLastPayToDate(mLastPaytoDate);
			tLJSPayPersonSchema.setCurPayToDate(mCurPayToDate);
			tLJSPayPersonSchema.setPayType("ZC");
			tLJSPayPersonSchema.setPayDate(mPayDate);
			tLJSPayPersonSchema.setBankCode(mBankCode);
			tLJSPayPersonSchema.setBankAccNo(mBankAccNo);
			tLJSPayPersonSchema.setBankOnTheWayFlag("0");
			tLJSPayPersonSchema.setBankSuccFlag("0");
			tLJSPayPersonSchema.setApproveCode(ApproveCode);
			tLJSPayPersonSchema.setApproveDate(ApproveDate);
			tLJSPayPersonSchema.setApproveTime(ApproveTime);
			tLJSPayPersonSchema.setRiskCode(RiskCode);
			// 准备团险的险种金额
			setRiskMoney(RiskCode, "" + tLCPremSchema.getPrem());
			tLJSPayPersonSchema.setOperator(tGI.Operator);
			tLJSPayPersonSchema.setMakeDate(CurrentDate);
			tLJSPayPersonSchema.setMakeTime(CurrentTime);
			tLJSPayPersonSchema.setModifyDate(CurrentDate);
			tLJSPayPersonSchema.setModifyTime(CurrentTime);
			tLJSPayPersonSet.add(tLJSPayPersonSchema);
			LJSPayPersonSchema nLJSPayPersonSchema = new LJSPayPersonSchema();
			nLJSPayPersonSchema.setGrpContNo(mGrpContNo);
			nLJSPayPersonSchema.setContNo(tLCPremSchema.getContNo());
			nLJSPayPersonSchema.setPolNo(tLCPremSchema.getPolNo());
			nLJSPayPersonSchema.setAppntNo(tLCPremSchema.getAppntNo());
			nLJSPayPersonSchema.setPayIntv(tLCPolSchema.getPayIntv());
			nLJSPayPersonSchema.setGetNoticeNo(mGetNoticeNo);
			nLJSPayPersonSchema.setPayCount(tLCPremSchema.getPayTimes() + 1);
			nLJSPayPersonSchema.setDutyCode(tLCPremSchema.getDutyCode());
			nLJSPayPersonSchema.setPayPlanCode(tLCPremSchema.getPayPlanCode());
			nLJSPayPersonSchema.setManageCom(ManageCom);
			nLJSPayPersonSchema.setAgentCode(AgentCode);
			nLJSPayPersonSchema.setGrpPolNo(GrpPolNo);
			nLJSPayPersonSchema.setAgentCom(AgentCom);
			nLJSPayPersonSchema.setAgentGroup(AgentGroup);
			nLJSPayPersonSchema.setAgentType(AgentType);
			nLJSPayPersonSchema.setPayAimClass("1");
			nLJSPayPersonSchema.setSumActuPayMoney((-1)
					* tLCPremSchema.getPrem());
			nLJSPayPersonSchema.setSumDuePayMoney((-1)
					* tLCPremSchema.getPrem());
			nLJSPayPersonSchema.setLastPayToDate(mLastPaytoDate);
			nLJSPayPersonSchema.setCurPayToDate(mCurPayToDate);
			nLJSPayPersonSchema.setPayType("HM");
			nLJSPayPersonSchema.setPayDate(mPayDate);
			// 准备团险的险种金额
			setRiskMoney(RiskCode, "" + tLCPremSchema.getPrem());
			nLJSPayPersonSchema.setBankCode(mBankCode);
			nLJSPayPersonSchema.setBankAccNo(mBankAccNo);
			nLJSPayPersonSchema.setBankOnTheWayFlag("0");
			nLJSPayPersonSchema.setBankSuccFlag("0");
			nLJSPayPersonSchema.setApproveCode(ApproveCode);
			nLJSPayPersonSchema.setApproveDate(ApproveDate);
			nLJSPayPersonSchema.setApproveTime(ApproveTime);
			nLJSPayPersonSchema.setRiskCode(RiskCode);
			nLJSPayPersonSchema.setOperator(tGI.Operator);
			nLJSPayPersonSchema.setMakeDate(CurrentDate);
			nLJSPayPersonSchema.setMakeTime(CurrentTime);
			nLJSPayPersonSchema.setModifyDate(CurrentDate);
			nLJSPayPersonSchema.setModifyTime(CurrentTime);
			tLJSPayPersonSet.add(nLJSPayPersonSchema);
		}
		return tLJSPayPersonSet;
	}

	private boolean CheckPolNoState(String tPolNo, String tDate) {
		String StateSql = "select * from LCContState where StateType in ('Available','Terminate') and State='1' and contno='"
				+ mGrpContNo
				+ "' and polno = '"
				+ tPolNo
				+ "' and enddate is null";

		logger.debug("校验险种是否中止、失效:::::::::::::::::::" + StateSql);

		LCContStateDB tLCContStateDB = new LCContStateDB();
		LCContStateSet tLCContStateSet = new LCContStateSet();
		tLCContStateSet = tLCContStateDB.executeQuery(StateSql);

		if (tLCContStateSet.size() > 0) {
			// 险种中止、失效
			return false;
		}

		return true;
	}

	// 准备渠道所用到的应收个人交费表
	// private LASPayPersonSet PreperaLAPay(LJSPayPersonSet tLJSPayPersonSet)
	// {
	// VData tVData = new VData();
	// LASPayPersonSet tLASPayPersonSet = new LASPayPersonSet();
	// for (int i = 1; i <= tLJSPayPersonSet.size(); i++)
	// {
	// LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
	// LASPayPersonSchema tLASPayPersonSchema = new LASPayPersonSchema();
	// tLJSPayPersonSchema = tLJSPayPersonSet.get(i);
	// tLASPayPersonSchema.setActuPayFlag("0");
	// tLASPayPersonSchema.setAgentCode(tLJSPayPersonSchema.getAgentCode());
	// tLASPayPersonSchema.setAgentGroup(tLJSPayPersonSchema.getAgentGroup());
	// tLASPayPersonSchema.setApproveCode(tLJSPayPersonSchema.
	// getApproveCode());
	// tLASPayPersonSchema.setApproveDate(tLJSPayPersonSchema.
	// getApproveDate());
	// tLASPayPersonSchema.setApproveTime(tLJSPayPersonSchema.
	// getApproveTime());
	// tLASPayPersonSchema.setContNo(tLJSPayPersonSchema.getContNo());
	// tLASPayPersonSchema.setCurPayToDate(tLJSPayPersonSchema.
	// getCurPayToDate());
	// tLASPayPersonSchema.setDutyCode(tLJSPayPersonSchema.getDutyCode());
	// tLASPayPersonSchema.setGetNoticeNo(tLJSPayPersonSchema.
	// getGetNoticeNo());
	// tLASPayPersonSchema.setGrpPolNo(tLJSPayPersonSchema.getGrpPolNo());
	// tLASPayPersonSchema.setGrpContNo(tLJSPayPersonSchema.getGrpContNo());
	// tLASPayPersonSchema.setLastPayToDate(tLJSPayPersonSchema.
	// getLastPayToDate());
	// tLASPayPersonSchema.setMakeDate(CurrentDate);
	// tLASPayPersonSchema.setMakeTime(CurrentTime);
	// tLASPayPersonSchema.setManageCom(tLJSPayPersonSchema.getManageCom());
	// tLASPayPersonSchema.setModifyTime(CurrentTime);
	// tLASPayPersonSchema.setModifyDate(CurrentDate);
	// tLASPayPersonSchema.setOperator(tGI.Operator);
	// tLASPayPersonSchema.setPayAimClass(tLJSPayPersonSchema.
	// getPayAimClass());
	// tLASPayPersonSchema.setPayCount(tLJSPayPersonSchema.getPayCount());
	// tLASPayPersonSchema.setPayDate(tLJSPayPersonSchema.getPayDate());
	// tLASPayPersonSchema.setPayIntv(tLJSPayPersonSchema.getPayIntv());
	// tLASPayPersonSchema.setPayPlanCode(tLJSPayPersonSchema.
	// getPayPlanCode());
	// tLASPayPersonSchema.setPayTypeFlag(tLJSPayPersonSchema.
	// getPayTypeFlag());
	// tLASPayPersonSchema.setPayType(tLJSPayPersonSchema.getPayType());
	// tLASPayPersonSchema.setPolNo(tLJSPayPersonSchema.getPolNo());
	// tLASPayPersonSchema.setRiskCode(tLJSPayPersonSchema.getRiskCode());
	// tLASPayPersonSchema.setSumActuPayMoney(tLJSPayPersonSchema.
	// getSumActuPayMoney());
	// tLASPayPersonSchema.setSumDuePayMoney(tLJSPayPersonSchema.
	// getSumDuePayMoney());
	//
	// String tContNo = tLJSPayPersonSchema.getContNo();
	// String tSql =
	// "select poltype from lacommisiondetail where grpcontno = '" +
	// tContNo + "'";
	// SSRS nSSRS = new SSRS();
	// ExeSQL tExeSQL = new ExeSQL();
	// nSSRS = tExeSQL.execSQL(tSql);
	// if (nSSRS.getMaxRow() > 0)
	// {
	// String tpoltype = nSSRS.GetText(1, 1);
	// tLASPayPersonSchema.setPolType(tpoltype);
	// }
	// tSql =
	// "select BranchSeries from LABranchGroup where AgentGroup = '" +
	// tLJSPayPersonSchema.getAgentGroup() + "'";
	// nSSRS = new SSRS();
	// tExeSQL = new ExeSQL();
	// nSSRS = tExeSQL.execSQL(tSql);
	// if (nSSRS.getMaxRow() > 0)
	// {
	// String tBranchSeries = nSSRS.GetText(1, 1);
	// tLASPayPersonSchema.setBranchSeries(tBranchSeries);
	// }
	// tLASPayPersonSet.add(tLASPayPersonSchema);
	// }
	//
	// return tLASPayPersonSet;
	// }

	private VData getPrintData(LJSPaySchema mLJSPaySchema, String prtSeqNo) {
		VData tVData = new VData();
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

		try {
			tLOPRTManagerSchema.setPrtSeq(prtSeqNo);
			tLOPRTManagerSchema.setOtherNo(mLCGrpContSchema.getGrpContNo());
			tLOPRTManagerSchema.setOtherNoType("1");

			tLOPRTManagerSchema.setCode("34");
			tLOPRTManagerSchema.setManageCom(mLCGrpContSchema.getManageCom());
			tLOPRTManagerSchema.setAgentCode(mLCGrpContSchema.getAgentCode());
			tLOPRTManagerSchema.setReqCom(mLCGrpContSchema.getManageCom());
			tLOPRTManagerSchema.setReqOperator(mLCGrpContSchema.getOperator());
			tLOPRTManagerSchema.setPrtType("0");
			tLOPRTManagerSchema.setStateFlag("0");
			tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
			tLOPRTManagerSchema.setOldPrtSeq(prtSeqNo);
			tLOPRTManagerSchema.setStandbyFlag2(mLJSPaySchema.getGetNoticeNo());
			tLOPRTManagerSchema
					.setStandbyFlag3(mLJSPaySchema.getStartPayDate());
			tLOPRTManagerSchema.setPatchFlag("0");
		} catch (Exception ex) {
			return null;
		}
		logger.debug(mLCGrpContSchema.getManageCom());
		String tLimit = PubFun.getNoLimit(mLCGrpContSchema.getManageCom());
		String tNo = PubFun1.CreateMaxNo("PAYNOTICENO", tLimit);
		// 产生打印通知书号
		String prtSeqNo1 = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
		// 发票（个人、银代）
		LOPRTManagerSchema dLOPRTManagerSchema = new LOPRTManagerSchema();
		try {
			dLOPRTManagerSchema.setPrtSeq(prtSeqNo1);
			dLOPRTManagerSchema.setOtherNo(mLCGrpContSchema.getGrpContNo());
			dLOPRTManagerSchema.setOtherNoType("1");
			dLOPRTManagerSchema.setCode("37");
			dLOPRTManagerSchema.setManageCom(mLCGrpContSchema.getManageCom());
			dLOPRTManagerSchema.setAgentCode(mLCGrpContSchema.getAgentCode());
			dLOPRTManagerSchema.setReqCom(mLCGrpContSchema.getManageCom());
			dLOPRTManagerSchema.setReqOperator(mLCGrpContSchema.getOperator());
			dLOPRTManagerSchema.setPrtType("0");
			dLOPRTManagerSchema.setStateFlag("0");
			dLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			dLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
			dLOPRTManagerSchema.setOldPrtSeq(prtSeqNo1);

			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSet tLCPolSet = new LCPolSet();
			String psql = "select * from lcpol where grpcontno='"
					+ mLCGrpContSchema.getGrpContNo()
					+ "' and mainpolno=polno ";
			tLCPolSet = tLCPolDB.executeQuery(psql);
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = tLCPolSet.get(1).getSchema(); // 目前一个合同只有一个主险
			String tpaytodate = tLCPolSchema.getPaytoDate();
			dLOPRTManagerSchema.setStandbyFlag3(tpaytodate);
			dLOPRTManagerSchema.setStandbyFlag4(String.valueOf(mLJSPaySchema
					.getSumDuePayMoney()));
			dLOPRTManagerSchema.setStandbyFlag1(mLJSPaySchema.getGetNoticeNo());
			dLOPRTManagerSchema.setPatchFlag("0");
		} catch (Exception ex) {
			return null;
		}

		LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();
		tLOPRTManagerSet.add(tLOPRTManagerSchema);
		tLOPRTManagerSet.add(dLOPRTManagerSchema);
		tVData.add(tLOPRTManagerSet);
		return tVData;
	}

	private LOPRTManagerSubSet PreperaLOPRTManagerSub(LCPolSet tLCPolSet,
			LJSPayPersonSet tLJSPayPersonSet) {
		LOPRTManagerSubSet tLOPRTManagerSubSet = new LOPRTManagerSubSet();

		/** @todo 生成打印子表数据打印* */
		for (int t = 1; t <= tLCPolSet.size(); t++) {
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = tLCPolSet.get(t);
			String tRiskCode = tLCPolSchema.getRiskCode();
			double RiskPrem = 0;
			// 取每个险种下的保费
			for (int i = 1; i <= tLJSPayPersonSet.size(); i++) {
				LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
				tLJSPayPersonSchema = tLJSPayPersonSet.get(i);
				if (tLJSPayPersonSchema.getRiskCode().equals(tRiskCode)) {
					RiskPrem = RiskPrem
							+ tLJSPayPersonSchema.getSumDuePayMoney();
				}
			}
			LOPRTManagerSubSchema tLOPRTManagerSubSchema = new LOPRTManagerSubSchema();
			tLOPRTManagerSubSchema.setPrtSeq(mPrtSeqNo);
			tLOPRTManagerSubSchema.setGetNoticeNo(mGetNoticeNo);
			tLOPRTManagerSubSchema.setOtherNo(mLCGrpContSchema.getGrpContNo());
			tLOPRTManagerSubSchema.setOtherNoType("00");
			tLOPRTManagerSubSchema.setRiskCode(tLCPolSchema.getRiskCode());
			tLOPRTManagerSubSchema.setDuePayMoney(RiskPrem);
			tLOPRTManagerSubSchema.setAppntName(mLCGrpContSchema.getGrpName());
			tLOPRTManagerSubSchema.setTypeFlag("1");
			tLOPRTManagerSubSet.add(tLOPRTManagerSubSchema);
		}
		return tLOPRTManagerSubSet;
	}

	public static void main(String[] args) {

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("GrpContNo", "240210000000039"); // autorenew
		// LCContSchema tLCContSchema = new LCContSchema();
		// LCContDB tLCContDB = new LCContDB();
		// tLCContDB.setContNo("000000040203");
		// tLCContDB.getInfo();
		// tLCContSchema = tLCContDB.getSchema();

		GrpFeePeriodicSingBL IndiDueFeePartBLF1 = new GrpFeePeriodicSingBL();
		GlobalInput tGI = new GlobalInput();
		tGI.ManageCom = "8621";
		tGI.Operator = "001";
		VData tv = new VData();
		// tv.add(tLCContSchema);
		tv.add(tTransferData);
		tv.add(tGI);

		logger.debug("准备好了数据");
		// tv.add(tTransferData);
		if (IndiDueFeePartBLF1.submitData(tv, "sing")) {
			logger.debug("个单批处理催收完成");
		} else {
			logger.debug("个单批处理催收信息提示：" /*
												 * +
												 * IndiDueFeePartBL1mErrors.getFirstError()
												 */);
		}
	}

	// 校验改险种是否是主险
	public boolean checkRisk(String riskcode) {
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String tSql = "select SubRiskFlag from lmriskapp where riskcode = '"
				+ riskcode + "'";
		tSSRS = tExeSQL.execSQL(tSql);
		if (tSSRS.GetText(1, 1).equalsIgnoreCase("M")) {
			return true;
		}
		if (tSSRS.GetText(1, 1).equalsIgnoreCase("S")) {
			return false;
		}
		return false;
	}

	// 输出当前操作
	public void printStr(String str) {
		logger.debug("++++++++++++++++++++++++++++++++++++++++++");
		logger.debug("            " + str);
		logger.debug("++++++++++++++++++++++++++++++++++++++++++");

	}

	private void jbInit() throws Exception {
	}

}
