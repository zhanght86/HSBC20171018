<%@include file="../i18n/language.jsp"%>
<!--
 * <p>FileName: \Risk111398.jsp </p>
 * <p>Description: 险种界面文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Author：Minim's ProposalInterfaceMaker
 * @CreateDate：2003-12-30
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
险种编码
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=RiskCode VALUE="" MAXLENGTH=6 CLASS=code ondblclick="if(loadFlag=='1')  showCodeList('RiskInd',[this]);" onkeyup="return showCodeListKey('RiskInd',[this]);" verify="险种编码|code:RiskCode" >
    </TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivRiskHidden STYLE="display:none;">
<TABLE class=common>

  <TR CLASS=common>
    <TD class=title5>
主险保单号码
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
险种版本
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=RiskVersion>
    </TD>
    <TD class=title5>
合同号
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=ContNo111>
    </TD>
    <TD class=title5>
集体合同号码
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=GrpContNo>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
首期交费日期
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=FirstPayDate>
    </TD>
    <TD class=title5>
语种
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Lang>
    </TD>
    <TD class=title5>
货币种类
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Currency>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
保单争议处理方式
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=DisputedFlag>
    </TD>
    <TD class=title5>
银行代收标记
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AgentPayFlag>
    </TD>
    <TD class=title5>
银行代付标记
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AgentGetFlag>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
经办人
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Handler>
    </TD>
    <TD class=title5>
联合代理人编码
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AgentCode1>
    </TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCPolButton STYLE="display:none;">
<!-- 保单信息部分 -->
<table>
<tr>
<td>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCPol);">
</td>
<td class= titleImg>
保单信息
<INPUT VALUE="查询责任信息" TYPE=button onclick="showDuty();">
<INPUT VALUE="关联暂交费信息" TYPE=button onclick="showFee();">
<!--<INPUT id="butChooseDuty" VALUE="选择责任" TYPE=button onclick="chooseDuty();" disabled >
<INPUT id="butBack" VALUE="返回" TYPE=button disabled >-->
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCPol STYLE="display:none;">
<TABLE class=common>

  <TR CLASS=common>
    <TD class=title5>
投保单号码</TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=ProposalNo VALUE="" CLASS=readonly readonly="readonly" TABINDEX=-1 MAXLENGTH=11 >
    </TD>
    <TD class=title5>
印刷号
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=PrtNo VALUE="" CLASS=readonly readonly="readonly" TABINDEX=-1 MAXLENGTH=11 verify="印刷号|notnull&len=11" >
    </TD>
    <TD class=title5>
管理机构
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=ManageCom VALUE="" MAXLENGTH=10 CLASS=code ondblclick="return showCodeList('comcode',[this],null,null,'#1# and Length(trim(comcode))=8','1',1);" onkeyup="return showCodeListKey('comcode',[this],null,null,'#1# and Length(trim(comcode))=8','1',1);" verify="管理机构|code:station&notnull" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
销售渠道
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=SaleChnl VALUE="" CLASS=common MAXLENGTH=2 >
    </TD>
    <TD class=title5>
代理人编码
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AgentCode VALUE="" MAXLENGTH=10 CLASS=code ondblclick="return queryAgent();"onkeyup="return queryAgent2();" >
    </TD>
    <TD class=title5>
代理人组别
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AgentGroup VALUE="" CLASS=readonly readonly="readonly" TABINDEX=-1 MAXLENGTH=12 verify="代理人组别|notnull" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
备注
    </TD>
    <TD class=input5>
      <Input NAME=Remark VALUE="" CLASS=common5 MAXLENGTH=255 >
    </TD>
    <TD class=title5>
合同号码
    </TD>
    <TD class=input5 COLSPAN=1>
     <Input NAME=ContNo VALUE="" CLASS=readonly readonly="readonly" TABINDEX=-1 MAXLENGTH=14  >
    </TD>
 
  </TR>

  <TR CLASS=common>
    <TD class=title5>
代理机构
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AgentCom VALUE="" CLASS=code ondblclick="showCodeList('AgentCom',[this],null,null,'#1# and ManageCom like #' + fm.all('ManageCom').value.substring(0,4) + '%# and #' + fm.all('ManageCom').value + '# is not null  ','1');" onkeyup="return showCodeListKey('AgentCom',[this],null,null,'#1# and ManageCom like #' + fm.all('ManageCom').value.substring(0,4) + '%# and #' + fm.all('ManageCom').value + '# is not null  ','1');" verify="代理机构|code:AgentCom" >
    </TD>
    <TD class=title5>
银行营业网点
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AgentType VALUE="" CLASS=common >
    </TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCAppntIndButton STYLE="display:none;">
<!-- 投保人信息部分 -->
<table>
<tr>
<td>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);">
</td>
<td class= titleImg>
投保人信息（客户号：<Input class= common  name=AppntCustomerNo >
<INPUT id="butBack" VALUE="查询" TYPE=button onclick="queryAppntNo();">
首次投保客户无需填写客户号）
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCAppntInd STYLE="display:none;">
<TABLE class=common>

  <TR CLASS=common>
    <TD class=title5>
姓名
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntName VALUE="" CLASS=common MAXLENGTH=20 verify="投保人姓名|notnull" >
    </TD>
    <TD class=title5>
性别
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntSex VALUE="" MAXLENGTH=1 CLASS=code ondblclick="return showCodeList('Sex', [this]);" onkeyup="return showCodeListKey('Sex', [this]);" verify="投保人性别|notnull&code:Sex" >
    </TD>
    <TD class=title5>
出生日期
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntBirthday VALUE="" CLASS=common verify="投保人出生日期|notnull&date" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
与被保人关系
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntRelationToInsured VALUE="" MAXLENGTH=2 CLASS=code ondblclick="return showCodeList('Relation', [this]);" onkeyup="return showCodeListKey('Relation', [this]);" verify="投保人与被保险人关系|code:Relation&notnull" >
    </TD>
    <TD class=title5>
证件类型
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntIDType VALUE="" MAXLENGTH=1 CLASS=code ondblclick="return showCodeList('IDType', [this]);" onkeyup="return showCodeListKey('IDType', [this]);" verify="投保人证件类型|code:IDType" >
    </TD>
    <TD class=title5>
证件号码
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntIDNo VALUE="" CLASS=common MAXLENGTH=20 >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
国籍
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntNativePlace VALUE="" CLASS=code ondblclick="return showCodeList('NativePlace', [this]);" onkeyup="return showCodeListKey('NativePlace', [this]);" verify="投保人国籍|code:NativePlace" >
    </TD>
    <TD class=title5>
户籍所在地
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
通讯地址
    </TD>
    <TD class=input5 COLSPAN=3>
      <Input NAME=AppntPostalAddress VALUE="" CLASS=common3 MAXLENGTH=80 >
    </TD>
    <TD class=title5>
通讯地址邮政编码
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntZipCode VALUE="" CLASS=common MAXLENGTH=6 verify="投保人邮政编码|zipcode" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
住址
    </TD>
    <TD class=input5 COLSPAN=3>
      <Input NAME=AppntHomeAddress VALUE="" CLASS=common3 MAXLENGTH=80 >
    </TD>
    <TD class=title5>
家庭地址邮政编码
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntHomeZipCode VALUE="" CLASS=common MAXLENGTH=6 verify="投保人住址邮政编码|zipcode" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
联系电话（1）
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntPhone VALUE="" CLASS=common MAXLENGTH=18 >
    </TD>
    <TD class=title5>
联系电话（2）
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntPhone2 VALUE="" CLASS=common MAXLENGTH=18 >
    </TD>
    <TD class=title5>
移动电话
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntMobile VALUE="" CLASS=common MAXLENGTH=15 >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
电子信箱
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntEMail VALUE="" CLASS=common MAXLENGTH=60 >
    </TD>
    <TD class=title5>
工作单位
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntGrpName VALUE="" CLASS=common MAXLENGTH=60 >
    </TD>
    <TD class=title5>
职业（工种）
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntWorkType VALUE="" CLASS=common >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
兼职（工种）
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntPluralityType VALUE="" CLASS=common >
    </TD>
    <TD class=title5>
职业代码
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntOccupationCode VALUE="" CLASS=code ondblclick="return showCodeList('OccupationCode', [this,AppntOccupationType],[0,2]);" onkeyup="return showCodeListKey('OccupationCode', [this,AppntOccupationType],[0,2]);" verify="投保人职业代码|code:OccupationCode" >
    </TD>
    <TD class=title5>
职业类别
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppntOccupationType VALUE="" CLASS=readonly readonly="readonly" TABINDEX=-1 verify="被保人职业类别|code:OccupationType" >
    </TD>
  </TR>

  <TR CLASS=common>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCAppntGrpButton STYLE="display:none;">
<!-- 集体投保人信息部分 -->
<table>
<tr>
<td>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntGrp);">
</td>
<td class= titleImg>
投保单位资料
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCAppntGrp STYLE="display:none;">
<TABLE class=common>

  <TR CLASS=common>
    <TD class=title5>
单位客户号
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppGrpNo>
    </TD>
    <TD class=title5>
单位名称
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppGrpName>
    </TD>
    <TD class=title5>
单位地址
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppGrpAddress>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
邮政编码
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AppGrpZipCode>
    </TD>
    <TD class=title5>
单位性质
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=GrpNature>
    </TD>
    <TD class=title5>
行业类别
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=BusinessType>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
单位总人数
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Peoples>
    </TD>
    <TD class=title5>
主营业务
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=MainBussiness>
    </TD>
    <TD class=title5>
单位法人代表
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Corporation>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD>
      <B>保险联系人一</B>
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
姓名
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=LinkMan1>
    </TD>
    <TD class=title5>
部门
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Department1>
    </TD>
    <TD class=title5>
联系电话
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=GrpPhone1>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
联系电话
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
传真
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Fax1>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD>
      <B>保险联系人二</B>
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
姓名
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=LinkMan2>
    </TD>
    <TD class=title5>
部门
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Department2>
    </TD>
    <TD class=title5>
联系电话
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
传真
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
付款方式
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=GetFlag>
    </TD>
    <TD class=title5>
开户银行
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=GrpBankCode>
    </TD>
    <TD class=title5>
银行帐号
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=GrpBankAccNo>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
货币种类
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Currency>
    </TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCInsuredButton STYLE="display:none;">
<!-- 被保人信息部分 -->
<table>
<tr>
<td class=common>
<!--<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsured);">-->
</td>
<td class= titleImg>
被保人信息（客户号：<Input class= common name=CustomerNo >
<INPUT id="butBack" VALUE="查询" TYPE=button onclick="queryInsuredNo();">首次投保客户无需填写客户号）
<Div  id= "divSamePerson" style= "display: ''">
<font color=red>
如投保人为被保险人本人，可免填本栏，请选择
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
姓名
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Name VALUE="" CLASS=common MAXLENGTH=20 verify="姓名|notnull" >
    </TD>
    <TD class=title5>
性别
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Sex VALUE="" MAXLENGTH=1 CLASS=code ondblclick="return showCodeList('Sex', [this]);" onkeyup="return showCodeListKey('Sex', [this]);" verify="被保人性别|notnull&code:Sex" >
    </TD>
    <TD class=title5>
出生日期
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Birthday VALUE="" CLASS=common verify="被保人出生日期|date&notnull" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
证件类型
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=IDType VALUE="" MAXLENGTH=1 CLASS=code ondblclick="return showCodeList('IDType', [this]);" onkeyup="return showCodeListKey('IDType', [this]);" verify="被保人证件类型|code:IDType" >
    </TD>
    <TD class=title5>
证件号码
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=IDNo VALUE="" CLASS=common MAXLENGTH=20 >
    </TD>
    <TD class=title5>
国籍
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=NativePlace VALUE="" CLASS=code ondblclick="return showCodeList('NativePlace', [this]);" onkeyup="return showCodeListKey('NativePlace', [this]);" verify="被保人国籍|code:NativePlace" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
户口所在地
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=RgtAddress VALUE="" CLASS=common MAXLENGTH=80 >
    </TD>
    <TD class=title5>
住址
    </TD>
    <TD class=input5 COLSPAN=3>
      <Input NAME=HomeAddress VALUE="" CLASS=common3 MAXLENGTH=80 >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
邮政编码
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=HomeZipCode VALUE="" CLASS=common MAXLENGTH=6 verify="被保人邮政编码|zipcode" >
    </TD>
    <TD class=title5>
联系电话（1）
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Phone VALUE="" CLASS=common MAXLENGTH=18 >
    </TD>
    <TD class=title5>
联系电话（2）
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Phone2 VALUE="" CLASS=common >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
工作单位
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=GrpName VALUE="" CLASS=common MAXLENGTH=60 >
    </TD>
    <TD class=title5>
职业（工种）
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=WorkType VALUE="" CLASS=common >
    </TD>
    <TD class=title5>
兼职（工种）
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=PluralityType VALUE="" CLASS=common >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
职业代码
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=OccupationCode VALUE="" CLASS=code ondblclick="return showCodeList('OccupationCode', [this,OccupationType],[0,2]);" onkeyup="return showCodeListKey('OccupationCode', [this,OccupationType],[0,2]);" verify="被保人职业代码|code:OccupationCode" >
    </TD>
    <TD class=title5>
职业类别
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=OccupationType VALUE="" CLASS=readonly readonly="readonly" TABINDEX=-1 verify="被保人职业类别|code:OccupationType" >
    </TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCInsuredHidden STYLE="display:none;">
<TABLE class=common>

  <TR CLASS=common>
    <TD class=title5>
健康状况
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=Health>
    </TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCBnf STYLE="display:''">
<!-- 受益人信息部分（列表） -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCBnf1);">
</td>
<td class= titleImg>
受益人信息
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
<!-- 险种信息部分 -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCKind);">
</td>
<td class= titleImg>
险种信息
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCKind STYLE="display:''">
<TABLE class=common>

  <TR CLASS=common>
    <TD class=title5>
投保申请日期
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=CValiDate VALUE="" CLASS=common verify="保单生效日期|notnull&date" >
    </TD>
    <!--TD class=title5>
      是否指定生效日期 
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=SpecifyValiDate VALUE="N" MAXLENGTH=1 CLASS=code ondblclick="return showCodeList('YesNo', [this]);" onkeyup="return showCodeListKey('YesNo', [this]);" verify="是否指定生效日|code:YesNo" >
    </TD>
    <TD class=title5>
      收费方式
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=PayLocation VALUE="" MAXLENGTH=1 CLASS=code ondblclick="return showCodeList('PayLocation', [this]);" onkeyup="return showCodeListKey('PayLocation', [this]);" verify="收费方式|code:PayLocation" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
      开户行 
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=BankCode VALUE="" MAXLENGTH=10 CLASS=code ondblclick="return showCodeList('bank', [this]);" onkeyup="return showCodeListKey('bank', [this]);" verify="开户行|code:bank" >
    </TD>
    <TD class=title5>
      户名 
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AccName VALUE="" CLASS=common MAXLENGTH=20 >
    </TD>
    <TD class=title5>
      银行帐号 
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=BankAccNo VALUE="" CLASS=common MAXLENGTH=40 >
    </TD>
  </TR>

  <TR CLASS=common-->
   	
    <TD class=title5>
保费</TD>
    <TD class=input5 COLSPAN=1>
      <Input class=common NAME=Prem VALUE="" MAXLENGTH=12 >
    </TD>
    <TD class=title5>
保额
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input CLASS=common NAME=Amnt VALUE="" MAXLENGTH=12 >
    </TD>
      
  </TR>
<tr>
<TD class=title5>
保险期间
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=InsuYear VALUE="" CLASS=code CodeData="0|^10|十年|Y^15|十五年|Y^20|二十年|Y^30|三十年|Y" ondblClick="showCodeListEx('InsuYear60199',[this,InsuYearFlag,PayEndYear,PayEndYearFlag],[0,2,0,2]);" onkeyup="showCodeListKeyEx('InsuYear60199',[this,InsuYearFlag,PayEndYear,PayEndYearFlag],[0,2,0,2]);" verify="交费期间|notnull" >
    </TD>
    <TD class=title5>
终交期间单位
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=InsuYearFlag VALUE=""  CLASS=readonly readonly="readonly" >
    </TD>
   <TD class=title5>
交费方式
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input  Name=PayIntv VALUE="" CLASS=code CodeData="0|^0|趸交|1000|Y^12|年交||Y" ondblClick="showCodeListEx('PayIntv60199',[this,PayEndYear,PayEndYearFlag],[0,2,3]);" onkeyup="showCodeListKeyEx('PayIntv60199',[this,PayEndYear,PayEndYearFlag],[0,2,3]);" verify="保险期间|not null"  >
    </TD>  
</tr>
  <!--TR CLASS=common>
    <TD class=title5>
      保险期间
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=InsuYear VALUE="" CLASS=code CodeData="0|^10|十年|Y^15|十五年|Y^20|-二十年|Y^30|-三十年|Y" ondblClick="showCodeListEx('PayEndYear1459',[this,InsuYearFlag],[0,2]);" onkeyup="showCodeListKeyEx('PayEndYear1459',[this,InsuYearFlag],[0,2]);" verify="保险期间|not null"  >
    </TD>
    <TD class=title5>
      保险期间单位 
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=InsuYearFlag VALUE="" CLASS=readonly readonly="readonly"  TABINDEX=-1 verify="终交期间单位|notnull"  >
    </TD>
    <TD class=title5>
      减额交清标志 
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=SubFlag VALUE="" CLASS=code ondblclick="return showCodeList('YesNo', [this]);" onkeyup="return showCodeListKey('YesNo', [this]);" >
    </TD>
  </TR-->
 <TR CLASS=common>
   
    <TD class=title5>
交费期间
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=PayEndYear VALUE="" CLASS=readonly readonly="readonly" >
    </TD>
  <TD class=title5>
交费期间单位
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
给付方式
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=GetDutyKind>
    </TD>
    <TD class=title5>
领取方式
    </TD>
    <!--TD class=input5 COLSPAN=1>
      <Input NAME=getIntv>
    </TD-->
    <TD class=title5>
起领期间
    </TD>
    <!--TD class=input5 COLSPAN=1>
      <Input NAME=GetYear>
    </TD-->
  </TR>

  <TR CLASS=common>
    <TD class=title5>
起领期间单位
    </TD>
    <!--TD class=input5 COLSPAN=1>
      <Input NAME=GetYearFlag>
    </TD-->
    <TD class=title5>
起领日期计算类型
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=GetStartType>
    </TD>
    <TD class=title5>
自动垫交标志
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=AutoPayFlag>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
利差返还方式
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=InterestDifFlag>
    </TD>
    <TD class=title5>
减额交清标志
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=SubFlag>
    </TD>
    <TD class=title5>
红利领取方式
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=BonusGetMode>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD class=title5>
生存金领取方式
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=LiveGetMode>
    </TD>
    <TD class=title5>
是否自动续保
    </TD>
    <TD class=input5 COLSPAN=1>
      <Input NAME=RnewFlag>
    </TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCSubInsured STYLE="display:none;">
<!-- 连带被保人信息部分（列表） -->
<Div  id= "divLCInsured0" style= "display:none;">
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsured2);">
</td>
<td class= titleImg>
连带被保人信息
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
<!-- 告知信息部分（列表） -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);">
</td>
<td class= titleImg>
告知信息
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
<!-- 特约信息部分（列表） -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCSpec1);">
</td>
<td class= titleImg>
特约信息
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
<!--可以选择的责任部分，该部分始终隐藏-->
<Div  id= "divChooseDuty0" style= "display:none;">
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divDutyGrid1);">
</td>
<td class= titleImg>
可选责任信息
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
if(isDia==0) { //如果是打开一个新的窗口，则执行下面的代码
top.opener.parent.document.body.innerHTML=window.document.body.innerHTML;
}
else { //如果打开一个模态对话框，则调用下面的代码
callerWindowObj.document.body.innerHTML=window.document.body.innerHTML;
haveMenu = 1;
callerWindowObj.parent.frames["fraMenu"].Ldd = 0;
callerWindowObj.parent.frames["fraMenu"].Go();
}
}
catch(ex)	{
if( haveMenu != 1 ) {
myAlert("初始化界面错误!"+ex.name);
}
}

top.close();
}

returnParent();
</script>

</DIV>



