<%
//程序名称 :FIRulePlanDefSave.jsp
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
  loggerDebug("FIRulePlanDefSave","1开始执行Save页面");
  FIRulePlanDefSchema mFIRulePlanDefSchema = new FIRulePlanDefSchema();
  //FIRulePlanDefUI mFIRulePlanDefUI = new FIRulePlanDefUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");              //获得用户信息
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  mOperateType = mOperateType.trim();
  loggerDebug("FIRulePlanDefSave","2操作的类型是" +  mOperateType);
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mType = "";
  String tRulesPlanID = "";

  loggerDebug("FIRulePlanDefSave","3开始进行获取数据的操作！！！");
  if(mOperateType.equals("INSERT||MAIN"))
  {
  	tRulesPlanID = PubFun1.CreateMaxNo("RulesPlanID",8);
 	 	loggerDebug("FIRulePlanDefSave","流水号作业：校验计划编号" + tRulesPlanID);
  	mFIRulePlanDefSchema.setVersionNo(request.getParameter("VersionNo"));
  	mFIRulePlanDefSchema.setRulesPlanID(tRulesPlanID);
  	mFIRulePlanDefSchema.setRulesPlanName(request.getParameter("RulesPlanName"));
  	mFIRulePlanDefSchema.setRulePlanState(request.getParameter("RulePlanState"));
  	mFIRulePlanDefSchema.setMarkInfo(request.getParameter("MarkInfo"));
  	mFIRulePlanDefSchema.setSequence(request.getParameter("Sequence"));
  	mFIRulePlanDefSchema.setCallPointID(request.getParameter("CallPointID")); 
  	VData tVData = new VData();
  	try
  	{
  		tVData.add(tG);
  		tVData.addElement(mOperateType);
    	tVData.addElement(mFIRulePlanDefSchema);
    	uiBusinessDelegate.submitData(tVData,mOperateType,"FIRulePlanDefUI");
  	}
  	catch(Exception ex)
  	{
    	Content = mType+"失败，原因是:" + ex.toString();
    	FlagStr = "Fail";
  	}
  }
  if(mOperateType.equals("UPDATE||MAIN"))
  {
  	mFIRulePlanDefSchema.setVersionNo(request.getParameter("VersionNo"));
  	mFIRulePlanDefSchema.setRulesPlanID(request.getParameter("RulesPlanID"));
  	mFIRulePlanDefSchema.setRulesPlanName(request.getParameter("RulesPlanName"));
  	mFIRulePlanDefSchema.setRulePlanState(request.getParameter("RulePlanState"));
  	mFIRulePlanDefSchema.setMarkInfo(request.getParameter("MarkInfo"));
  	mFIRulePlanDefSchema.setSequence(request.getParameter("Sequence"));
  	mFIRulePlanDefSchema.setCallPointID(request.getParameter("CallPointID")); 
  	VData tVData = new VData();
  	try
  	{
  		tVData.add(tG);
  		tVData.addElement(mOperateType);
    	tVData.addElement(mFIRulePlanDefSchema);
    	uiBusinessDelegate.submitData(tVData,mOperateType,"FIRulePlanDefUI");
  	}
  	catch(Exception ex)
  	{
    	Content = mType+"失败，原因是:" + ex.toString();
    	FlagStr = "Fail";
  	}
  }
  
  if(mOperateType.equals("DELETE||MAIN"))
  {
  	mFIRulePlanDefSchema.setVersionNo(request.getParameter("VersionNo"));
  	mFIRulePlanDefSchema.setRulesPlanID(request.getParameter("RulesPlanID"));
  	mFIRulePlanDefSchema.setRulesPlanName(request.getParameter("RulesPlanName"));
  	mFIRulePlanDefSchema.setRulePlanState(request.getParameter("RulePlanState"));
  	mFIRulePlanDefSchema.setMarkInfo(request.getParameter("MarkInfo"));
  	mFIRulePlanDefSchema.setSequence(request.getParameter("Sequence"));
  	mFIRulePlanDefSchema.setCallPointID(request.getParameter("CallPointID")); 
  	VData tVData = new VData();
  	try
  	{
  		tVData.add(tG);
  		tVData.addElement(mOperateType);
    	tVData.addElement(mFIRulePlanDefSchema);
    	uiBusinessDelegate.submitData(tVData,mOperateType,"FIRulePlanDefUI");
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
