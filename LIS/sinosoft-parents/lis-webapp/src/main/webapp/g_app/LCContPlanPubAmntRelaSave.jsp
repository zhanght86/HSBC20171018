<%
/***************************************************************
 * <p>ProName：LCContPlanPubAmntRelaSave.jsp</p>
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
<%@page import="com.sinosoft.lis.schema.LCContPlanPubAmntRelaSchema"%>
<%@page import="com.sinosoft.lis.schema.LCContPlanPubAmntRelaSubSchema"%>
<%@page import="com.sinosoft.lis.vschema.LCContPlanPubAmntRelaSubSet"%>
<%@page import="com.sinosoft.lis.schema.LCContPlanInvestSchema"%>
<%@page import="com.sinosoft.lis.vschema.LCContPlanPerInvestSet"%>
<%@page import="com.sinosoft.lis.schema.LCContPlanPerInvestSchema"%>
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
			String tPolicyNo = request.getParameter("GrpPropNo");
			String tSysPlanCode = request.getParameter("SysPlanCode");
			String tPlanCode = request.getParameter("PlanCode");
			String tRiskCodes = request.getParameter("RiskCode");
			String tDutyCodes = request.getParameter("DutyCode");
			String tLimitAmnt = request.getParameter("LimitAmnt");
			
			LCContPlanPubAmntRelaSchema tLCContPlanPubAmntRelaSchema = new LCContPlanPubAmntRelaSchema();
			LCContPlanPubAmntRelaSubSet tLCContPlanPubAmntRelaSubSet = new LCContPlanPubAmntRelaSubSet();
			LCContPlanInvestSchema tLCContPlanInvestSchema = new LCContPlanInvestSchema();
			LCContPlanPerInvestSet tLCContPlanPerInvestSet = new LCContPlanPerInvestSet();
			
			
			//询价公共保额关联主表
			tLCContPlanPubAmntRelaSchema.setPolicyNo(tPolicyNo);
			tLCContPlanPubAmntRelaSchema.setSysPlanCode(tSysPlanCode);
			tLCContPlanPubAmntRelaSchema.setPlanCode(tPlanCode);
			
			if ("SAVEPUBAMNT".equals(tOperate)) {
				
				tLCContPlanPubAmntRelaSchema.setLimitAmnt(tLimitAmnt);
				
				//询价公共保额管理子表
				String tChk [] = request.getParameterValues("PubAmntRelaDutyGridNo"); //序号列的所有值
				String tRiskCode [] = request.getParameterValues("PubAmntRelaDutyGrid1"); 
				String tDutyCode [] = request.getParameterValues("PubAmntRelaDutyGrid3"); 
				String tPubFlag [] = request.getParameterValues("PubAmntRelaDutyGrid5"); 
				String tDutyLimitAmnt [] = request.getParameterValues("PubAmntRelaDutyGrid7"); 
				for(int index=0;index<tChk.length;index++){
				
					LCContPlanPubAmntRelaSubSchema tLCContPlanPubAmntRelaSubSchema = new LCContPlanPubAmntRelaSubSchema();
					
					tLCContPlanPubAmntRelaSubSchema.setPolicyNo(tPolicyNo);
					tLCContPlanPubAmntRelaSubSchema.setSysPlanCode(tSysPlanCode);
					tLCContPlanPubAmntRelaSubSchema.setPlanCode(tPlanCode);
					tLCContPlanPubAmntRelaSubSchema.setRiskCode(tRiskCode[index]);
					tLCContPlanPubAmntRelaSubSchema.setDutyCode(tDutyCode[index]);
					tLCContPlanPubAmntRelaSubSchema.setPubFlag(tPubFlag[index]);
					
					if ("0".equals(tPubFlag[index])) {
						tLCContPlanPubAmntRelaSubSchema.setDutyLimitAmnt("-1");
					} else {
						tLCContPlanPubAmntRelaSubSchema.setDutyLimitAmnt(tDutyLimitAmnt[index]);
					}
										
					tLCContPlanPubAmntRelaSubSet.add(tLCContPlanPubAmntRelaSubSchema);
			
				}
			} else if("SAVEFEE".equals(tOperate)){
				String tRadio[] = request.getParameterValues("InpPayFeeGridSel");  
					for(int index=0; index<tRadio.length;index++){
						if(tRadio[index].equals("1")){
							tLCContPlanInvestSchema = new LCContPlanInvestSchema();
							String tGrid1 [] = request.getParameterValues("PayFeeGrid1");
							String tGrid3 [] = request.getParameterValues("PayFeeGrid3");
							String tGrid5 [] = request.getParameterValues("PayFeeGrid5");
							tLCContPlanInvestSchema.setPolicyNo(tPolicyNo);
							tLCContPlanInvestSchema.setSysPlanCode(tSysPlanCode);
							tLCContPlanInvestSchema.setRiskCode(tRiskCodes);
							tLCContPlanInvestSchema.setPlanCode(tPlanCode);
							tLCContPlanInvestSchema.setDutyCode(tGrid1[index]);
							tLCContPlanInvestSchema.setPayPlanCode(tGrid3 [index]);
							tLCContPlanInvestSchema.setPayMoney(tGrid5 [index]);
						}
					}
			}else if("SAVEACC".equals(tOperate)){
				String tGridNo[] = request.getParameterValues("TZFeeGridNo");
				for(int index=0; index<tGridNo.length;index++){
						LCContPlanPerInvestSchema tLCContPlanPerInvestSchema = new LCContPlanPerInvestSchema();
						String tGrid1 [] = request.getParameterValues("TZFeeGrid1");
						String tGrid3 [] = request.getParameterValues("TZFeeGrid3");
						String tGrid5 [] = request.getParameterValues("TZFeeGrid5");
						String tGrid6 [] = request.getParameterValues("TZFeeGrid6");
						
						tLCContPlanPerInvestSchema.setPolicyNo(tPolicyNo);
						tLCContPlanPerInvestSchema.setSysPlanCode(tSysPlanCode);
						tLCContPlanPerInvestSchema.setRiskCode(tRiskCodes);
						tLCContPlanPerInvestSchema.setPlanCode(tPlanCode);
						tLCContPlanPerInvestSchema.setDutyCode(tDutyCodes);
						tLCContPlanPerInvestSchema.setPayPlanCode(tGrid1[index]);
						tLCContPlanPerInvestSchema.setInsuAccNo(tGrid3 [index]);
						tLCContPlanPerInvestSchema.setInvestMoney("".equals(tGrid5 [index])?"-1":tGrid5 [index]);
						tLCContPlanPerInvestSchema.setInvestRate("".equals(tGrid6 [index])?"-1":tGrid6 [index]);
						tLCContPlanPerInvestSchema.setRealInvestMoney("0");
						tLCContPlanPerInvestSet.add(tLCContPlanPerInvestSchema);
				}
			}
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLCContPlanPubAmntRelaSchema);
			tVData.add(tLCContPlanPubAmntRelaSubSet);
			tVData.add(tLCContPlanInvestSchema);
			tVData.add(tLCContPlanPerInvestSet);
			
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LCContPlanPubAmntRelaUI")) {
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
