<%
//**************************************************************************************************
//�������ƣ�LLClaimReciInfoSave.jsp
//�����ܣ��ռ�����Ϣ¼���ı���
//�������ڣ�2005-10-25
//������  ��niuzj
//���¼�¼��
//**************************************************************************************************
%>

<!--�û�У����-->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%

//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tG = new GlobalInput();
tG = (GlobalInput)session.getValue("GI");

//ҳ����Ч��
if(tG == null)
{
    loggerDebug("LLClaimReciInfoSave","session has expired");
    return;
}

//����̨���ݵĲ���ֵ
String tClmNo = request.getParameter("ClmNo");             //�ⰸ��
loggerDebug("LLClaimReciInfoSave","ClmNo = " + tClmNo); 
                  
String tReciCode = request.getParameter("ReciCode");       //�ռ��˴���
loggerDebug("LLClaimReciInfoSave","ReciCode = " + tReciCode); 
String tReciName = request.getParameter("ReciName");       //�ռ�������
loggerDebug("LLClaimReciInfoSave","ReciName = " + tReciName);
String tRelation = request.getParameter("Relation");       //�ռ���������˹�ϵ
loggerDebug("LLClaimReciInfoSave","Relation = " + tRelation);
String tReciAddress = request.getParameter("ReciAddress"); //�ռ��˵�ַ
loggerDebug("LLClaimReciInfoSave","ReciAddress = " + tReciAddress);
String tReciPhone = request.getParameter("ReciPhone");     //�ռ��˵绰
loggerDebug("LLClaimReciInfoSave","ReciPhone = " + tReciPhone);
String tReciMobile = request.getParameter("ReciMobile");   //�ռ����ֻ�
loggerDebug("LLClaimReciInfoSave","ReciMobile = " + tReciMobile);
String tReciZip = request.getParameter("ReciZip");         //�ռ����ʱ�
loggerDebug("LLClaimReciInfoSave","ReciZip = " + tReciZip);
String tReciSex = request.getParameter("ReciSex");         //�ռ����Ա�
loggerDebug("LLClaimReciInfoSave","ReciSex = " + tReciSex);
String tReciEmail = request.getParameter("ReciEmail");     //�ռ���Email
loggerDebug("LLClaimReciInfoSave","ReciEmail = " + tReciEmail);
String tRemark = request.getParameter("Remark");           //�ռ���ϸ��
loggerDebug("LLClaimReciInfoSave","Remark = " + tRemark);


//�������
TransferData mTransferData = new TransferData();
mTransferData.setNameAndValue("ClmNo", tClmNo);

mTransferData.setNameAndValue("ReciCode", tReciCode);
mTransferData.setNameAndValue("ReciName", tReciName);
mTransferData.setNameAndValue("Relation", tRelation);
mTransferData.setNameAndValue("ReciAddress", tReciAddress);
mTransferData.setNameAndValue("ReciPhone", tReciPhone);
mTransferData.setNameAndValue("ReciMobile", tReciMobile);
mTransferData.setNameAndValue("ReciZip", tReciZip);
mTransferData.setNameAndValue("ReciSex", tReciSex);
mTransferData.setNameAndValue("ReciEmail", tReciEmail);
mTransferData.setNameAndValue("Remark", tRemark);

try
{
    // ׼���������� VData
    VData tVData = new VData();
    tVData.add(mTransferData);
    tVData.add(tG);
    
    // ���ݴ���
//    LLClaimReciInfoUI tLLClaimReciInfoUI = new LLClaimReciInfoUI();
//    if (tLLClaimReciInfoUI.submitData(tVData,""))
//    {
//        int n = tLLClaimReciInfoUI.mErrors.getErrorCount();
//        for (int i = 0; i < n; i++)
//        {
//            loggerDebug("LLClaimReciInfoSave","Error: "+tLLClaimReciInfoUI.mErrors.getError(i).errorMessage);
//            Content = " ����ʧ�ܣ�ԭ����: " + tLLClaimReciInfoUI.mErrors.getError(0).errorMessage;
//        }
//        FlagStr = "Fail";
//    }
	String busiName="LLClaimReciInfoUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	if(!tBusinessDelegate.submitData(tVData,"",busiName))
	    {    
	        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	        { 
	        	int n = tBusinessDelegate.getCErrors().getErrorCount();
		        for (int i = 0; i < n; i++)
		        {
		            loggerDebug("LLClaimReciInfoSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
		            Content = " ����ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
		        }
	       		FlagStr = "Fail";				   
			}
			else
			{
			   Content = "����ʧ��";
			   FlagStr = "Fail";				
			} 
	}

    //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
    if (FlagStr == "Fail")
    {
        //tError = tLLClaimReciInfoUI.mErrors;
        tError = tBusinessDelegate.getCErrors();
        loggerDebug("LLClaimReciInfoSave","tError.getErrorCount:"+tError.getErrorCount());
        if (!tError.needDealError())
        {
            Content = " ����ɹ�! ";
            FlagStr = "Succ";
        }
        else
        {
            Content = " ����ʧ�ܣ�ԭ����:";
            int n = tError.getErrorCount();
            if (n > 0)
            {
                for(int i = 0;i < n;i++)
                {
                    Content = Content.trim() +i+". "+ tError.getError(i).errorMessage.trim()+".";
                }
            }
            FlagStr = "Fail";
        }
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
