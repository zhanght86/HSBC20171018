<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDAlgoTempLibSave.jsp
//�����ܣ��㷨ģ���
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
 

 PDchoflagBL tPDchoflagBL = new PDchoflagBL();
 
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
 
 String tRadio[] = request.getParameterValues("InpMulline9GridSel");  
 String tCol1[] = request.getParameterValues("Mulline9Grid1");  
 String tCol4[] = request.getParameterValues("Mulline9Grid4");  
 String tCol5[] = request.getParameterValues("Mulline9Grid5");  
 //������ʽ=�� Inp+MulLine������+Sel�� 
 
 PD_LMRiskDutySchema tPd_LmriskdutySchema = new PD_LMRiskDutySchema();
 int count = tRadio.length;
 
 for (int index=0; index< tRadio.length;index++)
 {	 
  if(tRadio[index].equals("1"))
  {
	  tPd_LmriskdutySchema.setRiskCode(tCol1[index]);
	  tPd_LmriskdutySchema.setChoFlag(tCol5[index]);
	  tPd_LmriskdutySchema.setDutyCode(tCol4[index]);
	  
	  break;
  }
}
 
 transferData.setNameAndValue("PD_LMRiskDutySchema",tPd_LmriskdutySchema);
 transferData.setNameAndValue("TableName", request.getParameter("TableName"));
 try
 {
  // ׼���������� VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  tPDchoflagBL.submitData(tVData,operator);
 }
 catch(Exception ex)
 {
  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
  FlagStr = "Fail";
 }

 //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
 if (FlagStr=="")
 {
  tError = tPDchoflagBL.mErrors;
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


