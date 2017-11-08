<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
/*******************************************************************************
* <p>Title: 保全-团单磁盘导入</p>
* <p>Description: 团单磁盘导入js文件</p>
* <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
* <p>Company: 中科软科技股份有限公司</p>
* <p>WebSite: http://www.sinosoft.com.cn</p>
*
* @todo     : 保全-团单磁盘导入
* @author   : zhangtao
* @version  : 1.00 
* @date     : 2006-11-24
* @modify   : 2006-11-25
******************************************************************************/
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%
int count=0;

//获取界面参数
String sEdorNo = request.getParameter("EdorNo");
String sEdorAcceptNo = request.getParameter("EdorAcceptNo");
String sEdorType = request.getParameter("EdorType");
String sEdorValiDate = request.getParameter("EdorValiDate");
String sBQFlag = request.getParameter("BQFlag");

	    
loggerDebug("GEdorDiskImportSave","========EdorNo==" + sEdorNo);
loggerDebug("GEdorDiskImportSave","========sEdorAcceptNo==" + sEdorAcceptNo);
loggerDebug("GEdorDiskImportSave","========sEdorType==" + sEdorType);
loggerDebug("GEdorDiskImportSave","========EdorValiDate==" + sEdorValiDate);
loggerDebug("GEdorDiskImportSave","========BQFlag==" + sBQFlag);

String path = application.getRealPath("").replace('\\','/');
if(!path.endsWith("/")){
	path += "/";
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

//loggerDebug("GEdorDiskImportSave",path);
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
		loggerDebug("GEdorDiskImportSave","上传路径:"+ImportPath);
	}
	//忽略其他不是文件域的所有表单信息
	if (!item.isFormField())
	{
		String name = item.getName();
		loggerDebug("GEdorDiskImportSave",name);
		long size = item.getSize();
		if((name==null||name.equals("")) && size==0)
		{
			continue;
		}
		ImportPath= path + ImportPath;
		FileName = name.replace('\\','/');
		FileName = FileName.substring(FileName.lastIndexOf("/") + 1);

		//保存上传的文件到指定的目录
		try
		{
			item.write(new File(ImportPath + FileName));
			loggerDebug("GEdorDiskImportSave","======ImportPath + FileName======" + ImportPath + FileName);
			count = 1;
		}
		catch(Exception e){
			loggerDebug("GEdorDiskImportSave","upload file error ...");
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
loggerDebug("GEdorDiskImportSave","----FileName:"+FileName);
boolean res = true;

if (count <= 0)
{
	Content = "上载文件失败! ";
	FlagStr = "Fail";
}
else 
{
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");
	// 准备传输数据 VData
	VData tVData = new VData();
	FlagStr="";
	tTransferData.setNameAndValue("FileName",FileName);
	tTransferData.setNameAndValue("FilePath", path);
	loggerDebug("GEdorDiskImportSave","========FileName==" + FileName);
    loggerDebug("GEdorDiskImportSave","========FilePath==" + path);
  
	try
	{ //增人调用新契约人名清单导入 add by zhangtao 2006-11-23
			tTransferData.setNameAndValue("EdorAcceptNo", sEdorAcceptNo);
	    tTransferData.setNameAndValue("EdorNo", sEdorNo);
	    tTransferData.setNameAndValue("EdorType", sEdorType);
	    tTransferData.setNameAndValue("EdorValiDate", sEdorValiDate);
	    tTransferData.setNameAndValue("BQFlag", sBQFlag);

	} 
	catch(Exception e)
	{
	}
	
	EdorDiskImport tEdorDiskImport  = new EdorDiskImport();
	ParseGuideInUI tParseGuideInUI  = new ParseGuideInUI();
		
	tVData.add(tTransferData);
	tVData.add(tG);
	try
	{
		
		if (sEdorType.equals("NI"))
		{ //增人调用新契约人名清单导入 add by zhangtao 2006-11-23
			res= tParseGuideInUI.submitData(tVData,"");
		}
		else
		{
			res= tEdorDiskImport.submitData(tVData,"");
		}

	}
	catch(Exception ex)
	{
		Content = "导入失败，原因是:" + ex.toString();
		FlagStr = "Fail";
	}
	
	if ("".equals(FlagStr))
	{
				if (sEdorType.equals("NI"))
				{ //增人调用新契约人名清单导入 add by zhangtao 2006-11-23
					tError = tParseGuideInUI.mErrors;
				}
				else
				{
					tError = tEdorDiskImport.mErrors;
				}
		    
		    if (!tError.needDealError())
		    {                          
				  Content ="导入完毕，请查看人名清单导入日志！";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = "导入失败，原因是:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
	} 
}

%>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=Result%>");
</script>
