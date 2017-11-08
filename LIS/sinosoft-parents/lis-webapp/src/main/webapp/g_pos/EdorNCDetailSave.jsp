<%
/***************************************************************
 * <p>ProName：EdorNCDetailSave.jsp</p>
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
<%@page import="com.sinosoft.lis.schema.LPContPlanDetailSubSchema"%>
<%@page import="com.sinosoft.lis.schema.LPContPlanDetailRelaSubSchema"%>
<%@page import="com.sinosoft.lis.schema.LPContPlanDetailSchema"%>
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
		
			tOperate = request.getParameter("Operate");
			String tPolicyNo = request.getParameter("PolicyNo");
			String tEdorNo = request.getParameter("EdorNo");
			String tEdorType = request.getParameter("EdorType");
			String tSysPlanCode = request.getParameter("SysPlanCode");
			String tPlanCode = request.getParameter("PlanCode");
			String tContPlanType = request.getParameter("ContPlanType");
			String tPlanType = request.getParameter("PlanType");
			String tPremCalType = request.getParameter("PremCalType");
			String tPlanFlag = request.getParameter("PlanFlag");
			String tOccupTypeFlag = request.getParameter("OccupTypeFlag");
			
			LPContPlanDetailSchema tLPContPlanDetailSchema = new LPContPlanDetailSchema();
			LPContPlanDetailSubSchema tLPContPlanDetailSubSchema = new LPContPlanDetailSubSchema();
			LPContPlanDetailRelaSubSchema tLPContPlanDetailRelaSubSchema = new LPContPlanDetailRelaSubSchema();
			
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
			String tPropNo = mExeSQL.getOneValue("select grppropno from lcproposaltocont where grpcontno='"+tPolicyNo+"'");
			
			if (!"1".equals(tRelaShareFlag)) {
			
				tRelaToMain = "";
				tMainAmntRate = "-1";
				tRelaAmntRate = "-1";
			}
			
			String tRemark = request.getParameter("Remark");
			TransferData tTransferData = new TransferData();
			tLPContPlanDetailSchema.setEdorNo(tEdorNo);
			tLPContPlanDetailSchema.setEdorType(tEdorType);
			tLPContPlanDetailSchema.setPolicyNo(tPolicyNo);
			tLPContPlanDetailSchema.setPropNo(tPropNo);
			tLPContPlanDetailSchema.setSysPlanCode(tSysPlanCode);
			tLPContPlanDetailSchema.setPlanCode(tPlanCode);
			tLPContPlanDetailSchema.setRiskCode(tRiskCode);
			tLPContPlanDetailSchema.setDutyCode(tDutyCode);
			tLPContPlanDetailSchema.setAmntType(tAmntType);
			tLPContPlanDetailSchema.setFixedAmnt(tFixedAmnt);
			tLPContPlanDetailSchema.setSalaryMult(tSalaryMult);
			tLPContPlanDetailSchema.setMaxAmnt(tMaxAmnt);
			tLPContPlanDetailSchema.setMinAmnt(tMinAmnt);
			tLPContPlanDetailSchema.setExceptPremType(tExceptPremType);
			tLPContPlanDetailSchema.setExceptPrem(tExceptPrem);
			tLPContPlanDetailSchema.setInitPrem(tInitPrem);
			tLPContPlanDetailSchema.setExceptYield(tExceptYield);
			tLPContPlanDetailSchema.setRelaShareFlag(tRelaShareFlag);
			tLPContPlanDetailSchema.setRemark(tRemark);
			
			//查询出动态域字段,获取动态域值
//			String tSql = "select b.factorcode, b.fieldcode, b.factorname, b.fieldtype, b.valuetype, '', b.valuelength, b.valuescope "
//				+" from lmdutyfactorrela a, lmfactorconfig b "
//				+" where 1=1 and a.factorcode=b.factorcode and a.riskcode='"+ tRiskCode +"' and a.dutycode='"+ tDutyCode +"' "
//				+" order by a.factororder ";

			tLPContPlanDetailSubSchema.setEdorNo(tEdorNo);
			tLPContPlanDetailSubSchema.setEdorType(tEdorType);
			tLPContPlanDetailSubSchema.setPolicyNo(tPolicyNo);
			tLPContPlanDetailSubSchema.setPropNo(tPropNo);
			tLPContPlanDetailSubSchema.setSysPlanCode(tSysPlanCode);
			tLPContPlanDetailSubSchema.setPlanCode(tPlanCode);
			tLPContPlanDetailSubSchema.setRiskCode(tRiskCode);
			tLPContPlanDetailSubSchema.setDutyCode(tDutyCode);
			
			tLPContPlanDetailRelaSubSchema.setEdorNo(tEdorNo);
			tLPContPlanDetailRelaSubSchema.setEdorType(tEdorType);
			tLPContPlanDetailRelaSubSchema.setPolicyNo(tPolicyNo);
			tLPContPlanDetailRelaSubSchema.setPropNo(tPropNo);
			tLPContPlanDetailRelaSubSchema.setSysPlanCode(tSysPlanCode);
			tLPContPlanDetailRelaSubSchema.setPlanCode(tPlanCode);
			tLPContPlanDetailRelaSubSchema.setRiskCode(tRiskCode);
			tLPContPlanDetailRelaSubSchema.setDutyCode(tDutyCode);
			tLPContPlanDetailRelaSubSchema.setRelaToMain(tRelaToMain);
			tLPContPlanDetailRelaSubSchema.setRelaAmntRate(tRelaAmntRate);
			tLPContPlanDetailRelaSubSchema.setMainAmntRate(tMainAmntRate);
			//--LJL
			String tSql = "select b.factorcode, b.fieldcode, b.factorname, b.fieldtype, b.valuetype, '', b.valuelength, b.valuescope "
				+" from lmdutyfactorrela a, lmfactorconfig b "
				+" where 1=1 and a.factorcode=b.factorcode and a.riskcode='"+ "?tRiskCode?" +"' and a.dutycode='"+ "?tDutyCode?" +"' "
				+" order by a.factororder ";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
	        sqlbv.sql(tSql);
	        sqlbv.put("tRiskCode", tRiskCode);
	        sqlbv.put("tDutyCode", tDutyCode);
	        //--LJL
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS==null || tSSRS.getMaxRow()==0) {
				tTransferData.setNameAndValue("DetailSubArr", null);
			} else {
				String[][] tArr = new String[tSSRS.getMaxRow()][2];
				for (int i=1;i<=tSSRS.getMaxRow(); i++) {
					
					String tFactorCode = tSSRS.GetText(i, 2);
					String tValue = request.getParameter(tFactorCode);
					tLPContPlanDetailSubSchema.setV(tFactorCode, tValue);
					
					tArr[i-1][0] = tSSRS.GetText(i, 1);
					tArr[i-1][1] = tValue;
					
				}
				tTransferData.setNameAndValue("DetailSubArr", tArr);
				if ("1".equals(tRelaShareFlag)) {

					for (int i=1;i<=tSSRS.getMaxRow(); i++) {
					
						String tFactorCode = tSSRS.GetText(i, 2);
						String tValue = request.getParameter("Relation"+tFactorCode);
						tLPContPlanDetailRelaSubSchema.setV(tFactorCode, tValue);
					}
				}
			}
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLPContPlanDetailSchema);
			tVData.add(tLPContPlanDetailSubSchema);
			tVData.add(tLPContPlanDetailRelaSubSchema);
			
			tTransferData.setNameAndValue("ContPlanType", tContPlanType);
			tTransferData.setNameAndValue("PlanType", tPlanType);
			tTransferData.setNameAndValue("PolicyNo", tPolicyNo);
			tTransferData.setNameAndValue("EdorNo", tEdorNo);
			tTransferData.setNameAndValue("EdorType", tEdorType);
			
			String tOSysPlanCode = request.getParameter("OSysPlanCode");
			String tOPlanCode = request.getParameter("OPlanCode");
			String tORiskCode = request.getParameter("ORiskCode");
			String tODutyCode = request.getParameter("ODutyCode");
			
			tTransferData.setNameAndValue("OSysPlanCode", tOSysPlanCode);
			tTransferData.setNameAndValue("OPlanCode", tOPlanCode);
			tTransferData.setNameAndValue("ORiskCode", tORiskCode);
			tTransferData.setNameAndValue("ODutyCode", tODutyCode);
			
			tVData.add(tTransferData);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorNCDetailUI")) {
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
