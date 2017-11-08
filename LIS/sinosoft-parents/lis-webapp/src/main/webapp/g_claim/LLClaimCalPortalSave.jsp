<%
/***************************************************************
 * <p>ProName：LLClaimCalPortalSave.jsp</p>
 * <p>Title：匹配理算</p>
 * <p>Description：匹配理算</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : 杨治纲
 * @version  : 8.0
 * @date     : 2014-05-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
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
			
			String tOperate = request.getParameter("Operate");
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("GrpRgtNo", request.getParameter("GrpRgtNo"));
			tTransferData.setNameAndValue("RgtNo", request.getParameter("RegisterNo"));
			tTransferData.setNameAndValue("PayType", request.getParameter("PayType"));
			tTransferData.setNameAndValue("PublicFlag", request.getParameter("PublicFlag"));
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLClaimCalPortalUI")) {
				tContent = tBusinessDelegate.getCErrors().getLastError();
				tFlagStr = "Fail";						
			} else {
				tContent = "处理成功！";
				tFlagStr = "Succ";
				if ("G".equals(tOperate)) {
					
					System.out.println(tBusinessDelegate.getCErrors().getErrorCount());
					TransferData sTransferData = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData",0);
					if (sTransferData!=null) {
						
						CErrors mErrors = (CErrors)sTransferData.getValueByName("Error");
						if (mErrors!=null && mErrors.getErrorCount()>=1) {
							
							tContent = "处理成功，"+"但存在错误信息："+"<br>";
							for (int i=1;i<=mErrors.getErrorCount();i++) {
								
								String tErrorMessage = mErrors.getError(i-1).errorMessage;
								tContent=tContent+tErrorMessage+"<br>";
							}
						}
					}
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
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>
