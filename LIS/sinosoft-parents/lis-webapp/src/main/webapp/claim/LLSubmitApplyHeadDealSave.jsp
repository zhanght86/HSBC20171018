<%
//Name��LLSubmitApplyHeadDealSave.jsp
//Function���ʱ�������Ϣ�ύ
%>
<!--�û�У����-->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

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
//LLSubmitApplyHeadDealUI tLLSubmitApplyHeadDealUI = new LLSubmitApplyHeadDealUI();
//ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //���⹤��������
//String busiName="LLSubmitApplyHeadDealUI";
String busiName ="WorkFlowUI";//���������� 20130528
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

if(tGI == null)   //ҳ����Ч
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLSubmitApplyHeadDealSave","ҳ��ʧЧ,�����µ�½");    
}
else //ҳ����Ч
{
    String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����    
    String transact = request.getParameter("fmtransact"); //��ȡ��������
    loggerDebug("LLSubmitApplyHeadDealSave","-----transact= "+transact);
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
    mTransferData.setNameAndValue("SubType","2"); //�ʱ����ͣ�2--�ӷֹ�˾���ܹ�˾��
    mTransferData.setNameAndValue("SubRCode",request.getParameter("SubRCode")); //�ʱ�ԭ��
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
    mTransferData.setNameAndValue("DispType","1");
    mTransferData.setNameAndValue("DefaultOperator",Operator);
    mTransferData.setNameAndValue("transact",transact);
    try
    {
    	//׼���������� VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(isReportExist);    
        tVData.add(transact);          
        tVData.add(tLLSubmitApplySchema);
        tVData.add(mTransferData);
        loggerDebug("LLSubmitApplyHeadDealSave","---tLLSubmitApplyHeadDealUI submitData and transact="+transact);
//    	if (!tLLSubmitApplyHeadDealUI.submitData(tVData,transact))
//        {
//    		Content = " �����ύʧ�ܣ�ԭ����: " + tLLSubmitApplyHeadDealUI.mErrors.getError(0).errorMessage;
//        	FlagStr = "Fail";
//        }
		//if(!tBusinessDelegate.submitData(tVData,transact,busiName))
	if(!tBusinessDelegate.submitData(tVData,"execute",busiName))
		{    
		    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		    { 
				Content = "�����ύʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			else
			{
				Content = "�����ύʧ��";
				FlagStr = "Fail";				
			}
		}

        else
       {
        	tVData.clear();
    	 	//tVData = tLLSubmitApplyHeadDealUI.getResult();
    	 	tVData = tBusinessDelegate.getResult();
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
    loggerDebug("LLSubmitApplyHeadDealSave","------LLSubmitApplyHeadDealSave.jsp end------");
    loggerDebug("LLSubmitApplyHeadDealSave",FlagStr);
    loggerDebug("LLSubmitApplyHeadDealSave",Content);
}	
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
