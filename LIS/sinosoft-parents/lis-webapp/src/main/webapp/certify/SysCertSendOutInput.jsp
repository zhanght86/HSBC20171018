<% 
//��ջ���
//response.setHeader("Pragma","No-cache"); 
//response.setHeader("Cache-Control","no-cache"); 
//response.setDateHeader("Expires", 0); 
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-08-07
//������  ����ƽ
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="SysCertSendOutInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="SysCertSendOutInit.jsp"%>
</head>
<body  onload="initForm()">
  <form action="./SysCertSendOutSave.jsp" method=post name=fm id=fm target="fraSubmit">
  <!--  <table class="common">
    	<tr class="common">
    		<td class="input"></td></tr>
    
    </table>-->

    <!-- ��֤���� -->
    <div class="maxbox1">
    <table class="common">
      <tr class="common">
        <td class="title5">��֤����</td>
        <td class="input5"><input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name="CertifyCode" id="CertifyCode" ondblclick="return showCodeList('SysCertCode', [this]);" onMouseDown="return showCodeList('SysCertCode', [this]);" onkeyup="return showCodeList('SysCertCode', [this]);" verify="��֤����|NOTNULL"></td>
    	<td class="title5"></td>
        <td class="input5"></td></tr></table>
        </div>
    <!-- ���ŵ���Ϣ -->    
    <div style="width:120"><!-- this div is used to change output effect. zhouping 2002-08-07 -->
    
      <table class="common">
        <tr class="common">
          <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divSendOutInfo);"></td>
          <td class="titleImg">������Ϣ</td></tr></table></div>

    <div id="divSendOutInfo">
      <!-- ��֤����;������� -->
      <div class="maxbox1">
      <table class="common"> 
        <tr class="common">
          <td class="title5">���Ż���</td>
          <td class="input5"><input class="wid" class="common" name="SendOutCom" id="SendOutCom" verify="���Ż���|NOTNULL"></td>

          <td class="title5">���ջ���</td>
          <td class="input5"><input class="wid" class="common" name="ReceiveCom" id="ReceiveCom" verify="���ջ���|NOTNULL"></td></tr>

        <tr class="common">
          <td class="title5">������</td>
          <td class="input5"><input class="wid" class="common" name="Handler" id="Handler"></td>

          <td class="title5">��������</td>
          <td class="input5"><!--<input class="coolDatePicker" dateFormat="short" name="HandleDate">-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#HandleDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=HandleDate id="HandleDate"><span class="icon"><a onClick="laydate({elem: '#HandleDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td></tr>
        
      </table>
      </div>
    </div>

		<input type=hidden name="hideOperation">    

    <!-- ��֤�б� -->
    <table>
   	  <tr>
        <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCertifyList);"></td>
    		<td class= titleImg>��֤�б�</td></tr>
    </table>

		<div id="divCertifyList">
      <table class="common">
        <tr class="common">
          <td text-align: left colSpan=1><span id="spanCertifyList"></span></td></tr>
	  	</table>
		</div>

	  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

    <!-- 
      ���Ų�����Ա�����Ų������ڡ��Լ��������ڶ����Զ����ɵģ�����Ҫ�û����� 
    --><br>
    <!--<input class=cssButton type="button" value="���ŵ�֤" onclick="submitForm()" >-->
    <a href="javascript:void(0);" class="button" onClick="submitForm();">���ŵ�֤</a>
  </form>
</body>
</html>
