

package com.sinosoft.lis.reinsure.faculreinsure;

import com.sinosoft.utility.VData;
import com.sinosoft.utility.TransferData;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.PubFun;

public class RIPolRecodeChng {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private String mOpeFlag = "";
	private String mOpeData = "";
	private String mDutyCode = "";
	private String mPolNo = "";
	private String mRiskCode = "";
	private String mEdorNo = "";
	private String mEdorType = "";
	private String mCaseNo = "";
	private String mStandPay = "";
	private String mRealPay = "";
	private String mInsuredNo = "";
	private String mInsuredName = "";
	private LCPolSchema mLCPolSchema;
	private LPPolSchema mLPPolSchema;
	private LCDutySchema mLCDutySchema;
	private LPDutySchema mLPDutySchema;
	private LCGrpContSchema mLCGRPContSchema;
	private LLClaimDetailSchema mLLClaimDetailSchema;
	private RIPolRecordSchema mRIPolRecordSchema = new RIPolRecordSchema();
	private VData mInputData = new VData();
	private TransferData mTransferData = new TransferData();

	public RIPolRecodeChng() {
	}

	public boolean submitData(VData cInputData, String cOpeFlag) {
		mOpeFlag = cOpeFlag;
		if (!getInputData(cInputData))
			return false;

		if (!dealData()) {
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData) {
		this.mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		this.mLCPolSchema = (LCPolSchema) cInputData.getObjectByObjectName(
				"LCPolSchema", 0);
		this.mLCDutySchema = (LCDutySchema) cInputData.getObjectByObjectName(
				"LCDutySchema", 0);
		this.mLPPolSchema = (LPPolSchema) cInputData.getObjectByObjectName(
				"LPPolSchema", 0);
		this.mLPDutySchema = (LPDutySchema) cInputData.getObjectByObjectName(
				"LPDutySchema", 0);
		this.mLCGRPContSchema = (LCGrpContSchema) cInputData
				.getObjectByObjectName("LCGRPContSchema", 0);
		this.mLLClaimDetailSchema = (LLClaimDetailSchema) cInputData
				.getObjectByObjectName("LLClaimDetailSchema", 0);
		return true;
	}

	private boolean dealData() {
		if (mOpeFlag.equals("11")) {
			mOpeData = (String) mTransferData.getValueByName("OpeData");
			mDutyCode = (String) mTransferData.getValueByName("DutyCode");
			mPolNo = (String) mTransferData.getValueByName("PolNo");
		}
		if (mOpeFlag.equals("13")) {
			mDutyCode = (String) mTransferData.getValueByName("DutyCode");
			mPolNo = (String) mTransferData.getValueByName("PolNo");
			mEdorNo = (String) mTransferData.getValueByName("EdorNo");
			mEdorType = (String) mTransferData.getValueByName("EdorType");
		}
		if (mOpeFlag.equals("14")) {
			mRiskCode = (String) mTransferData.getValueByName("RiskCode");
			mCaseNo = (String) mTransferData.getValueByName("CaseNo");
			mStandPay = (String) mTransferData.getValueByName("StandPay");
			mRealPay = (String) mTransferData.getValueByName("RealPay");
			mPolNo = (String) mTransferData.getValueByName("PolNo");
			mInsuredNo = (String) mTransferData.getValueByName("InsuredNo");
			mInsuredName = (String) mTransferData.getValueByName("InsuredName");

		}
		if (!getRIPolRecord()) {
			return false;
		}
		return true;
	}

	private boolean getRIPolRecord() {
		if (mOpeFlag.equals("11")) {
			if (!getNewPolicy()) {
				return false;
			}
		}
		if (mOpeFlag.equals("13")) {
			if (!getEdorPolicy()) {
				return false;
			}
		}
		if (mOpeFlag.equals("14")) {
			if (!getClaimPolicy()) {
				return false;
			}
		}
		return true;
	}

	// 新单
	private boolean getNewPolicy() {
		LCPolSchema tLCPolSchema = new LCPolSchema();
		LCDutySchema tLCDutySchema = new LCDutySchema();
		if (mLCPolSchema != null) {
			tLCPolSchema.setSchema(mLCPolSchema);
		} else {
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(mPolNo);
			if (!tLCPolDB.getInfo()) {
				buildError("dealData", "没有相关保单信息,PolNo" + mPolNo);
				return false;
			}
			tLCPolSchema.setSchema(tLCPolDB.getSchema());
		}
		if (mLCDutySchema != null) {
			tLCDutySchema.setSchema(mLCDutySchema);
		} else {
			LCDutyDB tLCDutyDB = new LCDutyDB();
			tLCDutyDB.setPolNo(tLCPolSchema.getPolNo());
			tLCDutyDB.setDutyCode(mDutyCode);
			if (!tLCDutyDB.getInfo()) {
				buildError("dealData",
						"没有相关保单责任信息,PolNo:" + tLCPolSchema.getPolNo()
								+ " DutyCode: " + mDutyCode);
				return false;
			}
			tLCDutySchema.setSchema(tLCDutyDB.getSchema());
		}
		mRIPolRecordSchema.setEventNo("");
		mRIPolRecordSchema.setEventType("01");
		mRIPolRecordSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
		mRIPolRecordSchema.setPolNo(tLCPolSchema.getPolNo());
		mRIPolRecordSchema.setDutyCode(tLCDutySchema.getDutyCode());
		mRIPolRecordSchema.setRiskCode(tLCPolSchema.getRiskCode());
		mRIPolRecordSchema.setContNo(tLCPolSchema.getContNo());
		mRIPolRecordSchema.setInsuredYear(0);
		mRIPolRecordSchema.setDataFlag("02");
		mRIPolRecordSchema.setPayNo("");
		mRIPolRecordSchema.setPayCount(0);
		mRIPolRecordSchema.setPayMoney(0);
		mRIPolRecordSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
		mRIPolRecordSchema.setStandbyString1("");
		mRIPolRecordSchema.setOccupationType(tLCPolSchema.getOccupationType());
		setRIPolRecordInfo(tLCPolSchema, tLCDutySchema);

		return true;
	}

	// 保全
	private boolean getEdorPolicy() {
		LPPolSchema tLPPolSchema = new LPPolSchema();
		LPDutySchema tLPDutySchema = new LPDutySchema();
		if (mLPPolSchema != null) {
			tLPPolSchema.setSchema(mLPPolSchema);
		} else {
			LPPolDB tLPPolDB = new LPPolDB();
			tLPPolDB.setPolNo(mPolNo);
			tLPPolDB.setEdorNo(mEdorNo);
			tLPPolDB.setEdorType(mEdorNo);
			if (!tLPPolDB.getInfo()) {
				buildError("dealData", "没有相关保单信息,PolNo" + mPolNo);
				return false;
			}
			tLPPolSchema.setSchema(tLPPolDB.getSchema());
		}
		if (mLPDutySchema != null) {
			tLPDutySchema.setSchema(mLPDutySchema);
		} else {
			LPDutyDB tLPDutyDB = new LPDutyDB();
			tLPDutyDB.setPolNo(tLPDutySchema.getPolNo());
			tLPDutyDB.setDutyCode(tLPDutySchema.getDutyCode());
			tLPDutyDB.setEdorNo(tLPDutySchema.getEdorNo());
			tLPDutyDB.setEdorType(tLPDutySchema.getEdorType());
			if (!tLPDutyDB.getInfo()) {
				buildError("dealData",
						"没有相关保单责任信息,PolNo:" + tLPDutySchema.getPolNo()
								+ " DutyCode: " + tLPDutySchema.getDutyCode());
				return false;
			}
			tLPDutySchema.setSchema(tLPDutyDB.getSchema());
		}
		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLCDutyDB.setPolNo(tLPDutySchema.getPolNo());
		tLCDutyDB.setDutyCode(tLPDutySchema.getDutyCode());
		if (!tLCDutyDB.getInfo()) {
			buildError("dealData",
					"没有相关保单责任信息,PolNo:" + tLPDutySchema.getPolNo()
							+ " DutyCode: " + tLPDutySchema.getDutyCode());
			return false;
		}
		LCDutySchema tLCDutySchema = new LCDutySchema();
		tLCDutySchema.setSchema(tLCDutyDB.getSchema());

		mRIPolRecordSchema.setEventNo("");
		mRIPolRecordSchema.setEventType("03");
		mRIPolRecordSchema.setEdorNo(tLPPolSchema.getEdorNo());
		mRIPolRecordSchema.setEventType(tLPPolSchema.getEdorType());
		mRIPolRecordSchema.setGrpPolNo(tLPPolSchema.getGrpPolNo());
		mRIPolRecordSchema.setPolNo(tLPPolSchema.getPolNo());
		mRIPolRecordSchema.setDutyCode(tLPDutySchema.getDutyCode());
		mRIPolRecordSchema.setRiskCode(tLPPolSchema.getRiskCode());
		mRIPolRecordSchema.setContNo(tLPPolSchema.getContNo());
		mRIPolRecordSchema.setInsuredYear(0);
		mRIPolRecordSchema.setDataFlag("02");
		mRIPolRecordSchema.setPayNo("");
		mRIPolRecordSchema.setPayCount(0);
		mRIPolRecordSchema.setPayMoney(0);
		mRIPolRecordSchema.setGrpContNo(tLPPolSchema.getGrpContNo());
		mRIPolRecordSchema.setOccupationType(tLPPolSchema.getOccupationType());
		mRIPolRecordSchema.setAmnt(tLPDutySchema.getAmnt()
				- tLCDutySchema.getAmnt()); // 保额增量
		setRIPolRecordInfo(tLPPolSchema, tLPDutySchema);
		return true;

	}

	// 理赔
	private boolean getClaimPolicy() {
		mRIPolRecordSchema.setEventType("04");
		mRIPolRecordSchema.setRiskCode(mRiskCode);
		mRIPolRecordSchema.setDutyCode(mDutyCode);
		mRIPolRecordSchema.setDataFlag("02");
		mRIPolRecordSchema.setPolNo(mPolNo);
		mRIPolRecordSchema.setInsuredNo(mInsuredNo);
		mRIPolRecordSchema.setInsuredName(mInsuredName);
		mRIPolRecordSchema.setClmNo(mCaseNo); // 赔案号
		mRIPolRecordSchema.setStandGetMoney(mStandPay);
		mRIPolRecordSchema.setClmGetMoney(mRealPay);

		return true;
	}

	private void setRIPolRecordInfo(LCPolSchema tLCPolSchema,
			LCDutySchema tLCDutySchema) {
		mRIPolRecordSchema.setYears(tLCDutySchema.getYears());
		mRIPolRecordSchema.setCValiDate(tLCPolSchema.getCValiDate());
		mRIPolRecordSchema.setEndDate(tLCDutySchema.getEndDate());
		mRIPolRecordSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
		System.out.println(" 被保人号： " + tLCPolSchema.getInsuredNo());

		mRIPolRecordSchema.setInsuredName(tLCPolSchema.getInsuredName());
		mRIPolRecordSchema.setInsuredSex(tLCPolSchema.getInsuredSex());
		mRIPolRecordSchema.setInsuredAge(tLCPolSchema.getInsuredAppAge());
		mRIPolRecordSchema.setStandPrem(tLCDutySchema.getStandPrem());
		mRIPolRecordSchema.setPrem(tLCDutySchema.getPrem());
		mRIPolRecordSchema.setRiskAmnt(tLCDutySchema.getRiskAmnt());
		mRIPolRecordSchema.setAmnt(tLCDutySchema.getAmnt());
		mRIPolRecordSchema.setMult(tLCDutySchema.getMult());
		mRIPolRecordSchema.setPayIntv(tLCDutySchema.getPayIntv());
		mRIPolRecordSchema.setPayYears(tLCDutySchema.getPayYears());
		mRIPolRecordSchema.setPayEndYearFlag(tLCDutySchema.getPayEndYearFlag());
		mRIPolRecordSchema.setPayEndYear(tLCDutySchema.getPayEndYear());
		mRIPolRecordSchema.setGetYearFlag(tLCDutySchema.getGetYearFlag());
		mRIPolRecordSchema.setGetYear(tLCDutySchema.getGetYear());
		mRIPolRecordSchema.setInsuYearFlag(tLCDutySchema.getInsuYearFlag());
		mRIPolRecordSchema.setInsuYear(tLCDutySchema.getInsuYear());
		mRIPolRecordSchema.setAcciYearFlag(tLCDutySchema.getAcciYearFlag());
		mRIPolRecordSchema.setAcciYear(tLCDutySchema.getAcciYear());
		mRIPolRecordSchema.setAcciEndDate(tLCDutySchema.getAcciEndDate());
		mRIPolRecordSchema.setGetStartDate(tLCDutySchema.getGetStartDate());
		mRIPolRecordSchema.setGetStartType(tLCDutySchema.getGetStartType());
		mRIPolRecordSchema.setPeakLine(tLCDutySchema.getPeakLine());
		mRIPolRecordSchema.setGetLimit(tLCDutySchema.getGetLimit());
		mRIPolRecordSchema.setGetRate(tLCDutySchema.getGetRate());
	}

	// 保全
	private void setRIPolRecordInfo(LPPolSchema tLCPolSchema,
			LPDutySchema tLCDutySchema) {
		mRIPolRecordSchema.setYears(tLCDutySchema.getYears());
		mRIPolRecordSchema.setCValiDate(tLCPolSchema.getCValiDate());
		mRIPolRecordSchema.setEndDate(tLCDutySchema.getEndDate());
		mRIPolRecordSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
		mRIPolRecordSchema.setInsuredName(tLCPolSchema.getInsuredName());
		mRIPolRecordSchema.setInsuredSex(tLCPolSchema.getInsuredSex());
		mRIPolRecordSchema
				.setInsuredAge(PubFun.calInterval(
						tLCPolSchema.getInsuredBirthday(),
						PubFun.getCurrentDate(), "Y"));
		mRIPolRecordSchema.setOccupationType(tLCPolSchema.getOccupationType());
		mRIPolRecordSchema.setStandPrem(tLCDutySchema.getStandPrem());
		mRIPolRecordSchema.setPrem(tLCDutySchema.getPrem());
		mRIPolRecordSchema.setRiskAmnt(tLCDutySchema.getRiskAmnt());
		mRIPolRecordSchema.setMult(tLCDutySchema.getMult());
		mRIPolRecordSchema.setPayIntv(tLCDutySchema.getPayIntv());
		mRIPolRecordSchema.setPayYears(tLCDutySchema.getPayYears());
		mRIPolRecordSchema.setPayEndYearFlag(tLCDutySchema.getPayEndYearFlag());
		mRIPolRecordSchema.setPayEndYear(tLCDutySchema.getPayEndYear());
		mRIPolRecordSchema.setGetYearFlag(tLCDutySchema.getGetYearFlag());
		mRIPolRecordSchema.setGetYear(tLCDutySchema.getGetYear());
		mRIPolRecordSchema.setInsuYearFlag(tLCDutySchema.getInsuYearFlag());
		mRIPolRecordSchema.setInsuYear(tLCDutySchema.getInsuYear());
		mRIPolRecordSchema.setAcciYearFlag(tLCDutySchema.getAcciYearFlag());
		mRIPolRecordSchema.setAcciYear(tLCDutySchema.getAcciYear());
		mRIPolRecordSchema.setAcciEndDate(tLCDutySchema.getAcciEndDate());
		mRIPolRecordSchema.setGetStartDate(tLCDutySchema.getGetStartDate());
		mRIPolRecordSchema.setGetStartType(tLCDutySchema.getGetStartType());
		mRIPolRecordSchema.setPeakLine(tLCDutySchema.getPeakLine());
		mRIPolRecordSchema.setGetLimit(tLCDutySchema.getGetLimit());
		mRIPolRecordSchema.setGetRate(tLCDutySchema.getGetRate());
	}

	// 理赔
	private void setRIPolRecordInfo(LBPolSchema tLBPolSchema,
			LBDutySchema tLBDutySchema) {
		mRIPolRecordSchema.setYears(tLBDutySchema.getYears());
		mRIPolRecordSchema.setCValiDate(tLBPolSchema.getCValiDate());
		mRIPolRecordSchema.setEndDate(tLBDutySchema.getEndDate());
		mRIPolRecordSchema.setInsuredNo(tLBPolSchema.getInsuredNo());
		System.out.println(" 被保人号： " + tLBPolSchema.getInsuredNo());

		mRIPolRecordSchema.setInsuredName(tLBPolSchema.getInsuredName());
		mRIPolRecordSchema.setInsuredSex(tLBPolSchema.getInsuredSex());
		mRIPolRecordSchema
				.setInsuredAge(PubFun.calInterval(
						tLBPolSchema.getInsuredBirthday(),
						PubFun.getCurrentDate(), "Y"));
		mRIPolRecordSchema.setStandPrem(tLBDutySchema.getStandPrem());
		mRIPolRecordSchema.setPrem(tLBDutySchema.getPrem());
		mRIPolRecordSchema.setRiskAmnt(tLBDutySchema.getRiskAmnt());
		mRIPolRecordSchema.setAmnt(tLBDutySchema.getAmnt());
		mRIPolRecordSchema.setMult(tLBDutySchema.getMult());
		mRIPolRecordSchema.setPayIntv(tLBDutySchema.getPayIntv());
		mRIPolRecordSchema.setPayYears(tLBDutySchema.getPayYears());
		mRIPolRecordSchema.setPayEndYearFlag(tLBDutySchema.getPayEndYearFlag());
		mRIPolRecordSchema.setPayEndYear(tLBDutySchema.getPayEndYear());
		mRIPolRecordSchema.setGetYearFlag(tLBDutySchema.getGetYearFlag());
		mRIPolRecordSchema.setGetYear(tLBDutySchema.getGetYear());
		mRIPolRecordSchema.setInsuYearFlag(tLBDutySchema.getInsuYearFlag());
		mRIPolRecordSchema.setInsuYear(tLBDutySchema.getInsuYear());
		mRIPolRecordSchema.setAcciYearFlag(tLBDutySchema.getAcciYearFlag());
		mRIPolRecordSchema.setAcciYear(tLBDutySchema.getAcciYear());
		mRIPolRecordSchema.setAcciEndDate(tLBDutySchema.getAcciEndDate());
		mRIPolRecordSchema.setGetStartDate(tLBDutySchema.getGetStartDate());
		mRIPolRecordSchema.setGetStartType(tLBDutySchema.getGetStartType());
		mRIPolRecordSchema.setPeakLine(tLBDutySchema.getPeakLine());
		mRIPolRecordSchema.setGetLimit(tLBDutySchema.getGetLimit());
		mRIPolRecordSchema.setGetRate(tLBDutySchema.getGetRate());
	}

	public RIPolRecordSchema getResult() {
		return mRIPolRecordSchema;
	}

	public static void main(String[] args) {

	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "ReComManageBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

}
