<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDRiskDefiSave.jsp
//�����ܣ���Ʒ������Ϣ¼��
//�������ڣ�2009-3-12
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.workflow.proddef.*"%>
  
<%
 //������Ϣ������У�鴦����
 //�������
 

 PDRiskDefiBL pDRiskDefiBL = new PDRiskDefiBL();
 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 TransferData transferData = new TransferData();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 
 //ִ�ж�����insert ���Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
 operator = request.getParameter("operator");
 
	 transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));
	 transferData.setNameAndValue("RequDate",request.getParameter("RequDate"));
	 transferData.setNameAndValue("Operator",tG.Operator);
	 transferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
	 transferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
	 transferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
	 transferData.setNameAndValue("IsBaseInfoDone","1");
	 
	 ProdDefWorkFlowBL tProdDefWorkFlowBL= new ProdDefWorkFlowBL();
	 
	 
	 try
	 {
	  // ׼���������� VData
	  VData tVData = new VData();

	  tVData.add(tG);
	  tVData.add(transferData);
	  tProdDefWorkFlowBL.submitData(tVData,operator);
	 }
	 catch(Exception ex)
	 {
	  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
	  FlagStr = "Fail";
	 }

	 //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	 if (FlagStr=="")
	 {
	  tError = tProdDefWorkFlowBL.mErrors;
	  if (!tError.needDealError())
	  {                          
	   Content = " "+"����ɹ�!"+" ";
	   FlagStr = "Success";
	  }
	  else                                                                           
	  {
	   Content = " "+"����ʧ�ܣ�ԭ����:"+"" + tError.getFirstError();
	   FlagStr = "Fail";
	  }
	 }
 
 //���Ӹ���Ԥ����
%>                      
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
