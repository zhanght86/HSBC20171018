<%
//程序名称：ReGroupPolPrintSave.jsp
//程序功能：
//创建日期：2002-11-26
//创建人  ：Kevin
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%!
	
	String handleFunction(HttpSession session, HttpServletRequest request) {
		int nIndex = 0;
		//String tLCGrpContNos[] = request.getParameterValues("GrpPolGridNo");
		String tLCGrpContNos[] = request.getParameterValues("GrpPolGrid1");
		String tChecks[] = request.getParameterValues("InpGrpPolGridChk");
		String strOperation = request.getParameter("fmtransact");

		GlobalInput globalInput = new GlobalInput();
		
		if( (GlobalInput)session.getValue("GI") == null ) {
			return "网页超时或者是没有操作员信息，请重新登录";
		} else {
			globalInput.setSchema((GlobalInput)session.getValue("GI"));
		}
		
		if( tLCGrpContNos == null ) {
			return "没有输入需要的打印参数";
		}

		LCGrpContSet tLCGrpContSet = new LCGrpContSet();
		//ReLCGrpContF1PUI tReLCGrpContF1PUI = new ReLCGrpContF1PUI();
		String busiName="f1printReLCGrpContF1PUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		for(nIndex = 0; nIndex < tChecks.length; nIndex++ ) {
			// If this line isn't selected, continue
			if( !tChecks[nIndex].equals("1") ) {
			  continue;
			}
			
			if( tLCGrpContNos[nIndex] == null || tLCGrpContNos[nIndex].equals("") ) {
			  return "请输入保单号的信息";
			}

			LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
			
			tLCGrpContSchema.setGrpContNo( tLCGrpContNos[nIndex] );
			
			tLCGrpContSet.add(tLCGrpContSchema);
		}
		
		// Prepare data for submiting
		VData vData = new VData();
		
		vData.addElement(tLCGrpContSet);
		vData.add(globalInput);
	
		try {
			if( !tBusinessDelegate.submitData(vData, strOperation,busiName) ) {
		   	if ( tBusinessDelegate.getCErrors().needDealError() ) {
		   		return tBusinessDelegate.getCErrors().getFirstError();
			  } else {
			  	return "保存失败，但是没有详细的原因";
				}
			} 			
		} catch (Exception ex) {
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

