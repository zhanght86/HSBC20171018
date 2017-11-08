<!--
 * <p>FileName: \Risk111398.jsp </p>
 * <p>Description: 险种界面文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Author：Minim's ProposalInterfaceMaker
 * @CreateDate：2003-12-30
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
  /////获得参数-险种编码
  String riskcode=request.getParameter("riskcode");
  String PayEndYearCode="!PayEndYear-"+riskcode+"*0&0";
  String PayIntvCode="!PayIntv-"+riskcode+"*0&0";
  String GetYearCode="!GetYear-"+riskcode+"*0&0";
  String GetDutyKindCode="!GetDutyKind-"+riskcode+"*0&0";
  String InsuYearCode="!InsuYear-"+riskcode+"*0&0";
  String InterestDifTypeCode="!InterestDifType-"+riskcode+"*0&0";
////////////////////////////////////////
  String queryCodeApp="";
  ExeSQL rexeSql = new ExeSQL();
    SSRS riskFlagSSRS = new SSRS();
    riskFlagSSRS = rexeSql.execSQL("select subriskflag from lmriskapp where riskcode='"+riskcode+"'");
    loggerDebug("Risk000000","祝福险标记====="+riskFlagSSRS.GetText(1,1));
    if(riskFlagSSRS.GetText(1,1).equals("M"))
    {
      queryCodeApp="***-"+riskcode;
      session.putValue("MainRiskNo",riskcode);
    }else{
      queryCodeApp="***-"+(String)session.getValue("MainRiskNo");
    }
   %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
</head>

<body>
<form action="./ProposalSave.jsp" method=post name=fm id=fm  target="fraTitle">

</DIV>

<DIV id=DivRiskCode STYLE="display:''" class=maxbox1>
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      附加险种编码
    </TD>
    <TD CLASS=input COLSPAN=1>
     <Input class=codeno name=RiskCode id=RiskCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('<%=queryCodeApp%>',[this,RiskCodeName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('<%=queryCodeApp%>',[this,RiskCodeName],[0,1],null,null,null,null,'400');"><input class=codename name=RiskCodeName id=RiskCodeName readonly=true elementtype=nacessary>
    </TD>
	 <TD></TD>
	 <TD></TD>
	  <TD></TD>
	 <TD></TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivRiskHidden STYLE="display:none">
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      主险保单号码
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=MainPolNo id=MainPolNo class="wid">
    </TD>
	<TD></TD>
	 <TD></TD>
	  <TD></TD>
	 <TD></TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCPolHidden STYLE="display:none">
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      险种版本
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=RiskVersion id=RiskVersion class="wid">
    </TD>
    <TD CLASS=title>
      合同号
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=ContNo111 id=ContNo111 class="wid">
    </TD>
    <TD CLASS=title>
      集体合同号码
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=GrpContNo id=GrpContNo class="wid">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      首期缴费日期
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=FirstPayDate id=FirstPayDate class="wid">
    </TD>
    <TD CLASS=title>
      语种
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Lang id=Lang class="wid">
    </TD>
    <TD CLASS=title>
      货币种类
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Currency id=Currency class="wid">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      合同争议处理方式
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=DisputedFlag id=DisputedFlag class="wid">
    </TD>
    <TD CLASS=title>
      银行代收标记
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AgentPayFlag id=AgentPayFlag class="wid">
    </TD>
    <TD CLASS=title>
      银行代付标记
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AgentGetFlag id=AgentGetFlag class="wid">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      经办人
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Handler id=Handler class="wid">
    </TD>
    <TD CLASS=title>
      联合代理人编码
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AgentCode1 id=AgentCode1 class="wid">
    </TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCPolButton STYLE="display:none">
<!-- 保单信息部分 -->
<table>
<tr>
<td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAllNotice);">
</td>
<td class=titleImg>
保单信息
<INPUT class=cssButton VALUE="查询责任信息" TYPE=button onclick="showDuty();">
<INPUT  class=cssButton VALUE="关联暂缴费信息" TYPE=button onclick="showFee();">
<!--<INPUT id="butChooseDuty" VALUE="选择责任" TYPE=button onclick="chooseDuty();" disabled >
<INPUT id="butBack" VALUE="返回" TYPE=button disabled >-->
</td>
</tr>


</table>

</DIV>

<DIV id=DivLCPol STYLE="display:none" class=maxbox>
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      投保单号码</TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=ProposalNo id=ProposalNo VALUE="" CLASS="readonly wid" readonly TABINDEX=-1 MAXLENGTH=11 >
    </TD>
    <TD CLASS=title>
      印刷号码
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=PrtNo id=PrtNo VALUE="" CLASS="readonly wid" readonly TABINDEX=-1 MAXLENGTH=11 verify="印刷号码|notnull&len=11" >
    </TD>
    <TD CLASS=title>
      管理机构
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=ManageCom id=ManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center"  VALUE="" MAXLENGTH=10 CLASS=code ondblclick="return showCodeList('comcode',[this],null,null,'#1# and char_length(trim(comcode))=8','1',1);" onkeyup="return showCodeListKey('comcode',[this],null,null,'#1# and char_length(trim(comcode))=8','1',1);" verify="管理机构|code:station&notnull" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      销售渠道
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=SaleChnl id=SaleChnl VALUE="" CLASS="common wid" MAXLENGTH=2 >
    </TD>
    <TD CLASS=title>
      代理人编码
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AgentCode id=AgentCode VALUE="" MAXLENGTH=10 CLASS=code style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return queryAgent();"onkeyup="return queryAgent2();" >
    </TD>
    <TD CLASS=title>
      代理人组别
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AgentGroup id=AgentGroup VALUE="" CLASS="readonly wid" readonly TABINDEX=-1 MAXLENGTH=12 verify="代理人组别|notnull" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      备注
    </TD>
    <TD CLASS=input>
      <Input NAME=Remark id=Remark VALUE="" CLASS="common5 wid" MAXLENGTH=255 >
    </TD>
    <TD CLASS=title>
      合同号码
    </TD>
    <TD CLASS=input COLSPAN=1>
     <Input NAME=ContNo id=ContNo VALUE="" CLASS="readonly wid" readonly TABINDEX=-1 MAXLENGTH=14  >
    </TD>

  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      代理机构
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AgentCom id=AgentCom VALUE="" CLASS=code style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('AgentCom',[this],null,null,'#1# and ManageCom like #' + fm.all('ManageCom').value.substring(1,4) + '%# and #' + fm.all('ManageCom').value + '# is not null  ','1');" onkeyup="return showCodeListKey('AgentCom',[this],null,null,'#1# and ManageCom like #' + fm.all('ManageCom').value.substring(1,4) + '%# and #' + fm.all('ManageCom').value + '# is not null  ','1');" verify="代理机构|code:AgentCom" >
    </TD>
    <TD CLASS=title>
      银行营业网点
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AgentType id=AgentType VALUE="" CLASS="common wid" >
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
投保人信息（客户号：
<Input class= "common"  name=AppntCustomerNo id=AppntCustomerNo >
<INPUT id="butBack" class=cssButton VALUE="查询" TYPE=button onclick="queryAppntNo();">
首次投保客户无需填写客户号）
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCAppntInd STYLE="display:none" class=maxbox>
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      姓名
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntName id=AppntName VALUE="" CLASS="common wid" MAXLENGTH=20 verify="投保人姓名|notnull" >
    </TD>
    <TD CLASS=title>
      性别
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntSex id=AppntSex VALUE="" MAXLENGTH=1 CLASS=code style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('Sex', [this]);" onkeyup="return showCodeListKey('Sex', [this]);" verify="投保人性别|notnull&code:Sex" >
    </TD>
    <TD CLASS=title>
      出生日期
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntBirthday  VALUE="" CLASS="coolDatePicker" verify="投保人出生日期|notnull&date" onClick="laydate({elem: '#AppntBirthday'});" id="AppntBirthday"><span class="icon"><a onClick="laydate({elem: '#AppntBirthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      与被保人关系
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntRelationToInsured id=AppntRelationToInsured VALUE="" MAXLENGTH=2 CLASS=code style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('Relation', [this]);" onkeyup="return showCodeListKey('Relation', [this]);" verify="投保人与被保险人关系|code:Relation&notnull" >
    </TD>
    <TD CLASS=title>
      证件类型
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntIDType id=AppntIDType style="background:url(../common/images/select--bg_03.png) no-repeat right center" VALUE="" MAXLENGTH=1 CLASS=code ondblclick="return showCodeList('IDType', [this]);" onkeyup="return showCodeListKey('IDType', [this]);" verify="投保人证件类型|code:IDType" >
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
      国籍
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntNativePlace id=AppntNativePlace VALUE="" CLASS=code style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('NativePlace', [this]);" onkeyup="return showCodeListKey('NativePlace', [this]);" verify="投保人国籍|code:NativePlace" >
    </TD>
    <TD CLASS=title>
      户籍所在地
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntRgtAddress id=AppntRgtAddress VALUE="" CLASS="common wid" MAXLENGTH=80 >
    </TD> 
    <TD>
      <B></B>
    </TD>
    <TD COLSPAN=1>

    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      通讯地址
    </TD>
    <TD CLASS=input COLSPAN=3>
      <Input NAME=AppntPostalAddress id=AppntPostalAddress VALUE="" CLASS="common3 wid" MAXLENGTH=80 >
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
      住址
    </TD>
    <TD CLASS=input COLSPAN=3>
      <Input NAME=AppntHomeAddress id=AppntHomeAddress VALUE="" CLASS="common3 wid" MAXLENGTH=80 >
    </TD>
    <TD CLASS=title>
      住址邮政编码
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntHomeZipCode id=AppntHomeZipCode VALUE="" CLASS="common wid" MAXLENGTH=6 verify="投保人住址邮政编码|zipcode" >
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
      移动电话
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntMobile id=AppntMobile VALUE="" CLASS="common wid" MAXLENGTH=15 >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      电子邮箱
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntEMail id=AppntEMail VALUE="" CLASS="common wid" MAXLENGTH=60 >
    </TD>
    <TD CLASS=title>
      工作单位
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntGrpName id=AppntGrpName VALUE="" CLASS="common wid" MAXLENGTH=60 >
    </TD>
    <TD CLASS=title>
      职业（工种）
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntWorkType id=AppntWorkType VALUE="" CLASS="common wid" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      兼职（工种）
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntPluralityType id=AppntPluralityType VALUE="" CLASS="common wid" >
    </TD>
    <TD CLASS=title>
      职业代码
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntOccupationCode id=AppntOccupationCode VALUE="" style="background:url(../common/images/select--bg_03.png) no-repeat right center" CLASS=code ondblclick="return showCodeList('OccupationCode', [this,AppntOccupationType],[0,2]);" onkeyup="return showCodeListKey('OccupationCode', [this,AppntOccupationType],[0,2]);" verify="投保人职业代码|code:OccupationCode" >
    </TD>
    <TD CLASS=title>
      职业类别
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppntOccupationType id=AppntOccupationType VALUE="" CLASS="readonly wid" readonly TABINDEX=-1 verify="被保人职业类别|code:OccupationType" >
    </TD>
  </TR>

  <TR CLASS=common>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCAppntGrpButton STYLE="display:none">
<!-- 集体投保人信息部分 -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntGrp);">
</td>
<td class= titleImg>
投保单位资料
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCAppntGrp STYLE="display:none" class=maxbox>
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      单位客户号
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppGrpNo id=AppGrpNo class="wid">
    </TD>
    <TD CLASS=title>
      单位名称
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppGrpName id=AppGrpName  class="wid">
    </TD>
    <TD CLASS=title>
      单位地址
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppGrpAddress id=AppGrpAddress  class="wid">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      邮政编码
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AppGrpZipCode id=AppGrpZipCode  class="wid">
    </TD>
    <TD CLASS=title>
      单位性质
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=GrpNature id=GrpNature  class="wid">
    </TD>
    <TD CLASS=title>
      行业类别
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=BusinessType id=BusinessType  class="wid">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      单位总人数
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Peoples id=Peoples  class="wid">
    </TD>
    <TD CLASS=title>
      主营业务
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=MainBussiness id=MainBussiness  class="wid">
    </TD>
    <TD CLASS=title>
      单位法人代表
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Corporation id=Corporation  class="wid">
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
    <TD CLASS=title>
      姓名
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=LinkMan1 id=LinkMan1  class="wid">
    </TD>
    <TD CLASS=title>
      部门
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Department1 id=Department1  class="wid">
    </TD>
    <TD CLASS=title>
      联系电话
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=GrpPhone1 id=GrpPhone1  class="wid">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      联系电话
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=GrpPhone1 id=GrpPhone1  class="wid">
    </TD>
    <TD CLASS=title>
      E_mail
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=E_Mail1 id=E_Mail1  class="wid">
    </TD>
    <TD CLASS=title>
      传真
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Fax1 id=Fax1  class="wid">
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
    <TD CLASS=title>
      姓名
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=LinkMan2 id=LinkMan2  class="wid">
    </TD>
    <TD CLASS=title>
      部门
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Department2 id=Department2  class="wid">
    </TD>
    <TD CLASS=title>
      联系电话
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=GrpPhone2 id=GrpPhone2  class="wid">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      E_mail
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=E_Mail2 id=E_Mail2  class="wid">
    </TD>
    <TD CLASS=title>
      传真
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Fax2 id=Fax2  class="wid">
    </TD>
    <TD>
      <B></B>
    </TD>
    <TD COLSPAN=1>

    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      付款方式
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=GetFlag id=GetFlag  class="wid">
    </TD>
    <TD CLASS=title>
      开户银行
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=GrpBankCode id=GrpBankCode  class="wid">
    </TD>
    <TD CLASS=title>
      银行帐号
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=GrpBankAccNo id=GrpBankAccNo  class="wid">
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      货币种类
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Currency id=Currency  class="wid">
    </TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCInsuredButton STYLE="display:none">
<!-- 被保人信息部分 -->
<table>
<tr>
<td class=common>
<!--<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsured);">-->
 <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAllNotice);">
</td>
<td class= titleImg>
被保人信息（客户号：
<Input class="common wid" name=CustomerNo id=CustomerNo >
<INPUT id="butBack" VALUE="查询" TYPE=button onclick="queryInsuredNo();"> 首次投保客户无需填写客户号）
<Div  id= "divSamePerson" style= "display: ''">
<font color=red>
如投保人为被保险人本人，可免填本栏，请选择
<INPUT TYPE="checkbox" NAME="SamePersonFlag" id=SamePersonFlag onclick="isSamePerson();">
</font>
</div>
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCInsured STYLE="display:none" class=maxbox>
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      姓名
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Name id=Name VALUE="" CLASS="common wid" MAXLENGTH=20 verify="姓名|notnull" >
    </TD>
    <TD CLASS=title>
      性别
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Sex id=Sex VALUE="" MAXLENGTH=1 CLASS=code style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('Sex', [this]);" onkeyup="return showCodeListKey('Sex', [this]);" verify="被保人性别|notnull&code:Sex" >
    </TD>
    <TD CLASS=title>
      出生日期
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Birthday id=Birthday VALUE="" CLASS="coolDatePicker" verify="被保人出生日期|date&notnull" onClick="laydate({elem: '#Birthday'});" id="Birthday"><span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      证件类型
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=IDType id=IDType style="background:url(../common/images/select--bg_03.png) no-repeat right center" VALUE="" MAXLENGTH=1 CLASS=code ondblclick="return showCodeList('IDType', [this]);" onkeyup="return showCodeListKey('IDType', [this]);" verify="被保人证件类型|code:IDType" >
    </TD>
    <TD CLASS=title>
      证件号码
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=IDNo id=IDNo VALUE="" CLASS="common wid" MAXLENGTH=20 >
    </TD>
    <TD CLASS=title>
      国籍
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=NativePlace id=NativePlace style="background:url(../common/images/select--bg_03.png) no-repeat right center" VALUE="" CLASS=code ondblclick="return showCodeList('NativePlace', [this]);" onkeyup="return showCodeListKey('NativePlace', [this]);" verify="被保人国籍|code:NativePlace" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      户口所在地
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=RgtAddress id=RgtAddress VALUE="" CLASS="common wid" MAXLENGTH=80 >
    </TD>
    <TD CLASS=title>
      住址
    </TD>
    <TD CLASS=input COLSPAN=3>
      <Input NAME=HomeAddress id=HomeAddress VALUE="" CLASS="common wid" MAXLENGTH=80 >
    </TD>
	<TD CLASS=title></TD>
	<TD CLASS=title></TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      邮政编码
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=HomeZipCode id=HomeZipCode VALUE="" CLASS="common wid" MAXLENGTH=6 verify="被保人邮政编码|zipcode" >
    </TD>
    <TD CLASS=title>
      联系电话（1）
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Phone id=Phone VALUE="" CLASS="common wid" MAXLENGTH=18 >
    </TD>
    <TD CLASS=title>
      联系电话（2）
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Phone2 id=Phone2 VALUE="" CLASS="common wid" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      工作单位
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=GrpName id=GrpName VALUE="" CLASS="common wid" MAXLENGTH=60 >
    </TD>
    <TD CLASS=title>
      职业（工种）
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=WorkType id=WorkType VALUE="" CLASS="common wid" >
    </TD>
    <TD CLASS=title>
      兼职（工种）
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=PluralityType id=PluralityType VALUE="" CLASS="common wid" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      职业代码
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=OccupationCode id=OccupationCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" VALUE="" CLASS=code ondblclick="return showCodeList('OccupationCode', [this,OccupationType],[0,2]);" onkeyup="return showCodeListKey('OccupationCode', [this,OccupationType],[0,2]);" verify="被保人职业代码|code:OccupationCode" >
    </TD>
    <TD CLASS=title>
      职业类别
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=OccupationType id=OccupationType VALUE="" CLASS="readonly wid" readonly TABINDEX=-1 verify="被保人职业类别|code:OccupationType" >
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
      <Input NAME=Health id=Health> 
    </TD>
  </TR>

</TABLE>
</DIV>

<DIV id=DivLCBnf STYLE="display:''">
<!-- 受益人信息部分（列表） -->
<table id='divLCBnf1Main' style= "display: ''" >
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCBnf1);">
</td>
<td class= titleImg>
受益人信息
</td>
</tr>
</table>

<Div  id= "divLCBnf1" style= "display: ">
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

<DIV id=DivLCKind STYLE="display:''" class=maxbox>
<TABLE class=common>

  <TR CLASS=common>
<TD CLASS=title>
      保险期间
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=InsuYear id=InsuYear style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  VALUE="" CLASS=codeno  ondblClick="showCodeList('<%=InsuYearCode%>',[this,InsuYearName],[0,1]);" onkeyup="showCodeListKey('<%=InsuYearCode%>',[this,InsuYearName],[0,1]);" verify="保险期间|notnull"  ><input class=codename name=InsuYearName id=InsuYearName readonly=true elementtype=nacessary>
    </TD>
 <TD CLASS=title>
      缴费方式
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=PayIntv id=PayIntv style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  VALUE="" CLASS=codeno  ondblClick="showCodeList('<%=PayIntvCode%>',[this,PayIntvName],[0,1]);" onkeyup="showCodeListKey('<%=PayIntvCode%>',[this,PayIntvName],[0,1]);" verify="缴费方式|notnull"  ><input class=codename name=PayIntvName id=PayIntvName readonly=true elementtype=nacessary>
    </TD>

    <TD CLASS=title>
      缴费期限
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=PayEndYear VALUE="" CLASS=codeno style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  ondblClick="showCodeList('<%=PayEndYearCode%>',[this,PayEndYearName],[0,1]);" onkeyup="showCodeListKey('<%=PayEndYearCode%>',[this,PayEndYearName],[0,1]);" verify="缴费期限|notnull" ><input class=codename name=PayEndYearName id=PayEndYearName readonly=true elementtype=nacessary>
    </TD>

  </TR>

  <TR CLASS=common>
<TD CLASS=title>
      领取年龄
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input  Name="GetYear" id=GetYear VALUE="" CLASS=codeno style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblClick="showCodeList('<%=GetYearCode%>',[this,GetYearName],[0,1]);" onkeyup="showCodeListKey('<%=GetYearCode%>',[this,GetYearName],[0,1]);" verify="领取年龄|not null" ><input class=codename name=GetYearName id=GetYearName readonly=true elementtype=nacessary>
    </TD>

     <TD CLASS=title>
      领取方式
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME="GetDutyKind" VALUE="" CLASS=codeno style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  ondblClick="showCodeList('<%=GetDutyKindCode%>',[this,GetIntv,GetDutyKindName],[0,2,1]);" onkeyup="showCodeListKey('<%=GetDutyKindCode%>',[this,GetIntv,GetDutyKindName],[0,2,1]);" verify="领取年龄|not null"><input class=codename name=GetDutyKindName id=GetDutyKindName readonly=true elementtype=nacessary>
    </TD>


		<TD CLASS=title>
      份数
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Mult id=Mult VALUE="" CLASS="common wid">
    </TD>

  </TR>
<TR CLASS=common>

    <TD CLASS=title>
      保额
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Amnt id=Amnt VALUE="" CLASS="common wid">
    </TD>

    <TD CLASS=title>
      保费
      </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="common wid" NAME=Prem id=Prem VALUE="" MAXLENGTH=12>
    </TD>
</TR>
</TABLE>
</DIV>
 <DIV id=DivInterestDif STYLE="display:none">
 	<TABLE class=common>
 		<TR class=common>
    <TD CLASS=title>
      利差返还方式
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class=codeno NAME=InterestDifFlag id=InterestDifFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" VALUE="" ondblClick="showCodeList('<%=InterestDifTypeCode%>',[this,InterestDifFlagName],[0,1]);" onkeyup="showCodeListKey('<%=InterestDifTypeCode%>',[this,InterestDifFlagName],[0,1]);"><input class=codename name=InterestDifFlagName id=InterestDifFlagName readonly=true elementtype=nacessary>
    </TD>
    <TD CLASS=title>
    </TD>
    <TD CLASS=input COLSPAN=1>
    </TD>
    <TD CLASS=title>
    </TD>
    <TD CLASS=input COLSPAN=1>
    </TD>
  </TR>
   </TABLE>
  </DIV>

<DIV id=DivLCKindHidden STYLE="display:none">
<TABLE class=common>

  <!--TR CLASS=common>

     <TD CLASS=title>
      保险期限
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=InsuYear>
    </TD-->

    <TD CLASS=title>
      保险期限单位
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=InsuYearFlag id=InsuYearFlag>
    </TD>

    <TD CLASS=title>
      缴费期限单位
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=PayEndYearFlag id=PayEndYearFlag value="Y" class="common wid">
    </TD>
  </TR>

  <TR CLASS=common>

  	 <!--TD CLASS=title>
      保额
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Amnt>
    </TD-->

    <TD CLASS=title>
      给付间隔
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME="GetIntv" id=GetIntv VALUE="" CLASS="common wid">
    </TD>

    <!--TD CLASS=title>
      缴费方式
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=PayIntv VALUE="" CLASS=common>
    </TD-->

    <!--TD CLASS=title>
      给付方法
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=GetDutyKind>
    </TD-->

    <TD CLASS=title>
      保单生效日期
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=CValiDate VALUE="<%= ValidDate %>" CLASS="coolDatePicker" verify="保单生效日期|notnull&date" onClick="laydate({elem: '#CValiDate'});" id="CValiDate"><span class="icon"><a onClick="laydate({elem: '#CValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

    </TD>

  </TR>

  <!--TR CLASS=common>
    <TD CLASS=title>
      领取年龄单位
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=GetYearFlag value="A" class="common">
    </TD-->
    <!--TD CLASS=title>
      起领日期计算类型
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=GetStartType>
    </TD>
    <TD CLASS=title>
      自动垫交标志
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=AutoPayFlag>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      利差返还方式
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=InterestDifFlag>
    </TD>
    <TD CLASS=title>
      减额交清标志
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=SubFlag>
    </TD>
    <TD CLASS=title>
      红利领取方式
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=BonusGetMode>
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      生存金领取方式
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=LiveGetMode>
    </TD>
    <TD CLASS=title>
      是否自动续保
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=RnewFlag>
    </TD>
  </TR-->

</TABLE>
</DIV>

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

<!--后加的-->
<DIV id=DivInsured STYLE="display:none">
<!-- 连带被保人信息部分（列表） -->
<Div  id= "divLCInsured0" style= "display: none">
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInsured1);">
</td>
<td class= titleImg>
被保人信息
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
<!--以上后加的-->

<DIV id=DivLCImpart STYLE="display:none">
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
<td text-align: left colSpan=1>
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
				<td class=titleImg>可选折扣信息</td>
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
<!--可以选择的责任部分，该部分始终隐藏-->
<Div  id= "divChooseDuty0" style= "display: none">
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
<input  type= "hidden" class= Common name= SelPolNo value= "">
<input type=hidden id="inpNeedPremGrid" name="inpNeedPremGrid" value="0">

<Div  id= "divButton" style= "display: ''">
<br>
<%@include file="../common/jsp/ProposalOperateButton.jsp"%>
</DIV>

</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<span id="spanApprove"  style="display: none; position:relative; slategray"></span>

<br/><br/><br/><br/>
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
callerWindowObj.parent.frames("fraMenu").Ldd = 0;
callerWindowObj.parent.frames("fraMenu").Go();
}
}
catch(ex)	{
if( haveMenu != 1 ) {
alert("Riskxxx.jsp:发生错误："+ex.name);
}
}

top.close();
}

returnParent();
</script>

</DIV>



