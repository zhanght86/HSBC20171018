<!--
 * <p>FileName: \Risk111802.jsp </p>
 * <p>Description: ���ֽ����ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Author��Minim's ProposalInterfaceMaker
 * @CreateDate��2003-12-30
-->

<DIV id=DivPageHead STYLE="display: ''"><%@page
	contentType="text/html;charset=gb2312"%>
	<%@include file="../common/jsp/Log4jUI.jsp"%>   <%@page
	import="java.util.*"%> <%@page
	import="com.sinosoft.utility.*"%> <%@page
	import="com.sinosoft.lis.schema.*"%> <%@page
	import="com.sinosoft.lis.vschema.*"%> <%@page
	import="com.sinosoft.lis.tb.*"%> <%@page
	import="com.sinosoft.lis.pubfun.*"%> <%@page
	import="com.sinosoft.lis.cbcheck.*"%> <%@page
	import="com.sinosoft.lis.vbl.*"%><%@page 
	import="com.sinosoft.service.*" %> <%String CurrentDate = PubFun.getCurrentDate();
  Date dt = PubFun.calDate(new FDate().getDate(CurrentDate), 1, "D", null);
  String ValidDate = CurrentDate;
/////��ò���-���ֱ���
  String riskcode=request.getParameter("riskcode");
  String needSetValue = request.getParameter("needSetValue");
  loggerDebug("Risk111801","ҳ���Ƿ���Ҫ���ø�ֵ����=====" + needSetValue);
  
  String queryCode="!InsuYear-"+riskcode+"*0&0";
  String queryCode1="!PayIntv-"+riskcode+"*0&0";
  String queryCode2="!PayEndYear-"+riskcode+"*0&0";
  String queryCode3="!GetYear-"+riskcode+"*0&0";
  String queryCode4="!Getdutykind-"+riskcode+"*0&0";
   //////////////////////////////////
  String queryCodeApp="";
  ExeSQL rexeSql = new ExeSQL();
    SSRS riskFlagSSRS = new SSRS();
    //riskFlagSSRS = rexeSql.execSQL("select subriskflag from lmriskapp where riskcode='"+riskcode+"'");
    TransferData sqlTransferData = new TransferData();
	  VData sqlVData = new VData();
	  String sql="select subriskflag from lmriskapp where riskcode='"+riskcode+"'";
			    sqlTransferData.setNameAndValue("SQL",sql);
			    sqlVData.add(sqlTransferData);
			  	BusinessDelegate tsqlBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  	  if(!tsqlBusinessDelegate.submitData(sqlVData,"execSQL","ExeSQLUI"))
			  	  {    
			  	       if(tsqlBusinessDelegate.getCErrors()!=null&&tsqlBusinessDelegate.getCErrors().getErrorCount()>0)
			  	       { 
			  				loggerDebug("Risk111801","��ѯʧ�ܣ�ԭ����:" + tsqlBusinessDelegate.getCErrors().getFirstError());
			  		   }
			  		   else
			  		   {
			  			 	loggerDebug("Risk111801","��ѯʧ��");				
			  		   }
			  	  }
	riskFlagSSRS=(SSRS)tsqlBusinessDelegate.getResult().get(0);
    loggerDebug("Risk111801","�����ձ��====="+riskFlagSSRS.GetText(1,1));
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


<DIV id=DivRiskCode STYLE="display: ''">
<TABLE class=common>

	<TR CLASS=common>
		<TD CLASS=title>���ֱ���</TD>
		<TD CLASS=input COLSPAN=1><Input class=codeno name=RiskCode id=RiskCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
			ondblclick="showCodeList('<%=queryCodeApp%>',[this,RiskCodeName],[0,1],null,null,null,null,'400');"
			onkeyup="return showCodeListKey('<%=queryCodeApp%>',[this,RiskCodeName],[0,1],null,null,null,null,'400');"><input
			class=codename name=RiskCodeName id=RiskCodeName readonly=true elementtype=nacessary>
		</TD>
		<TD CLASS=title></TD>
		<TD CLASS=input></TD>
		<TD CLASS=title></TD>
		<TD CLASS=input></TD>
	</TR>

</TABLE>
</DIV>

<DIV id=DivRiskHidden STYLE="display: none">
<TABLE class=common>

	<TR CLASS=common>
		<TD CLASS=title>���ձ�������</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=MainPolNo NAME=MainPolNo></TD>
		<TD CLASS=title></TD>
		<TD CLASS=input></TD>
		<TD CLASS=title></TD>
		<TD CLASS=input></TD>
	</TR>

</TABLE>
</DIV>

<DIV id=DivLCPolHidden STYLE="display: none">
<TABLE class=common>

	<TR CLASS=common>
		<TD CLASS=title>���ְ汾</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=RiskVersion NAME=RiskVersion></TD>
		<TD CLASS=title>��ͬ��</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=ContNo111 NAME=ContNo111></TD>
		<TD CLASS=title>�����ͬ����</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=GrpContNo NAME=GrpContNo></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>���ڽ�������</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=FirstPayDate NAME=FirstPayDate></TD>
		<TD CLASS=title>����</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=Lang NAME=Lang></TD>
		<TD CLASS=title>��������</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=Currency NAME=Currency></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>��ͬ���鴦��ʽ</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=DisputedFlag NAME=DisputedFlag></TD>
		<TD CLASS=title>���д��ձ��</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=AgentPayFlag NAME=AgentPayFlag></TD>
		<TD CLASS=title>���д������</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=AgentGetFlag NAME=AgentGetFlag></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>������</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=Handler NAME=Handler></TD>
		<TD CLASS=title>���ϴ����˱���</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=AgentCode1 NAME=AgentCode1></TD>
	</TR>

</TABLE>
</DIV>

<DIV id=DivLCPolButton STYLE="display: none"><!-- ������Ϣ���� -->
<table>
	<tr>
		<td><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,DivLCPol);"></td>
		<td class=titleImg>������Ϣ <INPUT VALUE="��ѯ������Ϣ" TYPE=button class=cssButton
			onclick="showDuty();"> <INPUT VALUE="�����ݽ�����Ϣ" TYPE=button class=cssButton
			onclick="showFee();"> <!--<INPUT id="butChooseDuty" VALUE="ѡ������" TYPE=button class=cssButton onclick="chooseDuty();" disabled >
<INPUT id="butBack" VALUE="����" TYPE=button class=cssButton disabled >--></td>
	</tr>
</table>

</DIV>

<DIV id=DivLCPol STYLE="display: none">
<TABLE class=common>

	<TR CLASS=common>
		<TD CLASS=title>Ͷ��������</TD>
		<TD CLASS=input COLSPAN=1><Input id=ProposalNo NAME=ProposalNo VALUE=""
			class="readonly wid" readonly TABINDEX=-1 MAXLENGTH=11></TD>
		<TD CLASS=title>ӡˢ����</TD>
		<TD CLASS=input COLSPAN=1><Input id=PrtNo NAME=PrtNo VALUE=""
			class="readonly wid" readonly TABINDEX=-1 MAXLENGTH=11
			verify="ӡˢ����|notnull&len=11"></TD>
		<TD CLASS=title>�������</TD>
		<TD CLASS=input COLSPAN=1><Input id=ManageCom NAME=ManageCom VALUE=""
			MAXLENGTH=10 CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('comcode',[this],null,null,'#1# and Length(trim(comcode))=8','1',1);"
			onkeyup="return showCodeListKey('comcode',[this],null,null,'#1# and Length(trim(comcode))=8','1',1);"
			verify="�������|code:station&notnull"></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>��������</TD>
		<TD CLASS=input COLSPAN=1><Input id=SaleChnl NAME=SaleChnl VALUE=""
			CLASS="wid common" MAXLENGTH=2></TD>
		<TD CLASS=title>�����˱���</TD>
		<TD CLASS=input COLSPAN=1><Input id=AgentCode NAME=AgentCode VALUE="" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			MAXLENGTH=10 CLASS=code ondblclick="return queryAgent();"
			onkeyup="return queryAgent2();"></TD>
		<TD CLASS=title>���������</TD>
		<TD CLASS=input COLSPAN=1><Input id=AgentGroup NAME=AgentGroup VALUE=""
			class="readonly wid" readonly TABINDEX=-1 MAXLENGTH=12
			verify="���������|notnull"></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>��ע</TD>
		<TD CLASS=input COLSPAN=3><Input id=Remark NAME=Remark VALUE="" CLASS=common3
			MAXLENGTH=255></TD>
		<TD CLASS=title>��ͬ����</TD>
		<TD CLASS=input COLSPAN=1><Input id=ContNo NAME=ContNo VALUE=""
			class="readonly wid" readonly TABINDEX=-1 MAXLENGTH=14></TD>

	</TR>

	<TR CLASS=common>
		<TD CLASS=title>�������</TD>
		<TD CLASS=input COLSPAN=1><Input id=AgentCom NAME=AgentCom VALUE=""
			CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="showCodeList('AgentCom',[this],null,null,'#1# and ManageCom like #' + fm.all('ManageCom').value.substring(0,4) + '%# and #' + fm.all('ManageCom').value + '# is not null  ','1');"
			onkeyup="return showCodeListKey('AgentCom',[this],null,null,'#1# and ManageCom like #' + fm.all('ManageCom').value.substring(0,4) + '%# and #' + fm.all('ManageCom').value + '# is not null  ','1');"
			verify="�������|code:AgentCom"></TD>
		<TD CLASS=title>����Ӫҵ����</TD>
		<TD CLASS=input COLSPAN=1><Input id=AgentType NAME=AgentType VALUE=""
			CLASS="wid common"></TD>
	</TR>

</TABLE>
</DIV>

<DIV id=DivLCAppntIndButton STYLE="display: none"><!-- Ͷ������Ϣ���� -->
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,DivLCAppntInd);">
		</td>
		<td class=titleImg>Ͷ������Ϣ���ͻ��ţ�<Input class=common
			name=AppntCustomerNo id=AppntCustomerNo> <INPUT id="butBack" VALUE="��ѯ"
			TYPE=button class=cssButton onclick="queryAppntNo();"> �״�Ͷ���ͻ�������д�ͻ��ţ�</td>
	</tr>
</table>

</DIV>

<DIV id=DivLCAppntInd STYLE="display: none">
<TABLE class=common>

	<TR CLASS=common>
		<TD CLASS=title>����</TD>
		<TD CLASS=input COLSPAN=1><Input id=AppntName NAME=AppntName VALUE=""
			CLASS="wid common" MAXLENGTH=20 verify="Ͷ��������|notnull"></TD>
		<TD CLASS=title>�Ա�</TD>
		<TD CLASS=input COLSPAN=1><Input id=AppntSex NAME=AppntSex VALUE=""
			MAXLENGTH=1 CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('Sex', [this]);"
			onkeyup="return showCodeListKey('Sex', [this]);"
			verify="Ͷ�����Ա�|notnull&code:Sex"></TD>
		<TD CLASS=title>��������</TD>
		<TD CLASS=input COLSPAN=1><Input id=AppntBirthday NAME=AppntBirthday VALUE=""
			CLASS="wid common" verify="Ͷ���˳�������|notnull&date"></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>�뱻���˹�ϵ</TD>
		<TD CLASS=input COLSPAN=1><Input id=AppntRelationToInsured NAME=AppntRelationToInsured
			VALUE="" MAXLENGTH=2 CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('Relation', [this]);"
			onkeyup="return showCodeListKey('Relation', [this]);"
			verify="Ͷ�����뱻�����˹�ϵ|code:Relation&notnull"></TD>
		<TD CLASS=title>֤������</TD>
		<TD CLASS=input COLSPAN=1><Input id=AppntIDType NAME=AppntIDType VALUE=""
			MAXLENGTH=1 CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('IDType', [this]);"
			onkeyup="return showCodeListKey('IDType', [this]);"
			verify="Ͷ����֤������|code:IDType"></TD>
		<TD CLASS=title>֤������</TD>
		<TD CLASS=input COLSPAN=1><Input id=AppntIDNo NAME=AppntIDNo VALUE=""
			CLASS="wid common" MAXLENGTH=20></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>����</TD>
		<TD CLASS=input COLSPAN=1><Input id=AppntNativePlace NAME=AppntNativePlace VALUE="" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			CLASS=code ondblclick="return showCodeList('NativePlace', [this]);"
			onkeyup="return showCodeListKey('NativePlace', [this]);"
			verify="Ͷ���˹���|code:NativePlace"></TD>
		<TD CLASS=title>�������ڵ�</TD>
		<TD CLASS=input COLSPAN=1><Input id=AppntRgtAddress NAME=AppntRgtAddress VALUE=""
			CLASS="wid common" MAXLENGTH=80></TD>
		<TD><B></B></TD>
		<TD COLSPAN=1></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>ͨѶ��ַ</TD>
		<TD CLASS=input COLSPAN=3><Input id=AppntPostalAddress NAME=AppntPostalAddress VALUE=""
			CLASS=common3 MAXLENGTH=80></TD>
		<TD CLASS=title>ͨѶ��ַ��������</TD>
		<TD CLASS=input COLSPAN=1><Input id=AppntZipCode NAME=AppntZipCode VALUE=""
			CLASS="wid common" MAXLENGTH=6 verify="Ͷ������������|zipcode"></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>סַ</TD>
		<TD CLASS=input COLSPAN=3><Input id=AppntHomeAddress NAME=AppntHomeAddress VALUE=""
			CLASS=common3 MAXLENGTH=80></TD>
		<TD CLASS=title>סַ��������</TD>
		<TD CLASS=input COLSPAN=1><Input id=AppntHomeZipCode NAME=AppntHomeZipCode VALUE=""
			CLASS="wid common" MAXLENGTH=6 verify="Ͷ����סַ��������|zipcode"></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>��ϵ�绰��1��</TD>
		<TD CLASS=input COLSPAN=1><Input id=AppntPhone NAME=AppntPhone VALUE=""
			CLASS="wid common" MAXLENGTH=18></TD>
		<TD CLASS=title>��ϵ�绰��2��</TD>
		<TD CLASS=input COLSPAN=1><Input id=AppntPhone2 NAME=AppntPhone2 VALUE=""
			CLASS="wid common" MAXLENGTH=18></TD>
		<TD CLASS=title>�ƶ��绰</TD>
		<TD CLASS=input COLSPAN=1><Input id=AppntMobile NAME=AppntMobile VALUE=""
			CLASS="wid common" MAXLENGTH=15></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>��������</TD>
		<TD CLASS=input COLSPAN=1><Input id=AppntEMail NAME=AppntEMail VALUE=""
			CLASS="wid common" MAXLENGTH=60></TD>
		<TD CLASS=title>������λ</TD>
		<TD CLASS=input COLSPAN=1><Input id=AppntGrpName NAME=AppntGrpName VALUE=""
			CLASS="wid common" MAXLENGTH=60></TD>
		<TD CLASS=title>ְҵ�����֣�</TD>
		<TD CLASS=input COLSPAN=1><Input id=AppntWorkType NAME=AppntWorkType VALUE=""
			CLASS="wid common"></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>��ְ�����֣�</TD>
		<TD CLASS=input COLSPAN=1><Input id=AppntPluralityType NAME=AppntPluralityType VALUE=""
			CLASS="wid common"></TD>
		<TD CLASS=title>ְҵ����</TD>
		<TD CLASS=input COLSPAN=1><Input id=AppntOccupationCode NAME=AppntOccupationCode
			VALUE="" CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('OccupationCode', [this,AppntOccupationType],[0,2]);"
			onkeyup="return showCodeListKey('OccupationCode', [this,AppntOccupationType],[0,2]);"
			verify="Ͷ����ְҵ����|code:OccupationCode"></TD>
		<TD CLASS=title>ְҵ���</TD>
		<TD CLASS=input COLSPAN=1><Input id=AppntOccupationType NAME=AppntOccupationType
			VALUE="" class="readonly wid" readonly TABINDEX=-1
			verify="������ְҵ���|code:OccupationType"></TD>
	</TR>

	<TR CLASS=common>
	</TR>

</TABLE>
</DIV>

<DIV id=DivLCAppntGrpButton STYLE="display: none"><!-- ����Ͷ������Ϣ���� -->
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,DivLCAppntGrp);">
		</td>
		<td class=titleImg>Ͷ����λ����</td>
	</tr>
</table>

</DIV>

<DIV id=DivLCAppntGrp STYLE="display: none">
<TABLE class=common>

	<TR CLASS=common>
		<TD CLASS=title>��λ�ͻ���</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=AppGrpNo class=wid NAME=AppGrpNo></TD>
		<TD CLASS=title>��λ����</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=AppGrpName NAME=AppGrpName></TD>
		<TD CLASS=title>��λ��ַ</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=AppGrpAddress></TD>
	</TR> NAME=AppGrpAddress></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>��������</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=AppGrpZipCode NAME=AppGrpZipCode></TD>
		<TD CLASS=title>��λ����</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=GrpNature NAME=GrpNature></TD>
		<TD CLASS=title>��ҵ���</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=BusinessType NAME=BusinessType></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>��λ������</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=Peoples NAME=Peoples></TD>
		<TD CLASS=title>��Ӫҵ��</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=MainBussiness NAME=MainBussiness></TD>
		<TD CLASS=title>��λ���˴���</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=Corporation NAME=Corporation></TD>
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
		<TD CLASS=input COLSPAN=1><Input class=wid id=LinkMan1 NAME=LinkMan1></TD>
		<TD CLASS=title>����</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=Department1 NAME=Department1></TD>
		<TD CLASS=title>��ϵ�绰</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=GrpPhone1 NAME=GrpPhone1></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>��ϵ�绰</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=GrpPhone1 NAME=GrpPhone1></TD>
		<TD CLASS=title>E_mail</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=E_Mail1 NAME=E_Mail1></TD>
		<TD CLASS=title>����</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=Fax1 NAME=Fax1></TD>
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
		<TD CLASS=input COLSPAN=1><Input class=wid id=LinkMan2 NAME=LinkMan2></TD>
		<TD CLASS=title>����</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=Department2 NAME=Department2></TD>
		<TD CLASS=title>��ϵ�绰</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=GrpPhone2 NAME=GrpPhone2></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>E_mail</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=E_Mail2 NAME=E_Mail2></TD>
		<TD CLASS=title>����</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=Fax2 NAME=Fax2></TD>
		<TD><B></B></TD>
		<TD COLSPAN=1></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>���ʽ</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=GetFlag NAME=GetFlag></TD>
		<TD CLASS=title>��������</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=GrpBankCode NAME=GrpBankCode></TD>
		<TD CLASS=title>�����ʺ�</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=GrpBankAccNo NAME=GrpBankAccNo></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>��������</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=Currency NAME=Currency></TD>
	</TR>

</TABLE>
</DIV>

<DIV id=DivLCInsuredButton STYLE="display: none"><!-- ��������Ϣ���� -->
<table>
	<tr>
		<td class=common><!--<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsured);">-->
		</td>
		<td class=titleImg>��������Ϣ���ͻ��ţ�<Input class=common name=CustomerNo id=CustomerNo>
		<INPUT id="butBack" VALUE="��ѯ" TYPE=button class=cssButton onclick="queryInsuredNo();">
		�״�Ͷ���ͻ�������д�ͻ��ţ�
		<Div id="divSamePerson" style="display: ''"><font color=red>
		��Ͷ����Ϊ�������˱��ˣ������������ѡ�� <INPUT TYPE="checkbox" NAME="SamePersonFlag"
			onclick="isSamePerson();"> </font></div>
		</td>
	</tr>
</table>

</DIV>

<DIV id=DivLCInsured STYLE="display: none">
<TABLE class=common>

	<TR CLASS=common>
		<TD CLASS=title>����</TD>
		<TD CLASS=input COLSPAN=1><Input id=Name NAME=Name VALUE="" CLASS="wid common"
			MAXLENGTH=20 verify="����|notnull"></TD>
		<TD CLASS=title>�Ա�</TD>
		<TD CLASS=input COLSPAN=1><Input id=Sex NAME=Sex VALUE="" MAXLENGTH=1 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			CLASS=code ondblclick="return showCodeList('Sex', [this]);"
			onkeyup="return showCodeListKey('Sex', [this]);"
			verify="�������Ա�|notnull&code:Sex"></TD>
		<TD CLASS=title>��������</TD>
		<TD CLASS=input COLSPAN=1><Input id=Birthday NAME=Birthday VALUE=""
			CLASS="wid common" verify="�����˳�������|date&notnull"></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>֤������</TD>
		<TD CLASS=input COLSPAN=1><Input id=IDType NAME=IDType VALUE="" MAXLENGTH=1 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			CLASS=code ondblclick="return showCodeList('IDType', [this]);"
			onkeyup="return showCodeListKey('IDType', [this]);"
			verify="������֤������|code:IDType"></TD>
		<TD CLASS=title>֤������</TD>
		<TD CLASS=input COLSPAN=1><Input id=IDNo NAME=IDNo VALUE="" CLASS="wid common"
			MAXLENGTH=20></TD>
		<TD CLASS=title>����</TD>
		<TD CLASS=input COLSPAN=1><Input id=NativePlace NAME=NativePlace VALUE="" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			CLASS=code ondblclick="return showCodeList('NativePlace', [this]);"
			onkeyup="return showCodeListKey('NativePlace', [this]);"
			verify="�����˹���|code:NativePlace"></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>�������ڵ�</TD>
		<TD CLASS=input COLSPAN=1><Input id=RgtAddress NAME=RgtAddress VALUE=""
			CLASS="wid common" MAXLENGTH=80></TD>
		<TD CLASS=title>סַ</TD>
		<TD CLASS=input COLSPAN=3><Input id=HomeAddress NAME=HomeAddress VALUE=""
			CLASS=common3 MAXLENGTH=80></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>��������</TD>
		<TD CLASS=input COLSPAN=1><Input id=HomeZipCode NAME=HomeZipCode VALUE=""
			CLASS="wid common" MAXLENGTH=6 verify="��������������|zipcode"></TD>
		<TD CLASS=title>��ϵ�绰��1��</TD>
		<TD CLASS=input COLSPAN=1><Input id=Phone NAME=Phone VALUE="" CLASS="wid common"
			MAXLENGTH=18></TD>
		<TD CLASS=title>��ϵ�绰��2��</TD>
		<TD CLASS=input COLSPAN=1><Input id=Phone2 NAME=Phone2 VALUE=""
			CLASS="wid common"></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>������λ</TD>
		<TD CLASS=input COLSPAN=1><Input id=GrpName NAME=GrpName VALUE=""
			CLASS="wid common" MAXLENGTH=60></TD>
		<TD CLASS=title>ְҵ�����֣�</TD>
		<TD CLASS=input COLSPAN=1><Input id=WorkType NAME=WorkType VALUE=""
			CLASS="wid common"></TD>
		<TD CLASS=title>��ְ�����֣�</TD>
		<TD CLASS=input COLSPAN=1><Input id=PluralityType NAME=PluralityType VALUE=""
			CLASS="wid common"></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>ְҵ����</TD>
		<TD CLASS=input COLSPAN=1><Input id=OccupationCode NAME=OccupationCode VALUE=""
			CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('OccupationCode', [this,OccupationType],[0,2]);"
			onkeyup="return showCodeListKey('OccupationCode', [this,OccupationType],[0,2]);"
			verify="������ְҵ����|code:OccupationCode"></TD>
		<TD CLASS=title>ְҵ���</TD>
		<TD CLASS=input COLSPAN=1><Input id=OccupationType NAME=OccupationType VALUE=""
			class="readonly wid" readonly TABINDEX=-1
			verify="������ְҵ���|code:OccupationType"></TD>
	</TR>

</TABLE>
</DIV>

<DIV id=DivLCInsuredHidden STYLE="display: none">
<TABLE class=common>

	<TR CLASS=common>
		<TD CLASS=title>����״��</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=Health NAME=Health></TD>
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
<DIV id=DivLCBnf STYLE="display: ''"><!-- ��������Ϣ���֣��б� -->
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
		<td text-align: left colSpan=1><span id="spanBnfGrid"> </span></td>
	</tr>
</table>
</div>

</DIV>

<DIV id=DivLCKindButton STYLE="display: ''"><!-- ������Ϣ���� -->
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,DivLCKind);"></td>
		<td class=titleImg>������Ϣ</td>
	</tr>
</table>

</DIV>

<DIV id=DivLCKind STYLE="display: ''">
<TABLE class=common>

	<TR CLASS=common>

		<TD CLASS=title>����</TD>
		<TD CLASS=input COLSPAN=1><Input id=mult NAME=Mult MAXLENGTH=12
			CLASS="wid common" verify="����|notnull"></TD>
			<td class=title>����</td>
    					<td class=input><Input class="wid common" name=CurrencyCode readonly=true value='01'></td>
	</TR>
	<TR CLASS=common>
		<TD CLASS=title>����</TD>
		<TD CLASS=input COLSPAN=1><Input id=Prem NAME=Prem VALUE="" MAXLENGTH=12
			class="readonly wid" readonly></TD>

		<TD CLASS=title>����</TD>
		<TD CLASS=input COLSPAN=1><Input id=Amnt NAME=Amnt VALUE="" MAXLENGTH=12
			class="readonly wid" readonly></TD>
	</TR>



</TABLE>
</DIV>

<DIV id=DivLCKindHidden STYLE="display: none">
<TABLE class=common>
	<TR CLASS=common>
		<TD CLASS=title>�����ڼ�</TD>
		<TD CLASS=input COLSPAN=1><Input class=codeno name=InsuYear id=InsuYear
			verify="�����ڼ�|code:!InsuYear&notnull" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('<%=queryCode%>',[this,InsuYearName],[0,1]);"
			onkeyup="return showCodeListKey('<%=queryCode%>',[this,InsuYearName],[0,1]);"><input
			class=codename name=InsuYearName id=InsuYearName readonly=true elementtype=nacessary>
		</TD>

		<TD CLASS=title>�����ڼ䵥λ</TD>
		<TD CLASS=input COLSPAN=1><Input id=InsuYearFlag NAME=InsuYearFlag VALUE="A"
			class="readonly wid" readonly TABINDEX=-1 verify="�����ڼ䵥λ|notnull">
		</TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>�����ڼ�</TD>
		<TD CLASS=input COLSPAN=1><Input class=codeno name=GetYear id=GetYear
			verify="�����ڼ�|code:!GetYear&notnull" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('<%=queryCode3%>',[this,GetYearName],[0,1]);"
			onkeyup="return showCodeListKey('<%=queryCode3%>',[this,GetYearName],[0,1]);"><input
			class=codename name=GetYearName id=GetYearName readonly=true elementtype=nacessary>
		</TD>

		<TD CLASS=title>�����ڼ䵥λ</TD>
		<TD CLASS=input COLSPAN=1><Input NAME=GetYearFlag id=GetYearFlag VALUE="A"
			class="readonly wid" readonly TABINDEX=-1 verify="�����ڼ䵥λ|notnull">
		</TD>
	</TR>

	<TR CLASS=common>

		<TD CLASS=title>���ѷ�ʽ</TD>
		<TD CLASS=input COLSPAN=1><Input class=codeno name=PayIntv id=PayIntv
			verify="�ɷѷ�ʽ|code:!PayIntv&notnull"
			ondblclick="return showCodeList('<%=queryCode1%>',[this,PayIntvName],[0,1]);"
			onkeyup="return showCodeListKey('<%=queryCode1%>',[this,PayIntvName],[0,1]);"><input
			class=codename name=PayIntvName id=PayIntvName readonly=true elementtype=nacessary>
		</TD>

		<TD CLASS=title>��������</TD>
		<TD CLASS=input COLSPAN=1><Input class=codeno name=GetDutyKind id=GetDutyKind
			verify="��������|code:!GetDutyKind&notnull" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('<%=queryCode4%>',[this,GetDutyKindName],[0,1]);"
			onkeyup="return showCodeListKey('<%=queryCode4%>',[this,GetDutyKindName],[0,1]);"><input
			class=codename name=GetDutyKindName id=GetDutyKindName readonly=true
			elementtype=nacessary></TD>


	</TR>

	<TR CLASS=common>

		<TD CLASS=title>�����ڼ�</TD>
		<TD CLASS=input COLSPAN=1><Input class=codeno name=PayEndYear id=PayEndYear
			verify="�����ڼ�|code:!PayEndYear&notnull" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('<%=queryCode2%>',[this,PayEndYearName],[0,1]);"
			onkeyup="return showCodeListKey('<%=queryCode2%>',[this,PayEndYearName],[0,1]);"><input
			class=codename name=PayEndYearName id=PayEndYearName readonly=true
			elementtype=nacessary></TD>

		<TD CLASS=title>�����ڼ䵥λ</TD>
		<TD CLASS=input COLSPAN=1><Input id=PayEndYearFlag NAME=PayEndYearFlag VALUE="Y"
			class="readonly wid" readonly TABINDEX=-1 verify="�����ڼ䵥λ|notnull">
		</TD>

	</TR>




	<TR CLASS=common>

		<TD CLASS=title>������ȡ��ʽ</TD>
		<TD CLASS=input COLSPAN=1><Input id=BonusGetMode NAME=BonusGetMode VALUE="5"
			class="readonly wid" readonly TABINDEX=-1></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>�Զ��潻��־</TD>
		<TD CLASS=input COLSPAN=1><Input id=AutoPayFlag NAME=AutoPayFlag VALUE=""
			CLASS=code CodeData="0|^0|���Զ��潻^1|�Զ��潻"
			ondblClick="showCodeListEx('AutoPayFlag',[this],[0]);"
			onkeyup="showCodeListKeyEx('AutoPayFlag',[this],[0]);"
			verify="�Զ��潻��־|notnull"></TD>



	</TR>

	<TR CLASS=common>
		<TD CLASS=title>������Ч����</TD>
		<TD CLASS=input COLSPAN=1><Input id=CValiDate NAME=CValiDate
			VALUE="<%= ValidDate %>" CLASS="wid common" verify="������Ч����|notnull&date">
		</TD>
	<TR CLASS=common>



	</TR>

	<TR CLASS=common>

		<TD CLASS=title>�������ȡ��ʽ</TD>
		<TD CLASS=input COLSPAN=1><Input id=LiveGetMode NAME=LiveGetMode class="readonly wid"
			readonly TABINDEX=-1></TD>
		<TD CLASS=title>��ȡ��ʽ</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=getIntv NAME=getIntv></TD>


		<TD CLASS=title>�������ڼ�������</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=GetStartType NAME=GetStartType></TD>

	</TR>

	<TR CLASS=common>
		<TD CLASS=title>�������ʽ</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=InterestDifFlag NAME=InterestDifFlag></TD>
		<TD CLASS=title>������־</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=SubFlag NAME=SubFlag></TD>
	</TR>

	<TR CLASS=common>
		<TD CLASS=title>�������ȡ��ʽ</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=LiveGetMode NAME=LiveGetMode></TD>
		<TD CLASS=title>�Ƿ��Զ�����</TD>
		<TD CLASS=input COLSPAN=1><Input class=wid id=RnewFlag NAME=RnewFlag></TD>
	</TR>

</TABLE>
</DIV>

<DIV id=DivLCSubInsured STYLE="display: none"><!-- ������������Ϣ���֣��б� -->
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
<DIV id=DivInsured STYLE="display: none"><!-- ������������Ϣ���֣��б� -->
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

<DIV id=DivLCImpart STYLE="display: none"><!-- ��֪��Ϣ���֣��б� -->
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

<DIV id=DivLCSpec STYLE="display: none"><!-- ��Լ��Ϣ���֣��б� -->
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
		<td text-align: left colSpan=1><span id="spanSpecGrid"> </span></td>
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


<DIV id=DivChooseDuty STYLE="display: none"><!--����ѡ������β��֣��ò���ʼ������-->
<Div id="divChooseDuty0" style="display: none">
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divDutyGrid1);"></td>
		<td class=titleImg>��ѡ������Ϣ</td>
	</tr>
</table>

<Div id="divDutyGrid1" style="display: ''">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanDutyGrid"> </span>
		</td>
	</tr>
</table>
</div>
</div>

</DIV>

<DIV id=DivPageEnd STYLE="display: ''"><input type=hidden
	id="inpNeedDutyGrid" name="inpNeedDutyGrid" value="0"> <input
	type=hidden id="fmAction" name="fmAction"> <input type=hidden
	id="ContType" name="ContType" value=""> <input type="hidden"
	class=Common name=SelPolNo value=""> <input type=hidden
	id="inpNeedPremGrid" name="inpNeedPremGrid" value="0"> <input
	type='hidden' name=InputTime value=""> <!--��Ҫ�����ֽ�������--> <input
	type='hidden' name="ProposalContNo" value=""> <input
	type='hidden' name="WorkFlowFlag" value=""> <input
	type='hidden' name="MissionID" value=""> <input type='hidden'
	name="SubMissionID" value=""> <input type='hidden'
	name="AppntNo" value=""> <!--end-->
<Div id="divButton" style="display: ''"><br>
<%@include file="../common/jsp/ProposalOperateButton.jsp"%>
</DIV>
<br /><br /><br /><br />
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
if(isDia==0) { //����Ǵ�һ���µĴ��ڣ���ִ������Ĵ���
window.top.opener.document.body.innerHTML=window.document.body.innerHTML;
window.top.opener.returnFromRiskInput('111801','<%=needSetValue%>');
haveMenu = 1;
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
alert("Riskxxx.jsp:��������"+ex.name);
}
}

top.close();
}

returnParent();
</script></DIV>



