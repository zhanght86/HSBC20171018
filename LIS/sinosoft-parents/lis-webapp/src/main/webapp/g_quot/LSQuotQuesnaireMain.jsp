<%
/***************************************************************
 * <p>ProName：LSQuotQuesnaireMain.jsp</p>
 * <p>Title：问卷调查</p>
 * <p>Description：问卷调查</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-24
 ****************************************************************/
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.utility.SQLwithBindVariables"%>
<html>
<head>
<title>问卷调查</title>
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
				tStrBuff.append(" update ldattachment a set ");
				tStrBuff.append(" a.segment1='1' ");
				tStrBuff.append(" where 1=1 ");
				tStrBuff.append(" and a.othernotype='QUOT' and a.attachtype='00' and a.uploadnode in ('0800100001','0800100002') and a.otherno='?tQuotNo?' and a.subotherno='?tQuotBatNo?'");
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tStrBuff.toString());
				sqlbv.put("tQuotNo", tQuotNo);
				sqlbv.put("tQuotBatNo", tQuotBatNo);
				ExeSQL tExeSQL = new ExeSQL();
				tExeSQL.execUpdateSQL(sqlbv);
			} else if ("0800100003".equals(tActivityID)) {
				
				StringBuffer tStrBuff = new StringBuffer();
				tStrBuff.append(" update ldattachment a set ");
				tStrBuff.append(" a.segment2='1' ");
				tStrBuff.append(" where 1=1 ");
				tStrBuff.append(" and a.othernotype='QUOT' and a.attachtype='00' and a.uploadnode in ('0800100001','0800100002','0800100003') and a.otherno='?tQuotNo?' and a.subotherno='?tQuotBatNo?'");
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tStrBuff.toString());
				sqlbv.put("tQuotNo", tQuotNo);
				sqlbv.put("tQuotBatNo", tQuotBatNo);
				ExeSQL tExeSQL = new ExeSQL();
				tExeSQL.execUpdateSQL(sqlbv);
			} else if ("0800100004".equals(tActivityID)) {
				
				StringBuffer tStrBuff = new StringBuffer();
				tStrBuff.append(" update ldattachment a set ");
				tStrBuff.append(" a.segment3='1' ");
				tStrBuff.append(" where 1=1 ");
				tStrBuff.append(" and a.othernotype='QUOT' and a.attachtype='00' and a.uploadnode in ('0800100001','0800100002','0800100003','0800100004') and a.otherno='?tQuotNo?' and a.subotherno='?tQuotBatNo?'");
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tStrBuff.toString());
				sqlbv.put("tQuotNo", tQuotNo);
				sqlbv.put("tQuotBatNo", tQuotBatNo);
				ExeSQL tExeSQL = new ExeSQL();
				tExeSQL.execUpdateSQL(sqlbv);
			}
		%>
		<!--交互区域-->
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="./LSQuotQuesnaireInput.jsp?QuotType=<%=request.getParameter("QuotType")%>&QuotNo=<%=request.getParameter("QuotNo")%>&QuotBatNo=<%=request.getParameter("QuotBatNo")%>&ActivityID=<%=request.getParameter("ActivityID")%>">
		<!--下一步页面区域-->
		<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
