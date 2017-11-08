<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%
/***************************************************************
 * <p>ProName：LLClaimMaimRateSave.jsp</p>
 * <p>Title：伤残比例维护</p>
 * <p>Description：伤残比例维护</p>
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
<%@page import="com.sinosoft.lis.schema.LLParaDeformitySchema"%>
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
	String tDefoType= request.getParameter("tDefoType");
	String tDefoCode= request.getParameter("tDefoCode");
	String tDefoTypeCode= request.getParameter("DefoTypeCode");
	LLParaDeformitySchema tLLParaDeformitySchema=new LLParaDeformitySchema();
	tLLParaDeformitySchema.setDefoType(request.getParameter("DefoTypeCode"));
	
	if(tDefoTypeCode=="3"||"3".equals(tDefoTypeCode)){
	
		tLLParaDeformitySchema.setDefoClass(request.getParameter("DefoClass"));
		tLLParaDeformitySchema.setDefoClassName(request.getParameter("DefoClassName"));
	}
	tLLParaDeformitySchema.setDefoGrade(request.getParameter("DefoGrade"));
	tLLParaDeformitySchema.setDefoGradeName(request.getParameter("DefoGradeName"));
	tLLParaDeformitySchema.setDefoCode(request.getParameter("DefoCode"));
	tLLParaDeformitySchema.setDefoName(request.getParameter("DefoName"));
	tLLParaDeformitySchema.setDefoRate(request.getParameter("DefoRate"));
	TransferData mTransferData = new TransferData();
  
  mTransferData.setNameAndValue("DefoType", tDefoType);
  mTransferData.setNameAndValue("DefoCode", tDefoCode);
	
	VData tVData = new VData();
	tVData.add(tGI);
	tVData.add(tLLParaDeformitySchema);
	tVData.add(mTransferData);
	
	BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
	if (!tBusinessDelegate.submitData(tVData, tOperate, "LLParaDeformityUI")) {
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
