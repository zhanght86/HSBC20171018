<%
/***************************************************************
 * <p>ProName：LFGetPaySave.jsp</p>
 * <p>Title：生存领取</p>
 * <p>Description：生存领取</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-09-22
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LCInsuredDrawDetailSchema"%>
<%@page import="com.sinosoft.lis.schema.LJSGetDrawSchema"%>
<%@page import="com.sinosoft.lis.vschema.LCInsuredDrawDetailSet"%>
<%@page import="com.sinosoft.lis.vschema.LJSGetDrawSet"%>
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
			
			LCInsuredDrawDetailSet tLCInsuredDrawDetailSet = new LCInsuredDrawDetailSet();
			LJSGetDrawSet tLJSGetDrawSet = new LJSGetDrawSet();
			
			if ("SELGET".equals(tOperate)) {
				
				String tChk[]=request.getParameterValues("InpUnGetGridChk");
				String tContNo [] = request.getParameterValues("UnGetGrid3");
				String tInsuredNo [] = request.getParameterValues("UnGetGrid4");
				
				for (int index=0;index<tChk.length;index++) {
					
					if ("1".equals(tChk[index])) {
						
						LCInsuredDrawDetailSchema tLCInsuredDrawDetailSchema = new LCInsuredDrawDetailSchema();
						tLCInsuredDrawDetailSchema.setContNo(tContNo[index]);
						tLCInsuredDrawDetailSchema.setInsuredNo(tInsuredNo[index]);
						tLCInsuredDrawDetailSet.add(tLCInsuredDrawDetailSchema);
					}
				}
			} else if ("CANCEL".equals(tOperate) || "APPCONF".equals(tOperate)) {
				
				String tChk[]=request.getParameterValues("InpGetGridChk");
				String tGetNoticeNo [] = request.getParameterValues("GetGrid1");
				
				for (int index=0;index<tChk.length;index++) {
					
					if ("1".equals(tChk[index])) {
						
						LJSGetDrawSchema tLJSGetDrawSchema = new LJSGetDrawSchema();
						tLJSGetDrawSchema.setGetNoticeNo(tGetNoticeNo[index]);
						tLJSGetDrawSet.add(tLJSGetDrawSchema);
					}
				}
			}
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLCInsuredDrawDetailSet);
			tVData.add(tLJSGetDrawSet);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LFGetPayUI")) {
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
