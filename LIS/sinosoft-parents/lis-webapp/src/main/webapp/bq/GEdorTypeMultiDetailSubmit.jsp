<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
	//�������ƣ�GEdorTypeMultiDetailSubmit.jsp
	//�����ܣ�
	//�������ڣ�2002-07-19 16:49:22
	//������  ��CrtHtml���򴴽�
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.bqgrp.*"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
	
	String busiName="bqgrpGEdorTypeZTDetailUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	loggerDebug("GEdorTypeMultiDetailSubmit","=====This is GEdorTypeMultiDetailSubmit.jsp=====\n");

	//������Ϣ������У�鴦��
	String FlagStr = "";
	String Content = "";
	String transact = "";
	String Result = "";
	double tZTMoney = 0.0;
	TransferData tTransferData = new TransferData();
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
	LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
	LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	tLPGrpEdorItemSchema.setEdorAcceptNo(request
			.getParameter("EdorAcceptNo"));
	loggerDebug("GEdorTypeMultiDetailSubmit",request.getParameter("EdorAcceptNo"));
	//tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorNo"));
	tLPGrpEdorItemSchema
			.setGrpContNo(request.getParameter("GrpContNo"));
	tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));
	tLPGrpEdorItemSchema.setEdorTypeCal(request
			.getParameter("EdorTypeCal"));
	tLPGrpEdorItemSchema.setMakeDate(PubFun.getCurrentDate());
	tLPGrpEdorItemSchema.setMakeTime(PubFun.getCurrentTime());
	tLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
	tLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
	tLPGrpEdorItemSchema.setOperator(tG.Operator);
	tLPGrpEdorItemSchema.setManageCom(tG.ManageCom);
	transact = (String) request.getParameter("Transact");
	loggerDebug("GEdorTypeMultiDetailSubmit","=====Detail submit: " + transact);
	if (transact.equals("INSERT||MUlTIEDOR")) {
		String tContNo[] = request.getParameterValues("LCInsuredGrid1");
		String tInsuredNo[] = request
		.getParameterValues("LCInsuredGrid2");
		String tChk[] = request
		.getParameterValues("InpLCInsuredGridChk");
		for (int index = 0; index < tChk.length; index++) {
			if (tChk[index].equals("1")) {

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorAcceptNo(request
				.getParameter("EdorAcceptNo"));
		tLPEdorItemSchema.setEdorNo(request
				.getParameter("EdorNo"));
		tLPEdorItemSchema.setEdorType(request
				.getParameter("EdorType"));
		tLPEdorItemSchema.setContNo(tContNo[index]);
		tLPEdorItemSchema.setInsuredNo(tInsuredNo[index]);
		tLPEdorItemSchema.setPolNo(request
				.getParameter("000000"));
		tLPEdorItemSchema.setMakeDate(PubFun.getCurrentDate());
		tLPEdorItemSchema.setMakeTime(PubFun.getCurrentTime());
		tLPEdorItemSchema
				.setModifyDate(PubFun.getCurrentDate());
		tLPEdorItemSchema
				.setModifyTime(PubFun.getCurrentTime());
		tLPEdorItemSchema.setOperator(tG.Operator);
		tLPEdorItemSchema.setManageCom(tG.ManageCom);
		tLPEdorItemSet.add(tLPEdorItemSchema);
			}
		}
	} else if (transact.equals("DELETE||EDOR")) {
		String tContNo[] = request
		.getParameterValues("LCInsured2Grid1");
		String tInsuredNo[] = request
		.getParameterValues("LCInsured2Grid2");
		String tPolNo[] = request
		.getParameterValues("LCInsured2Grid10");
		String tChk[] = request
		.getParameterValues("InpLCInsured2GridChk");
		for (int index = 0; index < tChk.length; index++) {
			if (tChk[index].equals("1")) {
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorAcceptNo(request
				.getParameter("EdorAcceptNo"));
		tLPEdorItemSchema.setEdorNo(request
				.getParameter("EdorNo"));
		tLPEdorItemSchema.setEdorType(request
				.getParameter("EdorType"));
		tLPEdorItemSchema.setContNo(tContNo[index]);
		tLPEdorItemSchema.setInsuredNo(tInsuredNo[index]);
		tLPEdorItemSchema.setPolNo(tPolNo[index]);
		tLPEdorItemSchema.setMakeDate(PubFun.getCurrentDate());
		tLPEdorItemSchema.setMakeTime(PubFun.getCurrentTime());
		tLPEdorItemSchema
				.setModifyDate(PubFun.getCurrentDate());
		tLPEdorItemSchema
				.setModifyTime(PubFun.getCurrentTime());
		tLPEdorItemSchema.setOperator(tG.Operator);
		tLPEdorItemSchema.setManageCom(tG.ManageCom);

		tLPEdorItemSet.add(tLPEdorItemSchema);
			}
		}
	}

	
	try {
		VData tVData = new VData();
		tVData.addElement(tG);
		tVData.addElement(tLPGrpEdorItemSchema);
		tVData.addElement(tLPEdorItemSet);
		tBusinessDelegate.submitData(tVData, transact,busiName);
	} catch (Exception ex) {
		Content = transact + "ʧ�ܣ�ԭ����:" + ex.toString();
		FlagStr = "Fail";
	}

	//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	if (FlagStr == "") {
		CErrors tError = new CErrors();
		tError = tBusinessDelegate.getCErrors();
		if (!tError.needDealError()) {
			Content = " ����ɹ�! ";
			FlagStr = "Success";
		} else {
			Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>", "<%=Result%>");
</script>
</html>

