<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：ProposalCopyInput.jsp
//程序功能：
//创建日期：2002-08-21 09:25:18
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
int count=0;
String tBatchNo = "";  

String ImportFlag = request.getParameter("ImportFlag");

String path = application.getRealPath("").replace('\\','/');
String ImportPath = "";
String FileName = "";

if(!path.endsWith("/")){
	path += "/";
}

File tFile = new File(path+"/temp");
if (!tFile.exists()){
    tFile.mkdir();
}

DiskFileUpload fu = new DiskFileUpload();
// 设置允许用户上传文件大小,单位:字节
fu.setSizeMax(10000000);
// maximum size that will be stored in memory?
// 设置最多只允许在内存中存储的数据,单位:字节
fu.setSizeThreshold(4096);
// 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
fu.setRepositoryPath(path+"/temp");
//开始读取上传信息
List fileItems = fu.parseRequest(request);

//loggerDebug("DiskApplySave",path);


// 依次处理每个上传的文件
Iterator iter = fileItems.iterator();
while (iter.hasNext())
{
	FileItem item = (FileItem) iter.next();
//	loggerDebug("DiskApplySave","item.getFieldName()=" + item.getFieldName());
	if (item.getFieldName().compareTo("ImportPath")==0)
	{
		ImportPath = item.getString();
	}
	
	if (item.getFieldName().compareTo("ImportFlag")==0)
	{
		ImportFlag = item.getString();
	}
	//忽略其他不是文件域的所有表单信息
	if (!item.isFormField())
	{
		String name = item.getName();
		loggerDebug("DiskApplySave","name=" + name);
		long size = item.getSize();
		if((name==null||name.equals("")) && size==0)
			continue;
		ImportPath= path + ImportPath;
		loggerDebug("DiskApplySave","上传路径ImportPath:"+ImportPath);
		//ImportPath= path + "temp/";
		FileName = name.replace('\\','/');
		FileName = FileName.substring(FileName.lastIndexOf("/") + 1);
		//保存上传的文件到指定的目录
		try
		{
			item.write(new File(ImportPath + FileName));
			count = 1;
		}
		catch(Exception e){
			loggerDebug("DiskApplySave","upload file error ...");
		}
	}
}

//输出参数
CErrors tError = null;
String tRela  = "";
String FlagStr = "Fail";
String Content = "";
String Result="";

TransferData tTransferData = new TransferData();
loggerDebug("DiskApplySave","----FileName:"+FileName);
boolean res = false;

GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");
// 准备传输数据 VData
VData tVData = new VData();
FlagStr="";
tTransferData.setNameAndValue("FileName",FileName);
tTransferData.setNameAndValue("FilePath", path);
tVData.add(tTransferData);
tVData.add(tG);

loggerDebug("DiskApplySave","ImportFlag=" + ImportFlag);
if (ImportFlag != null && "Car".equals(ImportFlag))
{
    loggerDebug("DiskApplySave","GrpCarImportUI");
    //GrpCarImportUI tGrpCarImportUI   = new GrpCarImportUI();
    String busiName="tbGrpCarImportUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    if (count >0)
    {
    	
    	try
    	{
    		res= tBusinessDelegate.submitData(tVData,"",busiName);
    	}
    	catch(Exception ex)
    	{
    		Content = ex.toString();
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
    	Content = " 提交成功! ";
    	if (tBusinessDelegate.getCErrors().needDealError()){
    	    Content = Content + "提示：" + tBusinessDelegate.getCErrors().getLastError();
    	}
    	FlagStr = "Succ";
    	loggerDebug("DiskApplySave","---aaa");
    }
    else
    {
    	tError = tBusinessDelegate.getCErrors();
   		errMess= tError.getLastError();
    	loggerDebug("DiskApplySave","---ccc");
    	Content = errMess;
    	FlagStr = "Fail";
    }
}
else
{
    loggerDebug("DiskApplySave","ParseGuideInUI");
    //ParseGuideInUI tParseGuideInUI   = new ParseGuideInUI();
    String busiName="tbParseGuideInUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    if (count >0)
    {
    	try
    	{
    		res= tBusinessDelegate.submitData(tVData,"",busiName);
    	}
    	catch(Exception ex)
    	{
    		Content = ex.toString();
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
    	Content = " 提交成功! ";
    	FlagStr = "Succ";
    }
    else
    {
    	tError = tBusinessDelegate.getCErrors();
   		errMess= tError.getLastError();
   		VData tReVData = tBusinessDelegate.getResult();
   		tTransferData = (TransferData) tReVData.getObjectByObjectName(
            "TransferData",0);
        if (tTransferData != null)
        {
            tBatchNo = (String) tTransferData.getValueByName("BatchNo");
        }
    	loggerDebug("DiskApplySave","---ccc");
    	Content = errMess;
    	FlagStr = "Fail";
    }
}
loggerDebug("DiskApplySave","---bbb"+FlagStr);
//添加各种预处理
%>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","","<%=tBatchNo%>");
</script>
