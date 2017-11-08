 <%
//程序名称 :FIRulePlanDefDetailSave.jsp
//程序功能 :凭证费用数据源定义
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
  loggerDebug("FIRulePlanDefDetailSave","1开始执行Save页面");
  FIRulePlanDefDetailSchema mFIRulePlanDefDetailSchema = new FIRulePlanDefDetailSchema();
  //FIRulePlanDefDetailUI mFIRulePlanDefDetailUI = new FIRulePlanDefDetailUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");              //获得用户信息
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  mOperateType = mOperateType.trim();
  loggerDebug("FIRulePlanDefDetailSave","2操作的类型是" +  mOperateType);
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mType = "";//将操作标志的英文转换成汉字的形式

  loggerDebug("FIRulePlanDefDetailSave","3开始进行获取数据的操作！！！");
  mFIRulePlanDefDetailSchema.setVersionNo(request.getParameter("VersionNo"));
  mFIRulePlanDefDetailSchema.setRulePlanID(request.getParameter("RulePlanID"));
  mFIRulePlanDefDetailSchema.setRuleID(request.getParameter("RuleID"));
  mFIRulePlanDefDetailSchema.setSequence(request.getParameter("Sequence"));
  mFIRulePlanDefDetailSchema.setRuleState(request.getParameter("RuleState"));
  loggerDebug("FIRulePlanDefDetailSave","4");
  
  if(mOperateType.equals("INSERT||MAIN"))
  {
    mType = "新增";
  }
  if(mOperateType.equals("DELETE||MAIN"))
  {
    mType = "删除";
  }
  if(mOperateType.equals("UPDATE||MAIN"))
  {
    mType = "修改";
  }
  loggerDebug("FIRulePlanDefDetailSave","5");
  VData tVData = new VData();
  try
  {
  	tVData.add(tG);
    tVData.addElement(mFIRulePlanDefDetailSchema);
    uiBusinessDelegate.submitData(tVData,mOperateType,"FIRulePlanDefDetailUI");
  }
  catch(Exception ex)
  {
    Content = mType+"失败，原因是:" + ex.toString();
    FlagStr = "Fail";
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
