<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：GrpEdorTypeAmntLoadSubmit.jsp
//程序功能：分帐户给付导入
//创建日期：
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.get.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%
    //输出参数
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";


        int count=0;
		String tGrpContNo = request.getParameter("LGrpContNo");
		loggerDebug("LFGetPayLoad","GrpContNo:"+tGrpContNo);
    
;
    
    
		String path = application.getRealPath("").replace('\\','/');
		loggerDebug("LFGetPayLoad","Path:"+path);
		if(!path.endsWith("/"))
		{
		    path += "/";
		}
		loggerDebug("LFGetPayLoad","Path:"+path);
		DiskFileUpload fu  = new DiskFileUpload();
		fu.setSizeMax(10000000);
		fu.setSizeThreshold(4096);
		fu.setRepositoryPath(path+"bqconfig/BqUp/");
		loggerDebug("LFGetPayLoad","Path:"+path);
		List fileItems = fu.parseRequest(request);
 
		loggerDebug("LFGetPayLoad","Path:"+path);
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
				       loggerDebug("LFGetPayLoad","upload file error ...");
				    }
			  }

		}

		TransferData tTransferData = new TransferData();
		boolean res = true;
		SubAccForLoadBL tSubAccForLoadBL   = new SubAccForLoadBL();
		if (count >0)
		{
			  GlobalInput tG = new GlobalInput();
			  tG=(GlobalInput)session.getValue("GI");
			  VData tVData = new VData();
			  FlagStr="";
			  tTransferData.setNameAndValue("FileName",  FileName);
			  tTransferData.setNameAndValue("FilePath",  path);
			  tTransferData.setNameAndValue("GrpContNo",  tGrpContNo);
			  tVData.add(tTransferData);
			  tVData.add(tG);
			  try
			  {
			      res= tSubAccForLoadBL.submitData(tVData,"");
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
		    tError = tSubAccForLoadBL.mErrors;
		    loggerDebug("LFGetPayLoad","-----错误："+tError.getErrorCount());
			  for (int i=0;i<tError.getErrorCount();i++)
			  {
			      errMess+= tError.getError(i).errorMessage ;
			  }
			  Content = " 提交成功! "+ errMess;
			  FlagStr = "Succ";
		}
		else
		{
		    tError = tSubAccForLoadBL.mErrors;
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
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
