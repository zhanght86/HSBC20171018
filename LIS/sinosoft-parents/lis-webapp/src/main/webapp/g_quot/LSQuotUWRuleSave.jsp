<%
/***************************************************************
 * <p>ProName：LSQuotUWRuleSave.jsp</p>
 * <p>Title：核保规则</p>
 * <p>Description：核保规则</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-04-04
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LSQuotUWRuleSchema"%>
<%@page import="com.sinosoft.lis.schema.LSQuotEdorRuleSchema"%>
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
			
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tQuotType = request.getParameter("QuotType");
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			
			String tSerialNo = request.getParameter("SerialNo");//流水号
			String tRuleType = request.getParameter("RuleType");//保全规则类型
			String tEdorCode = request.getParameter("EdorCode");//保全项目
			String tCalCode = request.getParameter("CalCode");//保全算法
			String tOtherCalCode = request.getParameter("OtherCalCode");//类型描述
			
			String tRiskCode = request.getParameter("RiskCode");//产品编码
			String tRuleCode = request.getParameter("RuleCode");//规则编码
			String tParams = request.getParameter("Params");//参数
			String tInputValues = request.getParameter("InputValues");//调整参数值
			
			LSQuotUWRuleSchema tLSQuotUWRuleSchema = new LSQuotUWRuleSchema();
			LSQuotEdorRuleSchema tLSQuotEdorRuleSchema = new LSQuotEdorRuleSchema();
			
			if ("INSERT".equals(tOperate) || "UPDATE".equals(tOperate)) {
			
				tLSQuotEdorRuleSchema.setSerialNo(tSerialNo);
				tLSQuotEdorRuleSchema.setQuotNo(tQuotNo);
				tLSQuotEdorRuleSchema.setQuotBatNo(tQuotBatNo);
				tLSQuotEdorRuleSchema.setRuleType(tRuleType);
				tLSQuotEdorRuleSchema.setEdorCode(tEdorCode);
				tLSQuotEdorRuleSchema.setCalCode(tCalCode);
				
			} else if ("DELETE".equals(tOperate)) {
				
				tLSQuotEdorRuleSchema.setSerialNo(tSerialNo);
			} else if ("SAVE".equals(tOperate)) {
			
				tLSQuotUWRuleSchema.setQuotNo(tQuotNo);
				tLSQuotUWRuleSchema.setQuotBatNo(tQuotBatNo);
				tLSQuotUWRuleSchema.setRiskCode(tRiskCode);
				tLSQuotUWRuleSchema.setRuleCode(tRuleCode);
				tLSQuotUWRuleSchema.setParams(tParams);
				tLSQuotUWRuleSchema.setInputValues(tInputValues);
				
			}
				
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("QuotType", tQuotType);
			tTransferData.setNameAndValue("QuotNo", tQuotNo);
			tTransferData.setNameAndValue("QuotBatNo", tQuotBatNo);
			tTransferData.setNameAndValue("MissionID", tMissionID);
			tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
			tTransferData.setNameAndValue("ActivityID", tActivityID);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLSQuotUWRuleSchema);
			tVData.add(tLSQuotEdorRuleSchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotUWRuleUI")) {
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
