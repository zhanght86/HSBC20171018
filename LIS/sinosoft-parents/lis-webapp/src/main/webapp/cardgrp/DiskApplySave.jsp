<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�ProposalCopyInput.jsp
//�����ܣ�
//�������ڣ�2002-08-21 09:25:18
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
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
// ���������û��ϴ��ļ���С,��λ:�ֽ�
//fu.setSizeMax(10000000);
// maximum size that will be stored in memory?
// �������ֻ�������ڴ��д洢������,��λ:�ֽ�
//fu.setSizeThreshold(4096);
// ����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
fu.setRepositoryPath(path+"/temp");
//��ʼ��ȡ�ϴ���Ϣ
List fileItems = fu.parseRequest(request);

//loggerDebug("DiskApplySave",path);
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
//		loggerDebug("DiskApplySave","�ϴ�·��:"+ImportPath);
	}
	//�������������ļ�������б���Ϣ
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
		//�����ϴ����ļ���ָ����Ŀ¼
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

//�������
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
	// ׼���������� VData
	VData tVData = new VData();
	FlagStr="";
	//��ȫ���˱��==2005-5-11==update=====================
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
	Content = " �ύ�ɹ�! ";
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
	Content = " ����ʧ�ܣ�ԭ����:\\n " + errMess;
	FlagStr = "Fail";
}
loggerDebug("DiskApplySave","---bbb"+FlagStr);
//��Ӹ���Ԥ����
%>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=Result%>");
</script>
