<%
//�������ƣ�LRFeeRateImportSave.jsp
//�����ܣ�
//�������ڣ�2008-1-8
//������  ��JavaBean���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.finfee.*"%>
  <%@page import="java.util.*"%>
	<%@page import="java.io.*"%>
	<%@page import="org.apache.commons.fileupload.*"%>
<%
	String FileName = "";
	String filePath = "";
	int count = 0;
	
	 //�õ�excel�ļ��ı���·��
	 	String ImportPath = request.getParameter("ImportPath");		
	 	String path = application.getRealPath("").replace('\\','/')+'/';
	 	
		loggerDebug("TempFinFeeImportSave","ImportPath: "+ImportPath);
		loggerDebug("TempFinFeeImportSave","Path: "+path);
		
		TransferData tTransferData = new TransferData();
		VData tVData = new VData();
		tVData.add(tTransferData);
		
DiskFileUpload fu = new DiskFileUpload();
// ���������û��ϴ��ļ���С,��λ:�ֽ�
fu.setSizeMax(10000000);
// maximum size that will be stored in memory?
// �������ֻ�������ڴ��д洢������,��λ:�ֽ�
fu.setSizeThreshold(4096);
// ����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
fu.setRepositoryPath(path+"temp");
//��ʼ��ȡ�ϴ���Ϣ

List fileItems = null;
try{
 fileItems = fu.parseRequest(request);
}
catch(Exception ex){
	ex.printStackTrace();
}

// ���δ���ÿ���ϴ����ļ�
Iterator iter = fileItems.iterator();
while (iter.hasNext()) {
  FileItem item = (FileItem) iter.next();
  //�������������ļ�������б���Ϣ
  if (!item.isFormField()) {
    String name = item.getName(); 
    long size = item.getSize();
    if((name==null||name.equals("")) && size==0)
      continue;
    ImportPath= path + ImportPath;
    
    FileName = name.substring(name.lastIndexOf("\\") + 1);
    
    loggerDebug("TempFinFeeImportSave","-----------ImportPath:   "+FileName);
    loggerDebug("TempFinFeeImportSave","-----------ImportPath:   "+FileName);
    //�����ϴ����ļ���ָ����Ŀ¼
    
    
    try {
      item.write(new File(ImportPath + FileName));
      count = 1;
      loggerDebug("TempFinFeeImportSave","count="+ count);
    } catch(Exception e) {
      loggerDebug("TempFinFeeImportSave","upload file error ...");
    }
  }
}

//�������
CErrors tError = null;
String tRela  = "";                
String FlagStr = "Fail";
String Content = "";
String Result="";
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");
//�õ����ݵ����ļ�
LJTempFeeImport tLJTempFeeImport = new LJTempFeeImport(tG);
if (count >0)
{
  try
  {
	 	if(!tLJTempFeeImport.doAdd(ImportPath, FileName,tVData))
	  {
	  	String errMess = ""; 
			tError = tLJTempFeeImport.mErrors;
			if(tError.getErrorCount()!=0)
			{
				for (int i=0;i<tError.getErrorCount();i++)
				{
					errMess+= tError.getError(i).errorMessage ;
					
					
				}	
				 loggerDebug("TempFinFeeImportSave","��������"+Content);
			  	Content = " ����ʧ�ܣ�ԭ����:" + errMess;
			   	FlagStr = "Fail";			  
			}
	  }else{
		    Content = " �������ձ�ɹ�������" ;
		   	FlagStr = "SUCC";
		}
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
  //��Ӹ���Ԥ����

%>                      
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
