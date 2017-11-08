<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>

<%
	String mPolNo = "";
	String mInsuAccNo = "";
	String mPayPlanCode = "";
	
	try
	{
		mPolNo = request.getParameter( "PolNo" );
		mInsuAccNo = request.getParameter( "InsuAccNo" );
		mPayPlanCode = request.getParameter( "PayPlanCode" );
		loggerDebug("GpsLCInsureAccTraceQuery","---PolNo:"+mPolNo);
	}
	
	
	catch( Exception e1 )
	{
		
			loggerDebug("GpsLCInsureAccTraceQuery","e1");

	}
 %>
<%@include file="GpsLCInsureAccTraceQueryInit.jsp"%>
<script>
	var tPolNo = "<%=mPolNo%>"; 
	var tInsuAccNo = "<%=mInsuAccNo%>";
	var tPayPlanCode = "<%=mPayPlanCode%>"; //查询条件.

</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="GpsLCInsureAccTraceQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>


  <title>帐户轨迹查询 </title>
</head>
<body onload="initForm();LCInsureAccTraceQuery();">
  <form method=post name=fm id=fm target="fraSubmit">
   
  	<Div  id= "divLCPol1" style= "display: ''" align = center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanGrpPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="首  页" class = cssButton90 TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="上一页" class = cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class = cssButton92 TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="尾  页" class = cssButton93 TYPE=button onclick="getLastPage();"> 					
  	</Div>
  	  
  	 
  	<INPUT class=cssButton VALUE="返  回" TYPE=button onclick="GoBack();">
  	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  	</form>
  	
</body>
</html>
