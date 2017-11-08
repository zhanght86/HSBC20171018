<%
/***************************************************************
 * <p>ProName：LSQuotExpandSave.jsp</p>
 * <p>Title：责任拓展</p>
 * <p>Description：责任拓展</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-04-03
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LSQuotExpandSchema"%>
<%@page import="com.sinosoft.lis.schema.LSQuotExpandSubSchema"%>
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
			//固定值
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			String tSysPlanCode = request.getParameter("SysPlanCode");
			String tPlanCode = request.getParameter("PlanCode");
			String tRiskCode = request.getParameter("RiskCode");
			String tDutyCode = request.getParameter("DutyCode");
			String tGetDutyCode = request.getParameter("GetDutyCode");
			String tExpandType = request.getParameter("ExpandType");
			String tExpandDuty = request.getParameter("ExpandDuty");
			
			LSQuotExpandSchema tLSQuotExpandSchema = new LSQuotExpandSchema();
			LSQuotExpandSubSchema tLSQuotExpandSubSchema = new LSQuotExpandSubSchema();
			
			tLSQuotExpandSchema.setQuotNo(tQuotNo);
			tLSQuotExpandSchema.setQuotBatNo(tQuotBatNo);
			tLSQuotExpandSchema.setSysPlanCode(tSysPlanCode);
			tLSQuotExpandSchema.setPlanCode(tPlanCode);
			tLSQuotExpandSchema.setRiskCode(tRiskCode);
			tLSQuotExpandSchema.setDutyCode(tDutyCode);
			tLSQuotExpandSchema.setGetDutyCode(tGetDutyCode);
			tLSQuotExpandSchema.setExpandType(tExpandType);
			if ("0".equals(tExpandType)) {//扩大
				tLSQuotExpandSchema.setExpandDutyCode(tExpandDuty);
			} else if ("1".equals(tExpandType)) {//缩小
				tLSQuotExpandSchema.setExpandDutyCode(tGetDutyCode);
			}
			
			
			//动态值
			String tSql = "select a.factorcode,a.fieldcode,a.factorname,a.fieldtype,a.valuetype,c.calsql,a.valuelength, a.valuescope "
									+" from lmfactorconfig a ,lmdutyfactorrela b,lmdutyexpand c "
									+" where a.factorcode = b.factorcode and b.dutycode = c.expanddutycode "
									+" and b.riskcode = '000000' and b.dutycode = '"+"?tExpandDuty?"+"'" 
									+" order by b.factororder ";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSql);
			sqlbv1.put("tExpandDuty",tExpandDuty);
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv1);
			if (tSSRS==null) {
			
			} else {
			
				tLSQuotExpandSubSchema.setQuotNo(tQuotNo);
				tLSQuotExpandSubSchema.setQuotBatNo(tQuotBatNo);
				tLSQuotExpandSubSchema.setSysPlanCode(tSysPlanCode);
				tLSQuotExpandSubSchema.setPlanCode(tPlanCode);
				tLSQuotExpandSubSchema.setRiskCode(tRiskCode);
				tLSQuotExpandSubSchema.setDutyCode(tDutyCode);
				tLSQuotExpandSubSchema.setGetDutyCode(tGetDutyCode);
				tLSQuotExpandSubSchema.setExpandDutyCode(tExpandDuty);
				
				for (int i=1;i<=tSSRS.getMaxRow(); i++) {
					
					String tFactorCode = tSSRS.GetText(i, 2);
					String tValue = request.getParameter(tFactorCode);
					
					tLSQuotExpandSubSchema.setV(tFactorCode, tValue);
				}
			}
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("QuotNo", tQuotNo);
			tTransferData.setNameAndValue("QuotBatNo", tQuotBatNo);
			tTransferData.setNameAndValue("ActivityID", tActivityID);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLSQuotExpandSchema);
			tVData.add(tLSQuotExpandSubSchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotExpandUI")) {
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
