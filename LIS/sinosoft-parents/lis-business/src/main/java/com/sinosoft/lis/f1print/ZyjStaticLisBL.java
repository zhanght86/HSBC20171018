package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

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
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author 刘岩松
 * function :print InputEfficiency or print CheckEfficiency Business Logic layer
 * @version 1.0
 * @date 2003-04-04
 */

public class ZyjStaticLisBL {
private static Logger logger = Logger.getLogger(ZyjStaticLisBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/*
	 * @define globe variable
	 */
	private String strMngCom;
	private String comName;
	private String strStartDate;
	private String strSubCom;
	private String strAgentgroup;

	// private String strAgentGroup;
	private int departmentNo;
	private String strDepartment;
	private String strSQL;
	private String strEndDate;
	private String strSaleChnl;
	private DecimalFormat mDecimalFormat = new DecimalFormat("0.00");

	public ZyjStaticLisBL() {
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
		logger.debug("###############################");
		logger.debug("##########Print Sucess!########");
		logger.debug("###############################");
		return true;
	}

	/***************************************************************************
	 * Name : getInputData() function :receive data from jsp and check date
	 * check :judge whether startdate begin enddate return :true or false
	 */
	private boolean getInputData(VData cInputData) {
		strMngCom = (String) cInputData.get(0);
		strStartDate = (String) cInputData.get(1);
		// strAgentGroup= (String) cInputData.get(2);
		strEndDate = (String) cInputData.get(2);
		strSaleChnl = (String) cInputData.get(3);
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
		cError.moduleName = "ZyjStaticLisBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/***************************************************************************
	 * name : getPrintData() function :get print data parameter : null return
	 * :true or false
	 */

	private boolean getPrintData() {

		ListTable ListTable = new ListTable();
		ListTable.setName("List");
		String tSql = "";
		String flag = "False";
		int tInputAmount = 0;
		int tIssueAmount = 0;
		int tUWAmount = 0;
		if (strMngCom.equals("86")) {
			tSql = "select comcode,name from ldcom where upcomcode='86'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSql);
			SSRS TempSSRS = new SSRS();
			ExeSQL ExeSQL = new ExeSQL();

			TempSSRS = ExeSQL.execSQL(sqlbv1);
			for (int i = 1; i <= TempSSRS.getMaxRow(); i++) {
				// tSql = "select count(*) ,(select count(b.otherno) from
				// loprtmanager b where b.otherno=a.contno and b.code ='14') ,"
				// +
				// "(select count(b.otherno) from loprtmanager b where
				// b.otherno=a.contno and b.code ='14' )/count(*)," +
				// "(select count(b.otherno) from loprtmanager b where
				// b.otherno=a.contno and b.code in
				// ('00','03','04','06','81','82','83','84','85','86','87','88','89')
				// )," +
				// "(select count(b.otherno) from loprtmanager b where
				// b.otherno=a.contno and b.code in
				// ('00','03','04','06','81','82','83','84','85','86','87','88','89')
				// )/count(*) from lccont a where a.makedate between '"
				// + strStartDate + "' and '" + strEndDate + "' and
				// a.managecom='" +
				// TempSSRS.GetText(i, 2) + "'"; ;
				logger.debug("1############################################");
				logger.debug("1###########处理分公司数据："
						+ TempSSRS.GetText(i, 2) + "###########");
				logger.debug("2############################################");
				tSql = "select count(*) from lccont a where a.makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and a.managecom like concat('"
						+ "?managecom?"
						+ "','%') and salechnl='"
						+ "?strSaleChnl?"
						+ "' and inputoperator<>'DefaultUsr'";
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(tSql);
				sqlbv2.put("strStartDate", strStartDate);
				sqlbv2.put("strEndDate", strEndDate);
				sqlbv2.put("managecom", TempSSRS.GetText(i, 1));
				sqlbv2.put("strSaleChnl", strSaleChnl);
				SSRS SSRS1 = new SSRS();
				ExeSQL ExeSQL1 = new ExeSQL();
				SSRS1 = ExeSQL1.execSQL(sqlbv2);
				logger.debug("SSRS1.GetText(1, 1)" + SSRS1.GetText(1, 1));
				String tSql2 = "select count(b.otherno) from loprtmanager b where b.otherno in (select a.contno from lccont a where a.makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and a.managecom like concat('"
						+ "?managecom?"
						+ "','%') and salechnl='"
						+ "?strSaleChnl?"
						+ "') and b.code ='14' ";
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				sqlbv3.sql(tSql2);
				sqlbv3.put("strStartDate", strStartDate);
				sqlbv3.put("strEndDate", strEndDate);
				sqlbv3.put("managecom", TempSSRS.GetText(i, 1));
				sqlbv3.put("strSaleChnl", strSaleChnl);
				SSRS SSRS2 = new SSRS();
				ExeSQL ExeSQL2 = new ExeSQL();
				SSRS2 = ExeSQL2.execSQL(sqlbv3);
				logger.debug("SSRS2.GetText(1, 1)" + SSRS2.GetText(1, 1));
				String tSql3 = "select count(b.otherno) from loprtmanager b where b.otherno in (select a.contno from lccont a where a.makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and a.managecom like concat('"
						+ "?managecom?"
						+ "','%') and salechnl='"
						+ "?strSaleChnl?"
						+ "') and b.code in ('00','03','04','06','81','82','83','84','85','86','87','88','89')";
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql(tSql3);
				sqlbv4.put("strStartDate", strStartDate);
				sqlbv4.put("strEndDate", strEndDate);
				sqlbv4.put("managecom", TempSSRS.GetText(i, 1));
				sqlbv4.put("strSaleChnl", strSaleChnl);
				SSRS SSRS3 = new SSRS();
				ExeSQL ExeSQL3 = new ExeSQL();
				SSRS3 = ExeSQL3.execSQL(sqlbv4);
				logger.debug("SSRS3.GetText(1, 1)" + SSRS3.GetText(1, 1));

				if (SSRS1.getMaxRow() > 0) {

					String[] col = new String[6];
					col[0] = TempSSRS.GetText(i, 2);
					col[1] = SSRS1.GetText(1, 1);
					col[2] = SSRS2.GetText(1, 1);
					col[4] = SSRS3.GetText(1, 1);

					tInputAmount = tInputAmount
							+ Integer.parseInt(SSRS1.GetText(1, 1));
					tIssueAmount = tIssueAmount
							+ Integer.parseInt(SSRS2.GetText(1, 1));
					tUWAmount = tUWAmount
							+ Integer.parseInt(SSRS3.GetText(1, 1));

					flag = "true";
					if (!SSRS2.GetText(1, 1).equals("0")) {
						col[3] = (Double.parseDouble((Double.parseDouble(SSRS2
								.GetText(1, 1))
								/ Double.parseDouble(SSRS1.GetText(1, 1)) + "")
								.substring(1, 6)) * 100 + "").substring(0, 3)
								+ "%";

					} else {
						col[3] = "0.00%";
					}
					logger.debug("col[3]" + col[3]);
					if (!SSRS3.GetText(1, 1).equals("0")) {
						col[5] = (Double.parseDouble((Double.parseDouble(SSRS3
								.GetText(1, 1))
								/ Double.parseDouble(SSRS1.GetText(1, 1)) + "")
								.substring(1, 6)) * 100 + "").substring(0, 3)
								+ "%";
					} else {
						col[5] = "0.00%";
					}
					logger.debug("col[5]" + col[5]);
					ListTable.add(col);

					logger.debug("0#col[0]=" + col[0]);
					logger.debug("1#col[1]=" + col[1]);
					logger.debug("2#col[2]=" + col[2]);
					logger.debug("3#col[3]=" + col[3]);
					logger.debug("4#col[4]=" + col[4]);
					logger.debug("5#col[5]=" + col[5]);

				}
			}
			logger.debug("1##############################################");
			logger.debug("##############增加总公司汇总数据##################");
			logger.debug("2##############################################");
			/**
			 * @todo 增加总公司汇总数据
			 */
			DecimalFormat df = new DecimalFormat("0.00");
			double tIssueRate = (double) tIssueAmount / (double) tInputAmount;
			double tUWRate = (double) tUWAmount / (double) tInputAmount;
			String[] col2 = new String[6];
			col2[0] = "合计：";
			col2[1] = "" + tInputAmount;
			col2[2] = "" + tIssueAmount;
			col2[3] = df.format(tIssueRate * 100) + "%";
			col2[4] = "" + tUWAmount;
			col2[5] = df.format(tUWRate * 100) + "%";
			ListTable.add(col2);

			if (flag == "False") {
				buildError("getPrintData", "无可打印数据！");
				return false;
			}

		} else {
			// tSql = "select count(*) ,(select count(b.otherno) from
			// loprtmanager b where b.otherno=a.contno and b.code ='14') ," +
			// "(select count(b.otherno) from loprtmanager b where
			// b.otherno=a.contno and b.code ='14' )/count(*)," +
			// "(select count(b.otherno) from loprtmanager b where
			// b.otherno=a.contno and b.code in
			// ('00','03','04','06','81','82','83','84','85','86','87','88','89')
			// ),"+
			// "(select count(b.otherno) from loprtmanager b where
			// b.otherno=a.contno and b.code in
			// ('00','03','04','06','81','82','83','84','85','86','87','88','89'))/count(*)
			// from lccont a where a.makedate between '"
			// +strStartDate+"' and '"+strEndDate +"' and
			// a.managecom='"+strMngCom+"'";
			tSql = "select name from ldcom where comcode='" + "?strMngCom?" + "' ";
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(tSql);
			sqlbv5.put("strMngCom", strMngCom);
			SSRS SSRS0 = new SSRS();
			ExeSQL ExeSQL0 = new ExeSQL();
			SSRS0 = ExeSQL0.execSQL(sqlbv5);
			tSql = "select count(*) from lccont a where a.makedate between '"
					+ "?strStartDate?" + "' and '" + "?strEndDate?"
					+ "' and a.managecom like concat('" + "?strMngCom?"
					+ "','%')  and salechnl='" + "?strSaleChnl?"
					+ "' and inputoperator<>'DefaultUsr'";
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(tSql);
			sqlbv6.put("strStartDate", strStartDate);
			sqlbv6.put("strEndDate", strEndDate);
			sqlbv6.put("strMngCom", strMngCom);
			sqlbv6.put("strSaleChnl", strSaleChnl);
			SSRS SSRS1 = new SSRS();
			ExeSQL ExeSQL1 = new ExeSQL();
			SSRS1 = ExeSQL1.execSQL(sqlbv6);
			logger.debug("SSRS1.GetText(1, 1)" + SSRS1.GetText(1, 1));
			String tSql2 = "select count(distinct b.otherno) from loprtmanager b where b.otherno in (select a.contno from lccont a where a.makedate between '"
					+ "?strStartDate?"
					+ "' and '"
					+ "?strEndDate?"
					+ "' and a.managecom like concat('" + "?strMngCom?"
					+ "','%') and salechnl='" + "?strSaleChnl?" + "') and b.code ='14'";
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql(tSql2);
			sqlbv7.put("strStartDate", strStartDate);
			sqlbv7.put("strEndDate", strEndDate);
			sqlbv7.put("strMngCom", strMngCom);
			sqlbv7.put("strSaleChnl", strSaleChnl);
			SSRS SSRS2 = new SSRS();
			ExeSQL ExeSQL2 = new ExeSQL();
			SSRS2 = ExeSQL2.execSQL(sqlbv7);
			logger.debug("SSRS2.GetText(1, 1)" + SSRS2.GetText(1, 1));
			String tSql3 = "select count(distinct b.otherno) from loprtmanager b where b.otherno in (select a.contno from lccont a where a.makedate between '"
					+ "?strStartDate?"
					+ "' and '"
					+ "?strEndDate?"
					+ "' and a.managecom like concat('"
					+ "?strMngCom?"
					+ "','%') and salechnl='"
					+ "?strSaleChnl?"
					+ "') and b.code in ('00','03','04','06','81','82','83','84','85','86','87','88','89')";
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql(tSql3);
			sqlbv8.put("strStartDate", strStartDate);
			sqlbv8.put("strEndDate", strEndDate);
			sqlbv8.put("strMngCom", strMngCom);
			sqlbv8.put("strSaleChnl", strSaleChnl);
			SSRS SSRS3 = new SSRS();
			ExeSQL ExeSQL3 = new ExeSQL();
			SSRS3 = ExeSQL3.execSQL(sqlbv8);
			logger.debug("SSRS3.GetText(1, 1)" + SSRS3.GetText(1, 1));
			if (SSRS1.getMaxRow() > 0) {
				String[] col = new String[6];
				col[0] = SSRS0.GetText(1, 1);
				col[1] = SSRS1.GetText(1, 1);
				col[2] = SSRS2.GetText(1, 1);
				col[4] = SSRS3.GetText(1, 1);
				if (!SSRS1.GetText(1, 1).equals("0")) {
					if (!SSRS2.GetText(1, 1).equals("0")) {
						col[3] = mDecimalFormat.format((Double
								.parseDouble(SSRS2.GetText(1, 1)) / Double
								.parseDouble(SSRS1.GetText(1, 1))) * 100)
								+ "%";

						logger.debug("col[3]" + col[3]);
					} else {
						col[3] = "0";
					}
					if (!SSRS3.GetText(1, 1).equals("0")) {
						col[5] = mDecimalFormat.format((Double
								.parseDouble(SSRS3.GetText(1, 1)) / Double
								.parseDouble(SSRS1.GetText(1, 1))) * 100)
								+ "%";
						// String a="0.0010";
						//
						// col[5]=(Double.parseDouble(a.substring(0,6))*100+"").substring(0,3);
						logger.debug("col[5]" + col[5]);
					} else {
						col[5] = "0";
					}
					ListTable.add(col);
				} else {
					buildError("getPrintData", "无可打印数据！");
					return false;
				}
			} else {
				buildError("getPrintData", "无可打印数据！");
				return false;
			}
		}

		TextTag texttag = new TextTag(); // Create a TextTag instance
		XmlExport xmlexport = new XmlExport(); // Create a XmlExport instance
		xmlexport.createDocument("ZyjStaticLis.vts", "printer"); // appoint
																	// to a
																	// special
																	// output
																	// .vts file

		texttag.add("Date1", strStartDate);
		texttag.add("Date2", strEndDate);
		if (strSaleChnl.equals("1")) {
			texttag.add("ListTitle", "个人暂压件清单");
		} else if (strSaleChnl.equals("3")) {
			texttag.add("ListTitle", "银行代理暂压件清单");
		} else if (strSaleChnl.equals("2")) {
			texttag.add("ListTitle", "团体暂压件清单");
		}
		// 将数据装入表单
		String[] col2 = new String[6];
		xmlexport.addListTable(ListTable, col2);

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}

		// xmlexport.outputDocumentToFile("D:\\", "ZyjStaticLis"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;

	}

	/*
	 * @function :in order to debug
	 */
	public static void main(String[] args) {
		String tStrMngCom = "86";
		String tIssueDate = "2005-11-01";
		// String tAgentGroup = "0003";
		String tPrintDate = "2005-11-30";

		VData tVData = new VData();
		tVData.addElement(tStrMngCom);
		tVData.addElement(tIssueDate);
		tVData.addElement(tPrintDate);
		tVData.addElement("1");

		ZyjStaticLisUI tZyjStaticLisUI = new ZyjStaticLisUI();
		tZyjStaticLisUI.submitData(tVData, "PRINT");
	}
}
