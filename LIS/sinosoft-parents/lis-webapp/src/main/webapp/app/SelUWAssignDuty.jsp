<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
%>

<script>
  var Operator = "<%=tGlobalInput.Operator%>";
  var ComCode = "<%=tGlobalInput.ComCode%>";
  var manageCom = "<%=tGlobalInput.ManageCom%>"; //记录登陆机构
  var RiskSql = "1 and subriskFlag =#M# ";
  var tCreatePos="异常件处理";
  var tPolState="1006";
  var mActivityID = "0000001090";  //异常件处理
  
</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="SelUWAssignDuty.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="SelUWAssignDutyInit.jsp"%>
  <title>核保随机自动任务分配 </title>
</head>
<body  onload="initForm()" >
  <form action="./SelUWAssignDutySave.jsp" method=post name=fm target="fraSubmit">
	<!--<Table>
		<tr>
        
		<td class= titleImg>分配工作量录入</td>
		</tr>
	</Table>-->
	
	
	<!-- ########################保单信息部分 共享工作池########################  -->
	<Table>
		<tr>
		<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAssign);">
    		</td>
		<td class= titleImg>核保工作量分配录入</td>
		</tr>
	</Table>
	<Div  id= "divAssign" style= "display: ''" class="maxbox1">
 <table class=common>
    <TR  class= common>
          <TD  class= title5>
            起始时间  
			(YYYY-MM-DD HH:MM:SS)
          </TD>
          <TD  class= input5>
            
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartTime'});" verify="起始时间|notnull" dateFormat="short" name=StartTime id="StartTime"><span class="icon"><a onClick="laydate({elem: '#StartTime'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>
            终止时间
			(YYYY-MM-DD HH:MM:SS)
          </TD>
          <TD  class= input5>
            
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndTime'});" verify="终止时间|notnull" dateFormat="short" name=EndTime id="EndTime"><span class="icon"><a onClick="laydate({elem: '#EndTime'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
        </TR>
        <tr class = common>
           <td <TD  class= title5>公共池单数</td>
           <TD  class= input5>
            <Input class="wid" class= readonly name=UWPolSum id=UWPolSum >
          </TD>
        </tr>
    </table>
     </Div><br><br>
       <Div id="Assigninfo" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanAssignGrid" ></span>
					</td>
				</tr>
			</table>
            <center>
			<INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage1.firstPage();">
			<INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage1.previousPage();">
			<INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage1.nextPage();">
			<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage1.lastPage();"></center>
		</div>

	<!--<INPUT class=cssButton id="riskbutton" name=addNew VALUE="增    加" TYPE=button onClick="UWAssignSubmit();">
	<INPUT class=cssButton id="riskbutton" VALUE="修   改" TYPE=button onClick="UWAssignUpdate();">
	<INPUT class=cssButton id="riskbutton" VALUE="启动所选" TYPE=button onClick="UWStartSel();">
	<INPUT class=cssButton id="riskbutton" VALUE="启动全部" TYPE=button onClick="UWStartAll();">
	<INPUT class=cssButton id="riskbutton" VALUE="终止所选" TYPE=button onClick="UWStopSel();">
	<INPUT class=cssButton id="riskbutton" VALUE="终止全部" TYPE=button onClick="UWStopAll();">-->
    <br><br>
    <a href="javascript:void(0);" class="button" onClick="UWAssignSubmit();">增    加</a>
    <a href="javascript:void(0);" class="button" onClick="UWAssignUpdate();">修    改</a>
    <a href="javascript:void(0);" class="button" onClick="UWStartSel();">启动所选</a>
    <a href="javascript:void(0);" class="button" onClick="UWStartAll();">启动全部</a>
    <a href="javascript:void(0);" class="button" onClick="UWStopSel();">终止所选</a>
    <a href="javascript:void(0);" class="button" onClick="UWStopAll();">终止全部</a>

	</Div>

	<!--#########################  隐藏表单区域   ##############################-->
	<Input type=hidden name="MissionID" >
	<Input type=hidden name="SubMissionID" >
	<Input type=hidden name="ActivityID" >   	 
	<Input type=hidden name="Action" >   	 
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
