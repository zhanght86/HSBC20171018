package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

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
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author 刘岩松
 * function :print InputEfficiency or print CheckEfficiency Business Logic layer
 * @version 1.0
 * @date 2003-04-04
 */

public class WorkMissionPrintBL {
private static Logger logger = Logger.getLogger(WorkMissionPrintBL.class);
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

	public WorkMissionPrintBL() {
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
		cError.moduleName = "WorkMissionPrintBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/***************************************************************************
	 * name : getPrintData() function :get print data parameter : null return
	 * :true or false
	 */

	private boolean getPrintData() {
		XmlExport xmlexport = new XmlExport();
		xmlexport.createDocument("WorkMissionSatic.vts", "printer"); // appoint
																		// to a
																		// special
																		// output
																		// .vts
																		// file
		String[] cols = new String[19];
		ListTable tlistTable = new ListTable();
		tlistTable.setName("MODE");

		String SysDate = PubFun.getCurrentDate();

		String[] col = new String[19];
		String strSQL; // choose all Operator from LCPol between start date and
						// end date
		String strSQL1;
		String strSQL2;
		String strSQL3;
		String strSQL4;
		String strSQL5;
		String strSQL6;
		String strSQL7;
		String strSQL8;
		String strSQL9;
		String strSQL10;
		String strSQL11;
		String strSQL12;
		String strSQL13;
		String strSQL14;
		String strSQL15;
		String strSQL16;
		String strSQL17;
		String strSQL18;
		if (strSaleChnl.equals("1")) {
			// 扫描
			strSQL = "select count(*) from es_doc_main where "
					+ " managecom like concat('" + "?strMngCom?"
					+ "','%') and makedate between '" + "?strStartDate?" + "' and '"
					+ "?strEndDate?" + "' and subtype in('UA001','UA015')";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(strSQL);
			sqlbv1.put("strMngCom", strMngCom);
			sqlbv1.put("strStartDate", strStartDate);
			sqlbv1.put("strEndDate", strEndDate);
			logger.debug("开始执行sql操作" + strSQL);
			ExeSQL exeSQL = new ExeSQL();
			SSRS ssrs = exeSQL.execSQL(sqlbv1);
			int i_count = ssrs.getMaxRow();
			logger.debug("i_count的值" + i_count);
			if (i_count != 0) {
				logger.debug(ssrs.GetText(1, 1));
				col[0] = ssrs.GetText(1, 1);
			} else {
				col[0] = "0";
			}

			// 等待录单
//			strSQL1 = "select count(*) from lwmission where ((activityid ='0000001098' and missionprop5 ='TB01') or (activityid='0000001099' and missionprop5 in ( '01','07','08','09','10','45'))) "
//					+ " and defaultoperator is null "
//					/*
//					 * +" and makedate between '" + strStartDate + "' and '" +
//					 * strEndDate + "'
//					 */
//					+ " and missionprop3 like '"
//					+ strMngCom
//					+ "%' and processid = '0000000003'  ";
			//修改为用lwactivity.functionid 替换 activityid，此时processid可不用。2013-04-23 lzf
			strSQL1 = "select count(*) from lwmission where ((activityid in (select activityid from lwactivity  where functionid = '10010001') and missionprop5 ='TB01') "
					+" or (activityid in (select activityid from lwactivity  where functionid = '10010002') and missionprop5 in ( '01','07','08','09','10','45'))) "
					+ " and defaultoperator is null "
					/*
					 * +" and makedate between '" + strStartDate + "' and '" +
					 * strEndDate + "'
					 */
					+ " and missionprop3 like concat('"
					+ "?strMngCom?"
					+ "','%') ";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(strSQL1);
			sqlbv2.put("strMngCom", strMngCom);
			logger.debug("开始执行sql1操作" + strSQL1);
			ExeSQL exeSQL1 = new ExeSQL();
			SSRS ssrs1 = exeSQL1.execSQL(sqlbv2);
			int i_count1 = ssrs1.getMaxRow();
			logger.debug("i_count1的值" + i_count1);
			if (i_count1 == 0) {
				col[1] = "0";
			} else {
				logger.debug(ssrs1.GetText(1, 1));
				col[1] = ssrs1.GetText(1, 1);
			}

			// 录单中
//			strSQL14 = "select count(*) from lwmission where ((activityid ='0000001098' and missionprop5 ='TB01') or (activityid='0000001099' and missionprop5 in ( '01','07','08','09','10','45')))"
//					+ " and defaultoperator is not null"
//					+ " and missionprop3 like'"
//					+ strMngCom
//					+ "%' and processid = '0000000003' ";
			strSQL14 = "select count(*) from lwmission where ((activityid in (select activityid from lwactivity  where functionid = '10010001') and missionprop5 ='TB01') "
					+" or (activityid in (select activityid from lwactivity  where functionid = '10010002') and missionprop5 in ( '01','07','08','09','10','45')))"
					+ " and defaultoperator is not null"
					+ " and missionprop3 like concat('"
					+ "?strMngCom?"
					+ "','%')";
			// +" and makedate between '" +
			// strStartDate + "' and '" + strEndDate + "' ";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(strSQL14);
			sqlbv3.put("strMngCom", strMngCom);
			logger.debug("开始执行sql14操作" + strSQL14);
			SSRS ssrs14 = exeSQL1.execSQL(sqlbv3);
			int i_count14 = ssrs14.getMaxRow();
			logger.debug("i_count14的值" + i_count14);
			if (i_count14 == 0) {
				col[2] = "0";
			} else {
				logger.debug(ssrs14.GetText(1, 1));
				col[2] = ssrs14.GetText(1, 1);
			}

			// 录单完成

//			strSQL2 = " select count(*) from lbmission where ((activityid ='0000001098' and missionprop5 ='TB01') or (activityid='0000001099' and missionprop5 in ( '01','07','08','09','10','45')))"
			strSQL2 = " select count(*) from lbmission where ((activityid in (select activityid from lwactivity  where functionid = '10010001') and missionprop5 ='TB01') "
					+" or (activityid in (select activityid from lwactivity  where functionid = '10010002') and missionprop5 in ( '01','07','08','09','10','45')))"		
					+ " and MissionProp3 like concat('"
					+ "?strMngCom?"
					+ "','%') and outdate between '"
					+ "?strStartDate?"
					+ "' and '"
					+ "?strEndDate?" + "' ";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(strSQL2);
			sqlbv4.put("strMngCom", strMngCom);
			sqlbv4.put("strStartDate", strStartDate);
			sqlbv4.put("strEndDate", strEndDate);
			logger.debug("开始执行sql2操作" + strSQL2);
			ExeSQL exeSQL2 = new ExeSQL();
			SSRS ssrs2 = exeSQL2.execSQL(sqlbv4);
			int i_count2 = ssrs2.getMaxRow();
			logger.debug("i_count2的值" + i_count2);
			if (i_count2 == 0) {
				col[3] = "0";
			} else {
				logger.debug(ssrs2.GetText(1, 1));
				col[3] = ssrs2.GetText(1, 1);
			}

			// 等待问题件修改
//			strSQL3 = "select count(*) from lwmission where activityid='0000001002' and processid = '0000000003' "
			strSQL3 = "select count(*) from lwmission where activityid in (select activityid from lwactivity  where functionid = '10010004') "
					+ " and defaultoperator is null"
					+ " and MissionProp5 like concat('"
					+ "?strMngCom?"
					+ "','%')"
					// + "and makedate between '" +
					// strStartDate + "' and '" + strEndDate + "' "
					+ " and exists (select 'X' from lbmission where lbmission.missionid=lwmission.missionid and "
//					+ " ((activityid ='0000001098' and missionprop5 ='TB01') or (activityid='0000001099' and missionprop5 in ( '01','07','08','09','10','45')))) ";
					+ " ((activityid in (select activityid from lwactivity  where functionid = '10010001') and missionprop5 ='TB01') "
					+" or (activityid in (select activityid from lwactivity  where functionid = '10010002') and missionprop5 in ( '01','07','08','09','10','45')))) ";
			logger.debug("开始执行sql3操作" + strSQL3);
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(strSQL3);
			sqlbv5.put("strMngCom", strMngCom);
			ExeSQL exeSQL3 = new ExeSQL();
			SSRS ssrs3 = exeSQL3.execSQL(sqlbv5);
			int i_count3 = ssrs3.getMaxRow();
			logger.debug("i_count3的值" + i_count3);
			if (i_count3 == 0) {
				col[4] = "0";
			} else {
				logger.debug(ssrs3.GetText(1, 1));
				col[4] = ssrs3.GetText(1, 1);
			}

			// 问题件修改中

			strSQL15 = "select count(*) from lwmission where activityid in (select activityid from lwactivity  where functionid = '10010004') "
					+ " and defaultoperator is not null"
					+ " and MissionProp5 like concat('"
					+ "?strMngCom?"
					+ "','%')"
					// +"and makedate between '" +
					// strStartDate + "' and '" + strEndDate + "' "
					+ " and exists (select 'X' from lbmission where lbmission.missionid=lwmission.missionid and "
//					+ " ((activityid ='0000001098' and lbmission.missionprop5 ='TB01') or (activityid='0000001099' and missionprop5 in ( '01','07','08','09','10','45'))))";
					+ " ((activityid in (select activityid from lwactivity  where functionid = '10010001') and lbmission.missionprop5 ='TB01') or (activityid in (select activityid from lwactivity  where functionid = '10010002') and missionprop5 in ( '01','07','08','09','10','45'))))";
			logger.debug("开始执行sql5操作" + strSQL15);
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(strSQL15);
			sqlbv6.put("strMngCom", strMngCom);
			SSRS ssrs15 = exeSQL3.execSQL(sqlbv6);
			int i_count15 = ssrs15.getMaxRow();
			logger.debug("i_count15的值" + i_count15);
			if (i_count15 == 0) {
				col[5] = "0";
			} else {
				logger.debug(ssrs15.GetText(1, 1));
				col[5] = ssrs15.GetText(1, 1);
			}

			// 问题件修改完成
			strSQL4 = "select count(*) from lbmission a where activityid in (select activityid from lwactivity  where functionid = '10010004') "
					+ " and a.MissionProp5 like concat('"
					+ "?strMngCom?"
					+ "','%') and a.outdate between '"
					+ "?strStartDate?"
					+ "' and '"
					+ "?strEndDate?"
					+ "' and exists (select 'X' from lbmission b where b.missionid=a.missionid and "
//					+ " ((activityid ='0000001098' and missionprop5 ='TB01') or (activityid='0000001099' and missionprop5 in ( '01','07','08','09','10','45')))) ";
					+ " ((activityid in (select activityid from lwactivity  where functionid = '10010001') and missionprop5 ='TB01') or (activityid in (select activityid from lwactivity  where functionid = '10010002') and missionprop5 in ( '01','07','08','09','10','45')))) ";
			logger.debug("开始执行sql4操作" + strSQL4);
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql(strSQL4);
			sqlbv7.put("strMngCom", strMngCom);
			sqlbv7.put("strStartDate", strStartDate);
			sqlbv7.put("strEndDate", strEndDate);
			ExeSQL exeSQL4 = new ExeSQL();
			SSRS ssrs4 = exeSQL4.execSQL(sqlbv7);
			int i_count4 = ssrs4.getMaxRow();
			logger.debug("i_count4的值" + i_count4);
			if (i_count4 == 0) {
				col[6] = "0";
			} else {
				logger.debug(ssrs4.GetText(1, 1));
				col[6] = ssrs4.GetText(1, 1);
			}

			// 等待复核
//			strSQL5 = "select count(*) from lwmission where activityid='0000001001' and processid = '0000000003' "
			strSQL5 = "select count(*) from lwmission where activityid in (select activityid from lwactivity  where functionid = '10010003') "
					+ " and defaultoperator is null"
					// + " and makedate between '" +
					// strStartDate + "' and '" + strEndDate + "'"
					+ " and MissionProp8 like concat('"
					+ "?strMngCom?"
					+ "','%')"
					+ "  and exists (select 'X' from lbmission where lbmission.missionid=lwmission.missionid and "
//					+ " ((activityid ='0000001098' and missionprop5 ='TB01') or (activityid='0000001099' and missionprop5 in ( '01','07','08','09','10','45')))) ";
					+ " ((activityid in (select activityid from lwactivity  where functionid = '10010001') and missionprop5 ='TB01') or (activityid in (select activityid from lwactivity  where functionid = '10010002') and missionprop5 in ( '01','07','08','09','10','45')))) ";
			logger.debug("开始执行sql5操作" + strSQL5);
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql(strSQL5);
			sqlbv8.put("strMngCom", strMngCom);
			ExeSQL exeSQL5 = new ExeSQL();
			SSRS ssrs5 = exeSQL5.execSQL(sqlbv8);
			int i_count5 = ssrs5.getMaxRow();
			logger.debug("i_count5的值" + i_count5);
			if (i_count5 == 0) {
				col[7] = "0";
			} else {
				logger.debug(ssrs5.GetText(1, 1));
				col[7] = ssrs5.GetText(1, 1);
			}

			// 复核中
//			strSQL16 = "select count(*) from lwmission where activityid='0000001001' and processid = '0000000003' "
			strSQL16 = "select count(*) from lwmission where activityid in (select activityid from lwactivity  where functionid = '10010003') "
					+ " and defaultoperator is not null"
					// + " and makedate between '" +
					// strStartDate + "' and '" + strEndDate + "' "
					+ " and MissionProp8 like concat('"
					+ "?strMngCom?"
					+ "','%')"
					+ "and exists (select 'X' from lbmission where lbmission.missionid=lwmission.missionid and "
//					+ " ((activityid ='0000001098' and missionprop5 ='TB01') or (activityid='0000001099' and missionprop5 in ( '01','07','08','09','10','45'))))";
					+ " ((activityid in (select activityid from lwactivity  where functionid = '10010001') and missionprop5 ='TB01') or (activityid in (select activityid from lwactivity  where functionid = '10010002') and missionprop5 in ( '01','07','08','09','10','45'))))";
			logger.debug("开始执行sql16操作" + strSQL16);
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			sqlbv9.sql(strSQL16);
			sqlbv9.put("strMngCom", strMngCom);
			SSRS ssrs16 = exeSQL5.execSQL(sqlbv9);
			int i_count16 = ssrs16.getMaxRow();
			logger.debug("i_count16的值" + i_count16);
			if (i_count16 == 0) {
				col[8] = "0";
			} else {
				logger.debug(ssrs16.GetText(1, 1));
				col[8] = ssrs16.GetText(1, 1);
			}

			// 复核完成

//			strSQL6 = "select count(*) from lbmission a where activityid = '0000001001'"
			strSQL6 = "select count(*) from lbmission a where activityid in (select activityid from lwactivity  where functionid = '10010003')"
					+ " and a.MissionProp8 like concat('"
					+ "?strMngCom?"
//					+ "%' and a.processid = '0000000003' and a.outdate between '"
					+ "','%') and a.outdate between '"
					+ "?strStartDate?"
					+ "' and '"
					+ "?strEndDate?"
					+ "' and exists (select 'X' from lbmission b  where a.missionid=b.missionid and "
//					+ " ((activityid ='0000001098' and missionprop5 ='TB01') or (activityid='0000001099' and missionprop5 in ( '01','07','08','09','10','45'))))";
					+ " ((activityid in (select activityid from lwactivity  where functionid = '10010001') and missionprop5 ='TB01') or (activityid in (select activityid from lwactivity  where functionid = '10010002') and missionprop5 in ( '01','07','08','09','10','45'))))";
			logger.debug("开始执行sql6操作" + strSQL6);
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql(strSQL6);
			sqlbv10.put("strMngCom", strMngCom);
			sqlbv10.put("strStartDate", strStartDate);
			sqlbv10.put("strEndDate", strEndDate);
			ExeSQL exeSQL6 = new ExeSQL();
			SSRS ssrs6 = exeSQL6.execSQL(sqlbv10);
			int i_count6 = ssrs6.getMaxRow();
			logger.debug("i_count6的值" + i_count6);
			if (i_count6 == 0) {
				col[9] = "0";
			} else {
				logger.debug(ssrs6.GetText(1, 1));
				col[9] = ssrs6.GetText(1, 1);
			}

			// 等待核保
//			strSQL7 = "select count(*) from lwmission where activityid='0000001100' "
//					+ "and processid = '0000000003' and defaultoperator is null "
					// +" and makedate between '" +
					// strStartDate + "' and '" + strEndDate + "'"
			strSQL7 = "select count(*) from lwmission where activityid in (select activityid from lwactivity  where functionid = '10010028') "
					+ " and defaultoperator is null "
					+ " and MissionProp10 like concat('"
					+ "?strMngCom?"
					+ "','%') "
					+ " and exists (select 'X' from lbmission where lbmission.missionid=lwmission.missionid and "
//					+ " ((activityid ='0000001098' and missionprop5 ='TB01') or (activityid='0000001099' and missionprop5 in ( '01','07','08','09','10','45'))))";
					+ " ((activityid in (select activityid from lwactivity  where functionid = '10010001') and missionprop5 ='TB01') or (activityid in (select activityid from lwactivity  where functionid = '10010002') and missionprop5 in ( '01','07','08','09','10','45'))))";
			logger.debug("开始执行sql7操作" + strSQL7);
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.sql(strSQL7);
			sqlbv11.put("strMngCom", strMngCom);
			ExeSQL exeSQL7 = new ExeSQL();
			SSRS ssrs7 = exeSQL7.execSQL(sqlbv11);
			int i_count7 = ssrs7.getMaxRow();
			logger.debug("i_count7的值" + i_count7);
			if (i_count7 == 0) {
				col[10] = "0";
			} else {
				logger.debug(ssrs7.GetText(1, 1));
				col[10] = ssrs7.GetText(1, 1);
			}

			// 核保中
//			strSQL8 = "select count(*) from lwmission where activityid='0000001100' "
//					+ "and processid = '0000000003' and defaultoperator is not null "
//					// +"and makedate between '" +
//					// strStartDate + "' and '" + strEndDate + "'"
//					+ " and MissionProp10 like '"
//					+ strMngCom
//					+ "%' "
//					+ " and exists (select 'X' from lbmission where lbmission.missionid=lwmission.missionid and "
//					+ " ((activityid ='0000001098' and missionprop5 ='TB01') or (activityid='0000001099' and missionprop5 in ( '01','07','08','09','10','45'))))";
			strSQL8 = "select count(*) from lwmission where activityid in (select activityid from lwactivity  where functionid = '10010028') "
					+ " and defaultoperator is not null "
					// +"and makedate between '" +
					// strStartDate + "' and '" + strEndDate + "'"
					+ " and MissionProp10 like concat('"
					+ "?strMngCom?"
					+ "','%')"
					+ " and exists (select 'X' from lbmission where lbmission.missionid=lwmission.missionid and "
					+ " ((activityid in (select activityid from lwactivity  where functionid = '10010001') and missionprop5 ='TB01') or (activityid in (select activityid from lwactivity  where functionid = '10010002') and missionprop5 in ( '01','07','08','09','10','45'))))";
			logger.debug("开始执行sql8操作" + strSQL8);
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
			sqlbv12.sql(strSQL8);
			sqlbv12.put("strMngCom", strMngCom);
			ExeSQL exeSQL8 = new ExeSQL();
			SSRS ssrs8 = exeSQL8.execSQL(sqlbv12);
			int i_count8 = ssrs8.getMaxRow();
			logger.debug("i_count8的值" + i_count8);
			if (i_count8 == 0) {
				col[11] = "0";
			} else {
				col[11] = ssrs8.GetText(1, 1);
			}

			// 核保完成
//			strSQL9 = "select  count(*) from lbmission a where activityid ='0000001110'"
//					+ "and a.processid = '0000000003' and a.outdate between '"
//					+ strStartDate
//					+ "' and '"
//					+ strEndDate
//					// + "' and a.MissionProp10 like '" + strMngCom
//					+ "' and exists (select 'X' from lbmission b where b.missionid=a.missionid and missionprop3 like '"
//					+ strMngCom
//					+ "%' and "
//					+ " ((activityid ='0000001098' and missionprop5 ='TB01') or (activityid='0000001099' and missionprop5 in ( '01','07','08','09','10','45'))))";
			strSQL9 = "select  count(*) from lbmission a where activityid in (select activityid from lwactivity  where functionid = '10010028')"
					+ " and a.outdate between '"
					+ "?strStartDate?"
					+ "' and '"
					+ "?strEndDate?"
					// + "' and a.MissionProp10 like '" + strMngCom
					+ "' and exists (select 'X' from lbmission b where b.missionid=a.missionid and missionprop3 like concat('"
					+ "?strMngCom?"
					+ "','%') and "
					+ " ((activityid in (select activityid from lwactivity  where functionid = '10010001') and missionprop5 ='TB01') or (activityid in (select activityid from lwactivity  where functionid = '10010002') and missionprop5 in ( '01','07','08','09','10','45'))))";
			logger.debug("开始执行sql9操作" + strSQL9);
			SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
			sqlbv13.sql(strSQL9);
			sqlbv13.put("strStartDate", strStartDate);
			sqlbv13.put("strEndDate", strEndDate);
			sqlbv13.put("strMngCom", strMngCom);
			ExeSQL exeSQL9 = new ExeSQL();
			SSRS ssrs9 = exeSQL9.execSQL(sqlbv13);
			int i_count9 = ssrs9.getMaxRow();
			logger.debug("i_count8的值" + i_count9);
			if (i_count9 == 0) {
				col[12] = "0";
			} else {
				col[12] = ssrs9.GetText(1, 1);
			}

			// 等待生调

//			strSQL10 = "select count(*) from lwmission where activityid = '0000001130'"
//					+ " and processid = '0000000003' "
//					+ " and defaultoperator is null "
//					// +" and makedate between '" +
//					// strStartDate + "' and '" + strEndDate + "'"
//					+ " and MissionProp6 like '"
//					+ strMngCom
//					+ "%'"
//					+ " and exists (select 'X' from lbmission where lbmission.missionid=lwmission.missionid and "
//					+ " ((activityid ='0000001098' and missionprop5 ='TB01') or (activityid='0000001099' and missionprop5 in ( '01','07','08','09','10','45'))))";
			strSQL10 = "select count(*) from lwmission where activityid in (select activityid from lwactivity  where functionid = '10010038')"
					+ " and defaultoperator is null "
					// +" and makedate between '" +
					// strStartDate + "' and '" + strEndDate + "'"
					+ " and MissionProp6 like concat('"
					+ "?strMngCom?"
					+ "','%')"
					+ " and exists (select 'X' from lbmission where lbmission.missionid=lwmission.missionid and "
					+ " ((activityid in (select activityid from lwactivity  where functionid = '10010001') and missionprop5 ='TB01') or (activityid in (select activityid from lwactivity  where functionid = '10010002') and missionprop5 in ( '01','07','08','09','10','45'))))";
			logger.debug("开始执行sql10操作" + strSQL10);
			SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
			sqlbv14.sql(strSQL10);
			sqlbv14.put("strMngCom", strMngCom);
			ExeSQL exeSQL10 = new ExeSQL();
			SSRS ssrs10 = exeSQL10.execSQL(sqlbv14);
			int i_count10 = ssrs10.getMaxRow();
			logger.debug("i_count10的值" + i_count10);
			if (i_count10 == 0) {
				col[13] = "0";
			} else {
				logger.debug(ssrs10.GetText(1, 1));
				col[13] = ssrs10.GetText(1, 1);

			}
			// 生调中
//			strSQL17 = "select count(*) from (select distinct missionid as mission from lwmission where ((activityid in ('0000001120', '0000001108', '0000001113')) or (activityid='0000001130' and defaultoperator is not null))"
//					+ " and processid = '0000000003' )"
//					// +" and makedate between '" +
//					// strStartDate + "' and '" + strEndDate +
//					// "' "
//					+ " where exists (select 'X' from lbmission where lbmission.missionid=mission and missionprop3 like '"
//					+ strMngCom
//					+ "%'"
//					+ " and ((activityid ='0000001098' and missionprop5 ='TB01') or (activityid='0000001099' and missionprop5 in ( '01','07','08','09','10','45'))))";
			strSQL17 = "select count(*) from (select distinct missionid as mission from lwmission where ((activityid in (select activityid from lwactivity  where functionid in('10010027','10010031','10010035'))) or (activityid in (select activityid from lwactivity  where functionid = '10010038') and defaultoperator is not null))) g"
					+ " where exists (select 'X' from lbmission where lbmission.missionid=mission and missionprop3 like concat('"
					+ "?strMngCom?"
					+ "','%')"
					+ " and ((activityid in (select activityid from lwactivity  where functionid = '10010001') and missionprop5 ='TB01') or (activityid in (select activityid from lwactivity  where functionid = '10010002') and missionprop5 in ( '01','07','08','09','10','45'))))";
			logger.debug("开始执行sql17操作" + strSQL17);
			SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
			sqlbv15.sql(strSQL17);
			sqlbv15.put("strMngCom", strMngCom);
			SSRS ssrs17 = exeSQL10.execSQL(sqlbv15);
			int i_count17 = ssrs17.getMaxRow();
			logger.debug("i_count17的值" + i_count17);
			if (i_count17 == 0) {
				col[14] = "0";
			} else {
				logger.debug(ssrs17.GetText(1, 1));
				col[14] = ssrs17.GetText(1, 1);

			}

			// 生调完成
//			strSQL11 = "select count(*) from lbmission a ,lbmission c where a.activityid ='0000001120' and c.activityid='0000001113'"
//					+ " and a.processid = '0000000003' and c.processid = '0000000003' "
			strSQL11 = "select count(*) from lbmission a ,lbmission c where a.activityid in (select activityid from lwactivity  where functionid ='10010035') and c.activityid in (select activityid from lwactivity  where functionid ='10010031')"
					+ " and a.makedate=c.makedate and a.maketime=c.maketime and a.outdate is not null and c.outdate is not null and a.missionprop1=c.missionprop1 "
					+ " and (( a.outdate>=c.outdate and  a.outdate between '"
					+ "?strStartDate?"
					+ "' and '"
					+ "?strEndDate?"
					+ "') or (a.outdate<=c.outdate and c.outdate between ' "
					+ "?strStartDate?"
					+ "' and '"
					+ "?strEndDate?"
					+ "'))"
					+ " and exists (select 'X' from lbmission b where b.missionid=a.missionid and b.missionprop3 like concat('"
					+ "?strMngCom?"
					+ "','%')"
//					+ " and ((activityid ='0000001098' and missionprop5 ='TB01') or (activityid='0000001099' and missionprop5 in ( '01','07','08','09','10','45'))))";
			        + " and ((activityid in (select activityid from lwactivity  where functionid ='10010001') and missionprop5 ='TB01') or (activityid in (select activityid from lwactivity  where functionid ='10010001') and missionprop5 in ( '01','07','08','09','10','45'))))";
			logger.debug("开始执行sql10操作" + strSQL11);
			SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
			sqlbv16.sql(strSQL11);
			sqlbv16.put("strMngCom", strMngCom);
			sqlbv16.put("strStartDate", strStartDate);
			sqlbv16.put("strEndDate", strEndDate);
			ExeSQL exeSQL11 = new ExeSQL();
			SSRS ssrs11 = exeSQL11.execSQL(sqlbv16);
			int i_count11 = ssrs11.getMaxRow();
			logger.debug("i_count11的值" + i_count11);
			if (i_count11 == 0) {
				col[15] = "0";
			} else {
				logger.debug(ssrs11.GetText(1, 1));
				col[15] = ssrs11.GetText(1, 1);

			}

			// 等待再保
//			strSQL12 = "select count(*) from lwmission where activityid ='0000001140'"
//					+ "and processid = '0000000003' and defaultoperator is null "
//					// +"and makedate between '" +
//					// strStartDate + "' and '" + strEndDate + "'"
//					+ " and MissionProp6 like '"
//					+ strMngCom
//					+ "%' "
//					+ " and exists (select 'X' from lbmission where lbmission.missionid=lwmission.missionid and "
//					+ " ((activityid ='0000001098' and missionprop5 ='TB01') or (activityid='0000001099' and missionprop5 in ( '01','07','08','09','10','45'))))";
			strSQL12 = "select count(*) from lwmission where activityid in (select activityid from lwactivity  where functionid ='10010040')"
					+ " and defaultoperator is null "
					// +"and makedate between '" +
					// strStartDate + "' and '" + strEndDate + "'"
					+ " and MissionProp6 like concat('"
					+ "?strMngCom?"
					+ "','%') "
					+ " and exists (select 'X' from lbmission where lbmission.missionid=lwmission.missionid and "
					+ " ((activityid in (select activityid from lwactivity  where functionid ='10010001') and missionprop5 ='TB01') or (activityid in (select activityid from lwactivity  where functionid ='10010002') and missionprop5 in ( '01','07','08','09','10','45'))))";
			logger.debug("开始执行sql12操作" + strSQL12);
			SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
			sqlbv17.sql(strSQL12);
			sqlbv17.put("strMngCom", strMngCom);
			ExeSQL exeSQL12 = new ExeSQL();
			SSRS ssrs12 = exeSQL12.execSQL(sqlbv17);
			int i_count12 = ssrs12.getMaxRow();
			logger.debug("i_count12的值" + i_count12);
			if (i_count12 == 0) {
				col[16] = "0";
			} else {
				logger.debug(ssrs12.GetText(1, 1));
				col[16] = ssrs12.GetText(1, 1);
			}

			// 再保中
//			strSQL18 = "select count(*) from lwmission where activityid ='0000001140'"
//					+ "and processid = '0000000003' and defaultoperator is not null "
//					// +"and makedate between '" +
//					// strStartDate + "' and '" + strEndDate + "'"
//					+ " and MissionProp6 like '"
//					+ strMngCom
//					+ "%'"
//					+ " and exists (select 'X' from lbmission where lbmission.missionid=lwmission.missionid and "
//					+ " ((activityid ='0000001098' and missionprop5 ='TB01') or (activityid='0000001099' and missionprop5 in ( '01','07','08','09','10','45'))))";
			strSQL18 = "select count(*) from lwmission where activityid in (select activityid from lwactivity  where functionid ='10010040')"
					+ "  and defaultoperator is not null  and MissionProp6 like concat('"
					+ "?strMngCom?"
					+ "','%')  and exists (select 'X' from lbmission where lbmission.missionid=lwmission.missionid and "
					+ " ((activityid in (select activityid from lwactivity  where functionid ='10010001') and missionprop5 ='TB01') or (activityid in (select activityid from lwactivity  where functionid ='10010002') and missionprop5 in ( '01','07','08','09','10','45'))))";
			logger.debug("开始执行sql18操作" + strSQL18);
			SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
			sqlbv18.sql(strSQL18);
			sqlbv18.put("strMngCom", strMngCom);
			SSRS ssrs18 = exeSQL12.execSQL(sqlbv18);
			int i_count18 = ssrs18.getMaxRow();
			logger.debug("i_count18的值" + i_count18);
			if (i_count18 == 0) {
				col[17] = "0";
			} else {
				logger.debug(ssrs18.GetText(1, 1));
				col[17] = ssrs18.GetText(1, 1);
			}

			// 再保完成
//			strSQL13 = "select count(*) from lbmission a where activityid ='0000001140'"
//					+ "and a.processid = '0000000003' and a.outdate between '"
//					+ strStartDate
//					+ "' and '"
//					+ strEndDate
//					+ "' and MissionProp6 like '"
//					+ strMngCom
//					+ "%' "
//					+ " and exists (select 'X' from lbmission b where b.missionid=a.missionid and "
//					+ " ((activityid ='0000001098' and missionprop5 ='TB01') or (activityid='0000001099' and missionprop5 in ( '01','07','08','09','10','45'))))";
			strSQL13 = "select count(*) from lbmission a where activityid in (select activityid from lwactivity  where functionid ='10010040')"
					+ " and a.outdate between '"+ "?strStartDate?" + "' and '"+ "?strEndDate?" + "' and MissionProp6 like concat('" + "?strMngCom?" + "','%') "
					+ " and exists (select 'X' from lbmission b where b.missionid=a.missionid and "
					+ " ((activityid in (select activityid from lwactivity  where functionid ='10010001') and missionprop5 ='TB01') or (activityid in (select activityid from lwactivity  where functionid ='10010002') and missionprop5 in ( '01','07','08','09','10','45'))))";
			logger.debug("开始执行sql12操作" + strSQL13);
			SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
			sqlbv19.sql(strSQL13);
			sqlbv19.put("strStartDate", strStartDate);
			sqlbv19.put("strEndDate", strEndDate);
			sqlbv19.put("strMngCom", strMngCom);
			ExeSQL exeSQL13 = new ExeSQL();
			SSRS ssrs13 = exeSQL13.execSQL(sqlbv19);
			int i_count13 = ssrs13.getMaxRow();
			logger.debug("i_count13的值" + i_count13);
			if (i_count13 == 0) {
				col[18] = "0";
			} else {
				logger.debug(ssrs13.GetText(1, 1));
				col[18] = ssrs13.GetText(1, 1);
			}
		}

		if (strSaleChnl.equals("3")) {
			// 扫描 strSQL = "select count(*) from es_doc_main where makedate
			// between '" +
			// strStartDate + "' and '" + strEndDate + "' and managecom='" +
			// strMngCom + "' and subtype='UA001'";
			strSQL = "select count(*) from es_doc_main where "
					+ " managecom like concat('" + "?strMngCom?"
					+ "','%') and makedate between '" + "?strStartDate?" + "' and '"
					+ "?strEndDate?" + "' and subtype='UA006'";
			SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
			sqlbv20.sql(strSQL);
			sqlbv20.put("strMngCom", strMngCom);
			sqlbv20.put("strStartDate", strStartDate);
			sqlbv20.put("strEndDate", strEndDate);
			
			logger.debug("开始执行sql操作" + strSQL);
			ExeSQL exeSQL = new ExeSQL();
			SSRS ssrs = exeSQL.execSQL(sqlbv20);
			int i_count = ssrs.getMaxRow();
			logger.debug("i_count的值" + i_count);
			if (i_count != 0) {
				logger.debug(ssrs.GetText(1, 1));
				col[0] = ssrs.GetText(1, 1);
			} else {
				col[0] = "0";
			}

			// 等待录单
//			strSQL1 = "select count(*) from lwmission where ((activityid ='0000001098' and missionprop5 ='TB05') or (activityid='0000001099' and  missionprop5 in( '05','12'))) "
//					+ " and defaultoperator is  null"
//					// +" and makedate between '" +
//					// strStartDate + "' and '" + strEndDate + "'"
//					+ " and missionprop3 like '"
//					+ strMngCom
//					+ "%' and processid = '0000000003' ";
			strSQL1 = "select count(*) from lwmission where ((activityid in (select activityid from lwactivity  where functionid ='10010001') and missionprop5 ='TB05') "
					+" or (activityid in (select activityid from lwactivity  where functionid ='10010002') and  missionprop5 in( '05','12'))) "
					+ " and defaultoperator is  null  and missionprop3 like concat('" + "?strMngCom?" + "','%') ";
			SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
			sqlbv21.sql(strSQL1);
			sqlbv21.put("strMngCom", strMngCom);
			logger.debug("开始执行sql1操作" + strSQL1);
			ExeSQL exeSQL1 = new ExeSQL();
			SSRS ssrs1 = exeSQL1.execSQL(sqlbv21);
			int i_count1 = ssrs1.getMaxRow();
			logger.debug("i_count1的值" + i_count1);
			if (i_count1 == 0) {
				col[1] = "0";
			} else {
				logger.debug(ssrs1.GetText(1, 1));
				col[1] = ssrs1.GetText(1, 1);
			}

			// 录单中
//			strSQL14 = "select count(*) from lwmission where ((activityid ='0000001098' and missionprop5 ='TB05') or (activityid='0000001099' and  missionprop5 in( '05','12')))"
//					+ " and defaultoperator is not null"
//					+ " and missionprop3 like'"
//					+ strMngCom
//					+ "%' and processid = '0000000003' ";
			// +" and makedate between '" +
			// strStartDate + "' and '" + strEndDate + "'";
			strSQL14 = "select count(*) from lwmission where ((activityid in (select activityid from lwactivity  where functionid ='10010001') and missionprop5 ='TB05') or (activityid in (select activityid from lwactivity  where functionid ='10010002') and  missionprop5 in( '05','12')))"
					+ " and defaultoperator is not null  and missionprop3 like concat('" + "?strMngCom?" + "','%')  ";
			SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
			sqlbv22.sql(strSQL14);
			sqlbv22.put("strMngCom", strMngCom);
			logger.debug("开始执行sql14操作" + strSQL14);
			SSRS ssrs14 = exeSQL1.execSQL(sqlbv22);
			int i_count14 = ssrs14.getMaxRow();
			logger.debug("i_count14的值" + i_count14);
			if (i_count14 == 0) {
				col[2] = "0";
			} else {
				logger.debug(ssrs14.GetText(1, 1));
				col[2] = ssrs14.GetText(1, 1);
			}

			// 录单完成

//			strSQL2 = "select count(*) from lbmission where ((activityid ='0000001098' and missionprop5 ='TB05') or (activityid='0000001099' and  missionprop5 in( '05','12')))"
			strSQL2 = "select count(*) from lbmission where ((activityid in (select activityid from lwactivity  where functionid ='10010001') and missionprop5 ='TB05') or (activityid in (select activityid from lwactivity  where functionid ='10010002') and  missionprop5 in( '05','12')))"
					+ " and MissionProp3 like concat('"
					+ "?strMngCom?"
					+ "','%') and outdate between '"
					+ "?strStartDate?"
					+ "' and '"
					+ "?strEndDate?" + "' ";
			SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
			sqlbv23.sql(strSQL2);
			sqlbv23.put("strMngCom", strMngCom);
			sqlbv23.put("strStartDate", strStartDate);
			sqlbv23.put("strEndDate", strEndDate);
			logger.debug("开始执行sql2操作" + strSQL2);
			ExeSQL exeSQL2 = new ExeSQL();
			SSRS ssrs2 = exeSQL2.execSQL(sqlbv23);
			int i_count2 = ssrs2.getMaxRow();
			logger.debug("i_count2的值" + i_count2);
			if (i_count2 == 0) {
				col[3] = "0";
			} else {
				logger.debug(ssrs2.GetText(1, 1));
				col[3] = ssrs2.GetText(1, 1);
			}

			// 等待问题件修改
//			strSQL3 = "select count(*) from lwmission where activityid='0000001002' and processid = '0000000003' "
			strSQL3 = "select count(*) from lwmission where activityid in (select activityid from lwactivity  where functionid ='10010004') "
					+ " and defaultoperator is null"
					+ " and MissionProp5 like concat('"
					+ "?strMngCom?"
					+ "','%')"
					// +" and makedate between '" +
					// strStartDate + "' and '" + strEndDate + "' "
					+ " and exists (select * from lbmission where lbmission.missionid=lwmission.missionid and "
//					+ " ((activityid ='0000001098' and missionprop5 ='TB05') or (activityid='0000001099' and  missionprop5 in( '05','12'))))";
					+ " ((activityid in (select activityid from lwactivity  where functionid ='10010001') and missionprop5 ='TB05') or (activityid in (select activityid from lwactivity  where functionid ='10010002') and  missionprop5 in( '05','12'))))";

			SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
			sqlbv24.sql(strSQL3);
			sqlbv24.put("strMngCom", strMngCom);
			logger.debug("开始执行sql3操作" + strSQL3);
			ExeSQL exeSQL3 = new ExeSQL();
			SSRS ssrs3 = exeSQL3.execSQL(sqlbv24);
			int i_count3 = ssrs3.getMaxRow();
			logger.debug("i_count3的值" + i_count3);
			if (i_count3 == 0) {
				col[4] = "0";
			} else {
				logger.debug(ssrs3.GetText(1, 1));
				col[4] = ssrs3.GetText(1, 1);
			}

			// 问题件修改中

//			strSQL15 = "select count(*) from lwmission where activityid='0000001002' and processid = '0000000003' "
			strSQL15 = "select count(*) from lwmission where activityid in (select activityid from lwactivity  where functionid ='10010004') "
					+ " and defaultoperator is not null"
					+ " and MissionProp5 like concat('"
					+ "?strMngCom?"
					+ "','%')"
					// +"and makedate between '" +
					// strStartDate + "' and '" + strEndDate + "' "
					+ " and exists (select * from lbmission where lbmission.missionid=lwmission.missionid and "
//					+ " ((activityid ='0000001098' and missionprop5 ='TB05') or (activityid='0000001099' and  missionprop5 in( '05','12'))))";
					+ " ((activityid in (select activityid from lwactivity  where functionid ='10010001') and missionprop5 ='TB05') or (activityid in (select activityid from lwactivity  where functionid ='10010002') and  missionprop5 in( '05','12'))))";
			logger.debug("开始执行sql5操作" + strSQL15);
			SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
			sqlbv25.sql(strSQL15);
			sqlbv25.put("strMngCom", strMngCom);
			SSRS ssrs15 = exeSQL3.execSQL(sqlbv25);
			int i_count15 = ssrs15.getMaxRow();
			logger.debug("i_count15的值" + i_count15);
			if (i_count15 == 0) {
				col[5] = "0";
			} else {
				logger.debug(ssrs15.GetText(1, 1));
				col[5] = ssrs15.GetText(1, 1);
			}

			// 问题件修改完成
//			strSQL4 = "select count(*) from lbmission a where activityid = '0000001002' "
			strSQL4 = "select count(*) from lbmission a where activityid in (select activityid from lwactivity  where functionid ='10010004') "
					+ " and a.MissionProp5 like concat('"
					+ "?strMngCom?"
					+ "','%') and a.outdate between '"
					+ "?strStartDate?"
					+ "' and '"
					+ "?strEndDate?"
					+ "'"
					+ " and exists (select * from lbmission b where b.missionid=a.missionid and "
//					+ " ((activityid ='0000001098' and missionprop5 ='TB05') or (activityid='0000001099' and  missionprop5 in( '05','12'))))";
					+ " ((activityid in (select activityid from lwactivity  where functionid ='10010001') and missionprop5 ='TB05') or (activityid in (select activityid from lwactivity  where functionid ='10010002') and  missionprop5 in( '05','12'))))";
			logger.debug("开始执行sql4操作" + strSQL4);
			SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
			sqlbv26.sql(strSQL4);
			sqlbv26.put("strMngCom", strMngCom);
			sqlbv26.put("strStartDate", strStartDate);
			sqlbv26.put("strEndDate", strEndDate);
			ExeSQL exeSQL4 = new ExeSQL();
			SSRS ssrs4 = exeSQL4.execSQL(sqlbv26);
			int i_count4 = ssrs4.getMaxRow();
			logger.debug("i_count4的值" + i_count4);
			if (i_count4 == 0) {
				col[6] = "0";
			} else {
				logger.debug(ssrs4.GetText(1, 1));
				col[6] = ssrs4.GetText(1, 1);
			}

			// 等待复核
//			strSQL5 = "select count(*) from lwmission where activityid='0000001001' and processid = '0000000003' "
			strSQL5 = "select count(*) from lwmission where activityid in (select activityid from lwactivity  where functionid ='10010003')"
					+ " and defaultoperator is null"
					// + " and makedate between '" +
					// strStartDate + "' and '" + strEndDate + "'"
					+ " and MissionProp8 like concat('"
					+ "?strMngCom?"
					+ "','%') "
					+ " and exists (select * from lbmission where lbmission.missionid=lwmission.missionid and "
//					+ " ((activityid ='0000001098' and missionprop5 ='TB05') or (activityid='0000001099' and  missionprop5 in( '05','12'))))";
					+ " ((activityid in (select activityid from lwactivity  where functionid ='10010001') and missionprop5 ='TB05') or (activityid in (select activityid from lwactivity  where functionid ='10010002') and  missionprop5 in( '05','12'))))";
			logger.debug("开始执行sql5操作" + strSQL5);
			SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
			sqlbv27.sql(strSQL5);
			sqlbv27.put("strMngCom", strMngCom);
			ExeSQL exeSQL5 = new ExeSQL();
			SSRS ssrs5 = exeSQL5.execSQL(sqlbv27);
			int i_count5 = ssrs5.getMaxRow();
			logger.debug("i_count5的值" + i_count5);
			if (i_count5 == 0) {
				col[7] = "0";
			} else {
				logger.debug(ssrs5.GetText(1, 1));
				col[7] = ssrs5.GetText(1, 1);
			}

			// 复核中
//			strSQL16 = "select count(*) from lwmission where activityid='0000001001' and processid = '0000000003' "
			strSQL16 = "select count(*) from lwmission where activityid in (select activityid from lwactivity  where functionid ='10010003') "
					+ " and defaultoperator is not null"
					// + " and makedate between '" +
					// strStartDate + "' and '" + strEndDate + "'"
					+ " and MissionProp8 like concat('"
					+ "?strMngCom?"
					+ "','%')"
					+ " and exists (select * from lbmission where lbmission.missionid=lwmission.missionid and "
//					+ " ((activityid ='0000001098' and missionprop5 ='TB05') or (activityid='0000001099' and  missionprop5 in( '05','12'))))";
					+ " ((activityid in (select activityid from lwactivity  where functionid ='10010001') and missionprop5 ='TB05') or (activityid in (select activityid from lwactivity  where functionid ='10010002') and  missionprop5 in( '05','12'))))";
			logger.debug("开始执行sql16操作" + strSQL16);
			SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
			sqlbv28.sql(strSQL16);
			sqlbv28.put("strMngCom", strMngCom);
			SSRS ssrs16 = exeSQL5.execSQL(sqlbv28);
			int i_count16 = ssrs16.getMaxRow();
			logger.debug("i_count16的值" + i_count16);
			if (i_count16 == 0) {
				col[8] = "0";
			} else {
				logger.debug(ssrs16.GetText(1, 1));
				col[8] = ssrs16.GetText(1, 1);
			}

			// 复核完成

//			strSQL6 = "select count(*) from lbmission a where activityid = '0000001001'"
//					+ " and a.MissionProp8 like '"
//					+ strMngCom
//					+ "%' and a.processid = '0000000003' and a.outdate between '"
//					+ strStartDate
//					+ "' and '"
//					+ strEndDate
//					+ "' "
//					+ " and exists (select * from lbmission b where b.missionid=a.missionid and "
//					+ " ((activityid ='0000001098' and missionprop5 ='TB05') or (activityid='0000001099' and  missionprop5 in( '05','12')))) ";
			strSQL6 = "select count(*) from lbmission a where activityid in (select activityid from lwactivity  where functionid ='10010003')"
					+ " and a.MissionProp8 like concat('" + "?strMngCom?" + "','%') and a.outdate between '"
					+ "?strStartDate?" + "' and '" + "?strEndDate?"
					+ "' and exists (select * from lbmission b where b.missionid=a.missionid and "
					+ " ((activityid in (select activityid from lwactivity  where functionid ='10010001') and missionprop5 ='TB05') or (activityid in (select activityid from lwactivity  where functionid ='10010002') and  missionprop5 in( '05','12')))) ";
			SQLwithBindVariables sqlbv29 = new SQLwithBindVariables();
			sqlbv29.sql(strSQL6);
			sqlbv29.put("strMngCom", strMngCom);
			sqlbv29.put("strStartDate", strStartDate);
			sqlbv29.put("strEndDate", strEndDate);
			logger.debug("开始执行sql6操作" + strSQL6);
			ExeSQL exeSQL6 = new ExeSQL();
			SSRS ssrs6 = exeSQL6.execSQL(sqlbv29);
			int i_count6 = ssrs6.getMaxRow();
			logger.debug("i_count6的值" + i_count6);
			if (i_count6 == 0) {
				col[9] = "0";
			} else {
				logger.debug(ssrs6.GetText(1, 1));
				col[9] = ssrs6.GetText(1, 1);
			}

			// 等待核保
//			strSQL7 = "select count(*) from lwmission where activityid='0000001100' "
//					+ "and processid = '0000000003' and defaultoperator is null "
//					// +"and makedate between '" +
//					// strStartDate + "' and '" + strEndDate + "'"
//					+ " and MissionProp10 like '"
//					+ strMngCom
//					+ "%' "
//					+ " and exists (select * from lbmission where lbmission.missionid=lwmission.missionid and "
//					+ " ((activityid ='0000001098' and missionprop5 ='TB05') or (activityid='0000001099' and  missionprop5 in( '05','12'))))";
			strSQL7 = "select count(*) from lwmission where activityid in (select activityid from lwactivity  where functionid ='10010028') "
					+ " and defaultoperator is null  and MissionProp10 like concat('" + "?strMngCom?" + "','%') "
					+ " and exists (select * from lbmission where lbmission.missionid=lwmission.missionid and "
					+ " ((activityid in (select activityid from lwactivity  where functionid ='10010001') and missionprop5 ='TB05') or (activityid in (select activityid from lwactivity  where functionid ='10010002') and  missionprop5 in( '05','12'))))";
			SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
			sqlbv30.sql(strSQL7);
			sqlbv30.put("strMngCom", strMngCom);
			logger.debug("开始执行sql7操作" + strSQL7);
			ExeSQL exeSQL7 = new ExeSQL();
			SSRS ssrs7 = exeSQL7.execSQL(sqlbv30);
			int i_count7 = ssrs7.getMaxRow();
			logger.debug("i_count7的值" + i_count7);
			if (i_count7 == 0) {
				col[10] = "0";
			} else {
				logger.debug(ssrs7.GetText(1, 1));
				col[10] = ssrs7.GetText(1, 1);
			}

			// 核保中
//			strSQL8 = "select count(*) from lwmission where activityid='0000001100' "
//					+ "and processid = '0000000003' and defaultoperator is not null "
//					// + "and makedate between '" +
//					// strStartDate + "' and '" + strEndDate + "'"
//					+ " and MissionProp10 like '"
//					+ strMngCom
//					+ "%'"
//					+ " and exists (select * from lbmission where lbmission.missionid=lwmission.missionid and "
//					+ " ((activityid ='0000001098' and missionprop5 ='TB05') or (activityid='0000001099' and  missionprop5 in( '05','12'))))";
			strSQL8 = "select count(*) from lwmission where activityid in (select activityid from lwactivity  where functionid ='10010028') "
					+ " and defaultoperator is not null   and MissionProp10 like concat('"
					+ "?strMngCom?" + "','%')"
					+ " and exists (select * from lbmission where lbmission.missionid=lwmission.missionid and "
					+ " ((activityid in (select activityid from lwactivity  where functionid ='10010001') and missionprop5 ='TB05') or (activityid in (select activityid from lwactivity  where functionid ='10010002') and  missionprop5 in( '05','12'))))";
			SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
			sqlbv31.sql(strSQL8);
			sqlbv31.put("strMngCom", strMngCom);
			logger.debug("开始执行sql8操作" + strSQL8);
			ExeSQL exeSQL8 = new ExeSQL();
			SSRS ssrs8 = exeSQL8.execSQL(sqlbv31);
			int i_count8 = ssrs8.getMaxRow();
			logger.debug("i_count8的值" + i_count8);
			if (i_count8 == 0) {
				col[11] = "0";
			} else {
				col[11] = ssrs8.GetText(1, 1);
			}

			// 核保完成
//			strSQL9 = "select count(*) from lbmission a where activityid ='0000001110'"
//					+ "and a.processid = '0000000003' and a.outdate between '"
//					+ strStartDate
//					+ "' and '"
//					+ strEndDate
//					// + "' and a.MissionProp10 like '" + strMngCom + "%' "
//					+ "' and exists (select * from lbmission b where b.missionid=a.missionid and missionprop3 like '"
//					+ strMngCom
//					+ "%' and "
//					+ " ((activityid ='0000001098' and missionprop5 ='TB05') or (activityid='0000001099' and  missionprop5 in( '05','12'))))";
			strSQL9 = "select count(*) from lbmission a where activityid in (select activityid from lwactivity  where functionid ='10010028')"
					+ " and a.outdate between '" + "?strStartDate?" + "' and '" + "?strEndDate?"
					+ "' and exists (select * from lbmission b where b.missionid=a.missionid and missionprop3 like concat('"
					+ "?strMngCom?" + "','%') and "
					+ " ((activityid in (select activityid from lwactivity  where functionid ='10010001') and missionprop5 ='TB05') or (activityid in (select activityid from lwactivity  where functionid ='10010002') and  missionprop5 in( '05','12'))))";
			SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
			sqlbv32.sql(strSQL9);
			sqlbv32.put("strMngCom", strMngCom);
			sqlbv32.put("strStartDate", strStartDate);
			sqlbv32.put("strEndDate", strEndDate);
			logger.debug("开始执行sql9操作" + strSQL9);
			ExeSQL exeSQL9 = new ExeSQL();
			SSRS ssrs9 = exeSQL9.execSQL(sqlbv32);
			int i_count9 = ssrs9.getMaxRow();
			logger.debug("i_count8的值" + i_count9);
			if (i_count9 == 0) {
				col[12] = "0";
			} else {
				col[12] = ssrs9.GetText(1, 1);
			}

			// 等待生调

//			strSQL10 = "select count(*) from lwmission where activityid = '0000001130'"
//					+ " and processid = '0000000003' "
//					+ " and defaultoperator is null "
//					// +"and makedate between '" +
//					// strStartDate + "' and '" + strEndDate + "'"
//					+ " and MissionProp6 like '"
//					+ strMngCom
//					+ "%' "
//					+ " and exists (select * from lbmission where lbmission.missionid=lwmission.missionid and "
//					+ " ((activityid ='0000001098' and missionprop5 ='TB05') or (activityid='0000001099' and  missionprop5 in( '05','12'))))";
			strSQL10 = "select count(*) from lwmission where activityid in (select activityid from lwactivity  where functionid ='10010038')"
					+ " and defaultoperator is null   and MissionProp6 like concat('"
					+ "?strMngCom?" + "','%')  and exists (select * from lbmission where lbmission.missionid=lwmission.missionid and "
					+ " ((activityid in (select activityid from lwactivity  where functionid ='10010001') and missionprop5 ='TB05') or (activityid in (select activityid from lwactivity  where functionid ='10010002') and  missionprop5 in( '05','12'))))";
			SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
			sqlbv33.sql(strSQL10);
			sqlbv33.put("strMngCom", strMngCom);
			logger.debug("开始执行sql10操作" + strSQL10);
			ExeSQL exeSQL10 = new ExeSQL();
			SSRS ssrs10 = exeSQL10.execSQL(sqlbv33);
			int i_count10 = ssrs10.getMaxRow();
			logger.debug("i_count10的值" + i_count10);
			if (i_count10 == 0) {
				col[13] = "0";
			} else {
				logger.debug(ssrs10.GetText(1, 1));
				col[13] = ssrs10.GetText(1, 1);

			}

			// 生调中
//			strSQL17 = "select count(*) from (select distinct missionid as mission from lwmission where ((activityid in ('0000001120', '0000001108', '0000001113')) or (activityid='0000001130' and defaultoperator is not null))"
//					+ " and processid = '0000000003' )"
//					// +"and makedate between '" +
//					// strStartDate + "' and '" + strEndDate +"'"
//					+ " where  exists (select * from lbmission where lbmission.missionid=mission  and missionprop3 like '"
//					+ strMngCom
//					+ "%'"
//					+ " and ((activityid ='0000001098' and missionprop5 ='TB05') or (activityid='0000001099' and  missionprop5 in( '05','12'))))";
			strSQL17 = "select count(*) from (select distinct missionid as mission from lwmission where ((activityid in (select activityid from lwactivity  where functionid in('10010027','10010031','10010035'))) "
					+" or (activityid in (select activityid from lwactivity  where functionid ='10010038') and defaultoperator is not null))"
					+ " ) g where  exists (select * from lbmission where lbmission.missionid=mission  and missionprop3 like concat('"
					+ "?strMngCom?" + "','%')  and ((activityid in (select activityid from lwactivity  where functionid ='10010001') and missionprop5 ='TB05') or (activityid in (select activityid from lwactivity  where functionid ='10010002') and  missionprop5 in( '05','12'))))";
			SQLwithBindVariables sqlbv34 = new SQLwithBindVariables();
			sqlbv34.sql(strSQL17);
			sqlbv34.put("strMngCom", strMngCom);
			logger.debug("开始执行sql17操作" + strSQL17);
			SSRS ssrs17 = exeSQL10.execSQL(sqlbv34);
			int i_count17 = ssrs17.getMaxRow();
			logger.debug("i_count17的值" + i_count17);
			if (i_count17 == 0) {
				col[14] = "0";
			} else {
				logger.debug(ssrs17.GetText(1, 1));
				col[14] = ssrs17.GetText(1, 1);
			}

			// 生调完成
//			strSQL11 = "select count(*) from lbmission a,lbmission c  where a.activityid = '0000001120' and c.activityid='0000001113'"
//					+ "and a.processid = '0000000003' and  c.processid = '0000000003' and "
			strSQL11 = "select count(*) from lbmission a,lbmission c  where a.activityid in (select activityid from lwactivity  where functionid ='10010035') and c.activityid in (select activityid from lwactivity  where functionid ='10010031')"
					+ " and  a.makedate=c.makedate and a.maketime=c.maketime and a.outdate is not null and c.outdate is not null and a.missionprop1=c.missionprop1 "
					+ " and (( a.outdate>=c.outdate and  a.outdate between '"
					+ "?strStartDate?"
					+ "' and '"
					+ "?strEndDate?"
					+ "') or (a.outdate<=c.outdate and c.outdate between ' "
					+ "?strStartDate?"
					+ "' and '"
					+ "?strEndDate?"
					+ "'))"
					+ " and exists (select * from lbmission b where b.missionid=a.missionid and missionprop3 like concat('"
					+ "?strMngCom?"
					+ "','%')"
//					+ "  and ((activityid ='0000001098' and missionprop5 ='TB05') or (activityid='0000001099' and  missionprop5 in( '05','12'))))";
					+ "  and ((activityid in (select activityid from lwactivity  where functionid ='10010001') and missionprop5 ='TB05') or (activityid in (select activityid from lwactivity  where functionid ='10010002') and  missionprop5 in( '05','12'))))";
			logger.debug("开始执行sql10操作" + strSQL11);
			SQLwithBindVariables sqlbv35 = new SQLwithBindVariables();
			sqlbv35.sql(strSQL11);
			sqlbv35.put("strMngCom", strMngCom);
			sqlbv35.put("strStartDate", strStartDate);
			sqlbv35.put("strEndDate", strEndDate);
			ExeSQL exeSQL11 = new ExeSQL();
			SSRS ssrs11 = exeSQL11.execSQL(sqlbv35);
			int i_count11 = ssrs11.getMaxRow();
			logger.debug("i_count11的值" + i_count11);
			if (i_count11 == 0) {
				col[15] = "0";
			} else {
				logger.debug(ssrs11.GetText(1, 1));
				col[15] = ssrs11.GetText(1, 1);
			}

			// 等待再保
//			strSQL12 = "select count(*) from lwmission where activityid ='0000001140'"
//					+ "and processid = '0000000003' and defaultoperator is null "
//					// +"and makedate between '" +
//					// strStartDate + "' and '" + strEndDate + "'"
//					+ " and MissionProp6 like '"
//					+ strMngCom
//					+ "%'"
//					+ " and exists (select * from lbmission where lbmission.missionid=lwmission.missionid and "
//					+ " ((activityid ='0000001098' and missionprop5 ='TB05') or (activityid='0000001099' and  missionprop5 in( '05','12'))))";
			strSQL12 = "select count(*) from lwmission where activityid in (select activityid from lwactivity  where functionid ='10010040')"
					+ " and defaultoperator is null and MissionProp6 like concat('"
					+ "?strMngCom?" + "','%')"
					+ " and exists (select * from lbmission where lbmission.missionid=lwmission.missionid and "
					+ " ((activityid in (select activityid from lwactivity  where functionid ='10010001') and missionprop5 ='TB05') or (activityid in (select activityid from lwactivity  where functionid ='10010002') and  missionprop5 in( '05','12'))))";
			SQLwithBindVariables sqlbv36 = new SQLwithBindVariables();
			sqlbv36.sql(strSQL12);
			sqlbv36.put("strMngCom", strMngCom);
			logger.debug("开始执行sql12操作" + strSQL12);
			ExeSQL exeSQL12 = new ExeSQL();
			SSRS ssrs12 = exeSQL12.execSQL(sqlbv36);
			int i_count12 = ssrs12.getMaxRow();
			logger.debug("i_count12的值" + i_count12);
			if (i_count12 == 0) {
				col[16] = "0";
			} else {
				logger.debug(ssrs12.GetText(1, 1));
				col[16] = ssrs12.GetText(1, 1);
			}

			// 再保中
//			strSQL18 = "select count(*) from lwmission where activityid ='0000001140'"
//					+ "and processid = '0000000003' and defaultoperator is not null "
//					// + "and makedate between '" +
//					// strStartDate + "' and '" + strEndDate + "'"
//					+ " and MissionProp6 like '"
//					+ strMngCom
//					+ "%'"
//					+ " and exists (select * from lbmission where lbmission.missionid=lwmission.missionid and "
//					+ " ((activityid ='0000001098' and missionprop5 ='TB05') or (activityid='0000001099' and  missionprop5 in( '05','12'))))";
			strSQL18 = "select count(*) from lwmission where activityid in (select activityid from lwactivity  where functionid ='10010040')"
					+ " and defaultoperator is not null  and MissionProp6 like concat('"
					+ "?strMngCom?" + "','%')"
					+ " and exists (select * from lbmission where lbmission.missionid=lwmission.missionid and "
					+ " ((activityid in (select activityid from lwactivity  where functionid ='10010001') and missionprop5 ='TB05') or (activityid in (select activityid from lwactivity  where functionid ='10010002') and  missionprop5 in( '05','12'))))";
			SQLwithBindVariables sqlbv37 = new SQLwithBindVariables();
			sqlbv37.sql(strSQL18);
			sqlbv37.put("strMngCom", strMngCom);
			logger.debug("开始执行sql18操作" + strSQL18);
			SSRS ssrs18 = exeSQL12.execSQL(sqlbv37);
			int i_count18 = ssrs18.getMaxRow();
			logger.debug("i_count18的值" + i_count18);
			if (i_count18 == 0) {
				col[17] = "0";
			} else {
				logger.debug(ssrs18.GetText(1, 1));
				col[17] = ssrs18.GetText(1, 1);
			}

			// 再保完成
//			strSQL13 = "select count(*) from lbmission a where a.activityid ='0000001140'"
//					+ "and a.processid = '0000000003' and a.outdate between '"
			strSQL13 = "select count(*) from lbmission a where a.activityid in (select activityid from lwactivity  where functionid ='10010040')"
					+ " and a.outdate between '"
					+ "?strStartDate?"
					+ "' and '"
					+ "?strEndDate?"
					+ "' and a.MissionProp6 like concat('"
					+ "?strMngCom?"
					+ "','%')"
					+ " and exists (select * from lbmission b where b.missionid=a.missionid and "
//					+ " ((activityid ='0000001098' and missionprop5 ='TB05') or (activityid='0000001099' and  missionprop5 in( '05','12'))))";
					+ " ((activityid in (select activityid from lwactivity  where functionid ='10010001') and missionprop5 ='TB05') or (activityid in (select activityid from lwactivity  where functionid ='10010002') and  missionprop5 in( '05','12'))))";					
			SQLwithBindVariables sqlbv38 = new SQLwithBindVariables();
			sqlbv38.sql(strSQL13);
			sqlbv38.put("strMngCom", strMngCom);
			sqlbv38.put("strStartDate", strStartDate);
			sqlbv38.put("strEndDate", strEndDate);
			logger.debug("开始执行sql12操作" + strSQL13);
			ExeSQL exeSQL13 = new ExeSQL();
			SSRS ssrs13 = exeSQL13.execSQL(sqlbv38);
			int i_count13 = ssrs13.getMaxRow();
			logger.debug("i_count13的值" + i_count13);
			if (i_count13 == 0) {
				col[18] = "0";
			} else {
				logger.debug(ssrs13.GetText(1, 1));
				col[18] = ssrs13.GetText(1, 1);
			}
		}

		logger.debug("col[0]" + col[0]);
		logger.debug("col[1]" + col[1]);
		logger.debug("col[2]" + col[2]);
		logger.debug("col[3]" + col[3]);
		logger.debug("col[4]" + col[4]);
		logger.debug("col[5]" + col[5]);
		logger.debug("col[6]" + col[6]);
		logger.debug("col[7]" + col[7]);
		logger.debug("col[8]" + col[8]);
		logger.debug("col[9]" + col[9]);
		logger.debug("col[10]" + col[10]);
		logger.debug("col[11]" + col[11]);
		logger.debug("col[12]" + col[12]);
		tlistTable.add(col);
		String type = "";
		if (strSaleChnl.equals("1")) {
			type = "个人业务";
		} else {
			type = "银行代理";
		}
		xmlexport.addListTable(tlistTable, cols);
		TextTag texttag = new TextTag(); // Create a TextTag instance
		texttag.add("TYPE", type);
		xmlexport.addTextTag(texttag);

		// xmlexport.outputDocumentToFile("e:\\", "PNoticeBill"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

	/*
	 * @function :in order to debug
	 */
	public static void main(String[] args) {

		VData tVData = new VData();
		tVData.addElement("86110000");
		tVData.addElement("2005-08-01");
		tVData.addElement("2005-08-16");
		tVData.addElement("1");
		new WorkMissionPrintBL().submitData(tVData, "PRINT");
	}
}
