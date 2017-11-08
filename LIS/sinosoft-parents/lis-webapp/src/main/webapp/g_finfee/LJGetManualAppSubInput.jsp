<%
/***************************************************************
 * <p>ProName：LJGetManualAppSubInput.jsp</p>
 * <p>Title: 手动付费申请明细</p>
 * <p>Description：手动付费申请明细</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-06-10
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.ExeSQL"%>
<%
	String tAppCom = request.getParameter("AppCom");
	String tApplyBachNo = request.getParameter("ApplyBatchNo");
	String tAppDate = request.getParameter("AppDate");
%>
<script>
	var tApplyBatchNo = "<%=tApplyBachNo%>"
	var tAppCom = "<%=tAppCom%>";
	var tAppDate = "<%=tAppDate%>";
</script>
<html>
<head>
	<title>手动付费申请</title>
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
	<script src="./LJGetManualAppSubInput.js"></script>
	<%@include file="./LJGetManualAppSubInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<div style="display: none">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanVirtualGrid" ></span>
				</td>
			</tr>
		</table>
	</div>
	<div id="divInfo1" class=maxbox style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>批次号</td>
				<td class=input><input class="wid readonly" name=ApplyBatchNo id=ApplyBatchNo readonly></td>
				<td class=title>申请起期</td>
				<td class=input><input class="wid readonly" name=ApplyDate id=ApplyDate readonly></td>
				<td class=title>总金额</td>
				<td class=input><input class="wid readonly" name=ApplyMoney id=ApplyMoney readonly></td>
			</tr>
			<tr class=common>
				<td class=title>笔数</td>
				<td class=input><input class="wid readonly" name=ApplyCount id=ApplyCount readonly></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button id=ConfirmButton name=ConfirmButton value="批次提交确认" onclick="confirmClick();">
		<input class=cssButton type=button value="返  回" onclick="backClick();">
	</div>

	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo2);">
			</td>
			<td class=titleImg>查询条件</td>
		</tr>
	</table>
	<div id="divInfo2" class=maxbox style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>保单号</td>
				<td class=input><input class="wid common" name=QueryGrpContNo id=QueryGrpContNo></td>
				<td class=title>业务类型</td>
				<td class=input><input class=codeno name=QueryBussType id=QueryBussType ondblclick="return returnShowCodeList('finbusstype', [this,BussTypeName], [0,1]);" onkeyup="return returnShowCodeListKey('finbusstype', [this,BussTypeName], [0,1]);" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"><input class=codename name=BussTypeName id=BussTypeName readonly></td>
				<td class=title>业务号</td>
				<td class=input><input class="wid common" name=QueryBussNo id=QueryBussNo></td>
			</tr>
			<tr class=title>
				<td class=title>应付起期</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate dateFormat="short" onClick="laydate({elem: '#QueryStartDate'});" id="QueryStartDate"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>应付止期</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate dateFormat="short" onClick="laydate({elem: '#QueryEndDate'});" id="QueryEndDate"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>共保公司名称</td>
				<td class=input><input class="wid common" name=QueryInsuranceComName id=QueryInsuranceComName></td>
			</tr>
			<tr class=common>
				<td class=title>投保人名称</td>
				<td class=input><input class="wid common" name=QueryAppntName id=QueryAppntName></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="查  询" onclick="queryInfo('0');">
	</div>

	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo3);">
			</td>
			<td class=titleImg>待审核付费信息列表</td>
		</tr>
	</table>
	<div id="divInfo3" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanGetInfoGrid" ></span>
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
	
	<!-- add by songsz 20150306 增加处理转溢缴 -->
	<div id="divInfo2" style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>处理方式</td>
				<td class=input><input class=codeno name=GetDealType id=GetDealType ondblclick="return showCodeList('fingetdealtype', [this,GetDealTypeName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('fingetdealtype', [this,GetDealTypeName], [0,1], null, null, null, 1);" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"><input class=codename name=GetDealTypeName id=GetDealTypeName readonly elementtype=nacessary></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
	</div>
	<input class=cssButton type=button id=ChooseButton name=ChooseButton value="选  择" onclick="chooseClick();">
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo4);">
			</td>
			<td class=titleImg>已选择待付费信息列表</td>
		</tr>
	</table>
	<div id="divInfo4" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanApplyGetInfoGrid" ></span>
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
	<input class=cssButton type=button id=CancelButton name=CancelButton value="取消选择" onclick="cancelClick();">
	<input class=cssButton type=button id=ModifyButton name=ModifyButton value="银行维护申请" onclick="modifyBankClick();">
	<input class=cssButton type=button id=PrintButton name=PrintButton value="溢缴退费打印" onclick="printClick();">
</form>
<form name=fmPub id=fmPub method=post action="" target=fraSubmit>
	<div style="display: none">
		<input type=hidden name=Operate>
	</div>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
