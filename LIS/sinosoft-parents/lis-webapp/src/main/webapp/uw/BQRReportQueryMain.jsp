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
<title>����Լ���������ѯ</title>
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
    
       var sqlid10="BQRReportQuerySql10";
	   var mySql10=new SqlClass();
	   mySql10.setResourceName("uw.BQRReportQuerySql");
	   mySql10.setSqlId(sqlid10);//ָ��ʹ��SQL��id
	   var strSql = mySql10.getString();
    
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
		<frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("ContNo")%>&BussNoType=11&BussType=TB&SubType=TB1016">
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="./BQRReportQuery.jsp?ContNo=<%=request.getParameter("ContNo")%>&EdorNo=<%=request.getParameter("EdorNo")%>&PrtSeq=<%=request.getParameter("PrtSeq")%>&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&Flag=<%=request.getParameter("Flag")%>&QueryFlag=<%=request.getParameter("QueryFlag")%>">    <!--��һ��ҳ������-->
    <frame id="fraNext" name="fraPicBackup" scrolling="auto" noresize src="about:blank">  
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>

