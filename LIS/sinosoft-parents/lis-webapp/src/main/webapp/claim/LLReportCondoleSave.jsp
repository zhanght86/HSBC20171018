<%
//**************************************************************************************************
//Name��LLReportCondoleSave.jsp
//Function������ο��
//Author��zhoulei
//Date: 2005-6-30 16:20
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
    loggerDebug("LLReportCondoleSave","ҳ��ʧЧ,�����µ�½");
}
else
{
    
    //Stringʹ��TransferData������ύ
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("customerNo"));

    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);
        
    try
    {  
        //�ύ����
        loggerDebug("LLReportCondoleSave","-------------------start ����ο��---------------------");
//        LLReportCondoleUI tLLReportCondoleUI = new LLReportCondoleUI();
//        if(!tLLReportCondoleUI.submitData(tVData,"UPDATE"))
//        {
//            Content = " �����ύLLReportCondoleUIʧ�ܣ�ԭ����: " + tLLReportCondoleUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
 String busiName="LLReportCondoleUI";
 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

		if(!tBusinessDelegate.submitData(tVData,"UPDATE",busiName))
		   {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
						   Content = " �����ύLLReportCondoleUIʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
						   FlagStr = "Fail";
				}
				else
				{
						   Content = "�����ύLLReportCondoleUIʧ��";
						   FlagStr = "Fail";				
				}
		   }

        else
        {
            Content = " �����ύ�ɹ���";
            FlagStr = "Succ";
        }
        loggerDebug("LLReportCondoleSave","-------------------end ����ο��---------------------");
    }
    catch(Exception ex)
    {
        Content = "�����ύLLReportCondoleUIʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
    } 
    
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit3("<%=FlagStr%>","<%=Content%>");
</script>
</html>
