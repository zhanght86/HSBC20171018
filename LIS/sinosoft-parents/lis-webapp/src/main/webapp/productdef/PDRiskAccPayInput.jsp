<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

	<%
		//�������ƣ�PDRiskAccPayInput.jsp 
		//�����ܣ��˻������ֶ���
		//�������ڣ�2009-3-14
		//������  ��
		//���¼�¼��  ������    ��������     ����ԭ��/����
	%>
	<%
		String mRiskCode = request.getParameter("riskcode");
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
		<%@include file="buttonshow.jsp"%>
		<SCRIPT src="PDRiskAccPay.js"></SCRIPT>
		<%@include file="PDRiskAccPayInit.jsp"%>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
	<body onload="initForm();initElementtype();">
		<form action="./PDRiskAccPaySave.jsp" method=post name=fm
			target="fraSubmit">
			<br>

			<table>
				<tr>
					<td class="titleImg">
�����µ��˻���ϸ
					</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1>
						<span id="spanMulline11Grid"> </span>
					</td>
				</tr>
			</table>
			<INPUT CLASS=cssbutton VALUE="��  ҳ" TYPE=button
				onclick="Mulline11GridTurnPage.firstPage();">
			<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button
				onclick="Mulline11GridTurnPage.previousPage();">
			<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button
				onclick="Mulline11GridTurnPage.nextPage();">
			<INPUT CLASS=cssbutton VALUE="β  ҳ" TYPE=button
				onclick="Mulline11GridTurnPage.lastPage();">
			</BR>
			</BR>
			<table>
				<tr>
					<td class="titleImg">
�ѱ���������˻��ɷ�
					</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1>
						<span id="spanMulline10Grid"> </span>
					</td>
				</tr>
			</table>
			<INPUT CLASS=cssbutton VALUE="��  ҳ" TYPE=button
				onclick="Mulline10GridTurnPage.firstPage();">
			<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button
				onclick="Mulline10GridTurnPage.previousPage();">
			<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button
				onclick="Mulline10GridTurnPage.nextPage();">
			<INPUT CLASS=cssbutton VALUE="β  ҳ" TYPE=button
				onclick="Mulline10GridTurnPage.lastPage();">
			</BR>
			</BR>
			<!--input value="�ʻ�����Ѷ���" type=button  onclick="button184( )" class="cssButton" type="button" -->
			<br>
			<br>

			<table>
				<tr>
					<td class="titleImg">
�����˻��ɷѶ���
					</td>
				</tr>
			</table>

			<table class=common>
				<tr class=common>
					<TD class=title5>
���ֱ���
					</TD>
					<TD class=input5>
						<Input class=readonly readonly="readonly"   name=RISKCODE  elementtype="nacessary">
					</TD>
					<TD class=title5>
�ɷѱ���
					</TD>
					
					<TD class=input5>
						<Input class="codeno" name=PAYPLANCODE readonly="readonly"
							verify="�ɷѱ���|NOTNUlL"  
							ondblclick="return showCodeList('pd_payplancode',[this,PAYPLANNAME],[0,1],null,'<%=mRiskCode%>','<%=mRiskCode%>',1);"
							onkeyup="return showCodeListKey('pd_payplancode',[this,PAYPLANNAME],[0,1],null,'<%=mRiskCode%>','<%=mRiskCode%>',1);"><input class=codename name=PAYPLANNAME readonly="readonly"  elementtype="nacessary">

					</TD>
					</tr>
					<tr class = common>
					<TD class=title5>
�����ʻ�����
					</TD>
					<TD class=input5>
						<Input class="codeno" name=INSUACCNO readonly="readonly"
							verify="�����ʻ�����|NOTNUlL"
							ondblclick="return showCodeList('pd_riskinsuacc',[this,INSUACCNOName],[0,1],null,'<%=mRiskCode%>','<%=mRiskCode%>',1);"
							onkeyup="return showCodeListKey('pd_riskinsuacc',[this,INSUACCNOName],[0,1],null,'<%=mRiskCode%>','<%=mRiskCode%>',1);"><input class=codename name=INSUACCNOName readonly="readonly"  elementtype="nacessary">
					</TD>
					<TD class=title5 STYLE="display:none;">
Ĭ�ϱ���
					</TD>
					<TD class=input5 STYLE="display:none;">
						<Input class=common verify="Ĭ�ϱ���|num" name=DEFAULTRATE value="1">
					</TD>
				</tr>
				<tr class=common>
					<TD class=title5 STYLE="display:none;">
�Ƿ���Ҫ¼��
					</TD>
					<TD class=input5 STYLE="display:none;">
						<Input class="codeno" name=NEEDINPUT value="0" readonly="readonly"
							verify="�Ƿ���Ҫ¼��|NOTNUlL"
							ondblclick="return showCodeList('pd_zeroone',[this,NEEDINPUTName],[0,1]);"
							onkeyup="return showCodeListKey('pd_zeroone',[this,NEEDINPUTName],[0,1]);">
						<input class=codename name=NEEDINPUTName value="0" readonly="readonly">

					</TD>
					<TD class=title5 STYLE="display:none;">
�˻�����λ��
					</TD>
					<TD class=input5 STYLE="display:none;">
						<Input class="codeno" name=ACCCREATEPOS value="0" readonly="readonly"
							ondblclick="return showCodeList('pd_acccreatepos',[this,ACCCREATEPOSName],[0,1]);"
							onkeyup="return showCodeListKey('pd_acccreatepos',[this,ACCCREATEPOSName],[0,1]);">
						<input class=codename name=ACCCREATEPOSName value="Ͷ����¼��ʱ����"
							readonly="readonly">
					</TD>

					<TD class=title5 STYLE="display:none;">
ת���˻�ʱ���㷨����
					</TD>
					<TD class=input5 STYLE="display:none;">
						<Input class=common name=CALCODEMONEY value="0">
					</TD>

					<TD class=title5 STYLE="display:none;">
�˻�ת������־
					</TD>
					<TD class=input5 STYLE="display:none;">
						<Input class="codeno" name=CALFLAG value="0" readonly="readonly"
							ondblclick="return showCodeList('pd_calflag',[this,CALFLAGName],[0,1]);"
							onkeyup="return showCodeListKey('pd_calflag',[this,CALFLAGName],[0,1]);">
						<input class=codename value="��ȫת���˻�" name=CALFLAGName
							readonly="readonly">
					</TD>
				</tr>
				<tr class=common>
					<TD class=title5 STYLE="display:none;">
�˻�����ת��λ��
					</TD>
					<TD class=input5 STYLE="display:none;">
						<Input class="codeno" name=PAYNEEDTOACC value="9" readonly="readonly"
							ondblclick="return showCodeList('pd_zeroone',[this,PAYNEEDTOACCName],[0,1]);"
							onkeyup="return showCodeListKey('pd_zeroone',[this,PAYNEEDTOACCName],[0,1]);">
						<input class=codename value="1" name=PAYNEEDTOACCName
							readonly="readonly">
					</TD>

					<TD class=title5 STYLE="display:none;">
						�����ʻ�������
					</TD>
					<TD class=input5 STYLE="display:none;">
						<Input type=hidden name=RISKACCPAYNAME>
					</TD>
					<TD class=title5 STYLE="display:none;">
�Ƿ�������
					</TD>
					<TD class=input5 STYLE="display:none;">
						<Input class="codeno" name=ASCRIPTION readonly="readonly"
							ondblclick="return showCodeList('pd_zeroone',[this,ASCRIPTIONName],[0,1]);"
							onkeyup="return showCodeListKey('pd_zeroone',[this,ASCRIPTIONName],[0,1]);">
						<input class=codename name=ASCRIPTIONName readonly="readonly">
					</TD>
				</tr>
				<tr class=common>


				</tr>
			</table>
			
			<hr>

			<div align=left id=savabuttonid1>
				<input value="��  ��" type=button onclick="initDetail()"
					class="cssButton" type="button">
				<input value="��  ��" type=button onclick="save()" class="cssButton"
					type="button">
				<input value="��  ��" type=button onclick="update()" class="cssButton"
					type="button">
				<input value="ɾ  ��" type=button onclick="del()" class="cssButton"
					type="button">
				<input value="��  ��" type=button onclick="returnParent( )"
					class="cssButton" type="button">
			</div>
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1>
						<span id="spanMulline9Grid"> </span>
					</td>
				</tr>
			</table>
			<!--INPUT CLASS=cssbutton VALUE="��  ҳ" TYPE=button onclick="Mulline9GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline9GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline9GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="β  ҳ" TYPE=button onclick="Mulline9GridTurnPage.lastPage();"-->
			</BR>
			</BR>


			<br>
			<br>
			<table>
				<tr>
					<td class="titleImg">
�ѱ���Ľɷ�/����/�˻�����
					</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1>
						<span id="spanMulline12Grid"> </span>
					</td>
				</tr>
			</table>
			<INPUT CLASS=cssbutton VALUE="��  ҳ" TYPE=button
				onclick="Mulline12GridTurnPage.firstPage();">
			<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button
				onclick="Mulline12GridTurnPage.previousPage();">
			<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button
				onclick="Mulline12GridTurnPage.nextPage();">
			<INPUT CLASS=cssbutton VALUE="β  ҳ" TYPE=button
				onclick="Mulline12GridTurnPage.lastPage();">
			</BR>
			</BR>
			<table>
				<tr>
					<td class="titleImg">
�ɷ�/����/�˻���������
					</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<TD class=title5>
�ɷѱ���
					</TD>
					<TD class=input5>
						<Input class="codeno" name=PayPlanCode000 readonly="readonly"
							ondblclick="return showCodeList('pd_payplancode',[this,PayPlanCodeName000],[0,1],null,'<%=mRiskCode%>','<%=mRiskCode%>',1);"
							onkeyup="return showCodeListKey('pd_payplancode',[this,PayPlanCodeName000],[0,1],null,'<%=mRiskCode%>','<%=mRiskCode%>',1);"><input class=codename name=PayPlanCodeName000 readonly="readonly">
					</TD>
					<TD class=title5>
�˻�����
					</TD>
					<TD class=input5>
						<Input class="codeno" name=AccountType000 readonly="readonly"
							ondblclick="return showCodeList('pd_accounttype',[this,AccountTypeName000],[0,1]);"
							onkeyup="return showCodeListKey('pd_accounttype',[this,AccountTypeName000],[0,1]);"><input class=codename name=AccountTypeName000 readonly="readonly">
					</TD>
					<TD class=title5>
�ɷ�/����/�˻�����
					</TD>
					<TD class=input5>
						<Input class=common name=AccountCode000>
					</TD>
					</tr>
					<tr class=common>
						<TD class=title5>
�ɷ�/����/�˻�����
						</TD>
						<TD class=input5>
							<input class=common name=AccountCodeName000>
						</TD>
						<TD class=title5>
						</TD>
						<TD class=input5>
						</TD>
						<TD class=title5>
						</TD>
						<TD class=input5>
						</TD>
					</tr>
			</table>
			<br />
			<hr>
			<div align=left id=savabuttonid2>
				<input value="��  ��" type=button onclick="reset()"  class="cssButton" type="button">
				<input value="��  ��" type=button onclick="hand('save')" class="cssButton" type="button">
				<input value="��  ��" type=button onclick="hand('update')"  class="cssButton" type="button">
				<input value="ɾ  ��" type=button onclick="hand('delete')"  class="cssButton" type="button">
				<input value="��  ��" type=button onclick="returnParent( )" class="cssButton" type="button">
			</div>
			<input type=hidden name="operator">
			<input type=hidden name="tableName" value="PD_LMRiskAccPay">
			<input type=hidden name=IsReadOnly>
		</form>

		<span id="spanCode" style="display: none; position: absolute;"></span>
	</body>
</html>
