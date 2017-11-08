package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLRepColligateSchema;
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
 * @author fuyu function :print InputEfficiency or print CheckEfficiency
 *         Business Logic layer
 * @version 1.0
 * @date 2003-04-04
 */

public class UWStaticPrintSEBL implements BqBill {
private static Logger logger = Logger.getLogger(UWStaticPrintSEBL.class);
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/*
	 * @define globe variable
	 */
	private String strMngCom;

	private String strStartDate;

	private String strEndDate;

	private String tUWStatType;

	private String mCurrentDate = PubFun.getCurrentDate();

	private String mCurrentTime = PubFun.getCurrentTime();

	private GlobalInput mGlobalInput = new GlobalInput();

	private MMap mMMap = new MMap();

	private VData mInputData = new VData();

	private TransferData mTransferData = new TransferData();

	private String mOperator = "";

	public UWStaticPrintSEBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {

		if (!cOperate.equals("PRINT") && !cOperate.equals("SAVE")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		if (!getInputData(cInputData)) {
			return false;
		}
		mResult.clear();

		if (!getPrintData(cOperate)) {
			return false;
		}
		if (!prepareOutputData()) {
			return false;
		}
		if (!pubSubmit()) {
			return false;
		}
		return true;
	}

	/**
	 * ************************************************************************
	 * Name : getInputData() function :receive data from jsp and check date
	 * check :judge whether startdate begin enddate return :true or false
	 */
	private boolean getInputData(VData cInputData) {
		// strMngCom = (String) cInputData.get(0);
		// strStartDate = (String) cInputData.get(1);
		// strEndDate = (String) cInputData.get(2);
		// tUWStatType = (String) cInputData.get(3);
		// mOperator = (String) cInputData.get(4);

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
		mOperator = (String) tTD.getValueByName("Operator");

		logger.debug("strStartDate:" + strStartDate);
		logger.debug("mOperator:" + mOperator);
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	/**
	 * ************************************************************************
	 * name : buildError function : Prompt error message return :error message
	 */

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "UWStaticPrintSEBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * ************************************************************************
	 * name : getPrintData() function :get print data parameter : null return
	 * :true or false
	 */

	private boolean getPrintData(String tOperate) {
		ListTable tlistTable = new ListTable();
		tlistTable.setName("Work");
		String tSQL = " select distinct a.riskcode,"
				+ " (select lmrisk.riskshortname"
				+ " from lmrisk"
				+ " where lmrisk.riskcode = a.riskcode),"
				+ " case a.subriskflag"
				+ " when 'M' then"
				+ " '主险'"
				+ " when 'S' then"
				+ " '附加险'"
				+ " end,"
				+ " case (select lmrisksort.risksortvalue"
				+ " from lmrisksort"
				+ " where lmrisksort.riskcode = a.riskcode"
				+ " and lmrisksort.risksorttype = '3')"
				+ " when '1' then"
				+ " '寿险'"
				+ " when '2' then"
				+ " '重疾'"
				+ " when '3' then"
				+ " '短期健康险'"
				+ " when '4' then"
				+ " '意外伤害'"
				+ " when '5' then"
				+ " '投连'"
				+ " when '6' then"
				+ " '年金'"
				+ " when '7' then"
				+ " '卡式'"
				+ " when '0' then"
				+ " '无分类'"
				+ " end"
				+ " from lmriskapp a"
				+ " where riskprop = 'I'"
				// + " and (a.enddate is not null and
				// a.enddate>=add_months(to_date(sysdate),-6))"
				+ "  and ((a.enddate is not null and a.enddate>=add_months(to_date(now(),'yyyy-mm-dd hh24:mi:ss'),-6)) or a.enddate is null)"
				+ " order by a.riskcode";

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
					+ " where lmrisk.riskcode = a.riskcode) as ac,"
					+ " case (select lmriskapp.subriskflag" + " from lmriskapp"
					+ " where lmriskapp.riskcode = a.riskcode)"
					+ " when 'M' then" + " '主险'" + " when 'S' then" + " '附加险'"
					+ " end as ad," + " case (select lmrisksort.risksortvalue"
					+ " from lmrisksort"
					+ " where lmrisksort.riskcode = a.riskcode"
					+ " and lmrisksort.risksorttype = '3')" + " when '1' then"
					+ " '寿险'" + " when '2' then" + " '重疾'" + " when '3' then"
					+ " '短期健康险'" + " when '4' then" + " '意外伤害'"
					+ " when '5' then" + " '投连'" + " when '6' then" + " '年金'"
					+ " when '7' then" + " '卡式'" + " when '0' then" + " '无分类'"
					+ " end as ae" + " from lcpol a"
					+ " where a.managecom like concat('"
					+ "?strMngCom?"
					+ "','%')"
					+ " and a.salechnl = '1'"
					+ " ) tempa,"
					+ " (select missionprop1 as b1, defaultoperator as b2"
					+ " from lbmission"
//					+ " where activityid = '0000001100'"
					+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
					+ " union"
					+ " select missionprop1, defaultoperator"
					+ " from lwmission"
//					+ " where activityid = '0000001100'"
					+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
					+ " and defaultoperator is not null) tempb"
					+ " where trim(tempa.aa) = trim(tempb.b1)"
					+ " and tempb.b2 is not null"
					+ " and exists (select 'X' from lduwuser where usercode = tempb.b2)"
					+ " and exists (select 'X'"
					+ " from lduser"
					+ " where usercode = tempb.b2"
					+ " and comcode like concat('"
					+ "?strMngCom?"
					+ "','%'))" + " order by b2, ab"

			;
		}
		// 如果是长险前两年出险率则用下面的SQL
		if (tUWStatType.equals("7")) {
			/*
			 * tSQL = " select distinct a.riskcode," + " (select
			 * lmrisk.riskshortname" + " from lmrisk" + " where lmrisk.riskcode =
			 * a.riskcode)," + " case (select lmriskapp.subriskflag" + " from
			 * lmriskapp" + " where lmriskapp.riskcode = a.riskcode " + " and
			 * lmriskapp.subriskflag='M')" + " when 'M' then" + " '主险'" + " when
			 * 'S' then" + " '附加险'" + " end," + " case (select
			 * lmrisksort.risksortvalue" + " from lmrisksort" + " where
			 * lmrisksort.riskcode = a.riskcode" + " and risksortvalue in
			 * ('1','2','6')" + " and lmrisksort.risksorttype = '3')" + " when
			 * '1' then" + " '寿险'" + " when '2' then" + " '重疾'" + " when '3'
			 * then" + " '短期健康险'" + " when '4' then" + " '意外伤害'" + " when '5'
			 * then" + " '投连'" + " when '6' then" + " '年金'" + " when '7' then" + "
			 * '卡式'" + " when '0' then" + " '无分类'" + " end" + " from lcpol a" + "
			 * where a.managecom like '" + strMngCom + "%'" + " and
			 * Months_between('" + strEndDate + "',a.cvalidate) <=24" + " and
			 * a.salechnl='1'" + " and (Months_between(a.paytodate, a.cvalidate) <=
			 * 12 or payintv = 0 or paytodate is null)" + " and exists (select
			 * 'x'" + " from lccont" + " where lccont.prtno = a.prtno" + " and
			 * lccont.cvalidate = a.cvalidate)" + " and a.uwcode is not null" + "
			 * and exists (select 'x'" + " from lmriskapp" + " where riskcode =
			 * a.riskcode" + " and subriskflag = 'M')" + " and exists (select
			 * 'x'" + " from lmrisksort" + " where riskcode = a.riskcode" + "
			 * and lmrisksort.risksorttype = '3'" + " and risksortvalue in ('1',
			 * '2', '6'))" + " order by a.riskcode";
			 */

			tSQL = "select distinct a.riskcode,\n"
					+ "                (select lmrisk.riskshortname\n"
					+ "                   from lmrisk\n"
					+ "                  where lmrisk.riskcode = a.riskcode),\n"
					+ "                case a.subriskflag\n"
					+ "                  when 'M' then\n"
					+ "                   '主险'\n"
					+ "                  when 'S' then\n"
					+ "                   '附加险'\n" + "                end,\n"
					+ "                case b.risksortvalue\n"
					+ "                  when '1' then\n"
					+ "                   '寿险'\n"
					+ "                  when '2' then\n"
					+ "                   '重疾'\n"
					+ "                  when '3' then\n"
					+ "                   '短期健康险'\n"
					+ "                  when '4' then\n"
					+ "                   '意外伤害'\n"
					+ "                  when '5' then\n"
					+ "                   '投连'\n"
					+ "                  when '6' then\n"
					+ "                   '年金'\n"
					+ "                  when '7' then\n"
					+ "                   '卡式'\n"
					+ "                  when '0' then\n"
					+ "                   '无分类'\n" + "                end\n"
					+ "  from lmriskapp a, lmrisksort b\n" + " where 1 = 1\n"
					+ "   and a.subriskflag = 'M'\n"
					+ "   and b.riskcode = a.riskcode\n"
					+ "   and b.risksorttype = '3'\n"
					+ "   and b.risksortvalue in ('1', '2', '6')\n"
					+ "   and riskprop<>'G'\n" + " order by a.riskcode";
		}
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSQL);
		sqlbv1.put("strMngCom", strMngCom);
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
				strSQL = " select count(distinct contno)"
						+ " from lcpol"
						+ " where riskcode = '"
						+ "?riskcode?"
						+ "'"
						+ " and makedate between '"
						+ "?strStartDate?"
						+ "' and "
						+ "'"
						+ "?strEndDate?"
						+ "'"
						+ " and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <> 17"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
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
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv=0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and ((signdate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd')  and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd') ) or"
						+ " (uwflag in ('1', '2') and uwdate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd') "
						+ " ))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
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
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv=0 or paytodate is null)"
						+ " and signdate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')  "
						+ " and not exists"
						+ "  (select 'X'"
						+ "  from lbmission"
//						+ "  where activityid = '0000001100'"
						+ "  where activityid in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and missionprop1 ="
						+ "  trim(lcpol.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
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
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv=0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and prtno in (select rpad(missionprop1, lislen('lcpol', 'contno'), ' ')"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and missionprop10 like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " union"
						+ " select rpad(missionprop1, lislen('lcpol', 'contno'), ' ')"
						+ " from lwmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and missionprop10  like concat('"
						+ "?strMngCom?"
						+ "','%'))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
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
						+ "' and signdate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and char_length(trim(contno)) <>17"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv=0 or paytodate is null)"
						+ " and (appflag is not null and appflag <> '0') "
						+ " and uwflag = '9' "
						+ " and exists"
						+ "  (select 'X'"
						+ "  from lbmission"
//						+ "  where activityid = '0000001100'"
						+ "  where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and missionprop1 ="
						+ "  trim(lcpol.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
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
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and signdate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' "
						+ " and uwflag<>'1' and uwflag<>'2'"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
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
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and signdate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' "
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))"
						+ " and exists (select 'X'"
						+ " from lcprem"
						+ " where lcprem.polno=lcpol.polno"
						+ " and lcprem.payplantype in ('01','02')"
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
						+ "' and uwdate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and char_length(trim(contno)) <>17"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and uwflag = '1' "
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
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
						+ "' and uwdate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and uwflag = '2' "
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
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
						+ "' and uwdate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and (uwflag = 'a' "
						+ " or exists(select 'X' from lccont where contno=lcpol.contno and uwflag='a'))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
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
						+ "' and signdate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and uwflag = '4' "
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
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
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and polno = mainpolno"
						+ " and exists (select 'X' from lcrreport where lcrreport.proposalcontno = lcpol.prtno and lcrreport.replydate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "')"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
				SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
				sqlbv14.sql(strSQL);
				sqlbv14.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv14.put("strStartDate", strStartDate);
				sqlbv14.put("strEndDate", strEndDate);
				sqlbv14.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv14);
				tCount[11] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				/**
				 * ============================================================================
				 * 修改原因：由于查询数据比较多，打印非常慢，所以，现将需要打印的数据查出存储在一个一个临时表
				 * 中，然后再去临时表中去数据打印。可以在存储数据的同时做其他 的事情 修改人： 万泽辉 修改时间： 2005/12/22
				 * 16:00
				 * =============================================================================
				 */
				// 准备需要暂存在 llrepcolligate表中的数据
				if (tOperate != null && tOperate.equals("SAVE")) {
					LLRepColligateSchema tLLRepColligateSchema = new LLRepColligateSchema();
					String tRepID = "UWREPORTSE100" + String.valueOf(i);
					if (tRepID == null || tRepID.equals("")) {
						buildError("tRepID", "生成[承保状况统计]编号失败！");
						return false;
					}
					String mStartDate = strStartDate.substring(0, 4)
							+ strStartDate.substring(5, 7)
							+ strStartDate.substring(8, 10);
					String mEndDate = strEndDate.substring(0, 4)
							+ strEndDate.substring(5, 7)
							+ strEndDate.substring(8, 10);

					String tRepName = "承保状况统计";
					String tColID = String.valueOf(i);
					String tColName = "";
					tLLRepColligateSchema.setRepID(tRepID); // 报表编号
					tLLRepColligateSchema.setRepName(tRepName); // 报表名称
					tLLRepColligateSchema.setColID(tColID); // 栏目号
					tLLRepColligateSchema.setColName(tColName); // 栏目名称
					tLLRepColligateSchema.setEndDate(mEndDate); // 结束时间
					tLLRepColligateSchema.setStartDate(mStartDate); // 开始时间
					tLLRepColligateSchema.setMakeDate(mCurrentDate); // 最后一次修改日
					tLLRepColligateSchema.setMakeTime(mCurrentTime);
					tLLRepColligateSchema.setModifyDate(mCurrentDate);
					tLLRepColligateSchema.setModifyTime(mCurrentTime); // 最后一次修改时间
					tLLRepColligateSchema.setOperator(mOperator); // 操作人
					tLLRepColligateSchema.setMngCom(strMngCom); // 管理机构代码
					tLLRepColligateSchema.setMngComName(name); // 管理机构名称
					tLLRepColligateSchema.setC1(allSSRS.GetText(i + 1, 1)); // 险种编码
					tLLRepColligateSchema.setC2(allSSRS.GetText(i + 1, 2)); // 险种名称
					tLLRepColligateSchema.setC3(allSSRS.GetText(i + 1, 3)); // 主险或者附加险
					tLLRepColligateSchema.setC4(allSSRS.GetText(i + 1, 4)); // 险种分类值
					tLLRepColligateSchema.setC5(tCount[0]); // 查询预收录入件数
					tLLRepColligateSchema.setC6(tCount[1]); // 查询出具核保结论件数
					tLLRepColligateSchema.setC7(tCount[2]); // 电脑核保通过件数
					tLLRepColligateSchema.setC8(tCount[3]); // 人工核保件数
					tLLRepColligateSchema.setC9(tCount[4]); // 人工核保标准承保件数
					tLLRepColligateSchema.setC10(tCount[5]); // 承保件数
					tLLRepColligateSchema.setC11(tCount[6]); // 加费成功件数
					tLLRepColligateSchema.setC12(tCount[7]); // 拒保件数
					tLLRepColligateSchema.setC13(tCount[8]); // 延期件数
					tLLRepColligateSchema.setC14(tCount[9]); // 撤保件数
					tLLRepColligateSchema.setC15(tCount[10]); // 特别约定件数
					tLLRepColligateSchema.setC16(tCount[11]); // 生调件数
					mMMap.put(tLLRepColligateSchema, "DELETE&INSERT");

				}
				if (tOperate != null && tOperate.equals("PRINT")) {
					String mStartDate = strStartDate.substring(0, 4)
							+ strStartDate.substring(5, 7)
							+ strStartDate.substring(8, 10);
					String mEndDate = strEndDate.substring(0, 4)
							+ strEndDate.substring(5, 7)
							+ strEndDate.substring(8, 10);

					String tempSQL = "select mngcom, mngcomname,"
							+ " c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,"
							+ " c12,c13,c14,c15,c16 "
							+ " from llrepcolligate where 1=1 and "
							+ " repid like 'UWREPORTSE1%' and repname = '承保状况统计' and "
							+ " mngcom = '" + "?strMngCom?" + "' and " // 管理机构代码
							+ " startdate = '" + "?mStartDate?" + "' and " // 开始时间
							+ " enddate = '" + "?mEndDate?" + "' " // 结束时间
					;
					SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
					sqlbv15.sql(tempSQL);
					sqlbv15.put("strMngCom", strMngCom);
					sqlbv15.put("mStartDate", mStartDate);
					sqlbv15.put("mEndDate", mEndDate);
					
					ExeSQL tempExeSQL = new ExeSQL();
					SSRS temmSSRS = new SSRS();
					temmSSRS = tempExeSQL.execSQL(sqlbv15);

					if (temmSSRS.getMaxRow() == 0) {
						buildError("tLLRepColligateSchema", "没有得到打印信息！");
						return false;
					}
					for (int n = 1; n <= temmSSRS.getMaxRow(); n++) {
						String[] cols1 = new String[18];
						cols1[0] = temmSSRS.GetText(n, 1);
						cols1[1] = temmSSRS.GetText(n, 2);
						cols1[2] = temmSSRS.GetText(n, 3);
						cols1[3] = temmSSRS.GetText(n, 4);
						cols1[4] = temmSSRS.GetText(n, 5);
						cols1[5] = temmSSRS.GetText(n, 6);
						cols1[6] = temmSSRS.GetText(n, 7);
						cols1[7] = temmSSRS.GetText(n, 8);
						cols1[8] = temmSSRS.GetText(n, 9);
						cols1[9] = temmSSRS.GetText(n, 10);
						cols1[10] = temmSSRS.GetText(n, 11);
						cols1[11] = temmSSRS.GetText(n, 12);
						cols1[12] = temmSSRS.GetText(n, 13);
						cols1[13] = temmSSRS.GetText(n, 14);
						cols1[14] = temmSSRS.GetText(n, 15);
						cols1[15] = temmSSRS.GetText(n, 16);
						cols1[16] = temmSSRS.GetText(n, 17);
						cols1[17] = temmSSRS.GetText(n, 18);

						tlistTable.add(cols1);

					}
					String[] col = new String[1];
					xmlexport.addListTable(tlistTable, col);
					mResult.clear();
					mResult.addElement(xmlexport);
					return true;
				}

			}
		}
		if (tUWStatType.equals("2")) {
			XmlExport xmlexport = new XmlExport(); // Create a XmlExport
			// instance
			xmlexport.createDocument("befbstatic.vts", "printer");

			String name = "";
			String sql = "select name from ldcom where comcode='" + "?strMngCom?"
					+ "'";
			SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
			sqlbv16.sql(sql);
			sqlbv16.put("strMngCom",strMngCom);
			ExeSQL exeSQL = new ExeSQL();
			SSRS ssrs = exeSQL.execSQL(sqlbv16);
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
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
				SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
				sqlbv17.sql(strSQL);
				sqlbv17.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv17.put("strStartDate", strStartDate);
				sqlbv17.put("strEndDate", strEndDate);
				sqlbv17.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv17);
				tCount[0] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 查询出具核保结论件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and ((signdate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd') ) or"
						+ " (uwflag in ('1', '2') and uwdate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')"
						+ " ))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
				SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
				sqlbv18.sql(strSQL);
				sqlbv18.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv18.put("strStartDate", strStartDate);
				sqlbv18.put("strEndDate", strEndDate);
				sqlbv18.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv18);
				tCount[1] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// ≤2万
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and amnt <= 20000"
						+ " and ((signdate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')) or"
						+ " (uwflag in ('1', '2') and uwdate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
				SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
				sqlbv19.sql(strSQL);
				sqlbv19.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv19.put("strStartDate", strStartDate);
				sqlbv19.put("strEndDate", strEndDate);
				sqlbv19.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv19);
				tCount[2] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 20001-5万
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and amnt>20000 and amnt<=50000"
						+ " and ((signdate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')) or"
						+ " (uwflag in ('1', '2') and uwdate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
				SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
				sqlbv20.sql(strSQL);
				sqlbv20.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv20.put("strStartDate", strStartDate);
				sqlbv20.put("strEndDate", strEndDate);
				sqlbv20.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv20);
				tCount[3] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 50001-10万
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and amnt>50000 and amnt<=100000"
						+ " and ((signdate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd') ) or"
						+ " (uwflag in ('1', '2') and uwdate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
				SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
				sqlbv21.sql(strSQL);
				sqlbv21.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv21.put("strStartDate", strStartDate);
				sqlbv21.put("strEndDate", strEndDate);
				sqlbv21.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv21);
				tCount[4] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 100001-20万
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and amnt>100000 and amnt<=200000"
						+ " and ((signdate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')) or"
						+ " (uwflag in ('1', '2') and uwdate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
				SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
				sqlbv22.sql(strSQL);
				sqlbv22.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv22.put("strStartDate", strStartDate);
				sqlbv22.put("strEndDate", strEndDate);
				sqlbv22.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv22);
				tCount[5] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 200001-30万
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and amnt>200000 and amnt<=300000"
						+ " and ((signdate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')) or"
						+ " (uwflag in ('1', '2') and uwdate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
				SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
				sqlbv23.sql(strSQL);
				sqlbv23.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv23.put("strStartDate", strStartDate);
				sqlbv23.put("strEndDate", strEndDate);
				sqlbv23.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv23);
				tCount[6] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// ＞30万
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and amnt>300000"
						+ " and ((signdate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')) or"
						+ " (uwflag in ('1', '2') and uwdate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
				SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
				sqlbv24.sql(strSQL);
				sqlbv24.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv24.put("strStartDate", strStartDate);
				sqlbv24.put("strEndDate", strEndDate);
				sqlbv24.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv24);
				tCount[7] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 承保件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and signdate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' "
						+ " and uwflag<>'1' and uwflag<>'2'"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
				SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
				sqlbv25.sql(strSQL);
				sqlbv25.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv25.put("strStartDate", strStartDate);
				sqlbv25.put("strEndDate", strEndDate);
				sqlbv25.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv25);
				tCount[8] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// ≤2万
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and signdate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' "
						+ " and uwflag<>'1' and uwflag<>'2'"
						+ " and amnt <= 20000"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
				SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
				sqlbv26.sql(strSQL);
				sqlbv26.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv26.put("strStartDate", strStartDate);
				sqlbv26.put("strEndDate", strEndDate);
				sqlbv26.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv26);
				tCount[9] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 20001-5万
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and signdate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' "
						+ " and uwflag<>'1' and uwflag<>'2'"
						+ " and amnt > 20000 and amnt <= 50000"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
				SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
				sqlbv27.sql(strSQL);
				sqlbv27.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv27.put("strStartDate", strStartDate);
				sqlbv27.put("strEndDate", strEndDate);
				sqlbv27.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv27);
				tCount[10] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 50001-10万
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and signdate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' "
						+ " and uwflag<>'1' and uwflag<>'2'"
						+ " and amnt > 50000 and amnt <= 100000"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
				SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
				sqlbv28.sql(strSQL);
				sqlbv28.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv28.put("strStartDate", strStartDate);
				sqlbv28.put("strEndDate", strEndDate);
				sqlbv28.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv28);
				tCount[11] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 100001-20万
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and signdate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' "
						+ " and uwflag<>'1' and uwflag<>'2'"
						+ " and amnt > 100000 and amnt <= 200000"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
				SQLwithBindVariables sqlbv29 = new SQLwithBindVariables();
				sqlbv29.sql(strSQL);
				sqlbv29.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv29.put("strStartDate", strStartDate);
				sqlbv29.put("strEndDate", strEndDate);
				sqlbv29.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv29);
				tCount[12] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 200001-30万
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and signdate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' "
						+ " and uwflag<>'1' and uwflag<>'2'"
						+ " and amnt > 200000 and amnt <= 300000"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
				SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
				sqlbv30.sql(strSQL);
				sqlbv30.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv30.put("strStartDate", strStartDate);
				sqlbv30.put("strEndDate", strEndDate);
				sqlbv30.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv30);
				tCount[13] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// ＞30万
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and signdate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' "
						+ " and uwflag<>'1' and uwflag<>'2'"
						+ " and amnt > 300000"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
				SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
				sqlbv31.sql(strSQL);
				sqlbv31.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv31.put("strStartDate", strStartDate);
				sqlbv31.put("strEndDate", strEndDate);
				sqlbv31.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv31);
				tCount[14] = ssrs1.GetText(1, 1);

				/**
				 * ============================================================================
				 * 修改原因：由于查询数据比较多，打印非常慢，所以，现将需要打印的数据查出存储在一个一个临时表
				 * 中，然后再去临时表中去数据打印。可以在存储数据的同时做其他 的事情 修改人： 万泽辉 修改时间： 2005/12/22
				 * 16:00
				 * =============================================================================
				 */
				// 准备需要暂存在 llrepcolligate表中的数据
				if (tOperate != null && tOperate.equals("SAVE")) {
					LLRepColligateSchema tLLRepColligateSchema = new LLRepColligateSchema();
					String tRepID = "UWREPORTSE200" + String.valueOf(i);
					if (tRepID == null || tRepID.equals("")) {
						buildError("tRepID", "生成[保额分布统计]编号失败！");
						return false;
					}
					String mStartDate = strStartDate.substring(0, 4)
							+ strStartDate.substring(5, 7)
							+ strStartDate.substring(8, 10);
					String mEndDate = strEndDate.substring(0, 4)
							+ strEndDate.substring(5, 7)
							+ strEndDate.substring(8, 10);

					String tRepName = "保额分布统计";
					String tColID = String.valueOf(i);
					String tColName = "";
					tLLRepColligateSchema.setRepID(tRepID); // 报表编号
					tLLRepColligateSchema.setRepName(tRepName); // 报表名称
					tLLRepColligateSchema.setColID(tColID); // 栏目号
					tLLRepColligateSchema.setColName(tColName); // 栏目名称
					tLLRepColligateSchema.setEndDate(mEndDate); // 结束时间
					tLLRepColligateSchema.setStartDate(mStartDate); // 开始时间
					tLLRepColligateSchema.setMakeDate(mCurrentDate); // 最后一次修改日
					tLLRepColligateSchema.setMakeTime(mCurrentTime);
					tLLRepColligateSchema.setModifyDate(mCurrentDate);
					tLLRepColligateSchema.setModifyTime(mCurrentTime); // 最后一次修改时间
					tLLRepColligateSchema.setOperator(mOperator); // 操作人
					tLLRepColligateSchema.setMngCom(strMngCom); // 管理机构代码
					tLLRepColligateSchema.setMngComName(name); // 管理机构名称
					tLLRepColligateSchema.setC1(allSSRS.GetText(i + 1, 1)); // 险种编码
					tLLRepColligateSchema.setC2(allSSRS.GetText(i + 1, 2)); // 险种名称
					tLLRepColligateSchema.setC3(allSSRS.GetText(i + 1, 3)); // 主险或者附加险
					tLLRepColligateSchema.setC4(allSSRS.GetText(i + 1, 4)); // 险种分类值
					tLLRepColligateSchema.setC5(tCount[0]); // 查询预收录入件数
					tLLRepColligateSchema.setC6(tCount[1]); // 查询出具核保结论件数
					tLLRepColligateSchema.setC7(tCount[2]); // <=2万
					tLLRepColligateSchema.setC8(tCount[3]); // 2－5万
					tLLRepColligateSchema.setC9(tCount[4]); // 5－10万
					tLLRepColligateSchema.setC10(tCount[5]); // 10－20万
					tLLRepColligateSchema.setC11(tCount[6]); // 20－30万
					tLLRepColligateSchema.setC12(tCount[7]); // >＝30万
					tLLRepColligateSchema.setC13(tCount[8]); // 承保件数
					tLLRepColligateSchema.setC14(tCount[9]); // <=2万
					tLLRepColligateSchema.setC15(tCount[10]); // 2－5万
					tLLRepColligateSchema.setC16(tCount[11]); // 5－10万
					tLLRepColligateSchema.setC17(tCount[12]); // 10－20万
					tLLRepColligateSchema.setC18(tCount[13]); // 20－30万
					tLLRepColligateSchema.setC19(tCount[14]); // >=万
					mMMap.put(tLLRepColligateSchema, "DELETE&INSERT");

				}
				if (tOperate != null && tOperate.equals("PRINT")) {
					String mStartDate = strStartDate.substring(0, 4)
							+ strStartDate.substring(5, 7)
							+ strStartDate.substring(8, 10);
					String mEndDate = strEndDate.substring(0, 4)
							+ strEndDate.substring(5, 7)
							+ strEndDate.substring(8, 10);

					String tempSQL = "select mngcom, mngcomname,"
							+ " c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,"
							+ " c12,c13,c14,c15,c16,c17,c18,c19 "
							+ " from llrepcolligate where 1=1 and "
							+ " repid like 'UWREPORTSE2%' and repname = '保额分布统计' and "
							+ " mngcom = '" + "?strMngCom?" + "' and " // 管理机构代码
							+ " startdate = '" + "?mStartDate?" + "' and " // 开始时间
							+ " enddate = '" + "?mEndDate?" + "' " // 结束时间
					;
					SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
					sqlbv32.sql(tempSQL);
					sqlbv32.put("strMngCom", strMngCom);
					sqlbv32.put("mStartDate", mStartDate);
					sqlbv32.put("mEndDate", mEndDate);
					ExeSQL tempExeSQL = new ExeSQL();
					SSRS temmSSRS = new SSRS();
					temmSSRS = tempExeSQL.execSQL(sqlbv32);

					if (temmSSRS.getMaxRow() == 0) {
						buildError("tLLRepColligateSchema", "没有得到打印信息！");
						return false;
					}
					for (int n = 1; n <= temmSSRS.getMaxRow(); n++) {
						String[] cols1 = new String[21];
						cols1[0] = temmSSRS.GetText(n, 1);
						cols1[1] = temmSSRS.GetText(n, 2);
						cols1[2] = temmSSRS.GetText(n, 3);
						cols1[3] = temmSSRS.GetText(n, 4);
						cols1[4] = temmSSRS.GetText(n, 5);
						cols1[5] = temmSSRS.GetText(n, 6);
						cols1[6] = temmSSRS.GetText(n, 7);
						cols1[7] = temmSSRS.GetText(n, 8);
						cols1[8] = temmSSRS.GetText(n, 9);
						cols1[9] = temmSSRS.GetText(n, 10);
						cols1[10] = temmSSRS.GetText(n, 11);
						cols1[11] = temmSSRS.GetText(n, 12);
						cols1[12] = temmSSRS.GetText(n, 13);
						cols1[13] = temmSSRS.GetText(n, 14);
						cols1[14] = temmSSRS.GetText(n, 15);
						cols1[15] = temmSSRS.GetText(n, 16);
						cols1[16] = temmSSRS.GetText(n, 17);
						cols1[17] = temmSSRS.GetText(n, 18);
						cols1[18] = temmSSRS.GetText(n, 19);
						cols1[19] = temmSSRS.GetText(n, 20);
						cols1[20] = temmSSRS.GetText(n, 21);
						tlistTable.add(cols1);

					}
					String[] col = new String[1];
					xmlexport.addListTable(tlistTable, col);
					mResult.clear();
					mResult.addElement(xmlexport);
					return true;
				}

			}
		}

		if (tUWStatType.equals("3")) {
			XmlExport xmlexport = new XmlExport(); // Create a XmlExport
			// instance
			xmlexport.createDocument("tjzkstatic.vts", "printer");
			String name = "";
			String sql = "select name from ldcom where comcode='" + "?strMngCom?"
					+ "'";
			SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
			sqlbv33.sql(sql);
			sqlbv33.put("strMngCom", strMngCom);
			ExeSQL exeSQL = new ExeSQL();
			SSRS ssrs = exeSQL.execSQL(sqlbv33);
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
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
				SQLwithBindVariables sqlbv34 = new SQLwithBindVariables();
				sqlbv34.sql(strSQL);
				sqlbv34.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv34.put("strStartDate", strStartDate);
				sqlbv34.put("strEndDate", strEndDate);
				sqlbv34.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv34);
				tCount[0] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 查询出具核保结论件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and ((signdate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd') ) or"
						+ " (uwflag in ('1', '2') and uwdate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd') and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
				SQLwithBindVariables sqlbv35 = new SQLwithBindVariables();
				sqlbv35.sql(strSQL);
				sqlbv35.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv35.put("strStartDate", strStartDate);
				sqlbv35.put("strEndDate", strEndDate);
				sqlbv35.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv35);
				tCount[1] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 人工核保件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and prtno in (select rpad(missionprop1, lislen('lcpol', 'contno'), ' ')"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and missionprop10 like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " union"
						+ " select rpad(missionprop1, lislen('lcpol', 'contno'), ' ')"
						+ " from lwmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and missionprop10 like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ ")"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
				SQLwithBindVariables sqlbv36 = new SQLwithBindVariables();
				sqlbv36.sql(strSQL);
				sqlbv36.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv36.put("strStartDate", strStartDate);
				sqlbv36.put("strEndDate", strEndDate);
				sqlbv36.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv36);
				tCount[2] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 发出体检通知件数
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(b.contno)"
						+ " from lcpol a,lcpenotice b" + " where "
						+ " a.riskcode  like '"
						+ "?riskcode?"
						+ "'"
						+ " and a.managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.payintv = 0 or paytodate is null)"
						+ " and exists (select 'x'"
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
						+ " where lccont.contno = a.contno"
						+ " and (lccont.cvalidate = a.cvalidate or lccont.cvalidate is null))"
						+ " and a.polno = a.mainpolno"
						+ " and b.makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and a.prtno = b.proposalcontno";
				SQLwithBindVariables sqlbv37 = new SQLwithBindVariables();
				sqlbv37.sql(strSQL);
				sqlbv37.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv37.put("strStartDate", strStartDate);
				sqlbv37.put("strEndDate", strEndDate);
				sqlbv37.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv37);
				tCount[3] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 体检应答件数
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(b.contno)"
						+ " from lcpol a,lcpenotice b" + " where "
						+ " a.riskcode  like '"
						+ "?riskcode?"
						+ "'"
						+ " and a.managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.payintv = 0 or a.paytodate is null)"
						+ " and exists (select 'x'"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and missionprop1 = trim(a.prtno)"
						+ " union"
						+ " select 'x'"
						+ " from lwmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and "
						+ " missionprop1 = trim(a.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = a.contno"
						+ " and (lccont.cvalidate = a.cvalidate or lccont.cvalidate is null))"
						+ " and a.polno = a.mainpolno"
						+ " and a.prtno = b.proposalcontno"
						+ " and exists (select 'X'"
						+ " from lcpenoticeitem"
						+ " where lcpenoticeitem.proposalcontno = a.prtno"
						+ " and lcpenoticeitem.peitemresult is not null "
						+ " and lcpenoticeitem.modifydate between '"
						+ "?strStartDate?" + "' and '" + "?strEndDate?" + "')";
				SQLwithBindVariables sqlbv38 = new SQLwithBindVariables();
				sqlbv38.sql(strSQL);
				sqlbv38.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv38.put("strStartDate", strStartDate);
				sqlbv38.put("strEndDate", strEndDate);
				sqlbv38.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv38);
				tCount[4] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 体检阳性件数
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(b.contno)"
						+ " from lcpol a,lcpenotice b" + " where "
						+ " a.riskcode  like '"
						+ "?riskcode?"
						+ "'"
						+ " and a.managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.payintv = 0 or a.paytodate is null or paytodate is null)"
						+ " and exists ("
						+ " select 'x'"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno)"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno)"
						+ " union"
						+ " select 'x'"
						+ " from lwmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and"
						+ " missionprop1 = trim(a.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = a.prtno"
						+ " and (lccont.cvalidate = a.cvalidate or lccont.cvalidate is null))"
						+ " and a.polno = a.mainpolno"
						+ " and a.prtno = b.proposalcontno"
						+ " and b.masculineflag='Y'"
						+ " and exists (select 'X'"
						+ " from lcpenoticeitem"
						+ " where lcpenoticeitem.proposalcontno = a.prtno"
						+ " and lcpenoticeitem.peitemresult is not null "
						+ " and lcpenoticeitem.modifydate between '"
						+ "?strStartDate?" + "' and '" + "?strEndDate?" + "')";
				SQLwithBindVariables sqlbv39 = new SQLwithBindVariables();
				sqlbv39.sql(strSQL);
				sqlbv39.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv39.put("strStartDate", strStartDate);
				sqlbv39.put("strEndDate", strEndDate);
				sqlbv39.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv39);
				tCount[5] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 体检阳性未告知件数
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(b.contno)"
						+ " from lcpol a,lcpenotice b" + " where "
						+ " a.riskcode  like '"
						+ "?riskcode?"
						+ "'"
						+ " and a.managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.payintv = 0 or a.paytodate is null)"
						+ " and exists (select 'X' from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and missionprop1 = trim(a.prtno)"
						+ " union"
						+ " select 'x'"
						+ " from lwmission"
//						+ " where activityid = '0000001100' "
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') "
						+ " and missionprop1 = trim(a.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = a.prtno"
						+ " and (lccont.cvalidate = a.cvalidate or lccont.cvalidate is null))"
						+ " and a.polno = a.mainpolno"
						+ " and a.prtno = b.proposalcontno"
						+ " and b.masculineflag='Y'"
						+ " and not exists (select 'X'"
						+ " from lccustomerimpart"
						+ " where lccustomerimpart.prtno = a.prtno"
						+ " and lccustomerimpart.impartcode <> '000'"
						+ " and lccustomerimpart.impartver = '02')"
						+ " and exists (select 'X'"
						+ " from lcpenoticeitem"
						+ " where lcpenoticeitem.proposalcontno = a.prtno"
						+ " and lcpenoticeitem.peitemresult is not null "
						+ " and lcpenoticeitem.modifydate between '"
						+ "?strStartDate?" + "' and '" + "?strEndDate?" + "')";
				SQLwithBindVariables sqlbv40 = new SQLwithBindVariables();
				sqlbv40.sql(strSQL);
				sqlbv40.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv40.put("strStartDate", strStartDate);
				sqlbv40.put("strEndDate", strEndDate);
				sqlbv40.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv40);
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
						+ "','yyyy-mm-dd') "
						+ " and a.managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.payintv = 0 or a.paytodate is null)"
						+ " and b.pereason='01'"
						+ " and exists (select 'X'"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno)"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno)"
						+ " union"
						+ " select 'x'"
						+ " from lwmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and missionprop1 = trim(a.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = a.prtno"
						+ " and (lccont.cvalidate = a.cvalidate or lccont.cvalidate is null))"
						+ " and a.polno = a.mainpolno"
						+ " and b.makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and a.prtno = b.proposalcontno";
				SQLwithBindVariables sqlbv41 = new SQLwithBindVariables();
				sqlbv41.sql(strSQL);
				sqlbv41.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv41.put("strStartDate", strStartDate);
				sqlbv41.put("strEndDate", strEndDate);
				sqlbv41.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv41);
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
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.payintv = 0 or a.paytodate is null)"
						+ " and b.pereason='02'"
						+ " and exists (select 'X'"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno)"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno)"
						+ " union"
						+ " select 'x'"
						+ " from lwmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and missionprop1 = trim(a.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.prtno = a.prtno"
						+ " and (lccont.cvalidate = a.cvalidate or lccont.cvalidate is null))"
						+ " and a.polno = a.mainpolno"
						+ " and b.makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and a.prtno = b.proposalcontno";
				SQLwithBindVariables sqlbv42 = new SQLwithBindVariables();
				sqlbv42.sql(strSQL);
				sqlbv42.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv42.put("strStartDate", strStartDate);
				sqlbv42.put("strEndDate", strEndDate);
				sqlbv42.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv42);
				tCount[8] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 客户要求
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(b.contno)"
						+ " from lcpol a,lcpenotice b" + " where "
						+ " a.riskcode  like '"
						+ "?riskcode?"
						+ "'"
						+ " and a.managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.payintv = 0 or a.paytodate is null)"
						+ " and b.pereason='10'"
						+ " and exists (select 'X'"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno)"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno)"
						+ " union"
						+ " select 'x'"
						+ " from lwmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and missionprop1 = trim(a.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = a.contno"
						+ " and (lccont.cvalidate = a.cvalidate or lccont.cvalidate is null))"
						+ " and a.polno = a.mainpolno"
						+ " and b.makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and a.prtno = b.proposalcontno";
				SQLwithBindVariables sqlbv43 = new SQLwithBindVariables();
				sqlbv43.sql(strSQL);
				sqlbv43.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv43.put("strStartDate", strStartDate);
				sqlbv43.put("strEndDate", strEndDate);
				sqlbv43.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv43);
				tCount[9] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 拒保延期史
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(b.contno)"
						+ " from lcpol a,lcpenotice b" + " where "
						+ " a.riskcode  like '"
						+ "?riskcode?"
						+ "'"
						+ " and a.managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.payintv = 0 or a.paytodate is null)"
						+ " and b.pereason='03'"
						+ " and exists (select 'X'"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno)"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno)"
						+ " union"
						+ " select 'x'"
						+ " from lwmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and missionprop1 = trim(a.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = a.contno"
						+ " and (lccont.cvalidate = a.cvalidate or lccont.cvalidate is null))"
						+ " and a.polno = a.mainpolno"
						+ " and b.makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and a.prtno = b.proposalcontno";
				SQLwithBindVariables sqlbv44 = new SQLwithBindVariables();
				sqlbv44.sql(strSQL);
				sqlbv44.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv44.put("strStartDate", strStartDate);
				sqlbv44.put("strEndDate", strEndDate);
				sqlbv44.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv44);
				tCount[10] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 加费史
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(b.contno)"
						+ " from lcpol a,lcpenotice b" + " where "
						+ " a.riskcode  like '"
						+ "?riskcode?"
						+ "'"
						+ " and a.managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.payintv = 0 or a.paytodate is null)"
						+ " and b.pereason='04'"
						+ " and exists (select 'X'"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno)"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno)"
						+ " union"
						+ " select 'x'"
						+ " from lwmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and missionprop1 = trim(a.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = a.contno"
						+ " and (lccont.cvalidate = a.cvalidate or lccont.cvalidate is null))"
						+ " and a.polno = a.mainpolno"
						+ " and b.makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and a.prtno = b.proposalcontno";
				SQLwithBindVariables sqlbv45 = new SQLwithBindVariables();
				sqlbv45.sql(strSQL);
				sqlbv45.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv45.put("strStartDate", strStartDate);
				sqlbv45.put("strEndDate", strEndDate);
				sqlbv45.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv45);
				tCount[11] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 复检
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(b.contno)"
						+ " from lcpol a,lcpenotice b" + " where "
						+ " a.riskcode  like '"
						+ "?riskcode?"
						+ "'"
						+ " and a.managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.payintv = 0 or a.paytodate is null)"
						+ " and b.pereason='05'"
						+ " and exists (select 'X'"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno)"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno)"
						+ " union"
						+ " select 'x'"
						+ " from lwmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and missionprop1 = trim(a.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = a.contno"
						+ " and (lccont.cvalidate = a.cvalidate or lccont.cvalidate is null))"
						+ " and a.polno = a.mainpolno"
						+ " and b.makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and a.prtno = b.proposalcontno";
				SQLwithBindVariables sqlbv46 = new SQLwithBindVariables();
				sqlbv46.sql(strSQL);
				sqlbv46.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv46.put("strStartDate", strStartDate);
				sqlbv46.put("strEndDate", strEndDate);
				sqlbv46.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv46);
				tCount[12] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 高额保件
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(b.contno)"
						+ " from lcpol a,lcpenotice b" + " where "
						+ " a.riskcode  like '"
						+ "?riskcode?"
						+ "'"
						+ " and a.managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.payintv = 0 or a.paytodate is null)"
						+ " and b.pereason='06'"
						+ " and exists (select 'X'"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno)"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno)"
						+ " union"
						+ " select 'x'"
						+ " from lwmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and missionprop1 = trim(a.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = a.contno"
						+ " and (lccont.cvalidate = a.cvalidate or lccont.cvalidate is null))"
						+ " and a.polno = a.mainpolno"
						+ " and b.makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and a.prtno = b.proposalcontno";
				SQLwithBindVariables sqlbv47 = new SQLwithBindVariables();
				sqlbv47.sql(strSQL);
				sqlbv47.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv47.put("strStartDate", strStartDate);
				sqlbv47.put("strEndDate", strEndDate);
				sqlbv47.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv47);
				tCount[13] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 复效
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(b.contno)"
						+ " from lcpol a,lcpenotice b" + " where "
						+ " a.riskcode  like '"
						+ "?riskcode?"
						+ "'"
						+ " and a.managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.payintv = 0 or a.paytodate is null)"
						+ " and b.pereason='07'"
						+ " and exists (select 'X'"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno)"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno)"
						+ " union"
						+ " select 'x'"
						+ " from lwmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and missionprop1 = trim(a.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = a.contno"
						+ " and (lccont.cvalidate = a.cvalidate or lccont.cvalidate is null))"
						+ " and a.polno = a.mainpolno"
						+ " and b.makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and a.prtno = b.proposalcontno";
				SQLwithBindVariables sqlbv48 = new SQLwithBindVariables();
				sqlbv48.sql(strSQL);
				sqlbv48.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv48.put("strStartDate", strStartDate);
				sqlbv48.put("strEndDate", strEndDate);
				sqlbv48.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv48);
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
						+ " and a.managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.payintv = 0 or a.paytodate is null)"
						+ " and b.pereason='08'"
						+ " and exists (select 'X'"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno)"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno)"
						+ " union"
						+ " select 'x'"
						+ " from lwmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and missionprop1 = trim(a.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = a.contno"
						+ " and (lccont.cvalidate = a.cvalidate or lccont.cvalidate is null))"
						+ " and a.polno = a.mainpolno"
						+ " and b.makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and a.prtno = b.proposalcontno";
				SQLwithBindVariables sqlbv49 = new SQLwithBindVariables();
				sqlbv49.sql(strSQL);
				sqlbv49.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv49.put("strStartDate", strStartDate);
				sqlbv49.put("strEndDate", strEndDate);
				sqlbv49.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv49);
				tCount[15] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 补充告知
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(b.contno)"
						+ " from lcpol a,lcpenotice b" + " where "
						+ " a.riskcode  like '"
						+ "?riskcode?"
						+ "'"
						+ " and a.managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(a.paytodate, a.cvalidate) <= 12 or a.payintv = 0 or a.paytodate is null)"
						+ " and b.pereason='09'"
						+ " and exists (select 'X'"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and missionprop1 = trim(a.prtno)"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and missionprop1 = trim(a.prtno)"
						+ " union"
						+ " select 'x'"
						+ " from lwmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and missionprop1 = trim(a.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = a.contno"
						+ " and (lccont.cvalidate = a.cvalidate or lccont.cvalidate is null))"
						+ " and a.polno = a.mainpolno"
						+ " and b.makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and a.prtno = b.proposalcontno";
				SQLwithBindVariables sqlbv50 = new SQLwithBindVariables();
				sqlbv50.sql(strSQL);
				sqlbv50.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv50.put("strStartDate", strStartDate);
				sqlbv50.put("strEndDate", strEndDate);
				sqlbv50.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv50);
				tCount[16] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				/**
				 * ============================================================================
				 * 修改原因：由于查询数据比较多，打印非常慢，所以，现将需要打印的数据查出存储在一个一个临时表
				 * 中，然后再去临时表中去数据打印。可以在存储数据的同时做其他 的事情 修改人： 万泽辉 修改时间： 2005/12/22
				 * 16:00
				 * =============================================================================
				 */
				// 准备需要暂存在 llrepcolligate表中的数据
				if (tOperate != null && tOperate.equals("SAVE")) {
					LLRepColligateSchema tLLRepColligateSchema = new LLRepColligateSchema();
					String tRepID = "UWREPORTSE300" + String.valueOf(i);
					if (tRepID == null || tRepID.equals("")) {
						buildError("tRepID", "生成[体检状况统计]编号失败！");
						return false;
					}
					String mStartDate = strStartDate.substring(0, 4)
							+ strStartDate.substring(5, 7)
							+ strStartDate.substring(8, 10);
					String mEndDate = strEndDate.substring(0, 4)
							+ strEndDate.substring(5, 7)
							+ strEndDate.substring(8, 10);

					String tRepName = "体检状况统计";
					String tColID = String.valueOf(i);
					String tColName = "";
					tLLRepColligateSchema.setRepID(tRepID); // 报表编号
					tLLRepColligateSchema.setRepName(tRepName); // 报表名称
					tLLRepColligateSchema.setColID(tColID); // 栏目号
					tLLRepColligateSchema.setColName(tColName); // 栏目名称
					tLLRepColligateSchema.setEndDate(mEndDate); // 结束时间
					tLLRepColligateSchema.setStartDate(mStartDate); // 开始时间
					tLLRepColligateSchema.setMakeDate(mCurrentDate); // 最后一次修改日
					tLLRepColligateSchema.setMakeTime(mCurrentTime);
					tLLRepColligateSchema.setModifyDate(mCurrentDate);
					tLLRepColligateSchema.setModifyTime(mCurrentTime); // 最后一次修改时间
					tLLRepColligateSchema.setOperator(mOperator); // 操作人
					tLLRepColligateSchema.setMngCom(strMngCom); // 管理机构代码
					tLLRepColligateSchema.setMngComName(name); // 管理机构名称
					tLLRepColligateSchema.setC1(allSSRS.GetText(i + 1, 1)); // 险种编码
					tLLRepColligateSchema.setC2(allSSRS.GetText(i + 1, 2)); // 险种名称
					tLLRepColligateSchema.setC3(allSSRS.GetText(i + 1, 3)); // 主险或者附加险
					tLLRepColligateSchema.setC4(allSSRS.GetText(i + 1, 4)); // 险种分类值
					tLLRepColligateSchema.setC5(tCount[0]); // 查询预收录入件数
					tLLRepColligateSchema.setC6(tCount[1]); // 查询出具核保结论件数
					tLLRepColligateSchema.setC7(tCount[2]); // 人工核保件数
					tLLRepColligateSchema.setC8(tCount[3]); // 发出体检通知件数
					tLLRepColligateSchema.setC9(tCount[4]); // 体检应答件数
					tLLRepColligateSchema.setC10(tCount[5]); // 体检阳性件数
					tLLRepColligateSchema.setC11(tCount[6]); // 体检阳性未告知件数
					tLLRepColligateSchema.setC12(tCount[7]); // 达到体检标准
					tLLRepColligateSchema.setC13(tCount[8]); // 抽样体检
					tLLRepColligateSchema.setC14(tCount[9]); // 客户要求
					tLLRepColligateSchema.setC15(tCount[10]); // 拒保延期史
					tLLRepColligateSchema.setC16(tCount[11]); // 加费史
					tLLRepColligateSchema.setC17(tCount[12]); // 复检
					tLLRepColligateSchema.setC18(tCount[13]); // 高额保件
					tLLRepColligateSchema.setC19(tCount[14]); // 复效
					tLLRepColligateSchema.setC20(tCount[15]); // 加保附加险
					tLLRepColligateSchema.setC21(tCount[16]); // 补充告知

					mMMap.put(tLLRepColligateSchema, "DELETE&INSERT");
				}
				if (tOperate != null && tOperate.equals("PRINT")) {
					String mStartDate = strStartDate.substring(0, 4)
							+ strStartDate.substring(5, 7)
							+ strStartDate.substring(8, 10);
					String mEndDate = strEndDate.substring(0, 4)
							+ strEndDate.substring(5, 7)
							+ strEndDate.substring(8, 10);

					String tempSQL = "select mngcom, mngcomname,"
							+ " c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,"
							+ " c12,c13,c14,c15,c16,c17,c18,c19,c20,c21 "
							+ " from llrepcolligate where 1=1 and "
							+ " repid like 'UWREPORTSE3%' and repname = '体检状况统计' and "
							+ " mngcom = '" + "?strMngCom?" + "' and " // 管理机构代码
							+ " startdate = '" + "?mStartDate?" + "' and " // 开始时间
							+ " enddate = '" + "?mEndDate?" + "' " // 结束时间
					;
					SQLwithBindVariables sqlbv51 = new SQLwithBindVariables();
					sqlbv51.sql(tempSQL);
					sqlbv51.put("strMngCom", strMngCom);
					sqlbv51.put("mStartDate", mStartDate);
					sqlbv51.put("mEndDate", mEndDate);
					ExeSQL tempExeSQL = new ExeSQL();
					SSRS temmSSRS = new SSRS();
					temmSSRS = tempExeSQL.execSQL(sqlbv51);

					if (temmSSRS.getMaxRow() == 0) {
						buildError("tLLRepColligateSchema", "没有得到打印信息！");
						return false;
					}
					for (int n = 1; n <= temmSSRS.getMaxRow(); n++) {
						String[] cols1 = new String[23];
						cols1[0] = temmSSRS.GetText(n, 1);
						cols1[1] = temmSSRS.GetText(n, 2);
						cols1[2] = temmSSRS.GetText(n, 3);
						cols1[3] = temmSSRS.GetText(n, 4);
						cols1[4] = temmSSRS.GetText(n, 5);
						cols1[5] = temmSSRS.GetText(n, 6);
						cols1[6] = temmSSRS.GetText(n, 7);
						cols1[7] = temmSSRS.GetText(n, 8);
						cols1[8] = temmSSRS.GetText(n, 9);
						cols1[9] = temmSSRS.GetText(n, 10);
						cols1[10] = temmSSRS.GetText(n, 11);
						cols1[11] = temmSSRS.GetText(n, 12);
						cols1[12] = temmSSRS.GetText(n, 13);
						cols1[13] = temmSSRS.GetText(n, 14);
						cols1[14] = temmSSRS.GetText(n, 15);
						cols1[15] = temmSSRS.GetText(n, 16);
						cols1[16] = temmSSRS.GetText(n, 17);
						cols1[17] = temmSSRS.GetText(n, 18);
						cols1[18] = temmSSRS.GetText(n, 19);
						cols1[19] = temmSSRS.GetText(n, 20);
						cols1[20] = temmSSRS.GetText(n, 21);
						cols1[21] = temmSSRS.GetText(n, 22);
						cols1[22] = temmSSRS.GetText(n, 23);

						tlistTable.add(cols1);

					}
					String[] col = new String[1];
					xmlexport.addListTable(tlistTable, col);
					mResult.clear();
					mResult.addElement(xmlexport);
					return true;
				}

			}
		}

		if (tUWStatType.equals("4")) {
			XmlExport xmlexport = new XmlExport(); // Create a XmlExport
			// instance
			xmlexport.createDocument("jfstatic.vts", "printer");

			String name = "";
			String sql = "select name from ldcom where comcode='" + "?strMngCom?"
					+ "'";
			SQLwithBindVariables sqlbv52 = new SQLwithBindVariables();
			sqlbv52.sql(sql);
			sqlbv52.put("strMngCom", strMngCom);
			ExeSQL exeSQL = new ExeSQL();
			SSRS ssrs = exeSQL.execSQL(sqlbv52);
			name = ssrs.GetText(1, 1);
			logger.debug("开始执行strOperation＝" + tUWStatType + "的sql操作");

			String strSQL = "";
			ExeSQL exeSQL1 = new ExeSQL();
			SSRS ssrs1 = new SSRS();
			// 对于每一个险种进行循环判断相关的取值
			for (int i = 0; i < allRiskCount; i++) {
				String[] tCount = new String[11];

				// //////////////////////////////////////////////////////////////////
				// 查询出具核保结论件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and ((signdate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "') or"
						+ " (uwflag in ('1', '2') and uwdate between '"
						+ "?strStartDate?"
						+ "' and "
						+ " '"
						+ "?strEndDate?"
						+ "'))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
				SQLwithBindVariables sqlbv53 = new SQLwithBindVariables();
				sqlbv53.sql(strSQL);
				sqlbv53.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv53.put("strStartDate", strStartDate);
				sqlbv53.put("strEndDate", strEndDate);
				sqlbv53.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv53);
				tCount[1] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 人工核保件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and prtno in (select rpad(missionprop1, lislen('lcpol', 'contno'), ' ')"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and missionprop10 like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " union"
						+ " select rpad(missionprop1, lislen('lcpol', 'contno'), ' ')"
						+ " from lwmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and missionprop10 like concat('"
						+ "?strMngCom?"
						+ "','%'))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
				SQLwithBindVariables sqlbv54 = new SQLwithBindVariables();
				sqlbv54.sql(strSQL);
				sqlbv54.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv54.put("strStartDate", strStartDate);
				sqlbv54.put("strEndDate", strEndDate);
				sqlbv54.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv54);
				tCount[2] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 加费评点<＝ 50件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and exists (select 'X'"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and missionprop1 = trim(lcpol.prtno)"
						+ " union"
						+ " select 'x'"
						+ " from lwmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and missionprop1 = trim(lcpol.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))"
						+ " and exists (select 'X'"
						+ " from lcprem"
						+ " where lcprem.polno=lcpol.polno"
						+ " and lcprem.payplantype in ('01','02')"
						+ " and suppriskscore <= 50"
						+ " and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
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
				tCount[3] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 评点<＝ 50加费成功件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and signdate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and exists (select 'X'"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and missionprop1 = trim(lcpol.prtno)"
						+ " union"
						+ " select 'x'"
						+ " from lwmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and missionprop1 = trim(lcpol.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))"
						+ " and exists (select 'X'"
						+ " from lcprem"
						+ " where lcprem.polno=lcpol.polno"
						+ " and lcprem.payplantype in ('01','02')"
						+ " and suppriskscore <= 50"
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
				tCount[4] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 加费评点75-100件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						// + " and uwflag='3'"
						+ " and exists (select 'X'"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and missionprop1 = trim(lcpol.prtno)"
						+ " union"
						+ " select 'x'"
						+ " from lwmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"						
						+ " and missionprop1 = trim(lcpol.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))"
						+ " and exists (select 'X'"
						+ " from lcprem"
						+ " where lcprem.polno=lcpol.polno"
						+ " and lcprem.payplantype in ('01','02')"
						+ " and suppriskscore <= 100 and suppriskscore>=75"
						+ " and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
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
				tCount[5] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 评点75-100加费成功件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						// + " and uwflag='3'"
						+ " and signdate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and exists (select 'X'"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and missionprop1 = trim(lcpol.prtno)"
						+ " union"
						+ " select 'x'"
						+ " from lwmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and missionprop1 = trim(lcpol.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))"
						+ " and exists (select 'X'"
						+ " from lcprem"
						+ " where lcprem.polno=lcpol.polno"
						+ " and lcprem.payplantype in ('01','02')"
						+ " and suppriskscore <= 100 and suppriskscore>=75"
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
				tCount[6] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 加费评点150-200件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+
						// " and uwflag='3'" +
						" and exists ("
						+ " select 'x'"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and missionprop1 = trim(lcpol.prtno)"
						+ " union"
						+ " select 'x'"
						+ " from lwmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and "
						+ " missionprop1 = trim(lcpol.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))"
						+ " and exists (select 'X'"
						+ " from lcprem"
						+ " where lcprem.polno=lcpol.polno"
						+ " and lcprem.payplantype in ('01','02')"
						+ " and suppriskscore <= 200 and suppriskscore>=150"
						+ " and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
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
				tCount[7] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 评点150-200加费成功件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and signdate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and exists ("
						+ " select 'x'"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and (missionprop1) = trim(lcpol.prtno)"
						+ " union"
						+ " select 'x'"
						+ " from lwmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and "
						+ " (missionprop1) = trim(lcpol.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))"
						+ " and exists (select 'X'"
						+ " from lcprem"
						+ " where lcprem.polno=lcpol.polno"
						+ " and lcprem.payplantype in ('01','02')"
						+ " and suppriskscore <= 200 and suppriskscore>=150"
						+ " and managecom like concat('"
						+ "?strMngCom?"
						+ "','%'))";
				SQLwithBindVariables sqlbv60 = new SQLwithBindVariables();
				sqlbv60.sql(strSQL);
				sqlbv60.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv60.put("strStartDate", strStartDate);
				sqlbv60.put("strEndDate", strEndDate);
				sqlbv60.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv60);
				tCount[8] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 加费评点>200件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and exists ("
						+ " select 'x'"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and missionprop1 = trim(lcpol.prtno)"
						+ " union"
						+ " select 'x'"
						+ " from lwmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and "
						+ " missionprop1 = trim(lcpol.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))"
						+ " and exists (select 'X'"
						+ " from lcprem"
						+ " where lcprem.polno=lcpol.polno"
						+ " and lcprem.payplantype in ('01','02')"
						+ " and suppriskscore>200"
						+ " and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and managecom like concat('"
						+ "?strMngCom?"
						+ "','%'))";
				SQLwithBindVariables sqlbv61 = new SQLwithBindVariables();
				sqlbv61.sql(strSQL);
				sqlbv61.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv61.put("strStartDate", strStartDate);
				sqlbv61.put("strEndDate", strEndDate);
				sqlbv61.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv61);
				tCount[9] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 评点>200加费成功件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and signdate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and exists (select 'X'"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and (missionprop1) = trim(lcpol.prtno)"
						+ " union"
						+ " select 'x'"
						+ " from lwmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and "
						+ " (missionprop1) = trim(lcpol.prtno))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))"
						+ " and exists (select 'X'"
						+ " from lcprem"
						+ " where lcprem.polno=lcpol.polno"
						+ " and lcprem.payplantype in ('01','02')"
						+ " and suppriskscore > 200"
						+ " and managecom like concat('"
						+ "?strMngCom?"
						+ "','%'))";
				SQLwithBindVariables sqlbv62 = new SQLwithBindVariables();
				sqlbv62.sql(strSQL);
				sqlbv62.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv62.put("strStartDate", strStartDate);
				sqlbv62.put("strEndDate", strEndDate);
				sqlbv62.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv62);
				tCount[10] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////

				/**
				 * ============================================================================
				 * 修改原因：由于查询数据比较多，打印非常慢，所以，现将需要打印的数据查出存储在一个一个临时表
				 * 中，然后再去临时表中去数据打印。可以在存储数据的同时做其他 的事情 修改人： 万泽辉 修改时间： 2005/12/22
				 * 16:00
				 * =============================================================================
				 */
				// 准备需要暂存在 llrepcolligate表中的数据
				// 开始保存
				if (tOperate != null && tOperate.equals("SAVE")) {
					LLRepColligateSchema tLLRepColligateSchema = new LLRepColligateSchema();
					String tRepID = "UWREPORTSE400" + String.valueOf(i);
					if (tRepID == null || tRepID.equals("")) {
						buildError("tRepID", "生成[加费状况统计]编号失败！");
						return false;
					}
					String mStartDate = strStartDate.substring(0, 4)
							+ strStartDate.substring(5, 7)
							+ strStartDate.substring(8, 10);
					String mEndDate = strEndDate.substring(0, 4)
							+ strEndDate.substring(5, 7)
							+ strEndDate.substring(8, 10);

					String tRepName = "加费状况统计";
					String tColID = String.valueOf(i);
					String tColName = "";
					tLLRepColligateSchema.setRepID(tRepID); // 报表编号
					tLLRepColligateSchema.setRepName(tRepName); // 报表名称
					tLLRepColligateSchema.setColID(tColID); // 栏目号
					tLLRepColligateSchema.setColName(tColName); // 栏目名称
					tLLRepColligateSchema.setEndDate(mEndDate); // 结束时间
					tLLRepColligateSchema.setStartDate(mStartDate); // 开始时间
					tLLRepColligateSchema.setMakeDate(mCurrentDate); // 最后一次修改日
					tLLRepColligateSchema.setMakeTime(mCurrentTime);
					tLLRepColligateSchema.setModifyDate(mCurrentDate);
					tLLRepColligateSchema.setModifyTime(mCurrentTime); // 最后一次修改时间
					tLLRepColligateSchema.setOperator(mOperator); // 操作人
					tLLRepColligateSchema.setMngCom(strMngCom); // 管理机构代码
					tLLRepColligateSchema.setMngComName(name); // 管理机构名称
					tLLRepColligateSchema.setC1(allSSRS.GetText(i + 1, 1)); // 险种编码
					tLLRepColligateSchema.setC2(allSSRS.GetText(i + 1, 2)); // 险种名称
					tLLRepColligateSchema.setC3(allSSRS.GetText(i + 1, 3)); // 主险或者附加险
					tLLRepColligateSchema.setC4(allSSRS.GetText(i + 1, 4)); // 险种分类值
					tLLRepColligateSchema.setC6(tCount[1]); // 查询出具核保结论件数
					tLLRepColligateSchema.setC7(tCount[2]); // 人工核保件数
					tLLRepColligateSchema.setC8(tCount[3]); // 加费评点<＝ 50件数
					tLLRepColligateSchema.setC9(tCount[4]); // 评点<＝ 50加费成功件数
					tLLRepColligateSchema.setC10(tCount[5]); // 加费评点75-100件数
					tLLRepColligateSchema.setC11(tCount[6]); // 评点75-100加费成功件数
					tLLRepColligateSchema.setC12(tCount[7]); // 加费评点150-200件数
					tLLRepColligateSchema.setC13(tCount[8]); // 评点150-200加费成功件数
					tLLRepColligateSchema.setC14(tCount[9]); // 加费评点>200件数
					tLLRepColligateSchema.setC15(tCount[10]); // 评点>200加费成功件数

					mMMap.put(tLLRepColligateSchema, "DELETE&INSERT");

				}
				// 开始打印
				if (tOperate != null && tOperate.equals("PRINT")) {
					String mStartDate = strStartDate.substring(0, 4)
							+ strStartDate.substring(5, 7)
							+ strStartDate.substring(8, 10);
					String mEndDate = strEndDate.substring(0, 4)
							+ strEndDate.substring(5, 7)
							+ strEndDate.substring(8, 10);

					String tempSQL = "select mngcom, mngcomname,"
							+ " c1,c2,c3,c4,c6,c7,c8,c9,c10,c11,"
							+ " c12,c13,c14,c15 "
							+ " from llrepcolligate where 1=1 and "
							+ " repid like 'UWREPORTSE4%' and repname = '加费状况统计' and "
							+ " mngcom = '" + "?strMngCom?" + "' and " // 管理机构代码
							+ " startdate = '" + "?mStartDate?" + "' and " // 开始时间
							+ " enddate = '" + "?mEndDate?" + "' " // 结束时间
					;
					SQLwithBindVariables sqlbv63 = new SQLwithBindVariables();
					sqlbv63.sql(tempSQL);
					sqlbv63.put("strMngCom", strMngCom);
					sqlbv63.put("mStartDate", mStartDate);
					sqlbv63.put("mEndDate", mEndDate);
					ExeSQL tempExeSQL = new ExeSQL();
					SSRS temmSSRS = new SSRS();
					temmSSRS = tempExeSQL.execSQL(sqlbv63);

					if (temmSSRS.getMaxRow() == 0) {
						buildError("tLLRepColligateSchema", "没有得到打印信息！");
						return false;
					}
					for (int n = 1; n <= temmSSRS.getMaxRow(); n++) {
						String[] cols1 = new String[17];
						cols1[0] = temmSSRS.GetText(n, 1);
						cols1[1] = temmSSRS.GetText(n, 2);
						cols1[2] = temmSSRS.GetText(n, 3);
						cols1[3] = temmSSRS.GetText(n, 4);
						cols1[4] = temmSSRS.GetText(n, 5);
						cols1[5] = temmSSRS.GetText(n, 6);
						cols1[6] = temmSSRS.GetText(n, 7);
						cols1[7] = temmSSRS.GetText(n, 8);
						cols1[8] = temmSSRS.GetText(n, 9);
						cols1[9] = temmSSRS.GetText(n, 10);
						cols1[10] = temmSSRS.GetText(n, 11);
						cols1[11] = temmSSRS.GetText(n, 12);
						cols1[12] = temmSSRS.GetText(n, 13);
						cols1[13] = temmSSRS.GetText(n, 14);
						cols1[14] = temmSSRS.GetText(n, 15);
						cols1[15] = temmSSRS.GetText(n, 16);

						tlistTable.add(cols1);

					}
					String[] col = new String[1];
					xmlexport.addListTable(tlistTable, col);
					mResult.clear();
					mResult.addElement(xmlexport);
					return true;
				}

			}

		}

		if (tUWStatType.equals("5")) {
			XmlExport xmlexport = new XmlExport(); // Create a XmlExport
			// instance
			xmlexport.createDocument("cbstatic.vts", "printer");
			String name = "";
			String sql = "select name from ldcom where comcode='" + "?strMngCom?"
					+ "'";
			SQLwithBindVariables sqlbv64 = new SQLwithBindVariables();
			sqlbv64.sql(sql);
			sqlbv64.put("strMngCom", strMngCom);
			ExeSQL exeSQL = new ExeSQL();
			SSRS ssrs = exeSQL.execSQL(sqlbv64);
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
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
				SQLwithBindVariables sqlbv65 = new SQLwithBindVariables();
				sqlbv65.sql(strSQL);
				sqlbv65.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv65.put("strStartDate", strStartDate);
				sqlbv65.put("strEndDate", strEndDate);
				sqlbv65.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv65);
				tCount[0] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 查询出具核保结论件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and ((signdate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd')  and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd') ) or"
						+ " (uwflag in ('1', '2') and uwdate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd')  and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
				SQLwithBindVariables sqlbv66 = new SQLwithBindVariables();
				sqlbv66.sql(strSQL);
				sqlbv66.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv66.put("strStartDate", strStartDate);
				sqlbv66.put("strEndDate", strEndDate);
				sqlbv66.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv66);
				tCount[1] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 人工核保件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and prtno in (select rpad(missionprop1, lislen('lcpol', 'contno'), ' ')"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and missionprop10 like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " union"
						+ " select rpad(missionprop1, lislen('lcpol', 'contno'), ' ')"
						+ " from lwmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and "
						+ " missionprop10 like concat('"
						+ "?strMngCom?"
						+ "','%'))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
				SQLwithBindVariables sqlbv67 = new SQLwithBindVariables();
				sqlbv67.sql(strSQL);
				sqlbv67.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv67.put("strStartDate", strStartDate);
				sqlbv67.put("strEndDate", strEndDate);
				sqlbv67.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv67);
				tCount[2] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 总撤保件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and uwdate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and (exists (select 'X'"
						+ " from lccont"
						+ " where contno = lcpol.contno"
						+ " and uwflag = 'a') or uwflag = 'a')"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
				SQLwithBindVariables sqlbv68 = new SQLwithBindVariables();
				sqlbv68.sql(strSQL);
				sqlbv68.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv68.put("strStartDate", strStartDate);
				sqlbv68.put("strEndDate", strEndDate);
				sqlbv68.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv68);
				tCount[3] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 自动撤保件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and uwdate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and exists (select 'X'"
						+ " from lccont"
						+ " where contno = lcpol.contno"
						+ " and uwflag = 'a')"
						+ " and not exists (select 'X'"
						+ " from loprtmanager"
						+ " where loprtmanager.otherno = lcpol.contno"
						+ " and code in"
						+ " ('00', '03', '04', '06', '14', '15', '16', '17', '18', '81', '82', '83', '84', '86', '85', '87', '88', '89'))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
				SQLwithBindVariables sqlbv69 = new SQLwithBindVariables();
				sqlbv69.sql(strSQL);
				sqlbv69.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv69.put("strStartDate", strStartDate);
				sqlbv69.put("strEndDate", strEndDate);
				sqlbv69.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv69);
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
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and uwdate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
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
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))";
				SQLwithBindVariables sqlbv70 = new SQLwithBindVariables();
				sqlbv70.sql(strSQL);
				sqlbv70.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv70.put("strStartDate", strStartDate);
				sqlbv70.put("strEndDate", strEndDate);
				sqlbv70.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv70);
				tCount[5] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 加费后撤保件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and uwdate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and exists (select 'X'"
						+ " from lccont"
						+ " where contno = lcpol.contno"
						+ " and uwflag = 'a' )"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))"
						+ " and exists (select 'X'"
						+ " from lcprem"
						+ " where lcprem.polno=lcpol.polno"
						+ " and lcprem.payplantype in ('01','02')"
						+ " and managecom like concat('"
						+ "?strMngCom?"
						+ "','%'))";
				SQLwithBindVariables sqlbv71 = new SQLwithBindVariables();
				sqlbv71.sql(strSQL);
				sqlbv71.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv71.put("strStartDate", strStartDate);
				sqlbv71.put("strEndDate", strEndDate);
				sqlbv71.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv71);
				tCount[6] = ssrs1.GetText(1, 1);
				/**
				 * ============================================================================
				 * 修改原因：由于查询数据比较多，打印非常慢，所以，现将需要打印的数据查出存储在一个一个临时表
				 * 中，然后再去临时表中去数据打印。可以在存储数据的同时做其他 的事情 修改人： 万泽辉 修改时间： 2005/12/22
				 * 16:00
				 * =============================================================================
				 */
				// 准备需要暂存在 llrepcolligate表中的数据
				if (tOperate != null && tOperate.equals("SAVE")) {
					LLRepColligateSchema tLLRepColligateSchema = new LLRepColligateSchema();
					String tRepID = "UWREPORTSE500" + String.valueOf(i);
					if (tRepID == null || tRepID.equals("")) {
						buildError("tRepID", "生成[撤保状况统计]编号失败！");
						return false;
					}
					String mStartDate = strStartDate.substring(0, 4)
							+ strStartDate.substring(5, 7)
							+ strStartDate.substring(8, 10);
					String mEndDate = strEndDate.substring(0, 4)
							+ strEndDate.substring(5, 7)
							+ strEndDate.substring(8, 10);

					String tRepName = "撤保状况统计";
					String tColID = String.valueOf(i);
					String tColName = "";
					tLLRepColligateSchema.setRepID(tRepID); // 报表编号
					tLLRepColligateSchema.setRepName(tRepName); // 报表名称
					tLLRepColligateSchema.setColID(tColID); // 栏目号
					tLLRepColligateSchema.setColName(tColName); // 栏目名称
					tLLRepColligateSchema.setEndDate(mEndDate); // 结束时间
					tLLRepColligateSchema.setStartDate(mStartDate); // 开始时间
					tLLRepColligateSchema.setMakeDate(mCurrentDate); // 最后一次修改日
					tLLRepColligateSchema.setMakeTime(mCurrentTime);
					tLLRepColligateSchema.setModifyDate(mCurrentDate);
					tLLRepColligateSchema.setModifyTime(mCurrentTime); // 最后一次修改时间
					tLLRepColligateSchema.setOperator(mOperator); // 操作人
					tLLRepColligateSchema.setMngCom(strMngCom); // 管理机构代码
					tLLRepColligateSchema.setMngComName(name); // 管理机构名称
					tLLRepColligateSchema.setC1(allSSRS.GetText(i + 1, 1)); // 险种编码
					tLLRepColligateSchema.setC2(allSSRS.GetText(i + 1, 2)); // 险种名称
					tLLRepColligateSchema.setC3(allSSRS.GetText(i + 1, 3)); // 主险或者附加险
					tLLRepColligateSchema.setC4(allSSRS.GetText(i + 1, 4)); // 险种分类值
					tLLRepColligateSchema.setC5(tCount[0]); // 查询预收录入件数
					tLLRepColligateSchema.setC6(tCount[1]); // 查询出具核保结论件数
					tLLRepColligateSchema.setC7(tCount[2]); // 人工核保件数
					tLLRepColligateSchema.setC8(tCount[3]); // 总撤保件数
					tLLRepColligateSchema.setC9(tCount[4]); // 自动撤保件数
					tLLRepColligateSchema.setC10(tCount[5]); // 体检前撤保件数
					tLLRepColligateSchema.setC11(tCount[6]); // 加费后撤保件数

					mMMap.put(tLLRepColligateSchema, "DELETE&INSERT");
				}
				if (tOperate != null && tOperate.equals("PRINT")) {
					String mStartDate = strStartDate.substring(0, 4)
							+ strStartDate.substring(5, 7)
							+ strStartDate.substring(8, 10);
					String mEndDate = strEndDate.substring(0, 4)
							+ strEndDate.substring(5, 7)
							+ strEndDate.substring(8, 10);

					String tempSQL = "select mngcom, mngcomname,"
							+ " c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11"
							+ " from llrepcolligate where 1=1 and "
							+ " repid like 'UWREPORTSE5%' and repname = '撤保状况统计' and "
							+ " mngcom = '" + "?strMngCom?" + "' and " // 管理机构代码
							+ " startdate = '" + "?mStartDate?" + "' and " // 开始时间
							+ " enddate = '" + "?mEndDate?" + "' " // 结束时间
					;
					SQLwithBindVariables sqlbv72 = new SQLwithBindVariables();
					sqlbv72.sql(tempSQL);
					sqlbv72.put("strMngCom", strMngCom);
					sqlbv72.put("mStartDate", mStartDate);
					sqlbv72.put("mEndDate", mEndDate);
					ExeSQL tempExeSQL = new ExeSQL();
					SSRS temmSSRS = new SSRS();
					temmSSRS = tempExeSQL.execSQL(sqlbv72);

					if (temmSSRS.getMaxRow() == 0) {
						buildError("tLLRepColligateSchema", "没有得到打印信息！");
						return false;
					}
					for (int n = 1; n <= temmSSRS.getMaxRow(); n++) {
						String[] cols1 = new String[13];
						cols1[0] = temmSSRS.GetText(n, 1);
						cols1[1] = temmSSRS.GetText(n, 2);
						cols1[2] = temmSSRS.GetText(n, 3);
						cols1[3] = temmSSRS.GetText(n, 4);
						cols1[4] = temmSSRS.GetText(n, 5);
						cols1[5] = temmSSRS.GetText(n, 6);
						cols1[6] = temmSSRS.GetText(n, 7);
						cols1[7] = temmSSRS.GetText(n, 8);
						cols1[8] = temmSSRS.GetText(n, 9);
						cols1[9] = temmSSRS.GetText(n, 10);
						cols1[10] = temmSSRS.GetText(n, 11);
						cols1[11] = temmSSRS.GetText(n, 12);
						cols1[12] = temmSSRS.GetText(n, 13);
						tlistTable.add(cols1);

					}
					String[] col = new String[1];
					xmlexport.addListTable(tlistTable, col);
					mResult.clear();
					mResult.addElement(xmlexport);
					return true;
				}

			}
		}

		if (tUWStatType.equals("6")) {
			XmlExport xmlexport = new XmlExport(); // Create a XmlExport
			// instance
			xmlexport.createDocument("UWQuality.vts", "printer");
			String name = "";
			String sql = "select name from ldcom where comcode='" + "?strMngCom?"
					+ "'";
			SQLwithBindVariables sqlbv73 = new SQLwithBindVariables();
			sqlbv73.sql(sql);
			sqlbv73.put("strMngCom", strMngCom);
			ExeSQL exeSQL = new ExeSQL();
			SSRS ssrs = exeSQL.execSQL(sqlbv73);
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
						+ " and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <> 17"
						+ " and ((signdate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd')  and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd') ) or"
						+ " (uwflag in ('1', '2') and uwdate between to_date('"
						+ "?strStartDate?"
						+ "','yyyy-mm-dd')  and to_date('"
						+ "?strEndDate?"
						+ "','yyyy-mm-dd')))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))"
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
						+ " where trim(defaultoperator) = '"
						+ "?defaultoperator?"
						+ "'"
						+ " and (missionprop1) = trim(lcpol.prtno))";
				SQLwithBindVariables sqlbv74 = new SQLwithBindVariables();
				sqlbv74.sql(strSQL);
				sqlbv74.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv74.put("strStartDate", strStartDate);
				sqlbv74.put("strEndDate", strEndDate);
				sqlbv74.put("strMngCom", strMngCom);
				sqlbv74.put("defaultoperator", allSSRS.GetText(i + 1, 6));
				ssrs1 = exeSQL1.execSQL(sqlbv74);
				tCount[0] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 人工核保件数
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(distinct contno)" + " from lcpol"
						+ " where riskcode = '"
						+ "?riskcode?"
						+ "'"
						+ " and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <> 17"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))"
						+ " and exists (select 'X'"
						+ " from (select missionprop1, defaultoperator"
						+ " from lbmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " union"
						+ " select missionprop1, defaultoperator"
						+ " from lwmission"
//						+ " where activityid = '0000001100'"
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028')"
						+ " and makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ ") g"
						+ " where trim(defaultoperator) = '"
						+ "?defaultoperator?"
						+ "'"
						+ " and (missionprop1) = trim(lcpol.prtno))";
				SQLwithBindVariables sqlbv75 = new SQLwithBindVariables();
				sqlbv75.sql(strSQL);
				sqlbv75.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv75.put("strStartDate", strStartDate);
				sqlbv75.put("strEndDate", strEndDate);
				sqlbv75.put("strMngCom", strMngCom);
				sqlbv75.put("defaultoperator", allSSRS.GetText(i + 1, 6));
				ssrs1 = exeSQL1.execSQL(sqlbv75);
				tCount[1] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 体检件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and exists (select 'X'"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and (missionprop1) = trim(lcpol.prtno) "
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and (missionprop1) = trim(lcpol.prtno) "
						+ " and defaultoperator = '"
						+ "?defaultoperator?"
						+ "'"
						+ " union"
						+ " select 'X'"
						+ " from lwmission"
//						+ " where activityid = '0000001100' and (missionprop1) = trim(lcpol.prtno) "
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and (missionprop1) = trim(lcpol.prtno) "
						+ " and defaultoperator = '"
						+ "?defaultoperator?"
						+ "')"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))"
						+ " and exists"
						+ " (select 'X' from lcpenotice where lcpenotice.proposalcontno = lcpol.prtno"
						+ " and lcpenotice.makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ ")"
						+ " and polno=mainpolno";
				SQLwithBindVariables sqlbv76 = new SQLwithBindVariables();
				sqlbv76.sql(strSQL);
				sqlbv76.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv76.put("strStartDate", strStartDate);
				sqlbv76.put("strEndDate", strEndDate);
				sqlbv76.put("strMngCom", strMngCom);
				sqlbv76.put("defaultoperator", allSSRS.GetText(i + 1, 6));
				ssrs1 = exeSQL1.execSQL(sqlbv76);
				tCount[2] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 核保问题件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and exists (select 'X'"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and (missionprop1) = trim(lcpol.prtno) "
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and (missionprop1) = trim(lcpol.prtno) "
						+ " and defaultoperator = '"
						+ "?defaultoperator?"
						+ "'"
						+ " union"
						+ " select 'X'"
						+ " from lwmission"
//						+ " where activityid = '0000001100' and (missionprop1) = trim(lcpol.prtno) "
						+ " where activityid in (select activityid from lwactivity  where functionid ='10010028') and (missionprop1) = trim(lcpol.prtno) "
						+ " and defaultoperator = '"
						+ "?defaultoperator?"
						+ "')"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))"
						+ " and exists (select 'X'"
						+ " from loprtmanager"
						+ " where loprtmanager.otherno = lcpol.contno"
						+ " and loprtmanager.code = '81' "
						+ " and loprtmanager.makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "')"
						+ " and polno=mainpolno";
				SQLwithBindVariables sqlbv77 = new SQLwithBindVariables();
				sqlbv77.sql(strSQL);
				sqlbv77.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv77.put("strStartDate", strStartDate);
				sqlbv77.put("strEndDate", strEndDate);
				sqlbv77.put("strMngCom", strMngCom);
				sqlbv77.put("defaultoperator", allSSRS.GetText(i + 1, 6));
				ssrs1 = exeSQL1.execSQL(sqlbv77);
				tCount[3] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 标准承保件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and uwdate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and exists (select 'X'"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and (missionprop1) = trim(lcpol.prtno) "
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and (missionprop1) = trim(lcpol.prtno) "
						+ " and defaultoperator = '"
						+ "?defaultoperator?"
						+ "'"
						+ " union"
						+ " select 'X'"
						+ " from lwmission"
//						+ " where activityid = '0000001100' and (missionprop1) = trim(lcpol.prtno) "
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and (missionprop1) = trim(lcpol.prtno) "
						+ " and defaultoperator = '"
						+ "?defaultoperator?"
						+ "')"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))"
						+ " and exists (select 'X'"
						+ " from lccont"
						+ " where contno = lcpol.contno" + " and uwflag = '9')";
				SQLwithBindVariables sqlbv78 = new SQLwithBindVariables();
				sqlbv78.sql(strSQL);
				sqlbv78.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv78.put("strStartDate", strStartDate);
				sqlbv78.put("strEndDate", strEndDate);
				sqlbv78.put("strMngCom", strMngCom);
				sqlbv78.put("defaultoperator", allSSRS.GetText(i + 1, 6));
				ssrs1 = exeSQL1.execSQL(sqlbv78);
				tCount[4] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 加费件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and exists (select 'X'"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and (missionprop1) = trim(lcpol.prtno) "
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and (missionprop1) = trim(lcpol.prtno) "
						+ " and defaultoperator = '"
						+ "?defaultoperator?"
						+ "'"
						+ " union"
						+ " select 'X'"
						+ " from lwmission"
//						+ " where activityid = '0000001100' and (missionprop1) = trim(lcpol.prtno) "
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and (missionprop1) = trim(lcpol.prtno) "
						+ " and defaultoperator = '"
						+ "?defaultoperator?"
						+ "')"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))"
						+ " and exists (select 'X'"
						+ " from lcprem"
						+ " where lcprem.polno=lcpol.polno"
						+ " and lcprem.payplantype in ('01','02') and lcprem.operator='"
						+ "?defaultoperator?"
						+ "' "
						+ " and lcprem.makedate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and managecom like concat('"
						+ "?strMngCom?"
						+ "','%'))";
				SQLwithBindVariables sqlbv79 = new SQLwithBindVariables();
				sqlbv79.sql(strSQL);
				sqlbv79.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv79.put("strStartDate", strStartDate);
				sqlbv79.put("strEndDate", strEndDate);
				sqlbv79.put("strMngCom", strMngCom);
				sqlbv79.put("defaultoperator", allSSRS.GetText(i + 1, 6));
				ssrs1 = exeSQL1.execSQL(sqlbv79);
				tCount[5] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 加费成功件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and signdate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " and exists (select 'X'"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and (missionprop1) = trim(lcpol.prtno) "
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and (missionprop1) = trim(lcpol.prtno) "
						+ " and defaultoperator = '"
						+ "?defaultoperator?"
						+ "'"
						+ " union"
						+ " select 'X'"
						+ " from lwmission"
//						+ " where activityid = '0000001100' and (missionprop1) = trim(lcpol.prtno) "
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and (missionprop1) = trim(lcpol.prtno) "
						+ " and defaultoperator = '"
						+ "?defaultoperator?"
						+ "')"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))"
						// + " and uwflag='3'"
						+ " and exists (select 'X'"
						+ " from lcprem"
						+ " where lcprem.polno=lcpol.polno"
						+ " and lcprem.payplantype in ('01','02') and lcprem.operator='"
						+ "?defaultoperator?"
						+ "'"
						+ " and managecom like concat('"
						+ "?strMngCom?"
						+ "','%'))";
				SQLwithBindVariables sqlbv80 = new SQLwithBindVariables();
				sqlbv80.sql(strSQL);
				sqlbv80.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv80.put("strStartDate", strStartDate);
				sqlbv80.put("strEndDate", strEndDate);
				sqlbv80.put("strMngCom", strMngCom);
				sqlbv80.put("defaultoperator", allSSRS.GetText(i + 1, 6));
				ssrs1 = exeSQL1.execSQL(sqlbv80);
				tCount[6] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 特别约定件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and exists (select 'X'"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and (missionprop1) = trim(lcpol.prtno) "
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and (missionprop1) = trim(lcpol.prtno) "
						+ " and defaultoperator = '"
						+ "?defaultoperator?"
						+ "'"
						+ " union"
						+ " select 'X'"
						+ " from lwmission"
//						+ " where activityid = '0000001100' and (missionprop1) = trim(lcpol.prtno) "
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and (missionprop1) = trim(lcpol.prtno) "
						+ " and defaultoperator = '"
						+ "?defaultoperator?"
						+ "')"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))"
						+ " and exists(select 'x'"
						+ " from lcspec"
						+ " where contno = lcpol.contno"
						+ " and operator = '"
						+ "?defaultoperator?" + "'" + " )";
				SQLwithBindVariables sqlbv81 = new SQLwithBindVariables();
				sqlbv81.sql(strSQL);
				sqlbv81.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv81.put("strMngCom", strMngCom);
				sqlbv81.put("defaultoperator", allSSRS.GetText(i + 1, 6));
				ssrs1 = exeSQL1.execSQL(sqlbv81);
				tCount[7] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 拒保件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and uwdate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and exists (select 'X'"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and (missionprop1) = trim(lcpol.prtno) "
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and (missionprop1) = trim(lcpol.prtno) "
						+ " and defaultoperator = '"
						+ "?defaultoperator?"
						+ "'"
						+ " union"
						+ " select 'X'"
						+ " from lwmission"
//						+ " where activityid = '0000001100' and (missionprop1) = trim(lcpol.prtno) "
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and (missionprop1) = trim(lcpol.prtno) "
						+ " and defaultoperator = '"
						+ "?defaultoperator?"
						+ "')"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))"
						+ " and uwflag='1'";
				SQLwithBindVariables sqlbv82 = new SQLwithBindVariables();
				sqlbv82.sql(strSQL);
				sqlbv82.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv82.put("strStartDate", strStartDate);
				sqlbv82.put("strEndDate", strEndDate);
				sqlbv82.put("strMngCom", strMngCom);
				sqlbv82.put("defaultoperator", allSSRS.GetText(i + 1, 6));
				ssrs1 = exeSQL1.execSQL(sqlbv82);
				tCount[8] = ssrs1.GetText(1, 1);
				// //////////////////////////////////////////////////////////////////
				// 延期件数
				// //////////////////////////////////////////////////////////////////
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and uwdate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and (Months_between(paytodate, cvalidate) <= 12 or payintv = 0 or paytodate is null)"
						+ " and char_length(trim(contno)) <>17"
						+ " and exists (select 'X'"
						+ " from lbmission"
//						+ " where activityid = '0000001100' and (missionprop1) = trim(lcpol.prtno) "
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and (missionprop1) = trim(lcpol.prtno) "
						+ " and defaultoperator = '"
						+ "?defaultoperator?"
						+ "'"
						+ " union"
						+ " select 'X'"
						+ " from lwmission"
//						+ " where activityid = '0000001100' and (missionprop1) = trim(lcpol.prtno) "
						+ " where activityid  in (select activityid from lwactivity  where functionid ='10010028') and (missionprop1) = trim(lcpol.prtno) "
						+ " and defaultoperator = '"
						+ "?defaultoperator?"
						+ "')"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where lccont.contno = lcpol.contno"
						+ " and (lccont.cvalidate = lcpol.cvalidate or lccont.cvalidate is null))"
						+ " and uwflag='2'";
				SQLwithBindVariables sqlbv83 = new SQLwithBindVariables();
				sqlbv83.sql(strSQL);
				sqlbv83.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv83.put("strStartDate", strStartDate);
				sqlbv83.put("strEndDate", strEndDate);
				sqlbv83.put("strMngCom", strMngCom);
				sqlbv83.put("defaultoperator", allSSRS.GetText(i + 1, 6));
				ssrs1 = exeSQL1.execSQL(sqlbv83);
				tCount[9] = ssrs1.GetText(1, 1);
				/**
				 * ============================================================================
				 * 修改原因：由于查询数据比较多，打印非常慢，所以，现将需要打印的数据查出存储在一个一个临时表
				 * 中，然后再去临时表中去数据打印。可以在存储数据的同时做其他 的事情 修改人： 万泽辉 修改时间： 2005/12/22
				 * 16:00
				 * =============================================================================
				 */
				// 准备需要暂存在 llrepcolligate表中的数据
				if (tOperate != null && tOperate.equals("SAVE")) {
					LLRepColligateSchema tLLRepColligateSchema = new LLRepColligateSchema();
					String tRepID = "UWREPORTSE600" + String.valueOf(i);
					if (tRepID == null || tRepID.equals("")) {
						buildError("tRepID", "生成[核保员质量评审]编号失败！");
						return false;
					}
					String mStartDate = strStartDate.substring(0, 4)
							+ strStartDate.substring(5, 7)
							+ strStartDate.substring(8, 10);
					String mEndDate = strEndDate.substring(0, 4)
							+ strEndDate.substring(5, 7)
							+ strEndDate.substring(8, 10);

					String tRepName = "核保员质量评审";
					String tColID = String.valueOf(i);
					String tColName = "";
					tLLRepColligateSchema.setRepID(tRepID); // 报表编号
					tLLRepColligateSchema.setRepName(tRepName); // 报表名称
					tLLRepColligateSchema.setColID(tColID); // 栏目号
					tLLRepColligateSchema.setColName(tColName); // 栏目名称
					tLLRepColligateSchema.setEndDate(mEndDate); // 结束时间
					tLLRepColligateSchema.setStartDate(mStartDate); // 开始时间
					tLLRepColligateSchema.setMakeDate(mCurrentDate); // 最后一次修改日
					tLLRepColligateSchema.setMakeTime(mCurrentTime);
					tLLRepColligateSchema.setModifyDate(mCurrentDate);
					tLLRepColligateSchema.setModifyTime(mCurrentTime); // 最后一次修改时间
					tLLRepColligateSchema.setOperator(mOperator); // 操作人
					tLLRepColligateSchema.setMngCom(strMngCom); // 管理机构代码
					tLLRepColligateSchema.setMngComName(name); // 管理机构名称
					tLLRepColligateSchema.setC1(allSSRS.GetText(i + 1, 1)); // 险种编码
					tLLRepColligateSchema.setC2(allSSRS.GetText(i + 1, 2)); // 险种名称
					tLLRepColligateSchema.setC3(allSSRS.GetText(i + 1, 3)); // 主险或者附加险
					tLLRepColligateSchema.setC4(allSSRS.GetText(i + 1, 4)); // 险种分类值
					tLLRepColligateSchema.setC5(tCount[0]); // 查询预收录入件数
					tLLRepColligateSchema.setC6(tCount[1]); // 人工核保件数
					tLLRepColligateSchema.setC7(tCount[2]); // 体检件数
					tLLRepColligateSchema.setC8(tCount[3]); // 核保问题件数
					tLLRepColligateSchema.setC9(tCount[4]); // 标准承保件数
					tLLRepColligateSchema.setC10(tCount[5]); // 加费件数
					tLLRepColligateSchema.setC11(tCount[6]); // 加费成功件数
					tLLRepColligateSchema.setC12(tCount[7]); // 特别约定件数
					tLLRepColligateSchema.setC13(tCount[8]); // 拒保件数
					tLLRepColligateSchema.setC14(tCount[9]); // 延期件数
					tLLRepColligateSchema.setC15(allSSRS.GetText(i + 1, 5)); // 核保员名称
					mMMap.put(tLLRepColligateSchema, "DELETE&INSERT");

				}
				if (tOperate != null && tOperate.equals("PRINT")) {
					String mStartDate = strStartDate.substring(0, 4)
							+ strStartDate.substring(5, 7)
							+ strStartDate.substring(8, 10);
					String mEndDate = strEndDate.substring(0, 4)
							+ strEndDate.substring(5, 7)
							+ strEndDate.substring(8, 10);

					String tempSQL = "select mngcom, mngcomname,"
							+ " c15,c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,"
							+ " c12,c13,c14 "
							+ " from llrepcolligate where 1=1 and "
							+ " repid like 'UWREPORTSE6%' and repname = '核保员质量评审' and "
							+ " mngcom = '" + "?strMngCom?" + "' and " // 管理机构代码
							+ " startdate = '" + "?mStartDate?" + "' and " // 开始时间
							+ " enddate = '" + "?mEndDate?" + "'  order by c15,c1" // 结束时间
					;
					SQLwithBindVariables sqlbv84 = new SQLwithBindVariables();
					sqlbv84.sql(tempSQL);
					sqlbv84.put("strMngCom", strMngCom);
					sqlbv84.put("mStartDate", mStartDate);
					sqlbv84.put("mEndDate", mEndDate);
					ExeSQL tempExeSQL = new ExeSQL();
					SSRS temmSSRS = new SSRS();
					temmSSRS = tempExeSQL.execSQL(sqlbv84);

					if (temmSSRS.getMaxRow() == 0) {
						buildError("tLLRepColligateSchema", "没有得到打印信息！");
						return false;
					}
					for (int n = 1; n <= temmSSRS.getMaxRow(); n++) {
						String[] cols1 = new String[18];
						cols1[0] = temmSSRS.GetText(n, 1);
						cols1[1] = temmSSRS.GetText(n, 2);
						cols1[2] = temmSSRS.GetText(n, 3);
						cols1[3] = temmSSRS.GetText(n, 4);
						cols1[4] = temmSSRS.GetText(n, 5);
						cols1[5] = temmSSRS.GetText(n, 6);
						cols1[6] = temmSSRS.GetText(n, 7);
						cols1[8] = temmSSRS.GetText(n, 8);
						cols1[9] = temmSSRS.GetText(n, 9);
						cols1[10] = temmSSRS.GetText(n, 10);
						cols1[11] = temmSSRS.GetText(n, 11);
						cols1[12] = temmSSRS.GetText(n, 12);
						cols1[13] = temmSSRS.GetText(n, 13);
						cols1[14] = temmSSRS.GetText(n, 14);
						cols1[15] = temmSSRS.GetText(n, 15);
						cols1[16] = temmSSRS.GetText(n, 16);
						cols1[17] = temmSSRS.GetText(n, 17);
						// cols1[17] = temmSSRS.GetText(n, 18);
						tlistTable.add(cols1);

					}
					String[] col = new String[1];
					xmlexport.addListTable(tlistTable, col);
					mResult.clear();
					mResult.addElement(xmlexport);
					return true;
				}

			}
		}

		if (tUWStatType.equals("7")) {
			XmlExport xmlexport = new XmlExport(); // Create a XmlExport
			xmlexport.createDocument("cxRate.vts", "printer");
			String name = "";
			String sql = "select name from ldcom where comcode='" + "?strMngCom?"
					+ "'";
			SQLwithBindVariables sqlbv85 = new SQLwithBindVariables();
			sqlbv85.sql(sql);
			sqlbv85.put("strMngCom", strMngCom);
			ExeSQL exeSQL = new ExeSQL();
			SSRS ssrs = exeSQL.execSQL(sqlbv85);
			name = ssrs.GetText(1, 1);
			logger.debug("开始执行strOperation＝" + tUWStatType + "的sql操作");

			String strSQL = "";
			ExeSQL exeSQL1 = new ExeSQL();
			SSRS ssrs1 = new SSRS();
			FDate tFDate = new FDate();
			Date tStartDate = tFDate.getDate(strStartDate);
			Date tEndDate = tFDate.getDate(strEndDate);

			// 对于每一个险种进行循环判断相关的取值
			for (int i = 0; i < allRiskCount; i++) {
				String[] tCount = new String[10];
				// 有效承保件数
				// //////////////////////////////////////////////////////////////////
				// ∑(M+N)/n
				// //////////////////////////////////////////////////////////////////

				// for (int j = 0; j < tEndDate.getMonth(); j++) {
				// String aEndDate = PubFun.calDate(strEndDate, -j, "M",
				// null);
				strSQL = "select count(distinct contno) from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and managecom like concat('"
						+ "?strMngCom?"
						+ "','%')"
						+ " and polno=mainpolno"
						+ " and not exists (select 'X' from lpedoritem p where edorstate = '0' "
						+ " and edortype = 'CT' and contno = lcpol.contno)"
						+ " and signdate > '"
						+ "?date1?"
						+ "' and signdate <= '" + "?strEndDate?" + "'";
				SQLwithBindVariables sqlbv86 = new SQLwithBindVariables();
				sqlbv86.sql(strSQL);
				sqlbv86.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv86.put("strMngCom", strMngCom);
				sqlbv86.put("strEndDate", strEndDate);
				sqlbv86.put("date1", PubFun.calDate(strEndDate, -24, "M", null));
				// 用签单日期去查------hp
				ssrs1 = exeSQL1.execSQL(sqlbv86);
				tCount[0] = ssrs1.GetText(1, 1);

				// //////////////////////////////////////////////////////////////////
				// 出险件数
				// //////////////////////////////////////////////////////////////////
				// strSQL = " select count(distinct LLClaimPolicy.Clmno)"
				// + " from llregister, LLClaimUWMain, LLClaimPolicy"
				// + " where llregister.rgtno = LLClaimUWMain.Rgtno"
				// + " and LLClaimUWMain.Examdate > '"
				// + PubFun.calDate(strEndDate, -24, "M", null)
				// + "' and LLClaimUWMain.Examdate <= '" + strEndDate + "'"
				// + " and llregister.clmstate in ('50', '60')"
				// + " and LLClaimPolicy.Clmno = llregister.rgtno"
				// + " and LLClaimPolicy.Riskcode = '"
				// + allSSRS.GetText(i + 1, 1) + "'"
				// // + " and LLClaimPolicy.Givetype in ('0', '1')"
				// + " and LLClaimUWMain.Examconclusion='0'"
				// + " and exists(select 'X' from lcpol where "
				// + " lcpol.polno=LLClaimPolicy.polno and "
				// + " signdate> '"+PubFun.calDate(strEndDate, -24, "M", null)+"' "
				// + " and signdate<='"+strEndDate+"' and managecom like '"+strMngCom+"%') "
				// + " group by LLClaimPolicy.Givetype ";
				// ssrs1 = exeSQL1.execSQL(strSQL);
				// String count1=ssrs1.GetText(1, 1);
				// String count2=ssrs1.GetText(2,1);
				// String count3=ssrs1.GetText(3,1);
				// int
				// count=Integer.parseInt(count1)+Integer.parseInt(count2)+Integer.parseInt(count3);
				//
				// tCount[1]=String.valueOf(count);//出险件数
				// tCount[2]=count1;//给付件数
				// tCount[3]=count2;///拒付件数

				strSQL = " select count(distinct LLClaimPolicy.Clmno)"
						+ " from llregister, LLClaimUWMain, LLClaimPolicy"
						+ " where llregister.rgtno = LLClaimUWMain.Rgtno"
						+ " and LLClaimUWMain.Examdate > '" + "?strStartDate?"
						+ "' and LLClaimUWMain.Examdate <= '" + "?strEndDate?"
						+ "'" + " and llregister.clmstate in ('50', '60')"
						+ " and LLClaimPolicy.Clmno = llregister.rgtno"
						+ " and LLClaimPolicy.Riskcode = '"
						+ "?riskcode?" + "'"
						+ " and LLClaimPolicy.Givetype in ('0', '1')"
						+ " and LLClaimUWMain.Examconclusion='0'"
						+ " and exists(select 'X' from lcpol where "
						+ " lcpol.polno=LLClaimPolicy.polno and "
						+ " signdate> '" + "?strStartDate?" + "' "
						+ "  and signdate<='" + "?strEndDate?"
						+ "' and managecom like concat('" + "?strMngCom?" + "','%')) ";
				SQLwithBindVariables sqlbv87 = new SQLwithBindVariables();
				sqlbv87.sql(strSQL);
				sqlbv87.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv87.put("strStartDate", strStartDate);
				sqlbv87.put("strEndDate", strEndDate);
				sqlbv87.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv87);
				tCount[1] = ssrs1.GetText(1, 1);

				// //////////////////////////////////////////////////////////////////
				// 给付件数
				// //////////////////////////////////////////////////////////////////
				strSQL = " select count(distinct LLClaimPolicy.Clmno)"
						+ " from llregister, LLClaimUWMain, LLClaimPolicy"
						+ " where llregister.rgtno = LLClaimUWMain.Rgtno"
						+ " and LLClaimUWMain.Examdate > '" + "?strStartDate?"
						+ "' and LLClaimUWMain.Examdate <= '" + "?strEndDate?"
						+ "'" + " and llregister.clmstate in ('50', '60')"
						+ " and LLClaimPolicy.Clmno = llregister.rgtno"
						+ " and LLClaimPolicy.Riskcode = '"
						+ "?riskcode?" + "'"
						+ " and LLClaimPolicy.Givetype = '0'"
						+ " and LLClaimUWMain.Examconclusion='0'"
						+ " and exists(select 'X' from lcpol where "
						+ " lcpol.polno=LLClaimPolicy.polno and "
						+ " signdate> '" + "?strStartDate?" + "' "
						+ "  and signdate<='" + "?strEndDate?"
						+ "' and managecom like concat('" + "?strMngCom?" + "','%')) ";
				SQLwithBindVariables sqlbv88 = new SQLwithBindVariables();
				sqlbv88.sql(strSQL);
				sqlbv88.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv88.put("strStartDate", strStartDate);
				sqlbv88.put("strEndDate", strEndDate);
				sqlbv88.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv88);
				tCount[2] = ssrs1.GetText(1, 1);

				// //////////////////////////////////////////////////////////////////
				// 拒付件数
				// //////////////////////////////////////////////////////////////////

				strSQL = " select count(distinct LLClaimPolicy.Clmno)"
						+ " from llregister, LLClaimUWMain, LLClaimPolicy"
						+ " where llregister.rgtno = LLClaimUWMain.Rgtno"
						+ " and LLClaimUWMain.Examdate > '" + "?strStartDate?"
						+ "' and LLClaimUWMain.Examdate <= '" + "?strEndDate?"
						+ "'" + " and llregister.clmstate in ('50', '60')"
						+ " and LLClaimPolicy.Clmno = llregister.rgtno"
						+ " and LLClaimPolicy.Riskcode = '"
						+ "?riskcode?" + "'"
						+ " and LLClaimPolicy.Givetype = '1'"
						+ " and LLClaimUWMain.Examconclusion='0'"
						+ " and exists(select 'X' from lcpol where "
						+ " lcpol.polno=LLClaimPolicy.polno and "
						+ " signdate> '" + "?strStartDate?" + "' "
						+ "  and signdate<='" + "?strEndDate?"
						+ "' and managecom like concat('" + "?strMngCom?" + "','%')) ";
				SQLwithBindVariables sqlbv89 = new SQLwithBindVariables();
				sqlbv89.sql(strSQL);
				sqlbv89.put("riskcode", allSSRS.GetText(i + 1, 1));
				sqlbv89.put("strStartDate", strStartDate);
				sqlbv89.put("strEndDate", strEndDate);
				sqlbv89.put("strMngCom", strMngCom);
				ssrs1 = exeSQL1.execSQL(sqlbv89);
				tCount[3] = ssrs1.GetText(1, 1);

				// //////////////////////////////////////////////////////////////////
				// 长险出险率
				// //////////////////////////////////////////////////////////////////
				DecimalFormat tDecimalFormat = new DecimalFormat("0.0000");
				tCount[4] = tDecimalFormat
						.format((Double.parseDouble(tCount[1]) / Double
								.parseDouble(tCount[0])) * 100)
						+ "%";

				String[] cols1 = new String[11];
				cols1[0] = strMngCom; // 管理机构代码
				cols1[1] = name; // 管理机构名称
				cols1[2] = allSSRS.GetText(i + 1, 1); // 险种编码
				cols1[3] = allSSRS.GetText(i + 1, 2); // 险种名称
				cols1[4] = allSSRS.GetText(i + 1, 3); // 主险或者附加险
				cols1[5] = allSSRS.GetText(i + 1, 4); // 险种分类值
				cols1[6] = tCount[0]; // 有效承保件数
				cols1[7] = tCount[1]; // 出险件数
				cols1[8] = tCount[2]; // 给付件数
				cols1[9] = tCount[3]; // 拒付件数
				cols1[10] = tCount[4]; // 长险出险率

				tlistTable.add(cols1);

			}

			String[] col = new String[1];
			xmlexport.addListTable(tlistTable, col);

			mResult.clear();
			mResult.addElement(xmlexport);
			return true;

		}

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mInputData.clear();
		mInputData.add(mMMap);
		mResult.add(mMMap);
		return true;
	}

	/**
	 * 提交数据
	 * 
	 * @return boolean
	 */
	private boolean pubSubmit() {
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			this.mErrors.addOneError("数据提交失败,原因"
					+ tPubSubmit.mErrors.getError(0).errorMessage);
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		UWStaticPrintSEBL tUWStaticPrintSEUI = new UWStaticPrintSEBL();
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86";
		String mOperate = "SAVE";
		VData tVData = new VData();
		tVData.addElement("8632");
		tVData.addElement("2005-12-23");
		tVData.addElement("2005-12-25");
		tVData.addElement("7");
		tVData.addElement("001");

		tUWStaticPrintSEUI.submitData(tVData, mOperate);
		// FDate tFDate = new FDate();
		// logger.debug(PubFun.calInterval(tFDate.getDate("2006-02-26"),
		// tFDate.getDate("2006-03-25"), "D"));
		// logger.debug("***Date=" + PubFun.calDate("2006-01-25", -24, "M",
		// null));
	}

	public CErrors getErrors() {
		return null;
	}

}
