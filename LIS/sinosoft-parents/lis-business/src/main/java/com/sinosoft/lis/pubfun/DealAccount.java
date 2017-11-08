/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCGrpFeeDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccClassFeeDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCInsureAccFeeDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LCPremToAccDB;
import com.sinosoft.lis.db.LMDutyGetDB;
import com.sinosoft.lis.db.LMDutyPayDB;
import com.sinosoft.lis.db.LMRiskAccGetDB;
import com.sinosoft.lis.db.LMRiskAccPayDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LMRiskFeeDB;
import com.sinosoft.lis.db.LMRiskInsuAccDB;
import com.sinosoft.lis.db.LMRiskSortDB;
import com.sinosoft.lis.db.LMRiskToAccDB;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCGetToAccSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCInsureAccClassFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccFeeTraceSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LCPremToAccSchema;
import com.sinosoft.lis.schema.LMDutyGetSchema;
import com.sinosoft.lis.schema.LMDutyPaySchema;
import com.sinosoft.lis.schema.LMRiskAccGetSchema;
import com.sinosoft.lis.schema.LMRiskAccPaySchema;
import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.schema.LMRiskFeeSchema;
import com.sinosoft.lis.schema.LMRiskInsuAccSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.schema.LMRiskToAccSchema;
import com.sinosoft.lis.vdb.LCInsureAccTraceDBSet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCGetToAccSet;
import com.sinosoft.lis.vschema.LCGrpFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeTraceSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCPremToAccSet;
import com.sinosoft.lis.vschema.LMDutyGetSet;
import com.sinosoft.lis.vschema.LMDutyPaySet;
import com.sinosoft.lis.vschema.LMRiskAccGetSet;
import com.sinosoft.lis.vschema.LMRiskAccPaySet;
import com.sinosoft.lis.vschema.LMRiskFeeSet;
import com.sinosoft.lis.vschema.LMRiskInsuAccSet;
import com.sinosoft.lis.vschema.LMRiskSet;
import com.sinosoft.lis.vschema.LMRiskSortSet;
import com.sinosoft.lis.vschema.LMRiskToAccSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author hzm
 * @version 1.0
 */
public class DealAccount {
private static Logger logger = Logger.getLogger(DealAccount.class);
	/**
	 * 成员变量
	 */
	public CErrors mErrors = new CErrors(); // 错误类
	private String CurrentDate = PubFun.getCurrentDate(); // 系统当前时间
	private String CurrentTime = PubFun.getCurrentTime();
	private String tLimit = ""; // 流水号
	private String serNo = "";

	Reflections rf = new Reflections();
	private GlobalInput mGlobalInput = null;

	public DealAccount() {
	}

	public DealAccount(GlobalInput inGlobalInput) {
		this.mGlobalInput = inGlobalInput;
	}

	public static void main(String[] args) {
		DealAccount dealAccount1 = new DealAccount();
		String PolNo = "86110020020210000217";
		String AccCreatePos = "2"; // －－缴费时产生
		String OtherNo = "86110020020210000217";
		String OtherNoType = "1"; // －－ 个人保单号
		Double Rate = new Double(0.5);
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("PolNo", PolNo);
		tTransferData.setNameAndValue("AccCreatePos", AccCreatePos);
		tTransferData.setNameAndValue("OtherNo", OtherNo);
		tTransferData.setNameAndValue("OtherNoType", OtherNoType);
		tTransferData.setNameAndValue("Rate", Rate);

		VData tVData = new VData();

		dealAccount1.addPremTraceForAcc("86110020030220000068", "000003", 0.0);

		// 测试生成帐户
		// tVData=dealAccount1.createInsureAcc(tTransferData);
		// 测试生成保险帐户表
		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
		tLCInsureAccSet = dealAccount1.getLCInsureAcc(PolNo, AccCreatePos,
				OtherNo, OtherNoType);

		// 测试生成保费项表和客户帐户表的关联表
		LCPremToAccSet tLCPremToAccSet = new LCPremToAccSet();
		tLCPremToAccSet = dealAccount1.getPremToAcc(PolNo, AccCreatePos, Rate);

		// 生成给付项表和客户帐户表的关联表
		LCGetToAccSet tLCGetToAccSet = new LCGetToAccSet();
		tLCGetToAccSet = dealAccount1.getGetToAcc(PolNo, AccCreatePos, Rate);

		// 测试保险账户资金注入
		LCPremSchema tLCPremSchema = new LCPremSchema();
		LCPremSet tLCPremSet = new LCPremSet();
		tLCPremSet = dealAccount1.queryLCPrem(PolNo);
		tLCPremSchema = tLCPremSet.get(1);
		tVData.clear();
		tVData = dealAccount1.addPrem(tLCPremSchema, AccCreatePos, OtherNo,
				OtherNoType, "BF", Rate);
	}

	/**
	 * 生成保险帐户(生成结构:构建保险账户表,构建保费项表和客户账户表的关联表,构建给付项表和客户账户表的关联表)
	 * 
	 * @param parmData
	 *            (Type:TransferData include:
	 *            PolNo，AccCreatePos，OtherNo，OtherNoType，Rate)
	 * @return VData (include: LCInsureAccSet，LCPremToAccSet，LCGetToAccSet)
	 */
	public VData createInsureAcc(TransferData parmData) {
		// 1-检验
		if (!checkTransferData(parmData)) {
			return null;
		}

		// 2-得到数据后用
		String tPolNo = (String) parmData.getValueByName("PolNo");
		String tAccCreatePos = (String) parmData.getValueByName("AccCreatePos");
		String tOtherNo = (String) parmData.getValueByName("OtherNo");
		String tOtherNoType = (String) parmData.getValueByName("OtherNoType");
		Double tRate;
		if (parmData.getValueByName("Rate") == null) {
			tRate = null;
		} else if (parmData.getValueByName("Rate").getClass().getName().equals(
				"java.lang.String")) {
			String strRate = (String) parmData.getValueByName("Rate");
			tRate = Double.valueOf(strRate);
		} else {
			tRate = (Double) parmData.getValueByName("Rate");
		}
		logger.debug("费率:" + tRate);

		// 3-构建保险账户表
		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
		tLCInsureAccSet = getLCInsureAcc(tPolNo, tAccCreatePos, tOtherNo,
				tOtherNoType);
		if (tLCInsureAccSet == null) {
			return null;
		}

		// 4-构建保费项表和客户账户表的关联表
		LCPremToAccSet tLCPremToAccSet = new LCPremToAccSet();
		tLCPremToAccSet = getPremToAcc(tPolNo, tAccCreatePos, tRate);

		// if(tLCPremToAccSet==null) return null;
		// 5-构建给付项表和客户账户表的关联表
		LCGetToAccSet tLCGetToAccSet = new LCGetToAccSet();
		tLCGetToAccSet = getGetToAcc(tPolNo, tAccCreatePos, tRate);

		// if(tLCGetToAccSet==null) return null;
		// 6-返回数据
		VData tVData = new VData();
		tVData.add(tLCInsureAccSet);
		tVData.add(tLCPremToAccSet); // 可能是null
		tVData.add(tLCGetToAccSet); // 可能是null

		return tVData;
	}

	/**
	 * 对个人保单生成保险帐户表(类型 1：空帐户,不需要添加履历表纪录)
	 * 
	 * @param PolNo
	 *            保单号
	 * @param AccCreatePos
	 *            生成位置 :1-投保单录入时产生 2－缴费时产生 3－领取时产生
	 * @param OtherNo
	 *            保单号或交费号
	 * @param OtherNoType
	 *            保单号或交费号
	 * @return LCInsureAccSet
	 */
	public LCInsureAccSet getLCInsureAcc(String PolNo, String AccCreatePos,
			String OtherNo, String OtherNoType) {
		if ((PolNo == null) || (AccCreatePos == null) || (OtherNo == null)
				|| (OtherNoType == null)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "getLCInsureAcc";
			tError.errorMessage = "传入数据不能为空!";
			this.mErrors.addOneError(tError);

			return null;
		}

		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet(); // 保险帐户表

		// 1-查询保单表
		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolSchema = queryLCPol(PolNo);
		if (tLCPolSchema == null) {
			return null;
		}

		// 2-根据投保单表中的险种字段查询LMRisk表
		LMRiskSchema tLMRiskSchema = new LMRiskSchema();
		tLMRiskSchema = queryLMRisk(tLCPolSchema.getRiskCode());
		if (tLMRiskSchema == null) {
			return null;
		}

		// 3-判断是否与帐户相关
		if (tLMRiskSchema.getInsuAccFlag().equals("Y")
				|| tLMRiskSchema.getInsuAccFlag().equals("y")) {
			// 根据险种查询LMRiskToAcc表(险种账户关联表)
			LMRiskToAccSet tLMRiskToAccSet = new LMRiskToAccSet();
			tLMRiskToAccSet = queryLMRiskToAcc(tLCPolSchema.getRiskCode());
			if (tLMRiskToAccSet == null) {
				return null;
			}

			LMRiskToAccSchema tLMRiskToAccSchema = new LMRiskToAccSchema(); // 险种账户关联表
			LMRiskInsuAccSchema tLMRiskInsuAccSchema = new LMRiskInsuAccSchema(); // 险种保险帐户
			LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema(); // 保险帐户表

			for (int i = 1; i <= tLMRiskToAccSet.size(); i++) {
				// 根据保险账户号码查询LMRiskInsuAcc表(险种保险帐户)
				tLMRiskToAccSchema = tLMRiskToAccSet.get(i);
				tLMRiskInsuAccSchema = new LMRiskInsuAccSchema();
				tLMRiskInsuAccSchema = queryLMRiskInsuAcc(tLMRiskToAccSchema
						.getInsuAccNo());
				if (tLMRiskInsuAccSchema == null) {
					// return con;
					continue;
				}

				// LMRiskInsuAccSet tLMRiskInsuAccSet =
				// queryLMRiskInsuAccSet(tLMRiskToAccSchema.getInsuAccNo());
				// if (tLMRiskInsuAccSet == null)
				// {
				// CError.buildErr(this,"账户描述查询失败");
				// return null;
				// }
				// for ( int u=1;u<=tLMRiskInsuAccSet.size();u++)
				// {
				// tLMRiskInsuAccSchema = tLMRiskInsuAccSet.get(u);
				// 如果帐户类型是集体帐户,退出
				if (tLMRiskInsuAccSchema.getAccType().equals("001")) {
					// 如果保单类型是-2 --（团单）公共帐户(例如：众悦年金的集体帐户，从界面录入个人时选择保单类型为2)
					// 此时这个代表集体的个人除了生成个人的账户外，多生成集体的账户
					if ((tLCPolSchema.getPolTypeFlag() != null)
							&& tLCPolSchema.getPolTypeFlag().equals("2")) {
						logger.debug("需要生成集体帐户");
					} else {
						continue;
					}
				}

				// 生成保险账户表
				// 如果账户生成位置找到匹配的保险账户
				if (tLMRiskInsuAccSchema.getAccCreatePos().equals(AccCreatePos)) {
					tLCInsureAccSchema = new LCInsureAccSchema();
					tLCInsureAccSchema.setPolNo(PolNo);
					tLCInsureAccSchema.setInsuAccNo(tLMRiskInsuAccSchema
							.getInsuAccNo());
					tLCInsureAccSchema.setRiskCode(tLMRiskToAccSchema
							.getRiskCode());
					tLCInsureAccSchema.setAccType(tLMRiskInsuAccSchema
							.getAccType());
					// tLCInsureAccSchema.setOtherNo(OtherNo);
					// tLCInsureAccSchema.setOtherType(OtherNoType);
					tLCInsureAccSchema.setContNo(tLCPolSchema.getContNo());
					tLCInsureAccSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
					tLCInsureAccSchema
							.setInsuredNo(tLCPolSchema.getInsuredNo());
					// tLCInsureAccSchema.setAppntName(tLCPolSchema.
					// getAppntName());
					tLCInsureAccSchema.setSumPay(0);
					tLCInsureAccSchema.setInsuAccBala(0);
					tLCInsureAccSchema.setUnitCount(0);
					tLCInsureAccSchema.setInsuAccGetMoney(0);
					tLCInsureAccSchema.setSumPaym(0);
					tLCInsureAccSchema.setFrozenMoney(0);
					tLCInsureAccSchema.setAccComputeFlag(tLMRiskInsuAccSchema
							.getAccComputeFlag());
					tLCInsureAccSchema
							.setManageCom(tLCPolSchema.getManageCom());
					tLCInsureAccSchema.setOperator(tLCPolSchema.getOperator());
					tLCInsureAccSchema.setBalaDate(tLCPolSchema.getCValiDate());
					tLCInsureAccSchema.setMakeDate(CurrentDate);
					tLCInsureAccSchema.setMakeTime(CurrentTime);
					tLCInsureAccSchema.setModifyDate(CurrentDate);
					tLCInsureAccSchema.setModifyTime(CurrentTime);

					// 新增的
					tLCInsureAccSchema
							.setGrpContNo(tLCPolSchema.getGrpContNo());
					tLCInsureAccSchema.setPrtNo(tLCPolSchema.getPrtNo());
					tLCInsureAccSchema.setAppntNo(tLCPolSchema.getAppntNo());

					tLCInsureAccSet.add(tLCInsureAccSchema);
				}
				// }
			}

			return tLCInsureAccSet;
		}

		return null;
	}

	/**
	 * 对集体保单生成保险帐户表(类型 1：空帐户,不需要添加履历表纪录)
	 * 
	 * @param PolNo
	 *            集体保单号
	 * @param AccCreatePos
	 *            生成位置 :1-投保单录入时产生 2－缴费时产生 3－领取时产生
	 * @param OtherNo
	 *            集体保单号或交费号
	 * @param OtherNoType
	 *            集体保单号或交费号
	 * @return LCInsureAccSet
	 */
	public LCInsureAccSet getLCInsureAccForGrp(String GrpPolNo,
			String AccCreatePos, String OtherNo, String OtherNoType) {
		if ((GrpPolNo == null) || (AccCreatePos == null) || (OtherNo == null)
				|| (OtherNoType == null)) {
			// @@错误处理
			logger.debug("getLCInsureAccForGrp传入数据不能为空");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "getLCInsureAcc";
			tError.errorMessage = "传入数据不能为空!";
			this.mErrors.addOneError(tError);

			return null;
		}

		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet(); // 保险帐户表

		// 1-查询保单表
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpPolNo(GrpPolNo);
		if (tLCGrpPolDB.getInfo() == false) {
			return null;
		}

		LCGrpPolSchema tLCGrppolSchema = tLCGrpPolDB.getSchema();

		// 2-根据投保单表中的险种字段查询LMRisk表
		LMRiskSchema tLMRiskSchema = new LMRiskSchema();
		tLMRiskSchema = queryLMRisk(tLCGrppolSchema.getRiskCode());
		if (tLMRiskSchema == null) {
			return null;
		}

		// 3-判断是否与帐户相关
		if (tLMRiskSchema.getInsuAccFlag().equals("Y")
				|| tLMRiskSchema.getInsuAccFlag().equals("y")) {
			// 根据险种查询LMRiskToAcc表(险种账户关联表)
			LMRiskToAccSet tLMRiskToAccSet = new LMRiskToAccSet();
			tLMRiskToAccSet = queryLMRiskToAcc(tLCGrppolSchema.getRiskCode());
			if (tLMRiskToAccSet == null) {
				return null;
			}

			LMRiskToAccSchema tLMRiskToAccSchema = new LMRiskToAccSchema(); // 险种账户关联表
			LMRiskInsuAccSchema tLMRiskInsuAccSchema = new LMRiskInsuAccSchema(); // 险种保险帐户
			LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema(); // 保险帐户表

			for (int i = 1; i <= tLMRiskToAccSet.size(); i++) {
				// 根据保险账户号码查询LMRiskInsuAcc表(险种保险帐户)
				tLMRiskToAccSchema = tLMRiskToAccSet.get(i);
				tLMRiskInsuAccSchema = new LMRiskInsuAccSchema();
				tLMRiskInsuAccSchema = queryLMRiskInsuAcc(tLMRiskToAccSchema
						.getInsuAccNo());
				if (tLMRiskInsuAccSchema == null) {
					return null;
				}

				// 如果帐户类型不是集体帐户,退出
				if (!tLMRiskInsuAccSchema.getAccType().equals("001")) {
					continue;
				}

				// 生成保险账户表
				// 如果账户生成位置找到匹配的保险账户
				if (tLMRiskInsuAccSchema.getAccCreatePos().equals(AccCreatePos)) {
					tLCInsureAccSchema = new LCInsureAccSchema();
					tLCInsureAccSchema.setPolNo(tLCGrppolSchema.getGrpPolNo());
					tLCInsureAccSchema.setInsuAccNo(tLMRiskInsuAccSchema
							.getInsuAccNo());
					tLCInsureAccSchema.setRiskCode(tLMRiskToAccSchema
							.getRiskCode());
					tLCInsureAccSchema.setAccType(tLMRiskInsuAccSchema
							.getAccType());
					// tLCInsureAccSchema.setOtherNo(OtherNo);
					// tLCInsureAccSchema.setOtherType(OtherNoType);
					/*
					 * Lis5.3 upgrade get
					 * tLCInsureAccSchema.setContNo(tLCGrppolSchema.getContNo());
					 */
					tLCInsureAccSchema.setGrpPolNo(tLCGrppolSchema
							.getGrpPolNo());
					tLCInsureAccSchema.setInsuredNo("0"); // 因为是用集体的信息，没有被保人的客户号，所以填0
					// tLCInsureAccSchema.setAppntName(tLCGrppolSchema.getGrpName());
					tLCInsureAccSchema.setSumPay(0);
					tLCInsureAccSchema.setInsuAccBala(0);
					tLCInsureAccSchema.setUnitCount(0);
					tLCInsureAccSchema.setInsuAccGetMoney(0);
					tLCInsureAccSchema.setSumPaym(0);
					tLCInsureAccSchema.setFrozenMoney(0);
					tLCInsureAccSchema.setAccComputeFlag(tLMRiskInsuAccSchema
							.getAccComputeFlag());
					tLCInsureAccSchema.setManageCom(tLCGrppolSchema
							.getManageCom());
					tLCInsureAccSchema.setOperator(tLCGrppolSchema
							.getOperator());
					tLCInsureAccSchema.setBalaDate(tLCGrppolSchema
							.getCValiDate());
					tLCInsureAccSchema.setMakeDate(CurrentDate);
					tLCInsureAccSchema.setMakeTime(CurrentTime);
					tLCInsureAccSchema.setModifyDate(CurrentDate);
					tLCInsureAccSchema.setModifyTime(CurrentTime);
					tLCInsureAccSet.add(tLCInsureAccSchema);
				}
			}

			return tLCInsureAccSet;
		}

		return null;
	}

	/**
	 * 生成保险帐户表(类型 2:将生成帐户和注入资金合而为一,需要添加履历表纪录,注意：已经给出要注入的资金 )
	 * 
	 * @param PolNo
	 *            保单号
	 * @param AccCreatePos
	 *            生成位置 :1-投保单录入时产生 2－缴费时产生 3－领取时产生
	 * @param OtherNo
	 *            保单号或交费号
	 * @param OtherNoType
	 *            保单号或交费号
	 * @param ManageCom
	 *            登陆机构
	 * @param AccType
	 *            账号类型: 001-集体公共账户 002-个人缴费账户 003-个人储蓄账户 004-个人红利账户
	 * @param MoneyType
	 *            金额类型:BF－保费 GL－管理费 HL－红利 LX－累积生息的利息
	 * @param Money
	 *            存入帐户的金额
	 * @return VData(LCInsureAccSet,LCInsureAccTraceSet)
	 */
	public VData getLCInsureAcc(String PolNo, String AccCreatePos,
			String OtherNo, String OtherNoType, String ManageCom,
			String AccType, String MoneyType, double Money) {
		if ((PolNo == null) || (AccCreatePos == null) || (OtherNo == null)
				|| (OtherNoType == null) || (ManageCom == null)
				|| (AccType == null) || (MoneyType == null)) {
			// @@错误处理
			logger.debug("==getLCInsureAcc传入数据不能为空");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "getLCInsureAcc";
			tError.errorMessage = "传入数据不能为空!";
			this.mErrors.addOneError(tError);

			return null;
		}

		// 1-查询保单表
		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolSchema = queryLCPol(PolNo);
		if (tLCPolSchema == null) {
			return null;
		}

		// 2-根据投保单表中的险种字段查询LMRisk表
		LMRiskSchema tLMRiskSchema = new LMRiskSchema();
		tLMRiskSchema = queryLMRisk(tLCPolSchema.getRiskCode());
		if (tLMRiskSchema == null) {
			return null;
		}

		// 3-判断是否与帐户相关
		VData tVData = new VData();
		if (tLMRiskSchema.getInsuAccFlag().equals("Y")
				|| tLMRiskSchema.getInsuAccFlag().equals("y")) {
			// 根据险种查询LMRiskToAcc表(险种账户关联表)
			LMRiskToAccSet tLMRiskToAccSet = new LMRiskToAccSet();
			tLMRiskToAccSet = queryLMRiskToAcc(tLCPolSchema.getRiskCode());
			if (tLMRiskToAccSet == null) {
				return null;
			}

			LMRiskToAccSchema tLMRiskToAccSchema = new LMRiskToAccSchema(); // 险种账户关联表
			LMRiskInsuAccSchema tLMRiskInsuAccSchema = new LMRiskInsuAccSchema(); // 险种保险帐户
			LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema(); // 保险帐户表
			LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet(); // 保险帐户表
			LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet(); // 保险帐户表记价履历表
			LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
			for (int i = 1; i <= tLMRiskToAccSet.size(); i++) {
				// 根据保险账户号码查询LMRiskInsuAcc表(险种保险帐户)
				tLMRiskToAccSchema = tLMRiskToAccSet.get(i);
				tLMRiskInsuAccSchema = new LMRiskInsuAccSchema();
				tLMRiskInsuAccSchema = queryLMRiskInsuAcc(tLMRiskToAccSchema
						.getInsuAccNo(), AccType);
				if (tLMRiskInsuAccSchema == null) {
					return null;
				}

				// 生成保险账户表
				// 如果账户生成位置找到匹配的保险账户
				if (tLMRiskInsuAccSchema.getAccCreatePos().equals(AccCreatePos)) {
					tLCInsureAccSchema = new LCInsureAccSchema();
					tLCInsureAccSchema.setPolNo(PolNo);
					tLCInsureAccSchema.setInsuAccNo(tLMRiskInsuAccSchema
							.getInsuAccNo());
					tLCInsureAccSchema.setRiskCode(tLMRiskToAccSchema
							.getRiskCode());
					tLCInsureAccSchema.setAccType(tLMRiskInsuAccSchema
							.getAccType());
					// tLCInsureAccSchema.setOtherNo(OtherNo);
					// tLCInsureAccSchema.setOtherType(OtherNoType);
					tLCInsureAccSchema.setContNo(tLCPolSchema.getContNo());
					tLCInsureAccSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
					tLCInsureAccSchema
							.setInsuredNo(tLCPolSchema.getInsuredNo());
					// tLCInsureAccSchema.setAppntName(tLCPolSchema.getAppntName());
					tLCInsureAccSchema.setSumPay(Money);
					tLCInsureAccSchema.setInsuAccBala(Money);
					tLCInsureAccSchema.setUnitCount(0);
					tLCInsureAccSchema.setInsuAccGetMoney(0);
					tLCInsureAccSchema.setFrozenMoney(0);
					tLCInsureAccSchema.setAccComputeFlag(tLMRiskInsuAccSchema
							.getAccComputeFlag());
					tLCInsureAccSchema.setManageCom(ManageCom);
					tLCInsureAccSchema.setOperator(tLCPolSchema.getOperator());
					tLCInsureAccSchema.setBalaDate(tLCPolSchema.getCValiDate());
					tLCInsureAccSchema.setMakeDate(CurrentDate);
					tLCInsureAccSchema.setMakeTime(CurrentTime);
					tLCInsureAccSchema.setModifyDate(CurrentDate);
					tLCInsureAccSchema.setModifyTime(CurrentTime);
					tLCInsureAccSet.add(tLCInsureAccSchema);

					// 填充保险帐户表记价履历表
					tLimit = PubFun.getNoLimit(ManageCom);
					serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
					tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
					tLCInsureAccTraceSchema.setSerialNo(serNo);
					// tLCInsureAccTraceSchema.setInsuredNo(tLCInsureAccSchema
					// .getInsuredNo());
					tLCInsureAccTraceSchema.setPolNo(tLCInsureAccSchema
							.getPolNo());
					tLCInsureAccTraceSchema.setMoneyType(MoneyType);
					tLCInsureAccTraceSchema.setRiskCode(tLCInsureAccSchema
							.getRiskCode());
					tLCInsureAccTraceSchema.setOtherNo(OtherNo);
					tLCInsureAccTraceSchema.setOtherType(OtherNoType);
					tLCInsureAccTraceSchema.setMoney(Money);
					tLCInsureAccTraceSchema.setContNo(tLCInsureAccSchema
							.getContNo());
					tLCInsureAccTraceSchema.setGrpPolNo(tLCInsureAccSchema
							.getGrpPolNo());
					tLCInsureAccTraceSchema.setInsuAccNo(tLCInsureAccSchema
							.getInsuAccNo());
					/*
					 * Lis5.3 upgrade set
					 * tLCInsureAccTraceSchema.setAppntName(tLCInsureAccSchema
					 * .getAppntName());
					 */
					tLCInsureAccTraceSchema.setState(tLCInsureAccSchema
							.getState());
					tLCInsureAccTraceSchema.setManageCom(tLCInsureAccSchema
							.getManageCom());
					tLCInsureAccTraceSchema.setOperator(tLCInsureAccSchema
							.getOperator());
					tLCInsureAccTraceSchema.setMakeDate(CurrentDate);
					tLCInsureAccTraceSchema.setMakeTime(CurrentTime);
					tLCInsureAccTraceSchema.setModifyDate(CurrentDate);
					tLCInsureAccTraceSchema.setModifyTime(CurrentTime);
					tLCInsureAccTraceSchema.setPayDate(tLCPolSchema
							.getCValiDate());

					// 添加容器
					tLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
				}
			}
			if ((tLCInsureAccSet.size() == 0)
					|| (tLCInsureAccTraceSet.size() == 0)) {
				// @@错误处理
				// CError tError =new CError();
				// tError.moduleName="DealAccount";
				// tError.functionName="addPrem";
				// tError.errorMessage="条件不符合，没有生成纪录";
				// this.mErrors .addOneError(tError) ;
				return null;
			}
			tVData.add(tLCInsureAccSet);
			tVData.add(tLCInsureAccTraceSet);

			return tVData;
		} else {
			// CError tError = new CError();
			// tError.moduleName = "DealAccount";
			// tError.functionName = "getLCInsureAcc";
			// tError.errorMessage = "险种定义纪录中保险帐户标记为N!";
			// this.mErrors.addOneError(tError);
			return null;
		}
	}

	/**
	 * 生成保费项表和客户帐户表的关联表
	 * 
	 * @param PolNo
	 *            保单号
	 * @param AccCreatePos
	 *            生成位置
	 * @param Rate
	 *            费率
	 * @return LCPremToAccSet 保费项关联表
	 */
	public LCPremToAccSet getPremToAcc(String PolNo, String AccCreatePos,
			Double Rate) {
		if ((PolNo == null) || (AccCreatePos == null)) {
			// @@错误处理
			logger.debug("getPremToAcc错误原因:传入参数不能为空");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "getPremToAcc";
			tError.errorMessage = "错误原因:传入参数不能为空";
			this.mErrors.addOneError(tError);

			return null;
		}

		String tPolNo = PolNo;
		String tAccCreatePos = AccCreatePos;
		Double tRate = Rate;
		LCPremToAccSet tLCPremToAccSet = new LCPremToAccSet();

		// 1-取出保费项表
		LCPremSet tLCPremSet = new LCPremSet();
		tLCPremSet = queryLCPrem(tPolNo);
		if (tLCPremSet == null) {
			return null;
		}

		// 2-根据保费项表取出对应的责任缴费描述表
		VData tVData = new VData();
		tVData = getFromLMDutyPay(tLCPremSet);
		if (tVData == null) {
			return null;
		}

		// 3-生成保费项表和客户账户表的关联表
		tLCPremToAccSet = createPremToAcc(tVData, tPolNo, tAccCreatePos, tRate);

		return tLCPremToAccSet;
	}

	public VData addPremAcc(LCPremSchema pLCPremSchema, String OtherNo,
			String OtherNoType, String MoneyType, Double Rate, String mOperate,
			String PayNo) {
		if ((pLCPremSchema == null) || (mOperate == null) || (OtherNo == null)
				|| (OtherNoType == null) || (MoneyType == null)) {
			// @@错误处理
			logger.debug("addPremAcc传入数据不能为空");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "addPrem";
			tError.errorMessage = "传入数据不能为空";
			this.mErrors.addOneError(tError);

			return null;
		}
		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
		LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
		LCInsureAccFeeSet tLCInsureAccFeeSet = new LCInsureAccFeeSet();
		LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
		// LCInsureAccFeeTraceSet tLCInsureAccFeeTraceSet = new
		// LCInsureAccFeeTraceSet();
		LCPremToAccSet tLCPremToAccSet = new LCPremToAccSet();
		// 判断是否帐户相关
		VData mResult = new VData();
		if (pLCPremSchema.getNeedAcc().equals("1")) {
			tLCPremToAccSet = queryLCPremToAccSet(pLCPremSchema);
			for (int i = 1; i <= tLCPremToAccSet.size(); i++) { // 循环交费项账户表来判断是否要新建账户分类表的信息。
				LCPremToAccSchema tLCPremToAccSchema = tLCPremToAccSet.get(i);
				LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();
				tLMRiskInsuAccDB
						.setInsuAccNo(tLCPremToAccSchema.getInsuAccNo());
				tLMRiskInsuAccDB.getInfo();
				String polno = pLCPremSchema.getPolNo();
				String insuaccno = tLCPremToAccSchema.getInsuAccNo();
				String payplancode = pLCPremSchema.getPayPlanCode();
				LCInsureAccClassSchema tLCInsureAccClassSchema = this
						.queryLCInsureAccClass(polno, insuaccno, payplancode,
								OtherNo);

				/*
				 * if(tLMRiskInsuAccDB.getAccCreateType().equals("1"))
				 * //判断账户产生规则 1.同个人保单号,不用重新生成账户。 {
				 *  }
				 */
				if (tLMRiskInsuAccDB.getAccCreateType().equals("2")) { // 判断账户产生规则
																		// 2.同缴费收据号,要重新生成账户。
					tLCInsureAccClassSchema.setOtherNo(PayNo);
				}
				LCInsureAccFeeSchema tLCInsureAccFeeSchema = this
						.queryLCInsureAccFee(polno, insuaccno); // 得到管理费账户主表的记录
				tLCInsureAccFeeSchema.setModifyDate(PubFun.getCurrentDate());
				tLCInsureAccFeeSchema.setModifyTime(PubFun.getCurrentTime());
				LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema = this
						.queryLCInsrueAccClassFee(polno, insuaccno,
								payplancode, OtherNo); // 得到管理费账户分类表的记录
				tLCInsureAccClassFeeSchema.setModifyDate(PubFun
						.getCurrentDate());
				tLCInsureAccClassFeeSchema.setModifyTime(PubFun
						.getCurrentTime());
				LCInsureAccSchema tLCInsureAccSchema = this.queryLCInsureAcc(
						polno, insuaccno); // 得到账户的记录
				tLCInsureAccClassSet.add(tLCInsureAccClassSchema); // 得到账户分类表的记录
				tLCInsureAccSet.add(tLCInsureAccSchema);
				tLCInsureAccFeeSet.add(tLCInsureAccFeeSchema);
				tLCInsureAccClassFeeSet.add(tLCInsureAccClassFeeSchema);
			}

			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(pLCPremSchema.getPolNo());
			tLCPolDB.getInfo();
			LCPremSet tLCPremSet = new LCPremSet();
			tLCPremSet.add(pLCPremSchema);
			VData vData = new VData();
			vData.add(tLCPremSet);
			vData.add(tLCPremToAccSet);
			vData.add(tLCPolDB.getSchema());
			vData.add(tLCInsureAccSet);
			vData.add(tLCInsureAccClassSet);
			vData.add(tLCInsureAccTraceSet);
			vData.add(tLCInsureAccFeeSet);
			vData.add(tLCInsureAccClassFeeSet);
			// vData.add(tLCInsureAccFeeTraceSet);
			CManageFee managFee = new CManageFee();
			boolean ble = managFee.calPremManaFee(vData, "2", OtherNo,
					OtherNoType, MoneyType); // 计算管理费.
			if (ble == false) {
				logger.debug("addPremAcc计算管理费失败");
				CError tError = new CError();
				tError.moduleName = "DealAccount";
				tError.functionName = "addPremAcc";
				tError.errorMessage = "计算管理费失败";
				this.mErrors.addOneError(tError);
				return null;
			}
			// 重算可注入资金
			double inputMoney = 0;
			tLCInsureAccClassSet.clear();
			tLCInsureAccSet.clear();
			for (int i = 1; i <= tLCPremToAccSet.size(); i++) {
				LCPremToAccSchema tLCPremToAccSchema = tLCPremToAccSet.get(i);
				for (int j = 1; j <= tLCInsureAccTraceSet.size(); j++) {
					String polno = tLCInsureAccTraceSet.get(j).getPolNo();
					String insuaccno = tLCInsureAccTraceSet.get(j)
							.getInsuAccNo();
					String payplancode = tLCInsureAccTraceSet.get(j)
							.getPayPlanCode();
					String AccAscription = tLCInsureAccTraceSet.get(j)
							.getAccAscription();
					if (polno.equals(tLCPremToAccSchema.getPolNo())
							&& insuaccno.equals(tLCPremToAccSchema
									.getInsuAccNo())
							&& payplancode.equals(tLCPremToAccSchema
									.getPayPlanCode())
							&& AccAscription.equals("0")) {
						// 得到可注入资金.
						inputMoney = this.calInputMoney(tLCPremToAccSchema,
								tLCInsureAccTraceSet.get(j).getMoney());
						if (inputMoney == -1) {
							logger.debug("addPremAcc计算实际应该注入的资金出错");
							CError tError = new CError();
							tError.moduleName = "DealAccount";
							tError.functionName = "addPremAcc";
							tError.errorMessage = "计算实际应该注入的资金出错";
							this.mErrors.addOneError(tError);

							return null;
						}
						tLCInsureAccTraceSet.get(j).setMoney(inputMoney);
						tLCInsureAccTraceSet.get(j).setPayDate(
								PubFun.getCurrentDate());
						LCInsureAccClassSchema tLCInsureAccClassSchema = this
								.queryLCInsureAccClass(polno, insuaccno,
										payplancode, OtherNo);
						LCInsureAccSchema tLCInsureAccSchema = this
								.queryLCInsureAcc(polno, insuaccno);
						tLCInsureAccClassSchema
								.setSumPay(tLCInsureAccClassSchema.getSumPay()
										+ inputMoney);
						tLCInsureAccClassSchema
								.setInsuAccBala(tLCInsureAccClassSchema
										.getInsuAccBala()
										+ inputMoney);
						tLCInsureAccClassSchema.setModifyDate(PubFun
								.getCurrentDate());
						tLCInsureAccClassSchema.setModifyTime(PubFun
								.getCurrentTime());
						tLCInsureAccSchema.setSumPay(tLCInsureAccSchema
								.getSumPay()
								+ inputMoney);
						tLCInsureAccSchema.setInsuAccBala(tLCInsureAccSchema
								.getInsuAccBala()
								+ inputMoney);
						tLCInsureAccSchema.setModifyDate(PubFun
								.getCurrentDate());
						tLCInsureAccSchema.setModifyTime(PubFun
								.getCurrentTime());
						tLCInsureAccSet.add(tLCInsureAccSchema);
						tLCInsureAccClassSet.add(tLCInsureAccClassSchema);
					}

				}

			}
		}
		mResult.add(tLCInsureAccSet);
		mResult.add(tLCInsureAccClassSet);
		mResult.add(tLCInsureAccTraceSet);
		mResult.add(tLCInsureAccFeeSet);
		mResult.add(tLCInsureAccClassFeeSet);
		// mResult.add(tLCInsureAccFeeTraceSet);
		return mResult;
	}

	/**
	 * 查询一条管理费账户分类表的信息。
	 * 
	 * @param PolNo
	 *            String
	 * @param InsuAccNo
	 *            String
	 * @param PayPlanCode
	 *            String
	 * @param OtherNo
	 *            String
	 * @return LCInsureAccClassFeeSchema
	 */
	public LCInsureAccClassFeeSchema queryLCInsrueAccClassFee(String PolNo,
			String InsuAccNo, String PayPlanCode, String OtherNo) {
		LCInsureAccClassFeeDB tLCInsureAccClassFeeDB = new LCInsureAccClassFeeDB();
		tLCInsureAccClassFeeDB.setPolNo(PolNo);
		tLCInsureAccClassFeeDB.setInsuAccNo(InsuAccNo);
		tLCInsureAccClassFeeDB.setPayPlanCode(PayPlanCode);
		tLCInsureAccClassFeeDB.setOtherNo(OtherNo);
                //个险设置归属标记
                if (this.getPubFlag(PayPlanCode).equals("Y")
                    && this.getPayaimClass(PayPlanCode).equals("2")) {
                    tLCInsureAccClassFeeDB.setAccAscription("0");
                } else {
                    tLCInsureAccClassFeeDB.setAccAscription("1");
                }
		if (!tLCInsureAccClassFeeDB.getInfo()) {
			return null;
		}
		return tLCInsureAccClassFeeDB.getSchema();
	}

	/**
	 * 查询一条管理费账户主表信息
	 * 
	 * @param PolNo
	 *            String 个人保单号
	 * @param InsuAccNo
	 *            String 险种账户号
	 * @return LCInsureAccFeeSchema
	 */
	public LCInsureAccFeeSchema queryLCInsureAccFee(String PolNo,
			String InsuAccNo) {
		LCInsureAccFeeDB tLCInsureAccFeeDB = new LCInsureAccFeeDB();
		tLCInsureAccFeeDB.setPolNo(PolNo);
		tLCInsureAccFeeDB.setInsuAccNo(InsuAccNo);
		if (!tLCInsureAccFeeDB.getInfo()) {
			return null;
		}
		return tLCInsureAccFeeDB.getSchema();
	}

	/**
	 * 查询一条账户主表的信息。
	 * 
	 * @param PolNo
	 *            String 个人保单号
	 * @param InsuAccNo
	 *            String 险种账户号
	 * @return LCInsureAccSchema
	 */
	public LCInsureAccSchema queryLCInsureAcc(String PolNo, String InsuAccNo) {
		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		tLCInsureAccDB.setPolNo(PolNo);
		tLCInsureAccDB.setInsuAccNo(InsuAccNo);
		if (!tLCInsureAccDB.getInfo()) {
			return null;
		}
		return tLCInsureAccDB.getSchema();
	}
	/**
	 * 查询账户管理分类表信息
	 * 
	 * @param PolNo
	 *            String 个人保单号
	 * @param InsuAccNo
	 *            String 险种账户号
	 * @return LCInsureAccSchema
	 */
	public LCInsureAccFeeTraceSchema queryLCInsureAccFeeTrace(String PolNo, String InsuAccNo,String 
			 PayPlanCode,String OtherNo) {
		LCInsureAccClassFeeDB  tLCInsureAccClassFeeDB =new LCInsureAccClassFeeDB();
		LCInsureAccClassFeeSet  tLCInsureAccClassFeeSet =new LCInsureAccClassFeeSet();
		LCInsureAccFeeTraceSchema tLCInsureAccFeeTraceSchema=new LCInsureAccFeeTraceSchema();
		tLCInsureAccClassFeeDB.setPolNo(PolNo);
		tLCInsureAccClassFeeDB.setPayPlanCode(PayPlanCode);
		tLCInsureAccClassFeeDB.setInsuAccNo(InsuAccNo);
		//tLCInsureAccClassFeeDB.setOtherNo(OtherNo);
		tLCInsureAccClassFeeSet =tLCInsureAccClassFeeDB.query();
		if(tLCInsureAccClassFeeSet.size()==0){
			CError.buildErr(this, "查询账户管理分类表错误！");
			return null;
		}
		rf.transFields(tLCInsureAccFeeTraceSchema, tLCInsureAccClassFeeSet
				.get(1));
		return tLCInsureAccFeeTraceSchema;
	}
	public double getManageFee(String GrpPolNo,String InsuAccNo,String PayPlanCode){
		double Fee=0;
		String sql = "select lc.* from lcgrpfee lc,lmriskfee lm where grppolno='"
			+ "?w1?"
			+ "' and lc.insuaccno='"
			+ "?w2?"
			+ "' "
			+ "and lc.payplancode='"
			+ "?w3?"
			+ "' and lc.makedate <='"
			+ "?w4?"
			+ " ' and lc.insuaccno=lm.insuaccno and lc.feecode=lm.feecode"
			+ " and lc.payplancode=lm.payplancode "
			//tongmeng 2008-09-10 modify
			//取消计算方式匹配 
			//" and lc.feecalmode=lm.feecalmode"
			+ " and lc.FeeTakePlace='01' order by lc.makedate desc";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("w1", GrpPolNo);
		sqlbv1.put("w2", InsuAccNo);
		sqlbv1.put("w3", PayPlanCode);
		sqlbv1.put("w4", PubFun.getCurrentDate());

	logger.debug(sql);
	LCGrpFeeDB tLCGrpFeeDB = new LCGrpFeeDB();
	LCGrpFeeSet tLCGrpFeeSet = tLCGrpFeeDB.executeQuery(sqlbv1);
	if(tLCGrpFeeSet.size()==0){
		return -1;
	}
	Fee =tLCGrpFeeSet.get(1).getFeeValue();
	return Fee;
	}

	/**
	 * 保险账户资金注入(类型1 针对保费项,注意没有给出注入资金，内部会调用计算金额的函数)
	 * 
	 * @param pLCPremSchema
	 *            保费项
	 * @param AccCreatePos
	 *            参见 险种保险帐户缴费 LMRiskAccPay
	 * @param OtherNo
	 *            参见 保险帐户表 LCInsureAcc
	 * @param OtherNoType
	 *            号码类型
	 * @param MoneyType
	 *            参见 保险帐户表记价履历表 LCInsureAccTrace
	 * @param Rate
	 *            费率
	 * @return VData(tLCInsureAccSet:update or insert ,tLCInsureAccTraceSet:
	 *         insert)
	 */
	public VData addPrem(LCPremSchema pLCPremSchema, String AccCreatePos,
			String OtherNo, String OtherNoType, String MoneyType, Double Rate) {
		if ((pLCPremSchema == null) || (AccCreatePos == null)
				|| (OtherNo == null) || (OtherNoType == null)
				|| (MoneyType == null)) {
			// @@错误处理
			logger.debug("addPrem传入数据不能为空");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "addPrem";
			tError.errorMessage = "传入数据不能为空";
			this.mErrors.addOneError(tError);

			return null;
		}

		VData tVData = new VData();
		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet(); // 保险帐户表
		LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet(); // 保险帐户表记价履历表
		LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
		LCPremToAccSet tLCPremToAccSet = new LCPremToAccSet(); // 保费项表和客户帐户表的关联表
		LCPremToAccSchema tLCPremToAccSchema = new LCPremToAccSchema();
		LMRiskAccPaySchema tLMRiskAccPaySchema = new LMRiskAccPaySchema();
		String newFlag = "";
		boolean addPrem = false;
		double inputMoney = 0;

		// 判断是否帐户相关
		if (pLCPremSchema.getNeedAcc().equals("1")) {
			tLCPremToAccSet = queryLCPremToAccSet(pLCPremSchema);
			if (tLCPremToAccSet == null) {
				return null;
			}

			TransferData tFData = new TransferData();
			LCInsureAccSet mLCInsureAccSet = new LCInsureAccSet();

			// 判断生成位置是否匹配
			if (AccCreatePos.equals(tLCPremToAccSet.get(1).getNewFlag())) {
				// 如果匹配：生成帐户(即对于每次交费都产生新账号的情况，参看LCInsureAcc-保险帐户表)
				tFData = new TransferData();
				tFData.setNameAndValue("PolNo", pLCPremSchema.getPolNo());
				tFData.setNameAndValue("OtherNo", OtherNo); // 对于每次交费都产生新账号的情况，该字段存放交费号。主键
				tFData.setNameAndValue("OtherNoType", OtherNoType);
				tFData.setNameAndValue("Rate", Rate);
				tLCInsureAccSet = new LCInsureAccSet();
				mLCInsureAccSet = getLCInsureAcc(pLCPremSchema.getPolNo(),
						AccCreatePos, OtherNo, OtherNoType);
				if (mLCInsureAccSet == null) {
					return null;
				}
				newFlag = "INSERT";
			}

			for (int i = 1; i <= tLCPremToAccSet.size(); i++) {
				tLCPremToAccSchema = new LCPremToAccSchema();
				tLCPremToAccSchema = tLCPremToAccSet.get(i);

				// 计算实际应该注入的资金
				inputMoney = calInputMoney(tLCPremToAccSchema, pLCPremSchema
						.getPrem());
				if (inputMoney == -1) {
					// @@错误处理
					logger.debug("addPrem计算实际应该注入的资金出错");
					CError tError = new CError();
					tError.moduleName = "DealAccount";
					tError.functionName = "addPrem";
					tError.errorMessage = "计算实际应该注入的资金出错";
					this.mErrors.addOneError(tError);

					return null;
				}
				if (newFlag.equals("INSERT")) { // 如果是新生成帐户
					// 根据保单号和保险账户号和其它号码查询mLCInsureAccSet集合中唯一一条数据
					tLCInsureAccSchema = new LCInsureAccSchema();
					tLCInsureAccSchema = queryLCInsureAccSet(pLCPremSchema
							.getPolNo(), tLCPremToAccSchema.getInsuAccNo(),
							OtherNo, mLCInsureAccSet);
					if (tLCInsureAccSchema == null) {
						return null;
					}
				} else {
					// 根据保单号和保险账户号和其它号码查询LCInsureAcc表的唯一一条数据
					tLCInsureAccSchema = new LCInsureAccSchema();
					tLCInsureAccSchema = queryLCInsureAcc(pLCPremSchema
							.getPolNo(), tLCPremToAccSchema.getInsuAccNo(),
							OtherNo);
					if (tLCInsureAccSchema == null) {
						return null;
					}
				}

				// 修改保险帐户金额
				tLCInsureAccSchema.setInsuAccBala(tLCInsureAccSchema
						.getInsuAccBala()
						+ inputMoney);
				tLCInsureAccSchema.setSumPay(tLCInsureAccSchema.getSumPay()
						+ inputMoney);
				tLCInsureAccSchema.setModifyDate(CurrentDate);
				tLCInsureAccSchema.setModifyTime(CurrentTime);

				// tLCInsureAccSchema.setInsuAccGetMoney(tLCInsureAccSchema.getInsuAccGetMoney()+inputMoney);
				tLMRiskAccPaySchema = queryLMRiskAccPay2(tLCPremToAccSchema); // 查询险种保险帐户缴费
				if (tLMRiskAccPaySchema == null) {
					return null;
				}
				if (tLMRiskAccPaySchema.getPayNeedToAcc().equals("1")
						&& (inputMoney != 0)) {
					// 填充保险帐户表记价履历表
					tLimit = PubFun.getNoLimit(pLCPremSchema.getManageCom());
					serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
					tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
					tLCInsureAccTraceSchema.setSerialNo(serNo);
					// tLCInsureAccTraceSchema.setInsuredNo(tLCInsureAccSchema
					// .getInsuredNo());
					tLCInsureAccTraceSchema.setPolNo(tLCInsureAccSchema
							.getPolNo());
					tLCInsureAccTraceSchema.setMoneyType(MoneyType);
					tLCInsureAccTraceSchema.setRiskCode(tLCInsureAccSchema
							.getRiskCode());
					tLCInsureAccTraceSchema.setOtherNo(OtherNo);
					tLCInsureAccTraceSchema.setOtherType(OtherNoType);
					tLCInsureAccTraceSchema.setMoney(inputMoney);
					tLCInsureAccTraceSchema.setContNo(tLCInsureAccSchema
							.getContNo());
					tLCInsureAccTraceSchema.setGrpPolNo(tLCInsureAccSchema
							.getGrpPolNo());
					tLCInsureAccTraceSchema.setInsuAccNo(tLCInsureAccSchema
							.getInsuAccNo());
					/*
					 * Lis5.3 upgrade set
					 * tLCInsureAccTraceSchema.setAppntName(tLCInsureAccSchema
					 * .getAppntName());
					 */
					tLCInsureAccTraceSchema.setState(tLCInsureAccSchema
							.getState());
					tLCInsureAccTraceSchema.setManageCom(tLCInsureAccSchema
							.getManageCom());
					tLCInsureAccTraceSchema.setOperator(tLCInsureAccSchema
							.getOperator());
					tLCInsureAccTraceSchema.setMakeDate(CurrentDate);
					tLCInsureAccTraceSchema.setMakeTime(CurrentTime);
					tLCInsureAccTraceSchema.setModifyDate(CurrentDate);
					tLCInsureAccTraceSchema.setModifyTime(CurrentTime);
					tLCInsureAccTraceSchema.setPayDate(CurrentDate);
					tLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
				}

				// 添加容器
				tLCInsureAccSet.add(tLCInsureAccSchema);
			}
		}
		if (tLCInsureAccSet.size() == 0) {
			// @@错误处理
			// CError tError =new CError();
			// tError.moduleName="DealAccount";
			// tError.functionName="addPrem";
			// tError.errorMessage="条件不符合，没有生成纪录";
			// this.mErrors .addOneError(tError) ;
			return null;
		}
		tVData.add(tLCInsureAccSet);
		tVData.add(tLCInsureAccTraceSet);

		return tVData;

		// 最后在操作VData时，（数据tLCInsureAccSet可能是update or insert）
		// 因此操作数据库时先执行删除操作，再执行插入操作
	}
	/**
	 * 不定期核销时获取银行账户信息
	 */
	public VData getLCInsuAccInfo(LCPremSchema pLCPremSchema, String AccCreatePos,
			String OtherNo, String OtherNoType, String MoneyType, Double Rate ,String PayDate) {
		if ((pLCPremSchema == null) || (AccCreatePos == null)
				|| (OtherNo == null) || (OtherNoType == null)
				|| (MoneyType == null)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "addPrem";
			tError.errorMessage = "传入数据不能为空";
			this.mErrors.addOneError(tError);
			
			return null;
		}
		
		VData tVData = new VData();
		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet(); // 保险帐户表
		LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
		LCInsureAccClassSchema tLCInsureAccClassSchema = new LCInsureAccClassSchema();
		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet(); // 保险帐户表记价履历表
		LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema =new LCInsureAccClassFeeSchema();
		LCInsureAccFeeTraceSchema tLCInsureAccFeeTraceSchema =new LCInsureAccFeeTraceSchema();
		LCInsureAccFeeTraceSet tLCInsureAccFeeTraceSet=new LCInsureAccFeeTraceSet();
		LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
		LCPremToAccSet tLCPremToAccSet = new LCPremToAccSet(); // 保费项表和客户帐户表的关联表
		LCPremToAccSchema tLCPremToAccSchema = new LCPremToAccSchema();
		LMRiskAccPaySchema tLMRiskAccPaySchema = new LMRiskAccPaySchema();
		String newFlag = "";
		boolean addPrem = false;
		double inputMoney = 0;
		
		// 判断是否帐户相关
		if (pLCPremSchema.getNeedAcc().equals("1")) {
			tLCPremToAccSet = queryLCPremToAccSet(pLCPremSchema);
			if (tLCPremToAccSet == null) {
				return null;
			}
			
			TransferData tFData = new TransferData();
			LCInsureAccSet mLCInsureAccSet = new LCInsureAccSet();
			
			// 判断生成位置是否匹配
			if (AccCreatePos.equals(tLCPremToAccSet.get(1).getNewFlag())) {
				// 如果匹配：生成帐户(即对于每次交费都产生新账号的情况，参看LCInsureAcc-保险帐户表)
				tFData = new TransferData();
				tFData.setNameAndValue("PolNo", pLCPremSchema.getPolNo());
				tFData.setNameAndValue("OtherNo", OtherNo); // 对于每次交费都产生新账号的情况，该字段存放交费号。主键
				tFData.setNameAndValue("OtherNoType", OtherNoType);
				tFData.setNameAndValue("Rate", Rate);
				tLCInsureAccSet = new LCInsureAccSet();
				tLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
				mLCInsureAccSet = getLCInsureAcc(pLCPremSchema.getPolNo(),
						AccCreatePos, OtherNo, OtherNoType);
				if (mLCInsureAccSet == null) {
					return null;
				}
				newFlag = "INSERT";
			}
			
			for (int i = 1; i <= tLCPremToAccSet.size(); i++) {
				tLCPremToAccSchema = new LCPremToAccSchema();
				tLCPremToAccSchema = tLCPremToAccSet.get(i);
				
				// 计算实际应该注入的资金
				inputMoney = calInputMoney(tLCPremToAccSchema, pLCPremSchema
						.getPrem());
				if (inputMoney == -1) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "DealAccount";
					tError.functionName = "addPrem";
					tError.errorMessage = "计算实际应该注入的资金出错";
					this.mErrors.addOneError(tError);
					
					return null;
				}
				if (newFlag.equals("INSERT")) { // 如果是新生成帐户
					// 根据保单号和保险账户号和其它号码查询mLCInsureAccSet集合中唯一一条数据
					tLCInsureAccSchema = new LCInsureAccSchema();
					tLCInsureAccSchema = queryLCInsureAccSet(pLCPremSchema
							.getPolNo(), tLCPremToAccSchema.getInsuAccNo(),
							OtherNo, mLCInsureAccSet);
					if (tLCInsureAccSchema == null) {
						return null;
					}
				} else {
					// 根据保单号和保险账户号和其它号码查询LCInsureAcc表的唯一一条数据
					//6.5中不处理账户表和账户分类表 只记录账户轨迹表
					tLCInsureAccSchema = new LCInsureAccSchema();
					tLCInsureAccSchema = queryLCInsureAcc(pLCPremSchema
							.getPolNo(), tLCPremToAccSchema.getInsuAccNo(),
							OtherNo);
					if (tLCInsureAccSchema == null) {
						return null;
					}
					tLCInsureAccClassSchema = new LCInsureAccClassSchema();
					tLCInsureAccClassSchema = queryLCInsureAccClass(pLCPremSchema
							.getPolNo(), tLCPremToAccSchema.getInsuAccNo(),tLCPremToAccSchema.getPayPlanCode(),
							OtherNo);
					if(tLCInsureAccClassSchema == null){
						return null;
					}
				}
				
				// 修改保险帐户金额
//				tLCInsureAccSchema.setInsuAccBala(tLCInsureAccSchema
//						.getInsuAccBala()
//						+ inputMoney);
//				tLCInsureAccSchema.setSumPay(tLCInsureAccSchema.getSumPay()
//						+ inputMoney);
//				tLCInsureAccSchema.setModifyDate(CurrentDate);
//				tLCInsureAccSchema.setModifyTime(CurrentTime);
				
				// tLCInsureAccSchema.setInsuAccGetMoney(tLCInsureAccSchema.getInsuAccGetMoney()+inputMoney);
				// 操作管理费轨迹表
				//1.为保险账户管理履历表赋值
				tLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();
				tLCInsureAccFeeTraceSchema = queryLCInsureAccFeeTrace(pLCPremSchema
						.getPolNo(),tLCPremToAccSchema.getInsuAccNo(),tLCPremToAccSchema.getPayPlanCode(),OtherNo);
				//得到管理费
				double ManFee = getManageFee(tLCInsureAccSchema.getGrpPolNo()
						,tLCPremToAccSchema.getInsuAccNo(),tLCPremToAccSchema.getPayPlanCode());
				if(ManFee==-1){
					CError.buildErr(this, "获取管理费失败！");
				}
				tLMRiskAccPaySchema = queryLMRiskAccPay2(tLCPremToAccSchema); // 查询险种保险帐户缴费
				if (tLMRiskAccPaySchema == null) {
					return null;
				}
				if (tLMRiskAccPaySchema.getPayNeedToAcc().equals("1")
						&& (inputMoney != 0)) {
					// 填充保险帐户表记价履历表
					tLimit = PubFun.getNoLimit(pLCPremSchema.getManageCom());
					serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
					tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
					tLCInsureAccTraceSchema.setSerialNo(serNo);
					// tLCInsureAccTraceSchema.setInsuredNo(tLCInsureAccSchema
					// .getInsuredNo());
					tLCInsureAccTraceSchema.setAccAscription("1");
					tLCInsureAccTraceSchema.setFeeCode("000000");
					tLCInsureAccTraceSchema.setGrpContNo(tLCInsureAccSchema.getGrpContNo());
					tLCInsureAccTraceSchema.setGrpPolNo(tLCInsureAccSchema.getGrpPolNo());
					tLCInsureAccTraceSchema.setPolNo(tLCInsureAccSchema
							.getPolNo());
					tLCInsureAccTraceSchema.setPayPlanCode(tLCPremToAccSchema.getPayPlanCode());
					tLCInsureAccTraceSchema.setMoneyType(MoneyType);
					tLCInsureAccTraceSchema.setRiskCode(tLCInsureAccSchema
							.getRiskCode());
					tLCInsureAccTraceSchema.setOtherNo(OtherNo);
					tLCInsureAccTraceSchema.setOtherType(OtherNoType);
					if(ManFee!=-1){
						tLCInsureAccTraceSchema.setMoney(inputMoney-ManFee);
					}else{
						tLCInsureAccTraceSchema.setMoney(inputMoney);
					}
					
					tLCInsureAccTraceSchema.setContNo(tLCInsureAccSchema
							.getContNo());
					tLCInsureAccTraceSchema.setGrpPolNo(tLCInsureAccSchema
							.getGrpPolNo());
					tLCInsureAccTraceSchema.setInsuAccNo(tLCInsureAccSchema
							.getInsuAccNo());
					/*
					 * Lis5.3 upgrade set
					 * tLCInsureAccTraceSchema.setAppntName(tLCInsureAccSchema
					 * .getAppntName());
					 */
					tLCInsureAccTraceSchema.setState(tLCInsureAccSchema
							.getState());
					tLCInsureAccTraceSchema.setManageCom(tLCInsureAccSchema
							.getManageCom());
					tLCInsureAccTraceSchema.setOperator(tLCInsureAccSchema
							.getOperator());
					tLCInsureAccTraceSchema.setMakeDate(CurrentDate);
					tLCInsureAccTraceSchema.setMakeTime("00:00:00");
					tLCInsureAccTraceSchema.setModifyDate(CurrentDate);
					tLCInsureAccTraceSchema.setModifyTime(CurrentTime);
					tLCInsureAccTraceSchema.setPayDate(CurrentDate);
					tLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
				}
				
				// 添加容器
				tLCInsureAccSet.add(tLCInsureAccSchema);
				tLCInsureAccFeeTraceSet.add(tLCInsureAccFeeTraceSchema);
			}
		}
		LCInsureAccTraceSet ttLCInsureAccTraceSet =new LCInsureAccTraceSet();
		for (int n = 1; n <= tLCInsureAccTraceSet.size(); n++) {
			tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
			tLCInsureAccTraceSchema = tLCInsureAccTraceSet.get(n);
			tLCInsureAccTraceSchema.setPayDate(PayDate);
			ttLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
		}
		if (tLCInsureAccSet.size() == 0) {
			// @@错误处理
			// CError tError =new CError();
			// tError.moduleName="DealAccount";
			// tError.functionName="addPrem";
			// tError.errorMessage="条件不符合，没有生成纪录";
			// this.mErrors .addOneError(tError) ;
			return null;
		}
		//tVData.add(tLCInsureAccSet);
		tVData.add(tLCInsureAccTraceSet);
		tVData.add(tLCInsureAccFeeTraceSet);
		
		return tVData;
		
		// 最后在操作VData时，（数据tLCInsureAccSet可能是update or insert）
		// 因此操作数据库时先执行删除操作，再执行插入操作
	}

	/**
	 * 该方法有缺陷，不能用于签单程序，因为传入的数据中的得到的是保单号，可是库中的数据是 尚未签单的数据，只有投保单号。 保险账户资金注入(类型3
	 * 针对保费项,注意没有给出注入资金，内部会调用计算金额的函数) 适用于：在生成帐户结构后，此时数据尚未提交到数据库，又需要执行帐户的资金注入。
	 * 即在使用了 createInsureAcc()方法后，得到VData数据，接着修改VData中帐户的金额
	 * 
	 * @param inVData
	 *            使用了 createInsureAcc()方法后，得到的VData数据
	 * @param pLCPremSet
	 *            保费项集合
	 * @param AccCreatePos
	 *            参见 险种保险帐户缴费 LMRiskAccPay
	 * @param OtherNo
	 *            参见 保险帐户表 LCInsureAcc
	 * @param OtherNoType
	 *            号码类型
	 * @param MoneyType
	 *            参见 保险帐户表记价履历表 LCInsureAccTrace
	 * @param Rate
	 *            费率
	 * @return VData(tLCInsureAccSet:update or insert ,tLCInsureAccTraceSet:
	 *         insert)
	 */
	public VData addPremInner(VData inVData, LCPremSet pLCPremSet,
			String AccCreatePos, String OtherNo, String OtherNoType,
			String MoneyType, String RiskCode, String Rate) {
		if ((inVData == null) || (pLCPremSet == null) || (AccCreatePos == null)
				|| (OtherNo == null) || (OtherNoType == null)
				|| (MoneyType == null) || (RiskCode == null)) {
			// @@错误处理
			logger.debug("addPremInner传入数据不能为空");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "addPrem";
			tError.errorMessage = "传入数据不能为空";
			this.mErrors.addOneError(tError);

			return null;
		}

		VData tVData = new VData();

		// 得到生成的保险帐户表
		LCInsureAccSet tLCInsureAccSet = (LCInsureAccSet) (inVData
				.getObjectByObjectName("LCInsureAccSet", 0));

		// 得到生成的缴费帐户关联表
		LCPremToAccSet tLCPremToAccSet = (LCPremToAccSet) (inVData
				.getObjectByObjectName("LCPremToAccSet", 0));

		LCInsureAccClassSet tInsureAccClassSet = (LCInsureAccClassSet) inVData
				.getObjectByObjectName("LCInsureAccClassSet", 0);

		// 得到领取帐户关联表--目前不用
		LCGetToAccSet tLCGetToAccSet = (LCGetToAccSet) (inVData
				.getObjectByObjectName("LCGetToAccSet", 0));

		if (tLCInsureAccSet == null) {
			tLCInsureAccSet = new LCInsureAccSet();
		}
		if (tLCPremToAccSet == null) {
			tLCPremToAccSet = new LCPremToAccSet();
		}
		if (tLCGetToAccSet == null) {
			tLCGetToAccSet = new LCGetToAccSet();
		}
		if (tInsureAccClassSet == null) {
			tInsureAccClassSet = new LCInsureAccClassSet();
		}

		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet(); // 保险帐户表记价履历表
		LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
		LCInsureAccClassSchema tClassSchema = null;
		double inputMoney = 0;
		for (int n = 1; n <= pLCPremSet.size(); n++) {
			LCPremSchema tLCPremSchema = pLCPremSet.get(n);

			// 判断是否帐户相关
			if (tLCPremSchema.getNeedAcc().equals("1")) {
				for (int m = 1; m <= tLCPremToAccSet.size(); m++) {
					LCPremToAccSchema tLCPremToAccSchema = tLCPremToAccSet
							.get(m);

					// 如果当前保费项和当前的缴费帐户关联表的保单号，责任编码，交费计划编码相同
					if (tLCPremSchema.getPolNo().equals(
							tLCPremToAccSchema.getPolNo())
							&& tLCPremSchema.getDutyCode().equals(
									tLCPremToAccSchema.getDutyCode())
							&& tLCPremSchema.getPayPlanCode().equals(
									tLCPremToAccSchema.getPayPlanCode())) {

						// if ( tLCPremToAccSchema.getCalFlag()==null
						// || "0".equals( tLCPremToAccSchema.getCalFlag() ))
						// {
						// 计算需要注入的资金
						inputMoney = calInputMoney(tLCPremToAccSchema,
								tLCPremSchema.getPrem());

						if (inputMoney == -1) {
							// @@错误处理
							logger.debug("addPremInner计算实际应该注入的资金出错");
							CError tError = new CError();
							tError.moduleName = "DealAccount";
							tError.functionName = "addPrem";
							tError.errorMessage = "计算实际应该注入的资金出错";
							this.mErrors.addOneError(tError);

							return null;
						}
						// }else
						// {
						// //计算管理非
						// VData feeData = new VData();
						// CManageFee cManageFee = new CManageFee();
						// LCPremSet tLCPremSet = new LCPremSet();
						//
						// feeData.add(tLCPremSet);
						// cManageFee.Initialize( feeData );
						// cManageFee.computeManaFee();
						// if ( cManageFee.mErrors.needDealError())
						// {
						// this.mErrors.copyAllErrors( cManageFee.mErrors);
						// return false;
						// }
						// VData tResult = cManageFee.getResult();
						// LCInsureAccFeeSet tmpLCInsureAccFeeSet
						// =(LCInsureAccFeeSet)
						// tResult.getObjectByObjectName("LCInsureAccFeeSet",0);
						// LCInsureAccClassFeeSet
						// tmpLCInsureAccClassFeeSet=(LCInsureAccClassFeeSet)tResult.getObjectByObjectName("LCInsureAccClassFeeSet",0);
						// manFeeMap.put(tmpLCInsureAccFeeSet,this.INSERT );
						// manFeeMap.put(tmpLCInsureAccClassFeeSet,
						// this.INSERT);
						//
						//
						// }

						// 累计账户分类ss
						for (int t = 1; t <= tInsureAccClassSet.size(); t++) {
							tClassSchema = tInsureAccClassSet.get(t);
							if (tClassSchema.getInsuAccNo().equals(
									tLCPremToAccSchema.getInsuAccNo())
									&& tClassSchema.getPayPlanCode()
											.equals(
													tLCPremToAccSchema
															.getPayPlanCode())
									&& tClassSchema.getPolNo().equals(
											tLCPremToAccSchema.getPolNo())) {

								tClassSchema.setSumPay(tClassSchema.getSumPay()
										+ inputMoney);
								tClassSchema.setInsuAccBala(tClassSchema
										.getInsuAccBala()
										+ inputMoney);
								break;
							}

						}
						for (int j = 1; j <= tLCInsureAccSet.size(); j++) {
							// 如果当前缴费帐户关联表的保单号，账户号和当前的账户表的保单号，账户号相同并且资金不为0，将资金注入
							LCInsureAccSchema tLCInsureAccSchema = tLCInsureAccSet
									.get(j);
							if (tLCPremToAccSchema.getPolNo().equals(
									tLCInsureAccSchema.getPolNo())
									&& tLCPremToAccSchema.getInsuAccNo()
											.equals(
													tLCInsureAccSchema
															.getInsuAccNo())
									&& (inputMoney != 0)) {
								// 修改保险帐户金额
								tLCInsureAccSchema
										.setInsuAccBala(tLCInsureAccSchema
												.getInsuAccBala()
												+ inputMoney);
								tLCInsureAccSchema.setSumPay(tLCInsureAccSchema
										.getSumPay()
										+ inputMoney);

								// tLCInsureAccSchema.setInsuAccGetMoney(tLCInsureAccSchema.getInsuAccGetMoney()+inputMoney);
								tLCInsureAccSet.set(j, tLCInsureAccSchema);

								// 查询险种保险帐户缴费
								LMRiskAccPaySchema tLMRiskAccPaySchema = queryLMRiskAccPay3(
										RiskCode, tLCPremToAccSchema);
								if (tLMRiskAccPaySchema == null) {
									return null;
								}
								if (tLMRiskAccPaySchema.getPayNeedToAcc()
										.equals("1")) {
									// 填充保险帐户表记价履历表
									tLimit = PubFun.getNoLimit(tLCPremSchema
											.getManageCom());
									serNo = PubFun1.CreateMaxNo("SERIALNO",
											tLimit);
									tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
									tLCInsureAccTraceSchema.setSerialNo(serNo);
									// tLCInsureAccTraceSchema.setInsuredNo(
									// tLCInsureAccSchema
									// .getInsuredNo());
									tLCInsureAccTraceSchema
											.setPolNo(tLCInsureAccSchema
													.getPolNo());
									tLCInsureAccTraceSchema
											.setMoneyType(MoneyType);
									tLCInsureAccTraceSchema
											.setRiskCode(tLCInsureAccSchema
													.getRiskCode());
									tLCInsureAccTraceSchema.setOtherNo(OtherNo);
									tLCInsureAccTraceSchema
											.setOtherType(OtherNoType);
									tLCInsureAccTraceSchema
											.setMoney(inputMoney);
									tLCInsureAccTraceSchema
											.setContNo(tLCInsureAccSchema
													.getContNo());
									tLCInsureAccTraceSchema
											.setGrpPolNo(tLCInsureAccSchema
													.getGrpPolNo());
									tLCInsureAccTraceSchema
											.setInsuAccNo(tLCInsureAccSchema
													.getInsuAccNo());
									/*
									 * Lis5.3 upgrade set
									 * tLCInsureAccTraceSchema.setAppntName(tLCInsureAccSchema
									 * .getAppntName());
									 */
									tLCInsureAccTraceSchema
											.setPolNo(tLCInsureAccSchema
													.getPolNo());
									tLCInsureAccTraceSchema
											.setGrpContNo(tLCInsureAccSchema
													.getGrpContNo());
									tLCInsureAccTraceSchema
											.setState(tLCInsureAccSchema
													.getState());
									tLCInsureAccTraceSchema
											.setManageCom(tLCInsureAccSchema
													.getManageCom());
									tLCInsureAccTraceSchema
											.setOperator(tLCInsureAccSchema
													.getOperator());
									tLCInsureAccTraceSchema
											.setMakeDate(CurrentDate);
									tLCInsureAccTraceSchema
											.setMakeTime(CurrentTime);
									tLCInsureAccTraceSchema
											.setModifyDate(CurrentDate);
									tLCInsureAccTraceSchema
											.setModifyTime(CurrentTime);
									tLCInsureAccTraceSchema
											.setPayDate(CurrentDate);
									tLCInsureAccTraceSchema
											.setPolNo(tLCInsureAccSchema
													.getPolNo());
									tLCInsureAccTraceSchema
											.setGrpContNo(tLCInsureAccSchema
													.getGrpContNo());

									tLCInsureAccTraceSet
											.add(tLCInsureAccTraceSchema);

								}

								break;
							}
						}
					}
				}
			}
		}

		inVData.clear();
		inVData.add(tLCInsureAccSet);
		inVData.add(tLCPremToAccSet);
		inVData.add(tLCGetToAccSet);

		// 添加帐户注入资金轨迹
		inVData.add(tLCInsureAccTraceSet);

		// 操作数据库时执行插入操作
		return inVData; // (LCInsureAccSet,LCPremToAccSet,LCGetToAccSet,LCInsureAccTraceSet)
	}

	/**
	 * 保险账户资金注入(类型2 通用)
	 * 
	 * @param PolNo
	 *            保单号
	 * @param InsuAccNo
	 *            帐户号
	 * @param OtherNo
	 *            存放交费号或保单号
	 * @param OtherNoType
	 *            存放交费号或保单号
	 * @param MoneyType
	 *            金额类型:BF－保费 GL－管理费 HL－红利 LX－累积生息的利息
	 * @param ManageCom
	 *            管理机构
	 * @param money
	 *            注入资金
	 * @return VData(tLCInsureAccSet:update ,tLCInsureAccTraceSet: insert)
	 */
	public VData addPrem(String PolNo, String InsuAccNo, String OtherNo,
			String OtherNoType, String MoneyType, String ManageCom, double money) {
		if ((PolNo == null) || (InsuAccNo == null) || (OtherNo == null)
				|| (OtherNoType == null) || (MoneyType == null)
				|| (ManageCom == null)) {
			// @@错误处理
			logger.debug("addPrem==传入数据不能为空");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "addPrem";
			tError.errorMessage = "传入数据不能为空";
			this.mErrors.addOneError(tError);

			return null;
		}

		VData tVData = new VData();
		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet(); // 保险帐户表
		LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet(); // 保险帐户表记价履历表
		LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();

		// 根据保单号和保险账户号和其它号码查询LCInsureAcc表的唯一一条数据
		tLCInsureAccSchema = new LCInsureAccSchema();
		tLCInsureAccSchema = queryLCInsureAcc(PolNo, InsuAccNo, OtherNo);
		if (tLCInsureAccSchema == null) {
			return null;
		}

		// 填充保险帐户表记价履历表
		tLimit = PubFun.getNoLimit(ManageCom);
		tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
		if (money != 0) { // 如果注入资金=0，不添加轨迹
			serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
			tLCInsureAccTraceSchema.setSerialNo(serNo);
			// tLCInsureAccTraceSchema.setInsuredNo(tLCInsureAccSchema
			// .getInsuredNo());
			tLCInsureAccTraceSchema.setPolNo(tLCInsureAccSchema.getPolNo());
			tLCInsureAccTraceSchema.setMoneyType(MoneyType);
			tLCInsureAccTraceSchema.setRiskCode(tLCInsureAccSchema
					.getRiskCode());
			tLCInsureAccTraceSchema.setOtherNo(OtherNo);
			tLCInsureAccTraceSchema.setOtherType(OtherNoType);
			tLCInsureAccTraceSchema.setMoney(money);
			tLCInsureAccTraceSchema.setContNo(tLCInsureAccSchema.getContNo());
			tLCInsureAccTraceSchema.setGrpPolNo(tLCInsureAccSchema
					.getGrpPolNo());
			tLCInsureAccTraceSchema.setInsuAccNo(tLCInsureAccSchema
					.getInsuAccNo());
			/*
			 * Lis5.3 upgrade set
			 * tLCInsureAccTraceSchema.setAppntName(tLCInsureAccSchema
			 * .getAppntName());
			 */
			tLCInsureAccTraceSchema.setState(tLCInsureAccSchema.getState());
			tLCInsureAccTraceSchema.setManageCom(tLCInsureAccSchema
					.getManageCom());
			tLCInsureAccTraceSchema.setOperator(tLCInsureAccSchema
					.getOperator());
			tLCInsureAccTraceSchema.setMakeDate(CurrentDate);
			tLCInsureAccTraceSchema.setMakeTime(CurrentTime);
			tLCInsureAccTraceSchema.setModifyDate(CurrentDate);
			tLCInsureAccTraceSchema.setModifyTime(CurrentTime);
			tLCInsureAccTraceSchema.setPayDate(CurrentDate);
		}

		// 修改保险帐户金额
		tLCInsureAccSchema.setInsuAccBala(tLCInsureAccSchema.getInsuAccBala()
				+ money);
		tLCInsureAccSchema.setSumPay(tLCInsureAccSchema.getSumPay() + money);

		// tLCInsureAccSchema.setInsuAccGetMoney(tLCInsureAccSchema.getInsuAccGetMoney()+money);
		// 添加容器
		tLCInsureAccSet.add(tLCInsureAccSchema);
		tLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
		if (tLCInsureAccSet.size() == 0) {
			// // @@错误处理
			// CError tError =new CError();
			// tError.moduleName="DealAccount";
			// tError.functionName="addPrem";
			// tError.errorMessage="条件不符合，没有生成纪录";
			// this.mErrors .addOneError(tError) ;
			return null;
		}
		tVData.add(tLCInsureAccSet);
		tVData.add(tLCInsureAccTraceSet);

		return tVData;

		// 操作数据库时，只需要更新tLCInsureAccSet，插入tLCInsureAccTraceSet
	}

	// --------下面是四个主要的函数中调用的相关附属函数---------------

	/**
	 * 检验传入数据是否完整
	 * 
	 * @param parmData
	 *            传入数据
	 * @return boolean
	 */
	public boolean checkTransferData(TransferData parmData) {
		if (parmData == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "checkTransferData";
			tError.errorMessage = "传入数据不能为空";
			this.mErrors.addOneError(tError);

			return false;
		}
		try {
			String tPolNo = (String) parmData.getValueByName("PolNo");
			String tAccCreatePos = (String) parmData
					.getValueByName("AccCreatePos");
			String tOtherNo = (String) parmData.getValueByName("OtherNo");
			String tOtherNoType = (String) parmData
					.getValueByName("OtherNoType");

			// Double tRate=(Double)parmData.getValueByName("Rate");
			// //不校验费率，可以为空
			String FieldName = "";
			boolean errFlag = false;
			if (tPolNo == null) {
				FieldName = "PolNo";
				errFlag = true;
			} else if (tAccCreatePos == null) {
				FieldName = "AccCreatePos";
				errFlag = true;
			} else if (tOtherNo == null) {
				FieldName = "OtherNo";
				errFlag = true;
			} else if (tOtherNoType == null) {
				FieldName = "OtherNoType";
				errFlag = true;
			}
			if (errFlag) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "DealAccount";
				tError.functionName = "checkTransferData";
				tError.errorMessage = "没有接受到字段名为'" + FieldName + "'的数据";
				this.mErrors.addOneError(tError);

				return false;
			}
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "checkTransferData";
			tError.errorMessage = "错误原因:传入的数据类型不匹配";
			this.mErrors.addOneError(tError);

			return false;
		}

		return true;
	}

	/**
	 * 根据保单号查询保费项表
	 * 
	 * @param PolNo
	 *            保单号
	 * @return LCPremSet or null
	 */
	public LCPremSet queryLCPrem(String PolNo) {
		String sqlStr = "select * from LCPrem where PolNo='" + "?w8?"
				+ "' and needacc='1'";
		// logger.debug(sqlStr);
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(sqlStr);
		sqlbv2.put("w8", PolNo);

		LCPremSchema tLCPremSchema = new LCPremSchema();
		LCPremSet tLCPremSet = new LCPremSet();
		LCPremDB tLCPremDB = tLCPremSchema.getDB();
		tLCPremSet = tLCPremDB.executeQuery(sqlbv2);
		if (tLCPremDB.mErrors.needDealError() == true) {
			// @@错误处理
			logger.debug("queryLCPrem保费项表查询失败");
			this.mErrors.copyAllErrors(tLCPremDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLCPrem";
			tError.errorMessage = "保费项表查询失败!";
			this.mErrors.addOneError(tError);
			tLCPremSet.clear();

			return null;
		}
		if (tLCPremSet.size() == 0) {
			return null;
		}

		return tLCPremSet;
	}

	/**
	 * 通过传入的保费项纪录查询得到责任交费纪录集合
	 * 
	 * @param pLCPremSet
	 *            传入的保费项纪录
	 * @return VData
	 */
	public VData getFromLMDutyPay(LCPremSet pLCPremSet) {
		if (pLCPremSet == null) {
			// @@错误处理
			logger.debug("getFromLMDutyPay传入参数不能为空");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "getFromLMDutyPay";
			tError.errorMessage = "传入参数不能为空!";
			this.mErrors.addOneError(tError);

			return null;
		}

		// 1 循环判断交费计划编码的前6位全0；2 判断交费计划编码的前6位是否有重复值；
		LCPremSchema tLCPremSchema = new LCPremSchema();
		LCPremSet tLCPremSet = new LCPremSet();
		LMDutyPaySchema tLMDutyPaySchema = new LMDutyPaySchema();
		LMDutyPaySet tLMDutyPaySet = new LMDutyPaySet();
		String[] payPlanCode = new String[pLCPremSet.size()];
		int i = 0;
		String strCode = "";
		for (int n = 1; n <= pLCPremSet.size(); n++) {
			tLCPremSchema = pLCPremSet.get(n);
			strCode = StrTool.space(tLCPremSchema.getPayPlanCode(), 6);
			// logger.debug("@@@@strCode="+strCode);
			if (i == 0) {
				if (!strCode.equals("000000")) {
					payPlanCode[i] = strCode;
					i++;
					tLMDutyPaySchema = new LMDutyPaySchema();
					tLMDutyPaySchema = queryLMDutyPay(strCode);
					if (tLMDutyPaySchema == null) {
						// @@错误处理
						logger.debug("没有找到缴费编码=" + strCode + "的责任交费纪录");
						CError tError = new CError();
						tError.moduleName = "DealAccount";
						tError.functionName = "getFromLMDutyPay";
						tError.errorMessage = "没有找到缴费编码=" + strCode + "的责任交费纪录";
						this.mErrors.addOneError(tError);

						return null;
					}
					tLMDutyPaySet.add(tLMDutyPaySchema);
					tLCPremSet.add(tLCPremSchema);
				}
			} else {
				boolean saveFlag = true;
				if (!strCode.equals("000000")) {
					for (int m = 0; m < i; m++) {
						if (strCode.equals(payPlanCode[m])) {
							saveFlag = false;

							break;
						}
					}
					if (saveFlag) {
						payPlanCode[i] = strCode;
						i++;
						tLMDutyPaySchema = new LMDutyPaySchema();
						tLMDutyPaySchema = queryLMDutyPay(strCode);
						if (tLMDutyPaySchema == null) {
							// @@错误处理
							logger.debug("没有找到缴费编码=" + strCode
									+ "的责任交费纪录");
							CError tError = new CError();
							tError.moduleName = "DealAccount";
							tError.functionName = "getFromLMDutyPay";
							tError.errorMessage = "没有找到缴费编码=" + strCode
									+ "的责任交费纪录";
							this.mErrors.addOneError(tError);

							return null;
						}
						tLMDutyPaySet.add(tLMDutyPaySchema);
						tLCPremSet.add(tLCPremSchema);
					}
				}
			}
		}
		if ((tLMDutyPaySet.size() == 0) || (tLCPremSet.size() == 0)) {
			// @@错误处理
			logger.debug("没有找到责任交费纪录");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "getFromLMDutyPay";
			tError.errorMessage = "没有找到责任交费纪录";
			this.mErrors.addOneError(tError);

			return null;
		}

		VData tVData = new VData();
		tVData.add(tLMDutyPaySet);
		tVData.add(tLCPremSet);

		return tVData;
	}

	/**
	 * 根据责任交费编码查询责任交费表
	 * 
	 * @param payPlanCode
	 *            从保费项表查询出的交费编码（提取前6位）
	 * @return LMDutyPaySchema or null
	 */
	private LMDutyPaySchema queryLMDutyPay(String payPlanCode) {
		String sqlStr = "select * from LMDutyPay where payPlanCode='"
				+ "?w10?" + "'";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sqlStr);
		sqlbv3.put("w10", payPlanCode);
		LMDutyPaySchema tLMDutyPaySchema = new LMDutyPaySchema();
		LMDutyPaySet tLMDutyPaySet = new LMDutyPaySet();
		LMDutyPayDB tLMDutyPayDB = tLMDutyPaySchema.getDB();
		tLMDutyPaySet = tLMDutyPayDB.executeQuery(sqlbv3);
		if (tLMDutyPayDB.mErrors.needDealError() == true) {
			// @@错误处理
			logger.debug("queryLMDutyPay责任交费表查询失败");
			this.mErrors.copyAllErrors(tLMDutyPayDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMDutyPay";
			tError.errorMessage = "责任交费表查询失败!";
			this.mErrors.addOneError(tError);
			tLMDutyPaySet.clear();

			return null;
		}
		if (tLMDutyPaySet.size() == 0) {
			// @@错误处理
			logger.debug("queryLMDutyPay责任交费表没有查询到相关数据");
			this.mErrors.copyAllErrors(tLMDutyPayDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMDutyPay";
			tError.errorMessage = "责任交费表没有查询到相关数据!";
			this.mErrors.addOneError(tError);
			tLMDutyPaySet.clear();

			return null;
		}

		return tLMDutyPaySet.get(1);
	}

	/**
	 * 生成保费项表和客户账户表的关联表
	 * 
	 * @param tVData
	 *            包含责任交费和保费项集合
	 * @param PolNo
	 *            保单号
	 * @param AccCreatePos
	 *            生成帐户的流程位置标记（承保，交费等）
	 * @param Rate
	 *            提取比率
	 * @return LCPremToAccSet
	 */
	public LCPremToAccSet createPremToAcc(VData tVData, String PolNo,
			String AccCreatePos, Double Rate) {
		if ((tVData == null) || (PolNo == null) || (AccCreatePos == null)) {
			// @@错误处理
			logger.debug("createPremToAcc传入数据不能为空");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "createPremToAcc";
			tError.errorMessage = "传入数据不能为空!";
			this.mErrors.addOneError(tError);

			return null;
		}

		LCPremSet tLCPremSet = new LCPremSet();
		LMDutyPaySet tLMDutyPaySet = new LMDutyPaySet();
		tLCPremSet = (LCPremSet) tVData.getObjectByObjectName("LCPremSet", 0);
		tLMDutyPaySet = (LMDutyPaySet) tVData.getObjectByObjectName(
				"LMDutyPaySet", 0);

		LCPremSchema tLCPremSchema = new LCPremSchema(); // 保费项表
		LMDutyPaySchema tLMDutyPaySchema = new LMDutyPaySchema(); // 责任交费表
		LMRiskAccPaySchema tLMRiskAccPaySchema = new LMRiskAccPaySchema(); //
		LCPremToAccSet tLCPremToAccSet = new LCPremToAccSet(); // 保费项表和客户帐户表的关联表
		LCPremToAccSchema tLCPremToAccSchema = new LCPremToAccSchema(); // 保费项表和客户帐户表的关联表

		double tRate = 0;
		for (int i = 1; i <= tLMDutyPaySet.size(); i++) {
			tLMDutyPaySchema = tLMDutyPaySet.get(i);
			tLCPremSchema = tLCPremSet.get(i);

			// 判断是否和帐户关联
			if (tLMDutyPaySchema.getNeedAcc().equals("1")) {
				// 查询险种保险帐户缴费表
				tLMRiskAccPaySchema = new LMRiskAccPaySchema();
				tLMRiskAccPaySchema = queryLMRiskAccPay(tLMDutyPaySchema,
						tLCPremSchema, PolNo);
				if (tLMRiskAccPaySchema == null) {
					return null;
				}

				// 判断生成位置标记是否匹配
				if (AccCreatePos.equals(tLMRiskAccPaySchema.getAccCreatePos())) {
					// 判断费率是否需要录入
					if (tLMRiskAccPaySchema.getNeedInput().equals("1")) {
						// 如果需要录入:判断传入的费率是否为空
						if (Rate == null) {
							// @@错误处理
							logger.debug("createPremToAcc费率需要从界面录不能为空");
							CError tError = new CError();
							tError.moduleName = "DealAccount";
							tError.functionName = "createPremToAcc";
							tError.errorMessage = "费率需要从界面录入，不能为空!";
							this.mErrors.addOneError(tError);

							return null;
						}
						tRate = Rate.doubleValue();
					} else { // 取默认值
						tRate = tLMRiskAccPaySchema.getDefaultRate();
					}

					tLCPremToAccSchema = new LCPremToAccSchema();
					tLCPremToAccSchema.setPolNo(PolNo);
					tLCPremToAccSchema.setDutyCode(tLCPremSchema.getDutyCode());
					tLCPremToAccSchema.setPayPlanCode(tLCPremSchema
							.getPayPlanCode());
					tLCPremToAccSchema.setInsuAccNo(tLMRiskAccPaySchema
							.getInsuAccNo());
					tLCPremToAccSchema.setRate(tRate);
					tLCPremToAccSchema.setNewFlag(tLMRiskAccPaySchema
							.getAccCreatePos());
					tLCPremToAccSchema.setCalCodeMoney(tLMRiskAccPaySchema
							.getCalCodeMoney());
					tLCPremToAccSchema.setCalCodeUnit(tLMRiskAccPaySchema
							.getCalCodeUnit());
					tLCPremToAccSchema.setCalFlag(tLMRiskAccPaySchema
							.getCalFlag());
					tLCPremToAccSchema.setOperator(tLCPremSchema.getOperator());
					tLCPremToAccSchema.setModifyDate(CurrentDate);
					tLCPremToAccSchema.setModifyTime(CurrentTime);
					tLCPremToAccSet.add(tLCPremToAccSchema);
				}
			}
		}

		if (tLCPremToAccSet.size() == 0) {
			// @@错误处理
			return null;
		}

		return tLCPremToAccSet;
	}

	/**
	 * 查询险种保险帐户缴费表
	 * 
	 * @param pLMDutyPaySchema
	 * @param pLCPremSchema
	 * @param PolNo
	 * @return LMRiskAccPaySchema
	 */
	public LMRiskAccPaySchema queryLMRiskAccPay(
			LMDutyPaySchema pLMDutyPaySchema, LCPremSchema pLCPremSchema,
			String PolNo) {
		if ((pLMDutyPaySchema == null) || (pLCPremSchema == null)
				|| (PolNo == null)) {
			// @@错误处理
			logger.debug("queryLMRiskAccPay传入数据不能为空");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRiskAccPay";
			tError.errorMessage = "传入数据不能为空!";
			this.mErrors.addOneError(tError);

			return null;
		}

		// 查询保单表
		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolSchema = queryLCPol(PolNo);
		if (tLCPolSchema == null) { // 取默认值

			return null;
		}

		String riskCode = tLCPolSchema.getRiskCode();
		String payPlanCode = pLMDutyPaySchema.getPayPlanCode();

		// 查询险种保险帐户缴费表
		String sqlStr = "select * from LMRiskAccPay where RiskCode='" 
				+ "?w11?" + "' and payPlanCode='" + "?w12?" + "'";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(sqlStr);
		sqlbv4.put("w11", riskCode);
		sqlbv4.put("w12", payPlanCode);
		LMRiskAccPaySchema tLMRiskAccPaySchema = new LMRiskAccPaySchema();
		LMRiskAccPaySet tLMRiskAccPaySet = new LMRiskAccPaySet();
		LMRiskAccPayDB tLMRiskAccPayDB = tLMRiskAccPaySchema.getDB();
		tLMRiskAccPaySet = tLMRiskAccPayDB.executeQuery(sqlbv4);
		if (tLMRiskAccPayDB.mErrors.needDealError() == true) {
			// @@错误处理
			logger.debug("queryLMRiskAccPay险种保险帐户缴费表查询失败");
			this.mErrors.copyAllErrors(tLMRiskAccPayDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRiskAccPay";
			tError.errorMessage = "险种保险帐户缴费表查询失败!";
			this.mErrors.addOneError(tError);
			tLMRiskAccPaySet.clear();

			return null;
		}
		if (tLMRiskAccPaySet.size() == 0) {
			// @@错误处理
			logger.debug("queryLMRiskAccPay险种保险帐户缴费表没有查询到相关数据");
			this.mErrors.copyAllErrors(tLMRiskAccPayDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRiskAccPay";
			tError.errorMessage = "险种保险帐户缴费表没有查询到相关数据!";
			this.mErrors.addOneError(tError);
			tLMRiskAccPaySet.clear();

			return null;
		}

		return tLMRiskAccPaySet.get(1);
	}

	/**
	 * 查询险种保险帐户缴费表2
	 * 
	 * @param pLCPremToAccSchema
	 * @return LMRiskAccPaySchema
	 */
	public LMRiskAccPaySchema queryLMRiskAccPay2(
			LCPremToAccSchema pLCPremToAccSchema) {
		if (pLCPremToAccSchema == null) {
			// @@错误处理
			logger.debug("queryLMRiskAccPay2传入数据不能为空");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRiskAccPay2";
			tError.errorMessage = "传入数据不能为空!";
			this.mErrors.addOneError(tError);

			return null;
		}

		// 查询保单表
		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolSchema = queryLCPol(pLCPremToAccSchema.getPolNo());
		if (tLCPolSchema == null) {
			return null;
		}

		String riskCode = tLCPolSchema.getRiskCode();
		String payPlanCode = pLCPremToAccSchema.getPayPlanCode();
		String InsuAccNo = pLCPremToAccSchema.getInsuAccNo();

		// 查询险种保险帐户缴费表
		String sqlStr = "select * from LMRiskAccPay where RiskCode='"
				+ "?w15?" + "' and payPlanCode='" + "?w16?"
				+ "' and InsuAccNo='" + "?w17?" + "'";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(sqlStr);
		sqlbv5.put("w15", riskCode);
		sqlbv5.put("w16", payPlanCode);
		sqlbv5.put("w17", InsuAccNo);
		LMRiskAccPaySchema tLMRiskAccPaySchema = new LMRiskAccPaySchema();
		LMRiskAccPaySet tLMRiskAccPaySet = new LMRiskAccPaySet();
		LMRiskAccPayDB tLMRiskAccPayDB = tLMRiskAccPaySchema.getDB();
		tLMRiskAccPaySet = tLMRiskAccPayDB.executeQuery(sqlbv5);
		if (tLMRiskAccPayDB.mErrors.needDealError() == true) {
			// @@错误处理
			logger.debug("queryLMRiskAccPay2险种保险帐户缴费表查询失败");
			this.mErrors.copyAllErrors(tLMRiskAccPayDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRiskAccPay2";
			tError.errorMessage = "险种保险帐户缴费表查询失败!";
			this.mErrors.addOneError(tError);
			tLMRiskAccPaySet.clear();

			return null;
		}
		if (tLMRiskAccPaySet.size() == 0) {
			// @@错误处理
			logger.debug("queryLMRiskAccPay2险种保险帐户缴费表没有查询到相关数据");
			this.mErrors.copyAllErrors(tLMRiskAccPayDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRiskAccPay2";
			tError.errorMessage = "险种保险帐户缴费表没有查询到相关数据!";
			this.mErrors.addOneError(tError);
			tLMRiskAccPaySet.clear();

			return null;
		}

		return tLMRiskAccPaySet.get(1);
	}

	/**
	 * 查询险种保险帐户缴费表3
	 * 
	 * @param riskcode
	 * @param LCPremToAccSchema
	 * @return LMRiskAccPaySchema
	 */
	public LMRiskAccPaySchema queryLMRiskAccPay3(String riskCode,
			LCPremToAccSchema pLCPremToAccSchema) {
		if (riskCode == null) {
			// @@错误处理
			logger.debug("queryLMRiskAccPay3传入数据不能为空");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRiskAccPay3";
			tError.errorMessage = "传入数据不能为空!";
			this.mErrors.addOneError(tError);

			return null;
		}

		String payPlanCode = pLCPremToAccSchema.getPayPlanCode();
		String InsuAccNo = pLCPremToAccSchema.getInsuAccNo();

		// 查询险种保险帐户缴费表
		String sqlStr = "select * from LMRiskAccPay where RiskCode='"
				+ "?a1?" + "' and payPlanCode='" + "?a2?"
				+ "' and InsuAccNo='" + "?a3?" + "'";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(sqlStr);
		sqlbv6.put("a1", riskCode);
		sqlbv6.put("a2", payPlanCode);
		sqlbv6.put("a3", InsuAccNo);
		LMRiskAccPaySchema tLMRiskAccPaySchema = new LMRiskAccPaySchema();
		LMRiskAccPaySet tLMRiskAccPaySet = new LMRiskAccPaySet();
		LMRiskAccPayDB tLMRiskAccPayDB = tLMRiskAccPaySchema.getDB();
		tLMRiskAccPaySet = tLMRiskAccPayDB.executeQuery(sqlbv6);
		if (tLMRiskAccPayDB.mErrors.needDealError() == true) {
			// @@错误处理
			logger.debug("queryLMRiskAccPay3险种保险帐户缴费表查询失败");
			this.mErrors.copyAllErrors(tLMRiskAccPayDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRiskAccPay2";
			tError.errorMessage = "险种保险帐户缴费表查询失败!";
			this.mErrors.addOneError(tError);
			tLMRiskAccPaySet.clear();

			return null;
		}
		if (tLMRiskAccPaySet.size() == 0) {
			// @@错误处理
			logger.debug("queryLMRiskAccPay3险种保险帐户缴费表没有查询到相关数据");
			this.mErrors.copyAllErrors(tLMRiskAccPayDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRiskAccPay2";
			tError.errorMessage = "险种保险帐户缴费表没有查询到相关数据!";
			this.mErrors.addOneError(tError);
			tLMRiskAccPaySet.clear();

			return null;
		}

		return tLMRiskAccPaySet.get(1);
	}

	/**
	 * 查询保单表
	 * 
	 * @param PolNo
	 * @return
	 */
	public LCPolSchema queryLCPol(String PolNo) {
		logger.debug("帐户内查询保单表");

		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(PolNo);
		if (tLCPolDB.getInfo() == false) {
			// @@错误处理
			logger.debug("queryLCPol保单表查询失败");
			this.mErrors.copyAllErrors(tLCPolDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLCPol";
			tError.errorMessage = "保单表查询失败!";
			this.mErrors.addOneError(tError);

			return null;
		}

		return tLCPolDB.getSchema();
	}

	/**
	 * 得到保险账户分类表的一条记录，如果没有返回null.
	 * 
	 * @param PolNo
	 *            String 个人保单号
	 * @param InsuAccNo
	 *            String 保险账户号
	 * @param PayPlanCode
	 *            String 交费计划编码
	 * @param OtherNo
	 *            String 其它号码
	 * @return LCInsureAccClassSchema
	 */
	public LCInsureAccClassSchema queryLCInsureAccClass(String PolNo,
			String InsuAccNo, String PayPlanCode, String OtherNo) {
		LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
		tLCInsureAccClassDB.setPolNo(PolNo);
		tLCInsureAccClassDB.setInsuAccNo(InsuAccNo);
		tLCInsureAccClassDB.setPayPlanCode(PayPlanCode);
		tLCInsureAccClassDB.setOtherNo(OtherNo);
		/*
		 * tLCInsureAccClassDB.setAccAscription("0");
		 * if(!tLCInsureAccClassDB.getInfo()) { CError tError = new CError();
		 * tError.moduleName = "DealAccount"; tError.functionName =
		 * "queryLCInsureAccClass"; tError.errorMessage = "保险帐户分类表查询失败!";
		 * this.mErrors.addOneError(tError); //tLCInsureAccSet.clear(); return
		 * null;
		 *  }
		 */
		LCInsureAccClassSet mLCInsureAccClassSet = tLCInsureAccClassDB.query();
		if (mLCInsureAccClassSet.size() <= 0) {
			logger.debug("queryLCPol保险帐户分类表查询失败");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLCInsureAccClass";
			tError.errorMessage = "保险帐户分类表查询失败!";
			this.mErrors.addOneError(tError);
			// tLCInsureAccSet.clear();
			return null;
		}
		return mLCInsureAccClassSet.get(1);
	}

	/**
	 * 查询保险帐户表(传入3个主键，返回唯一纪录)
	 * 
	 * @param PolNo
	 * @param InsuAccNo
	 * @param OtherNo
	 * @return LCInsureAccSchema
	 */
	public LCInsureAccSchema queryLCInsureAcc(String PolNo, String InsuAccNo,
			String OtherNo) {
//		String sqlStr = "select * from LCInsureAcc where PolNo='" + PolNo
//				+ "' ";
//		sqlStr = sqlStr + "and InsuAccNo='" + InsuAccNo + "' ";
//		sqlStr = sqlStr + "and OtherNo='" + OtherNo + "' ";
		
		//查询账户表 为账户轨迹表赋值
		LCInsureAccDB tLCInsureAccDB=new LCInsureAccDB();
		tLCInsureAccDB.setInsuAccNo(InsuAccNo);
		tLCInsureAccDB.setPolNo(PolNo);
		if(!tLCInsureAccDB.getInfo()){
			CError.buildErr(this, "查询"+PolNo+"的账户表错误！");
			return null;
		}

//		LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
//		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
//		tLCInsureAccSet = tLCInsureAccDB.executeQuery(sqlStr);
//		if (tLCInsureAccDB.mErrors.needDealError() == true) {
//			// @@错误处理
//			this.mErrors.copyAllErrors(tLCInsureAccDB.mErrors);
//
//			CError tError = new CError();
//			tError.moduleName = "DealAccount";
//			tError.functionName = "queryLCInsureAcc";
//			tError.errorMessage = "保险帐户表查询失败!";
//			this.mErrors.addOneError(tError);
//			tLCInsureAccSet.clear();
//
//			return null;
//		}
//		if (tLCInsureAccSet.size() == 0) {
//			// @@错误处理
//			this.mErrors.copyAllErrors(tLCInsureAccDB.mErrors);
//
//			CError tError = new CError();
//			tError.moduleName = "DealAccount";
//			tError.functionName = "queryLCInsureAcc";
//			tError.errorMessage = "保险帐户表没有查询到相关数据!";
//			this.mErrors.addOneError(tError);
//			tLCInsureAccSet.clear();
//
//			return null;
//		}

		return tLCInsureAccDB.getSchema();
	}

	/**
	 * 查询保险帐户表(传入一个主键，返回纪录集合)
	 * 
	 * @param PolNo
	 * @return LCInsureAccSet
	 */
	public LCInsureAccSet queryLCInsureAcc(String PolNo) {
		String sqlStr = "select * from LCInsureAcc where PolNo='" + "?a6?"
				+ "' ";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(sqlStr);
		sqlbv6.put("a6", PolNo);
		LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
		LCInsureAccDB tLCInsureAccDB = tLCInsureAccSchema.getDB();
		tLCInsureAccSet = tLCInsureAccDB.executeQuery(sqlbv6);
		if (tLCInsureAccDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCInsureAccDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLCInsureAcc";
			tError.errorMessage = "保险帐户表查询失败!";
			this.mErrors.addOneError(tError);
			tLCInsureAccSet.clear();

			return null;
		}
		if (tLCInsureAccSet.size() == 0) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCInsureAccDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLCInsureAcc";
			tError.errorMessage = "保险帐户表没有查询到相关数据!";
			this.mErrors.addOneError(tError);
			tLCInsureAccSet.clear();

			return null;
		}

		return tLCInsureAccSet;
	}

	/**
	 * 险种定义表查询
	 * 
	 * @param RiskCode
	 * @return
	 */
	public LMRiskSchema queryLMRisk(String RiskCode) {
		String sqlStr = "select * from LMRisk where RiskCode='" + "?a7?"
				+ "'";
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		sqlbv7.sql(sqlStr);
		sqlbv7.put("a7", RiskCode);
		LMRiskSchema tLMRiskSchema = new LMRiskSchema();
		LMRiskSet tLMRiskSet = new LMRiskSet();
		LMRiskDB tLMRiskDB = tLMRiskSchema.getDB();
		tLMRiskSet = tLMRiskDB.executeQuery(sqlbv7);
		if (tLMRiskDB.mErrors.needDealError() == true) {
			// @@错误处理
			logger.debug("queryLMRisk险种定义表查询失败");
			this.mErrors.copyAllErrors(tLMRiskDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRisk";
			tError.errorMessage = "险种定义表查询失败!";
			this.mErrors.addOneError(tError);
			tLMRiskSet.clear();

			return null;
		}
		if (tLMRiskSet.size() == 0) {
			// @@错误处理
			logger.debug("queryLMRisk险种定义表没有查询到相关数据");
			this.mErrors.copyAllErrors(tLMRiskDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRisk";
			tError.errorMessage = "险种定义表没有查询到相关数据!";
			this.mErrors.addOneError(tError);
			tLMRiskSet.clear();

			return null;
		}

		return tLMRiskSet.get(1);
	}

	/**
	 * 查询险种账户关联表
	 * 
	 * @param RiskCode
	 * @return
	 */
	public LMRiskToAccSet queryLMRiskToAcc(String RiskCode) {
		String sqlStr = "select * from LMRiskToAcc where RiskCode='" + "?a8?"
				+ "'";
		SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
		sqlbv8.sql(sqlStr);
		sqlbv8.put("a8", RiskCode);
		LMRiskToAccSchema tLMRiskToAccSchema = new LMRiskToAccSchema();
		LMRiskToAccSet tLMRiskToAccSet = new LMRiskToAccSet();
		LMRiskToAccDB tLMRiskToAccDB = tLMRiskToAccSchema.getDB();
		tLMRiskToAccSet = tLMRiskToAccDB.executeQuery(sqlbv8);
		if (tLMRiskToAccDB.mErrors.needDealError() == true) {
			// @@错误处理
			logger.debug("queryLMRiskToAcc险种账户关联表查询失败");
			this.mErrors.copyAllErrors(tLMRiskToAccDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRiskToAcc";
			tError.errorMessage = "险种账户关联表查询失败!";
			this.mErrors.addOneError(tError);
			tLMRiskToAccSet.clear();

			return null;
		}
		if (tLMRiskToAccSet.size() == 0) {
			// @@错误处理
			logger.debug("queryLMRiskToAcc险种账户关联表没有查询到相关数据");
			this.mErrors.copyAllErrors(tLMRiskToAccDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRiskToAcc";
			tError.errorMessage = "险种账户关联表没有查询到相关数据!";
			this.mErrors.addOneError(tError);
			tLMRiskToAccSet.clear();

			return null;
		}

		return tLMRiskToAccSet;
	}

	/**
	 * 查询险种保险帐户(类型1)
	 * 
	 * @param InsuAccNo
	 * @return
	 */
	public LMRiskInsuAccSchema queryLMRiskInsuAcc(String InsuAccNo) {
		String sqlStr = "select * from LMRiskInsuAcc where InsuAccNo='"
				+ "?a9?" + "'";
		SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
		sqlbv9.sql(sqlStr);
		sqlbv9.put("a9", InsuAccNo);
		LMRiskInsuAccSchema tLMRiskInsuAccSchema = new LMRiskInsuAccSchema();
		LMRiskInsuAccSet tLMRiskInsuAccSet = new LMRiskInsuAccSet();
		LMRiskInsuAccDB tLMRiskInsuAccDB = tLMRiskInsuAccSchema.getDB();
		tLMRiskInsuAccSet = tLMRiskInsuAccDB.executeQuery(sqlbv9);
		if (tLMRiskInsuAccDB.mErrors.needDealError() == true) {
			// @@错误处理
			logger.debug("queryLMRiskInsuAcc险种保险帐户表查询失败");
			this.mErrors.copyAllErrors(tLMRiskInsuAccDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRiskInsuAcc";
			tError.errorMessage = "险种保险帐户表查询失败!";
			this.mErrors.addOneError(tError);
			tLMRiskInsuAccSet.clear();

			return null;
		}
		if (tLMRiskInsuAccSet.size() == 0) {
			// @@错误处理
			logger.debug("queryLMRiskInsuAcc险种保险帐户表没有查询到相关数据");
			this.mErrors.copyAllErrors(tLMRiskInsuAccDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRiskInsuAcc";
			tError.errorMessage = "险种保险帐户表没有查询到相关数据!";
			this.mErrors.addOneError(tError);
			tLMRiskInsuAccSet.clear();

			return null;
		}

		return tLMRiskInsuAccSet.get(1);
	}

	/**
	 * 查询险种保险帐户(类型2)
	 * 
	 * @param InsuAccNo
	 *            帐号
	 * @param AccType
	 *            帐户类型
	 * @return
	 */
	public LMRiskInsuAccSchema queryLMRiskInsuAcc(String InsuAccNo,
			String AccType) {
		String sqlStr = "select * from LMRiskInsuAcc where InsuAccNo='"
				+ "?a11?" + "' and AccType='" + "?a12?" + "'";
		SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
		sqlbv10.sql(sqlStr);
		sqlbv10.put("a11", InsuAccNo);
		sqlbv10.put("a12", AccType);
		LMRiskInsuAccSchema tLMRiskInsuAccSchema = new LMRiskInsuAccSchema();
		LMRiskInsuAccSet tLMRiskInsuAccSet = new LMRiskInsuAccSet();
		LMRiskInsuAccDB tLMRiskInsuAccDB = tLMRiskInsuAccSchema.getDB();
		tLMRiskInsuAccSet = tLMRiskInsuAccDB.executeQuery(sqlbv10);
		if (tLMRiskInsuAccDB.mErrors.needDealError() == true) {
			// @@错误处理
			logger.debug("queryLMRiskInsuAcc2险种保险帐户表查询失败");
			this.mErrors.copyAllErrors(tLMRiskInsuAccDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRiskInsuAcc";
			tError.errorMessage = "险种保险帐户表查询失败!";
			this.mErrors.addOneError(tError);
			tLMRiskInsuAccSet.clear();

			return null;
		}
		if (tLMRiskInsuAccSet.size() == 0) {
			// @@错误处理
			logger.debug("queryLMRiskInsuAcc2险种保险帐户表没有查询到相关数据");
			this.mErrors.copyAllErrors(tLMRiskInsuAccDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRiskInsuAcc";
			tError.errorMessage = "险种保险帐户表没有查询到相关数据!";
			this.mErrors.addOneError(tError);
			tLMRiskInsuAccSet.clear();

			return null;
		}

		return tLMRiskInsuAccSet.get(1);
	}

	/**
	 * 生成给付项表和客户帐户表的关联表
	 * 
	 * @param PolNo
	 * @param AccCreatePos
	 * @param Rate
	 * @return LCGetToAccSet
	 */
	public LCGetToAccSet getGetToAcc(String PolNo, String AccCreatePos,
			Double Rate) {
		// 1-取出领取项表
		LCGetSet tLCGetSet = new LCGetSet();
		tLCGetSet = queryLCGet(PolNo);
		if (tLCGetSet == null) {
			return null;
		}

		// 2-根据领取项表取出对应的责任给付描述表
		// LMDutyGetSet tLMDutyGetSet = new LMDutyGetSet();
		VData tVData = new VData();
		tVData = createLMDutyGet(tLCGetSet);
		if (tVData == null) {
			return null;
		}

		// 3-生成给付项表和客户账户表的关联表
		LCGetToAccSet tLCGetToAccSet = new LCGetToAccSet();
		tLCGetToAccSet = createGetToAcc(tVData, PolNo, AccCreatePos, Rate);

		return tLCGetToAccSet;
	}

	/**
	 * 取出领取项表
	 * 
	 * @param PolNo
	 * @return LCGetSet
	 */
	public LCGetSet queryLCGet(String PolNo) {
		String sqlStr = "select * from LCGet where PolNo='" + "?a13?"
				+ "' and needacc='1'";
		SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
		sqlbv11.sql(sqlStr);
		sqlbv11.put("a13", PolNo);
		LCGetSchema tLCGetSchema = new LCGetSchema();
		LCGetSet tLCGetSet = new LCGetSet();
		LCGetDB tLCGetDB = tLCGetSchema.getDB();
		tLCGetSet = tLCGetDB.executeQuery(sqlbv11);
		if (tLCGetDB.mErrors.needDealError() == true) {
			// @@错误处理
			logger.debug("queryLCGet领取项表查询失败");
			this.mErrors.copyAllErrors(tLCGetDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLCGet";
			tError.errorMessage = "领取项表查询失败!";
			this.mErrors.addOneError(tError);
			tLCGetSet.clear();

			return null;
		}
		if (tLCGetSet.size() == 0) {
			return null;
		}

		return tLCGetSet;
	}

	/**
	 * 根据领取项表取出对应的责任给付描述表
	 * 
	 * @param pLCGetSet
	 * @return VData
	 */
	public VData createLMDutyGet(LCGetSet pLCGetSet) {
		if (pLCGetSet == null) {
			logger.debug("pLCGetSet==不能传入空数据");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMDutyGet";
			tError.errorMessage = "不能传入空数据!";
			this.mErrors.addOneError(tError);
			return null;
		}

		LMDutyGetSchema tLMDutyGetSchema = new LMDutyGetSchema(); // 责任给付
		LMDutyGetSet tLMDutyGetSet = new LMDutyGetSet();
		LCGetSchema tLCGetSchema = new LCGetSchema(); // 领取项表
		LCGetSet tLCGetSet = new LCGetSet();
		for (int i = 1; i <= pLCGetSet.size(); i++) {
			tLCGetSchema = new LCGetSchema();
			tLCGetSchema = pLCGetSet.get(i);
			tLMDutyGetSchema = new LMDutyGetSchema();

			// 查询责任给付表
			tLMDutyGetSchema = new LMDutyGetSchema();
			tLMDutyGetSchema = queryLMDutyGet(tLCGetSchema.getGetDutyCode());
			if (tLMDutyGetSchema == null) {
				continue;
			}
			tLMDutyGetSet.add(tLMDutyGetSchema);
			tLCGetSet.add(tLCGetSchema);
		}
		if (tLMDutyGetSet.size() == 0) {
			logger.debug("tLMDutyGetSet.size==没有查到责任给付纪录"
					+ tLMDutyGetSet.size());
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMDutyGet";
			tError.errorMessage = "没有查到责任给付纪录!";
			this.mErrors.addOneError(tError);

			return null;
		}

		VData tVData = new VData();
		tVData.add(tLMDutyGetSet);
		tVData.add(tLCGetSet);

		return tVData;
	}

	/**
	 * 查询责任给付表
	 * 
	 * @param GetDutyCode
	 * @return
	 */
	public LMDutyGetSchema queryLMDutyGet(String GetDutyCode) {
		String sqlStr = "select * from LMDutyGet where GetDutyCode='"
				+ "?a14?" + "'";
		SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
		sqlbv12.sql(sqlStr);
		sqlbv12.put("a14", GetDutyCode);
		LMDutyGetSchema tLMDutyGetSchema = new LMDutyGetSchema();
		LMDutyGetSet tLMDutyGetSet = new LMDutyGetSet();
		LMDutyGetDB tLMDutyGetDB = tLMDutyGetSchema.getDB();
		tLMDutyGetSet = tLMDutyGetDB.executeQuery(sqlbv12);
		if (tLMDutyGetDB.mErrors.needDealError() == true) {
			// @@错误处理
			logger.debug("queryLMDutyGet责任给付表查询失败");
			this.mErrors.copyAllErrors(tLMDutyGetDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMDutyGet";
			tError.errorMessage = "责任给付表查询失败!";
			this.mErrors.addOneError(tError);
			tLMDutyGetSet.clear();

			return null;
		}
		if (tLMDutyGetSet.size() == 0) {
			// @@错误处理
			logger.debug("queryLMDutyGet责任给付表没有查询到相关数据");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMDutyGet";
			tError.errorMessage = "责任给付表没有查询到相关数据!";
			this.mErrors.addOneError(tError);

			return null;
		}

		return tLMDutyGetSet.get(1);
	}

	/**
	 * 生成给付项表和客户账户表的关联表
	 * 
	 * @param pVData
	 * @param AccCreatePos
	 * @param Rate
	 * @return LCGetToAccSet
	 */
	public LCGetToAccSet createGetToAcc(VData pVData, String PolNo,
			String AccCreatePos, Double Rate) {
		if ((pVData == null) || (AccCreatePos == null) || (PolNo == null)) {
			// @@错误处理
			logger.debug("createGetToAcc传入数据不能为空");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "createGetToAcc";
			tError.errorMessage = "传入数据不能为空!";
			this.mErrors.addOneError(tError);

			return null;
		}

		LMDutyGetSet tLMDutyGetSet = (LMDutyGetSet) pVData
				.getObjectByObjectName("LMDutyGetSet", 0);
		LCGetSet tLCGetSet = (LCGetSet) pVData.getObjectByObjectName(
				"LCGetSet", 0);
		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolSchema = queryLCPol(PolNo);
		if (tLCPolSchema == null) {
			return null;
		}

		LMDutyGetSchema tLMDutyGetSchema = new LMDutyGetSchema(); // 责任给付
		LMRiskAccGetSet tLMRiskAccGetSet = new LMRiskAccGetSet(); // 责任给付
		LMRiskAccGetSchema tLMRiskAccGetSchema = new LMRiskAccGetSchema(); // 险种保险帐户给付
		LCGetToAccSchema tLCGetToAccSchema = new LCGetToAccSchema(); // 给付项表和客户账户表的关联表
		LCGetToAccSet tLCGetToAccSet = new LCGetToAccSet(); // 给付项表和客户账户表的关联表

		for (int i = 1; i <= tLMDutyGetSet.size(); i++) {
			tLMDutyGetSchema = new LMDutyGetSchema();
			tLMDutyGetSchema = tLMDutyGetSet.get(i);

			// 判断是否和帐户相关
			if (tLMDutyGetSchema.getNeedAcc().equals("1")) {
				// 查询险种保险帐户给付表
				tLMRiskAccGetSet = new LMRiskAccGetSet();
				tLMRiskAccGetSet = queryLMRiskAccGet(
						tLCPolSchema.getRiskCode(), tLMDutyGetSchema
								.getGetDutyCode());
				if (tLMRiskAccGetSet == null) {
					continue;
				}
				for (int n = 1; n <= tLMRiskAccGetSet.size(); n++) {
					tLMRiskAccGetSchema = new LMRiskAccGetSchema();
					tLMRiskAccGetSchema = tLMRiskAccGetSet.get(n);
					if (tLMRiskAccGetSchema.getDealDirection().equals("0")
							&& tLMRiskAccGetSchema.getAccCreatePos().equals(
									AccCreatePos)) {
						tLCGetToAccSchema = new LCGetToAccSchema();

						// 判断是否需要录入
						if (tLMRiskAccGetSchema.getNeedInput().equals("1")) {
							if (Rate == null) {
								// @@错误处理
								logger.debug("createGetToAcc费率需要从界面录入");
								CError tError = new CError();
								tError.moduleName = "DealAccount";
								tError.functionName = "createGetToAcc";
								tError.errorMessage = "费率需要从界面录入，不能为空!";
								this.mErrors.addOneError(tError);

								return null;
							}
							tLCGetToAccSchema
									.setDefaultRate(Rate.doubleValue());
						} else {
							tLCGetToAccSchema
									.setDefaultRate(tLMRiskAccGetSchema
											.getDefaultRate());
						}

						tLCGetToAccSchema.setNeedInput(tLMRiskAccGetSchema
								.getNeedInput());
						tLCGetToAccSchema.setPolNo(PolNo);
						tLCGetToAccSchema.setDutyCode(tLCGetSet.get(i)
								.getDutyCode());
						tLCGetToAccSchema.setGetDutyCode(tLMRiskAccGetSchema
								.getGetDutyCode());
						tLCGetToAccSchema.setInsuAccNo(tLMRiskAccGetSchema
								.getInsuAccNo());
						tLCGetToAccSchema.setCalCodeMoney(tLMRiskAccGetSchema
								.getCalCodeMoney());
						tLCGetToAccSchema.setDealDirection(tLMRiskAccGetSchema
								.getDealDirection());
						tLCGetToAccSchema.setCalFlag(tLMRiskAccGetSchema
								.getCalFlag());
						tLCGetToAccSchema.setModifyDate(CurrentDate);
						tLCGetToAccSchema.setModifyTime(CurrentTime);
						tLCGetToAccSet.add(tLCGetToAccSchema);
					}
				}
			}
		}

		if (tLCGetToAccSet.size() == 0) {
			// @@错误处理
			// CError tError = new CError();
			// tError.moduleName = "DealAccount";
			// tError.functionName = "createGetToAcc";
			// tError.errorMessage = "没有符合条件的给付项表和客户账户表的关联表纪录!";
			// this.mErrors.addOneError(tError);
			return null;
		}

		return tLCGetToAccSet;
	}

	/**
	 * 查询险种保险帐户给付表
	 * 
	 * @param RiskCode
	 * @param GetDutyCode
	 * @return
	 */
	public LMRiskAccGetSet queryLMRiskAccGet(String RiskCode, String GetDutyCode) {
		String sqlStr = "select * from LMRiskAccGet where GetDutyCode='"
				+ "?a15?" + "' and RiskCode='" + "?a16?" + "'";
		SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
		sqlbv13.sql(sqlStr);
		sqlbv13.put("a15", GetDutyCode);
		sqlbv13.put("a16", RiskCode);
		logger.debug("sqlStr==" + sqlStr);
		LMRiskAccGetSchema tLMRiskAccGetSchema = new LMRiskAccGetSchema();
		LMRiskAccGetSet tLMRiskAccGetSet = new LMRiskAccGetSet();
		LMRiskAccGetDB tLMRiskAccGetDB = tLMRiskAccGetSchema.getDB();
		tLMRiskAccGetSet = tLMRiskAccGetDB.executeQuery(sqlbv13);
		if (tLMRiskAccGetDB.mErrors.needDealError() == true) {
			// @@错误处理
			logger.debug("queryLMRiskAccGet险种保险帐户给付表查询失败");
			this.mErrors.copyAllErrors(tLMRiskAccGetDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRiskAccGet";
			tError.errorMessage = "险种保险帐户给付表查询失败!";
			this.mErrors.addOneError(tError);
			tLMRiskAccGetSet.clear();

			return null;
		}
		if (tLMRiskAccGetSet.size() == 0) {
			// @@错误处理
			logger.debug("queryLMRiskAccGet2险种保险帐户给付表没有查询到相关数据");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRiskAccGet";
			tError.errorMessage = "险种保险帐户给付表没有查询到相关数据!";
			this.mErrors.addOneError(tError);

			return null;
		}

		return tLMRiskAccGetSet;
	}

	/**
	 * 查询保费项表和客户帐户表的关联表
	 * 
	 * @param pLCPremSchema
	 * @return
	 */
	public LCPremToAccSet queryLCPremToAccSet(LCPremSchema pLCPremSchema) {
		if (pLCPremSchema == null) {
			// @@错误处理
			logger.debug("queryLCPremToAccSet传入数据不能为空");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLCPremToAccSet";
			tError.errorMessage = "传入数据不能为空!";
			this.mErrors.addOneError(tError);

			return null;
		}

		String sqlStr = "select * from LCPremToAcc where PolNo='"
				+ "?a17?" + "' ";
		SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
		sqlbv13.sql(sqlStr);
		sqlbv13.put("a17", pLCPremSchema.getPolNo());
		sqlStr = sqlStr + " and  DutyCode='" + "?DutyCode?"
				+ "'";
		sqlbv13.sql(sqlStr);
		sqlbv13.put("DutyCode", pLCPremSchema.getDutyCode());
		sqlStr = sqlStr + " and PayPlanCode='" + "?PayPlanCode?"
				+ "' ";
		sqlbv13.sql(sqlStr);
		sqlbv13.put("PayPlanCode", pLCPremSchema.getPayPlanCode());
		LCPremToAccSchema tLCPremToAccSchema = new LCPremToAccSchema();
		LCPremToAccSet tLCPremToAccSet = new LCPremToAccSet();
		LCPremToAccDB tLCPremToAccDB = tLCPremToAccSchema.getDB();
		tLCPremToAccSet = tLCPremToAccDB.executeQuery(sqlbv13);
		if (tLCPremToAccDB.mErrors.needDealError() == true) {
			// // @@错误处理
			// this.mErrors.copyAllErrors(tLCPremToAccDB.mErrors);
			// CError tError = new CError();
			// tError.moduleName = "DealAccount";
			// tError.functionName = "queryLCPremToAcc";
			// tError.errorMessage = "查询保费项表和客户帐户表的关联表失败!";
			// this.mErrors.addOneError(tError);
			// tLCPremToAccSet.clear();
			return null;
		}
		if (tLCPremToAccSet.size() == 0) {
			// // @@错误处理
			// this.mErrors.copyAllErrors(tLCPremToAccDB.mErrors);
			// CError tError = new CError();
			// tError.moduleName = "DealAccount";
			// tError.functionName = "queryLCPremToAcc";
			// tError.errorMessage = "保费项表和客户帐户表的关联表没有查询到相关数据!";
			// this.mErrors.addOneError(tError);
			// tLCPremToAccSet.clear();
			return null;
		}

		return tLCPremToAccSet;
	}

	/**
	 * 从传入的保险帐户集合中查询符合条件的纪录
	 * 
	 * @param PolNo
	 * @param InsuAccNo
	 * @param OtherNo
	 * @param pLCInsureAccSet
	 * @return
	 */
	public LCInsureAccSchema queryLCInsureAccSet(String PolNo,
			String InsuAccNo, String OtherNo, LCInsureAccSet pLCInsureAccSet) {
		if ((PolNo == null) || (InsuAccNo == null) || (OtherNo == null)
				|| (pLCInsureAccSet == null)) {
			// @@错误处理
			logger.debug("queryLCInsureAccSet传入数据不能为空");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLCInsureAccSet";
			tError.errorMessage = "传入数据不能为空!";
			this.mErrors.addOneError(tError);

			return null;
		}

		LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
		for (int i = 1; i <= pLCInsureAccSet.size(); i++) {
			tLCInsureAccSchema = new LCInsureAccSchema();
			tLCInsureAccSchema = pLCInsureAccSet.get(i);
			if (tLCInsureAccSchema.getPolNo().equals(PolNo)
					&& tLCInsureAccSchema.getInsuAccNo().equals(InsuAccNo)
			// && tLCInsureAccSchema.getOtherNo().equals(OtherNo)
			) {
				return tLCInsureAccSchema;
			}
		}

		// @@错误处理
		CError tError = new CError();
		tError.moduleName = "DealAccount";
		tError.functionName = "queryLCInsureAccSet";
		tError.errorMessage = "没有从要生成的保险账户中找到匹配的数据!";
		this.mErrors.addOneError(tError);

		return null;
	}

	/**
	 * 计算实际应该注入的资金(类似佣金计算,不过数据库内的计算编码尚未描述)
	 * 
	 * @param tLCPremToAccSchema
	 *            传入保费项表和客户帐户表的关联表纪录
	 * @param Prem
	 *            缴纳保费
	 * @return 实际应该注入的资金
	 */
	public double calInputMoney(LCPremToAccSchema tLCPremToAccSchema,
			double Prem) {
		// @@错误处理
		if (tLCPremToAccSchema == null) {
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "calInputMoneyRate";
			tError.errorMessage = "传入数据不能为空!";
			this.mErrors.addOneError(tError);

			return -1;
		}

		String[] F = new String[5];
		int m = 0;
		double defaultRate = 0;
		double inputMoney = 0;
		String calMoney = "";
		defaultRate = tLCPremToAccSchema.getRate(); // 缺省比例

		Calculator tCalculator = new Calculator(); // 计算类

		if (tLCPremToAccSchema.getCalFlag() == null) { // 如果该标记为空
			inputMoney = Prem * 1 * defaultRate;

			return inputMoney;
		}

		// 账户转入计算标志:0 －－ 完全转入账户
		// 1 －－ 按现金计算转入账户
		// 2 －－ 按股份计算转入账户
		// 3 －－ 先算现金，然后按股份计算。(未做)
		if (tLCPremToAccSchema.getCalFlag().equals("0")) {
			inputMoney = Prem * 1 * defaultRate;

			return inputMoney;
		}
		if (tLCPremToAccSchema.getCalFlag().equals("1")) {
			if (tLCPremToAccSchema.getCalCodeMoney() == null) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "DealAccount";
				tError.functionName = "calInputMoneyRate";
				tError.errorMessage = "未找到转入账户时的算法编码(现金)!";
				this.mErrors.addOneError(tError);

				return -1;
			}
			tCalculator.setCalCode(tLCPremToAccSchema.getCalCodeMoney()); // 添加计算编码

			// 添加计算必要条件：保费
			LCPolDB tLCPolDB = new LCPolDB();

			// 注意：此时保单可能是还没有签单，所以要根据具体情况传入号码（投保单号或保单号）
			tLCPolDB.setPolNo(tLCPremToAccSchema.getPolNo());
			if (tLCPolDB.getInfo() == false) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "DealAccount";
				tError.functionName = "calInputMoneyRate";
				tError.errorMessage = "未找到账户对应的保单!";
				this.mErrors.addOneError(tError);

				return -1;
			}
			tCalculator.addBasicFactor("ManageFeeRate", Double.valueOf(tLCPolDB
					.getManageFeeRate()).toString()); // 管理费比例-参见众悦年金分红-计算编码601304
			tCalculator.addBasicFactor("Prem", Double.valueOf(Prem).toString());

			// 计算要素可后续添加
			calMoney = tCalculator.calculate();
			if (calMoney == null) {
				CError tError = new CError();
				tError.moduleName = "DealAccount";
				tError.functionName = "calInputMoneyRate";
				tError.errorMessage = "计算注入帐户资金失败!";
				this.mErrors.addOneError(tError);

				return -1;
			}
			inputMoney = Double.parseDouble(calMoney);

			return inputMoney;
		}
		if (tLCPremToAccSchema.getCalFlag().equals("2")) {
			if (tLCPremToAccSchema.getCalCodeMoney() == null) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "DealAccount";
				tError.functionName = "calInputMoneyRate";
				tError.errorMessage = "未找到转入账户时的算法编码(股份)";
				this.mErrors.addOneError(tError);

				return -1;
			}
			tCalculator.setCalCode(tLCPremToAccSchema.getCalCodeUnit()); // 添加计算编码

			// 添加计算必要条件：保费
			
			tCalculator.addBasicFactor("Prem",Double.valueOf(Prem).toString());
			calMoney = tCalculator.calculate();
			if (calMoney == null) {
				CError tError = new CError();
				tError.moduleName = "DealAccount";
				tError.functionName = "calInputMoneyRate";
				tError.errorMessage = "计算注入帐户资金失败!";
				this.mErrors.addOneError(tError);

				return -1;
			}
			else
			{
				inputMoney = Double.parseDouble(calMoney);

				return inputMoney;
			}
		}

		return 0;
	}

	/**
	 * 修改保险帐户表记价履历表纪录的交费日期
	 * 
	 * @param PayDate
	 * @param pVData
	 * @return
	 */
	public VData updateLCInsureAccTraceDate(String PayDate, VData pVData) {
		// @@错误处理
		if ((PayDate == null) || (pVData == null)) {
			logger.debug("updateLCInsureAccTraceDate传入数据不能为空");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "updateLCInsureAccTraceDate";
			tError.errorMessage = "传入数据不能为空!";
			this.mErrors.addOneError(tError);

			return null;
		}

		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();

		tLCInsureAccSet = (LCInsureAccSet) pVData.getObjectByObjectName(
				"LCInsureAccSet", 0);
		tLCInsureAccTraceSet = (LCInsureAccTraceSet) pVData
				.getObjectByObjectName("LCInsureAccTraceSet", 0);

		if (tLCInsureAccTraceSet == null) {
			logger.debug("updateLCInsureAccTraceDate中没有找到需要的数据");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "updateLCInsureAccTraceDate";
			tError.errorMessage = "VData中没有找到需要的数据!";
			this.mErrors.addOneError(tError);

			return null;
		}

		VData tVData = new VData();
		LCInsureAccTraceSet newLCInsureAccTraceSet = new LCInsureAccTraceSet();
		LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
		for (int n = 1; n <= tLCInsureAccTraceSet.size(); n++) {
			tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
			tLCInsureAccTraceSchema = tLCInsureAccTraceSet.get(n);
			tLCInsureAccTraceSchema.setPayDate(PayDate);
			newLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
		}
		tVData.add(newLCInsureAccTraceSet);
		tVData.add(tLCInsureAccSet);

		return tVData;
	}

	/**
	 * 为已经存在的集体下个人账户添加交费轨迹(譬如，承保签单时应该注入资金的没有注入)
	 * 
	 * @param GrpPolNo
	 * @return
	 */
	public boolean addPremTraceForAcc(String GrpPolNo, String InsuAccNo,
			double Money) {
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpPolNo(GrpPolNo);
		if (tLCGrpPolDB.getInfo() == false) {
			logger.debug("addPremTraceForAcc没有找到集体保单");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "addPremTraceForAcc";
			tError.errorMessage = "没有找到集体保单!";
			this.mErrors.addOneError(tError);

			return false;
		}

		String ManageCom = tLCGrpPolDB.getManageCom();
		VData tVData = new VData();
		LCInsureAccTraceSet saveLCInsureAccTraceSet = new LCInsureAccTraceSet();
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setGrpPolNo(GrpPolNo);

		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSet = tLCPolDB.query();

		LCPolSchema tLCPolSchema = new LCPolSchema();
		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
		for (int i = 1; i <= tLCPolSet.size(); i++) {
			tLCPolSchema = tLCPolSet.get(i);
			tVData = addPrem(tLCPolSchema.getPolNo(), InsuAccNo, tLCPolSchema
					.getPolNo(), "1", "BF", ManageCom, Money);
			tLCInsureAccTraceSet = (LCInsureAccTraceSet) tVData
					.getObjectByObjectName("LCInsureAccTraceSet", 0);
			if (tLCInsureAccTraceSet != null) {
				saveLCInsureAccTraceSet.add(tLCInsureAccTraceSet);
			}
		}

		Connection conn = DBConnPool.getConnection();

		try {
			conn.setAutoCommit(false);

			LCInsureAccTraceDBSet tLCInsureAccTraceDBSet = new LCInsureAccTraceDBSet(
					conn);
			tLCInsureAccTraceDBSet.add(saveLCInsureAccTraceSet);

			// 数据提交
			if (!tLCInsureAccTraceDBSet.insert()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCInsureAccTraceDBSet.mErrors);

				CError tError = new CError();
				tError.moduleName = "tLPAppntIndDB";
				tError.functionName = "insertData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);

				conn.rollback();
				conn.close();

				return false;
			}

			conn.commit();
		} catch (Exception ex) {
			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
			}
		}

		return true;
	}

	// ---------------为批量处理准备，例如：集体批量签单处理帐户------------------

	/**
	 * 对个人保单生成保险帐户表(类型 1：空帐户,不需要添加履历表纪录)
	 * 
	 * @param PolNo
	 *            保单号
	 * @param AccCreatePos
	 *            生成位置 :1-投保单录入时产生 2－缴费时产生 3－领取时产生
	 * @param OtherNo
	 *            保单号或交费号
	 * @param OtherNoType
	 *            保单号或交费号
	 * @param inVData
	 *            为批量处理传入参数
	 * @return LCInsureAccSet
	 */
	public LCInsureAccSet getLCInsureAccForBat(String PolNo,
			String AccCreatePos, String OtherNo, String OtherNoType,
			LCPolSchema inLCPolSchema, LMRiskSchema inLMRiskSchema) {
		logger.debug("PolNo==" + PolNo);
		logger.debug("AccCreatePos==" + AccCreatePos);
		logger.debug("OtherNo==" + OtherNo);
		logger.debug("OtherNoType==" + OtherNoType);
		logger.debug("inLCPolSchema==" + inLCPolSchema);
		logger.debug("inLMRiskSchema==" + inLMRiskSchema);
		if ((PolNo == null) || (AccCreatePos == null) || (OtherNo == null)
				|| (OtherNoType == null) || (inLCPolSchema == null)
				|| (inLMRiskSchema == null)) {
			// @@错误处理
			logger.debug("getLCInsureAccForBat传入数据不能为空");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "getLCInsureAccForBat";
			tError.errorMessage = "传入数据不能为空!";
			this.mErrors.addOneError(tError);

			return null;
		}

		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet(); // 保险帐户表

		if (inLMRiskSchema.getInsuAccFlag().equals("Y")
				|| inLMRiskSchema.getInsuAccFlag().equals("y")) {
			// 根据险种查询LMRiskToAcc表(险种账户关联表)
			LMRiskToAccSet tLMRiskToAccSet = new LMRiskToAccSet();
			tLMRiskToAccSet = queryLMRiskToAcc(inLCPolSchema.getRiskCode());
			if (tLMRiskToAccSet == null) {
				return null;
			}

			LMRiskToAccSchema tLMRiskToAccSchema = new LMRiskToAccSchema(); // 险种账户关联表
			LMRiskInsuAccSchema tLMRiskInsuAccSchema = new LMRiskInsuAccSchema(); // 险种保险帐户
			LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema(); // 保险帐户表

			for (int i = 1; i <= tLMRiskToAccSet.size(); i++) {
				// 根据保险账户号码查询LMRiskInsuAcc表(险种保险帐户)
				tLMRiskToAccSchema = tLMRiskToAccSet.get(i);
				tLMRiskInsuAccSchema = new LMRiskInsuAccSchema();
				tLMRiskInsuAccSchema = queryLMRiskInsuAcc(tLMRiskToAccSchema
						.getInsuAccNo());
				if (tLMRiskInsuAccSchema == null) {
					return null;
				}

				// 如果帐户类型是集体帐户,退出
				if (tLMRiskInsuAccSchema.getAccType().equals("001")) {
					// 如果保单类型是-2 --（团单）公共帐户(例如：众悦年金的集体帐户，从界面录入个人时选择保单类型为2)
					// 此时这个代表集体的个人除了生成个人的账户外，多生成集体的账户
					if ((inLCPolSchema.getContType() != null)
							&& inLCPolSchema.getContType().equals("2")) {
						logger.debug("需要生成集体帐户");
					} else {
						continue;
					}
				}

				// 生成保险账户表
				// 如果账户生成位置找到匹配的保险账户
				if (tLMRiskInsuAccSchema.getAccCreatePos().equals(AccCreatePos)) {
					tLCInsureAccSchema = new LCInsureAccSchema();
					// tLCInsureAccSchema.setPolNo(PolNo);
					tLCInsureAccSchema.setPolNo(inLCPolSchema.getPolNo());
					tLCInsureAccSchema.setInsuAccNo(tLMRiskInsuAccSchema
							.getInsuAccNo());
					tLCInsureAccSchema.setRiskCode(tLMRiskToAccSchema
							.getRiskCode());
					tLCInsureAccSchema.setAccType(tLMRiskInsuAccSchema
							.getAccType());
					// tLCInsureAccSchema.setOtherNo(OtherNo);
					// tLCInsureAccSchema.setOtherType(OtherNoType);
					tLCInsureAccSchema.setGrpContNo(inLCPolSchema
							.getGrpContNo());
					tLCInsureAccSchema.setPrtNo(inLCPolSchema.getPrtNo());
					tLCInsureAccSchema.setContNo(inLCPolSchema.getContNo());
					tLCInsureAccSchema.setGrpPolNo(inLCPolSchema.getGrpPolNo());
					tLCInsureAccSchema.setInsuredNo(inLCPolSchema
							.getInsuredNo());
					tLCInsureAccSchema.setAppntNo(inLCPolSchema.getAppntNo());
					// tLCInsureAccSchema.setAppntName(inLCPolSchema.getAppntName());

					/**
					 * ------------coding by heyq 20050719
					 * 此处已处理万能险（902），但未处理投联----------------
					 */
					// tLCInsureAccSchema.setSumPay(tAccFee);
					// tLCInsureAccSchema.setInsuAccBala(tAccFee);
					tLCInsureAccSchema.setSumPay(0);
					tLCInsureAccSchema.setInsuAccBala(0);
					tLCInsureAccSchema.setUnitCount(0);
					tLCInsureAccSchema.setInsuAccGetMoney(0);
					tLCInsureAccSchema.setFrozenMoney(0);
					// tLCInsureAccSchema.setLastAccBala(tAccFee);
					tLCInsureAccSchema.setLastAccBala(0);
					tLCInsureAccSchema.setLastUnitCount(0);
					tLCInsureAccSchema.setLastUnitPrice(0);
					tLCInsureAccSchema.setUnitPrice(0);
					/**
					 * ------------coding by heyq 20050719
					 * 此处已处理万能险（902），但未处理投联----------------
					 */
					tLCInsureAccSchema.setAccComputeFlag(tLMRiskInsuAccSchema
							.getAccComputeFlag());
					tLCInsureAccSchema.setAccType(tLMRiskInsuAccSchema
							.getAccType());
					tLCInsureAccSchema.setManageCom(inLCPolSchema
							.getManageCom());
					tLCInsureAccSchema.setOperator(inLCPolSchema.getOperator());
					tLCInsureAccSchema.setBalaDate(inLCPolSchema.getCValiDate());
					tLCInsureAccSchema.setBalaTime("00:00:00");
					tLCInsureAccSchema.setMakeDate(CurrentDate);
					tLCInsureAccSchema.setMakeTime(CurrentTime);
					tLCInsureAccSchema.setModifyDate(CurrentDate);
					tLCInsureAccSchema.setModifyTime(CurrentTime);
					tLCInsureAccSchema.setState("0");
					tLCInsureAccSchema.setInvestType(tLMRiskInsuAccSchema
							.getInvestType());
					tLCInsureAccSchema.setFundCompanyCode(tLMRiskInsuAccSchema
							.getFundCompanyCode());
					tLCInsureAccSchema
							.setOwner(tLMRiskInsuAccSchema.getOwner());
					//tongmeng 2010-11-08 modify
					//增加多币种处理
					//tongmeng 2010-11-30 modify
					if(tLMRiskInsuAccSchema.getCurrency()!=null&&!tLMRiskInsuAccSchema.getCurrency().equals(""))
					{
						tLCInsureAccSchema.setCurrency(tLMRiskInsuAccSchema.getCurrency());
					}
					else
					{
						tLCInsureAccSchema.setCurrency(inLCPolSchema.getCurrency());
					}
					tLCInsureAccSet.add(tLCInsureAccSchema);
				}
			}

			return tLCInsureAccSet;
		}

		return null;
	}

	/**
	 * 生成保险帐户(生成结构:构建保险账户表,构建保费项表和客户账户表的关联表,构建给付项表和客户账户表的关联表)
	 * 
	 * @param parmData
	 *            (Type:TransferData include:
	 *            PolNo，AccCreatePos，OtherNo，OtherNoType，Rate)
	 * @return VData (include: LCInsureAccSet，LCPremToAccSet，LCGetToAccSet)
	 */
	public VData createInsureAccForBat(TransferData parmData,
			LCPolSchema inLCPolSchema, LMRiskSchema inLMRiskSchema) {
		// 1-检验
		if (!checkTransferData(parmData)) {
			return null;
		}

		if ((inLCPolSchema == null) || (inLMRiskSchema == null)) {
			return null;
		}

		// 2-得到数据后用
		String tPolNo = (String) parmData.getValueByName("PolNo");
		String tAccCreatePos = (String) parmData.getValueByName("AccCreatePos");
		String tOtherNo = (String) parmData.getValueByName("OtherNo");
		String tOtherNoType = (String) parmData.getValueByName("OtherNoType");
		Double tRate;
		if (parmData.getValueByName("Rate") == null) {
			tRate = null;
		} else if (parmData.getValueByName("Rate").getClass().getName().equals(
				"java.lang.String")) {
			String strRate = (String) parmData.getValueByName("Rate");
			tRate = Double.valueOf(strRate);
		} else {
			tRate = (Double) parmData.getValueByName("Rate");
		}
		logger.debug("费率:" + tRate);

		// 3-构建保险账户表
		// 增加判断，如果是健康险则调用 getLCInsureAccForHealth 方法
		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();

		LMRiskAppSchema tLMRiskAppSchema = new LMRiskAppSchema();
		LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
		tLMRiskAppDB.setRiskCode(inLMRiskSchema.getRiskCode());
		tLMRiskAppSchema = tLMRiskAppDB.query().get(1);
		logger.debug("险种 " + tLMRiskAppSchema.getRiskCode()
				+ " 's RiskType is : " + tLMRiskAppSchema.getRiskType());
		if (tLMRiskAppSchema.getRiskType() != null
				&& tLMRiskAppSchema.getRiskType().equals("H")) {
			logger.debug("险种 " + tLMRiskAppSchema.getRiskCode()
					+ " 's RiskType is : " + tLMRiskAppSchema.getRiskType()
					+ "是健康险,调用 getLCInsureAccForHealth 方法生成帐户");
			tLCInsureAccSet = getLCInsureAccForHealth(tPolNo, tAccCreatePos,
					tOtherNo, tOtherNoType, inLCPolSchema, inLMRiskSchema);
			if (tLCInsureAccSet == null) {
				logger.debug("帐户生成失败!!!!!!!!!!");
				return null;
			}

		} else {
			tLCInsureAccSet = getLCInsureAccForBat(tPolNo, tAccCreatePos,
					tOtherNo, tOtherNoType, inLCPolSchema, inLMRiskSchema);
			if (tLCInsureAccSet == null) {
				return null;
			}
		}
		// logger.debug("@@@@tLCInsureAccSet.size="+tLCInsureAccSet.size());

		// 4-构建保费项表和客户账户表的关联表
		LCPremToAccSet tLCPremToAccSet = new LCPremToAccSet();
		tLCPremToAccSet = getPremToAccForBat(tPolNo, tAccCreatePos, tRate,
				inLCPolSchema);

		// 4.5 构建账户分类表
		LCInsureAccClassSet tLCInsureAccClassSet = null;
		if (tLCPremToAccSet != null) {
			tLCInsureAccClassSet = getLCInsureAccClassForBat(inLCPolSchema,
					tLCPremToAccSet);
		}
		/***********************************************************************
		 * Add by niuzj,2007-05-30 创建312001险的累计生息帐户:
		 * 1，只需要生成lcinsureacc/lcinsureaccclass表的数据 2，其余表数据都不需要生成 3，对于产品定义需要描述：
		 * 1）、lmrisk表中的InsuAccFlag描述成Y； 2）、lmrisktoacc表中描述险种和帐户的关联关系；
		 * 3）、lmriskinsuacc表中描述帐户的具体信息；
		 * 4）、lmrisksort表中描述一条risksorttype='50'的记录，表示该险种需要生成
		 * 累计生息帐户，其中risksortvalue为帐户代码；
		 * 5）、帐户的缴费和领取表lmriskaccpay/lmriskaccget都不用描述。
		 * 4，由于该帐户没有缴费，所以不能用上面方法去生成Class表，所以自己新写一个方法。
		 */
		if (tLCInsureAccClassSet == null) {

			LMRiskSortDB tLMRiskSortDB = new LMRiskSortDB();
			tLMRiskSortDB.setRiskCode(inLCPolSchema.getRiskCode());
			tLMRiskSortDB.setRiskSortType("50");
			LMRiskSortSet tLMRiskSortSet = tLMRiskSortDB.query();
			// 能查到定义的数据
			if (tLMRiskSortSet.size() > 0) {
				tLCInsureAccClassSet = getLCInsureAccClassForBat(inLCPolSchema,
						inLMRiskSchema);
			}
		}

		// if(tLCPremToAccSet==null) return null;
		// 5-构建给付项表和客户账户表的关联表
		LCGetToAccSet tLCGetToAccSet = new LCGetToAccSet();
		tLCGetToAccSet = getGetToAccForBat(tPolNo, tAccCreatePos, tRate,
				inLCPolSchema);

		// LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
		// tLCInsureAccTraceSet =
		// getInsureAccTraceForBat(tLCInsureAccClassSet,inLCPolSchema);

		// if(tLCGetToAccSet==null) return null;
		// 6-返回数据
		VData tVData = new VData();
		tVData.add(tLCInsureAccSet);
		logger.debug("tLCInsureAccSet==" + tLCInsureAccSet);
		tVData.add(tLCPremToAccSet); // 可能是null
		logger.debug("tLCPremToAccSet==" + tLCPremToAccSet);
		tVData.add(tLCInsureAccClassSet);
		logger.debug("tLCInsureAccClassSet==" + tLCInsureAccClassSet);
		tVData.add(tLCGetToAccSet); // 可能是null
		logger.debug("tLCGetToAccSet==" + tLCGetToAccSet);

		return tVData;
	}

	/**
	 * 创建客户账户轨迹表结构
	 * 
	 * @return LCInsureAccTraceSet
	 */
	public LCInsureAccTraceSet getInsureAccTraceForBat(
			LCInsureAccClassSet tLCInsureAccClassSet, LCPolSchema tLCPolSchema) {

		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
		LCInsureAccTraceSchema tLCInsureAccTraceSchema;
		LMRiskFeeSchema tLMRiskFeeSchema;
		for (int i = 1; i <= tLCInsureAccClassSet.size(); i++) {

			tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
			tLCInsureAccTraceSchema.setGrpContNo(tLCInsureAccClassSet.get(i)
					.getGrpContNo());
			tLCInsureAccTraceSchema.setGrpPolNo(tLCInsureAccClassSet.get(i)
					.getGrpPolNo());
			tLCInsureAccTraceSchema.setContNo(tLCInsureAccClassSet.get(i)
					.getContNo());
			tLCInsureAccTraceSchema.setPolNo(tLCInsureAccClassSet.get(i)
					.getPolNo());

			tLCInsureAccTraceSchema.setInsuAccNo(tLCInsureAccClassSet.get(i)
					.getInsuAccNo());
			tLCInsureAccTraceSchema.setRiskCode(tLCInsureAccClassSet.get(i)
					.getRiskCode());
			tLCInsureAccTraceSchema.setPayPlanCode(tLCInsureAccClassSet.get(i)
					.getPayPlanCode());
			tLCInsureAccTraceSchema.setOtherNo(tLCInsureAccClassSet.get(i)
					.getOtherNo());
			tLCInsureAccTraceSchema.setOtherType(tLCInsureAccClassSet.get(i)
					.getOtherType());
			tLCInsureAccTraceSchema.setAccAscription(tLCInsureAccClassSet
					.get(i).getAccAscription());

			tLCInsureAccTraceSchema.setUnitCount(tLCInsureAccClassSet.get(i)
					.getUnitCount());
			tLCInsureAccTraceSchema.setMoneyType("BF"); // 录单时产生的帐户
			tLCInsureAccTraceSchema.setMoney(tLCInsureAccClassSet.get(i)
					.getSumPay());
			tLCInsureAccTraceSchema.setState(tLCInsureAccClassSet.get(i)
					.getState());
			tLCInsureAccTraceSchema.setManageCom(tLCInsureAccClassSet.get(i)
					.getManageCom());
			tLCInsureAccTraceSchema.setOperator(tLCInsureAccClassSet.get(i)
					.getOperator());
			tLCInsureAccTraceSchema.setMakeDate(tLCInsureAccClassSet.get(i)
					.getMakeDate());
			tLCInsureAccTraceSchema.setMakeTime(tLCInsureAccClassSet.get(i)
					.getMakeTime());
			tLCInsureAccTraceSchema.setModifyDate(tLCInsureAccClassSet.get(i)
					.getModifyDate());
			tLCInsureAccTraceSchema.setModifyTime(tLCInsureAccClassSet.get(i)
					.getModifyTime());
			tLCInsureAccTraceSchema.setPayDate(tLCPolSchema.getCValiDate()); // 交费日期为保单对应生效日
			/*------------------生成流水号---------start--------*/
			String tSerielNo = PubFun1.CreateMaxNo("SERIALNO", 12);
			tLCInsureAccTraceSchema.setSerialNo(tSerielNo);
			/*------------------生成流水号---------end--------*/

			/*------------------查询管理费代码---------start--------*/
			tLMRiskFeeSchema = new LMRiskFeeSchema();
			tLMRiskFeeSchema = this.queryLMRiskFee(tLCInsureAccTraceSchema);
			if (tLMRiskFeeSchema == null) {
				return null;
			}
			tLCInsureAccTraceSchema.setFeeCode(tLMRiskFeeSchema.getFeeCode());
			/*------------------查询管理费代码---------end--------*/
			tLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
		}
		return tLCInsureAccTraceSet;
	}

	/**
	 * 创建管理费结构
	 * 
	 * @param tLCPolSchema
	 *            LCPolSchema
	 * @param tLCGrpFeeSet
	 *            LCGrpFeeSet
	 * @return VData
	 */
	public VData getNewManageFeeStru(LCPolSchema tLCPolSchema,
			LCPremToAccSet tLCPremToAccSet, LCInsureAccSet tLCInsureAccSet) {
		logger.debug("创建管理费结构");
		VData tData = new VData();
		LCInsureAccFeeSet tLCInsureAccFeeSet = new LCInsureAccFeeSet();
		LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
		LCPremToAccSchema tLCPremToAccSchema = null;

		for (int t = 1; t <= tLCPremToAccSet.size(); t++) {
			tLCPremToAccSchema = tLCPremToAccSet.get(t);

			LCInsureAccSchema tLCInsureAccSchema = null;
			for (int i = 1; i <= tLCInsureAccSet.size(); i++) {
				if (tLCInsureAccSet.get(i).getInsuAccNo().equals(
						tLCPremToAccSchema.getInsuAccNo())) {
					tLCInsureAccSchema = tLCInsureAccSet.get(i);
					break;
				}
			}
			if (tLCInsureAccSchema == null) {
				logger.debug("没有找对对应的账户");
				continue;
				// return null;
			}
			// 创建管理费分类表

			LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema = new LCInsureAccClassFeeSchema();
			tLCInsureAccClassFeeSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
			tLCInsureAccClassFeeSchema.setPolNo(tLCPolSchema.getPolNo());
			tLCInsureAccClassFeeSchema.setInsuAccNo(tLCPremToAccSchema
					.getInsuAccNo());
			tLCInsureAccClassFeeSchema.setPayPlanCode(tLCPremToAccSchema
					.getPayPlanCode());
			// add feecode
			logger.debug(tLCPremToAccSchema.getPayPlanCode());
			LMRiskFeeDB tLMRiskFeeDB = new LMRiskFeeDB();
			LMRiskFeeSet tLMRiskFeeSet = new LMRiskFeeSet();
			String tcode = tLCPremToAccSchema.getPayPlanCode();
			// if(tcode.equals("162102") || tcode.equals("162103"))
			// {
			// tcode="162101";
			// }
			// if(tcode.equals("188102") || tcode.equals("188103"))
			// {
			// tcode="188101";
			// }
			LMDutyPayDB tLMDutyPayDB = new LMDutyPayDB();
			LMDutyPaySet tLMDutyPaySet = new LMDutyPaySet();
			tLMDutyPayDB.setPayPlanCode(tcode);
			tLMDutyPaySet = tLMDutyPayDB.query();
			String tttInsuAccNo = tLMDutyPaySet.get(1).getDefaultVal();

			// 改造完毕

			String sql = "select * from lmriskfee where payplancode='" + "?a18?"
					+ "' and feetakeplace='01'";
			SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
			sqlbv14.sql(sql);
			sqlbv14.put("a18", tcode);
			tLMRiskFeeSet = tLMRiskFeeDB.executeQuery(sqlbv14);
			if (tLMRiskFeeSet.size() == 0) {
				CError.buildErr(this, "管理费定义数据不全，请检查产品定义！");
			}
			String feecode = tLMRiskFeeSet.get(1).getFeeCode();
			// 188险种特殊，初始值是LCGRPPOL INITRATE

			// 如果是188险种存放的是分单号。
			tLCInsureAccClassFeeSchema.setFeeCode(feecode);

			tLCInsureAccClassFeeSchema.setAccType(tLCInsureAccSchema
					.getAccType());
			tLCInsureAccClassFeeSchema.setContNo(tLCPolSchema.getContNo());
			tLCInsureAccClassFeeSchema
					.setManageCom(tLCPolSchema.getManageCom());
			tLCInsureAccClassFeeSchema
					.setGrpContNo(tLCPolSchema.getGrpContNo());
			tLCInsureAccClassFeeSchema.setOtherType("1"); // 个人保单号
			tLCInsureAccClassFeeSchema.setOtherNo(tLCPolSchema.getPolNo());
			tLCInsureAccClassFeeSchema.setRiskCode(tLCPolSchema.getRiskCode());
			tLCInsureAccClassFeeSchema
					.setInsuredNo(tLCPolSchema.getInsuredNo());
			tLCInsureAccClassFeeSchema.setAppntNo(tLCPolSchema.getAppntNo());
			tLCInsureAccClassFeeSchema.setAccComputeFlag(tLCInsureAccSchema
					.getAccComputeFlag());

			tLCInsureAccClassFeeSchema.setAccFoundDate(tLCPolSchema
					.getCValiDate());
			tLCInsureAccClassFeeSchema.setBalaDate(tLCPolSchema.getCValiDate());
			tLCInsureAccClassFeeSchema.setBalaTime(PubFun.getCurrentTime());
			tLCInsureAccClassFeeSchema.setAccFoundTime(PubFun.getCurrentTime());
			tLCInsureAccClassFeeSchema.setFee(0);
			tLCInsureAccClassFeeSchema.setFeeRate(0);
			tLCInsureAccClassFeeSchema.setFeeUnit(0);
			tLCInsureAccClassFeeSchema.setMakeDate(PubFun.getCurrentDate());
			tLCInsureAccClassFeeSchema.setOperator(tLCPolSchema.getOperator());
			tLCInsureAccClassFeeSchema.setMakeDate(CurrentDate);
			tLCInsureAccClassFeeSchema.setMakeTime(CurrentTime);
			tLCInsureAccClassFeeSchema.setModifyDate(CurrentDate);
			tLCInsureAccClassFeeSchema.setModifyTime(CurrentTime);
			// 暂时假设未归属
			tLCInsureAccClassFeeSchema.setAccAscription("0");
			tLCInsureAccClassFeeSet.add(tLCInsureAccClassFeeSchema);

			// 管理费表
			// 查找看是否已经存在
			boolean has = false;

			for (int j = 1; j <= tLCInsureAccFeeSet.size(); j++) {
				if (tLCInsureAccFeeSet.get(j).getInsuAccNo().equals(
						tLCPremToAccSchema.getInsuAccNo())) {
					has = true;
					break;
				}

			}

			if (has) {
				continue;
			}

			LCInsureAccFeeSchema tLCInsureAccFeeSchema = new LCInsureAccFeeSchema();
			tLCInsureAccFeeSchema.setAppntNo(tLCPolSchema.getAppntNo());

			tLCInsureAccFeeSchema.setInsuAccNo(tLCPremToAccSchema
					.getInsuAccNo());
			tLCInsureAccFeeSchema.setPolNo(tLCPolSchema.getPolNo());
			tLCInsureAccFeeSchema.setRiskCode(tLCPolSchema.getRiskCode());
			tLCInsureAccFeeSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
			tLCInsureAccFeeSchema.setContNo(tLCPolSchema.getContNo());
			tLCInsureAccFeeSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
			tLCInsureAccFeeSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());

			tLCInsureAccFeeSchema.setPrtNo(tLCPolSchema.getPrtNo());
			// tLCPolSchema
			tLCInsureAccFeeSchema.setAccFoundDate(tLCPolSchema.getCValiDate());
			tLCInsureAccFeeSchema.setBalaDate(tLCPolSchema.getCValiDate());
			tLCInsureAccFeeSchema.setAccType(tLCInsureAccSchema.getAccType());
			tLCInsureAccFeeSchema.setAccComputeFlag(tLCInsureAccSchema
					.getAccComputeFlag());
			tLCInsureAccFeeSchema.setFundCompanyCode(tLCInsureAccSchema
					.getFundCompanyCode());
			tLCInsureAccFeeSchema.setOwner(tLCInsureAccSchema.getOwner());
			tLCInsureAccFeeSchema.setInvestType(tLCInsureAccSchema
					.getInvestType());
			tLCInsureAccFeeSchema.setMakeDate(CurrentDate);
			tLCInsureAccFeeSchema.setMakeTime(CurrentTime);
			tLCInsureAccFeeSchema.setModifyDate(CurrentDate);
			tLCInsureAccFeeSchema.setModifyTime(CurrentTime);
			tLCInsureAccFeeSchema.setOperator(tLCPolSchema.getOperator());
			tLCInsureAccFeeSet.add(tLCInsureAccFeeSchema);
		}
		tData.add(tLCInsureAccClassFeeSet);
		tData.add(tLCInsureAccFeeSet);
		return tData;

	}
	public VData getManageFeeStru(LCPolSchema tLCPolSchema,
			LCPremToAccSet tLCPremToAccSet, LCInsureAccSet tLCInsureAccSet,String cTakePlace) {
		logger.debug("创建管理费结构");
		VData tData = new VData();
		LCInsureAccFeeSet tLCInsureAccFeeSet = new LCInsureAccFeeSet();
		LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
		LCPremToAccSchema tLCPremToAccSchema = null;

		for (int t = 1; t <= tLCPremToAccSet.size(); t++) {
			tLCPremToAccSchema = tLCPremToAccSet.get(t);

			LCInsureAccSchema tLCInsureAccSchema = null;
			for (int i = 1; i <= tLCInsureAccSet.size(); i++) {
				if (tLCInsureAccSet.get(i).getInsuAccNo().equals(
						tLCPremToAccSchema.getInsuAccNo())) {
					tLCInsureAccSchema = tLCInsureAccSet.get(i);
					break;
				}
			}
			if (tLCInsureAccSchema == null) {
				logger.debug("没有找对对应的账户");
				continue;
				// return null;
			}

			/*------------------查询管理费代码---------start--------*/
			LMRiskFeeSet tLMRiskFeeSet = new LMRiskFeeSet();
			LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
			tLCInsureAccTraceSchema.setInsuAccNo(tLCPremToAccSchema
					.getInsuAccNo());
			tLCInsureAccTraceSchema.setPayPlanCode(tLCPremToAccSchema
					.getPayPlanCode());

			tLMRiskFeeSet = this.queryLMRiskFeeSet(tLCInsureAccTraceSchema, cTakePlace);
			if (tLMRiskFeeSet == null) {
				return null;
			}

			for (int i = 1; i <= tLMRiskFeeSet.size(); i++) {
				LMRiskFeeSchema tLMRiskFeeSchema = new LMRiskFeeSchema();
				tLMRiskFeeSchema = tLMRiskFeeSet.get(i);
				// 创建管理费分类表
				LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema = new LCInsureAccClassFeeSchema();
				tLCInsureAccClassFeeSchema.setGrpPolNo(tLCPolSchema
						.getGrpPolNo());
				tLCInsureAccClassFeeSchema.setPolNo(tLCPolSchema.getPolNo());
				tLCInsureAccClassFeeSchema.setInsuAccNo(tLCPremToAccSchema
						.getInsuAccNo());
				tLCInsureAccClassFeeSchema.setFeeCode(tLMRiskFeeSchema
						.getFeeCode());
				tLCInsureAccClassFeeSchema.setPayPlanCode(tLCPremToAccSchema
						.getPayPlanCode());
				// tLCInsureAccClassFeeSchema.setAcc
				tLCInsureAccClassFeeSchema.setAccType(tLCInsureAccSchema
						.getAccType());
				tLCInsureAccClassFeeSchema.setContNo(tLCPolSchema.getContNo());
				tLCInsureAccClassFeeSchema.setManageCom(tLCPolSchema
						.getManageCom());
				tLCInsureAccClassFeeSchema.setGrpContNo(tLCPolSchema
						.getGrpContNo());
				tLCInsureAccClassFeeSchema.setOtherType("1"); // 个人保单号
				tLCInsureAccClassFeeSchema.setOtherNo(tLCPolSchema.getPolNo());
				tLCInsureAccClassFeeSchema.setRiskCode(tLCPolSchema
						.getRiskCode());
				tLCInsureAccClassFeeSchema.setInsuredNo(tLCPolSchema
						.getInsuredNo());
				tLCInsureAccClassFeeSchema
						.setAppntNo(tLCPolSchema.getAppntNo());
				tLCInsureAccClassFeeSchema.setAccComputeFlag(tLCInsureAccSchema
						.getAccComputeFlag());
				// tLCInsureAccClassFeeSchema.setAccFoundDate(null);
				tLCInsureAccClassFeeSchema.setBalaDate(tLCPolSchema
						.getCValiDate());
				// tLCInsureAccClassFeeSchema.setBalaTime();
				tLCInsureAccClassFeeSchema.setFee(0);
				tLCInsureAccClassFeeSchema.setFeeRate(0);
				tLCInsureAccClassFeeSchema.setFeeUnit(0);
				tLCInsureAccClassFeeSchema.setMakeDate(PubFun.getCurrentDate());
				tLCInsureAccClassFeeSchema.setOperator(tLCPolSchema
						.getOperator());
				tLCInsureAccClassFeeSchema.setMakeDate(CurrentDate);
				tLCInsureAccClassFeeSchema.setMakeTime(CurrentTime);
				tLCInsureAccClassFeeSchema.setModifyDate(CurrentDate);
				tLCInsureAccClassFeeSchema.setModifyTime(CurrentTime);
				tLCInsureAccClassFeeSchema.setCurrency(tLCPolSchema.getCurrency());
				// 个险设置归属标记
                                if (this.getPubFlag(tLCPremToAccSchema.getPayPlanCode()).equals("Y")
                                    && this.getPayaimClass(tLCPremToAccSchema.getPayPlanCode()).equals("2")) {
                                    tLCInsureAccClassFeeSchema.setAccAscription("0");
                                } else {
                                    tLCInsureAccClassFeeSchema.setAccAscription("1");
                                }

				tLCInsureAccClassFeeSet.add(tLCInsureAccClassFeeSchema);
			}
			// 管理费表
			// 查找看是否已经存在
			boolean has = false;

			for (int j = 1; j <= tLCInsureAccFeeSet.size(); j++) {
				if (tLCInsureAccFeeSet.get(j).getInsuAccNo().equals(
						tLCPremToAccSchema.getInsuAccNo())) {
					has = true;
					break;
				}

			}

			if (has) {
				continue;
			}
			// 还没有，则创建
			LCInsureAccFeeSchema tLCInsureAccFeeSchema = new LCInsureAccFeeSchema();
			tLCInsureAccFeeSchema.setAppntNo(tLCPolSchema.getAppntNo());
			// tLCInsureAccFeeSchema.setAppntName(tLCPolSchema.getAppntName());
			tLCInsureAccFeeSchema.setInsuAccNo(tLCPremToAccSchema
					.getInsuAccNo());
			tLCInsureAccFeeSchema.setPolNo(tLCPolSchema.getPolNo());
			tLCInsureAccFeeSchema.setRiskCode(tLCPolSchema.getRiskCode());
			tLCInsureAccFeeSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
			tLCInsureAccFeeSchema.setContNo(tLCPolSchema.getContNo());
			tLCInsureAccFeeSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
			tLCInsureAccFeeSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
			// tLCInsureAccFeeSchema.setMoney(0);
			tLCInsureAccFeeSchema.setPrtNo(tLCPolSchema.getPrtNo());
			// tLCInsureAccFeeSchema.setUnitCount(0);
			tLCInsureAccFeeSchema.setAccType(tLCInsureAccSchema.getAccType());
			tLCInsureAccFeeSchema.setAccComputeFlag(tLCInsureAccSchema
					.getAccComputeFlag());
			tLCInsureAccFeeSchema.setFundCompanyCode(tLCInsureAccSchema
					.getFundCompanyCode());
			tLCInsureAccFeeSchema.setOwner(tLCInsureAccSchema.getOwner());
			tLCInsureAccFeeSchema.setInvestType(tLCInsureAccSchema
					.getInvestType());
			tLCInsureAccFeeSchema.setBalaDate(tLCPolSchema.getCValiDate());
			tLCInsureAccFeeSchema.setMakeDate(CurrentDate);
			tLCInsureAccFeeSchema.setMakeTime(CurrentTime);
			tLCInsureAccFeeSchema.setModifyDate(CurrentDate);
			tLCInsureAccFeeSchema.setModifyTime(CurrentTime);
			tLCInsureAccFeeSchema.setOperator(tLCPolSchema.getOperator());
			tLCInsureAccFeeSet.add(tLCInsureAccFeeSchema);
		}
		logger.debug("tLCInsureAccClassFeeSet=="
				+ tLCInsureAccClassFeeSet.size());
		tData.add(tLCInsureAccClassFeeSet);
		logger.debug("tLCInsureAccFeeSet==" + tLCInsureAccFeeSet.size());
		tData.add(tLCInsureAccFeeSet);
		return tData;
	}

	/**
	 * 创建管理费结构
	 * 
	 * @param tLCPolSchema
	 *            LCPolSchema
	 * @param tLCGrpFeeSet
	 *            LCGrpFeeSet
	 * @return VData
	 */
	public VData getManageFeeStru(LCPolSchema tLCPolSchema,
			LCPremToAccSet tLCPremToAccSet, LCInsureAccSet tLCInsureAccSet) {

		VData tData = new VData();
		LCInsureAccFeeSet tLCInsureAccFeeSet = new LCInsureAccFeeSet();
		LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
		LCPremToAccSchema tLCPremToAccSchema = null;

		// 缴费与账户无关，无保险账户管理费分类表 modify by duanyh 2008-07-15
		if (tLCPremToAccSet == null) {
			return null;
		}

		for (int t = 1; t <= tLCPremToAccSet.size(); t++) {
			tLCPremToAccSchema = tLCPremToAccSet.get(t);

			LCInsureAccSchema tLCInsureAccSchema = null;
			for (int i = 1; i <= tLCInsureAccSet.size(); i++) {
				if (tLCInsureAccSet.get(i).getInsuAccNo().equals(
						tLCPremToAccSchema.getInsuAccNo())) {
					tLCInsureAccSchema = tLCInsureAccSet.get(i);
					break;
				}
			}
			if (tLCInsureAccSchema == null) {
				// logger.debug("没有找对对应的账户");
				continue;
				// return null;
			}
			// 创建管理费分类表
			LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema = new LCInsureAccClassFeeSchema();
			tLCInsureAccClassFeeSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
			tLCInsureAccClassFeeSchema.setPolNo(tLCPolSchema.getPolNo());
			tLCInsureAccClassFeeSchema.setInsuAccNo(tLCPremToAccSchema
					.getInsuAccNo());
			tLCInsureAccClassFeeSchema.setPayPlanCode(tLCPremToAccSchema
					.getPayPlanCode());
			// tLCInsureAccClassFeeSchema.setAcc
			tLCInsureAccClassFeeSchema.setAccType(tLCInsureAccSchema
					.getAccType());
			tLCInsureAccClassFeeSchema.setContNo(tLCPolSchema.getContNo());
			tLCInsureAccClassFeeSchema
					.setManageCom(tLCPolSchema.getManageCom());
			tLCInsureAccClassFeeSchema
					.setGrpContNo(tLCPolSchema.getGrpContNo());
			tLCInsureAccClassFeeSchema.setOtherType("1"); // 个人保单号
			tLCInsureAccClassFeeSchema.setOtherNo(tLCPolSchema.getPolNo());
			tLCInsureAccClassFeeSchema.setRiskCode(tLCPolSchema.getRiskCode());
			tLCInsureAccClassFeeSchema
					.setInsuredNo(tLCPolSchema.getInsuredNo());
			tLCInsureAccClassFeeSchema.setAppntNo(tLCPolSchema.getAppntNo());
			tLCInsureAccClassFeeSchema.setAccComputeFlag(tLCInsureAccSchema
					.getAccComputeFlag());
			// tLCInsureAccClassFeeSchema.setAccFoundDate(null);
			tLCInsureAccClassFeeSchema.setBalaDate(tLCPolSchema.getCValiDate());
			// tLCInsureAccClassFeeSchema.setBalaTime();
			tLCInsureAccClassFeeSchema.setFee(0);
			tLCInsureAccClassFeeSchema.setFeeRate(0);
			tLCInsureAccClassFeeSchema.setFeeUnit(0);
			tLCInsureAccClassFeeSchema.setMakeDate(PubFun.getCurrentDate());
			tLCInsureAccClassFeeSchema.setOperator(tLCPolSchema.getOperator());
			tLCInsureAccClassFeeSchema.setMakeDate(CurrentDate);
			tLCInsureAccClassFeeSchema.setMakeTime(CurrentTime);
			tLCInsureAccClassFeeSchema.setModifyDate(CurrentDate);
			tLCInsureAccClassFeeSchema.setModifyTime(CurrentTime);
			// 暂时假设未归属
			//tongmeng 2009-10-21 modify
			//设置为1,已归属
			tLCInsureAccClassFeeSchema.setAccAscription("1");
			//tLCInsureAccClassFeeSchema.setAccAscription("0");
			//tongmeng 2010-11-08 modify
			//多币种处理
			//tongmeng 2010-11-30 modify
			tLCInsureAccClassFeeSchema.setCurrency(tLCInsureAccSchema.getCurrency());
			// 默认未归属
			/*------------------查询管理费代码---------start--------*/

			LMRiskFeeSchema tLMRiskFeeSchema = new LMRiskFeeSchema();
			LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
			tLCInsureAccTraceSchema.setInsuAccNo(tLCPremToAccSchema
					.getInsuAccNo());
			tLCInsureAccTraceSchema.setPayPlanCode(tLCPremToAccSchema
					.getPayPlanCode());
			tLCInsureAccTraceSchema.setRiskCode(tLCPolSchema.getRiskCode());
			tLMRiskFeeSchema = this.queryLMRiskFee(tLCInsureAccTraceSchema);
			if (tLMRiskFeeSchema == null) {
				return null;
			}
			//tongmeng 2010-11-08 modify
			//增加多币种处理
			tLCInsureAccTraceSchema.setCurrency(tLCInsureAccSchema.getCurrency());
			tLCInsureAccClassFeeSchema
					.setFeeCode(tLMRiskFeeSchema.getFeeCode());
			/*------------------查询管理费代码---------end--------*/
			tLCInsureAccClassFeeSet.add(tLCInsureAccClassFeeSchema);
			// 管理费表
			// 查找看是否已经存在
			boolean has = false;

			for (int j = 1; j <= tLCInsureAccFeeSet.size(); j++) {
				if (tLCInsureAccFeeSet.get(j).getInsuAccNo().equals(
						tLCPremToAccSchema.getInsuAccNo())) {
					has = true;
					break;
				}

			}

			if (has) {
				continue;
			}
			// 还没有，则创建
			LCInsureAccFeeSchema tLCInsureAccFeeSchema = new LCInsureAccFeeSchema();
			tLCInsureAccFeeSchema.setAppntNo(tLCPolSchema.getAppntNo());
			// tLCInsureAccFeeSchema.setAppntName(tLCPolSchema.getAppntName());
			tLCInsureAccFeeSchema.setInsuAccNo(tLCPremToAccSchema
					.getInsuAccNo());
			tLCInsureAccFeeSchema.setPolNo(tLCPolSchema.getPolNo());
			tLCInsureAccFeeSchema.setRiskCode(tLCPolSchema.getRiskCode());
			tLCInsureAccFeeSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
			tLCInsureAccFeeSchema.setContNo(tLCPolSchema.getContNo());
			tLCInsureAccFeeSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
			tLCInsureAccFeeSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
			// tLCInsureAccFeeSchema.setMoney(0);
			tLCInsureAccFeeSchema.setPrtNo(tLCPolSchema.getPrtNo());
			// tLCInsureAccFeeSchema.setUnitCount(0);
			tLCInsureAccFeeSchema.setAccType(tLCInsureAccSchema.getAccType());
			tLCInsureAccFeeSchema.setAccComputeFlag(tLCInsureAccSchema
					.getAccComputeFlag());
			tLCInsureAccFeeSchema.setFundCompanyCode(tLCInsureAccSchema
					.getFundCompanyCode());
			tLCInsureAccFeeSchema.setOwner(tLCInsureAccSchema.getOwner());
			tLCInsureAccFeeSchema.setInvestType(tLCInsureAccSchema
					.getInvestType());
			tLCInsureAccFeeSchema.setBalaDate(tLCPolSchema.getCValiDate());
			tLCInsureAccFeeSchema.setMakeDate(CurrentDate);
			tLCInsureAccFeeSchema.setMakeTime(CurrentTime);
			tLCInsureAccFeeSchema.setModifyDate(CurrentDate);
			tLCInsureAccFeeSchema.setModifyTime(CurrentTime);
			tLCInsureAccFeeSchema.setOperator(tLCPolSchema.getOperator());
			//tongmeng 2010-11-08 modify
			//增加多币种处理
			tLCInsureAccFeeSchema.setCurrency(tLCInsureAccSchema.getCurrency());
			tLCInsureAccFeeSet.add(tLCInsureAccFeeSchema);
		}
		tData.add(tLCInsureAccClassFeeSet);
		tData.add(tLCInsureAccFeeSet);
		return tData;
	}

	/**
	 * 创建保险账户分类表
	 * 
	 * @param tPolSchema
	 *            LCPolSchema
	 * @param tLCPremToAccSet
	 *            LCPremToAccSet
	 * @return LCInsureAccClassSet
	 */
	private LCInsureAccClassSet getLCInsureAccClassForBat(
			LCPolSchema tPolSchema, LCPremToAccSet tLCPremToAccSet) {
		LCInsureAccClassSet tSet = new LCInsureAccClassSet();
		LCPremToAccSchema tPremAccSchema = null;
		String nowDate = PubFun.getCurrentDate();
		String nowTime = PubFun.getCurrentTime();
		LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();
		String tAccNo = "";
		for (int i = 1; i <= tLCPremToAccSet.size(); i++) {
			LCInsureAccClassSchema tSchema = new LCInsureAccClassSchema();
			tPremAccSchema = tLCPremToAccSet.get(i);
			// 不校验了
			//tongmeng 2010-11-30 modify
			//if (!tPremAccSchema.getInsuAccNo().equals(tAccNo)) 
			{ // ?
				tAccNo = tPremAccSchema.getInsuAccNo();
				tLMRiskInsuAccDB.setInsuAccNo(tAccNo);
				if (!tLMRiskInsuAccDB.getInfo()) {
					logger.debug("getLCInsureAccClassForBat查询险种账户描述表出错");
					CError.buildErr(this, "查询险种账户描述表出错");
					return null;
				}

			}
			tSchema.setAccType(tLMRiskInsuAccDB.getAccType());
			tSchema.setState("0");
			tSchema.setAccComputeFlag(tLMRiskInsuAccDB.getAccComputeFlag());

			tSchema.setPolNo(tPremAccSchema.getPolNo());
			tSchema.setInsuAccNo(tPremAccSchema.getInsuAccNo());
			tSchema.setPayPlanCode(tPremAccSchema.getPayPlanCode());
			tSchema.setContNo(tPolSchema.getContNo());
			// tSchema.setAppntName(tPolSchema.getAppntName());
			tSchema.setInsuredNo(tPolSchema.getInsuredNo());
			tSchema.setGrpContNo(tPolSchema.getGrpContNo());
			tSchema.setGrpPolNo(tPolSchema.getGrpPolNo());
			tSchema.setFrozenMoney(0);
			tSchema.setBalaDate(tPolSchema.getCValiDate());
			// tSchema.setBalaTime(nowTime);
			tSchema.setBalaTime("00:00:00");
			tSchema.setInsuAccBala(0);
			tSchema.setInsuAccGetMoney(0);
			tSchema.setMakeDate(nowDate);
			tSchema.setMakeTime(nowTime);
			tSchema.setManageCom(tPolSchema.getManageCom());
			tSchema.setModifyDate(nowDate);
			tSchema.setModifyTime(nowTime);
			tSchema.setOperator(this.mGlobalInput.Operator);
			tSchema.setOtherNo(tPolSchema.getPolNo());
			tSchema.setOtherType("1");
			tSchema.setState("0");
			tSchema.setSumPay(0);
			tSchema.setSumPaym(0);
			// tSchema.setState();
			// 新加字段
			tSchema.setGrpContNo(tPolSchema.getGrpContNo());
			tSchema.setAppntNo(tPolSchema.getAppntNo());
			tSchema.setUnitCount(0);
			tSchema.setRiskCode(tPolSchema.getRiskCode());
			//添加多币种的处理 2009-10-19  begin
			 tSchema.setCurrency(tPolSchema.getCurrency());	
			// 暂时假设未归属-- wujs
			/** ******2005-12-14 常亮修改 只把企业交费定为归属账户****** */
			//tongmeng 2009-10-21 modify
			//MS暂时没有归属规则,所有都设置为已归属
			tSchema.setAccAscription("1");
			//tongmeng 2010-11-08 modify
			//多币种处理
			//tongmeng 2010-11-30
			if(tLMRiskInsuAccDB.getCurrency()!=null&&!tLMRiskInsuAccDB.getCurrency().equals(""))
			{
				tSchema.setCurrency(tLMRiskInsuAccDB.getCurrency());
			}
			else
			{
				tSchema.setCurrency(tPolSchema.getCurrency());
			}
			/*
			String payPlanCode = tSchema.getPayPlanCode();
			if (this.getPubFlag(payPlanCode).equals("Y")
					&& this.getPayaimClass(payPlanCode).equals("2")) {
				tSchema.setAccAscription("0");
			} else {
				tSchema.setAccAscription("1");
			}*/
			tSet.add(tSchema);
		}
		return tSet;
	}
	/**
	 * 创建保险账户分类表
	 *
	 * @param tPolSchema
	 *            LCPolSchema
	 * @param tLMRiskSchema
	 *            tLMRiskSchema
	 * @return LCInsureAccClassSet Add by niuzj,2007-05-30
	 *         专门用来处理312001这个险种的累计生息帐户
	 */
	public LCInsureAccClassSet getLCInsureAccClassForBat(
			LCPolSchema tPolSchema, LMRiskSchema tLMRiskSchema) {
		String nowDate = PubFun.getCurrentDate();
		String nowTime = PubFun.getCurrentTime();

		LCInsureAccClassSet tSet = new LCInsureAccClassSet();

		String tSql1 = "select b.insuaccno,b.acctype,b.acccomputeflag,b.currency "
				+ "from lmrisktoacc a,lmriskinsuacc b "
				+ "where a.insuaccno=b.insuaccno " + "and a.riskcode='"
				+ "?a19?" + "' ";
		SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
		sqlbv15.sql(tSql1);
		sqlbv15.put("a19", tLMRiskSchema.getRiskCode());
		ExeSQL exesql1 = new ExeSQL();
		SSRS ssrs1 = exesql1.execSQL(sqlbv15);
		if (ssrs1 == null || ssrs1.getMaxRow() == 0) {
			this.mErrors.addOneError("查询险种账户描述表出错");
			return null;
		}
		else {
			String tInsureAccNo = "";
			String tAccType = "001";
			String tAccFlag = "2";
			for (int i = 1; i <= ssrs1.getMaxRow(); i++) {
				tInsureAccNo = ssrs1.GetText(i, 1);
				tAccType = ssrs1.GetText(i, 2);
				tAccFlag = ssrs1.GetText(i, 3);
				String tCurrency = ssrs1.GetText(i,4);
				String tSql2 = "select b.payplancode from lmriskduty a,lmdutypayrela b "
						+ "where a.dutycode=b.dutycode "
						+ "and a.riskcode='"
						+ "?a20?" + "' ";
				SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
				sqlbv16.sql(tSql2);
				sqlbv16.put("a20", tLMRiskSchema.getRiskCode());
				ExeSQL exesql2 = new ExeSQL();
				SSRS ssrs2 = exesql2.execSQL(sqlbv16);
				if (ssrs2 == null || ssrs2.getMaxRow() == 0) {
					this.mErrors.addOneError("查询险种账户描述表payplancode出错");
					return null;
				}
				else {
					String tPayPlanCode = "000000";
					for (int j = 1; j <= ssrs2.getMaxRow(); j++) {
						tPayPlanCode = ssrs2.GetText(j, 1);
						LCInsureAccClassSchema tSchema = new LCInsureAccClassSchema();
						tSchema.setAccType(tAccType);
						tSchema.setState("0");
						tSchema.setAccComputeFlag(tAccFlag);
						tSchema.setPolNo(tPolSchema.getPolNo());
						tSchema.setInsuAccNo(tInsureAccNo);
						tSchema.setPayPlanCode(tPayPlanCode);
						tSchema.setContNo(tPolSchema.getContNo());
						tSchema.setInsuredNo(tPolSchema.getInsuredNo());
						tSchema.setGrpContNo(tPolSchema.getGrpContNo());
						tSchema.setGrpPolNo(tPolSchema.getGrpPolNo());
						tSchema.setFrozenMoney(0);
						tSchema.setBalaDate(tPolSchema.getCValiDate());
						tSchema.setBalaTime(nowTime);
						tSchema.setInsuAccBala(0);
						tSchema.setInsuAccGetMoney(0);
						tSchema.setMakeDate(nowDate);
						tSchema.setMakeTime(nowTime);
						tSchema.setManageCom(tPolSchema.getManageCom());
						tSchema.setModifyDate(nowDate);
						tSchema.setModifyTime(nowTime);
						tSchema.setOperator(tPolSchema.getOperator());
						tSchema.setOtherNo(tPolSchema.getPolNo());
						tSchema.setOtherType("1");
						tSchema.setState("0");
						tSchema.setSumPay(0);
						tSchema.setSumPaym(0);
						tSchema.setGrpContNo(tPolSchema.getGrpContNo());
						tSchema.setAppntNo(tPolSchema.getAppntNo());
						tSchema.setUnitCount(0);
						tSchema.setRiskCode(tPolSchema.getRiskCode());
						tSchema.setAccAscription("1");
						//tongmeng 2010-11-30 modify
						if(tCurrency!=null&&!tCurrency.equals(""))
						{
							tSchema.setCurrency(tCurrency);
						}
						else
						{
							tSchema.setCurrency(tPolSchema.getCurrency());
						}

						tSet.add(tSchema);
					}
				}
			}
		}
		return tSet;
	}

	/**
	 * get pubFlg by payPlanCode
	 * 
	 * @param payPlanCode
	 *            String
	 * @return String
	 */
	private String getPubFlag(String payPlanCode) {
		String strSQL = "select PubFlag from lmdutypay where PayPlanCode='"
				+ "?a21?" + "'";
		SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
		sqlbv17.sql(strSQL);
		sqlbv17.put("a21",payPlanCode);
		ExeSQL exeSQL = new ExeSQL();
		String pubFlag = exeSQL.getOneValue(sqlbv17);
		if (pubFlag == null) {
			return "";
		}
		return pubFlag;
	}

	/**
	 * get PayaimClass by payPlanCode
	 * 
	 * @param payPlanCode
	 *            String
	 * @return String
	 */
	private String getPayaimClass(String payPlanCode) {
		String strSQL = "select PayaimClass from lmdutypay where PayPlanCode='"
				+ "?a22?" + "'";
		SQLwithBindVariables sqlbv18=new SQLwithBindVariables();
		sqlbv18.sql(strSQL);
		sqlbv18.put("a22",payPlanCode);
		ExeSQL exeSQL = new ExeSQL();
		String PayaimClass = exeSQL.getOneValue(sqlbv18);
		if (PayaimClass == null) {
			return "";
		}
		return PayaimClass;
	}

	/**
	 * 生成保费项表和客户帐户表的关联表
	 * 
	 * @param PolNo
	 *            保单号
	 * @param AccCreatePos
	 *            生成位置
	 * @param Rate
	 *            费率
	 * @param inLCPolSchema
	 *            传入保单数据
	 * @return LCPremToAccSet 保费项关联表
	 */
	public LCPremToAccSet getPremToAccForBat(String PolNo, String AccCreatePos,
			Double Rate, LCPolSchema inLCPolSchema) {
		logger.debug("PolNo===" + PolNo);
		logger.debug("AccCreatePos===" + AccCreatePos);
		if ((PolNo == null) || (AccCreatePos == null)) {
			// @@错误处理
			logger.debug("getPremToAccForBat传入参数不能为空");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "getPremToAcc";
			tError.errorMessage = "错误原因:传入参数不能为空";
			this.mErrors.addOneError(tError);

			return null;
		}

		String tPolNo = PolNo;
		String tAccCreatePos = AccCreatePos;
		Double tRate = Rate;
		LCPremToAccSet tLCPremToAccSet = new LCPremToAccSet();

		// 1-取出保费项表
		LCPremSet tLCPremSet = new LCPremSet();
		tLCPremSet = queryLCPrem(tPolNo);
		if (tLCPremSet == null) {
			return null;
		}
		// logger.debug("@@@@tLCPremSet.size="+tLCPremSet.size());
		// 2-根据保费项表取出对应的责任缴费描述表
		VData tVData = new VData();
		tVData = getFromLMDutyPay(tLCPremSet);
		if (tVData == null) {
			return null;
		}

		// 3-生成保费项表和客户账户表的关联表
		// logger.debug("@@@@@@ come to createPremToAccForBat");
		tLCPremToAccSet = createPremToAccForBat(tVData, tPolNo, tAccCreatePos,
				tRate, inLCPolSchema);

		return tLCPremToAccSet;
	}

	/**
	 * 生成保费项表和客户账户表的关联表
	 * 
	 * @param tVData
	 *            包含责任交费和保费项集合
	 * @param PolNo
	 *            保单号
	 * @param AccCreatePos
	 *            生成帐户的流程位置标记（承保，交费等）
	 * @param Rate
	 *            提取比率
	 * @param
	 * @return LCPremToAccSet
	 */
	public LCPremToAccSet createPremToAccForBat(VData tVData, String PolNo,
			String AccCreatePos, Double Rate, LCPolSchema inLCPolSchema) {
		if ((tVData == null) || (PolNo == null) || (AccCreatePos == null)) {
			// @@错误处理
			logger.debug("createPremToAccForBat传入参数不能为空");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "createPremToAcc";
			tError.errorMessage = "传入数据不能为空!";
			this.mErrors.addOneError(tError);

			return null;
		}

		LCPremSet tLCPremSet = (LCPremSet) tVData.getObjectByObjectName(
				"LCPremSet", 0);
		LMDutyPaySet tLMDutyPaySet = (LMDutyPaySet) tVData
				.getObjectByObjectName("LMDutyPaySet", 0);

		LCPremSchema tLCPremSchema = new LCPremSchema(); // 保费项表
		LMDutyPaySchema tLMDutyPaySchema = new LMDutyPaySchema(); // 责任交费表
		LMRiskAccPaySchema tLMRiskAccPaySchema = new LMRiskAccPaySchema(); //
		LCPremToAccSet tLCPremToAccSet = new LCPremToAccSet(); // 保费项表和客户帐户表的关联表
		LCPremToAccSchema tLCPremToAccSchema = new LCPremToAccSchema(); // 保费项表和客户帐户表的关联表
		LMRiskAccPaySet tLMRiskAccPaySet = null;
		double tRate = 0;
		// logger.debug("@@@@@tLMDutyPaySet="+tLMDutyPaySet.size());
		for (int i = 1; i <= tLMDutyPaySet.size(); i++) {
			tLMDutyPaySchema = tLMDutyPaySet.get(i);
			tLCPremSchema = tLCPremSet.get(i);
			// logger.debug("tLMDutyPaySchema.getNeedAcc()="+tLMDutyPaySchema.getNeedAcc());

			// 判断是否和帐户关联
			if (tLMDutyPaySchema.getNeedAcc().equals("1")) {
				// 查询险种保险帐户缴费表
				// tLMRiskAccPaySchema = new LMRiskAccPaySchema();
				tLMRiskAccPaySet = queryLMRiskAccPayForBat(tLMDutyPaySchema
						.getPayPlanCode(), inLCPolSchema.getRiskCode());
				if (tLMRiskAccPaySet == null || tLMRiskAccPaySet.size() == 0) {
					logger.debug("查询险种账户缴费表失败");
					CError.buildErr(this, "查询险种账户缴费表失败");
					return null;
				}
				for (int u = 1; u <= tLMRiskAccPaySet.size(); u++) {
					tLMRiskAccPaySchema = tLMRiskAccPaySet.get(u);

					// 判断生成位置标记是否匹配
					if (AccCreatePos.equals(tLMRiskAccPaySchema
							.getAccCreatePos())) {
						// 判断费率是否需要录入
						if (tLMRiskAccPaySchema.getNeedInput().equals("1")) {
							// 如果需要录入:判断传入的费率是否为空
							if (Rate == null) {
								// @@错误处理
								logger.debug("createPremToAccForBat费率需要从界面录入，不能为空");
								CError tError = new CError();
								tError.moduleName = "DealAccount";
								tError.functionName = "createPremToAcc";
								tError.errorMessage = "费率需要从界面录入，不能为空!";
								this.mErrors.addOneError(tError);

								return null;
							}
							tRate = Rate.doubleValue();
						} else { // 取默认值
							tRate = tLMRiskAccPaySchema.getDefaultRate();
						}

						tLCPremToAccSchema = new LCPremToAccSchema();
						// tLCPremToAccSchema.setPolNo(PolNo);
						tLCPremToAccSchema.setPolNo(inLCPolSchema.getPolNo());
						tLCPremToAccSchema.setDutyCode(tLCPremSchema
								.getDutyCode());
						tLCPremToAccSchema.setPayPlanCode(tLCPremSchema
								.getPayPlanCode());
						tLCPremToAccSchema.setInsuAccNo(tLMRiskAccPaySchema
								.getInsuAccNo());
						tLCPremToAccSchema.setRate(tRate);
						tLCPremToAccSchema.setNewFlag(tLMRiskAccPaySchema
								.getAccCreatePos());
						tLCPremToAccSchema.setCalCodeMoney(tLMRiskAccPaySchema
								.getCalCodeMoney());
						tLCPremToAccSchema.setCalCodeUnit(tLMRiskAccPaySchema
								.getCalCodeUnit());
						tLCPremToAccSchema.setCalFlag(tLMRiskAccPaySchema
								.getCalFlag());
						tLCPremToAccSchema
								.setOperator(this.mGlobalInput.Operator);
						tLCPremToAccSchema.setMakeDate(CurrentDate);
						tLCPremToAccSchema.setMakeTime(CurrentTime);
						tLCPremToAccSchema.setModifyDate(CurrentDate);
						tLCPremToAccSchema.setModifyTime(CurrentTime);

						tLCPremToAccSet.add(tLCPremToAccSchema);
					}
				}
			}
		}

		if (tLCPremToAccSet.size() == 0) {
			// @@错误处理
			return null;
		}

		return tLCPremToAccSet;
	}

	/**
	 * 查询险种保险帐户缴费表
	 * 
	 * @param pLMDutyPaySchema
	 * @param pLCPremSchema
	 * @param PolNo
	 * @return LMRiskAccPaySchema
	 */
	public LMRiskAccPaySchema queryLMRiskAccPayForBat(
			LMDutyPaySchema pLMDutyPaySchema, LCPremSchema pLCPremSchema,
			String PolNo, LCPolSchema inLCPolSchema) {
		if ((pLMDutyPaySchema == null) || (pLCPremSchema == null)
				|| (PolNo == null)) {
			// @@错误处理
			logger.debug("queryLMRiskAccPayForBat查询险种账户缴费表失败");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRiskAccPay";
			tError.errorMessage = "传入数据不能为空!";
			this.mErrors.addOneError(tError);

			return null;
		}

		// 查询保单表
		LCPolSchema tLCPolSchema = inLCPolSchema;
		if (tLCPolSchema == null) { // 取默认值

			return null;
		}

		String riskCode = tLCPolSchema.getRiskCode();
		String payPlanCode = pLMDutyPaySchema.getPayPlanCode();

		// 查询险种保险帐户缴费表
		String sqlStr = "select * from LMRiskAccPay where RiskCode='"
				+ "?a23?" + "' and payPlanCode='" + "?a24?" + "'";
		SQLwithBindVariables sqlbv19=new SQLwithBindVariables();
		sqlbv19.sql(sqlStr);
		sqlbv19.put("a23",riskCode);
		sqlbv19.put("a24",payPlanCode);
		LMRiskAccPaySchema tLMRiskAccPaySchema = new LMRiskAccPaySchema();
		LMRiskAccPaySet tLMRiskAccPaySet = new LMRiskAccPaySet();
		LMRiskAccPayDB tLMRiskAccPayDB = tLMRiskAccPaySchema.getDB();
		tLMRiskAccPaySet = tLMRiskAccPayDB.executeQuery(sqlbv19);
		if (tLMRiskAccPayDB.mErrors.needDealError() == true) {
			// @@错误处理
			logger.debug("queryLMRiskAccPayForBat险种保险帐户缴费表查询失败");
			this.mErrors.copyAllErrors(tLMRiskAccPayDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRiskAccPay";
			tError.errorMessage = "险种保险帐户缴费表查询失败!";
			this.mErrors.addOneError(tError);
			tLMRiskAccPaySet.clear();

			return null;
		}
		if (tLMRiskAccPaySet.size() == 0) {
			// @@错误处理
			logger.debug("queryLMRiskAccPayForBat险种保险帐户缴费表没有查询到相关数据");
			this.mErrors.copyAllErrors(tLMRiskAccPayDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRiskAccPay";
			tError.errorMessage = "险种保险帐户缴费表没有查询到相关数据!";
			this.mErrors.addOneError(tError);
			tLMRiskAccPaySet.clear();

			return null;
		}

		return tLMRiskAccPaySet.get(1);
	}

	/**
	 * 生成给付项表和客户帐户表的关联表
	 * 
	 * @param PolNo
	 * @param AccCreatePos
	 * @param Rate
	 * @return LCGetToAccSet
	 */
	public LCGetToAccSet getGetToAccForBat(String PolNo, String AccCreatePos,
			Double Rate, LCPolSchema inLCPolSchema) {
		// 1-取出领取项表
		LCGetSet tLCGetSet = new LCGetSet();
		tLCGetSet = queryLCGet(PolNo);
		if (tLCGetSet == null) {
			return null;
		}

		// 2-根据领取项表取出对应的责任给付描述表
		// LMDutyGetSet tLMDutyGetSet = new LMDutyGetSet();
		VData tVData = new VData();
		tVData = createLMDutyGet(tLCGetSet);
		if (tVData == null) {
			return null;
		}

		// 3-生成给付项表和客户账户表的关联表
		LCGetToAccSet tLCGetToAccSet = new LCGetToAccSet();
		tLCGetToAccSet = createGetToAccForBat(tVData, PolNo, AccCreatePos,
				Rate, inLCPolSchema);

		return tLCGetToAccSet;
	}

	/**
	 * 生成给付项表和客户账户表的关联表
	 * 
	 * @param pVData
	 * @param AccCreatePos
	 * @param Rate
	 * @return LCGetToAccSet
	 */
	public LCGetToAccSet createGetToAccForBat(VData pVData, String PolNo,
			String AccCreatePos, Double Rate, LCPolSchema inLCPolSchema) {
		logger.debug("pVData====" + pVData);
		logger.debug("AccCreatePos====" + AccCreatePos);
		logger.debug("PolNo====" + PolNo);
		if ((pVData == null) || (AccCreatePos == null) || (PolNo == null)) {
			// @@错误处理
			logger.debug("createGetToAccForBat传入数据不能为空");
			logger.debug("createGetToAccForBat");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "createGetToAcc";
			tError.errorMessage = "传入数据不能为空!";
			this.mErrors.addOneError(tError);

			return null;
		}

		LMDutyGetSet tLMDutyGetSet = (LMDutyGetSet) pVData
				.getObjectByObjectName("LMDutyGetSet", 0);
		LCGetSet tLCGetSet = (LCGetSet) pVData.getObjectByObjectName(
				"LCGetSet", 0);
		LCPolSchema tLCPolSchema = inLCPolSchema;
		if (tLCPolSchema == null) {
			return null;
		}

		LMDutyGetSchema tLMDutyGetSchema = new LMDutyGetSchema(); // 责任给付
		LMRiskAccGetSet tLMRiskAccGetSet = new LMRiskAccGetSet(); // 责任给付
		LMRiskAccGetSchema tLMRiskAccGetSchema = new LMRiskAccGetSchema(); // 险种保险帐户给付
		LCGetToAccSchema tLCGetToAccSchema = new LCGetToAccSchema(); // 给付项表和客户账户表的关联表
		LCGetToAccSet tLCGetToAccSet = new LCGetToAccSet(); // 给付项表和客户账户表的关联表

		for (int i = 1; i <= tLMDutyGetSet.size(); i++) {
			tLMDutyGetSchema = new LMDutyGetSchema();
			tLMDutyGetSchema = tLMDutyGetSet.get(i);

			// 判断是否和帐户相关
			if (tLMDutyGetSchema.getNeedAcc().equals("1")) {
				// 查询险种保险帐户给付表
				tLMRiskAccGetSet = new LMRiskAccGetSet();
				tLMRiskAccGetSet = queryLMRiskAccGet(
						tLCPolSchema.getRiskCode(), tLMDutyGetSchema
								.getGetDutyCode());
				if (tLMRiskAccGetSet == null) {
					continue;
				}
				for (int n = 1; n <= tLMRiskAccGetSet.size(); n++) {
					tLMRiskAccGetSchema = new LMRiskAccGetSchema();
					tLMRiskAccGetSchema = tLMRiskAccGetSet.get(n);
					if (tLMRiskAccGetSchema.getDealDirection().equals("0")
							&& tLMRiskAccGetSchema.getAccCreatePos().equals(
									AccCreatePos)) {
						tLCGetToAccSchema = new LCGetToAccSchema();

						// 判断是否需要录入
						if (tLMRiskAccGetSchema.getNeedInput().equals("1")) {
							logger.debug("Rate====" + Rate);
							if (Rate == null) {
								// @@错误处理
								logger.debug("createGetToAccForBat费率需要从界面录入");
								CError tError = new CError();
								tError.moduleName = "DealAccount";
								tError.functionName = "createGetToAcc";
								tError.errorMessage = "费率需要从界面录入，不能为空!";
								this.mErrors.addOneError(tError);

								return null;
							}
							tLCGetToAccSchema
									.setDefaultRate(Rate.doubleValue());
						} else {
							tLCGetToAccSchema
									.setDefaultRate(tLMRiskAccGetSchema
											.getDefaultRate());
						}

						tLCGetToAccSchema.setNeedInput(tLMRiskAccGetSchema
								.getNeedInput());
						// tLCGetToAccSchema.setPolNo(PolNo);
						tLCGetToAccSchema.setPolNo(inLCPolSchema.getPolNo());
						tLCGetToAccSchema.setDutyCode(tLCGetSet.get(i)
								.getDutyCode());
						tLCGetToAccSchema.setGetDutyCode(tLMRiskAccGetSchema
								.getGetDutyCode());
						tLCGetToAccSchema.setInsuAccNo(tLMRiskAccGetSchema
								.getInsuAccNo());
						tLCGetToAccSchema.setCalCodeMoney(tLMRiskAccGetSchema
								.getCalCodeMoney());
						tLCGetToAccSchema.setDealDirection(tLMRiskAccGetSchema
								.getDealDirection());
						tLCGetToAccSchema.setCalFlag(tLMRiskAccGetSchema
								.getCalFlag());
						tLCGetToAccSchema
								.setOperator(this.mGlobalInput.Operator);
						tLCGetToAccSchema.setMakeDate(CurrentDate);
						tLCGetToAccSchema.setMakeTime(CurrentTime);
						tLCGetToAccSchema.setModifyDate(CurrentDate);
						tLCGetToAccSchema.setModifyTime(CurrentTime);
						tLCGetToAccSet.add(tLCGetToAccSchema);
					}
				}
			}
		}

		if (tLCGetToAccSet.size() == 0) {
			// @@错误处理
			return null;
		}

		return tLCGetToAccSet;
	}

	/***************************************************************************
	 * ** 以下的方法都是健康险系统增加的方法 * AUTHOR: GUOXIANG * DATE: 2004-08-24
	 * *************************************************************************************
	 * 
	 * 生成保险帐户，生成结构SET对象: 构建保险账户表, 调用getLCInsureAccForHealth(....)
	 * 构建保费项表和客户账户表的关联表,调用getPremToAccForBat(....)
	 * 构建给付项表和客户账户表的关联表,调用getGetToAccForBat(....)
	 * 
	 * @param parmData
	 *            (Type:TransferData include:
	 *            PolNo，AccCreatePos，OtherNo，OtherNoType，Rate)
	 * @return VData (用容器include: LCInsureAccSet，LCPremToAccSet，LCGetToAccSet)
	 * @author guoxiang
	 * @data 2004-8-25
	 */
	public VData createInsureAccForHealth(TransferData parmData,
			LCPolSchema inLCPolSchema, LMRiskSchema inLMRiskSchema) {
		// 1-检验
		if (!checkTransferData(parmData)) {
			return null;
		}

		if ((inLCPolSchema == null) || (inLMRiskSchema == null)) {
			return null;
		}

		// 2-得到数据后用
		String tPolNo = (String) parmData.getValueByName("PolNo");
		String tAccCreatePos = (String) parmData.getValueByName("AccCreatePos");
		String tOtherNo = (String) parmData.getValueByName("OtherNo");
		String tOtherNoType = (String) parmData.getValueByName("OtherNoType");
		Double tRate;
		if (parmData.getValueByName("Rate") == null) {
			tRate = null;
		} else if (parmData.getValueByName("Rate").getClass().getName().equals(
				"java.lang.String")) {
			String strRate = (String) parmData.getValueByName("Rate");
			tRate = Double.valueOf(strRate);
		} else {
			tRate = (Double) parmData.getValueByName("Rate");
		}
		logger.debug("费率:" + tRate);

		// 3-构建保险账户表
		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
		tLCInsureAccSet = getLCInsureAccForHealth(tPolNo, tAccCreatePos,
				tOtherNo, tOtherNoType, inLCPolSchema, inLMRiskSchema);
		if (tLCInsureAccSet == null) {
			return null;
		}

		// 4-构建保费项表和客户账户表的关联表
		LCPremToAccSet tLCPremToAccSet = new LCPremToAccSet();
		tLCPremToAccSet = getPremToAccForBat(tPolNo, tAccCreatePos, tRate,
				inLCPolSchema);

		// if(tLCPremToAccSet==null) return null;
		// 5-构建给付项表和客户账户表的关联表
		LCGetToAccSet tLCGetToAccSet = new LCGetToAccSet();
		tLCGetToAccSet = getGetToAccForBat(tPolNo, tAccCreatePos, tRate,
				inLCPolSchema);

		// if(tLCGetToAccSet==null) return null;
		// 6-返回数据
		VData tVData = new VData();
		tVData.add(tLCInsureAccSet);
		tVData.add(tLCPremToAccSet); // 可能是null
		tVData.add(tLCGetToAccSet); // 可能是null

		return tVData;
	}

	/**
	 * 健康险部分对个人保单生成保险帐户表的扩充 (类型 1：空帐户,不需要添加履历表纪录)
	 * 原来处理方式是：把该险种的所有账户全部遍历，然后将数据插入到保险账户表
	 * 现有处理方式是：并不是把所有账户都要插入到保险账户表，根据保费项表的缴费计划编码 和给付项表的给付责任编码查对应的账户，然后将数据插入到保险账户表
	 * 因此现在的账户处理过程为： 1-校验 2-判断是否与帐户相关
	 * 3-根据投保单查询lcprem表(保费项表)和lcget表（给付项表）（数据库中是投保单号） 5-根据投保单表中的险种字段查询LMRisk表
	 * 6-判断是否与帐户相关 7-判断是否与帐户相关
	 * 
	 * @param PolNo
	 *            保单号
	 * @param AccCreatePos
	 *            生成位置 :1-签单时产生（承保） 2－缴费时产生 3－领取时产生
	 * @param OtherNo
	 *            保单号或交费号
	 * @param OtherNoType
	 *            保单号或交费号
	 * @return LCInsureAccSet
	 * @author guoxiang
	 * @data 2004-8-24
	 */
	public LCInsureAccSet getLCInsureAccForHealth(String PolNo,
			String AccCreatePos, String OtherNo, String OtherNoType,
			LCPolSchema inLCPolSchema, LMRiskSchema inLMRiskSchema) {
		// 1-校验
		if ((PolNo == null) || (AccCreatePos == null) || (OtherNo == null)
				|| (OtherNoType == null) || (inLCPolSchema == null)
				|| (inLMRiskSchema == null)) {
			// @@错误处理
			logger.debug("getLCInsureAccForHealth传入数据不能为空");
			buildError("getLCInsureAccForHealth", "传入数据不能为空!");

			return null;
		}

		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet(); // 保险帐户表

		// 2-判断是否与帐户相关
		if (inLMRiskSchema.getInsuAccFlag().equals("Y")
				|| inLMRiskSchema.getInsuAccFlag().equals("y")) {
			// 3根据投保单查询lcprem表(保费项表)和lcget表（给付项表）
			LCPremSet tLCPremSet = new LCPremSet();
			tLCPremSet = queryLCPrem(PolNo);
			if (tLCPremSet == null) {
				tLCPremSet = new LCPremSet();

			}

			LCGetSet tLCGetSet = new LCGetSet();
			tLCGetSet = queryLCGet(PolNo);
			if (tLCGetSet == null) {
				tLCGetSet = new LCGetSet();

			}

			LCPremSchema tLCPremSchema = new LCPremSchema(); // 保费项表
			LCGetSchema tLCGetSchema = new LCGetSchema(); // 给付项表

			LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
			LMRiskInsuAccSet tLMRiskInsuAccSet = new LMRiskInsuAccSet();
			LMRiskAccPaySet tLMRiskAccPaySet = null; // 险种缴费表
			LMRiskAccGetSet tLMRiskAccGetSet = null; // 险种给付表
			for (int i = 1; i <= tLCPremSet.size(); i++)
			// 保费项集合---险种交费账户集合---险种账户集合
			{
				tLCPremSchema = tLCPremSet.get(i);

				// 判断是否和帐户相关
				if (tLCPremSchema.getNeedAcc().equals("1")) {
					LMRiskAccPaySchema tLMRiskAccPaySchema = new LMRiskAccPaySchema();
					tLMRiskAccPaySchema = queryLMRiskAccPay(tLCPremSchema,
							inLMRiskSchema.getRiskCode());
					if (tLMRiskAccPaySchema == null) {
						continue;
					}

					LMRiskInsuAccSchema tLMRiskInsuAccSchema = new LMRiskInsuAccSchema();

					tLMRiskInsuAccSchema = queryLMRiskInsuAcc(tLMRiskAccPaySchema);

					if (tLMRiskInsuAccSchema == null) {
						continue;
					}

					tLMRiskInsuAccSet.add(tLMRiskInsuAccSchema);
				}
			}

			for (int i = 1; i <= tLCGetSet.size(); i++)
			// 给付项集合---险种给付账户集合---险种账户集合（不包含缴费项的得到账户集合）
			{
				tLCGetSchema = tLCGetSet.get(i);

				// 判断是否和帐户相关
				if (tLCGetSchema.getNeedAcc().equals("1")) {
					LMRiskAccGetSchema tLMRiskAccGetSchema = new LMRiskAccGetSchema();

					LMRiskInsuAccSchema tLMRiskInsuAccSchema = new LMRiskInsuAccSchema();

					tLMRiskAccGetSchema = queryLMRiskAccGet(tLCGetSchema,
							inLMRiskSchema.getRiskCode());

					if (tLMRiskAccGetSchema == null) {
						continue;
					}

					boolean continueFlag = false;
					for (int j = 1; j <= tLMRiskInsuAccSet.size(); j++) {
						LMRiskInsuAccSchema pLMRiskInsuAccSchema = tLMRiskInsuAccSet
								.get(j);

						if (tLMRiskAccGetSchema.getInsuAccNo().equals(
								pLMRiskInsuAccSchema.getInsuAccNo())) {
							continueFlag = true;

							break;
						}
					}
					if (continueFlag) {
						continue;
					}

					tLMRiskInsuAccSchema = queryLMRiskInsuAcc(tLMRiskAccGetSchema);

					if (tLMRiskInsuAccSchema == null) {
						continue;
					}
					tLMRiskInsuAccSet.add(tLMRiskInsuAccSchema);
				}
			}

			// 循环并集
			for (int k = 1; k <= tLMRiskInsuAccSet.size(); k++) {
				// 有险种账户表---生成保险账户表
				LMRiskInsuAccSchema tLMRiskInsuAccSchema = tLMRiskInsuAccSet
						.get(k); // 保险帐户表

				// 如果账户生成位置找到匹配的保险账户
				if (tLMRiskInsuAccSchema.getAccCreatePos().equals(AccCreatePos)) {
					tLCInsureAccSchema = new LCInsureAccSchema();
					tLCInsureAccSchema.setPolNo(inLCPolSchema.getPolNo());
					tLCInsureAccSchema.setInsuAccNo(tLMRiskInsuAccSchema
							.getInsuAccNo());
					tLCInsureAccSchema
							.setRiskCode(inLMRiskSchema.getRiskCode());
					tLCInsureAccSchema.setAccType(tLMRiskInsuAccSchema
							.getAccType());
					tLCInsureAccSchema.setGrpContNo(inLCPolSchema
							.getGrpContNo());
					tLCInsureAccSchema.setPrtNo(inLCPolSchema.getPrtNo());
					tLCInsureAccSchema.setContNo(inLCPolSchema.getContNo());
					tLCInsureAccSchema.setGrpPolNo(inLCPolSchema.getGrpPolNo());
					tLCInsureAccSchema.setInsuredNo(inLCPolSchema
							.getInsuredNo());
					tLCInsureAccSchema.setAppntNo(inLCPolSchema.getAppntNo());
					// tLCInsureAccSchema.setAppntName(inLCPolSchema.getAppntName());
					tLCInsureAccSchema.setSumPay(0);
					tLCInsureAccSchema.setInsuAccBala(0);
					tLCInsureAccSchema.setUnitCount(0);
					tLCInsureAccSchema.setInsuAccGetMoney(0);
					tLCInsureAccSchema.setFrozenMoney(0);
					tLCInsureAccSchema.setLastAccBala(0);
					tLCInsureAccSchema.setLastUnitCount(0);
					tLCInsureAccSchema.setLastUnitPrice(0);
					tLCInsureAccSchema.setUnitPrice(0);
					tLCInsureAccSchema.setAccComputeFlag(tLMRiskInsuAccSchema
							.getAccComputeFlag());
					tLCInsureAccSchema.setAccType(tLMRiskInsuAccSchema
							.getAccType());
					tLCInsureAccSchema.setManageCom(inLCPolSchema
							.getManageCom());
					tLCInsureAccSchema.setOperator(this.mGlobalInput.Operator);
					tLCInsureAccSchema.setBalaDate(inLCPolSchema.getCValiDate());
					tLCInsureAccSchema.setBalaTime("00:00:00");
					tLCInsureAccSchema.setMakeDate(CurrentDate);
					tLCInsureAccSchema.setMakeTime(CurrentTime);
					tLCInsureAccSchema.setModifyDate(CurrentDate);
					tLCInsureAccSchema.setModifyTime(CurrentTime);
					tLCInsureAccSchema.setState("0");
					tLCInsureAccSchema.setInvestType(tLMRiskInsuAccSchema
							.getInvestType());
					tLCInsureAccSchema.setFundCompanyCode(tLMRiskInsuAccSchema
							.getFundCompanyCode());
					tLCInsureAccSchema
							.setOwner(tLMRiskInsuAccSchema.getOwner());
					
					//tongmeng 2010-11-08 modify
					//增加多币种处理
					tLCInsureAccSchema.setCurrency(inLCPolSchema.getCurrency());
					
					tLCInsureAccSet.add(tLCInsureAccSchema);

				}
			}

			return tLCInsureAccSet;
		}

		return null;
	}

	/**
	 * 健康险系统注入资金取得管理费比例从保费项表中取 修改calInputMoney() 为 calInputMoneyHealth()
	 * 保险账户资金注入(类型3 针对保费项,注意没有给出注入资金，内部会调用计算金额的函数)
	 * 适用于：在生成帐户结构后，此时数据尚未提交到数据库，又需要执行帐户的资金注入。 即在使用了
	 * createInsureAccHealth()方法后，得到VData数据，接着修改VData中帐户的金额
	 * 
	 * @param inVData
	 *            使用了 createInsureAcc()方法后，得到的VData数据
	 * @param pLCPremSet
	 *            保费项集合
	 * @param AccCreatePos
	 *            参见 险种保险帐户缴费 LMRiskAccPay
	 * @param OtherNo
	 *            参见 保险帐户表 LCInsureAcc
	 * @param OtherNoType
	 *            号码类型
	 * @param MoneyType
	 *            参见 保险帐户表记价履历表 LCInsureAccTrace
	 * @param Rate
	 *            费率
	 * @return VData(tLCInsureAccSet:update or insert ,tLCInsureAccTraceSet:
	 *         insert)
	 * @author :guoxiang
	 * @date: 2004-08-25
	 */
	public VData addPremInnerHealth(VData inVData, LCPremSet pLCPremSet,
			String AccCreatePos, String OtherNo, String OtherNoType,
			String MoneyType, String RiskCode, String Rate) {
		if ((inVData == null) || (pLCPremSet == null) || (AccCreatePos == null)
				|| (OtherNo == null) || (OtherNoType == null)
				|| (MoneyType == null) || (RiskCode == null)) {
			// @@错误处理
			logger.debug("addPremInnerHealth传入数据不能为空");
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "addPrem";
			tError.errorMessage = "传入数据不能为空";
			this.mErrors.addOneError(tError);

			return null;
		}

		VData tVData = new VData();

		// 得到生成的保险帐户表
		LCInsureAccSet tLCInsureAccSet = (LCInsureAccSet) (inVData
				.getObjectByObjectName("LCInsureAccSet", 0));

		// 得到生成的缴费帐户关联表
		LCPremToAccSet tLCPremToAccSet = (LCPremToAccSet) (inVData
				.getObjectByObjectName("LCPremToAccSet", 0));

		// 得到领取帐户关联表--目前不用
		LCGetToAccSet tLCGetToAccSet = (LCGetToAccSet) (inVData
				.getObjectByObjectName("LCGetToAccSet", 0));

		if (tLCInsureAccSet == null) {
			tLCInsureAccSet = new LCInsureAccSet();
		}
		if (tLCPremToAccSet == null) {
			tLCPremToAccSet = new LCPremToAccSet();
		}
		if (tLCGetToAccSet == null) {
			tLCGetToAccSet = new LCGetToAccSet();
		}

		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet(); // 保险帐户表记价履历表
		LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();

		double inputMoney = 0;
		for (int n = 1; n <= pLCPremSet.size(); n++) {
			LCPremSchema tLCPremSchema = pLCPremSet.get(n);

			// 判断是否帐户相关
			if (tLCPremSchema.getNeedAcc().equals("1")) {
				for (int m = 1; m <= tLCPremToAccSet.size(); m++) {
					LCPremToAccSchema tLCPremToAccSchema = tLCPremToAccSet
							.get(m);

					// 如果当前保费项和当前的缴费帐户关联表的保单号，责任编码，交费计划编码相同
					if (tLCPremSchema.getPolNo().equals(
							tLCPremToAccSchema.getPolNo())
							&& tLCPremSchema.getDutyCode().equals(
									tLCPremToAccSchema.getDutyCode())
							&& tLCPremSchema.getPayPlanCode().equals(
									tLCPremToAccSchema.getPayPlanCode())) {
						// 计算需要注入的资金
						inputMoney = calInputMoneyHealth(tLCPremToAccSchema,
								tLCPremSchema);
						if (inputMoney == -1) {
							// @@错误处理
							logger.debug("addPremInnerHealth计算实际应该注入的资金出错");
							CError tError = new CError();
							tError.moduleName = "DealAccount";
							tError.functionName = "addPrem";
							tError.errorMessage = "计算实际应该注入的资金出错";
							this.mErrors.addOneError(tError);

							return null;
						}
						for (int j = 1; j <= tLCInsureAccSet.size(); j++) {
							// 如果当前缴费帐户关联表的保单号，账户号和当前的账户表的保单号，账户号相同并且资金不为0，将资金注入
							LCInsureAccSchema tLCInsureAccSchema = tLCInsureAccSet
									.get(j);
							if (tLCPremToAccSchema.getPolNo().equals(
									tLCInsureAccSchema.getPolNo())
									&& tLCPremToAccSchema.getInsuAccNo()
											.equals(
													tLCInsureAccSchema
															.getInsuAccNo())
									&& (inputMoney != 0)) {
								// 修改保险帐户金额
								tLCInsureAccSchema
										.setInsuAccBala(tLCInsureAccSchema
												.getInsuAccBala()
												+ inputMoney);
								tLCInsureAccSchema.setSumPay(tLCInsureAccSchema
										.getSumPay()
										+ inputMoney);

								// tLCInsureAccSchema.setInsuAccGetMoney(tLCInsureAccSchema.getInsuAccGetMoney()+inputMoney);
								tLCInsureAccSet.set(j, tLCInsureAccSchema);

								// 查询险种保险帐户缴费
								LMRiskAccPaySchema tLMRiskAccPaySchema = queryLMRiskAccPay3(
										RiskCode, tLCPremToAccSchema);
								if (tLMRiskAccPaySchema == null) {
									return null;
								}
								if (tLMRiskAccPaySchema.getPayNeedToAcc()
										.equals("1")) {
									// 填充保险帐户表记价履历表
									tLimit = PubFun.getNoLimit(tLCPremSchema
											.getManageCom());
									serNo = PubFun1.CreateMaxNo("SERIALNO",
											tLimit);
									tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
									tLCInsureAccTraceSchema.setSerialNo(serNo);
									// tLCInsureAccTraceSchema.setInsuredNo(
									// tLCInsureAccSchema
									// .getInsuredNo());
									tLCInsureAccTraceSchema
											.setPolNo(tLCInsureAccSchema
													.getPolNo());
									tLCInsureAccTraceSchema
											.setMoneyType(MoneyType);
									tLCInsureAccTraceSchema
											.setRiskCode(tLCInsureAccSchema
													.getRiskCode());
									tLCInsureAccTraceSchema.setOtherNo(OtherNo);
									tLCInsureAccTraceSchema
											.setOtherType(OtherNoType);
									tLCInsureAccTraceSchema
											.setMoney(inputMoney);
									tLCInsureAccTraceSchema
											.setContNo(tLCInsureAccSchema
													.getContNo());
									tLCInsureAccTraceSchema
											.setGrpPolNo(tLCInsureAccSchema
													.getGrpPolNo());
									tLCInsureAccTraceSchema
											.setInsuAccNo(tLCInsureAccSchema
													.getInsuAccNo());
									/*
									 * Lis5.3 upgrade set
									 * tLCInsureAccTraceSchema.setAppntName(tLCInsureAccSchema
									 * .getAppntName());
									 */
									tLCInsureAccTraceSchema
											.setState(tLCInsureAccSchema
													.getState());
									tLCInsureAccTraceSchema
											.setManageCom(tLCInsureAccSchema
													.getManageCom());
									tLCInsureAccTraceSchema
											.setOperator(tLCInsureAccSchema
													.getOperator());
									tLCInsureAccTraceSchema
											.setMakeDate(CurrentDate);
									tLCInsureAccTraceSchema
											.setMakeTime(CurrentTime);
									tLCInsureAccTraceSchema
											.setModifyDate(CurrentDate);
									tLCInsureAccTraceSchema
											.setModifyTime(CurrentTime);
									tLCInsureAccTraceSchema
											.setPayDate(CurrentDate);
									tLCInsureAccTraceSet
											.add(tLCInsureAccTraceSchema);
								}

								break;
							}
						}
					}
				}
			}
		}

		inVData.clear();
		inVData.add(tLCInsureAccSet);
		inVData.add(tLCPremToAccSet);
		inVData.add(tLCGetToAccSet);

		// 添加帐户注入资金轨迹
		inVData.add(tLCInsureAccTraceSet);

		// 操作数据库时执行插入操作
		return inVData; // (LCInsureAccSet,LCPremToAccSet,LCGetToAccSet,LCInsureAccTraceSet)
	}

	/**
	 * 保险账户资金注入(类型1 针对保费项,注意没有给出注入资金，内部会调用计算金额的函数)
	 * 
	 * @param pLCPremSchema
	 *            保费项
	 * @param AccCreatePos
	 *            参见 险种保险帐户缴费 LMRiskAccPay
	 * @param OtherNo
	 *            参见 保险帐户表 LCInsureAcc
	 * @param OtherNoType
	 *            号码类型
	 * @param MoneyType
	 *            参见 保险帐户表记价履历表 LCInsureAccTrace
	 * @param Rate
	 *            费率
	 * @return VData(tLCInsureAccSet:update or insert ,tLCInsureAccTraceSet:
	 *         insert)
	 * @author guoxiang
	 * @data 2004-9-2 10:14
	 */
	public VData addPremHealth(LCPremSchema pLCPremSchema, String AccCreatePos,
			String OtherNo, String OtherNoType, String MoneyType, Double Rate) {
		if ((pLCPremSchema == null) || (AccCreatePos == null)
				|| (OtherNo == null) || (OtherNoType == null)
				|| (MoneyType == null)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "addPrem";
			tError.errorMessage = "传入数据不能为空";
			this.mErrors.addOneError(tError);

			return null;
		}

		VData tVData = new VData();
		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet(); // 保险帐户表
		LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet(); // 保险帐户表记价履历表
		LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
		LCPremToAccSet tLCPremToAccSet = new LCPremToAccSet(); // 保费项表和客户帐户表的关联表
		LCPremToAccSchema tLCPremToAccSchema = new LCPremToAccSchema();
		LMRiskAccPaySchema tLMRiskAccPaySchema = new LMRiskAccPaySchema();
		String newFlag = "";
		boolean addPrem = false;
		double inputMoney = 0;

		// 判断是否帐户相关
		if (pLCPremSchema.getNeedAcc().equals("1")) {
			tLCPremToAccSet = queryLCPremToAccSet(pLCPremSchema);
			if (tLCPremToAccSet == null) {
				return null;
			}

			TransferData tFData = new TransferData();
			LCInsureAccSet mLCInsureAccSet = new LCInsureAccSet();

			// 判断生成位置是否匹配
			if (AccCreatePos.equals(tLCPremToAccSet.get(1).getNewFlag())) {
				// 如果匹配：生成帐户(即对于每次交费都产生新账号的情况，参看LCInsureAcc-保险帐户表)
				tFData = new TransferData();
				tFData.setNameAndValue("PolNo", pLCPremSchema.getPolNo());
				tFData.setNameAndValue("OtherNo", OtherNo); // 对于每次交费都产生新账号的情况，该字段存放交费号。主键
				tFData.setNameAndValue("OtherNoType", OtherNoType);
				tFData.setNameAndValue("Rate", Rate);
				tLCInsureAccSet = new LCInsureAccSet();
				mLCInsureAccSet = getLCInsureAcc(pLCPremSchema.getPolNo(),
						AccCreatePos, OtherNo, OtherNoType);
				if (mLCInsureAccSet == null) {
					return null;
				}
				newFlag = "INSERT";
			}

			for (int i = 1; i <= tLCPremToAccSet.size(); i++) {
				tLCPremToAccSchema = new LCPremToAccSchema();
				tLCPremToAccSchema = tLCPremToAccSet.get(i);

				// 计算实际应该注入的资金
				inputMoney = calInputMoneyHealth(tLCPremToAccSchema,
						pLCPremSchema);
				if (inputMoney == -1) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "DealAccount";
					tError.functionName = "addPrem";
					tError.errorMessage = "计算实际应该注入的资金出错";
					this.mErrors.addOneError(tError);

					return null;
				}
				if (newFlag.equals("INSERT")) { // 如果是新生成帐户
					// 根据保单号和保险账户号和其它号码查询mLCInsureAccSet集合中唯一一条数据
					tLCInsureAccSchema = new LCInsureAccSchema();
					tLCInsureAccSchema = queryLCInsureAccSet(pLCPremSchema
							.getPolNo(), tLCPremToAccSchema.getInsuAccNo(),
							OtherNo, mLCInsureAccSet);
					if (tLCInsureAccSchema == null) {
						return null;
					}
				} else {
					// 根据保单号和保险账户号和其它号码查询LCInsureAcc表的唯一一条数据
					tLCInsureAccSchema = new LCInsureAccSchema();
					tLCInsureAccSchema = queryLCInsureAcc(pLCPremSchema
							.getPolNo(), tLCPremToAccSchema.getInsuAccNo(),
							OtherNo);
					if (tLCInsureAccSchema == null) {
						return null;
					}
				}

				// 修改保险帐户金额
				tLCInsureAccSchema.setInsuAccBala(tLCInsureAccSchema
						.getInsuAccBala()
						+ inputMoney);
				tLCInsureAccSchema.setSumPay(tLCInsureAccSchema.getSumPay()
						+ inputMoney);
				tLCInsureAccSchema.setModifyDate(CurrentDate);
				tLCInsureAccSchema.setModifyTime(CurrentTime);

				// tLCInsureAccSchema.setInsuAccGetMoney(tLCInsureAccSchema.getInsuAccGetMoney()+inputMoney);
				tLMRiskAccPaySchema = queryLMRiskAccPay2(tLCPremToAccSchema); // 查询险种保险帐户缴费
				if (tLMRiskAccPaySchema == null) {
					return null;
				}
				if (tLMRiskAccPaySchema.getPayNeedToAcc().equals("1")
						&& (inputMoney != 0)) {
					// 填充保险帐户表记价履历表
					tLimit = PubFun.getNoLimit(pLCPremSchema.getManageCom());
					serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
					tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
					tLCInsureAccTraceSchema.setSerialNo(serNo);
					// tLCInsureAccTraceSchema.setInsuredNo(tLCInsureAccSchema
					// .getInsuredNo());
					tLCInsureAccTraceSchema.setPolNo(tLCInsureAccSchema
							.getPolNo());
					tLCInsureAccTraceSchema.setMoneyType(MoneyType);
					tLCInsureAccTraceSchema.setRiskCode(tLCInsureAccSchema
							.getRiskCode());
					tLCInsureAccTraceSchema.setOtherNo(OtherNo);
					tLCInsureAccTraceSchema.setOtherType(OtherNoType);
					tLCInsureAccTraceSchema.setMoney(inputMoney);
					tLCInsureAccTraceSchema.setContNo(tLCInsureAccSchema
							.getContNo());
					tLCInsureAccTraceSchema.setGrpPolNo(tLCInsureAccSchema
							.getGrpPolNo());
					tLCInsureAccTraceSchema.setInsuAccNo(tLCInsureAccSchema
							.getInsuAccNo());

					tLCInsureAccTraceSchema.setState(tLCInsureAccSchema
							.getState());
					tLCInsureAccTraceSchema.setManageCom(tLCInsureAccSchema
							.getManageCom());
					tLCInsureAccTraceSchema.setOperator(tLCInsureAccSchema
							.getOperator());
					tLCInsureAccTraceSchema.setMakeDate(CurrentDate);
					tLCInsureAccTraceSchema.setMakeTime(CurrentTime);
					tLCInsureAccTraceSchema.setModifyDate(CurrentDate);
					tLCInsureAccTraceSchema.setModifyTime(CurrentTime);
					tLCInsureAccTraceSchema.setPayDate(CurrentDate);
					tLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
				}

				// 添加容器
				tLCInsureAccSet.add(tLCInsureAccSchema);
			}
		}
		if (tLCInsureAccSet.size() == 0) {
			// @@错误处理
			// CError tError =new CError();
			// tError.moduleName="DealAccount";
			// tError.functionName="addPrem";
			// tError.errorMessage="条件不符合，没有生成纪录";
			// this.mErrors .addOneError(tError) ;
			return null;
		}
		tVData.add(tLCInsureAccSet);
		tVData.add(tLCInsureAccTraceSet);

		return tVData;

		// 最后在操作VData时，（数据tLCInsureAccSet可能是update or insert）
		// 因此操作数据库时先执行删除操作，再执行插入操作
	}

	/**
	 * 健康险系统的管理费比例取自保费项表 修改：tCalculator.addBasicFactor("ManageFeeRate",
	 * String.valueOf(tLCPolDB.getManageFeeRate())); //管理费比例-参见众悦年金分红-计算编码601304
	 * 为： 计算实际应该注入的资金(类似佣金计算,不过数据库内的计算编码尚未描述)
	 * 
	 * @param tLCPremToAccSchema
	 *            传入保费项表和客户帐户表的关联表纪录
	 * @param Prem
	 *            缴纳保费
	 * @return 实际应该注入的资金
	 */
	public double calInputMoneyHealth(LCPremToAccSchema tLCPremToAccSchema,
			LCPremSchema tLCPremSchema) {
		// @@错误处理
		if (tLCPremToAccSchema == null) {
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "calInputMoneyRate";
			tError.errorMessage = "传入数据不能为空!";
			this.mErrors.addOneError(tError);

			return -1;
		}

		double Prem = tLCPremSchema.getPrem();
		/*
		 * Lis5.3 upgrade get double ManageFeeRate =
		 * tLCPremSchema.getManageFeeRate();
		 */
		double ManageFeeRate = 0;
		String[] F = new String[5];
		int m = 0;
		double defaultRate = 0;
		double inputMoney = 0;
		String calMoney = "";
		defaultRate = tLCPremToAccSchema.getRate(); // 缺省比例

		Calculator tCalculator = new Calculator(); // 计算类

		if (tLCPremToAccSchema.getCalFlag() == null) { // 如果该标记为空
			inputMoney = Prem * 1 * defaultRate;

			return inputMoney;
		}

		// 账户转入计算标志:0 －－ 完全转入账户
		// 1 －－ 按现金计算转入账户
		// 2 －－ 按股份计算转入账户
		// 3 －－ 先算现金，然后按股份计算。(未做)
		if (tLCPremToAccSchema.getCalFlag().equals("0")) {
			inputMoney = Prem * 1 * defaultRate;

			return inputMoney;
		}
		if (tLCPremToAccSchema.getCalFlag().equals("1")) {
			if (tLCPremToAccSchema.getCalCodeMoney() == null) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "DealAccount";
				tError.functionName = "calInputMoneyRate";
				tError.errorMessage = "未找到转入账户时的算法编码(现金)!";
				this.mErrors.addOneError(tError);

				return -1;
			}
			tCalculator.setCalCode(tLCPremToAccSchema.getCalCodeMoney()); // 添加计算编码

			tCalculator.addBasicFactor("ManageFeeRate", Double
					.valueOf(ManageFeeRate).toString());

			// 管理费比例-参见众悦年金分红-计算编码601304
			tCalculator.addBasicFactor("Prem", Double.valueOf(Prem).toString()
					);

			// 计算要素可后续添加
			calMoney = tCalculator.calculate();
			if (calMoney == null) {
				CError tError = new CError();
				tError.moduleName = "DealAccount";
				tError.functionName = "calInputMoneyRate";
				tError.errorMessage = "计算注入帐户资金失败!";
				this.mErrors.addOneError(tError);

				return -1;
			}
			inputMoney = Double.parseDouble(calMoney);

			return inputMoney;
		}
		if (tLCPremToAccSchema.getCalFlag().equals("2")) {
			if (tLCPremToAccSchema.getCalCodeMoney() == null) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "DealAccount";
				tError.functionName = "calInputMoneyRate";
				tError.errorMessage = "未找到转入账户时的算法编码(股份)";
				this.mErrors.addOneError(tError);

				return -1;
			}
			tCalculator.setCalCode(tLCPremToAccSchema.getCalCodeUnit()); // 添加计算编码

			// 添加计算必要条件：保费
			tCalculator.addBasicFactor("Prem", Double.valueOf(Prem).toString());
			calMoney = tCalculator.calculate();
			if (calMoney == null) {
				CError tError = new CError();
				tError.moduleName = "DealAccount";
				tError.functionName = "calInputMoneyRate";
				tError.errorMessage = "计算注入帐户资金失败!";
				this.mErrors.addOneError(tError);

				return -1;
			}
			inputMoney = Double.parseDouble(calMoney);

			return inputMoney;
		}

		return 0;
	}

	/**
	 * 根据缴费项纪录和险种编码 查询险种保险帐户缴费表 因为只要保证一条缴费纪录
	 * 
	 * 
	 * 
	 * @param pLCPremSchema
	 * @param Riskcode
	 * @return LMRiskAccPaySchema
	 * @author guoxiang
	 * @data 2004-8-24 10:14
	 */
	public LMRiskAccPaySchema queryLMRiskAccPay(LCPremSchema pLCPremSchema,
			String riskcode) {
		if ((pLCPremSchema == null) || (riskcode == null)) {
			// @@错误处理
			logger.debug("queryLMRiskAccPay传入数据不能为空");
			buildError("queryLMRiskAccPay", "传入数据不能为空！");

			return null;
		}

		String payPlanCode = pLCPremSchema.getPayPlanCode();

		// 查询险种保险帐户缴费表
		String sqlStr = "select * from LMRiskAccPay where RiskCode='"
				+ "?s1?" + "' and payPlanCode='" + "?s2?" + "'";
		SQLwithBindVariables sqlbv20=new SQLwithBindVariables();
		sqlbv20.sql(sqlStr);
		sqlbv20.put("s1",riskcode);
		sqlbv20.put("s2",payPlanCode);
		LMRiskAccPaySet tLMRiskAccPaySet = new LMRiskAccPaySet();
		LMRiskAccPaySchema tLMRiskAccPaySchema = new LMRiskAccPaySchema();
		LMRiskAccPayDB tLMRiskAccPayDB = tLMRiskAccPaySchema.getDB();
		tLMRiskAccPaySet = tLMRiskAccPayDB.executeQuery(sqlbv20);
		if (tLMRiskAccPayDB.mErrors.needDealError() == true) {
			// @@错误处理
			logger.debug("queryLMRiskAccPay险种保险帐户缴费表查询失败");
			buildError("queryLMRiskAccPay", "险种保险帐户缴费表查询失败！");

			return null;
		}
		if (tLMRiskAccPaySet.size() == 0) {
			// @@错误处理
			logger.debug("queryLMRiskAccPay险种保险帐户缴费表没有查询到相关数据");
			buildError("queryLMRiskAccPay", "险种保险帐户缴费表没有查询到相关数据！");
			return null;
		}

		return tLMRiskAccPaySet.get(1);
	}

	/**
	 * 查询管理费表
	 * 
	 * @param tLCInsureAccTraceSchema
	 *            LCInsureAccTraceSchema
	 * @return LMRiskFeeSchema
	 */
	public LMRiskFeeSchema queryLMRiskFee(
			LCInsureAccTraceSchema tLCInsureAccTraceSchema) {
		LMRiskFeeSet tLMRiskFeeSet = new LMRiskFeeSet();
		LMRiskFeeDB tLMRiskFeeDB = new LMRiskFeeDB();
		String sqlStr = "select * from lmriskfee where 1=1"
				+ " and InsuAccNo = '" + "?s3?"
				+ "'" + " and payplancode = '"
				+ "?s4?" + "'";
		SQLwithBindVariables sqlbv21=new SQLwithBindVariables();
		sqlbv21.sql(sqlStr);
		sqlbv21.put("s3",tLCInsureAccTraceSchema.getInsuAccNo());
		sqlbv21.put("s4",tLCInsureAccTraceSchema.getPayPlanCode());
		// tongmeng 2007-11-29 add
		// 临时给MS万能险添加,二期需要考虑如何生成
		// 00903000
		// tongmeng 2008-01-07 modify
		// 去除此段校验
		// if(tLCInsureAccTraceSchema.getRiskCode().equals("314301")||tLCInsureAccTraceSchema.getRiskCode().equals("00903000"))
		//tongmeng 2008-09-09 modify
		//团险不加此条件 
		
		//logger.debug("Integer.parseInt(tLCInsureAccTraceSchema.getContNo()):"+Integer.parseInt(tLCInsureAccTraceSchema.getContNo()));
		//if(Integer.parseInt(tLCInsureAccTraceSchema.getContNo())!=0)
		LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
		tLMRiskAppDB.setRiskCode(tLCInsureAccTraceSchema.getRiskCode());
		if(!tLMRiskAppDB.getInfo())
		{
			buildError("queryLMRiskFee", "查询险种信息出错！");
			return null;
		}
		/*if(tLMRiskAppDB.getRiskProp()!=null&&!tLMRiskAppDB.getRiskProp().equals("G"))
		{
			sqlStr = sqlStr + " and FeeKind='03' and FeeTakePlace='01' ";
		}*/
		logger.debug(sqlStr);
		tLMRiskFeeSet = tLMRiskFeeDB.executeQuery(sqlbv21);
		if (tLMRiskFeeDB.mErrors.needDealError() == true) {
			// @@错误处理
			buildError("queryLMRiskFee", "险种保险帐户管理费表查询失败！");

			return null;
		}
		if (tLMRiskFeeSet.size() == 0) {
			// @@错误处理
			buildError("queryLMRiskFee", "险种保险帐户管理费表没有查询到相关数据！");
			return null;
		}

		return tLMRiskFeeSet.get(1);
	}
	 /* 查询管理费表
	 *
	 * @param tLCInsureAccTraceSchema
	 *            LCInsureAccTraceSchema
	 * @return LMRiskFeeSchema
	 */
	private LMRiskFeeSet queryLMRiskFeeSet(
			LCInsureAccTraceSchema tLCInsureAccTraceSchema, String cTakePlace) {
		LMRiskFeeSet tLMRiskFeeSet = new LMRiskFeeSet();
		LMRiskFeeDB tLMRiskFeeDB = new LMRiskFeeDB();
		String sqlStr = "";
		SQLwithBindVariables sqlbv22=new SQLwithBindVariables();
		if ("01".equals(cTakePlace)) {
			sqlStr = "select * from lmriskfee where 1=1"
				+ " and InsuAccNo = '" + "?s5?"
				+ "'" + " and payplancode = '"
				+ "?s6?"
				+ "' and (feetakeplace='" + "?s7?" + "' or feetakeplace='03')";
			
			sqlbv22.sql(sqlStr);
			sqlbv22.put("s5",tLCInsureAccTraceSchema.getInsuAccNo());
			sqlbv22.put("s6",tLCInsureAccTraceSchema.getPayPlanCode());
			sqlbv22.put("s7", cTakePlace);
		}
		logger.debug(sqlStr);
		tLMRiskFeeSet = tLMRiskFeeDB.executeQuery(sqlbv22);
		if (tLMRiskFeeDB.mErrors.needDealError() == true) {
			// @@错误处理
			logger.debug("queryLMRiskFee险种保险帐户管理费表查询失败");
			buildError("queryLMRiskFee", "险种保险帐户管理费表查询失败！");

			return null;
		}
		if (tLMRiskFeeSet.size() == 0) {
			// @@错误处理
			logger.debug("queryLMRiskFee险种保险帐户管理费表没有查询到相关数据");
			buildError("queryLMRiskFee", "险种保险帐户管理费表没有查询到相关数据！");
			return null;
		}

		return tLMRiskFeeSet;
	}

	/**
	 * 查询险种保险帐户给付表
	 * 
	 * @param RiskCode
	 * @param GetDutyCode
	 * @return
	 * @author guoxiang
	 * @data 2004-8-24 10:14
	 */
	public LMRiskAccGetSchema queryLMRiskAccGet(LCGetSchema pLCGetSchema,
			String riskcode) {
		String sqlStr = "select * from LMRiskAccGet where GetDutyCode='"
				+ "?s8?" + "' and RiskCode='" + "?s9?"
				+ "'";
		SQLwithBindVariables sqlbv23=new SQLwithBindVariables();
		sqlbv23.sql(sqlStr);
		sqlbv23.put("s8",pLCGetSchema.getGetDutyCode());
		sqlbv23.put("s9", riskcode);
		LMRiskAccGetSchema tLMRiskAccGetSchema = new LMRiskAccGetSchema();
		LMRiskAccGetSet tLMRiskAccGetSet = new LMRiskAccGetSet();
		LMRiskAccGetDB tLMRiskAccGetDB = tLMRiskAccGetSchema.getDB();
		tLMRiskAccGetSet = tLMRiskAccGetDB.executeQuery(sqlbv23);
		if (tLMRiskAccGetDB.mErrors.needDealError() == true) {
			// @@错误处理
			buildError("queryLMRiskAccGet", "险种保险帐户给付表查询失败！");

			return null;
		}
		if (tLMRiskAccGetSet.size() == 0) {
			// @@错误处理
			buildError("queryLMRiskAccGet", "险种保险帐户给付表没有查询到相关数据！");
			return null;
		}

		return tLMRiskAccGetSet.get(1);
	}

	/**
	 * 查询险种保险帐户(交费)
	 * 
	 * @param LMRiskAccPaySchema
	 *            tLMRiskAccPaySchema
	 * @return
	 * @author guoxiang
	 * @data 2004-8-24 10:14
	 */
	public LMRiskInsuAccSchema queryLMRiskInsuAcc(
			LMRiskAccPaySchema tLMRiskAccPaySchema) {
		String sqlStr = "select * from LMRiskInsuAcc where InsuAccNo='"
				+ "?s11?" + "'";
		SQLwithBindVariables sqlbv24=new SQLwithBindVariables();
		sqlbv24.sql(sqlStr);
		sqlbv24.put("s11",tLMRiskAccPaySchema.getInsuAccNo());
		LMRiskInsuAccSet tLMRiskInsuAccSet = new LMRiskInsuAccSet();
		LMRiskInsuAccSchema tLMRiskInsuAccSchema = new LMRiskInsuAccSchema();
		LMRiskInsuAccDB tLMRiskInsuAccDB = tLMRiskInsuAccSchema.getDB();
		tLMRiskInsuAccSet = tLMRiskInsuAccDB.executeQuery(sqlbv24);
		if (tLMRiskInsuAccDB.mErrors.needDealError() == true) {
			// @@错误处理
			buildError("queryLMRiskInsuAcc", "险种保险帐户表查询失败！");

			return null;
		}
		if (tLMRiskInsuAccSet.size() == 0) {
			// @@错误处理
			buildError("queryLMRiskInsuAcc", "险种保险帐户表没有查询到相关数据！");

			return null;
		}

		return tLMRiskInsuAccSet.get(1);
	}

	/**
	 * 查询险种保险帐户(给付)
	 * 
	 * @param LMRiskAccPaySchema
	 *            tLMRiskAccPaySchema
	 * @return
	 * 
	 */
	public LMRiskInsuAccSchema queryLMRiskInsuAcc(
			LMRiskAccGetSchema tLMRiskAccGetSchema) {
		String sqlStr = "select * from LMRiskInsuAcc where InsuAccNo='"
				+ "?s12?" + "'";
		SQLwithBindVariables sqlbv25=new SQLwithBindVariables();
		sqlbv25.sql(sqlStr);
		sqlbv25.put("s12",tLMRiskAccGetSchema.getInsuAccNo());
		LMRiskInsuAccSet tLMRiskInsuAccSet = new LMRiskInsuAccSet();
		LMRiskInsuAccSchema tLMRiskInsuAccSchema = new LMRiskInsuAccSchema();
		LMRiskInsuAccDB tLMRiskInsuAccDB = tLMRiskInsuAccSchema.getDB();
		tLMRiskInsuAccSet = tLMRiskInsuAccDB.executeQuery(sqlbv25);
		if (tLMRiskInsuAccDB.mErrors.needDealError() == true) {
			// @@错误处理
			buildError("queryLMRiskInsuAcc", "险种保险帐户表查询失败！");

			return null;
		}
		if (tLMRiskInsuAccSet.size() == 0) {
			// @@错误处理
			buildError("queryLMRiskInsuAcc", "险种保险帐户表没有查询到相关数据！");

			return null;
		}

		return tLMRiskInsuAccSet.get(1);
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "DealAccount";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 查询险种保险帐户缴费表
	 * 
	 * @param payPlanCode
	 * @param riskCode
	 *            return LMRiskAccPaySet
	 */
	private LMRiskAccPaySet queryLMRiskAccPayForBat(String payPlanCode,
			String riskCode) {
		// 查询险种保险帐户缴费表
		String sqlStr = "select * from LMRiskAccPay where RiskCode='"
				+ "?s15?" + "' and payPlanCode='" + "?s16?" + "'";
		SQLwithBindVariables sqlbv25=new SQLwithBindVariables();
		sqlbv25.sql(sqlStr);
		sqlbv25.put("s15",riskCode);
		sqlbv25.put("s16",payPlanCode);
		LMRiskAccPaySchema tLMRiskAccPaySchema = new LMRiskAccPaySchema();
		LMRiskAccPaySet tLMRiskAccPaySet = new LMRiskAccPaySet();
		LMRiskAccPayDB tLMRiskAccPayDB = tLMRiskAccPaySchema.getDB();
		tLMRiskAccPaySet = tLMRiskAccPayDB.executeQuery(sqlbv25);
		if (tLMRiskAccPayDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMRiskAccPayDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRiskAccPay";
			tError.errorMessage = "险种保险帐户缴费表查询失败!";
			this.mErrors.addOneError(tError);
			tLMRiskAccPaySet.clear();

			return null;
		}
		if (tLMRiskAccPaySet == null || tLMRiskAccPaySet.size() == 0) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMRiskAccPayDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRiskAccPay";
			tError.errorMessage = "险种保险帐户缴费表没有查询到相关数据!";
			this.mErrors.addOneError(tError);
			tLMRiskAccPaySet.clear();

			return null;
		}

		return tLMRiskAccPaySet;
	}

	/**
	 * 查询险种保险帐户(类型1)
	 * 
	 * @param InsuAccNo
	 * @return
	 */
	private LMRiskInsuAccSet queryLMRiskInsuAccSet(String InsuAccNo) {
		String sqlStr = "select * from LMRiskInsuAcc where InsuAccNo='"
				+ "?s16?" + "'";
		SQLwithBindVariables sqlbv26=new SQLwithBindVariables();
		sqlbv26.sql(sqlStr);
		sqlbv26.put("s16",InsuAccNo);
		LMRiskInsuAccSchema tLMRiskInsuAccSchema = new LMRiskInsuAccSchema();
		LMRiskInsuAccSet tLMRiskInsuAccSet = new LMRiskInsuAccSet();
		LMRiskInsuAccDB tLMRiskInsuAccDB = tLMRiskInsuAccSchema.getDB();
		tLMRiskInsuAccSet = tLMRiskInsuAccDB.executeQuery(sqlbv26);
		if (tLMRiskInsuAccDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMRiskInsuAccDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRiskInsuAcc";
			tError.errorMessage = "险种保险帐户表查询失败!";
			this.mErrors.addOneError(tError);
			tLMRiskInsuAccSet.clear();

			return null;
		}
		if (tLMRiskInsuAccSet.size() == 0) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMRiskInsuAccDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRiskInsuAcc";
			tError.errorMessage = "险种保险帐户表没有查询到相关数据!";
			this.mErrors.addOneError(tError);
			tLMRiskInsuAccSet.clear();

			return null;
		}

		return tLMRiskInsuAccSet;
	}

	/***************************************************************************
	 * @todo GaoHt Add For NCL
	 * 
	 **************************************************************************/

	public VData addPremAccBonus(LCPremSchema pLCPremSchema, String OtherNo,
			String OtherNoType, String MoneyType, Double Rate, String mOperate,
			String PayNo) {
		if ((pLCPremSchema == null) || (mOperate == null) || (OtherNo == null)
				|| (OtherNoType == null) || (MoneyType == null)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "addPrem";
			tError.errorMessage = "传入数据不能为空";
			this.mErrors.addOneError(tError);

			return null;
		}
		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
		LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
		LCInsureAccFeeSet tLCInsureAccFeeSet = new LCInsureAccFeeSet();
		LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
		LCInsureAccFeeTraceSet tLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
		LCPremToAccSet tLCPremToAccSet = new LCPremToAccSet();
		// 判断是否帐户相关
		VData mResult = new VData();
		if (pLCPremSchema.getNeedAcc().equals("1")) {
			tLCPremToAccSet = queryLCPremToAccSet(pLCPremSchema);
			for (int i = 1; i <= tLCPremToAccSet.size(); i++) { // 循环交费项账户表来判断是否要新建账户分类表的信息。
				LCPremToAccSchema tLCPremToAccSchema = tLCPremToAccSet.get(i);
				LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();
				tLMRiskInsuAccDB
						.setInsuAccNo(tLCPremToAccSchema.getInsuAccNo());
				tLMRiskInsuAccDB.getInfo();
				String polno = pLCPremSchema.getPolNo();
				String insuaccno = tLCPremToAccSchema.getInsuAccNo();
				String payplancode = pLCPremSchema.getPayPlanCode();

				// 账户分类表
				LCInsureAccClassSchema tLCInsureAccClassSchema = this
						.queryLCInsureAccClass(polno, insuaccno, payplancode,
								OtherNo);
				tLCInsureAccClassSchema.setOtherNo(PayNo);
				tLCInsureAccClassSchema.setMakeDate(PubFun.getCurrentDate());
				tLCInsureAccClassSchema.setMakeTime(PubFun.getCurrentTime());
				tLCInsureAccClassSchema.setModifyDate(PubFun.getCurrentDate());
				tLCInsureAccClassSchema.setModifyTime(PubFun.getCurrentTime());

				// 管理费主表
				LCInsureAccFeeSchema tLCInsureAccFeeSchema = this
						.queryLCInsureAccFee(polno, insuaccno); // 得到管理费账户主表的记录
				tLCInsureAccFeeSchema.setModifyDate(PubFun.getCurrentDate());
				tLCInsureAccFeeSchema.setModifyTime(PubFun.getCurrentTime());

				// 管理费
				LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema = this
						.queryLCInsrueAccClassFee(polno, insuaccno,
								payplancode, OtherNo); // 得到管理费账户分类表的记录
				tLCInsureAccClassFeeSchema.setOtherNo(PayNo);
				tLCInsureAccClassFeeSchema.setMakeDate(PubFun.getCurrentDate());
				tLCInsureAccClassFeeSchema.setModifyTime(PubFun
						.getCurrentTime());
				tLCInsureAccClassFeeSchema.setModifyDate(PubFun
						.getCurrentDate());
				tLCInsureAccClassFeeSchema.setModifyTime(PubFun
						.getCurrentTime());
				LCInsureAccSchema tLCInsureAccSchema = this.queryLCInsureAcc(
						polno, insuaccno); // 得到账户的记录
				tLCInsureAccClassSet.add(tLCInsureAccClassSchema); // 得到账户分类表的记录
				tLCInsureAccSet.add(tLCInsureAccSchema);
				tLCInsureAccFeeSet.add(tLCInsureAccFeeSchema);
				tLCInsureAccClassFeeSet.add(tLCInsureAccClassFeeSchema);
			}

			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(pLCPremSchema.getPolNo());
			tLCPolDB.getInfo();
			LCPremSet tLCPremSet = new LCPremSet();
			tLCPremSet.add(pLCPremSchema);
			VData vData = new VData();
			vData.add(tLCPremSet);
			vData.add(tLCPremToAccSet);
			vData.add(tLCPolDB.getSchema());
			vData.add(tLCInsureAccSet);
			vData.add(tLCInsureAccClassSet);
			vData.add(tLCInsureAccTraceSet);
			vData.add(tLCInsureAccFeeSet);
			vData.add(tLCInsureAccClassFeeSet);
			vData.add(tLCInsureAccFeeTraceSet);
			CManageFee managFee = new CManageFee();
			boolean ble = managFee.calPremManaFee(vData, "2", OtherNo,
					OtherNoType, MoneyType); // 计算管理费.
			if (ble == false) {
				CError tError = new CError();
				tError.moduleName = "DealAccount";
				tError.functionName = "addPremAcc";
				tError.errorMessage = "计算管理费失败";
				this.mErrors.addOneError(tError);
				return null;
			}
		}
		mResult.add(tLCInsureAccSet);
		mResult.add(tLCInsureAccClassSet);
		mResult.add(tLCInsureAccTraceSet);
		mResult.add(tLCInsureAccFeeSet);
		mResult.add(tLCInsureAccClassFeeSet);
		mResult.add(tLCInsureAccFeeTraceSet);
		return mResult;
	}
}
