<%
/***************************************************************
 * <p>ProName��LSQuotShowUWDescInput.jsp</p>
 * <p>Title��ѯ�۲�ѯ--�ܹ�˾�������</p>
 * <p>Description��ѯ�۲�ѯ--�ܹ�˾�������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2015-01-14
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
%>
<script>
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tOperator = "<%=tOperator%>";
</script>
<html>
<head >
	<title>�ܹ�˾�������</title>
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
	<script src="./LSQuotShowUWDescInput.js"></script>
	<%@include file="./LSQuotShowUWDescInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divDesc);">
			</td>
			<td class=titleImg>�ܹ�˾�������</td>
		</tr>
	</table>
	
	<div id="divDesc" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>ѯ�ۺ�</td>
				<td class=input><input class="wid readonly" name=QuotNo id=QuotNo value =<%=tQuotNo%> readonly></td>
				<td class=title>���κ�</td>
				<td class=input><input class="wid readonly" name=QuotBatNo id=QuotBatNo value =<%=tQuotBatNo%> readonly></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>�ܹ�˾�˱�����</td>
				<td class=input><input class=codeno name=UWConclu id=UWConclu readonly ><input class=codename name=UWConcluName id=UWConcluName></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>�ܹ�˾�ۺ����</td>
				<td class=input colspan=5><textarea cols=80 rows=8 name=UWOpinion id=UWOpinion ></textarea></td>
			</tr>
		</table>
	</div>
	<input class=cssButton type=button value="��  ��" onclick="top.close();">
	<input type=hidden name=Operate id=Operate>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
