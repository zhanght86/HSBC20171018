<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
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
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="ReProposalPrt.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="ReProposalPrtInit.jsp"%>
<title>�ش����</title>
</head>
<body onload="initForm();">
<form action="./ReProposalPrtSave.jsp" method=post name=fmSave id="fmSave" target="fraSubmit"><!-- ������Ϣ���� -->
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
<div class="maxbox1">
<Div id= "divLCPolContion" style= "display: ''">		
<table class=common align=center>
	<TR class=common>
		<TD class=title5>��������</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" type="text" class="codeno"  name=SaleChnl id="SaleChnl"
			verify="��������|code:SaleChnl"
			onclick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" ondblclick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);"
			onkeyup="return showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1]);"><input class="codename" name="SaleChnlName" id="SaleChnlName"></TD>
		<TD class=title5>��������</TD>
		<TD class=input5><Input class="common wid" name=ContNo id="ContNo"></TD>
	</TR>
	<TR class=common>
		<TD class=title5>�������</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" type="text" class="codeno" name=ManageCom id="ManageCom"
			 onclick="return showCodeList('station',[this,ManageComName],[0,1],null,codeSql);" ondblclick="return showCodeList('station',[this,ManageComName],[0,1],null,codeSql);"
			onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1],null,codeSql);" ><input class="codename" name="ManageComName" id="ManageComName"></TD>
		<TD class= title5>ӡˢ����</TD>
		<TD class= input5> <Input class="common wid" name=PrtNo id="PrtNo"> </TD>
	</TR>
</table>
</div>
</div>
<input type='hidden' name='BranchGroup' id="BranchGroup">
<a href="javascript:void(0)" class=button onclick="easyQueryClick();">��ѯͶ����</a>
<!-- <INPUT VALUE="��ѯͶ����" class="cssButton" TYPE=button onclick="easyQueryClick();"> -->
<table> 
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif" style="cursor: hand;" OnClick="showPage(this,divLCPol1);"></td>
		<td class=titleImg>������Ϣ</td>
	</tr>
</table>
<Div id="divLCPol1" style="display: ''">
<table class=common>
	<TR class=common>
		<td text-align:  colSpan=1><span id="spanPolGrid"></span></td>
	</tr>
</table>
<INPUT VALUE="��  ҳ" class="cssButton90" TYPE=button
	onclick="getFirstPage();"> <INPUT VALUE="��һҳ" class="cssButton91"
	TYPE=button onclick="getPreviousPage();"> <INPUT VALUE="��һҳ"
	class="cssButton92" TYPE=button onclick="getNextPage();"> <INPUT
	VALUE="β  ҳ" class="cssButton93" TYPE=button onclick="getLastPage();">
</div>
<TABLE>
    <TR>
        <TD class=common>
            <IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPolAdd);">
        </TD>
        <TD class = titleImg>
            �������ش�����Ϣ
        </TD>
    </TR>
</TABLE>
<div class="maxbox1">	
<Div id="divLCPolAdd" style="display: ''">
<table class=common align=center>
	<TR class=common>
		<TD class=title5>�� ��</TD>
		<TD class=input5><Input  style="background:url(../common/images/select--bg_03.png) 		no-repeat right center"  type="text" class="codeno"  name=NeedPay id="NeedPay" value=""
			CodeData="0|^0|�Ʒ�|^1|���Ʒ�"
			onClick="showCodeListEx('NeedPay',[this,NeedPayName],[0,1]);" ondblClick="showCodeListEx('NeedPay',[this,NeedPayName],[0,1]);"
			onkeyup="showCodeListKeyEx('NeedPay',[this,NeedPayName],[0,1]);"><input class="codename" name="NeedPayName" id="NeedPayName"></TD>

		<TD class=title5>�ش�ԭ��</TD>
		<TD class=input5><Input  style="background:url(../common/images/select--bg_03.png) 		no-repeat right center"   type="text" class="codeno"  name=Reason id="Reason" value=""
			CodeData="0|^1|��ӡ������|^2|����ʧ��|^3|���ݴ���|^4|��ʧ�����^5|����"
			onClick="showCodeListEx('Reason',[this,ReasonName],[0,1]);" ondblClick="showCodeListEx('Reason',[this,ReasonName],[0,1]);"
			onkeyup="showCodeListKeyEx('Reason',[this,ReasonName],[0,1]);"><input class="codename" name="ReasonName" id="ReasonName"></TD>
	</TR>
	<TR class=common>
		<TD class=title5>�������</TD>
		<TD class=input5><Input class="common wid" name=BatchNo id="BatchNo" elementtype=nacessary></TD>
	</TR>
</table>	
</div>
</div>
<br>
<div>
	<a href="javascript:void(0)" class=button onclick="ApplyRePrint();">�ύ����</a>
</div>
<br>
<!-- <INPUT VALUE="�� �� �� ��" class="cssButton" TYPE=button onclick="ApplyRePrint();">  -->
<input type=hidden id="fmtransact" name="fmtransact">
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
</script>
