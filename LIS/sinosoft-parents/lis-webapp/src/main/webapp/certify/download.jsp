<%@ page contentType="text/html;charset=gb2312" 
import="com.jspsmart.upload.*" %><% 
SmartUpload su = new SmartUpload(); 
su.initialize(pageContext); 
su.setContentDisposition(null); 
su.downloadFile(request.getParameter("file")); 
%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
