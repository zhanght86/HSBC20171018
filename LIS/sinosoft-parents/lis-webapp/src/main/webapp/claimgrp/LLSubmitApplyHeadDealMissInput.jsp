<%
//程序名称：LLSubmitApplyHeadDealMissInput.jsp
//程序功能：呈报信息处理队列
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
	<%
	    GlobalInput tGlobalInput = new GlobalInput(); 
	    tGlobalInput = (GlobalInput)session.getValue("GI");
    %>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT> 
    <SCRIPT src="LLSubmitApplyHeadDealMiss.js"></SCRIPT>
    <%@include file="LLSubmitApplyHeadDealMissInit.jsp"%>
    <title>呈报信息</title>  
</head>
<body  onload="initForm();">
	<Form action=""  method=post name=fm target="fraSubmit">
		<Table>
	        <TR>
	        	<TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLSubmit);"></TD>
	         	<TD class= titleImg>接收呈报队列</TD>
	       	</TR>
	    </Table> 
        <Div  id= "DivLLSubmit" style= "display: ''">
		    <span id="operateButton1" >
            <Table  class= common>
            	<TR class= common>
        	    	<TD text-align: left colSpan=1>
                    	<span id="spanLLSubmitApplyGrid"></span> 
                    </TD>
                </TR>
            </Table>  
	        <table> 
	            <tr>  
	                <td><INPUT class=cssButton VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"></td>
	                <td><INPUT class=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></td>
	                <td><INPUT class=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></td>
	                <td><INPUT class=cssButton VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();"></td>
	            </tr> 
           </table>    
        </Div>   
	    <%
	    //保存数据用隐藏表单区
	    %>   
	    <Input type=hidden name="Operator" >
	    <Input type=hidden name="ComCode" >
	</Form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>