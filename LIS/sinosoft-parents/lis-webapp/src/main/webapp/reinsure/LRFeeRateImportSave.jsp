<%@include file="/i18n/language.jsp"%>
<%
//�������ƣ�LRFeeRateImportSave.jsp
//�����ܣ�
//�������ڣ�2008-1-8
//������  ��JavaBean���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page contentType="text/html;charset=GBK" %>

<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.reinsure.*"%>
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
	 	String tBatchNo = request.getParameter("BatchNo");
	 	String tFeeTableNo = request.getParameter("FeeTableNo");
	 	
		System.out.println("ImportPath: "+ImportPath);
		System.out.println("Path: "+path);
		System.out.println("BatchNo: "+tBatchNo);
		System.out.println("FeeTableNo: "+tFeeTableNo);
	
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("BatchNo",tBatchNo);
		tTransferData.setNameAndValue("FeeTableNo",tFeeTableNo);
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
catch(Exception ex)
{
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
    System.out.println("-----------importpath:   "+FileName);
    //�����ϴ����ļ���ָ����Ŀ¼
    
    try {
      item.write(new File(ImportPath + FileName));
      count = 1;
       System.out.println("count="+ count);
    } catch(Exception e) {
      System.out.println("upload file error ...");
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
tG=(GlobalInput)session.getAttribute("GI");
//�õ����ݵ����ļ�
LRFeeRateImport tLRFeeRateImport = new LRFeeRateImport(tG);
if (count >0)
{
  
  try
  {
	 	if(!tLRFeeRateImport.doAdd(ImportPath, FileName,tVData))
	  {
	  	String errMess = ""; 
			tError = tLRFeeRateImport.mErrors;
			if(tError.getErrorCount()!=0)
			{
				for (int i=0;i<tError.getErrorCount();i++)
				{
					errMess+= tError.getError(i).errorMessage ;
				}				  
			  	Content = " "+"����ʧ�ܣ�ԭ����:"+"" + errMess;
			   	FlagStr = "Fail";			  
			}
	  }else{
		    Content = " "+"������ʱ�ɹ�������"+"" ;
		   	FlagStr = "SUCC";
		}
  }
  catch(Exception ex)
  {
    Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
    FlagStr = "Fail";
  }
  
}
else
{
	Content = ""+"�����ļ�ʧ��!"+" ";
  FlagStr = "Fail";
}
  //��Ӹ���Ԥ����


%>                      
<html>
<script type="text/javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>


