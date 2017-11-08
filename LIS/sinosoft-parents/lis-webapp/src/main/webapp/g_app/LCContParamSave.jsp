<%
/***************************************************************
 * <p>ProName：LCContParamSave.jsp</p>
 * <p>Title：产品参数信息维护--管理费维护</p>
 * <p>Description：产品参数信息维护--管理费维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-05-09
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LCGrpFeeSchema"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			tOperate = request.getParameter("Operate");
			
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tBussNo = request.getParameter("BussNo");//
			String tBussType = request.getParameter("BussType");//
			String tContPlanType = request.getParameter("ContPlanType");//
			
			String tRiskCode = request.getParameter("RiskCode");//险种
			String tAccType = request.getParameter("AccType");//账户类型
			String tFeeType = request.getParameter("FeeType");//管理费类型
			
			String tDeductType = request.getParameter("DeductType");//初始管理费扣除方式
			String tFeeValue = request.getParameter("FeeValue");//初始管理费(元)/比例
			String tMonthFeeType = request.getParameter("MonthFeeType");//月度管理费类型
			String tMonthValue = request.getParameter("MonthValue");//月度管理费(元)/比例
			String tYearFeeType = request.getParameter("YearFeeType");//年度管理费类型
			String tYearStartValue = request.getParameter("YearStartValue");//年度起始值
			String tYearEndValue = request.getParameter("YearEndValue");//年度终止值
			String tYearValue = request.getParameter("YearValue");//年度管理费(元)/比例
			
			LCGrpFeeSchema tLCGrpFeeSchema = new LCGrpFeeSchema();
			
			if ("INSERT".equals(tOperate) || "UPDATE".equals(tOperate) || "DELETE".equals(tOperate)) {
				
				tLCGrpFeeSchema.setGrpContNo(tBussNo);
				tLCGrpFeeSchema.setGrpPolNo(tBussNo);
				tLCGrpFeeSchema.setRiskCode(tRiskCode);
				tLCGrpFeeSchema.setInsuAccNo(tAccType);
				tLCGrpFeeSchema.setFeeCode(tFeeType);
					
				if ("0".equals(tFeeType)) {//管理费类型:0-初始管理费

					tLCGrpFeeSchema.setFeeCalCode(tDeductType);
					tLCGrpFeeSchema.setFeeValue(tFeeValue);
				} else if ("1".equals(tFeeType)) {//管理费类型:1-月度管理费
					
					tLCGrpFeeSchema.setFeeCalCode(tMonthFeeType);
					tLCGrpFeeSchema.setFeeValue(tMonthValue);
				} else if ("2".equals(tFeeType)) {//管理费类型:2-年度管理费
					
					tLCGrpFeeSchema.setFeeCalCode(tYearFeeType);
					tLCGrpFeeSchema.setFeeValue(tYearValue);
					tLCGrpFeeSchema.setValStartDate(tYearStartValue);
					tLCGrpFeeSchema.setValEndDate(tYearEndValue);
				}
				
			}
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("MissionID", tMissionID);
			tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
			tTransferData.setNameAndValue("ActivityID", tActivityID);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLCGrpFeeSchema);
		
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LCGrpParamUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tFlagStr = "Succ";
				tContent = "处理成功！";
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
