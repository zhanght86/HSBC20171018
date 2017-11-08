<%
/***************************************************************
 * <p>ProName��EdorCancelInput.jsp</p>
 * <p>Title����ȫ����</p>
 * <p>Description����ȫ����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-07-14
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
</script>
<html>
<head >
	<title>��ȫ����</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<script src="../common/Calendar/Calendar.js"></script>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./EdorCancelInput.js"></script>
	<%@include file="./EdorCancelInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=commontop>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
		 	<td class=titleImgtop>��ѯ����</td>
		</tr>
	</table>
	<div id="divQuery" style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>�������</td>
				<td class=input><input class=codeno name=Managecom readonly 
					ondblclick="return showCodeList('comcodeall',[this, ManagecomName],[0, 1],null,'1 and comgrade=#03# and comcode like #<%=tGI.ManageCom%>%%#','1','1',180);" 
					onkeyup="return showCodeListKey('comcodeall',[this, ManagecomName],[0, 1],null,'1 and comgrade=#03# and comcode like #<%=tGI.ManageCom%>%%#','1','1',180);"><input class=codename name=ManagecomName readonly></td>
				<td class=title>��ȫ�����</td>
				<td class=input><input class=common name=EdorAppNo></td>
				<td class=title>������</td>
				<td class=input><input class=common name=PolicyNo></td>
			</tr>
			<tr class=common>
				<td class=title>ɨ����Ա</td>
				<td class=input><input class=codeno name=ScanOperator readonly 
					ondblclick="return showCodeList('usercode',[this, ScanOperatorName],[0, 1],null,null,null,'1',180);" 
					onkeyup="return showCodeListKey('usercode',[this, ScanOperatorName],[0, 1],null,null,null,'1',180);"><input class=codename name=ScanOperatorName readonly></td>
				<td class=title>ɨ������</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=ScanStartDate></td>
				<td class=title>ɨ��ֹ��</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=ScanEndtDate></td>
			</tr>
			<tr class=common>
				<td class=title>������Ա</td>
				<td class=input><input class=codeno name=AcceptOperator readonly 
					ondblclick="return showCodeList('usercode',[this, AcceptOperatorName],[0, 1],null,null,null,'1',180);" 
					onkeyup="return showCodeListKey('usercode',[this, AcceptOperatorName],[0, 1],null,null,null,'1',180);"><input class=codename name=AcceptOperatorName readonly></td>
				<td class=title>��������</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=AcceptStartDate></td>
				<td class=title>����ֹ��</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=AcceptEndtDate></td>
			</tr>
		</table>
		
		<input class=cssButton name=QueryButton type=button value="��  ѯ" onclick="queryClick(1);">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divEdorAppGrid);">
			</td>
			<td class=titleImg>��ȫ�����б�</td>
		</tr>
	</table>
	<div id="divEdorAppGrid" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanEdorAppGrid"></span>
				</td>
			</tr>
		</table>
		<div id= "divEdorAppGridPage" style= "display: ''">
			<table align=center>
				<input class=cssButton type=button value="��  ҳ" onclick="turnPage1.firstPage();">
				<input class=cssButton type=button value="��һҳ" onclick="turnPage1.previousPage();">
				<input class=cssButton type=button value="��һҳ" onclick="turnPage1.nextPage();">
				<input class=cssButton type=button value="β  ҳ" onclick="turnPage1.lastPage();">
			</table>
		</div>
	</div>
	
	<input class=cssButton type=button value="��ȫ������" onclick="edorAppCancel();">
	<input class=cssButton type=button value="��������ȫ����" onclick="cancelToAccept();">
	
	<div id="divEdorMain" style="display: 'none'">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divEdorGrid);">
				</td>
				<td class=titleImg>��ȫ�����б�</td>
			</tr>
		</table>
		<div id="divEdorGrid" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanEdorGrid"></span>
					</td>
				</tr>
			</table>
			<div id= "divEdorGridPage" style= "display: ''">
				<table align=center>
					<input class=cssButton type=button value="��  ҳ" onclick="turnPage2.firstPage();">
					<input class=cssButton type=button value="��һҳ" onclick="turnPage2.previousPage();">
					<input class=cssButton type=button value="��һҳ" onclick="turnPage2.nextPage();">
					<input class=cssButton type=button value="β  ҳ" onclick="turnPage2.lastPage();">
				</table>
			</div>
		</div>
		
		<input class=cssButton type=button value="��ȫ��������" onclick="edorMainCancel();">
	</div>
	
	<div id="divEdorItem" style="display: 'none'">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divEdorItemGrid);">
				</td>
				<td class=titleImg>��ȫ��Ŀ�б�</td>
			</tr>
		</table>
		<div id="divEdorItemGrid" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanEdorItemGrid"></span>
					</td>
				</tr>
			</table>
			<div id= "divEdorItemGridPage" style= "display: ''">
				<table align=center>
					<input class=cssButton type=button value="��  ҳ" onclick="turnPage3.firstPage();">
					<input class=cssButton type=button value="��һҳ" onclick="turnPage3.previousPage();">
					<input class=cssButton type=button value="��һҳ" onclick="turnPage3.nextPage();">
					<input class=cssButton type=button value="β  ҳ" onclick="turnPage3.lastPage();">
				</table>
			</div>
		</div>
		
		<input class=cssButton type=button value="��ȫ��Ŀ����" onclick="edorItemCancel();">
	</div>
	
	<!--������-->
	<input type=hidden name=Operate>
	<input type=hidden name=CancelReason>
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
