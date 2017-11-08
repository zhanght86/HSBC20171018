<%@page import="com.sinosoft.utility.SSRS"%>
<%@page import="com.sinosoft.utility.TransferData"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.ibrms.RuleMapCached"%>
<%@include file="../jsp/Log4jUI.jsp"%>  
<%@include file="./EasyQueryKernel.jsp"%>
<%@ page language="java" pageEncoding="GBK"%>
<%

boolean error = false;
GlobalInput gi = null;
try {
    gi = (GlobalInput)session.getValue("GI");
}
catch(Exception ex) { }
if(gi == null || gi.Operator == null || "".equals(gi.Operator)) {
    error = true;
}
if(error){
    out.println("<script language=javascript>");
    out.println("  session = null;");
    out.println("  try {");
    out.println("    CollectGarbage();");
    out.println("  ) catch(ex) {}");
    out.println("  parent.window.location =\"../../indexlis.jsp\";");
    out.println("</script>");
    session.invalidate();
    return;
}

  String tSQL = request.getParameter("SQL");
   tSQL	= java.net.URLDecoder.decode(tSQL,"UTF-8");
   String intStart = request.getParameter("intStart");
	String LargeFlag = request.getParameter("LargeFlag");
	String strLimitFlag = "0";
	loggerDebug("mulLineQuery","tSQL:"+tSQL);
	loggerDebug("mulLineQuery","intStart:"+intStart);
	loggerDebug("mulLineQuery","LargeFlag:"+LargeFlag);
	loggerDebug("mulLineQuery","strLimitFlag:"+strLimitFlag);
	
	String strResult = easyQueryKernel(tSQL, intStart, LargeFlag, strLimitFlag);
	//loggerDebug("mulLineQuery","strResult:"+strResult);
	out.print(strResult);

%>
