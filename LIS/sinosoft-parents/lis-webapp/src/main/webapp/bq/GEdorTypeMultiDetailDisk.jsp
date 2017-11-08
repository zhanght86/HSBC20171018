<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�GrpEdorTypeZTLoadSubmit.jsp
//�����ܣ�BB����
//�������ڣ�
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.bqgrp.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
    String busiName="bqgrpGEdorTypeZTForLoadBL";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    //�������
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	String errMess = "";
	String Result = "";
        int count=0;


		String path = application.getRealPath("").replace('\\','/');
		if(!path.endsWith("/"))
		{
		    path += "/";
		}
		DiskFileUpload fu  = new DiskFileUpload();
		fu.setSizeMax(10000000);
		fu.setSizeThreshold(4096);
		fu.setRepositoryPath(path+"/bqconfig/BqUp/");
		List fileItems = fu.parseRequest(request);
		loggerDebug("GEdorTypeMultiDetailDisk","Path:"+path);
		String ImportPath = "";
		String FileName = "";
		// ���δ���ÿ���ϴ����ļ�
		Iterator iter = fileItems.iterator();
		while (iter.hasNext())
		{
			  FileItem item = (FileItem) iter.next();
			  if (item.getFieldName().compareTo("ImportPath")==0)
			  {
				    ImportPath = item.getString();
			  }
			  if (!item.isFormField())
			  {
				    String name = item.getName();
				    long size = item.getSize();
				    if((name==null||name.equals("")) && size==0)
				      continue;
				    ImportPath= path + "bqconfig/BqUp/";
				    FileName = name.replace('\\','/');
				    FileName = FileName.substring(FileName.lastIndexOf("/") + 1);
				    try
				    {
					      item.write(new File(ImportPath + FileName));
					      count = 1;
				    }
				    catch(Exception e)
				    {
				       loggerDebug("GEdorTypeMultiDetailDisk","upload file error ...");
				    }
			  }
			  
		}

		TransferData tTransferData = new TransferData();
		boolean res = true;
		
		if (count >0)
		{
			
			  GlobalInput tG = new GlobalInput();
			  tG=(GlobalInput)session.getValue("GI");
				LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
				tLPGrpEdorItemSchema.setEdorAcceptNo(request
						.getParameter("EdorAcceptNo"));
				loggerDebug("GEdorTypeMultiDetailDisk",request.getParameter("EdorAcceptNo"));
				tLPGrpEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
				tLPGrpEdorItemSchema
						.setGrpContNo(request.getParameter("GrpContNo"));
				tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
				tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));
				tLPGrpEdorItemSchema.setMakeDate(PubFun.getCurrentDate());
				tLPGrpEdorItemSchema.setMakeTime(PubFun.getCurrentTime());
				tLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
				tLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
				tLPGrpEdorItemSchema.setOperator(tG.Operator);
				tLPGrpEdorItemSchema.setManageCom(tG.ManageCom);
			  VData tVData = new VData();
			  FlagStr="";
			  tTransferData.setNameAndValue("FileName",  FileName);
			  tTransferData.setNameAndValue("FilePath",  path);
			  tVData.add(tLPGrpEdorItemSchema);
			  tVData.add(tTransferData);
			  tVData.add(tG);
			
	
			  res = true;
				   
			        if (!tBusinessDelegate.submitData(tVData, "INSERT||MUlTIEDOR",busiName)) {
			        	
						    FlagStr = "Fail";
						    tError = tBusinessDelegate.getCErrors();

							  Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
							  res = false;
			        }
			        
		}
		else
		{
			  Content = "�����ļ�ʧ��! ";
			  FlagStr = "Fail";
			  res = false;
		}
		
		
		
		if (res)
		{

			  Content = " �ύ�ɹ�! ";
			  FlagStr = "Succ";
		}
		


%>
<%@page import="com.sinosoft.lis.schema.LPGrpEdorItemSchema"%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=Result%>");
</script>
</html>
