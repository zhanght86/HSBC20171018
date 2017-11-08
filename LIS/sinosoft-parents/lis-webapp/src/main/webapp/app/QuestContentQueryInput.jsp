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
  
</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>   
  <SCRIPT src="QuestContentQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="QuestContentQueryInit.jsp"%>
  <title>问题件内容查询 </title>
</head>
<body  onload="initForm()" >
	<form action="./QusetContentInputSave.jsp" method=post name=fm id=fm target="fraSubmit">
		<Table>
			<tr>
            <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAssign);"></td>
			<td class= titleImg>问题件内容查询</td>
			</tr>
		</Table>
		<Div  id= "divAssign" style= "display: ''" class="maxbox1">
 			<table class=common>
    			<TR  class= common>
          			<TD  class= title>问题件编码</TD>
          			<TD  class= input>
            			<Input class="wid" class="common" name=QuestCode1 id=QuestCode1 >
          			</TD>
          			<TD  class= title>发送对象</TD>
         			<TD  class= input>
          				<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= "codeno" name = BackObj1 id = BackObj1  CodeData="0|^1|核保通知书(打印类)^2|核保通知书(非打印类)^3|业务员通知书^4|返回机构^5|问题件修改岗"; onclick="0|^1|核保通知书(打印类)^2|核保通知书(非打印类)^3|业务员通知书^4|返回机构^5|问题件修改岗"; ondblclick="return showCodeListEx('BackObj',[this,BackObjName],[0,1]);" onkeyup="return showCodeListKeyEx('backobj',[this,BackObjName],[0,1]);"><Input class = codename name=BackObjName id=BackObjName readonly = true>
          			</TD><TD  class= title>是否记入问题件比例</TD>
          			<TD  class= input>
            			<input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class='code' name="RecordQuest1" id="RecordQuest1" CodeData="0|^Y|是^N|否"; ondblclick="return showCodeListEx('RecordQuest',[this],[0,1]);" onclick="return showCodeListEx('RecordQuest',[this],[0,1]);" onkeyup="return showCodeListKeyEx('RecordQuest',[this],[0,1]);">
          			</TD></TR>
                    
    				<TR  class= common> 
      					<TD  class="title"> 问题件内容 </TD>
    				
      					<TD colspan="5"><textarea name="Content1" cols="204" rows="4" class="common"></textarea></TD>
    				</TR>
  			</table>
       		</Div><!--<INPUT VALUE="查  询" class= cssButton TYPE=button onclick="easyQueryClick();">-->
            <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">查    询</a> <br><br>
       		<Div  id= "divQuestGrid1" style= "display: ''" align = center>
				<Table  class= common >
					<tr  class= common align = left>
						<td text-align: left colSpan=1>
							<span id="spanQuestQueryGrid" ></span> 
			  			</td>
					</tr>
				</Table>
               
			</div>
	 
	
	
    <!--<INPUT VALUE="返  回" class= cssButton TYPE=button onclick="returnParent();"> 
    <INPUT VALUE="生成Excel文件" class= cssButton TYPE=button onclick="MakeExcel();">--><br> 
    <a href="javascript:void(0);" class="button" onClick="returnParent();">返    回</a>
    <a href="javascript:void(0);" class="button" onClick="MakeExcel();">生成Excel文件</a>

	</Div>

	<!--#########################  隐藏表单区域   ##############################-->
	<Input type=hidden name="MissionID" >
	<Input type=hidden name="SubMissionID" >
	<Input type=hidden name="ActivityID" >   	 
	<Input type=hidden name="fmAction" >   	 
	<Input type=hidden name="Init" >   	 
	<Input type=hidden name="tCode1" >   	 
	</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
