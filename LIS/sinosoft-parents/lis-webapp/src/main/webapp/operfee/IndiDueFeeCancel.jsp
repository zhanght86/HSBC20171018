
<html>
<%
	//�������ƣ�IndiDueFeeInput.jsp
	//�����ܣ����˱��Ѵ��գ�ʵ�����ݴӱ������Ӧ�ո��˱��Ӧ���ܱ����ת
	//�������ڣ�2002-07-24 
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	//�����¸���
	String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";

	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
	loggerDebug("IndiDueFeeCancel","tGI.Operator=" + tGI.Operator);
	loggerDebug("IndiDueFeeCancel","tGI.ManageCom" + tGI.ManageCom);
%>
<script>
	var grpPolNo = "<%=tGrpPolNo%>";      //���˵��Ĳ�ѯ����.
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var ComCode = "<%=tGI.ComCode%>"; //��¼��½����
</script>

<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="IndiDueFeeInputCancel.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="IndiDueFeeInputInitCancel.jsp"%>
</head>

<body onload="initForm();">
<form name=fm id="fm" action=./IndiDueFeeQueryCancel.jsp target=fraSubmit method=post>
<table class=common border=0 width=100%>
	<tr>
		<td class=common>
			<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
		</td>
		<td class=titleImg align=center>�������ѯ������</td>
	</tr>
</table>
<div class="maxbox1">
<div  id= "divFCDay" style= "display: ''">
<table class=common align=center>
	<TR class=common>
		<TD class=title5>��������</TD>
		<TD class=input5><Input class="common wid" name=ContNo2 id="ContNo2"></TD>
		<TD class=title5>ӡˢ��</TD>
		<TD class=input5><Input class="common wid" name=PrtNo id="PrtNo"></TD>
	</TR>
</table>
</div>
</div>
<a href="javascript:void(0)" class=button onclick="easyQueryClick();">��  ѯ</a>
<!-- <INPUT VALUE="��  ѯ" class=cssButton TYPE=button onclick="easyQueryClick()"> -->


<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divLCPol1);"></td>
		<td class=titleImg>������Ϣ</td>
	</tr>
</table>
<Div id="divLCPol1" style="display: ''">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanPolGrid"> </span></td>
	</tr>
</table>

<center>
	<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="getFirstPage();"> 
	<INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="getPreviousPage();"> 
	<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="getNextPage();"> 
	<INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="getLastPage();">
</center>
</div>
<a href="javascript:void(0)" class=button onclick="submitForm();">���ճ���</a>
	<!-- <INPUT VALUE="���ճ���" TYPE=button class=cssButton	onclick="submitForm()">  -->
	<INPUT type="hidden" name="Operator" id="Operator" value=""> 
	<INPUT type="hidden" name="ContNo" id="ContNo" value="">

</form>
<br>
<br>
<br>
<br>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
