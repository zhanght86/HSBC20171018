
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
<%@page import="com.sinosoft.lis.reagent.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.service.*"%>

<%
	//获取保单号  
	String tGetNoticeNo = request.getParameter("GetNoticeNo");
	String tContNo = request.getParameter("ContNo");
	String tReaSonType = request.getParameter("ReaSonType");
	String tReMark = request.getParameter("ReMark");

	// 输出参数
	CErrors tError = null;
	String FlagStr = "";
	String Content = "";

	GlobalInput tGI = new GlobalInput(); 
	tGI = (GlobalInput) session.getValue("GI"); //参见loginSubmit.jsp
	if (tGI == null) {
		loggerDebug("ReVerifyRollBackSave","页面失效,请重新登陆");
		FlagStr = "Fail";
		Content = "页面失效,请重新登陆";
	} else //页面有效
	{
	  TransferData tTransferData = new TransferData();
	  tTransferData.setNameAndValue("GetNoticeNo",tGetNoticeNo);
    tTransferData.setNameAndValue("ContNo",tContNo);
    tTransferData.setNameAndValue("ReaSonType",tReaSonType);
    tTransferData.setNameAndValue("ReMark",tReMark);
    
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(tGI);
		
		//ReVerifyRollBackUI tReVerifyRollBackUI = new ReVerifyRollBackUI();
		//tReVerifyRollBackUI.submitData(tVData, "");
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		tBusinessDelegate.submitData(tVData,"","ReVerifyRollBackUI");
    tError =  tBusinessDelegate.getCErrors();
		
		

		//tError = tReVerifyRollBackUI.mErrors;
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

