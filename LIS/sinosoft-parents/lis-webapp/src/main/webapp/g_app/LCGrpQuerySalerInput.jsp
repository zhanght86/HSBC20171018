<%
/***************************************************************
 * <p>ProName��LCGrpQuerySalerInput.jsp</p>
 * <p>Title�������˲�ѯ</p>
 * <p>Description�������˲�ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-05-15
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
	String tMulLineNo = request.getParameter("SelNo");
	String tAgentCom = request.getParameter("AgentCom");
	
%>
<script>
	var tOperator = "<%=tOperator%>";//��ǰ��¼��
	var tMulLineNo = "<%=tMulLineNo%>";//MulLine���
	var tAgentCom = "<%=tAgentCom%>";//�н��������
	var tCurrentDate = "<%=tCurrentDate%>";
</script>
<html>
<head>
	<title>�����˲�ѯ</title>
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
	<script src="./LCGrpQuerySalerInput.js"></script>
	<%@include file="./LCGrpQuerySalerInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<!-- ��ѯ���� -->
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo1);">
			</td>
			<td class=titleImg>�����˲�ѯ����</td>
		</tr>
	</table>
	<div id="divInfo1" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>����������</td>
				<td class=input><input class="wid common" id=SalerName name=SalerName></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="��  ѯ" onclick="querySaler();">
		<input class=cssButton type=button value="ѡ  ��" onclick="returnSaler();">
	</div>
    	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divResult1);">
			</td>
			<td class=titleImg>��������Ϣ�б�</td>
		</tr>
	</table>
	<div id="divResult1" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanSalerGrid"></span>
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
	<input type=hidden name=Operate>
</form>
<form name=fmPub method=post action="" target=fraSubmit>
	<div style="display: 'none'">
		<input type=hidden name=Operate>
	</div>
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
