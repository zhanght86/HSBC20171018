

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
 * Description: ILBL 0.3 分出，0.7 自留；自留超过20万 的全部分出
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @caosg
 * @version 3.0
 */
public class RIStandPolicyProcess_ILBL implements RIProcessType {
	public CErrors mErrors = new CErrors();
	private VData mVData = new VData();
	private MMap mMap;

	public RIStandPolicyProcess_ILBL() {
	}

	public boolean dealData(RIPolRecordSchema aRIPolRecordSchema,
			RIIncomeCompanySet aRIIncomeCompanySet,
			RIDivisionLineDefSet aRIDivisionLineDefSet,
			RIRiskDivideSet aRIRiskDivideSet) {
		try {

			mVData.clear();
			mMap = new MMap();
			RIPrecept tRIPrecept = new RIPrecept();
			double AccumulateAmnt = aRIPolRecordSchema.getAccAmnt(); // 本系统的累计风险保额定义为包括：历史与本单的风险保额
			double newAmnt = aRIPolRecordSchema.getRiskAmnt() * 0.7;
			double riskamnt = aRIPolRecordSchema.getRiskAmnt();
			double riLevel = 200000;
			double riLimit = tRIPrecept.getRILimit(aRIDivisionLineDefSet,
					aRIPolRecordSchema);
			if (riLevel == -1 || riLimit == -1) {// 计算动态限额系数时再保接口表对应字段为空(只能校验字符类型的字段)时返回-1
				this.mErrors.addOneError(tRIPrecept.mErrors.getFirstError());
				return false;
			}

			System.out.println(" eventno: " + aRIPolRecordSchema.getEventNo());
			RIPubFun tRIPubFun = new RIPubFun();
			String sNo = tRIPubFun.getCessRecordSerialNo();

			RIRecordTraceSchema tRIRecordTraceSchema = new RIRecordTraceSchema();
			tRIRecordTraceSchema.setSerialNo(sNo);
			tRIRecordTraceSchema.setBatchNo(aRIPolRecordSchema.getBatchNo());
			tRIRecordTraceSchema.setAccumulateDefNO(aRIPolRecordSchema
					.getAccumulateDefNO());
			tRIRecordTraceSchema.setOtherNo(aRIPolRecordSchema.getInsuredNo());
			tRIRecordTraceSchema.setContNo(aRIPolRecordSchema.getContNo());
			tRIRecordTraceSchema.setEventNo(aRIPolRecordSchema.getEventNo());
			tRIRecordTraceSchema
					.setEventType(aRIPolRecordSchema.getEventType());
			tRIRecordTraceSchema.setIncomeCompanyNo("R000000004");
			tRIRecordTraceSchema.setAreaID("02");
			tRIRecordTraceSchema.setRIPreceptNo("S005001001");
			tRIRecordTraceSchema.setAmountChang(aRIPolRecordSchema
					.getRiskAmnt() * 0.3);
			tRIRecordTraceSchema
					.setCurentAmnt(aRIPolRecordSchema.getRiskAmnt());
			tRIRecordTraceSchema.setCessionAmount(aRIPolRecordSchema
					.getRiskAmnt() * 0.3);
			tRIRecordTraceSchema.setCessionRate(0.3);
			tRIRecordTraceSchema.setRIDate(aRIPolRecordSchema.getGetDate());
			// tRIRecordTraceSchema.setAssociateCode(aRIPolRecordSchema.getRiskCode());
			// 记录分保比例、分保保额变化量
			RIRecord.setRIRecordSet(chngRecord(tRIRecordTraceSchema));

			RIRecordTraceSet tRIRecordTraceSet;
			if (AccumulateAmnt > riLevel) {
				// 处理存在未分保的大于自留额小于最低分出额的数据情况
				if ((AccumulateAmnt - newAmnt) > riLevel
						&& (AccumulateAmnt - newAmnt) < riLimit) {
					newAmnt = AccumulateAmnt - riLevel;
					riskamnt += AccumulateAmnt - newAmnt - riLevel;
				}
				tRIRecordTraceSet = tRIPrecept.DealAddDiv(AccumulateAmnt
						- newAmnt, newAmnt, aRIIncomeCompanySet,
						aRIDivisionLineDefSet, aRIRiskDivideSet,
						aRIPolRecordSchema);

				if (tRIPrecept.mErrors.getErrorCount() > 0) {
					this.mErrors
							.addOneError(tRIPrecept.mErrors.getFirstError());
					return false;
				}

				for (int i = 1; i <= tRIRecordTraceSet.size(); i++) {

					if ("02".equals(tRIRecordTraceSet.get(i).getAreaID())) {

						tRIRecordTraceSet.remove(tRIRecordTraceSet.get(i));
						i--;
					} else {
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
						tRIRecordTraceSet.get(i).setCurentAmnt(riskamnt);
						tRIRecordTraceSet.get(i).setCessionAmount(
								tRIRecordTraceSet.get(i).getAmountChang());
						tRIRecordTraceSet.get(i).setCessionRate(
								tRIRecordTraceSet.get(i).getCessionAmount()
										/ riskamnt);
						tRIRecordTraceSet.get(i).setRIDate(
								aRIPolRecordSchema.getGetDate());
						// tRIRecordTraceSet.get(i).setAssociateCode(aRIPolRecordSchema.getRiskCode());
						// 记录分保比例、分保保额变化量
						RIRecord.setRIRecordSet(chngRecord(tRIRecordTraceSet
								.get(i)));
					}
				}
				tRIRecordTraceSet.add(tRIRecordTraceSchema);
			} else {
				tRIRecordTraceSet = new RIRecordTraceSet();
				tRIRecordTraceSet.add(tRIRecordTraceSchema);
			}

			if (tRIRecordTraceSet.size() > 0) {
				mMap.put(tRIRecordTraceSet, "INSERT");
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
