<%
/***************************************************************
 * <p>ProName：LLClaimModApplySave.jsp</p>
 * <p>Title：保项修改分公司申请申请界面</p>
 * <p>Description：保项修改分公司申请申请界面</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : 王超
 * @version  : 8.0
 * @date     : 2015-03-11
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LLModifyApplySchema"%>
<%@page import="com.sinosoft.lis.schema.LLModifyApplyContSchema"%>
<%@page import="com.sinosoft.lis.schema.LLModifyApplyContPlanSchema"%>
<%@page import="com.sinosoft.lis.vschema.LLModifyApplyContSet"%>
<%@page import="com.sinosoft.lis.vschema.LLModifyApplyContPlanSet"%>
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
			
			String mOperate = request.getParameter("Operate");
			
			LLModifyApplySchema tLLModifyApplySchema = new LLModifyApplySchema();
			LLModifyApplyContSet tLLModifyApplyContSet = new LLModifyApplyContSet();
			LLModifyApplyContPlanSet tLLModifyApplyContPlanSet = new LLModifyApplyContPlanSet();
			
			if ("INSERT".equals(mOperate)) {
				
				tLLModifyApplySchema.setReasonNo(request.getParameter("ReasonType"));
				tLLModifyApplySchema.setRuleType(request.getParameter("RuleType"));
				tLLModifyApplySchema.setRiskCode(request.getParameter("RiskCode"));
				tLLModifyApplySchema.setAdjustDirection(request.getParameter("AdjustDirection"));			
				tLLModifyApplySchema.setUpAdjustRule(request.getParameter("UpAdjustRule"));
				tLLModifyApplySchema.setUpAdjustRate(request.getParameter("UpAdjustRate"));
				tLLModifyApplySchema.setStartDate(request.getParameter("StartDate"));
				tLLModifyApplySchema.setEndDate(request.getParameter("EndDate"));
				tLLModifyApplySchema.setRemark(request.getParameter("Remark"));
			} else if ("UPDATE".equals(mOperate)) {
				
				tLLModifyApplySchema.setApplyNo(request.getParameter("ApplyNo"));
				tLLModifyApplySchema.setApplyBatchNo(request.getParameter("ApplyBatchNo"));
				tLLModifyApplySchema.setReasonNo(request.getParameter("ReasonType"));
				tLLModifyApplySchema.setRuleType(request.getParameter("RuleType"));
				tLLModifyApplySchema.setRiskCode(request.getParameter("RiskCode"));
				tLLModifyApplySchema.setAdjustDirection(request.getParameter("AdjustDirection"));			
				tLLModifyApplySchema.setUpAdjustRule(request.getParameter("UpAdjustRule"));
				tLLModifyApplySchema.setUpAdjustRate(request.getParameter("UpAdjustRate"));
				tLLModifyApplySchema.setStartDate(request.getParameter("StartDate"));
				tLLModifyApplySchema.setEndDate(request.getParameter("EndDate"));
				tLLModifyApplySchema.setRemark(request.getParameter("Remark"));
			} else if ("DELETE".equals(mOperate)) {
				
				tLLModifyApplySchema.setApplyNo(request.getParameter("ApplyNo"));
				tLLModifyApplySchema.setApplyBatchNo(request.getParameter("ApplyBatchNo"));
			} else if ("SELECT".equals(mOperate)) {
				
				tLLModifyApplySchema.setApplyNo(request.getParameter("ApplyNo"));
				tLLModifyApplySchema.setApplyBatchNo(request.getParameter("ApplyBatchNo"));
				tLLModifyApplySchema.setRiskCode(request.getParameter("RiskCode"));
				tLLModifyApplySchema.setRuleType(request.getParameter("RuleType"));
				
				String tChk[] = request.getParameterValues("InpNotGrpGridChk");
				String tGrpContNo[] = request.getParameterValues("NotGrpGrid2");
				if (tChk!=null && tChk.length>0) {
					
					for (int i=0;i<tChk.length;i++) {					
						
						if ("1".equals(tChk[i])) {
							
							LLModifyApplyContSchema tLLModifyApplyContSchema=new LLModifyApplyContSchema();
							tLLModifyApplyContSchema.setGrpContNo(tGrpContNo[i]);
							tLLModifyApplyContSet.add(tLLModifyApplyContSchema);
						}
					}
				}
			} else if ("CANCEL".equals(mOperate)) {
				
				tLLModifyApplySchema.setApplyNo(request.getParameter("ApplyNo"));
				tLLModifyApplySchema.setApplyBatchNo(request.getParameter("ApplyBatchNo"));
				
				String tRuleType = request.getParameter("RuleType");
				if ("01".equals(tRuleType)) {
					
					String tChk[] = request.getParameterValues("InpAcceptGrpGridChk");
					String tGrpContNo[] = request.getParameterValues("AcceptGrpGrid2");
					
					if (tChk!=null && tChk.length>0) {
					
						for (int i=0;i<tChk.length;i++) {					
							
							if ("1".equals(tChk[i])) {
								
								LLModifyApplyContSchema tLLModifyApplyContSchema=new LLModifyApplyContSchema();
								tLLModifyApplyContSchema.setGrpContNo(tGrpContNo[i]);
								tLLModifyApplyContSet.add(tLLModifyApplyContSchema);
							}
						}
					}
				} else if ("02".equals(tRuleType)) {
					
					String tChk[] = request.getParameterValues("InpAcceptGrpPlanGridChk");
					String tGrpContNo[] = request.getParameterValues("AcceptGrpPlanGrid2");
					
					if (tChk!=null && tChk.length>0) {
					
						for (int i=0;i<tChk.length;i++) {					
							
							if ("1".equals(tChk[i])) {
								
								LLModifyApplyContSchema tLLModifyApplyContSchema=new LLModifyApplyContSchema();
								tLLModifyApplyContSchema.setGrpContNo(tGrpContNo[i]);
								tLLModifyApplyContSet.add(tLLModifyApplyContSchema);
							}
						}
					}
				}
			} else if ("SAVEPLAN".equals(mOperate)) {
				
				tLLModifyApplySchema.setApplyNo(request.getParameter("ApplyNo"));
				tLLModifyApplySchema.setApplyBatchNo(request.getParameter("ApplyBatchNo"));
				tLLModifyApplySchema.setApplyState(request.getParameter("ApplyState"));			
				String tGrpContNo = request.getParameter("HiddenGrpContNo");
				tLLModifyApplySchema.setRiskCode(request.getParameter("RiskCode"));
				tLLModifyApplySchema.setRuleType(request.getParameter("RuleType"));
				
				LLModifyApplyContSchema tLLModifyApplyContSchema=new LLModifyApplyContSchema();
				tLLModifyApplyContSchema.setGrpContNo(tGrpContNo);
				tLLModifyApplyContSet.add(tLLModifyApplyContSchema);
				
				String tGridNo[] = request.getParameterValues("PlanGridNo");
				String tSysPlanCode[] = request.getParameterValues("PlanGrid1");
				String tPlanCode[] = request.getParameterValues("PlanGrid2");
				
				if (tGridNo!=null && tGridNo.length>0) {
					
					for (int i=0;i<tGridNo.length;i++) {
						
						LLModifyApplyContPlanSchema tLLModifyApplyContPlanSchema = new LLModifyApplyContPlanSchema();
						tLLModifyApplyContPlanSchema.setGrpContNo(tGrpContNo);
						tLLModifyApplyContPlanSchema.setSysPlanCode(tSysPlanCode[i]);
						tLLModifyApplyContPlanSchema.setPlanCode(tPlanCode[i]);
						tLLModifyApplyContPlanSet.add(tLLModifyApplyContPlanSchema);
					}
				}
			} else if ("SUBMIT".equals(mOperate)) {
				
				tLLModifyApplySchema.setApplyNo(request.getParameter("ApplyNo"));
				tLLModifyApplySchema.setApplyBatchNo(request.getParameter("ApplyBatchNo"));
			} else if ("APPLYMODIFY".equals(mOperate)){
				
				tLLModifyApplySchema.setApplyNo(request.getParameter("ApplyNo"));
				tLLModifyApplySchema.setApplyBatchNo(request.getParameter("ApplyBatchNo"));
				tLLModifyApplySchema.setApplyState(request.getParameter("ApplyState"));
				
			}
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLLModifyApplySchema);
			tVData.add(tLLModifyApplyContSet);
			tVData.add(tLLModifyApplyContPlanSet);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, mOperate, "LLClaimModApplyUI")) {
			
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
