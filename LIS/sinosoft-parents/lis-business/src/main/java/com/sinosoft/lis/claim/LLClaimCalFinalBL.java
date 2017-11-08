package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLClaimDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.LDExch;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.lis.vschema.LLBalanceSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:理算步骤最后，更新赔案各项金额信息
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author 续涛，2005.05.24--2005.05.24
 * @version 1.0
 */
public class LLClaimCalFinalBL {
private static Logger logger = Logger.getLogger(LLClaimCalFinalBL.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */
	private MMap mMMap = new MMap();

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();

	private String mClmNo = ""; // 赔案号

	// 理赔--应付数据
	// private LJSGetClaimSchema mLJSGetClaimSchema = new LJSGetClaimSchema();
	private LLBalanceSet mLLBalanceSet = new LLBalanceSet();

	public LLClaimCalFinalBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("----------理算步骤最后-----生成最终数据-----LLClaimCalFinalBL测试-----开始----------");
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		if (!pubSubmit()) {
			return false;
		}

		logger.debug("----------理算步骤最后-----生成最终数据-----LLClaimCalFinalBL测试-----结束----------");
		return true;
	}

	/**
	 * 取传入参数信息
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {

		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		// this.mAccNo = (String) mTransferData.getValueByName("AccNo"); //事件号
		// this.mAccDate = (String) mTransferData.getValueByName("AccDate");
		// //意外事故发生日期

		this.mClmNo = (String) mTransferData.getValueByName("ClmNo"); // 赔案号

		return true;
	}

	private boolean dealData() {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 获取赔案信息
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimSchema tLLClaimSchema = new LLClaimSchema();
		LLClaimDB tLLClaimDB = new LLClaimDB();
		tLLClaimDB.setClmNo(this.mClmNo);
		if (!tLLClaimDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "没有查询到赔案信息");
			return false;
		} else {
			tLLClaimSchema.setSchema(tLLClaimDB.getSchema());
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 计算
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql = "select currency,substr(FeeOperationType,1,1),(case when sum(pay) is null then 0 else sum(pay) end)"
				/*+ " nvl(sum(case substr(FeeOperationType,1,1)  when 'B' then Pay end),0),"
				+ " nvl(sum(case substr(FeeOperationType,1,1)  when 'C' then Pay end),0),"
				+ " nvl(sum(case substr(FeeOperationType,1,1)  when 'D' then Pay end),0) "*/
				+ " from LLBalance  where 1 = 1 " + " and ClmNo ='"
				+ "?mClmNo?" + "'"
				+" group by currency,substr(FeeOperationType,1,1)";
		;
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("mClmNo", this.mClmNo);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "计算预付,保单结算,合同处理的金额发生错误,"+tExeSQL.mErrors.getLastError());
			logger.debug("------------------------------------------------------");
			logger.debug("--LLClaimCalFinalBL.dealData()--计算预付,保单结算,合同处理的金额发生错误!"
							+ tSql);
			logger.debug("------------------------------------------------------");
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 计算
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		double tStandPay = 0; // 核算赔付金额
		double tBeforePay = 0; // 预付
		double tContBalPay = 0; // 保单结算金额
		double tContDealPay = 0; // 合同处理金额
		double tRealPay = 0; // 实际支付

		tStandPay = tLLClaimSchema.getStandPay();
		tBeforePay = tLLClaimSchema.getBeforePay();
		
		LDExch tLDExch = new LDExch();
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			/*tBeforePay = Double.parseDouble(tSSRS.GetText(i, 1));
			tContBalPay = Double.parseDouble(tSSRS.GetText(i, 2));
			tContDealPay = Double.parseDouble(tSSRS.GetText(i, 3));*/
			if(tSSRS.GetText(i,2).equals("B"))
			{
				if(tSSRS.GetText(i,1).equals(tLLClaimSchema.getCurrency()))
					tBeforePay = tBeforePay +Double.parseDouble(tSSRS.GetText(i,3));
				else
					tBeforePay = tBeforePay +tLDExch.toOtherCur(tSSRS.GetText(i,1),tLLClaimSchema.getCurrency(),PubFun.getCurrentDate(),Double.parseDouble(tSSRS.GetText(i,3)));
			}
			if(tSSRS.GetText(i,2).equals("C"))
			{
				if(tSSRS.GetText(i,1).equals(tLLClaimSchema.getCurrency()))
					tContBalPay = tBeforePay +Double.parseDouble(tSSRS.GetText(i,3));
				else
					tContBalPay = tBeforePay +tLDExch.toOtherCur(tSSRS.GetText(i,1),tLLClaimSchema.getCurrency(),PubFun.getCurrentDate(),Double.parseDouble(tSSRS.GetText(i,3)));
			}
			if(tSSRS.GetText(i,2).equals("D"))
			{
				if(tSSRS.GetText(i,1).equals(tLLClaimSchema.getCurrency()))
					tContDealPay = tBeforePay +Double.parseDouble(tSSRS.GetText(i,3));
				else
					tContDealPay = tBeforePay +tLDExch.toOtherCur(tSSRS.GetText(i,1),tLLClaimSchema.getCurrency(),PubFun.getCurrentDate(),Double.parseDouble(tSSRS.GetText(i,3)));
			}
		}

        //zhangzheng 预付金额在理算时已经减去了,不用再累加了!
		tRealPay = tStandPay //+ tBeforePay 
		+ tContBalPay + tContDealPay;

		tLLClaimSchema.setBalancePay(tContBalPay + tContDealPay);
		tLLClaimSchema.setRealPay(tRealPay);

		logger.debug("----------------------------------------------------------------------------------------------------");
		logger.debug("--核赔金额:[" + tStandPay + "],预付金额:[" + tBeforePay
				+ "],保单结算金额:[" + tContBalPay + "],合同处理金额:[" + tContDealPay
				+ "],最后赔付:[" + tRealPay + "]");
		logger.debug("----------------------------------------------------------------------------------------------------");

		mMMap.put(tLLClaimSchema, "UPDATE");

		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {

		mInputData.clear();
		mInputData.add(mMMap);
		return true;
	}

	/**
	 * 提交数据
	 * 
	 * @return
	 */
	private boolean pubSubmit() {
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			CError.buildErr(this, "数据提交失败,"+tPubSubmit.mErrors.getLastError());
			return false;
		}
		return true;

	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86000000";
		tG.ComCode = "86110000";

		String tClmNo = "90000002680";
		TransferData tTransferData = new TransferData();

		tTransferData.setNameAndValue("ClmNo", tClmNo);

		VData tVData = new VData();
		tVData.addElement(tG);
		tVData.addElement(tTransferData);

		LLClaimCalShouldPayBL tLLClaimCalShouldPayBL = new LLClaimCalShouldPayBL();
		tLLClaimCalShouldPayBL.submitData(tVData, "");

	}
}
