<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�GrpCustomerDiskReportSave.jsp
//�����ܣ�������������˹���
//�������ڣ�2008-11-03 09:25:18
//������  ��  zhangzheng
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="com.sinosoft.service.*" %>
<%

	//****���ѳ���Ų����������ʱ�����·��������Ҫ�ģ�Ҫ�÷�б��
	String uploadPath=request.getParameter("ImportPath"); //�ϴ�·��
	loggerDebug("GrpCustomerDiskReportSave","�ϴ�·��:"+uploadPath);
	String tempPath="temp\\"; //�����ʱ�ļ���·��
	String path = application.getRealPath("").replace('\\','/');
	if(!path.endsWith("/")){
		path += "/";
	}
	//String saveExcelPath="excel\\"; //�ļ���ʱ���·��
	String saveExcelPath=""; //�ļ���ʱ���·��
	String FileName=""; //�ϴ����ļ���
	String ImportPath = "";

	//���������¼��ı�����Ϣ
	LLReportSchema tLLReportSchema=new LLReportSchema();
	tLLReportSchema.setRptNo(request.getParameter("RptNo")); //������
	tLLReportSchema.setRgtObjNo(tLLReportSchema.getRptNo()); //��������
	tLLReportSchema.setRptMode(StrTool.unicodeToGBK(request.getParameter("RptMode"))); //������ʽ
	tLLReportSchema.setRptorName(StrTool.unicodeToGBK(request.getParameter("RptorName"))); //����������
	tLLReportSchema.setRptorAddress(StrTool.unicodeToGBK(request.getParameter("RptorAddress"))); //��������ϵ��ַ
	tLLReportSchema.setRptorPhone(StrTool.unicodeToGBK(request.getParameter("RptorPhone"))); //�����˵绰
	tLLReportSchema.setAccidentDate(request.getParameter("AccidentDate")); //�¹�����
	tLLReportSchema.setAccidentReason(request.getParameter("AccidentReason")); //����ԭ��
	tLLReportSchema.setGrpContNo(request.getParameter("GrpContNo")); //�����ͬ��
	tLLReportSchema.setAppntNo(request.getParameter("GrpCustomerNo")); //Ͷ���˿ͻ���
	tLLReportSchema.setGrpName(StrTool.unicodeToGBK(request.getParameter("GrpName"))); //Ͷ��������
	tLLReportSchema.setRiskCode(request.getParameter("RiskCode")); //���ִ���
	tLLReportSchema.setRgtState(request.getParameter("rgtstate")); //�������� 
    tLLReportSchema.setPeoples2(request.getParameter("Peoples")); //Ͷ��������
	tLLReportSchema.setRelation(request.getParameter("Relation"));
    tLLReportSchema.setRgtFlag("10"); //������־Ϊ10��ʾδ����
    tLLReportSchema.setRgtClass("2"); //�����ŵ���־ 1:����,2:�ŵ�
	

	int count=0;

	DiskFileUpload fu = new DiskFileUpload();
	// ���������û��ϴ��ļ���С,��λ:�ֽ�
	fu.setSizeMax(10000000);
	// maximum size that will be stored in memory?
	// �������ֻ�������ڴ��д洢������,��λ:�ֽ�
	fu.setSizeThreshold(4096);
	
	// ����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
	//****���ѳ���Ų����������ʱ�����·��������Ҫ�ģ�Ҫ�÷�б��
	//fu.setRepositoryPath(uploadPath+tempPath);
	fu.setRepositoryPath(path+"/temp");
	//loggerDebug("GrpCustomerDiskReportSave","�ϴ���ʱ�ļ�����·��:"+uploadPath+tempPath);
	loggerDebug("GrpCustomerDiskReportSave","�ϴ���ʱ�ļ�����·��:"+path+"/temp");
	
	//��ʼ��ȡ�ϴ���Ϣ
	List fileItems = fu.parseRequest(request);
	
	// ���δ���ÿ���ϴ����ļ�
	Iterator iter = fileItems.iterator();
	loggerDebug("GrpCustomerDiskReportSave","iter.hasNext():"+iter.hasNext());
	
	while (iter.hasNext())
	{
	  FileItem item = (FileItem) iter.next();
	  if (item.getFieldName().compareTo("ImportPath")==0)
		{
			ImportPath = item.getString();
//			loggerDebug("GrpCustomerDiskReportSave","�ϴ�·��:"+ImportPath);
		}
	  //�����һ����ͨ�ı�����File���,�������������ļ�������б���Ϣ
	  if (!item.isFormField())
	  {
		  String name = item.getName();
		  loggerDebug("GrpCustomerDiskReportSave",name);
		  long size = item.getSize();
			if((name==null||name.equals("")) && size==0)
				continue;
			ImportPath= path + "temp/";
			FileName = name.replace('\\','/');
			FileName = FileName.substring(FileName.lastIndexOf("/") + 1);
		  loggerDebug("GrpCustomerDiskReportSave","item.getName():==>"+item.getName());
		  //FileName=item.getName().substring(item.getName().lastIndexOf("\\") + 1);
          loggerDebug("GrpCustomerDiskReportSave","FileName:==>"+FileName);
          //loggerDebug("GrpCustomerDiskReportSave","FieldName:==>"+item.getFieldName());
          loggerDebug("GrpCustomerDiskReportSave","Size:==>"+item.getSize());
          loggerDebug("GrpCustomerDiskReportSave","�洢excel�ļ��Ļ���·��:"+ImportPath);
          //****���ѳ���Ų����������ʱ�����·��������Ҫ�ģ�Ҫ��һ����б��
          loggerDebug("GrpCustomerDiskReportSave","д�ļ���·��:"+uploadPath + saveExcelPath+FileName);

	      //if((FileName==null||FileName.equals("")) && item.getSize()==0)
	      //  continue;
	    
	      //�����ϴ����ļ���ָ����Ŀ¼
	      try
	      {
	    	  //****���ѳ���Ų����������ʱ�����·��������Ҫ�ģ�Ҫ��һ����б��
		      //item.write(new File(uploadPath + saveExcelPath+FileName.substring(FileName.lastIndexOf("\\") + 1)));
		      item.write(new File(ImportPath + FileName));
			  count = 1;
	    	  count = 1;
		      loggerDebug("GrpCustomerDiskReportSave","count:"+count);
	      }
	      catch(Exception e){
	    	
	      	  loggerDebug("GrpCustomerDiskReportSave","upload file error ...");
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
	loggerDebug("GrpCustomerDiskReportSave","----FileName:"+FileName);
	boolean res = true;
	
	//�������������࣬
	//GrpCustomerBatchReportIn tGrpCustomerBatchReportIn   = null;
	String busiName="grpGrpCustomerBatchReportIn";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	if (count >0)
	{
	  GlobalInput tG = new GlobalInput();
	  tG=(GlobalInput)session.getValue("GI");
	  
	  // ׼���������� VData
	  VData tVData = new VData();
	  tVData.add(tLLReportSchema);
	  tTransferData.setNameAndValue("FileName",FileName);
	  tTransferData.setNameAndValue("SaveExcelPath", path);
	  tTransferData.setNameAndValue("GrpCustomerNo", request.getParameter("GrpCustomerNo"));
	  tTransferData.setNameAndValue("GrpName", StrTool.unicodeToGBK(request.getParameter("GrpName")));
	  
	  tVData.add(tTransferData);
	  tVData.add(tG);
	  
	  try
	  {
		    //������ģ�嵼�뵽ϵͳ��  
//			tGrpCustomerBatchReportIn = new GrpCustomerBatchReportIn();
//	    	res= tGrpCustomerBatchReportIn.submitData(tVData,"");
//	    	loggerDebug("GrpCustomerDiskReportSave","res="+res);
		if(!tBusinessDelegate.submitData(tVData,"",busiName))
		{    
		    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		    { 
				Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
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

	 // if (tGrpCustomerBatchReportIn.getResult()!=null)
	  if (tBusinessDelegate.getResult()!=null)
	  {
	    try
	    {
	      	//if(tGrpCustomerBatchReportIn.getResult().get(0)!=null&&tGrpCustomerBatchReportIn.getResult().size()>0)
	      	if(tBusinessDelegate.getResult().get(0)!=null&&tBusinessDelegate.getResult().size()>0)
	      	{
	    	  	//tRptNo = (String)tGrpCustomerBatchReportIn.getResult().get(0);
	    	  	//tAccNo = (String)tGrpCustomerBatchReportIn.getResult().get(1);
	    	  	
	    	  	tRptNo = (String)tBusinessDelegate.getResult().get(0);
	    	  	tAccNo = (String)tBusinessDelegate.getResult().get(1);
	    	  	
	        	loggerDebug("GrpCustomerDiskReportSave","tRptNo="+tRptNo);
	        	loggerDebug("GrpCustomerDiskReportSave","tAccNo="+tAccNo);
	        	
	        	LWMissionDB tLWMissionDB = new LWMissionDB();
	        	LWMissionSchema tLWMissionSchema = new LWMissionSchema();
	        	String sql = "select * from LWMission where"
	                   + " activityid = '0000009005' "
	                   + " and processid = '0000000009'"
	                   + " and  missionprop1 = '"+tRptNo+"'";
	        	loggerDebug("GrpCustomerDiskReportSave","Start query mission:" + sql);
	        	LWMissionSet tLWMissionSet = tLWMissionDB.executeQuery(sql);
	        	if (tLWMissionSet == null && tLWMissionSet.size() == 0)
	        	{
		            Content = "��ѯ�������ڵ�ʧ��!" ;
		            FlagStr = "Fail";
		        	}
		        else
		        {            
		       
		            tLWMissionSchema = tLWMissionSet.get(1);            
		            String MissionID = tLWMissionSchema.getMissionID();
		            String SubMissionID = tLWMissionSchema.getSubMissionID();
		            loggerDebug("GrpCustomerDiskReportSave","---�����½��������ڵ�="+MissionID); 
		        }      
	        }
	      	
		       Content = "����ɹ�";
		       FlagStr = "Success!";   
	     
	%>
	<script language="javascript">
	    parent.fraInterface.fm.all("ClmNo").value="<%=tRptNo%>";
	    parent.fraInterface.fm.all("AccNo").value="<%=tAccNo%>";
	</script>
	<%
	    }
	    catch(Exception e)
	    {
	       Content = "����ʧ�ܣ�ԭ����:" + e.toString();
	       FlagStr = "Fail";    
	    }
	  }
	}
	else
	{
		  //tError = tGrpCustomerBatchReportIn.mErrors;
		  tError = tBusinessDelegate.getCErrors();
		  
		  for (int i=0;i<tError.getErrorCount();i++)
		  {
		    	errMess+= tError.getError(i).errorMessage ;
		  		//tGrpCustomerBatchReportIn.LogError("00000000000000000000",FileName,i+1,)
		  }
	      loggerDebug("GrpCustomerDiskReportSave","---ccc");
	      Content = " ����ʧ�ܣ����ѯʧ��ԭ��";
	      FlagStr = "Fail";

	}
	loggerDebug("GrpCustomerDiskReportSave","---FlagStr = "+FlagStr);
	loggerDebug("GrpCustomerDiskReportSave","---Content = "+Content);
	//��Ӹ���Ԥ����
	loggerDebug("GrpCustomerDiskReportSave","---Result = "+tRptNo);
	%>
	<html>
	<script language="javascript">
	parent.fraInterface.afterSubmitDiskInput("<%=FlagStr%>","<%=PubFun.changForJavaScript(Content.replaceAll("\\\\","/"))%>","<%=tRptNo%>");
	</script>
	</html>
