<%
//Name: LLQueryUserInit.jsp
//Function���û���ѯҳ��
//Date��2005.07.11
//Author ��quyang
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
	<%
	//==========BGN�����ղ���
		  GlobalInput tG = new GlobalInput();
		  tG = (GlobalInput)session.getValue("GI");	  
		 
	//==========END
	%>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>     
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <SCRIPT src="LLQueryUser.js"></SCRIPT>  
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
     <%@include file="LLQueryUserInit.jsp"%>
</head>
<body  onload="initForm();">
<form action="" method=post name=fm target="fraSubmit">
    <table>
        <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLQueryUser);"></td>
            <td class= titleImg> �û���ѯ�嵥  </td>
        </tr>
    </table>
    <Div  id= "divLLQueryUser" style= "display: ''">
	<table>
	     <tr><td class=common>
	          <td class= titleImg>��ѯ����</td>
	     </tr>
	</table>
    <table  class= common align=center>
				<TD  class= title>�û�����</TD>
				<TD  class= input> <Input class=common name=UserCodeQ></TD>
                <TD  class= title>�û�����</TD>      
                <TD  class= input> <Input class=common name=UserNameQ></TD> 
				<TD  class= title>��������</TD>      
                <TD  class= input> <Input class=common name=ComCodeQ></TD>
       </TR>   
   </Table>  
      <INPUT VALUE="��  ѯ" class= cssButton TYPE=button onclick="queryClick();">
	  <input class=cssButton  type=button value=" �� �� " onclick="returnParent()">
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1><span id="spanLLQueryUserGrid" ></span></td>
            </tr>      
        </table> 
		<INPUT class= button VALUE=" ��  ҳ " TYPE=button onclick="getFirstPage();"> 
    <INPUT class= button VALUE=" ��һҳ " TYPE=button onclick="getPreviousPage();">                   
    <INPUT class= button VALUE=" ��һҳ " TYPE=button onclick="getNextPage();"> 
    <INPUT class= button VALUE=" β  ҳ " TYPE=button onclick="getLastPage();"> 
   <!--     <input class=cssButton  type=button value=" �� �� " onclick="saveClick()"> 
        <input class=cssButton  type=button value=" �� ѯ " onclick="queryClick()">        
        <input class=cssButton  type=button value=" �� �� " onclick="returnParent()">-->
    </div>
    <!--������,������Ϣ��-->
    <Input type=hidden id="AppntNo" name="AppntNo"><!--Ͷ���˿ͻ�����,��ǰһҳ�洫��-->
    <Input type=hidden id="isReportExist" name="isReportExist"><!--�ж��Ƿ������¼�-->
    <Input type=hidden id="fmtransact" name="fmtransact"><!--����-->
    <Input type=hidden id="State" name="State"><!--ѡ���״̬-->
    <Input type=hidden id="ContNo" name="ContNo"><!--Ͷ���˺�ͬ����-->

</form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
