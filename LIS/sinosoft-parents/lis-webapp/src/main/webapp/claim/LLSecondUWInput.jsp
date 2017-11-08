<html>
<%
//程序名称：LLSecondUWInput.jsp
//程序功能：合同单人工核保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 <%@page import = "com.sinosoft.utility.*"%>
 <%@page import = "com.sinosoft.lis.schema.*"%>
 <%@page import = "com.sinosoft.lis.vschema.*"%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>
 <head >
	<%
	//##############BGN#########################
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI"); 
	String  tCaseNo = request.getParameter("CaseNo"); //赔案号
 	String tInsuredNo= request.getParameter("InsuredNo"); //出险人编码
	String tInsuredName = request.getParameter("InsuredName");
	String tRptorName = request.getParameter("RptorName"); //报案人姓名
	String tMissionID=request.getParameter("MissionID");//工作流id
	loggerDebug("LLSecondUWInput","JSP中的tRptorName====================>"+tRptorName);
	//tInsuredName =  new String(tInsuredName.getBytes("ISO-8859-1"),"GB2312"); //出险人姓名
	//tRptorName =  new String(tRptorName.getBytes("ISO-8859-1"),"GB2312"); //出险人姓名
	//###############END######################
	%>
   <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
   <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
   <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
   <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
   <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
   <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
   <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
   <SCRIPT src="LLSecondUW.js"></SCRIPT>
   <%@include file="LLSecondUWInit.jsp"%>
 </head>
<body  onload="initForm();" >
<form method=post name=fm id=fm target="fraSubmit"> 
	<Table>
		<TR>
			<TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCont);" ></TD>
			<TD class= titleImg>该被保险人下的所有合同</TD>
		</TR>
	</Table>
	<Div  id= "divCont" style= "display: ''">
		<Table  class= common>
			<TR  class= common>
				<TD text-align: left colSpan=1><span id="spanLCContGrid" ></span></TD>
			</TR>
		</Table>
		
	</Div>
	<br>
	<Table class=title>
		<TR  class= common>
			<TD  class= title> 客户目前健康状况介绍 </TD>
		</TR>
		<TR  class= common>
			<TD  class= input><textarea name="Note" id="Note" cols="224" rows="4" class="common" ></textarea></TD>
		</TR>
	</Table>
	<!--<Table style="display:none">
		<TR>
			<input name="" class=cssButton type=button value="保单查询" onclick="showPolDetail();">
			<input name="" class=cssButton type=button value="保全明细" onclick="showPolBqEdor();">	 	
		 	<input name="" class=cssButton type=button value="确    认" onclick="LLSeUWSaveClick()">
		 	<input name="" class=cssButton type=button value="返    回" onclick="top.close();">
		</TR>
	</Table>-->
    <br>
    <a href="javascript:void(0);" class="button" onClick="showPolDetail();">保单查询</a>
    <a href="javascript:void(0);" class="button" onClick="showPolBqEdor();">保全明细</a>
    <a href="javascript:void(0);" class="button" onClick="LLSeUWSaveClick();">确    认</a>
    <a href="javascript:void(0);" class="button" onClick="top.close();">返    回</a>
	<!--****隐藏区域*****-->
	<Input type=hidden id="ManageCom" name="ManageCom"><!--机构-->
 	<Input type=hidden id="CaseNo" name="CaseNo" ><!--赔案号-->
 	<Input type=hidden id="InsuredNo" name="InsuredNo" ><!--出险人号码-->
 	<Input type=hidden id="InsuredName" name="InsuredName" ><!--出险人姓名-->
	<Input type=hidden id="RptorName" name="RptorName" ><!--报案人姓名-->
	<Input type=hidden id="CurrentDate" name="CurrentDate" ><!--发起二核日期-->
	<Input type=hidden id="MissionID" name="MissionID" >
	
</form>
  	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
