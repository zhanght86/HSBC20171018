package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.claim.LLClaimUpdatePolNoBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJSPayGrpDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LJAPayGrpSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.schema.LJAPaySchema;
import com.sinosoft.lis.schema.LJSPayGrpSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJAPayGrpSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.lis.vschema.LJSPayGrpSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 续期核销
 * <p>
 * Description:
 * </p>
 * 团险定期产品核销程序
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author Gaoht
 * @version 1.0
 */
public class RnGrpComfirmBL {
private static Logger logger = Logger.getLogger(RnGrpComfirmBL.class);
	/** 数据操作字符串 */
	public CErrors mErrors = new CErrors();
	private String mOperate;
	private GlobalInput mGlobalInput = new GlobalInput();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private String mGetNoticeNo = "";
	// 暂收费表
	private LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();
	private LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet();
	// 应收个人交费表
	private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
	// 应收交费表总表
	private LJSPaySet mLJSPaySet = new LJSPaySet();
	// 应收交费表集体表
	private LJSPayGrpSet mLJSPayGrpSet = new LJSPayGrpSet();

	// 实收个人交费表
	private LJAPayPersonSet mLJAPayPersonSet = new LJAPayPersonSet();
	// 实收集体交费表
	private LJAPayGrpSet mLJAPayGrpSet = new LJAPayGrpSet();
	// 实收总表
	private LJAPaySet mLJAPaySet = new LJAPaySet();
	// 集体保单表
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema(); // 取值用
	private LCGrpPolSet mLCGrpPolSet = new LCGrpPolSet();

	private LCPolSet mLCPolSet = new LCPolSet();
	private LCContSet mLCContSet = new LCContSet();
	// 保费项表
	private LCPremSet mLCPremSet = new LCPremSet();
	// 保险责任表LCDuty
	private LCDutySet mLCDutySet = new LCDutySet();
	private LCGetSet mLCGetSet = new LCGetSet();
	// 本次核销总金额
	double mSumMoneyTempFee = 0;
	double mSunMoneySpay = 0;
	double mDif = 0;
	private boolean mAll = false;
	private VData mResult = new VData();
	private MMap mMap = new MMap();

	public RnGrpComfirmBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mOperate = cOperate;

		if (!getInputData(cInputData)) {
			return false;
		}

		if (!checkdata()) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		return true;
	}

	/*-------------------------------------------------------------------
	 * 从输入数据中得到所有对象
	 * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 *-------------------------------------------------------------------
	 */
	private boolean getInputData(VData mInputData) {
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		// 应收总表
		logger.debug("----------------------------IN getinputdata-----------------------------");

		mLCGrpContSchema.setSchema((LCGrpContSchema) mInputData
				.getObjectByObjectName("LCGrpContSchema", 0));
		if (mLCGrpContSchema == null) {
			// @@错误处理
			CError.buildErr(this, "传入参数为空");
			return false;
		}
		return true;
	}

	/*---------------------------------------------------------------
	 * 业务校验
	 * ---------------------------------------------------------------
	 */

	private boolean checkdata() {
		// 判断未到缴费对应日不能核销
		return true;
	}

	/*---------------------------------------------------------------
	 * 业务处理
	 * ---------------------------------------------------------------
	 */
	private boolean dealData() {
		// 查询应收
		if (!QuerySpayData()) {
			return false;
		}
		// 查询暂收费
		if (!QueryTempFee()) {
			return false;
		}
		// 校验金额
		if (!VerifyMoney()) {
			return false;
		}
		// 校验是否全部核销
		if (!GrpVarifyCheck()) {
			return false;
		}
		// 团单级状态核销
		if (!GrpContVarify(mAll)) {
			return false;
		}
		// 团单险种级状态核销
		if (!GrpPolVarify(mAll)) {
			return false;
		}
		// 个人保单级状态核销
		if (!ContVarify()) {
			return false;
		}
		// 个人险种\责任\保费\领取状态核销
		if (!LCPolDeal()) {
			return false;
		}
		// 核销应收
		if (!CreatLJAPay()) {
			return false;
		}
		if (!CreatLJAPayGrp()) {
			return false;
		}
		if (!CreatLJAPayPerson()) {
			return false;
		}
		// 核销暂收费
		if (!VarifyTempFee()) {
			return false;
		}
		return true;
	}

	/*------------------------------------------------------
	 * 数据返回
	 *------------------------------------------------------
	 */
	private boolean prepareOutputData() {
		mMap.put(mLJSPayPersonSet, "DELETE");
		mMap.put(mLJSPaySet, "DELETE");
		mMap.put(mLJSPayGrpSet, "DELETE");
		mMap.put(mLCGrpContSchema, "UPDATE");
		mMap.put(mLCGrpPolSet, "UPDATE");
		mMap.put(mLCContSet, "UPDATE");
		mMap.put(mLCPolSet, "UPDATE");
		mMap.put(mLCDutySet, "UPDATE");
		mMap.put(mLCPremSet, "UPDATE");
		mMap.put(mLCGetSet, "UPDATE");
		mMap.put(mLJAPayPersonSet, "INSERT");
		mMap.put(mLJAPayGrpSet, "INSERT");
		mMap.put(mLJAPaySet, "INSERT");
		mMap.put(mLJTempFeeSet, "UPDATE");
		mMap.put(mLJTempFeeClassSet, "UPDATE");
		mResult.add(mMap);
		return true;
	}

	/*--------------------------------------------------------
	 * 查询应收记录
	 *--------------------------------------------------------
	 */
	private boolean QuerySpayData() {
		// 得到应收数据
		LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
		String tSql = "select * from ljspayperson where GrpContNo = '"
				+ mLCGrpContSchema.getGrpContNo() + "' and paytype='ZC'";
		logger.debug("得到应收数据=====" + tSql);
		mLJSPayPersonSet = tLJSPayPersonDB.executeQuery(tSql);
		if (mLJSPayPersonSet.size() == 0) {
			CError.buildErr(this, "应收子表数据为空,请确认是否催收");
			return false;
		}

		String tOtherNo = mLJSPayPersonSet.get(1).getGrpContNo();
		LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPayDB.setOtherNo(tOtherNo);
		mLJSPaySet = tLJSPayDB.query();
		if (mLJSPaySet.size() == 0) {
			CError.buildErr(this, "应收总表数据为空,请确认是否催收");
			return false;
		}

		LJSPayGrpDB tLJSPayGrpDB = new LJSPayGrpDB();
		tLJSPayGrpDB.setGrpContNo(tOtherNo);
		mLJSPayGrpSet = tLJSPayGrpDB.query();
		if (mLJSPayGrpSet.size() == 0) {
			CError.buildErr(this, "应收集体表数据为空,请确认是否催收");
			return false;
		}

		for (int i = 1; i <= mLJSPaySet.size(); i++) {
			LJSPaySchema tLJSPaySchema = new LJSPaySchema();
			tLJSPaySchema = mLJSPaySet.get(i);
			mSunMoneySpay = mSunMoneySpay + tLJSPaySchema.getSumDuePayMoney();
		}

		return true;
	}

	/**
	 * ----------------------------------------------------- 查询暂收费记录
	 * -----------------------------------------------------
	 */
	private boolean QueryTempFee() {
		String TempFeeSql = "select * from ljtempfee where otherno='"
				+ mLCGrpContSchema.getGrpContNo()
				+ "' and othernotype='1' and tempfeetype='2' and enteraccdate is not null and confdate is null ";

		logger.debug("TTTTTTTTTTT========" + TempFeeSql);
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		mLJTempFeeSet = tLJTempFeeDB.executeQuery(TempFeeSql);

		// 续期催收金额
		double CSMoney = 0;
		for (int i = 1; i <= mLJTempFeeSet.size(); i++) {
			LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
			tLJTempFeeSchema = mLJTempFeeSet.get(i);
			CSMoney = CSMoney + tLJTempFeeSchema.getPayMoney();
		}

		// 保单帐户金额
		double tDif = mLCGrpContSchema.getDif();

		mSumMoneyTempFee = CSMoney + tDif;

		return true;
	}

	/**
	 * ----------------------------------- 应收与暂收的核对
	 * ------------------------------------
	 */
	private boolean VerifyMoney() {
		if (mSumMoneyTempFee == 0) {
			CError.buildErr(this, "未查到此保单下相关暂收费信息");
			return false;
		}
		if (mSumMoneyTempFee < mSunMoneySpay) {
			CError.buildErr(this, "暂收费" + mSumMoneyTempFee + "元 不足以支付续期保费"
					+ mSunMoneySpay + "");
			return false;
		}
		mDif = mSumMoneyTempFee - mSunMoneySpay;
		return true;
	}

	/**
	 * -------------------------------------------------- 校验团单下是否全部抽档
	 * ------------------------------------------------------
	 */
	private boolean GrpVarifyCheck() {
		String tSql = "select * from LCCont where grpcontno='"
				+ mLCGrpContSchema.getGrpContNo() + "' and appflag='1'";
		logger.debug("查询团体下个人::::::::::::" + tSql);
		LCContSet tLCContSet = new LCContSet();
		LCContDB tLCContDB = new LCContDB();
		tLCContSet = tLCContDB.executeQuery(tSql);

		LCContSet NeedLCContSet = new LCContSet();
		// 筛选个人须催缴保单
		for (int t = 1; t <= tLCContSet.size(); t++) {
			LCContSchema tLCContSchema = tLCContSet.get(t);
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("Contno", tLCContSchema.getContNo());
			tTransferData.setNameAndValue("ContType", "2");
			VData tv = new VData();
			tv.add(tTransferData);
			tv.add(mGlobalInput);
			IndiDueFeePartQuery tIndiDueFeePartQuery = new IndiDueFeePartQuery();
			if (!tIndiDueFeePartQuery.submitData(tv, "AllCont")) {
				continue;
			} else {
				LCContSet nLCContSet = new LCContSet();
				VData tVData = tIndiDueFeePartQuery.getResult();
				nLCContSet = (LCContSet) tVData.getObjectByObjectName(
						"LCContSet", 0);
				NeedLCContSet.add(nLCContSet);
			}
		}

		if (NeedLCContSet.size() == 0) {
			mAll = true;
		} else {
			// 定义团单下个人业务数据容器
			LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
			for (int t = 1; t <= NeedLCContSet.size(); t++) {
				LCContSchema tLCContSchema = NeedLCContSet.get(t);
				RnDealBL tRnDealBL = new RnDealBL();
				VData tVData = new VData();
				tVData.add(tLCContSchema);
				tVData.add(mGlobalInput);
				if (!tRnDealBL.submitData(tVData, "ContNo")) {
					continue;
				} else {
					tLJSPayPersonSet.add(tRnDealBL.getLJSPayPerson());
				}
			}
			if (tLJSPayPersonSet.size() == 0) {
				mAll = true;
			} else {
				mAll = false;
			}
		}
		return true;
	}

	/**
	 * ------------------------------------------ 团单整单级保单状态修改
	 * --------------------------------------------
	 */
	private boolean GrpContVarify(boolean cConfFlag) {
		mLCGrpContSchema.setSumPrem(mLCGrpContSchema.getSumPrem()
				+ mSunMoneySpay);
		mLCGrpContSchema.setDif(mDif);
		mLCGrpContSchema.setModifyDate(CurrentDate);
		mLCGrpContSchema.setModifyTime(CurrentTime);

		return true;
	}

	/**
	 * ---------------------------------------------- 团体险种级保单状态核销
	 * -----------------------------------------------
	 */
	private boolean GrpPolVarify(boolean cConfFlag) {
		for (int i = 1; i <= mLJSPayGrpSet.size(); i++) {
			LJSPayGrpSchema tLJSPayGrpSchema = new LJSPayGrpSchema();
			tLJSPayGrpSchema = mLJSPayGrpSet.get(i);
			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			tLCGrpPolDB.setGrpPolNo(tLJSPayGrpSchema.getGrpPolNo());
			if (!tLCGrpPolDB.getInfo()) {
				CError.buildErr(this, "未查到此团单下相关险种信息");
				return false;
			}
			LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
			tLCGrpPolSchema = tLCGrpPolDB.getSchema();
			tLCGrpPolSchema.setSumPrem(tLCGrpPolSchema.getSumPrem()
					+ tLJSPayGrpSchema.getSumDuePayMoney());
			tLCGrpPolSchema.setModifyDate(CurrentDate);
			tLCGrpPolSchema.setModifyTime(CurrentTime);
			if (cConfFlag == true) {
				tLCGrpPolSchema
						.setPaytoDate(tLJSPayGrpSchema.getCurPayToDate());
			}
			mLCGrpPolSet.add(tLCGrpPolSchema);
		}

		return true;
	}

	/**
	 * ------------------------------------------ 团体下个人保单状态核销
	 * --------------------------------------------
	 */
	private boolean ContVarify() {
		String tSql = "select contno,sum(SumDuePayMoney) from ljspayperson where grpcontno='"
				+ mLCGrpContSchema.getGrpContNo() + "' group by contno";
		SSRS nSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		nSSRS = tExeSQL.execSQL(tSql);
		for (int i = 1; i <= nSSRS.getMaxRow(); i++) {
			String tContNo = nSSRS.GetText(i, 1);
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(tContNo);
			if (!tLCContDB.getInfo()) {
				CError.buildErr(this, "未查到此团单下" + tContNo + "相关个人信息");
				return false;
			}

			double tMoney = Double.parseDouble(nSSRS.GetText(i, 2));

			LCContSchema tLCContSchema = new LCContSchema();
			tLCContSchema = tLCContDB.getSchema();
			tLCContSchema.setSumPrem(tLCContSchema.getSumPrem() + tMoney);
			tLCContSchema.setModifyDate(CurrentDate);
			tLCContSchema.setModifyTime(CurrentTime);
			tLCContSchema.setPaytoDate(mLJSPaySet.get(1).getStartPayDate());
			mLCContSet.add(tLCContSchema);
		}
		return true;
	}

	/**
	 * -------------------------------------- 个人险种及下级状态修改
	 * ----------------------------------------
	 */
	private boolean LCPolDeal() {
		String tSql = "select polno,sum(SumDuePayMoney) from ljspayperson where grpcontno='"
				+ mLCGrpContSchema.getGrpContNo() + "' group by polno";
		SSRS nSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		nSSRS = tExeSQL.execSQL(tSql);
		for (int i = 1; i <= nSSRS.getMaxRow(); i++) {
			String tPolNo = nSSRS.GetText(i, 1);
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(tPolNo);
			if (!tLCPolDB.getInfo()) {
				CError.buildErr(this, "未查到此团单下" + tPolNo + "相关个人险种信息");
				return false;
			}
			String tOperater = "";
			String tNewPolNo = "";
			String tOldPolNo = "";
			if (tLCPolDB.getAppFlag().equals("1")) {
				tOperater = "RN";
				tNewPolNo = tLCPolDB.getPolNo();
				tOldPolNo = tLCPolDB.getPolNo();
				PolVarify(tNewPolNo, tOldPolNo, tOperater);
				DutyVarify(tNewPolNo, tOldPolNo, tOperater);
				PremVarify(tNewPolNo, tOldPolNo, tOperater);
			} else if (tLCPolDB.getAppFlag().equals("9")) {
				tOperater = "NB";
				tNewPolNo = tLCPolDB.getPolNo();
				String nSql = "select * from lcpol where contno='"
						+ tLCPolDB.getContNo() + "' and riskcode='"
						+ tLCPolDB.getRiskCode() + "' and appflag='1'";
				LCPolDB ntLCPolDB = new LCPolDB();
				LCPolSet tLCPolSet = new LCPolSet();
				tLCPolSet = ntLCPolDB.executeQuery(nSql);
				if (tLCPolSet.size() == 0) {
					CError.buildErr(this, "未查到此团单下" + tPolNo + "相关个人险种有效信息");
					return false;
				}
				tOldPolNo = tLCPolSet.get(1).getPolNo();
				PolVarify(tNewPolNo, tOldPolNo, tOperater);
				DutyVarify(tNewPolNo, tOldPolNo, tOperater);
				PremVarify(tNewPolNo, tOldPolNo, tOperater);
				GetVarify(tNewPolNo, tOldPolNo, tOperater);
				ClaimTransferData(tNewPolNo, tOldPolNo);
			} else {
				tOperater = "SX";
			}
		}

		return true;
	}

	/**
	 * ------------------------------------------ 团体下个人险种状态核销
	 * --------------------------------------------
	 */
	private boolean PolVarify(String NewPolNo, String OldPolNo, String cOperater) {
		if (cOperater.equals("RN")) {
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(NewPolNo);
			tLCPolDB.getInfo();
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = tLCPolDB.getSchema();
			for (int i = 1; i <= mLJSPayPersonSet.size(); i++) {
				LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
				tLJSPayPersonSchema = mLJSPayPersonSet.get(i);
				if (tLJSPayPersonSchema.getPolNo().equals(
						tLCPolSchema.getPolNo())) {
					tLCPolSchema.setSumPrem(tLCPolSchema.getPrem()
							+ tLJSPayPersonSchema.getSumDuePayMoney());
					tLCPolSchema.setPaytoDate(tLJSPayPersonSchema
							.getCurPayToDate());
				}
			}
			tLCPolSchema.setModifyDate(CurrentDate);
			tLCPolSchema.setModifyTime(CurrentTime);
			mLCPolSet.add(tLCPolSchema);
		} else if (cOperater.equals("NB")) {
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(NewPolNo);
			tLCPolDB.getInfo();
			LCPolSchema NewLCPolSchema = new LCPolSchema();
			NewLCPolSchema = tLCPolDB.getSchema();
			NewLCPolSchema.setPolNo(OldPolNo);
			NewLCPolSchema.setAppFlag("1");
			NewLCPolSchema.setModifyDate(CurrentDate);
			NewLCPolSchema.setModifyTime(CurrentTime);
			mLCPolSet.add(NewLCPolSchema);

			tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(OldPolNo);
			tLCPolDB.getInfo();
			LCPolSchema OldLCPolSchema = new LCPolSchema();
			OldLCPolSchema = tLCPolDB.getSchema();
			OldLCPolSchema.setPolNo(NewPolNo);
			OldLCPolSchema.setAppFlag("4");
			OldLCPolSchema.setModifyDate(CurrentDate);
			OldLCPolSchema.setModifyTime(CurrentTime);
			mLCPolSet.add(OldLCPolSchema);
		} else {
			CError.buildErr(this, "险种错误的处理类型");
			return false;
		}
		return true;
	}

	/**
	 * ------------------------------------------ 团体下个人责任状态核销
	 * --------------------------------------------
	 */
	private boolean DutyVarify(String NewPolNo, String OldPolNo,
			String cOperater) {
		if (cOperater.equals("RN")) {
			LCDutyDB tLCDutyDB = new LCDutyDB();
			tLCDutyDB.setPolNo(NewPolNo);
			LCDutySet tLCDutySet = new LCDutySet();
			tLCDutySet = tLCDutyDB.query();
			if (tLCDutySet.size() == 0) {
				CError.buildErr(this, "RN无责任信息");
				return false;
			}
			for (int i = 1; i <= tLCDutySet.size(); i++) {
				LCDutySchema tLCDutySchema = new LCDutySchema();
				tLCDutySchema = tLCDutySet.get(i);
				for (int t = 1; t <= mLJSPayPersonSet.size(); t++) {
					LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
					tLJSPayPersonSchema = mLJSPayPersonSet.get(t);
					if (tLCDutySchema.getPolNo().equals(
							tLJSPayPersonSchema.getPolNo())
							&& tLCDutySchema.getDutyCode().equals(
									tLJSPayPersonSchema.getDutyCode())) {
						tLCDutySchema.setSumPrem(tLCDutySchema.getSumPrem()
								+ tLJSPayPersonSchema.getSumDuePayMoney());
						tLCDutySchema.setPaytoDate(tLJSPayPersonSchema
								.getCurPayToDate());
						tLCDutySchema.setModifyDate(CurrentDate);
						tLCDutySchema.setModifyTime(CurrentTime);
					}
				}
				mLCDutySet.add(tLCDutySchema);
			}
		} else if (cOperater.equals("NB")) {
			LCDutyDB tLCDutyDB = new LCDutyDB();
			tLCDutyDB.setPolNo(NewPolNo);
			LCDutySet NewLCDutySet = new LCDutySet();
			NewLCDutySet = tLCDutyDB.query();
			if (NewLCDutySet.size() == 0) {
				CError.buildErr(this, "NB_NEW无责任信息");
				return false;
			}
			for (int i = 1; i <= NewLCDutySet.size(); i++) {
				LCDutySchema tLCDutySchema = new LCDutySchema();
				tLCDutySchema = NewLCDutySet.get(i);
				tLCDutySchema.setPolNo(OldPolNo);
				tLCDutySchema.setModifyDate(CurrentDate);
				tLCDutySchema.setModifyTime(CurrentTime);
				mLCDutySet.add(tLCDutySchema);
			}

			tLCDutyDB = new LCDutyDB();
			tLCDutyDB.setPolNo(OldPolNo);
			LCDutySet OldLCDutySet = new LCDutySet();
			OldLCDutySet = tLCDutyDB.query();
			if (OldLCDutySet.size() == 0) {
				CError.buildErr(this, "NB_OLD无责任信息");
				return false;
			}
			for (int i = 1; i <= OldLCDutySet.size(); i++) {
				LCDutySchema tLCDutySchema = new LCDutySchema();
				tLCDutySchema = OldLCDutySet.get(i);
				tLCDutySchema.setPolNo(NewPolNo);
				tLCDutySchema.setModifyDate(CurrentDate);
				tLCDutySchema.setModifyTime(CurrentTime);
				mLCDutySet.add(tLCDutySchema);
			}
		} else {
			CError.buildErr(this, "责任错误的处理类型");
			return false;
		}
		return true;
	}

	/**
	 * ------------------------------------------ 团体下个人保费状态核销
	 * --------------------------------------------
	 */
	private boolean PremVarify(String NewPolNo, String OldPolNo,
			String cOperater) {
		if (cOperater.equals("RN")) {
			LCPremDB tLCPremDB = new LCPremDB();
			tLCPremDB.setPolNo(NewPolNo);
			LCPremSet tLCPremSet = new LCPremSet();
			tLCPremSet = tLCPremDB.query();
			for (int i = 1; i <= tLCPremSet.size(); i++) {
				LCPremSchema tLCPremSchema = new LCPremSchema();
				tLCPremSchema = tLCPremSet.get(i);
				for (int t = 1; t <= mLJSPayPersonSet.size(); t++) {
					LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
					tLJSPayPersonSchema = mLJSPayPersonSet.get(t);
					if (tLCPremSchema.getPolNo().equals(
							tLJSPayPersonSchema.getPolNo())
							&& tLCPremSchema.getDutyCode().equals(
									tLJSPayPersonSchema.getDutyCode())
							&& tLCPremSchema.getPayPlanCode().equals(
									tLJSPayPersonSchema.getPayPlanCode())) {
						tLCPremSchema.setSumPrem(tLCPremSchema.getSumPrem()
								+ tLJSPayPersonSchema.getSumDuePayMoney());
						tLCPremSchema
								.setPayTimes(tLCPremSchema.getPayTimes() + 1);
						tLCPremSchema.setPaytoDate(tLJSPayPersonSchema
								.getCurPayToDate());
						tLCPremSchema.setModifyDate(CurrentDate);
						tLCPremSchema.setModifyTime(CurrentTime);
					}
				}
				mLCPremSet.add(tLCPremSchema);
			}
		} else if (cOperater.equals("NB")) {
			LCPremDB tLCPremDB = new LCPremDB();
			tLCPremDB.setPolNo(NewPolNo);
			LCPremSet NewLCPremSet = new LCPremSet();
			NewLCPremSet = tLCPremDB.query();

			if (NewLCPremSet.size() == 0) {
				CError.buildErr(this, "New未找到保费项");
				return false;
			}
			for (int i = 1; i <= NewLCPremSet.size(); i++) {
				LCPremSchema tLCPremSchema = new LCPremSchema();
				tLCPremSchema = NewLCPremSet.get(i);
				tLCPremSchema.setPolNo(OldPolNo);
				tLCPremSchema.setModifyDate(CurrentDate);
				tLCPremSchema.setModifyTime(CurrentTime);
				mLCPremSet.add(tLCPremSchema);
			}

			tLCPremDB = new LCPremDB();
			tLCPremDB.setPolNo(OldPolNo);
			LCPremSet OldLCPremSet = new LCPremSet();
			OldLCPremSet = tLCPremDB.query();
			if (OldLCPremSet.size() == 0) {
				CError.buildErr(this, "New未找到保费项");
				return false;
			}
			for (int i = 1; i <= OldLCPremSet.size(); i++) {
				LCPremSchema tLCPremSchema = new LCPremSchema();
				tLCPremSchema = OldLCPremSet.get(i);
				tLCPremSchema.setPolNo(NewPolNo);
				tLCPremSchema.setModifyDate(CurrentDate);
				tLCPremSchema.setModifyTime(CurrentTime);
				mLCPremSet.add(tLCPremSchema);
			}
		} else {
			CError.buildErr(this, "保费错误的处理类型");
			return false;
		}
		return true;
	}

	/**
	 * ------------------------------------------ 团体下个人领取状态核销
	 * --------------------------------------------
	 */
	private boolean GetVarify(String NewPolNo, String OldPolNo, String cOperater) {
		LCGetDB tLCGetDB = new LCGetDB();
		tLCGetDB.setPolNo(NewPolNo);
		LCGetSet tLCGetSet = new LCGetSet();
		tLCGetSet = tLCGetDB.query();

		if (tLCGetSet.size() == 0) {
			CError.buildErr(this, "New未找到领取项");
			return false;
		}

		for (int i = 1; i <= tLCGetSet.size(); i++) {
			LCGetSchema tLCGetSchema = new LCGetSchema();
			tLCGetSchema = tLCGetSet.get(i);
			tLCGetSchema.setPolNo(OldPolNo);
			tLCGetSchema.setModifyDate(CurrentDate);
			tLCGetSchema.setModifyTime(CurrentTime);
			mLCGetSet.add(tLCGetSchema);
		}

		tLCGetDB = new LCGetDB();
		tLCGetDB.setPolNo(OldPolNo);
		tLCGetSet = new LCGetSet();
		tLCGetSet = tLCGetDB.query();
		if (tLCGetSet.size() == 0) {
			CError.buildErr(this, "OLD未找到领取项");
			return false;
		}
		for (int i = 1; i <= tLCGetSet.size(); i++) {
			LCGetSchema tLCGetSchema = new LCGetSchema();
			tLCGetSchema = tLCGetSet.get(i);
			tLCGetSchema.setPolNo(NewPolNo);
			tLCGetSchema.setModifyDate(CurrentDate);
			tLCGetSchema.setModifyTime(CurrentTime);
			mLCGetSet.add(tLCGetSchema);
		}

		return true;
	}

	/**
	 * 理赔换号
	 */
	private boolean ClaimTransferData(String NewPolNo, String OldPolNo) {
		logger.debug("--------------------自动续保开始更新理赔表---------------");
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("NewPolNo", NewPolNo);
		tTransferData.setNameAndValue("OldPolNo", OldPolNo);
		VData tVData = new VData();
		tVData.addElement(tTransferData);
		LLClaimUpdatePolNoBL tLLClaimUpdatePolNoBL = new LLClaimUpdatePolNoBL();
		if (!tLLClaimUpdatePolNoBL.submitData(tVData, "UpDate")) {
			CError.buildErr(this, "更新理赔表失败");
			return false;
		} else {
			mMap.add(tLLClaimUpdatePolNoBL.getMMap());
		}
		return true;
	}

	/**
	 * ------------------------------------------ 应收总表转实收
	 * --------------------------------------------
	 */
	private boolean CreatLJAPay() {
		for (int i = 1; i <= mLJSPaySet.size(); i++) {
			LJSPaySchema tLJSPaySchema = new LJSPaySchema();
			LJAPaySchema tLJAPaySchema = new LJAPaySchema();
			tLJSPaySchema = mLJSPaySet.get(i);
			Reflections tReflections = new Reflections();
			tReflections.transFields(tLJAPaySchema, tLJSPaySchema);
			mLJAPaySet.add(tLJAPaySchema);
		}

		if (mLJAPaySet.size() == 0) {
			CError.buildErr(this, "应收数据核销失败");
			return false;
		}

		for (int i = 1; i <= mLJAPaySet.size(); i++) {
			mLJAPaySet.get(i).setPayNo(mLJAPaySet.get(i).getGetNoticeNo());
			mLJAPaySet.get(i).setIncomeNo(mLJAPaySet.get(i).getGetNoticeNo());
			mLJAPaySet.get(i).setIncomeType("1");
			mLJAPaySet.get(i).setMakeDate(CurrentDate);
			mLJAPaySet.get(i).setModifyDate(CurrentDate);
			mLJAPaySet.get(i).setModifyTime(CurrentTime);
			mLJAPaySet.get(i).setModifyTime(CurrentTime);
		}

		return true;
	}

	/**
	 * ------------------------------------------ 应收集体表转实收
	 * --------------------------------------------
	 */
	private boolean CreatLJAPayGrp() {
		for (int i = 1; i <= mLJSPayGrpSet.size(); i++) {
			LJSPayGrpSchema tLJSPayGrpSchema = new LJSPayGrpSchema();
			LJAPayGrpSchema tLJAPayGrpSchema = new LJAPayGrpSchema();
			tLJSPayGrpSchema = mLJSPayGrpSet.get(i);
			Reflections tReflections = new Reflections();
			tReflections.transFields(tLJAPayGrpSchema, tLJSPayGrpSchema);
			mLJAPayGrpSet.add(tLJAPayGrpSchema);
		}

		if (mLJAPayGrpSet.size() == 0) {
			CError.buildErr(this, "应收数据核销失败");
			return false;
		}

		for (int i = 1; i <= mLJAPayGrpSet.size(); i++) {
			LJAPayGrpSchema tLJAPayGrpSchema = new LJAPayGrpSchema();
			tLJAPayGrpSchema = mLJAPayGrpSet.get(i);
			tLJAPayGrpSchema.setPayNo(mLJSPaySet.get(1).getGetNoticeNo());
			tLJAPayGrpSchema.setPayDate(mLJTempFeeSet.get(1).getPayDate());
			tLJAPayGrpSchema.setEnterAccDate(mLJTempFeeSet.get(1)
					.getEnterAccDate());
			tLJAPayGrpSchema.setConfDate(CurrentDate); // 确认日期
			tLJAPayGrpSchema.setMakeDate(CurrentDate);
			tLJAPayGrpSchema.setMakeTime(CurrentTime);
			tLJAPayGrpSchema.setModifyDate(CurrentDate);
			tLJAPayGrpSchema.setModifyTime(CurrentTime);
			mLJAPayGrpSet.set(i, tLJAPayGrpSchema);
		}

		return true;
	}

	/**
	 * ------------------------------------------ 应收个人表转实收
	 * --------------------------------------------
	 */
	private boolean CreatLJAPayPerson() {
		for (int i = 1; i <= mLJSPayPersonSet.size(); i++) {
			LJAPayPersonSchema tLJAPayPersonSchema = new LJAPayPersonSchema();
			LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
			tLJSPayPersonSchema = mLJSPayPersonSet.get(i);
			Reflections tReflections = new Reflections();
			tReflections.transFields(tLJAPayPersonSchema, tLJSPayPersonSchema);
			mLJAPayPersonSet.add(tLJAPayPersonSchema);
		}

		if (mLJAPayPersonSet.size() == 0) {
			CError.buildErr(this, "应收数据核销失败");
			return false;
		}

		for (int i = 1; i <= mLJAPayPersonSet.size(); i++) {
			mLJAPayPersonSet.get(i)
					.setPayNo(mLJSPaySet.get(1).getGetNoticeNo());
			mLJAPayPersonSet.get(i).setEnterAccDate(
					mLJTempFeeSet.get(1).getEnterAccDate());
			mLJAPayPersonSet.get(i).setConfDate(CurrentDate);
			mLJAPayPersonSet.get(i).setMakeDate(CurrentDate);
			mLJAPayPersonSet.get(i).setModifyDate(CurrentDate);
			mLJAPayPersonSet.get(i).setModifyTime(CurrentTime);
			mLJAPayPersonSet.get(i).setModifyTime(CurrentTime);
		}

		return true;
	}

	/**
	 * ------------------------------------------ 暂收费核销
	 * --------------------------------------------
	 */
	private boolean VarifyTempFee() {
		for (int i = 1; i <= mLJTempFeeSet.size(); i++) {
			LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
			tLJTempFeeSchema = mLJTempFeeSet.get(i);
			tLJTempFeeSchema.setConfDate(CurrentDate);
			tLJTempFeeSchema.setConfFlag("1");
			tLJTempFeeSchema.setModifyDate(CurrentDate);
			tLJTempFeeSchema.setModifyTime(CurrentTime);
			mLJTempFeeSet.set(i, tLJTempFeeSchema);

			LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
			tLJTempFeeClassDB.setTempFeeNo(tLJTempFeeSchema.getTempFeeNo());
			LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
			tLJTempFeeClassSet = tLJTempFeeClassDB.query();

			for (int t = 1; t <= tLJTempFeeClassSet.size(); t++) {
				LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
				tLJTempFeeClassSchema = tLJTempFeeClassSet.get(t);
				tLJTempFeeClassSchema.setConfDate(CurrentDate);
				tLJTempFeeClassSchema.setConfFlag("1");
				tLJTempFeeClassSchema.setModifyDate(CurrentDate);
				tLJTempFeeClassSchema.setModifyTime(CurrentTime);
				mLJTempFeeClassSet.add(tLJTempFeeClassSchema);
			}
		}

		return true;
	}

	// 返回结果集
	public MMap GetMMap() {
		return mMap;
	}

	/**
	 * 调试函数
	 */
	public static void main(String[] args) {
		RnGrpComfirmBL rngrpcomfirmbl = new RnGrpComfirmBL();
		String tGrpContNo = "880000005805";
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(tGrpContNo);
		tLCGrpContDB.getInfo();
		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		tLCGrpContSchema = tLCGrpContDB.getSchema();
		TransferData tTransferData = new TransferData();
		GlobalInput tGI = new GlobalInput();
		tGI.ManageCom = "86";
		tGI.Operator = "001";

		VData tVData = new VData();
		tVData.addElement(tGI);
		tVData.add(tLCGrpContSchema);
		tTransferData.setNameAndValue("GrpContNo", tGrpContNo);
		tVData.addElement(tTransferData);
		if (!rngrpcomfirmbl.submitData(tVData, "")) {
			logger.debug(rngrpcomfirmbl.mErrors.getFirstError());
		}
	}
}
