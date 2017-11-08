<%
/***************************************************************
 * <p>ProName��LCAliveReplySave.jsp</p>
 * <p>Title������¼��</p>
 * <p>Description������¼��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-09-23
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LCInvestReplaySchema"%>
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
			
			String tContNo = request.getParameter("ContNo");
			String tInsuredNo = request.getParameter("InsuredNo");
			String tInvestDesc = request.getParameter("InvestDesc");
			String tDeathFlag = request.getParameter("DeathFlag");
			String tDeathDate = request.getParameter("DeathDate");
			
			LCInvestReplaySchema tLCInvestReplaySchema = new LCInvestReplaySchema();
			
			if ("INSERT".equals(tOperate)) {
				
				tLCInvestReplaySchema.setContNo(tContNo);
				tLCInvestReplaySchema.setInsuredNo(tInsuredNo);
				tLCInvestReplaySchema.setInvestDesc(tInvestDesc);
				tLCInvestReplaySchema.setDeathFlag(tDeathFlag);
				tLCInvestReplaySchema.setDeathDate(tDeathDate);
			}
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLCInvestReplaySchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LCAliveReplyUI")) {
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
