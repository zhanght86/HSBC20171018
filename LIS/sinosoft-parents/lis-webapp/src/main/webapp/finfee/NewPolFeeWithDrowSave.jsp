
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//�������ƣ�
	//�����ܣ�
	//�������ڣ�2002-07-22 
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="java.lang.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.finfee.*"%>
<%@page import="com.sinosoft.lis.operfee.*"%>

<%
	CErrors tError = null;
	String FlagStr = "";
	String Content = "";
	String LeavingMoney = "";
	NewPolFeeWithdrawBL tNewPolFeeWithdrawBL = new NewPolFeeWithdrawBL();
	try {
		GlobalInput tGI = new GlobalInput();
		tGI = (GlobalInput) session.getValue("GI");

		String PolNo = request.getParameter("ProPosalNo");
		String ContNo = request.getParameter("ContNo");
		LeavingMoney = request.getParameter("LeavingMoney");

		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolSchema.setPolNo(PolNo);
		tLCPolSchema.setContNo(ContNo);
		tLCPolSchema.setLeavingMoney(LeavingMoney);

		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSet.add(tLCPolSchema);

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("SpecWithDraw", "1");

		VData tVData = new VData();
		tVData.addElement(tGI);
		tVData.addElement(tLCPolSet);
		tVData.addElement(tTransferData);

		tNewPolFeeWithdrawBL.submitData(tVData);
	} catch (Exception ex) {
		Content = "ʧ�ܣ�ԭ����:" + ex.toString();
		FlagStr = "Fail";
	}

	if (FlagStr == "") {
		tError = tNewPolFeeWithdrawBL.mErrors;
		if (!tError.needDealError()) {
			Content = "�˷ѳɹ�";
			FlagStr = "Succ";
		} else {
			Content = " ʧ�ܣ�ԭ����:" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}

	loggerDebug("NewPolFeeWithDrowSave",Content);
%>
<html>
<script language="javascript">
        parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");

</script>
</html>

