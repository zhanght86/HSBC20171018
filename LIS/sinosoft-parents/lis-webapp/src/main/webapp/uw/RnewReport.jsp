<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<%                                                                                                      
    String sProposalNo = request.getParameter("ProposalNo");
    String sNoType = request.getParameter("NoType");   
    String sQueryFlag = request.getParameter("QueryFlag"); 
%>                                                                                
<script> 
 var sQueryFlag = "<%= sQueryFlag %>";
    function initParam()       
	{                                                                  
		fm.ProposalNo.value = "<%= sProposalNo %>";
		fm.NoType.value = "<%= sNoType %>";
	}
</script>  
  <SCRIPT src="RnewReport.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="RnewReportInit.jsp"%>
  <title>�˱���������</title>
  
</head>

<body  onload="initForm();" >
  <form action="./RnewReportSave.jsp" method=post name=fm id="fm" target="fraSubmit">
  <div id= "divNotePad" style= "display: ''" >
	  <table>
	    	<tr>
		        <td class=common>
		          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divNotePadInfo);">
		    	</td>
		    	<td class= titleImg>
		    	 �˱�����������Ϣ 
		    	</td>
	    	</tr>
	    </table>        
		<div id= "divReportInfo" style= "display:" >

		    <Div  id= "divReportGrid" style= "display: " align = center>
		      	<table  class= common>
		       		<tr  class= common>
		      	  		<td text-align: left colSpan=1 >
		  				<span id="spanReportGrid">
		  				</span> 
		  		  		</td>
		  			</tr>
		    	</table>
			         <INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onClick="turnPage.firstPage();"> 
			         <INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onClick="turnPage.previousPage();"> 					
			         <INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onClick="turnPage.nextPage();"> 
			         <INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onClick="turnPage.lastPage();"> 	      
		    </div>
			
			  <table class= common>
			    <TR  class= common> 
			      <TD width="100%" height="15%"  class= title> �˱������������ݣ�800������,�س���ռһ�����֣�</TD>
			    </TR>
			    <TR  class= common>
			      <TD height="85%"  class= title><textarea name="Content" verify="������������|len<=800" verifyorder="1" cols="130" rows="10" class="common" ></textarea></TD>
			    </TR>
			  </table>
			<div id= "divButton" style= "display: " >			  
			  <INPUT CLASS=cssButton VALUE="��  ��" id="Add" name="Add" TYPE=button onClick="addReport();">
			  <!--<INPUT CLASS=cssButton VALUE="��  ��" TYPE=button onclick="fm.Content.value=''">-->
			  <INPUT class=cssButton VALUE="��  ��" TYPE=button onClick="top.close();">	
			</div>
	  </div>	   
   </div> 
    	 <Input type=hidden id="ProposalNo" name="ProposalNo" >
    	 <Input type=hidden id="NoType" name="NoType" >    	    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
