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
  <title>�˱�����Զ�������� </title>
</head>
<body  onload="initForm()" >
  <form action="./SelUWAssignDutySave.jsp" method=post name=fm target="fraSubmit">
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
		<td class= titleImg>�˱�����������¼��</td>
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
        <tr class = common>
           <td <TD  class= title5>�����ص���</td>
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
			<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage1.firstPage();">
			<INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage1.previousPage();">
			<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage1.nextPage();">
			<INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage1.lastPage();"></center>
		</div>

	<!--<INPUT class=cssButton id="riskbutton" name=addNew VALUE="��    ��" TYPE=button onClick="UWAssignSubmit();">
	<INPUT class=cssButton id="riskbutton" VALUE="��   ��" TYPE=button onClick="UWAssignUpdate();">
	<INPUT class=cssButton id="riskbutton" VALUE="������ѡ" TYPE=button onClick="UWStartSel();">
	<INPUT class=cssButton id="riskbutton" VALUE="����ȫ��" TYPE=button onClick="UWStartAll();">
	<INPUT class=cssButton id="riskbutton" VALUE="��ֹ��ѡ" TYPE=button onClick="UWStopSel();">
	<INPUT class=cssButton id="riskbutton" VALUE="��ֹȫ��" TYPE=button onClick="UWStopAll();">-->
    <br><br>
    <a href="javascript:void(0);" class="button" onClick="UWAssignSubmit();">��    ��</a>
    <a href="javascript:void(0);" class="button" onClick="UWAssignUpdate();">��    ��</a>
    <a href="javascript:void(0);" class="button" onClick="UWStartSel();">������ѡ</a>
    <a href="javascript:void(0);" class="button" onClick="UWStartAll();">����ȫ��</a>
    <a href="javascript:void(0);" class="button" onClick="UWStopSel();">��ֹ��ѡ</a>
    <a href="javascript:void(0);" class="button" onClick="UWStopAll();">��ֹȫ��</a>

	</Div>

	<!--#########################  ���ر�����   ##############################-->
	<Input type=hidden name="MissionID" >
	<Input type=hidden name="SubMissionID" >
	<Input type=hidden name="ActivityID" >   	 
	<Input type=hidden name="Action" >   	 
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
