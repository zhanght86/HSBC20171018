<%
/***************************************************************
 * <p>ProName：LSQuotCustInfoImpSave</p>
 * <p>Title：费率测算</p>
 * <p>Description 信息导入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-10-11
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			
			String tOperate = request.getParameter("Operate");
			
			String tQuotType = request.getParameter("QuotType");
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			
			String tSysUploadPath = "";
			String tFileName = "";
			String tExtenName = "";
			String tFilePath = "";
			
			String tCurrentDate = PubFun.getCurrentDate();
			String tCurrentTime = PubFun.getCurrentTime();
			tCurrentDate = tCurrentDate.replace("-","");
			tCurrentTime = tCurrentTime.replace(":","");
			
			if ("UPLOAD".equals(tOperate)) {
			
				ExeSQL tExeSQL = new ExeSQL();
				String tSql = "select sysvarvalue from ldsysvar where sysvar='UploadPath'";
				tSysUploadPath = tExeSQL.getOneValue(tSql);
				
				if (tSysUploadPath==null || "".equals(tSysUploadPath)) {
					tContent = "未配置附件上传路径！";
					tFlagStr = "Fail";
				} else {
					
					String tDate = PubFun.getCurrentDate();
					String tYear = tDate.substring(0,4);
					String tMonth = tDate.substring(5,7);
					String tDay = tDate.substring(8);
			
					if (!tSysUploadPath.endsWith("/")) {
						tSysUploadPath += "/";
					}
					File tFile = new File(tSysUploadPath);
					if (!tFile.exists()) {
						tFile.mkdirs();
					}
					File tTempFile = new File(tSysUploadPath+"quot/impcustinfo/"+tYear+"/"+tMonth+"/"+tDay+"/");
					if (!tTempFile.exists()) {
						tTempFile.mkdirs();
					}
					DiskFileUpload tDiskFileUpload = new DiskFileUpload();
					//设置允许用户上传文件大小,单位:字节
					tDiskFileUpload.setSizeMax(10240000);
					//设置最多只允许在内存中存储的数据,单位:字节
					tDiskFileUpload.setSizeThreshold(4096);
					//设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
					tDiskFileUpload.setRepositoryPath(tSysUploadPath+"quot/impcustinfo/"+tYear+"/"+tMonth+"/"+tDay+"/");
					tDiskFileUpload.setHeaderEncoding("GBK");
					//开始读取上传信息
					List tList = tDiskFileUpload.parseRequest(request);
					Iterator tIterator = tList.iterator();
					while (tIterator.hasNext()) {
					
						FileItem tFileItem = (FileItem) tIterator.next();
						//忽略其他不是文件域的所有表单信息
						if (!tFileItem.isFormField()) {
							String tItemName = tFileItem.getName();
							long tItemSize = tFileItem.getSize();
							if ((tItemName==null || "".equals(tItemName)) && tItemSize==0) {
								continue;
							}
							
							tFileName = tItemName.replace('\\','/');
							tFileName = tFileName.substring(tFileName.lastIndexOf("/") + 1);
							tExtenName = tFileName.substring(tFileName.lastIndexOf(".")+1);
							tFilePath = tSysUploadPath+"quot/impcustinfo/"+tYear+"/"+tMonth+"/"+tDay+"/"+tQuotNo+"_"+tQuotBatNo+"_"+tCurrentTime+".xls";
							try {
								
								tFileItem.write(new File(tFilePath));
								tFlagStr = "Succ";
								tContent = "导入成功!";
							} catch(Exception e) {
								tFlagStr = "Fail";
								tContent = "导入失败!";
							}
						}
					}
				}
			}
			
			if ("Succ".equals(tFlagStr)) {
				
				TransferData tTransferData = new TransferData();
				
				tTransferData.setNameAndValue("FilePath",tFilePath);
				tTransferData.setNameAndValue("QuotNo",tQuotNo);
				tTransferData.setNameAndValue("QuotBatNo",tQuotBatNo);
				tTransferData.setNameAndValue("QuotType",tQuotType);
				
				VData tVData = new VData();
				tVData.add(tGI);
				tVData.add(tTransferData);
				
				BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
				if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotCustInfoImpUI")) {
				
					tContent = "导入失败!"+ tBusinessDelegate.getCErrors().getFirstError();
					tFlagStr = "Fail";
				} 
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "处理异常，请联系系统运维人员！";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>
