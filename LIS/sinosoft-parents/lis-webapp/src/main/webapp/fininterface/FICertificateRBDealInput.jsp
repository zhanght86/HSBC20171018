 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<html>

<%
     GlobalInput tGI = new GlobalInput();
     tGI = (GlobalInput)session.getValue("GI");
%>
<script>	
        var BusinessNo = <%=request.getParameter("BusinessNo")%>;
        var AppNo = <%=request.getParameter("AppNo")%>;
        var CertificateId = <%=request.getParameter("CertificateId")%>;	
         
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>    
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="FICertificateRBDealInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="FICertificateRBDealInit.jsp"%>
  
</head>
<body  onload="initForm();initElementtype();" >
  <form  method=post name=fm target="fraSubmit">
          

        <Input class= common  name=BusinessNo type= hidden  readonly=true>
        <Input class= common  name=AppNo type= hidden readonly=true>
        <Input class= common  name=CertificateId type= hidden readonly=true>

        <table>   		  		
          <tr class=common>
	    <td class=titleImg>��ҵ��������ƾ֤��Ϣ</td>
          </tr>
          <tr class=common>
	    <td><INPUT VALUE="��������ȡҵ������" class = cssButton TYPE=button onclick="ImportData1();"> </td>
	    <td><INPUT VALUE="����������ƾ֤����" class = cssButton TYPE=button onclick="ImportData2();"> </td>
          </tr>          
          
        </table>
  	<Div  id= "divLCGrp1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  				<span id="spanRBDealResultGrid" >
  				</span> 
  			</td>
  		</tr>
    	</table>
            <INPUT VALUE="��  ҳ" class = cssButton90 TYPE=button onclick="getFirstPage();"> 
            <INPUT VALUE="��һҳ" class = cssButton91 TYPE=button onclick="getPreviousPage();"> 					
            <INPUT VALUE="��һҳ" class = cssButton92 TYPE=button onclick="getNextPage();"> 
            <INPUT VALUE="β  ҳ" class = cssButton93 TYPE=button onclick="getLastPage();"> 					
  	</div>  	
  	<hr class="line">        
  	<INPUT VALUE="������ݲɼ�" class = cssButton TYPE=button onclick="ReDistill();"> 
  	<INPUT VALUE="���ƾ֤��ȡ" class = cssButton TYPE=button onclick="ReCertificate();"> <td class=titleImg>�����ƾ֤��ȡ���ɵ����ݿ����������ɵ�ƾ֤�����в鿴�򵼳���</td>

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
