<%
/***************************************************************
 * <p>ProName��LSQuotPersonUWUploadSave.jsp</p>
 * <p>Title��ѯ�۸��˺˱���������</p>
 * <p>Description��ѯ�۸��˺˱���������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-21
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
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
			
			String tOperate = request.getParameter("Operate");
			
			String tOtherNoType = request.getParameter("OtherNoType");
			String tOtherNo = request.getParameter("OtherNo");
			String tSubOtherNo = request.getParameter("SubOtherNo");
			String tUploadNode = request.getParameter("UploadNode");
			String tAttachType = request.getParameter("AttachType");
			String tPerUWSerialNo = request.getParameter("PerUWSerialNo");
			
			String tAttachPath = "";
			String tSysUploadPath = "";
			String tFileName = "";
			String tExtenName = "";
			
			String tCurrentDate = PubFun.getCurrentDate();
			String tCurrentTime = PubFun.getCurrentTime();
			tCurrentDate = tCurrentDate.replace("-","");
			tCurrentTime = tCurrentTime.replace(":","");
			
			if ("PerUWUPLOAD".equals(tOperate)) {
			
				ExeSQL tExeSQL = new ExeSQL();
				String tSql = "select sysvarvalue from ldsysvar where sysvar='UploadPath'";
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(tSql);
				tSysUploadPath = tExeSQL.getOneValue(sqlbv);
				
				if (tSysUploadPath==null || "".equals(tSysUploadPath)) {
					tContent = "δ���ø����ϴ�·����";
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
					//���������û��ϴ��ļ���С,��λ:�ֽ�
					tDiskFileUpload.setSizeMax(10240000);
					//�������ֻ�������ڴ��д洢������,��λ:�ֽ�
					tDiskFileUpload.setSizeThreshold(4096);
					//����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
					tDiskFileUpload.setRepositoryPath(tSysUploadPath+"/temp/");
					tDiskFileUpload.setHeaderEncoding("GBK");
					//��ʼ��ȡ�ϴ���Ϣ
					List tList = tDiskFileUpload.parseRequest(request);
					Iterator tIterator = tList.iterator();
					
					while (tIterator.hasNext()) {
					
						FileItem tFileItem = (FileItem) tIterator.next();
						//�������������ļ�������б�����Ϣ
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
								tFileItem.write(new File(tSysUploadPath+"temp/"+tCurrentDate+tCurrentTime+"."+tExtenName));
								tFlagStr = "Succ";
								tContent = "�ϴ��ļ��ɹ�";
							} catch(Exception e) {
								tFlagStr = "Fail";
								tContent = "�ϴ��ļ�ʧ��";
							}
						}
					}
				}
			}
			
			if ("Succ".equals(tFlagStr)) {
				
				LDAttachmentSchema tLDAttachmentSchema = new LDAttachmentSchema();
				
				tLDAttachmentSchema.setAttachID(tPerUWSerialNo);//����AttachID�ֶδ洢ѡ�и��˺˱���Ϣ�����
				tLDAttachmentSchema.setOtherNoType(tOtherNoType);
				tLDAttachmentSchema.setOtherNo(tOtherNo);
				tLDAttachmentSchema.setSubOtherNo(tSubOtherNo);
				tLDAttachmentSchema.setAttachType(tAttachType);
				tLDAttachmentSchema.setAttachName(tFileName);
				tLDAttachmentSchema.setAttachSysName(tCurrentDate+tCurrentTime+"."+tExtenName);
				tLDAttachmentSchema.setUploadNode(tUploadNode);
				tLDAttachmentSchema.setAttachPath(tSysUploadPath+"temp/"+tCurrentDate+tCurrentTime+"."+tExtenName);
				tLDAttachmentSchema.setExtenName(tExtenName);
				
				
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("UploadNode", tUploadNode);
				
				VData tVData = new VData();
				tVData.add(tGI);
				tVData.add(tTransferData);
				tVData.add(tLDAttachmentSchema);
				
				BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
				if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotPersonUWUI")) {
					tContent = tBusinessDelegate.getCErrors().getFirstError();
					tFlagStr = "Fail";
				} else {
					tContent = "�����ɹ���";
					tFlagStr = "Succ";
				}
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "�����쳣������ϵϵͳ��ά��Ա��";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>