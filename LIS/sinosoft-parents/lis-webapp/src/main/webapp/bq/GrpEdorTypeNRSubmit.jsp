<%
//�������ƣ�GrpEdorTypeNRSubmit.jsp
//�����ܣ�
//�������ڣ�2002-07-19 16:49:22
//������  ��Vivian
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
  
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bqgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	//����������Ϣ
	loggerDebug("GrpEdorTypeNRSubmit","---NR submit---");

	//����Ҫִ�еĶ�������ӣ��޸�
	String FlagStr = "";
	String Content = "";
	String[] tResult=new String[3];
	//ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
	GlobalInput tGlobalInput = new GlobalInput();
	tGlobalInput = (GlobalInput)session.getValue("GI");

LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();

String transact = request.getParameter("fmtransact");
String EdorNo = request.getParameter("EdorNo");
String EdorAcceptNo = request.getParameter("EdorAcceptNo");
String EdorType = request.getParameter("EdorType");
String GrpContNo = request.getParameter("GrpContNo");

tLPGrpEdorItemSchema.setEdorNo(EdorNo);
tLPGrpEdorItemSchema.setEdorType(EdorType);
tLPGrpEdorItemSchema.setGrpContNo(GrpContNo);
tLPGrpEdorItemSchema.setEdorAcceptNo(EdorAcceptNo);

VData tVData = new VData();
tVData.add(tGlobalInput);
tVData.add(tLPGrpEdorItemSchema);	

	GrpEdorNRDetailUI tGrpEdorNRDetailUI = new GrpEdorNRDetailUI();
	if (!tGrpEdorNRDetailUI.submitData(tVData, transact))
	{
		VData rVData = tGrpEdorNRDetailUI.getResult();
		loggerDebug("GrpEdorTypeNRSubmit","Submit Failed! " + tGrpEdorNRDetailUI.mErrors.getErrContent());
		Content = transact + "ʧ�ܣ�ԭ����:" + tGrpEdorNRDetailUI.mErrors.getFirstError();
		FlagStr = "Fail";
	}
	else 
	{
		loggerDebug("GrpEdorTypeNRSubmit","Submit Succed!");
		Content = "����ɹ�";
		FlagStr = "Success";
	}
%>   
                   
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>

