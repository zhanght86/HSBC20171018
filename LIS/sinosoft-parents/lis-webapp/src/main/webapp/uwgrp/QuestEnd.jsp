<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�QuestQuery.jsp
//�����ܣ��������ѯ
//�������ڣ�2002-09-24 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<%
  //�����¸���
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
</script>
<head >
<title>�������ѯ </title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="QuestEnd.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="QuestEndInit.jsp"%>
  
</head>
<body  onload="initForm('<%=tContNo%>','<%=tFlag%>','<%=tMissionID%>','<%=tSubMissionID%>');" >
  <form method=post name=fm target="fraSubmit" action="./QuestQueryChk.jsp">
    <table class=common >
    	<TR  class= common>
          <TD  class= title>
            ��ͬͶ��������
          </TD>
          <TD  class= input>
            <Input class=common name=ContNo>
          </TD>
          <TD  class= title>
            ���ض���
          </TD>
          <TD  class= input>
            <Input class=code name=BackObj ondblclick= "showCodeListEx('BackObj',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('BackObj',[this,''],[0,1]);" >
          </TD>
          <TD  class= title>
            ����λ��
          </TD>
          <TD  class= input>
            <Input class=code name=OperatePos ondblclick= "showCodeListEx('OperatePos',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('OperatePos',[this,''],[0,1]);" >
          </TD>
        </TR>

    	<TR  class= common>
          <TD  class= title>  �������  </TD>
          <TD  class= input>  <Input class=code name=ManageCom ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">  </TD>
          <TD  class= title>   �����ѡ��  </TD>
          <TD  class= input>  <Input class=code name=Quest ondblclick= "showCodeListEx('Quest',[this,''],[0,1],null,null,null,null,1500);" onkeyup="showCodeListKeyEx('Quest',[this,''],[0,1],null,null,null,null,1500);" >  </TD>
          <TD  class= title>  �Ƿ�ظ� </TD>
          <TD  class= input> <Input class=code name=IfReply value="N" ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);"> </TD>
        </TR>

    	<TR  class= common>       
          <TD  class= title >
            ��������ڹ������
          </TD>
          <TD  class= input >
            <Input class=code name=OManageCom ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
          </TD>
          <TD  class= input>
          <Input  type= "hidden" class= Common name = SerialNo >
          </TD>
          <TR>

      </TR>
    </table>
    <Input type=hidden class="readonly" name=IfReply>
    
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);">
    		</td>
    		<td class= titleImg>
    			 ������б�
    		</td>
    	</tr>
    </table>
    <Div  id= "divUWSpec1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanQuestGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
    </div>
      
  <table width="80%" height="25%" class= common>
    <TR  class= common> 
      <TD width="100%" height="13%"  class= title> ��������� </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="Content" cols="135" rows="8" class="common" readonly></textarea></TD>
    </TR>
  </table>
  
  <table width="80%" height="25%" class= common>
    <TR  class= common> 
      <TD width="100%" height="13%"  class= title> ������ظ� </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="ReplyResult" cols="135" rows="8" class="common" readonly></textarea></TD>
    </TR>
  </table>
  
  <p> 
    <!--��ȡ��Ϣ-->
    <input type= "hidden" name= "Flag" value="">
    <input type= "hidden" name= "ContNoHide" value= "">
    <input type= "hidden" name= "QuesFlag" value="">
    <input type= "hidden" name= "Type" value="">
    <input type= "hidden" name= "HideOperatePos" value="">
    <input type= "hidden" name= "HideSerialNo" value="">
    <input type= "hidden" name= "HideQuest" value="">
    <input type= "hidden" name= "MissionID" value="">
    <input type= "hidden" name= "SubMissionID" value="">
  </p>
</form>
<div id = "divButton">
	<input class= cssButton type= "button" name= "sure" value="�ظ�����" class= Common onClick="reply()">
  <input class= cssButton type= "button" name= "sure" value="�ظ����" class= Common onClick="replySave()">
</div>
<div id = "divModiButton">
	<input type= "button" name= "sure" value="������ظ�" class= cssButton onClick="IfPrint()">
</div>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 

</body>
</html>
