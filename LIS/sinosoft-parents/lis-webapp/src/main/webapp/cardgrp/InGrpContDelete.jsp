<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<html>
<%
  //集体下个人
	String tGrpContNo = request.getParameter("GrpContNo");
	
%>
<script>
	var GrpContNo = "<%=tGrpContNo%>";          //个人单的查询条件.
</script>

<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="InGrpContDelete.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="InGrpContDeleteInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form method=post action="./InGrpContDeleteDisposal.jsp" name=fm target="fraSubmit">
  
    <table class= common border=0 width=100%>
    	<tr>
		     <td class= titleImg align= center>请输入查询条件：</td>
	    </tr>
    </table>
    
    <table  class= common align=center>
        <TR  class= common>
          <TD  class= title>
            个人合同号
          </TD>
          <TD  class= input>
            <Input class=common name=ContNo>
          </TD>
          <TD  class= title>
            被保险人客户号
          </TD>
          <TD  class= input>
            <Input class=common name=InsuredNo>
          </TD>
        </TR>
    </table>
    
    <INPUT VALUE="查  询" Class="cssButton" TYPE=button onclick="querygCont();">
    
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 投保单查询结果
    		</td>
    	</tr>
    </table>
    
  	<Div  id= "divLCPol1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	   <td text-align: left colSpan=1 >
  					<span id="spanGrpGrid">
  					</span> 
  			   </td>
  			</tr>
    	</table>
      	<table  class= common>
      		<INPUT VALUE="首  页" Class="cssButton" TYPE=button onclick="getFirstPage();"> 
      		<INPUT VALUE="上一页" Class="cssButton" TYPE=button onclick="getPreviousPage();"> 					
      		<INPUT VALUE="下一页" Class="cssButton" TYPE=button onclick="getNextPage();"> 
      		<INPUT VALUE="尾  页" Class="cssButton" TYPE=button onclick="getLastPage();">    	
    	</table>
    	<br>
    	<br>
      <table  class= common>
            <INPUT VALUE="删  除" Class="cssButton" TYPE=button onclick="deleteCont();"> 
    	</table>
	</Div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
