<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDEdorZTDutySave.jsp
//�����ܣ��޸�ZTDuty
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
 

 PDEdorZTDutyBL tPDEdorZTDutyBL = new PDEdorZTDutyBL();
 
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
 
 String tRadio[] = request.getParameterValues("InpMulline11GridSel");  
 String tCol1[] = request.getParameterValues("Mulline11Grid1");  
 String tCol2[] = request.getParameterValues("Mulline11Grid2");  
 String tCol7[] = request.getParameterValues("Mulline11Grid7");  
 String tCol8[] = request.getParameterValues("Mulline11Grid8");  
 //������ʽ=�� Inp+MulLine������+Sel�� 
 
 PD_LMEdorZTDutySchema tPD_LMEdorZTDutySchema = new PD_LMEdorZTDutySchema();
 int count = tRadio.length;
 
 for (int index=0; index< tRadio.length;index++)
 {	 
  if(tRadio[index].equals("1"))
  {
	  tPD_LMEdorZTDutySchema.setRiskCode(tCol1[index]);
	  tPD_LMEdorZTDutySchema.setDutyCode(tCol2[index]);
	  tPD_LMEdorZTDutySchema.setPayByAcc(tCol7[index]);
	  tPD_LMEdorZTDutySchema.setPayCalType(tCol8[index]);
	  
	  break;
  }
}
 
 transferData.setNameAndValue("PD_LMEdorZTDutySchema",tPD_LMEdorZTDutySchema);
 transferData.setNameAndValue("TableName", request.getParameter("TableName"));
 try
 {
  // ׼���������� VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  tPDEdorZTDutyBL.submitData(tVData,operator);
 }
 catch(Exception ex)
 {
  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
  FlagStr = "Fail";
 }

 //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
 if (FlagStr=="")
 {
  tError = tPDEdorZTDutyBL.mErrors;
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


