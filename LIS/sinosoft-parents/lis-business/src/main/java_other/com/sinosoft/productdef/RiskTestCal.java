

package com.sinosoft.productdef;

import com.sinosoft.lis.bl.LCDutyBL;
import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class RiskTestCal implements BusinessService{
	
	private String mLanguage = "";
	
	////////////////////////////
	private String mRiskCode = "";
	
	private String mTemplateID = "";
	private String mRiskType = "";
	
	
	private static String mAppnt = "Appnt";
	private static String mInsured = "Insured";
	private static String mRisk = "Risk";
	private static String mDuty = "Duty";
	private static String mPay = "Pay";
	
	
	private TransferData mTransferData = new TransferData();
	private LCPremSet mLCPremSet = new LCPremSet();
	
	private VData mInputData=new VData();
	public CErrors mErrors = new CErrors();
	
	public RiskTestCal()
	{	
	}
	
	public boolean submitData(VData tInputData,String tOperate)
	{
		
		/*
		lcpol

		RiskCode
		PolNo
		ContNo
		Mult
		CValiDate
		Prem
		StandPrem
		Amnt
		RiskAmnt
		InsuredBirthday
		 */

		getInputData(tInputData);

		PDTCalBL tPDTCalBL;
		
		LCPolBL tLCPolBL = new LCPolBL();
		tLCPolBL.setContNo("T1");
		tLCPolBL.setPolNo("T1");
		//tLCPolBL.setAmnt(aAmnt)
		String aRiskCode = this.mTransferData.getValueByName("RiskCode")==null?"":(String) this.mTransferData.getValueByName("RiskCode");
		tLCPolBL.setRiskCode(aRiskCode);
		String aCValiDate = this.mTransferData.getValueByName("CValiDate")==null?PubFun.getCurrentDate():(String) this.mTransferData.getValueByName("CValiDate");
		tLCPolBL.setCValiDate(aCValiDate);
		
		String aPrem= this.mTransferData.getValueByName("Prem")==null?"":(String) this.mTransferData.getValueByName("Prem");
		if(aPrem==null||aPrem.equals(""))
		{
			aPrem = "0";
		}
		
		tLCPolBL.setPrem(Double.parseDouble(aPrem));
		
		String aAmnt= this.mTransferData.getValueByName("Amnt")==null?"":(String) this.mTransferData.getValueByName("Amnt");
		if(aAmnt==null||aAmnt.equals(""))
		{
			aAmnt = "0";
		}
		
		tLCPolBL.setAmnt(Double.parseDouble(aAmnt));
		
		
//		double aAmnt= this.mTransferData.getValueByName("Amnt")==null?0:Double.parseDouble((String) this.mTransferData.getValueByName("Amnt"));
//		tLCPolBL.setAmnt(aAmnt);
		
		String aMult= this.mTransferData.getValueByName("Mult")==null?"":(String) this.mTransferData.getValueByName("Mult");
		if(aMult==null||aMult.equals(""))
		{
			aMult = "0";
		}
		
		tLCPolBL.setMult(Double.parseDouble(aMult));

		tLCPolBL.setPayEndYear(this.mTransferData.getValueByName("PayEndYear")==null?"":(String) this.mTransferData.getValueByName("PayEndYear"));
		tLCPolBL.setPayEndYearFlag(this.mTransferData.getValueByName("PayEndYearFlag")==null?"":(String) this.mTransferData.getValueByName("PayEndYearFlag"));

		tLCPolBL.setInsuYear(this.mTransferData.getValueByName("InsuYear")==null?"":(String) this.mTransferData.getValueByName("InsuYear"));
		tLCPolBL.setInsuYearFlag(this.mTransferData.getValueByName("InsuYearFlag")==null?"":(String) this.mTransferData.getValueByName("InsuYearFlag"));
		tLCPolBL.setOccupationType(this.mTransferData.getValueByName("OccupationType")==null?"":(String) this.mTransferData.getValueByName("OccupationType"));
		String aInsuredBirthday= this.mTransferData.getValueByName("InsuredBirthday")==null?"1900-01-01":(String) this.mTransferData.getValueByName("InsuredBirthday");

		String aInsuredAppAge= this.mTransferData.getValueByName("AppAge")==null?"0":(String) this.mTransferData.getValueByName("AppAge");

		tLCPolBL.setInsuredBirthday(aInsuredBirthday);
		tLCPolBL.setInsuredAppAge(aInsuredAppAge);
		
		String aInsuredSex= this.mTransferData.getValueByName("Sex")==null?"0":(String) this.mTransferData.getValueByName("Sex");
		tLCPolBL.setInsuredSex(aInsuredSex);
		
		String aCurrency= this.mTransferData.getValueByName("CurrencyCode")==null?"01":(String) this.mTransferData.getValueByName("CurrencyCode");
		tLCPolBL.setCurrency(aCurrency);
		
		String aPayIntv= this.mTransferData.getValueByName("PayIntv")==null?"0":(String) this.mTransferData.getValueByName("PayIntv");
		tLCPolBL.setPayIntv(aPayIntv);

//		double aMult= this.mTransferData.getValueByName("Mult")==null?0:Double.parseDouble((String) this.mTransferData.getValueByName("Mult"));
//		tLCPolBL.setMult(aMult);
		
		
		LCDutyBLSet tLCDutyBLSet = new LCDutyBLSet();
		LCDutyBL tLCDutyBL = new LCDutyBL();
		tLCDutyBL.setInsuYear(tLCPolBL.getInsuYear());
		tLCDutyBL.setInsuYearFlag(tLCPolBL.getInsuYearFlag());
		tLCDutyBL.setPayEndYear(tLCPolBL.getPayEndYear());
		tLCDutyBL.setPayEndYearFlag(tLCPolBL.getPayEndYearFlag());
		tLCDutyBL.setPayIntv(aPayIntv);
		tLCDutyBLSet.add(tLCDutyBL);
		
		tPDTCalBL =  new PDTCalBL(tLCPolBL, tLCDutyBLSet,"");
		if (tPDTCalBL.calPol() == false) {

		}
		
		// 取出计算的结果
		LCPolSchema tLCPolSchema = tPDTCalBL.getLCPol().getSchema();
		System.out.println("Amnt:"+tLCPolSchema.getAmnt()+":Prem:"+tLCPolSchema.getPrem());
		LCPremSet tLCPremSet1 = tPDTCalBL.getLCPrem();
		this.mInputData.add(0,tLCPolSchema.getPrem());
		int n = mLCPremSet.size();

		for (int i = 1; i <= n; i++) {
			LCPremSchema tLCPremSchema = mLCPremSet.get(i);
		}
		//tLCGetBLSet.add(this.mLCPremSet);
//		if (tValue==null||tValue.equals("")) {
//			tCalBL = new CalBL(tLCPolBL, tLCDutyBLSet, "");
//		} else {
//			tLCGetBL.setGetDutyKind(tValue);
//			tLCGetBLSet.add(tLCGetBL);
//			tCalBL = new CalBL(tLCPolBL, tLCDutyBLSet, tLCGetBLSet, "");
//		}
//
//		if (tCalBL.calPol() == false) {
//			CError.buildErr(this, "险种保单重新计算时失败:"
//					+ tCalBL.mErrors.getFirstError());
//
//			//return null;
//		}
		
		return true;
	}

	private void getInputData(VData tInputData) {
		try {
			mTransferData  = tInputData.get(0)==null?(new TransferData()):(TransferData)tInputData.get(0);
			
			mLCPremSet = tInputData.get(1)==null?(new LCPremSet()):(LCPremSet)tInputData.get(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return mInputData;
	}
	
}
