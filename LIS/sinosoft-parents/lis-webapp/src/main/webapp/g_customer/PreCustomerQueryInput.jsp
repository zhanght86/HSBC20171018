<%
/***************************************************************
 * <p>ProName��PreCustomerQueryInput.jsp</p>
 * <p>Title��׼�ͻ�ά����ѯ����</p>
 * <p>Description��׼�ͻ�ά����ѯ����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tFlag = request.getParameter("Flag");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tFlag = "<%=tFlag%>";
</script>
<html>
<head >
	<title>׼�ͻ�ά��</title>
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

	
	
	
	<script src="./PreCustomerQueryInput.js"></script>
	<%@include file="./PreCustomerQueryInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryInfo);">
			</td>
		 	<td class=titleImg>׼�ͻ�ά����ѯ����</td>
		</tr>
	</table>
	
	<div id="divQueryInfo" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>׼�ͻ�����</td>
				<td class=input colspan=3><input class="wid common" style="width:300px" name=PreCustomerName id=PreCustomerName></td>
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
			<input class=cssButton name=AddButton id=AddButton type=button value="��  ��" onclick="addClick();">
			<input class=cssButton name=QueryButton id=QueryButton type=button value="��  ѯ" onclick="queryClick();">
		</table>
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPreCustomerGrid);">
			</td>
			<td class=titleImg>׼�ͻ��б�</td>
		</tr>
	</table>
	<div id="divPreCustomerGrid" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanPreCustomerGrid" ></span></td>
			</tr>
		</table>
		<div id= "divPreCustomerGridPage" style= "display: '';text-align:center;">
			<table align=center>
				<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
				<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
				<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
				<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
				<input class=cssButton type=button value="��������" onclick="exportData();">
			</table>
		</div>
	</div>
	
	<!--������-->
	<input type=hidden name=Operate id=Operate>
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
