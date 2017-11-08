package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description : 理赔发票打印类
 * </p>
 * <p>
 * Copyright : Copyright (c) 2005
 * </p>
 * <p>
 * Company : Sinosoft
 * </p>
 * 
 * @author ：zhaorx 2006-03-09
 * @version ：0.1
 */

public class ClaimCheckPrintBL implements PrintService {
private static Logger logger = Logger.getLogger(ClaimCheckPrintBL.class);
	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */

	private GlobalInput mG = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private String mClmNo = ""; // 赔案号
	private String mDate = ""; // 日期
	private String mAppntName = ""; // 投保人
	private double mSumMoney = 0; // 总额
	private String mCSumMoney = ""; // 总额大写
	private String mContNo = ""; // 保单号
	private String mRiskCode = ""; // 险种号
	private double mMoney = 0; // 加费
	private double mPay = 0; // 利息

	public ClaimCheckPrintBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("-------收付费--收 费--理赔二核收费发票打印（ClaimCheckPrintBL）---开始！---");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		logger.debug("-------收付费--收 费--理赔二核收费发票打印：getInputData---结束！---");
		// 校验检查外部传入的数据
		if (!checkInputData()) {
			return false;
		}
		logger.debug("-------收付费--收 费--理赔二核收费发票打印 checkInputData---结束！---");
		// 处理业务数据
		if (!dealData()) {
			return false;
		}
		// 准备传往后台的数据
		VData vData = new VData();
		if (!prepareOutputData(vData)) {
			return false;
		}
		logger.debug("-------收付费--收 费--理赔二核收费发票打印 dealData---结束！---");
		logger.debug("--------收付费--收 费--理赔二核收费发票打印（ClaimCheckPrintBL）---结束！---");

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
		mInputData = cInputData;
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mClmNo = (String) mTransferData.getValueByName("ABClmNo");
		return true;
	}

	/*
	 * 校验检查外部传入的数据 @param cInputData VData @return boolean
	 */
	private boolean checkInputData() {
		if (mG == null) {
			CError tError = new CError();
			tError.moduleName = "ClaimCheckPrintUI";
			tError.functionName = "checkInputData";
			tError.errorMessage = "获取用户登录信息失败!";
			mErrors.addOneError(tError);
			return false;
		}

		if (mClmNo == null || mClmNo == "") {
			CError tError = new CError();
			tError.moduleName = "ClaimCheckPrintUI";
			tError.functionName = "checkInputData";
			tError.errorMessage = "获取赔案号信息失败!";
			mErrors.addOneError(tError);
			return false;
		}
		logger.debug("UI@@@@@@@" + mClmNo);
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 * 
	 * @param vData
	 *            VData
	 * @return boolean
	 */
	private boolean prepareOutputData(VData vData) {
		try {
			vData.clear();
			vData.add(mGlobalInput);
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("prepareOutputData", "发生异常");
			return false;
		}
		return true;
	}

	/**
	 * 错误构建
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "ClaimCheckPrintBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 获取当前系统时间
		mDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		// 新建一个XmlExport的实例
		XmlExport xmlexport = new XmlExport();
		// 所用模板名称
		xmlexport.createDocument("ClaimCheckPrint.vts", "");
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		String tSQLF = " select (case when a.sumduepaymoney is not null then a.sumduepaymoney  else 0 end),(case when (a.sumduepaymoney-b.pay) is not null then (a.sumduepaymoney-b.pay) else 0 end),(case when b.pay is not null then b.pay  else 0 end),b.contno,b.riskcode,"
				+ " (select c.name from ldperson c where c.customerno = a.appntno) from ljspayclaim b,ljspay a "
				+ " where 1=1 and a.getnoticeno = b.getnoticeno "
				+ " and b.feeoperationtype= 'C30' and b.subfeeoperationtype= 'C3002' and b.feefinatype= 'LX' and b.othernotype= '5' "
				+ " and b.otherno ='" + "?mClmNo?" + "' ";
		sqlbv.sql(tSQLF);
		sqlbv.put("mClmNo", mClmNo);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRSF = new SSRS();
		tSSRSF = tExeSQL.execSQL(sqlbv);
		int tMaxRow = tSSRSF.getMaxRow();
		if (tMaxRow != 0) {
			mSumMoney = Double.parseDouble(tSSRSF.GetText(1, 1));// 总额
			mMoney = Double.parseDouble(tSSRSF.GetText(1, 2));// 加费
			mPay = Double.parseDouble(tSSRSF.GetText(1, 3));// 利息
			mContNo = tSSRSF.GetText(1, 4);// 合同号
			mRiskCode = tSSRSF.GetText(1, 5);// 险种号
			mAppntName = tSSRSF.GetText(1, 6);// 投保人名
			mCSumMoney = BqNameFun.getCMoney(new DecimalFormat("0.00")
					.format(mSumMoney));// 总额大写
		}

		logger.debug("*****以上为“ListTable实例”准备数据完成，成功！！！***");
		logger.debug("***********************************************");

		logger.debug("*********以下为“TextTag实例”准备数据*************");
		// 新建一个TextTag的实例
		TextTag tTextTag = new TextTag();

		// 为模板赋值
		tTextTag.add("Date", mDate); // 日期：$=/Date$
		tTextTag.add("CSumMoney", mCSumMoney); // 总额大写$=/CSumMoney$
		tTextTag.add("SumMoney", new DecimalFormat("0.00").format(mSumMoney)); // 总额$=/Money$
		tTextTag.add("AppntName", mAppntName);// 投保人名: $=/AppntName$
		tTextTag.add("LCcont.ContNo", mContNo); // 保单$=/LCcont.ContNo$项
		tTextTag.add("RiskCode", mRiskCode);// 险种：$=/RiskCode$
		tTextTag.add("Prem", new DecimalFormat("0.00").format(mMoney)); // 加费：$=/Prem$元
		tTextTag.add("Pay", new DecimalFormat("0.00").format(mPay));// 利息：$=/Pay元

		logger.debug("**以上为“TextTag实例”准备数据完成，成功！！！******");
		logger.debug("**********************************************");
		// 添加动态列表数组，参数为一个TextTag
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			xmlexport.addTextTag(tTextTag);
		}

		logger.debug("---以下 将XmlExport打包，返回前台--");
		mResult.clear();
		mResult.add(xmlexport);
		return true;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	// 得到结果集
	public VData getResult() {
		return mResult;
	}

	/**
	 * 测试主函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tG.ComCode = "86";
		TransferData tTransferData = new TransferData();

		tTransferData.setNameAndValue("ABClmNo", "90000020949"); // 赔案号

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		ClaimCheckPrintBL tClaimCheckPrintBL = new ClaimCheckPrintBL();
		if (tClaimCheckPrintBL.submitData(tVData, "") == false) {
			logger.debug("-------收付费--收 费--理赔二核收费发票打印（ClaimCheckPrintUI）---失败！----");
		}
		int n = tClaimCheckPrintBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tClaimCheckPrintBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}
}
