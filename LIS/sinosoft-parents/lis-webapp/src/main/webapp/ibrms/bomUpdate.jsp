<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//**************************************************************************************************
	//Name��bomUpdate.jsp
	//Function��
	//Author��
	//Date: 
	//Desc: 
	//**************************************************************************************************
%>

<%@page import="com.sinosoft.lis.db.LRBOMDB"%>
<%@page import="com.sinosoft.lis.vschema.LRBOMSet"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>BOM����</title>
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
	//��ȡbomMain.jspҳ����Ҫ�޸ĵ�BOM��Ϣ
	String bbName = request.getParameter("bbName");
	//��ȡ�༭����
	String viewFlag = request.getParameter("flag");
	//=================================================================END
%>
<body onload="initForm1('<%=viewFlag%>');initElementtype();">

<form action="./bomSave.jsp" name=fm id=fm target=fraSubmit method=post>
<Table>
	<TR>
		<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
		<TD class=titleImg>�޸�BOM</TD>
	</TR>
</Table>
<Div  id= "divPayPlan1" style= "display: ''" class="maxbox">
<table class=common>
	<TR class=common>
		<TD class=title5>BOMӢ����</TD>
		<TD class=input5><Input class="wid" class=common name=eName id=eName value=<%=bbName%>
			elementtype=nacessary verify="BOMӢ����|NOTNULL" readonly></TD>
		<TD class=title5>BOM������</TD>
		<TD class=input5><Input class="wid" class=common name=cName id=cName
			elementtype=nacessary verify="BOM������|NOTNULL"></TD></TR>
      <TR class=common>      
		<TD class=title5>��BOM</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=fBOM id=fBOM CodeData=""
			elementtype=nacessary
			ondblclick="showCodeListEx('ibrmsfBOM',[this,ibrmsfBOMName],[0,1],null,null,null,true);"
            onclick="showCodeListEx('ibrmsfBOM',[this,ibrmsfBOMName],[0,1],null,null,null,true);"
			onkeyup="showCodeListKeyEx('ibrmsfBOM',[this,ibrmsfBOMName],[0,1],null,null,null,true);"><input
			class=codename name="ibrmsfBOMName" id="ibrmsfBOMName" readonly></TD>
            <TD class=title5>��BOM�ֶ�</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=fatherItem id=fatherItem
			ondblclick="showCodeListEx('ibrmsfatherItem',[this,fatherItemName],[0,1],null,null,null,true);"
            onclick="showCodeListEx('ibrmsfatherItem',[this,fatherItemName],[0,1],null,null,null,true);"
			onkeyup="showCodeListKeyEx('ibrmsfatherItem',[this,fatherItemName],[0,1],null,null,null,true);"><input
			class=codename name="fatherItemName" id="fatherItemName" readonly></TD>
	</TR>

	<TR class=common>
		
		<TD class=title5>��BOM�ֶ�</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=localItem id=localItem
			ondblclick="showCodeListEx('ibrmslocalItem',[this,localItemName],[0,1],null,null,null,true);"
            onclick="showCodeListEx('ibrmslocalItem',[this,localItemName],[0,1],null,null,null,true);"
			onkeyup="showCodeListKeyEx('ibrmslocalItem',[this,localItemName],[0,1],null,null,null,true);"><input
			class=codename name="localItemName" id="localItemName" readonly></TD>
		<TD class=title5>BOM���</TD>
		<TD class=input5><Input class="wid" name=bomLevel id=bomLevel class=common
			verify="BOM���|INT"></TD>
	</TR>

	<TR class=common>
		<TD class=title5>ҵ��ģ��</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Business id=Business class=codeno
			ondblclick="return showCodeList('IbrmsBusiness',[this,BusinessName],[0,1]);"
            onclick="return showCodeList('IbrmsBusiness',[this,BusinessName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsBusiness',[this,BusinessName],[0,1]);"><input
			class=codename name="BusinessName" id="BusinessName" readonly=true></TD>
		<TD class=title5>��Ч��</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Valid id=Valid class=codeno
			ondblclick="return showCodeList('IbrmsValid',[this,ValidName],[0,1]);"
            onclick="return showCodeList('IbrmsValid',[this,ValidName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsValid',[this,ValidName],[0,1]);"><input
			class=codename name="ValidName" id="ValidName" readonly=true></TD></TR>
        <TR class=common>    
		<TD class=title5>������Ϣ</TD>
		<TD class=input5><textarea cols="38" class=common name=Description id=Description></textarea>
	</TR>
</Table>
</div>
<!--<INPUT class=cssButton VALUE="ȷ  ��" TYPE=button name=ok
			onclick="updateBom();" style="display:none;">
		<INPUT class=cssButton VALUE="��������Ϣ" TYPE=button  id="MultLanguage"
			onclick="defineMultLanguage();">
		<INPUT class=cssButton VALUE="��  ��" TYPE=button name=ret 
			onclick="returnParent();" style="display:none;">

		<INPUT class=cssButton VALUE="��  ��" TYPE=button name=close
			onclick="window.close();" >-->
            <a  name=ok style="display:none;" href="javascript:void(0);" class="button" onClick="updateBom();">ȷ    ��</a>
            <a id="MultLanguage" href="javascript:void(0);" class="button" onClick="defineMultLanguage();">��������Ϣ</a>
            <a href="javascript:void(0);" name=ret class="button" style="display:none;" onClick="returnParent();">��    ��</a>
            <a href="javascript:void(0);" name=close class="button" onClick="window.close();">��    ��</a>
<input type=hidden name=Transact>

<span id="spanCode" style="display: none; position: absolute;"></span></Form>
</body>
</html>
