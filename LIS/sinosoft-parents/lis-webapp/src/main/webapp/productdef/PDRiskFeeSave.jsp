<%@include file="../i18n/language.jsp"%>

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDRiskFeeSave.jsp
//�����ܣ��˻�����Ѷ���
//�������ڣ�2009-3-14
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
 

 //PDRiskFeeBL pDRiskFeeBL = new PDRiskFeeBL();
 
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

 String INSUACCNO = request.getParameter("INSUACCNO");
 String PAYINSUACCNAME = request.getParameter("PAYINSUACCNAME");
 String FEECODE = request.getParameter("FEECODE");
 String FEENAME = request.getParameter("FEENAME");
 String PAYPLANCODE = request.getParameter("PAYPLANCODE");
 String FEEKIND = request.getParameter("FEEKIND");
 String FEECALMODE = request.getParameter("FEECALMODE");

 String FEEITEMTYPE = request.getParameter("FEEITEMTYPE");
 String FEETAKEPLACE = request.getParameter("FEETAKEPLACE");
 String FEEPERIOD = request.getParameter("FEEPERIOD");
 String FEESTARTDATE = request.getParameter("FEESTARTDATE");
 String FEECALMODETYPE = request.getParameter("FEECALMODETYPE");
 String FEECALCODE = request.getParameter("FEECALCODE");
 String RISKCODE = request.getParameter("RiskCode");
 
 //String values[] = request.getParameterValues("Mulline9Grid4");
 
 java.util.ArrayList list = new java.util.ArrayList();
 list.add(FEECODE);
 list.add(FEENAME);
 list.add(INSUACCNO);
 list.add(PAYPLANCODE);
 list.add(PAYINSUACCNAME);
 list.add(FEEKIND);
 list.add(FEEITEMTYPE);
 list.add(FEETAKEPLACE);
 list.add(FEECALMODE);
 list.add(FEECALMODETYPE);
 list.add(FEECALCODE);
 list.add(FEEPERIOD);
 list.add(FEESTARTDATE);
 list.add(RISKCODE);
 /*
 for(int i = 0; i < values.length; i++)
 {
  list.add(values[i]);
 }*/
transferData.setNameAndValue("list",list);
 
 String tCalCodeType = request.getParameter("CalCodeSwitchFlag");
 transferData.setNameAndValue("CalCodeType",tCalCodeType);
transferData.setNameAndValue("tableName",request.getParameter("tableName"));
transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));
 try
 {
  // ׼���������� VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  //pDRiskFeeBL.submitData(tVData,operator);
String busiName="PDRiskFeeBL";
  
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //String tDiscountCode = "";
  if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
	//  VData rVData = tBusinessDelegate.getResult();
    Content = " "+"����ʧ�ܣ�ԭ����:"+"" + tBusinessDelegate.getCErrors().getFirstError();
  	FlagStr = "Fail";
  }
  else {
    Content = " "+"����ɹ�!"+" ";
  	FlagStr = "Success";
  	FEECALCODE = (String)tBusinessDelegate.getResult().get(0);
  }
 
 }
 catch(Exception ex)
 {
  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
  FlagStr = "Fail";
 }
  /*
 }
 catch(Exception ex)
 {
  Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
  FlagStr = "Fail";
 }

 //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
 if (FlagStr=="")
 {
  tError = pDRiskFeeBL.mErrors;
  if (!tError.needDealError())
  {                          
   Content = " ����ɹ�! ";
   FlagStr = "Success";
  }
  else                                                                           
  {
   Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
   FlagStr = "Fail";
  }
 }
*/
 //��Ӹ���Ԥ����
%>                      
<%=Content%>
<html>
<script type="text/javascript">
parent.fraInterface.setCalCode("<%=FEECALCODE%>");
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

