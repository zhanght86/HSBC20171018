<%@include file="../i18n/language.jsp"%>
<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�ScanContInput.jsp
//�����ܣ���������Լɨ�������¼��
//�������ڣ�2004-12-22 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<%
//�����¸���
String tContNo="";
String tFlag="";
GlobalInput tGI=new GlobalInput();
tGI=(GlobalInput)session.getAttribute("GI");
tFlag=request.getParameter("type");
%>
<script>
var contNo="<%=tContNo%>";          //���˵��Ĳ�ѯ����.
var operator="<%=tGI.Operator%>";   //��¼����Ա
var manageCom="<%=tGI.ManageCom%>"; //��¼��½����
var type="<%=tFlag%>";
var comcode="<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="ProvisionInput.js"></SCRIPT>
 <script src="../common/javascript/MultiCom.js"></script>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="ProvisionInit.jsp"%>
<title>ɨ��¼��</title>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body onload="initForm();initElementtype();" >
	<form action="./ProvisionInputSave.jsp" method=post name=fm target="fraSubmit" ENCTYPE="multipart/form-data">
		<!-- ������Ϣ���� -->
		<table class=common border=0 width=100%>
			<tr>
				<td class=title5Img align=center></td>
			</tr>
		</table>
		<table class=common>
			<TR class=common>
				<TD class=title5>��������</TD>
				<TD class=input5>
					<Input class=codeno name=ProvisionType verify="��������|code:provisiontype" 
					ondblclick="return showCodeList('provisiontype',[this,ProvisionTypeName],[0,1]);" 
					onkeyup="return showCodeListKey('provisiontype',[this,ProvisionTypeName],[0,1]);"><input
					 class=codename name=ProvisionTypeName elementtype=nacessary>
				</TD>
				<TD class=title5></TD><TD class=title5></TD><TD class=title5></TD><TD class=title5></TD>
			</TR>
			<TR class=common>
				<TD class=title5>���ֱ���</TD>
				<TD class=input5>
					<Input class=codeno name=RiskCode verify="���ֱ���|code:riskcode" 
					ondblclick="return showCodeList('riskcode',[this,RiskCodeName],[0,1]);" 
					onkeyup="return showCodeListKey('riskcode',[this,RiskCodeName],[0,1]);"><input class=codename name=RiskCodeName elementtype=nacessary>
				</TD>
				<TD class=title5>��������</TD>
				<TD class=input5>
					<Input class=codeno name=SaleChnl verify="|code:SaleChnl" 
					ondblclick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" 
					onkeyup="return showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1]);"><input class=codename name=SaleChnlName>
				</TD>
      			<TD class=title5>�������</TD>
				<TD class=input5>
					
						<Input class=codeno name=ManageCom verify="�������|code:ManageCom" 
						ondblclick="return showCodeList('ManageCom',[this,ManageComName],[0,1]);" 
						onkeyup="return showCodeListKey('ManageCom',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName>
					
				</TD>
			</tr>
			<tr id='PTypeDiv' style="display:none;">
	      		<TD class=title5>��������</TD>
				<TD class=input5>
					<Input class=codeno name=PolProperty verify="��������|code:polproperty" 
					ondblclick="return showCodeList('polproperty',[this,PolPropertyName],[0,1]);" 
					onkeyup="return showCodeListKey('polproperty',[this,PolPropertyName],[0,1]);">
					<input class=codename name=PolPropertyName readonly="readonly" elementtype=nacessary>
				</TD>
			</tr>
		</table>
		<INPUT VALUE="��  ѯ" class=cssButton TYPE=button onclick="easyQueryClick();">
		<table>
			<tr>
				<td class=common>
					<IMG  src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLCGrp1);">
				</td>
				<td class=title5Img></td>
			</tr>
			<INPUT type="hidden" class=Common name=MissionID  value="">
			<INPUT type="hidden" class=Common name=SubMissionID  value="">
		</table>
		<Div id="divLCGrp1" style="display: ''" align=center>
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1>
						<span id="spanProvisionGrid" ></span>
					</td>
				</tr>
			</table>
			<INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage1.firstPage();">
			<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage1.previousPage();">
			<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage1.nextPage();">
			<INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage1.lastPage();">
		</div>

		<table class=common>
		<tr class=common>
			&nbsp;
		</tr>
		<TR class=common>
			<TD class=title5 style="text-align:center;"></TD>
			<TD class=common>
				<input type=hidden name=ImportPath value='upload/'>
				<Input verify="�����ļ�|notnull" type="file" name=FileName size=25 >
				<input type=hidden name=FileNameTrue>
				<!-- <INPUT class=cssButton VALUE="��  ��" TYPE=button onclick="addClick();" elementtype=nacessary> -->
				<input type=text name=SuppressWarning value='' style="display:none" />			
			</TD>
		</TR>
		</table>

		<p style="text-align:center;">
			<INPUT VALUE="ɾ  ��" class=cssButton TYPE=button onclick="deleteClick();">
			&nbsp;&nbsp;
			<INPUT VALUE="��  ��" class=cssButton TYPE=button onclick="updateClick();">
			&nbsp;&nbsp;
			<INPUT VALUE="��  ��" class=cssButton TYPE=button onclick="submitForm();">
		</p>
		<input class=common type=hidden name=tFlag value="<%=tFlag%>">
		<Input class=common type=hidden name=Operator >
		<input type=hidden name=Transact>
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>