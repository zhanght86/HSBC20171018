<%@include file="../i18n/language.jsp"%>

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDRiskInsuAccSave.jsp
//�����ܣ������˻�����
//�������ڣ�2009-3-12
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
 

 //PDRiskInsuAccBL pDRiskInsuAccBL = new PDRiskInsuAccBL();
 
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
 
 //tLDCodeSchema.setCodeType(request.getParameter("CodeType"));
 String values[] = request.getParameterValues("Mulline10Grid4");
 String values2[] = request.getParameterValues("Mulline10Grid2");
 java.util.ArrayList list = new java.util.ArrayList();
 java.util.ArrayList list2 = new java.util.ArrayList();
 for(int i = 0; i < values.length; i++)
 {
  list.add(values[i]);
  list2.add(values2[i]);
 }
	transferData.setNameAndValue("list",list);
	transferData.setNameAndValue("list2",list2);
	transferData.setNameAndValue("tableName",request.getParameter("tableName"));
	transferData.setNameAndValue("tableName2",request.getParameter("tableName"));
	transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));
 	try
 	{
  // ׼���������� VData
  	VData tVData = new VData();

  	tVData.add(tG);
  	tVData.add(transferData);
  	//pDRiskInsuAccBL.submitData(tVData,operator);
	String busiName="PDRiskInsuAccBL";
    
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    //String tDiscountCode = "";
    if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
  	  VData rVData = tBusinessDelegate.getResult();
      Content = " "+"����ʧ�ܣ�ԭ����:"+"" + tBusinessDelegate.getCErrors().getFirstError();
    	FlagStr = "Fail";
    }
    else {
      Content = " "+"����ɹ�!"+" ";
    	FlagStr = "Success";
    }
   
   }
   catch(Exception ex)
   {
    Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
    FlagStr = "Fail";
   }
  	/*
 }
 catch(Exception ex)
 {
  Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
  FlagStr = "Fail";
 }

 //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
 if (FlagStr=="")
 {
  tError = pDRiskInsuAccBL.mErrors;
  if (!tError.needDealError())
  {                          
   Content = " ����ɹ�! ";
   FlagStr = "Success";
  }
  else                                                                           
  {
   Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
   FlagStr = "Fail";
  }
 }
*/
 //��Ӹ���Ԥ����
%>                      
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

