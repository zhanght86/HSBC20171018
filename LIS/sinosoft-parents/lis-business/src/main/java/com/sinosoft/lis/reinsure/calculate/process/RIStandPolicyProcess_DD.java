

package com.sinosoft.lis.reinsure.calculate.process;

import com.sinosoft.lis.db.RIPolRecordDB;
import com.sinosoft.lis.db.RIUnionAccumulateDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.reinsure.tools.RIPubFun;
import com.sinosoft.lis.schema.RIPolRecordSchema;
import com.sinosoft.lis.schema.RIRecordSchema;
import com.sinosoft.lis.schema.RIRecordTraceSchema;
import com.sinosoft.lis.vschema.RIDivisionLineDefSet;
import com.sinosoft.lis.vschema.RIIncomeCompanySet;
import com.sinosoft.lis.vschema.RIPolRecordSet;
import com.sinosoft.lis.vschema.RIRecordTraceSet;
import com.sinosoft.lis.vschema.RIRiskDivideSet;
import com.sinosoft.lis.vschema.RIUnionAccumulateSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 重疾险种累计超过150万的该临分特殊处理
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
public class RIStandPolicyProcess_DD implements RIProcessType {
	public CErrors mErrors = new CErrors();
	private VData mVData = new VData();
	private MMap mMap;

	public RIStandPolicyProcess_DD() {
	}

	public boolean dealData(RIPolRecordSchema aRIPolRecordSchema,
			RIIncomeCompanySet aRIIncomeCompanySet,
			RIDivisionLineDefSet aRIDivisionLineDefSet,
			RIRiskDivideSet aRIRiskDivideSet) {
		try {

			mVData.clear();
			mMap = new MMap();
			RIPrecept tRIPrecept = new RIPrecept();
			// 本系统的累计风险保额定义为包括：历史与本单的风险保额、其它重疾累计风险保额
			double AccumulateAmnt = aRIPolRecordSchema.getAccAmnt()
					+ getAccAmnt(aRIPolRecordSchema);

			double newAmnt = aRIPolRecordSchema.getRiskAmnt();
			double riLevel = tRIPrecept.getLevelone(aRIDivisionLineDefSet,
					aRIPolRecordSchema);
			double riLimit = tRIPrecept.getRILimit(aRIDivisionLineDefSet,
					aRIPolRecordSchema);
			if (riLevel == -1 || riLimit == -1) {
				// 计算动态限额系数时再保接口表对应字段为空(只能校验字符类型的字段)时返回-1
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
	 * 获取非DD（L000000009）的重疾险种累计风险保额
	 * 目前有：L000000001、L000000010、L000000011、L000000012
	 * 、L000000015、L000000016、L000000017
	 * 
	 * @return
	 */

	private double getAccAmnt(RIPolRecordSchema aRIPolRecordSchema) {
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		double accAmnt = 0;
		StringBuffer strBuffer;

		/**************************************
		 * 查询历史累计风险保额
		 ***************************************/
		strBuffer = new StringBuffer();
		strBuffer
				.append(" select nvl(sum(a.accamnt),0) from ripolrecordbake a where a.eventno in");
		strBuffer
				.append("(select max(b.eventno) from ripolrecordbake b where  b.insuredno='");
		strBuffer.append(aRIPolRecordSchema.getInsuredNo());
		strBuffer
				.append("' b.AccumulateDefNO in( 'L000000001','L000000010','L000000011','L000000012','L000000015','L000000016','L000000017')");
		strBuffer
				.append(" and b.EventType <> '04' and b.Getdate=(select max(c.Getdate) from ripolrecordbake c where c.InsuredNo= '");
		strBuffer.append(aRIPolRecordSchema.getInsuredNo());
		strBuffer
				.append("' and c.AccumulateDefNO=b.AccumulateDefNO and c.EventType <> '04' and c.getdate<='");
		strBuffer.append(aRIPolRecordSchema.getGetDate());
		strBuffer.append("') group by b.AccumulateDefNO");

		System.out.println(" cal accAmnt: " + strBuffer.toString());

		tSSRS = tExeSQL.execSQL(strBuffer.toString());
		// 计算历史累计风险保额
		accAmnt = (Double.parseDouble(tSSRS.GetText(1, 1)));

		/***************************************
		 * 累计本批次风险保额
		 ***************************************/
		strBuffer = new StringBuffer();
		strBuffer
				.append("select * from ripolrecord a where a.AccumulateDefNO in( 'L000000001','L000000010','L000000011','L000000012','L000000015','L000000016','L000000017')");
		strBuffer.append(" and a.getdate<='");
		strBuffer.append(aRIPolRecordSchema.getGetDate());
		strBuffer.append("' and a.InsuredNo= '");
		strBuffer.append(aRIPolRecordSchema.getInsuredNo());
		strBuffer.append("' order by a.AccumulateDefNO, a.getdate ");

		RIPolRecordDB tRIPolRecordDB = new RIPolRecordDB();
		RIPolRecordSet newRIPolRecordSet = tRIPolRecordDB
				.executeQuery(strBuffer.toString());
		if (tRIPolRecordDB.mErrors.needDealError()) {

			return accAmnt;
		}
		if (newRIPolRecordSet.size() == 0) {
			return accAmnt;
		}

		RIUnionAccumulateSet tRIUnionAccumulateSet = null;
		TransferData tTransferData = null;
		boolean unionAccFlag = false;
		String accumulateDefNO = "";

		for (int i = 1; i <= newRIPolRecordSet.size(); i++) {
			// 计算风险保额标记
			boolean calflag = true;
			// 联合累计编号，如果险种不属于联合累计风险中的险种
			String unionaccno = "";

			if (!newRIPolRecordSet.get(i).getAccumulateDefNO()
					.equals(accumulateDefNO)) {
				// 获取联合累计
				tRIUnionAccumulateSet = getUnionAccumulate(newRIPolRecordSet
						.get(i).getAccumulateDefNO());
				tTransferData = new TransferData();

				if (tRIUnionAccumulateSet != null
						&& tRIUnionAccumulateSet.size() > 0) {
					// 有联合风险累计
					unionAccFlag = true;
				}
			}

			if (unionAccFlag) {
				// 有联合累计风险定义
				for (int j = 1; j <= tRIUnionAccumulateSet.size(); j++) {
					if (tRIUnionAccumulateSet.get(j).getAssociatedCode()
							.equals(newRIPolRecordSet.get(i).getRiskCode())) {
						// 如果当前险种属于联合累计风险定义中的险种，取得联合累计编号
						unionaccno = tRIUnionAccumulateSet.get(j)
								.getUnionAccNo();
						for (int k = 1; k <= tRIUnionAccumulateSet.size(); k++) {
							if (tRIUnionAccumulateSet.get(k).getUnionAccNo()
									.equals(unionaccno)
									&& !tRIUnionAccumulateSet
											.get(k)
											.getAssociatedCode()
											.equals(newRIPolRecordSet.get(i)
													.getRiskCode())) {
								// 用contno+联合累计风险定义中不等于当前险种的险种编码+insuredno到容器中取得累计风险保额
								String unionkey = newRIPolRecordSet.get(i)
										.getContNo()
										+ tRIUnionAccumulateSet.get(k)
												.getAssociatedCode()
										+ newRIPolRecordSet.get(i)
												.getInsuredNo();
								if (tTransferData.getValueByName(unionkey) != null) {
									newRIPolRecordSet.get(i).setAccAmnt(
											((Double) tTransferData
													.getValueByName(unionkey))
													.doubleValue());
									// 不需要计算风险保额
									calflag = false;
									break;
								}
							}
						}
						break;
					}
				}
			}
			if (!newRIPolRecordSet.get(i).getEventType().equals("04")
					&& calflag == true) {
				System.out.println(" eventno: "
						+ newRIPolRecordSet.get(i).getEventNo());

				double amountChang = 0;
				if (newRIPolRecordSet.get(i).getEventType().equals("03")
						|| newRIPolRecordSet.get(i).getEventType().equals("05")) {
					// 如果是保全，将当前风险保额变化量累计进去
					amountChang = newRIPolRecordSet.get(i).getRiskAmnt()
							- newRIPolRecordSet.get(i).getPreRiskAmnt();

				} else if (newRIPolRecordSet.get(i).getEventType().equals("02")) {
					// 如果是续期，历史累计风险保额就是当前累计风险保额
					amountChang = newRIPolRecordSet.get(i).getRiskAmnt()
							- newRIPolRecordSet.get(i).getPreRiskAmnt();

				} else {
					// 如果是新单将当前风险保额累计进去
					amountChang = newRIPolRecordSet.get(i).getRiskAmnt();
				}
				accAmnt += amountChang;
			}
		}
		return accAmnt;
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

	/**
	 * 获取联合累计
	 * 
	 * @param accumulateDefNo
	 * @return
	 */
	private RIUnionAccumulateSet getUnionAccumulate(String accumulateDefNo) {
		RIUnionAccumulateSet tRIUnionAccumulateSet = new RIUnionAccumulateSet();
		RIUnionAccumulateDB tRIUnionAccumulateDB = new RIUnionAccumulateDB();

		tRIUnionAccumulateDB.setAccumulateDefNO(accumulateDefNo);
		tRIUnionAccumulateSet = tRIUnionAccumulateDB.query();
		if (tRIUnionAccumulateDB.mErrors.needDealError()) {
			buildError("InitInfo", "初始化 " + accumulateDefNo + " 的联合累计风险出错");
		}
		return tRIUnionAccumulateSet;

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
