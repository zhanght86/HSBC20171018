<%@page contentType="text/html;charset=gb2312"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
	//�������ƣ�UWManuRReport.jsp
	//�����ܣ�����Լ�˹��˱�������鱨��¼��
	//�������ڣ�2002-06-19 11:10:36
	//������  ��WHN
	//���¼�¼��  �����ˣ�ln    �������ڣ�2008-10-23   ����ԭ��/���ݣ������º˱�Ҫ������޸�
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="EdorUWManuRReport1.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<title>��ȫ��������¼��</title>
<%@include file="EdorUWManuRReportInit1.jsp"%>
</head>
<body
	onload="initForm('<%=tContNo%>','<%=tPrtNo%>','<%=tMissionID%>','<%=tSubMissionID%>','<%=tEdorNo %>','<%=tEdorType %>');">
<form method=post name=fm id=fm target="fraSubmit" action="./BQManuRReportChk.jsp"><!-- �����˱���¼���֣��б� -->
<table class=common>
	<tr class=common>
    	<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
		<td class=titleImg>����������Ϣ</td>
	</tr>
</table>
<Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
<table class=common>
	<tr>
		<td class=title>ӡˢ��</td>
		<TD class=input><Input class="readonly wid" readonly name=PrtNo id=PrtNo>
		</TD>
		<!--  
    	    <td class= title>   �˱���  </td>
            <TD  class= input>   <Input class= "readonly" name=Operator >  </TD>   
         -->
		<TD class=title>Ͷ���˿ͻ�����</TD>
		<TD class=input><Input class="readonly wid" readonly name=CustomerNo id=CustomerNo></TD>

		<TD class=title>Ͷ��������</TD>
		<TD class=input><Input class="readonly wid" readonly name=CustomerName id=CustomerName></TD>
	</tr>
	<tr>
	    <TD class=title>����ԭ��</TD>

		<td class=input><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=codeno name=RReportReason id=RReportReason
			ondblclick="return showCodeList('rreportreason',[this,RReportReasonname],[0,1]);" onclick="return showCodeList('rreportreason',[this,RReportReasonname],[0,1]);"
			onkeyup="return showCodeListKey('rreportreason',[this,RReportReasonname],[0,1]);"><Input
			class=codename name=RReportReasonname id=RReportReasonname></td>
		<!--  <TD class=title>���ն���</TD>-->
		<td><INPUT type="hidden" value=""></td>
		<TD><Input class="codeno" id="RReport" name=RReport type="hidden" value="1"></TD>		
	</TR>
</table>
</Div>

<Div id="divUWSpec" style="display: 'none'">
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divUWSpec1);"></td>
		<td class=titleImg>������Ŀ¼��</td>
	</tr>
</table>
</DIV>
<Div id="divUWSpec1" style="display: 'none'">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanInvestigateGrid"></span>
		</td>
	</tr>
</table>
</div>
<Div id="divUW1" style="display: ''">
<table class=common>
	<TR class=common>
		<TD class=title>����˵��(������1000��)</TD>
	</TR>
	<TR class=common>
		<TD colspan="6" style="padding-left:16px"><textarea name="Contente" id="Contente" cols="224" rows="4"
			class="common"></textarea></TD>
	</TR>
</table>
</Div>
    <INPUT type="hidden" id="ProposalNoHide" name="ProposalNoHide" value=""> 
    <INPUT type="hidden" id="Operator" name="Operator" value=""> 
    <INPUT type="hidden" id="ContNo" name="ContNo" value=""> 
    <INPUT type="hidden" id="MissionID" name="MissionID" value=""> 
    <INPUT type="hidden" id="SubMissionID" name="SubMissionID" value="">
    <INPUT type="hidden" id="SubNoticeMissionID" name="SubNoticeMissionID" value=""> 
    <INPUT type="hidden" id="Flag" name="Flag" value=""> 
    <INPUT type="hidden" id="EdorNo" name="EdorNo" value=""> 
    <INPUT type="hidden" id="EdorType" name="EdorType" value=""> 
    <INPUT type="hidden" id="PrtSeq" name="PrtSeq" value=""> 
    <INPUT type="button" id="sure" name="sure" value=" ȷ  �� " class=cssButton onclick="submitForm()">
	<input class= cssButton type= "button" value=" ��  �� " class= Common onClick="top.close();">

<!--��ȡ��Ϣ--></form>
<span id="spanCode" style="display: none; position: absolute;"></span>
<br/><br/><br/><br/>
</body>
</html>
