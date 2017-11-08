<html>
<%
//程序名称：LLSecondUWInput.jsp
//程序功能：合同单人工核保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
 <%@page contentType="text/html;charset=GBK" %>
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
	tInsuredName =  new String(tInsuredName.getBytes("ISO-8859-1"),"GB2312"); //出险人姓名
	//###############END######################
	%>
   <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
   <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
   <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
   <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
   <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
   <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
   <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
   <SCRIPT src="LLSecondUW.js"></SCRIPT>
   <%@include file="LLSecondUWInit.jsp"%>
 </head>
<body  onload="initForm();" >
<form action=method=post name=fm target="fraSubmit"> 
	<table>
		<TR>
			<TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLLLMainAskInput1);"></TD>
			<TD class= titleImg>该被保险人下的所有合同</TD>
		</TR>
	</table>
	<Div  id= "divCont" align= center style= "display: ''">
		<table  class= common>
			<TR  class= common>
				<TD text-align: left colSpan=1><span id="spanLCContGrid" ></span></TD>
			</TR>
		</table>
		<INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage.firstPage();"> 
		<INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage.previousPage();"> 					
		<INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage.nextPage();"> 
		<INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage.lastPage();"> 	
	</Div>

	<table class=title>
		<TR  class= common>
			<TD  class= common> 出险人目前健康状况介绍 </TD>
		</TR>
		<TR  class= common>
			<TD  class= common><textarea name="Note" cols="100" rows="5" class="common" ></textarea></TD>
		</TR>
	</table>
 	<input name="LLSeUWSave" class=cssButton type=button value="确  认" onclick="LLSeUWSaveClick()">
 	<input name="goBack" class=cssButton type=button value="返  回" onclick="top.close();">
	
	<Input type=hidden id="ManageCom" name="ManageCom"><!--机构-->
 	<INPUT type=hidden id="CaseNo" name="CaseNo" ><!--赔案号-->
 	<INPUT type=hidden id="InsuredNo" name="InsuredNo" ><!--出险人号码-->
 	<INPUT type=hidden id="InsuredName" name="InsuredName" ><!--出险人姓名-->

</form>
  	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
