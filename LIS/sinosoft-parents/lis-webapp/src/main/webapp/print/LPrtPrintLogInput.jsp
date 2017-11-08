<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>

<SCRIPT src="./LPrtPrintLogInput.js"></SCRIPT>

<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

<%@include file="./LPrtPrintLogInit.jsp"%>

</head>

<body onload="initForm();initElementtype();">

<form action="" method=post name=fm id="fm"
	target="fraSubmit" >
<input type=hidden id="fmtransact" name="fmtransact">
<table>
	<tr>
		<td class=common><img src="../common/images/butExpand.gif" style="cursor:hand"
			onclick="showPage(this,divTemplete1);"></td>
		<td class=titleImg>查询条件：</td>
	</tr>
</table>
<div class="maxbox1">
<div id="divTemplete1" name=divTemplete1 style="display: '' ">
<table class=common>
	<tr class=common>
	    <td class=title5>打印名称</td>
		<td class=input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=PrintID id="PrintID" verify="打印名称|notnull"
			onclick="return showCodeList('printid',[this,PrintName],[0,1],null,null,null,1);" ondblclick="return showCodeList('printid',[this,PrintName],[0,1],null,null,null,1);"
			onkeyup="return showCodeLisKey('printid',[this,PrintName],[0,1],null,null,null,1);"><input class=codename name=PrintName id="PrintName" elementtype=nacessary readOnly=true><FONT id="PrintNamen" color=red></FONT>
		</td>
	</tr>
	<tr class=common>
		<td class=title5>统计起期</td>
		<td class=input5><Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="统计起期|NOTNULL" elementtype=nacessary dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><FONT id="StartDaten" color=red></FONT></td>
	    <td class=title5>统计止期</td>
		<td class=input5><Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="统计止期|NOTNULL" elementtype=nacessary dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><FONT id="EndDaten" color=red></FONT></td>
	</tr>
</table>
</div>
</div>
<a href="javascript:void(0)" class=button onclick="easyQueryClick();">查  询</a>
<!-- <input value="查  询" type="button" class=cssButton onclick="easyQueryClick();">> -->
 <table>
	    <tr>
	       <td  class=common>
	       <IMG src="../common/images/butExpand.gif" style="cursor : hand;" onclick="showPage(this,divLPrtPrintLog);">
	       </td>
	       <td class=titleImg>
	           查询结果：
	       </td> 
	    </tr>
	</table>
	
	<div id="divLPrtPrintLog" style="display: ''">
	  <table class=common>
           <tr class=common>
              <td text-align:left colSpan=1>
              <span id="spanLPrtPrintLogGrid"></span>
              </td>
           </tr>	     
	  </table>
	</div>  
	<div id="divPage" align=center style="display: ''">
	   <input class=cssButton90 value="首  页" type=button onclick="turnPage.firstPage();"> 
	   <input class=cssButton91 value="上一页" type=button onclick="turnPage.previousPage();"> 
	   <input class=cssButton92 value="下一页" type=button onclick="turnPage.nextPage();"> 
	   <input class=cssButton93 value="尾  页" type=button onclick="turnPage.lastPage();"> 
	</div>
</form>
<br>
<br>
<br>
<br>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
