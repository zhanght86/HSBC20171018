<%
//**************************************************************************************************
//Name��LLCaseBackFeeSave.jsp
//Function�����������ݽ��ѻ����ύ
//Author��zl
//Date: 2005-10-21 14:33
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
    //׼��ͨ�ò���
    CErrors tError = null;
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput)session.getValue("GI");
    if(tG == null)
    {
        loggerDebug("LLCaseBackFeeSave","��¼��Ϣû�л�ȡ!");
        return;
    }

    //׼������������Ϣ
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo", request.getParameter("ClmNo"));

    try
    {
       //׼���������� VData
       VData tVData = new VData();
       tVData.add(tG);
       tVData.add(mTransferData);
       
//       LLTempFeeConfUI tLLTempFeeConfUI = new LLTempFeeConfUI();
//       if (!tLLTempFeeConfUI.submitData(tVData,""))
//       {
//            Content = "�����ύʧ�ܣ�ԭ����: " + tLLTempFeeConfUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//       }
		String busiName="LLTempFeeConfUI";
		 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		   if(!tBusinessDelegate.submitData(tVData,"",busiName))
		   {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
						   Content = "�����ύʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
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
            Content = "����ɹ�! ";
            FlagStr = "SUCC";
       }
    }
    catch(Exception e)
    {
        e.printStackTrace();
        Content = Content.trim()+".��ʾ���쳣��ֹ!";
    }

%>

<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
