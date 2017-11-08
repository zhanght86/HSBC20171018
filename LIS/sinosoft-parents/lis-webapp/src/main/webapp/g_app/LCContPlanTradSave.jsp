<%@page import="com.sinosoft.lis.g_quot.LSQuotPubFun"%>
<%
/***************************************************************
 * <p>ProName：LCContPlanTradSave.jsp</p>
 * <p>Title：投保方案信息保存</p>
 * <p>Description：投保方案信息保存</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LWMissionSchema"%>
<%@page import="com.sinosoft.lis.schema.LCContPlanSchema"%>
<%@page import="com.sinosoft.lis.g_app.LCContPubFun"%>
<%@page import="com.sinosoft.lis.schema.LCProposalSchema"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	ExeSQL mExeSQL = new ExeSQL();
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
		
			LSQuotPubFun tLSQuotPubFun = new LSQuotPubFun();
			String tGrpPropNo = "";
			String tOfferListNo = "";
			StringBuffer mStrBuff = new StringBuffer();
			
			tOperate = request.getParameter("Operate");
			tOfferListNo = request.getParameter("OfferListNo");
			tGrpPropNo = request.getParameter("GrpPropNo");
			
			String tPlanFlag = request.getParameter("PlanFlag");
			
			String tFlag = request.getParameter("Flag");
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tContPlanType = request.getParameter("ContPlanType");
			
			String tSysPlanCode = request.getParameter("SysPlanCode");
			String tPlanCode = request.getParameter("PlanCode");
			String tPlanDesc = request.getParameter("PlanDesc");
			String tPlanType = request.getParameter("PlanType");
			String tPremCalType = request.getParameter("PremCalType");
			String tOccupTypeFlag = request.getParameter("OccupTypeFlag");
			String tMinOccupType = request.getParameter("MinOccupType");
			String tMaxOccupType = request.getParameter("MaxOccupType");
			String tOccupType = request.getParameter("OccupType");
			String tOccupMidType = request.getParameter("OccupMidType");
			String tOccupCode = request.getParameter("OccupCode");
			String tOccupRate = request.getParameter("OccupRate");
			String tMinAge = tLSQuotPubFun.isNumNull(request.getParameter("MinAge"));
			String tMaxAge = tLSQuotPubFun.isNumNull(request.getParameter("MaxAge"));
			String tAvgAge = tLSQuotPubFun.isNumNull(request.getParameter("AvgAge"));
			String tNumPeople = tLSQuotPubFun.isNumNull(request.getParameter("NumPeople"));
			String tSocialInsuRate = tLSQuotPubFun.isNumNull(request.getParameter("SocialInsuRate"));
			String tMaleRate = tLSQuotPubFun.isNumNull(request.getParameter("MaleRate"));
			String tFemaleRate = tLSQuotPubFun.isNumNull(request.getParameter("FemaleRate"));
			String tRetireRate = tLSQuotPubFun.isNumNull(request.getParameter("RetireRate"));
			String tPremMode = request.getParameter("PremMode");
			String tEnterpriseRate = tLSQuotPubFun.isNumNull(request.getParameter("EnterpriseRate"));
			String tMinSalary = tLSQuotPubFun.isNumNull(request.getParameter("MinSalary"));
			String tMaxSalary = tLSQuotPubFun.isNumNull(request.getParameter("MaxSalary"));
			String tAvgSalary = tLSQuotPubFun.isNumNull(request.getParameter("AvgSalary"));
			String tOtherDesc = request.getParameter("OtherDesc");
			
			String tManageCom = request.getParameter("AppManageCom");
			String tInsuPeriod = request.getParameter("InsuPeriod");
			String tInsuPeriodFlag = request.getParameter("InsuPeriodFlag");
			String tPolicyFlag = request.getParameter("PolicyFlag");
			
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			LCContPlanSchema tLCContPlanSchema = new LCContPlanSchema();
			
			tLWMissionSchema.setMissionID(tMissionID);
			tLWMissionSchema.setSubMissionID(tSubMissionID);
			tLWMissionSchema.setActivityID(tActivityID);
			tLWMissionSchema.setMissionProp1(tGrpPropNo);
			tLWMissionSchema.setMissionProp2(tContPlanType);
			
			tLCContPlanSchema.setGrpContNo(tGrpPropNo);
			tLCContPlanSchema.setProposalGrpContNo(tGrpPropNo);
			tLCContPlanSchema.setPlanCode(tPlanCode);
			tLCContPlanSchema.setContPlanCode(tSysPlanCode);
			tLCContPlanSchema.setContPlanName(tPlanDesc);
			tLCContPlanSchema.setPlanType(tPlanType);
			tLCContPlanSchema.setPlanFlag(tPlanFlag);
			tLCContPlanSchema.setPremCalType(tPremCalType);
			tLCContPlanSchema.setOccupTypeFlag(tOccupTypeFlag);
			tLCContPlanSchema.setMinOccupType(tMinOccupType);
			tLCContPlanSchema.setMaxOccupType(tMaxOccupType);
			tLCContPlanSchema.setOccupType(tOccupType);
			tLCContPlanSchema.setOccupMidType(tOccupMidType);
			tLCContPlanSchema.setOccupCode(tOccupCode);
			tLCContPlanSchema.setMinAge(tMinAge);
			tLCContPlanSchema.setMaxAge(tMaxAge);
			tLCContPlanSchema.setAvgAge(tAvgAge);
			tLCContPlanSchema.setPeoples2(tNumPeople);
			tLCContPlanSchema.setSocialInsuRate(tSocialInsuRate);
			tLCContPlanSchema.setMaleRate(tMaleRate);
			tLCContPlanSchema.setFemaleRate(tFemaleRate);
			tLCContPlanSchema.setRetireRate(tRetireRate);
			tLCContPlanSchema.setPremMode(tPremMode);
			tLCContPlanSchema.setEnterpriseRate(tEnterpriseRate);
			tLCContPlanSchema.setMinSalary(tMinSalary);
			tLCContPlanSchema.setMaxSalary(tMaxSalary);
			tLCContPlanSchema.setAvgSalary(tAvgSalary);
			tLCContPlanSchema.setOtherDesc(tOtherDesc);
			if(tOccupRate!=null && !"".equals(tOccupRate)){
				tLCContPlanSchema.setOccupRate(tOccupRate.replaceAll("：",":"));
			}
			LCProposalSchema tLCProposalSchema = new LCProposalSchema();
			tLCProposalSchema.setGrpPropNo(tGrpPropNo);
			tLCProposalSchema.setOfferListNo(tOfferListNo);
		
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("ContPlanType", tContPlanType);
			tTransferData.setNameAndValue("GrpPropNo", tGrpPropNo);
			tTransferData.setNameAndValue("Flag", tFlag);
			tTransferData.setNameAndValue("ManageCom", tManageCom);
			tTransferData.setNameAndValue("InsuPeriod", tInsuPeriod);
			tTransferData.setNameAndValue("InsuPeriodFlag", tInsuPeriodFlag);
			tTransferData.setNameAndValue("PolicyFlag", tPolicyFlag);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLWMissionSchema);
			tVData.add(tLCContPlanSchema);
			tVData.add(tTransferData); 
			tVData.add(tLCProposalSchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "g_LCContPlanUI")) {
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
