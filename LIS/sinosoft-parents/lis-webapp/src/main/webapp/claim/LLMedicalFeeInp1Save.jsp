<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLMedicalFeeInp1Save.jsp
//�����ܣ�������Ϣ����
//�������ڣ�2005-05-19 11:10:36
//������  ������
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
//׼��ͨ�ò���
CErrors tError = null;
String FlagStr = "FAIL";
String Content = "";
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

//ҳ����Ч��
if(tG == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLMedicalFeeInp1Save","ҳ��ʧЧ,�����µ�½");
}
else
{
    String tOperate = request.getParameter("hideOperate");
    
    //׼������������Ϣ
    LLFeeMainSchema tLLFeeMainSchema = new LLFeeMainSchema();
    LLCaseReceiptSchema tLLCaseReceiptSchema = new LLCaseReceiptSchema();

    //׼����̨����
    tLLFeeMainSchema.setClmNo(request.getParameter("claimNo"));
    tLLFeeMainSchema.setCaseNo(tLLFeeMainSchema.getClmNo());
    tLLFeeMainSchema.setMainFeeNo(request.getParameter("ClinicMainFeeNo").trim()); //�˵���
    tLLFeeMainSchema.setCustomerNo(request.getParameter("custNo")); //�ͻ���
    tLLFeeMainSchema.setHospitalCode(request.getParameter("ClinicHosID")); //ҽԺ����
    tLLFeeMainSchema.setFeeType("A");    //����
            
    //�˵���ϸ��
    tLLCaseReceiptSchema.setClmNo(tLLFeeMainSchema.getClmNo());
    tLLCaseReceiptSchema.setCaseNo(tLLFeeMainSchema.getClmNo());
    tLLCaseReceiptSchema.setRgtNo(tLLFeeMainSchema.getClmNo());
    
    tLLCaseReceiptSchema.setCustomerNo(tLLFeeMainSchema.getCustomerNo()); //�˵��ͻ���
    tLLCaseReceiptSchema.setMainFeeNo(tLLFeeMainSchema.getMainFeeNo()); //�˵���
    tLLCaseReceiptSchema.setFeeDetailNo(request.getParameter("feeDetailNo")); //�˵�������ϸ���

    tLLCaseReceiptSchema.setFeeItemType(tLLFeeMainSchema.getFeeType());    //��Ŀ����(����,סԺ,���ò�����Ŀ)
    tLLCaseReceiptSchema.setFeeItemCode(request.getParameter("ClinicMedFeeType")); //�������ͱ���
    tLLCaseReceiptSchema.setFeeItemName(request.getParameter("ClinicMedFeeTypeName")); //������������
    
    tLLCaseReceiptSchema.setCurrency(request.getParameter("MedCurrency"));
    tLLCaseReceiptSchema.setFee(request.getParameter("ClinicMedFeeSum")); //���ý��
    tLLCaseReceiptSchema.setSelfAmnt(request.getParameter("selfMedFeeSum")); //�Է�/�Ը����
    
    tLLCaseReceiptSchema.setStartDate(request.getParameter("ClinicStartDate")); //��ʼ����
    tLLCaseReceiptSchema.setEndDate(request.getParameter("ClinicEndDate")); //��������
    tLLCaseReceiptSchema.setDayCount(request.getParameter("ClinicDayCount1")); //����
    tLLCaseReceiptSchema.setDealFlag(request.getParameter("DealFlag"));   //��ʼ�����Ƿ����ڳ�������,0��1����

    tLLCaseReceiptSchema.setAdjReason(request.getParameter("theReason1"));
    tLLCaseReceiptSchema.setAdjRemark(request.getParameter("Remark1"));

    try
    {
        // ׼���������� VData
        VData tVData = new VData();
        tVData.add( tG );
        tVData.add( tLLFeeMainSchema );
        tVData.add( tLLCaseReceiptSchema );

//        LLMedicalFeeInp1UI tLLMedicalFeeInp1UI = new LLMedicalFeeInp1UI();
//        if (tLLMedicalFeeInp1UI.submitData(tVData,tOperate) == false)
//        {
//            int n = tLLMedicalFeeInp1UI.mErrors.getErrorCount();
//            for (int i = 0; i < n; i++)
//            {
//                Content = Content + "��Ϣ�ύ���棬ԭ����: " + tLLMedicalFeeInp1UI.mErrors.getError(i).errorMessage;
//                FlagStr = "FAIL";
//            }
//        }
		String busiName="LLMedicalFeeInp1UI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
		    {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
		        	int n = tBusinessDelegate.getCErrors().getErrorCount();
			        for (int i = 0; i < n; i++)
			        {
			            //loggerDebug("LLMedicalFeeInp1Save","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
			            Content = Content + "��Ϣ�ύ���棬ԭ����: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
			        }
		       		FlagStr = "Fail";				   
				}
				else
				{
				   Content = "��Ϣ�ύ����";
				   FlagStr = "Fail";				
				} 
		}

        else
        {
            Content = " ����ɹ�! ";
            FlagStr = "SUCC";
        }
    }
    catch(Exception ex)
    {
        Content = "�����ύʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
    }
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
