<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLUWAddFeeCalSave.jsp
//�����ܣ����˼ӷѳб��ӷѽ�����
//�������ڣ�2005-11-08 
//������  �������
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.claimnb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
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
       
       //�ӷ���Ϣ
	 
	   String tDutyCode = request.getParameter("SpecGrid1");
	   String tAddFeeObject = request.getParameter("AddFeeObject");
	   String tAddFeeType = request.getParameter("AddFeeType");	  
	   String tSuppRiskScore = request.getParameter("SuppRiskScore");
	   String tSecondScore = request.getParameter("SecondScore");
	   String tAddFeeNo = request.getParameter("AddFeeNo");
	   tLCPremSchema.setSuppRiskScore(tSuppRiskScore);
	   tLCPremSchema.setSecInsuAddPoint(tSecondScore);	
	   tLCDutySchema.setDutyCode(tDutyCode);
	   tLCDutySchema.setPolNo(tPolNo);
	   
	   tLMDutyPayAddFeeSchema.setAddFeeObject(tAddFeeObject);
	   tLMDutyPayAddFeeSchema.setAddFeeType(tAddFeeType);	

	
	   boolean flag = true;
  	   if (flag == true)
  	   {
		   // ׼���������� VData
		   VData tVData = new VData();        
	       tVData.add(tGlobalInput);
		   tVData.add(tLCPolSchema);	 
		   tVData.add(tLCPremSchema);
		   tVData.add(tLMDutyPayAddFeeSchema);
		   tVData.add(tLCDutySchema);
		   
		   
//		   AddPremCalForClaimBL tAddPremCalForClaimBL = new AddPremCalForClaimBL();
//		   
//		   if (tAddPremCalForClaimBL.submitData(tVData,"") == false)
//		   {
//			 
//				int n = tAddPremCalForClaimBL.mErrors.getErrorCount();
//				for (int i = 0; i < n; i++)
//				Content = " ����ӷѽ��ʧ�ܣ�ԭ����: " + tAddPremCalForClaimBL.mErrors.getError(0).errorMessage;
//				FlagStr = "Fail";
//		   }
		String busiName="AddPremCalForClaimBL";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if(!tBusinessDelegate.submitData(tVData,"",busiName))
		    {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
		        	int n = tBusinessDelegate.getCErrors().getErrorCount();
			        for (int i = 0; i < n; i++)
			        {
			            //loggerDebug("LLUWAddFeeCalSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
			            Content = " ����ӷѽ��ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
			        }
		       		FlagStr = "Fail";				   
				}
				else
				{
				   Content = "����ӷѽ��ʧ��";
				   FlagStr = "Fail";				
				} 
		}

		   //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		   if (FlagStr == "Fail")
		   {
			
			    //tError = tAddPremCalForClaimBL.mErrors;
			    tError = tBusinessDelegate.getCErrors();
			    if (!tError.needDealError())
			    {    		                 
			  
			    	//VData tResult = tAddPremCalForClaimBL.getResult();
			    	VData tResult = tBusinessDelegate.getResult();
			    	if(tResult != null)
			    	{
			    
			    		mTransferData = (TransferData)tResult.getObjectByObjectName("TransferData",0);
			    		if(mTransferData != null)
			    		{
			    		
			    		     tHealthAddFeeResult = (String)mTransferData.getValueByName("mValue");
			    		
			    		%>
		  <script language="javascript">
		    		
		       var tSelNo = parent.fraInterface.SpecGrid.getSelNo()-1;
               tRow = tSelNo;
		       parent.fraInterface.SpecGrid.setRowColData(tRow,8,"<%=tHealthAddFeeResult%>");
		  </script>
		    	<%	
		    		 
		    		}
		    	}		    
		    	Content = "�ӷѽ����㴦��ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else{		   
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
