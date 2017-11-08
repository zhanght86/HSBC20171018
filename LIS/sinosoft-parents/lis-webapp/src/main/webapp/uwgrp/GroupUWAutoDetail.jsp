<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	String tContNo = "00000000000000000000";
	String tGrpContNo="";
	String tGrpPrtNo="";
	try
	{
		//tContNo = request.getParameter( "ContNo" );
		tGrpContNo = request.getParameter( "ProposalGrpContNo");
		tGrpPrtNo = request.getParameter( "PrtNo" );
		//默认情况下为集体投保单	
	}	
	catch( Exception e1 )
	{
		tContNo = "00000000000000000000";
		loggerDebug("GroupUWAutoDetail","contno:"+tContNo +"grpprtno:"+tGrpPrtNo);
	}	
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("GroupUWAutoDetail","---contno:"+tContNo+"prtno"+tGrpPrtNo+"GrpPolNo"+tGrpContNo);
%>
<script>
	var grpPolNo = "<%=tGrpContNo%>";      //个人单的查询条件.
	var grpprtno = "<%=tGrpPrtNo%>";  
	var contNo = "<%=tContNo%>";          //个人单的查询条件.
</script>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="GroupUWAutoDetail.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GroupUWAutoDetailInit.jsp"%>
  <title>待自动核保团体投保信息 </title>
</head>
<body  onload="initForm();" >
  <form action="./GroupUWAutoChk.jsp" method=post name=fm target="fraSubmit">
   
          	
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 团体保单信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanGrpPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="首页" class= common TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="上一页" class= common  TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class= common TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="尾页" class= common TYPE=button onclick="getLastPage();"> 					
  	</div>
  	 <br></br>
  	 <INPUT TYPE="hidden" name= "ProposalGrpContNo" value= "">
     <INPUT TYPE="hidden" name= "PrtNo" value= "">
     
     <INPUT VALUE="自动核保所选投保单" class= common TYPE=button onclick="GrpUWAutoMakeSure();">
     <INPUT VALUE="返回" class= common TYPE=button onclick="GoBack();">
   </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
