

package com.sinosoft.lis.reinsure.calculate.process;

import com.sinosoft.lis.db.RIRecordTraceDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.reinsure.tools.RIPubFun;
import com.sinosoft.lis.schema.RIPolRecordSchema;
import com.sinosoft.lis.schema.RIRecordSchema;
import com.sinosoft.lis.schema.RIRecordTraceSchema;
import com.sinosoft.lis.schema.RIRecordTraceTempSchema;
import com.sinosoft.lis.vschema.RIDivisionLineDefSet;
import com.sinosoft.lis.vschema.RIIncomeCompanySet;
import com.sinosoft.lis.vschema.RIRecordSet;
import com.sinosoft.lis.vschema.RIRecordTraceSet;
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
public class RICoinsureEdorProcess implements RIProcessType {
	public CErrors mErrors = new CErrors();
	private RIPubFun mRIPubFun = new RIPubFun();
	private RIPrecept mRIPrecept;
	private double mCessRate;
	private double mCessAmnt;
	private VData mVData = new VData();
	private MMap mMap;
	private String mTBFlag = "0";

	public RICoinsureEdorProcess() {
	}

	public boolean dealData(RIPolRecordSchema aRIPolRecordSchema,
			RIIncomeCompanySet aRIIncomeCompanySet,
			RIDivisionLineDefSet aRIDivisionLineDefSet,
			RIRiskDivideSet aRIRiskDivideSet) {
		try {
			mVData.clear();
			mMap = new MMap();
			mCessRate = 0;
			mCessAmnt = 0;
			System.out.println(" eventno: " + aRIPolRecordSchema.getEventNo());
			double premAmnt = aRIPolRecordSchema.getPreChRiskAmnt();
			double amnt = aRIPolRecordSchema.getChRiskAmnt();
			if ((amnt - premAmnt) > 0) { // 保全增额
				if (premAmnt == 0) {
					// 保全增人、保全复效
					if (!DealEdorNI(aRIPolRecordSchema, aRIIncomeCompanySet,
							aRIDivisionLineDefSet, aRIRiskDivideSet)) {
						return false;
					}
				} else {
					// 保全增额
					if (!DealEdorAddAmnt(aRIPolRecordSchema,
							aRIIncomeCompanySet, aRIDivisionLineDefSet,
							aRIRiskDivideSet)) {
						return false;
					}
				}
			} else if ((amnt - premAmnt) < 0) {
				if (amnt == 0) {
					// 保全退保
					if (!DealEdorTB(aRIPolRecordSchema, aRIIncomeCompanySet,
							aRIDivisionLineDefSet, aRIRiskDivideSet)) {
						return false;
					}
				} else {
					// 保全减费
					if (!DealEdorDelAmnt(aRIPolRecordSchema,
							aRIIncomeCompanySet, aRIDivisionLineDefSet,
							aRIRiskDivideSet)) {
						return false;
					}
				}
			}
			return true;
		} catch (Exception ex) {
			buildError("dealData", ex.getMessage());
			return false;
		}
	}

	/**
	 * 保全增人
	 * 
	 * @param aRIPolRecordSchema
	 *            RIPolRecordSchema
	 * @param aRIIncomeCompanySet
	 *            RIIncomeCompanySet
	 * @param aRIDivisionLineDefSet
	 *            RIDivisionLineDefSet
	 * @param aRIRiskDivideSet
	 *            RIRiskDivideSet
	 * @return boolean
	 */
	private boolean DealEdorNI(RIPolRecordSchema aRIPolRecordSchema,
			RIIncomeCompanySet aRIIncomeCompanySet,
			RIDivisionLineDefSet aRIDivisionLineDefSet,
			RIRiskDivideSet aRIRiskDivideSet) {
		try {
			mRIPrecept = new RIPrecept();
			double AccumulateAmnt = aRIPolRecordSchema.getAccAmnt();
			double newAmnt = aRIPolRecordSchema.getRiskAmnt();
			double riLevel = mRIPrecept.getLevelone(aRIDivisionLineDefSet,
					aRIPolRecordSchema);
			double riLimit = mRIPrecept.getRILimit(aRIDivisionLineDefSet,
					aRIPolRecordSchema);

			if (AccumulateAmnt > riLevel) {
				// 处理存在未分保的大于自留额小于最低分出额的数据情况
				if ((AccumulateAmnt - newAmnt) > riLevel
						&& (AccumulateAmnt - newAmnt) < riLimit) {
					newAmnt = AccumulateAmnt - riLevel;
				}
				RIRecordTraceSet tRIRecordTraceSet = mRIPrecept.DealAddDiv(
						AccumulateAmnt - newAmnt, newAmnt, aRIIncomeCompanySet,
						aRIDivisionLineDefSet, aRIRiskDivideSet,
						aRIPolRecordSchema);
				for (int i = 1; i <= tRIRecordTraceSet.size(); i++) {
					tRIRecordTraceSet.get(i).setSerialNo(
							mRIPubFun.getCessRecordSerialNo());
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
					tRIRecordTraceSet.get(i).setCurentAmnt(newAmnt);
					tRIRecordTraceSet.get(i).setCessionAmount(
							tRIRecordTraceSet.get(i).getAmountChang());
					tRIRecordTraceSet.get(i).setCessionRate(
							tRIRecordTraceSet.get(i).getCessionAmount()
									/ newAmnt);
					tRIRecordTraceSet.get(i).setAddSubFlag("1"); // 保全增人
					tRIRecordTraceSet.get(i).setRIDate(
							aRIPolRecordSchema.getGetDate());
					tRIRecordTraceSet.get(i).setRiskCode(
							aRIPolRecordSchema.getRiskCode());
					tRIRecordTraceSet.get(i).setDutyCode(
							aRIPolRecordSchema.getDutyCode());

					// 增人操作，记录分保比例、分保保额变化量
					RIRecord.setRIRecordSet(chngRecord(tRIRecordTraceSet.get(i)));
				}
				if (tRIRecordTraceSet.size() > 0) {

					mMap.put(tRIRecordTraceSet, "INSERT");
				}
				mVData.add(mMap);
			}
			return true;
		} catch (Exception ex) {
			buildError("DealEdorNI", ex.getMessage());
			return false;
		}
	}

	/**
	 * 保全增额
	 * 
	 * @param aRIPolRecordSchema
	 *            RIPolRecordSchema
	 * @param aRIIncomeCompanySet
	 *            RIIncomeCompanySet
	 * @param aRIDivisionLineDefSet
	 *            RIDivisionLineDefSet
	 * @param aRIRiskDivideSet
	 *            RIRiskDivideSet
	 * @return boolean
	 */
	private boolean DealEdorAddAmnt(RIPolRecordSchema aRIPolRecordSchema,
			RIIncomeCompanySet aRIIncomeCompanySet,
			RIDivisionLineDefSet aRIDivisionLineDefSet,
			RIRiskDivideSet aRIRiskDivideSet) {
		try {
			// if(aRIPolRecordSchema.getEventNo().equals("00000000000000003341")){
			// System.out.println(aRIPolRecordSchema.getEventNo());
			// }
			mRIPrecept = new RIPrecept();
			double AccumulateAmnt = aRIPolRecordSchema.getAccAmnt();
			double newAmnt = aRIPolRecordSchema.getChRiskAmnt()
					- aRIPolRecordSchema.getPreChRiskAmnt();
			double riLevel = mRIPrecept.getLevelone(aRIDivisionLineDefSet,
					aRIPolRecordSchema); // 自留额
			double riLimit = mRIPrecept.getRILimit(aRIDivisionLineDefSet,
					aRIPolRecordSchema); // 最低分出保额
			if (AccumulateAmnt > riLimit) {
				// 处理存在未分保的大于自留额小于最低分出额的数据情况
				if ((AccumulateAmnt - newAmnt) > riLevel
						&& (AccumulateAmnt - newAmnt) <= riLimit) {
					newAmnt = AccumulateAmnt - riLevel;
				}
				RIRecordTraceSet tRIRecordTraceSet = mRIPrecept.DealAddDiv(
						AccumulateAmnt - newAmnt, newAmnt, aRIIncomeCompanySet,
						aRIDivisionLineDefSet, aRIRiskDivideSet,
						aRIPolRecordSchema);
				for (int i = 1; i <= tRIRecordTraceSet.size(); i++) {
					tRIRecordTraceSet.get(i).setSerialNo(
							mRIPubFun.getCessRecordSerialNo());
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
					tRIRecordTraceSet.get(i).setAddSubFlag("2"); // 保全增额
					tRIRecordTraceSet.get(i).setRIDate(
							aRIPolRecordSchema.getGetDate());
					tRIRecordTraceSet.get(i).setRiskCode(
							aRIPolRecordSchema.getRiskCode());
					tRIRecordTraceSet.get(i).setDutyCode(
							aRIPolRecordSchema.getDutyCode());

					// 获取已保存的分保保额变化量，分保比例
					if (!getRecord(tRIRecordTraceSet.get(i))) {
						return false;
					}
					double addAmntCessionRate = tRIRecordTraceSet.get(i)
							.getAmountChang()
							/ aRIPolRecordSchema.getChRiskAmnt(); // 增额部分分保比例
					// 分保保额
					tRIRecordTraceSet.get(i).setCessionAmount(
							mCessAmnt + aRIPolRecordSchema.getRiskAmnt()
									* addAmntCessionRate);// 过去的分保保额加上增额部分分保保额
					// 分保比例
					tRIRecordTraceSet.get(i).setCessionRate(
							tRIRecordTraceSet.get(i).getCessionAmount()
									/ tRIRecordTraceSet.get(i).getCurentAmnt());

					// 保存新分保保额分保比例到内存
					if (!setRecord(tRIRecordTraceSet.get(i))) {
						return false;
					}
				}
				if (tRIRecordTraceSet.size() > 0) {
					mMap = new MMap();
					mMap.put(tRIRecordTraceSet, "INSERT");
				}
				mVData.clear();
				mVData.add(mMap);
			}
			return true;
		} catch (Exception ex) {
			buildError("DealEdorNI", ex.getMessage());
			return false;
		}
	}

	/**
	 * 保全退保
	 * 
	 * @param tAccumulateDefDeal
	 *            AccumulateDefDeal
	 * @param tAccumulateDef
	 *            AccumulateDef
	 * @param tRIPrecept
	 *            RIPrecept
	 * @param tRICalItem
	 *            RICalItem
	 * @param tRIPolRecordSchema
	 *            RIPolRecordSchema
	 * @param tAccumulateRecordTraceSchema
	 *            AccumulateRecordTraceSchema
	 * @return boolean
	 */
	private boolean DealEdorTB(RIPolRecordSchema aRIPolRecordSchema,
			RIIncomeCompanySet aRIIncomeCompanySet,
			RIDivisionLineDefSet aRIDivisionLineDefSet,
			RIRiskDivideSet aRIRiskDivideSet) {
		mRIPrecept = new RIPrecept();
		RIRecordTraceTempSet tRIRecordTraceTempSet = mRIPrecept.DealAddDiv(
				aRIIncomeCompanySet, aRIDivisionLineDefSet, aRIRiskDivideSet);
		for (int i = 1; i <= tRIRecordTraceTempSet.size(); i++) {
			tRIRecordTraceTempSet.get(i).setSerialNo(
					mRIPubFun.getCessRecordSerialNo());
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
			tRIRecordTraceTempSet.get(i).setCurentAmnt(0); // 退保应该为0
			tRIRecordTraceTempSet.get(i).setCessionAmount(0);

			if (mTBFlag.equals("1")) {
				tRIRecordTraceTempSet.get(i).setAddSubFlag("5"); // 由减额引起的退保状态
			} else {
				tRIRecordTraceTempSet.get(i).setAddSubFlag("4"); // 保全退保
			}
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
			// 分保变化量
			tRIRecordTraceTempSet.get(i).setAmountChang(-mCessAmnt);
			tRIRecordTraceTempSet.get(i).setCessionRate(mCessRate);
			tRIRecordTraceSchema = new RIRecordTraceSchema();
			tReflections.transFields(tRIRecordTraceSchema,
					tRIRecordTraceTempSet.get(i));
			// 保存新分保保额分保比例到内存
			if (!setRecord(tRIRecordTraceSchema)) {
				return false;
			}
		}
		mTBFlag = "0";
		if (tRIRecordTraceTempSet.size() > 0) {
			mMap = new MMap();
			mMap.put(tRIRecordTraceTempSet, "INSERT");
		}
		mVData.clear();
		mVData.add(mMap);

		return true;
	}

	/**
	 * 保全减费
	 * 
	 * @param tAccumulateDefDeal
	 *            AccumulateDefDeal
	 * @param tAccumulateDef
	 *            AccumulateDef
	 * @param tRIPrecept
	 *            RIPrecept
	 * @param tRICalItem
	 *            RICalItem
	 * @param tRIPolRecordSchema
	 *            RIPolRecordSchema
	 * @param tAccumulateRecordTraceSchema
	 *            AccumulateRecordTraceSchema
	 * @return boolean
	 */
	private boolean DealEdorDelAmnt(RIPolRecordSchema aRIPolRecordSchema,
			RIIncomeCompanySet aRIIncomeCompanySet,
			RIDivisionLineDefSet aRIDivisionLineDefSet,
			RIRiskDivideSet aRIRiskDivideSet) {
		try {
			// if(aRIPolRecordSchema.getEventNo().equals("00000000000000003345")||aRIPolRecordSchema.getEventNo().equals("00000000000000003343")){
			// System.out.println(aRIPolRecordSchema.getEventNo());
			// }
			mRIPrecept = new RIPrecept();
			// double riLevel =
			// mRIPrecept.getLevelone(aRIDivisionLineDefSet,aRIPolRecordSchema);
			double riLimit = mRIPrecept.getRILimit(aRIDivisionLineDefSet,
					aRIPolRecordSchema);
			double AccumulateAmnt = aRIPolRecordSchema.getAccAmnt(); // 本系统的累计风险保额定义为包括：历史与本单的风险保额
			double newAmnt = aRIPolRecordSchema.getRiskAmnt()
					- aRIPolRecordSchema.getPreRiskAmnt();
			// 如果减额前已经分保且当年度的风险保额*分保比例<1万，按退保计算
			// if((AccumulateAmnt-newAmnt) > riLimit &&
			// eqTB(aRIPolRecordSchema,aRIIncomeCompanySet,aRIDivisionLineDefSet,aRIRiskDivideSet)){
			// mTBFlag="1";
			// if(!DealEdorTB(aRIPolRecordSchema,aRIIncomeCompanySet,aRIDivisionLineDefSet,aRIRiskDivideSet)){
			// buildError("dealData","保全减额当做退保处理时出错"+mErrors.getFirstError());
			// return false;
			// }
			// return true;
			// }
			RIRecordTraceTempSet tRIRecordTraceTempSet = mRIPrecept.DealAddDiv(
					aRIIncomeCompanySet, aRIDivisionLineDefSet,
					aRIRiskDivideSet);
			for (int i = 1; i <= tRIRecordTraceTempSet.size(); i++) {
				tRIRecordTraceTempSet.get(i).setSerialNo(
						mRIPubFun.getCessRecordSerialNo());
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
				tRIRecordTraceTempSet.get(i).setAddSubFlag("3"); // 保全退保
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
				// 分保变化量
				tRIRecordTraceTempSet.get(i).setAmountChang(
						aRIPolRecordSchema.getRiskAmnt() * mCessRate
								- mCessAmnt);
				tRIRecordTraceTempSet.get(i).setCessionAmount(
						aRIPolRecordSchema.getRiskAmnt() * mCessRate);
				tRIRecordTraceTempSet.get(i).setCessionRate(mCessRate);
				tRIRecordTraceSchema = new RIRecordTraceSchema();
				tReflections.transFields(tRIRecordTraceSchema,
						tRIRecordTraceTempSet.get(i));
				// 保存新分保保额分保比例到内存
				if (!setRecord(tRIRecordTraceSchema)) {
					return false;
				}
			}
			if (tRIRecordTraceTempSet.size() > 0) {
				mMap = new MMap();
				mMap.put(tRIRecordTraceTempSet, "INSERT");
			}
			mVData.clear();
			mVData.add(mMap);
			return true;

		} catch (Exception ex) {
			buildError("dealData", "生成续期分保数据时出错" + ex);
			return false;
		}
	}

	/**
	 * 判断是否需要当做退保处理
	 * 
	 * @param aRIPolRecordSchema
	 * @param aRIIncomeCompanySet
	 * @param aRIDivisionLineDefSet
	 * @param aRIRiskDivideSet
	 * @return
	 */
	private boolean eqTB(RIPolRecordSchema aRIPolRecordSchema,
			RIIncomeCompanySet aRIIncomeCompanySet,
			RIDivisionLineDefSet aRIDivisionLineDefSet,
			RIRiskDivideSet aRIRiskDivideSet) {

		RIRecordTraceTempSet tRIRecordTraceTempSet = mRIPrecept.DealAddDiv(
				aRIIncomeCompanySet, aRIDivisionLineDefSet, aRIRiskDivideSet);
		double tCessRate = 0;
		for (int i = 1; i <= tRIRecordTraceTempSet.size(); i++) {
			tRIRecordTraceTempSet.get(i).setSerialNo(
					mRIPubFun.getCessRecordSerialNo());
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
			tRIRecordTraceTempSet.get(i).setAddSubFlag("3"); // 保全退保
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
			// 累计区域分保比例
			tCessRate += mCessRate;
		}
		// 当年度的风险保额*分保比例<1万时则同退保做法
		if (tCessRate > 0
				&& aRIPolRecordSchema.getRiskAmnt() * tCessRate < 10000) {
			return true;
		}
		return false;
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
			if (RIPubFun.compare(tRIRecordSet.get(i), riRecordTraceSchema)) {

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

	/**
	 * 查询历史分保保费和分保佣金
	 * 
	 * @return
	 */
	private boolean getCessFeeAndCessComm(
			RIRecordTraceTempSchema riRecordTraceTempSchema, int insuredYear) {
		try {
			ExeSQL tExeSQL = new ExeSQL();
			StringBuffer strBuffer = new StringBuffer();
			strBuffer
					.append("select sum(a.PremChang) ,sum(a.CommChang) from rirecordtrace a where a.accumulatedefno='");
			strBuffer.append(riRecordTraceTempSchema.getAccumulateDefNO());
			strBuffer.append("' and a.ripreceptno='");
			strBuffer.append(riRecordTraceTempSchema.getRIPreceptNo());
			strBuffer.append("' and a.otherno='");
			strBuffer.append(riRecordTraceTempSchema.getOtherNo());
			strBuffer.append("' and a.areaid = '");
			strBuffer.append(riRecordTraceTempSchema.getAreaID());
			strBuffer.append("' and a.contno='");
			strBuffer.append(riRecordTraceTempSchema.getContNo());
			strBuffer.append("' and a.RiskCode='");
			strBuffer.append(riRecordTraceTempSchema.getRiskCode());
			strBuffer.append("' and a.DutyCode='");
			strBuffer.append(riRecordTraceTempSchema.getDutyCode());
			strBuffer.append("' and a.eventtype in ('01','02','03') ");
			strBuffer
					.append(" and a.ridade >=(select max(ridate) from rirecordtrace where ");
			strBuffer.append("' and otherno='");
			strBuffer.append(riRecordTraceTempSchema.getOtherNo());
			strBuffer.append("' and areaid = '");
			strBuffer.append(riRecordTraceTempSchema.getAreaID());
			strBuffer.append("' and contno='");
			strBuffer.append(riRecordTraceTempSchema.getContNo());
			strBuffer.append("' and RiskCode='");
			strBuffer.append(riRecordTraceTempSchema.getRiskCode());
			strBuffer.append("' and DutyCode='");
			strBuffer.append(riRecordTraceTempSchema.getDutyCode());
			strBuffer.append("' and eventtype = ");
			if (insuredYear == 1) {
				strBuffer.append("'01'");
			} else {
				strBuffer.append("'02'");
			}
			strBuffer.append("')");

			System.out.println(" 查询历史分保保费、佣金： " + strBuffer.toString());

			SSRS tSSRS = tExeSQL.execSQL(strBuffer.toString());
			if (tExeSQL.mErrors.needDealError()) {
				buildError("getCessFeeAndCessComm", " 取得历史分保保费、佣金出错!! ");
				return false;
			}
			if (tSSRS.MaxRow > 0) {
				riRecordTraceTempSchema.setPremChang(Double.parseDouble(tSSRS
						.GetText(1, 1)));
				riRecordTraceTempSchema.setCommChang(Double.parseDouble(tSSRS
						.GetText(1, 2)));
			}
		} catch (Exception ex) {
			buildError("getCessFeeAndCessComm",
					" 取得历史分保保费、佣金出错 " + ex.getMessage());
			return false;
		}

		return true;
	}

	public VData getValue() {
		return mVData;
	}

	public CErrors getCErrors() {
		System.out.println(" mErrors: " + mErrors.getFirstError());
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

	public static void main(String[] args) {
		RIRecordTraceDB riRecordTraceDB = new RIRecordTraceDB();
		riRecordTraceDB.setSerialNo("00000000000000000027");
		// if(!riRecordTraceDB.getInfo()){
		// System.out.println(" fail ");
		// }
		// RIRecordTraceSchema riRecordTraceSchema =
		// riRecordTraceDB.getSchema();
		// RIStandEdorProcess tRIStandEdorProcess = new RIStandEdorProcess();
		// tRIStandEdorProcess.getAmntChagAndCessRate(riRecordTraceSchema);
		RIRecordSchema tRIRecordSchema = new RIRecordSchema();
		System.out.println(" aa: " + tRIRecordSchema.getCessionAmount());

	}
}
