<%
/***************************************************************
 * <p>ProName：download.jsp</p>
 * <p>Title：下载文件页面</p>
 * <p>Description：下载文件页面</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : sinosoft
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/
%>
<%@ page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.utility.*"%>

<%
	try {
		
		//String fileName = java.net.URLDecoder.decode(request.getParameter("FileName"), "UTF-8");
		//fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		//String filePath = java.net.URLDecoder.decode(request.getParameter("FilePath"), "UTF-8");
		
		String fileName = request.getParameter("FileName");
		String filePath = request.getParameter("FilePath");
		
		//获取文件名字符集，response.setHeader()文件名字符集必须是ISO8859-1
	
		fileName = new String(fileName.getBytes(), "ISO8859-1");
		response.setContentType("APPLICATION/OCTET-STREAM;");
		response.setHeader("Content-Disposition", "attachment;filename="+fileName);
		ServletOutputStream sos = response.getOutputStream();
		out.clear();
		out = pageContext.pushBody();
		FileInputStream fis = new FileInputStream(filePath);
		
		int j;
		while ((j=fis.read()) != -1) {    
			sos.write(j);   
		}
		sos.close();     
		fis.close();
	} catch(Exception e) {
		System.out.println("File download Exception ...");
	}
%>
