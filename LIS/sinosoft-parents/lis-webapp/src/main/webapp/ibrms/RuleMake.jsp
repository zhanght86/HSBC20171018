<%@ page language="java" import="java.util.*"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//��ȡ������ʱ����Ļ�����Ϣ
	String RuleName = request.getParameter("RuleName");
	loggerDebug("RuleMake","RuleName::" + RuleName);
	String RuleDes = request.getParameter("RuleDes");
	loggerDebug("RuleMake","RuleDes::" + RuleDes);
	String Creator = request.getParameter("Creator");
	loggerDebug("RuleMake","Creator::" + Creator);
	String RuleStartDate = request.getParameter("RuleStartDate");
	loggerDebug("RuleMake","RuleStartDate::" + RuleStartDate);
	String RuleEndDate = request.getParameter("RuleEndDate");
	loggerDebug("RuleMake","RuleEndDate::" + RuleEndDate);
	String TempalteLevel = request.getParameter("TempalteLevel");
	loggerDebug("RuleMake","TempalteLevel::" + TempalteLevel);
	String Business = request.getParameter("Business");
	loggerDebug("RuleMake","Business::" + Business);
	String State = request.getParameter("State");
	loggerDebug("RuleMake","State::" + State);
	String Valid = request.getParameter("Valid");
	loggerDebug("RuleMake","Valid::" + Valid);

	//��ȡ�������ͱ�־
	String flag = (String) request.getParameter("flag");
	loggerDebug("RuleMake","flag::" + flag);
	//��ȡ�����������־����Ҫ���ڹ�����޸ġ����ƺͲ鿴ʱ���ڲ��ҹ��������
	String LRTemplate_ID = (String) request
			.getParameter("LRTemplate_ID");
	loggerDebug("RuleMake","LTRemplate_ID::" + LRTemplate_ID);
	//��ȡ������Դ�ı�������Ҫ���ڹ�����޸ġ����ƺͲ鿴ʱ���ڲ��ҹ���ı�
	String LRTemplateName = (String) request
			.getParameter("LRTemplateName");
	loggerDebug("RuleMake","LRTemplateName::" + LRTemplateName);
	//tongmeng 2011-02-15 add
	//�ж��Ƿ񼯳ɲ���
	String IntegerFlag = request.getParameter("IntegerFlag")==null?"0":(String) request.getParameter("IntegerFlag");
	
	loggerDebug("RuleMake","IntegerFlag::" + IntegerFlag);	
	GlobalInput tGI = new GlobalInput();
  	tGI = (GlobalInput)session.getValue("GI");
  	
  	//String globalLanguage = tGI.
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>������</title>
<link rel="stylesheet" type="text/css" href="./resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/rule.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/examples.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/grid-examples.css" />
<link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">

<script type="text/javascript" src="./baseLib/ext-base.js"></script>
<script type="text/javascript" src="./baseLib/ext-all.js"></script>
<script type="text/javascript" src="./baseLib/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="./baseLib/multiSelectCbox.js"></script>
<script type="text/javascript" src="./JavaScript/makeLogical.js"></script>
<script type="text/javascript" src="./JavaScript/dicisionTable.js"></script>
<script type="text/javascript" src="./JavaScript/viewParameter.js"></script>
<script type="text/javascript" src="./JavaScript/RulePubFun.js"></script>

<script src="../common/javascript/jquery.js"></script>
<script src="../common/javascript/jquery.imageView.js"></script>
<script src="../common/javascript/jquery.easyui.min.js"></script>

<script type="text/javascript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css />
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css />
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
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
<script type="text/javascript" src="./RuleMake.js">
    </script>
<%@include file="./RuleMakeInit.jsp"%>

</head>
<body onload="initAllRuleMap();">

<div id="RuleDisplay"
    
	style="height: 300;width:3000; overflow-y: auto; overflow-x: visible">
<div id="conditions"
	style="width: 100; height: 10; color: #0754D4; font-size: 22; font-weight: bold; cursor: hand"
	onmouseout="normalLight()" onmouseover="hightLight()"
	onclick="showOrHideMenu()">�� ��:</div>
<br />
<div id="divThan" 
	style="position: ralative; left: -20; width: 100; height: 10; color: #0754D4; font-size: 22; font-weight: bold; cursor: hand"
	onmouseout="normalLight()" onmouseover="hightLight()">�� ô:</div>
<br />
<div id="display"
	style="position: absolute; display: none; width: auto; height: 200; overflow-y: auto; overflow-x: auto; border: thin lightblue solid; background: #E7F2FB;"></div>
<input type="input" id="Result" name="Result" size='80' maxlength=40
	value="��ʾ��Ϣ��" /></div>
<br>
<br>
<br>
<div>
<div id="buttonForm">
<input type="button" class=cssButton name="displayDisicionTable" id="displayDisicionTable" value="���ɾ��߱�"
	onclick="comfirmLogic()" /> &nbsp;&nbsp;&nbsp; <input type="button"
	class=cssButton name="submitData" id="submitData" value="�ύ����" onclick="submitData()" />
&nbsp;&nbsp;&nbsp; <input type="button" class=cssButton name="logicToTable" id="logicToTable" value="�߼�����߱�"
	onclick="dataToTable()" /> &nbsp;&nbsp;&nbsp; <input type="button"
	class=cssButton name="modifyLogic" id="modifyLogic" value="�޸��߼�" onclick="modifyLogic()" /> 
    <!--<a href="javascript:void(0);"  name="displayDisicionTable" class="button" onClick="comfirmLogic();">���ɾ��߱�</a>
    <a href="javascript:void(0);"  name="submitData" class="button" onClick="submitData();">�ύ����</a>
    <a href="javascript:void(0);"  name="logicToTable" class="button" onClick="dataToTable();">�߼�����߱�</a>
    <a href="javascript:void(0);"  name="modifyLogic" class="button" onClick="modifyLogic();">�޸��߼�</a>-->
</div>
<br>
<br>
<div id="grid-example"></div>
</div>
<form method="post" name="fm" id="fm" action="./RuleMakeSave.jsp"><!-- ���ڴ洢������ʱ������Ϣ�ı�ʶ -->
<input type="hidden" name="TableName" id="TableName"></input> <input type="hidden"
	name="BOMS" id="BOMS"></input> <input type="hidden" name="SQLPara" id="SQLPara"></input> <input
	type="hidden" name="ViewPara"></input> <input type="hidden"
	name="SQLStatement"	id="SQLStatement"></input> <input type="hidden" name="DTData" id="DTData" /> <input
	type="hidden" name="CreateTable"></input> <input type="hidden"
	name="RuleCh" id="RuleCh" value="RuleCh"></input> 
<input
	type="hidden" name="TableColumnName" id="TableColumnName"></input>
<input
	type="hidden" name="ColumnDataType" id="ColumnDataType"></input>
<input type="hidden"
	name="RuleName" value=<%=RuleName%>></input> <input type="hidden"
	name="RuleDes" value=<%=RuleDes%>></input> <input type="hidden"
	name="Creator" value=<%=Creator%>></input> <input type="hidden"
	name="RuleStartDate" value=<%=RuleStartDate%>></input> <input
	type="hidden" name="RuleEndDate" value=<%=RuleEndDate%>></input> <input
	type="hidden" name="TempalteLevel" value=<%=TempalteLevel%>></input> <input
	type="hidden" name="Business" value=<%=Business%>> <input
	type="hidden" name="State" value=<%=State%>></input> <input
	type="hidden" name="Valid" value=<%=Valid%>></input> <!-- ���ڴ洢������ʱ���߱��������͵ı�ʶ -->
<input type="hidden" name="Types"></input> <!-- ���ڴ洢������ʱ�����������͵ı�ʶ --> <input
	type="hidden" name="flag" value=<%=flag%>></input> <!-- ���ڴ洢������ʱ���������ı�ʶ -->
<input type="hidden" name="LRTemplate_ID" value=<%=LRTemplate_ID%>></input>
<input type="hidden" name="RuleType" value="0"></input> 
<!-- ���ڱ�ʶ�����޸�ʱ�Ĳ������� --> <input type="hidden" name="Operation"></input>
<input type="hidden" name="mIntegerFlag" value='<%=IntegerFlag%>'></input>
</form>
</body>
</html>
