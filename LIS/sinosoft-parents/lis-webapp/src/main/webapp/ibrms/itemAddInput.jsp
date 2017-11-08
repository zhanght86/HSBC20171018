<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//**************************************************************************************************
	//Name：itemAddInput.jsp
	//Function：
	//Author：
	//Date: 
	//Desc: 
	//**************************************************************************************************
%>

<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.LRBOMDB"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>词条添加</title>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<script src="./itemAddInput.js"></script>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="bomFunInit.jsp"%>
</head>

<body onload="initForm(1);initElementtype();">
<%
	String bbName = request.getParameter("bbName");
%>


<form action="./itemSave.jsp" name=fm id=fm target=fraSubmit method=post>
<Table>
	<TR>
		</TD>
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,spanItemAdd );"></TD>
		<TD class=titleImg>增加词条</TD>
	</TR>
</Table>
<!--<span id="spanItemAdd" style="display: ''"><div class="maxbox">-->
<Div  id= "spanItemAdd" style= "display: ''" class="maxbox">
<table class=common>
	
	<TR class=common>
		<TD class=title5>词条英文名</TD>
		<TD class=input5><Input class="wid" class=common name=ItemEName id=ItemEName
			elementtype=nacessary verify="所属BOM名字|NOTNULL"
			onblur="eNameCheck(fm.ItemEName)"></TD>
		<TD class=title5>所属BOM名字</TD>
		<TD class=input5><Input class="wid" class=common name=bomName id=bomName value=<%=bbName %>
			elementtype=nacessary verify="所属BOM名字|NOTNULL" readonly></TD></TR>
          <TR class=common>  
		<TD class=title5>词条中文名</TD>
		<TD class=input5><Input class="wid" class=common name=CNName id=CNName
			elementtype=nacessary verify="词条中文名|NOTNULL"
			onblur="cNameCheck(fm.CNName)"></TD>
            <TD class=title5>连接词</TD>
		<TD class=input5><Input class="wid" class=common name=Connector id=Connector></TD>
	</TR>
	<TR class=common>
			
		<TD class=title5>是否层次型数据</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=IsHierarchical id=IsHierarchical class=codeno
			ondblclick="return showCodeList('IbrmsIsHierarchical',[this,IsHierarchicalName],[0,1]);"
            onclick="return showCodeList('IbrmsIsHierarchical',[this,IsHierarchicalName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsIsHierarchical',[this,IsHierarchicalName],[0,1]);" readonly><input
			class=codename name="IsHierarchicalName" id="IsHierarchicalName" readonly
			elementtype=nacessary verify="是否层次型数据|NOTNULL"></TD>	
		<TD class=title5>是否基础词条</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=IsBase id=IsBase class=codeno
			ondblclick="return showCodeList('IbrmsIsBase',[this,IsBaseName],[0,1]);"
            onclick="return showCodeList('IbrmsIsBase',[this,IsBaseName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsIsBase',[this,IsBaseName],[0,1]);" readonly><input
			class=codename name="IsBaseName" id="IsBaseName" readonly elementtype=nacessary
			verify="是否基础词条|NOTNULL"></TD>	
	</TR>

	<TR class=common>
		<TD class=title5>词条数据类型</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=CommandType id=CommandType	class=codeno
			ondblclick="return showCodeList('IbrmsCommandType',[this,CommandTypeName],[0,1]);"
            onclick="return showCodeList('IbrmsCommandType',[this,CommandTypeName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsCommandType',[this,CommandTypeName],[0,1]);" readonly><input
			class=codename name="CommandTypeName" id="CommandTypeName" readonly
			elementtype=nacessary verify="词条数据类型|NOTNULL"></TD>
		<TD class=title5>基础数据取值类型</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=SourceType id=SourceType class=codeno
			ondblclick="return showCodeList('IbrmsSourceType',[this,SourceTypeName],[0,1]);"
            onclick="return showCodeList('IbrmsSourceType',[this,SourceTypeName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsSourceType',[this,SourceTypeName],[0,1]);"><input
			class=codename name="SourceTypeName" id="SourceTypeName" readonly></TD></TR>
         <TR class=common>   
		<TD class=title5>基础数据取值来源</TD>
		<TD class=input5><textarea cols="38" name=Source class=common></textarea></TD>
        <TD class=title5>词条预校验</TD>
		<TD class=input5><Input  style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=PreCheck id=PreCheck verify="词条预校验"
			CodeData="" ondblclick= "showCodeListEx('ibrmsPreCheck',[this,ibrmsPreCheckName],[0,1],null,null,null,true);"
            onclick= "showCodeListEx('ibrmsPreCheck',[this,ibrmsPreCheckName],[0,1],null,null,null,true);"
			onkeyup="showCodeListKeyEx('ibrmsPreCheck',[this,ibrmsPreCheckName],[0,1],null,null,null,true);"><input
			class=codename name="ibrmsPreCheckName" id="ibrmsPreCheckName" readonly></TD>
	</TR>
		
	<TR class=common>
		
		<TD class=title5>有效性</TD>
		<TD class=input5><Input  style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Valid id=Valid class=codeno
			ondblclick="return showCodeList('IbrmsValid',[this,ValidName],[0,1]);"
            onclick="return showCodeList('IbrmsValid',[this,ValidName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsValid',[this,ValidName],[0,1]);" readonly><input
			class=codename name="ValidName" id="ValidName" readonly elementtype=nacessary
			verify="有效性|NOTNULL"></TD>
		<TD class=title5>词条描述信息</TD>
		<TD class=input5><textarea cols="38" class=common name=Description></textarea>
		
	</TR>


</Table>
<!--</span>--></div>
<!--<INPUT class=cssButton VALUE="添  加" TYPE=button
			onclick="itemAdd();">
		<INPUT class=cssButton VALUE="返  回" TYPE=button
			onclick="returnParent();">-->
            <a href="javascript:void(0);" class="button" onClick="itemAdd();">添    加</a>
            <a href="javascript:void(0);" class="button" onClick="returnParent();">返    回</a>
            <input type=hidden name=Transact>
<span id="spanCode" style="display: none; position: absolute;"></span></Form>
</body>
</html>
