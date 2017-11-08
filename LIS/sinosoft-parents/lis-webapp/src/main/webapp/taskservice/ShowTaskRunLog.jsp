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
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ShowTaskRunLogInit.jsp"%>
  <SCRIPT src="ShowTaskRunLog.js"></SCRIPT>
</head>
<%
	String mTaskPlanCode = request.getParameter("TaskPlanCode");
%>
<script>
	var jTaskPlanCode = '<%=mTaskPlanCode%>';
	//alert(jTaskPlanCode);
</script>
<body  onload="initForm()" >
  <form action="./TaskGroupDefChk.jsp" method=post name=fm id=fm target="fraSubmit">

	<Div  id= "divTask" style= "display: ''">
      <table>
  		<TR>
  			<td class="titleImg">当天运行日志信息
  			</td>
  		</TR>
  	</table>
  	 <table  class= common>
	   	<TR>
	   	</TR>
	   	<TR>
    	  <TD text-align: left colSpan=1>
			<span id="spanTaskRunLogTodayGrid" > </span> 
		  </TD>
        </TR>
      </table>
      
  </div> 
  <hr>
  <Div  id= "divTask" style= "display: ''">
      <table>
  		<TR>
  			<td class="titleImg">既往运行日志信息
  			</td>
  		</TR>
  	</table>
	<div class=maxbox1>
  	<table class=common>
        <TR  class= common>
          <TD  class= title5>
            日志起期
          </TD>
          <TD  class= input5>
            <input class="coolDatePicker" dateFormat="short"  name="RunLogDateStart" onClick="laydate({elem: '#RunLogDateStart'});" id="RunLogDateStart"><span class="icon"><a onClick="laydate({elem: '#RunLogDateStart'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>
            日志止期
          </TD>
          <TD  class= input5>
             <input class="coolDatePicker" dateFormat="short"  name="RunLogDateEnd" onClick="laydate({elem: '#RunLogDateEnd'});" id="RunLogDateEnd"><span class="icon"><a onClick="laydate({elem: '#RunLogDateEnd'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
        </TR>
      </table>
  	</div>
  <table class=common>
	  <INPUT VALUE="查  询" class=cssButton TYPE=button name=addbutton onclick="queryTaskRunLogBefore();">
  </table>
  	 <table  class= common>
	   	<TR>
	   	</TR>
	   	<TR>
    	  <TD text-align: left colSpan=1>
			<span id="spanTaskRunLogBeforeGrid" > </span> 
		  </TD>
        </TR>
      </table>
      
  </div>
  
          
       <input type= "hidden" name= "fmAction" value="">
  </form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
</body>
</html>
