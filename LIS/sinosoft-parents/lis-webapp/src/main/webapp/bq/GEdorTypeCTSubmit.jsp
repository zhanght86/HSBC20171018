
<%
//�������ƣ�GEdorTypeCTSubmit.jsp
//�����ܣ�
//�������ڣ�2002-07-19 16:49:22
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
	String busiName="bqgrpPGrpEdorCTDetailUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	//������Ϣ������У�鴦��
	//�������
	//����������Ϣ
	//loggerDebug("GEdorTypeCTSubmit","-----CTsubmit---");
	LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	

	CErrors tError = null;
	//����Ҫִ�еĶ�������ӣ��޸�

	String FlagStr = "";
	String Content = "";
	String fmAction = "";
	//ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
	fmAction = request.getParameter("fmAction");
	GlobalInput tG = new GlobalInput();
	//loggerDebug("GEdorTypeCTSubmit","------------------begin ui");
	tG = (GlobalInput) session.getValue("GI");

	String edorAcceptNo = request.getParameter("EdorAcceptNo");
	String edorNo = request.getParameter("EdorNo");
	String edorType = request.getParameter("EdorType");
	String grpcontNo = request.getParameter("GrpContNo");
	
	tLPGrpEdorItemSchema.setEdorAcceptNo(edorAcceptNo);
	tLPGrpEdorItemSchema.setEdorNo(edorNo);
	tLPGrpEdorItemSchema.setEdorAppNo(request.getParameter("EdorAppNo"));
	tLPGrpEdorItemSchema.setEdorType(edorType);
	tLPGrpEdorItemSchema.setGrpContNo(grpcontNo);


	try {
		// ׼���������� VData

		VData tVData = new VData();
		tVData.addElement(tG);
		tVData.addElement(tLPGrpEdorItemSchema);
		tBusinessDelegate.submitData(tVData, fmAction,busiName);
	} catch (Exception ex) {
		Content = fmAction + "ʧ�ܣ�ԭ����:" + ex.toString();
		FlagStr = "Fail";
	}
	//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	if (FlagStr == "") {
		tError = tBusinessDelegate.getCErrors();
		if (!tError.needDealError()) {
			Content = " ����ɹ�";
			FlagStr = "Success";
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

