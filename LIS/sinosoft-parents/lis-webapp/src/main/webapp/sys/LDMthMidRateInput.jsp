<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2009-10-13 15:15:43
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
  <SCRIPT src="LDMthMidRateInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="LDMthMidRateInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();" >
  <form action="./LDMthMidRateSave.jsp" method=post name=fm id=fm target="fraSubmit">
    
    <%@include file="../common/jsp/InputButton.jsp"%>
    <table>
      <tr class=common>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divOLDCode1);">
          </IMG>
        </td>
        <td class=titleImg>
          ��ƽ������ά��
        </td>
    </tr>
    </table>
    <Div   id= "divOLDCode1" style= "display: ''" class="maxbox">
      <table  class= common>
        <TR  class= common>
        	<TD  class= title5>                                                                                                                                                                                                                                               
            �����ִ���
          </TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class='codeno' name=CurrCode id=CurrCode ondblclick="return showCodeList('currcode',[this,CurrCodeName],[0,1]);" 
            onclick="return showCodeList('currcode',[this,CurrCodeName],[0,1]);" 
            onkeyup="return showCodeListKey('currcode',[this,CurrCodeName],[0,1]);"  verify="�����ִ���|notnull&currcode"><input name=CurrCodeName id=CurrCodeName  class=codename elementtype=nacessary  readonly=true>
          </TD>
          <TD  class= title5>
            ������λ
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=Per id=Per verify="������λ|notnull&num&len<=10" elementtype=nacessary>
          </TD>
        </TR>
        <TR  class= common>
        	<TD  class= title5>
            ���ұ���
          </TD>
          <TD  class= input5>
          <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class='codeno' name=DestCode id=DestCode ondblclick="return showCodeList('currcode',[this,DestCodeName],[0,1]);" 
           onclick="return showCodeList('currcode',[this,DestCodeName],[0,1]);" 
            onkeyup="return showCodeListKey('currcode',[this,DestCodeName],[0,1]);"  verify="���ұ��ִ���|notnull&currcode"><input name=DestCodeName id=DestCodeName  class=codename elementtype=nacessary  readonly=true>
          </TD>
          <TD  class= title5>
            ��ƽ���м��
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=Average id=Average  verify="��ƽ���м��|notnull&num" elementtype=nacessary>
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            �������
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=ValidYear id=ValidYear verify="�������|notnull&len=4" elementtype=nacessary>(��:yyyy)
          </TD>
          <TD  class= title5>
            �����·�
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=ValidMonth id=ValidMonth  verify="�����·�|notnull&len<=2" elementtype=nacessary>(��:mm)
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
			
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
