<%
/***************************************************************
 * <p>ProName：PreCustomerUnlockInput.jsp</p>
 * <p>Title：准客户解锁界面</p>
 * <p>Description：准客户解锁界面</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String tTraceID = request.getParameter("TraceID");
	String tPreCustomerNo = request.getParameter("PreCustomerNo");
%>
<script>
	var tTraceID = "<%=tTraceID%>";
	var tPreCustomerNo = "<%=tPreCustomerNo%>";
</script>
<html>
<head >
	<title>准客户信息</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<script src="../common/laydate/laydate.js"></SCRIPT>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<link href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./PreCustomerUnlockInput.js"></script>
	<%@include file="./PreCustomerUnlockInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./PreCustomerUnlockSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPreCustomer);">
			</td>
			<td class=titleImg>准客户基本信息</td>
		</tr>
	</table>
	<div id="divPreCustomer" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>准客户号码</td>
				<td class=input><input class="wid readonly" name=PreCustomerNo id=PreCustomerNo readonly></td>
				<td class=title>准客户名称</td>
				<td class=input colspan=3><input class="wid readonly" name=PreCustomerName id=PreCustomerName readonly></td>
			</tr>
			<tr class=common>
				<td class=title>证件类型</td>
				<td class=input><input class="wid readonly" name=IDTypeName id=IDTypeName readonly></td>
				<td class=title>证件号码</td>
				<td class=input><input class="wid readonly" name=IDNo id=IDNo readonly></td>
				<td class=title>单位性质</td>
				<td class=input><input class="wid readonly" name=GrpNatureName id=GrpNatureName readonly></td>
			</tr>
			<tr class=common>
				<td class=title>行业分类</td>
				<td class=input><input class="wid readonly" name=BusiCategoryName id=BusiCategoryName readonly></td>
				<td class=title>单位总人数</td>
				<td class=input><input class="wid readonly" name=SumNumPeople id=SumNumPeople readonly></td>
				<td class=title>预计投保总人数</td>
				<td class=input><input class="wid readonly" name=PreSumPeople id=PreSumPeople readonly></td>
			</tr>
			<tr class=common>
				<td class=title>预计保费规模(元)</td>
				<td class=input><input class="wid readonly" name=PreSumPrem id=PreSumPrem readonly></td>
				<td class=title>所属上级客户</td>
				<td class=input><input class="wid readonly" name=UpCustomerName id=UpCustomerName readonly></td>
				<td class=title>销售渠道</td>
				<td class=input><input class="wid readonly" name=SaleChannelName id=SaleChannelName readonly></td>
			</tr>
			<tr class=common>
				<td class=title>单位地址</td>
				<td class=input colspan=5><input class="wid readonly" name=DetailAddress id=DetailAddress readonly></td>
			</tr>
			<tr class=common>
				<td class=title>公司简介</td>
				<td class=input colspan=5><textarea cols=76 rows=3 name=CustomerIntro id=CustomerIntro></textarea></td>
			</tr>
		</table>
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divLink);">
			</td>
			<td class=titleImg>主要联系人信息</td>
		</tr>
	</table>
	<div id="divLink" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>联系人姓名</td>
				<td class=input><input class="wid readonly" name=LinkMan id=LinkMan readonly></td>
				<td class=title>联系人手机</td>
				<td class=input><input class="wid readonly" name=Mobile id=Mobile readonly></td>
				<td class=title>办公电话</td>
				<td class=input><input class="wid readonly" name=Phone id=Phone readonly></td>
			</tr>
			<tr class=common>
				<td class=title>联系人部门</td>
				<td class=input><input class="wid readonly" name=Depart id=Depart readonly></td>
				<td class=title>联系人职务</td>
				<td class=input><input class="wid readonly" name=Post id=Post readonly></td>
				<td class=title>联系人邮箱</td>
				<td class=input><input class="wid readonly" name=Email id=Email readonly></td>
			</tr>
		</table>
	</div>
	
	<div id="divMainSaler" style="display: ''">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divMainSalerGrid);">
				</td>
				<td class=titleImg>主客户经理信息</td>
			</tr>
		</table>
		
		<div id="divMainSalerGrid"  class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>客户经理</td>
					<td class=input><input class=codeno name=SalerCode id=SalerCode style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="queryManagerInfo();" verify="客户经理|notnull"><input class=codename name=SalerName id=SalerName readonly elementtype=nacessary></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
		</div>
	</div>
	
	<div id="divSaler" style="display: ''">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divSalerGrid);">
				</td>
				<td class=titleImg>附属客户经理信息<font color=red>（多客户经理时，录入其他客户经理信息）</font></td>
			</tr>
		</table>
		<div id="divSalerGrid" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanSalerGrid"></span>
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	<div>
		<input class=cssButton type=button value="准客户解锁" name="UnlockButton" onclick="unlockClick();">
		<input class=cssButton type=button value="客户经理信息修改" name="ModifyButton" onclick="modifyClick();">
		<input class=cssButton type=button value="返  回" onclick="returnBack();">
	</div>
	
	<input type=hidden name=Operate id=Operate>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
