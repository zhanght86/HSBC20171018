<% 
//��ջ���
//response.setHeader("Pragma","No-cache"); 
//response.setHeader("Cache-Control","no-cache"); 
//response.setDateHeader("Expires", 0); 
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<html> 
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	String tCurrentDate = PubFun.getCurrentDate();
%>
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-08-07
//������  ����ƽ
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script language = "javascript">
  var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
  var sysdate = "<%=tCurrentDate%>"; //��¼��½����
</script>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
   <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
     <script src="../common/javascript/EasyQuery.js"></script>
   <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="GrpSysCertTakeBackInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GrpSysCertTakeBackInit.jsp"%>
  <script src="../common/EasyScanQuery/ShowPicControl.js"></script>
  <script src="ProposalAutoMove3.js"></script>
</head>
<body  onload="initForm()" >
  <form action="./GrpSysCertTakeBackSave.jsp" method=post name=fm id=fm target="fraSubmit">
  
    
  
    <!-- ��֤���� -->
    <div class="maxbox1">
    <table class="common">
      <tr class="common">
        <td class="title5">��֤����</td>
        <td class="input5"><input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name="CertifyCode" id="CertifyCode" value ="9994" readonly =true ondblclick="return showCodeList('SysCertCode1', [this])"onkeyup="return showCodeListKey('SysCertCode1', [this])" ></td>
        <!--td class="input"><input class="cssButton" type="button" value="ɨ���ǩ����ѯ" onclick="queryScanSignature()" ></td-->
        <td></td>
        </tr>
    <!-- ���յ���Ϣ -->
    <div style="width:120">
      <table class="common">
        <tr class="common">
          <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divTakeBackInfo);"></td>
          <td class="titleImg">������Ϣ</td></tr></table></div>

    <div id="divTakeBackInfo">
      <!-- ��֤����;������� -->
      <div class="maxbox">
      <table class="common"> 
        <tr class="common">
          <td class="title5" width="25%">��֤����</td>
          <td class="input5" width="25%">
          	<input class="wid" class="common" name="CertifyNo" id="CertifyNo" onblur="checkScan();queryagent();">
          	<!--<input class="cssButton" type="button" value="��ѯ��֤" style="width:90" onclick="query()">-->
            <a href="javascript:void(0);" class="button" onClick="query();">��ѯ��֤</a></td>

          <td class="title5" width="25%">��Ч����</td>
          <td class="input5" width="25%"><!--<input class="readonly" readonly name="ValidDate">-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#ValidDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=ValidDate id="ValidDate"><span class="icon"><a onClick="laydate({elem: '#ValidDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td></tr>

        <tr class="common">
          <td class="title5">������</td>
          <td class="input5"><input class="wid" class="common" name="SendOutCom" id="SendOutCom" verify="������|NOTNULL"></td>

          <td class="title5">������</td>
          <td class="input5"><input class="wid" class="common" name="ReceiveCom" id="ReceiveCom" verify="������|NOTNULL"></td></tr>

        <tr class="common">
          <td class="title5">������</td>
          <td class="input5"><input class="wid" class="readonly" readonly name="Handler" id="Handler"></td>

          <td class="title5">��������</td>
          <td class="input5"><!--<input class="readonly" readonly name="HandleDate">-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#HandleDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=HandleDate id="HandleDate"><span class="icon"><a onClick="laydate({elem: '#HandleDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td></tr>
        
        <tr class="common">
          <td class="title5">����Ա</td>
          <td class="input5"><input class="wid" class="readonly" readonly name="Operator" id="Operator"></td>
        
          <td class="title5">�������</td>
          <td class="input5"><!--<input class="readonly" readonly name="MakeDate">-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=MakeDate id="MakeDate"><span class="icon"><a onClick="laydate({elem: '#MakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td></tr>
          
        
    
        <tr class="common">
          <td class="title5">���ղ���Ա</td>
          <td class="input5"><input class="wid" class="readonly" readonly name="TakeBackOperator"></td>
        
          <td class="title5">��������</td>
          <td class="input5"><!--<input class="common"  name="TakeBackDate" verify="��������|DATE" onblur="confirmSecondInput(this,'onblur');">-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#TakeBackDate'});" verify="��������|DATE" onblur="confirmSecondInput(this,'onblur');" dateFormat="short" name=TakeBackDate id="TakeBackDate"><span class="icon"><a onClick="laydate({elem: '#TakeBackDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td></tr>

        <tr class="common">
          <td class="title5">�������κ�</td>
          <td class="input5"><input class="wid" class="readonly" readonly name="SendNo" id="SendNo"></td>
        
          <td class="title5">�������κ�</td>
          <td class="input5"><input class="wid" class="readonly" readonly name="TakeBackNo" id="TakeBackNo"></td></tr>
        
        <tr class="common">
          <td class="title5">���ղ�������</td>
          <td class="input5"><!--<input class="readonly" readonly name="TakeBackMakeDate">-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#TakeBackMakeDate'});" verify="��������|DATE" onblur="confirmSecondInput(this,'onblur');" dateFormat="short" name=TakeBackMakeDate id="TakeBackMakeDate"><span class="icon"><a onClick="laydate({elem: '#TakeBackMakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td>
          
          <td class="title5">���ղ���ʱ��</td>
          <td class="input5"><input class="wid" class="readonly" readonly name="TakeBackMakeTime" id="TakeBackMakeTime"></td></tr>
        
      </table>
    </div>

	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
    
    <input type="hidden" name="hideOperation">
    <input type="hidden" name="hideTakeBackDate">
    <input type="hidden" name="sql_where">
    
    <input type=hidden id="PolNo" name="PolNo">
  	<input type=hidden id="PrtSeq" name="PrtSeq">
  	<input type=hidden id="MissionID" name="MissionID">
  	<input type=hidden id="EdorNo" name="EdorNo">
  	<input type=hidden id="SignatureFlag" name="SignatureFlag" value="false">
  	<input type=hidden id="LastCertifyNo" name="LastCertifyNo" value="">
  	<input type=hidden id="ScanFlag" name="ScanFlag" value="true">
  	<input type="hidden" name="QUERY_FLAG">
    </div>
<!--    <table class="common">
    	<tr class="common">
    		<td class="input"><input class="cssButton" type="button" value="�� ֤ �� ��" onclick="submitForm()" ></td>
    		<td class="input"><input class="cssButton" type="button" value="�� ֤ �� ѯ" onclick="queryClick()" ></td>
    		<td></td>
    		</tr>
    
    </table>--><br>
    <a href="javascript:void(0);" class="button" onClick="submitForm();">��֤����</a>
    <a href="javascript:void(0);" class="button" onClick="queryClick();">��֤��ѯ</a>
  </form>
</body>
</html>
