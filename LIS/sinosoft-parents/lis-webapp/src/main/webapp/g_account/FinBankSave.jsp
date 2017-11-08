<%
/***************************************************************
 * <p>ProName：FinBankSave.jsp</p>
 * <p>Title：财务银行维护</p>
 * <p>Description：财务银行维护</p>
 * <p>Copyright：Copyright (c) 2013</p>
 * <p>Company：Sinosoft</p>
 * @author   : 杨治纲
 * @version  : 8.0
 * @date     : 2013-01-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.FinBankSchema"%>
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
			tTransferData.setNameAndValue("FinBankCode", request.getParameter("HiddenFinBankCode"));
			tTransferData.setNameAndValue("FinBankName", request.getParameter("FinBankName"));
			tTransferData.setNameAndValue("AccNo", request.getParameter("AccNo"));
			tTransferData.setNameAndValue("State", request.getParameter("State"));
			
			FinBankSchema tFinBankSchema = new FinBankSchema();
			String tFinBankCode = request.getParameter("HiddenFinBankCode");
			String tFinBankName = request.getParameter("FinBankName");
			String tFinBankClass = request.getParameter("FinBankClass");
			String tFinBankNature = request.getParameter("FinBankNature");
			String tFinComCode = request.getParameter("FinComCode");
			String tAccNo = request.getParameter("AccNo");
			String tState = request.getParameter("State");
			
			tFinBankSchema.setFinBankCode(tFinBankCode);
			tFinBankSchema.setFinBankName(tFinBankCode);
			tFinBankSchema.setFinBankName(tFinBankName);
			tFinBankSchema.setFinBankClass(tFinBankClass);
			tFinBankSchema.setFinBankNature(tFinBankNature);
			tFinBankSchema.setFinComCode(tFinComCode);
			tFinBankSchema.setAccNo(tAccNo);
			tFinBankSchema.setState(tState);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tFinBankSchema);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "FinBankUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tFlagStr = "Succ";
				tContent = "提交数据成功！";
			
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
