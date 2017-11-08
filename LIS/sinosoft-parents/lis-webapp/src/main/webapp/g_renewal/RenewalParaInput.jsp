<%
/***************************************************************
 * <p>ProName：RenewalParaInput.jsp</p>
 * <p>Title：续期参数配置</p>
 * <p>Description：续期参数配置</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-06-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
</script>
<html>
<head >
	<title>续期参数配置</title>
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
	<script src="./RenewalParaInput.js"></script>
	<%@include file="./RenewalParaInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
		 	<td class=titleImg>查询条件</td>
		</tr>
	</table>
	<div id="divQuery" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>管理机构</td>
				<td class=input><input class=codeno name=ManageComQ readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('managecom',[this,ManageComQName],[0,1],null,'1','1','1',180);" 
					onkeyup="return showCodeListKey('managecom',[this, ManageComName],[0,1],null,'1','1','1',180);"><input class=codename name=ManageComQName readonly></td>
				<td class=title>缴费频率</td>
				<td class=input><input class=codeno name=PayIntvQ readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('queryexp',[this, PayIntvQName],[0, 1],null,'1 and codetype=#payintv# and codeexp=#1#','1','1',180);" 
					onkeyup="return showCodeListKey('queryexp',[this, PayIntvQName],[0, 1],null,'1 and codetype=#payintv# and codeexp=#1#','1','1',180);"><input class=codename name=PayIntvQName readonly></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		
		<input class=cssButton name=QueryButton type=button value="查  询" onclick="queryClick(1);">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divContInfoGrid);">
			</td>
			<td class=titleImg>查询结果</td>
		</tr>
	</table>
	<div id="divContInfoGrid" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanResultInfoGrid"></span>
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
				<td class=title>承保机构</td>
				<td class=input><input class=codeno name=ManageCom readonly verify="承保机构|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick=" returnShowCodeList('conditioncomcode',[this,ManageComName],[0,1]);" 
					onkeyup="return returnShowCodeListKey('conditioncomcode',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly elementtype=nacessary></td>
				<td class=title>缴费频率</td>
				<td class=input><input class=codeno  name=PayIntv readonly verify="缴费频率|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('queryexp',[this, PayIntvName],[0, 1],null,'1 and codetype=#payintv# and codeexp=#1#','1','1',180);" 
					onkeyup="return showCodeListKey('queryexp',[this, PayIntvName],[0, 1],null,'1 and codetype=#payintv# and codeexp=#1#','1','1',180);"><input class=codename name=PayIntvName readonly elementtype=nacessary></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>提前抽档天数(天)</td>
				<td class=input><input class="wid common" name=PumpDays verify="抽档天数|notnull&int&value>0" elementtype=nacessary></td>
				<td class=title>宽限期(天)</td>
				<td class=input><input class="wid common" name=GracePeriod verify="宽限期|notnull&int&value>0" elementtype=nacessary></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="增  加" onclick="addClick();">
		<input class=cssButton type=button value="修  改" onclick="updateClick();">
		<input class=cssButton type=button value="删  除" onclick="deleteClick();">
	</div>
	<hr class=line />
	保单宽限期维护<input class=checkbox name=GracePeriodCheck type=checkbox onclick="showGracePeriod();">
	
<div id=DivGracePeriod style="display:none">
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divContQueryGrid);">
			</td>
			<td class=titleImg>保单查询</td>
		</tr>
	</table>
	<div id="divContQueryGrid" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>投保人名称</td>
				<td class=input><input class="wid common" name=GrpName id=GrpName></td>
				<td class=title>保单号</td>
				<td class=input><input class="wid common" name=GrpContNo id=GrpContNo></td>
				<td class=title>投保书号</td>
				<td class=input><input class="wid common" name=GrpPropNo id=GrpPropNo></td>
			</tr>
			<tr class=common>
				<td class=title>承保机构</td>
				<td class=input><input class=codeno name=ManageCom2 id=ManageCom2 readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('conditioncomcode',[this, ManageCom2Name],[0, 1],null,'1 and comgrade=#03#','1','1',180);" 
					onkeyup="return showCodeListKey('conditioncomcode',[this, ManageCom2Name],[0, 1],null,'1 and comgrade=#03#','1','1',180);"><input class=codename name=ManageCom2Name readonly></td>
				<td class=title>销售渠道</td>
				<td class=input><input class=codeno name=SaleChnl id=SaleChnl readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('agenttype',[this, SaleChnlName],[0, 1],null,'1','1','1',180);" 
					onkeyup="return showCodeListKey('agenttype',[this, SaleChnlName],[0, 1],null,'1','1','1',180);"><input class=codename name=SaleChnlName readonly></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="保单查询" onclick="queryCont(1);">
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divGrpContInfoGrid);">
			</td>
			<td class=titleImg>团体保单信息</td>
		</tr>
	</table>
	<div id=divGrpContInfoGrid >
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanContInfoGrid"></span>
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
	<table class=common>
		<tr class=common>
			<td class=title>宽限期(天)</td>
			<td class=input><input class="wid common" id=PGracePeriod name=PGracePeriod elementtype=nacessary></td>
			<td class=title></td>
			<td class=input></td>
			<td class=title></td>
			<td class=input></td>
		</tr>
	</table>
	<input class=cssButton type=button value="保  存" onclick="saveClick();">
</div>
	<!--隐藏区-->
	<input type=hidden name=Operate>
	<input type=hidden name=SerialNo>
	<input type=hidden name=PPolicyNo>
	<input type=hidden name=PSerialNo>
	<Br /><Br /><Br /><Br />
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
