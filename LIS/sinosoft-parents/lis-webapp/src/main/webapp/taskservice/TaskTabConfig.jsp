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
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <%@include file="TaskTabConfigInit.jsp"%>
  <SCRIPT src="TaskTabConfig.js"></SCRIPT>
  
</head>

<body  onload="initForm()" >
  <form action="./TaskTabConfigChk.jsp" method=post name=fm id=fm target="fraSubmit">
 
	<Div  id= "divTask" style= "display: ''">
      <table>
  		<TR><td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
  			<td class="titleImg">系统用户信息
  			</td>
  		</TR>
  	</table>
  </div><Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
  <table class=common>
        <TR  class= common>
          <TD  class= title>
            用户ID
          </TD>
          <TD  class= common>
            <Input class="wid" class= input  name=UserCode id=UserCode >
          </TD>
  		
          <TD  class= title>
            用户姓名
          </TD>
          <TD  class= common>
            <Input class="wid" class= input name=UserName id=UserName >
          </TD>
           <TD  class= title></TD>
          <TD  class= input></TD>
        </TR>
      </table></Div>  <Div id = "divTaskButton">
	  <INPUT VALUE="查询用户信息" class=cssButton TYPE=button name=addbutton onclick="queryLDUserGridInfo();">
 </Div>
      <table  class= common>
	   	<TR>
	   	</TR>
	   	<TR>
    	  <TD text-align: left colSpan=1>
			<span id="spanLDUserGrid" > </span> 
		  </TD>
        </TR>
      </table>
 
</div> <Div id = "divTaskGroupButton">
	  <INPUT VALUE="保存用户配置" class=cssButton TYPE=button name=addbutton onclick="appendTaskTabDetail();">
	  <INPUT VALUE="删除用户配置" class=cssButton TYPE=button name=delbutton onclick="deleteTaskTabDetail();">
  </Div>
   
  
  <Div  id= "divTask1" style= "display: ''">
      <table>
  		<TR>
  			<td class="titleImg">任务队列明细
  			</td>
  		</TR>
  	</table>
  
  <table  class= common>
	   	<TR>
	   	</TR>
	   	<TR>
    	  <TD text-align: left colSpan=1>
			<span id="spanUserTabDetailGrid" > </span> 
		  </TD>
        </TR>
      </table>
          
       <input type= "hidden" name= "fmAction" value="">
  </form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> <br><br><br><br>
</body>
</html>
