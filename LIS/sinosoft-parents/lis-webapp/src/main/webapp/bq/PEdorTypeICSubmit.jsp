<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%
//�������ƣ�PEdorTypeICSubmit.jsp
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
<%@page import="com.sinosoft.lis.bl.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.Date"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.service.*" %> 

<%
	//������Ϣ������У�鴦��
	//�������
	//����������Ϣ

	LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
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
		tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
		tLPEdorItemSchema.setPolNo("000000");
		//��Ŵ˱�������CM��Ŀʱ��������
		tLPEdorItemSchema.setStandbyFlag1(request.getParameter("CMEdorNo"));
	  tLPEdorItemSchema.setStandbyFlag2(request.getParameter("CustomerNo"));

				String tLastName = request.getParameter("LastNameBak");
				String tFirstName =  request.getParameter("FirstNameBak");
				
		tLPInsuredSchema.setEdorType(request
				.getParameter("EdorType"));
		tLPInsuredSchema.setInsuredNo(request
				.getParameter("CustomerNo"));
		tLPInsuredSchema.setContNo(request
				.getParameter("ContNo"));
		tLPInsuredSchema
				.setName(tLastName+tFirstName);
		tLPInsuredSchema.setSex(request.getParameter("SexBak"));
		tLPInsuredSchema.setBirthday(request
				.getParameter("BirthdayBak"));
		tLPInsuredSchema.setIDType(request
				.getParameter("IDTypeBak"));
		tLPInsuredSchema
				.setIDNo(request.getParameter("IDNoBak"));
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
		tVData.addElement(tLPInsuredSchema);
//		if (!tEdorDetailUI.submitData(tVData, transact)) {
		if (!tBusinessDelegate.submitData(tVData, transact ,busiName)) {
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
