<%
//程序名称：LDExRateSave.jsp
//程序功能：
//创建日期：2009-10-13 15:27:43
//创建人  ：ZhanPeng程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
    <%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  //接收信息，并作校验处理。
  //输入参数
  LDExRateSchema tLDExRateSchema   = new LDExRateSchema();
	LBExRateSchema tLBExRateSchema   = new LBExRateSchema();

  //输出参数
  CErrors tError = null;
  String tOperate=request.getParameter("hideOperate");
  tOperate=tOperate.trim();
  String tRela  = "";                
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
	if(tOperate.equals("UPDATE||MAIN")||tOperate.equals("INSERT||MAIN"))
	{
		tLDExRateSchema.setCurrCode(request.getParameter("CurrCode"));
	  tLDExRateSchema.setPer(request.getParameter("Per"));
	  tLDExRateSchema.setDestCode(request.getParameter("DestCode"));
	  tLDExRateSchema.setExchBid(request.getParameter("ExchBid"));
	  tLDExRateSchema.setExchOffer(request.getParameter("ExchOffer"));
	  tLDExRateSchema.setCashBid(request.getParameter("CashBid"));
	  tLDExRateSchema.setCashOffer(request.getParameter("CashOffer"));
	  tLDExRateSchema.setMiddle(request.getParameter("Middle"));
	  tLDExRateSchema.setMakeDate(request.getParameter("MakeDate"));
	  tLDExRateSchema.setMakeTime(request.getParameter("MakeTime"));
	}else if(tOperate.equals("DELETE||MAIN"))
		{
			tLBExRateSchema.setCurrCode(request.getParameter("CurrCode"));
		  tLBExRateSchema.setPer(request.getParameter("Per"));
		  tLBExRateSchema.setDestCode(request.getParameter("DestCode"));
		  tLBExRateSchema.setExchBid(request.getParameter("ExchBid"));
		  tLBExRateSchema.setExchOffer(request.getParameter("ExchOffer"));
		  tLBExRateSchema.setCashBid(request.getParameter("CashBid"));
		  tLBExRateSchema.setCashOffer(request.getParameter("CashOffer"));
		  tLBExRateSchema.setMiddle(request.getParameter("Middle"));
		  tLBExRateSchema.setStartDate(request.getParameter("MakeDate"));
		  tLBExRateSchema.setStartTime(request.getParameter("MakeTime"));
			tLBExRateSchema.setEndDate(request.getParameter("EndDate"));
		  tLBExRateSchema.setEndTime(request.getParameter("EndTime"));
		}
  

  // 准备传输数据 VData
  VData tVData = new VData();
  FlagStr="";
  if(tOperate.equals("UPDATE||MAIN")||tOperate.equals("INSERT||MAIN"))
  {
  	tVData.addElement(tLDExRateSchema);
  }else if(tOperate.equals("DELETE||MAIN"))
  {
  	tVData.addElement(tLBExRateSchema);
  }
	
	tVData.add(tG);
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  try
  {
		 if(!tBusinessDelegate.submitData(tVData, tOperate, "LDExRateUI")){
			 FlagStr = "Fail";
			 Content = "保存失败,原因是:"+tBusinessDelegate.getCErrors().getFirstError();
		}	
  }
  catch(Exception ex)
  {
    Content = "保存失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }
  
  if (!FlagStr.equals("Fail"))
  {
		Content = "保存成功";

    	FlagStr = "Succ";
  }
  
  //添加各种预处理

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

