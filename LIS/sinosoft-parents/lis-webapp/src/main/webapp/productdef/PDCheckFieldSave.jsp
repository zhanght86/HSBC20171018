<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDCheckFieldSave.jsp
//�����ܣ�Ͷ������
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
 

 //PDCheckFieldBL pDCheckFieldBL = new PDCheckFieldBL();
 
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
 
 
 String tRiskCode =  request.getParameter("RiskCode");
 String tCheckField =  request.getParameter("CheckField");
 String tSerialNo =  request.getParameter("Serialno");
 String tCalCode =  request.getParameter("CalCode");
 String tMsg =  request.getParameter("Msg");
 //tLDCodeSchema.setCodeType(request.getParameter("CodeType"));
 String tSTANDBYFLAG1 =  request.getParameter("STANDBYFLAG1");
 String tCalCodeType = request.getParameter("CalCodeSwitchFlag");
 
 System.out.println("tCalCodeType:"+tCalCodeType);
 /*
 String values[] = request.getParameterValues("Mulline9Grid4");
 java.util.ArrayList list = new java.util.ArrayList();
 for(int i = 0; i < values.length; i++)
 {
  list.add(values[i]);
 } 
 
transferData.setNameAndValue("list",list);

*/
transferData.setNameAndValue("tableName",request.getParameter("tableName"));
transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));
transferData.setNameAndValue("FieldName",tCheckField);
transferData.setNameAndValue("SerialNo",tSerialNo);
transferData.setNameAndValue("CalCode",tCalCode);
transferData.setNameAndValue("Msg",tMsg);
transferData.setNameAndValue("CalCodeType",tCalCodeType);
transferData.setNameAndValue("STANDBYFLAG1",tSTANDBYFLAG1);

 try
 {
  // ׼���������� VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  String busiName="PDCheckFieldBL";
  
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //String tDiscountCode = "";
  if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
	  VData rVData = tBusinessDelegate.getResult();
    Content = " "+"����ʧ�ܣ�ԭ����:"+"" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
	  VData rVData = tBusinessDelegate.getResult();
	  tCalCode = (String)rVData.get(0);
    Content = " "+"����ɹ�!"+" ";
  	FlagStr = "Succ";
  }
 
 }
 catch(Exception ex)
 {
  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
  FlagStr = "Fail";
 }


 //��Ӹ���Ԥ����
%>                      
<%=Content%>
<html>
<script type="text/javascript">
parent.fraInterface.setCalCode("<%=tCalCode%>");
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

