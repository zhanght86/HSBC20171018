<%
//**************************************************************************************************
//Name��LLBnfToPensionSave.jsp
//Function���������ύ
//Author��zhoulei
//Date: 2006-3-3 14:33
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
    loggerDebug("LLBnfToPensionSave","ҳ��ʧЧ,�����µ�½");    
}
else //ҳ����Ч
{
    //��ȡ��Ҫ��Ϣ
    String tClmNo = request.getParameter("ClmNo");
    String tPolNo = request.getParameter("polNo");
    String tBnfno = request.getParameter("bnfno");

    //Stringʹ��TransferData������ύ
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo", tClmNo);
    mTransferData.setNameAndValue("PolNo", tPolNo);
    mTransferData.setNameAndValue("bnfno", tBnfno);
    
    //��ȡת���ʽ
    LCGetSchema tLCGetSchema = new LCGetSchema();
    tLCGetSchema.setGetDutyCode(request.getParameter("PensionType"));
    
    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);
    tVData.add(tLCGetSchema);

    try
    {
        //LLClmToPensionBL tLLClmToPensionBL = new LLClmToPensionBL();
//        //�����ύ
//        if (!tLLClmToPensionBL.submitData(tVData,""))
//        {
//            Content = " ��������ʧ�ܣ�ԭ����: " + tLLClmToPensionBL.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
String busiName="LLClmToPensionBL";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,"",busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "��������ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "��������ʧ��";
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
