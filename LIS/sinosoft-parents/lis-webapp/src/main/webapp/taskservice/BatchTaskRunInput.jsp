<%@page contentType="text/html;charset=gb2312"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>���������</title> 
<!-- ����������ʽ -->
<link href="../common/css/Project.css" type="text/css" rel="stylesheet">
<link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<!-- �������ýű� -->
<script   src="../common/javascript/Common.js"></script>
<script   src="../common/cvar/CCodeOperate.js"></script>
<script   src="../common/javascript/MulLine.js"></script>
<script   src="../common/javascript/EasyQuery.js"></script>
<script   src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
<script   src="../common/easyQueryVer3/EasyQueryCache.js"></script>
<script   src="../common/javascript/VerifyInput.js"></script>
<script   src="../common/Calendar/Calendar.js"></script>
<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
<script src="../common/laydate/laydate.js"></script>

<!-- ˽�����ýű� -->
<script   src="BatchTaskRunInput.js"></script>

</head>

<body topmargin="0" onload=";" ondragstart="return false">
<form name="fm" method="post" target="fraSubmit">

<table>
	<tr>
		<td class=common><img src="../common/images/butExpand.gif"
			style="cursor:hand;" onclick="showPage(this,divAvailableList)">
		</td>
		<td class="titleImg">��������������</td>
	</tr>
</table>
<div id="divAvailableList" style="display:''">
<table class="common">
	<tr class="common">
		<td class="title5">������</td>
		<TD  class=input5 > 
		        	<Input class="codeno" name="tTaskCode" 
				      ondblClick="showCodeList('TaskCode',[this,tTaskName],[0,1],null,null,null,1,260);"
				      onkeyup="showCodeListKey('TaskCode',[this,tTaskName],[0,1],null,null,null,1,260);" ><Input 
				      class= codename name='tTaskName' >
		</TD> 
		<td class="title5">ִ������</td>
		<!--<td class="input5"><input type="text" class="coolDatePicker" onClick="laydate({elem: '#tExeDate'}); dateFormat="short"
			name="tExeDate" ></td>
		-->
		<td class="input5">
		<Input class="coolDatePicker" onClick="laydate({elem: '#tExeDate'});" dateFormat="short" name=tExeDate id="tExeDate"><span class="icon"><a onClick="laydate({elem: '#tExeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
		</td>
	</tr>
</table>

<table class="common">
	<tr class="common">
		<td><span id="spanAvailableGrid"></span></td>
	</tr>
</table>
<hr>
</div>
<input type="button" class="cssButton" value="ִ ��" onclick="exeTask()">
</form>
<!-- ͨ��������Ϣ�б� -->
<span id="spanCode" style="display:none; position:absolute; slategray"></span>
</body>
</html>
