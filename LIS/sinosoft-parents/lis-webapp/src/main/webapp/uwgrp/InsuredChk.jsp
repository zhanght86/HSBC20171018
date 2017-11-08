<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：InsuredChk.jsp
//程序功能：被保人查重
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
	String tInsuredNo = request.getParameter("InsuredNo");	
	//loggerDebug("InsuredChk","proposalno:"+tPolNo);
	String tLoadFlag = request.getParameter("LoadFlag");
        GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("InsuredChk","operator:"+tGI.Operator);
%>

<script>
	var grpPolNo = "<%=tGrpPolNo%>";      //个人单的查询条件.
	var contNo = "<%=tContNo%>";          //个人单的查询条件.
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var tContNo = "<%=tContNo%>";
	var tFlag = "<%=tFlag%>";
	var tInsuredNo= "<%=tInsuredNo%>";
	var LoadFlag = "<%=tLoadFlag%>";
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
  
  <title>被保人校验</title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm target="fraTitle">
    <!-- 保单信息部分 -->
        <table class= common>
        	<tr class= common>
        		原被保人信息
        	</tr>
        </table>
        <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanOPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>

        <table class= common>
        	<tr class= common>
        		相同被保人信息
        	</tr>
        </table>
  	<Div  id= "divLCPol1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	
      <Div  id= "divPage" align=center style= "display: 'none' ">
      <INPUT CLASS=common VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=common VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=common VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=common VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
      </Div>
      <table>
    	<tr>
    		<td class= titleImg>
    			<INPUT VALUE="确认更新被保人" class= cssButton TYPE=button onclick="sure();">
    			<INPUT value="客户合并录入" class = cssButton TYPE = button onclick="unitPrint();">
    			<INPUT type= "hidden" name= "ProposalNo" value= "">
    			<INPUT type= "hidden" name= "InsuredNo" value= "">    			
    			<INPUT type= "hidden" name= "Flag" value= "">
    		</td>
    	</tr>
      </table> 	 	
      				
    <table>
		<tr>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
			</td>
			<td class= titleImg>问题件信息</td>
		</tr>
	</table>
	 <table>
    	<TR  class= common>
	
          <TD id = divcustomernoname style = "display:none" class = title>
          	原客户号码
          </TD>
          <TD  id = divcustomerno style = "display:none" class= input>
            <Input class=common  name=CustomerNo >
          </TD>
          <TD id=divnamename style = "display:none" class = title>
          	原客户姓名
          </TD>
          <TD  id = divname style = "display:none" class= input>
            <Input class=common  name=CustomerName >
          </TD>
          <TD id = divcurrentnoname style = "display:none" class = title>
          	相同客户号码
          </TD>
          <TD  id = divcurrentno style = "display:none" class= input>
            <Input class=common  name=CurrentCustomerNo >
          </TD>
           
        </tr>
      </table>
         <table width="121%" height="37%">
    <TR  class= common> 
    	 
      <TD id = divcontent style = "display:none" width="100%" height="13%"  class= title> 问题件内容 </TD>
    </TR>
    <TR  class= common>
      <TD id = divcontentname style = "display:none" height="87%"  class= title><textarea name="Content" cols="135" rows="10" class="common"></textarea></TD>
    </TR>
  </table>
    
        <DIV id = "divSureButton" style = "display:'none'" >   
     	<INPUT  value="确  定" class = cssButton TYPE = button onclick="makesure();"> 				
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>

