<%
/***************************************************************
 * <p>ProName��FinAccItemTraceInput.jsp</p>
 * <p>Title����Ŀ��ϸ��ѯ</p>
 * <p>Description����֧��Ŀ��ϸ��ѯ�뵼��</p>
 * <p>Copyright��Copyright (c) 2013</p>
 * <p>Company��Sinosoft</p>
 * @author   : ʯȫ��
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
	var mManageCom = "<%=tGI.ManageCom%>";
</script>
<html>
<head>
	<title>��Ŀ��ϸ��ѯ</title>
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
	<script src="./FinAccItemTraceInput.js"></script>
	<%@include file="./FinAccItemTraceInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divFinAccItemTraceInfo);">
			</td>
			<td class=titleImg>�������ѯ����</td>
		</tr>
	</table>
	
	<div id="divFinAccItemTraceInfo" class=maxbox1 style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title>�������</td>
				<td class=input><input class=codeno name=ManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('conditioncomcode',[this,ManageComName],[0,1],null,'1 and comcode like #'+mManageCom+'%#','1',1);" onkeyup="return showCodeListKey('conditioncomcode',[this,ManageComName],[0,1],null,'1 and comcode like #'+mManageCom+'%#','1',1);"><input class=codename name=ManageComName></td>
				<td class=title>������</td>
				<td class=input><input class="wid common" name=GrpContNo></td>
				<td class=title>ҵ���</td>
				<td class=input><input class="wid common" name=OtherNo></td>
			</tr>
			<tr class=common>
				<td class=title>���κ�</td>
				<td class=input><input class="wid common" name=BatchNo></td>
				<td class=title>ҵ������</td>
				<td class=input><input class=coolDatePicker name=TransStartDate verify="��������|DATE" dateFormat="short" onClick="laydate({elem: '#TransStartDate'});" id="TransStartDate"><span class="icon"><a onClick="laydate({elem: '#TransStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>ҵ��ֹ��</td>
				<td class=input><input class=coolDatePicker name=TransEndDate verify="����ֹ��|DATE" dateFormat="short" onClick="laydate({elem: '#TransEndDate'});" id="TransEndDate"><span class="icon"><a onClick="laydate({elem: '#TransEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
			<tr class=common>
				<td class=title>��ƿ�Ŀ</td>
				<td class=input><input class=codeno name=FinCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('finaccount',[this,FinName],[0,1],null,null,null,1)" onkeyup="return showCodeListKey('finaccount',[this,FinName],[0,1],null,null,null,1)"><input class=codename name=FinName onkeydown="fuzzyQueryFinCode(FinCode,FinName)"></td>
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
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divFinAccItemTraceGrid);">
			</td>
			<td class=titleImg>��Ŀ��ϸ��Ϣ�б�</td>
		</tr>
	</table>
	<div id=divFinAccItemTraceGrid style="display:''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanFinAccItemTraceGrid"></span></td>
			</tr>
		</table>
		<center>		
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
			<input class=cssButton type=button value="��������" onclick="exportData();">
		</center>
	</div>
	
	<!--������-->
	<input type=hidden name=Operate>
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
