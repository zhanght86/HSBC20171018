<%
/***************************************************************
 * <p>ProName��FinComMappingInput.jsp</p>
 * <p>Title����������ά��</p>
 * <p>Description����������ά��</p>
 * <p>Copyright��Copyright (c) 2013</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���θ�
 * @version  : 8.0
 * @date     : 2013-01-01
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
</script>
<html>
<head>
	<title>��������ά��</title>
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
	<script src="./FinComMappingInput.js"></script>
	<%@include file="./FinComMappingInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./FinComMappingSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divComInfo);">
			</td>
			<td class=titleImg>�������ѯ����</td>
		</tr>
	</table>
	<div id="divComInfo" class=maxbox1 style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title>�������</td>
				<td class=input><input class=codeno name=ManageCom id=ManageCom verify="�������|code:conditioncomcode" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('conditioncomcode',[this, ManageComName],[0, 1],null,'1 and comcode like #<%=tGI.ManageCom%>%%#',1,1);" onkeyup="return showCodeListKey('conditioncomcode',[this,ManageComName],[0,1],null,'1 and comcode like #<%=tGI.ManageCom%>%%#',1,1);"><input class=codename name=ManageComName></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="��  ѯ" onclick="queryClick();">
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divComGrid);">
			</td>
			<td class=titleImg>��������б�</td>
		</tr>
	</table>
	<div id=divComGrid style="display:''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanComGrid"></span></td>
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
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divFinComInfo);">
			</td>
			<td class=titleImg>����������Ϣ</td>
		</tr>
	</table>
	<div id="divFinComInfo" class=maxbox1 style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title>�����������</td>
				<td class=input><input class=code name=FinComCode id=FinComCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="�������|NOTNULL" ondblclick="return showCodeList('fincom',[this,FinComName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('fincom',[this,FinComName],[0,1],null,null,null,'1',null);" elementtype=nacessary></td>
				<td class=title>�����������</td>
				<td class=input><input class="wid readonly" name=FinComName readonly></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="��  ��" onclick="saveClick();">
		<input class=cssButton type=button value="��  ��" onclick="resetClick();">
	</div>
	<!--������-->
	<input type=hidden name=Operate>
	<input type=hidden name=HiddenManageCom>
	<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
