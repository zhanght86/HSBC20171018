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
  String tHealthAddFeeResult = "";

  GlobalInput tGlobalInput = new GlobalInput();
  tGlobalInput=(GlobalInput)session.getValue("GI");	  
  if(tGlobalInput == null) {
	out.println("session has expired");
	return;
  }
  
  /*
    fm.action= "./UWCalHealthAddFeeChk.jsp?AddfeePolNo="+tPolNo+"&AddFeeDutyCode="tDutyCode
             +"&AddFeeType="+tAddFeeType+"&AddFeeMethod="+AddFeeMethod;
  */
     //������Ϣ
    
	   String tPolNo = request.getParameter("AddFeePolNo"); 	 
	   String tRiskCode = request.getParameter("AddFeeRiskCode");
	   String tInsuredNo = request.getParameter("AddFeeInsuredNo");
	   String tDutyCode = request.getParameter("AddFeeDutyCode");
	   String tAddFeeType = request.getParameter("AddFeeType");
	   String tAddFeeMethod = request.getParameter("AddFeeMethod");
	   String tAddFeePoint = request.getParameter("AddFeePoint");
	   String tBatNo = request.getParameter("BatNo");
	   String tClmNo = request.getParameter("ClmNo");
	   	LCPolSchema tLCPolSchema = new LCPolSchema();
	   	LLUWPremMasterSchema tLLUWPremMasterSchema = new LLUWPremMasterSchema();	    
	    LMDutyPayAddFeeSchema tLMDutyPayAddFeeSchema = new LMDutyPayAddFeeSchema();
	    TransferData mTransferData = new TransferData();
	    LCDutySchema tLCDutySchema = new LCDutySchema();
	    mTransferData.setNameAndValue("BatNo",tBatNo);
	    mTransferData.setNameAndValue("ClmNo",tClmNo);
	    
         tLCPolSchema.setPolNo(tPolNo);
         tLCPolSchema.setInsuredNo(tInsuredNo);
         tLCPolSchema.setRiskCode(tRiskCode);   
         loggerDebug("LLUWCalHealthAddFeeChk","RiskCode:"+tRiskCode);    
		
		 
    	 //�ӷ���Ϣ
	 	  
	      tLLUWPremMasterSchema.setSuppRiskScore(tAddFeePoint);
	      tLLUWPremMasterSchema.setDutyCode(tDutyCode);
	      
	      tLMDutyPayAddFeeSchema.setAddFeeObject(tAddFeeMethod);
	      tLMDutyPayAddFeeSchema.setAddFeeType(tAddFeeType);	
	      tLMDutyPayAddFeeSchema.setRiskCode(tRiskCode);
	      tLMDutyPayAddFeeSchema.setDutyCode(tDutyCode);
	      tLCDutySchema.setDutyCode(tDutyCode);
	      
	    
   
	
	boolean flag = true;


	loggerDebug("LLUWCalHealthAddFeeChk","flag:"+flag);
  	if (flag == true)
  	{
		// ׼���������� VData
	   VData tVData = new VData();        
       tVData.add(tGlobalInput);
	   tVData.add(tLCPolSchema);	 
	   tVData.add(tLLUWPremMasterSchema);
	   tVData.add(tLMDutyPayAddFeeSchema);
	   tVData.add(tLCDutySchema);
	   tVData.add(mTransferData);
	   
	   
	  
	   LLAddPremCalUI tLLAddPremCalUI = new LLAddPremCalUI();
	   
		if (tLLAddPremCalUI.submitData(tVData,"") == false)
		{
		 
			int n = tLLAddPremCalUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " ����ӷѽ��ʧ�ܣ�ԭ����: " + tLLAddPremCalUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		
		    tError = tLLAddPremCalUI.mErrors;
		    if (!tError.needDealError())
		    {    		                 
		  
		    	VData tResult = tLLAddPremCalUI.getResult();
		    	if(tResult != null)
		    	{
		    
		    		mTransferData = (TransferData)tResult.getObjectByObjectName("TransferData",0);
		    		if(mTransferData != null)
		    		{
		    		
		    		tHealthAddFeeResult = (String)mTransferData.getValueByName("mValue");
		    		loggerDebug("LLUWCalHealthAddFeeChk","tHealthAddFeeResult:"+tHealthAddFeeResult);
		    		
		    		%>
		    		<script language="javascript">
		    		
		    			 var tSelNo = parent.fraInterface.PolAddGrid.lastFocusRowNo;
              			 tRow = tSelNo;
		    	
	  parent.fraInterface.PolAddGrid.setRowColData(tRow,7,"<%=tHealthAddFeeResult%>");
		
		
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
	parent.fraInterface.afterSubmit1("<%=FlagStr%>","<%=Content%>");
	
</script>
</html>
