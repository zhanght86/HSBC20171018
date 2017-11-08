<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%
/***************************************************************
 * <p>ProName��LSQuotOtherOpinionSave.jsp</p>
 * <p>Title��ѯ�������������</p>
 * <p>Description��ѯ�������������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-04-16
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@page import="com.sinosoft.lis.schema.LDAttachmentSchema"%>
<%@page import="com.sinosoft.lis.schema.LSQuotOtherOpinionSchema"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import = "com.jspsmart.upload.*"%>
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
			
			String tQuotType = request.getParameter("QuotType");
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tTranProdType = request.getParameter("TranProdType");
			
			String tSerialNo = "";
			String tOpinionType = "";
			String tOpinionDesc = "";
			String tAttachPath = "";
			String tFileName = "";
			String tExtenName = "";
			
			String tCurrentDate = PubFun.getCurrentDate();
			String tCurrentTime = PubFun.getCurrentTime();
			tCurrentDate = tCurrentDate.replace("-","");
			tCurrentTime = tCurrentTime.replace(":","");
			
			ExeSQL tExeSQL = new ExeSQL();
			String tSql = "select sysvarvalue from ldsysvar where sysvar='UploadPath'";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(tSql);
			String tSysUploadPath = tExeSQL.getOneValue(sqlbv);
			
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
					
					File tTempFile = new File(tSysUploadPath+"/temp/");
					if (!tTempFile.exists()) {
						tTempFile.mkdirs();
					}
				}
				
				tAttachPath = tSysUploadPath;
				
				SmartUpload su = new SmartUpload();
				String tAllowFileType = "EML,eml,txt,doc,docx,xls,xlsx,pdf,zip,rar,gif,TXT,DOC,DOCX,XLS,XLSX,PDF,ZIP,RAR,GIF,,";
				su.initialize(pageContext);
				su.setAllowedFilesList(tAllowFileType);//���ÿ��ϴ��ļ�����
				su.setMaxFileSize(100000000);//���������ϴ��ļ���С,��λ:�ֽ�
				su.setTotalMaxFileSize(1000000000);
				su.setContentDisposition(null);
				su.upload();
				
				Request rs = su.getRequest();//�ύ��ʽ:multipart/form-data,��ȡ������
				
				tSerialNo = rs.getParameter("OtherOpinionSerialNo");
				tOpinionType = rs.getParameter("OpinionType");
				tOpinionDesc = rs.getParameter("OpinionDesc");
				
				//��ʼ��ȡ�ϴ���Ϣ
				int tFileSize = su.getFiles().getCount();
				for (int i=0; i<tFileSize; i++) {
				
					com.jspsmart.upload.File tUploadFile = su.getFiles().getFile(i);
					if (tUploadFile.isMissing()) {
						continue;
					}
					
					tFileName = tUploadFile.getFileName();
					tExtenName = tUploadFile.getFileExt();
					tUploadFile.saveAs(tSysUploadPath+tCurrentDate+tCurrentTime+"."+tExtenName);
				}
			}
			
			LSQuotOtherOpinionSchema tLSQuotOtherOpinionSchema = new LSQuotOtherOpinionSchema();
			
			tLSQuotOtherOpinionSchema.setSerialNo(tSerialNo);
			tLSQuotOtherOpinionSchema.setQuotNo(tOtherNo);
			tLSQuotOtherOpinionSchema.setQuotBatNo(tSubOtherNo);
			tLSQuotOtherOpinionSchema.setOpinionType(tOpinionType);
			tLSQuotOtherOpinionSchema.setOpinionDesc(tOpinionDesc);
			
			LDAttachmentSchema tLDAttachmentSchema = null;
			
			if (!"".equals(tFileName)) {
			
				tLDAttachmentSchema = new LDAttachmentSchema();
				
				tLDAttachmentSchema.setOtherNoType(tOtherNoType);
				tLDAttachmentSchema.setOtherNo(tOtherNo);
				tLDAttachmentSchema.setSubOtherNo(tSubOtherNo);
				tLDAttachmentSchema.setAttachType(tAttachType);
				tLDAttachmentSchema.setSubAttachType(tOpinionType);
				tLDAttachmentSchema.setAttachName(tFileName);
				tLDAttachmentSchema.setAttachSysName(tCurrentDate+tCurrentTime+"."+tExtenName);
				tLDAttachmentSchema.setUploadNode(tUploadNode);
				tLDAttachmentSchema.setAttachPath(tAttachPath+tCurrentDate+tCurrentTime+"."+tExtenName);
				tLDAttachmentSchema.setExtenName(tExtenName);
			}
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("QuotType", tQuotType);
			tTransferData.setNameAndValue("QuotNo", tQuotNo);
			tTransferData.setNameAndValue("QuotBatNo", tQuotBatNo);
			tTransferData.setNameAndValue("MissionID", tMissionID);
			tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
			tTransferData.setNameAndValue("ActivityID", tActivityID);
			tTransferData.setNameAndValue("TranProdType", tTranProdType);
			
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLSQuotOtherOpinionSchema);
			tVData.add(tLDAttachmentSchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotUWDetailUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "����ɹ���";
				tFlagStr = "Succ";
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
