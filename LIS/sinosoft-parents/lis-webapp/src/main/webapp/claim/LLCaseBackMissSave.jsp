<%
//**************************************************************************************************
//�������ƣ�LLCaseBackMissSave.jsp
//�����ܣ�
//�������ڣ�2005-10-20 17:29
//������  ��wanzh
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
    loggerDebug("LLCaseBackMissSave","ҳ��ʧЧ,���µ�½!");
    return;
}

String tClaimNo = request.getParameter("ClmNo");

//�������
TransferData mTransferData = new TransferData();
mTransferData.setNameAndValue("ClaimNo", tClaimNo);

//׼���������� VData
VData tVData = new VData();
tVData.add(tG);
tVData.add(mTransferData);

// ���ݴ���
// LLCaseBackUpdateUI tLLCaseBackUpdateUI = new LLCaseBackUpdateUI();
//
//if (!tLLCaseBackUpdateUI.submitData(tVData,""))
//{
//    Content = " ����ʧ�ܣ�ԭ����: " + tLLCaseBackUpdateUI.mErrors.getError(0).errorMessage;
//    FlagStr = "Fail";
//}
	String busiName="LLCaseBackUpdateUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	   if(!tBusinessDelegate.submitData(tVData,"",busiName))
	   {    
	        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	        { 
					   Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
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
    tVData.clear();
   // tVData = tLLCaseBackUpdateUI.getResult();
    tVData = tBusinessDelegate.getResult();
    TransferData tTransferData = new TransferData();
    tTransferData = (TransferData) tVData.getObjectByObjectName("TransferData", 0);
    loggerDebug("LLCaseBackMissSave","tTransferData:" + tTransferData);
    String mClmNo = (String) tTransferData.getValueByName("clmno");
    loggerDebug("LLCaseBackMissSave","���ص��ⰸ��Ϊ:" + mClmNo);
%>
<script language="javascript">
    parent.fraInterface.document.all("ClmNo").value="<%=mClmNo%>";
</script>
<%
    Content = " ����ɹ�! ";
    FlagStr = "Succ";
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
