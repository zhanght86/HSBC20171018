<%
//**************************************************************************************************
//Name��LLClaimPrepayAuditConclusionSave.jsp
//Function��Ԥ����˽����ύ
//Author��zhangzheng 
//Date: 2008-12-29
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<%

//�������
LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema(); //���������

//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");

//ҳ����Ч���ж�
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLClaimPrepayAuditConclusionSave","ҳ��ʧЧ,�����µ�½");
}
else
{
    //��ȡ������
    String transact = request.getParameter("fmtransact");

    //��ȡ����ҳ����Ϣ
    tLLClaimUWMainSchema.setClmNo(request.getParameter("RptNo")); //�ⰸ��
    tLLClaimUWMainSchema.setCaseNo(tLLClaimUWMainSchema.getClmNo()); //�ְ���
    tLLClaimUWMainSchema.setRgtNo(tLLClaimUWMainSchema.getClmNo()); //������
    tLLClaimUWMainSchema.setAuditConclusion(request.getParameter("AuditConclusion")); //��˽���
    tLLClaimUWMainSchema.setAuditIdea(request.getParameter("AuditIdea")); //������
    tLLClaimUWMainSchema.setAuditLevel(request.getParameter("AuditPopedom")); //������˼���
    tLLClaimUWMainSchema.setCheckType("1"); //�������: 0--��˽���,1--Ԥ����˽���

    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(tLLClaimUWMainSchema);

    try
    {
        //�ύ����
        loggerDebug("LLClaimPrepayAuditConclusionSave","-------------------start LLClaimAuditConclusionSave.jsp---------------------");
//        LLClaimPrepayAuditBL tLLClaimPrepayAuditBL = new LLClaimPrepayAuditBL();
//        if(!tLLClaimPrepayAuditBL.submitData(tVData,transact))
//        {
//            Content = " �����ύLLClaimPrepayAuditUIʧ�ܣ�ԭ����: " + tLLClaimPrepayAuditBL.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
		String busiName="LLClaimPrepayAuditBL";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

		if(!tBusinessDelegate.submitData(tVData,transact,busiName))
		{    
		    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		    { 
				Content = "�����ύLLClaimPrepayAuditUIʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			else
			{
				Content = "�����ύLLClaimPrepayAuditUIʧ��";
				FlagStr = "Fail";				
			}
		}

        else
        {
            Content = " �����ύ�ɹ���";
            FlagStr = "Succ";
        }
        loggerDebug("LLClaimPrepayAuditConclusionSave","-------------------end LLClaimAuditConclusionSave.jsp---------------------");
    }
    catch(Exception ex)
    {
        Content = "�����ύLLClaimAuditUIʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
    }
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit3("<%=FlagStr%>","<%=Content%>");
</script>
</html>
