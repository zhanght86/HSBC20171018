<%
/***************************************************************
 * <p>ProName：FinAccItemInput.jsp</p>
 * <p>Title：分支科目定义界面</p>
 * <p>Description：定义会计科目下的分支科目</p>
 * <p>Copyright：Copyright (c) 2013</p>
 * <p>Company：Sinosoft</p>
 * @author   : 石全彬
 * @version  : 8.0
 * @date     : 2013-01-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
	<title>分支科目定义</title>
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
	<script src="./FinAccItemInput.js"></script>
	<%@include file="./FinAccItemInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./FinAccItemSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divFinAccItemInfo);">
			</td>
			<td class=titleImg>分支科目信息</td>
		</tr>
	</table>
	
	<div id="divFinAccItemInfo" class=maxbox1 style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title>分支科目名称</td>
				<td class=input colspan="5"><input class="wid common "style="width:554px" name=AccItemName verify="分支科目名称|NOTNULL" disabled elementtype=nacessary><font color="#FF0000">&nbsp;&nbsp;分支科目名称系统自动生成</font></td>
			</tr>
			<tr class=common>
				<td class=title>分支科目编码1</td>
				<td class=input><input class=codeno name=AccItemCode1 verify="分支科目编码1|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('fincode1',[this,AccItemName1],[0,1],null,'1 and codetype=#accitem#','1',1)" onkeyup="return showCodeListKey('fincode1',[this,AccItemName1],[0,1],null,'1 and codetype=#accitem#','1',1)"><input class=codename name=AccItemName1 elementtype=nacessary></td>
				<td class=title>分支科目编码2</td>
				<td class=input><input class=codeno name=AccItemCode2 verify="分支科目编码2|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" onclick="return beforQueryCode(this, AccItemCode1)" ondblclick="return showCodeList('fincode2',[this,AccItemName2],[0,1],null,conditionCode2,'1',1)" onkeyup="return showCodeListKey('fincode2',[this,AccItemName2],[0,1],null,conditionCode2,'1',1)"><input class=codename name=AccItemName2 elementtype=nacessary></td>
				<td class=title>分支科目编码3</td>
				<td class=input><input class=codeno name=AccItemCode3 verify="分支科目编码3|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" onclick="return beforQueryCode(this, AccItemCode2)" ondblclick="return showCodeList('fincode3',[this,AccItemName3],[0,1],null,conditionCode3,'1',1)" onkeyup="return showCodeListKey('fincode3',[this,AccItemName3],[0,1],null,conditionCode3,'1',1)"><input class=codename name=AccItemName3 elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>会计科目</td>
				<td class=input><input class=codeno name=FinCode verify="会计科目|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('finaccount',[this,FinName],[0,1],null,null,null,1)" onkeyup="return showCodeListKey('finaccount',[this,FinName],[0,1],null,null,null,1)"><input class=codename  name=FinName elementtype=nacessary onkeydown="fuzzyQueryFinCode(FinCode,FinName)"></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>备注</td>
				<td class=input colspan="5"><textarea class=common name=Remark id=Remark rows="3" cols="60"></textarea></td>
			</tr>
		</table>
		
		<input class=cssButton type=button value="查  询" onclick="queryClick();">
		<input class=cssButton type=button value="新  增" onclick="insertClick();">
		<input class=cssButton type=button value="修  改" onclick="updateClick();">
		<input class=cssButton type=button value="删  除" onclick="deleteClick();">
		<input class=cssButton type=button value="重  置" onclick="resetClick();">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divFinAccItemGrid);">
			</td>
			<td class=titleImg>分支科目信息列表</td>
		</tr>
	</table>
	<div id=divFinAccItemGrid style="display:''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanFinAccItemGrid"></span></td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
			<input class=cssButton type=button value="导出数据" onclick="exportData();">
		</center>
	</div>
	
	<!--隐藏区-->
	<input type=hidden name=Operate>
	<input type=hidden name=AccItemCode>
	<input type=hidden name=HiddenAccItemName>
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
