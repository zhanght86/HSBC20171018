<%@include file="../i18n/language.jsp"%>

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDRequRiskSave.jsp
//�����ܣ���Ʒ�������ѯ
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
 //������Ϣ������У�鴦��
 //�������
 session.setAttribute("IsReadOnly", "0");
 ProdDefWorkFlowBL tProdDefWorkFlowBL= new ProdDefWorkFlowBL();

 String operator = "Start";
 
 CErrors tError = new CErrors();
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String transact = "";
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 TransferData trans = new TransferData();
 trans.setNameAndValue("RiskCode",request.getParameter("RiskCode"));
 trans.setNameAndValue("RequDate",request.getParameter("RequDate"));
 trans.setNameAndValue("Operator",tG.Operator);
 System.out.println("here");
 try
 {
  // ׼���������� VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(trans);
  tProdDefWorkFlowBL.submitData(tVData,operator);
  
	//��ʼ��ҳ��չʾ
	new RiskState().init(request.getParameter("RiskCode"));
	System.out.println("��ʼ���ɹ�");
 }
 catch(Exception ex)
 {
  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
  FlagStr = "Fail";
 }

 //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
 if (FlagStr.equals(""))
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

 //��Ӹ���Ԥ����
%>                      
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

