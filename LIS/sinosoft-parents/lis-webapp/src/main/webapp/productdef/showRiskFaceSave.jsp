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

 

 //RiskFaceDefService mRiskFaceDefService = new RiskFaceDefService();
	
 String riskOrder = request.getParameter("riskOrder");

 String RiskCode = request.getParameter("RiskCode");

 String StandbyFlag1 = request.getParameter("StandbyFlag1");

// try
 //{
  // ׼���������� VData
  VData tVData = new VData();


  tVData.add(tG);

  tVData.add(0,riskOrder);

  tVData.add(1,RiskCode);

  tVData.add(2,StandbyFlag1);

 // mRiskFaceDefService.savRiskOrder(tVData);
 // pD_LMDutyGetClmCalBL.submitData(tVData,operator);
 //}
 String busiName="RiskFaceDefUI";
  
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

  //String tDiscountCode = "";
  if (!tBusinessDelegate.submitData(tVData, "RiskFaceDefService",busiName)) {
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
  
	out.println("test");

%>     

