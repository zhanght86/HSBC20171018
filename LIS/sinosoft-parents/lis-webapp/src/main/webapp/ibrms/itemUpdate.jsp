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

<%@page import="com.sinosoft.lis.vschema.LRBOMSet"%>
<%@page import="com.sinosoft.lis.vdb.LRBOMItemDBSet"%>
<%@page import="com.sinosoft.lis.db.LRBOMItemDB"%>
<%@page import="com.sinosoft.lis.vschema.LRBOMItemSet"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>词条更新</title>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<script src="itemUpdate.js"></script>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="bomFunInit.jsp"%>
<%@include file="itemUpdateInit.jsp"%>
</head>

<%
//======================================================BEGIN
//获取bomMain.jsp页面需要修改的词条信息
	String itemEName = request.getParameter("itemName");
	String bomName=request.getParameter("bomName");
	String viewFlag=request.getParameter("flag");
//=======================================================END
%>

<body onload="initForm1('<%=viewFlag%>');initElementtype();">

<form action="./itemSave.jsp" name=fm id=fm target=fraSubmit method=post>
<Table>
	<TR>
       </TD>
		<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
		<TD class=titleImg>更新词条</TD>
	</TR>
</Table>
<Div  id= "divPayPlan1" style= "display: ''" class="maxbox">
<table class=common>
	<TR class=common>
		<TD class=title5>词条英文名</TD>
		<TD class=input5><Input class="wid" class=common name=ItemEName id=ItemEName value=<%=itemEName %>
			elementtype=nacessary verify="所属BOM名字|NOTNULL"
			onblur="eNameCheck(fm.ItemEName)"></TD>
		<TD class=title5>所属BOM名字</TD>
		<TD class=input5><Input class="wid" class=common name=bomName id=bomName value=<%=bomName%>
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
			onkeyup="return showCodeListKey('IbrmsSourceType',[this,SourceTypeName],[0,1]);" readonly><input
			class=codename name="SourceTypeName" id="SourceTypeName" readonly></TD></TR>
       <TR class=common>     
		<TD class=title5>基础数据取值来源</TD>
		<TD class=input5><textarea cols="38" name=Source class=common ></textarea></TD>
        <TD class=title5>词条预校验</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=PreCheck id=PreCheck verify="词条预校验" 
			ondblclick= "showCodeListEx('ibrmsPreCheck',[this,ibrmsPreCheckName],[0,1],null,null,null,true);"
            onclick= "showCodeListEx('ibrmsPreCheck',[this,ibrmsPreCheckName],[0,1],null,null,null,true);"
			onkeyup="showCodeListKeyEx('ibrmsPreCheck',[this,ibrmsPreCheckName],[0,1],null,null,null,true);"><input
			class=codename name="ibrmsPreCheckName" id="ibrmsPreCheckName" readonly></TD>
	</TR>
		
	<TR class=common>
		
		<TD class=title5>有效性</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Valid id=Valid class=codeno
			ondblclick="return showCodeList('IbrmsValid',[this,ValidName],[0,1]);"
            onclick="return showCodeList('IbrmsValid',[this,ValidName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsValid',[this,ValidName],[0,1]);" readonly><input
			class=codename name="ValidName" id="ValidName" readonly elementtype=nacessary
			verify="有效性|NOTNULL"></TD>
		<TD class=title5>词条描述信息</TD>
		<TD class=input5><textarea cols="38" class=common name=Description id=Description></textarea>
		
	</TR>

</Table>
</div>
<!--<INPUT class=cssButton VALUE="确  认" TYPE=button  id="ok"
			onclick="itemUpdate();">
		<INPUT class=cssButton VALUE="返  回" TYPE=button  id="ret"
			onclick="returnParent();">
			 <INPUT class=cssButton VALUE="多语言信息" TYPE=button  id="MultLanguage"
			onclick="defineMultLanguage();">
			
        <INPUT class=cssButton VALUE="关  闭" TYPE=button  id="close"
			onclick="window.close();">-->
            <a href="javascript:void(0);" id="ok" class="button" onClick="itemUpdate();">确    认</a>
            <a href="javascript:void(0);" id="ret" class="button" onClick="returnParent();">返    回</a>
            <a href="javascript:void(0);" id="MultLanguage" class="button" onClick="defineMultLanguage();">多语言信息</a>
            <a href="javascript:void(0);" id="close" class="button" onClick="window.close();">关    闭</a>
<input type=hidden name=Transact>
<span id="spanCode" style="display: none; position: absolute;"></span></Form>
</body>
</html>
