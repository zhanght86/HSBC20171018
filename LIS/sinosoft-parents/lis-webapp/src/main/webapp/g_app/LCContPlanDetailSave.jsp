<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%
/***************************************************************
 * <p>ProName：LCContPlanDetailSave.jsp</p>
 * <p>Title：方案明细信息保存</p>
 * <p>Description：方案明细信息保存</p>
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
			String tPolicyNo = request.getParameter("GrpPropNo");
			String tGrpPropNo = request.getParameter("GrpPropNo");

			if(tGrpPropNo!=null && !"-1".equals(tGrpPropNo.indexOf("_"))&& tGrpPropNo.indexOf("_")>0){
				tGrpPropNo = tGrpPropNo.substring(0, tGrpPropNo.indexOf("_"));
			}
			String tSysPlanCode = request.getParameter("SysPlanCode");
			String tPlanCode = request.getParameter("PlanCode");
			String tContPlanType = request.getParameter("ContPlanType");
			String tPlanType = request.getParameter("PlanType");
			String tPremCalType = request.getParameter("PremCalType");
			String tPlanFlag = request.getParameter("PlanFlag");
			String tOccupTypeFlag = request.getParameter("OccupTypeFlag");
			
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
			
			String tRemark = request.getParameter("Remark");
			TransferData tTransferData = new TransferData();
			//查询出动态域字段,获取动态域值
			//if ("ADDPLANDETAIL".equals(tOperate) || "MODIFYPLANDETAIL".equals(tOperate)) {
				tLCContPlanDetailSchema.setPolicyNo(tPolicyNo);
				tLCContPlanDetailSchema.setPropNo(tGrpPropNo);
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
				tLCContPlanDetailSchema.setRemark(tRemark);
				
				//查询出动态域字段,获取动态域值
				String tSql = "select b.factorcode, b.fieldcode, b.factorname, b.fieldtype, b.valuetype, '', b.valuelength, b.valuescope "
					+" from lmdutyfactorrela a, lmfactorconfig b "
					+" where 1=1 and a.factorcode=b.factorcode and a.riskcode='"+ tRiskCode +"' and a.dutycode='"+ tDutyCode +"' "
					+" order by a.factororder ";
				
				tLCContPlanDetailSubSchema.setPolicyNo(tPolicyNo);
				tLCContPlanDetailSubSchema.setPropNo(tGrpPropNo);
				tLCContPlanDetailSubSchema.setSysPlanCode(tSysPlanCode);
				tLCContPlanDetailSubSchema.setPlanCode(tPlanCode);
				tLCContPlanDetailSubSchema.setRiskCode(tRiskCode);
				tLCContPlanDetailSubSchema.setDutyCode(tDutyCode);
				
				tLCContPlanDetailRelaSubSchema.setPolicyNo(tPolicyNo);
				tLCContPlanDetailRelaSubSchema.setPropNo(tGrpPropNo);
				tLCContPlanDetailRelaSubSchema.setSysPlanCode(tSysPlanCode);
				tLCContPlanDetailRelaSubSchema.setPlanCode(tPlanCode);
				tLCContPlanDetailRelaSubSchema.setRiskCode(tRiskCode);
				tLCContPlanDetailRelaSubSchema.setDutyCode(tDutyCode);
				tLCContPlanDetailRelaSubSchema.setRelaToMain(tRelaToMain);
				tLCContPlanDetailRelaSubSchema.setRelaAmntRate(tRelaAmntRate);
				tLCContPlanDetailRelaSubSchema.setMainAmntRate(tMainAmntRate);
				
				SSRS tSSRS = new SSRS();
				ExeSQL tExeSQL = new ExeSQL();
				tSSRS = tExeSQL.execSQL(tSql);
				if (tSSRS==null || tSSRS.getMaxRow()==0) {
					tTransferData.setNameAndValue("DetailSubArr", null);
				} else {
					String[][] tArr = new String[tSSRS.getMaxRow()][2];
					for (int i=1;i<=tSSRS.getMaxRow(); i++) {
						
						String tFactorCode = tSSRS.GetText(i, 2);
						String tValue = "";
						//add by dianj 增加公共保额处理 20150325
						if ("P16".equals(tFactorCode)) {//保费计算方式
							tValue = request.getParameter("PremCalWay");
						} else if ("P17".equals(tFactorCode)) {//人均保费
							tValue = request.getParameter("PerPrem");
						} else if ("P18".equals(tFactorCode)) {//标准人均保费 初始默认 -1，后续计算后更新
							tValue = "-1";
						} else  {
							tValue = request.getParameter(tFactorCode);
						}
						
						tLCContPlanDetailSubSchema.setV(tFactorCode, tValue);
						
						tArr[i-1][0] = tSSRS.GetText(i, 1);
						tArr[i-1][1] = tValue;
						
					}
					tTransferData.setNameAndValue("DetailSubArr", tArr);
					if ("1".equals(tRelaShareFlag)) {
	
						for (int i=1;i<=tSSRS.getMaxRow(); i++) {
						
							String tFactorCode = tSSRS.GetText(i, 2);
							String tValue = request.getParameter("Relation"+tFactorCode);
							tLCContPlanDetailRelaSubSchema.setV(tFactorCode, tValue);
						}
					}
				}
			//}
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			
			tLWMissionSchema.setMissionID(tMissionID);
			tLWMissionSchema.setSubMissionID(tSubMissionID);
			tLWMissionSchema.setActivityID(tActivityID);
			tLWMissionSchema.setMissionProp1(tGrpPropNo);
			tLWMissionSchema.setMissionProp2(tContPlanType);
			tLWMissionSchema.setMissionProp3(tGrpPropNo);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLWMissionSchema);
			tVData.add(tLCContPlanDetailSchema);
			tVData.add(tLCContPlanDetailSubSchema);
			tVData.add(tLCContPlanDetailRelaSubSchema);
			
			tTransferData.setNameAndValue("ContPlanType", tContPlanType);
			tTransferData.setNameAndValue("PlanType", tPlanType);
			
			//if ("MODIFYPLANDETAIL".equals(tOperate)) {//修改时获取原数据记录
		
				String tOSysPlanCode = request.getParameter("OSysPlanCode");
				String tOPlanCode = request.getParameter("OPlanCode");
				String tORiskCode = request.getParameter("ORiskCode");
				String tODutyCode = request.getParameter("ODutyCode");
				
				tTransferData.setNameAndValue("OSysPlanCode", tOSysPlanCode);
				tTransferData.setNameAndValue("OPlanCode", tOPlanCode);
				tTransferData.setNameAndValue("ORiskCode", tORiskCode);
				tTransferData.setNameAndValue("ODutyCode", tODutyCode);
			//}
			tVData.add(tTransferData);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LCContPlanDetailUI")) {
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
