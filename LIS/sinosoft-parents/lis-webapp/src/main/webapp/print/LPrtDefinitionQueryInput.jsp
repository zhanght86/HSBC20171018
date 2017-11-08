<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page  contentType="text/html; charset=gbk"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>

<script  src="./LPrtDefinitionQueryInput.js"></script>

<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

<%@include file="./LPrtDefinitionQueryInit.jsp"%>
<title>查询</title>
</head>
<body onload="initForm();initElementtype();">
<form action=" " method=post name=fm id="fm" target=fraSubmit>
	<table>
		<tr>
			<td class=common><img src="../common/images/butExpand.gif" style="cursor:hand"
				onclick="showPage(this,divPrint1);"></td>
			<td class=titleImg>查询条件：</td>
		</tr>
	</table>
	<input class=common type=hidden name=PrintID id="PrintID" readOnly=true >
	<div class="maxbox1">
	<div id="divPrint1" name=divPrint1 style="display: '' ">
	<table class=common>
		<tr class=common>
		    <td class=title5>打印名称</td>
			<td class=input5><Input class="common wid" name=PrintName id="PrintName" elementtype=nacessary>
			</td>
			<td class=title5>打印对象</td>
			<td class=input5><input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=PrintObject id="PrintObject" 
				onclick="return showCodeList('printobject',[this,PrintObjectName],[0,1]);" ondblclick="return showCodeList('printobject',[this,PrintObjectName],[0,1]);"
				onkeyup="return showCodeListKey('printobject',[this,PrintObjectName],[0,1]);"><input class=codename name=PrintObjectName id="PrintObjectName" readOnly=true >
			</td> 
		</tr>
		<tr class=common>
			<td class=title5>打印类型</td>
			<td class=input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=PrintType  id="PrintType"
				onclick="return showCodeList('printtypelist',[this,PrintTypeName],[0,1]);" ondblclick="return showCodeList('printtypelist',[this,PrintTypeName],[0,1]);"
				onkeyup="return showCodeListKey('printtypelist',[this,PrintTypeName],[0,1]);"><input class=codename name=PrintTypeName id="PrintTypeName" readOnly=true >
			</td>
		</tr>
	</table>	
	</div>
	</div>
	<a href="javascript:void(0)" class=button onclick="easyQueryClick();">查　询</a>
    <!-- <input value="查　询" type="button" class=cssButton onclick="easyQueryClick();">
    <input value="返　回" type="button" class=cssButton onclick="returnParent();"> -->
	<table>
	    <tr>
	       <td  class=common>
	       <IMG src="../common/images/butExpand.gif" style="cursor : hand;" onclick="showPage(this,divPrtDefinition);">
	       </td>
	       <td class=titleImg>查询结果:</td> 
	    </tr>
	</table>
	<div id="divPrtDefinition" style="display: ''">
	  <table class=common>
           <tr class=common>
              <td text-align:left colSpan=1>
              <span id="spanLPrtDefinitionGrid"></span>
              </td>
           </tr>	     
	  </table>
	</div>  
	<div id="divPage" align=center style="displayl: ''">
	   <input class=cssButton90 value="首　页" type=button onclick="getFirstPage();"> 
	   <input class=cssButton91 value="上一页" type=button onclick="getPreviousPage();"> 
	   <input class=cssButton92 value="下一页" type=button onclick="getNextPage();"> 
	   <input class=cssButton93 value="尾　页" type=button onclick="getLastPage();"> 
	</div>
	<input type=hidden id="fmtransact" name="fmtransact">
	<a href="javascript:void(0)" class=button onclick="returnParent();">返　回</a>
</form>
<br>
<br>
<br>
<br>
	<span id="spanCode" style="display: none; position: absolute; slategray"></span>
</body>
</html>
