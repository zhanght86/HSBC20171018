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
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.LCContPlanDutyParamSchema"%>
<%@page import="com.sinosoft.lis.schema.LCContPlanSchema"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.vschema.LCContPlanDutyParamSet"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
//������Ϣ������У�鴦��
//�������
LCContPlanDutyParamSchema tLCContPlanDutyParamSchema = new LCContPlanDutyParamSchema();
LCContPlanSchema tLCContPlanSchema = new LCContPlanSchema();
LCContPlanDutyParamSet tLCContPlanDutyParamSet = new LCContPlanDutyParamSet();

//LCContPlanUI tLCContPlanUI = new LCContPlanUI();
String busiName="tbLCContPlanUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

//�������
CErrors tError = null;
String tRearStr = "";
String tRela = "";
String FlagStr = "Fail";
String Content = "";

//ȫ�ֱ���
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

loggerDebug("ContPlanSave","begin ...");

String tOperate=request.getParameter("mOperate");	//����ģʽ
String tGrpContNo = request.getParameter("GrpContNo");	//�����ͬ����
String tProposalGrpContNo = request.getParameter("ProposalGrpContNo");	//����Ͷ��������
String tContPlanCode = request.getParameter("ContPlanCode");	//���ϼ���
String tContPlanName = request.getParameter("ContPlanName");	//����˵��
String tPlanSql = request.getParameter("PlanSql");	//����˵��
String tPeoples3 = request.getParameter("Peoples3");	//�ɱ�����
String tPlanType = "0";	//�ƻ�����

//���ռƻ�������Ϣ
tLCContPlanSchema.setGrpContNo(tGrpContNo);
tLCContPlanSchema.setProposalGrpContNo(tProposalGrpContNo);
tLCContPlanSchema.setContPlanCode(tContPlanCode);
tLCContPlanSchema.setContPlanName(tContPlanName);
tLCContPlanSchema.setPlanSql(tPlanSql);
tLCContPlanSchema.setPeoples3(tPeoples3);
tLCContPlanSchema.setPlanType(tPlanType);

//���ռƻ�Ҫ����Ϣ
int lineCount = 0;
String[] arrCount = request.getParameterValues("ContPlanGridNo");
if (arrCount != null){
	String[] tChk = request.getParameterValues("InpContPlanGridChk");
	String[] tRiskCode = request.getParameterValues("ContPlanGrid2");	//���ֱ���
	String[] tDutyCode = request.getParameterValues("ContPlanGrid3");	//���α���
	String[] tCalFactor = request.getParameterValues("ContPlanGrid5");	//����Ҫ����
	String[] tCalFactorValue = request.getParameterValues("ContPlanGrid8");	//����Ҫ��ֵ
	String[] tRemark = request.getParameterValues("ContPlanGrid9");	//�ر�˵��
	String[] tRiskVersion = request.getParameterValues("ContPlanGrid10");	//���ְ汾
	String[] tGrpPolNo = request.getParameterValues("ContPlanGrid11");	//���屣�����ֺ���
	String[] tMainRiskCode = request.getParameterValues("ContPlanGrid12");	//���ձ���
	String[] tCalFactorType = request.getParameterValues("ContPlanGrid13");	//����
	
	lineCount = arrCount.length; //����
	
	for(int i=0;i<lineCount;i++){
		tLCContPlanDutyParamSchema = new LCContPlanDutyParamSchema();
		tLCContPlanDutyParamSchema.setGrpContNo(tGrpContNo);
		tLCContPlanDutyParamSchema.setProposalGrpContNo(tProposalGrpContNo);
		tLCContPlanDutyParamSchema.setContPlanCode(tContPlanCode);
		tLCContPlanDutyParamSchema.setContPlanName(tContPlanName);
		tLCContPlanDutyParamSchema.setRiskCode(tRiskCode[i]);
//		loggerDebug("ContPlanSave","**********"+tRiskCode[i]);
		tLCContPlanDutyParamSchema.setDutyCode(tDutyCode[i]);
		tLCContPlanDutyParamSchema.setCalFactor(tCalFactor[i]);
		tLCContPlanDutyParamSchema.setCalFactorValue(tCalFactorValue[i]);
		tLCContPlanDutyParamSchema.setRemark(tRemark[i]);
		tLCContPlanDutyParamSchema.setRiskVersion(tRiskVersion[i]);
		tLCContPlanDutyParamSchema.setGrpPolNo(tGrpPolNo[i]);
		tLCContPlanDutyParamSchema.setMainRiskCode(tMainRiskCode[i]);
		tLCContPlanDutyParamSchema.setMainRiskVersion(tRiskVersion[i]);
		tLCContPlanDutyParamSchema.setCalFactorType(tCalFactorType[i]);
		tLCContPlanDutyParamSchema.setPlanType("0");
		tLCContPlanDutyParamSet.add(tLCContPlanDutyParamSchema);
//		loggerDebug("ContPlanSave","��¼"+i+"����Set��tGrpPolNo[i] is "+tCalFactor[i]);
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
	loggerDebug("ContPlanSave","this will save the data!!!");
	tBusinessDelegate.submitData(tVData,tOperate,busiName);
}
catch(Exception ex){
	Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	FlagStr = "Fail";
}

if (!FlagStr.equals("Fail")){
	tError = tBusinessDelegate.getCErrors();
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
