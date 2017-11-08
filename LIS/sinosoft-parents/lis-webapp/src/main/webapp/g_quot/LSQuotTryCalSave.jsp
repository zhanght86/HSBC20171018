<%
/***************************************************************
 * <p>ProName：LSQuotPlanDetailSave.jsp</p>
 * <p>Title：方案明细信息保存</p>
 * <p>Description：方案明细信息保存</p>
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
<%@page import="com.sinosoft.lis.schema.LSQuotPlanDetailSchema"%>
<%@page import="com.sinosoft.lis.schema.LSQuotPlanDetailSubSchema"%>
<%@page import="com.sinosoft.lis.schema.LSQuotPlanDetailRelaSubSchema"%>
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
		
			tOperate = request.getParameter("Operate");
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			String tQuotType = request.getParameter("QuotType");
			String tSysPlanCode = request.getParameter("SysPlanCode");
			String tPlanCode = request.getParameter("PlanCode");
			String tTranProdType = request.getParameter("TranProdType");
			String tPlanType = request.getParameter("PlanType");
			
			LSQuotPubFun tLSQuotPubFun = new LSQuotPubFun();
			LSQuotPlanDetailSchema tLSQuotPlanDetailSchema = new LSQuotPlanDetailSchema();
			LSQuotPlanDetailSubSchema tLSQuotPlanDetailSubSchema = new LSQuotPlanDetailSubSchema();
			LSQuotPlanDetailRelaSubSchema tLSQuotPlanDetailRelaSubSchema = new LSQuotPlanDetailRelaSubSchema();
			
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
			
			tLSQuotPlanDetailSchema.setQuotNo(tQuotNo);
			tLSQuotPlanDetailSchema.setQuotBatNo(tQuotBatNo);
			tLSQuotPlanDetailSchema.setSysPlanCode(tSysPlanCode);
			tLSQuotPlanDetailSchema.setPlanCode(tPlanCode);
			tLSQuotPlanDetailSchema.setRiskCode(tRiskCode);
			tLSQuotPlanDetailSchema.setDutyCode(tDutyCode);
			tLSQuotPlanDetailSchema.setAmntType(tAmntType);
			tLSQuotPlanDetailSchema.setFixedAmnt(tFixedAmnt);
			tLSQuotPlanDetailSchema.setSalaryMult(tSalaryMult);
			tLSQuotPlanDetailSchema.setMaxAmnt(tMaxAmnt);
			tLSQuotPlanDetailSchema.setMinAmnt(tMinAmnt);
			tLSQuotPlanDetailSchema.setExceptPremType(tExceptPremType);
			tLSQuotPlanDetailSchema.setExceptPrem(tExceptPrem);
			tLSQuotPlanDetailSchema.setInitPrem(tInitPrem);
			tLSQuotPlanDetailSchema.setExceptYield(tExceptYield);
			tLSQuotPlanDetailSchema.setRelaShareFlag(tRelaShareFlag);
			System.out.println("1111");
			//查询出动态域字段,获取动态域值
			//select a.calfactor,b.factorcode,decode(b.factorname,null,a.factorname,'',a.factorname,b.factorname),b.fieldtype,b.valuetype,a.calsql,b.fieldlength from lmriskdutyfactor a,lmdutyfactorrelaconfig b where 1=1 and a.calfactor=b.calfactor {0} {1} order by a.factororder
			String tSql = "select b.factorcode, b.fieldcode, b.factorname, b.fieldtype, b.valuetype, '', b.valuelength, b.valuescope "
									+" from lmdutyfactorrela a, lmfactorconfig b "
									+" where 1=1 and a.factorcode=b.factorcode and a.riskcode='"+ "?tRiskCode?" +"' and a.dutycode='"+ "?tDutyCode?" +"' "
									+" order by a.factororder ";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("tRiskCode", tRiskCode);
			sqlbv.put("tDutyCode", tDutyCode);
			tLSQuotPlanDetailSubSchema.setQuotNo(tQuotNo);
			tLSQuotPlanDetailSubSchema.setQuotBatNo(tQuotBatNo);
			tLSQuotPlanDetailSubSchema.setSysPlanCode(tSysPlanCode);
			tLSQuotPlanDetailSubSchema.setPlanCode(tPlanCode);
			tLSQuotPlanDetailSubSchema.setRiskCode(tRiskCode);
			tLSQuotPlanDetailSubSchema.setDutyCode(tDutyCode);
			
			tLSQuotPlanDetailRelaSubSchema.setQuotNo(tQuotNo);
			tLSQuotPlanDetailRelaSubSchema.setQuotBatNo(tQuotBatNo);
			tLSQuotPlanDetailRelaSubSchema.setSysPlanCode(tSysPlanCode);
			tLSQuotPlanDetailRelaSubSchema.setPlanCode(tPlanCode);
			tLSQuotPlanDetailRelaSubSchema.setRiskCode(tRiskCode);
			tLSQuotPlanDetailRelaSubSchema.setDutyCode(tDutyCode);
			tLSQuotPlanDetailRelaSubSchema.setRelaToMain(tRelaToMain);
			tLSQuotPlanDetailRelaSubSchema.setMainAmntRate(tMainAmntRate);
			tLSQuotPlanDetailRelaSubSchema.setRelaAmntRate(tRelaAmntRate);
			System.out.println("222222");
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
			
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			
			tLWMissionSchema.setMissionID(tMissionID);
			tLWMissionSchema.setSubMissionID(tSubMissionID);
			tLWMissionSchema.setActivityID(tActivityID);
			tLWMissionSchema.setMissionProp1(tQuotNo);
			tLWMissionSchema.setMissionProp2(tQuotBatNo);
			tLWMissionSchema.setMissionProp3(tQuotType);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLWMissionSchema);
			tVData.add(tLSQuotPlanDetailSchema);
			tVData.add(tLSQuotPlanDetailSubSchema);
			tVData.add(tLSQuotPlanDetailRelaSubSchema);
			
			tTransferData.setNameAndValue("TranProdType", tTranProdType);
			tTransferData.setNameAndValue("PlanType", tPlanType);
			tTransferData.setNameAndValue("ChangePremType", "N");
			
			tVData.add(tTransferData);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotTryCalUI")) {
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
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>
