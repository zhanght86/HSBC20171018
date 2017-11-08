<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：ScanContInput.jsp
//程序功能：个单新契约扫描件保单录入
//创建日期：2008-11-3 11:10:36
//创建人  ：liuqh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<%
//个人下个人
String tContNo=request.getParameter("ContNo");
loggerDebug("UWModifyFloatRate","ContNo:"+tContNo);
String tLoadFlag = request.getParameter("LoadFlag");
loggerDebug("UWModifyFloatRate","tLoadFlag:"+tLoadFlag);
String tPolNo = request.getParameter("SelPolNo");
loggerDebug("UWModifyFloatRate","tPolNo: "+tPolNo);
String tInsuredNo = request.getParameter("InsuredNo");
loggerDebug("UWModifyFloatRate","InsuredNo:"+tInsuredNo);
String tFlag="";
GlobalInput tGI=new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");
tFlag=request.getParameter("type");
%>
<script>
var contNo="<%=tContNo%>";          //个人单的查询条件.
var polNo="<%=tPolNo%>";
var insuredNo = "<%=tInsuredNo%>";
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
<SCRIPT src="UWModifyFloatRate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="UWModifyFloatRateInit.jsp"%>
<title>浮动费率修改</title>
</head>
<body  onload="initForm();" >
	<form action="./UWModifyFloatRateSave.jsp" method=post id="fm" name=fm target="fraSubmit">
		<!-- 保单信息部分 -->
		<table>
			<tr>
				<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
				<td class=titleImg>投保单信息</td>
			</tr>
			<INPUT type="hidden" class=Common id="MissionID" name=MissionID  value="">
			<INPUT type="hidden" class=Common id="SubMissionID" name=SubMissionID  value="">
		</table>
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanRiskFloatRateGrid" ></span>
					</td>
				</tr>
			</table>
			<center>
			<INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage1.firstPage();">
			<INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage1.previousPage();">
			<INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage1.nextPage();">
			<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage1.lastPage();">
			</center>
		<p>
		 <input class="cssButton" value="员工特约录入" type="button" id="Button1" name="Button1" onclick="specInput();">
			<INPUT VALUE="修   改" class=cssButton TYPE=button onclick="return submitForm();">
			<INPUT VALUE="删除员工特约" class=cssButton TYPE=button onclick="return deleteSpec();">
			<!--INPUT class=cssButton VALUE="记事本查看" TYPE=button onclick="showNotePad();"-->
		</p>
		<div id = "divChangeResult" style = "display: none">
      	  <table  class= common align=center>
          	<td height="24"  class= title>
            		员工特约:
          	</TD>
		<tr></tr>
      		<td  class= input><textarea name="FloatRateIdea" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
    	 </table>
     </div>
		<input class=common type=hidden id="tFlag" name=tFlag value="<%=tFlag%>">
		<Input class=common type=hidden id="Operator" name=Operator >
		<Input class=common type=hidden id="ProposalNo" name=ProposalNo >
		<Input class=common type=hidden id="ContNo" name=ContNo value="<%=tContNo%>">
		<Input class=common type=hidden id="InsuredNo" name=InsuredNo value="<%=tInsuredNo%>">
		<Input class=common type=hidden id="PolNo" name=PolNo >
		<Input class=common type=hidden id="SamePersonFlag" name=SamePersonFlag >
		<Input class=common type=hidden id="NewFloatRate" name=NewFloatRate >
		<Input class=common type=hidden id="DivFlag" name=DivFlag value = "1" >
		<Input class=common type=hidden id="SpecFlag" name=SpecFlag value = "1" >
		<Input class=common type=hidden id="SpecType" name=SpecType value = "" >
		<Input class=common type=hidden id="SpecCode" name=SpecCode value = "" >
		<Input class=common type=hidden id="SpecReason" name=SpecReason value = "" >
		<Input class=common type=hidden id="SerialNo" name=SerialNo value = "" >
		<Input class=common type=hidden id="SpecOperate" name=SpecOperate value = "INSERT" >
		<Input class=common type=hidden id="GetDutyKind" name=GetDutyKind value = "INSERT" >
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
