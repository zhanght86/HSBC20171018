
<%
	//�������ƣ�ReceiveAccount.jsp
	//�����ܣ� ͳ�ƹ����б�ͳ�ơ�����3�յ�����
	//�������ڣ�2010-05-26
	//������  ��hanbin
	//���¼�¼��  ������    ��������     ����ԭ��/
%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tG1 = (GlobalInput) session.getValue("GI");
	String Branch = tG1.ComCode;
%>

<script language="javascript">
  var comCode ="<%=Branch%>";
</script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>

<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>

<SCRIPT src="./ReceiveAccount.js"></SCRIPT>
<%@include file="./ReceiveAccountInit.jsp"%>

<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<title>����3�յ�����</title>
</head>

<body onload="initForm();">
<form action="" method=post name=fm id=fm target="fraSubmit">
<div class="maxbox">
<Table class=common>
	<TR class=common>
		<TD class=title5>�������</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ManageCom
			ondblclick="showCodeList('station',[this,ManageComName],[0,1],null,codeSql,'1');"
            onclick="showCodeList('station',[this,ManageComName],[0,1],null,codeSql,'1');"
			onkeyup="showCodeListKey('station',[this,ManageComName],[0,1],null,codeSql,'1');"><input
			class=codename name=ManageComName readonly=true></TD>
		<TD class=title5>��������</TD>
		<TD class=input5><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=SaleChnl
			ondblclick="showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);"
            onclick="showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);"
			onkeyup="showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1]);"><input
			class=codename name=SaleChnlName readonly=true></TD>	

	</TR>
	
	<TR class=common>
		<TD class=title5>�������ڣ����ڣ�</TD>
		<TD class=input5>
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartFirstTrialDate'});" verify="�������ڣ����ڣ�|NOTNULL" elementtype=nacessary dateFormat="short" name=StartFirstTrialDate id="StartFirstTrialDate"><span class="icon"><a onClick="laydate({elem: '#StartFirstTrialDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
		<TD class=title5>�������ڣ�ֹ�ڣ�</TD>
		<TD class=input5>
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndFirstTrialDate'});" verify="�������ڣ�ֹ�ڣ�|NOTNULL" elementtype=nacessary dateFormat="short" name=EndFirstTrialDate id="EndFirstTrialDate"><span class="icon"><a onClick="laydate({elem: '#EndFirstTrialDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
	</TR>
	<TR>
		<TD class=title5>��������</TD>
		<TD class=input5><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name="State"
			CodeData="0|^35|����^16|����^21|�н�^51|��ͥ��^11|����^61|������"
			ondblClick="showCodeListEx('ModeSelect_0',[this],[0,1]);"
            onclick="showCodeListEx('ModeSelect_0',[this],[0,1]);"
			onkeyup="showCodeListKeyEx('ModeSelect_0',[this],[0,1]);"></TD>
		<TD class=title5>ͳ������</TD>
		<td class="input5"><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ComGrade
			CodeData="0|^2|�ܹ�˾^4|��������^6|��������^8|�ļ�����"
			ondblclick="showCodeListEx('ComGrade', [this,ComGradeName],[0,1])"
            onclick="showCodeListEx('ComGrade', [this,ComGradeName],[0,1])"
			onkeyup="showCodeListKeyEx('ComGrade', [this,ComGradeName],[0,1])"><input
			class="codename" name=ComGradeName readonly=true></td>

	</TR>
</Table></div>
<p><!--������--> <!--<INPUT VALUE="��   ѯ" class=cssButton TYPE=button
	onclick="easyQuery()"> <INPUT VALUE="�������غʹ�ӡ����" class=cssButto TYPE=button onclick="easyPrint();"> -->
    
    <a href="javascript:void(0);" class="button" onClick="easyQuery();">��    ѯ</a>
    <a href="javascript:void(0);" class="button" onClick="easyPrint();">�������غʹ�ӡ����</a><br><br>
    <input type=hidden id="fmtransact" name="fmtransact">
	
<Div id="divCodeGrid" style="display: ''" align=center>
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanCodeGrid"> </span>
		</td>
	</tr>
</table><br>
<center>
<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button
	onclick="getFirstPage();"> <INPUT VALUE="��һҳ" class=cssButton91
	TYPE=button onclick="getPreviousPage();"> <INPUT VALUE="��һҳ"
	class=cssButton92 TYPE=button onclick="getNextPage();"> <INPUT
	VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="getLastPage();"></center>
</div>	
</form>
<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
<script>
	<!--ѡ�������ֻ�ܲ�ѯ������¼�����-->
	var codeSql = "1  and code like #"+<%=Branch%>+"%#";
</script>