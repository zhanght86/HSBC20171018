
<%
	//程序名称：IndiFinVerifyUrgeGet.jsp
	//程序功能：
	//创建日期：
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
	//
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.operfee.*"%>
<%@page import="com.sinosoft.lis.finfee.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK"%>
<%
	GlobalInput tGI = new GlobalInput(); //repair:
	tGI = (GlobalInput) session.getValue("GI"); //参见loginSubmit.jsp
	
	// 输出参数
	CErrors tError = null;
	String FlagStr = "";
	String Content = "";

	int recordCount = 0;
	String TempFeeNo = request.getParameter("TempFeeNo");
	
	LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
	tLJTempFeeSchema.setTempFeeNo(TempFeeNo);
	
	VData tVData = new VData();
	tVData.add(tGI);
	tVData.add(tLJTempFeeSchema);

	IndiNormalPayVerifyUI tIndiNormalPayVerifyUI = new IndiNormalPayVerifyUI();
	tIndiNormalPayVerifyUI.submitData(tVData, "VERIFY");
	tError = tIndiNormalPayVerifyUI.mErrors;
	if (!tError.needDealError()) {
		Content = " 核销事务成功";
		FlagStr = "Succ";
	} else {
		Content = " 核销事务失败，原因是:" + tError.getFirstError();
		FlagStr = "Fail";
	}

	Content.replace('\"', '$');
	System.out.println("Content:" + Content);
%>
<HTML>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</HTML>
