<%@page contentType="text/html;charset=gb2312"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
	//程序名称：UWManuRReport.jsp
	//程序功能：新契约人工核保生存调查报告录入
	//创建日期：2002-06-19 11:10:36
	//创建人  ：WHN
	//更新记录：  更新人：ln    更新日期：2008-10-23   更新原因/内容：根据新核保要求进行修改
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
<title>保全生调申请录入</title>
<%@include file="EdorUWManuRReportInit1.jsp"%>
</head>
<body
	onload="initForm('<%=tContNo%>','<%=tPrtNo%>','<%=tMissionID%>','<%=tSubMissionID%>','<%=tEdorNo %>','<%=tEdorType %>');">
<form method=post name=fm id=fm target="fraSubmit" action="./BQManuRReportChk.jsp"><!-- 以往核保记录部分（列表） -->
<table class=common>
	<tr class=common>
    	<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
		<td class=titleImg>生调基本信息</td>
	</tr>
</table>
<Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
<table class=common>
	<tr>
		<td class=title>印刷号</td>
		<TD class=input><Input class="readonly wid" readonly name=PrtNo id=PrtNo>
		</TD>
		<!--  
    	    <td class= title>   核保人  </td>
            <TD  class= input>   <Input class= "readonly" name=Operator >  </TD>   
         -->
		<TD class=title>投保人客户号码</TD>
		<TD class=input><Input class="readonly wid" readonly name=CustomerNo id=CustomerNo></TD>

		<TD class=title>投保人姓名</TD>
		<TD class=input><Input class="readonly wid" readonly name=CustomerName id=CustomerName></TD>
	</tr>
	<tr>
	    <TD class=title>生调原因</TD>

		<td class=input><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=codeno name=RReportReason id=RReportReason
			ondblclick="return showCodeList('rreportreason',[this,RReportReasonname],[0,1]);" onclick="return showCodeList('rreportreason',[this,RReportReasonname],[0,1]);"
			onkeyup="return showCodeListKey('rreportreason',[this,RReportReasonname],[0,1]);"><Input
			class=codename name=RReportReasonname id=RReportReasonname></td>
		<!--  <TD class=title>接收对象</TD>-->
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
		<td class=titleImg>生调项目录入</td>
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
		<TD class=title>生调说明(不超过1000字)</TD>
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
    <INPUT type="button" id="sure" name="sure" value=" 确  认 " class=cssButton onclick="submitForm()">
	<input class= cssButton type= "button" value=" 返  回 " class= Common onClick="top.close();">

<!--读取信息--></form>
<span id="spanCode" style="display: none; position: absolute;"></span>
<br/><br/><br/><br/>
</body>
</html>
