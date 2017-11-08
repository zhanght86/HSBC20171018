<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>

<%
	String cPolNo = "";
	try
	{
		cPolNo = request.getParameter( "PolNo" );
		
		loggerDebug("GpsLCInsureAccFeeQuery","---PolNo:"+cPolNo);
	}
	
	
	catch( Exception e1 )
	{
		cPolNo = "00000000000000000000";
			loggerDebug("GpsLCInsureAccFeeQuery","---PolNo:"+cPolNo);

	}
 %>
<%@include file="GpsLCInsureAccFeeQueryInit.jsp" %>
<script>
	var tPolNo = "<%=cPolNo%>";  //查询条件.

</script>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="GpsLCInsureAccFeeQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>


    <title>帐户查询 </title>
</head>
<body onload="initForm();LCInsureAccFeeQuery();">
  <form method=post name=fm id="fm" target="fraSubmit">  	  
  	<table>
    	 
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		<td class= titleImg>
    		管理费信息
    		</td>
    	</tr>
    </table>
    <Div  id= "divLCPol2" style= "display: ''" >	
     <table  class= common>  	
       	<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanLCInsureAccFeeGrid" >
  					</span> 
  			  	</td>
  			</tr>
    </table>

  	</Div>  	  
  	<br>
    <div>
      <a href="javascript:void(0)" class=button onclick="LCInsureAccClassFeeQuery();">子帐户管理费查询</a>
      <a href="javascript:void(0)" class=button onclick="GoBack();">返  回</a>
    </div>
    <br>
  	<!-- <INPUT VALUE="子帐户管理费查询" class = cssButton TYPE=button onclick="LCInsureAccClassFeeQuery();">
  	<INPUT class=cssButton VALUE="返  回" TYPE=button onclick="GoBack();"> -->
  	<Div  id= "divLCPol1" style= "display: none" >	
     <table  class= common>  	
       	<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanLCInsureAccClassFeeGrid" >
  					</span> 
  			  	</td>
  			</tr>
    </table>
    <div align = center>
    <INPUT VALUE="首  页" class = cssButton90 TYPE=button onclick="getFirstPage();"> 
    <INPUT VALUE="上一页" class = cssButton91 TYPE=button onclick="getPreviousPage();"> 					
    <INPUT VALUE="下一页" class = cssButton92 TYPE=button onclick="getNextPage();"> 
    <INPUT VALUE="尾  页" class = cssButton93 TYPE=button onclick="getLastPage();">
  	</div>
  	</Div>  	  
  	
  	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  	</form>
    <br>
    <br>
    <br>
  	<br>
</body>
</html>
