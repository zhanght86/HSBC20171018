<%
/***************************************************************
 * <p>ProName：LSQuotFeeSave.jsp</p>
 * <p>Title：费用信息</p>
 * <p>Description：费用信息</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-18
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LSQuotFeeSchema"%>
<%@page import="com.sinosoft.lis.schema.LSQuotChargeFeeDetailSchema"%>
<%@page import="com.sinosoft.lis.schema.LSQuotOtherFeeSchema"%>
<%@page import="com.sinosoft.lis.vschema.LSQuotFeeSet"%>
<%@page import="com.sinosoft.lis.vschema.LSQuotChargeFeeDetailSet"%>
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
			
			LSQuotFeeSet tLSQuotFeeSet = new LSQuotFeeSet();
			//String tRiskFeeGridNo[] = request.getParameterValues("RiskFeeGridNo");
			String tRiskFeeGridSel[] = request.getParameterValues("InpRiskFeeGridSel");
			if (tRiskFeeGridSel!=null && tRiskFeeGridSel.length>0) {
				
				String tRiskCode[] = request.getParameterValues("RiskFeeGrid1");
				String tStandCommRate[] = request.getParameterValues("RiskFeeGrid3");
				String tExpectCommRate[] = request.getParameterValues("RiskFeeGrid4");
				String tStandChargeRate[] = request.getParameterValues("RiskFeeGrid5");
				String tExpectChargeRate[] = request.getParameterValues("RiskFeeGrid6");
				String tStandBusiFeeRate[] = request.getParameterValues("RiskFeeGrid7");
				String tExpectBusiFeeRate[] = request.getParameterValues("RiskFeeGrid8");
				String tPreLossRatio[] = request.getParameterValues("RiskFeeGrid9");
				String tPoolRate[] = request.getParameterValues("RiskFeeGrid10");
				String tTaxFeeRate[] = request.getParameterValues("RiskFeeGrid11");
				String tManageFeeRate[] = request.getParameterValues("RiskFeeGrid12");
				for (int i=0; i<tRiskFeeGridSel.length; i++) {
				
					if ("1".equals(tRiskFeeGridSel[i])) {
						
						LSQuotFeeSchema tLSQuotFeeSchema = new LSQuotFeeSchema();
						
						tLSQuotFeeSchema.setQuotNo(tQuotNo);
						tLSQuotFeeSchema.setQuotBatNo(tQuotBatNo);
						tLSQuotFeeSchema.setRiskCode(tRiskCode[i]);
						tLSQuotFeeSchema.setStandCommRate(tStandCommRate[i]);
						tLSQuotFeeSchema.setExpectCommRate(tExpectCommRate[i]);
						tLSQuotFeeSchema.setStandChargeRate(tStandChargeRate[i]);
						tLSQuotFeeSchema.setExpectChargeRate(tExpectChargeRate[i]);
						tLSQuotFeeSchema.setStandBusiFeeRate(tStandBusiFeeRate[i]);
						tLSQuotFeeSchema.setExpectBusiFeeRate(tExpectBusiFeeRate[i]);
						tLSQuotFeeSchema.setPreLossRatio(tPreLossRatio[i]);
						tLSQuotFeeSchema.setPoolRate(tPoolRate[i]);
						tLSQuotFeeSchema.setTaxFeeRate(tTaxFeeRate[i]);
						tLSQuotFeeSchema.setManageFeeRate(tManageFeeRate[i]);
						
						tLSQuotFeeSet.add(tLSQuotFeeSchema);
					}
				}
			}
			/**删除中介机构相关信息
			LSQuotChargeFeeDetailSet tLSQuotChargeFeeDetailSet = new LSQuotChargeFeeDetailSet();
			String tChargeFeeGridNo[] = request.getParameterValues("ChargeFeeGridNo");
			if (tChargeFeeGridNo!=null && tChargeFeeGridNo.length>0) {
				
				String tRiskCode = request.getParameter("RiskCode");
				String tAgencyName[] = request.getParameterValues("ChargeFeeGrid1");
				String tExpectChargeRate[] = request.getParameterValues("ChargeFeeGrid2");
				
				for (int i=0; i<tChargeFeeGridNo.length; i++) {
				
					if (tAgencyName[i]!=null && !"".equals(tAgencyName[i])) {
						
						LSQuotChargeFeeDetailSchema tLSQuotChargeFeeDetailSchema = new LSQuotChargeFeeDetailSchema();
						
						tLSQuotChargeFeeDetailSchema.setQuotNo(tQuotNo);
						tLSQuotChargeFeeDetailSchema.setQuotBatNo(tQuotBatNo);
						tLSQuotChargeFeeDetailSchema.setRiskCode(tRiskCode);
						tLSQuotChargeFeeDetailSchema.setAgencyName(tAgencyName[i]);
						tLSQuotChargeFeeDetailSchema.setStandChargeRate(-1);
						tLSQuotChargeFeeDetailSchema.setExpectChargeRate(tExpectChargeRate[i]);
						
						tLSQuotChargeFeeDetailSet.add(tLSQuotChargeFeeDetailSchema);
					}
				}
			}
			**/
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("QuotType", tQuotType);
			tTransferData.setNameAndValue("QuotNo", tQuotNo);
			tTransferData.setNameAndValue("QuotBatNo", tQuotBatNo);
			tTransferData.setNameAndValue("ActivityID", tActivityID);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLSQuotFeeSet);
			//tVData.add(tLSQuotChargeFeeDetailSet);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotFeeUI")) {
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
