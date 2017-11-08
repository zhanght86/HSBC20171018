

package com.sinosoft.lis.reinsure.calculate.process;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.reinsure.tools.RIPubFun;
import com.sinosoft.lis.schema.RIPolRecordSchema;
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
public class RICoinsureContinuousProcess implements RIProcessType {
	public CErrors mErrors = new CErrors();
	private VData mVData = new VData();
	private MMap mMap;

	public RICoinsureContinuousProcess() {
	}

	public boolean dealData(RIPolRecordSchema aRIPolRecordSchema,
			RIIncomeCompanySet aRIIncomeCompanySet,
			RIDivisionLineDefSet aRIDivisionLineDefSet,
			RIRiskDivideSet aRIRiskDivideSet) {
		try {
			mVData.clear();
			mMap = new MMap();
			RIPrecept tRIPrecept = new RIPrecept();
			double AccumulateAmnt = aRIPolRecordSchema.getAccAmnt(); // 本系统的累计风险保额定义为包括：历史与本单的风险保额。累计币种
			double newAmnt = aRIPolRecordSchema.getChRiskAmnt(); // 新增风险保额。累计币种
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
					// 先计算分保比例再用保单本币计算分保保额
					tRIRecordTraceSet.get(i).setCurentAmnt(
							aRIPolRecordSchema.getRiskAmnt());
					// 分保比例
					tRIRecordTraceSet.get(i)
							.setCessionRate(
									tRIRecordTraceSet.get(i).getAmountChang()
											/ newAmnt);
					// 分保保额
					tRIRecordTraceSet.get(i)
							.setCessionAmount(
									aRIPolRecordSchema.getRiskAmnt()
											* tRIRecordTraceSet.get(i)
													.getCessionRate());
					// 分保保额变量
					tRIRecordTraceSet.get(i).setAmountChang(
							tRIRecordTraceSet.get(i).getCessionAmount());
					// 业务日期
					tRIRecordTraceSet.get(i).setRIDate(
							aRIPolRecordSchema.getGetDate());
					tRIRecordTraceSet.get(i).setRiskCode(
							aRIPolRecordSchema.getRiskCode());
					tRIRecordTraceSet.get(i).setDutyCode(
							aRIPolRecordSchema.getDutyCode());
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
