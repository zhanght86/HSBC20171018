<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head>
<%@page import = "com.sinosoft.lis.pubfun.*"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>

<SCRIPT src="./LPrtXmlStyleInput.js"></SCRIPT>

<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

<%@include file="./LPrtXmlStyleInit.jsp"%>

</head>

<body onload="initForm();initElementtype();">

<form action="./LPrtXmlStyleSave.jsp" method=post name=fm id="fm"
	target="">
<input type=hidden id="fmtransact" name="fmtransact">
<table>
	<tr>
		<td class=common><img src="../common/images/butExpand.gif" style="cursor:hand"
			onclick="showPage(this,divTemplete1);"></td>
		<td class=titleImg>ģ���ѯ��</td>
	</tr>
</table>
<div class="maxbox1">
<div id="divTemplete1" name=divTemplete1 style="display: '' ">
<table class=common>
	<tr class=common>
	    <td class=title5>��ӡ����</td>
		<td class=input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=PrintID id="PrintID" verify="��ӡ����|notnull"
			onclick="return showCodeList('printid',[this,PrintName],[0,1],null,null,null,1);" ondblclick="return showCodeList('printid',[this,PrintName],[0,1],null,null,null,1);"
			onkeyup="return showCodeLisKey('printid',[this,PrintName],[0,1],null,null,null,1);"><input class=codename name=PrintName id="PrintName" elementtype=nacessary readOnly=true>
		</td>
	   <td class=title5>ģ������</td>
		<td class=input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=TempleteType id="TempleteType"
			onclick="return showCodeList('templetetype',[this,TempleteTypeName],[0,1]);" ondblclick="return showCodeList('templetetype',[this,TempleteTypeName],[0,1]);"
			onkeyup="return showCodeListKey('templetetype',[this,TempleteTypeName],[0,1]);"><input class=codename name=TempleteTypeName id="TempleteTypeName" readOnly=true >
		</td>  
		<Input  name=TempleteID id="TempleteID" type = hidden>
	</tr>
</table>
</div>
</div>
<!--<a href="javascript:void(0)" class=button onclick="easyQueryClick();">��  ѯ</a>
<a href="javascript:void(0)" class=button onclick="return exportFile();">��  ��</a>-->
<input value="��  ѯ" type="button" class=cssButton onclick="easyQueryClick();">
<input value="��  ��"  type=button class=cssButton   onclick="return exportFile();">
 <table>
	    <tr>
	       <td  class=common>
	       <IMG src="../common/images/butExpand.gif" style="cursor : hand;" onclick="showPage(this,divPrtTemplete);">
	       </td>
	       <td class=titleImg>
	           ��ѯ�����
	       </td> 
	    </tr>
	</table>
	
	<div id="divPrtTemplete" style="display: ''">
	  <table class=common>
           <tr class=common>
              <td text-align:left colSpan=1>
              <span id="spanLPrtXmlStyleGrid"></span>
              </td>
           </tr>	     
	  </table>
	</div>  
	
	<div  align=center style="display: none">
	   <input class=cssButton90 value="��  ҳ" type=button onclick="turnPage.firstPage();"> 
	   <input class=cssButton91 value="��һҳ" type=button onclick="turnPage.previousPage();"> 
	   <input class=cssButton92 value="��һҳ" type=button onclick="turnPage.nextPage();"> 
	   <input class=cssButton93 value="β  ҳ" type=button onclick="turnPage.lastPage();"> 
	</div>
</form>
<br>
<br>
<br>
<br>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
