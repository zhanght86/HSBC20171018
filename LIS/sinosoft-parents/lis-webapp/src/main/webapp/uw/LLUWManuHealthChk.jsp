<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuHealthChk.jsp
//�����ܣ��б��˹��˱��������¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //�������
  //loggerDebug("LLUWManuHealthChk","start..................................");

  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";


  GlobalInput tGlobalInput = new GlobalInput();
  tGlobalInput=(GlobalInput)session.getValue("GI");
  if(tGlobalInput == null) {
    out.println("session has expired");
    return;
  }

  loggerDebug("LLUWManuHealthChk","step1..................................");
    // Ͷ�����б�
    LLUWPENoticeSchema tLLUWPENoticeSchema = new LLUWPENoticeSchema();

    String tContNo = request.getParameter("ContNo");
    String tInsureNo = request.getParameter("InsureNo");
    String tHospital = request.getParameter("Hospital");
    String tPEReason = request.getParameter("PEReason");
    String tPrtNo =  request.getParameter("PrtNo");
    String tRgtNo =  request.getParameter("RgtNo");
    String tBatchNo =  request.getParameter("BatchNo");
    
    String tIfEmpty = request.getParameter("IfEmpty");
    String tEDate = request.getParameter("EDate");
    String tNote = request.getParameter("Note");
    String tOtherItem = request.getParameter("otheritem2");
    String tMissionID = request.getParameter("MissionID");
    String tSubMissionID = request.getParameter("SubMissionID");
    String tFlag = request.getParameter("Flag");
    //�����Ŀ
    String tHealthItem = request.getParameter("CheckedItem");
    //�������
    String tHealthCode = request.getParameter("HealthCode");
    String tCustomerType = request.getParameter("CustomerType");
    String tAction = request.getParameter("Action");
    if(tAction==null||tAction.equals(""))
    {
    	tAction = "INSERT";
    }
    String tPrtSeq = request.getParameter("PrtSeq");
    loggerDebug("LLUWManuHealthChk","step2..................................tHealthItem:"+tHealthItem+":tHealthCode:"+tHealthCode);

 		
    boolean flag = true;

    //loggerDebug("LLUWManuHealthChk","count:"+ChkCount);
        //�������һ
        tLLUWPENoticeSchema.setContNo(tContNo);
        tLLUWPENoticeSchema.setPrtSeq(tPrtSeq);
      //  tLCPENoticeSchema.setProposalContNo(tContNo);
        //tLCPENoticeSchema.setPEAddress(tHospital);
        tLLUWPENoticeSchema.setPEDate(tEDate);
        tLLUWPENoticeSchema.setPEBeforeCond(tIfEmpty);
        tLLUWPENoticeSchema.setRemark(tNote);
        tLLUWPENoticeSchema.setCustomerNo(tInsureNo);
        tLLUWPENoticeSchema.setPEReason(tPEReason);
        tLLUWPENoticeSchema.setCustomerType(tCustomerType);
        tLLUWPENoticeSchema.setClmNo(tRgtNo);
        tLLUWPENoticeSchema.setBatNo(tBatchNo);
        

    loggerDebug("LLUWManuHealthChk","flag:"+flag);
  if (flag == true){
        // ׼���������� VData
       VData tVData = new VData();
       TransferData tTransferData = new TransferData();
       tTransferData.setNameAndValue("ContNo",tContNo);
       tTransferData.setNameAndValue("PrtNo",tPrtNo);
       tTransferData.setNameAndValue("CustomerNo",tInsureNo );
       tTransferData.setNameAndValue("MissionID",tMissionID );
       tTransferData.setNameAndValue("SubMissionID",tSubMissionID );
     	 tTransferData.setNameAndValue("LLUWPENoticeSchema",tLLUWPENoticeSchema);
       tTransferData.setNameAndValue("HealthItem",tHealthItem);
       tTransferData.setNameAndValue("HealthCode",tHealthCode);
       tTransferData.setNameAndValue("OtherItem",tOtherItem);
       tTransferData.setNameAndValue("Flag",tFlag);
       tTransferData.setNameAndValue("RgtNo",tRgtNo);
       tTransferData.setNameAndValue("BatchNo",tBatchNo);

       tVData.add(tGlobalInput);
       tVData.add(tTransferData);
       tVData.add(tLLUWPENoticeSchema);
        // ���ݴ���
        /*LLUWAutoHealthBL tLLUWAutoHealthBL = new LLUWAutoHealthBL();
        if(tLLUWAutoHealthBL.submitData(tVData,tAction)==false)
        {
        	 int n = tLLUWAutoHealthBL.mErrors.getErrorCount();
             Content = "�������¼��ʧ�ܣ�ԭ����:";
             Content = Content + tLLUWAutoHealthBL.mErrors.getError(0).errorMessage;
             FlagStr = "Fail";
        }
    
        //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
        if (FlagStr == "Fail")
        {
            tError = tLLUWAutoHealthBL.mErrors;
            if (!tError.needDealError())
            {
               if(!tAction.equals("DELETE"))
               {
                Content = "�������¼��ɹ�! ";
                }
              else
              	{
              	 Content = "�������ɾ���ɹ�! ";
              	}
                FlagStr = "Succ";
            }
            else
            {
                Content = "�������¼��ʧ�ܣ�ԭ����:" + tError.getFirstError();
                FlagStr = "Fail";
            }
        }*/
        String busiName="LLUWAutoHealthBL";
        String mDescType="�������¼��";
        if(tAction.equals("DELETE"))
        {
        	mDescType="�������ɾ��";
        }
      	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
      	  if(!tBusinessDelegate.submitData(tVData,tAction,busiName))
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
      	      	FlagStr = "Succ";  
      	  }
    }
%>

<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
