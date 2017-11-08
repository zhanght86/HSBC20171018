
<%
	//程序名称：IndiDueFeeQueryDate.jsp
	//程序功能：个人保费催收，实现数据从保费项表到应收个人表和应收总表的流转
	//创建日期：2002-07-24 
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.operfee.*"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	CErrors tError = null;
	String mDealType = request.getParameter("DealType");
	String mManageCom = request.getParameter("ManageCom");
	String mContNo = request.getParameter("ContNo");
	String mPrtNo = request.getParameter("PrtNo");
	String mRiskCode = request.getParameter("RiskCode");
	String mAgentCode = request.getParameter("AgentCode");
	String mSecPayMode = request.getParameter("SecPayMode");
	String mContType = request.getParameter("ContType");
	String mRnewUWFlag = request.getParameter("RnewUWFlag");

	String mOperator =""; //处理类型，ZC为""，GQ为 "GQ"
	ExeSQL check_ExeSQL = new ExeSQL();
	
	LDSysVarDB tLDSysVarDB = new LDSysVarDB();
	tLDSysVarDB.setSysVar("aheaddays");
	String AheadDays ="";
	if (tLDSysVarDB.getInfo() == false) {
		AheadDays = "60"; //默认为60天
	} else {
		AheadDays = tLDSysVarDB.getSysVarValue();
	}
	//宽限期
	tLDSysVarDB = new LDSysVarDB();
	tLDSysVarDB.setSysVar("ExtendLapseDates");
	String ExtendDays ="";
	if (tLDSysVarDB.getInfo() == false) {
		ExtendDays = "0"; //默认为0天
	} else {
		ExtendDays = tLDSysVarDB.getSysVarValue();
	}
	String mStartDate = "";
	String mEndDate = "";
	if("ZC".equals(mDealType)){ //正常催收，paytodate在当前日期前后两个月之间，即mStartDate<=paytodate<=mEndDate
		mOperator="ZC";
		mStartDate = PubFun.calDate(PubFun.getCurrentDate(),-(Integer.parseInt(AheadDays) + Integer.parseInt(ExtendDays)), "D", null);
		mEndDate = PubFun.calDate(PubFun.getCurrentDate(), Integer.parseInt(AheadDays), "D", null);
	}
	else if("GQ".equals(mDealType))
	{ //特殊催收，paytodate在当前日期两个月之前,即paytodate<=mEndDate,由于要和正常催收的mStartDate错开，所以过期往前推60天
		mOperator="GQ";
		String MainXB_flag="";
		String MainXB_sql = "select count(*) from LCPol a where AppFlag='1' "
            + "and PayToDate = PayEndDate "
            + "and RnewFlag = '-1' "
            + "and (StopFlag='0' or StopFlag is null) and GrpPolNo='00000000000000000000' "
            + "and contno='"+ mContNo +"' and polno=mainpolno "
            + "and exists (select 1 from lmrisk where riskcode=a.riskcode and rnewflag='Y') " ;
		int RnewCheck = Integer.parseInt( check_ExeSQL.getOneValue(MainXB_sql) );
		if(RnewCheck>0)
		{
			MainXB_flag="1";
			mStartDate = "";
			mEndDate = PubFun.calDate(PubFun.getCurrentDate(), -15, "D", null);		
		}
		else
		{
			mStartDate = "";
			mEndDate = PubFun.calDate(PubFun.getCurrentDate(), -Integer.parseInt(AheadDays)-1, "D", null);		
		}
	}
	System.out.println("mDealType==" + mDealType);
	System.out.println("AheadDays==" + AheadDays);
	System.out.println("ExtendDays==" + ExtendDays);
	System.out.println("mStartDate==" + mStartDate);
	System.out.println("mEndDate==" + mEndDate);
	
	String FlagStr = "";
	String Content = "";
	String strRecord = "";

	int aheaddays = 63;
	String mSubDay;
	PubFun date = new PubFun();
	FDate tTranferDate = new FDate();
	LCContSchema tLCContSchema = new LCContSchema();
	LCContSet tLCContSet = new LCContSet();

	GlobalInput tGI = new GlobalInput(); //repair:
	tGI = (GlobalInput) session.getValue("GI"); //参见loginSubmit.jsp
	if (tGI == null) {
		System.out.println("页面失效,请重新登陆");
		FlagStr = "Fail";
		Content = "页面失效,请重新登陆";
		return;
	}

	int recordCount = 0;
	double PayMoney = 0; //交费金额

	TransferData mTransferData = new TransferData();
	mTransferData.setNameAndValue("MangeCom", mManageCom);
	mTransferData.setNameAndValue("Contno", mContNo);
	mTransferData.setNameAndValue("Prtno", mPrtNo);
	mTransferData.setNameAndValue("StartDate", mStartDate);
	mTransferData.setNameAndValue("EndDate", mEndDate);
	mTransferData.setNameAndValue("RiskCode", mRiskCode);
	mTransferData.setNameAndValue("AgentCode", mAgentCode);
	mTransferData.setNameAndValue("SecPayMode", mSecPayMode);
	mTransferData.setNameAndValue("ContType", mContType);
	mTransferData.setNameAndValue("RnewUWFlag", mRnewUWFlag);
	
	VData tVData = new VData();
	tVData.add(tGI);
	tVData.add(mTransferData);

	IndiDueFeePartQuery tIndiDueFeePartQuery = new IndiDueFeePartQuery();
	try {
		if (!tIndiDueFeePartQuery.submitData(tVData, mOperator)) {
			int n = tIndiDueFeePartQuery.mErrors.getErrorCount();
			for (int i = 0; i < n; i++) {
		System.out.println("Error: "+ tIndiDueFeePartQuery.mErrors.getError(i).errorMessage);
		Content = " 催收查询失败，原因是: " + tIndiDueFeePartQuery.mErrors.getError(0).errorMessage;
			}
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr != "Fail") {
			tError = tIndiDueFeePartQuery.mErrors;
			System.out.println("tError.getErrorCount:"
			+ tError.getErrorCount());
			if (!tError.needDealError()) {
		Content = " 查询成功! ";
		FlagStr = "Succ";
			} else {
		Content = " 查询失败，原因是:";
		int n = tError.getErrorCount();
		if (n > 0) {
			for (int i = 0; i < n; i++) {
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

	//tVData.clear();
	if (FlagStr != "Fail") 
	{
		tVData = tIndiDueFeePartQuery.getResult();
		String[][] Result ;
		Result = (String[][]) tVData.get(0);
		recordCount = Result.length;
		System.out.println("recordCount=" + recordCount);
		String tQueryResult1 = "";
		tQueryResult1 = "0|"+Result.length;
	   for(int i=0;i<Result.length;i++)
	   {
	   		String tTemp = "";
				for(int n=0;n<4;n++)
				{
				   if(n==0)
				     tTemp = "^"+Result[i][n];
				   else 
					 {
					   tTemp = tTemp + "|"+	Result[i][n];
					 }  
				}
	      tQueryResult1 = tQueryResult1 + 	tTemp;
	   }
	 // System.out.println(tQueryResult1);  
%>
<html>
<script language="javascript">
        //调用js文件中显示数据的函数       
     parent.fraInterface.showRecord("<%=tQueryResult1%>"); 	
		parent.fraInterface.getTime("<%=tIndiDueFeePartQuery.getStartTime()%>","<%=tIndiDueFeePartQuery.getSubTime()%>"); 
	</script>
<%
}
%>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");	
</script>
</html>
