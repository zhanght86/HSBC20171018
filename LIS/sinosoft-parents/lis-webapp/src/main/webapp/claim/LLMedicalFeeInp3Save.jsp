<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLMedicalFeeInp3Save.jsp
//�����ܣ��˲���Ϣ����
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
    loggerDebug("LLMedicalFeeInp3Save","ҳ��ʧЧ,�����µ�½");
}
else
{
    String tOperate = request.getParameter("hideOperate");
    loggerDebug("LLMedicalFeeInp3Save","$$$$$$$$$$$$$$"+request.getParameter("SerialNo3"));
    
    //׼������������Ϣ
    LLCaseInfoSchema tLLCaseInfoSchema = new LLCaseInfoSchema();

    //׼����̨����
    tLLCaseInfoSchema.setClmNo(request.getParameter("claimNo"));
    tLLCaseInfoSchema.setCaseNo(request.getParameter("caseNo"));
    tLLCaseInfoSchema.setSerialNo(request.getParameter("SerialNo3").trim());
    tLLCaseInfoSchema.setCustomerNo(request.getParameter("custNo"));
    
    tLLCaseInfoSchema.setDefoType(request.getParameter("DefoType")); //�˲�����
    tLLCaseInfoSchema.setDefoGrade(request.getParameter("DefoGrade")); //�˲м������
    tLLCaseInfoSchema.setDefoName(request.getParameter("DefoGradeName")); //�˲м�������
    tLLCaseInfoSchema.setDefoCode(request.getParameter("DefoCode")); //�˲д���
    tLLCaseInfoSchema.setDefoCodeName(request.getParameter("DefoCodeName")); //�˲д���
    
    tLLCaseInfoSchema.setDeformityReason(request.getParameter("DeformityReason"));
    tLLCaseInfoSchema.setDeformityRate(request.getParameter("DeformityRate"));
    tLLCaseInfoSchema.setAppDeformityRate(request.getParameter("AppDeformityRate"));
    tLLCaseInfoSchema.setRealRate(request.getParameter("RealRate"));
    tLLCaseInfoSchema.setJudgeOrganName(request.getParameter("JudgeOrganName"));
    tLLCaseInfoSchema.setJudgeDate(request.getParameter("JudgeDate"));
    tLLCaseInfoSchema.setAdjRemark(request.getParameter("Remark9"));

    try
    {
        // ׼���������� VData
        VData tVData = new VData();
        tVData.add( tG );
        tVData.add( tLLCaseInfoSchema );

//        LLMedicalFeeInp3UI tLLMedicalFeeInp3UI = new LLMedicalFeeInp3UI();
//        if (tLLMedicalFeeInp3UI.submitData(tVData,tOperate) == false)
//        {
//            int n = tLLMedicalFeeInp3UI.mErrors.getErrorCount();
//            for (int i = 0; i < n; i++)
//            {
//                Content = Content + "��Ϣ�ύ���棬ԭ����: " + tLLMedicalFeeInp3UI.mErrors.getError(i).errorMessage;
//                FlagStr = "FAIL";
//            }
//        }
		String busiName="LLMedicalFeeInp3UI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
		    {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
		        	int n = tBusinessDelegate.getCErrors().getErrorCount();
			        for (int i = 0; i < n; i++)
			        {
			            //loggerDebug("LLMedicalFeeInp3Save","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
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
