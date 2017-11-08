package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author fuyu
 * function :print InputEfficiency or print CheckEfficiency Business Logic layer
 * @version 1.0
 * @date 2003-04-04
 */

public class ChnlListPrintBL {
private static Logger logger = Logger.getLogger(ChnlListPrintBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	private String strMngCom;
	private String strStartDate;
	private String strEndDate;
	private String tUWStatType;
	private String tChnlType;

	public ChnlListPrintBL() {
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
		strStartDate = (String) cInputData.get(1);
		strEndDate = (String) cInputData.get(2);
		tUWStatType = (String) cInputData.get(3);
		tChnlType = (String) cInputData.get(4);
		logger.debug("strStartDate:" + strStartDate);
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
		cError.moduleName = "UWStaticPrintBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/***************************************************************************
	 * name : getPrintData() function :get print data parameter : null return
	 * :true or false
	 */

	private boolean getPrintData() {
		ListTable tlistTable = new ListTable();
		tlistTable.setName("List");
		if (tChnlType.equals("1")) {
			if (tUWStatType.equals("1")) {

				XmlExport xmlexport = new XmlExport(); // Create a XmlExport
														// instance
				xmlexport.createDocument("chnlforelist.vts", "printer");
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				String strSQL = "select a.contno,(select c.comcode as aa from lduser c where (c.usercode) = (a.operator)),"
						+ "(select z.name from ldcom z where (z.comcode)=(select (c.comcode) as aa from lduser c where (c.usercode) = (a.operator))) ,"
						+ "a.managecom,a.riskcode, concat(a.payendyear,a.payendyearflag),a.prem,a.amnt,a.agentcode,(select d.name from laagent d where (d.agentcode) = trim(a.agentcode))"
						+ " from lcpol a where a.makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and a.managecom like concat('"+ "?strMngCom?"+ "','%') and a.grpcontno = '00000000000000000000' and a.salechnl='1' and char_length(trim(a.contno))<=14";
				sqlbv1.sql(strSQL);
				sqlbv1.put("strStartDate", strStartDate);
				sqlbv1.put("strEndDate", strEndDate);
				sqlbv1.put("strMngCom", strMngCom);
				ExeSQL exeSQL2 = new ExeSQL();
				SSRS ssrs2 = exeSQL2.execSQL(sqlbv1);

				int i_count2 = ssrs2.getMaxRow();
				logger.debug("i_count2是" + i_count2);
				if (i_count2 == 0) {
					CError tError = new CError();
					tError.moduleName = "PNoticeBillBL";
					tError.functionName = "getDutyGetClmInfo";
					tError.errorMessage = "在该时间段内没有打印信息，请确认起止日期是否正确！！！";
					this.mErrors.addOneError(tError);
					return false;
				} else {
					for (int i = 1; i <= i_count2; i++) {
						String[] cols1 = new String[10];
						cols1[0] = ssrs2.GetText(i, 1);
						cols1[1] = ssrs2.GetText(i, 2);
						cols1[2] = ssrs2.GetText(i, 3);
						cols1[3] = ssrs2.GetText(i, 4);
						cols1[4] = ssrs2.GetText(i, 5);
						cols1[5] = ssrs2.GetText(i, 6);
						cols1[6] = ssrs2.GetText(i, 7);
						cols1[7] = ssrs2.GetText(i, 8);
						cols1[8] = ssrs2.GetText(i, 9);
						cols1[9] = ssrs2.GetText(i, 10);

						logger.debug("cols1[0]" + cols1[0]);
						logger.debug("cols1[1]" + cols1[1]);
						logger.debug("cols1[2]" + cols1[2]);
						tlistTable.add(cols1);
					}
				}
				String[] col = new String[10];
				xmlexport.addListTable(tlistTable, col);

				// xmlexport.outputDocumentToFile("e:\\", "chnlforelist");
				// //输出xml文档到文件
				mResult.clear();
				mResult.addElement(xmlexport);
				return true;

			}

			if (tUWStatType.equals("2")) {
				XmlExport xmlexport = new XmlExport(); // Create a XmlExport
														// instance
				xmlexport.createDocument("chnldailylist.vts", "printer");
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				String strSQL = "select distinct (select c.comcode as aa from lduser c where (c.usercode) = (a.operator)),"
						+ "(select z.name from ldcom z where (z.comcode)=(select (c.comcode) as aa from lduser c where (c.usercode) = (a.operator))) ,"
						+ "a.managecom,(select c.name from ldcom c where (c.comcode)=(a.managecom)),a.riskcode, concat(a.payendyear,a.payendyearflag),(select (d.contno) from lcpol d where d.riskcode =a.riskcode and d.contno =a.contno) ,(select sum(e.prem) from lcpol e where e.riskcode=a.riskcode and e.makedate between '2005-08-01' and '2005-08-02' and e.managecom like '86%' and e.grpcontno = '00000000000000000000')"
						+ " from lcpol a,lmriskapp b where a.makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and a.managecom like concat('"+ "?strMngCom?"	+ "','%') and a.grpcontno = '00000000000000000000'"
						+ " and a.riskcode=b.riskcode and b.subriskflag='M' and a.salechnl='1' and char_length(trim(a.contno))<=14";
				sqlbv2.sql(strSQL);
				sqlbv2.put("strStartDate", strStartDate);
				sqlbv2.put("strEndDate", strEndDate);
				sqlbv2.put("strMngCom", strMngCom);
				ExeSQL exeSQL2 = new ExeSQL();
				SSRS ssrs2 = exeSQL2.execSQL(sqlbv2);

				int i_count2 = ssrs2.getMaxRow();
				logger.debug("i_count2是" + i_count2);
				if (i_count2 == 0) {
					CError tError = new CError();
					tError.moduleName = "PNoticeBillBL";
					tError.functionName = "getDutyGetClmInfo";
					tError.errorMessage = "在该时间段内没有打印信息，请确认起止日期是否正确！！！";
					this.mErrors.addOneError(tError);
					return false;
				} else {
					for (int i = 1; i <= i_count2; i++) {
						String[] cols1 = new String[8];
						cols1[0] = ssrs2.GetText(i, 1);
						cols1[1] = ssrs2.GetText(i, 2);
						cols1[2] = ssrs2.GetText(i, 3);
						cols1[3] = ssrs2.GetText(i, 4);
						cols1[4] = ssrs2.GetText(i, 5);
						cols1[5] = ssrs2.GetText(i, 6);
						cols1[6] = ssrs2.GetText(i, 7);
						if (ssrs2.GetText(i, 8).equals("null")) {
							cols1[7] = "0";
							logger.debug("cols1[7]=" + cols1[7]);
						} else {
							cols1[7] = ssrs2.GetText(i, 8);
							logger.debug("cols1[7]：" + cols1[7]);
						}

						logger.debug("cols1[0]" + cols1[0]);
						logger.debug("cols1[1]" + cols1[1]);
						logger.debug("cols1[2]" + cols1[2]);
						tlistTable.add(cols1);
					}
				}
				String[] col = new String[8];
				xmlexport.addListTable(tlistTable, col);

				// xmlexport.outputDocumentToFile("e:\\", "chnldailylist");
				// //输出xml文档到文件
				mResult.clear();
				mResult.addElement(xmlexport);
				return true;
			}
			return true;
		}
		if (tChnlType.equals("2")) {
			if (tUWStatType.equals("1")) {

				XmlExport xmlexport = new XmlExport(); // Create a XmlExport
														// instance
				xmlexport.createDocument("bankforelist.vts", "printer");
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				String strSQL = "select a.contno,(select c.comcode as aa from lduser c where (c.usercode) = (a.operator)),"
						+ "(select z.name from ldcom z where (z.comcode)=(select (c.comcode) as aa from lduser c where (c.usercode) = (a.operator))) ,"
						+ "(select c.bankcode from lacom c where c.agentcom=a.agentcom),a.riskcode, concat(a.payendyear,a.payendyearflag),a.prem,a.amnt,a.agentcode,"
						+ "(select d.managecom from laagent d where (d.agentcode) = trim(a.agentcode)),a.agentcode1,(select d.managecom from laagent d where (d.agentcode) = trim(a.agentcode1)) "
						+ " from lcpol a where a.makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and a.managecom like concat('"+ "?strMngCom?"+ "','%') and a.grpcontno = '00000000000000000000' and salechnl='3'";
				sqlbv3.sql(strSQL);
				sqlbv3.put("strStartDate", strStartDate);
				sqlbv3.put("strEndDate", strEndDate);
				sqlbv3.put("strMngCom", strMngCom);
				ExeSQL exeSQL2 = new ExeSQL();
				SSRS ssrs2 = exeSQL2.execSQL(sqlbv3);

				int i_count2 = ssrs2.getMaxRow();
				logger.debug("i_count2是" + i_count2);
				if (i_count2 == 0) {
					CError tError = new CError();
					tError.moduleName = "PNoticeBillBL";
					tError.functionName = "getDutyGetClmInfo";
					tError.errorMessage = "在该时间段内没有打印信息，请确认起止日期是否正确！！！";
					this.mErrors.addOneError(tError);
					return false;
				} else {
					for (int i = 1; i <= i_count2; i++) {
						String[] cols1 = new String[12];
						cols1[0] = ssrs2.GetText(i, 1);
						cols1[1] = ssrs2.GetText(i, 2);
						cols1[2] = ssrs2.GetText(i, 3);
						cols1[3] = ssrs2.GetText(i, 4);
						cols1[4] = ssrs2.GetText(i, 5);
						cols1[5] = ssrs2.GetText(i, 6);
						cols1[6] = ssrs2.GetText(i, 7);
						cols1[7] = ssrs2.GetText(i, 8);
						cols1[8] = ssrs2.GetText(i, 9);
						cols1[9] = ssrs2.GetText(i, 10);
						cols1[10] = ssrs2.GetText(i, 11);
						cols1[11] = ssrs2.GetText(i, 12);

						logger.debug("cols1[0]" + cols1[0]);
						logger.debug("cols1[1]" + cols1[1]);
						logger.debug("cols1[2]" + cols1[2]);
						tlistTable.add(cols1);
					}
				}
				String[] col = new String[12];
				xmlexport.addListTable(tlistTable, col);

				// xmlexport.outputDocumentToFile("e:\\", "bankforelist");
				// //输出xml文档到文件
				mResult.clear();
				mResult.addElement(xmlexport);
				return true;

			}

			if (tUWStatType.equals("2")) {
				XmlExport xmlexport = new XmlExport(); // Create a XmlExport
														// instance
				xmlexport.createDocument("bankdailylist.vts", "printer");
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				String strSQL = "select distinct (select c.comcode as aa from lduser c where (c.usercode) = (a.operator)),"
						+ "(select z.name from ldcom z where (z.comcode)=(select (c.comcode) as aa from lduser c where (c.usercode) = (a.operator))) ,"
						+ "a.managecom,(select c.name from ldcom c where (c.comcode)=(a.managecom)),(select c.bankcode from lacom c where c.agentcom=a.agentcom),"
						+ "(select c.name from lacom c where c.agentcom=a.agentcom),a.riskcode, concat(a.payendyear,a.payendyearflag),(select count(d.contno) from lcpol d where d.riskcode =a.riskcode and d.contno =a.contno) ,"
						+ "(select sum(e.prem) from lcpol e where e.riskcode=a.riskcode and e.makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and e.managecom like '86%' and e.salechnl = '3')"
						+ " from lcpol a,lmriskapp b where a.makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and a.managecom like concat('"+ "?strMngCom?"	+ "','%') and a.salechnl='3'"
						+ " and a.riskcode=b.riskcode and b.subriskflag='M' ";
				sqlbv4.sql(strSQL);
				sqlbv4.put("strStartDate", strStartDate);
				sqlbv4.put("strEndDate", strEndDate);
				sqlbv4.put("strMngCom", strMngCom);
				ExeSQL exeSQL2 = new ExeSQL();
				SSRS ssrs2 = exeSQL2.execSQL(sqlbv4);

				int i_count2 = ssrs2.getMaxRow();
				logger.debug("i_count2是" + i_count2);
				if (i_count2 == 0) {
					CError tError = new CError();
					tError.moduleName = "PNoticeBillBL";
					tError.functionName = "getDutyGetClmInfo";
					tError.errorMessage = "在该时间段内没有打印信息，请确认起止日期是否正确！！！";
					this.mErrors.addOneError(tError);
					return false;
				} else {
					for (int i = 1; i <= i_count2; i++) {
						String[] cols1 = new String[10];
						cols1[0] = ssrs2.GetText(i, 1);
						cols1[1] = ssrs2.GetText(i, 2);
						cols1[2] = ssrs2.GetText(i, 3);
						cols1[3] = ssrs2.GetText(i, 4);
						cols1[4] = ssrs2.GetText(i, 5);
						cols1[5] = ssrs2.GetText(i, 6);
						cols1[6] = ssrs2.GetText(i, 7);
						cols1[7] = ssrs2.GetText(i, 8);
						cols1[8] = ssrs2.GetText(i, 9);
						cols1[9] = ssrs2.GetText(i, 10);

						logger.debug("cols1[0]" + cols1[0]);
						logger.debug("cols1[1]" + cols1[1]);
						logger.debug("cols1[2]" + cols1[2]);
						tlistTable.add(cols1);
					}
				}
				String[] col = new String[10];
				xmlexport.addListTable(tlistTable, col);

				// xmlexport.outputDocumentToFile("e:\\", "bankdailylist");
				// //输出xml文档到文件
				mResult.clear();
				mResult.addElement(xmlexport);
				return true;
			}
			return true;
		}

		return true;

	}

}
