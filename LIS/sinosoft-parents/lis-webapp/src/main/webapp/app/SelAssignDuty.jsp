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
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>   
  <SCRIPT src="SelAssignDuty.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="SelAssignDutyInit.jsp"%>
  <title>新契约随机自动任务分配 </title>
</head>
<body  onload="initForm()" >
  <form action="./SelAssignDutySave.jsp" method=post name=fm id=fm target="fraSubmit">
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
		<td class= titleImg>新契约工作量分配录入</td>
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
    </table></Div>
    
    <table class=common >
    <tr class="common">
    		<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
            <td class= titleImg>公共池总单数</td>
    </table>
    <Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
    <table class =common>
        <tr class = common>
           <td class= title5>异常件</td>
           <TD  class= input5>
            <Input class="wid" class= readonly name=QuestModify id=QuestModify >
          </TD>
           <td class= title5>问题件</td>
           <TD  class= input5>
            <Input class="wid" class= readonly name=ApproveModify id=ApproveModify >
          </TD>
          </tr>
           <tr class = common>
           <td class= title5>人工合并</td>
           <TD  class= input5>
            <Input class="wid" class= readonly name=CustomerMerge id=CustomerMerge >
          </TD>
        </tr>
      </table></Div>
       <br><br>
		<Table  class= common >
			<tr  class= common align = left>
				<td text-align: left colSpan=1>
					<span id="spanAssignGrid" ></span> 
			  	</td>
			</tr>
		</Table>
        <center>
		<INPUT class=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
		<INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
		<INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
		<INPUT class=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">		</center>
	 
	


<INPUT class=cssButton id="riskbutton" name=addNew VALUE="新   增" TYPE=button onClick="AssignSubmit();">

	<!--<INPUT class=cssButton id="riskbutton" VALUE="修   改" TYPE=button onClick="AssignUpdate();">
	<INPUT class=cssButton id="riskbutton" VALUE="启动所选" TYPE=button onClick="StartSel();">-->
	<!--<INPUT class=cssButton id="riskbutton" VALUE="启动全部" TYPE=button onClick="StartAll();">-->
	<!--<INPUT class=cssButton id="riskbutton" VALUE="终止所选" TYPE=button onClick="StopSel();">-->
	<!--<INPUT class=cssButton id="riskbutton" VALUE="终止全部" TYPE=button onClick="StopAll();">-->
    <!--<a href="javascript:void(0);" id="riskbutton" class="button" onClick="AssignSubmit();">新    增</a>-->
    <a href="javascript:void(0);" id="riskbutton" class="button" onClick="AssignUpdate();">修    改</a>
    <a href="javascript:void(0);" id="riskbutton" class="button" onClick="StartSel();">启动所选</a>
    <a href="javascript:void(0);" id="riskbutton" class="button" onClick="StartAll();">启动全部</a>
    <a href="javascript:void(0);" id="riskbutton" class="button" onClick="StopSel();">终止所选</a>
    <a href="javascript:void(0);" id="riskbutton" class="button" onClick="StopAll();">终止全部</a>
	
	</Div>

	<!--#########################  隐藏表单区域   ##############################-->
	<Input type=hidden name="MissionID" >
	<Input type=hidden name="SubMissionID" >
	<Input type=hidden name="ActivityID" >   	 
	<Input type=hidden name="Action" >   	 
	<Input type=hidden name="Init" >   	 
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
