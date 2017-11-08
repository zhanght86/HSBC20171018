<%@include file="../i18n/language.jsp"%>


<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

	<%
		//程序名称：PDDutyGetAliveInput.jsp
		//程序功能：责任给付生存
		//创建日期：2009-3-16
		//创建人  ：
		//更新记录：  更新人    更新日期     更新原因/内容
	%>

	<head>
		<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
		<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
		<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
		<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
		<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
		<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<link rel="stylesheet" type="text/css"
			href="../common/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css"
			href="../common/themes/icon.css">
		<script type="text/javascript">
	 		var riskcode = '<%=request.getParameter("riskcode")%>';
	 		var itemcodeStr = '<%=request.getParameter("itemcode")%>';
	 		var calelementStr = '<%=request.getParameter("calelement")%>';
	 		var operator = '<%=request.getParameter("operator")%>';
		</script>
		<SCRIPT src="PDSugIncomeFactor.js"></SCRIPT>
		<script src="../common/javascript/jquery.js"></script>
		<script src="../common/javascript/jquery.easyui.min.js"></script>
		<%@include file="PDSugIncomeFactorInit.jsp"%>


	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
	<body onload="init(),initElementtype();">
		<form action="./PDSugIncomeFactorSave.jsp" method=post name=fm target="fraSubmit">
			<table>
				<tr>
					<td class="titleImg">
收益计算因子:
					</td>
				</tr>
			</table>

			<table class=common>
				<tr class=common>
					<td class=title5>
收益项代码:
					</td>
					<td class=input5>
						<input class="common8" name=ITEMCODE verify="收益项代码|NOTNUlL&LEN<=20" elementtype="nacessary">
					</td>
					<td class=title5>
计算顺序：
					</td>
					<td class=input5>
						<input class="common8" name=CALORDERNO verify="计算顺序|NOTNUlL&NUM" elementtype="nacessary">
					</td>
				</tr>
				<tr class=common>
					<td class=title5>
计算因子：
					</td>
					<td class=input5>
						<input class="common8" name=CALELEMENT verify="计算因子|NOTNUlL&LEN<=20" elementtype="nacessary">
					</td>
					<td class=title5>
备注说明：
					</td>
					<td class=input5>
						<input class="common8" name=REMARK>
					</td>
				</tr>
				<tr class=common>
					<td class=title5>
因子属性：
					</td>
					<td class=input5>
						<Input class=codeno name=ELEMENTPROPERTY ondblclick="return showCodeList('sugeletype',[this,ELEMENTPROPERTYName],[0,1]);" onkeyup="return showCodeListKey('sugeletype',[this,ELEMENTPROPERTYName],[0,1]);"><input class=codename name="ELEMENTPROPERTYName" readonly="readonly">
					</td>

					<td class=title5>
位置调整：
					</td>
					<td class=input5>
						<Input class=codeno name=ADJUSTPOSITION ondblclick="return showCodeList('suglocationtype',[this,ADJUSTPOSITIONName],[0,1]);" onkeyup="return showCodeListKey('suglocationtype',[this,ADJUSTPOSITIONName],[0,1]);"><input class=codename name="ADJUSTPOSITIONName" readonly="readonly">
					</td>
				</tr>
				<tr class=common>
					<td class=title5>
变量代码：
					</td>
					<td class=input5>
						<input class="common" name=VARIABLECODE>
					</td>
					<td class=title5>
						Sql执行方式：
					</td>
					<td class=input5>
						<Input class=codeno name=SQLEXCUTETYPE ondblclick="return showCodeList('sugsqltype',[this,SQLEXCUTETYPEName],[0,1]);" onkeyup="return showCodeListKey('sugsqltype',[this,SQLEXCUTETYPEName],[0,1]);"><input class=codename name="SQLEXCUTETYPEName" readonly="readonly">
					</td>
				</tr>
				<tr class=common>
					<td class=title5>
计算sql：
					</td>
					<td class=input5 colspan="3">
						<textarea rows="5" cols="75" name=CALSQL
							tip="可以数据库表：LSPol、LSCont、LSAppnt、LSInsured"></textarea>
						<input value="测试sql" name="btnEdit" onClick="testSql();"
							class="cssButton" type="button">
					</td>
				</tr>
				<tr class=common>
					<td class=title5>
结果开始点：
					</td>
					<td class=input5>
						<input class="common" name=STARTPOINT>
					</td>
					<td class=title5>
开始点单位：
					</td>
					<td class=input5>
						<Input class=codeno name=STARTPOINTFLAG ondblclick="return showCodeList('sugyearflag',[this,STARTPOINTFLAGName],[0,1]);" onkeyup="return showCodeListKey('sugyearflag',[this,STARTPOINTFLAGName],[0,1]);"><input class=codename name="STARTPOINTFLAGName" readonly="readonly">
					</td>
				</tr>
				<tr class=common>
					<td class=title5>
结果结束点：
					</td>
					<td class=input5>
						<input class="common" name=ENDPOINT>
					</td>
					<td class=title5>
结束点单位：
					</td>
					<td class=input5>
						<Input class=codeno name=ENDPOINTFLAG ondblclick="return showCodeList('sugyearflag',[this,ENDPOINTFLAGName],[0,1]);" onkeyup="return showCodeListKey('sugyearflag',[this,ENDPOINTFLAGName],[0,1]);"><input class=codename name="ENDPOINTFLAGName" readonly="readonly">
					</td>
				</tr>
				<tr class=common>
					<td class=title5>
循环因子初始值：
					</td>
					<td class=input5>
						<input class="common" name=INITVALUE>
					</td>
					<td class=title5>
循环因子步长：
					</td>
					<td class=input5>
						<input class="common" name=STEPVALUE >
					</td>
				</tr>
				<tr class=common>
					<td class=title5>
结果精确位数：
					</td>
					<td class=input5>
						<input class="common" name=RESULTPRECISION verify="结果精确位数|NOTNUlL&NUM" elementtype="nacessary">
					</td>
				</tr>
			</table>


			<div align=left>
				<input value="保  存" name="btnSave" onClick="save()"
					class="cssButton" type="button">
				<input value="取  消" name="btnEdit" onClick="cannelFactor()"
					class="cssButton" type="button">
				<input value="数据表定义" name="btnEdit" onClick="dataTableDef()"
					class="cssButton" type="button">
			</div>
			<input type=hidden name="operator" value=<%=request.getParameter("operator")%> >
			<input type=hidden name="tableName" value="PD_CalcuteElemet">
			<input type=hidden name=IsReadOnly>
			<input type=hidden name="RiskCode" value=<%=request.getParameter("riskcode")%> >
		</form>
		<span id="spanCode" style="position: absolute; display: none; top: 277px; left: 411px;">
	</body>
</html>

