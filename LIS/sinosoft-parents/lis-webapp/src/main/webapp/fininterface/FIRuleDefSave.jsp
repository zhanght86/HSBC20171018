<%
//程序名称 :FIRuleDefSave.jsp
//程序功能 :校验计划定义
//创建人 :范昕
//创建日期 :2008-09-17
//
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.fininterface.checkdata.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  loggerDebug("FIRuleDefSave","1开始执行Save页面");
  FIRuleDefSchema mFIRuleDefSchema = new FIRuleDefSchema();
  //FIRuleDefUI mFIRuleDefUI = new FIRuleDefUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");              //获得用户信息
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  mOperateType = mOperateType.trim();
  loggerDebug("FIRuleDefSave","2操作的类型是" +  mOperateType);
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mType = "";
  String tRulesID = "";

  loggerDebug("FIRuleDefSave","3开始进行获取数据的操作！！！");
  if(mOperateType.equals("INSERT||MAIN"))
  {
  	tRulesID = PubFun1.CreateMaxNo("RulesPlanID",8);
 	 	loggerDebug("FIRuleDefSave","流水号作业：校验规则编号" + tRulesID);
  	mFIRuleDefSchema.setVersionNo(request.getParameter("VersionNo"));
  	mFIRuleDefSchema.setRuleID(tRulesID);
  	mFIRuleDefSchema.setRuleName(request.getParameter("RuleName"));
  	mFIRuleDefSchema.setRuleDealMode(request.getParameter("RuleDealMode"));
  	mFIRuleDefSchema.setRuleDealClass(request.getParameter("RuleDealClass"));
  	mFIRuleDefSchema.setRuleDealSQL(request.getParameter("RuleDealSQL"));
  	mFIRuleDefSchema.setRuleReturnMean(request.getParameter("RuleReturnMean")); 
  	VData tVData = new VData();
  	try
  	{
  		tVData.add(tG);
  		tVData.addElement(mOperateType);
    	tVData.addElement(mFIRuleDefSchema);
    	uiBusinessDelegate.submitData(tVData,mOperateType,"FIRuleDefUI");
  	}
  	catch(Exception ex)
  	{
    	Content = mType+"失败，原因是:" + ex.toString();
    	FlagStr = "Fail";
  	}
  }
  if(mOperateType.equals("UPDATE||MAIN"))
  {
  	mFIRuleDefSchema.setVersionNo(request.getParameter("VersionNo"));
  	mFIRuleDefSchema.setRuleID(request.getParameter("RuleID"));
  	mFIRuleDefSchema.setRuleName(request.getParameter("RuleName"));
  	mFIRuleDefSchema.setRuleDealMode(request.getParameter("RuleDealMode"));
  	mFIRuleDefSchema.setRuleDealClass(request.getParameter("RuleDealClass"));
  	mFIRuleDefSchema.setRuleDealSQL(request.getParameter("RuleDealSQL"));
  	mFIRuleDefSchema.setRuleReturnMean(request.getParameter("RuleReturnMean")); 
  	VData tVData = new VData();
  	try
  	{
  		tVData.add(tG);
  		tVData.addElement(mOperateType);
    	tVData.addElement(mFIRuleDefSchema);
    	uiBusinessDelegate.submitData(tVData,mOperateType,"FIRuleDefUI");
  	}
  	catch(Exception ex)
  	{
    	Content = mType+"失败，原因是:" + ex.toString();
    	FlagStr = "Fail";
  	}
  }
  
  if(mOperateType.equals("DELETE||MAIN"))
  {
  	mFIRuleDefSchema.setVersionNo(request.getParameter("VersionNo"));
  	mFIRuleDefSchema.setRuleID(request.getParameter("RuleID"));
  	mFIRuleDefSchema.setRuleName(request.getParameter("RuleName"));
  	mFIRuleDefSchema.setRuleDealMode(request.getParameter("RuleDealMode"));
  	mFIRuleDefSchema.setRuleDealClass(request.getParameter("RuleDealClass"));
  	mFIRuleDefSchema.setRuleDealSQL(request.getParameter("RuleDealSQL"));
  	mFIRuleDefSchema.setRuleReturnMean(request.getParameter("RuleReturnMean")); 
  	VData tVData = new VData();
  	try
  	{
  		tVData.add(tG);
  		tVData.addElement(mOperateType);
    	tVData.addElement(mFIRuleDefSchema);
    	uiBusinessDelegate.submitData(tVData,mOperateType,"FIRuleDefUI");
  	}
  	catch(Exception ex)
  	{
    	Content = mType+"失败，原因是:" + ex.toString();
    	FlagStr = "Fail";
  	}
  }

  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
     tError = uiBusinessDelegate.getCErrors();
    	   if (!tError.needDealError())
    		{     
    				Content = "操作已成功!";
    				FlagStr = "Succ";
    		}
   	  else
    		{
    			Content = " 操作失败，原因是:" + tError.getFirstError();
    			FlagStr = "Fail";
    		}
  }
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
