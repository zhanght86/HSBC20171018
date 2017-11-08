<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�GrpCustomerDiskForSimpleAllSave.jsp
//�����ܣ�
//�������ڣ�2006-01-18 17:25:18
//�� �� �ˣ�wanzh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
		int count=0;
		String path = application.getRealPath("").replace('\\','/');
		if(!path.endsWith("/"))
		{
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
		
		loggerDebug("GrpCustomerDiskForSimpleAllSave","Path:"+path);
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
		    loggerDebug("GrpCustomerDiskForSimpleAllSave","�ϴ�·��:"+ImportPath);
		  }
		  //�������������ļ�������б���Ϣ
		  if (!item.isFormField())
		  {
		    String name = item.getName();
		    long size = item.getSize();
		    if((name==null||name.equals("")) && size==0)
		      continue;
		    ImportPath= path + "temp/";
		    FileName = name.replace('\\','/');
		    FileName = FileName.substring(FileName.lastIndexOf("/") + 1);
		    //�����ϴ����ļ���ָ����Ŀ¼
		    try
		    {
		      item.write(new File(ImportPath + FileName));
		      count = 1;
		    }
		    catch(Exception e)
		    {
		      loggerDebug("GrpCustomerDiskForSimpleAllSave","upload file error ...");
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
		boolean res = true;
		
		//GrpClaimSimpleGuideIn tGrpClaimSimpleGuideIn   = new GrpClaimSimpleGuideIn();
		String busiName="grpGrpClaimSimpleGuideIn";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if (count >0)
		{
			  GlobalInput tG = new GlobalInput();
			  tG=(GlobalInput)session.getValue("GI");
			  // ׼���������� VData
			  VData tVData = new VData();
			  FlagStr="";
			  tTransferData.setNameAndValue("FileName",FileName);
			  tTransferData.setNameAndValue("FilePath", path);
			  tVData.add(tTransferData);
			  tVData.add(tG);
			  try
			  {
			      //res= tGrpClaimSimpleGuideIn.submitData(tVData,"");
			      if(!tBusinessDelegate.submitData(tVData,"",busiName))
					{    
					    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
					    { 
							Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
							FlagStr = "Fail";
						}
						else
						{
							Content = "����ʧ��";
							FlagStr = "Fail";				
						}
						res = false;
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
			
			String errMess = "";
			
			if (res)
			{
				  Content = " �ύ�ɹ�! ";
				  FlagStr = "Succ";
				  loggerDebug("GrpCustomerDiskForSimpleAllSave","---aaa");
				  //if (tGrpClaimSimpleGuideIn.getResult()!=null)
				  if (tBusinessDelegate.getResult()!=null)
				  {
					    try
					    {
//						      if(tGrpClaimSimpleGuideIn.getResult().get(0)!=null&&tGrpClaimSimpleGuideIn.getResult().size()>0)
//						      {
//						        Result = (String)tGrpClaimSimpleGuideIn.getResult().get(0);
//						      }
						      if(tBusinessDelegate.getResult().get(0)!=null&&tBusinessDelegate.getResult().size()>0)
						      {
						        Result = (String)tBusinessDelegate.getResult().get(0);
						      }
					    }
					    catch(Exception e)
					    {
					    }
				  }
		}
		else
		{
			  //tError = tGrpClaimSimpleGuideIn.mErrors;
			   tError = tBusinessDelegate.getCErrors();
			  for (int i=0;i<tError.getErrorCount();i++)
			  {
			    errMess+= tError.getError(i).errorMessage ;
			  }
			  loggerDebug("GrpCustomerDiskForSimpleAllSave","---ccc");
			  Content = " ����ʧ�ܣ�ԭ����:" + errMess;
			  FlagStr = "Fail";
		}
		loggerDebug("GrpCustomerDiskForSimpleAllSave","---bbb = "+FlagStr);
		//��Ӹ���Ԥ����
		loggerDebug("GrpCustomerDiskForSimpleAllSave","---Result = "+Result);
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=Result%>");
</script>
</html>
