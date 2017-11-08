<!--
ProposalApproveEasyScan.jsp
-->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--Root="../../" -->

<html>
<head>
<title>投保扫描件显示 </title>
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
  //图片的队列数组
  var pic_name = new Array();
  var pic_place = 0;
  var b_img	= 0;  //放大图片的次数
  var s_img = 0;	//缩小图片的次数
  
  prtNo = "<%=request.getParameter("prtNo")%>";
  var tLoadFlag = "<%=request.getParameter("LoadFlag")%>";
  //alert("tLoadFlag"+tLoadFlag);
  window.onbeforeunload = beforeAfterInput;
  window.onunload = afterInput;
  
  function beforeAfterInput() {
    try { 
       if(tLoadFlag != '99') 
       {   
        //关闭录入界面的时候，让随动显示险种信息
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
  
  //查询扫描图片的描述
  function queryScanType() {

 //   var strSql = "select code1, codename, codealias from ldcode1 where codetype='scaninput'";

    

      var sqlid70="WbProposalInputSql70";
	  var mySql70=new SqlClass();
	  mySql70.setResourceName("app.WbProposalInputSql");
	  mySql70.setSqlId(sqlid70);//指定使用SQL的id
	 
	  var strSql = mySql70.getString();

    return easyExecSql(strSql);
  }
</script>

</head>
<!--<frameset rows="0,0,0,65,*" frameborder="no" border="1" framespacing="0" cols="*"> -->
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
<!--标题与状态区域-->
	<!--保存客户端变量的区域，该区域必须有-->
	<frame name="VD" src="../common/cvar/CVarData.jsp">
	
	<!--保存客户端变量和WebServer实现交户的区域，该区域必须有-->
	<frame name="EX" src="../common/cvar/CExec.jsp">
	
	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
	<frameset name="fraSet" cols="*" frameborder="yes" border="1" framespacing="0" rows="0,50%,*,0">
		<!--菜单区域-->
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<!--交互区域-->
		<frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("prtNo")%>&BussNoType=11&BussType=TB&SubType=TB1005&QueryType=3">   
		<frameset name="fraInterfaceSet" cols="45%,18,*" frameborder="yes" border="1" framespacing="0">
		 <frame id="fraInterfacereason" name="fraInterfacereason" scrolling="auto" noresize src="./AbnormityErrAndRecordErr.jsp?prtNo=<%=request.getParameter("prtNo")%>&BussNoType=<%=request.getParameter("BussNoType")%>">
     		<frame id="fraInterfacechange" name="fraInterfacechange" scrolling="auto" noresize src="./ChangeFrame.html">
		 <frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="./WbProposalEasyScanInput.jsp?LoadFlag=<%=request.getParameter("LoadFlag")%>&prtNo=<%=request.getParameter("prtNo")%>&DealType=<%=request.getParameter("DealType")%>&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&ActivityID=<%=request.getParameter("ActivityID")%>&BussNoType=<%=request.getParameter("BussNoType")%>">
    </frameset>
    <!--下一步页面区域-->
    <frame id="fraNext" name="fraPicBackup" scrolling="auto" noresize src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>

