<%
//�������ƣ�LLInqFeeQueryInit.jsp
//�����ܣ����������Ϣ��ѯҳ��
//�������ڣ�2005-06-23
//������  ��yuejw
//���¼�¼��
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claimgrp.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
	<%
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");	  
	  String tClmNo = request.getParameter("ClmNo");	//�ⰸ��
	  String tInqNo = request.getParameter("InqNo");	//�������  
%>
<title>���ⰸ���������Ϣ�б�</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="LLInqFeeQuery.js"></SCRIPT>
    <%@include file="LLInqFeeQueryInit.jsp"%>
</head>
</head>
<body onload="initForm();">  
<form name=fm target=fraSubmit method=post>
    <br>  
    <Table>
        <TR>
            <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqFeeGrid);"></TD>
            <TD class= titleImg> ���ⰸ�����е�������б� </TD>
        </TR>
    </Table>       
    <Div id= "divLLInqFeeGrid" style= "display: ''">
         <Table class= common>
             <TR class= common>
                 <TD text-align: left colSpan=1><span id="spanLLInqFeeGrid" ></span> </TD>
             </TR>
         </Table>
         <table> 
             <tr>  
                 <td><INPUT class=cssButton VALUE=" ��ҳ " TYPE=button onclick="turnPage.firstPage();"></td>
                 <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
                 <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
                 <td><INPUT class=cssButton VALUE=" βҳ " TYPE=button onclick="turnPage.lastPage();"></td>
             </tr> 
         </table>    
    </Div>
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqFeeInfo);"></TD>
            <TD class= titleImg> �鿴���������ϸ��Ϣ </TD>
        </TR>
    </Table>
    <Div id= "divLLInqFeeInfo" style= "display: ''">    
        <TABLE class=common>
           <tr class=common>
               <td class=title> �ⰸ�� </td>
               <td class= input><Input type="input" class="readonly" readonly name="ClmNo1"></td>
               <td class=title> ������� </td>
               <td class= input><Input type="input" class="readonly" readonly name="InqNo1"></td>
               <td class=title> ������� </td>
               <!--<td class= input><Input type="input" class="readonly" readonly name="InqDept"></td>  -->                                 
               <td class= input><Input class=codeno disabled name="InqDept" ondblclick="return showCodeList('stati',[this,InqDeptName],[0,1]);" onkeyup="return showCodeListKey('stati',[this,InqDeptName],[0,1]);"><input class=codename name="InqDeptName" readonly=true></TD> 	             
           </tr>    
           <TR  class= common>
        		<td class=title> �������� </td>
		        <td class= input><Input class=codeno disabled name="FeeType" ondblclick="return showCodeList('llinqfeetype',[this,FeeTypeName],[0,1]);" onkeyup="return showCodeListKey('llinqfeetype',[this,FeeTypeName],[0,1]);"><input class=codename name="FeeTypeName" ></TD>
                <TD  class= title>���ý��</TD>
                <!--<TD  class= input><input class= common readonly name="FeeSum"></TD>-->
                <td class= input><Input type="input" class="readonly" readonly name="FeeSum"></td>
                <TD  class= title>����ʱ��</TD>
                <!--<TD  class= input><input class= 'coolDatePicker' dateFormat="Short" readonly name="FeeDate"></TD>-->
                <TD  class= input><input type="input" class="readonly" readonly  name="FeeDate"></TD>
           </TR>
           <TR  class= common>
                <TD  class= title>�����</TD>
                <TD  class= input><input type="input" class="readonly" readonly  name="Payee"></TD>
                <TD  class= title>��ʽ</TD>
                <td class= input><Input class=codeno disabled name="PayeeType" ondblclick="return showCodeList('llpaymode',[this,PayeeTypeName],[0,1]);" onkeyup="return showCodeListKey('llpaymode',[this,PayeeTypeName],[0,1]);"><input class=codename name="PayeeTypeName" readonly=true></TD>
           </TR>
        </TABLE>        
        <Table class= common>                	            
           <tr class= common>
               <td class= title> ��ע </td>
           </tr> 
           <tr class= common>       
               <td class= input> <textarea name="Remark" cols="100" rows="4" witdh=25% class="common" readonly ></textarea></td>
           </tr>       
        </TABLE>  
    </div>
    <Input class=cssButton value=" �� �� " type=button onclick=top.close() align= center>     

    <!--//�������ݵ����ر�-->    
    <Input type=hidden id="ClmNo" name="ClmNo"><!--�ⰸ��-->
    <Input type=hidden id="InqNo" name="InqNo"><!--�������-->
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>          
</Form>
</body>
</html>
