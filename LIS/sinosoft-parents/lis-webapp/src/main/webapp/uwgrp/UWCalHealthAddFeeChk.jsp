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
  String tHealthAddFeeResult = "";

  GlobalInput tGlobalInput = new GlobalInput();
  tGlobalInput=(GlobalInput)session.getValue("GI");	  
  if(tGlobalInput == null) {
	out.println("session has expired");
	return;
  }
     //������Ϣ
    
	   String tPolNo = request.getParameter("PolNo"); 	 
	   String tRiskCode = request.getParameter("RiskCode");
	   String tInsuredNo = request.getParameter("InsuredNo");
	    LCPolSchema tLCPolSchema = new LCPolSchema();
	    LCPremSchema tLCPremSchema = new LCPremSchema();	    
	    LMDutyPayAddFeeSchema tLMDutyPayAddFeeSchema = new LMDutyPayAddFeeSchema();
	    TransferData mTransferData = new TransferData();
	    LCDutySchema tLCDutySchema = new LCDutySchema();
		
         tLCPolSchema.setPolNo(tPolNo);
         tLCPolSchema.setInsuredNo(tInsuredNo);
         tLCPolSchema.setRiskCode(tRiskCode);   
         loggerDebug("UWCalHealthAddFeeChk","RiskCode:"+tRiskCode);    
		
		 
    //�ӷ���Ϣ
	 
	   String tDutyCode = request.getParameter("SpecGrid1");
	   String tAddFeeObject = request.getParameter("AddFeeObject");
	   loggerDebug("UWCalHealthAddFeeChk","tAddFeeObject:"+tAddFeeObject);
	   String tAddFeeType = request.getParameter("AddFeeType");	  
	  
	   String tSuppRiskScore = request.getParameter("SuppRiskScore");
	  
	   String tSecondScore = request.getParameter("SecondScore");
	   
	   String tAddFeeNo = request.getParameter("AddFeeNo");
	   
	 	  
	      tLCPremSchema.setSuppRiskScore(tSuppRiskScore);
	      tLCPremSchema.setSecInsuAddPoint(tSecondScore);	     
	      tLCDutySchema.setDutyCode(tDutyCode);
	     
	      tLMDutyPayAddFeeSchema.setAddFeeObject(tAddFeeObject);
	      tLMDutyPayAddFeeSchema.setAddFeeType(tAddFeeType);	
	     
	      
	    
   
	
	boolean flag = true;


	loggerDebug("UWCalHealthAddFeeChk","flag:"+flag);
  	if (flag == true)
  	{
		// ׼���������� VData
	   VData tVData = new VData();        
     tVData.add(tGlobalInput);
	   tVData.add(tLCPolSchema);	 
	   tVData.add(tLCPremSchema);
	   tVData.add(tLMDutyPayAddFeeSchema);
	   tVData.add(tLCDutySchema);
	   
	   
	  
	  // AddPremCalUI tAddPremCalUI = new AddPremCalUI();
	   String busiName="cbcheckgrpAddPremCalUI";
	   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if (tBusinessDelegate.submitData(tVData,"",busiName) == false)
		{
		 
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " ����ӷѽ��ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {    		                 
		  
		    	VData tResult = tBusinessDelegate.getResult();
		    	if(tResult != null)
		    	{
		    
		    		mTransferData = (TransferData)tResult.getObjectByObjectName("TransferData",0);
		    		if(mTransferData != null)
		    		{
		    		
		    		tHealthAddFeeResult = (String)mTransferData.getValueByName("mValue");
		    		loggerDebug("UWCalHealthAddFeeChk","tHealthAddFeeResult:"+tHealthAddFeeResult);
		    		
		    		%>
		    		<script language="javascript">
		    		
		    			 var tSelNo = parent.fraInterface.SpecGrid.getSelNo()-1;
               tRow = tSelNo;
		    	
	  parent.fraInterface.SpecGrid.setRowColData(tRow,8,"<%=tHealthAddFeeResult%>");
		
		
</script>
		    	<%	
		    		 
		    		}
		    	}		    
		    	
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {		   
		    	Content = " ����ӷѽ��ʧ�ܣ�ԭ����:" + tError.getFirstError();
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
