<%
//程序名称 :VersionRuleSave.jsp
//程序功能 :财务规则版本管理
//创建人 :范昕
//创建日期 :2008-08-21
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
  <%@page import="com.sinosoft.lis.fininterface.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  loggerDebug("VersionRuleSave","开始执行Save页面");
  FIRulesVersionSchema mFIRulesVersionSchema = new FIRulesVersionSchema();
  FIRulesVersionTraceSchema mFIRulesVersionTraceSchema = new FIRulesVersionTraceSchema();
  //FIVersionRuleUI mFIVersionRuleUI = new FIVersionRuleUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");              //获得用户信息
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  mOperateType = mOperateType.trim();
  loggerDebug("VersionRuleSave","操作的类型是" +  mOperateType);
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mType = "";//将操作标志的英文转换成汉字的形式
  loggerDebug("VersionRuleSave",mOperateType);
  String tVersionNo = "";
  
  loggerDebug("VersionRuleSave","3开始进行获取数据的操作！！！");
  if (mOperateType.equals("addVersion"))
  {
  	tVersionNo = PubFun1.CreateMaxNo("VersionNo",20);
  	loggerDebug("VersionRuleSave","流水号作业：版本编号" + tVersionNo);
		mFIRulesVersionSchema.setVersionNo(tVersionNo);
		mFIRulesVersionSchema.setVersionState(request.getParameter("VersionState"));
  	mFIRulesVersionSchema.setStartDate(request.getParameter("StartDate"));
  	mFIRulesVersionSchema.setEndDate(request.getParameter("EndDate"));
  	mFIRulesVersionSchema.setVersionReMark(request.getParameter("VersionReMark"));
  	VData tVData = new VData();
  	try
  	{
  		tVData.add(tG);
  		tVData.addElement(mOperateType);
    	tVData.addElement(mFIRulesVersionSchema);
    	uiBusinessDelegate.submitData(tVData,mOperateType,"FIVersionRuleUI");
  	}
  	catch(Exception ex)
  	{
    	Content = mType+"失败，原因是:" + ex.toString();
    	FlagStr = "Fail";
  	}
  }
  
	else if (mOperateType.equals("deleteVersion"))
	{
		mFIRulesVersionSchema.setVersionNo(request.getParameter("VersionNo"));
		mFIRulesVersionSchema.setVersionState(request.getParameter("VersionState"));
  	mFIRulesVersionSchema.setStartDate(request.getParameter("StartDate"));
  	mFIRulesVersionSchema.setEndDate(request.getParameter("EndDate"));
  	mFIRulesVersionSchema.setVersionReMark(request.getParameter("VersionReMark"));
  	loggerDebug("VersionRuleSave",mFIRulesVersionSchema.getVersionNo());
  	VData tVData = new VData();
  	try
  	{
  		tVData.add(tG);
  		tVData.addElement(mOperateType);
    	tVData.addElement(mFIRulesVersionSchema);
    	uiBusinessDelegate.submitData(tVData,mOperateType,"FIVersionRuleUI");
  	}
  	catch(Exception ex)
  	{
    	Content = mType+"失败，原因是:" + ex.toString();
    	FlagStr = "Fail";
  	}  	
	}
  else if (mOperateType.equals("applyAmend"))
  	{
			mFIRulesVersionTraceSchema.setMaintenanceno(request.getParameter("Maintenanceno"));
			mFIRulesVersionTraceSchema.setVersionNo(request.getParameter("VersionNo"));
			mFIRulesVersionTraceSchema.setMaintenanceState(request.getParameter("MaintenanceState"));
			mFIRulesVersionTraceSchema.setMaintenanceReMark(request.getParameter("MaintenanceReMark"));
			mFIRulesVersionSchema.setVersionNo(request.getParameter("VersionNo"));
			mFIRulesVersionSchema.setVersionState(request.getParameter("VersionState"));
  		mFIRulesVersionSchema.setStartDate(request.getParameter("StartDate"));
  		mFIRulesVersionSchema.setEndDate(request.getParameter("EndDate"));
  		mFIRulesVersionSchema.setVersionReMark(request.getParameter("VersionReMark"));
			VData tVData = new VData();
  		try
  		{
  			tVData.add(tG);
  			tVData.addElement(mOperateType);
  			tVData.addElement(mFIRulesVersionSchema);
    		tVData.addElement(mFIRulesVersionTraceSchema);
    		uiBusinessDelegate.submitData(tVData,mOperateType,"FIVersionRuleUI");
  		}
  		catch(Exception ex)
  		{
    		Content = mType+"失败，原因是:" + ex.toString();
    		FlagStr = "Fail";
  		}
  	}
	else if (mOperateType.equals("CompleteAmend"))
		{
			mFIRulesVersionTraceSchema.setMaintenanceno(request.getParameter("Maintenanceno"));
			mFIRulesVersionTraceSchema.setVersionNo(request.getParameter("TraceVersionNo"));
			mFIRulesVersionTraceSchema.setMaintenanceState(request.getParameter("MaintenanceState"));
			mFIRulesVersionTraceSchema.setMaintenanceReMark(request.getParameter("MaintenanceReMark"));
  		loggerDebug("VersionRuleSave",mFIRulesVersionTraceSchema.getMaintenanceno());
  		VData tVData = new VData();
  		try
  		{
  			tVData.add(tG);
  			tVData.addElement(mOperateType);
    		tVData.addElement(mFIRulesVersionTraceSchema);
    		uiBusinessDelegate.submitData(tVData,mOperateType,"FIVersionRuleUI");
  		}
  		catch(Exception ex)
  		{
    		Content = mType+"失败，原因是:" + ex.toString();
    		FlagStr = "Fail";
  		}  	
		}
	else if (mOperateType.equals("cancelAmend"))
		{
			mFIRulesVersionTraceSchema.setMaintenanceno(request.getParameter("Maintenanceno"));
			mFIRulesVersionTraceSchema.setVersionNo(request.getParameter("TraceVersionNo"));
			mFIRulesVersionTraceSchema.setMaintenanceState(request.getParameter("MaintenanceState"));
			mFIRulesVersionTraceSchema.setMaintenanceReMark(request.getParameter("MaintenanceReMark"));
  		loggerDebug("VersionRuleSave",mFIRulesVersionTraceSchema.getMaintenanceno());
  		VData tVData = new VData();
  		try
  		{
  			tVData.add(tG);
  			tVData.addElement(mOperateType);
    		tVData.addElement(mFIRulesVersionTraceSchema);
    		uiBusinessDelegate.submitData(tVData,mOperateType,"FIVersionRuleUI");
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
	parent.fraInterface.fm.TraceVersionNo.value = "<%=tVersionNo%>";
</script>
</html>
