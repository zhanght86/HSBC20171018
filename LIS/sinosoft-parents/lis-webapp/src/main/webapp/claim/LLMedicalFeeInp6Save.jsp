<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLMedicalFeeInp5Save.jsp
//�����ܣ��籣������������Ϣ����
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
    loggerDebug("LLMedicalFeeInp6Save","ҳ��ʧЧ,�����µ�½");
}
else
{
    String tOperate = request.getParameter("hideOperate");
    
    //׼������������Ϣ
    LLOtherFactorSchema tLLOtherFactorSchema = new LLOtherFactorSchema();

    //׼����̨����
    tLLOtherFactorSchema.setClmNo(request.getParameter("claimNo"));
    tLLOtherFactorSchema.setCaseNo(request.getParameter("caseNo"));
    tLLOtherFactorSchema.setSerialNo(request.getParameter("SerialNo6").trim());
    tLLOtherFactorSchema.setCustomerNo(request.getParameter("custNo"));
    
    tLLOtherFactorSchema.setCurrency(request.getParameter("FeeThreeCurrency"));
    tLLOtherFactorSchema.setFactorType(request.getParameter("FeeThreeType"));
    tLLOtherFactorSchema.setFactorCode(request.getParameter("FeeThreeCode"));
    tLLOtherFactorSchema.setFactorName(request.getParameter("FeeThreeName"));
    tLLOtherFactorSchema.setFactorValue(request.getParameter("FeeThreeValue"));
    tLLOtherFactorSchema.setAdjSum(request.getParameter("FeeThreeValue")); //���������Ϊ��ʼ���
    tLLOtherFactorSchema.setUnitNo("0");     //����������0
    tLLOtherFactorSchema.setUnitName(request.getParameter("FeeThreeUnitName"));  //��������
    tLLOtherFactorSchema.setStartDate(request.getParameter("FeeThreeStateDate")); 
    tLLOtherFactorSchema.setEndDate(request.getParameter("FeeThreeEndDate")); 
    tLLOtherFactorSchema.setAdjRemark(request.getParameter("AdjRemark")); 
    tLLOtherFactorSchema.setFeeItemType("D"); //������Ŀ����

    try
    {
        // ׼���������� VData
        VData tVData = new VData();
        tVData.add( tG );
        tVData.add( tLLOtherFactorSchema );

//        LLMedicalFeeInp5UI tLLMedicalFeeInp5UI = new LLMedicalFeeInp5UI();
//        if (tLLMedicalFeeInp5UI.submitData(tVData,tOperate) == false)
//        {
//            int n = tLLMedicalFeeInp5UI.mErrors.getErrorCount();
//            for (int i = 0; i < n; i++)
//            {
//                Content = Content + "��Ϣ�ύ���棬ԭ����: " + tLLMedicalFeeInp5UI.mErrors.getError(i).errorMessage;
//                FlagStr = "FAIL";
//            }
//        }
		String busiName="LLMedicalFeeInp5UI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
		    {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
		        	int n = tBusinessDelegate.getCErrors().getErrorCount();
			        for (int i = 0; i < n; i++)
			        {
			            //loggerDebug("LLMedicalFeeInp6Save","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
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
