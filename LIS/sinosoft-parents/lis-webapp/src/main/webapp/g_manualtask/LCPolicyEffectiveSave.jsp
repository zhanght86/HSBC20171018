<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%
/***************************************************************
 * <p>ProName：LCPolicyEffectiveSave.jsp</p>
 * <p>Title：保单生效</p>
 * <p>Description：保单生效/p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-07-06
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.*"%>
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
			String tCurrentDate = PubFun.getCurrentDate();
			String tOperate = request.getParameter("Operate");
			
			String tGrpPropNo = request.getParameter("GrpPropNo");
			String tGrpContNo = request.getParameter("TGrpContNo");
			String tManageCom = request.getParameter("ManageCom");
			
			TransferData mTransferData = new TransferData();
			
			mTransferData.setNameAndValue("GrpContNo", tGrpContNo);
			mTransferData.setNameAndValue("DealDate", tCurrentDate);
			
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(mTransferData);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LCPolicyEffectiveUI")) {
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
