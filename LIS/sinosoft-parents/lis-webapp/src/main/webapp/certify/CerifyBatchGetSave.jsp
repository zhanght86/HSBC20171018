<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�CerifyBatchGetSave
//�����ܣ���֤�����������Ϲ���
//�������ڣ�2009-08-10 09:25:18
//������  ��  zhangzheng
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.certify.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="com.sinosoft.service.*" %>
<%

	//���ѳ���Ų����������ʱ�����·��������Ҫ�ģ�Ҫ�÷�б��
	String uploadPath=request.getParameter("ImportPath"); //�ϴ�·��
	String stateFlag=request.getParameter("CertifyState"); //��֤״̬
	
	loggerDebug("CerifyBatchGetSave","�ϴ�·��:"+uploadPath);
	String tempPath="temp\\"; //�����ʱ�ļ���·��
	/*String path = application.getRealPath("").replace('\\','/');
	if(!path.endsWith("/")){
		path += "/";
	}*/
	String path ="";
	//String saveExcelPath="excel\\"; //�ļ���ʱ���·��
	String saveExcelPath=""; //�ļ���ʱ���·��
	String FileName=""; //�ϴ����ļ���
	String ImportPath = "";
	

	int count=0;

	DiskFileUpload fu = new DiskFileUpload();
	// ���������û��ϴ��ļ���С,��λ:�ֽ�
	fu.setSizeMax(10000000);
	// maximum size that will be stored in memory?
	// �������ֻ�������ڴ��д洢������,��λ:�ֽ�
	fu.setSizeThreshold(4096);
	
	// ����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
	//���ѳ���Ų����������ʱ�����·��������Ҫ�ģ�Ҫ�÷�б��
	//fu.setRepositoryPath(uploadPath+tempPath);
	//fu.setRepositoryPath(path+"/temp");
	//loggerDebug("CerifyBatchGetSave","�ϴ���ʱ�ļ�����·��:"+uploadPath+tempPath);
	//loggerDebug("CerifyBatchGetSave","�ϴ���ʱ�ļ�����·��:"+path+"/temp");
	
	//��ʼ��ȡ�ϴ���Ϣ
	List fileItems = fu.parseRequest(request);
	
	// ���δ���ÿ���ϴ����ļ�
	Iterator iter = fileItems.iterator();
	loggerDebug("CerifyBatchGetSave","iter.hasNext():"+iter.hasNext());
	
	while (iter.hasNext())
	{
	  FileItem item = (FileItem) iter.next();
	  if (item.getFieldName().compareTo("ImportPath")==0)
		{
			ImportPath = item.getString();
//			loggerDebug("CerifyBatchGetSave","�ϴ�·��:"+ImportPath);
		}
	  //�����һ����ͨ�ı�����File���,�������������ļ�������б���Ϣ
	  if (!item.isFormField())
	  {
		  String name = item.getName();
		  loggerDebug("CerifyBatchGetSave",name);
		  long size = item.getSize();
			if((name==null||name.equals("")) && size==0)
				continue;
			//ImportPath= path + "temp/";
			//FileName = name.replace('\\','/');
			//FileName = FileName.substring(FileName.lastIndexOf("/") + 1);
		  loggerDebug("CerifyBatchGetSave","item.getName():==>"+item.getName());
		  FileName=item.getName().substring(item.getName().lastIndexOf("\\") + 1);
          loggerDebug("CerifyBatchGetSave","FileName:==>"+FileName);
          //loggerDebug("CerifyBatchGetSave","FieldName:==>"+item.getFieldName());
          loggerDebug("CerifyBatchGetSave","Size:==>"+item.getSize());
          loggerDebug("CerifyBatchGetSave","�洢excel�ļ��Ļ���·��:"+uploadPath);
          //���ѳ���Ų����������ʱ�����·��������Ҫ�ģ�Ҫ��һ����б��
          loggerDebug("CerifyBatchGetSave","д�ļ���·��:"+uploadPath + saveExcelPath+FileName);

	      //if((FileName==null||FileName.equals("")) && item.getSize()==0)
	      //  continue;
	    
	      //�����ϴ����ļ���ָ����Ŀ¼
	      try
	      {
	    	  //���ѳ���Ų����������ʱ�����·��������Ҫ�ģ�Ҫ��һ����б��
		      //item.write(new File(uploadPath + saveExcelPath+FileName.substring(FileName.lastIndexOf("\\") + 1)));
		      item.write(new File(uploadPath + saveExcelPath+FileName));
			  count = 1;
	    	  count = 1;
		      loggerDebug("CerifyBatchGetSave","count:"+count);
	      }
	      catch(Exception e){
	    	
	      	  loggerDebug("CerifyBatchGetSave","upload file error ...");
	      }
	  }
	}



	//�������
	CErrors tError = null;
	CErrors tlogError = null;
	String tRela  = "";
	String FlagStr = "Fail";
	String Content = "";
	String tRptNo=""; //������
	String tAccNo=""; //�¼���
	String tsumcount="";
	String ttcount="";
	
	
	TransferData tTransferData = new TransferData();
	loggerDebug("CerifyBatchGetSave","----FileName:"+FileName);
	boolean res = true;
	
	//�������������࣬
	//CertifyBatchReportIn tCertifyBatchReportIn  = null;
	
	if (count >0)
	{
	  GlobalInput tG = new GlobalInput();
	  tG=(GlobalInput)session.getValue("GI");
	  
	  path=uploadPath + saveExcelPath+FileName;
	  
	  // ׼���������� VData
	  VData tVData = new VData();
	  tTransferData.setNameAndValue("FileName",FileName);
	  tTransferData.setNameAndValue("StateFlag", stateFlag);
	  tTransferData.setNameAndValue("SaveExcelPath", path);
	  
	  tVData.add(tTransferData);
	  tVData.add(tG);
	  
	  /*try
	  {
		    //������ģ�嵼�뵽ϵͳ��  
			tCertifyBatchReportIn = new CertifyBatchReportIn();
	    	res= tCertifyBatchReportIn.submitData(tVData,"");
	    	loggerDebug("CerifyBatchGetSave","res="+res);
	  }
	  catch(Exception ex)
	  {
	    	Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	    	FlagStr = "Fail";
	  }*/
	  String busiName="CertifyBatchReportIn";
	  String mDescType="����";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		  if(!tBusinessDelegate.submitData(tVData,"",busiName))
		  {    
		       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		       { 
					Content = mDescType+"ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
					FlagStr = "Fail";
			   }
			   else
			   {
					Content = mDescType+"ʧ��";
					FlagStr = "Fail";				
			   }
		  }
		  else
		  {
		     	Content = mDescType+"�ɹ�! ";
		      	FlagStr = "Success!";  
		  }
	}
	else
	{
	  Content = "�����ļ�ʧ��! ";
	  FlagStr = "Fail";
	}
	
	String errMess = "";
	
	/*if (res)
	{

	    try
	    {      	
		       Content = "����ɹ�";
		       FlagStr = "Success!";   
	    }
	    catch(Exception e)
	    {
	       Content = "����ʧ�ܣ�ԭ����:" + e.toString();
	       FlagStr = "Fail";    
	    }
	}
	else
	{
		  tError = tCertifyBatchReportIn.mErrors;
		  
		  for (int i=0;i<tError.getErrorCount();i++)
		  {
		    	errMess+= tError.getError(i).errorMessage ;
		  }
		  
	      loggerDebug("CerifyBatchGetSave","---ccc");
	      Content = " ����ʧ�ܣ����ѯʧ��ԭ��:"+errMess;
	      FlagStr = "Fail";

	}*/
	loggerDebug("CerifyBatchGetSave","---FlagStr = "+FlagStr);
	loggerDebug("CerifyBatchGetSave","---Content = "+Content);
	//��Ӹ���Ԥ����
	loggerDebug("CerifyBatchGetSave","---Result = "+tRptNo);
	%>
	<html>
	<script language="javascript">
	parent.fraInterface.afterSubmitDiskInput("<%=FlagStr%>","<%=PubFun.changForJavaScript(Content.replaceAll("\\\\","/"))%>","<%=tRptNo%>");
	</script>
	</html>
