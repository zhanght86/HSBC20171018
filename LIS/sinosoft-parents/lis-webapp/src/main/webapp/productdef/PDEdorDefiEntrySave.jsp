<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDEdorDefiEntrySave.jsp
//�����ܣ���ȫ��Ϣ����
//�������ڣ�2009-3-16
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>R
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>  
  
<%
 //������Ϣ������У�鴦��
 //�������
 

 //PDEdorDefiEntryBL pDEdorDefiEntryBL = new PDEdorDefiEntryBL();
 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 TransferData transferData = new TransferData();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 String tCalCode = "";
try
 {
 //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
 operator = request.getParameter("operator");
 String tRiskCode=StrTool.cTrim(request.getParameter("RiskCode"));

 String tRiskName=StrTool.cTrim(request.getParameter("RiskName"));
 String mEdorType=StrTool.cTrim(request.getParameter("edortype"));
 //tLDCodeSchema.setCodeType(request.getParameter("CodeType"));
 PD_LMRiskEdorItemSet tPD_LMRiskEdorItemSet=new PD_LMRiskEdorItemSet();
 String values[] = request.getParameterValues("EdorCode");
 if(values!=null && values.length>0){
   for(int i=0;i<values.length;i++){
		    PD_LMRiskEdorItemSchema tPD_LMRiskEdorItemSchema=new PD_LMRiskEdorItemSchema();
		    System.out.println(StrTool.cTrim(values[i]));
		 		tPD_LMRiskEdorItemSchema.setEdorCode(StrTool.cTrim(values[i]));
		    tPD_LMRiskEdorItemSchema.setRiskCode(tRiskCode);
		    tPD_LMRiskEdorItemSchema.setRiskName(tRiskName);
		    tPD_LMRiskEdorItemSet.add(tPD_LMRiskEdorItemSchema);
   }
 }
/*
 String tChk[] = request.getParameterValues("Mulline9Grid1"); 
 String tEdorType[] = request.getParameterValues("Mulline9Grid1");
 String tCalType[] = request.getParameterValues("Mulline9Grid2");
 String tCalCode[] = request.getParameterValues("Mulline9Grid3");
 PD_LMEdorCalSet tPD_LMEdorCalSet=new PD_LMEdorCalSet();
 if(tChk!=null){
	 for(int index=0;index<tChk.length;index++)
	 {
	    if(!StrTool.cTrim(tEdorType[index]).equals("")  ){
		      PD_LMEdorCalSchema tPD_LMEdorCalSchema=new PD_LMEdorCalSchema();
		    	tPD_LMEdorCalSchema.setRiskCode(tRiskCode);
		    	tPD_LMEdorCalSchema.setEdorType(StrTool.cTrim(tEdorType[index]));
		    	tPD_LMEdorCalSchema.setCalType(StrTool.cTrim(tCalType[index]));
		    	tPD_LMEdorCalSchema.setDutyCode("000000");
		    	tPD_LMEdorCalSchema.setCalCode(tCalCode[index]);
		    	tPD_LMEdorCalSet.add(tPD_LMEdorCalSchema);
	    	}
	 } 
 }
 */
 /*
 document.getElementById('EdorType').value = '';
		document.getElementById('EdorCalType').value = '';
		document.getElementById('CalCode').value = '';
 */
 
 //tongmeng 2011-07-14 modify
 //�޸ĳ�һ��ֻ����һ���ķ�ʽ
 String tCalEdorType = request.getParameter("EdorType");
 String tCalEdorCalType = request.getParameter("EdorCalType");
 String tDutyCode= request.getParameter("DutyCode");
 String tCalCalCode = request.getParameter("CalCode");
 String tCalDescibe = request.getParameter("CalDescibe");
 PD_LMEdorCalSet tPD_LMEdorCalSet=new PD_LMEdorCalSet();
 PD_LMEdorCalSchema tPD_LMEdorCalSchema=new PD_LMEdorCalSchema();
	tPD_LMEdorCalSchema.setRiskCode(tRiskCode);
	tPD_LMEdorCalSchema.setEdorType(StrTool.cTrim(tCalEdorType));
	tPD_LMEdorCalSchema.setCalType(StrTool.cTrim(tCalEdorCalType));
	tPD_LMEdorCalSchema.setDutyCode(tDutyCode);
	tPD_LMEdorCalSchema.setCalCode(tCalCalCode);
	tPD_LMEdorCalSchema.setCalDescibe(tCalDescibe);
	tPD_LMEdorCalSet.add(tPD_LMEdorCalSchema);
	 //��ȫ��Ŀ��CTʱ���lmedorzt1�в���һ�����ݡ�
	 //-Allen 2016-11-14
	 
	String tRiskcode = request.getParameter("RiskCode");
	String tDutycode = request.getParameter("DutyCode");
	String tPayPlanCode = "000000";
	String tSurrCalType = "0";
	String tCycPayIntvType = request.getParameter("CycPayIntvType");
	String tCycPayCalCode = request.getParameter("CalCode");
	String tLiveGetType = "1";
	String tDeadGetType = "1";
	String tCalcodeType= "1";
	String tZTYearType = "0";
	LMEdorZT1Schema tLMEdorZT1Schema = new LMEdorZT1Schema();
	tLMEdorZT1Schema.setRiskCode(tRiskcode);
	tLMEdorZT1Schema.setDutyCode(tDutycode);
	tLMEdorZT1Schema.setPayPlanCode(tPayPlanCode);
	tLMEdorZT1Schema.setSurrCalType(tSurrCalType);
	tLMEdorZT1Schema.setCycPayIntvType(tCycPayIntvType);
	tLMEdorZT1Schema.setCycPayCalCode(tCycPayCalCode);
	tLMEdorZT1Schema.setLiveGetType(tLiveGetType);
	tLMEdorZT1Schema.setDeadGetType(tDeadGetType);
	tLMEdorZT1Schema.setCalCodeType(tCalcodeType);
	tLMEdorZT1Schema.setZTYearType(tZTYearType);
	
	LMEdorZT1Set tLMEdorZT1Set = new LMEdorZT1Set();
	tLMEdorZT1Set.add(tLMEdorZT1Schema);
	
	//��ȫ��Ŀ��CTʱ���lmedorduty�в���һ�����ݡ�
		 //-Allen 2016-11-14
	LMEdorZTDutySchema tLMEdorZTDutySchema = new  LMEdorZTDutySchema();
	LMEdorZTDutySet tLMEdorZTDutySet = new LMEdorZTDutySet();
	
	tLMEdorZTDutySchema.setRiskCode(tRiskcode);
	tLMEdorZTDutySchema.setDutyCode(tDutycode);
	tLMEdorZTDutySchema.setPayByAcc("1");
	tLMEdorZTDutySchema.setPayCalType("1");
	
	
	tLMEdorZTDutySet.add(tLMEdorZTDutySchema);
	
	
	//��ȫ��Ŀ��WT��ʱ�����lmedorwt����һ������
	LMEdorWTSchema tLMEdorWTSchema = new LMEdorWTSchema();
	LMEdorWTSet tLMEdorWTSet = new LMEdorWTSet();
	tLMEdorWTSchema.setRiskCode(tRiskcode);
	tLMEdorWTSchema.setRiskVersion("2016");
	tLMEdorWTSchema.setHesitateFlag("Y");
	tLMEdorWTSchema.setHesitateType("D");
	tLMEdorWTSchema.setHesitateStart("0");
	tLMEdorWTSchema.setHesitateEnd("10");
	tLMEdorWTSet.add(tLMEdorWTSchema);
	
  transferData.setNameAndValue("PD_LMRiskEdorItemSet",tPD_LMRiskEdorItemSet);
  transferData.setNameAndValue("PD_LMEdorCalSet",tPD_LMEdorCalSet);
  transferData.setNameAndValue("LMEdorZT1Set",tLMEdorZT1Set);
  transferData.setNameAndValue("LMEdorZTDutySet", tLMEdorZTDutySet);
 // transferData.setNameAndValue("LMEdorZTDutySchema",tLMEdorZTDutySchema);
 // transferData.setNameAndValue("LMEdorWTSchema",tLMEdorWTSchema);
  
  transferData.setNameAndValue("LMEdorWTSet",tLMEdorWTSet);
  
  transferData.setNameAndValue("RiskCode",tRiskCode); 
  transferData.setNameAndValue("EdorType",mEdorType);
  
  String tCalCodeType = request.getParameter("CalCodeSwitchFlag");
  transferData.setNameAndValue("CalCodeType",tCalCodeType);
  
  
  
  
  // ׼���������� VData
  
  VData tVData = new VData();
  tVData.add(tG);
  tVData.add(transferData);
String busiName="PDEdorDefiEntryBL";
  
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //String tDiscountCode = "";
  if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
	//  VData rVData = tBusinessDelegate.getResult();
    Content = " "+"����ʧ�ܣ�ԭ����:"+"" + tBusinessDelegate.getCErrors().getFirstError();
  	FlagStr = "Fail";
  }
  else {
	  if(mEdorType!=null&&!mEdorType.equals(""))
	  {
	  	if(operator!=null&&!operator.equals("delcal"))
	  	{
	  		VData rVData = tBusinessDelegate.getResult();
	  		tCalCode = (String)rVData.get(0);
	  	}
	  }
    Content = " "+"����ɹ�!"+" ";
  	FlagStr = "Succ";
  }
 
 }
 catch(Exception ex)
 {
  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
  FlagStr = "Fail";
 }

  /*pDEdorDefiEntryBL.submitData(tVData,operator);
  if(mEdorType!=null&&!mEdorType.equals(""))
  {
  	RiskState.setState(tRiskCode, "��ȫҵ�����->��ȫ��Ŀ�㷨����", "1");
  }
	else
	{
		RiskState.setState(tRiskCode, "��ȫҵ�����->��ȫ��Ŀѡ��", "1");
	}
 */

 //��Ӹ���Ԥ����
 

 			
 			
%>                      
<%=Content%>
<html>
<script type="text/javascript">

parent.fraInterface.setCalCode("<%=tCalCode%>");
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>"); 
</script>
</html>

