<!--
FineProposalApproveEasyScan.jsp
-->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--Root="../../" -->

<html>
<head>
<title>Ͷ��ɨ�����ʾ </title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">

<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>

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
  
  TempFeeNo = "<%=request.getParameter("TempFeeNo")%>";
  var tLoadFlag = "<%=request.getParameter("LoadFlag")%>";
  //alert("tLoadFlag"+tLoadFlag);
  window.onbeforeunload = beforeAfterInput;
  window.onunload = afterInput;
  
  function beforeAfterInput() {
    try {      
     top.opener.unlock(TempFeeNo);
     //alert("parent"+parent.location);
     //alert("top"+TempFeeNo);
    }
    catch(e) {}
  }
  
  function afterInput() {
    try { 
      top.opener.afterInput(); 
    } 
    catch(e) {}
  }
  
  var mainPolNo = "";
  
  //��ѯɨ��ͼƬ������
  function queryScanType() {
    var strSql = "select code1, codename, codealias from ldcode1 where codetype='scaninput'";
    
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
		<frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("TempFeeNo")%>&BussType=OF&QueryType=3">
		<frameset name="fraInterfaceSet" cols="45%,18,*" frameborder="yes" border="1" framespacing="0">
		 <frame id="fraInterfacereason" name="fraInterfacereason" scrolling="auto" noresize src="./AbnormityErrAndRecordErr.jsp?TempFeeNo=<%=request.getParameter("TempFeeNo")%>">
     		<frame id="fraInterfacechange" name="fraInterfacechange" scrolling="auto" noresize src="./ChangeFrame.html">
		 <frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="./FineWbProposalEasyScanInput.jsp?LoadFlag=<%=request.getParameter("LoadFlag")%>&TempFeeNo=<%=request.getParameter("TempFeeNo")%>&DealType=<%=request.getParameter("DealType")%>">
    </frameset>
    <!--��һ��ҳ������-->
    <frame id="fraNext" name="fraPicBackup" scrolling="auto" noresize src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>

