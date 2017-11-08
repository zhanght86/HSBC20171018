<%
/***************************************************************
 * <p>ProName：LLClaimMaimRateInput.jsp</p>
 * <p>Title：伤残比例维护</p>
 * <p>Description：伤残比例维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
	<title>伤残比例维护</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./LLClaimMaimRateInput.js"></script>
	<%@include file="./LLClaimMaimRateInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
	<form name=fm id=fm method=post action="./LLClaimMaimRateSave.jsp" target=fraSubmit>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery1);">
				</td>
				<td class=titleImg>查询条件</td>
			</tr>
		</table> 
		<div id=divQuery1 class=maxbox1>
			<table class = common>
				<tr class = common>
  				<td class = title>伤残类型</td>
  				<td class=input><input class=codeno id=DefoTypeID name=DefoTypeCodeQ  style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('defotype',[this,DefoTypeNameQ],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('defotype',[this,DefoTypeNameQ],[0,1],null,null,null,1);"  ><input class=codename name=DefoTypeNameQ  readonly></td>
    			<td class = title>伤残分类</td>
 					<td class = input><input class="wid common" name=DefoClassQ></td>
  				<td class = title>伤残分类名称</td>
  				<td class = input><input class="wid common" name=DefoClassNameQ></td>
				</tr>
				<tr class = common>
  				<td class = title>伤残级别</td>
 					<td class = input><input class="wid common" name=DefoGradeQ></td>
  				<td class = title>伤残级别名称</td>
  				<td class = input><input class="wid common" name=DefoGradeNameQ ></td>
  				<td class = title></td>
  				<td class = input></td>
				</tr> 	 	
				<tr class = common>
  				<td class = title>伤残代码</td>
  				<td class = input><input class="wid common" name=DefoCodeQ></td>
    			<td class = title>伤残代码名称</td>
 					<td class = input><input class="wid common" name=DefoCodeNameQ></td>
  				<td class = title></td>
  				<td class = input></td>
				</tr> 			
			</table>	
			<input class=cssButton type=button value="查  询"  onclick=QueryDefoInfo();>
		</div>	
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryInfo1);">
				</td>
				<td class=titleImg>伤残等级参数列表</td>
			</tr>
		</table> 
		<div  id= "divQueryInfo1" style= "display: ''">
			<table  class= common>
				<tr  class= common>
					<td text-align: left colSpan=1>
						<span id="spanDefoGrid"></span> 
					</td>
				</tr>
			</table>
		</div>
		<center>
		<input class=cssbutton90 value="首  页" type=button onclick="turnPage2.firstPage();"> 
	    <input class= cssbutton91 value="上一页" type=button onclick="turnPage2.previousPage();">                   
	    <input class= cssbutton92 value="下一页" type=button onclick="turnPage2.nextPage();"> 
	    <input class= cssbutton93 value="尾  页" type=button onclick="turnPage2.lastPage();">  
	    <input class= cssbutton value="导出数据" type=button onclick="exportData();"> 
		</center>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryInfo10);">
				</td>
				<td class=titleImg>伤残等级参数维护</td>
			</tr>
		</table> 
		<div id="divQueryInfo10" class=maxbox1>
			<input class=	cssbutton value="新  增" type=button name=DInsert onclick="DefoInsert();"> 
	    <input class= cssbutton value="修  改" type=button name=DModify onclick="DefoModify();">                   
	    <input class= cssbutton value="删  除" type=button name=DDelete onclick="DefoDelete();"> 
	    <input class= cssbutton value="重  置" type=button name=DTurnBack onclick="DefoTurnBack();"> 
	 		<br/>
	    <table class = common>
				<tr class = common>
					<td class = title>伤残类型</td>
					<td class=input><input class=codeno id=DefoType name=DefoTypeCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('defotype',[this,DefoTypeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('defotype',[this,DefoTypeName],[0,1],null,null,null,1);"  ><input class=codename name=DefoTypeName  elementtype=nacessary readonly></td>
					<td class = title style="display:none" id=DefoClass1 name=DefoClass1>伤残分类</td>
					<td class = input style="display:"><input class="wid common"  id=DefoClass2 style="display:none" name="DefoClass" ></td>
					<td class = title style="display:none" id=DefoClassName1>伤残分类名称</td>
					<td class = input style="display:"><input class="wid common"  id=DefoClassName2  name="DefoClassName" style="display:none" ></td>
				</tr> 	
				<tr class = common>
					<td class = title>伤残级别</td>
					<td class = input><input class="wid common" name="DefoGrade" elementtype=nacessary></td>
					<td class = title>伤残级别名称</td>
					<td class = input><input class="wid common" name="DefoGradeName" elementtype=nacessary></td>
					<td class = title></td>
					<td class = input></td>
				</tr>
				<tr class = common>
					<td class = title>伤残代码</td>
					<td class = input><input class="wid common" name="DefoCode" elementtype=nacessary></td>
					<td class = title>伤残代码名称</td>
					<td class = input><input class="wid common" name="DefoName" elementtype=nacessary></td>
					<td class = title>伤残给付比例</td>
					<td class = input><input class="wid common" name="DefoRate" elementtype=nacessary></td>
				</tr> 			
			</table>		    
		</div>
	<!--隐藏表单区域-->	
		<input type=hidden  name=Operate > 
		<input type=hidden  name=tDefoType > 
		<input type=hidden  name=tDefoCode > 
		<br /><br /><br /><br />
  </form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>  
</body>
</html>
