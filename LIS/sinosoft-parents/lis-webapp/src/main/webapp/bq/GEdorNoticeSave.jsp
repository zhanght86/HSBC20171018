<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	//EdorApproveSave.jsp
	//�����ܣ���ȫ����
	//�������ڣ�2005-05-08 15:59:52
	//������  ��sinosoft
	//���¼�¼��  ������    ��������     ����ԭ��/����
	//
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
	 String busiName="bqGEdorNoticeSaveUI";
  	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	//�������
	String FlagStr = "";
	String Content = "";


	CErrors tError = new CErrors();

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	VData tVData = new VData();
	tVData.add(tG);
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("AskOperate",request.getParameter("AskOperate"));
	tTransferData.setNameAndValue("AskFlag",request.getParameter("AskFlag"));
	tTransferData.setNameAndValue("AskContent",request.getParameter("AskContent"));
	tTransferData.setNameAndValue("EdorAcceptNo",request.getParameter("EdorAcceptNo"));
	tTransferData.setNameAndValue("OtherNo",request.getParameter("OtherNo"));
	tTransferData.setNameAndValue("PrtSeq",request.getParameter("PrtSeq"));
	tTransferData.setNameAndValue("MyReply",request.getParameter("MyReply"));
	
	tVData.add(tTransferData);
	
	tBusinessDelegate.submitData(tVData,"",busiName);
	
	

	tError = tBusinessDelegate.getCErrors();
	if (!tError.needDealError()) {

		Content = "����ɹ�!";
		FlagStr = "Succ";
	} else {
		Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
		FlagStr = "Fail";
	}
%>
   
<%@page import="com.sinosoft.lis.f1print.PrintManagerBL"%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
   
   
   
 
