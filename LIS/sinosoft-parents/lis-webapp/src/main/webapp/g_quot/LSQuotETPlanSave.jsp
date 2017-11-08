<%
/***************************************************************
 * <p>ProName：LSQuotPlanSave.jsp</p>
 * <p>Title：一般询价方案信息保存</p>
 * <p>Description：一般询价方案信息保存</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LWMissionSchema"%>
<%@page import="com.sinosoft.lis.schema.LSQuotPlanSchema"%>
<%@page import="com.sinosoft.lis.g_quot.LSQuotPubFun"%>
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
		
			LSQuotPubFun tLSQuotPubFun = new LSQuotPubFun();
			
			tOperate = request.getParameter("Operate");
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			String tQuotType = request.getParameter("QuotType");
			String tTranProdType = request.getParameter("TranProdType");
			
			String tSysPlanCode = request.getParameter("SysPlanCode");
			String tPlanCode = request.getParameter("PlanCode");
			String tPlanDesc = request.getParameter("PlanDesc");
			String tPlanType = request.getParameter("PlanType");
			String tPlanFlag = request.getParameter("PlanFlag");
			String tPremCalType = request.getParameter("PremCalType");
			String tOccupTypeFlag = request.getParameter("OccupTypeFlag");
			String tMinOccupType = request.getParameter("MinOccupType");
			String tMaxOccupType = request.getParameter("MaxOccupType");
			String tOccupRate = request.getParameter("OccupRate");
			String tOccupType = request.getParameter("OccupType");
			String tOccupMidType = request.getParameter("OccupMidType");
			String tOccupCode = request.getParameter("OccupCode");
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
			LSQuotPlanSchema tLSQuotPlanSchema = new LSQuotPlanSchema();
			
			tLWMissionSchema.setMissionID(tMissionID);
			tLWMissionSchema.setSubMissionID(tSubMissionID);
			tLWMissionSchema.setActivityID(tActivityID);
			tLWMissionSchema.setMissionProp1(tQuotNo);
			tLWMissionSchema.setMissionProp2(tQuotBatNo);
			tLWMissionSchema.setMissionProp3(tQuotType);
			
			tLSQuotPlanSchema.setQuotNo(tQuotNo);
			tLSQuotPlanSchema.setQuotBatNo(tQuotBatNo);
			tLSQuotPlanSchema.setSysPlanCode(tSysPlanCode);
			tLSQuotPlanSchema.setPlanCode(tPlanCode);
			tLSQuotPlanSchema.setPlanDesc(tPlanDesc);
			tLSQuotPlanSchema.setPlanType(tPlanType);
			tLSQuotPlanSchema.setPlanFlag(tPlanFlag);
			tLSQuotPlanSchema.setPremCalType(tPremCalType);
			tLSQuotPlanSchema.setOccupTypeFlag(tOccupTypeFlag);
			tLSQuotPlanSchema.setMinOccupType(tMinOccupType);
			tLSQuotPlanSchema.setMaxOccupType(tMaxOccupType);
			tLSQuotPlanSchema.setOccupRate(tOccupRate.replaceAll("：",":"));
			tLSQuotPlanSchema.setOccupType(tOccupType);
			tLSQuotPlanSchema.setOccupMidType(tOccupMidType);
			tLSQuotPlanSchema.setOccupCode(tOccupCode);
			tLSQuotPlanSchema.setMinAge(tMinAge);
			tLSQuotPlanSchema.setMaxAge(tMaxAge);
			tLSQuotPlanSchema.setAvgAge(tAvgAge);
			tLSQuotPlanSchema.setNumPeople(tNumPeople);
			tLSQuotPlanSchema.setSocialInsuRate(tSocialInsuRate);
			tLSQuotPlanSchema.setMaleRate(tMaleRate);
			tLSQuotPlanSchema.setFemaleRate(tFemaleRate);
			tLSQuotPlanSchema.setRetireRate(tRetireRate);
			tLSQuotPlanSchema.setPremMode(tPremMode);
			tLSQuotPlanSchema.setEnterpriseRate(tEnterpriseRate);
			tLSQuotPlanSchema.setMinSalary(tMinSalary);
			tLSQuotPlanSchema.setMaxSalary(tMaxSalary);
			tLSQuotPlanSchema.setAvgSalary(tAvgSalary);
			tLSQuotPlanSchema.setOtherDesc(tOtherDesc);
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("TranProdType", tTranProdType);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLWMissionSchema);
			tVData.add(tLSQuotPlanSchema);
			tVData.add(tTransferData);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotPlanUI")) {
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
