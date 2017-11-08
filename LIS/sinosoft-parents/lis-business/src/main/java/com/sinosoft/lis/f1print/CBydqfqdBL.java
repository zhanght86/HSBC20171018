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

public class CBydqfqdBL implements PrintService {
private static Logger logger = Logger.getLogger(CBydqfqdBL.class);
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
	SQLwithBindVariables sqlbv = new SQLwithBindVariables();
	Vector tryNewWay = new Vector();
	private String strManagecom = "";
	private String strPrtDate = "";
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

	public CBydqfqdBL() {
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
		strPrtDate = (String) cInputData.get(1);
		logger.debug("BL 打印日期：" + strPrtDate);
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
		cError.moduleName = "CBydqfqdBL";
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
		ExeSQL exeSql = new ExeSQL();
		newXmlExport = new XmlExport();
		xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		String remark = "YDqfqd.vts";
		xmlexport.createDocument(remark, "");
		newXmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
		newXmlExport.setTemplateName("YDqfqd.vts");
		logger.debug("机构信息长度：" + strManagecom.length());
		boolean flag = false;

		if (strManagecom.length() == 8) {
			logger.debug("按照营业区打印");
			strSQL = "select "
					+ "(select c.proposalcontno from lccont c where c.contno = a.contno)," // 投保单号。1
					+ "a.contno," // 保单号。2
					+ "(select d.appntname from lccont d where d.contno = a.contno)," // 投保人。3
					+ "(select e.insuredname from lccont e where e.contno = a.contno)," // 被保险人。4
					+ "(select f.prem from lccont f where f.contno = a.contno)," // 保费。5
					+ "(select z.name from lacom z where z.agentcom = "
					+ "(select w.agentcom from lccont w where w.contno = a.contno))," // 储蓄所6
					+ "(select g.agentcode from lccont g where g.contno = a.contno)," // 业务员号。7
					+ "(select h.name from laagent h where h.agentcode = trim"
					+ "((select i.agentcode from lccont i where i.contno = a.contno)))," // 业务员姓名。8
					+ "(select q.agentgroup from lccont q where q.contno = a.contno) as juewang," // 营业组(代码)9
					+ "(select xx.name from labranchgroup xx where xx.agentgroup = "
					+ "(select k.agentgroup from lccont k where k.contno = a.contno))," // 营业组(名)10
					+ "(select s.signdate from lccont s where s.contno = a.contno)," // 签单日期11
					+ "(select riskcode from lcpol t where polno=mainpolno  and t.contno = a.contno),"
					// 险种代码12
					+ "(select polapplydate from lccont u where u.contno = a.contno)," // 投保日期13
					+ "(select companyphone from lcaddress v where v.customerno = (select appntno from lccont w"
					+ " where w.contno = a.contno) and addressno = "
					+ "(select addressno from lcappnt ap1 where ap1.contno = a.contno))," // 联系电话14
					+ "(select postaladdress from lcaddress vv where vv.customerno = (select appntno from lccont ww"
					+ " where ww.contno = a.contno) and addressno = "
					+ "(select addressno from lcappnt ap3 where ap3.contno = a.contno))," // 联系地址15
					+ "(select mobile from lcaddress vxx where vxx.customerno = (select appntno from lccont wx"
					+ " where wx.contno = a.contno) and addressno = "
					+ "(select addressno from lcappnt ap2 where ap2.contno = a.contno))," // 移动电话16
					+ "Substr(a.invoiceno, 1, 12)"// 发票号码17
					+ " from ldcontinvoicemap a  where a.opertype = '1'"
					+ " and a.makedate = '"
					+ "?strPrtDate?"
					+ "'"
					+ " and exists (select 'x' from lccont j where j.contno = a.contno and  j.salechnl = '3'"
					+ " and j.managecom = '" + "?strManagecom?"
					+ "') order by juewang";
			sqlbv.sql(strSQL);
			sqlbv.put("strPrtDate", strPrtDate);
			sqlbv.put("strManagecom", strManagecom);
			try {
				SSRS ssrs = exeSql.execSQL(sqlbv);
				int i_count = ssrs.getMaxRow();
				if (i_count == 0) {
					this.buildError("getPrintData", "没有查询到相关数据！");
					return false;
				}

				TextTag texttag = new TextTag(); // Create a TextTag instance
				ListTable alistTable = new ListTable();
				alistTable.setName("RISK"); // Appoint to a sepcial flag
				logger.debug(strManagecom.substring(0, 4));
				logger.debug(strManagecom.substring(0, 6));
				logger.debug(getComName(strManagecom.substring(0, 4)));
				logger.debug(getComName(strManagecom.substring(0, 6)));
				logger.debug(getComName(strManagecom));
				String[] colst = new String[16];
				colst[0] = "分公司：";
				colst[1] = getName(strManagecom.substring(0, 4));
				colst[2] = "中心支公司：";
				colst[3] = getName(strManagecom.substring(0, 6));
				colst[4] = "营销服务部:";
				colst[5] = getName(strManagecom);
				alistTable.add(colst);

				String[] colx = new String[16];
				colx[0] = "";
				colx[1] = "";
				colx[2] = "";
				colx[3] = "";
				colx[4] = "";
				colx[5] = "";
				colx[6] = "";
				colx[7] = "";
				colx[8] = "";
				colx[9] = "";
				colx[10] = "";
				colx[11] = "";
				colx[12] = "";
				colx[13] = "";
				colx[14] = "";
				colx[15] = "";
				alistTable.add(colx);

				String[] colss = new String[16];
				colss[0] = "序号";
				colss[1] = "投保单号";
				colss[2] = "保单号";
				colss[3] = "投保人";
				colss[4] = "被保险人";
				colss[5] = "保费";
				colss[6] = "储蓄所";
				colss[7] = "业务员号";
				colss[8] = "业务员";
				colss[9] = "业务分部";
				colss[10] = "签单日期";
				colss[11] = "险种代码";
				colss[12] = "投保日期";
				colss[13] = "电话";
				colss[14] = "联系地址";
				colss[15] = "发票号码";

				alistTable.add(colss);

				for (int i = 1; i <= i_count; i++) {
					String[] cols = new String[16];
					cols[0] = i + ""; // 序号
					cols[1] = ssrs.GetText(i, 1); // 投保单号
					cols[2] = ssrs.GetText(i, 2); // 保单号
					cols[3] = ssrs.GetText(i, 3); // 投保人
					cols[4] = ssrs.GetText(i, 4); // 被保险人
					cols[5] = ssrs.GetText(i, 5); // 保费
					cols[6] = ssrs.GetText(i, 6); // 储蓄所

					cols[7] = ssrs.GetText(i, 7); // 业务员号
					cols[8] = ssrs.GetText(i, 8); // 业务员
					cols[9] = getUpComName(ssrs.GetText(i, 9)); // 业务分部
					cols[10] = ssrs.GetText(i, 11); // 签单日期
					cols[11] = ssrs.GetText(i, 12); // 险种代码
					cols[12] = ssrs.GetText(i, 13); // 投保日期
					if (ssrs.GetText(i, 16) != null
							&& ssrs.GetText(i, 16) != "") {
						cols[13] = ssrs.GetText(i, 16);
					} else {
						cols[13] = ssrs.GetText(i, 14);
					} // 电话
					cols[14] = ssrs.GetText(i, 15); // 联系地址
					cols[15] = ssrs.GetText(i, 17); // 发票号码
					alistTable.add(cols);
				}
				String[] colsss = new String[16];
				colsss[0] = "合计：";
				colsss[1] = "(营业区)" + i_count;
				alistTable.add(colsss);

				texttag = new TextTag();
				texttag.add("IssueDate", strPrtDate);
				texttag.add("Count", "" + i_count);

				String[] col = new String[16];
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
			strSQL = "select comcode from ldcom where comcode like concat('"+ "?strManagecom?" + "','%') and char_length(trim(comcode))=8";
			sqlbv.sql(strSQL);
			sqlbv.put("strManagecom", strManagecom);
			SSRS tSSRS = new SSRS();
			tSSRS = exeSql.execSQL(sqlbv);
			for (int p = 1; p <= tSSRS.getMaxRow(); p++) {
				logger.debug(tSSRS.GetText(p, 1));
			}
			for (int k = 1; k <= tSSRS.getMaxRow(); k++) {

				strSQL = "select "
						+ "(select c.proposalcontno from lccont c where c.contno = a.contno)," // 投保单号。1
						+ "a.contno," // 保单号。2
						+ "(select d.appntname from lccont d where d.contno = a.contno)," // 投保人。3
						+ "(select e.insuredname from lccont e where e.contno = a.contno)," // 被保险人。4
						+ "(select f.prem from lccont f where f.contno = a.contno)," // 保费。5
						+ "(select z.name from lacom z where z.agentcom = "
						+ "(select w.agentcom from lccont w where w.contno = a.contno))," // 储蓄所6
						+ "(select g.agentcode from lccont g where g.contno = a.contno)," // 业务员号。7
						+ "(select h.name from laagent h where h.agentcode = trim"
						+ "((select i.agentcode from lccont i where i.contno = a.contno)))," // 业务员姓名。8
						+ "(select q.agentgroup from lccont q where q.contno = a.contno) as juewang," // 营业组(代码)9
						+ "(select xx.name from labranchgroup xx where xx.agentgroup = "
						+ "(select k.agentgroup from lccont k where k.contno = a.contno))," // 营业组(名)10
						+ "(select s.signdate from lccont s where s.contno = a.contno)," // 签单日期11
						+ "(select riskcode from lcpol t where polno=mainpolno and t.contno = a.contno),"
						// 险种代码12
						+ "(select polapplydate from lccont u where u.contno = a.contno)," // 投保日期13
						+ "(select companyphone from lcaddress v where v.customerno = (select appntno from lccont w"
						+ " where w.contno = a.contno) and addressno = "
						+ "(select addressno from lcappnt ap3 where ap3.contno = a.contno))," // 联系电话14
						+ "(select postaladdress from lcaddress vv where vv.customerno = (select appntno from lccont ww"
						+ " where ww.contno = a.contno) and addressno = "
						+ "(select addressno from lcappnt ap1 where ap1.contno = a.contno))," // 联系地址15
						+ "(select mobile from lcaddress vx where vx.customerno = (select appntno from lccont wx"
						+ " where wx.contno = a.contno) and addressno = "
						+ "(select addressno from lcappnt ap2 where ap2.contno = a.contno))," // 移动电话16
						+ "Substr(a.invoiceno, 1, 12)"
						+ " from ldcontinvoicemap a  where a.opertype = '1'"
						+ " and a.makedate = '"
						+ "?strPrtDate?"
						+ "'"
						+ " and exists (select 'x' from lccont j where j.contno = a.contno and  j.salechnl = '3'"
						+ " and j.managecom = '" + "?managecom?"
						+ "') order by juewang";
				sqlbv.sql(strSQL);
				sqlbv.put("strPrtDate", strPrtDate);
				sqlbv.put("managecom", tSSRS.GetText(k, 1));
				try {
					SSRS ssrs = exeSql.execSQL(sqlbv);
					int i_count = ssrs.getMaxRow();
					if (i_count == 0) {
						continue;
					}
					fflag = true;
					allcount = allcount + i_count;

					String[] colst = new String[16];
					colst[0] = "分公司：";
					colst[1] = getName(tSSRS.GetText(k, 1).substring(0, 4));
					colst[2] = "中心支公司：";
					colst[3] = getName(tSSRS.GetText(k, 1).substring(0, 6));
					colst[4] = "营销服务部:";
					colst[5] = getName(tSSRS.GetText(k, 1));
					alistTable.add(colst);

					String[] colx = new String[16];
					colx[0] = "";
					colx[1] = "";
					colx[2] = "";
					colx[3] = "";
					colx[4] = "";
					colx[5] = "";
					colx[6] = "";
					colx[7] = "";
					colx[8] = "";
					colx[9] = "";
					colx[10] = "";
					colx[11] = "";
					colx[12] = "";
					colx[13] = "";
					colx[14] = "";
					colx[15] = "";
					alistTable.add(colx);

					String[] colss = new String[16];
					colss[0] = "序号";
					colss[1] = "投保单号";
					colss[2] = "保单号";
					colss[3] = "投保人";
					colss[4] = "被保险人";
					colss[5] = "保费";
					colss[6] = "储蓄所";
					colss[7] = "业务员号";
					colss[8] = "业务员";
					colss[9] = "业务分部";
					colss[10] = "签单日期";
					colss[11] = "险种代码";
					colss[12] = "投保日期";
					colss[13] = "电话";
					colss[14] = "联系地址";
					colss[15] = "发票号码";
					alistTable.add(colss);

					for (int i = 1; i <= i_count; i++) {
						String[] cols = new String[16];
						cols[0] = i + ""; // 序号
						cols[1] = ssrs.GetText(i, 1); // 投保单号
						cols[2] = ssrs.GetText(i, 2); // 保单号
						cols[3] = ssrs.GetText(i, 3); // 投保人
						cols[4] = ssrs.GetText(i, 4); // 被保险人
						cols[5] = ssrs.GetText(i, 5); // 保费
						cols[6] = ssrs.GetText(i, 6); // 储蓄所

						cols[7] = ssrs.GetText(i, 7); // 业务员号
						cols[8] = ssrs.GetText(i, 8); // 业务员
						cols[9] = getUpComName(ssrs.GetText(i, 9)); // 业务分部
						cols[10] = ssrs.GetText(i, 11); // 签单日期
						cols[11] = ssrs.GetText(i, 12); // 险种代码
						cols[12] = ssrs.GetText(i, 13); // 投保日期
						if (ssrs.GetText(i, 16) != null
								&& ssrs.GetText(i, 16) != "") {
							cols[13] = ssrs.GetText(i, 16);
						} else {
							cols[13] = ssrs.GetText(i, 14);
						} // 电话
						cols[14] = ssrs.GetText(i, 15); // 联系地址
						cols[15] = ssrs.GetText(i, 17); // 发票号码
						alistTable.add(cols);
					}

					String[] colsss = new String[16];
					colsss[0] = "合计：";
					colsss[1] = "(营业区)" + i_count;
					alistTable.add(colsss);
					String[] cola = new String[16];
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
					cola[13] = "";
					cola[14] = "";
					cola[15] = "";

					alistTable.add(cola);

					String[] colb = new String[16];
					colb[0] = "";
					colb[1] = "";
					colb[2] = "";
					colb[3] = "";
					colb[4] = "";
					colb[5] = "";
					colb[6] = "";
					colb[7] = "";
					colb[8] = "";
					colb[9] = "";
					colb[10] = "";
					colb[11] = "";
					colb[12] = "";
					colb[13] = "";
					colb[14] = "";
					colb[15] = "";

					alistTable.add(colb);
					texttag = new TextTag();
					texttag.add("IssueDate", strPrtDate);
					texttag.add("Count", "" + allcount);

				} catch (Exception ex) {
					this.buildError("getPrintData", "没有查询到相关数据！");
					return false;
				}
			}
			if (fflag == false) {
				this.buildError("getPrintData", "中心支公司没有数据！");
				return false;
			} else {
				String[] col = new String[16];
				logger.debug("texttag.size=" + texttag.size());
				if (texttag.size() > 0) {
					xmlexport.addTextTag(texttag);
				}
				xmlexport.addListTable(alistTable, col);
				logger.debug("清单打印成功！");
				mResult.addElement(xmlexport);
			}

		} else {
			this.buildError("getPrintData", "只能按照营业区或者中心支公司打印！");
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
		CBydqfqdBL tCBydqfqdBL = new CBydqfqdBL();
		tCBydqfqdBL.submitData(tVData, "PRINT");
	}
}
