<%
//**************************************************************************************************
//Name��LLClaimPolDealSave.jsp
//Function�����������ύ
//Author��zl
//Date: 2005-7-20 15:05
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
//׼��ͨ�ò���
CErrors tError = null;
String FlagStr = "FAIL";
String Content = "";
GlobalInput tG = new GlobalInput();
tG = (GlobalInput)session.getValue("GI");

//ҳ����Ч��
if(tG == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLClaimPolDealSave","ҳ��ʧЧ,�����µ�½");    
}
else
{
    //׼������������Ϣ
    TransferData tTransferData = new TransferData(); 
    tTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo"));
 
    try
    {    
        // ׼���������� VData
        VData tVData = new VData();
        
        tVData.add( tG );        
        tVData.add( tTransferData );        

//        LLClaimPolDealUI tLLClaimPolDealUI = new LLClaimPolDealUI();
//        if (tLLClaimPolDealUI.submitData(tVData,"") == false)
//        {
//
//            Content = "�����ύʧ�ܣ�ԭ����: " + tLLClaimPolDealUI.mErrors.getError(0).errorMessage;
//            FlagStr = "FAIL";
//  
//        }
		String busiName="LLClaimPolDealUI";
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
            Content = "�ύ�ɹ�! ";
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
