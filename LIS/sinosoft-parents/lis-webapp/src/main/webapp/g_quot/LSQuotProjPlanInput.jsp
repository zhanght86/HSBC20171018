<%
/***************************************************************
 * <p>ProName：LSQuotProjPlanInput.jsp</p>
 * <p>Title：项目询价方案信息录入</p>
 * <p>Description：项目询价方案信息录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-04-02
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
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
	var tTranProdType = "";//产品类型
</script>
<html>
<head>
	<title>项目询价录入</title>
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
	<script src="./LSQuotCommonInput.js"></script>
	<script src="./LSQuotPubPlan.js"></script>
	<script src="./LSQuotProjPlanInput.js"></script>
	<%@include file="./LSQuotProjPlanInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<!-- 待录入询价查询条件 -->
<form name=fm id=fm method=post action="" target=fraSubmit>
	<div id="tab1">
		<ul>
		   <li onclick="goToStep(1)" >1、基本信息</li>
		   <li onclick="goToStep(2)" class="now">2、方案信息</li>
		   <li onclick="goToStep(3)">3、询价提交</li>
		   <li onmouseover="setTabOver(1,3)" onmouseout="setTabOut(1,3)" onclick="showAttachment();" class="libutton">附件管理</li>
		   <li onmouseover="setTabOver(1,4)" onmouseout="setTabOut(1,4)" onclick="showQuesnaire();" class="libutton">问卷调查</li>
		   <li onmouseover="setTabOver(1,5)" onmouseout="setTabOut(1,5)" onclick="showPast();" class="libutton">既往信息</li>
		   <li onmouseover="setTabOver(1,6)" onmouseout="setTabOut(1,6)" onclick="showFeeInfo();" class="libutton">费用信息</li>
		   <li onmouseover="setTabOver(1,7)" onmouseout="setTabOut(1,7)" onclick="showRequest();" class="libutton">业务需求</li>
		</ul>
	</div>
</form>
<div class="tablist block">
	<form name=fm2 id=fm2 method=post action="" target=fraSubmit>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo1);">
				</td>
				<td class=titleImg>询价－方案信息维护<span style="color: red">（第一步）</span></td>
			</tr>
		</table>
		<div id="divInfo1"   name="divInfo1" style="display: ''">
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
		
		<div id="divInfo2" style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>方案描述</td>
					<td class=input><input class="wid common" name=PlanDesc id=PlanDesc elementtype=nacessary></td>
					<td class=title>方案编码</td>
					<td class=input><input class="wid readonly" name=PlanCode id=PlanCode readonly></td>
					<td class=title style="display: 'none'">系统方案编码</td>
					<td class=input style="display: 'none'"><input class="wid readonly" name=SysPlanCode id=SysPlanCode readonly></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
                <tr class=common>
					<td class=title>保险期限</td>
					<td class=input><input class="wid common" style="width:113px" name=InsuPeriod  id=InsuPeriod verify="保险期限|INT&value>0"><input type=hidden name=InsuPeriodFlag id=InsuPeriodFlag type><input class=codeno name=InsuPeriodFlagName id=InsuPeriodFlagName verify="保险期间单位|notnull" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('insuperiodflag', [this,InsuPeriodFlag], [1,0], null, null, null, '1', null);" onkeyup="return showCodeListKey('insuperiodflag', [this,InsuPeriodFlag], [1,0], null, null, null, '1', null);" elementtype=nacessary readonly></td>
					<td class=title id=tdPlan5 name=tdPlan5 style="display: 'none'">方案类型</td>
					<td class=input id=tdPlan6 name=tdPlan6 style="display: 'none'"><input class=codeno name=PlanType id=PlanType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('plantype',[this, PlanTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('plantype',[this, PlanTypeName],[0,1]);" readonly><input class=codename name=PlanTypeName id=PlanTypeName readonly elementtype=nacessary></td>
					<td class=title id=tdPlan8 name=tdPlan8 style="display: 'none'">方案标识</td>
					<td class=input id=tdPlan9 name=tdPlan9 style="display: 'none'"><input class=codeno name=PlanFlag id=PlanFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('planflag', [this,PlanFlagName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('planflag', [this,PlanFlagName], [0,1], null, null, null, '1', null);"><input class=codename name=PlanFlagName id=PlanFlagName readonly elementtype=nacessary></td>
				</tr>
                <tr class=common>
                    <td class=title id=tdPlan10 name=tdPlan10 style="display: 'none'">保费计算方式</td>
					<td class=input id=tdPlan11 name=tdPlan11 style="display: 'none'"><input class=codeno name=PremCalType id=PremCalType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('engincaltype', [this,PremCalTypeName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('engincaltype', [this,PremCalTypeName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=PremCalTypeName  id=PremCalTypeName readonly elementtype=nacessary></td>
					<td class=title id=tdPlan12 name=tdPlan12 style="display: 'none'">方案人数</td>
					<td class=input id=tdPlan13 name=tdPlan13 style="display: 'none'"><input class="wid common" name=PlanPeople id=PlanPeople elementtype=nacessary></td>
					<td class=title id=tdPlan14 name=tdPlan14 style="display: 'none'">最低工程造价(元)</td>
					<td class=input id=tdPlan15 name=tdPlan15 style="display: 'none'"><input class="wid common" name=EnginCost id=EnginCost elementtype=nacessary></td>
				</tr>
                <tr class=common>                
                    <td class=title id=tdPlan16 name=tdPlan16 style="display: 'none'">最低工程面积(平方米)</td>
					<td class=input id=tdPlan17 name=tdPlan17 style="display: 'none'"><input class="wid common" name=EnginArea id=EnginArea elementtype=nacessary></td>
					<td class=title id=tdPlan1 name=tdPlan1 style="display: ''"></td>
					<td class=input id=tdPlan2 name=tdPlan2 style="display: ''"></td>
					<td class=title id=tdPlan3 name=tdPlan3 style="display: ''"></td>
					<td class=input id=tdPlan4 name=tdPlan4 style="display: ''"></td>
				<tr>
				<tr class=common id=trPlan1 name=trPlan1 style="display: 'none'">
					<td class=title>职业类型</td>
					<td class=input colspan=5><input class=radio type="radio" id=OccupTypeRadio1 name=OccupTypeRadio onclick="chooseOccupFlag('1');">单一职业类别<input class=radio type="radio" id=OccupTypeRadio2 name=OccupTypeRadio onclick="chooseOccupFlag('2');">多职业类别<input type=hidden name=OccupTypeFlag id=OccupTypeFlag></td>
				</tr>
				<tr class=common id=trOccupType1 name=trOccupType1>
					<td class=title>职业类别</td>
					<td class=input><input class=codeno name=OccupType id=OccupType style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return returnShowCodeList('occuptype1',[this, OccupTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('occuptype1',[this, OccupTypeName],[0,1]);" readonly><input class=codename name=OccupTypeName id=OccupTypeName readonly elementtype=nacessary></td>
					<td class=title>职业中分类</td>
					<td class=input><input class=codeno name=OccupMidType id=OccupMidType style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return returnShowCodeList('occupmidtype',[this, OccupMidTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('occupmidtype',[this,OccupMidTypeName],[0,1]);" readonly><input class=codename name=OccupMidTypeName id=OccupMidTypeName readonly><span id=spanOccupMid name=spanOccupMid style="display: 'none';color: red">*</span></td>
					<td class=title>工种</td>
					<td class=input nowrap><input class=codeno name=OccupCode id=OccupCode style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return returnShowCodeList('occupcode',[this, OccupCodeName],[0,1]);" onkeyup="return returnShowCodeListKey('occupcode',[this,OccupCodeName],[0,1]);" readonly><input class=codename name=OccupCodeName id=OccupCodeName onkeydown="fuzzyQueryOccupCode(OccupCode,OccupCodeName)"><span id=spanOccupCode name=spanOccupCode style="display: 'none';color: red">*</span><span style="color: red">(支持模糊查询)</span></td>
				</tr>
				<tr class=common id=trOccupType2 name=trOccupType2>
					<td class=title>最低职业类别</td>
					<td class=input><input class=codeno name=MinOccupType id=MinOccupType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('occuptype2',[this, MinOccupTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('occuptype2',[this, MinOccupTypeName],[0,1]);" readonly><input class=codename name=MinOccupTypeName id=MinOccupTypeName readonly elementtype=nacessary></td>
					<td class=title>最高职业类别</td>
					<td class=input><input class=codeno name=MaxOccupType id=MaxOccupType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('occuptype2',[this, MaxOccupTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('occuptype2',[this, MaxOccupTypeName],[0,1]);" readonly><input class=codename name=MaxOccupTypeName id=MaxOccupTypeName readonly elementtype=nacessary></td>
					<td class=title>职业比例</td>
					<td class=input><input class="wid common" name=OccupRate id=OccupRate elementtype=nacessary></td>
				</tr>
				<tr class=common id=trPlan2 name=trPlan2 style="display: 'none'">
					<td class=title>最低年龄(岁)</td>
					<td class=input><input class="wid common" name=MinAge id=MinAge verify="最低年龄(岁)|INT&value>=0"></td>
					<td class=title>最高年龄(岁)</td>
					<td class=input><input class="wid common" name=MaxAge id=MaxAge verify="最高年龄(岁)|INT&value>=0"></td>
					<td class=title>平均年龄(岁)</td>
					<td class=input><input class="wid common" name=AvgAge id=AvgAge verify="平均年龄(岁)|INT&value>=0" elementtype=nacessary></td>
				</tr>
				<tr class=common id=trPlan3 name=trPlan3 style="display: 'none'">
					<td class=title>人数</td>
					<td class=input><input class="wid common" name=NumPeople id=NumPeople verify="人数|INT&value>=0"></td>
					<td class=title>参加社保占比</td>
					<td class=input><input class="wid common" name=SocialInsuRate id=SocialInsuRate verify="参加社保占比|num&value>=0&value<=1" elementtype=nacessary></td>
					<td class=title>男女比例</td>
					<td class=input><input class="wid common" style="width:50px" name=MaleRate id=MaleRate verify="男女比例|INT&value>=0" maxlength=9><b>：</b><input class="wid common" style="width:50px" name=FemaleRate id=FemaleRate verify="男女比例|INT&value>=0" maxlength=9><font color=red> * (如 2:3)</font></td>
				</tr>
				<tr class=common id=trPlan4 name=trPlan4 style="display: 'none'">
					<td class=title>退休占比</td>
					<td class=input><input class="wid common" name=RetireRate id=RetireRate verify="退休占比|num&value>=0&value<=1"></td>
					<td class=title>保费分摊方式</td>
					<td class=input><input class=codeno name=PremMode id=PremMode style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('premmode', [this,PremModeName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('premmode', [this,PremModeName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=PremModeName id=PremModeName readonly></td>
					<td class=title>企业负担占比</td>
					<td class=input><input class="wid common" name=EnterpriseRate id=EnterpriseRate verify="企业负担占比|num&value>=0&value<=1"></td>
				</tr>
				<tr class=common id=trPlan5 name=trPlan5 style="display: 'none'">
					<td class=title>最低月薪(元)</td>
					<td class=input><input class="wid common" name=MinSalary id=MinSalary verify="最低月薪(元)|num&value>=0"></td>
					<td class=title>最高月薪(元)</td>
					<td class=input><input class="wid common" name=MaxSalary id=MaxSalary verify="最高月薪(元)|num&value>=0"></td>
					<td class=title>平均月薪(元)</td>
					<td class=input><input class="wid common" name=AvgSalary id=AvgSalary verify="平均月薪(元)|num&value>=0"></td>
				</tr>
				<tr class=common id=trPlan6 name=trPlan6 style="display: 'none'">
					<td class=title id=tdEngin1 name=tdEngin1 style="display: 'none'">最高工程造价(元)</td>
					<td class=input id=tdEngin2 name=tdEngin2 style="display: 'none'"><input class="wid common" name=MaxEnginCost id=MaxEnginCost elementtype=nacessary></td>
					<td class=title id=tdEngin3 name=tdEngin3 style="display: 'none'">最高工程面积(平方米)</td>
					<td class=input id=tdEngin4 name=tdEngin4 style="display: 'none'"><input class="wid common" name=MaxEnginArea id=MaxEnginArea elementtype=nacessary></td>
					<td class=title>工程类型</td>
					<td class=input><input class=codeno name=EnginType id=EnginType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('engintype', [this,EnginTypeName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('engintype', [this,EnginTypeName], [0,1], null, null, null, '1', null);"><input class=codename name=EnginTypeName id=EnginTypeName readonly elementtype=nacessary></td>
				</tr>
                <tr class=common>
                    <td class=title>施工天数</td>
					<td class=input><input class="wid common" name=EnginDays id=EnginDays maxlength=5  verify="施工天数|INT&value>0" elementtype=nacessary></td>
					<td class=title id=tdEngin5 name=tdEngin5 style="display: ''"></td>
					<td class=input id=tdEngin6 name=tdEngin6 style="display: ''"></td>
				</tr>
			</table>
			<div id=divEnginFactor>
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
				<tr class=common id=trPlan7 name=trPlan7 style="display: 'none'">
					<td class=title>工程概述</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=EnginDesc id=EnginDesc elementtype=nacessary></textarea></td>
				</tr>
				<tr class=common id=trEnginCondition name=trEnginCondition style="display: 'none'">
					<td class=title>工程状况说明</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=EnginCondition id=EnginCondition elementtype=nacessary></textarea><span style="color: red">(请说明各标段的地质、水文条件、常见自然灾害及损失情况)</span></td>
				</tr>
				<tr class=common>
					<td class=title>其他说明</td>
					<td class=input colspan=5><textarea class=common cols=80 rows=2 name=OtherDesc id=OtherDesc verify="其他说明|len<=1000"></textarea></td>
				</tr>
			</table>
			<input class=cssButton type=button value="新  增" onclick="addPlan();">
			<input class=cssButton type=button value="修  改" onclick="modifyPlan();">
			<input class=cssButton type=button value="删  除" onclick="delPlan();">
		</div>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo3);">
				</td>
				<td class=titleImg>询价－方案明细信息维护<span style="color: red">（第二步）</span></td>
			</tr>
		</table>
		<div id="divInfo3" class=maxbox1>
			<input class=cssButton type=button value="方案信息维护" onclick="planDetailOpen();">
			<input class=cssButton type=button value="方案明细一览" onclick="showAllDetail();">
		</div>
	</form>
	<form name=fmupload id=fmupload method=post action="./LDAttachmentSave.jsp" ENCTYPE="multipart/form-data" target=fraSubmit>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo6);">
				</td>
				<td class=titleImg>询价方案信息导入</td>
			</tr>
		</table>
		<div id="divInfo6" class=maxbox1>
			<table class=common>
				<tr class=common>
					<td class=title>导入文件</td>
					<td class=input colspan=5><input class="wid common" type=file id=UploadPath name=UploadPath style="width:400px"  elementtype=nacessary><input class=cssButton type=button value="方案导入" onclick="impPlanSubmit();">&nbsp;&nbsp;<input class=cssButton type=button value="导入信息查看" onclick="showPlanSubmit();">&nbsp;&nbsp;<a href='javascript:downTemplate();'>下载导入模板</href></td>
				</tr>
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
	</form>
	<form name=fmOther id=fmOther method=post action="" target=fraSubmit>
		<div name=otherDiv>
			<hr class="line"/>
			<center>
				<input class=cssButton type=button value="上一步" onclick="previousStep();">
				<input class=cssButton type=button value="下一步" onclick="nextStep();">
				<input class=cssButton type=button value="回目录" onclick="ReturnList('01');">
				<input class=cssButton type=button id=enterQuest name=enterQuest value="问题件查询" onclick="goToQuestion('0');" style="display: 'none'">
			</center>
		<div>
	</form>
</div>
<form name=fmPub id=fmPub method=post action="" target=fraSubmit>
	<div style="display: 'none'">
		<input type=hidden name=Operate id=Operate>
		<input type=hidden name=HiddenCodeType id=HiddenCodeType>
	</div>
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
