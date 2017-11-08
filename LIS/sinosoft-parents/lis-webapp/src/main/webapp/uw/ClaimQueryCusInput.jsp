<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ClaimQueryInput.jsp
//程序功能：既往理赔查询
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
	String tContNo = request.getParameter("ContNo");
	String tCustomerNo = request.getParameter("CustomerNo");
	//09-11-11新增标记，只有保单核保查询界面才会传入ContFlag并且值为1
	//如果ContFlag==1的话则只按contno查
	String tContFlag = request.getParameter("ContFlag");
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("ClaimQueryCusInput","operator:"+tGI.Operator);
%>
<script>
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
  var contNo = "<%=tContNo%>"; //号
  var customerNo = "<%=tCustomerNo%>"; //客户号
  var contFlag = "<%=tContFlag%>"
</script>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>既往理赔查询</title>

  <script src="../common/javascript/Common.js" ></SCRIPT>
  <script src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>

  <script src="ClaimQueryCus.js"></SCRIPT>
  
  
  <%@include file="ClaimQueryInit.jsp"%>
  
</head>
<body  onload="initForm();" >
  
  
  <form method="post" name="fm" id="fm" target="fraSubmit">
<div id= "divCustomer" style= "display: none" >
    <table class="common">
      <tr class="common">
        <td class="title5">
          客户号
        </td>
        <td class="input5">
          <input class="common wid" name="CustomerNo" id="CustomerNo" readonly>
        </td>
        <td class="title5">
          客户姓名
        </td>
        <td class="input5">
          <input class="common wid" name="CustomerName" id="CustomerName" readonly>
        </td>
      </tr>
    </table>
</div>
<!--分类保额累计-->

    <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divClaim);">
	    	</td>
	    	<td class= titleImg>
	    	 理赔信息 
	    	</td>
	    </tr>
	  </table>    
	  <div id= "divClaim" style= "display: ''" >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  				  <span id="spanClaimGrid" >
  					</span> 
          </td>
  			</tr>
    	</table>
        <div  id= "divTurnPage" align=center style= "display: '' ">
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
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol);">
	    	</td>
	    	<td class= titleImg>
	    	 既往赔案险种信息 
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
          <input class=cssButton90 VALUE="首  页" TYPE=button onClick="turnPage2.firstPage();"> 
          <input class=cssButton91 VALUE="上一页" TYPE=button onClick="turnPage2.previousPage();"> 					
          <input class=cssButton92 VALUE="下一页" TYPE=button onClick="turnPage2.nextPage();"> 
          <input class=cssButton93 VALUE="尾  页" TYPE=button onClick="turnPage2.lastPage();">
        </div>
    </div>	


    <div id="Button" style="display:">
   <hr class="line">
   <table>
   <tr>
   <td>
      <INPUT VALUE=" 既往理赔详细查询 " class=cssButton TYPE=button onClick="showDetail();">
      <INPUT VALUE=" 影像资料查询 " class=cssButton TYPE=button onClick="showImage();"> 
      <INPUT VALUE=" 核保照会查询 " class=cssButton TYPE=button onClick="queryClaimUW();">
   </td>
   </tr>
   </table>
    </div>

		<!--隐藏域-->
    <div id = "divHidden" style = "display:none" >   
  	</div>

  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

</body>


</html>
