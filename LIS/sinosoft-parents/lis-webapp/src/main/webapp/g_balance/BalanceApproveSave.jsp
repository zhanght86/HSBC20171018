<%
/***************************************************************
 * <p>ProName：BalanceApproveSave.jsp</p>
 * <p>Title：结算审批</p>
 * <p>Description：结算审批</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-06-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.lis.schema.LPBalanceAppSchema"%>
<%@page import="com.sinosoft.lis.vschema.LPBalanceAppSet"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.TransferData"%>
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
			//获取操作类型的前台参数
			tOperate = request.getParameter("Operate");
			VData tVData = new VData();
			tVData.add(tGI);
			TransferData tTransferData = new TransferData();
			LPBalanceAppSet mLPBalanceAppSet = new LPBalanceAppSet();
			if(tOperate.equals("APPROVE")){
			
				String tApproveFlag = request.getParameter("ApproveFlag");
				String tApproveDesc = request.getParameter("ApproveDesc");
				String tGrid2 [] = request.getParameterValues("ContInfoGrid2");
				String tGrid3 [] = request.getParameterValues("ContInfoGrid3");
				LPBalanceAppSchema mLPBalanceAppSchema = new LPBalanceAppSchema();
				mLPBalanceAppSchema.setGrpContNo(tGrid3[0]);
				mLPBalanceAppSchema.setState("1");
				mLPBalanceAppSchema.setBalanceAppNo(tGrid2[0]);
				mLPBalanceAppSchema.setApproveFlag(tApproveFlag);
				mLPBalanceAppSchema.setApproveDesc(tApproveDesc);
					
				mLPBalanceAppSet.add(mLPBalanceAppSchema);
				
			}
			
			tTransferData.setNameAndValue("BalanceType", "2");
			tTransferData.setNameAndValue("LPBalanceAppSet", mLPBalanceAppSet);
			
			tVData.add(mLPBalanceAppSet);
			tVData.add(tTransferData);	
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "BalanceManualUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tFlagStr = "Succ";
				tContent = "处理成功！";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
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
