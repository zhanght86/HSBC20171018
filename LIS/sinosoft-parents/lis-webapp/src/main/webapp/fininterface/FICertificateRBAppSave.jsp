<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%

//������  jw

%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.fininterface.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%

  loggerDebug("FICertificateRBAppSave","11111111111");

  //�������
  CErrors tError = null;
  String FlagStr = "Succ";
  String Content = "";
  String tFlag = request.getParameter("tFlag");
  String tManageCom = request.getParameter("ManageCom");
  
  String CertificateId = request.getParameter("CertificateId");
  String BusinessNo = request.getParameter("BusinessNo");
  String DetailIndexID = request.getParameter("DetailIndexID");
  String DetailIndexName = request.getParameter("DetailIndexName");
  String ReasonType = request.getParameter("ReasonType");
  String DetailReMark = request.getParameter("DetailReMark");
  String AppDate = request.getParameter("AppDate");
  String Applicant = request.getParameter("Applicant");
  
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  if(tG == null)
  {
  loggerDebug("FICertificateRBAppSave","session has expired");
  return;
  }
  
  VData tVData = new VData();
  FIDataFeeBackAppSchema tFIDataFeeBackAppSchema = new FIDataFeeBackAppSchema();

  tFIDataFeeBackAppSchema.setCertificateID(CertificateId);  
  tFIDataFeeBackAppSchema.setAppDate(AppDate);
  tFIDataFeeBackAppSchema.setApplicant(Applicant);
  tFIDataFeeBackAppSchema.setAppState("01");
  tFIDataFeeBackAppSchema.setBusinessCode("OOO");
  tFIDataFeeBackAppSchema.setBusinessNo(BusinessNo);
  tFIDataFeeBackAppSchema.setDetailIndexID(DetailIndexID);
  tFIDataFeeBackAppSchema.setDetailIndexName(DetailIndexName);
  tFIDataFeeBackAppSchema.setDetailReMark(DetailReMark);
  tFIDataFeeBackAppSchema.setReasonType(ReasonType);
  
  
   tVData.add( tFIDataFeeBackAppSchema );
   tVData.add( tG );
   try
   {
      //FICertificateApp tFICertificateApp = new FICertificateApp();
      BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
      if (!uiBusinessDelegate.submitData(tVData,"","FICertificateApp"))
      {
      	Content = uiBusinessDelegate.getCErrors().getFirstError();
      	FlagStr = "Fail";
      }
        				//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
      if (FlagStr != "Fail")
      {				    
        Content = " �������ɹ�! ";
        FlagStr = "Succ";
      		    			     
      }
   
   }
   catch(Exception ex)
   {
   	ex.printStackTrace();
   	Content = Content.trim() +" ��ʾ:�쳣�˳�.";
   }
%>
<html>
<script language="javascript">
   parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
