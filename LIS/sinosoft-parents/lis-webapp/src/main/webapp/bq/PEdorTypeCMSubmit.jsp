
<%
	//�������ƣ�PEdorTypeCMSubmit.jsp
	//�����ܣ�
	//�������ڣ�2002-07-19 16:49:22
	//������  ��CrtHtml���򴴽�
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.Date"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.service.*" %> 

<%
	//������Ϣ������У�鴦��
	//�������
	//����������Ϣ

	LPPersonSchema tLPPersonSchema = new LPPersonSchema();
	LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
//	EdorDetailUI tEdorDetailUI = new EdorDetailUI();
	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	CErrors tError = null;
	//����Ҫִ�еĶ�������ӣ��޸�

	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String transact = "";
	String Result = "";
	GlobalInput tG = new GlobalInput();
	try {
		transact = request.getParameter("fmtransact");

		tG = (GlobalInput) session.getValue("GI");

		//���˱���������Ϣ
		tLPEdorItemSchema.setEdorAcceptNo(request
		.getParameter("EdorAcceptNo"));
		tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
		tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
		tLPEdorItemSchema.setInsuredNo(request.getParameter("CustomerNo"));
		tLPEdorItemSchema.setPolNo("000000");
		tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
		tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorAppDate")); 
	  tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate")); 

		String tDateStr = request.getParameter("Birthday");
		FDate tFD = new FDate();
		Date tDate = tFD.getDate(tDateStr);
		if (tDate == null) {
			FlagStr = "Fail";
			Content = "��������¼������";
		} else {
			if (tDate.after(tFD.getDate(PubFun.getCurrentDate()))) {
		FlagStr = "Fail";
		Content = "�������ڲ������ڽ��죡";
			} else {
				String tLastName = request.getParameter("LastNameBak");
				String tFirstName =  request.getParameter("FirstNameBak");
		tLPPersonSchema.setEdorType(request
				.getParameter("EdorType"));
		tLPPersonSchema.setCustomerNo(request
				.getParameter("CustomerNo"));
		tLPPersonSchema
				.setName(tLastName+tFirstName);
		tLPPersonSchema.setSex(request.getParameter("SexBak"));
		tLPPersonSchema.setBirthday(request
				.getParameter("BirthdayBak"));
		tLPPersonSchema.setIDType(request
				.getParameter("IDTypeBak"));
		tLPPersonSchema
				.setIDNo(request.getParameter("IDNoBak"));
		tLPPersonSchema.setLastName(tLastName);
		tLPPersonSchema.setFirstName(tFirstName);
			}
		}
	} catch (Exception ex) {
		Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
		FlagStr = "Fail";
	}
	try {
		// ׼���������� VData

		// ׼���������� VData
		VData tVData = new VData();
		tVData.addElement(tG);
		tVData.addElement(tLPEdorItemSchema);
		tVData.addElement(tLPPersonSchema);
//		if (!tEdorDetailUI.submitData(tVData, transact)) {
		if (!tBusinessDelegate.submitData(tVData, transact,busiName)) {
//			tError = tEdorDetailUI.mErrors;
			tError = tBusinessDelegate.getCErrors();
			Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
			FlagStr = "Fail";
		} else {

			Content = " ����ɹ���";
			FlagStr = "Success";

		}

	} catch (Exception ex) {
		Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
		FlagStr = "Fail";
	}
	//��Ӹ���Ԥ����
%>

<html>
<script language="JavaScript">
parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
 </script>
</html>
