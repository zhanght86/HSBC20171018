
<%
	//�������ƣ�IndiDueFeePartInputQuery.jsp
	//�����ܣ�
	//�������ڣ�
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
	//
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--�û�У����-->
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
	//��ȡ�ύҳ��Ԫ��
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
			  		AheadDays = "60"; //Ĭ��Ϊ60��
			  	  }
			  	  else
			  	  {			  		
			  		AheadDays=(String)tsqlBusinessDelegate.getResult().get(0);
			  	  }
	//������
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
	String StartDate = PubFun.calDate(PubFun.getCurrentDate(),-(Integer.parseInt(AheadDays) + Integer.parseInt(ExtendDays)), "D", null);
	//String StartDate =""; //������Ҫ����������ȥ����
	String EndDate = PubFun.calDate(PubFun.getCurrentDate(), Integer.parseInt(AheadDays), "D", null);
	
	//�������
	CErrors tError = null;
	String FlagStr = "";
	String Content = "";

	GlobalInput tGI = new GlobalInput(); //repair
	tGI = (GlobalInput) session.getValue("GI");
	if (tGI == null) {
		loggerDebug("IndiDueFeePartInputQuery","ҳ��ʧЧ�������µ�½");
		FlagStr = "Fail";
		Content = "ҳ��ʧЧ�������µ�½";
		return;
	}
	//����������̨��ֵ
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
			Content = " ����ʧ�ܣ�ԭ����: ";
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
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr != "Fail") {
			//tError = tIndiDueFeePartUI.mErrors;
			tError = tBusinessDelegate.getCErrors();
			if (!tError.needDealError()) {
		Content = " ���ճɹ�! ";
		FlagStr = "Succ";
			} else {
		Content = " ����ʧ�ܣ�ԭ����:";
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
		Content = Content.trim() + ".��ʾ���쳣��ֹ!";
	}

	tVData.clear();
	VData ttData = new VData();
	LCContSet tLCContSet = new LCContSet();
	//ttData = tIndiDueFeePartUI.getLCResult();
	ttData = tBusinessDelegate.getResult();
	tLCContSet.set((LCContSet) ttData.getObjectByObjectName(
			"LCContSet", 0));

	//�ݽ��ѱ�õ���¼���ݣ�
	//�õ����ϲ�ѯ�����ļ�¼����Ŀ������ѭ��ʱ�õ�
	int recordCount = tLCContSet.size();
	if (recordCount > 0) {
		String strRecord = "0|" + recordCount + "^";
		strRecord = strRecord + tLCContSet.encode();
%>
<script language="javascript">
        //����js�ļ�����ʾ���ݵĺ���
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
