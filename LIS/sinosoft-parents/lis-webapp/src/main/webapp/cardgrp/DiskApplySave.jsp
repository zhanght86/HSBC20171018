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
<!--%@page import="com.sinosoft.lis.cardgrp.*"%-->
<%@page import="com.sinosoft.lis.tbgrp.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%
int count=0;

String EdorValiDate=request.getParameter("EdorValiDate");
String EdorTypeCal=request.getParameter("EdorTypeCal");
String path = application.getRealPath("").replace('\\','/');
if(!path.endsWith("/")){
	path += "/";
}


DiskFileUpload fu = new DiskFileUpload();
// 设置允许用户上传文件大小,单位:字节
//fu.setSizeMax(10000000);
// maximum size that will be stored in memory?
// 设置最多只允许在内存中存储的数据,单位:字节
//fu.setSizeThreshold(4096);
// 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
fu.setRepositoryPath(path+"/temp");
//开始读取上传信息
List fileItems = fu.parseRequest(request);

//loggerDebug("DiskApplySave",path);
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
//		loggerDebug("DiskApplySave","上传路径:"+ImportPath);
	}
	//忽略其他不是文件域的所有表单信息
	if (!item.isFormField())
	{
		String name = item.getName();
		loggerDebug("DiskApplySave",name);
		long size = item.getSize();
		if((name==null||name.equals("")) && size==0)
			continue;
		ImportPath= path + "temp/";
		FileName = name.replace('\\','/');
		FileName = FileName.substring(FileName.lastIndexOf("/") + 1);
		loggerDebug("DiskApplySave",ImportPath);
		loggerDebug("DiskApplySave",FileName);
    loggerDebug("DiskApplySave","*****************");
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
boolean res = true;

ParseGuideInUI tParseGuideInUI   = new ParseGuideInUI();

if (count >0)
{
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");
	// 准备传输数据 VData
	VData tVData = new VData();
	FlagStr="";
	//保全增人标记==2005-5-11==update=====================
	if(request.getParameter("BQFlag")!=null&&request.getParameter("EdorType")!=null)
	{
		tTransferData.setNameAndValue("BQFlag",request.getParameter("BQFlag"));
		tTransferData.setNameAndValue("SavePolType",request.getParameter("BQFlag"));
		tTransferData.setNameAndValue("EdorType",request.getParameter("EdorType"));
		tTransferData.setNameAndValue("EdorValiDate",request.getParameter("EdorValiDate"));		
		tTransferData.setNameAndValue("EdorTypeCal",request.getParameter("EdorTypeCal"));		
		loggerDebug("DiskApplySave","--BQFlag:"+tTransferData.getValueByName("BQFlag")+"----EdorType:"+request.getParameter("EdorValiDate"));
	}
	tTransferData.setNameAndValue("FileName",FileName);
	tTransferData.setNameAndValue("FilePath", path);
	tVData.add(tTransferData);
	tVData.add(tG);
	try
	{
		res= tParseGuideInUI.submitData(tVData,"");
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
	Content = " 提交成功! ";
	FlagStr = "Succ";
	loggerDebug("DiskApplySave","---aaa");
	if (tParseGuideInUI.getResult()!=null)
	{
		try
		{
			if(tParseGuideInUI.getResult().get(0)!=null&&tParseGuideInUI.getResult().size()>0)
			{
				Result = (String)tParseGuideInUI.getResult().get(0);
			}
		}
		catch(Exception e)
		{
		}
	}
}
else
{
	tError = tParseGuideInUI.mErrors;
	for (int i=0;i<tError.getErrorCount();i++)
	{
		errMess+= tError.getError(i).errorMessage + "\\n" + "\\n " ;
	}
	loggerDebug("DiskApplySave","---ccc");
	Content = " 保存失败，原因是:\\n " + errMess;
	FlagStr = "Fail";
}
loggerDebug("DiskApplySave","---bbb"+FlagStr);
//添加各种预处理
%>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=Result%>");
</script>
