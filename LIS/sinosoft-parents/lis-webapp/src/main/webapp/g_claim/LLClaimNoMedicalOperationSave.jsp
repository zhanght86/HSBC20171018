<%
/***************************************************************
 * <p>ProName：LLClaimSurveyInput.jsp</p>
 * <p>Title：调查录入</p>
 * <p>Description：调查过程录入</p>
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
<%@page import="com.sinosoft.lis.schema.LLOperationSchema"%>
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
			LLOperationSchema tLLOperationSchema = new LLOperationSchema();
			tLLOperationSchema.setClmNo(request.getParameter("tRgtNo"));
	    tLLOperationSchema.setCaseNo(request.getParameter("tCaseNo"));
	    tLLOperationSchema.setSerialNo(request.getParameter("tMajorSerialNo"));
	    tLLOperationSchema.setCustomerNo(request.getParameter("tCustomerNo"));    
	    tLLOperationSchema.setFeeType("G");
			tLLOperationSchema.setOperationCode(request.getParameter("OperationCode"));
	  	tLLOperationSchema.setOperationName(request.getParameter("OperationName"));
		tLLOperationSchema.setOpFee(request.getParameter("OpFee"));
		tLLOperationSchema.setUnitNo("0");
	    tLLOperationSchema.setUnitName(request.getParameter("UnitName"));
	    tLLOperationSchema.setDiagnoseDate(request.getParameter("DiagnoseDate"));
	    tLLOperationSchema.setMngCom(request.getParameter("ManageCom"));
	    
	// 准备传输数据 VData
			VData tVData = new VData();	
			tVData.add(tGI);
			tVData.add(tLLOperationSchema);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLOperationUI")) {
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
