
<%
	//�������ƣ�AccQuery.jsp
	//�����ܣ��˻���Ϣ��ѯ
	//�������ڣ�2008-03-17 15:10
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tG1 = (GlobalInput) session.getValue("GI");
	String tComCode = tG1.ComCode;
%>
<script>
	<!--ѡ�������ֻ�ܲ�ѯ������¼�����-->
	var codeSql = "1  and code like #"+<%=tComCode%>+"%#";
	var comCode = "<%=tComCode%>";
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
<SCRIPT src="./AccQuery.js"></SCRIPT>
<%@include file="./AccQueryInit.jsp"%>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<title>�˺���Ϣ��ѯ</title>
</head>

<body onLoad="initForm()">
<table class=common border=0 width=100%>
	<tr>
    <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
		<td class=titleImg align=center>�������ѯ������</td>
	</tr>
</table>
<form method=post name=fm id=fm>
<div id="divInvAssBuildInfo">
       <div class="maxbox1" >
<table class=common>
	<TR class=common>
		<TD class=title5>�������</TD>
		<TD class=input5>
			<Input class=codeno name=ManageCom id=ManageCom verify="�������|notnull&code:station" 
            style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
            onclick="return showCodeList('Station',[this,ManageComName],[0,1]);" 
            onDblClick="return showCodeList('Station',[this,ManageComName],[0,1]);" 
            onKeyUp="return showCodeListKey('comcode',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true>
		</TD>
		<TD class=title5>���д���</TD>
		<TD class=input5><Input class=codeno name=BankCode id=BankCode
             style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
            onclick="return showCodeList('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');"
			ondblclick="return showCodeList('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');"
			onkeyup="return showCodeListKey('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');"
			verify="���д���|notnull" readonly=true><input class=codename
			name=BankName readonly=true></TD>
	</TR>
	<TR class=common>
		<TD class=title5>��ʼ����</TD>
		<TD class=input5>
        <Input class="multiDatePicker laydate-icon"   onClick="laydate({elem: '#StartDate'});"dateFormat="short" name=StartDate id=StartDate verify="��ʼ����|notnull&date"><span class="icon"><a onClick="laydate({elem: '#ValidStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
</TD>
		<TD class=title5>��ֹ����</TD>
		<TD class=input5>
         <Input class="multiDatePicker laydate-icon"   onClick="laydate({elem: '#EndDate'});"dateFormat="short" name=EndDate id=EndDate verify="��ֹ����|notnull&date"><span class="icon"><a onClick="laydate({elem: '#ValidStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
</TD>
	</TR>

</table>
</div>
</div>
<br>
<!--������--> <a href="javascript:void(0);" class="button"onclick="easyQuery();">��   ѯ</a>
<a href="javascript:void(0);" class="button"onClick="easyPrint();">�������غʹ�ӡ����</a>
<!--<a href="javascript:void(0);" class="button"onclick="easyPrint();" VALUE="�������غʹ�ӡ����">�������غʹ�ӡ����</a>-->
<!--<INPUT VALUE="�������غʹ�ӡ����" class=cssButton  TYPE=button onClick="easyPrint();">-->

<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick=
	showPage(this, divCodeGrid);;
></td>
		<td class=titleImg>�˺���Ϣ</td>
	</tr>
</table>
<Div id="divCodeGrid" style="display: ''">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanCodeGrid"> </span></td>
	</tr>
</table>
 <div align="center">
<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick=
	getFirstPage();;
>
<INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick=
	getPreviousPage();;
>
<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick=
	getNextPage();;
>
<INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick=
	getLastPage();;
>
</div>
</div>
<br><br><br><br>

</form>
<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
