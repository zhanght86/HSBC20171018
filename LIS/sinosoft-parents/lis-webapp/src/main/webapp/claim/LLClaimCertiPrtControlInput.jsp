<html>
<%
//�ļ�����:LLClaimCertiPrtControlInput.jsp
//�ļ����ܣ����ⵥ֤��ӡ����
//�������ڣ�2005-10-15  ������ ��                   
//���¼�¼��
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
	<% 
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");		  
	String tClmNo=request.getParameter("ClmNo"); //�ⰸ�� 
	loggerDebug("LLClaimCertiPrtControlInput","---�ⰸ��=="+tClmNo);  		  
	%> 	
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>    
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>     
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="LLClaimCertiPrtControl.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLClaimCertiPrtControlInit.jsp"%>
</head>
<body onload="initForm();">
<form  method=post name=fm id=fm target="fraSubmit">
   
    <Table>
        <TR>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,ClaimCertiList);"></TD>
            <td class= titleImg>���ⰸ�¿��Խ���������ӡ���Ƶĵ�֤�б�</TD>
        </TR>
    </Table>
    <Div id= "ClaimCertiList" style= "display: ;text-align:center">
        <Table class= common>
            <TR class= common>
                <td style="text-align: left" colSpan=1><span id="spanClaimCertiGrid" ></span></TD>
            </TR>      
        </Table>
        <Table style="display:none">
             <TR>
                 <TD><Input class=cssButton90 value="��  ҳ" type=button onclick="turnPage.firstPage();"></TD>
                 <TD><Input class=cssButton91 value="��һҳ" type=button onclick="turnPage.previousPage();"></TD>
                 <TD><Input class=cssButton92 value="��һҳ" type=button onclick="turnPage.nextPage();"></TD>
                 <TD><Input class=cssButton93 value="β  ҳ" type=button onclick="turnPage.lastPage();"></TD>
             </TR>
         </Table>
    </Div>
    <hr class=line>
    <Table>
		<TR>
			<TD><Input class=cssButton value="�ύ������ӡ" type=button onclick="showCertiPrtControlSave();"></TD>
			<TD><Input class=cssButton value="��  ��" type=button onclick="top.close()"></TD>
		</TR>
   </Table>
    <!--##��������##-->
   <Input class=common type=hidden id="ClmNo" name="ClmNo" >
   
</form>
 	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>




