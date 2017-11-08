<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//**************************************************************************************************
	//Name：bomUpdate.jsp
	//Function：
	//Author：
	//Date: 
	//Desc: 
	//**************************************************************************************************
%>

<%@page import="com.sinosoft.lis.db.LRBOMDB"%>
<%@page import="com.sinosoft.lis.vschema.LRBOMSet"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>BOM更新</title>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/.js"></SCRIPT>
<script src="bomUpdate.js"></script>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="bomFunInit.jsp"%>
<%@include file="bomUpdateInit.jsp"%>
</head>


<%
	//================================================================BEGIN
	//获取bomMain.jsp页面需要修改的BOM信息
	String bbName = request.getParameter("bbName");
	//获取编辑参数
	String viewFlag = request.getParameter("flag");
	//=================================================================END
%>
<body onload="initForm1('<%=viewFlag%>');initElementtype();">

<form action="./bomSave.jsp" name=fm id=fm target=fraSubmit method=post>
<Table>
	<TR>
		<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
		<TD class=titleImg>修改BOM</TD>
	</TR>
</Table>
<Div  id= "divPayPlan1" style= "display: ''" class="maxbox">
<table class=common>
	<TR class=common>
		<TD class=title5>BOM英文名</TD>
		<TD class=input5><Input class="wid" class=common name=eName id=eName value=<%=bbName%>
			elementtype=nacessary verify="BOM英文名|NOTNULL" readonly></TD>
		<TD class=title5>BOM中文名</TD>
		<TD class=input5><Input class="wid" class=common name=cName id=cName
			elementtype=nacessary verify="BOM中文名|NOTNULL"></TD></TR>
      <TR class=common>      
		<TD class=title5>父BOM</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=fBOM id=fBOM CodeData=""
			elementtype=nacessary
			ondblclick="showCodeListEx('ibrmsfBOM',[this,ibrmsfBOMName],[0,1],null,null,null,true);"
            onclick="showCodeListEx('ibrmsfBOM',[this,ibrmsfBOMName],[0,1],null,null,null,true);"
			onkeyup="showCodeListKeyEx('ibrmsfBOM',[this,ibrmsfBOMName],[0,1],null,null,null,true);"><input
			class=codename name="ibrmsfBOMName" id="ibrmsfBOMName" readonly></TD>
            <TD class=title5>父BOM字段</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=fatherItem id=fatherItem
			ondblclick="showCodeListEx('ibrmsfatherItem',[this,fatherItemName],[0,1],null,null,null,true);"
            onclick="showCodeListEx('ibrmsfatherItem',[this,fatherItemName],[0,1],null,null,null,true);"
			onkeyup="showCodeListKeyEx('ibrmsfatherItem',[this,fatherItemName],[0,1],null,null,null,true);"><input
			class=codename name="fatherItemName" id="fatherItemName" readonly></TD>
	</TR>

	<TR class=common>
		
		<TD class=title5>本BOM字段</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=localItem id=localItem
			ondblclick="showCodeListEx('ibrmslocalItem',[this,localItemName],[0,1],null,null,null,true);"
            onclick="showCodeListEx('ibrmslocalItem',[this,localItemName],[0,1],null,null,null,true);"
			onkeyup="showCodeListKeyEx('ibrmslocalItem',[this,localItemName],[0,1],null,null,null,true);"><input
			class=codename name="localItemName" id="localItemName" readonly></TD>
		<TD class=title5>BOM层次</TD>
		<TD class=input5><Input class="wid" name=bomLevel id=bomLevel class=common
			verify="BOM层次|INT"></TD>
	</TR>

	<TR class=common>
		<TD class=title5>业务模块</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Business id=Business class=codeno
			ondblclick="return showCodeList('IbrmsBusiness',[this,BusinessName],[0,1]);"
            onclick="return showCodeList('IbrmsBusiness',[this,BusinessName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsBusiness',[this,BusinessName],[0,1]);"><input
			class=codename name="BusinessName" id="BusinessName" readonly=true></TD>
		<TD class=title5>有效性</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Valid id=Valid class=codeno
			ondblclick="return showCodeList('IbrmsValid',[this,ValidName],[0,1]);"
            onclick="return showCodeList('IbrmsValid',[this,ValidName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsValid',[this,ValidName],[0,1]);"><input
			class=codename name="ValidName" id="ValidName" readonly=true></TD></TR>
        <TR class=common>    
		<TD class=title5>描述信息</TD>
		<TD class=input5><textarea cols="38" class=common name=Description id=Description></textarea>
	</TR>
</Table>
</div>
<!--<INPUT class=cssButton VALUE="确  认" TYPE=button name=ok
			onclick="updateBom();" style="display:none;">
		<INPUT class=cssButton VALUE="多语言信息" TYPE=button  id="MultLanguage"
			onclick="defineMultLanguage();">
		<INPUT class=cssButton VALUE="返  回" TYPE=button name=ret 
			onclick="returnParent();" style="display:none;">

		<INPUT class=cssButton VALUE="关  闭" TYPE=button name=close
			onclick="window.close();" >-->
            <a  name=ok style="display:none;" href="javascript:void(0);" class="button" onClick="updateBom();">确    认</a>
            <a id="MultLanguage" href="javascript:void(0);" class="button" onClick="defineMultLanguage();">多语言信息</a>
            <a href="javascript:void(0);" name=ret class="button" style="display:none;" onClick="returnParent();">返    回</a>
            <a href="javascript:void(0);" name=close class="button" onClick="window.close();">关    闭</a>
<input type=hidden name=Transact>

<span id="spanCode" style="display: none; position: absolute;"></span></Form>
</body>
</html>
