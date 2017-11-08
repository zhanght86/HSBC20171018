<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//程序名称：SelfCertifySearchOut.jsp
//程序功能：自助卡单打印清单
//创建日期：2008-03-13 
//创建人  ：zz
//更新记录：  更新人    更新日期     更新原因/内容
%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.certify.*"%>
  <%@page import="com.sinosoft.lis.list.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="java.util.*"%>

	<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
  String FlagStr = "Fail";
  String Content = "";
  String strResult = "";
  
  try 
  {
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
		tLZCardSchema.setMakeDate( request.getParameter("MakeDateB") );
		
		// 加入第一个Schema
		tLZCardSet.add( tLZCardSchema );
		
		tLZCardSchema = new LZCardSchema();		
		tLZCardSchema.setMakeDate( request.getParameter("MakeDateE") );		
		
		// 加入第二个Schema
		tLZCardSet.add( tLZCardSchema );
		
		GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	    
	  	// 准备传输数据 VData
	  	VData tVData = new VData();
	
		tVData.add( globalInput );
		tVData.add( tLZCardSet );
		tVData.add( new Hashtable() );
	
	  	// 数据传输
	  	SelfCertifySearchUI tSelfCertifySearchUI = new SelfCertifySearchUI();
	  
	  	if( !tSelfCertifySearchUI.submitData(tVData, "SEARCH||MAIN") ) 
	  	{
	    	if( tSelfCertifySearchUI.mErrors.needDealError() )
	     	{
	    		throw new Exception( tSelfCertifySearchUI.mErrors.getFirstError() );
	    	} 
	    	else 
	    	{
	    		throw new Exception("CertifySearchUI查询失败，但是没有提供详细的信息");
	    	}
	  } 
	  else 
	  {
	    tVData.clear();
	    tVData = tSelfCertifySearchUI.getResult();
	    
	    SSRS tSSRS=null;
	    
	    tSSRS = (SSRS)tVData.getObjectByObjectName("SSRS", 0);
	    
	    //strResult = "0|" + String.valueOf(tSSRS.getMaxRow());
	    //strResult += "^" + tSSRS.encode();
	    strResult =tSSRS.encode();
	    loggerDebug("SelfCertifySearchOut","strResult:"+strResult);
	  }
	  
	  FlagStr = "Succ";
	  
	} 
	catch (Exception ex)
	 {
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
		 
		 varResult[0] = "<%= strResult %>";
		 
		 parent.fraInterface.onShowResult(varResult);
<% } else { %>
<% } %>
	</script>
</html>
