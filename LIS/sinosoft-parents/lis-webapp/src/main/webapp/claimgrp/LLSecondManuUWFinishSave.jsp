<%
//�������ƣ�LLSecondManuUWFinishSave.jsp
//�����ܣ������˹��˱�----[�˱����]
//�������ڣ�2002-09-24 11:10:36
//������  ��zhangxing
//���¼�¼��  ������    ��������     ����ԭ��/����
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

<%
//�������

//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI"); 
 
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
    
    String tMissionID = request.getParameter("tMissionid");
	String tSubMissionID = request.getParameter("tSubmissionid");
	String tActivityID = request.getParameter("tActivityid");
	
	String tCaseNo = request.getParameter("tCaseNo"); //�ⰸ�ְ���
	String tBatNo = request.getParameter("tBatNo"); ////���κ�
	String tInsuredNo = request.getParameter("tInsuredNo"); //�����˿ͻ���
	String tClaimRelFlag = request.getParameter("tClaimRelFlag"); //�ⰸ��ر�־			
    //*******************��ȡҳ����Ϣ *****************************  
    LLCUWMasterSet tLLCUWMasterSet=new LLCUWMasterSet(); //���˺�ͬ����˱���������
//    LLCUWMasterSchema tLLCUWMasterSchema=new LLCUWMasterSchema(); 
    String tGridNo[] = request.getParameterValues("LLCUWBatchGridNo");  //�õ�����е�����ֵ
	String tContNo[] = request.getParameterValues("LLCUWBatchGrid1"); //�õ���1�е�����ֵ����ͬ���롷
	int  Count = tGridNo.length; //�õ����ܵ��ļ�¼��
	for(int index=0;index<Count;index++)
	{
		LLCUWMasterSchema tLLCUWMasterSchema = new LLCUWMasterSchema(); //���˱�����
		tLLCUWMasterSchema.setCaseNo(tCaseNo); //�ⰸ�ְ���		
		tLLCUWMasterSchema.setContNo(tContNo[index]); //��ͬ��
        tLLCUWMasterSet.add(tLLCUWMasterSchema);
	}
    
 	//������ݡ��ڵ���Ϣ��
	TransferData mTransferData = new TransferData();
	mTransferData.setNameAndValue("CaseNo", tCaseNo);
	mTransferData.setNameAndValue("BatNo", tBatNo);
	mTransferData.setNameAndValue("InsuredNo", tInsuredNo);
	mTransferData.setNameAndValue("ClaimRelFlag", tClaimRelFlag);
	mTransferData.setNameAndValue("MissionID", tMissionID);
	mTransferData.setNameAndValue("SubMissionID", tSubMissionID);
	mTransferData.setNameAndValue("ActivityID", tActivityID);
//    System.out.println("------m"MissionID"------"+ mTransferData.getValueByName("MissionID"));
//    System.out.println("------"tSubMissionID"------"+ mTransferData.getValueByName("SubMissionID"));
//    System.out.println("------"tActivityID"------"+ mTransferData.getValueByName("ActivityID"));
	
	
    try
    {
        //׼���������� VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(transact);
        tVData.add(mTransferData); 
        tVData.add(tLLCUWMasterSet);
        String wFlag="0000005505";    
        try
        {
            System.out.println("---ClaimWorkFlowUI submitData and transact="+wFlag);
            ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI();
            if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
            {
                Content = "�ύ������ʧ�ܣ�ԭ����: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
                FlagStr = "Fail";
            }
            else
        	{
        		//�ӽ������ȡ��ǰ̨��Ҫ����
//                tVData.clear();
//                tVData = tClaimWorkFlowUI.getResult();
//                System.out.println("tVData="+tVData);	    
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
        Content = "�����ύʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
    }
    
    System.out.println("------LLSecondManuUWFinishSave.jsp end------");
    System.out.println(FlagStr);
    System.out.println(Content);
}    
%>
<html>
<script language="javascript">
    parent.fraInterface.UWFinishafterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>