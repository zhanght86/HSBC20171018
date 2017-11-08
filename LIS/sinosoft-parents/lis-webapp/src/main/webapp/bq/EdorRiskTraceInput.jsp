<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：EdorRiskTraceInput.jsp
//程序功能：险种核保轨迹查询
//创建日期：2005-07-13 11:10:36
//创建人  ：liurx 
//更新记录：  更新人    更新日期     更新原因/内容 
%>
 
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
	String tContNo = request.getParameter("ContNo");
	String tPolNo = request.getParameter("PolNo");
	String tEdorNo = request.getParameter("EdorNo");
	
    GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");

%>
<script>
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
    var ContNo = "<%=tContNo%>"; //保单号
    var PolNo = "<%=tPolNo%>"; //险种保单号
    var EdorNo = "<%=tEdorNo%>";
</script>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>险种核保轨迹</title>

  <script src="../common/javascript/Common.js" ></SCRIPT>
  <script src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>

  <script src="EdorRiskTrace.js"></SCRIPT> 
  
  <%@include file="EdorRiskTraceInit.jsp"%> 
  
</head>
<body  onload="initForm();" >
  
  
  <form method="post" name="fm" id=fm target="fraSubmit">

    <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divRisk);">
	    	</td>
	    	<td class= titleImg>
	    	 险种核保轨迹信息 
	    	</td>
	    </tr>
	  </table>      
	  <div id= "divRisk" style= "display: ''" >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  				  <span id="spanRiskTraceGrid" >   
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

      <input class="cssButton" value=" 返  回 " type="button" onclick="returnParent();">
    </table>
    </div>

		<!--隐藏域-->
    <div id = "divHidden" style = "display:'none'" >   
  	</div>

  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>

</html>
