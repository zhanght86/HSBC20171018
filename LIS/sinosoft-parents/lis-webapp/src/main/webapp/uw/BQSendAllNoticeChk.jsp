<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWSendPrintChk.jsp
//�����ܣ��ʹ�ӡ����
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
  
<%
  //�������
  CErrors tError = null;
  //CErrors tErrors = null;
  String FlagStr = "Fail";
  String Content = "����֪ͨ�鷢��ʧ��,ԭ����:";
	//�ں˱�������ִ������жϱ�־
	String testAflag = "1";
	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  BusinessDelegate tBusinessDelegate;
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
  //tongmeng 2007-11-09 modify
  //�޸�Ϊͳһ���ź˱�֪ͨ��,һ��������ԼΪ��,�����ٶ��������ô˹��ܳ������޸�.
  //У�鴦��
  //���ݴ����	
			
  	//������Ϣ
  	// Ͷ�����б�	
	String tProposalNo = request.getParameter("ContNo");
    String tEDorNo = request.getParameter("EdorNo");
    String tEDorType = request.getParameter("EdorType");
	//String tOtherNoType = "02";
	
	String tCode = request.getParameter("hiddenNoticeType");
	
	loggerDebug("BQSendAllNoticeChk","tCode="+ tCode);
	String tMissionID=request.getParameter("MissionID");
	String tSubMissionID=request.getParameter("SubMissionID");
  loggerDebug("BQSendAllNoticeChk","@@@@@@@@@@@@@@@@tSubMissionID:"+tSubMissionID+"################");
	boolean flag = true;
try
{
  	if (flag == true)
  	{
  	
  	//tongmeng 2008-08-12 modify
  	//Ϊ֧�ֶ����ն౻����,ͬһʱ����ԶԲ�ͬ���ն����ͺ˱�֪ͨ��,
  	//������������֪ͨ��Ҳ�����﷢��.
  	String tSQL = "select missionid,SubMissionID from lwmission where missionid='"+tMissionID+"' and activityid='0000000006' ";
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(tSQL);
		int errorCount = 0;
		for(int m=1;m<=tSSRS.getMaxRow();m++)
		{
			String tMissionId = tSSRS.GetText(m,1);
			String tSubMissionId = tSSRS.GetText(m,2);
			// ׼���������� VData
			VData tVData = new VData();
			//tVData.add( tLOPRTManagerSchema);
			tVData.add( tG );
			TransferData mTransferData = new TransferData();
			mTransferData.setNameAndValue("ContNo",tProposalNo);
			mTransferData.setNameAndValue("NoticeType",tCode);
			mTransferData.setNameAndValue("MissionID",tMissionId);
   		mTransferData.setNameAndValue("SubMissionID",tSubMissionId); 
   		mTransferData.setNameAndValue("mSubMissionID",tSubMissionID); 
   		mTransferData.setNameAndValue("EdorNo",tEDorNo); 
   		mTransferData.setNameAndValue("EdorType",tEDorType); 
      mTransferData.setNameAndValue("Code","");
      //mTransferData.setNameAndValue("DefaultOperator", tG.Operator);
      mTransferData.setNameAndValue("testAflag", testAflag);
      //��Ҫ���²�ѯ�Զ��˱��ڵ��������ID
      ExeSQL yExeSQL = new ExeSQL();
    	SSRS ySSRS = new SSRS();
      String sqlstr="select activityid from lwactivity where functionid='10020004'";
    	ySSRS = yExeSQL.execSQL(sqlstr);
    	String xActivityID="";
    	if(ySSRS.MaxRow==0)
    	{}
    	else
    	{
      		 xActivityID = ySSRS.GetText(1,1);
  		}
  		mTransferData.setNameAndValue("ActivityID", xActivityID);
  		mTransferData.setNameAndValue("BusiType", "1002");
    		tVData.add(mTransferData);
			// ���ݴ���
		//	EdorWorkFlowUI tEdorWorkFlowUI   = new EdorWorkFlowUI();
			//tongmeng 2007-11-08 modify
			//ͳһ���ź˱�֪ͨ��
		// String busiName="tWorkFlowUI";
		 String busiName="WorkFlowUI";
     			
    			
     tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
     System.out.println("��ʼִ�У�tBusinessDelegate");
     if(!tBusinessDelegate.submitData(tVData,"execute",busiName))
    		{
    		 if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
     {  
    			   Content = "���ʧ�ܣ�ԭ����: û����Ҫ�·������������졢���������ܷ��˱�֪ͨ�� ��" + tBusinessDelegate.getCErrors().getFirstError();
    			   FlagStr = "Fail";
    			}
    			else
    			{

    			   Content = "���ʧ��";
    			   FlagStr = "Fail";		
    			   continue;		
    			}		
    		
    		
    		/**
    			int n = tBusinessDelegate.getCErrors().getErrorCount();
    			System.out.println(tBusinessDelegate.getCErrors);
    			if(tBusinessDelegate.getCErrors.equals("û����Ҫ�·������������졢���������ܷ��˱�֪ͨ��!"))
    			{
    				n = n + 1;
    				if(n == tSSRS.getMaxRow())
    				{
    					FlagStr = "Fail";
    					Content = " ����ʧ��,ԭ����:";
    				}
    				else
    				{
    					tBusinessDelegate.mErrors.clearErrors();
    					continue;
    				}					    
    			}
    			else
    			{			
    				FlagStr = "Fail";	
    			}
    			**/
    			
    			
    		}
    		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+FlagStr);
    		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
    		if (FlagStr == "Fail")
    		{
    	    	tError = tBusinessDelegate.getCErrors();		    
    	    	if (!tError.needDealError())
    	    	{                     
    	    		if (m == tSSRS.getMaxRow())
    		    	{                     
    	    		Content = " �����ɹ�! ";
    		    	}
    	    		FlagStr = "Succ";
    	    	}
    	    	else                                                              
    	   		{		    		
    	    		int n = tError.getErrorCount();
    				if (n > 0)
    				{
    	        
    		        	Content = Content.trim() + tError.getError(0).errorMessage.trim();
    				}
    	    			FlagStr = "Fail";
    	    			break;
    	   		}
    		}
    }
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
	parent.fraInterface.afterSubmitSendNotice("<%=FlagStr%>","<%=Content%>");
	
</script>
</html>
