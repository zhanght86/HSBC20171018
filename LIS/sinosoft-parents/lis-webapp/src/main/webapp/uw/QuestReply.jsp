<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%> 
<%
//�������ƣ�QuestReply.jsp
//�����ܣ�������ظ�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="QuestReply.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>�˱�������ظ�</title>
  <%@include file="QuestReplyInit.jsp"%>
</head>
<body  onload="initForm('<%=tContNo%>','<%=tFlag%>','<%=tQuest%>');" >
  <form method=post name=fm id=fm target="fraSubmit" action= "./QuestReplyChk.jsp">
    <div class="maxbox1">
    <table>
    	<TR  class= common>
          <TD  class= title5>
            ��������
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=ContNo id=ContNo>
          </TD>
		  <TD  class= title5></TD>
          <TD  class= input5></TD>
        </TR>
    </table>
  <table width="121%" height="37%">
    <TR  class= common> 
      <TD width="100%" height="13%"  class= title> ��������� </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="Content" id=Content cols="135" rows="10" class="common"></textarea></TD>
    </TR>
  </table>
  <table width="121%" height="37%">
    <TR  class= common> 
      <TD width="100%" height="13%"  class= title> ������ظ� </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="ReplyResult" id=ReplyResult cols="135" rows="10" class="common"></textarea></TD>
    </TR>
  </table>
  <p> 
  </div>
    <!--��ȡ��Ϣ-->
    <input type= "hidden" name= "Flag" value="">
    <input type= "hidden" name= "ContNo" value= "">
    <input type= "hidden" name= "Quest" value="">
  </p>
</form>
  
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
<input type= "button" class="cssButton" name= "sure" value="ȷ��" onClick="submitForm()">
</body>
</html>
