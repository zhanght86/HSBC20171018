<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDDutyGetClmSave.jsp
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
 

 //PDDutyGetClmBL pDDutyGetClmBL = new PDDutyGetClmBL();
 
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
/*
 String values[] = request.getParameterValues("Mulline9Grid4");
 java.util.ArrayList list = new java.util.ArrayList();
 for(int i = 0; i < values.length; i++)
 {
  list.add(values[i]);
 }
transferData.setNameAndValue("list",list);*/
transferData.setNameAndValue("tableName",request.getParameter("tableName"));

transferData.setNameAndValue("tableName2",request.getParameter("tableName2"));
transferData.setNameAndValue("getDutyCode2",request.getParameter("getDutyCode2"));
transferData.setNameAndValue("getDutyCode",request.getParameter("GetDutyCode"));
transferData.setNameAndValue("dutyType2",request.getParameter("dutyType2"));
transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));
//transferData.setNameAndValue("IsSubmitData","0");
/*
	document.getElementById('GETDUTYKIND').value= '';
		document.getElementById('INPFLAG').value= '';
		document.getElementById('STATTYPE').value= '';
		document.getElementById('STATTYPEName').value= '';
		document.getElementById('INPFLAGName').value= '';
		document.getElementById('CALCODE').value= '';
*/
String tCalCodeType = request.getParameter("CalCodeSwitchFlag");
String tGETDUTYKIND =  request.getParameter("GETDUTYKIND");
String tSTATTYPE =  request.getParameter("STATTYPE");
String tCALCODE =  request.getParameter("CALCODE");
String tObsPeriod = request.getParameter("ObsPeriod");
String tAFTERGET =  request.getParameter("AfterGet");
String tGETBYHOSDAY = request.getParameter("GetByHosDay");

transferData.setNameAndValue("CalCodeType",tCalCodeType);
transferData.setNameAndValue("GETDUTYKIND",tGETDUTYKIND);
transferData.setNameAndValue("STATTYPE",tSTATTYPE);
transferData.setNameAndValue("CALCODE",tCALCODE);
transferData.setNameAndValue("OBSPERIOD",tObsPeriod);
transferData.setNameAndValue("AFTERGET",tAFTERGET);
transferData.setNameAndValue("GETBYHOSDAY",tGETBYHOSDAY);
 try
 {
  // ׼���������� VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  //pDDutyGetClmBL.submitData(tVData,operator);
  String busiName="PDDutyGetClmBL";
  
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
  //RiskState.setState(request.getParameter("RiskCode"), "����ҵ�����->���������⸶��ϸ","1");
  
 
 //��Ӹ���Ԥ����
%>                      
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.setCalCode("<%=tCALCODE%>");
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

