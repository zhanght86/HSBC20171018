<html>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head>
<%
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
	String tCaseNo = request.getParameter("CaseNo");
	String tInsuredNo = request.getParameter("InsuredNo");
	String tFlag = request.getParameter("Flag");
%>
<title>二核结论</title>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="LLClaimPubFun.js"></SCRIPT>
<script src="LLDealUWsecond.js"></script>
<%@include file="LLDealUWsecondInit.jsp"%>
</head>

<body onload="initForm();">
<form name=fm id=fm target=fraSubmit method=post>
<Table>
	<TR>
		<TD class="common"><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divUnfinishedCont);"></TD>
		<TD class=titleImg>发起二核但未核保完成的合同单列表</TD>
	</TR>
</Table>
<Div id="divUnfinishedCont" align=center style="display: ''">
<Table class=common>
	<TR class=common>
		<TD text-align: left colSpan=1><span
			id="spanLLUnfinishedContGrid"></span></TD>
	</TR>
</Table>
<Input VALUE="首  页" class=cssButton90 TYPE=button
	onclick="turnPage1.firstPage();"> <Input VALUE="上一页"
	class=cssButton91 TYPE=button onclick="turnPage1.previousPage();">
<Input VALUE="下一页" class=cssButton92 TYPE=button
	onclick="turnPage1.nextPage();"> <Input VALUE="尾  页"
	class=cssButton93 TYPE=button onclick="turnPage1.lastPage();">
</Div>

<Table>
	<TR>
		<TD class="common"><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,peer);"></TD>
		<TD class=titleImg>已经二次核保完成的合同单列表</TD>
	</TR>
</Table>
<Div id="peer" style="display: ''">
<Table class=common>
	<TR class=common>
		<TD text-align: left colSpan=1><span id="spanLLContGrid"></span></TD>
	</TR>
</Table>
</Div>


<Table>
	<TR>
		<TD class="common"><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divContInfo);"></TD>
		<TD class=titleImg>选中的合同单的二次核保信息</TD>
	</TR>
</Table>
<Div id="divContInfo" style="display: ''" class="maxbox">
<Table class=common>
	<TR class=common>
		<TD class=title>投保书健康告知栏询问号</TD>
	</TR>
	<TR class=common>
		<TD style="padding-left:16px" colspan="6"><textarea name="HealthImpartNo1" cols="197"
			rows="4" witdh=25% class="common" readonly></textarea></TD>
	</TR>
	<TR class=common>
		<TD class=title>体检健康告知栏询问号</TD>
	</TR>
	<TR class=common>
		<TD style="padding-left:16px" colspan="6"><textarea name="HealthImpartNo2" cols="197"
			rows="4" witdh=25% class="common" readonly></textarea></TD>
	</TR>
	<TR class=common>
		<TD class=title>对应未告知情况</TD>
	</TR>
	<TR class=common>
		<TD style="padding-left:16px" colspan="6"><textarea name="NoImpartDesc" cols="197" rows="4"
			witdh=25% class="common" readonly></textarea></TD>
	</TR>
	<TR class=common>
		<TD class=title>出险人目前健康状况介绍</TD>
	</TR>
	<TR class=common>
		<TD style="padding-left:16px" colspan="6"><textarea name="Remark1" cols="197" rows="4"
			witdh=25% class="common" readonly></textarea></TD>
	</TR>
</Table>
<br>
<div class="maxbox">
<Table class=common>
	<tr class=common>
		<TD class=title>合同核保结论</td>
		<td class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name="PassFlag" id="PassFlag"
			ondblclick="return showCodeList('contuwstate',[this,PassFlagname],[0,1]);"
            onclick="return showCodeList('contuwstate',[this,PassFlagname],[0,1]);"
			onkeyup="return showCodeListKey('contuwstate',[this,PassFlagname],[0,1]);"><Input
			class=codename name=PassFlagname id=PassFlagname readonly></td>
		<td class=title>核保人</td>
        
		<td class=input><Input class="readonly wid" readonly
			name="UWOperator" id="UWOperator"></td>
            <td class=title>核保日期</td>
		<td class=input><Input class="readonly wid" readonly name="UWDate" id="UWDate"></td>
            </tr>
           
</Table>
<Table class=common>
	<TR class=common>
		<TD class=title>合同核保意见</TD>
	</TR>
	<TR class=common>
		<TD style="padding-left:16px" colspan="6"><textarea name="UWIdea" cols="197" rows="4"
			witdh=25% class="common" readonly></textarea></TD>
	</TR>
</Table></div>
<br>
<div class="maxbox">

<Table class=common>
	<tr class=common>
		<TD class=title>最近合同核保结论</td>
		<td class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name="uwContState" id="uwContState"
			ondblclick="return showCodeList('contuwstate',[this,uwContStatename],[0,1]);"
            onclick="return showCodeList('contuwstate',[this,uwContStatename],[0,1]);"
			onkeyup="return showCodeListKey('contuwstate',[this,uwContStatename],[0,1]);"><Input
			class=codename name=uwContStatename id=uwContStatename readonly></td>
		<td class=title>核保人</td>
		<td class=input><Input class="readonly wid" readonly
			name="UWContOperator" id="UWContOperator"></td>
            <td class=title>核保日期</td>
		<td class=input><Input class="readonly wid" readonly
			name="UWContDate" id="UWContDate"></td>
            </tr>
       
</Table>
<Table class=common>
	<TR class=common>
		<TD class=title>最近合同核保意见</TD>
	</TR>
	<TR class=common>
		<TD style="padding-left:16px" colspan="6"><textarea name="uwContIdea" cols="197" rows="4"
			witdh=25% class="common" readonly></textarea></TD>
	</TR>
</Table>
</Div>

<hr class="line">
<Div id="divCont" style="display: ''">
<Table class=common>
	<TR class=common>
		<Input VALUE="加费承保查询" class=cssButton TYPE=button onclick="LLUWAddFeeQuery();">
		<Input VALUE="特约承保查询" class=cssButton TYPE=button onclick="LLUWSpecQuery();">
		<Input VALUE="核保通知书查询" class=cssButton TYPE=button onclick="LLUWNoticeQuery();">
		<Input VALUE="加费应收查询" class=cssButton TYPE=button onclick="LLUWLJSPayQuery();">
	</TR>
	<hr class="line">
	<TR class=common>	
		<!--Input VALUE=" 暂交费核销 " class=cssButton TYPE=button id=cancelbutton1 onclick="CaseFeeCancel()"-->
		<Input VALUE="核保加费核销" class=cssButton TYPE=button id=cancelbutton2 onclick="AddFeeCancel()">
		<input value="发核保通知书" class=cssButton TYPE=button id=cancelbutton3 onclick="SendAllNotice()">
	</TR>
</Table>
</Div>

<Table>
	<TR>
		<TD class="common"><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divPol);"></TD>
		<TD class=titleImg>选中的合同单下的险种单</TD>
	</TR>
</Table>
<Div id="divPol" style="display: ''">
<Table class=common>
	<TR class=common>
		<TD text-align: left colSpan=1><span id="spanLLPolGrid"></span></TD>
	</TR>
</Table>
<div class="maxbox1">
<Table class=common>
	<TR class=common style="display:none">
		<td class=title5>险种核保结论</td>
		<td class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=UWRiskState id=UWRiskState
			ondblclick="return showCodeList('uwstate',[this,UWRiskStateName],[0,1]);"
            onclick="return showCodeList('uwstate',[this,UWRiskStateName],[0,1]);"
			onkeyup="return showCodeListKey('uwstate',[this,UWRiskStateName],[0,1]);"><Input
			class=codename name=UWRiskStateName id=UWRiskStateName readonly></td>
		<td class=title5></td>
		<td class=input5></td>
	</TR>
</Table></div>
<Table class=common>
	<TR class=common>
		<TD class=title>险种核保意见</TD>
	</TR>
	<TR class=common>
		<TD style="padding-left:16px" colspan="6"><textarea name="UWRiskIdea" cols="197" rows="4"
			witdh=25% class="common" readonly></textarea></TD>
	</TR>
</Table>

<Table class=common>
	<TR class=common>
		<TD class=title>返回理赔原因</TD>
	</TR>
	<TR class=common>
		<TD style="padding-left:16px" colspan="6"><textarea name="UWRisk2" cols="197" rows="4"
			witdh=25% class="common" readonly></textarea></TD>
	</TR>
</Table>
<tr>
	<td>
		<Input class=cssButton name="CvalidateButton" type=button value="生  效" onclick="Cvalidate()"> 
		<Input class=cssButton name="NotCvalidateButton" type=button value="不生效" onclick="NotCvalidate()"> 
		<Input class=cssButton name="UWConclusionCancelButton" type=button value="发起二核" onclick="showSecondUWInput()">
		<Input class=cssButton name="turnback" type=button value="返  回" onclick="turnback1()">  
	</td>
</tr>

<!---隐藏表单区域，用于接收上页传入的数据--------> 
<Input type="hidden" name="Operator" >
<Input type="hidden" id="CaseNo" name="CaseNo"> 
<!--Input type="hidden" id="InsuredNo" name="InsuredNo"-->
<Input type="hidden" id="InsuredName" name="InsuredName">

<Input type="hidden" id="ContNo" name="ContNo">
<Input type="hidden" id="BatNo" name="BatNo"> 
<Input type="hidden" id="InsuredNo" name="InsuredNo"> 
<Input type="hidden" id="uwContState1" name="uwContState1"> 
<Input type="hidden" id="InEffectFlag" name="InEffectFlag"> 
<Input type="hidden" id="Flag" name="Flag"> 
<input type="hidden" id="MissionID" name="MissionID" value=""> 
<input type="hidden" id="SubMissionID" name="SubMissionID" value=""> 
<span id="spanCode" style="display: none; position:absolute; slategray"></span><br><br><br><br>
</Form>
</body>
</html>
