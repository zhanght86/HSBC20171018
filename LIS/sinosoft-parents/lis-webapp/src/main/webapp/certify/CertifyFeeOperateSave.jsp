<%
 loggerDebug("CertifyFeeOperateSave","Start CertifyFeeOperateSave.jsp Submit...1");
//程序名称：CertifyFeeOperateSave.jsp
//程序功能：
//创建日期：2007-01-16 
//创建人  ：张征
//更新记录：  更新人    更新日期     更新原因/内容
%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.get.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.certify.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%

  //接收信息，并作校验处理。
  //输入参数
  //输出参数
  

  String FlagStr = "";
  String Content = "";
  String ManageCom="";
  String Operator="";
  String tPayDate="";
  String mOperateType="";
  String mDescType = ""; //将操作标志的英文转换成汉字的形式
  CErrors tError = null;
  
  loggerDebug("CertifyFeeOperateSave","开始进行获取数据的操作！！！");
  mOperateType = request.getParameter("OperateType");
  ManageCom = request.getParameter("StationCode");
  
  //操作类型是插入，查询，更新
  loggerDebug("CertifyFeeOperateSave","操作的类型是"+mOperateType);
	loggerDebug("CertifyFeeOperateSave","页面录入的单证编码是"+request.getParameter("CertifyCode"));
  loggerDebug("CertifyFeeOperateSave","页面录入的单证名称是"+request.getParameter("CertifyName"));
  loggerDebug("CertifyFeeOperateSave","页面录入的单证数量是"+request.getParameter("Count"));
  loggerDebug("CertifyFeeOperateSave","页面录入的缴费金额是"+request.getParameter("PayMoney"));
  loggerDebug("CertifyFeeOperateSave","页面录入的缴费时间是"+request.getParameter("PayDate"));
  loggerDebug("CertifyFeeOperateSave","页面录入的管理机构是"+ManageCom);
 
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
	    loggerDebug("CertifyFeeOperateSave","操作员是"+Operator);
  }
  

  //单证操作表：LZCardFeeOper
  LZCardFeeOperSchema mLZCardFeeOperSchema = new LZCardFeeOperSchema();
  LZCardFeeOperSet mLZCardFeeOperSet = new LZCardFeeOperSet();
  CertifyFeeOperateUI mCertifyFeeOperateUI = new CertifyFeeOperateUI();
  //给LZCardFeeSchema的实例赋值,已便在BL层中获得这些数据,根据这些变量查询数据库，进行一些校验判断：是否可以进行插入等操作
  mLZCardFeeOperSchema.setCertifyCode(request.getParameter("CertifyCode"));
  mLZCardFeeOperSchema.setCertifyName(request.getParameter("CertifyName"));
  mLZCardFeeOperSchema.setCount(request.getParameter("Count"));
  mLZCardFeeOperSchema.setPayMoney(request.getParameter("PayMoney")); 
  mLZCardFeeOperSchema.setPayDate(request.getParameter("PayDate")); 
  
  //将Schema放入Set中
  mLZCardFeeOperSet.add(mLZCardFeeOperSchema);
  VData tVData = new VData();
  
  if(mOperateType.equals("INSERT"))
  {
    mDescType = "保存";
  }
  
  //将数据提交给后台UI,参数是VData和操作类型
  try
  {
	    //将操作类型，管理机构，操作员添加到容器中
	    tVData.addElement(mOperateType);
	    tVData.addElement(ManageCom);
	    tVData.addElement(Operator);
	    tVData.addElement(mLZCardFeeOperSet);
	   
	    mCertifyFeeOperateUI.submitData(tVData,mOperateType);
  }
  catch(Exception ex)
  {
    Content = mDescType+"失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }

  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
    tError = mCertifyFeeOperateUI.mErrors;
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
  loggerDebug("CertifyFeeOperateSave","Flag : " + FlagStr + " -- Content : " + Content);
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

