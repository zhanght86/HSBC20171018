<%
/***************************************************************
 * <p>ProName：LLClaimQueryCustMain.jsp</p>
 * <p>Title：医疗帐单</p>
 * <p>Description：医疗帐单</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-04-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String tRgtNo = request.getParameter("RgtNo");
	String tCaseNo = request.getParameter("CaseNo");
	String tCustomerNo = request.getParameter("CustomerNo");
	String tCaseSource = request.getParameter("CaseSource");
	String tMode = request.getParameter("Mode");
	String BussNo = request.getParameter("BussNo");
	String BussType = request.getParameter("BussType");
	String SubType = request.getParameter("SubType");
	String tSrc = "./LLClaimMedicalInput.jsp?RgtNo="+tRgtNo+"&CaseNo="+tCaseNo+"&CustomerNo="+tCustomerNo+"&CaseSource="+tCaseSource+"&Mode="+tMode+"&BussNo="+BussNo+"&BussType="+BussType+"&SubType="+SubType;

%>
<html>
<head>
<title>医疗账单录入 </title>
<script language="javascript">
	var pic_name = new Array();
	var pic_place = 0;
	var viewMode = 1;
	//当用户关闭一个页面时触发 onunload 事件。
	window.onunload = afterInput;
	function afterInput() {
		try {
			$("body").remove();//释放内存
		}catch(e) {}
	}
</script>

</head>
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
	<frame name="VD" src="../common/cvar/CVarData.jsp">
	<frame name="EX" src="../common/cvar/CExec.jsp">
	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
	<frameset name="fraSet" cols="*" frameborder="yes" border="1" framespacing="0" rows="0,50%,*,0">
	<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
	<frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../g_easyscan/ScanServo.jsp?BussType=<%=BussType%>&SubType=<%=SubType%>&BussNo=<%=BussNo%>">
	<frame id="fraInterface" name="fraInterface" scrolling="auto" noresize src="<%=tSrc%>">
	<frame id="fraNext" name="fraPicBackup" scrolling="auto" noresize src="about:blank">
	</frameset>
</frameset>

<noframes>
	<body bgcolor="#ffffff" onblur="focusMe();">
	</body>
</noframes>

</html>

