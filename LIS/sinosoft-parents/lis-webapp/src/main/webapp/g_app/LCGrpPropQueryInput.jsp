<%
/***************************************************************
 * <p>ProName��LCGrpPropQueryInput.jsp</p>
 * <p>Title��Ͷ������ѯ</p>
 * <p>Description��Ͷ������ѯ</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-08-04
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var tManageCom = "<%= tGI.ManageCom %>";
	var tComCode="<%=tGI.ComCode%>";
	var tOperator="<%=tGI.Operator%>";
</script>
<html>
<head>
	<title>Ͷ������ѯ</title>
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
	<script src="./LCGrpPropQueryInput.js"></script>
	<%@include file="./LCGrpPropQueryInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryInfo);">
			</td>
			<td class=titleImg>��ѯ����</td>
		</tr>
	</table>
	<div id="divQueryInfo" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>�������</td>
				<td class=input><input class=codeno name=ManageCom id=ManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('managecom',[this,ManageComName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('managecom',[this,ManageComName],[0,1],null,null,null,1);"><input class=codename name=ManageComName readonly ></td>
				<td class=title>Ͷ������</td>
				<td class=input><input class="wid common" name=GrpPropNo id=GrpPropNo></td>
				<td class=title>��������</td>
				<td class=input><input class=codeno name=Salechnl id=Salechnl style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('agenttype',[this,SalechnlName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('agenttype',[this,SalechnlName],[0,1],null,null,null,1);"><input class=codename name=SalechnlName readonly ></td>
			</tr>
			<tr class=common>
				<td class=title>�ͻ�����</td>
				<td class=input><input class=codeno name=AgentCode id=AgentCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('agentcode',[this,AgentCodeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('agentcode',[this,AgentCodeName],[0,1],null,null,null,1);"><input class=codename name=AgentCodeName readonly ></td>
				<td class=title></td>
				<td class=input></td>		
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		
		<input class=cssButton value="��  ѯ" type=button onclick="queryClick();">
		<input class=cssButton value="��  ��" type=button onclick="resetClick();">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divQueryResult);">
			</td>
			<td class=titleImg>��ѯ���</td>
		</tr>
	</table>
	<div id="divQueryResult" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanQueryResultGrid"></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
		</center>
		
		<input class=cssButton value="����Ͷ������ϸ" type=button onclick="policyQuery();">
	</div>
	
	<Input type=hidden name=Operate>
	<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
