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
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="LDExRateInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="LDExRateInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();" >
  <form action="./LDExRateSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <%@include file="../common/jsp/InputButton.jsp"%>
    
    <table>
      <tr class=common>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divOLDCode1);">
          </IMG>
        </td>
        <td class=titleImg>
        	��������Ƽ�ά��
        </td>
    </tr>
    </table>
    <Div  id= "divOLDCode1" style= "display: ''" class="maxbox">
      <table  class= common>
        <TR  class= common>    
        	<TD  class= title5>                                                                                                                                                                                                                                               
            ��ұ���
          </TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class='codeno' name=CurrCode id=CurrCode ondblclick="return showCodeList('currcode',[this,CurrCodeName],[0,1]);" 
            onclick="return showCodeList('currcode',[this,CurrCodeName],[0,1]);" 
            onkeyup="return showCodeListKey('currcode',[this,CurrCodeName],[0,1]);"  verify="��ұ��ִ���|notnull&currcode"><input name=CurrCodeName id=CurrCodeName  class=codename elementtype=nacessary  readonly=true>
          </TD>
          <TD  class= title5>
            ������λ
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=Per id=Per verify="������λ|notnull&num&len<=10" elementtype=nacessary  >
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
            �ֻ������
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=ExchBid id=ExchBid verify="�ֻ������|num" >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            �ֻ�������
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=ExchOffer id=ExchOffer verify="�ֻ�������|num" >
          </TD>
          <TD  class= title5>
            �ֳ������
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=CashBid id=CashBid verify="�ֳ������|num" >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            �ֳ�������
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=CashOffer id=CashOffer verify="�ֳ�������|num" >
          </TD>
          <TD  class= title5>
            �м��
          </TD>
          <TD  class= input5>
           <Input class="wid" class= common name=Middle id=Middle verify="�м��|num" >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            ��������
          </TD>
          <TD  class= input5>
            <!--<Input class="coolDatePicker" dateFormat="short" name=MakeDate verify="��������|notnull&date" elementtype=nacessary>-->
             <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=MakeDate id="MakeDate"><span class="icon"><a onClick="laydate({elem: '#MakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>
            ����ʱ��
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=MakeTime id=MakeTime verify="����ʱ��|notnull&len=8" elementtype=nacessary>�����磺19:39:20 ��
          </TD>
        </TR>
      </table></Div>

						<!--<INPUT class=cssButton name="deletebutton" VALUE="ת  ��"  TYPE=button onclick="return deleteClick();">-->
                        <a href="javascript:void(0);" name="deletebutton" class="button" onClick="return deleteClick();">ת    ��</a><br><br>
					
            <div class="maxbox1">
			<table  class=common>
				<TR  class= common>
          <TD  class= title5>
            ͣ������
          </TD>
          <TD  class= input5>
            <!--<Input class="coolDatePicker" dateFormat="short" name=EndDate verify="��������|date">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>
            ͣ��ʱ��
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=EndTime id=EndTime verify="ͣ��ʱ��|len=8">�����磺19:39:20 ��
          </TD>
        </TR>
			</table></div>

						<!--<INPUT class=cssButton name="querybutton" VALUE="�����ʷ��ѯ"  TYPE=button onclick="return queryClick2();">-->
                        <a href="javascript:void(0);" name="querybutton" class="button" onClick="return queryClick2();">�����ʷ��ѯ</a><br><br>
				
    <input type=hidden name=hideOperate value=''>
    <hr class="line">
    <br>

				<!--<INPUT class=cssButton name="addbutton" VALUE="��  ��"  TYPE=button onclick="return addClick();">
		
				<INPUT class=cssButton name="updatebutton" VALUE="��  ��"  TYPE=button onclick="return updateClick();">
		
				<INPUT class=cssButton name="querybutton" VALUE="��  ѯ"  TYPE=button onclick="return queryClick();">-->
                
                <a href="javascript:void(0);" name="addbutton" class="button" onClick="return addClick();">��    ��</a>
                <a href="javascript:void(0);" name="updatebutton" class="button" onClick="return updateClick();">��    ��</a>
                <a href="javascript:void(0);" name="querybutton" class="button" onClick="return queryClick();">��    ѯ</a>
		
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
