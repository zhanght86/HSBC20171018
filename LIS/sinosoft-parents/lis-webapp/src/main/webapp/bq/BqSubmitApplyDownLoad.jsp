<%@page import="com.sinosoft.lis.db.LDSysVarDB"%>
<%@page import="java.io.File"%>
<%@page contentType="text/html;charset=gb2312"
	import="com.jspsmart.upload.*"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	response.reset();

response.setContentType("text/html; charset=GB2312");

	// �½�һ��SmartUpload����     
	SmartUpload su = new SmartUpload();
	// ��ʼ��     
	su.initialize(pageContext);
	//�趨contentDispositionΪnull�Խ�ֹ������Զ����ļ���     
	//��֤������Ӻ��������ļ��������趨�������ص��ļ���չ��Ϊ     
	//docʱ����������Զ���word��������չ��Ϊpdfʱ��     
	//���������acrobat�򿪡�     
	su.setContentDisposition(null);
	LDSysVarDB tLDSysVarDB = new LDSysVarDB();
	tLDSysVarDB.setSysVar("BqSubPath");
	if (!tLDSysVarDB.getInfo()) {
		out.print("<center>�ϴ��ļ�·��������!<Input  value=' �� �� ' type=button onclick='javascript:history.back();'></center>");
		return;
	} else {
		String path = tLDSysVarDB.getSysVarValue();
		String tFileName = request.getParameter("file");
		File tFile = new File(path + tFileName);
		if (tFile.exists()) {
			//   �����ļ�     
			su.downloadFile(path + tFileName);
			out.clear();
			out = pageContext.pushBody();
		}else{
			out.print("<center>û���ҵ�����!<br><br><Input  value=' �� �� ' type=button onclick='javascript:history.back();'></center>");
			return;
		}
	}
%>
