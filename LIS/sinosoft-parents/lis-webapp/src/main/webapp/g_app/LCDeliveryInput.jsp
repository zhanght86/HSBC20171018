<%
/***************************************************************
 * <p>ProName：LCDeliveryInput.jsp</p>
 * <p>Title：递送登记</p>
 * <p>Description：递送登记</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-05-07
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
%>

<script>
	var tManageCom = "<%=tGI.ManageCom%>";//记录登陆机构
	var tOperator = "<%=tGI.Operator%>";
</script>
<html>
<head>
	<title>递送登记</title>
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
	<script src="./LCDeliveryInput.js"></script>
	<%@include file="./LCDeliveryInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">

<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo);">
			</td>
			<td class=titleImg>查询条件</td>
		</tr>
	</table>
	<div id="divInfo" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>承保机构</td>
				<td class=input><input class=codeno name=ManageCom id=ManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" showCodeList('comcodeall',[this,EscanComName],[0,1],null,'1 and comgrade=#03#',1,1);" onkeyup="return showCodeListKey('comcodeall',[this,EscanComName],[0,1],null,'1 and comgrade=#03#',1,1);"><input class=codename name=EscanComName readonly=true></td>
				<td class=title>投保单号</td>
				<td class=input><input class="wid common" name=GrpPropNo id=GrpPropNo></td>
				<td class=title>团体保单号</td>
				<td class=input><input class="wid common" name=GrpContNo id=GrpContNo></td>
			</tr>
			<tr class=common>
				<td class=title>打印起期</td>
				<td class=input><Input class="coolDatePicker" dateFormat="short"  name=PrintStartDate verify="打印起期|date" onClick="laydate({elem: '#PrintStartDate'});" id="PrintStartDate"><span class="icon"><a onClick="laydate({elem: '#PrintStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>打印止期</td>
				<td class=input><Input class="coolDatePicker" dateFormat="short"  name=PrintEndDate verify="打印止期|date" onClick="laydate({elem: '#PrintEndDate'});" id="PrintEndDate"><span class="icon"><a onClick="laydate({elem: '#PrintEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>投保人名称</td>
				<td class=input><input class="wid common" name=GrpName id=GrpName></td>
			</tr>
			<tr class=common>
				<td class=title>登记状态</td>
				<td class=input><input class=codeno name=ContState id=ContState style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" showCodeList('registerflag',[this,ContStateName],[0,1],null,null,null,1,null);" onkeyup="return showCodeListKey('registerflag',[this,ContStateName],[0,1],null,null,null,1,null);"><input class=codename name=ContStateName readonly=true></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td> 
			</tr>
		</table>
		<input class=cssButton  type=button value="查  询" onclick="queryClick();">
	</div>

	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryInfo);">
			</td>
			<td class=titleImg>查询结果</td>
		</tr>
	</table>
	<div id="divQueryInfo" style="display: ''">	
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanContInfoGrid"></span>
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
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divSaveInfo);">
			</td>
			<td class=titleImg>递送信息</td>
		</tr>
	</table>
	<div id="divSaveInfo" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>登记结论</td>
				<td class=input><input class=codeno name=RegisterPassFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center" id=RegisterPassFlag verify="登记结论|notnull" ondblclick="return showCodeList('registerclu',[this,RegisterPassFlagName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('registerclu',[this,RegisterPassFlagName],[0,1],null,null,null,'1',null);"><input class=codename name=RegisterPassFlagName  elementtype=nacessary></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>   
	</div>	
	<div id="divQuery1Info" style="display: none">
		<table class=common>
			<tr class=common>
				<td class=title>交接类型</td>
				<td class=input><input class=codeno name=TransferType id=TransferType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('sendtype',[this,TransferTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('sendtype',[this,TransferTypeName],[0,1],null,null,null,'1',null);"><input class=codename name=TransferTypeName></td>
				<td class=title>快递公司</td>
				<td class=input><input class="wid common" name=ExpressCorpName id=ExpressCorpName verify="快递公司|len<40"></td>
				<td class=title>快递单号</td>
				<td class=input><input class="wid common" name=ExpressNo id=ExpressNo verify="快递单号|len<25"></td>
			</tr>
			<tr class=common>
				<td class=title>交接日期</td>
				<td class=input><input class="coolDatePicker" dateFormat="short"  name=TransferDate verify="交接起期|date" onClick="laydate({elem: '#TransferDate'});" id="TransferDate"><span class="icon"><a onClick="laydate({elem: '#TransferDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>登记人</td>
				<td class=input><input class="wid readonly" name=Register value=<%=tGI.Operator%>></td>
				<td class=title>快递登记日期</td>
				<td class=input><input class="wid readonly" name=ExpressDate value=<%=PubFun.getCurrentDate()%>></td>
			</tr>
		</table>   
	</div>
	<div id="divQuery2Info" style="display: none">
		<table class=common>
			<tr class=common>
				<td class=title colspan=6>不合格原因</td>
			</tr>
			<tr class=common>
				<td class=input colspan=6><textarea cols=80 rows=3 name=Reason id=Reason></textarea><span style='color: red'>*</span></td>
			</tr>
		</table>   
	</div>
	<input class=cssButton  type=button value="保  存" onclick="saveClick();">
	<input type=hidden name=Operate>
			
</form>
<br /><br /><br /><br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
