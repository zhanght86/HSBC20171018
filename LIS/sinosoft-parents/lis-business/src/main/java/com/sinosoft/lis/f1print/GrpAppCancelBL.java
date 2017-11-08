package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author 殷晓屹
 * function :print InputEfficiency or print CheckEfficiency Business Logic layer
 * @version 1.0
 * @date 2006-05-04
 */

import java.text.DecimalFormat;
import java.util.Vector;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class GrpAppCancelBL {
private static Logger logger = Logger.getLogger(GrpAppCancelBL.class);
	public GrpAppCancelBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	private String strMngCom;
	private String comName;
	private String strIssueDate;
	private String strIssueEndDate;
	private String strPrintDate;
	private String mListType;

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
		strMngCom = (String) cInputData.get(0);
		strIssueDate = (String) cInputData.get(1);
		strIssueEndDate = (String) cInputData.get(2);
		strPrintDate = (String) cInputData.get(3);
		mListType = (String) cInputData.get(4);
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
		cError.moduleName = "GrpAppCancelBL";
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
		String tSQL = "";
		ExeSQL exeSql = new ExeSQL();

		// 选择临时文件读取目录
		ListTable alistTable = new ListTable(); // Create a ListTable instance
		alistTable.setName("RISK"); // Appoint to a sepcial flag

		SSRS FenSSRS = new SSRS();
		SSRS ZhongSSRS = new SSRS();
		SSRS YingSSRS = new SSRS();
		// 查询分公司SQL
		String tFenSQL = "";
		// 查询中支SQL
		String tZhongSQL = "";
		// 查询营销服务部SQL
		String tYingSQL = "";
		/**
		 * @todo 如果录入的机构为总公司，则循环取总公司下的所有分公司。 如果没有选择总公司则将分公司的数量设为1
		 */
		if (strMngCom.equals("86")) {
			tFenSQL = " select comcode,name from ldcom where upcomcode = '"
					+ strMngCom + "'";
		} else {
			if (strMngCom.length() == 4) {
				tFenSQL = " select comcode,name from ldcom where comcode = '"
						+ strMngCom + "'";
			} else {
				tFenSQL = " select comcode,name from ldcom where comcode = '"
						+ strMngCom.substring(0, 4) + "'";
			}
		}
		FenSSRS = exeSql.execSQL(tFenSQL);
		int j_count = 0;
		/**
		 * @todo 循环分公司
		 */
		for (int i = 0; i < FenSSRS.getMaxRow(); i++) {
			// 取得分公司名称
			String tFenName = FenSSRS.GetText(i + 1, 2);
			if (strMngCom.length() <= 4) {
				tZhongSQL = " select comcode,name from ldcom where upcomcode = '"
						+ FenSSRS.GetText(i + 1, 1) + "'";
			} else {
				tZhongSQL = " select comcode,name from ldcom where comcode = '"
						+ strMngCom.substring(0, 6) + "'";
			}
			ZhongSSRS = exeSql.execSQL(tZhongSQL);
			// 循环中支
			for (int j = 0; j < ZhongSSRS.getMaxRow(); j++) {
				// 中支名称
				String tZhongName = ZhongSSRS.GetText(j + 1, 2);
				if (strMngCom.length() <= 6) {
					tYingSQL = " select comcode,name from ldcom where upcomcode = '"
							+ ZhongSSRS.GetText(j + 1, 1) + "'";
				} else {
					tYingSQL = " select comcode,name from ldcom where comcode = '"
							+ strMngCom.substring(0, 8) + "'";
				}
				YingSSRS = exeSql.execSQL(tYingSQL);
				// 循环营销部
				for (int k = 0; k < YingSSRS.getMaxRow(); k++) {
					String tYingName = YingSSRS.GetText(k + 1, 2);

					// ///////////////////////////////////////////////////////////
					// 根据界面的选择“全部”“撤保退费”、“溢缴退费”
					// ///////////////////////////////////////////////////////////
					if (this.mListType != null && mListType.equals("1")) {
						tSQL = "select otherno,'溢交退费' ff,APPntName,getmoney from LJAGetOther "
								+ "where makedate >='"
								+ strIssueDate
								+ "' and makedate<='"
								+ strIssueEndDate
								+ "' and othernotype='8' and managecom like '"
								+ YingSSRS.GetText(k + 1, 1)
								+ "%'  "
								+ " union all "
								+ " select a.otherno,'暂交费退费' gg,(select Name from ldperson where CustomerNo=a.AppntNo),a.sumgetmoney from ljaget a where a.makedate >='"
								+ strIssueDate
								+ "' and a.makedate<='"
								+ strIssueEndDate
								+ "' and a.managecom  like '"
								+ YingSSRS.GetText(k + 1, 1)
								+ "%' "
								+ " and a.othernotype = '4' order by ff ";

					}
					if (this.mListType != null && mListType.equals("2")) {
						tSQL = "select otherno,'溢交退费' ff,APPntName,getmoney from LJAGetOther "
								+ "where makedate >='"
								+ strIssueDate
								+ "' and makedate<='"
								+ strIssueEndDate
								+ "' and othernotype='8' and managecom like '"
								+ YingSSRS.GetText(k + 1, 1) + "%'  ";

					}
					if (this.mListType != null && mListType.equals("3")) {
						tSQL = " select a.otherno,'暂交费退费' gg,(select Name from ldperson where CustomerNo=a.AppntNo),a.sumgetmoney from ljaget a where a.makedate >='"
								+ strIssueDate
								+ "' and a.makedate<='"
								+ strIssueEndDate
								+ "' and a.managecom  like '"
								+ YingSSRS.GetText(k + 1, 1)
								+ "%' "
								+ " and a.othernotype = '4'  ";

					}
					SSRS ssrs = exeSql.execSQL(tSQL);
					if (ssrs.getMaxRow() <= 0) {
						logger.debug("管理机构'" + YingSSRS.GetText(k + 1, 1)
								+ "'在'" + strIssueDate + "'没有数据");
						continue;
					}
					// //////////////////////////////////////////////////////////////////////////

					String[] colx = new String[8];
					colx[0] = "  退费日期：";
					colx[1] = strIssueDate + "至" + strIssueEndDate;
					colx[2] = " 打印日期：" + strPrintDate;
					colx[3] = "    ";
					// colx[4] = " ";
					colx[4] = "单位：人民币/外币";
					alistTable.add(colx);

					String[] col1 = new String[8];
					col1[0] = "分公司：";
					col1[1] = tFenName;
					col1[2] = "中心支公司：";
					col1[3] = tZhongName;
					col1[4] = "营业区：" + tYingName;

					alistTable.add(col1);

					String[] col2 = new String[8];
					col2[0] = "   ";
					col2[1] = "   ";
					col2[2] = "   ";
					col2[3] = "   ";
					col2[4] = "   ";

					alistTable.add(col2);

					String[] col3 = new String[8];
					col3[0] = "序号";
					col3[1] = "投保单号";
					col3[2] = "退费类型";
					col3[3] = "投保人";

					col3[4] = "金额";
					alistTable.add(col3);

					int i_count = 0;
					double Sum = 0;
					i_count = ssrs.getMaxRow();
					for (int l = 1; l <= i_count; l++) {
						String[] cols = new String[8];
						try {
							cols[0] = l + "";
							cols[1] = ssrs.GetText(l, 1);
							cols[2] = ssrs.GetText(l, 2);
							cols[3] = ssrs.GetText(l, 3);
							cols[4] = ssrs.GetText(l, 4);
							// cols[5] = ssrs.GetText(l, 5);
							Sum = Sum + Double.parseDouble(ssrs.GetText(l, 4));
						} catch (Exception ex) {
							buildError("getprintData", "抽取数据失败");
							logger.debug("抽取数据失败");
							return false;
						}
						logger.debug("抽取信息成功！！");
						alistTable.add(cols);
					}
					String[] col4 = new String[8];
					col4[0] = "   ";
					col4[1] = "   ";
					col4[2] = "   ";
					col4[3] = "   ";
					col4[4] = "   ";
					// col4[5] = " ";
					alistTable.add(col4);

					j_count = j_count + i_count;
					String[] col5 = new String[8];
					col5[0] = " 小计:金额：";
					col5[1] = new DecimalFormat("0.00").format(Sum);
					col5[2] = "   ";
					col5[3] = " 件数：" + i_count;
					col5[4] = "   ";
					// col5[5] = " ";
					alistTable.add(col5);

					String[] col6 = new String[8];
					col6[0] = "复核： ";
					col6[1] = "   ";
					col6[2] = "   ";
					col6[3] = "经办： ";
					col6[4] = "   ";
					// col6[5] = " ";
					alistTable.add(col6);

					String[] col7 = new String[8];
					col7[0] = "   ";
					col7[1] = "   ";
					col7[2] = "   ";
					col7[3] = "   ";
					col7[4] = "   ";
					// col7[5] = " ";
					alistTable.add(col7);

					String[] col8 = new String[8];
					col8[0] = "   ";
					col8[1] = "   ";
					col8[2] = "   ";
					col8[3] = "   ";
					col8[4] = "   ";
					// col8[5] = " ";
					alistTable.add(col8);

					String[] col9 = new String[8];
					col9[0] = "   ";
					col9[1] = "   ";
					col9[2] = "   ";
					col9[3] = "   ";
					col9[4] = "   ";
					// col9[5] = " ";
					alistTable.add(col9);
				}
			}
		}
		if (j_count == 0) {
			buildError("tSSRSError", "对不起，没有查询结果。所选管理机构在对应日期没有数据！");
			return false;
		}
		String[] col = new String[1];
		TextTag texttag = new TextTag(); // Create a TextTag instance
		XmlExport xmlexport = new XmlExport(); // Create a XmlExport instance
		if (this.mListType == null || this.mListType == "") {
			buildError("tSSRSError", "对不起，没有查询结果。所选管理机构在对应日期没有数据！");
			return false;
		}
		xmlexport.createDocument("GrpAppCancel.vts", "printer"); // appoint
																	// to a
																	// special
																	// output
																	// .vts file
		xmlexport.addListTable(alistTable, col);
		texttag.add("IssueDate", strIssueDate);
		texttag.add("MngCom", comName);
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}

		xmlexport.outputDocumentToFile("D:\\", "GrpAppCancelHZM"); // 输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

	//
	private Vector getSearchResult(String str, Vector v) {
		String upAgentgroup = str;
		Vector vd = new Vector();
		vd = v;
		String result1 = "";
		int search_count = 0;
		ExeSQL searchOneSQL = new ExeSQL();
		SSRS searchOneSsrs = new SSRS();
		searchOneSsrs = searchOneSQL
				.execSQL("Select agentgroup From labranchgroup Where upbranch='"
						+ upAgentgroup + "'");
		search_count = searchOneSsrs.getMaxRow();

		for (int search = 1; search < search_count + 1; search++) {
			searchOneSsrs = searchOneSQL
					.execSQL("Select agentgroup From labranchgroup Where upbranch='"
							+ upAgentgroup + "'");
			int deb = searchOneSsrs.getMaxRow();
			if (deb == 0) {
				break;
			}

			result1 = searchOneSsrs.GetText(search, 1);
			vd.add(result1);
			this.getSearchResult(result1, vd);
		}
		return vd;
	}

	private String getSearchR(String str) {
		ExeSQL searchOneSQL = new ExeSQL();
		SSRS searchOneSsrs = new SSRS();
		searchOneSsrs = searchOneSQL
				.execSQL("Select branchattr From labranchgroup Where agentgroup='"
						+ str + "' and managecom='" + strMngCom + "'");
		String result = searchOneSsrs.GetText(1, 1);
		return result;
	}

	private String getAgentGroup(Vector v) {

		Vector agent = new Vector();
		agent = v;
		String goOne = " or";
		String goTwo = " c.agentGroup='";
		String goThree = "' ";
		String output = "";
		String value = "";
		if (agent.isEmpty()) {
			return "";
		}
		for (int i = 0; i < agent.size(); i++) {
			value = (String) agent.get(i);
			if (agent.size() == 1) {
				output = goOne + goTwo + value + goThree;
				return output;
			}
			if (i == 0) {
				output = goOne + goTwo + value;
			} else if (i > 0 && i < agent.size() - 1) {
				output = output + goThree + goOne + goTwo + value;
			} else if (i == agent.size() - 2) {
				output = output + goThree + goOne + goTwo + value;
			} else {
				output = output + goThree + goOne + goTwo + value + goThree;
			}

		}
		// logger.debug("jieguoxianshi"+output);
		return output;
	}

	/*
	 * @function :in order to debug
	 */
	public static void main(String[] args) {
		String tStrMngCom = "86320000";
		String tIssueDate = "2006-03-26";
		String tIssueEndDate = "2006-04-11";
		String tAgentGroup = "0001";
		String tPrintDate = "2006-04-04";
		String mListType = "1";

		VData tVData = new VData();
		tVData.addElement(tStrMngCom);
		tVData.addElement(tIssueDate);
		tVData.addElement(tIssueEndDate);
		tVData.addElement(tAgentGroup);
		tVData.addElement(tPrintDate);
		tVData.addElement(mListType);

		GrpAppCancelUI tGrpAppCancelUI = new GrpAppCancelUI();
		tGrpAppCancelUI.submitData(tVData, "PRINT");
	}

	private void jbInit() throws Exception {
	}
}
