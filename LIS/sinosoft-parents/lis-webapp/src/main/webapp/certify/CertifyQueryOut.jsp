<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//程序名称：CertifyQueryOut.jsp
//程序功能：
//创建日期：2002-08-18
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
  String strSQL = "";
  String strSum = "";
  
  try {
    // 由于日期的查询条件有可能有两值，我们在第一个Schema中保存开始时间，在第二个Schema中保存结束时间
    LZCardSet tLZCardSet = new LZCardSet();
    
		LZCardSchema tLZCardSchema = new LZCardSchema();
		
		tLZCardSchema.setCertifyCode( request.getParameter("CertifyCode") );
		tLZCardSchema.setState( request.getParameter("State") );

		tLZCardSchema.setSendOutCom( request.getParameter("SendOutCom") );
		tLZCardSchema.setReceiveCom( request.getParameter("ReceiveCom") );

		tLZCardSchema.setStartNo( request.getParameter("StartNo") );
		tLZCardSchema.setEndNo( request.getParameter("EndNo") );
		
		tLZCardSchema.setOperator( request.getParameter("Operator") );
		tLZCardSchema.setHandler( request.getParameter("Handler") );

		tLZCardSchema.setMakeDate( request.getParameter("MakeDateB") );		
		tLZCardSchema.setHandleDate( request.getParameter("HandleDateB") );
		tLZCardSchema.setInvaliDate( request.getParameter("InvaliDateB") );
		
		// 加入第一个Schema
		tLZCardSet.add( tLZCardSchema );
		
		tLZCardSchema = new LZCardSchema();
		
		tLZCardSchema.setMakeDate( request.getParameter("MakeDateE") );		
		tLZCardSchema.setHandleDate( request.getParameter("HandleDateE") );
		tLZCardSchema.setInvaliDate( request.getParameter("InvaliDateE") );
		
		// 加入第二个Schema
		tLZCardSet.add( tLZCardSchema );
		
		GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	    
	  // 准备传输数据 VData
	  VData tVData = new VData();
	
		tVData.add( globalInput );
		tVData.add( tLZCardSet );
		tVData.add( new Hashtable() );
	
	  // 数据传输
	  CertStatUI tCertStatUI = new CertStatUI();
	  
	  if( !tCertStatUI.submitData(tVData, "QUERY||MAIN") ) {
	    if( tCertStatUI.mErrors.needDealError() ) {
	    	throw new Exception( tCertStatUI.mErrors.getFirstError() );
	    } else {
	    	throw new Exception("CertStatUI查询失败，但是没有提供详细的信息");
	    }
	  } else {
	    tVData.clear();
	    tVData = tCertStatUI.getResult();
	    
	    // tLZCardSet = (LZCardSet)tVData.getObjectByObjectName("LZCardSet", 0);
	    
	    // strResult = "0|" + String.valueOf(tLZCardSet.size());
	    // strResult += "^" + tLZCardSet.encode();
	    
			strSQL = (String)tVData.getObjectByObjectName("String", 0);
	    strSum = (String)tVData.getObjectByObjectName("String", 1);
	    
	    loggerDebug("CertifyQueryOut","CertifyQueryOut.jsp");
	    loggerDebug("CertifyQueryOut",strSQL);
	  }
	  
	  FlagStr = "Succ";
	  
	} catch (Exception ex) {
	  ex.printStackTrace();
	  FlagStr = "Fail";
	  Content = ex.getMessage();
	}
%>

<html>
	<script language="javascript">
		parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
<% if( FlagStr.equals("Succ") ) { %>
		 var varResult = new Array();
		 
		 varResult[0] = "<%= strSQL %>";
		 varResult[1] = "<%= strSum %>";
		 
		 parent.fraInterface.onShowResult(varResult);
<% } else { %>
<% } %>
	</script>
</html>
