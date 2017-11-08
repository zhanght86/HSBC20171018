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
  <%@page import="com.sinosoft.workflow.cardgrp.*"%>
  <%@page import="com.sinosoft.lis.cardgrp.*"%>
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
	String busiName1="cardgrpGrpTbWorkFlowUI";
   BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
	//GrpTbWorkFlowUI tGrpTbWorkFlowUI= new GrpTbWorkFlowUI();
	if (tBusinessDelegate1.submitData( tVData, "0000011001", busiName1) == false)
	{
		Content = " 复核失败，原因是: " + tBusinessDelegate1.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	//如果在Catch中发现异常，则不从错误类中提取错误信息
	
	if (FlagStr == "Fail")
	{
	    tError = tBusinessDelegate1.getCErrors();
	    if (!tError.needDealError())
	    {
	                                
	    	Content = " 复核成功! ";
	    	FlagStr = "Succ";
	    	
	    	//tongmeng 2008-07-23 modify
	    	//不做再保
	    	/*
	    	      //复核成功后调用再保险程序
	    	      //ReLifeNeed tReLifeNeed=new ReLifeNeed();
	    	      //数据准备
	    	      VData tmpvdata=new VData();
              TransferData tTransferData = new TransferData(); //传入页面信息	    	
              tTransferData.setNameAndValue("ManageCom",request.getParameter("ManageCom"));
              tTransferData.setNameAndValue("Operator",tG.Operator);        
              tTransferData.setNameAndValue("OtherNo",request.getParameter("PrtNo"));
              tmpvdata.add(tTransferData);
              
	    	      if (!tReLifeNeed.submitdata(tmpvdata,"1"))   //1---表示新保
	    	      {
	    	      	//再保险计算失败,UNDO复核过程,等待用户重新复核
	    	        tReLifeNeed.UNDOApp();
	    	      
	    	      }
	    	      else//再保险计算成功
	    	      {
	    	        Content = " 复核成功,再保险数据提交成功! ";
	    	        FlagStr = "Succ";
	    	      }
	    	      */
	    	
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
        
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>")
	} catch(ex) {}
</script>
</html>
