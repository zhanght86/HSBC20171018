<%
/***************************************************************
 * <p>ProName��LLClaimQueryCustMain.jsp</p>
 * <p>Title��ϵͳ�û���ѯ</p>
 * <p>Description��ϵͳ�û���ѯ</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : ��Ф��
 * @version  : 8.0
 * @date     : 2014-04-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<%@page import = "com.sinosoft.utility.*"%>
<html>
	<head>
	<title>�ͻ���Ϣ��ѯ</title>
		<script language="javascript">
			function focusMe() {
				window.focus();
			}
		</script>
	</head>
	<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">	
		<!--����ͻ��˱��������򣬸����������-->
		<frame name="VD" src="../common/cvar/CVarData.jsp">
		<!--����ͻ��˱�����WebServerʵ�ֽ��������򣬸����������-->
		<frame name="EX" src="../common/cvar/CExec.jsp">
		<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank">
		<frame name="fraTitle"  scrolling="no" noresize src="about:blank">
		<frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="1" framespacing="0" rows="*">
			<!--�˵�����-->
			<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		  <!--��������-->
		  <%
				StrTool tStrTool = new StrTool();
				String tGrpRgtNo = tStrTool.unicodeToGBK(request.getParameter("GrpRgtNo"));
				String tAppntNo = tStrTool.unicodeToGBK(request.getParameter("AppntNo"));
				String tCustomerName = tStrTool.unicodeToGBK(request.getParameter("CustomerName"));
				String tBirthday = tStrTool.unicodeToGBK(request.getParameter("Birthday"));
				String tEmpNo = tStrTool.unicodeToGBK(request.getParameter("EmpNo"));
				String tIDNo = tStrTool.unicodeToGBK(request.getParameter("IDNo"));				
		  	String tSrc = "./LLClaimQueryInput.jsp?GrpRgtNo="+tGrpRgtNo+"&AppntNo="+tAppntNo+"&CustomerName="+tCustomerName+"&Birthday="+tBirthday+"&EmpNo="+tEmpNo+"&IDNo="+tIDNo;
		  %>
			<frame id="fraInterface" name="fraInterface" scrolling="auto" src="<%=tSrc%>">
			<!--��һ��ҳ������-->
			<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
		</frameset>
	</frameset>
	<noframes>
		<body bgcolor="#ffffff" onblur="focusMe();">
		</body>
	</noframes>
</html>
