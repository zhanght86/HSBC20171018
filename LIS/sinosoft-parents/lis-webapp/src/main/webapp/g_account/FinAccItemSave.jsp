<%
/***************************************************************
 * <p>ProName：FinAccItemSave.jsp</p>
 * <p>Title：分支科目定义界面</p>
 * <p>Description：定义会计科目下的分支科目</p>
 * <p>Copyright：Copyright (c) 2013</p>
 * <p>Company：Sinosoft</p>
 * @author   : 石全彬
 * @version  : 8.0
 * @date     : 2013-01-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.FinAccItemSchema"%>
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
			
			String tAccItemCode = request.getParameter("AccItemCode");
			String tAccItemName = request.getParameter("HiddenAccItemName");
			System.out.println("**************************"+tAccItemName);
			String tAccItemCode1 = request.getParameter("AccItemCode1");
			String tAccItemCode2 = request.getParameter("AccItemCode2");
			String tAccItemCode3 = request.getParameter("AccItemCode3");
			String tFinCode = request.getParameter("FinCode");
			String tRemark = request.getParameter("Remark");
			
			FinAccItemSchema tFinAccItemSchema = new FinAccItemSchema();
			tFinAccItemSchema.setAccItemCode(tAccItemCode);
			tFinAccItemSchema.setAccItemCode1(tAccItemCode1);
			tFinAccItemSchema.setAccItemCode2(tAccItemCode2);
			tFinAccItemSchema.setAccItemCode3(tAccItemCode3);
			tFinAccItemSchema.setAccItemCode4("0000"); //备用
			tFinAccItemSchema.setAccItemCode5("0000"); //备用
			tFinAccItemSchema.setAccItemCode6("0000"); //备用
			tFinAccItemSchema.setAccItemName(tAccItemName);
			tFinAccItemSchema.setFinCode(tFinCode);
			tFinAccItemSchema.setRemark(tRemark);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tFinAccItemSchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "FinAccItemUI")) {
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
