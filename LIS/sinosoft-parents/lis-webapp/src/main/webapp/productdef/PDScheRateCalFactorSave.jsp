<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDScheRateCalFactorSave.jsp
//�����ܣ����ʱ�Ҫ�ؿ�
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
 

 PDScheRateCalFactorBL pDScheRateCalFactorBL = new PDScheRateCalFactorBL();
 
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

 PD_ScheRateCalFactor_LibSchema tPD_ScheRateCalFactor_LibSchema = new PD_ScheRateCalFactor_LibSchema();
 tPD_ScheRateCalFactor_LibSchema.setFactorCode(request.getParameter("FactorCode"));
 tPD_ScheRateCalFactor_LibSchema.setFactorName(request.getParameter("FactorName"));
 tPD_ScheRateCalFactor_LibSchema.setFactorDataType(request.getParameter("FactorDataType"));
 tPD_ScheRateCalFactor_LibSchema.setFactorType(request.getParameter("FactorType"));
 
 transferData.setNameAndValue("PD_ScheRateCalFactor_LibSchema",tPD_ScheRateCalFactor_LibSchema);
 
 try
 {
  // ׼���������� VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  pDScheRateCalFactorBL.submitData(tVData,operator);
 }
 catch(Exception ex)
 {
  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
  FlagStr = "Fail";
 }

 //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
 if (FlagStr=="")
 {
  tError = pDScheRateCalFactorBL.mErrors;
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

