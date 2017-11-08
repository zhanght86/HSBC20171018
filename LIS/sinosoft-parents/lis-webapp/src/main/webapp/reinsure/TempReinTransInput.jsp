<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	//程序名称：TempReinTransInput.jsp
	//程序功能：再保审核功能
	//创建日期：2006-11-19 11:10:36
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>

<SCRIPT src="TempReinSendInput.js"></SCRIPT>
<%@include file="TempReinSendInit.jsp"%>

<title>再保信息</title>
</head>
<body onload="initForm();assgnOperate();">
	<form method=post name=fm target="fraSubmit" action="">
		<%@include file="../common/jsp/InputButton.jsp"%>
		<Div id="divIndRule" style="display: ''">
			<Div id="divUWResult" style="display: ''">
				<table>
					<tr>
						<td class=common><IMG src="../common/images/butExpand.gif"
							style="cursor: hand;" OnClick="showPage(this,divLCReInsure);">
						</td>
						<td class=titleImg>临分自核结果</td>
					</tr>
				</table>
				<Div id="divLCReInsure" style="display: ''">
					<table class=common>
						<tr class=common>
							<td style="text-align:left;" colSpan=1><span id="spanReInsureGrid">
							</span></td>
						</tr>
					</table>
					<INPUT type="hidden" name="ContNo" value=""> <INPUT
						type="hidden" name="RunRuleFlag" value="">
				</Div>
			</Div>

			<Div id="divRunRuleButton" style="display:none;">
				<table class=common>
					<td class=common align="right"><input value="运行再保规则"
						class=CssButton type=button onclick="AutoReInsure();"></td>
				</table>
			</Div>
			<table>
				<tr>
					<td class=common><IMG src="../common/images/butExpand.gif"
						style="cursor: hand;" OnClick="showPage(this,divShowInfo);">
					</td>
					<td class=titleImg>审核列表</td>
				</tr>
			</table>

			<Div id="divShowInfo" style="display: ''">
				<Div id="divLCDuty1" style="display: ''">
					<table class=common>
						<tr class=common>
							<td style="text-align:left;" colSpan=1><span id="spanRiskInfoGrid"></span>
							</td>
						</tr>
					</table>
				</div>
				<Div id="divLLClaim" style="display: ''">
					<table class=common>
						<tr class=common>
							<td style="text-align:left;" colSpan=1><span id="spanClaimInfoGrid"></span>
							</td>
						</tr>
					</table>
				</Div>
			</Div>
		</div>

		<Div id="divGrpRule" style="display: ''">
			<table>
				<tr>
					<td class=common><IMG src="../common/images/butExpand.gif"
						style="cursor: hand;" OnClick="showPage(this,divGrpUWResult);">
					</td>
					<td class=titleImg>团单临分自核结果</td>
				</tr>
			</table>
			<Div id="divGrpUWResult" style="display: ''">
				<table class=common>
					<tr class=common>
						<td style="text-align:left;" colSpan=1><span
							id="spanGrpUWResultGrid"> </span></td>
					</tr>
				</table>
			</div>
			<table>
				<tr>
					<td class=common><IMG src="../common/images/butExpand.gif"
						style="cursor: hand;" OnClick="showPage(this,divIndUWResult);">
					</td>
					<td class=titleImg>个人临分自核结果</td>
				</tr>
			</table>
			<Div id="divIndUWResult" style="display: ''">
				<table class=common>
					<tr class=common>
						<td style="text-align:left;" colSpan=1><span
							id="spanIndUWResultGrid"> </span></td>
					</tr>
				</table>
			</Div>
		</Div>

		<Div id="divSearchStag" style="display: ''">
			<br>
			<hr>
			<br>
			<table>
				<tr>
					<td class=common><IMG src="../common/images/butExpand.gif"
						style="cursor: hand;" OnClick="showPage(this,divSearch);"></td>
					<td class=titleImg>查询条件</td>
				</tr>
			</table>
			<Div id="divSearch" style="display: ''">
				<Table class=common>
					<TR>
						<TD class=title5>集体投保单号</TD>
						<TD class=input5><Input class=common name=GrpContNo readonly="readonly">
						</TD>
						<TD class=title5>保障计划</TD>
						<TD class=input5><Input class="codeno" name='ContPlanCode'
							ondblClick="showCodeList('rigrpcontplan',[this,ContPlanCodeName],[0,1],null,null,fm.GrpContNo.value,1,180);"
							onkeyup="showCodeListKey('rigrpcontplan',[this,ContPlanCodeName],[0,1],null,null,fm.GrpContNo.value,1,180);"><Input
							class=codename name='ContPlanCodeName'></TD>
						<td text-align:right class="title5">险种代码</td>
						<td class="input5"><Input class="codeno" name='RiskCode'
							ondblClick="showCodeList('rigrpcontrisk',[this,RiskCodeName],[0,1],null,null,fm.GrpContNo.value,1,180);"
							onkeyup="showCodeListKey('rigrpcontrisk',[this,RiskCodeName],[0,1],null,null,fm.GrpContNo.value,1,180);"><Input
							class=codename name='RiskCodeName'></td>
					</TR>
					<TR>
						<TD class=title5>责任代码</TD>
						<TD class=input5><Input class="codeno" name='DutyCode'
							ondblClick="showCodeList('rigrpcontduty',[this,DutyCodeName],[0,1],null,null,fm.GrpContNo.value,1,180);"
							onkeyup="showCodeListKey('rigrpcontduty',[this,DutyCodeName],[0,1],null,null,fm.GrpContNo.value,1,180);"><Input
							class=codename name='DutyCodeName'></TD>
						<TD class=title5>被保人号</TD>
						<TD class=input5><Input class=common name=InsuredNo>
						</TD>
						<td text-align:right class="title5">被保人姓名</td>
						<td class="input5"><Input class=common name=InsuredName>
						</td>
					</TR>
					<TR>
						<TD class=title5>临分核保结论</TD>
						<TD class=input5><input class=codeno readOnly
							name="TempUWConclusionConf" CodeData="0|^00|合同分保|^02|待审核|"
							ondblclick="return showCodeListEx('', [this,TempUWConclusionConfName],[0,1],null,null,null,1);"
							onkeyup="return showCodeListKeyEx('', [this,TempUWConclusionConfName],[0,1],null,null,null,1);"
							verify="合同类型|NOTNULL"><input class=codename
							name=TempUWConclusionConfName readonly="readonly"></TD>
						<TD class=title5></TD>
						<TD class=input5></TD>
						<td class="title5"></td>
						<td class="input5"></td>
					</TR>
				</Table>
			</Div>
			<br> <INPUT VALUE="查  询" class=cssButton TYPE=button
				onclick="SearchRecord();"> &nbsp;&nbsp;&nbsp;&nbsp; <INPUT
				VALUE="重  置" class=cssButton TYPE=button onclick="ResetForm();"><br>
			<br>
			<table>
				<tr>
					<td class=common><IMG src="../common/images/butExpand.gif"
						style="cursor: hand;" OnClick="showPage(this,divSearchResult);">
					</td>
					<td class=titleImg>查询结果</td>
				</tr>
			</table>
			<Div id="divSearchResult" style="display: ''">
				<table class=common>
					<tr class=common>
						<td style="text-align:left;" colSpan=1><span
							id="spanSearchResultGrid"> </span></td>
					</tr>
				</table>
			</Div>
		</Div>
		<br>

		<Div id="divConclusionXiala" style="display: ''">
			<Div id="divXiala1" style="display: ''">
				<Table class=common>
					<TR>
						<TD class=title5>临分核保结论</TD>
						<TD class=input5><input class=codeno readOnly
							name="TempUWConclusion" CodeData="0|^02|待临分审核|^99|取消临分审核"
							ondblclick="return showCodeListEx('State', [this,TempUWConclusionName],[0,1],null,null,null,1);"
							onkeyup="return showCodeListKeyEx('State', [this,TempUWConclusionName],[0,1],null,null,null,1);"
							verify="临分核保结论|NOTNULL"><input class=codename
							name=TempUWConclusionName readonly="readonly" elementtype=nacessary>
						</TD>
						<TD class=input5><INPUT VALUE="对选中结果下结论" name="SelConClusion"
							class=cssButton TYPE=button onclick="TempConclusionSel();">
						</TD>
						<TD class=title5><INPUT VALUE="对所有结果下结论" name="AllConClusion"
							class=cssButton TYPE=button onclick="TempConclusionAll();">
						</TD>
						<td text-align:right class="title5"></td>
						<td class="input5"></td>
					</TR>
				</Table>
			</Div>
		</Div>
		<Div id="divLcConclusionXiala" style="display: ''">
			<Table class=common>
				<TR>
					<TD class=title5>理赔核赔结论</TD>
					<TD class=input5><input class=codeno readOnly
						name="lcTempUWConclusion"
						CodeData="0|^01|通知限额|^02|参与限额|^03|其它非正常理赔|^99|取消审核"
						ondblclick="return showCodeListEx('State', [this,lcTempUWConclusionName],[0,1],null,null,null,1);"
						onkeyup="return showCodeListKeyEx('State', [this,lcTempUWConclusionName],[0,1],null,null,null,1);"
						verify="临分核保结论|NOTNULL"><input class=codename
						name=lcTempUWConclusionName readonly="readonly" elementtype=nacessary>
					</TD>
					<TD class=input5><INPUT VALUE="对选中结果下结论" name="lcSelConClusion"
						class=cssButton TYPE=button onclick="TempConclusionSel();">
					</TD>
					<TD class=title5><INPUT VALUE="对所有结果下结论" name="lcAllConClusion"
						class=cssButton TYPE=button onclick="TempConclusionAll();">
					</TD>
					<td text-align:right class="title5"></td>
					<td class="input5"></td>
				</TR>
			</Table>
		</Div>
		<br>
		<hr>
		<br>
		<table>
			<tr>
				<td class=common><IMG src="../common/images/butExpand.gif"
					style="cursor: hand;" OnClick="showPage(this,divDiskInput);">
				</td>
				<td class=titleImg>申报意见</td>
			</tr>
		</table>
		<div id="divDiskInput" style="display: ''">
			<table>
				<TD class=common><textarea name="Remark" cols="120" rows="3"
						class="common" onfocus=clearData() verify="审核意见|len<=1000">
  	     </textarea></TD>
			</table>
	</form>

	<form action="./UpLodeSave.jsp" method=post name=fmImport
		target="fraSubmit" ENCTYPE="multipart/form-data">

		<td class=title5>选择导入的档：</td> <Input type="file" name=FileName
			class=common> <INPUT VALUE="上载附件" class=cssButton TYPE=hidden
			onclick="ReInsureUpload();"> <input TYPE="hidden"
			class=Common name=FilePath value="">

		<td class=title5>处理类型</td> <input class=codeno readOnly
			name="AutoAnswer" CodeData="0|^0|简易件|^1|标准件"
			ondblclick="return showCodeListEx('SendType', [this,AutoAnswerName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKeyEx('SendType', [this,AutoAnswerName],[0,1],null,null,null,1);"
			verify="处理类型|NOTNULL"><input class=codename
			name=AutoAnswerName readonly="readonly" elementtype=nacessary> <br>
		<br>
		<table>
			<tr>
				<td class=common><INPUT VALUE="发送再保审核" class=cssButton
					TYPE=button onclick="SendUWReInsure();"></td>
				<td class=common><INPUT
					VALUE="&nbsp;&nbsp;办&nbsp;&nbsp;&nbsp;&nbsp;结&nbsp;&nbsp;"
					class=cssButton TYPE=button onclick="ReInsureOver();"></td>
			</tr>
		</table>
		<div id="divText1" style="display:none;">
			OpeData <INPUT TYPE=text name="OpeData"> OpeFlag <INPUT
				TYPE=text name="OpeFlag"> AuditCode <INPUT TYPE=text
				name="AuditCode"> AuditAffixCode <INPUT TYPE=text
				name="AuditAffixCode"> DeTailFlag <INPUT TYPE=text
				name="DeTailFlag"> SerialNo <INPUT TYPE=text name="SerialNo">
		</div>
	</form>
	<hr>

	<form method=post name=fmInfo target="fraSubmit" action="">
		<table>
			<tr>
				<td class=common><IMG src="../common/images/butExpand.gif"
					style="cursor: hand;" OnClick="showPage(this,divLCReInsureAudit);">
				</td>
				<td class=titleImg>再保审核任务</td>
			</tr>
		</table>
		<Div id="divLCReInsureAudit" style="display: ''">
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1><span
						id="spanReInsureAuditGrid"> </span></td>
				</tr>
			</table>
		</div>
		<Div id="div1" align="center" style="display: ''">
			<INPUT VALUE="首  页" class=cssButton TYPE=button
				onclick="turnPage2.firstPage();"> <INPUT VALUE="上一页"
				class=cssButton TYPE=button onclick="turnPage2.previousPage();">
			<INPUT VALUE="下一页" class=cssButton TYPE=button
				onclick="turnPage2.nextPage();"> <INPUT VALUE="尾  页"
				class=cssButton TYPE=button onclick="turnPage2.lastPage();">
		</Div>
		<table>
			<tr>
				<td class=common><IMG src="../common/images/butExpand.gif"
					style="cursor: hand;" OnClick="showPage(this,divShowLCDuty);">
				</td>
				<td class=titleImg>申报/回复信息</td>
			</tr>
		</table>
		<div id=divShowLCDuty>
			<table>
				<TD class=common><textarea name="RemarkInfo" cols="120"
						rows="3" readonly="readonly" class="common" verify="审核意见|len<=1000"></textarea>
				</TD>
			</table>
		</div>
		<div id=divButton1>
			<INPUT VALUE="&nbsp;下&nbsp;&nbsp;&nbsp;&nbsp;载&nbsp;" class=cssButton
				TYPE=button onclick="DownLoad();">
		</div>

		<br>
		<hr>
		<br>
		<table>
			<tr>
				<td class=common><IMG src="../common/images/butExpand.gif"
					style="cursor: hand;" OnClick="showPage(this,divUWConclusion);">
				</td>
				<td class=titleImg>审核结论信息</td>
			</tr>
		</table>
		<div id=divUWConclusion style="display: ''">
			<div id=divTextArea1 style="display:none;">
				<table>
					<TD class=common><textarea name="UWConclusionInfo" cols="120"
							rows="3" class="common" verify="核保结论信息|len<=1000"></textarea></TD>
				</table>
			</div>
			<INPUT VALUE="&nbsp;审核完毕&nbsp;" class=cssButton TYPE=button
				onclick="AuditEnd();">
		</div>
		<br>
		<hr>
		<br> <INPUT VALUE="&nbsp;关&nbsp&nbsp&nbsp&nbsp闭&nbsp;"
			class=cssButton TYPE=button onclick="CloseWindow();">
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
