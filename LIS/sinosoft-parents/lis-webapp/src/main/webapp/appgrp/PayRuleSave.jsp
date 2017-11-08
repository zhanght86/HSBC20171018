<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tbgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
//������Ϣ������У�鴦��
//�������
LCPayRuleFactorySchema tLCPayRuleFactorySchema = new LCPayRuleFactorySchema();
LCPayRuleFactorySet tLCPayRuleFactorySet = new LCPayRuleFactorySet();

LCPayRuleFactoryUI tLCPayRuleFactoryUI = new LCPayRuleFactoryUI();

//�������
CErrors tError = null;
String tRearStr = "";
String tRela = "";
String FlagStr = "Fail";
String Content = "";

//ȫ�ֱ���
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

loggerDebug("PayRuleSave","begin ...");

String tOperate=request.getParameter("mOperate");	//����ģʽ
String GrpContNo = request.getParameter("GrpContNo");	//�����ͬ����
String PayRuleCode = request.getParameter("PayRuleCode");	//Ա�����
String PayRuleName = request.getParameter("PayRuleName");	//����˵��

//ȡ�����ֹ������ϸ
int lineCount = 0;
String arrCount[] = request.getParameterValues("PayRuleNewGridNo");
if (arrCount != null){
	String tRiskCode[] = request.getParameterValues("PayRuleNewGrid1");	//���ֱ���
	String tFactoryType[] = request.getParameterValues("PayRuleNewGrid2");	//Ҫ������
	String tOtherNo[] = request.getParameterValues("PayRuleNewGrid3");	//Ŀ������
	String tFactory[] = request.getParameterValues("PayRuleNewGrid4");	//Ҫ�ؼ������
	String tCalRemark[] = request.getParameterValues("PayRuleNewGrid5");	//Ҫ������
	String tParams[] = request.getParameterValues("PayRuleNewGrid6");	//Ҫ��ֵ
	String tFactoryName[] = request.getParameterValues("PayRuleNewGrid7");	//�����������
	String tGrpPolNo[] = request.getParameterValues("PayRuleNewGrid9");	//���屣�����ֺ���
	String tFactoryCode = "";
	String tFactorySubCode = "";
	
	lineCount = arrCount.length; //����
	
	for(int i=0;i<lineCount;i++){
		tLCPayRuleFactorySchema = new LCPayRuleFactorySchema();
	
		tLCPayRuleFactorySchema.setGrpContNo(GrpContNo);
		tLCPayRuleFactorySchema.setPayRuleCode(PayRuleCode);
		tLCPayRuleFactorySchema.setPayRuleName(PayRuleName);
		tLCPayRuleFactorySchema.setRiskCode(tRiskCode[i]);
		tLCPayRuleFactorySchema.setFactoryType(tFactoryType[i]);
		tLCPayRuleFactorySchema.setOtherNo(tOtherNo[i]);
		tFactoryCode = tFactory[i].substring(0,6);
		tFactorySubCode = tFactory[i].substring(6);
		loggerDebug("PayRuleSave",tFactory[i]+"****"+tFactoryCode+"****"+tFactorySubCode);
		tLCPayRuleFactorySchema.setFactoryCode(tFactoryCode);
		tLCPayRuleFactorySchema.setFactorySubCode(tFactorySubCode);
		tLCPayRuleFactorySchema.setCalRemark(tCalRemark[i]);
		tLCPayRuleFactorySchema.setParams(tParams[i]);
		tLCPayRuleFactorySchema.setFactoryName(tFactoryName[i]);
		tLCPayRuleFactorySchema.setGrpPolNo(tGrpPolNo[i]);
	
		tLCPayRuleFactorySet.add(tLCPayRuleFactorySchema);
		loggerDebug("PayRuleSave","��¼"+i+"����Set��");
	}
}
loggerDebug("PayRuleSave","end ...");

// ׼���������� VData
VData tVData = new VData();
FlagStr="";

tVData.add(tG);
tVData.addElement(tLCPayRuleFactorySet);
tVData.addElement(GrpContNo);

try{
	loggerDebug("PayRuleSave","this will save the data!!!");
	tLCPayRuleFactoryUI.submitData(tVData,tOperate);
}
catch(Exception ex){
	Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	FlagStr = "Fail";
}

if (!FlagStr.equals("Fail")){
	tError = tLCPayRuleFactoryUI.mErrors;
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
parent.fraInterface.fm.all('PayRuleCode').value="";
parent.fraInterface.fm.all('PayRuleName').value="";
</script>
</html>
