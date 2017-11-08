<%
//程序名称：LDExOtherRateQueryInput.jsp
//程序功能：
//创建日期：2009-10-13 10:57:48
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
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="./LDExOtherRateQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./LDExOtherRateQueryInit.jsp"%>
  <title> </title>
</head>
<body  onload="initForm();" >
  <form  name=fm id=fm target="fraSubmit">
  <div class="maxbox">
  <table  class= common>
  <TR  class= common>
    <TD  class= title5>                                                                                                                                                                                                                                               
      外汇币种代码
    </TD>
    <TD  class= input5>
      <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class='codeno' name=CurrCode id=CurrCode ondblclick="return showCodeList('currcode',[this,CurrCodeName],[0,1]);"  onclick="return showCodeList('currcode',[this,CurrCodeName],[0,1]);" 
      onkeyup="return showCodeListKey('currcode',[this,CurrCodeName],[0,1]);"  verify="外汇币种代码|notnull&currcode"><input name=CurrCodeName id=CurrCodeName  class=codename elementtype=nacessary  readonly=true>
    </TD>
    <TD  class= title5>
      外币数额单位
    </TD>
    <TD  class= input5>
      <Input class="wid" class= common class="wid" name=Per id=Per >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title5>
      目标币种代码
    </TD>
    <TD  class= input5>
    <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class='codeno' name=DestCurrCode id=DestCurrCode ondblclick="return showCodeList('currcode',[this,DestCodeName],[0,1]);"
     onclick="return showCodeList('currcode',[this,DestCodeName],[0,1]);" 
      onkeyup="return showCodeListKey('currcode',[this,DestCodeName],[0,1]);"  verify="目标币种代码|notnull&currcode"><input name=DestCodeName id=DestCodeName  class=codename elementtype=nacessary  readonly=true>
    </TD>
    <TD  class= title5>
      对美元的折算率
    </TD>
    <TD  class= input5>
      <Input class="wid" class= common class="wid" name=ExchRate id=ExchRate >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title5>
      启用日期
    </TD>
    <TD  class= input5>
      <!--<Input class="coolDatePicker" dateFormat="short" name=StartDate >-->
      <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="有效开始日期|DATE" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    </TD>
    <TD  class= title5>
      停用日期
    </TD>
    <TD  class= input5>
      <!--<Input class="coolDatePicker" dateFormat="short" name=EndDate >-->
      <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="有效开始日期|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    </TD>
  </TR>
</table>
</div>
          <!--<INPUT VALUE="查  询" TYPE=button class=cssButton onclick="return easyQueryClick();"> 
          <INPUT VALUE="返  回" TYPE=button class=cssButton onclick="returnParent();"> 	-->
          <a href="javascript:void(0);" class="button" onClick="return easyQueryClick();">查    询</a>
          				
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);">
    		</td>
    		<td class= titleImg>
    			外汇币种对美元折算率查询
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
     		<INPUT class = cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();"></center>
  								
  	</div>
    <a href="javascript:void(0);" class="button" onClick="returnParent();">返    回</a>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
