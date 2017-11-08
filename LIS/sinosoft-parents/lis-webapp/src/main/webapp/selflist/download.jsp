<%@ page contentType="text/html;charset=gb2312"
import="com.jspsmart.upload.*" import="com.sinosoft.utility.*" %><%
SmartUpload su = new SmartUpload();
su.initialize(pageContext);
su.setContentDisposition(null);
su.downloadFile(StrTool.unicodeToGBK(request.getParameter("file")));
%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
