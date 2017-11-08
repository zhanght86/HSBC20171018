

package com.sinosoft.lis.reinsure.calculate.bonus;
	
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class RIInterestCalBL {
	public CErrors mErrors = new CErrors();
	private String mStartDate = "";
	private String mEndDate = "";
	private String[][] mRiskCodeArr;
	private String mRiskCode = "";
	private double mInterestRate = 0; // 利率
	private String mOperate;
	private TransferData mTransferData;
	private VData mInputData;
	private RIInterestCal mRIInterestCal = new RIInterestCal();

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
		this.mStartDate = (String) mTransferData.getValueByName("StartDate");
		this.mEndDate = (String) mTransferData.getValueByName("EndDate");

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
		for (int i = 1; i <= this.mRiskCodeArr.length; i++) {
			mRiskCode = mRiskCodeArr[i-1][0]; //
			//mRiskCode="IBE62";
			// 初始化利率
			if (!initInterestRate(mRiskCode)) {
				buildError("initRiskCodeArr", "初始化利率时出错！");
				return false;
			}
			if (!prepareData()) {
				buildError("initRiskCodeArr", "计算红利利息初始化险种时出错！");
				return false;
			}
			if (!mRIInterestCal.submitData(mInputData, "")) {
				buildError("InitInfo",
						"计算周年红利出错！" + mRIInterestCal.mErrors.getFirstError());
				return false;
			}
		}
		return true;
	}

	/**
	 * 初始化利率
	 */
	private boolean initInterestRate(String mRiskCode) {
		//String  sql ="select  InterestRate from RIInterest a where a.RiskCode='"+mRiskCode+"' and a.State='01' and a.InterestType='01'";
		String  sql ="select nvl(x.InterestRate,0) from RIInterest x,(select max(a.Serialno) Serialno,a.Riskcode Riskcode from RIInterest a "
		+" where a.state='01' group by a.Riskcode) y where x.Serialno = y.Serialno and x.Riskcode = y.Riskcode and x.RiskCode='"+mRiskCode+"'";
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sql);
		String sInterestRate;
		if(tSSRS.getMaxRow()!=0){
			 sInterestRate = tSSRS.GetText(1, 1);	
		}
		else{
			 sInterestRate = "0";
		}
		mInterestRate = Double.parseDouble(sInterestRate);
		return true;
	}

	private boolean prepareData() {
		try {
			//this.mInputData.clear();
			mTransferData = new TransferData();
			mInputData = new VData();
			this.mTransferData.setNameAndValue("StartDate", mStartDate);
			this.mTransferData.setNameAndValue("EndDate", mEndDate);
			this.mTransferData.setNameAndValue("RiskCode", mRiskCode);
			this.mTransferData.setNameAndValue("InterestRate", mInterestRate+ "");
			mInputData.add(mTransferData);
			
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
		TransferData mTransferData = new TransferData();
		mTransferData.setNameAndValue("StartDate", "2011-12-01");
		mTransferData.setNameAndValue("EndDate", "2011-12-30");
		cInputData.add(mTransferData);
		RIInterestCalBL aa = new RIInterestCalBL();
		aa.submitData(cInputData, "01");
	}

}
