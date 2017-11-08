

package com.sinosoft.lis.reinsure.calculate.process;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.reinsure.tools.RIPubFun;
import com.sinosoft.lis.schema.RIPolRecordSchema;
import com.sinosoft.lis.vschema.RIDivisionLineDefSet;
import com.sinosoft.lis.vschema.RIIncomeCompanySet;
import com.sinosoft.lis.vschema.RIRecordTraceTempSet;
import com.sinosoft.lis.vschema.RIRiskDivideSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 生成续期分保记录
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @zhangbin
 * @version 3.0
 */
public class RIStandContinuousProcess implements RIProcessType {
	public CErrors mErrors = new CErrors();
	private VData mVData = new VData();
	private MMap mMap;

	public RIStandContinuousProcess() {
	}

	public boolean dealData(RIPolRecordSchema aRIPolRecordSchema,
			RIIncomeCompanySet aRIIncomeCompanySet,
			RIDivisionLineDefSet aRIDivisionLineDefSet,
			RIRiskDivideSet aRIRiskDivideSet) {
		mVData.clear();
		mMap = new MMap();
		RIPrecept tRIPrecept = new RIPrecept();
		RIPubFun tRIPubFun = new RIPubFun();

		RIRecordTraceTempSet tRIRecordTraceTempSet = tRIPrecept.DealAddDiv(
				aRIIncomeCompanySet, aRIDivisionLineDefSet, aRIRiskDivideSet);

		for (int i = 1; i <= tRIRecordTraceTempSet.size(); i++) {
			tRIRecordTraceTempSet.get(i).setSerialNo(
					tRIPubFun.getCessRecordSerialNo());
			tRIRecordTraceTempSet.get(i).setBatchNo(
					aRIPolRecordSchema.getBatchNo());
			tRIRecordTraceTempSet.get(i).setAccumulateDefNO(
					aRIPolRecordSchema.getAccumulateDefNO());
			tRIRecordTraceTempSet.get(i).setOtherNo(
					aRIPolRecordSchema.getInsuredNo());
			tRIRecordTraceTempSet.get(i).setContNo(
					aRIPolRecordSchema.getContNo());
			tRIRecordTraceTempSet.get(i).setEventNo(
					aRIPolRecordSchema.getEventNo());
			tRIRecordTraceTempSet.get(i).setEventType(
					aRIPolRecordSchema.getEventType());
			tRIRecordTraceTempSet.get(i).setCurentAmnt(
					aRIPolRecordSchema.getRiskAmnt());
			tRIRecordTraceTempSet.get(i).setRIDate(
					aRIPolRecordSchema.getGetDate());
			tRIRecordTraceTempSet.get(i).setRiskCode(
					aRIPolRecordSchema.getRiskCode());
			tRIRecordTraceTempSet.get(i).setDutyCode(
					aRIPolRecordSchema.getDutyCode());
		}

		if (tRIRecordTraceTempSet.size() > 0) {
			mMap.put(tRIRecordTraceTempSet, "INSERT");
		}
		mVData.add(mMap);

		return true;
	}

	public VData getValue() {
		return mVData;
	}

	public CErrors getCErrors() {
		return mErrors;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RIPolicyProcess";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
		System.out.print(szErrMsg);
	}
}
