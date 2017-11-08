<%
/***************************************************************
 * <p>ProName：EdorUWGErrInput.jsp</p>
 * <p>Title：保全核保处理</p>
 * <p>Description：保全核保处理</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tGrpPropNo = request.getParameter("GrpPropNo");
	String tEdorAppNo = request.getParameter("EdorAppNo");
	String tEdorNo =  request.getParameter("EdorNo");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tGrpPropNo = "<%=tGrpPropNo%>";
	var tEdorNo =  "<%=tEdorNo%>";
	var tEdorAppNo =  "<%=tEdorAppNo%>";
		
</script>
<html>
<head >
	<title>核保处理</title>
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
	<script src="./EdorUWGErrInput.js"></script>
	<%@include file="./EdorUWGErrInit.jsp"%>

<script type="text/javascript">	
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	
	function setTab(m,n){
		var menu=document.getElementById("tab"+m).getElementsByTagName("li");
	
		for(i=0;i<2;i++)
		{
		   menu[i].className=i==n?"now":""; 
			if (i==n) {
				document.getElementById("tablistdiv"+i).style.display = "block";
			} else {
				document.getElementById("tablistdiv"+i).style.display = "none";
			}
		}		
		var tFlag = n+1;
		initForm();
	}

	function setTabOver(m,n){
		var menu=document.getElementById("tab"+m).getElementsByTagName("li");
		menu[n].className="lobutton";
	}
	
	function setTabOut(m,n){
		var menu=document.getElementById("tab"+m).getElementsByTagName("li");
		menu[n].className="libutton";
	}
</script>	
	
</head>
<body onload="initForm(); initElementtype();">
	<div id="tab1">
	<ul>
		<li onclick="setTab(1,0)" class="now">规则列表显示</li>
		<li onclick="setTab(1,1)">被保险人列表显示</li>
	</ul>
	</div>
	<div id="tablist1">
			<form name=fm id=fm method=post action="" target=fraSubmit>
				<div id="tablistdiv0" class="tablist block" >
				<!--规则列表显示-->
				<table>
					<tr>
						<td class=common>
							<img  src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divUWGErr);">
						</td>
						<td class=titleImg>保单自动核保信息</td>
					</tr>
				</table>
				<div id="divUWGErr" style="display: ''">
					<table class=common>
						<tr>
							<td colSpan=1>
								<span id="spanUWGCErrGrid"></span>
							</td>
				  	</tr>
			 		</table>
			 		<center>
						<input value="首  页" class=cssButton90 type=button onclick="turnPage1.firstPage();">
						<input value="上一页" class=cssButton91 type=button onclick="turnPage1.previousPage();">
						<input value="下一页" class=cssButton92 type=button onclick="turnPage1.nextPage();">
						<input value="尾  页" class=cssButton93 type=button onclick="turnPage1.lastPage();">
					</center>
					<input class=cssButton type=button value="关  闭" onclick="top.close();">
				</div>
				<br/>
				<!--个人单层级信息-->
				<div id="divUWGErr1" style="display: none">
					<table>
					<tr>
						<td class=common>
							<img  src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divUWGErr1);">
						</td>
						<td class=titleImg>被保险人信息</td>
					</tr>
				</table>
				<table class=common>
						<tr>
							<td colSpan=1>
								<span id="spanUWContErrGrid"></span>
							</td>
						</tr>
					</table>
					<center>
						<input value="首  页" class=cssButton90 type=button onclick="turnPage2.firstPage();">
						<input value="上一页" class=cssButton91 type=button onclick="turnPage2.previousPage();">
						<input value="下一页" class=cssButton92 type=button onclick="turnPage2.nextPage();">
						<input value="尾  页" class=cssButton93 type=button onclick="turnPage2.lastPage();">
					</center>
				</div>
				<!--团单层级信息-->
				<div id="divUWGErr2" style="display: none">
					<table>
						<tr>
							<td class=common>
								<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divUWGErr2);">
							</td>
							<td class=titleImg>团体保单核保结论</td>
						</tr>
					</table>
					<div class=maxbox1>
					<table class=common>
						<tr class=common>
							<td class=title>核保结论</td>
							<td class=input><input class=codeno name=UWConclusion id=UWConclusion style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('grpcontuwclu',[this,UWConclusionName],[0,1],null,null,null,1,null);" onkeyup="return showCodeListKey('grpcontuwclu',[this,UWConclusionName],[0,1],null,null,null,1,null);"><input class=codename name=UWConclusionName elementtype=nacessary></td>
							<td class=title></td>
							<td class=input></td>
							<td class=title></td>
							<td class=input></td>
						</tr>
						<tr class=common>
							<td class=title>核保意见</td>
							<td class=input colspan=5><textarea name="UWOption" id=UWOption cols=76 rows=2 class=common></textarea></td>
						</tr>
					</table>
					<input class=cssButton type=button value="保存核保结论" onclick="saveUWGrpclu();">
					</div>
				</div>
			</div>
			
			<!--被保险人列表显示 -->
			<div id="tablistdiv1" class="tablist" style="display: none">
				<table>
					<tr>
						<td class=common>
							<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divCUWMaster);">
							</td>
							<td class=titleImg>请输入查询条件</td>
					</tr>
				</table>
				<div id="divCUWMaster" class=maxbox1 style="display:''">
					<table class=common>
						<tr>
							<td class=title>被保险人姓名</td>
							<td class=input><input class="wid common" name=insuredName id=insuredName></td>
							<td class=title>证件号码</td>
							<td class=input><input class="wid common" name=idNo id=idNo></td>
							<td class=title>保险方案</td>
							<td class=input><Input class="codeno" name="ContPlanCode" id=ContPlanCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showContPlanCode(this,ContPlanCodeName,sysPlanCode);" onkeyup="showContPlanCodeName(this,ContPlanCodeName,sysPlanCode);"><input class=codename name=ContPlanCodeName ><input type=hidden name=sysPlanCode ></td>
						</tr>
						<tr>
							<td class=title>是否通过自动核保</td>
							<td class=input><input class=codeno name="FlagState" id=FlagState value="0" style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="showCodeList('trueflag',[this,FlagStateName],[0,1],null,null,null,1,null);" onkeyup="return showCodeListKey('trueflag',[this,FlagStateName],[0,1],null,null,null,1,null);" ><input class=codename name=FlagStateName value="否" readonly=true ></td>
							<td class=title></td>
							<td class=input></td>
							<td class=title></td>
							<td class=input></td>
						</tr>
					</table>
						<input class=cssButton value="查  询" type=button onclick="queryCGUWMaster()">
						<input class=cssButton value="整单通过" type=button onclick="passUWMaster()">
						<input class=cssButton value="清除查询条件" type=button onclick="initQuery()">
					</div>
					
					<!--被保险人信息-->
					<table>
						<tr>
							<td class=common>
								<img  src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divCUW);">
							</td>
							<td class=titleImg>被保险人信息</td>
						</tr>
					</table>
					<div id="divCUW" style="display:''">
						<table class=common>
							<tr class=common>
								<td text-align: left colSpan=1>
									<span id="spanUWGErrGrid"></span>
								</td>
							</tr>
						</table>
						<center>
							<input value="首  页" class=cssButton90 type=button onclick="turnPage3.firstPage();">
							<input value="上一页" class=cssButton91 type=button onclick="turnPage3.previousPage();">
							<input value="下一页" class=cssButton92 type=button onclick="turnPage3.nextPage();">
							<input value="尾  页" class=cssButton93 type=button onclick="turnPage3.lastPage();">
						</center>
					</div>
					
					<!--个人核保信息-->
					<table>
						<tr>
							<td class=common>
								<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divCCErr);">
								</td>
							<td class=titleImg>个人核保信息</td>
						</tr>
				</table>
				<div id="divCCErr" style="display:''">
					<table class=common>
						<tr class=common>
							<td text-align: left colSpan=1>
								<span id="spanCCErrGrid"></span>
							</td>
						</tr>
					</table>
					<center>
						<input value="首  页" class=cssButton90 type=button onclick="turnPage4.firstPage();">
						<input value="上一页" class=cssButton91 type=button onclick="turnPage4.previousPage();">
						<input value="下一页" class=cssButton92 type=button onclick="turnPage4.nextPage();">
						<input value="尾  页" class=cssButton93 type=button onclick="turnPage4.lastPage();">
					</center>	
				</div>
				
				<!--个人险种信息-->
				<div id="divPerPol01" style="display:none">
						<table>
							<tr>
								<td class=common>
									<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divCpolErr);">
								</td>
								<td class=titleImg>个人险种责任信息</td>
							</tr>
						</table>
						<div id="divCpolErr" style="display:''">
							<table class=common>
								<tr class=common>
									<td text-align: left colSpan=1>
										<span id="spanCpolErrGrid"></span>
									</td>
								</tr>
							</table>
							<input class=cssButton value="保  存" type=button onclick="savePremAdjust()">	
						</div>
				</div>
				
				<div id="divPerPol02" style="display:none">
						<table>
							<tr>
								<td class=common>
									<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divCpolErr);">
								</td>
								<td class=titleImg>个人险种责任信息</td>
							</tr>
						</table>
						<div id="divCpolErr" style="display:''">
							<table class=common>
								<tr class=common>
									<td text-align: left colSpan=1>
										<span id="spanCpolTErrGrid"></span>
									</td>
								</tr>
							</table>
							<!--<input class=cssButton value="打印健康告知通知书" type=button onclick="">	-->
						</div>
				</div>
				
				<!--个人核保结论-->
				<table>
					<tr>
						<td class=common>
							<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divUWConclu);">
						</td>
						<td class=titleImg>个人核保结论</td>
					</tr>
				</table>
				<div id="divUWConclu" class=maxbox1 style="display: ''">
					<table class=common>
						<tr class=common id="showReins" style="display: none" >
							<td class=title>个人再保结论</td>
							<td class=input><input class=codeno name=ReinsType id=ReinsType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" showCodeList('reinsarrange',[this,ReinsTypeName],[0,1],null,null,null,1,null);" onkeyup="return showCodeListKey('reinsarrange',[this,ReinsTypeName],[0,1],null,null,null,1,null);"><input class=codename name=ReinsTypeName elementtype=nacessary></td>
							<td class=title></td>
							<td class=input></td>
							<td class=title></td>
							<td class=input></td>
						</tr>
						<tr class=common>
							<td class=title>个人核保结论</td>
							<td class=input><input class=codeno name=UWConclu id=UWConclu verify="个人核保结论|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" showCodeList('contuwclu',[this,UWConcluName],[0,1],null,null,null,1,null);" onkeyup="return showCodeListKey('contuwclu',[this,UWConcluName],[0,1],null,null,null,1,null);"><input class=codename name=UWConcluName elementtype=nacessary></td>
							<td class=title></td>
							<td class=input></td>
							<td class=title></td>
							<td class=input></td>
					</tr>
					<tr class=common>
						<td class=title>个人核保意见</td>
						<td class=input colspan=5>
							<textarea cols=76 rows=2 class=common name=UWIdea id=UWIdea></textarea>
						</td>
					</tr>
				</table>
				<input class=cssButton value="保存个人核保结论" type=button onclick="saveUWConclu()">
				<!--<input class=cssButton value="批量保存个人核保结论" type=button onclick="saveUWContAllclu()">-->
				<input class=cssButton value="关  闭" type=button onclick="top.close();">
			</div>
		</div>
		
		<!--隐藏区域-->
		<input type=hidden name=Operate>
		<input type=hidden name=GrpPropNo value="<%=tGrpPropNo%>">
		<input type=hidden name=EdorNo value="<%=tEdorNo%>">
		<input type=hidden name=EdorAppNo value="<%=tEdorAppNo%>">
		<input type=hidden name=ReinsFlag>
		</form>
		<Br /><Br /><Br /><Br />
	</div>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
