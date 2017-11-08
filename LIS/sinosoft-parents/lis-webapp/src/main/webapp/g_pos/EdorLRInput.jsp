<%
/***************************************************************
 * <p>ProName:EdorLRInput.jsp</p>
 * <p>Title:  保单补发</p>
 * <p>Description:保单补发</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-06-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tCurrentDate= PubFun.getCurrentDate();
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tEdorAppNo = request.getParameter("EdorAppNo");
	String tEdorType = request.getParameter("EdorType");
	String tEdorNo = request.getParameter("EdorNo");
	String tGrpContNo = request.getParameter("GrpContNo");

%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tEdorAppNo = "<%=tEdorAppNo%>";
	var tGrpContNo = "<%=tGrpContNo%>";
	var tEdorType = "<%=tEdorType%>";
	var tEdorNo = "<%=tEdorNo%>";
</script>
<html>
<head>
	<title>保单补发</title>
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
	<script src="./EdorLRInput.js"></script>
	<%@include file="./EdorLRInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryCont);">
			</td>
			<td class=titleImg>保单补发</td>
		</tr>
	</table>
	<div id="divQueryCont" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title><input type="checkBox" id="GrpLR"  name="GrpLR" onclick="showGrpLR();">保单补发</td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>保单补发次数</td>
				<td class=input><input class="wid readonly" id=Times name="Times"></td>
				<td class=input><input type=checkBox id="NeedGetMoney"  name="NeedGetMoney" onclick="showMoney();">收取保单补发费用</td>
				<td class=title></td>
				<td class= title>补发费用(元)</td>
				<td class=input><input  name="Money" id=Money value='0' class="wid readonly" readonly></td>
			</tr>				
		</table>
	</div>
	<table class=common>
		<tr class=common>
			<td class=input><input type="checkBox" id="PerLR" onclick="showHiddenInfo();" name="PerLR">个人凭证补发</td>
			<td class=title></td>
			<td class=title></td>
			<td class=input></td>
			<td class=title></td>
			<td class=input></td>
		</tr>
	</table>

<div id=hiddenInfo style="display:none">
	<div id="showOldList" style="display:''">	
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
				</td>
				<td class=titleImg>查询条件</td>
			</tr>
		</table>
		<div id="divQuery" class=maxbox1 style="display: ''">
			<div id="div01" style="display: ''">
				<table class=common>
					<tr class=common>  
						<td class=title>被保险人姓名</td>
						<td class = input ><input class ="wid common"  name="OldInsuredName" id=OldInsuredName></td> 
						<td class=title>证件号码</td>
						<td class = input ><input class ="wid common"  name="OldInsuredIDNo" id=OldInsuredIDNo></td>
						<td class=title>保险方案</td>
						<td class=input><Input class=codeno name=ContPlanCode id=ContPlanCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showContPlanCode(this,ContPlanCodeName,sysPlanCode);" onkeyup="showContPlanCodeName(this,ContPlanCodeName,sysPlanCode);">
						<input class=codename name=ContPlanCodeName ><input type=hidden name=sysPlanCode ></td>
					</tr>
					<tr class=common>
						<td class=title>被保险人客户号</td>
						<td class = input ><input class ="wid common" id=CustomernNo  name="CustomernNo"></td>
						<td class=title></td>
						<td class = input ></td>
						<td class=title></td>
						<td class = input ></td> 
					</tr>
				</table>
				<input class=cssButton type=button value="查  询" onclick="queryOldClick();">	
			</div>
		<br/>
			<div id="div02" style="display: ''">
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1>
							<span id="spanOldInsuredInfoGrid" ></span>
						</td>
					</tr>
				</table>
				<center>
					<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
					<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
					<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
					<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
				</center>	
				<input class=cssButton type=button value="个人凭证补发" onclick="contClick();">
				<input class=cssButton type=button value="整单凭证补发" onclick="grpContClick();">
			</div>
		</div>
	</div>
	
	<!--保单补发列表-->
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divResultUpdate);">
			</td>
			<td class=titleImg>补发列表</td>
		</tr>
	</table>
	<div id="divResultUpdate" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanUpdateInsuredInfoGrid" ></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="首  页" onclick="turnPage2.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage2.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage2.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage2.lastPage();">
		</center>
		<div id="divButton03" style="display: none">
			<input class=cssButton type=button value="撤销个人凭证" onclick="deleteOperate();">
		</div>
	</div>
	<hr class=line>
	
</div>
	<!--补发原因-->
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
			<td class=title>补发原因</td>
			<td class=input colspan=5><textarea cols=80 rows=3 name=Reason id=Reason verify="补发原因|notnull" elementtype=nacessary></textarea></td>
		</tr>
	</table>
	<div id="divButton01" style="display: none">
 	 	<Input type=button  class = cssButton value="保  存" onclick="saveClick();">
 		<Input type=button  class = cssButton value="关  闭" onclick="top.close();">
	</div>
	<div id="divButton02" style="display: none">
 		<Input type=button  class = cssButton value="关  闭" onclick="top.close();">
	</div>
	
	<!--隐藏区-->
	<input type=hidden name=MissionID> <!-- 工作任务ID -->
	<input type=hidden name=SubMissionID> <!-- 子工作任务ID -->
	<input type=hidden name=ActivityID> <!-- 工作节点ID -->
	<input type=hidden name=Operate>
	<input type=hidden name=GrpContNo>
	<input type=hidden name=CurrentDate>
	<input type=hidden name=EdorAppNo>
	<input type=hidden name=EdorNo>
	<input type=hidden name=GrpLRFlag value='0'><!-- 保单补发标记 -->
	<input type=hidden name=FeeLRFlag value='0'><!-- 保单补发费用标记 -->
	<input type=hidden name=PreLRFlag value='0'><!-- 个人凭证标记 -->
	<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
