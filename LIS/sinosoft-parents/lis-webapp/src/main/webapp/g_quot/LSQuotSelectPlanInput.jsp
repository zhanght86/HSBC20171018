<%
/***************************************************************
 * <p>ProName��LSQuotSelectPlanInput.jsp</p>
 * <p>Title��ѡ�񱨼۷���</p>
 * <p>Description��ѡ�񱨼۷���</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-05-07
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
	String tOfferListNo = request.getParameter("OfferListNo");
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
	String tQuotType = request.getParameter("QuotType");
	String tTranProdType = request.getParameter("TranProdType");
	String tQuotQuery = request.getParameter("QuotQuery");
%>
<script>
	var tOfferListNo = "<%=tOfferListNo%>";
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tQuotType = "<%=tQuotType%>";
	var tTranProdType = "<%=tTranProdType%>";
	var tOperator = "<%=tOperator%>";
	var tQuotQuery = "<%=tQuotQuery%>";//��ѯ�������ʱ,ΪY
</script>
<html>
<head >
	<title>ѡ�񱨼۷���</title>
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
	<script src="./LSQuotSelectPlanInput.js"></script>
	<%@include file="./LSQuotSelectPlanInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LSQuotSelectPlanSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divNoPlanCombi);">
			</td>
			<td class=titleImg><span style="color: red">�����������</span></td>
		</tr>
	</table>
	
	<div id="divNoPlanCombi"  style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanNoPlanCombiGrid" ></span>
				</td>
			</tr>
		</table>

		<div id="divTurnPage" style="display: ''">
			<center>		
				<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
				<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
				<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
				<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
			</center>
		</div>
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPlanList);">
			</td>
			<td class=titleImg>ѯ�۷���</td>
		</tr>
	</table>
	
	<div id="divPlanList"  style="display: ''">
	
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanQuotPlanGrid" ></span>
				</td>
			</tr>
		</table>
	</div>
	
	<input class=cssButton type=button value="ѡ  ��" name="SelectButton" id=SelectButton onclick="selectClick();">
	<div id="divBindPlan" style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>���󷽰�����</td>
				<td class=input><input class=common name=BindPlanDesc id=BindPlanDesc>&nbsp;&nbsp;<input class=cssButton type=button value="����ѡ��" onclick="bindSelectClick();"></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>			
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divBJPlan);">
			</td>
			<td class=titleImg>������ѡ����</td>
		</tr>
	</table>
	<div id="divBJPlan"  style="display: ''">
	
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanBJQuotPlanGrid" ></span>
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
	<div id="divFixedValue" style="display: none">
		<table class=common>
			<tr class=common>
				<td class=title id=tdFixed1 name=tdFixed1 style="display: none">�������(Ԫ)</td>
				<td class=input id=tdFixed2 name=tdFixed2 style="display: none"><input class=common name=EnginCost id=EnginCost elementtype=nacessary>&nbsp;&nbsp;<input class=cssButton type=button value="��  ��" onclick="saveFixedValue();"></td>
				<td class=title id=tdFixed3 name=tdFixed3 style="display: none">�������(ƽ����)</td>
				<td class=input id=tdFixed4 name=tdFixed4 style="display: none"><input class=common name=EnginArea id=EnginArea elementtype=nacessary>&nbsp;&nbsp;<input class=cssButton type=button value="��  ��" onclick="saveFixedValue();"></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>			
	</div>
	<input class=cssButton type=button value="ɾ  ��" name="DelButton" onclick="deleteClick();">
	<input class=cssButton type=button value="��  ��" onclick="top.close();">
	<input type=hidden name=Operate id=Operate>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
