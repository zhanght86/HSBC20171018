<%
/***************************************************************
 * <p>ProName��LLClaimNoAcceptSave.jsp</p>
 * <p>Title����������Ϣά��</p>
 * <p>Description����������Ϣά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LLClaimSpotCheckRuleSchema"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
			
			String tOperate = request.getParameter("Operate");
			
			LLClaimSpotCheckRuleSchema tLLClaimSpotCheckSchema = new LLClaimSpotCheckRuleSchema();			
			
			tLLClaimSpotCheckSchema.setRuleNo(request.getParameter("RuleNo"));
			tLLClaimSpotCheckSchema.setRuleType(request.getParameter("CheckType"));
			tLLClaimSpotCheckSchema.setRuleMngCom(request.getParameter("CheckManageCom"));
			tLLClaimSpotCheckSchema.setCheckUserCode(request.getParameter("CheckUserCode"));
			tLLClaimSpotCheckSchema.setGrpContNo(request.getParameter("CheckGrpContNo"));
			tLLClaimSpotCheckSchema.setAppntNo(request.getParameter("CheckAppntNo"));
			tLLClaimSpotCheckSchema.setAppntName(request.getParameter("CheckAppntName"));
			tLLClaimSpotCheckSchema.setRiskCode(request.getParameter("CheckRiskCode"));
			tLLClaimSpotCheckSchema.setRate(request.getParameter("CheckRate"));
			tLLClaimSpotCheckSchema.setRealMoney(request.getParameter("CheckMoney"));
			tLLClaimSpotCheckSchema.setGiveType(request.getParameter("CheckGiveType"));
			tLLClaimSpotCheckSchema.setIsBnf(request.getParameter("QueryBnfFlag"));
			tLLClaimSpotCheckSchema.setStartDate(request.getParameter("CheckStartDate"));
			tLLClaimSpotCheckSchema.setEndDate(request.getParameter("CheckEndDate"));
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLLClaimSpotCheckSchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLClaimSpotCheckUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "�����ɹ���";
				tFlagStr = "Succ";
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "�����쳣������ϵϵͳ��ά��Ա��";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>