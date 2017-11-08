package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LJAGetDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
/**
 * <p>Title: 保单账户领取打印</p>
 * <p>Description: 打印保单账户领取凭证</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: sinosoft</p>
 * @author guanwei
 * @version 1.0
 */

public class RNContAccGetPrintBL {
private static Logger logger = Logger.getLogger(RNContAccGetPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	private String mGetNoticeNo = "";
	private String mContNo = "";

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private LCContDB mLCContDB = new LCContDB();
	private LCContSet mLCContSet = new LCContSet();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LJAGetDB mLJAGetDB = new LJAGetDB();
	private LJAGetSet mLJAGetSet = new LJAGetSet();
	private LJAGetSchema mLJAGetSchema = new LJAGetSchema();

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		mResult.clear();

		// 准备所有要打印的数据
		if (!getPrintData()) {
			return false;
		}
		return true;
	}

	/**
	 * 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		String mContNo = "880000000822";
		String mGetNoticeNo = "370210000000025";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ContNo", mContNo);
		tTransferData.setNameAndValue("GetNoticeNo", mGetNoticeNo);
		logger.debug("ContNo:" + mContNo);
		logger.debug("GetNoticeNo:" + mGetNoticeNo);
		VData tVData = new VData();
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "862100";

		tVData.addElement(tTransferData);
		tVData.add(tG);
		RNContAccGetPrintBL tRNContAccGetPrintBL = new RNContAccGetPrintBL();
		tRNContAccGetPrintBL.submitData(tVData, "PRINT");
		VData result = new VData();
		result = tRNContAccGetPrintBL.getResult();
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量

		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		mGetNoticeNo = (String) mTransferData.getValueByName("GetNoticeNo"); // 缴费通知书号
		mContNo = (String) mTransferData.getValueByName("ContNo"); // 缴费通知书号

		return true;
	}

	/**
	 * 获取返回信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return this.mResult;
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
		cError.moduleName = "RNContAccGetPrintBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 打印处理
	 * 
	 * @return boolean
	 */
	private boolean getPrintData() {
		// 新建一个TextTag的实例
		TextTag texttag = new TextTag();
		// 新建一个XmlExport的实例
		XmlExport xmlexport = new XmlExport();
		// 最好紧接着就初始化xml文档
		xmlexport.createDocument("RNContAccGetNotice.vts", "PRINT");

		mLCContDB.setContNo(mContNo);
		mLCContSchema = mLCContDB.query().get(1);
		mLJAGetDB.setGetNoticeNo(mGetNoticeNo);
		mLJAGetSchema = mLJAGetDB.query().get(1);

		// 金额
		double tAccBalance = mLCContSchema.getDif();
		double tGetMoney = mLJAGetSchema.getSumGetMoney();
		double tAccMoney = tAccBalance + tGetMoney;

		// 领取方式
		SSRS tSSRS = new SSRS();
		String tSQL = "select CodeName from LDCode where Codetype = 'gettype'"
				+ "and Code = '" + "?Code?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSQL);
		sqlbv1.put("Code", mLJAGetSchema.getPayMode());
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv1);
		if (tSSRS.getMaxRow() > 0) {
			String tGetMode = tSSRS.GetText(1, 1);
			texttag.add("GetMode", tGetMode);
			logger.debug("GetMode into vts" + tGetMode);
		}

		// 经办单位
		tSQL = "select Name from LDCom where ComCode = '"
				+ "?ComCode?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSQL);
		sqlbv2.put("ComCode", mGlobalInput.ManageCom);
		tSSRS = tExeSQL.execSQL(sqlbv2);
		if (tSSRS.getMaxRow() > 0) {
			String tOperateCom = tSSRS.GetText(1, 1);
			texttag.add("OperateCom", tOperateCom);
			logger.debug("OperateCom into vts" + tOperateCom);
		}

		// 代理人及其区部组
		String tAgentCode = mLJAGetSchema.getAgentCode();
		tSQL = "select Name,AgentGroup from LAAgent where AgentCode = '"
				+ "?tAgentCode?" + "'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSQL);
		sqlbv3.put("tAgentCode", tAgentCode);
		tSSRS = tExeSQL.execSQL(sqlbv3);
		if (tSSRS.getMaxRow() > 0) {
			String tAgentName = tSSRS.GetText(1, 1);
			texttag.add("AgentName", tAgentName);
			logger.debug("AgentName into vts" + tAgentName);
			tSQL = "select getbranchglobalname('" + "?var1?"
					+ "') from dual";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(tSQL);
			sqlbv4.put("var1", tSSRS.GetText(1, 2));
			tSSRS = tExeSQL.execSQL(sqlbv4);
			String tAgentGroup = tSSRS.GetText(1, 1);
			texttag.add("AgentGroup", tAgentGroup);
			logger.debug("AgentGroup into vts" + tAgentGroup);
		}

		// 客户姓名
		texttag.add("CustomerName", mLCContSchema.getAppntName());
		logger.debug("CustomerName into vts"
				+ mLCContSchema.getAppntName());
		// 领取日期
		texttag.add("GetYear", mLJAGetSchema.getMakeDate().substring(0, 4));
		logger.debug("GetYear into vts"
				+ mLJAGetSchema.getMakeDate().substring(0, 4));
		texttag.add("GetMonth", mLJAGetSchema.getMakeDate().substring(5, 7));
		logger.debug("GetMonth into vts"
				+ mLJAGetSchema.getMakeDate().substring(5, 7));
		texttag.add("GetDate", mLJAGetSchema.getMakeDate().substring(8, 10));
		logger.debug("GetDate into vts"
				+ mLJAGetSchema.getMakeDate().substring(8, 10));
		// 保单号
		texttag.add("ContNo", mContNo);
		logger.debug("ContNo into vts" + mContNo);
		// 通知书号
		texttag.add("GetNoticeNo", mGetNoticeNo);
		logger.debug("GetNoticeNo into vts" + mGetNoticeNo);
		// 投保人姓名
		texttag.add("AppntName", mLCContSchema.getAppntName());
		logger.debug("AppntName into vts" + mLCContSchema.getAppntName());
		// 账户金额（本次领取前）
		texttag.add("AccMoney", String.valueOf(tAccMoney));
		logger.debug("AccMoney into vts" + String.valueOf(tAccMoney));
		// 领取金额
		texttag.add("GetMoney", String.valueOf(tGetMoney));
		logger.debug("GetMoney into vts" + String.valueOf(tGetMoney));
		// 账户余额（本次领取后）
		texttag.add("AccBalance", String.valueOf(tAccBalance));
		logger.debug("AccBalance into vts" + String.valueOf(tAccBalance));
		texttag.add("SumGetMoneyChn", PubFun.getChnMoney(tGetMoney));
		logger.debug("SumGetMoneyChn into vts"
				+ PubFun.getChnMoney(tGetMoney));

		texttag.add("Operator", mGlobalInput.Operator);
		logger.debug("Operator into vts" + mGlobalInput.Operator);

		texttag.add("AgentCode", tAgentCode);
		logger.debug("AgentName into vts" + tAgentCode);

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		mResult.addElement(xmlexport);
		return true;
	}

}
