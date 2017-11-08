<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%
LPrtTempleteSchema tLPrtTempleteSchema=new LPrtTempleteSchema();
String tBusiName = "LPrtTempleteUI";
BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
LPrtRelatedSchema tLPrtRelatedSchema = new LPrtRelatedSchema();
TransferData tTransferData = new TransferData();
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");
CErrors tError =null;
String transact ="";
String tTempleteID ="";
String tTempleteName ="";
String tTempleteType ="";
String tTempleteTypeName = "";
String tTempleteID1 ="";
String tPrintID = "";
String tLanguage = "";
String tOutputTypeName = "";
String tDefaultType = "";
String FilePath ="print/templete";
String FilePath1 ="";
String FileName = "";
String FileLastName = "";
String FlagStr ="";
String Content ="";
int count = 0;
//应用根路径
String tPath = application.getRealPath("").replace('\\','/');
if(!tPath.endsWith("/"))
{
	tPath += "/";
}
loggerDebug("LPrtTempleteSave","应用路径"+tPath);
File tFile = new File(tPath+"/temp");
if (!tFile.exists())
{
	tFile.mkdir();
}

DiskFileUpload fu = new DiskFileUpload();
// 设置允许用户上传文件大小,单位:字节
fu.setSizeMax(10000000);
// maximum size that will be stored in memory?
// 设置最多只允许在内存中存储的数据,单位:字节
fu.setSizeThreshold(4096);
// 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
fu.setRepositoryPath(tPath+"/temp");
//开始读取上传信息
List fileItems = fu.parseRequest(request);
// 依次处理每个上传的文件
Iterator iter = fileItems.iterator();
while (iter.hasNext())
{
	FileItem item = (FileItem) iter.next();
	if (item.getFieldName().compareTo("TempleteID")==0)
	{
		tLPrtTempleteSchema.setTempleteID(item.getString());
		tLPrtRelatedSchema.setTempleteID(item.getString());
		tTempleteID = item.getString();
	}
	if (item.getFieldName().compareTo("TempleteName")==0)
	{
		tLPrtTempleteSchema.setTempleteName(item.getString());
		tTempleteName = item.getString();
	}
	if (item.getFieldName().compareTo("Language")==0)
	{
		tLPrtTempleteSchema.setLanguage(item.getString());
		tLPrtRelatedSchema.setLanguage(item.getString());
		tLanguage = item.getString();
	}
	if (item.getFieldName().compareTo("TempleteType")==0)
	{
		tLPrtTempleteSchema.setTempleteType(item.getString());
		tTempleteType = item.getString();
	}
	if (item.getFieldName().compareTo("TempleteTypeName")==0)
	{
		tTempleteTypeName = item.getString(); 
	} 
	if (item.getFieldName().compareTo("OutputType")==0)
	{
		tLPrtTempleteSchema.setOutputType(item.getString());
		tLPrtRelatedSchema.setOutputType(item.getString());
	}
	if (item.getFieldName().compareTo("Output")==0)
	{
		tLPrtTempleteSchema.setOutput(item.getString());
	}
	if (item.getFieldName().compareTo("OutputTypeName")==0)
	{
		tOutputTypeName = item.getString();
	}
	if (item.getFieldName().compareTo("fmtransact")==0)
	{
		transact=item.getString();
		loggerDebug("LPrtTempleteSave","transact=" + transact);
	}
	if (item.getFieldName().compareTo("PrintID")==0)
	{
		tLPrtRelatedSchema.setPrintID(item.getString());
		tPrintID = item.getString();
	}
	if (item.getFieldName().compareTo("DefaultType")==0)
	{
		tLPrtTempleteSchema.setDefaultType(item.getString());
		tDefaultType = item.getString();
	}
	//忽略其他不是文件域的所有表单信息
	if (!item.isFormField())
	{
		try
		{
			if(!transact.equals("DELETE||MAIN"))
			{
				String tClientFilePath = item.getName();
				loggerDebug("LPrtTempleteSave","tClientFilePath=" + tClientFilePath);
				loggerDebug("LPrtTempleteSave","transact=" + transact);
				long size = item.getSize();
				if((tClientFilePath==null||tClientFilePath.equals("")) && size==0)
				{
				    loggerDebug("LPrtTempleteSave","上传文件为空！");
				    continue;
				}
				//上传的文件路径
				FilePath= tPath + FilePath +"/"+tTempleteTypeName;
				//获得文件后缀名
				FileLastName = tClientFilePath.substring(tClientFilePath.lastIndexOf(".")+1);
				//上传文件名
//				FileName = tPrintID + tLanguage + tOutputTypeName +"."+ FileLastName;
				//以xls为模板，输出pdf文件时，LPrtTempleteBL找不到文件，所以修改20120608 by huangliang
				FileName = tPrintID + tLanguage + tTempleteTypeName +"."+ FileLastName;
				loggerDebug("LPrtTempleteSave","FileName=" + FileName);
				loggerDebug("LPrtTempleteSave","FilePath=" + FilePath);
				//保存上传的文件到指定的目录
				item.write(new File(FilePath,FileName));
				count = 1;
			}
			else 
			{
				loggerDebug("LPrtTempleteSave","删除模板！");
			}
		}
		catch(Exception e)
		{
			count = -1;
			loggerDebug("LPrtTempleteSave","文件上传出错！");
			FlagStr="Fail";
			Content="文件上传出错！";
		}
	}
}

//数据库里存的文件路
FilePath ="print/templete/" +tTempleteTypeName;
tLPrtTempleteSchema.setFilePath(FilePath+"/"+FileName);
tTransferData.setNameAndValue("Path",tPath);
tTransferData.setNameAndValue("OutputTypeName",tOutputTypeName);
tTransferData.setNameAndValue("TempleteTypeName",tTempleteTypeName);
//上载文件失败时 count = -1 
if(count != -1)
{
	VData tVData=new VData();
	tVData.addElement(tG);
	tVData.addElement(tLPrtTempleteSchema);
	tVData.addElement(tLPrtRelatedSchema);
	tVData.addElement(tTransferData);
	if(!tBusinessDelegate.submitData(tVData,transact,tBusiName))
	{
		if(tBusinessDelegate.getCErrors()!=null && tBusinessDelegate.getCErrors().getErrorCount()>0)
		{
			Content = "保存失败，原因是" + tBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
		}
		else
		{
			Content = "保存失败";
			FlagStr = "Fail";
		}	
	}
	else
	{
		if(transact.equals("DELETE||MAIN"))
		{
			Content = " 删除成功!";
			FlagStr = "Fail";
		}
		else
		{
			if(transact.equals("INSERT||MAIN"))
			{
				Content = " 保存成功! ";
			}
			if(transact.equals("UPDATE||MAIN"))
			{
				Content = " 修改成功! ";
			}
			FlagStr = "Success";
			tTempleteID1 = (String)tBusinessDelegate.getResult().get(0);
			FilePath1 = (String)tBusinessDelegate.getResult().get(1);
		}
	}		
}
%>
<html>
	<script language="javascript">
		parent.fraInterface.fm.all("TempleteID").value = '<%=tTempleteID1%>';
		parent.fraInterface.fm.all("FilePath1").value = '<%=FilePath1%>';
		parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	</script>
</html>
