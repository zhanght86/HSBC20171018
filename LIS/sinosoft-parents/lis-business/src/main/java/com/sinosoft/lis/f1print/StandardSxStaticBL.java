package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

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
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author 刘岩松
 * function :print InputEfficiency or print CheckEfficiency Business Logic layer
 * @version 1.0
 * @date 2003-04-04
 */

public class StandardSxStaticBL {
private static Logger logger = Logger.getLogger(StandardSxStaticBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	private String strMngCom;
	// private String strAgentCode;
	private String strStartDate;
	private String strEndDate;
	// private String strFlag;
	private String strOperation;
	private String strSaleChnl;
	private String FORMATMODOL = "0.0000"; // 精确位数
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL);

	public StandardSxStaticBL() {
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
		strSaleChnl = (String) cInputData.get(3);
		// strOperation = (String)cInputData.get(4);
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
		cError.moduleName = "StandardSxStaticBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/***************************************************************************
	 * name : getPrintData() function :get print data parameter : null return
	 * :true or false
	 */

	private boolean getPrintData() {

		//time1-time2  分支实现，多处公用提出来
		//zhangyingfeng 
		String tm="";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			tm="round(sum((a.backdate-a.makedate)+(to_number(to_date(a.backtime,'HH24:MI:SS')-to_date(a.maketime,'HH24:MI:SS')))),5)";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			tm="ROUND(SUM(DATEDIFF(a.backdate,a.makedate)+(to_number(TIME_TO_SEC(to_date(a.backtime,'HH24:MI:SS'))-TIME_TO_SEC(to_date(a.maketime,'HH24:MI:SS')))/(24*60*60))),5)";
		}
		//
		// 个人业务
		if (strSaleChnl.equals("1")) {
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("StandardSxStatic.vts", "printer"); // appoint
																			// to a
																			// special
																			// output
																			// .vts
																			// file
			String[] cols = new String[3];
			ListTable tlistTable = new ListTable();
			tlistTable.setName("List");
			String[] col = new String[3];
			String[] tcol = null;
			String strSQL;

			// choose all Operator from LCPol between start date and end date
			// strSQL="select count(*),Sum() from ldconttime a,lccont b where
			// a.makedate between '"+strStartDate+"' and '"+strEndDate+"' and
			// managecom='"+strMngCom+"' and subtype='UA001'" ;
			
			strSQL = " select count(*),sum(tm)/count(*) from (select "+tm+" as tm from ldconttime a "
					+ " where a.businesstype in ('1001','1002','1003','1004') " // 个单录单，复核完成
					+ " and a.makedate between '?strStartDate?' and '"
					+ "?strEndDate?" // 在统计时间区间
					+ "' and exists (select 'X' from lccont qd where qd.salechnl = '1' and qd.proposalcontno=a.contno and qd.managecom like concat('?strMngCom?','%'))" // 符合保单类型
					+ " and  not exists  (select 'X' from LCIssuePol wx where wx.proposalcontno=a.contno and BackObjType='2') "
					+ // 无外部问题件
//					"and exists (select * from lbmission where missionprop1 = trim(a.contno) and activityid = '0000001003') "
					"and exists (select * from lbmission where missionprop1 = trim(a.contno) and activityid = '0000001003') "
					+ // 自核通过
//					"and not exists (select * from lwmission where missionprop1 = trim(a.contno) and activityid = '0000001100') "
					"and not exists (select * from lwmission where missionprop1 = trim(a.contno) and activityid  in (select activityid from lwactivity  where functionid ='10010028')) "
					+ //
//					"and not exists (select * from lbmission where missionprop1 = trim(a.contno) and activityid = '0000001110') "
					"and not exists (select * from lbmission where missionprop1 = trim(a.contno) and activityid  in (select activityid from lwactivity  where functionid ='10010028')) "
					+ // 不需人工核保
//					"and not exists (select * from lbmission where missionprop1 = trim(a.contno) and activityid = '0000001100') "
//					+ "and not exists (select * from lwmission where missionprop1 = trim(a.contno) and activityid = '0000001110') group by a.contno)";
					"and not exists (select * from lbmission where missionprop1 = trim(a.contno) and activityid  in (select activityid from lwactivity  where functionid ='10010028')) "
					+ "and not exists (select * from lwmission where missionprop1 = trim(a.contno) and activityid  in (select activityid from lwactivity  where functionid ='10010028')) group by a.contno) g";

			logger.debug("开始执行sql操作" + strSQL);
			ExeSQL exeSQL = new ExeSQL();
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(strSQL);
			sqlbv.put("strStartDate", strStartDate);
			sqlbv.put("strEndDate", strEndDate);
			sqlbv.put("strMngCom", strMngCom);
			SSRS ssrs = exeSQL.execSQL(sqlbv);
			String i_count = ssrs.GetText(1, 1);
			logger.debug("i_count的值" + i_count);
			if (!i_count.equals("0")) {
				logger.debug(ssrs.GetText(1, 1));
				col[0] = GetManageName(strMngCom);
				col[1] = ssrs.GetText(1, 1);
				col[2] = mDecimalFormat.format(Double.parseDouble(ssrs.GetText(
						1, 2)));
				if (col[2].equals("0.0000")) {
					col[2] = "0";
				}
				col[2] = col[2] + "天";
			} else {
				CError tError = new CError();
				tError.moduleName = "StandardSxStaticBL";
				tError.functionName = "getDutyGetClmInfo";
				tError.errorMessage = "在该时间段内没有打印信息，请确认起止日期是否正确！！！";
				this.mErrors.addOneError(tError);
				return false;

			}
			tlistTable.add(col);
			if (strMngCom.equals("86")) {
				strSQL = "select comcode From ldcom where char_Length(trim(comcode)) = '4'";
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(strSQL);
				SSRS MSSRS = new SSRS();
				MSSRS = exeSQL.execSQL(sqlbv2);
				logger.debug("循环次数：" + MSSRS.getMaxRow());
				for (int m = 0; m < MSSRS.getMaxRow(); m++) {
					logger.debug("机构代码：" + MSSRS.GetText(m + 1, 1));
					strSQL = " select count(*),sum(tm)/count(*) from (select "+tm+" as tm from ldconttime a "
							+ " where a.businesstype in ('1001','1002','1003','1004') " // 个单录单，复核完成
							+ " and a.makedate between '?strStartDate?' and '"
							+ "?strEndDate?" // 在统计时间区间
							+ "' and exists (select 'X' from lccont qd where qd.salechnl = '1' and qd.proposalcontno=a.contno and qd.managecom like concat('?managecom?','%'))" // 符合保单类型
							+ " and  not exists  (select 'X' from LCIssuePol wx where wx.proposalcontno=a.contno and BackObjType='2') "
							+ // 无外部问题件
//							"and exists (select * from lbmission where missionprop1 = trim(a.contno) and activityid = '0000001003') "
							"and exists (select * from lbmission where missionprop1 = trim(a.contno) and activityid  in (select activityid from lwactivity  where functionid ='10010005')) "
							+ // 自核通过
//							"and not exists (select * from lwmission where missionprop1 = trim(a.contno) and activityid = '0000001100') "
							"and not exists (select * from lwmission where missionprop1 = trim(a.contno) and activityid  in (select activityid from lwactivity  where functionid ='10010028')) "
							+ // 在签单队列
//							"and not exists (select * from lwmission where missionprop1 = trim(a.contno) and activityid = '0000001110') "
							"and not exists (select * from lwmission where missionprop1 = trim(a.contno) and activityid  in (select activityid from lwactivity  where functionid ='10010028')) "
							+ // 不需人工核保
//							"and not exists (select * from lbmission where missionprop1 = trim(a.contno) and activityid = '0000001100') "
//							+ "and not exists (select * from lbmission where missionprop1 = trim(a.contno) and activityid = '0000001110') group by a.contno)";
							"and not exists (select * from lbmission where missionprop1 = trim(a.contno) and activityid  in (select activityid from lwactivity  where functionid ='10010028')) "
							+ "and not exists (select * from lbmission where missionprop1 = trim(a.contno) and activityid  in (select activityid from lwactivity  where functionid ='10010028')) group by a.contno) g";
					logger.debug("开始执行sql操作" + strSQL);
					SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
					sqlbv3.sql(strSQL);
					sqlbv3.put("strStartDate", strStartDate);
					sqlbv3.put("strEndDate", strEndDate);
					sqlbv3.put("managecom", MSSRS.GetText(m + 1, 1));
					SSRS ssrsp = exeSQL.execSQL(sqlbv3);
					tcol = new String[3];
					if (!ssrsp.GetText(1, 1).equals("0")) {
						logger.debug(ssrsp.GetText(1, 1));
						tcol[0] = GetManageName(MSSRS.GetText(m + 1, 1));
						tcol[1] = ssrsp.GetText(1, 1);
						tcol[2] = mDecimalFormat.format(Double
								.parseDouble(ssrsp.GetText(1, 2)));
						if (tcol[2].equals("0.0000")) {
							tcol[2] = "0";
						}
						tcol[2] = tcol[2] + "天";
					} else {
						tcol[0] = GetManageName(MSSRS.GetText(m + 1, 1));
						tcol[1] = ssrsp.GetText(1, 1);
						tcol[2] = "0天";
					}
					tlistTable.add(tcol);
				}

			}

			// String name="1";
			xmlexport.addListTable(tlistTable, cols);
			TextTag texttag = new TextTag(); // Create a TextTag instance
			String type = "";
			if (strSaleChnl.equals("1")) {
				type = "个人业务";
			} else {
				type = "银行代理";
			}
			texttag.add("TYPE", type);
			texttag.add("StartDate", strStartDate);
			texttag.add("EndDate", strEndDate);
			logger.debug("StartDate" + strStartDate);
			logger.debug("EndDate" + strEndDate);
			// Create a XmlExport instance
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}

			// xmlexport.outputDocumentToFile("e:\\", "StandardSxStatic");
			// //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);
		}

		// 银行代理
		if (strSaleChnl.equals("3")) {
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("StandardSxStatic.vts", "printer"); // appoint
																			// to a
																			// special
																			// output
																			// .vts
																			// file
			String[] cols = new String[3];
			ListTable tlistTable = new ListTable();
			tlistTable.setName("List");
			String[] col = new String[3];
			String[] tcol = null;
			String strSQL;

			// choose all Operator from LCPol between start date and end date
			// strSQL="select count(*),Sum() from ldconttime a,lccont b where
			// a.makedate between '"+strStartDate+"' and '"+strEndDate+"' and
			// managecom='"+strMngCom+"' and subtype='UA001'" ;

			strSQL = " select count(*),sum(tm)/count(*) from (select "+tm+" as tm from ldconttime a "
					+ " where a.businesstype in ('1001','1002','1003','1004') " // 个单录单，复核完成
					+ " and a.makedate between '?strStartDate?' and '"
					+ "?strEndDate?" // 在统计时间区间
					+ "' and exists (select 'X' from lccont qd where qd.salechnl = '3' and qd.proposalcontno=a.contno and qd.managecom like concat('?strMngCom?','%'))" // 符合保单类型
					+ " and  not exists  (select 'X' from LCIssuePol wx where wx.proposalcontno=a.contno and BackObjType='2') "
					+ // 无外部问题件
//					"and exists (select * from lbmission where missionprop1 = trim(a.contno) and activityid = '0000001003') "
					"and exists (select * from lbmission where missionprop1 = trim(a.contno) and activityid  in (select activityid from lwactivity  where functionid ='10010005')) "
					+ // 自核通过
//					"and not exists (select * from lbmission where missionprop1 = trim(a.contno) and activityid = '0000001100') "
					"and not exists (select * from lbmission where missionprop1 = trim(a.contno) and activityid  in (select activityid from lwactivity  where functionid ='10010028')) "
					+ // 在签单队列
//					"and not exists (select * from lbmission where missionprop1 = trim(a.contno) and activityid = '0000001110') "
					"and not exists (select * from lbmission where missionprop1 = trim(a.contno) and activityid  in (select activityid from lwactivity  where functionid ='10010028')) "
					+ // 不需人工核保
//					"and not exists (select * from lwmission where missionprop1 = trim(a.contno) and activityid = '0000001100') "
//					+ "and not exists (select * from lwmission where missionprop1 = trim(a.contno) and activityid = '0000001110') group by a.contno)";
					"and not exists (select * from lwmission where missionprop1 = trim(a.contno) and activityid  in (select activityid from lwactivity  where functionid ='10010028')) "
					+ "and not exists (select * from lwmission where missionprop1 = trim(a.contno) and activityid  in (select activityid from lwactivity  where functionid ='10010028')) group by a.contno) g";
			logger.debug("开始执行sql操作" + strSQL);
			ExeSQL exeSQL = new ExeSQL();
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(strSQL);
			sqlbv4.put("strStartDate", strStartDate);
			sqlbv4.put("strEndDate", strEndDate);
			sqlbv4.put("strMngCom", strMngCom);
			SSRS ssrs = exeSQL.execSQL(sqlbv4);
			String i_count = ssrs.GetText(1, 1);
			logger.debug("i_count的值" + i_count);
			if (!i_count.equals("0")) {
				logger.debug(ssrs.GetText(1, 1));
				col[0] = GetManageName(strMngCom);
				col[1] = ssrs.GetText(1, 1);
				col[2] = mDecimalFormat.format(Double.parseDouble(ssrs.GetText(
						1, 2)));
				if (col[2].equals("0.0000")) {
					col[2] = "0";
				}
				col[2] = col[2] + "天";
			} else {
				CError tError = new CError();
				tError.moduleName = "StandardSxStaticBL";
				tError.functionName = "getDutyGetClmInfo";
				tError.errorMessage = "在该时间段内没有打印信息，请确认起止日期是否正确！！！";
				this.mErrors.addOneError(tError);
				return false;

			}
			tlistTable.add(col);
			if (strMngCom.equals("86")) {
				strSQL = "select comcode From ldcom where char_Length(trim(comcode)) = '4'";
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				sqlbv5.sql(strSQL);
				SSRS MSSRS = new SSRS();
				MSSRS = exeSQL.execSQL(sqlbv5);
				logger.debug("循环次数：" + MSSRS.getMaxRow());
				for (int m = 0; m < MSSRS.getMaxRow(); m++) {
					logger.debug("机构代码：" + MSSRS.GetText(m + 1, 1));
					strSQL = " select count(*),sum(tm)/count(*) from (select "+tm+" as tm from ldconttime a "
							+ " where a.businesstype in ('1001','1002','1003','1004') " // 个单录单，复核完成
							+ " and a.makedate between '?strStartDate?' and '"
							+ "?strEndDate?" // 在统计时间区间
							+ "' and exists (select 'X' from lccont qd where qd.salechnl = '3' and qd.proposalcontno=a.contno and qd.managecom like concat('?managecom?','%'))" // 符合保单类型
							+ " and  not exists  (select 'X' from LCIssuePol wx where wx.proposalcontno=a.contno and BackObjType='2') "
							+ // 无外部问题件
//							"and exists (select * from lbmission where missionprop1 = trim(a.contno) and activityid = '0000001003') "
//							+ // 自核通过
//							"and not exists (select * from lbmission where missionprop1 = trim(a.contno) and activityid = '0000001100') "
//							+ // 在签单队列
//							"and not exists (select * from lbmission where missionprop1 = trim(a.contno) and activityid = '0000001110') "
//							+ // 不需人工核保
//							"and not exists (select * from lwmission where missionprop1 = trim(a.contno) and activityid = '0000001100') "
//							+ "and not exists (select * from lwmission where missionprop1 = trim(a.contno) and activityid = '0000001110') group by a.contno)";
							"and exists (select * from lbmission where missionprop1 = trim(a.contno) and activityid  in (select activityid from lwactivity  where functionid ='10010005')) "
							+ // 自核通过
							"and not exists (select * from lbmission where missionprop1 = trim(a.contno) and activityid  in (select activityid from lwactivity  where functionid ='10010028')) "
							+ // 在签单队列
							"and not exists (select * from lbmission where missionprop1 = trim(a.contno) and activityid  in (select activityid from lwactivity  where functionid ='10010028')) "
							+ // 不需人工核保
							"and not exists (select * from lwmission where missionprop1 = trim(a.contno) and activityid  in (select activityid from lwactivity  where functionid ='10010028')) "
							+ "and not exists (select * from lwmission where missionprop1 = trim(a.contno) and activityid  in (select activityid from lwactivity  where functionid ='10010028')) group by a.contno) g";
					// strSQL = "select count(*)
					// ,round(sum((b.backdate-a.makedate)+(to_number(to_date(b.backtime,'HH24:MI:SS')-to_date(a.maketime,'HH24:MI:SS'))))/count(*),5)
					// from ldconttime a ,ldconttime b where a.businesstype "
					// + "in ('1001','1002') and a.contno in (select contno from
					// lccont where salechnl = '3') and "
					// + " not exists "
					// +" (select 'X' from LCIssuePol wx where
					// wx.contno=a.contno and BackObjType='2') "
					// + "and b.businesstype = '1003' and b.contno = a.contno
					// and a.makedate between '"
					// + strStartDate + "' and '"
					// + strEndDate +
					// "' and a.contno in (select g.contno from lccont g where
					// g.managecom like '" +
					// MSSRS.GetText(m + 1, 1) + "%')" +
					// "and exists (select * from lbmission where missionprop1 =
					// trim(a.contno) and activityid = '0000001003') " +
					// "and exists (select * from lwmission where missionprop1 =
					// trim(a.contno) and activityid = '0000001150') " +
					// "and not exists (select * from lbmission where
					// missionprop1 = trim(a.contno) and activityid =
					// '0000001110') " +
					// "and not exists (select * from lwmission where
					// missionprop1 = trim(a.contno) and activityid =
					// '0000001100') " +
					// "and not exists (select * from lwmission where
					// missionprop1 = trim(a.contno) and activityid =
					// '0000001110') ";

					logger.debug("开始执行sql操作" + strSQL);
					SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
					sqlbv6.sql(strSQL);
					sqlbv6.put("strStartDate", strStartDate);
					sqlbv6.put("strEndDate", strEndDate);
					sqlbv6.put("managecom", MSSRS.GetText(m + 1, 1));
					SSRS ssrsp = exeSQL.execSQL(sqlbv6);
					tcol = new String[3];
					if (!ssrsp.GetText(1, 1).equals("0")) {
						logger.debug(ssrsp.GetText(1, 1));
						tcol[0] = GetManageName(MSSRS.GetText(m + 1, 1));
						tcol[1] = ssrsp.GetText(1, 1);
						tcol[2] = mDecimalFormat.format(Double
								.parseDouble(ssrsp.GetText(1, 2)));
						if (tcol[2].equals("0.0000")) {
							tcol[2] = "0";
						}
						tcol[2] = tcol[2] + "天";
					} else {
						tcol[0] = GetManageName(MSSRS.GetText(m + 1, 1));
						tcol[1] = ssrsp.GetText(1, 1);
						tcol[2] = "0天";
					}
					tlistTable.add(tcol);
				}

			}

			// String name="1";
			xmlexport.addListTable(tlistTable, cols);
			TextTag texttag = new TextTag(); // Create a TextTag instance
			String type = "";
			if (strSaleChnl.equals("1")) {
				type = "个人业务";
			} else {
				type = "银行代理";
			}
			texttag.add("TYPE", type);
			texttag.add("StartDate", strStartDate);
			texttag.add("EndDate", strEndDate);
			logger.debug("StartDate" + strStartDate);
			logger.debug("EndDate" + strEndDate);
			// Create a XmlExport instance
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}

			// xmlexport.outputDocumentToFile("e:\\", "StandardSxStatic");
			// //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);
		}
		return true;
	}

	public String GetManageName(String mamagecode) {
		String tsql = "select name from ldcom where comcode = '" + "?mamagecode?"
				+ "'";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(tsql);
		sqlbv7.put("mamagecode", mamagecode);
		ExeSQL texesql = new ExeSQL();
		SSRS tssrs = new SSRS();
		tssrs = texesql.execSQL(sqlbv7);
		return tssrs.GetText(1, 1);

	}

}
