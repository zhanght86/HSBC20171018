<%
//程序名称：ProposalSpotCheckSave.jsp
//程序功能：复核抽查规则定义
//创建日期：2007-07-26 
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
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
  <%@page import="com.sinosoft.service.*" %>

<%
  loggerDebug("ProposalSpotCheckSave","开始执行Save页面");
  //定义全局变量：管理机构和操作员
  String Operator="";
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mDescType = ""; //将操作标志的英文转换成汉字的形式
  
  //复核抽检算法表：BPOCheckCalMode
  BPOCheckCalModeSchema mBPOCheckCalModeSchema = new BPOCheckCalModeSchema();
  BPOCheckCalModeSet mBPOCheckCalModeSet = new BPOCheckCalModeSet();

  //ProposalSpotCheckUI mProposalSpotCheckUI = new ProposalSpotCheckUI();
  String busiName="tbProposalSpotCheckUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //建立错误对象
  CErrors tError = null;
  loggerDebug("ProposalSpotCheckSave","开始进行获取数据的操作！！！");
  String mOperateType = request.getParameter("OperateType");
  //操作类型是插入，查询，更新
  loggerDebug("ProposalSpotCheckSave","操作的类型是"+mOperateType);
  loggerDebug("ProposalSpotCheckSave","页面录入的外包方编码是"+request.getParameter("BussNo"));
  loggerDebug("ProposalSpotCheckSave","页面录入的险种编码是"+request.getParameter("RiskCode"));
  loggerDebug("ProposalSpotCheckSave","页面录入的抽查比率是"+request.getParameter("checkRate"));
  loggerDebug("ProposalSpotCheckSave","页面录入的抽查上限是"+request.getParameter("checkMax"));
  loggerDebug("ProposalSpotCheckSave","页面录入的管理机构是"+request.getParameter("ManageCom"));
  loggerDebug("ProposalSpotCheckSave","页面录入的备注是"+request.getParameter("Remark"));
  
  
  //给BPOCheckCalModeSchema的实例赋值,已便在BL层中获得这些数据,根据这些变量查询数据库，进行一些校验判断：是否可以进行插入等操作
  mBPOCheckCalModeSchema.setBPOID(request.getParameter("BussNo"));
  mBPOCheckCalModeSchema.setBussNoType("TB");
  mBPOCheckCalModeSchema.setRiskCode(request.getParameter("RiskCode"));
  mBPOCheckCalModeSchema.setRate(request.getParameter("checkRate"));
  mBPOCheckCalModeSchema.setMaxMum(request.getParameter("checkMax"));
  mBPOCheckCalModeSchema.setRemark(request.getParameter("Remark"));
  mBPOCheckCalModeSchema.setManageCom(request.getParameter("ManageCom"));

  if(mOperateType.equals("INSERT"))
  {
      mDescType = "新增";
  }
  if(mOperateType.equals("UPDATE"))
  {
      mDescType = "修改";
  } 
  if(mOperateType.equals("QUERY"))
  {
      mDescType = "查询";
  }

  //将Schema放入Set中
  mBPOCheckCalModeSet.add(mBPOCheckCalModeSchema);
  VData tVData = new VData();
  
  //将数据提交给后台UI,参数是VData和操作类型
  try
  {
    //将操作类型，管理机构，操作员添加到容器中
    tVData.addElement(mOperateType);
    tVData.addElement(mBPOCheckCalModeSet);
   
    tBusinessDelegate.submitData(tVData,mOperateType,busiName);
  }
  catch(Exception ex)
  {
    Content = mDescType+"失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }

  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {
      Content = "保存成功";
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
