<%
//Name��LLInqApplySave.jsp
//Function����������ύ
//Author��zhoulei
//Date:
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
//************************
//������Ϣ������У�鴦��
//************************

//�������
LLInqApplySchema tLLInqApplySchema = new LLInqApplySchema(); //��������

//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI"); 
//ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //���⹤�������� 
//LLInqApplyUI tLLInqApplyUI = new LLInqApplyUI();
String busiName="grpClaimWorkFlowUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

//String busiName1="grpLLInqApplyUI";
//BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLInqApplySave","ҳ��ʧЧ,�����µ�½");    
}
else //ҳ����Ч
{
    String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����

    String transact = request.getParameter("fmtransact"); //��ȡ����insert||update
    loggerDebug("LLInqApplySave","-----transact= "+transact);
    String wFlag = request.getParameter("WorkFlowFlag");
    //***************************************
    //��ȡҳ����Ϣ 
    //***************************************   
    tLLInqApplySchema.setClmNo(request.getParameter("ClmNo")); //�ⰸ��
    tLLInqApplySchema.setInqNo(request.getParameter("InqNo")); //�������
    tLLInqApplySchema.setBatNo(request.getParameter("BatNo")); //�������κ�
    tLLInqApplySchema.setCustomerNo(request.getParameter("CustomerNo")); //�����˿ͻ���
    tLLInqApplySchema.setCustomerName(request.getParameter("CustomerName")); //����������
    tLLInqApplySchema.setVIPFlag(request.getParameter("VIPFlag")); //VIP�ͻ���־
    tLLInqApplySchema.setInitPhase(request.getParameter("InitPhase")); //����׶�
    tLLInqApplySchema.setInqRCode(request.getParameter("InqReason")); //����ԭ��
    tLLInqApplySchema.setInqItem(request.getParameter("InqItem")); //������Ŀ
    tLLInqApplySchema.setInqDesc(request.getParameter("InqDesc")); //��������
    tLLInqApplySchema.setInqDept(request.getParameter("InqOrg2")); //�������
    tLLInqApplySchema.setLocFlag(request.getParameter("LocFlag")); //���ر�־
    
    //Stringʹ��TransferData������ύ
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo")); //�ⰸ��
    mTransferData.setNameAndValue("InqNo",request.getParameter("InqNo")); //�������
    mTransferData.setNameAndValue("BatNo",request.getParameter("BatNo")); //�������κ�    
    mTransferData.setNameAndValue("CustomerNo",request.getParameter("CustomerNo")); //�����˿ͻ���
    mTransferData.setNameAndValue("CustomerName",request.getParameter("CustomerName")); //����������
    mTransferData.setNameAndValue("InitPhase",tLLInqApplySchema.getInitPhase()); //����׶�
    mTransferData.setNameAndValue("InqRCode",request.getParameter("InqReason")); //����ԭ��
    mTransferData.setNameAndValue("InqDept",request.getParameter("InqOrg2")); //�������  
	mTransferData.setNameAndValue("MngCom", request.getParameter("ManageCom"));  //����         
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    try
    {
        //׼���������� VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(transact);
        tVData.add(mTransferData);
        tVData.add(tLLInqApplySchema);
    	wFlag = "8899999999"; 
        try
        {
            loggerDebug("LLInqApplySave","---ClaimWorkFlowUI submitData and transact="+transact);
//            if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//            {           
//                Content = "�ύ������ClaimWorkFlowUIʧ�ܣ�ԭ����: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//    
//            }
		if(!tBusinessDelegate.submitData(tVData,wFlag,busiName))
		{    
		    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		    { 
				Content = "�ύ������ClaimWorkFlowUIʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			else
			{
				Content = "�ύ������ClaimWorkFlowUIʧ��";
				FlagStr = "Fail";				
			}
		}

            else
        	{
        		//�ӽ������ȡ��ǰ̨��Ҫ����
                tVData.clear();
                //tVData = tClaimWorkFlowUI.getResult();
                tVData = tBusinessDelegate.getResult();
                loggerDebug("LLInqApplySave","tVData="+tVData);	    
                Content = "�����ύ�ɹ�";
                FlagStr = "Succ";            
        	}
    	}
    	catch(Exception ex)
        {
        	Content = "�����ύClaimWorkFlowUIʧ�ܣ�ԭ����:" + ex.toString();
        	FlagStr = "Fail";
        }	
    }    
    catch(Exception ex)
    {
        Content = "�����ύClaimWorkFlowUIʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
    }
    
    loggerDebug("LLInqApplySave","------LLInqApplySave.jsp end------");
    loggerDebug("LLInqApplySave",FlagStr);
    loggerDebug("LLInqApplySave",Content);
}    
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
