<!--
ProposalApproveEasyScan.jsp
-->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--Root="../../" -->
<%
  String SubType = request.getParameter("SubType");
  String tPrtNo=request.getParameter("prtNo");
  String tSplitPrtNo="";
  loggerDebug("TSWorkPoolMain","印刷号为："+tPrtNo);
  tSplitPrtNo=tPrtNo.substring(0,4);
  if(!tSplitPrtNo.equals("8661")&&!tSplitPrtNo.equals("8651")&&!tSplitPrtNo.equals("8621")
		  &&!tSplitPrtNo.equals("8635")&&!tSplitPrtNo.equals("8616")&&!tSplitPrtNo.equals("8615")
		  &&!tSplitPrtNo.equals("8611")&&!tSplitPrtNo.equals("8625")&&!tSplitPrtNo.equals("3110"))
  {
%>
 <script language="javascript">
			//prtNoError();
			alert("解析印刷号失败，未找到对应的印刷号类型下的保单类型！");
			window.opener=null;
			window.close();
			//tFlag = true;
		</script>  
<%
   }
%>
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

	    var sqlid1="TSWorkPoolMainSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.TSWorkPoolMainSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1); //指定使用的Sql的id
		//mySql1.addSubPara(tContNo); //指定传入的参数
	     var strSql =mySql1.getString();	
	  
    //var strSql = "select code1, codename, codealias from ldcode1 where codetype='scaninput'";
    
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
		<frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("prtNo")%>&BussNoType=11&BussType=TB&SubType=<%=request.getParameter("SubType")%>&QueryType=3">   
		<frameset name="fraInterfaceSet" cols="45%,18,*" frameborder="yes" border="1" framespacing="0">
		 <frame id="fraInterfacereason" name="fraInterfacereason" scrolling="auto" noresize src="./DSErrorReason.jsp?prtNo=<%=request.getParameter("prtNo")%>">
     		<frame id="fraInterfacechange" name="fraInterfacechange" scrolling="auto" noresize src="./ChangeFrame.html">
		 <%
			if(tSplitPrtNo.equals("8661")){ //多主险
		%>
		 <frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="./DSMulti-PolCont.jsp?LoadFlag=<%=request.getParameter("LoadFlag")%>&prtNo=<%=request.getParameter("prtNo")%>&DealType=<%=request.getParameter("DealType")%>&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&ActivityID=<%=request.getParameter("ActivityID")%>&InputTime=<%=request.getParameter("InputTime") %>">
    	<%
			}else if(tSplitPrtNo.equals("8651")){ //家庭单
    	%>
    	<frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="./DSHomeCont.jsp?LoadFlag=<%=request.getParameter("LoadFlag")%>&prtNo=<%=request.getParameter("prtNo")%>&DealType=<%=request.getParameter("DealType")%>&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&ActivityID=<%=request.getParameter("ActivityID")%>&InputTime=<%=request.getParameter("InputTime") %>">
    	<%
			}if(tSplitPrtNo.equals("8621")||tSplitPrtNo.equals("8611")){
		%>
		<frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="./DSAgentCont.jsp?LoadFlag=<%=request.getParameter("LoadFlag")%>&prtNo=<%=request.getParameter("prtNo")%>&DealType=<%=request.getParameter("DealType")%>&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&ActivityID=<%=request.getParameter("ActivityID")%>&InputTime=<%=request.getParameter("InputTime") %>">
		<%	
			}else if(tSplitPrtNo.equals("8635")||tSplitPrtNo.equals("8615")||tSplitPrtNo.equals("8625")||tSplitPrtNo.equals("3110")){
		%>
		<frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="./DSBankCont.jsp?LoadFlag=<%=request.getParameter("LoadFlag")%>&prtNo=<%=request.getParameter("prtNo")%>&DealType=<%=request.getParameter("DealType")%>&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&ActivityID=<%=request.getParameter("ActivityID")%>&InputTime=<%=request.getParameter("InputTime") %>">
		<%
			}else if(tSplitPrtNo.equals("8616")){
		%>
			<frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="./DSEasyCont.jsp?ScanFlag=<%=request.getParameter("ScanFlag")%>&prtNo=<%=tPrtNo%>&ManageCom=<%=request.getParameter("ManageCom")%>&scantype=<%=request.getParameter("scantype")%>&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&ActivityID=<%=request.getParameter("ActivityID")%>&NoType=<%=request.getParameter("NoType")%>&InputTime=<%=request.getParameter("InputTime")%>">
		<%
			}
			else{
				loggerDebug("TSWorkPoolMain","解析印刷号失败，未找到对应的印刷号类型下的保单类型！");
		%>
		<%
			}
    	%>
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

