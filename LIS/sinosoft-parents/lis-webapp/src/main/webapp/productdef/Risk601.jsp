<%@include file="../i18n/language.jsp"%>
<!--
 * <p>FileName: \Risk111398.jsp </p>
 * <p>Description: ���ֽ����ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Author��Minim's ProposalInterfaceMaker
 * @CreateDate��2003-12-30
-->

<DIV id=DivPageHead STYLE="display:''">
<%@page contentType="text/html;charset=GBK" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>

<body>
<form action="./ProposalSave.jsp" method=post name=fm target="fraTitle">

</DIV>

<DIV id=DivRiskCode STYLE="display:none;">
<TABLE class=common>

  <TR CLASS=common>
    <TD class=title5>
���ֱ���
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=RiskCode VALUE="" MAXLENGTH=6 CLASS=code ondblclick="if(loadFlag=='1')  showCodeList('RiskInd',[this]);" onkeyup="return showCodeListKey('RiskInd',[this]);" verify="���ֱ���|code:RiskCode" >
    </TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivRiskHidden STYLE="display:none;">
<TABLE class=common>

  <TR CLASS=common>
    <TD class=title5>
���ձ�������
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=MainPolNo>
    </TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCPolHidden STYLE="display:none;">
<TABLE class=common>

  <TR CLASS=common>
    <TD class=title5>
���ְ汾
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=RiskVersion>
    </TD>
    <TD class=title5>
��ͬ��
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=ContNo111>
    </TD>
    <TD class=title5>
�����ͬ����
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=GrpContNo>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
���ڽ�������
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=FirstPayDate>
    </TD>
    <TD class=title5>
����
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Lang>
    </TD>
    <TD class=title5>
��������
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Currency>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
�������鴦��ʽ
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=DisputedFlag>
    </TD>
    <TD class=title5>
���д��ձ��
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AgentPayFlag>
    </TD>
    <TD class=title5>
���д������
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AgentGetFlag>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
������
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Handler>
    </TD>
    <TD class=title5>
���ϴ����˱���
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AgentCode1>
    </TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCPolButton STYLE="display:none;">
<!-- ������Ϣ���� -->
<table>
<tr>
<td>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCPol);">
</td>
<td class= titleImg>
������Ϣ
<INPUT VALUE="��ѯ������Ϣ" TYPE=button onclick="showDuty();">
<INPUT VALUE="�����ݽ�����Ϣ" TYPE=button onclick="showFee();">
<!--<INPUT id="butChooseDuty" VALUE="ѡ������" TYPE=button onclick="chooseDuty();" disabled >
<INPUT id="butBack" VALUE="����" TYPE=button disabled >-->
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCPol STYLE="display:none;">
<TABLE class=common>

  <TR CLASS=common>
    <TD class=title5>
Ͷ��������</TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=ProposalNo VALUE="" CLASS=readonly readonly="readonly" TABINDEX=-1 MAXLENGTH=11 >
    </TD>
    <TD class=title5>
ӡˢ��
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=PrtNo VALUE="" CLASS=readonly readonly="readonly" TABINDEX=-1 MAXLENGTH=11 verify="ӡˢ��|notnull&len=11" >
    </TD>
    <TD class=title5>
�������
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=ManageCom VALUE="" MAXLENGTH=10 CLASS=code ondblclick="return showCodeList('comcode',[this],null,null,'#1# and Length(trim(comcode))=8','1',1);" onkeyup="return showCodeListKey('comcode',[this],null,null,'#1# and Length(trim(comcode))=8','1',1);" verify="�������|code:station&notnull" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
��������
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=SaleChnl VALUE="" CLASS=common MAXLENGTH=2 >
    </TD>
    <TD class=title5>
�����˱���
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AgentCode VALUE="" MAXLENGTH=10 CLASS=code ondblclick="return queryAgent();"onkeyup="return queryAgent2();" >
    </TD>
    <TD class=title5>
���������
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AgentGroup VALUE="" CLASS=readonly readonly="readonly" TABINDEX=-1 MAXLENGTH=12 verify="���������|notnull" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
��ע
    </TD>
    <TD class=input5>
      <Input NAME=Remark VALUE="" CLASS=common5 MAXLENGTH=255 >
    </TD>
    <TD class=title5>
��ͬ����
    </TD>
    <TD class=input5 COLSPAN=1>
     <Input NAME=ContNo VALUE="" CLASS=readonly readonly="readonly" TABINDEX=-1 MAXLENGTH=14  >
    </TD>
 
  </TR>

  <TR CLASS=common>
    <TD class=title5>
�������
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AgentCom VALUE="" CLASS=code ondblclick="showCodeList('AgentCom',[this],null,null,'#1# and ManageCom like #' + fm.all('ManageCom').value.substring(0,4) + '%# and #' + fm.all('ManageCom').value + '# is not null  ','1');" onkeyup="return showCodeListKey('AgentCom',[this],null,null,'#1# and ManageCom like #' + fm.all('ManageCom').value.substring(0,4) + '%# and #' + fm.all('ManageCom').value + '# is not null  ','1');" verify="�������|code:AgentCom" >
    </TD>
    <TD class=title5>
����Ӫҵ����
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AgentType VALUE="" CLASS=common >
    </TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCAppntIndButton STYLE="display:none;">
<!-- Ͷ������Ϣ���� -->
<table>
<tr>
<td>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);">
</td>
<td class= titleImg>
Ͷ������Ϣ���ͻ��ţ�<Input class= common  name=AppntCustomerNo >
<INPUT id="butBack" VALUE="��ѯ" TYPE=button onclick="queryAppntNo();">
�״�Ͷ���ͻ�������д�ͻ��ţ�
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCAppntInd STYLE="display:none;">
<TABLE class=common>

  <TR CLASS=common>
    <TD class=title5>
����
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntName VALUE="" CLASS=common MAXLENGTH=20 verify="Ͷ��������|notnull" >
    </TD>
    <TD class=title5>
�Ա�
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntSex VALUE="" MAXLENGTH=1 CLASS=code ondblclick="return showCodeList('Sex', [this]);" onkeyup="return showCodeListKey('Sex', [this]);" verify="Ͷ�����Ա�|notnull&code:Sex" >
    </TD>
    <TD class=title5>
��������
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntBirthday VALUE="" CLASS=common verify="Ͷ���˳�������|notnull&date" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
�뱻���˹�ϵ
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntRelationToInsured VALUE="" MAXLENGTH=2 CLASS=code ondblclick="return showCodeList('Relation', [this]);" onkeyup="return showCodeListKey('Relation', [this]);" verify="Ͷ�����뱻�����˹�ϵ|code:Relation&notnull" >
    </TD>
    <TD class=title5>
֤������
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntIDType VALUE="" MAXLENGTH=1 CLASS=code ondblclick="return showCodeList('IDType', [this]);" onkeyup="return showCodeListKey('IDType', [this]);" verify="Ͷ����֤������|code:IDType" >
    </TD>
    <TD class=title5>
֤������
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntIDNo VALUE="" CLASS=common MAXLENGTH=20 >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
����
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntNativePlace VALUE="" CLASS=code ondblclick="return showCodeList('NativePlace', [this]);" onkeyup="return showCodeListKey('NativePlace', [this]);" verify="Ͷ���˹���|code:NativePlace" >
    </TD>
    <TD class=title5>
�������ڵ�
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntRgtAddress VALUE="" CLASS=common MAXLENGTH=80 >
    </TD>
    <TD>
      <B></B>
    </TD>
    <TD COLSPAN=1>

    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
ͨѶ��ַ
    </TD>
    <TD class=input5 COLSPAN=3>
      <Input NAME=AppntPostalAddress VALUE="" CLASS=common3 MAXLENGTH=80 >
    </TD>
    <TD class=title5>
ͨѶ��ַ��������
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntZipCode VALUE="" CLASS=common MAXLENGTH=6 verify="Ͷ������������|zipcode" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
סַ
    </TD>
    <TD class=input5 COLSPAN=3>
      <Input NAME=AppntHomeAddress VALUE="" CLASS=common3 MAXLENGTH=80 >
    </TD>
    <TD class=title5>
��ͥ��ַ��������
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntHomeZipCode VALUE="" CLASS=common MAXLENGTH=6 verify="Ͷ����סַ��������|zipcode" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
��ϵ�绰��1��
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntPhone VALUE="" CLASS=common MAXLENGTH=18 >
    </TD>
    <TD class=title5>
��ϵ�绰��2��
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntPhone2 VALUE="" CLASS=common MAXLENGTH=18 >
    </TD>
    <TD class=title5>
�ƶ��绰
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntMobile VALUE="" CLASS=common MAXLENGTH=15 >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
��������
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntEMail VALUE="" CLASS=common MAXLENGTH=60 >
    </TD>
    <TD class=title5>
������λ
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntGrpName VALUE="" CLASS=common MAXLENGTH=60 >
    </TD>
    <TD class=title5>
ְҵ�����֣�
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntWorkType VALUE="" CLASS=common >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
��ְ�����֣�
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntPluralityType VALUE="" CLASS=common >
    </TD>
    <TD class=title5>
ְҵ����
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntOccupationCode VALUE="" CLASS=code ondblclick="return showCodeList('OccupationCode', [this,AppntOccupationType],[0,2]);" onkeyup="return showCodeListKey('OccupationCode', [this,AppntOccupationType],[0,2]);" verify="Ͷ����ְҵ����|code:OccupationCode" >
    </TD>
    <TD class=title5>
ְҵ���
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntOccupationType VALUE="" CLASS=readonly readonly="readonly" TABINDEX=-1 verify="������ְҵ���|code:OccupationType" >
    </TD>
  </TR>

  <TR CLASS=common>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCAppntGrpButton STYLE="display:none;">
<!-- ����Ͷ������Ϣ���� -->
<table>
<tr>
<td>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntGrp);">
</td>
<td class= titleImg>
Ͷ����λ����
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCAppntGrp STYLE="display:none;">
<TABLE class=common>

  <TR CLASS=common>
    <TD class=title5>
��λ�ͻ���
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppGrpNo>
    </TD>
    <TD class=title5>
��λ����
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppGrpName>
    </TD>
    <TD class=title5>
��λ��ַ
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppGrpAddress>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
��������
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppGrpZipCode>
    </TD>
    <TD class=title5>
��λ����
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=GrpNature>
    </TD>
    <TD class=title5>
��ҵ���
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=BusinessType>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
��λ������
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Peoples>
    </TD>
    <TD class=title5>
��Ӫҵ��
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=MainBussiness>
    </TD>
    <TD class=title5>
��λ���˴���
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Corporation>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD>
      <B>������ϵ��һ</B>
    </TD>
    <TD COLSPAN=1>

    </TD>
    <TD>
      <B></B>
    </TD>
    <TD COLSPAN=1>

    </TD>
    <TD>
      <B></B>
    </TD>
    <TD COLSPAN=1>

    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
����
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=LinkMan1>
    </TD>
    <TD class=title5>
����
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Department1>
    </TD>
    <TD class=title5>
��ϵ�绰
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=GrpPhone1>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
��ϵ�绰
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=GrpPhone1>
    </TD>
    <TD class=title5>
      E_mail
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=E_Mail1>
    </TD>
    <TD class=title5>
����
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Fax1>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD>
      <B>������ϵ�˶�</B>
    </TD>
    <TD COLSPAN=1>

    </TD>
    <TD>
      <B></B>
    </TD>
    <TD COLSPAN=1>

    </TD>
    <TD>
      <B></B>
    </TD>
    <TD COLSPAN=1>

    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
����
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=LinkMan2>
    </TD>
    <TD class=title5>
����
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Department2>
    </TD>
    <TD class=title5>
��ϵ�绰
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=GrpPhone2>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
      E_mail
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=E_Mail2>
    </TD>
    <TD class=title5>
����
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Fax2>
    </TD>
    <TD>
      <B></B>
    </TD>
    <TD COLSPAN=1>

    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
���ʽ
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=GetFlag>
    </TD>
    <TD class=title5>
��������
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=GrpBankCode>
    </TD>
    <TD class=title5>
�����ʺ�
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=GrpBankAccNo>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
��������
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Currency>
    </TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCInsuredButton STYLE="display:none;">
<!-- ��������Ϣ���� -->
<table>
<tr>
<td class=common>
<!--<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsured);">-->
</td>
<td class= titleImg>
��������Ϣ���ͻ��ţ�<Input class= common name=CustomerNo >
<INPUT id="butBack" VALUE="��ѯ" TYPE=button onclick="queryInsuredNo();">�״�Ͷ���ͻ�������д�ͻ��ţ�
<Div  id= "divSamePerson" style= "display: ''">
<font color=red>
��Ͷ����Ϊ�������˱��ˣ������������ѡ��
<INPUT TYPE="checkbox" NAME="SamePersonFlag" onclick="isSamePerson();">
</font>
</div>
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCInsured STYLE="display:none;">
<TABLE class=common>

  <TR CLASS=common>
    <TD class=title5>
����
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Name VALUE="" CLASS=common MAXLENGTH=20 verify="����|notnull" >
    </TD>
    <TD class=title5>
�Ա�
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Sex VALUE="" MAXLENGTH=1 CLASS=code ondblclick="return showCodeList('Sex', [this]);" onkeyup="return showCodeListKey('Sex', [this]);" verify="�������Ա�|notnull&code:Sex" >
    </TD>
    <TD class=title5>
��������
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Birthday VALUE="" CLASS=common verify="�����˳�������|date&notnull" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
֤������
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=IDType VALUE="" MAXLENGTH=1 CLASS=code ondblclick="return showCodeList('IDType', [this]);" onkeyup="return showCodeListKey('IDType', [this]);" verify="������֤������|code:IDType" >
    </TD>
    <TD class=title5>
֤������
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=IDNo VALUE="" CLASS=common MAXLENGTH=20 >
    </TD>
    <TD class=title5>
����
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=NativePlace VALUE="" CLASS=code ondblclick="return showCodeList('NativePlace', [this]);" onkeyup="return showCodeListKey('NativePlace', [this]);" verify="�����˹���|code:NativePlace" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
�������ڵ�
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=RgtAddress VALUE="" CLASS=common MAXLENGTH=80 >
    </TD>
    <TD class=title5>
סַ
    </TD>
    <TD class=input5 COLSPAN=3>
      <Input NAME=HomeAddress VALUE="" CLASS=common3 MAXLENGTH=80 >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
��������
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=HomeZipCode VALUE="" CLASS=common MAXLENGTH=6 verify="��������������|zipcode" >
    </TD>
    <TD class=title5>
��ϵ�绰��1��
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Phone VALUE="" CLASS=common MAXLENGTH=18 >
    </TD>
    <TD class=title5>
��ϵ�绰��2��
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Phone2 VALUE="" CLASS=common >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
������λ
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=GrpName VALUE="" CLASS=common MAXLENGTH=60 >
    </TD>
    <TD class=title5>
ְҵ�����֣�
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=WorkType VALUE="" CLASS=common >
    </TD>
    <TD class=title5>
��ְ�����֣�
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=PluralityType VALUE="" CLASS=common >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
ְҵ����
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=OccupationCode VALUE="" CLASS=code ondblclick="return showCodeList('OccupationCode', [this,OccupationType],[0,2]);" onkeyup="return showCodeListKey('OccupationCode', [this,OccupationType],[0,2]);" verify="������ְҵ����|code:OccupationCode" >
    </TD>
    <TD class=title5>
ְҵ���
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=OccupationType VALUE="" CLASS=readonly readonly="readonly" TABINDEX=-1 verify="������ְҵ���|code:OccupationType" >
    </TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCInsuredHidden STYLE="display:none;">
<TABLE class=common>

  <TR CLASS=common>
    <TD class=title5>
����״��
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Health>
    </TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCBnf STYLE="display:''">
<!-- ��������Ϣ���֣��б� -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCBnf1);">
</td>
<td class= titleImg>
��������Ϣ
</td>
</tr>
</table>

<Div  id= "divLCBnf1" style= "display: ''">
<table  class= common>
<tr  class= common>
<td style="text-align:left;" colSpan=1>
<span id="spanBnfGrid" >
</span>
</td>
</tr>
</table>
</div>

</DIV>

<DIV id=DivLCKindButton STYLE="display:''">
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

<DIV id=DivLCKind STYLE="display:''">
<TABLE class=common>

  <TR CLASS=common>
    <TD class=title5>
Ͷ����������
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=CValiDate VALUE="" CLASS=common verify="������Ч����|notnull&date" >
    </TD>
    <!--TD class=title5>
      �Ƿ�ָ����Ч���� 
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=SpecifyValiDate VALUE="N" MAXLENGTH=1 CLASS=code ondblclick="return showCodeList('YesNo', [this]);" onkeyup="return showCodeListKey('YesNo', [this]);" verify="�Ƿ�ָ����Ч��|code:YesNo" >
    </TD>
    <TD class=title5>
      �շѷ�ʽ
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=PayLocation VALUE="" MAXLENGTH=1 CLASS=code ondblclick="return showCodeList('PayLocation', [this]);" onkeyup="return showCodeListKey('PayLocation', [this]);" verify="�շѷ�ʽ|code:PayLocation" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
      ������ 
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=BankCode VALUE="" MAXLENGTH=10 CLASS=code ondblclick="return showCodeList('bank', [this]);" onkeyup="return showCodeListKey('bank', [this]);" verify="������|code:bank" >
    </TD>
    <TD class=title5>
      ���� 
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AccName VALUE="" CLASS=common MAXLENGTH=20 >
    </TD>
    <TD class=title5>
      �����ʺ� 
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=BankAccNo VALUE="" CLASS=common MAXLENGTH=40 >
    </TD>
  </TR>

  <TR CLASS=common-->
   	
    <TD class=title5>
����</TD>
    <TD class=input5 COLSPAN=1>
      <Input class=common NAME=Prem VALUE="" MAXLENGTH=12 >
    </TD>
    <TD class=title5>
����
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input CLASS=common NAME=Amnt VALUE="" MAXLENGTH=12 >
    </TD>
      
  </TR>
<tr>
<TD class=title5>
�����ڼ�
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=InsuYear VALUE="" CLASS=code CodeData="0|^10|ʮ��|Y^15|ʮ����|Y^20|��ʮ��|Y^30|��ʮ��|Y" ondblClick="showCodeListEx('InsuYear60199',[this,InsuYearFlag,PayEndYear,PayEndYearFlag],[0,2,0,2]);" onkeyup="showCodeListKeyEx('InsuYear60199',[this,InsuYearFlag,PayEndYear,PayEndYearFlag],[0,2,0,2]);" verify="�����ڼ�|notnull" >
    </TD>
    <TD class=title5>
�ս��ڼ䵥λ
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=InsuYearFlag VALUE=""  CLASS=readonly readonly="readonly" >
    </TD>
   <TD class=title5>
���ѷ�ʽ
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input  Name=PayIntv VALUE="" CLASS=code CodeData="0|^0|����|1000|Y^12|�꽻||Y" ondblClick="showCodeListEx('PayIntv60199',[this,PayEndYear,PayEndYearFlag],[0,2,3]);" onkeyup="showCodeListKeyEx('PayIntv60199',[this,PayEndYear,PayEndYearFlag],[0,2,3]);" verify="�����ڼ�|not null"  >
    </TD>  
</tr>
  <!--TR CLASS=common>
    <TD class=title5>
      �����ڼ�
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=InsuYear VALUE="" CLASS=code CodeData="0|^10|ʮ��|Y^15|ʮ����|Y^20|-��ʮ��|Y^30|-��ʮ��|Y" ondblClick="showCodeListEx('PayEndYear1459',[this,InsuYearFlag],[0,2]);" onkeyup="showCodeListKeyEx('PayEndYear1459',[this,InsuYearFlag],[0,2]);" verify="�����ڼ�|not null"  >
    </TD>
    <TD class=title5>
      �����ڼ䵥λ 
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=InsuYearFlag VALUE="" CLASS=readonly readonly="readonly"  TABINDEX=-1 verify="�ս��ڼ䵥λ|notnull"  >
    </TD>
    <TD class=title5>
      ������־ 
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=SubFlag VALUE="" CLASS=code ondblclick="return showCodeList('YesNo', [this]);" onkeyup="return showCodeListKey('YesNo', [this]);" >
    </TD>
  </TR-->
 <TR CLASS=common>
   
    <TD class=title5>
�����ڼ�
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=PayEndYear VALUE="" CLASS=readonly readonly="readonly" >
    </TD>
  <TD class=title5>
�����ڼ䵥λ
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=PayEndYearFlag VALUE="" CLASS=readonly readonly="readonly" >
    </TD>
  </TR>
  
</TABLE>
</DIV>

<DIV id=DivLCKindHidden STYLE="display:none;">
<TABLE class=common>

  <TR CLASS=common>
    <TD class=title5>
      StandbyFlag1
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=StandbyFlag1>
    </TD>
    
  </TR>

  <TR CLASS=common>
    <TD class=title5>
������ʽ
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=GetDutyKind>
    </TD>
    <TD class=title5>
��ȡ��ʽ
    </TD>
    <!--TD class=input5 COLSPAN=1>
      <Input NAME=getIntv>
    </TD-->
    <TD class=title5>
�����ڼ�
    </TD>
    <!--TD class=input5 COLSPAN=1>
      <Input NAME=GetYear>
    </TD-->
  </TR>

  <TR CLASS=common>
    <TD class=title5>
�����ڼ䵥λ
    </TD>
    <!--TD class=input5 COLSPAN=1>
      <Input NAME=GetYearFlag>
    </TD-->
    <TD class=title5>
�������ڼ�������
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=GetStartType>
    </TD>
    <TD class=title5>
�Զ��潻��־
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AutoPayFlag>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
�������ʽ
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=InterestDifFlag>
    </TD>
    <TD class=title5>
������־
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=SubFlag>
    </TD>
    <TD class=title5>
������ȡ��ʽ
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=BonusGetMode>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
�������ȡ��ʽ
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=LiveGetMode>
    </TD>
    <TD class=title5>
�Ƿ��Զ�����
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=RnewFlag>
    </TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCSubInsured STYLE="display:none;">
<!-- ������������Ϣ���֣��б� -->
<Div  id= "divLCInsured0" style= "display:none;">
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

<Div  id= "divLCInsured2" style= "display: ''">
<table  class= common>
<tr  class= common>
<td style="text-align:left;" colSpan=1>
<span id="spanSubInsuredGrid" >
</span>
</td>
</tr>
</table>
</div>

</div>

</DIV>

<DIV id=DivLCImpart STYLE="display:none;">
<!-- ��֪��Ϣ���֣��б� -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);">
</td>
<td class= titleImg>
��֪��Ϣ
</td>
</tr>
</table>

<Div  id= "divLCImpart1" style= "display: ''">
<table  class= common>
<tr  class= common>
<td style="text-align:left;" colSpan=1>
<span id="spanImpartGrid" >
</span>
</td>
</tr>
</table>
</div>

</DIV>

<DIV id=DivLCSpec STYLE="display:''">
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

<Div  id= "divLCSpec1" style= "display: ''">
<table  class= common>
<tr  class= common>
<td style="text-align:left;" colSpan=1>
<span id="spanSpecGrid">
</span>
</td>
</tr>
</table>
</div>

</DIV>

<DIV id=DivChooseDuty STYLE="display:none;">
<!--����ѡ������β��֣��ò���ʼ������-->
<Div  id= "divChooseDuty0" style= "display:none;">
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divDutyGrid1);">
</td>
<td class= titleImg>
��ѡ������Ϣ
</td>
</tr>
</table>

<Div  id= "divDutyGrid1" style= "display: ''">
<table  class= common>
<tr  class= common>
<td style="text-align:left;" colSpan=1>
<span id="spanDutyGrid" >
</span>
</td>
</tr>
</table>
</div>
</div>

</DIV>

<DIV id=DivPageEnd STYLE="display:''">
<input type=hidden id="inpNeedDutyGrid" name="inpNeedDutyGrid" value="0">
<input type=hidden id="fmAction" name="fmAction">
<input type=hidden id="ContType" name="ContType" value="">
<input  type= "hidden" class= Common name= SelPolNo value= "">
<input type=hidden id="inpNeedPremGrid" name="inpNeedPremGrid" value="0">

<Div  id= "divButton" style= "display: ''">
<br>
<%@include file="../common/jsp/ProposalOperateButton.jsp"%>
</DIV>

</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<span id="spanApprove"  style="display: none; position:relative; slategray"></span>

</body>
</html>

<script>
function returnParent() {
var isDia=0;
var haveMenu=0;
var callerWindowObj;

try	{
callerWindowObj = dialogArguments;
isDia=1;
}
catch(ex1) {
isDia=0;
}

try	{
if(isDia==0) { //����Ǵ�һ���µĴ��ڣ���ִ������Ĵ���
top.opener.parent.document.body.innerHTML=window.document.body.innerHTML;
}
else { //�����һ��ģ̬�Ի������������Ĵ���
callerWindowObj.document.body.innerHTML=window.document.body.innerHTML;
haveMenu = 1;
callerWindowObj.parent.frames["fraMenu"].Ldd = 0;
callerWindowObj.parent.frames["fraMenu"].Go();
}
}
catch(ex)	{
if( haveMenu != 1 ) {
myAlert("��ʼ���������!"+ex.name);
}
}

top.close();
}

returnParent();
</script>

</DIV>



