<!--
 * <p>FileName: \Risk112401.jsp </p>
 * <p>Description: ���ֽ����ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Author��Minim's ProposalInterfaceMaker
 * @CreateDate��2003-12-30
-->

<DIV id=DivPageHead STYLE="display:''">
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.vbl.*"%>
<%String CurrentDate = PubFun.getCurrentDate();
  Date dt = PubFun.calDate(new FDate().getDate(CurrentDate), 1, "D", null);
  String ValidDate = CurrentDate;
/////��ò���-���ֱ���
  String riskcode=request.getParameter("riskcode");
  String queryCode="!InsuYear-"+riskcode+"*0&0";
  String queryCode1="!PayIntv-"+riskcode+"*0&0";
  String queryCode2="!PayEndYear-"+riskcode+"*0&0";
  String queryCode3="!GetYear-"+riskcode+"*0&0";
  String queryCode4="!Getdutykind-"+riskcode+"*0&0";
  String queryCode6="!LiveGetMode-"+riskcode+"*0&0";
  String queryCode7="!InsuYearFlag-"+riskcode+"*0&0";
  String queryCode8="!PayEndYearFlag-"+riskcode+"*0&0";
   //////////////////////////////////
  String queryCodeApp="";
  ExeSQL rexeSql = new ExeSQL();
    SSRS riskFlagSSRS = new SSRS();
    riskFlagSSRS = rexeSql.execSQL("select subriskflag from lmriskapp where riskcode='"+riskcode+"'");
    loggerDebug("RiskTCIB5","ף���ձ��====="+riskFlagSSRS.GetText(1,1));
    if(riskFlagSSRS.GetText(1,1).equals("M"))
    {
      queryCodeApp="***-"+riskcode;
      session.putValue("MainRiskNo",riskcode);
    }else{
      queryCodeApp="***-"+(String)session.getValue("MainRiskNo");
    }
   %>
</DIV>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>

<body>
<form action="./ProposalSave.jsp" method=post name=fm id=fm target="fraTitle">
<DIV id=DivRiskCode class=maxbox1 STYLE="display:''">
<TABLE class=common>
    
  <TR CLASS=common>
    <TD CLASS=title>
      ���ֱ���
    </TD>
    <TD CLASS=input COLSPAN=1>
     <Input class=codeno name=RiskCode id=RiskCode style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="showCodeList('<%=queryCodeApp%>',[this,RiskCodeName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('<%=queryCodeApp%>',[this,RiskCodeName],[0,1],null,null,null,null,'400');"><input class=codename name=RiskCodeName id=RiskCodeName readonly=true elementtype=nacessary>
    </TD>
	<TD CLASS=title></TD>
    <TD CLASS=input></TD>
	<TD CLASS=title></TD>
    <TD CLASS=input></TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivRiskHidden STYLE="display:none">
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      ���ձ�������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=MainPolNo id=MainPolNo>
    </TD>
	<TD CLASS=title></TD>
    <TD CLASS=input></TD>
	<TD CLASS=title></TD>
    <TD CLASS=input></TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCPolHidden STYLE="display:none">
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      ���ְ汾
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=RiskVersion id=RiskVersion> 
    </TD>
    <TD CLASS=title>
      ��ͬ��
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=ContNo111 id=ContNo111>
    </TD>
    <TD CLASS=title>
      �����ͬ����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=GrpContNo id=GrpContNo>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ���ڽ�������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=FirstPayDate id=FirstPayDate>
    </TD>
    <TD CLASS=title>
      ����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=Lang id=Lang>
    </TD>
    <TD CLASS=title>
      ��������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=Currency id=Currency>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ��ͬ���鴦��ʽ
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=DisputedFlag id=DisputedFlag>
    </TD>
    <TD CLASS=title>
      ���д��ձ��
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=AgentPayFlag id=AgentPayFlag>
    </TD>
    <TD CLASS=title>
      ���д������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=AgentGetFlag id=AgentGetFlag>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=Handler id=Handler> 
    </TD>
    <TD CLASS=title>
      ���ϴ����˱���
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=AgentCode1 id=AgentCode1> 
    </TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCPolButton STYLE="display:none">
<!-- ������Ϣ���� -->
<table>
<tr>
<td class=common>
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

<DIV id=DivLCPol class=maxbox1 STYLE="display:none">
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      Ͷ��������</TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=ProposalNo id=ProposalNo VALUE="" CLASS="wid readonly" readonly TABINDEX=-1 MAXLENGTH=11 >
    </TD>
    <TD CLASS=title>
      ӡˢ����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=PrtNo id=PrtNo VALUE="" CLASS="wid readonly" readonly TABINDEX=-1 MAXLENGTH=11 verify="ӡˢ����|notnull&len=11" >
    </TD>
    <TD CLASS=title>
      �������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=ManageCom id=ManageCom VALUE="" MAXLENGTH=10 CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('comcode',[this],null,null,'#1# and Length(trim(comcode))=8','1',1);" onkeyup="return showCodeListKey('comcode',[this],null,null,'#1# and Length(trim(comcode))=8','1',1);" verify="�������|code:station&notnull" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ��������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=SaleChnl id=SaleChnl VALUE="" CLASS="wid common" MAXLENGTH=2 >
    </TD>
    <TD CLASS=title>
      �����˱���
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AgentCode id=AgentCode VALUE="" MAXLENGTH=10 CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return queryAgent();"onkeyup="return queryAgent2();" >
    </TD>
    <TD CLASS=title>
      ���������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AgentGroup id=AgentGroup VALUE="" CLASS="wid readonly" readonly TABINDEX=-1 MAXLENGTH=12 verify="���������|notnull" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ��ע
    </TD>
    <TD CLASS=input>
      <Input NAME=Remark id=Remark VALUE="" CLASS=common5 MAXLENGTH=255 >
    </TD>
    <TD CLASS=title>
      ��ͬ����
    </TD>
    <TD CLASS=input COLSPAN=1>
     <Input NAME=ContNo id=ContNo VALUE="" CLASS="wid readonly" readonly TABINDEX=-1 MAXLENGTH=14  >
    </TD>
	<TD CLASS=title></TD>
    <TD CLASS=input></TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      �������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AgentCom id=AgentCom VALUE="" CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('AgentCom',[this],null,null,'#1# and ManageCom like #' + fm.all('ManageCom').value.substring(0,4) + '%# and #' + fm.all('ManageCom').value + '# is not null  ','1');" onkeyup="return showCodeListKey('AgentCom',[this],null,null,'#1# and ManageCom like #' + fm.all('ManageCom').value.substring(0,4) + '%# and #' + fm.all('ManageCom').value + '# is not null  ','1');" verify="�������|code:AgentCom" >
    </TD>
    <TD CLASS=title>
      ����Ӫҵ����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AgentType id=AgentType VALUE="" CLASS="wid common" >
    </TD>
	<TD CLASS=title></TD>
    <TD CLASS=input></TD>
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
Ͷ������Ϣ���ͻ��ţ�<Input class="wid common"  name=AppntCustomerNo  id=AppntCustomerNo>
<INPUT id="butBack" VALUE="��ѯ" TYPE=button onclick="queryAppntNo();">
�״�Ͷ���ͻ�������д�ͻ��ţ�
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCAppntInd class=maxbox1 STYLE="display:none">
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      ����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntName id=AppntName VALUE="" CLASS="wid common" MAXLENGTH=20 verify="Ͷ��������|notnull" >
    </TD>
    <TD CLASS=title>
      �Ա�
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntSex id=AppntSex VALUE="" MAXLENGTH=1 CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Sex', [this]);" onkeyup="return showCodeListKey('Sex', [this]);" verify="Ͷ�����Ա�|notnull&code:Sex" >
    </TD>
    <TD CLASS=title>
      ��������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntBirthday id=AppntBirthday VALUE="" CLASS="wid common" verify="Ͷ���˳�������|notnull&date" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      �뱻���˹�ϵ
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntRelationToInsured id=AppntRelationToInsured VALUE="" MAXLENGTH=2 CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Relation', [this]);" onkeyup="return showCodeListKey('Relation', [this]);" verify="Ͷ�����뱻�����˹�ϵ|code:Relation&notnull" >
    </TD>
    <TD CLASS=title>
      ֤������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntIDType id=AppntIDType VALUE="" MAXLENGTH=1 CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('IDType', [this]);" onkeyup="return showCodeListKey('IDType', [this]);" verify="Ͷ����֤������|code:IDType" >
    </TD>
    <TD CLASS=title>
      ֤������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntIDNo id=AppntIDNo VALUE="" CLASS="wid common" MAXLENGTH=20 >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntNativePlace id=AppntNativePlace VALUE="" CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('NativePlace', [this]);" onkeyup="return showCodeListKey('NativePlace', [this]);" verify="Ͷ���˹���|code:NativePlace" >
    </TD>
    <TD CLASS=title>
      �������ڵ�
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntRgtAddress id=AppntRgtAddress VALUE="" CLASS="wid common" MAXLENGTH=80 >
    </TD>
    <TD>
      <B></B>
    </TD>
    <TD COLSPAN=1>

    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ͨѶ��ַ
    </TD>
    <TD CLASS=input COLSPAN=3>
      <Input NAME=AppntPostalAddress id=AppntPostalAddress VALUE="" CLASS=common3 MAXLENGTH=80 >
    </TD>
    <TD CLASS=title>
      ͨѶ��ַ��������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntZipCode id=AppntZipCode VALUE="" CLASS=common MAXLENGTH=6 verify="Ͷ������������|zipcode" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      סַ
    </TD>
    <TD CLASS=input COLSPAN=3>
      <Input NAME=AppntHomeAddress id=AppntHomeAddress VALUE="" CLASS=common3 MAXLENGTH=80 >
    </TD>
    <TD CLASS=title>
      סַ��������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntHomeZipCode id=AppntHomeZipCode VALUE="" CLASS="wid common" MAXLENGTH=6 verify="Ͷ����סַ��������|zipcode" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ��ϵ�绰��1��
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntPhone id=AppntPhone VALUE="" CLASS="wid common" MAXLENGTH=18 >
    </TD>
    <TD CLASS=title>
      ��ϵ�绰��2��
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntPhone2 id=AppntPhone2 VALUE="" CLASS="wid common" MAXLENGTH=18 >
    </TD>
    <TD CLASS=title>
      �ƶ��绰
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntMobile id=AppntMobile VALUE="" CLASS="wid common" MAXLENGTH=15 >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ��������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntEMail id=AppntEMail VALUE="" CLASS="wid common" MAXLENGTH=60 >
    </TD>
    <TD CLASS=title>
      ������λ
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntGrpName id=AppntGrpName VALUE="" CLASS="wid common" MAXLENGTH=60 >
    </TD>
    <TD CLASS=title>
      ְҵ�����֣�
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntWorkType id=AppntWorkType VALUE="" CLASS="wid common" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ��ְ�����֣�
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntPluralityType id=AppntPluralityType VALUE="" CLASS="wid common" >
    </TD>
    <TD CLASS=title>
      ְҵ����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntOccupationCode id=AppntOccupationCode VALUE="" CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('OccupationCode', [this,AppntOccupationType],[0,2]);" onkeyup="return showCodeListKey('OccupationCode', [this,AppntOccupationType],[0,2]);" verify="Ͷ����ְҵ����|code:OccupationCode" >
    </TD>
    <TD CLASS=title>
      ְҵ���
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntOccupationType id=AppntOccupationType VALUE="" CLASS="wid readonly" readonly TABINDEX=-1 verify="������ְҵ���|code:OccupationType" >
    </TD>
  </TR>

  <TR CLASS=common>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCAppntGrpButton STYLE="display:none">
<!-- ����Ͷ������Ϣ���� -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntGrp);">
</td>
<td class= titleImg>
Ͷ����λ����
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCAppntGrp class=maxbox1 STYLE="display:none">
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      ��λ�ͻ���
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=AppGrpNo id=AppGrpNo> 
    </TD>
    <TD CLASS=title>
      ��λ����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=AppGrpName id=AppGrpName>
    </TD>
    <TD CLASS=title>
      ��λ��ַ
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=AppGrpAddress id=AppGrpAddress>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ��������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=AppGrpZipCode id=AppGrpZipCode>
    </TD>
    <TD CLASS=title>
      ��λ����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=GrpNature id=GrpNature>
    </TD>
    <TD CLASS=title>
      ��ҵ���
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=BusinessType id=BusinessType>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ��λ������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=Peoples id=Peoples>
    </TD>
    <TD CLASS=title>
      ��Ӫҵ��
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=MainBussiness id=MainBussiness>
    </TD>
    <TD CLASS=title>
      ��λ���˴���
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=Corporation id=Corporation>
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
    <TD CLASS=title>
      ����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=LinkMan1 id=LinkMan1>
    </TD>
    <TD CLASS=title>
      ����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=Department1 id=Department1>
    </TD>
    <TD CLASS=title>
      ��ϵ�绰
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=GrpPhone1 id=GrpPhone1> 
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ��ϵ�绰
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=GrpPhone1 id=GrpPhone1>
    </TD>
    <TD CLASS=title>
      E_mail
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=E_Mail1 id=E_Mail1>
    </TD>
    <TD CLASS=title>
      ����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=Fax1 id=Fax1>
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
    <TD CLASS=title>
      ����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=LinkMan2 id=LinkMan2>
    </TD>
    <TD CLASS=title>
      ����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=Department2 id=Department2>
    </TD>
    <TD CLASS=title>
      ��ϵ�绰
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=GrpPhone2 id=GrpPhone2>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      E_mail
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=E_Mail2 id=E_Mail2>
    </TD>
    <TD CLASS=title>
      ����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=Fax2 id=Fax2>
    </TD>
    <TD>
      <B></B>
    </TD>
    <TD COLSPAN=1>

    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ���ʽ
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=GetFlag id=GetFlag>
    </TD>
    <TD CLASS=title>
      ��������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=GrpBankCode id=GrpBankCode>
    </TD>
    <TD CLASS=title>
      �����ʺ�
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=GrpBankAccNo id=GrpBankAccNo>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ��������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=Currency id=Currency>
    </TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCInsuredButton STYLE="display:none">
<!-- ��������Ϣ���� -->
<table>
<tr>
<td class=common>
<!--<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsured);">-->
</td>
<td class= titleImg>
��������Ϣ���ͻ��ţ�<Input class="common" name=CustomerNo  id=CustomerNo>
<INPUT id="butBack" VALUE="��ѯ" TYPE=button onclick="queryInsuredNo();"> �״�Ͷ���ͻ�������д�ͻ��ţ�
<Div  id= "divSamePerson" style= "display: ''">
<font color=red>
��Ͷ����Ϊ�������˱��ˣ������������ѡ��
<INPUT TYPE="checkbox" NAME="SamePersonFlag" id=SamePersonFlag onclick="isSamePerson();">
</font>
</div>
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCInsured class=maxbox1 STYLE="display:none">
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      ����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Name id=Name VALUE="" CLASS="wid common" MAXLENGTH=20 verify="����|notnull" >
    </TD>
    <TD CLASS=title>
      �Ա�
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Sex id=Sex VALUE="" MAXLENGTH=1 CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Sex', [this]);" onkeyup="return showCodeListKey('Sex', [this]);" verify="�������Ա�|notnull&code:Sex" >
    </TD>
    <TD CLASS=title>
      ��������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Birthday id=Birthday VALUE="" CLASS="wid common" verify="�����˳�������|date&notnull" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ֤������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=IDType id=IDType VALUE="" MAXLENGTH=1 CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('IDType', [this]);" onkeyup="return showCodeListKey('IDType', [this]);" verify="������֤������|code:IDType" >
    </TD>
    <TD CLASS=title>
      ֤������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=IDNo id=IDNo VALUE="" CLASS="wid common" MAXLENGTH=20 >
    </TD>
    <TD CLASS=title>
      ����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=NativePlace id=NativePlace VALUE="" CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('NativePlace', [this]);" onkeyup="return showCodeListKey('NativePlace', [this]);" verify="�����˹���|code:NativePlace" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      �������ڵ�
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=RgtAddress id=RgtAddress VALUE="" CLASS="wid common" MAXLENGTH=80 >
    </TD>
    <TD CLASS=title>
      סַ
    </TD>
    <TD CLASS=input COLSPAN=3>
      <Input NAME=HomeAddress id=HomeAddress VALUE="" CLASS=common3 MAXLENGTH=80 >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ��������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=HomeZipCode id=HomeZipCode VALUE="" CLASS="wid common" MAXLENGTH=6 verify="��������������|zipcode" >
    </TD>
    <TD CLASS=title>
      ��ϵ�绰��1��
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Phone id=Phone VALUE="" CLASS="wid common" MAXLENGTH=18 >
    </TD>
    <TD CLASS=title>
      ��ϵ�绰��2��
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Phone2 id=Phone2 VALUE="" CLASS="wid common" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ������λ
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=GrpName id=GrpName VALUE="" CLASS="wid common" MAXLENGTH=60 >
    </TD>
    <TD CLASS=title>
      ְҵ�����֣�
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=WorkType id=WorkType VALUE="" CLASS="wid common" >
    </TD>
    <TD CLASS=title>
      ��ְ�����֣�
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=PluralityType id=PluralityType VALUE="" CLASS="wid common" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ְҵ����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=OccupationCode id=OccupationCode VALUE="" CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('OccupationCode', [this,OccupationType],[0,2]);" onkeyup="return showCodeListKey('OccupationCode', [this,OccupationType],[0,2]);" verify="������ְҵ����|code:OccupationCode" >
    </TD>
    <TD CLASS=title>
      ְҵ���
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=OccupationType id=OccupationType VALUE="" CLASS="wid readonly" readonly TABINDEX=-1 verify="������ְҵ���|code:OccupationType" >
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
      <Input class=wid NAME=Health id=Health>
    </TD>
	<TD CLASS=title></TD>
    <TD CLASS=input></TD>
	<TD CLASS=title></TD>
    <TD CLASS=input></TD>
  </TR>

</TABLE>
</DIV>
<!-- ������¼��֧�� -->
		<div  id= "divMultMainRisk" style= "display: none">
        <table>
      	  <tr>
            <td class=common>
		  	      <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMultMainRisk2);">
      		  </td>
      		  <td class= titleImg>�ѱ���������Ϣ</td>
      	  </tr>
        </table>
	      <div  id= "divMultMainRisk2" style= "display: ''">
	      <table  class= common>
	            <tr  class= common>
	      	      <td text-align: left colSpan=1><span id="spanMultMainRiskGrid" ></span></td>
			  	</tr>
			  </table>
	      </div>
	  </div>
		<!-- -->
<DIV id=DivLCBnf STYLE="display:''">
<!-- ��������Ϣ���֣��б� -->
<table id='divLCBnf1Main' style= "display: ''" >
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
<td text-align: left colSpan=1>
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

<DIV id=DivLCKind class=maxbox1 STYLE="display:''">
<TABLE class=common>

 <TR CLASS=common>
 	 <TD CLASS=title>
      �����ڼ�
    </TD>
    <TD CLASS=input COLSPAN=1>
       <Input class=codeno name=InsuYear  id=InsuYear verify="�����ڼ�|code:!InsuYear&notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('<%=queryCode%>',[this,InsuYearName],[0,1]);" onkeyup="return showCodeListKey('<%=queryCode%>',[this,InsuYearName],[0,1]);"><input class=codename name=InsuYearName id=InsuYearName  readonly=true elementtype=nacessary>

    </TD>
    
    <TD CLASS=title>
      �����ڼ䵥λ 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=InsuYearFlag id=InsuYearFlag VALUE="A" CLASS="wid readonly" readonly TABINDEX=-1 verify="�����ڼ䵥λ|notnull">
	  <!-- <Input class=codeno name=InsuYearFlag  id=InsuYearFlag verify="�����ڼ䵥λ|code:!InsuYearFlag&notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('<%=queryCode7%>',[this,InsuYearFlagName],[0,1]);" onkeyup="return showCodeListKey('<%=queryCode7%>',[this,InsuYearFlagName],[0,1]);"><input class=codename name=InsuYearFlagName id=InsuYearFlagName  readonly=true elementtype=nacessary> -->
    </TD>
	
    </TR>
    <tr class=common>

<TD CLASS=title>
                �����ڼ�
    </TD>
    <TD CLASS=input COLSPAN=1>
           <Input class=codeno name=PayEndYear id=PayEndYear verify="�����ڼ�|code:!PayEndYear&notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('<%=queryCode2%>',[this,PayEndYearName],[0,1]);" onkeyup="return showCodeListKey('<%=queryCode2%>',[this,PayEndYearName],[0,1]);"><input class=codename name=PayEndYearName id=PayEndYearName readonly=true elementtype=nacessary>
     </TD>	
     <TD CLASS=title>
      �����ڼ䵥λ
    </TD>
    <TD CLASS=input COLSPAN=1>
      <!--<Input NAME=PayEndYearFlag id=PayEndYearFlag value="Y" class="wid readonly" TABINDEX=-1 verify="�����ڼ䵥λ|notnull">-->
	  
	  <Input class=codeno name=PayEndYearFlag id=PayEndYearFlag verify="�����ڼ䵥λ|code:!PayEndYearFlag&notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('<%=queryCode8%>',[this,PayEndYearFlagName],[0,1]);" onkeyup="return showCodeListKey('<%=queryCode8%>',[this,PayEndYearFlagName],[0,1]);"><input class=codename name=PayEndYearFlagName id=PayEndYearFlagName readonly=true elementtype=nacessary>
    </TD> 
	</TR>
	<tr class=common>
<TD CLASS=title>
      ���ѷ�ʽ
    </TD>
    <TD CLASS=input COLSPAN=1>
     <Input class=codeno name=PayIntv id=PayIntv  verify="�ɷѷ�ʽ|code:!PayIntv&notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('<%=queryCode1%>',[this,PayIntvName],[0,1]);" onkeyup="return showCodeListKey('<%=queryCode1%>',[this,PayIntvName],[0,1]);"><input class=codename name=PayIntvName id=PayIntvName readonly=true elementtype=nacessary>
    </TD>	
   <TD CLASS=title>
      ����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Amnt id=Amnt VALUE="" MAXLENGTH=12 CLASS="wid common">
    </TD>

   <!--<TR CLASS=common>
	
     <TD CLASS=title>
                �������ȡ��ʽ
     </TD>
     <TD CLASS=input COLSPAN=1>
                <Input class=codeno NAME=LiveGetMode id=LiveGetMode verify="�������ȡ��ʽ|code:!LiveGetMode&notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('<%=queryCode6%>',[this,LiveGetModeName],[0,1]);" onkeyup="return showCodeListKey('<%=queryCode6%>',[this,LiveGetModeName],[0,1]);"><input class=codename name=LiveGetModeName  id=LiveGetModeName readonly=true elementtype=nacessary>
      </TD>-->
 </TR>

 <TR CLASS=common>
   	<TD CLASS=title>
      ����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Prem id=Prem VALUE="" MAXLENGTH=12 CLASS="wid common" >
    </TD>

    
   <!--  <TD CLASS=title>
      ������ȡ��ʽ 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=BonusGetMode id=BonusGetMode VALUE="1" CLASS="wid readonly" readonly TABINDEX=-1>
    </TD>--> 
    <!--<TD CLASS=title>
                �������ȡ��ʽ
     </TD>
     <TD CLASS=input COLSPAN=1>
                <Input class=codeno NAME=LiveGetMode id=LiveGetMode verify="�������ȡ��ʽ|code:!LiveGetMode&notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('<%=queryCode6%>',[this,LiveGetModeName],[0,1]);" onkeyup="return showCodeListKey('<%=queryCode6%>',[this,LiveGetModeName],[0,1]);"><input class=codename name=LiveGetModeName  id=LiveGetModeName readonly=true elementtype=nacessary>
      </TD>
 </TR>

	   <TR CLASS=common>
    <TD CLASS=title>
         �������ۼ��ڼ�
    </TD>
     <TD CLASS=input COLSPAN=1>
      <Input class=codeno name=GetYear id=GetYear  style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('<%=queryCode3%>',[this,GetYearName],[0,1]);" onkeyup="return showCodeListKey('<%=queryCode3%>',[this,GetYearName],[0,1]);"><input class=codename name=GetYearName id=GetYearName readonly=true elementtype=nacessary>
    </TD>
    
    <TD CLASS=title>
             �������ۼ��ڼ䵥λ
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=GetYearFlag id=GetYearFlag  VALUE="Y" CLASS="wid readonly" readonly TABINDEX=-1 ">
   </TD>-->
   <TD CLASS=title></TD>
   <TD CLASS=input></TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCKindHidden STYLE="display:none">
<TABLE class=common>
   <TR CLASS=common>  
  	<TD CLASS=title>
      �Զ��潻��־
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AutoPayFlag id=AutoPayFlag VALUE="" CLASS=code CodeData="0|^0|���Զ��潻^1|�Զ��潻" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblClick="showCodeListEx('AutoPayFlag',[this],[0]);" onkeyup="showCodeListKeyEx('AutoPayFlag',[this],[0]);" verify="�Զ��潻��־|notnull" >
    </TD> 
	<TD CLASS=title></TD>
    <TD CLASS=input></TD>
	<TD CLASS=title></TD>
    <TD CLASS=input></TD>
    
  </TR>
 
  <TR CLASS=common>
    <TD CLASS=title>
      ������Ч����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=CValiDate id=CValiDate VALUE="<%= ValidDate %>" CLASS="wid common" verify="������Ч����|notnull&date" >
    </TD>
	<TD CLASS=title></TD>
    <TD CLASS=input></TD>
	<TD CLASS=title></TD>
    <TD CLASS=input></TD>

   </tr>
  <TR CLASS=common>

<!-- <TD CLASS=title>
      �������ȡ��ʽ 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=LiveGetMode VALUE="1" CLASS=readonly readonly TABINDEX=-1>
    </TD>-->
    <TD CLASS=title>
      ��ȡ��ʽ
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=getIntv id=getIntv>
    </TD>


    <TD CLASS=title>
      �������ڼ�������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=GetStartType id=GetStartType>
    </TD>
	<TD CLASS=title></TD>
    <TD CLASS=input></TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      �������ʽ
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=InterestDifFlag id=InterestDifFlag>
    </TD>
    <TD CLASS=title>
      ������־
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=SubFlag id=SubFlag>
    </TD>
    <TD CLASS=title>
      �Ƿ��Զ�����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=wid NAME=RnewFlag id=RnewFlag>
    </TD>
  </TR>

</TABLE>
</DIV>

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

<Div  id= "divLCInsured2" style= "display: ''">
<table  class= common>
<tr  class= common>
<td text-align: left colSpan=1>
<span id="spanSubInsuredGrid" >
</span>
</td>
</tr>
</table>
</div>

</div>

</DIV>

<!--��ӵ�-->
<DIV id=DivInsured STYLE="display:none">
<!-- ������������Ϣ���֣��б� -->
<Div  id= "divLCInsured0" style= "display: none">
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInsured1);">
</td>
<td class= titleImg>
��������Ϣ
</td>
</tr>
</table>

<Div  id= "divInsured" style= "display: ''">
<table  class= common>
<tr  class= common>
<td text-align: left colSpan=1>
<span id="spanInsuredGrid" >
</span>
</td>
</tr>
</table>
</div>

</div>

</DIV>
<!--���Ϻ�ӵ�-->

<DIV id=DivLCImpart STYLE="display:none">
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
<td text-align: left colSpan=1>
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
<td text-align: left colSpan=1>
<span id="spanSpecGrid">
</span>
</td>
</tr>
</table>
</div>

</DIV>

<DIV id=DivChooseDiscountGrid STYLE="display:''">
<table> 
			<tr>
				<td class=common><IMG src="../common/images/butExpand.gif" style="cursor: hand;" OnClick="showPage(this,divDiscount2);"></td>
				<td class=titleImg>��ѡ�ۿ���Ϣ</td>
			</tr>
		</table>
		<Div id="divDiscount2" style="display: ''">
		<table  class= common>
<tr  class= common>
<td text-align: left colSpan=1>
<span id="spanDiscountGrid" >
</span>
</td>
</tr>
</table>
		</div>
</DIV>


<DIV id=DivChooseDuty STYLE="display:none">
<!--����ѡ������β��֣��ò���ʼ������-->
<Div  id= "divChooseDuty0" style= "display: none">
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
<td text-align: left colSpan=1>
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
<input  type= "hidden" class="wid Common" name=SelPolNo id=SelPolNo value= "">
<input type=hidden id="inpNeedPremGrid" name="inpNeedPremGrid" value="0">
<input type='hidden' name=InputTime id=InputTime value="">
<!--��Ҫ�����ֽ�������-->
    <input type='hidden' name="ProposalContNo"  id=ProposalContNo value="">
    <input type='hidden' name="WorkFlowFlag" id=WorkFlowFlag value="">		
    <input type='hidden' name="MissionID" id=MissionID value="">		
    <input type='hidden' name="SubMissionID" id=SubMissionID value="">		
    <input type='hidden' name="AppntNo" id=AppntNo value="">
<!--end-->
<Div  id= "divButton" style= "display: ''">
<br>
<%@include file="../common/jsp/ProposalOperateButton.jsp"%>
</DIV>
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
window.top.opener.document.body.innerHTML=window.document.body.innerHTML;
window.top.opener.returnFromRiskInput('TCIB5');
}
else { //�����һ��ģ̬�Ի������������Ĵ���
callerWindowObj.document.body.innerHTML=window.document.body.innerHTML;
haveMenu = 1;
callerWindowObj.parent.frames("fraMenu").Ldd = 0;
callerWindowObj.parent.frames("fraMenu").Go();
}
}
catch(ex)	{
if( haveMenu != 1 ) {
alert("Riskxxx.jsp:��������"+ex.name);
}
}

top.close();
}

returnParent();
</script>





