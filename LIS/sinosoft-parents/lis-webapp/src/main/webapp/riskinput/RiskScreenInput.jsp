<!--
 * <p>FileName: \Risk111302.jsp </p>
 * <p>Description: 险种界面文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Author：Minim's ProposalInterfaceMaker
 * @CreateDate：2003-12-30
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
      险种编码 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=RiskCode id=RiskCode VALUE="" MAXLENGTH=6 CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
	  ondblclick="showCodeList('RiskInd',[this]);" onkeyup="return showCodeListKey('RiskInd',[this]);" verify="险种编码|code:RiskCode" >
    </TD>
  </TR>

</TABLE>
</DIV>



<DIV id=DivLCPolButton STYLE="display: ">
<!-- 保单信息部分 -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCPol);">
</td>
<td class= titleImg>
保单信息
<!--<INPUT VALUE="查询责任信息" TYPE=button class= cssButton onclick="showDuty();"-->
<!--INPUT VALUE="关联暂交费信息" TYPE=button onclick="showFee();"-->
<!--<INPUT id="butChooseDuty" VALUE="选择责任" TYPE=button onclick="chooseDuty();" disabled >
<INPUT id="butBack" VALUE="返回" TYPE=button disabled >-->
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCPol class=maxbox STYLE="display: ">
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      投保单号码 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=ProposalNo id=ProposalNo VALUE="" class="wid common" readonly TABINDEX=-1 MAXLENGTH=20 >
    </TD>
    <TD CLASS=title>
      印刷号码 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=PrtNo id=PrtNo VALUE="" class="wid common" readonly TABINDEX=-1 MAXLENGTH=14 verify="印刷号码|notnull&len=14" >
    </TD>
    <TD CLASS=title>
      管理机构 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=ManageCom id=ManageCom VALUE="" MAXLENGTH=10 class="wid common" readonly 
	  style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
	  ondblclick="return showCodeList('comcode',[this],null,null,'#1# and char_length(trim(comcode))=8','1',1);" onkeyup="return showCodeListKey('comcode',[this],null,null,'#1# and char_length(trim(comcode))=8','1',1);" verify="管理机构|code:station&notnull" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      销售渠道 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=SaleChnl id=SaleChnl VALUE="" class="wid common" readonly MAXLENGTH=2 >
    </TD>
    <TD CLASS=title>
      代理人编码 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AgentCode id=AgentCode VALUE="" MAXLENGTH=10 class="wid common" readonly 
	  style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
	  ondblclick="return queryAgent();"onkeyup="return queryAgent2();" >
    </TD>
    <TD CLASS=title>
      代理人组别 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AgentGroup id=AgentGroup VALUE="" class="wid common" readonly TABINDEX=-1 MAXLENGTH=12 verify="代理人组别|notnull" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      代理机构 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AgentCom id=AgentCom VALUE="" class="wid common" readonly style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
	  ondblclick="showCodeList('AgentCom',[this],null,null,'#1# and ManageCom like #' + document.all('ManageCom').value.substring(1,4) + '%# and #' + document.all('ManageCom').value + '# is not null  ','1');" onkeyup="return showCodeListKey('AgentCom',[this],null,null,'#1# and ManageCom like #' + document.all('ManageCom').value.substring(1,4) + '%# and #' + document.all('ManageCom').value + '# is not null  ','1');" verify="代理机构|code:AgentCom" >
    </TD>
    <TD CLASS=title>
      银行营业网点
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AgentType id=AgentType VALUE="" class="wid common" readonly >
    </TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCAppntIndButton STYLE="display:none">
<!-- 投保人信息部分 -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);">
</td>
<td class= titleImg>
投保人信息(客户号)：<Input class= common  name=AppntCustomerNo >
<INPUT id="butBack" VALUE="查询" TYPE=button class= cssButton onclick="queryAppntNo();">
首次投保客户无需填写客户号）
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCAppntInd class=maxbox STYLE="display:none">
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      姓名 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntName id=AppntName VALUE="" class="wid common" MAXLENGTH=20 verify="投保人姓名|notnull" >
    </TD>
    <TD CLASS=title>
      性别 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntSex id=AppntSex VALUE="" MAXLENGTH=1 CLASS=code 
	  style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
	  ondblclick="return showCodeList('Sex', [this]);" onkeyup="return showCodeListKey('Sex', [this]);" verify="投保人性别|notnull&code:Sex" >
    </TD>
    <TD CLASS=title>
      出生日期 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntBirthday id=AppntBirthday VALUE="" class="wid common" verify="投保人出生日期|notnull&date" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      与被保人关系 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntRelationToInsured id=AppntRelationToInsured VALUE="" MAXLENGTH=2 CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
	  ondblclick="return showCodeList('Relation', [this]);" onkeyup="return showCodeListKey('Relation', [this]);" verify="投保人与被保险人关系|code:Relation&notnull" >
    </TD>
    <TD CLASS=title>
      证件类型 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntIDType id=AppntIDType VALUE="0" class="wid common" readonly TABINDEX=-1 MAXLENGTH=1 CLASS=code 
	  style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
	  ondblclick="return showCodeList('IDType', [this]);" onkeyup="return showCodeListKey('IDType', [this]);" verify="投保人证件类型|code:IDType" >
    </TD>
    <TD CLASS=title>
      证件号码 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntIDNo id=AppntIDNo VALUE="" CLASS="common wid" MAXLENGTH=20 >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      通讯地址
    </TD>
    <TD CLASS=input COLSPAN=3>
      <Input NAME=AppntPostalAddress id=AppntPostalAddress id=AppntPostalAddress VALUE="" CLASS="common3" MAXLENGTH=80 >
    </TD>
    <TD CLASS=title>
      通讯地址邮政编码 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntZipCode id=AppntZipCode VALUE="" CLASS="common wid" MAXLENGTH=6 verify="投保人邮政编码|zipcode" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      联系电话（1）
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntPhone id=AppntPhone VALUE="" CLASS="common wid" MAXLENGTH=18 >
    </TD>
    <TD CLASS=title>
      联系电话（2）
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntPhone2 id=AppntPhone2 VALUE="" CLASS="common wid" MAXLENGTH=18 >
    </TD>
    <TD CLASS=title>
      工作单位
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
<!-- 被保人信息部分 -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butCollapse.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsured);">
</td>
<td class= titleImg>
被保人信息（客户号：<Input class= readonly readonly name=CustomerNo >）
<Div  id= "divSamePerson" style= "display: none">
<INPUT id="butBack" VALUE="查询" TYPE=button class= cssButton onclick="queryInsuredNo();"> 首次投保客户无需填写客户号）
<font color=red>
如投保人为被保险人本人，可免填本栏，请选择
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
      姓名 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Name id=Name VALUE="" class="wid common" readonly MAXLENGTH=20 verify="姓名|notnull" >
    </TD>
    <TD CLASS=title>
      性别 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Sex id=Sex VALUE="" MAXLENGTH=1 class="wid common" readonly ondblclick="return showCodeList('Sex', [this]);" onkeyup="return showCodeListKey('Sex', [this]);" verify="被保人性别|notnull&code:Sex" >
    </TD>
    <TD CLASS=title>
      出生日期 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Birthday VALUE="" class="wid common" readonly verify="被保人出生日期|date&notnull" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      证件类型 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=IDType VALUE="0" MAXLENGTH=1 class="wid common" readonly ondblclick="return showCodeList('IDType', [this]);" onkeyup="return showCodeListKey('IDType', [this]);" verify="被保人证件类型|code:IDType" >
    </TD>
    <TD CLASS=title>
      证件号码 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=IDNo VALUE="" class="wid common" readonly MAXLENGTH=20 >
    </TD>
    <TD CLASS=title>
      与投保人关系 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntRelationToInsured VALUE="" MAXLENGTH=2 class="wid common" readonly ondblclick="return showCodeList('Relation', [this]);" onkeyup="return showCodeListKey('Relation', [this]);" verify="投保人与被保险人关系|code:Relation&notnull" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      住址 
    </TD>
    <TD CLASS=input COLSPAN=3>
      <Input NAME=HomeAddress VALUE="" class="common3" readonly MAXLENGTH=80 >
    </TD>
    <TD CLASS=title>
      邮政编码 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=HomeZipCode VALUE="" class="wid common" readonly MAXLENGTH=6 verify="被保人邮政编码|zipcode" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      联系电话（1）
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Phone VALUE="" class="wid common" readonly MAXLENGTH=18 >
    </TD>
    <TD CLASS=title>
      联系电话（2）
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Phone2 VALUE="" class="wid common" readonly >
    </TD>
    <TD CLASS=title>
      工作单位
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
      健康状况 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Health>
    </TD>
  </TR>

</TABLE>
</DIV>
<DIV id=DivLCInsuredNoListButton STYLE="display: ">
<!-- 被保人信息部分 -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butCollapse.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsuredNoList);">
</td>
<td class= titleImg>
无名单录入
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCInsuredNoList class=maxbox STYLE="display:none">
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      *性别 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Sex id=Sex VALUE="" MAXLENGTH=1 CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
	  ondblclick="return showCodeList('Sex', [this]);" onkeyup="return showCodeListKey('Sex', [this]);" verify="被保人性别|notnull&code:Sex" >
    </TD>
    <TD CLASS=title>
      *年龄 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Age id=Age VALUE="" CLASS="common wid"  >
    </TD>
  </TR>

  
    <TR CLASS=common>
    <TD CLASS=title>
      *职业代码 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="code"  NAME=AppntOccupationCode id=AppntOccupationCode>
    </TD>
    <TD CLASS=title>
      职业类别 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="readonly wid" readonly NAME=AppntOccupationType id=AppntOccupationType>
    </TD>
    
    <TD CLASS=title>
      邮政编码 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=HomeZipCode id="HomeZipCode" VALUE="" CLASS="common wid" MAXLENGTH=6 verify="被保人邮政编码|zipcode" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      联系电话（1）
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Phone id=Phone VALUE="" class="wid common" MAXLENGTH=18 >
    </TD>
    <TD CLASS=title>
      联系电话（2）
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Phone2 id=Phone2 VALUE="" class="wid common" >
    </TD>
    <TD CLASS=title>
      工作单位
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=GrpName id=GrpName VALUE="" class="wid common" MAXLENGTH=60 >
    </TD>
  </TR>
</TABLE>
</DIV>



<DIV id=DivLCKindButton STYLE="display: ">
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

<DIV id=DivLCKind class=maxbox STYLE="display: ">
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      险种编码 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <!--Input NAME=RiskCode11 VALUE="" MAXLENGTH=6 CLASS=code CodeData="0|^111301|团体关爱|^211601|团体意外" ondblclick="return showCodeList('RiskCode11',[this]);" onkeyup="return showCodeListKey('RiskCode11',[this]);" -->
        <Input class="code"  CodeData="0|^111301|团体关爱|^211601|团体意外"  name=RiskCode11 id=RiskCode11
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
      投保申请日期
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=PolApplyDate id=PolApplyDate VALUE="" class="wid common" >
    </TD>
    <TD CLASS=title>
      投保单生效日期
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=CValiDate id=CValiDate VALUE="" class="wid common" verify="保单生效日期|notnull&date" >
    </TD>
    <TD CLASS=title>
      份数 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Mult id=Mult VALUE="" class="wid common" verify="份数|notnull&value>0&value<4" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      保费 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Prem id=Prem VALUE="" class="wid common" MAXLENGTH=12 >
    </TD>
    <TD CLASS=title>
      保额 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Amnt id=Amnt VALUE="" class="wid common" MAXLENGTH=12 >
    </TD>
    <TD CLASS=title>
      浮动费率
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=FloatRate id=FloatRate VALUE="" class="wid common" readonly TABINDEX=-1 verify="浮动费率|Num" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      是否指定生效日期 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=SpecifyValiDate id=SpecifyValiDate VALUE="Y" MAXLENGTH=1 CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
	  ondblclick="return showCodeList('YesNo', [this]);" onkeyup="return showCodeListKey('YesNo', [this]);" verify="是否指定生效日|code:YesNo" >
    </TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCKindHidden STYLE="display:none">
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      收费方式
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=PayLocation id=PayLocation class="common wid">
    </TD>
    <TD CLASS=title>
      开户行
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=BankCode id=BankCode class="common wid">
    </TD>
    <TD CLASS=title>
      户名
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AccName id=AccName class="common wid">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      银行帐号
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=银行帐号 class="common wid">
    </TD>
    <TD CLASS=title>
      是否体检件
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=HealthCheckFlag id=HealthCheckFlag class="common wid">
    </TD>
    <TD CLASS=title>
      保险期间
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=InsuYear id=InsuYear class="common wid">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      保险期间单位 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=InsuYearFlag id=InsuYearFlag class="common wid">
    </TD>
    <TD CLASS=title>
      交费期间
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=PayEndYear id=PayEndYear class="common wid">
    </TD>
    <TD CLASS=title>
      终交期间单位
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=PayEndYearFlag id=PayEndYearFlag class="common wid">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      给付方法
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=GetDutyKind id=GetDutyKind class="common wid">
    </TD>
    <TD CLASS=title>
      交费方式
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=PayIntv id=PayIntv class="common wid">
    </TD>
    <TD CLASS=title>
      领取方式
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=getIntv id=getIntv class="common wid">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      起领期间
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=GetYear class="common wid">
    </TD>
    <TD CLASS=title>
      起领期间单位 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=GetYearFlag class="common wid">
    </TD>
    <TD CLASS=title>
      起领日期计算类型 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=GetStartType class="common wid">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      自动垫交标志 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AutoPayFlag class="common wid">
    </TD>
    <TD CLASS=title>
      利差返还方式 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=InterestDifFlag class="common wid">
    </TD>
    <TD CLASS=title>
      减额交清标志 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=SubFlag class="common wid">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      红利领取方式 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=BonusGetMode class="common wid">
    </TD>
    <TD CLASS=title>
      生存金领取方式 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=LiveGetMode class="common wid">
    </TD>
    <TD CLASS=title>
      是否自动续保
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=RnewFlag class="common wid">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      溢交保费方式
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
<!--可以选择的责任部分，该部分始终隐藏-->
<Div  id= "divChooseDuty0" style= "display:  ">
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divDutyGrid1);">
</td>
<td class= titleImg>
责任信息
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
 <!-- 保费项信息部分（列表） -->
 <table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPremGrid);">
</td>
<td class= titleImg>
保费项信息
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

<!-- 要约录入部分（列表） -->

	<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCFactor);">
    		</td>
    		<td class= titleImg>
    			 要约信息
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
<!-- 受益人信息部分（列表） -->
<DIV id=DivLCBnf STYLE="display: ">
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butCollapse.gif" style= "cursor:hand;" OnClick= "showPage(this,divBnfGrid1);">
</td>
<td class= titleImg>
受益人信息
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
<!-- 连带被保人信息部分（列表） -->
<Div  id= "divLCInsured0" style= "display: none">
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

<Div  id= "divLCSpec1" style= "display:  ">
<table class=common>
   <TR  class= common> 
      <TD  class= title> 特别约定</TD>
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
<!-- 特约信息部分（列表） -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCNoti1);">
</td>
<td class= titleImg>
备注信息
</td>
</tr>
</table>

<Div  id= "divLCNoti1" style= "display:  ">
<table class=common>
   <TR  class= common> 
      <TD  class= title> 备注信息</TD>
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
<INPUT VALUE="上 一 步" TYPE=button class= cssButton onclick="parent.close();">
<INPUT VALUE="保存投保单" TYPE=button class= cssButton onclick="alert('保存成功');">
</DIV>

</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<span id="spanApprove"  style="display: none; position:relative; slategray"></span>
<br /><br /><br /><br />
</body>
</html>




