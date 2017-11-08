<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<%                                                                                
	String sMissionID = request.getParameter("MissionID");
	String sSubMissionID = request.getParameter("SubMissionID");                  
	String sActivityID = request.getParameter("ActivityID");                      
    String sPrtNo = request.getParameter("PrtNo");
    String sNoType = request.getParameter("NoType");     
%>                                                                                
                                                                                  
<script> 
function initParam()       
{                                                                  
	fm.MissionID.value = "<%= sMissionID %>";
	fm.SubMissionID.value = "<%= sSubMissionID %>";
	fm.ActivityID.value = "<%= sActivityID %>";
	fm.PrtNo.value = "<%= sPrtNo %>";
	fm.NoType.value = "<%= sNoType %>";
}	
</script> 
  
  <SCRIPT src="WorkFlowNotePad.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="WorkFlowNotePadInit.jsp"%>
  <title>记事本查看</title>
  
</head>


<body  onload="initForm();" >
  <form action="./WorkFlowNotePadSave.jsp" method=post name=fm target="fraSubmit">
  <div id= "divNotePad" style= "display: ''" >
	  <table>
	    	<tr>
		        <td class=common>
		          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divNotePadInfo);">
		    	</td>
		    	<td class= titleImg>
		    	 记事本信息 
		    	</td>
	    	</tr>
	    </table>        
		<div id= "divNotePadInfo" style= "display: ''" >

		    <Div  id= "divQuestGrid" style= "display: ''" align = center>
		      	<table  class= common>
		       		<tr  class= common>
		      	  		<td text-align: left colSpan=1 >
		  				<span id="spanQuestGrid">
		  				</span> 
		  		  		</td>
		  			</tr>
		    	</table>
			         <INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage.firstPage();"> 
			         <INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage.previousPage();"> 					
			         <INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage.nextPage();"> 
			         <INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage.lastPage();"> 	      
		    </div>
			
			  <table class= common>
			    <TR  class= common> 
			      <TD width="100%" height="15%"  class= title> 记事内容（400字以内,回车符占一个汉字）</TD>
			    </TR>
			    <TR  class= common>
			      <TD height="85%"  class= title><textarea name="Content" verify="记事内容|len<800" verifyorder="1" cols="130" rows="5" class="common" ></textarea></TD>
			    </TR>
			  </table>
			  			  
			  <INPUT CLASS=cssButton VALUE="新  增" TYPE=button onclick="addNote();">
			  <INPUT CLASS=cssButton VALUE="清  空" TYPE=button onclick="fm.Content.value=''">
			  <INPUT class=cssButton VALUE="返  回" TYPE=button onclick="top.close();">	
	  </div>	   
   </div> 	 
   
      	 <Input type=hidden name="MissionID" >
    	 <Input type=hidden name="SubMissionID" >
    	 <Input type=hidden name="ActivityID" >
    	 <Input type=hidden name="PrtNo" >
    	 <Input type=hidden name="NoType" >
    	    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
