<%
//�������ƣ�LLInqCourseMissInpute.jsp
//�����ܣ���ȡ�������������
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
	<%
	    GlobalInput tGlobalInput = new GlobalInput(); 
	    tGlobalInput = (GlobalInput)session.getValue("GI");
    %>
     <script>	
	var operator = "<%=tGlobalInput.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGlobalInput.ManageCom%>"; //��¼��½����
	var comcode = "<%=tGlobalInput.ComCode%>"; //��¼��½����
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
    <title>�ʱ���Ϣ</title>  
</head>
<body  onload="initForm();initElementtype();">
	<Form action=""  method=post name=fm id=fm target="fraSubmit">
	<div id ="CourseInputPool"></div>
<!-- 		<Table>
	        <TR>
	        	<TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLInqApply);"></TD>
	         	<TD class= titleImg>��ȡ�����������</TD>
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
	                <td><INPUT class=cssButton VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();HighlightByRow()"></td>
	                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();HighlightByRow()"></td>
	                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();HighlightByRow()"></td>
	                <td><INPUT class=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();HighlightByRow()"></td>
	            </tr> 
           </table>    
        </Div>   
        -->
	    <%
	    //�������������ر���
	    %>   
	    <Input type=hidden name="Operator" >
	    <Input type=hidden name="ComCode" >
	    <Input type=hidden id="ManageCom" name="ManageCom"><!--����-->
        <Input type=hidden name=CurDate  id="CurDate">
        <Input type=hidden id="MissionID" 	 name= "MissionID">
		<Input type=hidden id="SubMissionID" name= "SubMissionID">
		<Input type=hidden id="ActivityID" name= "ActivityID">
		</Form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
