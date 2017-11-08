<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
//程序名称：
//程序功能：
//创建日期：
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	
	String tContNo = "";
	try
	{
		tContNo = request.getParameter("ContNo");
	}
	catch( Exception e )
	{
		tContNo = "";
	}
	String tflag = "";
	try
	{
		tflag = request.getParameter("flag");
	}
	catch( Exception e )
	{
		tflag = "";
	}
%>

<%
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
%>   
<Script>
var tContNo = "<%=tContNo%>"	
var tflag = "<%=tflag%>"
var comCode = <%=tG.ComCode%>
</Script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="PolBqEdor.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./PolBqEdorInit.jsp"%>
  <title>个人保单保全查询 </title>
</head>
<body  onload="initForm();">
  <form action="./PolBqEdorSubmit.jsp" method=post name=fm id=fm target="fraSubmit" >
    <!-- 保单信息部分 -->
	<div class="maxbox1">
    <table  class= common>
      	<TR  class= common>
         <td class=title>
            个人保单号
         </td>
         <td class= input>
         <Input class="wid" class=readonly readonly   name=ContNo1 id=ContNo1>
         </td>
         <td class=title></td>
         <td class= input></td>
         <td class=title></td>
         <td class= input></td>
     
    </table>
</div>
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBqEdorInfo);">
    		</td>
    		<td class= titleImg>
    			 保全历史
    		</td>
    	</tr>
    </table>
    
  	<Div  id= "divBqEdorInfo" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanBqEdorGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    </Div>	
    	
     <Div  id= "divButton" style= "display: ''" align = center>
      <INPUT VALUE="首页" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT VALUE="尾页" class=cssButton93 TYPE=button onclick="turnPage.lastPage();">
    </Div>	
 
    	<!--<INPUT class=cssButton VALUE=" 保全明细 " TYPE=button onclick="gotoDetail();">
        <INPUT class=cssButton VALUE="  返  回  " TYPE=button onclick="returnParent();">-->
        <a href="javascript:void(0);" class="button" onClick="gotoDetail();">保全明细</a>
        <a href="javascript:void(0);" class="button" onClick="returnParent();">返    回</a>
    	<input type=hidden id="fmtransact" name="fmtransact">
        <input type=hidden id="Transact" name="Transact">
    	<input type=hidden id="EdorAcceptNo" name= "EdorAcceptNo">
  	    <input type=hidden id="EdorNo" name= "EdorNo">
  	    <input type=hidden id="EdorAppDate" name= "EdorAppDate">
  	    <input type=hidden id="EdorState" name= "EdorState">
  	    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
