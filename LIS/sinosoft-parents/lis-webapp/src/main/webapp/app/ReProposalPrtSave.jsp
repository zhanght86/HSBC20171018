<%
//程序名称：ReProposalPrintInput.jsp
//程序功能：
//创建日期：2002-11-25
//创建人  ：Kevin
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%!
	String handleFunction(HttpSession session, HttpServletRequest request) {
	int nIndex = 0;
	String tLCPolGrids[] = request.getParameterValues("PolGridNo");
	String tContNo[] = request.getParameterValues("PolGrid1");
	String tPrtNo[] = request.getParameterValues("PolGrid3");
	String tMg[] = request.getParameterValues("PolGrid6");
  String tLCContFamilyContNo[] = request.getParameterValues("PolGrid9");
  String tLCContFamilyType[] = request.getParameterValues("PolGrid8");
	String tChecks[] = request.getParameterValues("InpPolGridChk");
	String strOperation = request.getParameter("fmtransact");
	

	GlobalInput globalInput = new GlobalInput();

	if( (GlobalInput)session.getValue("GI") == null )
	{
		return "网页超时或者是没有操作员信息，请重新登录";
	}
	else
	{
		globalInput.setSchema((GlobalInput)session.getValue("GI"));
	}

	if( tLCPolGrids == null ) {
		return "没有输入需要的打印参数";
	}

	LCContSet tLCContSet = new LCContSet();
	LCContPrintTraceSet tLCContPrintTraceSet = new LCContPrintTraceSet();
	
	//ReLCContF1PUI tReLCContF1PUI = new ReLCContF1PUI();
   String busiName="f1printReLCContF1PUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	for(nIndex = 0; nIndex < tChecks.length; nIndex++ )
	{
		// If this line isn't selected, continue
		if( !tChecks[nIndex].equals("1") )
		{
		  continue;
		}

		if( tContNo[nIndex] == null || tContNo[nIndex].equals("") ) {
		  return "请输入保单号的信息";
		}

		LCContSchema tLCContSchema = new LCContSchema();
		tLCContSchema.setContNo( tContNo[nIndex] );
		tLCContSchema.setPrtNo( tPrtNo[nIndex] );
		tLCContSchema.setFamilyContNo(tLCContFamilyContNo[nIndex]);
		tLCContSchema.setFamilyType(tLCContFamilyType[nIndex]);
		tLCContSet.add(tLCContSchema);
		
		LCContPrintTraceSchema tLCContPrintTraceSchema = new LCContPrintTraceSchema();
		tLCContPrintTraceSchema.setContNo( tContNo[nIndex] );
		tLCContPrintTraceSchema.setPrtNo( tPrtNo[nIndex] );
		tLCContPrintTraceSchema.setBatchNo(request.getParameter("BatchNo"));
		tLCContPrintTraceSchema.setManageCom(tMg[nIndex]);
		loggerDebug("ReProposalPrtSave","BatchNo"+ request.getParameter("BatchNo"));
		//0|计费|^1|不计费"
		tLCContPrintTraceSchema.setNeedPay(request.getParameter("NeedPay"));
		//1|打印机故障,2|操作失误,3|数据错误,4|遗失或毁损,5|其它"
		tLCContPrintTraceSchema.setReason(request.getParameter("Reason"));
		tLCContPrintTraceSet.add(tLCContPrintTraceSchema);
		
	} 
   
	// Prepare data for submiting
	VData vData = new VData();

	vData.addElement(tLCContSet);
	vData.addElement(tLCContPrintTraceSet);
	vData.add(globalInput);

	try {
		if( !tBusinessDelegate.submitData(vData, strOperation,busiName) )
		{
	   		if ( tBusinessDelegate.getCErrors().needDealError() )
	   		{
	   			return tBusinessDelegate.getCErrors().getFirstError();
		  	}
		  	else
		  	{
		  		return "保存失败，但是没有详细的原因";
			}
		}

	}
	catch (Exception ex)
	{
		ex.printStackTrace();
		return ex.getMessage();
	}
	return "";
}
%>
<%
String FlagStr = "";
String Content = "";

try {
	Content = handleFunction(session, request);

	if( Content.equals("") ) {
		String strTemplatePath = application.getRealPath("xerox/printdata") + "/";
		FlagStr = "Succ";
		Content = "提交申请成功";
	} else {
		FlagStr = "Fail";
	}
} catch (Exception ex) {
	ex.printStackTrace();
}
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

