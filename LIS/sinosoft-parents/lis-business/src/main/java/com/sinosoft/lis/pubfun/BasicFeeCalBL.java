package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpFeeDB;
import com.sinosoft.lis.db.LCGrpFeeParamDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMDutyPayDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LMRiskFeeDB;
import com.sinosoft.lis.db.LMRiskFeeParamDB;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpFeeSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.vschema.LCGrpFeeParamSet;
import com.sinosoft.lis.vschema.LCGrpFeeSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LMDutyPaySet;
import com.sinosoft.lis.vschema.LMRiskFeeParamSet;
import com.sinosoft.lis.vschema.LMRiskFeeSet;
import com.sinosoft.lis.vschema.LMRiskSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;

/**
 * <p>
 * ClassName: JdbcUrl
 * </p>
 * <p>
 * Description: 提供计算管理费的基本算法及团单总体外缴管理费算法
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 */
public class BasicFeeCalBL {
private static Logger logger = Logger.getLogger(BasicFeeCalBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 全局数据 */
	private Reflections ref = new Reflections();
	private TransferData mTransferData = new TransferData();
	public double manaFee; // 需要返回的管理费
	public double manaFeeRate; // 需要返回的比例

	public BasicFeeCalBL() {
	}

	public double computeManaFee(double baseMoney, String payplancode,
			String polno, double sumprem, String tInsureaccno) {
		// 查询管理费定义。
		LCGrpFeeDB tLCGrpFeeDB = new LCGrpFeeDB();
		LCGrpFeeSet tLCGrpFeeSet = new LCGrpFeeSet();
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		LMDutyPayDB tLMDutyPayDB = new LMDutyPayDB();
		tLMDutyPayDB.setPayPlanCode(payplancode);
		tLCPolDB.setPolNo(polno);
		tLCPolSet = tLCPolDB.query(); // 取得分单号。
		String tGrpPolno = tLCPolSet.get(1).getGrpPolNo();
		tLCGrpFeeDB.setGrpPolNo(tLCPolSet.get(1).getGrpPolNo());
		tLCGrpFeeDB.setInsuAccNo(tInsureaccno);
		tLCGrpFeeSet = tLCGrpFeeDB.query();

		LCGrpFeeSchema tFeeSchema = tLCGrpFeeSet.get(1);
		String calMode = tFeeSchema.getFeeCalMode();

		if (calMode == null || "01".equals(calMode)) { // 内扣固定值

			if (tLCGrpFeeSet.size() > 0) {
				manaFee = tFeeSchema.getFeeValue();

			}

			manaFeeRate = this.calInnerRate(manaFee, baseMoney);
		} else if (calMode.equals("02")) { // 内扣比例

			if (tLCGrpFeeSet.size() > 0) {
				manaFeeRate = tFeeSchema.getFeeValue();
			}

			manaFee = this.calInnerManaFee(baseMoney, manaFeeRate);

		} else if (calMode.equals("03")) { // 外缴-固定值

			if (tLCGrpFeeSet.size() > 0) {
				manaFee = tFeeSchema.getFeeValue();

			}

			manaFeeRate = this.calOutRate(manaFee, baseMoney);
		} else if (calMode.equals("04")) { // 外缴-比例值
			if (tLCGrpFeeSet.size() > 0) {
				manaFeeRate = tFeeSchema.getFeeValue();
			}

			manaFee = Arith.round(baseMoney * manaFeeRate, 2);
		} else if (calMode.equals("05")) { // 固定值，比例计算后取较小值
			if (tLCGrpFeeSet.size() > 0) {
				manaFeeRate = tFeeSchema.getFeeValue();
			}

			manaFee = this.calManaFeeMinRate(baseMoney, tFeeSchema
					.getCompareValue(), manaFeeRate);
		} else if (calMode.equals("06")) { // 固定值，比例计算后取较大值
			if (tLCGrpFeeSet.size() > 0) {
				manaFeeRate = tFeeSchema.getFeeValue();
			}

			manaFee = this.calManaFeeMaxRate(baseMoney, tFeeSchema
					.getCompareValue(), manaFeeRate);
		} else if (calMode.equals("08")) { // 分档计算内扣

			manaFeeRate = 0; // 不同金额的档比例不同。
			manaFee = 0;
			// 分个人帐户与法人帐户两种，公共帐户不收取管理费,这个是无所谓的，162对于公共帐户只是一个资金转移帐户
			// 现在定制与以前相同的情况，即：总保费累计一个档次

			double tPrem = baseMoney;

			double tSumPrem = sumprem;
			String tsql = "";
			tsql = "select * from LCGrpFeeParam where grppolno='" + "?a15?"
					+ "' and insuaccno='" + "?a16?" + "'  and feemin<="
					+ "?a17?" + " and feemax>" + "?a18?" + " order by feeid";
			SQLwithBindVariables sqlbv18=new SQLwithBindVariables();
			sqlbv18.sql(tsql);
			sqlbv18.put("a15", tGrpPolno);
			sqlbv18.put("a16", tInsureaccno);
			sqlbv18.put("a17", tSumPrem);
			sqlbv18.put("a18", tSumPrem);
			LCGrpFeeParamDB tLCGrpFeeParamDB = new LCGrpFeeParamDB();
			LCGrpFeeParamSet tLCGrpFeeParamSet = new LCGrpFeeParamSet();
			tLCGrpFeeParamSet = tLCGrpFeeParamDB.executeQuery(sqlbv18);
			manaFeeRate = tLCGrpFeeParamSet.get(1).getFeeRate();
			manaFee = Arith.round(tPrem * manaFeeRate, 2);

		}

		return manaFee;
	}

	/**
	 * 查询团单管理费计算参数
	 * 
	 * @param InsureAccNo
	 *            String
	 * @param payPlanCode
	 *            String
	 * @param feebase
	 *            double
	 * @return LCGrpFeeParamSchema
	 */
	private double queryLCGrpFeeParamSchema(String InsureAccNo,
			String payPlanCode, double feebase, LCPolSchema mLCPolSchema) {
		double feerate = 0;
		// String sql = "select * from LCGrpFeeParam where ";
		StringBuffer sb = new StringBuffer();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sb.append(" select * from LCGrpFeeParam where ");
		sb.append("GrpPolNo='").append("?GrpPolNo?").append("'");
		sb.append(" and InsuAccNo='").append("?InsureAccNo?").append("'");
		sb.append(" and PayPlanCode='").append("?payPlanCode?").append("'");
		sb.append(" and FeeMin<=").append("?feebase?");
		sb.append(" and feemax>").append("?feebase?");
		sqlbv.sql(sb.toString());
		sqlbv.put("GrpPolNo", mLCPolSchema.getGrpPolNo());
		sqlbv.put("InsureAccNo", InsureAccNo);
		sqlbv.put("payPlanCode", payPlanCode);
		sqlbv.put("feebase", feebase);
		LCGrpFeeParamDB tLCGrpFeeParamDB = new LCGrpFeeParamDB();
		LCGrpFeeParamSet tLCGrpFeeParamSet = tLCGrpFeeParamDB.executeQuery(sqlbv);
		if (tLCGrpFeeParamDB.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLCGrpFeeParamDB.mErrors);
			return 0;
		}
		if (tLCGrpFeeParamSet == null || tLCGrpFeeParamSet.size() == 0) {
			// CError.buildErr(this, "参数表描述取值不唯一!");
			// return null;
			// 需要查询默认的描述值
			LMRiskFeeParamDB tLMRiskFeeParamDB = new LMRiskFeeParamDB();
			LMRiskFeeParamSet tLMRiskFeeParamSet = new LMRiskFeeParamSet();
			String sql = "select * from LMRiskFeeParam where PayPlanCode='"
					+ "?PayPlanCode?" + "' and FeeMin<='" + "?FeeMin?"
					+ "' and feemax>'" + "?feemax?" + "'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(sql);
			sqlbv1.put("PayPlanCode", payPlanCode);
			sqlbv1.put("FeeMin", feebase);
			sqlbv1.put("feemax", feebase);
	
			logger.debug(sql);
			tLMRiskFeeParamSet = tLMRiskFeeParamDB.executeQuery(sqlbv1);
			feerate = tLMRiskFeeParamSet.get(1).getFeeRate();
		} else {
			feerate = tLCGrpFeeParamSet.get(1).getFeeRate();
		}

		// return tLCGrpFeeParamSet.get(1);
		return feerate;

	}

	/**
	 * 固定值和比例结合，取较大值
	 * 
	 * @param calMode
	 *            String
	 * @param basePrem
	 *            double
	 * @param fixValue
	 *            double
	 * @param rate
	 *            double
	 * @param refValue
	 *            double
	 * @return double
	 */
	private double calManaFeeMaxRate(double basePrem, double fixValue,
			double rate) {
		double manaFee = 0;
		manaFee = this.calInnerManaFee(basePrem, rate);
		if (manaFee < fixValue) {
			return fixValue;
		}
		return manaFee;
	}

	/**
	 * 固定值和比例结合，取较小值
	 * 
	 * @param calMode
	 *            String
	 * @param basePrem
	 *            double
	 * @param fixValue
	 *            double
	 * @param rate
	 *            double
	 * @param refValue
	 *            double
	 * @return double
	 */
	private double calManaFeeMinRate(double basePrem, double fixValue,
			double rate) {
		double manaFee = 0;
		manaFee = this.calInnerManaFee(basePrem, rate);
		if (fixValue < manaFee) {
			return fixValue;
		}
		return manaFee;
	}

	/**
	 * 计算管理费内扣费率
	 * 
	 * @param manaFee
	 *            double
	 * @param prem
	 *            double
	 * @return double
	 */
	private double calInnerRate(double manaFee, double prem) {
		if (prem == 0) {
			return 1;
		} else {
			return manaFee / prem;
		}
	}

	/**
	 * 计算管理费外缴费率
	 * 
	 * @param manaFee
	 *            double
	 * @param prem
	 *            double
	 * @return double
	 */
	private double calOutRate(double manaFee, double prem) {
		if (prem == 0) {
			return 1;
		} else {
			return manaFee / prem;
		}
	}

	/**
	 * 计算管理费 － 内扣
	 * 
	 * @param prem
	 *            double
	 * @param rate
	 *            double
	 * @return double
	 */
	private double calInnerManaFee(double prem, double rate) {
		return prem * rate;
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

	/**
	 * 计算团单下外缴管理费
	 * 
	 * @param tLCGrpContSchema
	 *            LCGrpContSchema
	 * @return double
	 */
	public double VerifyOutPayMoney(LCGrpContSchema tLCGrpContSchema) {
		double temvale = 0;
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		tLCGrpPolDB.setPrtNo(tLCGrpContSchema.getPrtNo());
		tLCGrpPolSet = tLCGrpPolDB.query();
		LCGrpPolSchema tLCGrpPolSchema;
		for (int i = 1; i <= tLCGrpPolSet.size(); i++) {
			tLCGrpPolSchema = new LCGrpPolSchema();
			tLCGrpPolSchema = tLCGrpPolSet.get(i);
			if (tLCGrpPolSchema.getRiskCode().equals("139")) {
				continue;
			}
			LMRiskDB tLMRiskDB = new LMRiskDB();
			LMRiskSet tLMRiskSet = new LMRiskSet();
			tLMRiskDB.setRiskCode(tLCGrpPolSchema.getRiskCode());
			tLMRiskSet = tLMRiskDB.query();
			LMRiskSchema tLMRiskSchema = tLMRiskSet.get(1);
			if (tLMRiskSchema.getInsuAccFlag().equals("N")) {
				continue;
			}
			// 查询该分单是否有外缴管理费。
			LCGrpFeeDB tLCGrpFeeDB = new LCGrpFeeDB();
			LCGrpFeeSet tLCGrpFeeSet = new LCGrpFeeSet();
			tLCGrpFeeDB.setGrpPolNo(tLCGrpPolSchema.getGrpPolNo());
			tLCGrpFeeSet = tLCGrpFeeDB.query();
			LCGrpFeeSchema tLCGrpFeeSchema;
			for (int j = 1; j <= tLCGrpFeeSet.size(); j++) {
				tLCGrpFeeSchema = new LCGrpFeeSchema();
				tLCGrpFeeSchema = tLCGrpFeeSet.get(j);
				String ttInsurAccNO;
				String ttTakePlace;
				if (tLCGrpFeeSchema.getFeeCalMode().equals("03")
						|| tLCGrpFeeSchema.getFeeCalMode().equals("04")) {
					// 判断该管理费收取点是不是收费时。
					ttInsurAccNO = tLCGrpFeeSchema.getInsuAccNo();
					LMRiskFeeDB tLMRiskFeeDB = new LMRiskFeeDB();
					LMRiskFeeSet tLMRiskFeeSet = new LMRiskFeeSet();
					tLMRiskFeeDB.setInsuAccNo(ttInsurAccNO);
					tLMRiskFeeSet = tLMRiskFeeDB.query();
					ttTakePlace = tLMRiskFeeSet.get(1).getFeeTakePlace();
					ExeSQL tExeSQL = new ExeSQL();
					if (ttTakePlace.equals("01")) {
						SSRS ttSSRS = new SSRS();
						String ttsql;
//						ttsql = "select owner from lmriskinsuacc where insuaccno=(select feecode from lmriskfee where insuaccno='"
//								+ ttInsurAccNO + "')";
						ttsql = "select owner from lmriskinsuacc where insuaccno='"+"?insuaccno?"+"'";
						SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
						sqlbv1.sql(ttsql);
						sqlbv1.put("insuaccno", ttInsurAccNO);
						ttSSRS = tExeSQL.execSQL(sqlbv1);
						String ttOwner = ttSSRS.GetText(1, 1);
						// 计算外缴管理费进行累加
						// LMRiskInsuAccDB tLMRiskInsuAccDB=new
						// LMRiskInsuAccDB();
						// LMRiskInsuAccSet tLMRiskInsuAccSet=new
						// LMRiskInsuAccSet();
						// tLMRiskInsuAccDB.setInsuAccNo(ttInsurAccNO);
						// tLMRiskInsuAccSet=tLMRiskInsuAccDB.query();
						/*"Oracle：select nvl(sum(prem),0) from ljtempfee_lmriskapp
						改造为：select (case when sum(prem) is not null then sum(prem)  else 0 end)  from ljtempfee_lmriskapp;"
                        */
						if (ttOwner.equals("0")) { // 个人账户

							SSRS tSSRS = new SSRS();
							SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
							if (tLCGrpFeeSchema.getFeeCalMode().equals("03")) {
								ttsql = "select (case when count(*) is not null then count(*) else 0 end) from lcpol where grppolno='"
										+ "?grppolno?"
										+ "' and (poltypeflag is null or poltypeflag='0')";
								
								sqlbv2.sql(ttsql);
								sqlbv2.put("grppolno", tLCGrpPolSchema.getGrpPolNo());
							} else {
								ttsql = "select (case when sum(prem) is not null then sum(prem) else 0 end) from lcpol where grppolno='"
										+ "?grppolno1?"
										+ "' and (poltypeflag is null or poltypeflag='0')";
								sqlbv2.sql(ttsql);
								sqlbv2.put("grppolno1", tLCGrpPolSchema.getGrpPolNo());
							}
							tSSRS = tExeSQL.execSQL(sqlbv2);
							temvale = temvale
									+ Float.parseFloat(tSSRS.GetText(1, 1))
									* tLCGrpFeeSchema.getFeeValue();
						} else if (ttOwner.equals("1")) { // 法人账户
							if (tLCGrpFeeSchema.getFeeCalMode().equals("03")) {
								temvale = temvale
										+ tLCGrpFeeSchema.getFeeValue();
							} else {
								SSRS tSSRS = new SSRS();
								ttsql = "select (case when sum(prem) is not null then sum(prem) else 0 end) from lcpol where grppolno='"
										+ "?grppolno2?"
										+ "' and (poltypeflag='3')";
								SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
								sqlbv4.sql(ttsql);
								sqlbv4.put("grppolno2", tLCGrpPolSchema.getGrpPolNo());
								tSSRS = tExeSQL.execSQL(sqlbv4);
								temvale = temvale
										+ Float.parseFloat(tSSRS.GetText(1, 1))
										* tLCGrpFeeSchema.getFeeValue();
							}

						} else if (ttOwner.equals("2")) { // 公共账户
							// 面前只有139险种考虑。
							
						} else {
							logger.debug("产品定义错误");
						}
					} else {
						continue;
					}
				} else {
					continue;
				}
			}
		}
		return temvale;
	}

	/**
	 * 计算团单下内扣管理费
	 * 
	 * @param tLCGrpContSchema
	 *            LCGrpContSchema
	 * @return double
	 */
	public double VerifyInerPayMoney(LCGrpContSchema tLCGrpContSchema) {
		double temvale = 0;
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		tLCGrpPolDB.setPrtNo(tLCGrpContSchema.getPrtNo());
		tLCGrpPolSet = tLCGrpPolDB.query();
		LCGrpPolSchema tLCGrpPolSchema;
		for (int i = 1; i <= tLCGrpPolSet.size(); i++) {
			tLCGrpPolSchema = new LCGrpPolSchema();
			tLCGrpPolSchema = tLCGrpPolSet.get(i);
			if (tLCGrpPolSchema.getRiskCode().equals("139")) {
				continue;
			}
			LMRiskDB tLMRiskDB = new LMRiskDB();
			LMRiskSet tLMRiskSet = new LMRiskSet();
			tLMRiskDB.setRiskCode(tLCGrpPolSchema.getRiskCode());
			tLMRiskSet = tLMRiskDB.query();
			LMRiskSchema tLMRiskSchema = tLMRiskSet.get(1);
			if (tLMRiskSchema.getInsuAccFlag().equals("N")) {
				continue;
			}
			// 查询该分单是否有外缴管理费。
			LCGrpFeeDB tLCGrpFeeDB = new LCGrpFeeDB();
			LCGrpFeeSet tLCGrpFeeSet = new LCGrpFeeSet();
			tLCGrpFeeDB.setGrpPolNo(tLCGrpPolSchema.getGrpPolNo());
			tLCGrpFeeSet = tLCGrpFeeDB.query();
			LCGrpFeeSchema tLCGrpFeeSchema;
			for (int j = 1; j <= tLCGrpFeeSet.size(); j++) {
				tLCGrpFeeSchema = new LCGrpFeeSchema();
				tLCGrpFeeSchema = tLCGrpFeeSet.get(j);
				String ttInsurAccNO;
				String ttTakePlace;
				if (tLCGrpFeeSchema.getFeeCalMode().equals("01")
						|| tLCGrpFeeSchema.getFeeCalMode().equals("02")) {
					// 判断该管理费收取点是不是收费时。
					ttInsurAccNO = tLCGrpFeeSchema.getInsuAccNo();
					LMRiskFeeDB tLMRiskFeeDB = new LMRiskFeeDB();
					LMRiskFeeSet tLMRiskFeeSet = new LMRiskFeeSet();
					tLMRiskFeeDB.setInsuAccNo(ttInsurAccNO);
					tLMRiskFeeSet = tLMRiskFeeDB.query();
					ttTakePlace = tLMRiskFeeSet.get(1).getFeeTakePlace();
					ExeSQL tExeSQL = new ExeSQL();
					if (ttTakePlace.equals("01")) {
						SSRS ttSSRS = new SSRS();
						String ttsql;
						ttsql = "select owner from lmriskinsuacc where insuaccno='"+"?insuaccno?"+"' ";
						SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
						sqlbv5.sql(ttsql);
						sqlbv5.put("insuaccno", ttInsurAccNO);
						//		"(select feecode from lmriskfee where insuaccno='"
						//		+ ttInsurAccNO + "')";
						ttSSRS = tExeSQL.execSQL(sqlbv5);
						String ttOwner = ttSSRS.GetText(1, 1);
						// 计算外缴管理费进行累加

						if (ttOwner.equals("0")) { // 个人账户

							SSRS tSSRS = new SSRS();
							SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
							if (tLCGrpFeeSchema.getFeeCalMode().equals("01")) {
								//tongmeng 2008-09-10 modify
								//到缴费项
								/*"Oracle：select nvl(sum(prem),0) from ljtempfee_lmriskapp
								改造为：select (case when sum(prem) is not null then sum(prem)  else 0 end)  from ljtempfee_lmriskapp;"
                                */
								ttsql = "select (case when count(*) is not null then count(*) else 0 end) from lcprem where "
									  + " polno in (select polno from lcpol where grppolno ='"+"?grppolno?"+"' and (poltypeflag is null or poltypeflag='0'))"
									  + " and payplancode='"+"?payplancode?"+"' ";
								
								sqlbv6.sql(ttsql);
								sqlbv6.put("grppolno", tLCGrpPolSchema.getGrpPolNo());
								sqlbv6.put("payplancode", tLCGrpFeeSchema.getPayPlanCode());
 
							} else {
								ttsql = "select (case when sum(prem) is not null then sum(prem) else 0 end) from lcprem where "
										+ " polno in (select polno from lcpol where grppolno ='"+"?grppolno1?"+"' and (poltypeflag is null or poltypeflag='0'))" 
										+ " and payplancode='"+"?payplancode1?"+"' ";
								sqlbv6.sql(ttsql);
								sqlbv6.put("grppolno1", tLCGrpPolSchema.getGrpPolNo());
								sqlbv6.put("payplancode1", tLCGrpFeeSchema.getPayPlanCode());
							}
							tSSRS = tExeSQL.execSQL(sqlbv6);
							temvale = temvale
									+ Float.parseFloat(tSSRS.GetText(1, 1))
									* tLCGrpFeeSchema.getFeeValue();
						} else if (ttOwner.equals("1")) { // 法人账户
							if (tLCGrpFeeSchema.getFeeCalMode().equals("01")) {
								temvale = temvale
										+ tLCGrpFeeSchema.getFeeValue();
							} else {
								SSRS tSSRS = new SSRS();
								ttsql = "select (case when sum(prem) is not null then sum(prem) else 0 end) from lcpol where grppolno='"
										+ "?grppolno3?"
										+ "' and (poltypeflag='3')";
								SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
								sqlbv8.sql(ttsql);
								sqlbv8.put("grppolno3", tLCGrpPolSchema.getGrpPolNo());
								tSSRS = tExeSQL.execSQL(sqlbv8);
								temvale = temvale
										+ Float.parseFloat(tSSRS.GetText(1, 1))
										* tLCGrpFeeSchema.getFeeValue();
							}

						} else if (ttOwner.equals("2")) { // 公共账户
							// 面前只有139险种考虑。
							//佟盟 2008-08-10 modify
							//公共账户也有管理费 
							SSRS tSSRS = new SSRS();
							SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
							if (tLCGrpFeeSchema.getFeeCalMode().equals("01")) {
								//tongmeng 2008-09-10 modify
								//到缴费项
								ttsql = "select (case when count(*) is not null then count(*) else 0 end) from lcprem where "
									  + " polno in (select polno from lcpol where grppolno ='"+"?f1?"+"' and ( poltypeflag='2'))"
									  + " and payplancode='"+"?f2?"+"' ";
								
								sqlbv9.sql(ttsql);
								sqlbv9.put("f1", tLCGrpPolSchema.getGrpPolNo());
								sqlbv9.put("f2", tLCGrpFeeSchema.getPayPlanCode());
 
							} else {
								ttsql = "select (case when sum(prem) is not null then sum(prem) else 0 end) from lcprem where "
										+ " polno in (select polno from lcpol where grppolno ='"+"?f3?"+"' and ( poltypeflag='2'))" 
										+ " and payplancode='"+"?f4?"+"' ";
								sqlbv9.sql(ttsql);
								sqlbv9.put("f3", tLCGrpPolSchema.getGrpPolNo());
								sqlbv9.put("f4", tLCGrpFeeSchema.getPayPlanCode());
							}
							tSSRS = tExeSQL.execSQL(sqlbv9);
							temvale = temvale
									+ Float.parseFloat(tSSRS.GetText(1, 1))
									* tLCGrpFeeSchema.getFeeValue();
						} else {
							logger.debug("产品定义错误");
						}
					} else {
						continue;
					}
				} else if (tLCGrpFeeSchema.getFeeCalMode().equals("08")) {
					ttInsurAccNO = tLCGrpFeeSchema.getInsuAccNo();
					double tSumPrem = tLCGrpPolSchema.getPrem(); // 总保费
					String tsql = "";

					tsql = "select * from LCGrpFeeParam where grppolno='"
							+ "?s1?"
							+ "' and insuaccno='" + "?s2?"
							+ "' and feemin<=" + "?s3?" + " and feemax>"
							+ "?s4?" + " order by feeid";
					SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
					sqlbv11.sql(tsql);
					sqlbv11.put("s1", tLCGrpFeeSchema.getGrpPolNo());
					sqlbv11.put("s2", ttInsurAccNO);
					sqlbv11.put("s3", tSumPrem);
					sqlbv11.put("s4", tSumPrem);

					LCGrpFeeParamDB tLCGrpFeeParamDB = new LCGrpFeeParamDB();
					LCGrpFeeParamSet tLCGrpFeeParamSet = new LCGrpFeeParamSet();
					tLCGrpFeeParamSet = tLCGrpFeeParamDB.executeQuery(sqlbv11);
					manaFeeRate = tLCGrpFeeParamSet.get(1).getFeeRate();
					manaFee = tSumPrem * manaFeeRate;
					temvale = temvale + manaFee;
					temvale = Arith.round(temvale, 2);
				} else {
					continue;
				}
			}
		}
		return temvale;

	}

	/**
	 * 计算团单下139管理费
	 * 
	 * @param tLCGrpContSchema
	 *            LCGrpContSchema
	 * @return double
	 */

	public double VerifySpeciacOutFee(String tGrpContNo, String FeeCalMode) {
		double tmanagefee = 0;
		ManageFeeCalBL tManageFeeCalBL = new ManageFeeCalBL();

		if (!tManageFeeCalBL.submitData(tGrpContNo, "139", FeeCalMode, "1",
				"000000", "390002")) {
			logger.debug("139外缴管理费计算失败！");
		} else {
			tmanagefee = tmanagefee + tManageFeeCalBL.managefee;

		}
		return tmanagefee;
	}

	/**
	 * 计算缴费项对应的年费
	 * 
	 * @param tLCInsureAccclass
	 *            LCInsureAccclassSchema
	 * @return double
	 */

	public double VerifyYearFee(LCInsureAccClassSchema tLCInsureAccClassSchema) {
		double tmanagefee = 0;
		// 查询定义取出年费，注意缴费账户
		String tInsuAccNo = "880005";
		String tOrign = "0";
		LMDutyPayDB tLMDutyPayDB = new LMDutyPayDB();
		LMDutyPaySet ttLMDutyPaySet = new LMDutyPaySet();
		tLMDutyPayDB.setPayPlanCode(tLCInsureAccClassSchema.getPayPlanCode());
		ttLMDutyPaySet = tLMDutyPayDB.query();
		// tInsuAccNo = ttLMDutyPaySet.get(1).getDefaultVal();
		SSRS ttSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		String ttsql;
		ttsql = "select owner from lmriskinsuacc where insuaccno=(select feecode from lmriskfee where insuaccno='"
				+ "?a1?" + "' and feetakeplace='01')";
		SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
		sqlbv12.sql(ttsql);
		sqlbv12.put("a1", tInsuAccNo);
	
		ttSSRS = tExeSQL.execSQL(sqlbv12);
		String ttOwner = ttSSRS.GetText(1, 1);
		if (ttOwner.equals("1")) {
			tOrign = "1"; // 法人账户
		} else {
			tOrign = "0"; // 个人账户
		}
		if (tLCInsureAccClassSchema.getRiskCode().equals("162"))

			ttsql = "select insuaccno from lmriskfee where  feetakeplace='01' and payplancode=(select payplancode from lmriskfee where insuaccno='"
					+ "?a2?" + "')"; // 暂时用管理费代替年费，把程序跑完，等年费描完再改、
		
		else
			
			ttsql = "select insuaccno from lmriskfee where  feetakeplace='02' and payplancode=(select payplancode from lmriskfee where insuaccno='"
					+ "?a3?" + "')";
		SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
		sqlbv13.sql(ttsql);
		sqlbv13.put("a2", tInsuAccNo);
		sqlbv13.put("a3", tInsuAccNo);
		ttSSRS = tExeSQL.execSQL(sqlbv13);
		String mInsuaccno = ttSSRS.GetText(1, 1);
		// 查询年费管理费定义
		LCGrpFeeDB tLCGrpFeeDB = new LCGrpFeeDB();
		LCGrpFeeSet tLCGrpFeeSet = new LCGrpFeeSet();
		ttsql = "select * From lcgrpfee where grppolno='"
				+ "?a4?" + "' and insuaccno='"
				+ "?a5?" + "'";
		SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
		sqlbv14.sql(ttsql);
		sqlbv14.put("a4", tLCInsureAccClassSchema.getGrpPolNo());
		sqlbv14.put("a5", mInsuaccno);
		logger.debug(ttsql);
		tLCGrpFeeSet = tLCGrpFeeDB.executeQuery(sqlbv14);
		if (tLCGrpFeeSet == null || tLCGrpFeeSet.size() == 0) {
			return -1; // 年费没有定义，需要业务程序进行错误日志处理。
		}
		String feecalcode = tLCGrpFeeSet.get(1).getFeeCalCode();
		// 分红全是从个人账户收年费
		if (tLCInsureAccClassSchema.getRiskCode().equals("162")) {
			String feesql = "select * from lcinsureacc where polno='"
					+ "?a6?" + "' and insuaccno='"
					+ "?a7?" + "'";
			SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
			sqlbv15.sql(feesql);
			sqlbv15.put("a6", tLCInsureAccClassSchema.getPolNo());
			sqlbv15.put("a7", tLCInsureAccClassSchema.getInsuAccNo());
			LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
			LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
			tLCInsureAccSet = tLCInsureAccDB.executeQuery(sqlbv15);
			double yearfee = 0;
			yearfee = tLCInsureAccSet.get(1).getInsuAccBala() * 0.01;
			if (yearfee > tLCGrpFeeSet.get(1).getFeeValue()) {
				tmanagefee = yearfee;
			} else {
				tmanagefee = tLCGrpFeeSet.get(1).getFeeValue();
			}

			// 增加对年费的拆分，年费不以金额而是以账户个数收取。
			LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
			LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
			tLCInsureAccClassDB.setPolNo(tLCInsureAccClassSchema.getPolNo());
			tLCInsureAccClassSet = tLCInsureAccClassDB.query();
			int j = tLCInsureAccClassSet.size();
			tmanagefee = tmanagefee / j;

		} else {
			if (feecalcode.equals("00") && tOrign.equals("0")) {
				// 个人账户从自身收取年费
				logger.debug(tLCInsureAccClassSchema.getAccFoundDate());
				logger.debug(tLCInsureAccClassSchema.getBalaDate());

				int n = PubFun.calInterval(tLCInsureAccClassSchema
						.getAccFoundDate(), tLCInsureAccClassSchema
						.getBalaDate(), "M");
				logger.debug(n);

				if (n == 0)
					n = 1;

				if (n >= 12) { // 如果大于一年,收取一年的管理费
					tmanagefee = tmanagefee + tLCGrpFeeSet.get(1).getFeeValue();
				} else {
					// 如果少于一年说明是首期管理费收取。
					tmanagefee = tmanagefee
							+ (tLCGrpFeeSet.get(1).getFeeValue() * n) / 12;
				}

				// 增加对年费的拆分，年费不以金额而是以账户个数收取。
				LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
				LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
				tLCInsureAccClassDB
						.setPolNo(tLCInsureAccClassSchema.getPolNo());
				tLCInsureAccClassSet = tLCInsureAccClassDB.query();
				int j = tLCInsureAccClassSet.size();
				tmanagefee = tmanagefee / j;
			} else if (feecalcode.equals("01") && tOrign.equals("0")) { // 个人账户从法人收取年费
				tmanagefee = 0;
			} else if (tOrign.equals("1")) { // 法人账户从法人收取年费
				int n = PubFun.calInterval(tLCInsureAccClassSchema
						.getAccFoundDate(), tLCInsureAccClassSchema
						.getBalaDate(), "M");
				if (n == 0)
					n = 1;
				if (n >= 12) { // 如果大于一年,收取一年的管理费
					tmanagefee = tmanagefee + tLCGrpFeeSet.get(1).getFeeValue();
				} else {
					// 如果少于一年说明是首期管理费收取。
					tmanagefee = tmanagefee
							+ (tLCGrpFeeSet.get(1).getFeeValue() * n) / 12;
				}
				// 查询个人年费是否从法人账户扣除。
				ttsql = "select * From lcgrpfee where grppolno='"
						+ "?a8?"
						+ "' and insuaccno='880003'";
				SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
				sqlbv16.sql(ttsql);
				sqlbv16.put("a8", tLCInsureAccClassSchema.getGrpPolNo());
				tLCGrpFeeSet = new LCGrpFeeSet();
				if (tLCGrpFeeSet != null && tLCGrpFeeSet.size() > 0) {
					if (tLCGrpFeeSet.get(1).getFeeCalCode().equals("01")) {
						// 存在个人账户缴费账户是法人账户
						LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
						LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
						String tsql = "select * From LCInsureAccClass where grpcontno='"
								+ "?a11?"
								+ "' and polno!='"
								+ "?a12?" + "'";
						SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
						sqlbv17.sql(tsql);
						sqlbv17.put("a11", tLCInsureAccClassSchema.getGrpContNo());
						sqlbv17.put("a12", tLCInsureAccClassSchema.getPolNo());
						tLCGrpFeeSet = tLCGrpFeeDB.executeQuery(sqlbv16);
						tLCInsureAccSet = tLCInsureAccDB.executeQuery(sqlbv17);
						LCInsureAccSchema tLCInsureAccSchema;
						for (int t = 1; t <= tLCInsureAccSet.size(); t++) {
							tLCInsureAccSchema = new LCInsureAccSchema();
							tLCInsureAccSchema = tLCInsureAccSet.get(t);
							int k = PubFun.calInterval(tLCInsureAccSchema
									.getAccFoundDate(), tLCInsureAccSchema
									.getBalaDate(), "M");
							if (k == 0)
								k = 1;
							if (k >= 12) { // 如果大于一年,收取一年的管理费
								tmanagefee = tmanagefee
										+ tLCGrpFeeSet.get(1).getFeeValue();
							} else {
								// 如果少于一年说明是首期管理费收取。
								tmanagefee = tmanagefee
										+ (tLCGrpFeeSet.get(1).getFeeValue() * k)
										/ 12;
							}

						}
					}
				}
			}
		}
		tmanagefee = Arith.round(tmanagefee, 2);
		return tmanagefee;
	}

	public static void main(String[] args) {

	}
}
