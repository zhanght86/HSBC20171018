<%
//Name��LLSubmitApplyHeadDealSave.jsp
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
LLSubmitApplyHeadDealUI tLLSubmitApplyHeadDealUI = new LLSubmitApplyHeadDealUI();
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
    String transact = request.getParameter("fmtransact"); //��ȡ��������
    System.out.println("-----transact= "+transact);
    String isReportExist = request.getParameter("isReportExist"); //�Ƿ�Ϊ�����¼�����true����false

     //***************************************
    //��ȡ�ʱ�����ҳ����Ϣ 
    //***************************************   
   //�������
    LLSubmitApplySchema tLLSubmitApplySchema = new LLSubmitApplySchema(); //�ʱ������
    tLLSubmitApplySchema.setClmNo(request.getParameter("ClmNo"));
    tLLSubmitApplySchema.setSubNo(request.getParameter("SubNO"));
    tLLSubmitApplySchema.setSubCount(request.getParameter("SubCount"));
    tLLSubmitApplySchema.setSubType("2"); //�ʱ������á�2--�ֹ�˾���ܹ�˾��
    tLLSubmitApplySchema.setDispType(request.getParameter("DispType")); //��������
    tLLSubmitApplySchema.setDispIdea(request.getParameter("DispIdea")); //�������
    //tLLSubmitApplySchema.setSubState(request.getParameter("SubState")); //�ʱ�״̬
    
    //Stringʹ��TransferData������ύ
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo")); //�ⰸ��
    mTransferData.setNameAndValue("SubNo",request.getParameter("SubNO")); //�ʱ����
    mTransferData.setNameAndValue("SubCount",request.getParameter("SubCount")); //�ʱ�����
    mTransferData.setNameAndValue("CustomerNo",request.getParameter("CustomerNo")); //�����˿ͻ���
    mTransferData.setNameAndValue("CustomerName",request.getParameter("CustomerName")); //����������
    mTransferData.setNameAndValue("SubType",request.getParameter("2")); //�ʱ����ͣ�2--�ӷֹ�˾���ܹ�˾��
    mTransferData.setNameAndValue("SubRCode",request.getParameter("SubRCode")); //�ʱ�ԭ��
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));

    try
    {
    	//׼���������� VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(isReportExist);    
        tVData.add(transact);          
        tVData.add(tLLSubmitApplySchema);
        tVData.add(mTransferData);
        System.out.println("---tLLSubmitApplyHeadDealUI submitData and transact="+transact);
    	if (!tLLSubmitApplyHeadDealUI.submitData(tVData,transact))
        {
    		Content = " �����ύʧ�ܣ�ԭ����: " + tLLSubmitApplyHeadDealUI.mErrors.getError(0).errorMessage;
        	FlagStr = "Fail";
        }
        else
       {
        	tVData.clear();
    	 	tVData = tLLSubmitApplyHeadDealUI.getResult();
    	 	Content = "�ظ���������ύ�ɹ�";
            FlagStr = "Succ";     
       }
    }
    catch(Exception ex)
    {
    	Content = "�ύʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
     }
    //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
    System.out.println("------LLSubmitApplyHeadDealSave.jsp end------");
    System.out.println(FlagStr);
    System.out.println(Content);
}	
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>