<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
String tFlag="";
GlobalInput tGI=new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");
%>
<script>
var operator="<%=tGI.Operator%>";   //��¼����Ա
var manageCom="<%=tGI.ManageCom%>"; //��¼��½����
var comcode="<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="SysCertTakeBackWorkPool.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="SysCertTakeBackWorkPoolInit.jsp"%>
<title>��֤����</title>
</head>
<body  onload="initForm();initElementtype();" >
	<form action="./NoScanContInputSave.jsp" method=post id=fm target="fraSubmit">
		<!-- ������Ϣ���� -->
		<table class=common border=0 width=100%>
			<tr>
            	<td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,mumupo);"></td>
				<td class=titleImg align=center>�����ѯ����</td>
			</tr>
		</table>
        <Div id="mumupo" style="display: ''" class="maxbox1">
        
		<table class=common>
			<TR class=common>
				<TD class="title">ӡˢ��</TD>
				<TD class="input">
					<Input class="wid" class=common name=PrtNo id=PrtNo >
				</TD>
				<!--TD class=title>Ͷ��������</TD>
				<TD class=input>
					<input class=codeno name="ContType" CodeData="0|^01|����Ͷ����^02|����Ͷ����^05|����Ͷ����" ondblclick="return showCodeListEx('SysCertCode', [this,ContTypeName],[0,1])"onkeyup="return showCodeListKeyEx('SysCertCode', [this,ContTypeName],[0,1])"><input class=codename name=ContTypeName readonly=true>
				</TD-->				
				<TD class="title">��������</TD>
				<TD class="input">
					<Input class="wid" class="common" name=ContNo id=ContNo  >
				</TD>
                <TD class="title">��ִɨ������</TD>
				<TD class="input">
					<!--<Input class="coolDatePicker" name=ScanDate >-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#ScanDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=ScanDate id="ScanDate"><span class="icon"><a onClick="laydate({elem: '#ScanDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</TD>
			</TR>
			<TR class=common>
	      		<TD class="title">�������</TD>
				<TD class="input">
					<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=qManageCom id=qManageCom ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onMouseDown="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName id=ManageComName readonly=true elementtype=nacessary>
				</TD>
				<TD class="title">ҵ��Ա����</TD>
				<TD class="input">
					<Input class="wid" class=common name=AgentCode>
				</TD>
				
      
			</tr>
		</table></div>
		<!--<INPUT VALUE="��  ѯ" class=cssButton TYPE=button onclick="easyQueryClick();">-->
        <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a>
		<!-- INPUT VALUE="��  ��" class=cssButton TYPE=button onclick="ApplyInput();" -->
        
		<table>
			<tr>
				<td class=common>
					<IMG  src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLCGrp1);">
				</td>
				<td class=titleImg>Ͷ������Ϣ</td>
			</tr>
			<INPUT type="hidden" class=Common name=MissionID  value="">
			<INPUT type="hidden" class=Common name=SubMissionID  value="">
		</table>
		<Div id="divLCGrp1" style="display: ''" align=center>
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanGrpGrid" ></span>
					</td>
				</tr>
			</table>
			<!--<INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage1.firstPage();">
			<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage1.previousPage();">
			<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage1.nextPage();">
			<INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage1.lastPage();">-->
		</div>
		<p>
			<!-- INPUT VALUE="��ʼ¼��" class=cssButton TYPE=button onclick="GoToInput();" -->
			<!--INPUT class=cssButton VALUE="���±��鿴" TYPE=button onclick="showNotePad();"-->
		</p>
		<input class=common type=hidden name=tFlag value="<%=tFlag%>">
		<Input class=common type=hidden name=Operator >
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
