<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：UWHealthInput.jsp
//程序功能：个单新契约扫描件保单录入
//创建日期：
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<%
//个人下个人
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
 <!-- modified by lzf -->
<script src="../common/javascript/jquery.workpool.js"></script>
<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
<SCRIPT src="LLHealthInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="LLHealthInit.jsp"%>
<title>扫描录入</title>
</head>
<body  onload="initForm();initElementtype();" >
	<form action="./UWHealthSave.jsp" method=post name=fm id=fm target="fraSubmit">
	<div id = "LLHealthPool"></div>
		<!-- 保单信息部分 -->
<!-- lzf 		<table class=common border=0 width=100%>
			<tr>
				<td class=titleImg align=center>请输入查询条件：</td>
			</tr>
		</table>
		<table class=common>
			<TR class=common>
				<TD class=title>合同号码</TD>
				<TD class=input>		
        <Input class= common name=QContNo > </TD>
				</TD>
				<TD class=title>通知书流水号</TD>
				<TD class=input>
				<Input class= common name=QPrtSeq > </TD>
				</TD>				
				<TD class=title>经办日期</TD>
				<TD class=input>
					<Input class="coolDatePicker"  dateFormat="short" name=QHandleDate verify="经办日期|date" >
				</TD>
			</tr>
			<tr id='SubTitle' style="display:none">
				<TD class=title>经办人</TD>
				<TD class=input>
					<Input class= common name=QHandler > </TD>					
			</tr>
		</table>
		<INPUT VALUE="查  询" class=cssButton TYPE=button onclick="easyQueryClick();">
		
		<table>
			<tr>
				<td class=common>
					<IMG  src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLCGrp1);">
				</td>
				<td class=titleImg>保单信息</td>
			</tr>
			<INPUT type="hidden" class=Common name=MissionID  value="">
			<INPUT type="hidden" class=Common name=SubMissionID  value="">
		</table>
		<Div id="divLCGrp1" style="display: ''" align=center>
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanGrpGrid" ></span>
					</td>
				</tr>
			</table>
			<INPUT VALUE="首  页" class=cssButton TYPE=button onclick="getFirstPage();">
			<INPUT VALUE="上一页" class=cssButton TYPE=button onclick="getPreviousPage();">
			<INPUT VALUE="下一页" class=cssButton TYPE=button onclick="getNextPage();">
			<INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="getLastPage();">
		</div>
		-->
		<p>
			<INPUT VALUE="开始录入" class=cssButton TYPE=button onclick="showHealthQ();">
		</p>
		<input class=common type=hidden id="tFlag" name=tFlag value="<%=tFlag%>">
		<Input class=common type=hidden id="Operator" name=Operator >
		<Input class=common type=hidden id="ComCode" name="ComCode" value="">
		<Input class=common type=hidden id="QContNo" name=QContNo >
		<Input class=common type=hidden id="QPrtSeq" name=QPrtSeq >
		<Input class=common type=hidden id="QHandler" name=QHandler >
		<Input class=common type=hidden id="MissionID" name=MissionID >
		<Input class=common type=hidden id="SubMissionID" name=SubMissionID >
		<Input class=common type=hidden id="ActivityID" name=ActivityID >
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>







