<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GrpFeeSave.jsp
//�����ܣ�
//�������ڣ�2002-08-16 15:12:33
//������ ��CrtHtml���򴴽�
//���¼�¼�� ������  ��������   ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tbgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
//������Ϣ������У�鴦��
//�������
LCContPlanFactorySchema tLCContPlanFactorySchema = new LCContPlanFactorySchema();
LCContPlanFactorySet tLCContPlanFactorySet = new LCContPlanFactorySet();

LCContPlanFactoryUI tLCContPlanFactoryUI = new LCContPlanFactoryUI();

//�������
CErrors tError = null;
String tRearStr = "";
String tRela = "";
String FlagStr = "Fail";
String Content = "";

//ȫ�ֱ���
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

loggerDebug("ContPlanNextSave","begin ...");

String tOperate=request.getParameter("mOperate");	//����ģʽ
String GrpContNo = request.getParameter("GrpContNo");	//�����ͬ����
String ProposalGrpContNo = request.getParameter("ProposalGrpContNo");	//����Ͷ��������

//ȡ�����ֹ������ϸ
int lineCount = 0;
String arrCount[] = request.getParameterValues("ImpartGridNo");
if (arrCount != null){
	String tContPlanCode[] = request.getParameterValues("ImpartGrid1");	//�ƻ�����
	String tRiskCode[] = request.getParameterValues("ImpartGrid2");	//���ֱ���
	String tFactoryType[] = request.getParameterValues("ImpartGrid3");	//Ҫ������
	String tOtherNo[] = request.getParameterValues("ImpartGrid4");	//Ŀ������
	String tFactory[] = request.getParameterValues("ImpartGrid5");	//Ҫ�ؼ������
	String tCalRemark[] = request.getParameterValues("ImpartGrid6");	//Ҫ������
	String tParams[] = request.getParameterValues("ImpartGrid7");	//Ҫ��ֵ
	String tContPlanName[] = request.getParameterValues("ImpartGrid10");	//�ƻ�����
	String tRiskVersion[] = request.getParameterValues("ImpartGrid11");	//���ְ汾
	String tFactoryName[] = request.getParameterValues("ImpartGrid12");	//�����������
	String tMainRiskCode[] = request.getParameterValues("ImpartGrid13");	//���ձ���
	String tMainRiskVersion[] = request.getParameterValues("ImpartGrid14");	//���հ汾
	String tFactoryCode = "";
	String tFactorySubCode = "";
	
	lineCount = arrCount.length; //����
	
	for(int i=0;i<lineCount;i++){
		tLCContPlanFactorySchema = new LCContPlanFactorySchema();
	
		tLCContPlanFactorySchema.setGrpContNo(GrpContNo);
		tLCContPlanFactorySchema.setProposalGrpContNo(ProposalGrpContNo);
		tLCContPlanFactorySchema.setContPlanCode(tContPlanCode[i]);
		tLCContPlanFactorySchema.setRiskCode(tRiskCode[i]);
		tLCContPlanFactorySchema.setFactoryType(tFactoryType[i]);
		tLCContPlanFactorySchema.setOtherNo(tOtherNo[i]);
		tFactoryCode = tFactory[i].substring(0,6);
		tFactorySubCode = tFactory[i].substring(6);
		loggerDebug("ContPlanNextSave",tFactory[i]+"****"+tFactoryCode+"****"+tFactorySubCode);
		tLCContPlanFactorySchema.setFactoryCode(tFactoryCode);
		tLCContPlanFactorySchema.setFactorySubCode(tFactorySubCode);
		tLCContPlanFactorySchema.setCalRemark(tCalRemark[i]);
		tLCContPlanFactorySchema.setParams(tParams[i]);
		tLCContPlanFactorySchema.setContPlanName(tContPlanName[i]);
		tLCContPlanFactorySchema.setRiskVersion(tRiskVersion[i]);
		tLCContPlanFactorySchema.setFactoryName(tFactoryName[i]);
		tLCContPlanFactorySchema.setMainRiskCode(tMainRiskCode[i]);
		tLCContPlanFactorySchema.setMainRiskVersion(tMainRiskVersion[i]);
		tLCContPlanFactorySchema.setPlanType("0");	//�ƻ����ͣ�Ĭ��Ϊ�̶��ƻ�
	
		tLCContPlanFactorySet.add(tLCContPlanFactorySchema);
		loggerDebug("ContPlanNextSave","��¼"+i+"����Set��");
	}
}
loggerDebug("ContPlanNextSave","end ...");

// ׼���������� VData
VData tVData = new VData();
FlagStr="";

tVData.add(tG);
tVData.addElement(tLCContPlanFactorySet);
tVData.addElement(ProposalGrpContNo);
tVData.addElement(GrpContNo);

try{
	loggerDebug("ContPlanNextSave","this will save the data!!!");
	tLCContPlanFactoryUI.submitData(tVData,tOperate);
}
catch(Exception ex){
	Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	FlagStr = "Fail";
}

if (!FlagStr.equals("Fail")){
	tError = tLCContPlanFactoryUI.mErrors;
	if (!tError.needDealError()){
		Content = " ����ɹ�! ";
		FlagStr = "Succ";
	}
	else{
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
