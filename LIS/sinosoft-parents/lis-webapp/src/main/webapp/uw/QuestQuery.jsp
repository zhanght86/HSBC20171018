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
	String tMasterCenter = (String)request.getParameter("MasterCenter");
	if(tMasterCenter==null||tMasterCenter.equals("")||tMasterCenter.equals("null"))
	{
		 tMasterCenter = "Y";
	}
	
%>
<script>
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var mMasterCenter = "<%=tMasterCenter%>"; //��¼��½����
	
	
</script>
<head >
<title>�������ѯ </title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="QuestQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="QuestQueryInit.jsp"%>
  <script src="../common/javascript/MultiCom.js"></script>
  
</head>
<body  onload="initForm('<%=tContNo%>','<%=tFlag%>','<%=tMissionID%>','<%=tSubMissionID%>');" >
  <form method=post name=fm id="fm" target="fraSubmit" action="./QuestQueryChk.jsp">
    
    <table class=common >
    	<TR  class= common>
          <!--TD  class= title>
            ��ͬͶ��������
          </TD-->
          <TD  class= input>
            <Input type="hidden" class="common wid" name=ContNo id="ContNo">
          </TD>
          <!--TD  class= title>
            ���ض���
          </TD-->
          <TD  class= input>
            <Input type="hidden" class=code name=BackObj id="BackObj" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;" onClick="showCodeListEx('BackObj',[this,''],[0,1]);" ondblclick= "showCodeListEx('BackObj',[this,''],[0,1]);" onKeyUp="showCodeListKeyEx('BackObj',[this,''],[0,1]);" >
          </TD>
          <!--TD  class= title>
            ����λ��
          </TD-->
          <TD  class= input>
            <Input type="hidden" class=code name=OperatePos id="OperatePos" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;" onClick="showCodeListEx('OperatePos',[this,''],[0,1]);" ondblclick= "showCodeListEx('OperatePos',[this,''],[0,1]);" onKeyUp="showCodeListKeyEx('OperatePos',[this,''],[0,1]);" >
          </TD>
        </TR>

    	<TR  class= common>
          <!--TD  class= title>  �������  </TD-->
          <TD  class= input>  <Input type="hidden" class=code name=ManageCom id="ManageCom" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;" onClick="return showCodeList('station',[this]);" onDblClick="return showCodeList('station',[this]);" onKeyUp="return showCodeListKey('station',[this]);">  </TD>
          <!--TD  class= title>   �����ѡ��  </TD-->
          <TD  class= input>  <Input type="hidden" class=code name=Quest id="Quest" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;" onClick="showCodeListEx('Quest',[this,''],[0,1],null,null,null,null,1500);" ondblclick= "showCodeListEx('Quest',[this,''],[0,1],null,null,null,null,1500);" onKeyUp="showCodeListKeyEx('Quest',[this,''],[0,1],null,null,null,null,1500);" >  </TD>
          <!--TD  class= title>  �Ƿ�ظ� </TD-->
          <TD  class= input> <Input type="hidden" class=code name=IfReply id="IfReply" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;" onClick="return showCodeList('YesNo',[this]);"  ondblclick="return showCodeList('YesNo',[this]);" onKeyUp="return showCodeListKey('YesNo',[this]);"> </TD>
        </TR>

    	<TR  class= common>       
          <!--TD  class= title >
            ��������ڹ������
          </TD-->
          <TD  class= input >
            <Input type="hidden" class=code name=OManageCom id="OManageCom" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;" onClick="return showCodeList('station',[this]);" onDblClick="return showCodeList('station',[this]);" onKeyUp="return showCodeListKey('station',[this]);">
          </TD>
          <TD  class= input>
          <Input  type= "hidden" class= "common wid" name = SerialNo id="SerialNo" >
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
    <Div  id= "divUWSpec1" style= "display: " align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanQuestGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
      <center>
      <input CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onClick="turnPage1.firstPage();"> 
      <input CLASS=cssButton91 VALUE="��һҳ" TYPE=button onClick="turnPage1.previousPage();"> 					
      <input CLASS=cssButton92 VALUE="��һҳ" TYPE=button onClick="turnPage1.nextPage();"> 
      <input CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onClick="turnPage1.lastPage();">
     </center>
    </div>
      <div id ="divOperation" style = "display : none">
    <table class=common>
       <tr  class= common>
          <td class=title>������</td><td class=common><Input  class= "common wid" name= 'Operator' id="Operator" readonly></td>
          <td class=title>����ʱ��</td><td class=common><Input  class= multiDatePicker name= 'MakeDate' id="MakeDate" readonly></td>
       <!--/tr>
       <tr  class= common-->
          <td class=title>�ظ�ʱ��</td><td class=common><Input  class= multiDatePicker name= 'ReplyDate' id="ReplyDate" readonly></td>
       </tr>
    </table>
    </div>
    <div class="maxbox">
  <table width="80%" height="25%" class= common>
    <TR  class= common> 
      <TD width="100%" height="13%"  class= title> ��������� </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="Content" class="common" id="Content" cols="135" rows="8"  readonly></textarea></TD>
    </TR>
  </table>
  
  <table width="80%" height="25%" class= common>
    <TR  class= common> 
      <TD width="100%" height="13%"  class= title> ������ظ� </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="ReplyResult" id="ReplyResult" class="common" cols="135" rows="8" ></textarea></TD>
    </TR>
  </table>
  </div>
  <div id = "divErrorSave" style= "display: none">
  <table class= common>
  	<tr class="common">
		<td CLASS="title">�Ƿ������ </td>
		<td CLASS="input">
			<input NAME="ErrorFlag" id="ErrorFlag"  CLASS="codeno" style='background: url("../common/images/select--bg_03.png") no-repeat right;' 
			onClick="showCodeList('yesno',[this,ErrorFlagName],[0,1],null,null,null,1);" 
			onDblClick="showCodeList('yesno',[this,ErrorFlagName],[0,1],null,null,null,1);" 
			onKeyUp="showCodeListKey('yesno',[this,ErrorFlagName],[0,1],null,null,null,1);" >
			<input NAME="ErrorFlagName" id="ErrorFlagName" elementtype=nacessary CLASS="codename" readonly>
		</td>
		<td CLASS="title"></td>
        <td CLASS="input"></td>
		<td CLASS="title"></td>
        <td CLASS="input"></td>
	</tr>
  </table>
  <input type= "hidden" name= "ErrorFlagAction" id="ErrorFlagAction" value="">
  <input class=cssButton VALUE=������ TYPE=button onClick="UpdateErrorFlag();">
</div>
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
    <input type= "hidden" name= "hiddenOperate" value="">
    <input type= "hidden" name= "ActivityID" value="">
  </p>
</form>
<div id = "divButton">  
	<input class=cssButton VALUE=�����Ӱ���ѯ TYPE=button onClick="QuestPicQuery();">
	<input class= cssButton type= "button" name= "sure" value="����"  onClick="reply()">
	<!-- modified by liuyuxiao 2011-05-24 ��ȥ���أ���tab������ -->
	<input class= cssButton type= "button" value="����"  onClick="top.close();" style= "display: none">
  
  <!--input class= cssButton type= "button" name= "sure" value="�ظ����" class= Common onClick="replySave()"-->
</div>
<div id = "divButtonCenter" style= "display: none">
	<input class=cssButton VALUE=������ظ���� TYPE=button onClick="finishMission();">
  <!--input class= cssButton type= "button" name= "sure" value="�ظ����" class= Common onClick="replySave()"-->
</div>
<div id = "divModiButton">
	<!--input type= "button" name= "sure" value="�޸��������ӡ���" class= cssButton onClick="IfPrint()"-->
</div>

<div id = "divUWButton">
	<input class=cssButton VALUE=�����Ӱ���ѯ TYPE=button onClick="QuestPicQuery();">
	<!-- modified by liuyuxiao 2011-05-24 ��ȥ���أ���tab������ -->
	<input class= cssButton type= "button" value="����"  onClick="top.close();" style= "display: none">
  
  <!--input class= cssButton type= "button" name= "sure" value="�ظ����" class= Common onClick="replySave()"-->
</div>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 

</body>
</html>
