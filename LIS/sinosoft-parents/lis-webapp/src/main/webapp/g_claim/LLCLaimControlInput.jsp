<%
/***************************************************************
 * <p>ProName：LLCLaimControlInput.jsp</p>
 * <p>Title：理赔责任控制</p>
 * <p>Description：理赔责任控制</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 杨治纲
 * @version  : 8.0
 * @date     : 2014-05-05
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String tFlag = request.getParameter("Flag");
	String tBussType = request.getParameter("BussType");
	String tBussNo = request.getParameter("BussNo");
	String tSubBussNo = request.getParameter("SubBussNo");
%>
<html>
<head>
	<title>理赔责任控制</title>
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
	<script src="./LLCLaimControlInput.js"></script>
	<%@include file="./LLCLaimControlInit.jsp"%>

<script>
	var tButtonFlag = "<%=tFlag%>";
	var tBussType = "<%=tBussType%>";
	var tBussNo = "<%=tBussNo%>";
	var tSubBussNo = "<%=tSubBussNo%>";
</script>

<style type="text/css">
	
	*{margin:0;padding:0;}
	
	li{list-style:none;}
	
	#tab1,#tab2{width:1330px;height:34px;border:1px #cfedff solid;border-bottom:0;background:url(../common/images/tab.gif) repeat-x;}
	#tab1 ul,#tab2 ul{margin:0;padding:0;}
	#tab1 li,#tab2 li{float:left;padding:0 23px;height:34px;line-height:34px;text-align:center;border-right:1px #ebf7ff solid;cursor:pointer;}
	#tab1 li.now,#tab2 li.now{color:#5299c4;background:#fff;font-weight:bold;font-size:13px;}
	#tab1 li.libutton,#tab2 li.libutton{color:black;padding:0 10px;float:right;}
	#tab1 li.lobutton,#tab2 li.lobutton{color:#5299c4;padding:0 10px;background:#fff;font-weight:bold;float:right;}
	.tablist{width:1330px;height:100%;padding:10px;font-size:14px;line-height:24px;border:1px #cfedff solid;border-top:0;display:none;}
	.block{display:block;}
</style>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LLCLaimControlSave.jsp" target=fraSubmit>
	<div id="tab1">
		<!--
		<ul>
		   <li onmouseover="setTabOver(1,0)" onmouseout="setTabOut(1,0)" onclick="setTab(1,0)" class="now">要素控制</li>
		   <li onmouseover="setTabOver(1,1)" onmouseout="setTabOut(1,1)" onclick="setTab(1,1)">共用控制</li>
		   <li onmouseover="setTabOver(1,2)" onmouseout="setTabOut(1,2)" onclick="setTab(1,2)">给付控制</li>
		</ul>
		-->
	</div>
	<!-- 要素控制 -->
	<div id="tablistdiv0" class="tablist block">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divPlan1Grid);">
				</td>
				<td class=titleImg>保险方案列表</td>
			</tr>
		</table>
		<div id="divPlan1Grid" style="display:''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanPlan1Grid"></span></td>
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
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divDutyControlGrid);">
				</td>
				<td class=titleImg>定制理赔责任要素</td>
			</tr>
		</table>
		<div id="divDutyControlGrid" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>要素类型</td>
					<td class=input><input class=codeno name=DutyFactorType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="getDutyFactorType(DutyFactorType, DutyFactorTypeName);"><input class=codename name=DutyFactorTypeName elementtype=nacessary></td>
					<td class=title>险种</td>
					<td class=input><input class=codeno name=RiskCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="getRisk(RiskCode, RiskName);"><input class=codename name=RiskName elementtype=nacessary></td>
					<td class=title>责任</td>
					<td class=input><input class=codeno name=DutyCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="getDuty(DutyCode, DutyName);"><input class=codename name=DutyName elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>要素</td>
					<td class=input><input class=codeno name=FactorCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="getFactor(FactorCode, FactorName, CalRemark, Params);"><input class=codename name=FactorName elementtype=nacessary></td>
					<td class=title>要素明细</td>
					<td class=input><input class="wid common" name=CalRemark disabled></td>
					<td class=title>要素值</td>
					<td class=input><input class="wid common" name=Params elementtype=nacessary></td>
				</tr>
			</table>
			<input class=cssButton type=button name="InsertDuty" value="增  加" onclick="insertDutyClick();">
		</div>
		
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divConfDutyControlGrid);">
				</td>
				<td class=titleImg>已定制理赔责任要素</td>
			</tr>
		</table>
		<div id="divConfDutyControlGrid" style="display:''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanConfDutyControlGrid"></span></td>
				</tr>
			</table>
			<input class=cssButton type=button name="UpdateDuty" value="修  改" onclick="updateDutyClick();">
			<input class=cssButton type=button name="DeleteDuty" value="删  除" onclick="deleteDutyClick();">
		</div>
	</div>
	<!-- 共用控制 -->
	<div id="tablistdiv1" class="tablist">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divPlan2Grid);">
				</td>
				<td class=titleImg>保险方案列表</td>
			</tr>
		</table>
		<div id="divPlan2Grid"  style="display:''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanPlan2Grid"></span></td>
				</tr>
			</table>
			<center>
				<input class=cssButton90 type=button value="首  页" onclick="turnPage4.firstPage();">
				<input class=cssButton91 type=button value="上一页" onclick="turnPage4.previousPage();">
				<input class=cssButton92 type=button value="下一页" onclick="turnPage4.nextPage();">
				<input class=cssButton93 type=button value="尾  页" onclick="turnPage4.lastPage();">
			</center>
		</div>
		
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divShareControlGrid);">
				</td>
				<td class=titleImg>定制理赔共用控制</td>
			</tr>
		</table>
		<div id="divShareControlGrid" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>要素类型</td>
					<!--return returnShowCodeList('plantype',[this, PlanTypeName],[0,1]); onfocus="getShareFactorType();" -->
					<td class=input><input class=codeno name=ShareFactorType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('ShareFactorType',[this,ShareFactorTypeName,ShareFactorAttribute],[0,1,2]);" onkeyup="return returnShowCodeListKey('ShareFactorType',[this,ShareFactorTypeName,ShareFactorAttribute],[0,1,2]);"><input class=codename name=ShareFactorTypeName elementtype=nacessary></td>
					<!--<td class=input><input class=codeno name=ShareFactorType onfocus="getShareFactorType();" ondblclick="showCodeListEx('ShareFactorType',[this,ShareFactorTypeName,ShareFactorAttribute],[0,1,2],null,null,null,1);" onkeyup="showCodeListKey('ShareFactorType',[this,ShareFactorTypeName,ShareFactorAttribute],[0,1,2],null,null,null,1);"><input class=codename name=ShareFactorTypeName elementtype=nacessary></td>-->
					<td class=title></td>
					<td class=input><input type=hidden name=ShareFactorAttribute></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanShareControlGrid"></span></td>
				</tr>
			</table>
			<input class=cssButton type=button name="InsertShare" value="增  加" onclick="insertShareClick();">
		</div>
		
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divConfShareControlGrid);">
				</td>
				<td class=titleImg>已共用信息列表</td>
			</tr>
		</table>
		<div id="divConfShareControlGrid" style="display:''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanConfShareControlGrid"></span></td>
				</tr>
			</table>
			<input class=cssButton type=button name="DeleteShare" value="删  除" onclick="deleteShareClick();">
		</div>
		
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divConfDutyShareControlGrid);">
				</td>
				<td class=titleImg>已共用险种责任信息列表</td>
			</tr>
		</table>
		<div id="divConfDutyShareControlGrid" style="display:''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanConfDutyShareControlGrid"></span></td>
				</tr>
			</table>
		</div>
	</div>
	<!-- 给付控制 -->
	<div id="tablistdiv2" class="tablist">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divPlan3Grid);">
				</td>
				<td class=titleImg>保险方案列表</td>
			</tr>
		</table>
		<div id="divPlan3Grid" style="display:''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanPlan3Grid"></span></td>
				</tr>
			</table>
			<center>
				<input class=cssButton90 type=button value="首  页" onclick="turnPage8.firstPage();">
				<input class=cssButton91 type=button value="上一页" onclick="turnPage8.previousPage();">
				<input class=cssButton92 type=button value="下一页" onclick="turnPage8.nextPage();">
				<input class=cssButton93 type=button value="尾  页" onclick="turnPage8.lastPage();">
			</center>
		</div>
		
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divRiskDutyGrid);">
				</td>
				<td class=titleImg>险种责任信息列表</td>
			</tr>
		</table>
		<div id="divRiskDutyGrid" style="display:''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanRiskDutyGrid"></span></td>
				</tr>
			</table>
		</div>
		
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divGetControlGrid);">
				</td>
				<td class=titleImg>给付责任控制信息列表<font color="red" size="2">【次限额】,【单位津贴额】只能录入其中一项</font></td>
			</tr>
		</table>
		<div id="divGetControlGrid" style="display:''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanGetControlGrid"></span></td>
				</tr>
			</table>
			<input class=cssButton type=button name="SaveGet" value="保  存" onclick="saveGetClick();" >
			 <input class=cssButton type=button value="关  闭" onclick="top.close();" style="margin-left: 1px;margin-top: 4px;">
		</div>
	</div>
      
	<!--隐藏区-->
	<input type=hidden name=Action>
	<input type=hidden name=Operate>
	<input type=hidden name=BussType value="<%=tBussType%>">
	<input type=hidden name=BussNo value="<%=tBussNo%>">
	<input type=hidden name=SubBussNo value="<%=tSubBussNo%>">
	<input type=hidden name=DutySysPlanCode>
	<input type=hidden name=DutyPlanCode>
	<input type=hidden name=ShareSysPlanCode>
	<input type=hidden name=SharePlanCode>
	<input type=hidden name=GetSysPlanCode>
	<input type=hidden name=GetPlanCode>
	<input type=hidden name=GetRiskCode>
	<input type=hidden name=GetDutyCode>
	<input type=hidden name=SheetName>
	<input type=hidden name=SheetName>
	<input type=hidden name=SheetName>
	<input type=hidden name=SheetName>	
	<input type=hidden name=SheetTitle>
	<input type=hidden name=SheetTitle>
	<input type=hidden name=SheetTitle>
	<input type=hidden name=SheetTitle>	
	<input type=hidden name=SheetSql>
	<input type=hidden name=SheetSql>	
	<input type=hidden name=SheetSql>
	<input type=hidden name=SheetSql>	
	<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
