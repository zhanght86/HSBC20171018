<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDAlgoDefiSave.jsp
//�����ܣ��㷨�������
//�������ڣ�2009-3-17
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
  
<%
 //������Ϣ������У�鴦��
 //�������
 

 PDAlgoDefiBL pDAlgoDefiBL = new PDAlgoDefiBL();
 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 TransferData transferData = new TransferData();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 
 //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
 operator = request.getParameter("operator");
 
 //tLDCodeSchema.setCodeType(request.getParameter("CodeType"));

 String values[] = request.getParameterValues("Mulline9Grid4");
 java.util.ArrayList list = new java.util.ArrayList();
 
 if(values != null)
 {
	 for(int i = 0; i < values.length; i++)
	 {
	  list.add(values[i]);
	 }
	transferData.setNameAndValue("list",list);
 }
transferData.setNameAndValue("tableName",request.getParameter("tableName"));

 if(operator.equals("test"))
 {
 /*
	 String FactorCodes[] = request.getParameterValues("Mulline10Grid3");
	 String FactorValues[] = request.getParameterValues("Mulline10Grid4");
	 
	 transferData.setNameAndValue("FactorCodes", FactorCodes);
	 transferData.setNameAndValue("FactorValues", FactorValues);
	 
	 String SubAlgoCodes[] = request.getParameterValues("Mulline11Grid3");
	 String SubAlgoValues[] = request.getParameterValues("Mulline11Grid4");

	 transferData.setNameAndValue("SubAlgoCodes", SubAlgoCodes);
	 transferData.setNameAndValue("SubAlgoValues", SubAlgoValues);
	 
	 transferData.setNameAndValue("CalSQL", request.getParameter("AlgoContent"));
	 */
	 String FactorCodes[] = request.getParameterValues("Mulline10Grid3");
	 String FactorValues[] = request.getParameterValues("Mulline10Grid4");
	 
	 transferData.setNameAndValue("FactorCodes", FactorCodes);
	 transferData.setNameAndValue("FactorValues", FactorValues);
	 transferData.setNameAndValue("AlgoCode", request.getParameter("AlgoCode"));
 }
 else if(operator.equals("add"))
 {
	 PD_LMCalFactorSchema tPD_LMCalFactorSchema = new PD_LMCalFactorSchema();
	 tPD_LMCalFactorSchema.setFactorCode(request.getParameter("SubAlgoCode"));
	 tPD_LMCalFactorSchema.setFactorName(request.getParameter("SubAlgoName"));
	 tPD_LMCalFactorSchema.setCalCode(request.getParameter("AlgoCode"));
	 tPD_LMCalFactorSchema.setFactorGrade("0");
	 tPD_LMCalFactorSchema.setFactorType("2");
	 
	 transferData.setNameAndValue("PD_LMCalFactorSchema", tPD_LMCalFactorSchema);
	 //add by jucy
 }else if(operator.equals("updateFac")){
		PD_LMCalFactorSchema tPD_LMCalFactorSchema = new PD_LMCalFactorSchema();
		 tPD_LMCalFactorSchema.setCalCode(request.getParameter("MainAlgoCode"));
		 tPD_LMCalFactorSchema.setFactorCode(request.getParameter("AlgoCode"));
		 tPD_LMCalFactorSchema.setFactorName(request.getParameter("AlgoDesc"));
		 tPD_LMCalFactorSchema.setFactorType("2");
		 tPD_LMCalFactorSchema.setFactorGrade(request.getParameter("SubAlgoGrade"));
		 tPD_LMCalFactorSchema.setFactorCalCode(request.getParameter("AlgoCode"));
		 transferData.setNameAndValue("PD_LMCalFactorSchema", tPD_LMCalFactorSchema);
	//-------- end
 }
 else
 {
	 PD_LMCalModeSchema tPDCalModeSchema = new PD_LMCalModeSchema();
	 tPDCalModeSchema.setCalCode(request.getParameter("AlgoCode"));
	 tPDCalModeSchema.setType(request.getParameter("AlgoType"));
	 tPDCalModeSchema.setRemark(request.getParameter("AlgoDesc"));
	 tPDCalModeSchema.setCalSQL(request.getParameter("AlgoContent"));
	 tPDCalModeSchema.setRiskCode(request.getParameter("RiskCode"));

	 transferData.setNameAndValue("PD_LMCalModeSchema", tPDCalModeSchema);
	 
	 if(StrTool.cTrim(request.getParameter("AlgoType")).equals("F")){
		 PD_LMCalFactorSchema tPD_LMCalFactorSchema = new PD_LMCalFactorSchema();
		 tPD_LMCalFactorSchema.setCalCode(request.getParameter("MainAlgoCode"));
		 tPD_LMCalFactorSchema.setFactorCode(request.getParameter("AlgoCode"));
		 tPD_LMCalFactorSchema.setFactorName(request.getParameter("AlgoDesc"));
		 tPD_LMCalFactorSchema.setFactorType("2");
		 tPD_LMCalFactorSchema.setFactorGrade(request.getParameter("SubAlgoGrade"));
		 tPD_LMCalFactorSchema.setFactorCalCode(request.getParameter("AlgoCode"));
		 transferData.setNameAndValue("PD_LMCalFactorSchema", tPD_LMCalFactorSchema);
	 }
 } 
   
 transferData.setNameAndValue("RiskCode", request.getParameter("RiskCode"));
   
 try
 { 
  // ׼���������� VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  pDAlgoDefiBL.submitData(tVData,operator);
 }
 catch(Exception ex)
 {
  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
  FlagStr = "Fail";
 }

 //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
 if (FlagStr=="")
 {
  tError = pDAlgoDefiBL.mErrors;
  if (!tError.needDealError())
  {                       
   Content = " "+"����ɹ�!"+" ";
   if(operator.equals("test"))
	{
		Content = ""+"���Խ��:"+"" + pDAlgoDefiBL.mTestResult;
	}
   FlagStr = "Success";
  }
  else                                                                           
  {
   Content = " "+"����ʧ�ܣ�ԭ����:"+"" + tError.getFirstError();
   FlagStr = "Fail";
  }
 }

 //��Ӹ���Ԥ����
%>                      
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

