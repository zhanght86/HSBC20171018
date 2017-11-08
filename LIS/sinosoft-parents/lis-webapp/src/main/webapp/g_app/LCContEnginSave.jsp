<%
/***************************************************************
 * <p>ProName：LCContPlanEnginSave.jsp</p>
 * <p>Title：方案录入工程信息保存</p>
 * <p>Description：方案录入工程信息保存</p>
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
<%@page import="com.sinosoft.lis.schema.LCContPlanEngineeringSchema"%>
<%@page import="com.sinosoft.lis.schema.LCContPlanEnginDetailSchema"%>
<%@page import="com.sinosoft.lis.vschema.LCContPlanEnginDetailSet"%>
<%@page import="com.sinosoft.lis.g_app.LCContPubFun"%>
<%@page import="com.sinosoft.lis.g_app.LCContPubConst"%>
<%@page import="com.sinosoft.lis.schema.LCProposalSchema"%>
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
			LCContPubConst pubConst = new LCContPubConst();
			tOperate = request.getParameter("Operate");
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tGrpPropNo = request.getParameter("GrpPropNo");
			String tContPlanType = request.getParameter("ContPlanType");
			
			String tEnginName = request.getParameter("EnginName");
			String tEnginType = request.getParameter("EnginType");
			String tEnginArea = tLSQuotPubFun.isNumNull(request.getParameter("EnginArea"));
			String tEnginCost = tLSQuotPubFun.isNumNull(request.getParameter("EnginCost"));
			String tEnginPlace = request.getParameter("EnginPlace");
			String tEnginStartDate = request.getParameter("EnginStartDate");
			String tEnginEndDate = request.getParameter("EnginEndDate");
			String tEnginDesc = request.getParameter("EnginDesc");
			String tEnginCondition = request.getParameter("EnginCondition");
			String tRemark = request.getParameter("Remark");
			String tInsurerName = request.getParameter("InsurerName");
			String tInsurerType = request.getParameter("InsurerType");
			String tContractorName = request.getParameter("ContractorName");
			String tContractorType = request.getParameter("ContractorType");
			
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			LCContPlanEngineeringSchema tLCContPlanEngineeringSchema = new LCContPlanEngineeringSchema();
			
			LCContPlanEnginDetailSet tLCContPlanEnginDetailSet = new LCContPlanEnginDetailSet();
			
			LCProposalSchema tLCProposalSchema = new LCProposalSchema();
			tLCProposalSchema.setGrpPropNo(tGrpPropNo);
			
			tLWMissionSchema.setMissionID(tMissionID);
			tLWMissionSchema.setSubMissionID(tSubMissionID);
			tLWMissionSchema.setActivityID(tActivityID);
			tLWMissionSchema.setMissionProp1(tGrpPropNo);
			tLWMissionSchema.setMissionProp2(tContPlanType);
			tLWMissionSchema.setMissionProp3("");
			
			tLCContPlanEngineeringSchema.setPolicyNo(tGrpPropNo);
			tLCContPlanEngineeringSchema.setPropNo(tGrpPropNo);
			tLCContPlanEngineeringSchema.setEnginName(tEnginName);
			tLCContPlanEngineeringSchema.setEnginType(tEnginType);
			tLCContPlanEngineeringSchema.setEnginArea(tEnginArea);
			tLCContPlanEngineeringSchema.setEnginCost(tEnginCost);
			tLCContPlanEngineeringSchema.setEnginPlace(tEnginPlace);
			tLCContPlanEngineeringSchema.setEnginStartDate(tEnginStartDate);
			tLCContPlanEngineeringSchema.setEnginEndDate(tEnginEndDate);
			tLCContPlanEngineeringSchema.setEnginDesc(tEnginDesc);
			tLCContPlanEngineeringSchema.setEnginCondition(tEnginCondition);
			tLCContPlanEngineeringSchema.setRemark(tRemark);
			tLCContPlanEngineeringSchema.setInsurerName(tInsurerName);
			tLCContPlanEngineeringSchema.setInsurerType(tInsurerType);
			tLCContPlanEngineeringSchema.setContractorName(tContractorName);
			tLCContPlanEngineeringSchema.setContractorType(tContractorType);
			
			String tSql = "select a.codetype,a.code from ldcode a where 1=1 and a.codetype='engindetail'";
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(tSql);
			
			if (tSSRS==null || tSSRS.getMaxRow()==0) {
				tContent = "获取基础信息失败！";
				tFlagStr = "Fail";
			} else {
				
				for (int i=1; i<=tSSRS.getMaxRow(); i++) {
				
					String tElementCode = tSSRS.GetText(i, 1)+tSSRS.GetText(i, 2);
					String tElementValue = request.getParameter(tElementCode +"Value");
					
					String isChecked = request.getParameter(tElementCode);
					
					if (isChecked!=null && "on".equals(isChecked)) {
						
						LCContPlanEnginDetailSchema tLCContPlanEnginDetailSchema = new LCContPlanEnginDetailSchema();
						tLCContPlanEnginDetailSchema.setPropNo(tGrpPropNo);
						tLCContPlanEnginDetailSchema.setPolicyNo(tGrpPropNo);
						tLCContPlanEnginDetailSchema.setPlanCode(pubConst.getEnginPlanCode());
						tLCContPlanEnginDetailSchema.setSysPlanCode(pubConst.getEnginSysPlanCode());
						tLCContPlanEnginDetailSchema.setEnginCode(tSSRS.GetText(i, 2));
						tLCContPlanEnginDetailSchema.setOtherDesc(tElementValue);

						tLCContPlanEnginDetailSet.add(tLCContPlanEnginDetailSchema);
					}
				}
			}
			TransferData transferData = new TransferData();
			transferData.setNameAndValue("ContPlanType", tContPlanType);
			transferData.setNameAndValue("GrpPropNo", tGrpPropNo);
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLWMissionSchema);
			tVData.add(tLCContPlanEngineeringSchema);
			tVData.add(tLCContPlanEnginDetailSet);
			tVData.add(tContPlanType);
			tVData.add(transferData);
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
<script language="JavaScript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>
