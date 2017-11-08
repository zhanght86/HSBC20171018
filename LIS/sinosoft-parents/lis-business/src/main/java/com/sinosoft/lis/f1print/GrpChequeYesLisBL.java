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
 * @date 2006-04-16
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

public class GrpChequeYesLisBL {
private static Logger logger = Logger.getLogger(GrpChequeYesLisBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	private String strMngCom;
	private String comName;
	private String strPayDate;
	private String strSQL;
	private String strPrintDate;

	public GrpChequeYesLisBL() {
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
		strMngCom = (String) cInputData.get(0);
		strPayDate = (String) cInputData.get(1);
		strPrintDate = (String) cInputData.get(3);
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
		cError.moduleName = "GrpChequeYesLisBL";
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
		ListTable alistTable = new ListTable(); // Create a ListTable instance
		alistTable.setName("RISK"); // Appoint to a sepcial flag
		ExeSQL exeSql = new ExeSQL();

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
					// /////////////////////////////////////////////////
					strSQL = "select a.MakeDate,(select codename"
							+ " from ldcode"
							+ " where codetype = 'bank'"
							+ " and code = a.bankcode),"
							+ " a.chequeno,"
							+ " a.otherno,"
							+ " nvl(a.AccName,''),"
							+ " b.agentcode,"
							+ " (select name from laagent where agentcode = trim(b.agentcode)),"
							+ " a.paymoney"
							+ " from ljtempfeeclass a, ljtempfee b"
							+ " where a.paymode = '3' "
							+ " and a.enteraccdate <> '3000-01-01'"
							+ " and a.policycom like '"
							+ YingSSRS.GetText(k + 1, 1) + "%'"
							+ " and a.tempfeeno = b.tempfeeno"
							+ " and a.PayDate = '" + strPayDate
							+ "' and b.TempFeeType='1' and b.othernotype='4'";// modify
																				// by
																				// yinxiaoyi
																				// 20060714
																				// 根据QCbug6662
					// logger.debug("strSQL==" + strSQL);
					SSRS ssrs = exeSql.execSQL(strSQL);
					if (ssrs.getMaxRow() <= 0) {
						logger.debug("管理机构'" + YingSSRS.GetText(k + 1, 1)
								+ "'在'" + strPayDate + "'没有数据");
						continue;
					}
					// //////////////////////////////////////////////////
					String[] col7 = new String[9];
					col7[0] = "   ";
					col7[1] = "   ";
					col7[2] = "   ";
					col7[3] = "   ";
					col7[4] = "   ";
					col7[5] = "   ";
					col7[6] = "   ";
					col7[7] = "   ";
					col7[8] = "   ";
					alistTable.add(col7);

					String[] colx = new String[9];
					colx[0] = "交费日期：";
					colx[1] = strPayDate;
					colx[2] = "   ";
					colx[3] = "   ";
					colx[4] = "打印日期：";
					colx[5] = strPrintDate;
					colx[6] = "   ";
					colx[7] = "   ";
					colx[8] = "   ";
					alistTable.add(colx);

					String[] col1 = new String[9];
					col1[0] = "分公司：";
					col1[1] = tFenName;
					col1[2] = "中心支公司：";
					col1[3] = tZhongName;
					col1[4] = "营业区：";
					col1[5] = tYingName;
					col1[6] = "   ";
					col1[7] = "   ";
					col1[8] = "   ";
					alistTable.add(col1);

					String[] col2 = new String[9];
					col2[0] = "   ";
					col2[1] = "   ";
					col2[2] = "   ";
					col2[3] = "   ";
					col2[4] = "   ";
					col2[5] = "   ";
					col2[6] = "   ";
					col2[7] = "   ";
					col2[8] = "   ";
					alistTable.add(col2);

					String[] col3 = new String[9];
					col3[0] = "序号";
					col3[1] = "录入日期";
					col3[2] = "银行";
					col3[3] = "支票号";
					col3[4] = "投保单号";
					col3[5] = "投保人";
					col3[6] = "业务员号";
					col3[7] = "业务员";
					col3[8] = "金额";
					alistTable.add(col3);

					double Sum = 0;
					int i_count = ssrs.getMaxRow();
					// 将数据装入表单
					for (int l = 1; l <= i_count; l++) {
						String[] cols = new String[9];
						try {
							cols[0] = l + "";
							cols[1] = ssrs.GetText(l, 1);
							cols[2] = ssrs.GetText(l, 2);
							cols[3] = ssrs.GetText(l, 3);
							cols[4] = ssrs.GetText(l, 4);
							cols[5] = ssrs.GetText(l, 5);
							cols[6] = ssrs.GetText(l, 6);
							cols[7] = ssrs.GetText(l, 7);
							cols[8] = ssrs.GetText(l, 8);
							Sum = Sum + Double.parseDouble(ssrs.GetText(l, 8));
						} catch (Exception ex) {
							buildError("getprintData", "抽取数据失败");
							logger.debug("抽取数据失败");
							return false;
						}

						logger.debug("抽取信息成功！！");
						alistTable.add(cols);
					}
					j_count = j_count + i_count;
					String[] col4 = new String[9];
					col4[0] = " 小计:金额：";
					col4[1] = new DecimalFormat("0.00").format(Sum);
					col4[2] = "   ";
					col4[3] = " 件数：" + i_count;
					col4[4] = "   ";
					col4[5] = "   ";
					col4[6] = "   ";
					col4[7] = "   ";
					col4[8] = "   ";
					alistTable.add(col4);

					String[] col5 = new String[9];
					col5[0] = "复核： ";
					col5[1] = "   ";
					col5[2] = "经办： ";
					col5[3] = "   ";
					col5[4] = "   ";
					col5[5] = "   ";
					col5[6] = "   ";
					col5[7] = "   ";
					col5[8] = "   ";
					alistTable.add(col5);

					String[] col6 = new String[9];
					col6[0] = "   ";
					col6[1] = "   ";
					col6[2] = "   ";
					col6[3] = "   ";
					col6[4] = "   ";
					col6[5] = "   ";
					col6[6] = "   ";
					col6[7] = "   ";
					col6[8] = "   ";
					alistTable.add(col6);

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
		xmlexport.createDocument("GrpChequeYesLis.vts", "printer"); // appoint
																	// to a
																	// special
																	// output
																	// .vts file

		xmlexport.addListTable(alistTable, col);
		texttag.add("PayDate", strPayDate);
		texttag.add("MngCom", comName);
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}

		xmlexport.outputDocumentToFile("D:\\", "testHZM"); // 输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;

	}

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

	private Vector getSearchResult(String str1, String str2) {
		String result1 = "";
		int search_count = 0;
		Vector output = new Vector();
		ExeSQL searchOneSQL = new ExeSQL();
		SSRS searchOneSsrs = new SSRS();
		searchOneSsrs = searchOneSQL
				.execSQL("Select c.agentgroup From labranchgroup b,labranchgroup c Where c.branchattr Like trim(b.branchattr) || '%' And c.managecom='"
						+ str2
						+ "'And b.branchlevel = '03' And b.agentgroup in(Select agentgroup From labranchgroup Where trim(branchattr)='"
						+ str1
						+ "'And managecom='"
						+ str2
						+ "') Order By agentgroup");
		search_count = searchOneSsrs.getMaxRow();
		for (int i = 1; i < search_count + 1; i++) {
			result1 = searchOneSsrs.GetText(i, 1);
			output.add(result1);
		}
		return output;
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
				output = goTwo + value + goThree;
				return output;
			}
			if (i == 0) {
				output = goTwo + value;
			} else if (i > 0 && i < agent.size() - 1) {
				output = output + goThree + goOne + goTwo + value;
			} else {
				output = output + goThree + goOne + goTwo + value + goThree;
			}

		}

		return output;
	}

	/*
	 * @function :in order to debug
	 */
	public static void main(String[] args) {
		String tStrMngCom = "86110000";
		String tPayDate = "2005-8-12";
		String tAgentGroup = "0001";
		String tPrintDate = "2005-08-12";

		VData tVData = new VData();
		tVData.addElement(tStrMngCom);
		tVData.addElement(tPayDate);
		tVData.addElement(tAgentGroup);
		tVData.addElement(tPrintDate);

		PayChequeNoLisUI tPayChequeNoLisUI = new PayChequeNoLisUI();
		tPayChequeNoLisUI.submitData(tVData, "PRINT");
	}
}
