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
//������Ϣ������У�鴦����
//�������
LCContPlanDutyParamSchema tLCContPlanDutyParamSchema = new LCContPlanDutyParamSchema();
LCContPlanSchema tLCContPlanSchema = new LCContPlanSchema();
LCContPlanDutyParamSet tLCContPlanDutyParamSet = new LCContPlanDutyParamSet();

LCContPlanUI tLCContPlanUI = new LCContPlanUI();

//�������
CErrors tError = null;
String tRearStr = "";
String tRela = "";
String FlagStr = "Fail";
String Content = "";

//ȫ�ֱ���
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

loggerDebug("GrpFeeSave","begin ...");

String tOperate=request.getParameter("mOperate");	//����ģʽ
String tGrpContNo = request.getParameter("GrpContNo");	//�����ͬ����
String tProposalGrpContNo = request.getParameter("ProposalGrpContNo");	//����Ͷ��������
String tContPlanCode = "00";	//���ϼ���
String tContPlanName = "Ĭ��ֵ";	//����˵��
String tPlanSql = "";	//����˵��
String tPlanType = "3";	//�ƻ�����
String tPeoples3 = "0";	//�ɱ�����

//Ĭ��������Ϣ
tLCContPlanSchema.setGrpContNo(tGrpContNo);
tLCContPlanSchema.setProposalGrpContNo(tProposalGrpContNo);
tLCContPlanSchema.setContPlanCode(tContPlanCode);
tLCContPlanSchema.setContPlanName(tContPlanName);
tLCContPlanSchema.setPlanSql(tPlanSql);
tLCContPlanSchema.setPeoples3(tPeoples3);
tLCContPlanSchema.setPlanType(tPlanType);

//Ĭ��������Ϣ�µ�Ҫ����Ϣ
int lineCount = 0;
String arrCount[] = request.getParameterValues("ContPlanGridNo");
if (arrCount != null){
	String tChk[] = request.getParameterValues("InpContPlanGridChk");
	String tRiskCode[] = request.getParameterValues("ContPlanGrid2");	//���ֱ���
	String tDutyCode[] = request.getParameterValues("ContPlanGrid3");	//���α���
	String tCalFactor[] = request.getParameterValues("ContPlanGrid5");	//����Ҫ����
	String tCalFactorValue[] = request.getParameterValues("ContPlanGrid8");	//����Ҫ��ֵ
	String tRemark[] = request.getParameterValues("ContPlanGrid9");	//�ر�˵��
	String tRiskVersion[] = request.getParameterValues("ContPlanGrid10");	//���ְ汾
	String tGrpPolNo[] = request.getParameterValues("ContPlanGrid11");	//���屣�����ֺ���
	String tMainRiskCode[] = request.getParameterValues("ContPlanGrid12");	//���ձ���
	String tCalFactorType[] = request.getParameterValues("ContPlanGrid13");	//����
	
	lineCount = arrCount.length; //����
	
	for(int i=0;i<lineCount;i++){
		tLCContPlanDutyParamSchema = new LCContPlanDutyParamSchema();
		tLCContPlanDutyParamSchema.setGrpContNo(tGrpContNo);
		tLCContPlanDutyParamSchema.setProposalGrpContNo(tProposalGrpContNo);
		tLCContPlanDutyParamSchema.setContPlanCode(tContPlanCode);
		tLCContPlanDutyParamSchema.setContPlanName(tContPlanName);
		tLCContPlanDutyParamSchema.setRiskCode(tRiskCode[i]);
//		loggerDebug("GrpFeeSave","**********"+tRiskCode[i]);
		tLCContPlanDutyParamSchema.setDutyCode(tDutyCode[i]);
		tLCContPlanDutyParamSchema.setCalFactor(tCalFactor[i]);
		tLCContPlanDutyParamSchema.setCalFactorValue(tCalFactorValue[i].toUpperCase());
		tLCContPlanDutyParamSchema.setRemark(tRemark[i]);
		tLCContPlanDutyParamSchema.setRiskVersion(tRiskVersion[i]);
		tLCContPlanDutyParamSchema.setGrpPolNo(tGrpPolNo[i]);
		tLCContPlanDutyParamSchema.setMainRiskCode(tMainRiskCode[i]);
		tLCContPlanDutyParamSchema.setMainRiskVersion(tRiskVersion[i]);
		tLCContPlanDutyParamSchema.setCalFactorType(tCalFactorType[i]);
		tLCContPlanDutyParamSchema.setPlanType("3");
		tLCContPlanDutyParamSet.add(tLCContPlanDutyParamSchema);
//		loggerDebug("GrpFeeSave","��¼"+i+"����Set��tGrpPolNo[i] is "+tGrpPolNo[i]);
	}
}

// ׼���������� VData
VData tVData = new VData();
FlagStr="";

tVData.add(tG);
tVData.addElement(tLCContPlanDutyParamSet);
tVData.addElement(tLCContPlanSchema);
//tVData.addElement(ProposalGrpContNo);
//tVData.addElement(GrpContNo);
//tVData.addElement(ContPlanCode);
//tVData.addElement(PlanType);
//tVData.addElement(PlanSql);
//tVData.addElement(Peoples3);

try{
	loggerDebug("GrpFeeSave","this will save the data!!!");
	tLCContPlanUI.submitData(tVData,tOperate);
}
catch(Exception ex){
	Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	FlagStr = "Fail";
}

if (!FlagStr.equals("Fail")){
	tError = tLCContPlanUI.mErrors;
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