<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
/*******************************************************************************
* <p>Title: ��ȫ-�ŵ����̵���</p>
* <p>Description: �ŵ����̵���js�ļ�</p>
* <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
* <p>Company: �п���Ƽ��ɷ����޹�˾</p>
* <p>WebSite: http://www.sinosoft.com.cn</p>
*
* @todo     : ��ȫ-�ŵ����̵���
* @author   : zhangtao
* @version  : 1.00 
* @date     : 2006-11-24
* @modify   : 2006-11-25
******************************************************************************/
%>
<!--�û�У����-->
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

//��ȡ�������
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
// ���������û��ϴ��ļ���С,��λ:�ֽ�
fu.setSizeMax(10000000);
// maximum size that will be stored in memory?
// �������ֻ�������ڴ��д洢������,��λ:�ֽ�
fu.setSizeThreshold(4096);
// ����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
fu.setRepositoryPath(path+"/temp");
//��ʼ��ȡ�ϴ���Ϣ
List fileItems = fu.parseRequest(request);

//loggerDebug("GEdorDiskImportSave",path);
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
		loggerDebug("GEdorDiskImportSave","�ϴ�·��:"+ImportPath);
	}
	//�������������ļ�������б���Ϣ
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

		//�����ϴ����ļ���ָ����Ŀ¼
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

//�������
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
	Content = "�����ļ�ʧ��! ";
	FlagStr = "Fail";
}
else 
{
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");
	// ׼���������� VData
	VData tVData = new VData();
	FlagStr="";
	tTransferData.setNameAndValue("FileName",FileName);
	tTransferData.setNameAndValue("FilePath", path);
	loggerDebug("GEdorDiskImportSave","========FileName==" + FileName);
    loggerDebug("GEdorDiskImportSave","========FilePath==" + path);
  
	try
	{ //���˵�������Լ�����嵥���� add by zhangtao 2006-11-23
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
		{ //���˵�������Լ�����嵥���� add by zhangtao 2006-11-23
			res= tParseGuideInUI.submitData(tVData,"");
		}
		else
		{
			res= tEdorDiskImport.submitData(tVData,"");
		}

	}
	catch(Exception ex)
	{
		Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
		FlagStr = "Fail";
	}
	
	if ("".equals(FlagStr))
	{
				if (sEdorType.equals("NI"))
				{ //���˵�������Լ�����嵥���� add by zhangtao 2006-11-23
					tError = tParseGuideInUI.mErrors;
				}
				else
				{
					tError = tEdorDiskImport.mErrors;
				}
		    
		    if (!tError.needDealError())
		    {                          
				  Content ="������ϣ���鿴�����嵥������־��";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
	} 
}

%>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=Result%>");
</script>
