

package com.sinosoft.lis.reinsure.calculate.process;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.reinsure.tools.RIPubFun;
import com.sinosoft.lis.schema.RIPolRecordSchema;
import com.sinosoft.lis.schema.RIRecordTraceSchema;
import com.sinosoft.lis.vschema.RIDivisionLineDefSet;
import com.sinosoft.lis.vschema.RIIncomeCompanySet;
import com.sinosoft.lis.vschema.RIRecordSet;
import com.sinosoft.lis.vschema.RIRecordTraceTempSet;
import com.sinosoft.lis.vschema.RIRiskDivideSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
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
public class RIStandClaimProcess implements RIProcessType {
	public CErrors mErrors = new CErrors();
	private VData mVData = new VData();

	private double mCessRate;
	private double mCessAmnt;
	private MMap mMap;

	public RIStandClaimProcess() {
	}

	public boolean dealData(RIPolRecordSchema aRIPolRecordSchema,
			RIIncomeCompanySet aRIIncomeCompanySet,
			RIDivisionLineDefSet aRIDivisionLineDefSet,
			RIRiskDivideSet aRIRiskDivideSet) {
		mVData.clear();
		mMap = new MMap();
		RIPrecept tRIPrecept = new RIPrecept();
		RIRecordTraceTempSet tRIRecordTraceTempSet = tRIPrecept.DealAddDiv(
				aRIIncomeCompanySet, aRIDivisionLineDefSet, aRIRiskDivideSet);
		RIPubFun tRIPubFun = new RIPubFun();
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

			// 记录分保保额变化量，分保比例
			Reflections tReflections = new Reflections();
			RIRecordTraceSchema tRIRecordTraceSchema = new RIRecordTraceSchema();
			tReflections.transFields(tRIRecordTraceSchema,
					tRIRecordTraceTempSet.get(i));
			if (!getRecord(tRIRecordTraceSchema)) {
				return false;
			}
			tRIRecordTraceTempSet.get(i).setCessionRate(mCessRate);
			tRIRecordTraceTempSet.get(i).setCessionAmount(mCessAmnt);
		}
		if (tRIRecordTraceTempSet.size() > 0) {
			mMap.put(tRIRecordTraceTempSet, "INSERT");
		}
		mVData.add(mMap);
		return true;
	}

	/**
	 * 记录保全。如果已存在该区间记录，则修改分保比例分保保额。如果没有则添加记录
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
			if (RIPubFun.compare(tRIRecordSet.get(i), riRecordTraceSchema)) {
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
			strBuffer.append("' and  b.riskcode='");
			strBuffer.append(riRecordTraceSchema.getRiskCode());
			strBuffer.append("' and  b.dutycode='");
			strBuffer.append(riRecordTraceSchema.getDutyCode());

			strBuffer
					.append("' and b.eventtype in ('01','03') and b.ridate=(select max(a.ridate) from rirecordtrace a where a.accumulatedefno=b.accumulatedefno and a.ripreceptno=b.ripreceptno and a.otherno=b.otherno and a.areaid = b.areaid and a.contno=a.contno and  b.RiskCode=a.RiskCode and b.DutyCode=a.DutyCode and a.eventtype in ('01','03') and a.ridate<=b.ridate ))) x ");
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
