<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：GetSendToBankSave.jsp
//程序功能：
//创建日期：2002-11-18 11:10:36
//创建人  ：胡 博
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.bank.*"%>
  <%@page import="com.sinosoft.lis.operfee.*"%>
  <%@page import="java.text.*"%>
  <%@page import="java.util.*"%>
  <%@page import="com.sinosoft.service.*"%>
  
<%!String checkDate(String start, String end){
	if(PubFun.calInterval(start, end, "D")<0){
		return "起期应小于止期！";
	}
	if(PubFun.calInterval(end,PubFun.calDate(PubFun.getCurrentDate(),7,"D",null), "D")<0){
		if(PubFun.calInterval("2010-02-01",PubFun.getCurrentDate(),"D")>=0 && PubFun.calInterval(PubFun.getCurrentDate(),"2010-02-12","D")>=0){
			if(PubFun.calInterval(end, "2010-02-19", "D")<0){
				return "2月1日至2月12日期间操作，终止日期不能操过2月19日";
			}		
		}else{
			return "止期最多推后七天！";
		}
	}
	return null;
}
%>
<% 
  String Content = "";
  String FlagStr = "";
  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");


  //自动催收
  LCPolSchema  tLCPolSchema = new LCPolSchema();  // 个人保单表
  String startDate = request.getParameter("StartDate");
  String endDate = request.getParameter("EndDate");
  loggerDebug("GetSendToBankSave","StartDate:"+startDate);
  String  r=checkDate(startDate, endDate);
  if (r==null) {
      loggerDebug("GetSendToBankSave","StartDate:"+startDate);
  tLCPolSchema.setGetStartDate(PubFun.calDate(PubFun.getCurrentDate(),-60,"D",null)); //根据精算齐振要求
  tLCPolSchema.setPayEndDate(PubFun.getCurrentDate());

 
 //用TransferData来向后台传送数据  zhanghui 2005.2.18
 TransferData transferData2 = new TransferData();
 String bankCode=request.getParameter("BankCode");
 transferData2.setNameAndValue("bankCode", bankCode);
 
 
//tongmeng 2011-01-24 add
//新契约直接从暂收获取数据,不处理应收
/*
  VData tVData2 = new VData();
  tVData2.add(tLCPolSchema);
  tVData2.add(tGlobalInput);
  tVData2.add(transferData2); //by zhanghui
  //NewIndiDueFeeMultiUI tNewIndiDueFeeMultiUI = new NewIndiDueFeeMultiUI();
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	String key = "SendS" + bankCode;
	PubLock tPubLock = new PubLock();
	if (!tPubLock.lock(key, "准备" + bankCode + "代收发盘首期数据")) {
		 Content=tPubLock.mErrors.getErrContent();
	}else{
		try{
		 // tNewIndiDueFeeMultiUI.submitData(tVData2, "INSERT");
		  tBusinessDelegate.submitData(tVData2,"INSERT","NewIndiDueFeeMultiUI");
		  //if (tNewIndiDueFeeMultiUI.mErrors.needDealError()) {
		   if (tBusinessDelegate.getCErrors().needDealError()) {
		    Content="催收处理失败，不能生成银行发送数据，原因是:";
		    
		    //for(int n=0;n<tNewIndiDueFeeMultiUI.mErrors.getErrorCount();n++) {       
		    // Content=Content+tNewIndiDueFeeMultiUI.mErrors.getError(n).errorMessage;
		    for(int n=0;n<tBusinessDelegate.getCErrors().getErrorCount();n++) {       
		     Content=Content+tBusinessDelegate.getCErrors().getError(n).errorMessage;
		     Content=Content+"|";
		     loggerDebug("GetSendToBankSave",Content);
		    }
		    FlagStr="Fail";
		  }
		  else { 
		    loggerDebug("GetSendToBankSave","催收处理成功！");
		  }
		}finally{
			if(!tPubLock.unLock(key))
				loggerDebug("GetSendToBankSave",bankCode + "代收发盘首期数据解锁失败");
		}
	}
  */
  //生成银行数据
  loggerDebug("GetSendToBankSave","\n\n---GetSendToBankSave Start---");
  //GetSendToBankUI getSendToBankUI1 = new GetSendToBankUI(); 
  BusinessDelegate tBusinessDelegate2=BusinessDelegate.getBusinessDelegate();

  TransferData transferData1 = new TransferData();
  transferData1.setNameAndValue("startDate", request.getParameter("StartDate"));
  transferData1.setNameAndValue("endDate", request.getParameter("EndDate"));
  transferData1.setNameAndValue("bankCode", bankCode);
  transferData1.setNameAndValue("typeFlag", request.getParameter("typeFlag"));

  VData tVData = new VData();
  tVData.add(transferData1);
  tVData.add(tGlobalInput);

  //if (!getSendToBankUI1.submitData(tVData, "GETMONEY")) {
  if (!tBusinessDelegate2.submitData(tVData,"GETMONEY","GetSendToBankUI")) {
    //VData rVData = getSendToBankUI1.getResult();
    VData rVData = tBusinessDelegate2.getResult(); 
    Content = " 处理失败，原因是:" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
    Content = "送银行数据处理成功! ";
  	FlagStr = "Succ";
  }  

	loggerDebug("GetSendToBankSave",Content + "\n" + FlagStr + "\n---GetSendToBankSave End---\n\n");
  }else{
	  FlagStr = "Fail";
	  Content = " 处理失败，原因是:" + r;
  }
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
</script>
</html>
