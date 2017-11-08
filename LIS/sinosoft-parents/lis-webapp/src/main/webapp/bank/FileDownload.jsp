<%@ page contentType="text/html; charset=GBK" language="java"
	import="java.io.*" errorPage=""%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="java.net.*"%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
		<title>文件下载</title>
	</head>

	<body>
		<%
			response.setContentType("application/x-download");
			String fileName = request.getParameter("fileName");
			String filePath = "/bank/SendToBankFile/files/" + fileName;
			//String fileType = request.getParameter("filetype");
			String fileDisplayName = URLEncoder.encode("download.z", "GBK");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ fileDisplayName);
			try {
				RequestDispatcher dis = application
						.getRequestDispatcher(filePath);
				if (null != dis) {
					dis.forward(request, response);
				}
				response.flushBuffer();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				//response.reset();
        		out.clear();
        		out=pageContext.pushBody();
			}
		%></body>
</html>

<%@include file="../common/jsp/Log4jUI.jsp"%>  
