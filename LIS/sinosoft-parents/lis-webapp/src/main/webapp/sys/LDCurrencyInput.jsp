<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2009-10-12 19:26:43
//������  ��zhanpeng���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="LDCurrencyInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="LDCurrencyInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();" >
  <form action="./LDCurrencySave.jsp" method=post name=fm id=fm target="fraSubmit">
   
    <%@include file="../common/jsp/InputButton.jsp"%>
    <table>
      <tr class=common>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divOLDCode1);">
          </IMG>
        </td>
        <td class=titleImg>
          ������ά��
        </td>
    </tr>
    </table>
    <Div  id= "divOLDCode1" style= "display: ''" class="maxbox">
      <table  class= common>
        <TR  class= common>
          <TD  class= title5>
            ���ִ���
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=CurrCode id=CurrCode verify="���ִ���|notnull&len<=3" elementtype=nacessary>
          </TD>
          <TD  class= title5>
            ��������
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=CurrName id=CurrName verify="��������|notnull&len<=50" elementtype=nacessary>
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            ��Ч��־
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=Validation id=Validation verify="��Ч��־|notnull&len<=1" elementtype=nacessary>
          </TD>
          <TD  class= title5>
            �������
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=CodeAlias id=CodeAlias >
          </TD>
        </TR>
      </table>
    </Div>
    <input type=hidden name=hideOperate value=''>
    <br>

				<!--<INPUT class=cssButton name="addbutton" VALUE="��  ��"  TYPE=button onclick="return addClick();">
			
				<INPUT class=cssButton name="updatebutton" VALUE="��  ��"  TYPE=button onclick="return updateClick();">
			
				<INPUT class=cssButton name="querybutton" VALUE="��  ѯ"  TYPE=button onclick="return queryClick();">
			
				<INPUT class=cssButton name="deletebutton" VALUE="ɾ  ��"  TYPE=button onclick="return deleteClick();">-->
                <a href="javascript:void(0);" name="addbutton" class="button" onClick="return addClick();">��    ��</a>
    <!--<INPUT class=cssButton name="updatebutton" VALUE="��  ��"  TYPE=button onclick="return updateClick();">-->
    <a href="javascript:void(0);" name="updatebutton" class="button" onClick="return updateClick();">��    ��</a>
    <!--<INPUT class=cssButton name="querybutton" VALUE="��  ѯ"  TYPE=button onclick="return queryClick();">-->
    <a href="javascript:void(0);" name="querybutton" class="button" onClick="return queryClick();">��    ѯ</a>
    <!--<INPUT class=cssButton name="deletebutton" VALUE="ɾ  ��"  TYPE=button onclick="return deleteClick();">-->
    <a href="javascript:void(0);" name="deletebutton" class="button" onClick="return deleteClick();">ɾ    ��</a>
			
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
