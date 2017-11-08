<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�EdorAppMUHealthSave.jsp
//�����ܣ���ȫ�˹��˱��������¼��
//�������ڣ�2005-06-14 10:35:36
//������  ��liurongxiao
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
<%
  //�������
  loggerDebug("EdorAppMUHealthSave","save start..................................");

  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  boolean mflag = true;

  GlobalInput tGlobalInput = new GlobalInput();
  tGlobalInput=(GlobalInput)session.getValue("GI");
  if(tGlobalInput == null) {
	out.println("session has expired");
	return;
  }

    loggerDebug("EdorAppMUHealthSave","step1..................................");
  	// Ͷ�����б�
	LPPENoticeSchema tLPPENoticeSchema = new LPPENoticeSchema();
	LPPENoticeItemSet tLPPENoticeItemSet = new LPPENoticeItemSet();

	String tContNo = request.getParameter("ContNo");
	String tInsureNo = request.getParameter("InsureNo");
	String tEdorNo = request.getParameter("EdorNo");
	String tEdorAcceptNo = request.getParameter("EdorAcceptNo");
	String tHospital = request.getParameter("Hospital");
	String tPrtNo =  request.getParameter("PrtNo");
	String tIfEmpty = request.getParameter("IfEmpty");
	String tEDate = request.getParameter("EDate");
	String tNote = request.getParameter("Note");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	

    loggerDebug("EdorAppMUHealthSave","step2..................................");
      

    String  tbodyCheck = request.getParameter("bodyCheck");
    
    if(tbodyCheck == null){ //startif4
    	Content = "��ѡ���������!";
		FlagStr = "Fail";
		mflag = false;
	}
	else{
	    loggerDebug("EdorAppMUHealthSave","step3...................");
        String[] tBodyCheckItems = request.getParameterValues(tbodyCheck);
        if(tBodyCheckItems == null){ //startif3
    	    Content = "��ѡ�������Ŀ!";
		    FlagStr = "Fail";
		    mflag = false;
	    }
	    else{       
	    
	    
	        String[] thealthcode = new String[20];
            String[] thealthname = new String[20];
         
        	for(int i = 0;i<tBodyCheckItems.length;i++)
            {
                thealthcode[i] = tBodyCheckItems[i].substring(0,3);
                thealthname[i] = tBodyCheckItems[i].substring(3);
            }
        

	        int ChkCount = tBodyCheckItems.length;
	        if (tHospital.equals("") || tIfEmpty.equals("")){ //startif2
		        Content = "���������Ϣ¼�벻����!";
		        FlagStr = "Fail";
		        mflag = false;
		   	}
	        else{
	           //�������һ
               tLPPENoticeSchema.setContNo(tContNo);
               tLPPENoticeSchema.setEdorNo(tEdorNo);
               tLPPENoticeSchema.setEdorAcceptNo(tEdorAcceptNo);
	    	   tLPPENoticeSchema.setPEAddress(tHospital);
	    	   tLPPENoticeSchema.setPEDate(tEDate);
	    	   tLPPENoticeSchema.setPEBeforeCond(tIfEmpty);
	    	   tLPPENoticeSchema.setRemark(tNote);
	    	   tLPPENoticeSchema.setCustomerNo(tInsureNo);

	    	   loggerDebug("EdorAppMUHealthSave","chkcount="+ChkCount);

                   for (int i = 0; i < ChkCount; i++){ //startfor
				     if (!thealthcode[i].equals("")){ //startif1
		  			  LPPENoticeItemSchema tLPPENoticeItemSchema = new LPPENoticeItemSchema();
		  			  tLPPENoticeItemSchema.setContNo(tContNo);
		  			  tLPPENoticeItemSchema.setEdorNo(tEdorNo);
		  			  tLPPENoticeItemSchema.setEdorAcceptNo(tEdorAcceptNo);
					  tLPPENoticeItemSchema.setPEItemCode( thealthcode[i]);
					  tLPPENoticeItemSchema.setPEItemName( thealthname[i]);
					  tLPPENoticeItemSchema.setFreePE("N");
			    	  tLPPENoticeItemSet.add( tLPPENoticeItemSchema );
			          loggerDebug("EdorAppMUHealthSave","i:"+i);
	    		      loggerDebug("EdorAppMUHealthSave","healthcode:"+thealthcode[i]);
			   		  mflag = true;
				     } //endif1
			        } //endfor
	         } //endif2
	        } //endif3
	    } //endif4

    
    
	loggerDebug("EdorAppMUHealthSave","mflag:"+mflag);
  	if (mflag == true)
  	{
		// ׼���������� VData
	   VData tVData = new VData();
	   EdorWorkFlowUI tEdorWorkFlowUI = new EdorWorkFlowUI();
    
	   TransferData tTransferData = new TransferData();
	   tTransferData.setNameAndValue("ContNo",tContNo);
	   tTransferData.setNameAndValue("EdorNo",tEdorNo);
	   tTransferData.setNameAndValue("PrtNo",tPrtNo);
	   tTransferData.setNameAndValue("CustomerNo",tInsureNo );
	   tTransferData.setNameAndValue("MissionID",tMissionID );
	   tTransferData.setNameAndValue("SubMissionID",tSubMissionID );
       tTransferData.setNameAndValue("LPPENoticeSchema",tLPPENoticeSchema);
       tTransferData.setNameAndValue("LPPENoticeItemSet",tLPPENoticeItemSet);

	   tVData.add(tGlobalInput);
	   tVData.add(tTransferData);
			   
	
	   	if (!tEdorWorkFlowUI.submitData(tVData,"0000000019"))
		{
			int n = tEdorWorkFlowUI.mErrors.getErrorCount();
			Content = " ��ȫ�������¼��ʧ�ܣ�ԭ����:";
			Content = Content + tEdorWorkFlowUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tEdorWorkFlowUI.mErrors;
		    if (!tError.needDealError())
		    {
		    	Content = " ��ȫ�������¼��ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else
		    {
		    	Content = " ��ȫ�������¼��ʧ�ܣ�ԭ����:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }

		}

	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
