<%
/***************************************************************
 * <p>ProName��LSQuotCountProfitSave.jsp</p>
 * <p>Title�����ղ���</p>
 * <p>Description���������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-10-15
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
			tOperate = request.getParameter("Operate");
			
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			String tQuotType = request.getParameter("QuotType");
			
			String tRiskCode = request.getParameter("RiskCode");
			String tDutyCode = request.getParameter("DutyCode");
			String tInitPrem = request.getParameter("InitPrem");
			String tMangFeeType = request.getParameter("MangFeeType");
			String tInitMangFee = request.getParameter("InitMangFee");
			String tMonthFeeType = request.getParameter("MonthFeeType");
			String tMonthFee = request.getParameter("MonthFee");
			String tProfit = request.getParameter("Profit");
			String tYears = request.getParameter("Years");
			
			TransferData tTransferData = new TransferData();
			
			tTransferData.setNameAndValue("QuotNo", tQuotNo);
			tTransferData.setNameAndValue("QuotBatNo", tQuotBatNo);
			tTransferData.setNameAndValue("RiskCode", tRiskCode);
			tTransferData.setNameAndValue("DutyCode", tDutyCode);
			tTransferData.setNameAndValue("InitPrem", tInitPrem);
			tTransferData.setNameAndValue("MangFeeType", tMangFeeType);
			tTransferData.setNameAndValue("InitMangFee", tInitMangFee);
			tTransferData.setNameAndValue("MonthFeeType", tMonthFeeType);
			tTransferData.setNameAndValue("MonthFee", tMonthFee);
			tTransferData.setNameAndValue("Profit", tProfit);
			tTransferData.setNameAndValue("Years", tYears);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotCountProfitUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
			
				String tReturn = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 1);
				
				tFlagStr = "Succ";
				tContent = tReturn;
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "�����쳣������ϵϵͳ��ά��Ա��";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>
