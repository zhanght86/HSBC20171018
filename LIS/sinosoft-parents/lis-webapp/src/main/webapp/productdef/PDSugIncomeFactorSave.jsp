<%@include file="../i18n/language.jsp"%>


<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDSugIncomeDataAlgSave.jsp
//�����ܣ������㷨����
//�������ڣ�2011-10-24
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.Hashtable"%>
<%@page import="com.sinosoft.service.*" %>  
<%@page import="org.codehaus.xfire.addressing.RandomGUID" %>  
  
<%
 //������Ϣ������У�鴦��
 //�������

 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 String tRiskCode=null;
 
 TransferData transferData = new TransferData();
 Hashtable tHashtable = new Hashtable();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 
 //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
 operator = request.getParameter("operator");
 tRiskCode=request.getParameter("RiskCode");
 if(operator.equals("testSql")){
	 ProposalUtil testUtil = new ProposalUtil();
	 String sql = request.getParameter("CALSQL");
	 if(testUtil.testSql(sql)){
		 Content = " sq"+"l�����ȷ!"+" ";
	  	 FlagStr = "testSql";
	 }else{
		 Content = " sq"+"l��䲻��ȷ!"+" ";
	  	 FlagStr = "Fail"; 
	 }
 }else{
	 if(operator.equals("del")){
		 transferData.setNameAndValue("ITEMCODE", request.getParameter("hiddenItemCode"));
		 transferData.setNameAndValue("CALELEMENT",request.getParameter("hiddenCalElement"));
	 }else{
		 String tITEMCODE =  request.getParameter("ITEMCODE");
		 String tCALORDERNO =  request.getParameter("CALORDERNO");
		 String tCALELEMENT=  request.getParameter("CALELEMENT");
		 String tELEMENTPROPERTY =  request.getParameter("ELEMENTPROPERTY");
		 String tADJUSTPOSITION =  request.getParameter("ADJUSTPOSITION");
		 String tVARIABLECODE =  request.getParameter("VARIABLECODE");
		 String tCALSQL =  request.getParameter("CALSQL");
		 String tSQLEXCUTETYPE =  request.getParameter("SQLEXCUTETYPE");
		 String tSTARTPOINT = request.getParameter("STARTPOINT");
		 String tSTARTPOINTFLAG =  request.getParameter("STARTPOINTFLAG");
		 String tENDPOINT =  request.getParameter("ENDPOINT");
		 String tENDPOINTFLAG =  request.getParameter("ENDPOINTFLAG");
		 String tINITVALUE =  request.getParameter("INITVALUE");
		 String tSTEPVALUE =  request.getParameter("STEPVALUE");
		 String tRESULTPRECISION = request.getParameter("RESULTPRECISION");
		 String tREMARK = request.getParameter("REMARK");
		 transferData.setNameAndValue("ITEMCODE",tITEMCODE);
		 transferData.setNameAndValue("CALORDERNO",tCALORDERNO);
		 transferData.setNameAndValue("CALELEMENT",tCALELEMENT);
		 transferData.setNameAndValue("ELEMENTPROPERTY",tELEMENTPROPERTY);
		 transferData.setNameAndValue("ADJUSTPOSITION",tADJUSTPOSITION);
		 transferData.setNameAndValue("VARIABLECODE",tVARIABLECODE);
		 transferData.setNameAndValue("CALSQL",tCALSQL);
		 transferData.setNameAndValue("SQLEXCUTETYPE",tSQLEXCUTETYPE);
		 transferData.setNameAndValue("STARTPOINT",tSTARTPOINT);
		 transferData.setNameAndValue("STARTPOINTFLAG",tSTARTPOINTFLAG);
		 transferData.setNameAndValue("ENDPOINT",tENDPOINT);
		 transferData.setNameAndValue("ENDPOINTFLAG",tENDPOINTFLAG);
		 transferData.setNameAndValue("INITVALUE",tINITVALUE);
		 transferData.setNameAndValue("STEPVALUE",tSTEPVALUE);
		 transferData.setNameAndValue("RESULTPRECISION",tRESULTPRECISION);
		 transferData.setNameAndValue("REMARK",tREMARK);
	 }
	 
	 transferData.setNameAndValue("tableName",request.getParameter("tableName"));
	 transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));

	 try
	 {
	  // ׼���������� VData
	  VData tVData = new VData();

	  tVData.add(tG);
	  tVData.add(transferData);
	  String busiName="PDSugIncomeFactorBL";
	  
	  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  //String tDiscountCode = "";
	  if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
		  VData rVData = tBusinessDelegate.getResult();
	    Content = " "+"����ʧ�ܣ�ԭ����:"+"" + (String)rVData.get(0);
	  	FlagStr = "Fail";
	  }
	  else {
		  VData rVData = tBusinessDelegate.getResult();
	      Content = " "+"����ɹ�!"+" ";
	  	  FlagStr = "Succ";

	  }
	 
	 }
	 catch(Exception ex)
	 {
	  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
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


