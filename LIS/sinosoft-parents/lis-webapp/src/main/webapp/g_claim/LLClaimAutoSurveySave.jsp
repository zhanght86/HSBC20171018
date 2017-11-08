<%
/***************************************************************
 * <p>ProName��LLClaimNoAcceptSave.jsp</p>
 * <p>Title���Զ��������ά��</p>
 * <p>Description���Զ��������ά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ��Ф��
 * @version  : 8.0
 * @date     : 2014-01-01
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
			tLLClaimSpotCheckSchema.setRuleType("2");//�Զ��������
			tLLClaimSpotCheckSchema.setRuleMngCom(request.getParameter("CheckManageCom"));			

			tLLClaimSpotCheckSchema.setRiskCode(request.getParameter("CheckRiskCode"));
			tLLClaimSpotCheckSchema.setApplyMoney(request.getParameter("CheckAppPay"));
			tLLClaimSpotCheckSchema.setRealMoney(request.getParameter("CheckClmPay"));

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
				tContent = "����ɹ���";
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
