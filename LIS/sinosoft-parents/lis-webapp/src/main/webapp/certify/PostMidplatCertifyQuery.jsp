<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	//�������ƣ�PostMidplatCertifyQuery.jsp
	//�����ܣ��ʱ�ͨ��֤������ѯ
	//�������ڣ�2007-11-07
	//������  ��zhangzheng���򴴽�
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="PostMidplatCertifyQuery.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="PostMidplatCertifyQueryInit.jsp"%>
<title>�ʱ�ͨ��֤������ѯ</title>
</head>
<body onload="initForm();">
<form action="" method=post name=fm id=fm target="fraSubmit">
<table class="common" border=0 width=100%>
	<tr>
    	 <td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLLReport1);"></td>
		 <td class="titleImg" align="center">��ѯ������</td>
	</tr>
</table>
<Div id= "divLLReport1" style= "display: ''"><div class="maxbox">
<table class="common" align="center" id="tbInfo" name="tbInfo">
	<tr class="common">
		<td class="title5">��֤����</td>
		<td class="input5"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name="CertifyCode2" id="CertifyCode2"
			CodeData="0|^250101|�ʱ�ͨ���յ�|^5201|�����������մ����տ��վ�|" verify="��֤����|NOTNULL"
			ondblclick="return showCodeListEx('CertifyCode', [this,CertifyCodeName],[0,1],null,null,null,1);"
            onMouseDown="return showCodeListEx('CertifyCode', [this,CertifyCodeName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKeyEx('CertifyCode', [this,CertifyCodeName],[0,1],null,null,null,1);"><Input
			class=codename name=CertifyCodeName id=CertifyCodeName readonly=true><font
			color=red>*</font></TD>
		<td class="title5">��֤״̬</td>
		<td class="input5"><input  style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name="StateFlag" id="StateFlag"
			CodeData="0|^3|�ѷ���δ����|^4|�Զ�����|^5|�ֹ�����|^6|ʹ������|^7|ͣ������|^10|��ʧ|^11|����|"
			verify="ͳ��״̬|NOTNULL"
			ondblclick="return showCodeListEx('StateFlag', [this,StateFlagName],[0,1],null,null,null,1);"
            onMouseDown="return showCodeListEx('StateFlag', [this,StateFlagName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKeyEx('StateFlag', [this,StateFlagName],[0,1],null,null,null,1);"><Input
			class=codename name=StateFlagName id=StateFlagName readonly=true><font
			color=red>*</font></TD>
	</tr>

	<tr class="common">
		<td class="title5">�������ڣ���ʼ��</td>
		<td class="input5"><!--<input class="coolDatePicker"
			dateFormat="short" name="MakeDateB" verify="�������ڣ���ʼ��|NOTNULL">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDateB'});" verify="�������ڣ���ʼ��|NOTNULL" dateFormat="short" name=MakeDateB id="MakeDateB"><span class="icon"><a onClick="laydate({elem: '#MakeDateB'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            <font
			color=red>*</font></td>
		<td class="title5">�������ڣ�������</td>
		<td class="input5"><!--<input class="coolDatePicker"
			dateFormat="short" name="MakeDateE" verify="�������ڣ�������|NOTNULL">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDateE'});" verify="�������ڣ�������|NOTNULL" dateFormat="short" name=MakeDateE id="MakeDateE"><span class="icon"><a onClick="laydate({elem: '#MakeDateE'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            <font
			color=red>*</font></td>
	</tr>

	<tr class="common">
		<td class="title5">��֤��ʼ��</td>
		<td class="input5"><input class="wid" class="common" name="StartNo" id="StartNo"></td>
		<td class="title5">��֤��ֹ��</td>
		<td class="input5"><input class="wid" class="common" name="EndNo" id="EndNo"></td>
	</tr>

	<tr class="common">
		<td class="title5">ҵ��Ա/�ͻ�������</td>
		<td class="input5"><input class="wid" class="common" name="Agentcode" id="Agentcode"></td>
		<TD class=title5>�������</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ManageCom id=ManageCom
			ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);"
            onMouseDown="return showCodeList('station',[this,ManageComName],[0,1]);"
			onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input
			class="codename" name="ManageComName" id="ManageComName" readonly></TD>
	</tr>
</table>
</div>
</Div>
<input value="��  ѯ" type="button" class="cssButton"
	onclick="easyQueryClick()">
    <!--<a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a>-->


<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this, divCertifyInfo);"></td>
		<td class=titleImg>��֤��Ϣ</td>
	</tr>
</table>

<div id="divCertifyInfo" style="display: ''">
<table class="common">
	<tr class="common">
		<td text-align: left colSpan=1><span id="spanCardInfo"></span></td>
	</tr>
</table>
<center><input VALUE="��  ҳ" TYPE="button"
	onclick="turnPage.firstPage();" class="cssButton90"> <input
	VALUE="��һҳ" TYPE="button" onclick="turnPage.previousPage();"
	class="cssButton91"> <input VALUE="��һҳ" TYPE="button"
	onclick="turnPage.nextPage();" class="cssButton92"> <input
	VALUE="β  ҳ" TYPE="button" onclick="turnPage.lastPage();"
	class="cssButton93"></center>
</div>

<INPUT VALUE="��  ӡ" class=cssButton TYPE=button onclick="easyPrint()">
<!--<a href="javascript:void(0);" class="button" onClick="easyPrint();">��    ӡ</a>-->
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br />
<br />
<br />
</body>
</html>
