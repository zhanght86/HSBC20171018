<%
//程序名称：LLInqConclusionMissInput.jsp
//程序功能：调查结论信息
//创建日期：2005-06-27
//创建人  ：yuejw
//更新记录：
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claimgrp.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head>
	<%
	    GlobalInput tGlobalInput = new GlobalInput(); 
	    tGlobalInput = (GlobalInput)session.getValue("GI");
    %>
    <title>调查结论信息</title>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <script src="./LLInqConclusionMiss.js"></script>
    <%@include file="LLInqConclusionMissInit.jsp"%>
</head>
<body onload="initForm();">
<form name=fm target=fraSubmit method=post>
    
    <!--信息列表-->
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqConclusionGrid);"></TD>
            <TD class= titleImg> 调查结论 </TD>
        </TR>
    </Table>
    <Div  id= "divLLInqConclusionGrid" style= "display: ''">
         <Table  class= common>
             <TR  class= common>
                 <TD text-align: left colSpan=1><span id="spanLLInqConclusionGrid" ></span> </TD>
             </TR>
         </Table>
         <table>
             <tr>
                 <td><INPUT class=cssButton VALUE=" 首页 " TYPE=button onclick="turnPage.firstPage();"></td>
                 <td><INPUT class=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></td>
                 <td><INPUT class=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></td>
                 <td><INPUT class=cssButton VALUE=" 尾页 " TYPE=button onclick="turnPage.lastPage();"></td>
             </tr>
         </table>
    </Div>

	    <%
	    //保存数据用隐藏表单区
	    %>   
	    <Input type=hidden name="Operator" >
	    <Input type=hidden name="ComCode" >
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
