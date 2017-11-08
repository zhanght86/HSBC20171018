package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import java.util.StringTokenizer;

import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LLRegisterSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
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
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class RNGrpRenewNoticeBL {
private static Logger logger = Logger.getLogger(RNGrpRenewNoticeBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private String mManageCom = "";
	private VData mResult = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	private TransferData mTransferData = new TransferData();

	public RNGrpRenewNoticeBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
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
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息");
			return false;
		}
		mLOPRTManagerSet = (LOPRTManagerSet) cInputData.getObjectByObjectName(
				"LOPRTManagerSet", 0);
		if (mLOPRTManagerSet.size() == 0) {
			buildError("getInputData", "没有获得前台数据");
			return false;
		}
		logger.debug("本次处理件数::::::::::::::" + mLOPRTManagerSet.size());
		return true;
	}

	private boolean getPrintData() {

		for (int i = 1; i <= mLOPRTManagerSet.size(); i++) {
			XmlExport xmlExport = new XmlExport();
			xmlExport.createDocument("RNGrpRenewNotice.vts", "PRINT");
			String Notice1 = "我公司决定在上述保险期间届满后，不再自动续保，并终止划转相应的保费。";
			String Notice2 = "";
			LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
			tLOPRTManagerSchema = mLOPRTManagerSet.get(i).getSchema();
			String PrtSeq = tLOPRTManagerSchema.getPrtSeq();
			LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
			tLOPRTManagerDB.setPrtSeq(PrtSeq);
			if (!tLOPRTManagerDB.getInfo()) {
				logger.debug("查询打印管理表失败！");
				return false;
			}
			// 查询地址信息
			LCAddressDB tLCAddressDB = new LCAddressDB();
			tLCAddressDB.setCustomerNo(tLOPRTManagerDB.getStandbyFlag5());
			tLCAddressDB.setAddressNo(tLOPRTManagerDB.getStandbyFlag6());
			tLCAddressDB.getInfo();
			// LDPerson
			LDPersonDB tLDPersonDB = new LDPersonDB();
			tLDPersonDB.setCustomerNo(tLOPRTManagerDB.getStandbyFlag5());
			tLDPersonDB.getInfo();

			String Sex = tLDPersonDB.getSex();
			String tSex = "";
			if (Sex != null) {
				if (Sex.equals("0"))
					tSex = "先生";
				else
					tSex = "女士";
			}
			// 拆分年月日
			int p = 0;
			String tYear = "";
			String tMonth = "";
			String tDate = "";
			java.util.StringTokenizer strTok = new StringTokenizer(
					tLOPRTManagerDB.getStandbyFlag4(), "-");
			while (strTok.hasMoreTokens()) {
				if (p == 0) {
					tYear = strTok.nextToken();
				}
				if (p == 1) {
					tMonth = strTok.nextToken();
				}
				if (p == 2) {
					tDate = strTok.nextToken();
				}
				p++;
				if (p == 3)
					break;
			}

			// 查询险种信息
			String AllSql = tLOPRTManagerDB.getStandbyFlag1().trim();
			int RiskInt = AllSql.length() / 8;
			String RiskTxt = "";
			for (int t = 0; t < RiskInt; t++) {
				String tRiskcode = AllSql.substring(8 * t, 8 * (t + 1));
				LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
				tLMRiskAppDB.setRiskCode(tRiskcode);
				tLMRiskAppDB.getInfo();
				if (t > 0) {
					RiskTxt = RiskTxt + "、";
				}
				RiskTxt = RiskTxt + tLMRiskAppDB.getRiskName();
				Notice2 = "如果您想继续投保“" + RiskTxt + "保险”，须在" + tYear + "年"
						+ tMonth + "月" + tDate
						+ "日前向我公司申请续保，我公司有权决定是否承保并有权重新核定费率。";
				if (tRiskcode.equals("00237000")) {
					String EndDate = PubFun.getCurrentDate();
					String StartDate = PubFun.calDate(EndDate, -5, "Y", null);
					logger.debug("237险种查询5年内是否有过理赔立案，时间：" + StartDate
							+ "至" + EndDate + "");
					String LLSql = "select * from llregister where contno='"
							+ tLOPRTManagerDB.getOtherNo()
							+ "' and makedate between '" + StartDate
							+ "' and '" + EndDate + "'";
					logger.debug("=======================" + LLSql);
					LLRegisterDB tLLRegisterDB = new LLRegisterDB();
					LLRegisterSet tLLRegisterSet = new LLRegisterSet();
					tLLRegisterSet = tLLRegisterDB.executeQuery(LLSql);
					if (tLLRegisterSet.size() > 0) {
						Notice1 = "我公司决定在上述保险期间届满后，按续保时被保险人年龄划转相应的保费。";
					} else {
						Notice2 = "如果您想继续投保“" + RiskTxt + "保险”，须在" + tYear
								+ "年" + tMonth + "月" + tDate
								+ "日前根据续保时被保险人年龄所对应的交费标准交纳保费。";
					}
				}
			}

			String riskname = RiskTxt;
			String paytodate = tLOPRTManagerDB.getStandbyFlag4();
			String contno = tLOPRTManagerDB.getOtherNo();
			String AppntName = tLDPersonDB.getName();
			String PostalAddress = tLCAddressDB.getPostalAddress();
			String ZipCode = tLCAddressDB.getZipCode();
			String Phone = tLCAddressDB.getPhone();
			String cname = tLOPRTManagerDB.getStandbyFlag2();
			String cphone = tLOPRTManagerDB.getStandbyFlag3();

			logger.debug("ZipCode==========" + ZipCode);
			logger.debug("Address==========" + PostalAddress);
			logger.debug("AppntName==========" + AppntName);
			logger.debug("PhoneNumber==========" + Phone);
			logger.debug("RiskName==========" + riskname);
			logger.debug("ContNo==========" + contno);
			logger.debug("Date==========" + paytodate);
			logger.debug("Service==========" + cname);
			logger.debug("ServicePhone==========" + cphone);
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("ZipCode", ZipCode);
			texttag.add("Sex", tSex);
			texttag.add("Number", tLDPersonDB.getCustomerNo());
			texttag.add("Service", cname);
			texttag.add("ServicePhone", cphone);
			texttag.add("AppntName", AppntName);
			texttag.add("Address", PostalAddress);
			texttag.add("PhoneNumber", Phone);
			texttag.add("Notice1", Notice1);
			texttag.add("Year", tYear);
			texttag.add("Month", tMonth);
			texttag.add("Date", tDate);
			texttag.add("Notice2", Notice2);
			texttag.add("RiskName", riskname);
			texttag.add("ContNo", contno);

			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}

			mResult.clear();
			mResult.addElement(xmlExport);
		}
		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "LCPolF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public VData getResult() {
		return this.mResult;
	}

	/**
	 * 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String arg[]) {
		TransferData tTransferData = new TransferData();
		GlobalInput mGlobalInput = new GlobalInput();
		VData tVData = new VData();
		LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq("810000000278210");
		tLOPRTManagerSet.add(tLOPRTManagerSchema);
		mGlobalInput.Operator = "001";
		mGlobalInput.ComCode = "863200";
		mGlobalInput.ManageCom = "863200";
		tVData.addElement(mGlobalInput);
		tVData.addElement(tTransferData);
		tVData.add(tLOPRTManagerSet);
		RNGrpRenewNoticeBL tRNGrpRenewNoticeBL = new RNGrpRenewNoticeBL();
		if (!tRNGrpRenewNoticeBL.submitData(tVData, "")) {
			logger.debug("false!!!");
		}
	}

}
