<%
/***************************************************************
 * <p>ProName：LDInsuredStateSave.jsp</p>
 * <p>Title：被保险人身故维护</p>
 * <p>Description：被保险人身故维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-07-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@page import="com.sinosoft.lis.schema.LDAttachmentSchema"%>
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
		
			String tAttachPath = "";
			String tSysUploadPath = "";
			String tFileName = "";
			String tExtenName = "";
			
			String tCurrentDate = PubFun.getCurrentDate();
			String tCurrentTime = PubFun.getCurrentTime();
			tCurrentDate = tCurrentDate.replace("-","");
			tCurrentTime = tCurrentTime.replace(":","");
			
			LDAttachmentSchema tLDAttachmentSchema = null;
			//从前台获取信息
			String tOperate = request.getParameter("Operate");
						
			 if("UPLOAD".equals(tOperate)){
				ExeSQL tExeSQL = new ExeSQL();
				String tSql = "select sysvarvalue from ldsysvar where sysvar='UploadPath'";
				tSysUploadPath = tExeSQL.getOneValue(tSql);
				
				if (tSysUploadPath==null || "".equals(tSysUploadPath)) {
					tContent = "未配置附件上传路径！";
					tFlagStr = "Fail";
				} else {
					
					if (!tSysUploadPath.endsWith("/")) {
						tSysUploadPath += "/";
					}
					
					File tFile = new File(tSysUploadPath);
					if (!tFile.exists()) {
						tFile.mkdirs();
					}
					File tTempFile = new File(tSysUploadPath+"/temp/");
					if (!tTempFile.exists()) {
						tTempFile.mkdirs();
					}
					
					tAttachPath = tSysUploadPath;
					
					DiskFileUpload tDiskFileUpload = new DiskFileUpload();
					//设置允许用户上传文件大小,单位:字节
					tDiskFileUpload.setSizeMax(10240000);
					//设置最多只允许在内存中存储的数据,单位:字节
					tDiskFileUpload.setSizeThreshold(4096);
					//设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
					tDiskFileUpload.setRepositoryPath(tSysUploadPath+"/temp/");
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
							
							try {
								tFileItem.write(new File(tSysUploadPath+tCurrentDate+tCurrentTime+"."+tExtenName));
								tFlagStr = "Succ";
								tContent = "上传文件成功";
							} catch(Exception e) {
								tFlagStr = "Fail";
								tContent = "上传文件失败";
							}
						}
					}
				}
			}
			if ("Succ".equals(tFlagStr)) {
				
				tLDAttachmentSchema = new LDAttachmentSchema();
				
				tLDAttachmentSchema.setOtherNoType("CUS");
				tLDAttachmentSchema.setSubOtherNo("CUS");
				tLDAttachmentSchema.setAttachType("CU");
				tLDAttachmentSchema.setAttachName(tFileName);
				tLDAttachmentSchema.setAttachSysName(tCurrentDate+tCurrentTime+"."+tExtenName);
				tLDAttachmentSchema.setUploadNode("");
				tLDAttachmentSchema.setAttachPath(tSysUploadPath+tCurrentDate+tCurrentTime+"."+tExtenName);
				tLDAttachmentSchema.setExtenName(tExtenName);

			}
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLDAttachmentSchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LDInsuredStateUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				if ("UPLOAD".equals(tOperate)) {
					String kResultStr = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 1);
					String tResultStr = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 2);
					String nResultStr = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 3);
					if(tResultStr.equals("0")){
						tContent = "处理成功！";
					}else{
						tContent = "共导入"+kResultStr+"人,导入失败"+tResultStr+"人,导入成功"+nResultStr+"人,查看错误信息,请点击【错误信息导出按钮】！";
					}
				}else{
					tContent = "处理成功！";
				}
				tFlagStr = "Succ";
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
