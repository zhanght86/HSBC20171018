<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>

<SCRIPT src="./LPrtDefinitionInput.js"></SCRIPT>

<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>

<%@include file="./LPrtDefinitionInit.jsp"%>

</head>
<body onload="initForm();initElementtype();">
<form action="./LPrtDefinitionSave.jsp" method=post name=fm id="fm" target="fraSubmit">
<table class=common>
	<tr>
		<td class=common><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divPrint1);"></td>
		<td class=titleImg>打印定义：</td>
	</tr>
</table>
<div class="maxbox1">
<div id="divPrint1" name=divPrint1 style="display: '' ">
<table class=common>
	<tr class=common>
		<td class=title5>打印号码</td>
		<td class=input5><input class="common wid" name=PrintID  id="PrintID"
			 readOnly=true >
		</td>
	    <td class=title5>打印名称</td>
		<td class=input5><Input class="common wid" name=PrintName  id="PrintName"
			verify="打印名称|notnull" elementtype=nacessary>
		</td>
	</tr>
	<tr class=common>
		<td class=title5>打印对象</td>
		<td class=input5><input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=PrintObject  id="PrintObject" verify="打印对象|notnull"
			onclick="return showCodeList('printobject',[this,PrintObjectName],[0,1]);" ondblclick="return showCodeList('printobject',[this,PrintObjectName],[0,1]);"
			onkeyup="return showCodeListKey('printobject',[this,PrintObjectName],[0,1]);"><input class=codename name=PrintObjectName  id="PrintObjectName" readOnly=true elementtype=nacessary>
		</td> 
		<td class=title5>打印类型</td>
		<td class=input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=PrintType  id="PrintType" verify="打印类型|notnull"
			onclick="return showCodeList('printtypelist',[this,PrintTypeName],[0,1],null,null,null,1);" ondblclick="return showCodeList('printtypelist',[this,PrintTypeName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('printtypelist',[this,PrintTypeName],[0,1],null,null,null,1);"><input class=codename name=PrintTypeName  id="PrintTypeName" readOnly=true elementtype=nacessary>
		</td>
	</tr>
	<tr class=common>
		<td class=title5>打印语言</td>
		<td class=input5><input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=LanguageType  id="LanguageType" verify="打印语言|notnull"
			onclick="return showCodeList('languagetype',[this,LanguageTypeName],[0,1]);" ondblclick="return showCodeList('languagetype',[this,LanguageTypeName],[0,1]);"
			onkeyup="return showCodeListKey('languagetype',[this,LanguageTypeName],[0,1]);"><input class=codename name=LanguageTypeName  id="LanguageTypeName" readOnly=true elementtype=nacessary>
		</td> 
	</tr>
</table>	
</div>
</div>
<div>
	<a href="javascript:void(0)" class=button onclick="return addClick();">增  加</a>
	<a href="javascript:void(0)" class=button onclick="return deleteClick();">删  除</a>
	<a href="javascript:void(0)" class=button onclick="return updateClick();">修  改</a>
	<a href="javascript:void(0)" class=button onclick="return queryClick();">查  询</a>
	<a href="javascript:void(0)" class=button onclick="return resetForm();">重  置</a>
<!-- <input class=cssButton type=button value="增  加" onclick="return addClick();">
<input class=cssButton type=button value="删  除" onclick="return deleteClick();">
<input class=cssButton type=button value="修  改" onclick="return updateClick();">
<input class=cssButton type=button value="查  询" onclick="return queryClick();">
<input class=cssButton type=button value="重  置" onclick="return resetForm();"> -->
</div>
<input type = hidden name = State  id="State">
<input type = hidden id ="fmtransact" name="fmtransact">
</form>
<br>
<br>
<br>
<br>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
