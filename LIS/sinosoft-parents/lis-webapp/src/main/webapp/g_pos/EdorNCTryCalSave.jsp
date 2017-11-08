<%
/***************************************************************
 * <p>ProName：EdorTryCalSave.jsp</p>
 * <p>Title：方案试算</p>
 * <p>Description：方案试算</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-06-10
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LPContPlanDetailSchema"%>
<%@page import="com.sinosoft.lis.schema.LPContPlanDetailSubSchema"%>
<%@page import="com.sinosoft.lis.schema.LPContPlanDetailRelaSubSchema"%>
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
		
			String tPolicyNo = request.getParameter("PolicyNo");
			String tEdorNo = request.getParameter("EdorNo");
			String tEdorType = request.getParameter("EdorType");
			String tSysPlanCode = request.getParameter("SysPlanCode");
			String tPlanCode = request.getParameter("PlanCode");
			String tContPlanType = request.getParameter("ContPlanType");
			String tPlanType = request.getParameter("PlanType");
			
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
			
			if (!"1".equals(tRelaShareFlag)) {
			
				tRelaToMain = "";
				tMainAmntRate = "-1";
				tRelaAmntRate = "-1";
			}
			
			tLPContPlanDetailSchema.setPolicyNo(tPolicyNo);
			tLPContPlanDetailSchema.setEdorNo(tEdorNo);
			tLPContPlanDetailSchema.setEdorType(tEdorType);
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
			
			//查询出动态域字段,获取动态域值
			//select a.calfactor,b.factorcode,decode(b.factorname,null,a.factorname,'',a.factorname,b.factorname),b.fieldtype,b.valuetype,a.calsql,b.fieldlength from lmriskdutyfactor a,lmdutyfactorrelaconfig b where 1=1 and a.calfactor=b.calfactor {0} {1} order by a.factororder
//			String tSql = "select b.factorcode, b.fieldcode, b.factorname, b.fieldtype, b.valuetype, '', b.valuelength, b.valuescope "
//				+" from lmdutyfactorrela a, lmfactorconfig b "
//				+" where 1=1 and a.factorcode=b.factorcode and a.riskcode='"+ tRiskCode +"' and a.dutycode='"+ tDutyCode +"' "
//				+" order by a.factororder ";
			
	
			tLPContPlanDetailSubSchema.setPolicyNo(tPolicyNo);
			tLPContPlanDetailSubSchema.setEdorNo(tEdorNo);
			tLPContPlanDetailSubSchema.setEdorType(tEdorType);
			tLPContPlanDetailSubSchema.setSysPlanCode(tSysPlanCode);
			tLPContPlanDetailSubSchema.setPlanCode(tPlanCode);
			tLPContPlanDetailSubSchema.setRiskCode(tRiskCode);
			tLPContPlanDetailSubSchema.setDutyCode(tDutyCode);
			
			tLPContPlanDetailRelaSubSchema.setPolicyNo(tPolicyNo);
			tLPContPlanDetailRelaSubSchema.setEdorNo(tEdorNo);
			tLPContPlanDetailRelaSubSchema.setEdorType(tEdorType);
			tLPContPlanDetailRelaSubSchema.setSysPlanCode(tSysPlanCode);
			tLPContPlanDetailRelaSubSchema.setPlanCode(tPlanCode);
			tLPContPlanDetailRelaSubSchema.setRiskCode(tRiskCode);
			tLPContPlanDetailRelaSubSchema.setDutyCode(tDutyCode);
			tLPContPlanDetailRelaSubSchema.setRelaToMain(tRelaToMain);
			tLPContPlanDetailRelaSubSchema.setMainAmntRate(tMainAmntRate);
			tLPContPlanDetailRelaSubSchema.setRelaAmntRate(tRelaAmntRate);
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
			
			tVData.add(tTransferData);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorNCTryCalUI")) {
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
