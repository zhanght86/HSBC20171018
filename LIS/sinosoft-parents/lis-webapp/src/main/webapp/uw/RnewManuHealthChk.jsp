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
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.xbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
<%
  //�������
  //loggerDebug("RnewManuHealthChk","start..................................");

  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";


  GlobalInput tGlobalInput = new GlobalInput();
  tGlobalInput=(GlobalInput)session.getValue("GI");
  if(tGlobalInput == null) {
    out.println("session has expired");
    return;
  }

  loggerDebug("RnewManuHealthChk","step1..................................");
    // Ͷ�����б�
    RnewPENoticeSchema tRnewPENoticeSchema = new RnewPENoticeSchema();

    String tContNo = request.getParameter("ContNo");
    String tInsureNo = request.getParameter("InsureNo");
    String tHospital = request.getParameter("Hospital");
    String tPEReason = request.getParameter("PEReason");
    String tPrtNo =  request.getParameter("PrtNo");
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
    loggerDebug("RnewManuHealthChk","step2..................................tHealthItem:"+tHealthItem+":tHealthCode:"+tHealthCode);

 		
    boolean flag = true;

    //loggerDebug("RnewManuHealthChk","count:"+ChkCount);
        //�������һ
        tRnewPENoticeSchema.setContNo(tContNo);
        tRnewPENoticeSchema.setPrtSeq(tPrtSeq);
      //  tRnewPENoticeSchema.setProposalContNo(tContNo);
        //tRnewPENoticeSchema.setPEAddress(tHospital);
        tRnewPENoticeSchema.setPEDate(tEDate);
        tRnewPENoticeSchema.setPEBeforeCond(tIfEmpty);
        tRnewPENoticeSchema.setRemark(tNote);
        tRnewPENoticeSchema.setCustomerNo(tInsureNo);
        tRnewPENoticeSchema.setPEReason(tPEReason);  
        tRnewPENoticeSchema.setCustomerType(tCustomerType);

    loggerDebug("RnewManuHealthChk","flag:"+flag);
  if (flag == true)
  {
        // ׼���������� VData
       VData tVData = new VData();
       TransferData tTransferData = new TransferData();
       tTransferData.setNameAndValue("ContNo",tContNo);
       tTransferData.setNameAndValue("PrtNo",tPrtNo);
       tTransferData.setNameAndValue("CustomerNo",tInsureNo );
       tTransferData.setNameAndValue("MissionID",tMissionID );
       tTransferData.setNameAndValue("SubMissionID",tSubMissionID );
     	 tTransferData.setNameAndValue("RnewPENoticeSchema",tRnewPENoticeSchema);
       tTransferData.setNameAndValue("HealthItem",tHealthItem);
       tTransferData.setNameAndValue("HealthCode",tHealthCode);
       tTransferData.setNameAndValue("OtherItem",tOtherItem);
       tTransferData.setNameAndValue("Flag",tFlag);

       tVData.add(tGlobalInput);
       tVData.add(tTransferData);
       tVData.add(tRnewPENoticeSchema);
        // ���ݴ���
        /*
        RnewAutoHealthBL tRnewAutoHealthBL = new RnewAutoHealthBL();
        if(tRnewAutoHealthBL.submitData(tVData,tAction)==false)
        {
        	 int n = tRnewAutoHealthBL.mErrors.getErrorCount();
             Content = "�������¼��ʧ�ܣ�ԭ����:";
             Content = Content + tRnewAutoHealthBL.mErrors.getError(0).errorMessage;
             FlagStr = "Fail";
        }
        */
        String busiName="RnewAutoHealthUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
          if(!tBusinessDelegate.submitData(tVData,tAction,busiName))
          {    
              if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
              { 
            	  int n = tBusinessDelegate.getCErrors().getErrorCount();
      				Content = "�������¼��ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
      				FlagStr = "Fail";
      				}
      	 }
    
        //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
        if (FlagStr == "Fail")
        {
            tError = tBusinessDelegate.getCErrors();
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
        }
    }
%>

<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
