<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDRiskRoleSave.jsp
//�����ܣ����ֽ�ɫ����
//�������ڣ�2009-3-13
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
 

 PDRiskRoleBL pDRiskRoleBL = new PDRiskRoleBL();
 
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

 String values[] = request.getParameterValues("Mulline9Grid4");
 java.util.ArrayList list = new java.util.ArrayList();
 for(int i = 0; i < values.length; i++)
 {
  list.add(values[i]);
 }
transferData.setNameAndValue("list",list);
transferData.setNameAndValue("tableName",request.getParameter("tableName"));
transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));

 try
 {
  // ׼���������� VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  String busiName="PDRiskRoleBL";
  
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //String tDiscountCode = "";
  if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
	  VData rVData = tBusinessDelegate.getResult();
    Content = " "+"����ʧ�ܣ�ԭ����:"+"" + tBusinessDelegate.getCErrors().getFirstError();
  	FlagStr = "Fail";
  }
  else {
	
    Content = " "+"����ɹ�!"+" ";
  	FlagStr = "Succ";
  	 RiskState.setState(request.getParameter("RiskCode"),""+"��Լҵ�����"+"->"+"���ָ���ɫ����"+"","1");
  	// new RiskState().setState(request.getParameter("RiskCode"), "��Լҵ�����->���ֺ˱�����", "1");

  }
 
 }
 catch(Exception ex)
 {
  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
  FlagStr = "Fail";
 }
 /* pDRiskRoleBL.submitData(tVData,operator);
 }
 catch(Exception ex)
 {
  Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
  FlagStr = "Fail";
 }

 //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
 if (FlagStr=="")
 {
  tError = pDRiskRoleBL.mErrors;
  if (!tError.needDealError())
  {                          
   Content = " ����ɹ�! ";
   FlagStr = "Success";
   RiskState.setState(request.getParameter("RiskCode"),"��Լҵ�����->���ָ���ɫ����","1");
  }
  else                                                                           
  {
   Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
   FlagStr = "Fail";
  }
 }*/
 

 //��Ӹ���Ԥ����
%>                      
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

