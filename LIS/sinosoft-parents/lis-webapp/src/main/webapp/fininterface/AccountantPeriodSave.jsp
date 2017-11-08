<%
//程序名称：AccountantPeriodSave.jsp
//程序功能：会计期间管理
//创建日期：2008-08-04	
//创建人  ：范昕
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
  loggerDebug("AccountantPeriodSave","1开始执行Save页面");
  FIPeriodManagementSchema mFIPeriodManagementSchema = new FIPeriodManagementSchema();
  //FIPeriodManagementUI mFIPeriodManagementUI = new FIPeriodManagementUI();
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();	
	String mmResult = "";
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");              //获得用户信息
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  mOperateType = mOperateType.trim();
  loggerDebug("AccountantPeriodSave","2操作的类型是" +  mOperateType);
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mType = "";//将操作标志的英文转换成汉字的形式

  loggerDebug("AccountantPeriodSave","3开始进行获取数据的操作！！！");
  mFIPeriodManagementSchema.setyear(request.getParameter("Year"));
  mFIPeriodManagementSchema.setmonth(request.getParameter("Month"));
  mFIPeriodManagementSchema.setstartdate(request.getParameter("StartDay"));
  mFIPeriodManagementSchema.setenddate(request.getParameter("EndDay"));
  mFIPeriodManagementSchema.setstate(request.getParameter("State"));  
  
  
  if(mOperateType.equals("INSERT||MAIN"))
  {
    mType = "新增";
  }
  if(mOperateType.equals("UPDATE||MAIN"))
  {
    mType = "修改";
  }
  if(mOperateType.equals("DELETE||MAIN"))
  {
    mType = "删除";
  }
  if(mOperateType.equals("QUERY||MAIN"))
  {
    mType = "查询";
  }
  
  VData tVData = new VData();
  try
  {
  	tVData.add(tG);
    tVData.addElement(mFIPeriodManagementSchema);
    loggerDebug("AccountantPeriodSave","5tVData.addElement(mFIPeriodManagementSchema);");
    tBusinessDelegate.submitData(tVData,mOperateType,"FIPeriodManagementUI");
  }
  catch(Exception ex)
  {
    Content = mType+"失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }

  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
     tError = tBusinessDelegate.getCErrors();
    	   if (!tError.needDealError())
    		{     
    				Content = "操作已成功!";
    				FlagStr = "Succ";
    		}
   	  else
    		{
    			Content = " 保存失败，原因是:" + tError.getFirstError();
    			FlagStr = "Fail";
    		}
  }
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
