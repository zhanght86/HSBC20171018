<%
/***************************************************************
 * <p>ProName��LCGrpApproveListInput.jsp</p>
 * <p>Title�����������</p>
 * <p>Description�����������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �龰
 * @version  : 8.0
 * @date     : 2014-04-14
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.ExeSQL"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";//��¼��½����
	var tOperator = "<%=tGI.Operator%>";
</script>
<html>
<head>
	<title>���������</title>
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
	<script src="./LCGrpApproveListInput.js"></script>
	<%@include file="./LCGrpApproveListInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LCGrpApproveListSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryES);">
			</td>
			<td class=titleImg>��ѯ����</td>
		</tr>
	</table>
	<div id="divQueryES" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>�б�����</td>
				<td class=input><input class=codeno name=ManageCom id=ManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center"ondblclick=" showCodeList('comcodeall',[this,EscanComName],[0,1],null,'1 and comgrade=#03#',1,1);" onkeyup="return showCodeListKey('comcodeall',[this,EscanComName],[0,1],null,'1 and comgrade=#03#',1,1);"><input class=codename name=EscanComName readonly=true></td>
				<td class=title>Ͷ������</td>
				<td class=input><input class="wid common" id=GrpPropNo name=GrpPropNo></td>
				<td class=title>Ͷ��������</td>
				<td class=input><input class="wid common" id=GrpName name=GrpName></td>
			</tr>
		</table>
	</div>
		<input class=cssButton type=button value="��  ѯ" onclick="queryClick(1);">
		<input class=cssButton type=button value="��  ��" onclick="applyClick();">
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryResult);">
			</td>
			<td class=titleImg>������</td>
		</tr>
	</table>
	<div id="divQueryResult" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanGrpApproveListGrid"></span>
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
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divSelfGrpApproveResult);">
			</td>
			<td class=titleImg>���˳�</td>
		</tr>
	</table>
	<div id="divSelfGrpApproveResult" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanSelfGrpApproveListGrid"></span>
				</td>
			</tr>
		</table>
		<center>		
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage2.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage2.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage2.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage2.lastPage();">
		</center>
	</div>
	<input class=cssButton type=button value="��  ��" onclick="enterGrpApprove();">
	<input class=cssButton type=button value="�˻ع�����" onclick="reApplyClick();">
	<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
