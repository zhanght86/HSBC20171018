

package com.sinosoft.lis.reinsure.calculate.process;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.reinsure.tools.RIPubFun;
import com.sinosoft.lis.schema.RIPolRecordSchema;
import com.sinosoft.lis.schema.RIRecordSchema;
import com.sinosoft.lis.schema.RIRecordTraceSchema;
import com.sinosoft.lis.vschema.RIDivisionLineDefSet;
import com.sinosoft.lis.vschema.RIIncomeCompanySet;
import com.sinosoft.lis.vschema.RIRecordSet;
import com.sinosoft.lis.vschema.RIRecordTraceSet;
import com.sinosoft.lis.vschema.RIRecordTraceTempSet;
import com.sinosoft.lis.vschema.RIRiskDivideSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
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
public class RIStandContinuousProcess_BENE implements RIProcessType {
	public CErrors mErrors = new CErrors();
	private VData mVData = new VData();
	private double mCessRate;
	private double mCessAmnt;
	private MMap mMap;

	public RIStandContinuousProcess_BENE() {
	}

	public boolean dealData(RIPolRecordSchema aRIPolRecordSchema,
			RIIncomeCompanySet aRIIncomeCompanySet,
			RIDivisionLineDefSet aRIDivisionLineDefSet,
			RIRiskDivideSet aRIRiskDivideSet) {
		try {
			mVData.clear();
			mMap = new MMap();
			String riskcode = aRIPolRecordSchema.getRiskCode().substring(0, 4);
			RIPubFun tRIPubFun = new RIPubFun();
			RIPrecept tRIPrecept = new RIPrecept();

			if ("BENE".equals(riskcode)) {

				double AccumulateAmnt = aRIPolRecordSchema.getAccAmnt();
				double newAmnt = aRIPolRecordSchema.getRiskAmnt()
						- aRIPolRecordSchema.getPreRiskAmnt();
				double riLevel = tRIPrecept.getLevelone(aRIDivisionLineDefSet,
						aRIPolRecordSchema);
				double riLimit = tRIPrecept.getRILimit(aRIDivisionLineDefSet,
						aRIPolRecordSchema);
				if (AccumulateAmnt > riLimit) {
					// 处理存在未分保的大于自留额小于最低分出额的数据情况
					if ((AccumulateAmnt - newAmnt) > riLevel
							&& (AccumulateAmnt - newAmnt) <= riLimit) {
						newAmnt = AccumulateAmnt - riLevel;
					}
					RIRecordTraceSet tRIRecordTraceSet = tRIPrecept.DealAddDiv(
							AccumulateAmnt - newAmnt, newAmnt,
							aRIIncomeCompanySet, aRIDivisionLineDefSet,
							aRIRiskDivideSet, aRIPolRecordSchema);
					for (int i = 1; i <= tRIRecordTraceSet.size(); i++) {
						tRIRecordTraceSet.get(i).setSerialNo(
								tRIPubFun.getCessRecordSerialNo());
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
						tRIRecordTraceSet.get(i).setRIDate(
								aRIPolRecordSchema.getGetDate());
						// tRIRecordTraceSet.get(i).setAssociateCode(aRIPolRecordSchema.getRiskCode());

						// 记录分保保额变化量，分保比例
						if (!getRecord(tRIRecordTraceSet.get(i))) {
							return false;
						}
						tRIRecordTraceSet.get(i).setCessionAmount(
								mCessAmnt
										+ tRIRecordTraceSet.get(i)
												.getAmountChang());
						tRIRecordTraceSet.get(i).setCessionRate(
								tRIRecordTraceSet.get(i).getCessionAmount()
										/ tRIRecordTraceSet.get(i)
												.getCurentAmnt());
						// 保存新分保保额分保比例到内存
						if (!setRecord(tRIRecordTraceSet.get(i))) {
							return false;
						}
					}
					if (tRIRecordTraceSet.size() > 0) {
						mMap = new MMap();
						mMap.put(tRIRecordTraceSet, "INSERT");
					}
				}
			} else {
				RIRecordTraceTempSet tRIRecordTraceTempSet = tRIPrecept
						.DealAddDiv(aRIIncomeCompanySet, aRIDivisionLineDefSet,
								aRIRiskDivideSet);
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
					// tRIRecordTraceTempSet.get(i).setAssociateCode(aRIPolRecordSchema.getRiskCode());
					// 记录分保保额变化量，分保比例

				}
				if (tRIRecordTraceTempSet.size() > 0) {
					mMap.put(tRIRecordTraceTempSet, "INSERT");
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
	 * 记录保全。如何已存在该区间记录，则修改分保比例分保保额。如果没有则添加记录
	 * 
	 * @param riRecordTraceSchema
	 *            RIRecordTraceSchema
	 * @return boolean
	 */
	private boolean getRecord(RIRecordTraceSchema riRecordTraceSchema) {
		RIRecordSet tRIRecordSet = RIRecord.getRIRecordSet();
		// 该区域在本批次中已经记录下分保信息
		int i = 1;
		for (; i <= tRIRecordSet.size(); i++) {
			System.out.println(" tRIRecordSet.get():"
					+ tRIRecordSet.get(i).getAreaID());
			if (compareRecord(tRIRecordSet.get(i), riRecordTraceSchema)) {
				mCessAmnt = tRIRecordSet.get(i).getCessionAmount();
				mCessRate = tRIRecordSet.get(i).getCessionRate();
				System.out.println(" CessionAmount: "
						+ tRIRecordSet.get(i).getCessionAmount()
						+ "  CessionRate:　"
						+ tRIRecordSet.get(i).getCessionRate());
				return true;
			}
		}
		// 该区域在本批次中未记录下分保信息，则查询历史分保信息
		if (i > tRIRecordSet.size()) {
			// 如果没有该记录信息，查询数据库得到分保保额变化量和分保比例
			if (!getAmntChagAndCessRate(riRecordTraceSchema)) {
				return false;
			}
			RIRecordSchema riRecordSchema = new RIRecordSchema();
			riRecordSchema.setAccumulateDefNO(riRecordTraceSchema
					.getAccumulateDefNO());
			riRecordSchema.setContNo(riRecordTraceSchema.getContNo());
			// riRecordSchema.setAssociateCode(riRecordTraceSchema.getAssociateCode());
			riRecordSchema.setOtherNo(riRecordTraceSchema.getOtherNo());
			riRecordSchema.setRIPreceptNo(riRecordTraceSchema.getRIPreceptNo());
			riRecordSchema.setAreaID(riRecordTraceSchema.getAreaID());
			riRecordSchema.setCessionAmount(mCessAmnt);
			riRecordSchema.setCessionRate(mCessRate);
			RIRecord.setRIRecordSet(riRecordSchema);
		}
		return true;
	}

	/**
	 * 记录保全
	 * 
	 * @param riRecordTraceSchema
	 *            RIRecordTraceSchema
	 * @return boolean
	 */
	private boolean setRecord(RIRecordTraceSchema riRecordTraceSchema) {
		RIRecordSet tRIRecordSet = RIRecord.getRIRecordSet();
		int i = 1;
		for (; i <= tRIRecordSet.size(); i++) {
			if (compareRecord(tRIRecordSet.get(i), riRecordTraceSchema)) {
				tRIRecordSet.get(i).setCessionAmount(
						mCessAmnt + riRecordTraceSchema.getAmountChang());
				// 如果是减额分保比例不变
				if (riRecordTraceSchema.getAmountChang() < 0) {
					tRIRecordSet.get(i).setCessionRate(mCessRate);
				} else if (riRecordTraceSchema.getCurentAmnt() == 0) { // 如果是增额计算新分保保额
					tRIRecordSet.get(i).setCessionRate(0);
				} else {
					tRIRecordSet.get(i).setCessionRate(
							tRIRecordSet.get(i).getCessionAmount()
									/ riRecordTraceSchema.getCurentAmnt());
				}
				System.out.println(" CessionAmount: "
						+ tRIRecordSet.get(i).getCessionAmount()
						+ "  CessionRate:　"
						+ tRIRecordSet.get(i).getCessionRate());
				return true;
			}
		}
		if (i > tRIRecordSet.size()) {
			buildError("getAmntChagAndCessRate",
					" 记录保全分保信息时出错: 在内存中没有找到保全分保区间信息");
			return false;
		}
		return true;
	}

	/**
	 * 得到历史分保保额和分保比例
	 * 
	 * @param riRecordTraceSchema
	 *            RIRecordTraceSchema
	 * @return boolean
	 */
	private boolean getAmntChagAndCessRate(
			RIRecordTraceSchema riRecordTraceSchema) {
		try {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer
					.append(" select nvl(x.A1,0),nvl(x.A2,0) from (select c.cessionrate A1,c.CessionAmount A2 from rirecordtrace c where c.serialno=(select max(b.serialno) from rirecordtrace b where b.accumulatedefno='");
			strBuffer.append(riRecordTraceSchema.getAccumulateDefNO());
			strBuffer.append("' and b.ripreceptno='");
			strBuffer.append(riRecordTraceSchema.getRIPreceptNo());
			strBuffer.append("' and b.otherno='");
			strBuffer.append(riRecordTraceSchema.getOtherNo());
			strBuffer.append("' and b.areaid = '");
			strBuffer.append(riRecordTraceSchema.getAreaID());
			strBuffer.append("' and b.contno='");
			strBuffer.append(riRecordTraceSchema.getContNo());
			strBuffer.append("' and  b.AssociateCode='");
			// strBuffer.append(riRecordTraceSchema.getAssociateCode());
			strBuffer
					.append("' and b.eventtype in ('01','02','03') and b.ridate=(select max(a.ridate) from rirecordtrace a where a.accumulatedefno=b.accumulatedefno and a.ripreceptno=b.ripreceptno and a.otherno=b.otherno and a.areaid = b.areaid and a.contno=a.contno and  b.AssociateCode=a.AssociateCode and a.eventtype in ('01','02','03') and a.ridate<=b.ridate ))) x ");
			System.out.println(" 查询历史分保记录： " + strBuffer.toString());
			ExeSQL tExeSQL = new ExeSQL();
			// String strSQL =
			// "select nvl(x.A1,0),nvl(x.A2,0) from (select c.cessionrate A1,c.CessionAmount A2 from rirecordtrace c where c.serialno=(select max(b.serialno) from rirecordtrace b where b.accumulatedefno='"+riRecordTraceSchema.getAccumulateDefNO()+"' and b.ripreceptno='"+riRecordTraceSchema.getRIPreceptNo()+"' and b.otherno='"+riRecordTraceSchema.getOtherNo()+"' and b.areaid = '"+riRecordTraceSchema.getAreaID()+"' and b.contno='"+riRecordTraceSchema.getContNo()+"' and b.eventtype in ('01','03') and b.ridate=(select max(a.ridate) from rirecordtrace a where a.accumulatedefno='"+riRecordTraceSchema.getAccumulateDefNO()+"' and a.ripreceptno='"+riRecordTraceSchema.getRIPreceptNo()+"' and a.otherno='"+riRecordTraceSchema.getOtherNo()+"' and a.areaid = '"+riRecordTraceSchema.getAreaID()+"' and a.contno='"+riRecordTraceSchema.getContNo()+"' and a.eventtype in ('01','03') and a.ridate<='"+riRecordTraceSchema.getRIDate()+"' ))) x "
			// ;
			SSRS tSSRS = tExeSQL.execSQL(strBuffer.toString());
			if (tExeSQL.mErrors.needDealError()) {
				buildError("getAmntChagAndCessRate", " 取得保全分保比例时出错 ");
				return false;
			}
			if (tSSRS.MaxRow == 0) {
				mCessRate = 0;
				mCessAmnt = 0;
			} else {
				mCessRate = Double.parseDouble(tSSRS.GetText(1, 1));
				mCessAmnt = Double.parseDouble(tSSRS.GetText(1, 2));
			}
		} catch (Exception ex) {
			buildError("getAmntChagAndCessRate",
					" 取得保全分保比例时出错 " + ex.getMessage());
			return false;
		}
		return true;
	}

	private boolean compareRecord(RIRecordSchema riRecordSchema,
			RIRecordTraceSchema riRecordTraceSchema) {
		if (riRecordTraceSchema.getAccumulateDefNO().equals(
				riRecordSchema.getAccumulateDefNO())
				&& riRecordTraceSchema.getOtherNo().equals(
						riRecordSchema.getOtherNo())
				&& riRecordTraceSchema.getContNo().equals(
						riRecordSchema.getContNo())
				&& riRecordTraceSchema.getRIPreceptNo().equals(
						riRecordSchema.getRIPreceptNo())
				&& riRecordTraceSchema.getAreaID().equals(
						riRecordSchema.getAreaID())
		// &&riRecordTraceSchema.getAssociateCode().equals(riRecordSchema.getAssociateCode())
		) {

			return true;
		} else {
			return false;
		}
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
