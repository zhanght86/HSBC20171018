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
 
 //tLDCodeSchema.setCodeType(request.getParameter("CodeType"));
 
 String tPROCEEDSCODE =  request.getParameter("PROCEEDSCODE");
 String tEXPRESSPROPERTY =  request.getParameter("EXPRESSPROPERTY");
 String tCALORDERNO =  request.getParameter("CALORDERNO");
 String tCALEXPRESS =  request.getParameter("CALEXPRESS");
 String tRECURSIONINITVALUE =  request.getParameter("RECURSIONINITVALUE");
 String tREMARK =  request.getParameter("REMARK");
 String tRESULTPRECISION =  request.getParameter("RESULTPRECISION");
 String tSHOWFLAG =  request.getParameter("SHOWFLAG");
 String tTERMS = request.getParameter("TERMS");
 String tBACK = request.getParameter("BACK");
 
 if(tEXPRESSPROPERTY.equals("3")){
 	tCALEXPRESS="0";
 }
 transferData.setNameAndValue("PROCEEDSCODE",tPROCEEDSCODE);
 transferData.setNameAndValue("EXPRESSPROPERTY",tEXPRESSPROPERTY);
 transferData.setNameAndValue("CALORDERNO",tCALORDERNO);
 transferData.setNameAndValue("CALEXPRESS",tCALEXPRESS);
 transferData.setNameAndValue("RECURSIONINITVALUE",tRECURSIONINITVALUE);
 transferData.setNameAndValue("REMARK",tREMARK);
 transferData.setNameAndValue("RESULTPRECISION",tRESULTPRECISION);
 transferData.setNameAndValue("SHOWFLAG",tSHOWFLAG);
 transferData.setNameAndValue("TERMS",tTERMS);
 transferData.setNameAndValue("BACK",tBACK);
 transferData.setNameAndValue("tableName",request.getParameter("tableName"));
 transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));


 try
 {
  // ׼���������� VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  String busiName="PDSugIncomeDataAlgBL";
  
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
  

 //��Ӹ���Ԥ����
%>                      
<%=Content%>
<html>
<script type="text/javascript">
		parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>


