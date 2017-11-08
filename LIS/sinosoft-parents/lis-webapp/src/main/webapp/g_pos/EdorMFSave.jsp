<%
/***************************************************************
 * <p>ProName：EdorMFSave.jsp</p>
 * <p>Title：长险费用变更</p>
 * <p>Description：长险费用变更</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-08-16
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LPGrpFeeSchema"%>
<%@page import="com.sinosoft.lis.schema.LPGetCalModeSchema"%>
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
			String tGrpContNo = request.getParameter("GrpContNo");
			String tEdorType = request.getParameter("EdorType");
			String tEdorNo = request.getParameter("EdorNo");
			String tEdorAppNo = request.getParameter("EdorAppNo");
			
			String tSerialNo = request.getParameter("SerialNo");//流水号
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
			
			String tRiskCode2 = request.getParameter("RiskCode2");//险种
			String tGetType = request.getParameter("GetType2");//退费类型
			String tValPeriod = request.getParameter("ValPeriod");//单位
			String tValStartDate = request.getParameter("ValStartDate");//起始值
			String tValEndDate = request.getParameter("ValEndDate");//终止值
			String tFeeValues = request.getParameter("FeeValues");//费用比例

			LPGetCalModeSchema tLPGetCalModeSchema = new LPGetCalModeSchema();
			LPGrpFeeSchema tLPGrpFeeSchema = new LPGrpFeeSchema();
			if ("INSERT1".equals(tOperate) || "UPDATE1".equals(tOperate) ) {
				
				tLPGetCalModeSchema.setSerialNo(tSerialNo);
				tLPGetCalModeSchema.setGrpContNo(tGrpContNo);
				tLPGetCalModeSchema.setEdorNo(tEdorNo);
				tLPGetCalModeSchema.setEdorType(tEdorType);
				tLPGetCalModeSchema.setRiskCode(tRiskCode2);
				tLPGetCalModeSchema.setGetType(tGetType);
				tLPGetCalModeSchema.setValPeriod(tValPeriod);
				tLPGetCalModeSchema.setValStartDate(tValStartDate);
				tLPGetCalModeSchema.setValEndDate(tValEndDate);
				tLPGetCalModeSchema.setFeeValue(tFeeValues);
				
			} else if ("DELETE1".equals(tOperate)) {
				tLPGetCalModeSchema.setEdorNo(tEdorNo);
				tLPGetCalModeSchema.setEdorType(tEdorType);
				tLPGetCalModeSchema.setSerialNo(tSerialNo);
			}else if ("INSERT".equals(tOperate) || "UPDATE".equals(tOperate) || "DELETE".equals(tOperate) ) {
				
				tLPGrpFeeSchema.setGrpContNo(tGrpContNo);
				tLPGrpFeeSchema.setGrpPolNo(tGrpContNo);
				tLPGrpFeeSchema.setEdorNo(tEdorNo);
				tLPGrpFeeSchema.setEdorType(tEdorType);
				tLPGrpFeeSchema.setRiskCode(tRiskCode);
				tLPGrpFeeSchema.setInsuAccNo(tAccType);
				tLPGrpFeeSchema.setFeeCode(tFeeType);
					
				if ("0".equals(tFeeType)) {//管理费类型:0-初始管理费

					tLPGrpFeeSchema.setFeeCalCode(tDeductType);
					tLPGrpFeeSchema.setFeeValue(tFeeValue);
				} else if ("1".equals(tFeeType)) {//管理费类型:1-月度管理费
					
					tLPGrpFeeSchema.setFeeCalCode(tMonthFeeType);
					tLPGrpFeeSchema.setFeeValue(tMonthValue);
				} else if ("2".equals(tFeeType)) {//管理费类型:2-年度管理费
					
					tLPGrpFeeSchema.setFeeCalCode(tYearFeeType);
					tLPGrpFeeSchema.setFeeValue(tYearValue);
					tLPGrpFeeSchema.setValStartDate(tYearStartValue);
					tLPGrpFeeSchema.setValEndDate(tYearEndValue);
				}
			}
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("MissionID", tMissionID);
			tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
			tTransferData.setNameAndValue("ActivityID", tActivityID);
			tTransferData.setNameAndValue("GrpContNo", tGrpContNo);
			tTransferData.setNameAndValue("EdorNo", tEdorNo);
			tTransferData.setNameAndValue("EdorType", tEdorType);
			tTransferData.setNameAndValue("EdorAppNo", tEdorAppNo);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLPGrpFeeSchema);
			tVData.add(tLPGetCalModeSchema);
		
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorMFUI")) {
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
