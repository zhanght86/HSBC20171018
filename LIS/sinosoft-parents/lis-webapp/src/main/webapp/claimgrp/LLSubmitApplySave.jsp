<%
//Name��LLSubmitApplySave.jsp
//Function������ʱ��ύ
//Author��zhoulei
//Date:
%>
<%@page contentType="text/html;charset=GBK" %> 
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
LLSubmitApplySchema tLLSubmitApplySchema = new LLSubmitApplySchema(); //�ʱ�

//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI");  
//ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //���⹤��������
//LLSubmitApplyUI tLLSubmitApplyUI = new LLSubmitApplyUI();  //�ʱ�������ύ��
String busiName="grpClaimWorkFlowUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
//String busiName1="grpLLSubmitApplyUI";
//BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    System.out.println("ҳ��ʧЧ,�����µ�½");    
}
else //ҳ����Ч
{
    String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����

    String transact = request.getParameter("fmtransact"); //��ȡ����insert||update
    System.out.println("-----transact= "+transact);
    //�����������ͱ𣬸��ݴ�ֵ�����ID��ȡ��������ִ�о���ҵ���߼�
    String wFlag = request.getParameter("WorkFlowFlag");

    //***************************************
    //��ȡ����ҳ����Ϣ�������ֶ���schema�����String����ɢ����ʹ��TransferData���
    //***************************************
    tLLSubmitApplySchema.setClmNo(request.getParameter("ClmNo")); //�ⰸ��
   // tLLSubmitApplySchema.setInqNo(request.getParameter("SubNo")); //�ʱ���ţ��Զ����ɣ�
    tLLSubmitApplySchema.setSubCount(request.getParameter("SubCount")); //�ʱ�����
    tLLSubmitApplySchema.setCustomerNo(request.getParameter("CustomerNo")); //�����˿ͻ���
    tLLSubmitApplySchema.setCustomerName(request.getParameter("CustomerName")); //����������
    tLLSubmitApplySchema.setVIPFlag(request.getParameter("VIPFlag")); //VIP�ͻ���־
    tLLSubmitApplySchema.setInitPhase(request.getParameter("InitPhase")); //����׶�    
    tLLSubmitApplySchema.setSubType(request.getParameter("SubType")); //�ʱ�����
    tLLSubmitApplySchema.setSubRCode(request.getParameter("SubRCode")); //�ʱ�ԭ��
    tLLSubmitApplySchema.setSubDesc(request.getParameter("SubDesc")); //�ʱ�����
    tLLSubmitApplySchema.setSubPer(request.getParameter("SubPer")); //�ʱ���
    tLLSubmitApplySchema.setSubDate(request.getParameter("SubDate")); //�ʱ�����
    tLLSubmitApplySchema.setSubDept(request.getParameter("SubDept")); //�ʱ�����
    tLLSubmitApplySchema.setSubState(request.getParameter("SubState")); //�ʱ�״̬
            
    //Stringʹ��TransferData������ύ
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo")); //�ⰸ��
    mTransferData.setNameAndValue("SubNo",request.getParameter("SubNo")); //�ʱ����
    mTransferData.setNameAndValue("SubCount",request.getParameter("SubCount")); //�ʱ�����
    mTransferData.setNameAndValue("CustomerNo",request.getParameter("CustomerNo")); //�����˿ͻ���
    mTransferData.setNameAndValue("CustomerName",request.getParameter("CustomerName")); //����������
    mTransferData.setNameAndValue("SubType",request.getParameter("SubType")); //�ʱ�����
    mTransferData.setNameAndValue("SubRCode",request.getParameter("SubRCode")); //�ʱ�ԭ��
    mTransferData.setNameAndValue("FilialeDirector",request.getParameter("FilialeDirector")); //�ֹ�˾���κ���Ա
    mTransferData.setNameAndValue("MngCom",request.getParameter("MngCom")); //����
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
                    
    try
    {
        //׼���������� VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(mTransferData);
        tVData.add(tLLSubmitApplySchema);       
    	wFlag = "8999999999";
        try
        {
            System.out.println("---ClaimWorkFlowUI submitData and transact="+transact);
//            if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//            {
//                Content = " �ύ������ClaimWorkFlowUIʧ�ܣ�ԭ����: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
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
                System.out.println("tVData="+tVData);	    
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
    
    System.out.println("------LLSubmitApplySave.jsp end------");
    System.out.println(FlagStr);
    System.out.println(Content);
    
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>