<!--
 * <p>FileName: \Risk111302.jsp </p>
 * <p>Description: ���ֽ����ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Author��Minim's ProposalInterfaceMaker
 * @CreateDate��2003-12-30
-->

<DIV id=DivPageHead STYLE="display: ">
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="../riskinput/RiskScreenInit.jsp"%>

</head>

<body  onload="initForm();" >
<form action="./ProposalSave.jsp" method=post name=fm id=fm target="fraSubmit">

</DIV>

<DIV id=DivRiskCode STYLE="display:none">
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      ���ֱ��� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=RiskCode id=RiskCode VALUE="" MAXLENGTH=6 CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
	  ondblclick="showCodeList('RiskInd',[this]);" onkeyup="return showCodeListKey('RiskInd',[this]);" verify="���ֱ���|code:RiskCode" >
    </TD>
  </TR>

</TABLE>
</DIV>



<DIV id=DivLCPolButton STYLE="display: ">
<!-- ������Ϣ���� -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCPol);">
</td>
<td class= titleImg>
������Ϣ
<!--<INPUT VALUE="��ѯ������Ϣ" TYPE=button class= cssButton onclick="showDuty();"-->
<!--INPUT VALUE="�����ݽ�����Ϣ" TYPE=button onclick="showFee();"-->
<!--<INPUT id="butChooseDuty" VALUE="ѡ������" TYPE=button onclick="chooseDuty();" disabled >
<INPUT id="butBack" VALUE="����" TYPE=button disabled >-->
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCPol class=maxbox STYLE="display: ">
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      Ͷ�������� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=ProposalNo id=ProposalNo VALUE="" class="wid common" readonly TABINDEX=-1 MAXLENGTH=20 >
    </TD>
    <TD CLASS=title>
      ӡˢ���� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=PrtNo id=PrtNo VALUE="" class="wid common" readonly TABINDEX=-1 MAXLENGTH=14 verify="ӡˢ����|notnull&len=14" >
    </TD>
    <TD CLASS=title>
      ������� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=ManageCom id=ManageCom VALUE="" MAXLENGTH=10 class="wid common" readonly 
	  style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
	  ondblclick="return showCodeList('comcode',[this],null,null,'#1# and char_length(trim(comcode))=8','1',1);" onkeyup="return showCodeListKey('comcode',[this],null,null,'#1# and char_length(trim(comcode))=8','1',1);" verify="�������|code:station&notnull" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      �������� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=SaleChnl id=SaleChnl VALUE="" class="wid common" readonly MAXLENGTH=2 >
    </TD>
    <TD CLASS=title>
      �����˱��� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AgentCode id=AgentCode VALUE="" MAXLENGTH=10 class="wid common" readonly 
	  style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
	  ondblclick="return queryAgent();"onkeyup="return queryAgent2();" >
    </TD>
    <TD CLASS=title>
      ��������� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AgentGroup id=AgentGroup VALUE="" class="wid common" readonly TABINDEX=-1 MAXLENGTH=12 verify="���������|notnull" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ������� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AgentCom id=AgentCom VALUE="" class="wid common" readonly style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
	  ondblclick="showCodeList('AgentCom',[this],null,null,'#1# and ManageCom like #' + document.all('ManageCom').value.substring(1,4) + '%# and #' + document.all('ManageCom').value + '# is not null  ','1');" onkeyup="return showCodeListKey('AgentCom',[this],null,null,'#1# and ManageCom like #' + document.all('ManageCom').value.substring(1,4) + '%# and #' + document.all('ManageCom').value + '# is not null  ','1');" verify="�������|code:AgentCom" >
    </TD>
    <TD CLASS=title>
      ����Ӫҵ����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AgentType id=AgentType VALUE="" class="wid common" readonly >
    </TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCAppntIndButton STYLE="display:none">
<!-- Ͷ������Ϣ���� -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);">
</td>
<td class= titleImg>
Ͷ������Ϣ(�ͻ���)��<Input class= common  name=AppntCustomerNo >
<INPUT id="butBack" VALUE="��ѯ" TYPE=button class= cssButton onclick="queryAppntNo();">
�״�Ͷ���ͻ�������д�ͻ��ţ�
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCAppntInd class=maxbox STYLE="display:none">
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      ���� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntName id=AppntName VALUE="" class="wid common" MAXLENGTH=20 verify="Ͷ��������|notnull" >
    </TD>
    <TD CLASS=title>
      �Ա� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntSex id=AppntSex VALUE="" MAXLENGTH=1 CLASS=code 
	  style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
	  ondblclick="return showCodeList('Sex', [this]);" onkeyup="return showCodeListKey('Sex', [this]);" verify="Ͷ�����Ա�|notnull&code:Sex" >
    </TD>
    <TD CLASS=title>
      �������� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntBirthday id=AppntBirthday VALUE="" class="wid common" verify="Ͷ���˳�������|notnull&date" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      �뱻���˹�ϵ 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntRelationToInsured id=AppntRelationToInsured VALUE="" MAXLENGTH=2 CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
	  ondblclick="return showCodeList('Relation', [this]);" onkeyup="return showCodeListKey('Relation', [this]);" verify="Ͷ�����뱻�����˹�ϵ|code:Relation&notnull" >
    </TD>
    <TD CLASS=title>
      ֤������ 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntIDType id=AppntIDType VALUE="0" class="wid common" readonly TABINDEX=-1 MAXLENGTH=1 CLASS=code 
	  style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
	  ondblclick="return showCodeList('IDType', [this]);" onkeyup="return showCodeListKey('IDType', [this]);" verify="Ͷ����֤������|code:IDType" >
    </TD>
    <TD CLASS=title>
      ֤������ 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntIDNo id=AppntIDNo VALUE="" CLASS="common wid" MAXLENGTH=20 >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ͨѶ��ַ
    </TD>
    <TD CLASS=input COLSPAN=3>
      <Input NAME=AppntPostalAddress id=AppntPostalAddress id=AppntPostalAddress VALUE="" CLASS="common3" MAXLENGTH=80 >
    </TD>
    <TD CLASS=title>
      ͨѶ��ַ�������� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntZipCode id=AppntZipCode VALUE="" CLASS="common wid" MAXLENGTH=6 verify="Ͷ������������|zipcode" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ��ϵ�绰��1��
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntPhone id=AppntPhone VALUE="" CLASS="common wid" MAXLENGTH=18 >
    </TD>
    <TD CLASS=title>
      ��ϵ�绰��2��
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntPhone2 id=AppntPhone2 VALUE="" CLASS="common wid" MAXLENGTH=18 >
    </TD>
    <TD CLASS=title>
      ������λ
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntGrpName id=AppntGrpName VALUE="" CLASS="common wid" MAXLENGTH=60 >
    </TD>
  </TR>

  <TR CLASS=common>
  </TR>

</TABLE>
</DIV>


<DIV id=DivLCInsuredButton STYLE="display: ">
<!-- ��������Ϣ���� -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butCollapse.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsured);">
</td>
<td class= titleImg>
��������Ϣ���ͻ��ţ�<Input class= readonly readonly name=CustomerNo >��
<Div  id= "divSamePerson" style= "display: none">
<INPUT id="butBack" VALUE="��ѯ" TYPE=button class= cssButton onclick="queryInsuredNo();"> �״�Ͷ���ͻ�������д�ͻ��ţ�
<font color=red>
��Ͷ����Ϊ�������˱��ˣ������������ѡ��
<INPUT TYPE="checkbox" NAME="SamePersonFlag" id=SamePersonFlag onclick="isSamePerson();">
</font>
</div>
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCInsured class=maxbox STYLE="display:none">
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      ���� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Name id=Name VALUE="" class="wid common" readonly MAXLENGTH=20 verify="����|notnull" >
    </TD>
    <TD CLASS=title>
      �Ա� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Sex id=Sex VALUE="" MAXLENGTH=1 class="wid common" readonly ondblclick="return showCodeList('Sex', [this]);" onkeyup="return showCodeListKey('Sex', [this]);" verify="�������Ա�|notnull&code:Sex" >
    </TD>
    <TD CLASS=title>
      �������� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Birthday VALUE="" class="wid common" readonly verify="�����˳�������|date&notnull" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ֤������ 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=IDType VALUE="0" MAXLENGTH=1 class="wid common" readonly ondblclick="return showCodeList('IDType', [this]);" onkeyup="return showCodeListKey('IDType', [this]);" verify="������֤������|code:IDType" >
    </TD>
    <TD CLASS=title>
      ֤������ 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=IDNo VALUE="" class="wid common" readonly MAXLENGTH=20 >
    </TD>
    <TD CLASS=title>
      ��Ͷ���˹�ϵ 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntRelationToInsured VALUE="" MAXLENGTH=2 class="wid common" readonly ondblclick="return showCodeList('Relation', [this]);" onkeyup="return showCodeListKey('Relation', [this]);" verify="Ͷ�����뱻�����˹�ϵ|code:Relation&notnull" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      סַ 
    </TD>
    <TD CLASS=input COLSPAN=3>
      <Input NAME=HomeAddress VALUE="" class="common3" readonly MAXLENGTH=80 >
    </TD>
    <TD CLASS=title>
      �������� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=HomeZipCode VALUE="" class="wid common" readonly MAXLENGTH=6 verify="��������������|zipcode" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ��ϵ�绰��1��
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Phone VALUE="" class="wid common" readonly MAXLENGTH=18 >
    </TD>
    <TD CLASS=title>
      ��ϵ�绰��2��
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Phone2 VALUE="" class="wid common" readonly >
    </TD>
    <TD CLASS=title>
      ������λ
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=GrpName VALUE="" class="wid common" readonly MAXLENGTH=60 >
    </TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCInsuredHidden STYLE="display:none">
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      ����״�� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Health>
    </TD>
  </TR>

</TABLE>
</DIV>
<DIV id=DivLCInsuredNoListButton STYLE="display: ">
<!-- ��������Ϣ���� -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butCollapse.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsuredNoList);">
</td>
<td class= titleImg>
������¼��
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCInsuredNoList class=maxbox STYLE="display:none">
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      *�Ա� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Sex id=Sex VALUE="" MAXLENGTH=1 CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
	  ondblclick="return showCodeList('Sex', [this]);" onkeyup="return showCodeListKey('Sex', [this]);" verify="�������Ա�|notnull&code:Sex" >
    </TD>
    <TD CLASS=title>
      *���� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Age id=Age VALUE="" CLASS="common wid"  >
    </TD>
  </TR>

  
    <TR CLASS=common>
    <TD CLASS=title>
      *ְҵ���� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="code"  NAME=AppntOccupationCode id=AppntOccupationCode>
    </TD>
    <TD CLASS=title>
      ְҵ��� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="readonly wid" readonly NAME=AppntOccupationType id=AppntOccupationType>
    </TD>
    
    <TD CLASS=title>
      �������� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=HomeZipCode id="HomeZipCode" VALUE="" CLASS="common wid" MAXLENGTH=6 verify="��������������|zipcode" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ��ϵ�绰��1��
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Phone id=Phone VALUE="" class="wid common" MAXLENGTH=18 >
    </TD>
    <TD CLASS=title>
      ��ϵ�绰��2��
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Phone2 id=Phone2 VALUE="" class="wid common" >
    </TD>
    <TD CLASS=title>
      ������λ
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=GrpName id=GrpName VALUE="" class="wid common" MAXLENGTH=60 >
    </TD>
  </TR>
</TABLE>
</DIV>



<DIV id=DivLCKindButton STYLE="display: ">
<!-- ������Ϣ���� -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCKind);">
</td>
<td class= titleImg>
������Ϣ
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCKind class=maxbox STYLE="display: ">
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      ���ֱ��� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <!--Input NAME=RiskCode11 VALUE="" MAXLENGTH=6 CLASS=code CodeData="0|^111301|����ذ�|^211601|��������" ondblclick="return showCodeList('RiskCode11',[this]);" onkeyup="return showCodeListKey('RiskCode11',[this]);" -->
        <Input class="code"  CodeData="0|^111301|����ذ�|^211601|��������"  name=RiskCode11 id=RiskCode11
		style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
		ondblClick="showCodeListEx('RiskCode11',[this],[0]);" onkeyup="showCodeListKeyEx('RiskCode11',[this],[0]);" >
    </TD>
	<td class=title></td>
	<td class=input></td>
	<td class=title></td>
	<td class=input></td>
  </TR>

</TABLE>

<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      Ͷ����������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=PolApplyDate id=PolApplyDate VALUE="" class="wid common" >
    </TD>
    <TD CLASS=title>
      Ͷ������Ч����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=CValiDate id=CValiDate VALUE="" class="wid common" verify="������Ч����|notnull&date" >
    </TD>
    <TD CLASS=title>
      ���� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Mult id=Mult VALUE="" class="wid common" verify="����|notnull&value>0&value<4" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ���� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Prem id=Prem VALUE="" class="wid common" MAXLENGTH=12 >
    </TD>
    <TD CLASS=title>
      ���� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Amnt id=Amnt VALUE="" class="wid common" MAXLENGTH=12 >
    </TD>
    <TD CLASS=title>
      ��������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=FloatRate id=FloatRate VALUE="" class="wid common" readonly TABINDEX=-1 verify="��������|Num" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      �Ƿ�ָ����Ч���� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=SpecifyValiDate id=SpecifyValiDate VALUE="Y" MAXLENGTH=1 CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
	  ondblclick="return showCodeList('YesNo', [this]);" onkeyup="return showCodeListKey('YesNo', [this]);" verify="�Ƿ�ָ����Ч��|code:YesNo" >
    </TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCKindHidden STYLE="display:none">
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      �շѷ�ʽ
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=PayLocation id=PayLocation class="common wid">
    </TD>
    <TD CLASS=title>
      ������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=BankCode id=BankCode class="common wid">
    </TD>
    <TD CLASS=title>
      ����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AccName id=AccName class="common wid">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      �����ʺ�
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=�����ʺ� class="common wid">
    </TD>
    <TD CLASS=title>
      �Ƿ�����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=HealthCheckFlag id=HealthCheckFlag class="common wid">
    </TD>
    <TD CLASS=title>
      �����ڼ�
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=InsuYear id=InsuYear class="common wid">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      �����ڼ䵥λ 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=InsuYearFlag id=InsuYearFlag class="common wid">
    </TD>
    <TD CLASS=title>
      �����ڼ�
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=PayEndYear id=PayEndYear class="common wid">
    </TD>
    <TD CLASS=title>
      �ս��ڼ䵥λ
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=PayEndYearFlag id=PayEndYearFlag class="common wid">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ��������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=GetDutyKind id=GetDutyKind class="common wid">
    </TD>
    <TD CLASS=title>
      ���ѷ�ʽ
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=PayIntv id=PayIntv class="common wid">
    </TD>
    <TD CLASS=title>
      ��ȡ��ʽ
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=getIntv id=getIntv class="common wid">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      �����ڼ�
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=GetYear class="common wid">
    </TD>
    <TD CLASS=title>
      �����ڼ䵥λ 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=GetYearFlag class="common wid">
    </TD>
    <TD CLASS=title>
      �������ڼ������� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=GetStartType class="common wid">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      �Զ��潻��־ 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AutoPayFlag class="common wid">
    </TD>
    <TD CLASS=title>
      �������ʽ 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=InterestDifFlag class="common wid">
    </TD>
    <TD CLASS=title>
      ������־ 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=SubFlag class="common wid">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ������ȡ��ʽ 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=BonusGetMode class="common wid">
    </TD>
    <TD CLASS=title>
      �������ȡ��ʽ 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=LiveGetMode class="common wid">
    </TD>
    <TD CLASS=title>
      �Ƿ��Զ�����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=RnewFlag class="common wid">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      �罻���ѷ�ʽ
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=OutPayFlag class="common wid">
    </TD>
  </TR>

</TABLE>
</DIV>

		<input type=hidden id="inpNeedDutyGrid" name="inpNeedDutyGrid" value="0">
		<input type=hidden id="fmAction" name="fmAction">
 </Div>
 
 
<DIV id=DivChooseDuty STYLE="display:none">
<!--����ѡ������β��֣��ò���ʼ������-->
<Div  id= "divChooseDuty0" style= "display:  ">
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divDutyGrid1);">
</td>
<td class= titleImg>
������Ϣ
</td>
</tr>
</table>

<Div  id= "divDutyGrid1" style= "display:  ">
<table  class= common>
<tr  class= common>
<td style=" text-align: left" colSpan=1>
<span id="spanDutyGrid" >
</span>
</td>
</tr>
</table>
</div>
</div>

</DIV>
<DIV id=DivLCPremGrid STYLE="display:none">
 <!-- ��������Ϣ���֣��б� -->
 <table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPremGrid);">
</td>
<td class= titleImg>
��������Ϣ
</td>
</tr>
</table>
<Div  id= "divLCPremGrid" style= "display: none">
    	<table  class= common>
        	<tr  class= common>
    	  		<td style=" text-align: left" colSpan=1>
			<span id="spanPremGrid" ></span> 
			</td>
		</tr>
	</table>
		 					
</div>

<!-- ҪԼ¼�벿�֣��б� -->

	<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCFactor);">
    		</td>
    		<td class= titleImg>
    			 ҪԼ��Ϣ
    		</td>
    	</tr>
       </table>
<Div  id= "divLCFactor" style= "display: none">
    	<table  class= common>
        	<tr  class= common>
    	  		<td style=" text-align: left" colSpan=1>
			<span id="spanFactorGrid" ></span> 
			</td>
		</tr>
	</table>
		 					
</div>
</DIV>
<!-- ��������Ϣ���֣��б� -->
<DIV id=DivLCBnf STYLE="display: ">
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butCollapse.gif" style= "cursor:hand;" OnClick= "showPage(this,divBnfGrid1);">
</td>
<td class= titleImg>
��������Ϣ
</td>
</tr>
</table>

<Div  id= "divBnfGrid1" style= "display: none">
<table  class= common>
<tr  class= common>
<td style=" text-align: left" colSpan=1>
<span id="spanBnfGrid" >
</span>
</td>
</tr>
</table>
</div>
</div>
<DIV id=DivLCSubInsured STYLE="display:none">
<!-- ������������Ϣ���֣��б� -->
<Div  id= "divLCInsured0" style= "display: none">
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsured2);">
</td>
<td class= titleImg>
������������Ϣ
</td>
</tr>
</table>

<Div  id= "divLCInsured2" style= "display:  ">
<table  class= common>
<tr  class= common>
<td style=" text-align: left" colSpan=1>
<span id="spanSubInsuredGrid" >
</span>
</td>
</tr>
</table>
</div>

</div>

</DIV>

<DIV id=DivLCSpec STYLE="display: ">
<!-- ��Լ��Ϣ���֣��б� -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCSpec1);">
</td>
<td class= titleImg>
��Լ��Ϣ
</td>
</tr>
</table>

<Div  id= "divLCSpec1" style= "display:  ">
<table class=common>
   <TR  class= common> 
      <TD  class= title> �ر�Լ��</TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
      <textarea name="GrpSpec" id=GrpSpec cols="120" rows="3" class="common" >
      </textarea></TD>
    </TR>
  </table>  
</DIV>
</div>

</DIV>
<DIV id=DivLCNoti STYLE="display: ">
<!-- ��Լ��Ϣ���֣��б� -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCNoti1);">
</td>
<td class= titleImg>
��ע��Ϣ
</td>
</tr>
</table>

<Div  id= "divLCNoti1" style= "display:  ">
<table class=common>
   <TR  class= common> 
      <TD  class= title> ��ע��Ϣ</TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
      <textarea name="Noti" id=Noti cols="120" rows="3" class="common" >
      </textarea></TD>
    </TR>
  </table>  
</DIV>
</div>


<DIV id=DivPageEnd STYLE="display: ">
<input type=hidden id="inpNeedDutyGrid" name="inpNeedDutyGrid" value="0">
<input type=hidden id="fmAction" name="fmAction">
</Div>
<Div  id= "divButton" style= "display:  ">
<br>
<INPUT VALUE="�� һ ��" TYPE=button class= cssButton onclick="parent.close();">
<INPUT VALUE="����Ͷ����" TYPE=button class= cssButton onclick="alert('����ɹ�');">
</DIV>

</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<span id="spanApprove"  style="display: none; position:relative; slategray"></span>
<br /><br /><br /><br />
</body>
</html>




