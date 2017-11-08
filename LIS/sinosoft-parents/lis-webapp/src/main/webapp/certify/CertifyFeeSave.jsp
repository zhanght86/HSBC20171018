<%
//程序名称：CertifyFeeSave.jsp
//程序功能：
//创建日期：2007-01-15 
//创建人  ：张征
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
 <%@page import="com.sinosoft.lis.certify.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  


<%
  loggerDebug("CertifyFeeSave","开始执行Save页面");
  //定义全局变量：管理机构和操作员
  String Operator="";
  String tManageCom="";
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mDescType = ""; //将操作标志的英文转换成汉字的形式
  
  //单证收费定义表：LZCardFee
  LZCardFeeSchema mLZCardFeeSchema = new LZCardFeeSchema();
  LZCardFeeSet mLZCardFeeSet = new LZCardFeeSet();

  CertifyFeeUI mCertifyFeeUI = new CertifyFeeUI();
  //建立错误对象
  CErrors tError = null;
  
  loggerDebug("CertifyFeeSave","开始进行获取数据的操作！！！");
  String mOperateType = request.getParameter("OperateType");
  //操作类型是插入，查询，更新
  loggerDebug("CertifyFeeSave","操作的类型是"+mOperateType);
  loggerDebug("CertifyFeeSave","页面录入的单证编码是"+request.getParameter("CertifyCode"));
  loggerDebug("CertifyFeeSave","页面录入的单证名称是"+request.getParameter("CertifyName"));
  loggerDebug("CertifyFeeSave","页面录入的单证单位是"+request.getParameter("Price"));
  loggerDebug("CertifyFeeSave","页面录入的单证定价是"+request.getParameter("Unit"));
  loggerDebug("CertifyFeeSave","登陆机构是"+request.getParameter("ManageCom"));
  
  
  //给LZCardFeeSchema的实例赋值,已便在BL层中获得这些数据,根据这些变量查询数据库，进行一些校验判断：是否可以进行插入等操作
  mLZCardFeeSchema.setCertifyCode(request.getParameter("CertifyCode"));
  mLZCardFeeSchema.setCertifyName(request.getParameter("CertifyName"));
  mLZCardFeeSchema.setPrice(request.getParameter("Price"));
  mLZCardFeeSchema.setUnit(request.getParameter("Unit"));
  mLZCardFeeSchema.setManageCom(request.getParameter("ManageCom"));
  
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
      loggerDebug("CertifyFeeSave","操作员是"+Operator);
      tManageCom=tG.ManageCom;
      loggerDebug("CertifyFeeSave","管理机构是"+tManageCom);
  }
 

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

  //将Schema放入Set中
  mLZCardFeeSet.add(mLZCardFeeSchema);
  VData tVData = new VData();
  
  //将数据提交给后台UI,参数是VData和操作类型
  try
  {
    //将操作类型，管理机构，操作员添加到容器中
    tVData.addElement(mOperateType);
    tVData.addElement(Operator);
    tVData.addElement(tManageCom);
    tVData.addElement(mLZCardFeeSet);
   
	mCertifyFeeUI.submitData(tVData,mOperateType);
  }
  catch(Exception ex)
  {
    Content = mDescType+"失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }

  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
    tError = mCertifyFeeUI.mErrors;
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
