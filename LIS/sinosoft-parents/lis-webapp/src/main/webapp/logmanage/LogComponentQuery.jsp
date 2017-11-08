<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
  loggerDebug("LogComponentQuery","LogComponentQuery.jsp");
%>
<head >
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="LogComponentQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="LogComponentQueryInit.jsp"%>

</head>
<body  onload="initForm();initElementtype();" >
  <form action="" method=post name=fm  id=fm target="fraSubmit">	
  
	<Div id= "DivLogSubject" style= "display: ''" >
	<Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivQuery);">
    		</TD>
    		<TD class= titleImg>
    			 日志查询：
    		</TD>
    	</TR>
	</Table> 
  <div id="DivQuery">
       <div class="maxbox1" >  
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>日志主题</TD>
          <TD  class= input5><input class=common name= SubjectID></TD>
          <TD  class= title5>控制点</TD>
          
          <TD  class= input5><input class=common name= ItemID></TD>
          </TR>
          <TR  class= common>
          <TD  class= title5>关键号码</TD>
          <TD  class= input5><input class=common name= KeyNO></TD>
        </TR>
	</Table>
	
    </div>
    </div>
   <!-- <table>
      <tr>
        <td><input type="button" class=cssButton value="&nbsp;查  询&nbsp;" onClick="ClickQuery()"></td>
        <td><input type="button" class=cssButton value="&nbsp;重  置&nbsp;" onClick="DateReset()"></td>
      </tr>
	</table>-->
    <a href="javascript:void(0);" class="button"onClick="ClickQuery()">查   询</a>
    <a href="javascript:void(0);" class="button"onClick="DateReset()">重   置</a>
	<br>
	</div>
 
	<Div  id= "divState" style= "display: ''">
	<Table>
   	<TR>
       	<TD class=common>
           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLogStateGrid);">
   		</TD>
   		<TD class= titleImg>状态日志信息</TD>
   	</TR>
    </Table>
    <Div  id= "divLogStateGrid" style= "display: ''"  align=center>
	<Table  class= common >
       <TR  class= common>
        <TD text-align: left colSpan=1>
            <span id="spanLogStateGrid" ></span> 
		</TD>
      </TR>
	</Table>					
        <INPUT VALUE="首  页" class="cssButton90" TYPE=button onClick="turnPage.firstPage();"> 
	 	<INPUT VALUE="上一页" class="cssButton91" TYPE=button onClick="turnPage.previousPage();"> 					
		<INPUT VALUE="下一页" class="cssButton92" TYPE=button onClick="turnPage.nextPage();"> 
		<INPUT VALUE="尾  页" class="cssButton93" TYPE=button onClick="turnPage.lastPage();"> 		
	</Div>
	</Div>  
  <Div  id= "divTrack" style= "display: ''">
	<Table>
   	<TR>
       	<TD class=common>
           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLogTrackGrid);">
   		</TD>
   		<TD class= titleImg>轨迹日志信息</TD>
   	</TR>
    </Table>
    <Div  id= "divLogTrackGrid" style= "display: ''"  align=center>
	<Table  class= common >
       <TR  class= common>
        <TD text-align: left colSpan=1>
            <span id="spanLogTrackGrid" ></span> 
		</TD>
      </TR>
	</Table>					
        <INPUT VALUE="首  页" class="cssButton90" TYPE=button onClick="turnPage1.firstPage();"> 
	 	<INPUT VALUE="上一页" class="cssButton91" TYPE=button onClick="turnPage1.previousPage();"> 					
		<INPUT VALUE="下一页" class="cssButton92" TYPE=button onClick="turnPage1.nextPage();"> 
		<INPUT VALUE="尾  页" class="cssButton93" TYPE=button onClick="turnPage1.lastPage();"> 		
	</Div>
	</Div>
	<br><br><br><br>
</form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

</html>
