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
<title>�ͻ��ϲ���ѯ </title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="CustCombintQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="CustCombintQueryInit.jsp"%>
  
</head>
<body  onload="initForm('<%=tContNo%>','<%=tFlag%>','<%=tMissionID%>','<%=tSubMissionID%>');" >
  <form method=post name=fm id=fm target="fraSubmit" action="./QuestQueryChk.jsp">
    
	<table class=common >
    	<TR  class= common>
         
          <TD  class= input>
            <Input type= "hidden" class="common wid" name=ContNo id=ContNo>
          </TD>
          
      </TR>
    </table>

    
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);">
    		</td>
    		<td class= titleImg>
    			 �ͻ��ϲ�֪ͨ���б�
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
      <div id ="divOperation" style = "display : ''" class=maxbox1>
    <table class=common>
       <tr  class= common>
          <td class=title>������</td><td class=common><Input  class= "common wid" name= 'Operator' id=Operator readonly></td>
          <td class=title>����ʱ��</td><td class=common><Input  class= "common wid" name= 'MakeDate' id=MakeDate readonly></td>
       <!--/tr>
       <tr  class= common-->
          <td class=title>�ظ�ʱ��</td><td class=common><Input  class= "common wid" name= 'ReplyDate' id=ReplyDate readonly></td>
       </tr>
    </table>
    </div>
  <table width="80%" height="25%" class= common>
    <TR  class= common> 
      <TD width="100%" height="13%"  class= title> �ͻ��ϲ�֪ͨ������ </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="Content" id=Content cols="135" rows="8" class="common wid" readonly></textarea></TD>
    </TR>
  </table>
  
  <table width="80%" height="25%" class= common>
    <TR  class= common> 
      <TD width="100%" height="13%"  class= title> �ͻ��ϲ�֪ͨ��ظ� </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="ReplyResult" id=ReplyResult cols="135" rows="8" class="common wid" readonly></textarea></TD>
    </TR>
  </table>
  
  <p> 
    <!--��ȡ��Ϣ-->
    <input type= "hidden" id=Flag name= "Flag" value="">
    <input type= "hidden" id=ContNoHide name= "ContNoHide" value= "">
    <input type= "hidden" id=QuesFlag name= "QuesFlag" value="">
    <input type= "hidden" id=Type name= "Type" value="">
    <input type= "hidden" id=HideOperatePos name= "HideOperatePos" value="">
    <input type= "hidden" id=HideSerialNo name= "HideSerialNo" value="">
    <input type= "hidden" id=HideQuest name= "HideQuest" value="">
    <input type= "hidden" id=MissionID name= "MissionID" value="">
    <input type= "hidden" id=SubMissionID name= "SubMissionID" value="">
  </p>
</form>
<div id = "divButton">
	<input class= cssButton type= "button" name= "sure" value="�ظ�����" class= Common onClick="reply()">
	<input class= cssButton type= "button" value="����" class= Common onClick="top.close();">
  <input class=cssButton VALUE=�����Ӱ���ѯ TYPE=button onclick="QuestPicQuery();">
  <!--input class= cssButton type= "button" name= "sure" value="�ظ����" class= Common onClick="replySave()"-->
</div>
<div id = "divModiButton">
	<!--input type= "button" name= "sure" value="�޸��������ӡ���" class= cssButton onClick="IfPrint()"-->
</div>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
<br/><br/><br/><br/>
</body>
</html>
