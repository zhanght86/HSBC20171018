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
<%@page import="com.sinosoft.lis.config.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
//������Ϣ������У�鴦��
//�������
LDPlanDutyParamSchema tLDPlanDutyParamSchema = new LDPlanDutyParamSchema();
LDPlanDutyParamSet tLDPlanDutyParamSet = new LDPlanDutyParamSet();

CombinationConfigUI tCombinationConfigUI = new CombinationConfigUI();

//�������
CErrors tError = null;
String tRearStr = "";
String tRela = "";
String FlagStr = "Fail";
String Content = "";

//ȫ�ֱ���
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

loggerDebug("CombinationConfigSave","begin ...");

String tOperate=request.getParameter("mOperate");	//����ģʽ
String PlanCode = request.getParameter("ContPlanCode");	//���ϼ���
String PlanName = request.getParameter("ContPlanName");	//����˵��
String PlanSql = request.getParameter("PlanSql");	//����˵��
String Peoples3 = request.getParameter("Peoples3");	//�ɱ�����
String PlanType = "0";	//�ƻ�����
String Remark =StrTool.unicodeToGBK(request.getParameter("Remark")); //�ر�Լ��
String PlanKind1=request.getParameter("PlanKind1"); //�ײͲ㼶1
String PlanKind2=request.getParameter("PlanKind2"); //�ײͲ㼶2
String PlanKind3=request.getParameter("PlanKind3"); //�ײͲ㼶3
String ManageCom=request.getParameter("ManageCom"); //�������
String Portfolioflag = "1";

//���ռƻ�Ҫ����Ϣ
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
		tLDPlanDutyParamSchema = new LDPlanDutyParamSchema();
		tLDPlanDutyParamSchema.setContPlanCode(PlanCode);
		tLDPlanDutyParamSchema.setContPlanName(PlanName);
		tLDPlanDutyParamSchema.setRiskCode(tRiskCode[i]);
		loggerDebug("CombinationConfigSave","**********"+tRiskCode[i]);
		tLDPlanDutyParamSchema.setDutyCode(tDutyCode[i]);
		tLDPlanDutyParamSchema.setCalFactor(tCalFactor[i]);
		tLDPlanDutyParamSchema.setCalFactorValue(tCalFactorValue[i]);
		tLDPlanDutyParamSchema.setRemark(tRemark[i]);
		tLDPlanDutyParamSchema.setRiskVersion(tRiskVersion[i]);
		tLDPlanDutyParamSchema.setMainRiskCode(tMainRiskCode[i]);
		tLDPlanDutyParamSchema.setMainRiskVersion(tRiskVersion[i]);
		tLDPlanDutyParamSchema.setCalFactorType(tCalFactorType[i]);
		tLDPlanDutyParamSchema.setPlanType("0");
		tLDPlanDutyParamSchema.setPortfolioFlag("1");
		tLDPlanDutyParamSet.add(tLDPlanDutyParamSchema);
		loggerDebug("CombinationConfigSave","��¼"+i+"����Set��tGrpPolNo[i] is "+tCalFactor[i]);
	}
}
loggerDebug("CombinationConfigSave","end ...");

// ׼���������� VData
VData tVData = new VData();
FlagStr="";

tVData.add(tG);
tVData.addElement(tLDPlanDutyParamSet);
tVData.addElement(PlanCode);
tVData.addElement(PlanType);
tVData.addElement(PlanSql);
tVData.addElement(Peoples3);
tVData.addElement(Remark);
tVData.addElement(PlanKind1);
tVData.addElement(PlanKind2);
tVData.addElement(PlanKind3);
tVData.addElement(ManageCom);
tVData.addElement(Portfolioflag);

try{
	loggerDebug("CombinationConfigSave","this will save the data!!!");
	tCombinationConfigUI.submitData(tVData,tOperate);
}
catch(Exception ex){
	Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	FlagStr = "Fail";
}

if (!FlagStr.equals("Fail")){
	tError = tCombinationConfigUI.mErrors;
	if (!tError.needDealError()){
		Content = " ����ɹ�! ";
		FlagStr = "Succ";
	}
/* 	else{
		Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
		FlagStr = "Fail";
	} */
}
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
