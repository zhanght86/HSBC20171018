<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：EdorQueryInput.jsp
//程序功能：保全查询
//创建日期：2005-6-10 14:36
//创建人  ：guomy
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
        //客户号
	String tContNo = request.getParameter("ContNo");
	
        GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("EdorQueryInput","operator:"+tGI.Operator);
%>
<script>
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
        var contNo = "<%=tContNo%>"; //客户号
</script>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css> 
  <title>保全查询</title>

  <script src="../common/javascript/Common.js" ></SCRIPT>
  <script src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>

  <script src="EdorQuery.js"></SCRIPT>
  
  
  <%@include file="EdorQueryInit.jsp"%>
  
</head>
<body  onload="initForm();" >
  
  
  <form method="post" name="fm" id="fm" target="fraSubmit">
   <div id= "divcustomer" style= "display:none" >
    <table class="common">
      <tr class="common">
        <td class="title5">
          客户号
        </td>
        <td class="input5">
          <input class="common wid" id="CustomerNo" name="CustomerNo" readonly>
        </td>
        <td class="title5">
          客户姓名
        </td>
        <td class="input5">
          <input class="common wid" id="CustomerName" name="CustomerName" readonly>
        </td>
      </tr>
    </table>   

 </div>
<!--分类保额累计-->

    <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdor);">
	    	</td>
	    	<td class= titleImg>
	    	 既往保全批单信息 
	    	</td>
	    </tr>
	  </table>    
	  <div id= "divEdor" style= "display:  " >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  				  <span id="spanEdorGrid" >
  					</span> 
          </td>
  			</tr>
    	</table>
        <div  id= "divTurnPage" align=center style= "display:   ">
          <input class=cssButton90 VALUE="首  页" TYPE=button onClick="turnPage.firstPage();"> 
          <input class=cssButton91 VALUE="上一页" TYPE=button onClick="turnPage.previousPage();"> 					
          <input class=cssButton92 VALUE="下一页" TYPE=button onClick="turnPage.nextPage();"> 
          <input class=cssButton93 VALUE="尾  页" TYPE=button onClick="turnPage.lastPage();">
        </div>
    </div>	


<!--险种信息-->
    <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorItem);">
	    	</td>
	    	<td class= titleImg>
	    	 既往保全批改项目信息 
	    	</td>
	    </tr>
	  </table>    
	  <div id= "divEdorItem" style= "display:  " >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  				  <span id="spanEdorItemGrid" >
  					</span> 
          </td>
  			</tr>
    	</table>
        <div  id= "divTurnPage" align=center style= "display:   ">
          <input class=cssButton90 VALUE="首  页" TYPE=button onClick="turnPage.firstPage();"> 
          <input class=cssButton91 VALUE="上一页" TYPE=button onClick="turnPage.previousPage();"> 					
          <input class=cssButton92 VALUE="下一页" TYPE=button onClick="turnPage.nextPage();"> 
          <input class=cssButton93 VALUE="尾  页" TYPE=button onClick="turnPage.lastPage();">
        </div>
    </div>	



    <div id="Button" style="display:">
      <input class="cssButton" id="button2" name="button2" value="  影像资料查询  " type="button" onClick="showImage();"> 
      <input class="cssButton" id="button3" name="button3" value="  保全明细查询  " type="button" onClick="BqEdorQueryClick();"> 
      <!-- <input class="cssButton" name="button4" value="保全核保照会查询" type="button" onclick="EdorUWDetailQuery()">  -->

      <input class="cssButton" value="  返  回  " type="button" onClick="returnParent();">
    </table>
    </div>

		<!--隐藏域-->
    <div id = "divHidden" style = "display:none" >   
  	</div>

  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

</body>


</html>
