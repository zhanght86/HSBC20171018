<%
//**************************************************************************************************
//�������ƣ�LLClaimPrepayMissSave.jsp
//�����ܣ�
//�������ڣ�2005-07-06 10:33
//������  ��yuejw
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
//ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //���⹤��������
String busiName="ClaimWorkFlowUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

//ҳ����Ч��
if(tG == null)
{
    loggerDebug("LLClaimPrepayMissSave","session has expired");
    return;
}

//�������,�������ɡ�Ԥ���ڵ㡱
TransferData mTransferData = new TransferData();
mTransferData.setNameAndValue("RptNo",request.getParameter("tRptNo")); //"�ⰸ��" 
mTransferData.setNameAndValue("RptorState",request.getParameter("tRptorState")); //"����״̬"
mTransferData.setNameAndValue("CustomerNo",request.getParameter("tCustomerNo")); //"�����˱���"
mTransferData.setNameAndValue("CustomerName",request.getParameter("tCustomerName")); //"���������� 
mTransferData.setNameAndValue("CustomerSex",request.getParameter("tCustomerSex")); //"�������Ա�"
mTransferData.setNameAndValue("AccDate",request.getParameter("tAccDate")); //"�¹�����" 
mTransferData.setNameAndValue("RgtClass",request.getParameter("tRgtClass")); //"��������" 
mTransferData.setNameAndValue("RgtObj",request.getParameter("tRgtObj")); //"��������" 
mTransferData.setNameAndValue("RgtObjNo",request.getParameter("tRgtObjNo")); //"��������" 
mTransferData.setNameAndValue("Popedom",request.getParameter("tPopedom")); //"����ʦȨ��" 
//mTransferData.setNameAndValue("Popedom",request.getParameter("tPrepayFlag")); //"Ԥ����־" 
mTransferData.setNameAndValue("ComeWhere",request.getParameter("tComeWhere")); //"����" 
mTransferData.setNameAndValue("Auditer",request.getParameter("tAuditer")); //"��˲�����" 
mTransferData.setNameAndValue("MngCom",request.getParameter("tMngCom")); //"����" 
mTransferData.setNameAndValue("Operator",request.getParameter("Operator")); //Ԥ��������
mTransferData.setNameAndValue("ComFlag",request.getParameter("tComFlag")); //Ȩ�޿缶��־
mTransferData.setNameAndValue("BudgetFlag","1");
mTransferData.setNameAndValue("MissionID",request.getParameter("tMissionID"));
mTransferData.setNameAndValue("SubMissionID",request.getParameter("tSubMissionID"));
mTransferData.setNameAndValue("ActivityID",request.getParameter("tActivityID"));
mTransferData.setNameAndValue("IsRunBL","0"); //�����ж��Ƿ�����BL��0--������

try
{
    // ׼���������� VData
    VData tVData = new VData();
    tVData.add(mTransferData);
    tVData.add(tG);
    try
    {
        // if(!tClaimWorkFlowUI.submitData(tVData,"0000005035"))
//        {
//            Content = " �ύ������ʧ�ܣ�ԭ����: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
//      
		 if(!tBusinessDelegate.submitData(tVData,"0000005035",busiName))
		   {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
						   Content = "�ύ������ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
						   FlagStr = "Fail";
				}
				else
				{
						   Content = "�ύ������ʧ��";
						   FlagStr = "Fail";				
				}
		   }
   
        else
    	{
    		//�ӽ������ȡ��ǰ̨��Ҫ����
            tVData.clear();
            //tVData = tClaimWorkFlowUI.getResult();
             tVData = tBusinessDelegate.getResult();
            loggerDebug("LLClaimPrepayMissSave","tVData="+tVData);	    
            Content = "�����ύ�ɹ�";
            FlagStr = "Succ";            
    	}
	}
	catch(Exception ex)
    {
    	Content = "�����ύClaimWorkFlowUIʧ�ܣ�ԭ����:" + ex.toString();
    	FlagStr = "Fail";
    }	
}
catch(Exception ex)
{
    Content = "�����ύClaimWorkFlowUIʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
}
loggerDebug("LLClaimPrepayMissSave","------LLClaimPrepayMissSave.jsp end------");

%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
