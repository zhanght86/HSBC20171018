<!--
ProposalApproveEasyScan.jsp
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
  
  prtNo = "<%=request.getParameter("prtNo")%>";
  var tLoadFlag = "<%=request.getParameter("LoadFlag")%>";
  //alert("tLoadFlag"+tLoadFlag);
  window.onbeforeunload = beforeAfterInput;
  window.onunload = afterInput;
  
  function beforeAfterInput() {
    try { 
       if(tLoadFlag != '99') 
       {   
        //�ر�¼������ʱ�����涯��ʾ������Ϣ
        top.fraInterface.goToLock = "RiskCode";
       //top.fraInterface.goToAreaProposal();
      
       for (i=0; i<top.fraInterface.arrScanType.length; i++) {
        eval("if (top.fraInterface.RiskType==top.fraInterface.arrScanType[i][0]) top.fraInterface.goToArea" + top.fraInterface.arrScanType[i][0] + "()");
       }
      top.fraInterface.goToLock = "RiskCode";

     } 
     
     //top.opener.easyQueryClick();
	   top.opener.easyQueryClickSelf();
     
     //alert("parent"+parent.location);
     //alert("top"+top.opener.location);
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

 //   var strSql = "select code1, codename, codealias from ldcode1 where codetype='scaninput'";

    

      var sqlid70="WbProposalInputSql70";
	  var mySql70=new SqlClass();
	  mySql70.setResourceName("app.WbProposalInputSql");
	  mySql70.setSqlId(sqlid70);//ָ��ʹ��SQL��id
	 
	  var strSql = mySql70.getString();

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
		<frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("prtNo")%>&BussNoType=11&BussType=TB&SubType=TB1005&QueryType=3">   
		<frameset name="fraInterfaceSet" cols="45%,18,*" frameborder="yes" border="1" framespacing="0">
		 <frame id="fraInterfacereason" name="fraInterfacereason" scrolling="auto" noresize src="./AbnormityErrAndRecordErr.jsp?prtNo=<%=request.getParameter("prtNo")%>&BussNoType=<%=request.getParameter("BussNoType")%>">
     		<frame id="fraInterfacechange" name="fraInterfacechange" scrolling="auto" noresize src="./ChangeFrame.html">
		 <frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="./WbProposalEasyScanInput.jsp?LoadFlag=<%=request.getParameter("LoadFlag")%>&prtNo=<%=request.getParameter("prtNo")%>&DealType=<%=request.getParameter("DealType")%>&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&ActivityID=<%=request.getParameter("ActivityID")%>&BussNoType=<%=request.getParameter("BussNoType")%>">
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

