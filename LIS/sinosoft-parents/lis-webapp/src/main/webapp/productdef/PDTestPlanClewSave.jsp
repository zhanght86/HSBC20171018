<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDTestPointClewSave.jsp
//�����ܣ�����Ҫ����ʾ
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
 

 PDTestPlanClewBL tPDTestPlanClewBL = new PDTestPlanClewBL();
 
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
 
 PD_TestPlanClew_LibSchema tPD_TestPlanClew_LibSchema = new PD_TestPlanClew_LibSchema();
 String tPlanKind = request.getParameter("planKind");
 String tRiskKind = request.getParameter("riskkind");
 String tClewContent = request.getParameter("ClewContent");
 String tRemark = request.getParameter("Remark");
 String tClewcontentcode = request.getParameter("clewcontentcode");
 
 if(tRiskKind == null || tRiskKind.trim().equals("")){
	 tRiskKind = "9";
 }
 
 System.out.println("ǰ����յ�����Ϊ�� tPlanKind = " + tPlanKind + " tRiskKind = " + tRiskKind + " tClewContentCode = " + tClewContent + " tRemark = " + tRemark + " tClewcontentcode = " + tClewcontentcode);
 tPD_TestPlanClew_LibSchema.setTESTPLANKIND(tPlanKind);
 tPD_TestPlanClew_LibSchema.setTESTPLANRISKKIND(tRiskKind);
 tPD_TestPlanClew_LibSchema.setClewContent(tClewContent);
 tPD_TestPlanClew_LibSchema.setRemark(tRemark);
 tPD_TestPlanClew_LibSchema.setClewContentCode(tClewcontentcode);

 transferData.setNameAndValue("PD_TestPlanClew_LibSchema",tPD_TestPlanClew_LibSchema);
 
 try
 {
  // ׼���������� VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  tPDTestPlanClewBL.submitData(tVData,operator);
 }
 catch(Exception ex)
 {
  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
  FlagStr = "Fail";
 }

 //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
 if (FlagStr=="")
 {
  tError = tPDTestPlanClewBL.mErrors;
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

 //��Ӹ���Ԥ����
%>                      
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

