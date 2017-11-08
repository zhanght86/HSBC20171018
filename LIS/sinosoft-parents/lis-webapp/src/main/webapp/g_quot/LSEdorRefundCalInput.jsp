<%
/***************************************************************
 * <p>ProName：LSEdorRefundCalInput.jsp</p>
 * <p>Title：保全退费算法维护</p>
 * <p>Description：保全退费算法维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-04-23
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
	String tObjType = request.getParameter("ObjType");
%>
<script>
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tObjType = "<%=tObjType%>";
	var tOperator = "<%=tOperator%>";
</script>
<html>
<head>
	<title>保全退费算法维护</title>
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
	<link href="../common/css/tab.css" rel=stylesheet type=text/css>
	<script src="./LSEdorRefundCalInput.js"></script>
	<script src="./LSQuotCommonInput.js"></script>
	<%@include file="./LSEdorRefundCalInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<div id="tab1">
		<ul>
		   <li onclick="goToPordParamStep(0)">管理费维护</li>
		   <li onclick="goToPordParamStep(1)" class="now">保全退费算法维护</li>
		</ul>
	</div>
</form>
<form name=fm2 id=fm2 method=post action="" target=fraSubmit>
	<div class="tablist block">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divRefundQuery);">
				</td>
				<td class=titleImg>退费信息查询</td>
			</tr>
		</table>
		<div id="divRefundQuery" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>险种</td>
					<td class=input><input class=codeno name=RiskCode1 id=RiskCode1 style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('quotprodrisk',[this,RiskName1],[0,1],null,'1 and a.quotno = #<%=tQuotNo%># and a.quotbatno = #<%=tQuotBatNo%>#','1','1',null);" onkeyup="return showCodeListKey('quotprodrisk',[this,RiskName1],[0,1],null,'1 and a.quotno = #<%=tQuotNo%># and a.quotbatno = #<%=tQuotBatNo%>#','1','1',null);" readonly><input class=codename name=RiskName1 id=RiskName1></td>
					<td class=title>退费类型</td>
					<td class=input><input class=codeno name=GetType1 id=GetType1 style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('gp_gettype', [this, GetTypeName1],[0,1] ,null,null,null,1);" onkeyup="showCodeListKey('gp_gettype', [this, GetTypeName1],[0,1],null,null,null,1);" readonly><input class=codename name=GetTypeName1 id=GetTypeName1 ></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
			<input class=cssButton type=button value="查  询" onclick="querySubmit('Page');">
		</div>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divRefundList);">
				</td>
				<td class=titleImg>退费信息列表</td>
			</tr>
		</table>
		<div id="divRefundList" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanRefundListGrid"></span>
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
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divRefundInfo);">
				</td>
				<td class=titleImg>退费信息维护</td>
			</tr>
		</table>
		<div id="divRefundInfo" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>险种</td>
					<td class=input><input class=codeno name=RiskCode2 id=RiskCode2 verify="险种|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('quotprodrisk',[this,RiskName2],[0,1],null,'1 and a.quotno = #<%=tQuotNo%># and a.quotbatno = #<%=tQuotBatNo%>#','1','1',null);" onkeyup="return showCodeListKey('quotprodrisk',[this,RiskName2],[0,1],null,'1 and a.quotno = #<%=tQuotNo%># and a.quotbatno = #<%=tQuotBatNo%>#','1','1',null);" readonly><input class=codename name=RiskName2 id=RiskName2 readonly elementtype=nacessary></td>
					<td class=title>退费类型</td>
					<td class=input><input class=codeno name=GetType2 id=GetType2 verify="退费类型|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('gp_gettype', [this, GetTypeName2],[0,1] ,null,null,null,1);" onkeyup="showCodeListKey('gp_gettype', [this, GetTypeName2],[0,1],null,null,null,1);" readonly><input class=codename name=GetTypeName2 id=GetTypeName2 readonly elementtype=nacessary></td>
					<td class=title>单位</td>
					<td class=input><input class=codeno name=ValPeriod id=ValPeriod verify="单位|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('insuperiodflag',[this, ValPeriodName], [0,1],null,'1 and code !=#M# ','1','1',null);" onkeyup="return showCodeListKey('insuperiodflag',[this, ValPeriodName], [0,1],null,'1 and code !=#M# ','1','1',null);" readonly><input class=codename name=ValPeriodName id=ValPeriodName readonly elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>起始值（≥）</td>
					<td class=input><input class="wid common" name=ValStartDate id=ValStartDate maxlength=6 verify="起始值|NOTNULL&INT&VALUE>=0" elementtype=nacessary></td>
					<td class=title>终止值（〈）</td>
					<td class=input><input class="wid common" name=ValEndDate id=ValEndDate maxlength=6 verify="终止值|NOTNULL&INT&VALUE>=0" elementtype=nacessary></td>
					<td class=title>费用比例</td>
					<td class=input><input class="wid common" name=FeeValue id=FeeValue maxlength=20 verify="费用比例|NOTNULL&NUM" elementtype=nacessary></td>
				</tr>
			</table>
			<input class=cssButton type=button value="新  增" name="AddButton" onclick="addSubmit();">
			<input class=cssButton type=button value="修  改" name="ModButton" onclick="modSubmit();">
			<input class=cssButton type=button value="删  除" name="DelButton" onclick="delSubmit();">
			<input class=cssButton type=button value="关  闭" onclick="top.close();">	
		</div>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divSysRefundInfo);">
				</td>
				<td class=titleImg>系统默认退费信息维护</td>
			</tr>
		</table>
		<div id="divSysRefundInfo" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanSysRefundInfoGrid"></span>
					</td>
				</tr>
			</table>
			<center>		
				<input class=cssButton90 type=button value="首  页" onclick="turnPage2.firstPage();">
				<input class=cssButton91 type=button value="上一页" onclick="turnPage2.previousPage();">
				<input class=cssButton92 type=button value="下一页" onclick="turnPage2.nextPage();">
				<input class=cssButton93 type=button value="尾  页" onclick="turnPage2.lastPage();">
			</center>
		</div>
	</div>
	<input type=hidden name=Operate>
	<input type=hidden name=SerialNo>
	<Br /><Br /><Br /><Br />
</form>
<span id="spanCode" style="display:none; position:absolute; slategray"></span>
</body>
</html>
