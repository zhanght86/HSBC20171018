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
 

 //PDRiskAccPayBL pDRiskAccPayBL = new PDRiskAccPayBL();
 
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
 String  PAYPLANCODE = request.getParameter("PAYPLANCODE");
 String  INSUACCNO = request.getParameter("INSUACCNO");
 String  RISKCODE = request.getParameter("RISKCODE");
 String  PAYPLANNAME = request.getParameter("PAYPLANNAME");
  String  DEFAULTRATE = request.getParameter("DEFAULTRATE");
 String  NEEDINPUT = request.getParameter("NEEDINPUT");
 String  ACCCREATEPOS = request.getParameter("ACCCREATEPOS");
 String  CALFLAG = request.getParameter("CALFLAG");
 //String  RISKACCPAYNAME = request.getParameter("RISKACCPAYNAME");
 
	String CALCODEMONEY="";
	String  PAYNEEDTOACC = "";
	String  RISKACCPAYNAME = "";
	String  ASCRIPTION = "";
 //tLDCodeSchema.setCodeType(request.getParameter("CodeType"));

 //String values[] = request.getParameterValues("Mulline9Grid4");
 java.util.ArrayList list = new java.util.ArrayList();
 


 list.add(PAYPLANCODE);
 list.add(INSUACCNO);
 list.add(RISKCODE);
 list.add(DEFAULTRATE);
  list.add(NEEDINPUT);
 //list.add(ACCCREATEPOS);
 list.add(CALCODEMONEY);
 list.add(CALFLAG);
 list.add(PAYPLANNAME);
 list.add(PAYNEEDTOACC);
 list.add(RISKACCPAYNAME);
 list.add(ASCRIPTION);
 
 //list.add(RISKACCPAYNAME);
transferData.setNameAndValue("list",list);
transferData.setNameAndValue("tableName",request.getParameter("tableName"));
transferData.setNameAndValue("RiskCode",request.getParameter("RISKCODE"));
//String tCalCodeType = request.getParameter("CalCodeSwitchFlag");
//transferData.setNameAndValue("CalCodeType",tCalCodeType);
 try
 {
  // ׼���������� VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  //pDRiskAccPayBL.submitData(tVData,operator);
String busiName="PDRiskAccPayBL"; 
  
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
  }
 
 }
 catch(Exception ex)
 {
  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
  FlagStr = "Fail";
 }
  /*
  
  new RiskState().setState(request.getParameter("riskcode"), "��Լҵ�����->�˻����Ѷ���", "1");
 }
 catch(Exception ex)
 {
  Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
  FlagStr = "Fail";
 }

 //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
 if (FlagStr=="")
 {
  tError = pDRiskAccPayBL.mErrors;
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

