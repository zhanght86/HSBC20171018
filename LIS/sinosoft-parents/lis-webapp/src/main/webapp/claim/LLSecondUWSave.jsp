<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLSecondUWSave.jsp
//�����ܣ���ͬ���˹��˱�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
  //�������
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");
	if(tG == null)
	{
		loggerDebug("LLSecondUWSave","session has expired");
		return;
	}
	
	String tCaseNo = request.getParameter("CaseNo"); //�ⰸ��
	String tInsuredNo = request.getParameter("InsuredNo"); //�����˿ͻ���
	String tSendUW[] = request.getParameterValues("LCContGridChk");
	String tContNo[] = request.getParameterValues("LCContGrid1"); //��ͬ��
	String tAppntNo[] = request.getParameterValues("LCContGrid2"); //Ͷ���˿ͻ�����
	String tAppntName[] = request.getParameterValues("LCContGrid3"); //Ͷ��������
	String tClaimRelFlag[] = request.getParameterValues("LCContGrid7"); //�ⰸ��ر�־
	String tHealthImpartNo1[] = request.getParameterValues("LCContGrid8"); //Ͷ���齡����֪��ѯ�ʺ�
	String tHealthImpartNo2[] = request.getParameterValues("LCContGrid9"); //��콡����֪��
	String tNoImpartDesc[] = request.getParameterValues("LCContGrid10"); //��Ӧδ��֪���
    String tChk[] = request.getParameterValues("InpLCContGridChk");; //������ʽ=�� Inp+MulLine������+Chk��
	LLCUWBatchSet tLLCUWBatchSet = new LLCUWBatchSet();
    //�����ݿ��ж�ȡ�����˵�����
    SSRS tSSRS = new SSRS();
    //ExeSQL tExeSQL = new ExeSQL();
    String tSql = "";
    tSql = "select name from lcinsured where insuredno = '"+tInsuredNo+"'";
    //tSSRS = tExeSQL.execSQL(tSql);
    TransferData sqlTransferData = new TransferData();
	  VData sqlVData = new VData();
			    sqlTransferData.setNameAndValue("SQL",tSql);
			    sqlVData.add(sqlTransferData);
			  	BusinessDelegate tsqlBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  	  if(!tsqlBusinessDelegate.submitData(sqlVData,"execSQL","ExeSQLUI"))
			  	  {    
			  	       if(tsqlBusinessDelegate.getCErrors()!=null&&tsqlBusinessDelegate.getCErrors().getErrorCount()>0)
			  	       { 
			  				loggerDebug("LLSecondUWSave","��ѯʧ�ܣ�ԭ����:" + tsqlBusinessDelegate.getCErrors().getFirstError());
			  		   }
			  		   else
			  		   {
			  			 	loggerDebug("LLSecondUWSave","��ѯʧ��");				
			  		   }
			  	  }
	tSSRS=(SSRS)tsqlBusinessDelegate.getResult().get(0);
    
    if(tSSRS.getMaxRow()==0)
    {
        return ;
    }
    String tInsuredName  =   tSSRS.GetText(1,1);

    loggerDebug("LLSecondUWSave","-tInsuredName--"+tInsuredName);
     
	if (tChk!=null&&tChk.length>0)
	{
		for(int index=0;index<tChk.length;index++)
		{
			if(tChk[index].equals("1")) 
			{    
			     String mAppntName = "";
			     //ExeSQL tExe = new ExeSQL();
			     SSRS tS = new SSRS();
			     String tSQLPD = " select rgtobj from llregister where rgtno='"+tCaseNo+"'" ;
			     //String tRgtObj = tExe.getOneValue(tSQLPD);
			     TransferData sqlTransferData1 = new TransferData();
	  			VData sqlVData1 = new VData();
			    sqlTransferData1.setNameAndValue("SQL",tSQLPD);
			    sqlVData1.add(sqlTransferData1);
			  	  if(!tsqlBusinessDelegate.submitData(sqlVData1,"getOneValue","ExeSQLUI"))
			  	  {    
			  	       if(tsqlBusinessDelegate.getCErrors()!=null&&tsqlBusinessDelegate.getCErrors().getErrorCount()>0)
			  	       { 
			  				loggerDebug("LLSecondUWSave","��ѯʧ�ܣ�ԭ����:" + tsqlBusinessDelegate.getCErrors().getFirstError());
			  		   }
			  		   else
			  		   {
			  			 	loggerDebug("LLSecondUWSave","��ѯʧ��");				
			  		   }
			  	  }
			  	String tRgtObj =(String)tsqlBusinessDelegate.getResult().get(0);
			     if(tRgtObj.trim().equals("1")||tRgtObj.trim().equals("3"))
			     {			     
				     //�����ݿ��ж�ȡͶ���˵�����					 				     
				     String tSQL = "";
				     tSQL = "select appntname from lcappnt where appntno = '"+tAppntNo[index]+"'";
				     //tS = tExe.execSQL(tSQL);
				     TransferData sqlTransferData2 = new TransferData();
		  			VData sqlVData2 = new VData();
				    sqlTransferData2.setNameAndValue("SQL",tSQL);
				    sqlVData2.add(sqlTransferData2);
				  	  if(!tsqlBusinessDelegate.submitData(sqlVData2,"execSQL","ExeSQLUI"))
				  	  {    
				  	       if(tsqlBusinessDelegate.getCErrors()!=null&&tsqlBusinessDelegate.getCErrors().getErrorCount()>0)
				  	       { 
				  				loggerDebug("LLSecondUWSave","��ѯʧ�ܣ�ԭ����:" + tsqlBusinessDelegate.getCErrors().getFirstError());
				  		   }
				  		   else
				  		   {
				  			 	loggerDebug("LLSecondUWSave","��ѯʧ��");				
				  		   }
				  	  }
				  	tS =(SSRS)tsqlBusinessDelegate.getResult().get(0);
				     if(tS.getMaxRow()==0)
				     {
				        return ;
				     }
				     mAppntName  =   tS.GetText(1,1);
				     loggerDebug("LLSecondUWSave",".................................���շ�����ˣ�...........................");
			     }
			     else
			     {
				     //�����ݿ��ж�ȡͶ���˵�����					 				     
				     String tSQL = "";
				     tSQL = " select grpname from lcgrpcont a where a.grpcontno in "
				          + " (select grpcontno from lccont b where b.contno = '"+tContNo[index]+"')";
				     //tS = tExe.execSQL(tSQL);
				     TransferData sqlTransferData2 = new TransferData();
		  			VData sqlVData2 = new VData();
				    sqlTransferData2.setNameAndValue("SQL",tSQL);
				    sqlVData2.add(sqlTransferData2);
				  	  if(!tsqlBusinessDelegate.submitData(sqlVData2,"execSQL","ExeSQLUI"))
				  	  {    
				  	       if(tsqlBusinessDelegate.getCErrors()!=null&&tsqlBusinessDelegate.getCErrors().getErrorCount()>0)
				  	       { 
				  				loggerDebug("LLSecondUWSave","��ѯʧ�ܣ�ԭ����:" + tsqlBusinessDelegate.getCErrors().getFirstError());
				  		   }
				  		   else
				  		   {
				  			 	loggerDebug("LLSecondUWSave","��ѯʧ��");				
				  		   }
				  	  }
				  	tS =(SSRS)tsqlBusinessDelegate.getResult().get(0);
				     if(tS.getMaxRow()==0)
				     {
				        return ;
				     }
				     mAppntName  =   tS.GetText(1,1);	
				     loggerDebug("LLSecondUWSave","..........................�����ⰸ�µ�һ���ְ�������ˣ�....................");		     
			     }
			     String tRemark = request.getParameter("Note");	
			     LLCUWBatchSchema tLLCUWBatchSchema = new LLCUWBatchSchema();
				 tLLCUWBatchSchema.setCaseNo(tCaseNo); //�ⰸ��
				 tLLCUWBatchSchema.setInsuredNo(tInsuredNo); //�����˿ͻ���
				 tLLCUWBatchSchema.setInsuredName(tInsuredName); //����������
			     tLLCUWBatchSchema.setContNo(tContNo[index]); //��ͬ��
			     tLLCUWBatchSchema.setAppntNo(tAppntNo[index]); //Ͷ���˺���			     
			     tLLCUWBatchSchema.setAppntName(mAppntName); //Ͷ��������
			     tLLCUWBatchSchema.setClaimRelFlag(tClaimRelFlag[index]); //�ⰸ��ر�־			     
			     tLLCUWBatchSchema.setHealthImpartNo1(tHealthImpartNo1[index]); //Ͷ���齡����֪��ѯ�ʺ�
			     tLLCUWBatchSchema.setHealthImpartNo2(tHealthImpartNo2[index]); //��콡����֪��
			     tLLCUWBatchSchema.setNoImpartDesc(tNoImpartDesc[index]); //��Ӧδ��֪���
			     tLLCUWBatchSchema.setRemark1(tRemark); //������Ŀǰ����״������ 
			     tLLCUWBatchSet.add( tLLCUWBatchSchema );
			}
		}          	
	}
  	
  	//Stringʹ��TransferData������ύ
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("CaseNo",request.getParameter("CaseNo")); //�ⰸ��
    mTransferData.setNameAndValue("ClmNo",request.getParameter("CaseNo")); //�ⰸ��
    mTransferData.setNameAndValue("BatNo",""); //�������κ� ---��̨����   
    mTransferData.setNameAndValue("InsuredNo",request.getParameter("InsuredNo")); //�����˺���
    mTransferData.setNameAndValue("InsuredName",request.getParameter("InsuredName")); //����������   
    mTransferData.setNameAndValue("ClaimRelFlag",""); //��ر�־----��̨����
    mTransferData.setNameAndValue("MngCom",request.getParameter("ManageCom")); //����
    mTransferData.setNameAndValue("VIP",""); //VIP��־
    mTransferData.setNameAndValue("StartAgent",""); //�Ǽ�ҵ��Ա
    //loggerDebug("LLSecondUWSave","ת֮ǰ��tRptorName=========>  "+request.getParameter("RptorName"));
    //String tRptorName = StrTool.unicodeToGBK(request.getParameter("RptorName"));
   // String str= new String(request.getParameter("RptorName").toString().getBytes("iso8859_1"), "GBK");
    //tRptorName =  new String(tRptorName.getBytes("ISO-8859-1"),"GBK");
   // loggerDebug("LLSecondUWSave","ת�����1tRptorName=========>  "+str);
   // loggerDebug("LLSecondUWSave","ת�����2tRptorName=========>  "+tRptorName);
    //String tRptorName = request.getParameter("RptorName");
    //xuyunpeng add Ϊ�˽����������������������
    			     SSRS tS = new SSRS();

    				String tSQL = "";
				     tSQL = " select rgtantname from llregister where rgtno='"+request.getParameter("CaseNo")+"'";
				     //tS = tExe.execSQL(tSQL);
				     TransferData sqlTransferDatax = new TransferData();
		  			VData sqlVDatax = new VData();
				    sqlTransferDatax.setNameAndValue("SQL",tSQL);
				    sqlVDatax.add(sqlTransferDatax);
				  	  if(!tsqlBusinessDelegate.submitData(sqlVDatax,"execSQL","ExeSQLUI"))
				  	  {    
				  	       if(tsqlBusinessDelegate.getCErrors()!=null&&tsqlBusinessDelegate.getCErrors().getErrorCount()>0)
				  	       { 
				  				loggerDebug("LLSecondUWSave","��ѯʧ�ܣ�ԭ����:" + tsqlBusinessDelegate.getCErrors().getFirstError());
				  		   }
				  		   else
				  		   {
				  			 	loggerDebug("LLSecondUWSave","��ѯʧ��");				
				  		   }
				  	  }
				  	tS =(SSRS)tsqlBusinessDelegate.getResult().get(0);
				     if(tS.getMaxRow()==0)
				     {
				    	 
				        return ;
				     }
				   String  tRptorName  =   tS.GetText(1,1);	
				     //loggerDebug("LLSecondUWSave","..........................�����ⰸ�µ�һ���ְ�������ˣ�....................");		     
    
    mTransferData.setNameAndValue("RptorName",tRptorName); //������
    mTransferData.setNameAndValue("theCurrentDate",request.getParameter("CurrentDate")); //�����������
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID")); 
    tSql ="select submissionid, activityid from lwmission where activityid in (select activityid from lwactivity where functionid = '10030000') and missionid = '"+request.getParameter("MissionID")+"'";
    TransferData MTransferData = new TransferData();
	VData MVData = new VData();
	MTransferData.setNameAndValue("SQL",tSql);
    MVData.add(MTransferData);
  	  if(!tsqlBusinessDelegate.submitData(MVData,"execSQL","ExeSQLUI"))
  	  {    
  	       if(tsqlBusinessDelegate.getCErrors()!=null&&tsqlBusinessDelegate.getCErrors().getErrorCount()>0)
  	       { 
  				loggerDebug("LLSecondUWSave","��ѯʧ�ܣ�ԭ����:" + tsqlBusinessDelegate.getCErrors().getFirstError());
  		   }
  		   else
  		   {
  			 	loggerDebug("LLSecondUWSave","��ѯʧ��");				
  		   }
  	  }
  	SSRS MSSRS =(SSRS)tsqlBusinessDelegate.getResult().get(0);
     if(MSSRS.getMaxRow()==0)
     {
    	
        return ;
     }
     String tSubMissionID  =   MSSRS.GetText(1,1);
     String tActivityID  =   MSSRS.GetText(1,2);
     mTransferData.setNameAndValue("ActivityID", tActivityID);
     mTransferData.setNameAndValue("SubMissionID", tSubMissionID);
     
    
	try
	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add(tLLCUWBatchSet);
		tVData.add(mTransferData);		
		tVData.add(tG);
		String wFlag="Create||ScondUWNode"; //�����ڵ�
    	/*ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI();		
        if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
        {           
            Content = "�ύ������ʧ�ܣ�ԭ����: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
            FlagStr = "Fail";

        }
		else 
		{
		    Content = " ����ɹ�! ";
		    FlagStr = "SUCC";
		}*/
		String busiName="tWorkFlowUI";
		//String busiName="CreateLLScondUWNodeBL";
		  String mDescType="����";
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

			if(!tBusinessDelegate.submitData(tVData,"execute",busiName))
			  {    
			       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			       { 
						Content = "�ύ������ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
						FlagStr = "Fail";
				   }
				   else
				   {
						Content = "�ύ������ʧ��";
						FlagStr = "Fail";				
				   }
			  }
			  else
			  {
			     	Content = mDescType+"�ɹ�! ";
			      	FlagStr = "SUCC";  
			  }
	}
	catch(Exception e)
	{
		e.printStackTrace();
		Content = Content.trim()+".��ʾ���쳣��ֹ!";
	}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

