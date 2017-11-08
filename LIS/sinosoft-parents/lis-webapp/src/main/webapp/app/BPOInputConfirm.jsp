<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
<%
    //输出参数
    CErrors tError = null;
    String tRela  = "";                
    String FlagStr="";
    String Content = "";
    String tAction = "";
    String tOperate = "";
    String wFlag = "";
    loggerDebug("BPOInputConfirm","Start BPOInputConfirm...");
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
 
    VData tVData = new VData();
    PubConcurrencyLock mPubLock = new PubConcurrencyLock();
    String PrtNo = request.getParameter("PrtNo");
    try{
	     //加入并发控制，同一个清洁件在同一时间只能处理一次	     
	     if (!mPubLock.lock(PrtNo, "LC0031")) {
	    	 FlagStr="Fail";
	 	  	if(mPubLock.mErrors.needDealError())
	 	  	{
	 	  		Content="投保单（印刷号："+PrtNo+")正在处理中，请稍后再试！";
	 	  	}
	 	  	loggerDebug("BPOInputConfirm","FailFailFailFailFailFail" );
		 }
	     else{
	    	    ExeSQL tExe = new ExeSQL();
		     	String tExist=tExe.getOneValue("select 1 from lccont where prtno='"+PrtNo+"' ");
		     	loggerDebug("BPOInputConfirm","tExist:"+tExist);
		     	if(tExist==null || tExist.equals(""))
		     	{		     		
			 	   Content = "还未保存成功,不容许您进行 [处理完毕] 确认！";
			 	   FlagStr="Fail";
		     	}
		     	else
		     	{
		     		ExeSQL tExeSQL = new ExeSQL();
		    	    String tSQL = "select State from BPOMissionState where bussno='"+PrtNo+"' and bussnotype='"+request.getParameter("BussNoType")+"' and DealType='"+request.getParameter("DealType")+"'";
		    	    String tState = StrTool.cTrim(tExeSQL.getOneValue(tSQL));
		    	    if("".equals(tState))
		    	    {
		    	      Content = " 处理完毕确认失败，原因是: BPOMissionState表记录查询失败";
		    	      FlagStr = "Fail";
		    	    }
		    	   else
		    	  	{
		    	      //工作流操作型别，根据此值检索活动ID，取出服务类执行具体业务逻辑
		    	      wFlag = request.getParameter("WorkFlowFlag");
		    	      TransferData mTransferData = new TransferData();
		    	      mTransferData.setNameAndValue("ContNo", request.getParameter("PrtNo"));
		    	      mTransferData.setNameAndValue("PrtNo", request.getParameter("PrtNo"));
		    	      mTransferData.setNameAndValue("AppntNo", request.getParameter("AppntNo"));
		    	      mTransferData.setNameAndValue("AppntName",request.getParameter("AppntName"));
		    	      mTransferData.setNameAndValue("AgentCode",request.getParameter("AgentCode"));
		    	      mTransferData.setNameAndValue("ManageCom", request.getParameter("ManageCom"));
		    	      mTransferData.setNameAndValue("Operator",tG.Operator);
		    	      mTransferData.setNameAndValue("MakeDate",PubFun.getCurrentDate());
		    	      mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));  
		    	      mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));   
		    	      mTransferData.setNameAndValue("DealType", request.getParameter("DealType"));
		    	      mTransferData.setNameAndValue("BussNoType",request.getParameter("BussNoType"));
		    	      mTransferData.setNameAndValue("State", tState);
		    	      mTransferData.setNameAndValue("SpecType",StrTool.cTrim(request.getParameter("SpecType")));
		    	      
		    	      loggerDebug("BPOInputConfirm","ContNo="+request.getParameter("ProposalContNo"));
		    	      loggerDebug("BPOInputConfirm","PrtNo="+request.getParameter("PrtNo"));
		    	      loggerDebug("BPOInputConfirm","AppntNo="+request.getParameter("AppntNo"));
		    	      loggerDebug("BPOInputConfirm","AppntName="+request.getParameter("AppntName"));
		    	      loggerDebug("BPOInputConfirm","AgentCode="+request.getParameter("AgentCode"));
		    	      loggerDebug("BPOInputConfirm","ManageCom="+request.getParameter("ManageCom"));
		    	      loggerDebug("BPOInputConfirm","Operator="+tG.Operator);
		    	      loggerDebug("BPOInputConfirm","MakeDate="+PubFun.getCurrentDate());
		    	      loggerDebug("BPOInputConfirm","MissionID="+request.getParameter("MissionID"));
		    	      loggerDebug("BPOInputConfirm","SubMissionID="+request.getParameter("SubMissionID"));
		    	      loggerDebug("BPOInputConfirm","DealType="+request.getParameter("DealType"));
		    	      loggerDebug("BPOInputConfirm","BussNoType="+request.getParameter("BussNoType"));
		    	      loggerDebug("BPOInputConfirm","State="+tState);
		    	      loggerDebug("BPOInputConfirm","SpecType="+StrTool.cTrim(request.getParameter("SpecType")));
		    	     
		    	      tVData.add(mTransferData);
		    	      tVData.add(tG);
		    	      loggerDebug("BPOInputConfirm","wFlag="+wFlag);
		    	      loggerDebug("BPOInputConfirm","-------------------start workflow---------------------");
		    	      TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
		    	      if( !tTbWorkFlowUI.submitData( tVData, wFlag ) ) {
		    	          Content = " 处理完毕确认失败，原因是: " + tTbWorkFlowUI.mErrors.getError(0).errorMessage;
		    	          FlagStr = "Fail";
		    	      }else {
		    	          Content =" 处理成功！";
		    	          FlagStr = "Succ";
		    	      }
		    	  }
		    	 loggerDebug("BPOInputConfirm","-------------------end workflow---------------------");
		     }
	    	 	
	     }  
    }
 	catch(Exception e)
	{ 		
 	   Content = "处理时发生异常:e.toString()";
 	   FlagStr="Fail";
	}
	finally
	{
		mPubLock.unLock();
	}
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmitConfirm("<%=FlagStr%>","<%=Content%>");
</script>
</html>
