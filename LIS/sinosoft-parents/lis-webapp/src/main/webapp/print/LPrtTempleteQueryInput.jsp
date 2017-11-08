<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page language="java" contentType="text/html; charset=gbk"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>

  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<script  src="./LPrtTempleteQueryInput.js"></script>
<%@include file="./LPrtTempleteQueryInit.jsp"%>
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
<div class="maxbox1">
<div id="divPrint1" name=divPrint1 style="display: '' ">
<input class=common name=TempleteID id="TempleteID" type=hidden readOnly=true >
<table class=common>
	<tr class=common>
	    <td class=title5>打印名称</td>
		<td class=input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=PrintID  id="PrintID" verify="打印名称|notnull"
			onclick="return showCodeList('printid',[this,TempleteName],[0,1],null,null,null,1);" ondblclick="return showCodeList('printid',[this,TempleteName],[0,1],null,null,null,1);"
			onkeyup="return showCodeLisKey('printid',[this,TempleteName],[0,1],null,null,null,1);"><input class=codename name=TempleteName id="TempleteName"  elementtype=nacessary>
		</td>
		<td class=title5>语言</td>
	    <td class=input5><input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=Language id="Language"
	        onclick="return showCodeList('language',[this,LanguageType],[0,1]);"  ondblclick="return showCodeList('language',[this,LanguageType],[0,1]);"
	        onkeyup="return showCodeListKey('language',[this,LanguageType],[0,1]);"><input class=codename name=LanguageType id="LanguageType" readOnly=true >
	    </td>
	</tr>
	<tr class=common>

	    <td class=title5>模板类型</td>
		<td class=input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=TempleteType id="TempleteType"
			onclick="return showCodeList('templetetype',[this,TempleteTypeName],[0,1]);" ondblclick="return showCodeList('templetetype',[this,TempleteTypeName],[0,1]);"
			onkeyup="return showCodeListKey('templetetype',[this,TempleteTypeName],[0,1]);"><input class=codename name=TempleteTypeName id="TempleteTypeName" readOnly=true >
		</td>
	</tr>
</table>	
</div>
</div>
<a href="javascript:void(0)" class=button onclick="easyQueryClick();">查  询</a>
<!-- <input value="查  询" type="button" class=cssButton onclick="easyQueryClick();">
<input value="返  回" type="button" class=cssButton onclick="returnParent();"> -->
	
	<table>
	    <tr>
	       <td  class=common>
	       <IMG src="../common/images/butExpand.gif" style="cursor : hand;" onclick="showPage(this,divPrtTemplete);">
	       </td>
	       <td class=titleImg>
	           查询结果：
	       </td> 
	    </tr>
	</table>
	
	<div id="divPrtTemplete" style="display: ''">
	  <table class=common>
           <tr class=common>
              <td text-align:left colSpan=1>
              <span id="spanLPrtTempleteGrid"></span>
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
	<a href="javascript:void(0)" class=button onclick="returnParent();">返  回</a>
	<input type=hidden id="fmtransact" name="fmtransact">
</form>
<br>
<br>
<br>
<br>
  <span id="spanCode" style="display: none; position: absolute; slategray"></span>
</body>
</html>
