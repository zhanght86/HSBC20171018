<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	//�������ƣ�TempReinTransInput.jsp
	//�����ܣ��ٱ���˹���
	//�������ڣ�2006-11-19 11:10:36
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
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

<title>�ٱ���Ϣ</title>
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
						<td class=titleImg>�ٷ��Ժ˽��</td>
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
					<td class=common align="right"><input value="�����ٱ�����"
						class=CssButton type=button onclick="AutoReInsure();"></td>
				</table>
			</Div>
			<table>
				<tr>
					<td class=common><IMG src="../common/images/butExpand.gif"
						style="cursor: hand;" OnClick="showPage(this,divShowInfo);">
					</td>
					<td class=titleImg>����б�</td>
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
					<td class=titleImg>�ŵ��ٷ��Ժ˽��</td>
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
					<td class=titleImg>�����ٷ��Ժ˽��</td>
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
					<td class=titleImg>��ѯ����</td>
				</tr>
			</table>
			<Div id="divSearch" style="display: ''">
				<Table class=common>
					<TR>
						<TD class=title5>����Ͷ������</TD>
						<TD class=input5><Input class=common name=GrpContNo readonly="readonly">
						</TD>
						<TD class=title5>���ϼƻ�</TD>
						<TD class=input5><Input class="codeno" name='ContPlanCode'
							ondblClick="showCodeList('rigrpcontplan',[this,ContPlanCodeName],[0,1],null,null,fm.GrpContNo.value,1,180);"
							onkeyup="showCodeListKey('rigrpcontplan',[this,ContPlanCodeName],[0,1],null,null,fm.GrpContNo.value,1,180);"><Input
							class=codename name='ContPlanCodeName'></TD>
						<td text-align:right class="title5">���ִ���</td>
						<td class="input5"><Input class="codeno" name='RiskCode'
							ondblClick="showCodeList('rigrpcontrisk',[this,RiskCodeName],[0,1],null,null,fm.GrpContNo.value,1,180);"
							onkeyup="showCodeListKey('rigrpcontrisk',[this,RiskCodeName],[0,1],null,null,fm.GrpContNo.value,1,180);"><Input
							class=codename name='RiskCodeName'></td>
					</TR>
					<TR>
						<TD class=title5>���δ���</TD>
						<TD class=input5><Input class="codeno" name='DutyCode'
							ondblClick="showCodeList('rigrpcontduty',[this,DutyCodeName],[0,1],null,null,fm.GrpContNo.value,1,180);"
							onkeyup="showCodeListKey('rigrpcontduty',[this,DutyCodeName],[0,1],null,null,fm.GrpContNo.value,1,180);"><Input
							class=codename name='DutyCodeName'></TD>
						<TD class=title5>�����˺�</TD>
						<TD class=input5><Input class=common name=InsuredNo>
						</TD>
						<td text-align:right class="title5">����������</td>
						<td class="input5"><Input class=common name=InsuredName>
						</td>
					</TR>
					<TR>
						<TD class=title5>�ٷֺ˱�����</TD>
						<TD class=input5><input class=codeno readOnly
							name="TempUWConclusionConf" CodeData="0|^00|��ͬ�ֱ�|^02|�����|"
							ondblclick="return showCodeListEx('', [this,TempUWConclusionConfName],[0,1],null,null,null,1);"
							onkeyup="return showCodeListKeyEx('', [this,TempUWConclusionConfName],[0,1],null,null,null,1);"
							verify="��ͬ����|NOTNULL"><input class=codename
							name=TempUWConclusionConfName readonly="readonly"></TD>
						<TD class=title5></TD>
						<TD class=input5></TD>
						<td class="title5"></td>
						<td class="input5"></td>
					</TR>
				</Table>
			</Div>
			<br> <INPUT VALUE="��  ѯ" class=cssButton TYPE=button
				onclick="SearchRecord();"> &nbsp;&nbsp;&nbsp;&nbsp; <INPUT
				VALUE="��  ��" class=cssButton TYPE=button onclick="ResetForm();"><br>
			<br>
			<table>
				<tr>
					<td class=common><IMG src="../common/images/butExpand.gif"
						style="cursor: hand;" OnClick="showPage(this,divSearchResult);">
					</td>
					<td class=titleImg>��ѯ���</td>
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
						<TD class=title5>�ٷֺ˱�����</TD>
						<TD class=input5><input class=codeno readOnly
							name="TempUWConclusion" CodeData="0|^02|���ٷ����|^99|ȡ���ٷ����"
							ondblclick="return showCodeListEx('State', [this,TempUWConclusionName],[0,1],null,null,null,1);"
							onkeyup="return showCodeListKeyEx('State', [this,TempUWConclusionName],[0,1],null,null,null,1);"
							verify="�ٷֺ˱�����|NOTNULL"><input class=codename
							name=TempUWConclusionName readonly="readonly" elementtype=nacessary>
						</TD>
						<TD class=input5><INPUT VALUE="��ѡ�н���½���" name="SelConClusion"
							class=cssButton TYPE=button onclick="TempConclusionSel();">
						</TD>
						<TD class=title5><INPUT VALUE="�����н���½���" name="AllConClusion"
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
					<TD class=title5>����������</TD>
					<TD class=input5><input class=codeno readOnly
						name="lcTempUWConclusion"
						CodeData="0|^01|֪ͨ�޶�|^02|�����޶�|^03|��������������|^99|ȡ�����"
						ondblclick="return showCodeListEx('State', [this,lcTempUWConclusionName],[0,1],null,null,null,1);"
						onkeyup="return showCodeListKeyEx('State', [this,lcTempUWConclusionName],[0,1],null,null,null,1);"
						verify="�ٷֺ˱�����|NOTNULL"><input class=codename
						name=lcTempUWConclusionName readonly="readonly" elementtype=nacessary>
					</TD>
					<TD class=input5><INPUT VALUE="��ѡ�н���½���" name="lcSelConClusion"
						class=cssButton TYPE=button onclick="TempConclusionSel();">
					</TD>
					<TD class=title5><INPUT VALUE="�����н���½���" name="lcAllConClusion"
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
				<td class=titleImg>�걨���</td>
			</tr>
		</table>
		<div id="divDiskInput" style="display: ''">
			<table>
				<TD class=common><textarea name="Remark" cols="120" rows="3"
						class="common" onfocus=clearData() verify="������|len<=1000">
  	     </textarea></TD>
			</table>
	</form>

	<form action="./UpLodeSave.jsp" method=post name=fmImport
		target="fraSubmit" ENCTYPE="multipart/form-data">

		<td class=title5>ѡ����ĵ���</td> <Input type="file" name=FileName
			class=common> <INPUT VALUE="���ظ���" class=cssButton TYPE=hidden
			onclick="ReInsureUpload();"> <input TYPE="hidden"
			class=Common name=FilePath value="">

		<td class=title5>��������</td> <input class=codeno readOnly
			name="AutoAnswer" CodeData="0|^0|���׼�|^1|��׼��"
			ondblclick="return showCodeListEx('SendType', [this,AutoAnswerName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKeyEx('SendType', [this,AutoAnswerName],[0,1],null,null,null,1);"
			verify="��������|NOTNULL"><input class=codename
			name=AutoAnswerName readonly="readonly" elementtype=nacessary> <br>
		<br>
		<table>
			<tr>
				<td class=common><INPUT VALUE="�����ٱ����" class=cssButton
					TYPE=button onclick="SendUWReInsure();"></td>
				<td class=common><INPUT
					VALUE="&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;"
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
				<td class=titleImg>�ٱ��������</td>
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
			<INPUT VALUE="��  ҳ" class=cssButton TYPE=button
				onclick="turnPage2.firstPage();"> <INPUT VALUE="��һҳ"
				class=cssButton TYPE=button onclick="turnPage2.previousPage();">
			<INPUT VALUE="��һҳ" class=cssButton TYPE=button
				onclick="turnPage2.nextPage();"> <INPUT VALUE="β  ҳ"
				class=cssButton TYPE=button onclick="turnPage2.lastPage();">
		</Div>
		<table>
			<tr>
				<td class=common><IMG src="../common/images/butExpand.gif"
					style="cursor: hand;" OnClick="showPage(this,divShowLCDuty);">
				</td>
				<td class=titleImg>�걨/�ظ���Ϣ</td>
			</tr>
		</table>
		<div id=divShowLCDuty>
			<table>
				<TD class=common><textarea name="RemarkInfo" cols="120"
						rows="3" readonly="readonly" class="common" verify="������|len<=1000"></textarea>
				</TD>
			</table>
		</div>
		<div id=divButton1>
			<INPUT VALUE="&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;" class=cssButton
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
				<td class=titleImg>��˽�����Ϣ</td>
			</tr>
		</table>
		<div id=divUWConclusion style="display: ''">
			<div id=divTextArea1 style="display:none;">
				<table>
					<TD class=common><textarea name="UWConclusionInfo" cols="120"
							rows="3" class="common" verify="�˱�������Ϣ|len<=1000"></textarea></TD>
				</table>
			</div>
			<INPUT VALUE="&nbsp;������&nbsp;" class=cssButton TYPE=button
				onclick="AuditEnd();">
		</div>
		<br>
		<hr>
		<br> <INPUT VALUE="&nbsp;��&nbsp&nbsp&nbsp&nbsp��&nbsp;"
			class=cssButton TYPE=button onclick="CloseWindow();">
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
