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
  <%@page import="com.sinosoft.lis.claim.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%//���������������ֱ�õ���һҳ���ֵ
  GlobalInput tGlobalInput = new GlobalInput();
  tGlobalInput = (GlobalInput)session.getValue("GI");
 // ./UWManuAddDelSave.jsp?DelPolNo="+tPolNo+"&DelDutyCode="+tDutyCode+"&DelPayPlanCode="+tPayPlanCode;
  String tPolNo=request.getParameter("DelPolNo"); //Ͷ������
  loggerDebug("LLUWManuAddDelSave","===tPolNo======"+tPolNo) ;
  String tDutyCode=request.getParameter("DelDutyCode"); //�ӷ�����
   loggerDebug("LLUWManuAddDelSave","===tDutyCode======"+tDutyCode);
  String tPayPlanCode=request.getParameter("DelPayPlanCode"); //�ӷ�ԭ��
 loggerDebug("LLUWManuAddDelSave","===tPayPlanType======"+tPayPlanCode);

  String tClmNo = request.getParameter("ClmNo");
  
  String tBatNo = request.getParameter("BatNo");
  
	//������ֵ������ֵװ��TransferData��
	TransferData tTransferData = new TransferData();
  tTransferData.setNameAndValue("PolNo",tPolNo);
  loggerDebug("LLUWManuAddDelSave","::::::::::::::polno::::::::::::"+tPolNo);
  tTransferData.setNameAndValue("DutyCode",tDutyCode);
   loggerDebug("LLUWManuAddDelSave","::::::::::::::tDutyCode::::::::::::"+tDutyCode);
  tTransferData.setNameAndValue("PayPlanCode",tPayPlanCode);
   loggerDebug("LLUWManuAddDelSave","::::::::::::::tPayPlanType::::::::::::"+tPayPlanCode);
  tTransferData.setNameAndValue("ClmNo",tClmNo);
  tTransferData.setNameAndValue("BatNo",tBatNo);


	//��TransferData װ��VData
  VData tVData = new VData();
  VData mResult = new VData();
  CErrors mErrors = new CErrors();
    tVData.addElement(tTransferData);
    tVData.addElement(tGlobalInput);


  //��������
  String Content = "";
  String FlagStr = "";
  loggerDebug("LLUWManuAddDelSave","CCCCCCCCCCCCCCCCCCCCCCCCCcc");
  /*LLUWManuAddDelUI tLLUWManuAddDelUI = new LLUWManuAddDelUI();
  if (!tLLUWManuAddDelUI.submitData(tVData, "")) {
    //VData rVData = tUWManuAddDelUI.mErrors;

    Content = " ����ʧ�ܣ�ԭ����:" +tLLUWManuAddDelUI.mErrors.getFirstError().toString() ;
 	  FlagStr = "Fail";
  }
  else {
    Content = "^_^ �ѳɹ�ɾ��������¼ ^_^ ";
  	FlagStr = "Succ";
 */
 String busiName="LLUWManuAddDelUI";
 String mDescType="����";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  if(!tBusinessDelegate.submitData(tVData,"",busiName))
	  {    
	       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	       { 
				Content = mDescType+"ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
				FlagStr = "Fail";
		   }
		   else
		   {
				Content = mDescType+"ʧ��";
				FlagStr = "Fail";				
		   }
	  }
	  else
	  {
		  	Content = "^_^ �ѳɹ�ɾ��������¼ ^_^ ";
	      	FlagStr = "Succ";  
	  }

	loggerDebug("LLUWManuAddDelSave",Content + "\n" + FlagStr + "\n---GetReturnFromBankSave End---\n\n");
%>
<html>
<script language = "JavaScript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
	//parent.fraInterface.initQuery();
</script>
</html>
