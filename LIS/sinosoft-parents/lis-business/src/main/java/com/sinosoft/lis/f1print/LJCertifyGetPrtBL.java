package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.LJCertifyGetAgentDB;
import com.sinosoft.lis.db.LJCertifyGetDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LJCertifyGetAgentSchema;
import com.sinosoft.lis.schema.LJCertifyGetSchema;
import com.sinosoft.lis.vschema.LJCertifyGetAgentSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
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
public class LJCertifyGetPrtBL {
private static Logger logger = Logger.getLogger(LJCertifyGetPrtBL.class);
	private TransferData mTransferData = new TransferData();
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	private String mActuGetNo = "";
	private LJCertifyGetSchema mLJCertifyGetSchema = new LJCertifyGetSchema();
	private LJCertifyGetAgentSet mLJCertifyGetAgentSet = new LJCertifyGetAgentSet();

	public LJCertifyGetPrtBL() {
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

		// String tSql = "select * from ljcertifyget where actugetno='"+mActuGetNo+"'";
		LJCertifyGetDB tLJCertifyGetDB = new LJCertifyGetDB();

		tLJCertifyGetDB.setActuGetNo(mActuGetNo);

		if (!tLJCertifyGetDB.getInfo()) {
			CError.buildErr(this, "未找到未付数据");
			return false;
		}
		mLJCertifyGetSchema = tLJCertifyGetDB.getSchema();
		LJCertifyGetAgentDB tLJCertifyGetAgentDB = new LJCertifyGetAgentDB();
		tLJCertifyGetAgentDB.setActuGetNo(mActuGetNo);
		mLJCertifyGetAgentSet = tLJCertifyGetAgentDB.query();

		if (mLJCertifyGetAgentSet.size() == 0) {
			CError.buildErr(this, "未找到未付数据");
			return false;
		}

		if (mLJCertifyGetSchema.getEnterAccDate() == null
				|| mLJCertifyGetSchema.getEnterAccDate().equals("")) {
			CError.buildErr(this, "请先进行付费再打印凭证");
			return false;
		}

		return true;
	}

	private boolean getPrintData() {

		String tDrawer = "";
		String tDrawerID = "";
		String tBJ = "";
		String tKK = "";
		String tMoney = "";
		String tMoneyCH = "";
		String tAcDrawer = "";
		String tAcDrawerID = "";
		String tDate = "";
		String tOperater = "";

		tDrawer = mLJCertifyGetSchema.getDrawer();
		tDrawerID = mLJCertifyGetSchema.getDrawerID();
		DecimalFormat df = new DecimalFormat("0.00");
		tMoney = df.format(mLJCertifyGetSchema.getSumGetMoney());
		tMoneyCH = PubFun.getChnMoney(mLJCertifyGetSchema.getSumGetMoney());
		tAcDrawer = mLJCertifyGetSchema.getActualDrawer();
		tAcDrawerID = mLJCertifyGetSchema.getActualDrawerID();
		tDate = mLJCertifyGetSchema.getEnterAccDate();
		tOperater = mGlobalInput.Operator;

		double bj = 0;
		double kk = 0;
		for (int i = 1; i <= mLJCertifyGetAgentSet.size(); i++) {
			LJCertifyGetAgentSchema tLJCertifyGetAgentSchema = new LJCertifyGetAgentSchema();
			tLJCertifyGetAgentSchema = mLJCertifyGetAgentSet.get(i);
			if (tLJCertifyGetAgentSchema.getFeeFinaType().equals("TF")) {
				bj = bj + tLJCertifyGetAgentSchema.getGetMoney();
			} else {
				kk = kk + tLJCertifyGetAgentSchema.getGetMoney();
			}
		}
		tBJ = df.format(bj);
		tKK = df.format(-kk);

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("CertifyGetPrint.vts", "PRINT"); // 最好紧接着就初始化xml文档
		texttag.add("Drawer", tDrawer);
		texttag.add("DrawerID", tDrawerID);
		texttag.add("BJ", tBJ);
		texttag.add("KK", tKK);
		texttag.add("Money", tMoney);
		texttag.add("MoneyCH", tMoneyCH);
		texttag.add("AcDrawer", tAcDrawer);
		texttag.add("AcDrawerID", tAcDrawerID);
		texttag.add("Date", tDate);
		texttag.add("Operater", tOperater);

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		mResult.clear();
		mResult.addElement(xmlexport);

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	public static void main(String[] args) {
		LJCertifyGetPrtBL ljcertifygetprtbl = new LJCertifyGetPrtBL();
	}
}
