<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�PRnewUWManuHealthChk.jsp
//�����ܣ��б��˹��˱��������¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";

  GlobalInput tGlobalInput = new GlobalInput();
  tGlobalInput=(GlobalInput)session.getValue("GI");	  
  if(tGlobalInput == null) {
	out.println("session has expired");
	return;
  }

  TransferData tTransferData = new TransferData();
  LCContSchema tLCContSchema = new LCContSchema();
  LCCSpecSchema tLCCSpecSchema = new LCCSpecSchema();
  LCCUWMasterSchema tLCCUWMasterSchema =new LCCUWMasterSchema();
  
	String tContNo = request.getParameter("ContNo");  
	String tCvalidate = request.getParameter("Cvalidate");
	String tOperate = request.getParameter("Operate");
	tLCCSpecSchema.setSerialNo(request.getParameter("SerialNo"));
	tLCCSpecSchema.setProposalContNo(request.getParameter("ProposalContNo"));
	tLCCSpecSchema.setContNo(tContNo);
	tLCCSpecSchema.setGrpContNo("00000000000000000000");
	tLCCSpecSchema.setOperator(tGlobalInput.Operator);
	tLCCSpecSchema.setPrtFlag("1");
	tLCCSpecSchema.setNeedPrint("N");
	tLCCSpecSchema.setSpecCode(request.getParameter("SpecCode"));
	tLCCSpecSchema.setSpecType(request.getParameter("SpecType"));
	tLCCSpecSchema.setSpecReason(request.getParameter("SpecReason"));
	tLCCSpecSchema.setSpecContent(request.getParameter("CvalidateIdea"));
	tLCCUWMasterSchema.setSpecReason(request.getParameter("SpecReason"));
	tTransferData.setNameAndValue("SerialNo",request.getParameter("SerialNo"));
	tTransferData.setNameAndValue("Operatetype",tOperate);
	tTransferData.setNameAndValue("SpecifyValiDate",request.getParameter("CvalidateConfirm"));
	loggerDebug("UWChangeCvalidateChk","tCvalidate:"+tCvalidate);
	loggerDebug("UWChangeCvalidateChk","tOperate:"+tOperate);
  tLCContSchema.setContNo(tContNo);
  tLCContSchema.setCValiDate(tCvalidate);
	
	
	boolean flag = true;


	loggerDebug("UWChangeCvalidateChk","flag:"+flag);
  	if (flag == true)
  	{
		// ׼���������� VData
	   VData tVData = new VData();        
     tVData.add(tGlobalInput);
	   tVData.add(tLCContSchema);
	   tVData.add(tTransferData);
	   //tVData.add(tLCCSpecSchema);
	  
	   ChangeCvalidateUI tChangeCvalidateUI = new ChangeCvalidateUI();
	   
		if (tChangeCvalidateUI.submitData(tVData,"") == false)
		{
		 
			int n = tChangeCvalidateUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " �޸ı�����Ч����ʧ�ܣ�ԭ����: " + tChangeCvalidateUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}else{
			tVData.add(tLCCSpecSchema);
			tVData.add( tLCCUWMasterSchema );
			//Succ �ύ��Ч�ջ�����Լ
			UWSpecInputUI tUWSpecInputUI   = new UWSpecInputUI();
		 	if (!tUWSpecInputUI.submitData(tVData,tOperate))
			  {     
		        
				int n = tUWSpecInputUI.mErrors.getErrorCount();
				Content = " �˱���Լʧ�ܣ�ԭ����: " + tUWSpecInputUI.mErrors.getError(0).errorMessage;
				FlagStr = "Fail";
			  }
//		 	�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
			if (FlagStr == "Fail")
			{
			    tError = tUWSpecInputUI.mErrors;
			    if (!tError.needDealError())
			    {                          
			    	Content = " �����˱���Լ�ɹ�! ";
			    	FlagStr = "Succ";
			    }
			    else                                                                           
			    {
			    	FlagStr = "Fail";
			    }
			}
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tChangeCvalidateUI.mErrors;
		    if (!tError.needDealError())
		    {    
		    loggerDebug("UWChangeCvalidateChk","chenggong");                   
		    	Content = " �޸ı�����Ч���ڳɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		   
		    	Content = " �޸ı�����Ч����ʧ�ܣ�ԭ����:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
		}
		
	} 
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
