<%

//Name:StandPayInfoMain.jsp
//Function������__Ԥ�����__FRAME
//UPDATE:
//DESC:���ҳ���������FRAME������:  CaseAffixList.jsp
%>
<html>
  <%@page contentType="text/html;charset=GBK" %>
<head>
<title>����Ԥ�����¼��</title>
<script language="javascript">
  var intPageWidth=850;
  var intPageHeight=600;
  window.resizeTo(intPageWidth,intPageHeight);
  window.focus();

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
  <frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="1" framespacing="0" rows="*">
    <!--�˵�����-->
    <frame name="fraMenu" scrolling="yes" noresize src="about:blank">
    <!--��������-->

    <frame id="fraInterface" name="fraInterface" scrolling="auto" 
    	   src="./StandPayInfoList.jsp?CaseNo=<%=request.getParameter("CaseNo")%>&customerName=<%=request.getParameter("customerName")%>&customerNo=<%=request.getParameter("customerNo")%>&GrpContNo=<%=request.getParameter("GrpContNo")%>&RiskCode=<%=request.getParameter("RiskCode")%>">
      <!--��һ��ҳ������-->
      <frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
  </frameset>
</frameset>
<noframes>
  <body bgcolor="#ffffff">
  </body>
</noframes>
</html>
