
<%
	//�������ƣ�IndiDueFeeQuery.jsp
	//�����ܣ�
	//�������ڣ�
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
	//
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--�û�У����-->
<%@page import="java.util.*"%>
<%@page import="java.lang.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.operfee.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
	//��ȡ������  
	String ContNo = request.getParameter("ContNo");

	// �������
	CErrors tError = null;
	String FlagStr = "";
	String Content = "";

	GlobalInput tGI = new GlobalInput(); 
	tGI = (GlobalInput) session.getValue("GI"); //�μ�loginSubmit.jsp
	if (tGI == null) {
		loggerDebug("IndiDueFeeQueryCancel","ҳ��ʧЧ,�����µ�½");
		FlagStr = "Fail";
		Content = "ҳ��ʧЧ,�����µ�½";
	} else //ҳ����Ч
	{
		LCContSchema tLCContSchema = new LCContSchema(); // ���˱�����
		tLCContSchema.setContNo(ContNo);
		
		VData tVData = new VData();
		tVData.add(tLCContSchema);
		tVData.add(tGI);
		
		//IndiDueFeeCancelUI tIndiDueFeeCancelUI = new IndiDueFeeCancelUI();
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		//tIndiDueFeeCancelUI.submitData(tVData, "INSERT");
		tBusinessDelegate.submitData(tVData, "INSERT","IndiDueFeeCancelUI");
		//tError = tIndiDueFeeCancelUI.mErrors;
		tError = tBusinessDelegate.getCErrors();
		if (!tError.needDealError()) {
			Content = "����ɹ���";
			FlagStr = "Succ";
		} else {
			Content = "����ʧ�ܣ�ԭ��" + tError.getFirstError();
			FlagStr = "Fail";
		}
	} //ҳ����Ч��
%>
<html>
<script language="javascript">
     parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
<body>
</body>
</html>

