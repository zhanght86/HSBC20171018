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
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>   
  <SCRIPT src="QusetContentInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="QusetContentInputInit.jsp"%>
  <title>��������ݶ��� </title>
</head>
<body  onload="initForm()" >
	<form action="./QusetContentInputSave.jsp" method=post name=fm target="fraSubmit">
		<Table>
			<tr>
            <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAssign);"></td>
			<td class= titleImg>���������¼��</td>
			</tr>
		</Table>
		<Div  id= "divAssign" style= "display: ''" class="maxbox1">
 			<table class=common>
    			<TR  class= common>
          			<TD  class= title>���������</TD>
          			<TD  class= input>
            			<Input class="wid" class="common" name=QuestCode id=QuestCode  >
          			</TD>
          			<TD  class= title>���Ͷ���</TD>
         			<TD  class= input>
          				<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= "codeno" name = BackObj id = BackObj  CodeData="0|^1|�˱�֪ͨ��(��ӡ��)^2|�˱�֪ͨ��(�Ǵ�ӡ��)^3|ҵ��Ա֪ͨ��^4|���ػ���^5|������޸ĸ�"; onclick="0|^1|�˱�֪ͨ��(��ӡ��)^2|�˱�֪ͨ��(�Ǵ�ӡ��)^3|ҵ��Ա֪ͨ��^4|���ػ���^5|������޸ĸ�"; ondblclick="return showCodeListEx('BackObj',[this,BackObjName],[0,1]);" onkeyup="return showCodeListKeyEx('backobj',[this,BackObjName],[0,1]);"><Input class = codename name=BackObjName id=BackObjName readonly = true>
          			</TD><TD  class= title>�Ƿ�������������</TD>
          			<TD  class= input>
            			<input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class='code' name="RecordQuest" id="RecordQuest" CodeData="0|^Y|��^N|��"; ondblclick="return showCodeListEx('RecordQuest',[this],[0,1]);" onclick="return showCodeListEx('RecordQuest',[this],[0,1]);" onkeyup="return showCodeListKeyEx('RecordQuest',[this],[0,1]);">
          			</TD></TR></table>
                   <table class=common>
    				<TR  class= common> 
      					<TD class= title> ��������� </TD>
    				
      					<TD colspan="5"><textarea name="Content" cols="185" rows="4" class="common"></textarea></TD>
    				</TR>
  			</table></Div>
  
       		<Div  id= "divQuestGrid" style= "display: none" align = center>
				<Table  class= common >
					<tr  class= common align = left>
						<td text-align: left colSpan=1>
							<span id="spanQuestGrid" ></span> 
			  			</td>
					</tr>
				</Table>
                <center>
			<INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
			<INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
			<INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
			<INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">	</center>	
			</div>
	 
	<!--<INPUT class=cssButton id="riskbutton" VALUE="��   ��" TYPE=button onClick="SaveQuest();">
	<INPUT class=cssButton id="riskbutton" VALUE="��   ��" TYPE=button onClick="updateQuest();">
	<INPUT class=cssButton id="riskbutton" VALUE="��   ѯ" TYPE=button onClick="QueryData();">
	<INPUT class=cssButton id="riskbutton" VALUE="ɾ   ��" TYPE=button onClick="deleteQuest();">-->
    <a href="javascript:void(0);" class="button" onClick="SaveQuest();">��    ��</a>
    <a href="javascript:void(0);" class="button" onClick="updateQuest();">��    ��</a>
    <a href="javascript:void(0);" class="button" onClick="QueryData();">��    ѯ</a>
    <a href="javascript:void(0);" class="button" onClick="deleteQuest();">ɾ    ��</a>

	</Div>

	<!--#########################  ���ر�����   ##############################-->
	<Input type=hidden name="MissionID" >
	<Input type=hidden name="SubMissionID" >
	<Input type=hidden name="ActivityID" >   	 
	<Input type=hidden name="fmAction" >   	 
	<Input type=hidden name="Init" >   	 
	<Input type=hidden name="tCode" >   	 
	</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
