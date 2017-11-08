<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：AmntAccumulateInput.jsp
//程序功能：保额累计
//创建日期：2005-06-01 11:10:36
//创建人  ：HL
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
  //客户号
	String tCustomerNo = request.getParameter("CustomerNo");
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("AmntAccumulateInput","operator:"+tGI.Operator);
%>
<script>
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
  var customerNo = "<%=tCustomerNo%>"; //客户号
</script>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>保额累计</title>

  <script src="../common/javascript/Common.js" ></SCRIPT>
  <script src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>

  <script src="AmntAccumulate.js"></SCRIPT>
  
  
  <%@include file="AmntAccumulateInit.jsp"%>
  
</head>
<body  onload="initForm();" >
  
  
  <form method="post" name="fm" target="fraSubmit">

    <table class="common">
      <tr class="common">
        <td class="title">
          客户号
        </td>
        <td class="input">
          <input class="common" name="CustomerNo" readonly="readonly">
        </td>
        <td class="title">
          客户姓名
        </td>
        <td class="input">
          <input class="common" name="CustomerName" readonly="readonly">
        </td>
      </tr>
    </table>

<!--分类保额累计-->

    <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAmntAccu);">
	    	</td>
	    	<td class= titleImg>
	    	 分类保额累计 
	    	</td>
	    </tr>
	  </table>    
	  <div id= "divAmntAccuGrid" style= "display: ''" >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  				  <span id="spanAmntAccuGrid" >
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

<!--保额累计明细-->

	  <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAmntAccuDetail);">
	    	</td>
	    	<td class= titleImg>
	    	  保额累计明细
	    	</td>
	    </tr>
	  </table>    

  	<div  id= "divAmntAccuDetail" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanAmntAccuDetailGrid" >
  					</span> 
  			  	</td>
  			  </tr>
    	  </table>
    	
        <div  id= "divPage2" align=center style= "display: '' ">
          <input class=cssButton VALUE="首  页" TYPE=button onclick="turnPage2.firstPage();"> 
          <input class=cssButton VALUE="上一页" TYPE=button onclick="turnPage2.previousPage();"> 					
          <input class=cssButton VALUE="下一页" TYPE=button onclick="turnPage2.nextPage();"> 
          <input class=cssButton VALUE="尾  页" TYPE=button onclick="turnPage2.lastPage();">
        </div>
    </div>  

    <div id="Button" style="display:">
      <input class="cssButton" value="返回" type="button" onclick="returnParent();">
    </div>

		<!--隐藏域-->
    <div id = "divHidden" style = "display:'none'" >   
  	</div>

  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

</body>


</html>
