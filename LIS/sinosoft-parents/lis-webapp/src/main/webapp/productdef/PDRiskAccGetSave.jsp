<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDRiskAccGetSave.jsp 
//�����ܣ������˻�����
//�������ڣ�2009-3-16
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
 

 PDRiskAccGetBL pDRiskAccGetBL = new PDRiskAccGetBL();
 
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
	transferData.setNameAndValue("GETDUTYCODE",request.getParameter("GETDUTYCODE"));
	transferData.setNameAndValue("INSUACCNO",request.getParameter("INSUACCNO"));
	transferData.setNameAndValue("RISKCODE",request.getParameter("RISKCODE"));
	transferData.setNameAndValue("GETDUTYNAME",request.getParameter("GETDUTYNAME"));
	transferData.setNameAndValue("tableName",request.getParameter("tableName"));
	
	transferData.setNameAndValue("DEFAULTRATE","");
	transferData.setNameAndValue("NEEDINPUT","");
	transferData.setNameAndValue("CALCODEMONEY","");
	transferData.setNameAndValue("DEALDIRECTION","1");
	transferData.setNameAndValue("CALFLAG","");
	transferData.setNameAndValue("ACCCREATEPOS","");
 try
 {
  // ׼���������� VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  pDRiskAccGetBL.submitData(tVData,operator);
 }
 catch(Exception ex)
 {
  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
  FlagStr = "Fail";
 }

 //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
 if (FlagStr=="")
 {
  tError = pDRiskAccGetBL.mErrors;
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

