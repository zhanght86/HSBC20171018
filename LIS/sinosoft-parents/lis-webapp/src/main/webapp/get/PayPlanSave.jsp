<%
 loggerDebug("PayPlanSave","Start PayPlan JSP Submit...1");
//�������ƣ�PayPlanInput.jsp
//�����ܣ�
//�������ڣ�2002-07-24 08:38:44
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.get.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  //������Ϣ������У�鴦��
  //�������
  //�������
  CErrors tError = null;
  String tBmCert = "";

  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String Count = "";
  loggerDebug("PayPlanSave","Start PayPlan JSP Submit...");
 
  GlobalInput tGlobalInput = new GlobalInput();
	tGlobalInput=(GlobalInput)session.getValue("GI"); //���ҳ��ؼ��ĳ�ʼ����
	
 
  LCPolSchema tLCPolSchema=new LCPolSchema();
  tLCPolSchema.setContNo(request.getParameter("ContNo"));
  tLCPolSchema.setGrpContNo(request.getParameter("GrpContNo"));
  tLCPolSchema.setManageCom(request.getParameter("ManageCom"));
  tLCPolSchema.setAppntNo(request.getParameter("AppntNo"));
  tLCPolSchema.setInsuredNo(request.getParameter("InsuredNo"));
  
  LCGetSchema tLCGetSchema=new LCGetSchema();
  tLCGetSchema.setContNo(request.getParameter("ContNo"));
  tLCGetSchema.setGrpContNo(request.getParameter("GrpContNo"));
  tLCGetSchema.setManageCom(request.getParameter("ManageCom"));
  //tLCGetSchema.setAppntNo(request.getParameter("AppntNo"));
  tLCGetSchema.setInsuredNo(request.getParameter("InsuredNo"));
  
  TransferData aTransferData = new TransferData();
  //aTransferData.setNameAndValue("timeStart",request.getParameter("timeStart"));
  aTransferData.setNameAndValue("timeEnd",request.getParameter("timeEnd"));
  
 
  
 // PayPlanUI tPayPlanUI = new PayPlanUI();
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  VData tVData = new VData();
  tVData.addElement(tGlobalInput) ;
	tVData.addElement(aTransferData);
	tVData.addElement(tLCPolSchema);
	tVData.addElement(tLCGetSchema);
	
  loggerDebug("PayPlanSave","Start PayPlan JSP Submit...4");
  try
  {	  
    //tPayPlanUI.submitData(tVData,"INSERT||PERSON");
    tBusinessDelegate.submitData(tVData,"INSERT||PERSON","PayPlanUI")
  }
  catch(Exception ex)
  {
    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }

  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    //tError = tPayPlanUI.mErrors;
    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {
   
      Content = " ����ɹ�";
      loggerDebug("PayPlanSave","------111---");
      // if (tPayPlanUI.getResult()!=null&&tPayPlanUI.getResult().size()>0)
      //{      
			//	loggerDebug("PayPlanSave","------SerialNo:"+tPayPlanUI.getResult().get(0));
      //  FlagStr = (String)tPayPlanUI.getResult().get(0);
      //  Count = (String)tPayPlanUI.getResult().get(1);
      //}
       if (tBusinessDelegate.getResult()!=null&&tBusinessDelegate.getResult().size()>0)
      {      
				loggerDebug("PayPlanSave","------SerialNo:"+tBusinessDelegate.getResult().get(0));
        FlagStr = (String)tBusinessDelegate.getResult().get(0);
        Count = (String)tBusinessDelegate.getResult().get(1);
      }
      else
      {
     	   Content = "�߸�ʧ�ܣ��Ѿ������߸���¼��";
     	   FlagStr = "Fail";
      }
  
    }
    else
    {
    	Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
  //��Ӹ���Ԥ����
 
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=Count%>");
</script>
</html>

