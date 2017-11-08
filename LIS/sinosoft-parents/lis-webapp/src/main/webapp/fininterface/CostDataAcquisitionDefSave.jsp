<%
//程序名称 :CostDataAcquisitionDefInput.jsp
//程序功能 :凭证费用数据采集定义
//创建人 :范昕
//创建日期 :2008-08-18
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
  loggerDebug("CostDataAcquisitionDefSave","1开始执行Save页面");
  FICostDataAcquisitionDefSchema mFICostDataAcquisitionDefSchema = new FICostDataAcquisitionDefSchema();
  //FICostDataAcquisitionDefUI mFICostDataAcquisitionDefUI = new FICostDataAcquisitionDefUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");              //获得用户信息
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  mOperateType = mOperateType.trim();
  loggerDebug("CostDataAcquisitionDefSave","2操作的类型是" +  mOperateType);
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mType = "";

  loggerDebug("CostDataAcquisitionDefSave","3开始进行获取数据的操作！！！");
  if(mOperateType.equals("INSERT||MAIN"))
  {
  	mFICostDataAcquisitionDefSchema.setVersionNo(request.getParameter("VersionNo"));
  	mFICostDataAcquisitionDefSchema.setAcquisitionID(request.getParameter("AcquisitionID"));
  	mFICostDataAcquisitionDefSchema.setAcquisitionType(request.getParameter("AcquisitionType"));
  	mFICostDataAcquisitionDefSchema.setDataSourceType(request.getParameter("DataSourceType"));
  	mFICostDataAcquisitionDefSchema.setCostOrDataID(request.getParameter("CostOrDataID"));
  	mFICostDataAcquisitionDefSchema.setDistillMode(request.getParameter("DistillMode"));
  	mFICostDataAcquisitionDefSchema.setDistillSQL(request.getParameter("DistillSQL"));
  	mFICostDataAcquisitionDefSchema.setDistillSQLForOne(request.getParameter("DistillSQLForOne"));
  	mFICostDataAcquisitionDefSchema.setDistillClass(request.getParameter("DistillClass"));
  	mFICostDataAcquisitionDefSchema.setDistillClassForOne(request.getParameter("DistillClassForOne"));  	
  	mFICostDataAcquisitionDefSchema.setRemark(request.getParameter("Remark"));   
  	VData tVData = new VData();
  	try
  	{
  	   tVData.add(tG);
  	   tVData.addElement(mOperateType);
    	   tVData.addElement(mFICostDataAcquisitionDefSchema);
    	   uiBusinessDelegate.submitData(tVData,mOperateType,"FICostDataAcquisitionDefUI");
  	}
  	catch(Exception ex)
  	{
    	   Content = mType+"失败，原因是:" + ex.toString();
    	   FlagStr = "Fail";
  	}
  }
  if(mOperateType.equals("UPDATE||MAIN"))
  {
  	mFICostDataAcquisitionDefSchema.setVersionNo(request.getParameter("VersionNo"));
  	mFICostDataAcquisitionDefSchema.setAcquisitionID(request.getParameter("AcquisitionID"));
  	mFICostDataAcquisitionDefSchema.setAcquisitionType(request.getParameter("AcquisitionType"));
  	mFICostDataAcquisitionDefSchema.setDataSourceType(request.getParameter("DataSourceType"));
  	mFICostDataAcquisitionDefSchema.setCostOrDataID(request.getParameter("CostOrDataID"));
  	mFICostDataAcquisitionDefSchema.setDistillMode(request.getParameter("DistillMode"));
  	mFICostDataAcquisitionDefSchema.setDistillSQL(request.getParameter("DistillSQL"));
  	mFICostDataAcquisitionDefSchema.setDistillSQLForOne(request.getParameter("DistillSQLForOne"));
  	mFICostDataAcquisitionDefSchema.setDistillClass(request.getParameter("DistillClass"));
  	mFICostDataAcquisitionDefSchema.setDistillClassForOne(request.getParameter("DistillClassForOne")); 
  	mFICostDataAcquisitionDefSchema.setRemark(request.getParameter("Remark"));   
  	VData tVData = new VData();
  	try
  	{
  		tVData.add(tG);
  		tVData.addElement(mOperateType);
    	tVData.addElement(mFICostDataAcquisitionDefSchema);
    	uiBusinessDelegate.submitData(tVData,mOperateType,"FICostDataAcquisitionDefUI");
  	}
  	catch(Exception ex)
  	{
    	Content = mType+"失败，原因是:" + ex.toString();
    	FlagStr = "Fail";
  	}
  }
  
  if(mOperateType.equals("DELETE||MAIN"))
  {
  	mFICostDataAcquisitionDefSchema.setVersionNo(request.getParameter("VersionNo"));
  	mFICostDataAcquisitionDefSchema.setAcquisitionID(request.getParameter("AcquisitionID"));
  	mFICostDataAcquisitionDefSchema.setAcquisitionType(request.getParameter("AcquisitionType"));
  	mFICostDataAcquisitionDefSchema.setDataSourceType(request.getParameter("DataSourceType"));	
  	mFICostDataAcquisitionDefSchema.setCostOrDataID(request.getParameter("CostOrDataID"));
  	mFICostDataAcquisitionDefSchema.setDistillMode(request.getParameter("DistillMode"));
  	mFICostDataAcquisitionDefSchema.setDistillSQL(request.getParameter("DistillSQL"));
  	mFICostDataAcquisitionDefSchema.setDistillSQLForOne(request.getParameter("DistillSQLForOne"));
  	mFICostDataAcquisitionDefSchema.setDistillClass(request.getParameter("DistillClass"));
  	mFICostDataAcquisitionDefSchema.setDistillClassForOne(request.getParameter("DistillClassForOne")); 
  	mFICostDataAcquisitionDefSchema.setRemark(request.getParameter("Remark"));   

  	VData tVData = new VData();
  	try
  	{
  		tVData.add(tG);
  		tVData.addElement(mOperateType);
    	tVData.addElement(mFICostDataAcquisitionDefSchema);
    	uiBusinessDelegate.submitData(tVData,mOperateType,"FICostDataAcquisitionDefUI");
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
