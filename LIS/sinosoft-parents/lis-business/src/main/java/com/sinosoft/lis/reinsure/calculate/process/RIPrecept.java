

package com.sinosoft.lis.reinsure.calculate.process;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.lis.vschema.RIRiskDivideSet;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.RIDivisionLineDefSet;
import com.sinosoft.lis.vschema.RIIncomeCompanySet;
import com.sinosoft.lis.vschema.RIRecordTraceSet;
import com.sinosoft.lis.schema.RIRecordTraceSchema;
import com.sinosoft.lis.vschema.RIRecordTraceTempSet;
import com.sinosoft.lis.schema.RIRecordTraceTempSchema;

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
 * Company:
 * </p>
 * @ zhangbin
 * 
 * @version 1.0
 */
public class RIPrecept {
	public CErrors mErrors = new CErrors();

	public RIPrecept() {
	}

	public RIRecordTraceSet DealAddDiv(double CumulateMoney, double addMoney,
			RIIncomeCompanySet aRIIncomeCompanySet,
			RIDivisionLineDefSet tRIDivisionLineDefSet,
			RIRiskDivideSet aRIRiskDivideSet,
			RIPolRecordSchema aRIPolRecordSchema) {
		RIRecordTraceSet tRIRecordTraceSet = new RIRecordTraceSet();
		if (addMoney <= 0) {
			return tRIRecordTraceSet;
		}
		for (int i = 1; i <= aRIRiskDivideSet.size(); i++) {
			double LowerLimit = 0;
			double UpperLimit = 0;
			RIRiskDivideSchema tRIRiskDivideSchema = aRIRiskDivideSet.get(i);
			if (getCalFlag(tRIRiskDivideSchema.getIncomeCompanyNo(),
					aRIIncomeCompanySet)) {
				// 获取区域的上限与下限
				for (int k = 1; k <= tRIDivisionLineDefSet.size(); k++) {
					if (tRIDivisionLineDefSet.get(k).getRIPreceptNo()
							.equals(tRIRiskDivideSchema.getRIPreceptNo())
							&& tRIDivisionLineDefSet.get(k)
									.getDivisionLineValue() == tRIRiskDivideSchema
									.getUpperLimit()) {// 计算上限UpperLimit
						if (tRIDivisionLineDefSet.get(k).getDivisionFactor() != null) {// 如果定义了动态限额系数
							String DivisionFactor = tRIDivisionLineDefSet
									.get(k).getDivisionFactor();
							if (aRIPolRecordSchema.getV(DivisionFactor) == null) {// 动态限额系数对应的接口表数据为空
								buildError("DealAddDiv()", "再保计算时，动态限额系数为空");
								return null;
							}
							double FactorValue = Double
									.parseDouble(aRIPolRecordSchema
											.getV(DivisionFactor));
							UpperLimit = tRIRiskDivideSchema.getUpperLimit()
									* FactorValue;
						} else {// 如果没有定义动态限额系数，upperlimit=RIRiskDivide前台定义的upperLimit
							UpperLimit = tRIRiskDivideSchema.getUpperLimit();
						}
					}
					if (tRIDivisionLineDefSet.get(k).getRIPreceptNo()
							.equals(tRIRiskDivideSchema.getRIPreceptNo())
							&& tRIDivisionLineDefSet.get(k)
									.getDivisionLineValue() == tRIRiskDivideSchema
									.getLowerLimit()) {// 计算下限LowerLimit
						if (tRIDivisionLineDefSet.get(k).getDivisionFactor() != null) {// 如果定义了动态限额系数
							String DivisionFactor = tRIDivisionLineDefSet
									.get(k).getDivisionFactor();
							if (aRIPolRecordSchema.getV(DivisionFactor) == null) {// 动态限额系数对应的接口表数据为空(只能校验字符类型的字段,数值类型为空时schema会初始为0)
								buildError("DealAddDiv()", "再保计算时，动态限额系数为空");
								return null;
							}
							double FactorValue = Double
									.parseDouble(aRIPolRecordSchema
											.getV(DivisionFactor));
							LowerLimit = tRIRiskDivideSchema.getLowerLimit()
									* FactorValue;
						} else {// 如果没有定义动态限额系数，upperlimit=RIRiskDivide前台定义的upperLimit
							LowerLimit = tRIRiskDivideSchema.getLowerLimit();
						}
					}
				}
				// 临分标志
				boolean reinsureflag = false;
				for (int m = 1; m <= tRIDivisionLineDefSet.size(); m++) {// 如果此区域属于临分区域
																			// tRIRiskDivideSchema
					if (tRIRiskDivideSchema.getLowerLimit() == tRIDivisionLineDefSet
							.get(m).getDivisionLineValue()
							&& tRIDivisionLineDefSet.get(m)
									.getDivisionLineType().equals("03")) {
						reinsureflag = true;
						break;
					}
				}
				if (LowerLimit <= CumulateMoney
						&& UpperLimit >= (CumulateMoney + addMoney)) {
					RIRecordTraceSchema tRIRecordTraceSchema = new RIRecordTraceSchema();
					tRIRecordTraceSchema.setRIPreceptNo(tRIRiskDivideSchema
							.getRIPreceptNo());
					tRIRecordTraceSchema.setAreaID(tRIRiskDivideSchema
							.getAreaID());
					tRIRecordTraceSchema.setIncomeCompanyNo(tRIRiskDivideSchema
							.getIncomeCompanyNo());
					tRIRecordTraceSchema.setAmountChang(addMoney
							* tRIRiskDivideSchema.getPossessScale());
					// 置临分标记
					if (reinsureflag) {
						tRIRecordTraceSchema.setStandbyString1("03");
					}
					tRIRecordTraceSet.add(tRIRecordTraceSchema);
				} else if (LowerLimit < CumulateMoney
						&& UpperLimit > CumulateMoney
						&& UpperLimit < (CumulateMoney + addMoney)) {
					RIRecordTraceSchema tRIRecordTraceSchema = new RIRecordTraceSchema();
					tRIRecordTraceSchema.setRIPreceptNo(tRIRiskDivideSchema
							.getRIPreceptNo());
					tRIRecordTraceSchema.setAreaID(tRIRiskDivideSchema
							.getAreaID());
					tRIRecordTraceSchema.setIncomeCompanyNo(tRIRiskDivideSchema
							.getIncomeCompanyNo());
					tRIRecordTraceSchema
							.setAmountChang((UpperLimit - CumulateMoney)
									* tRIRiskDivideSchema.getPossessScale());
					// 置临分标记
					if (reinsureflag) {
						tRIRecordTraceSchema.setStandbyString1("03");
					}
					tRIRecordTraceSet.add(tRIRecordTraceSchema);
				} else if (LowerLimit > (CumulateMoney)
						&& LowerLimit < (CumulateMoney + addMoney)
						&& UpperLimit > (CumulateMoney + addMoney)) {
					RIRecordTraceSchema tRIRecordTraceSchema = new RIRecordTraceSchema();
					tRIRecordTraceSchema.setRIPreceptNo(tRIRiskDivideSchema
							.getRIPreceptNo());
					tRIRecordTraceSchema.setAreaID(tRIRiskDivideSchema
							.getAreaID());
					tRIRecordTraceSchema.setIncomeCompanyNo(tRIRiskDivideSchema
							.getIncomeCompanyNo());
					tRIRecordTraceSchema.setAmountChang((CumulateMoney
							+ addMoney - LowerLimit)
							* tRIRiskDivideSchema.getPossessScale());
					// 置临分标记
					if (reinsureflag) {
						tRIRecordTraceSchema.setStandbyString1("03");
					}
					tRIRecordTraceSet.add(tRIRecordTraceSchema);
				} else if ((LowerLimit >= (CumulateMoney) && UpperLimit < (CumulateMoney + addMoney))
						|| (LowerLimit > (CumulateMoney) && UpperLimit <= (CumulateMoney + addMoney))) {
					RIRecordTraceSchema tRIRecordTraceSchema = new RIRecordTraceSchema();
					tRIRecordTraceSchema.setRIPreceptNo(tRIRiskDivideSchema
							.getRIPreceptNo());
					tRIRecordTraceSchema.setAreaID(tRIRiskDivideSchema
							.getAreaID());
					tRIRecordTraceSchema.setIncomeCompanyNo(tRIRiskDivideSchema
							.getIncomeCompanyNo());
					tRIRecordTraceSchema
							.setAmountChang((UpperLimit - LowerLimit)
									* tRIRiskDivideSchema.getPossessScale());
					// 置临分标记
					if (reinsureflag) {
						tRIRecordTraceSchema.setStandbyString1("03");
					}
					tRIRecordTraceSet.add(tRIRecordTraceSchema);
				}
			}
		}
		return tRIRecordTraceSet;
	}

	/**
	 * 得到全部分保区间
	 * 
	 * @param aRIIncomeCompanySet
	 *            RIIncomeCompanySet
	 * @param tRIDivisionLineDefSet
	 *            RIDivisionLineDefSet
	 * @param aRIRiskDivideSet
	 *            RIRiskDivideSet
	 * @return RIRecordTraceSet
	 */
	public RIRecordTraceTempSet DealAddDiv(
			RIIncomeCompanySet aRIIncomeCompanySet,
			RIDivisionLineDefSet tRIDivisionLineDefSet,
			RIRiskDivideSet aRIRiskDivideSet) {
		RIRecordTraceTempSet tRIRecordTraceTempSet = new RIRecordTraceTempSet();
		RIRecordTraceTempSchema tRIRecordTraceTempSchema;
		for (int i = 1; i <= aRIRiskDivideSet.size(); i++) {
			RIRiskDivideSchema tRIRiskDivideSchema = aRIRiskDivideSet.get(i);
			if (getCalFlag(tRIRiskDivideSchema.getIncomeCompanyNo(),
					aRIIncomeCompanySet)) {
				if (tRIRiskDivideSchema.getPossessScale() != 0) {
					// 临分标志
					boolean reinsureflag = false;
					for (int m = 1; m <= tRIDivisionLineDefSet.size(); m++) {// 如果此区域属于临分区域
																				// tRIRiskDivideSchema
						if (tRIRiskDivideSchema.getLowerLimit() == tRIDivisionLineDefSet
								.get(m).getDivisionLineValue()
								&& tRIDivisionLineDefSet.get(m)
										.getDivisionLineType().equals("03")) {
							reinsureflag = true;
							break;
						}
					}
					tRIRecordTraceTempSchema = new RIRecordTraceTempSchema();
					tRIRecordTraceTempSchema.setRIPreceptNo(tRIRiskDivideSchema
							.getRIPreceptNo());
					tRIRecordTraceTempSchema.setAreaID(tRIRiskDivideSchema
							.getAreaID());
					tRIRecordTraceTempSchema
							.setIncomeCompanyNo(tRIRiskDivideSchema
									.getIncomeCompanyNo());
					// 置临分标记
					if (reinsureflag) {
						tRIRecordTraceTempSchema.setStandbyString1("03");
					}
					tRIRecordTraceTempSet.add(tRIRecordTraceTempSchema);
				}
			}
		}
		return tRIRecordTraceTempSet;
	}

	/**
	 * 判断再保公司是否计算分出
	 * 
	 * @param IncomeCompanyNo
	 *            String
	 * @return boolean
	 */
	private boolean getCalFlag(String IncomeCompanyNo,
			RIIncomeCompanySet aRIIncomeCompanySet) {
		for (int i = 1; i <= aRIIncomeCompanySet.size(); i++) {
			if (aRIIncomeCompanySet.get(i).getIncomeCompanyNo()
					.equals(IncomeCompanyNo)) {
				if (aRIIncomeCompanySet.get(i).getIncomeCompanyType()
						.equals("01")) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * 得到最低分出额
	 * 
	 * @param pRIDivisionLineDefSet
	 *            RIDivisionLineDefSet
	 * @return double
	 * @throws Exception
	 */
	public double getRILimit(RIDivisionLineDefSet pRIDivisionLineDefSet,
			RIPolRecordSchema pRIPolRecordSchema) throws Exception {
		try {
			double tValue = 0;
			String DivisionFactor = "";
			double FactorValue = 0;
			for (int i = 1; i <= pRIDivisionLineDefSet.size(); i++) {
				if (pRIDivisionLineDefSet.get(i).getDivisionLineType()
						.equals("04")) {
					if (pRIDivisionLineDefSet.get(i).getDivisionFactor() == null
							|| pRIDivisionLineDefSet.get(i).getDivisionFactor()
									.equals("")) {
						tValue = pRIDivisionLineDefSet.get(i)
								.getDivisionLineValue();
					} else { // 如果定义了动态限额系数
						DivisionFactor = pRIPolRecordSchema
								.getV(pRIDivisionLineDefSet.get(i)
										.getDivisionFactor());
						if (DivisionFactor == null) { // 如果动态限额对应的接口数据为空
							this.buildError("getRILimit()", "再保计算时，动态限额系数为空");
							tValue = -1;
						} else {
							FactorValue = Double.parseDouble(DivisionFactor);
							tValue = pRIDivisionLineDefSet.get(i)
									.getDivisionLineValue() * FactorValue;
						}
					}
					break;
				}
				if (i == pRIDivisionLineDefSet.size()) {
					tValue = this.getLevelone(pRIDivisionLineDefSet,
							pRIPolRecordSchema);
				}
			}
			return tValue;
		} catch (Exception ex) {
			throw ex;
		}
	}

	/**
	 * 得到自留额
	 * 
	 * @param pRIDivisionLineDefSet
	 *            RIDivisionLineDefSet
	 * @return double
	 * @throws Exception
	 */
	public double getLevelone(RIDivisionLineDefSet pRIDivisionLineDefSet,
			RIPolRecordSchema pRIPolRecordSchema) throws Exception {
		try {
			double tValue = 0;
			String DivisionFactor = "";
			double FactorValue = 0;
			for (int i = 1; i <= pRIDivisionLineDefSet.size(); i++) {
				if (pRIDivisionLineDefSet.get(i).getDivisionLineType()
						.equals("01")) {
					if (pRIDivisionLineDefSet.get(i).getDivisionFactor() != null) {// 如果定义了动态限额系数
						DivisionFactor = pRIPolRecordSchema
								.getV(pRIDivisionLineDefSet.get(i)
										.getDivisionFactor());
						if (DivisionFactor == null) { // 如果动态限额对应的借口数据为空
							this.buildError("getLevelone()", "再保计算时，动态限额系数为空");
							tValue = -1;
						} else {
							FactorValue = Double.parseDouble(DivisionFactor);
							tValue = pRIDivisionLineDefSet.get(i)
									.getDivisionLineValue() * FactorValue;
						}
					} else {
						tValue = pRIDivisionLineDefSet.get(i)
								.getDivisionLineValue();
					}
					break;
				}
				if (i == pRIDivisionLineDefSet.size()) {
					tValue = 0;
				}
			}
			return tValue;
		} catch (Exception ex) {
			throw ex;
		}
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RIPrecept";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
		System.out.print(szErrMsg);
	}
}
