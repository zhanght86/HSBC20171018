<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//程序名称：RiskFeeInput.jsp
//程序功能：
//创建日期：2002-08-15 11:48:42
//创建人 ：CrtHtml程序创建
//更新记录： 更新人  更新日期   更新原因/内容
%>
<%
String GrpContNo = request.getParameter("GrpContNo");
String LoadFlag = request.getParameter("LoadFlag");
//GrpContNo = "220110000000071";
%>
<head>
<script>
LoadFlag="<%=LoadFlag%>";
GrpContNo="<%=GrpContNo%>";
</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="RiskFeeInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="RiskFeeInit.jsp"%>
</head>
<body onload="initForm();">
	<form method=post name=fm target="fraSubmit" action="RiskFeeSave.jsp">
		<table>
			<tr>
				<td class=common>
					<IMG src="../common/images/butExpand.gif" style="cursor:hand;">
				</td>
				<td class=titleImg align=center>集体保单信息</td>
			</tr>
		</table>
		<table class=common align=center>
			<TR class=common>
				<TD class=title>
					合同号
				</TD>
				<TD class=input>
					<Input class="readonly" readonly name=GrpContNo value="<%=GrpContNo%>">
					<input type=hidden name="mOperate">
				</TD>
				<TD class=title>
					险种编码
				</TD>
				<TD class=input>
					<Input class=codeno name=RiskCode ondblclick="return showCodeList('GrpRisk',[this,RiskCodeName,GrpPolNo],[0,1,2],null,fm.GrpContNo.value,'b.GrpContNo','1');" onkeyup="return showCodeListKey('GrpRisk',[this,RiskCodeName,GrpPolNo],[0,1,2],null,fm.GrpContNo.value,'b.GrpContNo','1');"><input class=codename name=RiskCodeName readonly=true><font color=red>*</font>
				</TD>
				<TD class=title>
					分保险单号
				</TD>
				<TD class=input>
					<input class="readonly" readonly name="GrpPolNo">
				</tD>
			</TR>
		</table>
		<input type=button value="管理费查询" class="cssButton" onclick="easyQueryClick();">
		<br><br>
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
		<!--input type=button class="cssButton" value="上一步" onclick="returnparent();"-->
			</Div>
	        <Div  id= "divRiskFeeSave" style= "display: ''" align= left> 
		<input type=button class="cssButton" value="保  存" onclick="submitForm();" name=save>
		<input type=button class="cssButton" value="删  除" onclick="deleteClick();">
		</Div>
	</form>
	<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
