package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
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
 * Company: Sinosoft <S/p>
 * 
 * @author cc
 * @version 1.0
 */
public class ReinsureNPrintBL implements PrintService {
private static Logger logger = Logger.getLogger(ReinsureNPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();

	private String mAgentCode = "";

	// 业务处理相关变量
	/** 全局数据 */

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	private LCContSchema mLCContSchema = new LCContSchema();

	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();

	public ReinsureNPrintBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")) {
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
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mTransferData = ((TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0));
		if (mTransferData == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		return true;
	}

	/**
	 * 返回处理信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误信息
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
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
		cError.moduleName = "ReinsureNPrintBL";
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
		// 根据印刷号查询打印队列中的纪录
		String StartDate = ((String) mTransferData.getValueByName("StartDate"));
		String EndDate = ((String) mTransferData.getValueByName("EndDate"));
		String ManageCom = ((String) mTransferData.getValueByName("ManageCom"));
		String tSql = "select ProposalContNo,(select codename from ldcode where codetype='uqreportstate'"
				+ "and code = ReinsuredResult),"
				+ "ModifyDate,reinsudesc from LCReinsureReport where contno in "
				+ "(select prtno from lccont where ManageCom like concat('"
				+ "?ManageCom?"
				+ "','%')) and ModifyDate between  '"
				+ "?StartDate?"
				+ "' and" + " '" + "?EndDate?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("ManageCom", ManageCom);
		sqlbv1.put("StartDate", StartDate);
		sqlbv1.put("EndDate", EndDate);
		SSRS tempSSRS = new SSRS();
		ExeSQL tempExeSQL = new ExeSQL();
		tempSSRS = tempExeSQL.execSQL(sqlbv1);
		String[] Title = new String[9];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";
		Title[5] = "";
		Title[6] = "";
		Title[7] = "";
		Title[8] = "";

		ListTable ListTable = new ListTable();
		ListTable.setName("RISK");
		if (tempSSRS != null && tempSSRS.getMaxRow() > 0
				&& tempSSRS.getMaxCol() > 0) {
			for (int i = 1; i <= tempSSRS.getMaxRow(); i++) {
				tSql = "select a.contno,"
						+ " a.insuredname,"
						+ " (select b.IDNo from ldperson b where b.customerno = a.insuredno),"
						+ " a.riskcode,"
						+ " a.amnt,"
						+ " (select codename"
						+ " from ldcode"
						+ " where codetype = 'uwstate'"
						+ " and code = a.uwflag),"
						+ " (select sum(suppriskscore) from lcprem where polno = a.polno)"
						+ " from lcpol a" + " where a.prtno = '"
						+ "?prtno?" + "'"
						+ " and exists (select 'c'" + " from lcpol d"
						+ " where d.prtno = a.prtno"
						+ " and d.mainpolno = d.polno"
						+ " and signdate is null)"
						+ " order by insuredname, a.riskcode desc";
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(tSql);
				sqlbv2.put("prtno", tempSSRS.GetText(i, 1));
				SSRS temp2SSRS = new SSRS();
				temp2SSRS = tempExeSQL.execSQL(sqlbv2);

				if (temp2SSRS != null && temp2SSRS.getMaxRow() > 0
						&& temp2SSRS.getMaxCol() > 0) {

					for (int j = 1; j <= temp2SSRS.getMaxRow(); j++) {
						String[] stra = new String[11];

						stra[0] = tempSSRS.GetText(i, 1);
						stra[1] = "";
						stra[2] = temp2SSRS.GetText(j, 2);
						stra[3] = temp2SSRS.GetText(j, 3);
						stra[4] = temp2SSRS.GetText(j, 4);
						stra[5] = temp2SSRS.GetText(j, 5);
						stra[6] = temp2SSRS.GetText(j, 6);
						stra[7] = temp2SSRS.GetText(j, 7);
						stra[8] = tempSSRS.GetText(i, 2);
						stra[9] = tempSSRS.GetText(i, 4);
						stra[10] = tempSSRS.GetText(i, 3);

						ListTable.add(stra);
					}
				}

			}
		}

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例

		xmlexport.createDocument("ReInsureNlis.vts", "");

		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";

		texttag.add("BarCode1", mLOPRTManagerSchema.getCode());
		texttag
				.add(
						"BarCodeParam1",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		texttag.add("BarCode2", mLOPRTManagerSchema.getPrtSeq());
		texttag
				.add(
						"BarCodeParam2",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");

		texttag.add("reissueDate", StartDate + "至" + EndDate);
		texttag.add("statsDate", StrTool.getYear() + "年" + StrTool.getMonth()
				+ "月" + StrTool.getDay() + "日");

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
			xmlexport.addListTable(ListTable, Title);
		}
		// xmlexport.outputDocumentToFile("d:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		logger.debug("xmlexport=" + xmlexport);
		return true;
	}

	private String getComName(String strComCode) {
		LDComDB tLDComDB = new LDComDB();

		tLDComDB.setComCode(strComCode);
		if (!tLDComDB.getInfo()) {
			mErrors.copyAllErrors(tLDComDB.mErrors);
			buildError("outputXML", "在取得Ldcom的数据时发生错误");
		}
		return tLDComDB.getName();
	}

}
