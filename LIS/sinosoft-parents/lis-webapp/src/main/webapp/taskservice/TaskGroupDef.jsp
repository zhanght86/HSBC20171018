<%
//�������ƣ�TaskService.jsp
//�����ܣ�
//�������ڣ�2004-12-15 
//������  ��ZhangRong
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.taskservice.*"%>
<script>
</script>
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>

  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <%@include file="TaskGroupDefInit.jsp"%>
  <SCRIPT src="TaskGroupDef.js"></SCRIPT>
  
</head>

<body  onload="initForm()" >
  <form action="./TaskGroupDefChk.jsp" method=post name=fm id=fm target="fraSubmit">
 

	<Div  id= "divTask" style= "display: ''">
      <table>
  		<TR>
        	<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
  			<td class="titleImg">���������Ϣ
  			</td>
  		</TR>
  	</table>
  </div>
  <Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
  <table class=common>
        <TR  class= common>
          <TD  class= title>
            ������б���
          </TD>
          <TD  class= input>
            <Input class="wid" class= common readonly name=TaskGroupCode id=TaskGroupCode >
          </TD>
  		
          <TD  class= title>
            �����������
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=TaskGroupDescribe id=TaskGroupDescribe >
          </TD>
          <TD  class= title></TD>
          <TD  class= input></TD>
        </TR>
      </table></Div> <Div id = "divTaskButton">
	  <INPUT VALUE="�����������" class=cssButton TYPE=button name=addbutton onclick="appendTaskGroup();">
	  <INPUT VALUE="ɾ���������" class=cssButton TYPE=button name=delbutton onclick="deleteTask();">
	  <INPUT VALUE="�������ˢ��" class=cssButton TYPE=button name=delbutton onclick="refreshTask();">
  </Div>
      <table  class= common>
	   	<TR>
	   	</TR>
	   	<TR>
    	  <TD text-align: left colSpan=1>
			<span id="spanTaskGroupGrid" > </span> 
		  </TD>
        </TR>
      </table>
  
    <Div id = "divTaskGroupButton">
	  <INPUT VALUE="�������������ϸ" class=cssButton TYPE=button name=addbutton onclick="appendTaskGroupDetail();">
	  <INPUT VALUE="ɾ�����������ϸ" class=cssButton TYPE=button name=delbutton onclick="deleteTaskGroupDetail();">
  </Div>
  
  <Div  id= "divTask1" style= "display: none">
      <table>
  		<TR>
  			<td class="titleImg">���������ϸ
  			</td>
  		</TR>
  	</table>
  </div>
  <table style= "display: none"  class= common>
	   	<TR>
	   	</TR>
	   	<TR>
    	  <TD text-align: left colSpan=1>
			<span id="spanTaskGroupDetailGrid" > </span> 
		  </TD>
        </TR>
      </table>
          
       <input type= "hidden" name= "fmAction" value="">
  </form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> <br><br><br><br>
</body>
</html>
