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
  <SCRIPT src="RnewQuestQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="RnewQuestQueryInit.jsp"%>
  
</head>
<body  onload="initForm('<%=tContNo%>','<%=tFlag%>','<%=tMissionID%>','<%=tSubMissionID%>');" >
  <form method=post name=fm id="fm" target="fraSubmit" action="./RnewQuestQueryChk.jsp">
    
    <table class=common >
    	<TR  class= common>
          <!--TD  class= title>
            ��ͬͶ��������
          </TD-->
          <TD  class= input>
            <Input type="hidden" class=common name=ContNo id="ContNo">
          </TD>
          <!--TD  class= title>
            ���ض���
          </TD-->
          <TD  class= input>
            <Input type="hidden" class=code name=BackObj id="BackObj" ondblclick= "showCodeListEx('BackObj',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('BackObj',[this,''],[0,1]);" >
          </TD>
          <!--TD  class= title>
            ����λ��
          </TD-->
          <TD  class= input>
            <Input type="hidden" class=code name=OperatePos id="OperatePos" ondblclick= "showCodeListEx('OperatePos',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('OperatePos',[this,''],[0,1]);" >
          </TD>
        </TR>

    	<TR  class= common>
          <!--TD  class= title>  �������  </TD-->
          <TD  class= input>  <Input type="hidden" class=code name=ManageCom id="ManageCom" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">  </TD>
          <!--TD  class= title>   �����ѡ��  </TD-->
          <TD  class= input>  <Input type="hidden" class=code name=Quest id="Quest" ondblclick= "showCodeListEx('Quest',[this,''],[0,1],null,null,null,null,1500);" onkeyup="showCodeListKeyEx('Quest',[this,''],[0,1],null,null,null,null,1500);" >  </TD>
          <!--TD  class= title>  �Ƿ�ظ� </TD-->
          <TD  class= input> <Input type="hidden" class=code name=IfReply id="IfReply" ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);"> </TD>
        </TR>

    	<TR  class= common>       
          <!--TD  class= title >
            ��������ڹ������
          </TD-->
          <TD  class= input >
            <Input type="hidden" class=code name=OManageCom id="OManageCom" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
          </TD>
          <TD  class= input>
          <Input  type= "hidden" class= Common name = SerialNo id="SerialNo">
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
    <Div  id= "divUWSpec1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanQuestGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
    	<input CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      <input CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      <input CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      <input CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
    </div>
    <div id ="divOperation" style = "display : none">
      <div class="maxbox1">
      <table class=common>
       <tr  class= common>
          <td class=title>������</td>
          <td class=common><Input  class= "Common wid " name= 'Operator' id="Operator" readonly></td>
          <td class=title>����ʱ��</td> 
          <td class=common><Input  class= "Common wid " name= 'MakeDate' id="MakeDate" readonly></td>
          <td class=title>�ظ�ʱ��</td><td class=common><Input  class= "Common wid " name= 'ReplyDate' id="ReplyDate" readonly></td>
       </tr>
      </table>
      </div>
    </div>
    <table class= common>
      <TR  class= common> 
        <TD width="14%" class= title> ��������� </TD>
      </TR>
      <tr>
      	 <TD >
      	 &nbsp;&nbsp;&nbsp;&nbsp;<textarea name="Content" id="Content" cols="135" rows="4" class="common" readonly></textarea></TD>
      </tr>
    </table>
  
  <table class= common>
    <TR  class= common> 
      <TD width="14%" class= title> ������ظ� </TD>
    </TR>
    <tr>
    	 <TD >
    	 &nbsp;&nbsp;&nbsp;&nbsp;<textarea name="ReplyResult" id="ReplyResult" cols="135" rows="4" class="common" readOnly></textarea></TD>
    </tr>
  </table>
  
  <p> 
    <!--��ȡ��Ϣ-->
    <input type= "hidden" name= "Flag" id="Flag" value="">
    <input type= "hidden" name= "ContNoHide" id="ContNoHide" value= "">
    <input type= "hidden" name= "QuesFlag" id="QuesFlag" value="">
    <input type= "hidden" name= "Type" id="Type" value="">
    <input type= "hidden" name= "HideOperatePos" id="HideOperatePos" value="">
    <input type= "hidden" name= "HideSerialNo" id="HideSerialNo" value="">
    <input type= "hidden" name= "HideQuest" id="HideQuest" value="">
    <input type= "hidden" name= "MissionID" id="MissionID" value="">
    <input type= "hidden" name= "SubMissionID" id="SubMissionID" value="">
    <input type= "hidden" name= "hiddenOperate" id="hiddenOperate" value="">
  </p>
</form>
<div id = "divButton">
  	<input class=cssButton VALUE=�����Ӱ���ѯ TYPE=button onclick="QuestPicQuery();">
	<input class= cssButton type= "button" name= "sure" value="����" class= Common onClick="reply()">
	<input class= cssButton type= "button" value="����" class= Common onClick="top.close();"> 
</div>
<br>
<div id = "divButtonCenter" style= "display: 'none'">
	<input class=cssButton VALUE=������ظ���� TYPE=button onclick="finishMission();">
</div>
<br>
<div id = "divModiButton">
</div>
<div id = "divUWButton">
	<input class=cssButton VALUE=�����Ӱ���ѯ TYPE=button onclick="QuestPicQuery();">
	<input class= cssButton type= "button" value="����"  onClick="top.close();"> 
</div>
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 

</body>
</html>
