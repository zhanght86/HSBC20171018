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
LCGrpFeeSchema tLCGrpFeeSchema = new LCGrpFeeSchema();
LCGrpFeeSet tLCGrpFeeSet = new LCGrpFeeSet();
LCGrpFeeParamSchema tLCGrpFeeParamSchema = new LCGrpFeeParamSchema();
LCGrpFeeParamSet tLCGrpFeeParamSet = new LCGrpFeeParamSet();
//GrpFeeUI tGrpFeeUI = new GrpFeeUI();
   String busiName="tbGrpFeeUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
//�������
CErrors tError = null;
String tOperate=request.getParameter("mOperate");
String tRearStr = "";
String tRela = "";
String FlagStr = "Fail";
String Content = "";

//ȫ�ֱ���
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

loggerDebug("RiskFeeSave","begin ...");

String GrpPolNo = request.getParameter("GrpPolNo");	//���屣�����ֺ���

//�����������Ϣ
int lineCount = 0;
String arrCount[] = request.getParameterValues("RiskFeeGridNo");
if (arrCount != null){
	String tRadio[] = request.getParameterValues("InpRiskFeeGridSel");	//Radioѡ��
	String tInsuAccNo[] = request.getParameterValues("RiskFeeGrid1");	//�����ʻ�����
	String tPayPlanCode[] = request.getParameterValues("RiskFeeGrid3");	//���������
	String tFeeCode[] = request.getParameterValues("RiskFeeGrid5");	//����ѱ���
	String tPayInsuAccName[] = request.getParameterValues("RiskFeeGrid7");	//��ϵ˵��
	String tFeeCalMode[] = request.getParameterValues("RiskFeeGrid8");	//����Ѽ��㷽ʽ
	String tFeeCalCode[] = request.getParameterValues("RiskFeeGrid9");	//����Ѽ��㹫ʽ
	String tFeeValue[] = request.getParameterValues("RiskFeeGrid10");	//�̶�ֵ
	String tCompareValue[] = request.getParameterValues("RiskFeeGrid11");	//�Ƚ�ֵ
	String tFeeCalModeType[] = request.getParameterValues("RiskFeeGrid12");	//��������
	String tFeePeriod[] = request.getParameterValues("RiskFeeGrid13");	//�۳����������
	String tMaxTime[] = request.getParameterValues("RiskFeeGrid14");	//�۳������������
	String tDefaultFlag[] = request.getParameterValues("RiskFeeGrid15");	//ȱʡ���
	//modify by zhangxing ��ʱע��tInerSerialNo
	//String tInerSerialNo[] = request.getParameterValues("RiskFeeGrid17");    //����ѵ�˳���

	lineCount = arrCount.length; //����

	//ѡ��ѡ��ѡ�е������ύ
	for(int i=0;i<lineCount;i++){
		if(tRadio[i].equals("1")){
			tLCGrpFeeSchema = new LCGrpFeeSchema();
			tLCGrpFeeSchema.setGrpPolNo(GrpPolNo);
			tLCGrpFeeSchema.setInsuAccNo(tInsuAccNo[i]);
			tLCGrpFeeSchema.setPayPlanCode(tPayPlanCode[i]);
			tLCGrpFeeSchema.setFeeCode(tFeeCode[i]);
			tLCGrpFeeSchema.setPayInsuAccName(tPayInsuAccName[i]);
			tLCGrpFeeSchema.setFeeCalMode(tFeeCalMode[i]);
			tLCGrpFeeSchema.setFeeCalCode(tFeeCalCode[i]);
			tLCGrpFeeSchema.setFeeValue(tFeeValue[i]);
			tLCGrpFeeSchema.setCompareValue(tCompareValue[i]);
			tLCGrpFeeSchema.setFeeCalModeType(tFeeCalModeType[i]);
			tLCGrpFeeSchema.setFeePeriod(tFeePeriod[i]);
			tLCGrpFeeSchema.setMaxTime(tMaxTime[i]);
			tLCGrpFeeSchema.setDefaultFlag(tDefaultFlag[i]);
			//modify by zhangxing ��ʱע��InerSerialNo
			//tLCGrpFeeSchema.setInerSerialNo(tInerSerialNo[i]);
			tLCGrpFeeSet.add(tLCGrpFeeSchema);
			loggerDebug("RiskFeeSave","��ǰ�б�ѡ��"+tFeeCode[i]);
		}
	}
}

//������ӱ���Ϣ
lineCount = 0;
String arrCount2[] = request.getParameterValues("RiskFeeParamGridNo");
if (arrCount2 != null){
	String tFeeMin[] = request.getParameterValues("RiskFeeParamGrid1");	//��������
	String tFeeMax[] = request.getParameterValues("RiskFeeParamGrid2");	//��������
	String tFeeRate[] = request.getParameterValues("RiskFeeParamGrid3");	//����ѱ���
	String tFeeID[] = request.getParameterValues("RiskFeeParamGrid4");	//FeeID

	lineCount = arrCount2.length; //����
	loggerDebug("RiskFeeSave",lineCount+"*******************************");

	for(int i=0;i<lineCount;i++){
		tLCGrpFeeParamSchema = new LCGrpFeeParamSchema();
		tLCGrpFeeParamSchema.setGrpPolNo(GrpPolNo);
		tLCGrpFeeParamSchema.setFeeMin(tFeeMin[i]);
		tLCGrpFeeParamSchema.setFeeMax(tFeeMax[i]);
		tLCGrpFeeParamSchema.setFeeRate(tFeeRate[i]);
		tLCGrpFeeParamSchema.setFeeID(tFeeID[i]);
		tLCGrpFeeParamSet.add(tLCGrpFeeParamSchema);
	}
}
loggerDebug("RiskFeeSave","end ...");

// ׼���������� VData
VData tVData = new VData();
FlagStr="";

tVData.add(tG);
tVData.addElement(tLCGrpFeeSet);
tVData.addElement(tLCGrpFeeParamSet);

try{
	loggerDebug("RiskFeeSave","this will save the data!!!");
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
