<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=UTF-8" %>

<%
//�������ƣ�PDCheckRuleSave.jsp
//�����ܣ���������
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
 

 PDCheckRuleBL pDCheckRuleBL = new PDCheckRuleBL();
 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 TransferData transferData = new TransferData();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getValue("GI");
 
 //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
 operator = request.getParameter("operator");

 PD_CheckRule_LibSchema tPD_CheckRule_LibSchema = new PD_CheckRule_LibSchema();
 
 
 String type = request.getParameter("Type");
 String checkRuleCode = "";
 
 if(operator.equals("save"))
 {
 	PDGetMaxNo getMaxNo = new PDGetMaxNo();
 	String maxNo = getMaxNo.getMaxNo("CHECKRULE",type);
 	checkRuleCode = type + maxNo;
 }
 else
 {
	 checkRuleCode = request.getParameter("CheckRuleCode");
 }
 
 tPD_CheckRule_LibSchema.setCode(checkRuleCode);
 tPD_CheckRule_LibSchema.setType(type);
 tPD_CheckRule_LibSchema.setName(request.getParameter("CheckRuleName"));
 tPD_CheckRule_LibSchema.setAlgo(request.getParameter("Algo"));
 tPD_CheckRule_LibSchema.setDescription(request.getParameter("Description"));
 
 transferData.setNameAndValue("PD_CheckRule_LibSchema",tPD_CheckRule_LibSchema);
 try
 {
  // ׼���������� VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  pDCheckRuleBL.submitData(tVData,operator);
 }
 catch(Exception ex)
 {
  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
  FlagStr = "Fail";
 }

 //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
 if (FlagStr=="")
 {
  tError = pDCheckRuleBL.mErrors;
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
<script language="javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

