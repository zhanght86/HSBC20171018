package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.cbcheck.BQAddPremCalBL;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LMDutyPayAddFeeSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * Title: 计算保全重算加费金额
 *        传入参数：6个容器后调用cbcheck.BQAddPremCalBL返回tranferdata
 * @author jiaqiangli
 * @category 2009-01-20
 * @version 1.0
 */

public class AddPremReCalBQInterface {
private static Logger logger = Logger.getLogger(AddPremReCalBQInterface.class);
	
	public CErrors mErrors = new CErrors();
	
	private Reflections mReflections = new Reflections();
	
	private GlobalInput mGlobalInput = new GlobalInput();
	
	//加费中lcprem的前后的standprem之差值
	private double mAddFeeStandPremMinus = 0.00;
	
	public AddPremReCalBQInterface(GlobalInput tGlobalInput) {
		mGlobalInput = tGlobalInput;
	}
	
	private LCDutySchema getLCDuty(String sDutyCode, LCDutyBLSet tLCDutyBLSet) {
		LCDutySchema tLCDutySchema = new LCDutySchema();
		for (int i = 1; i <= tLCDutyBLSet.size(); i++) {
			if (sDutyCode != null && sDutyCode.equals(tLCDutyBLSet.get(i).getDutyCode())) {
				mReflections.transFields(tLCDutySchema, tLCDutyBLSet.get(i).getSchema());
				return tLCDutySchema;
			}
		}
		return null;
	}
	
	private LCInsuredSchema getLCInsured(String tContNo, String tInsuredNo) {
		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(tContNo);
		tLCInsuredDB.setInsuredNo(tInsuredNo);
		if (tLCInsuredDB.getInfo() == false) {
			return null;
		}
		tLCInsuredSchema = tLCInsuredDB.getSchema();
		return tLCInsuredSchema;
	}
	
	/**
	 * @param tLPPolSchema
	 * @param tLPPermSet
	 * @param tLCDutyBLSet
	 * @return
	 */
	public LPPremSet recalAddPrem(LPPolSchema tLPPolSchema, LPPremSet tLPPermSet, LCDutyBLSet tLCDutyBLSet) {
		LPPremSet tReturnLPPermSet = new LPPremSet();

		LCDutySchema tLCDutySchema = null;
		LPDutySchema tLPDutySchema = null;
		LPPremSchema tAddFeeLPPremSchema = null;
		LPPremSchema tNonAddFeeLPPremSchema = null;
		LMDutyPayAddFeeSchema tLMDutyPayAddFeeSchema = null;
		
		LCInsuredSchema tLCInsuredSchema = getLCInsured(tLPPolSchema.getContNo(), tLPPolSchema.getInsuredNo());
		LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
	    mReflections.transFields(tLPInsuredSchema, tLCInsuredSchema);
	    
	    for (int i = 1; i <= tLPPermSet.size(); i++) {
			if (tLPPermSet.get(i).getPayPlanType() != null && tLPPermSet.get(i).getPayPlanType().equals("0")) {
				tNonAddFeeLPPremSchema = new LPPremSchema();
				mReflections.transFields(tNonAddFeeLPPremSchema, tLPPermSet.get(i).getSchema());
				//非加费的lpprem表
				tReturnLPPermSet.add(tLPPermSet.get(i));
				break;
			}
	    }
	    double tMinus = 0.00;
		for (int i = 1; i <= tLPPermSet.size(); i++) {
			if (tLPPermSet.get(i).getPayPlanType() != null && !tLPPermSet.get(i).getPayPlanType().equals("0")) {
				tLMDutyPayAddFeeSchema = new LMDutyPayAddFeeSchema();
				//AddFeeObject 加费方式 有可能为null，cbcheck重新算法：保额保费的等比变化
				tLMDutyPayAddFeeSchema.setAddFeeObject(tLPPermSet.get(i).getAddFeeDirect());
				tLMDutyPayAddFeeSchema.setAddFeeType(tLPPermSet.get(i).getPayPlanType());

				tLCDutySchema = new LCDutySchema();
				tLCDutySchema = getLCDuty(tLPPermSet.get(i).getDutyCode(), tLCDutyBLSet);
				if (tLCDutySchema == null) {
					CError.buildErr(this, "加费项责任编码有误!");
					return null;
				}
				tLPDutySchema = new LPDutySchema();
				mReflections.transFields(tLPDutySchema, tLCDutySchema);
				
				//pk RISKCODE, DUTYCODE, ADDFEETYPE, ADDFEEOBJECT
				tLMDutyPayAddFeeSchema.setRiskCode(tLPPolSchema.getRiskCode());
				tLMDutyPayAddFeeSchema.setDutyCode(tLCDutySchema.getDutyCode());

				tAddFeeLPPremSchema = new LPPremSchema();
				mReflections.transFields(tAddFeeLPPremSchema, tLPPermSet.get(i).getSchema());
				double tSourceStandPrem = tAddFeeLPPremSchema.getStandPrem();

				VData tInputData = new VData();
				tInputData.add(mGlobalInput);
				tInputData.add(tLPPolSchema);
				tInputData.add(String.valueOf((tNonAddFeeLPPremSchema.getStandPrem())));
				tInputData.add(tAddFeeLPPremSchema);
				tInputData.add(tLPDutySchema);
				tInputData.add(tLMDutyPayAddFeeSchema);
				tInputData.add(tLPInsuredSchema);

				BQAddPremCalBL tBQAddPremCalBL = new BQAddPremCalBL();
				if (!tBQAddPremCalBL.submitData(tInputData, "")) {
					CError.buildErr(this, "加费计算失败!");
					return null;
				}
				TransferData tTransferData = (TransferData) ((VData) tBQAddPremCalBL.getResult()).getObjectByObjectName(
						"TransferData", 0);
				String sNewAddFee = (String) tTransferData.getValueByName("mValue");

				if (!sNewAddFee.equals("-1")) {
					tLPPermSet.get(i).setPrem((String) tTransferData.getValueByName("mValue"));
					//lcpem表的standprem = prem
					tLPPermSet.get(i).setStandPrem(tLPPermSet.get(i).getPrem());
				}
				else { // 没有加费费率
					CError.buildErr(this, "没有对应的加费费率，请上报申请并补充表外费率!");
					return null;
				}
				double tDestStandPrem = Double.parseDouble(sNewAddFee);
				tMinus += tDestStandPrem - tSourceStandPrem;
				//加费的lpprem表
				tReturnLPPermSet.add(tLPPermSet.get(i));
			}
		}
		mAddFeeStandPremMinus = tMinus;
		return tReturnLPPermSet;
	}
	
	
	/**
	 * @param tLPPolSchema
	 * @param tLPPermSet
	 * @param tLCDutyBLSet
	 * @param tInsuredSex
	 * @param tInsOccupationType
	 * @return
	 */
    //保全项IC,IO重算加费时涉及到被保人性别和职业类别变化，需要将此变量传入
	public LPPremSet recalAddPrem2(LPPolSchema tLPPolSchema, LPPremSet tLPPermSet, LCDutyBLSet tLCDutyBLSet,
			String tInsuredSex,String tInsOccupationType) 
	{
		LPPremSet tReturnLPPermSet = new LPPremSet();

		LCDutySchema tLCDutySchema = null;
		LPDutySchema tLPDutySchema = null;
		LPPremSchema tAddFeeLPPremSchema = null;
		LPPremSchema tNonAddFeeLPPremSchema = null;
		LMDutyPayAddFeeSchema tLMDutyPayAddFeeSchema = null;
		
		LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
		tLPInsuredSchema.setSex(tInsuredSex);
		tLPInsuredSchema.setOccupationType(tInsOccupationType);
	    
	    for (int i = 1; i <= tLPPermSet.size(); i++) {
			if (tLPPermSet.get(i).getPayPlanType() != null && tLPPermSet.get(i).getPayPlanType().equals("0")) {
				tNonAddFeeLPPremSchema = new LPPremSchema();
				mReflections.transFields(tNonAddFeeLPPremSchema, tLPPermSet.get(i).getSchema());
				//非加费的lpprem表
				tReturnLPPermSet.add(tLPPermSet.get(i));
				break;
			}
	    }
	    double tMinus = 0.00;
		for (int i = 1; i <= tLPPermSet.size(); i++) {
			if (tLPPermSet.get(i).getPayPlanType() != null && !tLPPermSet.get(i).getPayPlanType().equals("0")) {
				tLMDutyPayAddFeeSchema = new LMDutyPayAddFeeSchema();
				//AddFeeObject 加费方式 有可能为null，cbcheck重新算法：保额保费的等比变化
				tLMDutyPayAddFeeSchema.setAddFeeObject(tLPPermSet.get(i).getAddFeeDirect());
				tLMDutyPayAddFeeSchema.setAddFeeType(tLPPermSet.get(i).getPayPlanType());

				tLCDutySchema = new LCDutySchema();
				tLCDutySchema = getLCDuty(tLPPermSet.get(i).getDutyCode(), tLCDutyBLSet);
				if (tLCDutySchema == null) {
					CError.buildErr(this, "加费项责任编码有误!");
					return null;
				}
				tLPDutySchema = new LPDutySchema();
				mReflections.transFields(tLPDutySchema, tLCDutySchema);
				
				//pk RISKCODE, DUTYCODE, ADDFEETYPE, ADDFEEOBJECT
				tLMDutyPayAddFeeSchema.setRiskCode(tLPPolSchema.getRiskCode());
				tLMDutyPayAddFeeSchema.setDutyCode(tLCDutySchema.getDutyCode());

				tAddFeeLPPremSchema = new LPPremSchema();
				mReflections.transFields(tAddFeeLPPremSchema, tLPPermSet.get(i).getSchema());
				double tSourceStandPrem = tAddFeeLPPremSchema.getStandPrem();

				VData tInputData = new VData();
				tInputData.add(mGlobalInput);
				tInputData.add(tLPPolSchema);
				tInputData.add(String.valueOf((tNonAddFeeLPPremSchema.getStandPrem())));
				tInputData.add(tAddFeeLPPremSchema);
				tInputData.add(tLPDutySchema);
				tInputData.add(tLMDutyPayAddFeeSchema);
				tInputData.add(tLPInsuredSchema);

				BQAddPremCalBL tBQAddPremCalBL = new BQAddPremCalBL();
				if (!tBQAddPremCalBL.submitData(tInputData, "")) {
					CError.buildErr(this, "加费计算失败!");
					return null;
				}
				TransferData tTransferData = (TransferData) ((VData) tBQAddPremCalBL.getResult()).getObjectByObjectName(
						"TransferData", 0);
				String sNewAddFee = (String) tTransferData.getValueByName("mValue");

				if (!sNewAddFee.equals("-1")) {
					tLPPermSet.get(i).setPrem((String) tTransferData.getValueByName("mValue"));
					//lcpem表的standprem = prem
					tLPPermSet.get(i).setStandPrem(tLPPermSet.get(i).getPrem());
				}
				else { // 没有加费费率
					CError.buildErr(this, "没有对应的加费费率，请上报申请并补充表外费率!");
					return null;
				}
				double tDestStandPrem = Double.parseDouble(sNewAddFee);
				tMinus += tDestStandPrem - tSourceStandPrem;
				//加费的lpprem表
				tReturnLPPermSet.add(tLPPermSet.get(i));
			}
		}
		mAddFeeStandPremMinus = tMinus;
		return tReturnLPPermSet;
	}
	
	/**
	 * @return recalAddPrem调完调用返回差值
	 */
	public double getAddFeeMinus() {
		double tTmpMinus = mAddFeeStandPremMinus;
		return tTmpMinus;
	}
}
