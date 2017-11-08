
<%
	//程序名称：IndiDueFeeQuery.jsp
	//程序功能：
	//创建日期：
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
	//
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--用户校验类-->
<%@page import="java.util.*"%>
<%@page import="java.lang.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.operfee.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
	//获取保单号  
	String ContNo = request.getParameter("ContNo");

	// 输出参数
	CErrors tError = null;
	String FlagStr = "";
	String Content = "";

	GlobalInput tGI = new GlobalInput(); 
	tGI = (GlobalInput) session.getValue("GI"); //参见loginSubmit.jsp
	if (tGI == null) {
		loggerDebug("IndiDueFeeQueryCancel","页面失效,请重新登陆");
		FlagStr = "Fail";
		Content = "页面失效,请重新登陆";
	} else //页面有效
	{
		LCContSchema tLCContSchema = new LCContSchema(); // 个人保单表
		tLCContSchema.setContNo(ContNo);
		
		VData tVData = new VData();
		tVData.add(tLCContSchema);
		tVData.add(tGI);
		
		//IndiDueFeeCancelUI tIndiDueFeeCancelUI = new IndiDueFeeCancelUI();
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		//tIndiDueFeeCancelUI.submitData(tVData, "INSERT");
		tBusinessDelegate.submitData(tVData, "INSERT","IndiDueFeeCancelUI");
		//tError = tIndiDueFeeCancelUI.mErrors;
		tError = tBusinessDelegate.getCErrors();
		if (!tError.needDealError()) {
			Content = "处理成功！";
			FlagStr = "Succ";
		} else {
			Content = "处理失败！原因：" + tError.getFirstError();
			FlagStr = "Fail";
		}
	} //页面有效区
%>
<html>
<script language="javascript">
     parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
<body>
</body>
</html>

