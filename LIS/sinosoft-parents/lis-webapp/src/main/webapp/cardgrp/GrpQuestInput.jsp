<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GrpQuestInput.jsp
//�����ܣ����������¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
String hh = request.getParameter("Flag");
loggerDebug("GrpQuestInput","��־====="+hh);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="GrpQuestInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>����˱������¼��</title>
  <%@include file="GrpQuestInputInit.jsp"%>
  <SCRIPT>var tFlag="<%=request.getParameter("Flag")%>";</SCRIPT>
</head>
<body  onload="initForm('<%=tGrpContNo%>','<%=tFlag%>');" >
  <form method=post name=fm target="fraSubmit" action= "./GrpQuestInputChk.jsp">

    <table class = common>
	        <TD  class= title>
            ���Ͷ���
          </TD>
          <%if(!hh.equals("2") && !hh.equals("9")){%>
          <TD>
          <!--input class="code" name="BackObj" CodeData="0|^1|����Ա ^2|�ͻ� " ondblclick="return showCodeListEx('BackObj', [this])"onkeyup="return showCodeListKeyEx('BackObj', [this])"-->
          <input class="code" name="BackObj" CodeData="0|^1|����Ա" ondblclick="return showCodeListEx('BackObj', [this])"onkeyup="return showCodeListKeyEx('BackObj', [this])">
          </TD>
          <%}else if(hh.equals("2")){%>
          <TD>
          
          <!--input class="code" name="BackObj" CodeData="0|^2|�ͻ�" ondblclick="return showCodeListEx('BackObj', [this])"onkeyup="return showCodeListKeyEx('BackObj', [this])"-->
          <input class="code" name="BackObj" CodeData="0|^1|����Ա" ondblclick="return showCodeListEx('BackObj', [this])"onkeyup="return showCodeListKeyEx('BackObj', [this])">
          
          </TD>
          <%}else if(hh.equals("9")){%>
          <TD>
          
          <input class="code" name="BackObj" CodeData="0|^1|����Ա" ondblclick="return showCodeListEx('BackObj', [this])"onkeyup="return showCodeListKeyEx('BackObj', [this])">
          
          </TD>
          <%}%>
        </table>

       <table class = common>

       <TD  class= title>
    	  �����ѡ��
    	</TD>
        <TD >
            <Input class=code name=Quest ondblclick= "showCodeListEx('GrpQuest',[this,Content],[0,1],null,null,null,null,500);" onkeyup="showCodeListKeyEx('GrpQuest',[this,Content],[0,1],null,null,null,null,500);" >
        </TD>

    </table>


  <table  class=common width="121%" height="37%">
    <TR  class= common>
      <TD width="100%" height="13%"  class= title> ��������� </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="Content" cols="135" rows="10" class="common"></textarea></TD>
    </TR>
  </table>
  <p>
    <!--��ȡ��Ϣ-->
    <input type= "hidden" name= "Flag" value="">
    <input type= "hidden" name= "ProposalNoHide" value= "">
    <input type= "hidden" name= "GrpContNoHide" value= "">
    <input type= "hidden" name= "SerialNoHide" value= "">
  </p>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<input class= cssButton type= "button" name= "sure" value="��  ��" onClick="submitForm()">
<input class= cssButton type= "button" name= "backbutton" value="��  ��" onClick="javascript:top.close();">
<!--input class= cssButton type= "button" name= "modify" value="��  ѯ" onClick="query()"-->
<!--<input class= cssButton type= "button" name= "modify" value="�� ��" onClick="modify()">-->
</body>
</html>
