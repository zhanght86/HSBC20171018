<%
/***************************************************************
 * <p>ProName��LSQuotShowRequestInput.jsp</p>
 * <p>Title��ҵ������</p>
 * <p>Description��ҵ������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-05-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String tQuotType = request.getParameter("QuotType");
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
%>
<script>
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
</script>
<html>
<head >
	<title>ҵ������</title>
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
	<script src="./LSQuotShowRequestInput.js"></script>
	<%@include file="./LSQuotShowRequestInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divRequest);">
			</td>
			<td class=titleImg>ҵ������ά��</td>
		</tr>
	</table>
	<div id="divRequest" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanRequestGrid"></span>
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
	<br>
	<div id="divRequestDetail" style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>ҵ���������</td>
				<td class=input><input class=codeno name=RequestType id=RequestType readonly><input class=codename name=RequestTypeName id=RequestTypeName readonly></td>
				<td class=title id="divOtherTypeDescTitle" style="display: none">������������</td>
				<td class=input id="divOtherTypeDescInput" style="display: none"><input class=common name=OtherTypeDesc id=OtherTypeDesc readonly></td>
				<td class=title id="divRiskTitle" style="display: none">����</td>
				<td class=input id="divRiskInput" style="display: none"><input class=codeno name=RiskCode id=RiskCode readonly><input class=codename name=RiskName id=RiskName readonly></td>
				<td class=title id="divTD1" style="display: ''"></td>
				<td class=input id="divTD2" style="display: ''"></td>
				<td class=title id="divTD3" style="display: ''"></td>
				<td class=input id="divTD4" style="display: ''"></td>
			</tr>
			<tr class=common>
				<td class=title>ҵ����������</td>
				<td class=input colspan=5><textarea cols=76 rows=5 name=RequestDesc id=RequestDesc readonly></textarea></td>
			</tr>
			<tr class=common id="divSubUWOpinion" style="display: ''">
				<td class=title>��֧��˾�˱����</td>
				<td class=input colspan=5><textarea cols=76 rows=5 name=SubUWOpinion id=SubUWOpinion readonly></textarea></td>
			</tr>
			<tr class=common id="divBranchUWOpinion" style="display: ''">
				<td class=title>�ֹ�˾�˱����</td>
				<td class=input colspan=5><textarea cols=76 rows=5 name=BranchUWOpinion id=BranchUWOpinion readonly></textarea></td>
			</tr>
		</table>
	</div>
	<input class=cssButton type=button value="��  ��" onclick="top.close();">
	
	<input type=hidden name=Operate id=Operate>
	<input type=hidden name=SerialNo id=SerialNo>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
