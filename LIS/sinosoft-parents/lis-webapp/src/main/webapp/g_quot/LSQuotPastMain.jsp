<%
/***************************************************************
 * <p>ProName：LSQuotPastMain.jsp</p>
 * <p>Title：既往信息</p>
 * <p>Description：既往信息</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-20
 ****************************************************************/
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.utility.*"%>
<html>
<head>
<title>既往信息</title>
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
			if ("0800100002".equals(tActivityID)) {
				
				StringBuffer tStrBuff = new StringBuffer();
				tStrBuff.append(" update lsquotpast a set ");
				tStrBuff.append(" a.segment1='1' ");
				tStrBuff.append(" where 1=1 ");
				tStrBuff.append(" and a.quotno='"+ "?tQuotNo?" +"' and a.quotbatno='"+ "?tQuotBatNo?"+"'");
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(tStrBuff.toString());
				sqlbv1.put("tQuotNo",tQuotNo);
				sqlbv1.put("tQuotBatNo",tQuotBatNo);	
				ExeSQL tExeSQL = new ExeSQL();
				tExeSQL.execUpdateSQL(sqlbv1);
				
				tStrBuff = new StringBuffer();
				tStrBuff.append(" update lsquotpastdetail a set ");
				tStrBuff.append(" a.segment1='1' ");
				tStrBuff.append(" where 1=1 ");
				tStrBuff.append(" and a.quotno='"+ "?tQuotNo?" +"' and a.quotbatno='"+ "?tQuotBatNo?"+"'");
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(tStrBuff.toString());
				sqlbv2.put("tQuotNo",tQuotNo);
				sqlbv2.put("tQuotBatNo",tQuotBatNo);	
				tExeSQL.execUpdateSQL(sqlbv2);
			} else if ("0800100003".equals(tActivityID)) {
				
				StringBuffer tStrBuff = new StringBuffer();
				tStrBuff.append(" update lsquotpast a set ");
				tStrBuff.append(" a.segment2='1' ");
				tStrBuff.append(" where 1=1 ");
				tStrBuff.append(" and a.quotno='"+ "?tQuotNo?" +"' and a.quotbatno='"+ "?tQuotBatNo?"+"'");
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				sqlbv3.sql(tStrBuff.toString());
				sqlbv3.put("tQuotNo",tQuotNo);
				sqlbv3.put("tQuotBatNo",tQuotBatNo);	
				ExeSQL tExeSQL = new ExeSQL();
				tExeSQL.execUpdateSQL(sqlbv3);
				
				tStrBuff = new StringBuffer();
				tStrBuff.append(" update lsquotpastdetail a set ");
				tStrBuff.append(" a.segment2='1' ");
				tStrBuff.append(" where 1=1 ");
				tStrBuff.append(" and a.quotno='"+ "?tQuotNo?" +"' and a.quotbatno='"+ "?tQuotBatNo?"+"'");
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql(tStrBuff.toString());
				sqlbv4.put("tQuotNo",tQuotNo);
				sqlbv4.put("tQuotBatNo",tQuotBatNo);	
				tExeSQL.execUpdateSQL(sqlbv4);
			} else if ("0800100004".equals(tActivityID)) {
				
				StringBuffer tStrBuff = new StringBuffer();
				tStrBuff.append(" update lsquotpast a set ");
				tStrBuff.append(" a.segment3='1' ");
				tStrBuff.append(" where 1=1 ");
				tStrBuff.append(" and a.quotno='"+ "?tQuotNo?" +"' and a.quotbatno='"+ "?tQuotBatNo?"+"'");
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				sqlbv5.sql(tStrBuff.toString());
				sqlbv5.put("tQuotNo",tQuotNo);
				sqlbv5.put("tQuotBatNo",tQuotBatNo);	
				ExeSQL tExeSQL = new ExeSQL();
				tExeSQL.execUpdateSQL(sqlbv5);
				
				tStrBuff = new StringBuffer();
				tStrBuff.append(" update lsquotpastdetail a set ");
				tStrBuff.append(" a.segment3='1' ");
				tStrBuff.append(" where 1=1 ");
				tStrBuff.append(" and a.quotno='"+ "?tQuotNo?" +"' and a.quotbatno='"+ "?tQuotBatNo?"+"'");
				SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
				sqlbv6.sql(tStrBuff.toString());
				sqlbv6.put("tQuotNo",tQuotNo);
				sqlbv6.put("tQuotBatNo",tQuotBatNo);	
				tExeSQL.execUpdateSQL(sqlbv6);
			}
		%>
		<!--交互区域-->
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="./LSQuotPastInput.jsp?QuotType=<%=request.getParameter("QuotType")%>&QuotNo=<%=request.getParameter("QuotNo")%>&QuotBatNo=<%=request.getParameter("QuotBatNo")%>&ActivityID=<%=request.getParameter("ActivityID")%>&Flag=<%=request.getParameter("Flag")%>">
		<!--下一步页面区域-->
		<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
