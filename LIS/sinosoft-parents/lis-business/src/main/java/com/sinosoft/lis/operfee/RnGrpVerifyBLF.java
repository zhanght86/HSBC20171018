package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCUrgeLogSchema;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
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
public class RnGrpVerifyBLF {
private static Logger logger = Logger.getLogger(RnGrpVerifyBLF.class);
	private GlobalInput mGI = new GlobalInput();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	public CErrors mErrors = new CErrors();

	public RnGrpVerifyBLF() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData)) {
			mGI = ((GlobalInput) cInputData.getObjectByObjectName(
					"GlobalInput", 0));
			return false;
		}

		if (!dealData()) {
			return false;
		}

		return true;
	}

	private boolean getInputData(VData cInputData) {
		RNGrpAutoVerifyQuery tRNGrpAutoVerifyQuery = new RNGrpAutoVerifyQuery();
		if (!tRNGrpAutoVerifyQuery.submitData(cInputData, "")) {
			CError.buildErr(this, "未查询到待核销保单");
			return false;
		} else {
			VData tVData = tRNGrpAutoVerifyQuery.getResult();
			LCGrpContSet tLCGrpContSet = (LCGrpContSet) tVData
					.getObjectByObjectName("LCGrpContSet", 0);
			for (int i = 1; i <= tLCGrpContSet.size(); i++) {
				LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
				tLCGrpContSchema = tLCGrpContSet.get(i);
				tVData.clear();
				tVData.add(tLCGrpContSchema);
				tVData.add(mGI);
				RnGrpComfirmBL tRnGrpComfirmBL = new RnGrpComfirmBL();
				MMap mMMap = new MMap();
				if (!tRnGrpComfirmBL.submitData(tVData, "")) {
					LCUrgeLogSchema tLCUrgeLogSchmea = new LCUrgeLogSchema();
					tLCUrgeLogSchmea.setErrorMessage(tRnGrpComfirmBL.mErrors
							.getFirstError());
					tLCUrgeLogSchmea.setPolNo(tLCGrpContSchema.getGrpContNo());
					tLCUrgeLogSchmea.setMakeDate(CurrentDate);
					tLCUrgeLogSchmea.setMakeTime(CurrentTime);
					tLCUrgeLogSchmea.setManageCom(mGI.ManageCom);
					tLCUrgeLogSchmea.setTempFeeNo(tLCGrpContSchema
							.getGrpContNo());
					mMMap.put(tLCUrgeLogSchmea, "DELETE&INSERT");
				} else {
					mMMap.add(tRnGrpComfirmBL.GetMMap());
				}

				tVData.clear();
				tVData.add(mMMap);
				PubSubmit tSubmit = new PubSubmit();
				if (!tSubmit.submitData(tVData, "")) {
					// @@错误处理
					CError.buildErr(this, "传入参数为空");
					tVData.clear();
					continue;
				}
				tVData.clear();
			}
		}

		return true;
	}

	private boolean dealData() {
		return true;
	}

	public static void main(String[] args) {
		RnGrpVerifyBLF rngrpverifyblf = new RnGrpVerifyBLF();
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "005";
		tGlobalInput.ManageCom = "86";
		VData tVData = new VData();
		tVData.add(tGlobalInput);
		rngrpverifyblf.submitData(tVData, "");
	}
}
