<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="../common/jsp/AccessCheck.jsp"%>
<html>
<head>
<%
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI"); 
	String tClmNo = request.getParameter("ClmNo");	//�ⰸ��
	String tCustNo = request.getParameter("CustNo"); //�����˱���	
%>      
    <title>�ⰸ�᰸��֤��ӡ</title>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <script src="./LLClaimEndCaseAffixPrt.js"></script> 
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLClaimEndCaseAffixPrtInit.jsp"%>
</head>

<body  onload="initForm();">
<!--��¼������-->
<br>
<form name=fm target=fraSubmit method=post>
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLEndCasePrtInfo);"></TD>
            <TD class= titleImg> ��ӡ��Ϣ </TD>
        </TR>
    </Table>      
    <Div  id= "divLLEndCasePrtInfo" style= "display: ''" align=center>
		<Table  class= common>
			<TR  class= common>
				<TD  class= title> �ⰸ�� </TD>
				<TD  class= input> <Input class="readonly" readonly name=ClaimNo ></TD>
				<TD  class= title> �����˿ͻ��� </TD>       
				<TD  class= input> <Input class="readonly" readonly name=customerNo ></TD>			    	     					          	
				<TD  class= title> </TD>
				<TD  class= input> </TD>
			</TR>
			<TR  class= common>
				<TD  class= title> ��֤���� </TD>       
				<TD  class= Input><Input class=codeno readonly=true name=PrtCode ondblclick="return showCodeList('llallprtcode',[this,PrtCodeName],[0,1],null,fm.all('ClaimNo').value,'otherno','1');" onkeyup="return showCodeListKey('llallprtcode',[this,PrtCodeName],[0,1],null,fm.all('ClaimNo').value,'otherno','1');" onFocus="showDIVInqGrid();"><input class=codename name=PrtCodeName readonly=true></TD>   				    	     					          	
				<TD  class= title> </TD>
				<TD  class= input> </TD>
				<TD  class= title> </TD>
				<TD  class= input> </TD>
		</Table>
    </Div>   		
    <Div id= "divLLInqApplyGrid" style= "display: 'none'" >
    	<hr>
    	<table  class= common>
	        	<tr  class= common>
	        		<td text-align: left colSpan=1><span id="spanLLInqApplyGrid" ></span></td>
		    		</tr>
	    </table>
	    <hr>
    </Div>   
    
    <Table>
		<Input class=cssButton VALUE="��  ӡ" TYPE=button onclick="showPrtAffix();"> 
		<Input class=cssButton VALUE="��  ��" TYPE=button onclick="returnParent();">  
    </Table>     
    <!---***���ر�����*****-----> 
    <Input type=hidden id="ClmNo" name="ClmNo">   <!----�ⰸ��---------->
    <Input type=hidden id="RptNo" name="RptNo">
    
    <Input type=hidden id="CustNo" name="CustNo" ><!-----�ͻ���--------->
    
    <Input type=hidden id="PrtSeq" name="PrtSeq" ><!-----��ӡ��ˮ��-----> 
    
    <Input type=hidden id="ClmNo3" name="ClmNo3"><!---�ⰸ�ţ����ڵ����ӡ--->
    <Input type=hidden id="InqNo" name="InqNo" ><!-----�������-----> 
    
    <Input type=hidden id="fmtransact" name="fmtransact" ><!---����--->
       
</Form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>     
</body>
</html>
