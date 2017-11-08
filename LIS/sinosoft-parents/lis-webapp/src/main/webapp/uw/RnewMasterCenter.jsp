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
  <title>管理中心 </title>
 </head>

<body  onload="initForm();" >
<form action="../bq/MissionApply.jsp" method=post name=fm id="fm" target="fraSubmit">
	<!-- ########################保单信息部分 输入查询条件########################  -->
	<Table class= common>
		<tr>
			<td class=common>
    			<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);">
			</td>
			<td class= titleImg align= center>请输入查询条件：</td>
		</tr>
	</Table>
	<div class="maxbox1">
	<Div  id= "divSearch" style= "display: ''">
		<Table  class= common>
			<TR  class= common>
				<TD  class= title5> 合同号 </TD>
				<TD  class= input5>					
					<Input class= "common wid" name=ContNo  id="ContNo">
				</TD>
				<TD  class= title5> 管理机构</TD>
				<TD  class= input5>
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name=ManageCom  id="ManageCom" onclick="showCodeList('station',[this,ManageComName],[0,1]);" ondblclick="showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName  id="ManageComName" readonly=true>
				</TD>  
			</TR>
		</Table>
	</DIV>
	</div>
	<INPUT class=cssButton VALUE="查  询" TYPE=button onclick="easyQueryClick();">	
	<!-- ########################保单信息部分 共享工作池########################  -->
	<Table>
		<tr>
		<td class=common>
		    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
		</td>
		<td class= titleImg>共享工作池</td>
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
		<INPUT class=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
		<INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 
		<INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
		<INPUT class=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
		</div>		
	</Div>
	<div>
		<a href="javascript:void(0)" class=button onclick="ApplyUW();">申  请</a>
	</div>
    <!-- <INPUT class=cssButton id="riskbutton" VALUE="申  请" TYPE=button onClick="ApplyUW();">  -->
	<br>
	<!-- ########################保单信息部分 个人待复核保单队列########################  -->
	<div class="maxbox1">
	<Div  id= "divSearch1" style= "display: ''">
		<Table  class= common>
			<TR  class= common>
				<TD  class= title5> 合同号 </TD>
				<TD  class= input5>
					<Input class= "common wid" name=ContNo1   id="ContNo1">
				</TD>
				<TD  class= title5> 管理机构</TD>
				<TD  class= input5>
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name=ManageCom1  id="ManageCom1" onclick="showCodeList('station',[this,ManageComName1],[0,1]);" ondblclick="showCodeList('station',[this,ManageComName1],[0,1]);" onkeyup="showCodeListKey('station',[this,ManageComName1],[0,1]);"><input class=codename name=ManageComName1  id="ManageComName1" readonly=true>
				</TD>  
			</TR>
		</Table> 
	</DIV>
	</div>
	<div>
		<a href="javascript:void(0)" class=button onclick="easyQueryClickSelf();">查  询</a>
	</div>
	<!-- <INPUT class=cssButton VALUE="查  询" TYPE=button onclick="easyQueryClickSelf();"> -->
	<Table>
		<tr>
		<td class=common>
		    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfPolGrid);">
		</td>
		<td class= titleImg>待处理保单</td>
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
		<INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage2.firstPage();"> 
		<INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage2.previousPage();"> 					
		<INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage2.nextPage();"> 
		<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage2.lastPage();">
		</div>     
	</Div>

	<!--#########################  隐藏表单区域   ##############################-->
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


