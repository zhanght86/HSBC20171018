<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
%>
<script>
  Operator = "<%=tGlobalInput.Operator%>";
  ComCode = "<%=tGlobalInput.ComCode%>";
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
  <SCRIPT src="RnewMasterCenter.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="RnewMasterCenterInit.jsp"%>
  <title>�������� </title>
 </head>

<body  onload="initForm();" >
<form action="../bq/MissionApply.jsp" method=post name=fm id="fm" target="fraSubmit">
	<!-- ########################������Ϣ���� �����ѯ����########################  -->
	<Table class= common>
		<tr>
			<td class=common>
    			<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);">
			</td>
			<td class= titleImg align= center>�������ѯ������</td>
		</tr>
	</Table>
	<div class="maxbox1">
	<Div  id= "divSearch" style= "display: ''">
		<Table  class= common>
			<TR  class= common>
				<TD  class= title5> ��ͬ�� </TD>
				<TD  class= input5>					
					<Input class= "common wid" name=ContNo  id="ContNo">
				</TD>
				<TD  class= title5> �������</TD>
				<TD  class= input5>
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name=ManageCom  id="ManageCom" onclick="showCodeList('station',[this,ManageComName],[0,1]);" ondblclick="showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName  id="ManageComName" readonly=true>
				</TD>  
			</TR>
		</Table>
	</DIV>
	</div>
	<INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="easyQueryClick();">	
	<!-- ########################������Ϣ���� ��������########################  -->
	<Table>
		<tr>
		<td class=common>
		    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
		</td>
		<td class= titleImg>��������</td>
		</tr>
	</Table>
	
	<Div  id= "divLCPol1" style= "display: ''" align = center>
		<Table  class= common >
			<tr  class= common align = left>
				<td text-align: left colSpan=1>
					<span id="spanPolGrid" ></span> 
			  	</td>
			</tr>
		</Table>
		<div style= "display: none">
		<INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
		<INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 
		<INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
		<INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
		</div>		
	</Div>
	<div>
		<a href="javascript:void(0)" class=button onclick="ApplyUW();">��  ��</a>
	</div>
    <!-- <INPUT class=cssButton id="riskbutton" VALUE="��  ��" TYPE=button onClick="ApplyUW();">  -->
	<br>
	<!-- ########################������Ϣ���� ���˴����˱�������########################  -->
	<div class="maxbox1">
	<Div  id= "divSearch1" style= "display: ''">
		<Table  class= common>
			<TR  class= common>
				<TD  class= title5> ��ͬ�� </TD>
				<TD  class= input5>
					<Input class= "common wid" name=ContNo1   id="ContNo1">
				</TD>
				<TD  class= title5> �������</TD>
				<TD  class= input5>
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name=ManageCom1  id="ManageCom1" onclick="showCodeList('station',[this,ManageComName1],[0,1]);" ondblclick="showCodeList('station',[this,ManageComName1],[0,1]);" onkeyup="showCodeListKey('station',[this,ManageComName1],[0,1]);"><input class=codename name=ManageComName1  id="ManageComName1" readonly=true>
				</TD>  
			</TR>
		</Table> 
	</DIV>
	</div>
	<div>
		<a href="javascript:void(0)" class=button onclick="easyQueryClickSelf();">��  ѯ</a>
	</div>
	<!-- <INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="easyQueryClickSelf();"> -->
	<Table>
		<tr>
		<td class=common>
		    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfPolGrid);">
		</td>
		<td class= titleImg>��������</td>
		</tr>  	
	</Table>
	<Div  id= "divSelfPolGrid" style= "display: ''" align = center>
		<Table  class= common >
		<tr  class=common>
			<td text-align: left colSpan=1 >
				<span id="spanSelfPolGrid" ></span> 
		  	</td>
		</tr>
		</Table>
		<div style= "display: none">
		<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage2.firstPage();"> 
		<INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage2.previousPage();"> 					
		<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage2.nextPage();"> 
		<INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage2.lastPage();">
		</div>     
	</Div>

	<!--#########################  ���ر�����   ##############################-->
	<Input type=hidden name="Operator"  id="Operator">
  <Input type=hidden name="ComCode"  id="ComCode">
	<Input type=hidden name="MissionID"  id="MissionID">
	<Input type=hidden name="SubMissionID"  id="SubMissionID">
	<Input type=hidden name="ActivityID"  id="ActivityID"> 
	<Input type=hidden name="hiddenContNo"  id="hiddenContNo"> 
	<Input class= common  name=EdorNo  id="EdorNo" type="hidden">
	<Input class= common  name=EdorType  id="EdorType" type="hidden">  	 
</form>
<br>
<br>
<br>
<br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>


