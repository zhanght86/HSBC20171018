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
  <SCRIPT src="GrpQuestQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GrpQuestQueryInit.jsp"%>
  
</head>
<body  onload="initForm('<%=tGrpContNo%>','<%=tFlag%>');" >
  <form method=post name=fm id="fm" target="fraSubmit" action="./GrpQuestQueryChk.jsp">
    <div class="maxbox1">
    <table class=common >
    	<TR  class= common>
          <TD  class= title>
            Ͷ��������
          </TD>
          <TD  class= input>
            <Input class="common wid" name=GrpContNo id="GrpContNo">
          </TD>
          <TD  class= title>
            ���ض���
          </TD>
          <TD  class= input>
          <input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name="BackObj" id="BackObj" CodeData="0|^1|����Ա ^2|�ͻ� " onclick="return showCodeListEx('BackObj', [this],null,null,null,null,'1')" ondblclick="return showCodeListEx('BackObj', [this],null,null,null,null,'1')" onkeyup="return showCodeListKeyEx('BackObj', [this],null,null,null,null,'1')">
            <!--Input class=code name=BackObj ondblclick= "showCodeListEx('BackObj',[this,''],[0,1],'','','',true);" onkeyup="showCodeListKeyEx('BackObj',[this,''],[0,1],'','','',true);" -->
          </TD>
          <TD  class= title>
            ����λ��
          </TD>
          <TD  class= input>
            <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" type= "hidden" class=code name=OperatePos id="OperatePos" onclick= "showCodeListEx('OperatePos',[this,''],[0,1],'','','',true);" ondblclick= "showCodeListEx('OperatePos',[this,''],[0,1],'','','',true);" onkeyup="showCodeListKeyEx('OperatePos',[this,''],[0,1],'','','',true);" >
          </TD>
        </TR>

    	<TR  class= common>
          <TD  class= title>  �������  </TD>
          <TD  class= input>  <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class=code name=ManageCom id="ManageCom" onclick="return showCodeList('station',[this]);" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">  </TD>
          <TD  class= title>   �����ѡ��  </TD>
          <TD  class= input>  <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class=code name=Quest id="Quest" onclick= "showCodeListEx('GrpQuest',[this,''],[0,1],null,null,null,null,1500);" ondblclick= "showCodeListEx('GrpQuest',[this,''],[0,1],null,null,null,null,1500);" onkeyup="showCodeListKeyEx('GrpQuest',[this,''],[0,1],null,null,null,null,1500);" >  </TD>
          <TD  class= title>  �Ƿ�ظ� </TD>
          <TD  class= input> <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class=code name=IfReply id="IfReply" value="" onclick="return showCodeList('YesNo',[this]);" ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);"> </TD>
        </TR>

    	<TR  class= common>       
          <TD  class= title >
            ��������ڹ������
          </TD>
          <TD  class= input >
            <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class=code name=OManageCom id="OManageCom" onclick="return showCodeList('station',[this]);" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
          </TD>
          <td class=title></td>
          <TD  class= input>
          <Input  type= "hidden" class= Common name = SerialNo id="SerialNo">
          </TD>
        
      </TR>
      </table>
    </div>
      <a href="javascript:void(0)" class=button name= "Query" onclick="query();">��  ѯ</a>
      
      <!-- <input type="button" class=cssButton name= "Query" class= Common value="��  ѯ" onClick= "query()">
      <input type="button" class=cssButton name= "Back" class= Common value="��  ��" onClick= "returnParent()"> -->           

    <!--Input type=hidden class="readonly" name=IfReply-->
    
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
  <div class="maxbox1">    
  <table  class= common>
    <TR  class= common> 
      <TD class= title style="width:9.8%;"> ��������� </TD>
      <TD ><textarea name="Content" cols="122" rows="4" class="common" readonly></textarea></TD>
    </TR>
  </table>
  
  <table  class= common>
    <TR  class= common> 
      <TD class= title style="width:9.8%;"> ������ظ� </TD>
      <TD ><textarea name="ReplyResult" cols="122" rows="4" class="common" readonly></textarea></TD>
    </TR>
  </table>
  </div>
  <a href="javascript:void(0)" class=button name= "Back" onclick="returnParent();">��  ��</a>
  <p> 
    <!--��ȡ��Ϣ-->
    <input type= "hidden" name= "Flag" id="Flag" value="">
    <input type= "hidden" name= "GrpContNoHide" id="GrpContNoHide" value= "">
    <input type= "hidden" name= "Type" id="Type" value="">
    <input type= "hidden" name= "HideOperatePos" id="HideOperatePos" value="">
    <input type= "hidden" name= "HideSerialNo" id="HideSerialNo" value="">
    <input type= "hidden" name= "HideQuest" id="HideQuest" value="">
    <input type= "hidden" name= "MissionID" id="MissionID" value="">
    <input type= "hidden" name= "SubMissionID" id="SubMissionID" value="">
  </p>
</form>
<div id = "divButton">
  <a href="javascript:void(0)" class=button name= "sure" onclick="reply();">�ظ�����</a>
	<!-- <input class= cssButton type= "button" name= "sure" value="�ظ�����" class= Common onClick="reply()"> -->
</div>
<div id = "divModiButton">
  <a href="javascript:void(0)" class=button name= "sure" onclick="IfPrint();">�޸��������ӡ���</a>
	<!-- <input type= "button" name= "sure" value="�޸��������ӡ���" class= cssButton onClick="IfPrint()"> -->
</div>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
<br>
<br>
<br>
<br>
</body>
</html>
