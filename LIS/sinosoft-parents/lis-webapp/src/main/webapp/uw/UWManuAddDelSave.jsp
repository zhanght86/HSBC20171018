<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2005-10-22 11:10:36
//�� �� ��: guanwei
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.service.*" %>
  
<%//���������������ֱ�õ���һҳ���ֵ
  GlobalInput tGlobalInput = new GlobalInput();
  tGlobalInput = (GlobalInput)session.getValue("GI");
 // ./UWManuAddDelSave.jsp?DelPolNo="+tPolNo+"&DelDutyCode="+tDutyCode+"&DelPayPlanCode="+tPayPlanCode;
  String tPolNo=request.getParameter("DelPolNo"); //Ͷ������
  loggerDebug("UWManuAddDelSave","===tPolNo======"+tPolNo) ;
  String tDutyCode=request.getParameter("DelDutyCode"); //�ӷ�����
   loggerDebug("UWManuAddDelSave","===tDutyCode======"+tDutyCode);
  String tPayPlanCode=request.getParameter("DelPayPlanCode"); //�ӷ�ԭ��
 loggerDebug("UWManuAddDelSave","===tPayPlanType======"+tPayPlanCode);

	//������ֵ������ֵװ��TransferData��
	TransferData tTransferData = new TransferData();
  tTransferData.setNameAndValue("PolNo",tPolNo);
  loggerDebug("UWManuAddDelSave","::::::::::::::polno::::::::::::"+tPolNo);
  tTransferData.setNameAndValue("DutyCode",tDutyCode);
   loggerDebug("UWManuAddDelSave","::::::::::::::tDutyCode::::::::::::"+tDutyCode);
  tTransferData.setNameAndValue("PayPlanCode",tPayPlanCode);
   loggerDebug("UWManuAddDelSave","::::::::::::::tPayPlanType::::::::::::"+tPayPlanCode);


	//��TransferData װ��VData
  VData tVData = new VData();
  VData mResult = new VData();
  CErrors mErrors = new CErrors();
    tVData.addElement(tTransferData);
    tVData.addElement(tGlobalInput);


  //��������
  String Content = "";
  String FlagStr = "";
  loggerDebug("UWManuAddDelSave","CCCCCCCCCCCCCCCCCCCCCCCCCcc");
  //UWManuAddDelUI tUWManuAddDelUI = new UWManuAddDelUI();
  
  String busiName="cbcheckUWManuAddDelUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  if (!tBusinessDelegate.submitData(tVData, "",busiName)) {
    //VData rVData = tUWManuAddDelUI.mErrors;

    Content = " ����ʧ�ܣ�ԭ����:" +tBusinessDelegate.getCErrors().getFirstError().toString() ;
 	  FlagStr = "Fail";
  }
  else {
    Content = "^_^ �ѳɹ�ɾ��������¼ ^_^ ";
  	FlagStr = "Succ";


	loggerDebug("UWManuAddDelSave",Content + "\n" + FlagStr + "\n---GetReturnFromBankSave End---\n\n");
%>
<html>
<script language = "JavaScript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
	//parent.fraInterface.initQuery();
</script>
</html>
<%  }%>
