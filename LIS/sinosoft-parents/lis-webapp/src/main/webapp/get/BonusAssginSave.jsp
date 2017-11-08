<%//程序名称：
			//程序功能：分红险分配批处理
			//创建日期：2008-11-09 17:55:57
			//创建人  ：彭送庭%>
<!--用户校验类-->
 <%@include file="../common/jsp/UsrCheck.jsp"%>
 <%@page import="com.sinosoft.utility.*"%>
 <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@page import="com.sinosoft.lis.get.*"%>
 <%@page import="com.sinosoft.service.*" %>
 
 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
	String busiName="getBonusAssignManuBL";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	//输出参数
	CErrors tError = null;

	String FlagStr = "";
	String Content = "";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
	String tFiscalYear = request.getParameter("FiscalYear");
	String tContNo = request.getParameter("ContNo");
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("FiscalYear", tFiscalYear);
	tTransferData.setNameAndValue("ContNo", tContNo);
	VData tVData = new VData();
	tVData.addElement(tTransferData);
	tVData.addElement(tG);
	

	if (!tBusinessDelegate.submitData(tVData, "",busiName)) {
		tError = tBusinessDelegate.getCErrors();
		Content = " 计算失败，原因是:" + tError.getFirstError();
		FlagStr = "Fail";
	} else {
		Content = "计算成功！";
		FlagStr = "Succ";
	}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

