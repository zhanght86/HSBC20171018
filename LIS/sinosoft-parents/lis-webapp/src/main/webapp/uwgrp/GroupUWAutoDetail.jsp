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
		//Ĭ�������Ϊ����Ͷ����	
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
	var grpPolNo = "<%=tGrpContNo%>";      //���˵��Ĳ�ѯ����.
	var grpprtno = "<%=tGrpPrtNo%>";  
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
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
  <title>���Զ��˱�����Ͷ����Ϣ </title>
</head>
<body  onload="initForm();" >
  <form action="./GroupUWAutoChk.jsp" method=post name=fm target="fraSubmit">
   
          	
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 ���屣����Ϣ
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
      <INPUT VALUE="��ҳ" class= common TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class= common  TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class= common TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="βҳ" class= common TYPE=button onclick="getLastPage();"> 					
  	</div>
  	 <br></br>
  	 <INPUT TYPE="hidden" name= "ProposalGrpContNo" value= "">
     <INPUT TYPE="hidden" name= "PrtNo" value= "">
     
     <INPUT VALUE="�Զ��˱���ѡͶ����" class= common TYPE=button onclick="GrpUWAutoMakeSure();">
     <INPUT VALUE="����" class= common TYPE=button onclick="GoBack();">
   </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
