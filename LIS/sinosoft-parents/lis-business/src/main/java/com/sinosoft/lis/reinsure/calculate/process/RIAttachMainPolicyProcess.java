

package com.sinosoft.lis.reinsure.calculate.process;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.reinsure.tools.RIPubFun;
import com.sinosoft.lis.schema.RIPolRecordSchema;
import com.sinosoft.lis.schema.RIRecordSchema;
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
 * Description: 生成依附主险新单分保记录
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @zhangbin
 * @version 3.0
 */
public class RIAttachMainPolicyProcess implements RIProcessType {
	public CErrors mErrors = new CErrors();
	private VData mVData = new VData();
	private double mCessRate;
	private double mCessAmnt;
	private MMap mMap;

	public RIAttachMainPolicyProcess() {
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

			// 查询历史分保保额变化量和分保比例
			Reflections tReflections = new Reflections();
			RIRecordTraceSchema tRIRecordTraceSchema = new RIRecordTraceSchema();
			tReflections.transFields(tRIRecordTraceSchema,
					tRIRecordTraceTempSet.get(i));
			if (!getRecord(tRIRecordTraceSchema)) {
				return false;
			}
			tRIRecordTraceTempSet.get(i).setCessionRate(mCessRate);

			// // 查询历史分保保额变化量和分保比例
			// tReflections = new Reflections();
			// tRIRecordTraceSchema = new RIRecordTraceSchema();
			// tReflections.transFields(tRIRecordTraceSchema,
			// tRIRecordTraceTempSet.get(i));
			//
			// RIRecord.setRIRecordSet(chngRecord(tRIRecordTraceSchema));
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

		// 查询主险分保比例
		if (!getAmntChagAndCessRate(riRecordTraceSchema)) {
			return false;
		}
		if (mCessRate != 0) {
			RIRecordSchema riRecordSchema = new RIRecordSchema();
			riRecordSchema.setAccumulateDefNO(riRecordTraceSchema
					.getAccumulateDefNO());
			riRecordSchema.setContNo(riRecordTraceSchema.getContNo());
			riRecordSchema.setRiskCode(riRecordTraceSchema.getRiskCode());
			riRecordSchema.setDutyCode(riRecordTraceSchema.getDutyCode());

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
	 * 得到历史分保保额和分保比例
	 * 
	 * @param riRecordTraceSchema
	 *            RIRecordTraceSchema
	 * @return boolean
	 */
	private boolean getAmntChagAndCessRate(
			RIRecordTraceSchema riRecordTraceSchema) {
		try {
			// 获取主险分保比例
			StringBuffer strBuffer = new StringBuffer();
			strBuffer
					.append(" select  nvl(max(a.Cessionrate), 0)  from rirecordtrace a where 1=1 and a.Serialno= (select max(x.Serialno) from rirecordtrace x where 1 = 1 and x.ContNo ='");
			strBuffer.append(riRecordTraceSchema.getContNo());
			strBuffer.append("' and x.OtherNo = '");
			strBuffer.append(riRecordTraceSchema.getOtherNo());
			strBuffer.append("' and x.AreaID ='");
			strBuffer.append(riRecordTraceSchema.getAreaID());
			strBuffer
					.append("' and x.Eventtype in ('01','03') and exists (SELECT 1 FROM ripolrecord u, ripolrecordbake v where u.mainpolno = v.polno and v.eventno = x.eventno and u.Eventno='"
							+ riRecordTraceSchema.getEventNo() + "')) ");
			System.out.println(" 查询历史分保记录： " + strBuffer.toString());
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = tExeSQL.execSQL(strBuffer.toString());
			if (tExeSQL.mErrors.needDealError()) {
				buildError("getAmntChagAndCessRate", " 取得保全分保比例时出错 ");
				return false;
			}
			if (tSSRS.MaxRow == 0) {
				mCessRate = 0;
			} else {
				mCessRate = Double.parseDouble(tSSRS.GetText(1, 1));
			}
		} catch (Exception ex) {
			buildError("getAmntChagAndCessRate",
					" 取得保全分保比例时出错 " + ex.getMessage());
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
		riRecordSchema.setRiskCode(riRecordTraceSchema.getRiskCode());
		riRecordSchema.setDutyCode(riRecordTraceSchema.getDutyCode());

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
