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
    String tMissionID = request.getParameter("MissionID");
    String tSubMissionID = request.getParameter("SubMissionID");
    String tActivityID = request.getParameter("ActivityID");
    
%>
<script>
	var grpPolNo = "<%=tGrpPolNo%>";      //个人单的查询条件.
	var contNo = "<%=tContNo%>";          //个人单的查询条件.
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var tContNo = "<%=tContNo%>";
	var tFlag = "<%=tFlag%>";
	var LoadFlag = "<%=tLoadFlag%>";
	var tInsuredNo = "<%=tInsuredNo%>";
    var tMissionID = "<%=tMissionID%>";
    var tSubMissionID = "<%=tSubMissionID%>";
    var tActivityID = "<%=tActivityID%>";

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
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <%@include file="AppntChkInit.jsp"%>
  
  <title>重复客户校验</title>
</head>
<body  onload="initForm();" >
  <form method=post id="fm" name=fm target="fraTitle">
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
    	
      <Div  id= "divPage" align=center style= "display: none ">
      <input CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
      <input CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
      <input CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      <input CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
      </Div>
      </Div>
      <table>
    	<tr>
    		<td class= titleImg>
    			<!--INPUT VALUE=" 合并客户 " class= cssButton TYPE=button onclick="customerUnion();"-->
    			<input type= "hidden" id="ProposalNo" name= "ProposalNo" value= "">
    			<input type= "hidden" id="Flag" name= "Flag" value= "">
    			<INPUT type= "hidden" id="InsuredNo" name= "InsuredNo" value= "">    			
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
	<div id= "divCustomerUnionInfo" class="maxbox1" style= "display: ''" >
	 <table class= common>
    	<TR  class= common>
          <TD class= title5> 原客户号码 </td>
          <TD class= input5><input class= "readonly wid" id="CustomerNo_OLD" name=CustomerNo_OLD readonly ></td>
          <TD class= title5> 合并后客户号码 </td>
          <TD class= input5><input class= "readonly wid" id="CustomerNo_NEW"  name=CustomerNo_NEW readonly ></td>
        </tr>
        <tr>
          <td class="title">合并方向</td>
          <td class="input">
			      <input type="radio" id="exchangeRadio" name="exchangeRadio" checked  value="1"  OnClick="exchangeCustomerNo();" >正向 
			      <input type="radio" id="exchangeRadio" name="exchangeRadio"  value="-1" OnClick="exchangeCustomerNo();">反向          
		      </td>
        </tr>
      </table>		
	</div>
	<div id= "divCustomerUnionIssue" style= "display: none" >
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
	
	<div id= "divCustomerUnionIssueInfo" class="maxbox1" style= "display: ''" >	
		<table width="121%" height="37%" class= common>
		    <TR  class= common> 
		      <TD width="100%" height="13%"  class= title> 问题件内容 </td>
		    </TR>
		    <TR  class= common>
		      <TD height="87%"  class= title><textarea id="CUIContent" name="CUIContent" cols="135" rows="10" class="common"></textarea></td>
		    </TR>
		</table>      	
	</div>
	</div>
	<div id="divBtCustomerUnion" style= "display: ''" >	
	<input value="发出问题件" class = cssButton TYPE = button onclick="showCustomerUnionIssueSend();">
	
    <input value="     返  回       " id="button3" class="cssButton" type="button" onclick="top.close();">
	</div>

	<div id= "divBtCustomerUnionSendIssue" style= "display: none" >	
		<P>
			<input value="确认合并客户" id="button1"  class = cssButton TYPE = button onclick="customerUnionSubmit();"> 
		
		<!--input value="  取  消  " class = cssButton TYPE = button onclick="showCustomerUnion();"-->
	 </P>
	</div>
		<div id = "divSendNotice" style= "display: none">
				<input value="发送客户合并通知书" id="button2" class = cssButton TYPE = button onclick="sendCustomerUnionIssue();">
	</div>
</DIV>      
      
    </DIV>  
        <DIV id = "divSureButton" style = "display:none" >   
			<!--隐藏域-->
			<input id="SureFlag" name ="SureFlag" type="hidden">     	
			<input id="LoadFlag" name = "LoadFlag" type = "hidden">
            <input type= "hidden" id="MissionID" name= "MissionID" value= "">
            <input type= "hidden" id="SubMissionID" name= "SubMissionID" value= "">
            <input type= "hidden" id="ActivityID" name= "ActivityID" value= "">

      </div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
