<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	//�������ƣ�
	//�����ܣ�
	//�������ڣ�2005-10-22 11:10:36
	//�� �� ��: guanwei
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%
	//���������������ֱ�õ���һҳ���ֵ
	GlobalInput tGlobalInput = new GlobalInput();
	tGlobalInput = (GlobalInput) session.getValue("GI");
	// ./UWManuAddDelSave.jsp?DelPolNo="+tPolNo+"&DelDutyCode="+tDutyCode+"&DelPayPlanCode="+tPayPlanCode;
	String tPolNo = request.getParameter("DelPolNo"); //Ͷ������
	loggerDebug("BQManuAddDelSave","===tPolNo======" + tPolNo);
	String tDutyCode = request.getParameter("DelDutyCode"); //�ӷ�����
	loggerDebug("BQManuAddDelSave","===tDutyCode======" + tDutyCode);
	String tPayPlanCode = request.getParameter("DelPayPlanCode"); //�ӷ�ԭ��
	loggerDebug("BQManuAddDelSave","===tPayPlanType======" + tPayPlanCode);
	String tEdorNo = request.getParameter("EdorNo");
	String tEdorType = request.getParameter("EdorType");

	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("PolNo", tPolNo);
	loggerDebug("BQManuAddDelSave","::::::::::::::polno::::::::::::" + tPolNo);
	tTransferData.setNameAndValue("DutyCode", tDutyCode);
	loggerDebug("BQManuAddDelSave","::::::::::::::tDutyCode::::::::::::"
			+ tDutyCode);
	tTransferData.setNameAndValue("PayPlanCode", tPayPlanCode);
	loggerDebug("BQManuAddDelSave","::::::::::::::tPayPlanType::::::::::::"
			+ tPayPlanCode);
	tTransferData.setNameAndValue("EdorNo", tEdorNo);
	tTransferData.setNameAndValue("EdorType", tEdorType);

	//��TransferData װ��VData
	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
	tVData.addElement(tTransferData);
	tVData.addElement(tGlobalInput);

	//��������
	String Content = "";
	String FlagStr = "";
	loggerDebug("BQManuAddDelSave","CCCCCCCCCCCCCCCCCCCCCCCCCcc");
	BQManuAddDelUI tBQManuAddDelUI = new BQManuAddDelUI();
	if (!tBQManuAddDelUI.submitData(tVData, "")) {
		//VData rVData = tUWManuAddDelUI.mErrors;

		Content = " ����ʧ�ܣ�ԭ����:"
		+ tBQManuAddDelUI.mErrors.getFirstError().toString();
		FlagStr = "Fail";
	} else {
		Content = "^_^ �ѳɹ�ɾ��������¼ ^_^ ";
		FlagStr = "Succ";

		loggerDebug("BQManuAddDelSave",Content + "\n" + FlagStr
		+ "\n---GetReturnFromBankSave End---\n\n");
%>
<html>
<script language="JavaScript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
	//parent.fraInterface.initQuery();
</script>
</html>
<%
}
%>
