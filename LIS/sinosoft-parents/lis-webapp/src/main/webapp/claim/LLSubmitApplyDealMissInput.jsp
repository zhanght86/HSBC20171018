<%
//�������ƣ�LLSubmitApplyDealMissInput.jsp
//�����ܣ��ʱ���Ϣ��������
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
    <SCRIPT src="LLSubmitApplyDealMiss.js"></SCRIPT>
    <%@include file="LLSubmitApplyDealMissInit.jsp"%>
    <title>�ʱ���Ϣ</title>  
</head>
<body  onload="initForm();initElementtype();">
	<Form action="./LLSubmitApplyDealMissSave.jsp"  method=post name=fm id=fm target="fraSubmit">
	<div id = "LLSubmitApplyPool"></div>
	<div id = "ReLLSubmitApplyPool"></div>
<!-- 		<Table>
	        <TR>
	        	<TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLSubmit);"></TD>
	         	<TD class= titleImg>���ճʱ�����</TD>
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
	                <td><INPUT class=cssButton VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"></td>
	                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
	                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
	                <td><INPUT class=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"></td>
	            </tr> 
           </table>    
        </Div>   
        <hr>      
        <Table>
	        <TR>
	        	<TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivReLLSubmit);"></TD>
	         	<TD class= titleImg>�����ʱ�����</TD>
	       	</TR>
	    </Table> 
        <Div  id= "DivReLLSubmit" style= "display: ''">
		    <span id="operateButton1" >
            <Table  class= common>
            	<TR class= common>
        	    	<TD text-align: left colSpan=1>
                    	<span id="spanReLLSubmitApplyGrid"></span> 
                    </TD>
                </TR>
            </Table>                 
	        <table> 
	            <tr>  
	                <td><INPUT class=cssButton VALUE="��  ҳ" TYPE=button onclick="returnPage.firstPage();"></td>
	                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="returnPage.previousPage();"></td>
	                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="returnPage.nextPage();"></td>
	                <td><INPUT class=cssButton VALUE="β  ҳ" TYPE=button onclick="returnPage.lastPage();"></td>
	            </tr> 
           </table>     	    
        </Div> 
 -->
	    <%
	    //�������������ر�����
	    %>   
	    <Input type=hidden name="Operator" >
	    <Input type=hidden name="ComCode" >
	    <Input type=hidden id="MissionID" 	 name= "MissionID">
		<Input type=hidden id="SubMissionID" name= "SubMissionID">
		<Input type=hidden id="ActivityID" name= "ActivityID">
	</Form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>