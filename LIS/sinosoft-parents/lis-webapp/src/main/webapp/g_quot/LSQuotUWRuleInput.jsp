<%
/***************************************************************
 * <p>ProName��LSQuotUWRuleInput.jsp</p>
 * <p>Title���˱�����</p>
 * <p>Description���˱�����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-04-04
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getAttribute("GI");
	String tCurrentDate= PubFun.getCurrentDate();
	String tOperator = tGI.Operator;
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
	String tQuotType = request.getParameter("QuotType");
%>
<script>
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tQuotType = "<%=tQuotType%>";
	var tOperator = "<%=tOperator%>";
</script>
<html>
<head >
	<title>�˱�����</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<script src="../common/laydate/laydate.js"></script>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./LSQuotUWRuleInput.js"></script>
	<script src="./LSQuotCommonInput.js"></script>
	<%@include file="./LSQuotUWRuleInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LSQuotPlanCombiSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divUWRule);">
			</td>
			<td class=titleImg>�˱�����</td>
		</tr>
	</table>
	
	<div id="divdivUWRule"  style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanUWRuleGrid" ></span>
				</td>
			</tr>
		</table>
		<center>		
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
		</center>
		<input class=cssButton type=button value="��  ��" name="SaveButton" onclick="saveClick();">
	</div>
	<hr class="line"/>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divEdorRule);">
			</td>
			<td class=titleImg>��ȫ����</td>
		</tr>
	</table>
	
	<div id="divEdorRule"  style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanEdorRuleGrid" ></span>
				</td>
			</tr>
		</table>
		<center>		
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage2.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage2.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage2.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage2.lastPage();">
		</center>
	
		<table class=common>
			<tr class=common>
				<td class=title>��ȫ��������</td>
				<td class=input><input class=codeno name=RuleType id=RuleType readonly ondblclick="return showCodeList('edorruletype',[this,RuleTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('edorruletype',[this,RuleTypeName],[0,1],null,null,null,'1',null);" ><input class=codename name=RuleTypeName id=RuleTypeName elementtype=nacessary></td> 
				<td class=title id=tdRule5 name=tdRule5 style="display: none">��ȫ��Ŀ</td>
				<td class=input id=tdRule6 name=tdRule6 style="display: none"><input class=codeno name=EdorCode id=EdorCode readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('quotedortypecal',[this,EdorCodeName],[0,1],null,'1','1','1',null);" onkeyup="return showCodeListKey('quotedortypecal',[this,EdorCodeName],[0,1],null,null,null,'1',null);"><input class=codename name=EdorCodeName id=EdorCodeName  elementtype=nacessary></td>
				<td class=title id=tdRule7 name=tdRule7 style="display: none">�㷨����</td>
				<td class=input id=tdRule8 name=tdRule8 style="display: none"><input class=codeno name=CalCode id=CalCode readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('edorrulecode',[this,CalCodeName],[0,1]);" onkeyup="return returnShowCodeListKey('edorrulecode',[this,CalCodeName],[0,1]);"><input class=codename name=CalCodeName id=CalCodeName elementtype=nacessary></td>
				<td class=title id=tdRule9 name=tdRule9 style="display: none"></td>
				<td class=input id=tdRule10 name=tdRule10 style="display: none"></td>
				<td class=title id=tdRule11 name=tdRule11 style="display: none"></td>
				<td class=input id=tdRule12 name=tdRule12 style="display: none"></td>
			</tr>
		</table>
		<input class=cssButton type=button value="��  ��" name="AddButton" id=AddButton onclick="addClick();">
		<input class=cssButton type=button value="��  ��" name="ModButton" id=ModButton onclick="modClick();">
		<input class=cssButton type=button value="ɾ  ��" name="DelButton" id=DelButton onclick="delClick();">
	</div>
	<hr class="line"/>
	<input class=cssButton type=button value="��  ��" onclick="top.close();">
	<input type=hidden name=Operate id=Operate>
	<input type=hidden name=SerialNo id=SerialNo>
	<input type=hidden name=RiskCode id=RiskCode>
	<input type=hidden name=RuleCode id=RuleCode>
	<input type=hidden name=Params id=Params>
	<input type=hidden name=InputValues id=InputValues>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
