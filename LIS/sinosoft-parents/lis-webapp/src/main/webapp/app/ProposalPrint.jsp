<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
%>
<html>
<%
GlobalInput tGI = new GlobalInput();
tGI = (GlobalInput)session.getValue("GI");
%>
<script>
var manageCom = "<%=tGI.ManageCom%>"; //��¼�������
var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="ProposalPrint.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="ProposalPrintInit.jsp"%>
<title>��ӡ���˱��� </title>
</head>
<body onload="initForm();" >
	<form action="./ProposalPrintSave.jsp" method=post name=fm id="fm" target="fraSubmit">
	<!-- ���˱�����Ϣ���� -->
<TABLE>
    <TR>
        <TD class= common>
            <IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPolContion);">
        </TD>
        <TD class = titleImg>
            ������Ͷ������ѯ������
        </TD>
    </TR>
</TABLE>
<div class="maxbox1">	
<Div id= "divLCPolContion" style= "display: ''">		
		<table class= common align=center>
			<TR class= common>
				<TD class= title5>��������</TD>
				<TD class= input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" type="text" class="codeno" name=SaleChnl id="SaleChnl" verify="��������|code:SaleChnl" onclick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1],null,Sql);" ondblclick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1],null,Sql);" onkeyup="return showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1],null,Sql);"><input class="codename" name="SaleChnlName" id="SaleChnlName" ></TD>
				<TD class= title5>��������</TD>
				<TD class= input5> <Input class="common wid" name=ContNo id="ContNo" > </TD>
			</TR>
			<TR class= common>
				<TD class= title5>���ֱ���</TD>
				<TD class= input5> <Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" type="text" class="codeno" name=RiskCode id="RiskCode" onclick="return showCodeList('RiskCode',[this,RiskCodeName],[0,1],null,codeRiskSql,1);" ondblclick="return showCodeList('RiskCode',[this,RiskCodeName],[0,1],null,codeRiskSql,1);" onkeyup="return showCodeListKey('RiskCode',[this,RiskCodeName],[0,1]);"><input class="codename" name="RiskCodeName" id="RiskCodeName" > </TD>

				<TD class= title5>ӡˢ����</TD>
				<TD class= input5> <Input class="common wid" name=PrtNo id="PrtNo"> </TD>
			</TR>
			<TR class= common>	
				<TD class= title5>�������</TD>
				<TD class= input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" type="text" class="codeno" name=ManageCom id="ManageCom" onclick="return showCodeList('station',[this,ManageComName],[0,1],null,codeSql);" ondblclick="return showCodeList('station',[this,ManageComName],[0,1],null,codeSql);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class="codename" name="ManageComName" id="ManageComName"> </TD>
			</TR>
		</table>
</div>
</div>			
<input type='hidden' name='BranchGroup' id="BranchGroup">
<a href="javascript:void(0)" class=button onclick="easyQueryClick();">��ѯ��ͨ����</a>
<a href="javascript:void(0)" class=button name='printButton' onclick="printPol();">��ӡ����</a>
<a href="javascript:void(0)" class=button name='printVIPButton' onclick="QueryVIPClick();">��ѯVIP����</a>
<!-- <INPUT VALUE="��ѯ��ͨ����" class="cssButton" TYPE=button onclick="easyQueryClick();">
<INPUT VALUE="��ӡ����" class="cssButton" TYPE=button onclick="printPol();" name='printButton' >
<INPUT VALUE="��ѯVIP����" class="cssButton" TYPE=button onclick="QueryVIPClick();"name='printVIPButton'> -->
		<table>
			<tr>
				<td class=common>
					<IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
				</td>
				<td class= titleImg>������Ϣ</td>
			</tr>
		</table>
		<Div id= "divLCPol1" style= "display: ''">
			<table class= common>
				<tr class= common>
					<td text-align: left colSpan=1>
						<span id="spanContGrid" ></span>
					</td>
				</tr>
			</table>
			<INPUT VALUE="�� ҳ" class="cssButton90" TYPE=button onclick="getFirstPage();">
			<INPUT VALUE="��һҳ" class="cssButton91" TYPE=button onclick="getPreviousPage();">
			<INPUT VALUE="��һҳ" class="cssButton92" TYPE=button onclick="getNextPage();">
			<INPUT VALUE="β ҳ" class="cssButton93" TYPE=button onclick="getLastPage();">
		</div>
	
<Div id= "divErrorMInfo" style= "display: ''">		
<TABLE>
    <TR>
        <TD class= common>
            <IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divErrorInfo);">
        </TD>
        <TD class = titleImg>
            ������ӡ������Ϣ��ѯ
        </TD>
    </TR>
</TABLE>	
		
<Div id= "divErrorInfo" style= "display: ''">	
<div class="maxbox1">
	<table class= common align=center>
		<TR class= common>
			<TD class= title5>������</TD>
			<TD class= input5> <Input class= "common wid" name=ErrorContNo id="ErrorContNo"> </TD>
			<TD class= title5>����</TD>
			<TD class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#MakeDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=MakeDate id="MakeDate"><span class="icon"><a onClick="laydate({elem: '#MakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
		</TR>
		<TR class= common> 
 			<TD  class= title5> ������� </TD>
 			<TD  class= input5><Input  style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" type="text" class="codeno" name=EManageCom id="EManageCom" onclick="return showCodeList('station',[this,EManageComName],[0,1]);" ondblclick="return showCodeList('station',[this,EManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,EManageComName],[0,1]);"><input class="codename" name="EManageComName" id="EManageComName"></TD>			
		</TR>
	</table>
</div>	
	<div>
		<a href="javascript:void(0)" class=button onclick="easyQueryErrClick();">��  ѯ</a>
	</div>
	<br>
	<!-- <INPUT VALUE="��   ѯ" class="cssButton" TYPE=button onclick="easyQueryErrClick();"> -->
	<Div id= "divLCPrintError" style= "display: ' '" align=center>
		<table class= common>
			<tr class= common>
				<td text-align: left colSpan=1>
					<span id="spanErrorGrid" ></span>
				</td>
			</tr>
		</table>
		<INPUT VALUE="�� ҳ" class="cssButton90" TYPE=button onclick="getFirstPage();">
		<INPUT VALUE="��һҳ" class="cssButton91" TYPE=button onclick="getPreviousPage();">
		<INPUT VALUE="��һҳ" class="cssButton92" TYPE=button onclick="getNextPage();">
		<INPUT VALUE="β ҳ" class="cssButton93" TYPE=button onclick="getLastPage();">
	</Div>
</Div>	
</Div>	
</form>
<br>
<br>
<br>
<br>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
<script>
	<!--ѡ�������ֻ�ܲ�ѯ������¼�����-->
	var codeSql = "1  and code like #"+<%=tGI.ComCode%>+"%#";
	var Sql="1 and code not in (#01#)";
	//NotPrintPol=0,��̨��ӡ��NotPrintPol=1,ǰ̨��ӡ��NotPrintPol=2,����ӡ
  var codeRiskSql = "1  and RiskProp in (#I#,#A#,#C#,#Y#,#D#) and NotPrintPol = #0#";
</script>
