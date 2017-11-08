<%@page import="com.sinosoft.lis.db.LDSysVarDB"%>
<%@page import="java.io.File"%>
<%@page contentType="text/html;charset=gb2312"
	import="com.jspsmart.upload.*"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	response.reset();

response.setContentType("text/html; charset=GB2312");

	// 新建一个SmartUpload对象     
	SmartUpload su = new SmartUpload();
	// 初始化     
	su.initialize(pageContext);
	//设定contentDisposition为null以禁止浏览器自动打开文件，     
	//保证点击链接后是下载文件。若不设定，则下载的文件扩展名为     
	//doc时，浏览器将自动用word打开它。扩展名为pdf时，     
	//浏览器将用acrobat打开。     
	su.setContentDisposition(null);
	LDSysVarDB tLDSysVarDB = new LDSysVarDB();
	tLDSysVarDB.setSysVar("BqSubPath");
	if (!tLDSysVarDB.getInfo()) {
		out.print("<center>上传文件路径不存在!<Input  value=' 返 回 ' type=button onclick='javascript:history.back();'></center>");
		return;
	} else {
		String path = tLDSysVarDB.getSysVarValue();
		String tFileName = request.getParameter("file");
		File tFile = new File(path + tFileName);
		if (tFile.exists()) {
			//   下载文件     
			su.downloadFile(path + tFileName);
			out.clear();
			out = pageContext.pushBody();
		}else{
			out.print("<center>没有找到附件!<br><br><Input  value=' 返 回 ' type=button onclick='javascript:history.back();'></center>");
			return;
		}
	}
%>
