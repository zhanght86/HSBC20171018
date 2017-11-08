<%
//Name��LLClaimPrepaySave.jsp
//Function����Ԥ�����桰�ύ
//Author��yuejw
//Date:
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
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
	//�������

	//�������
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	GlobalInput tGI = new GlobalInput(); 
	tGI=(GlobalInput)session.getValue("GI"); 
	//LLClaimPrepayUI tLLClaimPrepayUI = new LLClaimPrepayUI(); 
	String busiName="LLClaimPrepayUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	if(tGI == null)
	{
	    FlagStr = "Fail";
	    Content = "ҳ��ʧЧ,�����µ�½";
	    loggerDebug("LLClaimPrepaySave","ҳ��ʧЧ,�����µ�½");    
	}
	else //ҳ����Ч
	{
	    String Operator  = tGI.Operator ; //�����½����Ա�˺�
	    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����
	
	    String transact = request.getParameter("fmtransact"); //��ȡ����insert||update
	    loggerDebug("LLClaimPrepaySave","-----transact= "+transact);
	
	    //***************************************
	    //��ȡҳ����Ϣ 
	    //***************************************   
		LLPrepayDetailSchema tLLPrepayDetailSchema=new LLPrepayDetailSchema(); //Ԥ����ϸ��¼��
	    tLLPrepayDetailSchema.setClmNo(request.getParameter("ClmNo")); //�ⰸ��
	    tLLPrepayDetailSchema.setCaseNo(request.getParameter("CaseNo")); //�ְ���
	    tLLPrepayDetailSchema.setPolNo(request.getParameter("PolNo")); //������
	    tLLPrepayDetailSchema.setGetDutyKind(request.getParameter("GetDutyKind")); //������������
	    tLLPrepayDetailSchema.setGetDutyCode(request.getParameter("GetDutyCode")); //�������α���
	    //tLLPrepayDetailSchema.setSerialNo(request.getParameter("SerialNo")); //��ţ��ɺ�̨����    
	    tLLPrepayDetailSchema.setPrepayNo(request.getParameter("PrepayNo")); //Ԥ�����κ�
	    tLLPrepayDetailSchema.setCurrency(request.getParameter("Currency")); //����
	    tLLPrepayDetailSchema.setPrepaySum(request.getParameter("PrepaySum")); //Ԥ�����
	    tLLPrepayDetailSchema.setCasePayMode(request.getParameter("CasePayMode")); //֧����ʽ
	    
		LLPrepayClaimSchema tLLPrepayClaimSchema=new LLPrepayClaimSchema(); //Ԥ���ⰸ��¼
	    tLLPrepayClaimSchema.setClmNo(request.getParameter("ClmNo")); ////�ⰸ��
	    tLLPrepayClaimSchema.setCaseNo(request.getParameter("ClmNo")); ////�ְ���
	    tLLPrepayClaimSchema.setRgtNo(request.getParameter("ClmNo")); //������
	    tLLPrepayClaimSchema.setCurrency(request.getParameter("Currency"));
	    tLLPrepayClaimSchema.setPrepaySum(request.getParameter("PrepaySum")); //Ԥ���⸶���<���ڸ�������>
	
	 	LLClaimDetailSchema tLLClaimDetailSchema=new LLClaimDetailSchema(); //�⸶��ϸ��
	    tLLClaimDetailSchema.setClmNo(request.getParameter("ClmNo")); //�ⰸ��
	    tLLClaimDetailSchema.setCaseNo(request.getParameter("CaseNo")); ////�ְ���
	    tLLClaimDetailSchema.setPolNo(request.getParameter("PolNo")); //������
	    tLLClaimDetailSchema.setDutyCode(request.getParameter("DutyCode")); //���α���    
	    tLLClaimDetailSchema.setGetDutyKind(request.getParameter("GetDutyKind")); //������������
	    tLLClaimDetailSchema.setGetDutyCode(request.getParameter("GetDutyCode")); //�������α���
	    tLLClaimDetailSchema.setCaseRelaNo(request.getParameter("CaseRelaNo")); //�����¹ʺ�
	    tLLClaimDetailSchema.setCurrency(request.getParameter("Currency"));
	    tLLClaimDetailSchema.setPrepaySum(request.getParameter("PrepaySum")); //Ԥ�����
	    tLLClaimDetailSchema.setCustomerNo(request.getParameter("CustomerNo")); //�ͻ�����
	
		LLClaimSchema tLLClaimSchema=new LLClaimSchema();
		tLLClaimSchema.setClmNo(request.getParameter("ClmNo")); //�ⰸ�� 
		
		LLBalanceSchema tLLBalanceSchema=new LLBalanceSchema();
		tLLBalanceSchema.setClmNo(request.getParameter("ClmNo")); //�ⰸ��
		tLLBalanceSchema.setFeeOperationType("B");
		tLLBalanceSchema.setSubFeeOperationType("B");
		tLLBalanceSchema.setFeeFinaType("YFPK"); //	Ԥ�����Ĳ�������
		tLLBalanceSchema.setBatNo(request.getParameter("PrepayNo")); //Ԥ�����κ�
		tLLBalanceSchema.setPolNo(request.getParameter("PolNo")); //������
		tLLBalanceSchema.setPayFlag("0"); //Ԥ����־
		tLLBalanceSchema.setCurrency(request.getParameter("Currency"));
		tLLBalanceSchema.setPay(request.getParameter("PrepaySum")); //Ԥ�����
		tLLBalanceSchema.setCustomerNo(request.getParameter("CustomerNo"));
		
	    try
	    {
	        //׼���������� VData
	        VData tVData = new VData();
	        tVData.add(tGI);
	        tVData.add(transact);
	        tVData.add(tLLPrepayDetailSchema);
	        tVData.add(tLLPrepayClaimSchema);
	        tVData.add(tLLClaimDetailSchema);
	        tVData.add(tLLClaimSchema);
	        tVData.add(tLLBalanceSchema);
	        
	        try
	        {
//  	            if(!tLLClaimPrepayUI.submitData(tVData,transact))
//	            {           
//	                Content = "�ύʧ�ܣ�ԭ����: " + tLLClaimPrepayUI.mErrors.getError(0).errorMessage;
//	                FlagStr = "Fail";    
//	            }
	            if(!tBusinessDelegate.submitData(tVData,transact,busiName))
				{    
				    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
				    { 
						Content = "�ύʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
						FlagStr = "Fail";
					}
					else
					{
						Content = "�ύʧ��";
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
	        	Content = "�����ύLLClaimPrepayUIʧ�ܣ�ԭ����:" + ex.toString();
	        	FlagStr = "Fail";
	        }	
	    }    
	    catch(Exception ex)
	    {
	        Content = "�����ύLLClaimPrepayUIʧ�ܣ�ԭ����:" + ex.toString();
	        FlagStr = "Fail";
	    }
	    
	    loggerDebug("LLClaimPrepaySave","------LLClaimPrepaySave.jsp end------");
	    loggerDebug("LLClaimPrepaySave",FlagStr);
	    loggerDebug("LLClaimPrepaySave",Content);
	}    
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
