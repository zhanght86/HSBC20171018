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

public class NBPreNoLisBL {
private static Logger logger = Logger.getLogger(NBPreNoLisBL.class);
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
	private int sumBills = 0;
	private int areaNo = 0;
	private int sumBillCom = 0;
	String[] nodeStore = new String[9];
	private XmlExport newXmlExport = new XmlExport();
	private int getJ = 1;
	private GlobalInput mGlobalInput = new GlobalInput();
	private int nodeNo = 0;
	private String strSaleCh = "0";
	private String saleChannel = "0";
	private String subtype = "";
	private String strRiskT = "";
	private String twebpath = "";

	public NBPreNoLisBL() {
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
		// strNode=(String) cInputData.get(2);
		strSaleCh = (String) cInputData.get(3);
		twebpath = (String) cInputData.get(4);

		for (int i = 0; i < cInputData.size(); i++) {
			logger.debug("&&&" + cInputData.get(i).toString());
		}
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
		cError.moduleName = "NBPreNoLisBL";
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
			saleChannel = "6";
			strRiskT = "I";
		} else if (strSaleCh.equals("02")) {
			saleChannel = "7";
			strRiskT = "Y";
		} else if (strSaleCh.equals("03")) {
			strRiskT = "T";
		}

		ExeSQL exeSql = new ExeSQL();
		SSRS testSSRS = new SSRS();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql("select sysvarvalue From ldsysvar where sysvar='DataPrintCombine'");
		testSSRS = exeSql.execSQL(sqlbv1);
		String strTemplatePath = twebpath;
		// String strTemplatePath = testSSRS.GetText(1, 1);
		// //数据库中的路径-模板路径D:/lis/ui/f1print/NCLtemplate/
		// sString strTemplatePath = "G:/NewSys/ui/f1print/NCLtemplate/";
		// String strTemplatePath = "D:/lis/ui/f1print/NCLtemplate/";
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
		if (comGrade.equals("1")) // 总公司
		{
			buildError("getprintData", "只能打印分公司、中心支公司或者营销服务部的清单");
			logger.debug("只能打印分公司、中心支公司或者营销服务部的清单");
			return false;
		}
		if (comGrade.equals("2")) // 分公司
		{
			strSQL = "Select a.otherno,a.tempfeeno,sum(a.paymoney),a.agentcode,"
					+ " (select name from laagent c where c.agentcode = trim(a.agentcode)) Agentname,"
					+ " a.agentgroup,a.makedate,a.enteraccdate,"
					+ " (Select Comcode From Ldcom Where a.policycom Like concat(concat(Trim(Comcode) , '%')) And Comgrade = '02') Subcom,"
					+ " (Select Name From Ldcom Where a.policycom Like concat(concat(Trim(Comcode) , '%')) And Comgrade = '02') Subcomname,"
					+ " (Select Comcode From Ldcom Where a.policycom Like concat(concat(Trim(Comcode) , '%')) And Comgrade = '03') Subcenter,"
					+ " (Select Name From Ldcom Where a.policycom Like concat(concat(Trim(Comcode) , '%')) And Comgrade = '03') Subcentername"
					+ " From Ljtempfee a , lis.lmriskapp b"
					+ " Where 1=1"
					+ " and not exists (select 'x' from lccont where contno = a.otherno)"
					+ " And Not Exists (select 'x' from ljagettempfee where tempfeeno = a.tempfeeno)"
					+ " And Not Exists (Select 'x' From Lis.Es_Doc_Relation Where Bussno = Trim(a.Otherno) And Bussnotype = '11' And Subtype In ('TB000', 'UA001', 'UA060', 'UF001', 'UF002', 'UO060', 'UA006', 'UA002', 'UA003', 'UA004','UA005', 'UA081', 'UA009')) "
					+ " And b.riskprop='"
					+ "?strRiskT?"
					+ "' And b.riskcode=a.riskcode  "
					+ " and a.makedate >='"
					+ "?strStartDate?"
					+ "'and a.makedate <='"
					+ "?strEndDate?"
					+ "'"
					+ " And a.policycom Like concat('"
					+ "?strMngCom?"
					+ "','%')"
					+ " group by a.otherno,a.policycom,a.tempfeeno,a.makedate,a.enteraccdate,a.agentcode,a.agentgroup"
					+ " order by Subcom,Subcenter,a.enteraccdate,a.otherno";
		}
		if (comGrade.equals("3")) // 中心支公司
		{
			strSQL = "Select a.otherno,a.tempfeeno,sum(a.paymoney),a.agentcode,"
					+ " (select name from laagent c where c.agentcode = trim(a.agentcode)) Agentname,"
					+ " a.agentgroup,a.makedate,a.enteraccdate,"
					+ " (Select Comcode From Ldcom Where a.policycom Like concat(concat(Trim(Comcode) , '%')) And Comgrade = '03') Subcom,"
					+ " (Select Name From Ldcom Where a.policycom Like concat(concat(Trim(Comcode) , '%')) And Comgrade = '03') Subcomname,"
					+ " (case when (Select Comcode From Ldcom Where a.policycom Like concat(concat(Trim(Comcode) , '%')) And Comgrade = '04') is not null then (Select Comcode From Ldcom Where a.policycom Like concat(concat(Trim(Comcode) , '%')) And Comgrade = '04')  else '000' end) Subcenter,"
					+ " (case when (Select Name From Ldcom Where a.policycom Like concat(concat(Trim(Comcode) , '%')) And Comgrade = '04') is not null then (Select Name From Ldcom Where a.policycom Like concat(concat(Trim(Comcode) , '%')) And Comgrade = '04')  else '无法确定机构' end) Subcentername"
					+ " From Ljtempfee a , lis.lmriskapp b"
					+ " Where 1=1"
					+ " and not exists (select 'x' from lccont where contno = a.otherno)"
					+ " And Not Exists (select 'x' from ljagettempfee where tempfeeno = a.tempfeeno)"
					+ " And Not Exists (Select 'x' From Lis.Es_Doc_Relation Where Bussno = Trim(a.Otherno) And Bussnotype = '11' And Subtype In ('TB000', 'UA001', 'UA060', 'UF001', 'UF002', 'UO060', 'UA006', 'UA002', 'UA003', 'UA004','UA005', 'UA081', 'UA009')) "
					+ " And b.riskprop='"
					+ "?strRiskT?"
					+ "' And b.riskcode=a.riskcode  "
					+ " and a.makedate >='"
					+ "?strStartDate?"
					+ "'and a.makedate <='"
					+ "?strEndDate?"
					+ "'"
					+ " And a.policycom Like concat('"
					+ "?strMngCom?"
					+ "','%')"
					+ " group by a.otherno,a.policycom,a.tempfeeno,a.makedate,a.enteraccdate,a.agentcode,a.agentgroup"
					+ " order by Subcom,Subcenter,a.enteraccdate,a.otherno";

		}
		if (comGrade.equals("4") && strMngCom.substring(0, 4).equals("8621")) // 营销服务部且为北分的数据
		{
			strSQL = "Select a.otherno,a.tempfeeno,sum(a.paymoney),a.agentcode,"
					+ " (select name from laagent c where c.agentcode = trim(a.agentcode)) Agentname,"
					+ " a.agentgroup,a.makedate,a.enteraccdate,"
					+ " (Select Comcode From Ldcom Where a.policycom Like concat(concat(Trim(Comcode) , '%')) And Comgrade = '04') Subcom,"
					+ " (Select Name From Ldcom Where a.policycom Like concat(concat(Trim(Comcode) , '%')) And Comgrade = '04') Subcomname,"
					+ " (case when (Select Comcode From Ldcom Where a.policycom Like concat(concat(Trim(Comcode) , '%')) And Comgrade = '04') is not null then (Select Comcode From Ldcom Where a.policycom Like concat(concat(Trim(Comcode) , '%')) And Comgrade = '04')  else '000' end) Subcenter,"
					+ " (case when (Select Name From Ldcom Where a.policycom Like concat(concat(Trim(Comcode) , '%')) And Comgrade = '04') is not null then (Select Name From Ldcom Where a.policycom Like concat(concat(Trim(Comcode) , '%')) And Comgrade = '04')  else '无法确定机构' end) Subcentername"
					+ " From Ljtempfee a , lis.lmriskapp b"
					+ " Where 1=1"
					+ " and not exists (select 'x' from lccont where contno = a.otherno)"
					+ " And Not Exists (select 'x' from ljagettempfee where tempfeeno = a.tempfeeno)"
					+ " And Not Exists (Select 'x' From Lis.Es_Doc_Relation Where Bussno = Trim(a.Otherno) And Bussnotype = '11' And Subtype In ('TB000', 'UA001', 'UA060', 'UF001', 'UF002', 'UO060', 'UA006', 'UA002', 'UA003', 'UA004','UA005', 'UA081', 'UA009')) "
					+ " And b.riskprop='"
					+ "?strRiskT?"
					+ "' And b.riskcode=a.riskcode  "
					+ " and a.makedate >='"
					+ "?strStartDate?"
					+ "'and a.makedate <='"
					+ "?strEndDate?"
					+ "'"
					+ " And a.policycom Like concat('"
					+ "?strMngCom?"
					+ "','%') "
					+ " group by a.otherno,a.policycom,a.tempfeeno,a.makedate,a.enteraccdate,a.agentcode,a.agentgroup"
					+ " order by Subcom,Subcenter,a.enteraccdate,a.otherno";
		} else if (comGrade.equals("4")
				&& !strMngCom.substring(0, 4).equals("8621"))// 营销服务部但不为北分
		{
			buildError("getprintData", "只能打印分公司,中心支公司的清单");
			logger.debug("只能打印分公司,中心支公司的清单");
			logger.debug(strMngCom.substring(0, 4));
			return false;
		}
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(strSQL);
		sqlbv3.put("strRiskT", strRiskT);
		sqlbv3.put("strStartDate", strStartDate);
		sqlbv3.put("strEndDate", strEndDate);
		sqlbv3.put("strMngCom", strMngCom);
		ExeSQL exeSQL = new ExeSQL();
		SSRS ssrs = exeSql.execSQL(sqlbv3);
		int i_count = ssrs.getMaxRow();

		if (i_count == 0) {
			logger.debug("没有可打印的信息");
			buildError("getprintData", "没有需要打印的信息");
			return false;
		}
		int areaNoBefore = 0;
		int areaNoAfter = 0;
		areaNo = 1;

		for (int i = 1; i < i_count; i++) {
			try {
				if (ssrs.GetText(i, 11) != null
						&& !ssrs.GetText(i, 11).equals("")) {
					areaNoBefore = Integer.parseInt(ssrs.GetText(i, 11));
				}
				if (ssrs.GetText(i + 1, 11) != null
						&& !ssrs.GetText(i + 1, 11).equals("")) {
					areaNoAfter = Integer.parseInt(ssrs.GetText(i + 1, 11));
				}
				if (areaNoBefore != areaNoAfter) {
					areaNo++;
				}
			} catch (Exception ex) {
				buildError("getprintData", "后台有脏数据！！");
				logger.debug("后台有脏数据");
				return false;

			}

		}
		int countk = -1;
		String[] strVFPathName = new String[areaNo]; // 临时文件个数。
		// newXmlExport = new XmlExport();
		// 新建一个XmlExport的实例
		String remark = "newNBPreNoLis.vts";
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
			xmlexport.createDocument("NBPreNoLis.vts", "printer");
			TextTag texttag = new TextTag();
			ListTable alistTable = new ListTable();
			alistTable.setName("RISK");
			sumBills = 0;
			// Appoint to a sepcial flag

			int numberCount = 1;
			for (int j = getJ; j < i_count + 1; j++) {
				newSubCom = ssrs.GetText(j, 9).trim();
				newSubComName = ssrs.GetText(j, 10).trim();
				logger.debug(newSubComName);
				newSubcenterCom = ssrs.GetText(j, 11).trim();
				newSubcenterComName = ssrs.GetText(j, 12).trim();
				logger.debug(newSubcenterComName);
				logger.debug(newSubComName + "----" + newSubcenterComName);
				if ((!newSubcenterCom.equals(oldSubcenterCom) && j != 1)) {
					getJ = j;
					// oldArea = newArea;
					oldSubcenterCom = newSubcenterCom;
					sumBillCom = 0;
					display = true;
					break;
				}

				try {
					String[] cols = new String[9];
					cols[0] = numberCount + "";
					cols[1] = ssrs.GetText(j, 1);
					cols[2] = ssrs.GetText(j, 2);
					cols[3] = ssrs.GetText(j, 3);
					cols[4] = ssrs.GetText(j, 4);
					cols[5] = ssrs.GetText(j, 5);
					cols[6] = ssrs.GetText(j, 6);
					cols[7] = ssrs.GetText(j, 7);
					cols[8] = ssrs.GetText(j, 8);
					alistTable.add(cols);
					// logger.debug("取数成功" + cols[0] + " " + cols[1] + " "
					// + cols[2] + " " + cols[3] + " " + cols[4] + " " +
					// cols[5]+" "+cols[6] + " " + cols[7] + " " + cols[8] );

				} catch (Exception ex) {
					buildError("getprintData", "没有需要打印的信息");
					logger.debug("没有需要打印的信息");
					return false;

				}
				// oldArea = newArea;
				oldSubcenterCom = newSubcenterCom;
				// oldAreaName=newAreaName;
				oldSubComName = newSubComName;
				oldSubcenterComName = newSubcenterComName;
				logger.debug("aaa" + newSubcenterComName);

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
			texttag.add("SysDate1", strStartDate); // 起始日期
			texttag.add("SysDate2", strEndDate); // 截至日期

			texttag.add("ManageComName", newSubComName); // 查询机构

			logger.debug("bbb" + newSubcenterComName);
			// if (!strMngCom.substring(0, 4).equals("8621"))
			// {
			// texttag.add("ComName", newSubcenterComName); //二层机构
			// logger.debug("***********************");
			// logger.debug("***********************");
			// logger.debug("newSubcenterComName="+newSubcenterComName);
			// logger.debug("***********************");
			// logger.debug("***********************");
			// }
			logger.debug("&&&&&&&&&&&&&&&&&&&&&&&");
			logger.debug("&&&&&&&&&&&&&&&&&&&&&&&");
			logger.debug("oldSubcenterComName=" + oldSubcenterComName);
			logger.debug("&&&&&&&&&&&&&&&&&&&&&&&");
			logger.debug("&&&&&&&&&&&&&&&&&&&&&&&");
			texttag.add("ComName", oldSubcenterComName); // 二层机构
			texttag.add("SumAll", i_count); // 总件数
			texttag.add("SumBillCom", sumBills); // 二层机构件数

			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}

			CombineVts tcombineVts = null;
			tcombineVts = new CombineVts(xmlexport.getInputStream(),
					strTemplatePath);

			ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
			tcombineVts.output(dataStream);
			strVFPathName[countk] = strTemplatePath + countk + "NBPreNoLis.vts";
			logger.debug(strVFPathName[0]);
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
			vtsfilecombine.write(tb, strTemplatePath + "newNBPreNoLis.vts");
		} catch (IOException ex) {
		} catch (F1Exception ex) {
		} catch (Exception ex) {
			buildError("getprintData", "没有需要打印的信息");
			logger.debug("3没有需要打印的信息");
			return false;
		}

		// newXmlExport.outputDocumentToFile("d:\\", "NBPreNoLis"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(strTemplatePath + "newNBPreNoLis.vts");
		// logger.debug("from back"+strTemplatePath+
		// "newBankAgentOutLis.vts");
		mResult.addElement(newXmlExport);
		return true;

	}

	/*
	 * @function :in order to debug
	 */
	public static void main(String[] args) {
		String tStrMngCom = "8621";
		String tStartDate = "2006-04-19";
		String tEndDate = "2006-04-19";
		String tSaleChannel = "01";
		String tPath = "D:\\lis\\ui\\f1print\\NCLtemplate/";
		VData tVData = new VData();
		tVData.addElement(tStrMngCom);
		tVData.addElement(tStartDate);
		tVData.addElement(tEndDate);
		tVData.addElement(tSaleChannel);
		tVData.addElement(tPath);
		NBPreNoLisBL tNBPreNoLisBL = new NBPreNoLisBL();
		tNBPreNoLisBL.submitData(tVData, "PRINT");
	}
}
