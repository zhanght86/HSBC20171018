
<%
	//�������ƣ�IndiFinVerifyUrgeGet.jsp
	//�����ܣ�
	//�������ڣ�
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
	//
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.operfee.*"%>
<%@page import="com.sinosoft.lis.finfee.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK"%>
<%
	GlobalInput tGI = new GlobalInput(); //repair:
	tGI = (GlobalInput) session.getValue("GI"); //�μ�loginSubmit.jsp
	
	// �������
	CErrors tError = null;
	String FlagStr = "";
	String Content = "";

	int recordCount = 0;
	String TempFeeNo = request.getParameter("TempFeeNo");
	
	LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
	tLJTempFeeSchema.setTempFeeNo(TempFeeNo);
	
	VData tVData = new VData();
	tVData.add(tGI);
	tVData.add(tLJTempFeeSchema);

	IndiNormalPayVerifyUI tIndiNormalPayVerifyUI = new IndiNormalPayVerifyUI();
	tIndiNormalPayVerifyUI.submitData(tVData, "VERIFY");
	tError = tIndiNormalPayVerifyUI.mErrors;
	if (!tError.needDealError()) {
		Content = " ��������ɹ�";
		FlagStr = "Succ";
	} else {
		Content = " ��������ʧ�ܣ�ԭ����:" + tError.getFirstError();
		FlagStr = "Fail";
	}

	Content.replace('\"', '$');
	System.out.println("Content:" + Content);
%>
<HTML>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</HTML>
