

package com.sinosoft.lis.reinsure.calculate.param;

import com.sinosoft.lis.reinsure.calculate.feerate.RIFeeTableSet;
import com.sinosoft.lis.reinsure.calculate.init.RIInitData;
import com.sinosoft.lis.reinsure.calculate.init.RIInitSplitSeg;
import com.sinosoft.lis.reinsure.calculate.manage.RICalMan;
import com.sinosoft.lis.reinsure.tools.RIPubFun;
import com.sinosoft.lis.schema.RIWFLogSchema;
import com.sinosoft.lis.vschema.RIAssociateFeeTableSet;
import com.sinosoft.lis.vschema.RIItemCalSet;
import com.sinosoft.lis.vschema.RIPreceptSet;
import com.sinosoft.lis.vschema.RIRiskDivideSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
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
 * @version 1.0
 */
public class RIInitParam implements RICalMan {
	public CErrors mErrors = new CErrors();
	private RIInitData mRIInitData;
	private String mAccumulateDefNo = "";
	private RIWFLogSchema mRIWFLogSchema;
	private RIInitSplitSeg mRIInitSplitSeg;
	private String mEventType;
	private String[][] mSeg;
	private RIPubFun mRIPubFun = new RIPubFun();

	public RIInitParam() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		if (!init()) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		// 记录工作流程日志
		if (!recordLog()) {
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData, String cOperate) {
		mEventType = cOperate;
		mRIWFLogSchema = (RIWFLogSchema) cInputData.getObjectByObjectName(
				"RIWFLogSchema", 0);
		mAccumulateDefNo = mRIWFLogSchema.getTaskCode();
		return true;
	}

	private boolean init() {
		// 查询批次下的该累计风险的EVENTNO的最大值和最小值，然后分割条数，分批进行处理。
		try {
			mRIInitData = RIInitData.getObject(mAccumulateDefNo);
			mRIInitSplitSeg = RIInitSplitSeg.getObject(mRIWFLogSchema);
			mSeg = mRIInitSplitSeg.getValue();
		} catch (Exception ex) {
			buildError("initInfo", " 生成计算参数记录时，初始化参数失败: " + ex);
			return false;
		}
		return true;
	}

	private boolean dealData() {
		if (!buildParam()) {
			return false;
		}
		if (!makeParam()) {
			return false;
		}
		return true;
	}

	/**
	 * 生成分保记录参数表
	 * 
	 * @return boolean
	 */
	private boolean buildParam() {
		for (int i = 0; i < mSeg.length; i++) {
			String tSql = " insert into ricalparam select a.SerialNo,a.EventNo,a.BatchNo,a.OtherNo,a.ContNo,a.RiskCode,a.DutyCode,a.AreaID,a.AccumulateDefNO,a.RIPreceptNo,0,0,0,0,0,0,0,0,0,0,null,null,null,null,null,null,null,null,null from RIRecordTrace a where a.AccumulateDefNO='"
					+ mAccumulateDefNo
					+ "' and a.BatchNo='"
					+ mRIWFLogSchema.getBatchNo()
					+ "' and not exists (select * from ricalparam t where t.serialno=a.serialno and t.batchno=a.batchno ) and a.EventType='"
					+ mEventType
					+ "' and a.eventno between '"
					+ mSeg[i][0]
					+ "' and '" + mSeg[i][1] + "' ";
			// StringBuffer buf = new StringBuffer();

			if (!execUpdateSQL(tSql)) {
				buildError("RIDataMake", " 生成累计风险：" + mAccumulateDefNo
						+ "分保参数表记录出错。" + mErrors.getFirstError());
				return false;
			}
		}
		return true;
	}

	/**
	 * 初始化分保参数，（调试用）
	 * 
	 * @return boolean
	 */
	private boolean makeParam(int a) {
		RIPreceptSet tRIPreceptSet = mRIInitData.getRIPreceptSet();
		for (int i = 1; i <= tRIPreceptSet.size(); i++) {
			RIItemCalSet tRIItemCalSet = (RIItemCalSet) mRIInitData
					.getRIParamCalTD().getValueByName(
							tRIPreceptSet.get(i).getRIPreceptNo());
			RIItemCalSet riItemCalSet = new RIItemCalSet();
			for (int j = 1; j <= tRIItemCalSet.size(); j++) {
				if (tRIItemCalSet.get(j).getCalItemType().equals(mEventType)) {
					riItemCalSet.add(tRIItemCalSet.get(j));
				}
			}
			if (!makeEachParam(riItemCalSet, tRIPreceptSet.get(i)
					.getRIPreceptNo())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 初始化分保参数
	 * 
	 * @return boolean
	 */
	private boolean makeParam() {
		RIPreceptSet tRIPreceptSet = mRIInitData.getRIPreceptSet();
		for (int i = 1; i <= tRIPreceptSet.size(); i++) {
			// 得到再保方案下的费率表批次号
			String[][] tFeeTableBatch = (String[][]) mRIInitData
					.getRIFeeRateTD().getValueByName(
							tRIPreceptSet.get(i).getRIPreceptNo());
			// 得到再保方案下的费率表算法
			RIItemCalSet tRIItemCalSet = (RIItemCalSet) mRIInitData
					.getRIParamCalTD().getValueByName(
							tRIPreceptSet.get(i).getRIPreceptNo());
			// 查询需要处理的业务类型的算法
			RIItemCalSet typeRIItemCalSet = new RIItemCalSet();
			for (int j = 1; j <= tRIItemCalSet.size(); j++) {
				if (tRIItemCalSet.get(j).getCalItemType().equals(mEventType)) {
					typeRIItemCalSet.add(tRIItemCalSet.get(j));
				}
			}
			RIItemCalSet feeItemCalSet = new RIItemCalSet();
			RIItemCalSet nofeeItemCalSet = new RIItemCalSet();
			// 区分费率表算法和非费率表算法
			for (int j = 1; j <= typeRIItemCalSet.size(); j++) {
				if (typeRIItemCalSet.get(j).getReMark() != null
						&& !typeRIItemCalSet.get(j).getReMark().equals("")
						&& typeRIItemCalSet.get(j).getReMark().toLowerCase()
								.equals("feetable")) {
					feeItemCalSet.add(typeRIItemCalSet.get(j));
				} else {
					nofeeItemCalSet.add(typeRIItemCalSet.get(j));
				}
			}
			/**** 调试语句 ****/
			// for(int j=1;j<=feeItemCalSet.size();j++){
			// System.out.println(" ArithmeticID: "+feeItemCalSet.get(j).getArithmeticID());
			// System.out.println(" Order : "+feeItemCalSet.get(j).getCalItemOrder());
			// System.out.println(" feetable: "+feeItemCalSet.get(j).getTarGetClumn());
			// System.out.println(" ReMark: "+feeItemCalSet.get(j).getReMark());
			// }
			// for(int j=1;j<=nofeeItemCalSet.size();j++){
			// System.out.println(" ArithmeticID: "+nofeeItemCalSet.get(j).getArithmeticID());
			// System.out.println(" Order : "+feeItemCalSet.get(j).getCalItemOrder());
			// System.out.println(" feetable: "+nofeeItemCalSet.get(j).getTarGetClumn());
			// System.out.println(" ReMark: "+nofeeItemCalSet.get(j).getReMark());
			// }
			/****************/

			// 置费率表参数
			if (!mEventType.equals("04") && !mEventType.equals("11")) {
				if (!makeFeeParam(feeItemCalSet, tFeeTableBatch, tRIPreceptSet
						.get(i).getRIPreceptNo())) {
					return false;
				}
			}
			// 置普通参数
			if (!makeEachParam(nofeeItemCalSet, tRIPreceptSet.get(i)
					.getRIPreceptNo())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param riItemCalSet
	 *            RIItemCalSet
	 * @param riPreceptNo
	 *            String
	 * @return boolean
	 */
	private boolean makeFeeParam(RIItemCalSet riItemCalSet,
			String[][] tFeeTableBatch, String riPreceptNo) {
		// FeeTableBatch 字段顺序：费率表编号，费率表批次，费率表类型，起始日期，终止日期
		RIRiskDivideSet tRIRiskDivideSet = (RIRiskDivideSet) mRIInitData
				.getRIRiskDivideTD().getValueByName(riPreceptNo);
		RIAssociateFeeTableSet tRIAssociateFeeTableSet = (RIAssociateFeeTableSet) mRIInitData
				.getAssociateFeeTableTD().getValueByName(riPreceptNo);
		RIPubFun tRIPubFun = new RIPubFun();
		StringBuffer strBuffer;
		try {
			boolean existFeeTableFlag;
			String param = "";
			for (int i = 1; i <= tRIRiskDivideSet.size(); i++) {
				// 如果分保区域中配置了分保费率表
				if (tRIRiskDivideSet.get(i).getPremFeeTableNo() != null
						&& !tRIRiskDivideSet.get(i).getPremFeeTableNo()
								.equals("")) {
					// 对所有费率表批次循环
					for (int j = 0; j < tFeeTableBatch.length; j++) {
						RIFeeTableSet tRIFeeTableSet = new RIFeeTableSet(
								mAccumulateDefNo);
						if (tRIRiskDivideSet.get(i).getPremFeeTableNo()
								.equals(tFeeTableBatch[j][0])) {
							existFeeTableFlag = false;
							String check = "";
							for (int m = 1; m <= riItemCalSet.size(); m++) { // 循环判断费率表是否匹配
								check = riItemCalSet.get(m).getCalItemType()
										+ "   "
										+ mEventType
										+ " ,  "
										+ riItemCalSet.get(m).getReMark()
										+ "   "
										+ "feetable"
										+ " ,  "
										+ riItemCalSet.get(m)
												.getStandbyString1() + "   "
										+ tRIRiskDivideSet.get(i).getAreaID()
										+ " ,   "
										+ riItemCalSet.get(m).getCalItemID()
										+ "   " + "feerate";
								System.out.println(" check : " + check);
								// 如果费率表匹配
								if (riItemCalSet.get(m).getCalItemType()
										.toLowerCase()
										.equals(mEventType.toLowerCase())
										&& riItemCalSet
												.get(m)
												.getReMark()
												.toLowerCase()
												.equals("feetable"
														.toLowerCase())
										&& riItemCalSet
												.get(m)
												.getStandbyString1()
												.toLowerCase()
												.equals(tRIRiskDivideSet.get(i)
														.getAreaID()
														.toLowerCase())
										&& riItemCalSet
												.get(m)
												.getCalItemID()
												.toLowerCase()
												.equals("feerate".toLowerCase())) {
									param = riItemCalSet.get(m)
											.getTarGetClumn();
									existFeeTableFlag = true;
									break;
								}
							}
							if (!existFeeTableFlag) {
								buildError("RIDataMake",
										" 初始化参数失败。不能得到分保费率保存的字段名。再保方案号： "
												+ riPreceptNo + " 费率表名："
												+ tFeeTableBatch[0][0]
												+ " 费率表批次号："
												+ tFeeTableBatch[0][1]);
								return false;
							}
							// 分保费率
							for (int k = 0; k < mSeg.length; k++) {
								strBuffer = new StringBuffer();
								strBuffer.append(" update RICalParam a set a."
										+ param + " = nvl((");
								strBuffer.append(tRIFeeTableSet.getRateSql(
										tFeeTableBatch[j][0], // 费率表编号
										tFeeTableBatch[j][1] // 费率表批次号
										));
								strBuffer.append("),0) where a.BatchNo='");
								strBuffer.append(mRIWFLogSchema.getBatchNo());
								strBuffer.append("' and a.AccumulateDefNO='");
								strBuffer.append(mRIWFLogSchema.getTaskCode());
								strBuffer.append("' and a.EventNo between '");
								strBuffer.append(mSeg[k][0]);
								strBuffer.append("' and '");
								strBuffer.append(mSeg[k][1]);
								strBuffer.append("' and a.RIPreceptNo = '");
								strBuffer.append(riPreceptNo);
								strBuffer
										.append("' and exists (select * from ripolrecord r where r.eventtype='");
								strBuffer.append(mEventType);
								strBuffer.append("' and a.EventNo=r.EventNo) ");
								strBuffer.append(" and a.Areaid ='");
								strBuffer.append(tRIRiskDivideSet.get(i)
										.getAreaID());
								// 选择哪个日期来匹配费率表批次的日期
								strBuffer
										.append("' and (select c."
												+ tFeeTableBatch[j][5]
												+ " from ripolrecord c where c.eventno = a.eventno) between '");
								strBuffer.append(tFeeTableBatch[j][3]);
								strBuffer.append("' and '");
								if (tFeeTableBatch[j][4] == null
										|| tFeeTableBatch[j][4].equals("")) {
									strBuffer.append("3000-1-1");
								} else {
									strBuffer.append(tFeeTableBatch[j][4]);
								}
								strBuffer.append("'");
								System.out.println(" param sql: " + strBuffer);
								// mRIPubFun.recordLog(mRIWFLogSchema,"分保计算参数之分保费率表查询sql: "+strBuffer.toString(),"01");
								if (!execUpdateSQL(strBuffer.toString())) {
									buildError(
											"RIDataMake",
											" 初始化累计风险为："
													+ mRIWFLogSchema
															.getTaskCode()
													+ "的参数时出错:"
													+ this.mErrors
															.getFirstError());
									return false;
								}
							}
						}
					}
				}
				// 如果分保区域中配置了分保佣金费率表
				if (tRIRiskDivideSet.get(i).getComFeeTableNo() != null
						&& !tRIRiskDivideSet.get(i).getComFeeTableNo()
								.equals("")) {
					// 对所有费率表批次循环
					for (int j = 0; j < tFeeTableBatch.length; j++) {
						RIFeeTableSet tRIFeeTableSet = new RIFeeTableSet(
								mAccumulateDefNo);
						if (tRIRiskDivideSet.get(i).getComFeeTableNo()
								.equals(tFeeTableBatch[j][0])) {
							existFeeTableFlag = false;
							for (int m = 1; m <= riItemCalSet.size(); m++) {
								if (riItemCalSet.get(m).getCalItemType()
										.equals(mEventType)
										&& riItemCalSet.get(m).getReMark()
												.equals("feetable")
										&& riItemCalSet
												.get(m)
												.getStandbyString1()
												.equals(tRIRiskDivideSet.get(i)
														.getAreaID())
										&& riItemCalSet.get(m).getCalItemID()
												.equals("commrate")) {
									param = riItemCalSet.get(m)
											.getTarGetClumn();
									existFeeTableFlag = true;
								}
							}
							if (!existFeeTableFlag) {
								buildError("RIDataMake",
										" 初始化参数失败。不能得到分保佣金费率保存的字段名。再保方案号： "
												+ riPreceptNo + " 费率表名："
												+ tFeeTableBatch[0][0]
												+ " 费率表批次号："
												+ tFeeTableBatch[0][1]);
								return false;
							}
							// 分保佣金率
							for (int k = 0; k < mSeg.length; k++) {
								strBuffer = new StringBuffer();
								strBuffer.append(" update RICalParam a set "
										+ param + " = nvl((");
								strBuffer.append(tRIFeeTableSet.getRateSql(
										tFeeTableBatch[j][0],
										tFeeTableBatch[j][1])); // 得到
								strBuffer.append("),0) where a.BatchNo='");
								strBuffer.append(mRIWFLogSchema.getBatchNo());
								strBuffer.append("' and a.AccumulateDefNO='");
								strBuffer.append(mRIWFLogSchema.getTaskCode());
								strBuffer.append("' and a.EventNo between '");
								strBuffer.append(mSeg[k][0]);
								strBuffer.append("' and '");
								strBuffer.append(mSeg[k][1]);
								strBuffer.append("' and a.RIPreceptNo = '");
								strBuffer.append(riPreceptNo);
								strBuffer
										.append("' and exists (select * from ripolrecord r where r.eventtype='");
								strBuffer.append(mEventType);
								strBuffer.append("' and a.EventNo=r.EventNo) ");
								strBuffer.append(" and a.Areaid ='");
								strBuffer.append(tRIRiskDivideSet.get(i)
										.getAreaID());
								// 选择哪个日期来匹配费率表批次的日期
								strBuffer
										.append("' and (select c."
												+ tFeeTableBatch[j][5]
												+ " from ripolrecord c where c.eventno = a.eventno) between '");
								strBuffer.append(tFeeTableBatch[j][3]);
								strBuffer.append("' and '");
								if (tFeeTableBatch[j][4] == null
										|| tFeeTableBatch[j][4].equals("")) {
									strBuffer.append("3000-1-1");
								} else {
									strBuffer.append(tFeeTableBatch[j][4]);
								}
								strBuffer.append("'");
								mRIPubFun.recordLog(
										mRIWFLogSchema,
										"分保计算参数之分保佣金费率表查询sql: "
												+ strBuffer.toString(), "01");
								if (!execUpdateSQL(strBuffer.toString())) {
									buildError(
											"RIDataMake",
											" 初始化累计风险为："
													+ mRIWFLogSchema
															.getTaskCode()
													+ "的参数时出错:"
													+ this.mErrors
															.getFirstError());
									return false;
								}
							}
						}
					}
				}
				/** -- 针对累计风险中不同险种适用不同费率表的的情况 -- **/
				for (int k = 0; k < tFeeTableBatch.length; k++) {
					System.out.println("feetableno: " + tFeeTableBatch[k][0]
							+ "  batchno: " + tFeeTableBatch[k][1]);
				}
				RIFeeTableSet tRIFeeTableSet;
				for (int j = 1; j <= tRIAssociateFeeTableSet.size(); j++) {// 对分保方案险种/责任费率进行循环
					// 如果<分保方案险种/责任费率表记录>与当前区域ID匹配,
					// 即：得到属于这个再保方案与区域ID的分保方案险种/责任费率表关联记录
					System.out.println("RIRiskDivide AreaID: "
							+ tRIRiskDivideSet.get(i).getAreaID()
							+ " RIAssociateFee AreaID: "
							+ tRIAssociateFeeTableSet.get(j).getAreaID());
					if (tRIAssociateFeeTableSet.get(j).getRIPreceptNo()
							.equals(tRIRiskDivideSet.get(i).getRIPreceptNo())
							&& tRIAssociateFeeTableSet
									.get(j)
									.getAreaID()
									.equals(tRIRiskDivideSet.get(i).getAreaID())) {
						// 对费率表批次进行循环
						for (int k = 0; k < tFeeTableBatch.length; k++) {
							System.out.println("feetableno: "
									+ tFeeTableBatch[k][0] + "  batchno: "
									+ tFeeTableBatch[k][1]);

							// 如果“分保方案险种/责任费率表”关联记录的分保费率表代码=费率表的代码
							if (tRIAssociateFeeTableSet.get(j)
									.getPremFeeTableNo()
									.equals(tFeeTableBatch[k][0])) { // tFeeTableBatch[k][0]：费率表编码
								existFeeTableFlag = false;
								for (int m = 1; m <= riItemCalSet.size(); m++) {
									String check = riItemCalSet.get(m)
											.getCalItemType()
											+ "   "
											+ mEventType
											+ " ,  "
											+ riItemCalSet.get(m).getReMark()
											+ "   "
											+ "feetable"
											+ " ,  "
											+ riItemCalSet.get(m)
													.getStandbyString1()
											+ "   "
											+ tRIRiskDivideSet.get(i)
													.getAreaID()
											+ " ,   "
											+ riItemCalSet.get(m)
													.getCalItemID()
											+ "   "
											+ "feerate";
									System.out.println(" check : " + check);
									mRIPubFun.recordLog(mRIWFLogSchema,
											"分保计算参数之分保佣金费率表查询sql: " + check,
											"01");
									if (riItemCalSet.get(m).getCalItemType()
											.equals(mEventType)
											&& riItemCalSet.get(m).getReMark()
													.equals("feetable")
											&& riItemCalSet
													.get(m)
													.getStandbyString1()
													.equals(tRIRiskDivideSet
															.get(i).getAreaID())
											&& riItemCalSet.get(m)
													.getCalItemID()
													.equals("feerate")) {
										param = riItemCalSet.get(m)
												.getTarGetClumn();
										existFeeTableFlag = true;
										break;
									}
								}
								if (!existFeeTableFlag) {
									buildError("RIDataMake",
											" 初始化参数失败。不能得到分保佣金费率保存的字段名。再保方案号： "
													+ riPreceptNo + " 费率表名："
													+ tFeeTableBatch[k][0]
													+ " 费率表批次号："
													+ tFeeTableBatch[k][1]);
									return false;
								}
								// 分保费率
								for (int n = 0; n < mSeg.length; n++) {
									tRIFeeTableSet = new RIFeeTableSet(
											mAccumulateDefNo);
									strBuffer = new StringBuffer();
									strBuffer
											.append(" update RICalParam a set "
													+ param + " = nvl((");
									strBuffer.append(tRIFeeTableSet.getRateSql(
											tFeeTableBatch[k][0],
											tFeeTableBatch[k][1]));
									strBuffer.append("),0) where a.BatchNo='");
									strBuffer.append(mRIWFLogSchema
											.getBatchNo());
									strBuffer
											.append("' and a.AccumulateDefNO='");
									strBuffer.append(mRIWFLogSchema
											.getTaskCode());
									strBuffer
											.append("' and a.EventNo between '");
									strBuffer.append(mSeg[n][0]);
									strBuffer.append("' and '");
									strBuffer.append(mSeg[n][1]);
									strBuffer.append("' and a.RIPreceptNo = '");
									strBuffer.append(riPreceptNo);
									strBuffer
											.append("' and exists (select * from ripolrecord r where r.eventtype='");
									strBuffer.append(mEventType);
									strBuffer
											.append("' and a.EventNo=r.EventNo) ");
									strBuffer.append(" and a.Areaid ='");
									strBuffer.append(tRIRiskDivideSet.get(i)
											.getAreaID());
									strBuffer.append("' and a.RiskCode='");
									strBuffer.append(tRIAssociateFeeTableSet
											.get(j).getRiskCode());
									strBuffer.append("' and a.DutyCode='");
									strBuffer.append(tRIAssociateFeeTableSet
											.get(j).getDutyCode());
									// 选择哪个日期来匹配费率表批次的日期
									strBuffer
											.append("' and (select c."
													+ tFeeTableBatch[j][5]
													+ " from ripolrecord c where c.eventno = a.eventno) between '");
									strBuffer.append(tFeeTableBatch[k][3]);
									strBuffer.append("' and '");
									if (tFeeTableBatch[k][4] == null
											|| tFeeTableBatch[k][4].equals("")) {
										strBuffer.append("3000-1-1");
									} else {
										strBuffer.append(tFeeTableBatch[k][4]);
									}
									strBuffer.append("'");
									System.out.println(" 初始化参数(到险种)： "
											+ strBuffer.toString());
									mRIPubFun.recordLog(
											mRIWFLogSchema,
											"分保计算参数之分保佣金费率表查询sql: "
													+ strBuffer.toString(),
											"01");
									if (!execUpdateSQL(strBuffer.toString())) {
										buildError(
												"RIDataMake",
												" 初始化累计风险为："
														+ mRIWFLogSchema
																.getTaskCode()
														+ "的参数时出错:"
														+ this.mErrors
																.getFirstError());
										return false;
									}
								}
							}

							// 如果“分保方案险种/责任费率表”关联记录的佣金率表代码=费率表的代码
							if (tRIAssociateFeeTableSet.get(j)
									.getComFeeTableNo()
									.equals(tFeeTableBatch[k][0])) { // tFeeTableBatch[k][0]：费率表编码
								existFeeTableFlag = false;
								for (int m = 1; m <= riItemCalSet.size(); m++) {
									String check = riItemCalSet.get(m)
											.getCalItemType()
											+ "   "
											+ mEventType
											+ " ,  "
											+ riItemCalSet.get(m).getReMark()
											+ "   "
											+ "feetable"
											+ " ,  "
											+ riItemCalSet.get(m)
													.getStandbyString1()
											+ "   "
											+ tRIRiskDivideSet.get(i)
													.getAreaID()
											+ " ,   "
											+ riItemCalSet.get(m)
													.getCalItemID()
											+ "   "
											+ "feerate";
									System.out.println(" check : " + check);
									mRIPubFun.recordLog(mRIWFLogSchema,
											"分保计算参数之分保佣金费率表查询sql: " + check,
											"01");
									if (riItemCalSet.get(m).getCalItemType()
											.equals(mEventType)
											&& riItemCalSet.get(m).getReMark()
													.equals("feetable")
											&& riItemCalSet
													.get(m)
													.getStandbyString1()
													.equals(tRIRiskDivideSet
															.get(i).getAreaID())
											&& riItemCalSet.get(m)
													.getCalItemID()
													.equals("commrate")) {
										param = riItemCalSet.get(m)
												.getTarGetClumn();
										existFeeTableFlag = true;
										break;
									}
								}
								if (!existFeeTableFlag) {
									buildError("RIDataMake",
											" 初始化参数失败。不能得到分保佣金费率保存的字段名。再保方案号： "
													+ riPreceptNo + " 费率表名："
													+ tFeeTableBatch[0][0]
													+ " 费率表批次号："
													+ tFeeTableBatch[0][1]);
									return false;
								}
								// 分保费率
								for (int n = 0; n < mSeg.length; n++) {
									tRIFeeTableSet = new RIFeeTableSet(
											mAccumulateDefNo);
									strBuffer = new StringBuffer();
									strBuffer
											.append(" update RICalParam a set "
													+ param + " = nvl((");
									strBuffer.append(tRIFeeTableSet.getRateSql(
											tFeeTableBatch[k][0],
											tFeeTableBatch[k][1]));
									strBuffer.append("),0) where a.BatchNo='");
									System.out.println(" aa: "
											+ strBuffer.toString());
									strBuffer.append(mRIWFLogSchema
											.getBatchNo());
									strBuffer
											.append("' and a.AccumulateDefNO='");
									strBuffer.append(mRIWFLogSchema
											.getTaskCode());
									strBuffer
											.append("' and a.EventNo between '");
									strBuffer.append(mSeg[n][0]);
									strBuffer.append("' and '");
									strBuffer.append(mSeg[n][1]);
									strBuffer.append("' and a.RIPreceptNo = '");
									strBuffer.append(riPreceptNo);
									strBuffer
											.append("' and exists (select * from ripolrecord r where r.eventtype='");
									strBuffer.append(mEventType);
									strBuffer
											.append("' and a.EventNo=r.EventNo) ");
									strBuffer.append(" and a.Areaid ='");
									strBuffer.append(tRIRiskDivideSet.get(i)
											.getAreaID());
									strBuffer.append("' and a.AssociateCode='");
									strBuffer.append("' and a.RiskCode='");
									strBuffer.append(tRIAssociateFeeTableSet
											.get(j).getRiskCode());
									strBuffer.append("' and a.DutyCode='");
									strBuffer.append(tRIAssociateFeeTableSet
											.get(j).getDutyCode());
									// 选择哪个日期来匹配费率表批次的日期
									strBuffer
											.append("' and (select c."
													+ tFeeTableBatch[j][5]
													+ " from ripolrecord c where c.eventno = a.eventno) between '");
									strBuffer.append(tFeeTableBatch[k][3]);
									strBuffer.append("' and '");
									if (tFeeTableBatch[k][4] == null
											|| tFeeTableBatch[k][4].equals("")) {
										strBuffer.append("3000-1-1");
									} else {
										strBuffer.append(tFeeTableBatch[k][4]);
									}
									strBuffer.append("'");
									System.out.println(" 初始化参数(到险种)： "
											+ strBuffer.toString());
									mRIPubFun.recordLog(
											mRIWFLogSchema,
											"分保计算参数之分保佣金费率表查询sql: "
													+ strBuffer.toString(),
											"01");
									if (!execUpdateSQL(strBuffer.toString())) {
										buildError(
												"RIDataMake",
												" 初始化累计风险为："
														+ mRIWFLogSchema
																.getTaskCode()
														+ "的参数时出错:"
														+ this.mErrors
																.getFirstError());
										return false;
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			buildError("RIDataMake",
					" 初始化累计风险为：" + mRIWFLogSchema.getTaskCode() + "的参数时出错:"
							+ ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 初始化各分保参数
	 * 
	 * @param riItemCalSet
	 *            RIItemCalSet
	 * @param riPreceptNo
	 *            String
	 * @return boolean
	 */
	private boolean makeEachParam(RIItemCalSet riItemCalSet, String riPreceptNo) {
		StringBuffer strBuffer;
		try {
			for (int i = 1; i <= riItemCalSet.size(); i++) {
				System.out.println(riItemCalSet.get(i).getItemCalType() + "=="
						+ riItemCalSet.get(i).getArithmeticDefID());
				if (mEventType.equals("03")) {
					System.out.println("aaaaaaaaaaaaaa");
				}
				for (int j = 0; j < mSeg.length; j++) {
					strBuffer = new StringBuffer();
					strBuffer.append("update RICalParam a set ");
					if (riItemCalSet.get(i).getItemCalType().equals("1")) {
						strBuffer.append(" a.");
						strBuffer.append(riItemCalSet.get(i).getTarGetClumn());
						strBuffer.append(" =");
						strBuffer.append(riItemCalSet.get(i).getDoubleValue());
						// if (i != riItemCalSet.size()) {
						// strBuffer.append(", ");
						// }
					}
					if (riItemCalSet.get(i).getItemCalType().equals("2")) {
						strBuffer.append(" a.");
						strBuffer.append(riItemCalSet.get(i).getTarGetClumn());
						strBuffer.append(" =(");
						strBuffer.append(riItemCalSet.get(i).getCalSQL());
						strBuffer.append(") ");
					}
					if (riItemCalSet.get(i).getItemCalType().equals("3")) {
						Class tClass = Class.forName(riItemCalSet.get(i)
								.getCalClass());
						RIInterParam tRIInterParam = (RIInterParam) tClass
								.newInstance();
						tRIInterParam.dealData();
						strBuffer.append(" a.");
						strBuffer.append(riItemCalSet.get(i).getTarGetClumn());
						strBuffer.append(" = ");
						strBuffer.append(tRIInterParam.getValue());
					}
					strBuffer.append(" where a.BatchNo='");
					strBuffer.append(mRIWFLogSchema.getBatchNo());
					strBuffer.append("' and a.AccumulateDefNO='");
					strBuffer.append(mRIWFLogSchema.getTaskCode());
					strBuffer.append("' and a.EventNo between '");
					strBuffer.append(mSeg[j][0]);
					strBuffer.append("' and '");
					strBuffer.append(mSeg[j][1]);
					strBuffer.append("' and a.RIPreceptNo = '");
					strBuffer.append(riPreceptNo);
					strBuffer
							.append("' and exists (select * from rirecordtrace r where r.eventtype='");
					strBuffer.append(mEventType);
					strBuffer.append("' and a.SerialNo=r.SerialNo) ");
					if (riItemCalSet.get(i).getCalItemID().equals("feerate")
							|| riItemCalSet.get(i).getCalItemID()
									.equals("commrate")) {
						if (riItemCalSet.get(i).getStandbyString1() != null
								&& !riItemCalSet.get(i).getStandbyString1()
										.equals("")) {
							strBuffer.append(" and a.Areaid ='");
							strBuffer.append(riItemCalSet.get(i)
									.getStandbyString1());
							strBuffer.append("' ");
						} else {
							buildError("RIDataMake",
									" 初始化参数时出错: 分保费率或分保佣金率的算法中未制定费率适用区域ID");
							return false;
						}
					}
					System.out.println("StrBuffer.toString(): "
							+ strBuffer.toString());
					if (!execUpdateSQL(strBuffer.toString())) {
						buildError(
								"RIDataMake",
								" 初始化累计风险为：" + mRIWFLogSchema.getTaskCode()
										+ "的参数时出错:"
										+ this.mErrors.getFirstError());
						return false;
					}
				}
			}
		} catch (Exception ex) {
			buildError("RIDataMake",
					" 初始化累计风险为：" + mRIWFLogSchema.getTaskCode() + "的参数时出错:"
							+ ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 执行SQL
	 * 
	 * @param strSQL
	 *            String
	 * @return boolean
	 */
	private boolean execUpdateSQL(String strSQL) {
		ExeSQL tExeSQL = new ExeSQL();
		if (!tExeSQL.execUpdateSQL(strSQL)) {
			return false;
		}
		if (tExeSQL.mErrors.needDealError()) {
			return false;
		}
		return true;
	}

	/**
	 * 记录工作流日志
	 * 
	 * @return boolean
	 */
	private boolean recordLog() {
		return true;
	}

	public static void main(String[] args) {
		RIInitParam tRIInitParam = new RIInitParam();
		VData tVData = new VData();
		RIWFLogSchema mRIWFLogSchema = new RIWFLogSchema();
		mRIWFLogSchema.setBatchNo("0000000067");
		mRIWFLogSchema.setTaskCode("L001000001");
		tVData.add(mRIWFLogSchema);
		tRIInitParam.submitData(tVData, "01");
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RIDataMake";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
		System.out.print(szErrMsg);
	}
}

