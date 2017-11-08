<%
//程序名称：AbnormityErrAndRecordErrSave.jsp
//程序功能：异常件错误原因查询以及记录差错功能
//创建日期：2008-06-26
//创建人  ：ln
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


<%
  loggerDebug("AbnormityErrAndRecordErrSave","开始执行Save页面");
  //定义全局变量：管理机构和操作员
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  
  //差错记录表：BPOErrLog
  BPOErrLogSet mBPOErrLogSet = new BPOErrLogSet();
  
  //建立错误对象
  CErrors tError = null;
  loggerDebug("AbnormityErrAndRecordErrSave","开始进行获取数据的操作！！！");
  String mOperateType = "INSERT";
  String rgtNo=request.getParameter("TempFeeNo");
  String type=request.getParameter("bussNoType");
  //操作类型是插入，查询，更新
  loggerDebug("AbnormityErrAndRecordErrSave","单证印刷号是"+rgtNo);

  
  //获取mulitiline中的值
  String tImpartNum[] = request.getParameterValues("ErrGridNo");
  String terrorType[] = request.getParameterValues("ErrGrid1");            //差错类别
  String terrorCode[] = request.getParameterValues("ErrGrid2");           //差错编码
  String terrorContent[] = request.getParameterValues("ErrGrid3");        //差错内容

 
  //给BPOErrLogSchema的实例赋值,已便在BL层中获得这些数据,根据这些变量查询数据库，进行一些校验判断：是否可以进行插入等操作
  int ImpartCount = 0;
  
  
  if (tImpartNum != null)
  {
       ImpartCount = tImpartNum.length;
       loggerDebug("AbnormityErrAndRecordErrSave","ImpartCount= "+ImpartCount);
       
      for (int i = 0; i < ImpartCount; i++)	
  	   {
  	      BPOErrLogSchema mBPOErrLogSchema = new BPOErrLogSchema();
  	      loggerDebug("AbnormityErrAndRecordErrSave","i="+i);
		  mBPOErrLogSchema.setBussNo(rgtNo);
		  loggerDebug("AbnormityErrAndRecordErrSave","单证印刷号是"+rgtNo);
	      mBPOErrLogSchema.setErrCode(terrorCode[i]);
	      loggerDebug("AbnormityErrAndRecordErrSave","差错编码terrorCode["+i+"]="+terrorCode[i]);
		  mBPOErrLogSchema.setBussNoType(type);
		  mBPOErrLogSchema.setErrorContent(terrorContent[i]) ;
		  loggerDebug("AbnormityErrAndRecordErrSave","差错内容terrorContent["+i+"]="+terrorContent[i]);
		  mBPOErrLogSchema.setErrVer(terrorType[i]);
		  loggerDebug("AbnormityErrAndRecordErrSave","差错类别terrorType["+i+"]="+terrorType[i]);
		  mBPOErrLogSet.add(mBPOErrLogSchema);
  	   }
  }

	
  
  
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  

  AbnormityErrAndRecordErrUI mAbnormityErrAndRecordErrUI = new AbnormityErrAndRecordErrUI();

  VData tVData = new VData();
  
  //将数据提交给后台UI,参数是VData和操作类型
  try
  {
    //将操作类型，管理机构，操作员添加到容器中
    tVData.addElement(mOperateType);
    tVData.addElement(mBPOErrLogSet);
    tVData.addElement(tG);
   
	mAbnormityErrAndRecordErrUI.submitData(tVData,mOperateType);
  }
  catch(Exception ex)
  {
    Content = "保存失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }

  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
    tError = mAbnormityErrAndRecordErrUI.mErrors;
    if (!tError.needDealError())
    {
      Content = "保存成功";
    	FlagStr = "Succ";
    }
    else
    {
    	Content = "保存失败，原因是:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
%>
<html>
<script language="javascript">
	parent.fraInterfacereason.afterSubmit("<%=FlagStr%>","<%=Content%>");
	//alert("<%=Content%>");
</script>
</html>
