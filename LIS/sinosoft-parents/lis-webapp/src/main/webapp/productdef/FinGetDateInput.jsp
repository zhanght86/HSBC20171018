<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>    
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2007-11-15 15:39:06
//������  ��sunyu���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="FinGetDay.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>   
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <%@include file="FinGetDayInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();" >    
  <form  method=post name=fm target="fraSubmit">
    <table>
    	<tr> 
    		<td>
    		 <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
    		</td>
    		 <td class= titleImg>
�����ѯ��ʱ�䷶Χ
       		 </td>   		 
    	</tr>
    </table>
    <Div  id= "divFCDay" style= "display: ''">
      <table  class= common>
        <TR  class= common>
          <TD  class= title>
��ʼʱ��
          </TD>
          <TD  class= input>
            <Input class= "multiDatePicker" dateFormat="short" name=StartDay verify="��ʼʱ��|NOTNULL">
          </TD>
          <TD  class= title>
����ʱ��
          </TD>
          <TD  class= input>
            <Input class= "multiDatePicker" dateFormat="short" name=EndDay  verify="����ʱ��|NOTNULL">
          </TD>  
        </TR>
        </table>
    </Div>
    <table>
    	<tr> 
    		<td>
    		 <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divOperator);">
    		</td>
    		 <td class= titleImg>
������ϸ
       		 </td>   		 
    	</tr>
    </table>
    <Div  id= "divOperator" style= "display: ''">    
    <table align=right>
        <TR > 
		<TD >
			<input class= cssButton type=Button name=button1 value="����ҵ�񱣷Ѵ�ӡ" onclick="printPay('finget')">
			<input class= cssButton type=Button name=button2 value="��ԥ���˷Ѵ�ӡ" onclick="printPay('finpay')">
		</TD>
		<TD>
		</TD>
		</TR>    	 
      </table>
      </Div>
    
       <input type=hidden id="fmtransact" name="fmtransact">
    <Div id=DivFileDownload style="display:none;">
      <A id=fileUrl href=""></A>
    </Div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>