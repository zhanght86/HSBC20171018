<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<% 
//程序名称：NoScanBankContInput.jsp 
//程序功能：银代新契约无扫描保单录入
//创建日期：2005-07-18 11:10:36
//创建人  ：ccvip 
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<%
//银代个人
String tContNo="";
String tFlag="";
GlobalInput tGI=new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");
tFlag=request.getParameter("type");
%>
<script>
var contNo="<%=tContNo%>";          //个人单的查询条件.
var operator="<%=tGI.Operator%>";   //记录操作员
var manageCom="<%=tGI.ManageCom%>"; //记录登陆机构
var type="<%=tFlag%>";
var comcode="<%=tGI.ComCode%>"; //记录登陆机构
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK"> 
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="NoScanBankContInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="NoScanBankContInit.jsp"%> 
<title>无扫描录入</title> 
</head>
<body  onload="initForm();initElementtype();" >
	<form action="./NoScanContInputSave.jsp" method=post name=fm id="fm" target="fraSubmit">
		<!-- 保单信息部分 -->
		<table class=common border=0 width=100%>
			<tr>
				<td class=common>
    				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
				</td>
				<td class=titleImg align=center>请输入印刷号：</td>
			</tr>
		</table>
		<div class="maxbox1">
		<Div  id= "divFCDay" style= "display: ''"> 
		<table class=common>
			<TR class=common>
				<TD class=title5>管理机构</TD>
				<TD class=input5>
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=ManageCom id="ManageCom" verify="管理机构|code:station" onClick="return showCodeList('station',[this,ManageComName],[0,1]);" onDblClick="return showCodeList('station',[this,ManageComName],[0,1]);" onKeyUp="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName id="ManageComName" readonly=true elementtype=nacessary>
				</TD>				
				<TD class=title5>申请日期</TD>
				<TD class=input5>
					<Input class="coolDatePicker" onClick="laydate({elem: '#InputDate'});" verify="投保申请日期|date" dateFormat="short" name=InputDate id="InputDate"><span class="icon"><a onClick="laydate({elem: '#InputDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</TD>
			</TR>
			<TR class=common>
				<TD  class= title5>交费日期</TD>
	            <TD  class= input5>
	            	<Input class="coolDatePicker" onClick="laydate({elem: '#PayDate'});" verify="投保申请日期|date" dateFormat="short" name=PayDate id="PayDate"><span class="icon"><a onClick="laydate({elem: '#PayDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
	            </TD>
			</tr>
			<tr id='SubTitle' style="display:none">
				<TD class=title5>印刷号</TD>
				<TD class=input5>
					<Input class="common wid" name=PrtNo id="PrtNo" elementtype=nacessary MAXLENGTH="14" verify="投保单号|num&len=14">
				</TD>
			</tr>
		</table>
		</Div>
		</div>
		 <INPUT VALUE="查  询" class=cssButton TYPE=button onclick="easyQueryClick();">
		<INPUT VALUE="申  请" class=cssButton TYPE=button onclick="ApplyInput();"> 
		<table>
			<tr>
				<td class=common>
					<IMG  src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLCGrp1);">
				</td>
				<td class=titleImg>投保单信息</td>
			</tr>
			<INPUT type="hidden" class=Common name=MissionID  id="MissionID" value="">
			<INPUT type="hidden" class=Common name=SubMissionID id="SubMissionID"  value="">
		</table>
		<Div id="divLCGrp1" style="display: ''" align=center>
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanGrpGrid" ></span>
					</td>
				</tr> 
			</table>
			<div style="display: none" >
			<INPUT VALUE="首  页" class=cssButton90 TYPE=button onClick="turnPage1.firstPage();">
			<INPUT VALUE="上一页" class=cssButton91 TYPE=button onClick="turnPage1.previousPage();">
			<INPUT VALUE="下一页" class=cssButton92 TYPE=button onClick="turnPage1.nextPage();">
			<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onClick="turnPage1.lastPage();">
			</div>
		</div>
			 <INPUT VALUE="开始录入" class=cssButton TYPE=button onclick="GoToInput();"> 
			<!--INPUT class=cssButton VALUE="记事本查看" TYPE=button onclick="showNotePad();"-->
		<input class=common type=hidden name=tFlag id="tFlag" value="<%=tFlag%>">
		<Input class=common type=hidden name=Operator id="Operator" >
	</form>
	<br>
	<br>
	<br>
	<br>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
