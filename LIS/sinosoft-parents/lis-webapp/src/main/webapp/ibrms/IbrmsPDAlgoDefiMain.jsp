<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
 //程序名称：PDAlgoDefiInput.jsp
 //程序功能：算法定义界面
 //创建日期：2009-3-17
 //创建人  ：
 //更新记录：  更新人    更新日期     更新原因/内容
  
%>
<head>
<title>算法定义界面</title>
<script language="javascript">
	
	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	window.moveTo(-1, -1);
	window.resizeTo(intPageWidth,intPageHeight);
	//window.focus();
	window.onunload = afterInput;
	
	var tRuleInputName = "11";
	var tRuleCalName = "";

function afterInput() {
	//alert('close');
	try {
	//endProcess();
	var tExeCommand = "";
	var tValue = window.frames["fraInterface"].document.getElementById("RuleName").value;
	var tInputName = window.frames["fraInterface"].document.getElementById("RuleInputName").value;
	//alert(tValue);
	
	if(tInputName!=null&&tInputName!="")
	{
		try{
		 tExeCommand = "top.opener.document.getElementById('"+tInputName+"').value='"+tValue+"'";
		}
		catch(e)
		{
		}
	// tExeCommand = "top.opener.document.getElementById('CalCode').value='"+tValue+"'";
	//alert("1:"+tExeCommand);
	eval(tExeCommand);
	}
	top.opener.afterInput();
	
}
	catch(e) {}
	window.focus();
} 
</script>

</head>
<!--<frameset rows="0,0,0,65,*" frameborder="no" border="1" framespacing="0" cols="*"> -->
<frameset id=fraMain name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
<!--标题与状态区域-->
 <!--保存客户端变量的区域，该区域必须有-->
 <frame id=VD name="VD" src="../common/cvar/CVarData.jsp">
 
 <!--保存客户端变量和WebServer实现交户的区域，该区域必须有-->
 <frame id=EX name="EX" src="../common/cvar/CExec.jsp">
 
 <frame id=fraSubmit name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
 <frame id=fraTitle name="fraTitle"  scrolling="no" noresize src="about:blank" >
 <frameset id=fraSet name="fraSet" cols="0%,*,0%" frameborder="no" border="1" framespacing="0" rows="*">
  <!--菜单区域-->
  <frame id=fraMenu name="fraMenu" scrolling="yes" noresize src="about:blank">
  <!--交互区域-->
  <frame id="fraInterface" name="fraInterface" scrolling="auto" src="./IBRMSMakeRule.jsp?riskcode=<%=request.getParameter("riskcode")%>&RuleName=<%=request.getParameter("RuleName")%>&Creator=<%=request.getParameter("Creator")%>&RuleStartDate=<%=request.getParameter("RuleStartDate")%>&Business=<%=request.getParameter("Business")%>&State=<%=request.getParameter("State")%>&flag=<%=request.getParameter("flag")%>&RuleEndDate=<%=request.getParameter("RuleEndDate")%>&RuleModul=<%=request.getParameter("RuleModul")%>&RuleInputName=<%=request.getParameter("RuleInputName")%>&RuleType=<%=request.getParameter("RuleType")%>">
     <!--下一步页面区域-->
     <frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
 </frameset>
</frameset>
<noframes>
 <body bgcolor="#ffffff">
 </body>
</noframes>
</html>

