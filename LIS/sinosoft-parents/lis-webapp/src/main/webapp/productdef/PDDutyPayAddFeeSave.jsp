<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDDutyPayAddFeeSave.jsp
//�����ܣ��������μӷѶ���
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
 

 //PDDutyPayAddFeeBL pDDutyPayAddFeeBL = new PDDutyPayAddFeeBL();
 
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
 String RISKCODE = request.getParameter("RISKCODE");
 String DUTYCODE = request.getParameter("DUTYCODE");
 String ADDFEETYPE = request.getParameter("ADDFEETYPE");
 String ADDFEEOBJECT = request.getParameter("ADDFEEOBJECT");
 String ADDFEECALCODE = request.getParameter("ADDFEECALCODE");
 String ADDPOINTLIMIT = request.getParameter("ADDPOINTLIMIT");


 //String values[] = request.getParameterValues("Mulline9Grid4");
 java.util.ArrayList list = new java.util.ArrayList();
 list.add(RISKCODE);
 list.add(DUTYCODE);
 list.add(ADDFEETYPE);
 list.add(ADDFEEOBJECT);
 list.add(ADDFEECALCODE);
 list.add(ADDPOINTLIMIT);
 /*for(int i = 0; i < values.length; i++)
 {
  list.add(values[i]);
 }*/
transferData.setNameAndValue("list",list);
 String tCalCodeType = request.getParameter("CalCodeSwitchFlag");
 transferData.setNameAndValue("CalCodeType",tCalCodeType);
transferData.setNameAndValue("tableName",request.getParameter("tableName"));
transferData.setNameAndValue("RiskCode",request.getParameter("RISKCODE"));

 try
 {
  // ׼���������� VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  //pDDutyPayAddFeeBL.submitData(tVData,operator);
String busiName="PDDutyPayAddFeeBL";
  
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //String tDiscountCode = "";
  if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
	//  VData rVData = tBusinessDelegate.getResult();
    Content = " "+"����ʧ�ܣ�ԭ����:"+"" + tBusinessDelegate.getCErrors().getFirstError();
  	FlagStr = "Fail";
  }
  else {
    Content = " "+"����ɹ�!"+" ";
  	FlagStr = "Success";
  	ADDFEECALCODE = (String)tBusinessDelegate.getResult().get(0);
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
  tError = pDDutyPayAddFeeBL.mErrors;
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
parent.fraInterface.setCalCode("<%=ADDFEECALCODE%>");
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

