<%
/***************************************************************
 * <p>ProName��PreCustomerTraceQueryInput.jsp</p>
 * <p>Title��׼�ͻ��޸Ĺ켣��ѯ����</p>
 * <p>Description��׼�ͻ��޸Ĺ켣��ѯ����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tPreCustomerNo = request.getParameter("PreCustomerNo");
%>
<script>
	var tPreCustomerNo = "<%=tPreCustomerNo%>";
</script>
<html>
<head >
	<title>׼�ͻ��޸Ĺ켣��ѯ</title>
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
	<script src="./PreCustomerTraceQueryInput.js"></script>
	<%@include file="./PreCustomerTraceQueryInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm method=post action="" target=fraSubmit>
	<!-- table>
		<tr>
			<td class=commontop>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryInfo);">
			</td>
		 	<td class=titleImgtop>׼�ͻ���ѯ����</td>
		</tr>
	</table>
	
	<div id="divQueryInfo" style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>׼�ͻ�����</td>
				<td class=input colspan=3><input class=common style="width:300px" name=PreCustomerName></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<table>
			<input class=cssButton name=QueryButton type=button value="��  ѯ" onclick="queryClick();">
			<input class=cssButton name=ReturnButton type=button value="��  ��" onclick="top.close();">
		</table>
	</div -->
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPreCustomerGrid);">
			</td>
			<td class=titleImg>׼�ͻ��޸Ĺ켣�б�</td>
		</tr>
	</table>
	<div id="divPreCustomerGrid" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanPreCustomerGrid" ></span></td>
			</tr>
		</table>
		<div id= "divPreCustomerGridPage" style= "display: ''">
			<table align=center>
				<input class=cssButton type=button value="��  ҳ" onclick="turnPage1.firstPage();">
				<input class=cssButton type=button value="��һҳ" onclick="turnPage1.previousPage();">
				<input class=cssButton type=button value="��һҳ" onclick="turnPage1.nextPage();">
				<input class=cssButton type=button value="β  ҳ" onclick="turnPage1.lastPage();">
			</table>
		</div>
	</div>
	
	<!--������-->
	<input type=hidden name=Operate>
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
