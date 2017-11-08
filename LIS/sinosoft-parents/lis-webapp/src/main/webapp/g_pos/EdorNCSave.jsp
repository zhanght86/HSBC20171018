<%
/***************************************************************
 * <p>ProName：EdorNCSave.jsp</p>
 * <p>Title：方案信息保存</p>
 * <p>Description：方案信息保存</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-07-14
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LWMissionSchema"%>
<%@page import="com.sinosoft.lis.schema.LPContPlanSchema"%>
<%@page import="com.sinosoft.lis.g_quot.LSQuotPubFun"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	ExeSQL tExeSQL = new ExeSQL();
	String mStrBuff ="";
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
		
			LSQuotPubFun tLSQuotPubFun = new LSQuotPubFun();
			
			tOperate = request.getParameter("Operate");
			String tPolicyNo = request.getParameter("PolicyNo");
			String tEdorAppNo = request.getParameter("EdorAppNo");
			String tEdorNo = request.getParameter("EdorNo");
			String tEdorType = request.getParameter("EdorType");
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tContPlanType = request.getParameter("ContPlanType");
			
			String tSysPlanCode = request.getParameter("SysPlanCode");
			String tPlanCode = request.getParameter("PlanCode");
			String tPlanDesc = request.getParameter("PlanDesc");
			String tPlanType = request.getParameter("PlanType");
			String tPlanFlag = request.getParameter("PlanFlag");
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
			
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			LPContPlanSchema tLPContPlanSchema = new LPContPlanSchema();
			
			tLWMissionSchema.setMissionID(tMissionID);
			tLWMissionSchema.setSubMissionID(tSubMissionID);
			tLWMissionSchema.setActivityID(tActivityID);
			
			tLPContPlanSchema.setEdorNo(tEdorNo);
			tLPContPlanSchema.setEdorType(tEdorType);
			tLPContPlanSchema.setGrpContNo(tPolicyNo);
			tLPContPlanSchema.setPlanCode(tPlanCode);
			tLPContPlanSchema.setContPlanCode(tSysPlanCode);
			tLPContPlanSchema.setContPlanName(tPlanDesc);
			tLPContPlanSchema.setPlanType(tPlanType);
			tLPContPlanSchema.setPlanFlag(tPlanFlag);
			tLPContPlanSchema.setPremCalType(tPremCalType);
			tLPContPlanSchema.setOccupTypeFlag(tOccupTypeFlag);
			tLPContPlanSchema.setMinOccupType(tMinOccupType);
			tLPContPlanSchema.setMaxOccupType(tMaxOccupType);
			tLPContPlanSchema.setOccupType(tOccupType);
			tLPContPlanSchema.setOccupMidType(tOccupMidType);
			tLPContPlanSchema.setOccupCode(tOccupCode);
			tLPContPlanSchema.setMinAge(tMinAge);
			tLPContPlanSchema.setMaxAge(tMaxAge);
			tLPContPlanSchema.setAvgAge(tAvgAge);
			tLPContPlanSchema.setPeoples2(tNumPeople);
			tLPContPlanSchema.setSocialInsuRate(tSocialInsuRate);
			tLPContPlanSchema.setMaleRate(tMaleRate);
			tLPContPlanSchema.setFemaleRate(tFemaleRate);
			tLPContPlanSchema.setRetireRate(tRetireRate);
			tLPContPlanSchema.setPremMode(tPremMode);
			tLPContPlanSchema.setEnterpriseRate(tEnterpriseRate);
			tLPContPlanSchema.setMinSalary(tMinSalary);
			tLPContPlanSchema.setMaxSalary(tMaxSalary);
			tLPContPlanSchema.setAvgSalary(tAvgSalary);
			tLPContPlanSchema.setOtherDesc(tOtherDesc);
			if(tOccupRate!=null && !"".equals(tOccupRate)){
				tLPContPlanSchema.setOccupRate(tOccupRate.replaceAll("：",":"));
			}

			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("MissionID", tMissionID);
			tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
			tTransferData.setNameAndValue("ActivityID",tActivityID);
			tTransferData.setNameAndValue("ContPlanType", tContPlanType);
			tTransferData.setNameAndValue("PolicyNo", tPolicyNo);
			tTransferData.setNameAndValue("EdorAppNo", tEdorAppNo);
			tTransferData.setNameAndValue("EdorNo", tEdorNo);
			tTransferData.setNameAndValue("EdorType", tEdorType);

			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLWMissionSchema);
			tVData.add(tLPContPlanSchema);
			tVData.add(tTransferData); 
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorNCUI")) {
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
