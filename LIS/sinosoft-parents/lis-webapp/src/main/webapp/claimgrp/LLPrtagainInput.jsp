<%
//**************************************************************************************************
//�ļ����ƣ�LLPrtagainInput.jsp
//�ļ����ܣ���֤����ԭ��¼��ҳ��
//�����ˣ�yuejw
//��������: 2005-08-21
//ҳ������: �����ڲ���֤ʱ¼�벹��ԭ��
//**************************************************************************************************
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claimgrp.*"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
    String CurrentDate = PubFun.getCurrentDate();
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	String tPrtSeq=request.getParameter("PrtSeq"); //��ˮ��
%>
<head>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <SCRIPT src="LLPrtagain.js"></SCRIPT>
    <%@include file="LLPrtagainInit.jsp"%>
</head>

<body  onload="initForm()" >
<form method=post name=fm target="fraSubmit">
	<Div id= "" style= "display: ''">
		<Table class=common>
            <TR class=common>
                <TD class=title> ��ˮ�� </TD>
                <TD class= input><Input class="readonly" readonly  name="PrtSeq"></TD>
                <TD class=title> �ⰸ�� </TD>
                <TD class= input><Input class="readonly" readonly  name="OtherNo"></TD>
                <TD class=title> �������� </TD>
                <TD class= input><Input class="readonly" readonly  name="Code"></TD>                
            </TR>
            <TR class=common>
		        <TD class=title>������</TD> 
		        <TD class= input><Input class="readonly" readonly  name="ReqOperator"></TD>
		        <TD class=title> ������� </TD>  <!--##ondblclick="return showCodeList('stati',[this,ReqComName],[0,1]);"##-->
		        <TD class= input><Input class=codeno readonly name="ReqCom"  onkeyup="return showCodeListKey('stati',[this,ReqComName],[0,1]);"><input class=codename name="ReqComName" readonly ></TD>
                <TD class=title> ������� </TD>
                <TD class= input><Input class="readonly" readonly  name="ManageCom"></TD>
            </TR>
        	<TR  class= common>
                <TD class=title> ִ���� </TD>
                <TD class= input><Input class="readonly" readonly  name="ExeOperator" ></TD>
                <TD class=title> ��ӡ��ʽ </TD>
                <TD class= input><Input class="readonly" readonly  name="PrtType" ></TD>
                <TD class=title> ��ӡ״̬ </TD>
                <TD class= input><Input class="readonly" readonly  name="StateFlag" ></TD>
		    </TR>
        </table>
        <hr>
        <Table class=common>
		    <TR class= common>
			  <TD class= title> ��¼�벹��ԭ�� </TD>
			</TR> 
			<TR class= common>       
			  <TD class= input> <textarea name="Remark" cols="100" rows="3" witdh=25% class="common"  ></textarea></TD>
			</TR>   
		</Table>
	</Div>
    <table>
        <tr>
            <td><input class=cssButton name="PrtagainReasion" VALUE="����ԭ�򱣴�"  TYPE=button onclick="showPrtagainReasion()"></td>
            <td><input class=cssButton  disabled name="Prtagain" VALUE="����֤"  TYPE=button onclick="showPrtAffix()"></td>
            <td><input class=cssButton name="back" VALUE="��   ��"  TYPE=button onclick="goToBack()"></td>
        </tr>
    </table>
    <!---��������--->
	<input type=hidden id="PrtCode" name="PrtCode">
	<input type=hidden id="ClmNo" name="ClmNo">
	<input type=hidden id="CustomerNo" name="CustomerNo">
	<input type=hidden id="fmtransact" name="fmtransact">
	
    
</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
