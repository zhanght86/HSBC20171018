
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--Root="../../" -->

<html>
<head>
<title>Ͷ��ɨ�����ʾ </title>
<script language="javascript">
	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	window.resizeTo(intPageWidth,intPageHeight);
	window.moveTo(-1, -1);
	//window.focus();
	
	var initWidth = 0;
  //ͼƬ�Ķ�������
  var pic_name = new Array();
  var pic_place = 0;
  var b_img	= 0;  //�Ŵ�ͼƬ�Ĵ���
  var s_img = 0;	//��СͼƬ�Ĵ���
  
  window.onunload = afterClose;
  
  function afterClose() {
    try {
      //var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=<%=request.getParameter("prtNo")%>&CreatePos=�б�����&PolState=1003&Action=DELETE";
      //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 

      //top.opener.easyQueryClick();
    }
    catch(e) {}
  }
  
  var type = "<%=request.getParameter("Type")%>";
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
	<frameset name="fraSet" cols="*" frameborder="yes" border="1" framespacing="0" rows="0,40%,*,0">
		<!--�˵�����-->
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<!--��������-->
		<frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("prtNo")%>">
		<frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="./ContPlanNextDetailInput.jsp?GrpContNo=<%=request.getParameter("GrpContNo")%>&LoadFlag=<%=request.getParameter("LoadFlag")%>">
    <!--��һ��ҳ������-->
    <frame id="fraNext" name="fraPicBackup" scrolling="auto" noresize src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>

