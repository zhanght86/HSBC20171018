

package com.sinosoft.lis.reinsure.calculate.bonus;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class RIBonusCalBL {
	public CErrors mErrors = new CErrors();
	private String mStartDate = "";
	private String mEndDate = "";
	private String mOperate = "";
	private String[][] mRiskCodeArr;
	private String mRiskCode = "";
	private String Operator = "";
	private TransferData mTransferData = new TransferData();
	private GlobalInput  tGlobalInput = new GlobalInput();
	private VData mInputData;

	RIBonusCal mRIBonusCal = new RIBonusCal();

	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		if (!init()) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData, String cOperate) {
		this.mOperate = cOperate;
		this.mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		this.tGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		this.mStartDate = (String) mTransferData.getValueByName("StartDate");
		this.mEndDate = (String) mTransferData.getValueByName("EndDate");
		this.Operator = (String) tGlobalInput.Operator;
		return true;
	}

	private boolean init() {
		// 初始化险种
		if (!initRiskCodeArr()) {
			return false;
		}
		return true;
	}

	private boolean dealData() {
		// 计算累计红利
		if (mOperate.equals("01")) {
			for (int i = 1; i <= this.mRiskCodeArr.length; i++) {
				mRiskCode = mRiskCodeArr[i-1][0]; 
				//mRiskCode="IBE53";
				if (!prepareData()) {

				}
				if (!mRIBonusCal.submitData(mInputData, "")) {
					buildError("InitInfo",
							"计算周年红利出错！" + mRIBonusCal.mErrors.getFirstError());
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * 初始化险种编码
	 * 
	 * @return
	 */
	private boolean initRiskCodeArr() {
		ExeSQL tExeSQL = new ExeSQL();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select distinct a.Associatedcode from RIAccumulateRDCode a where a.Accumulatedefno in( select  distinct b.Accumulatedefno from RIPrecept b  where b.Bonus = '01')");
		SSRS tSSRS = tExeSQL.execSQL(sqlBuffer.toString());
		if (tExeSQL.mErrors.needDealError() || tSSRS == null) {
			buildError("initRiskCodeArr", "计算累计红利初始化险种时出错！");
			return false;
		}
		mRiskCodeArr = tSSRS.getAllData();
		return true;
	}

	private boolean prepareData() {
		try {
			this.mInputData = new VData();
			this.mTransferData = new TransferData();
			this.mTransferData.setNameAndValue("RiskCode", mRiskCode);
			this.mTransferData.setNameAndValue("StartDate", mStartDate);
			this.mTransferData.setNameAndValue("EndDate", mEndDate);
			this.mTransferData.setNameAndValue("Operator", Operator);

			this.mInputData.add(mTransferData);

		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "RITaskApplyBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "计算累计红利在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RIRiskCal";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
		System.out.print(szErrMsg);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		VData cInputData = new VData();
		TransferData mTransferData123 = new TransferData();
		//GlobalInput tGlobalInput = new GlobalInput();
		GlobalInput xxx  = new GlobalInput();
		xxx.Operator="12";
		mTransferData123.setNameAndValue("StartDate", "2011-12-01");
		mTransferData123.setNameAndValue("EndDate", "2011-12-08");
		cInputData.add(0,xxx);
		cInputData.add(0, mTransferData123);
		RIBonusCalBL aa = new RIBonusCalBL();
		aa.submitData(cInputData,"01");
	}

}
