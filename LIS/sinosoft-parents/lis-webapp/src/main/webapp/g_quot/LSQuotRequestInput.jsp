<%
/***************************************************************
 * <p>ProName：LSQuotRequestInput.jsp</p>
 * <p>Title：业务需求</p>
 * <p>Description：业务需求</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-18
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String tQuotType = request.getParameter("QuotType");
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
	String tActivityID = request.getParameter("ActivityID");
%>
<script>
	var tQuotType = "<%=tQuotType%>";
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tActivityID = "<%=tActivityID%>";
</script>
<html>
<head >
	<title>业务需求</title>
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
	<script src="./LSQuotRequestInput.js"></script>
	<%@include file="./LSQuotRequestInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LSQuotRequestSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divRequest);">
			</td>
			<td class=titleImg>业务需求维护</td>
		</tr>
	</table>
	<div id="divRequest"  style="display: ''">
	<div id="divRequest"  style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanRequestGrid"></span>
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
	<br>
	<div id="divRequestDetail" style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>业务需求分类</td>
				<td class=input><input class=codeno name=RequestType id=RequestType readonly verify="业务需求分类|notnull"
					style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('requesttype',[this, RequestTypeName],[0, 1],null,null,null,'1',180);" 
					onkeyup="return showCodeListKey('requesttype',[this, RequestTypeName],[0, 1],null,null,null,'1',180);"><input class=codename name=RequestTypeName id=RequestTypeName readonly elementtype=nacessary></td>
				<td class=title id="divOtherTypeDescTitle" style="display: none">其他分类描述</td>
				<td class=input id="divOtherTypeDescInput" style="display: none"><input class="wid common" name=OtherTypeDesc id=OtherTypeDesc verify="其他分类描述|len<=15" maxlength=15 elementtype=nacessary></td>
				<td class=title id="divRiskTitle" style="display: none">险种</td>
				<td class=input id="divRiskInput" style="display: none"><input class=codeno name=RiskCode id=RiskCode readonly 
					style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('quotprodrisk',[this, RiskName],[0, 1],null,'1 and quotno=#<%=tQuotNo%># and quotbatno=#<%=tQuotBatNo%>#','1','1',300);" 
					onkeyup="return showCodeListKey('quotprodrisk',[this, RiskName],[0, 1],null,'1 and quotno=#<%=tQuotNo%># and quotbatno=#<%=tQuotBatNo%>#','1','1',300);"><input class=codename name=RiskName id=RiskName readonly elementtype=nacessary></td>
				<td class=title id="divTD1" style="display: ''"></td>
				<td class=input id="divTD2" style="display: ''"></td>
				<td class=title id="divTD3" style="display: ''"></td>
				<td class=input id="divTD4" style="display: ''"></td>
			</tr>
			<tr class=common>
				<td class=title>业务需求内容</td>
				<td class=input colspan=5><textarea cols=76 rows=5 name=RequestDesc id=RequestDesc elementtype=nacessary></textarea></td>
			</tr>
			<tr class=common id="divSubUWOpinion" style="display: ''">
				<td class=title>中支公司核保意见</td>
				<td class=input colspan=5><textarea cols=76 rows=5 name=SubUWOpinion id=SubUWOpinion elementtype=nacessary></textarea></td>
			</tr>
			<tr class=common id="divBranchUWOpinion" style="display: ''">
				<td class=title>分公司核保意见</td>
				<td class=input colspan=5><textarea cols=76 rows=5 name=BranchUWOpinion id=BranchUWOpinion elementtype=nacessary></textarea></td>
			</tr>
		</table>
	</div>
	
	<div>
		<input class=cssButton type=button value="新  增" name="AddButton" onclick="addClick();">
		<input class=cssButton type=button value="修  改" name="ModifyButton" onclick="modifyClick();">
		<input class=cssButton type=button value="删  除" name="DeleteButton" onclick="deleteClick();">
		<input class=cssButton type=button value="保  存" name="SaveButton" onclick="saveClick();">
		<input class=cssButton type=button value="关  闭" onclick="top.close();">
	</div>
	
	<input type=hidden name=Operate id=Operate>
	<input type=hidden name=SerialNo id=SerialNo>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
