<%@page contentType="text/html;charset=GBK" %>
<!--Root="../../" -->

<html>
<head>
<title>��ȫɨ�����ʾ </title>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<script language="javascript">
	
	//var intPageWidth=screen.availWidth;
	//var intPageHeight=screeWidth,intPageHeight);
	//window.en.availHeight;
	//window.resizeTo(intPagmoveTo(-1, -1);
	//window.focus();
	
	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	window.resizeTo(intPageWidth,intPageHeight);
	window.moveTo(-1, -1);
	
	var initWidth = 0;
  //ͼƬ�Ķ�������
  var pic_name = new Array();
  var pic_place = 0;
  var b_img	= 0;  //�Ŵ�ͼƬ�Ĵ���
  var s_img = 0;	//��СͼƬ�Ĵ���
  
  var viewMode = 1;//1��ͼ���ϣ�2��������3������Mode
  
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
    //var strSql = "select code1, codename, codealias from ldcode1 where codetype='scaninput'";
    var strSql="";
    var sqlid="ContInputScanMainSql1";
		var mySql=new SqlClass();
		mySql.setResourceName("app.ScanContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql.setSqlId(sqlid); //ָ��ʹ�õ�Sql��id
    mySql.addSubPara("1");
		strSql=mySql.getString();
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
		<frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("EdorAcceptNo")%>&BussNoType=31&BussType=BQ&EdorAcceptNo=<%=request.getParameter("EdorAcceptNo")%>&QueryType=3">
		<frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="./PEdorAppInput.jsp?ScanFlag=<%=request.getParameter("ScanFlag")%>&EdorAcceptNo=<%=request.getParameter("EdorAcceptNo")%>&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&ActivityID=0000000001&LoadFlag=<%=request.getParameter("LoadFlag")%>">
    <!--��һ��ҳ������-->
    <frame id="fraNext" name="fraPicBackup" scrolling="auto" noresize src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
