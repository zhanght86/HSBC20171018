<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：PRnewUWManuHealthChk.jsp
//程序功能：承保人工核保体检资料录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
  <%@page import="com.sinosoft.service.*" %>
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
  
  /*
    fm.action= "./UWCalHealthAddFeeChk.jsp?AddfeePolNo="+tPolNo+"&AddFeeDutyCode="tDutyCode
             +"&AddFeeType="+tAddFeeType+"&AddFeeMethod="+AddFeeMethod;
  */
     //险种信息
    
	   String tPolNo = request.getParameter("AddFeePolNo"); 	 
	   String tRiskCode = request.getParameter("AddFeeRiskCode");
	   String tInsuredNo = request.getParameter("AddFeeInsuredNo");
	   String tDutyCode = request.getParameter("AddFeeDutyCode");
	   String tAddFeeType = request.getParameter("AddFeeType");
	   String tAddFeeMethod = request.getParameter("AddFeeMethod");
	   String tAddFeePoint = request.getParameter("AddFeePoint");
	   	LCPolSchema tLCPolSchema = new LCPolSchema();
	    LCPremSchema tLCPremSchema = new LCPremSchema();	    
	    LMDutyPayAddFeeSchema tLMDutyPayAddFeeSchema = new LMDutyPayAddFeeSchema();
	    TransferData mTransferData = new TransferData();
	    LCDutySchema tLCDutySchema = new LCDutySchema();
		
         tLCPolSchema.setPolNo(tPolNo);
         tLCPolSchema.setInsuredNo(tInsuredNo);
         tLCPolSchema.setRiskCode(tRiskCode);   
         loggerDebug("UWCalHealthAddFeeChk","RiskCode:"+tRiskCode);    
		
		 
    	 //加费信息
	 	  
	      tLCPremSchema.setSuppRiskScore(tAddFeePoint);
	      tLCDutySchema.setDutyCode(tDutyCode);
	      
	      tLMDutyPayAddFeeSchema.setAddFeeObject(tAddFeeMethod);
	      tLMDutyPayAddFeeSchema.setAddFeeType(tAddFeeType);	
	      tLMDutyPayAddFeeSchema.setRiskCode(tRiskCode);
	      tLMDutyPayAddFeeSchema.setDutyCode(tDutyCode);
	      
	    
   
	
	boolean flag = true;


	loggerDebug("UWCalHealthAddFeeChk","flag:"+flag);
  	if (flag == true)
  	{
		// 准备传输数据 VData
	   VData tVData = new VData();        
       tVData.add(tGlobalInput);
	   tVData.add(tLCPolSchema);	 
	   tVData.add(tLCPremSchema);
	   tVData.add(tLMDutyPayAddFeeSchema);
	   tVData.add(tLCDutySchema);
	   
	   
	  
	   //AddPremCalUI tAddPremCalUI = new AddPremCalUI();
	   String busiName="cbcheckAddPremCalUI";
       BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	   
		if (tBusinessDelegate.submitData(tVData,"",busiName) == false)
		{
		 
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " 计算加费金额失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
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
		    		
		    			 var tSelNo = parent.fraInterface.PolAddGrid.lastFocusRowNo;
              			 tRow = tSelNo;
		    			if('<%=tHealthAddFeeResult%>'<0)
		    			{
		    				alert('加费金额计算为负数!');
		    				parent.fraInterface.PolAddGrid.setRowColData(tRow,7,'');
		    				//return false;
		    			}
		    			else
		    				{
	 				 parent.fraInterface.PolAddGrid.setRowColData(tRow,7,"<%=tHealthAddFeeResult%>");
							}
		
		
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
	parent.fraInterface.afterSubmit1("<%=FlagStr%>","<%=Content%>");
	
</script>
</html>
