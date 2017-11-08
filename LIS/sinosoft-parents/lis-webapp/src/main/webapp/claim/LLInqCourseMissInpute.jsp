<%
//程序名称：LLInqCourseMissInpute.jsp
//程序功能：获取调查任务处理队列
%>
<!--用户校验类-->
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
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
    <!-- modified by lzf -->
   <script src="../common/javascript/jquery.workpool.js"></script>
   <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <SCRIPT src="LLInqCourseMiss.js"></SCRIPT>
    <%@include file="LLInqCourseMissInit.jsp"%>
    <title>呈报信息</title>  
</head>
<body  onload="initForm();initElementtype();">
	<Form action=""  method=post name=fm id=fm target="fraSubmit">
	<div id ="CourseInputPool"></div>
<!-- 		<Table>
	        <TR>
	        	<TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLInqApply);"></TD>
	         	<TD class= titleImg>获取调查任务队列</TD>
	       	</TR>
	    </Table> 
        <Div  id= "DivLLInqApply" style= "display: ''">
		    <span id="operateButton1" >
            <Table  class= common>
            	<TR class= common>
        	    	<TD text-align: left colSpan=1>
                    	<span id="spanLLInqApplyGrid"></span> 
                    </TD>
                </TR>
            </Table>  
	        <table> 
	            <tr>  
	                <td><INPUT class=cssButton VALUE="首  页" TYPE=button onclick="turnPage.firstPage();HighlightByRow()"></td>
	                <td><INPUT class=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();HighlightByRow()"></td>
	                <td><INPUT class=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();HighlightByRow()"></td>
	                <td><INPUT class=cssButton VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();HighlightByRow()"></td>
	            </tr> 
           </table>    
        </Div>   
        -->
	    <%
	    //保存数据用隐藏表单区
	    %>   
	    <Input type=hidden name="Operator" >
	    <Input type=hidden name="ComCode" >
	    <Input type=hidden id="ManageCom" name="ManageCom"><!--机构-->
        <Input type=hidden name=CurDate  id="CurDate">
        <Input type=hidden id="MissionID" 	 name= "MissionID">
		<Input type=hidden id="SubMissionID" name= "SubMissionID">
		<Input type=hidden id="ActivityID" name= "ActivityID">
		</Form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
