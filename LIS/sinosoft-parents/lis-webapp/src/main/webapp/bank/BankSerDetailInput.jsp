<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html> 
<%
//�������ƣ�BankSerDetailInput.jsp
//�����ܣ����з������ζ�Ӧ��ϸ
//�������ڣ�2010-2-2 14:20
//������  �� Fuqx
//���¼�¼��  ������  ln  ��������     ����ԭ��/����
%>
<%
  String tSerialNo = request.getParameter("SerialNo");
  loggerDebug("BankSerDetailInput","SerialNo:"+tSerialNo);
%> 
<script>
	var tSerialNo = "<%=tSerialNo%>";
</script>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="BankSerDetailInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="BankSerDetailInit.jsp"%>
  <title>���з���������ϸ��ѯ </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit">
   <div class=maxbox>
   <table class="common">
    <tr class="common">
      <td class="title5">
        ���κ�
      </td>
      <td class="input5">
        <input class="common wid" name=SerialNo id=SerialNo readonly=true>
      </td>
      <td  class= title5>
         ���д���
       </td>
       <td  class= input5>
         <Input class=codeno name=BankCode id=BankCode readonly style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('bank',[this,BankName],[0,1]);"  onkeyup="return showCodeListKey('bank',[this,BankName],[0,1]);"><input class=codename name=BankName id=BankName readonly=true>
       </td>
    </tr>
    <tr class="common">
      <td class="title5">
        �ܱ���
      </td>
      <td class="input5">
        <input class="common wid" name=TotalNum id=TotalNum readonly=true>
      </td>
      <td  class= title5>
         �ܽ��
       </td>
       <td  class= input5>
         <Input class="wid common" name=TotalMoney id=TotalMoney readonly=true>
       </td>
    </tr>
        <tr class="common">
      <td class="title5">
        ����״̬
      </td>
      <td class="input5">
        <Input class=codeno name=SendBankFileState id=SendBankFileState style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" readonly ondblclick="return showCodeList('sendbankfilestate',[this,SendBankFileStateName],[0,1]);"  onkeyup="return showCodeListKey('bank',[this,SendBankFileStateName],[0,1]);"><input class=codename name=SendBankFileStateName id=SendBankFileStateName readonly=true>
      </td>
	  <TD  class=title5></TD>
	  <TD  class=input5></TD>
    </tr>
   </table>
		<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol);">
				</td>
				<td class= titleImg>��ϸ��Ϣ</td>
			</tr>
		</table>    
		<table  class= common>
			<tr  class= common>
				<td text-align: left colSpan=1>
					<span id="spanPolGrid" ></span> 
				</td>
			</tr>
		</table>

		<input class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
		<input class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
		<input class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
		<input class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">

   <br><br><br><br>
  <INPUT VALUE="��  ��" class= cssButton TYPE=button onclick="parent.close();"> 	       
  </form>
  <br /><br /><br /><br /><br />
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
