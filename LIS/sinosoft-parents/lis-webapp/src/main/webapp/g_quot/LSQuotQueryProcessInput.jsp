<%
/***************************************************************
 * <p>ProName��LSQuotQueryProcessInput.jsp</p>
 * <p>Title��ѯ�����̽ڵ��ѯ</p>
 * <p>Description��ѯ�����̽ڵ��ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-08-08
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
	String tManageCom = tGI.ManageCom;
%>
<script>
	var tOperator = "<%=tOperator%>";//��ǰ��¼��
	var tManageCom = "<%=tManageCom%>";//��ǰ��¼����
	var tCurrentDate = "<%=tCurrentDate%>";
</script>
<html>
<head>
	<title>ѯ�����̲�ѯ</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<script src="../common/laydate/laydate.js"></SCRIPT>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./LSQuotCommonInput.js"></script>
	<script src="./LSQuotQueryProcessInput.js"></script>
	<%@include file="./LSQuotQueryProcessInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<div style="display: none">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanVirtualGrid" ></span>
				</td>
			</tr>
		</table>
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
			<td class=titleImg>��ѯ����</td>
		</tr>
	</table>
	<div id="divQuery" style="display: ''" class=maxbox1>
		<table class=common>
			<tr class=common>
				<td class=title>ѯ�ۺ�</td>
				<td class=input><input class="wid common" name=QuotNo id=QuotNo></td>
				<td class=title>���κ�</td>
				<td class=input><input class="wid common" name=QuotBatNo id=QuotBatNo></td>
				<td class=title>׼�ͻ�����/��Ŀ����</td>
				<td class=input><input class="wid common" name=CustName id=CustName></td>
			</tr>
			<tr class=common>
				<td class=title>ѯ������</td>
				<td class=input><input class=codeno name=QuotType id=QuotType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('quottype', [this,QuotTypeName], [0,1], null, null, null, '1', 180);" onkeyup="return showCodeListKey('quottype', [this,QuotTypeName], [0,1], null, null, null, '1', 180);" readonly><input class=codename name=QuotTypeName id=QuotTypeName></td>
				<td class=title>ѯ��״̬</td>
				<td class=input><input class=codeno name=QuotState id=QuotState style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('quotstate', [this,QuotStateName], [0,1], null, null, null, '1', 180);" onkeyup="return showCodeListKey('quotstate', [this,QuotStateName], [0,1], null, null, null, '1', 180);" readonly><input class=codename name=QuotStateName id=QuotStateName></td>
				<td class=title>��ǰλ��</td>
				<td class=input><input class=codeno name=QuotNode id=QuotNode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('quotnode', [this,QuotNodeName], [0,1], null, null, null, '1', 180);" onkeyup="return showCodeListKey('quotnode', [this,QuotNodeName], [0,1], null, null, null, '1', 180);" readonly><input class=codename name=QuotNodeName id=QuotNodeName></td>
			</tr>
		</table>
		<input class=cssButton type=button value="��  ѯ" onclick="queryClick();">
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo);">
			</td>
			<td class=titleImg>ѯ��������Ϣ</td>
		</tr>
	</table>
	<div id="divInfo" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanProcessGrid"></span>
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
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divTrace);">
			</td>
			<td class=titleImg>ѯ�۹켣��Ϣ</td>
		</tr>
	</table>
	<div id="divTrace" style="display: '';text-align:center" >
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanTraceGrid"></span>
				</td>
			</tr>
		</table>
		<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage2.firstPage();">
		<input class=cssButton91 type=button value="��һҳ" onclick="turnPage2.previousPage();">
		<input class=cssButton92 type=button value="��һҳ" onclick="turnPage2.nextPage();">
		<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage2.lastPage();">
	</div>
	<input type=hidden name=Operate id=Operate>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
