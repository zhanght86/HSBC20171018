<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
	//�����¸���
	String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";

	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
%>
<script>
	var grpPolNo = "<%=tGrpPolNo%>";      //���˵��Ĳ�ѯ����.
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var comcode="<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
<title>�������ѯ</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="MSRReportReply.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="MSRReportReplyInit.jsp"%>
<script src="../common/javascript/MultiCom.js"></script>
<script src="../common/javascript/jquery.workpool.js"></script>
</head>
<body onload="initForm();">
	<form method=post name=fm id=fm target="fraSubmit" action="">
		<div id="WorkPool"></div>
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
