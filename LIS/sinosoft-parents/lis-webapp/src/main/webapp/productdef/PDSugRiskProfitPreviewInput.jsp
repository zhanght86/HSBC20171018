<%@include file="../i18n/language.jsp"%>


<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.productdef.*" %>
<%
 //�������ƣ�PDSugRiskProfitPreview.jsp
 //�����ܣ����θ�������
 //�������ڣ�2009-3-16
 //������  ��
 //���¼�¼��  ������    ��������     ����ԭ��/����
 
%>
<%
String tRiskCode = request.getParameter("riskcode");
String tType = request.getParameter("type");
ProposalUtil tTabHeadPreview = new ProposalUtil();
String tstring = tTabHeadPreview.previewProfitHead(tRiskCode,tType);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
  <script src="../common/javascript/jquery.js"></script>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>

<body>
<%=tstring%>
</body>
</html>

