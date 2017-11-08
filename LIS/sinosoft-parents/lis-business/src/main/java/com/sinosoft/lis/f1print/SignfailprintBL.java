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

public class SignfailprintBL implements PrintService {
private static Logger logger = Logger.getLogger(SignfailprintBL.class);
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
	private String strManagecom = "";
	private String strSalechnl = "";
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

	public SignfailprintBL() {
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
		strSalechnl = (String) cInputData.get(1);
		logger.debug("BL 销售渠道：" + strSalechnl);
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
		cError.moduleName = "SignfailprintBL";
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
		String remark = "Signfail.vts";
		xmlexport.createDocument(remark, "");
		newXmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
		newXmlExport.setTemplateName("Signfail.vts");
		logger.debug("机构信息长度：" + strManagecom.length());

		if (strManagecom.length() == 8) {
			logger.debug("按照营业区打印");
			if (strSalechnl == null || strSalechnl.equals("")) {
//				strSQL = "select missionprop1 from (select missionprop1,missionprop7 from lwmission where activityid = '0000001150'"
//						+ " and processid = '0000000003' and missionprop7 like '"
				strSQL = "select missionprop1 from (select missionprop1,missionprop7 from lwmission where activityid in "
						+ " (select activityid from lwactivity  where functionid = '10010042') and missionprop7 like concat('"
						+ "?strManagecom?"
						+ "','%') "
						+ " union "
						+ " select contno,managecom from lccont where appflag = '0' "
						+ " and cardflag = '3' and managecom like concat('"
						+ "?strManagecom?"
						+ "','%')) order by missionprop7"; // 先查出在待签单队列的投保单（包括卡单）
			} else if (strSalechnl != null && strSalechnl.equals("4")) {
				strSQL = " select contno,managecom from lccont where appflag = '0' "
						+ " and cardflag = '3' and managecom like concat('"
						+ "?strManagecom?"
						+ "','%') order by managecom"; // 先查出在待签单队列的卡单
			} else {
//				strSQL = "select missionprop1 from lwmission where activityid = '0000001150'"
//						+ " and processid = '0000000003' and missionprop7 like '"
				strSQL = "select missionprop1 from lwmission where activityid IN "
						+ " (select activityid from lwactivity  where functionid = '10010042') and missionprop7 like concat('"
						+ "?strManagecom?"
						+ "','%') order by missionprop7"; // 先查出在待签单队列的投保单（不包括卡单）
			}
			try {
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(strSQL);
				sqlbv1.put("strManagecom", strManagecom);
				
				SSRS ssrs = exeSql.execSQL(sqlbv1);
				int i_count = ssrs.getMaxRow();
				int scount = 0;
				if (i_count == 0) {
					this.buildError("getPrintData", strManagecom
							+ "机构下没有等待签单的数据！");
					return false;
				}

				TextTag texttag = new TextTag(); // Create a TextTag instance
				ListTable alistTable = new ListTable();
				alistTable.setName("RISK"); // Appoint to a sepcial flag
				String[] colst = new String[12];
				colst[0] = "中支：";
				colst[1] = getName(strManagecom.substring(0, 6));
				colst[2] = "营销服务部:";
				colst[3] = getName(strManagecom);
				alistTable.add(colst);

				String[] colss = new String[12];
				colss[0] = "序号";
				colss[1] = "投保单号";
				colss[2] = "投保日期";
				colss[3] = "生效日期";
				colss[4] = "被保险人";
				colss[5] = "被保险人出生日期";
				colss[6] = "保费";
				colss[7] = "交费方式";
				colss[8] = "业务员";
				colss[9] = "营销服务部";
				colss[10] = "营业部";
				colss[11] = "不成功原因";
				alistTable.add(colss);
				int num = 0;
				for (int p = 0; p < i_count; p++) {
					// 对每张单子进行查询相关信息.
					if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
						strSQL = "select a.contno,a.polapplydate,(case when to_char(a.cvalidate,'YYYY-MM-DD') is not null then to_char(a.cvalidate,'YYYY-MM-DD')  else '无' end),a.insuredname,a.insuredbirthday,"
								+ "(select sum(xx.paymoney) from ljtempfee xx where xx.otherno = a.contno and enteraccdate is not null),"
								+ "(case when (select codename"
								+ " from ldcode"
								+ " where codetype = 'paymode'"
								+ " and code = (select paymode"
								+ " from ljtempfeeclass"
								+ " where otherno = '"
								+ "?contno?"
								+ "'"
								+ " and rownum = 1)) is not null then (select codename"
								+ " from ldcode"
								+ " where codetype = 'paymode'"
								+ " and code = (select paymode"
								+ " from ljtempfeeclass"
								+ " where otherno = '"
								+ "?contno?"
								+ "'"
								+ " and rownum = 1))  else '未交费' end),"
								+ "(select name from laagent where agentcode ="
								+ " trim(a.agentcode)),"
								+ " a.managecom,"
								+ "(select branchcode"
								+ " from laagent"
								+ " where agentcode = trim(a.agentcode)),"
								+ " (select errinfo from lcsignlog"
								+ "  where serino = (select max(serino)"
								+ " from lcsignlog"
								+ " where contno = '"
								+ "?contno?"
								+ "'))"
								+ " from lccont a where contno = '"
								+ "?contno?"
								+ "'"
								+ " and exists(select 'x' from lcsignlog o where o.contno = a.contno)";
					}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
						strSQL = "select a.contno,a.polapplydate,(case when to_char(a.cvalidate,'YYYY-MM-DD') is not null then to_char(a.cvalidate,'YYYY-MM-DD')  else '无' end),a.insuredname,a.insuredbirthday,"
								+ "(select sum(xx.paymoney) from ljtempfee xx where xx.otherno = a.contno and enteraccdate is not null),"
								+ "(case when (select codename"
								+ " from ldcode"
								+ " where codetype = 'paymode'"
								+ " and code = (select paymode"
								+ " from ljtempfeeclass"
								+ " where otherno = '"
								+ "?contno?"
								+ "'"
								+ " limit 0,1)) is not null then (select codename"
								+ " from ldcode"
								+ " where codetype = 'paymode'"
								+ " and code = (select paymode"
								+ " from ljtempfeeclass"
								+ " where otherno = '"
								+ "?contno?"
								+ "'"
								+ " limit 0,1))  else '未交费' end),"
								+ "(select name from laagent where agentcode ="
								+ " trim(a.agentcode)),"
								+ " a.managecom,"
								+ "(select branchcode"
								+ " from laagent"
								+ " where agentcode = trim(a.agentcode)),"
								+ " (select errinfo from lcsignlog"
								+ "  where serino = (select max(serino)"
								+ " from lcsignlog"
								+ " where contno = '"
								+ "?contno?"
								+ "'))"
								+ " from lccont a where contno = '"
								+ "?contno?"
								+ "'"
								+ " and exists(select 'x' from lcsignlog o where o.contno = a.contno)";
					}
					
					// "' and salechnl = '" + strSalechnl + "'";
					if (strSalechnl != null
							&& (strSalechnl.equals("1")
									|| strSalechnl.equals("2") || strSalechnl
									.equals("3"))) {
						strSQL = strSQL + " and salechnl = '" + "?strSalechnl?"
								+ "'";
					} else if (strSalechnl != null && strSalechnl.equals("5")) {
						strSQL = strSQL
								+ " and exists(select 'x' from lmriskapp m where m.riskprop = 'T' and m.riskcode = (select o.riskcode from lcpol o where o.contno = '"
								+ "?contno?"
								+ "' and o.polno = o.mainpolno))";
					}
					SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
					sqlbv2.sql(strSQL);
					sqlbv2.put("contno", ssrs.GetText(p + 1, 1));
					sqlbv2.put("strSalechnl", strSalechnl);
					
					SSRS Onessrs = new SSRS();
					Onessrs = exeSql.execSQL(sqlbv2);
					if (Onessrs.getMaxRow() > 0) {
						num++;
						scount++;
						allcount++;
					}

					if (Onessrs.getMaxRow() == 0) {
						continue;
					}

					String[] cols = new String[12];
					cols[0] = num + ""; // 序号
					cols[1] = ssrs.GetText(p + 1, 1); // 投保单号
					cols[2] = Onessrs.GetText(1, 2); // 投保日期
					cols[3] = Onessrs.GetText(1, 3); // 生效日期
					cols[4] = Onessrs.GetText(1, 4); // 被保险人
					cols[5] = Onessrs.GetText(1, 5); // 被保险人出生日期
					logger.debug(Onessrs.GetText(1, 6));
					if (Onessrs.GetText(1, 6) == "null"
							|| Onessrs.GetText(1, 6) == "") {
						cols[6] = "" + 0;
					} else {
						cols[6] = Onessrs.GetText(1, 6); // 保费
					}
					cols[7] = Onessrs.GetText(1, 7); // 交费方式
					cols[8] = Onessrs.GetText(1, 8); // 业务员
					cols[9] = getName(Onessrs.GetText(1, 9)); // 营销服务部
					cols[10] = getUpComName(Onessrs.GetText(1, 10)); // 营业部
					cols[11] = Onessrs.GetText(1, 11); // 不成功原因
					alistTable.add(cols);
				}

				String[] colsss = new String[12];
				colsss[0] = "合计：";
				colsss[1] = "" + scount;
				alistTable.add(colsss);

				texttag = new TextTag();

				texttag.add("Count", "" + allcount);
				texttag.add("SystemDate", "" + PubFun.getCurrentDate());
				String[] col = new String[12];
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
			strSQL = "select comcode from ldcom where upcomcode = '"
					+ "?strManagecom?" + "'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(strSQL);
			sqlbv3.put("strManagecom", strManagecom);
			SSRS YXssrs = new SSRS();
			YXssrs = exeSql.execSQL(sqlbv3); // 该中支下的营销服务部。
			TextTag texttag = new TextTag(); // Create a TextTag instance
			ListTable alistTable = new ListTable();

			alistTable.setName("RISK"); // Appoint to a sepcial flag

			for (int y = 0; y < YXssrs.getMaxRow(); y++) {
				// strSQL =
				// "select missionprop1 from (select missionprop1,missionprop7
				// from lwmission where activityid = '0000001150'"
				// +
				// " and processid = '0000000003' and missionprop7 like '" +
				// YXssrs.GetText(y + 1, 1) + "%' " +
				// " union " +
				// " select contno,managecom from lccont where appflag = '0' " +
				// " and cardflag = '3' and managecom like '" +
				// YXssrs.GetText(y + 1, 1) +
				// "%') order by missionprop7"; //先查出在待签单队列的投保单（包括卡单）
				if (strSalechnl == null || strSalechnl.equals("")) {
//					strSQL = "select missionprop1 from (select missionprop1,missionprop7 from lwmission where activityid = '0000001150'"
//							+ " and processid = '0000000003' and missionprop7 like '"
					strSQL = "select missionprop1 from (select missionprop1,missionprop7 from lwmission where activityid IN "
							+ " (select activityid from lwactivity  where functionid = '10010042') and missionprop7 like concat('"
							+ "?managecom?"
							+ "','%')"
							+ " union "
							+ " select contno,managecom from lccont where appflag = '0' "
							+ " and cardflag = '3' and managecom like concat('"
							+ "?managecom?"
							+ "','%')) order by missionprop7"; // 先查出在待签单队列的投保单（包括卡单）
				} else if (strSalechnl != null && strSalechnl.equals("4")) {
					strSQL = " select contno,managecom from lccont where appflag = '0' "
							+ " and cardflag = '3' and managecom like concat('"
							+ "?managecom?"
							+ "','%') order by managecom"; // 先查出在待签单队列的卡单
				} else {
//					strSQL = "select missionprop1 from lwmission where activityid = '0000001150'"
//							+ " and processid = '0000000003' and missionprop7 like '"
					strSQL = "select missionprop1 from lwmission where activityid in "
							+ " (select activityid from lwactivity  where functionid = '10010042') and missionprop7 like concat('"
							+ "?managecom?"
							+ "','%') order by missionprop7"; // 先查出在待签单队列的投保单（不包括卡单）
				}
				try {
					SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
					sqlbv4.sql(strSQL);
					sqlbv4.put("managecom", YXssrs.GetText(y + 1, 1));
					SSRS ssrs = exeSql.execSQL(sqlbv4);
					int i_count = ssrs.getMaxRow();
					int scount = 0;
					if (i_count == 0) {
						continue;
					}

					String[] colst = new String[12];
					colst[0] = "中支：";
					colst[1] = getName(YXssrs.GetText(y + 1, 1).substring(0, 6));
					colst[2] = "营销服务部:";
					colst[3] = getName(YXssrs.GetText(y + 1, 1));
					alistTable.add(colst);

					String[] colss = new String[12];
					colss[0] = "序号";
					colss[1] = "投保单号";
					colss[2] = "投保日期";
					colss[3] = "生效日期";
					colss[4] = "被保险人";
					colss[5] = "被保险人出生日期";
					colss[6] = "保费";
					colss[7] = "交费方式";
					colss[8] = "业务员";
					colss[9] = "营销服务部";
					colss[10] = "营业部";
					colss[11] = "不成功原因";
					alistTable.add(colss);
					int num = 0;
					for (int p = 0; p < i_count; p++) { // 对每张单子进行查询相关信息.
						if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
							strSQL = "select a.contno,a.polapplydate,(case when to_char(a.cvalidate,'YYYY-MM-DD') is not null then to_char(a.cvalidate,'YYYY-MM-DD') else '无' end),a.insuredname,a.insuredbirthday,"
									+ "(select sum(xx.paymoney) from ljtempfee xx where xx.otherno = a.contno and enteraccdate is not null),"
									+ "(case when (select codename"
									+ " from ldcode"
									+ " where codetype = 'paymode'"
									+ " and code = (select paymode"
									+ " from ljtempfeeclass"
									+ " where otherno = '"
									+ "?contno?"
									+ "'"
									+ " and rownum = 1)) is not null then (select codename"
									+ " from ldcode"
									+ " where codetype = 'paymode'"
									+ " and code = (select paymode"
									+ " from ljtempfeeclass"
									+ " where otherno = '"
									+ "?contno?"
									+ "'"
									+ " and rownum = 1))  else '未交费' end),"
									+ "(select name from laagent where agentcode ="
									+ " trim(a.agentcode)),"
									+ " a.managecom,"
									+ "(select branchcode"
									+ " from laagent"
									+ " where agentcode = trim(a.agentcode)),"
									+ " (select errinfo from lcsignlog"
									+ "  where serino = (select max(serino)"
									+ " from lcsignlog"
									+ " where contno = '"
									+ "?contno?"
									+ "'))"
									+ " from lccont a where contno = '"
									+ "?contno?"
									+ "'"
									+ " and exists(select 'x' from lcsignlog o where o.contno = a.contno)";
						}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
							strSQL = "select a.contno,a.polapplydate,(case when to_char(a.cvalidate,'YYYY-MM-DD') is not null then to_char(a.cvalidate,'YYYY-MM-DD') else '无' end),a.insuredname,a.insuredbirthday,"
									+ "(select sum(xx.paymoney) from ljtempfee xx where xx.otherno = a.contno and enteraccdate is not null),"
									+ "(case when (select codename"
									+ " from ldcode"
									+ " where codetype = 'paymode'"
									+ " and code = (select paymode"
									+ " from ljtempfeeclass"
									+ " where otherno = '"
									+ "?contno?"
									+ "'"
									+ " limit 0,1)) is not null then (select codename"
									+ " from ldcode"
									+ " where codetype = 'paymode'"
									+ " and code = (select paymode"
									+ " from ljtempfeeclass"
									+ " where otherno = '"
									+ "?contno?"
									+ "'"
									+ " limit 0,1))  else '未交费' end),"
									+ "(select name from laagent where agentcode ="
									+ " trim(a.agentcode)),"
									+ " a.managecom,"
									+ "(select branchcode"
									+ " from laagent"
									+ " where agentcode = trim(a.agentcode)),"
									+ " (select errinfo from lcsignlog"
									+ "  where serino = (select max(serino)"
									+ " from lcsignlog"
									+ " where contno = '"
									+ "?contno?"
									+ "'))"
									+ " from lccont a where contno = '"
									+ "?contno?"
									+ "'"
									+ " and exists(select 'x' from lcsignlog o where o.contno = a.contno)";
						}
						// "' and salechnl = '" + strSalechnl + "'";
						if (strSalechnl != null
								&& (strSalechnl.equals("1")
										|| strSalechnl.equals("2") || strSalechnl
										.equals("3"))) {
							strSQL = strSQL + " and salechnl = '" + "?strSalechnl?"
									+ "'";
						} else if (strSalechnl != null
								&& strSalechnl.equals("5")) {
							strSQL = strSQL
									+ " and exists(select 'x' from lmriskapp m where m.riskprop = 'T' and m.riskcode = (select o.riskcode from lcpol o where o.contno = '"
									+ "?contno?"
									+ "' and o.polno = o.mainpolno))";
						}
                        SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
                        sqlbv5.sql(strSQL);
                        sqlbv5.put("contno", ssrs.GetText(p + 1, 1));
                        sqlbv5.put("strSalechnl", strSalechnl);
						SSRS Onessrs = new SSRS();
						Onessrs = exeSql.execSQL(sqlbv5);
						if (Onessrs.getMaxRow() > 0) {
							num++;
							scount++;
							allcount++;
						}
						if (Onessrs.getMaxRow() == 0) {
							continue;
						}
						// allcount = allcount + scount;
						String[] cols = new String[12];
						cols[0] = num + ""; // 序号
						cols[1] = ssrs.GetText(p + 1, 1); // 投保单号
						cols[2] = Onessrs.GetText(1, 2); // 投保日期
						cols[3] = Onessrs.GetText(1, 3); // 生效日期
						cols[4] = Onessrs.GetText(1, 4); // 被保险人
						cols[5] = Onessrs.GetText(1, 5); // 被保险人出生日期
						if (Onessrs.GetText(1, 6) == "null"
								|| Onessrs.GetText(1, 6) == "") {
							cols[6] = "" + 0;
						} else {
							cols[6] = Onessrs.GetText(1, 6); // 保费
						}
						cols[7] = Onessrs.GetText(1, 7); // 交费方式
						cols[8] = Onessrs.GetText(1, 8); // 业务员
						cols[9] = getName(Onessrs.GetText(1, 9)); // 营销服务部
						cols[10] = getUpComName(Onessrs.GetText(1, 10)); // 营业部
						cols[11] = Onessrs.GetText(1, 11); // 不成功原因
						alistTable.add(cols);
					}
					String[] colsss = new String[12];
					colsss[0] = "合计：";
					colsss[1] = "" + scount;
					alistTable.add(colsss);

				} catch (Exception ex) {
					this.buildError("getPrintData", "没有查询到相关数据！");
					return false;
				}
			}

			texttag = new TextTag();

			texttag.add("Count", "" + allcount);
			texttag.add("SystemDate", "" + PubFun.getCurrentDate());
			String[] col = new String[12];
			logger.debug("texttag.size=" + texttag.size());
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			xmlexport.addListTable(alistTable, col);
			logger.debug("清单打印成功！");
			// newXmlExport.outputDocumentToFile("d:\\","PersonBillSucc");//输出xml文档到文件
			mResult.addElement(xmlexport);

		} else if (strManagecom.length() == 4) { // 按照分公司打印
			logger.debug("按照分公司打印");
			strSQL = "select comcode from ldcom where upcomcode = '"
					+ "?strManagecom?" + "'";
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(strSQL);
			sqlbv6.put("strManagecom", strManagecom);
			SSRS ZZssrs = new SSRS();
			ZZssrs = exeSql.execSQL(sqlbv6); // 该分公司下的中支。
			TextTag texttag = new TextTag(); // Create a TextTag instance
			ListTable alistTable = new ListTable();
			alistTable.setName("RISK"); // Appoint to a sepcial flag
			for (int y = 0; y < ZZssrs.getMaxRow(); y++) {
				// strSQL =
				// "select missionprop1 from (select missionprop1,missionprop7
				// from lwmission where activityid = '0000001150'"
				// +
				// " and processid = '0000000003' and missionprop7 like '" +
				// ZZssrs.GetText(y + 1, 1) + "%' " +
				// " union " +
				// " select contno,managecom from lccont where appflag = '0' " +
				// " and cardflag = '3' and managecom like '" +
				// ZZssrs.GetText(y + 1, 1) +
				// "%') order by missionprop7"; //先查出在待签单队列的投保单（包括卡单）
				if (strSalechnl == null || strSalechnl.equals("")) {
//					strSQL = "select missionprop1 from (select missionprop1,missionprop7 from lwmission where activityid = '0000001150'"
//							+ " and processid = '0000000003' and missionprop7 like '"
					strSQL = "select missionprop1 from (select missionprop1,missionprop7 from lwmission where activityid in "
							+ " (select activityid from lwactivity  where functionid = '10010042') and missionprop7 like concat('"
							+ "?managecom?"
							+ "','%') "
							+ " union "
							+ " select contno,managecom from lccont where appflag = '0' "
							+ " and cardflag = '3' and managecom like concat('"
							+ "?managecom?"
							+ "','%')) order by missionprop7"; // 先查出在待签单队列的投保单（包括卡单）
				} else if (strSalechnl != null && strSalechnl.equals("4")) {
					strSQL = " select contno,managecom from lccont where appflag = '0' "
							+ " and cardflag = '3' and managecom like concat('"
							+ "?managecom?"
							+ "','%')) order by managecom"; // 先查出在待签单队列的卡单
				} else {
//					strSQL = "select missionprop1 from lwmission where activityid = '0000001150'"
//							+ " and processid = '0000000003' and missionprop7 like '"
					strSQL = "select missionprop1 from lwmission where activityid in "
							+ " (select activityid from lwactivity  where functionid = '10010042') and missionprop7 like concat('"
							+ "?managecom?"
							+ "','%') order by missionprop7"; // 先查出在待签单队列的投保单（不包括卡单）
				}
				try {
					SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
					sqlbv7.sql(strSQL);
					sqlbv7.put("managecom", ZZssrs.GetText(y + 1, 1));
					SSRS ssrs = exeSql.execSQL(sqlbv7);
					int i_count = ssrs.getMaxRow();
					int scount = 0;
					if (i_count == 0) {
						continue;
					}

					String[] colst = new String[12];
					colst[0] = "分公司：";
					colst[1] = getName(ZZssrs.GetText(y + 1, 1).substring(0, 4));
					colst[2] = "中支:";
					colst[3] = getName(ZZssrs.GetText(y + 1, 1));
					alistTable.add(colst);

					String[] colss = new String[12];
					colss[0] = "序号";
					colss[1] = "投保单号";
					colss[2] = "投保日期";
					colss[3] = "生效日期";
					colss[4] = "被保险人";
					colss[5] = "被保险人出生日期";
					colss[6] = "保费";
					colss[7] = "交费方式";
					colss[8] = "业务员";
					colss[9] = "营销服务部";
					colss[10] = "营业部";
					colss[11] = "不成功原因";
					alistTable.add(colss);
					int num = 0;
					for (int p = 0; p < i_count; p++) { // 对每张单子进行查询相关信息.
						if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
							strSQL = "select a.contno,a.polapplydate,(case when to_char(a.cvalidate,'YYYY-MM-DD') is not null then to_char(a.cvalidate,'YYYY-MM-DD')  else '无' end),a.insuredname,a.insuredbirthday,"
									+ "(select sum(xx.paymoney) from ljtempfee xx where xx.otherno = a.contno and enteraccdate is not null),"
									+ "(case when (select codename"
									+ " from ldcode"
									+ " where codetype = 'paymode'"
									+ " and code = (select paymode"
									+ " from ljtempfeeclass"
									+ " where otherno = '"
									+ "?contno?"
									+ "'"
									+ " and rownum = 1)) is not null then (select codename"
									+ " from ldcode"
									+ " where codetype = 'paymode'"
									+ " and code = (select paymode"
									+ " from ljtempfeeclass"
									+ " where otherno = '"
									+ "?contno?"
									+ "'"
									+ " and rownum = 1))  else '未交费' end),"
									+ "(select name from laagent where agentcode ="
									+ " trim(a.agentcode)),"
									+ " a.managecom,"
									+ "(select branchcode"
									+ " from laagent"
									+ " where agentcode = trim(a.agentcode)),"
									+ " (select errinfo from lcsignlog"
									+ "  where serino = (select max(serino)"
									+ " from lcsignlog"
									+ " where contno = '"
									+ "?contno?"
									+ "'))"
									+ " from lccont a where contno = '"
									+ "?contno?"
									+ "'"
									+ " and exists(select 'x' from lcsignlog o where o.contno = a.contno)";
						}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
							strSQL = "select a.contno,a.polapplydate,(case when to_char(a.cvalidate,'YYYY-MM-DD') is not null then to_char(a.cvalidate,'YYYY-MM-DD')  else '无' end),a.insuredname,a.insuredbirthday,"
									+ "(select sum(xx.paymoney) from ljtempfee xx where xx.otherno = a.contno and enteraccdate is not null),"
									+ "(case when (select codename"
									+ " from ldcode"
									+ " where codetype = 'paymode'"
									+ " and code = (select paymode"
									+ " from ljtempfeeclass"
									+ " where otherno = '"
									+ "?contno?"
									+ "'"
									+ " limit 0,1)) is not null then (select codename"
									+ " from ldcode"
									+ " where codetype = 'paymode'"
									+ " and code = (select paymode"
									+ " from ljtempfeeclass"
									+ " where otherno = '"
									+ "?contno?"
									+ "'"
									+ " limit 0,1))  else '未交费' end),"
									+ "(select name from laagent where agentcode ="
									+ " trim(a.agentcode)),"
									+ " a.managecom,"
									+ "(select branchcode"
									+ " from laagent"
									+ " where agentcode = trim(a.agentcode)),"
									+ " (select errinfo from lcsignlog"
									+ "  where serino = (select max(serino)"
									+ " from lcsignlog"
									+ " where contno = '"
									+ "?contno?"
									+ "'))"
									+ " from lccont a where contno = '"
									+ "?contno?"
									+ "'"
									+ " and exists(select 'x' from lcsignlog o where o.contno = a.contno)";
						}
						// "' and salechnl = '" + strSalechnl + "'";
						if (strSalechnl != null
								&& (strSalechnl.equals("1")
										|| strSalechnl.equals("2") || strSalechnl
										.equals("3"))) {
							strSQL = strSQL + " and salechnl = '" + "?strSalechnl?"
									+ "'";
						}
						if (strSalechnl != null && strSalechnl.equals("5")) {
							strSQL = strSQL
									+ " and exists(select 'x' from lmriskapp m where m.riskprop = 'T' and m.riskcode = (select o.riskcode from lcpol o where o.contno = '"
									+ "?contno?"
									+ "' and o.polno = o.mainpolno))";
						}
						SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
						sqlbv8.sql(strSQL);
						sqlbv8.put("contno", ssrs.GetText(p + 1, 1));
						sqlbv8.put("strSalechnl", strSalechnl);
						SSRS Onessrs = new SSRS();
						Onessrs = exeSql.execSQL(sqlbv8);
						if (Onessrs.getMaxRow() > 0) {
							num++;
							scount++;
							allcount++;
						}

						if (Onessrs.getMaxRow() == 0) {
							continue;
						}
						// allcount = allcount + scount;
						String[] cols = new String[12];
						cols[0] = num + ""; // 序号
						cols[1] = ssrs.GetText(p + 1, 1); // 投保单号
						cols[2] = Onessrs.GetText(1, 2); // 投保日期
						cols[3] = Onessrs.GetText(1, 3); // 生效日期
						cols[4] = Onessrs.GetText(1, 4); // 被保险人
						cols[5] = Onessrs.GetText(1, 5); // 被保险人出生日期
						if (Onessrs.GetText(1, 6) == "null"
								|| Onessrs.GetText(1, 6) == "") {
							cols[6] = "" + 0;
						} else {
							cols[6] = Onessrs.GetText(1, 6); // 保费
						}
						cols[7] = Onessrs.GetText(1, 7); // 交费方式
						cols[8] = Onessrs.GetText(1, 8); // 业务员
						cols[9] = getName(Onessrs.GetText(1, 9)); // 营销服务部
						cols[10] = getUpComName(Onessrs.GetText(1, 10)); // 营业部
						cols[11] = Onessrs.GetText(1, 11); // 不成功原因
						alistTable.add(cols);
					}
					String[] colsss = new String[12];
					colsss[0] = "合计：";
					colsss[1] = "" + scount;
					alistTable.add(colsss);

				} catch (Exception ex) {
					this.buildError("getPrintData", "没有查询到相关数据！");
					return false;
				}
			}

			texttag = new TextTag();

			texttag.add("Count", "" + allcount);
			texttag.add("SystemDate", "" + PubFun.getCurrentDate());
			String[] col = new String[12];
			logger.debug("texttag.size=" + texttag.size());
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			xmlexport.addListTable(alistTable, col);
			logger.debug("清单打印成功！");
			// newXmlExport.outputDocumentToFile("d:\\","PersonBillSucc");//输出xml文档到文件
			mResult.addElement(xmlexport);

		} else {
			this.buildError("getPrintData", "不能按照总公司打印！");
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
			this.buildError("getUpComName", comcode + "机构不存在1！");
			return null;
		} else {
			return tLABranchGroupDB.getName();
		}
	}

	public String getName(String comcode) {
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(comcode);
		if (!tLDComDB.getInfo()) {
			this.buildError("getUpComName", comcode + "机构不存在！");
			return null;
		} else {
			return tLDComDB.getName();
		}
	}

	public static void main(String[] args) {

		VData tVData = new VData();
		tVData.addElement("864201");
		tVData.addElement("1");
		SignfailprintBL tCBgrqfqdBL = new SignfailprintBL();
		tCBgrqfqdBL.submitData(tVData, "PRINT");
	}
}
