<%
//**************************************************************************************************
//�������ƣ�LLClaimPrepayCofirmSave.jsp
//�����ܣ���Ԥ��ȷ�ϡ��ύ��Ԥ���ڵ����������ɴ���˽ڵ�
//�������ڣ�2005-07-08 9:16
//������  ��yuejw
//���¼�¼��
//**************************************************************************************************
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
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tG = new GlobalInput();
tG = (GlobalInput)session.getValue("GI");
//ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //���⹤��������
//String busiName="ClaimWorkFlowUI";
String busiName = "WorkFlowUI";//20130521 lzf
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

	//ҳ����Ч��
	if(tG == null)
	{
	    loggerDebug("LLClaimPrepayCofirmSave","session has expired");
	    return;
	}

	loggerDebug("LLClaimPrepayCofirmSave","tAuditer:"+request.getParameter("tAuditer"));
	
	//�������,����������Ԥ���ڵ㡱�����ɴ���˽ڵ㣬����ص�����˹���������
	TransferData mTransferData = new TransferData();
	mTransferData.setNameAndValue("RptNo",request.getParameter("ClmNo")); //"�ⰸ��" 
	if(request.getParameter("DecisionSP").equals("2")){
		mTransferData.setNameAndValue("RptorState","35"); //"����״̬"
	}else{
		mTransferData.setNameAndValue("RptorState","30"); //"����״̬"
	}
	mTransferData.setNameAndValue("CustomerNo",request.getParameter("CustomerNo")); //"�����˱���"
	mTransferData.setNameAndValue("CustomerName",request.getParameter("CustomerName")); //"���������� 
	mTransferData.setNameAndValue("CustomerSex",request.getParameter("CustomerSex")); //"�������Ա�"
	mTransferData.setNameAndValue("AccDate",request.getParameter("AccidentDate")); //"�¹�����"
	
	mTransferData.setNameAndValue("RgtClass",request.getParameter("tRgtClass")); //"��������" 
	mTransferData.setNameAndValue("RgtObj",request.getParameter("tRgtObj")); //"��������" 
	mTransferData.setNameAndValue("RgtObjNo",request.getParameter("tRgtObjNo")); //"��������" 
	mTransferData.setNameAndValue("Popedom",request.getParameter("AuditPopedom")); //"����ʦȨ��" 
	
	mTransferData.setNameAndValue("PrepayFlag",request.getParameter("tPrepayFlag")); //"Ԥ����־" 
	mTransferData.setNameAndValue("ComeWhere","C"); //"����" A' then '����' when 'B' then '���' when 'C' then '����' when 'D' then '���װ���'
	
	mTransferData.setNameAndValue("Auditer", request.getParameter("tAuditer")); //��˲�����
	mTransferData.setNameAndValue("MngCom",tG.ManageCom); //"����" 
	mTransferData.setNameAndValue("ComFlag",request.getParameter("tComFlag")); //"Ȩ�޿缶��־" 
	
	mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
	mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
	mTransferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
	mTransferData.setNameAndValue("ExamConclusion",request.getParameter("DecisionSP"));
	mTransferData.setNameAndValue("BudgetFlag","1");//Ԥ����־
	
    //��ȡ��������
    LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema(); //���������
    tLLClaimUWMainSchema.setClmNo(request.getParameter("RptNo")); //�ⰸ��
    tLLClaimUWMainSchema.setExamConclusion(request.getParameter("DecisionSP")); //��������
    tLLClaimUWMainSchema.setExamIdea(request.getParameter("RemarkSP")); //�������
	
	try
	{
	    // ׼���������� VData
	    VData tVData = new VData();
	    tVData.add(mTransferData);
	    tVData.add(tG);
	    tVData.add(tLLClaimUWMainSchema);
	    try
	    {
//	        if(!tClaimWorkFlowUI.submitData(tVData,"0000005025"))
//	        {
//	            Content = " �ύ������ʧ�ܣ�ԭ����: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//	            FlagStr = "Fail";
//	        } 
			//if(!tBusinessDelegate.submitData(tVData,"0000005025",busiName))
		if(!tBusinessDelegate.submitData(tVData,"execute",busiName))
			{    
			    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			    { 
					Content = "�ύ������ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					Content = "�ύ������ʧ��";
					FlagStr = "Fail";				
				}
			}

	        else
	    	{   
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
	loggerDebug("LLClaimPrepayCofirmSave","------LLClaimPrepayMissSave.jsp end------");
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
