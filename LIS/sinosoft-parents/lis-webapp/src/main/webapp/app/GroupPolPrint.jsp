<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	String tContNo = "";
			//Ĭ�������Ϊ����Ͷ����
			tContNo = request.getParameter("ContNo");
		  if (tContNo == null || "".equals(tContNo))
		  {
			tContNo = "00000000000000000000";
			}
%>

<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
%>
<script>
var manageCom = "<%=tGI.ManageCom%>"; //��¼�������
var comcode = "<%=tGI.ComCode%>"; //��¼��½����

var contNo = "<%=tContNo%>";  //���˵��Ĳ�ѯ����.
//alert(contNo);
</SCRIPT>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="GroupPolPrint.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="GroupPolPrintInit.jsp"%>
<title>���屣����ѯ</title>
</head>
<body onload="initForm();">
<form action="./GroupPolPrintSave.jsp" method=post name=fm id="fm" 
	target="fraSubmit"><!-- ������Ϣ���� -->
<TABLE>
    <TR>
        <TD class=common>
            <IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPolContion);">
        </TD>
        <TD class = titleImg>
            ������Ͷ������ѯ������
        </TD>
    </TR>
</TABLE>
<div class="maxbox">
<Div id= "divLCPolContion" style= "display: ''">		
<table class=common align=center>
	<TR class=common>
		<TD class=title5>��������</TD>
    	<TD class= input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" type="text" class="codeno" name=SaleChnl id="SaleChnl" verify="��������|code:SaleChnl" onclick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" ondblclick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" onkeyup="return showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1]);"><input class="codename" name="SaleChnlName" id="SaleChnlName"></TD>	
		<TD class= title5>�����˱���</TD>
		<TD class= input5> <Input class="common wid" name=AgentCode id="AgentCode"> </TD>
	</TR>
	<TR class=common>
		<TD class=title5>���屣����</TD>
		<TD class=input5><Input class="common wid" name=GrpContNo id="GrpContNo"></TD>
		<TD class=title5>���ֱ���</TD>
    	<TD class= input5> <Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" type="text" class="codeno" name=RiskCode id="RiskCode" onclick="return showCodeList('RiskCode',[this,RiskCodeName],[0,1],null,codeRiskSql);" ondblclick="return showCodeList('RiskCode',[this,RiskCodeName],[0,1],null,codeRiskSql);" onkeyup="return showCodeListKey('RiskCode',[this,RiskCodeName],[0,1],null,codeRiskSql);"><input class="codename" name="RiskCodeName" id="RiskCodeName"> </TD>
    </TR>
	<TR class=common>
		<TD class=title5>�������</TD>
    	<TD class= input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" type="text" class="codeno" name=ManageCom id="ManageCom" onclick="return showCodeList('station',[this,ManageComName],[0,1],null,codeSql);" ondblclick="return showCodeList('station',[this,ManageComName],[0,1],null,codeSql);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1],null,codeSql);"><input class="codename" name="ManageComName" id="ManageComName"> </TD>
		<TD class=title5>ӡˢ����</TD>
		<TD class=input5><Input class="common wid" name=PrtNo id="PrtNo"></TD>
	</TR>
	<TR class=common>
		<TD class=title5>չҵ����</TD>
		<td class="input5" nowrap="true"><Input class="common wid"
			name="BranchGroup" id="BranchGroup"> <input name="btnQueryBranch" id="btnQueryBranch" 
			class="common wid" type="button" value="?" onclick="queryBranch()"
			style="width: 20"></TD>
	</TR>
</table>
</div>
</div>
<a href="javascript:void(0)" class=button onclick="easyQueryClick();">��ѯ����</a>
<a href="javascript:void(0)" class=button name='printButton' onclick="printGroupPol();">��ӡ����</a>	
<!-- <INPUT VALUE="��ѯ����" class="cssButton" TYPE="button" onclick="easyQueryClick();"> 
<input value="��ӡ����" class="cssButton" type="button" onclick="printGroupPol();" name='printButton'> -->
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divLCPol1);"></td>
		<td class=titleImg>������Ϣ</td>
	</tr>
</table>
<Div id="divLCPol1" style="display: ''" align=center>
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanGrpContGrid"></span>
		</td>
	</tr>
</table>
<INPUT VALUE="��ҳ" class="cssButton90" TYPE=button onclick="getFirstPage();"> 
<INPUT VALUE="��һҳ" class="cssButton91" TYPE=button onclick="getPreviousPage();">
<INPUT VALUE="��һҳ" class="cssButton92" TYPE=button onclick="getNextPage();"> 
<INPUT VALUE="βҳ" class="cssButton93" TYPE=button onclick="getLastPage();">
</Div>

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
		<TD class= input5> <Input class= common name=ErrorContNo id="ErrorContNo"> </TD>
		<TD class= title5>����</TD>
		<TD class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#MakeDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=MakeDate id="MakeDate"><span class="icon"><a onClick="laydate({elem: '#MakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
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
</div>
</div>	
</div>	
</form>
<br>
<br>
<br>
<br>
<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
<script>
	<!--ѡ�������ֻ�ܲ�ѯ������¼�����-->
	var codeSql = "1  and code like #"+<%=tGI.ComCode%>+"%#";
	var codeRiskSql = "1  and RiskProp in (#G#,#A#,#B#,#D#) and NotPrintPol = #0#";
</script>
