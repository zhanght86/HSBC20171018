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
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
//������Ϣ������У�鴦��
//�������
LCAscriptionRuleFactorySchema tLCAscriptionRuleFactorySchema = new LCAscriptionRuleFactorySchema();
LCAscriptionRuleFactorySchema tOldLCAscriptionRuleFactorySchema = new LCAscriptionRuleFactorySchema();
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
	String tFactoryType[] = request.getParameterValues("AscriptionRuleNewGrid2");	//Ҫ������
	String tOtherNo[] = request.getParameterValues("AscriptionRuleNewGrid3");	//Ŀ������
	String tFactory[] = request.getParameterValues("AscriptionRuleNewGrid4");	//Ҫ�ؼ������
	String tCalRemark[] = request.getParameterValues("AscriptionRuleNewGrid5");	//Ҫ������
	String tParams[] = request.getParameterValues("AscriptionRuleNewGrid6");	//Ҫ��ֵ
	String tFactoryName[] = request.getParameterValues("AscriptionRuleNewGrid7");	//�����������
	String tGrpPolNo[] = request.getParameterValues("AscriptionRuleNewGrid9");	//���屣�����ֺ���
	String tFactoryCode = "";
	String tFactorySubCode = "";
	
	lineCount = arrCount.length; //����
	
	for(int i=0;i<lineCount;i++){
		tLCAscriptionRuleFactorySchema = new LCAscriptionRuleFactorySchema();
	
		tLCAscriptionRuleFactorySchema.setGrpContNo(GrpContNo);
		tLCAscriptionRuleFactorySchema.setAscriptionRuleCode(AscriptionRuleCode);
		tLCAscriptionRuleFactorySchema.setAscriptionRuleName(AscriptionRuleName);
		tLCAscriptionRuleFactorySchema.setRiskCode(tRiskCode[i]);
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

if (tOperate.equals("DELETE||MAIN") || tOperate.equals("UPDATE||MAIN"))
{
    loggerDebug("AscriptionRuleSave","AscriptionRuleCodeOld"+request.getParameter("AscriptionRuleCodeOld"));
    tOldLCAscriptionRuleFactorySchema.setGrpContNo(GrpContNo);
    tOldLCAscriptionRuleFactorySchema.setAscriptionRuleCode(request.getParameter("AscriptionRuleCodeOld"));
    tOldLCAscriptionRuleFactorySchema.setAscriptionRuleName(request.getParameter("AscriptionRuleNameOld"));
}		
loggerDebug("AscriptionRuleSave","end ...");

// ׼���������� VData
VData tVData = new VData();
FlagStr="";

tVData.add(tG);
tVData.addElement(tLCAscriptionRuleFactorySet);
tVData.addElement(GrpContNo);
tVData.addElement(tOldLCAscriptionRuleFactorySchema);
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
try{
	loggerDebug("AscriptionRuleSave","this will save the data!!!");
	String busiName="tbLCAscriptionRuleFactoryUI";
	
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
parent.fraInterface.document.all('AscriptionRuleCode').value="";
parent.fraInterface.document.all('AscriptionRuleName').value="";
</script>
</html>
