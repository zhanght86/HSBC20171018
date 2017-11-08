<%
/***************************************************************
 * <p>ProName：LLClaimSurveyInput.jsp</p>
 * <p>Title：伤残录入</p>
 * <p>Description：伤残录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LLCaseInfoSchema"%>
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
			String tOperate = request.getParameter("Operate");//获得操作状态
				//调查过程表 
			LLCaseInfoSchema tLLCaseInfoSchema = new LLCaseInfoSchema();
			tLLCaseInfoSchema.setClmNo(request.getParameter("tRgtNo"));
			tLLCaseInfoSchema.setCaseNo(request.getParameter("tCaseNo"));
			tLLCaseInfoSchema.setSerialNo(request.getParameter("tDefoSerialNo"));
			tLLCaseInfoSchema.setCustomerNo(request.getParameter("tCustomerNo"));    
			tLLCaseInfoSchema.setDefoType(request.getParameter("DefoTypeCode"));//残疾类型
			tLLCaseInfoSchema.setDefoClass(request.getParameter("DefoClassCode"));//借用字段 存储伤残大类
			tLLCaseInfoSchema.setDefoGrade(request.getParameter("DefoGradeCode"));//伤残细类
			tLLCaseInfoSchema.setDefoCode(request.getParameter("DefoCode"));//伤残代码
			tLLCaseInfoSchema.setDefoName(request.getParameter("DefoName"));//伤残代码名称
			tLLCaseInfoSchema.setDeformityRate(request.getParameter("defoRate"));//残疾给付比例
			tLLCaseInfoSchema.setRealRate(request.getParameter("defoRate"));
			tLLCaseInfoSchema.setJudgeOrganName(request.getParameter("JudgeOrganName"));//残疾鉴定机构
			tLLCaseInfoSchema.setJudgeDate(request.getParameter("JudgeDate"));//残疾鉴定时间
			tLLCaseInfoSchema.setMngCom(request.getParameter("ManageCom"));
			// 准备传输数据 VData
			VData tVData = new VData();	
			tVData.add(tGI);
			tVData.add(tLLCaseInfoSchema);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLCaseInfoUI")) {
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
