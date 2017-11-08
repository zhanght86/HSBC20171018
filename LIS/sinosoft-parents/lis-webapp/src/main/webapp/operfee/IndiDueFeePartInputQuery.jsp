
<%
	//程序名称：IndiDueFeePartInputQuery.jsp
	//程序功能：
	//创建日期：
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
	//
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--用户校验类-->
<%@page import="java.lang.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.bl.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.operfee.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.service.*"%>
<%
	//提取提交页的元素
	String MangeCom = request.getParameter("ManageCom");
	String ContNo = request.getParameter("ContNo");
	String RiskCode = request.getParameter("RiskCode");
	String AgentCode = request.getParameter("AgentCode");
	String mSecPayMode = request.getParameter("SecPayMode");
	String mContType = request.getParameter("ContType");

	String AheadDays ="";
	String sql="select SysVarValue from ldsysvar where sysvar='aheaddays'";
	TransferData sqlTransferData = new TransferData();
	  VData sqlVData = new VData();
			    sqlTransferData.setNameAndValue("SQL",sql);
			    sqlVData.add(sqlTransferData);
			  	BusinessDelegate tsqlBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  	  if(!tsqlBusinessDelegate.submitData(sqlVData,"getOneValue","ExeSQLUI"))
			  	  {    
			  		AheadDays = "60"; //默认为60天
			  	  }
			  	  else
			  	  {			  		
			  		AheadDays=(String)tsqlBusinessDelegate.getResult().get(0);
			  	  }
	//宽限期
	String ExtendDays ="";
	sql="select SysVarValue from ldsysvar where sysvar='ExtendLapseDates'";
	sqlTransferData = new TransferData();
	sqlVData = new VData();
	sqlTransferData.setNameAndValue("SQL",sql);
    sqlVData.add(sqlTransferData);
  	tsqlBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  	  if(!tsqlBusinessDelegate.submitData(sqlVData,"getOneValue","ExeSQLUI"))
  	  {    
  		ExtendDays = "0"; //默认为0天
  	  }
  	  else
  	  {			  		
  		ExtendDays=(String)tsqlBusinessDelegate.getResult().get(0);
  	  }
	String StartDate = PubFun.calDate(PubFun.getCurrentDate(),-(Integer.parseInt(AheadDays) + Integer.parseInt(ExtendDays)), "D", null);
	//String StartDate =""; //新需求要求将起期限制去掉。
	String EndDate = PubFun.calDate(PubFun.getCurrentDate(), Integer.parseInt(AheadDays), "D", null);
	
	//输出参数
	CErrors tError = null;
	String FlagStr = "";
	String Content = "";

	GlobalInput tGI = new GlobalInput(); //repair
	tGI = (GlobalInput) session.getValue("GI");
	if (tGI == null) {
		loggerDebug("IndiDueFeePartInputQuery","页面失效，请重新登陆");
		FlagStr = "Fail";
		Content = "页面失效，请重新登陆";
		return;
	}
	//批量的往后台传值
	TransferData mTransferData = new TransferData();
	mTransferData.setNameAndValue("Contno", ContNo);
	mTransferData.setNameAndValue("MangeCom", MangeCom);
	mTransferData.setNameAndValue("StartDate", StartDate);
	mTransferData.setNameAndValue("EndDate", EndDate);
	mTransferData.setNameAndValue("RiskCode", RiskCode);
	mTransferData.setNameAndValue("AgentCode", AgentCode);
	mTransferData.setNameAndValue("SecPayMode", mSecPayMode);
	mTransferData.setNameAndValue("ContType", mContType);
	loggerDebug("IndiDueFeePartInputQuery","StartDate==================" + StartDate);
	loggerDebug("IndiDueFeePartInputQuery","EndDate==================" + EndDate);
	
	VData tVData = new VData();
	tVData.add(tGI);
	tVData.add(mTransferData);

	//IndiDueFeePartUI tIndiDueFeePartUI = new IndiDueFeePartUI();
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	try {
		//if (!tIndiDueFeePartUI.submitData(tVData, "ZC")) {
		if (!tBusinessDelegate.submitData(tVData, "ZC","IndiDueFeePartUI")) {		
			//int n = tIndiDueFeePartUI.mErrors.getErrorCount();
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			Content = " 催收失败，原因是: ";
			for (int i = 0; i < n; i++) {
		Content = Content.trim()
				+ i
				+ ". "
				//+ tIndiDueFeePartUI.mErrors.getError(i).errorMessage
				+ tBusinessDelegate.getCErrors().getError(i).errorMessage
				.trim() + ".";
			}
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr != "Fail") {
			//tError = tIndiDueFeePartUI.mErrors;
			tError = tBusinessDelegate.getCErrors();
			if (!tError.needDealError()) {
		Content = " 催收成功! ";
		FlagStr = "Succ";
			} else {
		Content = " 催收失败，原因是:";
		int n = tError.getErrorCount();
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				//tError = tErrors.getError(i);
				Content = Content.trim()
				+ i
				+ ". "
				+ tError.getError(i).errorMessage
				.trim() + ".";
			}
		}
		FlagStr = "Fail";
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
		Content = Content.trim() + ".提示：异常终止!";
	}

	tVData.clear();
	VData ttData = new VData();
	LCContSet tLCContSet = new LCContSet();
	//ttData = tIndiDueFeePartUI.getLCResult();
	ttData = tBusinessDelegate.getResult();
	tLCContSet.set((LCContSet) ttData.getObjectByObjectName(
			"LCContSet", 0));

	//暂交费表得到纪录数据：
	//得到符合查询条件的纪录的数目，后面循环时用到
	int recordCount = tLCContSet.size();
	if (recordCount > 0) {
		String strRecord = "0|" + recordCount + "^";
		strRecord = strRecord + tLCContSet.encode();
%>
<script language="javascript">
        //调用js文件中显示数据的函数
        parent.fraInterface.showRecord("<%=strRecord%>"); 
        </script>
<%
}
%>

<html>
<script language="javascript">  	
     parent.fraInterface.afterSubmit1("<%=FlagStr%>","<%=Content%>");     
</script>
</html>
