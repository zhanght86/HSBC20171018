<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDUMInput.jsp
 //程序功能：险种核保规则定义
 //创建日期：2009-3-14
 //创建人  ：
 //更新记录：  更新人    更新日期     更新原因/内容
%>

<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<link rel="stylesheet" type="text/css"
	href="../common/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
<%@include file="buttonshow.jsp"%>
<SCRIPT src="PDUM.js"></SCRIPT>
<script src="../common/javascript/jquery.js"></script>
<script src="../common/javascript/jquery.easyui.min.js"></script>
<%@include file="PDUMInit.jsp"%>
<%
	String tType = request.getParameter("Type");
	String tEdorType = request.getParameter("EdorType");
	System.out.println("tType:"+tType+":tEdorType:"+tEdorType);
%>

<script>
	var mType = '<%=tType%>';
	var mEdorType = '<%=tEdorType%>';
</script>
</head>
<body onload="initForm();initElementtype();">
<form action="./PDUMSave.jsp" method=post name=fm target="fraSubmit">
<table class=common>
	<tr class=common>
		<td class=title5>险种代码</td>
		<td class=input5><input class=common name="RiskCode"
			readonly="readonly"></td>
		<!--td class=title5>险种名称</td>
		<td class=input5>
			<input class=common name="RiskName" readonly="readonly" >
		</td-->
	</tr>
</table>
<table>
	<tr>
		<td class="titleImg">已有核保规则：</td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<td style="text-align: left;" colSpan=1><span
			id="spanMulline10Grid"> </span></td>
	</tr>
</table>
<INPUT CLASS=cssbutton VALUE="首  页"
	TYPE=button onclick="Mulline10GridTurnPage.firstPage();"> <INPUT
	CLASS=cssbutton VALUE="上一页"
	TYPE=button onclick="Mulline10GridTurnPage.previousPage();"> <INPUT
	CLASS=cssbutton VALUE="下一页"
	TYPE=button onclick="Mulline10GridTurnPage.nextPage();"> <INPUT
	CLASS=cssbutton VALUE="尾  页"
	TYPE=button onclick="Mulline10GridTurnPage.lastPage();"> </BR>
</BR>
<!--input value="进入算法定义" type=button  onclick="button163( )" class="cssButton" type="button" -->

<table>
	<tr>
		<td class="titleImg">核保规则定义:</td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<!--TD  class= title5>险种编码</TD>
        <TD  class= input5>
            <Input class="codeno" name=RiskCode readonly="readonly" ondblclick="return showCodeList('RiskCode',[this,RiskCodeName],[0,1]);" onkeyup="return showCodeListKey('RiskCode',[this,RiskCodeName],[0,1]);"><input class=codename name=RiskCodeName readonly="readonly">
        </TD-->
		<TD class=title5>核保编码</TD>
		<TD class=input5><Input class=common name=UWCODE
			verify="核保编码|NOTNUlL&LEN<=10" 
			elementtype="nacessary"></TD>
		<TD class=title5>关联保单类型</TD>
		<TD class=input5><Input class="codeno" name=RELAPOLTYPE
			readonly="readonly"
			verify="关联保单类型|NOTNUlL"
			ondblclick="return showCodeList('pd_relapoltype',[this,RELAPOLTYPEName],[0,1],null,'<%=tType%>','<%=tEdorType%>',1);"
			onkeyup="return showCodeListKey('pd_relapoltype',[this,RELAPOLTYPEName],[0,1],null,'<%=tType%>','<%=tEdorType%>',1);"><input
			class=codename name=RELAPOLTYPEName readonly="readonly"
			elementtype="nacessary"></TD>
	</tr>
	<tr class=common>
		<TD class=title5>核保顺序号</TD>
		<TD class=input5><input class="common" name="UWORDER"
			verify="核保顺序号|NOTNUlL&NUM"
			elementtype="nacessary"></TD>
		<TD class=title5>提示信息</TD>
		<TD class=input5><input class="common" name="REMARK"
			verify="提示信息|NOTNUlL"
			elementtype="nacessary"></TD>
	</tr>
	<tr class=common>
		<TD class=title5>算法编码</TD>
		<TD class=input5><input class="common" name="CALCODE"
			verify="算法编码|LEN>=6"></TD>
		<TD class=title5>适用系统</TD>
		<TD class=input5><Input class=codeno name=STANDBYFLAG1
			readonly="readonly"
			verify="适用系统|NOTNUlL"
			ondblclick="return showCodeList('pd_systype',[this,STANDBYFLAG1NAME],[0,1]);"
			onkeyup="return showCodeListKey('pd_systype',[this,STANDBYFLAG1NAME],[0,1]);"><input
			class=codename name="STANDBYFLAG1NAME" readonly="readonly"
			elementtype="nacessary"></TD>
	</tr>
	<tr class=common>
		<TD class=title5>核保类型
		</TD>
		<TD class=input5><Input class="codeno" name=UWTYPE
			readonly="readonly"
			verify="核保类型|NOTNUlL"
			ondblclick="return showCodeList('pd_uwtype',[this,UWTYPEName],[0,1],null,'<%=tType%>','<%=tEdorType%>',1);"
			onkeyup="return showCodeListKey('pd_uwtype',[this,UWTYPEName],[0,1],null,'<%=tType%>','<%=tEdorType%>',1);"><input
			class=codename name=UWTYPEName readonly="readonly"
			elementtype="nacessary"></TD>
		<TD class=title5>业务模块</TD>
		<TD class=input5><input class="codeno" name=STANDBYFLAG2
			ondblclick="return showCodeList('ibrmsbusinessrule',[this,STANDBYFLAG2NAME],[0,1]);"
			onkeyup="return showCodeListKey('ibrmsbusinessrule',[this,STANDBYFLAG2NAME],[0,1]);"><input
			class="codename" name=STANDBYFLAG2NAME readonly="readonly">
		</TD>
	</tr>
</table>
<!--算法定义引用页--> <jsp:include page="CalCodeDefMain.jsp?RuleType=0" />

<table class=common>
	<tr class=common>
		<td style="text-align: left;" colSpan=1><span
			id="spanMulline9Grid"> </span></td>
	</tr>
</table>
<br>
<div align=left id=savabuttonid><input
	value="重  置" type=button
	onclick="initForm()" class="cssButton" type="button"> <input
	value="保  存" type=button
	onclick="save()" class="cssButton" type="button"> <input
	value="修  改" type=button
	onclick="update()" class="cssButton" type="button"> <input
	value="删  除" type=button
	onclick="del()" class="cssButton" type="button"></div>
<div align=left id=checkFunc><input
	value="查看规则内容" type=button
	onclick="InputCalCodeDefFace2()" class="cssButton" type="button">
</div>

<input value="记事本" type=hidden
	onclick="button164( )" class="cssButton" type="button"> <input
	value="问题件查询" type=hidden
	onclick="button165( )" class="cssButton" type="button"> <input
	value="返  回" type=button
	onclick="returnParent( )" class="cssButton" type="button"> <br>
<br>

<input type=hidden name="operator"> <input type=hidden
	name="tableName" value="PD_LMUW"> <input type=hidden
	name=IsReadOnly> <input type=hidden name=VALIFLAG> <!-- 
<input type=hidden name=STANDBYFLAG2>
<input type=hidden name=STANDBYFLAG2Name>
<input type=hidden name=STANDBYFLAG2NAME>
 --></form>

<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
