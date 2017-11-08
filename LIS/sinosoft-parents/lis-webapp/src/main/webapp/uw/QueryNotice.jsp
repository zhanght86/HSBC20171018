<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：QueryNotice.jsp
//程序功能：通知书查询
//创建日期：2006-11-17 17:05
//创建人  ：haopan
//更新记录：  更新人    更新日期     更新原因/内容 
%> 
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<html>
	<%
	String tGrpContNo = request.getParameter("GrpContNo");
	//loggerDebug("QueryNotice","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@tGrpContNo"+tGrpContNo);
	%>
	<head>
		<script>
	
  var GrpContNo = "<%=tGrpContNo%>"; //投保单号  
</script>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>通知书查询</title>

  <script src="../common/javascript/Common.js" ></SCRIPT>
  <script src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
 <script src="QueryNotice.js"></SCRIPT> 
  
  <%@include file="QueryNoticeInit.jsp"%> 
    
</head>
  
  <body onload="initForm();">
  	
  	<form method="post" id="fm" name="fm" target="">
  		
  	 <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divnotice);">
	    	</td>
	    	<td class= titleImg>
	    	 已发放通知书信息
	    	</td>
	    </tr>
	  </table> 
	    
	  <div id= "divnotice" style= "display: ''" >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  				  <span id="spanNoticeGrid" >   
  					</span>  
          </td>
  			</tr>
    	</table>
        <div  id= "divTurnPage" align=center style= "display: '' ">
          <input class=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
          <input class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
          <input class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
          <input class=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
        </div>
    </div>	
    <input class="cssButton" value="  返  回  " type="button" onclick="returnParent();">
    </table>
  </form>
  </body>
</html>
