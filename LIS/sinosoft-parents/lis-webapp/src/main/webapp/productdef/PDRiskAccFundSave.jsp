<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDRiskAccPaySave.jsp
//�����ܣ��˻������ֶ���
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
             
 String FlagStr = "Success";
 String Content = ""+"�ύ�ɹ���"+"";
 String operator = "";
 TransferData transferData = new TransferData();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 
 //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
operator = request.getParameter("operator");
PD_LMRiskAccFundSchema   tPD_LMRiskAccFundSchema=new PD_LMRiskAccFundSchema();
tPD_LMRiskAccFundSchema.setRiskCode(request.getParameter("RISKCODE"));
tPD_LMRiskAccFundSchema.setPayPlanCode(request.getParameter("PayPlanCode000"));
tPD_LMRiskAccFundSchema.setAccountCode(request.getParameter("AccountCode000"));
tPD_LMRiskAccFundSchema.setAccountName(request.getParameter("AccountCodeName000"));
tPD_LMRiskAccFundSchema.setAccountType(request.getParameter("AccountType000"));
tPD_LMRiskAccFundSchema.setOperator(request.getParameter("operator"));
  VData tVData = new VData();
  tVData.add(tG);
  tVData.add(tPD_LMRiskAccFundSchema);
  tVData.add(transferData);
 PD_LMRiskAccFundBL tPD_LMRiskAccFundBL=new PD_LMRiskAccFundBL();
 if(!tPD_LMRiskAccFundBL.submitData(tVData,operator)){
 FlagStr="Fail";
 System.out.println("�ύʧ�ܣ�ԭ���ǣ�"+tPD_LMRiskAccFundBL.getErrorMessage());
 Content=""+"�ύʧ�ܣ�ԭ���ǣ�"+""+tPD_LMRiskAccFundBL.getErrorMessage();
 }
%>                      

<html>
<script type="text/javascript">
<%if((operator.equals("delete")||operator.equals("del"))&&FlagStr.equals("Success")){
%>parent.fraInterface.reset();<%
}%>
	parent.fraInterface.afterSubmit1("<%=FlagStr%>","<%=Content%>");
</script>
</html>

