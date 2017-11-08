<%
/***************************************************************
 * <p>ProName��EdorUWInsuredQueryInput.jsp</p>
 * <p>Title����Ա�嵥��ѯ</p>
 * <p>Description����Ա�嵥��ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");	
	String tGrpContNo = request.getParameter("GrpContNo");
	String tEdorNo = request.getParameter("EdorNo");
	String tEdorAppNo = request.getParameter("EdorAppNo");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tEdorNo =  "<%=tEdorNo%>";
	var tEdorAppNo =  "<%=tEdorAppNo%>";
		
</script>
<html>
<head >
	<title>��Ա�嵥��ѯ</title>
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
	<script src="./EdorUWInsuredQueryInput.js"></script>
	<%@include file="./EdorUWInsuredQueryInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
		<table>
			<tr>
				<td class=common>
					<img  src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divQueryUW);">
				</td>
				<td class=titleImg>�������ѯ����</td>
			</tr>
		</table>
	<div id="divQueryUW" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>����</td>
				<td class=input><input class="wid common" id=InsuredName name=InsuredName></td>
				<td class=title>֤������</td>
				<td class=input><input class="wid common" id=IDNo name=IDNo></td>
				<td class=title>���շ���</td>
				<td class=input><Input class="codeno" name="ContPlanCode" id=ContPlanCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showContPlanCode(this,ContPlanCodeName,sysPlanCode);" onkeyup="showContPlanCodeName(this,ContPlanCodeName,sysPlanCode);"><input class=codename name=ContPlanCodeName ><input type=hidden name=sysPlanCode ></td>
			</tr>
			<tr class=common>
				<td class=title>��ȫ��Ŀ</td>
				<td class=input><input class=codeno name=EdorType id=EdorType readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('edorcode',[this, EdorTypeName, HidEdorLevel, HidValDateFlag],[0, 1, 2, 3],null,'1 and b.appobj in(#G#,#C#) and exists(select 1 from lpedorapp m,lcgrppol n where n.grpcontno=m.otherno and n.riskcode=a.riskcode and m.edoracceptno=#<%=tEdorAppNo%>#)','1','1',180);" 
					onkeyup="return showCodeListKey('edorcode',[this, EdorTypeName, HidEdorLevel, HidValDateFlag],[0, 1, 2, 3],null,'1 and b.appobj in(#G#,#C#) and exists(select 1 from lpedorapp m,lcgrppol n where n.grpcontno=m.otherno and n.riskcode=a.riskcode and m.edoracceptno=#<%=tEdorAppNo%>#)','1','1',180);"><input class=codename name=EdorTypeName readonly elementtype=nacessary></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton value="��  ѯ" type=button onclick="queryInsured()">
		<input class=cssButton value="���շ�������" type=button onclick="queryTermInsured('Grade')">
		<input class=cssButton value="������������" type=button onclick="queryTermInsured('Age')">
		<input class=cssButton value="���ձ�������" type=button onclick="queryTermInsured('Amnt')">
		<input class=cssButton value="��  ��" type=button onclick="returnBack();">
		
		<table>
			<tr>
				<td class=common>
					<img  src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divResult);">
				</td>
				<td class=titleImg>��ѯ���</td>
			</tr>
		</table>
			<div id="divResult" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanUWInsuredGrid"></span>
					</td>
				</tr>
			</table>
			<center>
				<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
				<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
				<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
				<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
				<input class=cssButton type=button value="��������" onclick="exportData()">
			</center>
		</div>	
	</div>
	
	<!--������-->
	<input type=hidden name=Operate>
	<input type=hidden name=GrpContNo value="<%=tGrpContNo%>">
	<input type=hidden name=HidEdorLevel>
	<input type=hidden name=HidValDateFlag>
	<Br /><Br /><Br /><Br />
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
