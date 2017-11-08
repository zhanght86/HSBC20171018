<%
/***************************************************************
 * <p>ProName：LCContPlanDetailInput.jsp</p>
 * <p>Title：方案明细信息录入</p>
 * <p>Description：方案明细信息录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-05-14
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	String tCurrentDate= PubFun.getCurrentDate();
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tGrpPropNo = request.getParameter("GrpPropNo");
	String tContPlanType = request.getParameter("ContPlanType");
	String tQueryFlag = request.getParameter("QueryFlag");
%>
<script>
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tGrpPropNo = "<%=tGrpPropNo%>";
	var tQueryFlag = "<%=tQueryFlag%>";
	var tContPlanType = "<%=tContPlanType%>";//改为页面初始化
	var tCurrentDate = "<%=tCurrentDate%>";
</script>
<html>
<head>
	<title>方案明细录入</title>
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
	<script src="./LCContCommonInput.js"></script>
	<script src="./LCContPlanDetailInput.js"></script>
	<%@include file="./LCContPlanDetailInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm2 id=fm2 method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo1);">
			</td>
			<td class=titleImg>方案明细信息</td>
		</tr>
	</table>
	<div id="divInfo1" name="divInfo1" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanPlanDetailInfoGrid" ></span>
				</td>
			</tr>
		</table>
		<center style="display:none">		
			<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
		</center>
	</div>
	
	<div id="divInfo2" style="display: ''">
		<br/>
		<table class=common>
			<tr class=common style="display: none">
				<td class=title colspan=6><input type=hidden name=SysPlanCode id=SysPlanCode><input class=common name=PlanType id=PlanType><input class=common name=PremCalType><input class=common name=PlanFlag><input class=common name=OccupTypeFlag><input class=common name=OldPlanType><input class=common name=OldPremCalType><input class=common name=OldOccupTypeFlag></td>
			</tr>
			<tr class=common>
				<td class=title>方案编码</td>
				<td class=input><input class=codeno name=PlanCode id=PlanCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('contplan', [this,PlanDesc,SysPlanCode,PlanType,PremCalType,PlanFlag,OccupTypeFlag,PolicyNo], [0,1,2,3,4,5,6,7]);" onkeyup="return returnShowCodeListKey('contplan', [this,PlanDesc,SysPlanCode,PlanType,PremCalType,PlanFlag,OccupTypeFlag,PolicyNo], [0,1,2,3,4,5,6,7]);"><input class=codename name=PlanDesc id=PlanDesc elementtype=nacessary></td>
				<td class=title>险种</td>
				<td class=input><input class=codeno name=RiskCode id=RiskCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('quotrisk',[this,RiskName],[0,1]);" onkeyup="return returnShowCodeListKey('quotrisk',[this,RiskName],[0,1]);"><input class=codename name=RiskName id=RiskName elementtype=nacessary></td>
				<td class=title>责任</td>
				<td class=input><input class=codeno name=DutyCode id=DutyCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('quotduty',[this,DutyName],[0,1]);" onkeyup="return returnShowCodeListKey('quotduty',[this,DutyName],[0,1]);"><input class=codename name=DutyName id=DutyName elementtype=nacessary></td>
			</tr>
		</table>
		<!--责任要素动态域3-->
		<div id="divDutyFactor">
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
		<!--险种责任录入固定域-->
		<table class=common>
			<tr class=common id=idAmnt name=idAmnt style="display: none">
				<td class=title>保额类型</td>
				<td class=input><input class=codeno name=AmntType id=AmntType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('amnttype',[this, AmntTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('amnttype',[this, AmntTypeName],[0,1]);" readonly><input class=codename name=AmntTypeName readonly elementtype=nacessary></td>
				<td class=title id=idFixedAmnt00 name=idFixedAmnt00 style="display: none">固定保额(元)</td>
				<td class=input id=idFixedAmnt01 name=idFixedAmnt01 style="display: none"><input class="wid common" name=FixedAmnt id=FixedAmnt verify="固定保额|num&value>0" elementtype=nacessary></td>
				<td class=title id=idSalaryMult00 name=idSalaryMult00 style="display: none">月薪倍数</td>
				<td class=input id=idSalaryMult01 name=idSalaryMult01 style="display: none"><input class="wid common" name=SalaryMult id=SalaryMult verify="月薪倍数|num&value>0" elementtype=nacessary></td>
				<td class=title id=idMinAmnt00 name=idMinAmnt00 style="display: none">最低保额(元)</td>
				<td class=input id=idMinAmnt01 name=idMinAmnt01 style="display: none"><input class="wid common" name=MinAmnt id=MinAmnt verify="最低保额|num&value>0" elementtype=nacessary></td>
				<td class=title id=idMaxAmnt00 name=idMaxAmnt00 style="display: none">最高保额(元)</td>
				<td class=input id=idMaxAmnt01 name=idMaxAmnt01 style="display: none"><input class="wid common" name=MaxAmnt verify="最高保额|num&value>0" elementtype=nacessary></td>
				<td class=title id=idAmnt00 name=idAmnt00 style="display: ''"></td>
				<td class=input id=idAmnt01 name=idAmnt01 style="display: ''"></td>
				<td class=title id=idAmnt02 name=idAmnt02 style="display: ''"></td>
				<td class=input id=idAmnt03 name=idAmnt03 style="display: ''"></td>
			</tr>
			<tr class=common id=idPrem name=idPrem style="display: none">
				<td class=title>期望保费类型</td>
				<td class=input><input class=codeno name=ExceptPremType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('exceptpremtype',[this, ExceptPremTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('exceptpremtype',[this, ExceptPremTypeName],[0,1]);" readonly><input class=codename name=ExceptPremTypeName readonly elementtype=nacessary></td>
				<td class=title>期望保费(元)/期望费率/费率折扣</td>
				<td class=input colspan=3><input class="wid common" name=ExceptPrem id=ExceptPrem verify="期望保费/期望费率/费率折扣|num&value>=0" elementtype=nacessary> <input class=cssButton type=button id=tryCalButton name=tryCalButton style="display: none" value="试  算" onclick="tryCal();"></td>
			</tr>
			<tr class=common id=idFeeRate name=idFeeRate style="display: none">
				<td class=title>初始保费</td>
				<td class=input><input class="wid common" name=InitPrem id=InitPrem verify="初始保费|num&value>=0" elementtype=nacessary></td>
				<td class=title>期望收益率</td>
				<td class=input><input class="wid common" name=ExceptYield id=ExceptYield verify="期望收益率|num&value>=0"></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common id=trRelation name=trRelation style="display: none">
				<td class=input colspan=6><input class=checkbox type=checkbox id=SetRelation name=SetRelation onclick="setRelationFlag();">主附共用配置<input type=hidden name=RelaShareFlag value="0"></td>
			</tr>
			<tr class=common id=trRelationRate name=trRelationRate style="display: none">
				<td class=title style="display: none">与主被保险人关系</td>
				<td class=input style="display: none"><input class=codeno name=RelaToMain style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('relation', [this, RelaToMainName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('relation', [this, RelaToMainName], [0,1], null, null, null, 1);" readonly><input class=codename name=RelaToMainName readonly elementtype=nacessary></td>
				<td class=title>主被保险人保额占比</td>
				<td class=input><input class="wid common" name=MainAmntRate verify="主被保险人保额占比|num&value>0&value<=1" elementtype=nacessary></td>
				<td class=title>附属被保人保额占比</td>
				<td class=input><input class="wid common" name=RelaAmntRate verify="附属被保人保额占比|num&value>0&value<=1" elementtype=nacessary></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<div id="divDutyFactorRelation" style="display: none">
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
		<table class=common>
			<tr class=common>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title colspan=6>备注</td>
			</tr>
			<tr class=common>
				<td class=title colspan=6><textarea cols=80 rows=3 name=Remark id=Remark></textarea></td>
			</tr>
		</table>
			<div id="divInfoButton" style="display: none">
				<input class=cssButton type=button value="新  增" onclick="addPlanDetail();">
				<input class=cssButton type=button value="修  改" onclick="modifyPlanDetail();">
				<input class=cssButton type=button value="删  除" onclick="delPlanDetail();">
			</div>
			<div id="divClose2" style="display: ''">
				<input class=cssButton type=button value="关  闭" onclick="top.close();">
			</div>
	</div>
	<div id=divInfo4 name=divInfo4 style="display: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo3);">
				</td>
				<td class=titleImg>公共保额使用关联</td>
			</tr>
		</table>
		<div id="divInfo3" name="divInfo1" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanPubAmntRelaPlanGrid" ></span>
					</td>
				</tr>
			</table>
			<center>		
				<input class=cssButton90 type=button value="首  页" onclick="turnPage2.firstPage();">
				<input class=cssButton91 type=button value="上一页" onclick="turnPage2.previousPage();">
				<input class=cssButton92 type=button value="下一页" onclick="turnPage2.nextPage();">
				<input class=cssButton93 type=button value="尾  页" onclick="turnPage2.lastPage();">
			</center>
		
		<br/>
		<div id=divInfo5 name=divInfo5 style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanPubAmntRelaDutyGrid" ></span>
					</td>
				</tr>
			</table>
		</div>
		<div id="divInfoButton2" style="display: none">
			<input class=cssButton type=button value="保  存" onclick="saveClick();">
			<input class=cssButton type=button value="撤  销" onclick="cancelClick();">
			<!-- input class=cssButton type=button value="关  闭" onclick="top.close();" -->
		</div>
		</div>
	</div>
	<div id=divInfo6 name=divInfo6 style="display:none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo7);">
				</td>
				<td class=titleImg>缴费项信息</td>
			</tr>
		</table>
		<div id=divInfo7 name=divInfo7 style="style:none">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanPayFeeGrid" ></span>
					</td>
				</tr>
			</table>
		<div id="divInfoButton3" style="display: none">
			<input class=cssButton type=button value="保  存" onclick="saveInfoClick();">
		</div>
		<div id=divInfo8 name=divInfo8 style="display:none">
			<table>
				<tr>
					<td class=common>
						<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo9);">
					</td>
					<td class=titleImg>投资账户信息</td>
				</tr>
			</table>
			<div id=divInfo9 style="style:none">
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1>
							<span id="spanTZFeeGrid" ></span>
						</td>
					</tr>
				</table>
				<div id="divInfoButton4" style="display: none">
					<input class=cssButton type=button value="保  存" onclick="saveAccClick();">
				</div>
			</div>
		</div>
	</div>
</div>
	<input type=hidden name=PolicyNo>
</form>
<form name=fmPub id=fmPub method=post action="" target=fraSubmit>
	<div style="display: none">
		<input type=hidden name=Operate>
		<input type=hidden name=HiddenCodeType>
	</div>
</form>
<Br /><Br /><Br /><Br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
