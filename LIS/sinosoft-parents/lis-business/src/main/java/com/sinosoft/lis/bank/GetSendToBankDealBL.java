package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.operfee.RNHangUp;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContHangUpStateSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LYBankLogSchema;
import com.sinosoft.lis.schema.LYSendToBankSchema;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LYSendToBankSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.RSWrapper;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:多业务银行代收数据生成类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author yafeng
 * @version 1.0
 */

public class GetSendToBankDealBL {
private static Logger logger = Logger.getLogger(GetSendToBankDealBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 传出数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	// 业务数据
	private String manageCom = "";

	private String startDate = "";

	private String endDate = "";

	private String bankCode = "";

	private String[] typeFlag = null;

	private double totalMoney = 0;

	private int sumNum = 0;

	private String serialNo = "";

	private GlobalInput mGlobalInput = new GlobalInput();

	// private LJSPaySet outLJSPaySet = new LJSPaySet () ;

	// private LYSendToBankSet outLYSendToBankSet = new LYSendToBankSet () ;

	// private LYBankLogSet outLYBankLogSet = new LYBankLogSet () ;

	private LYSendToBankSet reLYSendToBankSet = new LYSendToBankSet();

	// private LCContHangUpStateSchema mInsetLCContHangUpSchema = new
	// LCContHangUpStateSchema () ;

	// private LCContHangUpStateSchema mUpdateLCContHangUpSchema = new
	// LCContHangUpStateSchema () ;

	private int mTempSer = 0;

	private boolean tFlag = false;

	private double mTempMoney = 0;

	private double dTotalMoney = 0; // 定义送银行的批次的数目

	private int tSaveCount = 0; // 判断如果是第一条值有问题就不将修改总的笔数

	private boolean mCreateLybanklog = false; // 如果发生正确的提交的就需要产生一个正确的批次号

	// private boolean mDateOperate = false ;
	// private String mSDateOperate = "" ;

	private ExeSQL tExeSQL = new ExeSQL();
	private SSRS tSSRS = new SSRS();

	public GetSendToBankDealBL() {
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
		if (!FirstdealData()) {
			return false;
		}
		logger.debug("---End dealData---");
		mResult.add(serialNo);
		logger.debug("End GetSendToBankDealBL BLS Submit...");

		return true;
	}

	/**
	 * 校验该银行是否有发盘样式表，如果无，则返回错误信息
	 * 
	 * @param bankcode
	 * @return
	 */

	public boolean bankXSLCheck(String bankcode) {
		// 如果集中收费登陆机构只能是分公司
		// ExeSQL tExeSQL = new ExeSQL () ;
		// SSRS tSSRS = new SSRS () ;

		String tSQL = "select comcode,codealias from ldcode1 where code = '"
				+ "?code?" + "' And codetype='BankBigList'";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
        sqlbv1.sql(tSQL);
        sqlbv1.put("code", bankcode);
		tSSRS = tExeSQL.execSQL(sqlbv1);
		if (tSSRS.MaxRow > 0 && tSSRS.GetText(1, 1) != null
				&& !tSSRS.GetText(1, 1).equals("null")
				&& !tSSRS.GetText(1, 1).equals("")) {
			if (tSSRS.GetText(1, 1).equals(manageCom)) {
				logger.debug(tSSRS.GetText(1, 1) + "====="
						+ tSSRS.GetText(1, 2));
				tSQL = "select AgentPaySendF from ldbank where bankcode = '"
						+ "?bankcode?" + "' and comcode = '" + "?comcode?"
						+ "'";
				SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		        sqlbv2.sql(tSQL);
		        sqlbv2.put("bankcode", bankcode);
		        sqlbv2.put("comcode", tSSRS.GetText(1, 2));
				tSSRS = tExeSQL.execSQL(sqlbv2);
				if (tSSRS.MaxRow > 0 && tSSRS.GetText(1, 1) != null
						&& !tSSRS.GetText(1, 1).equals("null")
						&& !tSSRS.GetText(1, 1).equals("")) {
					return true;
				} else {
					throw new NullPointerException("无此银行划款文件格式，无法制盘！");
				}
			} else {
				throw new NullPointerException("该银行集中收费，请用二级机构代码做制盘！");
			}
		} else {
			tSQL = "select AgentPaySendF from ldbank where bankcode = '"
					+ "?bankcode1?" + "' and comcode = '" + "?comcode1?" + "'";
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
	        sqlbv3.sql(tSQL);
	        sqlbv3.put("bankcode1", bankcode);
	        sqlbv3.put("comcode1", manageCom);
			tSSRS = tExeSQL.execSQL(sqlbv3);
			if (tSSRS.MaxRow > 0 && tSSRS.GetText(1, 1) != null
					&& !tSSRS.GetText(1, 1).equals("null")
					&& !tSSRS.GetText(1, 1).equals("")) {
				return true;
			} else {
				throw new NullPointerException("无此银行划款文件格式，无法制盘！");
			}
		}
		// return false ;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {

		try {
			TransferData tTransferData = (TransferData) mInputData
					.getObjectByObjectName("TransferData", 0);
			startDate = (String) tTransferData.getValueByName("startDate");
			if (startDate == null || startDate.equals("null")
					|| startDate.equals("")) {
				startDate = "2005-09-03";
			}
			// logger.debug("=-=====================================startDate" +
			// startDate) ;
			endDate = (String) tTransferData.getValueByName("endDate");
			logger.debug("=-=====================================endDate"
					+ endDate + "=================startDate" + startDate);
			bankCode = (String) tTransferData.getValueByName("bankCode");
			logger.debug("=-===============================bankCode======"
							+ bankCode);

			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			manageCom = (String) mGlobalInput.ManageCom;
			logger.debug("=-=============================ManageCom========"
							+ manageCom);

			// 校验银行
			if (!bankXSLCheck(bankCode)) {
				throw new NullPointerException("无此银行划款文件格式，无法制盘！");
			}
			typeFlag = (String[]) tTransferData.getValueByName("typeFlag");
			logger.debug("=============需要开始操作的业务类型==================");

			logger.debug("=============需要开始操作的业务类型其中包括的业务类型的长度=="
					+ typeFlag.length);
			for (int i = 0; i < typeFlag.length; i++) {
				logger.debug("====" + i + "==" + typeFlag[i]);
			}

			// ManageCom = (String) tTransferData.getValueByName ("ManageCom") ;
			// logger.debug("=-=============================startDate========"+ ManageCom) ;
			// 在此处判断是否发生了相同银行的相同条件是否在置盘
			if (!estSerialno()) {
				throw new NullPointerException(
						"由于您的重复选择。您现在所选的条件已经有批次在置盘，请稍候去生成文件！");
			}

			// 生成送银行的批次号 如果在小事务生成的时候发生数据中断就将其用以前的批次重新生成

			String tSql = " select distinct serialno from lysendtobank a where not exists "
					+ " (select 'X' from lybanklog b where a.serialno = b.serialno "
					+ " and b.bankcode = '"
					+ "?serialno?"
					+ "') "
					+ " and bankcode = '" + "?serialno1?" + "'";
			// + "' group by serialno " ;
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
	        sqlbv4.sql(tSql);
	        sqlbv4.put("serialno", bankCode);
	        sqlbv4.put("serialno1", bankCode);
			tSSRS = tExeSQL.execSQL(sqlbv4);
			if (tSSRS.getMaxRow() > 0 && tSSRS != null
					&& !tSSRS.GetText(1, 1).equals("null")
					&& !tSSRS.GetText(1, 1).equals("")) {
				serialNo = tSSRS.GetText(1, 1);
			} else {
				serialNo = PubFun1.CreateMaxNo("1", 20);
			}
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GetSendToBankDealBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!" + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * ========================================================
	 * 判断是否在发生相同的批次在进行置返盘
	 * =======================================================
	 */
	public boolean estSerialno() {
		int tFirstC = 0;
		int tSecondC = 0;
		String tSql = "select count(*) from lysendtobank where  bankcode = '"
				+ "?code?" + "'";
		// ExeSQL tExeSQL = new ExeSQL () ;
		// SSRS tSSRS = new SSRS () ;
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
        sqlbv5.sql(tSql);
        sqlbv5.put("code", bankCode);
		tSSRS = tExeSQL.execSQL(sqlbv5);
		tFirstC = Integer.parseInt(tSSRS.GetText(1, 1));
		logger.debug("=======开始判断是否重复点击==================================");
		try {

			Thread.currentThread().sleep(8 * 1000);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		tSSRS = tExeSQL.execSQL(sqlbv5);
		tSecondC = Integer.parseInt(tSSRS.GetText(1, 1));
		if (tFirstC != tSecondC) {
			return false;
		}
		return true;
	}

	/**
	 * ========================================================= 把返回的数据进行分数量的提交
	 * 调用下面的方法
	 * 
	 * @return boolean
	 */
	public boolean FirstdealData() { // int tExistCount = 0;
		// boolean tExistFlag = false;

		LJSPaySet tLJSPaySet = new LJSPaySet();
		SQLwithBindVariables tSql =new SQLwithBindVariables();
		// String tSql1 = "" ;
		// 得到这个批次产生的业务数据的全部SQL
		for (int i = 0; i < typeFlag.length; i++) {
			logger.debug("============================" + typeFlag[i]);
			tSql = getLJSPayByPaydate(startDate, endDate, typeFlag[i]);
			// if (!tSql1.equals(null)&&!tSql1.equals(""))
			// {
			// if (i==0)
			// {
			// tSql=tSql1;
			// }
			// else tSql=tSql+ " union " +tSql1;
			// }
			logger.debug(tSql);
		}

		/* 分别得到不同的 Sql去处理业务 */
		for (int i = 0; i < typeFlag.length; i++) {
			tFlag = false;
			tSql = getLJSPayByPaydate(startDate, endDate, typeFlag[i]);

			// 在处理数据时由于送银行数据过大,所以需要每5000条数据的处理,这个类来实现这样的功

			RSWrapper rsWrapper = new RSWrapper();
			if (!rsWrapper.prepareData(tLJSPaySet, tSql)) {
				this.mErrors.copyAllErrors(rsWrapper.mErrors);
				logger.debug(rsWrapper.mErrors.getFirstError());
				return false;
			}

			do {
				logger.debug("Start getData....");
				rsWrapper.getData();
				if (!dealData(tLJSPaySet)) {

					return false;
				}

			} while (tLJSPaySet.size() > 0);
			rsWrapper.close();
		}
		// 生成银行日志表数据

		if (mCreateLybanklog) {
			LYBankLogSchema tLYBankLogSchema = getBankLog();
			logger.debug("---End getBankLog---");
			// 判断该批次中是否没有数据
			if (tLYBankLogSchema != null) {
				VData tVData = new VData();
				MMap map = new MMap();
				map.put(tLYBankLogSchema, "INSERT");
				tVData.add(map);
				PubSubmit p = new PubSubmit();
				if (!p.submitData(tVData, "")) {

				}
			}
		} else {
			CError tError = new CError();
			tError.moduleName = "GetSendToBankDealBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误! 总应收表无数据";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;

	}

	/**
	 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * 
	 * 
	 * 下面的方法是为了准备一条数据所需要的处理方法
	 * 
	 * @param tLJSPaySchema
	 *            LJSPaySchema
	 * @return boolean
	 *         +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */

	/**
	 * ==========================================================================
	 * 
	 * 查询需要提交置盘的SQL
	 * 
	 * @param startDate
	 *            String
	 * @param endDate
	 *            String
	 * @return LJSPaySet
	 */

	/**
	 * 获取交费日期在设置的日期区间内的总应付表记录
	 * 
	 * @param startDate
	 * @param endDate
	 * @param othertype
	 *            //业务类型
	 * @return
	 */
	private SQLwithBindVariables getLJSPayByPaydate(String startDate, String endDate,
			String othernotype) {

		String tSql2 = "";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		// 新契约代收
		if (typeFlag != null
				&& (othernotype.equals("6") || othernotype.equals("7") || othernotype
						.equals("0"))) {
			tSql2 = "select * from LJSPay ljs where 1=1"
					+ " and ljs.SendBankCount not in ('-2','-5') and ljs.StartPayDate >= '"
					+ "?StartPayDate?"
					+ "'"
					+ " and ljs.StartPayDate <= '"
					+ "?StartPayDate1?"
					+ "'"

					+ " and ljs.BankCode = '"
					+ "?code1?"
					+ "'"
					+ " and ljs.ManageCom like concat('"+"?a1?"+"','%')"
					+ " and (ljs.BankOnTheWayFlag = '0' or ljs.BankOnTheWayFlag is null)"
					+ " and ljs.BankAccNo is not null "
					+ " and exists (select 'X' from ljtempfee where enteraccdate is null and ljs.getnoticeno=tempfeeno)"
					+ " and ljs.OtherNoType = '" + "?OtherNoType?" + "'";
		
	        sqlbv6.sql(tSql2);
	        sqlbv6.put("StartPayDate", startDate);
	        sqlbv6.put("StartPayDate1", endDate);
	        sqlbv6.put("code1", bankCode);
	        sqlbv6.put("a1", mGlobalInput.ManageCom);
	        sqlbv6.put("OtherNoType", othernotype);
		}
		// 续期代收
		else if (typeFlag != null
				&& (othernotype.equals("2") || othernotype.equals("3") || othernotype
						.equals("8"))) {

			tSql2 = "select * from LJSPay ljs where 1=1"
					+ " and ljs.StartPayDate >= '"
					+ "?StartPayDate2?"
					+ "'"
					+ " and ljs.StartPayDate <= '"
					+ "?StartPayDate3?"
					+ "'"

					+ " and ljs.BankCode = '"
					+ "?code2?"
					+ "' and ljs.sumduepaymoney <> '0' "
					+ " and ljs.ManageCom like concat('"+"?a2?"+"','%')"
					+ " and (ljs.BankOnTheWayFlag = '0' or ljs.BankOnTheWayFlag is null) and  (ljs.banksuccflag <> '1' or ljs.banksuccflag is null) "
					+ " and ljs.BankAccNo is not null "
					+ " and not exists ( select tempfeeno from ljtempfeeclass where paymode != '7' and othernotype in('2','02','3','03','8','08') and ljs.getnoticeno = tempfeeno) "
					// + " and not exists (select contno from lccontstate where state = '1' and
					// StateType in ('Available','PayPrem','Terminate') "
					// + " and enddate is null and ljspay.otherno = contno )";
					+ " and not exists (select 'X' from lcpol a where a.Contno = ljs.otherno And ljs.startpaydate=a.paytodate "
					+ " And Exists (Select 'X' From Lccontstate "
					+ " where a.contno=contno And polno = a.polno And Statetype In ('Available', 'PayPrem','Terminate') And State = '1' And Enddate Is null ) "
					+ " And Exists (Select 'X' From Ljspayperson Where Riskcode = a.Riskcode And Contno = a.Contno) And a.Appflag <> '9') "
					+ " and not exists (select 'X' from lcconthangupstate where contno=ljs.otherno and hanguptype='3') ";


			logger.debug("5");
			// '2'代表正常的续期个人保费 ，'02'代表预期的续交个人保费
			if (othernotype.equals("2"))// || othernotype.equals ("02"))
			{
				tSql2 += " and ljs.OtherNoType in('2','02') ";
			}
			// ‘3’代表正常的续期银代的保费，‘03’代表预期的续交银代保费
			if (othernotype.equals("3"))// || othernotype.equals ("03"))
			{
				tSql2 += " and ljs.OtherNoType in ('3','03') ";
			}
			// ‘8’代表正常的续期电话直销的保费，‘08’代表预期的续交电话直销保费
			if (othernotype.equals("8"))// || othernotype.equals ("08"))
			{
				tSql2 += " and ljs.OtherNoType in ('8','08') ";
			}
			sqlbv6.sql(tSql2);
	        sqlbv6.put("StartPayDate2", startDate);
	        sqlbv6.put("StartPayDate3", endDate);
	        sqlbv6.put("code2", bankCode);
	        sqlbv6.put("a2", mGlobalInput.ManageCom);
		} else if (typeFlag != null && othernotype.equals("10")) {

			tSql2 = "select * from LJSPay where 1=1"
					+ " and StartPayDate >= '"
					+ "?StartPayDate3?"
					+ "'"
					+ " and StartPayDate <= '"
					+ "?StartPayDate4?"
					+ "'"
					+ " and BankCode = '"
					+ "?code3?"
					+ "'"
					+ " and ManageCom like concat('"+"?a3?"+"','%')"
					+ " and (BankOnTheWayFlag = '0' or BankOnTheWayFlag is null)"
					+ " and BankAccNo is not null and paydate > '"
					+ "?paydate?" + "'"
					+ " and OtherNoType = '10' ";
			sqlbv6.sql(tSql2);
			sqlbv6.put("StartPayDate3", startDate);
	        sqlbv6.put("StartPayDate4", endDate);
	        sqlbv6.put("code3", bankCode);
	        sqlbv6.put("a3", mGlobalInput.ManageCom);
	        sqlbv6.put("paydate",PubFun.getCurrentDate());
		}

		logger.debug("============SQL===================" + tSql2);

		return sqlbv6;
	}

	/**
	 * 生成送银行表数据
	 * 
	 * @param tLJSPaySet
	 * @return
	 */
	private LYSendToBankSchema GetSendToBankBig(LJSPaySchema tLJSPaySchema,
			String tMoney) {
		int i;

		LYSendToBankSet tLYSendToBankSet = new LYSendToBankSet();

		// ExeSQL tExeSQL = new ExeSQL () ;
		// SSRS tSSRS = new SSRS () ;

		// 生成送银行表数据
		LYSendToBankSchema tLYSendToBankSchema = new LYSendToBankSchema();
		tLYSendToBankSchema.setSerialNo(serialNo);
		tLYSendToBankSchema.setDealType("S");
		tLYSendToBankSchema.setPayCode(tLJSPaySchema.getGetNoticeNo());
		tLYSendToBankSchema.setBankCode(bankCode);

		tLYSendToBankSchema.setAccName(tLJSPaySchema.getAccName());
		tLYSendToBankSchema.setAccNo(tLJSPaySchema.getBankAccNo());

		tLYSendToBankSchema.setPolNo(tLJSPaySchema.getOtherNo());
		tLYSendToBankSchema.setNoType(tLJSPaySchema.getOtherNoType());
		tLYSendToBankSchema.setComCode(tLJSPaySchema.getManageCom());
		// 银行名称

		String sql = " select distinct bankname from ldbank where bankcode = '"
				+ "?code4?" + "'";
		SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
        sqlbv9.sql(sql);
        sqlbv9.put("code4", bankCode);
		tSSRS = tExeSQL.execSQL(sqlbv9);
		String tBankName = tSSRS.GetText(1, 1);
		tLYSendToBankSchema.setBankName(tBankName);
		SSRS nSSRS = new SSRS();
		// sql = "select name from ldcom where ComCode = '" + mGlobalInput.ManageCom +
		// "' " ;

		// sql = " select bankcode,bankname,acctype,accno from ldcomtobank where ComCode
		// = substr('"
		// + mGlobalInput.ManageCom + "',1,4)" ;
		// tSSRS = tExeSQL.execSQL (sql) ;

		// 收款帐户类型
		tLYSendToBankSchema.setInAccType("1");
		// String bank = "" ;
		// 收款帐号
		

		// if (bankCode.equals ("320101"))
		// {
		// tLYSendToBankSchema.setInBankName (tBankName) ;
		// tLYSendToBankSchema.setInAccNo ("0402020319300007432") ;
		
		// }
		// else if (bankCode.equals ("320105"))
		// {
		// tLYSendToBankSchema.setInBankName (tBankName) ;
		// tLYSendToBankSchema.setInAccNo ("0407040019300023718") ;
		
		// }
		// else
		// {
		tLYSendToBankSchema.setInBankName(tBankName);
		// }

		// 输入客户的证件号码
		// 为续期做准备
		if (tLJSPaySchema.getOtherNoType().equals("2")
				|| tLJSPaySchema.getOtherNoType().equals("3")
				|| tLJSPaySchema.getOtherNoType().equals("02")
				|| tLJSPaySchema.getOtherNoType().equals("03")
				|| tLJSPaySchema.getOtherNoType().equals("8")
				|| tLJSPaySchema.getOtherNoType().equals("08")) {
			sql = "select idtype, idno from lcappnt where contno = '"
					+ "?contno8?" + "'";
			SQLwithBindVariables sqlbv33=new SQLwithBindVariables();
	        sqlbv33.sql(sql);
	        sqlbv33.put("contno8", tLJSPaySchema.getOtherNo() );
			nSSRS = tExeSQL.execSQL(sqlbv33);
			if (nSSRS.MaxRow != 0) {
				if (nSSRS.GetText(1, 1) != null
						&& !nSSRS.GetText(1, 1).equals("null")) {

					tLYSendToBankSchema.setIDNo(nSSRS.GetText(1, 2));
					tLYSendToBankSchema.setIDType(nSSRS.GetText(1, 1));
				}
			}
			if (bankCode.equals("2100040")) {
				sql = " select payintv from lcpol a where a.polno = a.mainpolno and a.contno = '"
						+ "?contno?" + "'";
				SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
		        sqlbv10.sql(sql);
		        sqlbv10.put("contno", tLJSPaySchema.getOtherNo() );
				nSSRS = tExeSQL.execSQL(sqlbv10);
				if (nSSRS.MaxRow != 0) {
					if (nSSRS.GetText(1, 1) != null
							&& !nSSRS.GetText(1, 1).equals("null")) {
						if (nSSRS.GetText(1, 1).equals("0")) {
							tLYSendToBankSchema.setStandByFlag1("1");
						} else if (nSSRS.GetText(1, 1).equals("1")) {
							tLYSendToBankSchema.setStandByFlag1("3");
						} else if (nSSRS.GetText(1, 1).equals("12")) {
							tLYSendToBankSchema.setStandByFlag1("2");
						} else {
							tLYSendToBankSchema.setStandByFlag1("2");
						}

					}
				}
			}

		}
		// 为保全做准备
		if (tLJSPaySchema.getOtherNoType().equals("10")) {
			sql = " select PERSONID from lpedorapp where lpedorapp.edoracceptno =  '"
					+ "?edoracceptno?" + "'";
			SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
	        sqlbv11.sql(sql);
	        sqlbv11.put("edoracceptno", tLJSPaySchema.getOtherNo() );
			nSSRS = tExeSQL.execSQL(sqlbv11);
			if (nSSRS.MaxRow != 0) {
				if (nSSRS.GetText(1, 1) != null
						&& !nSSRS.GetText(1, 1).equals("null")) {
					tLYSendToBankSchema.setIDNo(nSSRS.GetText(1, 1));
					tLYSendToBankSchema.setIDType("0");
				}
			}
		}
		// 首期做准备
		if (tLJSPaySchema.getOtherNoType().equals("6")
				|| tLJSPaySchema.getOtherNoType().equals("7")
				|| tLJSPaySchema.getOtherNoType().equals("0")) {
			sql = " select idtype, idno from ljtempfeeclass where tempfeeno =  '"
					+ "?tempfeeno?" + "'";
			SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
	        sqlbv12.sql(sql);
	        sqlbv12.put("tempfeeno", tLJSPaySchema.getGetNoticeNo() );
			nSSRS = tExeSQL.execSQL(sqlbv12);
			if (nSSRS.MaxRow != 0) {
				if (nSSRS.GetText(1, 1) != null
						&& !nSSRS.GetText(1, 1).equals("null")) {

					tLYSendToBankSchema.setIDNo(nSSRS.GetText(1, 2));
					tLYSendToBankSchema.setIDType(nSSRS.GetText(1, 1));
				}
			}

			if (bankCode.equals("2100040")) {
				sql = " select payintv from ljtempfee where tempfeeno = '"
						+ "?tempfeeno1?" + "'";
				SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
		        sqlbv13.sql(sql);
		        sqlbv13.put("tempfeeno1", tLJSPaySchema.getGetNoticeNo() );
				nSSRS = tExeSQL.execSQL(sqlbv13);
				if (nSSRS.MaxRow != 0) {
					if (nSSRS.GetText(1, 1) != null
							&& !nSSRS.GetText(1, 1).equals("null")) {
						if (nSSRS.GetText(1, 1).equals("0")) {
							tLYSendToBankSchema.setStandByFlag1("1");
						} else if (nSSRS.GetText(1, 1).equals("1")) {
							tLYSendToBankSchema.setStandByFlag1("3");
						} else if (nSSRS.GetText(1, 1).equals("12")) {
							tLYSendToBankSchema.setStandByFlag1("2");
						} else {
							tLYSendToBankSchema.setStandByFlag1("2");
						}

					}
				}
			}

		}

		// 顺序号
		mTempSer += 1;
		tLYSendToBankSchema.setSerNo(mTempSer);
		tLYSendToBankSchema.setAgentCode(tLJSPaySchema.getAgentCode());
		tLYSendToBankSchema.setPayMoney(tMoney);
		tLYSendToBankSchema.setSendDate(PubFun.getCurrentDate());
		tLYSendToBankSchema.setDoType("1");
		// 因为没有为发送银行盘表设计操作员字段，所以暂时保存在备注字段中，add by Minim at 2004-2-5
		tLYSendToBankSchema.setRemark(mGlobalInput.Operator);
		tLYSendToBankSchema.setModifyDate(PubFun.getCurrentDate());
		tLYSendToBankSchema.setModifyTime(PubFun.getCurrentTime());
		tLYSendToBankSchema.setRiskCode(tLJSPaySchema.getRiskCode());
		tLYSendToBankSchema.setStandByFlag3(tLJSPaySchema.getStartPayDate());
		tLYSendToBankSet.add(tLYSendToBankSchema);

		// 保存此笔数据的缴费，如果失败就将在总的保费中减去
		mTempMoney = Double.parseDouble((new DecimalFormat("0.00"))
				.format(tLJSPaySchema.getSumDuePayMoney()));
		// 累加总金额和总数量
		dTotalMoney = dTotalMoney + tLJSPaySchema.getSumDuePayMoney();
		// 转换精度
		dTotalMoney = Double.parseDouble((new DecimalFormat("0.00"))
				.format(dTotalMoney));
		sumNum = sumNum + 1;

		totalMoney = Double.parseDouble((new DecimalFormat("0.00"))
				.format(dTotalMoney));

		return tLYSendToBankSchema;
	}

	/**
	 * 判断目前的值是否都挂起
	 * 
	 * @param startDate
	 *            String
	 * @param endDate
	 *            String
	 * @return String
	 */

	/**
	 * 修改应收表银行在途标志
	 * 
	 * @param tLJSPaySet
	 * @return
	 */
	private LJSPaySchema modifyBankFlag(LJSPaySchema tLJSPaySchema) {

		tLJSPaySchema.setBankOnTheWayFlag("1");
		// 记录发送银行次数
		if ((tLJSPaySchema.getOtherNoType().equals("6")
				|| tLJSPaySchema.getOtherNoType().equals("7") || tLJSPaySchema
				.getOtherNoType().equals("0"))
				&& (String.valueOf(tLJSPaySchema.getSendBankCount())
						.equals("1") || String.valueOf(
						tLJSPaySchema.getSendBankCount()).equals("4"))) {
			logger.debug("这要开始处理发盘的记录了使其增加负数" + "-"
					+ (tLJSPaySchema.getSendBankCount() + 1));
			tLJSPaySchema
					.setSendBankCount(-(tLJSPaySchema.getSendBankCount() + 1));

		} else {
			logger.debug("这要" + (tLJSPaySchema.getSendBankCount() + 1));
			tLJSPaySchema
					.setSendBankCount(tLJSPaySchema.getSendBankCount() + 1);

		}

		return tLJSPaySchema;
	}

	/**
	 * 生成银行日志表数据
	 * 
	 * @return
	 */
	private LYBankLogSchema getBankLog() {
		// ExeSQL tExeSQL = new ExeSQL () ;
		// SSRS tSSRS = new SSRS () ;
		// 检查是否发生该批次没有数据的情况
		String tSendHit = "select count(*) from lysendtobank where serialno = '"
				+ "?serialno?" + "'";
		SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
        sqlbv13.sql(tSendHit);
        sqlbv13.put("serialno", serialNo);
		tSSRS = tExeSQL.execSQL(sqlbv13);
		if (tSSRS.getMaxRow() == 0) {
			return null;
		}

		String paymoney = "select sum(paymoney) from lysendtobank where serialno = '"
				+ "?serialno5?" + "'";
		SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
        sqlbv14.sql(paymoney);
        sqlbv14.put("serialno5", serialNo);
		String payCount = "select count(*) from lysendtobank where serialno = '"
				+ "?serialno6?" + "'";
		SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
        sqlbv15.sql(payCount);
        sqlbv15.put("serialno5", serialNo);
		LYBankLogSchema tLYBankLogSchema = new LYBankLogSchema();

		tLYBankLogSchema.setSerialNo(serialNo);
		tLYBankLogSchema.setBankCode(bankCode);
		tLYBankLogSchema.setLogType("S");
		tLYBankLogSchema.setStartDate(PubFun.getCurrentDate());
		tLYBankLogSchema.setMakeDate(PubFun.getCurrentDate());
		tSSRS = tExeSQL.execSQL(sqlbv14);
		tLYBankLogSchema.setTotalMoney(tSSRS.GetText(1, 1));

		tLYBankLogSchema.setSaleChnl("X");

		tLYBankLogSchema.setOperationType("X");

		tSSRS = tExeSQL.execSQL(sqlbv15);
		tLYBankLogSchema.setTotalNum(tSSRS.GetText(1, 1));
		tLYBankLogSchema.setModifyDate(PubFun.getCurrentDate());
		tLYBankLogSchema.setModifyTime(PubFun.getCurrentTime());
		tLYBankLogSchema.setComCode(mGlobalInput.ManageCom);

		return tLYBankLogSchema;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return
	 */
	private boolean dealData(LJSPaySet tLJSPaySet1) {

		LJSPaySet tLJSPaySet = new LJSPaySet();
		// 在对挂起表修改值时，如果以前已存在就将其update 反之 INsert
		LCContHangUpStateSchema tInsetLCContHangUpSchema = new LCContHangUpStateSchema();
		LCContHangUpStateSchema tUpdateLCContHangUpSchema = new LCContHangUpStateSchema();

		tLJSPaySet.set(tLJSPaySet1);
		try {
			// 总应收表处理（获取交费日期在设置的日期区间内的记录；获取银行在途标志为0 的记录）
			if (!tFlag) {
				if (tLJSPaySet == null) {
					return true;
				}
			}
			tFlag = true;
			logger.debug("---End getLJSPayByPaydate---");
			// 生成文件，一条记录一条记录的提，
			// 初始化变量
			LJSPaySchema tLJSPaySchema = new LJSPaySchema();
			RNHangUp mrn = new RNHangUp(mGlobalInput);
			String tSql = "";
			String tESql = "";
			String t1Sql = "";
			// ExeSQL tExeSQL = new ExeSQL () ;
			// SSRS tSSRS = new SSRS () ;
			for (int i = 0; i < tLJSPaySet.size(); i++) {
				// 每次都需判断是执行插入语句或更新语句
				boolean tUpdateFlag = false;
				boolean tInsertFlag = false;
				tSql = "";
				tESql = "";
				t1Sql = "";

				logger.debug(tLJSPaySet.get(i + 1).getOtherNo());
				tLJSPaySchema.setSchema(tLJSPaySet.get(i + 1));
				// 在置盘时如果应收金额为0，将不在将这张保单参加置盘
				String tMoney = getActMoney(tLJSPaySchema.getGetNoticeNo());
				if (tMoney.equals("0")) {
					continue;
				}
				// 对续期应收做处理
				if (tLJSPaySchema.getOtherNoType().equals("2")
						|| tLJSPaySchema.getOtherNoType().equals("02")
						|| tLJSPaySchema.getOtherNoType().equals("3")
						|| tLJSPaySchema.getOtherNoType().equals("03")
						|| tLJSPaySchema.getOtherNoType().equals("8")
						|| tLJSPaySchema.getOtherNoType().equals("08")) {
					// logger.debug("==========用于判断是否在发生了预交记录============") ;
					// 用于判断该保单是否有预交缴费记录
					tESql = "select 'X' from lccont a, ljspay b  where a.contno = '"
							+ "?contno?"
							+ "' and a.contno = b.otherno and a.dif >= b.sumduepaymoney ";
					SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
			        sqlbv15.sql(tESql);
			        sqlbv15.put("contno", tLJSPaySet.get(i + 1).getOtherNo());
					tSSRS = tExeSQL.execSQL(sqlbv15);

					logger.debug("========校验是否有预交记录"
							+ tLJSPaySet.get(i + 1).getOtherNo());

					if (tSSRS.MaxRow > 0 && tSSRS.GetText(1, 1) != null
							&& !tSSRS.GetText(1, 1).equals("null")) {
						logger.debug("======已交========"
								+ tLJSPaySet.get(i + 1).getOtherNo());
						continue;
					} else {

					}

					// 用于判断是否有在置盘程序期间有其他的缴费
					logger.debug("==========用于判断是否在置盘程序提交的过程中发生了暂交的记录============");
					tESql = "select tempfeeno from ljtempfeeclass where enteraccdate is not null and tempfeeno = '"
							+ "?tempfeeno?" + "' ";
					SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
			        sqlbv16.sql(tESql);
			        sqlbv16.put("tempfeeno", tLJSPaySet.get(i + 1).getGetNoticeNo());
					tSSRS = tExeSQL.execSQL(sqlbv16);

					logger.debug("========校验是否有咱交记录"
							+ tLJSPaySet.get(i + 1).getOtherNo());

					if (tSSRS.MaxRow > 0 && tSSRS.GetText(1, 1) != null
							&& !tSSRS.GetText(1, 1).equals("null")) {
						logger.debug("======暂缴费已交========"
								+ tLJSPaySet.get(i + 1).getOtherNo());
						continue;
					} else {

					}

					LCContHangUpStateSchema tLCContHangUpStateSchema = new LCContHangUpStateSchema();
					logger.debug("++  contno+++++"
							+ tLJSPaySchema.getOtherNo());
					// 核对是否被挂起，如果没有挂起产生挂起记录
					if (mrn.checkHangUP(tLJSPaySchema.getOtherNo())) {
						tLCContHangUpStateSchema = mrn.hangUp(String
								.valueOf(tLJSPaySchema.getOtherNo()));
						if (mrn.getOperate().equalsIgnoreCase("INSERT")) {
							tInsetLCContHangUpSchema
									.setSchema(tLCContHangUpStateSchema);
							tInsertFlag = true;
							tSaveCount += 1;
						}
						if (mrn.getOperate().equalsIgnoreCase("UPDATE")) {
							tUpdateLCContHangUpSchema
									.setSchema(tLCContHangUpStateSchema);
							tUpdateFlag = true;
							tSaveCount += 1;
						}
					} else {
						if (tSaveCount != 0) {
							totalMoney -= mTempMoney;
							sumNum -= 1;
						}
						continue;
					}
				}

				logger.debug("==保单号：===" + tLJSPaySchema.getOtherNo());
				// 修改应收表银行在途标志
				LJSPaySchema tUPDATELJSPaySchema = modifyBankFlag(tLJSPaySet
						.get(i + 1));
				logger.debug("---End modifyBankFlag---");
				// 生成制盘数据
				LYSendToBankSchema tSLYSendToBankSchema = GetSendToBankBig(
						tLJSPaySchema, tMoney);

				t1Sql = "select * from ljspay a where a.bankonthewayflag = '1'"
						+ " and a.getnoticeno = '"
						+ "?getnoticeno?" + "'";
				SQLwithBindVariables sqlbv27=new SQLwithBindVariables();
		        sqlbv27.sql(t1Sql);
		        sqlbv27.put("getnoticeno",  tSLYSendToBankSchema.getPayCode());
				// 用于判断是否被保全挂起
				tSql = "select * from  LCContHangUpState b where b.contno= '"
						+ "?contno1?" + "' and b.rnflag = '1'";
				SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
		        sqlbv17.sql(tSql);
		        sqlbv17.put("contno1", tLJSPaySchema.getOtherNo());
				logger.debug(tSql);
				VData tVData = new VData();
				MMap map = new MMap();
				map.put(tSLYSendToBankSchema, "INSERT");
				map.put(sqlbv27, "SELECT");
				map.put(tUPDATELJSPaySchema, "UPDATE");
				map.put(sqlbv17, "SELECT");
				if (tInsertFlag) {
					map.put(tInsetLCContHangUpSchema, "INSERT");
				}
				if (tUpdateFlag) {
					map.put(tUpdateLCContHangUpSchema, "UPDATE");
				}
				tVData.add(map);

				PubSubmit p = new PubSubmit();
				if (!p.submitData(tVData, "")) {
					if (tSaveCount != 0) {
						totalMoney -= mTempMoney;
						sumNum -= 1;

					}
				} else {
					// 如果发生正确的提交的就需要产生一个正确的批次号
					mCreateLybanklog = true;
				}
			}
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GetSendToBankDealBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误! " + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 保费重新计算，等到当前新的保费， 以处理在制盘期间发生的保费变化
	 * 
	 * @param tGetNoticeNo
	 * @return
	 */
	public String getActMoney(String tGetNoticeNo) {
		String tSQL = "   select sumduepaymoney from ljspay where getnoticeno = '"
				+ "?getnoticeno?" + "'";
		// ExeSQL tExeSQL = new ExeSQL () ;
		// SSRS tSSRS = new SSRS () ;
		SQLwithBindVariables sqlbv18=new SQLwithBindVariables();
        sqlbv18.sql(tSQL);
        sqlbv18.put("getnoticeno", tGetNoticeNo);
		tSSRS = tExeSQL.execSQL(sqlbv18);
		if (tSSRS.MaxRow > 0 && tSSRS.GetText(1, 1) != null
				&& !tSSRS.GetText(1, 1).equals("null")
				&& !tSSRS.GetText(1, 1).equals("")) {

			return tSSRS.GetText(1, 1);
		}
		return "0";
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 数据输出方法，供外界获取数据处理 ,目前“银保通”用
	 * 
	 * @return 返回本次发盘表的应插入数据，为一个Set
	 */

	public LYSendToBankSet getLYSendToBankSet() {
		return reLYSendToBankSet;
	}

	public static void main(String[] args) {

	}
}
