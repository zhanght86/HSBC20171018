<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDContDefiEntrySave.jsp
//�����ܣ���Լ��Ϣ�������
//�������ڣ�2009-3-13
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.workflow.proddef.*"%>
 <%@page import="com.sinosoft.service.*" %>  
<%
 //������Ϣ������У�鴦��
 //�������
System.out.println("------------into PDContDefiEntrySave.jsp-------------------");

// PDContDefiEntryBL pDContDefiEntryBL = new PDContDefiEntryBL();
 //PDPrepAndDealWFBL tPDPrepAndDealWFBL = new PDPrepAndDealWFBL();
 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 TransferData transferData = new TransferData();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 
 operator = request.getParameter("operator");
 
transferData.setNameAndValue("tableName",request.getParameter("tableName"));
 try
 {
  // ׼���������� VData
  VData tVData = new VData();

  tVData.add(tG);
  
  transferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
  transferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
  transferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
  transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));
  transferData.setNameAndValue("RequDate",request.getParameter("RequDate"));
  transferData.setNameAndValue("Operator",tG.Operator);

  transferData.setNameAndValue("NeedTransferFlag",request.getParameter("NeedTransferFlag"));
  

  
  if(operator.equals("simplecont"))
  {
	  transferData.setNameAndValue("SimpleContPara", request.getParameter("SimpleContPara"));
  }
  
  tVData.add(transferData);
  //tPDPrepAndDealWFBL.submitData(tVData,operator);
  String busiName="PDPrepAndDealWFBL";
  
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
  tError = tPDPrepAndDealWFBL.mErrors;
  if (!tError.needDealError())
  {                          
   RiskState.setState(request.getParameter("RiskCode"),"�����Ϣ�趨->����ᱨ��Ĳ�Ʒ����","1");
   Content = " ����ɹ�! ";
   FlagStr = "Success";
  }
  else                                                                           
  {
   Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
   FlagStr = "Fail";
  }
 }*/

 //��Ӹ���Ԥ����
%>                      
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

