<%
//**************************************************************************************************
//Name：LLClaimPrepayMissInput.jsp
//Function：待审核工作队列工作队列信息（准备用于“预付”处理）
//Author：yuejw
//Date: 2005-7-5 16:00
//Desc:
//**************************************************************************************************
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<%
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
%>
<script>	
	var operator = "<%=tGlobalInput.Operator%>";   //记录操作员
	var manageCom = "<%=tGlobalInput.ManageCom%>"; //记录登陆机构
	var comcode = "<%=tGlobalInput.ComCode%>"; //记录登陆机构
</script>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <!-- modified by lzf -->
   <script src="../common/javascript/jquery.workpool.js"></script>
   <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <SCRIPT src="LLClaimPrepayMiss.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLClaimPrepayMissInit.jsp"%>
    <title> 预付管理</title>
</head>
<body  onload="initForm();initElementtype();" >
<form  method=post name=fm id=fm target="fraSubmit">
<div id="ClaimPrepayPool"></div>
<div>
	<Input type=hidden id="Operator" name="Operator" >
    <Input type=hidden id="ComCode" name="ComCode" >      
    <Input type=hidden id="tRptNo" name= "tRptNo">     <!--//赔案号-->
    <Input type=hidden id="tRptorState" name= "tRptorState">     <!--//报案状态--> 
    <Input type=hidden id="tCustomerNo" name= "tCustomerNo">     <!--//出险人编码--> 
    <Input type=hidden id="tCustomerName" name= "tCustomerName">    <!--// 出险人姓名--> 
    <Input type=hidden id="tCustomerSex" name= "tCustomerSex">    <!--// 出险人性别"t-->
    <Input type=hidden id="tAccDate" name= "tAccDate">     <!--//事故日期" -->
    <Input type=hidden id="tRgtClass" name= "tRgtClass">     <!--//申请类型" -->
    <Input type=hidden id="tRgtObj" name= "tRgtObj">     <!--//号码类型" -->
    <Input type=hidden id="tRgtObjNo" name= "tRgtObjNo">     <!--//其他号码" -->
    <Input type=hidden id="tPopedom" name= "tPopedom">     <!--//核赔师权限"-->
    <Input type=hidden id="tPrepayFlag" name= "tPrepayFlag">     <!--//预付标志"-->
    <Input type=hidden id="tComeWhere" name= "tComeWhere">     <!--//来自"-->
    <Input type=hidden id="tAuditer" name= "tAuditer">     <!--//审核操作人"-->
    <Input type=hidden id="tMngCom" name= "tMngCom">     <!--//机构"-->
    <Input type=hidden id="tComFlag" name= "tComFlag">     <!--//权限跨级标志-->
    <Input type=hidden name=CurDate  id="CurDate">    
	<input type=hidden id="tMissionID" 	 name= "tMissionID">
	<input type=hidden id="tSubMissionID" name= "tSubMissionID">
	<input type=hidden id="tActivityID" name= "tActivityID">	
</div>
</form>
 <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
    <!-- 共享池部分 -->
	<!--
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);"></TD>
            <TD class= titleImg> 请输入查询条件： </TD>
        </TR>
    </Table>   -->

<!-- lzf     <Div  id= "divSearch" style= "display: 'none'">
        <table class= common>
            <TR class= common>
                <TD class= title> 赔案号 </TD>
                <TD class= input> <Input class= common name="RptNo" ></TD>
                <TD class= title> 客户编码 </TD>
                <TD class= input> <Input class= common name="CustomerNo" ></TD>
                <TD class= title> 客户姓名 </TD>
                <TD class= input> <Input class= common name="CustomerName" ></TD>
            </TR>
        </table>
    </DIV>
    <!-- 
    <Table>
        <TR>
            <TD> <Input TYPE=button class=cssButton VALUE="查  询" onclick="LLClaimAuditGridQuery();"></TD>
        </TR>
    </Table>
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimAudit);"></TD>
            <TD class= titleImg> 共享池 </TD>
        </TR>
    </Table>  -->
<!-- lzf   <Div id= "divLLClaimAudit" style= "display: 'none'" align = center>
        <Table  class= common>
            <TR  class= common>
                <TD text-align: left colSpan=1><span id="spanLLClaimAuditGrid" ></span> </TD>
            </TR>
        </Table>
        <Table>
            <tr>
                <td><INPUT class=cssButton VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr>
        </Table>
    </Div> 
    <hr>
    <Table>
        <TR>
        	<!-- <TD><INPUT class=cssButton id="riskbutton" VALUE="申  请" TYPE=button onClick="ApplyLLClaimAudit();"></TD> -->
<!-- lzf       </TR>
    </Table>
    <table>
        <tr>
            <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimPrepayGrid);"></td>
            <td class= titleImg> 工作队列 </td>
        </tr>
    </table>
    <Div id= "divLLClaimPrepayGrid" style= "display: ''" align = center>
        <table  class= common >
            <tr  class=common>
  				<td text-align: left colSpan=1 ><span id="spanLLClaimPrepayGrid" ></span></td>
            </tr>
        </table>
        <table>
            <tr>
                <td><INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage2.firstPage();HighlightSelfByRow()"></td>
                <td><INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage2.previousPage();HighlightSelfByRow()"></td>
                <td><INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage2.nextPage();HighlightSelfByRow()"></td>
                <td><INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage2.lastPage();HighlightSelfByRow()"></td>
            </tr>
        </table>        
    </div>    
    <!--//保存数据用隐藏表单区-->
<!--     <Input type=hidden id="Operator" name="Operator" >
    <Input type=hidden id="ComCode" name="ComCode" >  
    
    <Input type=hidden id="tRptNo" name= "tRptNo">     <!--//赔案号-->
<!--     <Input type=hidden id="tRptorState" name= "tRptorState">     <!--//报案状态--> 
<!--     <Input type=hidden id="tCustomerNo" name= "tCustomerNo">     <!--//出险人编码--> 
 <!--    <Input type=hidden id="tCustomerName" name= "tCustomerName">    <!--// 出险人姓名--> 
<!--     <Input type=hidden id="tCustomerSex" name= "tCustomerSex">    <!--// 出险人性别"t-->
<!--     <Input type=hidden id="tAccDate" name= "tAccDate">     <!--//事故日期" -->
<!--     <Input type=hidden id="tRgtClass" name= "tRgtClass">     <!--//申请类型" -->
<!--     <Input type=hidden id="tRgtObj" name= "tRgtObj">     <!--//号码类型" -->
<!--     <Input type=hidden id="tRgtObjNo" name= "tRgtObjNo">     <!--//其他号码" -->
<!--     <Input type=hidden id="tPopedom" name= "tPopedom">     <!--//核赔师权限"-->
<!--     <Input type=hidden id="tPrepayFlag" name= "tPrepayFlag">     <!--//预付标志"-->
<!--     <Input type=hidden id="tComeWhere" name= "tComeWhere">     <!--//来自"-->
<!--     <Input type=hidden id="tAuditer" name= "tAuditer">     <!--//审核操作人"-->
<!--     <Input type=hidden id="tMngCom" name= "tMngCom">     <!--//机构"-->
<!--     <Input type=hidden id="tComFlag" name= "tComFlag">     <!--//权限跨级标志-->
<!--     <Input type=hidden name=CurDate  id="CurDate">
    
	<input type=hidden id="tMissionID" 	 name= "tMissionID">
	<input type=hidden id="tSubMissionID" name= "tSubMissionID">
	<input type=hidden id="tActivityID" name= "tActivityID">
	
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  -->
</body>
</html>
