<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
	//�������ƣ�RReportReplyDetail.jsp
	//�����ܣ�������鱨��ظ�
	//�������ڣ�2002-06-19 11:10:36
	//������  ��WHN
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<%
	String tPolNo = "00000000000000000000";
	String tSerialNo = "00000000000000000000";
	String tContNo = "00000000000000000000";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
	try {
		tPolNo = request.getParameter("PolNo");
		tSerialNo = request.getParameter("SerialNo");
		loggerDebug("RnewRReportReplyDetail","---Exception:" + tPolNo + tSerialNo);
	} catch (Exception e1) {
		loggerDebug("RnewRReportReplyDetail","---Exception:" + e1);

	}
%>
<script>
	var PolNo = "<%= tPolNo%>";
	var SerialNo = "<%= tSerialNo%>";
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
</script>
<head>
<title>�������ظ�</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="RnewRReportReplyDetail.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel="stylesheet" type="text/css">
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="RnewRReportReplyDetailInit.jsp"%>

</head>
<body onload="initForm();">
<form method=post id="fm" name=fm target="fraSubmit"
	action="./RnewRReportReplyChk.jsp"><!-- ������ѯ���� -->
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divUWSpec1);"></td>
		<td class=titleImg>������鱨�����ݣ�</td>
	</tr>
</table>
<Div id="divUWSpec1" style="display: ''">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanQuestGrid"> </span></td>
	</tr>
</table>
</div>
<Div id="divUW1" style="display: ''">
<table >
	<TR class=common>
		<TD class=titleImg>����˵����</TD>
	</TR>
	<TR class=common>
		<TD height="87%" class=title><textarea name="Content" id=Content cols="120"
			rows="10" class="common" readonly></textarea></TD>
	</TR>
</table>
</Div>
<table>
	<tr>
		<td class=titleImg>�������棺</td>
	</tr>
</table>
<Div id="divUW2" style="display: ''">
<div class="maxbox1">
<table class=common>
	<TR class=common>
		<TD class=title5>���������</TD>
		<TD class=input5><Input class="common wid"  name=Reporter></TD>
		<TD class=title5>��������</TD>
		<TD class=input5><Input class="common wid"  name=ReportFee></TD>
	</TR>
</table>
</div>
<table width="121%" height="37%">
	<TR class=common>
		<TD width="100%" height="13%" class=common>�ظ�����(������2000��)</TD>
	</TR>
	<TR class=common>
		<TD height="87%" class=input><textarea name="ReplyResult"
			cols="120" rows="10" class="common"></textarea></TD>
	</TR>
</table>
</Div>
<p><!--��ȡ��Ϣ--> <input type="button" class=cssButton name="Reply"
	value="��  ��" onClick="submitForm()"> <input type="hidden"
	name="Flag" value=""> <input type="hidden"
	name="ProposalNoHide" value=""> <input type="hidden"
	name="Type" value="">
	<input type="hidden" name="PrtSeqHide" value="">
	<input type="hidden" name="MissionID" value="">
	<input type="hidden" name="SubMissionID" value="">
</p>
</form>

<span id="spanCode" style="display: none; position: absolute;"></span>
<br/><br/><br/><br/>
</body>
</html>
