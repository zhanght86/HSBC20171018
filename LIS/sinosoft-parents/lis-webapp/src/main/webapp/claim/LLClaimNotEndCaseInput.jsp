
<%
	//�������ƣ�LLClaimInqBillInput.jsp
	//�����ܣ���������δ����鰸���嵥
	//�������ڣ�2009-04-16
	//������  ��mw
	//���¼�¼��
%>
<html>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head>
<%
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
%>
<title></title>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<script src="./LLClaimNotEndCase.js"></script>
<SCRIPT src="LLClaimPubFun.js"></SCRIPT>
<%@include file="LLClaimNotEndCaseInit.jsp"%>
</head>
<body onLoad="initForm();">
<form action="./LLClaimNotEndCaseSave.jsp" name=fm id=fm target=fraSubmit method=post>
<table>
	<TR>
		<TD  class=common ><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,DivStatisticCondition);"></TD>
		<TD class=titleImg>ͳ������</TD>
	</TR>
</table>
<Div id="DivStatisticCondition" style="display: ''">
<div class="maxbox1" >

<Table class=common>
	<TR class=common>
		<TD class=title5>ͳ�ƻ���</TD>
		<TD class=input5><Input class=codeno name="MngCom"
			verify="ͳ�ƻ���|NOTNULL"
            style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('station',[this,MngComName],[0,1]);"
			ondblclick="return showCodeList('station',[this,MngComName],[0,1]);"
			onkeyup="return showCodeListKey('station',[this,MngComName],[0,1]);"><input
			class=codename name="MngComName" readonly=true><font
			color='#ff0000'><b>*</b></font></TD>
					<TD class=title5>ͳ������</TD>
			<TD class=input5><Input class=code name=ClaimType CodeData="0|^5|5��δ�᰸��^10|10��δ�᰸��^30|30��δ�᰸��^ȫ��|ȫ��" 
					  				ondblClick="showCodeListEx('ClaimType',[this],[0,1]);"onkeyup="showCodeListKeyEx('ClaimType',[this],[0,1]);"><font color='#ff0000'><b>*</b></font></TD>

	</TR>
	<TR class=common>
		<TD class=title5>��ʼ����</TD>
		<TD class=input5>
            <input class="coolDatePicker" dateFormat="short" name="StartDate"  id="StartDate" onClick="laydate({elem:'#StartDate'});" verify="��ʼ����|NOTNULL"> <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            <font color='#ff0000'><b>*</b></font></TD>
		<TD class=title5>��������</TD>
		<TD class=input5>
            <input class="coolDatePicker" dateFormat="short" name="EndDate"  id="EndDate" onClick="laydate

({elem:'#EndDate'});" verify="��������|NOTNULL"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img 

src="../common/laydate/skins/default/icon.png" /></a></span>
            <font color='#ff0000'><b>*</b></font></TD>
	</TR>
</table>
</Div>
 </div>
<a href="javascript:void(0);" class="button"onclick="showLLClaimEndCaseBill()">��ӡ�嵥</a>

<!--<table>
	<TR>
		<TD><Input class=cssButton VALUE="��ӡ�嵥" TYPE=button
			onclick="showLLClaimEndCaseBill()"></TD>
	</TR>
</table>
-->
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
