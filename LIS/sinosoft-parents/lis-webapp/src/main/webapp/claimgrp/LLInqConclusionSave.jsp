<%
//Name��LLInqConclusionSave.jsp
//Function����������ύ
//Author��zhoulei
//Date:  
//Remark: 2005-08-20 �޸ģ����ڱ������������ۣ����ж��Ƿ�����  �ⰸ����ĵ�����۽ڵ㡣
//		�ж���������ѯ ����״̬==��δ��ɡ� and �ⰸ��==�����ⰸ�� and ���ܱ�־=��2-����������ۡ�������ڼ�¼��������һ�ڵ�
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
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//************************
//������Ϣ������У�鴦��
//************************

//�������
LLInqConclusionSchema tLLInqConclusionSchema = new LLInqConclusionSchema(); //�������

//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI");  
ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //���⹤��������

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLInqConclusionSave","ҳ��ʧЧ,�����µ�½");    
}
else //ҳ����Ч
{
    String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����

    String transact = request.getParameter("fmtransact"); //��ȡ����insert||update
    loggerDebug("LLInqConclusionSave","-----transact= "+transact);
    //�����������ͱ𣬸��ݴ�ֵ�����ID��ȡ��������ִ�о���ҵ���߼�
    String wFlag = request.getParameter("WorkFlowFlag");
    //***************************************
    //��ȡҳ����Ϣ 
    //***************************************   
    tLLInqConclusionSchema.setClmNo(request.getParameter("ClmNo")); //�ⰸ��
    tLLInqConclusionSchema.setConNo(request.getParameter("ConNo")); //�������
    tLLInqConclusionSchema.setInqConclusion(request.getParameter("InqConclusion")); //�������
    tLLInqConclusionSchema.setRemark(request.getParameter("Remark")); //��ע
    tLLInqConclusionSchema.setInitDept(request.getParameter("InitDept")); //�������
    tLLInqConclusionSchema.setInqDept(request.getParameter("InitDept")); //�������
     //String����ɢ����ʹ��TransferData���������׼�������ڵ㣨����������ۣ������� ���ⰸ������۽ڵ㣩
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo")); //�ⰸ��
    mTransferData.setNameAndValue("ConNo",request.getParameter("ConNo")); //������� 
    mTransferData.setNameAndValue("BatNo",request.getParameter("BatNo")); //��������
    mTransferData.setNameAndValue("InitDept",request.getParameter("InitDept")); //�������
    mTransferData.setNameAndValue("InqDept",request.getParameter("InitDept")); //�������  
    mTransferData.setNameAndValue("ColFlag",request.getParameter("0")); //���ܱ�־ 
    mTransferData.setNameAndValue("MngCom", request.getParameter("ManageCom"));  //����  
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("Activityid",request.getParameter("Activityid"));    
    mTransferData.setNameAndValue("transact",request.getParameter("fmtransact"));
 
    try
    {
        //׼���������� VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(mTransferData);
        tVData.add(tLLInqConclusionSchema);       
    	wFlag = "0000005165"; //��ʶ���Զ��壩
        try
        {
            loggerDebug("LLInqConclusionSave","---ClaimWorkFlowUI submitData and transact="+transact);
            if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
            {
                Content = " �ύ������ʧ�ܣ�ԭ����: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
                FlagStr = "Fail";
            }
            else
        	{
        		//�ӽ������ȡ��ǰ̨��Ҫ����
//                tVData.clear();
//                tVData = tClaimWorkFlowUI.getResult();
//                loggerDebug("LLInqConclusionSave","tVData="+tVData);	    
                Content = "�����ύ�ɹ�";
                FlagStr = "Succ";            
        	}
    	}
    	catch(Exception ex)
        {
        	Content = "�����ύʧ�ܣ�ԭ����:" + ex.toString();
        	FlagStr = "Fail";
        }	
    }  
    catch(Exception ex)
    {
        Content = "�����ύʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
    }
    
    loggerDebug("LLInqConclusionSave","------LLInqConclusionSave.jsp end------");
    loggerDebug("LLInqConclusionSave",FlagStr);
    loggerDebug("LLInqConclusionSave",Content);
    
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
