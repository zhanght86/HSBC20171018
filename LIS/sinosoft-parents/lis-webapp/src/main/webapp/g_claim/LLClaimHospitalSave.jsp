<%
/***************************************************************
 * <p>ProName：LLClaimHospitalSave.jsp</p>
 * <p>Title：医院信息维护</p>
 * <p>Description：医院信息维护</p>
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
<%@page import="com.sinosoft.lis.schema.LDHospitalSchema"%>
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
	
	try{
	
	String tOperate = request.getParameter("Operate");
	LDHospitalSchema tLDHospitalSchema=new LDHospitalSchema();
	tLDHospitalSchema.setHospitCode(request.getParameter("HospitalCode"));
	tLDHospitalSchema.setHospitalName(request.getParameter("HospitalName"));
	tLDHospitalSchema.setHosState(request.getParameter("HosState"));
	tLDHospitalSchema.setHospitalGrade(request.getParameter("HosGrade"));
	
	tLDHospitalSchema.setAddress(request.getParameter("HosAddress"));
	tLDHospitalSchema.setPhone(request.getParameter("HosPhone"));
	
	VData tVData = new VData();
	tVData.add(tGI);
	tVData.add(tLDHospitalSchema);
	BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
	if (!tBusinessDelegate.submitData(tVData, tOperate, "LLHospitalUI")) {
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
