
<%@page import="com.itextpdf.text.log.SysoLogger"%><%
/***************************************************************
 * <p>ProName：LLCLaimControlSave.jsp</p>
 * <p>Title：理赔责任控制</p>
 * <p>Description：理赔责任控制</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 杨治纲
 * @version  : 8.0
 * @date     : 2014-05-05
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LCClaimFactorControlSchema"%>
<%@page import="com.sinosoft.lis.vschema.LCClaimFactorControlSet"%>
<%@page import="com.sinosoft.lis.schema.LCClaimGetDutyControlSchema"%>
<%@page import="com.sinosoft.lis.vschema.LCClaimGetDutyControlSet"%>
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
			
			VData tVData = new VData();
			TransferData tTransferData = new TransferData();
			
			String tAction = request.getParameter("Action");
			String tOperate = request.getParameter("Operate");
			String tBussType = request.getParameter("BussType");
			String tBussNo = request.getParameter("BussNo");
			String tSubBussNo = request.getParameter("SubBussNo");
			
			tTransferData.setNameAndValue("Action", tAction);
			tTransferData.setNameAndValue("BussType", tBussType);
			tTransferData.setNameAndValue("BussNo", tBussNo);
			tTransferData.setNameAndValue("SubBussNo", tSubBussNo);
			
			if ("DUTY".equals(tAction)) {
				
				tTransferData.setNameAndValue("SysPlanCode", request.getParameter("DutySysPlanCode"));
				tTransferData.setNameAndValue("PlanCode", request.getParameter("DutyPlanCode"));
				
				if ("INSERT".equals(tOperate)) {
					
					tTransferData.setNameAndValue("FactorType", request.getParameter("DutyFactorType"));
					tTransferData.setNameAndValue("RiskCode", request.getParameter("RiskCode"));
					tTransferData.setNameAndValue("DutyCode", request.getParameter("DutyCode"));
					tTransferData.setNameAndValue("FactorCode", request.getParameter("FactorCode"));
					tTransferData.setNameAndValue("Params", request.getParameter("Params"));
				} else {
					
					String tRadio[] = request.getParameterValues("InpConfDutyControlGridSel");
					String tRiskCode[] = request.getParameterValues("ConfDutyControlGrid1");
					String tDutyCode[] = request.getParameterValues("ConfDutyControlGrid3");
					String tFactorType[] = request.getParameterValues("ConfDutyControlGrid5");
					String tFactorCode[] = request.getParameterValues("ConfDutyControlGrid7");
					String tInerSerialNo[] = request.getParameterValues("ConfDutyControlGrid9");
					String tParams[] = request.getParameterValues("ConfDutyControlGrid11");
					
					for (int i=0;i<tRadio.length;i++) {
						
						if (tRadio[i].equals("1")) {
							
							tTransferData.setNameAndValue("FactorType", tFactorType[i]);
							tTransferData.setNameAndValue("RiskCode", tRiskCode[i]);
							tTransferData.setNameAndValue("DutyCode", tDutyCode[i]);
							tTransferData.setNameAndValue("FactorCode", tFactorCode[i]);
							tTransferData.setNameAndValue("Params", tParams[i]);
							tTransferData.setNameAndValue("InerSerialNo", tInerSerialNo[i]);
							break;
						}
					}
				}
			}
			
			if ("SHARE".equals(tAction)) {
				
				tTransferData.setNameAndValue("SysPlanCode", request.getParameter("ShareSysPlanCode"));
				tTransferData.setNameAndValue("PlanCode", request.getParameter("SharePlanCode"));
				
				if ("INSERT".equals(tOperate)) {
					
					tTransferData.setNameAndValue("FactorType", request.getParameter("ShareFactorType"));
					
					String tChk[] = request.getParameterValues("InpShareControlGridChk");
					String tRiskCode[] = request.getParameterValues("ShareControlGrid1");
					String tDutyCode[] = request.getParameterValues("ShareControlGrid3");
					String tFactorType[] = request.getParameterValues("ShareControlGrid5");
					String tFactorCode[] = request.getParameterValues("ShareControlGrid7");
					
					LCClaimFactorControlSet tLCClaimFactorControlSet = new LCClaimFactorControlSet();
					
					for (int i=0;i<tChk.length;i++) {
						
						if (tChk[i].equals("1")) {
							
							LCClaimFactorControlSchema tLCClaimFactorControlSchema = new LCClaimFactorControlSchema();
							tLCClaimFactorControlSchema.setRiskCode(tRiskCode[i]);
							tLCClaimFactorControlSchema.setDutyCode(tDutyCode[i]);
							tLCClaimFactorControlSchema.setFactorType(tFactorType[i]);
							tLCClaimFactorControlSchema.setFactorCode(tFactorCode[i]);
							tLCClaimFactorControlSet.add(tLCClaimFactorControlSchema);
						}
					}
					
					tVData.add(tLCClaimFactorControlSet);
				} else {
					
					String tRadio[] = request.getParameterValues("InpConfShareControlGridSel");
					String tFactorType[] = request.getParameterValues("ConfShareControlGrid1");
					String tFactorCode[] = request.getParameterValues("ConfShareControlGrid3");
					String tInerSerialNo[] = request.getParameterValues("ConfShareControlGrid5");
					
					for (int i=0;i<tRadio.length;i++) {
						
						if (tRadio[i].equals("1")) {
							
							tTransferData.setNameAndValue("FactorType", tFactorType[i]);
							tTransferData.setNameAndValue("FactorCode", tFactorCode[i]);
							tTransferData.setNameAndValue("InerSerialNo", tInerSerialNo[i]);
							break;
						}
					}
				}
			}
			
			if ("GET".equals(tAction)) {
				
				tTransferData.setNameAndValue("SysPlanCode", request.getParameter("GetSysPlanCode"));
				tTransferData.setNameAndValue("PlanCode", request.getParameter("GetPlanCode"));
				tTransferData.setNameAndValue("RiskCode", request.getParameter("GetRiskCode"));
				tTransferData.setNameAndValue("DutyCode", request.getParameter("GetDutyCode"));
				
				String tGridNo[] = request.getParameterValues("GetControlGridNo");
				String tGetDutyCode[] = request.getParameterValues("GetControlGrid1");
				String tGetDutyKind[] = request.getParameterValues("GetControlGrid3");
				String tWaitPeriod[] = request.getParameterValues("GetControlGrid5");
				String tDeductibleType[] = request.getParameterValues("GetControlGrid6");
				String tDeductibles[] = request.getParameterValues("GetControlGrid7");
				String tDeductibleDays[] = request.getParameterValues("GetControlGrid8");
				String tGetRate[] = request.getParameterValues("GetControlGrid9");
				String tYearLimit[] = request.getParameterValues("GetControlGrid10");
				String tSubLimit[] = request.getParameterValues("GetControlGrid11");
				String tUnitAmnt[] = request.getParameterValues("GetControlGrid12");
				String tAmntUnit[] = request.getParameterValues("GetControlGrid13");
				String tCountLimit[] = request.getParameterValues("GetControlGrid14");
				
				LCClaimGetDutyControlSet tLCClaimGetDutyControlSet = new LCClaimGetDutyControlSet();
				
				if (tGridNo!=null && tGridNo.length>0) {
					
					for (int i=0;i<tGridNo.length;i++) {
						
						String WaitPeriod = tWaitPeriod[i];
						if (WaitPeriod==null || "".equals(WaitPeriod)) {
							WaitPeriod = "-1";
						}
						
						String Deductibles = tDeductibles[i];
						if (Deductibles==null || "".equals(Deductibles)) {
							Deductibles = "-1";
						}
						
						String DeductibleDays = tDeductibleDays[i];
						if (DeductibleDays==null || "".equals(DeductibleDays)) {
							DeductibleDays = "-1";
						}
						
						String GetRate = tGetRate[i];
						if (GetRate==null || "".equals(GetRate)) {
							GetRate = "-1";
						}
						
						String YearLimit = tYearLimit[i];
						if (YearLimit==null || "".equals(YearLimit)) {
							YearLimit = "-1";
						}
						
						String SubLimit = tSubLimit[i];
						if (SubLimit==null || "".equals(SubLimit)) {
							SubLimit = "-1";
						}
						
						String UnitAmnt = tUnitAmnt[i];
						if (UnitAmnt==null || "".equals(UnitAmnt)) {
							UnitAmnt = "-1";
						}
						
						String CountLimit = tCountLimit[i];
						if (CountLimit==null || "".equals(CountLimit)) {
							CountLimit = "-1";
						}
						
						LCClaimGetDutyControlSchema tLCClaimGetDutyControlSchema = new LCClaimGetDutyControlSchema();
						tLCClaimGetDutyControlSchema.setSysPlanCode(request.getParameter("GetSysPlanCode"));
						tLCClaimGetDutyControlSchema.setPlanCode(request.getParameter("GetPlanCode"));
						tLCClaimGetDutyControlSchema.setRiskCode(request.getParameter("GetRiskCode"));
						tLCClaimGetDutyControlSchema.setDutyCode(request.getParameter("GetDutyCode"));
						tLCClaimGetDutyControlSchema.setGetDutyCode(tGetDutyCode[i]);
						tLCClaimGetDutyControlSchema.setGetDutyKind(tGetDutyKind[i]);
						tLCClaimGetDutyControlSchema.setWaitPeriod(WaitPeriod);
						tLCClaimGetDutyControlSchema.setDeductibleType(tDeductibleType[i]);
						tLCClaimGetDutyControlSchema.setDeductibles(Deductibles);
						tLCClaimGetDutyControlSchema.setDeductibleDays(DeductibleDays);
						tLCClaimGetDutyControlSchema.setGetRate(GetRate);
						tLCClaimGetDutyControlSchema.setYearLimit(YearLimit);
						tLCClaimGetDutyControlSchema.setSubLimit(SubLimit);
						tLCClaimGetDutyControlSchema.setUnitAmnt(UnitAmnt);
						tLCClaimGetDutyControlSchema.setAmntUnit(tAmntUnit[i]);
						tLCClaimGetDutyControlSchema.setCountLimit(CountLimit);
						tLCClaimGetDutyControlSet.add(tLCClaimGetDutyControlSchema);
					}
				}
				
				tVData.add(tLCClaimGetDutyControlSet);
			}
			
			tVData.add(tGI);
			tVData.add(tTransferData);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLCLaimControlUI")) {
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
