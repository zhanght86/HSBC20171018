<%
/***************************************************************
 * <p>ProName：LLClaimPreinvestSave.jsp</p>
 * <p>Title：发起调查</p>
 * <p>Description：发起调查</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2012-01-01
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

			tLLInqApplySchema.setGrpRgtNo(request.getParameter("GrpRgtNo"));
			tLLInqApplySchema.setClmNo(request.getParameter("RgtNo"));//赔案号
			tLLInqApplySchema.setInqNo(request.getParameter("InqNo"));//调查序号
			tLLInqApplySchema.setApplyDate(request.getParameter("ApplyDate"));//申请日期
			tLLInqApplySchema.setCustomerNo(request.getParameter("CustomerNo"));//出险人客户号
			tLLInqApplySchema.setCustomerName(request.getParameter("CustomerName"));//出险人名称
			tLLInqApplySchema.setAppntNo(request.getParameter("AppntNo"));//投保人编码
			tLLInqApplySchema.setInqType(request.getParameter("SurveyType"));//调查类型
			tLLInqApplySchema.setInitPhase(request.getParameter("InitPhase"));//提起阶段
			tLLInqApplySchema.setInqRCode(request.getParameter("InqReason"));//调查原因
			tLLInqApplySchema.setInqItem(request.getParameter("InqPurpose"));//调查目的
			tLLInqApplySchema.setInqDesc(request.getParameter("InqPlan"));//调查计划
			tLLInqApplySchema.setInqDept(request.getParameter("ManageCom"));//调查机构
			tLLInqApplySchema.setInqMngCom(request.getParameter("ManageCom"));//调查分配机构 默认受理机构
			tLLInqApplySchema.setInitDept(request.getParameter("ManageCom"));//调查发起机构
			tLLInqApplySchema.setLocFlag(request.getParameter("LocFlag"));//本地标志
			tLLInqApplySchema.setBatNo(request.getParameter("BatNO"));//批次号
			tLLInqApplySchema.setRemark(request.getParameter("Remark"));//调查备注
			tLLInqApplySchema.setInqFlowState(request.getParameter("InqFlowState"));//流程状态
			tLLInqApplySchema.setInitiationType(request.getParameter("InitiationType"));//发起方式
			tLLInqApplySchema.setInqCloseReason(request.getParameter("InqCloseReason"));//关闭原因
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLLInqApplySchema);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLClaimPreinvestUI")) {
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
