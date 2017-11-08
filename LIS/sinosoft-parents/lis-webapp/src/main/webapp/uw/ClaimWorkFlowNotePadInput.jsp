<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<%                                                                                
	String sMissionID = request.getParameter("MissionID");
	String sSubMissionID = request.getParameter("SubMissionID");                  
	String sActivityID = request.getParameter("ActivityID");                      
    String sPrtNo = request.getParameter("PrtNo");
    String sNoType = request.getParameter("NoType"); 
    String sUnSaveFlag = request.getParameter("UnSaveFlag"); //如果只是查询，则隐藏保存的按钮，UnSaveFlag为"1"    
%>                                                                                
                                                                                  
<script> 
var QueryFlag = <%=request.getParameter("QueryFlag")%>;
function initParam()       
{                                                                  
	fm.MissionID.value = "<%= sMissionID %>";
	fm.SubMissionID.value = "<%= sSubMissionID %>";
	fm.ActivityID.value = "<%= sActivityID %>";
	fm.PrtNo.value = "<%= sPrtNo %>";
	fm.NoType.value = "<%= sNoType %>";
	fm.UnSaveFlag.value = "<%= sUnSaveFlag %>";
}	
</script> 
  
  <SCRIPT src="ClaimWorkFlowNotePad.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ClaimWorkFlowNotePadInit.jsp"%>
  <title>记事本查看</title>
  
</head>


<body  onload="initForm();" >
  <form action="./WorkFlowNotePadSave.jsp" method=post name=fm id="fm" target="fraSubmit">
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
			         <INPUT VALUE="首  页" class=cssButton90 TYPE=button onClick="turnPage.firstPage();"> 
			         <INPUT VALUE="上一页" class=cssButton91 TYPE=button onClick="turnPage.previousPage();"> 					
			         <INPUT VALUE="下一页" class=cssButton92 TYPE=button onClick="turnPage.nextPage();"> 
			         <INPUT VALUE="尾  页" class=cssButton93 TYPE=button onClick="turnPage.lastPage();"> 	      
		    </div>
			
			  <table class= common>
			    <TR  class= common> 
			      <TD width="100%" height="15%"  class= title> 记事内容（400字以内,回车符占一个汉字）</TD>
			    </TR>
			    <TR  class= common>
			      <TD height="85%"  class= title><textarea name="Content" verify="记事内容|len<800" verifyorder="1" cols="130" rows="5" class="common" ></textarea></TD>
			    </TR>
			  </table>
			<div id= "divButton" style= "display: ''" >			  
			  <INPUT CLASS=cssButton VALUE="新  增" TYPE=button onClick="addNote();">
			  <!--<INPUT CLASS=cssButton VALUE="清  空" TYPE=button onclick="fm.Content.value=''">-->
			  <INPUT class=cssButton VALUE="返  回" TYPE=button onClick="top.close();">	
			</div>
			<div id= "divReturn" style= "display: none" >			  
			  <INPUT class=cssButton VALUE="返  回" TYPE=button onClick="top.close();">	
			</div>
	  </div>	   
   </div> 	 
   
      	 <Input type=hidden id="MissionID" name="MissionID" >
    	 <Input type=hidden id="SubMissionID" name="SubMissionID" >
    	 <Input type=hidden id="ActivityID" name="ActivityID" >
    	 <Input type=hidden id="PrtNo" name="PrtNo" >
    	 <Input type=hidden id="NoType" name="NoType" >
    	 <Input type=hidden id="UnSaveFlag" name="UnSaveFlag" >
    	    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
