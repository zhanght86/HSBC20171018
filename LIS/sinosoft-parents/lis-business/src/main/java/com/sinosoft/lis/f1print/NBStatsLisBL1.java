package com.sinosoft.lis.f1print;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.f1j.ss.BookModelImpl;
import com.f1j.util.F1Exception;
import com.sinosoft.lis.pubfun.GlobalInput;
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

public class NBStatsLisBL1 {
private static Logger logger = Logger.getLogger(NBStatsLisBL1.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	private String strMngCom;
	private String strIssueDate;
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
	private int sumBills = 0;
	private int areaNo = 0;
	private int sumBillCom = 0;
	String[] nodeStore = new String[9];
	private XmlExport newXmlExport = new XmlExport();
	private int getJ = 1;
	private GlobalInput mGlobalInput = new GlobalInput();
	private int nodeNo = 0;
	private String strSaleCh = "0";
	private String saleChannel = 0 + "";
	private String subtype = "";
	private String strOperator = "";
	private String Missionprop = "";
	private String payMode = "";
	private String middle = "";

	public NBStatsLisBL1() {
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
		strNode = (String) cInputData.get(2);
		strSaleCh = (String) cInputData.get(3);
		strStartDate = (String) cInputData.get(4);
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
		cError.moduleName = "NBStatsLisBL1";
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
		if (strSaleCh.equals("01")) {
			saleChannel = 1 + "";
		} else if (strSaleCh.equals("02")) {
			saleChannel = 3 + "";
		}

		ExeSQL exeSql = new ExeSQL();
		SSRS testSSRS = new SSRS();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql("select sysvarvalue From ldsysvar where sysvar='DataPrintCombine'");
		testSSRS = exeSql.execSQL(sqlbv1);
		String strTemplatePath = testSSRS.GetText(1, 1); // 数据库中的路径-模板路径D:/lis/ui/f1print/NCLtemplate/
		// String strTemplatePath = "D:/ui/f1print/NCLtemplate/";
		// String strTemplatePath
		// ="D:/IBM/WebSphere/AppServer/profiles/AppSrv02/installedApps/sn-bj-term-2Node02Cell/lis_218_9061.ear/lis_war.war/f1print/NCLtemplate/";

		//
		nodeStore[0] = "'0000001098','0000001099'";
		nodeStore[1] = "'0000001001'";
		nodeStore[2] = "'0000001002'";
		nodeStore[3] = "'0000001250'";
		nodeStore[4] = "'0000001260'";
		nodeStore[5] = "'0000001220'";
		nodeStore[6] = "'0000001230'";
		nodeStore[7] = "'0000001100'";
		nodeStore[8] = "'0000001150'";

		ExeSQL searchOneSQL = new ExeSQL();
		SSRS searchOneSsrs = new SSRS();
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql("Select substr(Comgrade,2,1) From ldcom Where comcode='"
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

		// if(strMngCom.substring(0,4).equals("8621"))
		// {
		// logger.debug("success");
		// }
		if (!strMngCom.substring(0, 4).equals("8621")) {
			if (comGrade.equals("1") || comGrade.equals("4")) {
				buildError("getprintData", "只能打印分公司的清单");
				logger.debug("只能打印分公司的清单");
				logger.debug(strMngCom.substring(0, 4));
				return false;
			}
		}
		// 禁止打印总公司清单
		// if (comGrade.equals("1") ) {
		// buildError("getprintData", "不只能选择打印总公司的清单");
		// logger.debug("不只能选择打印总公司的清单");
		// return false;
		// }

		SSRS getNode = new SSRS();
		ExeSQL getNodeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql("Select codename From ldcode Where code='"
				+ "?strNode?" + "'And codetype='nbstatstype'");
		sqlbv3.put("strNode", strNode);
		getNode = getNodeSQL.execSQL(sqlbv3);
		int getNodeP = 0;
		getNodeP = getNode.getMaxRow();
		if (getNodeP == 0) {
			buildError("getprintData", "表ldcode中没有环节关于的数据");
			logger.debug("表ldcode中没有环节关于的数据");
			return false;

		}
		nodeName = getNode.GetText(1, 1);

		if (strNode.equals("10")) {
			strSQL = "Select c.Contno, c.Appntname, (select (case when sum(prem) is not null then sum(prem)  else 0 end) from lcpol where contno=c.contno), c.Agentcode, a.Name, c.Managecom,c.makedate,"
					+ "   (Select Comcode From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '02') Subcom,"
					+ "   (Select Name From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '02') Subcomname,"
					+ "   (Select Comcode From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '03') Subcenter,"
					+ "  (Select Name From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '03') Subcentername,"
					+ "  (Select Comcode From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '04') Area,"
					+ "  (Select Name From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '04') AreaName ,"
					+ "  (Select distinct Operator From lcapplyrecallpol b Where c.prtno=b.prtno) Operator "
					+ "  From Lis.Lccont c, Lis.Laagent a"
					+ "  Where Trim(c.Agentcode) = (a.Agentcode) "
					+ "   And c.Managecom Like concat('"
					+ "?strMngCom?"
					+ "','%') and c.salechnl='"
					+ "?saleChannel?"
					+ "' and c.uwflag='a' and c.makedate >='"
					+ "?strStartDate?"
					+ "'and c.makedate <='"
					+ "?strEndDate?"
					+ "'"
					+ " group by c.contno,c.managecom,c.appntname,c.agentcode,c.makedate,a.name,c.prtno  Order By Subcom,Subcenter,c.makedate";

		} else if (strNode.equals("11")) {

			strSQL = "Select c.Contno, c.Appntname, (select (case when sum(prem) is not null then sum(prem)  else 0 end) from lcpol where contno=c.contno), c.Agentcode, a.Name, c.Managecom,c.makedate,"
					+ "   (Select Comcode From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '02') Subcom,"
					+ "   (Select Name From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '02') Subcomname,"
					+ "   (Select Comcode From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '03') Subcenter,"
					+ "  (Select Name From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '03') Subcentername,"
					+ "  (Select Comcode From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '04') Area,"
					+ "  (Select Name From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '04') AreaName ,''"
					+ "  From Lis.Lccont c, Lis.Laagent a "
					+ "  Where Trim(c.Agentcode) = (a.Agentcode)  "
					+ "   And c.Managecom Like concat('"
					+ "?strMngCom?"
					+ "','%') and c.salechnl='"
					+ "?saleChannel?"
					+ "' and appflag='0' and c.makedate >='"
					+ "?strStartDate?"
					+ "'and c.makedate <='"
					+ "?strEndDate?"
					+ "'"
					+ " group by c.contno,c.managecom,c.appntname,c.agentcode,c.makedate,a.name  Order By Subcom,Subcenter,c.makedate";

		} else if (strNode.equals("06") || strNode.equals("07")) {
			String strNode1 = "";
			String strNode2 = "";
			if (strNode.equals("06")) {
				strNode1 = "0000001220";
				strNode2 = "0000001250";
			} else if (strNode.equals("07")) {
				strNode1 = "0000001230";
				strNode2 = "0000001250";
			}
			strSQL = "Select c.Contno, c.Appntname, (select (case when sum(prem) is not null then sum(prem)  else 0 end) from lcpol where contno=c.contno), c.Agentcode, a.Name, c.Managecom,c.makedate,"
					+ "   (Select Comcode From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '02') Subcom,"
					+ "   (Select Name From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '02') Subcomname,"
					+ "   (Select Comcode From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '03') Subcenter,"
					+ "  (Select Name From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '03') Subcentername,"
					+ "  (Select Comcode From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '04') Area,"
					+ "  (Select Name From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '04') AreaName, "
					+ "  (case when (Select d.Username From Lduser d, Lwmission e Where e.Defaultoperator = d.Usercode And e.Missionprop1 = Trim(c.Contno)) is not null then (Select d.Username From Lduser d, Lwmission e Where e.Defaultoperator = d.Usercode And e.Missionprop1 = Trim(c.Contno))  else '公共队列' end) Operator "
					+ "  From Lis.Lccont c, Lis.Laagent a "
					+ "  Where Trim(c.Agentcode) = (a.Agentcode)  And "
					+ "  c.Contno In (Select Rpad(Missionprop1, lislen('Lccont','contno'), ' ') "
					+ "   From Lis.Lwmission" + "   Where Activityid In ("
					+ strNode1
					+ ") And makedate >='"
					+ "?strStartDate?"
					+ "'and makedate <='"
					+ "?strEndDate?"
					+ "' and Missionprop7 Like concat('"
					+ "?strMngCom?"
					+ "','%') )"
					+ "  and Trim(c.Contno) not In (Select Missionprop1"
					+ "   From Lis.Lwmission"
					+ "   Where Activityid In ("
					+ strNode2
					+ ") And makedate >='"
					+ "?strStartDate?"
					+ "'and makedate <='"
					+ "?strEndDate?"
					+ "' )"
					+ "  And c.Managecom Like concat('"
					+ "?strMngCom?"
					+ "','%') And c.Salechnl = '"
					+ "?saleChannel?"
					+ "'"
					+ "   group by c.contno,c.managecom,c.appntname,c.agentcode,c.makedate,a.name  Order By Subcom,Subcenter,c.makedate";

		} else if (strNode.equals("12")) {

			if (saleChannel.equals("1")) {
				// subtype = "UA001";
				subtype = "6";
			} else if (saleChannel.equals("3")) {
				// subtype = "UA006";
				subtype = "7";
				payMode = "and paymode<>'A'";
			}
			logger.debug("subtype:  " + subtype);
			//
			// strSQL = " Select c.doccode,'', '', '','', c.Managecom ,c.makedate,"
			// + " (Select Comcode From Ldcom Where c.Managecom Like Trim(Comcode) || '%'
			// And Comgrade = '02') Subcom,"
			// + " (Select Name From Ldcom Where c.Managecom Like Trim(Comcode) || '%' And
			// Comgrade = '02') Subcomname,"
			// + " (Select Comcode From Ldcom Where c.Managecom Like Trim(Comcode) || '%'
			// And Comgrade = '03') Subcenter,"
			// + " (Select Name From Ldcom Where c.Managecom Like Trim(Comcode) || '%' And
			// Comgrade = '03') Subcentername,"
			// + " (Select Comcode From Ldcom Where c.Managecom Like Trim(Comcode) || '%'
			// And Comgrade = '04') Area,"
			// + " (Select Name From Ldcom Where c.Managecom Like Trim(Comcode) || '%' And
			// Comgrade = '04') AreaName ,''"
			// + " From Lis.es_doc_main c "
			// + " where c.subtype ='" + subtype + "' and not exists (select 'x' from lccont
			// where trim(lccont.prtno)=c.doccode) And c.Managecom Like '" +
			// strMngCom + "0%' And c.makedate >='" + strStartDate +
			// "'And c.makedate <='" + strEndDate + "' "
			// + " Group By c.doccode,c.managecom,c.makedate"
			// + " Order By c.managecom,c.makedate";

			// 新程序10/30/80
			strSQL = "Select Otherno, nvl(c.appntname,''),"
					+ " (Case  When c.Enteraccdate Is Null Then"
					+ "  '0'"
					+ "  Else"
					+ "   trim(cast(c.Paymoney as char(20)))"
					+ "  End) , '', '', c.Managecom, c.Makedate,"
					+ "  (Select Comcode From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '02') Subcom,"
					+ " (Select Name From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '02') Subcomname,"
					+ " (Select Comcode From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '03') Subcenter,"
					+ " (Select Name From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '03') Subcentername,"
					+ " (case when (Select Comcode From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '04') is not null then (Select Comcode From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '04')  else '0000' end) area,"
					+ " (case when (Select Name From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '04') is not null then (Select Name From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '04') else '无' end) Areaname, "
					+ " (case when (Select d.Username From Lduser d Where c.operator = d.Usercode ) is not null then (Select d.Username From Lduser d Where c.operator = d.Usercode )  else '无' end) Operator"
					+ " From Ljtempfeeclass c"
					+ " Where othernotype = '"
					+ subtype
					+ "' "
					+ payMode
					+ " And Not Exists (Select 'x' From Es_Doc_Main Where Trim(c.Otherno) = Es_Doc_Main.Doccode) "
					+ " and Not Exists (Select 'y' From lccont Where Trim(c.Otherno) = trim(lccont.prtno) ) "
					+ "  And c.Managecom Like concat('"
					+ "?strMngCom?"
					+ "','%') And c.Makedate >= to_date('"
					+ "?strStartDate?"
					+ "','yyyy-mm-dd')   And c.Makedate <= to_date('"
					+ "?strEndDate?"
					+ "','yyyy-mm-dd') "
					+ " group by otherno,c.Managecom, c.Makedate,c.appntname,c.Enteraccdate,c.Paymoney,c.operator"
					+ " Order By c.Managecom, c.Makedate";

		} else if (strNode.equals("04") || strNode.equals("05")) {
			if (strNode.equals("04")) {
				nodeNo = 3;
			} else if (strNode.equals("05")) {
				nodeNo = 4;
			}

			strSQL = "Select c.Contno, c.Appntname, (select (case when sum(prem) is not null then sum(prem)  else 0 end) from lcpol where contno=c.contno), c.Agentcode, a.Name, c.Managecom,c.makedate,"
					+ "   (Select Comcode From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '02') Subcom,"
					+ "   (Select Name From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '02') Subcomname,"
					+ "   (Select Comcode From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '03') Subcenter,"
					+ "  (Select Name From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '03') Subcentername,"
					+ "  (Select Comcode From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '04') Area,"
					+ "  (Select Name From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '04') AreaName,"
					+ "  (case when (Select d.Username From Lduser d, Lwmission e Where e.Defaultoperator = d.Usercode And e.Missionprop1 = Trim(c.Contno)) is not null then (Select d.Username From Lduser d, Lwmission e Where e.Defaultoperator = d.Usercode And e.Missionprop1 = Trim(c.Contno))  else '公共队列' end) Operator "
					+ "  From Lis.Lccont c, Lis.Laagent a"
					+ "  Where Trim(c.Agentcode) = (a.Agentcode)  And"
					+ "  c.Contno In (Select Rpad(Missionprop1, lislen('Lccont','contno'), ' ') "
					+ "   From Lis.Lwmission"
					+ "   Where Activityid In ("
					+ nodeStore[nodeNo]
					+ ") And makedate >='"
					+ "?strStartDate?"
					+ "'and makedate <='"
					+ "?strEndDate?"
					+ "'  ) And c.Managecom Like concat('"
					+ "?strMngCom?"
					+ "','%') And c.Salechnl = '"
					+ "?saleChannel?"
					+ "'"
					+ "   group by c.contno,c.managecom,c.appntname,c.agentcode,c.makedate,a.name Order By Subcom,Subcenter,c.makedate";

		}
		// 新程序录单流程
		else if (strNode.equals("01")) {

			if (saleChannel.equals("1")) {
				// subtype = "UA001";
				middle = "'01', '07', '08', '09', '10', 'TB01'";
			} else if (saleChannel.equals("3")) {
				middle = "'05', '12'";
			}

			nodeNo = 0;
			strSQL = " Select Missionprop1 As 投保单号, '', '', '','',"
					+ "  missionprop3,Makedate,"
					+ " (Select Comcode From Ldcom Where missionprop3 Like concat(Trim(Comcode) , '%') And Comgrade = '02') Subcom,"
					+ " (case when (Select Name From Ldcom Where missionprop3 Like concat(Trim(Comcode) , '%') And Comgrade = '02') is not null then (Select Name From Ldcom Where missionprop3 Like concat(Trim(Comcode) , '%') And Comgrade = '02')  else '无' end) Subcomname,"
					+ " (case when (Select Comcode From Ldcom Where missionprop3 Like concat(Trim(Comcode) , '%') And Comgrade = '03') is not null then (Select Comcode From Ldcom Where missionprop3 Like concat(Trim(Comcode) , '%') And Comgrade = '03')  else '000000' end) Subcenter,"
					+ " (case when (Select Name From Ldcom Where missionprop3 Like concat(Trim(Comcode) , '%') And Comgrade = '03') is not null then (Select Name From Ldcom Where missionprop3 Like concat(Trim(Comcode) , '%') And Comgrade = '03')  else '无' end) Subcentername,"
					+ " (case when (Select Comcode From Ldcom Where missionprop3 Like concat(Trim(Comcode) , '%') And Comgrade = '04') is not null then (Select Comcode From Ldcom Where missionprop3 Like concat(Trim(Comcode) , '%') And Comgrade = '04')  else '00000000' end) Area,"
					+ " (case when (Select Name From Ldcom Where missionprop3 Like concat(Trim(Comcode) , '%') And Comgrade = '04') is not null then (Select Name From Ldcom Where missionprop3 Like concat(Trim(Comcode) , '%') And Comgrade = '04')  else '直属分公司' end) Areaname,"
					+ " (case when (Select Username From Lduser Where Usercode = Defaultoperator and Activityid In ("
					+ nodeStore[nodeNo]
					+ ")) is not null then (Select Username From Lduser Where Usercode = Defaultoperator and Activityid In ("
					+ nodeStore[nodeNo]
					+ "))  else '公共队列' end) As 操作员"
					+ " From Lis.Lwmission"
					+ " Where Activityid In ("
					+ nodeStore[nodeNo]
					+ ") And Missionprop5 In ("
					+ middle
					+ ")  And length(trim(missionprop3))>4 "
					+ " And Makedate <'"
					+ "?strEndDate?"
					+ "' /*and defaultoperator Is Null*/ And Missionprop3 Like concat('"
					+ "?strMngCom?"
					+ "','%')" + " Order By  Missionprop3,Makedate";

		}
		// 05,12
		else {

			if (strNode.equals("02")) {
				nodeNo = 1;
				Missionprop = "Missionprop8";
			} else if (strNode.equals("03")) {
				nodeNo = 2;
				Missionprop = "Missionprop5";
			}
			// else if (strNode.equals("04"))
			// {
			// nodeNo = 3;
			// }
			// else if (strNode.equals("05"))
			// {
			// nodeNo = 4;
			// }
			else if (strNode.equals("08")) {
				nodeNo = 7;
				Missionprop = "MissionProp10";
			} else if (strNode.equals("09")) {
				nodeNo = 8;
				Missionprop = "MissionProp7";
			}

			else {
			}
			strSQL = "Select c.Contno, c.Appntname, (select (case when sum(prem) is not null then sum(prem)  else 0 end) from lcpol where contno=c.contno), c.Agentcode, a.Name, c.Managecom,c.makedate,"
					+ "   (Select Comcode From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '02') Subcom,"
					+ "   (Select Name From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '02') Subcomname,"
					+ "   (Select Comcode From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '03') Subcenter,"
					+ "  (Select Name From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '03') Subcentername,"
					+ "  (Select Comcode From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '04') Area,"
					+ "  (Select Name From Ldcom Where c.Managecom Like concat(Trim(Comcode) , '%') And Comgrade = '04') AreaName,"
					+ "  (case when (Select d.Username From Lduser d, Lwmission e Where e.defaultoperator = d.Usercode And e.Missionprop1 = Trim(c.Contno) and e.Activityid In ("
					+ nodeStore[nodeNo]
					+ ") ) is not null then (Select d.Username From Lduser d, Lwmission e Where e.defaultoperator = d.Usercode And e.Missionprop1 = Trim(c.Contno) and e.Activityid In ("
					+ nodeStore[nodeNo]
					+ ") )  else '公共队列' end) Operator "
					+ "  From Lis.Lccont c, Lis.Laagent a"
					+ "  Where Trim(c.Agentcode) = (a.Agentcode)  And"
					+ "  c.Contno In (Select Rpad(Missionprop1, lislen('Lccont','contno'), ' ') "
					+ "   From Lis.Lwmission"
					+ "   Where Activityid In ("
					+ nodeStore[nodeNo]
					+ ") And makedate >='"
					+ "?strStartDate?"
					+ "'and makedate <='"
					+ "?strEndDate?"
					+ "' and  "
					+ Missionprop
					+ " Like concat('"
					+ "?strMngCom?"
					+ "','%') ) And c.Managecom Like concat('"
					+ "?strMngCom?"
					+ "','%') And c.Salechnl = '"
					+ "?saleChannel?"
					+ "'"
					+ "   group by c.contno,c.managecom,c.appntname,c.agentcode,c.makedate,a.name Order By Subcom,Subcenter,c.makedate";

		}
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(strSQL);
		sqlbv4.put("strMngCom", strMngCom);
		sqlbv4.put("saleChannel", saleChannel);
		sqlbv4.put("strEndDate", strEndDate);
		sqlbv4.put("strStartDate", strStartDate);
		ExeSQL exeSQL = new ExeSQL();
		SSRS ssrs = exeSql.execSQL(sqlbv4);
		int i_count = ssrs.getMaxRow();

		if (i_count == 0) {
			logger.debug("没有可打印的信息");
			buildError("getprintData", "没有需要打印的信息1");
			return false;
		}
		int areaNoBefore = 0;
		int areaNoAfter = 0;
		areaNo = 1;
		if ((strMngCom.substring(0, 4).equals("8621"))
				&& (comGrade.equals("4") || comGrade.equals("3"))) {
			for (int i = 1; i < i_count; i++) {
				try {
					areaNoBefore = Integer.parseInt(ssrs.GetText(i, 12));
					areaNoAfter = Integer.parseInt(ssrs.GetText(i + 1, 12));

					if (areaNoBefore != areaNoAfter) {
						areaNo++;
					}
				} catch (Exception ex) {
					buildError("getprintData", "后台有脏数据！！");
					logger.debug("没有需要打印的信息2");
					return false;

				}
			}

		} else {
			for (int i = 1; i < i_count; i++) {
				try {
					areaNoBefore = Integer.parseInt(ssrs.GetText(i, 10));
					areaNoAfter = Integer.parseInt(ssrs.GetText(i + 1, 10));

					if (areaNoBefore != areaNoAfter) {
						areaNo++;
					}
				} catch (Exception ex) {
					buildError("getprintData", "后台有脏数据！！");
					logger.debug("没有需要打印的信息");
					return false;

				}
			}
		}
		int countk = -1;
		String[] strVFPathName = new String[areaNo]; // 临时文件个数。
		// newXmlExport = new XmlExport();
		// 新建一个XmlExport的实例
		String remark = "newNBStatsLis.vts";
		newXmlExport.createDocuments(remark, mGlobalInput);

		String newArea = "";
		String newAreaName = "";
		String oldArea = "";
		String oldAreaName = "";
		String oldSubCom = ""; // 分公司
		String oldSubComName = "";
		String newSubCom = "";
		String newSubComName = "";
		String oldSubcenterCom = "";
		String oldSubcenterComName = "";
		String newSubcenterCom = "";
		String newSubcenterComName = ""; // 中支公司

		boolean display = false;
		for (int k = 0; k < areaNo; k++) {
			countk = countk + 1;
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("NBStatsLis.vts", "printer");
			TextTag texttag = new TextTag();
			ListTable alistTable = new ListTable();
			alistTable.setName("RISK");
			sumBills = 0;
			// Appoint to a sepcial flag

			int numberCount = 1;

			for (int j = getJ; j < i_count + 1; j++) {

				newArea = ssrs.GetText(j, 12).trim();
				newAreaName = ssrs.GetText(j, 13).trim();
				newSubCom = ssrs.GetText(j, 8).trim();
				newSubComName = ssrs.GetText(j, 9).trim();
				newSubcenterCom = ssrs.GetText(j, 10).trim();
				newSubcenterComName = ssrs.GetText(j, 11).trim();
				if ((strMngCom.substring(0, 4).equals("8621"))
						&& (comGrade.equals("4") || comGrade.equals("3"))) {
					if ((!newArea.equals(oldArea) && j != 1)) {

						getJ = j;
						oldArea = newArea;
						// oldSubcenterCom = newSubcenterCom;
						sumBillCom = 0;

						display = true;

						break;
					}

				} else {
					if ((!newSubcenterCom.equals(oldSubcenterCom) && j != 1)) {

						getJ = j;
						// oldArea = newArea;
						oldSubcenterCom = newSubcenterCom;
						sumBillCom = 0;

						display = true;

						break;
					}

				}

				try {
					String[] cols = new String[8];
					cols[0] = numberCount + "";
					cols[1] = ssrs.GetText(j, 1);
					cols[2] = ssrs.GetText(j, 2);
					cols[3] = ssrs.GetText(j, 3);
					cols[4] = ssrs.GetText(j, 4);
					cols[5] = ssrs.GetText(j, 5);
					cols[6] = ssrs.GetText(j, 14);
					cols[7] = ssrs.GetText(j, 7);
					alistTable.add(cols);
					logger.debug("取数成功" + cols[0] + " " + cols[1] + " "
							+ cols[2] + "  " + cols[3] + "  " + cols[4] + "  "
							+ cols[5]);

				} catch (Exception ex) {
					buildError("getprintData", "没有需要打印的信息");
					logger.debug("没有需要打印的信息3");
					return false;

				}
				oldArea = newArea;
				oldSubcenterCom = newSubcenterCom;
				oldAreaName = newAreaName;
				oldSubComName = newSubComName;
				oldSubcenterComName = newSubcenterComName;

				numberCount++;
				sumBills++;
				sumBillCom++;

			}
			String[] col = new String[1];

			// logger.debug("====="+alistTable.size());
			xmlexport.addListTable(alistTable, col);
			if (k == 0) {

				xmlexport.addDisplayControl("displayTitle1");

			}
			if (k == areaNo - 1) {
				xmlexport.addDisplayControl("displayTitle2");

			}
			display = false;
			texttag.add("SysDate1", strStartDate); // 出单日期
			texttag.add("SysDate2", strEndDate);
			// texttag.add("ManageComName", oldSubComName); //分公司
			if ((strMngCom.substring(0, 4).equals("8621"))
					&& (comGrade.equals("4") || comGrade.equals("3"))) {
				texttag.add("ManageComName", oldAreaName); // 营销服务部
				// logger.debug(oldAreaName);
			} else {
				texttag.add("ManageComName", oldSubcenterComName); // 中心支公司
			}
			// texttag.add("ManageArea", oldAreaName); //银行
			texttag.add("SumAll", i_count); // 分公司件数
			texttag.add("SumBillCom", sumBills); // 中心支公司件数
			texttag.add("NodeName", nodeName);
			// texttag.add("SumBills", sumBills); //营业区件数

			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}

			CombineVts tcombineVts = null;
			tcombineVts = new CombineVts(xmlexport.getInputStream(),
					strTemplatePath);

			ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
			tcombineVts.output(dataStream);
			strVFPathName[countk] = strTemplatePath + countk + "NBStatsLis.vts";
			// 把dataStream存储到磁盘文件
			// logger.debug("存储文件到"+strVFPathName);
			AccessVtsFile.saveToFile(dataStream, strVFPathName[countk]);

			// 把所有xml文件拼成一个xml文件。
			newXmlExport.addDataSet(
					newXmlExport.getDocument().getRootElement(), xmlexport
							.getDocument().getRootElement());

		}
		VtsFileCombine vtsfilecombine = new VtsFileCombine();

		String[] strNewVFPathName = new String[countk + 1];
		for (int b = 0; b < countk + 1; b++) {
			strNewVFPathName[b] = strVFPathName[b];
		}
		try {
			BookModelImpl tb = vtsfilecombine.dataCombine(strNewVFPathName);
			vtsfilecombine.write(tb, strTemplatePath + "newNBStatsLis.vts");
		} catch (IOException ex) {
		} catch (F1Exception ex) {
		} catch (Exception ex) {
			buildError("getprintData", "没有需要打印的信息");
			logger.debug("没有需要打印的信息4");
			return false;
		}

		// newXmlExport.outputDocumentToFile("d:\\", "NBStatsLis"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(strTemplatePath + "newNBStatsLis.vts");
		// logger.debug("from back"+strTemplatePath+
		// "newBankAgentOutLis.vts");
		mResult.addElement(newXmlExport);
		return true;

	}

	// * @function :in order to debug

	public static void main(String[] args) {
		String tStrMngCom = "8621";
		String tStartDate = "2006-10-1";
		String tEndDate = "2006-11-1";
		String tNode = "01";
		String tSaleChannel = "02";
		VData tVData = new VData();
		tVData.addElement(tStrMngCom);
		tVData.addElement(tEndDate);
		tVData.addElement(tNode);
		tVData.addElement(tSaleChannel);
		tVData.addElement(tStartDate);
		NBStatsLisUI1 tNBStatsLisUI1 = new NBStatsLisUI1();
		tNBStatsLisUI1.submitData(tVData, "PRINT");
	};
}
