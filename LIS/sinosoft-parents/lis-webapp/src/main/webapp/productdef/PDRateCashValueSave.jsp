<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDRateCashValueSave.jsp
//�����ܣ����ݱ���ֽ��ֵ����
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
 
 
 System.out.println("test:" + request.getParameter("testfm2"));

 PDRateCashValueBL pDRateCashValueBL = new PDRateCashValueBL();
 
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
 
 String MullineName = "";
 if(operator.equals("createRateTable"))
 {
	 MullineName = "Mulline10Grid3";
 }
 else if(operator.equals("deleteRateTable"))
 {
	 
 }
 else if(operator.equals("createCashValue"))
 {
	 MullineName = "Mulline13Grid3";
 }

 if(!MullineName.equals(""))
 {
 String values[] = request.getParameterValues(MullineName);
 
 java.util.ArrayList list = new java.util.ArrayList();
 for(int i = 0; i < values.length; i++)
 {
  list.add(values[i]);
 }
 transferData.setNameAndValue("list",list);
 }
  
transferData.setNameAndValue("tableName",request.getParameter("tableName"));
transferData.setNameAndValue("newTableName",request.getParameter("newTableName"));
transferData.setNameAndValue("PremDataTypeName",request.getParameter("PremDataTypeName"));
transferData.setNameAndValue("CashValueDataTypeName",request.getParameter("CashValueDataTypeName"));
transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));
transferData.setNameAndValue("PayCode",request.getParameter("PayCode"));
transferData.setNameAndValue("DataTBLName",request.getParameter("DataTBLName"));
//transferData.setNameAndValue("CoreTableName",request.getParameter("CoreTableName"));

 try
 {
  // ׼���������� VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  pDRateCashValueBL.submitData(tVData,operator);
 }
 catch(Exception ex)
 {
  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
  FlagStr = "Fail";
 }

 //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
 if (FlagStr=="")
 {
  tError = pDRateCashValueBL.mErrors;
  if (!tError.needDealError())
  {                          
   if(operator.equals("deleteRateTable"))
   {
	   MullineName = "Mulline10Grid3";
	   String tvle[] = request.getParameterValues(MullineName);
	   if(tvle.length ==0)
	   {
		   RiskState.setState(request.getParameter("RiskCode"),""+"��Ʒ�����"+"->"+"���ݱ���"+"","0");
	   }
   }
   else
   {
	   RiskState.setState(request.getParameter("RiskCode"),""+"��Ʒ�����"+"->"+"���ݱ���"+"","1");
   }
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

