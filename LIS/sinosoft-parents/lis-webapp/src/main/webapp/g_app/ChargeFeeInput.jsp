<%
/***************************************************************
 * <p>ProName：ChargeFeeInput.jsp</p>
 * <p>Title：中介手续费维护</p>
 * <p>Description：中介手续费维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-04-24
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String tGrpPropNo = request.getParameter("GrpPropNo");
	String tState = request.getParameter("state");
%>
<script>
	var tGrpPropNo = "<%=tGrpPropNo%>";
	var tState = "<%=tState%>";
</script>
<html>
<head>
	<title>中介手续费维护</title>
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
	<script src="./ChargeFeeInput.js"></script>
	<%@include file="./ChargeFeeInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
	<div id="div3" style="display: ''">
		<form name=fm id=fm method=post action="./ChargeFeeSave.jsp" target=fraSubmit>	
			<table>
				<tr>
					<td class=common>
						<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divFeeRateInfo);">
					</td>
					<td class=titleImg>费用率信息</td>
				</tr>
			</table>
			<div id="divFeeRateInfo" style="display: ''">
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1>
							<span id="spanFeeRateInfoGrid" ></span>
						</td>
					</tr>
				</table>
			
		<br>
			<div id="divZJInfo" style="display: none">
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1>
							<span id="spanZJGrid" ></span>
						</td>
					</tr>
				</table>
			</div>
			<input class=cssButton type=button id=FeeRateSaveButton name=FeeRateSaveButton style="display: none" value="费用率信息保存" onclick="feeRateSubmit();">
			<input class=cssButton type=button id=BussRateSaveButton name=BussRateSaveButton style="display: ''" value="关  闭" onclick="top.close();">
			<input type=hidden name=Operate>
			<input type=hidden name=GrpPropNo>
		</div>
		</form>
	</div>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
