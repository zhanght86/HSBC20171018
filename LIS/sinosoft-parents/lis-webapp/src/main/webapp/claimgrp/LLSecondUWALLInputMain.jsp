<!--Root="../../" -->
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuInputMain.jsp
//程序功能：新契约人工核保
//创建日期：2005-01-09 11:10:36
//创建人  ：HYQ
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<head>
<title>新契约人工核保</title>
<script language="javascript">
	var intPageWidth=730;
	var intPageHeight=600;
	window.resizeTo(intPageWidth,intPageHeight);
	window.focus();	
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
	<frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="1" framespacing="0" rows="*">
		<!--菜单区域-->
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<!--交互区域-->
		<%
		  String szSrc = "./LLSecondManuUWInput.jsp";	
		  szSrc += "?CaseNo=" + request.getParameter("CaseNo");	//赔案号
		  szSrc += "&BatNo=" + request.getParameter("BatNo"); //批次号			    
		  szSrc += "&InsuredNo=" + request.getParameter("InsuredNo");   //出险人客户号    
		  szSrc += "&ClaimRelFlag=" + request.getParameter("ClaimRelFlag");   //赔案相关标志
		  szSrc += "&Missionid=" + request.getParameter("Missionid");  //任务ID 
		  szSrc += "&Submissionid=" + request.getParameter("Submissionid");   //子任务ID 
		  szSrc += "&Activityid=" + request.getParameter("Activityid");   //节点号 		  		  		   	    		  
		  System.out.println(szSrc);
		 %>
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="<%= szSrc %>">
    	<!--下一步页面区域-->
    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
