
<%
	//�������ƣ�IndiDueFeeQuery.jsp
	//�����ܣ�
	//�������ڣ�
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--�û�У����-->
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
	//��ȡ������  
	String ContNo = request.getParameter("ContNo1");
	//�������ͣ�ZC�������գ�GQ���ڸ�������
	String mDealType = request.getParameter("DealType");
	String mOperator =""; //�������ͣ�ZCΪ""��GQΪ "GQ"
	
	String AheadDays ="";
	String sql="select SysVarValue from ldsysvar where sysvar='aheaddays'";
	TransferData sqlTransferData = new TransferData();
	  VData sqlVData = new VData();
			    sqlTransferData.setNameAndValue("SQL",sql);
			    sqlVData.add(sqlTransferData);
			  	BusinessDelegate tsqlBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  	  if(!tsqlBusinessDelegate.submitData(sqlVData,"getOneValue","ExeSQLUI"))
			  	  {    
			  		AheadDays = "60"; //Ĭ��Ϊ60��
			  	  }
			  	  else
			  	  {			  		
			  		AheadDays=(String)tsqlBusinessDelegate.getResult().get(0);
			  	  }
			  	
    //	������
	String ExtendDays ="";
	sql="select SysVarValue from ldsysvar where sysvar='ExtendLapseDates'";
	sqlTransferData = new TransferData();
	sqlVData = new VData();
	sqlTransferData.setNameAndValue("SQL",sql);
    sqlVData.add(sqlTransferData);
  	tsqlBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  	  if(!tsqlBusinessDelegate.submitData(sqlVData,"getOneValue","ExeSQLUI"))
  	  {    
  		ExtendDays = "0"; //Ĭ��Ϊ0��
  	  }
  	  else
  	  {			  		
  		ExtendDays=(String)tsqlBusinessDelegate.getResult().get(0);
  	  }
	
	String StartDate = "";
	String EndDate = "";
	if("ZC".equals(mDealType)){ //�������գ�paytodate�ڵ�ǰ����ǰ��������֮�䣬��mStartDate<=paytodate<=mEndDate
		mOperator="ZC";
		StartDate = PubFun.calDate(PubFun.getCurrentDate(),-(Integer.parseInt(AheadDays) + Integer.parseInt(ExtendDays)), "D", null);
		EndDate = PubFun.calDate(PubFun.getCurrentDate(), Integer.parseInt(AheadDays), "D", null);
	}else if("GQ".equals(mDealType)){ //������գ�paytodate�ڵ�ǰ����������֮ǰ,��paytodate<=mEndDate
		//add by xiongzh 09-7-3 ���������������������գ�enddateΪ��ǰ����ǰ��15��
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
				  		loggerDebug("IndiDueFeeQuery","��ѯʧ��");
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
	
	// �������
	CErrors tError = null;
	String FlagStr = "";
	String Content = "";

	GlobalInput tGI = new GlobalInput(); 
	tGI = (GlobalInput) session.getValue("GI"); //�μ�loginSubmit.jsp
	if (tGI == null) {
		loggerDebug("IndiDueFeeQuery","ҳ��ʧЧ,�����µ�½");
		FlagStr = "Fail";
		Content = "ҳ��ʧЧ,�����µ�½";
	} else //ҳ����Ч
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
			Content = "����ɹ���";
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
				Content = "����Ͷ����ǩ��ʧ�ܣ�ԭ����: " + strErr;
				FlagStr = "Fail";
				} else {
					Content += "��������Ϣ:" + strErr;
				}
		}

	} //ҳ����Ч��
%>
<html>
<script language="javascript">
     parent.fraInterface.afterSubmit1("<%=FlagStr%>","<%=Content%>");
</script>
<body>
</body>
</html>

