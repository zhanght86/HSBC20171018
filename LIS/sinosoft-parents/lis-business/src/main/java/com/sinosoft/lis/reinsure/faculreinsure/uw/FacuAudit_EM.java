

package com.sinosoft.lis.reinsure.faculreinsure.uw;

import com.sinosoft.lis.schema.RICalFactorValueSchema;
import com.sinosoft.lis.vschema.RIExchangeRateSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class FacuAudit_EM implements FacuAudit {

	private String mContNo;
	private String mInsuredNo;
	private String mFactorID;
	private String mFactorValue;
	private String mCurrency;
	private String mAccumulateDefNO;
	private String mAccumulateMode;
	private String mPolNo;
	private String mDutyCode;
	private boolean uwFlag = true;
	public CErrors mErrors = new CErrors();
	private RIExchangeRateSet mRIExchangeRateSet;

	private RICalFactorValueSchema mRICalFactorValueSchema = new RICalFactorValueSchema();
	private TransferData mTransferData = new TransferData();

	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData)) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData) {
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mRIExchangeRateSet = (RIExchangeRateSet) cInputData
				.getObjectByObjectName("RIExchangeRateSet", 0);

		mContNo = (String) mTransferData.getValueByName("ContNo");
		mInsuredNo = (String) mTransferData.getValueByName("InsuredNo");
		mFactorID = (String) mTransferData.getValueByName("FactorID");
		mFactorValue = (String) mTransferData.getValueByName("FactorValue");
		mCurrency = (String) mTransferData.getValueByName("Currency");
		mAccumulateDefNO = (String) mTransferData
				.getValueByName("AccumulateDefNO");
		mAccumulateMode = (String) mTransferData
				.getValueByName("AccumulateMode");
		mPolNo = (String) mTransferData.getValueByName("PolNo");
		mDutyCode = (String) mTransferData.getValueByName("DutyCode");

		return true;
	}

	private boolean dealData() {
		uwFlag = true;
		StringBuffer strBuffer = new StringBuffer();

		ExeSQL tExeSQL = new ExeSQL();

		SSRS emSSRS = new SSRS();
		strBuffer
				.append(" select  max(a.SuppRiskScore) from LCPrem a where a.Contno='");
		strBuffer.append(mContNo);
		strBuffer.append("' and a.Polno = '");
		strBuffer.append(mPolNo);
		strBuffer.append("' and a.Dutycode ='");
		strBuffer.append(mDutyCode);
		strBuffer.append("' and a.AddFeeDirect = '01' ");

		System.out.println("sql1: " + strBuffer.toString());

		emSSRS = tExeSQL.execSQL(strBuffer.toString());
		if (tExeSQL.mErrors.needDealError()) {
			buildError("InitInfo", "获取累计风险保额明细出错！");
			return false;
		}

		if (emSSRS.getMaxRow() == 0) {
			uwFlag = true;
			return true;
		}
		if (emSSRS.getAllData()[0][0] == null
				|| emSSRS.getAllData()[0][0].equals("")
				|| emSSRS.getAllData()[0][0].equals("null")) {
			uwFlag = true;
			return true;
		}
		if (Double.parseDouble(emSSRS.getAllData()[0][0]) >= Double
				.parseDouble(mFactorValue)) {
			uwFlag = false;
		}
		return true;
	}

	public boolean getResult() {
		return uwFlag;
	}

	public CErrors getCErrors() {
		return this.mErrors;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RIRiskCal";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
		System.out.print(szErrMsg);
	}

}
