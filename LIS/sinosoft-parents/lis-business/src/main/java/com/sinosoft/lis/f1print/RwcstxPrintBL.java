package com.sinosoft.lis.f1print;
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

public class RwcstxPrintBL {
private static Logger logger = Logger.getLogger(RwcstxPrintBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	private String strMngCom;

	private String strStaticType;
	private String strOverTime;
	private String strSaleChnl;

	public RwcstxPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("Opertate=" + cOperate);
		if (!cOperate.equals("PRINT") && !cOperate.equals("PRINT2")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		if (!getInputData(cInputData)) {
			return false;
		}
		mResult.clear();
		if (cOperate.equals("PRINT")) {
			if (!getPrintData()) {
				return false;
			}
		}
		if (cOperate.equals("PRINT2")) {

			if (!getPrintDatal()) {
				return false;
			}

		}
		return true;
	}

	/***************************************************************************
	 * Name : getInputData() function :receive data from jsp and check date
	 * check :judge whether startdate begin enddate return :true or false
	 */
	private boolean getInputData(VData cInputData) {
		strMngCom = (String) cInputData.get(0);
		strSaleChnl = (String) cInputData.get(1);
		strStaticType = (String) cInputData.get(2);
		strOverTime = (String) cInputData.get(3);
		logger.debug("OverTime:" + strOverTime);
		logger.debug("StaticType:" + strStaticType);
		logger.debug("SaleChnl:" + strSaleChnl);
		logger.debug("MngCom:" + strMngCom);
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
		cError.moduleName = "RwcstxPrintBL";
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
		xmlexport.createDocument("RWCSTXList.vts", "printer"); // appoint to a
																// special
																// output .vts
																// file
		String[] cols = new String[3];

		ListTable tlistTable = new ListTable();
		tlistTable.setName("List");
		String t_sql = "";
		// String[] col=new String[3];
		/**
		 * @todo 处理超时未处理的保单
		 */
		if (strStaticType.equals("1")) {
			logger.debug("StaticType:" + strStaticType);

			/**
			 * @todo 判断是否选择总公司86
			 */
			if (strMngCom != null && strMngCom.equals("86")) {
				/**
				 * @todo 查询超时的任务
				 */
				t_sql = "select distinct a.missionprop1,"
						+ " (select codename"
						+ " from ldcode"
						+ " where codetype = 'rwcstx'"
						+ " and code = a.activityid),"
						+ " (select lduser.username"
						+ " from lduser"
						+ " where lduser.usercode = a.defaultoperator) as a3"
						+ " from lwmission a"
//						+ " where a.activityid in ('0000001098', '0000001099', '0000001001',"
//						+ " '0000001002', '0000001100', '0000001150')"
//						+ " and a.processid = '0000000003'"
						+ " where a.activityid in (select activityid from lwactivity  where functionid in "
						+" ('10010003','10010004','10010001','10010002','10010028','10010042'))"					
						+ " and a.defaultoperator in"
						+ " (select usercode from lduser where comcode = '"
						+ "?strMngCom?" + "')"
						+ " and exists (select 'x' from lccont"
						+ " where trim(contno) = a.missionprop1"
						+ " and managecom like concat('" + "?strMngCom?" + "','%')";
				if (!(strSaleChnl == null) && !(strSaleChnl.equals(""))) {
					// logger.debug("#########");
					t_sql = t_sql + " and salechnl = '" + "?strSaleChnl?" + "'";
				}
				t_sql = t_sql + " and makedate < subdate(now() , '" + "?strOverTime?"
						+ "'))" + " order by a3";

			} else {
				/**
				 * @todo 查询超时的任务
				 */
				t_sql = "select distinct a.missionprop1,"
						+ " (select codename"
						+ " from ldcode"
						+ " where codetype = 'rwcstx'"
						+ " and code = a.activityid),"
						+ " (select lduser.username"
						+ " from lduser"
						+ " where lduser.usercode = a.defaultoperator) as a3"
						+ " from lwmission a"
//						+ " where a.activityid in ('0000001098', '0000001099', '0000001001',"
//						+ " '0000001002', '0000001100', '0000001150')"
//						+ " and a.processid = '0000000003'"
						+ " where a.activityid in (select activityid from lwactivity  where functionid in "
						+" ('10010003','10010004','10010001','10010002','10010028','10010042'))"
						+ " and (a.defaultoperator is null or"
						+ " a.defaultoperator in"
						+ " (select usercode from lduser where comcode like concat('"
						+ "?strMngCom?" + "','%')))" + " and exists (select 'x'"
						+ " from lccont"
						+ " where trim(contno) = a.missionprop1"
						+ " and managecom like concat('" + "?strMngCom?" + "','%')";
				if (!(strSaleChnl == null) && !(strSaleChnl.equals(""))) {
					t_sql = t_sql + " and salechnl = '" + "?strSaleChnl?" + "'";
				}
				t_sql = t_sql + " and makedate < subdate(now() , '" + "?strOverTime?"
						+ "'))" + " order by a3";
			}
			logger.debug("开始执行sql操作" + t_sql);
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(t_sql);
			sqlbv1.put("strMngCom", strMngCom);
			sqlbv1.put("strSaleChnl", strSaleChnl);
			sqlbv1.put("strOverTime", strOverTime);
			ExeSQL exeSQL1 = new ExeSQL();
			SSRS ssrs1 = exeSQL1.execSQL(sqlbv1);
			int i_count1 = ssrs1.getMaxRow();
			logger.debug("i_count1的值" + i_count1);
			if (i_count1 != 0) {
				for (int i = 1; i <= i_count1; i++) {
					logger.debug(ssrs1.GetText(1, 1));
					String[] col = new String[3];
					col[0] = ssrs1.GetText(i, 1);
					col[1] = ssrs1.GetText(i, 2);
					col[2] = ssrs1.GetText(i, 3);
					logger.debug("col[0]" + col[0]);
					logger.debug("col[1]" + col[1]);
					logger.debug("col[2]" + col[2]);
					tlistTable.add(col);
				}
			}

			// if (i_count1 == 0) {
			// CError tError = new CError();
			// tError.moduleName = "StandardSxStaticBL";
			// tError.functionName = "getDutyGetClmInfo";
			// tError.errorMessage = "在该时间段内没有打印信息，请确认起止日期是否正确！！！";
			// this.mErrors.addOneError(tError);
			// return false;
			//
			// }

		}

		/**
		 * @todo 查询问件超时未回复的保单
		 */
		if (strStaticType.equals("2")) {
			logger.debug("StaticType:" + strStaticType);
			logger.debug("开始执行sql操作");
			/**
			 * @todo 判断是否选择总公司86
			 */
			if (strMngCom != null && strMngCom.equals("86")) {
				t_sql = "select a1, a2, (select username from lduser where usercode = a3)"
						+ " from (select distinct a.missionprop1 as a1,"
						+ " (select codename"
						+ " from ldcode"
						+ " where codetype = 'rwcstx'"
						+ " and code = a.activityid) as a2,"
						+ " (select lwmission.defaultoperator"
						+ " from lwmission"
						+ " where lwmission.missionid = a.missionid"
//						+ " and lwmission.activityid = '0000001100') as a3"
						+ " and lwmission.activityid  in (select activityid from lwactivity  where functionid ='10010028')) as a3"
						+ " from lwmission a"
						+ " where a.activityid in"
//						+ " ('0000001220', '0000001230', '0000001250', '0000001260',"
//						+ " '0000001280', '0000001300', '0000001106', '0000001108',"
//						+ " '0000001111', '0000001113')"
//						+ " and a.processid = '0000000003'"
						+ " (select activityid from lwactivity  where functionid in "
						+" ('10010025','10010027','10010029','10010031','10010044','10010045',"
						+" '10010047','10010048','10010050','10010052'))"
						+ " and (a.defaultoperator is null or"
						+ " a.defaultoperator in"
						+ " (select usercode from lduser where comcode = '"
						+ "?strMngCom?"
						+ "'))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where trim(contno) = a.missionprop1"
						+ " and managecom like concat('" + "?strMngCom?" + "','%')";
				if (!(strSaleChnl == null) && !(strSaleChnl.equals(""))) {
					t_sql = t_sql + " and salechnl = '" + "?strSaleChnl?" + "'";
				}
				t_sql = t_sql + " and makedate < subdate(now() , '" + "?strOverTime?"
						+ "'))" + " order by a3) g ";
			} else {
				t_sql = "select a1, a2, (select username from lduser where usercode = a3)"
						+ " from (select distinct a.missionprop1 as a1,"
						+ " (select codename"
						+ " from ldcode"
						+ " where codetype = 'rwcstx'"
						+ " and code = a.activityid) as a2,"
						+ " (select lwmission.defaultoperator"
						+ " from lwmission"
						+ " where lwmission.missionid = a.missionid"
//						+ " and lwmission.activityid = '0000001100') as a3"
						+ " and lwmission.activityid  in (select activityid from lwactivity  where functionid ='10010028')) as a3"
						+ " from lwmission a"
						+ " where a.activityid in"
//						+ " ('0000001220', '0000001230', '0000001250', '0000001260',"
//						+ " '0000001280', '0000001300', '0000001106', '0000001108',"
//						+ " '0000001111', '0000001113')"
//						+ " and a.processid = '0000000003'"
						+ " (select activityid from lwactivity  where functionid in "
						+" ('10010025','10010027','10010029','10010031','10010044','10010045',"
						+" '10010047','10010048','10010050','10010052'))"
						+ " and (a.defaultoperator is null or"
						+ " a.defaultoperator in"
						+ " (select usercode from lduser where comcode like concat('" + "?strMngCom?" + "','%')))"
						+ " and exists (select 'x'"
						+ " from lccont"
						+ " where trim(contno) = a.missionprop1"
						+ " and managecom like concat('" + "?strMngCom?" + "','%')";
				if (!(strSaleChnl == null) && !(strSaleChnl.equals(""))) {
					t_sql = t_sql + " and salechnl = '" + "?strSaleChnl?" + "'";
				}
				t_sql = t_sql + " and makedate < subdate(now() , '" + "?strOverTime?"
						+ "'))" + " order by a3) g";
			}
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(t_sql);
			sqlbv2.put("strMngCom", strMngCom);
			sqlbv2.put("strSaleChnl", strSaleChnl);
			sqlbv2.put("strOverTime", strOverTime);
			logger.debug("开始执行sql操作" + t_sql);
			ExeSQL exeSQL = new ExeSQL();
			SSRS ssrs = exeSQL.execSQL(sqlbv2);
			int i_count = ssrs.getMaxRow();
			logger.debug("i_count的值" + i_count);
			if (i_count != 0) {
				for (int i = 1; i <= i_count; i++) {
					logger.debug(ssrs.GetText(1, 1));
					String[] col2 = new String[3];
					col2[0] = ssrs.GetText(i, 1);
					col2[1] = ssrs.GetText(i, 2);
					col2[2] = ssrs.GetText(i, 3);
					logger.debug("col[0]" + col2[0]);
					logger.debug("col[1]" + col2[1]);
					logger.debug("col[2]" + col2[2]);
					tlistTable.add(col2);
				}

			}
			// else {
			// CError tError = new CError();
			// tError.moduleName = "StandardSxStaticBL";
			// tError.functionName = "getDutyGetClmInfo";
			// tError.errorMessage = "在该时间段内没有打印信息，请确认起止日期是否正确！！！";
			// this.mErrors.addOneError(tError);
			// return false;
			//
			// }

		}
		xmlexport.addListTable(tlistTable, cols);

		TextTag texttag = new TextTag();
		// XmlExport xmlexport = new XmlExport(); //Create a XmlExport instance

		// logger.debug("您的t_sql的执行结果是"+t_sql);

		// xmlexport.addListTable(alistTable, col);
		// texttag.add("ManageCom", strMngCom);
		// texttag.add("Date1", strStartDate);
		// texttag.add("Date2", strEndDate);
		if (strSaleChnl.equals("1")) {
			texttag.add("ListTitle", "个人业务任务超时提醒清单");
		} else if (strSaleChnl.equals("3")) {
			texttag.add("ListTitle", "银行代理业务任务超时提醒清单");
		}
		xmlexport.addTextTag(texttag);

		// xmlexport.outputDocumentToFile("e:\\", "RWCSTXList"); //输出xml文档到文件

		mResult.clear();

		mResult.addElement(xmlexport);

		return true;
	}

	private boolean getPrintDatal() {
		XmlExport xmlexport = new XmlExport();
		xmlexport.createDocument("RWCSTXList.vts", "printer"); // appoint to a
																// special
																// output .vts
																// file
		String[] cols = new String[3];

		ListTable tlistTable = new ListTable();
		tlistTable.setName("List");
		String t_sql = "";
		// String[] col=new String[3];
		/**
		 * @todo 处理超时未处理的保单
		 */
		if (strStaticType.equals("1")) {
			logger.debug("StaticType:" + strStaticType);

			/**
			 * @todo 判断是否选择总公司86
			 */
			if (strMngCom != null && strMngCom.equals("86")) {
				/**
				 * @todo 查询超时的任务
				 */
				t_sql = "select distinct a.missionprop1,"
						+ " (select codename"
						+ " from ldcode"
						+ " where codetype = 'rwcstx'"
						+ " and code = a.activityid),"
						+ " (select lduser.username"
						+ " from lduser"
						+ " where lduser.usercode = a.defaultoperator) as a3"
						+ " from lwmission a"
						+ " where a.activityid in ('0000002098', '0000002099', '0000002001',"
						+ " '0000002002', '0000002003', '0000002004','0000002005','0000002006')"
						+ " and a.processid = '0000000004'"
						+ " and a.defaultoperator in"
						+ " (select usercode from lduser where comcode = '"
						+ "?strMngCom?" + "')" + " and exists (select 'x'"
						+ " from lcgrpcont"
						+ " where trim(grpcontno) = a.missionprop1"
						+ " and managecom like concat('" + "?strMngCom?" + "','%')";
				if (!(strSaleChnl == null) && !(strSaleChnl.equals(""))) {
					t_sql = t_sql + " and salechnl = '" + "?strSaleChnl?" + "'";
				}
				t_sql = t_sql + " and makedate < subdate(now() , '" + "?strOverTime?"
						+ "'))" + " order by a3";

			} else {
				/**
				 * @todo 查询超时的任务
				 */
				t_sql = "select distinct a.missionprop1,"
						+ " (select codename"
						+ " from ldcode"
						+ " where codetype = 'rwcstx'"
						+ " and code = a.activityid),"
						+ " (select lduser.username"
						+ " from lduser"
						+ " where lduser.usercode = a.defaultoperator) as a3"
						+ " from lwmission a"
						+ " where a.activityid in ('0000002098', '0000002099', '0000002001',"
						+ " '0000002002', '0000002003', '0000002004','0000002005','0000002006')"
						+ " and a.processid = '0000000004'"
						+ " and (a.defaultoperator is null or"
						+ " a.defaultoperator in"
						+ " (select usercode from lduser where comcode like concat('" + "?strMngCom?" + "','%')))" + " and exists (select 'x'"
						+ " from lcgrpcont"
						+ " where trim(grpcontno) = a.missionprop1"
						+ " and managecom like concat('" + "?strMngCom?" + "','%')";
				if (!(strSaleChnl == null) && !(strSaleChnl.equals(""))) {
					t_sql = t_sql + " and salechnl = '" + "?strSaleChnl?" + "'";
				}
				t_sql = t_sql + " and makedate < subdate(now() , '" + "?strOverTime?"
						+ "'))" + " order by a3";

			}
			logger.debug("开始执行sql操作" + t_sql);
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(t_sql);
			sqlbv3.put("strMngCom", strMngCom);
			sqlbv3.put("strSaleChnl", strSaleChnl);
			sqlbv3.put("strOverTime", strOverTime);
			ExeSQL exeSQL1 = new ExeSQL();
			SSRS ssrs1 = exeSQL1.execSQL(sqlbv3);
			int i_count1 = ssrs1.getMaxRow();
			logger.debug("i_count1的值" + i_count1);
			if (i_count1 != 0) {
				for (int i = 1; i <= i_count1; i++) {
					logger.debug(ssrs1.GetText(1, 1));
					String[] col = new String[3];
					col[0] = ssrs1.GetText(i, 1);
					col[1] = ssrs1.GetText(i, 2);
					col[2] = ssrs1.GetText(i, 3);
					logger.debug("col[0]" + col[0]);
					logger.debug("col[1]" + col[1]);
					logger.debug("col[2]" + col[2]);
					tlistTable.add(col);
				}
			}

			// if (i_count1 == 0) {
			// CError tError = new CError();
			// tError.moduleName = "StandardSxStaticBL";
			// tError.functionName = "getDutyGetClmInfo";
			// tError.errorMessage = "在该时间段内没有打印信息，请确认起止日期是否正确！！！";
			// this.mErrors.addOneError(tError);
			// return false;
			//
			// }

		}
		/**
		 * @todo 查询问题件超时未回复的保单
		 */
		if (strStaticType.equals("2")) {
			logger.debug("StaticType:" + strStaticType);
			logger.debug("开始执行sql操作");
			/**
			 * @todo 判断是否选择总公司86
			 */
			if (strMngCom != null && strMngCom.equals("86")) {

				t_sql = "select distinct a.missionprop1,"
						+ " (select codename"
						+ " from ldcode"
						+ " where codetype = 'rwcstx'"
						+ " and code = a.activityid),"
						+ " (select lduser.username"
						+ " from lduser"
						+ " where lduser.usercode = a.defaultoperator) as a3"
						+ " from lwmission a"
						+ " where a.activityid in"
						+ " ('0000002225','0000002314','00000021140000002301','0000002306',"
						+ "'0000002311','0000002240','0000002260','0000002250','0000002230',"
						+ "'0000002101','0000002114','0000002111','0000002106')"
						+ " and a.processid = '0000000004'"
						+ " and (a.defaultoperator is null or"
						+ " a.defaultoperator in"
						+ " (select usercode from lduser where comcode = '"
						+ "?strMngCom?" + "'))" + " and exists (select 'x'"
						+ " from lcgrpcont"
						+ " where trim(grpcontno) = a.missionprop1"
						+ " and managecom like concat('" + "?strMngCom?" + "','%')";
				if (!(strSaleChnl == null) && !(strSaleChnl.equals(""))) {
					t_sql = t_sql + " and salechnl = '" + "?strSaleChnl?" + "'";
				}
				t_sql = t_sql + " and makedate < subdate(now() , '" + "?strOverTime?"
						+ "'))" + " order by a3";
			} else {
				t_sql = "select distinct a.missionprop1,"
						+ " (select codename"
						+ " from ldcode"
						+ " where codetype = 'rwcstx'"
						+ " and code = a.activityid),"
						+ " (select lduser.username"
						+ " from lduser"
						+ " where lduser.usercode = a.defaultoperator) as a3"
						+ " from lwmission a"
						+ " where a.activityid in"
						+ " ('0000002225','0000002314','00000021140000002301','0000002306',"
						+ "'0000002311','0000002240','0000002260','0000002250','0000002230',"
						+ "'0000002101','0000002114','0000002111','0000002106')"
						+ " and a.processid = '0000000004'"
						+ " and (a.defaultoperator is null or"
						+ " a.defaultoperator in"
						+ " (select usercode from lduser where comcode like concat('" + "?strMngCom?" + "','%')))" + " and exists (select 'x'"
						+ " from lcgrpcont"
						+ " where trim(grpcontno) = a.missionprop1"
						+ " and managecom like concat('" + "?strMngCom?" + "','%')";
				if (!(strSaleChnl == null) && !(strSaleChnl.equals(""))) {
					t_sql = t_sql + " and salechnl = '" + "?strSaleChnl?" + "'";
				}
				t_sql = t_sql + " and makedate < subdate(now() , '" + "?strOverTime?"
						+ "'))" + " order by a3";

			}
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(t_sql);
			sqlbv4.put("strMngCom", strMngCom);
			sqlbv4.put("strSaleChnl", strSaleChnl);
			sqlbv4.put("strOverTime", strOverTime);
			logger.debug("开始执行sql操作" + t_sql);
			ExeSQL exeSQL = new ExeSQL();
			SSRS ssrs = exeSQL.execSQL(sqlbv4);
			int i_count = ssrs.getMaxRow();
			logger.debug("i_count的值" + i_count);
			if (i_count != 0) {
				for (int i = 1; i <= i_count; i++) {
					logger.debug(ssrs.GetText(1, 1));
					String[] col2 = new String[3];
					col2[0] = ssrs.GetText(i, 1);
					col2[1] = ssrs.GetText(i, 2);
					col2[2] = ssrs.GetText(i, 3);
					logger.debug("col[0]" + col2[0]);
					logger.debug("col[1]" + col2[1]);
					logger.debug("col[2]" + col2[2]);
					tlistTable.add(col2);
				}

			}
			// else {
			// CError tError = new CError();
			// tError.moduleName = "StandardSxStaticBL";
			// tError.functionName = "getDutyGetClmInfo";
			// tError.errorMessage = "在该时间段内没有打印信息，请确认起止日期是否正确！！！";
			// this.mErrors.addOneError(tError);
			// return false;
			//
			// }

		}
		xmlexport.addListTable(tlistTable, cols);

		TextTag texttag = new TextTag();
		// XmlExport xmlexport = new XmlExport(); //Create a XmlExport instance

		// logger.debug("您的t_sql的执行结果是"+t_sql);

		// xmlexport.addListTable(alistTable, col);
		// texttag.add("ManageCom", strMngCom);
		// texttag.add("Date1", strStartDate);
		// texttag.add("Date2", strEndDate);
		if (strSaleChnl.equals("1")) {
			texttag.add("ListTitle", "个人业务任务超时提醒清单");
		} else if (strSaleChnl.equals("2")) {
			logger.debug("Just here !");
			texttag.add("ListTitle", "团体任务超时提醒清单");
		} else if (strSaleChnl.equals("3")) {
			texttag.add("ListTitle", "银行代理业务任务超时提醒清单");
		}
		xmlexport.addTextTag(texttag);

		// xmlexport.outputDocumentToFile("e:\\", "RWCSTXList"); //输出xml文档到文件

		mResult.clear();

		mResult.addElement(xmlexport);

		return true;
	}

}
