<DIV id=DivPageHead STYLE="display: ''">
	<%@page contentType="text/html;charset=gb2312"%>
	<%@page import="java.util.*"%>
	<%@page import="com.sinosoft.utility.*"%>
	<%@page import="com.sinosoft.lis.schema.*"%>
	<%@page import="com.sinosoft.lis.vschema.*"%>
	<%@page import="com.sinosoft.lis.tb.*"%>
	<%@page import="com.sinosoft.lis.pubfun.*"%>
	<%@page import="com.sinosoft.lis.cbcheck.*"%>
	<%@page import="com.sinosoft.lis.vbl.*"%>
	<%@include file="../common/jsp/Log4jUI.jsp"%>
	<%@page import="com.sinosoft.productdef.*"%>
	<%@page import="com.sinosoft.service.*"%>
	<%
		String CurrentDate = PubFun.getCurrentDate();
		Date dt = PubFun.calDate(new FDate().getDate(CurrentDate), 1, "D",
				null);
		String ValidDate = CurrentDate;
		/////��ò���-���ֱ���
		String riskcode = request.getParameter("riskcode");
		String queryCode = "!InsuYear-" + riskcode + "*0&0";
		String queryCode1 = "!PayIntv-" + riskcode + "*0&0";
		String queryCode2 = "!PayEndYear-" + riskcode + "*0&0";
		String queryCode3 = "!GetYear-" + riskcode + "*0&0";
		String queryCode4 = "!Getdutykind-" + riskcode + "*0&0";
		String queryCode5 = "!BonusGetMode-" + riskcode + "*0&0";

		String queryCode6 = "!Currency-" + riskcode + "*0&0";
		//////////////////////////////////
		String queryCodeApp = "";

		ExeSQL rexeSql = new ExeSQL();
		SSRS riskFlagSSRS = new SSRS();
		String sql = "select subriskflag from lmriskapp where riskcode='"
				+ riskcode + "'";
		TransferData sTransferData = new TransferData();
		sTransferData.setNameAndValue("SQL", sql);
		VData sVData = new VData();
		sVData.add(sTransferData);
		BusinessDelegate tBusinessDelegate = BusinessDelegate
				.getBusinessDelegate();
		if (tBusinessDelegate.submitData(sVData, "execSQL", "ExeSQLUI")) {
			riskFlagSSRS = (SSRS) tBusinessDelegate.getResult()
					.getObjectByObjectName("SSRS", 0);
		}
		loggerDebug("Risk111301", "ף���ձ��=====" + riskFlagSSRS.GetText(1, 1));
		if (riskFlagSSRS.GetText(1, 1).equals("M")) {
			queryCodeApp = "***-" + riskcode;
			session.putValue("MainRiskNo", riskcode);
		} else {
			queryCodeApp = "***-" + (String) session.getValue("MainRiskNo");
		}

		String tRiskCode = request.getParameter("riskcode");
		//String tStandbyFlag1 = request.getParameter("StandbyFlag1");
		tRiskCode = "" + tRiskCode + "";
	%>
	<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script src="../common/javascript/jquery.js"></script>
<script src="../common/javascript/jquery.ui.core.js"></script>
<script src="../common/javascript/jquery.ui.widget.js"></script>
<script src="../common/javascript/jquery.ui.mouse.js"></script>
<script src="../common/javascript/jquery.ui.draggable.js"></script>
<script src="../common/javascript/jquery.ui.sortable.js"></script>
<script src="../common/javascript/jquery.easyui.js"></script>
<link rel="stylesheet" type="text/css"
	href="../common/themes/default/easyui.css">
<link rel="stylesheet" href="../demos.css">
<SCRIPT src="RiskProductdef.js"></SCRIPT>
<style>
.divRisk ul {width =100%;
	list-style-type: none;
	margin: 0;
	padding: 0;
	margin-bottom: 10px;
}

//
.demo li {
	margin: 5px;
	padding: 5px;
	width: 150px;
}

.divRisk li {
	float: left;
	width: 30%;
	margin-left: 10px;
	display: inline;
	height: 30px;
	background: #F7F7F7;
	margin-top: 10px;
}
</style>
<script language="JavaScript">
	var jRiskCode = '<%=tRiskCode%>';
	var jStandbyFlag1 = '01';
	var ProposalContNo="<%=request.getParameter("ProposalContNo")%>";
</script>

</head>

</DIV>
<body>
<form action="./ProposalSave.jsp" method=post name=fm id=fm target="fraTitle">

	<DIV id=DivRiskCode STYLE="display: ''">
		<TABLE class=common>
			<TR CLASS=common>
				<TD CLASS=title>���ֱ���</TD>
				<TD CLASS=input COLSPAN=1><Input class=codeno name=RiskCode id=RiskCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
 					ondblclick="showCodeList('<%=queryCodeApp%>',[this,RiskCodeName],[0,1],null,null,null,null,'400');"
					onkeyup="return showCodeListKey('<%=queryCodeApp%>',[this,RiskCodeName],[0,1],null,null,null,null,'400');">
					<input
					class=codename name=RiskCodeName id=RiskCodeName readonly=true
					elementtype=nacessary></TD>
					<TD ></TD>
					<TD ></TD>
					<TD ></TD>
					<TD ></TD>
			</TR>

		</TABLE>
	</DIV>

	<DIV id=DivRiskHidden STYLE="display: none">
		<TABLE class=common>

			<TR CLASS=common>
				<TD CLASS=title>���ձ�������</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=MainPolNo id=MainPolNo class="wid"></TD>
				<TD ></TD>
					<TD ></TD>
					<TD ></TD>
					<TD ></TD>
			</TR>

		</TABLE>
	</DIV>

	<DIV id=DivLCPolHidden STYLE="display: none">
		<TABLE class=common>

			<TR CLASS=common>
				<TD CLASS=title>���ְ汾</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=RiskVersion id=RiskVersion  class="wid"></TD>
				<TD CLASS=title>��ͬ��</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=ContNo111 id=ContNo111 class="wid"></TD>
				<TD CLASS=title>�����ͬ����</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=GrpContNo id=GrpContNo class="wid"></TD>
			</TR>

			<TR CLASS=common>
				<TD CLASS=title>���ڽ�������</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=FirstPayDate id=FirstPayDate  class="wid"></TD>
				<TD CLASS=title>����</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=Lang id=Lang  class="wid"></TD>
				<TD CLASS=title>��������</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=Currency id=Currency  class="wid"></TD>
			</TR>

			<TR CLASS=common>
				<TD CLASS=title>��ͬ���鴦��ʽ</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=DisputedFlag id=DisputedFlag class="wid"></TD>
				<TD CLASS=title>���д��ձ��</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AgentPayFlag id=DisputedFlag class="wid"></TD>
				<TD CLASS=title>���д������</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AgentGetFlag id=AgentGetFlag class="wid"></TD>
			</TR>

			<TR CLASS=common>
				<TD CLASS=title>������</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=Handler id=Handler class="wid"></TD>
				<TD CLASS=title>���ϴ����˱���</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AgentCode1 id=AgentCode1 class="wid"></TD>
				<TD ></TD>
				<TD ></TD>
			</TR>

		</TABLE>
	</DIV>

	<DIV id=DivLCPolButton STYLE="display: none">
		<!-- ������Ϣ���� -->
		<table>
			<tr>
				<td class=common>
				 <IMG src="../common/images/butExpand.gif"
					style="cursor: hand;" OnClick="showPage(this,DivLCPol);"></td>
				<td class=titleImg>������Ϣ 
				<INPUT VALUE="��ѯ������Ϣ" class=cssButton TYPE=button
					onclick="showDuty();"> 
					<INPUT VALUE="�����ݽ�����Ϣ" class=cssButton TYPE=button
					onclick="showFee();"> <!--<INPUT id="butChooseDuty" VALUE="ѡ������" TYPE=button onclick="chooseDuty();" disabled >
<INPUT id="butBack" VALUE="����" TYPE=button disabled >--></td>
			</tr>
		</table>

	</DIV>

	<DIV id=DivLCPol STYLE="display: none">
		<TABLE class=common>

			<TR CLASS=common>
				<TD CLASS=title>Ͷ��������</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=ProposalNo id=ProposalNo VALUE=""
					CLASS="readonly wid" readonly TABINDEX=-1 MAXLENGTH=11></TD>
				<TD CLASS=title>ӡˢ����</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=PrtNo id=PrtNo VALUE=""
					CLASS="readonly wid" readonly TABINDEX=-1 MAXLENGTH=11
					verify="ӡˢ����|notnull&len=11"></TD>
				<TD CLASS=title>�������</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=ManageCom id=ManageCom VALUE=""
					MAXLENGTH=10 CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
					ondblclick="return showCodeList('comcode',[this],null,null,'#1# and char_length(trim(comcode))=8','1',1);"
					onkeyup="return showCodeListKey('comcode',[this],null,null,'#1# and char_length(trim(comcode))=8','1',1);"
					verify="�������|code:station&notnull"></TD>
			</TR>

			<TR CLASS=common>
				<TD CLASS=title>��������</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=SaleChnl id=SaleChnl VALUE=""
					CLASS="common wid" MAXLENGTH=2></TD>
				<TD CLASS=title>�����˱���</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AgentCode id=AgentCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" VALUE=""
					MAXLENGTH=10 CLASS=code ondblclick="return queryAgent();"
					onkeyup="return queryAgent2();"></TD>
				<TD CLASS=title>���������</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AgentGroup id=AgentGroup style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  VALUE=""
					CLASS="readonly wid" readonly TABINDEX=-1 MAXLENGTH=12
					verify="���������|notnull"></TD>
			</TR>

			<TR CLASS=common>
				<TD CLASS=title>��ע</TD>
				<TD CLASS=input><Input NAME=Remark id=Remark VALUE="" CLASS="common5 wid"
					MAXLENGTH=255></TD>
				<TD CLASS=title>��ͬ����</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=ContNo id=ContNo VALUE=""
					CLASS="readonly wid" readonly TABINDEX=-1 MAXLENGTH=14></TD>
					<TD ></TD>
					<TD ></TD>

			</TR>

			<TR CLASS=common>
				<TD CLASS=title>�������</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AgentCom id=AgentCom VALUE=""
					CLASS=code
					ondblclick="showCodeList('AgentCom',[this],null,null,'#1# and ManageCom like #' + fm.all('ManageCom').value.substring(1,4) + '%# and #' + fm.all('ManageCom').value + '# is not null  ','1');"
					onkeyup="return showCodeListKey('AgentCom',[this],null,null,'#1# and ManageCom like #' + fm.all('ManageCom').value.substring(1,4) + '%# and #' + fm.all('ManageCom').value + '# is not null  ','1');"
					verify="�������|code:AgentCom"></TD>
				<TD CLASS=title>����Ӫҵ����</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AgentType id=AgentType VALUE=""
					CLASS="common wid"></TD>
			</TR>

		</TABLE>
	</DIV>

	<DIV id=DivLCAppntIndButton STYLE="display: none">
		<!-- Ͷ������Ϣ���� -->
		<table>
			<tr class=common> 
				<td class=common><IMG src="../common/images/butExpand.gif"
					style="cursor: hand;" OnClick="showPage(this,DivLCAppntInd);">
				</td>
				<td class=titleImg>Ͷ������Ϣ���ͻ��ţ�<Input class=common
					name=AppntCustomerNo> <INPUT id="butBack" class=cssButton VALUE="��ѯ"
					TYPE=button onclick="queryAppntNo();"> �״�Ͷ���ͻ�������д�ͻ��ţ�
				</td>
			</tr>
		</table>

	</DIV>

	<DIV id=DivLCAppntInd STYLE="display: none" class=maxbox>
		<TABLE class=common>

			<TR CLASS=common>
				<TD CLASS=title>����</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AppntName id=AppntName VALUE=""
					CLASS="common wid" MAXLENGTH=20 verify="Ͷ��������|notnull"></TD>
				<TD CLASS=title>�Ա�</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AppntSex id=AppntSex VALUE=""
					MAXLENGTH=1 CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
					ondblclick="return showCodeList('Sex', [this]);"
					onkeyup="return showCodeListKey('Sex', [this]);"
					verify="Ͷ�����Ա�|notnull&code:Sex"></TD>
				<TD CLASS=title>��������</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AppntBirthday id=AppntBirthday VALUE=""
					CLASS="coolDatePicker" verify="Ͷ���˳�������|notnull&date"  onClick="laydate({elem: '#AppntBirthday'});" id="AppntBirthday"><span class="icon"><a onClick="laydate({elem: '#AppntBirthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

					</TD>
			</TR>

			<TR CLASS=common>
				<TD CLASS=title>�뱻���˹�ϵ</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AppntRelationToInsured id=AppntRelationToInsured
					VALUE="" MAXLENGTH=2 CLASS=code style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('Relation', [this]);"
					onkeyup="return showCodeListKey('Relation', [this]);"
					verify="Ͷ�����뱻�����˹�ϵ|code:Relation&notnull"></TD>
				<TD CLASS=title>֤������</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AppntIDType id=AppntIDType VALUE=""
					MAXLENGTH=1 CLASS=code style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('IDType', [this]);"
					onkeyup="return showCodeListKey('IDType', [this]);"
					verify="Ͷ����֤������|code:IDType"></TD>
				<TD CLASS=title>֤������</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AppntIDNo id=AppntIDNo VALUE=""
					CLASS="common wid" MAXLENGTH=20></TD>
			</TR>

			<TR CLASS=common>
				<TD CLASS=title>����</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AppntNativePlace id=AppntNativePlace VALUE=""
					CLASS=code style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('NativePlace', [this]);"
					onkeyup="return showCodeListKey('NativePlace', [this]);"
					verify="Ͷ���˹���|code:NativePlace"></TD>
				<TD CLASS=title>�������ڵ�</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AppntRgtAddress id=AppntRgtAddress VALUE=""
					CLASS="common wid" MAXLENGTH=80></TD>
				<TD><B></B></TD>
				<TD COLSPAN=1></TD>
			</TR>

			<TR CLASS=common>
				<TD CLASS=title>ͨѶ��ַ</TD>
				<TD CLASS=input COLSPAN=3><Input NAME=AppntPostalAddress id=AppntPostalAddress
					VALUE="" CLASS="common3 wid" MAXLENGTH=80></TD>
				<TD CLASS=title>ͨѶ��ַ��������</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AppntZipCode VALUE=""
					CLASS="common wid" MAXLENGTH=6 verify="Ͷ������������|zipcode"></TD>
			</TR>

			<TR CLASS=common>
				<TD CLASS=title>סַ</TD>
				<TD CLASS=input COLSPAN=3><Input NAME=AppntHomeAddress id=AppntHomeAddress VALUE=""
					CLASS="common3 wid" MAXLENGTH=80></TD>
				<TD CLASS=title>סַ��������</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AppntHomeZipCode id=AppntHomeZipCode VALUE=""
					CLASS="common wid" MAXLENGTH=6 verify="Ͷ����סַ��������|zipcode"></TD>
			</TR>

			<TR CLASS=common>
				<TD CLASS=title>��ϵ�绰��1��</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AppntPhone id=AppntPhone VALUE=""
					CLASS="common wid" MAXLENGTH=18></TD>
				<TD CLASS=title>��ϵ�绰��2��</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AppntPhone2 id=AppntPhone2 VALUE=""
					CLASS="common wid" MAXLENGTH=18></TD>
				<TD CLASS=title>�ƶ��绰</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AppntMobile id=AppntMobile VALUE=""
					CLASS="common wid" MAXLENGTH=15></TD>
			</TR>

			<TR CLASS=common>
				<TD CLASS=title>��������</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AppntEMail id=AppntEMail VALUE=""
					CLASS="common wid" MAXLENGTH=60></TD>
				<TD CLASS=title>������λ</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AppntGrpName id=AppntGrpName VALUE=""
					CLASS="common wid" MAXLENGTH=60></TD>
				<TD CLASS=title>ְҵ�����֣�</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AppntWorkType id=AppntWorkType VALUE=""
					CLASS="common wid"></TD>
			</TR>

			<TR CLASS=common>
				<TD CLASS=title>��ְ�����֣�</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AppntPluralityType id=AppntPluralityType
					VALUE="" CLASS="common wid"></TD>
				<TD CLASS=title>ְҵ����</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AppntOccupationCode id=AppntOccupationCode
					VALUE="" CLASS=code style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
					ondblclick="return showCodeList('OccupationCode', [this,AppntOccupationType],[0,2]);"
					onkeyup="return showCodeListKey('OccupationCode', [this,AppntOccupationType],[0,2]);"
					verify="Ͷ����ְҵ����|code:OccupationCode"></TD>
				<TD CLASS=title>ְҵ���</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AppntOccupationType id=AppntOccupationType 
					VALUE="" CLASS="readonly wid" readonly TABINDEX=-1
					verify="������ְҵ���|code:OccupationType"></TD>
			</TR>

			<TR CLASS=common>
			</TR>

		</TABLE>
	</DIV>

	<DIV id=DivLCAppntGrpButton STYLE="display: none">
		<!-- ����Ͷ������Ϣ���� -->
		<table>
			<tr>
				<td class=common><IMG src="../common/images/butExpand.gif"
					style="cursor: hand;" OnClick="showPage(this,DivLCAppntGrp);">
				</td>
				<td class=titleImg>Ͷ����λ����</td>
			</tr>
		</table>

	</DIV>

	<DIV id=DivLCAppntGrp STYLE="display: none" class=maxbox>
		<TABLE class=common>

			<TR CLASS=common>
				<TD CLASS=title>��λ�ͻ���</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AppGrpNo id=AppGrpNo class="wid"></TD>
				<TD CLASS=title>��λ����</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AppGrpName id=AppGrpName  class="wid"></TD>
				<TD CLASS=title>��λ��ַ</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AppGrpAddress id=AppGrpAddress  class="wid"></TD>
			</TR>

			<TR CLASS=common>
				<TD CLASS=title>��������</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AppGrpZipCode id=AppGrpZipCode  class="wid"></TD>
				<TD CLASS=title>��λ����</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=GrpNature id=GrpNature  class="wid"></TD>
				<TD CLASS=title>��ҵ���</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=BusinessType id=BusinessType  class="wid"></TD>
			</TR>

			<TR CLASS=common>
				<TD CLASS=title>��λ������</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=Peoples id=Peoples class="wid"></TD>
				<TD CLASS=title>��Ӫҵ��</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=MainBussiness id=MainBussiness class="wid"></TD>
				<TD CLASS=title>��λ���˴���</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=Corporation id=Corporation class="wid"></TD>
			</TR>

			<TR CLASS=common>
				<TD><B>������ϵ��һ</B></TD>
				<TD COLSPAN=1></TD>
				<TD><B></B></TD>
				<TD COLSPAN=1></TD>
				<TD><B></B></TD>
				<TD COLSPAN=1></TD>
			</TR>

			<TR CLASS=common>
				<TD CLASS=title>����</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=LinkMan1 id=LinkMan1 class="wid"></TD>
				<TD CLASS=title>����</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=Department1 id=Department1 class="wid"></TD>
				<TD CLASS=title>��ϵ�绰</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=GrpPhone1 id=GrpPhone1 class="wid"></TD>
			</TR>

			<TR CLASS=common>
				<TD CLASS=title>��ϵ�绰</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=GrpPhone1 id=GrpPhone1 class="wid"></TD>
				<TD CLASS=title>E_mail</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=E_Mail1 id=E_Mail1 class="wid"></TD>
				<TD CLASS=title>����</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=Fax1 id=Fax1 class="wid"></TD>
			</TR>

			<TR CLASS=common>
				<TD><B>������ϵ�˶�</B></TD>
				<TD COLSPAN=1></TD>
				<TD><B></B></TD>
				<TD COLSPAN=1></TD>
				<TD><B></B></TD>
				<TD COLSPAN=1></TD>
			</TR>

			<TR CLASS=common>
				<TD CLASS=title>����</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=LinkMan2 id=LinkMan2 class="wid"></TD>
				<TD CLASS=title>����</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=Department2  id=Department2 class="wid"></TD>
				<TD CLASS=title>��ϵ�绰</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=GrpPhone2 id=GrpPhone2 class="wid"></TD>
			</TR>

			<TR CLASS=common>
				<TD CLASS=title>E_mail</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=E_Mail2 id=E_Mail2 class="wid"></TD>
				<TD CLASS=title>����</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=Fax2 id=Fax2 class="wid"></TD>
				<TD><B></B></TD>
				<TD COLSPAN=1></TD>
			</TR>

			<TR CLASS=common>
				<TD CLASS=title>���ʽ</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=GetFlag id=GetFlag class="wid"></TD>
				<TD CLASS=title>��������</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=GrpBankCode id=GrpBankCode class="wid"></TD>
				<TD CLASS=title>�����ʺ�</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=GrpBankAccNo id=GrpBankAccNo class="wid"></TD>
			</TR>

			<TR CLASS=common>
				<TD CLASS=title>��������</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=Currency id=Currency  class="wid"></TD>
			</TR>

		</TABLE>
	</DIV>

	<DIV id=DivLCInsuredButton STYLE="display: none">
		<!-- ��������Ϣ���� -->
		<table class=common>
			<tr >
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAllNotice);">
				</td>

				<td class=titleImg>��������Ϣ���ͻ��ţ�
				<Input class=common name=CustomerNo id=CustomerNo>  
				<INPUT id="butBack" VALUE="��ѯ" TYPE=button class=cssButton 
					onclick="queryInsuredNo();"> �״�Ͷ���ͻ�������д�ͻ��ţ�
					
				</td>
				<td class=titleImg>
				<Div id="divSamePerson" style="display: ''">
						<font color=red> ��Ͷ����Ϊ�������˱��ˣ������������ѡ�� 
						<INPUT
							TYPE="checkbox" NAME="SamePersonFlag" id=SamePersonFlag onclick="isSamePerson();">
						</font>
				</div>
				</td>
				
			</tr>
		</table>

	</DIV>

	<DIV id=DivLCInsured STYLE="display: none" class=maxbox>
		<TABLE class=common>

			<TR CLASS=common>
				<TD CLASS=title>����</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=Name id=Name VALUE=""
					CLASS="common wid" MAXLENGTH=20 verify="����|notnull"></TD>
				<TD CLASS=title>�Ա�</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=Sex id=Sex VALUE="" MAXLENGTH=1
					CLASS=code style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('Sex', [this]);"
					onkeyup="return showCodeListKey('Sex', [this]);"
					verify="�������Ա�|notnull&code:Sex"></TD>
				<TD CLASS=title>��������</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=Birthday VALUE=""
					CLASS=coolDatePicker verify="�����˳�������|date&notnull" onClick="laydate({elem: '#Birthday'});" id="Birthday"><span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
					</TD>
			</TR>

			<TR CLASS=common>
				<TD CLASS=title>֤������</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=IDType VALUE=""
					MAXLENGTH=1 CLASS=code style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('IDType', [this]);"
					onkeyup="return showCodeListKey('IDType', [this]);"
					verify="������֤������|code:IDType"></TD>
				<TD CLASS=title>֤������</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=IDNo id=IDNo VALUE=""
					CLASS="common wid" MAXLENGTH=20></TD>
				<TD CLASS=title>����</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=NativePlace id=NativePlace style="background:url(../common/images/select--bg_03.png) no-repeat right center" VALUE=""
					CLASS=code ondblclick="return showCodeList('NativePlace', [this]);"
					onkeyup="return showCodeListKey('NativePlace', [this]);"
					verify="�����˹���|code:NativePlace"></TD>
			</TR>

			<TR CLASS=common>
				<TD CLASS=title>�������ڵ�</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=RgtAddress id=RgtAddress VALUE=""
					CLASS="common wid" MAXLENGTH=80></TD>
				<TD CLASS=title>סַ</TD>
				<TD CLASS=input COLSPAN=3><Input NAME=HomeAddress id=HomeAddress VALUE=""
					CLASS="common3 wid" MAXLENGTH=80></TD>
			</TR>

			<TR CLASS=common>
				<TD CLASS=title>��������</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=HomeZipCode id=HomeZipCode VALUE=""
					CLASS="common wid" MAXLENGTH=6 verify="��������������|zipcode"></TD>
				<TD CLASS=title>��ϵ�绰��1��</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=Phone id=Phone VALUE=""
					CLASS="common wid" MAXLENGTH=18></TD>
				<TD CLASS=title>��ϵ�绰��2��</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=Phone2 id=Phone2 VALUE=""
					CLASS="common wid"></TD>
			</TR>

			<TR CLASS=common>
				<TD CLASS=title>������λ</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=GrpName id=GrpName VALUE=""
					CLASS="common wid" MAXLENGTH=60></TD>
				<TD CLASS=title>ְҵ�����֣�</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=WorkType id=WorkType VALUE=""
					CLASS="common wid"></TD>
				<TD CLASS=title>��ְ�����֣�</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=PluralityType id=PluralityType VALUE=""
					CLASS="common wid"></TD>
			</TR>

			<TR CLASS=common>
				<TD CLASS=title>ְҵ����</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=OccupationCode id=OccupationCode VALUE=""
					CLASS=code style="background:url(../common/images/select--bg_03.png) no-repeat right center"

					ondblclick="return showCodeList('OccupationCode', [this,OccupationType],[0,2]);"
					onkeyup="return showCodeListKey('OccupationCode', [this,OccupationType],[0,2]);"
					verify="������ְҵ����|code:OccupationCode"></TD>
				<TD CLASS=title>ְҵ���</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=OccupationType VALUE=""
					CLASS="readonly wid" readonly TABINDEX=-1
					verify="������ְҵ���|code:OccupationType"></TD>
			</TR>

		</TABLE>
	</DIV>

	<DIV id=DivLCInsuredHidden STYLE="display: none">
		<TABLE class=common>

			<TR CLASS=common>
				<TD CLASS=title>����״��</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=Health id=Health class="wid"></TD>
				<TD ></TD>
				<TD ></TD>
				<TD ></TD>
				<TD ></TD>
			</TR>

		</TABLE>
	</DIV>
	<!-- ������¼��֧�� -->
	<div id="divMultMainRisk" style="display: none">
		<table>
			<tr>
				<td class=common><IMG src="../common/images/butExpand.gif"
					style="cursor: hand;" OnClick="showPage(this,divMultMainRisk2);">
				</td>
				<td class=titleImg>�ѱ���������Ϣ</td>
			</tr>
		</table>
		<div id="divMultMainRisk2" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanMultMainRiskGrid"></span></td>
				</tr>
			</table>
		</div>
	</div>
	<!-- -->
	<DIV id=DivLCBnf STYLE="display: ''">
		<!-- ��������Ϣ���֣��б� -->
		<table id='divLCBnf1Main' style="display: ''">
			<tr>
				<td class=common><IMG src="../common/images/butExpand.gif"
					style="cursor: hand;" OnClick="showPage(this,divLCBnf1);"></td>
				<td class=titleImg>��������Ϣ</td>
			</tr>
		</table>

		<Div id="divLCBnf1" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanBnfGrid">
					</span></td>
				</tr>
			</table>
		</div>

	</DIV>

	<DIV id=DivLCKindButton STYLE="display: ''">
		<!-- ������Ϣ���� -->
		<table>
			<tr>
				<td class=common><IMG src="../common/images/butExpand.gif"
					style="cursor: hand;" OnClick="showPage(this,divRisk);"></td>
				<td class=titleImg>������Ϣ</td>
			</tr>
		</table>

	</DIV>


	<!--����-->
	<div class='divRisk'></div>
	<hr class=line>
	<!--����-->
	<DIV id="DivLCRiskDuty">
		<Table>
			<TR>
				<TD class=common><IMG src='../common/images/butExpand.gif'
					style='cursor: hand;'></TD>
				<TD class=titleImg>��ѡ������Ϣ</TD>
			</TR>
		</Table>
	</DIV>
	<div id="divDuty" style="height: auto">
		<table id='dutyGrid1'>
		</table>
	</div>
	<hr class=line>
	<!--�ɷ�-->
	<DIV id="DivLCRiskPay" >
		<Table>
			<TR>
				<TD class=common><IMG src='../common/images/butExpand.gif'
					style='cursor: hand;'></TD>
				<TD class=titleImg>��ѡ�ɷ���Ϣ</TD>
			</TR>
		</Table>
	</DIV>
	<div id="divPay">
		<table id="payGrid">
		</table>
	</div>
<Div id= "divDutyGrid" style= "display: none">
	<table  class= common>
		<tr  class= common>
			<td text-align: left colSpan=1>
				<span id="spanDutyGrid" ></span>
			</td>
		</tr>
	</table>
</div>

<Div  id= "divPremGrid" style= "display: none">
	<table  class= common>
		<tr  class= common>
			<td text-align: left colSpan=1>
				<span id="spanPremGrid" ></span>
			</td>
		</tr>
	</table>
</div>

	<DIV id=DivLCKindHidden STYLE="display: none">
		<TABLE class=common>
			<TR CLASS=common>
				<TD CLASS=title>�Զ��潻��־</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=AutoPayFlag id=AutoPayFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center" VALUE=""
					CLASS=code CodeData="0|^0|���Զ��潻^1|�Զ��潻"
					ondblClick="showCodeListEx('AutoPayFlag',[this],[0]);"
					onkeyup="showCodeListKeyEx('AutoPayFlag',[this],[0]);"
					verify="�Զ��潻��־|notnull"></TD>
			</TR>

			<TR CLASS=common>
				<TD CLASS=title>������Ч����</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=CValiDate id=CValiDate 
					VALUE="<%=ValidDate%>" CLASS="coolDatePicker" verify="������Ч����|notnull&date" onClick="laydate({elem: '#StartDate'});" id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</TD>
			<TR CLASS=common>
			</TR>

			<TR CLASS=common>

				<TD CLASS=title>�������ȡ��ʽ</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=LiveGetMode id=LiveGetMode VALUE=""
					CLASS="readonly wid" readonly TABINDEX=-1></TD>
				<TD CLASS=title>��ȡ��ʽ</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=getIntv id=getIntv class="wid"></TD>


				<TD CLASS=title>�������ڼ�������</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=GetStartType id=GetStartType class="wid"></TD>

			</TR>

			<TR CLASS=common>
				<TD CLASS=title>�������ʽ</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=InterestDifFlag id=InterestDifFlag  class="wid"></TD>
				<TD CLASS=title>������־</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=SubFlag id=SubFlag  class="wid"></TD>
				<TD ></TD><TD ></TD>
			</TR>

			<TR CLASS=common>
				<TD CLASS=title>�������ȡ��ʽ</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=LiveGetMode id=LiveGetMode class="wid"></TD>
				<TD CLASS=title>�Ƿ��Զ�����</TD>
				<TD CLASS=input COLSPAN=1><Input NAME=RnewFlag id=RnewFlag class="wid"></TD>
				<TD ></TD>
				<TD ></TD>
			</TR>

		</TABLE>
	</DIV>

	<DIV id=DivLCSubInsured STYLE="display: none">
		<!-- ������������Ϣ���֣��б� -->
		<Div id="divLCInsured0" style="display: none">
			<table>
				<tr>
					<td class=common><IMG src="../common/images/butExpand.gif"
						style="cursor: hand;" OnClick="showPage(this,divLCInsured2);">
					</td>
					<td class=titleImg>������������Ϣ</td>
				</tr>
			</table>

			<Div id="divLCInsured2" style="display: ''">
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1><span id="spanSubInsuredGrid">
						</span></td>
					</tr>
				</table>
			</div>

		</div>

	</DIV>

	<!--��ӵ�-->
	<DIV id=DivInsured STYLE="display: none">
		<!-- ������������Ϣ���֣��б� -->
		<Div id="divLCInsured0" style="display: none">
			<table>
				<tr>
					<td class=common><IMG src="../common/images/butExpand.gif"
						style="cursor: hand;" OnClick="showPage(this,divInsured1);"></td>
					<td class=titleImg>��������Ϣ</td>
				</tr>
			</table>

			<Div id="divInsured" style="display: ''">
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1><span id="spanInsuredGrid">
						</span></td>
					</tr>
				</table>
			</div>

		</div>

	</DIV>
	<!--���Ϻ�ӵ�-->

	<DIV id=DivLCImpart STYLE="display: none">
		<!-- ��֪��Ϣ���֣��б� -->
		<table>
			<tr>
				<td class=common><IMG src="../common/images/butExpand.gif"
					style="cursor: hand;" OnClick="showPage(this,divLCImpart1);"></td>
				<td class=titleImg>��֪��Ϣ</td>
			</tr>
		</table>

		<Div id="divLCImpart1" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanImpartGrid">
					</span></td>
				</tr>
			</table>
		</div>

	</DIV>

	<DIV id=DivLCSpec STYLE="display: ''">
		<!-- ��Լ��Ϣ���֣��б� -->
		<table>
			<tr>
				<td class=common><IMG src="../common/images/butExpand.gif"
					style="cursor: hand;" OnClick="showPage(this,divLCSpec1);"></td>
				<td class=titleImg>��Լ��Ϣ</td>
			</tr>
		</table>

		<Div id="divLCSpec1" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanSpecGrid">
					</span></td>
				</tr>
			</table>
		</div>

	</DIV>


	<DIV id=DivChooseDiscountGrid STYLE="display: ''">
		<table>
			<tr>
				<td class=common><IMG src="../common/images/butExpand.gif"
					style="cursor: hand;" OnClick="showPage(this,divDiscount2);"></td>
				<td class=titleImg>��ѡ�ۿ���Ϣ</td>
			</tr>
		</table>
		<Div id="divDiscount2" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanDiscountGrid">
					</span></td>
				</tr>
			</table>
		</div>
	</DIV>




	<DIV id=DivChooseDuty STYLE="display: none">
		<!--����ѡ������β��֣��ò���ʼ������-->
		<Div id="divChooseDuty0" style="display: none">
			<table>
				<tr>
					<td class=common><IMG src="../common/images/butExpand.gif"
						style="cursor: hand;" OnClick="showPage(this,divDutyGrid1);"></td>
					<td class=titleImg>��ѡ������Ϣ</td>
				</tr>
			</table>
		</div>

	</DIV>

	<DIV id=DivPageEnd STYLE="display: ''">
		<input type=hidden id="inpNeedDutyGrid" name="inpNeedDutyGrid"
			value="0"> <input type=hidden id="fmAction" name="fmAction">
		<input type=hidden id="ContType" name="ContType" value=""> <input
			type="hidden" class="Common wid" name=SelPolNo id=SelPolNo value=""> <input
			type=hidden id="inpNeedPremGrid" name="inpNeedPremGrid" value="0">
		<input type='hidden' name=InputTime id=InputTime value="">
		<!--��Ҫ�����ֽ�������-->
		<input type='hidden' id=ProposalContNo name="ProposalContNo" value="<%=request.getParameter("ProposalContNo")%>"> <input
			type='hidden' name="WorkFlowFlag" id=WorkFlowFlag value=""> <input
			type='hidden' name="MissionID" id=MissionID value=""> <input type='hidden'
			name="SubMissionID" id=SubMissionID value=""> <input type='hidden'
			name="AppntNo" value="">
		<!--end-->
		<Div id="divButton" style="display: ''">
			<br>
			<%@include file="../common/jsp/ProposalOperateButton.jsp"%>
		</DIV>
</form>

<span id="spanCode" style="display: none; position: absolute;"></span>
<span id="spanApprove" style="display: none; position: relative;"></span>
<br/><br/><br/><br/>
</body>
</html>

<script>
	function returnParent() {
		var isDia = 0;
		var haveMenu = 0;
		var callerWindowObj;

		try {
			callerWindowObj = dialogArguments;
			isDia = 1;
		} catch (ex1) {
			isDia = 0;
		}

		try {
			if (isDia == 0) { //����Ǵ�һ���µĴ��ڣ���ִ������Ĵ���
				top.opener.parent.document.body.innerHTML = window.document.body.innerHTML;
			} else { //�����һ��ģ̬�Ի������������Ĵ���
				callerWindowObj.document.body.innerHTML = window.document.body.innerHTML;
				haveMenu = 1;
				callerWindowObj.parent.frames("fraMenu").Ldd = 0;
				callerWindowObj.parent.frames("fraMenu").Go();
			}
		} catch (ex) {
			if (haveMenu != 1) {
				alert("Riskxxx.jsp:��������" + ex.name);
			}
		}

		top.close();
	}

	returnParent();
</script>
</DIV>





