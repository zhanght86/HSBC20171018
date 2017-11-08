
<%
	//程序名称：IndiDueFeeQuery.jsp
	//程序功能：
	//创建日期：
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--用户校验类-->
<%@page import="java.util.*"%>
<%@page import="java.lang.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.operfee.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
	//获取保单号  
	String ContNo = request.getParameter("ContNo1");
	//催收类型，ZC正常催收，GQ过期个单催收
	String mDealType = request.getParameter("DealType");
	String mOperator =""; //处理类型，ZC为""，GQ为 "GQ"
	
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
			  	
    //	宽限期
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
	
	String StartDate = "";
	String EndDate = "";
	if("ZC".equals(mDealType)){ //正常催收，paytodate在当前日期前后两个月之间，即mStartDate<=paytodate<=mEndDate
		mOperator="ZC";
		StartDate = PubFun.calDate(PubFun.getCurrentDate(),-(Integer.parseInt(AheadDays) + Integer.parseInt(ExtendDays)), "D", null);
		EndDate = PubFun.calDate(PubFun.getCurrentDate(), Integer.parseInt(AheadDays), "D", null);
	}else if("GQ".equals(mDealType)){ //特殊催收，paytodate在当前日期两个月之前,即paytodate<=mEndDate
		//add by xiongzh 09-7-3 如果是主险续保，特殊催收，enddate为当前日期前推15天
		mOperator="GQ";
		String MainXB_flag="";
		String MainXB_sql = "select count(*) from LCPol a where AppFlag='1' "
            + "and PayToDate = PayEndDate "
            + "and RnewFlag = '-1' "
            + "and (StopFlag='0' or StopFlag is null) and GrpPolNo='00000000000000000000' "
            + "and contno='"+ ContNo +"' and polno=mainpolno "
            + "and exists (select 1 from lmrisk where riskcode=a.riskcode and rnewflag='Y') " ;
		String resultS="";
		sqlTransferData = new TransferData();
		  sqlVData = new VData();
				    sqlTransferData.setNameAndValue("SQL",MainXB_sql);
				    sqlVData.add(sqlTransferData);
				  	tsqlBusinessDelegate=BusinessDelegate.getBusinessDelegate();
				  	  if(!tsqlBusinessDelegate.submitData(sqlVData,"getOneValue","ExeSQLUI"))
				  	  {    
				  		loggerDebug("IndiDueFeeQuery","查询失败");
				  	  }
				  	  else
				  	  {			  		
				  		resultS=(String)tsqlBusinessDelegate.getResult().get(0);
				  	  }
		int RnewCheck = Integer.parseInt(resultS);
		if(RnewCheck>0)
		{
			MainXB_flag="1";
			StartDate = "";
			EndDate = PubFun.calDate(PubFun.getCurrentDate(), -15, "D", null);		
			
		}
		else
		{
			StartDate = "";
			EndDate = PubFun.calDate(PubFun.getCurrentDate(), -Integer.parseInt(AheadDays)-1, "D", null);		
		}
	
		
	}
	
	// 输出参数
	CErrors tError = null;
	String FlagStr = "";
	String Content = "";

	GlobalInput tGI = new GlobalInput(); 
	tGI = (GlobalInput) session.getValue("GI"); //参见loginSubmit.jsp
	if (tGI == null) {
		loggerDebug("IndiDueFeeQuery","页面失效,请重新登陆");
		FlagStr = "Fail";
		Content = "页面失效,请重新登陆";
	} else //页面有效
	{
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StartDate", StartDate);
		tTransferData.setNameAndValue("EndDate", EndDate);
		tTransferData.setNameAndValue("Contno", ContNo);		
		loggerDebug("IndiDueFeeQuery","StartDate==" + StartDate);
		loggerDebug("IndiDueFeeQuery","EndDate==" + EndDate);
		loggerDebug("IndiDueFeeQuery","ContNo==" + ContNo);
		
		VData tVData = new VData();
		tVData.add(tGI);
		tVData.add(tTransferData);
		
		//IndiDueFeePartUI tIndiDueFeePartUI = new IndiDueFeePartUI();
	  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		//tIndiDueFeePartUI.submitData(tVData, mOperator);
		tBusinessDelegate.submitData(tVData,mOperator,"IndiDueFeePartUI");
		
		//tError = tIndiDueFeePartUI.mErrors;
		tError = tBusinessDelegate.getCErrors();
		int n = tError.getErrorCount();
		if (!tError.needDealError()) {
			Content = "处理成功！";
			FlagStr = "Succ";
			loggerDebug("IndiDueFeeQuery",Content + FlagStr);
		} else {
			String strErr = "";
			for (int t = 0; t < n; t++) {
				strErr += (t + 1)
				+ ": "
				+ tBusinessDelegate.getCErrors().getError(t).errorMessage
				+ "; ";
			}
			if (FlagStr.equals("Succ")) {
				Content = "部分投保单签单失败，原因是: " + strErr;
				FlagStr = "Fail";
				} else {
					Content += "有如下信息:" + strErr;
				}
		}

	} //页面有效区
%>
<html>
<script language="javascript">
     parent.fraInterface.afterSubmit1("<%=FlagStr%>","<%=Content%>");
</script>
<body>
</body>
</html>

