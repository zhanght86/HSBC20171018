
<%
	//�������ƣ�IndiDueFeeQueryDate.jsp
	//�����ܣ����˱��Ѵ��գ�ʵ�����ݴӱ������Ӧ�ո��˱��Ӧ���ܱ����ת
	//�������ڣ�2002-07-24 
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
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

	String mOperator =""; //�������ͣ�ZCΪ""��GQΪ "GQ"
	ExeSQL check_ExeSQL = new ExeSQL();
	
	LDSysVarDB tLDSysVarDB = new LDSysVarDB();
	tLDSysVarDB.setSysVar("aheaddays");
	String AheadDays ="";
	if (tLDSysVarDB.getInfo() == false) {
		AheadDays = "60"; //Ĭ��Ϊ60��
	} else {
		AheadDays = tLDSysVarDB.getSysVarValue();
	}
	//������
	tLDSysVarDB = new LDSysVarDB();
	tLDSysVarDB.setSysVar("ExtendLapseDates");
	String ExtendDays ="";
	if (tLDSysVarDB.getInfo() == false) {
		ExtendDays = "0"; //Ĭ��Ϊ0��
	} else {
		ExtendDays = tLDSysVarDB.getSysVarValue();
	}
	String mStartDate = "";
	String mEndDate = "";
	if("ZC".equals(mDealType)){ //�������գ�paytodate�ڵ�ǰ����ǰ��������֮�䣬��mStartDate<=paytodate<=mEndDate
		mOperator="ZC";
		mStartDate = PubFun.calDate(PubFun.getCurrentDate(),-(Integer.parseInt(AheadDays) + Integer.parseInt(ExtendDays)), "D", null);
		mEndDate = PubFun.calDate(PubFun.getCurrentDate(), Integer.parseInt(AheadDays), "D", null);
	}
	else if("GQ".equals(mDealType))
	{ //������գ�paytodate�ڵ�ǰ����������֮ǰ,��paytodate<=mEndDate,����Ҫ���������յ�mStartDate�������Թ�����ǰ��60��
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
	tGI = (GlobalInput) session.getValue("GI"); //�μ�loginSubmit.jsp
	if (tGI == null) {
		System.out.println("ҳ��ʧЧ,�����µ�½");
		FlagStr = "Fail";
		Content = "ҳ��ʧЧ,�����µ�½";
		return;
	}

	int recordCount = 0;
	double PayMoney = 0; //���ѽ��

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
		Content = " ���ղ�ѯʧ�ܣ�ԭ����: " + tIndiDueFeePartQuery.mErrors.getError(0).errorMessage;
			}
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr != "Fail") {
			tError = tIndiDueFeePartQuery.mErrors;
			System.out.println("tError.getErrorCount:"
			+ tError.getErrorCount());
			if (!tError.needDealError()) {
		Content = " ��ѯ�ɹ�! ";
		FlagStr = "Succ";
			} else {
		Content = " ��ѯʧ�ܣ�ԭ����:";
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
		Content = Content.trim() + ".��ʾ���쳣��ֹ!";
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
        //����js�ļ�����ʾ���ݵĺ���       
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
