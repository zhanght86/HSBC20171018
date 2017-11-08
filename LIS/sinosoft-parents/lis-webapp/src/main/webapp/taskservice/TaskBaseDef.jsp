<%
//程序名称：TaskService.jsp
//程序功能：
//创建日期：2004-12-15 
//创建人  ：ZhangRong
//更新记录：  更新人    更新日期     更新原因/内容
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
  
  <%@include file="TaskBaseDefInit.jsp"%>
  <SCRIPT src="TaskBaseDef.js"></SCRIPT>
  
</head>

<body  onload="initForm()" >
  <form action="./TaskBaseDefChk.jsp" method=post name=fm id=fm target="fraSubmit">
 

	<Div  id= "divTask" style= "display: ''">
      <table>
  		<TR>
        	<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
  			<td class="titleImg">任务信息
  			</td>
  		</TR>
  	</table>
  </div>
  <Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
  <table class=common>
        <TR  class= common>
          <TD  class= title>
            基本任务编码
          </TD>
          <TD  class= common>
            <Input class="wid" class= common readonly name=BaseTaskCode >
          </TD>
  		
          <TD  class= title>
            任务描述
          </TD>
          <TD  class= common>
            <Input class="wid" class= common name=TaskDescribe >
          </TD>
       
          <TD  class= title>
            任务处理类             
          </TD>
          <TD  class= common>
            <Input class="wid" class= common name=TaskClass >
          </TD>
        </TR>
      </table></Div><br>
      <table  class= common>
	   	<TR>
	   	</TR>
	   	<TR>
    	  <TD text-align: left colSpan=1>
			<span id="spanTaskGrid" > </span> 
		  </TD>
        </TR>
      </table>
       <Div id = "divTaskButton">
	  <INPUT VALUE="增加任务" class=cssButton TYPE=button name=addbutton onclick="appendTask();">
	  <INPUT VALUE="删除任务" class=cssButton TYPE=button name=delbutton onclick="deleteTask();">
	  <INPUT VALUE="任务刷新" class=cssButton TYPE=button name=delbutton onclick="refreshTask();">
  </Div>
       <input type= "hidden" name= "fmAction" value="">
  </form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> <br><br><br><br>
</body>
</html>
