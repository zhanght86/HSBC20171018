<%
//**************************************************************************************************
//Name��LLClaimRegisterConfirmSave.jsp
//Function��������Ϣȷ��
//Author��zhoulei
//Date: 2005-6-20 11:34
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
//�������
CErrors tError = null;
String FlagStr="";
String Content = "";
String wFlag = "";
GlobalInput tGI = new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");

//ҳ����Ч���ж�
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLClaimRegisterConfirmSave","ҳ��ʧЧ,�����µ�½");
}
else 
{
    String RgtConclusion = request.getParameter("RgtConclusion");
    //add by liyan 2016-10-31
    String SimpleFlag = request.getParameter("rgtType");
    if(SimpleFlag == "11"){
    	SimpleFlag = "0";
    }
    if(SimpleFlag == "01"){
    	SimpleFlag = "1";
    }
    //**************************************************
    //��������ز���ʹ��TransferData������ύ
    //**************************************************
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("RptorState", "20");
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("customerNo"));
    mTransferData.setNameAndValue("CustomerName", request.getParameter("customerName"));
    mTransferData.setNameAndValue("CustomerSex", request.getParameter("customerSex"));
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate")); //�¹�����   
    mTransferData.setNameAndValue("RgtClass", "1");  //�������ͣ�����Ϊ1,����Ϊ2
    mTransferData.setNameAndValue("RgtObj", "1");  //��������
    mTransferData.setNameAndValue("RgtObjNo", request.getParameter("RptNo"));  //��������
    mTransferData.setNameAndValue("MngCom", tGI.ManageCom);  //����
    mTransferData.setNameAndValue("PrepayFlag", "0");  //Ԥ����־
    mTransferData.setNameAndValue("ComeWhere", "A");  //����:A����������ˣ�B�������������Ϊ��ͨ����C�������������Ϊ�ⰸΪ������DΪԤ���������
    //------------------------------------------------------------------------------BEG
    //���Ȩ�ޣ���ƥ��������ƺ����service���ȥ������
    //------------------------------------------------------------------------------
//    mTransferData.setNameAndValue("Popedom","A01"); 
    //------------------------------------------------------------------------------END
    //ת����������
    mTransferData.setNameAndValue("SimpleFlag", SimpleFlag);  //�Ƿ���װ���
    //ҵ������Ҫ����
    mTransferData.setNameAndValue("PopedomPhase","A"); //Ȩ�޽׶α�ʾA:���B:����
    mTransferData.setNameAndValue("standpay",request.getParameter("standpay")); //Ԥ�����
    mTransferData.setNameAndValue("adjpay",request.getParameter("adjpay")); //��������
    
    mTransferData.setNameAndValue("RgtConclusion",RgtConclusion);
    
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("BusiType", "1003");
    mTransferData.setNameAndValue("ActivityID", request.getParameter("ActivityID"));
    
    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);


    loggerDebug("LLClaimRegisterConfirmSave","-------------------start workflow---------------------");
    //wFlag = "0000005015";
//	ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI();
//    if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//    {
//        Content = " �����ύClaimWorkFlowUIʧ�ܣ�ԭ����: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//        FlagStr = "Fail";
//    }
//    else
//    {
//        Content = " �����ύ�ɹ���";
//        FlagStr = "Succ";
//    }
   String busiName="tWorkFlowUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
   if(!tBusinessDelegate.submitData(tVData,"execute",busiName))
   {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
				   Content = "�����ύClaimWorkFlowUIʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
		}
		else
		{
				   Content = "����ʧ��";
				   FlagStr = "Fail";				
		}
   }
  else
  {
	    Content = " �����ύ�ɹ���";
	    FlagStr = "Succ";
  }
    loggerDebug("LLClaimRegisterConfirmSave","-------------------end workflow---------------------");

}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>");
</script>
</html>
