<%
/***************************************************************
 * <p>ProName��LQuotPlanImpSave.jsp</p>
 * <p>Title��ѯ�۷�������</p>
 * <p>Description��ѯ�۷�������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-07-16
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@page import="com.sinosoft.lis.schema.LDAttachmentSchema"%>
<%@page import="com.sinosoft.lis.schema.LWMissionSchema"%>
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
			
			String tQuotType = request.getParameter("QuotType");
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			String tProdType = request.getParameter("ProdType");
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			
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
					tContent = "δ���ø����ϴ�·����";
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
					File tTempFile = new File(tSysUploadPath+"quot/imppaln/"+tYear+"/"+tMonth+"/"+tDay+"/");
					if (!tTempFile.exists()) {
						tTempFile.mkdirs();
					}
					
					DiskFileUpload tDiskFileUpload = new DiskFileUpload();
					//���������û��ϴ��ļ���С,��λ:�ֽ�
					tDiskFileUpload.setSizeMax(10240000);
					//�������ֻ�������ڴ��д洢������,��λ:�ֽ�
					tDiskFileUpload.setSizeThreshold(4096);
					//����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
					tDiskFileUpload.setRepositoryPath(tSysUploadPath+"quot/imppaln/"+tYear+"/"+tMonth+"/"+tDay+"/");
					tDiskFileUpload.setHeaderEncoding("GBK");
					//��ʼ��ȡ�ϴ���Ϣ
					List tList = tDiskFileUpload.parseRequest(request);
					Iterator tIterator = tList.iterator();
					
					while (tIterator.hasNext()) {
					
						FileItem tFileItem = (FileItem) tIterator.next();
						//�������������ļ�������б���Ϣ
						if (!tFileItem.isFormField()) {
						
							String tItemName = tFileItem.getName();
							long tItemSize = tFileItem.getSize();
							if ((tItemName==null || "".equals(tItemName)) && tItemSize==0) {
								continue;
							}
							
							tFileName = tItemName.replace('\\','/');
							tFileName = tFileName.substring(tFileName.lastIndexOf("/") + 1);
							tExtenName = tFileName.substring(tFileName.lastIndexOf(".")+1);
							tFilePath = tSysUploadPath+"quot/imppaln/"+tYear+"/"+tMonth+"/"+tDay+"/"+tCurrentDate+tCurrentTime+"."+tExtenName;
							
							try {
								
								tFileItem.write(new File(tFilePath));
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
				
				TransferData tTransferData = new TransferData();
				
				tTransferData.setNameAndValue("FilePath",tFilePath);
				tTransferData.setNameAndValue("QuotNo",tQuotNo);
				tTransferData.setNameAndValue("QuotBatNo",tQuotBatNo);
				tTransferData.setNameAndValue("QuotType",tQuotType);
				tTransferData.setNameAndValue("ProdType",tProdType);
				
				LWMissionSchema tLWMissionSchema = new LWMissionSchema();
				tLWMissionSchema.setMissionID(tMissionID);
				tLWMissionSchema.setSubMissionID(tSubMissionID);
				tLWMissionSchema.setActivityID(tActivityID);
				
				VData tVData = new VData();
				tVData.add(tGI);
				tVData.add(tTransferData);
				tVData.add(tLWMissionSchema);
				
				BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
				if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotPlanImpUI")) {
				
						tContent = "����ʧ��!"+ tBusinessDelegate.getCErrors().getFirstError();
						tFlagStr = "Fail";
				} else {
				
					int tImpPlanCount = Integer.parseInt((String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 1));
					int tSuccPlanCount = Integer.parseInt((String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 2));
					
					if(tSuccPlanCount<tImpPlanCount) {
						tContent = "���ι����룺"+tImpPlanCount+"������,���е���ɹ���"+tSuccPlanCount+"��,���ڵ���ʧ�ܵĵ��뷽��,��鿴������Ϣ��";
					} else {
						tContent = "����ɹ�!���ι��ɹ����룺"+tImpPlanCount+"��������";	
					}
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
