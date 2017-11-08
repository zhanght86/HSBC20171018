<%
/***************************************************************
 * <p>ProName：LSQuotPlanAllDetailSave.jsp</p>
 * <p>Title：方案明细一览</p>
 * <p>Description：方案明细一览</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-05-06
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LWMissionSchema"%>
<%@page import="com.sinosoft.lis.schema.LSQuotPlanDetailSchema"%>
<%@page import="com.sinosoft.lis.vschema.LSQuotPlanDetailSet"%>
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
			
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			String tQuotType = request.getParameter("QuotType");
			String tSysPlanCode = request.getParameter("SysPlanCode");
			String tPlanCode = request.getParameter("PlanCode");
			String tTranProdType = request.getParameter("TranProdType");
			String tDetailRows = request.getParameter("DetailRows");
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("TranProdType", tTranProdType);
			tTransferData.setNameAndValue("DetailRows", tDetailRows);
			
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			
			tLWMissionSchema.setMissionID(tMissionID);
			tLWMissionSchema.setSubMissionID(tSubMissionID);
			tLWMissionSchema.setActivityID(tActivityID);
			tLWMissionSchema.setMissionProp1(tQuotNo);
			tLWMissionSchema.setMissionProp2(tQuotBatNo);
			tLWMissionSchema.setMissionProp3(tQuotType);
			
			//重新获取方案中险种信息,该处需与页面查询出的排序条件保持一致
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			LSQuotPlanDetailSet tLSQuotPlanDetailSet = new LSQuotPlanDetailSet();

			StringBuffer tStrBuff = new StringBuffer();
			tStrBuff.append(" select a.riskcode,a.dutycode from lsquotplandetail a ");
			tStrBuff.append(" where 1=1 ");
			tStrBuff.append(" and a.quotno='"+ "?tQuotNo?" +"' and a.quotbatno='"+ "?tQuotBatNo?" +"'");
			tStrBuff.append(" and a.sysplancode='"+ "?tSysPlanCode?" +"' and a.plancode='"+ "?tPlanCode?" +"' ");
			tStrBuff.append(" order by a.riskcode, a.dutycode");
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tStrBuff.toString());
			sqlbv1.put("tQuotNo",tQuotNo);
			sqlbv1.put("tQuotBatNo",tQuotBatNo);
			sqlbv1.put("tSysPlanCode",tSysPlanCode);
			sqlbv1.put("tPlanCode",tPlanCode);
			tSSRS = tExeSQL.execSQL(sqlbv1);
			if (tSSRS==null || tSSRS.getMaxRow()==0) {
			
			} else {
				
				String tRiskCode;
				String tDutyCode;
				for (int i=1; i<=tSSRS.getMaxRow(); i++) {
				
					LSQuotPlanDetailSchema tLSQuotPlanDetailSchema = new LSQuotPlanDetailSchema();
				
					tRiskCode = tSSRS.GetText(i, 1);
					tDutyCode = tSSRS.GetText(i, 2);
					
					String tPageRiskCode = request.getParameter("RiskCode"+ tRiskCode + tDutyCode + tSysPlanCode);
					String tPageDutyCode = request.getParameter("DutyCode"+ tRiskCode + tDutyCode + tSysPlanCode);
					String tExceptPremType = request.getParameter("ExceptPremType"+ tRiskCode + tDutyCode + tSysPlanCode);//期望类型
					String tExceptPrem = request.getParameter("ExceptPrem"+ tRiskCode + tDutyCode + tSysPlanCode);//期望保费
					String tSubUWValue = request.getParameter("SubUWValue"+ tRiskCode + tDutyCode + tSysPlanCode);//中支
					String tBranchUWValue = request.getParameter("BranchUWValue"+ tRiskCode + tDutyCode + tSysPlanCode);//分核
					String tUWValue = request.getParameter("UWValue"+ tRiskCode + tDutyCode + tSysPlanCode);//总核
					
					tLSQuotPlanDetailSchema.setQuotNo(tQuotNo);
					tLSQuotPlanDetailSchema.setQuotBatNo(tQuotBatNo);
					tLSQuotPlanDetailSchema.setSysPlanCode(tSysPlanCode);
					tLSQuotPlanDetailSchema.setPlanCode(tPlanCode);
					tLSQuotPlanDetailSchema.setRiskCode(tPageRiskCode);
					tLSQuotPlanDetailSchema.setDutyCode(tPageDutyCode);
					tLSQuotPlanDetailSchema.setExceptPremType(tExceptPremType);
					tLSQuotPlanDetailSchema.setExceptPrem(tExceptPrem);
					tLSQuotPlanDetailSchema.setSubUWValue(tSubUWValue);
					tLSQuotPlanDetailSchema.setBranchUWValue(tBranchUWValue);
					tLSQuotPlanDetailSchema.setUWValue(tUWValue);
					
					tLSQuotPlanDetailSet.add(tLSQuotPlanDetailSchema);
				}
			}
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLWMissionSchema);
			tVData.add(tLSQuotPlanDetailSet);
			tVData.add(tTransferData);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotPlanAllDetailUI")) {
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
