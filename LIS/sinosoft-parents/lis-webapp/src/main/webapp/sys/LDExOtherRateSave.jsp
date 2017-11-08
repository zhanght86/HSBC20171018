<%
//程序名称：LDExOtherRateSave.jsp
//程序功能：
//创建日期：2009-10-13 9:42:43
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
  LDExOtherRateSchema tLDExOtherRateSchema   = new LDExOtherRateSchema();


  //输出参数
  CErrors tError = null;
  String tOperate=request.getParameter("hideOperate");
  tOperate=tOperate.trim();
  String tRela  = "";                
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");

  tLDExOtherRateSchema.setCurrCode(request.getParameter("CurrCode"));
  tLDExOtherRateSchema.setPer(request.getParameter("Per"));
  tLDExOtherRateSchema.setDestCurrCode(request.getParameter("DestCurrCode"));
  tLDExOtherRateSchema.setExchRate(request.getParameter("ExchRate"));
  tLDExOtherRateSchema.setStartDate(request.getParameter("StartDate"));
  tLDExOtherRateSchema.setEndDate(request.getParameter("EndDate"));


  // 准备传输数据 VData
  VData tVData = new VData();
  FlagStr="";
	tVData.addElement(tLDExOtherRateSchema);
	tVData.add(tG);
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  try
  {
	 if(!tBusinessDelegate.submitData(tVData, tOperate, "LDExOtherRateUI")){
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
    	Content = " 保存成功! ";
    	FlagStr = "Succ";
  }
  
  //添加各种预处理

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

