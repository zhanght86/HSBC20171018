<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLInqDistributeSave.jsp
//�����ܣ���������������
//�������ڣ�
//������  ��
//���¼�¼��  ������:yuejw    ��������     ����ԭ��/����
%>

<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
//�������
LLInqApplySchema tLLInqApplySchema = new LLInqApplySchema(); //��������
//ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //���⹤�������� 
//String busiName="ClaimWorkFlowUI";
String busiName="WorkFlowUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

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
    loggerDebug("LLInqDistributeSave","ҳ��ʧЧ,�����µ�½");    
}
else //ҳ����Ч
{
    String Operator=tGI.Operator ; //�����½����Ա�˺�
    String ManageCom=tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����
    String transact = request.getParameter("fmtransact"); //��ȡ����
    String wFlag = request.getParameter("WorkFlowFlag");
    System.out.println("WorkFlowFlag===="+wFlag);
    String ActivityID = request.getParameter("ActivityID");
    System.out.println("ActivityID===="+ActivityID);
    //***************************************
    //��ȡҳ����Ϣ 
    //***************************************   
    tLLInqApplySchema.setInqPer(request.getParameter("InqPer"));
	tLLInqApplySchema.setClmNo(request.getParameter("tClmNo"));
    tLLInqApplySchema.setInqNo(request.getParameter("tInqNo"));
    
        //Stringʹ��TransferData������ύ
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo",request.getParameter("tClmNo")); //�ⰸ��
    mTransferData.setNameAndValue("InqNo",request.getParameter("tInqNo")); //�������
    mTransferData.setNameAndValue("CustomerNo",request.getParameter("tCustomerNo")); //�����˿ͻ���
    mTransferData.setNameAndValue("CustomerName",request.getParameter("tCustomerName")); //����������
    mTransferData.setNameAndValue("InqDept",request.getParameter("tInitDept")); //��������������  
    mTransferData.setNameAndValue("InqStartDate",PubFun.getCurrentDate()); //�����������  
    mTransferData.setNameAndValue("InqPer",request.getParameter("InqPer")); //ָ���ĵ�����(���ڲ�ѯ����)  
	mTransferData.setNameAndValue("MngCom", request.getParameter("tInitDept"));  //���� ------�����������   
	//Ϊlwmission��defaultoperator������׼����������һ�ڵ�ʱ��Ĭ�ϲ�����Ϊ�������ʱ�ı�ָ���� Add by zhaorx 2006-03-11 
	mTransferData.setNameAndValue("Operator",request.getParameter("InqPer"));  
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));   
    mTransferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));   
    mTransferData.setNameAndValue("BusiType","1006");
    mTransferData.setNameAndValue("DefaultOperator",request.getParameter("InqPer"));  
    mTransferData.setNameAndValue("CreateOrExecuteFlag","1");  //���ڹ�����createaction���Ƿ�ִ�д���������
    try
    {
        //׼���������� VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(mTransferData);
        tVData.add(tLLInqApplySchema);
        wFlag=ActivityID;
        try
        {
            loggerDebug("LLInqDistributeSave","---ClaimWorkFlowUI submitData and transact="+transact);
//            if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//            {
//  	            loggerDebug("LLInqDistributeSave","---@@@@@@@@@@@@@@@");
//                Content = "�ύ������ʧ�ܣ�ԭ����: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//            }
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
        		//�ӽ������ȡ��ǰ̨��Ҫ����
                tVData.clear();
                //tVData = tClaimWorkFlowUI.getResult();
                tVData = tBusinessDelegate.getResult();
                loggerDebug("LLInqDistributeSave","tVData="+tVData);	    
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
    loggerDebug("LLInqDistributeSave","------LLInqDistributeSave.jsp end------");
    loggerDebug("LLInqDistributeSave",FlagStr);
    loggerDebug("LLInqDistributeSave",Content);
}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

