<%@include file="/i18n/language.jsp"%>

<%
	//程序名称：LRFeeRateImportSave.jsp
	//程序功能：
	//创建日期：2008-1-8
	//创建人  ：JavaBean程序创建
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page contentType="text/html;charset=GBK"%>

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%
	String FileName = "";
	String filePath = "";
	int count = 0;

	//得到excel文件的保存路径
	String ImportPath = request.getParameter("ImportPath");
	String path = application.getRealPath("").replace('\\', '/') + '/';

	DiskFileUpload fu = new DiskFileUpload();
	// 设置允许用户上传文件大小,单位:字节
	fu.setSizeMax(10000000);
	// maximum size that will be stored in memory?
	// 设置最多只允许在内存中存储的数据,单位:字节
	fu.setSizeThreshold(4096);
	// 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
	fu.setRepositoryPath(path + "temp");
	//开始读取上传信息

	List fileItems = null;
	try {

		fileItems = fu.parseRequest(request);

	} catch (Exception ex) {
		ex.printStackTrace();
	}

	// 依次处理每个上传的文件
	Iterator iter = fileItems.iterator();
	while (iter.hasNext()) {
		FileItem item = (FileItem) iter.next();
		//忽略其他不是文件域的所有表单信息
		if (!item.isFormField()) {
			String name = item.getName();
			long size = item.getSize();
			if ((name == null || name.equals("")) && size == 0)
				continue;
			ImportPath = path + ImportPath;
			FileName = name.substring(name.lastIndexOf("\\") + 1);
			//保存上传的文件到指定的目录

			try {
				item.write(new File(ImportPath + FileName));
				count = 1;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	//输出参数
	CErrors tError = null;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String Result = "";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");
	//得到数据导入文件

	VData tVData = new VData();
	tVData.add(tG);
	TransferData t = new TransferData();
	t.setNameAndValue("ImportPath", ImportPath);
	t.setNameAndValue("FileName", FileName);
	tVData.add(t);
	if (count > 0) {
		BusinessDelegate uiBusinessDelegate = BusinessDelegate
				.getBusinessDelegate();
		if (!uiBusinessDelegate.submitData(tVData, "", "RiDataImpUI")) {
			if (uiBusinessDelegate.getCErrors() != null
					&& uiBusinessDelegate.getCErrors().getErrorCount() > 0) {
				Content = ""+"保存失败，原因是："+""
						+ uiBusinessDelegate.getCErrors()
								.getFirstError();
				FlagStr = "Fail";
			} else {
				Content = ""+"保存失败"+"";
				FlagStr = "Fail";
			}
		}
		if ("".equals(FlagStr)) {
			tError = uiBusinessDelegate.getCErrors();
			if (!tError.needDealError()) {
				Content = ""+"保存成功"+"";
				FlagStr = "Succ";
			} else {
				Content = ""+"保存失败，原因是："+"" + tError.getFirstError();
				FlagStr = "Fail";
			}
		}
	} else {
		Content = ""+"导入文件失败!"+" ";
		FlagStr = "Fail";
	}
	//添加各种预处理
%>
<html>
<script type="text/javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
