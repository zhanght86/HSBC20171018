<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�EdorUWCalAddFee.jsp
//�����ܣ���ȫ�˹��˱��ӷ���Ϣ¼��
//�������ڣ�2005-08-13 11:10:36
//������  ��ZhangRong
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.utility.*"%>
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
	   String tEdorNo = request.getParameter("EdorNo");
	   String tEdorType = request.getParameter("EdorType");
	   String tRiskCode = request.getParameter("RiskCode");
	   String tRow = request.getParameter("GridRow");
	   String tDutyCode = request.getParameter("SpecGrid1");
	   String tAddFeeObject = request.getParameter("AddFeeObject");
	   String tAddFeeType = request.getParameter("AddFeeType");	  
	   String tSuppRiskScore = request.getParameter("SuppRiskScore");
	   String tSecondScore = request.getParameter("SecondScore");
	   String tAddFeeNo = request.getParameter("AddFeeNo");
	 
//		  loggerDebug("EdorUWCalAddFee","PolNo: " + tPolNo);
//		  loggerDebug("EdorUWCalAddFee","tEdorNo: " + tEdorNo);
//		  loggerDebug("EdorUWCalAddFee","tEdorType: " + tEdorType);
//		  loggerDebug("EdorUWCalAddFee","tRiskCode: " + tRiskCode);
//		  loggerDebug("EdorUWCalAddFee","tDutyCode: " + tDutyCode);
//		  loggerDebug("EdorUWCalAddFee","tAddFeeObject: " + tAddFeeObject);
//		  loggerDebug("EdorUWCalAddFee","tAddFeeType: " + tAddFeeType);
//		  loggerDebug("EdorUWCalAddFee","SuppRiskScore: " + tSuppRiskScore);
       
	   LPPolDB tLPPolDB = new LPPolDB();
	   LCPolSchema tLCPolSchema = new LCPolSchema();
	   LPDutyDB tLPDutyDB = new LPDutyDB();
	   LCDutySchema tLCDutySchema = new LCDutySchema();
	   LCPremSchema tLCPremSchema = new LCPremSchema();	    
	   LMDutyPayAddFeeSchema tLMDutyPayAddFeeSchema = new LMDutyPayAddFeeSchema();
	   TransferData mTransferData = new TransferData();
	   Reflections tReflections = new Reflections();
		
       tLPPolDB.setPolNo(tPolNo);
       tLPPolDB.setEdorNo(tEdorNo);
       tLPPolDB.setEdorType(tEdorType);
       if (!tLPPolDB.getInfo())
       {
            loggerDebug("EdorUWCalAddFee","Can not find pol inforation");
            return;
       }
       tReflections.transFields(tLCPolSchema, tLPPolDB.getSchema());
       
       tLPDutyDB.setDutyCode(tDutyCode);
       tLPDutyDB.setPolNo(tPolNo);
       tLPDutyDB.setEdorNo(tEdorNo);
       tLPDutyDB.setEdorType(tEdorType);
       if (!tLPDutyDB.getInfo())
       {
            loggerDebug("EdorUWCalAddFee","Can not find duty inforation");
            return;
       }
       tReflections.transFields(tLCDutySchema, tLPDutyDB.getSchema());
       
	   tLCPremSchema.setSuppRiskScore(tSuppRiskScore);
	   tLCPremSchema.setSecInsuAddPoint(tSecondScore);	     
	     
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
	   tVData.add(mTransferData);
	   
	   
	  
	   com.sinosoft.lis.bq.AddPremCalBL tAddPremCalBL = new AddPremCalBL();
	   tAddPremCalBL.setEdorType(tEdorType);
		if (tAddPremCalBL.submitData(tVData,"") == false)
		{
		 
			int n = tAddPremCalBL.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " ����ӷѽ��ʧ�ܣ�ԭ����: " + tAddPremCalBL.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		else
		{
			Content = " ����ӷѽ��ɹ�!";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		
		    tError = tAddPremCalBL.mErrors;
		    if (!tError.needDealError())
		    {    		                 
		  
		    	VData tResult = tAddPremCalBL.getResult();
		    	if(tResult != null)
		    	{
		    
		    		mTransferData = (TransferData)tResult.getObjectByObjectName("TransferData",0);
		    		if(mTransferData != null)
		    		{
		    		
		    		tHealthAddFeeResult = (String)mTransferData.getValueByName("mValue");
		    		loggerDebug("EdorUWCalAddFee","�ӷѽ��:"+tHealthAddFeeResult);
		    		
		    		%>
		    		<script language="javascript">
		    		    
		    		var tRow = <%=tRow%>;
		    		var sAddFee = "<%=tHealthAddFeeResult%>";
		    		var tAddFee = 1 * sAddFee;
		    		tAddFee = Math.round(tAddFee * 100) / 100;
		    		tAddFee = "" + tAddFee;
		            parent.fraInterface.SpecGrid.setRowColData(tRow,7,tAddFee);
		            
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
