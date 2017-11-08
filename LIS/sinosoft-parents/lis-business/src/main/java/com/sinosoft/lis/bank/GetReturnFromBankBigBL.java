package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sinosoft.lis.bq.PEdorAutoConfirmBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJSPayGrpDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.operfee.RNHangUp;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContHangUpStateSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCCustomerAccTraceSchema;
import com.sinosoft.lis.schema.LDBankSchema;
import com.sinosoft.lis.schema.LJSPayGrpSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LYReturnFromBankBSchema;
import com.sinosoft.lis.schema.LYReturnFromBankSchema;
import com.sinosoft.lis.schema.LYSendToBankSchema;
import com.sinosoft.lis.vschema.LCContHangUpStateSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCCustomerAccTraceSet;
import com.sinosoft.lis.vschema.LJSPayGrpSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LYReturnFromBankBSet;
import com.sinosoft.lis.vschema.LYReturnFromBankSet;
import com.sinosoft.lis.vschema.LYSendToBankSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.RSWrapper;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * 
 * <p>
 * Description: 银行数据转换到业务系统
 * </p>
 * 此类是文件返回处理的程序。这个程序的主要功能是将文件返回后的批次作最后的金额确认， 和对多个表的操作的将相应的值插如到相应的表中。
 * 
 * 续期催收（7）
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author yangyf
 * @version 1.0
 */

public class GetReturnFromBankBigBL {
private static Logger logger = Logger.getLogger(GetReturnFromBankBigBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	// 业务数据
	private String totalMoney = "";
	private int sumNum = 0;
	private String serialNo = "";
	private Reflections mReflections = new Reflections();
	private GlobalInput mGlobalInput = new GlobalInput();

	private LYReturnFromBankSchema inLYReturnFromBankSchema = new LYReturnFromBankSchema();
	private TransferData inTransferData = new TransferData();

	private LCContHangUpStateSet mLCContHangUpStateSet = new LCContHangUpStateSet();

	private LYReturnFromBankSet outDelLYReturnFromBankSet = new LYReturnFromBankSet();

	private LJSPaySet updateLJSPaySet = new LJSPaySet();
	private LJTempFeeSet outLJTempFeeSet = new LJTempFeeSet();
	private LJTempFeeSet updateLJTempFeeSet = new LJTempFeeSet();
	private LJTempFeeClassSet updateLJTempFeeClassSet = new LJTempFeeClassSet();
	private LJSPaySet outLJSPaySet = new LJSPaySet();
	private LYReturnFromBankBSet outLYReturnFromBankBSet = new LYReturnFromBankBSet();
	private LYSendToBankSet outDelLYSendToBankSet = new LYSendToBankSet();
	private LJTempFeeClassSet outLJTempFeeClassSet = new LJTempFeeClassSet();
	private LJTempFeeSet outUpdateLJTempFeeSet = new LJTempFeeSet();
	private LJTempFeeClassSet outUpdateLJTempFeeClassSet = new LJTempFeeClassSet();

	private LCContSet mLCCont = new LCContSet();
	private LCCustomerAccTraceSet minsertCustomerAcc = new LCCustomerAccTraceSet();
	private LOPRTManagerSet outLOPRTManagerSet = new LOPRTManagerSet();

	private String mFirstPayFlag = "0";
	private String mXQPayFlag = "0";
	// 创建对数据库的连接 ，完成对数据的统一提交
	private Connection coon = null;
	private Statement stat = null;
	private ResultSet rs = null;
	private String mBankCode = "";
	boolean tRun = false; // 代表数据运行,并无错误返回i
	boolean BQVER = false;
	private String bankSuccFlag = "";
	private String tErrorContNo = "";

	// 为通知置盘提供判断
	private boolean mLjManagecom = false;
	private String mBankManage = "";

	public GetReturnFromBankBigBL() {
	}

	// 公共调用关闭数据库的连接的方法
	public void closeData() {
		try {
			stat.close();
			coon.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"GETMONEY"和"PAYMONEY"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("---End dealData---");

		logger.debug("End GetReturnFromBankBig BLS Submit...");

		return true;
	}

	/**
	 * =================如果是保全代收将在最后一步进行保全的确认==================
	 * 
	 */
	private void BQverify(LYReturnFromBankBSchema inLYReturnFromBankBSchema) {

		PEdorAutoConfirmBL tPEdorAutoConfirmBL = new PEdorAutoConfirmBL(
				mGlobalInput);
		tPEdorAutoConfirmBL.AutoConfirm(inLYReturnFromBankBSchema.getPolNo());

	}

	/**
	 * ==================将传入本类的置初始化====================================
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			inLYReturnFromBankSchema = (LYReturnFromBankSchema) mInputData
					.getObjectByObjectName("LYReturnFromBankSchema", 0);
			inTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);

			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GetReturnFromBankBigBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * ======================这里开始逻辑处理，完成数据的基本校验=====================
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {

		int i;
		LYReturnFromBankSet tLYReturnFromBankSet = new LYReturnFromBankSet();
		try {
			// 记录批次号
			String tTempSerialno = "";
			// 创建数据库的连接
			coon = DBConnPool.getConnection();
			stat = coon.createStatement();
			// 银行代收
			if (mOperate.equals("GETMONEY")) {
				serialNo = inLYReturnFromBankSchema.getSerialNo();
				tTempSerialno = serialNo;
				totalMoney = (String) inTransferData
						.getValueByName("totalMoney");

				/** ************校验要处理的批次是否已经做了文件返回***************** */
				if (!SerialnoExists(serialNo)) {
					throw new NullPointerException("无银行返回数据！");
				}
				// 判断该银行是否是统一扣划的银行
				SSRS tSSRS = new SSRS();
				ExeSQL tExeSQL = new ExeSQL();
				String t1Sql = "select codealias  from ldcode1 where codetype = 'BankBigList' and code = '"
						+ "?code?"
						+ "'"
						+ " and code1 = 'Y' and comcode = '"
						+ "?comcode?" + "'";
				SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
				sqlbv1.sql(t1Sql);
				sqlbv1.put("code", mBankCode);
				sqlbv1.put("comcode", mGlobalInput.ManageCom);
				tSSRS = tExeSQL.execSQL(sqlbv1);
				if (tSSRS.getMaxRow() > 0) {
					mLjManagecom = true;
					mBankManage = tSSRS.GetText(1, 1);
				}

				/** ****************在数据处理之前提前的计算输入的总的金额是否于回盘成功的总金额相等 */

				// 校验是否已做了文件返回,如果无则提示未作文件返回
				// 校验银行扣款成功与否并设置成功总的金额值
				if (!verifyBankSucc(serialNo, totalMoney)) {
					throw new NullPointerException("财务总金额确认失败！请与银行对帐！");
				}
				logger.debug("---End verifyBankSucc---");
				logger.debug("---End confirmTotalMoney---");
				logger.debug("---End confirmTotalMoney---");

				/** ****************在此处开始基本的数据处理由于数据过大，没5000一处理******** */
				RSWrapper rsWrapper = new RSWrapper();
				String tSql = "select * from lyreturnfrombank  where serialno= '"
						+ "?serialno?" + "'";
				SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
				sqlbv11.sql(tSql);
				sqlbv11.put("serialno", serialNo);
				logger.debug(tSql);
				if (!rsWrapper.prepareData(tLYReturnFromBankSet, sqlbv11)) {
					this.mErrors.copyAllErrors(rsWrapper.mErrors);
					logger.debug(rsWrapper.mErrors.getFirstError());
					throw new NullPointerException("查询数据失败");
				}

				do {
					logger.debug("Start getData....");
					rsWrapper.getData();
					if (!dealTableData(tLYReturnFromBankSet)) {
						return false;
					}

					tRun = true;
				} while (tLYReturnFromBankSet.size() > 0);
				// 生成银行日志表数据
				rsWrapper.close();

				logger.debug("---End verifyLJSPay---");

				// 备份返回盘数据到返回盘B表
				if (tRun) {
					logger.debug("---End getLYReturnFromBankB---");

					// 获取发送盘数据，用于删除
					logger.debug("---End getLYSendToBank---");

					// 记录日志
					getLYBankLog();
					logger.debug("---End verifyUnitMoney---");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			closeData();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GetReturnFromBankBigBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误! " + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}
		closeData();
		return true;
	}

	// ======================循环获得银行回盘数据的备份表=======================
	private LYReturnFromBankBSchema getLYReturnFromBankB(
			LYReturnFromBankSchema tLYReturnFromBankSchema) {
		int i;

		LYReturnFromBankBSchema tLYReturnFromBankBSchema = new LYReturnFromBankBSchema();
		if (!bankSuccFlag.equals(tLYReturnFromBankSchema.getBankSuccFlag())) {
			tLYReturnFromBankSchema.setConvertFlag("1");
		} else {
			tLYReturnFromBankSchema.setConvertFlag("5");
			tLYReturnFromBankSchema.setBankSuccFlag("0000");
		}
		mReflections.transFields(tLYReturnFromBankBSchema,
				tLYReturnFromBankSchema);

		// 因为没有设计操作员字段，所以暂时保存在备注字段中，add by Minim at 2004-2-5
		tLYReturnFromBankBSchema.setRemark(mGlobalInput.Operator);
		tLYReturnFromBankBSchema.setModifyDate(PubFun.getCurrentDate());
		tLYReturnFromBankBSchema.setModifyTime(PubFun.getCurrentTime());
		return tLYReturnFromBankBSchema;
	}

	/**
	 * =======================对表的操作=====================
	 * 
	 * @param tLJSPaySet
	 * @return
	 */
	public boolean dealTableData(LYReturnFromBankSet tLYReturnFromBankSet) {
		LYReturnFromBankBSchema tLYReturnFromBankBSchema = null;
		LJSPaySchema tLJSPaySchema = null;
		for (int i = 1; i <= tLYReturnFromBankSet.size(); i++) {
			mXQPayFlag = "";
			mFirstPayFlag = "";
			tLYReturnFromBankBSchema = new LYReturnFromBankBSchema();
			tLJSPaySchema = new LJSPaySchema();
			tLYReturnFromBankBSchema = getLYReturnFromBankB(tLYReturnFromBankSet
					.get(i).getSchema());

			// 校验应收表

			verifyLJSPay(tLYReturnFromBankBSchema);

			// 调用此方法将需要提交的数据统一的装入到一容器中

			prepareData();

		}
		logger.debug("---End getLJSPay---");

		return true;
	}

	/**
	 * ================下面开始业务处理的基本逻辑的独立的方法======================
	 * 获取交费日期在设置的日期区间内的总应付表记录
	 * 
	 * @param startDate
	 * 
	 * 
	 * 获取银行扣款成功标志信息
	 * @param tLYReturnFromBankSchema
	 * @return
	 */
	private String getBankSuccFlag() {
		try {
			LDBankSchema tLDBankSchema = new LDBankSchema();

			tLDBankSchema.setBankCode(mBankCode.trim());
			tLDBankSchema.setSchema(tLDBankSchema.getDB().query().get(1));

			return tLDBankSchema.getAgentPaySuccFlag();
		} catch (Exception e) {
			e.printStackTrace();
			throw new NullPointerException("获取银行扣款成功标志信息失败！(getBankSuccFlag) "
					+ e.getMessage());
		}
	}

	/**
	 * *校验这个批次是否已做了文件返回
	 * 
	 */
	public boolean SerialnoExists(String Serialno) {
		try {
			String tSql = "select bankcode from lyreturnfrombank where serialno = '"
					+ Serialno + "'";
//			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
//			sqlbv2.sql(tSql);
//			sqlbv2.put("serialno", Serialno);
			logger.debug("============" + tSql);
			rs = stat.executeQuery(tSql);
			while (rs.next()) {

				mBankCode = rs.getString(1);
				rs.close();
				return true;
			}

			rs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return false;
		}
		return false;
	}

	/**
	 * ==========================校验金额的正确如果不正确则返回错误置 ======================
	 * 校验银行扣款成功与否并校验成功的金额是否正确
	 */
	private boolean verifyBankSucc(String Serialno, String mTotoalMoney) {
		// 定义连接数据库类
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		int i;
		bankSuccFlag = getBankSuccFlag();
		String[] arrSucc = PubFun.split(bankSuccFlag, ";");
		bankSuccFlag = arrSucc[0];
		try {

			double sumMoney = Double.valueOf(mTotoalMoney).doubleValue();

			double checkMoney = 0;
			// 如果返盘一半发生异常，则需要将lyreturnfrombankb 的sum(paymoney)减去
			// 通过SQL计算此批次总的金额
			String tPayMoney = "select sum(paymoney) from lyreturnfrombank  where "
					+ " banksuccflag = '"
					+ "?banksuccflag?"
					+ "' and serialno = '"
					+ "?serialno1?" + "'";
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql(tPayMoney);
			sqlbv3.put("banksuccflag", bankSuccFlag);
			sqlbv3.put("serialno1", Serialno);
			String tPayMoneyb = "select sum(paymoney) from lyreturnfrombankb  where "
					+ " banksuccflag = '0000' and serialno = '"
					+ "?serialno2?"
					+ "'";
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(tPayMoneyb);
			sqlbv4.put("banksuccflag", bankSuccFlag);
			sqlbv4.put("serialno2", Serialno);
			tSSRS = tExeSQL.execSQL(sqlbv3);
			if (tSSRS.getMaxRow() > 0 && tSSRS != null
					&& !tSSRS.GetText(1, 1).equals("null")
					&& !tSSRS.GetText(1, 1).equals("")) {
				checkMoney = Double.valueOf(tSSRS.GetText(1, 1)).doubleValue();
			}

			tSSRS = tExeSQL.execSQL(sqlbv4);
			if (tSSRS.getMaxRow() > 0 && tSSRS != null
					&& !tSSRS.GetText(1, 1).equals("null")
					&& !tSSRS.GetText(1, 1).equals("")) {
				checkMoney = checkMoney
						+ Double.valueOf(tSSRS.GetText(1, 1)).doubleValue();
			}

			// 校验准的金额
			if (checkMoney - sumMoney > 0.0001
					|| checkMoney - sumMoney < -0.0001) {
				return false;
			}
		} catch (Exception ex) {

			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 获取发送盘数据，用于删除
	 */
	private void getLYSendToBank(String paycode) {
		LYSendToBankSchema tLYSendToBankSchema = new LYSendToBankSchema();

		String tSql = "select * from lysendtobank where paycode = '" + "?paycode?"
				+ "' and serialno = '" + "?serialno3?" + "' ";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(tSql);
		sqlbv5.put("paycode", paycode);
		sqlbv5.put("serialno3", serialNo);
		outDelLYSendToBankSet = tLYSendToBankSchema.getDB().executeQuery(sqlbv5);
	}

	/**
	 * 获取发送盘数据，用于删除
	 */
	private void getLYReturnFromBank(String paycode) {
		LYReturnFromBankSchema tLYReturnFromBankSchema = new LYReturnFromBankSchema();

		String tSql = "select * from lyreturnfrombank where paycode = '"
				+ "?paycode1?" + "' and serialno = '" + "?serialno4?" + "' ";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(tSql);
		sqlbv6.put("paycode1", paycode);
		sqlbv6.put("serialno4", serialNo);
		outDelLYReturnFromBankSet = tLYReturnFromBankSchema.getDB()
				.executeQuery(sqlbv6);
	}

	/**
	 * 校验暂交费表
	 * 
	 * @param tLYReturnFromBankSchema
	 * @return
	 */
	private boolean verifyLJTempFee(
			LYReturnFromBankSchema tLYReturnFromBankSchema) {
		LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();

		tLJTempFeeSchema.setTempFeeNo(tLYReturnFromBankSchema.getPayCode());
		LJTempFeeSet tLJTempFeeSet = tLJTempFeeSchema.getDB().query();

		if (tLJTempFeeSet.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 通过银行返回数据获取应收表数据
	 * 
	 * @param tLYReturnFromBankSchema
	 * @return
	 */
	private LJSPaySchema getLJSPayByLYReturnFromBank(
			LYReturnFromBankBSchema tLYReturnFromBankBSchema) {
		try {
			LJSPaySchema tLJSPaySchema = new LJSPaySchema();

			tLJSPaySchema.setGetNoticeNo(tLYReturnFromBankBSchema.getPayCode());
			return tLJSPaySchema.getDB().query().get(1);
		} catch (Exception e) {
			// 总应收表无数据，表示扣款失败，
			throw new NullPointerException("此保单应收记录没有 "
					+ tLYReturnFromBankBSchema.getPolNo() + e.getMessage());

		}
	}

	private void setLJTempFeeCont(
			LYReturnFromBankBSchema tLYReturnFromBankBSchema) {
		boolean tTempFlag = false;
		if (!checkTempFee(tLYReturnFromBankBSchema)) {
			tTempFlag = true;
		}

		LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		LJSPaySchema tLJSPaySchema = getLJSPayByLYReturnFromBank(tLYReturnFromBankBSchema);
		String tLimit = PubFun.getNoLimit(tLJSPaySchema.getManageCom());
		String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);

		if (tLJSPaySchema.getOtherNoType().equals("1")
				|| tLJSPaySchema.getOtherNoType().equals("5")) // 集体保单号
		{
			LJSPayGrpDB tLJSPayGrpDB = new LJSPayGrpDB();
			LJSPayGrpSet tLJSPayGrpSet = new LJSPayGrpSet();
			tLJSPayGrpDB.setGetNoticeNo(tLJSPaySchema.getGetNoticeNo());
			tLJSPayGrpSet = tLJSPayGrpDB.query();
			for (int i = 1; i <= tLJSPayGrpSet.size(); i++) {
				LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
				LJSPayGrpSchema tLJSPayGrpSchema = new LJSPayGrpSchema();
				tLJSPayGrpSchema = tLJSPayGrpSet.get(i);
				tLJTempFeeSchema.setTempFeeNo(tLYReturnFromBankBSchema
						.getPayCode());
				// 首期交费
				if (tLYReturnFromBankBSchema.getDoType().equals("6")) {
					tLJTempFeeSchema.setTempFeeType("1");
					tLJTempFeeSchema.setOtherNoType("6");
				} else if (tLYReturnFromBankBSchema.getDoType().equals("7")) {

					tLJTempFeeSchema.setTempFeeType("1");
					tLJTempFeeSchema.setOtherNoType("7");
				} else if (tLYReturnFromBankBSchema.getDoType().equals("0")) {

					tLJTempFeeSchema.setTempFeeType("1");
					tLJTempFeeSchema.setOtherNoType("0");
				}
				// 续期
				else if (tLYReturnFromBankBSchema.getDoType().equals("2")) {

					tLJTempFeeSchema.setTempFeeType("2");
					tLJTempFeeSchema.setOtherNoType("2");
				}
				// 银代
				else if (tLYReturnFromBankBSchema.getDoType().equals("3")) {

					tLJTempFeeSchema.setTempFeeType("2");
					tLJTempFeeSchema.setOtherNoType("3");
				} else if (tLYReturnFromBankBSchema.getDoType().equals("10")) {

					tLJTempFeeSchema.setTempFeeType("4");
					tLJTempFeeSchema.setOtherNoType("10");
				} else if (tLYReturnFromBankBSchema.getDoType().equals("8")) {

					tLJTempFeeSchema.setTempFeeType("2");
					tLJTempFeeSchema.setOtherNoType("8");
				}

				tLJTempFeeSchema.setRiskCode(tLJSPayGrpSchema.getRiskCode());
				tLJTempFeeSchema.setPayIntv(tLJSPayGrpSchema.getPayIntv());
				tLJTempFeeSchema.setOtherNo(tLJSPayGrpSchema.getGrpPolNo());
				tLJTempFeeSchema.setPayMoney(tLJSPayGrpSchema
						.getSumDuePayMoney());
				tLJTempFeeSchema.setPayDate(tLYReturnFromBankBSchema
						.getSendDate());
				tLJTempFeeSchema.setEnterAccDate(PubFun.getCurrentDate());

				tLJTempFeeSchema.setManageCom(tLJSPayGrpSchema.getManageCom());
				tLJTempFeeSchema
						.setAgentGroup(tLJSPayGrpSchema.getAgentGroup());
				tLJTempFeeSchema.setAgentCode(tLJSPayGrpSchema.getAgentCode());
				tLJTempFeeSchema.setConfFlag("0");
				tLJTempFeeSchema.setSerialNo(serNo);
				tLJTempFeeSchema.setOperator(mGlobalInput.Operator);
				tLJTempFeeSchema.setMakeDate(PubFun.getCurrentDate());
				tLJTempFeeSchema.setMakeTime(PubFun.getCurrentTime());
				tLJTempFeeSchema.setModifyDate(PubFun.getCurrentDate());
				tLJTempFeeSchema.setModifyTime(PubFun.getCurrentTime());
				tLJTempFeeSchema.setConfMakeDate(PubFun.getCurrentDate());
				if (mLjManagecom) {
					tLJTempFeeSchema.setManageCom(mBankManage);
				}

				if (tTempFlag) {
					updateLJTempFeeSet.add(tLJTempFeeSchema);
				} else {
					outLJTempFeeSet.add(tLJTempFeeSchema);
				}
			}
		} else if (tLJSPaySchema.getOtherNoType().equals("2")
				|| tLJSPaySchema.getOtherNoType().equals("3")
				|| tLJSPaySchema.getOtherNoType().equals("6")
				|| tLJSPaySchema.getOtherNoType().equals("10")
				|| tLJSPaySchema.getOtherNoType().equals("01")
				|| tLJSPaySchema.getOtherNoType().equals("02")
				|| tLJSPaySchema.getOtherNoType().equals("03")
				|| tLJSPaySchema.getOtherNoType().equals("8")
				|| tLJSPaySchema.getOtherNoType().equals("08")) // 个人保单号
		{
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String tSql = "select riskcode,otherno, sumduepaymoney , managecom from LJSPay where GetNoticeNo='"
					+ "?GetNoticeNo?" + "'";
			SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
			sqlbv7.sql(tSql);
			sqlbv7.put("GetNoticeNo", tLJSPaySchema.getGetNoticeNo());
			tSSRS = tExeSQL.execSQL(sqlbv7);
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
				tLJTempFeeSchema.setTempFeeNo(tLYReturnFromBankBSchema
						.getPayCode());
				// 首期直接交费
				if (tLYReturnFromBankBSchema.getDoType().equals("6")) {
					tLJTempFeeSchema.setTempFeeType("1");
					tLJTempFeeSchema.setOtherNoType("6");
				} else if (tLYReturnFromBankBSchema.getDoType().equals("1")) {
					// 首期事后交费
					if (tLYReturnFromBankBSchema.getNoType().equals("6")) {
						tLJTempFeeSchema.setTempFeeType("1");
						tLJTempFeeSchema.setOtherNoType("6");
					}
					// 正常续期催收
					else if (tLYReturnFromBankBSchema.getNoType().equals("2")) {
						tLJTempFeeSchema.setTempFeeType("2");

						tLJTempFeeSchema.setOtherNoType("2");
					} else if (tLYReturnFromBankBSchema.getNoType().equals("3")) {
						tLJTempFeeSchema.setTempFeeType("2");

						tLJTempFeeSchema.setOtherNoType("3");
					} else if (tLYReturnFromBankBSchema.getNoType().equals("8")) {
						tLJTempFeeSchema.setTempFeeType("2");

						tLJTempFeeSchema.setOtherNoType("8");
					}

					else if (tLYReturnFromBankBSchema.getNoType().equals("10")) {
						tLJTempFeeSchema.setTempFeeType("4");

						tLJTempFeeSchema.setOtherNoType("10");

					}
					// 正常续期预交
					else if (tLYReturnFromBankBSchema.getNoType().equals("01")) {
						tLJTempFeeSchema.setTempFeeType("3");

						tLJTempFeeSchema.setOtherNoType("01");

					} else if (tLYReturnFromBankBSchema.getNoType()
							.equals("02")) {
						tLJTempFeeSchema.setTempFeeType("3");

						tLJTempFeeSchema.setOtherNoType("02");

					} else if (tLYReturnFromBankBSchema.getNoType()
							.equals("03")) {
						tLJTempFeeSchema.setTempFeeType("3");

						tLJTempFeeSchema.setOtherNoType("03");

					} else if (tLYReturnFromBankBSchema.getNoType()
							.equals("08")) {
						tLJTempFeeSchema.setTempFeeType("3");

						tLJTempFeeSchema.setOtherNoType("08");

					}

				}
				// 查询应收表

				LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
				LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
				tLJSPayPersonDB.setGetNoticeNo(tLJSPaySchema.getGetNoticeNo());
				tLJSPayPersonDB.setContNo(tSSRS.GetText(i, 2));
				tLJSPayPersonSet = tLJSPayPersonDB.query();
				LJSPayPersonSchema tLJSPayPersonSchema = tLJSPayPersonSet
						.get(1);
				if (tSSRS.GetText(i, 1) != null
						&& !tSSRS.GetText(i, 1).equals("null")
						&& !tSSRS.GetText(i, 1).equals("")) {
					tLJTempFeeSchema.setRiskCode(tSSRS.GetText(i, 1));
				} else {
					tLJTempFeeSchema.setRiskCode("000000");
				}
				tLJTempFeeSchema.setOtherNo(tSSRS.GetText(i, 2));
				tLJTempFeeSchema.setPayMoney(tSSRS.GetText(i, 3));
				tLJTempFeeSchema.setPayDate(tLYReturnFromBankBSchema
						.getSendDate());

				tLJTempFeeSchema.setEnterAccDate(PubFun.getCurrentDate());
				if (tLYReturnFromBankBSchema.getNoType().equals("2")
						|| tLYReturnFromBankBSchema.getNoType().equals("3")
						|| tLYReturnFromBankBSchema.getNoType().equals("8")) {
					tLJTempFeeSchema.setPolicyCom(tLJSPayPersonSchema
							.getManageCom());
					tLJTempFeeClassSchema.setManageCom(tLJSPayPersonSchema
							.getManageCom());
				}

				if (tLYReturnFromBankBSchema.getNoType().equals("2")
						|| tLYReturnFromBankBSchema.getNoType().equals("3")
						|| tLYReturnFromBankBSchema.getNoType().equals("8")) {
					tLJTempFeeClassSchema.setPolicyCom(tLJSPayPersonSchema
							.getManageCom());
					tLJTempFeeClassSchema.setManageCom(tLJSPayPersonSchema
							.getManageCom());
				}

				if (!tLYReturnFromBankBSchema.getNoType().equals("10")
						&& !tLYReturnFromBankBSchema.getNoType().equals("01")
						&& !tLYReturnFromBankBSchema.getNoType().equals("02")
						&& !tLYReturnFromBankBSchema.getNoType().equals("03")
						&& !tLYReturnFromBankBSchema.getNoType().equals("08")) {
					tLJTempFeeSchema.setPayIntv(tLJSPayPersonSchema
							.getPayIntv());
					tLJTempFeeSchema.setManageCom(tLJSPayPersonSchema
							.getManageCom());

					tLJTempFeeSchema.setAgentGroup(tLJSPayPersonSchema
							.getAgentGroup());
					tLJTempFeeSchema.setAgentCode(tLJSPayPersonSchema
							.getAgentCode());
				} else if (tLYReturnFromBankBSchema.getNoType().equals("10")) {
					tLJTempFeeSchema.setManageCom(tLJSPaySchema.getManageCom());
					tLJTempFeeSchema.setPolicyCom(tLJSPaySchema.getManageCom());
				} else {
					tLJTempFeeSchema.setManageCom(mGlobalInput.ManageCom);
					tLJTempFeeClassSchema.setManageCom(mGlobalInput.ManageCom);
					tLJTempFeeClassSchema.setPolicyCom(tSSRS.GetText(i, 4));
					tLJTempFeeSchema.setPolicyCom(tSSRS.GetText(i, 4));
				}
				tLJTempFeeSchema.setConfFlag("0");
				tLJTempFeeSchema.setSerialNo(serNo);
				tLJTempFeeSchema.setOperator(mGlobalInput.Operator);
				tLJTempFeeSchema.setMakeDate(PubFun.getCurrentDate());
				tLJTempFeeSchema.setMakeTime(PubFun.getCurrentTime());
				tLJTempFeeSchema.setModifyDate(PubFun.getCurrentDate());
				tLJTempFeeSchema.setModifyTime(PubFun.getCurrentTime());
				tLJTempFeeSchema.setConfMakeDate(PubFun.getCurrentDate());
				if (mLjManagecom) {
					tLJTempFeeSchema.setManageCom(mBankManage);
					tLJTempFeeClassSchema.setManageCom(mBankManage);
				}
				if (tTempFlag) {
					updateLJTempFeeSet.add(tLJTempFeeSchema);
				} else {
					outLJTempFeeSet.add(tLJTempFeeSchema);

				}
			}
		}
		// 为暂交费分类表设置数据
		tLJTempFeeClassSchema.setTempFeeNo(tLYReturnFromBankBSchema
				.getPayCode());
		tLJTempFeeClassSchema.setPayMode("7");
		tLJTempFeeClassSchema.setPayMoney(tLYReturnFromBankBSchema
				.getPayMoney());
		tLJTempFeeClassSchema
				.setPayDate(tLYReturnFromBankBSchema.getSendDate());

		tLJTempFeeClassSchema.setEnterAccDate(PubFun.getCurrentDate());
		if (tLYReturnFromBankBSchema.getDoType().equals("6")) {
			tLJTempFeeClassSchema.setOtherNoType("6");
		} else if (tLYReturnFromBankBSchema.getDoType().equals("1")) {
			// 首期事后交费
			if (tLYReturnFromBankBSchema.getNoType().equals("6")) {
				tLJTempFeeClassSchema.setOtherNoType("6");
			}
			if (tLYReturnFromBankBSchema.getNoType().equals("7")) {
				tLJTempFeeClassSchema.setOtherNoType("7");
			}

			// 正常续期催收
			else if (tLYReturnFromBankBSchema.getNoType().equals("2")) {
				tLJTempFeeClassSchema.setOtherNoType("2");
			} else if (tLYReturnFromBankBSchema.getNoType().equals("3")) {
				tLJTempFeeClassSchema.setOtherNoType("3");
			} else if (tLYReturnFromBankBSchema.getNoType().equals("8")) {
				tLJTempFeeClassSchema.setOtherNoType("8");
			}

			else if (tLYReturnFromBankBSchema.getNoType().equals("10")) {
				tLJTempFeeClassSchema.setOtherNoType("10");

			}
			// 正常续期预交
			else if (tLYReturnFromBankBSchema.getNoType().equals("01")) {
				tLJTempFeeClassSchema.setOtherNoType("01");
			} else if (tLYReturnFromBankBSchema.getNoType().equals("02")) {
				tLJTempFeeClassSchema.setOtherNoType("02");
			} else if (tLYReturnFromBankBSchema.getNoType().equals("03")) {
				tLJTempFeeClassSchema.setOtherNoType("03");
			} else if (tLYReturnFromBankBSchema.getNoType().equals("08")) {
				tLJTempFeeClassSchema.setOtherNoType("08");
			}
		}

		tLJTempFeeClassSchema.setConfFlag("0");
		tLJTempFeeClassSchema.setSerialNo(serNo);
		tLJTempFeeClassSchema.setChequeNo("000000");
		tLJTempFeeClassSchema.setOperator(this.mGlobalInput.Operator);
		tLJTempFeeClassSchema.setMakeDate(PubFun.getCurrentDate());
		tLJTempFeeClassSchema.setMakeTime(PubFun.getCurrentTime());
		tLJTempFeeClassSchema.setModifyDate(PubFun.getCurrentDate());
		tLJTempFeeClassSchema.setModifyTime(PubFun.getCurrentTime());
		tLJTempFeeClassSchema.setManageCom(tLJSPaySchema.getManageCom());
		tLJTempFeeClassSchema.setPolicyCom(tLJSPaySchema.getManageCom());
		tLJTempFeeClassSchema.setBankCode(tLYReturnFromBankBSchema
				.getBankCode());
		tLJTempFeeClassSchema.setBankAccNo(tLYReturnFromBankBSchema.getAccNo());
		tLJTempFeeClassSchema.setAccName(tLYReturnFromBankBSchema.getAccName());
		tLJTempFeeClassSchema.setConfMakeDate(PubFun.getCurrentDate());
		tLJTempFeeClassSchema.setOtherNo(tLYReturnFromBankBSchema.getPolNo());
		if (mLjManagecom) {
			tLJTempFeeClassSchema.setManageCom(mBankManage);
		}

		if (tTempFlag) {
			updateLJTempFeeClassSet.add(tLJTempFeeClassSchema);
		} else {
			outLJTempFeeClassSet.add(tLJTempFeeClassSchema);

		}
	}

	/**
	 * 如果是续期预交就要在lccont中更新余额而且要在保单帐户表中置一个状态
	 * 
	 * @param tLYReturnFromBankSchema
	 */
	private void setLCContb(LYReturnFromBankBSchema tLYReturnFromBankBSchema) {
		LCContSchema tLCContSchema = new LCContSchema();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(tLYReturnFromBankBSchema.getPolNo());
		tLCContDB.getInfo();
		tLCContSchema = tLCContDB.getSchema();

		double tStartMoney = tLCContSchema.getDif()
				+ tLYReturnFromBankBSchema.getPayMoney();
		tLCContSchema.setDif(tStartMoney);
		mLCCont.add(tLCContSchema);
		// 给内部ACCNO取值
		ExeSQL tEXeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String tSql = "select max(accno) from LCCustomerAccTrace where customerno = '"
				+ "?customerno?" + "'";
		SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
		sqlbv8.sql(tSql);
		sqlbv8.put("customerno", tLCContSchema.getAppntNo());
		tSSRS = tEXeSQL.execSQL(sqlbv8);
		int tAccNo = 1;
		if (tSSRS.GetText(1, 1) != null && !tSSRS.GetText(1, 1).equals("null")
				&& !tSSRS.GetText(1, 1).equals("")) {
			tAccNo = Integer.parseInt(tSSRS.GetText(1, 1)) + 1;
		}
		LCCustomerAccTraceSchema tLCCustomer = new LCCustomerAccTraceSchema();
		tLCCustomer.setCustomerNo(tLCContSchema.getAppntNo());
		tLCCustomer.setCustomerType("1");
		tLCCustomer.setInsuAccNo(tLCContSchema.getContNo());
		tLCCustomer.setAccNo(tAccNo);
		tLCCustomer.setAccType("001");
		tLCCustomer.setAccHappenNo(tLYReturnFromBankBSchema.getPayCode());
		tLCCustomer.setOtherNoType(tLYReturnFromBankBSchema.getNoType());
		tLCCustomer.setOtherNo(tLCContSchema.getContNo());
		tLCCustomer.setOperationType("YS");
		tLCCustomer.setMoneyType("BF");
		tLCCustomer.setMoney(tStartMoney);
		tLCCustomer.setOperFlag("2");
		tLCCustomer.setOperator(mGlobalInput.Operator);
		tLCCustomer.setMakeDate(PubFun.getCurrentDate());
		tLCCustomer.setMakeTime(PubFun.getCurrentTime());
		tLCCustomer.setModifyDate(PubFun.getCurrentDate());
		tLCCustomer.setModifyTime(PubFun.getCurrentTime());
		minsertCustomerAcc.add(tLCCustomer);

		// 删除应收表数据

	}

	/**
	 * 转入暂交费表
	 * 
	 * @param tLYReturnFromBankSchema
	 */
	private void setLJTempFee(LYReturnFromBankBSchema tLYReturnFromBankBSchema) {
		LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
		LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		LJSPaySchema tLJSPaySchema = getLJSPayByLYReturnFromBank(tLYReturnFromBankBSchema);

		tLJTempFeeSchema.setTempFeeNo(tLYReturnFromBankBSchema.getPayCode());
		// 首期直接交费
		if (tLYReturnFromBankBSchema.getDoType().equals("2")) {
			tLJTempFeeSchema.setTempFeeType("1");
			tLJTempFeeSchema.setOtherNoType("0");
		} else if (tLYReturnFromBankBSchema.getDoType().equals("1")) {
			// 首期事后交费
			if (tLYReturnFromBankBSchema.getNoType().equals("6")) {
				tLJTempFeeSchema.setTempFeeType("1");
				tLJTempFeeSchema.setOtherNoType("0");
			}

			// 正常续期催收
			else if (tLYReturnFromBankBSchema.getNoType().equals("2")) {
				tLJTempFeeSchema.setTempFeeType("2");
				tLJTempFeeSchema.setOtherNoType("0");
			}
		}

		tLJTempFeeSchema.setOtherNo(tLJSPaySchema.getOtherNo());

		tLJTempFeeSchema.setEnterAccDate(PubFun.getCurrentDate());

		tLJTempFeeSchema.setConfMakeDate(PubFun.getCurrentDate());
		tLJTempFeeSchema.setConfFlag("0");
		// 流水号
		String tLimit = PubFun.getNoLimit(tLJSPaySchema.getManageCom());
		String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
		tLJTempFeeSchema.setSerialNo(serNo);
		tLJTempFeeSchema.setManageCom(tLJSPaySchema.getManageCom());
		tLJTempFeeSchema.setAgentGroup(tLJSPaySchema.getAgentGroup());
		tLJTempFeeSchema.setAgentCode(tLJSPaySchema.getAgentCode());

		tLJTempFeeSchema.setRiskCode(tLJSPaySchema.getRiskCode());
		tLJTempFeeSchema.setPayMoney(tLYReturnFromBankBSchema.getPayMoney());
		tLJTempFeeSchema.setPayDate(tLYReturnFromBankBSchema.getSendDate());
		tLJTempFeeSchema.setOperator(mGlobalInput.Operator);
		tLJTempFeeSchema.setMakeDate(PubFun.getCurrentDate());
		tLJTempFeeSchema.setMakeTime(PubFun.getCurrentTime());
		tLJTempFeeSchema.setModifyDate(PubFun.getCurrentDate());
		tLJTempFeeSchema.setModifyTime(PubFun.getCurrentTime());

		// 为暂交费分类表设置数据
		mReflections.transFields(tLJTempFeeClassSchema, tLJTempFeeSchema);
		tLJTempFeeClassSchema.setPayMode("4");
		tLJTempFeeClassSchema.setBankCode(tLYReturnFromBankBSchema
				.getBankCode());
		tLJTempFeeClassSchema.setBankAccNo(tLYReturnFromBankBSchema.getAccNo());
		tLJTempFeeClassSchema.setAccName(tLYReturnFromBankBSchema.getAccName());

		tLJTempFeeClassSchema.setEnterAccDate(PubFun.getCurrentDate());
		tLJTempFeeClassSchema.setConfMakeDate(PubFun.getCurrentDate());
		tLJTempFeeClassSchema.setConfFlag("0");

		// 流水号
		tLJTempFeeClassSchema.setSerialNo(serNo);
		tLJTempFeeClassSchema.setManageCom(tLJSPaySchema.getManageCom());

		outLJTempFeeSet.add(tLJTempFeeSchema);
		outLJTempFeeClassSet.add(tLJTempFeeClassSchema);
	}

	/**
	 * 更新暂交费表，适用于事后选择银行转账
	 * 
	 * @param tLYReturnFromBankSchema
	 */
	private void updateLJTempFee(
			LYReturnFromBankBSchema tLYReturnFromBankBSchema) {
		outUpdateLJTempFeeSet.clear();
		outUpdateLJTempFeeClassSet.clear();
		LJSPaySchema tLJSPaySchema = getLJSPayByLYReturnFromBank(tLYReturnFromBankBSchema);

		// 核销暂交费分类表，设置到帐日期
		LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
		// tLJTempFeeClassDB.setTempFeeNo(tLYReturnFromBankSchema.getPayCode());
		// tLJTempFeeClassDB.setPayMode("4"); //银行转账

		String tSql = "select * from ljtempfeeclass where 1=1 "
				+ " and tempfeeno = '" + "?tempfeeno?"
				+ "'" + " and paymode in ('7','9') ";
		SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
		sqlbv9.sql(tSql);
		sqlbv9.put("tempfeeno",tLYReturnFromBankBSchema.getPayCode()); 
		LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB
				.executeQuery(sqlbv9);

		for (int i = 0; i < tLJTempFeeClassSet.size(); i++) {
			LJTempFeeClassSchema tLJTempFeeClassSchema = tLJTempFeeClassSet
					.get(i + 1);

			// 修改到帐日期
			tLJTempFeeClassSchema.setEnterAccDate(PubFun.getCurrentDate());
			tLJTempFeeClassSchema.setConfMakeDate(PubFun.getCurrentDate());
			tLJTempFeeClassSchema.setModifyDate(PubFun.getCurrentDate());
			tLJTempFeeClassSchema.setModifyTime(PubFun.getCurrentTime());
			if (mLjManagecom) {
				tLJTempFeeClassSchema.setManageCom(mBankManage);
			}

		}

		// 校验该暂交费号是否已经全部到帐，是则设置暂交费表的到帐日期，否则不设置
		LJTempFeeClassDB tLJTempFeeClassDB2 = new LJTempFeeClassDB();
		String strSql = "select * from LJTempFeeClass where TempFeeNo='"
				+ "?TempFeeNo?"
				+ "' and PayMode not in ('7','9')";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSql);
		sqlbv.put("TempFeeNo",tLYReturnFromBankBSchema.getPayCode()); 
		LJTempFeeClassSet tLJTempFeeClassSet2 = tLJTempFeeClassDB2
				.executeQuery(sqlbv);

		boolean isAllEnterAcc = true;
		for (int j = 0; j < tLJTempFeeClassSet2.size(); j++) {
			if (tLJTempFeeClassSet2.get(j + 1).getEnterAccDate() == null) {
				isAllEnterAcc = false;
				break;
			}
		}

		if (isAllEnterAcc) {
			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
			tLJTempFeeDB.setTempFeeNo(tLYReturnFromBankBSchema.getPayCode());
			LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.query();

			for (int i = 0; i < tLJTempFeeSet.size(); i++) {
				LJTempFeeSchema tLJTempFeeSchema = tLJTempFeeSet.get(i + 1);

				// 修改到帐日期
				tLJTempFeeSchema.setEnterAccDate(PubFun.getCurrentDate());
				tLJTempFeeSchema.setConfMakeDate(PubFun.getCurrentDate());
				tLJTempFeeSchema.setModifyDate(PubFun.getCurrentDate());
				tLJTempFeeSchema.setModifyTime(PubFun.getCurrentTime());
				if (mLjManagecom) {
					tLJTempFeeSchema.setManageCom(mBankManage);
				}

			}

			outUpdateLJTempFeeSet.add(tLJTempFeeSet);
		}

		outUpdateLJTempFeeClassSet.add(tLJTempFeeClassSet);
	}

	/**
	 * ===================这里是对多张表的统一修改====================== 校验应收表
	 */
	private boolean verifyLJSPay(
			LYReturnFromBankBSchema inLYReturnFromBankBSchema) {
		int i;

		LJSPaySchema tTempLJSpay = new LJSPaySchema();
		LOPRTManagerSchema tLOPRTManagerSchenam = new LOPRTManagerSchema();
		String tOthernotype = "";
		try {

			tOthernotype = inLYReturnFromBankBSchema.getPolNo();
			/** *********下面的方法是为了在成功或不成功的时候要对应收表的不同操作************* */
			if (inLYReturnFromBankBSchema.getDealType().equals("S")) {
				// 如果在返回中银行处理标志不为成功，就要修改应收表的相应数据
				if (!inLYReturnFromBankBSchema.getBankSuccFlag().equals("0000")) {
					tTempLJSpay = setLJSPaySchema(inLYReturnFromBankBSchema,
							false);
					updateLJSPaySet.add(tTempLJSpay);

				} // 这段程序是为了当文件返回时做统一的更新bankonthewayflag
				else {
					if (inLYReturnFromBankBSchema.getNoType().equals("2")
							|| inLYReturnFromBankBSchema.getNoType()
									.equals("3")
							|| inLYReturnFromBankBSchema.getNoType().equals(
									"02")
							|| inLYReturnFromBankBSchema.getNoType().equals(
									"03")
							|| inLYReturnFromBankBSchema.getNoType().equals(
									"10")
							|| inLYReturnFromBankBSchema.getNoType()
									.equals("8")
							|| inLYReturnFromBankBSchema.getNoType().equals(
									"08")) {
						// 如果在银行回盘成功则需要将

						tTempLJSpay = setLJSPaySchema(
								inLYReturnFromBankBSchema, true);
					} else {
						tTempLJSpay = getLJSPayByLYReturnFromBank(inLYReturnFromBankBSchema);
					}
					outLJSPaySet.add(tTempLJSpay);
					// 删除本条的发盘表的数据 和回盘表的数据

				}

			}
			/** *********下面的方法是在处理每条数据时要将发盘表的数据和回盘表的数据删除掉************* */
			getLYSendToBank(inLYReturnFromBankBSchema.getPayCode());
			getLYReturnFromBank(inLYReturnFromBankBSchema.getPayCode());
			/** *********下面的方法是对打印表的数据的处理为的发各种各样的通知书********************** */
			// 此处的操作是在对暂缴费表和暂缴费分类表的操作。
			String tSendBankCount = sendBankCount(inLYReturnFromBankBSchema
					.getSchema());

			if ((inLYReturnFromBankBSchema.getNoType().equals("6")
					|| inLYReturnFromBankBSchema.getNoType().equals("7") || inLYReturnFromBankBSchema
					.getNoType().equals("0"))
					&& inLYReturnFromBankBSchema.getConvertFlag().equals("1")) // 首期交费需要核销暂收费表
			{
				if (tSendBankCount.equals("2") || tSendBankCount.equals("-2")) {
					mFirstPayFlag = "1";

					setLOPRTManager(inLYReturnFromBankBSchema, "15");
				}
				if (tSendBankCount.equals("5") || tSendBankCount.equals("-5")) {
					mFirstPayFlag = "1";

					setLOPRTManager(inLYReturnFromBankBSchema, "16");
				}
			}

			if ((inLYReturnFromBankBSchema.getNoType().equals("6")
					|| inLYReturnFromBankBSchema.getNoType().equals("7")
					|| inLYReturnFromBankBSchema.getNoType().equals("0")
					|| inLYReturnFromBankBSchema.getNoType().equals("02")
					|| inLYReturnFromBankBSchema.getNoType().equals("08") || inLYReturnFromBankBSchema
					.getNoType().equals("03"))
					&& !inLYReturnFromBankBSchema.getConvertFlag().equals("1")) // 首期交费需要核销暂收费表
			{
				// 更新暂交费表
				updateLJTempFee(inLYReturnFromBankBSchema);

				mFirstPayFlag = "1";
				// setLOPRTManager(tLYReturnFromBankSchema, "48");
			} else if ((inLYReturnFromBankBSchema.getNoType().equals("2")
					|| inLYReturnFromBankBSchema.getNoType().equals("8") || inLYReturnFromBankBSchema
					.getNoType().equals("3"))
					&& !inLYReturnFromBankBSchema.getConvertFlag().equals("1")) {
				// 生成暂收表数据
				setLJTempFeeCont(inLYReturnFromBankBSchema);
				// 生成划款成功通知书数据

				mXQPayFlag = "1";

				setLOPRTManager(inLYReturnFromBankBSchema, "48");
			} else if (inLYReturnFromBankBSchema.getNoType().equals("10")
					&& !inLYReturnFromBankBSchema.getConvertFlag().equals("1")) {
				setLOPRTManager(inLYReturnFromBankBSchema, "BQ49");
				setLJTempFeeCont(inLYReturnFromBankBSchema);

				mXQPayFlag = "1";
			} else if ((inLYReturnFromBankBSchema.getNoType().equals("01")
					|| inLYReturnFromBankBSchema.getNoType().equals("08")
					|| inLYReturnFromBankBSchema.getNoType().equals("02") || inLYReturnFromBankBSchema
					.getNoType().equals("03"))
					&& !inLYReturnFromBankBSchema.getConvertFlag().equals("1")) {
				setLJTempFeeCont(inLYReturnFromBankBSchema);
				setLCContb(inLYReturnFromBankBSchema);
				mXQPayFlag = "2";
			}

			// 当续期划款时 解除在生成 数据时的挂旗表
			if (inLYReturnFromBankBSchema.getNoType().equals("2")
					|| inLYReturnFromBankBSchema.getNoType().equals("3")
					|| inLYReturnFromBankBSchema.getNoType().equals("02")
					|| inLYReturnFromBankBSchema.getNoType().equals("03")
					|| inLYReturnFromBankBSchema.getNoType().equals("8")
					|| inLYReturnFromBankBSchema.getNoType().equals("08")) {
				setLCContHangUpStateSchema(inLYReturnFromBankBSchema);
			}
			outLYReturnFromBankBSet.add(inLYReturnFromBankBSchema);
		} catch (Exception e) {
			throw new NullPointerException("银行返回盘数据校验失败！保单号为 " + tOthernotype
					+ e.getMessage());
		}
		return true;
	}

	/**
	 * 
	 * @param tLYReturnFromBankSchema
	 *            LYReturnFromBankSchema 当续期银行划款失败的时候更新的应收表
	 * @return LJSPaySchema
	 */

	private LJSPaySchema setLJSPaySchema(
			LYReturnFromBankBSchema inLYReturnFromBankBSchema, boolean banksucc) {
		LJSPaySchema tLJSPaySchema = new LJSPaySchema();
		LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPayDB.setGetNoticeNo(String.valueOf(inLYReturnFromBankBSchema
				.getPayCode()));
		tLJSPayDB.getInfo();
		if (!banksucc) {
			tLJSPayDB.setBankOnTheWayFlag("0");
			tLJSPayDB.setBankSuccFlag("0");
		} else {
			tLJSPayDB.setBankOnTheWayFlag("0"); // 这个标签是等程序稳定后将其置为"0"
			tLJSPayDB.setBankSuccFlag("1");

		}
		tLJSPayDB.setApproveDate(PubFun.getCurrentDate());
		tLJSPayDB.setModifyDate(PubFun.getCurrentDate());
		tLJSPayDB.setModifyTime(PubFun.getCurrentTime());

		tLJSPaySchema = tLJSPayDB.getSchema();
		return tLJSPaySchema;

	}

	/**
	 * 生成打印管理表数据
	 * 
	 * @param tLJSPaySchema
	 * @param tEdorNo
	 * @param type
	 * @return
	 */

	private void setLCContHangUpStateSchema(
			LYReturnFromBankBSchema tLYReturnFromBankBSchema) {
		RNHangUp mrn = new RNHangUp(mGlobalInput);
		mLCContHangUpStateSet.clear();
		LCContHangUpStateSchema t = new LCContHangUpStateSchema();
		t = mrn.undoHangUp(tLYReturnFromBankBSchema.getPolNo());
		mLCContHangUpStateSet.add(t);

	}

	private void setLOPRTManager(
			LYReturnFromBankBSchema tLYReturnFromBankBSchema, String type) {
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

		String mLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
		String serNo = PubFun1.CreateMaxNo("PRTSEQNO", mLimit);
		tLOPRTManagerSchema.setPrtSeq(serNo);
		tLOPRTManagerSchema.setStandbyFlag4(tLYReturnFromBankBSchema
				.getPayCode());
		tLOPRTManagerSchema.setOtherNo(tLYReturnFromBankBSchema.getPolNo());
		if (tLYReturnFromBankBSchema.getNoType().equals("10")) {
			tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_EDORACCEPT); // 根据NoType判断代扣类型（保全）
		} else {
			tLOPRTManagerSchema.setOtherNoType(tLYReturnFromBankBSchema
					.getNoType()); // 根据NoType判断代扣类型（首期、续期、保全）
		}

		tLOPRTManagerSchema.setCode(type);
		tLOPRTManagerSchema
				.setStandbyFlag6(tLYReturnFromBankBSchema.getAccNo());
		tLOPRTManagerSchema.setManageCom(tLYReturnFromBankBSchema.getComCode());
		tLOPRTManagerSchema.setAgentCode(tLYReturnFromBankBSchema
				.getAgentCode());
		tLOPRTManagerSchema.setReqCom(mGlobalInput.ManageCom);
		tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
		tLOPRTManagerSchema.setPrtType("0"); // 前台打印
		tLOPRTManagerSchema.setStateFlag("0"); // 提交打印
		tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());

		outLOPRTManagerSet.add(tLOPRTManagerSchema);
	}

	/**
	 * 获取发送盘数据，用于删除
	 */

	// /**
	// * 获取应收表数据，修改银行在途标志
	// */
	// private void getLJSPay(LYReturnFromBankSet inLYReturnFromBankSet)
	// {
	// int i;
	//
	// for (i = 0; i < inLYReturnFromBankSet.size(); i++)
	// {
	// LYReturnFromBankSchema tLYReturnFromBankSchema =
	// inLYReturnFromBankSet.get(i + 1);
	//
	//
	// if (!tLYReturnFromBankSchema.getConvertFlag().equals("1"))
	// {
	//
	// LJSPaySchema tLJSPaySchema = getLJSPayByLYReturnFromBank(
	// tLYReturnFromBankSchema);
	// //总应收表无数据，表示扣款失败，并且已经用其它方式交费
	// if (tLJSPaySchema == null)
	// {
	// continue;
	// }
	//
	// tLJSPaySchema.setBankOnTheWayFlag("0");
	// tLJSPaySchema.setBankSuccFlag("0");
	// tLJSPaySchema.setApproveDate(PubFun.getCurrentDate());
	// tLJSPaySchema.setModifyDate(PubFun.getCurrentDate());
	// tLJSPaySchema.setModifyTime(PubFun.getCurrentTime());
	// outLJSPaySet.add(tLJSPaySchema);
	// }
	// }
	// }
	/**
	 * 生成银行日志数据
	 * 
	 * @param tLYSendToBankSchema
	 * @return
	 */
	private void getLYBankLog() {
		// 得到成功的笔数
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String tSQL = "select count(*) from lyreturnfrombankb where serialno = '"
				+ "?serialno?" + "' and banksuccflag = '0000'";
		SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
		sqlbv10.sql(tSQL);
		sqlbv10.put("serialno",serialNo); 
		tSSRS = tExeSQL.execSQL(sqlbv10);
		logger.debug("+++++++++++++++++++" + tSSRS.GetText(1, 1)
				+ "+++++++++++++++++++++++++");

		String tSql = " update lybanklog set AccTotalMoney = '" + totalMoney
				+ "'" + " , BankSuccMoney = '" + totalMoney
				+ "' , BankSuccNum = '" + tSSRS.GetText(1, 1) + "'"
				+ " , DealState = '1' , ModifyDate = '"
				+ PubFun.getCurrentDate() + "'" + " , ModifyTime = '"
				+ PubFun.getCurrentTime() + "' where serialno = '" + serialNo
				+ "'";
//		SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
//		sqlbv11.sql(tSql);
//		sqlbv11.put("AccTotalMoney",totalMoney); 
//		sqlbv11.put("BankSuccMoney", totalMoney);
//		sqlbv11.put("BankSuccNum", tSSRS.GetText(1, 1));
//		sqlbv11.put("ModifyDate", PubFun.getCurrentDate());
//		sqlbv11.put("ModifyTime", PubFun.getCurrentTime());
//		sqlbv11.put("serialno", serialNo);
		try {
			stat.execute(tSql);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return 如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareData() {
		mInputData = new VData();

		try {

			MMap map = new MMap();
			map.put(mLCContHangUpStateSet, "DELETE");

			map.put(outDelLYReturnFromBankSet, "DELETE");
			if (mFirstPayFlag.equals("1")) {
				map.put(outLJSPaySet, "DELETE");
			} else if (mXQPayFlag.equals("1")) {
				map.put(outLJSPaySet, "UPDATE");
				for (int i = 1; i <= outLJTempFeeSet.size(); i++) {
					logger.debug("tempfeeno=="
							+ outLJTempFeeSet.get(i).getTempFeeNo());
				}
				map.put(outLJTempFeeSet, "INSERT");

				map.put(outLJTempFeeClassSet, "INSERT");
			} else if (mXQPayFlag.equals("2")) {
				map.put(mLCCont, "UPDATE");
				map.put(outLJSPaySet, "DELETE");
				map.put(minsertCustomerAcc, "INSERT");

			}

			map.put(outLYReturnFromBankBSet, "INSERT");
			map.put(outDelLYSendToBankSet, "DELETE");
			map.put(updateLJSPaySet, "UPDATE");
			map.put(outUpdateLJTempFeeSet, "UPDATE");
			map.put(outUpdateLJTempFeeClassSet, "UPDATE");

			// map.put(outLJTempFeeSet, "UPDATE");
			// map.put(outLJTempFeeClassSet, "UPDATE");
			map.put(outLOPRTManagerSet, "INSERT");
			map.put(updateLJTempFeeSet, "UPDATE");
			map.put(updateLJTempFeeClassSet, "UPDATE");

			mInputData.add(map);
			PubSubmit tPubSubmit = new PubSubmit();

			if (!tPubSubmit.submitData(mInputData, "")) {
				tErrorContNo += outLYReturnFromBankBSet.get(1).getPolNo();
			} else {
				sumNum += 1;
				/** *********进行保全确认不需要传入任何的值进行确认*************************************** */

				if (outLYReturnFromBankBSet.get(1).getSchema().getNoType()
						.equals("10")
						&& !outLYReturnFromBankBSet.get(1).getSchema()
								.getConvertFlag().equals("1")) {
					BQverify(outLYReturnFromBankBSet.get(1).getSchema());
				}

			}

			mLCContHangUpStateSet.clear();

			outDelLYReturnFromBankSet.clear();
			outLJSPaySet.clear();

			outLJSPaySet.clear();
			outLJTempFeeSet.clear();
			outLJTempFeeClassSet.clear();

			mLCCont.clear();
			outLJSPaySet.clear();
			minsertCustomerAcc.clear();

			outLYReturnFromBankBSet.clear();
			outDelLYSendToBankSet.clear();
			updateLJSPaySet.clear();
			outUpdateLJTempFeeSet.clear();
			outUpdateLJTempFeeClassSet.clear();
			outLOPRTManagerSet.clear();
			updateLJTempFeeSet.clear();
			updateLJTempFeeClassSet.clear();

		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GetReturnFromBankBigBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	// 用来测验ljtempfee 和ljtempfeeclass中是否有paycode的值，为的单次etf的操作
	public boolean checkTempFee(LYReturnFromBankBSchema tLYReturnFromBankBSchema) {
		LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
		LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		tLJTempFeeClassDB.setTempFeeNo(tLYReturnFromBankBSchema.getPayCode());
		if (tLJTempFeeClassDB.query().size() == 0) {
			logger.debug("收据号没有被置在ljtempfeeclass,直接执行插入");
		} else {
			logger.debug("收据号被置在ljtempfeeclass,直接update");
			return false;

		}
		return true;
	}

	// 得到应收表中的数据发送银行的数量
	public String sendBankCount(LYReturnFromBankBSchema tLYReturnFromBankBSchema) {
		String sql = "select sendbankcount from ljspay where getnoticeno = '"
				+ "?getnoticeno?" + "'";
		SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
		sqlbv12.sql(sql);
		sqlbv12.put("getnoticeno",tLYReturnFromBankBSchema.getPayCode()); 
		ExeSQL tExeSQL = new ExeSQL();
		SSRS ssrs = new SSRS();
		ssrs = tExeSQL.execSQL(sqlbv12);
		return String.valueOf(ssrs.GetText(1, 1));
	}

	/**
	 * ================由于数据量过大但也要整体的提交，还是要将多条数据统一的装入到同一个容器中。==============
	 * 
	 * @return VData
	 */

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		GetReturnFromBankBigBL GetReturnFromBankBigBL1 = new GetReturnFromBankBigBL();
	}
}
