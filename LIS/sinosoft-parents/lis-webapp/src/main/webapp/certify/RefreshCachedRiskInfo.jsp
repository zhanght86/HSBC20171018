<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.tb.*"%>
<%
	// Kevin 2003-09-28
	// This page is used to call refresh() function of CachedRiskInfo class
	// This will reset buffer which contains information and get new information
	// from database to buffer.
	CachedRiskInfo cri = CachedRiskInfo.getInstance();
	cri.refresh();
	BPOChoose.refresh();
%>
<html> 
<head >
</head>
<body>
	�����ˢ�¡���ť�������������������Ϣ�����´����ݿ��ж�ȡ�����Ϣ��
  <form method="post">
  	<input type="submit" value="ˢ��">
  </form>
</body>
</html>
