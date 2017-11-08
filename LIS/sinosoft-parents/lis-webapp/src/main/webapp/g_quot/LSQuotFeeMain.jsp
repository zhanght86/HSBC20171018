<%
/***************************************************************
 * <p>ProName：LSQuotFeeMain.jsp</p>
 * <p>Title：费用信息</p>
 * <p>Description：费用信息</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-18
 ****************************************************************/
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.utility.*"%>
<html>
<head>
<title>费用信息</title>
<script language="javascript">
	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	window.resizeTo(intPageWidth,intPageHeight);
	window.focus();
</script>
</head>
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
		<%
			String tActivityID = request.getParameter("ActivityID");
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			if ("0800100002".equals(tActivityID) || "0800100003".equals(tActivityID) || "0800100004".equals(tActivityID)) {
				
				ExeSQL tExeSQL = new ExeSQL();
				StringBuffer tStrBuff = new StringBuffer();
				tStrBuff.append(" update lsquotfee a set ");
				if ("0800100002".equals(tActivityID)) {
					tStrBuff.append(" a.segment1='1' ");
				} else if ("0800100003".equals(tActivityID)) {
					tStrBuff.append(" a.segment2='1' ");
				}	if ("0800100004".equals(tActivityID)) {//总核
					tStrBuff.append(" a.segment3='1' ");
				}
				tStrBuff.append(" where 1=1 ");
				tStrBuff.append(" and a.quotno='"+ "?tQuotNo?" +"' and a.quotbatno='"+ "?tQuotBatNo?"+"'");
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(tStrBuff.toString());
				sqlbv1.put("tQuotNo",tQuotNo);
				sqlbv1.put("tQuotBatNo",tQuotBatNo);				
				tExeSQL.execUpdateSQL(sqlbv1);
				
				tStrBuff = new StringBuffer();
				tStrBuff.append(" update lsquototherfee a set ");
				if ("0800100002".equals(tActivityID)) {
					tStrBuff.append(" a.segment1='1' ");
				} else if ("0800100003".equals(tActivityID)) {
					tStrBuff.append(" a.segment2='1' ");
				}	if ("0800100004".equals(tActivityID)) {//总核
					tStrBuff.append(" a.segment3='1' ");
				}
				tStrBuff.append(" where 1=1 ");
				tStrBuff.append(" and a.quotno='"+ "?tQuotNo?" +"' and a.quotbatno='"+ "?tQuotBatNo?"+"'");
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(tStrBuff.toString());
				sqlbv2.put("tQuotNo",tQuotNo);
				sqlbv2.put("tQuotBatNo",tQuotBatNo);		
				tExeSQL.execUpdateSQL(sqlbv2);
			}
		%>
		<!--交互区域-->
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="./LSQuotFeeInput.jsp?QuotType=<%=request.getParameter("QuotType")%>&QuotNo=<%=request.getParameter("QuotNo")%>&QuotBatNo=<%=request.getParameter("QuotBatNo")%>&ActivityID=<%=request.getParameter("ActivityID")%>">
		<!--下一步页面区域-->
		<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
