<%
/***************************************************************
 * <p>ProName��EdorQueryInput.jsp</p>
 * <p>Title����ȫ��ѯ</p>
 * <p>Description����ȫ��ѯ</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-08-04
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tGrpContNo = request.getParameter("GrpContNo");
	String tFlag = request.getParameter("Flag"); 
%>
<script>
	var tManageCom="<%=tGI.ManageCom%>";
	var tComCode="<%=tGI.ComCode%>";
	var tOperator="<%=tGI.Operator%>";
	var tGrpContNo = "<%=tGrpContNo%>";
	var tFlag =  "<%=tFlag%>";
	
</script>
<html>
<head>
	<title>��ȫ��ѯ</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<script src="../common/laydate/laydate.js"></script>
	<script src="../common/Calendar/Calendar.js"></script>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./EdorQueryInput.js"></script>
	<%@include file="./EdorQueryInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>

 <div id="divShowQueryInfo" style="display: ''">
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryInfo);">
			</td>
			<td class=titleImg>��ѯ����</td>
		</tr>
	</table>
	<div id="divQueryInfo" class=maxbox style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>��ȫ�����</td>
				<td class=input><input class="wid common" name=EdorAppNo id=EdorAppNo></td>
				<td class=title>������</td>
				<td class=input><input class="wid common" name=GrpContNo id=GrpContNo></td>
				<td class=title>�ͻ���</td>
				<td class=input><input class="wid common" name=AppntNo id=AppntNo></td>
			</tr>
			<tr class=common>
				<td class=title>�������</td>
				<td class=input><input class=codeno name=ManageCom id=ManageCom verify="�б�����|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('managecom',[this,ManageComName],[0,1],null,null,null,null,180);" onkeyup="return showCodeListKey('managecom',[this,ManageComName],[0,1],null,null,null,null,180);"><input class=codename name=ManageComName readonly elementtype=nacessary></td>
				<td class=title>��������</td>
				<td class=input><Input class="coolDatePicker" dateFormat="short"  name=AcceptDATE verify="��������|date" onClick="laydate({elem: '#AcceptDATE'});" id="AcceptDATE"><span class="icon"><a onClick="laydate({elem: '#AcceptDATE'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>Ͷ��������</td>
				<td class=input><input class="wid common" name=GrpName id=GrpName></td>
			</tr>
			<tr class=common>
				<td class=title>����������ϸ</td>
				<td class=input><input class=codeno name=SaleChnl style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('agenttype',[this,SaleChnlName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('agenttype',[this,SaleChnlName],[0,1],null,null,null,1);"><input class=codename name=SaleChnlName readonly></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton value="��  ѯ" type=button onclick="queryClick(1);">
	</div>
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
		
		<input class=cssButton id=CloseButton name=CloseButton  style= "display: none" type=button value="��  ��" onclick="top.close();">
	</div>
	
	<div id="divQueryEdorDetail" style="display: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divQueryDetail);">
				</td>
				<td class=titleImg>��ȫ������Ϣ</td>
			</tr>
		</table>
		<div id="divQueryDetail" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanQueryDetailGrid"></span>
					</td>
				</tr>
			</table>
		</div>
		<br/>
		<input class=cssButton value="��ϸ��ѯ" type=button onclick="policyDetClick();">
	</div>
	
	
	<Input type=hidden name=Operate id=Operate>
	<br /><br /><br /><br /><br />
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
