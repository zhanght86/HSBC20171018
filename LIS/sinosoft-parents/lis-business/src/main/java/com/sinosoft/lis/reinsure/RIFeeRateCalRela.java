

package com.sinosoft.lis.reinsure;

import com.sinosoft.lis.db.RIItemCalDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.RIAccumulateDefSchema;
import com.sinosoft.lis.schema.RIAssociateFeeTableSchema;
import com.sinosoft.lis.schema.RIItemCalSchema;
import com.sinosoft.lis.schema.RIPreceptSchema;
import com.sinosoft.lis.vschema.RIItemCalSet;
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
 * Description: 根据再保方案管理页面关联的费率表字段添加费率算法
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class RIFeeRateCalRela {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private RIItemCalSet mRIItemCalSet = new RIItemCalSet();
	private MMap mMap = new MMap();
	private VData mResult = new VData();

	public RIFeeRateCalRela() {
	}

	/**
	 * 配置再保方案的费率表
	 * 
	 * @param tRIRiskDivideSet
	 *            RIRiskDivideSet
	 * @param tRIPreceptSchema
	 *            RIPreceptSchema
	 * @param tRIAccumulateDefSchema
	 *            RIAccumulateDefSchema
	 * @return boolean
	 */
	public boolean deal(RIRiskDivideSet tRIRiskDivideSet,
			RIPreceptSchema tRIPreceptSchema,
			RIAccumulateDefSchema tRIAccumulateDefSchema) {
		// 删除原费率表算法
		RIItemCalSet tRIItemCalSet = delRIItemCal(tRIPreceptSchema);
		if (tRIItemCalSet == null) {
			return false;
		} else {
			mMap.put(tRIItemCalSet, "DELETE");
		}

		// 如果数据处理模式为：事件增量模式
		mRIItemCalSet.add(confAddMode(tRIRiskDivideSet, tRIPreceptSchema));

		// if (tRIAccumulateDefSchema.getDealMode().equals("01")) {
		// //如果数据处理模式为：事件增量模式
		// mRIItemCalSet.add(confAddMode(tRIRiskDivideSet,tRIPreceptSchema));
		// } else if (tRIAccumulateDefSchema.getDealMode().equals("02")) {
		// //如果数据处理模式为：有效保单模式
		// mRIItemCalSet.add(confCvalMode(tRIRiskDivideSet,tRIPreceptSchema));
		// } else {
		// buildError("deal", "数据处理模式错误");
		// return false;
		// }
		mMap.put(mRIItemCalSet, "INSERT");
		mResult.add(mMap);

		return true;
	}

	/**
	 * 配置再保方案中区域ID的费率表
	 * 
	 * @param tRIRiskDivideSchema
	 *            RIRiskDivideSchema
	 * @param tRIPreceptSchema
	 *            RIPreceptSchema
	 * @param tRIAccumulateDefSchema
	 *            RIAccumulateDefSchema
	 * @return boolean
	 */
	public boolean deal(RIAssociateFeeTableSchema tRIAssociateFeeTableSchema,
			RIPreceptSchema tRIPreceptSchema,
			RIAccumulateDefSchema tRIAccumulateDefSchema) {
		// 删除原费率表算法
		RIItemCalSet tRIItemCalSet = delRIItemCal(tRIAssociateFeeTableSchema,
				tRIPreceptSchema);
		if (tRIItemCalSet == null) {
			return false;
		} else {
			mMap.put(tRIItemCalSet, "DELETE");
		}
		RIItemCalSet aRIItemCalSet;
		// 如果数据处理模式为：事件增量模式
		aRIItemCalSet = confAddMode(tRIAssociateFeeTableSchema,
				tRIPreceptSchema);
		// if (tRIAccumulateDefSchema.getDealMode().equals("01")) {
		// //如果数据处理模式为：事件增量模式
		// aRIItemCalSet =
		// confAddMode(tRIAssociateFeeTableSchema,tRIPreceptSchema) ;
		// if(aRIItemCalSet==null){
		// return false;
		// }
		// mRIItemCalSet.add(aRIItemCalSet);
		// } else if (tRIAccumulateDefSchema.getDealMode().equals("02")) {
		// //如果数据处理模式为：有效保单模式
		// aRIItemCalSet =
		// confCvalMode(tRIAssociateFeeTableSchema,tRIPreceptSchema);
		// if(aRIItemCalSet==null){
		// return false;
		// }
		// mRIItemCalSet.add(aRIItemCalSet);
		// } else {
		// buildError("deal", "数据处理模式错误");
		// return false;
		// }
		mMap.put(mRIItemCalSet, "INSERT");
		mResult.add(mMap);

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 删除再保方案下的费率表算法
	 * 
	 * @param tRIPreceptSchema
	 *            RIPreceptSchema
	 * @return RIItemCalSet
	 */
	private RIItemCalSet delRIItemCal(RIPreceptSchema tRIPreceptSchema) {
		try {
			RIItemCalDB tRIItemCalDB = new RIItemCalDB();
			tRIItemCalDB.setArithmeticDefID(tRIPreceptSchema.getArithmeticID());
			tRIItemCalDB.setArithmeticType("12");
			tRIItemCalDB.setReMark("feetable".toLowerCase());
			RIItemCalSet tRIItemCalSet = tRIItemCalDB.query();
			if (tRIItemCalDB.mErrors.needDealError()) {
				buildError("delRIItemCal", "获取原费率表算法失败");
				return null;
			}
			return tRIItemCalSet;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 删除再保方案下某个区域的费率表算法
	 * 
	 * @param tRIAssociateFeeTableSchema
	 *            RIAssociateFeeTableSchema
	 * @param tRIPreceptSchema
	 *            RIPreceptSchema
	 * @return RIItemCalSet
	 */
	private RIItemCalSet delRIItemCal(
			RIAssociateFeeTableSchema tRIAssociateFeeTableSchema,
			RIPreceptSchema tRIPreceptSchema) {
		try {
			RIItemCalDB tRIItemCalDB = new RIItemCalDB();
			tRIItemCalDB.setArithmeticDefID(tRIPreceptSchema.getArithmeticID());
			tRIItemCalDB.setArithmeticType("12");
			tRIItemCalDB.setStandbyString1(tRIAssociateFeeTableSchema
					.getAreaID());
			tRIItemCalDB.setReMark("feetable".toLowerCase());
			RIItemCalSet tRIItemCalSet = tRIItemCalDB.query();
			if (tRIItemCalDB.mErrors.needDealError()) {
				buildError("delRIItemCal", "获取原费率表算法失败");
				return null;
			}
			return tRIItemCalSet;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 再保方案下的费率表算法配置，事件增量模式
	 * 
	 * @param tRIRiskDivideSet
	 *            RIRiskDivideSet
	 * @param tRIPreceptSchema
	 *            RIPreceptSchema
	 * @return RIItemCalSet
	 */
	private RIItemCalSet confAddMode(RIRiskDivideSet tRIRiskDivideSet,
			RIPreceptSchema tRIPreceptSchema) {
		RIItemCalSet tRIItemCalSet = new RIItemCalSet();
		RIItemCalSchema tRIItemCalSchema;
		for (int j = 1; j <= 3; j++) {
			int order = 1;
			for (int i = 1; i <= tRIRiskDivideSet.size(); i++) {
				// 配置费率表算法
				if (tRIRiskDivideSet.get(i).getPremFeeTableNo() != null
						&& !tRIRiskDivideSet.get(i).getPremFeeTableNo()
								.equals("")) {
					tRIItemCalSchema = new RIItemCalSchema();
					tRIItemCalSchema.setArithmeticDefID(tRIPreceptSchema
							.getArithmeticID());
					tRIItemCalSchema.setArithmeticID(tRIPreceptSchema
							.getArithmeticID() + "12");
					if (j == 1) { // 新单算法
						tRIItemCalSchema.setArithmeticName("新单分保费率算法");
					} else if (j == 2) { // 需求算法
						tRIItemCalSchema.setArithmeticName("续期分保费率算法");
					} else { // 保全算法
						tRIItemCalSchema.setArithmeticName("保全分保费率算法");
					}
					tRIItemCalSchema.setArithmeticType("12");
					tRIItemCalSchema.setCalItemID("feerate");
					tRIItemCalSchema.setCalItemName("分保费率");
					String strOrder = formatOrder(order);
					if (j == 1) { // 新单算法
						tRIItemCalSchema.setCalItemOrder("10" + strOrder);
					} else if (j == 2) { // 需求算法
						tRIItemCalSchema.setCalItemOrder("20" + strOrder);
					} else { // 保全算法
						tRIItemCalSchema.setCalItemOrder("30" + strOrder);
					}
					if (j == 1) { // 新单算法
						tRIItemCalSchema.setCalItemType("01");
					} else if (j == 2) { // 需求算法
						tRIItemCalSchema.setCalItemType("02");
					} else { // 保全算法
						tRIItemCalSchema.setCalItemType("03");
					}
					tRIItemCalSchema.setItemCalType("2");
					tRIItemCalSchema.setTarGetClumn("ParamDouble1");
					tRIItemCalSchema.setReMark("feetable");
					tRIItemCalSchema.setStandbyString1(tRIRiskDivideSet.get(i)
							.getAreaID());

					tRIItemCalSet.add(tRIItemCalSchema);
					order++;
				}
				if (tRIRiskDivideSet.get(i).getComFeeTableNo() != null
						&& !tRIRiskDivideSet.get(i).getComFeeTableNo()
								.equals("")) {
					tRIItemCalSchema = new RIItemCalSchema();
					tRIItemCalSchema.setArithmeticDefID(tRIPreceptSchema
							.getArithmeticID());
					tRIItemCalSchema.setArithmeticID(tRIPreceptSchema
							.getArithmeticID() + "03");
					if (j == 1) { // 新单算法
						tRIItemCalSchema.setArithmeticName("新单分保佣金率算法");
					} else if (j == 2) { // 需求算法
						tRIItemCalSchema.setArithmeticName("续期分保佣金率算法");
					} else { // 保全算法
						tRIItemCalSchema.setArithmeticName("保全分保佣金率算法");
					}
					tRIItemCalSchema.setArithmeticType("12");
					tRIItemCalSchema.setCalItemID("commrate");
					tRIItemCalSchema.setCalItemName("分保佣金率");
					String strOrder = formatOrder(order);
					if (j == 1) { // 新单算法
						tRIItemCalSchema.setCalItemOrder("10" + strOrder);
					} else if (j == 2) { // 需求算法
						tRIItemCalSchema.setCalItemOrder("20" + strOrder);
					} else { // 保全算法
						tRIItemCalSchema.setCalItemOrder("30" + strOrder);
					}
					if (j == 1) { // 新单算法
						tRIItemCalSchema.setCalItemType("01");
					} else if (j == 2) { // 需求算法
						tRIItemCalSchema.setCalItemType("02");
					} else { // 保全算法
						tRIItemCalSchema.setCalItemType("03");
					}
					tRIItemCalSchema.setItemCalType("2");
					tRIItemCalSchema.setTarGetClumn("ParamDouble2");
					tRIItemCalSchema.setReMark("feetable");
					tRIItemCalSchema.setStandbyString1(tRIRiskDivideSet.get(i)
							.getAreaID());
					tRIItemCalSet.add(tRIItemCalSchema);
					order++;
				}
			}
		}
		return tRIItemCalSet;
	}

	/**
	 * 再保方案下某个分保区域费率表配置，事件增量模式
	 * 
	 * @param tRIRiskDivideSet
	 *            RIRiskDivideSet
	 * @param tRIPreceptSchema
	 *            RIPreceptSchema
	 * @return RIItemCalSet
	 */
	private RIItemCalSet confAddMode(
			RIAssociateFeeTableSchema tRIAssociateFeeTableSchema,
			RIPreceptSchema tRIPreceptSchema) {
		RIItemCalSet tRIItemCalSet = new RIItemCalSet();
		RIItemCalSchema tRIItemCalSchema;
		for (int j = 1; j <= 3; j++) {
			int order = getOrder(tRIAssociateFeeTableSchema, tRIPreceptSchema,
					j);
			if (order < 0) {
				return null;
			}
			// 配置费率表算法
			if (tRIAssociateFeeTableSchema.getPremFeeTableNo() != null
					&& !tRIAssociateFeeTableSchema.getPremFeeTableNo().equals(
							"")) {
				tRIItemCalSchema = new RIItemCalSchema();
				tRIItemCalSchema.setArithmeticDefID(tRIPreceptSchema
						.getArithmeticID());
				tRIItemCalSchema.setArithmeticID(tRIPreceptSchema
						.getArithmeticID() + "12");
				if (j == 1) { // 新单算法
					tRIItemCalSchema.setArithmeticName("新单分保费率算法");
				} else if (j == 2) { // 需求算法
					tRIItemCalSchema.setArithmeticName("续期分保费率算法");
				} else { // 保全算法
					tRIItemCalSchema.setArithmeticName("保全分保费率算法");
				}
				tRIItemCalSchema.setArithmeticType("12");
				tRIItemCalSchema.setCalItemID("feerate");
				tRIItemCalSchema.setCalItemName("分保费率");
				if (j == 1) { // 新单算法
					tRIItemCalSchema.setCalItemOrder(order);
				} else if (j == 2) { // 需求算法
					tRIItemCalSchema.setCalItemOrder(order);
				} else { // 保全算法
					tRIItemCalSchema.setCalItemOrder(order);
				}
				if (j == 1) { // 新单算法
					tRIItemCalSchema.setCalItemType("01");
				} else if (j == 2) { // 需求算法
					tRIItemCalSchema.setCalItemType("02");
				} else { // 保全算法
					tRIItemCalSchema.setCalItemType("03");
				}
				tRIItemCalSchema.setItemCalType("2");
				tRIItemCalSchema.setTarGetClumn("ParamDouble1");
				tRIItemCalSchema.setReMark("feetable");
				tRIItemCalSchema.setStandbyString1(tRIAssociateFeeTableSchema
						.getAreaID());

				tRIItemCalSet.add(tRIItemCalSchema);
				order++;
			}
			if (tRIAssociateFeeTableSchema.getComFeeTableNo() != null
					&& !tRIAssociateFeeTableSchema.getComFeeTableNo()
							.equals("")) {
				tRIItemCalSchema = new RIItemCalSchema();
				tRIItemCalSchema.setArithmeticDefID(tRIPreceptSchema
						.getArithmeticID());
				tRIItemCalSchema.setArithmeticID(tRIPreceptSchema
						.getArithmeticID() + "12");
				if (j == 1) { // 新单算法
					tRIItemCalSchema.setArithmeticName("新单分保佣金率算法");
				} else if (j == 2) { // 需求算法
					tRIItemCalSchema.setArithmeticName("续期分保佣金率算法");
				} else { // 保全算法
					tRIItemCalSchema.setArithmeticName("保全分保佣金率算法");
				}
				tRIItemCalSchema.setArithmeticType("12");
				tRIItemCalSchema.setCalItemID("commrate");
				tRIItemCalSchema.setCalItemName("分保佣金率");
				if (j == 1) { // 新单算法
					tRIItemCalSchema.setCalItemOrder(order);
				} else if (j == 2) { // 需求算法
					tRIItemCalSchema.setCalItemOrder(order);
				} else { // 保全算法
					tRIItemCalSchema.setCalItemOrder(order);
				}
				if (j == 1) { // 新单算法
					tRIItemCalSchema.setCalItemType("01");
				} else if (j == 2) { // 需求算法
					tRIItemCalSchema.setCalItemType("02");
				} else { // 保全算法
					tRIItemCalSchema.setCalItemType("03");
				}
				tRIItemCalSchema.setItemCalType("2");
				tRIItemCalSchema.setTarGetClumn("ParamDouble2");
				tRIItemCalSchema.setReMark("feetable");
				tRIItemCalSchema.setStandbyString1(tRIAssociateFeeTableSchema
						.getAreaID());
				tRIItemCalSet.add(tRIItemCalSchema);
				order++;
			}
		}
		return tRIItemCalSet;
	}

	/**
	 * 配置再保方案下费率表算法，有效保单模式
	 * 
	 * @param tRIRiskDivideSet
	 *            RIRiskDivideSet
	 * @param tRIPreceptSchema
	 *            RIPreceptSchema
	 * @return RIItemCalSet
	 */
	private RIItemCalSet confCvalMode(RIRiskDivideSet tRIRiskDivideSet,
			RIPreceptSchema tRIPreceptSchema) {
		RIItemCalSet tRIItemCalSet = new RIItemCalSet();

		RIItemCalSchema tRIItemCalSchema;
		int order = 1;
		for (int i = 1; i <= tRIRiskDivideSet.size(); i++) {// 配置费率表算法
			if (tRIRiskDivideSet.get(i).getPremFeeTableNo() != null
					&& !tRIRiskDivideSet.get(i).getPremFeeTableNo().equals("")) {
				tRIItemCalSchema = new RIItemCalSchema();
				tRIItemCalSchema.setArithmeticDefID(tRIPreceptSchema
						.getArithmeticID());
				tRIItemCalSchema.setArithmeticID(tRIPreceptSchema
						.getArithmeticID() + "12");
				tRIItemCalSchema.setArithmeticName("有效保单分保费率算法");
				tRIItemCalSchema.setArithmeticType("12");
				tRIItemCalSchema.setCalItemID("feerate");
				tRIItemCalSchema.setCalItemName("分保费率");
				tRIItemCalSchema.setCalItemOrder("10" + formatOrder(order));
				tRIItemCalSchema.setCalItemType("01");
				tRIItemCalSchema.setItemCalType("2");
				tRIItemCalSchema.setTarGetClumn("ParamDouble1");
				tRIItemCalSchema.setReMark("feetable");
				tRIItemCalSchema.setStandbyString1(tRIRiskDivideSet.get(i)
						.getAreaID());

				tRIItemCalSet.add(tRIItemCalSchema);
				order++;
			}
			if (tRIRiskDivideSet.get(i).getComFeeTableNo() != null
					&& !tRIRiskDivideSet.get(i).getComFeeTableNo().equals("")) {
				tRIItemCalSchema = new RIItemCalSchema();
				tRIItemCalSchema.setArithmeticDefID(tRIPreceptSchema
						.getArithmeticID());
				tRIItemCalSchema.setArithmeticID(tRIPreceptSchema
						.getArithmeticID() + "12");
				tRIItemCalSchema.setArithmeticName("有效保单分保佣金率算法");
				tRIItemCalSchema.setArithmeticType("12");
				tRIItemCalSchema.setCalItemID("commrate");
				tRIItemCalSchema.setCalItemName("佣金费率");
				tRIItemCalSchema.setCalItemOrder("10" + formatOrder(order));
				tRIItemCalSchema.setCalItemType("01");
				tRIItemCalSchema.setItemCalType("2");
				tRIItemCalSchema.setTarGetClumn("ParamDouble2");
				tRIItemCalSchema.setReMark("feetable");
				tRIItemCalSchema.setStandbyString1(tRIRiskDivideSet.get(i)
						.getAreaID());

				tRIItemCalSet.add(tRIItemCalSchema);
				order++;
			}
		}
		return tRIItemCalSet;
	}

	/**
	 * 再保方案下某个分保区域费率表配置，有效保单模式
	 * 
	 * @param tRIRiskDivideSet
	 *            RIRiskDivideSet
	 * @param tRIPreceptSchema
	 *            RIPreceptSchema
	 * @return RIItemCalSet
	 */
	private RIItemCalSet confCvalMode(
			RIAssociateFeeTableSchema tRIAssociateFeeTableSchema,
			RIPreceptSchema tRIPreceptSchema) {
		RIItemCalSet tRIItemCalSet = new RIItemCalSet();
		RIItemCalSchema tRIItemCalSchema;
		int order = getOrder(tRIAssociateFeeTableSchema, tRIPreceptSchema, 1);
		if (order < 0) {
			return null;
		}
		if (tRIAssociateFeeTableSchema.getPremFeeTableNo() != null
				&& !tRIAssociateFeeTableSchema.getPremFeeTableNo().equals("")) {
			tRIItemCalSchema = new RIItemCalSchema();
			tRIItemCalSchema.setArithmeticDefID(tRIPreceptSchema
					.getArithmeticID());
			tRIItemCalSchema.setArithmeticID(tRIPreceptSchema.getArithmeticID()
					+ "12");
			tRIItemCalSchema.setArithmeticName("有效保单分保费率算法");
			tRIItemCalSchema.setArithmeticType("12");
			tRIItemCalSchema.setCalItemID("feerate");
			tRIItemCalSchema.setCalItemName("分保费率");
			tRIItemCalSchema.setCalItemOrder(order);
			tRIItemCalSchema.setCalItemType("01");
			tRIItemCalSchema.setItemCalType("2");
			tRIItemCalSchema.setTarGetClumn("ParamDouble1");
			tRIItemCalSchema.setReMark("feetable");
			tRIItemCalSchema.setStandbyString1(tRIAssociateFeeTableSchema
					.getAreaID());

			tRIItemCalSet.add(tRIItemCalSchema);
			order++;
		}
		if (tRIAssociateFeeTableSchema.getComFeeTableNo() != null
				&& !tRIAssociateFeeTableSchema.getComFeeTableNo().equals("")) {
			tRIItemCalSchema = new RIItemCalSchema();
			tRIItemCalSchema.setArithmeticDefID(tRIPreceptSchema
					.getArithmeticID());
			tRIItemCalSchema.setArithmeticID(tRIPreceptSchema.getArithmeticID()
					+ "12");
			tRIItemCalSchema.setArithmeticName("有效保单分保佣金率算法");
			tRIItemCalSchema.setArithmeticType("12");
			tRIItemCalSchema.setCalItemID("commrate");
			tRIItemCalSchema.setCalItemName("佣金费率");
			tRIItemCalSchema.setCalItemOrder(order);
			tRIItemCalSchema.setCalItemType("01");
			tRIItemCalSchema.setItemCalType("2");
			tRIItemCalSchema.setTarGetClumn("ParamDouble2");
			tRIItemCalSchema.setReMark("feetable");
			tRIItemCalSchema.setStandbyString1(tRIAssociateFeeTableSchema
					.getAreaID());

			tRIItemCalSet.add(tRIItemCalSchema);
			order++;
		}
		return tRIItemCalSet;
	}

	private int getOrder(RIAssociateFeeTableSchema tRIAssociateFeeTableSchema,
			RIPreceptSchema tRIPreceptSchema, int i) {
		try {
			StringBuffer sqlStrBuffer = new StringBuffer();
			sqlStrBuffer
					.append(" select nvl(max(a.CalItemOrder),0) from riitemcal a where a.arithmeticdefid='");
			sqlStrBuffer.append(tRIPreceptSchema.getArithmeticID());
			sqlStrBuffer
					.append("' and a.ArithmeticType = '12' and a.ReMark = 'feetable' and a.CalItemType = ");
			if (i == 1) {
				sqlStrBuffer.append("'01' ");
			} else if (i == 2) {
				sqlStrBuffer.append("'02' ");
			} else {
				sqlStrBuffer.append("'03' ");
			}
			ExeSQL tExeSql = new ExeSQL();
			SSRS tSSRS = tExeSql.execSQL(sqlStrBuffer.toString());
			if (tExeSql.mErrors.needDealError()) {
				buildError("getOriOrder", "查询费率表算法序号时出错");
				return -1;
			}
			int order = Integer.parseInt((String) tSSRS.GetText(1, 1));
			if (order == 0) {
				if (i == 1) {
					order = 10001;
				} else if (i == 2) {
					order = 20001;
				} else {
					order = 30001;
				}
			} else {
				order++;
			}
			return order;
		} catch (Exception ex) {
			buildError("getOriOrder", "查询费率表算法序号时出错");
			return -1;
		}
	}

	/**
	 * 将序号转换为'01'-'10'的形式
	 * 
	 * @param order
	 *            int
	 * @return String
	 */
	private String formatOrder(int order) {
		String orderStr = "";
		if (order < 10) {
			orderStr = "00" + order;
		} else if (order < 100 && order >= 10) {
			orderStr = "0" + order;
		} else {
			orderStr = order + "";
		}
		return orderStr;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RIFeeRateCalRela";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		RIFeeRateCalRela tRIFeeRateCalRela = new RIFeeRateCalRela();
		RIItemCalSchema tRIItemCalSchema = new RIItemCalSchema();

		System.out.println(" order : " + tRIFeeRateCalRela.formatOrder(990));
	}
}
