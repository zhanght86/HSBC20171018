<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
	//程序名称：FileUploadSave.jsp
	//程序功能：文件上载页面
	//创建日期：2011-12-12
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.ibrms.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="jxl.*"%>
<%@page import="org.json.*"%>
<%
	//接收信息，并作校验处理。
	//输入参数

	String FlagStr = "";
	String Content = "";

	String targetPath = "";
	String excelSavePath = "select sysvarvalue from LDSYSVAR where sysvar = 'ImportExcelSavePath'";
	ExeSQL exec = new ExeSQL();

	String relativePath = exec.getOneValue(excelSavePath);
	String appPath = application.getRealPath("/").replace('\\', '/');

	targetPath = appPath + relativePath;

	String FileName = "";
	String dealType = "";
	String isUpload = request.getParameter("IsUpload");
	// 上传
	//if((isUpload == null) || !(isUpload.equals("1")))

	try {
		if (!targetPath.endsWith("/")) {
			targetPath += "/";
		}

		DiskFileUpload fu = new DiskFileUpload();// 设置允许用户上传文件大小,单位:字节
		fu.setSizeMax(50000000);
		// maximum size that will be stored in memory?
		// 设置最多只允许在内存中存储的数据,单位:字节
		fu.setSizeThreshold(409600);
		// 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
		fu.setRepositoryPath(targetPath);

		// 开始读取上传信息
		List fileItems = fu.parseRequest(request);

		//  依次处理每个上传的文件
		Iterator iter = fileItems.iterator();

		if (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();

			if (!item.isFormField()) {
				String name = item.getName();
				loggerDebug("FileUploadSave","杨明：：："+name);
				if (name != null && !name.endsWith(".xls")) {
					out.println("{error:'请使用Excel作为模板，并且需要与决策表的列数相同'}");
					return;
				}

				FileName = System.currentTimeMillis()
						+ name.replace('\\', '/');
				FileName = FileName
						.substring(FileName.lastIndexOf("/") + 1);

				//保存上传的文件到指定的目录
				try {
					File tFile = new File(targetPath);
					if (!tFile.exists()) {
						tFile = null;
						tFile = new File(targetPath.replace('/', '\\'));
						tFile.mkdirs();
					}
					loggerDebug("FileUploadSave","FilePath=============="
							+ targetPath + "/" + FileName);

					item.write(new File(targetPath + FileName));

					FileName = targetPath + FileName;
				} catch (Exception e) {
					FlagStr = "Fail";
					Content = "上传文件失败！";
					e.printStackTrace();
				}
			}
		}

	} catch (Exception ex) {
		FlagStr = "Fail";
		Content = "处理失败！";
		ex.printStackTrace();
		out.println("{error:'上载失败'}");
		return;
	}

	if (FlagStr != null && !FlagStr.equals("Fail")) {
		Workbook wb = Workbook.getWorkbook(new File(FileName));
		Sheet st = wb.getSheet(0);

		JSONArray array = new JSONArray();

		int RowNum = st.getRows();
		for (int i = 1; i < RowNum; i++) {// 从第二行开始读取数据。
			Cell[] arrCe = st.getRow(i);
			HashMap map = new HashMap();
			for (int j = 0; j < arrCe.length; j++) {

				if (arrCe[j] != null && !arrCe[j].equals("")) {

					if (j == arrCe.length - 1) {
						map.put("DTRate", arrCe[j].getContents());
						//map.put("RuleId", "RuleId");
					} else {
						map.put("COLUMN" + j, arrCe[j].getContents());
					}
				}

			}
			array.put(map);
		}

		JSONObject obj = new JSONObject();
		//obj.put("success", "Success");
		//obj.put("msg", FileName);
		obj.put("rows", array);
		loggerDebug("FileUploadSave",obj.toString());
		out.println(obj.toString());

	}
%>
