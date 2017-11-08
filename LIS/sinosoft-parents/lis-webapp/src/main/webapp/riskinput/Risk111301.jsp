<!--
 * <p>FileName: \Risk112401.jsp </p>
 * <p>Description: 险种界面文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Author：Minim's ProposalInterfaceMaker
 * @CreateDate：2003-12-30
-->

<DIV id=DivPageHead STYLE="display:  "><%@page
	contentType="text/html;charset=gb2312"%> <%@page
	import="java.util.*"%> <%@page
	import="com.sinosoft.utility.*"%> <%@page
	import="com.sinosoft.lis.schema.*"%> <%@page
	import="com.sinosoft.lis.vschema.*"%> <%@page
	import="com.sinosoft.lis.tb.*"%> <%@page
	import="com.sinosoft.lis.pubfun.*"%> <%@page
	import="com.sinosoft.lis.cbcheck.*"%> <%@page
	import="com.sinosoft.lis.vbl.*"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
	<%@page import="com.sinosoft.service.*"%> <%String CurrentDate = PubFun.getCurrentDate();
  Date dt = PubFun.calDate(new FDate().getDate(CurrentDate), 1, "D", null);
  String ValidDate = CurrentDate;
/////获得参数-险种编码
  String riskcode=request.getParameter("riskcode");
  String needSetValue = request.getParameter("needSetValue");
  loggerDebug("Risk111301","页面是否需要调用赋值函数=====" + needSetValue);
  
  String queryCode="!InsuYear-"+riskcode+"*0&0";
  String queryCode1="!PayIntv-"+riskcode+"*0&0";
  String queryCode2="!PayEndYear-"+riskcode+"*0&0";
  String queryCode3="!GetYear-"+riskcode+"*0&0";
  String queryCode4="!Getdutykind-"+riskcode+"*0&0";
  String queryCode5="!BonusGetMode-"+riskcode+"*0&0";  
  
  String queryCode6="!Currency-"+riskcode+"*0&0";  
   //////////////////////////////////
  String queryCodeApp="";
  
  ExeSQL rexeSql = new ExeSQL();
    SSRS riskFlagSSRS = new SSRS();
//    riskFlagSSRS = rexeSql.execSQL("select subriskflag from lmriskapp where riskcode='"+riskcode+"'");
       String sql="select subriskflag from lmriskapp where riskcode='"+riskcode+"'";
	   TransferData sTransferData=new TransferData();
       sTransferData.setNameAndValue("SQL", sql);
       VData sVData = new VData();
       sVData.add(sTransferData);
       BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
       if(tBusinessDelegate.submitData(sVData, "execSQL", "ExeSQLUI"))
       {
    	   riskFlagSSRS = (SSRS)tBusinessDelegate.getResult().getObjectByObjectName("SSRS", 0);
       }
    loggerDebug("Risk111301","主副险标记====="+riskFlagSSRS.GetText(1,1));
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
<form action="./ProposalSave.jsp" method=post name=fm id="fm" target="fraTitle">


<DIV id=DivRiskCode STYLE="display:  ">
<TABLE class=common>

	<TR CLASS=common>
		<TD CLASS=title>险种编码</TD>
		<TD CLASS=input COLSPAN=1><Input class=codeno name=RiskCode id="RiskCode" style='background: url("../common/images/select--bg_03.png") no-repeat right;'
			ondblclick="showCodeList('<%=queryCodeApp%>',[this,'RiskCodeName'],[0,1],null,null,null,null,'400');"
			onkeyup="return showCodeListKey('<%=queryCodeApp%>',[this,'RiskCodeName'],[0,1],null,null,null,null,'400');"><input
			class=codename name=RiskCodeName id="RiskCodeName" readonly=true elementtype=nacessary>
		</TD>
		<td class=title></td>
		<td class=input></td>
	</TR>

</TABLE>
</DIV>

<DIV id=DivRiskHidden STYLE="display: none">
<TABLE class=common>

	<TR CLASS=common>
		<TD CLASS=title>主险保单号码</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=MainPolNo id="MainPolNo"></TD>
	</TR>

</TABLE>
</DIV>

<DIV id=DivLCPolHidden STYLE="display: none">
<TABLE class=common>

	<TR CLASS=common>
		<TD CLASS=title>险种版本</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=RiskVersion id="RiskVersion"></TD>
		<TD CLASS=title>合同号</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=ContNo111 id="ContNo111"></TD>
		<TD CLASS=title>集体合同号码</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=GrpContNo id="GrpContNo"></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>首期交费日期</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=FirstPayDate id="FirstPayDate"></TD>
		<TD CLASS=title>语种</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=Lang id="Lang"></TD>
		<TD CLASS=title>货币种类</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=Currency id="Currency"></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>合同争议处理方式</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=DisputedFlag id="DisputedFlag"></TD>
		<TD CLASS=title>银行代收标记</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AgentPayFlag id="AgentPayFlag"></TD>
		<TD CLASS=title>银行代付标记</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AgentGetFlag id="AgentGetFlag"></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>经办人</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=Handler id="Handler"></TD>
		<TD CLASS=title>联合代理人编码</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AgentCode1 id="AgentCode1"></TD>
		<TD CLASS=title></TD>
		<TD CLASS=input COLSPAN=1></TD>
	</TR>

</TABLE>
</DIV>

<DIV id=DivLCPolButton STYLE="display: none"><!-- 保单信息部分 -->
<table>
	<tr>
		<td><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,DivLCPol);"></td>
		<td class=titleImg>保单信息 <INPUT VALUE="查询责任信息" TYPE=button
			onclick="showDuty();"> <INPUT VALUE="关联暂交费信息" TYPE=button
			onclick="showFee();"> <!--<INPUT id="butChooseDuty" VALUE="选择责任" TYPE=button onclick="chooseDuty();" disabled >
<INPUT id="butBack" VALUE="返回" TYPE=button disabled >--></td>
	</tr>
</table>

</DIV>

<DIV id=DivLCPol STYLE="display: none">
<TABLE class=common>

	<TR CLASS=common>
		<TD CLASS=title>投保单号码</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=ProposalNo id="ProposalNo" VALUE=""
			CLASS=readonly readonly TABINDEX=-1 MAXLENGTH=11></TD>
		<TD CLASS=title>印刷号码</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=PrtNo id=PrtNo VALUE=""
			CLASS=readonly readonly TABINDEX=-1 MAXLENGTH=11
			verify="印刷号码|notnull&len=11"></TD>
		<TD CLASS=title>管理机构</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=ManageCom id=ManageCom VALUE=""
			MAXLENGTH=10 CLASS=code style='background: url("../common/images/select--bg_03.png") no-repeat right;'
			ondblclick="return showCodeList('comcode',[this],null,null,'#1# and char_length(trim(comcode))=8','1',1);"
			onkeyup="return showCodeListKey('comcode',[this],null,null,'#1# and char_length(trim(comcode))=8','1',1);"
			verify="管理机构|code:station&notnull"></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>销售渠道</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=SaleChnl id=SaleChnl VALUE=""
			CLASS=common MAXLENGTH=2></TD>
		<TD CLASS=title>代理人编码</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AgentCode id=AgentCode VALUE=""
			MAXLENGTH=10 CLASS=code ondblclick="return queryAgent();"
			onkeyup="return queryAgent2();"></TD>
		<TD CLASS=title>代理人组别</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AgentGroup id=AgentGroup VALUE=""
			CLASS=readonly readonly TABINDEX=-1 MAXLENGTH=12
			verify="代理人组别|notnull"></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>备注</TD>
		<TD CLASS=input><Input NAME=Remark id=Remark VALUE="" CLASS=common5
			MAXLENGTH=255></TD>
		<TD CLASS=title>合同号码</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=ContNo id=ContNo VALUE=""
			CLASS=readonly readonly TABINDEX=-1 MAXLENGTH=14></TD>

	</TR>

	<TR CLASS=common>
		<TD CLASS=title>代理机构</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AgentCom id=AgentCom VALUE=""
			CLASS=code style='background: url("../common/images/select--bg_03.png") no-repeat right;'
			ondblclick="showCodeList('AgentCom',[this],null,null,'#1# and ManageCom like #' + fm.all('ManageCom').value.substring(1,4) + '%# and #' + fm.all('ManageCom').value + '# is not null  ','1');"
			onkeyup="return showCodeListKey('AgentCom',[this],null,null,'#1# and ManageCom like #' + fm.all('ManageCom').value.substring(1,4) + '%# and #' + fm.all('ManageCom').value + '# is not null  ','1');"
			verify="代理机构|code:AgentCom"></TD>
		<TD CLASS=title>银行营业网点</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AgentType id=AgentType VALUE=""
			CLASS=common></TD>
	</TR>

</TABLE>
</DIV>

<DIV id=DivLCAppntIndButton STYLE="display: none"><!-- 投保人信息部分 -->
<table>
	<tr>
		<td><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,DivLCAppntInd);">
		</td>
		<td class=titleImg>投保人信息（客户号：<Input class=common
			name=AppntCustomerNo id=AppntCustomerNo> <INPUT id="butBack" VALUE="查询"
			TYPE=button onclick="queryAppntNo();"> 首次投保客户无需填写客户号）</td>
	</tr>
</table>

</DIV>

<DIV id=DivLCAppntInd STYLE="display: none">
<TABLE class=common>

	<TR CLASS=common>
		<TD CLASS=title>姓名</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AppntName id=AppntName VALUE=""
			CLASS=common MAXLENGTH=20 verify="投保人姓名|notnull"></TD>
		<TD CLASS=title>性别</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AppntSex id=AppntSex VALUE=""
			MAXLENGTH=1 CLASS=code
			ondblclick="return showCodeList('Sex', [this]);" style='background: url("../common/images/select--bg_03.png") no-repeat right;'
			onkeyup="return showCodeListKey('Sex', [this]);"
			verify="投保人性别|notnull&code:Sex"></TD>
		<TD CLASS=title>出生日期</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AppntBirthday id=AppntBirthday VALUE=""
			CLASS=common verify="投保人出生日期|notnull&date"></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>与被保人关系</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AppntRelationToInsured id=AppntRelationToInsured
			VALUE="" MAXLENGTH=2 CLASS=code
			ondblclick="return showCodeList('Relation', [this]);" style='background: url("../common/images/select--bg_03.png") no-repeat right;'
			onkeyup="return showCodeListKey('Relation', [this]);"
			verify="投保人与被保险人关系|code:Relation&notnull"></TD>
		<TD CLASS=title>证件类型</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AppntIDType id=AppntIDType VALUE=""
			MAXLENGTH=1 CLASS=code
			ondblclick="return showCodeList('IDType', [this]);" style='background: url("../common/images/select--bg_03.png") no-repeat right;'
			onkeyup="return showCodeListKey('IDType', [this]);"
			verify="投保人证件类型|code:IDType"></TD>
		<TD CLASS=title>证件号码</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AppntIDNo id=AppntIDNo VALUE=""
			CLASS=common MAXLENGTH=20></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>国籍</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AppntNativePlace id=AppntNativePlace VALUE="" style='background: url("../common/images/select--bg_03.png") no-repeat right;'
			CLASS=code ondblclick="return showCodeList('NativePlace', [this]);"
			onkeyup="return showCodeListKey('NativePlace', [this]);"
			verify="投保人国籍|code:NativePlace"></TD>
		<TD CLASS=title>户籍所在地</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AppntRgtAddress id=AppntRgtAddress VALUE=""
			CLASS=common MAXLENGTH=80></TD>
		<TD><B></B></TD>
		<TD COLSPAN=1></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>通讯地址</TD>
		<TD CLASS=input COLSPAN=3><Input NAME=AppntPostalAddress id=AppntPostalAddress VALUE=""
			CLASS=common3 MAXLENGTH=80></TD>
		<TD CLASS=title>通讯地址邮政编码</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AppntZipCode id=AppntZipCode VALUE=""
			CLASS=common MAXLENGTH=6 verify="投保人邮政编码|zipcode"></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>住址</TD>
		<TD CLASS=input COLSPAN=3><Input NAME=AppntHomeAddress id=AppntHomeAddress VALUE=""
			CLASS=common3 MAXLENGTH=80></TD>
		<TD CLASS=title>住址邮政编码</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AppntHomeZipCode id=AppntHomeZipCode VALUE=""
			CLASS=common MAXLENGTH=6 verify="投保人住址邮政编码|zipcode"></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>联系电话（1）</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AppntPhone id=AppntPhone VALUE=""
			CLASS=common MAXLENGTH=18></TD>
		<TD CLASS=title>联系电话（2）</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AppntPhone2 id=AppntPhone2 VALUE=""
			CLASS=common MAXLENGTH=18></TD>
		<TD CLASS=title>移动电话</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AppntMobile id=AppntMobile VALUE=""
			CLASS=common MAXLENGTH=15></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>电子邮箱</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AppntEMail id=AppntEMail VALUE=""
			CLASS=common MAXLENGTH=60></TD>
		<TD CLASS=title>工作单位</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AppntGrpName id=AppntGrpName VALUE=""
			CLASS=common MAXLENGTH=60></TD>
		<TD CLASS=title>职业（工种）</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AppntWorkType id=AppntWorkType VALUE=""
			CLASS=common></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>兼职（工种）</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AppntPluralityType id=AppntPluralityType VALUE=""
			CLASS=common></TD>
		<TD CLASS=title>职业代码</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AppntOccupationCode id=AppntOccupationCode
			VALUE="" CLASS=code style='background: url("../common/images/select--bg_03.png") no-repeat right;'
			ondblclick="return showCodeList('OccupationCode', [this,AppntOccupationType],[0,2]);"
			onkeyup="return showCodeListKey('OccupationCode', [this,AppntOccupationType],[0,2]);"
			verify="投保人职业代码|code:OccupationCode"></TD>
		<TD CLASS=title>职业类别</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AppntOccupationType id=AppntOccupationType
			VALUE="" CLASS=readonly readonly TABINDEX=-1
			verify="被保人职业类别|code:OccupationType"></TD>
	</TR>

	<TR CLASS=common>
	</TR>

</TABLE>
</DIV>

<DIV id=DivLCAppntGrpButton STYLE="display: none"><!-- 集体投保人信息部分 -->
<table>
	<tr>
		<td><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,DivLCAppntGrp);">
		</td>
		<td class=titleImg>投保单位资料</td>
	</tr>
</table>

</DIV>

<DIV id=DivLCAppntGrp STYLE="display: none">
<TABLE class=common>

	<TR CLASS=common>
		<TD CLASS=title>单位客户号</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AppGrpNo id=AppGrpNo></TD>
		<TD CLASS=title>单位名称</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AppGrpName id=AppGrpName></TD>
		<TD CLASS=title>单位地址</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AppGrpAddress id=AppGrpAddress></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>邮政编码</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AppGrpZipCode id=AppGrpZipCode></TD>
		<TD CLASS=title>单位性质</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=GrpNature id=GrpNature></TD>
		<TD CLASS=title>行业类别</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=BusinessType id=BusinessType></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>单位总人数</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=Peoples id=Peoples></TD>
		<TD CLASS=title>主营业务</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=MainBussiness id=MainBussiness></TD>
		<TD CLASS=title>单位法人代表</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=Corporation id=Corporation></TD>
	</TR>

	<TR CLASS=common>
		<TD><B>保险联系人一</B></TD>
		<TD COLSPAN=1></TD>
		<TD><B></B></TD>
		<TD COLSPAN=1></TD>
		<TD><B></B></TD>
		<TD COLSPAN=1></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>姓名</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=LinkMan1 id=LinkMan1></TD>
		<TD CLASS=title>部门</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=Department1 id=Department1></TD>
		<TD CLASS=title>联系电话</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=GrpPhone1 id=GrpPhone1></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>联系电话</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=GrpPhone1 id=GrpPhone1></TD>
		<TD CLASS=title>E_mail</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=E_Mail1 id=E_Mail1></TD>
		<TD CLASS=title>传真</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=Fax1 id=Fax1></TD>
	</TR>

	<TR CLASS=common>
		<TD><B>保险联系人二</B></TD>
		<TD COLSPAN=1></TD>
		<TD><B></B></TD>
		<TD COLSPAN=1></TD>
		<TD><B></B></TD>
		<TD COLSPAN=1></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>姓名</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=LinkMan2 id=LinkMan2></TD>
		<TD CLASS=title>部门</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=Department2 id=Department2></TD>
		<TD CLASS=title>联系电话</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=GrpPhone2 id=GrpPhone2></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>E_mail</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=E_Mail2 id=E_Mail2></TD>
		<TD CLASS=title>传真</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=Fax2 id=Fax2></TD>
		<TD><B></B></TD>
		<TD COLSPAN=1></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>付款方式</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=GetFlag id=GetFlag></TD>
		<TD CLASS=title>开户银行</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=GrpBankCode id=GrpBankCode></TD>
		<TD CLASS=title>银行帐号</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=GrpBankAccNo id=GrpBankAccNo></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>货币种类</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=Currency id=Currency></TD>
	</TR>

</TABLE>
</DIV>

<DIV id=DivLCInsuredButton STYLE="display: none"><!-- 被保人信息部分 -->
<table>
	<tr>
		<td class=common><!--<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsured);">-->
		</td>
		<td class=titleImg>被保人信息（客户号：<Input class=common name=CustomerNo id=CustomerNo>
		<INPUT id="butBack" VALUE="查询" TYPE=button onclick="queryInsuredNo();">
		首次投保客户无需填写客户号）
		<Div id="divSamePerson" style="display:  "><font color=red>
		如投保人为被保险人本人，可免填本栏，请选择 <INPUT TYPE="checkbox" NAME="SamePersonFlag"
			onclick="isSamePerson();"> </font></div>
		</td>
	</tr>
</table>

</DIV>

<DIV id=DivLCInsured STYLE="display: none">
<TABLE class=common>

	<TR CLASS=common>
		<TD CLASS=title>姓名</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=Name id=Name VALUE="" CLASS=common
			MAXLENGTH=20 verify="姓名|notnull"></TD>
		<TD CLASS=title>性别</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=Sex id=Sex VALUE="" MAXLENGTH=1
			CLASS=code ondblclick="return showCodeList('Sex', [this]);"
			onkeyup="return showCodeListKey('Sex', [this]);"
			verify="被保人性别|notnull&code:Sex"></TD>
		<TD CLASS=title>出生日期</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=Birthday id=Birthday VALUE=""
			CLASS=common verify="被保人出生日期|date&notnull"></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>证件类型</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=IDType id=IDType VALUE="" MAXLENGTH=1
			CLASS=code ondblclick="return showCodeList('IDType', [this]);"
			onkeyup="return showCodeListKey('IDType', [this]);"
			verify="被保人证件类型|code:IDType"></TD>
		<TD CLASS=title>证件号码</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=IDNo id=IDNo VALUE="" CLASS=common
			MAXLENGTH=20></TD>
		<TD CLASS=title>国籍</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=NativePlace id=NativePlace VALUE=""
			CLASS=code ondblclick="return showCodeList('NativePlace', [this]);"
			onkeyup="return showCodeListKey('NativePlace', [this]);"
			verify="被保人国籍|code:NativePlace"></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>户口所在地</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=RgtAddress id=RgtAddress VALUE=""
			CLASS=common MAXLENGTH=80></TD>
		<TD CLASS=title>住址</TD>
		<TD CLASS=input COLSPAN=3><Input NAME=HomeAddress id=HomeAddress VALUE=""
			CLASS=common3 MAXLENGTH=80></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>邮政编码</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=HomeZipCode id=HomeZipCode VALUE=""
			CLASS=common MAXLENGTH=6 verify="被保人邮政编码|zipcode"></TD>
		<TD CLASS=title>联系电话（1）</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=Phone id=Phone VALUE="" CLASS=common
			MAXLENGTH=18></TD>
		<TD CLASS=title>联系电话（2）</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=Phone2 id=Phone2 VALUE=""
			CLASS=common></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>工作单位</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=GrpName id=GrpName VALUE=""
			CLASS=common MAXLENGTH=60></TD>
		<TD CLASS=title>职业（工种）</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=WorkType id=WorkType VALUE=""
			CLASS=common></TD>
		<TD CLASS=title>兼职（工种）</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=PluralityType id=PluralityType VALUE=""
			CLASS=common></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>职业代码</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=OccupationCode id=OccupationCode VALUE=""
			CLASS=code
			ondblclick="return showCodeList('OccupationCode', [this,OccupationType],[0,2]);"
			onkeyup="return showCodeListKey('OccupationCode', [this,OccupationType],[0,2]);"
			verify="被保人职业代码|code:OccupationCode"></TD>
		<TD CLASS=title>职业类别</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=OccupationType id=OccupationType VALUE=""
			CLASS=readonly readonly TABINDEX=-1
			verify="被保人职业类别|code:OccupationType"></TD>
	</TR>

</TABLE>
</DIV>

<DIV id=DivLCInsuredHidden STYLE="display: none">
<TABLE class=common>

	<TR CLASS=common>
		<TD CLASS=title>健康状况</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=Health id=Health></TD>
	</TR>

</TABLE>
</DIV>
<!-- 多主险录入支持 -->
<div id="divMultMainRisk" style="display: none">
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divMultMainRisk2);">
		</td>
		<td class=titleImg>已保存主险信息</td>
	</tr>
</table>
<div id="divMultMainRisk2" style="display:  ">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanMultMainRiskGrid"></span></td>
	</tr>
</table>
</div>
</div>
<!-- -->
<DIV id=DivLCBnf STYLE="display:  "><!-- 受益人信息部分（列表） -->
<table id='divLCBnf1Main'  style="display:  ">
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divLCBnf1);"></td>
		<td class=titleImg>受益人信息</td>
	</tr>
</table>

<Div id="divLCBnf1"  style="display:  ">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanBnfGrid"> </span></td>
	</tr>
</table>
</div>

</DIV>

<DIV id=DivLCKindButton STYLE="display:  "><!-- 险种信息部分 -->
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,DivLCKind);"></td>
		<td class=titleImg>险种信息</td>
	</tr>
</table>

</DIV>

<DIV id=DivLCKind class="maxbox" STYLE="display:  ">
<TABLE class=common>

	<TR CLASS=common STYLE="display: none">
		<TD CLASS=title>保险期间</TD>
		<TD CLASS=input COLSPAN=1><Input class=codeno name=InsuYear id=InsuYear VALUE="70"
			verify="保险期间|code:!InsuYear&notnull" style='background: url("../common/images/select--bg_03.png") no-repeat right;'
			ondblclick="return showCodeList('<%=queryCode%>',[this,InsuYearName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('<%=queryCode%>',[this,InsuYearName],[0,1],null,null,null,1);"><input
			class=codename name=InsuYearName readonly=true elementtype=nacessary id=InsuYearName>
		</TD>

		<TD CLASS=title>保险期间单位</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=InsuYearFlag id=InsuYearFlag VALUE="A"
			CLASS=readonly readonly TABINDEX=-1 verify="保险期间单位|notnull">
		</TD>
	</TR>

	<TR CLASS=common STYLE="display: none">
		<TD CLASS=title>起领期间</TD>
		<TD CLASS=input COLSPAN=1><Input class=codeno name=GetYear id=GetYear
			verify="起领期间|code:!GetYear&notnull" style='background: url("../common/images/select--bg_03.png") no-repeat right;'
			ondblclick="return showCodeList('<%=queryCode3%>',[this,GetYearName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('<%=queryCode3%>',[this,GetYearName],[0,1],null,null,null,1);"><input
			class=codename name=GetYearName id=GetYearName readonly=true elementtype=nacessary>
		</TD>

		<TD CLASS=title>起领期间单位</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=GetYearFlag id=GetYearFlag VALUE="A"
			CLASS=readonly readonly TABINDEX=-1 verify="起领期间单位|notnull">
		</TD>
	</TR>

	<TR CLASS=common>

		<TD CLASS=title>交费方式</TD>
		<TD CLASS=input COLSPAN=1><Input class=codeno name=PayIntv id=PayIntv
			verify="缴费方式|code:!PayIntv&notnull" style='background: url("../common/images/select--bg_03.png") no-repeat right;'
			ondblclick="return showCodeList('<%=queryCode1%>',[this,'PayIntvName'],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('<%=queryCode1%>',[this,'PayIntvName'],[0,1],null,null,null,1);"><input
			class=codename name=PayIntvName id=PayIntvName readonly=true elementtype=nacessary>
		</TD>

		<TD CLASS=title STYLE="display: none">给付方法</TD>
		<TD CLASS=input COLSPAN=1 STYLE="display: none"><Input
			class=codeno name=GetDutyKind style='background: url("../common/images/select--bg_03.png") no-repeat right;' id=GetDutyKind verify="给付方法|code:!GetDutyKind&notnull"
			ondblclick="return showCodeList('<%=queryCode4%>',[this,GetDutyKindName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('<%=queryCode4%>',[this,GetDutyKindName],[0,1],null,null,null,1);"><input
			class=codename name=GetDutyKindName id=GetDutyKindName readonly=true
			elementtype=nacessary></TD>
		<TD CLASS=title>交费期间</TD>
		<TD CLASS=input COLSPAN=1><Input class=codeno name=PayEndYear id=PayEndYear
			verify="交费期间|code:!PayEndYear&notnull" style='background: url("../common/images/select--bg_03.png") no-repeat right;'
			ondblclick="return showCodeList('<%=queryCode2%>',[this,'PayEndYearName'],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('<%=queryCode2%>',[this,'PayEndYearName'],[0,1],null,null,null,1);"><input
			class=codename name=PayEndYearName id=PayEndYearName readonly=true
			elementtype=nacessary></TD>

		<TD CLASS=title>交费期间单位</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=PayEndYearFlag id=PayEndYearFlag VALUE=""
			CLASS="readonly wid" readonly TABINDEX=-1 verify="交费期间单位|notnull">
		</TD>

	</TR>
	
    <TR CLASS=common>
		<TD CLASS=title>币种</TD>
		<TD CLASS=input COLSPAN=1><Input class=codeno NAME=CurrencyCode id=CurrencyCode style='background: url("../common/images/select--bg_03.png") no-repeat right;' verify="币种|code:notnull"
		    ondblclick="return showCodeList('<%=queryCode6%>',[this,'CurrencyName'],[0,1]);"
			onkeyup="return showCodeListKey('<%=queryCode6%>',[this,'CurrencyName'],[0,1]);"><input class=codename name=CurrencyName id=CurrencyName readonly=true elementtype=nacessary id=CurrencyName></TD>
		<TD CLASS=title>保费</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=Prem id=Prem VALUE="" MAXLENGTH=12
			CLASS="common wid"></TD>

		<TD CLASS=title>保额</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=Amnt id=Amnt VALUE="" MAXLENGTH=12
			CLASS="common wid"></TD>
	</TR>

	<TR CLASS=common STYLE="display: none">

		<TD CLASS=title>红利领取方式</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=BonusGetMode id=BonusGetMode VALUE="1"
			CLASS=codeno  
			verify="红利领取方式|code:!BonusGetMode&notnull"
			ondblclick="return showCodeList('<%=queryCode5%>',[this,BonusGetModeName],[0,1]);"
			onkeyup="return showCodeListKey('<%=queryCode5%>',[this,BonusGetModeName],[0,1]);"><input
			class=codename name=BonusGetModeName id=BonusGetModeName readonly=true
			elementtype=nacessary></TD>
	</TR>



</TABLE>
</DIV>

<DIV id=DivLCKindHidden STYLE="display: none">
<TABLE class=common>
	<TR CLASS=common>
		<TD CLASS=title>自动垫交标志</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=AutoPayFlag id=AutoPayFlag VALUE=""
			CLASS=code CodeData="0|^0|不自动垫交^1|自动垫交"
			ondblClick="showCodeListEx('AutoPayFlag',[this],[0]);"
			onkeyup="showCodeListKeyEx('AutoPayFlag',[this],[0]);"
			verify="自动垫交标志|notnull"></TD>



	</TR>

	<TR CLASS=common>
		<TD CLASS=title>保单生效日期</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=CValiDate id=CValiDate
			VALUE="<%= ValidDate %>" CLASS=common verify="保单生效日期|notnull&date">
		</TD>
	<TR CLASS=common>



	</TR>

	<TR CLASS=common>

		<TD CLASS=title>生存金领取方式</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=LiveGetMode id=LiveGetMode VALUE=""
			CLASS=readonly readonly TABINDEX=-1></TD>
		<TD CLASS=title>领取方式</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=getIntv id=getIntv></TD>


		<TD CLASS=title>起领日期计算类型</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=GetStartType id=GetStartType></TD>

	</TR>

	<TR CLASS=common>
		<TD CLASS=title>利差返还方式</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=InterestDifFlag id=InterestDifFlag></TD>
		<TD CLASS=title>减额交清标志</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=SubFlag id=SubFlag></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>生存金领取方式</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=LiveGetMode id=LiveGetMode></TD>
		<TD CLASS=title>是否自动续保</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=RnewFlag id=RnewFlag></TD>
	</TR>

</TABLE>
</DIV>

<DIV id=DivLCSubInsured STYLE="display: none"><!-- 连带被保人信息部分（列表） -->
<Div id="divLCInsured0" style="display: none">
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divLCInsured2);">
		</td>
		<td class=titleImg>连带被保人信息</td>
	</tr>
</table>

<Div id="divLCInsured2" style="display:  ">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanSubInsuredGrid">
		</span></td>
	</tr>
</table>
</div>

</div>

</DIV>

<!--后加的-->
<DIV id=DivInsured STYLE="display: none"><!-- 连带被保人信息部分（列表） -->
<Div id="divLCInsured0" style="display: none">
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divInsured1);"></td>
		<td class=titleImg>被保人信息</td>
	</tr>
</table>

<Div id="divInsured" style="display:  ">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanInsuredGrid">
		</span></td>
	</tr>
</table>
</div>

</div>

</DIV>
<!--以上后加的-->

<DIV id=DivLCImpart STYLE="display: none"><!-- 告知信息部分（列表） -->
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divLCImpart1);"></td>
		<td class=titleImg>告知信息</td>
	</tr>
</table>

<Div id="divLCImpart1" style="display:  ">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanImpartGrid">
		</span></td>
	</tr>
</table>
</div>

</DIV>

<DIV id=DivLCSpec STYLE="display:none ;"><!-- 特约信息部分（列表） -->
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divLCSpec1);"></td>
		<td class=titleImg>特约信息</td>
	</tr>
</table>

<Div id="divLCSpec1" style="display:  ">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanSpecGrid"> </span></td>
	</tr>
</table>
</div>

</DIV>


<DIV id=DivChooseDiscountGrid STYLE="display: ">
<table> 
			<tr>
				<td class=common><IMG src="../common/images/butExpand.gif" style="cursor: hand;" OnClick="showPage(this,divDiscount2);"></td>
				<td class=titleImg>可选折扣信息</td>
			</tr>
		</table>
		<Div id="divDiscount2" style="display:  ">
		<table  class= common>
<tr  class= common>
<td style="text-align: left" colSpan=1>
<span id="spanDiscountGrid" >
</span>
</td>
</tr>
</table>
		</div>
</DIV>




<DIV id=DivChooseDuty STYLE="display: none"><!--可以选择的责任部分，该部分始终隐藏-->
<Div id="divChooseDuty0" style="display: none">
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divDutyGrid1);"></td>
		<td class=titleImg>可选责任信息</td>
	</tr>
</table>

<Div id="divDutyGrid1" style="display:  ">
<table class=common>
	<tr class=common>
		<td style="text-align: left" colSpan=1><span id="spanDutyGrid"> </span>
		</td>
	</tr>
</table>
</div>
<div id= "divPremGrid1" style= "display:  ;">
	    	<table class= common>
	    		<tr class= common>
	    		<td style="text-align:left" colSpan="1">
	    			<span id="spanPremGrid" ></span>
	    		</td>
	    		</tr>
	    	</table>
	    </div>
</div>

</DIV>

<DIV id=DivPageEnd STYLE="display:  "><input type=hidden
	id="inpNeedDutyGrid" name="inpNeedDutyGrid" value="0"> <input
	type=hidden id="fmAction" name="fmAction"> <input type=hidden
	id="ContType" name="ContType" value=""> <input type="hidden"
	class=Common name=SelPolNo value=""> <input type=hidden
	id="inpNeedPremGrid" name="inpNeedPremGrid" value="0"> <input
	type='hidden' name=InputTime value=""> <!--需要在险种界面增加--> <input
	type='hidden' name="ProposalContNo" value=""> <input
	type='hidden' name="WorkFlowFlag" value=""> <input
	type='hidden' name="MissionID" value=""> <input type='hidden'
	name="SubMissionID" value=""> <input type='hidden'
	name="AppntNo" value=""> <!--end-->
<Div id="divButton" style="display:  "><br>
<%@include file="../common/jsp/ProposalOperateButton.jsp"%>
</DIV>
</DIV>
</form>

<span id="spanCode" style="display: none; position: absolute;"></span> <span
	id="spanApprove" style="display: none; position: relative;"></span>

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
window.top.opener.document.body.innerHTML=window.document.body.innerHTML;
window.top.opener.returnFromRiskInput('111301','<%=needSetValue%>');
haveMenu = 1;
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
alert("Riskxxx.jsp:发生错误："+ex);
}
}

top.close();
}

returnParent();
</script>



