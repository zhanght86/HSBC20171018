<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--Root="../../" -->
<%
  String SubType = request.getParameter("SubType");
  loggerDebug("BQUWConfirmMain","SubType:"+SubType);
  String tContNo=request.getParameter("ContNo");
  String tEdorNo=request.getParameter("EdorNo");
  String tEdorType=request.getParameter("EdorType");
  String tSplitPrtNo="";
  loggerDebug("BQUWConfirmMain","��ͬ��Ϊ��"+tContNo+" EdorNo=="+tEdorNo+" tEdorType=="+tEdorType);
%>
<html>

<head>
<title>Ͷ��ɨ�����ʾ </title>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<script language="javascript">
	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	window.resizeTo(intPageWidth,intPageHeight);
	window.moveTo(-1, -1);
	//window.focus();
	//alert('test');
	
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
    
     var sqlid4="BQUWConfirmSql4";
	 var mySql4=new SqlClass();
	 mySql4.setResourceName("bq.BQUWConfirmSql");
	 mySql4.setSqlId(sqlid4);//ָ��ʹ��SQL��id
	 var strSql = mySql4.getString();
    
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
		<frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("prtNo")%>&BussNoType=11&BussType=TB&SubType=<%=SubType%>">
		<frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="./BQUWConfirmInput.jsp?teFlag=<%=request.getParameter("teFlag")%>&ScanFlag=<%=request.getParameter("ScanFlag")%>&EdorAcceptNo=<%=request.getParameter("EdorAcceptNo")%>&ContNo=<%=tContNo%>&ManageCom=<%=request.getParameter("ManageCom")%>&scantype=<%=request.getParameter("scantype")%>&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&ActivityID=<%=request.getParameter("ActivityID")%>&EdorNo=<%=tEdorNo %>&EdorType=<%=tEdorType %>">
    <!--��һ��ҳ������-->
    <frame id="fraNext" name="fraPicBackup" scrolling="auto" noresize src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
<script language="javascript">
 
</script>

