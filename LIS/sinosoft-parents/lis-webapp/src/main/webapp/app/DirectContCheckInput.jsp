<html>
<%
//�������ƣ�DirectContInput.jsp
//�����ܣ�ֱ������¼�����
//�������ڣ� 2006-1-20 10:13
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@include file="../common/jsp/UsrCheck.jsp" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>

<script language="javascript">
	//�ӷ�������ȡ������:
	var	MissionID = "<%=request.getParameter("MissionID")%>";
	var	SubMissionID = "<%=request.getParameter("SubMissionID")%>";
	var ActivityID = "<%=request.getParameter("ActivityID")%>";
	var ContNo = "<%=request.getParameter("ContNo")%>";
	var prtNo = "<%=request.getParameter("prtNo")%>";
	var NoType = "<%=request.getParameter("NoType")%>"; 
	var ManageCom = "<%=request.getParameter("ManageCom")%>";
	var scantype = "<%=request.getParameter("scantype")%>";
	var ScanFlag = "<%=request.getParameter("ScanFlag")%>";
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
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="ProposalAutoMove4.js"></script>
	<Script src="DirectContCheckInput.js"></Script>
	<%@include file="DirectContCheckInit.jsp"%>
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

<body  onload="initForm();initElementtype();" >
<form  method=post name=fm id=fm target="fraSubmit">
	<!--#######################�����ͬ��Ϣ¼��ؼ�###############################--->
	<jsp:include page="DirectContInfoPage.jsp"/>
	<hr>
	<!--#######################����Ͷ������Ϣ¼��ؼ�#############################--->
	<jsp:include page="DirectContAppntPage.jsp"/>	
	<hr>
	<!--#######################���뱣���ͬ��Ͷ������Ϣ��ť�ؼ�#####################--->

	<!--#################################���뱻�����б�ؼ�#################################--->
	<div  id= "divInsuredGrid" style= "display:none"> 
		<table class=common>
			<tr>
				<td text-align: left colSpan=1><span id="spanInsuredGrid" > </span></td>
			</tr>
		</Table>
	</div>
	<!--#################################���뱻������Ϣ¼��ؼ�#################################--->
	<jsp:include page="DirectContInsurdPage.jsp"/>	
	<hr>
	<!--#################################���뱣�汻������Ϣ��ť�ؼ�#############################--->
	
	<!---#################################����Ϊ��������������Ϣ�б�###########################--->
	<table>
		<tr>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCPol);">
			</td>
			<td class= titleImg>��������������Ϣ</td>
			<TD><Input type="button" class="cssButton" name="HiddenInsuredPol" value="��������������Ϣ"></TD>
		</tr>
	</table>
	<div  id= "DivLCPol" style= "display: ''">
		<table  class= common>
			<tr  class= common>
				<td style="text-align:left" colSpan=1><span id="spanPolGrid" ></span></td>
			</tr>
		</table>
	</div>
	<hr>
	<!--VVVVVVVVVVVVVVVVVVVVVVVV ��������Ϣ���֣��б� VVVVVVVVVVVVVVVVVVVVVVVV-->
		<table>
			<tr>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCBnfGrid);">
			</td>
			<td class= titleImg>��������Ϣ</td>
			<TD><Input type="button" class="cssButton" name="HiddenLCBnf" value="��������Ϣ"></TD>
			</tr>
		</table>
		<div id= "divLCBnfGrid" style= "display: ''" >
			<table class= common>
				<tr class= common>
					<td style="text-align: left" colSpan=1><span id="spanLCBnfGrid" ></span></td>
				</tr>
			</table>
		</div>
    <hr>
	<!--##############################����Ϊ¼�����桾 ���˰�ť ��##############################--->
	<br>
	<div id = "divApproveContButton" style = "display:none;float: left">
		<table>
		<tr><td>
		<input class=cssButton id="" VALUE=" �����¼�� " TYPE=button onClick="QuestInput();">
		<input class=cssButton id="" VALUE=" Ͷ����У�� " TYPE=button name="AppntChkButton" onclick='AppntChk();'>
		<input class=cssButton id="" VALUE=" ������У�� " TYPE=button name="InsuredChkButton" onclick='InsuredChk();' >
		<input class=cssButton id="" VALUE=" ����������Ϣ " TYPE=button onclick="intoRiskInfo();">	
		</td></tr>
		<tr><td>
		<input class=cssButton id="" VALUE="Ӱ�����ѯ" TYPE=button onclick="QuestPicQuery();">	
		<input class=cssButton id="" VALUE="�������ѯ" TYPE=button name="ApproveQuestQuery"  onclick="QuestQuery();">	
		<input class=cssButton id="" VALUE="���±��鿴" TYPE=button name="NotePadButton5" onclick="showNotePad();">
		<input class=cssButton id="" VALUE=" ������� " TYPE=button onclick="inputConfirm(2);">
		</td></tr>
		</table>
	</div>
	

	<!--################################�涯���ư�ť #######################################-->	    
    <div id="autoMoveButton" style="display:none">
		<input type="button" name="autoMoveInput" value="�涯����ȷ��" onclick="submitAutoMove('11');" class="cssButton">
		<input type="button" name="Next" value="��һ��" onclick="location.href='ContInsuredInput.jsp?LoadFlag='+LoadFlag+'&checktype=1&prtNo='+prtNo+'&scantype='+scantype" class="cssButton">
		<input class=cssButton id="queryintoriskbutton2" VALUE="����������Ϣ" TYPE=button onclick="intoRiskInfo();">
		<input value="��  ��" class="cssButton" type="button" onclick="top.close();">
		<input type=hidden name="autoMoveFlag">
		<input type=hidden name="autoMoveValue">
		<input type=hidden name="pagename" value="11">
    </div>
	<!--#################################��ѯͶ������ť #####################################-->
    <div id = "divInputQuery" style="display:none;float:left">
      <input class=cssButton id="queryintoriskbutton" VALUE="����������Ϣ" TYPE=button onclick="intoRiskInfo();">
    </div>

	
	
	<!---#################################���ؿؼ�λ��#################################--->
	<div>
	<input type="hidden" id="fmAction" name="fmAction"><!----��ť������ʶ----->
	<input type="hidden" id="WorkFlowFlag" name="WorkFlowFlag">
	<input type="hidden" id="MissionID" name="MissionID" ><!-- ����������ID -->
	<input type="hidden" id="SubMissionID" name="SubMissionID" ><!-- ������������ID -->
	<input type="hidden" id="ActivityID" name="ActivityID" ><!-- ����������ڵ�� -->
	<input type='hidden' id="GrpContNo" name="GrpContNo" value="00000000000000000000">
	<input type="hidden" id="ContNo" name="ContNo"  >
	<input type="hidden" id="ProposalGrpContNo" name="ProposalGrpContNo">
	<input type='hidden' id="ContType" name="ContType">
	<input type="hidden" name="SelPolNo" value="">
	<input type='hidden' id="MakeDate" name="MakeDate">
    <input type='hidden' id="MakeTime" name="MakeTime">
	<input type="hidden" id="BQFlag" name="BQFlag">  
	<!------############# FamilyType:0-���ˣ�1-��ͥ�� #######################--------->
	<input type="hidden" name=FamilyType value="1">
	</div>  
	<Br /><Br /><Br /><Br />
</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>


