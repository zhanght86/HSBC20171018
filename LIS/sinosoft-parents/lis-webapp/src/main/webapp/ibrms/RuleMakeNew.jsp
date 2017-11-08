<%@page language="java" import="java.util.*"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//获取规则定制时传入的基本信息
	String RuleName = request.getParameter("RuleName");
	//RuleName = "R_01";
	loggerDebug("RuleMakeNew","RuleName::" + RuleName);
	String RuleDes = request.getParameter("RuleDes");
	loggerDebug("RuleMakeNew","RuleDes::" + RuleDes);
	String Creator = request.getParameter("Creator");
	//Creator = "001";
	loggerDebug("RuleMakeNew","Creator::" + Creator);
	String RuleStartDate = request.getParameter("RuleStartDate");
	//RuleStartDate = "2010-12-15";
	loggerDebug("RuleMakeNew","RuleStartDate::" + RuleStartDate);
	String RuleEndDate = request.getParameter("RuleEndDate");
	loggerDebug("RuleMakeNew","RuleEndDate::" + RuleEndDate);
	String TempalteLevel = request.getParameter("TempalteLevel");
	//TempalteLevel = "01";
	loggerDebug("RuleMakeNew","TempalteLevel::" + TempalteLevel);
	
	String Business = request.getParameter("Business");
	//Business = "99";
	loggerDebug("RuleMakeNew","Business::" + Business);
	String State = request.getParameter("State");
	//State = "0";
	loggerDebug("RuleMakeNew","State::" + State);
	String Valid = request.getParameter("Valid");
	String riskcode = request.getParameter("riskcode");
	//Valid = "1";
	loggerDebug("RuleMakeNew","Valid::" + Valid);

	//获取操作类型标志
	String flag = (String) request.getParameter("flag");
	//flag ="1";
	loggerDebug("RuleMakeNew","flag::" + flag);
	//获取规则表主键标志：主要用于规则的修改、复制和查看时用于查找规则的主键
	String LRTemplate_ID = (String) request
			.getParameter("LRTemplate_ID");
	loggerDebug("RuleMakeNew","LTRemplate_ID::" + LRTemplate_ID);
	//获取规则来源的表名：主要用于规则的修改、复制和查看时用于查找规则的表
	String LRTemplateName = (String) request
			.getParameter("LRTemplateName");
	loggerDebug("RuleMakeNew","LRTemplateName::" + LRTemplateName);
	//tongmeng 2011-02-15 add
	//判断是否集成部署
	String IntegerFlag = request.getParameter("IntegerFlag")==null?"0":(String) request.getParameter("IntegerFlag");
	
	loggerDebug("RuleMakeNew","IntegerFlag::" + IntegerFlag);	
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>规则定制</title>
<link rel="stylesheet" type="text/css" href="./resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/rule.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/examples.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/grid-examples.css" />
<link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
<link rel="stylesheet" type="text/css" href="./resources/css/Ext.form.FileUploadField.css">


<script type="text/javascript" src="./baseLib/ext-base.js"></script>
<script type="text/javascript" src="./baseLib/ext-all.js"></script>
<script type="text/javascript" src="./baseLib/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="./JavaScript/Ext.form.FileUploadField.js"></script>
<script type="text/javascript" src="./baseLib/multiSelectCbox.js"></script>
<script type="text/javascript" src="./JavaScript/makeLogicalNew.js"></script>
<script type="text/javascript" src="./JavaScript/dicisionTableNew.js"></script>
<script type="text/javascript" src="./JavaScript/viewParameterNew.js"></script>
<script type="text/javascript" src="./JavaScript/RulePubFun.js"></script>

<script src="../common/javascript/jquery.js"></script>
<script src="../common/javascript/jquery.imageView.js"></script>
<script src="../common/javascript/jquery.easyui.min.js"></script>
<script type="text/javascript" src="./JavaScript/ajaxfileupload.js"></script>

<script type="text/javascript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css />
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script type="text/javascript">
       var flag=<%=flag%>;
       var LRTemplate_ID='<%=LRTemplate_ID%>';
       var State='<%=State%>';
       var LRTemplateName='<%=LRTemplateName%>';
       
			var tLanguage='zh';
			var mRuleName = '<%=RuleName%>';
			var mBusiness = '<%=Business%>';
			var mIBRMSDefType = '0';
			
			var RuleMapArray;
    </script>
<script type="text/javascript" src="./RuleMakeNew.js">
    </script>
<%@include file="./RuleMakeNewInit.jsp"%>

</head>
<body onload="BrowserCompatible();initAllRuleMap();">
<div id="RuleTableDiv">
 <table>
    <tr class=common>
    	<td class=common>
    		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" >
    		<td class=titleImg>指定数据表:</td>
    	</td>
    </tr>
 </table>
 <table>
    <tr class=common>
    	<TD class=title>数据表名</TD>
			<TD class=input><Input class=common id=cTableName></TD>
			<TD class=input><Input type=button class=cssButton name=cTableButton value="确  定" onclick="setRuleTable();"></TD>
    </tr>
 </table>
<br>

</div>

<hr>
<div id="RuleDisplay"
    
	style="height: 300;width:3000; overflow-y: auto; overflow-x: visible">

<div id="calConditions"
	style="position: ralative; left: -20; width: 100; height: 25; color: #0754D4; font-size: 22; font-weight: bold; cursor: hand"
	onmouseout="normalLight()" onmouseover="hightLight()" onclick="showOrHideMenuForCal()" >算 法:</div>
<br />	
	
<div id="conditions"
	style="width: 100; height: 25; color: #0754D4; font-size: 22; font-weight: bold; cursor: hand"
	onmouseout="normalLight()" onmouseover="hightLight()"
	onclick="showOrHideMenu()">条 件:</div>
<br />

<div id="display"
	style="position: absolute; display: none; width: auto; height: 200; overflow-y: auto; overflow-x: auto; border: thin lightblue solid; background: #E7F2FB;"></div>
<input type="hidden" id="Result" name="Result" /></div>
<br>
<br>
<br>
<div>
<div id="buttonForm">
<input type="button" class=cssButton name="displayDisicionTable" value="生成决策表"
	onclick="comfirmLogic()" /> &nbsp;&nbsp;&nbsp; <input type="button"
	class=cssButton name="submitData" value="提交数据" onclick="submitData()" />
&nbsp;&nbsp;&nbsp; <input type="hidden" class=cssButton name="logicToTable" value="逻辑入决策表"
	onclick="dataToTable()" /> <input type="button"
	class=cssButton name="modifyLogic" value="修改逻辑" onclick="modifyLogic()" /> 
</div>
<br>
<br>
<div id="grid-example"></div>
</div>
<form method="post" name="fm" action="./RuleMakeNewSave.jsp"><!-- 用于存储规则定制时基本信息的标识 -->
<input type="hidden" name="TableName"></input> <input type="hidden"
	name="BOMS"></input> <input type="hidden" name="SQLPara"></input> <input
	type="hidden" name="ViewPara"></input> <input type="hidden"
	name="SQLStatement"></input> <input type="hidden" name="DTData" /> <input
	type="hidden" name="CreateTable"></input> <input type="hidden"
	name="RuleCh" value="RuleCh"></input> 
<input
	type="hidden" name="TableColumnName"></input>
<input
	type="hidden" name="ColumnDataType"></input>
<input type="hidden"
	name="RuleName" value=<%=RuleName%>></input> <input type="hidden"
	name="RuleDes" value=<%=RuleDes%>></input> <input type="hidden"
	name="Creator" value=<%=Creator%>></input> <input type="hidden"
	name="RuleStartDate" value=<%=RuleStartDate%>></input> <input
	type="hidden" name="RuleEndDate" value=<%=RuleEndDate%>></input> <input
	type="hidden" name="TempalteLevel" value=<%=TempalteLevel%>></input> <input
	type="hidden" name="Business" value=<%=Business%>> <input
	type="hidden" name="State" value=<%=State%>></input> <input
	type="hidden" name="Valid" value=<%=Valid%>></input> <!-- 用于存储规则定制时决策表数据类型的标识 -->
<input type="hidden" name="Types"></input> <!-- 用于存储规则定制时基本操作类型的标识 --> <input
	type="hidden" name="flag" value=<%=flag%>></input> <!-- 用于存储规则定制时规则主键的标识 -->
<input type="hidden" name="LRTemplate_ID" value=<%=LRTemplate_ID%>></input>

<input type="hidden" name="riskcode" value='<%=riskcode%>'></input>

<input type="hidden" name="CalSQLStatement"></input>
<input type="hidden" name="CalRuleCh" value="CalRuleCh"></input> 

<!--tongmeng 2010-12-29 add 判断是否需要创建决策表 -->
<input type="hidden" name="CreateDTFlag" value="CreateDTFlag"></input> 
<!--tongmeng 2011-08-17 add 记录规则类型 -->
<input type="hidden" name="RuleType" value="1"></input> 
<!--tongmeng 2010-12-29 end add -->
<!-- 用于标识规则修改时的操作类型 --> <input type="hidden" name="Operation"></input>
<input type="hidden" name="mIntegerFlag" value='<%=IntegerFlag%>'></input>
<input type="hidden" name="RuleTableName">
<!-- 此隐藏控件，用户Excel上载后，将数据直接封装成Json的序列数据保存，在Ext.form.FileUploadField.js进行数据封装 -->
<input type="hidden" name="JsonDataFromExcelName" id="JsonDataFromExcel" >
</form>
</body>
</html>
