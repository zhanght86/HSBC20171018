<%
/***************************************************************
 * <p>ProName��LDAttachmentMain.jsp</p>
 * <p>Title����������</p>
 * <p>Description����������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
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
		<%
			String tUploadNode = request.getParameter("UploadNode");
			String tOtherNoType = request.getParameter("OtherNoType");
			String tOtherNo = request.getParameter("OtherNo");
			String tSubOtherNo = request.getParameter("SubOtherNo");
			if ("QUOT".equals(tOtherNoType)) {
			
				if ("0800100002".equals(tUploadNode)) {
					
					StringBuffer tStrBuff = new StringBuffer();
					tStrBuff.append(" update ldattachment a set ");
					tStrBuff.append(" a.segment1='1' ");
					tStrBuff.append(" where 1=1 ");
					tStrBuff.append(" and a.othernotype='"+ "?tOtherNoType?" +"' and a.uploadnode in ('0800100001','0800100002') and a.otherno='"+ "?tOtherNo?" +"' and a.subotherno='"+ "?tSubOtherNo?" +"'");
					SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
					sqlbv1.sql(tStrBuff.toString());
					sqlbv1.put("tOtherNoType",tOtherNoType);
					sqlbv1.put("tOtherNo",tOtherNo);
					sqlbv1.put("tSubOtherNo",tSubOtherNo);
					ExeSQL tExeSQL = new ExeSQL();
					tExeSQL.execUpdateSQL(sqlbv1);
				} else if ("0800100003".equals(tUploadNode)) {
					
					StringBuffer tStrBuff = new StringBuffer();
					tStrBuff.append(" update ldattachment a set ");
					tStrBuff.append(" a.segment2='1' ");
					tStrBuff.append(" where 1=1 ");
					tStrBuff.append(" and a.othernotype='"+ "?tOtherNoType?" +"' and a.uploadnode in ('0800100001','0800100002','0800100003') and a.otherno='"+ "?tOtherNo?" +"' and a.subotherno='"+ "?tSubOtherNo?" +"'");
					SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
					sqlbv2.sql(tStrBuff.toString());
					sqlbv2.put("tOtherNoType",tOtherNoType);
					sqlbv2.put("tOtherNo",tOtherNo);
					sqlbv2.put("tSubOtherNo",tSubOtherNo);
					ExeSQL tExeSQL = new ExeSQL();
					tExeSQL.execUpdateSQL(sqlbv2);
				} else if ("0800100004".equals(tUploadNode)) {
					
					StringBuffer tStrBuff = new StringBuffer();
					tStrBuff.append(" update ldattachment a set ");
					tStrBuff.append(" a.segment3='1' ");
					tStrBuff.append(" where 1=1 ");
					tStrBuff.append(" and a.othernotype='"+ "?tOtherNoType?" +"' and a.uploadnode in ('0800100001','0800100002','0800100003','0800100004') and a.otherno='"+ "?tOtherNo?" +"' and a.subotherno='"+ "?tSubOtherNo?" +"'");
					SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
					sqlbv3.sql(tStrBuff.toString());
					sqlbv3.put("tOtherNoType",tOtherNoType);
					sqlbv3.put("tOtherNo",tOtherNo);
					sqlbv3.put("tSubOtherNo",tSubOtherNo);
					ExeSQL tExeSQL = new ExeSQL();
					tExeSQL.execUpdateSQL(sqlbv3);
				}
			}
		%>
		<!--��������-->
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="./LDAttachmentInput.jsp?OtherNoType=<%=request.getParameter("OtherNoType")%>&OtherNo=<%=request.getParameter("OtherNo")%>&SubOtherNo=<%=request.getParameter("SubOtherNo")%>&UploadNode=<%=request.getParameter("UploadNode")%>">
		<!--��һ��ҳ������-->
		<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
