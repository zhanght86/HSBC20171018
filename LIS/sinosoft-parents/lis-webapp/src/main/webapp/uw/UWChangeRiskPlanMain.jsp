<!--Root="../../" -->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuSpecMain.jsp
//程序功能：人工核保条件承保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
    String SubType = "TB10" + request.getParameter("SubType");
%>
<html>
<head>
<title>客户承保结论变更录入 </title>
<script language="javascript">
	var intPageWidth=730;
	var intPageHeight=600;
	window.resizeTo(intPageWidth,intPageHeight);
	window.focus();

	var initWidth = 0;
	  //图片的队列数组
	  var pic_name = new Array();
	  var pic_place = 0;
	  var b_img	= 0;  //放大图片的次数
	  var s_img = 0;	//缩小图片的次数
	
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
		<!--图形区域-->
		<%
if(request.getParameter("BankFlag")!=null&&(request.getParameter("BankFlag").equals("1")||request.getParameter("BankFlag").equals("01"))){
%>		
		<frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("PrtNo")%>&BussNoType=11&BussType=TB&SubType=<%=SubType%>&QueryType=<%=request.getParameter("QueryType")%>">
<%
}
else if(request.getParameter("BankFlag")!=null&&request.getParameter("BankFlag").equals("3")){
%>   	
    <frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("PrtNo")%>&BussNoType=11&BussType=TB&SubType=TB1005&QueryType=<%=request.getParameter("QueryType")%>">
<%
}
else if(request.getParameter("BankFlag")!=null&&request.getParameter("BankFlag").equals("5"))
{
%>   	
<frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("PrtNo")%>&BussNoType=11&BussType=TB&SubType=<%=SubType%>">
<%
}
else {
%>
    <frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("PrtNo")%>&BussNoType=11&BussType=TB&SubType=TB1005">  
<% 
}
%>  
		<!--frame id="fraPic" name="fraPic" scrolling="auto" border="1"  noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("prtNo")%>&BussNoType=11&BussType=TB&QueryType=3&SubType=<%=SubType%>"-->
		<!--交互区域-->
		<frame id="fraInterface" name="fraInterface" noresize scrolling="auto" src="./UWChangeRiskPlanInput.jsp?ContNo=<%=request.getParameter("ContNo")%>&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&PrtNo=<%=request.getParameter("PrtNo")%>&QueryFlag=<%=request.getParameter("QueryFlag")%>&InsuredNo=<%=request.getParameter("InsuredNo")%>&InsuredSumLifeAmnt=<%=request.getParameter("InsuredSumLifeAmnt")%>&InsuredSumHealthAmnt=<%=request.getParameter("InsuredSumHealthAmnt")%>">
  	<!--险种界面区域-->
    <!--frame id="fraInterfaceRisk" name="fraInterfaceRisk" noresize scrolling="auto" src="../app/ProposalInput.jsp"-->
	
    <!--下一步页面区域-->
    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
	
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
