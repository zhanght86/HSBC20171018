<%
/***************************************************************
 * <p>ProName��LSQuotCoinsuranceMain.jsp</p>
 * <p>Title����������</p>
 * <p>Description����������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-06-03
 ****************************************************************/
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.utility.*"%>
<html>
<head>
<title>��������</title>
<script language="javascript">
	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	window.resizeTo(intPageWidth,intPageHeight);
	window.focus();
</script>
</head>
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
		<%
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			String tSrc = "";
			
			if ("0100100002".equals(tActivityID)) {
				
				StringBuffer tStrBuff = new StringBuffer();
				tStrBuff.append(" update lsquotcoinsurance a set ");
				tStrBuff.append(" a.segment1='1' ");
				tStrBuff.append(" where 1=1 ");
				tStrBuff.append(" and a.quotno='?tQuotNo?' and a.quotbatno='?tQuotBatNo?' ");
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tStrBuff.toString());
				sqlbv.put("tQuotNo", tQuotNo);
				sqlbv.put("tQuotBatNo", tQuotBatNo);
				ExeSQL tExeSQL = new ExeSQL();
				tExeSQL.execUpdateSQL(sqlbv);
			} else if ("0100100003".equals(tActivityID)) {
				
				StringBuffer tStrBuff = new StringBuffer();
				tStrBuff.append(" update lsquotcoinsurance a set ");
				tStrBuff.append(" a.segment2='1' ");
				tStrBuff.append(" where 1=1 ");
				tStrBuff.append(" and a.quotno='?tQuotNo?' and a.quotbatno='?tQuotBatNo?' ");
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tStrBuff.toString());
				sqlbv.put("tQuotNo", tQuotNo);
				sqlbv.put("tQuotBatNo", tQuotBatNo);
				ExeSQL tExeSQL = new ExeSQL();
				tExeSQL.execUpdateSQL(sqlbv);
			} else if ("0100100004".equals(tActivityID)) {
				
				StringBuffer tStrBuff = new StringBuffer();
				tStrBuff.append(" update lsquotcoinsurance a set ");
				tStrBuff.append(" a.segment3='1' ");
				tStrBuff.append(" where 1=1 ");
				tStrBuff.append(" and a.quotno='?tQuotNo?' and a.quotbatno='?tQuotBatNo?'");
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tStrBuff.toString());
				sqlbv.put("tQuotNo", tQuotNo);
				sqlbv.put("tQuotBatNo", tQuotBatNo);
				ExeSQL tExeSQL = new ExeSQL();
				tExeSQL.execUpdateSQL(sqlbv);
			}
			
			tSrc = "./LSQuotCoinsuranceInput.jsp";
			tSrc += "?QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
		%>
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="<%=tSrc%>">
		<!--��һ��ҳ������-->
		<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
