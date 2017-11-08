<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	//Name：CerifyImportTakeBackInput.jsp
	//Function：单证导入缴销
	//Author：mw
	//Date: 2009-08-24
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.certify.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
	//当把程序挪到服务器上时这里的路径操作符要改，要用反斜杠
	String uploadPath = request.getParameter("ImportPath"); //上传路径	
	loggerDebug("CerifyImportTakeBackSave","上传路径:" + uploadPath);

	/*String path = application.getRealPath("").replace('\\', '/');
	if (!path.endsWith("/")) {
		path += "/";
	}*/
	String path = "";

	String saveExcelPath = ""; //文件临时存放路径
	String FileName = ""; //上传的文件名
	String ImportPath = "";

	int count = 0;

	DiskFileUpload fu = new DiskFileUpload();
	// 设置允许用户上传文件大小,单位:字节
	fu.setSizeMax(10000000);

	// 设置最多只允许在内存中存储的数据,单位:字节
	fu.setSizeThreshold(4096);

	//开始读取上传信息
	List fileItems = fu.parseRequest(request);

	// 依次处理每个上传的文件
	Iterator iter = fileItems.iterator();
	loggerDebug("CerifyImportTakeBackSave","iter.hasNext():" + iter.hasNext());

	while (iter.hasNext()) {
		FileItem item = (FileItem) iter.next();
		if (item.getFieldName().compareTo("ImportPath") == 0) {
			ImportPath = item.getString();
		}
		//检查是一个普通的表单域还是File组件,忽略其他不是文件域的所有表单信息
		if (!item.isFormField()) {
			String name = item.getName();
			loggerDebug("CerifyImportTakeBackSave",name);
			long size = item.getSize();
			if ((name == null || name.equals("")) && size == 0)
		continue;

			FileName = item.getName().substring(
			item.getName().lastIndexOf("\\") + 1);

			loggerDebug("CerifyImportTakeBackSave","uploadPath:==>" + uploadPath);
			loggerDebug("CerifyImportTakeBackSave","saveExcelPath:==>" + saveExcelPath);
			loggerDebug("CerifyImportTakeBackSave","FileName:==>" + FileName);
			loggerDebug("CerifyImportTakeBackSave","写文件的路径:" + uploadPath + saveExcelPath
			+ FileName);

			try {
		item.write(new File(uploadPath + saveExcelPath
				+ FileName)); //保存上传的文件到指定的目录
		count = 1;
		loggerDebug("CerifyImportTakeBackSave","count:" + count);
			} catch (Exception e) {
		loggerDebug("CerifyImportTakeBackSave","upload file error ...");
			}
		}
	}

	//输出参数
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";

	TransferData tTransferData = new TransferData();
	boolean res = true;

	//建立批量导入类，
	//CertifyImportTakeBack tCertifyImportTakeBack = null;
	if (count > 0) {
		GlobalInput tG = new GlobalInput();
		tG = (GlobalInput) session.getValue("GI");

		// 准备传输数据 VData
		VData tVData = new VData();
		tTransferData.setNameAndValue("FileName", FileName);
		path = uploadPath + saveExcelPath + FileName;
		tTransferData.setNameAndValue("SaveExcelPath", path);

		tVData.add(tG);
		tVData.add(tTransferData);
		/*try {
			tCertifyImportTakeBack = new CertifyImportTakeBack();
			res = tCertifyImportTakeBack.submitData(tVData, "");
			loggerDebug("CerifyImportTakeBackSave","res=" + res);
		} catch (Exception ex) {
			Content = "导入失败，原因是:" + ex.toString();
			FlagStr = "Fail";
		}*/
		String errMess = "";
		String busiName="CertifyImportTakeBack";
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  if(!tBusinessDelegate.submitData(tVData,"",busiName))
			  {    
			       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			       { 
			    	   tError = tBusinessDelegate.getCErrors();
				   		for (int i = 0; i < tError.getErrorCount(); i++) {
				   		if(i==0)
				   			errMess +=tError.getError(i).errorMessage;
				   		else
				   			errMess += ";"+tError.getError(i).errorMessage;
				   		}
				   		Content = "导入失败，原因是:" + errMess;
				   		FlagStr = "Fail";
				   }
				   else
				   {
						Content = "导入失败";
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
						errMess +=";"+ tError.getError(i).errorMessage;
					}
					Content = "导入完成,请查询清单确认成功失败记录。" + errMess;
					FlagStr = "Success!"; 
			  }
	} else {
		Content = "上传文件失败! ";
		FlagStr = "Fail";
	}

	/*String errMess = "";
	if (res) {
		tError = tCertifyImportTakeBack.mErrors;
		for (int i = 0; i < tError.getErrorCount(); i++) {
		if(i==0)
			errMess += tError.getError(i).errorMessage;
		else
			errMess +=";"+ tError.getError(i).errorMessage;
		}
		Content = "导入完成,请查询清单确认成功失败记录。" + errMess;
		FlagStr = "Success!";
	} else {
		tError = tCertifyImportTakeBack.mErrors;
		for (int i = 0; i < tError.getErrorCount(); i++) {
		if(i==0)
			errMess +=tError.getError(i).errorMessage;
		else
			errMess += ";"+tError.getError(i).errorMessage;
		}
		Content = "导入失败，原因是:" + errMess;
		FlagStr = "Fail";
	}*/
	loggerDebug("CerifyImportTakeBackSave","---FlagStr = " + FlagStr);
	loggerDebug("CerifyImportTakeBackSave","---Content = " + Content);
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmitDiskInput("<%=FlagStr%>","<%=PubFun.changForJavaScript(Content.replaceAll("\\\\","/"))%>");
	</script>
</html>
