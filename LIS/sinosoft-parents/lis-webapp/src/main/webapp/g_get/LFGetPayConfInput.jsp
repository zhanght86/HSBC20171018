<%
/***************************************************************
 * <p>ProName��LFGetPayConfInput.jsp</p>
 * <p>Title����ȡȷ��</p>
 * <p>Description����ȡȷ��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-09-23
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tCurrenDate = PubFun.getCurrentDate();
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tCurrenDate = "<%=tCurrenDate%>";
</script>
<html>
<head >
	<title>��ȡȷ��</title>
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
	<script src="./LFGetPayConfInput.js"></script>
	<%@include file="./LFGetPayConfInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
		 	<td class=titleImg>��ѯ����</td>
		</tr>
	</table>
	<div id="divQuery" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>�б�����</td>
				<td class=input><input class=codeno name=Managecom id=Managecom readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
					ondblclick="return showCodeList('conditioncomcode',[this, ManagecomName],[0, 1],null,'1 and comgrade=#03# and comcode like #<%=tGI.ManageCom%>%%#','1','1',180);" 
					onkeyup="return showCodeListKey('conditioncomcode',[this, ManagecomName],[0, 1],null,'1 and comgrade=#03# and comcode like #<%=tGI.ManageCom%>%%#','1','1',180);"><input class=codename name=ManagecomName readonly></td>
				<td class=title>���屣����</td>
				<td class=input><input class="wid common" name=GrpContNo id=GrpContNo></td>
				<td class=title>Ͷ��������</td>
				<td class=input><input class="wid common" name=GrpName id=GrpName></td>
			</tr>
			<tr class=common>
				<td class=title>���˱�����</td>
				<td class=input><input class="wid common" name=ContNo id=ContNo></td>
				<td class=title>������������</td>
				<td class=input><input class="wid common" name=InsuredName id=InsuredName></td>
				<td class=title>֤������</td>
				<td class=input><input class="wid common" name=IDNo id=IDNo></td>
			</tr>
			<tr class=common>
				<td class=title>��ȡ����</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=GetStartDate onClick="laydate({elem: '#GetStartDate'});" id="GetStartDate"><span class="icon"><a onClick="laydate({elem: '#GetStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>��ȡֹ��</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=GetEndDate onClick="laydate({elem: '#GetEndDate'});" id="GetEndDate"><span class="icon"><a onClick="laydate({elem: '#GetEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		
		<input class=cssButton name=QueryButton type=button value="��  ѯ" onclick="queryClick(1);">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divGetGrid);">
			</td>
			<td class=titleImg>��ȡ������Ϣ</td>
		</tr>
	</table>
	<div id="divGetGrid" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanGetGrid"></span>
				</td>
			</tr>
		</table>
		<div id= "divGetGridPage" style= "display: ;text-align:center">
			<table align=center>
				<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
				<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
				<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
				<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
			</table>
		</div>
	</div>
	
	<input class=cssButton type=button value="��ȡȷ��" onclick="getConfClick();">
	
	<!--������-->
	<input type=hidden name=Operate id=Operate>
	<br /><br /><br /><br />
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
