<%
/***************************************************************
 * <p>PreName：LLClaimSurveyAllotSave.jsp</p>
 * <p>Title：调查任务分配</p>
 * <p>Description：调查任务分配</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-02-21
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LLInqApplySchema"%>
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
			LLInqApplySchema tLLInqApplySchema=new LLInqApplySchema();
			String tOperate = request.getParameter("Operate");
			String tAccpetMngCom=request.getParameter("ManageCom");//受理机构

			tLLInqApplySchema.setClmNo(request.getParameter("RgtNo"));//赔案号
			tLLInqApplySchema.setInqNo(request.getParameter("InqNo"));//调查序号
			tLLInqApplySchema.setInqMngCom(request.getParameter("InqDept"));//调查分配机构 默认受理机构
			tLLInqApplySchema.setInqPer(request.getParameter("InqPer"));//分配调查人
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLLInqApplySchema);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLClaimSurveyAllotUI")) {
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
