<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>

<%
//�������ƣ�PDRiskDutyRelaSave.jsp
//�����ܣ����ֹ�������
//�������ڣ�2009-3-12
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
 //������Ϣ������У�鴦��
 //�������

 //PDRiskDutyRelaBL tPDRiskDutyRelaBL = new PDRiskDutyRelaBL();
 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 
TransferData transferData = new TransferData();
GlobalInput tG = new GlobalInput(); 
tG=(GlobalInput)session.getAttribute("GI");
operator = request.getParameter("operatorS");
String tDutyCode  ="";
if("del".equals(operator) ){
	String tGetSel[] = request.getParameterValues("InpMulline14GridSel");
	String tDutyArr[] = request.getParameterValues("Mulline14Grid1");
	for(int i=0;i<tGetSel.length;i++){
		if(tGetSel[i].equals("1")){
			tDutyCode = tDutyArr[i];
		}
	}
}

if("update".equals(operator) ){
	String tGetSel[] = request.getParameterValues("InpMulline14GridSel");
	String tDutyArr[] = request.getParameterValues("Mulline14Grid1");
	for(int i=0;i<tGetSel.length;i++){
		if(tGetSel[i].equals("1")){
			tDutyCode = tDutyArr[i];
		}
	}
}
 String riskCode = request.getParameter("riskCodeS");
 transferData.setNameAndValue("riskCode",riskCode);
 transferData.setNameAndValue("DutyCode",tDutyCode);

 System.out.println("riskCode:" + riskCode);
 //������δ���
	//String maxdutysel = "select code from ldcode where codetype = 'dutysel'";
	//ExeSQL ttExeSQL = new ExeSQL();
	//SSRS ttSSRS = ttExeSQL.execSQL(maxdutysel);
	//
	//String maxSel = ttSSRS.GetText(1, 1);
	//int maxSelInt = Integer.parseInt(maxSel);
	//
	//int temp = maxSelInt + 1;
	//String sql = "update ldcode set code = '" + temp + "' where codetype = 'dutysel'";
	//ttExeSQL.execUpdateSQL(sql);
	//
	//String tempStr = temp + "";
	//if(tempStr.length() < 3){
	//	int tempStrL = tempStr.length();
	//	for(int i = tempStrL; i < 3; i ++){
	//		tempStr = "0" + tempStr;
	//	}
	//}
	//String tPayPlanCode = "";
	//if(riskCode.length() < 4){
	//	tPayPlanCode = tPayPlanCode + tempStr;
	//}else{
	//	tPayPlanCode = riskCode.substring(0, 3);
	//	tPayPlanCode = tPayPlanCode + tempStr;
	//}
	//
	//
	//System.out.println(" +++++++++++++++++++ " + tPayPlanCode);
 
	String tChoFlag = request.getParameter("choFlag");
	String tSpecFlag = "Y";
	String tRiskVer = "2012";
	
	System.out.println("lmriskduty����Ϣ��  riskCode :" + riskCode + " RiskVer: " + tRiskVer + " ChoFlag:" + tChoFlag + " tSpecFlag:" + tSpecFlag);
	
	//���lmduty������
	String tDutyName = request.getParameter("DutyName");
	String tInsuYear = request.getParameter("InsuYear");
	String tInsuYearFlag = request.getParameter("InsuYearFlag");
	String tInsuYearRela = request.getParameter("InsuYearRela");
	String tPayEndDateCalRef = request.getParameter("PayEndDateCalRef");
	String tPayEndDateCalMode = request.getParameter("PayEndDateCalMode");
	String tPayEndYearRela = request.getParameter("PayEndYearRela");
	String tGetYear = request.getParameter("GetYear");
	String tGetYearFlag = request.getParameter("GetYearFlag");
	String tGetYearRela = request.getParameter("GetYearRela");
	String tCalMode = request.getParameter("CalMode");
	//String tBasicCalCode = request.getParameter("BasicCalCode");
	String tBasicCalCode ="000003";
	String tAmntFlag = request.getParameter("AmntFlag");
	String tVPU = request.getParameter("VPU");
	//String AddAmntFlag = request.getParameter("AddAmntFlagDuty");
	//  modify AddAmntFlag
	String AddAmntFlag="1";
	//-------- add by jucy
	String tPCalMode = request.getParameter("PCalMode");
	//-------- end
	
	//-------- add by jucy
	//�ɷ���ֹ�ڼ� �ı�¼��򣻴洢��LMDuty ���PAYENDYEAR
	//�ɷ���ֹ�ڼ䵥λ�� �洢��LMDuty ���PAYENDYEARFLAG 
	String tPayEndYear = request.getParameter("DutyPayEndYear");
	String tPayEndYearFlag = request.getParameter("DutyPayEndYearFlag");
	//-------- end
	//PDRiskDutyBL tPDRiskDutyBL = new PDRiskDutyBL();
	System.out.println("lmduty����Ϣ�� DutyName:" + tDutyName + " InsuYear:" + tInsuYear + " InsuYearFlag:" + tInsuYearFlag + " InsuYearRela:" + tInsuYearRela);
	try{
	 	// ׼���������� VData
	 	VData tVData = new VData(); 
	 	
	 	//lmriskduty��schema�и�ֵ
		PD_LMRiskDutySchema pd_LMRiskDutySchema = new PD_LMRiskDutySchema();
		pd_LMRiskDutySchema.setRiskCode(riskCode);
		pd_LMRiskDutySchema.setRiskVer(tRiskVer);
		//pd_LMRiskDutySchema.setDutyCode(tPayPlanCode);
		pd_LMRiskDutySchema.setChoFlag(tChoFlag);
		pd_LMRiskDutySchema.setSpecFlag(tSpecFlag);
		tVData.add(pd_LMRiskDutySchema);
		
		//��lmduty��schema�и�ֵ
		PD_LMDutySchema tPD_LMDutySchema = new PD_LMDutySchema();
		//tPD_LMDutySchema.setDutyCode(tPayPlanCode);
		tPD_LMDutySchema.setDutyName(tDutyName);
		tPD_LMDutySchema.setInsuYear(tInsuYear);
		tPD_LMDutySchema.setInsuYearFlag(tInsuYearFlag);
		tPD_LMDutySchema.setInsuYearRela(tInsuYearRela);
		tPD_LMDutySchema.setPayEndDateCalRef(tPayEndDateCalRef);
		tPD_LMDutySchema.setPayEndDateCalMode(tPayEndDateCalMode);
		tPD_LMDutySchema.setGetYearFlag(tGetYearFlag);
		tPD_LMDutySchema.setGetYear(tGetYear);
		tPD_LMDutySchema.setCalMode(tCalMode);
		tPD_LMDutySchema.setBasicCalCode(tBasicCalCode);
		tPD_LMDutySchema.setAmntFlag(tAmntFlag);
		tPD_LMDutySchema.setVPU(tVPU);
		tPD_LMDutySchema.setPayEndYearRela(tPayEndYearRela);
		tPD_LMDutySchema.setGetYearRela(tGetYearRela);
		
		/**------Add RiskCalCode 2016-11-10---------*/
		tPD_LMDutySchema.setRiskCalCode("000003");
		
		if(tInsuYearRela != null && !tInsuYearRela.trim().equals("")){
			tPD_LMDutySchema.setInsuYearRela(tInsuYearRela);
		}else{
			tPD_LMDutySchema.setInsuYearRela("0");
		}
		//-------- add by jucy
		//1)ADDAMNTFLAG����������Ƿ����ܻ��Ͷ������ϵͳ�Զ��洢Ϊ1��������������ܻ�Ͷ������ϵͳ�Զ��洢Ϊ2
		tPD_LMDutySchema.setAddAmntFlag(AddAmntFlag);
		//2)��������㷽��
		tPD_LMDutySchema.setPCalMode(tPCalMode);
		//-------- end
		//-------- add by jucy
		//���������
		tPD_LMDutySchema.setPayEndYear(tPayEndYear);
		tPD_LMDutySchema.setPayEndYearFlag(tPayEndYearFlag);
		//-------- end
		tVData.add(tPD_LMDutySchema);
		
	 	tVData.add(tG);
	 	tVData.add(transferData);
	 	
	 	//tPDRiskDutyBL.submitData(tVData,operator);
	 	
		//	new RiskState().setState(riskCode, "��Ʒ�����->������Ϣ", "1");
		String busiName="PDRiskDutyBL";
	   
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		//String tDiscountCode = "";
		if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
			VData rVData = tBusinessDelegate.getResult();
			 Content = " "+"����ʧ�ܣ�ԭ����:"+"" + tBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
		}
		else {
			Content = " "+"����ɹ�!"+" ";
			FlagStr = "Success";
		}
	}catch(Exception ex){
		System.out.println("========"+ ex.getMessage());
		Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.getMessage();
		FlagStr = "Fail";
	}
/*
}catch(Exception ex){
	Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	FlagStr = "Fail";
}
	//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
if (FlagStr==""){
	tError = tPDRiskDutyBL.mErrors;
	if (!tError.needDealError()){                          
		Content = " ����ɹ�! ";
		FlagStr = "Success";
	}else{
	   Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
	   FlagStr = "Fail";
	}
}*/

 //��Ӹ���Ԥ����
%>
<html>
	<script type="text/javascript">
 parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
</script>
</html>