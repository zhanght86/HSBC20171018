<%
/***************************************************************
 * <p>ProName：EdorMFInput.jsp</p>
 * <p>Title：长险费用变更</p>
 * <p>Description：长险费用变更</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-08-16
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getAttribute("GI");
	String tOperator = tGI.Operator;
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tEdorAppNo = request.getParameter("EdorAppNo"); 
	String tEdorType = request.getParameter("EdorType");
	String tGrpContNo = request.getParameter("GrpContNo");
	String tEdorNo = request.getParameter("EdorNo");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tEdorAppNo = "<%=tEdorAppNo%>";
	var tEdorType = "<%=tEdorType%>";
	var tGrpContNo = "<%=tGrpContNo%>";
	var tEdorNo = "<%=tEdorNo%>";
</script>
<html>
<head>
	<title>长险费用变更</title>
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
	<script src="./EdorMFInput.js"></script>
	<%@include file="./EdorMFInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<div id="tab1">
		<ul>
			<li onclick="goToPordParamStep(0)" class="now">管理费维护</li>
			<li onclick="goToPordParamStep(1)">保全退费算法维护</li>
		</ul>
	</div>
	<div id="tab2" style="display:'none'">
		<ul>
			<li onclick="goToPordParamStep(0)">管理费维护</li>
			<li onclick="goToPordParamStep(1)" class="now">保全退费算法维护</li>
		</ul>
	</div>
</form>
<form name=fm2 id=fm2 method=post action="" target=fraSubmit>
	<div class="tablist block" id=EdorMFFee>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divManageFee);">
				</td>
				<td class=titleImg>管理费信息列表</td>
			</tr>
		</table>
		<div id="divManageFee" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanManageFeeGrid"></span>
					</td>
				</tr>
			</table>
			<center>		
				<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
				<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
				<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
				<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
			</center>
			<table class=common>
				<tr class=common>
					<td class=title>险种</td>
					<td class=input><input class=codeno name=RiskCode id=RiskCode readonly verify="险种|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('contplanrisk',[this,RiskName],[0,1],null,'1 and a.policyno = #<%=tGrpContNo%>#','1','1',null);" onkeyup="return showCodeListKey('contplanrisk',[this,RiskName],[0,1],null,'1 and a.policyno = #<%=tGrpContNo%>#','1','1',null);"><input class=codename name=RiskName elementtype=nacessary> </td>
					<td class=title>账户类型</td>
					<td class=input><input class=codeno name=AccType id=AccType  readonly verify="账户类型|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="selectAccType(this,AccTypeName,RiskCode);" onkeyup="return showCodeListKey('risktoacc',[this,AccTypeName],[0,1],null,null,null,'1',null);" ><input class=codename name=AccTypeName elementtype=nacessary readonly></td>
					<td class=title>管理费类型</td>
					<td class=input><input class=codeno name=FeeType id=FeeType readonly verify="管理费类型|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('mafeetype',[this, FeeTypeName], [0,1],null,null,null,1);" onkeyup="return showCodeListKey('mafeetype',[this, FeeTypeName], [0,1],null,null,null,1);"><input class=codename name=FeeTypeName elementtype=nacessary readonly></td>
				</tr>
				<tr id=allTR1 class=common style="display: 'none'">
					<td class=title>初始管理费扣除方式</td>
					<td class=input><input class=codeno name=DeductType id=DeductType readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="returnShowCodeList('deducttype',[this,DeductTypeName],[0,1]);" onkeyup="returnShowCodeListKey('deducttype',[this,DeductTypeName],[0,1]);"><input class=codename name=DeductTypeName elementtype=nacessary readonly></td>
					<td class=title>初始管理费(元)/比例</td>
					<td class=input><input class="wid common" name=FeeValue id=FeeValue maxlength=20 elementtype=nacessary></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
				<tr id=allTR2 class=common style="display: 'none'">
					<td class=title>月度管理费类型</td>
					<td class=input><input class=codeno name=MonthFeeType id=MonthFeeType readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="returnShowCodeList('deducttype',[this,MonthFeeTypeName],[0,1]);" onkeyup="returnShowCodeListKey('deducttype',[this,MonthFeeTypeName],[0,1]);"><input class=codename name=MonthFeeTypeName elementtype=nacessary readonly></td>
					<td class=title>月度管理费(元)/比例</td>
					<td class=input><input class="wid common" name=MonthValue id=MonthValue maxlength=20 elementtype=nacessary></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
				<tr id=allTR3 class=common style="display: 'none'">
					<td class=title>年度管理费类型</td>
					<td class=input><input class=codeno name=YearFeeType id=YearFeeType readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="returnShowCodeList('deducttype',[this,YearFeeTypeName],[0,1]);" onkeyup="returnShowCodeListKey('deducttype',[this,YearFeeTypeName],[0,1]);"><input class=codename name=YearFeeTypeName elementtype=nacessary readonly></td>
					<td class=title>起始值(≥)</td>
					<td class=input><input class="wid common" name=YearStartValue id=YearStartValue maxlength=6 elementtype=nacessary></td>
					<td class=title>终止值(<)</td>
					<td class=input><input class="wid common" name=YearEndValue id= YearEndValuemaxlength=6 elementtype=nacessary></td>
				</tr>
				<tr id=allTR4 class=common style="display: 'none'">
					<td class=title>管理费(元)/比例</td>
					<td class=input><input class="wid common" name=YearValue maxlength=20 elementtype=nacessary></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
			<div id="divInfoButton1" style="display: 'none'">
				<input class=cssButton type=button value="增  加" name="AddButton" onclick="addSubmit();">
				<input class=cssButton type=button value="修  改" name="ModButton" onclick="modSubmit();">
				<input class=cssButton type=button value="删  除" name="DelButton" onclick="delSubmit();">
			</div>
		</div>
		<hr size="2" color="#D7E1F6">
		<input class=cssButton type=button value="关  闭" onclick="top.close();">
	</div>
		<input type=hidden name=Operate>
		<input type=hidden name=SerialNo>
</form>
<form name=fm3 method=post action="" target=fraSubmit>
<div id=EdorMFCal style="display:'none'">
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
					<td class=title>险种编码</td>
					<td class=input><input class=codeno name=RiskCode1 style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('contplanrisk',[this,RiskName1],[0,1],null,'1 and a.policyno = #<%=tGrpContNo%>#','1','1',null);" onkeyup="return showCodeListKey('contplanrisk',[this,RiskName1],[0,1],null,'1 and a.policyno = #<%=tGrpContNo%>#','1','1',null);" ><input class=codename name=RiskName1></td>					
					<td class=title>退费类型</td>
					<td class=input><input class=codeno name=GetType1 style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('gp_gettype', [this, GetTypeName1],[0,1] ,null,null,null,1);" onkeyup="showCodeListKey('gp_gettype', [this, GetTypeName1],[0,1],null,null,null,1);"><input class=codename name=GetTypeName1 ></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
			<input class=cssButton type=button value="查  询" onclick="query(1);">
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
				<input class=cssButton90 type=button value="首  页" onclick="turnPage2.firstPage();">
				<input class=cssButton91 type=button value="上一页" onclick="turnPage2.previousPage();">
				<input class=cssButton92 type=button value="下一页" onclick="turnPage2.nextPage();">
				<input class=cssButton93 type=button value="尾  页" onclick="turnPage2.lastPage();">
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
					<td class=title>险种编码</td>
					<td class=input><input class=codeno name=RiskCode2 readonly verify="险种|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('contplanrisk',[this,RiskName2],[0,1],null,'1 and a.policyno = #<%=tGrpContNo%>#','1','1',null);" onkeyup="return showCodeListKey('contplanrisk',[this,RiskName2],[0,1],null,'1 and a.policyno = #<%=tGrpContNo%>#','1','1',null);" ><input class=codename name=RiskName2 elementtype=nacessary></td>
					<td class=title>退费类型</td>
					<td class=input><input class=codeno name=GetType2 readonly verify="退费类型|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('gp_gettype', [this, GetTypeName2],[0,1] ,null,null,null,1);" onkeyup="showCodeListKey('gp_gettype', [this, GetTypeName2],[0,1],null,null,null,1);"><input class=codename name=GetTypeName2 elementtype=nacessary></td>
					<td class=title>单位</td>
					<td class=input><input class=codeno name=ValPeriod readonly verify="单位|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('insuperiodflag',[this, ValPeriodName], [0,1],null,'1','1','1',null);" onkeyup="return showCodeListKey('insuperiodflag',[this, ValPeriodName], [0,1],null,null,null,1);"><input class=codename name=ValPeriodName elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>起始值（≥）</td>
					<td class=input><input class="wid common" name=ValStartDate maxlength=6 verify="起始值|NOTNULL&NUM" elementtype=nacessary></td>
					<td class=title>终止值（<）</td>
					<td class=input><input class="wid common" name=ValEndDate maxlength=6 verify="终止值|NOTNULL&NUM" elementtype=nacessary></td>
					<td class=title>费用比例</td>
					<td class=input><input class="wid common" name=FeeValues maxlength=20 verify="费用比例|NOTNULL&NUM" elementtype=nacessary></td>
				</tr>
			</table>
			<div id="divInfoButton2" style="display: 'none'">
				<input class=cssButton type=button value="新  增" name="AddButton" onclick="addsSubmit();">
				<input class=cssButton type=button value="修  改" name="ModButton" onclick="modsSubmit();">
				<input class=cssButton type=button value="删  除" name="DelButton" onclick="delsSubmit();">
			</div>
			<div id="divInfoButton4" style="display: ''">
				<input class=cssButton type=button value="关  闭" onclick="top.close();">	
			</div>
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
				<input class=cssButton90 type=button value="首  页" onclick="turnPage3.firstPage();">
				<input class=cssButton91 type=button value="上一页" onclick="turnPage3.previousPage();">
				<input class=cssButton92 type=button value="下一页" onclick="turnPage3.nextPage();">
				<input class=cssButton93 type=button value="尾  页" onclick="turnPage3.lastPage();">
			</center>
		</div>
	</div>
</div>
	<input type=hidden name=Operate>
	<input type=hidden name=SerialNo>
	<Br><Br><Br><Br><Br>
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
