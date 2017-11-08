<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//**************************************************************************************************
	//Name：bomAddInput.jsp
	//Function：
	//Author：
	//Date: 
	//Desc: 
	//**************************************************************************************************
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>BOM对象添加</title>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<script src="bomAddInput.js"></script>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="bomFunInit.jsp"%>
</head>

<body onload="initForm(1);initElementtype();fbomonload();">
<form action="./bomSave.jsp" name=fm id=fm target=fraSubmit method=post>
<Table>
	<TR>
		<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
		<TD class=titleImg>增加BOM对象</TD>
	</TR>
</Table>
<Div  id= "divPayPlan1" style= "display: ''" class="maxbox">
<table class=common>
	<TR class=common>
		<TD class=title5>BOM英文名</TD>
		<TD class=input5><Input class="wid" class=common name=eName id=eName
			elementtype=nacessary verify="BOM英文名|NOTNULL" onblur="eNameCheck(fm.eName)"></TD>
		<TD class=title5>BOM中文名</TD>
		<TD class=input5><Input class="wid" class=common name=cName id=cName
			elementtype=nacessary verify="BOM中文名|NOTNULL" onblur="cNameCheck(fm.cName)"></TD></TR>
        <TR class=common>    
		<TD class=title5>父BOM</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=fBOM id=fBOM 
			CodeData="" slementtype=nacessary verify="父BOM|NOTNULL" ondblclick= "showCodeListEx('ibrmsfBOM',[this,ibrmsfBOMName],[0,1],null,null,null,true);"
            onclick= "showCodeListEx('ibrmsfBOM',[this,ibrmsfBOMName],[0,1],null,null,null,true);"
			onkeyup="showCodeListKeyEx('ibrmsfBOM',[this,ibrmsfBOMName],[0,1],null,null,null,true);"><input
			class=codename name="ibrmsfBOMName" id="ibrmsfBOMName" readonly></TD>
            <TD class=title5>父BOM字段</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=fatherItem id=fatherItem
			ondblclick= "showCodeListEx('ibrmsfatherItem',[this,fatherItemName],[0,1],null,null,null,true);"
            onclick= "showCodeListEx('ibrmsfatherItem',[this,fatherItemName],[0,1],null,null,null,true);"
			onkeyup="showCodeListKeyEx('ibrmsfatherItem',[this,fatherItemName],[0,1],null,null,null,true);"><input
			class=codename name="fatherItemName" id="fatherItemName" readonly></TD>
	</TR>

	<TR class=common>
		
		<TD class=title5>本BOM字段</TD>
		<TD class=input5><Input class="wid" class=common name=localItem id=localItem readonly></TD>
		<TD class=title5>BOM层次</TD>
		<TD class=input5><Input class="wid" name=bomLevel id=bomLevel class=common elementtype=nacessary verify="BOM层次|NOTNULL" onblur="bomLevelCheck(fm.bomLevel)"></TD>
	</TR>

	<TR class=common>
		<TD class=title5>业务模块</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name="Business" id="Business" class=codeno
			ondblclick="return showCodeList('IbrmsBusiness',[this,BusinessName],[0,1]);"
            onclick="return showCodeList('IbrmsBusiness',[this,BusinessName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsBusiness',[this,BusinessName],[0,1]);" readonly><input
			class=codename name="BusinessName" id="BusinessName" readonly elementtype=nacessary  verify="BOM层次|NOTNULL"></TD>
		<TD class=title5>描述信息</TD>
		<TD class=input5><textarea cols="38" class=common name=Description></textarea></TD>
        </TR>
        <TR class=common>
		<TD class=title5>有效性</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Valid id=Valid class=codeno
			ondblclick="return showCodeList('IbrmsValid',[this,ValidName],[0,1]);"
            onclick="return showCodeList('IbrmsValid',[this,ValidName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsValid',[this,ValidName],[0,1]);" readonly><input
			class=codename name="ValidName" readonly></TD>
	</TR>
</Table>
</Div>
			
<span id="spanCode" style="display: none; position: absolute;"></span>
<!--<INPUT class=cssButton VALUE="添  加" TYPE=button
			onclick="submitForm();">
            <INPUT class=cssButton VALUE="返  回" TYPE=button
			onclick="returnParent();">-->
            <a href="javascript:void(0);" class="button" onClick="submitForm();">添    加</a>
            <a href="javascript:void(0);" class="button" onClick="returnParent();">返    回</a>
            <input type=hidden name=Transact></Form>
</body>
</html>
