<%
//**************************************************************************************************
//Name��LLClaimConfirmReportBackSave.jsp
//Function�����������ϱ�
//Author��zhangzheng
//Date:2007-11-28
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
<%@page import="com.sinosoft.service.*" %>

<%


//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI");  

//ҳ����Ч
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLClaimConfirmReportBackSave","ҳ��ʧЧ,�����µ�½");    
}
else
{
    //��ȡ����insert||update
    String transact = request.getParameter("fmtransact");
    loggerDebug("LLClaimConfirmReportBackSave","LLClaimConfirmReportBackSaveҳ���ò�������="+transact);
    //�����������ͱ𣬸��ݴ�ֵ�����ID��ȡ��������ִ�о���ҵ���߼�
    
    //Stringʹ��TransferData������ύ
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
    
    //��ȡ��������
    LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema(); //���������
    tLLClaimUWMainSchema.setClmNo(request.getParameter("RptNo")); //�ⰸ��
    tLLClaimUWMainSchema.setExamConclusion(request.getParameter("DecisionSP")); //��������
    tLLClaimUWMainSchema.setExamIdea(request.getParameter("RemarkSP")); //�������
 

    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);  
    tVData.add(tLLClaimUWMainSchema); 
        
    try
    {  
        //�ύ����
        loggerDebug("LLClaimConfirmReportBackSave","-------------------start workflow---------------------");

//        LLClaimConfirmReportBackBL tLLClaimConfirmReportBackBL = new LLClaimConfirmReportBackBL();
//        if(!tLLClaimConfirmReportBackBL.submitData(tVData,transact))
//        {
//            Content = " �����ϱ�ʧ�ܣ�ԭ����: " + tLLClaimConfirmReportBackBL.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
		String busiName="LLClaimConfirmReportBackBL";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if(!tBusinessDelegate.submitData(tVData,transact,busiName))
		{    
		    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		    { 
				Content = "�����ϱ�ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			else
			{
				Content = "�����ϱ�ʧ��";
				FlagStr = "Fail";				
			}
		}

        else
        {
            Content = " �����ϱ��ɹ���";
            FlagStr = "Succ";
        }
        loggerDebug("LLClaimConfirmReportBackSave","-------------------end workflow---------------------");
    }
    catch(Exception ex)
    {
        Content = "�����ϱ�ʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
    } 
    
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
