<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWGChangeResultInput.jsp
//�����ܣ�������Ҫ����¼��
//�������ڣ�2006-10-10 15:50:36
//������  ��CHENRONG
//���¼�¼��  ������          ��������     ����ԭ��/����
//            liuxiaosong      2006-11-15   ��ȫ���ø�ҳ�棬���ӷ��ͱ�ȫ�˱�Ҫ��֪ͨ�鹦��
%>
<html> 

<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>  
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="UWGChangeResult.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="UWGChangeResultInit.jsp"%>
  <title>������Ҫ����¼��</title>
</head>
<body  onload="initForm();initElementtype();" >
  <form method=post id="fm" name=fm target="fraSubmit">
    <DIV id=DivLCContButton STYLE="display:''">
    	<table id="table1">
    		<tr>
    			<td class="common">
    				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivGrpContInfo);">
    			</td>
    			<td class="titleImg">�����ͬ��Ϣ</td>
    		</tr>
    	</table>
    </DIV>
    <DIV id=DivGrpContInfo class="maxbox1" STYLE="display:''">
        <table  class= common>
            <TR  class= common>
                <TD  class= title5>����Ͷ��������</TD>
                <TD  class= input5><Input class="readonly wid" readonly name=GrpContNo id=GrpContNo ></TD>
                <TD></TD>
            </TR>
          	<TR>
              	<TD height="24"  class= title>֪ͨ������¼��:</TD>
            </TR>     
            <TR>   
      		    <TD  class= input  colspan=2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea name="ChangeIdea" cols="100%" rows="5" witdh=100% class="common" elementtype=nacessary></textarea></TD>      		    
    	    </TR>
    	</table>
    </DIV>
    <hr class="line">
    <p>
        <INPUT VALUE="��  ��"class="cssButton"  TYPE=button onclick="parent.close();"> 	
        <INPUT VALUE="ȷ  ��"class="cssButton"  TYPE=button onclick="saveChangeResult();"> 	
    </p>
    <input type="hidden" id="MissionID" name= "MissionID" value= "">
    <input type="hidden" id="SubMissionID" name= "SubMissionID" value= "">
    <input type="hidden" id="EdorNo" name= "EdorNo">
    <input type="hidden" id="EdorType" name= "EdorType">
    <input type="hidden" id="PrtNo" name= "PrtNo">
  </form>
</body>
</html>
