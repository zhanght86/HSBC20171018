<%
/***************************************************************
 * <p>ProName：LCContTryCalSave.jsp</p>
 * <p>Title：方案试算</p>
 * <p>Description：方案试算</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-06-10
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LWMissionSchema"%>
<%@page import="com.sinosoft.lis.schema.LCContPlanDetailSchema"%>
<%@page import="com.sinosoft.lis.schema.LCContPlanDetailSubSchema"%>
<%@page import="com.sinosoft.lis.schema.LCContPlanDetailRelaSubSchema"%>
<%@page import="com.sinosoft.lis.g_app.LCContPubFun"%>
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
		
			tOperate = request.getParameter("Operate");
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
		
			String tPolicyNo = request.getParameter("PolicyNo");
			String tGrpPropNo = request.getParameter("GrpPropNo");
			if(tGrpPropNo!=null && !"-1".equals(tGrpPropNo.indexOf("_"))&& tGrpPropNo.indexOf("_")>0){
				tGrpPropNo = tGrpPropNo.substring(0, tGrpPropNo.indexOf("_"));
			}
			String tSysPlanCode = request.getParameter("SysPlanCode");
			String tPlanCode = request.getParameter("PlanCode");
			String tContPlanType = request.getParameter("ContPlanType");
			String tPlanType = request.getParameter("PlanType");
			
			LCContPubFun tLCContPubFun = new LCContPubFun();
			LCContPlanDetailSchema tLCContPlanDetailSchema = new LCContPlanDetailSchema();
			LCContPlanDetailSubSchema tLCContPlanDetailSubSchema = new LCContPlanDetailSubSchema();
			LCContPlanDetailRelaSubSchema tLCContPlanDetailRelaSubSchema = new LCContPlanDetailRelaSubSchema();
			
			//获取固定域
			String tRiskCode = request.getParameter("RiskCode");
			String tDutyCode = request.getParameter("DutyCode");
			String tAmntType = request.getParameter("AmntType");
			String tFixedAmnt = request.getParameter("FixedAmnt");
			String tSalaryMult = request.getParameter("SalaryMult");
			String tMaxAmnt = request.getParameter("MaxAmnt");
			String tMinAmnt = request.getParameter("MinAmnt");
			String tExceptPremType = request.getParameter("ExceptPremType");
			String tExceptPrem = request.getParameter("ExceptPrem");
			String tInitPrem = request.getParameter("InitPrem");
			String tExceptYield = request.getParameter("ExceptYield");
			String tRelaShareFlag = request.getParameter("RelaShareFlag");
			String tRelaToMain = request.getParameter("RelaToMain");
			String tMainAmntRate = request.getParameter("MainAmntRate");
			String tRelaAmntRate = request.getParameter("RelaAmntRate");
			
			if (!"1".equals(tRelaShareFlag)) {
			
				tRelaToMain = "";
				tMainAmntRate = "-1";
				tRelaAmntRate = "-1";
			}
			
			tLCContPlanDetailSchema.setPolicyNo(tPolicyNo);
			tLCContPlanDetailSchema.setSysPlanCode(tSysPlanCode);
			tLCContPlanDetailSchema.setPlanCode(tPlanCode);
			tLCContPlanDetailSchema.setRiskCode(tRiskCode);
			tLCContPlanDetailSchema.setDutyCode(tDutyCode);
			tLCContPlanDetailSchema.setAmntType(tAmntType);
			tLCContPlanDetailSchema.setFixedAmnt(tFixedAmnt);
			tLCContPlanDetailSchema.setSalaryMult(tSalaryMult);
			tLCContPlanDetailSchema.setMaxAmnt(tMaxAmnt);
			tLCContPlanDetailSchema.setMinAmnt(tMinAmnt);
			tLCContPlanDetailSchema.setExceptPremType(tExceptPremType);
			tLCContPlanDetailSchema.setExceptPrem(tExceptPrem);
			tLCContPlanDetailSchema.setInitPrem(tInitPrem);
			tLCContPlanDetailSchema.setExceptYield(tExceptYield);
			tLCContPlanDetailSchema.setRelaShareFlag(tRelaShareFlag);
			
			//查询出动态域字段,获取动态域值
			//select a.calfactor,b.factorcode,decode(b.factorname,null,a.factorname,'',a.factorname,b.factorname),b.fieldtype,b.valuetype,a.calsql,b.fieldlength from lmriskdutyfactor a,lmdutyfactorrelaconfig b where 1=1 and a.calfactor=b.calfactor {0} {1} order by a.factororder
			String tSql = "select b.factorcode, b.fieldcode, b.factorname, b.fieldtype, b.valuetype, '', b.valuelength, b.valuescope "
				+" from lmdutyfactorrela a, lmfactorconfig b "
				+" where 1=1 and a.factorcode=b.factorcode and a.riskcode='"+ tRiskCode +"' and a.dutycode='"+ tDutyCode +"' "
				+" order by a.factororder ";
			
	
			tLCContPlanDetailSubSchema.setPolicyNo(tPolicyNo);
			tLCContPlanDetailSubSchema.setSysPlanCode(tSysPlanCode);
			tLCContPlanDetailSubSchema.setPlanCode(tPlanCode);
			tLCContPlanDetailSubSchema.setRiskCode(tRiskCode);
			tLCContPlanDetailSubSchema.setDutyCode(tDutyCode);
			
			tLCContPlanDetailRelaSubSchema.setPolicyNo(tPolicyNo);
			tLCContPlanDetailRelaSubSchema.setSysPlanCode(tSysPlanCode);
			tLCContPlanDetailRelaSubSchema.setPlanCode(tPlanCode);
			tLCContPlanDetailRelaSubSchema.setRiskCode(tRiskCode);
			tLCContPlanDetailRelaSubSchema.setDutyCode(tDutyCode);
			tLCContPlanDetailRelaSubSchema.setRelaToMain(tRelaToMain);
			tLCContPlanDetailRelaSubSchema.setMainAmntRate(tMainAmntRate);
			tLCContPlanDetailRelaSubSchema.setRelaAmntRate(tRelaAmntRate);
			
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(tSql);
			
			TransferData tTransferData = new TransferData();
			
			if (tSSRS==null || tSSRS.getMaxRow()==0) {
			
			} else {
				
				String[][] tArr = new String[tSSRS.getMaxRow()][2];

				for (int i=1;i<=tSSRS.getMaxRow(); i++) {
					
					tArr[i-1][0] = tSSRS.GetText(i, 1);
					String tFactorCode = tSSRS.GetText(i, 2);
					String tValue = request.getParameter(tFactorCode);
					tArr[i-1][1] = tValue;
				}
				
				tTransferData.setNameAndValue("DetailSubArr", tArr);
			}
			
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			
			tLWMissionSchema.setMissionID(tMissionID);
			tLWMissionSchema.setSubMissionID(tSubMissionID);
			tLWMissionSchema.setActivityID(tActivityID);
			tLWMissionSchema.setMissionProp1(tPolicyNo);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLWMissionSchema);
			tVData.add(tLCContPlanDetailSchema);
			tVData.add(tLCContPlanDetailSubSchema);
			tVData.add(tLCContPlanDetailRelaSubSchema);
			
			tTransferData.setNameAndValue("ContPlanType", tContPlanType);
			tTransferData.setNameAndValue("PlanType", tPlanType);
			
			tVData.add(tTransferData);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LCContTryCalUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tFlagStr = "Succ";
				tContent = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 1);
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "处理异常，请联系系统运维人员！";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmitTryCal("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>
