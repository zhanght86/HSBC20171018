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

<SCRIPT src="./LPrtTemApproveInput.js"></SCRIPT>

<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

<%@include file="./LPrtTemApproveInit.jsp"%>

</head>

<body onload="initForm();initElementtype();">

<form action="./LPrtTemApproveSave.jsp" method=post name=fm id="fm" target="fraSubmit" >
<input type=hidden id="fmtransact" name="fmtransact">
<table class=common>
	<tr><td class=common><img src="../common/images/butExpand.gif" style="cursor:hand"
			onclick="showPage(this,divTemplete1);"></td>
		<td class=titleImg>
			模板查询：
			</td>
	</tr>
</table>
<div class="maxbox1">
<div id="divTemplete1" name=divTemplete1 style="display: '' ">
<input type= hidden name = TempleteID id="TempleteID">
<table class=common>
	<tr class=common>
	    <td class=title5>打印名称</td>
		<td class=input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=PrintID id="PrintID"  
			onclick="return showCodeList('printid',[this,TempleteName],[0,1],null,null,null,1);" ondblclick="return showCodeList('printid',[this,TempleteName],[0,1],null,null,null,1);"
			onkeyup="return showCodeLisKey('printid',[this,TempleteName],[0,1],null,null,null,1);"><input class=codename name=TempleteName id="TempleteName" readOnly=true>
		</td>
		<td class=title5>语言</td>
	    <td class=input5><input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=Language  id="Language"
	        onclick="return showCodeList('language',[this,LanguageType],[0,1]);" ondblclick="return showCodeList('language',[this,LanguageType],[0,1]);"
	        onkeyup="return showCodeListKey('language',[this,LanguageType],[0,1]);"><input class=codename name=LanguageType id="LanguageType" readOnly=true >
	    </td>
	</tr>
	<tr class=common>
	    <td class=title5>模板类型</td>
		<td class=input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=TempleteType  id="TempleteType"
			onclick="return showCodeList('templetetype',[this,TempleteTypeName],[0,1]);" ondblclick="return showCodeList('templetetype',[this,TempleteTypeName],[0,1]);"
			onkeyup="return showCodeListKey('templetetype',[this,TempleteTypeName],[0,1]);"><input class=codename name=TempleteTypeName id="TempleteTypeName" readOnly=true >
		</td>
		<td class= title5>审批状态</td>
	 	<td class=input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=State  id="State"
			onclick="return showCodeList('state',[this,ApproveState],[0,1],null,null,null,1);" ondblclick="return showCodeList('state',[this,ApproveState],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('state',[this,ApproveState],[0,1],null,null,null,1);"><input class=codename name=ApproveState id="ApproveState" readOnly=true  elementtype=nacessary>
		</td> 
	</tr>
</table>
</div>
</div>
<a href="javascript:void(0)" class=button onclick="easyQueryClick();">查  询</a>
<!-- <input value="查  询" type="button" class=cssButton onclick="easyQueryClick();"> -->
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
	<div id = "DivApprove" style ="display : none">
		<table class=common>
		    <td class= title>结论</td>
		 	<td class=input style ="width : 100%;"><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=State1 id="State1" verify="结论|notnull"
				onclick="return showCodeList('state',[this,ApproveFlag],[0,1],null,null,null,1);" ondblclick="return showCodeList('state',[this,ApproveFlag],[0,1],null,null,null,1);"
				onkeyup="return showCodeListKey('state',[this,ApproveFlag],[0,1],null,null,null,1);"><input class=codename name=ApproveFlag id="ApproveFlag" readOnly=true elementtype=nacessary>
			</td>
			<tr class=common>
				<td class=title> 审批意见 </td>
			    <td >
			    	<textarea name=Remark cols="100%" rows="4" witdh=100% class="common"></textarea>
		    	</td>
		    </tr>	  
	    </table>
		<a href="javascript:void(0)" class=button onclick="return templeteApprove();">模板审批</a>
   	 	<!-- <input value="模板审批"  type=button class=cssButton   onclick="return templeteApprove();"> -->
    </div>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
