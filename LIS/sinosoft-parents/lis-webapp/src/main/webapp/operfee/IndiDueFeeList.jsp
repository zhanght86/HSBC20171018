
<html>
<%
	//�������ƣ�IndiDueFeeInput.jsp
	//�����ܣ����˱��Ѵ��գ�ʵ�����ݴӱ������Ӧ�ո��˱��Ӧ���ܱ����ת
	//�������ڣ�2002-07-24 
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.operfee.*"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
	loggerDebug("IndiDueFeeList","tGI.Operator=" + tGI.Operator);
	loggerDebug("IndiDueFeeList","tGI.ManageCom=" + tGI.ManageCom);
%>
<script>
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var managecom = "<%=tGI.ManageCom%>"; //��¼��½����
	var ComCode = "<%=tGI.ComCode%>"; //��¼��½����
	var DealType = "<%=request.getParameter("DealType")%>";
</script>

<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="IndiDueFeeList.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
<%@include file="IndiDueFeeListInit.jsp"%>
</head>

<body onload="initForm();">
<form name=fm id="fm" action=./IndiDueFeeQuery.jsp target=fraSubmit method=post>
<table class=common border=0 width=100%>
	<table class=common border=0 width=100%>
		<tr>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
			</td>
			<td class=titleImg align=center>�������ѯ������</td>
		</tr>
	</table>
	<div class="maxbox">
	<div  id= "divFCDay" style= "display: ''">
	<table class=common>
		<tr class=common>
			<TD class=title5>���������</TD>
			<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=ManageCom id="ManageCom"
				onclick="return showCodeList('comcode',[this,ManageComName],[0,1]);"
				ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1]);"
				onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1]);"><input
				class=codename name=ManageComName id="ManageComName" readonly=true
				elementtype=nacessary></TD>
			<TD class=title5>�������룺</TD>
			<TD class=input5><Input class="common wid" name=ContNo id="ContNo"></TD>
		</tr>

		<!--  <tr class=common>
			<TD class=title>��ʼ����:</TD>
			<TD class=input><Input class="coolDatePicker" dateFormat="short"
				name=StartDate></TD>
			<TD class=title>��ֹ����:</TD>
			<TD class=input><Input class="coolDatePicker" dateFormat="short"
				name=EndDate></TD>
		</tr>-->
		
		<tr class=common>
			<TD class=title5>���ִ��룺</TD>
			<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=RiskCode id="RiskCode"
				onclick="return showCodeList('riskcode',[this,polName],[0,1]);"
				ondblclick="return showCodeList('riskcode',[this,polName],[0,1]);"
				onkeyup="return showCodeListKey('riskcode',[this,polName],[0,1]);"><input
				class=codename name=polName id="polName" readonly=true></TD>
			<TD class=title5>ҵ��Ա��</TD>
			<TD class=input5><Input class="common wid" name=AgentCode id="AgentCode"></TD>
		</tr>

		<tr class="common">
			<td class="title5">������ʽ:</td>
			<td class="input5"><input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="SecPayMode" id="SecPayMode"
				verify="������|NOTNUlL&CODE:continuepaymode" verifyorder="1"
				onclick="return showCodeList('continuepaymode',[this,PayModeName],[0,1]);"
				ondblclick="return showCodeList('continuepaymode',[this,PayModeName],[0,1]);"
				onkeyup="return showCodeListKey('continuepaymode',[this,PayModeName],[0,1]);"><input
				class="codename" name="PayModeName" id="PayModeName" readonly="readonly"
				elementtype=nacessary></td>
			<TD class=title5>��������:</TD>
			<TD class=input5><input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name="ContType" id="ContType"
				onclick="return showCodeList('salechnl', [this,ContTypeName],[0,1])"
				ondblclick="return showCodeList('salechnl', [this,ContTypeName],[0,1])"
				onkeyup="return showCodeListKey('salechnl', [this,ContTypeName],[0,1])"><input
				class=codename name=ContTypeName id="ContTypeName" readonly=true></TD>
		</tr>
		
		<tr class="common">
			<td class="title5">�������˱��:</td>
			<td class="input5"><input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="RnewUWFlag" id="RnewUWFlag" value="Y"
				verify="��������|NOTNUlL&CODE:RnewUWFlag" verifyorder="1"
				CodeData="0|^Y|������������|^N|��������������|";
				onClick="showCodeListEx('RnewUWFlag',[this,RnewUWIdea],[0,1]);" 
				ondblClick="showCodeListEx('RnewUWFlag',[this,RnewUWIdea],[0,1]);"
				onkeyup="showCodeListKeyEx('RnewUWFlag',[this,RnewUWIdea],[0,1]);"><input class="codename" name="RnewUWIdea" id="RnewUWIdea" value="������������" readonly="readonly"
				elementtype=nacessary></td>
		</tr>
	</table>
	</div>
	</div>
<a href="javascript:void(0)" class=button onclick="easyQueryClick();">��  ѯ</a>
<!-- <INPUT VALUE="��  ѯ" class=cssButton TYPE=button onclick="easyQueryClick()"> -->

	
	<table>
		<tr>
			<td class=common><IMG src="../common/images/butExpand.gif"
				style="cursor:hand;" OnClick="showPage(this,divLCCont);"></td>
			<td class=titleImg>������Ϣ</td>
		</tr>
	</table>
	<Div id="divLCCont" style="display: ''">
	<table class=common>
		<tr class=common>
			<td text-align: left colSpan=1><span id="spanContGrid">
			</span></td>
		</tr>
	</table>
	
	<center>
	<INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
	<INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 
	<INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();">
	<INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
	</center>
	</div>
	
	<table>
		<tr>
			<td class=common><IMG src="../common/images/butExpand.gif"
				style="cursor:hand;" OnClick="showPage(this,divLCPol1);"></td>
			<td class=titleImg>����������Ϣ</td>
		</tr>
	</table>
	<Div id="divLCPol1" style="display: ''">
	<table class=common>
		<tr class=common>
			<td text-align: left colSpan=1><span id="spanPolGrid"> </span>
			</td>
		</tr>
	</table>
	
	<center>
		<INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage2.firstPage();"> 
		<INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage2.previousPage();"> 
		<INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button 	onclick="turnPage2.nextPage();"> 
		<INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage2.lastPage();">
	</center>
	</div>
	
	<p><!--INPUT VALUE="�������ڸ�������" TYPE=button class= cssButton onclick="PersonSingle()"-->
	<INPUT type="hidden" name="Operator"  id="Operator" value=""> 
	<INPUT type="hidden" name="ContNo2" id="ContNo2" value=""> 
	<INPUT type="hidden" name="StartDate1" id="StartDate1" value=""> 
	<INPUT type="hidden" name="EndDate1"  id="EndDate1" value=""></p>
	</form>
	<br>
	<br>
	<br>
	<br>
	<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
