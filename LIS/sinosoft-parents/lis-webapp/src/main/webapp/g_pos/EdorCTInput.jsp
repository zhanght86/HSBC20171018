<%
/***************************************************************
 * <p>ProName:EdorCTInput.jsp</p>
 * <p>Title:  �����˱�</p>
 * <p>Description:�����˱�</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-06-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tCurrentDate= PubFun.getCurrentDate();
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tEdorAppNo = request.getParameter("EdorAppNo");
	String tEdorType = request.getParameter("EdorType");
	String tGrpContNo = request.getParameter("GrpContNo");
	String tEdorNo =  request.getParameter("EdorNo");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tEdorAppNo = "<%=tEdorAppNo%>";
	var tGrpContNo = "<%=tGrpContNo%>";
	var tEdorType = "<%=tEdorType%>";
	var tEdorNo =  "<%=tEdorNo%>";
</script>
<html>
<head>
	<title>�����˱�</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./EdorCTInput.js"></script>
	<%@include file="./EdorCTInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
			<td class=titleImg>�����˱�</td>
		</tr>
	</table>
	<div id="divQuery" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>�˱���ʽ</td>
				<td class=input><input class=codeno name=BackType id=BackType readonly verify="�˱���ʽ|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('queryexp',[this, BackTypeName],[0, 1],null,'surrenderCT','concat(codetype,codeexp)','1',180);" 
					onkeyup="return showCodeListKey('queryexp',[this, BackTypeName],[0, 1],null,'surrenderCT','concat(codetype,codeexp)','1',180);"><input class=codename name=BackTypeName id=BackTypeName elementtype=nacessary></td>
				<td class=title ><div id=divCalName style="display:none" >�˱��㷨</div></td>
				<td class=input ><div id=divCalCode style="display:none"><input class=codeno name=BackCal id=BackCal readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('edorcalcode',[this, BackCalName],[0, 1],null,null,null,'1',180);" 
					onkeyup="return showCodeListKey('edorcalcode',[this, BackCalName],[0, 1],null,null,null,'1',180);"><input class=codename name=BackCalName id=BackCalName elementtype=nacessary></div></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>�˱�ԭ������</td>
				<td class=input colspan=5><textarea cols=50 rows=3 name=ReasonDesc id=ReasonDesc verify="�˱�ԭ������|NOTNULL" elementtype=nacessary></textarea></td>
			</tr>
		</table>
		<div id="divButton01" style="display: none">
			<input class=cssButton type=button value="��  ��" onclick="saveClick();">
			<input class=cssButton type=button value="��  ��" onclick="top.close();">
		</div>
		<div id="divButton02" style="display: none">
			<input class=cssButton type=button value="��  ��" onclick="top.close();">
		</div>
	</div>

	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divInsuredInfoUp);">
			</td>
			<td class=titleImg>�˱��������˲�ѯ����</td>
		</tr>
	</table>
	<div id="divInsuredInfoUp" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>������������</td>
				<td class=input><input class="wid common" id=InsuredName name=InsuredName></td>
				<td class=title>֤������</td>
				<td class=input><input class="wid common" id=InsuredIDNo name=InsuredIDNo></td>
				<td class=title>�������˿ͻ���</td>
				<td class=input><input class="wid common" id=InsuredNo name=InsuredNo></td>
			</tr>
		</table>
		<input class=cssButton type=button value="��  ѯ" onclick="queryClick();">		
	</div>
	<br>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divResultUpdate);">
			</td>
			<td class=titleImg>�˱�����������Ϣ</td>
		</tr>
	</table>
	<div id="divResultUpdate" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanUpdateInsuredInfoGrid" ></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
		</center>
	</div>
	
	<!--������-->
	<input type=hidden name=Operate id=Operate>
	<input type=hidden name=GrpContNo id=GrpContNo>
	<input type=hidden name=CurrentDate id=CurrentDate>
	<input type=hidden name=EdorAppNo id=EdorAppNo>
	<input type=hidden name=EdorNo id=EdorNo>
	<input type=hidden name=MissionID id=MissionID> <!-- ��������ID -->
	<input type=hidden name=SubMissionID id=SubMissionID> <!-- �ӹ�������ID -->
	<input type=hidden name=ActivityID id=ActivityID> <!-- �����ڵ�ID -->
<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
