<%
//**************************************************************************************************
//Name��LLBnfRepealSave.jsp
//Function�������˳����ύ
//Author��zhangzheng
//Date: 2009-03-24 
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK"%>
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
LLBnfSet mLLBnfSet = new LLBnfSet();

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLBnfRepealSave","ҳ��ʧЧ,�����µ�½");    
}
else //ҳ����Ч
{

    //Stringʹ��TransferData������ύ
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo", request.getParameter("ClmNo"));
    mTransferData.setNameAndValue("BnfKind", request.getParameter("BnfKind"));
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("insuredno"));
    
    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);

    try
    {
//        LLBnfRepealBL tLLBnfRepealBL = new LLBnfRepealBL();
//        //�����ύ
//        if (!tLLBnfRepealBL.submitData(tVData,"REPEAL"))
//        {
//            Content = " ������������ʧ�ܣ�ԭ����: " + tLLBnfRepealBL.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
String busiName="LLBnfRepealBL";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,"REPEAL",busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "������������ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "������������ʧ��";
		FlagStr = "Fail";				
	}
}

        else
        {
            tVData.clear();
            Content = " �����ύ�ɹ���";
            FlagStr = "Succ";
        }
    }
    catch(Exception ex)
    {
        Content = "�����ύ��������ʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
    }

}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>");
</script>
</html>
