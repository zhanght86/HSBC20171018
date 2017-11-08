package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.Date;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:对首期银行交款和银行转账的处理
 * </p>
 * 首期银行交款：录入投保单号催收：检验投保单号，保费项表，保险责任表；应收总表，暂交费表中需无该纪录；插入应收纪录
 * 银行转账：录入印刷号催收：检验应收总表中无该纪录，暂交费表中有该纪录，且交费位置，险种编码匹配，且到帐日期为空；插入应收纪录
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HZM
 * @version 1.0
 */

public class NewIndiDueFeeBL {
private static Logger logger = Logger.getLogger(NewIndiDueFeeBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	private VData mResult = new VData();
	private GlobalInput tGI = new GlobalInput();
	/** 数据操作字符串 */
	private String mOperate = "";
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	/** 数据表 保存数据 */
	// 个人保单表
	private LCPolSet mLCPolSet = new LCPolSet();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	// 保费项表
	private LCPremSet mLCPremSet = new LCPremSet();
	private LCPremSchema mLCPremSchema = new LCPremSchema();
	// 因收总表
	private LJSPaySchema mLJSPaySchema = new LJSPaySchema();
	private LJSPaySet mLJSPaySet = new LJSPaySet();
	// 保险责任表
	private LCDutySet mLCDutySet = new LCDutySet();
	private LCDutySchema mLCDutySchema = new LCDutySchema();
	// 查询暂交费，如果有并不是退出，而是判断暂加费的和是否小于保费，如果是，容许再次新单催收
	// 是否有暂交费标记
	private boolean tempFeeFlag = false;
	// 纪录已有的暂交费的总和
	private double tempFeeSum = 0;
	// 校验催收标记
	private String mSignFlag = "";
	/* 转换精确位数的对象 */
	private String FORMATMODOL = "0.00";// 保费保额计算出来后的精确位数
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL);// 数字转换对象

	public NewIndiDueFeeBL() {
	}

	public static void main(String[] args) {

		// DecimalFormat tDecimalFormat=new DecimalFormat("0.00");//数字转换对象
		// logger.debug((36.63+1013.54)-1050.17);
		// double t1=new Double(1050.17).doubleValue();
		// double t2=new Double(36.63).doubleValue();
		// double t3=new Double(1013.54).doubleValue();
		//
		// double t4=t2+t3-t1;
		//
		// String t6=tDecimalFormat.format(t4);
		// if(t4==0)
		// {
		// double t=1;
		// }
		// double t7=Double.parseDouble(t6);
		// if(t7==0)
		// {
		// double t=1;
		// }

		NewIndiDueFeeBL NewIndiDueFeeBL1 = new NewIndiDueFeeBL();
		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolSchema.setPolNo("86110020040110000370");
		GlobalInput tGI = new GlobalInput();
		tGI.ManageCom = "86";
		tGI.ComCode = "86";
		tGI.Operator = "001";
		VData tv = new VData();
		tv.add(tLCPolSchema);
		tv.add(tGI);
		NewIndiDueFeeBL1.submitData(tv, "INSERT");
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
			return false;
		logger.debug("After getinputdata");

		// 进行业务处理
		if (!dealData())
			return false;
		logger.debug("After dealData！");

		return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData() {
		String PolNo = mLCPolSchema.getPolNo();
		if (PolNo == null) {
			PolNo = mLCPolSchema.getProposalNo();
		}

		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(PolNo);
		if (tLCPolDB.getInfo() == false) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "NewIndiDueFeeBL";
			tError.functionName = "dealData";
			tError.errorMessage = "没有查询到该投保单,请您确认!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tLCPolDB.getAppFlag() == null || tLCPolDB.getAppFlag().equals("0")) {
			mLCPolSchema = tLCPolDB.getSchema();
		} else {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "NewIndiDueFeeBL";
			tError.functionName = "dealData";
			tError.errorMessage = "该投保单已签单,请您确认!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 校验描述变量
		// NewPolDunFlag (新单催收标记) 0 不催收 1 只催收个险 3 只催收银行险 9 都催收
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("NewPolDunFlag");
		if (tLDSysVarDB.getInfo() == false) {
			mSignFlag = "0";
		} else {
			mSignFlag = tLDSysVarDB.getSysVarValue();
		}
		// 1-以投保单号方式处理
		dealPolProsal();
		// 2-以印刷号方式处理
		dealPrtNo();

		prepareOutputData();

		NewIndiDueFeeBLS tNewIndiDueFeeBLS = new NewIndiDueFeeBLS();
		tNewIndiDueFeeBLS.submitData(mResult, "INSERT");
		if (tNewIndiDueFeeBLS.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tNewIndiDueFeeBLS.mErrors);
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData mInputData) {
		tGI = ((GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0));
		mLCPolSchema = ((LCPolSchema) mInputData.getObjectByObjectName(
				"LCPolSchema", 0));

		if (tGI == null || mLCPolSchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "NewIndiDueFeeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的数据，请您确认!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// 准备往后层输出所需要的数据
	// 输出：如果准备数据时发生错误则返回false,否则返回true
	private boolean prepareOutputData() {
		mResult.add(mLJSPaySet);
		return true;
	}

	/**
	 * 如果是投保单号调用该函数
	 * 
	 * @return
	 */
	private boolean dealPolProsal() {

		String PolNo = mLCPolSchema.getPolNo();
		String Operator = tGI.Operator; // 保存登陆管理员账号
		String ManageCom = tGI.ManageCom; // 保存登陆区站,ManageCom=内存中登陆区站代码
		double sumPay = 0; // 保存累计和的变量
		// 保存纪录的条数
		int recordCount = 0;
		int i = 0, iMax = 0;
		String tLimit = "";
		String tNo = ""; // 产生的通知书号
		String serNo = "";// 流水号
		tLimit = PubFun.getNoLimit(ManageCom);
		serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
		VData tVData = new VData();

		if (CheckDueSign(mLCPolSchema, mSignFlag) == false)
			return true;

		// Step 1 :query from LCPol table
		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolSchema = mLCPolSchema; // 保存后用

		// 判断是否是（0 --银行自动转帐，8 --银行转帐(事后转账)）中的一项
		if (tLCPolSchema.getPayLocation() == null) {
			mErrors.addOneError("投保单纪录中交费位置不能为空！");
			return false;
		}
		if (!(tLCPolSchema.getPayLocation().equals("0") || tLCPolSchema
				.getPayLocation().equals("8"))) {
			mErrors.addOneError("投保单纪录中交费位置不是:银行自动转帐或银行转帐(事后转账)！");
			return false;
		}

		// Step 2:query from LJSPay table and query from LJTemp table
		if (tLCPolSchema.getRiskCode() == null) {
			mErrors.addOneError("投保单纪录中险种编码和投保单号不能为空！");
			return false;
		}

		if (queryLJSPay(tLCPolSchema.getPolNo(), "6")) {
			// mErrors.addOneError("应收总表中已存在该纪录！银行代扣操作略过");
			return true;
		}

		if (queryLJTempFee(tLCPolSchema.getPolNo(), "0", tLCPolSchema
				.getRiskCode())) {
			// 这种情况是新单催收后，发银行数据成功，应收总表删除，添加的一笔暂交费纪录，其它号码类型=0
			// 这笔暂交费纪录的金额累加。不催收
			// 其它号码类型=0 个人投保单号
			// 暂交费标记为真，累加暂加费
		}

		if (queryLJTempFee(tLCPolSchema.getPrtNo(), "4", tLCPolSchema
				.getRiskCode())) {
			// 这种情况是财务录入暂交费金额，
			// 这笔暂交费纪录的金额累加。后续处理催收
			// 其它号码类型=4 印刷号
			// 暂交费标记为真，累加暂加费
		}

		// Step 3:query from LCPrem table
		tVData.clear();
		LCPremSchema tLCPremSchema = new LCPremSchema();
		tLCPremSchema.setPolNo(PolNo);
		tVData.add(tLCPremSchema);
		LCPremQueryUI tLCPremQueryUI = new LCPremQueryUI();
		if (!tLCPremQueryUI.submitData(tVData, "QUERY")) {
			this.mErrors.copyAllErrors(tLCPremQueryUI.mErrors);
			return false;
		}
		tVData.clear();
		mLCPremSet = new LCPremSet();
		tVData = tLCPremQueryUI.getResult();
		// 可能有多条纪录，所以保存记录集
		mLCPremSet
				.set((LCPremSet) tVData.getObjectByObjectName("LCPremSet", 0));

		// Step 4 : query from LCDuty
		recordCount = mLCPremSet.size();
		mLCDutySet = new LCDutySet(); // 保存保险责任表纪录集合
		LCDutySchema tLCDutySchema = new LCDutySchema();
		LCDutySet tempLCDutySet = new LCDutySet();

		LCPremSet saveLCPremSet = new LCPremSet(); // 保存过滤后的保费项纪录集

		for (i = 1; i <= recordCount; i++) {
			tLCPremSchema = new LCPremSchema();
			tLCPremSchema = mLCPremSet.get(i);
			tLCDutySchema.setPolNo(tLCPremSchema.getPolNo());
			tLCDutySchema.setDutyCode(tLCPremSchema.getDutyCode());
			tVData.clear();
			tVData.add(tLCDutySchema);
			// *.java内置查询条件:免交比率<1.0
			LCDutyQueryUI tLCDutyQueryUI = new LCDutyQueryUI();
			if (!tLCDutyQueryUI.submitData(tVData, "QUERY")) {
				logger.debug("没有查到符合条件的责任纪录");
			} else {
				tVData.clear();
				tVData = tLCDutyQueryUI.getResult();
				tempLCDutySet.set((LCDutySet) tVData.getObjectByObjectName(
						"LCDutySet", 0));
				tLCDutySchema = tempLCDutySet.get(1);
				mLCDutySet.add(tLCDutySchema);
				saveLCPremSet.add(tLCPremSchema);
				sumPay = sumPay + (1 - tLCDutySchema.getFreeRate())
						* tLCPremSchema.getPrem();
			}
		} // end for()
		if (saveLCPremSet.size() == 0)// 如果过滤后的保费项表纪录数=0
		{
			this.mErrors.addOneError("查询保险责任表失败，原因是:都为免交");
			return false;
		}

		// Step 5:
		if (tempFeeFlag == true) {
			double actuPayMoney = sumPay - tempFeeSum;// 保费总和-暂交费
			String stractuPayMoney = mDecimalFormat.format(actuPayMoney);
			actuPayMoney = Double.parseDouble(stractuPayMoney);
			if (actuPayMoney <= 0)//
			{
				// this.mErrors.addOneError("发现暂交费纪录,且暂交费不少于应交保费!");
				return true;
			} else {
				sumPay = actuPayMoney;// 将实际要催收的钱赋予应收总表
			}
		}
		LJSPaySchema tLJSPaySchema = new LJSPaySchema();
		// 计算交费日期 =个人保单表交至日期+2个月

		FDate fDate = new FDate();
		Date paytoDate = new Date(); // 交费日期
		paytoDate = fDate.getDate(tLCPolSchema.getCValiDate());
		String strPayDate = fDate.getString(PubFun.calDate(paytoDate, 2, "M",
				null));

		/*
		 * Date paytoDate = new Date(); //交至日期 Date payDate = new Date(); //交费日期
		 * FDate fDate = new FDate(); String strPayDate="";
		 * paytoDate=fDate.getDate(tLCPolSchema.getPaytoDate());
		 * 
		 * if(paytoDate==null)//下面的函数第一个参数不能为空 {
		 * paytoDate=fDate.getDate(PubFun.getCurrentDate()); } payDate =
		 * PubFun.calDate(paytoDate,2,"M",null); strPayDate =
		 * fDate.getString(payDate);
		 */

		// 产生通知书号
		tLimit = PubFun.getNoLimit(tLCPolSchema.getManageCom());
		tNo = PubFun1.CreateMaxNo("PAYNOTICENO", tLimit);
		tLJSPaySchema.setGetNoticeNo(tNo);
		tLJSPaySchema.setOtherNo(PolNo);
		tLJSPaySchema.setOtherNoType("6");
		tLJSPaySchema.setAppntNo(tLCPolSchema.getAppntNo());
		tLJSPaySchema.setPayDate(strPayDate);
		tLJSPaySchema.setStartPayDate(tLCPolSchema.getCValiDate());
		tLJSPaySchema.setBankOnTheWayFlag("0");
		tLJSPaySchema.setBankSuccFlag("0");
		tLJSPaySchema.setSendBankCount(0);// 送银行次数
		tLJSPaySchema.setSumDuePayMoney(sumPay);
		tLJSPaySchema.setApproveCode(tLCPolSchema.getApproveCode());
		tLJSPaySchema.setApproveDate(tLCPolSchema.getApproveDate());
		tLJSPaySchema.setRiskCode(tLCPolSchema.getRiskCode());
		/*
		 * Lis5.3 upgrade get
		 * tLJSPaySchema.setBankAccNo(tLCPolSchema.getBankAccNo());
		 * tLJSPaySchema.setBankCode(tLCPolSchema.getBankCode());
		 * tLJSPaySchema.setAccName(tLCPolSchema.getAccName());
		 */
		tLJSPaySchema.setManageCom(tLCPolSchema.getManageCom());
		tLJSPaySchema.setAgentCode(tLCPolSchema.getAgentCode());
		tLJSPaySchema.setAgentGroup(tLCPolSchema.getAgentGroup());
		tLJSPaySchema.setSerialNo(serNo); // 流水号
		tLJSPaySchema.setOperator(Operator);
		tLJSPaySchema.setMakeDate(CurrentDate);
		tLJSPaySchema.setMakeTime(CurrentTime);
		tLJSPaySchema.setModifyDate(CurrentDate);
		tLJSPaySchema.setModifyTime(CurrentTime);

		mLJSPaySet.add(tLJSPaySchema);
		return true;

	}

	/**
	 * 如果是印刷号，调用该函数
	 * 
	 * @return
	 */
	private boolean dealPrtNo() {

		String PolNo = mLCPolSchema.getPrtNo();// 得到印刷号
		String Operator = tGI.Operator; // 保存登陆管理员账号
		String ManageCom = tGI.ManageCom; // 保存登陆区站,ManageCom=内存中登陆区站代码

		double sumPay = 0; // 保存累计和的变量
		// 保存纪录的条数
		int recordCount = 0;
		String tLimit = "";
		String serNo = "";// 流水号
		tLimit = PubFun.getNoLimit(ManageCom);
		serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);

		LCPolSchema tLCPolSchema = mLCPolSchema; // 保存后用

		// Step 1 :query from LCPol table
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPrtNo(mLCPolSchema.getPrtNo());
		mLCPolSet = tLCPolDB.query();
		if (tLCPolDB.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLCPolDB.mErrors);
			return false;
		}
		if (mLCPolSet == null || mLCPolSet.size() == 0) {
			this.mErrors.addOneError("没有找到印刷号对应的投保单");
			return false;
		}
		// 查询出主险保单数据
		for (int j = 1; j <= mLCPolSet.size(); j++) {
			tLCPolSchema = mLCPolSet.get(j);
			if (tLCPolSchema.getPolNo().equals(tLCPolSchema.getMainPolNo())) {
				break;
			}
		}

		// 2-queryLJSPay //因为不同批次的情况存在，所以容许多条应收总表
		// if(queryLJSPay(tLCPolSchema.getPrtNo(),"9"))
		// {
		// //mErrors.addOneError("应收总表中已经存在该纪录！银行代扣操作略过");
		// return true;
		// }

		// 3-queryLJTempFee2
		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		tLJTempFeeSet = queryLJTempFee2(tLCPolSchema.getPrtNo(), "4");
		if (tLJTempFeeSet == null)
			return true;

		tLJTempFeeSet = checkLJSPay(tLJTempFeeSet);
		if (tLJTempFeeSet.size() == 0)
			return true;

		LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
		for (int k = 1; k <= tLJTempFeeSet.size(); k++) {
			String strSql = "select * from ljtempfeeclass where tempfeeno='?tempfeeno?' and paymode='4' and EnterAccDate is null";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(strSql);
			sqlbv.put("tempfeeno", tLJTempFeeSet.get(k).getTempFeeNo());
			logger.debug("query ljtempfeeclass:" + strSql);

			LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
			tLJTempFeeClassSchema = tLJTempFeeClassDB.executeQuery(sqlbv).get(
					1);

			// tLJTempFeeClassDB.setTempFeeNo(tLJTempFeeSet.get(1).getTempFeeNo());
			// tLJTempFeeClassDB.setPayMode("4");//银行转账
			// if(!tLJTempFeeClassDB.getInfo())
			// {
			// this.mErrors.copyAllErrors(tLJTempFeeClassDB.mErrors);
			// return false;
			// }
			// LJTempFeeClassSchema tLJTempFeeClassSchema= new LJTempFeeClassSchema();
			// tLJTempFeeClassSchema=tLJTempFeeClassDB.getSchema();
			sumPay = tLJTempFeeClassSchema.getPayMoney();

			// 计算交费日期 =个人保单表交至日期+2个月
			FDate fDate = new FDate();
			Date paytoDate = new Date(); // 交费日期
			paytoDate = fDate.getDate(tLCPolSchema.getCValiDate());
			String strPayDate = fDate.getString(PubFun.calDate(paytoDate, 2,
					"M", null));
			/*
			 * Date paytoDate = new Date(); //交至日期 Date payDate = new Date(); //交费日期
			 * FDate fDate = new FDate();
			 * paytoDate=fDate.getDate(tLCPolSchema.getPaytoDate());
			 * if(paytoDate==null)//下面的函数第一个参数不能为空 {
			 * paytoDate=fDate.getDate(PubFun.getCurrentDate()); } payDate =
			 * PubFun.calDate(paytoDate,2,"M",null); strPayDate =
			 * fDate.getString(payDate);
			 */

			LJSPaySchema tLJSPaySchema = new LJSPaySchema();
			// 如果有多条暂交费纪录，其暂交费号是相同的
			tLJSPaySchema.setGetNoticeNo(tLJTempFeeClassSchema.getTempFeeNo());
			tLJSPaySchema.setOtherNo(tLCPolSchema.getPrtNo());
			tLJSPaySchema.setOtherNoType("9");
			tLJSPaySchema.setAppntNo(tLCPolSchema.getAppntNo());
			tLJSPaySchema.setPayDate(strPayDate);
			tLJSPaySchema.setStartPayDate(tLCPolSchema.getCValiDate());
			tLJSPaySchema.setBankOnTheWayFlag("0");
			tLJSPaySchema.setBankSuccFlag("0");
			tLJSPaySchema.setSendBankCount(0);// 送银行次数
			tLJSPaySchema.setSumDuePayMoney(sumPay);
			tLJSPaySchema.setApproveCode(tLCPolSchema.getApproveCode());
			tLJSPaySchema.setApproveDate(tLCPolSchema.getApproveDate());
			tLJSPaySchema.setRiskCode(tLCPolSchema.getRiskCode());
			tLJSPaySchema.setBankAccNo(tLJTempFeeClassSchema.getBankAccNo());// 个人保单表没有，从暂交费子表中取
			tLJSPaySchema.setBankCode(tLJTempFeeClassSchema.getBankCode());
			tLJSPaySchema.setAccName(tLJTempFeeClassSchema.getAccName());
			tLJSPaySchema.setManageCom(tLCPolSchema.getManageCom());
			tLJSPaySchema.setAgentCode(tLCPolSchema.getAgentCode());
			tLJSPaySchema.setAgentGroup(tLCPolSchema.getAgentGroup());
			tLJSPaySchema.setSerialNo(serNo); // 流水号
			tLJSPaySchema.setOperator(Operator);
			tLJSPaySchema.setMakeDate(CurrentDate);
			tLJSPaySchema.setMakeTime(CurrentTime);
			tLJSPaySchema.setModifyDate(CurrentDate);
			tLJSPaySchema.setModifyTime(CurrentTime);

			mLJSPaySet.add(tLJSPaySchema);
		}

		return true;
	}

	/**
	 * 查询应收总表
	 * 
	 * @param No
	 *            号码
	 * @param NoType
	 *            号码类型
	 * @return
	 */
	private boolean queryLJSPay(String No, String NoType) {
		LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPayDB.setOtherNo(No);
		tLJSPayDB.setOtherNoType(NoType);
		LJSPaySet tLJSPaySet = new LJSPaySet();
		tLJSPaySet = tLJSPayDB.query();
		if (tLJSPaySet.size() > 0)
			return true;
		else
			return false;
	}

	/**
	 * 校验是否存在交费收据号相同的应收总表
	 * 
	 * @param tLJTempFeeSet
	 * @return
	 */
	private LJTempFeeSet checkLJSPay(LJTempFeeSet tLJTempFeeSet) {
		LJTempFeeSet newLJTempFeeSet = new LJTempFeeSet();
		for (int i = 1; i <= tLJTempFeeSet.size(); i++) {
			LJSPayDB tLJSPayDB = new LJSPayDB();
			tLJSPayDB.setGetNoticeNo(tLJTempFeeSet.get(i).getTempFeeNo());
			if (tLJSPayDB.getInfo() == false) {
				newLJTempFeeSet.add(tLJTempFeeSet.get(i));
			}
		}

		return newLJTempFeeSet;
	}

	/**
	 * 查询暂交费表
	 * 
	 * @param No
	 *            号码
	 * @param NoType
	 *            号码类型
	 * @param RiskType
	 *            险种编码
	 * @return
	 */
	private boolean queryLJTempFee(String No, String NoType, String RiskType) {
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		tLJTempFeeDB.setOtherNo(No);
		tLJTempFeeDB.setOtherNoType(NoType);
		tLJTempFeeDB.setRiskCode(RiskType);
		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		tLJTempFeeSet = tLJTempFeeDB.query();
		if (tLJTempFeeSet.size() > 0) {
			tempFeeFlag = true;
			for (int n = 1; n <= tLJTempFeeSet.size(); n++) {
				// if(tLJTempFeeSet.get(n).getTempFeeType().equals("5")&&NoType.equals("4"))
				// {
				// //如果是暂交费类型=5 银行首期代扣并且暂加费其它号码类型是=4 印刷号，那么不处理
				// }
				// else
				// {
				// tempFeeSum=tempFeeSum+tLJTempFeeSet.get(n).getPayMoney();
				// }
				tempFeeSum = tempFeeSum + tLJTempFeeSet.get(n).getPayMoney();

			}
			return true;
		} else
			return false;
	}

	/**
	 * 根据其它号码和其它号码类型查询暂交费（对应多条纪录）
	 * 
	 * @param No
	 * @param NoType
	 * @return
	 */
	private LJTempFeeSet queryLJTempFee2(String No, String NoType) {
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		String strSql = "select distinct tempfeeno from ljtempfee where otherno='?otherno?' and othernotype='?NoType?' and confFlag='0' and EnterAccDate is null";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(strSql);
		sqlbv1.put("otherno", No);
		sqlbv1.put("NoType", NoType);
		logger.debug("query ljtempfee:" + strSql);
		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv1);
		if (tSSRS == null || tSSRS.MaxRow == 0) {
			mErrors.addOneError("暂交费查询失败！");
			return null;
		}
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
			tLJTempFeeSchema.setTempFeeNo(tSSRS.GetText(i, 1));
			tLJTempFeeSet.add(tLJTempFeeSchema);
		}
		return tLJTempFeeSet;

	}

	/**
	 * 校验催收标记
	 * 
	 * @return
	 */
	private boolean CheckDueSign(LCPolSchema tLCPolSchema, String SignFlag) {
		// 01-团险直销,02-个人营销,03-银行代理,04-兼业代理,05-专业代理,06-经纪公司,07-不计业绩销售渠道,99-其他
		String SaleChnl = tLCPolSchema.getSaleChnl();
		if (SaleChnl == null)
			return false;

		if (SignFlag.equals("3")) {
			if (SaleChnl.equals("03"))
				return true;
		}
		if (SignFlag.equals("9")) {
			return true;
		}
		return false;
	}

	public boolean DueToTempFee(VData tVData) {

		return true;
	}

}
