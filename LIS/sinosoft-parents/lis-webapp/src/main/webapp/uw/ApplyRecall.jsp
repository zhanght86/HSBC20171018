<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�ApplyRecall.jsp
//�����ܣ�
//�������ڣ�
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="ApplyRecall.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ApplyRecallInit.jsp"%>
  <title>��������</title>  
</head>
<body  onload="initForm();" >
  <form method=post id="fm" name=fm target="fraSubmit" action= "./ApplyRecallChk.jsp">
    <table class= common border=0 width=100%>
    	<tr>
			<td class= titleImg align= center>�������ѯ������</td>
		</tr>
	</table>
	<Div  id= "divSearch" class="maxbox" style= "display: ''">
    <table  class= common>
      	<TR  class= common>
          <TD  class= title> Ͷ��������  </TD>
          <TD  class= input> <Input class= "common wid" id="ContNo" name=ContNo > </TD>
          <TD  class= title> ӡˢ���� </TD>
          <TD  class= input><Input class= "common wid" id="PrtNoSearch" name=PrtNoSearch > </TD>
          <TD  class= title> ������� </TD>
          <TD  class= input>
            <Input class="codeno" id="ManageCom" name=ManageCom style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename id="ManageComName" name=ManageComName readonly=true>
          </TD>
        <TR  class= common>
          <TD  class= title>  Ͷ��������  </TD>
          <TD  class= input> <Input class= "common wid" id="AppntName" name=AppntName > </TD>
          <TD  class= title>  ����������</TD>
          <TD  class= input> <Input class= "common wid" id="InsuredName" name=InsuredName > </TD>
          <TD  class= title> �������Ա�  </TD>
          <TD class=input>  
          <Input class= codeno id="InsuredSex" name=InsuredSex style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('sex',[this,SexName],[0,1]);" onkeyup="showCodeListKey('sex',[this,SexName],[0,1]);" ><input class=codename id="SexName" name=SexName readonly=true>  </TD>        
        </TR>
        <TR  class= common>
          <TD  class= title>  �˱����� </TD>
          <TD  class= input> 
          <Input class="codeno" id="UWStateQuery" name=UWStateQuery style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('uwstate',[this,UWStateQueryName],[0,1]);" onkeyup="showCodeListKey('uwstate',[this,UWStateQueryName],[0,1]);" ><input class=codename id="UWStateQueryName" name=UWStateQueryName readonly=true></TD>
          <TD  class= title> �����˱��� </TD>
          <TD  class= input>
            <Input class="codeno" id="AgentCode" name=AgentCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('AgentCode',[this, AgentName], [0, 1]);" onkeyup="showCodeListKey('AgentCode', [this, AgentName], [0, 1]);"><input class=codename id="AgentName" name=AgentName readonly=true>
          </TD>
          <TD  class= title> ��������� </TD>
          <TD  class= input>
            <Input class="codeno" id="AgentGroup" name=AgentGroup style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('AgentGroup',[this,AgentGroupName],[0,1]);" onkeyup="return showCodeListKey('AgentGroup',[this,AgentGroupName],[0,1]);"><input class="codename" id="AgentGroupName" name=AgentGroupName readonly=true>
          </TD>  
        </TR>
        <TR>
        	<TD  class= title> �������� </TD>
          <TD  class= input>
            <Input class="codeno" id="ContType" name=ContType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('conttype',[this,conttypeName],[0,1]);" onkeyup="return showCodeListKey('conttype',[this,conttypeName],[0,1]);"><input class="codename" id="conttypeName" name=conttypeName readonly=true>
          </TD>  
       </TR>  
    </table>
    </DIV>
          <INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="easyQueryClick();"> 
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid);">
    		</td>
    		<td class= titleImg>
    			 Ͷ������Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divPolGrid" style= "display: ''" align = center>
      	<table  class= common >
       		<tr  class= common align = left>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">		
  	</div>
    
  <br>
  
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divContent);">
    		</td>
    		<td class= titleImg>
    			 ����˵��
    		</td>
    	</tr>
    </table>
  	<Div  id= "divContent" class="maxbox" style= "display: ''" align = center>      
  <table width="121%" height="37%" class= common>
    <TR  class= common>
      <TD height="87%"  class= title><textarea class= "common" name="Content" cols="135" rows="10" class="common"></textarea></TD>
    </TR>
  </table>
  </div>
  	<input type=hidden id="PrtNo" name="PrtNo">
  	<input type=hidden id="ContNoH" name="ContNoH">
  	<input type = hidden id="PolType" name = "PolType">

</form>
  
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
<input type= "button" class=cssButton id="sure" name= "sure" value="ȷ  ��" onClick="submitForm()">
<br/><br/><br/><br/>
</body>
</html>
