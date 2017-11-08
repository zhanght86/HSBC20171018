<%

//Name:StandPayInfoMain.jsp
//Function：立案__预估金额__FRAME
//UPDATE:
//DESC:这个页面里面的主FRAME内容是:  CaseAffixList.jsp
%>
<html>
  <%@page contentType="text/html;charset=GBK" %>
<head>
<title>理赔预估金额录入</title>
<script language="javascript">
  var intPageWidth=850;
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

    <frame id="fraInterface" name="fraInterface" scrolling="auto" 
    	   src="./StandPayInfoList.jsp?CaseNo=<%=request.getParameter("CaseNo")%>&customerName=<%=request.getParameter("customerName")%>&customerNo=<%=request.getParameter("customerNo")%>&GrpContNo=<%=request.getParameter("GrpContNo")%>&RiskCode=<%=request.getParameter("RiskCode")%>">
      <!--下一步页面区域-->
      <frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
  </frameset>
</frameset>
<noframes>
  <body bgcolor="#ffffff">
  </body>
</noframes>
</html>
