<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	//Name��CerifyImportDestroyInput.jsp
	//Function����֤��������
	//Author��mw
	//Date: 2009-08-24
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.certify.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
	//���ѳ���Ų����������ʱ�����·��������Ҫ�ģ�Ҫ�÷�б��
	String uploadPath = request.getParameter("ImportPath"); //�ϴ�·��	
	loggerDebug("CerifyImportDestroySave","�ϴ�·��:" + uploadPath);

	/*String path = application.getRealPath("").replace('\\', '/');
	if (!path.endsWith("/")) {
		path += "/";
	}*/
	String path="";

	String saveExcelPath = ""; //�ļ���ʱ���·��
	String FileName = ""; //�ϴ����ļ���
	String ImportPath = "";

	int count = 0;

	DiskFileUpload fu = new DiskFileUpload();
	// ���������û��ϴ��ļ���С,��λ:�ֽ�
	fu.setSizeMax(10000000);

	// �������ֻ�������ڴ��д洢������,��λ:�ֽ�
	fu.setSizeThreshold(4096);

	//��ʼ��ȡ�ϴ���Ϣ
	List fileItems = fu.parseRequest(request);

	// ���δ���ÿ���ϴ����ļ�
	Iterator iter = fileItems.iterator();
	loggerDebug("CerifyImportDestroySave","iter.hasNext():" + iter.hasNext());

	while (iter.hasNext()) {
		FileItem item = (FileItem) iter.next();
		if (item.getFieldName().compareTo("ImportPath") == 0) {
			ImportPath = item.getString();
		}
		//�����һ����ͨ�ı�����File���,�������������ļ�������б���Ϣ
		if (!item.isFormField()) {
			String name = item.getName();
			loggerDebug("CerifyImportDestroySave",name);
			long size = item.getSize();
			if ((name == null || name.equals("")) && size == 0)
		continue;

			FileName = item.getName().substring(
			item.getName().lastIndexOf("\\") + 1);

			loggerDebug("CerifyImportDestroySave","uploadPath:==>" + uploadPath);
			loggerDebug("CerifyImportDestroySave","saveExcelPath:==>" + saveExcelPath);
			loggerDebug("CerifyImportDestroySave","FileName:==>" + FileName);
			loggerDebug("CerifyImportDestroySave","д�ļ���·��:" + uploadPath + saveExcelPath
			+ FileName);

			try {
		item.write(new File(uploadPath + saveExcelPath
				+ FileName)); //�����ϴ����ļ���ָ����Ŀ¼
		count = 1;
		loggerDebug("CerifyImportDestroySave","count:" + count);
			} catch (Exception e) {
		loggerDebug("CerifyImportDestroySave","upload file error ...");
			}
		}
	}

	//�������
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";

	TransferData tTransferData = new TransferData();
	boolean res = true;

	//�������������࣬
	//CertifyImportDestroy tCertifyImportDestroy = null;
	if (count > 0) {
		GlobalInput tG = new GlobalInput();
		tG = (GlobalInput) session.getValue("GI");

		// ׼���������� VData
		VData tVData = new VData();
		tTransferData.setNameAndValue("FileName", FileName);
		path = uploadPath + saveExcelPath + FileName;
		tTransferData.setNameAndValue("SaveExcelPath", path);

		tVData.add(tG);
		tVData.add(tTransferData);
		try {
			/*tCertifyImportDestroy = new CertifyImportDestroy();
			res = tCertifyImportDestroy.submitData(tVData, "");
			loggerDebug("CerifyImportDestroySave","res=" + res);*/
			String errMess = "";
			String busiName="CertifyImportDestroy";
				BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
				  if(!tBusinessDelegate.submitData(tVData,"",busiName))
				  {    
				       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
				       { 
				    	   tError = tBusinessDelegate.getCErrors();
					   		for (int i = 0; i < tError.getErrorCount(); i++) {
					   		if(i==0)
					   			errMess += tError.getError(i).errorMessage;
					   		else
					   			errMess +=";"+ tError.getError(i).errorMessage;
					   		}
					   		Content = "����ʧ�ܣ�ԭ����:" + errMess;
							FlagStr = "Fail";
					   }
					   else
					   {
							Content = "����ʧ��";
							FlagStr = "Fail";				
					   }
				  }
				  else
				  {
					   tError = tBusinessDelegate.getCErrors();
						for (int i = 0; i < tError.getErrorCount(); i++) {
						if(i==0)
							errMess += tError.getError(i).errorMessage;
						else
							errMess += ";"+tError.getError(i).errorMessage;
						}
						Content = "�������,���ѯ�嵥ȷ�ϳɹ�ʧ�ܼ�¼��" + errMess;
						FlagStr = "Success!";  
				  }
		} catch (Exception ex) {
			Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
			FlagStr = "Fail";
		}
	} else {
		Content = "�ϴ��ļ�ʧ��! ";
		FlagStr = "Fail";
	}

	/*String errMess = "";
	if (res) {
		tError = tCertifyImportDestroy.mErrors;
		for (int i = 0; i < tError.getErrorCount(); i++) {
		if(i==0)
			errMess += tError.getError(i).errorMessage;
		else
			errMess += ";"+tError.getError(i).errorMessage;
		}
		Content = "�������,���ѯ�嵥ȷ�ϳɹ�ʧ�ܼ�¼��" + errMess;
		FlagStr = "Success!";
	} else {
		tError = tCertifyImportDestroy.mErrors;
		for (int i = 0; i < tError.getErrorCount(); i++) {
		if(i==0)
			errMess += tError.getError(i).errorMessage;
		else
			errMess +=";"+ tError.getError(i).errorMessage;
		}
		Content = "����ʧ�ܣ�ԭ����:" + errMess;
		FlagStr = "Fail";
	}*/
	loggerDebug("CerifyImportDestroySave","---FlagStr = " + FlagStr);
	loggerDebug("CerifyImportDestroySave","---Content = " + Content);
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmitDiskInput("<%=FlagStr%>","<%=PubFun.changForJavaScript(Content.replaceAll("\\\\","/"))%>");
	</script>
</html>
