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
  var manageCom = "<%=tGlobalInput.ManageCom%>"; //��¼��½����
  var RiskSql = "1 and subriskFlag =#M# ";
  var tCreatePos="�쳣������";
  var tPolState="1006";
  var mActivityID = "0000001090";  //�쳣������
  
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
  <title>����Լ����Զ�������� </title>
</head>
<body  onload="initForm()" >
  <form action="./SelAssignDutySave.jsp" method=post name=fm id=fm target="fraSubmit">
	<!--<Table>
		<tr>
		<td class= titleImg>���乤����¼��</td>
		</tr>
	</Table>-->
	
	
	<!-- ########################������Ϣ���� ��������########################  -->
	<Table>
		<tr>
		<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAssign);">
    		</td>
		<td class= titleImg>����Լ����������¼��</td>
		</tr>
	</Table>
	<Div  id= "divAssign" style= "display: ''" class="maxbox1">
 <table class=common>
    <TR  class= common>
          <TD  class= title5>
            ��ʼʱ��  
			(YYYY-MM-DD HH:MM:SS)
          </TD>
          <TD  class= input5>
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartTime'});" verify="��ʼʱ��|notnull" dateFormat="short" name=StartTime id="StartTime"><span class="icon"><a onClick="laydate({elem: '#StartTime'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>
            ��ֹʱ��
			(YYYY-MM-DD HH:MM:SS)
          </TD>
          <TD  class= input5>
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndTime'});" verify="��ֹʱ��|notnull" dateFormat="short" name=EndTime id="EndTime"><span class="icon"><a onClick="laydate({elem: '#EndTime'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
        </TR>
    </table></Div>
    
    <table class=common >
    <tr class="common">
    		<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
            <td class= titleImg>�������ܵ���</td>
    </table>
    <Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
    <table class =common>
        <tr class = common>
           <td class= title5>�쳣��</td>
           <TD  class= input5>
            <Input class="wid" class= readonly name=QuestModify id=QuestModify >
          </TD>
           <td class= title5>�����</td>
           <TD  class= input5>
            <Input class="wid" class= readonly name=ApproveModify id=ApproveModify >
          </TD>
          </tr>
           <tr class = common>
           <td class= title5>�˹��ϲ�</td>
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
		<INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
		<INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
		<INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
		<INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">		</center>
	 
	


<INPUT class=cssButton id="riskbutton" name=addNew VALUE="��   ��" TYPE=button onClick="AssignSubmit();">

	<!--<INPUT class=cssButton id="riskbutton" VALUE="��   ��" TYPE=button onClick="AssignUpdate();">
	<INPUT class=cssButton id="riskbutton" VALUE="������ѡ" TYPE=button onClick="StartSel();">-->
	<!--<INPUT class=cssButton id="riskbutton" VALUE="����ȫ��" TYPE=button onClick="StartAll();">-->
	<!--<INPUT class=cssButton id="riskbutton" VALUE="��ֹ��ѡ" TYPE=button onClick="StopSel();">-->
	<!--<INPUT class=cssButton id="riskbutton" VALUE="��ֹȫ��" TYPE=button onClick="StopAll();">-->
    <!--<a href="javascript:void(0);" id="riskbutton" class="button" onClick="AssignSubmit();">��    ��</a>-->
    <a href="javascript:void(0);" id="riskbutton" class="button" onClick="AssignUpdate();">��    ��</a>
    <a href="javascript:void(0);" id="riskbutton" class="button" onClick="StartSel();">������ѡ</a>
    <a href="javascript:void(0);" id="riskbutton" class="button" onClick="StartAll();">����ȫ��</a>
    <a href="javascript:void(0);" id="riskbutton" class="button" onClick="StopSel();">��ֹ��ѡ</a>
    <a href="javascript:void(0);" id="riskbutton" class="button" onClick="StopAll();">��ֹȫ��</a>
	
	</Div>

	<!--#########################  ���ر�����   ##############################-->
	<Input type=hidden name="MissionID" >
	<Input type=hidden name="SubMissionID" >
	<Input type=hidden name="ActivityID" >   	 
	<Input type=hidden name="Action" >   	 
	<Input type=hidden name="Init" >   	 
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
