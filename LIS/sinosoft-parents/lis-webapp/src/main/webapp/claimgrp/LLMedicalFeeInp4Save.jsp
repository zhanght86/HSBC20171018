<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLMedicalFeeInp4Save.jsp
//�����ܣ��������ؼ���Ϣ����
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
<%@page import="com.sinosoft.workflow.tb.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
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
    loggerDebug("LLMedicalFeeInp4Save","ҳ��ʧЧ,�����µ�½");
}
else
{
    String tOperate = request.getParameter("hideOperate");
    
    //׼������������Ϣ
    LLOperationSchema tLLOperationSchema = new LLOperationSchema();

    //׼����̨����
    tLLOperationSchema.setClmNo(request.getParameter("claimNo"));
    tLLOperationSchema.setCaseNo(request.getParameter("caseNo"));
    tLLOperationSchema.setSerialNo(request.getParameter("SerialNo4"));
    tLLOperationSchema.setCustomerNo(request.getParameter("custNo"));
    
    tLLOperationSchema.setOperationType(request.getParameter("OperationType"));
    tLLOperationSchema.setOperationCode(request.getParameter("OperationCode"));
    tLLOperationSchema.setOperationName(request.getParameter("OperationName"));
    tLLOperationSchema.setOpLevel(request.getParameter("OpLevel"));
    tLLOperationSchema.setOpGrade(request.getParameter("OpGrade"));
    tLLOperationSchema.setOpFee(request.getParameter("OpFee"));
    tLLOperationSchema.setMainOp(request.getParameter("MainOp"));
    tLLOperationSchema.setAdjSum(request.getParameter("OpFee"));   //���������Ϊ��ʼ���
    tLLOperationSchema.setUnitNo("0");     //����������0
    tLLOperationSchema.setUnitName(request.getParameter("UnitName"));  //��������
    tLLOperationSchema.setDiagnoseDate(request.getParameter("DiagnoseDate"));  //ȷ������

    try
    {
        // ׼���������� VData
        VData tVData = new VData();
        tVData.add( tG );
        tVData.add( tLLOperationSchema );

//        LLMedicalFeeInp4UI tLLMedicalFeeInp4UI = new LLMedicalFeeInp4UI();
//        if (tLLMedicalFeeInp4UI.submitData(tVData,tOperate) == false)
//        {
//            int n = tLLMedicalFeeInp4UI.mErrors.getErrorCount();
//            for (int i = 0; i < n; i++)
//            {
//                Content = Content + "��Ϣ�ύ���棬ԭ����: " + tLLMedicalFeeInp4UI.mErrors.getError(i).errorMessage;
//                FlagStr = "FAIL";
//            }
//        }
String busiName="grpLLMedicalFeeInp4UI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
        	int n = tBusinessDelegate.getCErrors().getErrorCount();
	        for (int i = 0; i < n; i++)
	        {
	            //loggerDebug("LLMedicalFeeInp4Save","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
	            Content = Content + "��Ϣ�ύ���棬ԭ����: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
	        }
       		FlagStr = "Fail";				   
		}
		else
		{
		   Content = "��Ϣ�ύ����ʧ��";
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
