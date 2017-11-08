<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：AppntChk.jsp
//程序功能：投保人查重
//创建日期：2002-11-23 17:06:57
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
  //个人下个人
	String tGrpPolNo = "00000000000000000000";
	//String tContNo = "00000000000000000000";
	String tContNo = request.getParameter("ProposalNo1");
	String tFlag = request.getParameter("Flag");
	//loggerDebug("AppntChk","proposalno:"+tPolNo);
	String tLoadFlag = request.getParameter("LoadFlag");
	String tInsuredNo = request.getParameter("InsuredNo");
        GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("AppntChk","operator:"+tGI.Operator);
%>
<script>
	var grpPolNo = "<%=tGrpPolNo%>";      //个人单的查询条件.
	//alert(grpPolNo);
	var contNo = "<%=tContNo%>";          //个人单的查询条件.
	//alert(contNo);
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var tContNo = "<%=tContNo%>";
	var tFlag = "<%=tFlag%>";
	var LoadFlag = "<%=tLoadFlag%>";
	var tInsuredNo = "<%=tInsuredNo%>";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="AppntChk.js"></SCRIPT>
  
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <%@include file="AppntChkInit.jsp"%>
  
  <title>重复客户校验</title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm target="fraSubmit" action="AppntChkUnionQuestInputSave.jsp">
    <!-- 保单信息部分 -->
	  <table>
	    	<tr>
	        <td class=common>
	          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divOPolGrid);">
	    	</td>
	    	<td class= titleImg>
	    	 原客户信息 
	    	</td>
	    </tr>
	    </table>    
	<div id= "divOPolGrid" style= "display: ''" >
        <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanOPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    </div>	
	  <table>
	    	<tr>
	        <td class=common>
	          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid);">
	    	</td>
	    	<td class= titleImg>
	    	 重复客户信息
	    	</td>
	    </tr>
	    </table>    

  	<Div  id= "divPolGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	
      <Div  id= "divPage" align=center style= "display: 'none' ">
      <input CLASS=cssButton VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
      <input CLASS=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
      <input CLASS=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      <input CLASS=cssButton VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
      </Div>
      </Div>
      <table>
    	<tr>
    		<td class= titleImg>
    			<!--INPUT VALUE=" 合并客户 " class= cssButton TYPE=button onclick="customerUnion();"-->
    			<input type= "hidden" name= "ProposalNo" value= "">
    			<input type= "hidden" name= "Flag" value= "">
    			<INPUT type= "hidden" name= "InsuredNo" value= "">    			
    		</td>
    	</tr>
      </table> 	
      
<Div  id= "divCustomerUnion" style= "display: '' "> 
	  <table>
	    	<tr>
	        <td class=common>
	          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCustomerUnionInfo);">
	    	</td>
	    	<td class= titleImg>
	    	 合并客户信息 
	    	</td>
	    </tr>
	    </table>        
	<div id= "divCustomerUnionInfo" style= "display: ''" >
	 <table class= common>
    	<TR  class= common>
          <TD class = title> 客户姓名 </td>
          <TD class= input><input class=readonly  name=CustomerName readonly ></td>    	
          <TD class= title> 原客户号码 </td>
          <TD class= input><input class= readonly  name=CustomerNo_OLD readonly ></td>
          <TD class= title> 合并后客户号码 </td>
          <TD class= input><input class= readonly  name=CustomerNo_NEW readonly ></td>
        </tr>
        <tr>
          <td class="title">合并方向</td>
          <td class="input">
			      <input type="radio" name="exchangeRadio" checked  value="1"  OnClick="exchangeCustomerNo();" >正向 
			      <input type="radio" name="exchangeRadio"  value="-1" OnClick="exchangeCustomerNo();">反向          
		      </td>
        </tr>
      </table>		
	</div>
	<div id= "divCustomerUnionIssue" style= "display: 'none'" >
		<table>
			<tr>
		        <td class=common>
		          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCustomerUnionIssueInfo);">
		    	</td>
		    	<td class= titleImg>
		    	 合并客户问题件信息 
		    	</td>
		    </tr>
		</table> 	
	
	<div id= "divCustomerUnionIssueInfo" style= "display: ''" >	
		<table width="121%" height="37%" class= common>
		    <TR  class= common> 
		      <TD width="100%" height="13%"  class= title> 问题件内容 </td>
		    </TR>
		    <TR  class= common>
		      <TD height="87%"  class= title><textarea name="CUIContent" cols="100" rows="10" class="common" >本次投保申请中*******，但您在我公司投保的其他保单中********，请您提供您的身份证复印件，以便我公司进行核对</textarea></td>
		    </TR>
		</table>      	
	</div>
	</div>
	<div id="divBtCustomerUnion" style= "display: ''" >	
	
		<input value="填写通知书" id="button2" class = cssButton TYPE = button onclick="sendCustomerUnionIssue();">
		<input value="保存" id="button4" class = cssButton TYPE = button onclick="sendCustomerUnionIssue1();" disabled="disabled">
		<input value="发送客户合并通知书" id="button5" class = cssButton TYPE = button onclick="sendCustomerUnionIssue2();" disabled="disabled">
    <input value="     返  回       " id="button3" class="cssButton" type="button" onclick="top.close();">
	</div>

	<div id= "divBtCustomerUnionSendIssue" style= "display: 'none'" >	
		<P>
			<input value="确认合并客户" id="button1"  class = cssButton TYPE = button onclick="customerUnionSubmit();"> 
		<!--input value="发出问题件" class = cssButton TYPE = button onclick="showCustomerUnionIssueSend();"-->
		<!--input value="  取  消  " class = cssButton TYPE = button onclick="showCustomerUnion();"-->
	 </P>
	
	</div>
</DIV>      
      
    </DIV>  
        <DIV id = "divSureButton" style = "display:'none'" >   
			<!--隐藏域-->
			<input name ="SureFlag" type="hidden">     				
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
