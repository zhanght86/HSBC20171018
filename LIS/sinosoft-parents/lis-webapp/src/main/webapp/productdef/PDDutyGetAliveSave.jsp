<%@include file="../i18n/language.jsp"%>

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDDutyGetAliveSave.jsp
//�����ܣ����θ�������
//�������ڣ�2009-3-16
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
 

 //PDDutyGetAliveBL pDDutyGetAliveBL = new PDDutyGetAliveBL();
 
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
 
 /*
 String values[] = request.getParameterValues("Mulline9Grid4");
 java.util.ArrayList list = new java.util.ArrayList();
 for(int i = 0; i < values.length; i++)
 {
  list.add(values[i]);
 }
transferData.setNameAndValue("list",list);

*/


String tGETDUTYKIND =  request.getParameter("GETDUTYKIND");
String tGETINTV =  request.getParameter("GETINTV");
String tGETSTARTPERIOD =  request.getParameter("GETSTARTPERIOD");
String tGETSTARTUNIT =  request.getParameter("GETSTARTUNIT");
String tSTARTDATECALREF =  request.getParameter("STARTDATECALREF");
String tSTARTDATECALMODE =  request.getParameter("STARTDATECALMODE");
String tENDDATECALREF =  request.getParameter("ENDDATECALREF");
String tENDDATECALMODE =  request.getParameter("ENDDATECALMODE");
String tAFTERGET =  request.getParameter("AFTERGET");
String tCALCODE =  request.getParameter("CALCODE");
String tCalCodeType = request.getParameter("CalCodeSwitchFlag");

String tGETENDPERIOD =  request.getParameter("GETENDPERIOD");
String tGETENDUNIT =  request.getParameter("GETENDUNIT");
String tGETACTIONTYPE =  request.getParameter("GETACTIONTYPE");
String tURGEGETFLAG =  request.getParameter("URGEGETFLAG");
String tMAXGETCOUNTTYPE =  request.getParameter("MAXGETCOUNTTYPE");
String tNeedReCompute = request.getParameter("NeedReCompute");

transferData.setNameAndValue("GETDUTYKIND",tGETDUTYKIND);
transferData.setNameAndValue("GETINTV",tGETINTV);
transferData.setNameAndValue("GETSTARTPERIOD",tGETSTARTPERIOD);
transferData.setNameAndValue("GETSTARTUNIT",tGETSTARTUNIT);
transferData.setNameAndValue("STARTDATECALREF",tSTARTDATECALREF);
transferData.setNameAndValue("STARTDATECALMODE",tSTARTDATECALMODE);
transferData.setNameAndValue("ENDDATECALREF",tENDDATECALREF);
transferData.setNameAndValue("ENDDATECALMODE",tENDDATECALMODE);
transferData.setNameAndValue("AFTERGET",tAFTERGET);
transferData.setNameAndValue("CALCODE",tCALCODE);
transferData.setNameAndValue("CalCodeType",tCalCodeType);

transferData.setNameAndValue("GETENDPERIOD",tGETENDPERIOD);
transferData.setNameAndValue("GETENDUNIT",tGETENDUNIT);
transferData.setNameAndValue("GETACTIONTYPE",tGETACTIONTYPE);
transferData.setNameAndValue("URGEGETFLAG",tURGEGETFLAG);
transferData.setNameAndValue("MAXGETCOUNTTYPE",tMAXGETCOUNTTYPE);
transferData.setNameAndValue("NeedReCompute",tNeedReCompute);

transferData.setNameAndValue("tableName",request.getParameter("tableName"));
transferData.setNameAndValue("tableName2",request.getParameter("tableName2"));
transferData.setNameAndValue("getDutyCode2",request.getParameter("getDutyCode2"));
transferData.setNameAndValue("getDutyCode",request.getParameter("GetDutyCode"));
transferData.setNameAndValue("dutyType2",request.getParameter("dutyType2"));
transferData.setNameAndValue("IsSubmitData","0");
transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));

 try
 {
  // ׼���������� VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  //pDDutyGetAliveBL.submitData(tVData,operator);
String busiName="PDDutyGetAliveBL";
  
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //String tDiscountCode = "";
  if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
	  VData rVData = tBusinessDelegate.getResult();
    Content = " "+"����ʧ�ܣ�ԭ����:"+"" + tBusinessDelegate.getCErrors().getFirstError();
  	FlagStr = "Fail";
  }
  else {
	  VData rVData = tBusinessDelegate.getResult();
	  tCALCODE = (String)rVData.get(0);
    Content = " "+"����ɹ�!"+" ";
  	FlagStr = "Succ";
  	// new RiskState().setState(request.getParameter("RiskCode"), "��Լҵ�����->���ֺ˱�����", "1");

  }
 
 }
 catch(Exception ex)
 {
  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
  FlagStr = "Fail";
 }
  /*
  if(((String)request.getParameter("tableName")).equals("PD_LMDutyGetAlive"))
  {
   	RiskState.setState(request.getParameter("RiskCode"), "��Ʒ�����->�������������Ϣ", "1");
   }
  else
  {
  	RiskState.setState(request.getParameter("RiskCode"), "����ҵ�����->���������⸶��ϸ","1");
  }
 }
 catch(Exception ex)
 {
  Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
  FlagStr = "Fail";
 }

 //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
 if (FlagStr=="")
 {
  tError = pDDutyGetAliveBL.mErrors;
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
 }*/

 //��Ӹ���Ԥ����
%>                      
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.setCalCode("<%=tCALCODE%>");
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

