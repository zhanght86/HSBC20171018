<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--Root="../../" -->
<%
  String SubType = request.getParameter("SubType");
  loggerDebug("DSContInputScanMain","SubType:"+SubType);
  String tPrtNo=request.getParameter("prtNo");
  String tSplitPrtNo="";
  loggerDebug("DSContInputScanMain","ӡˢ��Ϊ��"+tPrtNo);
  tSplitPrtNo=tPrtNo.substring(0,4);
  if(!tSplitPrtNo.equals("8661")&&!tSplitPrtNo.equals("8651")&&!tSplitPrtNo.equals("8621")
		  &&!tSplitPrtNo.equals("8635")&&!tSplitPrtNo.equals("8616")&&!tSplitPrtNo.equals("8615")
		  &&!tSplitPrtNo.equals("8611")&&!tSplitPrtNo.equals("8625")&&!tSplitPrtNo.equals("3110"))
  {
%>
 <script language="javascript">
			//prtNoError();
			alert("����ӡˢ��ʧ�ܣ�δ�ҵ���Ӧ��ӡˢ�������µı������ͣ�");
			window.opener=null;
			window.close();
			//tFlag = true;
		</script>  
<%
   }
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
		<frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("prtNo")%>&BussType=TB&QueryType=3">
		<%

			if(tSplitPrtNo.equals("8661")){ //������
		%>
		<frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="./DSMulti-PolCont.jsp?ScanFlag=<%=request.getParameter("ScanFlag")%>&prtNo=<%=tPrtNo%>&ManageCom=<%=request.getParameter("ManageCom")%>&scantype=<%=request.getParameter("scantype")%>&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&ActivityID=<%=request.getParameter("ActivityID")%>&NoType=<%=request.getParameter("NoType")%>&InputTime=<%=request.getParameter("InputTime")%>">
    	<%
			}else if(tSplitPrtNo.equals("8651")){ //��ͥ��
    	%>
    	<frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="./DSHomeCont.jsp?ScanFlag=<%=request.getParameter("ScanFlag")%>&prtNo=<%=tPrtNo%>&ManageCom=<%=request.getParameter("ManageCom")%>&scantype=<%=request.getParameter("scantype")%>&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&ActivityID=<%=request.getParameter("ActivityID")%>&NoType=<%=request.getParameter("NoType")%>&InputTime=<%=request.getParameter("InputTime")%>">
    	<%
			}else if(tSplitPrtNo.equals("8621")||tSplitPrtNo.equals("8611")){ //�ݶ�Ϊ�н�Ͷ����
		%>
		<frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="./DSAgentCont.jsp?ScanFlag=<%=request.getParameter("ScanFlag")%>&prtNo=<%=tPrtNo%>&ManageCom=<%=request.getParameter("ManageCom")%>&scantype=<%=request.getParameter("scantype")%>&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&ActivityID=<%=request.getParameter("ActivityID")%>&NoType=<%=request.getParameter("NoType")%>&InputTime=<%=request.getParameter("InputTime")%>">
		<%		
			}else if(tSplitPrtNo.equals("8635")||tSplitPrtNo.equals("8615")||tSplitPrtNo.equals("8625")||tSplitPrtNo.equals("3110")){
		%>
		<frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="./DSBankCont.jsp?ScanFlag=<%=request.getParameter("ScanFlag")%>&prtNo=<%=tPrtNo%>&ManageCom=<%=request.getParameter("ManageCom")%>&scantype=<%=request.getParameter("scantype")%>&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&ActivityID=<%=request.getParameter("ActivityID")%>&NoType=<%=request.getParameter("NoType")%>&InputTime=<%=request.getParameter("InputTime")%>">
		<%
			}else if(tSplitPrtNo.equals("8616")){
		%>
			<frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="./DSEasyCont.jsp?ScanFlag=<%=request.getParameter("ScanFlag")%>&prtNo=<%=tPrtNo%>&ManageCom=<%=request.getParameter("ManageCom")%>&scantype=<%=request.getParameter("scantype")%>&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&ActivityID=<%=request.getParameter("ActivityID")%>&NoType=<%=request.getParameter("NoType")%>&InputTime=<%=request.getParameter("InputTime")%>">
		<%
			}
			else{
				loggerDebug("DSContInputScanMain","����ӡˢ��ʧ�ܣ�δ�ҵ���Ӧ��ӡˢ�������µı������ͣ�");
		%>
		
		<%
			}
    	%>
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

