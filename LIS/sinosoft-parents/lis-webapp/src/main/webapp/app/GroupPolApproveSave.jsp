<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：GroupPolApproveSave.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：HST
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.workflow.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");
	            
	TransferData mTransferData = new TransferData();
  mTransferData.setNameAndValue("GrpContNo",request.getParameter("ProposalGrpContNo"));
  mTransferData.setNameAndValue("PrtNo",request.getParameter("PrtNo"));
  mTransferData.setNameAndValue("SaleChnl",request.getParameter("SaleChnl"));
  mTransferData.setNameAndValue("ManageCom",request.getParameter("ManageCom"));
  mTransferData.setNameAndValue("GrpName",request.getParameter("GrpName"));
  mTransferData.setNameAndValue("CValiDate",request.getParameter("CValiDate"));
  mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
  mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
  //mTransferData.setNameAndValue("Operator",tG.Operator); //add by yaory
  //mTransferData.setNameAndValue("MakeDate",PubFun.getCurrentDate()); //add by yaory
  //mTransferData.setNameAndValue("AgentCode",request.getParameter("AgentCode")); //add by yaory
	
	loggerDebug("GroupPolApproveSave","GrpContNo   =="+request.getParameter("ProposalGrpContNo"));
	loggerDebug("GroupPolApproveSave","PrtNo       =="+request.getParameter("PrtNo"));
	loggerDebug("GroupPolApproveSave","SaleChnl    =="+request.getParameter("SaleChnl"));
	loggerDebug("GroupPolApproveSave","ManageCom   =="+request.getParameter("ManageCom"));
	loggerDebug("GroupPolApproveSave","GrpName     =="+request.getParameter("GrpName"));
	loggerDebug("GroupPolApproveSave","CValiDate   =="+request.getParameter("CValiDate"));
	loggerDebug("GroupPolApproveSave","MissionID   =="+request.getParameter("MissionID"));
	loggerDebug("GroupPolApproveSave","SubMissionID=="+request.getParameter("SubMissionID"));
  	//接收信息
	LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
    String tProposalGrpContNo = request.getParameter("ProposalGrpContNo");
    String flag=request.getParameter("Flag1");
     
	if( tProposalGrpContNo!= null&flag!=null )
	{    
	 loggerDebug("GroupPolApproveSave","jsp中GrpPolNo:"+tProposalGrpContNo);

	// 准备传输数据 VData
	VData tVData = new VData();
	tVData.add( mTransferData );
	tVData.add( tG );
	
	// 数据传输
	//GrpTbWorkFlowUI tGrpTbWorkFlowUI= new GrpTbWorkFlowUI();
	String busiName="tbgrpGrpTbWorkFlowUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if (tBusinessDelegate.submitData( tVData, "0000002001" ,busiName) == false)
	{
		Content = " 复核失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	//如果在Catch中发现异常，则不从错误类中提取错误信息
	
	if (FlagStr == "Fail")
	{
	    tError = tBusinessDelegate.getCErrors();
	    if (!tError.needDealError())
	    {                          
	    	Content = " 复核成功! ";
	    	FlagStr = "Succ";
	    }
	    else                                                                           
	    {
	    	Content = " 复核失败，原因是:" + tError.getFirstError();
	    	FlagStr = "Fail";
	    }
	}
	}
%>                      
<html>
<script language="javascript">
try {
        
	parent.fraInterface.afterSubmit1("<%=FlagStr%>","<%=Content%>")
	} catch(ex) {}
</script>
</html>
