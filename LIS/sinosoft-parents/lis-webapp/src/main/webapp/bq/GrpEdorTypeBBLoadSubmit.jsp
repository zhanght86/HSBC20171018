<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�GrpEdorTypeBBLoadSubmit.jsp
//�����ܣ�BB����
//�������ڣ�
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.operfee.*"%>
<%@page import="com.sinosoft.lis.bqgrp.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%
    //�������
	CErrors tError = null;
	String tRela  = "";
	String FlagStr = "Fail";
	String Content = "";
	String Result="";
	VData mVData = new VData();
	String mBatchNo = "";
        int count=0;
		String tGrpContNo = request.getParameter("GrpContNo");
                String tEdorNo = request.getParameter("EdorNo");
    String tEdorValiDate = request.getParameter("EdorValiDate");
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
		loggerDebug("GrpEdorTypeBBLoadSubmit","Path:"+path);
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
				       loggerDebug("GrpEdorTypeBBLoadSubmit","upload file error ...");
				    }
			  }
			  
		}

		TransferData tTransferData = new TransferData();
		boolean res = true;
		GrpEdorBBForLoadBL tGrpEdorBBForLoadBL   = new GrpEdorBBForLoadBL();
		if (count >0)
		{
			  GlobalInput tG = new GlobalInput();
			  tG=(GlobalInput)session.getValue("GI");
			  VData tVData = new VData();
			  FlagStr="";
			  tTransferData.setNameAndValue("FileName",  FileName);
			  tTransferData.setNameAndValue("FilePath",  path);
			  tTransferData.setNameAndValue("GrpContNo",  tGrpContNo);
			  tTransferData.setNameAndValue("EdorNo",  tEdorNo);
			  tTransferData.setNameAndValue("EdorValiDate",tEdorValiDate);
			  tVData.add(tTransferData);
			  tVData.add(tG);
			  try
			  {
			      res= tGrpEdorBBForLoadBL.submitData(tVData,"");
			      mVData = tGrpEdorBBForLoadBL.getResult();
			      //mBatchNo = (String)mVData.getObjectByObjectName("String",0);
			  }
			  catch(Exception ex)
			  {
				    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
				    FlagStr = "Fail";
			  }
		}
		else
		{
			  Content = "�����ļ�ʧ��! ";
			  FlagStr = "Fail";
		}
		
		String errMess = "";
		
		if (res)
		{
		    tError = tGrpEdorBBForLoadBL.mErrors;
			  for (int i=0;i<tError.getErrorCount();i++)
			  {
			      errMess+= tError.getError(i).errorMessage ;
			  }
			  Content = " �ύ�ɹ�! "+ errMess;
			  FlagStr = "Succ";
		}
		else
		{
		    tError = tGrpEdorBBForLoadBL.mErrors;
			  for (int i=0;i<tError.getErrorCount();i++)
			  {
			    errMess+= tError.getError(i).errorMessage ;
			  }
			  Content = " ����ʧ�ܣ�ԭ����:" + errMess;
			  FlagStr = "Fail";
		}
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmitForLoad("<%=FlagStr%>","<%=Content%>");
</script>
</html>
