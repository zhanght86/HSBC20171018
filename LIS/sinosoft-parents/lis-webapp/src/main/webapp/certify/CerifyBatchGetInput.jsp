<%
//Name��CerifyBatchGetInput.jsp
//Function����֤�����������Ϲ���
//Author��zhangzheng
//Date: 2009-08-10
%>

<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.certify.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//=======================BGN========================
  String CurrentDate = PubFun.getCurrentDate();
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");

//=======================END========================
%>
<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="CerifyBatchGet.js"></SCRIPT>
  <%@include file="CerifyBatchGetInit.jsp"%>
</head>

<body  onload="initForm()" >

<form action="./CerifyBatchGetSave.jsp" method=post name=fmload id=fmload target="fraSubmit" ENCTYPE="multipart/form-data">    
  <table>
  <TR>
    <TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divreport4);"></TD>
    <TD class= titleImg>�������</TD>
  </TR>
  </table>
  <Div id="divreport4" style="display:''" class="maxbox1">
  <table class=common>
  <tr class=common>
      <TD class=title5>��֤״̬</TD>
      <TD class=input5>
			<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=CertifyState id=CertifyState CodeData="0|3^6|ʹ������^7|ͣ������^10|��ʧ" ondblclick="return showCodeListEx('CertifyState', [this,CertifyStateName],[0,1])" onclick="return showCodeListEx('CertifyState', [this,CertifyStateName],[0,1])" onkeyup="return showCodeListKeyEx('CertifyState', [this,CertifyStateName],[0,1])" ><Input class=codename name=CertifyStateName id=CertifyStateName readonly=true>
      </TD>   
      <TD class=title5 >�ļ���ַ:</TD>   
      <TD class=input5 >
        <Input type="file" name=FileName id=FileName size=20>
        <input name=ImportPath type= hidden>
        <input class=common name=BatchNo type=hidden>
        
      </TD>  </tr>  
     <input type=hidden name=ImportFile>
  </table> </Div>
  <INPUT class=cssButton VALUE="��  ��" TYPE=button name="PrintIn" onclick="getin();">
</form>

 <form action="./CertifyPrint.jsp" method=post name=fm target="fraSubmit">    
  <p>
  <table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divCertifyList);"></td>
		<td class=titleImg>δ�����嵥</td>
	</tr>
</table>
 <Div id="divCertifyList" style="display:''" class="maxbox1">
<table  class=common>
	<tr class="common">
		<td class="title5">��ʼ����</td>
		<td class="input5">
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��ʼ����|NOTNULL" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            <font
			color=red>*</font></td>
		<td class="title5">��������</td>
		<td class="input5">
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="��������|NOTNULL" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            <font
			color=red>*</font></td>
	</tr></table></Div>
<input value="δ�ɹ������嵥����" type="button" onclick="certifyPrint()" class="cssButton">
</form>
</Div>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
