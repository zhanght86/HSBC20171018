<%@include file="../i18n/language.jsp"%>

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PD_LMDutyGetClmCalSave.jsp
//�����ܣ����θ����⸶������㹫ʽ
//�������ڣ�2009-3-16
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
 //������Ϣ������У�鴦��
 //�������
 


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

 String[] values = request.getParameterValues("arr");
 System.out.println("values:"+values);
	RiskParamsBL mRiskParamsBL = new RiskParamsBL("");
	
	String RiskCode = request.getParameter("RiskCode");
	String StandbyFlag1 = request.getParameter("StandbyFlag1");
	
	String DutyChoose = request.getParameter("DutyChoose");
	String PayChoose = request.getParameter("PayChoose");
	
	String[] DutySel = request.getParameterValues("DutySel");
	String[] PaySel = request.getParameterValues("PaySel");
try{
  // ׼���������� VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(0,values);
   tVData.add(1,RiskCode);
   tVData.add(2,DutyChoose);
   tVData.add(3,PayChoose);
   tVData.add(4,DutySel);
   tVData.add(5,PaySel);
   tVData.add(6,StandbyFlag1);
 
  String busiName="RiskFaceDefUI";
  
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //String tDiscountCode = "";
  if (!tBusinessDelegate.submitData(tVData, "SaveRiskParams",busiName)) {
	  VData rVData = tBusinessDelegate.getResult();
    //Content = " ����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
  	//FlagStr = "Fail";
  }
  else {
	  VData rVData = tBusinessDelegate.getResult();
	 // getItems = (JSONObject)rVData.get(0);
	
    //Content = " ����ɹ�! ";
  	//FlagStr = "Succ";
  	// new RiskState().setState(request.getParameter("RiskCode"), "��Լҵ�����->���ֺ˱�����", "1");

  }
  }
 catch(Exception ex)
 {
 // Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
 // FlagStr = "Fail";
 }
 
%>                      
<html>
<script type="text/javascript">

</script>
</html>

