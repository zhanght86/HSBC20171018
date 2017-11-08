<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：EdorErrorUWQueryInput.jsp
//程序功能：保全初次核保查询
//创建日期：2008-7-21 19:10:36
//创建人  ：pst
//更新记录：  更新人    更新日期     更新原因/内容
%> 
<%
	String tEdorAcceptNo = "";
	tEdorAcceptNo = request.getParameter("EdorAcceptNo"); 

%>
<html>
<%
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var EdorAcceptNo = "<%=tEdorAcceptNo%>";          //个人单的查询条件.
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录管理机构
	var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
	
</script>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <script src="../common/javascript/VerifyInput.js"></script>
  <SCRIPT src="EdorErrorUWQuery.js"></SCRIPT>
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="EdorErrorUWQueryInit.jsp"%>
</head>
<body  onload="initForm();" >
 <form method=post name=fm id=fm target="fraSubmit">
  

  <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorMain);">
    		</td>
    		<td class= titleImg>
    			 保全申请初次核保信息
    		</td>
    	    </tr>
   </table>	
  <Div  id= "divEdorMain"   >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanEdorMainGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table><center>
	        <INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
            <INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage.previousPage();"> 					
            <INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
            <INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage.lastPage();"> </center> 
  </div>    					

  <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInfoGrid);">
    		  </td>
    		  <td class= titleImg>
    			 审批轨迹
    		  </td>
    	</tr>
   </table>
   <Div  id= "divInfoGrid"   >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanInfoGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table><center>
	        <INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage1.firstPage();"> 
            <INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage1.previousPage();"> 					
            <INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage1.nextPage();"> 
            <INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage1.lastPage();">  </center>
  </div>    

<br>
<INPUT VALUE="返 回" class=cssButton TYPE=button onclick="returnParent();">

	<input type=hidden id="EdorNo" 	      name= "EdorNo">


  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>
</html>
