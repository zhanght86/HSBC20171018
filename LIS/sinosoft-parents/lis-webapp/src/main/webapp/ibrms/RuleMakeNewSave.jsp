<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page import="java.util.*"%>
<%@page import="com.sinosoft.ibrms.RuleMakeUI"%>
<%@ page import="com.sinosoft.utility.*"%>
<%@ page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<!--用户校验类-->
<%
	request.setCharacterEncoding("GBK");
	//接收信息，并作校验处理。
	//输入参数
	//操作类型；
	String flag = request.getParameter("flag");

	String Operation = request.getParameter("Operation");

	String TableName = request.getParameter("TableName");

	String BOMS = request.getParameter("BOMS");
	String SQLPara = request.getParameter("SQLPara");
	String ViewPara = request.getParameter("ViewPara");
	String SQLStatement = request.getParameter("SQLStatement");
	String CalSQLStatement = request.getParameter("CalSQLStatement");
	String CreateTable = request.getParameter("CreateTable");
	String RuleCh = request.getParameter("RuleCh");
	loggerDebug("RuleMakeNewSave","RuleCh::" + RuleCh);
	String RuleName = request.getParameter("RuleName");
	loggerDebug("RuleMakeNewSave","RuleName::" + RuleName);
	String RuleDes = request.getParameter("RuleDes");
	loggerDebug("RuleMakeNewSave","RuleDes::" + RuleDes);
	String Creator = request.getParameter("Creator");
	String RuleStartDate = request.getParameter("RuleStartDate");
	String RuleEndDate = request.getParameter("RuleEndDate");
	String TempalteLevel = request.getParameter("TempalteLevel");
	String Business = request.getParameter("Business");
	String State = request.getParameter("State");
	String Valid = request.getParameter("Valid");
	String ColumnDataType = request.getParameter("ColumnDataType");
	String TableColumnName = request.getParameter("TableColumnName");
	String JsonDataFromExcel = request.getParameter("JsonDataFromExcelName");
	
	String riskcode = request.getParameter("riskcode");


  //tongmeng 2010-12-17 add
  String CalRuleCh = request.getParameter("CalRuleCh");
  
  String RuleTableName = request.getParameter("RuleTableName");
	loggerDebug("RuleMakeNewSave","CalRuleCh::" + CalRuleCh);
	String[] TableColumnNameArray = TableColumnName.split(",");
	String[] ColumnDataTypeArray = ColumnDataType.split(",");
	String mIntegerFlag = request.getParameter("mIntegerFlag");
	Map DTColumnTypeMap = new HashMap();

	if(TableColumnNameArray.length != ColumnDataTypeArray.length){
		loggerDebug("RuleMakeNewSave","字段和录入的决策表数量不符");
		out.println("<script>alert(字段和录入的决策表数量不符);</script>");
		return;
	}
	
	for (int i = 0; i < TableColumnNameArray.length; i++) {
		loggerDebug("RuleMakeNewSave",TableColumnNameArray[i]);
		loggerDebug("RuleMakeNewSave",ColumnDataTypeArray[i]);
		DTColumnTypeMap.put(TableColumnNameArray[i],
				ColumnDataTypeArray[i]);
	}

	loggerDebug("RuleMakeNewSave","RuleName::" + RuleName);
	loggerDebug("RuleMakeNewSave","RuleDes::" + RuleDes);

	String DTData = request.getParameter("DTData");
	if(JsonDataFromExcel!=null && !JsonDataFromExcel.equals("")){
		DTData = JsonDataFromExcel;
	}
	String Types = request.getParameter("Types");
	String LRTemplate_ID = request.getParameter("LRTemplate_ID");

	loggerDebug("RuleMakeNewSave","BOMS::" + BOMS);
	loggerDebug("RuleMakeNewSave","Parameter::" + SQLPara);
	loggerDebug("RuleMakeNewSave","ViewParameter::" + ViewPara);
	loggerDebug("RuleMakeNewSave","SQLStatement::" + SQLStatement);
	loggerDebug("RuleMakeNewSave","CreateTable::" + CreateTable);
	loggerDebug("RuleMakeNewSave","DTData::" + DTData);
	loggerDebug("RuleMakeNewSave","DTColumnTypeMap::" + DTColumnTypeMap);
	loggerDebug("RuleMakeNewSave","ColumnDataType::" + ColumnDataType);

	//String[] TypesArray = Types.split(",");

	//获取记录的最大条数
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("flag", flag);
	tTransferData.setNameAndValue("Operation", Operation);
	tTransferData.setNameAndValue("TableName", TableName);
	tTransferData.setNameAndValue("LRTemplate_ID", LRTemplate_ID);

	tTransferData.setNameAndValue("RiskCode", riskcode);
	
	tTransferData.setNameAndValue("BOMS", BOMS);
	tTransferData.setNameAndValue("SQLPara", SQLPara);
	tTransferData.setNameAndValue("ViewPara", ViewPara);
	tTransferData.setNameAndValue("SQLStatement", SQLStatement);
	tTransferData.setNameAndValue("CalSQLStatement", CalSQLStatement);
	tTransferData.setNameAndValue("CreateTable", CreateTable);
	tTransferData.setNameAndValue("RuleCh", RuleCh);

	tTransferData.setNameAndValue("RuleName", RuleName);
	tTransferData.setNameAndValue("RuleDes", RuleDes);
	tTransferData.setNameAndValue("Creator", Creator);
	tTransferData.setNameAndValue("RuleStartDate", RuleStartDate);
	tTransferData.setNameAndValue("RuleEndDate", RuleEndDate);
	tTransferData.setNameAndValue("TempalteLevel", TempalteLevel);
	tTransferData.setNameAndValue("Business", Business);
	tTransferData.setNameAndValue("State", State);
	tTransferData.setNameAndValue("Valid", Valid);

	tTransferData.setNameAndValue("DTData", DTData);
	tTransferData.setNameAndValue("DTColumnTypeMap", DTColumnTypeMap);
	tTransferData.setNameAndValue("CalRuleCh", CalRuleCh);
	tTransferData.setNameAndValue("RuleTableName", RuleTableName);
	tTransferData.setNameAndValue("RuleType", "1");
	//tTransferData.setNameAndValue("XMLSavePath", sXMLSavePath);
	// tTransferData.setNameAndValue("XMLSavePath", sXMLSavePath);
	GlobalInput tGlobalInput = new GlobalInput();
	tGlobalInput = (GlobalInput) session.getValue("GI");
	VData tVData = new VData();
	tVData.add(tGlobalInput);
	tVData.add(tTransferData);

	CErrors tErrors = new CErrors();
	String busiName="RuleMakeUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	tBusinessDelegate.submitData(tVData, "",busiName);
	
//	VData returnVData =  tRuleMakeUI.getResult();
//	TransferData returnTransferData = (TransferData)returnVData.getObjectByObjectName("TransferData",0);
//	String tempSQL = (String)returnTransferData.getValueByName("SQL");
//	String tempSQLPara = (String)returnTransferData.getValueByName("SQLPara");
//	String tempBOMS = (String)returnTransferData.getValueByName("BOMS");
//	String returnSQL = tempSQL+"~"+tempSQLPara+"~"+tempBOMS;
//	loggerDebug("RuleMakeNewSave","In RuleMakeSave 返回的SQL串："+returnSQL);
	
%>
<script language="javascript">
var flag=<%=flag%>;
var url="";
//tongmeng 2011-02-15
var tmIntegerFlag = <%=mIntegerFlag%>;
//alert(fm.all('mIntegerFlag').value);

if(tmIntegerFlag=='1')
{
	parent.onRiskTabChange('1');
}
else
{
	if(flag==1)
	{
    	url='RuleInfor.jsp?flag=';
		}
	else
	{
		url='RuleQuery.jsp?flag=';
	}
	url+=flag;
		document.location.href = url;
}
</script>

<%@page import="org.jdom.input.SAXBuilder"%>
<%@page import="org.jdom.Document"%>
<%@page import="org.jdom.Element"%>
<%@page import="java.io.StringReader"%>
<%@page import="org.json.JSONException"%>
<%@page import="org.jdom.JDOMException"%></html>
