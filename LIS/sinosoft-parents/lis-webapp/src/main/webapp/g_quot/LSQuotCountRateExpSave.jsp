<%
/***************************************************************
 * <p>ProName��LSQuotCountRateExpSave.jsp</p>
 * <p>Title�����ղ���</p>
 * <p>Description�����ʲ���--����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-10-13
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
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
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			
			String tExpType = request.getParameter("ExpType");
			String tAge = request.getParameter("Age");
			String tYearStart = request.getParameter("YearStart");
			String tYearEnd = request.getParameter("YearEnd");
			
			String tSysUploadPath = "";
			String tFilePath = "";
			
			String tCurrentDate = PubFun.getCurrentDate();
			String tCurrentTime = PubFun.getCurrentTime();
			tCurrentDate = tCurrentDate.replace("-","");
			tCurrentTime = tCurrentTime.replace(":","");
			
			String tTempPath = "";
			String tTempFileName = "";
			if ("EXPRATE".equals(tOperate)) {
				tTempPath = "exprate";
				tTempFileName = "ExpRate_";
			} else if ("EXPCASHVALUE".equals(tOperate)) {
				tTempPath = "expcashvalue";
				tTempFileName = "ExpCashValue_"+tQuotNo+"_"+tQuotBatNo+"_";
			}
			
			ExeSQL tExeSQL = new ExeSQL();
			String tSql = "select sysvarvalue from ldsysvar where sysvar='ExportPath'";
			tSysUploadPath = tExeSQL.getOneValue(tSql);
			
			if (tSysUploadPath==null || "".equals(tSysUploadPath)) {
				tContent = "δ���ø����ϴ�·����";
				tFlagStr = "Fail";
			} else {
				
				String tDate = PubFun.getCurrentDate();
				String tYear = tDate.substring(0,4);
				String tMonth = tDate.substring(5,7);
				String tDay = tDate.substring(8);
					
				tFlagStr = "Succ";
				if (!tSysUploadPath.endsWith("/")) {
					tSysUploadPath += "/";
				}
				
				File tFile = new File(tSysUploadPath);
				if (!tFile.exists()) {
					tFile.mkdirs();
				}
				File tTempFile = new File(tSysUploadPath+"quot/"+tTempPath+"/"+tYear+"/"+tMonth+"/"+tDay+"/");
				if (!tTempFile.exists()) {
					tTempFile.mkdirs();
				}
				
				DiskFileUpload tDiskFileUpload = new DiskFileUpload();
				//���������û��ϴ��ļ���С,��λ:�ֽ�
				tDiskFileUpload.setSizeMax(10240000);
				//�������ֻ�������ڴ��д洢������,��λ:�ֽ�
				tDiskFileUpload.setSizeThreshold(4096);
				//����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
				tDiskFileUpload.setRepositoryPath(tSysUploadPath+"quot/"+tTempPath+"/"+tYear+"/"+tMonth+"/"+tDay+"/");
				tDiskFileUpload.setHeaderEncoding("GBK");
				
				tFilePath = tSysUploadPath+"quot/"+tTempPath+"/"+tYear+"/"+tMonth+"/"+tDay+"/"+tTempFileName+tCurrentTime+".xls";
						
			}
			
			if ("Succ".equals(tFlagStr)) {
				
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("FilePath",tFilePath);
				tTransferData.setNameAndValue("QuotNo",tQuotNo);
				tTransferData.setNameAndValue("QuotBatNo",tQuotBatNo);
				tTransferData.setNameAndValue("ExpType",tExpType);
				tTransferData.setNameAndValue("Age",tAge);
				tTransferData.setNameAndValue("YearStart",tYearStart);
				tTransferData.setNameAndValue("YearEnd",tYearEnd);
				
				LWMissionSchema tLWMissionSchema = new LWMissionSchema();
				
				VData tVData = new VData();
				tVData.add(tGI);
				tVData.add(tTransferData);
				tVData.add(tLWMissionSchema);
				
				BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
				if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotCountRateExpUI")) {
				
						tContent = "����ʧ��!"+ tBusinessDelegate.getCErrors().getFirstError();
						tFlagStr = "Fail";
				} else {
					tContent = tFilePath;
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
