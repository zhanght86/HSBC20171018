
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
<%@page import="com.sinosoft.lis.reagent.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.service.*"%>

<%
	//��ȡ������  
	String tGetNoticeNo = request.getParameter("GetNoticeNo");
	String tContNo = request.getParameter("ContNo");
	String tReaSonType = request.getParameter("ReaSonType");
	String tReMark = request.getParameter("ReMark");

	// �������
	CErrors tError = null;
	String FlagStr = "";
	String Content = "";

	GlobalInput tGI = new GlobalInput(); 
	tGI = (GlobalInput) session.getValue("GI"); //�μ�loginSubmit.jsp
	if (tGI == null) {
		loggerDebug("ReVerifyRollBackSave","ҳ��ʧЧ,�����µ�½");
		FlagStr = "Fail";
		Content = "ҳ��ʧЧ,�����µ�½";
	} else //ҳ����Ч
	{
	  TransferData tTransferData = new TransferData();
	  tTransferData.setNameAndValue("GetNoticeNo",tGetNoticeNo);
    tTransferData.setNameAndValue("ContNo",tContNo);
    tTransferData.setNameAndValue("ReaSonType",tReaSonType);
    tTransferData.setNameAndValue("ReMark",tReMark);
    
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(tGI);
		
		//ReVerifyRollBackUI tReVerifyRollBackUI = new ReVerifyRollBackUI();
		//tReVerifyRollBackUI.submitData(tVData, "");
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		tBusinessDelegate.submitData(tVData,"","ReVerifyRollBackUI");
    tError =  tBusinessDelegate.getCErrors();
		
		

		//tError = tReVerifyRollBackUI.mErrors;
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

