<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//�õ�����ĵ���λ��,Ĭ��Ϊ1,��ʾ���˱���ֱ��¼��.
// 1 -- ����Ͷ����ֱ��¼��
// 2 -- �����¸���Ͷ����¼��
// 3 -- ����Ͷ������ϸ��ѯ
// 4 -- ���帴��
// 5 -- ����
// 6 -- ��ѯ
// 7 -- ��ȫ�±�����
// 8 -- ��ȫ����������
// 9 -- ������������
// 10-- ��������
// 13-- �ŵ������޸�
// 14-- �ŵ��˱��޸�
// 16-- �ŵ���ϸ��ѯ
// 99-- �涯����

String tLoadFlag ="";
String tGrpContNo ="";

try
{
	tLoadFlag =request.getParameter( "LoadFlag" );
	tGrpContNo =request.getParameter( "GrpContNo" );
	//Ĭ�������Ϊ���˱���ֱ��¼��
	if( tLoadFlag ==null || tLoadFlag.equals( "" ))
		tLoadFlag ="2"; //LoadFlag����� ����ؼ������ڸ�������
}
catch( Exception e1 )
{
	tLoadFlag ="2";
}

GlobalInput tGI =new GlobalInput();
tGI =(GlobalInput)session.getValue("GI");
loggerDebug("ContPolInput","LoadFlag:" + tLoadFlag);
loggerDebug("ContPolInput","ɨ������:" + request.getParameter("scantype"));
%>
<script>
var prtNo ="<%=request.getParameter("prtNo")%>";
var polNo ="<%=request.getParameter("polNo")%>";
var scantype ="<%=request.getParameter("scantype")%>";
var MissionID ="<%=request.getParameter("MissionID")%>";
var ManageCom ="<%=request.getParameter("ManageCom")%>";
var SubMissionID ="<%=request.getParameter("SubMissionID")%>";
var ActivityID = "<%=request.getParameter("ActivityID")%>";
var NoType = "<%=request.getParameter("NoType")%>";
var GrpContNo ="<%=request.getParameter("GrpContNo")%>";
var ScanFlag ="<%=request.getParameter("ScanFlag")%>";
var PolApplyDate="<%=request.getParameter("PolApplyDate")%>";
if (ScanFlag ==null||ScanFlag=="null") ScanFlag="0";
if (polNo =="null") polNo ="";
if (prtNo =="null") prtNo ="";
var LoadFlag ="<%=tLoadFlag%>";

var tLoadFlag ="<%=tLoadFlag%>";
//��ȫ���ûᴫ2����������Ĭ��Ϊ0������ֵ�ڱ������е�appflag�ֶ�
var BQFlag ="<%=request.getParameter("BQFlag")%>";
if (BQFlag ==null||BQFlag=="null") BQFlag ="0";
//��ȫ���ûᴫ���ֹ���
var BQRiskCode ="<%=request.getParameter("riskCode")%>";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="ContPolInit.jsp"%>
<SCRIPT src="ContPolInput.js"></SCRIPT>
<SCRIPT src="ProposalAutoMove5.js"></SCRIPT>
<%
if(request.getParameter("scantype")!=null&&request.getParameter("scantype").equals("scan"))
{
%>
<SCRIPT src="../common/EasyScanQuery/ShowPicControl.js"></SCRIPT>
<SCRIPT>window.document.onkeydown =document_onkeydown;</SCRIPT>
<%
}
%>
</head>
<body  onload="initForm();initElementtype();checkNotePad(prtNo,LoadFlag);">
	<form action="./ContPolSave.jsp" method=post name=fm id=fm target="fraSubmit">
		<!-- ��ͬ��Ϣ���� GroupPolSave.jsp-->
		<DIV id=DivLCContButton STYLE="display:''">
			<table id="table1">
				<tr>
					<td class="common">
						<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol1);">
					</td>
					<td class="titleImg">�����ͬ��Ϣ</td>
				</tr>
			</table>
		</DIV>
		<Div id="divGroupPol1" style="display: ''" class="maxbox">
			<table border="0" class=common>
				<tr class=common>
					<!--TD class=title>
						����Ͷ��������
					</td>
					<td class=input>
						<input class="common" name=ProposalGrpContNo readonly TABINDEX="-1"  MAXLENGTH="40">
					</TD-->
					<td class="title">Ͷ��������</td>
					<td class="input">
						<input class="common wid" name=PrtNo id=PrtNo elementtype=nacessary TABINDEX="-1" MAXLENGTH="14" verify="Ͷ��������|notnull&len<=14">
					</td>
				  <td class="title">�ʱ�����</td>
					<td class="input">
            <input class="common wid" name="ReportNo" id="ReportNo">
					</td>
				  <td class="title">����Э�����</td>
					<td class="input">
            <input class="common wid" name="AgentConferNo" id="AgentConferNo">
					</td>
					<!--
					<td class=title>�������</td>
					<td class=input>
						<input class="code" name=ManageCom ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
						<input class=codeno name=ManageCom verify="�������|code:comcode&notnull" ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,null,null,1);"><input class=codename name=ManageComName readonly=true elementtype=nacessary>
					</td>
					-->
				</tr>
				<tr class=common>
					 <td class="title">����Э�����</td>
					<td class="input">
            <input class="common wid" name="ConferNo" id="ConferNo">
					</td>
				  <td class="title">ǩ����</td>
					<td class="input">
            <input class="common wid" name="SignReportNo" id="SignReportNo">
					</td>
					<td class=title>Ͷ����������</td>
					<td class=input>
						<!--<input class="coolDatePicker" elementtype=nacessary dateFormat="short" onblur="checkapplydate();" name=PolApplyDate verify="Ͷ����������|notnull&DATE verifyorder="1"">-->
                        <Input class="coolDatePicker" onClick="laydate({elem: '#PolApplyDate'});" verify="Ͷ����������|notnull&DATE verifyorder="1"" dateFormat="short" name=PolApplyDate id="PolApplyDate"><span class="icon"><a onClick="laydate({elem: '#ValidStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
					</td>

				</tr> 
				<tr class=common>
						<td class=title>������Ч����</td>
					<td class=input>
						<!--<input class="common" dateFormat="short" elementtype=nacessary onblur="checkCValidate();" name=CValiDate verify="��Ч����|notnull&DATE">-->
                        <Input class="coolDatePicker" onblur="checkCValidate();" onClick="laydate({elem: '#CValiDate'});" verify="��Ч����|notnull&DATE" dateFormat="short" name=CValiDate id="CValiDate"><span class="icon"><a onClick="laydate({elem: '#CValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
					</td>			    		

			    <td CLASS="title">�������
    	    </td>
			    <td CLASS="input">
			      <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" NAME="ManageCom" id="ManageCom"  verifyorder="1" VALUE MAXLENGTH="10" CLASS="codeno" ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" verify="�������|code:station&amp;notnull"><input NAME="ManageComName" id="ManageComName" elementtype=nacessary CLASS="codename" readonly>
    	    </td>
    	    <td class="title">�����շ�����</td>
					<td class="input">
            <input class="common wid" name="PayDate" id="PayDate" readonly>
					</td>

				</tr>
				<tr>
				    <td class="title">���۷�ʽ</td>		
				    <td class="input">
						<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=AgentType id=AgentType verify="���۷�ʽ|notnull" ondblclick="return showCodeList('AgentType',[this,AgentTypeName],[0,1],null,null,null,1);"onclick="return showCodeList('AgentType',[this,AgentTypeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('AgentType',[this,AgentTypeName],[0,1],null,null,null,1);"><input class=codename name=AgentTypeName id=AgentTypeName readonly=true elementtype=nacessary>
					</td>
					<td class=title>�н����</td>
					<td class=input>
						<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=MediAgentCom id=MediAgentCom ondblclick="return showCodeList('GrpAgentCom',[this,MediAgentComName],[0,1],null,'managecom like #'+ fm.ManageCom.value + '%# and Branchtype=#2# and actype=#' + fm.AgentType.value + '#','1',1);" onclick="return showCodeList('GrpAgentCom',[this,MediAgentComName],[0,1],null,'managecom like #'+ fm.ManageCom.value + '%# and Branchtype=#2# and actype=#' + fm.AgentType.value + '#','1',1);" onkeyup="return showCodeListKey('GrpAgentCom',[this,MediAgentComName],[0,1],null,'managecom like #'+ fm.ManageCom.value + '%# and actype=#' + fm.AgentType.value + '#','1',1);"><input class=codename name=MediAgentComName id=MediAgentComName readonly=true>
					</td>
					<td class=title>�н����ҵ��Ա</td>
					<td class=input>
						<input class="wid" NAME=MediAgentCode id=MediAgentCode VALUE="" CLASS=common>
					</td>
				</tr>
<!--
				<tr class = common>
					  
				</tr>
-->
<!--
				<tr class=common>
					<td class=title>�������</td>
					<td class=input>
						<input class=codeno name=AgentCom ondblclick="return showCodeList('AgentCom',[this,AgentComName],[0,1],null, fm.all('ManageCom').value, 'ManageCom');" onkeyup="return showCodeListKey('AgentCom',[this,AgentComName],[0,1],null, fm.all('ManageCom').value, 'ManageCom');"><input class=codename name=AgentComName readonly=true>
					</td>
					<td class=title>ҵ��Ա</td>
					<td class=input>
						<input NAME=AgentCode VALUE="" MAXLENGTH=8 CLASS=code8 elementtype=nacessary ondblclick="return queryAgent();"onkeyup="return queryAgent2();" verify="ҵ��Ա|notnull">
					</td>
					<td class=title>ҵ��Ա���</td>
					<td class=input>
						<input class="readonly"  readonly name=AgentGroup verify="���������|notnull&len<=12">
					</td>
				</tr>
-->
</table>
<hr class="line">
<table class="common">
  		<tr class="common">
			<td class="title">ҵ��Ա����
    	</td>
			<td class="input">
			  <input NAME="AgentCode" id="AgentCode" VALUE="" MAXLENGTH="10" CLASS="code wid" elementtype=nacessary onkeyup="return queryAgent();" ondblclick="return queryAgent();">
      </td>
			<td class="title">ҵ��Ա����
    	</td>
			<td CLASS="input">
			  <input NAME="AgentName" id="AgentName" readonly VALUE="" MAXLENGTH="10" CLASS="common wid" elementtype=nacessary  verifyorder="1" >
      </td>
			<td CLASS="title">��������
    	</td>
			<td CLASS="input">
				<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" NAME="AgentManageCom" id="AgentManageCom" readonly verifyorder="1" VALUE MAXLENGTH="10" CLASS="codeno" ondblclick="return showCodeList('comcode',[this,AppntManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onclick="return showCodeList('comcode',[this,AppntManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onkeyup="return showCodeListKey('comcode',[this,AppntManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" verify="�������|code:station&amp;notnull"><input name="AppntManageComName" id="AppntManageComName" elementtype=nacessary CLASS="codename" readonly>
    	</td>
		</tr>
		<tr class="common">
			<td CLASS="title">�����ֲ�
    	</td>
			<td CLASS="input">
			  <input NAME="BranchAttr" id="BranchAttr"  verifyorder="1" VALUE CLASS="common wid" readonly elementtype=nacessary TABINDEX="-1" MAXLENGTH="12" verify="ҵ��ԱӪҵ����Ӫҵ��|notnull">
    	  <input NAME="AgentGroup" type="hidden" value="">
    	</td>
      <td class="title">
        �Ǽ�ҵ��Ա
      </td>
      <td class="input">
        <input class="codeno" readonly name="starAgent" id="starAgent"><input class="codename" name="starAgentName" id="starAgentName" readonly>
      </td>
      <td class="title">��ҵ��Ա���빴ѡ
      </td>
      <td class="title">
        <input type="checkbox" name="multiagentflag" value="true" onclick="haveMultiAgent();">
      </td>

      </table>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                   ��ҵ��ԱMultiLine
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
  <div id="DivMultiAgent" style="display:'none'">
    <table>
        <tr>
            <td class=common>
                <img src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divMultiAgent1);">
            </td>
            <td class= titleImg>
                ����ҵ��Ա��Ϣ
            </td>
        </tr>
    </table>

    <div id="divMultiAgent1" style="display:''">
        <table class="common">
            <tr class="common">
                <td style="text-align:left" colSpan="1">
                    <span id="spanMultiAgentGrid">
                    </span>
                </td>
            </tr>
        </table>
    </div>
  </div>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

<!--
      <table class="common">
					<td class=title>�罻���ѷ�ʽ</td>
					<td class=input>
						<input class=codeno name=OutPayFlag value="1" MAXLENGTH=1 ondblclick="return showCodeList('OutPayFlag', [this,OutPayFlagName],[0,1]);" onkeyup="return showCodeListKey('OutPayFlag', [this,OutPayFlagName],[0,1]);" verify="�罻���Ѵ���ʽ|code:OutPayFlag&notnull"><input class=codename name=OutPayFlagName readonly=true value="�˷�">
					</td>
				<tr class=common>
					<td class=title>�α���ʽ</td>
					<td class=input>
						<input class=codeno name=EnterKind ondblclick="return showCodeList('enterkind',[this,EnterKindName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('enterkind',[this,EnterKindName],[0,1],null,null,null,1);"><input class=codename name=EnterKindName readonly=true>
					</td>
					<td class=title>����ȼ�</td>
					<td class=input>
						<input class=codeno name=AmntGrade ondblclick="return showCodeList('amntgrade',[this,AmntGradeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('amntgrade',[this,AmntGradeName],[0,1],null,null,null,1);"><input class=codename name=AmntGradeName readonly=true>
					</td>
					<td class=title>��λ��Ͷ������</td>
					<td class=input>
						<input name=Peoples3 class=common>
					</td>
				</tr>
				<tr class=common>
					<td class=title>��ְ����</td>
					<td class=input>
						<input name=OnWorkPeoples class=common>
					</td>
					<td class=title>��������</td>
					<td class=input>
						<input name=OffWorkPeoples class=common>
					</td>
					<td class=title>������Ա����</td>
					<td class=input>
						<input name=OtherPeoples class=common>
					</td>
				</tr>
				<tr class=common>
					<td class=title>����������������������</td>
					<td class=input>
						<input name=RelaPeoples class=common>
					</td>
				</tr>
				<tr class=common>
					<td class=title>��ż����</td>
					<td class=input>
						<input name=RelaMatePeoples class=common>
					</td>
					<td class=title>��Ů����</td>
					<td class=input>
						<input name=RelaYoungPeoples class=common>
					</td>
					<td class=title>������Ա����</td>
					<td class=input>
						<input name=RelaOtherPeoples class=common>
					</td>
				</tr>
					<TD class=title>
					���ϴ����˴���
					</TD>
					<TD class=input>
					<input class=common type=hidden name=AgentCode1>
					<input class=common type=hidden name=MissionID>
					<input class=common type=hidden name=SubMissionID>
					<input class="common" type=hidden name=ProposalGrpContNo>
					</TD>
			</table>
-->
		</Div>


		<table>
			<tr>
				<td class=common>
					<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol2);">
				</td>
				<td class=titleImg>
					Ͷ����λ���ϣ��ͻ��� <input class=common name=GrpNo><input id="butGrpNoQuery" class=cssButton VALUE="��  ѯ" TYPE=button onclick="showAppnt();"> ��(�״�Ͷ����λ������д�ͻ���)
				</td>
			</tr>
		</table>
		<Div id="divGroupPol2" style="display: ''" class="maxbox">
			<table class=common>
			    <TR class="common">
        			<TD class="title">VIP�ͻ�</TD>
        			<TD class=input>
        				<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type="text" name="AppntVIPValue" id="AppntVIPValue" class="codeno" verify="VIP�ͻ�|CODE:vipvalue" ondblclick="return showCodeList('vipvalue',[this,AppntVIPFlagname],[0,1]);" onclick="return showCodeList('vipvalue',[this,AppntVIPFlagname],[0,1]);" onkeyup="return showCodeListKey('vipvalue',[this,AppntVIPFlagname],[0,1]);"><input type="text" name="AppntVIPFlagname" id="AppntVIPFlagname" class="codename" readonly>
        			</TD>
                    <td class=title>��λ����</td>
					<td class=input>
						<input class="wid" class=common name=GrpName id=GrpName elementtype=nacessary onchange=checkuseronly(this.value) verify="��λ����|notnull&len<=60">
					</td>
					<td class=title>�ʲ��ܶ�(Ԫ)</td>
					<td class=input>
						<input class="wid" class=common name=Asset id=Asset verify="�ʲ��ܶ�|num&len<=17">
					</td>
        		</TR>
				<tr>
					
					<td class=title>��λ����</td>
					<td class=input>
						<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=GrpNature id=GrpNature verify="��λ����|notnull&code:grpNature&len<=10" ondblclick="showCodeList('GrpNature',[this,GrpNatureName],[0,1]);" onclick="showCodeList('GrpNature',[this,GrpNatureName],[0,1]);" onkeyup="showCodeListKey('GrpNature',[this,GrpNatureName],[0,1]);"><input class=codename name=GrpNatureName id=GrpNatureName readonly=true elementtype=nacessary>
					</td>
                    <td class=title>��ҵ���</td>
					<td class=input>
						<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=BusinessType id=BusinessType verify="��ҵ���|notnull&code:BusinessType&len<=20" ondblclick="return showCodeList('BusinessType',[this,BusinessTypeName],[0,1],null,null,null,null,300);" onclick="return showCodeList('BusinessType',[this,BusinessTypeName],[0,1],null,null,null,null,300);" onkeyup="return showCodeListKey('BusinessType',[this,BusinessTypeName],[0,1],null,null,null,null,300);"><input class=codename name=BusinessTypeName id=BusinessTypeName readonly=true elementtype=nacessary>
					</td>
					<td class=title>��λ������</td>
					<td class=input>
						<input class="wid" class=common name=Peoples id=Peoples  elementtype=nacessary verify="��λ������|notnull&int">
					</td>
				</tr>
				<tr class=common>
					
					<td class=title>�������</td>
					<td class=input>
						<input class="wid" class=common name=Fax id=Fax>
					</td>
                    <td class=title>��ᱣ�Ϻ�</td>
					<td class=input>
						<input class="wid" class=common name=BankAccNo1 id=BankAccNo1 >
					</td>
				</tr>
					
<!--
				<tr class=common>
					<td class=title>��ְ����</td>
					<td class=input>
						<input class=common name=AppntOnWorkPeoples verify="��ְ����|int">
					</td>
					<td class=title>��������</td>
					<td class=input>
						<input class=common name=AppntOffWorkPeoples verify="��������|int">
					</td>
					<td class=title>������Ա����</td>
					<td class=input>
						<input class=common name=AppntOtherPeoples verify="������Ա����|int">
					</td>
				</tr>
-->
<!--
        <tr class=common>
					<td class=title>��λ�ܻ�</td>
					<td class=input>
						<input class=common  name=Phone elementtype=nacessary verify="��λ�ܻ�|notnull&NUM&len<=30">
					</td-->
					<!--td class=title>����ʱ��</td>
					<td class=input>
						<input class="coolDatePicker" dateFormat="short" name=FoundDate>
					</td>
				</tr>
-->
				<tr>
					<td class=title>��ϵ��ַ����</td>
					<td class=input>
						<input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" class="code" name="GrpAddressNo" id="GrpAddressNo"  ondblclick="getaddresscodedata();return showCodeListEx('GetGrpAddressNo',[this],[0],'', '', '', true);" onclick="getaddresscodedata();return showCodeListEx('GetGrpAddressNo',[this],[0],'', '', '', true);" onkeyup="getaddresscodedata();return showCodeListKeyEx('GetGrpAddressNo',[this],[0],'', '', '', true);">
					</td>
                    <td class=title>��ϵ��ַ</td>
					<td class=input>
						<input class="common3 wid" name=GrpAddress id=GrpAddress elementtype=nacessary verify="��λ��ַ|notnull&len<=60">
					</td>
					<td class=title>��������</td>
					<td class=input>
						<input class="wid" class=common name=GrpZipCode id=GrpZipCode  elementtype=nacessary verify="��������|notnull&zipcode">
					</td>
				</tr>
				
				<tr class=common>
					<td class=title>������ϵ��һ</td><td class=input></td>
                    <td class=title>��  ��</td>
					<td class=input>
						<input class="wid" class=common name=LinkMan1 id=LinkMan1 elementtype=nacessary verify="������ϵ��һ����|notnull&len<=10">
					</td>
					<td class=title>��ϵ�绰</td>
					<td class=input>
						<input class="wid" class=common name=Phone1 id=Phone1 elementtype=nacessary verify="������ϵ��һ��ϵ�绰|notnull&len<=30">
					</td>
				</tr>
				<tr class=common>
					
					<td class=title>E-MAIL</td>
					<td class=input>
						<input class="wid" class=common name=E_Mail1 verify="������ϵ��һE-MAIL|len<=60&Email">
					</td>
                    <td class=title>���ڲ���</td>
					<td class=input>
						<input class="wid" class=common3 name=Department1 verify="������ϵ��һ����|len<=30">
					</td>
                    <td class=title>������ϵ�˶�</td><td class=input></td>
				</tr>
				
				
				<tr class=common>
					<td class=title>��  ��</td>
					<td class=input>
						<input class="wid" class=common name=LinkMan2 id=LinkMan2 verify="������ϵ�˶�����|len<=10">
					</td>
					<td class=title>��ϵ�绰</td>
					<td class=input>
						<input class="wid" class=common name=Phone2 id=Phone2 verify="������ϵ�˶���ϵ�绰|NUM&len<=30">
					</td>
					<td class=title>E-MAIL</td>
					<td class=input>
						<input class="wid" class=common name=E_Mail2 id=E_Mail2 verify="������ϵ�˶�E-MAIL|len<=60&Email">
					</td>
				</tr>
				<tr class=common>
					<td class=title>���ڲ���</td>
					<td class=input>
						<input class="wid" class=common3 name=Department2 id=Department2 verify="������ϵ�˶�����|len<=30">
					</td>
                    <td class=title>���ʽ</td>
					<td class=input>
						<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=GetFlag id=GetFlag verify="���ʽ|notnull&code:PayMode" ondblclick="return showCodeList('PayMode',[this,GetFlagName],[0,1]);" onclick="return showCodeList('PayMode',[this,GetFlagName],[0,1]);" onkeyup="return showCodeListKey('PayMode',[this,GetFlagName],[0,1]);"><input class=codename name=GetFlagName id=GetFlagName readonly=true elementtype=nacessary>
					</td>
					<td class=title>��������</td>
					<td class=input>
						<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=BankCode id=BankCode verify="��������|code:bank&len<=24" ondblclick="showCodeList('bank',[this,BankCodeName],[0,1]);" onclick="showCodeList('bank',[this,BankCodeName],[0,1]);" onkeyup="showCodeListKey('bank',[this,BankCodeName],[0,1]);"><input class=codename name=BankCodeName id=BankCodeName readonly=true>
					</td>
				</tr>
				<tr class=common>
					
					<td class=title>��  ��</td>
					<td class=input>
						<input class="wid" class=common name=BankAccNo id=BankAccNo verify="�ʺ�|len<=40">
					</td>
                    <td class=title></td><td class=input></td><td class=title></td><td class=input></td>
				</tr>

			</table>
			<table class=common>
				<tr class=common>
					<td class=title>��ע</td>
				</tr>
				<tr class=common>
					<td colspan="6" style="padding-left:16px">
						<textarea name="Remark" cols="224" rows="4" class="common"></textarea>
					</td>
				</tr>
				<input type=hidden name=EmployeeRate verify="��Ա�Ը�����|num&len<=5">
				<input type=hidden name=FamilyRate verify="�����Ը�����|num&len<=80">
			</table>
		</Div>
			<!-- ��֪��Ϣ���֣��б� -->
<!--
		<DIV id=DivLCImpart STYLE="display:''">
			<table>
				<tr>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLCImpart1);">
					</td>
					<td class=titleImg>�ͻ���������</td>
				</tr>
			</table>
			<div id="divLCImpart1" style="display: ''">
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1>
							<span id="spanServInfoGrid"></span>
						</td>
					</tr>
				</table>
			</div>
			<table>
				<tr>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLCImpart1);">
					</td>
					<td class=titleImg>�����֪��Ϣ</td>
				</tr>
			</table>
			<div id="divLCImpart1" style="display: ''">
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1>
							<span id="spanImpartGrid"></span>
						</td>
					</tr>
				</table>
			</div>
			<table>
				<tr>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLCImpart2);">
					</td>
					<td class=titleImg>���������֪</td>
				</tr>
			</table>
			<div id="divLCImpart2" style="display: ''">
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1>
							<span id="spanHistoryImpartGrid"></span>
						</td>
					</tr>
				</table>
			</div>
			<table>
				<tr>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLCImpart3);">
					</td>
					<td class=titleImg>���ؼ��������֪</td>
				</tr>
			</table>
			<div id="divLCImpart3" style="display: ''">
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1>
							<span id="spanDiseaseGrid"></span>
						</td>
					</tr>
				</table>
			</div>
		</DIV>
-->
		<Div id="divButton" style="display: ''" align=left>
			<!--INPUT class=cssButton VALUE="�� ѯ"  TYPE=button onclick="queryClick()">
			<input class=cssButton VALUE="�� ��"  TYPE=button onclick="updateClick()">
			<input class=cssButton VALUE="ɾ ��"  TYPE=button onclick="deleteClick()">
			<input class=cssButton VALUE="�� ��"  TYPE=button onclick="submitForm();"-->
			<%@include file="OperateButton.jsp"%>
			<%@include file="../common/jsp/InputButton.jsp"%>
		</DIV>
		<Div id="divUpdateButton" style="display: 'none'" align=right>	
		    <table>
		        <tr>		
			        <td><input class=cssButton VALUE="�� ��"  TYPE=button onclick="updateClick()"></td>
			    </tr>
			</table>
		</DIV>
		<Div id="divhiddeninfo" style="display: 'none'">
			<table class=common>
				<tr class=common>
					<td class=title>��������</td>
					<td class=input>
						<!--<input class="readonly" readonly name=SaleChnl verify="��������|code:SaleChnl&notnull">-->
						<input style="background:url(../common/images/select--bg_03.png) no-repeat right center"  class=codeno  name=SaleChnl  id=SaleChnl verify="��������|code:SaleChnl&notnull"  ondblclick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" onclick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" onkeyup="return showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1]);"><input class=codename name=SaleChnlName id=SaleChnlName readonly=true elementtype=nacessary>
					</td>   
					<td class=title>ע���ʱ���</td>
					<td class=input>
						<input class="wid" class=common name=RgtMoney id=RgtMoney verify="ע���ʱ���|num&len<=17">
					</td>
					<td class=title>���ʲ�������</td>
					<td class=input>
						<input class="wid" class=common name=NetProfitRate id=NetProfitRate verify="���ʲ�������|num&len<=17">
					</td>
				</tr>
				<tr class=common>
					<td class=title>��Ӫҵ��</td>
					<td class=input>
						<input class="wid" class=common name=MainBussiness id=MainBussiness verify="��Ӫҵ��|len<=60">
					</td>
					<td class=title>��λ���˴���</td>
					<td class=input>
						<input class="wid" class=common name=Corporation id=Corporation verify="��λ���˴���|len<=20">
					</td>
					<td class=title>�����ֲ�����</td>
					<td class=input>
						<input class="wid" class=common name=ComAera id=ComAera verify="�����ֲ�����|len<=30">
					</td>
				</tr>
				<tr class=common>
					<td class=title>ְ��</td>
					<td class=input>
						<input class="wid" class=common name=HeadShip1 id=HeadShip1 verify="������ϵ��һְ��|len<=30">
					</td>
					<td class=title>����</td>
					<td class=input>
						<input class="wid" class=common name=Fax1 id=Fax1 verify="������ϵ��һ����|len<=30">
					</td>
				</tr>
				<tr class=common>
					<td class=title>ְ��</td>
					<td class=input>
						<input class="wid" class=common name=HeadShip2 id=HeadShip2 verify="������ϵ�˶�ְ��|len<=30">
					</td>
					<td class=title>����</td>
					<td class=input>
						<input class="wid" class=common name=Fax2 id=Fax2 verify="������ϵ�˶�����|len<=30">
					</td>
				</tr>
				<tr class=common>
					<td class=title>����</td>
					<td class=input>
						<input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" class=wid name=Currency id=Currency verify="����|len<=2" ondblclick="showCodeList('currency',[this]);"  onclick="showCodeList('currency',[this]);" onkeyup="showCodeListKey('currency',[this]);">
					</td>
				</tr>
			</table>
		</DIV>
		<table class=common>
    		<tr>
    		    <td class="title">��Ʒ���¼�룬�빴ѡ<input type="checkbox" name="PlanFlag" value="true" onclick="inputPlan();"></td>
            </tr>
        </table>
		<DIV id="divGrpNormalRisk" style="display: ''">
    		<table>
    			<tr>
    				<td class=common>
    					<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol3);">
    				</td>
    				<td class=titleImg>���屣��������Ϣ</td>
    			</tr>
    		</table>
    		<Div id="divGroupPol3" style="display: ''">
    			<Div id="divGroupPol5" style="display: 'none'" class="maxbox1">
    				<table class=common>
    					<tr class=common>
    						<td class=title>�����ͬ����</td>
    						<td class=input>
    							<input class="readonly wid" readonly name=GrpContNo id=GrpContNo >
    						</td><td class=title></td>
                            <td class=input></td>
    					</tr>
    				</table>
    			</Div>
    			<!--¼����ݽ��ѱ��� -->
    			<Table class=common>
    				<tr>
    					<td text-align: left colSpan=1>
    						<span id="spanRiskGrid"></span>
    					</td>
    				</tr>
    			</Table>
    			<Div  id="divPage" align=center style="display:'none'">
        			<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage1.firstPage();">
        			<INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage1.previousPage();">
        			<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage1.nextPage();">
        			<INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage1.lastPage();">
    		    </Div>
    			<table>
    				<tr>
    					<td class=common>
    						<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol4);">
    					</td>
    					<td class=titleImg>������Ϣ</td>
    				</tr>
    			</table>
    			<Div id="divGroupPol4" style="display: ''" class="maxbox1">
    				<table class=common>
    					<tr class=common>
    						<td class=title>���ֱ���</td>
    						<td class=input>
    							<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=RiskCode id=RiskCode ondblclick="return showCodeList('RiskGrp',[this,RiskCodeName],[0,1]);" onclick="return showCodeList('RiskGrp',[this,RiskCodeName],[0,1]);" onkeyup="return showCodeListKey('RiskGrp',[this,RiskCodeName],[0,1]);"><input class=codename name=RiskCodeName id=RiskCodeName readonly=true elementtype=nacessary>
    						</td>
    						<td class=title>�����ڼ�</td>
    						<td class=input>
    							<!--<input class=codeno name=PayIntv ondblclick="return showCodeList('RiskPayIntv',[this,PayIntvName],[0,1],null,fm.RiskCode.value,'a.RiskCode');" onkeyup="return showCodeListKey('RiskPayIntv',[this,PayIntvName],[0,1],null,fm.RiskCode.value,'a.RiskCode');"><input class=codename name=PayIntvName readonly=true elementtype=nacessary>-->
    							<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=PayIntv id=PayIntv ondblclick="return showCodeList('RiskPayIntv',[this,PayIntvName],[0,1],null,fm.RiskCode.value,'RiskCode');" onclick="return showCodeList('RiskPayIntv',[this,PayIntvName],[0,1],null,fm.RiskCode.value,'RiskCode');" onkeyup="return showCodeListKey('RiskPayIntv',[this,PayIntvName],[0,1],null,fm.RiskCode.value,'RiskCode');"><input class=codename name=PayIntvName id=PayIntvName readonly=true elementtype=nacessary>	
    						</td>
                            <TD class=title8 colspan=1>
                              				  �ֺ��־
                              				</TD>
                              				<TD class=input8>
                              				    <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" class=wid name=BonusFlag id=BonusFlag value="" CodeData="0|^0|�����ֺ�^1|����ֺ�" ondblclick="return showCodeListEx('BonusFlag',[this]);" onkeyup="return showCodeListKeyEx('BonusFlag',[this]);">
                              			    </TD>
    <!--
    						<td class=title>Ԥ������</td>
    						<td class=input>
    							<input class=common name=ExpPeoples  elementtype=nacessary>
    						</td>
    -->
    					</tr>
    					
    					<tr class=common>
    					   <td class=title>����</td>
    						<td class=input>
    							<input class="wid" class=common name=ExpPrem id=ExpPrem elementtype=nacessary value="0">
    						</td>
    						<td class=title>����/����</td>
    						<td class=input>
    							<!--<input class=codeno name=PayIntv ondblclick="return showCodeList('RiskPayIntv',[this,PayIntvName],[0,1],null,fm.RiskCode.value,'a.RiskCode');" onkeyup="return showCodeListKey('RiskPayIntv',[this,PayIntvName],[0,1],null,fm.RiskCode.value,'a.RiskCode');"><input class=codename name=PayIntvName readonly=true elementtype=nacessary>-->
    							<input class="wid" class=common name=ExpAmnt id=ExpAmnt>
    						</td>
    						<td>
    						    <Div id="divRiskDeal" style="display: ''">
                					<table>
                			        	<tr>
                							<td>
                								<input type =button class=cssButton value="�������" onclick="addRecord();">
                							</td>
                							<td>
                								<input type =button class=cssButton value="ɾ������" onclick="deleteRecord();">
                							</td>
                						</tr>						
                					</table>
                				</Div>
    						</td>
    					</tr>
    				</table>
    				
    			</Div>
    		</DIV>
	    </DIV>
	    <DIV id="divGrpPlanRisk" style="display: 'none'">
	        <table>
    			<tr>
    				<td class=common>
    					<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPlan);">
    				</td>
    				<td class=titleImg>���屣����Ʒ�����Ϣ</td>
    			</tr>
    		</table>
    		<Div id="divGroupPlan" style="display: ''">			
    	 		<Table class=common>
    				<tr>
    					<td text-align: left colSpan=1>
    						<span id="spanPlanGrid"></span>
    					</td>
    				</tr>
    			</Table>
     		    <table>
    				<tr>
    					<td class=common>
    						<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divPlnaRiskDetail);">
    					</td>
    					<td class=titleImg>������ϸ��Ϣ</td>
    				</tr>
    			</table>
    			<div id="divPlnaRiskDetail">
        		    <Table class=common>
        				<tr>
        					<td text-align: left colSpan=1>
        						<span id="spanPlanRiskGrid"></span>
        					</td>
        				</tr>
        			</Table>
        			<center>
            			<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage2.firstPage();">
            			<INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage2.previousPage();">
            			<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage2.nextPage();">
            			<INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage2.lastPage();">
        			</center>
        		</div>
    			<table>
    				<tr>
    					<td class=common>
    						<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPlan1);">
    					</td>
    					<td class=titleImg>��Ʒ�����Ϣ</td>
    				</tr>
    			</table>
    			<Div id="divGroupPlan1" style="display: ''" class="maxbox1">
    				<table class=common>
    					<tr class=common>
    						<td class=title>��Ʒ��ϱ���</td>
                            <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=PlanRiskCode id=PlanRiskCode ondblclick="showCodeList('PlanRiskCode',[this,PlanRiskCodeName],[0,1],null,null,null,1,'400');" onclick="showCodeList('PlanRiskCode',[this,PlanRiskCodeName],[0,1],null,null,null,1,'400');" onkeyup="showCodeListKey('PlanRiskCode',[this,PlanRiskCodeName],[0,1],null,null,null,1,'400');"><input class="codename" name="PlanRiskCodeName" id="PlanRiskCodeName" elementtype=nacessary readonly></TD>                           
                            
                    <td class=title>�ⶥ��</td>
					<td class=input>
						<input class="wid" class=common name=PeakLine id=PeakLine>
					</td>
				<!--</tr>
				<tr class=common>-->
					<td class=title>ҽ�Ʒ����޶�</td>
					<td class=input>
						<input class="wid" class=common name=MaxMedFee id=MaxMedFee>
					</td>
    					    <!--
    					    <td class=title>����</td>
    						<td class=input>    							
    							<input class=common name=Mult value="1">	
    						</td>
    						-->
    					   <!-- <td>
    						    <Div id="divPlanDeal" style="display: ''">
                					<table>
                			        	<tr>
                							<td>
                								<input type =button class=cssButton value="��Ӳ�Ʒ���" onclick="addPlanRecord();">
                							</td>
                							<td>
                								<input type =button class=cssButton value="ɾ����Ʒ���" onclick="deletePlanRecord();">
                							</td>
                						</tr>						
                					</table>
                				</Div>
    						</td>-->
    					</tr>					
    				</table>
    				
    			</Div>
    		</DIV>
    	</DIV><br><br>
        <a href="javascript:void(0);" class="button" onClick="addPlanRecord();">��Ӳ�Ʒ���</a>
        <a href="javascript:void(0);" class="button" onClick="deletePlanRecord();">ɾ����Ʒ���</a>
        
		<Div id="divHidden" style="display: 'none'">
			<table class=common>
				<tr class=common>
					
				</tr>
			</table>
		</Div>
		
		    <Div id="divnormalbtn" style="display: ''">
    			<input class=cssButton VALUE="������Ϣ"  TYPE=button onclick="grpFeeInput()">
    	  	    <input class=cssButton VALUE="���ռƻ�����"  TYPE=button onclick="grpRiskPlanInfo()">
    			<!--input type =button class=cssButton value="��ӱ�����" onclick="grpInsuInfo();"-->
    			<input class=cssButton VALUE="�������嵥"  TYPE=button onclick="grpInsuList()">
    			<input class=cssButton VALUE="�����嵥"  TYPE=button onclick="grpCarList()">
			</Div>
			<Div id="divapprovenormalbtn" style="display: 'none'">
    			<input class=cssButton VALUE="������Ϣ"  TYPE=button onclick="grpFeeInput()">
    			<input class=cssButton VALUE="���ռƻ�����"  TYPE=button onclick="grpRiskPlanInfo()">
    			<input class=cssButton VALUE="�������嵥"  TYPE=button onclick="grpInsuList()">
    			<input class=cssButton VALUE="�����嵥"  TYPE=button onclick="grpCarList()"> 
			    <!--<input class=cssButton VALUE="�ֵ�����"  TYPE=button onclick="grpSubContInfo()">-->
			</Div>
			<Div id="divnormalquesbtn" style="display: 'none'">
			
			<input VALUE="¼�����"   class=cssButton TYPE=button onclick="GrpInputConfirm(1);">
			<INPUT VALUE="���±��鿴" class=cssButton  id="NotePadButton2" name="NotePadButton" TYPE=button onclick="showNotePad();">
			<input VALUE="�����������ѯ"  name="qryQuestionBtn2" class=cssButton TYPE=button onclick="GrpQuestQuery(0);">
			<input VALUE="���������¼��"   class=cssButton TYPE=button onclick="GrpQuestInput();">
			<INPUT VALUE="Ӱ�����ѯ"    Class=cssButton TYPE=button onclick="showImage()">
		</Div>
		<Div id="divapprovebtn" style="display: 'none'">
			
			<INPUT class=cssButton VALUE="���±��鿴"  id="NotePadButton4" TYPE=button onclick="showNotePad();">
			<input VALUE="���帴��ͨ��" class=cssButton TYPE=button onclick="gmanuchk();">
			<input VALUE="�����������ѯ"  id="qryQuestionBtn4" class=cssButton TYPE=button onclick="GrpQuestQuery(0);">
			<input VALUE="���������¼��"   class=cssButton TYPE=button onclick="GrpQuestInput();">
			<INPUT VALUE="Ӱ�����ѯ"    Class=cssButton TYPE=button onclick="showImage()">
			<input VALUE="��  ��" class=cssButton TYPE=button onclick="goback();">
		</Div>
		<Div id="divapproveupdatebtn" style="display: 'none'">
			
			<INPUT class=cssButton VALUE="���±��鿴"  id="NotePadButton" TYPE=button onclick="showNotePad();">
			<input VALUE="��  ��" class=cssButton TYPE=button onclick="updateClick();">
			<input VALUE="������޸�ȷ��" class=cssButton TYPE=button onclick="GrpInputConfirm(2);">
			<input VALUE="�����������ѯ"   class=cssButton TYPE=button onclick="GrpQuestQuery(0);">
			<input VALUE="���������¼��"   class=cssButton TYPE=button onclick="GrpQuestInput();">
			<input VALUE="��  ��" class=cssButton TYPE=button onclick="goback();">
		</Div>
		<Div id="divapproveupdatebtn1" style="display: 'none'">
			
			<INPUT class=cssButton VALUE="���±��鿴" id="NotePadButton13" TYPE=button onclick="showNotePad();">
			<input VALUE="��  ��" class=cssButton TYPE=button onclick="updateClick();">
			<input VALUE="������޸�ȷ��" class=cssButton TYPE=button onclick="GrpInputConfirm(2);">
			<input VALUE="����������ظ�"   class=cssButton TYPE=button onclick="GrpQuestQuery(1);">
			<INPUT VALUE="Ӱ�����ѯ"    Class=cssButton TYPE=button onclick="showImage()">
			<input VALUE="��  ��" class=cssButton TYPE=button onclick="goback();">
		</Div>
		    <Div id="divchangplanbtn" style="display: 'none'">
			<input VALUE="��  ��" class=cssButton TYPE=button onclick="updateClick();">
			<input VALUE="�����������ѯ"  name="qryQuestionBtn23" class=cssButton TYPE=button onclick="GrpQuestQuery(0);">
			<!--<input VALUE="���������¼��"   class=cssButton TYPE=button onclick="GrpQuestInput();">-->
			<input VALUE="��  ��" class=cssButton TYPE=button onclick="goback();">
			</Div>

			<Div id="divuwupdatebtn" style="display: 'none'">
			
			<input VALUE="��  ��" class=cssButton TYPE=button onclick="goback();">
		</Div>
		<Div id="divquerybtn" style="display: 'none'" align=right>
			
			<input VALUE="�����������ѯ"  name="qryQuestionBtn16" class=cssButton TYPE=button onclick="GrpQuestQuery(0);">
			<input VALUE="��  ��" class=cssButton TYPE=button onclick="goback();">
		</Div>
		<div id="autoMoveButton" style="display: none">
			<input type="button" name="autoMoveInput" value="�涯����ȷ��" onclick="submitAutoMove('21');" class=cssButton>
			<input type="button" name="Next" value="��һ��" onclick="location.href='ContInsuredInput.jsp?LoadFlag='+tLoadFlag+'&prtNo='+prtNo+'&checktype=2&scantype='+scantype" class=cssButton>
			<input VALUE="��  ��" class=cssButton TYPE=button onclick="top.close();">
			<input type=hidden id="" name="autoMoveFlag">
			<input type=hidden id="" name="autoMoveValue">
			<input type=hidden id="" name="pagename" value="21">
			</div>
			<input type=hidden id="fmAction" name="fmAction">
			<input type=hidden id="" name="WorkFlowFlag">
					<input class=common type=hidden name=AgentCode1>
					<input class=common type=hidden name=MissionID>
					<input class=common type=hidden name=SubMissionID>
					<input class="common" type=hidden name=ProposalGrpContNo>

					<input class="common" type=hidden name=ActivityID>
					<input class="common" type=hidden name=NoType>

<!--
#########################################################################################
                                 ��ǰҳ����Ŀؼ������ڲ���Ҫ�ˣ��������ڴˣ�����ɾ��
-->
						<input type="hidden" class=codeno name=OutPayFlag value="1" MAXLENGTH=1 ondblclick="return showCodeList('OutPayFlag', [this,OutPayFlagName],[0,1]);" onkeyup="return showCodeListKey('OutPayFlag', [this,OutPayFlagName],[0,1]);" verify="�罻���Ѵ���ʽ|code:OutPayFlag&notnull">
						<input type="hidden" class=codeno name=EnterKind ondblclick="return showCodeList('enterkind',[this,EnterKindName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('enterkind',[this,EnterKindName],[0,1],null,null,null,1);">
						<input type="hidden" class=codeno name=AmntGrade ondblclick="return showCodeList('amntgrade',[this,AmntGradeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('amntgrade',[this,AmntGradeName],[0,1],null,null,null,1);">
						<input type="hidden" name=Peoples3 class=common>
						<input type="hidden" name=OnWorkPeoples class=common>
						<input type="hidden" name=OffWorkPeoples class=common>
						<input type="hidden" name=OtherPeoples class=common>
						<input type="hidden" name=RelaPeoples class=common>
						<input type="hidden" name=RelaMatePeoples class=common>
						<input type="hidden" name=RelaYoungPeoples class=common>
						<input type="hidden" name=RelaOtherPeoples class=common>

<!--
#########################################################################################
-->
			<!--INPUT class=cssButton VALUE="���뱻�����嵥"  TYPE=button onclick=""-->
			<!--<input class=cssButton VALUE="���뱣��������Ϣ"  TYPE=button onclick="grpRiskInfo()">-->
		</Div>
		<input type=hidden name=LoadFlag>
	</form>
	<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
