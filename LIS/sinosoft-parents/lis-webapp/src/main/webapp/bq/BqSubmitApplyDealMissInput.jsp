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
    <SCRIPT src="BqSubmitApplyDealMiss.js"></SCRIPT>
    <%@include file="BqSubmitApplyDealMissInit.jsp"%>
    <title>�ʱ���Ϣ</title>  
</head>
<body  onload="initForm();">
	<Form action="./BqSubmitApplyDealMissSave.jsp"  method=post name=fm id=fm target="fraSubmit">
	
		<Table>
	        <TR>
	        	<TD class="common">
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLPSubmit);">
				</TD>
	         	<TD class= titleImg>��Ҫ�Ҵ���ĳʱ��б�</TD>
	       	</TR>
	    </Table> 
        <Div  id= "DivLPSubmit" style= "display: '';">
		    <span id="operateButton1" >
            <Table  class= common>
            	<TR class= common>
        	    	<TD text-align: left colSpan=1>
                    	<span id="spanLPSubmitApplyGrid"></span> 
                    </TD>
                </TR>
            </Table>   
	      <center style="text-align:center;"> 
					<INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();">
	                <INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();">
	                <INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();">
	                <INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
	      </center>
             </Div>
        <Table>
	        <TR>
	        	<TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivReLLSubmit);"></TD>
	         	<TD class= titleImg>�ҳʱ��Ķ���</TD>
	       	</TR>
	    </Table> 
        <Div  id= "DivReLLSubmit" style= "display: ''">
		    <span id="operateButton1" >
            <Table  class= common>
            	<TR class= common>
        	    	<TD text-align: left colSpan=1>
                    	<span id="spanReLPSubmitApplyGrid"></span> 
                    </TD>
                </TR>
            </Table>                 
	        <!--<table align="center"> 
	            <tr>  
	                <td><INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="returnPage.firstPage();"></td>
	                <td><INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="returnPage.previousPage();"></td>
	                <td><INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="returnPage.nextPage();"></td>
	                <td><INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="returnPage.lastPage();"></td>
	            </tr> 
           </table>    --> 	    
        </Div> 
	 <!--<input VALUE="�³�����¼��" class=cssButton TYPE=button onclick="apply();">-->
     <a href="javascript:void(0);" class="button" onClick="apply();">�³�����¼��</a>
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
