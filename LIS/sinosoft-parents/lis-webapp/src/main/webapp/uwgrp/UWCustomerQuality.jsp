<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�UWCustomerQuality.jsp
//�����ܣ��ͻ�Ʒ�ʹ���
//�������ڣ�2005-06-18 11:10:36
//������  ��ccvip
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<%
  //�����¸���
	String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>

<script>
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	
	var ContNo = "<%=request.getParameter("ContNo")%>";
	var tContNo = "<%=request.getParameter("ContNo")%>";
	//alert("ContNo="+ContNo);
	var PrtNo = "<%=request.getParameter("PrtNo")%>";
//	var OperatePos = "<%=request.getParameter("OperatePos")%>";
</script>

<head >
<title>�ͻ�Ʒ�ʹ��� </title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="UWCustomerQuality.js"></SCRIPT>
  <%@include file="UWCustomerQualityInit.jsp"%>
  
</head>

<body  onload="initForm();" >
  <form method=post name=fm target="fraSubmit" action="./UWCustomerQualitySave.jsp">
    
    <table class= common>
    	<TR  class= common>
    	  
    	  <td class=title>
           ��ѡ��ͻ�
         </td>
                                
         <td class=input>
         <Input class="codeno" name=customer ondblclick="return showCodeList('customer',[this,customername],[0,1],null,tContNo,'ContNo');" onkeyup="return showCodeListKey('customer',[this,customername],[0,1]);" ><Input class="codename" name=customername readonly elementtype=nacessary>
         </td>
         <td></td>
         <td></td>
         <td></td>
         <td></td>
      </TR>
    
    </table>
    
    <table class= common>
    	<hr>
    	<tr class= common>
        	<TD  class= title>
          �ͻ���
        </TD>
        <TD  class= input>
          <Input class=common name=CustomerNo readonly=true>
        </TD>
        <TD  class= title>
          ����
        </TD>
        <TD  class= input>
          <Input class=common name=Name readonly=true>
        </TD>
        <TD  class= title>
          ��������
        </TD>
        <TD  class= input>
          <Input class="coolDatePicker" dateFormat="short" name=BirthDay  >
        </TD>
       
    	</tr>
    	
    	<tr>
         <TD  class= title>
          �Ա�
        </TD>
        <TD  class= input>
          <Input class=common name=Sex readonly=true>
        </TD>
        <TD  class= title>
          ֤������
        </TD>
        <TD  class= input>
          <Input class=common name=IDType readonly=true>
        </TD>
        <TD  class= title>
          ֤������
        </TD>
        <TD  class= input>
          <Input class=common name=IDNumber readonly=true>
        </TD>
    	</tr>
    	</table>
    	
      <table class= common>
      	<hr>
    	  <tr>
    	    <TD  class= title>
          ���������
        </TD>
        <TD  class= input>
            <Input class="codeno" name=BlacklistFlagNo ondblclick="return showCodeList('BlacklistFlag',[this,BlacklistFlagName],[0,1]);" onkeyup="return showCodeListKey('BlacklistFlag',[this,BlacklistFlagName],[0,1]);"><input class=codename name=BlacklistFlagName readonly=true>
          </TD> 
         <td></td>
         <td></td>
         <td></td>
         <td></td>  
    	  </tr>	  
    	
    </table>


  
  <table width="80%" height="20%" class= common>
    <TR  class= common> 
      <TD width="100%" height="15%"  class= title> ��ע��Ϣ </TD>
    </TR>
    <TR  class= common>
      <TD height="85%"  class= title><textarea name="Remark" cols="135" rows="5" class="common" ></textarea></TD>
    </TR>
  </table>
  
  <input type="hidden" name= "PrtNo" value= "">
  <input type="hidden" name= "ContNo" value= "">


  <INPUT CLASS=cssButton VALUE="ȷ  ��" TYPE=button onclick="UpdateClick()">
  <INPUT CLASS=cssButton VALUE="��  ��" TYPE=button onclick="returnParent()">
  
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 

</body>
</html>
