package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
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
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author Gaoht
 * @version 1.0
 */
public class LJCertifyTempFeeGetPrtBL {
private static Logger logger = Logger.getLogger(LJCertifyTempFeeGetPrtBL.class);

	private TransferData mTransferData = new TransferData();
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	private String mActuGetNo = "";

	public LJCertifyTempFeeGetPrtBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		mResult.clear();
		// 准备所有要打印的数据
		if (!getPrintData()) {
			logger.debug("未找到打印数据");
			return false;
		}

		return true;
	}

	private boolean getPrintData() {
		SSRS nSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();

		String tSql = "select drawer,drawerID from ljcertifyget where actugetno='"
				+ "?mActuGetNo?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("mActuGetNo", mActuGetNo);
		nSSRS = tExeSQL.execSQL(sqlbv1);
		String tDrawer = nSSRS.GetText(1, 1);
		String tDrawerID = nSSRS.GetText(1, 2);
		logger.debug("证件号=================" + tDrawerID);
		logger.debug("姓名=================" + tDrawer);
		tSql = "select paymoney,to_char(paydate,'YYYY'),to_char(paydate,'MM'),to_char(paydate,'DD'),idtype from ljcertifytempfee where idno='"
				+ "?tDrawerID?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSql);
		sqlbv2.put("tDrawerID", tDrawerID);
		nSSRS = new SSRS();
		tExeSQL = new ExeSQL();
		nSSRS = tExeSQL.execSQL(sqlbv2);
		String tPayMoney = nSSRS.GetText(1, 1);
		String tYear = nSSRS.GetText(1, 2);
		String tMonth = nSSRS.GetText(1, 3);
		String tDay = nSSRS.GetText(1, 4);
		String tIDType = nSSRS.GetText(1, 5);

		tSql = "select to_char(now(),'YYYY'),to_char(now(),'MM'),to_char(now(),'DD') from dual ";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSql);
		nSSRS = new SSRS();
		tExeSQL = new ExeSQL();
		nSSRS = tExeSQL.execSQL(sqlbv3);
		String tNowYear = nSSRS.GetText(1, 1);
		String tNowMonth = nSSRS.GetText(1, 2);
		String tNowDay = nSSRS.GetText(1, 3);

		tSql = "select codename from ldcode where codetype='idtype' and code='"
				+ "?tIDType?" + "'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tSql);
		sqlbv4.put("tIDType", tIDType);
		nSSRS = new SSRS();
		tExeSQL = new ExeSQL();
		nSSRS = tExeSQL.execSQL(sqlbv4);
		tIDType = nSSRS.GetText(1, 1);

		logger.debug("================本次打印的信息=================");
		logger.debug("姓名=================" + tDrawer);
		logger.debug("证件类型=================" + tIDType);
		logger.debug("证件号=================" + tDrawerID);
		logger.debug("交费年=================" + tYear);
		logger.debug("交费月=================" + tMonth);
		logger.debug("交费日=================" + tDay);
		logger.debug("当前年=================" + tNowYear);
		logger.debug("当前月=================" + tNowMonth);
		logger.debug("当前日=================" + tNowDay);
		logger.debug("金额=================" + tPayMoney);

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("CertifyTempFeeGet.vts", "PRINT"); // 最好紧接着就初始化xml文档
		texttag.add("GetNoticeNo", mActuGetNo);
		texttag.add("Name", tDrawer);
		texttag.add("Year", tYear);
		texttag.add("Month", tMonth);
		texttag.add("Day", tDay);
		texttag.add("Money", tPayMoney);
		texttag.add("IDType", tIDType);
		texttag.add("IDNo", tDrawerID);
		texttag.add("NowYear", tNowYear);
		texttag.add("NowMonth", tNowMonth);
		texttag.add("NowDay", tNowDay);

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		mResult.clear();
		mResult.addElement(xmlexport);

		return true;
	}

	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		mActuGetNo = (String) mTransferData.getValueByName("ActugetNo");
		if (mActuGetNo.equals(null) || mActuGetNo == "") {
			CError.buildErr(this, "没有得到足够的数据，请您确认!");
			return false;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	public static void main(String[] args) {
		LJCertifyTempFeeGetPrtBL ljcertifytempfeegetprtbl = new LJCertifyTempFeeGetPrtBL();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ActugetNo", "370000000010612");
		VData tVData = new VData();
		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "86";
		tG.Operator = "0001";
		tVData.add(tG);
		tVData.add(tTransferData);
		ljcertifytempfeegetprtbl.submitData(tVData, "");
	}
}
