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
  <SCRIPT src="UWSendPrint.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="UWSendPrintInit.jsp"%>
  <title>����֪ͨ��</title>
</head>
<body  onload="initForm('<%=tProposalNo%>','<%=tOtherNoType%>','<%=tCode%>');" >
  <form method=post id="fm" name=fm target="fraSubmit" action= "./UWSendPrintChk.jsp">
    <!-- �����˱���¼���֣��б� -->
    <table>
    	<tr>
    		<td class= titleImg>
    			 �˹��˱����ݴ洢......
    		</td>
    	</tr>
    </table> 
      <INPUT type= "hidden" id="ProposalNoHide" name= "ProposalNoHide" value= "">
      <INPUT type= "hidden" id="OtherNoType" name= "OtherNoType" value="">
      <input type= "hidden" id="Code" name= "Code" value= "">			
		
    <!--��ȡ��Ϣ-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
