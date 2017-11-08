<%@page import="com.sinosoft.lis.vschema.LPContPlanPerInvestSet"%>
<%@page import="com.sinosoft.lis.schema.LPContPlanPerInvestSchema"%>
<%@page import="com.sinosoft.lis.schema.LPContPlanInvestSchema"%>
<%
/***************************************************************
 * <p>ProName：EdorNCPubAmntRelaSave.jsp</p>
 * <p>Title：公共保额使用关联</p>
 * <p>Description：公共保额使用关联</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-05-08
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LPContPlanPubAmntRelaSchema"%>
<%@page import="com.sinosoft.lis.schema.LPContPlanPubAmntRelaSubSchema"%>
<%@page import="com.sinosoft.lis.vschema.LPContPlanPubAmntRelaSubSet"%>
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
			
			String tGrpPropNo = request.getParameter("GrpPropNo");
			String tPolicyNo = request.getParameter("PolicyNo");
			String tSysPlanCode = request.getParameter("SysPlanCode");
			String tPlanCode = request.getParameter("PlanCode");
			String tRiskCodes = request.getParameter("RiskCode");
			String tDutyCodes = request.getParameter("DutyCode");
			String tLimitAmnt = request.getParameter("LimitAmnt");
			String tEdorNo = request.getParameter("EdorNo");
			String tEdorType = request.getParameter("EdorType");
			
			LPContPlanPubAmntRelaSchema tLPContPlanPubAmntRelaSchema = new LPContPlanPubAmntRelaSchema();
			LPContPlanPubAmntRelaSubSet tLPContPlanPubAmntRelaSubSet = new LPContPlanPubAmntRelaSubSet();
			LPContPlanInvestSchema tLPContPlanInvestSchema = new LPContPlanInvestSchema();
			LPContPlanPerInvestSet tLPContPlanPerInvestSet = new LPContPlanPerInvestSet();
			//询价公共保额关联主表
			tLPContPlanPubAmntRelaSchema.setEdorNo(tEdorNo);
			tLPContPlanPubAmntRelaSchema.setEdorType(tEdorType);
			tLPContPlanPubAmntRelaSchema.setPolicyNo(tPolicyNo);
			tLPContPlanPubAmntRelaSchema.setSysPlanCode(tSysPlanCode);
			tLPContPlanPubAmntRelaSchema.setPlanCode(tPlanCode);
			
			if ("SAVEPUBAMNT".equals(tOperate)) {
				
				tLPContPlanPubAmntRelaSchema.setLimitAmnt(tLimitAmnt);
				
				//询价公共保额管理子表
				String tChk [] = request.getParameterValues("PubAmntRelaDutyGridNo"); //序号列的所有值
				String tRiskCode [] = request.getParameterValues("PubAmntRelaDutyGrid1"); 
				String tDutyCode [] = request.getParameterValues("PubAmntRelaDutyGrid3"); 
				String tPubFlag [] = request.getParameterValues("PubAmntRelaDutyGrid5"); 
				String tDutyLimitAmnt [] = request.getParameterValues("PubAmntRelaDutyGrid7"); 
				for(int index=0;index<tChk.length;index++){
				
					LPContPlanPubAmntRelaSubSchema tLPContPlanPubAmntRelaSubSchema = new LPContPlanPubAmntRelaSubSchema();
					
					tLPContPlanPubAmntRelaSubSchema.setPolicyNo(tPolicyNo);
					tLPContPlanPubAmntRelaSubSchema.setEdorNo(tEdorNo);
					tLPContPlanPubAmntRelaSubSchema.setEdorType(tEdorType);
					tLPContPlanPubAmntRelaSubSchema.setSysPlanCode(tSysPlanCode);
					tLPContPlanPubAmntRelaSubSchema.setPlanCode(tPlanCode);
					tLPContPlanPubAmntRelaSubSchema.setRiskCode(tRiskCode[index]);
					tLPContPlanPubAmntRelaSubSchema.setDutyCode(tDutyCode[index]);
					tLPContPlanPubAmntRelaSubSchema.setPubFlag(tPubFlag[index]);
					
					if ("0".equals(tPubFlag[index])) {
						tLPContPlanPubAmntRelaSubSchema.setDutyLimitAmnt("-1");
					} else {
						tLPContPlanPubAmntRelaSubSchema.setDutyLimitAmnt(tDutyLimitAmnt[index]);
					}
										
					tLPContPlanPubAmntRelaSubSet.add(tLPContPlanPubAmntRelaSubSchema);
			
				}
			} else if("SAVEFEE".equals(tOperate)){
				String tRadio[] = request.getParameterValues("InpPayFeeGridSel");  
				for(int index=0; index<tRadio.length;index++){
					if(tRadio[index].equals("1")){
						tLPContPlanInvestSchema = new LPContPlanInvestSchema();
						String tGrid1 [] = request.getParameterValues("PayFeeGrid1");
						String tGrid3 [] = request.getParameterValues("PayFeeGrid3");
						String tGrid5 [] = request.getParameterValues("PayFeeGrid5");
						tLPContPlanInvestSchema.setEdorNo(tEdorNo);
						tLPContPlanInvestSchema.setEdorType(tEdorType);
						tLPContPlanInvestSchema.setPolicyNo(tPolicyNo);
						tLPContPlanInvestSchema.setSysPlanCode(tSysPlanCode);
						tLPContPlanInvestSchema.setRiskCode(tRiskCodes);
						tLPContPlanInvestSchema.setPlanCode(tPlanCode);
						tLPContPlanInvestSchema.setDutyCode(tGrid1[index]);
						tLPContPlanInvestSchema.setPayPlanCode(tGrid3 [index]);
						tLPContPlanInvestSchema.setPayMoney(tGrid5 [index]);
					}
				}
			}else if("SAVEACC".equals(tOperate)){
				String tGridNo[] = request.getParameterValues("TZFeeGridNo");
				for(int index=0; index<tGridNo.length;index++){
						LPContPlanPerInvestSchema tLPContPlanPerInvestSchema = new LPContPlanPerInvestSchema();
						String tGrid1 [] = request.getParameterValues("TZFeeGrid1");
						String tGrid3 [] = request.getParameterValues("TZFeeGrid3");
						String tGrid5 [] = request.getParameterValues("TZFeeGrid5");
						String tGrid6 [] = request.getParameterValues("TZFeeGrid6");
						
						tLPContPlanPerInvestSchema.setEdorNo(tEdorNo);
						tLPContPlanPerInvestSchema.setEdorType(tEdorType);
						tLPContPlanPerInvestSchema.setPolicyNo(tPolicyNo);
						tLPContPlanPerInvestSchema.setSysPlanCode(tSysPlanCode);
						tLPContPlanPerInvestSchema.setRiskCode(tRiskCodes);
						tLPContPlanPerInvestSchema.setPlanCode(tPlanCode);
						tLPContPlanPerInvestSchema.setDutyCode(tDutyCodes);
						tLPContPlanPerInvestSchema.setPayPlanCode(tGrid1[index]);
						tLPContPlanPerInvestSchema.setInsuAccNo(tGrid3 [index]);
						tLPContPlanPerInvestSchema.setInvestMoney(tGrid5 [index]);
						tLPContPlanPerInvestSchema.setInvestRate(tGrid6 [index]);
						tLPContPlanPerInvestSchema.setRealInvestMoney(tGrid5 [index]);
						tLPContPlanPerInvestSet.add(tLPContPlanPerInvestSchema);
				}
			}
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLPContPlanPubAmntRelaSchema);
			tVData.add(tLPContPlanPubAmntRelaSubSet);
			tVData.add(tLPContPlanInvestSchema);
			tVData.add(tLPContPlanPerInvestSet);
			
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorNCPubAmntRelaUI")) {
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
