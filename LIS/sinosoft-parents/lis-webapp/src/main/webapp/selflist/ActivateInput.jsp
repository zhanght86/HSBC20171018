<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
	<%
//�������ƣ�ActivateInput.jsp
//�����ܣ����������
//�������ڣ�2008-03-05 
//������  ������
//���¼�¼��  ������    ��������     ����ԭ��/����
  GlobalInput tG = (GlobalInput)session.getValue("GI");
  String Branch =tG.ComCode;
  %>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="ActivateInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ActivateInit.jsp"%>
</head>
<body onload="initForm()">
  <form action="./ActivateSave.jsp" method=post name=fm id="fm" target="fraSubmit">
  	<Table class= common>
	     <tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
        <td class="titleImg">������������</td>
	     </tr>
	  </Table>
    <div class="maxbox1">
    <Div  id= "divFCDay" style= "display: ''">
   	<Table class= common>
   		<TR class= common>
   			<TD class= title5>����</TD>
   			<TD class= input5><Input class= "common wid" name= CertifyNo id="CertifyNo"></TD>

        <TD class= title5>����</TD>
        <TD class= input5><Input class= "common wid" type=password name= Password id="Password"></TD>
   		</TR>
    </Table>
  </Div>
  </div>
<a href="javascript:void(0)" name="ActivateOper" class=button onclick="submitForm();">��  ��</a>
<!-- <input name="ActivateOper" class="cssButton" type="button" value="��  ��" onclick="submitForm()"> -->
</form>
 <br> 
 <br> 
 <br> 
 <br> 
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>
</html>
