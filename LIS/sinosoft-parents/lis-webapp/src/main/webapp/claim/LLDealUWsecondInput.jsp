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
<title>���˽���</title>
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
		<TD class=titleImg>������˵�δ�˱���ɵĺ�ͬ���б�</TD>
	</TR>
</Table>
<Div id="divUnfinishedCont" align=center style="display: ''">
<Table class=common>
	<TR class=common>
		<TD text-align: left colSpan=1><span
			id="spanLLUnfinishedContGrid"></span></TD>
	</TR>
</Table>
<Input VALUE="��  ҳ" class=cssButton90 TYPE=button
	onclick="turnPage1.firstPage();"> <Input VALUE="��һҳ"
	class=cssButton91 TYPE=button onclick="turnPage1.previousPage();">
<Input VALUE="��һҳ" class=cssButton92 TYPE=button
	onclick="turnPage1.nextPage();"> <Input VALUE="β  ҳ"
	class=cssButton93 TYPE=button onclick="turnPage1.lastPage();">
</Div>

<Table>
	<TR>
		<TD class="common"><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,peer);"></TD>
		<TD class=titleImg>�Ѿ����κ˱���ɵĺ�ͬ���б�</TD>
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
		<TD class=titleImg>ѡ�еĺ�ͬ���Ķ��κ˱���Ϣ</TD>
	</TR>
</Table>
<Div id="divContInfo" style="display: ''" class="maxbox">
<Table class=common>
	<TR class=common>
		<TD class=title>Ͷ���齡����֪��ѯ�ʺ�</TD>
	</TR>
	<TR class=common>
		<TD style="padding-left:16px" colspan="6"><textarea name="HealthImpartNo1" cols="197"
			rows="4" witdh=25% class="common" readonly></textarea></TD>
	</TR>
	<TR class=common>
		<TD class=title>��콡����֪��ѯ�ʺ�</TD>
	</TR>
	<TR class=common>
		<TD style="padding-left:16px" colspan="6"><textarea name="HealthImpartNo2" cols="197"
			rows="4" witdh=25% class="common" readonly></textarea></TD>
	</TR>
	<TR class=common>
		<TD class=title>��Ӧδ��֪���</TD>
	</TR>
	<TR class=common>
		<TD style="padding-left:16px" colspan="6"><textarea name="NoImpartDesc" cols="197" rows="4"
			witdh=25% class="common" readonly></textarea></TD>
	</TR>
	<TR class=common>
		<TD class=title>������Ŀǰ����״������</TD>
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
		<TD class=title>��ͬ�˱�����</td>
		<td class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name="PassFlag" id="PassFlag"
			ondblclick="return showCodeList('contuwstate',[this,PassFlagname],[0,1]);"
            onclick="return showCodeList('contuwstate',[this,PassFlagname],[0,1]);"
			onkeyup="return showCodeListKey('contuwstate',[this,PassFlagname],[0,1]);"><Input
			class=codename name=PassFlagname id=PassFlagname readonly></td>
		<td class=title>�˱���</td>
        
		<td class=input><Input class="readonly wid" readonly
			name="UWOperator" id="UWOperator"></td>
            <td class=title>�˱�����</td>
		<td class=input><Input class="readonly wid" readonly name="UWDate" id="UWDate"></td>
            </tr>
           
</Table>
<Table class=common>
	<TR class=common>
		<TD class=title>��ͬ�˱����</TD>
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
		<TD class=title>�����ͬ�˱�����</td>
		<td class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name="uwContState" id="uwContState"
			ondblclick="return showCodeList('contuwstate',[this,uwContStatename],[0,1]);"
            onclick="return showCodeList('contuwstate',[this,uwContStatename],[0,1]);"
			onkeyup="return showCodeListKey('contuwstate',[this,uwContStatename],[0,1]);"><Input
			class=codename name=uwContStatename id=uwContStatename readonly></td>
		<td class=title>�˱���</td>
		<td class=input><Input class="readonly wid" readonly
			name="UWContOperator" id="UWContOperator"></td>
            <td class=title>�˱�����</td>
		<td class=input><Input class="readonly wid" readonly
			name="UWContDate" id="UWContDate"></td>
            </tr>
       
</Table>
<Table class=common>
	<TR class=common>
		<TD class=title>�����ͬ�˱����</TD>
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
		<Input VALUE="�ӷѳб���ѯ" class=cssButton TYPE=button onclick="LLUWAddFeeQuery();">
		<Input VALUE="��Լ�б���ѯ" class=cssButton TYPE=button onclick="LLUWSpecQuery();">
		<Input VALUE="�˱�֪ͨ���ѯ" class=cssButton TYPE=button onclick="LLUWNoticeQuery();">
		<Input VALUE="�ӷ�Ӧ�ղ�ѯ" class=cssButton TYPE=button onclick="LLUWLJSPayQuery();">
	</TR>
	<hr class="line">
	<TR class=common>	
		<!--Input VALUE=" �ݽ��Ѻ��� " class=cssButton TYPE=button id=cancelbutton1 onclick="CaseFeeCancel()"-->
		<Input VALUE="�˱��ӷѺ���" class=cssButton TYPE=button id=cancelbutton2 onclick="AddFeeCancel()">
		<input value="���˱�֪ͨ��" class=cssButton TYPE=button id=cancelbutton3 onclick="SendAllNotice()">
	</TR>
</Table>
</Div>

<Table>
	<TR>
		<TD class="common"><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divPol);"></TD>
		<TD class=titleImg>ѡ�еĺ�ͬ���µ����ֵ�</TD>
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
		<td class=title5>���ֺ˱�����</td>
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
		<TD class=title>���ֺ˱����</TD>
	</TR>
	<TR class=common>
		<TD style="padding-left:16px" colspan="6"><textarea name="UWRiskIdea" cols="197" rows="4"
			witdh=25% class="common" readonly></textarea></TD>
	</TR>
</Table>

<Table class=common>
	<TR class=common>
		<TD class=title>��������ԭ��</TD>
	</TR>
	<TR class=common>
		<TD style="padding-left:16px" colspan="6"><textarea name="UWRisk2" cols="197" rows="4"
			witdh=25% class="common" readonly></textarea></TD>
	</TR>
</Table>
<tr>
	<td>
		<Input class=cssButton name="CvalidateButton" type=button value="��  Ч" onclick="Cvalidate()"> 
		<Input class=cssButton name="NotCvalidateButton" type=button value="����Ч" onclick="NotCvalidate()"> 
		<Input class=cssButton name="UWConclusionCancelButton" type=button value="�������" onclick="showSecondUWInput()">
		<Input class=cssButton name="turnback" type=button value="��  ��" onclick="turnback1()">  
	</td>
</tr>

<!---���ر��������ڽ�����ҳ���������--------> 
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
