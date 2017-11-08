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
//Ӧ�ø�·��
String tPath = application.getRealPath("").replace('\\','/');
if(!tPath.endsWith("/"))
{
	tPath += "/";
}
loggerDebug("LPrtTempleteSave","Ӧ��·��"+tPath);
File tFile = new File(tPath+"/temp");
if (!tFile.exists())
{
	tFile.mkdir();
}

DiskFileUpload fu = new DiskFileUpload();
// ���������û��ϴ��ļ���С,��λ:�ֽ�
fu.setSizeMax(10000000);
// maximum size that will be stored in memory?
// �������ֻ�������ڴ��д洢������,��λ:�ֽ�
fu.setSizeThreshold(4096);
// ����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
fu.setRepositoryPath(tPath+"/temp");
//��ʼ��ȡ�ϴ���Ϣ
List fileItems = fu.parseRequest(request);
// ���δ���ÿ���ϴ����ļ�
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
	//�������������ļ�������б���Ϣ
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
				    loggerDebug("LPrtTempleteSave","�ϴ��ļ�Ϊ�գ�");
				    continue;
				}
				//�ϴ����ļ�·��
				FilePath= tPath + FilePath +"/"+tTempleteTypeName;
				//����ļ���׺��
				FileLastName = tClientFilePath.substring(tClientFilePath.lastIndexOf(".")+1);
				//�ϴ��ļ���
//				FileName = tPrintID + tLanguage + tOutputTypeName +"."+ FileLastName;
				//��xlsΪģ�壬���pdf�ļ�ʱ��LPrtTempleteBL�Ҳ����ļ��������޸�20120608 by huangliang
				FileName = tPrintID + tLanguage + tTempleteTypeName +"."+ FileLastName;
				loggerDebug("LPrtTempleteSave","FileName=" + FileName);
				loggerDebug("LPrtTempleteSave","FilePath=" + FilePath);
				//�����ϴ����ļ���ָ����Ŀ¼
				item.write(new File(FilePath,FileName));
				count = 1;
			}
			else 
			{
				loggerDebug("LPrtTempleteSave","ɾ��ģ�壡");
			}
		}
		catch(Exception e)
		{
			count = -1;
			loggerDebug("LPrtTempleteSave","�ļ��ϴ�����");
			FlagStr="Fail";
			Content="�ļ��ϴ�����";
		}
	}
}

//���ݿ������ļ�·
FilePath ="print/templete/" +tTempleteTypeName;
tLPrtTempleteSchema.setFilePath(FilePath+"/"+FileName);
tTransferData.setNameAndValue("Path",tPath);
tTransferData.setNameAndValue("OutputTypeName",tOutputTypeName);
tTransferData.setNameAndValue("TempleteTypeName",tTempleteTypeName);
//�����ļ�ʧ��ʱ count = -1 
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
			Content = "����ʧ�ܣ�ԭ����" + tBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
		}
		else
		{
			Content = "����ʧ��";
			FlagStr = "Fail";
		}	
	}
	else
	{
		if(transact.equals("DELETE||MAIN"))
		{
			Content = " ɾ���ɹ�!";
			FlagStr = "Fail";
		}
		else
		{
			if(transact.equals("INSERT||MAIN"))
			{
				Content = " ����ɹ�! ";
			}
			if(transact.equals("UPDATE||MAIN"))
			{
				Content = " �޸ĳɹ�! ";
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
