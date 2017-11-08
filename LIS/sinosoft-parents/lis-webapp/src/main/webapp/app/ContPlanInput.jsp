<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
String GrpContNo = request.getParameter("GrpContNo");
String LoadFlag = request.getParameter("LoadFlag");
%>
<head>
<script>
GrpContNo="<%=GrpContNo%>";
LoadFlag="<%=LoadFlag%>";
</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="ContPlan.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="ContPlanInit.jsp"%>
<title>团体保险计划定制 </title>
</head>
<body onload="initForm();">
	<form method=post name=fm id="fm" target="fraSubmit" action="ContPlanSave.jsp">
		<table>
			<tr>
				<td class=common>
    				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
				</td>
				<td class= titleImg>合同信息</td>
			</tr>
		</table>
		<div class="maxbox1">
		<div  id= "divFCDay" style= "display: ''"> 
		<table  class= common align=center>
			<TR  class= common>
				<TD  class= title>集体合同号</TD>
				<TD  class= input>
					<Input class="readonly wid" readonly name=GrpContNo id="GrpContNo" value="<%=GrpContNo%>">
					<Input type=hidden name=ProposalGrpContNo id="ProposalGrpContNo">
					<input type=hidden name=mOperate id="mOperate">
				</TD>
				<TD  class= title>管理机构</TD>
				<TD  class= input>
					<Input class="readonly wid" readonly name=ManageCom id="ManageCom">
				</TD>
				<TD  class= title>投保人客户号</TD>
				<TD  class= input>
					<Input class="readonly wid" readonly name=AppntNo id="AppntNo">
				</TD>
			</TR>
			<TR  class= common>
				
				<TD  class= title>投保单位名称</TD>
				<TD  class= input>
					<Input class="readonly wid"  readonly name=GrpName id="GrpName">
				</TD>
			</TR>
		</table>
	</div>
	</div>
		<table>
			<tr class=common>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
				</td>
				<td class= titleImg>已存在保险计划（<!-- <Input VALUE="查询核保测算保险计划" class=cssButton TYPE=button onclick="QueryAskPlanClick();"> --><a href="javascript:void(0)" class=button style="font-weight:normal;" onclick="QueryAskPlanClick();">查询核保测算保险计划</a>）</td>
			</tr>
		</table>
		<table  class= common>
			<tr  class= common>
				<td text-align: left colSpan=1>
					<span id="spanContPlanCodeGrid" ></span>
				</td>
			</tr>
		</table>
		<table>
			<tr class=common>
				<td class=common>
    				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay1);">
				</td>
				<td class= titleImg>保险计划定制</td>
			</tr>
		</table>
		<div class="maxbox1">
		<div  id= "divFCDay1" style= "display: ''">
		<table  class= common align=center>
			<TR  class= common>
				<TD  class= title>保障级别</TD>
				<TD  class= input>
					<Input class="common wid"  name=ContPlanCode id="ContPlanCode" maxlength="2" onblur="ChangePlan();">
				</TD>
				<TD  class= title>分类说明</TD>
				<TD  class= input>
					<input class="common wid" name=ContPlanName id="ContPlanName">
				</TD>
				<TD  id="divableinsurepeople" style="display:none" class= title>可保人数</TD>
				<TD  id="idvablepeople" style="display:none" class= input>
					<Input class="common wid" name=Peoples3 id="Peoples3">
				</TD>
				<Input type=hidden name=PlanSql id="PlanSql">
				
				<TD id="divriskcodename" class= title>险种代码</TD>
				<TD id="divriskcode"  class= input>
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=RiskCode id="RiskCode" onchange="CheckRisk(1);" onclick="return showCodeList('GrpRisk',[this,RiskCodeName,RiskFlag],[0,1,3],null,fm.GrpContNo.value,'b.GrpContNo',1);" ondblclick="return showCodeList('GrpRisk',[this,RiskCodeName,RiskFlag],[0,1,3],null,fm.GrpContNo.value,'b.GrpContNo',1);" onkeyup="return showCodeListKey('GrpRisk',[this,RiskCodeName,RiskFlag],[0,1,3],null,fm.GrpContNo.value,'b.GrpContNo');"><Input class=codename name="RiskCodeName" id="RiskCodeName" readonly=true>
					<input type=hidden name=RiskFlag id="RiskFlag">
				</TD>
			</TR>
			<TR>
				<!--<TD  class= title>计划类别</TD>
				<TD  class= input>
					<Input class=codeno name=RiskPlan1 CodeData='0|^0|非固定计划^1|保险套餐' value=0 ondblclick="return showCodeListEx('RiskPlan1',[this,RiskPlan1Name],[0,1]);" onkeyup="return showCodeListKeyEx('RiskPlan1',[this,RiskPlan1Name],[0,1]);"><Input class=codename name="RiskPlan1Name" readonly=true value="非固定计划">
				</TD> -->

				<TD id="divmainriskname" style="display:none" class=title>主险编码</TD>
				<TD id="divmainrisk" style="display:none" class=input>
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno maxlength=6 name=MainRiskCode id="MainRiskCode" onclick="return showCodeList('GrpMainRisk',[this,MainRiskCodeName],[0,1],null,fm.GrpContNo.value,'b.GrpContNo',1);" ondblclick="return showCodeList('GrpMainRisk',[this,MainRiskCodeName],[0,1],null,fm.GrpContNo.value,'b.GrpContNo',1);" onkeyup="return showCodeListKey('GrpMainRisk',[this,MainRiskCodeName],[0,1],null,fm.GrpContNo.value,'b.GrpContNo');"><Input class=codename name="MainRiskCodeName" id="MainRiskCodeName" readonly=true>
				</TD>
				<TD id="divcontplanname" style="display:none" class=title>保险套餐</TD>
				<TD id="divcontplan" style="display:none" class=input>
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno maxlength=6 name=RiskPlan id="RiskPlan" onclick="return showCodeList('RiskPlan',[this,RiskPlanName],[0,1],null,'','',1);" ondblclick="return showCodeList('RiskPlan',[this,RiskPlanName],[0,1],null,'','',1);" onkeyup="return showCodeListKey('RiskPlan',[this,RiskPlanName],[0,1],null,'','');"><Input class=codename name="RiskPlanName" id="RiskPlanName" readonly=true>
				</TD>
			</TR>
		</table>
		</div>
		</div>
		<div>
			<a href="javascript:void(0)" class=button onclick="QueryDutyClick();">险种责任查询</a>
		</div>
		<br>
		<!-- <Input VALUE="险种责任查询" class=cssButton TYPE=button onclick="QueryDutyClick();"> -->
		<div>
			<table  class= common>
				<tr  class= common>
					<td text-align: left colSpan=1>
						<span id="spanContPlanDutyGrid" ></span>
					</td>
				</tr>
			</table>
			<Div  id = "divPage" align=center style = "display: none ">
    			<INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage.firstPage();">
    			<INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage.previousPage();">
    			<INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage.nextPage();">
    			<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage.lastPage();">
		    </Div>
		</div>
		<a href="javascript:void(0)" class=button name="btnDefContPlanDuty" onclick="AddContClick();">定义保险计划要素</a>
		<!-- <Input VALUE="定义保险计划要素" name="btnDefContPlanDuty" class=cssButton  TYPE=button onclick="AddContClick();"> -->
		<!--table class=common>
			<TR  class= common>
				<TD>
					<Input VALUE="添加险种到保险计划" class=cssButton  TYPE=button onclick="AddContClick();">
				</TD>
			</TR>
			<TR  class= common>
				<TD  class= title>保险计划类型</TD>
				<TD  class= input>
					<Input class="code" name=ContPlanType ondblclick="showCodeList('contplantype',[this]);" onkeyup="showCodeListKey('contplantype',[this]);">
				</TD>
			</TR>
		</table-->
		<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
				</td>
				<td class= titleImg>保险计划详细信息</td>
			</tr>
		</table>
		<div>
			<table  class= common>
				<tr  class= common>
					<td text-align: left colSpan=1>
						<span id="spanContPlanGrid" ></span>
					</td>
				</tr>
			</table>
			<Div  id = "divPage" align=center style = "display: 'none' ">
    			<INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage.firstPage();">
    			<INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage.previousPage();">
    			<INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage.nextPage();">
    			<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage.lastPage();">
		    </Div>
		</div>
		<Div  id= "divRiskPlanSave" style= "display: ''" align= right> 
			<a href="javascript:void(0)" class=button style= "display: 'none'" onclick="">保险计划查询</a>
			<a href="javascript:void(0)" class=button onclick="submitForm();">保险计划保存</a>
			<a href="javascript:void(0)" class=button onclick="UptContClick();">保险计划修改</a>
			<a href="javascript:void(0)" class=button onclick="DelContClick();">保险计划删除</a>
		<!-- <INPUT VALUE="保险计划查询" class="cssButton" style= "display: 'none'" TYPE=button onclick="">
		<INPUT VALUE="保险计划保存" class="cssButton"  TYPE=button onclick="submitForm();">
		<INPUT VALUE="保险计划修改" class="cssButton"  TYPE=button onclick="UptContClick();">
		<INPUT VALUE="保险计划删除" class="cssButton"  TYPE=button onclick="DelContClick();"> -->
		</Div>
	    <!-- <INPUT VALUE="上一步" class="cssButton"  TYPE=button onclick="returnparent();"> -->
	    	<a href="javascript:void(0)" class=button onclick="returnparent();">上一步</a>
	    	<br>
		<Div  id= "divRiskPlanRela" style= "display: none" align= left>     
			<a href="javascript:void(0)" class=button onclick="nextstep();">保险计划要约录入</a>        
			<!-- <INPUT VALUE="保险计划要约录入" class="cssButton"  TYPE=button onclick="nextstep();"> -->
		</Div>
	</form>
	<br>
	<br>
	<br>
	<br>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
