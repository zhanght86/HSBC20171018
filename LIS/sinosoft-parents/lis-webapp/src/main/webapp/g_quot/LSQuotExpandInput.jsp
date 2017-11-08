<%
/***************************************************************
 * <p>ProName：LSQuotExpandInput.jsp</p>
 * <p>Title：责任拓展</p>
 * <p>Description：责任拓展</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-04-03
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
	var tFlag = "0";//根据传入参数重新查询赋值
	var tTranProdType = "";//产品类型
</script>
<html>
<head >
	<title>责任拓展</title>
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
	<script src="./LSQuotExpandInput.js"></script>
	<%@include file="./LSQuotExpandInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LSQuotExpandSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPlanInfo);">
			</td>
			<td class=titleImg>方案信息</td>
		</tr>
	</table>
	
	<div id="divPlanInfo"  style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanPlanInfoGrid" ></span>
				</td>
			</tr>
		</table>
		<center>		
			<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
		</center>
	</div>
	
	<div id="divAllExpandInfo" style="display: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divExpandInfo);">
				</td>
				<td class=titleImg>已维护拓展信息</td>
			</tr>
		</table>
		<div id="divExpandInfo" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanExpandInfoGrid" ></span>
					</td>
				</tr>
			</table>
			<center>		
				<input class=cssButton90 type=button value="首  页" onclick="turnPage2.firstPage();">
				<input class=cssButton91 type=button value="上一页" onclick="turnPage2.previousPage();">
				<input class=cssButton92 type=button value="下一页" onclick="turnPage2.nextPage();">
				<input class=cssButton93 type=button value="尾  页" onclick="turnPage2.lastPage();">
			</center>
			<table class=common>
				<tr class=common>
					<td class=title>险种 </td>
					<td class=input><input class=codeno name=RiskCode id=RiskCode verify="险种|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('quotprodriskexpand',[this, RiskName],[0,1]);" onkeyup="return returnShowCodeListKey('quotprodriskexpand',[this, RiskName],[0,1]);" readonly><input class=codename name=RiskName id=RiskName elementtype=nacessary></td>
					<td class=title>责任</td>
					<td class=input><input class=codeno name=DutyCode id=DutyCode verify="责任|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('quotplanduty',[this, DutyName],[0,1]);" onkeyup="return returnShowCodeListKey('quotplanduty',[this, DutyName],[0,1]);" readonly><input class=codename name=DutyName id=DutyName elementtype=nacessary></td>
					<td class=title>给付项</td>
					<td class=input><input class=codeno name=GetDutyCode id=GetDutyCode verify="给付项|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('quotgetduty',[this, GetDutyName],[0,1]);" onkeyup="return returnShowCodeListKey('quotgetduty',[this, GetDutyName],[0,1]);" readonly><input class=codename name=GetDutyName id=GetDutyName elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td id=allTD1 class=title style="display: none">拓展类型</td>
					<td id=allTD2 class=input style="display: none"><input class=codeno name=ExpandType id=ExpandType verify="拓展类型|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('qoutexpandtype',[this, ExpandTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('qoutexpandtype',[this, ExpandTypeName],[0,1]);" readonly><input class=codename name=ExpandTypeName id=ExpandTypeName elementtype=nacessary></td>
					<td id=allTD3 class=title style="display: none">扩大责任</td>
					<td id=allTD4 class=input style="display: none"><input class=codeno name=ExpandDuty id=ExpandDuty style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('expandduty',[this, ExpandDutyName],[0,1]);" onkeyup="return returnShowCodeListKey('expandduty',[this, ExpandDutyName],[0,1]);" readonly><input class=codename name=ExpandDutyName id=ExpandDutyName elementtype=nacessary></td>
					<td id=allTD5 class=title style="display: none"></td>
					<td id=allTD6 class=input style="display: none"></td>
					<td id=allTD7 class=title style="display: none"></td>
					<td id=allTD8 class=input style="display: none"></td>
				</tr>
			</table>
			<!--责任要素动态域3-->
			<div id="divDutyFactor" style="display: none">
				<table class=common>
					<tr class=common>
						<td class=title></td>
						<td class=input></td>
						<td class=title></td>
						<td class=input></td>
						<td class=title></td>
						<td class=input></td>
					</tr>
				</table>
			</div>
		</div>
		<input class=cssButton type=button value="增  加" name="AddButton" onclick="addClick();">
		<input class=cssButton type=button value="修  改" name="ModButton" onclick="modifyClick();">
		<input class=cssButton type=button value="删  除" name="DelButton" onclick="delClick();">
	</div>
	<hr class="line"/>
	<input class=cssButton type=button value="关  闭" onclick="top.close();">
	<input type=hidden name=Operate id=Operate>
	<input type=hidden name=PlanCode id=PlanCode>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
