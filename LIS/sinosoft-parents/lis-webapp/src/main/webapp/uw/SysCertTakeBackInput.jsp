<html>
<% 
//��ջ���
//response.setHeader("Pragma","No-cache"); 
//response.setHeader("Cache-Control","no-cache"); 
//response.setDateHeader("Expires", 0); 
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 
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
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="SysCertTakeBackInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="SysCertTakeBackInit.jsp"%>
  <script src="../common/javascript/MultiCom.js"></script>
</head>
<body  onload="initForm()" >
  <form action="./SysCertTakeBackSave.jsp" method=post name=fm id=fm target="fraSubmit">
  
    
  
    <!-- ��֤���� -->
    <div class="maxbox1">
    <table class="common">
      <tr class="common">
        <td class="title5">��֤����</td>
        <td class="input5"><input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name="CertifyCode" id="CertifyCode" CodeData="0|^8888|�б����֪ͨ��^9999|�б��˱�֪ͨ��^1113|����֪ͨ��^9996|ҵ��Ա֪ͨ��^9986|�ͻ��ϲ�֪ͨ��^0000|�ܱ�֪ͨ��^0006|����֪ͨ��^0075|�ŵ��˱�Ҫ��֪ͨ��^0076|�ŵ��˱�����֪ͨ��^0081|�޸��������Ҫ����˵��֪ͨ��^0082|�б��ر�Լ��֪ͨ��^0083|�б��ӷ�֪ͨ��^0084|�˱������ջ����ղ����˱�֪ͨ��^0085|�б��޶�֪ͨ��^0086|�ܱ����ڸ�����֪ͨ��^0087|�б����˷�֪ͨ��^0088|�ֱ��ɹ�֪ͨ��^0089|�б��˱��ʾ�֪ͨ��^2000|��ȫ���֪ͨ��^2001|��ȫ�˱��ӷ�^2002|��ȫ�˱��ر�Լ��^2003|��ȫ�˱��޶�^2004|��ȫ�˱��ܱ�^2005|��ȫ�˱�����^2006|��ȫ�˱��޸�������Ҫ����^2007|��ȫ�˱��ܱ����ڸ�����^2008|��ȫ�˱���ͬ��^2009|��ȫ�˱��ʾ�^2010|��ȫ����^2011|��ȫ�˱�֪ͨ��^2012|��ȫ��������֪ͨ��^4403|�������֪ͨ��^4481|�����޸��������Ҫ����֪ͨ��^4489|����˱��ʾ�֪ͨ��^4490|����˱�֪ͨ��^7775|�˱�֪ͨ��^7009|�����������֪ͨ��^7012|������������֪ͨ��^7006|�������˺˱�֪ͨ��^4499|�����֪�ʾ�" ondblclick="return showCodeListEx('SysCertCode', [this],null,null,null,null,1)" onMouseDown="return showCodeListEx('SysCertCode', [this],null,null,null,null,1)" onkeyup="return showCodeListKeyEx('SysCertCode', [this],null,null,null,null,1)"></td>
        <td class="title5"></td>
        <td class="input5"></td>
        </tr>
        </table>
        </div>
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
          	<input class="wid" class="common" name="CertifyNo" id="CertifyNo"  verify="��֤����|NOTNULL" onchange="query();">
          <td class="title5" width="25%">��Ч����</td>
          <td class="input5" width="25%"><!--<input class="multiDatePicker" readonly name="ValidDate">-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#ValidDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=ValidDate id="ValidDate"><span class="icon"><a onClick="laydate({elem: '#ValidDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td></tr>

        <tr class="common">
          <td class="title5">������</td>
          <td class="input5"><input class="wid" class="common" name="SendOutCom" id="SendOutCom" verify="������|NOTNULL"></td>
 
          <td class="title5">������</td>
          <td class="input5"><input class="wid" class="common" name="ReceiveCom" id="ReceiveCom" verify="������|NOTNULL"></td></tr>

        <tr class="common">
          <td class="title5">������</td>
          <td class="input5"><input class="wid" class="common" readonly name="Handler" id="Handler"></td>

          <td class="title5">��������</td>
          <td class="input5"><!--<input class="multiDatePicker" readonly name="HandleDate">-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#HandleDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=HandleDate id="HandleDate"><span class="icon"><a onClick="laydate({elem: '#HandleDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td></tr>
        
        <tr class="common">
          <td class="title5">����Ա</td>
          <td class="input5"><input class="wid" class="readonly" readonly id="Operator" name="Operator"></td>
        
          <td class="title5">�������</td>
          <td class="input5"><!--<input class="multiDatePicker" readonly name="MakeDate">-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=MakeDate id="MakeDate"><span class="icon"><a onClick="laydate({elem: '#MakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td></tr>
          
        
    
        <tr class="common">
          <td class="title5">���ղ���Ա</td>
          <td class="input5"><input class="wid" class="readonly" readonly id="TakeBackOperator" name="TakeBackOperator"></td>
        
          <td class="title5">��������</td>
          <td class="input5"><!--<input class="multiDatePicker" dateFormat="short" name="TakeBackDate" verify="��������|DATE&NOTNULL">-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#TakeBackDate'});" verify="��������|DATE&NOTNULL" dateFormat="short" name=TakeBackDate id="TakeBackDate"><span class="icon"><a onClick="laydate({elem: '#TakeBackDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td></tr>

        <tr class="common">
          <td class="title5">�������κ�</td>
          <td class="input5"><input class="wid" class="readonly" readonly name="SendNo" id="SendNo"></td>
        
          <td class="title5">�������κ�</td>
          <td class="input5"><input class="wid" class="readonly" readonly name="TakeBackNo" id="TakeBackNo"></td></tr>
        
        <tr class="common">
          <td class="title5">���ղ�������</td>
          <td class="input5"><!--<input class="readonly" readonly name="TakeBackMakeDate">-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#TakeBackMakeDate'});" verify="��������|DATE&NOTNULL" dateFormat="short" name=TakeBackMakeDate id="TakeBackMakeDate"><span class="icon"><a onClick="laydate({elem: '#TakeBackMakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td>
          
          <td class="title5">���ղ���ʱ��</td>
          <td class="input5"><input class="wid" class="readonly" readonly name="TakeBackMakeTime" id="TakeBackMakeTime"></td></tr>
        
       <input type=hidden id="ContNo" name="ContNo">
  	   <input type=hidden id="MissionID" name="MissionID">
  	   <input type=hidden id="SubMissionID" name="SubMissionID">
  	   <input type=hidden id="ActivityID" name="ActivityID">
  	   <input type=hidden id="EdorNo" name="EdorNo">
  	   <input type=hidden id="CodeType" name="CodeType">
      </table>
    </div></div>

	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
    
    <input type="hidden" id="hideOperation" name="hideOperation">
    <input type="hidden" id="sql_where" name="sql_where">
    <table class="common">
    	<tr class="common">
    		<td class="input"><!--<input class="cssButton" type="button" value="��  ��" onclick="submitForm()" >
            <input class="cssButton" type="button" value="��  ѯ" onclick="queryClick()" >-->
            <a href="javascript:void(0);" class="button" onClick="submitForm();">��    ��</a>
            <!--<a href="javascript:void(0);" class="button" onClick="queryClick();">��    ѯ</a>-->
    		</td></tr>
    
    </table>
    
  	
  </form>
</body>
</html>
