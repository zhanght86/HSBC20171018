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
<%
  //输出参数
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
		// 准备传输数据 VData
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
			Content = " 修改保单生效日期失败，原因是: " + tChangeCvalidateUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}else{
			tVData.add(tLCCSpecSchema);
			tVData.add( tLCCUWMasterSchema );
			//Succ 提交生效日回溯特约
			UWSpecInputUI tUWSpecInputUI   = new UWSpecInputUI();
		 	if (!tUWSpecInputUI.submitData(tVData,tOperate))
			  {     
		        
				int n = tUWSpecInputUI.mErrors.getErrorCount();
				Content = " 核保特约失败，原因是: " + tUWSpecInputUI.mErrors.getError(0).errorMessage;
				FlagStr = "Fail";
			  }
//		 	如果在Catch中发现异常，则不从错误类中提取错误信息
			if (FlagStr == "Fail")
			{
			    tError = tUWSpecInputUI.mErrors;
			    if (!tError.needDealError())
			    {                          
			    	Content = " 续保核保特约成功! ";
			    	FlagStr = "Succ";
			    }
			    else                                                                           
			    {
			    	FlagStr = "Fail";
			    }
			}
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tChangeCvalidateUI.mErrors;
		    if (!tError.needDealError())
		    {    
		    loggerDebug("UWChangeCvalidateChk","chenggong");                   
		    	Content = " 修改保单生效日期成功! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		   
		    	Content = " 修改保单生效日期失败，原因是:" + tError.getFirstError();
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
