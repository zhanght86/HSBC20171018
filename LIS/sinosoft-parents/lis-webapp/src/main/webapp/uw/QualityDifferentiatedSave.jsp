
<%
//�������ƣ�QualityDifferentiatedSave.jsp
//�����ܣ�ҵ��ԱƷ�ʲ��컯ά��
//�������ڣ�2009-11-09 
%>

<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  //�������
  	CErrors tError = null;
  	String FlagStr = "Fail";
  	String Content = "";
	TransferData tTransferData=new TransferData();
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");

  	if(tG == null) {
		out.println("session has expired");
		return;
	}		
	tTransferData.setNameAndValue("AgentCode",request.getParameter("AgentCode")); //ҵ��Ա����
	tTransferData.setNameAndValue("ManageCom",request.getParameter("ManageCom")); //�������     	     	
	tTransferData.setNameAndValue("UWClass",request.getParameter("UWClass")); //ҵ��Ա���
	tTransferData.setNameAndValue("UWLevel",request.getParameter("UWLevel")); //���컯����

	// ׼���������� VData
	VData tVData = new VData();
	tVData.add( tTransferData );
	tVData.add( tG );

	// ���ݴ���
	QualityDifferentiatedUI tQualityDifferentiatedUI   = new QualityDifferentiatedUI();	
	loggerDebug("QualityDifferentiatedSave","before QualityDifferentiatedUI");
	if (tQualityDifferentiatedUI.submitData(tVData,"PrtReplace") == false) {
		int n = tQualityDifferentiatedUI.mErrors.getErrorCount();
		for (int i = 0; i < n; i++){
			Content = " ӡˢ���滻ʧ�ܣ�ԭ����: " + tQualityDifferentiatedUI.mErrors.getError(0).errorMessage;
		}
		FlagStr = "Fail";
	}
	//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	if (FlagStr == "Fail") {
	    tError = tQualityDifferentiatedUI.mErrors;
		if (!tError.needDealError()) {
			Content = " �޸ĳɹ�! ";
		    FlagStr = "Succ";
		} else {
			Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
