<%
/***************************************************************
 * <p>ProName：LSQuotProjPlanSave</p>
 * <p>Title：项目询价方案信息保存</p>
 * <p>Description：项目询价方案信息保存</p>
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
<%@page import="com.sinosoft.lis.schema.LSQuotEngineeringSchema"%>
<%@page import="com.sinosoft.lis.schema.LSQuotEnginDetailSchema"%>
<%@page import="com.sinosoft.lis.vschema.LSQuotEnginDetailSet"%>
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
			String tInsuPeriod = request.getParameter("InsuPeriod");
			String tInsuPeriodFlag = request.getParameter("InsuPeriodFlag");
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
			tLSQuotPlanSchema.setInsuPeriod(tInsuPeriod);
			tLSQuotPlanSchema.setInsuPeriodFlag(tInsuPeriodFlag);
			tLSQuotPlanSchema.setOccupTypeFlag(tOccupTypeFlag);
			tLSQuotPlanSchema.setMinOccupType(tMinOccupType);
			tLSQuotPlanSchema.setMaxOccupType(tMaxOccupType);
			tLSQuotPlanSchema.setOccupRate(tOccupRate.replaceAll("：", ":"));
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
			
			if ("01".equals(tTranProdType) && !"DELPLAN".equals(tOperate)) {//项目询价建工险额外获取工程信息
				
				String tEnginType = request.getParameter("EnginType");
				String tEnginDays = request.getParameter("EnginDays");
				String tMinEnginArea = tLSQuotPubFun.isNumNull(request.getParameter("EnginArea"));//最低工程面积
				String tMinEnginCost = tLSQuotPubFun.isNumNull(request.getParameter("EnginCost"));//最低工程造价
				String tMaxEnginArea = tLSQuotPubFun.isNumNull(request.getParameter("MaxEnginArea"));//最高工程面积
				String tMaxEnginCost = tLSQuotPubFun.isNumNull(request.getParameter("MaxEnginCost"));//最高工程造价
				
				String tEnginDesc = request.getParameter("EnginDesc");
				String tEnginCondition = request.getParameter("EnginCondition");
				
				LSQuotEngineeringSchema tLSQuotEngineeringSchema = new LSQuotEngineeringSchema();
				tLSQuotEngineeringSchema.setQuotNo(tQuotNo);
				tLSQuotEngineeringSchema.setQuotBatNo(tQuotBatNo);
				tLSQuotEngineeringSchema.setEnginType(tEnginType);
				tLSQuotEngineeringSchema.setEnginDays(tEnginDays);
				tLSQuotEngineeringSchema.setMinEnginArea(tMinEnginArea);//最低工程面积
				tLSQuotEngineeringSchema.setMinEnginCost(tMinEnginCost);//最低工程造价
				tLSQuotEngineeringSchema.setEnginArea(tMaxEnginArea);//原EnginArea字段存 最高工程面积
				tLSQuotEngineeringSchema.setEnginCost(tMaxEnginCost);//原EnginCost字段存 最高工程造价
				tLSQuotEngineeringSchema.setEnginDesc(tEnginDesc);
				tLSQuotEngineeringSchema.setEnginCondition(tEnginCondition);
				
				tVData.add(tLSQuotEngineeringSchema);
				
				String tSql = "select a.codetype,a.code from ldcode a where 1=1 and a.codetype='engindetail'";
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(tSql);
				SSRS tSSRS = new SSRS();
				ExeSQL tExeSQL = new ExeSQL();
				tSSRS = tExeSQL.execSQL(sqlbv);
				
				LSQuotEnginDetailSet tLSQuotEnginDetailSet = new LSQuotEnginDetailSet();
				if (tSSRS==null || tSSRS.getMaxRow()==0) {
					tContent = "获取基础信息失败！";
					tFlagStr = "Fail";
				} else {

					for (int i=1; i<=tSSRS.getMaxRow(); i++) {
					
						String tElementCode = tSSRS.GetText(i, 1)+tSSRS.GetText(i, 2);
						String tElementValue = request.getParameter(tElementCode +"Value");
						
						String isChecked = request.getParameter(tElementCode);
						
						if (isChecked!=null && "on".equals(isChecked)) {
							
							LSQuotEnginDetailSchema tLSQuotEnginDetailSchema = new LSQuotEnginDetailSchema();
							tLSQuotEnginDetailSchema.setQuotNo(tQuotNo);
							tLSQuotEnginDetailSchema.setQuotBatNo(tQuotBatNo);
							tLSQuotEnginDetailSchema.setEnginCode(tSSRS.GetText(i, 2));
							tLSQuotEnginDetailSchema.setOtherDesc(tElementValue);
      	
							tLSQuotEnginDetailSet.add(tLSQuotEnginDetailSchema);
						}
					}
				}
				
				tVData.add(tLSQuotEnginDetailSet);
			}
			
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
