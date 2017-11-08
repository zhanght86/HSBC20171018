<%
//程序名称 :CostDataKeyDefInput.jsp
//程序功能 :凭证费用数据主键定义
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
  loggerDebug("CostDataKeyDefSave","1开始执行Save页面");
  FICostDataKeyDefSchema mFICostDataKeyDefSchema = new FICostDataKeyDefSchema();
  //FICostDataKeyDefUI mFICostDataKeyDefUI = new FICostDataKeyDefUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");              //获得用户信息
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  mOperateType = mOperateType.trim();
  loggerDebug("CostDataKeyDefSave","2操作的类型是" +  mOperateType);
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mType = "";//将操作标志的英文转换成汉字的形式

  loggerDebug("CostDataKeyDefSave","3开始进行获取数据的操作！！！");
  mFICostDataKeyDefSchema.setVersionNo(request.getParameter("VersionNo"));
  mFICostDataKeyDefSchema.setAcquisitionID(request.getParameter("AcquisitionID"));
  mFICostDataKeyDefSchema.setKeyID(request.getParameter("KeyID"));
  mFICostDataKeyDefSchema.setKeyName(request.getParameter("KeyName"));
  mFICostDataKeyDefSchema.setKeyOrder(request.getParameter("KeyOrder"));
  mFICostDataKeyDefSchema.setRemark(request.getParameter("Remark")); 
  loggerDebug("CostDataKeyDefSave","4");
  
  if(mOperateType.equals("INSERT||MAIN"))
  {
    mType = "新增";
  }
  if(mOperateType.equals("DELETE||MAIN"))
  {
    mType = "删除";
  }
  loggerDebug("CostDataKeyDefSave","5");
  VData tVData = new VData();
  try
  {
  	tVData.add(tG);
    tVData.addElement(mFICostDataKeyDefSchema);
    uiBusinessDelegate.submitData(tVData,mOperateType,"FICostDataKeyDefUI");
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
