<%
//程序名称：RateCardSave.jsp
//Creator :zz
//date :2008-06-20
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.vbl.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@page import="com.sinosoft.lis.list.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  


<%
  loggerDebug("RateCardSave","开始执行Save页面");
  //定义全局变量：管理机构和操作员
  String Operator="";
  String tManageCom="";
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mDescType = ""; //将操作标志的英文转换成汉字的形式
  String hiddenRiskCode=""; //隐藏的RiskCode,用于在修改操作时查询数据数据库中的老记录
  String hiddenPrem=""; //隐藏的Prem,用于在修改操作时查询数据数据库中的老记录

  RateCardSchema tRateCardSchema = new RateCardSchema();

  //建立错误对象
  CErrors tError = null;
  
  loggerDebug("RateCardSave","开始进行获取数据的操作！！！");
  String mOperateType = request.getParameter("OperateType");
  //操作类型是插入，查询，更新
  loggerDebug("RateCardSave","操作的类型是"+mOperateType);
  loggerDebug("RateCardSave","页面录入的险种编码是"+request.getParameter("Riskcode"));
  loggerDebug("RateCardSave","页面录入的保费是"+request.getParameter("Prem"));
  loggerDebug("RateCardSave","页面录入的份数是"+request.getParameter("Mult"));
  loggerDebug("RateCardSave","页面录入的保险年期是"+request.getParameter("InsuYear"));
  loggerDebug("RateCardSave","页面录入的保险年期单位是"+request.getParameter("InsuYearFlag"));
  loggerDebug("RateCardSave","产品计划是"+request.getParameter("ProductPlan"));  
  loggerDebug("RateCardSave","页面传入的隐藏险种编码是"+request.getParameter("HiddenRiskCode"));
  loggerDebug("RateCardSave","页面传入的隐藏保费是"+request.getParameter("HiddenPrem"));
  
  
  //给LZCardFeeSchema的实例赋值,已便在BL层中获得这些数据,根据这些变量查询数据库，进行一些校验判断：是否可以进行插入等操作
  tRateCardSchema.setRiskCode(request.getParameter("Riskcode"));
  tRateCardSchema.setPrem(request.getParameter("Prem"));
  tRateCardSchema.setMult(request.getParameter("Mult"));
  tRateCardSchema.setInsuYear(request.getParameter("InsuYear"));
  tRateCardSchema.setInsuYearFlag(request.getParameter("InsuYearFlag"));
  tRateCardSchema.setProductPlan(request.getParameter("ProductPlan"));
  
  
  hiddenRiskCode=request.getParameter("HiddenRiskCode");
  hiddenPrem=request.getParameter("HiddenPrem");
  if(hiddenRiskCode.equals(""))
  {
	  hiddenRiskCode=tRateCardSchema.getRiskCode();
  }
  
  if(hiddenPrem.equals(""))
  {
	  hiddenPrem=request.getParameter("Prem");
  }
  
  loggerDebug("RateCardSave","处理后的隐藏险种编码是"+hiddenRiskCode);
  loggerDebug("RateCardSave","处理后的隐藏保费是"+hiddenPrem);
  
  TransferData tTransferData = new TransferData();
  tTransferData.setNameAndValue("HiddenRiskCode",hiddenRiskCode);
  tTransferData.setNameAndValue("HiddenPrem",hiddenPrem);
  
  
  
  //获得全局变量
  GlobalInput tG = new GlobalInput();  
  tG=(GlobalInput)session.getValue("GI");  
  if(tG == null) 
  {
	  out.println("全局变量为空");
	  return;
  }
  else
  {
      Operator=tG.Operator;
      loggerDebug("RateCardSave","操作员是"+Operator);
      tManageCom=tG.ManageCom;
      loggerDebug("RateCardSave","管理机构是"+tManageCom);
  }
  
  tRateCardSchema.setManagerCom("86"); //目前只有总公司能定义
  tRateCardSchema.setOperator(tG.Operator);
 

  if(mOperateType.equals("INSERT"))
  {
      mDescType = "新增";
  }
  if(mOperateType.equals("UPDATE"))
  {
      mDescType = "修改";
  } 
  if(mOperateType.equals("DELETE"))
  {
      mDescType = "删除";
  }
  if(mOperateType.equals("QUERY"))
  {
      mDescType = "查询";
  }


  VData tVData = new VData();
  
  //将数据提交给后台UI,参数是VData和操作类型
  RateCardBL tRateCardBL = new RateCardBL();
  try
  {
    //将操作类型，管理机构，操作员添加到容器中
    tVData.addElement(tRateCardSchema);
    tVData.addElement(tTransferData);
    if (!tRateCardBL.submitData(tVData,mOperateType))
    {
    	Content = mDescType+"失败，原因是: " + tRateCardBL.mErrors.getError(0).errorMessage;
      	FlagStr = "Fail";
    }
  }
  catch(Exception ex)
  {
    Content = mDescType+"失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }

  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
    tError = tRateCardBL.mErrors;
    if (!tError.needDealError())
    {
      Content = mDescType+" 成功";
    	FlagStr = "Succ";
    }
    else
    {
    	Content = mDescType+" 失败，原因是:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
