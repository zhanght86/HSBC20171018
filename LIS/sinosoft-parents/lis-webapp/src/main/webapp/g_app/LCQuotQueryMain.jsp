<%
/***************************************************************
 * <p>ProName��LCQuotQueryMain.jsp</p>
 * <p>Title�����۵���ѯ</p>
 * <p>Description�����۵���ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-04-24
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<script language="javascript">
function focusMe() {
	window.focus();
}
</script>
<head>
<%
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
	String tOfferListNo = request.getParameter("OfferListNo");
	String tQuotType = request.getParameter("QuotType");
	String tReturnFlag = request.getParameter("ReturnFlag");
	String tSrc = "../quot/LSQuotETSeeInput.jsp";
	if ("00".equals(tQuotType)) {
		tSrc = "../g_quot/LSQuotETSeeInput.jsp";
	} else if ("01".equals(tQuotType)) {
		tSrc = "../g_quot/LSQuotProjSeeInput.jsp";
	}
	tSrc += "?QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&OfferListNo="+ tOfferListNo+"&QuotType="+tQuotType+"&ReturnFlag="+tReturnFlag;
	
%>
</head>
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
	
	<!--����ͻ��˱��������򣬸����������-->
	<frame name="VD" src="../common/cvar/CVarData.html">
	<!--����ͻ��˱�����WebServerʵ�ֽ��������򣬸����������-->
	<frame name="EX" src="../common/cvar/CExec.jsp">
	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
		
	<frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="1" framespacing="0" rows="*">
		<!--�˵�����-->
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<!--��������-->
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="<%=tSrc%>">
		<!--��һ��ҳ������-->
		<frame name="fraNext" src="about:blank" scrolling="auto">
	</frameset>
	
</frameset>

<noframes>
	<body bgcolor="#ffffff" onblur="focusMe();">
	</body>
</noframes>

</html>
