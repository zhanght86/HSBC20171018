<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWSub.jsp
//�����ܣ��˱��켣��ѯ
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="UWSub.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="UWSubInit.jsp"%>
  <title>�����˱���¼ </title>
</head>
<body  onload="initForm('<%=tContNo%>', '<%=tPolNo%>');" >
  <form method=post name=fm target="fraSubmit" action= "./UWSubQuery.jsp">
    <%@include file="../common/jsp/InputButton.jsp"%>
    <!-- �����˱���¼���֣��б� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCDuty1);">
    		</td>
    		<td class= titleImg>
    			 �����˱���¼
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCUWSub1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1 >
					<span id="spanUWSubGrid" >
					</span> 
				</td>
			</tr>
		</table>
      <INPUT VALUE="��  ҳ" class="cssButton" TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class="cssButton" TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class="cssButton" TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="β  ҳ" class="cssButton" TYPE=button onclick="getLastPage();"> 
      <INPUT type= "hidden" name= "ProposalNoHide" value= "">				
      <INPUT type= "hidden" name= "PolNo" value= "">
      <INPUT type= "hidden" name= "ContNo" value= "">
	</div>
	<p>
        <INPUT VALUE="��  ��" class="cssButton" TYPE=button onclick="parent.close();"> 					
    </P>
    <!--��ȡ��Ϣ-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>  
</body>
</html>
