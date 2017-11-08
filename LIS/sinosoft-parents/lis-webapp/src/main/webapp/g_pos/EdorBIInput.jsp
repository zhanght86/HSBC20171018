<%
/***************************************************************
 * <p>ProName：GEdorBIDetail.jsp</p>
 * <p>Title：银行信息变更</p>
 * <p>Description：银行信息变更</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-06-13
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
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
	<title>银行信息变更</title>
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
	<script src="./EdorBIInput.js"></script>
	<%@include file="./EdorBIInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<div id="divQueryOld" style="display: ''">
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
			<td class=titleImg>原被保险人查询条件</td>
		</tr>
	</table>
	<div id="divQuery" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>  
				<td class=title>被保险人姓名</td>
				<td class = input ><input class="wid common"  name=OldInsuredName id=OldInsuredName></td> 
				<td class=title>证件号码</td>
				<td class = input ><input class="wid common"  name=OldInsuredIDNo id=OldInsuredIDNo></td>
				<td class=title style="display: none">被保险人客户号 </td>
				<td class = input ><input class="wid common"  name=OldInsuredNo id=OldInsuredNo type=hidden></td>
			</tr>
		</table>
		<input class=cssButton type=button value="查  询" onclick="queryOldClick();">
	</div>
	<br/>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divResult);">
			</td>
			<td class=titleImg>原被保险人信息</td>
		</tr>
	</table>
	<div id="divResult" style="display: ''">
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
	</div>
</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryUpdate);">
			</td>
			<td class=titleImg>修改过的被保险人查询条件</td>
		</tr>
	</table>
	<div id="divQueryUpdate" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>  
				<td class=title>被保险人姓名</td>
				<td class= input ><input class="wid common"  name=InsuredName id=InsuredName></td> 
				<td class=title>证件号码</td>
				<td class=input ><input class="wid common"  name=InsuredIDNo id=InsuredIDNo></td>
				<td class=title> </td>
				<td class=input ></td>
			</tr>
		</table>	
	<input class=cssButton type=button value="查  询" onclick="queryUpClick(1);">
	<input class=cssButton type=button value="返  回"  onclick="top.close();">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divResultUpdate);">
			</td>
			<td class=titleImg>修改过的被保险人</td>
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
	</div>
		
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divBankInfoUp);">
			</td>
			<td class=titleImg>修改后被保险人银行信息</td>
		</tr>
	</table>
	<div id="divBankInfoUp" class=maxbox1 style="display: ''">
		<table class=common>
			<tr  class=common>
				<td class=title>保全生效日期</td>
				<td class=input><input class=coolDatePicker name=EdorValDate dateFormat="short" verify="保全生效日期|notnull&date" elementtype=nacessary onClick="laydate({elem: '#EdorValDate'});" id="EdorValDate"><span class="icon"><a onClick="laydate({elem: '#EdorValDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
</td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>开户银行</td>
				<td class=input><input class=codeno name=HeadBankCode id=HeadBankCode  verify="开户银行|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('headbank',[this,HeadBankName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('headbank',[this,HeadBankName],[0,1],null,null,null,'1',null);" readonly>
				<input class=codename name="HeadBankName" id=HeadBankName readonly elementtype=nacessary></td>
				<td class=title>开户名</td>
				<td class=input><input class="wid common" name=AccName id=AccName verify="开户名|notnull&len<=25" elementtype=nacessary></td>
				<td class=title>账号</td>
				<td class=input><input class="wid common" name=BankAccNo id=BankAccNo verify="账号|notnull&len<=25" elementtype=nacessary></td>	
			</tr>
			<tr class=common>
				<td class=title>开户行所在省</td>
				<td class=input><input class=codeno name=BankProvince id=BankProvince style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('province',[this,BankProvinceName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('province',[this,BankProvinceName],[0,1],null,null,null,'1',null);"><input class=codename name="BankProvinceName" id=BankProvinceName ></td>
				<td class=title>开户行所在市</td>
				<td class=input><input class=codeno name=BankCity id=BankCity style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="checkProvince();showCodeList('city', [this,BankCityName],[0,1],null,fm.BankProvince.value,'upplacename',1);" onkeyup="checkProvince();showCodeListKey('city', [this,BankCityName],[0,1],null,fm.BankProvince.value,'upplacename',1);"><input class=codename name="BankCityName" id=BankCityName></td>	
				<td class=title>手机号</td>
				<td class=input><input class="wid common" name=Mobile id=Mobile  onblur="checkNumber(this);"  verify="被保险人移动电话|len=11" ></td>
			</tr>
		</table>
	</div>
	
	<div id="divButton01" style="display: none">
		<table class=common>
			<tr class=common>
				<td class=input colspan=6 align=left><input class=checkbox type=checkbox name=FS1 id=FS1 onclick="showFS(0)">调整所有附属被保险人<input class=checkbox type=checkbox name=FS2 onclick="showFS(1);">调整选择附属被保险人</td>
			</tr>
			<tr class=common>
				<td class=title colspan=6>
					<div  id="divFS" style="display: none">
						<table class=common>
							<tr class=common>
								<td text-align: left colSpan=1>
									<span id="spanFSGrid" ></span>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<div id="divButton02" style="display: none">
		<input class=cssButton type=button value="保  存" onclick="saveBank();">
		<input class=cssButton type=button value="撤  销" onclick="deleteOperate();">
	</div>
	
	<input type=hidden name=Operate id=Operate>
	<input type=hidden name=HContNo id=HContNo>
	<input type=hidden name=HInsuredNo id=HInsuredNo>
	<input type=hidden name=InsuredNames id=InsuredNames>
	<input type=hidden name=InsuredIdNos id=InsuredIdNos>
	<input type=hidden name=SerialNo id=SerialNo>
	<input type=hidden name=MainInsuredNames id=MainInsuredNames>
	<input type=hidden name=MainInsuredIdNos id=MainInsuredIdNos>
	<input type=hidden name=BatchNo id=BatchNo>
	<br /><br /><br /><br /><br />
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
