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
	   String tEdorNo = request.getParameter("EdorNo");
	   String tContNo = request.getParameter("ContNo");
	   String tEdorAcceptNo = request.getParameter("EdorAcceptNo");
	   String tEdorType = request.getParameter("EdorType");
	   
	   //String tSql="";
	   
	   	LPPolSchema tLPPolSchema = new LPPolSchema();
	    LPPremSchema tLPPremSchema = new LPPremSchema();	    
	    LMDutyPayAddFeeSchema tLMDutyPayAddFeeSchema = new LMDutyPayAddFeeSchema();
	    TransferData mTransferData = new TransferData();
	    LPDutySchema tLPDutySchema = new LPDutySchema();
		
         tLPPolSchema.setPolNo(tPolNo);
         tLPPolSchema.setContNo(tContNo);
         tLPPolSchema.setEdorNo(tEdorNo);
         tLPPolSchema.setEdorType(tEdorType);
         tLPPolSchema.setInsuredNo(tInsuredNo);
         tLPPolSchema.setRiskCode(tRiskCode);
         loggerDebug("BQCalHealthAddFeeChk","RiskCode:"+tRiskCode);    
		
		 
    	 //�ӷ���Ϣ
	 	  
	      tLPPremSchema.setSuppRiskScore(tAddFeePoint);
	      tLPPremSchema.setEdorNo(tEdorNo);
	      tLPPremSchema.setEdorType(tEdorType);
	      
	      tLPDutySchema.setDutyCode(tDutyCode);
	      tLPDutySchema.setEdorType(tEdorType);
	      tLPDutySchema.setEdorNo(tEdorNo);
	      
	      tLMDutyPayAddFeeSchema.setAddFeeObject(tAddFeeMethod);
	      tLMDutyPayAddFeeSchema.setAddFeeType(tAddFeeType);	
	      tLMDutyPayAddFeeSchema.setRiskCode(tRiskCode);
	      tLMDutyPayAddFeeSchema.setDutyCode(tDutyCode);
	      
	    
   
	
	boolean flag = true;


	loggerDebug("BQCalHealthAddFeeChk","flag:"+flag);
  	if (flag == true)
  	{
		// ׼���������� VData
	   VData tVData = new VData(); 
	   TransferData tTransferData = new TransferData();
	   tTransferData.setNameAndValue("UWFlag","UW");
       tVData.add(tGlobalInput);
	   tVData.add(tLPPolSchema);	 
	   tVData.add(tLPPremSchema);
	   tVData.add(tLMDutyPayAddFeeSchema);
	   tVData.add(tLPDutySchema);
	   tVData.add(tTransferData);	   
	  
	   BQAddPremCalUI tBQAddPremCalUI = new BQAddPremCalUI();
	   
		if (tBQAddPremCalUI.submitData(tVData,"") == false)
		{
		 
			int n = tBQAddPremCalUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " ����ӷѽ��ʧ�ܣ�ԭ����: " + tBQAddPremCalUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		
		    tError = tBQAddPremCalUI.mErrors;
		    if (!tError.needDealError())
		    {    		                 
		  
		    	VData tResult = tBQAddPremCalUI.getResult();
		    	if(tResult != null)
		    	{
		    
		    		mTransferData = (TransferData)tResult.getObjectByObjectName("TransferData",0);
		    		if(mTransferData != null)
		    		{
		    		
		    		tHealthAddFeeResult = (String)mTransferData.getValueByName("mValue");
		    		loggerDebug("BQCalHealthAddFeeChk","tHealthAddFeeResult:"+tHealthAddFeeResult);
		    		
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
