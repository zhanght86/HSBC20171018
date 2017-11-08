<%
/***************************************************************
 * <p>ProName：LSQuotReceivPremSave.jsp</p>
 * <p>Title：项目询价建工险试算保费</p>
 * <p>Description：项目询价建工险试算保费</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2015-04-02
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
<%@page import="com.sinosoft.lis.quot.LSQuotPubFun"%>
<%@page import="com.sinosoft.utility.SQLwithBindVariables"%>
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
			
			String tOfferListNo = request.getParameter("OfferListNo");
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			String tSysPlanCode = request.getParameter("SysPlanCode");
			String tPlanCode = request.getParameter("PlanCode");
			String tRiskCode = request.getParameter("RiskCode");
			String tDutyCode = request.getParameter("DutyCode");
			String tChangeType = request.getParameter("ChangeType");//变更类型
			String tReceivPrem = request.getParameter("ReceivPrem");//应收保费
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("OfferListNo", tOfferListNo);
			tTransferData.setNameAndValue("QuotNo", tQuotNo);
			tTransferData.setNameAndValue("QuotBatNo", tQuotBatNo);
			tTransferData.setNameAndValue("SysPlanCode", tSysPlanCode);
			tTransferData.setNameAndValue("PlanCode", tPlanCode);
			tTransferData.setNameAndValue("RiskCode", tRiskCode);
			tTransferData.setNameAndValue("DutyCode", tDutyCode);
			tTransferData.setNameAndValue("ChangeType", tChangeType);
			tTransferData.setNameAndValue("ReceivPrem", tReceivPrem);
			
			//查询出动态域字段,获取动态域值
			String tSql = "select b.factorcode, b.fieldcode, b.factorname, b.fieldtype, b.valuetype, '', b.valuelength, b.valuescope "
									+" from lmdutyfactorrela a, lmfactorconfig b "
									+" where 1=1 and a.factorcode=b.factorcode and a.riskcode='?tRiskCode?' and a.dutycode='?tDutyCode?' "
									+" order by a.factororder ";
			
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("tRiskCode", tRiskCode);
			sqlbv.put("tDutyCode", tDutyCode);
			
			
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
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
			tVData.add(tTransferData);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotReceivPremUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tFlagStr = "Succ";
				
				
				if ("TRYCALPREM".equals(tOperate)) {
					tContent = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 1);
				} else if ("SAVEPREM".equals(tOperate)){
					tContent = "保存成功！";
				}
				
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
