<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2009-10-13 9:42:43
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
  <SCRIPT src="LDExOtherRateInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="LDExOtherRateInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();" >
  <form action="./LDExOtherRateSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <%@include file="../common/jsp/InputButton.jsp"%>
    <table>
      <tr class=common>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divOLDCode1);">
          </IMG>
        </td>
        <td class=titleImg>
          ��Ҫ�����ֶ���Ԫ������ά��
        </td>
    </tr>
    </table>
    <Div  id= "divOLDCode1" style= "display: ''" class="maxbox">
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
            Ŀ����ִ���
          </TD>
          <TD  class= input5>
          <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class='codeno' name=DestCurrCode id=DestCurrCode ondblclick="return showCodeList('currcode',[this,DestCodeName],[0,1]);"  onclick="return showCodeList('currcode',[this,DestCodeName],[0,1]);" 
            onkeyup="return showCodeListKey('currcode',[this,DestCodeName],[0,1]);"  verify="Ŀ����ִ���|notnull&currcode"><input name=DestCodeName id=DestCodeName  class=codename elementtype=nacessary  readonly=true>
          </TD>
          <TD  class= title5>
            ����Ԫ��������
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=ExchRate id=ExchRate verify="����Ԫ��������|notnull" elementtype=nacessary>
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            ��������
          </TD>
          <TD  class= input5>
            <!--<Input class="coolDatePicker" dateFormat="short" name=StartDate verify="��������|notnull&date" elementtype=nacessary>-->
             <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��������|notnull&date" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>
            ͣ������
          </TD>
          <TD  class= input5>
            <!--<Input class="coolDatePicker" dateFormat="short" name=EndDate verify="��������|date">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="��������|date" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
        </TR>
      </table>
    </Div>
    <input type=hidden name=hideOperate value=''>
    <br>
    <INPUT class=cssButton name="addbutton" VALUE="��  ��"  TYPE=button onclick="return addClick();">
    <!--<a href="javascript:void(0);" name="addbutton" class="button" onClick="return addClick();">��    ��</a>-->
    <INPUT class=cssButton name="updatebutton" VALUE="��  ��"  TYPE=button onclick="return updateClick();">
    <!--<a href="javascript:void(0);" name="updatebutton" class="button" onClick="return updateClick();">��    ��</a>-->
    <INPUT class=cssButton name="querybutton" VALUE="��  ѯ"  TYPE=button onclick="return queryClick();">
    <!--<a href="javascript:void(0);" name="querybutton" class="button" onClick="return queryClick();">��    ѯ</a>-->
    <INPUT class=cssButton name="deletebutton" VALUE="ɾ  ��"  TYPE=button onclick="return deleteClick();">
    <!--<a href="javascript:void(0);" name="deletebutton" class="button" onClick="return deleteClick();">ɾ    ��</a>-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
