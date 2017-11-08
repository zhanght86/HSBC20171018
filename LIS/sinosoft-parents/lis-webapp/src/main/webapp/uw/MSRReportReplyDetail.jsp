<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
	//程序名称：RReportReplyDetail.jsp
	//程序功能：生存调查报告回复
	//创建日期：2002-06-19 11:10:36
	//创建人  ：WHN
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<%
	String tPolNo = "00000000000000000000";
	String tSerialNo = "00000000000000000000";
	String tContNo = "00000000000000000000";
	String tActivityID = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
	try {
		tPolNo = request.getParameter("PolNo");
		tSerialNo = request.getParameter("SerialNo");
		tActivityID = request.getParameter("ActivityID");
		//loggerDebug("MSRReportReplyDetail","---Exception:" + tPolNo + tSerialNo);
	} catch (Exception e1) {
		loggerDebug("MSRReportReplyDetail","---Exception:" + e1);

	}
%>
<script>
	var PolNo = "<%= tPolNo%>";
	var SerialNo = "<%= tSerialNo%>";
	var contNo = "<%=tContNo%>";          //个人单的查询条件.
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var ActivityID = "<%=tActivityID%>";
	//alert("ActivityID--aaaaaa-->"+ActivityID);	
</script>
<head>
<title>生调件回复</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="MSRReportReplyDetail.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="MSRReportReplyDetailInit.jsp"%>
<script src="../common/javascript/MultiCom.js"></script>

</head>
<body onLoad="initForm();">
<form method=post name=fm id="fm" target="fraSubmit"
	action="./MSRReportReplyChk.jsp"><!-- 保单查询条件 -->
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divUWSpec1);"></td>
		<td class=titleImg>生存调查报告内容：</td>
	</tr>
</table>
<Div id="divUWSpec1" style="display:  ">
<table class=common>
	<tr class=common>
		<td style="text-align: left" colSpan=1><span id="spanQuestGrid"> </span></td>
	</tr>
</table>
</div>
<Div id="divUW1" style="display:  ">
<table >
	<TR class=common>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;"></td>
        <TD class=titleImg>生调说明：</TD>
	</TR>
</table>
<div class="maxbox1">
<table>
	<TR class=common>
		<TD height="87%" class=title><textarea name="Content" id="Content" cols="120"
			rows="10" class="common" readonly></textarea></TD>
	</TR>
</table>
</div>
</Div>
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;"></td>
        <td class=titleImg>生调报告：</td>
	</tr>
</table>
<Div id="divUW2" class="maxbox1" style="display:  ">
<table class=common>
	<TR class=common>
		<TD class=title5>生存调查人</TD>
		<TD class=input5><Input class="common wid"  name=Reporter id="Reporter"></TD>
		<TD class=title5>生调费用</TD>
		<TD class=input5><Input class="common wid"  name=ReportFee id="ReportFee"></TD>
	</TR>
</table>
<table width="121%" height="37%">
	<TR class=common>
		<TD width="100%" height="13%" class=title>回复内容(不超过2000字)</TD>
	</TR>
	<TR class=common>
		<TD height="87%" class=title><textarea name="ReplyResult" id="ReplyResult"
			cols="120" rows="10" class="common"></textarea></TD>
	</TR>
</table>
</Div>
<p><!--读取信息--> <input type="button" class=cssButton id="Reply" name="Reply" value="回  复" onClick="submitForm()"> 
	<input type="hidden" name="Flag" id="Flag" value=""> 
	<input type="hidden" name="ProposalNoHide" id="ProposalNoHide" value=""> 
	<input type="hidden" name="Type" id="Type" value="">
	<input type="hidden" name="PrtSeqHide" id="PrtSeqHide" value="">
	<input type="hidden" name="MissionID" id="MissionID" value="">
	<input type="hidden" name="SubMissionID" id="SubMissionID" value="">
	<input type="hidden" name="ActivityID" id="ActivityID" value="">
</p>
</form>
<br><br><br><br><br>
<span id="spanCode" style="display: none; position: absolute;"></span>

</body>
</html>
