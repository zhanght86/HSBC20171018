<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>

<head>
<script>
var LoadFlag = "<%=request.getParameter("LoadFlag")%>";
var GrpContNo="<%=request.getParameter("GrpContNo")%>";
</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="OutRiskFeeInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="OutRiskFeeInit.jsp"%>
</head>
<body onload="initForm();">
	<form method=post name=fm id="fm" target="fraSubmit" action="RiskFeeSave.jsp">
		<table>
			<tr>
				<td class=common>
    				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
				</td>
				<td class=titleImg align=center>集体保单信息</td>
			</tr>
		</table>
		<div class="maxbox1">
		<Div  id= "divFCDay" style= "display: ''"> 
		<table class=common align=center>
			<TR class=common>
				<TD class=title5>
					投保单号/保单号
				</TD>
				<TD class=input5>
					<Input class="common wid"  name=GrpContNo id="GrpContNo">
					<input type=hidden name="mOperate" id="mOperate">
				</TD>
				<TD class=title5>
					险种编码
				</TD>
				<TD class=input5>
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center"  class=codeno name=RiskCode id="RiskCode" onclick="return showCodeList('GrpRisk',[this,RiskCodeName,GrpPolNo],[0,1,2],null,fm.GrpContNo.value,'b.GrpContNo','1');" ondblclick="return showCodeList('GrpRisk',[this,RiskCodeName,GrpPolNo],[0,1,2],null,fm.GrpContNo.value,'b.GrpContNo','1');" onkeyup="return showCodeListKey('GrpRisk',[this,RiskCodeName,GrpPolNo],[0,1,2],null,fm.GrpContNo.value,'b.GrpContNo','1');"><input class=codename name=RiskCodeName id="RiskCodeName" readonly=true><font color=red>*</font>
				</TD>
			</TR>
			<TR class=common>
				<TD class=title5>
					团体险种保单号
				</TD>
				<TD class=input5>
					<input class="readonly wid" readonly name="GrpPolNo" id="GrpPolNo">
				</tD>
			</TR>
		</table>
	</Div>
	</div>
		<table class=common align=center>
			<TR class=common>
				<TD class=title>
					计算方式说明：01固定值（内扣）02固定比例 （内扣）03固定值（外缴）04固定比例 （外缴）07分档计算（外缴）08分档计算（内扣）09年费
				</td>
			</tr>
		</table>
		<br>
		<div>
			<a href="javascript:void(0)" class=button onclick="easyQueryClick();">管理费查询</a>
		</div>
		
		<!-- <input type=button value="管理费查询" class="cssButton" onclick="easyQueryClick();"> -->
		<table>
			<tr>
				<td class=common>
					<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divRiskFee);">
				</td>
				<td class=titleImg>
					险种管理费明细
				</td>
			</tr>
		</table>
		<Div id="divRiskFee" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanRiskFeeGrid">
						</span>
					</td>
				</tr>
			</table>
		</div>
		<br>
		<div id="divRiskFeeParam" style="display:'none'">
			<table>
				<tr>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor:hand;">
					</td>
					<td class=titleImg>
						管理费明细参数
					</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanRiskFeeParamGrid">
						</span>
					</td>
				</tr>
			</table>
		</div>
		<Div  id= "divReturnBack" style= "display: ''" align= left> 
		
			</Div>
	        <Div  id= "divRiskFeeSave" style= "display: ''" align= left> 
	    <a href="javascript:void(0)" class=button onclick="submitForm();">保  存</a>
	    <a href="javascript:void(0)" class=button onclick="deleteClick();">删  除</a>
		<!-- <input type=button class="cssButton" value="保  存" onclick="submitForm();">
		<input type=button class="cssButton" value="删  除" onclick="deleteClick();"> -->
		</Div>
	</form>
	<br>
	<br>
	<br>
	<br>
	<span id="spanCode" style="display: none; position:absolute; slategray"></span>
	<script>changecolor(); </script>
</body>
</html>
