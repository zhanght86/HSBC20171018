<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：EdorUWCalAddFee.jsp
//程序功能：保全人工核保加费信息录入
//创建日期：2005-08-13 11:10:36
//创建人  ：ZhangRong
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.utility.*"%>
<%
  //输出参数
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
     //险种信息
    
        
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
		// 准备传输数据 VData
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
			Content = " 计算加费金额失败，原因是: " + tAddPremCalBL.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		else
		{
			Content = " 计算加费金额成功!";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
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
		    		loggerDebug("EdorUWCalAddFee","加费金额:"+tHealthAddFeeResult);
		    		
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
		    	Content = " 计算加费金额失败，原因是:" + tError.getFirstError();
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
