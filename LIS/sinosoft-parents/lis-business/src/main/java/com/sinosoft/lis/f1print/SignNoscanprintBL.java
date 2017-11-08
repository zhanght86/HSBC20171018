package com.sinosoft.lis.f1print;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author 刘岩松 function :print InputEfficiency or print CheckEfficiency Business
 *         Logic layer
 * @version 1.0
 * @date 2003-04-04
 */
// import org.jdom.Element;

public class SignNoscanprintBL implements PrintService {
private static Logger logger = Logger.getLogger(SignNoscanprintBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	private int printLisNo = 0;

	private int intTotalQ = 0;
	private int intComTotalQ = 0;
	private int flag = 0;
	private int districtNo = 1;
	private int subComNo = 1;
	private String strSQL = "";
	Vector tryNewWay = new Vector();
	// **************************
	private String strManagecom = "";
	private String startDate = "";
	private String endDate = "";
	// *************************
	private String prtSeq = "";
	private String strManageCom = "";
	private GlobalInput mGlobalInput = new GlobalInput();
	private String subCom = "";
	private String districtCode = "";
	private String comName = "";
	private String strIssueDate = PubFun.getCurrentDate();
	private String districtName = "";
	private boolean fflag = false;
	private int allcount = 0;
	private XmlExport xmlexport;
	private XmlExport newXmlExport;

	public SignNoscanprintBL() {
	}

	public CErrors getErrors() {
		return this.mErrors;
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

	/***************************************************************************
	 * Name : getInputData() function :receive data from jsp and check date
	 * check :judge whether startdate begin enddate return :true or false
	 */
	private boolean getInputData(VData cInputData) {
		strManagecom = (String) cInputData.get(0);
		logger.debug("BL  管理机构：" + strManagecom);
		startDate = (String) cInputData.get(1);
		logger.debug("BL 打印日期：" + startDate);
		endDate = (String) cInputData.get(2);
		logger.debug("BL 打印日期：" + endDate);
		return true;

	}

	public VData getResult() {
		return this.mResult;
	}

	/***************************************************************************
	 * name : buildError function : Prompt error message return :error message
	 */

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "CBgrqfqdBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/***************************************************************************
	 * name : getPrintData() function :get print data parameter : null return
	 * :true or false
	 */

	private boolean getPrintData() {

		// 选择临时文件读取目录
		int allcount = 0;
		ExeSQL exeSql = new ExeSQL();
		newXmlExport = new XmlExport();
		xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		String remark = "SignNoscan.vts";
		xmlexport.createDocument(remark, "");
		newXmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
		newXmlExport.setTemplateName("SignNoscan.vts");
		logger.debug("机构信息长度：" + strManagecom.length());

		if (strManagecom.length() == 8) {
			logger.debug("按照营业区打印");
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				strSQL = "select a.proposalcontno,"
						+ "a.contno,"
						+ "a.makedate,"
						+ "(select b.makedate"
						+ " from ljtempfee b"
						+ " where b.otherno = a.contno"
						+ " and rownum = 1),"
						+ "a.signdate,"
						+ "a.appntname,"
						+ "a.insuredname,"
						+ "(select sum(c.sumactupaymoney)"
						+ " from ljapayperson c"
						+ " where c.contno = a.contno),"
						+ "(case when (select codename"
						+ " from ldcode"
						+ " where codetype = 'paymode '"
						+ " and code = (select paymode"
						+ " from ljtempfeeclass"
						+ " where otherno = a.prtno"
						+ " and rownum = 1)) is not null then (select codename"
						+ " from ldcode"
						+ " where codetype = 'paymode '"
						+ " and code = (select paymode"
						+ " from ljtempfeeclass"
						+ " where otherno = a.prtno"
						+ " and rownum = 1))  else '未交费' end),"
						+ "(select name from laagent where agentcode = trim(a.agentcode)),"
						+ "a.managecom,"
						+ "(select branchcode from laagent where agentcode = trim(a.agentcode))"
						+ " from lccont a"
						+ " where a.cardflag = '3'"
						+ " and a.appflag = '1'"
						+ " and a.conttype = '1'"
						+ // 个险
						" and a.managecom like concat('"
						+ "?strManagecom?"
						+ "','%')"
						+ " and a.makedate between '"
						+ "?startDate?"
						+ "' and '"
						+ "?endDate?"
						+ "'"
						+ " and not exists(select 'x' from es_doc_relation where bussno = trim(a.proposalcontno))"
						+ " order by a.makedate";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				strSQL = "select a.proposalcontno,"
						+ "a.contno,"
						+ "a.makedate,"
						+ "(select b.makedate"
						+ " from ljtempfee b"
						+ " where b.otherno = a.contno"
						+ " limit 0,1),"
						+ "a.signdate,"
						+ "a.appntname,"
						+ "a.insuredname,"
						+ "(select sum(c.sumactupaymoney)"
						+ " from ljapayperson c"
						+ " where c.contno = a.contno),"
						+ "(case when (select codename"
						+ " from ldcode"
						+ " where codetype = 'paymode '"
						+ " and code = (select paymode"
						+ " from ljtempfeeclass"
						+ " where otherno = a.prtno"
						+ " limit 0,1)) is not null then (select codename"
						+ " from ldcode"
						+ " where codetype = 'paymode '"
						+ " and code = (select paymode"
						+ " from ljtempfeeclass"
						+ " where otherno = a.prtno"
						+ " limit 0,1))  else '未交费' end),"
						+ "(select name from laagent where agentcode = trim(a.agentcode)),"
						+ "a.managecom,"
						+ "(select branchcode from laagent where agentcode = trim(a.agentcode))"
						+ " from lccont a"
						+ " where a.cardflag = '3'"
						+ " and a.appflag = '1'"
						+ " and a.conttype = '1'"
						+ // 个险
						" and a.managecom like concat('"
						+ "?strManagecom?"
						+ "','%')"
						+ " and a.makedate between '"
						+ "?startDate?"
						+ "' and '"
						+ "?endDate?"
						+ "'"
						+ " and not exists(select 'x' from es_doc_relation where bussno = trim(a.proposalcontno))"
						+ " order by a.makedate";
			}
			try {
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(strSQL);
				sqlbv1.put("strManagecom", strManagecom);
				sqlbv1.put("startDate", startDate);
				sqlbv1.put("endDate", endDate);
				SSRS ssrs = exeSql.execSQL(sqlbv1);
				int i_count = ssrs.getMaxRow();
				if (i_count == 0) {
					this.buildError("getPrintData", "没有查询到相关数据！");
					return false;
				}

				TextTag texttag = new TextTag(); // Create a TextTag instance
				ListTable alistTable = new ListTable();
				alistTable.setName("RISK"); // Appoint to a sepcial flag
				String[] colst = new String[13];
				colst[0] = "中支：";
				colst[1] = getName(strManagecom.substring(0, 6));
				colst[2] = "营销服务部:";
				colst[3] = getName(strManagecom);
				alistTable.add(colst);

				// String[] colx = new String[13];
				// colx[0] = "";
				// colx[1] = "";
				// colx[2] = "";
				// colx[3] = "";
				// colx[4] = "";
				// colx[5] = "";
				// colx[6] = "";
				// colx[7] = "";
				// colx[8] = "";
				// colx[9] = "";
				// colx[10] = "";
				// colx[11] = "";
				// colx[12] = "";
				// alistTable.add(colx);

				String[] colss = new String[13];
				colss[0] = "序号";
				colss[1] = "投保单号";
				colss[2] = "保单号";
				colss[3] = "录单日期";
				colss[4] = "收费日期";
				colss[5] = "签单日期";
				colss[6] = "投保人";
				colss[7] = "被保人";
				colss[8] = "保费";
				colss[9] = "交费方式";
				colss[10] = "业务员";
				colss[11] = "营销服务部";
				colss[12] = "营业部";
				alistTable.add(colss);

				for (int i = 1; i <= i_count; i++) {
					String[] cols = new String[13];
					cols[0] = i + ""; // 序号
					cols[1] = ssrs.GetText(i, 1); // 投保单号
					cols[2] = ssrs.GetText(i, 2); // 保单号
					cols[3] = ssrs.GetText(i, 3); // 录单日期
					cols[4] = ssrs.GetText(i, 4); // 收费日期
					cols[5] = ssrs.GetText(i, 5); // 签单日期
					cols[6] = ssrs.GetText(i, 6); // 投保人
					cols[7] = ssrs.GetText(i, 7); // 被保人
					cols[8] = ssrs.GetText(i, 8); // 保费
					cols[9] = ssrs.GetText(i, 9); // 交费方式
					cols[10] = ssrs.GetText(i, 10); // 业务员
					cols[11] = getName(ssrs.GetText(i, 11)); // 营销服务部
					cols[12] = getUpComName(ssrs.GetText(i, 12)); // 营业部
					alistTable.add(cols);
				}
				String[] colsss = new String[13];
				colsss[0] = "合计：";
				colsss[1] = "" + i_count;
				alistTable.add(colsss);

				allcount = allcount + i_count;
				texttag = new TextTag();

				texttag.add("IssueDate", startDate + "至" + endDate);
				texttag.add("PrtDate", StrTool.getYear() + "年"
						+ StrTool.getMonth() + "月" + StrTool.getDay() + "日");
				texttag.add("Count", "" + allcount);

				String[] col = new String[13];
				logger.debug("texttag.size=" + texttag.size());
				if (texttag.size() > 0) {
					xmlexport.addTextTag(texttag);
				}
				xmlexport.addListTable(alistTable, col);
				logger.debug("清单打印成功！");
				// newXmlExport.outputDocumentToFile("d:\\","PersonBillSucc");//输出xml文档到文件
				mResult.addElement(xmlexport);
			} catch (Exception ex) {
				this.buildError("getPrintData", "没有查询到相关数据！");
				return false;
			}
		}

		else if (strManagecom.length() == 6) {
			logger.debug("按照中心支公司打印");
			TextTag texttag = new TextTag();
			ListTable alistTable = new ListTable();
			alistTable.setName("RISK");
			strSQL = "select comcode from ldcom where comcode like concat('"
					+ "?strManagecom?" + "','%') and char_length(trim(comcode))=8";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(strSQL);
			sqlbv2.put("strManagecom", strManagecom);
			SSRS tSSRS = new SSRS();
			tSSRS = exeSql.execSQL(sqlbv2);
			for (int p = 1; p <= tSSRS.getMaxRow(); p++) {
				logger.debug(tSSRS.GetText(p, 1));
			}
			for (int k = 1; k <= tSSRS.getMaxRow(); k++) {
				if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
					strSQL = "select a.proposalcontno,"
							+ "a.contno,"
							+ "a.makedate,"
							+ "(select b.makedate"
							+ " from ljtempfee b"
							+ " where b.otherno = a.contno"
							+ " and rownum = 1),"
							+ "a.signdate,"
							+ "a.appntname,"
							+ "a.insuredname,"
							+ "(select sum(c.sumactupaymoney)"
							+ " from ljapayperson c"
							+ " where c.contno = a.contno),"
							+ "(case when (select codename"
							+ " from ldcode"
							+ " where codetype = 'paymode '"
							+ " and code = (select paymode"
							+ " from ljtempfeeclass"
							+ " where otherno = a.prtno"
							+ " and rownum = 1)) is not null then (select codename"
							+ " from ldcode"
							+ " where codetype = 'paymode '"
							+ " and code = (select paymode"
							+ " from ljtempfeeclass"
							+ " where otherno = a.prtno"
							+ " and rownum = 1))  else '未交费' end),"
							+ "(select name from laagent where agentcode = trim(a.agentcode)),"
							+ "a.managecom,"
							+ "(select branchcode from laagent where agentcode = trim(a.agentcode))"
							+ " from lccont a"
							+ " where a.cardflag = '3'"
							+ " and a.appflag = '1'"
							+ " and a.conttype = '1'"
							+ // 个险
							" and a.managecom like concat('"
							+ "?managecom?"
							+ "','%')"
							+ " and a.makedate between '"
							+ "?startDate?"
							+ "' and '"
							+ "?endDate?"
							+ "'"
							+ " and not exists(select 'x' from es_doc_relation where bussno = trim(a.proposalcontno))"
							+ " order by a.makedate";
				}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
					strSQL = "select a.proposalcontno,"
							+ "a.contno,"
							+ "a.makedate,"
							+ "(select b.makedate"
							+ " from ljtempfee b"
							+ " where b.otherno = a.contno"
							+ " limit 0,1),"
							+ "a.signdate,"
							+ "a.appntname,"
							+ "a.insuredname,"
							+ "(select sum(c.sumactupaymoney)"
							+ " from ljapayperson c"
							+ " where c.contno = a.contno),"
							+ "(case when (select codename"
							+ " from ldcode"
							+ " where codetype = 'paymode '"
							+ " and code = (select paymode"
							+ " from ljtempfeeclass"
							+ " where otherno = a.prtno"
							+ " limit 0,1)) is not null then (select codename"
							+ " from ldcode"
							+ " where codetype = 'paymode '"
							+ " and code = (select paymode"
							+ " from ljtempfeeclass"
							+ " where otherno = a.prtno"
							+ " limit 0,1))  else '未交费' end),"
							+ "(select name from laagent where agentcode = trim(a.agentcode)),"
							+ "a.managecom,"
							+ "(select branchcode from laagent where agentcode = trim(a.agentcode))"
							+ " from lccont a"
							+ " where a.cardflag = '3'"
							+ " and a.appflag = '1'"
							+ " and a.conttype = '1'"
							+ // 个险
							" and a.managecom like concat('"
							+ "?managecom?"
							+ "','%')"
							+ " and a.makedate between '"
							+ "?startDate?"
							+ "' and '"
							+ "?endDate?"
							+ "'"
							+ " and not exists(select 'x' from es_doc_relation where bussno = trim(a.proposalcontno))"
							+ " order by a.makedate";
				}
				try {
					SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
					sqlbv3.sql(strSQL);
					sqlbv3.put("managecom", tSSRS.GetText(k, 1));
					sqlbv3.put("startDate", startDate);
					sqlbv3.put("endDate", endDate);
					SSRS ssrs = exeSql.execSQL(sqlbv3);
					int i_count = ssrs.getMaxRow();
					if (i_count == 0) {
						continue;
					}
					fflag = true;
					allcount = allcount + i_count;

					String[] colst = new String[13];
					colst[0] = "中支：";
					colst[1] = getName(tSSRS.GetText(k, 1).substring(0, 6));
					colst[2] = "营销服务部:";
					colst[3] = getName(tSSRS.GetText(k, 1));
					alistTable.add(colst);

					// String[] colx = new String[13];
					// colx[0] = "";
					// colx[1] = "";
					// colx[2] = "";
					// colx[3] = "";
					// colx[4] = "";
					// colx[5] = "";
					// colx[6] = "";
					// colx[7] = "";
					// colx[8] = "";
					// colx[9] = "";
					// colx[10] = "";
					// colx[11] = "";
					// colx[12] = "";
					// alistTable.add(colx);

					String[] colss = new String[13];
					colss[0] = "序号";
					colss[1] = "投保单号";
					colss[2] = "保单号";
					colss[3] = "录单日期";
					colss[4] = "收费日期";
					colss[5] = "签单日期";
					colss[6] = "投保人";
					colss[7] = "被保人";
					colss[8] = "保费";
					colss[9] = "交费方式";
					colss[10] = "业务员";
					colss[11] = "营销服务部";
					colss[12] = "营业部";
					alistTable.add(colss);

					for (int i = 1; i <= i_count; i++) {
						String[] cols = new String[13];
						cols[0] = i + ""; // 序号
						cols[1] = ssrs.GetText(i, 1); // 投保单号
						cols[2] = ssrs.GetText(i, 2); // 保单号
						cols[3] = ssrs.GetText(i, 3); // 录单日期
						cols[4] = ssrs.GetText(i, 4); // 收费日期
						cols[5] = ssrs.GetText(i, 5); // 签单日期
						cols[6] = ssrs.GetText(i, 6); // 投保人
						cols[7] = ssrs.GetText(i, 7); // 被保人
						cols[8] = ssrs.GetText(i, 8); // 保费
						cols[9] = ssrs.GetText(i, 9); // 交费方式
						cols[10] = ssrs.GetText(i, 10); // 业务员
						cols[11] = getName(ssrs.GetText(i, 11)); // 营销服务部
						cols[12] = getUpComName(ssrs.GetText(i, 12)); // 营业部
						alistTable.add(cols);
					}

					String[] colsss = new String[13];
					colsss[0] = "合计：";
					colsss[1] = "" + i_count;
					alistTable.add(colsss);
					String[] cola = new String[13];
					cola[0] = "";
					cola[1] = "";
					cola[2] = "";
					cola[3] = "";
					cola[4] = "";
					cola[5] = "";
					cola[6] = "";
					cola[7] = "";
					cola[8] = "";
					cola[9] = "";
					cola[10] = "";
					cola[11] = "";
					cola[12] = "";
					alistTable.add(cola);

					// String[] colb = new String[13];
					// colb[0] = "";
					// colb[1] = "";
					// colb[2] = "";
					// colb[3] = "";
					// colb[4] = "";
					// colb[5] = "";
					// colb[6] = "";
					// colb[7] = "";
					// colb[8] = "";
					// colb[9] = "";
					// colb[10] = "";
					// cola[11] = "";
					// cola[12] = "";
					// alistTable.add(colb);

				} catch (Exception ex) {
					this.buildError("getPrintData", "没有查询到相关数据！");
					return false;
				}
			}
			if (fflag == false) {
				this.buildError("getPrintData", "中心支公司没有数据！");
				return false;
			} else {
				String[] col = new String[13];
				texttag.add("IssueDate", startDate + "至" + endDate);
				texttag.add("PrtDate", StrTool.getYear() + "年"
						+ StrTool.getMonth() + "月" + StrTool.getDay() + "日");
				texttag.add("Count", "" + allcount);
				if (texttag.size() > 0) {
					xmlexport.addTextTag(texttag);
				}
				xmlexport.addListTable(alistTable, col);
				logger.debug("清单打印成功！");
				mResult.addElement(xmlexport);
			}

		} else if (strManagecom.length() == 4) {
			logger.debug("按照分公司打印");
			TextTag texttag = new TextTag();
			ListTable alistTable = new ListTable();
			alistTable.setName("RISK");
			strSQL = "select comcode from ldcom where comcode like concat('"
					+ "?strManagecom?" + "','%') and char_length(trim(comcode))=6";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(strSQL);
			sqlbv4.put("strManagecom", strManagecom);
			SSRS tSSRS = new SSRS();
			tSSRS = exeSql.execSQL(sqlbv4);
			for (int p = 1; p <= tSSRS.getMaxRow(); p++) {
				logger.debug(tSSRS.GetText(p, 1));
			}
			for (int k = 1; k <= tSSRS.getMaxRow(); k++) {
				if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
					strSQL = "select a.proposalcontno,"
							+ "a.contno,"
							+ "a.makedate,"
							+ "(select b.makedate"
							+ " from ljtempfee b"
							+ " where b.otherno = a.contno"
							+ " and rownum = 1),"
							+ "a.signdate,"
							+ "a.appntname,"
							+ "a.insuredname,"
							+ "(select sum(c.sumactupaymoney)"
							+ " from ljapayperson c"
							+ " where c.contno = a.contno),"
							+ "(case when (select codename"
							+ " from ldcode"
							+ " where codetype = 'paymode '"
							+ " and code = (select paymode"
							+ " from ljtempfeeclass"
							+ " where otherno = a.prtno"
							+ " and rownum = 1)) is not null then (select codename"
							+ " from ldcode"
							+ " where codetype = 'paymode '"
							+ " and code = (select paymode"
							+ " from ljtempfeeclass"
							+ " where otherno = a.prtno"
							+ " and rownum = 1))  else '未交费' end),"
							+ "(select name from laagent where agentcode = trim(a.agentcode)),"
							+ "a.managecom,"
							+ "(select branchcode from laagent where agentcode = trim(a.agentcode))"
							+ " from lccont a"
							+ " where a.cardflag = '3'"
							+ " and a.appflag = '1'"
							+ " and a.conttype = '1'"
							+ // 个险
							" and a.managecom like concat('"
							+ "?managecom?"
							+ "','%')"
							+ " and a.makedate between '"
							+ "?startDate?"
							+ "' and '"
							+ "?endDate?"
							+ "'"
							+ " and not exists(select 'x' from es_doc_relation where bussno = trim(a.proposalcontno))"
							+ " order by a.makedate";
				}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
					strSQL = "select a.proposalcontno,"
							+ "a.contno,"
							+ "a.makedate,"
							+ "(select b.makedate"
							+ " from ljtempfee b"
							+ " where b.otherno = a.contno"
							+ " limit 0,1),"
							+ "a.signdate,"
							+ "a.appntname,"
							+ "a.insuredname,"
							+ "(select sum(c.sumactupaymoney)"
							+ " from ljapayperson c"
							+ " where c.contno = a.contno),"
							+ "(case when (select codename"
							+ " from ldcode"
							+ " where codetype = 'paymode '"
							+ " and code = (select paymode"
							+ " from ljtempfeeclass"
							+ " where otherno = a.prtno"
							+ " limit 0,1)) is not null then (select codename"
							+ " from ldcode"
							+ " where codetype = 'paymode '"
							+ " and code = (select paymode"
							+ " from ljtempfeeclass"
							+ " where otherno = a.prtno"
							+ " limit 0,1))  else '未交费' end),"
							+ "(select name from laagent where agentcode = trim(a.agentcode)),"
							+ "a.managecom,"
							+ "(select branchcode from laagent where agentcode = trim(a.agentcode))"
							+ " from lccont a"
							+ " where a.cardflag = '3'"
							+ " and a.appflag = '1'"
							+ " and a.conttype = '1'"
							+ // 个险
							" and a.managecom like concat('"
							+ "?managecom?"
							+ "','%')"
							+ " and a.makedate between '"
							+ "?startDate?"
							+ "' and '"
							+ "?endDate?"
							+ "'"
							+ " and not exists(select 'x' from es_doc_relation where bussno = trim(a.proposalcontno))"
							+ " order by a.makedate";
				}
				
				try {
					SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
					sqlbv5.sql(strSQL);
					sqlbv5.put("managecom", tSSRS.GetText(k, 1));
					sqlbv5.put("startDate", startDate);
					sqlbv5.put("endDate", endDate);
					SSRS ssrs = exeSql.execSQL(sqlbv5);
					int i_count = ssrs.getMaxRow();
					if (i_count == 0) {
						continue;
					}
					fflag = true;
					allcount = allcount + i_count;

					String[] colst = new String[13];
					colst[0] = "分公司：";
					colst[1] = getName(tSSRS.GetText(k, 1).substring(0, 4));
					colst[2] = "中支:";
					colst[3] = getName(tSSRS.GetText(k, 1));
					alistTable.add(colst);

					// String[] colx = new String[13];
					// colx[0] = "";
					// colx[1] = "";
					// colx[2] = "";
					// colx[3] = "";
					// colx[4] = "";
					// colx[5] = "";
					// colx[6] = "";
					// colx[7] = "";
					// colx[8] = "";
					// colx[9] = "";
					// colx[10] = "";
					// colx[11] = "";
					// colx[12] = "";
					// alistTable.add(colx);

					String[] colss = new String[13];
					colss[0] = "序号";
					colss[1] = "投保单号";
					colss[2] = "保单号";
					colss[3] = "录单日期";
					colss[4] = "收费日期";
					colss[5] = "签单日期";
					colss[6] = "投保人";
					colss[7] = "被保人";
					colss[8] = "保费";
					colss[9] = "交费方式";
					colss[10] = "业务员";
					colss[11] = "营销服务部";
					colss[12] = "营业部";
					alistTable.add(colss);

					for (int i = 1; i <= i_count; i++) {
						String[] cols = new String[13];
						cols[0] = i + ""; // 序号
						cols[1] = ssrs.GetText(i, 1); // 投保单号
						cols[2] = ssrs.GetText(i, 2); // 保单号
						cols[3] = ssrs.GetText(i, 3); // 录单日期
						cols[4] = ssrs.GetText(i, 4); // 收费日期
						cols[5] = ssrs.GetText(i, 5); // 签单日期
						cols[6] = ssrs.GetText(i, 6); // 投保人
						cols[7] = ssrs.GetText(i, 7); // 被保人
						cols[8] = ssrs.GetText(i, 8); // 保费
						cols[9] = ssrs.GetText(i, 9); // 交费方式
						cols[10] = ssrs.GetText(i, 10); // 业务员
						cols[11] = getName(ssrs.GetText(i, 11)); // 营销服务部
						cols[12] = getUpComName(ssrs.GetText(i, 12)); // 营业部
						alistTable.add(cols);
					}

					String[] colsss = new String[13];
					colsss[0] = "合计：";
					colsss[1] = "" + i_count;
					alistTable.add(colsss);
					String[] cola = new String[13];
					cola[0] = "";
					cola[1] = "";
					cola[2] = "";
					cola[3] = "";
					cola[4] = "";
					cola[5] = "";
					cola[6] = "";
					cola[7] = "";
					cola[8] = "";
					cola[9] = "";
					cola[10] = "";
					cola[11] = "";
					cola[12] = "";
					alistTable.add(cola);

					// String[] colb = new String[13];
					// colb[0] = "";
					// colb[1] = "";
					// colb[2] = "";
					// colb[3] = "";
					// colb[4] = "";
					// colb[5] = "";
					// colb[6] = "";
					// colb[7] = "";
					// colb[8] = "";
					// colb[9] = "";
					// colb[10] = "";
					// cola[11] = "";
					// cola[12] = "";
					// alistTable.add(colb);

				} catch (Exception ex) {
					this.buildError("getPrintData", "没有查询到相关数据！");
					return false;
				}
			}
			if (fflag == false) {
				this.buildError("getPrintData", "分公司没有数据！");
				return false;
			} else {
				String[] col = new String[13];
				texttag.add("IssueDate", startDate + "至" + endDate);
				texttag.add("PrtDate", StrTool.getYear() + "年"
						+ StrTool.getMonth() + "月" + StrTool.getDay() + "日");
				texttag.add("Count", "" + allcount);
				if (texttag.size() > 0) {
					xmlexport.addTextTag(texttag);
				}
				xmlexport.addListTable(alistTable, col);
				logger.debug("清单打印成功！");
				mResult.addElement(xmlexport);
			}

		} else {
			this.buildError("getPrintData", "只能按照分公司或者中心支公司打印！");
			return false;
		}
		return true;
	}

	public String getUpComName(String comcode) {
		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		LABranchGroupDB ttLABranchGroupDB = new LABranchGroupDB();
		tLABranchGroupDB.setAgentGroup(comcode);
		if (!tLABranchGroupDB.getInfo()) {
			this.buildError("getUpComName", comcode + "机构不存在！");
			return null;
		} else {
			ttLABranchGroupDB.setAgentGroup(tLABranchGroupDB.getUpBranch());
			if (!ttLABranchGroupDB.getInfo()) {
				this.buildError("getUpComName", "在查找comcode所属的营业部时出错！");
				return null;
			} else {
				return ttLABranchGroupDB.getName();
			}
		}
	}

	public String getComName(String comcode) {
		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		tLABranchGroupDB.setAgentGroup(comcode);
		if (!tLABranchGroupDB.getInfo()) {
			this.buildError("getUpComName", "comcode机构不存在！");
			return null;
		} else {
			return tLABranchGroupDB.getName();
		}
	}

	public String getName(String comcode) {
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(comcode);
		if (!tLDComDB.getInfo()) {
			this.buildError("getUpComName", "comcode机构不存在！");
			return null;
		} else {
			return tLDComDB.getName();
		}
	}

	public static void main(String[] args) {

		VData tVData = new VData();
		tVData.addElement("865100");
		tVData.addElement("2005-12-16");
		SignNoscanprintBL tSignNoscanprintBL = new SignNoscanprintBL();
		tSignNoscanprintBL.submitData(tVData, "PRINT");
	}
}
