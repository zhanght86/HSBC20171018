<%
/***************************************************************
 * <p>ProName��LSQuotCountProfitInput.jsp</p>
 * <p>Title���������</p>
 * <p>Description���������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-05-29
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
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tQuotType = request.getParameter("QuotType");
%>
<script>
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tQuotType = "<%=tQuotType%>";
	var tOperator = "<%=tOperator%>";
	var tCurrentDate = "<%=tCurrentDate%>";
</script>
<html>
<head>
	<title>�����ղ���</title>
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
	<link href="../common/css/tab.css" rel=stylesheet type=text/css>
	<script src="./LSQuotCountProfitInput.js"></script>
	<script src="./LSQuotCommonInput.js"></script>
	<%@include file="./LSQuotCountProfitInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<div id="tab1">
		<ul>
		   <li onclick="goToLongRiskStep(0)">���ʲ���</li>
		   <li onclick="goToLongRiskStep(1)" class="now">�������</li>
		</ul>
	</div>
</form>
<form name=fm2 id=fm2 method=post action="" target=fraSubmit>
	<div class="tablist block">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPolInfo);">
				</td>
				<td class=titleImg>������Ϣ</td>
			</tr>
		</table>
		<div id="divPolInfo" class=maxbox style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>����</td>
					<td class=input><input class=codeno name=RiskCode id=RiskCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="����|NOTNULL" ondblclick="return returnShowCodeList('quotrisk',[this,RiskName],[0,1]);" onkeyup="return returnShowCodeListKey('quotrisk',[this,RiskName],[0,1]);"><input class=codename name=RiskName id=RiskName onkeydown="fuzzyRiskName(RiskCode,RiskName)" elementtype=nacessary></td>
					<td class=title>����</td>
					<td class=input><input class=codeno name=DutyCode id=DutyCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="����|NOTNULL" ondblclick="return returnShowCodeList('quotduty',[this,DutyName],[0,1]);" onkeyup="return returnShowCodeListKey('quotduty',[this,DutyName],[0,1]);"><input class=codename name=DutyName id=DutyName elementtype=nacessary></td>
					<td class=title>Ԥ�Ƴ�ʼ����</td>
					<td class=input><input class="wid common" name=InitPrem id=InitPrem verify="Ԥ�Ƴ�ʼ����|NOTNULL&NUM" elementtype=nacessary ></td>
				</tr>
				<tr class=common>
					<td class=title>����ѿ۽ɷ�ʽ</td>
					<td class=input><input class=codeno name=MangFeeType id=MangFeeType style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="����ѿ۽ɷ�ʽ|NOTNULL" ondblclick="return returnShowCodeList('deducttype',[this,MangFeeTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('deducttype',[this,MangFeeTypeName],[0,1]);"><input class=codename name=MangFeeTypeName  id=MangFeeTypeName elementtype=nacessary></td>
					<td class=title>��ʼ�����(Ԫ)/����</td>
					<td class=input><input class="wid common" name=InitMangFee id=InitMangFee verify="��ʼ�����(Ԫ)/����|NOTNULL&NUM" elementtype=nacessary></td>
					<td class=title>Ԥ��������</td>
					<td class=input><input class="wid common" name=Profit id=Profit verify="Ԥ��������|NOTNULL&NUM" elementtype=nacessary ></td>
				</tr>
				<tr class=common>
					<td class=title>�¶ȹ��������</td>
					<td class=input><input class=codeno name=MonthFeeType  id=MonthFeeType style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="�¶ȹ��������|NOTNULL" ondblclick="return returnShowCodeList('deducttype1',[this,MonthFeeTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('deducttype1',[this,MonthFeeTypeName],[0,1]);"><input class=codename name=MonthFeeTypeName id=MonthFeeTypeName readonly elementtype=nacessary></td>
					<td class=title>�¶ȹ����(Ԫ)/����</td>
					<td class=input><input class="wid common" name=MonthFee id=MonthFee verify="�¶ȹ����(Ԫ)/����|NOTNULL&NUM" elementtype=nacessary></td>
					<td class=title>Ԥ��Ͷ������</td>
					<td class=input><input class="wid common" name=Years id=Years verify="Ԥ��Ͷ������|NOTNULL&INT&VALUE>0&VALUE<=100" elementtype=nacessary></td>
				</tr>
			</table>
			<input class=cssButton type=button value="�������" name="ProfitClick" id=ProfitClick onclick="profitClick();">
		</div>
		<!-- ���������Ϣ -->
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divProfitInfo);">
				</td>
				<td class=titleImg>���������Ϣ</td>
			</tr>
		</table>
		<div id="divProfitInfo" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanProfitInfoGrid" ></span>
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
		<hr class="line"/>
		<input class=cssButton type=button value="��  ��" onclick="top.close();">
	</div>
	<input type=hidden name=Operate id=Operate>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
