<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLInqConfirmSave.jsp
//�����ܣ������ύ���������Ϣ�������ýڵ㣻Ȼ���ж��Ƿ����������һ���ڵ�
//         �ж���������ѯ ����״̬==��Ϊ��ɡ� and �������<>������� and �������==���λ��� and �ⰸ��==�����ⰸ�ţ�����ڼ�¼��������һ�ڵ�
//�������ڣ�2005-6-22
//���¼�¼��  ������:yuejw    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
//������Ϣ������У�鴦��
//�������
LLInqApplySchema tLLInqApplySchema = new LLInqApplySchema(); //��������
//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI");  
//ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //���⹤��������
//String busiName="ClaimWorkFlowUI";
String busiName="WorkFlowUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLInqConfirmSave","ҳ��ʧЧ,�����µ�½");    
}
else //ҳ����Ч
{
    String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����
    String transact = request.getParameter("fmtransact"); //��ȡ����
    loggerDebug("LLInqConfirmSave","-----transact= "+transact);
    //�����������ͱ𣬸��ݴ�ֵ�����ID��ȡ��������ִ�о���ҵ���߼�
    String wFlag = request.getParameter("WorkFlowFlag");
    //***************************************
    //��ȡҳ����Ϣ�������ֶ���schema����������޸ļ�¼
     tLLInqApplySchema.setClmNo(request.getParameter("ClmNo")); //�ⰸ��
     tLLInqApplySchema.setInqNo(request.getParameter("InqNo")); //�������
     tLLInqApplySchema.setBatNo(request.getParameter("BatNo")); //��������         
     tLLInqApplySchema.setInqState(request.getParameter("InqState")); //����״̬
     tLLInqApplySchema.setInqConclusion(request.getParameter("InqConclusion")); //�������
        
    //String����ɢ����ʹ��TransferData���������׼�� �½���һ�ڵ㣨������ۣ�
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo")); //�ⰸ��
    mTransferData.setNameAndValue("ConNo",request.getParameter("ConNo")); //�������
    mTransferData.setNameAndValue("BatNo",request.getParameter("BatNo")); //��������
    mTransferData.setNameAndValue("InitDept",request.getParameter("InitDept")); //�������
    mTransferData.setNameAndValue("InqDept",request.getParameter("InqDept")); //������� 
    mTransferData.setNameAndValue("ColFlag","2"); //���ܱ�־   
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("ActivityID",request.getParameter("Activityid"));    
    mTransferData.setNameAndValue("InqNo",request.getParameter("InqNo")); //�������  
	mTransferData.setNameAndValue("MngCom", request.getParameter("ManageCom"));  //����      
    mTransferData.setNameAndValue("transact",request.getParameter("fmtransact"));
                        
    try
    {
        //׼���������� VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(mTransferData);
        tVData.add(tLLInqApplySchema);       
    	//wFlag = request.getParameter("Activityid"); //��ʶ���Զ��壩
        try
        {
            loggerDebug("LLInqConfirmSave","---ClaimWorkFlowUI submitData and transact="+transact);
//            if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//            {
//                Content = " �ύ������ClaimWorkFlowUIʧ�ܣ�ԭ����: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//            }
			if(!tBusinessDelegate.submitData(tVData,"execute",busiName))
			{    
			    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			    { 
					Content = "�ύ������WorkFlowUIʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					Content = "�ύ������WorkFlowUIʧ��";
					FlagStr = "Fail";				
				}
			}

            else
        	{
        		//�ӽ������ȡ��ǰ̨��Ҫ����
                tVData.clear();
               // tVData = tClaimWorkFlowUI.getResult();
                tVData = tBusinessDelegate.getResult();
                loggerDebug("LLInqConfirmSave","tVData="+tVData);	    
                Content = "�����ύ�ɹ�";
                FlagStr = "Succ";            
        	}
    	}
    	catch(Exception ex)
        {
        	Content = "�����ύWorkFlowUIʧ�ܣ�ԭ����:" + ex.toString();
        	FlagStr = "Fail";
        }	
    }  
    catch(Exception ex)
    {
        Content = "�����ύWorkFlowUIʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
    }
    
    loggerDebug("LLInqConfirmSave","------LLInqConfirmSave.jsp end------");
    loggerDebug("LLInqConfirmSave",FlagStr);
    loggerDebug("LLInqConfirmSave",Content);
    
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterComfirmSubmit("<%=FlagStr%>","<%=Content%>");
</script>  
