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
  <SCRIPT src="BQQuestQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="BQQuestQueryInit.jsp"%>
  
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit" action="./BQQuestQueryChk.jsp">
    
    <table class=common >
    	<TR  class= common>
          <!--TD  class= title>
            ��ͬͶ��������
          </TD-->
          <TD  class= input>
            <Input type="hidden" class=common id="ContNo" name=ContNo>
          </TD>
          <!--TD  class= title>
            ���ض���
          </TD-->
          <TD  class= input>
            <Input type="hidden" class=code id="BackObj" name=BackObj style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick= "showCodeListEx('BackObj',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('BackObj',[this,''],[0,1]);" >
          </TD>
          <!--TD  class= title>
            ����λ��
          </TD-->
          <TD  class= input>
            <Input type="hidden" class=code id="OperatePos" name=OperatePos style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick= "showCodeListEx('OperatePos',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('OperatePos',[this,''],[0,1]);" >
          </TD>
        </TR>

    	<TR  class= common>
          <!--TD  class= title>  �������  </TD-->
          <TD  class= input>  <Input type="hidden" class=code id="ManageCom" name=ManageCom style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">  </TD>
          <!--TD  class= title>   �����ѡ��  </TD-->
          <TD  class= input>  <Input type="hidden" class=code id="Quest" name=Quest style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick= "showCodeListEx('Quest',[this,''],[0,1],null,null,null,null,1500);" onkeyup="showCodeListKeyEx('Quest',[this,''],[0,1],null,null,null,null,1500);" >  </TD>
          <!--TD  class= title>  �Ƿ�ظ� </TD-->
          <TD  class= input> <Input type="hidden" class=code id="IfReply" name=IfReply style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);"> </TD>
        </TR>

    	<TR  class= common>       
          <!--TD  class= title >
            ��������ڹ������
          </TD-->
          <TD  class= input >
            <Input type="hidden" class=code id="OManageCom" name=OManageCom style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
          </TD>
          <TD  class= input>
          <Input  type= "hidden" class= Common id="SerialNo" name = SerialNo >
          </TD>
          <TR>
          <!--TD  class= title>
            <input type="button" class=cssButton name= "Query" class= Common value="��  ѯ" onClick= "query()">
          </TD>
          </TD-->
      </TR>
    </table>
    
  
    
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
    <Div  id= "divUWSpec1" style= "display: ''" >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanQuestGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
        <center>
    	<input CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      <input CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      <input CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      <input CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"></center>
    </div><br>
      <div id ="divOperation" style = "display : none" class="maxbox1">
    <table class=common>
       <tr  class= common>
          <td class=title>������</td><td class=common><Input class="wid"  class= Common name= 'Operator' id= 'Operator' readonly></td>
          <td class=title>����ʱ��</td><td class=common><Input class="wid"  class= Common name= 'MakeDate' id= 'MakeDate' readonly></td>
       <!--/tr>
       <tr  class= common-->
          <td class=title>�ظ�ʱ��</td><td class=common><Input class="wid" class= Common name= 'ReplyDate' id= 'ReplyDate' readonly></td>
       </tr>
    </table>
    </div>
  <table class= common>
    <TR  class= common> 
      <TD class= title> ��������� </TD>
    </TR>
    <TR  class= common>
      <TD style="padding-left:16px" colspan="6"><textarea name="Content" cols="200" rows="4" class="common" readonly></textarea></TD>
    </TR>
  </table>
  
  <table class= common>
    <TR  class= common> 
      <TD class= title> ������ظ� </TD>
    </TR>
    <TR  class= common>
      <TD style="padding-left:16px" class="common" colspan="6"><textarea name="ReplyResult" cols="200" rows="4" class="common" readonly></textarea></TD>
    </TR>
  </table>
  <div id = "divErrorSave" style= "display: none">
  <table class= common>
  <tr class="common">
	 <td CLASS="title">�Ƿ������ </td>
			<td CLASS="input">
				<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" NAME="ErrorFlag" id="ErrorFlag"  CLASS="codeno" ondblclick="showCodeList('yesno',[this,ErrorFlagName],[0,1],null,null,null,1);" onclick="showCodeList('yesno',[this,ErrorFlagName],[0,1],null,null,null,1);" onkeyup="showCodeListKey('yesno',[this,ErrorFlagName],[0,1],null,null,null,1);" >
				<input NAME="ErrorFlagName" id="ErrorFlagName" elementtype=nacessary CLASS="codename" readonly>
			</td>
            <td CLASS="title"></td>
            <td CLASS="input"></td>
            <td CLASS="title"></td>
            <td CLASS="input"></td></tr>	
  </table>
</div>
  <p> 
    <!--��ȡ��Ϣ-->
    <input type= "hidden" id="Flag" name= "Flag" value="">
    <input type= "hidden" id="EdorNo" name= "EdorNo" value="">
    <input type= "hidden" id="ContNoHide" name= "ContNoHide" value= "">
    <input type= "hidden" id="QuesFlag" name= "QuesFlag" value="">
    <input type= "hidden" id="Type" name= "Type" value="">
    <input type= "hidden" id="HideOperatePos" name= "HideOperatePos" value="">
    <input type= "hidden" id="HideSerialNo" name= "HideSerialNo" value="">
    <input type= "hidden" id="HideQuest" name= "HideQuest" value="">
    <input type= "hidden" id="MissionID" name= "MissionID" value="">
    <input type= "hidden" id="SubMissionID" name= "SubMissionID" value="">
    <input type= "hidden" id="ActivityID" name= "ActivityID" value="">
    <input type= "hidden" id="hiddenOperate" name= "hiddenOperate" value="">
  </p>
</form>
<div id = "divButton">
	<input class=cssButton VALUE=�����Ӱ���ѯ TYPE=button onclick="QuestPicQuery();">
	<input class= cssButton type= "button" name= "sure" value="����" class= Common onClick="reply()">
	<input class= cssButton type= "button" value="����" class= Common onClick="top.close();">
  
  <!--input class= cssButton type= "button" name= "sure" value="�ظ����" class= Common onClick="replySave()"-->
</div><br>
<hr class="line"><br>
<div id = "divButtonCenter" style= "display: none">
	<input class=cssButton VALUE=������ظ���� TYPE=button onclick="finishMission();">
  <!--input class= cssButton type= "button" name= "sure" value="�ظ����" class= Common onClick="replySave()"-->
</div>
<div id = "divModiButton">
	<!--input type= "button" name= "sure" value="�޸��������ӡ���" class= cssButton onClick="IfPrint()"-->
</div>

<div id = "divUWButton">
	<input class=cssButton VALUE=�����Ӱ���ѯ TYPE=button onclick="QuestPicQuery();">
	<input class= cssButton type= "button" value="����" class= Common onClick="top.close();">
  
  <!--input class= cssButton type= "button" name= "sure" value="�ظ����" class= Common onClick="replySave()"-->
</div>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> <br><br><br><br>

</body>
</html>
