<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<html>
<head>
<title>批处理引擎V2.0</title>
<script src="../common/javascript/jquery.js"></script>
<script src="../common/javascript/jquery.form.js"></script>
<script src="../common/javascript/jquery.easyui.js"></script>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script src="../common/javascript/jquery.autocomplete.js"></script>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../common/css/jquery.autocomplete.css">
<script src="TaskServiceMission.js"></script>
<%
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");
	String tOperator = tG.Operator;
	
%>
<Script>
 	var TabsMapArray;
 	var mOperator = '<%=tOperator%>'
</Script>
</head>  
<body onload="initAllTabs();initButton();" >
	<div id="loading"><div></div></div>
<div id="taskbuttons" align="left">
		<table class=common border=0>
			<tr>
  			<td class=common>
  			<!--<input type='button' id='startButton' class=cssButton value='启动引擎' disabled onclick="startEngine();">
  			<input type='button' id='stopButton' class=cssButton value='停止引擎' disabled onclick="stopEngine();">-->
            <a href="javascript:void(0);" id='startButton' class="button" disabled onClick="startEngine();">启动引擎</a>
            <a href="javascript:void(0);" id='stopButton' class="button" disabled onClick="stopEngine();">停止引擎</a><br>
  			</td>
  		</tr>
		</table> 
		<div id="initProcessBar" class="easyui-progressbar" style="position:absolute;top:50%;left:50%;margin-top:0px;margin-left:-80px;width:200px; display:'none'"></div>
</div>  
<div id="TaskTab" class="easyui-tabs" fit="true"> 
</div>	

<form action='TaskServiceMissionChk.jsp' method=post name=fm target="fraSubmit">
	 <input type= "hidden" name= "fmAction" value="">
</form>	
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
</body>

</html>
