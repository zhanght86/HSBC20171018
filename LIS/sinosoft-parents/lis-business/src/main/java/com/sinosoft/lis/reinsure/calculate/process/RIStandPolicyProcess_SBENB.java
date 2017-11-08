

package com.sinosoft.lis.reinsure.calculate.process;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.reinsure.tools.RIPubFun;
import com.sinosoft.lis.schema.RIPolRecordSchema;
import com.sinosoft.lis.schema.RIRecordSchema;
import com.sinosoft.lis.schema.RIRecordTraceSchema;
import com.sinosoft.lis.vschema.RIDivisionLineDefSet;
import com.sinosoft.lis.vschema.RIIncomeCompanySet;
import com.sinosoft.lis.vschema.RIRecordTraceSet;
import com.sinosoft.lis.vschema.RIRiskDivideSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
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
public class RIStandPolicyProcess_SBENB implements RIProcessType {
	public CErrors mErrors = new CErrors();
	private VData mVData = new VData();
	private MMap mMap;

	public RIStandPolicyProcess_SBENB() {
	}

	public boolean dealData(RIPolRecordSchema aRIPolRecordSchema,
			RIIncomeCompanySet aRIIncomeCompanySet,
			RIDivisionLineDefSet aRIDivisionLineDefSet,
			RIRiskDivideSet aRIRiskDivideSet) {
		try {
			// if(aRIPolRecordSchema.getEventNo().equals("00000000000000003344")||aRIPolRecordSchema.getEventNo().equals("00000000000000003342")){
			// System.out.println(aRIPolRecordSchema.getEventNo());
			// }
			mVData.clear();
			mMap = new MMap();
			RIPrecept tRIPrecept = new RIPrecept();
			double AccumulateAmnt = aRIPolRecordSchema.getRiskAmnt(); // SBENB不累计风险保额
			double newAmnt = aRIPolRecordSchema.getRiskAmnt();
			double riLevel = tRIPrecept.getLevelone(aRIDivisionLineDefSet,
					aRIPolRecordSchema);
			double riLimit = tRIPrecept.getRILimit(aRIDivisionLineDefSet,
					aRIPolRecordSchema);
			if (riLevel == -1 || riLimit == -1) {// 计算动态限额系数时再保接口表对应字段为空(只能校验字符类型的字段)时返回-1
				this.mErrors.addOneError(tRIPrecept.mErrors.getFirstError());
				return false;
			}

			System.out.println(" eventno: " + aRIPolRecordSchema.getEventNo());
			if (AccumulateAmnt > riLimit) {
				// 处理存在未分保的大于自留额小于最低分出额的数据情况
				if ((AccumulateAmnt - newAmnt) > riLevel
						&& (AccumulateAmnt - newAmnt) <= riLimit) {
					newAmnt = AccumulateAmnt - riLevel;
				}
				RIRecordTraceSet tRIRecordTraceSet = tRIPrecept.DealAddDiv(
						AccumulateAmnt - newAmnt, newAmnt, aRIIncomeCompanySet,
						aRIDivisionLineDefSet, aRIRiskDivideSet,
						aRIPolRecordSchema);
				if (tRIPrecept.mErrors.getErrorCount() > 0) {
					this.mErrors
							.addOneError(tRIPrecept.mErrors.getFirstError());
					return false;
				}
				RIPubFun tRIPubFun = new RIPubFun();
				for (int i = 1; i <= tRIRecordTraceSet.size(); i++) {
					String serialNo = tRIPubFun.getCessRecordSerialNo();
					tRIRecordTraceSet.get(i).setSerialNo(serialNo);
					tRIRecordTraceSet.get(i).setBatchNo(
							aRIPolRecordSchema.getBatchNo());
					tRIRecordTraceSet.get(i).setAccumulateDefNO(
							aRIPolRecordSchema.getAccumulateDefNO());
					tRIRecordTraceSet.get(i).setOtherNo(
							aRIPolRecordSchema.getInsuredNo());
					tRIRecordTraceSet.get(i).setContNo(
							aRIPolRecordSchema.getContNo());
					tRIRecordTraceSet.get(i).setEventNo(
							aRIPolRecordSchema.getEventNo());
					tRIRecordTraceSet.get(i).setEventType(
							aRIPolRecordSchema.getEventType());
					tRIRecordTraceSet.get(i).setCurentAmnt(
							aRIPolRecordSchema.getRiskAmnt());
					tRIRecordTraceSet.get(i).setCessionAmount(
							tRIRecordTraceSet.get(i).getAmountChang());
					tRIRecordTraceSet.get(i).setCessionRate(
							tRIRecordTraceSet.get(i).getCessionAmount()
									/ newAmnt);
					tRIRecordTraceSet.get(i).setRIDate(
							aRIPolRecordSchema.getGetDate());
					// tRIRecordTraceSet.get(i).setAssociateCode(aRIPolRecordSchema.getRiskCode());

					// 增人操作，记录分保比例、分保保额变化量
					RIRecord.setRIRecordSet(chngRecord(tRIRecordTraceSet.get(i)));
				}
				if (tRIRecordTraceSet.size() > 0) {
					mMap.put(tRIRecordTraceSet, "INSERT");
				}
			}
			mVData.add(mMap);
		} catch (Exception ex) {
			buildError("dealData", "生成新单分保数据时出错" + ex);
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param riRecordSchema
	 *            RIRecordSchema
	 * @param riRecordTraceSchema
	 *            RIRecordTraceSchema
	 * @return boolean
	 */
	private RIRecordSchema chngRecord(RIRecordTraceSchema riRecordTraceSchema) {
		RIRecordSchema riRecordSchema = new RIRecordSchema();
		riRecordSchema.setAccumulateDefNO(riRecordTraceSchema
				.getAccumulateDefNO());
		riRecordSchema.setContNo(riRecordTraceSchema.getContNo());
		riRecordSchema.setOtherNo(riRecordTraceSchema.getOtherNo());
		riRecordSchema.setRIPreceptNo(riRecordTraceSchema.getRIPreceptNo());
		riRecordSchema.setAreaID(riRecordTraceSchema.getAreaID());
		riRecordSchema.setCessionAmount(riRecordTraceSchema.getAmountChang());
		riRecordSchema.setCessionRate(riRecordTraceSchema.getCessionRate());
		// riRecordSchema.setAssociateCode(riRecordTraceSchema.getAssociateCode());

		return riRecordSchema;
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
