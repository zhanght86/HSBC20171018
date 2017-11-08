<%
//Name��LLSubmitApplyDealSave.jsp
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
//LLSubmitApplyDealUI tLLSubmitApplyDealUI = new LLSubmitApplyDealUI();
//ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //���⹤��������
// busiName="LLSubmitApplyDealUI";
//BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
//String busiName1="ClaimWorkFlowUI";
String busiName ="WorkFlowUI";
BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();

if(tGI == null)   //ҳ����Ч
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLSubmitApplyDealSave","ҳ��ʧЧ,�����µ�½");    
}
else //ҳ����Ч
{
    String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����
    loggerDebug("LLSubmitApplyDealSave","----ComCode= "+tGI.ComCode);
    loggerDebug("LLSubmitApplyDealSave","-----ManageCom= "+ManageCom);
    
    String transact = request.getParameter("fmtransact"); //��ȡ��������insert||update
    loggerDebug("LLSubmitApplyDealSave","-----transact= "+transact);
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
   // mTransferData.setNameAndValue("SubType",request.getParameter("SubType")); //�ʱ����ͣ�1--�ӷֹ�˾���ܹ�˾��
   // loggerDebug("SubType","-----------SubType ="+request.getParameter("SubType"));
    mTransferData.setNameAndValue("SubRCode",request.getParameter("SubRCode")); //�ʱ�ԭ��
	mTransferData.setNameAndValue("MngCom", request.getParameter("ManageCom"));  //����    
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    //add by lzf
    mTransferData.setNameAndValue("transact",request.getParameter("fmtransact"));
    mTransferData.setNameAndValue("DispType",request.getParameter("DispType"));
    mTransferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
    mTransferData.setNameAndValue("SubType","1");//�ʱ����ͣ�1--�ӷֹ�˾���ܹ�˾��
    mTransferData.setNameAndValue("DefaultOperator",Operator);
    //end
    try
    {
    	//׼���������� VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(isReportExist);        
        tVData.add(tLLSubmitApplySchema);
        tVData.add(mTransferData);
        loggerDebug("LLSubmitApplyDealSave","---submitData and transact="+transact);
        if(!tBusinessDelegate1.submitData(tVData,"execute",busiName))
		{    
		    if(tBusinessDelegate1.getCErrors()!=null&&tBusinessDelegate1.getCErrors().getErrorCount()>0)
		    { 
				Content = "�ύ������ʧ�ܣ�ԭ����:" + tBusinessDelegate1.getCErrors().getError(0).errorMessage;
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
        	//�ύ�ɹ����ӽ������ȡ��ǰ̨��Ҫ����
            tVData.clear();
             tVData = tBusinessDelegate1.getResult();   
            Content = "�����ύ�ɹ�";
            FlagStr = "Succ";            
       	} 
         //���ݴ��䣨�����ʱ���ֱ�Ӵ���[�˴���transact �滻mOperate]
/*        if (transact.equals("INSERT||Reporthead"))
        {
            loggerDebug("LLSubmitApplyDealSave","---tLLSubmitApplyDealUI submitData and transact="+transact);
//        	if (!tLLSubmitApplyDealUI.submitData(tVData,transact))
//	        {
//        		Content = " �����ύʧ�ܣ�ԭ����: " + tLLSubmitApplyDealUI.mErrors.getError(0).errorMessage;
//            	FlagStr = "Fail";
//	        }
			if(!tBusinessDelegate.submitData(tVData,transact,busiName))
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
	    	 	//tVData = tLLSubmitApplyDealUI.getResult();
	    	 	tVData = tBusinessDelegate.getResult();
	    	 	Content = "�����ʱ��ύ�ɹ�";
                FlagStr = "Succ";     
	       }
        }
         
         //���ѡ����������ظ���������߹�����  
       if(transact.equals("UPDATE||Replyport")|| transact.equals("UPDATE||Investgate")) //ɾ���ڵ�
        {
            loggerDebug("LLSubmitApplyDealSave","---ClaimWorkFlowUI submitData and transact="+transact);
            wFlag="0000005105";           
//            if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//            {
//                Content = " �ύ������ʧ�ܣ�ԭ����: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//            }   
			if(!tBusinessDelegate1.submitData(tVData,wFlag,busiName1))
			{    
			    if(tBusinessDelegate1.getCErrors()!=null&&tBusinessDelegate1.getCErrors().getErrorCount()>0)
			    { 
					Content = "�ύ������ʧ�ܣ�ԭ����:" + tBusinessDelegate1.getCErrors().getError(0).errorMessage;
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
            	//�ύ�ɹ����ӽ������ȡ��ǰ̨��Ҫ����
                tVData.clear();
                //tVData = tClaimWorkFlowUI.getResult();
                 tVData = tBusinessDelegate1.getResult();
                loggerDebug("LLSubmitApplyDealSave","tVData="+tVData);	    
                Content = "�����ύ�ɹ�";
                FlagStr = "Succ";            
           	}   
        } */
    }
    catch(Exception ex)
    {
    	Content = "�ύʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
     }
    //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
    loggerDebug("LLSubmitApplyDealSave","------LLSubmitApplyDealSave.jsp end------");
    loggerDebug("LLSubmitApplyDealSave",FlagStr);
    loggerDebug("LLSubmitApplyDealSave",Content);
}	
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
