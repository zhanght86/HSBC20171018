<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//程序名称：CertifyListPrint.jsp
//程序功能：打印单证处理清单的功能模块
//创建日期：2003-04-17
//创建人  ：Kevin
//更新记录：  更新人    更新日期     更新原因/内容
%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.certify.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="java.util.*"%>

	<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
  String FlagStr = "Fail";
  String Content = "";
  String strResult = "";
  String strSum = "";
  
  try {
    // 由于日期的查询条件有可能有两值，我们在第一个Schema中保存开始时间，在第二个Schema中保存结束时间
    LZCardSet tLZCardSet = new LZCardSet();
    
    String strTakeBackNo = (String)session.getAttribute("TakeBackNo");
    
		LZCardSchema tLZCardSchema = new LZCardSchema();
		
		Hashtable hashParams = new Hashtable();
		
		// 直接从session中取值
		if( strTakeBackNo != null || !strTakeBackNo.equals("") ) {
			
			tLZCardSchema.setState( (String)session.getAttribute("State") );
			tLZCardSchema.setTakeBackNo( strTakeBackNo );
			
			if( session.getAttribute("NoUseAgentTemplate") != null ) {
				hashParams.put("NoUseAgentTemplate", "YES");
			}
			
			// Build set needed by CertStatUI
			tLZCardSet.add( tLZCardSchema );
			tLZCardSet.add( tLZCardSchema );
			
			// After using, clear session data
			session.removeAttribute("TakeBackNo");
			session.removeAttribute("State");
			session.removeAttribute("NoUseAgentTemplate");
			
		} else {  // 否则，从request对象中取值
			tLZCardSchema.setCertifyCode( request.getParameter("CertifyCode") );
			tLZCardSchema.setState( request.getParameter("State") );
	
			tLZCardSchema.setSendOutCom( request.getParameter("SendOutCom") );
			tLZCardSchema.setReceiveCom( request.getParameter("ReceiveCom") );
	
			tLZCardSchema.setOperator( request.getParameter("Operator") );
			tLZCardSchema.setHandler( request.getParameter("Handler") );
	
			tLZCardSchema.setMakeDate( request.getParameter("MakeDateB") );		
			tLZCardSchema.setHandleDate( request.getParameter("HandleDateB") );
			tLZCardSchema.setInvaliDate( request.getParameter("InvaliDateB") );
			
			tLZCardSchema.setTakeBackNo( request.getParameter("TakeBackNo") );
			
			// 加入第一个Schema
			tLZCardSet.add( tLZCardSchema );
			
			tLZCardSchema = new LZCardSchema();
			
			tLZCardSchema.setMakeDate( request.getParameter("MakeDateE") );		
			tLZCardSchema.setHandleDate( request.getParameter("HandleDateE") );
			tLZCardSchema.setInvaliDate( request.getParameter("InvaliDateE") );
			
			// 加入第二个Schema
			tLZCardSet.add( tLZCardSchema );
		}
		
		GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	    
	  // 准备传输数据 VData
	  VData tVData = new VData();
	
		tVData.add( globalInput );
		tVData.add( tLZCardSet );
		tVData.add( hashParams );
	
	  // 数据传输
	  CertStatUI tCertStatUI = new CertStatUI();
	  
	  if( !tCertStatUI.submitData(tVData, "QUERY||PRINT") ) {
	    if( tCertStatUI.mErrors.needDealError() ) {
	    	throw new Exception( tCertStatUI.mErrors.getFirstError() );
	    } else {
	    	throw new Exception("CertStatUI查询失败，但是没有提供详细的信息");
	    }
	  } else {
	    tVData.clear();
	    tVData = tCertStatUI.getResult();
	
			XmlExport xe = (XmlExport)tVData.getObjectByObjectName("XmlExport", 0);
			session.setAttribute("PrintStream", xe.getInputStream());
		  loggerDebug("CertifyListPrint","put session value");
		  response.sendRedirect("../f1print/GetF1Print.jsp");
	  }
	  
	  FlagStr = "Succ";
	  
	} catch (Exception ex) {
	  ex.printStackTrace();
	  FlagStr = "Fail";
	  Content = ex.getMessage();
	}
%>
