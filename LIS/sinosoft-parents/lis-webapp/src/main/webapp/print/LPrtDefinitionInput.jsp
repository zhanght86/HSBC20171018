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
		<td class=titleImg>��ӡ���壺</td>
	</tr>
</table>
<div class="maxbox1">
<div id="divPrint1" name=divPrint1 style="display: '' ">
<table class=common>
	<tr class=common>
		<td class=title5>��ӡ����</td>
		<td class=input5><input class="common wid" name=PrintID  id="PrintID"
			 readOnly=true >
		</td>
	    <td class=title5>��ӡ����</td>
		<td class=input5><Input class="common wid" name=PrintName  id="PrintName"
			verify="��ӡ����|notnull" elementtype=nacessary>
		</td>
	</tr>
	<tr class=common>
		<td class=title5>��ӡ����</td>
		<td class=input5><input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=PrintObject  id="PrintObject" verify="��ӡ����|notnull"
			onclick="return showCodeList('printobject',[this,PrintObjectName],[0,1]);" ondblclick="return showCodeList('printobject',[this,PrintObjectName],[0,1]);"
			onkeyup="return showCodeListKey('printobject',[this,PrintObjectName],[0,1]);"><input class=codename name=PrintObjectName  id="PrintObjectName" readOnly=true elementtype=nacessary>
		</td> 
		<td class=title5>��ӡ����</td>
		<td class=input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=PrintType  id="PrintType" verify="��ӡ����|notnull"
			onclick="return showCodeList('printtypelist',[this,PrintTypeName],[0,1],null,null,null,1);" ondblclick="return showCodeList('printtypelist',[this,PrintTypeName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('printtypelist',[this,PrintTypeName],[0,1],null,null,null,1);"><input class=codename name=PrintTypeName  id="PrintTypeName" readOnly=true elementtype=nacessary>
		</td>
	</tr>
	<tr class=common>
		<td class=title5>��ӡ����</td>
		<td class=input5><input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=LanguageType  id="LanguageType" verify="��ӡ����|notnull"
			onclick="return showCodeList('languagetype',[this,LanguageTypeName],[0,1]);" ondblclick="return showCodeList('languagetype',[this,LanguageTypeName],[0,1]);"
			onkeyup="return showCodeListKey('languagetype',[this,LanguageTypeName],[0,1]);"><input class=codename name=LanguageTypeName  id="LanguageTypeName" readOnly=true elementtype=nacessary>
		</td> 
	</tr>
</table>	
</div>
</div>
<div>
	<a href="javascript:void(0)" class=button onclick="return addClick();">��  ��</a>
	<a href="javascript:void(0)" class=button onclick="return deleteClick();">ɾ  ��</a>
	<a href="javascript:void(0)" class=button onclick="return updateClick();">��  ��</a>
	<a href="javascript:void(0)" class=button onclick="return queryClick();">��  ѯ</a>
	<a href="javascript:void(0)" class=button onclick="return resetForm();">��  ��</a>
<!-- <input class=cssButton type=button value="��  ��" onclick="return addClick();">
<input class=cssButton type=button value="ɾ  ��" onclick="return deleteClick();">
<input class=cssButton type=button value="��  ��" onclick="return updateClick();">
<input class=cssButton type=button value="��  ѯ" onclick="return queryClick();">
<input class=cssButton type=button value="��  ��" onclick="return resetForm();"> -->
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
