<%
//程序名称：LDCurrencySave.jsp
//程序功能：
//创建日期：2009-10-12 19:29:43
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
  LDCurrencySchema tLDCurrencySchema   = new LDCurrencySchema();


  //输出参数
  CErrors tError = null;
  String tOperate=request.getParameter("hideOperate");
  tOperate=tOperate.trim();
  String tRela  = "";                
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");

  tLDCurrencySchema.setCurrCode(request.getParameter("CurrCode"));
  tLDCurrencySchema.setCurrName(request.getParameter("CurrName"));
  tLDCurrencySchema.setValidation(request.getParameter("Validation"));
  tLDCurrencySchema.setCodeAlias(request.getParameter("CodeAlias"));


  // 准备传输数据 VData
  VData tVData = new VData();
  FlagStr="";
	tVData.addElement(tLDCurrencySchema);
	tVData.add(tG);
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  try
  {
		 if(!tBusinessDelegate.submitData(tVData, tOperate, "LDCurrencyUI")){
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

