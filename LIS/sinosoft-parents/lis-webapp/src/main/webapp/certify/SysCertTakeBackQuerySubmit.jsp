<%
//程序名称：SysCertTakeBackSubmit.jsp
//程序功能：
//创建日期：2002-10-14 10:20:50
//创建人  ：kevin
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.certify.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  
<%!
	String buildMsg(boolean bFlag, String strMsg) {
		String strReturn = "";
		
		strReturn += "<script language=\"javascript\">";
		
		if( bFlag ) {
			strReturn += "  parent.fraInterface.afterSubmit('Succ', '操作成功完成');";
		} else {
			strReturn += "  parent.fraInterface.afterSubmit('Fail','" + strMsg + "');";
		}
		strReturn += "</script>";
		
		return strReturn;
	}
%>

<html>

<%
  SysCertTakeBackUI sysCertTakeBackUI = new SysCertTakeBackUI();

  if( (GlobalInput)session.getValue("GI") == null ) {
  	out.println( buildMsg(false, "网页超时或者是没有操作员信息") );
		return;
	}

	try {
		GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	  LZSysCertifySchema schemaLZSysCertify = new LZSysCertifySchema();
	
	  schemaLZSysCertify.setCertifyCode(request.getParameter("CertifyCode"));
	  schemaLZSysCertify.setCertifyNo(request.getParameter("CertifyNo"));
	  schemaLZSysCertify.setValidDate(request.getParameter("ValidDate"));
	  schemaLZSysCertify.setSendOutCom(request.getParameter("SendOutCom"));
	  schemaLZSysCertify.setReceiveCom(request.getParameter("ReceiveCom"));
	  schemaLZSysCertify.setHandler(request.getParameter("Handler"));
	  schemaLZSysCertify.setHandleDate(request.getParameter("HandleDate"));
	  
	  String strSQL = request.getParameter("sql_where");
	  
	  if( strSQL == null ) {
	  	strSQL = "";
	  }

	  // 准备传输数据 VData
	  VData tVData = new VData();
	
		tVData.addElement(schemaLZSysCertify);
		tVData.add(globalInput);
		tVData.add(strSQL);
	
	  // 数据传输
	  loggerDebug("SysCertTakeBackQuerySubmit","before sysCertTakeBackUI.submitData");
		if (!sysCertTakeBackUI.submitData(tVData, "QUERY||MAIN")) {
			out.println( buildMsg(false, "查询失败，原因是:" + sysCertTakeBackUI.mErrors.getFirstError()));
			return;
		
		} else {
			tVData.clear();
			tVData = sysCertTakeBackUI.getResult();
			
			// 显示
			LZSysCertifySet setLZSysCertify = new LZSysCertifySet();
			setLZSysCertify.set((LZSysCertifySet)tVData.getObjectByObjectName("LZSysCertifySet",0));
	    int i=0;		
	    out.println("<script language=javascript>");
	    out.println("function getGridResult()");
	    out.println("{");
	    out.println("parent.fraInterface.arrStrReturn[0]=\"0|" + String.valueOf(setLZSysCertify.size()) + "^" + setLZSysCertify.encode()+"\";");
	    out.println("}");
	    out.println("</script>");
		} // end of if
		
	} catch (Exception ex) {
		ex.printStackTrace();
		out.println( buildMsg(false, "发生异常") );
		return;
	}
	
	out.println( buildMsg(true, "查询成功") );
%>

</html>
