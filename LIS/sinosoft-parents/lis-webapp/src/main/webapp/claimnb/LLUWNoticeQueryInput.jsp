<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	  GlobalInput tGI = new GlobalInput();
	  tGI = (GlobalInput)session.getValue("GI");
	  String tContNo =  request.getParameter("ContNo");
%>
<script>
	  var managecom = "<%=tGI.ManageCom%>"; //记录管理机构
	  var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
	  var ContNo = "<%=tContNo%>";
</script>
<head>
	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    
<SCRIPT src="LLUWNoticeQuery.js"></SCRIPT>
<%@include file="LLUWNoticeQueryInit.jsp"%>
  <title>查询承保核保通知书 </title>
</head>
<body  onload="initForm();" >
  
  <form  method=post name=fm id=fm target="fraSubmit">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 二核过程中发起的通知书
    		</td>
    	</tr>
    </table>
    
  	<Div  id= "divLCPol1" style= "display: ''" align = center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanNoticeGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
        <Input VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
		<Input VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage.previousPage();"> 					
		<Input VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
		<Input VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage.lastPage();"> 	
	</div>
  	<table class=common style="display:none">
  		<tr class=common>
  			<td class=common>
              <INPUT VALUE="返  回" class= cssButton TYPE=button onclick="top.close();">
  	        </td>
  	    </tr>
  	</table>
    <br>
    <a href="javascript:void(0);" class="button" onClick="top.close();">返    回</a>
  	 <Input  type= "hidden" id="ContNo" name="ContNo">
     <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  </form>
</body>
</html>
