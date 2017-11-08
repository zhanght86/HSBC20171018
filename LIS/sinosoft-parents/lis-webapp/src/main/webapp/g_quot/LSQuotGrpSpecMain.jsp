<%
/***************************************************************
 * <p>ProName：LSQuotGrpSpecMain.jsp</p>
 * <p>Title：特别约定</p>
 * <p>Description：特别约定</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-04-01
 ****************************************************************/
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.utility.*"%>
<html>
<head>
<title>特别约定</title>
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
		<!--交互区域-->
		<%
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			String tQuotType = request.getParameter("QuotType");
			String tSrc = "";
			
			if ("0800100002".equals(tActivityID)) {
				
				StringBuffer tStrBuff = new StringBuffer();
				tStrBuff.append(" update lsquotgrpspec a set ");
				tStrBuff.append(" a.segment1='1' ");
				tStrBuff.append(" where 1=1 ");
				tStrBuff.append(" and a.quotno='"+ tQuotNo +"' and a.quotbatno="+ tQuotBatNo);
				
				ExeSQL tExeSQL = new ExeSQL();
				tExeSQL.execUpdateSQL(tStrBuff.toString());
			} else if ("0800100003".equals(tActivityID)) {
				
				StringBuffer tStrBuff = new StringBuffer();
				tStrBuff.append(" update lsquotgrpspec a set ");
				tStrBuff.append(" a.segment2='1' ");
				tStrBuff.append(" where 1=1 ");
				tStrBuff.append(" and a.quotno='"+ "?tQuotNo?" +"' and a.quotbatno='"+ "?tQuotBatNo?"+"'");
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(tStrBuff.toString());
				sqlbv1.put("tQuotNo",tQuotNo);
				sqlbv1.put("tQuotBatNo",tQuotBatNo);	
				ExeSQL tExeSQL = new ExeSQL();
				tExeSQL.execUpdateSQL(sqlbv1);
			} else if ("0800100004".equals(tActivityID)) {
				
				StringBuffer tStrBuff = new StringBuffer();
				tStrBuff.append(" update lsquotgrpspec a set ");
				tStrBuff.append(" a.segment3='1' ");
				tStrBuff.append(" where 1=1 ");
				tStrBuff.append(" and a.quotno='"+ "?tQuotNo?" +"' and a.quotbatno='"+ "?tQuotBatNo?"+"'");
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(tStrBuff.toString());
				sqlbv2.put("tQuotNo",tQuotNo);
				sqlbv2.put("tQuotBatNo",tQuotBatNo);	
				ExeSQL tExeSQL = new ExeSQL();
				tExeSQL.execUpdateSQL(sqlbv2);
			}

			tSrc = "./LSQuotGrpSpecInput.jsp";
			tSrc += "?QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
		%>
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="<%=tSrc%>">
		<!--下一步页面区域-->
		<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
