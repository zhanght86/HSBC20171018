package com.sinosoft.lis.f1print;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
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
public class DispatchBonusPrintPackBL implements PrintService {
private static Logger logger = Logger.getLogger(DispatchBonusPrintPackBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();
	private VData mInputData = new VData();
	private VData tVData = new VData();
	private GlobalInput tGI = new GlobalInput();
	private SQLwithBindVariables mSelString = null;
	private String updateStr = "";
	private String templateOp = null;
	private String mContNo = "";
	private String mAgentCode = "";
	private String mManageCom = "";
	private String mSaleChnl = "";
	private String mStartDate = "";
	private String mEndDate = "";
	private String mSearchType = "";
	private String mfiscalyear = "";
	private String mPrinted = "";
	private String mPrinter = "";
	private String tSaleChnl = "";
	private String mFaultInfo = "";
	private String templateNameTemp = "";

	List datasetList = null;
	LOPRTManagerSchema tLOPRTManagerSchema;
	DispatchBonusPrintBL tDispatchBonusPrintBL = null;
	MMap map = new MMap();
	final String PRT_NAME_30 = "30";

	public DispatchBonusPrintPackBL() {
	}

	public boolean submitData(VData cInputData, String cOpertaor) {
		mInputData = (VData) cInputData.clone();

		if (!getInputData()) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean dealData() {
		XmlExport txmlExportAll = new XmlExport();
		ExeSQL exeSql = new ExeSQL();
		SSRS tSSRS = new SSRS();
		mSelString = this.getSQL();
		tSSRS = exeSql.execSQL(mSelString);
		if (tSSRS.MaxRow == 0) {
			buildError("dealData", "没有查到相关信息!");
			return false;
		}
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql("select sysvarvalue From ldsysvar where sysvar='XmlPath'");
		String OppoPath = exeSql.getOneValue(sqlbv1);
		txmlExportAll.createDocuments(mPrinter, tGI);
		logger.debug("共" + tSSRS.MaxRow);
		int j = 0;
		for (int i = 1; i <= tSSRS.MaxRow; i++) {
			String PrtSeq = tSSRS.GetText(i, 1).trim();
			if (PrtSeq.equals("0"))
				continue;
			tLOPRTManagerSchema = new LOPRTManagerSchema();
			tVData = new VData();

			tLOPRTManagerSchema.setPrtSeq(PrtSeq);
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			updateStr = "update LOPRTManager set stateflag = 1 where PrtSeq = '"
					+ "?PrtSeq?" + "'";
			sqlbv2.sql(updateStr);
			sqlbv2.put("PrtSeq", PrtSeq);
			// logger.debug("=======PrtSeq====" + PrtSeq);
			tVData.addElement(tLOPRTManagerSchema);
			tVData.addElement(tGI);
			tDispatchBonusPrintBL = new DispatchBonusPrintBL();
			if (!tDispatchBonusPrintBL.submitData(tVData, "PRINT")) {
				mFaultInfo += " 印刷号" + PrtSeq + ";";
				continue;
			}
			mResult = tDispatchBonusPrintBL.getResult();
			XmlExport txmlExport = (XmlExport) mResult.getObjectByObjectName(
					"XmlExport", 0);
			if (txmlExport == null) {
				mFaultInfo += " 印刷号" + PrtSeq + ";";
				continue;
			}
			j++;
			Element telement = txmlExport.getDocument().getRootElement();
			// 得到模板名,准备生成文件名;
			if (i == 1) {
				templateNameTemp = telement.element("CONTROL").element(
						"TEMPLATE").getTextTrim();
				int n = templateNameTemp.indexOf(".");
				if (n != -1) {
					templateOp = templateNameTemp.substring(0, n);
				}
				templateOp = templateOp + tGI.Operator.trim();
				txmlExportAll.setTemplateName(templateNameTemp);
			}
			map.put(sqlbv2, "UPDATE");
			if (j % 50 == 0 || i == tSSRS.MaxRow) {
				txmlExportAll.addDataSet(txmlExportAll.getDocument()
						.getRootElement(), telement);
				String filename = FileNameQueue.getFileName(OppoPath,
						templateOp);
				logger.debug("生成文件:" + filename);
				txmlExportAll.outputDocumentToFile(OppoPath, filename);
				txmlExportAll = new XmlExport();
				txmlExportAll.createDocuments(mPrinter, tGI);
				txmlExportAll.setTemplateName(templateNameTemp);
				PubSubmit tPs = new PubSubmit();
				VData tVD = new VData();
				tVD.add(map);
				tPs.submitData(tVD, "");
				map = new MMap();
			} else {
				logger.debug("获得中间文件，开始合并");
				txmlExportAll.addDataSet(txmlExportAll.getDocument()
						.getRootElement(), telement);
			}
			tDispatchBonusPrintBL = null;
			tVData = null;
		}
		logger.debug("实际" + j);
		return true;
	}

	private boolean getInputData() {
		tGI.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		TransferData tTransferData = (TransferData) mInputData
				.getObjectByObjectName("TransferData", 0);
		mContNo = (String) tTransferData.getValueByName("ContNo");
		mAgentCode = (String) tTransferData.getValueByName("AgentCode");
		mManageCom = (String) tTransferData.getValueByName("ManageCom");
		mSaleChnl = (String) tTransferData.getValueByName("SaleChnl");
		mStartDate = (String) tTransferData.getValueByName("StartDate");
		mEndDate = (String) tTransferData.getValueByName("EndDate");
		mSearchType = (String) tTransferData.getValueByName("SearchType");
		mfiscalyear = (String) tTransferData.getValueByName("fiscalyear");
		mPrinted = (String) tTransferData.getValueByName("Printed");
		mPrinter = (String) tTransferData.getValueByName("PRINTER");
		logger.debug("ContNo=" + mContNo);
		logger.debug("AgentCode=" + mAgentCode);
		logger.debug("ManageCom=" + mManageCom);
		logger.debug("SaleChnl=" + mSaleChnl);
		logger.debug("StartDate=" + mStartDate);
		logger.debug("EndDate=" + mEndDate);
		logger.debug("PRINTER=" + mPrinter);
		return true;
	}

	private SQLwithBindVariables getSQL() {
		String sql = "";
		sql = "Select t.Prtseq, a.Contno, t.Agentcode, t.Managecom, t.Execom, t.Code";
		sql += " From Loprtmanager t, Lcpol a, Lccont b, Lmriskapp e";
		sql += " Where t.Otherno = a.Polno ";
		if (mContNo != null && !mContNo.trim().equals("")) {
			sql += " and a.contno='" + "?mContNo?" + "'";
		}
		sql += " and t.Code = '" + "?PRT_NAME_30?" + "' And t.Managecom Like concat('"
				+ "?mManageCom?" + "','%')";
		sql += " And to_date(t.Standbyflag2,'yyyy-mm-dd') Between add_months('"
				+ "?mStartDate?" + "',12) And add_months('" + "?mEndDate?" + "',12) ";
		if (mAgentCode != null && !mAgentCode.trim().equals("")) {
			sql += " and t.AgentCode='" + "?mAgentCode?" + "'";
		}
		if (mSaleChnl.equals("1"))
			tSaleChnl = "I";
		else if (mSaleChnl.equals("2"))
			tSaleChnl = "G";
		else
			tSaleChnl = "Y";
		sql += " and a.Contno = b.Contno And a.Polno = a.Mainpolno And e.Riskcode = a.Riskcode And e.Riskprop = '"
				+ "?tSaleChnl?" + "' ";
		if (mfiscalyear != null && !mfiscalyear.trim().equals("")) {
			sql += " and t.standbyflag1='" + "?mfiscalyear?" + "'";
		}
		if (mSearchType != null && !mSearchType.trim().equals("")) {
			if (mSearchType.equals("1"))
				sql += " and (exists (select * from lacommisiondetail where poltype = 1 and grpcontno = a.contno) or exists (select * from laorphanpolicy where contno = a.contno))";
			if (mSearchType.equals("0"))
				sql += " and (exists (select * from lacommisiondetail where poltype = 0 and grpcontno = a.contno))";
		}
		if (mPrinted.equals("0")) {
			sql += "and t.Stateflag = 0 ";
			sql += " and a.appflag = '1' ";
		}
		sql += "  Order By t.Standbyflag2, a.Contno";
		SQLwithBindVariables sqlbvsql = new SQLwithBindVariables();
		sqlbvsql.sql(sql);
		sqlbvsql.put("mContNo", mContNo);
		sqlbvsql.put("PRT_NAME_30", PRT_NAME_30);
		sqlbvsql.put("mManageCom", mManageCom);
		sqlbvsql.put("mStartDate", mStartDate);
		sqlbvsql.put("mEndDate", mEndDate);
		sqlbvsql.put("mAgentCode", mAgentCode);
		sqlbvsql.put("tSaleChnl", tSaleChnl);
		sqlbvsql.put("mfiscalyear", mfiscalyear);
		return sqlbvsql;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "DispatchBonusPrintPackBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public VData getResult() {
		return this.mResult;
	}

	public CErrors getErrors() {
		return this.mErrors;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		DispatchBonusPrintPackBL tDispatchBonusPrintPackBL = new DispatchBonusPrintPackBL();
		GlobalInput tg = new GlobalInput();
		tg.Operator = "001";
		tg.ManageCom = "8621";
		tg.ComCode = "8621";
		TransferData tt = new TransferData();
		tt.setNameAndValue("ContNo", "");
		tt.setNameAndValue("AgentCode", "");
		tt.setNameAndValue("ManageCom", "8621");
		tt.setNameAndValue("SaleChnl", "1");
		tt.setNameAndValue("StartDate", "2005-03-01");
		tt.setNameAndValue("EndDate", "2005-03-27");
		tt.setNameAndValue("fiscalyear", "");
		tt.setNameAndValue("SearchType", "");
		tt.setNameAndValue("Printed", "1");
		tt.setNameAndValue("printercode", "");
		VData tvd = new VData();
		tvd.add(tg);
		tvd.add(tt);
		if (!tDispatchBonusPrintPackBL.submitData(tvd, "PRINT")) {
			logger.debug("Wrong!");
		} else {
			logger.debug("OK!");
		}

	}
}
