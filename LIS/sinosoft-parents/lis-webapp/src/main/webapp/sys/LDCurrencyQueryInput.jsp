<%
//程序名称：LDCurrencyQueryInput.jsp
//程序功能：
//创建日期：2009-10-12 19:31:48
//创建人  ：ZhanPeng程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>

  <SCRIPT src="./LDCurrencyQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./LDCurrencyQueryInit.jsp"%>
  <title> </title>
</head>
<body  onload="initForm();" >
  <form action="./OLDCodeQuerySubmit.jsp" method=post name=fm id=fm target="fraSubmit">
  <div class="maxbox1">
  <table  class= common>
  <TR  class= common>
    <TD  class= title5>
      币种代码
    </TD>
    <TD  class= input5>
      <Input class="wid" class= common name=CurrCode id=CurrCode >
    </TD>
    <TD  class= title5>
      币种名称
    </TD>
    <TD  class= input5>
      <Input class="wid" class= common name=CurrName id=CurrName >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title5>
      有效标志
    </TD>
    <TD  class= input5>
      <Input class="wid" class= common name=Validation id=Validation >
    </TD>
    <TD  class= title5>
      代码别名
    </TD>
    <TD  class= input5>
      <Input class="wid" class= common name=CodeAlias id=CodeAlias >
    </TD>
  </TR>
</table>
</div>
          <!--<INPUT VALUE="查  询" TYPE=button class=cssButton onclick="return easyQueryClick();"> 
          <INPUT VALUE="返  回" TYPE=button class=cssButton onclick="returnParent();"> -->
          <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">查    询</a>
          		
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);">
    		</td>
    		<td class= titleImg>
    			外汇币种查询
    		</td>
    	</tr>
    </table>
  	<Div  id= "divCodeGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanCodeGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <center>
     	    <INPUT class = cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();">
     		<INPUT class = cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();">
     		<INPUT class = cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();">
     		<INPUT class = cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">	</center>				
  	</div>
     <a href="javascript:void(0);" class="button" onClick="returnParent();">返    回</a>			
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
