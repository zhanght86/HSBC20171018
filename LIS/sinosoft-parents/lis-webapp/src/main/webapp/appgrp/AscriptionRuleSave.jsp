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
LCAscriptionRuleFactorySchema tLCAscriptionRuleFactorySchema = new LCAscriptionRuleFactorySchema();
LCAscriptionRuleFactorySet tLCAscriptionRuleFactorySet = new LCAscriptionRuleFactorySet();

LCAscriptionRuleFactoryUI tLCAscriptionRuleFactoryUI = new LCAscriptionRuleFactoryUI();

//�������
CErrors tError = null;
String tRearStr = "";
String tRela = "";
String FlagStr = "Fail";
String Content = "";

//ȫ�ֱ���
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

loggerDebug("AscriptionRuleSave","begin ...");

String tOperate=request.getParameter("mOperate");	//����ģʽ
String GrpContNo = request.getParameter("GrpContNo");	//�����ͬ����
String AscriptionRuleCode = request.getParameter("AscriptionRuleCode");	//Ա�����
String AscriptionRuleName = request.getParameter("AscriptionRuleName");	//����˵��

//ȡ�����ֹ������ϸ
int lineCount = 0;
String arrCount[] = request.getParameterValues("AscriptionRuleNewGridNo");
if (arrCount != null){
	String tRiskCode[] = request.getParameterValues("AscriptionRuleNewGrid1");	//���ֱ���
	String tAscriptType[] = request.getParameterValues("AscriptionRuleNewGrid2");	//����       
	String tFactoryType[] = request.getParameterValues("AscriptionRuleNewGrid3");	//Ҫ������
	String tOtherNo[] = request.getParameterValues("AscriptionRuleNewGrid4");	//Ŀ������
	String tFactory[] = request.getParameterValues("AscriptionRuleNewGrid5");	//Ҫ�ؼ������
	String tCalRemark[] = request.getParameterValues("AscriptionRuleNewGrid6");	//Ҫ������
	String tParams[] = request.getParameterValues("AscriptionRuleNewGrid7");	//Ҫ��ֵ
	String tFactoryName[] = request.getParameterValues("AscriptionRuleNewGrid8");	//�����������
	String tGrpPolNo[] = request.getParameterValues("AscriptionRuleNewGrid10");	//���屣�����ֺ���
	String tFactoryCode = "";
	String tFactorySubCode = "";
	
	lineCount = arrCount.length; //����
	loggerDebug("AscriptionRuleSave","lineCount:"+lineCount);	
	for(int i=0;i<lineCount;i++){
		tLCAscriptionRuleFactorySchema = new LCAscriptionRuleFactorySchema();
	
		tLCAscriptionRuleFactorySchema.setGrpContNo(GrpContNo);
		tLCAscriptionRuleFactorySchema.setAscriptionRuleCode(AscriptionRuleCode);
		tLCAscriptionRuleFactorySchema.setAscriptionRuleName(AscriptionRuleName);
		tLCAscriptionRuleFactorySchema.setRiskCode(tRiskCode[i]);
		tLCAscriptionRuleFactorySchema.setAscriptType(tAscriptType[i]);
		tLCAscriptionRuleFactorySchema.setFactoryType(tFactoryType[i]);
		tLCAscriptionRuleFactorySchema.setOtherNo(tOtherNo[i]);
		tFactoryCode = tFactory[i].substring(0,6);
		tFactorySubCode = tFactory[i].substring(6);
		loggerDebug("AscriptionRuleSave",tFactory[i]+"****"+tFactoryCode+"****"+tFactorySubCode);
		tLCAscriptionRuleFactorySchema.setFactoryCode(tFactoryCode);
		tLCAscriptionRuleFactorySchema.setFactorySubCode(tFactorySubCode);
		tLCAscriptionRuleFactorySchema.setCalRemark(tCalRemark[i]);
		tLCAscriptionRuleFactorySchema.setParams(tParams[i]);
		tLCAscriptionRuleFactorySchema.setFactoryName(tFactoryName[i]);
		tLCAscriptionRuleFactorySchema.setGrpPolNo(tGrpPolNo[i]);
	
		tLCAscriptionRuleFactorySet.add(tLCAscriptionRuleFactorySchema);
		loggerDebug("AscriptionRuleSave","��¼"+i+"����Set��");
	}
}
loggerDebug("AscriptionRuleSave","end ...");

// ׼���������� VData
VData tVData = new VData();
FlagStr="";

tVData.add(tG);
tVData.addElement(tLCAscriptionRuleFactorySet);
tVData.addElement(GrpContNo);

try{
	loggerDebug("AscriptionRuleSave","this will save the data!!!");
	tLCAscriptionRuleFactoryUI.submitData(tVData,tOperate);
}
catch(Exception ex){
	Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	FlagStr = "Fail";
}

if (!FlagStr.equals("Fail")){
	tError = tLCAscriptionRuleFactoryUI.mErrors;
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
