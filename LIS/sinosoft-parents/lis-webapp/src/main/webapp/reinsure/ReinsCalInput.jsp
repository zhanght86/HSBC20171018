<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="ReinsCalInput.js"></SCRIPT>
  <title>�ٱ�����</title>
</head>
<body>
  <form method=post name=fm action= "./ReinsCalSave.jsp" target="fraSubmit">
    <!-- ������Ϣ���� -->
    <table class= common border=0 width=100%>
    	<tr>
			<td class= titleImg align= center>����¼�����ڣ�</td>
		</tr>
	</table>
    <table  class= common align=center>
      	
      	<TR  class= common>
          <TD  class= title5>
��ʼ����
          </TD>
          <TD  class= input5>
            <Input class="coolDatePicker" onClick="laydate({elem: '#Bdate'});" dateFormat="short" name=Bdate id="Bdate">		<span class="icon"><a onClick="laydate({elem: '#Bdate'});">		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
          <TD  class= title5>
��ֹ����
          </TD>
          <TD  class= input5>
            <Input class="coolDatePicker" onClick="laydate({elem: '#Edate'});" dateFormat="short" name=Edate id="Edate">		<span class="icon"><a onClick="laydate({elem: '#Edate'});">		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
          </TD>
        </TR>

	<TR  class= common>
          <TD>
          <INPUT  class=common VALUE="�����ֱ�������ȡ" TYPE=button onclick="SubmitForm1();">
          </TD>
          <TD>
          <INPUT  class=common VALUE="��ҵ�ֱ�������ȡ" TYPE=button onclick="SubmitForm();">
          </TD>
           <input type=hidden name="Opt">
        </TR>
        <TR  class= common>
          <TD>
          <INPUT  class=common VALUE="��ȫ������ȡ" TYPE=button onclick="SubmitForm2();">
          </TD>
          <TD>
          <INPUT  class=common VALUE="����������ȡ" TYPE=button onclick="SubmitForm3();">
          </TD>
           
        </TR>
    </table>


      </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
