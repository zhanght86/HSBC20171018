<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.SSRS"%>
<%@page import="com.sinosoft.utility.ExeSQL"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
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
String tCardFlag ="";

try
{
	tLoadFlag =request.getParameter( "LoadFlag" );
	tGrpContNo =request.getParameter( "GrpContNo" );
	tCardFlag =request.getParameter("CardFlag");
	//loggerDebug("ContPolInput","�ŵ������ţ�"+request.getParameter("prtNo"));
	
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
var ManageCom ="<%=tGI.ManageCom%>";
var SubMissionID ="<%=request.getParameter("SubMissionID")%>";
var ActivityID = "<%=request.getParameter("ActivityID")%>";
var NoType = "<%=request.getParameter("NoType")%>";
var GrpContNo ="<%=request.getParameter("GrpContNo")%>";
var vContNo ="<%=request.getParameter("vContNo")%>";
var ScanFlag ="<%=request.getParameter("ScanFlag")%>";
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
var tuser= "<%=tGI.Operator%>"; 
var cardflag ="<%=tCardFlag%>";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="ContPolInit.jsp"%>
<SCRIPT src="ContPolInput.js"></SCRIPT>
<%
//���ݱ������ж��Ƿ��������
	ExeSQL tExeSQL=new ExeSQL();
	String tsql="select subtype From es_doc_main where doccode='"+request.getParameter("prtNo")+"'";
	SSRS tSSRS=new SSRS();
	String tvalue="1000";
	tSSRS=tExeSQL.execSQL(tsql);
	if(tSSRS.MaxRow>0)
	{
	 tvalue=tSSRS.GetText(1,1);
	 }
	String tflag="0";
	if(tvalue.equals("1000"))
	{
	 loggerDebug("ContPolInput","tflag==0");
	}else{
	tflag="1";
	}
 if(tflag.equals("0"))
 {
%>
<SCRIPT src="ProposalAutoMove.js"></SCRIPT>
<%
 }else{
%>
<SCRIPT src="ProposalAutoMove2.js"></SCRIPT>
<%
}
%>
<%
if(request.getParameter("scantype")!=null&&request.getParameter("scantype").equals("scan"))
{
loggerDebug("ContPolInput","ɨ��¼��");
%>
<SCRIPT src="../common/EasyScanQuery/ShowPicControl.js"></SCRIPT>
<SCRIPT>window.document.onkeydown =document_onkeydown;</SCRIPT>
<%
}
%>
</head>
<body  onload="initForm();initElementtype();">
	<form action="./ContPolSave.jsp" method=post name=fm target="fraSubmit">
		<!-- ��ͬ��Ϣ���� GroupPolSave.jsp-->
		<DIV id=DivLCContButton STYLE="display:''">
			<table id="table1">
				<tr>
					<td>
						<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol1);">
					</td>
					<td class="titleImg">����������Ϣ</td>
				</tr>
			</table>
		</DIV>
		<Div id="divGroupPol1" style="display: ''">
			<table border="0" class=common>
				<tr class=common>
					<!--TD class=title>
						����Ͷ��������
					</td>
					<td class=input>
						<input class="common" name=ProposalGrpContNo readonly TABINDEX="-1"  MAXLENGTH="40">
					</TD-->
					<td class="title">�������κ�</td>
					<td class="input">
						<input class="common" name=PrtNo elementtype=nacessary TABINDEX="-1" MAXLENGTH="14" verify="Ͷ��������|notnull&len<=14">
					</td>
				  <td class="title">�ʱ�����</td>
					<td class="input">
            <input class="common" name="ReportNo">
					</td>
				  
					<TD  class= title8>
            ��������
          </TD>
          <TD  class= input8>
            <Input class="code8" name=AgentType elementtype=nacessary verify="��������|notnull" ondblclick="return showCodeList('AgentType',[this],null,null,null,null,1);" onkeyup="return showCodeListKey('AgentType',[this],null,null,null,null,1);">
          </TD>
					<!--
					<td class=title>�������</td>
					<td class=input>
						<input class="code" name=ManageCom ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
						<input class=codeno name=ManageCom verify="�������|code:comcode&notnull" ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,null,null,1);"><input class=codename name=ManageComName readonly=true elementtype=nacessary>
					</td>
					-->
				</tr>
				<tr class=common>
								<TD  class= title8>
           ������������
          </TD>
          <TD  class= input8>
            <Input class="code8" name=SecondAgentType   ondblclick="return showCodeList('SecondAgentType',[this],null,null,null,null,1);" onkeyup="return showCodeListKey('SecondAgentType',[this],null,null,null,null,1);">
          </TD> 
					<TD  class= title8>
            �н����
          </TD>
          <TD  class= input8>
            <!--Input class="code8" name=AgentCom ondblclick="return showCodeList('AgentCom',[this],null,null, fm.all('ManageCom').value, 'ManageCom');" onkeyup="return showCodeListKey('AgentCom',[this],null,null, fm.all('ManageCom').value, 'ManageCom');"-->
            <!-------20060217----changing start--------->
            <Input class="code8" name=AgentCom onkeyup="return queryCom();" ondblclick="return queryCom();">
            <!-----------changing end------------------->
          </TD>
				  
					<td class=title>Ͷ����������</td>
					<td class=input>
						<input class="coolDatePicker" elementtype=nacessary dateFormat="short" onblur="checkapplydate();" name=PolApplyDate verify="Ͷ����������|notnull&DATE verifyorder="1"">
					</td>
				</tr> 
				<tr class=common>
           <td class=title>������Ч����</td>
						<td class=input>
						<input class="coolDatePicker" elementtype=nacessary dateFormat="short" onblur="checkCValidate();" name=CValiDate verify="������Ч����|notnull&DATE verifyorder="1"">
					</td>
						
					<!--td class=input>
						<input class="common" elementtype=nacessary dateFormat="short" onblur="checkCValidate();" name=CValiDate verify="������Ч����|notnull&DATE veryfyorder="1"">
					</td-->
<!--					<td class="title">��������</td>
					<td class="input">
						<input class=codeno name=AgentType verify="��������|notnull" ondblclick="return showCodeList('AgentType',[this,AgentTypeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('AgentType',[this,AgentTypeName],[0,1],null,null,null,1);"><input class=codename name=AgentTypeName readonly=true elementtype=nacessary>
					</td>
-->
			    <td CLASS="title">�������
    	    </td>
			    <td CLASS="input">
			      <Input name=ManageCom class='codeno' id="ManageCom" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input name=ManageComName class=codename readonly=true>
			      <!--input NAME="ManageCom"  verifyorder="1" VALUE MAXLENGTH="10" CLASS="codeno" ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" verify="�������|code:station&amp;notnull"><input NAME="ManageComName" elementtype=nacessary CLASS="codename" readonly="readonly"-->
    	    </td>
    	    <td class="title">�����շ�����</td>
					<td class="input">
            <input class="common" name="PayDate" readonly="readonly">
					</td>
					
	<!--				
    	    <td class="title">�Ƿ�ʹ�ñ�ȫ�����㷨</td>
					<td class="input">
            <Input class="code" name=EdorCalType CodeData="0|^Y|��^N|��" ondblclick="showCodeListEx('EdorCalType',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('EdorCalType',[this,''],[0,1]);" >          
					</td>					

				</tr>	
			<tr class=common>	
				  <td class="title">�Ƿ�Ϊ�����ŵ�</td>
					<td class="input">
            <Input class="code" name=DonateContflag CodeData="0|^0|��^1|��" ondblclick="showCodeListEx('DonateContflag',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('DonateContflag',[this,''],[0,1]);" >          
					</td>	
					 <td class="title">��������</td>
					<td class="input">
            <input class="common" name="ExamAndAppNo">
					</td>
			</tr>				
	 -->
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
<tr class=common>
		<td class="title">��ȫ���˱���</td>
					<td class="input">
            <input class="common" name="EdorTransPercent" verify="��ȫ���˱���|DECIMAL">
					</td>			  
</tr>
</table>
<table class="common">
				<tr class=common>
					<td class=title>����׷�ݱ�ע</td>
				</tr>
				<tr class=common>
					<td class=title>
						<textarea name="BackDateRemark" cols="110" rows="3" class="common"></textarea>
					</td>
				</tr>
</table>
<hr>
<table class="common">
  		<tr class="common">
			<td class="title">ҵ��Ա����
    	</td>
			<td class="input">
			  <input NAME="AgentCode" VALUE="" MAXLENGTH="10" CLASS="code" elementtype=nacessary onkeyup="return queryAgent();" ondblclick="return queryAgent();">
      </td>
			<td class="title">ҵ��Ա����
    	</td>
			<td CLASS="input">
			  <input NAME="AgentName" readonly VALUE="" MAXLENGTH="10" CLASS="common" elementtype=nacessary  verifyorder="1" >
      </td>
			<td CLASS="title">��������
    	</td>
			<td CLASS="input">
				<input NAME="AgentManageCom" readonly verifyorder="1" VALUE MAXLENGTH="10" CLASS="codeno" ondblclick="return showCodeList('comcode',[this,AppntManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onkeyup="return showCodeListKey('comcode',[this,AppntManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" verify="��������|code:station&amp;notnull"><input name="AppntManageComName" elementtype=nacessary CLASS="codename" readonly="readonly">
    	</td>
		</tr>
		<tr class="common">
			<td CLASS="title">�����ֲ�
    	</td>
			<td CLASS="input">
			  <input NAME="BranchAttr"  verifyorder="1" VALUE CLASS="common" readonly elementtype=nacessary TABINDEX="-1" MAXLENGTH="12" verify="ҵ��ԱӪҵ����Ӫҵ��|notnull">
    	  <input NAME="AgentGroup" type="hidden" value="">
    	</td>
      <td class="title">&nbsp;
      </td>
      <td class="title">
        &nbsp;
      </td>
      <!-- 
      <td class="title">��ҵ��Ա���빴ѡ
      </td>
      <td class="title">
        <input type="checkbox" name="multiagentflag" value="true" onclick="haveMultiAgent();">
      </td>
      -->
      </table>
  <div id="DivAssiOper" style="display:'none'">
    <table class="common">
      <tr class="common">
			<td class="title">�ۺϿ���רԱ����
    	</td>
			<td class="input">
			  <input NAME="AgentCodeOper" readonly VALUE="" MAXLENGTH="10" CLASS="common" elementtype=nacessary>
      </td>
			<td class="title">�ۺϿ���רԱ����
    	</td>
			<td CLASS="input">
			  <input NAME="AgentNameOper" readonly VALUE="" MAXLENGTH="10" CLASS="common" elementtype=nacessary>
      </td>
      <td class="title">�ۺϿ����������
    	</td>
			<td class="input">
			  <input NAME="AgentCodeAssi" readonly VALUE="" MAXLENGTH="10" CLASS="common" elementtype=nacessary>
      </td>
      </tr>
      <tr class="common">
			<td class="title">�ۺϿ�����������
    	</td>
			<td CLASS="input">
			  <input NAME="AgentNameAssi" readonly VALUE="" MAXLENGTH="10" CLASS="common" elementtype=nacessary>
      	</td>
      </tr>
   </table>
  </div>
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

<hr>
		<table>
			<tr>
				<td class=common>
					<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol2);">
				</td>
				<td class=titleImg>
					���㵥λ���ϣ��ͻ��� <input class=common name=GrpNo><input id="butGrpNoQuery" class=cssButton VALUE="��  ѯ" TYPE=button onclick="showAppnt();"> ��(�״�Ͷ����λ������д�ͻ���)
				</td>
			</tr>
		</table>
		<Div id="divGroupPol2" style="display: ''">
			<table class=common>
				<tr>
					<td class=title>���㵥λ����</td>
					<td class=input>
						<input class=common name=GrpName elementtype=nacessary onchange=checkuseronly(this.value) verify="��λ����|notnull&len<=60">
					</td>
                 </tr>

				<tr>
					<td class=title>��λ��ַ����</td>
					<td class=input>
						<input class="code" name="GrpAddressNo"  ondblclick="getaddresscodedata();return showCodeListEx('GetGrpAddressNo',[this],[0],'', '', '', true);" onkeyup="getaddresscodedata();return showCodeListKeyEx('GetGrpAddressNo',[this],[0],'', '', '', true);">
					</td>
				</tr>
				<tr class=common>
					<td class=title>��λ��ַ</td>
					<td class=input colspan="3">
						<input class="common3" name=GrpAddress  verify="��λ��ַ|len<=60">
					</td>
					<td class=title>��������</td>
					<td class=input>
						<input class=common name=GrpZipCode maxlength=6  verify="��������|zipcode">
					</td>
				</tr>
				<tr class=common>
					<td class=title>������ϵ��һ</td>
				</tr>
				<tr class=common>
					<td class=title>��  ��</td>
					<td class=input>
						<input class=common name=LinkMan1  verify="������ϵ��һ����|len<=10">
					</td>
					<td class=title>��ϵ�绰</td>
					<td class=input>
						<input class=common name=Phone1  verify="������ϵ��һ��ϵ�绰|len<=30">
					</td>
					<td class=title>E-MAIL</td>
					<td class=input>
						<input class=common name=E_Mail1 verify="������ϵ��һE-MAIL|len<=60&Email">
					</td>
				</tr>
				<tr class=common>
					<td class=title>��  ��</td>
					<td class=input colspan=3>
						<input class=common3 name=Department1 verify="������ϵ��һ����|len<=30">
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
</table>
</div>
  <Div id='divAppntUnit' style="display:'none'">			
  <Table class=common>
               <tr class=common>
					<td class=title>�ʲ��ܶ�(Ԫ)</td>
					<td class=input>
						<input class=common name=Asset>
					</td>
					<td class=title>��λ����</td>
					<td class=input>
						<input class=codeno name=GrpNature  ondblclick="showCodeList('GrpNature',[this,GrpNatureName],[0,1],null,null,null,null,100);" onkeyup="showCodeListKey('GrpNature',[this,GrpNatureName],[0,1],null,null,null,null,100);"><input class=codename name=GrpNatureName readonly=true >
					</td>
				</tr>
				<tr class=common>
					<td class=title>��ҵ���</td>
					<td class=input>
						<input class=codeno name=BusinessType  ondblclick="return showCodeList('BusinessType',[this,BusinessTypeName],[0,1],null,null,null,null);" onkeyup="return showCodeListKey('BusinessType',[this,BusinessTypeName],[0,1],null,null,null,null);"><input class=codename name=BusinessTypeName readonly=true elementtype=nacessary >
					</td>
					<td class=title>Ա������</td>
					<td class=input>
						<input class=common name=Peoples   >
					</td>
					<td class=title>��λ����</td>
					<td class=input>
						<input class=common name=Fax>
					</td>
				</tr>
					<tr>
						<td class=title>��λ���˴���</td>
					<td class=input>
						<input class=common name=Corporation >
					</td>
					
					
				</tr>
				<tr class=common>
					<td class=title>������ϵ�˶�</td>
				</tr>
				<tr class=common>
					<td class=title>��  ��</td>
					<td class=input>
						<input class=common name=LinkMan2 >
					</td>
					<td class=title>��ϵ�绰</td>
					<td class=input>
						<input class=common name=Phone2 >
					</td>
					<td class=title>E-MAIL</td>
					<td class=input>
						<input class=common name=E_Mail2 >
					</td>
				</tr>
				<tr class=common>
					<td class=title>��  ��</td>
					<td class=input colspan=3>
						<input class=common3 name=Department2 >
					</td>
				</tr>
				<tr class=common>
					<td class=title>���ʽ</td>
					<td class=input>
						<input class=codeno name=GetFlag  ondblclick="return showCodeList('paymode',[this,GetFlagName],[0,1]);" onkeyup="return showCodeListKey('PayMode',[this,GetFlagName],[0,1]);"><input class=codename name=GetFlagName readonly=true >
					</td>
					<td class=title>��������</td>
					<td class=input>
            <Input NAME=BankCode VALUE="" CLASS="code" MAXLENGTH=20 readonly  ondblclick="return queryBank();" onkeyup=" queryBank();" > 						
					</td>
					<td class=title>��  ��</td>
					<td class=input>
						<input class=common name=BankAccNo >
					</td>

				</tr>
  </Table>
  </Div>
<!--
				<tr class=common>
					<td class=title>�ͻ���ע����</td>
					<td class=input>
						<input class=codeno name=ClientCare  ondblclick="return showCodeList('clientcare',[this,ClientCareContent],[0,1]);" onkeyup="return showCodeListKey('clientcare',[this,ClientCareContent],[0,1]);"><input class=codename name=ClientCareContent readonly=true elementtype=nacessary >
					</td>
          <td class="title">Ͷ��������Ĳ�Ʒ��ͻ����������Ƿ�һ��
          </td>
          <td class="title">
	        <Input class="code" name=ClientNeedJudge CodeData="0|^Y|��^N|��" ondblclick="showCodeListEx('ClientNeedJudge',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('ClientNeedJudge',[this,''],[0,1]);" elementtype=nacessary>             
          </td>
          <td class="title">��ͻ�����ı��������������Բ���,�Ƿ�����ͻ��������
          </td>
          <td class="title">
	        <Input class="code" name=FundReason CodeData="0|^Y|��^N|��" ondblclick="showCodeListEx('FundReason',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('FundReason',[this,''],[0,1]);" elementtype=nacessary>          
          </td>	        
				<tr class=common>        
          <td class="title">�����Ƿ���Դ��Ͷ����λ
          </td>
          <td class="title">
	        <Input class="code" name=FundJudge CodeData="0|^Y|��^N|��" ondblclick="showCodeListEx('FundJudge',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('FundJudge',[this,''],[0,1]);" elementtype=nacessary>          
          </td>
				</tr>			
-->					
       	<!--table class=common>
       	<tr class=common>
					<td class=title>�ʽ���Ͷ�����ṩԭ������</td>
				</tr>
				<tr class=common>
					<td class=title>
						<textarea name="FundReason" cols="110" rows="3" class="common"></textarea>
					</td>
				</tr>
			</table-->			
<!--			
			<table class=common>
				<tr class=common>
					<td class=title>��ע</td>
				</tr>
				<tr class=common>
					<td class=title>
						<textarea name="Remark" cols="110" rows="3" class="common"></textarea>
					</td>
				</tr>
				<tr class=common>
					<td class=title>�ر�Լ��</td>
				</tr>
				</table>
		<div id="divSpecInfo" style="display:''">
          <table class="common">
              <tr class="common">
                  <td style="text-align:left" colSpan="1">
                      <span id="spanSpecInfoGrid">
                      </span>
                  </td>
              </tr>
          </table>
-->          
    <table class="common">
    <!--
				<tr class=common>
					<td class=title>
						<textarea name="GrpSpec" cols="110" rows="7.5" class="common"></textarea>
					</td>
				</tr>
    -->				
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
		<Div id="divhiddeninfo" style="display: 'none'">
			<table class=common>
				<tr class=common>
					<td class=title>��������</td>
					<td class=input>
						<!--<input class="readonly" readonly name=SaleChnl verify="��������|code:SaleChnl&notnull">-->
						<input class=codeno  name=SaleChnl ondblclick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" onkeyup="return showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1]);"><input class=codename name=SaleChnlName readonly=true elementtype=nacessary>
					</td>   
					<td class=title>�籣�Ǽ�֤����</td>
					<td class=input>
						<input class=common name=BankAccNo1 >
					</td>
					<td class="title">ǩ����</td>
					<td class="input">
            <input class="common" name="SignReportNo">
					</td>
					<td class="title">����Э�����</td>
					<td class="input">
            <input class="common" name="AgentConferNo">
					</td>
					<td class="title">����Э�����</td>
					<td class="input">
            <input class="common" name="ConferNo">
					</td>
					<td class=title>ע���ʱ���</td>
					<td class=input>
						<input class=common name=RgtMoney verify="ע���ʱ���|num&len<=17">
					</td>
					<td class=title>���ʲ�������</td>
					<td class=input>
						<input class=common name=NetProfitRate verify="���ʲ�������|num&len<=17">
					</td>
				<td class="title">
        �Ǽ�ҵ��Ա
      </td>
      <td class="input">
        <input class="codeno" readonly="readonly" name="starAgent"><input class="codename" name="starAgentName" readonly="readonly">
      </td>
				</tr>
				<tr class=common>
					<td class=title>��Ӫҵ��</td>
					<td class=input>
						<input class=common name=MainBussiness verify="��Ӫҵ��|len<=60">
					</td>
					
					<td class=title>�����ֲ�����</td>
					<td class=input>
						<input class=common name=ComAera verify="�����ֲ�����|len<=30">
					</td>
				</tr>
				<tr class=common>
					<td class=title>ְ��</td>
					<td class=input>
						<input class=common name=HeadShip1 verify="������ϵ��һְ��|len<=30">
					</td>
					<td class=title>����</td>
					<td class=input>
						<input class=common name=Fax1 verify="������ϵ��һ����|len<=30">
					</td>
				</tr>
				<tr class=common>
					<td class=title>ְ��</td>
					<td class=input>
						<input class=common name=HeadShip2 verify="������ϵ�˶�ְ��|len<=30">
					</td>
					<td class=title>����</td>
					<td class=input>
						<input class=common name=Fax2 verify="������ϵ�˶�����|len<=30">
					</td>
				</tr>
				<tr class=common>
					<td class=title>����</td>
					<td class=input>
						<input class=code8 name=Currency verify="����|len<=2" ondblclick="showCodeList('currency',[this]);" onkeyup="showCodeListKey('currency',[this]);">
					</td>
				</tr>
			</table>
		</DIV>
		<table>
			<tr>
				<td class=common>
					<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol3);">
				</td>
				<td class=titleImg>������Ϣ</td>
			</tr>
		</table>
		<Div id="divGroupPol3" style="display: ''">
			<Div id="divGroupPol5" style="display: 'none'">
				<table class=common>
					<tr class=common>
						<td class=title>�����ͬ����</td>
						<td class=input>
							<input class="readonly" readonly name=GrpContNo >
						</td>
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
			<table>
				<tr>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol4);">
					</td>
					<td class=titleImg>������Ϣ</td>
				</tr>
			</table>
			<Div id="divGroupPol4" style="display: ''">
				<table class=common>
					<tr class=common>
						<td class=title>���ֱ���</td>
						<td class=input>
							<input class=codeno name=RiskCode ondblclick="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,RiskCodeName],[0,1],null,null,null,true,'400');" onkeyup="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,RiskCodeName],[0,1],null,null,null,1,'400');"><input class=codename name=RiskCodeName readonly=true elementtype=nacessary>							
							<!--Input class= "codeno" name=RiskCode  ondblclick="initRiskGrp(this,RiskCodeName);" onkeyup="initRiskGrp(this,RiskCodeName);" ><input class=codename name=RiskCodeName readonly=true elementtype=nacessary-->
						<!--Input name=riskgrade class='codeno' id="riskgrade" ondblclick="getcodedata2();return showCodeListEx('riskgrade',[this,riskgradeName],[0,1],'', '', '', true);" onkeyup="getcodedata2();return showCodeListEx('riskgrade',[this,cooperateName],[0,1]);"><input name=riskgradeName class=codename readonly=true><font color=red>*</font-->                                  
                </td>
						<td class=title>�����ڼ�</td>
						<td class=input>
							<input class=codeno name=PayIntv ondblclick="return showCodeList('RiskPayIntv',[this,PayIntvName],[0,1],null,fm.RiskCode.value,'RiskCode');" onkeyup="return showCodeListKey('RiskPayIntv',[this,PayIntvName],[0,1],null,fm.RiskCode.value,'RiskCode');"><input class=codename name=PayIntvName readonly=true elementtype=nacessary>
						</td>
						<td class=title>�ֱ����</td>
						<td class=input>
							 <Input class="code" name=DistriFlag CodeData="0|^0|��^1|�����ֱ�^2|��ҵ�ֱ�" ondblclick="showCodeListEx('condition3',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('condition3',[this,''],[0,1]);"> 
			
						</td>
						
		      </tr>
		            <tr class=common>
		            <td class=title>�����ѱ���</td>
							<td CLASS="input">
								<input class=common NAME="ChargeFeeRate"  value="-1"    verify="�����ѱ���|value<=1||value=-1"  onKeypress="if (event.keyCode < 46 || (event.keyCode > 46 && event.keyCode < 48)|| event.keyCode > 57 ) event.returnValue = false;">
				    	    </td>
				    <td class=title>Ӷ�����</td>
							<td CLASS="input">
								<input class=common NAME="CommRate" value="-1"    verify="Ӷ�����|value<=1||value=-1" onKeypress="if (event.keyCode < 46 || (event.keyCode > 46 && event.keyCode < 48)|| event.keyCode > 57 ) event.returnValue = false;">
				    	</td>
		            </tr>
		    </table>
		    <div id ="divFH" style="display:'none'">
		    <table class=common>
		      	<TD  class= title>�ֺ���</TD>
				<TD  class= input>
	               <Input class="code" name=StandbyFlag1  CodeData="0|^0|����ֺ�^1|��׼�ֺ�" ondblclick="showCodeListEx('condition2',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('condition2',[this,''],[0,1]);"> 		
			      </td>
		    </table>
		    </div>
		    <div  id= "divExplain" style= "display: 'none'">
		    <table class=common>
		      <tr class=common>
		      
		       
			    <td class=title>�����������</td>
							<td CLASS="input">
								<input class=common NAME="BonusRate" value="0.0"    verify="�����������|value<=1||value=-1" onKeypress="if (event.keyCode < 46 || (event.keyCode > 46 && event.keyCode < 48)|| event.keyCode > 57 ) event.returnValue = false;">
				    	</td>
	   <td class=title>�̶��������</td>
							<td CLASS="input">
								<input class=common NAME="FixprofitRate" value="0.0"    verify="�̶��������|value<=1||value=-1" onKeypress="if (event.keyCode < 46 || (event.keyCode > 46 && event.keyCode < 48)|| event.keyCode > 57 ) event.returnValue = false;">
				    	</td>  
				    	<td></td>
		      </tr>
        	</table>
           </div>
<!--
						<td class=title>Ԥ������</td>
						<td class=input>
							<input class=common name=ExpPeoples  elementtype=nacessary>
						</td>
-->
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
			</Div>
		</DIV>
		<Div id="divHidden" style="display: 'none'">
			<table class=common>
				<tr class=common>
					<td class=title>�ⶥ��</td>
					<td class=input>
						<input class=common name=PeakLine>
					</td>
				</tr>
				<tr class=common>
					<td class=title>ҽ�Ʒ����޶�</td>
					<td class=input>
						<input class=common name=MaxMedFee>
					</td>
				</tr>
			</table>
		</Div>
		<hr/>
		<Div id="divnormalbtn" style="display: ''">
			
	<input class=cssButton VALUE="���ռƻ�����"  TYPE=button onclick="grpRiskPlanInfo()">
			<!--input type =button class=cssButton value="��ӱ�����" onclick="grpInsuInfo();"-->
			
			<input class=cssButton VALUE="�������嵥"  TYPE=button onclick="grpInsuList()">
			<input class=cssButton VALUE="��ӱ�����"  TYPE=button onclick="getintopersoninsured();">
		<input class=cssButton VALUE="�ֵ�����"  disabled=true TYPE=button onclick="grpSubContInfo()">
		<input value="����������" disabled=true class=cssButton type=button onclick="ascriptDefine();">
		<input value="����Ѷ���" class=cssButton type=button onclick="feeDefine();">
		<!--input value="�ɷѹ�����" class=cssButton type=button onclick="payDefine();"-->
		<!--input class=cssButton VALUE="������Ϣ"  TYPE=button onclick="grpFeeInput()"-->
		<!--input value="�� Լ ¼ ��" class=cssButton type=button onclick="showSpecInfo();"-->
		<input class=cssButton VALUE="�������ο���" TYPE=button onclick="ctrlclaimduty();">

		<input value="���ŵ������"  class=cssButton type=button onclick="showRealFee();">

		<!--input class=cssButton VALUE="����Ѳ�ѯ" TYPE=button onclick="ManageFeeCal();"-->
		<!--INPUT class=cssButton VALUE="����ֲ�"  TYPE=button onclick="grpPersonAge()"-->
			</Div>
			<Div id="divapprovenormalbtn" style="display: 'none'">
			<!--<input class=cssButton VALUE="������Ϣ"  TYPE=button onclick="grpFeeInput()">-->
			<!--<input class=cssButton VALUE="���ռƻ�����"  TYPE=button onclick="grpRiskPlanInfo()">-->
			<!--input value="����������" class=cssButton type=button onclick="ascriptDefine();"-->
			
			<input class=cssButton VALUE="�������嵥"  TYPE=button onclick="grpInsuList()">
			
			<input class=cssButton VALUE="���ռƻ��鿴" name=buttonContPlan  TYPE=button onclick="grpRiskPlanInfo()">
			<input class=cssButton VALUE="�ֵ�����"  disabled=true TYPE=button onclick="grpSubContInfo()">
		<input value="����������" class=cssButton type=button disabled=true onclick="ascriptDefine();">
		<input value="����Ѷ���" class=cssButton name=buttonManageFee type=button onclick="feeDefine();">
		<!--input value="�ɷѹ�����" class=cssButton type=button onclick="payDefine();"-->
			<!--input class=cssButton VALUE="������Ϣ�鿴"  TYPE=button onclick="grpFeeInput()"-->
			<input class=cssButton VALUE="�������ο���" name=buttonClaimDutyCtrl TYPE=button onclick="ctrlclaimduty();">
			<INPUT VALUE ="����ɨ�����ѯ" Class="cssButton" name=buttonScanDoc TYPE=button onclick="ScanQuery2();"> 
			<!--input class=cssButton VALUE="����Ѳ�ѯ" TYPE=button onclick="ManageFeeCal();"-->
			
			<input value="���ŵ������" id="feewatch"  name=buttonShowManageFee class=cssButton type=button onclick="showRealFee();">
			<!--<input class=cssButton VALUE="�ֵ�����"  TYPE=button onclick="grpSubContInfo()">-->
			</Div>
			<Div id="divnopertoperbtn" style="display: 'none'">
			<!--input class=cssButton VALUE="������Ϣ"  TYPE=button onclick="grpFeeInput()"-->
			<!--<input class=cssButton VALUE="���ռƻ�����"  TYPE=button onclick="grpRiskPlanInfo()">-->
			<!--input class=cssButton VALUE="�������嵥"  TYPE=button onclick="grpInsuList()"-->
			<input class=cssButton VALUE="������"  TYPE=button onclick="grpfilllist()">
			<input class=cssButton VALUE="���ռƻ��鿴"  TYPE=button onclick="grpRiskPlanInfo()">
			<INPUT VALUE="����������" class= cssButton TYPE=button onclick="AlladdNameClick();">
			<!--<input class=cssButton VALUE="�ֵ�����"  TYPE=button onclick="grpSubContInfo()">-->
			</Div>
			<Div id="divnormalquesbtn" style="display: 'none'" align=right>
			<hr/>
			<input VALUE="¼�����"   class=cssButton TYPE=button onclick="GrpInputConfirm(1);">
			<INPUT VALUE="���±��鿴" class=cssButton TYPE=button onclick="showNotePad();">
			<input VALUE="�������ѯ"   class=cssButton TYPE=button onclick="GrpQuestQuery();">
			<input VALUE="�����¼��"   class=cssButton TYPE=button onclick="GrpQuestInput();">
		</Div>
		<Div id="divapprovebtn" style="display: 'none'" align=right>
			<hr/>
			<INPUT class=cssButton VALUE="���±��鿴" TYPE=button onclick="showNotePad();">
			<input VALUE="����ͨ��" class=cssButton TYPE=button onclick="gmanuchk();">
			<input VALUE="�������ѯ"   class=cssButton name=buttonIssueQuery TYPE=button onclick="GrpQuestQuery();">
			<input VALUE="�����¼��"   class=cssButton TYPE=button onclick="GrpQuestInput();">
			<input VALUE="��  ��" class=cssButton TYPE=button onclick="goback();">
		</Div>
		<Div id="divapproveupdatebtn" style="display: 'none'" align=right>
			<hr/>
			<INPUT class=cssButton VALUE="���±��鿴" TYPE=button onclick="showNotePad();">
			<!--input VALUE="��  ��" class=cssButton TYPE=button onclick="updateClick();"-->
			<input VALUE="������޸�ȷ��" class=cssButton TYPE=button onclick="GrpInputConfirm(2);">
			<input VALUE="�������ѯ"   class=cssButton TYPE=button onclick="GrpQuestQuery();">
			<input VALUE="�����¼��"   class=cssButton TYPE=button onclick="GrpQuestInput();">
			<input VALUE="��  ��" class=cssButton TYPE=button onclick="goback();">
		</Div>
		<Div id="divapproveupdatebtn1" style="display: 'none'" align=right>
			<hr/>
			<INPUT class=cssButton VALUE="���±��鿴" TYPE=button onclick="showNotePad();">
			<!--input VALUE="��  ��" class=cssButton TYPE=button onclick="updateClick();"-->
			<input VALUE="������޸�ȷ��" class=cssButton TYPE=button onclick="GrpInputConfirm(2);">
			<input VALUE="������ظ�"   class=cssButton TYPE=button onclick="GrpQuestQuery();">
			<!--input VALUE="���������¼��"   class=cssButton TYPE=button onclick="GrpQuestInput();"-->
			<input VALUE="��  ��" class=cssButton TYPE=button onclick="goback();">
		</Div>
		<Div id="divchangplanbtn" style="display: 'none'" align=right>
			<!--input VALUE="��  ��" class=cssButton TYPE=button onclick="updateClick();"-->
			<input VALUE="�������ѯ"   class=cssButton TYPE=button onclick="GrpQuestQuery();">
			<input VALUE="�����¼��"   class=cssButton TYPE=button onclick="GrpQuestInput();">
			<input VALUE="��  ��" class=cssButton TYPE=button onclick="goback();">
			</Div>

			<Div id="divuwupdatebtn" style="display: 'none'" align=left>
			<hr/>
			<input VALUE="��  ��" class=cssButton TYPE=button onclick="goback();">
		</Div>
		<Div id="divquerybtn" style="display: 'none'" align=right>
			<hr/>
			<input VALUE="�������ѯ"   class=cssButton TYPE=button onclick="GrpQuestQuery();">
			<input VALUE="��  ��" class=cssButton TYPE=button onclick="parent.close();">
		</Div>
		<div id="autoMoveButton" style="display: none">
			<input type="button" name="autoMoveInput" value="�涯����ȷ��" onclick="submitAutoMove('21');" class=cssButton>
			<input type="button" name="Next" value="��һ��" onclick="location.href='ContInsuredInput.jsp?LoadFlag='+tLoadFlag+'&prtNo='+prtNo+'&checktype=2&scantype='+scantype" class=cssButton>
			<input VALUE="��  ��" class=cssButton TYPE=button onclick="top.close();">
			<input type=hidden id="" name="autoMoveFlag">
			<%
          String today=PubFun.getCurrentDate();
          
          %>
			<input type=hidden id="" name="sysdate" value="<%=today%>">
			<input type=hidden id="" name="autoMoveValue">
			<input type=hidden id="" name="pagename" value="21">
			</div>
			<input type=hidden id="fmAction" name="fmAction">
			<input type=hidden id="" name="WorkFlowFlag">
					<input class=common type=hidden name=AgentCode1>
					<input class=common type=hidden name=BankCodeName>
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
						<input type="hidden" name=SpecFlag class=common>

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
