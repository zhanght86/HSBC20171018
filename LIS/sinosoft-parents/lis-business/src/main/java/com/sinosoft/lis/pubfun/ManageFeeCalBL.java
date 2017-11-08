package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpFeeDB;
import com.sinosoft.lis.db.LCGrpFeeParamDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMRiskFeeDB;
import com.sinosoft.lis.db.LMRiskFeeParamDB;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCGrpFeeParamSet;
import com.sinosoft.lis.vschema.LCGrpFeeSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LMRiskFeeParamSet;
import com.sinosoft.lis.vschema.LMRiskFeeSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;

public class ManageFeeCalBL {
private static Logger logger = Logger.getLogger(ManageFeeCalBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 全局数据 */
	private Reflections ref = new Reflections();
	private TransferData mTransferData = new TransferData();
	public double managefee; // 需要返回的管理费

	/** 数据操作字符串 */
	private String mOperate;

	public ManageFeeCalBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean 参数说明：集体合同号，险种，管理费类型，是否是公共帐户，个人合同号，管理费编码
	 */
	public boolean submitData(String GrpContno, String RiskCode,
			String PayPlantype, String CommFlag, String ContNo, String FeeCode) {

		// 首先判断是不是公共帐户如果是返回值=该险种在该团单下管理费的和 否则=该个人合同的该险种的管理费
		// ***如果管理费类型是3（固定值） 
		double tempValue = 0;
		LCGrpFeeDB tLCGrpFeeDB = new LCGrpFeeDB();
		LCGrpFeeSet tLCGrpFeeSet = new LCGrpFeeSet();
		String sql = "select * from lcgrpfee where grpcontno='" + "?grpcontno?"
				+ "' and riskcode='" + "?riskcode?" + "' and feecode='" + "?feecode?"
				+ "'";
		logger.debug(sql);
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("grpcontno", GrpContno);
		sqlbv1.put("riskcode", RiskCode);
		sqlbv1.put("feecode", FeeCode);
		tLCGrpFeeSet = tLCGrpFeeDB.executeQuery(sqlbv1);
		if (tLCGrpFeeSet.size() > 0) {
			tempValue = tLCGrpFeeSet.get(1).getFeeValue(); // 可能是比例也可能是固定值
		} else {
			// 查询险种定义
			LMRiskFeeDB tLMRiskFeeDB = new LMRiskFeeDB();
			LMRiskFeeSet tLMRiskFeeSet = new LMRiskFeeSet();
			sql = "select * from lmriskfee where feecode='" + "?feecode1?" + "'";
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(sql);
			sqlbv2.put("feecode1", FeeCode);
			tLMRiskFeeSet = tLMRiskFeeDB.executeQuery(sqlbv2);
			if (tLMRiskFeeSet.size() > 0) {
				tempValue = tLMRiskFeeSet.get(1).getFeeValue(); // 可能是比例也可能是固定值
			}
		}
		// 首先处理公共帐户
		if (CommFlag.equals("1")) {
			if (PayPlantype.equals("03")) {
				// 查询人数，-1，*每个人的费用
				managefee = tempValue;
				// managefee = parseFloat(String.valueOf(managefee));
				return true;
			} else if (PayPlantype.equals("04")) {
				// 查询该团单下该险种所有保费*比例
				LCPolDB tLCPolDB = new LCPolDB();
				LCPolSet tLCPolSet = new LCPolSet();
				sql = "select * from lcpol where grpcontno='" + "?grpcontno?"
						+ "' and riskcode='" + "?riskcode?"
						+ "' and appflag!='1' and appflag!='4'";
				SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
				sqlbv12.sql(sql);
				sqlbv12.put("grpcontno", GrpContno);
				sqlbv12.put("riskcode", RiskCode);
				tLCPolSet = tLCPolDB.executeQuery(sqlbv12);
				double sumprem = 0;
				if (tLCPolSet.size() > 0) {
					for (int i = 1; i <= tLCPolSet.size(); i++) {
						sumprem = sumprem + tLCPolSet.get(i).getPrem();
					}
				} else// 续收使用
				{
					// 查询此次续收的交费总额。存放于lcgrpcont表中的standbyflag3字段
					LCGrpContSet nLCGrpContSet = new LCGrpContSet();
					LCGrpContDB nLCGrpContDB = new LCGrpContDB();
					nLCGrpContDB.setGrpContNo(GrpContno);
					nLCGrpContSet = nLCGrpContDB.query();
					if (nLCGrpContSet.size() > 0) {
						sumprem = nLCGrpContSet.get(1).getSumPay();
					} else {
						sumprem = 0.00;
					}
					logger.debug("得到此次交费总额：" + sumprem);
				}
				managefee = sumprem * tempValue;
				return true;
			} else if (PayPlantype.equals("07")) {
				LCPolDB tLCPolDB = new LCPolDB();
				LCPolSet tLCPolSet = new LCPolSet();
				sql = "select * from lcpol where grpcontno='" + "?grpcontno?"
						+ "' and riskcode='" + "?riskcode?"
						+ "' and appflag!='1' and appflag!='4'";
				SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
				sqlbv3.sql(sql);
				sqlbv3.put("grpcontno", GrpContno);
				sqlbv3.put("riskcode", RiskCode);
				tLCPolSet = tLCPolDB.executeQuery(sqlbv3);
				double sumprem = 0;
				if (tLCPolSet.size() > 0) {
					for (int i = 1; i <= tLCPolSet.size(); i++) {
						sumprem = sumprem + tLCPolSet.get(i).getPrem();
					}
				} else// 续收使用
				{
					// 查询此次续收的交费总额。存放于lcgrpcont表中的standbyflag3字段
					LCGrpContSet nLCGrpContSet = new LCGrpContSet();
					LCGrpContDB nLCGrpContDB = new LCGrpContDB();
					nLCGrpContDB.setGrpContNo(GrpContno);
					nLCGrpContSet = nLCGrpContDB.query();
					if (nLCGrpContSet.size() > 0) {
						sumprem = nLCGrpContSet.get(1).getSumPay();
					} else {
						sumprem = 0.00;
					}
					logger.debug("得到此次交费总额：" + sumprem);
				}
				// 先查询PAYPLANCODE
				LMRiskFeeDB tLMRiskFeeDB = new LMRiskFeeDB();
				LMRiskFeeSet tLMRiskFeeSet = new LMRiskFeeSet();
				String tsql = "select * From lmriskfee where feecode='"
						+ "?FeeCode?" + "'";
				SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
				sqlbv4.sql(tsql);
				sqlbv4.put("FeeCode", FeeCode);
				tLMRiskFeeSet = tLMRiskFeeDB.executeQuery(sqlbv4);
				String tPayPlanCode = "";
				if (tLMRiskFeeSet != null && tLMRiskFeeSet.size() > 0) {
					tPayPlanCode = tLMRiskFeeSet.get(1).getPayPlanCode();
				} else {
					managefee = 0;
					return true;
				}
				// 先查询lcgrpfeeparam，如果没有则查询LMRiskFeeParam
				LCGrpFeeParamDB tLCGrpFeeParamDB = new LCGrpFeeParamDB();
				LCGrpFeeParamSet tLCGrpFeeParamSet = new LCGrpFeeParamSet();
				tsql = "select * From lcgrpfeeparam where grpcontno='"
						+ "?GrpContno?" + "' and riskcode='" + "?RiskCode?"
						+ "' and payplancode='" + "?tPayPlanCode?"
						+ "' and feecode='" + "?FeeCode?" + "' order by feeid";
				SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
				sqlbv5.sql(tsql);
				sqlbv5.put("GrpContno", GrpContno);
				sqlbv5.put("RiskCode", RiskCode);
				sqlbv5.put("tPayPlanCode", tPayPlanCode);
				sqlbv5.put("FeeCode", FeeCode);
				tLCGrpFeeParamSet = tLCGrpFeeParamDB.executeQuery(sqlbv5);
				if (tLCGrpFeeParamSet != null && tLCGrpFeeParamSet.size() > 0) {
					int tt = tLCGrpFeeParamSet.size();
					double str[][] = null;
					str = new double[tt][2];
					for (int i = 0; i < tt; i++) {
						str[i][0] = tLCGrpFeeParamSet.get(i + 1).getFeeMax();
						str[i][1] = tLCGrpFeeParamSet.get(i + 1).getFeeRate();
					}
					// 目前考虑的是最少俩个分档，所以容错没有处理，如果以后有报错现象再做。
					if (tt < 2) {
						managefee = sumprem * str[0][1];
						return true;
					}
					// 至少俩个分档时的处理,先处理最极端的一种情况
					if (sumprem > str[tt - 2][0]) { // 如果账户金额在最后一个区段
						for (int j = 0; j <= tt - 2; j++) {
							managefee = managefee + str[j][0] * str[j][1];
						}
						managefee = managefee + (sumprem - str[tt - 2][0])
								* str[tt - 1][1];
						return true;
					}
					// 处理其它情况。
					// 思路：循环数组，锁定该账户金额对应的分档位置，记住这个数组下标，然后进行处理。
					int LocateIndex = 0;
					for (int k = 0; k <= tt - 2; k++) {
						if (str[k][0] >= sumprem) {
							LocateIndex = k;
							break;
						}
					}
					// 根据下标的数值进行处理即可
					if (LocateIndex == 0) {
						managefee = sumprem * str[0][1];
						return true;
					}

					for (int t = 0; t < LocateIndex; t++) {
						if (t == 0) {
							managefee = managefee + str[t][0] * str[t][1];
						} else {
							managefee = managefee + (str[t][0] - str[t - 1][0])
									* str[t][1];
						}
					}
					managefee = managefee + (sumprem - str[LocateIndex - 1][0])
							* str[LocateIndex][1];
					return true;
				} else {
					// 查询LMRiskFeeParam
					LMRiskFeeParamDB tLMRiskFeeParamDB = new LMRiskFeeParamDB();
					LMRiskFeeParamSet tLMRiskFeeParamSet = new LMRiskFeeParamSet();
					tsql = "select * From LMRiskFeeParam where  payplancode='"
							+ "?payplancode?" + "' and feecode='" + "?feecode?"
							+ "' order by feeid";
					SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
					sqlbv6.sql(tsql);
					sqlbv6.put("payplancode", tPayPlanCode);
					sqlbv6.put("feecode", FeeCode);
					tLMRiskFeeParamSet = tLMRiskFeeParamDB.executeQuery(sqlbv6);
					if (tLMRiskFeeParamSet != null
							&& tLMRiskFeeParamSet.size() > 0) {
						int tt = tLMRiskFeeParamSet.size();
						double str[][] = null;
						str = new double[tt][2];
						for (int i = 0; i < tt; i++) {
							str[i][0] = tLMRiskFeeParamSet.get(i + 1)
									.getFeeMax();
							str[i][1] = tLMRiskFeeParamSet.get(i + 1)
									.getFeeRate();
						}
						// 目前考虑的是最少俩个分档，所以容错没有处理，如果以后有报错现象再做。
						if (tt < 2) {
							managefee = sumprem * str[0][1];
							return true;
						}
						// 至少俩个分档时的处理,先处理最极端的一种情况
						if (sumprem > str[tt - 2][0]) { // 如果账户金额在最后一个区段
							for (int j = 0; j <= tt - 2; j++) {
								managefee = managefee + str[j][0] * str[j][1];
							}
							managefee = managefee + (sumprem - str[tt - 2][0])
									* str[tt - 1][1];
							return true;
						}
						// 处理其它情况。
						// 思路：循环数组，锁定该账户金额对应的分档位置，记住这个数组下标，然后进行处理。
						int LocateIndex = 0;
						for (int k = 0; k < tt - 2; k++) {
							if (str[k][0] >= sumprem) {
								LocateIndex = k;
								break;
							}
						}
						// 根据下标的数值进行处理即可
						if (LocateIndex == 0) {
							managefee = sumprem * str[0][1];
							return true;
						}
						for (int t = 0; t < LocateIndex - 1; t++) {
							managefee = managefee + str[t][0] * str[t][1];
						}
						if (sumprem > str[0][0]) {
							managefee = managefee
									+ (sumprem - str[LocateIndex - 1][0])
									* str[LocateIndex][1];
						}
						return true;

					}

				}
				return true;
			}
		} else {
			// 处理个人帐户
			if (PayPlantype.equals("03")) {
				// 个人的保费
				managefee = tempValue;
				return true;
			} else if (PayPlantype.equals("04")) {
				// 查询该团单下该险种所有保费*比例
				LCPolDB tLCPolDB = new LCPolDB();
				LCPolSet tLCPolSet = new LCPolSet();
				sql = "select * from lcpol where grpcontno='" + "?GrpContno?"
						+ "' and riskcode='" + "?RiskCode?" + "' and contno='"
						+ "?ContNo?" + "'";
				SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
				sqlbv7.sql(sql);
				sqlbv7.put("GrpContno", GrpContno);
				sqlbv7.put("RiskCode", RiskCode);
				sqlbv7.put("ContNo", ContNo);
				tLCPolSet = tLCPolDB.executeQuery(sqlbv7);
				managefee = tLCPolSet.get(1).getPrem() * tempValue;
				// managefee = parseFloat(String.valueOf(managefee));
				return true;
			} else if (PayPlantype.equals("07")) {
				LCPolDB tLCPolDB = new LCPolDB();
				LCPolSet tLCPolSet = new LCPolSet();
				sql = "select * from lcpol where grpcontno='" + "?grpcontno?"
						+ "' and riskcode='" + "?riskcode?" + "' and contno='"
						+ "?contno?" + "'";
				SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
				sqlbv8.sql(sql);
				sqlbv8.put("grpcontno", GrpContno);
				sqlbv8.put("riskcode", RiskCode);
				sqlbv8.put("contno", ContNo);
				tLCPolSet = tLCPolDB.executeQuery(sqlbv8);
				double sumprem = tLCPolSet.get(1).getPrem();
				// 先查询PAYPLANCODE
				LMRiskFeeDB tLMRiskFeeDB = new LMRiskFeeDB();
				LMRiskFeeSet tLMRiskFeeSet = new LMRiskFeeSet();
				String tsql = "select * From lmriskfee where feecode='"
						+ "?FeeCode?" + "'";
				SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
				sqlbv9.sql(tsql);
				sqlbv9.put("FeeCode", FeeCode);
				tLMRiskFeeSet = tLMRiskFeeDB.executeQuery(sqlbv9);
				String tPayPlanCode = "";
				if (tLMRiskFeeSet != null && tLMRiskFeeSet.size() > 0) {
					tPayPlanCode = tLMRiskFeeSet.get(1).getPayPlanCode();
				} else {
					managefee = 0;
					return true;
				}
				// 先查询lcgrpfeeparam，如果没有则查询LMRiskFeeParam
				LCGrpFeeParamDB tLCGrpFeeParamDB = new LCGrpFeeParamDB();
				LCGrpFeeParamSet tLCGrpFeeParamSet = new LCGrpFeeParamSet();
				tsql = "select * From lcgrpfeeparam where grpcontno='"
						+ "?grpcontno1?" + "' and riskcode='" + "?riskcode1?"
						+ "' and payplancode='" + "?tPayPlanCode?"
						+ "' and feecode='" + "?FeeCode1?" + "' order by feeid";
				SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
				sqlbv10.sql(tsql);
				sqlbv10.put("grpcontno1", GrpContno);
				sqlbv10.put("riskcode1", RiskCode);
				sqlbv10.put("tPayPlanCode", tPayPlanCode);
				sqlbv10.put("FeeCode1", FeeCode);
				tLCGrpFeeParamSet = tLCGrpFeeParamDB.executeQuery(sqlbv10);
				if (tLCGrpFeeParamSet != null && tLCGrpFeeParamSet.size() > 0) {
					int tt = tLCGrpFeeParamSet.size();
					double str[][] = null;
					str = new double[tt][2];
					for (int i = 0; i < tt; i++) {
						str[i][0] = tLCGrpFeeParamSet.get(i + 1).getFeeMax();
						str[i][1] = tLCGrpFeeParamSet.get(i + 1).getFeeRate();
					}
					// 目前考虑的是最少俩个分档，所以容错没有处理，如果以后有报错现象再做。
					if (tt < 2) {
						managefee = sumprem * str[0][1];
						// managefee = parseFloat(String.valueOf(managefee));
						return true;
					}
					// 至少俩个分档时的处理,先处理最极端的一种情况
					if (sumprem > str[tt - 2][0]) { // 如果账户金额在最后一个区段
						for (int j = 0; j <= tt - 2; j++) {
							managefee = managefee + str[j][0] * str[j][1];
						}
						managefee = managefee + (sumprem - str[tt - 2][0])
								* str[tt - 1][1];
						// managefee = parseFloat(String.valueOf(managefee));
						return true;
					}
					// 处理其它情况。
					// 思路：循环数组，锁定该账户金额对应的分档位置，记住这个数组下标，然后进行处理。
					int LocateIndex = 0;
					for (int k = 0; k < tt - 2; k++) {
						if (str[k][0] >= sumprem) {
							LocateIndex = k;
							break;
						}
					}
					// 根据下标的数值进行处理即可
					for (int t = 0; t < LocateIndex - 1; t++) {
						managefee = managefee + str[t][0] * str[t][1];
					}
					managefee = managefee + (sumprem - str[LocateIndex - 1][0])
							* str[LocateIndex][1];
					// managefee = parseFloat(String.valueOf(managefee));
					return true;
				} else {
					// 查询LMRiskFeeParam
					LMRiskFeeParamDB tLMRiskFeeParamDB = new LMRiskFeeParamDB();
					LMRiskFeeParamSet tLMRiskFeeParamSet = new LMRiskFeeParamSet();
					tsql = "select * From LMRiskFeeParam where  payplancode='"
							+ "?payplancode?" + "' and feecode='" + "?feecode?"
							+ "' order by feeid";
					SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
					sqlbv11.sql(tsql);
					sqlbv11.put("payplancode", tPayPlanCode);
					sqlbv11.put("feecode", FeeCode);
					tLMRiskFeeParamSet = tLMRiskFeeParamDB.executeQuery(sqlbv11);
					logger.debug(tsql);
					if (tLMRiskFeeParamSet != null
							&& tLMRiskFeeParamSet.size() > 0) {
						int tt = tLMRiskFeeParamSet.size();
						double str[][] = null;
						str = new double[tt][2];
						for (int i = 0; i < tt; i++) {
							str[i][0] = tLMRiskFeeParamSet.get(i + 1)
									.getFeeMax();
							str[i][1] = tLMRiskFeeParamSet.get(i + 1)
									.getFeeRate();
						}
						// 目前考虑的是最少俩个分档，所以容错没有处理，如果以后有报错现象再做。
						if (tt < 2) {
							managefee = sumprem * str[0][1];
							// managefee =
							// parseFloat(String.valueOf(managefee));
							return true;
						}
						// 至少俩个分档时的处理,先处理最极端的一种情况
						if (sumprem > str[tt - 2][0]) { // 如果账户金额在最后一个区段
							for (int j = 0; j <= tt - 2; j++) {
								managefee = managefee + str[j][0] * str[j][1];
							}
							managefee = managefee + (sumprem - str[tt - 2][0])
									* str[tt - 1][1];
							// managefee =
							// parseFloat(String.valueOf(managefee));
							return true;
						}
						// 处理其它情况。
						// 思路：循环数组，锁定该账户金额对应的分档位置，记住这个数组下标，然后进行处理。
						int LocateIndex = 0;
						for (int k = 0; k < tt - 2; k++) {
							if (str[k][0] >= sumprem) {
								LocateIndex = k;
								break;
							}
						}
						// 根据下标的数值进行处理即可
						for (int t = 0; t < LocateIndex - 1; t++) {
							managefee = managefee + str[t][0] * str[t][1];
						}
						if (sumprem > str[0][0]) {
							managefee = managefee
									+ (sumprem - str[LocateIndex - 1][0])
									* str[LocateIndex][1];
						}
						// managefee = parseFloat(String.valueOf(managefee));
						return true;

					}

				}

				return true;
			}

		}
		return true;
	}

	private float parseFloat(String s) {
		float f1 = 0;
		String tmp = "";
		String s1 = "";
		String tFlag = "0";
		for (int i = 0; i < s.length(); i++) {
			s1 = s.substring(i, i + 1);
			if (s1.equals(".")) {
				tmp = tmp + s1;
				if (s.substring(i + 1, i + 2).compareTo("5") >= 0) {
					tFlag = "1";
				}
				break;
			} else {
				tmp = tmp + s1;
			}

		}
		f1 = Float.parseFloat(tmp);
		if (tFlag.equals("1")) {
			f1 = f1 + 1;
		}
		// logger.debug("tmp:"+tmp+" f1:"+f1);
		return f1;
	}

	public static void main(String[] args) {
		String tGrpContNo = "230101000028";
		String tRiskCode = "139";
		String tPayPlantype = "07";
		String tCommFlag = "1";
		String tContNo = "200101001284";
		String tFeeCode = "390002";
		ManageFeeCalBL tManageFeeCalBL = new ManageFeeCalBL();
		if (!tManageFeeCalBL.submitData(tGrpContNo, tRiskCode, tPayPlantype,
				tCommFlag, tContNo, tFeeCode)) {
			logger.debug("＝＝＝管理费计算失败＝＝＝");
		}

	}
}
