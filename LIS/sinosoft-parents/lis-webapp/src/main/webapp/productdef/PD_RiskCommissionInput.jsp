<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
//�������ƣ�LABKRateToRiskInput.jsp
//�����ܣ�
//�������ڣ�
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
		<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
		<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
		<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
		<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
		<%@include file="buttonshow.jsp"%>
		<SCRIPT src="PD_RiskCommission.js"></SCRIPT>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
		<%@include file="PD_RiskCommissionInit.jsp"%>
		<%@include file="../common/jsp/ManageComLimit.jsp"%>
		<title></title>
	</head>
	<%
		GlobalInput tGI1 = new GlobalInput();
		tGI1=(GlobalInput)session.getAttribute("GI");//���ҳ��ؼ��ĳ�ʼ����
		String mRiskCode = request.getParameter("riskcode");
	%>
	<script>
	var mRiskCode = "<%=mRiskCode%>";
 	var comcode = "<%=tGI1.ComCode%>";
 	var tOperator = "<%=tGI1.Operator%>";
	</script>

	<body onload="initForm();initElementtype();">
		<form name=fm action="./PD_RiskCommissionSave.jsp" method=post
			target="fraSubmit">
			<table>
				<tr>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
							OnClick="showPage(this,divLDPerson1);">
					</td>
					<td class=title5Img>
��ѯ����
					</td>
				</tr>
			</table>
			<Div id="divLDPerson1" style="display: ''">
				<table class=common align=center>
					<tr class=common>
						<TD class=title5>
�������
						</TD>
						<TD class=input5>
							<Input class="codeno" name=ManageCom
								ondblclick="showCodeList('ldcom',[this,ManageComName],[0,1],null,null,null,1);"
								onkeyup="showCodeListKey('ldcom',[this,ManageComName],[0,1],null,null,null,1);"
								onFocus="checkAgCom(this.value);"><input class=CodeName name=ManageComName readonly="readonly">
						</TD>
						<td class=title5>
���ֱ���
						</td>
						<TD class=input5>
							<Input class=CodeNo name=RiskCode
								ondblclick="showCodeList('pd_lmriskinfo',[this,RiskName],[0,1],null,null,null,1);"
								onkeyup="showCodeListKey('pd_lmriskinfo',[this,RiskName],[0,1],null,null,null,1);"><Input class=CodeName readonly="readonly" name=RiskName>
						</TD>
						<TD class=title5 STYLE="display:none;">
�������
						</TD>
						<TD class=input5 STYLE="display:none;">
							<Input class="codeno" name=AgentCom verify="���л���|code:bankcom"
								ondblclick="dbclick(this,'bankcom',AgentComName,null,null);"
								onkeyup="keyup(this,'bankcom',AgentComName,null,null);"
								onFocus="showManageCom(this.value);"><input class=codename name=AgentComName readonly="readonly">
							<!-- ondblclick="initEdorType1(this,AgentComName);" onkeyup="actionKeyUp1(this,AgentComName);"><input class=codename name=AgentComName elementtype=nacessary readonly="readonly">-->
						</TD>
					</TR>
					<TR class=common>
						<td class=title5>
��Ч����
						</td>
						<td class=input5>
							<input class="coolDatePicker" dateFormat="short" name=StartDate 
								onClick="laydate({elem: '#StartDate'});" id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
						</td>
					</TR>
				</table>
				<tr>
					<td>
						<INPUT class=cssbutton VALUE="��  ѯ" TYPE=button
							onclick="easyQueryClick();">
					</td>
				</tr>
			</Div>
			<table>
				<tr>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
							OnClick="showPage(this,divAgent1);">
					</td>
					<td class=title5Img>
����������Ϣ
					</td>
				</tr>
			</table>
			<Div id="divAgent1" style="display: ''">
				<table class=common>
					<tr class=common>
						<td style="text-align:left;" colSpan=1>
							<span id="spanRateGrid"> </span>
						</td>
					</tr>
				</table>
				<table>
					<tr>
						<td>
							<INPUT class=cssbutton VALUE="��  ҳ" TYPE=button
								onclick="turnPage.firstPage();">

							<INPUT class=cssbutton VALUE="��һҳ" TYPE=button
								onclick="turnPage.previousPage();">

							<INPUT class=cssbutton VALUE="��һҳ" TYPE=button
								onclick="turnPage.nextPage();">

							<INPUT class=cssbutton VALUE="β  ҳ" TYPE=button
								onclick="turnPage.lastPage();">
						</td>
					</tr>
					<tr>
					</tr>
				</table>
				<table>
<!-- 					<INPUT id=savabutton1 VALUE="�� ��" TYPE="button" class=cssButton
						onclick="addClick();">
					<INPUT id=savabutton2 VALUE="�� ��" TYPE="button" class=cssButton
						onclick="updateClick();">
					<INPUT id=savabutton3 VALUE="ɾ ��" TYPE="button" class=cssButton
						onclick="deleteClick();">
 -->
				</table>
				<input type=hidden name=hideOperate value=''>
				<input type=hidden name=Operator value=''>
				<input type=hidden name=BranchType value=''>
			</div>
			<div id="singleAdjustment" style="display: ''">
				<table>
					<tr>
						<td class=common>
							<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
								OnClick="showPage(this,divAgent2);">
						</td>
						<td class=title5Img>
����������Ϣ
						</td>
					</tr>
				</table>
				<div id="divAgent2" style="display: ''">
					<table class=common align=center>
						<tr class=common>
							<TD class=title5>
�������
							</TD>
							<td class=input5>
								<Input class="codeno" name=ManageOrgan readonly="readonly" 
									ondblclick="return showCodeList('pd_ldcom',[this,ManageOrganName],[0,1]);" 
									onkeyup="return showCodeListKey('pd_ldcom',[this,ManageOrganName],[0,1]);"><input class=codename name=ManageOrganName readonly="readonly"  elementtype=nacessary>
							</td>
							<TD class=title5>
Ӷ�����
							</TD>
							<TD class=input5>
								<Input class="common" name=CommissionRate elementtype=nacessary>
							</TD>
						</tr>
						<tr class=common>
							<td class=title5>
���ֱ���
							</td>
							<TD class=input5>
								<Input class=CodeNo name=Risk
									ondblclick="showCodeList('pd_lmriskinfo',[this,RiskCodeName],[0,1],null,null,null,1);"
									onkeyup="showCodeListKey('pd_lmriskinfo',[this,RiskCodeName],[0,1],null,null,null,1);"><Input class=CodeName readonly="readonly" name=RiskCodeName
									elementtype=nacessary>
							</TD>
							<td class=title5>
�������(��)
							</td>
							<TD class=input5>
								<Input class="common" name=PremPayPeriod  elementtype=nacessary>
							</TD>
						</tr>
						<tr class=common>
							<td class=title5>
��������
							</td>
							<TD class=input5>
								<Input class="common" name=InsureAgeStart>
							</TD>
							<td class=title5>
����ֹ��
							</td>
							<TD class=input5>
								<Input class="common" name=InsureAgeEnd>
							</TD>
						</tr>

						<tr class=common>
							<td class=title5>
��������
							</td>
							<TD class=input5>
								<Input class="common" name=PayToAge>
							</TD>
							<TD class=title5>
����
							</TD>
							<TD class=input5>
								<Input class="codeno" name=Currency
									ondblclick="dbclick(this,'sacurrency',CurrencyName,null,null);"
									onkeyup="keyup(this,'sacurrency',CurrencyName,null,null);"><input class=codename name=CurrencyName
									readonly="readonly">
							</TD>
						</tr>
						<tr class=common>
							<TD class=title5>
Ͷ������
							</TD>
							<TD class=input5>
								<Input class="codeno" name=PayType
									ondblclick="dbclick(this,'partbelong',PayTypeName,null,null);"
									onkeyup="keyup(this,'partbelong',PayTypeName,null,null);"><input class=codename name=PayTypeName readonly="readonly">
							</TD>
							<TD class=title5>
�ɷѷ�ʽ
							</TD>
							<TD class=input5>
								<Input class="codeno" name=PayMode
									ondblclick="dbclick(this,'paymentmode',PayModeName,null,null);"
									onkeyup="keyup(this,'paymentmode',PayModeName,null,null);"><input class=codename name=PayModeName 
									readonly="readonly">
							</TD>
						</tr>
						<tr class=common>
							<TD class=title5>
���ϼƻ�
							</TD>
							<TD class=input5>
								<Input class="codeno" name=ProtectionPlan
									ondblclick="return showCodeList('pd_lmriskinfonew',[this,ProtectionPlanName],[0,1],null,fm.Risk.value,'riskcode',1);"
									onkeyup="return showCodeList('pd_lmriskinfonew',[this,ProtectionPlanName],[0,1],null,fm.Risk.value,'riskcode',1);"><input class=codename name=ProtectionPlanName readonly="readonly">
							</TD>
							<TD class=title5>
��������(��)
							</TD>
							<TD class=input5>
								<Input class="common" name=PayYears >
							</TD>

						</tr>
						<TR class=common>
							
							<td class=title5>
��Ч����
							</td>
							<td class=input5>
								<Input class="multiDatePicker" name=StartDate2 dateFormat='short'
									verify="��Ч����|DATE"   elementtype=nacessary>
							</td>
							<TD class=title5 STYLE="display:none;">
Ա���ۿ���������Ӷ������ͬ���
							</TD>
							<TD class=input5 STYLE="display:none;">
								<Input class=codeno name=SRFlag value="N"
									ondblclick="dbclick(this,'yesorno',SRFlagS,null,null);"
									onkeyup="keyup(this,'yesorno',SRFlagS,null,null);"><Input class=codename name=SRFlagS>
							</TD>
						</TR>
						<TR class=common>
							<TD class=title5 STYLE="display:none;">
Ա���ۿ۱���
							</TD>
							<TD class=input5 STYLE="display:none;">
								<Input class="common" name=STAFFRATE value="0">
							</TD>
							<TD class=title5 STYLE="display:none;">
�������
							</TD>
							<TD class=input5 STYLE="display:none;">
								<Input class="codeno" name=AgentOrgan value="000000"
									ondblclick="dbclick(this,'bankcom',AgentOrganName,null,null);"
									onkeyup="keyup(this,'bankcom',AgentOrganName,null,null);"
									onFocus="showManageOrgan(this.value);" ><input class=codename name=AgentOrganName readonly="readonly"
									elementtype=nacessary>
								<!-- ondblclick="initEdorType1(this,AgentComName);" onkeyup="actionKeyUp1(this,AgentComName);"><input class=codename name=AgentComName elementtype=nacessary readonly="readonly">-->
							</TD>
						</TR>
						<Input type="hidden" name=AppState>
						<Input type="hidden" name=IDNo>
						<Input type="hidden" name=DefaultFlag value='1'>
					</table>
					<table>
						<INPUT VALUE="��  ��" TYPE="button" class=cssButton 
							onclick="addClick();">
						<INPUT VALUE="��  ��" TYPE="button" class=cssButton id=savabutton1
							onclick="saveClick();">
						<INPUT id=savabutton2 VALUE="��  ��" TYPE="button" class=cssButton id=savabutton2
							onclick="updateClick();">
						<INPUT id=savabutton3 VALUE="ɾ  ��" TYPE="button" class=cssButton id=savabutton3
							onclick="deleteClick();">
					</table>
				</div>
			</div>

		</form>
		<span id="spanCode" style="display: none; position: absolute;"></span>
	</body>
</html>
