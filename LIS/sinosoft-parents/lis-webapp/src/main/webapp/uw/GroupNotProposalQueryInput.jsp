<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：GroupNotProposalQueryInput.jsp
//程序功能：未承保查询
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
	String tAppntNo = request.getParameter("AppntNo");

	
	
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("GroupNotProposalQueryInput","operator:"+tGI.Operator);
%>
<script>
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
  var AppntNo = "<%=tAppntNo%>"; //客户号
</script>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>未承保查询</title>

  <script src="../common/javascript/Common.js" ></SCRIPT>
  <script src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <script src="GroupNotProposalQuery.js"></SCRIPT>
  
  
  <%@include file="GroupNotProposalQueryInit.jsp"%>
  
</head>
<body  onload="initForm();" >
  
  
  <form method="post" name="fm" id="fm" target="fraSubmit">
	<div class="maxbox1">
    <table class="common">
      <tr class="common">
        <td class="title5">
         单位编码
        </td>
        <td class="input5">
          <input class="common wid" name="AppntNo" id="AppntNo" readonly>
        </td>
        <td class="title5">
          单位名称
        </td>
        <td class="input5">
          <input class="common wid" name="GrpName" id="GrpName" readonly>
         <input type="hidden" id="GrpContNo" name= "GrpContNo" value= "">
        </td>
      </tr>
    </table>
</div>
<!--分类保额累计-->

    <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCont);">
	    	</td>
	    	<td class= titleImg>
	    	 未承保保单 
	    	</td>
	    </tr>
	  </table>    
	  <div id= "divCont" style= "display: ''" >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  				  <span id="spanContGrid" >
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


<!--险种信息-->
    <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol);">
	    	</td>
	    	<td class= titleImg>
	    	 未承保保单险种信息 
	    	</td>
	    </tr>
	  </table>    
	  <div id= "divPol" style= "display: ''" >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  				  <span id="spanPolGrid" >
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



    <div id="Button" style="display:">
      <input class="cssButton" id="button1" name="button1" value="保单详细信息查询"  type="button" onclick="getContDetailInfo();">
      <input class="cssButton" id="button2" name="button2" value="  影像资料查询  "  type="button" onclick="showImage();">
      <input class="cssButton" id="button3" name="button3" value="  保单交费查询  "  type="button" onclick="showTempFee();">
     <input class="cssButton" id="button5" name="button5" value="  操作履历查询  "  type="button" onclick="OperationQuery();">
      <input class="cssButton" id="button4" name="button4" value="  核保结论查询  "  type="button" onclick="UWQuery();">

      <!--<input class="cssButton" value="     返  回     " type="button" onclick="returnParent();">-->
    </table>
    </div>
<br>
<a href="javascript:void(0);" class="button" onClick="returnParent();">返    回</a>
		<!--隐藏域-->
    <div id = "divHidden" style = "display:none" >   
  	</div>

  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>

</body>


</html>
