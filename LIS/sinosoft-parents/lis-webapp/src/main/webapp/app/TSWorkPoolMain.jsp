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
  loggerDebug("TSWorkPoolMain","ӡˢ��Ϊ��"+tPrtNo);
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

	    var sqlid1="TSWorkPoolMainSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.TSWorkPoolMainSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1); //ָ��ʹ�õ�Sql��id
		//mySql1.addSubPara(tContNo); //ָ������Ĳ���
	     var strSql =mySql1.getString();	
	  
    //var strSql = "select code1, codename, codealias from ldcode1 where codetype='scaninput'";
    
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
		<frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("prtNo")%>&BussNoType=11&BussType=TB&SubType=<%=request.getParameter("SubType")%>&QueryType=3">   
		<frameset name="fraInterfaceSet" cols="45%,18,*" frameborder="yes" border="1" framespacing="0">
		 <frame id="fraInterfacereason" name="fraInterfacereason" scrolling="auto" noresize src="./DSErrorReason.jsp?prtNo=<%=request.getParameter("prtNo")%>">
     		<frame id="fraInterfacechange" name="fraInterfacechange" scrolling="auto" noresize src="./ChangeFrame.html">
		 <%
			if(tSplitPrtNo.equals("8661")){ //������
		%>
		 <frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="./DSMulti-PolCont.jsp?LoadFlag=<%=request.getParameter("LoadFlag")%>&prtNo=<%=request.getParameter("prtNo")%>&DealType=<%=request.getParameter("DealType")%>&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&ActivityID=<%=request.getParameter("ActivityID")%>&InputTime=<%=request.getParameter("InputTime") %>">
    	<%
			}else if(tSplitPrtNo.equals("8651")){ //��ͥ��
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
				loggerDebug("TSWorkPoolMain","����ӡˢ��ʧ�ܣ�δ�ҵ���Ӧ��ӡˢ�������µı������ͣ�");
		%>
		<%
			}
    	%>
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

