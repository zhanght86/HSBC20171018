<%
/***************************************************************
 * <p>ProName��LSQuotPlanImpInfoInput.jsp</p>
 * <p>Title��ѯ�۷���������Ϣ</p>
 * <p>Description��ѯ�۷���������Ϣ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-07-16
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
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
	String tQuotType = request.getParameter("QuotType");
	String tTranProdType = request.getParameter("TranProdType");
%>
<script>
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tQuotType = "<%=tQuotType%>";
	var tOperator = "<%=tOperator%>";
	var tTranProdType = "<%=tTranProdType%>";
</script>
<html>
<head >
	<title>ѯ�۷���������Ϣ</title>
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
	<script src="./LSQuotPlanImpInfoInput.js"></script>
	<script src="./LSQuotCommonInput.js"></script>
	<%@include file="./LSQuotPlanImpInfoInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LSQuotPlanCombiSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPlanQuery);">
			</td>
			<td class=titleImg>��ѯ����</td>
		</tr>
	</table>
	
	<div id="divPlanQuery" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>ѯ�ۺ�</td>
				<td class=input><input class="wid readonly" name=QuotNo id=QuotNo readonly></td>
				<td class=title>���κ�</td>
				<td class=input><input class="wid readonly" name=QuotBatNo id=QuotBatNo readonly></td>
				<td class=title>��������</td>
				<td class=input><input class="wid common" name=BacthNo id=BacthNo></td>
			</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td class=title>�Ƿ���ɹ�</td>
				<td class=input><input class=codeno name=ImpState id=ImpState style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('trueflag', [this,ImpStateName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('trueflag', [this,ImpStateName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=ImpStateName id=ImpStateName readonly></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="��  ѯ" name="QueryImpInfo" id=QueryImpInfo onclick="queryPlanImpList();">
		<input class=cssButton type=button value="��  ��" onclick="top.close();">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo1);">
				</td>
				<td class=titleImg>����������Ϣ�б�</td>
			</tr>
		</table>
		<div id="divInfo1" name="divInfo1" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanPlanImpInfoGrid" ></span>
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
		<input class=cssButton type=button value="�������δ�����Ϣ����" name="DownloadButton" onclick="downloadClick();">
	</div>
	<input type=hidden name=Operate>
	<input type=hidden name=SheetName>
	<input type=hidden name=SheetName>
	<input type=hidden name=SheetName>
	<input type=hidden name=SheetTitle>
	<input type=hidden name=SheetTitle>
	<input type=hidden name=SheetTitle>
	<input type=hidden name=SheetSql>
	<input type=hidden name=SheetSql>
	<input type=hidden name=SheetSql>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br /><br /><br /><br />
</body>
</html>
