<%
/***************************************************************
 * <p>ProName：LLClimSpecialCaseSave.jsp</p>
 * <p>Title：理赔特殊立案界面</p>
 * <p>Description：理赔特殊立案界面</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : 李肖峰
 * @version  : 8.0
 * @date     : 2014-05-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LLGrpRegisterSchema"%>
<%@page import="com.sinosoft.lis.schema.LLRegisterSchema"%>
<%@page import="com.sinosoft.lis.schema.LLCaseSchema"%>
<%@page import="com.sinosoft.lis.schema.LLAppClaimReasonSchema"%>
<%@page import="com.sinosoft.lis.vschema.LLAppClaimReasonSet"%>
<%@page import="com.sinosoft.lis.schema.LDBankSchema"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tGrpRegisterNo = "";
	String tRegisterNo = "";
	String tCaseNo = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			
			String tOperate = request.getParameter("Operate");
			String tRgtClass = request.getParameter("RgtClass");
						
			LLGrpRegisterSchema tLLGrpRegisterSchema = new LLGrpRegisterSchema();
			tLLGrpRegisterSchema.setRgtNo(request.getParameter("GrpRgtNo"));
			tLLGrpRegisterSchema.setRgtState(request.getParameter("CaseType"));
			tLLGrpRegisterSchema.setApplyerType("2");
			tLLGrpRegisterSchema.setRgtClass(request.getParameter("RgtClass"));
			tLLGrpRegisterSchema.setRgtObj(request.getParameter("RgtClass"));
			tLLGrpRegisterSchema.setCustomerNo(request.getParameter("AppntNo"));
			tLLGrpRegisterSchema.setGrpName(request.getParameter("AppntName"));
			tLLGrpRegisterSchema.setAppDate(request.getParameter("AppDate"));
			tLLGrpRegisterSchema.setRgtDate(request.getParameter("AcceptDate"));
			tLLGrpRegisterSchema.setAcceptOperator(request.getParameter("AcceptOperator"));
			tLLGrpRegisterSchema.setMngCom(request.getParameter("AcceptCom"));
			tLLGrpRegisterSchema.setRgtObjNo(request.getParameter("PageNo"));
							
			LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
			tLLRegisterSchema.setGrpRgtNo(request.getParameter("GrpRgtNo"));
			tLLRegisterSchema.setRgtNo(request.getParameter("RgtNo"));			
			tLLRegisterSchema.setRgtState(request.getParameter("CaseType"));
			tLLRegisterSchema.setAppntNo(request.getParameter("SelfAppntNo"));		
			tLLRegisterSchema.setGrpName(request.getParameter("SelfAppntName"));		
			tLLRegisterSchema.setRgtClass(request.getParameter("RgtClass"));
			tLLRegisterSchema.setRgtObj(request.getParameter("RgtClass"));
			tLLRegisterSchema.setRgtObjNo(request.getParameter("PageNo"));
			tLLRegisterSchema.setCustomerNo(request.getParameter("CustomerNo"));
			tLLRegisterSchema.setRgtantName(request.getParameter("CustomerName"));
			tLLRegisterSchema.setRgtantSex(request.getParameter("Gender"));
			tLLRegisterSchema.setBirthday(request.getParameter("Birthday"));
			tLLRegisterSchema.setIDType(request.getParameter("IDType"));
			tLLRegisterSchema.setIDNo(request.getParameter("IDNo"));
			tLLRegisterSchema.setEmployeNo(request.getParameter("EmployeNo"));
			tLLRegisterSchema.setRemark(request.getParameter("ApplyRemarks"));
			tLLRegisterSchema.setRgtantPhone(request.getParameter("TelPhone"));
			if ("1".equals(request.getParameter("ClaimFlag"))) {
				tLLRegisterSchema.setOldClmNo(request.getParameter("HistoryRgtNo"));//历史赔案号
			}else {
				tLLRegisterSchema.setOldClmNo("");//历史赔案号
			}
			if("12".equals(request.getParameter("CaseType"))){
				tLLRegisterSchema.setReturnMode(request.getParameter("ErrorStation"));//差错责任岗				    					
			}else {
				tLLRegisterSchema.setReturnMode("");
			}
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLLGrpRegisterSchema);
			tVData.add(tLLRegisterSchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLClaimSpecialCaseUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				
				TransferData tTransferData = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData",0);
				if (tOperate.contains("CASE")) {
					tGrpRegisterNo = (String)tTransferData.getValueByName("GrpRegisterNo");
					tRegisterNo = (String)tTransferData.getValueByName("RegisterNo");
				}
				tContent = "处理成功！";
				tFlagStr = "Succ";
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
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>","<%=tGrpRegisterNo%>","<%=tRegisterNo%>");
</script>
</html>
