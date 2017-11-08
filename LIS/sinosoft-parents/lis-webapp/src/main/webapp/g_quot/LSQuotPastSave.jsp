<%
/***************************************************************
 * <p>ProName：LSQuotPastSave.jsp</p>
 * <p>Title：既往信息</p>
 * <p>Description：既往信息</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-20
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LSQuotPastSchema"%>
<%@page import="com.sinosoft.lis.schema.LSQuotPastDetailSchema"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			
			String tOperate = request.getParameter("Operate");
			
			String tQuotType = request.getParameter("QuotType");
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			String tActivityID = request.getParameter("ActivityID");
			
			String tTotalSerialNo = request.getParameter("TotalSerialNo");
			String tInsuYear = request.getParameter("InsuYear");
			String tInsuranceCom = request.getParameter("InsuranceCom");
			String tSumPrem = request.getParameter("SumPrem");
			String tSumLoss = request.getParameter("SumLoss");
			String tSumClaimPeople = request.getParameter("SumClaimPeople");
			String tMaleRate = request.getParameter("MaleRate");
			String tFemaleRate = request.getParameter("FemaleRate");
			
			String tDetailSerialNo = request.getParameter("DetailSerialNo");
			String tGrpContNo = request.getParameter("GrpContNo");
			String tValDate = request.getParameter("ValDate");
			String tEndDate = request.getParameter("EndDate");
			String tNonMedSumPrem = request.getParameter("NonMedSumPrem");
			String tNonMedSumLoss = request.getParameter("NonMedSumLoss");
			String tNonMedPeople = request.getParameter("NonMedPeople");
			String tNonMedClmPeople = request.getParameter("NonMedClmPeople");
			String tMedSumPrem = request.getParameter("MedSumPrem");
			String tMedSumLoss = request.getParameter("MedSumLoss");
			String tMedPeople = request.getParameter("MedPeople");
			String tMedClmPeople = request.getParameter("MedClmPeople");
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("QuotType", tQuotType);
			tTransferData.setNameAndValue("ActivityID", tActivityID);
			
			LSQuotPastSchema tLSQuotPastSchema = new LSQuotPastSchema();
			
			tLSQuotPastSchema.setSerialNo(tTotalSerialNo);
			tLSQuotPastSchema.setQuotNo(tQuotNo);
			tLSQuotPastSchema.setQuotBatNo(tQuotBatNo);
			tLSQuotPastSchema.setInsuYear(tInsuYear);
			tLSQuotPastSchema.setInsuranceCom(tInsuranceCom);
			tLSQuotPastSchema.setSumPrem(tSumPrem);
			tLSQuotPastSchema.setSumLoss(tSumLoss);
			tLSQuotPastSchema.setSumClaimPeople(tSumClaimPeople);
			tLSQuotPastSchema.setMaleRate(tMaleRate);
			tLSQuotPastSchema.setFemaleRate(tFemaleRate);
			
			LSQuotPastDetailSchema tLSQuotPastDetailSchema = new LSQuotPastDetailSchema();
			
			tLSQuotPastDetailSchema.setSerialNo(tDetailSerialNo);
			tLSQuotPastDetailSchema.setQuotNo(tQuotNo);
			tLSQuotPastDetailSchema.setQuotBatNo(tQuotBatNo);
			tLSQuotPastDetailSchema.setInsuYear(tInsuYear);
			tLSQuotPastDetailSchema.setGrpContNo(tGrpContNo);
			tLSQuotPastDetailSchema.setValDate(tValDate);
			tLSQuotPastDetailSchema.setEndDate(tEndDate);
			tLSQuotPastDetailSchema.setNonMedSumPrem(tNonMedSumPrem);
			tLSQuotPastDetailSchema.setNonMedSumLoss(tNonMedSumLoss);
			tLSQuotPastDetailSchema.setNonMedPeople(tNonMedPeople);
			tLSQuotPastDetailSchema.setNonMedClmPeople(tNonMedClmPeople);
			tLSQuotPastDetailSchema.setMedSumPrem(tMedSumPrem);
			tLSQuotPastDetailSchema.setMedSumLoss(tMedSumLoss);
			tLSQuotPastDetailSchema.setMedPeople(tMedPeople);
			tLSQuotPastDetailSchema.setMedClmPeople(tMedClmPeople);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLSQuotPastSchema);
			tVData.add(tLSQuotPastDetailSchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotPastUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "处理成功！";
				tFlagStr = "Succ";
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "处理异常，请联系系统运维人员！";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>
