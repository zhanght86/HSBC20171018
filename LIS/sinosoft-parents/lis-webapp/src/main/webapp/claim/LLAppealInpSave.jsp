<%
//**************************************************************************************************
//Name��LLAppealSave.jsp
//Function��������������Ϣ�����ύ
//Author��zhoulei
//Date: 2005-7-26 16:33
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
    loggerDebug("LLAppealInpSave","ҳ��ʧЧ,�����µ�½");
}
else
{
    //׼���ύ������
    LLAppealSchema mLLAppealSchema = new LLAppealSchema();
    
    mLLAppealSchema.setAppealNo(request.getParameter("ClmNo"));
    mLLAppealSchema.setAppealType(request.getParameter("AppealType"));
    mLLAppealSchema.setAppealState(request.getParameter("AppealState"));
    mLLAppealSchema.setWaitDate(request.getParameter("WaitDate"));
    mLLAppealSchema.setAppealName(request.getParameter("AppealName"));
    mLLAppealSchema.setAppealSex(request.getParameter("AppealSex"));
    mLLAppealSchema.setAddress(request.getParameter("Address"));
    mLLAppealSchema.setPhone(request.getParameter("Phone"));
    mLLAppealSchema.setMobile(request.getParameter("Mobile"));
    mLLAppealSchema.setAppealMode(request.getParameter("AppealMode"));
    mLLAppealSchema.setPostCode(request.getParameter("PostCode"));
    mLLAppealSchema.setRelation(request.getParameter("Relation2"));
    mLLAppealSchema.setReturnMode(request.getParameter("ReturnMode"));
    mLLAppealSchema.setIDNo(request.getParameter("IDNo"));
    mLLAppealSchema.setIDType(request.getParameter("IDType"));
    mLLAppealSchema.setAppealRDesc(request.getParameter("AppealRDesc"));

    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mLLAppealSchema);

    try
    {
        //�ύ����
        //LLAppealUpdateUI tLLAppealUpdateUI = new LLAppealUpdateUI();
//        if(!tLLAppealUpdateUI.submitData(tVData,""))
//        {
//            Content = " �����ύʧ�ܣ�ԭ����: " + tLLAppealUpdateUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
String busiName="LLAppealUpdateUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,"",busiName))
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
            Content = " �����ύ�ɹ���";
            FlagStr = "Succ";
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
    parent.fraInterface.afterSubmit4("<%=FlagStr%>","<%=Content%>");
</script>
</html>
