
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�BookAddSave.jsp
//�����ܣ�
//�������ڣ�2008-09-02 15:12:33
//������  ��dxy���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->


<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">

</head>
<script language="javascript">
   
		parent.fraInterface.afterSubmit('<%=request.getParameter("flag")%>','<%=request.getParameter("Content")%>');

</script>
</html>

