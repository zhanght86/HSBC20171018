package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
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

public class UWStaticPrintBL implements BqBill {
private static Logger logger = Logger.getLogger(UWStaticPrintBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	private String strMngCom;
	private String strStartDate;
	private String strEndDate;
	private String tUWStatType;

	public UWStaticPrintBL() {
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
		// strMngCom = (String) cInputData.get(0);
		// strStartDate = (String) cInputData.get(1);
		// strEndDate = (String) cInputData.get(2);
		// tUWStatType = (String) cInputData.get(3);

		TransferData tTD = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (tTD == null) {
			buildError("getInputData", "传入数据不足！");
			return false;
		}
		strMngCom = (String) tTD.getValueByName("ManageCom");
		strStartDate = (String) tTD.getValueByName("StartDate");
		strEndDate = (String) tTD.getValueByName("EndDate");
		tUWStatType = (String) tTD.getValueByName("UWStatType");

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
		tlistTable.setName("Work");
		String tSQL = " select distinct a.riskcode,"
				+ " (select lmrisk.riskshortname"
				+ "  from lmrisk"
				+ " where lmrisk.riskcode = a.riskcode),"
				+ " case"
				+ " when b.subriskflag = 'M' then"
				+ "  '主险'"
				+ " when b.subriskflag = 'S' then"
				+ "  '附加险'"
				+ "  end,"
				+ " case"
				+ "  when c.risksortvalue = '1' then"
				+ "   '寿险'"
				+ "  when c.risksortvalue = '2' then"
				+ "   '重疾'"
				+ " when c.risksortvalue = '3' then"
				+ "   '短期健康险'"
				+ " when c.risksortvalue = '4' then"
				+ "   '意外伤害'"
				+ "  when c.risksortvalue = '5' then"
				+ "   '投连'"
				+ " when c.risksortvalue = '6' then"
				+ "   '年金'"
				+ "  when c.risksortvalue = '7' then"
				+ "   '卡式'"
				+ "  when c.risksortvalue = '0' then"
				+ "   '无分类'"
				+ " end"
				+ "  from lcpol a, lmriskapp b,lmrisksort c"
				+ "  where a.managecom like concat('"
				+ "?strMngCom?"
				+ "','%')"
				+ "  and a.makedate between '"
				+ "?strStartDate?"
				+ "' and '"
				+ "?strEndDate?"
				+ "'"
				+ "  and a.riskcode = b.riskcode"
				+ "  and a.riskcode = c.riskcode"
				+ "  and a.salechnl='1'"
				+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.paytodate is null)"
				+ " and exists (select 'x'"
				+ " from lccont"
				+ " where lccont.prtno = a.prtno"
				+ " and lccont.cvalidate = a.cvalidate)"
				+ " and c.risksorttype='3' and char_length(trim(a.contno)) <>17 order by a.riskcode";
		// 如果是核保员质量评审则相关的SQL
		if (tUWStatType.equals("6")) {
			tSQL = " select distinct tempa.ab,"
					+ " tempa.ac,"
					+ " tempa.ad,"
					+ " tempa.ae,"
					+ " (select username from lduser where usercode = tempb.b2),"
					+ " tempb.b2" + " from (select distinct a.prtno as aa,"
					+ " a.riskcode as ab," + " (select lmrisk.riskshortname"
					+ " from lmrisk"
					+ " where lmrisk.riskcode = a.riskcode) as ac," + " case"
					+ " when b.subriskflag = 'M' then" + " '主险'"
					+ " when b.subriskflag = 'S' then" + " '附加险'"
					+ " end as ad," + " case"
					+ " when c.risksortvalue = '1' then" + " '寿险'"
					+ " when c.risksortvalue = '2' then" + " '重疾'"
					+ " when c.risksortvalue = '3' then" + " '短期健康险'"
					+ " when c.risksortvalue = '4' then" + " '意外伤害'"
					+ " when c.risksortvalue = '5' then" + " '投连'"
					+ " when c.risksortvalue = '6' then" + " '年金'"
					+ " when c.risksortvalue = '7' then" + " '卡式'"
					+ " when c.risksortvalue = '0' then" + " '无分类'"
					+ " end as ae" + " from lcpol a, lmriskapp b, lmrisksort c"
					+ " where a.managecom like concat('"
					+ "?strMngCom?"
					+ "','%')"
					+ " and a.makedate between to_date('?strStartDate?','yyyy-mm-dd') and to_date('?strEndDate?','yyyy-mm-dd')"
					+ " and a.riskcode = b.riskcode"
					+ " and a.riskcode = c.riskcode"
					+ " and a.salechnl = '1'"
					+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or"
					+ " a.paytodate is null)"
					+ " and exists (select 'x'"
					+ " from lccont"
					+ " where lccont.prtno = a.prtno"
					+ " and lccont.cvalidate = a.cvalidate)"
					+ " and c.risksorttype = '3'"
					+ " and char_length(trim(a.contno)) <> 17) tempa,"
					+ " (select missionprop1 as b1, defaultoperator as b2"
					+ " from lbmission"
//					+ " where activityid = '0000001100'"
					+ " where activityid in (select activityid from lwactivity  where functionid ='10010028')"
					+ " union"
					+ " select missionprop1, defaultoperator"
					+ " from lwmission"
//					+ " where activityid = '0000001100'"
					+ " where activityid in (select activityid from lwactivity  where functionid ='10010028')"
					+ " and defaultoperator is not null) tempb"
					+ " where tempa.aa = tempb.b1"
					+ " and tempb.b2 is not null"
					+ " and exists (select 'X' from lduwuser where usercode = tempb.b2)"
					+ " and exists (select 'X'"
					+ " from lduser"
					+ " where usercode = tempb.b2"
					+ " and comcode like concat('"
					+ "?strMngCom?" + "','%'))" + " order by b2, ab";
		}
		// 如果是长险前两年出险率则用下面的SQL
		if (tUWStatType.equals("7")) {
			tSQL = " select distinct a.riskcode,"
					+ " (select lmrisk.riskshortname"
					+ "  from lmrisk"
					+ " where lmrisk.riskcode = a.riskcode),"
					+ " case"
					+ " when b.subriskflag = 'M' then"
					+ "  '主险'"
					+ " when b.subriskflag = 'S' then"
					+ "  '附加险'"
					+ "  end,"
					+ " case"
					+ "  when c.risksortvalue = '1' then"
					+ "   '寿险'"
					+ "  when c.risksortvalue = '2' then"
					+ "   '重疾'"
					+ " when c.risksortvalue = '3' then"
					+ "   '短期健康险'"
					+ " when c.risksortvalue = '4' then"
					+ "   '意外伤害'"
					+ "  when c.risksortvalue = '5' then"
					+ "   '投连'"
					+ " when c.risksortvalue = '6' then"
					+ "   '年金'"
					+ "  when c.risksortvalue = '7' then"
					+ "   '卡式'"
					+ "  when c.risksortvalue = '0' then"
					+ "   '无分类'"
					+ " end"
					+ "  from lcpol a, lmriskapp b,lmrisksort c"
					+ "  where a.managecom like concat('"
					+ "?strMngCom?"
					+ "','%')"
					+ "  and Months_between('"
					+ "?strEndDate?"
					+ "',a.cvalidate) <=24"
					+ "  and a.riskcode = b.riskcode"
					+ "  and a.riskcode = c.riskcode"
					+ "  and a.salechnl='1'"
					+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.paytodate is null)"
					+ " and exists (select 'x'" + " from lccont"
					+ " where lccont.prtno = a.prtno"
					+ " and lccont.cvalidate = a.cvalidate)"
					+ " and a.uwcode is not null" + " and b.subriskflag='M'"
					+ " and c.risksortvalue in ('1','2','6')"
					+ " and c.risksorttype='3' " + " order by a.riskcode";
		}
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSQL);
		sqlbv1.put("strMngCom", strMngCom);
		sqlbv1.put("strEndDate", strEndDate);
		sqlbv1.put("strStartDate", strStartDate);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS allSSRS = tExeSQL.execSQL(sqlbv1);
		int allRiskCount = allSSRS.getMaxRow();
		logger.debug("allRiskCount=" + allRiskCount);
		if (allRiskCount == 0) {
			CError tError = new CError();
			tError.moduleName = "PNoticeBillBL";
			tError.functionName = "getDutyGetClmInfo";
			tError.errorMessage = "在该时间段内没有打印信息，请确认起止日期是否正确！！！";
			this.mErrors.addOneError(tError);
			return false;
		}

		String[] cols = new String[allRiskCount + 1];
		if (tUWStatType.equals("1")) {
			XmlExport xmlexport = new XmlExport(); // Create a XmlExport
													// instance
			xmlexport.createDocument("cbzkstatic.vts", "printer");
			String name = "";
			String sql = "select name from ldcom where comcode='" + "?strMngCom?"
					+ "'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(sql);
			sqlbv2.put("strMngCom", strMngCom);
			ExeSQL exeSQL = new ExeSQL();
			SSRS ssrs = exeSQL.execSQL(sqlbv2);
			name = ssrs.GetText(1, 1);
			logger.debug("开始执行strOperation＝" + tUWStatType + "的sql操作");
			String strSQL = "";
			ExeSQL exeSQL1 = new ExeSQL();
			SSRS ssrs1 = new SSRS();
			// 对于每一个险种进行循环判断相关的取值
			for (int i = 0; i < allRiskCount; i++) {
				String[] tCount = new String[12];
				// 查询预收录入件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')";
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				sqlbv3.sql(strSQL);
				sqlbv3.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv3.put("strStartDate", strStartDate);
				sqlbv3.put("strEndDate", strEndDate);
				sqlbv3.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv3);
				tCount[0] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 查询出具核保结论件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and ((appflag is not null and appflag <> '0') or uwflag = '1' or uwflag = '2')"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)";
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql(strSQL);
				sqlbv4.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv4.put("strStartDate", strStartDate);
				sqlbv4.put("strEndDate", strEndDate);
				sqlbv4.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv4);
				tCount[1] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 电脑核保通过件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and char_length(trim(contno)) <>17"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and (appflag is not null and appflag <> '0') "
						+ " and not exists"
						+ "  (select 'X'"
						+ "  from lbmission"
//						+ "  where activityid = '0000001100'"
						+ "  where activityid in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and missionprop1 ="
						+ "  lcpol.prtno)"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)";
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				sqlbv5.sql(strSQL);
				sqlbv5.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv5.put("strStartDate", strStartDate);
				sqlbv5.put("strEndDate", strEndDate);
				sqlbv5.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv5);
				tCount[2] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 人工核保件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+
						// modify by ml 20060214 效率调整
						// " and exists (select missionprop1" +
						" and prtno in (select rpad(missionprop1, lislen('lcpol', 'contno'), ' ')"
						+ " from lbmission"
						+
						// " where activityid = '0000001100' and missionprop1 =
						// trim(lcpol.prtno)" +
//						" where activityid = '0000001100' and missionprop10 like '"
						" where activityid in (select activityid from lwactivity  where functionid ='10010028') and missionprop10 like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " union"
						+
						// " select missionprop1" +
						" select rpad(missionprop1, lislen('lcpol', 'contno'), ' ')"
						+ " from lwmission"
						+
						// " where activityid = '0000001100' and missionprop1 =
						// trim(lcpol.prtno))" +
//						" where activityid = '0000001100' and missionprop10 like '"
						" where activityid in (select activityid from lwactivity  where functionid ='10010028') and missionprop10 like concat('"
						+ "?strMngCom?"
						+ "','%'))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
				sqlbv6.sql(strSQL);
				sqlbv6.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv6.put("strStartDate", strStartDate);
				sqlbv6.put("strEndDate", strEndDate);
				sqlbv6.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv6);
				tCount[3] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 人工核保标准承保件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and char_length(trim(contno)) <>17"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and (appflag is not null and appflag <> '0') "
						+ " and uwflag = '9' "
						+ " and exists"
						+ "  (select 'X'"
						+ "  from lbmission"
//						+ "  where activityid = '0000001100'"
						+ "  where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and missionprop1 ="
						+ "  lcpol.prtno)"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
				sqlbv7.sql(strSQL);
				sqlbv7.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv7.put("strStartDate", strStartDate);
				sqlbv7.put("strEndDate", strEndDate);
				sqlbv7.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv7);
				tCount[4] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 承保件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and (appflag is not null and appflag <> '0') "
						+ " and uwflag<>'1' and uwflag<>'2'"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)";
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
				sqlbv8.sql(strSQL);
				sqlbv8.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv8.put("strStartDate", strStartDate);
				sqlbv8.put("strEndDate", strEndDate);
				sqlbv8.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv8);
				tCount[5] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 加费成功件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and (appflag is not null and appflag <> '0') "
						+ " and uwflag = '3'"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)" +
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)"
						+ " and exists (select 'X'"
						+ " from lcprem"
						+ " where lcprem.polno=lcpol.polno"
						+ " and lcprem.payplantype in ('01')"
						+ " and managecom like concat('" + "?strMngCom?" + "','%'))";
				SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
				sqlbv9.sql(strSQL);
				sqlbv9.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv9.put("strStartDate", strStartDate);
				sqlbv9.put("strEndDate", strEndDate);
				sqlbv9.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv9);
				tCount[6] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 拒保件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and char_length(trim(contno)) <>17"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and uwflag = '1' "
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)";
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
				sqlbv10.sql(strSQL);
				sqlbv10.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv10.put("strStartDate", strStartDate);
				sqlbv10.put("strEndDate", strEndDate);
				sqlbv10.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv10);
				tCount[7] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 延期件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and uwflag = '2' "
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)";
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
				sqlbv11.sql(strSQL);
				sqlbv11.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv11.put("strStartDate", strStartDate);
				sqlbv11.put("strEndDate", strEndDate);
				sqlbv11.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv11);
				tCount[8] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 撤保件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and (uwflag = 'a' "
						+ " or exists(select 'X' from lccont where contno=lcpol.contno and uwflag='a'))"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)";
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
				sqlbv12.sql(strSQL);
				sqlbv12.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv12.put("strStartDate", strStartDate);
				sqlbv12.put("strEndDate", strEndDate);
				sqlbv12.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv12);
				tCount[9] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 特别约定件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and uwflag = '4' "
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)";
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
				sqlbv13.sql(strSQL);
				sqlbv13.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv13.put("strStartDate", strStartDate);
				sqlbv13.put("strEndDate", strEndDate);
				sqlbv13.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv13);
				tCount[10] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 生调件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and polno = mainpolno"
						+ " and exists (select 'X' from lcrreport where lcrreport.proposalcontno = lcpol.prtno)"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)";
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
				sqlbv14.sql(strSQL);
				sqlbv14.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv14.put("strStartDate", strStartDate);
				sqlbv14.put("strEndDate", strEndDate);
				sqlbv14.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv14);
				tCount[11] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				String[] cols1 = new String[18];
				cols1[0] = strMngCom;
				cols1[1] = name;
				cols1[2] = allSSRS.GetText(i + 1, 1);
				cols1[3] = allSSRS.GetText(i + 1, 2);
				cols1[4] = allSSRS.GetText(i + 1, 3);
				cols1[5] = allSSRS.GetText(i + 1, 4);
				cols1[6] = tCount[0];
				cols1[7] = tCount[1];
				cols1[8] = tCount[2];
				cols1[9] = tCount[3];
				cols1[10] = tCount[4];
				cols1[11] = tCount[5];
				cols1[12] = tCount[6];
				cols1[13] = tCount[7];
				cols1[14] = tCount[8];
				cols1[15] = tCount[9];
				cols1[16] = tCount[10];
				cols1[17] = tCount[11];

				logger.debug("cols1[0]" + cols1[0]);
				logger.debug("cols1[1]" + cols1[1]);
				logger.debug("cols1[2]" + cols1[2]);
				tlistTable.add(cols1);

			}
			String[] col = new String[18];
			xmlexport.addListTable(tlistTable, col);
			// xmlexport.outputDocumentToFile("e:\\", "CBZKStatic");
			// //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);
			return true;

		}

		if (tUWStatType.equals("2")) {
			XmlExport xmlexport = new XmlExport(); // Create a XmlExport
													// instance
			xmlexport.createDocument("befbstatic.vts", "printer");

			String name = "";
			String sql = "select name from ldcom where comcode='" + "?strMngCom?"
					+ "'";
			SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
			sqlbv15.sql(sql);
			sqlbv15.put("strMngCom", strMngCom);
			ExeSQL exeSQL = new ExeSQL();
			SSRS ssrs = exeSQL.execSQL(sqlbv15);
			name = ssrs.GetText(1, 1);
			logger.debug("开始执行strOperation＝" + tUWStatType + "的sql操作");

			String strSQL = "";
			ExeSQL exeSQL1 = new ExeSQL();
			SSRS ssrs1 = new SSRS();
			// 对于每一个险种进行循环判断相关的取值
			for (int i = 0; i < allRiskCount; i++) {
				String[] tCount = new String[15];
				// 查询预收录入件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
				sqlbv16.sql(strSQL);
				sqlbv16.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv16.put("strStartDate", strStartDate);
				sqlbv16.put("strEndDate", strEndDate);
				sqlbv16.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv16);
				tCount[0] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 查询出具核保结论件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and ((appflag is not null and appflag <> '0') or uwflag = '1' or uwflag = '2')"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
				sqlbv17.sql(strSQL);
				sqlbv17.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv17.put("strStartDate", strStartDate);
				sqlbv17.put("strEndDate", strEndDate);
				sqlbv17.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv17);
				tCount[1] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// ≤2万
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and amnt<=20000"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
				sqlbv18.sql(strSQL);
				sqlbv18.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv18.put("strStartDate", strStartDate);
				sqlbv18.put("strEndDate", strEndDate);
				sqlbv18.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv18);
				tCount[2] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 20001-5万
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and amnt>20000 and amnt<=50000"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
				sqlbv19.sql(strSQL);
				sqlbv19.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv19.put("strStartDate", strStartDate);
				sqlbv19.put("strEndDate", strEndDate);
				sqlbv19.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv19);
				tCount[3] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 50001-10万
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and amnt>50000 and amnt<=100000"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
				sqlbv20.sql(strSQL);
				sqlbv20.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv20.put("strStartDate", strStartDate);
				sqlbv20.put("strEndDate", strEndDate);
				sqlbv20.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv20);
				tCount[4] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 100001-20万
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and amnt>100000 and amnt<=200000"
						+
						// " and ((appflag is not null and appflag <> '0') or
						// uwflag = '1' or uwflag = '2')" +
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)";
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
				sqlbv21.sql(strSQL);
				sqlbv21.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv21.put("strStartDate", strStartDate);
				sqlbv21.put("strEndDate", strEndDate);
				sqlbv21.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv21);
				tCount[5] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 200001-30万
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and amnt>200000 and amnt<=300000"
						+
						// " and ((appflag is not null and appflag <> '0') or
						// uwflag = '1' or uwflag = '2')" +
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)";
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
				sqlbv22.sql(strSQL);
				sqlbv22.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv22.put("strStartDate", strStartDate);
				sqlbv22.put("strEndDate", strEndDate);
				sqlbv22.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv22);
				tCount[6] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// ＞30万
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and amnt>300000"
						+
						// " and ((appflag is not null and appflag <> '0') or
						// uwflag = '1' or uwflag = '2')" +
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)";
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
				sqlbv23.sql(strSQL);
				sqlbv23.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv23.put("strStartDate", strStartDate);
				sqlbv23.put("strEndDate", strEndDate);
				sqlbv23.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv23);
				tCount[7] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 承保件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and (appflag is not null and appflag <> '0') "
						+ " and uwflag<>'1' and uwflag<>'2'"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)";
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
				sqlbv24.sql(strSQL);
				sqlbv24.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv24.put("strStartDate", strStartDate);
				sqlbv24.put("strEndDate", strEndDate);
				sqlbv24.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv24);
				tCount[8] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// ≤2万
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and (appflag is not null and appflag <> '0') "
						+ " and uwflag<>'1' and uwflag<>'2'"
						+ " and amnt <= 20000"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)";
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
				sqlbv25.sql(strSQL);
				sqlbv25.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv25.put("strStartDate", strStartDate);
				sqlbv25.put("strEndDate", strEndDate);
				sqlbv25.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv25);
				tCount[9] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 20001-5万
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and (appflag is not null and appflag <> '0') "
						+ " and uwflag<>'1' and uwflag<>'2'"
						+ " and amnt > 20000 and amnt <= 50000"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)";
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
				sqlbv26.sql(strSQL);
				sqlbv26.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv26.put("strStartDate", strStartDate);
				sqlbv26.put("strEndDate", strEndDate);
				sqlbv26.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv26);
				tCount[10] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 50001-10万
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and (appflag is not null and appflag <> '0') "
						+ " and uwflag<>'1' and uwflag<>'2'"
						+ " and amnt > 50000 and amnt <= 100000"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)";
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
				sqlbv27.sql(strSQL);
				sqlbv27.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv27.put("strStartDate", strStartDate);
				sqlbv27.put("strEndDate", strEndDate);
				sqlbv27.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv27);
				tCount[11] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 100001-20万
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and (appflag is not null and appflag <> '0') "
						+ " and uwflag<>'1' and uwflag<>'2'"
						+ " and amnt > 100000 and amnt <= 200000"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)";
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
				sqlbv28.sql(strSQL);
				sqlbv28.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv28.put("strStartDate", strStartDate);
				sqlbv28.put("strEndDate", strEndDate);
				sqlbv28.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv28);
				tCount[12] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 200001-30万
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and (appflag is not null and appflag <> '0') "
						+ " and uwflag<>'1' and uwflag<>'2'"
						+ " and amnt > 200000 and amnt <= 300000"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)";
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv29 = new SQLwithBindVariables();
				sqlbv29.sql(strSQL);
				sqlbv29.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv29.put("strStartDate", strStartDate);
				sqlbv29.put("strEndDate", strEndDate);
				sqlbv29.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv29);
				tCount[13] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// ＞30万
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and (appflag is not null and appflag <> '0') "
						+ " and uwflag<>'1' and uwflag<>'2'"
						+ " and amnt > 300000"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)";
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
				sqlbv30.sql(strSQL);
				sqlbv30.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv30.put("strStartDate", strStartDate);
				sqlbv30.put("strEndDate", strEndDate);
				sqlbv30.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv30);
				tCount[14] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				String[] cols1 = new String[21];
				cols1[0] = strMngCom;
				cols1[1] = name;
				cols1[2] = allSSRS.GetText(i + 1, 1);
				cols1[3] = allSSRS.GetText(i + 1, 2);
				cols1[4] = allSSRS.GetText(i + 1, 3);
				cols1[5] = allSSRS.GetText(i + 1, 4);
				cols1[6] = tCount[0];
				cols1[7] = tCount[1];
				cols1[8] = tCount[2];
				cols1[9] = tCount[3];
				cols1[10] = tCount[4];
				cols1[11] = tCount[5];
				cols1[12] = tCount[6];
				cols1[13] = tCount[7];
				cols1[14] = tCount[8];
				cols1[15] = tCount[9];
				cols1[16] = tCount[10];
				cols1[17] = tCount[11];
				cols1[18] = tCount[12];
				cols1[19] = tCount[13];
				cols1[20] = tCount[14];

				logger.debug("cols1[0]" + cols1[0]);
				logger.debug("cols1[1]" + cols1[1]);
				logger.debug("cols1[2]" + cols1[2]);
				tlistTable.add(cols1);

			}
			String[] col = new String[21];
			xmlexport.addListTable(tlistTable, col);
			// xmlexport.outputDocumentToFile("e:\\", "BEFBZK"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);
			return true;
		}
		if (tUWStatType.equals("3")) {
			XmlExport xmlexport = new XmlExport(); // Create a XmlExport
													// instance
			xmlexport.createDocument("tjzkstatic.vts", "printer");
			String name = "";
			String sql = "select name from ldcom where comcode='" + "?strMngCom?"
					+ "'";
			SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
			sqlbv31.sql(sql);
			sqlbv31.put("strMngCom", strMngCom);
			ExeSQL exeSQL = new ExeSQL();
			SSRS ssrs = exeSQL.execSQL(sqlbv31);
			name = ssrs.GetText(1, 1);
			logger.debug("开始执行strOperation＝" + tUWStatType + "的sql操作");
			String strSQL = "";
			ExeSQL exeSQL1 = new ExeSQL();
			SSRS ssrs1 = new SSRS();
			// 对于每一个险种进行循环判断相关的取值
			for (int i = 0; i < allRiskCount; i++) {
				String[] tCount = new String[17];
				// 查询预收录入件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)";
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
				sqlbv32.sql(strSQL);
				sqlbv32.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv32.put("strStartDate", strStartDate);
				sqlbv32.put("strEndDate", strEndDate);
				sqlbv32.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv32);
				tCount[0] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 查询出具核保结论件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and ((appflag is not null and appflag <> '0') or uwflag = '1' or uwflag = '2')"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)";
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
				sqlbv33.sql(strSQL);
				sqlbv33.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv33.put("strStartDate", strStartDate);
				sqlbv33.put("strEndDate", strEndDate);
				sqlbv33.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv33);
				tCount[1] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 人工核保件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+
						// modify by ml 20060214 效率调整
						// " and exists (select missionprop1" +
						" and prtno in (select rpad(missionprop1, lislen('lcpol', 'contno'), ' ')"
						+ " from lbmission"
						+
						// " where activityid = '0000001100' and missionprop1 =
						// trim(lcpol.prtno)" +
//						" where activityid = '0000001100' and missionprop10 like '"
						" where activityid in (select activityid from lwactivity  where functionid ='10010028') and missionprop10 like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " union"
						+
						// " select missionprop1" +
						" select rpad(missionprop1, lislen('lcpol', 'contno'), ' ')"
						+ " from lwmission"
						+
						// " where activityid = '0000001100' and missionprop1 =
						// trim(lcpol.prtno))" +
//						" where activityid = '0000001100' and missionprop10 like '"
						" where activityid in (select activityid from lwactivity  where functionid ='10010028') and missionprop10 like concat('"
						+ "?strMngCom?"
						+ "','%'))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv34 = new SQLwithBindVariables();
				sqlbv34.sql(strSQL);
				sqlbv34.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv34.put("strStartDate", strStartDate);
				sqlbv34.put("strEndDate", strEndDate);
				sqlbv34.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv34);
				tCount[2] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 发出体检通知件数
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(b.contno)"
						+ " from lcpol a,lcpenotice b" + " where "
						+ " a.riskcode  like '"
						+ "?riskcode?"
						+ "'"
						+ " and a.makedate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')"
						+ " and a.managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.paytodate is null)"
						+ " and exists (select missionprop1"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno)"
						+ " where activityid in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno)"
						+ " union"
						+ " select missionprop1"
						+ " from lwmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno))"
						+ " where activityid in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = a.prtno"
						+ " and lccont.cvalidate = a.cvalidate)"
						+ " and a.polno = a.mainpolno"
						+ " and a.prtno = b.proposalcontno";
				SQLwithBindVariables sqlbv35 = new SQLwithBindVariables();
				sqlbv35.sql(strSQL);
				sqlbv35.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv35.put("strStartDate", strStartDate);
				sqlbv35.put("strEndDate", strEndDate);
				sqlbv35.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv35);
				tCount[3] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 体检应答件数
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(b.contno)"
						+ " from lcpol a,lcpenotice b" + " where "
						+ " a.riskcode  like '"
						+ "?riskcode?"
						+ "'"
						+ " and a.makedate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')"
						+ " and a.managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.paytodate is null)"
						+ " and exists (select missionprop1"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno)"
						+ " where activityid in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno)"
						+ " union"
						+ " select missionprop1"
						+ " from lwmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno))"
						+ " where activityid in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = a.prtno"
						+ " and lccont.cvalidate = a.cvalidate)"
						+ " and a.polno = a.mainpolno"
						+ " and a.prtno = b.proposalcontno"
						+ " and exists (select 'X'"
						+ " from lcpenoticeitem"
						+ " where lcpenoticeitem.proposalcontno = a.prtno"
						+ " and lcpenoticeitem.peitemresult is not null)";
				SQLwithBindVariables sqlbv36 = new SQLwithBindVariables();
				sqlbv36.sql(strSQL);
				sqlbv36.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv36.put("strStartDate", strStartDate);
				sqlbv36.put("strEndDate", strEndDate);
				sqlbv36.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv36);
				tCount[4] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 体检阳性件数
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(b.contno)"
						+ " from lcpol a,lcpenotice b" + " where "
						+ " a.riskcode  like '"
						+ "?riskcode?"
						+ "'"
						+ " and a.makedate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')"
						+ " and a.managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.paytodate is null)"
						+ " and exists (select missionprop1"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno)"
						+ " where activityid in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno)"
						+ " union"
						+ " select missionprop1"
						+ " from lwmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno))"
						+ " where activityid in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = a.prtno"
						+ " and lccont.cvalidate = a.cvalidate)"
						+ " and a.polno = a.mainpolno"
						+ " and a.prtno = b.proposalcontno"
						+ " and b.masculineflag='Y'";
				SQLwithBindVariables sqlbv37 = new SQLwithBindVariables();
				sqlbv37.sql(strSQL);
				sqlbv37.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv37.put("strStartDate", strStartDate);
				sqlbv37.put("strEndDate", strEndDate);
				sqlbv37.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv37);
				tCount[5] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 体检阳性未告知件数
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(b.contno)"
						+ " from lcpol a,lcpenotice b" + " where "
						+ " a.riskcode  like '"
						+ "?riskcode?"
						+ "'"
						+ " and a.makedate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')"
						+ " and a.managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.paytodate is null)"
						+ " and exists (select missionprop1"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and (missionprop1) = trim(a.prtno)"
						+ " union"
						+ " select missionprop1"
						+ " from lwmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and "
						+ " missionprop1 = trim(a.prtno))"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = a.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = a.polno)" +
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = a.prtno"
						+ " and lccont.cvalidate = a.cvalidate)"
						+ " and a.polno = a.mainpolno"
						+ " and a.prtno = b.proposalcontno"
						+ " and b.masculineflag='Y'"
						+ " and not exists (select 'X'"
						+ " from lccustomerimpart"
						+ " where lccustomerimpart.prtno = a.prtno"
						+ " and lccustomerimpart.impartcode <> '000'"
						+ " and lccustomerimpart.impartver = '02')";
				SQLwithBindVariables sqlbv38 = new SQLwithBindVariables();
				sqlbv38.sql(strSQL);
				sqlbv38.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv38.put("strStartDate", strStartDate);
				sqlbv38.put("strEndDate", strEndDate);
				sqlbv38.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv38);
				tCount[6] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 达到体检标准
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(b.contno)"
						+ " from lcpol a,lcpenotice b" + " where "
						+ " a.riskcode  like '"
						+ "?riskcode?"
						+ "'"
						+ " and a.makedate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')"
						+ " and a.managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.paytodate is null)"
						+ " and b.pereason='01'"
						+ " and exists (select missionprop1"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno)"
						+ " where activityid in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno)"
						+ " union"
						+ " select missionprop1"
						+ " from lwmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno))"
						+ " where activityid in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = a.prtno"
						+ " and lccont.cvalidate = a.cvalidate)"
						+ " and a.polno = a.mainpolno"
						+ " and a.prtno = b.proposalcontno";
				SQLwithBindVariables sqlbv39 = new SQLwithBindVariables();
				sqlbv39.sql(strSQL);
				sqlbv39.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv39.put("strStartDate", strStartDate);
				sqlbv39.put("strEndDate", strEndDate);
				sqlbv39.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv39);
				tCount[7] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 抽样体检
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(b.contno)"
						+ " from lcpol a,lcpenotice b" + " where "
						+ " a.riskcode  like '"
						+ "?riskcode?"
						+ "'"
						+ " and a.makedate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')"
						+ " and a.managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.paytodate is null)"
						+ " and b.pereason='02'"
						+ " and exists (select missionprop1"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno)"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno)"
						+ " union"
						+ " select missionprop1"
						+ " from lwmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno))"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = a.prtno"
						+ " and lccont.cvalidate = a.cvalidate)"
						+ " and a.polno = a.mainpolno"
						+ " and a.prtno = b.proposalcontno";
				SQLwithBindVariables sqlbv40 = new SQLwithBindVariables();
				sqlbv40.sql(strSQL);
				sqlbv40.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv40.put("strStartDate", strStartDate);
				sqlbv40.put("strEndDate", strEndDate);
				sqlbv40.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv40);
				tCount[8] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 客户要求
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(b.contno)"
						+ " from lcpol a,lcpenotice b" + " where "
						+ " a.riskcode  like '"
						+ "?riskcode?"
						+ "'"
						+ " and a.makedate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')"
						+ " and a.managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.paytodate is null)"
						+ " and b.pereason='10'"
						+ " and exists (select missionprop1"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno)"
						+ " where activityid in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno)"
						+ " union"
						+ " select missionprop1"
						+ " from lwmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno))"
						+ " where activityid in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = a.prtno"
						+ " and lccont.cvalidate = a.cvalidate)"
						+ " and a.polno = a.mainpolno"
						+ " and a.prtno = b.proposalcontno";
				SQLwithBindVariables sqlbv41 = new SQLwithBindVariables();
				sqlbv41.sql(strSQL);
				sqlbv41.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv41.put("strStartDate", strStartDate);
				sqlbv41.put("strEndDate", strEndDate);
				sqlbv41.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv41);
				tCount[9] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 拒保延期史
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(b.contno)"
						+ " from lcpol a,lcpenotice b" + " where "
						+ " a.riskcode  like '"
						+ "?riskcode?"
						+ "'"
						+ " and a.makedate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')"
						+ " and a.managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.paytodate is null)"
						+ " and b.pereason='03'"
						+ " and exists (select missionprop1"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno)"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno)"
						+ " union"
						+ " select missionprop1"
						+ " from lwmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno))"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = a.prtno"
						+ " and lccont.cvalidate = a.cvalidate)"
						+ " and a.polno = a.mainpolno"
						+ " and a.prtno = b.proposalcontno";
				SQLwithBindVariables sqlbv42 = new SQLwithBindVariables();
				sqlbv42.sql(strSQL);
				sqlbv42.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv42.put("strStartDate", strStartDate);
				sqlbv42.put("strEndDate", strEndDate);
				sqlbv42.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv42);
				tCount[10] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 加费史
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(b.contno)"
						+ " from lcpol a,lcpenotice b" + " where "
						+ " a.riskcode  like '"
						+ "?riskcode?"
						+ "'"
						+ " and a.makedate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')"
						+ " and a.managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.paytodate is null)"
						+ " and b.pereason='04'"
						+ " and exists (select missionprop1"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno)"
						+ " where activityid in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno)"
						+ " union"
						+ " select missionprop1"
						+ " from lwmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno))"
						+ " where activityid in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = a.prtno"
						+ " and lccont.cvalidate = a.cvalidate)"
						+ " and a.polno = a.mainpolno"
						+ " and a.prtno = b.proposalcontno";
				SQLwithBindVariables sqlbv43 = new SQLwithBindVariables();
				sqlbv43.sql(strSQL);
				sqlbv43.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv43.put("strStartDate", strStartDate);
				sqlbv43.put("strEndDate", strEndDate);
				sqlbv43.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv43);
				tCount[11] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 复检
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(b.contno)"
						+ " from lcpol a,lcpenotice b" + " where "
						+ " a.riskcode  like '"
						+ "?riskcode?"
						+ "'"
						+ " and a.makedate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')"
						+ " and a.managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.paytodate is null)"
						+ " and b.pereason='05'"
						+ " and exists (select missionprop1"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno)"
						+ " where activityid in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno)"
						+ " union"
						+ " select missionprop1"
						+ " from lwmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno))"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = a.prtno"
						+ " and lccont.cvalidate = a.cvalidate)"
						+ " and a.polno = a.mainpolno"
						+ " and a.prtno = b.proposalcontno";
				SQLwithBindVariables sqlbv44 = new SQLwithBindVariables();
				sqlbv44.sql(strSQL);
				sqlbv44.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv44.put("strStartDate", strStartDate);
				sqlbv44.put("strEndDate", strEndDate);
				sqlbv44.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv44);
				tCount[12] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 高额保件
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(b.contno)"
						+ " from lcpol a,lcpenotice b" + " where "
						+ " a.riskcode  like '"
						+ "?riskcode?"
						+ "'"
						+ " and a.makedate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')"
						+ " and a.managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.paytodate is null)"
						+ " and b.pereason='06'"
						+ " and exists (select missionprop1"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno)"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno)"
						+ " union"
						+ " select missionprop1"
						+ " from lwmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno))"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = a.prtno"
						+ " and lccont.cvalidate = a.cvalidate)"
						+ " and a.polno = a.mainpolno"
						+ " and a.prtno = b.proposalcontno";
				SQLwithBindVariables sqlbv45 = new SQLwithBindVariables();
				sqlbv45.sql(strSQL);
				sqlbv45.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv45.put("strStartDate", strStartDate);
				sqlbv45.put("strEndDate", strEndDate);
				sqlbv45.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv45);
				tCount[13] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 复效
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(b.contno)"
						+ " from lcpol a,lcpenotice b" + " where "
						+ " a.riskcode  like '"
						+ "?riskcode?"
						+ "'"
						+ " and a.makedate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')"
						+ " and a.managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.paytodate is null)"
						+ " and b.pereason='07'"
						+ " and exists (select missionprop1"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno)"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno)"
						+ " union"
						+ " select missionprop1"
						+ " from lwmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno))"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = a.prtno"
						+ " and lccont.cvalidate = a.cvalidate)"
						+ " and a.polno = a.mainpolno"
						+ " and a.prtno = b.proposalcontno";
				SQLwithBindVariables sqlbv46 = new SQLwithBindVariables();
				sqlbv46.sql(strSQL);
				sqlbv46.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv46.put("strStartDate", strStartDate);
				sqlbv46.put("strEndDate", strEndDate);
				sqlbv46.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv46);
				tCount[14] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// //////////////////////////////////////////////////////////////////
				// 加保附加险
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(b.contno)"
						+ " from lcpol a,lcpenotice b" + " where "
						+ " a.riskcode  like '"
						+ "?riskcode?"
						+ "'"
						+ " and a.makedate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')"
						+ " and a.managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.paytodate is null)"
						+ " and b.pereason='08'"
						+ " and exists (select missionprop1"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno)"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno)"
						+ " union"
						+ " select missionprop1"
						+ " from lwmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno))"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = a.prtno"
						+ " and lccont.cvalidate = a.cvalidate)"
						+ " and a.polno = a.mainpolno"
						+ " and a.prtno = b.proposalcontno";
				SQLwithBindVariables sqlbv47 = new SQLwithBindVariables();
				sqlbv47.sql(strSQL);
				sqlbv47.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv47.put("strStartDate", strStartDate);
				sqlbv47.put("strEndDate", strEndDate);
				sqlbv47.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv47);
				tCount[15] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 补充告知
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(b.contno)"
						+ " from lcpol a,lcpenotice b" + " where "
						+ " a.riskcode  like '"
						+ "?riskcode?"
						+ "'"
						+ " and a.makedate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')"
						+ " and a.managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.paytodate is null)"
						+ " and b.pereason='09'"
						+ " and exists (select missionprop1"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno)"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno)"
						+ " union"
						+ " select missionprop1"
						+ " from lwmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno))"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = a.prtno"
						+ " and lccont.cvalidate = a.cvalidate)"
						+ " and a.polno = a.mainpolno"
						+ " and a.prtno = b.proposalcontno";
				SQLwithBindVariables sqlbv48 = new SQLwithBindVariables();
				sqlbv48.sql(strSQL);
				sqlbv48.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv48.put("strStartDate", strStartDate);
				sqlbv48.put("strEndDate", strEndDate);
				sqlbv48.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv48);
				tCount[16] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				String[] cols1 = new String[23];
				cols1[0] = strMngCom;
				cols1[1] = name;
				cols1[2] = allSSRS.GetText(i + 1, 1);
				cols1[3] = allSSRS.GetText(i + 1, 2);
				cols1[4] = allSSRS.GetText(i + 1, 3);
				cols1[5] = allSSRS.GetText(i + 1, 4);
				cols1[6] = tCount[0];
				cols1[7] = tCount[1];
				cols1[8] = tCount[2];
				cols1[9] = tCount[3];
				cols1[10] = tCount[4];
				cols1[11] = tCount[5];
				cols1[12] = tCount[6];
				cols1[13] = tCount[7];
				cols1[14] = tCount[8];
				cols1[15] = tCount[9];
				cols1[16] = tCount[10];
				cols1[17] = tCount[11];
				cols1[18] = tCount[12];
				cols1[19] = tCount[13];
				cols1[20] = tCount[14];
				cols1[21] = tCount[15];
				cols1[22] = tCount[16];

				logger.debug("cols1[0]" + cols1[0]);
				logger.debug("cols1[1]" + cols1[1]);
				logger.debug("cols1[2]" + cols1[2]);
				tlistTable.add(cols1);

			}

			String[] col = new String[1];
			xmlexport.addListTable(tlistTable, col);

			// xmlexport.outputDocumentToFile("e:\\", "PNoticeBill3");
			// //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);
			return true;
		}
		if (tUWStatType.equals("4")) {
			XmlExport xmlexport = new XmlExport(); // Create a XmlExport
													// instance
			xmlexport.createDocument("jfstatic.vts", "printer");

			String name = "";
			String sql = "select name from ldcom where comcode='" + "?strMngCom?"
					+ "'";
			SQLwithBindVariables sqlbv49 = new SQLwithBindVariables();
			sqlbv49.sql(sql);
			sqlbv49.put("strMngCom", strMngCom);
			ExeSQL exeSQL = new ExeSQL();
			SSRS ssrs = exeSQL.execSQL(sqlbv49);
			name = ssrs.GetText(1, 1);
			logger.debug("开始执行strOperation＝" + tUWStatType + "的sql操作");

			String strSQL = "";
			ExeSQL exeSQL1 = new ExeSQL();
			SSRS ssrs1 = new SSRS();
			// 对于每一个险种进行循环判断相关的取值
			for (int i = 0; i < allRiskCount; i++) {
				String[] tCount = new String[11];
				// //查询预收录入件数
				// ////////////////////////////////////////////////////////////////////
				// strSQL =
				// "select count(distinct contno) from lcpol where riskcode='" +
				// allSSRS.GetText(i + 1, 1) + "' and makedate between '" +
				// strStartDate + "' and '" + strEndDate +
				// "' and managecom like '" + strMngCom + "%'" +
				// " and (Months_between(paytodate, cvalidate) <= 12 or
				// Months_between(paytodate, cvalidate) >= 10000 or paytodate is
				// null)" +
				// " and length(trim(contno)) <>17" +
				// " and not exists (select 'X'" +
				// " from lpedoritem, lppol" +
				// " where lpedoritem.contno = lcpol.contno" +
				// " and lpedoritem.edortype = 'NS'" +
				// " and lppol.edorno = lpedoritem.edorno" +
				// " and lppol.polno = lcpol.polno)";
				//
				// ssrs1 = exeSQL1.execSQL(strSQL);
				// tCount[0] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 查询出具核保结论件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and ((appflag is not null and appflag <> '0') or uwflag = '1' or uwflag = '2')"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)";
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv50 = new SQLwithBindVariables();
				sqlbv50.sql(strSQL);
				sqlbv50.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv50.put("strStartDate", strStartDate);
				sqlbv50.put("strEndDate", strEndDate);
				sqlbv50.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv50);
				tCount[1] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 人工核保件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+
						// modify by ml 20060214 效率调整
						// " and exists (select missionprop1" +
						" and prtno in (select rpad(missionprop1, lislen('lcpol', 'contno'), ' ')"
						+ " from lbmission"
						+
						// " where activityid = '0000001100' and missionprop1 =
						// trim(lcpol.prtno)" +
//						" where activityid = '0000001100' and missionprop10 like '"
						" where activityid  in (select activityid from lwactivity  where functionid ='10010028') and missionprop10 like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " union"
						+
						// " select missionprop1" +
						" select rpad(missionprop1, lislen('lcpol', 'contno'), ' ')"
						+ " from lwmission"
						+
						// " where activityid = '0000001100' and missionprop1 =
						// trim(lcpol.prtno))" +
//						" where activityid = '0000001100' and missionprop10 like '"
						" where activityid  in (select activityid from lwactivity  where functionid ='10010028') and missionprop10 like concat('"
						+ "?strMngCom?"
						+ "','%'))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv51 = new SQLwithBindVariables();
				sqlbv51.sql(strSQL);
				sqlbv51.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv51.put("strStartDate", strStartDate);
				sqlbv51.put("strEndDate", strEndDate);
				sqlbv51.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv51);
				tCount[2] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 加费评点<＝ 50件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and uwflag='3'"
						+ " and exists (select 'X'"
						+ " from (select missionprop1"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " union"
						+ " select missionprop1"
						+ " from lwmission"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')) g"
//						+ " where activityid = '0000001100')"
						+ " where "
						+ " missionprop1 = lcpol.prtno)"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)" +
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)"
						+ " and exists (select 'X'"
						+ " from lcprem"
						+ " where lcprem.polno=lcpol.polno"
						+ " and lcprem.payplantype in ('01')"
						+ " and suppriskscore <= 50"
						+ " and managecom like concat('"
						+ "?strMngCom?"
						+ "','%'))";
				SQLwithBindVariables sqlbv52 = new SQLwithBindVariables();
				sqlbv52.sql(strSQL);
				sqlbv52.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv52.put("strStartDate", strStartDate);
				sqlbv52.put("strEndDate", strEndDate);
				sqlbv52.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv52);
				tCount[3] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 评点<＝ 50加费成功件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and uwflag='3'"
						+ " and (appflag is not null and appflag <>'0')"
						+ " and exists (select 'X'"
						+ " from (select missionprop1"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " union"
						+ " select missionprop1"
						+ " from lwmission"
//						+ " where activityid = '0000001100')"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')) g"
						+ " where "
						+ " missionprop1 = lcpol.prtno)"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)" +
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)"
						+ " and exists (select 'X'"
						+ " from lcprem"
						+ " where lcprem.polno=lcpol.polno"
						+ " and lcprem.payplantype in ('01')"
						+ " and suppriskscore <= 50"
						+ " and managecom like concat('"
						+ "?strMngCom?"
						+ "','%'))";
				SQLwithBindVariables sqlbv53 = new SQLwithBindVariables();
				sqlbv53.sql(strSQL);
				sqlbv53.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv53.put("strStartDate", strStartDate);
				sqlbv53.put("strEndDate", strEndDate);
				sqlbv53.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv53);
				tCount[4] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 加费评点75-100件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and uwflag='3'"
						+ " and exists (select 'X'"
						+ " from (select missionprop1"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " union"
						+ " select missionprop1"
						+ " from lwmission"
//						+ " where activityid = '0000001100')"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')) g"
						+ " where "
						+ " missionprop1 = lcpol.prtno)"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)" +
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)"
						+ " and exists (select 'X'"
						+ " from lcprem"
						+ " where lcprem.polno=lcpol.polno"
						+ " and lcprem.payplantype in ('01')"
						+ " and suppriskscore <= 100 and suppriskscore>=75"
						+ " and managecom like concat('"
						+ "?strMngCom?"
						+ "','%'))";
				SQLwithBindVariables sqlbv54 = new SQLwithBindVariables();
				sqlbv54.sql(strSQL);
				sqlbv54.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv54.put("strStartDate", strStartDate);
				sqlbv54.put("strEndDate", strEndDate);
				sqlbv54.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv54);
				tCount[5] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 评点75-100加费成功件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and uwflag='3'"
						+ " and (appflag is not null and appflag <>'0')"
						+ " and exists (select 'X'"
						+ " from (select missionprop1"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " union"
						+ " select missionprop1"
						+ " from lwmission"
//						+ " where activityid = '0000001100')"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')) g"
						+ " where "
						+ " missionprop1 = lcpol.prtno)"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)" +
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)"
						+ " and exists (select 'X'"
						+ " from lcprem"
						+ " where lcprem.polno=lcpol.polno"
						+ " and lcprem.payplantype in ('01')"
						+ " and suppriskscore <= 100 and suppriskscore>=75"
						+ " and managecom like concat('"
						+ "?strMngCom?"
						+ "','%'))";
				SQLwithBindVariables sqlbv55 = new SQLwithBindVariables();
				sqlbv55.sql(strSQL);
				sqlbv55.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv55.put("strStartDate", strStartDate);
				sqlbv55.put("strEndDate", strEndDate);
				sqlbv55.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv55);
				tCount[6] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 加费评点150-200件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and uwflag='3'"
						+ " and exists (select 'X'"
						+ " from (select missionprop1"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " union"
						+ " select missionprop1"
						+ " from lwmission"
//						+ " where activityid = '0000001100')"
						+ " where activityid in (select activityid from lwactivity  where functionid ='10010028')) g"
						+ " where "
						+ " missionprop1 = lcpol.prtno)"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)" +
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)"
						+ " and exists (select 'X'"
						+ " from lcprem"
						+ " where lcprem.polno=lcpol.polno"
						+ " and lcprem.payplantype in ('01')"
						+ " and suppriskscore <= 200 and suppriskscore>=150"
						+ " and managecom like concat('"
						+ "?strMngCom?"
						+ "','%'))";
				SQLwithBindVariables sqlbv56 = new SQLwithBindVariables();
				sqlbv56.sql(strSQL);
				sqlbv56.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv56.put("strStartDate", strStartDate);
				sqlbv56.put("strEndDate", strEndDate);
				sqlbv56.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv56);
				tCount[7] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 评点150-200加费成功件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and uwflag='3'"
						+ " and (appflag is not null and appflag <>'0')"
						+ " and exists (select 'X'"
						+ " from (select missionprop1"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " union"
						+ " select missionprop1"
						+ " from lwmission"
//						+ " where activityid = '0000001100')"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')) g"
						+ " where "
						+ " missionprop1 = lcpol.prtno)"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)" +
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)"
						+ " and exists (select 'X'"
						+ " from lcprem"
						+ " where lcprem.polno=lcpol.polno"
						+ " and lcprem.payplantype in ('01')"
						+ " and suppriskscore <= 200 and suppriskscore>=150"
						+ " and managecom like concat('"
						+ "?strMngCom?"
						+ "','%'))";
				SQLwithBindVariables sqlbv57 = new SQLwithBindVariables();
				sqlbv57.sql(strSQL);
				sqlbv57.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv57.put("strStartDate", strStartDate);
				sqlbv57.put("strEndDate", strEndDate);
				sqlbv57.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv57);
				tCount[8] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 加费评点>200件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and uwflag='3'"
						+ " and exists (select 'X'"
						+ " from (select missionprop1"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " union"
						+ " select missionprop1"
						+ " from lwmission"
//						+ " where activityid = '0000001100')"
						+ " where activityid in (select activityid from lwactivity  where functionid ='10010028')) g"
						+ " where "
						+ " missionprop1 = lcpol.prtno)"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)" +
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)"
						+ " and exists (select 'X'"
						+ " from lcprem"
						+ " where lcprem.polno=lcpol.polno"
						+ " and lcprem.payplantype in ('01')"
						+ " and suppriskscore>200"
						+ " and managecom like concat('"
						+ "?strMngCom?"
						+ "','%'))";
				SQLwithBindVariables sqlbv58 = new SQLwithBindVariables();
				sqlbv58.sql(strSQL);
				sqlbv58.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv58.put("strStartDate", strStartDate);
				sqlbv58.put("strEndDate", strEndDate);
				sqlbv58.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv58);
				tCount[9] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 评点>200加费成功件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and uwflag='3'"
						+ " and (appflag is not null and appflag <>'0')"
						+ " and exists (select 'X'"
						+ " from (select missionprop1"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " union"
						+ " select missionprop1"
						+ " from lwmission"
//						+ " where activityid = '0000001100')"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')) g"
						+ " where "
						+ " missionprop1 = lcpol.prtno)"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)" +
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)"
						+ " and exists (select 'X'"
						+ " from lcprem"
						+ " where lcprem.polno=lcpol.polno"
						+ " and lcprem.payplantype in ('01')"
						+ " and suppriskscore > 200"
						+ " and managecom like concat('"
						+ "?strMngCom?"
						+ "','%'))";
				SQLwithBindVariables sqlbv59 = new SQLwithBindVariables();
				sqlbv59.sql(strSQL);
				sqlbv59.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv59.put("strStartDate", strStartDate);
				sqlbv59.put("strEndDate", strEndDate);
				sqlbv59.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv59);
				tCount[10] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				String[] cols1 = new String[17];
				cols1[0] = strMngCom;
				cols1[1] = name;
				cols1[2] = allSSRS.GetText(i + 1, 1);
				cols1[3] = allSSRS.GetText(i + 1, 2);
				cols1[4] = allSSRS.GetText(i + 1, 3);
				cols1[5] = allSSRS.GetText(i + 1, 4);
				cols1[6] = tCount[1];
				cols1[7] = tCount[2];
				cols1[8] = tCount[3];
				cols1[9] = tCount[4];
				cols1[10] = tCount[5];
				cols1[11] = tCount[6];
				cols1[12] = tCount[7];
				cols1[13] = tCount[8];
				cols1[14] = tCount[9];
				cols1[15] = tCount[10];
				// cols1[16] = tCount[10];

				logger.debug("cols1[0]" + cols1[0]);
				logger.debug("cols1[1]" + cols1[1]);
				logger.debug("cols1[2]" + cols1[2]);
				logger.debug("cols1[14]" + cols1[14]);
				logger.debug("cols1[15]" + cols1[15]);
				logger.debug("cols1[16]" + cols1[16]);
				tlistTable.add(cols1);

			}
			String[] col = new String[16];
			xmlexport.addListTable(tlistTable, col);

			// xmlexport.outputDocumentToFile("d:\\", "PNoticeBill4");
			// //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);
			return true;

		}
		if (tUWStatType.equals("5")) {
			XmlExport xmlexport = new XmlExport(); // Create a XmlExport
													// instance
			xmlexport.createDocument("cbstatic.vts", "printer");
			String name = "";
			String sql = "select name from ldcom where comcode='" + "?strMngCom?"
					+ "'";
			SQLwithBindVariables sqlbv60 = new SQLwithBindVariables();
			sqlbv60.sql(sql);
			sqlbv60.put("strMngCom", strMngCom);
			ExeSQL exeSQL = new ExeSQL();
			SSRS ssrs = exeSQL.execSQL(sqlbv60);
			name = ssrs.GetText(1, 1);
			logger.debug("开始执行strOperation＝" + tUWStatType + "的sql操作");
			String strSQL = "";
			ExeSQL exeSQL1 = new ExeSQL();
			SSRS ssrs1 = new SSRS();
			// 对于每一个险种进行循环判断相关的取值
			for (int i = 0; i < allRiskCount; i++) {
				String[] tCount = new String[7];
				// 查询预收录入件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)";
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv61 = new SQLwithBindVariables();
				sqlbv61.sql(strSQL);
				sqlbv61.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv61.put("strStartDate", strStartDate);
				sqlbv61.put("strEndDate", strEndDate);
				sqlbv61.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv61);
				tCount[0] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 查询出具核保结论件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and ((appflag is not null and appflag <> '0') or uwflag = '1' or uwflag = '2')"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)";
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv62 = new SQLwithBindVariables();
				sqlbv62.sql(strSQL);
				sqlbv62.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv62.put("strStartDate", strStartDate);
				sqlbv62.put("strEndDate", strEndDate);
				sqlbv62.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv62);
				tCount[1] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 人工核保件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+
						// modify by ml 20060214 效率调整
						// " and exists (select missionprop1" +
						" and prtno in (select rpad(missionprop1, lislen('lcpol', 'contno'), ' ')"
						+ " from lbmission"
						+
						// " where activityid = '0000001100' and missionprop1 =
						// trim(lcpol.prtno)" +
//						" where activityid = '0000001100' and missionprop10 like '"
						" where activityid  in (select activityid from lwactivity  where functionid ='10010028') and missionprop10 like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " union"
						+
						// " select missionprop1" +
						" select rpad(missionprop1, lislen('lcpol', 'contno'), ' ')"
						+ " from lwmission"
						+
						// " where activityid = '0000001100' and missionprop1 =
						// trim(lcpol.prtno))" +
//						" where activityid = '0000001100' and missionprop10 like '"
						" where activityid in (select activityid from lwactivity  where functionid ='10010028') and missionprop10 like concat('"
						+ "?strMngCom?"
						+ "','%'))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv63 = new SQLwithBindVariables();
				sqlbv63.sql(strSQL);
				sqlbv63.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv63.put("strStartDate", strStartDate);
				sqlbv63.put("strEndDate", strEndDate);
				sqlbv63.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv63);
				tCount[2] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 总撤保件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and (exists (select 'X'"
						+ " from lccont"
						+ " where contno = lcpol.contno"
						+ " and uwflag = 'a') or uwflag = 'a')"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)";
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv64 = new SQLwithBindVariables();
				sqlbv64.sql(strSQL);
				sqlbv64.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv64.put("strStartDate", strStartDate);
				sqlbv64.put("strEndDate", strEndDate);
				sqlbv64.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv64);
				tCount[3] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 自动撤保件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and exists (select 'X'"
						+ " from lccont"
						+ " where contno = lcpol.contno"
						+ " and uwflag = 'a')"
						+ " and not exists (select 'X'"
						+ " from loprtmanager"
						+ " where loprtmanager.otherno = lcpol.contno"
						+ " and code in"
						+ " ('00', '03', '04', '06', '14', '15', '16', '17', '18', '81', '82', '83', '84', '86', '85', '87', '88', '89'))"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)";
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv65 = new SQLwithBindVariables();
				sqlbv65.sql(strSQL);
				sqlbv65.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv65.put("strStartDate", strStartDate);
				sqlbv65.put("strEndDate", strEndDate);
				sqlbv65.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv65);
				tCount[4] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 体检前撤保件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and exists (select 'X'"
						+ " from lccont"
						+ " where contno = lcpol.contno"
						+ " and uwflag = 'a' )"
						+ " and not exists (select 'X'"
						+ " from lcpenoticeitem"
						+ " where lcpenoticeitem.proposalcontno = lcpol.prtno"
						+ " and lcpenoticeitem.peitemresult is not null)"
						+ " and exists"
						+ " (select 'X' from lcpenotice where lcpenotice.proposalcontno = lcpol.prtno)"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)";
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv66 = new SQLwithBindVariables();
				sqlbv66.sql(strSQL);
				sqlbv66.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv66.put("strStartDate", strStartDate);
				sqlbv66.put("strEndDate", strEndDate);
				sqlbv66.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv66);
				tCount[5] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 加费后撤保件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and exists (select 'X'"
						+ " from lccont"
						+ " where contno = lcpol.contno"
						+ " and uwflag = 'a' )"
						+ " and uwflag='3'"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)";
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)";
				SQLwithBindVariables sqlbv67 = new SQLwithBindVariables();
				sqlbv67.sql(strSQL);
				sqlbv67.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv67.put("strStartDate", strStartDate);
				sqlbv67.put("strEndDate", strEndDate);
				sqlbv67.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv67);
				tCount[6] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				String[] cols1 = new String[13];
				cols1[0] = strMngCom;
				cols1[1] = name;
				cols1[2] = allSSRS.GetText(i + 1, 1);
				cols1[3] = allSSRS.GetText(i + 1, 2);
				cols1[4] = allSSRS.GetText(i + 1, 3);
				cols1[5] = allSSRS.GetText(i + 1, 4);
				cols1[6] = tCount[0];
				cols1[7] = tCount[1];
				cols1[8] = tCount[2];
				cols1[9] = tCount[3];
				cols1[10] = tCount[4];
				cols1[11] = tCount[5];
				cols1[12] = tCount[6];

				logger.debug("cols1[0]" + cols1[0]);
				logger.debug("cols1[1]" + cols1[1]);
				logger.debug("cols1[2]" + cols1[2]);
				logger.debug("cols1[5]" + cols1[5]);
				logger.debug("cols1[6]" + cols1[6]);
				tlistTable.add(cols1);
			}
			String[] col = new String[13];
			xmlexport.addListTable(tlistTable, col);

			// xmlexport.outputDocumentToFile("d:\\", "PNoticeBill5");
			// //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);

			return true;

		}
		if (tUWStatType.equals("6")) {
			XmlExport xmlexport = new XmlExport(); // Create a XmlExport
													// instance
			xmlexport.createDocument("UWQuality.vts", "printer");
			String name = "";
			String sql = "select name from ldcom where comcode='" + "?strMngCom?"
					+ "'";
			SQLwithBindVariables sqlbv68 = new SQLwithBindVariables();
			sqlbv68.sql(sql);
			sqlbv68.put("strMngCom", strMngCom);
			ExeSQL exeSQL = new ExeSQL();
			SSRS ssrs = exeSQL.execSQL(sqlbv68);
			name = ssrs.GetText(1, 1);
			logger.debug("开始执行strOperation＝" + tUWStatType + "的sql操作");

			String strSQL = "";
			ExeSQL exeSQL1 = new ExeSQL();
			SSRS ssrs1 = new SSRS();
			// 对于每一个险种进行循环判断相关的取值
			for (int i = 0; i < allRiskCount; i++) {
				String[] tCount = new String[10];
				// 出具核保结论件数
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(distinct contno)" + " from lcpol"
						+ " where riskcode = '"
						+ "?riskcode?"
						+ "'"
						+ " and makedate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd')  and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')"
						+ " and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <> 17"
						+ " and ((appflag is not null and appflag <> '0') or uwflag = '1' or"
						+ " uwflag = '2')"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)" +
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)"
						+ " and exists (select 'X'"
						+ " from (select missionprop1, defaultoperator"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " union"
						+ " select missionprop1, defaultoperator"
						+ " from lwmission"
//						+ " where activityid = '0000001100')"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')) g"
						+ " where defaultoperator = '"
						+ "?defaultoperator?"
						+ "'"
						+ " and missionprop1 = lcpol.prtno)";
				SQLwithBindVariables sqlbv69 = new SQLwithBindVariables();
				sqlbv69.sql(strSQL);
				sqlbv69.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv69.put("strStartDate", strStartDate);
				sqlbv69.put("strEndDate", strEndDate);
				sqlbv69.put("strMngCom", strMngCom);
				sqlbv69.put("defaultoperator", allSSRS.GetText(i + 1, 6));
				ssrs1 = exeSQL1.execSQL(sqlbv69);
				tCount[0] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 人工核保件数
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(distinct contno)" + " from lcpol"
						+ " where riskcode = '"
						+ "?riskcode?"
						+ "'"
						+ " and makedate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd')  and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')"
						+ " and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <> 17"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)" +
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)"
						+ " and exists (select 'X'"
						+ " from (select missionprop1, defaultoperator"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " union"
						+ " select missionprop1, defaultoperator"
						+ " from lwmission"
//						+ " where activityid = '0000001100')"
						+ " where activityid in (select activityid from lwactivity  where functionid ='10010028')) g"
						+ " where defaultoperator = '"
						+ "?defaultoperator?"
						+ "'"
						+ " and missionprop1 = lcpol.prtno)";
				SQLwithBindVariables sqlbv70 = new SQLwithBindVariables();
				sqlbv70.sql(strSQL);
				sqlbv70.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv70.put("strStartDate", strStartDate);
				sqlbv70.put("strEndDate", strEndDate);
				sqlbv70.put("strMngCom", strMngCom);
				sqlbv70.put("defaultoperator", allSSRS.GetText(i + 1, 6));
				ssrs1 = exeSQL1.execSQL(sqlbv70);
				tCount[1] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 体检件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and exists (select 'X'"
						+ " from (select missionprop1, defaultoperator"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " union"
						+ " select missionprop1, defaultoperator"
						+ " from lwmission"
//						+ " where activityid = '0000001100')"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')) g"
						+ " where defaultoperator = '"
						+ "?defaultoperator?"
						+ "'"
						+ " and missionprop1 = lcpol.prtno)"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)" +
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)"
						+ " and exists"
						+ " (select 'X' from lcpenotice where lcpenotice.proposalcontno = lcpol.prtno)"
						+ " and polno=mainpolno";
				SQLwithBindVariables sqlbv71 = new SQLwithBindVariables();
				sqlbv71.sql(strSQL);
				sqlbv71.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv71.put("strStartDate", strStartDate);
				sqlbv71.put("strEndDate", strEndDate);
				sqlbv71.put("strMngCom", strMngCom);
				sqlbv71.put("defaultoperator", allSSRS.GetText(i + 1, 6));
				ssrs1 = exeSQL1.execSQL(sqlbv71);
				tCount[2] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 核保问题件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and exists (select 'X'"
						+ " from (select missionprop1, defaultoperator"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " union"
						+ " select missionprop1, defaultoperator"
						+ " from lwmission"
//						+ " where activityid = '0000001100')"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')) g"
						+ " where defaultoperator = '"
						+ "?defaultoperator?"
						+ "'"
						+ " and missionprop1 = lcpol.prtno)"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)" +
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)"
						+ " and exists (select 'X'"
						+ " from loprtmanager"
						+ " where loprtmanager.otherno = lcpol.contno"
						+ " and loprtmanager.code = '81')"
						+ " and polno=mainpolno";
				SQLwithBindVariables sqlbv72 = new SQLwithBindVariables();
				sqlbv72.sql(strSQL);
				sqlbv72.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv72.put("strStartDate", strStartDate);
				sqlbv72.put("strEndDate", strEndDate);
				sqlbv72.put("strMngCom", strMngCom);
				sqlbv72.put("defaultoperator", allSSRS.GetText(i + 1, 6));
				ssrs1 = exeSQL1.execSQL(sqlbv72);
				tCount[3] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 标准承保件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and appflag is not null and appflag <>'0'"
						+ " and exists (select 'X'"
						+ " from (select missionprop1, defaultoperator"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " union"
						+ " select missionprop1, defaultoperator"
						+ " from lwmission"
//						+ " where activityid = '0000001100')"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')) g"
						+ " where defaultoperator = '"
						+ "?defaultoperator?"
						+ "'"
						+ " and missionprop1 = lcpol.prtno)"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)" +
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)"
						+ " and exists (select 'X'"
						+ " from lccont"
						+ " where contno = lcpol.contno" + " and uwflag = '9')";
				SQLwithBindVariables sqlbv73 = new SQLwithBindVariables();
				sqlbv73.sql(strSQL);
				sqlbv73.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv73.put("strStartDate", strStartDate);
				sqlbv73.put("strEndDate", strEndDate);
				sqlbv73.put("strMngCom", strMngCom);
				sqlbv73.put("defaultoperator", allSSRS.GetText(i + 1, 6));
				ssrs1 = exeSQL1.execSQL(sqlbv73);
				tCount[4] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 加费件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and exists (select 'X'"
						+ " from (select missionprop1, defaultoperator"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " union"
						+ " select missionprop1, defaultoperator"
						+ " from lwmission"
//						+ " where activityid = '0000001100')"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')) g"
						+ " where defaultoperator = '"
						+ "?defaultoperator?"
						+ "'"
						+ " and missionprop1 = lcpol.prtno)"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)" +
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)"
						+ " and uwflag='3'"
						+ " and exists (select 'X'"
						+ " from lcprem"
						+ " where lcprem.polno=lcpol.polno"
						+ " and lcprem.payplantype in ('01') and lcprem.operator='"
						+ "?defaultoperator?"
						+ "'"
						+ " and managecom like concat('"
						+ "?strMngCom?"
						+ "','%'))";
				SQLwithBindVariables sqlbv74 = new SQLwithBindVariables();
				sqlbv74.sql(strSQL);
				sqlbv74.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv74.put("strStartDate", strStartDate);
				sqlbv74.put("strEndDate", strEndDate);
				sqlbv74.put("strMngCom", strMngCom);
				sqlbv74.put("defaultoperator", allSSRS.GetText(i + 1, 6));
				ssrs1 = exeSQL1.execSQL(sqlbv74);
				tCount[5] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 加费成功件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and (appflag is not null and appflag<>'0')"
						+ " and exists (select 'X'"
						+ " from (select missionprop1, defaultoperator"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " union"
						+ " select missionprop1, defaultoperator"
						+ " from lwmission"
//						+ " where activityid = '0000001100')"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')) g"
						+ " where defaultoperator = '"
						+ "?defaultoperator?"
						+ "'"
						+ " and missionprop1 = lcpol.prtno)"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)" +
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)"
						+ " and uwflag='3'"
						+ " and exists (select 'X'"
						+ " from lcprem"
						+ " where lcprem.polno=lcpol.polno"
						+ " and lcprem.payplantype in ('01') and lcprem.operator='"
						+ "?defaultoperator?"
						+ "'"
						+ " and managecom like concat('"
						+ "?strMngCom?"
						+ "','%'))";
				SQLwithBindVariables sqlbv75 = new SQLwithBindVariables();
				sqlbv75.sql(strSQL);
				sqlbv75.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv75.put("strStartDate", strStartDate);
				sqlbv75.put("strEndDate", strEndDate);
				sqlbv75.put("strMngCom", strMngCom);
				sqlbv75.put("defaultoperator", allSSRS.GetText(i + 1, 6));
				ssrs1 = exeSQL1.execSQL(sqlbv75);
				tCount[6] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 特别约定件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and exists (select 'X'"
						+ " from (select missionprop1, defaultoperator"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " union"
						+ " select missionprop1, defaultoperator"
						+ " from lwmission"
//						+ " where activityid = '0000001100')"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')) g"
						+ " where defaultoperator = '"
						+ "?defaultoperator?"
						+ "'"
						+ " and missionprop1 = lcpol.prtno)"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)" +
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)"
						+ " and uwflag='4'";
				SQLwithBindVariables sqlbv76 = new SQLwithBindVariables();
				sqlbv76.sql(strSQL);
				sqlbv76.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv76.put("strStartDate", strStartDate);
				sqlbv76.put("strEndDate", strEndDate);
				sqlbv76.put("strMngCom", strMngCom);
				sqlbv76.put("defaultoperator", allSSRS.GetText(i + 1, 6));
				ssrs1 = exeSQL1.execSQL(sqlbv76);
				tCount[7] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 拒保件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and exists (select 'X'"
						+ " from (select missionprop1, defaultoperator"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " union"
						+ " select missionprop1, defaultoperator"
						+ " from lwmission"
//						+ " where activityid = '0000001100')"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')) g"
						+ " where defaultoperator = '"
						+ "?defaultoperator?"
						+ "'"
						+ " and missionprop1 = lcpol.prtno)"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)" +
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)"
						+ " and uwflag='1'";
				SQLwithBindVariables sqlbv77 = new SQLwithBindVariables();
				sqlbv77.sql(strSQL);
				sqlbv77.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv77.put("strStartDate", strStartDate);
				sqlbv77.put("strEndDate", strEndDate);
				sqlbv77.put("strMngCom", strMngCom);
				sqlbv77.put("defaultoperator", allSSRS.GetText(i + 1, 6));
				ssrs1 = exeSQL1.execSQL(sqlbv77);
				tCount[8] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 延期件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and exists (select 'X'"
						+ " from (select missionprop1, defaultoperator"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " union"
						+ " select missionprop1, defaultoperator"
						+ " from lwmission"
//						+ " where activityid = '0000001100')"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')) g"
						+ " where defaultoperator = '"
						+ "?defaultoperator?"
						+ "'"
						+ " and missionprop1 = lcpol.prtno)"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)" +
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)"
						+ " and uwflag='2'";
				SQLwithBindVariables sqlbv78 = new SQLwithBindVariables();
				sqlbv78.sql(strSQL);
				sqlbv78.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv78.put("strStartDate", strStartDate);
				sqlbv78.put("strEndDate", strEndDate);
				sqlbv78.put("strMngCom", strMngCom);
				sqlbv78.put("defaultoperator", allSSRS.GetText(i + 1, 6));
				ssrs1 = exeSQL1.execSQL(sqlbv78);
				tCount[9] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				String[] cols1 = new String[18];
				cols1[0] = strMngCom;
				cols1[1] = name;
				cols1[2] = allSSRS.GetText(i + 1, 5);
				cols1[3] = allSSRS.GetText(i + 1, 1);
				cols1[4] = allSSRS.GetText(i + 1, 2);
				cols1[5] = allSSRS.GetText(i + 1, 3);
				cols1[6] = allSSRS.GetText(i + 1, 4);
				// cols1[7] = tCount[0];
				cols1[8] = tCount[0];
				cols1[9] = tCount[1];
				cols1[10] = tCount[2];
				cols1[11] = tCount[3];
				cols1[12] = tCount[4];
				cols1[13] = tCount[5];
				cols1[14] = tCount[6];
				cols1[15] = tCount[7];
				cols1[16] = tCount[8];
				cols1[17] = tCount[9];

				logger.debug("cols1[0]" + cols1[0]);
				logger.debug("cols1[1]" + cols1[1]);
				logger.debug("cols1[2]" + cols1[2]);
				tlistTable.add(cols1);

			}

			String[] col2 = new String[18];
			xmlexport.addListTable(tlistTable, col2);

			// xmlexport.outputDocumentToFile("e:\\", "PNoticeBill6");
			// //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);
			return true;

		}

		if (tUWStatType.equals("7")) {
			XmlExport xmlexport = new XmlExport(); // Create a XmlExport
													// instance
			xmlexport.createDocument("cxRate.vts", "printer");
			String name = "";
			String sql = "select name from ldcom where comcode='" + "?strMngCom?"
					+ "'";
			SQLwithBindVariables sqlbv79 = new SQLwithBindVariables();
			sqlbv79.sql(sql);
			sqlbv79.put("strMngCom", strMngCom);
			ExeSQL exeSQL = new ExeSQL();
			SSRS ssrs = exeSQL.execSQL(sqlbv79);
			name = ssrs.GetText(1, 1);
			logger.debug("开始执行strOperation＝" + tUWStatType + "的sql操作");

			String strSQL = "";
			ExeSQL exeSQL1 = new ExeSQL();
			SSRS ssrs1 = new SSRS();
			// 对于每一个险种进行循环判断相关的取值
			for (int i = 0; i < allRiskCount; i++) {
				String[] tCount = new String[10];
				// 有效承保件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or Months_between(paytodate, cvalidate) >= 10000 or paytodate is null)"
						+ " and polno=mainpolno"
						+
						// " and not exists (select 'X'" +
						// " from lpedoritem, lppol" +
						// " where lpedoritem.contno = lcpol.contno" +
						// " and lpedoritem.edortype = 'NS'" +
						// " and lppol.edorno = lpedoritem.edorno" +
						// " and lppol.polno = lcpol.polno)" +
						" and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = lcpol.prtno"
						+ " and lccont.cvalidate = lcpol.cvalidate)"
						+ " and Months_between('"
						+ "?strEndDate?"
						+ "',cvalidate) <= 24 ";
				SQLwithBindVariables sqlbv80 = new SQLwithBindVariables();
				sqlbv80.sql(strSQL);
				sqlbv80.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv80.put("strEndDate", strEndDate);
				sqlbv80.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv80);
				tCount[0] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 出险件数
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(distinct LLClaimPolicy.Clmno)"
						+ " from llregister, LLClaimUWMain, LLClaimPolicy"
						+ " where llregister.rgtno = LLClaimUWMain.Rgtno"
						+ " and LLClaimUWMain.Examdate between to_date('"
						+ "?strStartDate?" + "','yyyy-mm-dd') and to_date('" + "?strEndDate?" + "','yyyy-mm-dd') "
						+ " and llregister.clmstate in ('50', '60')"
						+ " and LLClaimPolicy.Clmno = llregister.rgtno"
						+ " and LLClaimPolicy.Riskcode = '"
						+ "?riskcode?" + "'"
						+ " and LLClaimPolicy.Givetype in ('0', '1')"
						+ " and LLClaimUWMain.Examconclusion='0'"
						+ " and exists" + " (select 'X'" + " from lcpol"
						+ " where lcpol.polno = LLClaimPolicy.Polno"
						+ " and Months_between(to_date('" + "?strEndDate?" + "','yyyy-mm-dd'),lcpol.cvalidate) <= 24)";
				SQLwithBindVariables sqlbv81 = new SQLwithBindVariables();
				sqlbv81.sql(strSQL);
				sqlbv81.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv81.put("strStartDate", strStartDate);
				sqlbv81.put("strEndDate", strEndDate);
				ssrs1 = exeSQL1.execSQL(sqlbv81);
				tCount[1] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 给付件数
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(distinct LLClaimPolicy.Clmno)"
						+ " from llregister, LLClaimUWMain, LLClaimPolicy"
						+ " where llregister.rgtno = LLClaimUWMain.Rgtno"
						+ " and LLClaimUWMain.Examdate between to_date('"
						+ "?strStartDate?" + "','yyyy-mm-dd')" + " and to_date('" + "?strEndDate?" + "','yyyy-mm-dd')"
						+ " and llregister.clmstate in ('50', '60')"
						+ " and LLClaimPolicy.Clmno = llregister.rgtno"
						+ " and LLClaimPolicy.Riskcode = '"
						+ "?riskcode?" + "'"
						+ " and LLClaimPolicy.Givetype = '0'"
						+ " and LLClaimUWMain.Examconclusion='0'"
						+ " and exists" + " (select 'X'" + " from lcpol"
						+ " where lcpol.polno = LLClaimPolicy.Polno"
						+ " and Months_between(to_date('" + "?strEndDate?" + "','yyyy-mm-dd'),lcpol.cvalidate) <= 24)";
				SQLwithBindVariables sqlbv82 = new SQLwithBindVariables();
				sqlbv82.sql(strSQL);
				sqlbv82.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv82.put("strStartDate", strStartDate);
				sqlbv82.put("strEndDate", strEndDate);
				ssrs1 = exeSQL1.execSQL(sqlbv82);
				tCount[2] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 拒付件数
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(distinct LLClaimPolicy.Clmno)"
						+ " from llregister, LLClaimUWMain, LLClaimPolicy"
						+ " where llregister.rgtno = LLClaimUWMain.Rgtno"
						+ " and LLClaimUWMain.Examdate between to_date('"
						+ "?strStartDate?" + "','yyyy-mm-dd')" + " and to_date('" + "?strEndDate?" + "','yyyy-mm-dd')"
						+ " and llregister.clmstate in ('50', '60')"
						+ " and LLClaimPolicy.Clmno = llregister.rgtno"
						+ " and LLClaimPolicy.Riskcode = '"
						+ "?riskcode?" + "'"
						+ " and LLClaimPolicy.Givetype = '1'"
						+ " and LLClaimUWMain.Examconclusion='0'"
						+ " and exists" + " (select 'X'" + " from lcpol"
						+ " where lcpol.polno = LLClaimPolicy.Polno"
						+ " and Months_between(to_date('" + "?strEndDate?" + "','yyyy-mm-dd'),lcpol.cvalidate) <= 24)";
				SQLwithBindVariables sqlbv83 = new SQLwithBindVariables();
				sqlbv83.sql(strSQL);
				sqlbv83.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv83.put("strStartDate", strStartDate);
				sqlbv83.put("strEndDate", strEndDate);
				ssrs1 = exeSQL1.execSQL(sqlbv83);
				tCount[3] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 长险出险率
				// //////////////////////////////////////////////////////////////////
				// strSQL =
				// "";
				// ssrs1 = exeSQL1.execSQL(strSQL);
				// tCount[4] = ssrs1.GetText(1, 1);
				DecimalFormat tDecimalFormat = new DecimalFormat("0.0000");
				tCount[4] = tDecimalFormat
						.format((Double.parseDouble(tCount[1]) / Double
								.parseDouble(tCount[0])) * 100)
						+ "%";
				// //////////////////////////////////////////////////////////////////
				String[] cols1 = new String[11];
				cols1[0] = strMngCom;
				cols1[1] = name;
				cols1[2] = allSSRS.GetText(i + 1, 1);
				cols1[3] = allSSRS.GetText(i + 1, 2);
				cols1[4] = allSSRS.GetText(i + 1, 3);
				cols1[5] = allSSRS.GetText(i + 1, 4);
				cols1[6] = tCount[0];
				cols1[7] = tCount[1];
				cols1[8] = tCount[2];
				cols1[9] = tCount[3];
				cols1[10] = tCount[4];

				logger.debug("cols1[0]" + cols1[0]);
				logger.debug("cols1[1]" + cols1[1]);
				logger.debug("cols1[2]" + cols1[2]);
				tlistTable.add(cols1);
			}
			String[] col = new String[11];
			xmlexport.addListTable(tlistTable, col);

			// xmlexport.outputDocumentToFile("e:\\", "PNoticeBill7");
			// //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);
			return true;

		}

		return true;
	}

	public CErrors getErrors() {
		return null;
	}

}
