<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ContTrace.jsp
//程序功能：合同核保轨迹查询  
//创建日期：2005-06-27 11:10:36
//创建人  ：CCVIP 
//更新记录：  更新人    更新日期     更新原因/内容 
%>
 
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
  //投保单号
	String tContNo = request.getParameter("ContNo");
	String tPolNo = request.getParameter("PolNo");
	loggerDebug("EdorContTrace","PolNo="+tPolNo);     
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("EdorContTrace","operator:"+tGI.Operator);
%>
<script>
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
  var ContNo = "<%=tContNo%>"; //投保单号
  var PolNo = "<%=tPolNo%>"; //险种保单号  
</script>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>合同核保轨迹</title> 

  <script src="../common/javascript/Common.js" ></SCRIPT>
  <script src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>

  <script src="EdorContTrace.js"></SCRIPT> 
  
  <%@include file="EdorContTraceInit.jsp"%>  
  
</head>
<body  onload="initForm();" >
  
  
  <form method="post" name="fm" target="fraSubmit">

    <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCont);">
	    	</td>
	    	<td class= titleImg>
	    	 合同核保轨迹信息 
	    	</td>
	    </tr>
	  </table>      
	  <div id= "divCont" style= "display: ''" >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  				  <span id="spanContTraceGrid" >   
  					</span>  
          </td>
  			</tr>
    	</table> 
        <div  id= "divTurnPage" align=center style= "display: '' ">
          <input class=cssButton VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
          <input class=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
          <input class=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
          <input class=cssButton VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
        </div>
    </div>	

      <input class="cssButton" value="     返  回     " type="button" onclick="returnParent();">
    </table>
    </div>

		<!--隐藏域-->
    <div id = "divHidden" style = "display:'none'" >   
  	</div>

  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

</body>

</html>
