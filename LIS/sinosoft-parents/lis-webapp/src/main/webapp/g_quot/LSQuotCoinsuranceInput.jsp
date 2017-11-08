<%
/***************************************************************
 * <p>ProName��LSQuotCoinsuranceInput.jsp</p>
 * <p>Title����������</p>
 * <p>Description����������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-06-03
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
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
%>
<script>
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tOperator = "<%=tOperator%>";
</script>
<html>
<head >
	<title>��������</title>
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
	<script src="./LSQuotCoinsuranceInput.js"></script>
	<script src="./LSQuotCommonInput.js"></script>
	<%@include file="./LSQuotCoinsuranceInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LSQuotCoinsuranceSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divCoinsurance);">
			</td>
			<td class=titleImg>��������</td>
		</tr>
	</table>
	
	<div id="divCoinsurance" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanCoinsuranceGrid" ></span>
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
		<table class=common>
			<tr class=common>
				<td class=title>������/�ӷ���־</td>
				<td class=input><input class=codeno name=MasterSlaveFlag id=MasterSlaveFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="������/�ӷ���־|NOTNULL" ondblclick="return showCodeList('coinmasterslave', [this,MasterSlaveName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('coinmasterslave', [this,MasterSlaveName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=MasterSlaveName id=MasterSlaveName readonly elementtype=nacessary></td>
				<td class=title>��˾���� </td>
				<td class=input><input class=codeno name=CoinComCode id=CoinComCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="��˾����|NOTNULL" ondblclick="return showCodeList('ldinsurancecom', [this,CoinComName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('ldinsurancecom', [this,CoinComName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=CoinComName id=CoinComName readonly elementtype=nacessary></td>
				<td class=title>�����̯����</td>
				<td class=input><input class="wid common" name=AmntShareRate id=AmntShareRate verify="�����̯����|NOTNULL&NUM&VALUE>0&VALUE<1" elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>���ѷ�̯����</td>
				<td class=input><input class="wid common" name=PremShareRate id=PremShareRate verify="���ѷ�̯����|NOTNULL&NUM&VALUE>0&VALUE<1" elementtype=nacessary></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
			  	<td class=title colspan=6><span style="color: red">�������˾Ϊ��������,�ں�����ѯ���б�����¼�뱣�����ܱ���,�������˾Ϊ�ӹ�����,��ôѯ�۵ı�����¼�뱾��˾�е��ı��</span></td>
			</tr>
		</table>
		<br/>
		<div>
			<input class=cssButton type=button value="��  ��" name="AddButton" id=AddButton onclick="addClick();">
			<input class=cssButton type=button value="��  ��" name="ModButton" id=ModButton onclick="modifyClick();">
			<input class=cssButton type=button value="ɾ  ��" name="DelButton" id=DelButton onclick="deleteClick();">
			<input class=cssButton type=button value="��  ��" onclick="top.close();">
		</div>
	</div>
	<input type=hidden name=Operate id=Operate>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
