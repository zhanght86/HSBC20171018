<%
/***************************************************************
 * <p>ProName��LCGrpChargeFeeInput.jsp</p>
 * <p>Title���н�������ά��</p>
 * <p>Description���н�������ά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-05-05
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String tGrpPropNo = request.getParameter("GrpPropNo");
	String tFlag = request.getParameter("Flag");
%>
<script>
	var tGrpPropNo = "<%=tGrpPropNo%>";
	var tFlag = "<%=tFlag%>";

</script>
<html>
<head>
	<title>�н�������ά��</title>
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
	<script src="./LCGrpChargeFeeInput.js"></script>
	<%@include file="./LCGrpChargeFeeInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
	<div id="div3" style="display: ''">
		<form name=fm id=fm method=post action="" target=fraSubmit>
			<table>
				<tr>
					<td class=common>
						<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divFeeRateInfo);">
					</td>
					<td class=titleImg>��������Ϣ</td>
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
			</div>
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
			<input class=cssButton type=button id=FeeRateSaveButton name=FeeRateSaveButton style="display: none" value="��������Ϣ����" onclick="feeRateSubmit();">
			<input class=cssButton type=button id=BussRateSaveButton name=BussRateSaveButton style="display: ''" value="��  ��" onclick="top.close();">
			<input type=hidden name=Operate>
			<input type=hidden name=GrpPropNo>
		</form>
		<br /><br /><br /><br />
	</div>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
