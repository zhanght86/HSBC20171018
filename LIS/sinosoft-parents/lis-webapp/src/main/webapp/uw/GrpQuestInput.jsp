<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%> 
<%
//�������ƣ�GrpQuestInput.jsp
//�����ܣ����������¼��
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
  <SCRIPT src="GrpQuestInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>����˱������¼��</title>
  <%@include file="GrpQuestInputInit.jsp"%>
</head>
<body  onload="initForm('<%=tGrpProposalNo%>','<%=tProposalNo%>','<%=tFlag%>');" >
  <form method=post id="fm" name=fm target="fraSubmit" action= "./GrpQuestInputChk.jsp">
    <div class="maxbox1">
    <table>
    	<TR  class= common>
          <TD  class= title5>���ض��� &nbsp;</TD>
          <TD  class= input5>
            <Input class="code wid" id="BackObj" name=BackObj style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  ondblclick= "showCodeListEx('BackObj',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('BackObj',[this,''],[0,1]);" >
          </TD>
        </TR>
    </table>
    <table>
    <tr class="common">
    	<TD class= title5>�����ѡ��</TD>
        <TD  class= input5>
            <Input class="code wid" id="Quest" name=Quest style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  ondblclick= "showCodeListEx('Quest',[this,Content],[0,1],null,null,null,null,1500);" onkeyup="showCodeListKeyEx('Quest',[this,Content],[0,1],null,null,null,null,1500);" >
        </TD>
        </tr>
    </table>
</div>
    <div class="maxbox1">
  <table width="121%" height="37%">
    <TR  class= common> 
      <TD width="100%" height="13%"  class= title> ��������� </TD>
    </TR>
    
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="Content" id=Content cols="135" rows="10" class="common"></textarea></TD>
    </TR>
    
  </table>
  </div>
  <p> 
    <!--��ȡ��Ϣ-->
    <input type= "hidden" id="Flag" name= "Flag" value="">
    <input type= "hidden" id="ProposalNoHide" name= "ProposalNoHide" value= "">
    <input type= "hidden" id="GrpProposalNoHide" name= "GrpProposalNoHide" value= "">
  </p>
</form>
  
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
<input type= "button" class="cssButton" id="sure" name= "sure" value="ȷ��" onClick="submitForm()">
</body>
</html>
