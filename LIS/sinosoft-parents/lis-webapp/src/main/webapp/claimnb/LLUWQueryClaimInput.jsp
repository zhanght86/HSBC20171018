<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：LLUWQueryClaimInput.jsp
//程序功能：二核投保人和被保人赔案查询
//创建日期：2005-12-03 
//创建人  ：万泽辉
%>
<html>
<%
	String tClmNo      = "";  //赔案号
	String tTransferNo = "";  //投保人或者被保人号码
	String tFlag       = "";  //标识

	tClmNo      = request.getParameter("ClmNo");
	tTransferNo = request.getParameter("transferNo");
	tFlag       = request.getParameter("Flag");
    
    GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var operator   = "<%=tGI.Operator%>";     //记录操作员
	var manageCom  = "<%=tGI.ManageCom%>";    //记录登陆机构
    var ClmNo      = "<%=tClmNo%>";           //赔案号
    var transferNo = "<%=tTransferNo%>";      //投保人或者被保人号码
    var Flag       = "<%=tFlag%>";            //标识
</script>

<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <script src="../common/javascript/VerifyInput.js"></script>
  <SCRIPT src="LLUWQueryClaim.js"></SCRIPT>
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="LLUWQueryClaimInit.jsp"%>
</head>
<body  onload="initForm();" >
  
  
  <form method="post" name="fm" id="fm" target="fraSubmit">
<br>
    <table class="common">
      <tr class="common">
        <td class="title5"> 客户号 </td>
        <td class="input5">
          <input class="common wid" name="CustomerNo" id="CustomerNo" readonly>
        </td>
        <td class="title5"> 客户姓名</td>
        <td class="input5">
          <input class="common wid" name="CustomerName" id="CustomerName" readonly>
        </td>
      </tr>
    </table>

<!--分类保额累计-->
    <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divClaim);">
	    	</td>
	    	<td class= titleImg> 理赔信息 </td>
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
      <table class=common>
    	<tr class=common>
    	  <td class=common>
            <input class=cssButton90 VALUE="首  页" TYPE=button id="buttona" onClick="turnPage.firstPage();"> 
            <input class=cssButton91 VALUE="上一页" TYPE=button id="buttonb" onClick="turnPage.previousPage();"> 					
            <input class=cssButton92 VALUE="下一页" TYPE=button id="buttonc" onClick="turnPage.nextPage();"> 
            <input class=cssButton93 VALUE="尾  页" TYPE=button id="buttond" onClick="turnPage.lastPage();">
          </td>
        </tr>
     </table>
    </div>
   </div>	


<!--险种信息-->
    <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol);">
	    	</td>
	    	<td class= titleImg>既往赔案险种信息 </td>
	    </tr>
	  </table>    
	  <div id= "divPol" style= "display: ''" >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  			<span id="spanPolGrid" ></span> 
          </td>
  		</tr>
      </table>
   </div>	
   <div id="Button" style="display:">
   <hr class="line">
   <table>
   <tr>
   <td>
      <INPUT VALUE="既往理赔详细查询" class=cssButton TYPE=button onClick="showDetail();">
      <INPUT VALUE="  影像资料查询  " class=cssButton TYPE=button onClick="showImage();"> 
      <INPUT VALUE="  理赔核保查询  " class=cssButton TYPE=button id="button1"  onclick="showClaimSecond();">
	  <Input Value ="返    回" class=cssButton Type=button onClick="top.close();"> 
   </td>
   </tr>
   </table>
    </div>

		<!--隐藏域-->
    <div id = "divHidden" style = "display:'none'" >   
  	</div>

  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

</body>


</html>

 
