<%
/***************************************************************
 * <p>ProName:EdorSFInput.jsp</p>
 * <p>Title:  ���������ո���</p>
 * <p>Description:���������ո���</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2015-01-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tEdorAppNo = request.getParameter("EdorAppNo");
	String tGrpContNo = request.getParameter("GrpContNo");
	String tEdorType = request.getParameter("EdorType");
	String tEdorNo = request.getParameter("EdorNo");
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
	var tEdorNo = "<%=tEdorNo%>";
</script>
<html>
<head>
	<title>���������ո���</title>
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
	<script src="./EdorSFInput.js"></script>
	<%@include file="./EdorSFInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
<div id="divQueryOld" style="display: ''">
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
			<td class=titleImg>���������ո���</td>
		</tr>
	</table>
	<div id="divQuery" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>  
				<td class=title>�ո�������</td>
				<td class=input><input class=codeno name=GetType id=GetType readonly verify="�ո�������|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('queryexp',[this, GetTypeName],[0, 1],null,'surrenderSF','concat(codetype,codeexp)','1',180);"
					onkeyup="return showCodeListKey('queryexp',[this, GetTypeName],[0, 1],null,'surrenderSF','concat(codetype,codeexp)','1',180);"><input class=codename name=GetTypeName id=GetTypeName readonly></td>
				<td class=title>�ո��ѽ��</td>
				<td class=input><input class="wid common" id=GetMoney name=GetMoney verify="�ո��ѽ��|NOTNULL&VALUE>0" maxlength=10></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		
		<input class=cssButton type=button name=SaveButton value="��  ��" onclick="saveClick();">
		<input class=cssButton type=button name=CloseButton value="��  ��" onclick="top.close();">
	</div>
</div>
	
	<!--������-->
	<input type=hidden name=Operate>
	<input type=hidden name=GrpContNo>
	<input type=hidden name=CurrentDate>
	<input type=hidden name=EdorAppNo>
	<input type=hidden name=EdorNo>
	<input type=hidden name=BatchNo>
	<input type=hidden name=InsuredID>
	<input type=hidden name=MissionID> <!-- ��������ID -->
	<input type=hidden name=SubMissionID> <!-- �ӹ�������ID -->
	<input type=hidden name=ActivityID> <!-- �����ڵ�ID -->
</form>
<br /><br /><br /><br /><br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
