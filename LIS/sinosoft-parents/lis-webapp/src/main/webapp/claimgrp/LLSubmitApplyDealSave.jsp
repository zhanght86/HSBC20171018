<%
//Name��LLSubmitApplyDealSave.jsp
//Function���ʱ�������Ϣ�ύ
%>
<!--�û�У����-->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>

<%
//************************
//������Ϣ������У�鴦��
//************************
//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI");  
LLSubmitApplyDealUI tLLSubmitApplyDealUI = new LLSubmitApplyDealUI();
ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //���⹤��������

if(tGI == null)   //ҳ����Ч
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    System.out.println("ҳ��ʧЧ,�����µ�½");    
}
else //ҳ����Ч
{
    String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����
    System.out.println("----ComCode= "+tGI.ComCode);
    System.out.println("-----ManageCom= "+ManageCom);
    
    String transact = request.getParameter("fmtransact"); //��ȡ��������insert||update
    System.out.println("-----transact= "+transact);
    String isReportExist = request.getParameter("isReportExist"); //�Ƿ�Ϊ�����¼�����true����false
    String wFlag = request.getParameter("WorkFlowFlag");

     //***************************************
    //��ȡ�ʱ�����ҳ����Ϣ 
    //***************************************   
   //�������
    LLSubmitApplySchema tLLSubmitApplySchema = new LLSubmitApplySchema(); //�ʱ������
    tLLSubmitApplySchema.setClmNo(request.getParameter("ClmNo"));
    tLLSubmitApplySchema.setSubNo(request.getParameter("SubNO"));
    tLLSubmitApplySchema.setSubCount(request.getParameter("SubCount"));
    tLLSubmitApplySchema.setDispType(request.getParameter("DispType"));
    tLLSubmitApplySchema.setDispIdea(request.getParameter("DispIdea")); //�ʱ��������
    tLLSubmitApplySchema.setSubDesc(request.getParameter("ReportheadSubDesc")); //�����ʱ�����
    
    //Stringʹ��TransferData������ύ
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo")); //�ⰸ��
    mTransferData.setNameAndValue("SubNo",request.getParameter("SubNO")); //�ʱ����
    mTransferData.setNameAndValue("SubCount",request.getParameter("SubCount")); //�ʱ�����
    mTransferData.setNameAndValue("CustomerNo",request.getParameter("CustomerNo")); //�����˿ͻ���
    mTransferData.setNameAndValue("CustomerName",request.getParameter("CustomerName")); //����������
    mTransferData.setNameAndValue("SubType",request.getParameter("SubType")); //�ʱ����ͣ�1--�ӷֹ�˾���ܹ�˾��
    mTransferData.setNameAndValue("SubRCode",request.getParameter("SubRCode")); //�ʱ�ԭ��
	mTransferData.setNameAndValue("MngCom", request.getParameter("ManageCom"));  //����    
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("transact",request.getParameter("fmtransact"));

    try
    {
    	//׼���������� VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(isReportExist);        
        tVData.add(tLLSubmitApplySchema);
        tVData.add(mTransferData);
        System.out.println("---submitData and transact="+transact);
         //���ݴ��䣨�����ʱ���ֱ�Ӵ���[�˴���transact �滻mOperate]
        if (transact.equals("INSERT||Reporthead"))
        {
            System.out.println("---tLLSubmitApplyDealUI submitData and transact="+transact);
        	if (!tLLSubmitApplyDealUI.submitData(tVData,transact))
	        {
        		Content = " �����ύʧ�ܣ�ԭ����: " + tLLSubmitApplyDealUI.mErrors.getError(0).errorMessage;
            	FlagStr = "Fail";
	        }
	        else
	       {
	        	tVData.clear();
	    	 	tVData = tLLSubmitApplyDealUI.getResult();
	    	 	Content = "�����ʱ��ύ�ɹ�";
                FlagStr = "Succ";     
	       }
        }
         //���ѡ����������ظ���������߹�����  
        if(transact.equals("UPDATE||Replyport")|| transact.equals("UPDATE||Investgate")) //ɾ���ڵ�
        {
            System.out.println("---ClaimWorkFlowUI submitData and transact="+transact);
            wFlag="0000005105";           
            if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
            {
                Content = " �ύ������ʧ�ܣ�ԭ����: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
                FlagStr = "Fail";
            }   
            else
            {
            	//�ύ�ɹ����ӽ������ȡ��ǰ̨��Ҫ����
                tVData.clear();
                tVData = tClaimWorkFlowUI.getResult();
                System.out.println("tVData="+tVData);	    
                Content = "�����ύ�ɹ�";
                FlagStr = "Succ";            
           	}   
        } 
    }
    catch(Exception ex)
    {
    	Content = "�ύʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
     }
    //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
    System.out.println("------LLSubmitApplyDealSave.jsp end------");
    System.out.println(FlagStr);
    System.out.println(Content);
}	
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>