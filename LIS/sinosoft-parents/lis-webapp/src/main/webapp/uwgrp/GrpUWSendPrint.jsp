<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWSendPrint.jsp
//�����ܣ��ʹ�ӡ����
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
  <SCRIPT src="GrpUWSendPrint.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GrpUWSendPrintInit.jsp"%>
  <title>����֪ͨ��</title>
</head>
<body  onload="initForm('<%=tProposalNo%>','<%=tOtherNoType%>','<%=tCode%>');" >
  <form method=post name=fm target="fraSubmit" action= "./GrpUWSendPrintChk.jsp">
    <!-- �����˱���¼���֣��б� -->
    <table>
    	<tr>
    		<td class= titleImg>
    			 �˹��˱����ݴ洢......
    		</td>
    	</tr>
    </table> 
      <INPUT type= "hidden" name= "ProposalNoHide" value= "">
      <INPUT type= "hidden" name= "OtherNoType" value="">
      <input type= "hidden" name= "Code" value= "">			
		
    <!--��ȡ��Ϣ-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
