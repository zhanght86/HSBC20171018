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
// ���������û��ϴ��ļ���С,��λ:�ֽ�
fu.setSizeMax(10000000);
// maximum size that will be stored in memory?
// �������ֻ�������ڴ��д洢������,��λ:�ֽ�
fu.setSizeThreshold(4096);
// ����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
fu.setRepositoryPath(path+"/temp");
//��ʼ��ȡ�ϴ���Ϣ
List fileItems = fu.parseRequest(request);

//loggerDebug("DiskApplySave",path);


// ���δ���ÿ���ϴ����ļ�
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
	//�������������ļ�������б���Ϣ
	if (!item.isFormField())
	{
		String name = item.getName();
		loggerDebug("DiskApplySave","name=" + name);
		long size = item.getSize();
		if((name==null||name.equals("")) && size==0)
			continue;
		ImportPath= path + ImportPath;
		loggerDebug("DiskApplySave","�ϴ�·��ImportPath:"+ImportPath);
		//ImportPath= path + "temp/";
		FileName = name.replace('\\','/');
		FileName = FileName.substring(FileName.lastIndexOf("/") + 1);
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
boolean res = false;

GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");
// ׼���������� VData
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
    	Content = "�����ļ�ʧ��! ";
    	FlagStr = "Fail";
    }
    
    String errMess = "";
    
    if (res)
    {
    	Content = " �ύ�ɹ�! ";
    	if (tBusinessDelegate.getCErrors().needDealError()){
    	    Content = Content + "��ʾ��" + tBusinessDelegate.getCErrors().getLastError();
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
    	Content = "�����ļ�ʧ��! ";
    	FlagStr = "Fail";
    }
    
    String errMess = "";      
    
    
    if (res)
    {
    	Content = " �ύ�ɹ�! ";
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
//��Ӹ���Ԥ����
%>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","","<%=tBatchNo%>");
</script>
