<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="CardQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="CardQueryInit.jsp"%>
  <title>���֤��ѯ</title>
</head>
<body  onload="initForm();" >
  <form action="./CardQueryOut.jsp" method=post name=fm id=fm target="fraSubmit">
    <!-- ��ͨ��֤��Ϣ���� -->
    <table class="common" border=0 width=100%>
      <tr>
		<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"></td>
		<td class="titleImg" align="center">�������ѯ������</td>
	  </tr>
	</table>
    <div class=maxbox>
    <table class="common" align="center">
      <tr class="common">
        <td class="title">ͳ��״̬</td>
        <td class="input" cols=3>
          <select size="1" class="common wid" id="Stat">
            <option value="1">���</option>
            <option value="2">�ѷ���</option>
            <option value="3">�ѻ���</option>
            <option value="4">����δ����</option>
            <option value="5">�������</option></select></td></tr>
        
      <tr class="common">
        <td class="title5">����</td>
        <td class="input5"><input class="code" id="Kind" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="javascript:alert('kind');"></td>
        
        <td class="title5">�����</td>
        <td class="input5"><input class="code" id="SubCode" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="javascript:alert('SubCode');"></td></tr>
       
      <tr class="common">
        <td class="title5">����/����</td>
        <td class="input5"><input class="common wid" id="SendOrTake"></td>

        <td class="title5">����</td>
        <td class="input5"><input class="common wid" id="Receiver"></td></tr>
        
      <tr class="common">
        <td class="title5">�����</td>
        <td class="input5"><input class="common wid" id="Picker"></td>
        
        <td class="title5">�������</td>
        <td class="input5"><input class="coolDatePicker wid" dateFormat="short" id="PickDate"></td></tr>
        
      <tr class="common">
        <td class="title5">����Ա</td>
        <td class="input5"><input class="common wid" id="Operator"></td>
        
        <td class="title5">���ʱ��</td>
        <td class="input5"><input class="coolDatePicker wid" dateFormat="short" id="EnterTime"></td></tr>
        
      <tr class="common"><td cols=4><br></td></tr>
      
      <tr class="common">
        <td class="title5">���㵥��</td>
        <td class="input5"><input class="common wid" id="ReckCode"></td>
        
        <td class="title5">����</td>
        <td class="input5"><input class="common wid" id="Count"></td></tr>
        
      <tr class="common">
        <td class="title5">��ʼ��</td>
        <td class="input5"><input class="common wid" id="BeginCode"></td>
        
        <td class="title5">��ֹ��</td>
        <td class="input5"><input class="common wid" id="EndCode"></td></tr>
        
      <tr class="common">
        <td class="title5">��֤״̬</td>
        <td class="input5"><input class="common wid" id="CertifyStat"></td>
        
        <td class="title5">��֤��־</td>
        <td class="input5"><input class="common wid" id="CertifyFlag"></td></tr>

      <tr class="common"><td cols=4><br></td></tr>
      
      <tr class="common">
        <td class="title5">�����ϼ�</td>
        <td class="input5"><input class="common wid" id="AllCount"></td>
		<td class="title5"></td>
        <td class="input5"></td>
	  </tr>
        
    </table>
    </div>
    <input value="��ѯ" type="button" class=cssButton onclick="submitForm( ); return false;">
    <input value="����" type="button" class=cssButton onclick="returnParent( );")>

    <table>
      <tr>
        <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this, divCertifyInfo);"></td>
    	<td class= titleImg>��֤��Ϣ</td></tr>
    </table>
    
  	<div id="divCertifyInfo" style="display: ;text-align:center">
      <table class="common">
        <tr class="common">
      	  <td style="text-align: left" colSpan=1><span id="spanCertifyInfo"></span></td></tr>
      </table>
      
      <input value="��ҳ" type="button" class=cssButton90>
      <input value="��һҳ" type="button" class=cssButton91>					
      <input value="��һҳ" type="button" class=cssButton92>
      <input value="βҳ" type="button" class=cssButton93>					
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
