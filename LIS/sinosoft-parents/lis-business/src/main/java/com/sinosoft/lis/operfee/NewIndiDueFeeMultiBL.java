package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.Date;

import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
 * </p>
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

public class NewIndiDueFeeMultiBL {
private static Logger logger = Logger.getLogger(NewIndiDueFeeMultiBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	private GlobalInput tGI = new GlobalInput();
	private VData mResult = new VData();
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
	// 应收个人交费表
	private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
	private LJSPayPersonSchema mLJSPayPersonSchema = new LJSPayPersonSchema();
	// 因收总表
	private LJSPaySchema mLJSPaySchema = new LJSPaySchema();
	private LJSPaySet mLJSPaySet = new LJSPaySet();

	// 保险责任表
	private LCDutySet mLCDutySet = new LCDutySet();
	private LCDutySchema mLCDutySchema = new LCDutySchema();

	/* 转换精确位数的对象 */
	private String FORMATMODOL = "0.00";// 保费保额计算出来后的精确位数
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL);// 数字转换对象
	// 查询暂交费，如果有并不是退出，而是判断暂加费的和是否小于保费，如果是，容许再次新单催收
	// 是否有暂交费标记
	private boolean tempFeeFlag = false;
	// 纪录已有的暂交费的总和
	private double tempFeeSum = 0;
	// 催收标记
	private String mSignFlag = "";
	private String tbankCode = "";

	public NewIndiDueFeeMultiBL() {
	}

	public static void main(String[] args) {
		NewIndiDueFeeMultiBL NewIndiDueFeeMultiBL1 = new NewIndiDueFeeMultiBL();
		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolSchema.setGetStartDate("2004-6-2");
		tLCPolSchema.setPayEndDate("2004-6-9");
		GlobalInput tGI = new GlobalInput();
		tGI.ManageCom = "86";
		tGI.ComCode = "86";
		tGI.Operator = "001";
		VData tv = new VData();
		tv.add(tLCPolSchema);
		tv.add(tGI);

		NewIndiDueFeeMultiBL1.submitData(tv, "INSERT");
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		logger.debug("OperateData:  " + cOperate);

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
		// 校验描述变量
		// NewPolDunFlag (新单催收标记) 0 不催收 1 只催收个险 3 只催收银行险 9 都催收
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("NewPolDunFlag");
		if (tLDSysVarDB.getInfo() == false) {
			mSignFlag = "0";
		} else {
			mSignFlag = tLDSysVarDB.getSysVarValue();
		}

		// 因为业务方面不再从
		// dealPolProsal();
		dealPrtNo();
		mResult.add(mLJSPaySet);
		NewIndiDueFeeMultiBLS tNewIndiDueFeeMultiBLS = new NewIndiDueFeeMultiBLS();
		tNewIndiDueFeeMultiBLS.submitData(mResult, "INSERT");
		if (tNewIndiDueFeeMultiBLS.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tNewIndiDueFeeMultiBLS.mErrors);
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
		/*
		 * by zhanghui 2005.2.18 接收前面由TransferData传入后台的BankCode数据
		 */
		TransferData tTransferData = (TransferData) mInputData
				.getObjectByObjectName("TransferData", 0);
		tbankCode = (String) tTransferData.getValueByName("bankCode");

		if (tGI == null || mLCPolSchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "NewIndiDueFeeMultiBL";
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
		mInputData = new VData();
		return true;
	}

	private VData getResult() {
		mInputData.clear();
		return mInputData;
	}

	/**
	 * 处理银行事后交款（个人保单表中位置是8）数据
	 * 
	 * @return
	 */
	private boolean dealPolProsal() {

		String startDate = mLCPolSchema.getGetStartDate();
		String endDate = mLCPolSchema.getPayEndDate();
		String Operator = tGI.Operator;
		String ManageCom = tGI.ManageCom;

		double sumPay = 0; // 保存累计和的变量
		// 保存纪录的条数
		int recordCount = 0;
		String tLimit = "";
		String tNo = ""; // 产生的通知书号
		String serNo = "";// 流水号
		tLimit = PubFun.getNoLimit(ManageCom);
		serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
		VData tVData = new VData();

		// Step 1 :query from LCPol table
		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolSchema.setGetStartDate(startDate);
		tLCPolSchema.setPayEndDate(endDate);
		tLCPolSchema.setManageCom(ManageCom);
		tLCPolSchema.setAppFlag("0");
		tLCPolSchema.setSaleChnl("03");

		QueryLCPolTypeBL tQueryLCPolTypeBL = new QueryLCPolTypeBL();
		if (!tQueryLCPolTypeBL.queryC(tLCPolSchema)) {
			// this.mErrors .copyAllErrors(tQueryLCPolTypeBL.mErrors ) ;
			return false;
		}
		mLCPolSet = new LCPolSet();
		mLCPolSet = tQueryLCPolTypeBL.getResult();
		LJSPaySchema tLJSPaySchema = new LJSPaySchema();
		for (int i = 1; i <= mLCPolSet.size(); i++) {
			// 初始化
			tempFeeFlag = false;
			tempFeeSum = 0;
			sumPay = 0;

			tLCPolSchema = new LCPolSchema();
			tLCPolSchema = mLCPolSet.get(i); // 保存后用

			// if(CheckDueSign(tLCPolSchema,mSignFlag)==false) continue;

			// 判断是否是（0 --银行自动转帐，8 --银行转帐(事后转账)）中的一项
			if (tLCPolSchema.getPayLocation() == null) {
				continue;
			}
			if (!(tLCPolSchema.getPayLocation().equals("0") || tLCPolSchema
					.getPayLocation().equals("8"))) {
				continue;
			}
			// Step 2:query from LJSPay table and query from LJTemp table
			if (tLCPolSchema.getRiskCode() == null) {
				mErrors.addOneError("投保单纪录中险种编码不能为空！");
				continue;
			}
			if (queryLJSPay(tLCPolSchema.getPolNo(), "6")) {
				// mErrors.addOneError("应收总表中已经存在该纪录！");
				continue;
			}

			if (queryLJTempFee(tLCPolSchema.getPolNo(), "0", tLCPolSchema
					.getRiskCode())) {

			}

			if (queryLJTempFee(tLCPolSchema.getPrtNo(), "4", tLCPolSchema
					.getRiskCode())) {

			}
			// Step 3:query from LCPrem table
			tVData.clear();
			LCPremSchema tLCPremSchema = new LCPremSchema();
			tLCPremSchema.setPolNo(tLCPolSchema.getPolNo());
			tVData.add(tLCPremSchema);
			LCPremQueryUI tLCPremQueryUI = new LCPremQueryUI();
			if (!tLCPremQueryUI.submitData(tVData, "QUERY")) {
				// this.mErrors .copyAllErrors(tLCPremQueryUI.mErrors ) ;
				continue;
			}
			tVData.clear();
			mLCPremSet = new LCPremSet();
			tVData = tLCPremQueryUI.getResult();
			// 可能有多条纪录，所以保存记录集
			mLCPremSet.set((LCPremSet) tVData.getObjectByObjectName(
					"LCPremSet", 0));

			// Step 4 : query from LCDuty
			recordCount = mLCPremSet.size();
			mLCDutySet = new LCDutySet(); // 保存保险责任表纪录集合
			LCDutySchema tLCDutySchema = new LCDutySchema();
			LCDutySet tempLCDutySet = new LCDutySet();

			LCPremSet saveLCPremSet = new LCPremSet(); // 保存过滤后的保费项纪录集

			for (int m = 1; m <= recordCount; m++) {
				tLCPremSchema = new LCPremSchema();
				tLCPremSchema = mLCPremSet.get(m);
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
				// this.mErrors.addOneError("查询保险责任表失败，原因是:都为免交");
				continue;
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
			tLJSPaySchema = new LJSPaySchema();
			// 计算交费日期 =个人保单表交至日期+2个月
			FDate fDate = new FDate();
			Date paytoDate = new Date(); // 交费日期
			paytoDate = fDate.getDate(tLCPolSchema.getCValiDate());
			String strPayDate = fDate.getString(PubFun.calDate(paytoDate, 2,
					"M", null));
			/*
			 * Date paytoDate = new Date(); //交至日期 Date payDate = new Date(); //交费日期
			 * FDate fDate = new FDate(); String strPayDate="";
			 * paytoDate=fDate.getDate(tLCPolSchema.getPaytoDate());
			 * if(paytoDate==null)//下面的函数第一个参数不能为空 {
			 * paytoDate=fDate.getDate(PubFun.getCurrentDate()); } payDate =
			 * PubFun.calDate(paytoDate,2,"M",null); strPayDate =
			 * fDate.getString(payDate);
			 */
			// 产生通知书号
			tLimit = PubFun.getNoLimit(tLCPolSchema.getManageCom());
			tNo = PubFun1.CreateMaxNo("PAYNOTICENO", tLimit);
			tLJSPaySchema.setGetNoticeNo(tNo);
			tLJSPaySchema.setOtherNo(tLCPolSchema.getPolNo());
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
		}
		return true;

	}

	/**
	 * 处理银行扣款：此时，暂收费表中已存在其它号码是印刷号的数据-暂不用
	 * 
	 * @return
	 */
	private boolean dealPrtNo() {

		String startDate = mLCPolSchema.getGetStartDate();
		String endDate = mLCPolSchema.getPayEndDate();
		/*
		 * Lis5.3 upgrade get String bankcode=mLCPolSchema.getBankCode();
		 */
		// by zhanghui 2005.2.18
		String bankcode = tbankCode;
		/*
		 * Lis6.0 upgrade get String bankcode="";
		 */
		String Operator = tGI.Operator;
		String ManageCom = tGI.ManageCom;

		boolean nextFlag = false;
		double sumPay = 0; // 保存累计和的变量
		// 保存纪录的条数
		String tLimit = "";
		String serNo = "";// 流水号
		tLimit = PubFun.getNoLimit(ManageCom);
		serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);

		LJTempFeeClassSet xLJTempFeeClassSet = new LJTempFeeClassSet();
		xLJTempFeeClassSet = QueryLJTempfeeClass(startDate, endDate, ManageCom,
				bankcode);
		if (xLJTempFeeClassSet == null)
			return false;

		LJTempFeeClassSchema xLJTempFeeClassSchema = new LJTempFeeClassSchema();
		for (int n = 1; n <= xLJTempFeeClassSet.size(); n++) {
			xLJTempFeeClassSchema = xLJTempFeeClassSet.get(n); // 保存后用
			// 1-找到对应的主表
			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
			tLJTempFeeDB.setTempFeeNo(xLJTempFeeClassSchema.getTempFeeNo());
			LJTempFeeSet tempLJTempFeeSet = tLJTempFeeDB.query();
			if (tempLJTempFeeSet == null || tempLJTempFeeSet.size() == 0) {
				mErrors.addOneError("没有找到暂交费号"
						+ xLJTempFeeClassSchema.getTempFeeNo() + "对应的暂交费表纪录!");
				return false;
			}

			LJTempFeeSchema tempLJTempFeeSchema = tempLJTempFeeSet.get(1);// 默认是1
			LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
			for (int m = 1; m <= tempLJTempFeeSet.size(); m++) {
				tLMRiskAppDB.setRiskCode(tempLJTempFeeSet.get(m).getRiskCode());
				if (tLMRiskAppDB.getInfo() == false) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "NewIndiDueFeeMultiBL";
					tError.functionName = "dealPrtNo";
					tError.errorMessage = "险种描述取出失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				// 是主险的情况
				if (StrTool.cTrim(tLMRiskAppDB.getSubRiskFlag()).equals("M")) {
					tempLJTempFeeSchema = tempLJTempFeeSet.get(m);// 保存后用
					break;
				}

			}

			// 2-如果发现应收总表已经存在，跳到下次循环--//因为不同批次的情况存在，所以容许多条应收总表
			// if(queryLJSPay(tempLJTempFeeSchema.getOtherNo(),"9"))
			// {
			// continue;
			// }
			if (checkLJSPay(tempLJTempFeeSchema) == true) {
				continue;
			}

			sumPay = xLJTempFeeClassSchema.getPayMoney();

			LJSPaySchema tLJSPaySchema = new LJSPaySchema();

			FDate fDate = new FDate();
			Date paytoDate = new Date(); // 交费日期
			paytoDate = fDate.getDate(tempLJTempFeeSchema.getMakeDate());
			String strPayDate = fDate.getString(PubFun.calDate(paytoDate, 2,
					"M", null));

			// 如果有多条暂交费纪录，其暂交费号是相同的
			tLJSPaySchema.setGetNoticeNo(tempLJTempFeeSchema.getTempFeeNo());
			tLJSPaySchema.setOtherNo(tempLJTempFeeSchema.getOtherNo());
			tLJSPaySchema.setOtherNoType("9");
			tLJSPaySchema.setPayDate(strPayDate);
			tLJSPaySchema.setStartPayDate(tempLJTempFeeSchema.getMakeDate());
			tLJSPaySchema.setBankOnTheWayFlag("0");
			tLJSPaySchema.setBankSuccFlag("0");
			tLJSPaySchema.setSendBankCount(0);// 送银行次数
			tLJSPaySchema.setSumDuePayMoney(sumPay);
			tLJSPaySchema.setRiskCode(tempLJTempFeeSchema.getRiskCode());
			tLJSPaySchema.setBankAccNo(xLJTempFeeClassSchema.getBankAccNo());// 从暂交费子表中取
			tLJSPaySchema.setBankCode(xLJTempFeeClassSchema.getBankCode());
			tLJSPaySchema.setAccName(xLJTempFeeClassSchema.getAccName());
			tLJSPaySchema.setManageCom(tempLJTempFeeSchema.getManageCom());
			tLJSPaySchema.setAgentCode(tempLJTempFeeSchema.getAgentCode());
			tLJSPaySchema.setAgentGroup(tempLJTempFeeSchema.getAgentGroup());
			tLJSPaySchema.setSerialNo(serNo); // 流水号
			tLJSPaySchema.setOperator(Operator);
			tLJSPaySchema.setMakeDate(CurrentDate);
			tLJSPaySchema.setMakeTime(CurrentTime);
			tLJSPaySchema.setModifyDate(CurrentDate);
			tLJSPaySchema.setModifyTime(CurrentTime);
			tLJSPaySchema.setIDNo(xLJTempFeeClassSchema.getIDNo());
			tLJSPaySchema.setIDType(xLJTempFeeClassSchema.getIDType());
			sumPay = 0;
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
	 * 查询应收总表
	 * 
	 * @param No
	 *            号码
	 * @param NoType
	 *            号码类型
	 * @return
	 */
	private boolean checkLJSPay(LJTempFeeSchema tLJTempFeeSchema) {
		LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPayDB.setGetNoticeNo(tLJTempFeeSchema.getTempFeeNo());
		if (tLJSPayDB.getInfo() == false)
			return false;

		return true;
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
		tLJTempFeeDB.setOtherNo(No);
		tLJTempFeeDB.setOtherNoType(NoType);
		tLJTempFeeDB.setConfFlag("0");
		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		tLJTempFeeSet = tLJTempFeeDB.query();
		if (tLJTempFeeDB.mErrors.needDealError() == true) {
			mErrors.addOneError("暂交费查询失败！");
			return null;
		}
		if (tLJTempFeeSet.size() == 0) {
			mErrors.addOneError("没有该暂交费纪录！");
			return null;
		}
		return tLJTempFeeSet;

	}

	private LJTempFeeSet QueryLJTempfee3(String StartDate, String EndDate) {
		String strSQL = "select * from LJTempFee ";
		strSQL = strSQL + " where (MakeDate>='?StartDate?' and MakeDate<='?EndDate?') ";
		strSQL = strSQL + " and OtherNoType='4'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSQL);
		sqlbv.put("StartDate", StartDate);
		sqlbv.put("EndDate", EndDate);
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		tLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv);
		if (tLJTempFeeDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJTempFeeDB.mErrors);
			getError("QueryLJTempfee3", "数据库查询失败!");
			tLJTempFeeSet.clear();
			return null;
		}
		if (tLJTempFeeSet.size() == 0) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJTempFeeDB.mErrors);
			getError("QueryLJTempfee3", "暂交费未找到相关数据!");
			tLJTempFeeSet.clear();
			return null;
		}
		return tLJTempFeeSet;
	}

	private LJTempFeeClassSet QueryLJTempfeeClass(String StartDate,
			String EndDate, String tManageCom, String tBankCode) {
		//如果2009-10-2日之前发盘采用原有逻辑，2009-10-2及之后采用新逻辑
		String strSQL="";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		logger.debug(""+PubFun.calInterval("2009-10-02", PubFun.getCurrentDate(), "D"));
		if(PubFun.calInterval("2009-10-02", PubFun.getCurrentDate(), "D")>=0)
		{
	        strSQL="select * from LJTempFeeClass "
               +" where (MakeDate>='?StartDate?' and MakeDate<='?EndDate?') "
               +" and PayMode='4'"
               +" and Enteraccdate is  null "
               +" and ManageCom like concat('?tManageCom?','%')"
               +" and exists(select 1 from ljtempfee where tempfeeno=ljtempfeeclass.tempfeeno "
               //zy 2009-10-16 根据新需求：只有一个合同下的所有险种都不通过才不发盘，否则按照收费记录进行发盘
//               +" and not exists(select 1 from lcpol where prtno=ljtempfee.otherno and uwflag in ('a','1','2'))  "
               +" and not exists(select 1 from lccont where prtno=ljtempfee.otherno and (uwflag in ('a','1','2') or NewAutoSendBankFlag='0')) "  //排除撤单、拒保、延期以及核保师指定不发盘的保单
             //根据新保险法进行调整，如果为非银代产品，则必须存在1）业务信息且合同核保结论不能为撤单，2）身故风险保额小于等于30万或者身故风险保额大于30万且合同核保结论为次标准体和标准体
               +"and exists(select 1 from lmriskapp where riskcode = ljtempfee.riskcode and risktype8 = '06'  "
               +" union (select 1 from lmriskapp where riskcode = ljtempfee.riskcode and (risktype8 <> '06' or risktype8 is null )"
               +"and exists (select 1 from lccont  where prtno = ljtempfee.otherno and (uwflag not in ('a','1','2') or uwflag is null ) "
               +"and ((uwflag in ('4', '9')  and getPrtAmnt(prtno,'12')>300000) or getPrtAmnt(prtno,'12')<=300000 )))))"
  				+" and not exists(select 1 from ljspay where getnoticeno=ljtempfeeclass.tempfeeno) ";
		}
		else
		{
	       
		        strSQL="select * from LJTempFeeClass "
	               +" where (MakeDate>='?StartDate?' and MakeDate<='?EndDate?') "
	               +" and PayMode='4'"
	               +" and Enteraccdate is  null "
	               +" and ManageCom like concat('?tManageCom?','%')"
	               +" and exists(select 1 from ljtempfee where tempfeeno=ljtempfeeclass.tempfeeno "
	               +" and not exists(select 1 from lcpol where prtno=ljtempfee.otherno and uwflag='a')) "
	  				+" and not exists(select 1 from ljspay where getnoticeno=ljtempfeeclass.tempfeeno) ";

		}
		sqlbv.put("StartDate", StartDate);
		sqlbv.put("EndDate", EndDate);
		sqlbv.put("tManageCom", tManageCom);
		if (tBankCode != null && !tBankCode.equals("")) {
			strSQL = strSQL + " and BankCode='?tBankCode?'";
			sqlbv.put("tBankCode", tBankCode);
		}
		logger.debug(strSQL);
		sqlbv.sql(strSQL);
		LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
		LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
		tLJTempFeeClassSet = tLJTempFeeClassDB.executeQuery(sqlbv);
		if (tLJTempFeeClassDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJTempFeeClassDB.mErrors);
			getError("QueryLJTempfeeClass", "数据库查询失败!");
			tLJTempFeeClassSet.clear();
			return null;
		}
		if (tLJTempFeeClassSet.size() == 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors(tLJTempFeeClassDB.mErrors);
			// getError("QueryLJTempfeeClass","暂交费分类未找到相关数据!");
			// tLJTempFeeClassSet.clear();
			return null;
		}
		return tLJTempFeeClassSet;
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

	/**
	 * 填充错误信息
	 * 
	 * @param funName
	 * @param errMsg
	 */
	public void getError(String funName, String errMsg) {
		CError tError = new CError();
		tError.moduleName = "NewIndiDueFeeMultiBL";
		tError.functionName = funName;
		tError.errorMessage = errMsg;
		mErrors.addOneError(tError);
	}

}
