<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//**************************************************************************************************
	//Name��itemAddInput.jsp
	//Function��
	//Author��
	//Date: 
	//Desc: 
	//**************************************************************************************************
%>

<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.LRBOMDB"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>�������</title>
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
		<TD class=titleImg>���Ӵ���</TD>
	</TR>
</Table>
<!--<span id="spanItemAdd" style="display: ''"><div class="maxbox">-->
<Div  id= "spanItemAdd" style= "display: ''" class="maxbox">
<table class=common>
	
	<TR class=common>
		<TD class=title5>����Ӣ����</TD>
		<TD class=input5><Input class="wid" class=common name=ItemEName id=ItemEName
			elementtype=nacessary verify="����BOM����|NOTNULL"
			onblur="eNameCheck(fm.ItemEName)"></TD>
		<TD class=title5>����BOM����</TD>
		<TD class=input5><Input class="wid" class=common name=bomName id=bomName value=<%=bbName %>
			elementtype=nacessary verify="����BOM����|NOTNULL" readonly></TD></TR>
          <TR class=common>  
		<TD class=title5>����������</TD>
		<TD class=input5><Input class="wid" class=common name=CNName id=CNName
			elementtype=nacessary verify="����������|NOTNULL"
			onblur="cNameCheck(fm.CNName)"></TD>
            <TD class=title5>���Ӵ�</TD>
		<TD class=input5><Input class="wid" class=common name=Connector id=Connector></TD>
	</TR>
	<TR class=common>
			
		<TD class=title5>�Ƿ���������</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=IsHierarchical id=IsHierarchical class=codeno
			ondblclick="return showCodeList('IbrmsIsHierarchical',[this,IsHierarchicalName],[0,1]);"
            onclick="return showCodeList('IbrmsIsHierarchical',[this,IsHierarchicalName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsIsHierarchical',[this,IsHierarchicalName],[0,1]);" readonly><input
			class=codename name="IsHierarchicalName" id="IsHierarchicalName" readonly
			elementtype=nacessary verify="�Ƿ���������|NOTNULL"></TD>	
		<TD class=title5>�Ƿ��������</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=IsBase id=IsBase class=codeno
			ondblclick="return showCodeList('IbrmsIsBase',[this,IsBaseName],[0,1]);"
            onclick="return showCodeList('IbrmsIsBase',[this,IsBaseName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsIsBase',[this,IsBaseName],[0,1]);" readonly><input
			class=codename name="IsBaseName" id="IsBaseName" readonly elementtype=nacessary
			verify="�Ƿ��������|NOTNULL"></TD>	
	</TR>

	<TR class=common>
		<TD class=title5>������������</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=CommandType id=CommandType	class=codeno
			ondblclick="return showCodeList('IbrmsCommandType',[this,CommandTypeName],[0,1]);"
            onclick="return showCodeList('IbrmsCommandType',[this,CommandTypeName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsCommandType',[this,CommandTypeName],[0,1]);" readonly><input
			class=codename name="CommandTypeName" id="CommandTypeName" readonly
			elementtype=nacessary verify="������������|NOTNULL"></TD>
		<TD class=title5>��������ȡֵ����</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=SourceType id=SourceType class=codeno
			ondblclick="return showCodeList('IbrmsSourceType',[this,SourceTypeName],[0,1]);"
            onclick="return showCodeList('IbrmsSourceType',[this,SourceTypeName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsSourceType',[this,SourceTypeName],[0,1]);"><input
			class=codename name="SourceTypeName" id="SourceTypeName" readonly></TD></TR>
         <TR class=common>   
		<TD class=title5>��������ȡֵ��Դ</TD>
		<TD class=input5><textarea cols="38" name=Source class=common></textarea></TD>
        <TD class=title5>����ԤУ��</TD>
		<TD class=input5><Input  style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=PreCheck id=PreCheck verify="����ԤУ��"
			CodeData="" ondblclick= "showCodeListEx('ibrmsPreCheck',[this,ibrmsPreCheckName],[0,1],null,null,null,true);"
            onclick= "showCodeListEx('ibrmsPreCheck',[this,ibrmsPreCheckName],[0,1],null,null,null,true);"
			onkeyup="showCodeListKeyEx('ibrmsPreCheck',[this,ibrmsPreCheckName],[0,1],null,null,null,true);"><input
			class=codename name="ibrmsPreCheckName" id="ibrmsPreCheckName" readonly></TD>
	</TR>
		
	<TR class=common>
		
		<TD class=title5>��Ч��</TD>
		<TD class=input5><Input  style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Valid id=Valid class=codeno
			ondblclick="return showCodeList('IbrmsValid',[this,ValidName],[0,1]);"
            onclick="return showCodeList('IbrmsValid',[this,ValidName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsValid',[this,ValidName],[0,1]);" readonly><input
			class=codename name="ValidName" id="ValidName" readonly elementtype=nacessary
			verify="��Ч��|NOTNULL"></TD>
		<TD class=title5>����������Ϣ</TD>
		<TD class=input5><textarea cols="38" class=common name=Description></textarea>
		
	</TR>


</Table>
<!--</span>--></div>
<!--<INPUT class=cssButton VALUE="��  ��" TYPE=button
			onclick="itemAdd();">
		<INPUT class=cssButton VALUE="��  ��" TYPE=button
			onclick="returnParent();">-->
            <a href="javascript:void(0);" class="button" onClick="itemAdd();">��    ��</a>
            <a href="javascript:void(0);" class="button" onClick="returnParent();">��    ��</a>
            <input type=hidden name=Transact>
<span id="spanCode" style="display: none; position: absolute;"></span></Form>
</body>
</html>
