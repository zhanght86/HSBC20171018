
<%
//程序名称：QualityDifferentiatedSave.jsp
//程序功能：业务员品质差异化维护
//创建日期：2009-11-09 
%>

<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  //输出参数
  	CErrors tError = null;
  	String FlagStr = "Fail";
  	String Content = "";
	TransferData tTransferData=new TransferData();
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");

  	if(tG == null) {
		out.println("session has expired");
		return;
	}		
	tTransferData.setNameAndValue("AgentCode",request.getParameter("AgentCode")); //业务员代码
	tTransferData.setNameAndValue("ManageCom",request.getParameter("ManageCom")); //管理机构     	     	
	tTransferData.setNameAndValue("UWClass",request.getParameter("UWClass")); //业务员类别
	tTransferData.setNameAndValue("UWLevel",request.getParameter("UWLevel")); //差异化级别

	// 准备传输数据 VData
	VData tVData = new VData();
	tVData.add( tTransferData );
	tVData.add( tG );

	// 数据传输
	QualityDifferentiatedUI tQualityDifferentiatedUI   = new QualityDifferentiatedUI();	
	loggerDebug("QualityDifferentiatedSave","before QualityDifferentiatedUI");
	if (tQualityDifferentiatedUI.submitData(tVData,"PrtReplace") == false) {
		int n = tQualityDifferentiatedUI.mErrors.getErrorCount();
		for (int i = 0; i < n; i++){
			Content = " 印刷号替换失败，原因是: " + tQualityDifferentiatedUI.mErrors.getError(0).errorMessage;
		}
		FlagStr = "Fail";
	}
	//如果在Catch中发现异常，则不从错误类中提取错误信息
	if (FlagStr == "Fail") {
	    tError = tQualityDifferentiatedUI.mErrors;
		if (!tError.needDealError()) {
			Content = " 修改成功! ";
		    FlagStr = "Succ";
		} else {
			Content = " 操作失败，原因是:" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
