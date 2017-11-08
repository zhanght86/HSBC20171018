package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * function :print InputEfficiency or print CheckEfficiency Business Logic layer
 * @version 1.0
 * @date 2003-04-04
 */
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class GrpFaultyReportLisBL {
private static Logger logger = Logger.getLogger(GrpFaultyReportLisBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	private GlobalInput mGI = new GlobalInput();// 操作员信息
	private TransferData mTransferData = new TransferData();// 传递非SCHEMA信息

	private String mManageCom;// 管理机构
	private String mStartIssueDate;// 问题开始日期
	private String mEndIssueDate;// 问题件
	private String mPrintDate;// 打印日期

	private String mOperator;// 管理机构

	private String mCurrentDate = PubFun.getCurrentDate();// 当前日期

	private GlobalInput mGlobalInput = new GlobalInput();

	public GrpFaultyReportLisBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		if (!getInputData(cInputData)) {
			return false;
		}
		mResult.clear();

		if (!getPrintData()) {
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData) {
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (mGlobalInput == null) {
			buildError("getInputData", "获取传入的操作员登录信息失败。");
			return false;
		}

		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mTransferData == null) {
			buildError("getInputData", "获取传入的管理机构及日期信息失败。");
			return false;
		}

		mOperator = mGlobalInput.Operator;
		mManageCom = (String) mTransferData.getValueByName("ManageCom"); // 管理机构
		mStartIssueDate = (String) mTransferData
				.getValueByName("StartIssueDate"); // 问题日期
		mEndIssueDate = (String) mTransferData.getValueByName("EndIssueDate"); // 问题件
		mPrintDate = (String) mTransferData.getValueByName("PrintDate"); // 打印日期

		if (mManageCom == null || mManageCom.trim().equals("")) {
			buildError("getInputData", "传入的管理机构信息为空。");
			return false;
		}
		if (mStartIssueDate == null || mStartIssueDate.trim().equals("")) {
			buildError("getInputData", "传入的起始日期信息为空。");
			return false;
		}
		if (mEndIssueDate == null || mEndIssueDate.trim().equals("")) {
			buildError("getInputData", "传入的终止日期信息为空。");
			return false;
		}
		if (mPrintDate == null || mPrintDate.trim().equals("")) {
			mPrintDate = mCurrentDate;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private boolean getPrintData() {
		// 模板

		// 根据传入的管理机构查询中心支公司。
		String strComSQL = "select a.comcode,a.name,a.upcomcode,"
				+ "(select name from ldcom b where b.comcode=a.upcomcode)"
				+ " from ldcom a where a.comgrade='03'"
				+ " and a.comcode like '" + mManageCom + "%'"
				+ " order by a.comcode";
		ExeSQL ComExeSQL = new ExeSQL();
		SSRS ComSSRS = new SSRS();
		ComSSRS = ComExeSQL.execSQL(strComSQL);
		int Count_ComSSRS = ComSSRS.getMaxRow();
		if (Count_ComSSRS <= 0) {
			return false;
		}
		for (int i = 1; i <= Count_ComSSRS; i++) {
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("GrpFaultyReportLis.vts", "printer");
			TextTag aTextTag = new TextTag();
			ListTable aListTable = new ListTable();
			aListTable.setName("ISSUE");

			String BranchComCode = ComSSRS.GetText(i, 1);// 中心支公司代码
			String BranchComName = ComSSRS.GetText(i, 2);// 中心支公司名称
			String FilialeComCode = ComSSRS.GetText(i, 3);
			;// 分公司代码
			String FilialeComName = ComSSRS.GetText(i, 4);
			;// 分公司名称

			String strIssueSQL = "select distinct b.grpcontno,b.prtseq,a.grpname,a.agentcode,c.name, d.name"
					+ " from lcgrpcont a, lcgrpissuepol b,laagent c ,labranchgroup d"
					+ " where b.grpcontno=a.grpcontno and trim(a.agentcode)=trim(c.agentcode)"
					+ " and c.agentgroup=d.agentgroup"
					+ " and a.managecom like '"
					+ BranchComCode
					+ "%'"
					+ " and b.backobjtype = '2'" // b.backobjtype=2表示外部问题件
					+ " and b.makedate between '"
					+ mStartIssueDate
					+ "' and '"
					+ mEndIssueDate + "'" + " order by b.grpcontno,b.prtseq";
			ExeSQL IssueExeSQL = new ExeSQL();
			SSRS IssueSSRS = new SSRS();
			IssueSSRS = IssueExeSQL.execSQL(strIssueSQL);
			int IssueCount = IssueSSRS.getMaxRow();
			if (IssueCount <= 0) {
				logger.debug("中支公司：" + BranchComCode + "("
						+ BranchComName + ")下无数据。");
				continue;
			}
			for (int j = 1; j <= IssueCount; j++) {
				String[] cols = new String[7];
				cols[0] = j + "";
				cols[1] = IssueSSRS.GetText(j, 1);
				cols[2] = IssueSSRS.GetText(j, 2);
				cols[3] = IssueSSRS.GetText(j, 3);
				cols[4] = IssueSSRS.GetText(j, 4);
				cols[5] = IssueSSRS.GetText(j, 5);
				cols[6] = IssueSSRS.GetText(j, 6);
				aListTable.add(cols);
			}
			String[] col = new String[7];
			xmlexport.addListTable(aListTable, col);

			aTextTag.add("StatiStartDate", StrTool.getChnDate(mStartIssueDate));
			aTextTag.add("StatiEndDate", StrTool.getChnDate(mEndIssueDate));
			aTextTag.add("PrintDate", StrTool.getChnDate(mPrintDate));
			aTextTag.add("FilialeComName", FilialeComName);
			aTextTag.add("BranchComName", BranchComName);
			if (aTextTag.size() > 0) {
				xmlexport.addTextTag(aTextTag);
			}
			if (i == 1) {
				xmlexport.addDisplayControl("displayTitle");
			}
			mResult.addElement(xmlexport);
		}
		if (mResult.size() <= 0) {
			buildError("getInputData", "没有可以打印的数据");
			return false;
		}
		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "GrpFaultyReportLisBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/*
	 * @function :in order to debug
	 */
	public static void main(String[] args) {
		// GlobalInput tGI = new GlobalInput();
		// tGI.ComCode="86";
		// tGI.ManageCom="86";
		// tGI.Operator="001";
		//
		// //传递非SCHEMA信息
		// TransferData tTransferData = new TransferData();
		// tTransferData.setNameAndValue("StartIssueDate", "2006-07-01");
		// tTransferData.setNameAndValue("EndIssueDate", "2006-07-20");
		// tTransferData.setNameAndValue("ManageCom", "8621");
		// tTransferData.setNameAndValue("PrintDate", "2006-07-21");
		//
		// VData tVData = new VData();
		// tVData.addElement(tGI);
		// tVData.addElement(tTransferData);
		//
		// GrpFaultyReportLisBL tGrpFaultyReportLisBL= new GrpFaultyReportLisBL();
		// if(tGrpFaultyReportLisBL.submitData(tVData,"PRINT"))
		// {
		// VData RResult = new VData();
		// RResult = tGrpFaultyReportLisBL.getResult();
		// String[] strVFPathName = new String[RResult.size()]; //临时文件个数。
		// for (int k=0;k<RResult.size();k++)
		// {
		// XmlExport txmlExport = new XmlExport();
		// txmlExport = (XmlExport) RResult.getObjectByObjectName("XmlExport", k);
		// CombineVts tcombineVts = null;
		// String strTemplatePath = "D:/lis/ui/f1print/NCLtemplate/";
		// tcombineVts = new CombineVts(txmlExport.getInputStream(), strTemplatePath);
		// ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		// tcombineVts.output(dataStream);
		// strVFPathName[k] = "D:/lis/ui/vtsfile/" + 001 + "_" + k + "_"
		// +"GrpFaultyReportLis.vts";
		// AccessVtsFile.saveToFile(dataStream, strVFPathName[k]);
		// }
		// VtsFileCombine vtsfilecombine = new VtsFileCombine();
		// try {
		// BookModelImpl tb = vtsfilecombine.dataCombine(strVFPathName);
		// vtsfilecombine.write(tb, "D:/lis/ui/vtsfile/" + 001 +
		// "_newFaultyReportLis.vts");
		// }
		// catch (IOException ex) {
		// }
		// catch (F1Exception ex) {
		// }
		// catch (Exception ex) {
		// }
		//
		// }

	}
}
