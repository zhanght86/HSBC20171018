<%
//程序名称：ProposalSpotCheckSave.jsp
//程序功能：复核抽查规则定义
//创建日期：2008-06-30 
//创建人  ：ln
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.vbl.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.finfee.*"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  


<%
  loggerDebug("ProposalSpotCheckSave","开始执行Save页面");
  //定义全局变量：管理机构和操作员
  String Operator="";
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mDescType = ""; //将操作标志的英文转换成汉字的形式
  String mContent = ""; //将操作标志的英文转换成汉字的形式
  
  //复核抽检算法表：BPOCheckCalMode
  BPOCheckCalModeSchema mBPOCheckCalModeSchema = new BPOCheckCalModeSchema();
  BPOCheckCalModeSet mBPOCheckCalModeSet = new BPOCheckCalModeSet();

  ProposalSpotCheckUI mProposalSpotCheckUI = new ProposalSpotCheckUI();
  //建立错误对象
  CErrors tError = null;
  loggerDebug("ProposalSpotCheckSave","开始进行获取数据的操作！！！");
  String mOperateType = request.getParameter("OperateType");
  //操作类型是插入，查询，更新
  loggerDebug("ProposalSpotCheckSave","操作的类型是"+mOperateType);
  
  String tBussNo   = request.getParameter("BussNoa");
  String tManageCom   = request.getParameter("ManageComa");
  String tcheckRate  = request.getParameter("checkRatea");
  String tcheckMax   = request.getParameter("checkMaxa");
  
  loggerDebug("ProposalSpotCheckSave","页面录入的外包公司是"+tBussNo);
  loggerDebug("ProposalSpotCheckSave","页面录入的抽查比率是"+tManageCom);
  loggerDebug("ProposalSpotCheckSave","页面录入的抽查上限是"+tcheckRate);
  loggerDebug("ProposalSpotCheckSave","页面录入的管理机构是"+tcheckMax);
  //loggerDebug("ProposalSpotCheckSave","页面录入的备注是"+request.getParameter("Remark"));
  
  //给BPOCheckCalModeSchema的实例赋值,已便在BL层中获得这些数据,根据这些变量查询数据库，进行一些校验判断：是否可以进行插入等操作
  mBPOCheckCalModeSchema.setBussNoType("OF");
  mBPOCheckCalModeSchema.setBPOID(tBussNo);
  mBPOCheckCalModeSchema.setRate(tcheckRate);
  mBPOCheckCalModeSchema.setMaxMum(tcheckMax);
  mBPOCheckCalModeSchema.setManageCom(tManageCom);
  
  if(mOperateType.equals("1"))
  {
      mDescType = "INSERT";
      mContent = "新增";
  }
  if(mOperateType.equals("2"))
  {
      mDescType = "UPDATE";
      mContent = "修改";
  }

  if(mOperateType.equals("3"))
  {
      mDescType = "DELETE";
      mContent = "删除";
  }
  
  loggerDebug("ProposalSpotCheckSave","mDescType"+mDescType); 

  //将Schema放入Set中
  mBPOCheckCalModeSet.add(mBPOCheckCalModeSchema);
  VData tVData = new VData();
  
  //将数据提交给后台UI,参数是VData和操作类型
  try
  {
    //将操作类型，管理机构，操作员添加到容器中
    tVData.addElement(mDescType);
    tVData.addElement(mBPOCheckCalModeSet);
   
	mProposalSpotCheckUI.submitData(tVData,mDescType);
  }
  catch(Exception ex)
  {
    Content = mContent+"失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }

  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
    tError = mProposalSpotCheckUI.mErrors;
    if (!tError.needDealError())
    {
      Content = mContent+"成功";
    	FlagStr = "Succ";
    }
    else
    {
    	Content = mContent+"失败，原因是:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
