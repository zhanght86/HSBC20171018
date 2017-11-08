<html>
<%
//程序名称：DirectContInput.jsp
//程序功能：直销险种录入界面
//创建日期： 2006-1-20 10:13
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@include file="../common/jsp/UsrCheck.jsp" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>

<script language="javascript">
	//从服务器端取得数据:
	var	MissionID = "<%=request.getParameter("MissionID")%>";
	var	SubMissionID = "<%=request.getParameter("SubMissionID")%>";
	var ActivityID = "<%=request.getParameter("ActivityID")%>";
	var ContNo = "<%=request.getParameter("ContNo")%>";
	var prtNo = "<%=request.getParameter("prtNo")%>";
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var NoType = "<%=request.getParameter("NoType")%>"; 
	var ManageCom = "<%=request.getParameter("ManageCom")%>";
	var scantype = "<%=request.getParameter("scantype")%>";
	var ScanFlag = "<%=request.getParameter("ScanFlag")%>";
	if (ScanFlag == "null") ScanFlag = "0";
	var LoadFlag= "<%=request.getParameter("LoadFlag")%>"
	var ContType = 1;
	var BQFlag = "<%=request.getParameter("BQFlag")%>";
	if (BQFlag == "null") BQFlag = "0";
	var EdorType = "<%=request.getParameter("EdorType")%>";
	var checktype = "1";
</script>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>		 
	<SCRIPT src="../common/javascript/VerifyWorkFlow.js"></SCRIPT>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>	
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="ProposalAutoMove4.js"></script>
	<script src="TabAutoScroll.js"></script>
	<Script src="DirectContInput.js"></Script>
	<%@include file="DirectContInit.jsp"%>
	<%
	if(request.getParameter("scantype")!=null&&request.getParameter("scantype").equals("scan"))
	{
	%>
	<script src="../common/EasyScanQuery/ShowPicControl.js"></script>
	<script language="javascript">window.document.onkeydown = document_onkeydown;</script>
	<%
	}
	%>  
</head>  

<body  onload="initForm();initElementtype();window.document.ondblclick=AutoTab;" >
<form  method=post name=fm id=fm target="fraSubmit">
	<!--#######################引入合同信息录入控件###############################--->
	<jsp:include page="DirectContInfoPage.jsp"/>
	<!--#######################引入投保人信息录入控件#############################--->
	<jsp:include page="DirectContAppntPage.jsp"/>	
	<!--#######################引入保存合同及投保人信息按钮控件#####################--->
	<div  id= "divContInfoSaveButton" style= "display:none">
	 	<table  class=common align=center>
		<tr align=right>
			<td class=button >&nbsp;&nbsp;</td>
			<td class=button width="10%" align=right>
				<input class=cssButton name="addbutton"    VALUE="保  存"  TYPE=button onclick="addClick();">
			</td>
			<td class=button width="10%" align=right>
				<input class=cssButton name="updatebutton" VALUE="修  改"  TYPE=button onclick="updateClick();">
			</td>
			<td class=button width="10%" align=right>
				<input class=cssButton name="deletebutton" VALUE="删  除"  TYPE=button onclick="deleteClick();">
			</td> 
		</tr>	
		</table>   
    </div>   
    
	<!--#################################引入被保人列表控件#################################--->
	<div  id= "divInsuredGrid" style= "display:none"> 
		<table class=common>
			<tr>
				<td style=text-align:left colSpan=1><span id="spanInsuredGrid" > </span></td>
			</tr>
		</Table>
	</div>
	<!--#################################引入被保人信息录入控件#################################--->
	<jsp:include page="DirectContInsurdPage.jsp"/>	

	<!--#################################引入保存被保人信息按钮控件#############################--->
	<div  id= "divContInsurdSaveButton" style= "display:none" align=right>
      <input type="button" class="cssButton" value="添加被保险人" onclick="addRecord();"> 
      <input type="button" class="cssButton" value="修改被保险人" onclick="modifyRecord();">
      <input type="button" class="cssButton" value="删除被保险人" onclick="deleteRecord();"> 
    </div>
 
	<!---#################################以下为被保险人险种信息列表###########################--->
	<table>
		<tr>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCPol);">
			</td>
			<td class= titleImg>被保险人险种信息</td>
			<TD><Input type="hidden" class="cssButton" name="HiddenInsuredPol" value="被保险人险种信息"></TD>
		</tr>
	</table>
	<div  id= "DivLCPol" style= "display: ''">
		<table  class= common>
			<tr  class= common>
				<td style="text-align:left" colSpan=1><span id="spanPolGrid" ></span></td>
			</tr>
		</table>
	</div>

	<!---##############################以下为录单界面【 录单按钮 】###########################---->
	<br>
	<div  id= "divInputContButton" style="display:none;float: left">
		<input class=cssButton id="" VALUE="影像件查询"   TYPE=button onclick="QuestPicQuery();">	
		<input class=cssButton id="" VALUE="问题件录入"   TYPE=button onclick="QuestInput();">
		<input class=cssButton id="" VALUE="问题件查询"   TYPE=button onclick="QuestQuery();">
		<input class=cssButton id="" VALUE="险种信息录入" TYPE=button onclick="intoRiskInfo();">
		<input class=cssButton id="" VALUE="险种删除"    TYPE=button onclick="DelRiskInfo();">
		<input class=cssButton id="" VALUE="记事本查看"   TYPE=button name="NotePadButton1" onclick="showNotePad();">
		<input class=cssButton id="" VALUE="录入完毕"    TYPE=button onclick="inputConfirm(1)">
	</div>
	<!--##############################以下为录单界面【 复核按钮 】##############################--->
	<div id = "divApproveContButton" style = "display:none;float: left">
		<table>
		<tr><td>
		<input class=cssButton id="" VALUE=" 进入险种信息 " TYPE=button onclick="intoRiskInfo();">	
		<input class=cssButton id="" VALUE=" 问题件录入 " TYPE=button onClick="QuestInput();">
		<input class=cssButton id="" VALUE=" 投保人校验 " TYPE=button name="AppntChkButton" onclick='AppntChk();'>
		<input class=cssButton id="" VALUE=" 被保人校验 " TYPE=button name="InsuredChkButton" onclick='InsuredChk();' >
		</td></tr>
		<tr><td>
		<input class=cssButton id="" VALUE="影像件查询" TYPE=button onclick="QuestPicQuery();">	
		<input class=cssButton id="" VALUE="问题件查询" TYPE=button name="ApproveQuestQuery"  onclick="QuestQuery();">	
		<input class=cssButton id="" VALUE="记事本查看" TYPE=button name="NotePadButton5" onclick="showNotePad();">
		<input class=cssButton id="" VALUE=" 复核完毕 " TYPE=button onclick="inputConfirm(2);">
		</td></tr>
		</table>
	</div>
	
<!--#################################个险问题件修改按钮 #################################-->
    <div id = "divProblemModifyContButton" style = "display:none;float:left">
		<table>
		<tr><td>
			<input class=cssButton id="" VALUE="进入险种信息" TYPE=button onclick="intoRiskInfo();">
			<input class=cssButton id="" VALUE="险种删除" TYPE=button onclick="DelRiskInfo();">
			<input class=cssButton id="" name="AppntChkButton2" VALUE=投保人校验 TYPE=button onclick='AppntChk();'>
			<input class=cssButton id="" name="InsuredChkButton2" VALUE=被保人校验 TYPE=button onclick='InsuredChk();'>
			<input class=cssButton id="" VALUE="问题件回复 " TYPE=button onclick="QuestQuery();">
		</td></tr>
		<tr><td>
			<input class=cssButton id="" VALUE="记事本查看" name="NotePadButton3" TYPE=button onclick="showNotePad();">
			<input class=cssButton id="" VALUE="  问题件影像查询 " TYPE=button onclick="QuestPicQuery();">
			<input class=cssButton id="" VALUE="  问题件修改完毕 " TYPE=button onclick="inputConfirm(3);">
		</td></tr>
		</table>
	</div>
	<!--################################随动定制按钮 #######################################-->	    
    <div id="autoMoveButton" style="display:none">
		<input type="button" name="autoMoveInput" value="随动定制确定" onclick="submitAutoMove('11');" class="cssButton">
		<input type="button" name="Next" value="下一步" onclick="location.href='ContInsuredInput.jsp?LoadFlag='+LoadFlag+'&checktype=1&prtNo='+prtNo+'&scantype='+scantype" class="cssButton">
		<input class=cssButton id="queryintoriskbutton2" VALUE="进入险种信息" TYPE=button onclick="intoRiskInfo();">
		<input value="返  回" class="cssButton" type="button" onclick="top.close();">
		<input type=hidden name="autoMoveFlag">
		<input type=hidden name="autoMoveValue">
		<input type=hidden name="pagename" value="11">
    </div>
	<!--#################################查询投保单按钮 #####################################-->
    <div id = "divInputQuery" style="display:none;float:left">
      <input class=cssButton id="queryintoriskbutton" VALUE="进入险种信息" TYPE=button onclick="intoRiskInfo();">
    </div>

	
	
	<!---#################################隐藏控件位置#################################--->
	<div>
	<input type="hidden" id="fmAction" name="fmAction"><!----按钮动作标识----->
	<input type="hidden" id="WorkFlowFlag" name="WorkFlowFlag">
	<input type="hidden" id="MissionID" name="MissionID" ><!-- 工作流任务ID -->
	<input type="hidden" id="SubMissionID" name="SubMissionID" ><!-- 工作流子任务ID -->
	<input type="hidden" id="ActivityID" name="ActivityID" ><!-- 工作流任务节点号 -->
	<input type='hidden' id="GrpContNo" name="GrpContNo" value="00000000000000000000">
	<input type="hidden" id="ContNo" name="ContNo"  >
	<input type="hidden" id="ProposalGrpContNo" name="ProposalGrpContNo">
	<input type='hidden' id="ContType" name="ContType">
	<input type="hidden" name="SelPolNo" value="">
	<input type='hidden' id="MakeDate" name="MakeDate">
    <input type='hidden' id="MakeTime" name="MakeTime">
	<input type="hidden" id="BQFlag" name="BQFlag">  
	<!------############# FamilyType:0-个人，1-家庭单 #######################--------->
	<input type="hidden" name=FamilyType value="1">
	
    <input type="hidden" id="RollSpeedSelf" name="RollSpeedSelf">
    <input type="hidden" id="RollSpeedBase" name="RollSpeedBase">
    <input type="hidden" id="RollSpeedOperator" name="RollSpeedOperator">   
    <input type="hidden" id="Operator" name="Operator"> 
	</div>  
	<Br /><Br /><Br /><Br />
</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>


