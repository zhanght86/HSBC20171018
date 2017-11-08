<%
/***************************************************************
 * <p>ProName：LJMoidfyBankApplyInput.jsp</p>
 * <p>Title: 客户银行信息修改申请</p>
 * <p>Description：客户银行信息修改申请</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-08-02
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.ExeSQL"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getAttribute("GI");
%>
<script>
	var tOperator = "<%=tGI.Operator%>";
	var tManageCom = "<%=tGI.ManageCom%>";
</script>
<html>
<head>
	<title>客户银行信息修改申请</title>
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
	<script src="./LJModifyBankApplyInput.js"></script>
	<%@include file="./LJModifyBankApplyInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<div style="display: 'none'">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanVirtualGrid" ></span>
				</td>
			</tr>
		</table>
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo1);">
			</td>
			<td class=titleImg>查询条件</td>
		</tr>
	</table>
	<div id="divInfo1" class=maxbox style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>业务类型</td>
				<td class=input><input class=codeno name=QueryBussType id=QueryBussType ondblclick="return returnShowCodeList('finbusstype', [this,BussTypeName], [0,1]);" onkeyup="return returnShowCodeListKey('finbusstype', [this,BussTypeName], [0,1]);" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"><input class=codename name=BussTypeName id=BussTypeName readonly></td>
				<td class=title>业务号</td>
				<td class=input><input class="wid common" name=QueryBussNo id=QueryBussNo></td>
				<td class=title>客户开户银行</td>
				<td class=input><input class=codeno name=QueryHeadBankCode id=QueryHeadBankCode ondblclick="return showCodeList('headbank', [this,QueryHeadBankName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('headbank', [this,QueryHeadBankName], [0,1], null, null, null, 1);" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"><input class=codename name=QueryHeadBankName id=QueryHeadBankName readonly></td>
			</tr>
			<tr class=common>
				<td class=title>客户银行账号</td>
				<td class=input><input class="wid common" name=QueryBankAccNo id=QueryBankAccNo></td>
				<td class=title>客户账户名</td>
				<td class=input><input class="wid common" name=QueryBankAccName id=QueryBankAccName></td>
				<td class=title>投保人名称</td>
				<td class=input><input class="wid common" name=QueryGrpName id=QueryGrpName></td>
			</tr>
			<tr class=common>
				<td class=title>管理机构</td>
				<td class=input><input class=codeno name=QueryManageCom id=QueryManageCom value="<%=tGI.ManageCom%>" ondblclick="return showCodeList('conditioncomcode', [this,QueryManageComName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('conditioncomcode', [this,QueryManageComName], [0,1], null, null, null, 1);" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"><input class=codename name=QueryManageComName id=QueryManageComName readonly></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="查  询" onclick="queryInfo();">
	</div>

	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo2);">
			</td>
			<td class=titleImg>待维护银行信息列表</td>
		</tr>
	</table>
	<div id="divInfo2" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanModifyBankInfoGrid" ></span>
				</td>
			</tr>
		</table>
		<center>		
			<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
			
		</center>
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo3);">
			</td>
			<td class=titleImg>历史银行信息</td>
		</tr>
	</table>
	<div id="divInfo3" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanHisBankInfoGrid" ></span>
				</td>
			</tr>
		</table>
		<center>		
			<input class=cssButton90 type=button value="首  页" onclick="turnPage2.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage2.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage2.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage2.lastPage();">
		</center>
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo4);">
			</td>
			<td class=titleImg>客户银行信息</td>
		</tr>
	</table>
	<div id="divInfo4" class=maxbox style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>客户开户银行</td>
				<td class=input><input class=codeno name=HeadBankCode id=HeadBankCode ondblclick="return showCodeList('headbank', [this,HeadBankName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('headbank', [this,HeadBankName], [0,1], null, null, null, 1);" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"><input class=codename name=HeadBankName id=HeadBankName onkeydown="fuzzyQueryHeadBank(HeadBankCode,HeadBankName)" elementtype=nacessary></td>
				<td class=title>客户开户行所在省</td>
				<td class=input><input class=codeno name=Province id=Province ondblclick="return showCodeList('province', [this,ProvinceName], [0,1], null, null, null, 1);" onkeyup="return showCodeListkey('province', [this,ProvinceName], [0,1], null, null, null, 1);" style="background:url(../common/images/select--bg_03.png) no-repeat right center"><input class=codename name=ProvinceName id=ProvinceName readonly></td>
				<td class=title>客户开户行所在市</td>
				<td class=input><input class=codeno name=City id=City ondblclick="return returnShowCodeList('city', [this,CityName], [0,1]);" onkeyup="return returnShowCodeListkey('city', [this,CityName], [0,1]);" style="background:url(../common/images/select--bg_03.png) no-repeat right center"><input class=codename name=CityName id=CityName readonly></td>
			</tr>
			<tr class=common>
				<td class=title>客户银行账号</td>
				<td class=input><input class="wid common" name=AccNo id=AccNo elementtype=nacessary></td>
				<td class=title>客户账户名</td>
				<td class=input><input class="wid common" name=AccName id=AccName elementtype=nacessary></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>修改原因</td>
				<td class=input colspan=5><textarea cols=80 rows=3 name=AppDesc></textarea><span style="color: red"> *</span></td>
			</tr>
		</table>
	</div>
	<input class=cssButton type=button id=ApplyButton name=ApplyButton value="申请提交" onclick="applyClick();">
</form>
<form name=fmPub id=fmPub method=post action="" target=fraSubmit>
	<div style="display: 'none'">
		<input type=hidden name=Operate>
	</div>
	
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
