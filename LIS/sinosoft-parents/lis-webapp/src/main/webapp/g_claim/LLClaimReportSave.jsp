<%
/***************************************************************
 * <p>ProName：LLClaimReportSave.jsp</p>
 * <p>Title：报案录入界面</p>
 * <p>Description：报案录入界面</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LLReportSchema"%>
<%@page import="com.sinosoft.lis.schema.LLSubReportSchema"%>
<%@page import="com.sinosoft.lis.schema.LLAccidentSchema"%>
<%@page import="com.sinosoft.lis.schema.LLCaseReportDetailSchema"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tRptNo = "";
	String tCaseNo = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			
			String tOperate = request.getParameter("Operate");
			
			LLReportSchema tLLReportSchema = new LLReportSchema();
			tLLReportSchema.setRptNo(request.getParameter("RptNo"));
			tLLReportSchema.setRgtClass(request.getParameter("RgtClass"));
			tLLReportSchema.setRgtObj(request.getParameter("RgtClass"));
			tLLReportSchema.setAppntType(request.getParameter("AppntType"));
			tLLReportSchema.setAppntNo(request.getParameter("AppntNo"));
			tLLReportSchema.setGrpName(request.getParameter("AppntName"));
			tLLReportSchema.setRptorName(request.getParameter("RptName"));
			tLLReportSchema.setRptorPhone(request.getParameter("RptPhone"));
			tLLReportSchema.setRptMode(request.getParameter("RptMode"));
			tLLReportSchema.setRelation(request.getParameter("Relation"));
			tLLReportSchema.setRptDate(request.getParameter("RptDate"));
			tLLReportSchema.setRgtFlag("0");
			tLLReportSchema.setRgtState("0");
			tLLReportSchema.setAvaiFlag("10");
			tLLReportSchema.setMngCom(request.getParameter("RptCom"));
			
			LLSubReportSchema tLLSubReportSchema = new LLSubReportSchema();
			tLLSubReportSchema.setSubRptNo(request.getParameter("RptNo"));
			tLLSubReportSchema.setAppntNo(request.getParameter("SelfAppntNo"));
			tLLSubReportSchema.setAppntName(request.getParameter("SelfAppntName"));
			tLLSubReportSchema.setCustomerNo(request.getParameter("CustomerNo"));
			tLLSubReportSchema.setCustomerName(request.getParameter("CustomerName"));
			tLLSubReportSchema.setBirthday(request.getParameter("Birthday"));
			tLLSubReportSchema.setEmpNo(request.getParameter("EmpNo"));
			tLLSubReportSchema.setIDNo(request.getParameter("IDNo"));
			tLLSubReportSchema.setIDType(request.getParameter("IDType"));
			tLLSubReportSchema.setSex(request.getParameter("Gender"));
			tLLSubReportSchema.setRemark(request.getParameter("CustomerRemarks"));
			
			LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema();
			tLLAccidentSchema.setRptNo(request.getParameter("RptNo"));
			tLLAccidentSchema.setCustomerNo(request.getParameter("CustomerNo"));
			tLLAccidentSchema.setAccNo(request.getParameter("AccNo"));
			tLLAccidentSchema.setAccReason(request.getParameter("AccReason"));
			tLLAccidentSchema.setAccDate(request.getParameter("AccDate"));
			tLLAccidentSchema.setHospitalCode(request.getParameter("HospitalCode"));
			tLLAccidentSchema.setHospitalName(request.getParameter("HospitalName"));
			tLLAccidentSchema.setClaimPay(request.getParameter("ClaimPay"));
			tLLAccidentSchema.setAccGrade(request.getParameter("AccGrade"));
/* 			tLLAccidentSchema.setAccProvince(request.getParameter("AccProvince"));
			tLLAccidentSchema.setAccCity(request.getParameter("AccCity"));
			tLLAccidentSchema.setAccCounty(request.getParameter("AccCounty")); */
			tLLAccidentSchema.setAccPlace(request.getParameter("AccSite"));
			tLLAccidentSchema.setAccDesc(request.getParameter("AccDesc"));
			tLLAccidentSchema.setRemark(request.getParameter("CaseRemark"));
			
			String tClaimReason = request.getParameter("AccReason");
			
			String tClaimTypeDetail = "";			
			String tClaimType[] = request.getParameterValues("ClaimType");
			if (tClaimType!=null && tClaimType.length>=1) {
				
			  	for (int i = 0; i < tClaimType.length; i++){
			  		
			  		if (i==tClaimType.length-1) {
			  			tClaimTypeDetail = tClaimTypeDetail+tClaimReason+tClaimType[i];
			  		} else {
						tClaimTypeDetail = tClaimTypeDetail+tClaimReason+tClaimType[i]+",";	
			  		}												
			  	}
			}
			tLLAccidentSchema.setAccType(tClaimTypeDetail);
			String tMajorFlag = request.getParameter("MajorFlag");
			System.out.println(tMajorFlag+"------");
 			LLCaseReportDetailSchema tLLCaseReportDetailSchema = new LLCaseReportDetailSchema();			
			if ("MAJORCONFIRM".equals(tOperate)) {
						
				if ("2".equals(tMajorFlag)) {
				
					tLLCaseReportDetailSchema.setRptNo(request.getParameter("RptNo"));
					tLLCaseReportDetailSchema.setCustomerNo(request.getParameter("CustomerNo"));
					tLLCaseReportDetailSchema.setRepOperator(request.getParameter("RepInputOperator"));
					tLLCaseReportDetailSchema.setRepDate(request.getParameter("RepInputDate"));
					tLLCaseReportDetailSchema.setRepManageCom(request.getParameter("RepInputCom"));
					tLLCaseReportDetailSchema.setRepOpinion(request.getParameter("RepInputRemarks"));
					tLLCaseReportDetailSchema.setRepType("2");			
				} else if ("3".equals(tMajorFlag)) {
					
					tLLCaseReportDetailSchema.setRptNo(request.getParameter("RptNo"));
					tLLCaseReportDetailSchema.setCustomerNo(request.getParameter("CustomerNo"));
					tLLCaseReportDetailSchema.setRepOperator(request.getParameter("RepInputOperator1"));
					tLLCaseReportDetailSchema.setRepDate(request.getParameter("RepInputDate1"));
					tLLCaseReportDetailSchema.setRepManageCom(request.getParameter("RepInputCom1"));
					tLLCaseReportDetailSchema.setRepOpinion(request.getParameter("RepInputRemarks1"));
					tLLCaseReportDetailSchema.setRepType("3");					
				}			
			}
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLLReportSchema);
			tVData.add(tLLSubReportSchema);
			tVData.add(tLLAccidentSchema);
			tVData.add(tLLCaseReportDetailSchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLClaimReportUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				
				TransferData tTransferData = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData",0);
				if (tOperate.contains("REPORT")) {
					tRptNo = (String)tTransferData.getValueByName("RptNo");
				} else if (tOperate.contains("CASE")) {
					tCaseNo = (String)tTransferData.getValueByName("CaseNo");
				}
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
	parent.fraInterface.afterSubmit("<%=tFlagStr%>","<%=tContent%>","<%=tRptNo%>");
</script>
</html>
