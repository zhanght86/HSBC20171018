<%
//�������ƣ�LLSubmitApplyDealMissInput.jsp
//�����ܣ��ʱ���Ϣ�������
%>
<!--�û�У����-->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
	<%
	    GlobalInput tGlobalInput = new GlobalInput(); 
	    tGlobalInput = (GlobalInput)session.getValue("GI");
    %>
    <script language="javascript">
    var usercode="<%=tGlobalInput.Operator%>";
    //alert(usercode);
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
    <SCRIPT src="BqSubmitApplyScanMissInput.js"></SCRIPT>
    <%@include file="BqSubmitApplyScanMissInit.jsp"%>
    <title>�ʱ���Ϣ</title>  
</head>
<body  onload="initForm();">
	<Form action="./BqSubmitApplyDealMissSave.jsp"  method=post name=fm id=fm target="fraSubmit">
	
		<Table>
	        <TR>
	        	<TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" ></TD>
	         	<TD class= titleImg>��¼��ʱ�����</TD>
	       	</TR>
	    </Table> 
        <Div  id= "DivLLSubmit" style= "display: '';text-align:center;">
		   <!--<span id="operateButton1" >-->
            <Table  class= common>
            	<TR class= common>
        	    	<TD text-align: left colSpan=1>
                    	<span id="spanLLSubmitApplyGrid"></span> 
                    </TD>
                </TR>
            </Table>  
			<DIV align=center> 
	        <table > 
	            <tr>  
	                <td><INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"></td>
	                <td><INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
	                <td><INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
	                <td><INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"></td>
	            </tr> 
           </table>  
			</div>
        </Div>   
        <hr>      
        
        </Div> 
       
	    <%
	    //�������������ر���
	    %>   
	    <Input type=hidden name="Operator" id=Operator >
	    <Input type=hidden name="ComCode" id=ComCode>
	</Form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</body>
</html>
