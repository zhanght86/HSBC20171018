<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�LDRiskToRateTableSave.jsp
//�������ڣ�2012-09-19 15:13:22
//������  ��Guxin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="com.sinosoft.service.*"%>
<%
 //������Ϣ������У�鴦��
 //�������

 String FlagStr = "";
 String Content = "";
 
 String targetPath = "";
 String excelSavePath = "select sysvarvalue from LDSYSVAR where sysvar = 'ImportExcelSavePath'";
 ExeSQL exec = new ExeSQL();
 
 String TableName = request.getParameter("TableName");
 String Remark = request.getParameter("Remark");
 String RiskCode = request.getParameter("RiskCode");
 String RiskName = request.getParameter("RiskName");
 String RateType = request.getParameter("RateType");
 //Remark = new String(Remark.getBytes("ISO8859-1"),"GBK");
 //RiskName = new String(RiskName.getBytes("ISO8859-1"),"GBK");
 System.out.println("RiskName:"+RiskName);
 System.out.println("Remark:"+Remark);
 String relativePath = exec.getOneValue(excelSavePath);
 String appPath = application.getRealPath("/").replace('\\', '/');
 if(appPath.endsWith("/"))
 targetPath = appPath + relativePath;
 else
  targetPath = appPath +"/"+ relativePath;
 
 String FileName = "";
 String operator = request.getParameter("ImportType");
 System.out.println("operator:"+operator);
	 try
	 {
			 if(!targetPath.endsWith("/"))
			 {
				 targetPath += "/";
			 }
			 DiskFileUpload fu = new DiskFileUpload();// ���������û��ϴ��ļ���С,��λ:�ֽ�
			 fu.setSizeMax(50000000);
			// maximum size that will be stored in memory?
			// �������ֻ�������ڴ��д洢������,��λ:�ֽ�
			 fu.setSizeThreshold(409600);
			// ����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
			 fu.setRepositoryPath(targetPath);
			
			 // ��ʼ��ȡ�ϴ���Ϣ
			 System.out.println("sdfdfsd "+request.getHeader("Content-type"));
			 List fileItems = fu.parseRequest(request);
			
			//  ���δ���ÿ���ϴ����ļ�
			 Iterator iter = fileItems.iterator();
			
			 if (iter.hasNext())
			 {
			 	FileItem item = (FileItem) iter.next(); 
			 	
			 	if (!item.isFormField())
			 	{
			 		String name = item.getName();
			 		
			 		FileName = name.replace('\\','/');
			 		FileName = FileName.substring(FileName.lastIndexOf("/") + 1);
			 		
			 		//�����ϴ����ļ���ָ����Ŀ¼
			 		try
			 		{
				       File tFile = new File(targetPath);
				       if(!tFile.exists())
				       {
				        
				         //tFile = new File(targetPath.replace('/','\\'));
				         boolean b=tFile.mkdirs();
				         if(!b){
				         tFile = null;
				         tFile = new File(targetPath.replace('/','\\'));
				         tFile.mkdirs();
				         }
				       }
				      // System.out.println("FilePath=============="+targetPath +"/"+FileName);					   
				       item.write(new File(targetPath + FileName) ) ;					   
				       Content = targetPath + FileName;
				      System.out.println("UPLOAD FILEPATH:"+Content);
			 		}
			 		catch(Exception e)
			 		{
			 			FlagStr = "Fail";
			 			Content = ""+"�ϴ��ļ�ʧ�ܣ�"+"";
			         	e.printStackTrace();
			 		}
			 	}
		 }
			 
			// deal excel
			if(!FlagStr.equals("Fail"))
			{
			 TransferData transferData = new TransferData();
			 GlobalInput tG = new GlobalInput(); 
			 tG=(GlobalInput)session.getAttribute("GI");
			 
			 transferData.setNameAndValue("Remark",Remark);
			 transferData.setNameAndValue("TableName",TableName);
			 transferData.setNameAndValue("RiskCode",RiskCode);
			 transferData.setNameAndValue("RiskName",RiskName);
			 transferData.setNameAndValue("RateType",RateType);
			 transferData.setNameAndValue("FileName",FileName);
			 transferData.setNameAndValue("targetFileName",Content);
			 
			 try
			 {
			  // ׼���������� VData
			  VData tVData = new VData();
			  tVData.add(tG);
			  tVData.add(transferData);
			   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  if(tBusinessDelegate.submitData(tVData, operator, "RateImportExcelBL"))
			  {
				  Content=""+"����ɹ�"+"";
			  }
			  else
			  {
				  FlagStr = "Fail";
				  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + tBusinessDelegate.getCErrors().getLastError();
			  }
			 }
			 catch(Exception ex)
			 {
			  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
			  FlagStr = "Fail";
			 }
			}
	 }
	 catch(Exception ex)
	 {
		 FlagStr = "Fail";
		 Content = ""+"����ʧ�ܣ�"+"";
		 ex.printStackTrace();
	 }
%>                      

<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

