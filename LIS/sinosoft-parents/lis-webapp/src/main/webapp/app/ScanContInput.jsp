<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�ScanContInput.jsp
//�����ܣ���������Լɨ�������¼��
//�������ڣ�2004-12-22 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<%
  //�����¸���	
	String tContNo = "";
	String tFlag = request.getParameter("type");
  	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>	
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var type = "<%=tFlag%>";
	var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="ScanContInput.js"></SCRIPT>
  <!-- modified by lzf -->
  <script src="../common/javascript/jquery.workpool.js"></script>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ScanContInit.jsp"%>
   <script src="../common/javascript/MultiCom.js"></script>
  <title>ɨ��¼��</title>
</head>
<body  onload="initForm();initElementtype();">
  <form action="./ScanContInputSave.jsp" method=post name=fm id="fm" target="fraSubmit">
   <!-- modified by lzf -->
    <div id="ScanPool"></div>            
	<!-- MulLine����ScanPublicPoolGrid -->	
		<input type="hidden" name=MissionID value=""> 
		<input type="hidden" name=SubMissionID value="">
		<input type="hidden" name=PrtNo value="">
		<input type="hidden" name=ContType value="">
		<input type="hidden" name=ManageCom value="">
		<input type="hidden" name=InputDate value="">
		<input type="hidden" name=ActivityID value="">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
