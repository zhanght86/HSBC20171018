<!--Root="../../" -->
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<%
//�������ƣ�UWManuHealthMain.jsp
//�����ܣ������˹��˱��������¼��
//�������ڣ�2005-01-09 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<head>
<title>��ȫ�������¼��</title>
<script language="javascript">
	var intPageWidth=730;
	var intPageHeight=600;
	window.resizeTo(intPageWidth,intPageHeight);
	//window.focus();	
	
		var initWidth = 0;
  //ͼƬ�Ķ�������
  var pic_name = new Array();
  var pic_place = 0;
  var b_img	= 0;  //�Ŵ�ͼƬ�Ĵ���
  var s_img = 0;	//��СͼƬ�Ĵ���
  
  window.onunload = afterInput;
  
  function afterInput() {
    try {
      top.opener.afterInput();
    }
    catch(e) {}
  }
  
  var mainPolNo = "";
  var mainRisk = "";
  //��ѯɨ��ͼƬ������
  function queryScanType() {
  //  var strSql = "select code1, codename, codealias from ldcode1 where codetype='scaninput'";
    

   var sqlid1="BQManuHealthQSql1";
   var mySql1=new SqlClass();
   mySql1.setResourceName("uw.BQManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
   mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
   var strSql = mySql1.getString();
    
    return easyExecSql(strSql);
  }  
</script>
</head>
<!--<frameset rows="0,0,0,65,*" frameborder="no" border="1" framespacing="0" cols="*"> -->
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
<!--������״̬����-->
	<!--����ͻ��˱��������򣬸����������-->
	<frame name="VD" src="../common/cvar/CVarData.jsp">
	
	<!--����ͻ��˱�����WebServerʵ�ֽ��������򣬸����������-->
	<frame name="EX" src="../common/cvar/CExec.jsp">
	
	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
	<frameset name="fraSet" cols="*" frameborder="yes" border="1" framespacing="0" rows="0,50%,*,0">
		<!--�˵�����-->
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<!--��������-->
		<frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("ContNo")%>&BussNoType=11&BussType=TB&SubType=TB1015">
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="./BQManuHealthQ.jsp?ActivityID=<%=request.getParameter("ActivityID")%>&ContNo=<%=request.getParameter("ContNo")%>&EdorNo=<%=request.getParameter("EdorNo")%>&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&PrtNo=<%=request.getParameter("PrtNo")%>&PrtSeq=<%=request.getParameter("PrtSeq")%>&Flag=<%=request.getParameter("Flag")%>&CustomerNo=<%=request.getParameter("CustomerNo")%>">
    <!--��һ��ҳ������-->
    <frame id="fraNext" name="fraPicBackup" scrolling="auto" noresize src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>

