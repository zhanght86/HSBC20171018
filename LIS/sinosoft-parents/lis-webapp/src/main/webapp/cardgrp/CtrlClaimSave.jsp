<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�CtrlClaimSave.jsp
//�����ܣ�
//�������ڣ�2005-11-17 15:12:33
//������ ��Sujie
//���¼�¼�� ������  ��������   ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.LLDutyCtrlSchema"%>
<%@page import="com.sinosoft.lis.cardgrp.*"%>
<%@page import="com.sinosoft.lis.vschema.LLDutyCtrlSet"%>
<%@page import="com.sinosoft.utility.*"%>
<%
//������Ϣ������У�鴦��
//�������
LLDutyCtrlSchema tLLDutyCtrlSchema = new LLDutyCtrlSchema();

LLCtrlClaimBL tLLCtrlClaimBL = new LLCtrlClaimBL();

//�������
CErrors tError = null;
String tRearStr = "";
String tRela = "";
String FlagStr = "Fail";
String Content = "";

	//ȫ�ֱ���
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");
	
	loggerDebug("CtrlClaimSave","begin ...");
	
	String tOperate=request.getParameter("mOperate");	//����ģʽ
	//String tlevelflag=request.getParameter("levelflag");
	String tGrpContNo = request.getParameter("GrpContNo");	//���屣����
	String tContType = "2";	//��������Ϊ���屣��
	String tOccupationType = request.getParameter("OccupationType");	//ְҵ���
	String tContPlanCode = request.getParameter("ContPlanCode");	//���ռƻ�
	String tCtrlProp = request.getParameter("CtrlProp"); //��������
	String tRiskCode = request.getParameter("RiskCode");	//����
	String tDutyCode = request.getParameter("DutyCode");	//���α���
	String tGetDutyCode = request.getParameter("GetDutyCode");	//�������α���
	
	//CtrlBatchNo
	String tCtrlBatchNo = request.getParameter("CtrlBatchNo"); //�������κ�(ֻ��)

	//��Ϊ�յĿ��Ʋ������Ϊ*	
	if (tOccupationType==null||tOccupationType.equals(""))
		tOccupationType="*";
	if (tCtrlProp==null||tCtrlProp.equals(""))
		tCtrlProp="*";
	if (tContPlanCode==null||tContPlanCode.equals(""))
		tContPlanCode="*";
	if (tRiskCode==null||tRiskCode.equals(""))
		tRiskCode="*";
	if (tDutyCode==null||tDutyCode.equals(""))
		tDutyCode="*";
	if (tGetDutyCode==null||tGetDutyCode.equals(""))
		tGetDutyCode="*";
	
	//��ȡ��������
	String tObserveDate = request.getParameter("ObserveDate");	// �۲���
	String tExempt = request.getParameter("Exempt");	//�����
	String tExemptDate = request.getParameter("ExemptDate");	//��������
	String tTotalLimit = request.getParameter("TotalLimit");	//���⸶�޶�
	
	String tClaimRate = request.getParameter("ClaimRate"); //�⸶����
	
	String tStartPay = request.getParameter("StartPay");	//����
	String tEndPay = request.getParameter("EndPay");	//�ⶥ��
	
	String tRemark = request.getParameter("Remark");	//��ע
	
	tLLDutyCtrlSchema = new LLDutyCtrlSchema();
	tLLDutyCtrlSchema.setGrpContNo(tGrpContNo);
	tLLDutyCtrlSchema.setCtrlBatchNo(tCtrlBatchNo);
	tLLDutyCtrlSchema.setContType(tContType);
	tLLDutyCtrlSchema.setOccupationType(tOccupationType);
	tLLDutyCtrlSchema.setCtrlProp(tCtrlProp);
	tLLDutyCtrlSchema.setContPlanCode(tContPlanCode);
	tLLDutyCtrlSchema.setRiskCode(tRiskCode);
	tLLDutyCtrlSchema.setDutyCode(tDutyCode);
	tLLDutyCtrlSchema.setGetDutyCode(tGetDutyCode);
	
	//��ע
	tLLDutyCtrlSchema.setRemark(tRemark);
	
	//�۲���
	if (tObserveDate==null || tObserveDate.equals("")) 
	{
	   tObserveDate="-1";
	}
	
	tLLDutyCtrlSchema.setObserveDate(tObserveDate);
	
	//�����
	if (tExempt==null || tExempt.equals("")) 
	{
	   tExempt="-1";
	}
	tLLDutyCtrlSchema.setExempt(tExempt);
	
	//��������
	if (tExemptDate==null || tExemptDate.equals("")) 
	{
	   tExemptDate="-1";
	}
	tLLDutyCtrlSchema.setExemptDate(tExemptDate);
	
	//���⸶�޶�
	if (tTotalLimit==null || tTotalLimit.equals("")) 
	{
	   tTotalLimit="-1";
	}
	tLLDutyCtrlSchema.setTotalLimit(tTotalLimit);
	//�⸶����
	if (tClaimRate==null || tClaimRate.equals("")) 
	{
	   tClaimRate="-1";
	}
	tLLDutyCtrlSchema.setClaimRate(tClaimRate);
	
	//����
	if (tStartPay==null || tStartPay.equals("")) 
	{
	   tStartPay="-1";
	}
	tLLDutyCtrlSchema.setStartPay(tStartPay);
	
	//�ⶥ��
	if (tEndPay==null || tEndPay.equals("")) 
	{
		tEndPay="-1";
	}
	tLLDutyCtrlSchema.setEndPay(tEndPay);
	
	
	// ׼���������� VData
	VData tVData = new VData();
	FlagStr="";
	
	tVData.add(tG);
	tVData.addElement(tLLDutyCtrlSchema);
	
	try{
		loggerDebug("CtrlClaimSave","this will save the data!!!");
		tLLCtrlClaimBL.submitData(tVData,tOperate,"");
	}
	catch(Exception ex){
		Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
		FlagStr = "Fail";
	}
	
	if (!FlagStr.equals("Fail")){
		tError = tLLCtrlClaimBL.mErrors;
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
<<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>  
