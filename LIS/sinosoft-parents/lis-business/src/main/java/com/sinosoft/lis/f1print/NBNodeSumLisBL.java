package com.sinosoft.lis.f1print;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.f1j.ss.BookModelImpl;
import com.f1j.util.F1Exception;
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

public class NBNodeSumLisBL {
private static Logger logger = Logger.getLogger(NBNodeSumLisBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	private String strMngCom;
	private String comGrade = "";
	private String strSQL;
	private String strStartDate;
	private String strEndDate;
	private String strNode;
	private String nodeName = "";
	Vector finalAeon = new Vector();
	Vector departmentBin = new Vector();
	Vector groupBin = new Vector();
	Vector tryNewWay = new Vector();
	String[] nodeStore = new String[9];

	private int comNo = 0;
	private SQLwithBindVariables [] sqlStore = new SQLwithBindVariables [23];
	private Vector comCodeStore = new Vector();
	private Vector comNameStore = new Vector();
	private String manageComName = "";
	private Vector sqlpackStore = new Vector();
	private String strSaleCh = "0";
	private String saleCh = "0";

	public NBNodeSumLisBL() {
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
		strEndDate = (String) cInputData.get(1);
		saleCh = (String) cInputData.get(2);
		strStartDate = (String) cInputData.get(3);
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
		cError.moduleName = "NBNodeSumLisBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/***************************************************************************
	 * name : getPrintData() function :get print data parameter : null return
	 * :true or false
	 */

	private boolean getPrintData() {
		ExeSQL exeSql = new ExeSQL();
		SSRS testSSRS = new SSRS();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql("select sysvarvalue From ldsysvar where sysvar='DataPrintCombine'");
		testSSRS = exeSql.execSQL(sqlbv1);
		String strTemplatePath = testSSRS.GetText(1, 1); // 数据库中的路径-模板路径D:/lis/ui/f1print/NCLtemplate/
		// String strTemplatePath = "D:/lis/ui/f1print/NCLtemplate/";

		// 选择临时文件读取目录
		if (saleCh.equals("01")) {
			strSaleCh = 1 + "";
		} else if (saleCh.equals("02")) {
			strSaleCh = 3 + "";
		}
		ExeSQL searchOneSQL = new ExeSQL();
		SSRS searchOneSsrs = new SSRS();
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql("Select substr(Comgrade,2,1),name From ldcom Where comcode='"
				+ "?strMngCom?" + "'");
		sqlbv2.put("strMngCom", strMngCom);
		searchOneSsrs = searchOneSQL.execSQL(sqlbv2);
		int errd = searchOneSsrs.getMaxRow();
		if (errd == 0) {
			buildError("getprintData", "机构不存在");
			logger.debug("机构不存在");
			return false;

		}
		comGrade = searchOneSsrs.GetText(1, 1);
		manageComName = searchOneSsrs.GetText(1, 2);
		if (comGrade.equals("1") || comGrade.equals("4")) {
			buildError("getprintData", "只能打印分公司或者中心支公司的清单");
			logger.debug("只能打印分公司或者中心支公司的清单");
			return false;
		} else if (comGrade.equals("3")) {
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql("Select comcode,name From ldcom Where comcode='"
					+ "?strMngCom?" + "'");
			sqlbv3.put("strMngCom", strMngCom);
			searchOneSsrs = searchOneSQL.execSQL(sqlbv3);
			comNo = searchOneSsrs.getMaxRow();
			if (comNo == 0) {
				buildError("getprintData", "机构不存在");
				logger.debug("机构不存在");
				return false;

			}

			for (int p = 0; p < comNo; p++) {
				comCodeStore.add(p, searchOneSsrs.GetText(p + 1, 1));
				comNameStore.add(p, searchOneSsrs.GetText(p + 1, 2));
			}
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql("Select upcomcode From ldcom Where comcode='"
					+ "?strMngCom?" + "'");
			sqlbv4.put("strMngCom", strMngCom);
			searchOneSsrs = searchOneSQL.execSQL(sqlbv4);
			int errd1 = searchOneSsrs.getMaxRow();
			if (errd1 == 0) {
				buildError("getprintData", "机构不存在");
				logger.debug("机构不存在");
				return false;

			}

			strMngCom = searchOneSsrs.GetText(1, 1);

		} else if (comGrade.equals("2")) {
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql("Select comcode,name From ldcom Where comgrade='03' And upcomcode='"
					+ "?strMngCom?" + "'");
			sqlbv5.put("strMngCom", strMngCom);
			searchOneSsrs = searchOneSQL.execSQL(sqlbv5);
			comNo = searchOneSsrs.getMaxRow();
			if (errd == 0) {
				buildError("getprintData", "机构不存在");
				logger.debug("机构不存在");
				return false;

			}

			for (int p = 0; p < comNo; p++) {
				comCodeStore.add(p, searchOneSsrs.GetText(p + 1, 1));
				comNameStore.add(p, searchOneSsrs.GetText(p + 1, 2));
			}

		}
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql("select count(distinct contno),substr(managecom,1,6) from lccont where salechnl='"
				+ "?strSaleCh?"
				+ "' and signdate >= '"
				+ "?strStartDate?"
				+ "' and signdate<='"
				+ "?strEndDate?"
				+ "' and appflag='1' And managecom Like concat('"
				+ "?strMngCom?"
				+ "','%') group by substr(managecom,1,6)");
		sqlbv6.put("strSaleCh", strSaleCh);
		sqlbv6.put("strStartDate", strStartDate);
		sqlbv6.put("strEndDate", strEndDate);
		sqlbv6.put("strMngCom", strMngCom);
		sqlStore[0] = sqlbv6 ; // 承保件数1

		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql("select sum(p.prem),substr(c.managecom,1,6) from lccont c,lcpol p where c.salechnl='"
				+ "?strSaleCh?"
				+ "' and c.signdate >= '"
				+ "?strStartDate?"
				+ "' and c.signdate<='"
				+ "?strEndDate?"
				+ "'  and (c.contno) = (p.contno) and c.cvalidate = p.cvalidate and c.appflag='1' And c.managecom Like concat('"
				+ "?strMngCom?" + "','%') group by substr(c.managecom,1,6)");
		sqlbv7.put("strSaleCh", strSaleCh);
		sqlbv7.put("strStartDate", strStartDate);
		sqlbv7.put("strEndDate", strEndDate);
		sqlbv7.put("strMngCom", strMngCom);
		sqlStore[1] = sqlbv7 ; // 承保保费2
		
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(" select sum(p.prem),substr(c.managecom,1,6)  from lccont c,lcpol p where (c.contno) = (p.contno) and c.salechnl='"
				+ "?strSaleCh?"
//				+ "' and trim(c.prtno) in (select MissionProp1 from lwmission where activityid in ('0000001098','0000001099') and makedate >='"
				+ "' and trim(c.prtno) in (select MissionProp1 from lwmission where activityid in (select activityid from lwactivity  where functionid in('10010001','10010002')) and makedate >='"
				+ "?strStartDate?"
				+ "'And makedate<='"
				+ "?strEndDate?"
				+ "') And c.managecom Like concat('"
				+ "?strMngCom?"
				+ "','%')  group by substr(c.managecom,1,6)");
		sqlbv8.put("strSaleCh", strSaleCh);
		sqlbv8.put("strStartDate", strStartDate);
		sqlbv8.put("strMngCom", strMngCom);
		sqlbv8.put("strEndDate", strEndDate);
		sqlStore[2] = sqlbv8; // 录单保费3
		
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql("select count(distinct contno),substr(managecom,1,6)  from lccont where salechnl='"
				+ "?strSaleCh?"
//				+ "' and trim(prtno) in (select MissionProp1 from lwmission where activityid in ('0000001001') and makedate >='"
				+ "' and trim(prtno) in (select MissionProp1 from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010003') and makedate >='"
				+ "?strStartDate?"
				+ "'and makedate<='"
				+ "?strEndDate?"
				+ "' ) And managecom Like concat('"
				+ "?strMngCom?"
				+ "','%') group by substr(managecom,1,6)");
		sqlbv9.put("strSaleCh", strSaleCh);
		sqlbv9.put("strStartDate", strStartDate);
		sqlbv9.put("strEndDate", strEndDate);
		sqlbv9.put("strMngCom", strMngCom);
		sqlStore[3] = sqlbv9; // 复核件数4

		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		sqlbv10.sql("select sum(p.prem),substr(c.managecom,1,6)  from lccont c,lcpol p where (c.contno) = (p.contno) and c.salechnl='"
				+ "?strSaleCh?"
//				+ "' and trim(c.prtno) in (select MissionProp1 from lwmission where activityid in ('0000001001') and makedate >='"
				+ "' and trim(c.prtno) in (select MissionProp1 from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010003') and makedate >='"
				+ "?strStartDate?"
				+ "'and makedate<='"
				+ "?strEndDate?"
				+ "' ) And c.managecom Like concat('"
				+ "?strMngCom?"
				+ "','%') group by substr(c.managecom, 1, 6)");
		sqlbv10.put("strSaleCh", strSaleCh);
		sqlbv10.put("strStartDate", strStartDate);
		sqlbv10.put("strEndDate", strEndDate);
		sqlbv10.put("strMngCom", strMngCom);
		sqlStore[4] = sqlbv10; // 复核保费5
		
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		sqlbv11.sql("select count(distinct contno),substr(managecom,1,6)  from lccont where salechnl='"
				+ "?strSaleCh?"
//				+ "' and trim(prtno) in (select MissionProp1 from lwmission where activityid in ('0000001002') and makedate >='"
				+ "' and trim(prtno) in (select MissionProp1 from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010004') and makedate >='"
				+ "?strStartDate?"
				+ "'and makedate<='"
				+ "?strEndDate?"
				+ "' ) And managecom Like concat('"
				+ "?strMngCom?"
				+ "','%') group by substr(managecom, 1, 6)");
		sqlbv11.put("strSaleCh", strSaleCh);
		sqlbv11.put("strStartDate", strStartDate);
		sqlbv11.put("strEndDate", strEndDate);
		sqlbv11.put("strMngCom", strMngCom);
		sqlStore[5] = sqlbv11; // 问题件修改件数6
		
		SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
		sqlbv12.sql(" select sum(p.prem),substr(c.managecom,1,6)  from lccont c,lcpol p where (c.contno) = (p.contno) and c.salechnl='"
				+ "?strSaleCh?"
//				+ "' and trim(c.prtno) in (select MissionProp1 from lwmission where activityid in ('0000001002') and makedate >='"
				+ "' and trim(c.prtno) in (select MissionProp1 from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010004') and makedate >='"
				+ "?strStartDate?"
				+ "'and makedate<='"
				+ "?strEndDate?"
				+ "' ) And c.managecom Like concat('"
				+ "?strMngCom?"
				+ "','%') group by substr(c.managecom, 1, 6)");
		sqlbv12.put("strSaleCh", strSaleCh);
		sqlbv12.put("strStartDate", strStartDate);
		sqlbv12.put("strEndDate", strEndDate);
		sqlbv12.put("strMngCom", strMngCom);
		sqlStore[6] = sqlbv12; // 问题件修改保费7
		
		SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
		sqlbv13.sql("select count(distinct contno),substr(managecom,1,6)  from lccont where salechnl='"
				+ "?strSaleCh?"
//				+ "' and trim(prtno) in (select MissionProp1 from lwmission where activityid in ('0000001250') and makedate >='"
				+ "' and trim(prtno) in (select MissionProp1 from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010047') and makedate >='"
				+ "?strStartDate?"
				+ "'and makedate<='"
				+ "?strEndDate?"
				+ "' ) And managecom Like concat('"
				+ "?strMngCom?"
				+ "','%') group by substr(managecom, 1, 6)");
		sqlbv13.put("strSaleCh", strSaleCh);
		sqlbv13.put("strStartDate", strStartDate);
		sqlbv13.put("strEndDate", strEndDate);
		sqlbv13.put("strMngCom", strMngCom);
		sqlStore[7] = sqlbv13; // 问题件回收件数8
		
		SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
		sqlbv14.sql("select sum(p.prem),substr(c.managecom,1,6)  from lccont c,lcpol p where (c.contno) = (p.contno) and c.salechnl='"
				+ "?strSaleCh?"
//				+ "' and trim(c.prtno) in (select MissionProp1 from lwmission where activityid in ('0000001250') and makedate >='"
				+ "' and trim(c.prtno) in (select MissionProp1 from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010047') and makedate >='"
				+ "?strStartDate?"
				+ "'and makedate<='"
				+ "?strEndDate?"
				+ "' ) And c.managecom Like concat('"
				+ "?strMngCom?"
				+ "','%') group by substr(c.managecom, 1, 6)");
		sqlbv14.put("strSaleCh", strSaleCh);
		sqlbv14.put("strStartDate", strStartDate);
		sqlbv14.put("strEndDate", strEndDate);
		sqlbv14.put("strMngCom", strMngCom);
		sqlStore[8] = sqlbv14; // 问题件回收保费9
		
		SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
		sqlbv15.sql("select count(distinct contno),substr(managecom,1,6)  from lccont where salechnl='"
				+ "?strSaleCh?"
//				+ "' and trim(prtno) in (select MissionProp1 from lwmission where activityid in ('0000001260') and makedate >='"
				+ "' and trim(prtno) in (select MissionProp1 from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010048') and makedate >='"
				+ "?strStartDate?"
				+ "'and makedate<='"
				+ "?strEndDate?"
				+ "' ) And managecom Like concat('"
				+ "?strMngCom?"
				+ "','%') group by substr(managecom, 1, 6)");
		sqlbv15.put("strSaleCh", strSaleCh);
		sqlbv15.put("strStartDate", strStartDate);
		sqlbv15.put("strEndDate", strEndDate);
		sqlbv15.put("strMngCom", strMngCom);
		sqlStore[9] = sqlbv15; // 客户合并问题件回收件数10

		SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
		sqlbv16.sql(" select sum(p.prem),substr(c.managecom,1,6)  from lccont c,lcpol p where (c.contno) = (p.contno) and  c.salechnl='"
				+ "?strSaleCh?"
//				+ "' and trim(c.prtno) in (select MissionProp1 from lwmission where activityid in ('0000001260') and makedate >='"
				+ "' and trim(c.prtno) in (select MissionProp1 from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010048') and makedate >='"
				+ "?strStartDate?"
				+ "'and makedate<='"
				+ "?strEndDate?"
				+ "' ) And c.managecom Like concat('"
				+ "?strMngCom?"
				+ "','%') group by substr(c.managecom, 1, 6)");
		sqlbv16.put("strSaleCh", strSaleCh);
		sqlbv16.put("strStartDate", strStartDate);
		sqlbv16.put("strEndDate", strEndDate);
		sqlbv16.put("strMngCom", strMngCom);
		sqlStore[10] = sqlbv16; // 客户合并问题件回收保费11
		
		SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
		sqlbv17.sql("select count(distinct contno),substr(managecom,1,6)  from lccont where salechnl='"
				+ "?strSaleCh?"
//				+ "' and trim(prtno) in (select MissionProp1 from lwmission where activityid in ('0000001220') and makedate >='"
				+ "' and trim(prtno) in (select MissionProp1 from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010044') and makedate >='"
				+ "?strStartDate?"
				+ "'and makedate<='"
				+ "?strEndDate?"
				+ "' ) And managecom Like concat('"
				+ "?strMngCom?"
				+ "','%') group by substr(managecom,1,6)");
		sqlbv17.put("strSaleCh", strSaleCh);
		sqlbv17.put("strStartDate", strStartDate);
		sqlbv17.put("strEndDate", strEndDate);
		sqlbv17.put("strMngCom", strMngCom);
		sqlStore[11] = sqlbv17; // 问题件打印件数12

		SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
		sqlbv18.sql("select sum(p.prem),substr(c.managecom,1,6)  from lccont c,lcpol p where  (c.contno) = (p.contno) and c.salechnl='"
				+ "?strSaleCh?"
//				+ "' and trim(c.prtno) in (select MissionProp1 from lwmission where activityid in ('0000001220') and makedate >='"
				+ "' and trim(c.prtno) in (select MissionProp1 from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010044') and makedate >='"
				+ "?strStartDate?"
				+ "'and makedate<='"
				+ "?strEndDate?"
				+ "' ) And c.managecom Like concat('"
				+ "?strMngCom?"
				+ "','%') group by substr(c.managecom,1,6)");
		sqlbv18.put("strSaleCh", strSaleCh);
		sqlbv18.put("strStartDate", strStartDate);
		sqlbv18.put("strEndDate", strEndDate);
		sqlbv18.put("strMngCom", strMngCom);
		sqlStore[12] = sqlbv18; // 问题件打印保费13
		
		SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
		sqlbv19.sql("select count(distinct contno),substr(managecom,1,6)  from lccont where salechnl='"
				+ "?strSaleCh?"
//				+ "' and trim(prtno) in (select MissionProp1 from lwmission where activityid in ('0000001230') and makedate >='"
				+ "' and trim(prtno) in (select MissionProp1 from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010045') and makedate >='"
				+ "?strStartDate?"
				+ "'and makedate<='"
				+ "?strEndDate?"
				+ "' ) And managecom Like concat('"
				+ "?strMngCom?"
				+ "','%') group by substr(managecom,1,6)");
		sqlbv19.put("strSaleCh", strSaleCh);
		sqlbv19.put("strStartDate", strStartDate);
		sqlbv19.put("strEndDate", strEndDate);
		sqlbv19.put("strMngCom", strMngCom);
		sqlStore[13] = sqlbv19; // 客户合并问题件打印件数14
		
		SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
		sqlbv20.sql("select sum(p.prem),substr(c.managecom,1,6)  from lccont c,lcpol p where (c.contno) = (p.contno) and c.salechnl='"
				+ "?strSaleCh?"
//				+ "' and trim(c.prtno) in (select MissionProp1 from lwmission where activityid in ('0000001230') and makedate >='"
				+ "' and trim(c.prtno) in (select MissionProp1 from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010045') and makedate >='"
				+ "?strStartDate?"
				+ "'and makedate<='"
				+ "?strEndDate?"
				+ "' ) And c.managecom Like concat('"
				+ "?strMngCom?"
				+ "','%') group by substr(c.managecom,1,6)");
		sqlbv20.put("strSaleCh", strSaleCh);
		sqlbv20.put("strStartDate", strStartDate);
		sqlbv20.put("strEndDate", strEndDate);
		sqlbv20.put("strMngCom", strMngCom);
		sqlStore[14] = sqlbv20; // 客户合并问题件打印保费15
		
		SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
		sqlbv21.sql("select count(distinct contno),substr(managecom,1,6)  from lccont  where salechnl='"
				+ "?strSaleCh?"
//				+ "' and trim(prtno) in (select MissionProp1 from lwmission where activityid in ('0000001100') and makedate >='"
				+ "' and trim(prtno) in (select MissionProp1 from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010028') and makedate >='"
				+ "?strStartDate?"
				+ "'and makedate<='"
				+ "?strEndDate?"
				+ "' ) And managecom Like concat('"
				+ "?strMngCom?"
				+ "','%') group by substr(managecom,1,6)");
		sqlbv21.put("strSaleCh", strSaleCh);
		sqlbv21.put("strStartDate", strStartDate);
		sqlbv21.put("strEndDate", strEndDate);
		sqlbv21.put("strMngCom", strMngCom);
		sqlStore[15] = sqlbv21; // 核保件数16

		SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
		sqlbv22.sql("select sum(p.prem),substr(c.managecom,1,6)  from lccont c,lcpol p  where (c.contno) = (p.contno) and  c.salechnl='"
				+ "?strSaleCh?"
//				+ "' and trim(c.prtno) in (select MissionProp1 from lwmission where activityid in ('0000001100') and makedate >='"
				+ "' and trim(c.prtno) in (select MissionProp1 from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010028') and makedate >='"
				+ "?strStartDate?"
				+ "'and makedate<='"
				+ "?strEndDate?"
				+ "' ) And c.managecom Like concat('"
				+ "?strMngCom?"
				+ "','%') group by substr(c.managecom,1,6)");
		sqlbv22.put("strSaleCh", strSaleCh);
		sqlbv22.put("strStartDate", strStartDate);
		sqlbv22.put("strEndDate", strEndDate);
		sqlbv22.put("strMngCom", strMngCom);
		sqlStore[16] = sqlbv22; // 核保保费17
		
		SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
		sqlbv23.sql(" select count(distinct contno),substr(managecom,1,6)  from lccont where salechnl='"
				+ "?strSaleCh?"
//				+ "' and trim(prtno) in (select MissionProp1 from lwmission where activityid in ('0000001150') and makedate >='"
				+ "' and trim(prtno) in (select MissionProp1 from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010042') and makedate >='"
				+ "?strStartDate?"
				+ "'and makedate<='"
				+ "?strEndDate?"
				+ "' ) And managecom Like concat('"
				+ "?strMngCom?"
				+ "','%') group by substr(managecom,1,6)");
		sqlbv23.put("strSaleCh", strSaleCh);
		sqlbv23.put("strStartDate", strStartDate);
		sqlbv23.put("strEndDate", strEndDate);
		sqlbv23.put("strMngCom", strMngCom);
		sqlStore[17] = sqlbv23; // 签单件数18
		
		SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
		sqlbv24.sql("select sum(p.prem),substr(c.managecom,1,6)  from lccont c,lcpol p where  (c.contno) = (p.contno) and c.salechnl='"
				+ "?strSaleCh?"
//				+ "' and trim(c.prtno) in (select MissionProp1 from lwmission where activityid in ('0000001150') and makedate >='"
				+ "' and trim(c.prtno) in (select MissionProp1 from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010042') and makedate >='"
				+ "?strStartDate?"
				+ "'and makedate<='"
				+ "?strEndDate?"
				+ "' ) And c.managecom Like concat('"
				+ "?strMngCom?"
				+ "','%') group by substr(c.managecom,1,6)");
		sqlbv24.put("strSaleCh", strSaleCh);
		sqlbv24.put("strStartDate", strStartDate);
		sqlbv24.put("strEndDate", strEndDate);
		sqlbv24.put("strMngCom", strMngCom);
		sqlStore[18] = sqlbv24; // 客签单保费19
		
		SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
		sqlbv25.sql("select count(distinct contno),substr(managecom,1,6)  from lccont where salechnl='"
				+ "?strSaleCh?"
				+ "' and uwflag='a' and makedate >='"
				+ "?strStartDate?"
				+ "' and makedate<='"
				+ "?strEndDate?"
				+ "' And managecom Like concat('"
				+ "?strMngCom?"
				+ "','%') group by substr(managecom,1,6)");
		sqlbv25.put("strSaleCh", strSaleCh);
		sqlbv25.put("strStartDate", strStartDate);
		sqlbv25.put("strEndDate", strEndDate);
		sqlbv25.put("strMngCom", strMngCom);
		sqlStore[19] = sqlbv25; // 撤单件数20
		
		SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
		sqlbv26.sql(" select sum(p.prem),substr(c.managecom,1,6)  from lccont c,lcpol p where (c.contno) = (p.contno) and c.salechnl='"
				+ "?strSaleCh?"
				+ "' and c.uwflag='a' and c.makedate >='"
				+ "?strStartDate?"
				+ "' and c.makedate<='"
				+ "?strEndDate?"
				+ "' And c.managecom Like concat('"
				+ "?strMngCom?"
				+ "','%') group by substr(c.managecom,1,6)");
		sqlbv26.put("strSaleCh", strSaleCh);
		sqlbv26.put("strStartDate", strStartDate);
		sqlbv26.put("strEndDate", strEndDate);
		sqlbv26.put("strMngCom", strMngCom);
		sqlStore[20] = sqlbv26; // 撤单保费21
		
		SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
		sqlbv27.sql(" select count(distinct contno), substr(managecom,1,6) from lccont where makedate>='"
				+ "?strStartDate?"
				+ "'and makedate<='"
				+ "?strEndDate?"
				+ "' And managecom Like concat('"
				+ "?strMngCom?"
				+ "','%') and salechnl='"
				+ "?strSaleCh?" + "' group by substr(managecom,1,6)");
		sqlbv27.put("strSaleCh", strSaleCh);
		sqlbv27.put("strStartDate", strStartDate);
		sqlbv27.put("strEndDate", strEndDate);
		sqlbv27.put("strMngCom", strMngCom);
		sqlStore[21] = sqlbv27; // 总入机件数22
		
		SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
		sqlbv28.sql(" select sum(p.prem),substr(c.managecom,1,6) from lccont c,lcpol p where (c.contno) = (p.contno) and c.makedate>='"
				+ "?strStartDate?"
				+ "' and c.makedate<='"
				+ "?strEndDate?"
				+ "' And c.managecom Like concat('"
				+ "?strMngCom?"
				+ "','%') and c.salechnl='"
				+ "?strSaleCh?"
				+ "'  group by substr(c.managecom,1,6)");
		sqlbv28.put("strSaleCh", strSaleCh);
		sqlbv28.put("strStartDate", strStartDate);
		sqlbv28.put("strEndDate", strEndDate);
		sqlbv28.put("strMngCom", strMngCom);
		sqlStore[22] = sqlbv28; // 总入机保费23
		String[][] sqlpack1 = getSqlPack(sqlStore[0]);
		String[][] sqlpack2 = getSqlPack(sqlStore[1]);
		String[][] sqlpack3 = getSqlPack(sqlStore[2]);
		String[][] sqlpack4 = getSqlPack(sqlStore[3]);
		String[][] sqlpack5 = getSqlPack(sqlStore[4]);
		String[][] sqlpack6 = getSqlPack(sqlStore[5]);
		String[][] sqlpack7 = getSqlPack(sqlStore[6]);
		String[][] sqlpack8 = getSqlPack(sqlStore[7]);
		String[][] sqlpack9 = getSqlPack(sqlStore[8]);
		String[][] sqlpack10 = getSqlPack(sqlStore[9]);
		String[][] sqlpack11 = getSqlPack(sqlStore[10]);
		String[][] sqlpack12 = getSqlPack(sqlStore[11]);
		String[][] sqlpack13 = getSqlPack(sqlStore[12]);
		String[][] sqlpack14 = getSqlPack(sqlStore[13]);
		String[][] sqlpack15 = getSqlPack(sqlStore[14]);
		String[][] sqlpack16 = getSqlPack(sqlStore[15]);
		String[][] sqlpack17 = getSqlPack(sqlStore[16]);
		String[][] sqlpack18 = getSqlPack(sqlStore[17]);
		String[][] sqlpack19 = getSqlPack(sqlStore[18]);
		String[][] sqlpack20 = getSqlPack(sqlStore[19]);
		String[][] sqlpack21 = getSqlPack(sqlStore[20]);
		String[][] sqlpack22 = getSqlPack(sqlStore[21]);
		String[][] sqlpack23 = getSqlPack(sqlStore[22]);
		sqlpackStore.add(0, sqlpack1);
		sqlpackStore.add(1, sqlpack2);
		sqlpackStore.add(2, sqlpack3);
		sqlpackStore.add(3, sqlpack4);
		sqlpackStore.add(4, sqlpack5);
		sqlpackStore.add(5, sqlpack6);
		sqlpackStore.add(6, sqlpack7);
		sqlpackStore.add(7, sqlpack8);
		sqlpackStore.add(8, sqlpack9);
		sqlpackStore.add(9, sqlpack10);
		sqlpackStore.add(10, sqlpack11);
		sqlpackStore.add(11, sqlpack12);
		sqlpackStore.add(12, sqlpack13);
		sqlpackStore.add(13, sqlpack14);
		sqlpackStore.add(14, sqlpack15);
		sqlpackStore.add(15, sqlpack16);
		sqlpackStore.add(16, sqlpack17);
		sqlpackStore.add(17, sqlpack18);
		sqlpackStore.add(18, sqlpack19);
		sqlpackStore.add(19, sqlpack20);
		sqlpackStore.add(20, sqlpack21);
		sqlpackStore.add(21, sqlpack22);
		sqlpackStore.add(22, sqlpack23);

		TextTag texttag = new TextTag(); // Create a TextTag instance
		XmlExport xmlexport = new XmlExport(); // Create a XmlExport instance
		xmlexport.createDocument("NBNodeSumLis.vts", "printer"); // appoint
		// to a
		// special
		// output
		// .vts file
		ListTable alistTable = new ListTable(); // Create a ListTable instance
		alistTable.setName("RISK"); // Appoint to a sepcial flag
		// logger.debug("comNo==" + comNo);
		// logger.debug("comCodeStore==" + comCodeStore);

		for (int k = 0; k < comNo; k++) {

			try {

				String[] cols = new String[25];
				cols[0] = k + 1 + "";
				for (int i = 1; i < 24; i++) {
					String getResult = "";

					// Vector agb= new Vector();
					// agb.add(0,sqlpack2);

					String[][] temp = (String[][]) sqlpackStore.get(i - 1);
					if (temp == null) {
						logger.debug("表" + i + "是空的");
						cols[i] = 0 + "";
						// buildError("getprintData", "查表失败1");
						// return false;
					} else {
						int size = temp.length;
						for (int w = 0; w < size; w++) {
							if (temp[w][1].equals(comCodeStore.get(k)
									.toString())) {

								getResult = temp[w][0];
								break;
							} else if (Integer.parseInt(comCodeStore.get(k)
									.toString()) < Integer.parseInt(temp[w][1])) {
								// logger.debug("xxxxxxxxx2");
								break;
							}

						}

						// 1************************************************
						if (getResult.equals("null")) {
							logger.debug("查表失败2");
							buildError("getprintData", "查表失败2");
							cols[i] = 0 + "";
							// return false;
						} else if (getResult.equals("")) {
							cols[i] = 0 + "";
						} else {
							cols[i] = getResult;
							// logger.debug("jieguo"+cols[i]+" "+i);
						}
					}
				}
				cols[24] = comNameStore.get(k).toString();
				// logger.debug("取数成功" + cols[0] + " " + cols[1] + " " +
				// cols[2] + " " + cols[3]);
				alistTable.add(cols);

			} catch (Exception ex) {
				buildError("getprintData", "没有需要打印的信息");
				logger.debug("没有需要打印的信息");
				return false;

			}
		}
		String[] col = new String[1];

		xmlexport.addListTable(alistTable, col);
		texttag.add("SysDate2", strEndDate);
		texttag.add("ManageComName", manageComName); // 分公司
		texttag.add("SysDate1", strStartDate);

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		String[] strVFPathName = new String[1];
		CombineVts tcombineVts = null;
		tcombineVts = new CombineVts(xmlexport.getInputStream(),
				strTemplatePath);
		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		tcombineVts.output(dataStream);
		strVFPathName[0] = strTemplatePath + 0 + "NBNodeSumLis.vts";
		// 把dataStream存储到磁盘文件
		// logger.debug("存储文件到"+strVFPathName);
		AccessVtsFile.saveToFile(dataStream, strVFPathName[0]);
		VtsFileCombine vtsfilecombine = new VtsFileCombine();
		try {
			BookModelImpl tb = vtsfilecombine.dataCombine(strVFPathName);
			vtsfilecombine.write(tb, strTemplatePath + "newNBNodeSumLis.vts");
		} catch (IOException ex) {
		} catch (F1Exception ex) {
		} catch (Exception ex) {
			buildError("getprintData", "没有需要打印的信息");
			logger.debug("没有需要打印的信息");
			return false;
		}

		// xmlexport.outputDocumentToFile("D:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(strTemplatePath + "newNBNodeSumLis.vts");
		mResult.addElement(xmlexport);
		return true;

	}

	ExeSQL exeSQL = new ExeSQL();

	private String[][] getSqlPack(SQLwithBindVariables  str) {
		SSRS ssrs;
		ssrs = exeSQL.execSQL(str);
		int i_count = ssrs.getMaxRow();
		// String x="";
		String y = "";
		if (i_count == 0) {
			return null;
		}
		String[][] output = new String[i_count][2];
		for (int i = 1; i <= i_count; i++) {
			// x = ssrs.GetText(1, 1);
			for (int k = 1; k <= 2; k++) {
				y = ssrs.GetText(i, k);
				output[i - 1][k - 1] = y;
				// logger.debug("output===" + output[i - 1][k - 1]);
			}
		}

		return output;

	}

	/*
	 * @function :in order to debug
	 */
	public static void main(String[] args) {
		String tStrMngCom = "8632";
		String tStartDate = "2005-10-03";
		String tEndDate = "2005-12-10";
		String tSale = "01";
		VData tVData = new VData();
		tVData.addElement(tStrMngCom);
		tVData.addElement(tEndDate);
		tVData.addElement(tSale);
		tVData.addElement(tStartDate);
		// NBNodeSumLisBL tNBNodeSumLisBL = new NBNodeSumLisBL();
		// tNBNodeSumLisBL.getSqlPack("select count(distinct
		// contno),substr(managecom,1,6) from lccont where salechnl='1' and
		// signdate >= '"+strStartDate+"' and appflag='1' group by
		// substr(managecom,1,6)");

		NBNodeSumLisUI tNBNodeSumLisUI = new NBNodeSumLisUI();
		tNBNodeSumLisUI.submitData(tVData, "PRINT");
	}
}
