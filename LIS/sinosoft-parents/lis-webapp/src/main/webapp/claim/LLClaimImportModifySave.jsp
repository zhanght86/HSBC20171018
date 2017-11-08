<%
//**************************************************************************************************
//Name��LLClaimImportModifySave.jsp
//Function����Ҫ��Ϣ�޸��ύ
//Author��quyang
//Date: 2005-6-23 
//Desc: ��ҳ����Ҫ����������ܣ�������Ϣ���жϲ��������ֱ��ύ���������½����������޸���ֱ�Ӵ���
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
loggerDebug("LLClaimImportModifySave","#########################LLClaimImportModifySave.jsp start#################################");
	//�������
	
	LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema(); //�����¼���
	LLCaseSchema tLLCaseSchema = new LLCaseSchema(); //�����ְ�
	LLAppClaimReasonSchema tLLAppClaimReasonSchema = null; //������������
	LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema(); //�����ְ�
	LLAppClaimReasonSet tLLAppClaimReasonSet = new LLAppClaimReasonSet(); //�������ͼ���
	LLAffixSchema tLLAffixSchema = new LLAffixSchema(); //����������
	
	
	
	//�������
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
    
    String newAccNo=""; //���ص����¼�̖

	//ҳ����Ч���ж�
	if(tGI == null)
	{
	    FlagStr = "Fail";
	    Content = "ҳ��ʧЧ,�����µ�½";
	    loggerDebug("LLClaimImportModifySave","ҳ��ʧЧ,�����µ�½");
	}
	else
	{
	    //**********************************************************************************************
	    //��ȡ����ҳ����Ϣ�������ֶ���schema�����String����ɢ����ʹ��TransferData���
	    //**********************************************************************************************
	
	    //��ȡ��������insert||first��insert||customer��update��
	    String transact = request.getParameter("fmtransact");
	    //��������
	    String tClaimType[] = request.getParameterValues("claimType");
		//�������ͱ���
		String newtClaimType[] = request.getParameterValues("newclaimType");
	    //����ԭ��
	    String toccurReason = request.getParameter("newoccurReason");
	    //�����������ͱ𣬸��ݴ�ֵ�����ID��ȡ��������ִ�о���ҵ���߼�
	    String wFlag = request.getParameter("WorkFlowFlag");

	
	    //������--�¹���Ϣ
	    tLLAccidentSchema.setAccNo(request.getParameter("AccNo")); //�¼���
	    tLLAccidentSchema.setAccType(request.getParameter("newoccurReason")); //�¹�����===����ԭ��
	    tLLAccidentSchema.setAccDate(request.getParameter("newAccidentDate")); //�¹�����
	    
	        
	    //������--׼������ǰҵ�����д����ⰸ��                    
	    tLLCaseSchema.setRgtNo(request.getParameter("ClmNo")); //�ⰸ��
	    tLLCaseSchema.setCaseNo(tLLCaseSchema.getRgtNo()); //�ⰸ��
	    tLLCaseSchema.setCustomerNo(request.getParameter("customerNo")); //�����˿ͻ���
	    tLLCaseSchema.setCustomerName(request.getParameter("newcustomerName")); //����������
	    tLLCaseSchema.setCustomerAge(request.getParameter("newcustomerAge")); //����������
	    tLLCaseSchema.setCustomerSex(request.getParameter("newcustomerSex")); //�������Ա�
	
	    tLLCaseSchema.setHospitalCode(request.getParameter("newhospital")); //����ҽԺ
	    tLLCaseSchema.setHospitalName(request.getParameter("newTreatAreaName")); //����ҽԺ����
	    
	    tLLCaseSchema.setMedAccDate(request.getParameter("newMedicalAccidentDate")); //ҽ�Ƴ�������
	    tLLCaseSchema.setAccDate(request.getParameter("newOtherAccidentDate")); //������������
	    tLLCaseSchema.setAccidentDetail(request.getParameter("newaccidentDetail")); //����ϸ��
	    
	    tLLCaseSchema.setCureDesc(request.getParameter("newcureDesc")); //�������            
	    tLLCaseSchema.setAccResult1(request.getParameter("newAccResult1")); //���ս��1
	    tLLCaseSchema.setAccResult2(request.getParameter("newAccResult2")); //���ս��2
	    
	    tLLCaseSchema.setCustState(request.getParameter("newAccDateModSign")); //�¹�����״�������ݵĺ��壺0-�������������޸� 1-�������ڷ������޸�
	    
			
	    if(null!=newtClaimType){
	        for (int i = 0; i < newtClaimType.length; i++)
	        {
	            loggerDebug("LLClaimImportModifySave","newtClaimType"+newtClaimType[i]);
	            newtClaimType[i] = toccurReason + newtClaimType[i];
	            
	            tLLAppClaimReasonSchema = new LLAppClaimReasonSchema();
	            
			    tLLAppClaimReasonSchema.setCaseNo(tLLCaseSchema.getCaseNo()); //�ⰸ��
	            tLLAppClaimReasonSchema.setRgtNo(tLLCaseSchema.getCaseNo()); //����ǼǺ�
	            tLLAppClaimReasonSchema.setCustomerNo(tLLCaseSchema.getCustomerNo()); //�����˱���/�����˿ͻ���
	            tLLAppClaimReasonSchema.setReasonCode(newtClaimType[i]); //��������
	            tLLAppClaimReasonSet.add(tLLAppClaimReasonSchema);
	            
	            tLLAppClaimReasonSchema=null;
	        }
	    }
	
	    tLLRegisterSchema.setAcceptedDate(request.getParameter("newAcceptedDate")); //��������
	    tLLRegisterSchema.setApplyDate(request.getParameter("newApplyDate")); //�ͻ��������� 
	    tLLRegisterSchema.setAccidentDate(tLLAccidentSchema.getAccDate()); //�¹�����
	    
	    tLLRegisterSchema.setRgtantMobile(request.getParameter("newRptorMoPhone")); //�������ֻ����� 
	    tLLRegisterSchema.setRgtantAddress(request.getParameter("newRptorAddress")); //������ͨѶ��ַ
	    tLLRegisterSchema.setPostCode(request.getParameter("newAppntZipCode")); //�������ʱ�
	    
	    tLLAffixSchema.setRgtNo(request.getParameter("ClmNo"));
	    tLLAffixSchema.setReAffixDate(request.getParameter("newAddFileDate"));
	    
	    TransferData mTransferData = new TransferData();
	    mTransferData.setNameAndValue("EditReason", request.getParameter("EditReason")); //�޸ı�ע
	    mTransferData.setNameAndValue("tPayGetMode", request.getParameter("newPayGetMode")); //�⸶����ȡ��ʽ
	    mTransferData.setNameAndValue("CancleMergeFlag",request.getParameter("cancelMergeFlag")); //�Ƿ���������ϲ���Ϣ��־
	
	    //׼���������� VData
	    VData tVData = new VData();
	    tVData.add(tGI);
	    tVData.add(tLLAccidentSchema);    
	    tVData.add(tLLCaseSchema);
	    tVData.add(tLLRegisterSchema);
	    tVData.add(tLLAppClaimReasonSet);
	    tVData.add(tLLAffixSchema);
	    tVData.add(mTransferData);
	     
	    
	    // ���ݴ���
	    //LLClaimImportModifyUI tLLClaimImportModifyUI = new LLClaimImportModifyUI();
	 	String busiName="LLClaimImportModifyUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	 	
	    try
	    {
//			if (!tLLClaimImportModifyUI.submitData(tVData,""))
//			{
//		        Content = " ����ʧ�ܣ�ԭ����: " + tLLClaimImportModifyUI.mErrors.getError(0).errorMessage;
//		        FlagStr = "Fail";
//			}
			if(!tBusinessDelegate.submitData(tVData,"",busiName))
			{    
			    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			    { 
					Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					Content = "����ʧ��";
					FlagStr = "Fail";				
				}
			}

		    else
		    {
		        tVData.clear();
		    	Content = " ����ɹ�! ";
		    	FlagStr = "Succ";
		    	
		    	VData tResult = new VData();
	            //tResult = tLLClaimImportModifyUI.getResult();
	            tResult = tBusinessDelegate.getResult();	 
		        newAccNo = (String)tResult.getObjectByObjectName("String",0);
		        //���û����������ϲ���Ϣ,������¼���û��,�ش�ԭ�����¼���
		        if(newAccNo==null||newAccNo.equals(""))
		        {
		        	newAccNo=tLLAccidentSchema.getAccNo();
		        }
		        
		        loggerDebug("LLClaimImportModifySave","����"+tLLCaseSchema.getCaseNo()+"ԭ���¼���"+tLLAccidentSchema.getAccNo()+",���¼���:"+newAccNo);
		    }
	    }
	    catch(Exception ex)
	    {
	        //Content = "�����ύʧ�ܣ�ԭ����:" + tLLClaimImportModifyUI.mErrors.getLastError();
	       	Content = "�����ύʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getLastError();
	        FlagStr = "Fail";
	    }
	}
	%>
	
	<html>
	<script language="javascript">
	    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=newAccNo%>");
	</script>
</html>
