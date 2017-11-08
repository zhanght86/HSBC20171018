<%
/***************************************************************
 * <p>ProName：FinBankInput.jsp</p>
 * <p>Title：财务机构维护</p>
 * <p>Description：财务银行维护</p>
 * <p>Copyright：Copyright (c) 2013</p>
 * <p>Company：Sinosoft</p>
 * @author   : 杨治纲
 * @version  : 8.0
 * @date     : 2013-01-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
	<title>财务银行维护</title>
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
	<script src="./FinBankInput.js"></script>
	<%@include file="./FinBankInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./FinBankSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divFinBankInfo);">
			</td>
			<td class=titleImg>财务银行信息</td>
		</tr>
	</table>
	<div id="divFinBankInfo" class=maxbox1 style="display:''">
		
		<table class=common>
			<tr class=common>
				<td class=title>银行编码</td>
				<td class=input><input class="wid common" name=FinBankCode id=FinBankCode verify="财务机构代码|NOTNULL" maxlength=13 elementtype=nacessary></td>
				<td class=title>银行名称</td>
				<td class=input colspan="3"><input class=common style="width:554px" name=FinBankName id=FinBankName verify="财务机构名称|NOTNULL" maxlength=60 elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>银行大类</td>
				<td class=input><input class=codeno name=FinBankClass id=FinBankClass style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('finheadbank',[this, FinBankClassName],[0, 1],null,null,null,1);" onkeyup="return showCodeListKey('finheadbank',[this,FinBankClassName],[0,1],null,null,null,1);"><input class=codename name=FinBankClassName></td>
				<td class=title>账户性质</td>
				<td class=input><input class=codeno name=FinBankNature style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('finbanknature',[this, FinBankNatureName],[0, 1],null,null,null,1);" onkeyup="return showCodeListKey('finbanknature',[this,FinBankNatureName],[0,1],null,null,null,1);"><input class=codename name=FinBankNatureName></td>
				<td class=title>财务机构</td>
				<td class=input><input class=codeno name=FinComCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('fincom',[this,FinComName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('fincom',[this,FinComName],[0,1],null,null,null,'1',null);"><input class=codename name=FinComName></td>
			</tr>
			<tr class=common>
				<td class=title>银行账号</td>
				<td class=input><input class="wid common" name=AccNo verify="银行账号|NOTNULL" maxlength=60 elementtype=nacessary></td>
				<td class=title>是否生效</td>
				<td class=input><input class=codeno name=State verify="是否生效|code:trueflag" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('trueflag',[this,StateName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('trueflag',[this,StateName],[0,1],null,null,null,'1',null);"><input class=codename name=StateName></td>
				<td class=title></td>
				<td class=input></td>
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
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divFinBankGridInfo);">
			</td>
			<td class=titleImg>财务机构列表</td>
		</tr>
	</table>
	<div id=divFinBankGridInfo style="display:''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanFinBankGrid"></span></td>
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
	<input type=hidden name=HiddenFinBankCode>
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
