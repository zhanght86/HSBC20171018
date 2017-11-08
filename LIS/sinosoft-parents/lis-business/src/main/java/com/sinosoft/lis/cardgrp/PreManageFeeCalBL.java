package com.sinosoft.lis.cardgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpFeeDB;
import com.sinosoft.lis.db.LCGrpFeeParamDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMRiskFeeDB;
import com.sinosoft.lis.db.LMRiskFeeParamDB;
import com.sinosoft.lis.vschema.LCGrpFeeParamSet;
import com.sinosoft.lis.vschema.LCGrpFeeSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LMRiskFeeParamSet;
import com.sinosoft.lis.vschema.LMRiskFeeSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;

public class PreManageFeeCalBL {
private static Logger logger = Logger.getLogger(PreManageFeeCalBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 全局数据 */
	private Reflections ref = new Reflections();
	private TransferData mTransferData = new TransferData();
	public double managefee;// 需要返回的管理费
	/** 数据操作字符串 */
	private String mOperate;

	public PreManageFeeCalBL() {
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
		String sql = "select * from lcgrpfee where grpcontno='" + GrpContno
				+ "' and riskcode='" + RiskCode + "' and feecode='" + FeeCode
				+ "'";
		logger.debug(sql);
		tLCGrpFeeSet = tLCGrpFeeDB.executeQuery(sql);
		if (tLCGrpFeeSet.size() > 0) {
			tempValue = tLCGrpFeeSet.get(1).getFeeValue(); // 可能是比例也可能是固定值
		} else {
			// 查询险种定义
			LMRiskFeeDB tLMRiskFeeDB = new LMRiskFeeDB();
			LMRiskFeeSet tLMRiskFeeSet = new LMRiskFeeSet();
			sql = "select * from lmriskfee where feecode='" + FeeCode + "'";
			tLMRiskFeeSet = tLMRiskFeeDB.executeQuery(sql);
			if (tLMRiskFeeSet.size() > 0) {
				tempValue = tLMRiskFeeSet.get(1).getFeeValue(); // 可能是比例也可能是固定值
			}
		}
		// 首先处理公共帐户
		if (CommFlag.equals("1")) {
			if (PayPlantype.equals("03")) {
				// 查询人数，-1，*每个人的费用
				// LCInsuredDB tLCInsuredDB=new LCInsuredDB();
				// LCInsuredSet tLCInsuredSet=new LCInsuredSet();
				// sql="select * from lcinsured where
				// grpcontno='"+GrpContno+"'";
				// tLCInsuredSet=tLCInsuredDB.executeQuery(sql);
				// int peoples=tLCInsuredSet.size()-1;
				// LCPolDB tLCPolDB = new LCPolDB();
				// LCPolSet tLCPolSet = new LCPolSet();
				// sql = "select * from lcpol where grpcontno='" + GrpContno +
				// "' and riskcode='" + RiskCode + "'";
				// tLCPolSet = tLCPolDB.executeQuery(sql);
				// int peoples = tLCPolSet.size();
				// managefee = tempValue * peoples;
				// logger.debug(managefee);
				managefee = tempValue;
				// managefee=parseFloat(String.valueOf(managefee));
				return true;
			} else if (PayPlantype.equals("04")) {
				// 查询该团单下该险种所有保费*比例
				LCPolDB tLCPolDB = new LCPolDB();
				LCPolSet tLCPolSet = new LCPolSet();
				sql = "select * from lcpol where grpcontno='" + GrpContno
						+ "' and riskcode='" + RiskCode + "'";
				tLCPolSet = tLCPolDB.executeQuery(sql);
				double sumprem = 0;
				for (int i = 1; i <= tLCPolSet.size(); i++) {
					sumprem = sumprem + tLCPolSet.get(i).getPrem();
				}
				managefee = sumprem * tempValue;
				// managefee=parseFloat(String.valueOf(managefee));
				return true;
			} else if (PayPlantype.equals("07")) {
				LCPolDB tLCPolDB = new LCPolDB();
				LCPolSet tLCPolSet = new LCPolSet();
				sql = "select * from lcpol where grpcontno='" + GrpContno
						+ "' and riskcode='" + RiskCode + "'";
				tLCPolSet = tLCPolDB.executeQuery(sql);
				double sumprem = 0;
				for (int i = 1; i <= tLCPolSet.size(); i++) {
					sumprem = sumprem + tLCPolSet.get(i).getPrem();
				}

				// LMRiskFeeParamDB tLMRiskFeeParamDB=new LMRiskFeeParamDB();
				// LMRiskFeeParamSet tLMRiskFeeParamSet=new LMRiskFeeParamSet();
				// sql="select * From LMRiskFeeParam where feecode='"+FeeCode+"'
				// and feemin<='"+sumprem+"' and feemax>='"+sumprem+"'";
				// tLMRiskFeeParamSet=tLMRiskFeeParamDB.executeQuery(sql);
				// double temrate=0;
				// if(tLMRiskFeeParamSet.size()>0)
				// {
				// temrate=tLMRiskFeeParamSet.get(1).getFeeRate();
				// }
				// managefee=sumprem*temrate;
				// 它的计算与工资的利率一样
				// if(sumprem>500000)
				// {
				// managefee=50000*0.1+(200000-50000)*0.08+(500000-200000)*0.06+(sumprem-500000)*0.05;
				// }else if(sumprem<=500000 && sumprem>200000)
				// {
				// managefee=50000*0.1+(200000-50000)*0.08+(sumprem-200000)*0.06;
				// }else if(sumprem<=200000 && sumprem>50000)
				// {
				// managefee=50000*0.1+(sumprem-50000)*0.08;
				// }else
				// {
				// managefee=sumprem*0.1;
				// }
				// 先查询PAYPLANCODE
				LMRiskFeeDB tLMRiskFeeDB = new LMRiskFeeDB();
				LMRiskFeeSet tLMRiskFeeSet = new LMRiskFeeSet();
				String tsql = "select * From lmriskfee where feecode='"
						+ FeeCode + "'";
				tLMRiskFeeSet = tLMRiskFeeDB.executeQuery(tsql);
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
						+ GrpContno + "' and riskcode='" + RiskCode
						+ "' and payplancode='" + tPayPlanCode
						+ "' and feecode='" + FeeCode + "' order by feeid";
				tLCGrpFeeParamSet = tLCGrpFeeParamDB.executeQuery(tsql);
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
						// managefee=parseFloat(String.valueOf(managefee));
						return true;
					}
					// 至少俩个分档时的处理,先处理最极端的一种情况
					if (sumprem > str[tt - 2][0]) { // 如果账户金额在最后一个区段
						for (int j = 0; j <= tt - 2; j++) {
							managefee = managefee + str[j][0] * str[j][1];
						}
						managefee = managefee + (sumprem - str[tt - 2][0])
								* str[tt - 1][1];
						// managefee=parseFloat(String.valueOf(managefee));
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
						// managefee = parseFloat(String.valueOf(managefee));
						return true;
					}

					for (int t = 0; t < LocateIndex - 1; t++) {
						managefee = managefee + str[t][0] * str[t][1];
					}
					managefee = managefee + (sumprem - str[LocateIndex - 1][0])
							* str[LocateIndex][1];
					// managefee=parseFloat(String.valueOf(managefee));
					return true;
				} else {
					// 查询LMRiskFeeParam
					LMRiskFeeParamDB tLMRiskFeeParamDB = new LMRiskFeeParamDB();
					LMRiskFeeParamSet tLMRiskFeeParamSet = new LMRiskFeeParamSet();
					tsql = "select * From LMRiskFeeParam where  payplancode='"
							+ tPayPlanCode + "' and feecode='" + FeeCode
							+ "' order by feeid";
					tLMRiskFeeParamSet = tLMRiskFeeParamDB.executeQuery(tsql);
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
							// managefee=parseFloat(String.valueOf(managefee));
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
							// managefee =
							// parseFloat(String.valueOf(managefee));
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
						// managefee=parseFloat(String.valueOf(managefee));
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
				sql = "select * from lcpol where grpcontno='" + GrpContno
						+ "' and riskcode='" + RiskCode + "' and contno='"
						+ ContNo + "'";
				tLCPolSet = tLCPolDB.executeQuery(sql);
				managefee = tLCPolSet.get(1).getPrem() * tempValue;
				managefee = parseFloat(String.valueOf(managefee));
				return true;
			} else if (PayPlantype.equals("07")) {
				LCPolDB tLCPolDB = new LCPolDB();
				LCPolSet tLCPolSet = new LCPolSet();
				sql = "select * from lcpol where grpcontno='" + GrpContno
						+ "' and riskcode='" + RiskCode + "' and contno='"
						+ ContNo + "'";
				tLCPolSet = tLCPolDB.executeQuery(sql);
				double sumprem = tLCPolSet.get(1).getPrem();

				// LMRiskFeeParamDB tLMRiskFeeParamDB=new LMRiskFeeParamDB();
				// LMRiskFeeParamSet tLMRiskFeeParamSet=new LMRiskFeeParamSet();
				// sql="select * From LMRiskFeeParam where feecode='"+FeeCode+"'
				// and feemin<='"+sumprem+"' and feemax>='"+sumprem+"'";
				// tLMRiskFeeParamSet=tLMRiskFeeParamDB.executeQuery(sql);
				// double temrate=0;
				// if(tLMRiskFeeParamSet.size()>0)
				// {
				// temrate=tLMRiskFeeParamSet.get(1).getFeeRate();
				// }
				// managefee=sumprem*temrate;
				// if(sumprem>500000)
				// {
				// managefee=50000*0.1+(200000-50000)*0.08+(500000-200000)*0.06+(sumprem-500000)*0.05;
				// }else if(sumprem<=500000 && sumprem>200000)
				// {
				// managefee=50000*0.1+(200000-50000)*0.08+(sumprem-200000)*0.06;
				// }else if(sumprem<=200000 && sumprem>50000)
				// {
				// managefee=50000*0.1+(sumprem-50000)*0.08;
				// }else
				// {
				// managefee=sumprem*0.1;
				// }
				// 先查询PAYPLANCODE
				LMRiskFeeDB tLMRiskFeeDB = new LMRiskFeeDB();
				LMRiskFeeSet tLMRiskFeeSet = new LMRiskFeeSet();
				String tsql = "select * From lmriskfee where feecode='"
						+ FeeCode + "'";
				tLMRiskFeeSet = tLMRiskFeeDB.executeQuery(tsql);
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
						+ GrpContno + "' and riskcode='" + RiskCode
						+ "' and payplancode='" + tPayPlanCode
						+ "' and feecode='" + FeeCode + "' order by feeid";
				tLCGrpFeeParamSet = tLCGrpFeeParamDB.executeQuery(tsql);
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
						managefee = parseFloat(String.valueOf(managefee));
						return true;
					}
					// 至少俩个分档时的处理,先处理最极端的一种情况
					if (sumprem > str[tt - 2][0]) { // 如果账户金额在最后一个区段
						for (int j = 0; j <= tt - 2; j++) {
							managefee = managefee + str[j][0] * str[j][1];
						}
						managefee = managefee + (sumprem - str[tt - 2][0])
								* str[tt - 1][1];
						managefee = parseFloat(String.valueOf(managefee));
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
					managefee = parseFloat(String.valueOf(managefee));
					return true;
				} else {
					// 查询LMRiskFeeParam
					LMRiskFeeParamDB tLMRiskFeeParamDB = new LMRiskFeeParamDB();
					LMRiskFeeParamSet tLMRiskFeeParamSet = new LMRiskFeeParamSet();
					tsql = "select * From LMRiskFeeParam where  payplancode='"
							+ tPayPlanCode + "' and feecode='" + FeeCode
							+ "' order by feeid";
					tLMRiskFeeParamSet = tLMRiskFeeParamDB.executeQuery(tsql);
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
							managefee = parseFloat(String.valueOf(managefee));
							return true;
						}
						// 至少俩个分档时的处理,先处理最极端的一种情况
						if (sumprem > str[tt - 2][0]) { // 如果账户金额在最后一个区段
							for (int j = 0; j <= tt - 2; j++) {
								managefee = managefee + str[j][0] * str[j][1];
							}
							managefee = managefee + (sumprem - str[tt - 2][0])
									* str[tt - 1][1];
							managefee = parseFloat(String.valueOf(managefee));
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
						managefee = parseFloat(String.valueOf(managefee));
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
	}
}
