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
 String SubType = "";
 String mSelPolNo = "";
 String tPageFlag =request.getParameter("PageFlag");
 //mSelPolNo = request.getParameter("SelPolNo");
%>
<html>
<head>
<title>客户承保结论变更录入 </title>
<script language="javascript">
	var intPageWidth=730;
	var intPageHeight=600;
</script>
</head>
<!--<frameset rows="0,0,0,65,*" frameborder="no" border="1" framespacing="0" cols="*"> -->
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
<!--标题与状态区域-->

	<!--保存客户端变量的区域，该区域必须有-->
	<frame name="VD" src="../common/cvar/CVarData.jsp">
	
	<!--保存客户端变量和WebServer实现交户的区域，该区域必须有-->
	<frame name="EX" src="../common/cvar/CExec.jsp">

	<frame name="fraSubmit1"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
	<frameset name="fraSet" cols="*" frameborder="yes" border="1" framespacing="0" rows="*">
		<%if(tPageFlag.equals("2")){ %>
		<frame id="fraInterface" name="fraInterface"  src="../app/ProposalInput.jsp?LoadFlag=25&ContType=1&scantype=noscan&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&ActivityID=null&NoType=null&hh=1&checktype=1&ProposalContNo=<%=request.getParameter("ContNo")%>&ScanFlag=0&InsuredChkFlag=true&AppntChkFlag=false&SelPolNo=<%=request.getParameter("SelPolNo")%>&NewChangeRiskPlanFlag=Y">
		<%}else{ %>
				<frame id="fraInterface" name="fraInterface"  src="../uw/UWModifyFloatRate.jsp?LoadFlag=25&ContType=1&scantype=scan&MissionID=<%=request.getParameter("MissionID")%>&SubMissionID=<%=request.getParameter("SubMissionID")%>&ActivityID=null&NoType=null&hh=1&checktype=1&ContNo=<%=request.getParameter("ContNo")%>&ScanFlag=0&InsuredChkFlag=true&AppntChkFlag=false&InsuredNo=<%=request.getParameter("InsuredNo")%>&NewChangeRiskPlanFlag=Y">
		<%} %>
	</frameset>
	
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
