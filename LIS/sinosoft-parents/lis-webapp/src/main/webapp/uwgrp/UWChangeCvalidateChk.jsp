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
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tbgrp.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
   <%@page import="com.sinosoft.service.*" %>
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

  
  LCContSchema tLCContSchema = new LCContSchema();
	
	String tContNo = request.getParameter("ContNo");  
	String tCvalidate = request.getParameter("Cvalidate");
	loggerDebug("UWChangeCvalidateChk","tCvalidate:"+tCvalidate);
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
	   String busiName="cbcheckgrpChangeCvalidateUI";
	   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	   //ChangeCvalidateUI tChangeCvalidateUI = new ChangeCvalidateUI();
	   
		if (tBusinessDelegate.submitData(tVData,"",busiName) == false)
		{
		 
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " �޸ı�����Ч����ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
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
