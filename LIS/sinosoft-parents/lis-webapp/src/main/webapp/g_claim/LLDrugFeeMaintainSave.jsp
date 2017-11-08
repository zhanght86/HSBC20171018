<%
/***************************************************************
 * <p>ProName：LLDrugFeeMaintainSave.jsp</p>
 * <p>Title：药品信息维护</p>
 * <p>Description：药品信息维护</p>
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
<%@page import="com.sinosoft.lis.schema.LLDrugsFeeSchema"%>
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
	String tDrugsSerialNo=request.getParameter("tDrugsSerialNo");
	LLDrugsFeeSchema tLLDrugsFeeSchema=new LLDrugsFeeSchema();
	tLLDrugsFeeSchema.setAreaCode(request.getParameter("areaCode"));
	tLLDrugsFeeSchema.setDrugsName(request.getParameter("drugFeeMaintainName"));
	tLLDrugsFeeSchema.setCommodityName(request.getParameter("bussinessName"));
	tLLDrugsFeeSchema.setHealthCareType(request.getParameter("medicalInsuranceCode"));	
	tLLDrugsFeeSchema.setDosageForms(request.getParameter("drugForm"));
	tLLDrugsFeeSchema.setSpecifications(request.getParameter("format"));
	tLLDrugsFeeSchema.setSelfRate(request.getParameter("selfRate"));
	tLLDrugsFeeSchema.setPrice(request.getParameter("price"));
	tLLDrugsFeeSchema.setLimitInfo(request.getParameter("restrictions"));
	tLLDrugsFeeSchema.setUpDataDate(request.getParameter("UpdateDate"));
	TransferData mTransferData = new TransferData();
 	mTransferData.setNameAndValue("DrugsSerialNo", tDrugsSerialNo);
	
	VData tVData = new VData();
	tVData.add(tGI);
	tVData.add(tLLDrugsFeeSchema);
	tVData.add(mTransferData);
	
	BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
	if (!tBusinessDelegate.submitData(tVData, tOperate, "LLDrugsFeeUI")) {
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
