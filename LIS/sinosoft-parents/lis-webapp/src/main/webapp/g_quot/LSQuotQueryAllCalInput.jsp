<%
/***************************************************************
 * <p>ProName��LSQuotQueryAllCalInput.jsp</p>
 * <p>Title�����ܼ����ѯ</p>
 * <p>Description�����ܼ����ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-08-14
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
	String tQuotType = request.getParameter("QuotType");
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
	String tActivityID = request.getParameter("ActivityID");
%>
<script>
	var tOperator = "<%=tOperator%>";//��ǰ��¼��
	var tManageCom = "<%=tManageCom%>";//��ǰ��¼����
	var tCurrentDate = "<%=tCurrentDate%>";
	var tQuotType = "<%=tQuotType%>";
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tActivityID = "<%=tActivityID%>";
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
	<script src="../common/laydate/laydate.js"></script>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./LSQuotCommonInput.js"></script>
	<script src="./LSQuotQueryAllCalInput.js"></script>
	<%@include file="./LSQuotQueryAllCalInit.jsp"%>
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
			<td class=titleImg>������Ϣ��ѯ</td>
		</tr>
	</table>
	<div id="divQuery" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>������</td>
				<td class=input><input class="wid common" name=PolicyNo id=PolicyNo></td>
				<td class=title>�ͻ�����</td>
				<td class=input><input class="wid common" name=CustomerName id=CustomerName></td>
				<td class=title>��Ч����</td>
				<td class=input><input class="coolDatePicker" dateFormat="short" name=CValiDate id=CValiDate verify="��Ч����|date"></td>
			</tr>
			<tr class=common>
				<td class=title>��Чֹ��</td>
				<td class=input><input class="coolDatePicker" dateFormat="short" name=EndDate id=EndDate verify="��Ч����|date"></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="��  ѯ" onclick="queryClick();">
		<input class=cssButton type=button value="��  ��" onclick="top.close();">
		<input class=cssButton type=button value="ȫ������" onclick="expAllInfo();">
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo);">
			</td>
			<td class=titleImg>����������Ϣ�б�</td>
		</tr>
	</table>
	<div id="divInfo" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanExpCalTotalGrid"></span>
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
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divMonth);">
			</td>
			<td class=titleImg>���½᰸������б�</td>
		</tr>
	</table>
	<div id="divMonth" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanExpMonthCMCalGrid"></span>
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
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divExpCMCal);">
			</td>
			<td class=titleImg>Ԥ���⸶���б�</td>
		</tr>
	</table>
	<div id="divExpCMCal"  style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanExpCMCalGrid"></span>
				</td>
			</tr>
		</table>
		<center>		
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage3.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage3.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage3.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage3.lastPage();">
		</center>
	</div>
	<input type=hidden name=Operate id=Operate>
	<input type=hidden name=SheetName id=SheetName>
	<input type=hidden name=SheetName id=SheetName>
	<input type=hidden name=SheetName id=SheetName>
	<input type=hidden name=SheetTitle id=SheetTitle>
	<input type=hidden name=SheetTitle id=SheetTitle>
	<input type=hidden name=SheetTitle id=SheetTitle>
	<input type=hidden name=SheetSql id=SheetTitle>
	<input type=hidden name=SheetSql id=SheetTitle>
	<input type=hidden name=SheetSql id=SheetTitle>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
