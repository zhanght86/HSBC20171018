<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html> 
<%
//�������ƣ�GetSendToBankInput.jsp
//�����ܣ�
//�������ڣ�2002-11-18 11:10:36
//������  ���� ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>

  <SCRIPT src="GetSendToBankInput.js"></SCRIPT>
  <%@include file="GetSendToBankInit.jsp"%>
  
  <title>���д��� </title>
</head>

<%
  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
%>

<script>
  comCode = "<%=tGlobalInput.ComCode%>";
</script>

<body  onload="initForm();" >
  <form action="./GetSendToBankSave.jsp" method=post name=fm id=fm target="fraTitle">
    <%@include file="../common/jsp/InputButton.jsp"%>
    <!-- ���д��� -->
    <table class= common border=0 width=100%>
    	<tr>
         <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
			<td class= titleImg align= center>��ѡ�����У�����������������䣨�����������ݺͰ��ռ������������������ݣ���</td>
  		</tr>
  	</table>
  	<div id="divInvAssBuildInfo">
       <div class="maxbox1" >
    <table  class= common>
    <TR  class= common>
      <TD  class= title5>
        ���д���
      </TD>
      <TD  class= input5>
		 <Input class=codeno name=BankCode  id=BankCode
          style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');"  
         onDblClick="return showCodeList('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');"  
         onkeyup="return showCodeListKey('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');" verify="���д���|notnull&code:bank"><input class=codename name=BankName readonly=true>
      </TD>
      </TR>
         <TR  class= common>
      <TD  class= title5>
        ��ʼ����
      </TD>
      <TD  class= input5>
        <input class="coolDatePicker" dateFormat="short" id="StartDate"  name="StartDate" onClick="laydate
({elem:'#StartDate'});" > <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>

      </TD>
      <TD  class= title5>
        ��ֹ����
      </TD>
      <TD  class= input5>
        <input class="coolDatePicker" dateFormat="short" id="EndDate"  name="EndDate" onClick="laydate({elem:'#EndDate'});" > <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
      </TD>
    </TR>
    </table>
    </div>
    </div>
    <br>
    
    <INPUT VALUE="���д���" class= cssButton name="signbutton1" TYPE=button onClick="submitForm()">
    <!--
    <br><br><hr><br>
    
    <table class= common border=0 width=100%>
    	<tr>
			<td class= titleImg align= center>�������ں����������������ݣ�Ӧ����ҵ����������У���</td>
  		</tr>
  	</table>
  	
    <INPUT VALUE="���д��գ�����������" class=common TYPE=button onclick="submitFormForXQ()">
    -->
    <Input type="hidden" name=typeFlag >
           										
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>
</html>
