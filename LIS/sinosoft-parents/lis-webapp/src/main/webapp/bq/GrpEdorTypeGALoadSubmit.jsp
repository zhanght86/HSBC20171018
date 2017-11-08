<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：GrpEdorTypeGBLoadSubmit.jsp
//程序功能：GB导入
//创建日期：
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.operfee.*"%>
<%@page import="com.sinosoft.lis.bqgrp.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
    
    String busiName="bqgrpGrpEdorGAForLoadBL";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    //输出参数
	CErrors tError = null;
	String tRela  = "";
	String FlagStr = "Fail";
	String Content = "";
	String Result="";
	VData mVData = new VData();
	String mBatchNo = "";
        int count=0;
		String tGrpContNo = request.getParameter("GrpContNo");
		loggerDebug("GrpEdorTypeGALoadSubmit","GrpContNo:"+tGrpContNo);
    String tEdorNo = request.getParameter("EdorNo");
    String tEdorType = request.getParameter("EdorType");
    loggerDebug("GrpEdorTypeGALoadSubmit","EdorType:"+tEdorType);
    String tEdorAcceptNo = request.getParameter("EdorAcceptNo");
    loggerDebug("GrpEdorTypeGALoadSubmit","EdorAcceptNo:"+tEdorAcceptNo);
		String path = application.getRealPath("").replace('\\','/');
		loggerDebug("GrpEdorTypeGALoadSubmit","Path:"+path);
		if(!path.endsWith("/"))
		{
		    path += "/";
		}
		loggerDebug("GrpEdorTypeGALoadSubmit","Path:"+path);
		DiskFileUpload fu  = new DiskFileUpload();
		fu.setSizeMax(10000000);
		fu.setSizeThreshold(4096);
		fu.setRepositoryPath(path+"/bqconfig/BqUp/");
		loggerDebug("GrpEdorTypeGALoadSubmit","Path:"+path);
		List fileItems = fu.parseRequest(request);
 
		loggerDebug("GrpEdorTypeGALoadSubmit","Path:"+path);
		String ImportPath = "";
		String FileName = "";
		// 依次处理每个上传的文件
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
				       loggerDebug("GrpEdorTypeGALoadSubmit","upload file error ...");
				    }
			  }

		}

		TransferData tTransferData = new TransferData();
		boolean res = true;
		
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
			  tTransferData.setNameAndValue("EdorType",  tEdorType);
			  tTransferData.setNameAndValue("EdorAcceptNo",  tEdorAcceptNo);
			  tVData.add(tTransferData);
			  tVData.add(tG);
			  try
			  {
			      res= tBusinessDelegate.submitData(tVData,"",busiName);
			      loggerDebug("GrpEdorTypeGALoadSubmit","------结果："+res);
			      mVData = tBusinessDelegate.getResult();
			      //mBatchNo = (String)mVData.getObjectByObjectName("String",0);
			  }
			  catch(Exception ex)
			  {
				    Content = "保存失败，原因是:" + ex.toString();
				    FlagStr = "Fail";
			  }
		}
		else
		{
			  Content = "上载文件失败! ";
			  FlagStr = "Fail";
		}

		String errMess = "";

		if (res)
		{
		    tError = tBusinessDelegate.getCErrors();
		    loggerDebug("GrpEdorTypeGALoadSubmit","-----错误："+tError.getErrorCount());
			  for (int i=0;i<tError.getErrorCount();i++)
			  {
			      errMess+= tError.getError(i).errorMessage ;
			  }
			  Content = " 提交成功! "+ errMess;
			  FlagStr = "Succ";
		}
		else
		{
		    tError = tBusinessDelegate.getCErrors();
			  for (int i=0;i<tError.getErrorCount();i++)
			  {
			    errMess+= tError.getError(i).errorMessage ;
			  }
			  Content = " 保存失败，原因是:" + errMess;
			  FlagStr = "Fail";
		}
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmitForLoad("<%=FlagStr%>","<%=Content%>");
</script>
</html>
