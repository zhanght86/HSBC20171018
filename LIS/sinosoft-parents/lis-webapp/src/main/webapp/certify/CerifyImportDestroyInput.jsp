
<%
	//Name��CerifyImportDestroyInput.jsp
	//Function����֤��������
	//Author��mw
	//Date: 2009-08-24
%>

<html>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.certify.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String CurrentDate = PubFun.getCurrentDate();
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
%>
<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="CerifyImportDestroy.js"></SCRIPT>
<%@include file="CerifyImportDestroyInit.jsp"%>
</head>

<body onload="initForm()">

<form action="./CerifyImportDestroySave.jsp" method=post name=fmload id=fmload
	target="fraSubmit" ENCTYPE="multipart/form-data">
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,div0);"></td>
		<td class=titleImg>����ģ������</td>
	</tr>
</table>
<Div id="div0" style="display:''" class="maxbox1">
<table class="common">
	<tr>
		<td><a href="./CertifyDestroy20090825.xls">����˴�����</a><br>
		</TD>
	</tr>
</table>
</Div>
<table>
	<TR>
		<TD class="common"><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,div1);"></TD>
		<TD class=titleImg>��֤��������(��ע���ύ���ٵĵ�֤���ܳ���3000��)</TD>
	</TR>
</table>
<Div id="div1" style="display:''" class="maxbox1">
<table class=common>
	<tr class=common>
		<TD class=title5>�ļ���ַ:</TD>
		<TD class=input5><Input type="file" name=FileName id=FileName size=22></TD>
        <TD class=title5></TD>
        <TD class=input5></TD>
	</tr>

</table>
</Div>
<INPUT class=cssButton VALUE="��������" TYPE=button
			name="PrintIn" onclick="getin()">
           <!-- <a href="javascript:void(0);" name="PrintIn" class="button" onClick="getin();">��������</a>-->
            
		<input name=ImportPath type=hidden>
		<input name=BatchNo type=hidden>
		<input name=ImportFile type=hidden>
		<input name=dManageCom type=hidden VALUE='<%=tG.ComCode%>' >
</form>

<form action="./CertifyPrint.jsp" method=post name=fm id=fm target="fraSubmit">
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,div);"></td>
		<td class=titleImg>�������β�ѯ</td>
	</tr>
</table>

<Div id="div" style="display:''" class="maxbox1">
<table class="common">		
	<tr class="common">
		<td class="title5">��ʼ����</td>
		<td class="input5">
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate1'});" verify="��ʼ����|NOTNULL" dateFormat="short" name=StartDate1 id="StartDate1"><span class="icon"><a onClick="laydate({elem: '#StartDate1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            <font
			color=red>*</font></td>
		<td class="title5">��������</td>
		<td class="input5">
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate1'});" verify="��������|NOTNULL" dateFormat="short" name=EndDate1 id="EndDate1"><span class="icon"><a onClick="laydate({elem: '#EndDate1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            <font
			color=red>*</font></td>
	</tr>
	</table></Div>
   <input value="��  ѯ" type="button" onclick="SerialQuery()"
				class="cssButton">
               <!-- <a href="javascript:void(0);" class="button" onClick="SerialQuery();">��    ѯ</a>-->
	<table>
		<tr>
			<td class=common><IMG src="../common/images/butExpand.gif"
				style="cursor:hand;" OnClick="showPage(this, divSerialInfo);"></td>
			<td class=titleImg>������Ϣ</td>
		</tr>
	</table>
	<div id="divSerialInfo" style="display: ''">
	<table class="common">
		<tr class="common">
			<td text-align: left colSpan=1><span id="spanSerialInfoGrid"></span></td>
		</tr>
	</table>
	<center>
		<input VALUE="��  ҳ" TYPE="button"	onclick="turnPage2.firstPage();" class="cssButton90"> 
		<input VALUE="��һҳ" TYPE="button" onclick="turnPage2.previousPage();" class="cssButton91"> 
		<input VALUE="��һҳ" TYPE="button" onclick="turnPage2.nextPage();" class="cssButton92">
		<input VALUE="β  ҳ" TYPE="button" onclick="turnPage2.lastPage();" class="cssButton93">
	</center>
	</div>

</table>
</Div>
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,div2);"></td>
		<td class=titleImg>������Ϣ��ѯ</td>
	</tr>
</table>
<Div id="div2" style="display:''" class="maxbox1">
<table class="common">
	<tr class="common">
		<td class="title5">��֤����</td>
		<td class="input5"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name="CertifyCode" id="CertifyCode"
			ondblclick="return showCodeList('CertifyCode', [this,CertifyName],[0,1],null,null,null,1);"
            onclick="return showCodeList('CertifyCode', [this,CertifyName],[0,1],null,null,null,1);"
			onkeyup="return showCodeListKey('CertifyCode', [this,CertifyName],[0,1],null,null,null,1);"><input
			class=codename name=CertifyName id=CertifyName readonly></td>
		<TD class=title5>����״̬</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=State id=State
			CodeData="0|^Y|�ɹ�^N|ʧ��"
			ondblClick="showCodeListEx('State',[this,StateName],[0,1]);"
            onClick="showCodeListEx('State',[this,StateName],[0,1]);"
			onkeyup="showCodeListKeyEx('State',[this,StateName],[0,1]);"><Input
			class=codename name=StateName id=StateName readonly=true></TD>
	</tr>
	<tr class="common">
		<td class="title5">��ʼ����</td>
		<td class="input5"><Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��ʼ����|NOTNULL" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><font
			color=red>*</font></td>
		<td class="title5">��������</td>
		<td class="input5"><Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="��������|NOTNULL" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><font
			color=red>*</font></td>
	</tr>
	<tr class="common">
		<TD  class= title5>
          ���κ�
         </TD>
         <TD  class= input5>
          <Input NAME=SerialNo id=SerialNo class="wid" >
        </TD>
	</tr>
	</table></Div>
    <input value="��  ѯ" type="button" onclick="certifyQuery()"
				class="cssButton">
                <!--<a href="javascript:void(0);" class="button" onClick="certifyQuery();">��    ѯ</a>-->
	<table>
		<tr>
			<td class=common><IMG src="../common/images/butExpand.gif"
				style="cursor:hand;" OnClick="showPage(this, divCardImportInfo);"></td>
			<td class=titleImg>������Ϣ</td>
		</tr>
	</table>
	<div id="divCardImportInfo" style="display: ''">
	<table class="common">
		<tr class="common">
			<td text-align: left colSpan=1><span id="spanCardImportInfoGrid"></span></td>
		</tr>
	</table>
	<center><input VALUE="��  ҳ" TYPE="button"
		onclick="turnPage2.firstPage();" class="cssButton90"> <input
		VALUE="��һҳ" TYPE="button" onclick="turnPage2.previousPage();"
		class="cssButton91"> <input VALUE="��һҳ" TYPE="button"
		onclick="turnPage2.nextPage();" class="cssButton92"> <input
		VALUE="β  ҳ" TYPE="button" onclick="turnPage2.lastPage();"
		class="cssButton93"></center>
	</div>

<!--	<tr>
		<td></td>
	</tr>
</table>--><input value="�嵥����" type="button" onclick="certifyPrint()"
			class="cssButton">
<!--<a href="javascript:void(0);" class="button" onClick="certifyPrint();">�嵥����</a>-->
</Div>
</form>

<span id="spanCode" style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
