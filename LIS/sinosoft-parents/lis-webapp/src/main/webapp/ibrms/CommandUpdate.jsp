<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//**************************************************************************************************
	//Name：CommandUpdate.jsp
	//Function：修改运算符
	//Author：
	//Date: 2008-9-17
	//Desc: 
	//**************************************************************************************************
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>运算符修改</title>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<script src="CommandUpdate.js"></script>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="CommandInit.jsp"%>
<%@include file="CommandUpdateInit.jsp"%>
</head>
<%
//===================================================================BEGIN
	String CommandName = request.getParameter("CommandName");
//====================================================================END
%>
<body onload="initForm();initElementtype();">

<form action="./CommandSave.jsp" name=fm id=fm target=fraSubmit method=post>
<Table>
	<TR>
		<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
		<TD class=titleImg>修改运算符</TD>
	</TR>
</Table>
<Div  id= "divPayPlan1" style= "display: ''" class="maxbox">
<table class=common >
	<TR class=common>
		<TD class=title5>运算符名称</TD>
		<TD class=input5><Input class="wid" class=common name=Name id=Name value=<%=CommandName%>
			elementtype=nacessary verify="运算符名称|NOTNULL" readonly></TD>
		<TD class=title5>界面展示</TD>
		<TD class=input5><Input class="wid" class=common name=display id=display
			elementtype=nacessary verify="界面展示|NOTNULL"></TD></TR>
         <TR class=common>   
		<TD class=title5>技术实现</TD>
		<TD class=input5><Input class="wid" class=common name=implenmation id=implenmation
			elementtype=nacessary verify="技术实现|NOTNULL"></TD>
            <TD class=title5>有效性</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Valid id=Valid class=codeno
			ondblclick="return showCodeList('IbrmsValid',[this,ValidName],[0,1]);"
            onclick="return showCodeList('IbrmsValid',[this,ValidName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsValid',[this,ValidName],[0,1]);"><input
			class=codename name="ValidName" id="ValidName" readonly></TD>
	</TR>

	<TR class=common>
		
		<TD class=title5>运算数据类型</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=commandType id=commandType 
           	ondblclick="return showCodeList('IbrmsCommandType',[this,CommandTypeName],[0,1]);"
            onclick="return showCodeList('IbrmsCommandType',[this,CommandTypeName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsCommandType',[this,CommandTypeName],[0,1]);" readonly><input	class=codename name="CommandTypeName" id="CommandTypeName"  elementtype=nacessary verify="运算数据类型|NOTNULL" readonly></TD>
		<TD class=title5>运算结果类型</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=resulttype id=resulttype class=codeno 
			ondblclick="return showCodeList('IbrmsResulttype',[this,ResulttypeName],[0,1]);"
            onclick="return showCodeList('IbrmsResulttype',[this,ResulttypeName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsResulttype',[this,ResulttypeName],[0,1]);" readonly><input class=codename name="ResulttypeName" id="ResulttypeName" elementtype=nacessary verify="运算结果类型|NOTNULL" readonly></TD>
	</TR>

	<TR class=common>	
		<TD class=title5>连接参数的类型</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=paratype id=paratype class=codeno
			ondblclick="return showCodeList('IbrmsParatype',[this,ParatypeName],[0,1]);"
            onclick="return showCodeList('IbrmsParatype',[this,ParatypeName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsParatype',[this,ParatypeName],[0,1]);" readonly><input class=codename name="ParatypeName" id="ParatypeName" readonly></TD>
		<TD class=title5>参数个数</TD>
		<TD class=input5><Input class="wid" class=common name=ParaNum id=ParaNum ></TD></TR>
        <TR class=common>
        <TD class=title5>描述信息</TD>
		<TD class=input5><textarea cols="38" class=common name=Description id=Description></textarea>	</TD>
        <TD class=title5>运算符类型</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=CommType id=CommType class=codeno
			ondblclick="return showCodeList('IbrmsCommType',[this,CommTypeName],[0,1]);"
            onclick="return showCodeList('IbrmsCommType',[this,CommTypeName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsCommType',[this,CommTypeName],[0,1]);"><input
			class=codename name="CommTypeName" id="CommTypeName" readonly=true></TD>	
	</TR>
<!--</Table>
<div id='CommDetailDiv' style="display:''">	-->
	<tr class=common>
		<TD class=title5>函数参数明细(参数间用','分隔)</TD>
		<TD class=input5><Input class="wid" class=common name=CommDetail id=CommDetail ></TD>
		<TD class=title5></TD>
		<TD class=input5></TD>
		</tr></table>
	</div>

<span id="spanCode" style="display: none; position: absolute;"></span>
<!--<INPUT class=cssButton VALUE="修  改" TYPE=button
			onclick="submitForm();">
			<INPUT class=cssButton VALUE="多语言信息" TYPE=button  id="MultLanguage"
			onclick="defineMultLanguage();">
			
		<INPUT class=cssButton VALUE="返  回" TYPE=button
			onclick="returnParent();">-->
            <a href="javascript:void(0);" class="button" onClick="submitForm();">修    改</a>
            <a href="javascript:void(0);" id="MultLanguage" class="button" onClick="defineMultLanguage();">多语言信息</a>
            <a href="javascript:void(0);" class="button" onClick="returnParent();">返    回</a>
<input type=hidden name=Transact>
</Form>

</body>
</html>
