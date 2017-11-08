<%
/***************************************************************
 * <p>ProName：PreCustomerManageInput.jsp</p>
 * <p>Title：准客户维护界面</p>
 * <p>Description：准客户维护界面</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.ExeSQL"%>
<%
	String tFlag = request.getParameter("Flag");
	String tPreCustomerNo = request.getParameter("PreCustomerNo");
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
	ExeSQL	tExeSQL = new ExeSQL();
	String tComGrade = tExeSQL.getOneValue("select a.comgrade from ldcom a where 1=1 and a.comcode='"+tGI.ManageCom+"'");
%>
<script>
	var tFlag = "<%=tFlag%>";
	var tPreCustomerNo = "<%=tPreCustomerNo%>";
	var tComGrade = "<%=tComGrade%>";
</script>
<html>
<head >
	<title>准客户维护</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<script src="./PreCustomerManageInput.js"></script>
	<%@include file="./PreCustomerManageInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./PreCustomerManageSave.jsp" target=fraSubmit>
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
				<td class=input colspan=3><input class="wid common" name=PreCustomerName id=PreCustomerName verify="准客户名称|notnull&len<60" maxlength=60 style="width:555px" elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>证件类型</td>
				<td class=input><input class=codeno name=IDType id=IDType readonly verify="证件类型|notnull"
					ondblclick="return showCodeList('gidtype',[this, IDTypeName],[0, 1],null,null,null,'1',180);" 
					onkeyup="return showCodeListKey('gidtype',[this, IDTypeName],[0, 1],null,null,null,'1',180);"><input class=codename name=IDTypeName  id=IDTypeName readonly elementtype=nacessary></td>
				<td class=title>证件号码</td>
				<td class=input><input class="wid common" name=IDNo id=IDNo verify="证件号码|notnull&len<30" maxlength=30 elementtype=nacessary></td>
				<td class=title>单位性质</td>
				<td class=input><input class=codeno name=GrpNature id=GrpNature readonly verify="单位性质|notnull"
					ondblclick="return showCodeList('grpnature',[this, GrpNatureName],[0, 1],null,null,null,'1',180);" 
					onkeyup="return showCodeListKey('grpnature',[this, GrpNatureName],[0, 1],null,null,null,'1',180);"><input class=codename name=GrpNatureName id=GrpNatureName readonly elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>行业分类</td>
				<td class=input><input class=codeno name=BusiCategory id=BusiCategory readonly verify="行业分类|notnull"
					style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('category',[this, BusiCategoryName],[0, 1],null,null,null,'1',null);" 
					onkeyup="return showCodeListKey('category',[this, BusiCategoryName],[0, 1],null,null,null,'1',null);"><input class=codename name=BusiCategoryName id=BusiCategoryName readonly elementtype=nacessary></td>
				<td class=title>单位总人数</td>
				<td class=input><input class="wid common" name=SumNumPeople id=SumNumPeople verify="单位总人数|INT&VALUE>=0"></td>
				<td class=title>预计投保总人数</td>
				<td class=input><input class="wid common" name=PreSumPeople id=PreSumPeople verify="预计投保总人数|INT&VALUE>=0"></td>
			</tr>
			<tr class=common>
				<td class=title>预计保费规模(元)</td>
				<td class=input><input class="wid common" name=PreSumPrem id=PreSumPrem verify="预计保费规模|NUM&VALUE>=0"></td>
				<td class=title>所属上级客户</td>
				<td class=input colspan=4><input class=codeno name=UpCustomerNo id=UpCustomerNo style="width:80px"><input class=codename name=UpCustomerName id=UpCustomerName onkeydown="queryUpCustomerNo(UpCustomerNo,this,PreCustomerNo);" style="width:290px"><span style="color: red">（录入上级客户名称敲回车模糊查询）</span></td>
				</tr>
			<tr class=common>
				<td class=title>销售渠道</td>
				<td class=input><input class=codeno name=SaleChannel id=SaleChannel readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('salechannel',[this, SaleChannelName],[0, 1],null,null,null,'1',180);" 
					onkeyup="return showCodeListKey('salechannel',[this, SaleChannelName],[0, 1],null,null,null,'1',180);"><input class=codename name=SaleChannelName id=SaleChannelName readonly></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>单位地址</td>
				<td class=input colspan=5>
					<input class=codeno name=ProvinceName id=ProvinceName style="width:60px" ondblclick="return showCodeList('province',[this, ProvinceCode],[1,0],null,null,null,'1',180);" onkeydown="fuzzyQueryProvince(ProvinceCode,ProvinceName)"><input type=hidden name=ProvinceCode id=ProvinceCode readonly>省
					<input class=codeno name=CityName id=CityName style="width:60px" ondblclick="return returnShowCodeList('city',[this, CityCode],[1,0]);"  onkeydown="fuzzyQueryCity(CityCode,CityName,ProvinceCode)"><input type=hidden name=CityCode id=CityCode readonly>市
					<input class=codeno name=CountyName  id=CountyName style="width:60px" ondblclick="return returnShowCodeList('county',[this, CountyCode],[1,0]);"  onkeydown="fuzzyQueryCounty(CountyCode,CountyName,ProvinceCode,CityCode)"><input type=hidden name=CountyCode id=CountyCode readonly>区/县
					<input class=common name=DetailAddress id=DetailAddress verify="单位详细地址|len<100" maxlength=100 style="width:300px">
				</td>
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
			<td class=titleImg>主要联系人信息<font color=red>（联系人手机、办公电话至少录入一个）</font></td>
		</tr>
	</table>
	<div id="divLink" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>联系人姓名</td>
				<td class=input><input class="wid common" name=LinkMan id=LinkMan verify="联系人姓名|notnull" maxlength=30 elementtype=nacessary></td>
				<td class=title>联系人手机</td>
				<td class=input><input class="wid common" name=Mobile id=Mobile verify="联系人手机|PHONE" maxlength=15></td>
				<td class=title>办公电话</td>
				<td class=input><input class="wid common" name=Phone id=Phone maxlength=20></td>
			</tr>
			<tr class=common>
				<td class=title>联系人部门</td>
				<td class=input><input class="wid common" name=Depart id=Depart maxlength=25></td>
				<td class=title>联系人职务</td>
				<td class=input><input class="wid common" name=Post id=Post maxlength=25></td>
				<td class=title>联系人邮箱</td>
				<td class=input><input class="wid common" name=Email id=Email verify="联系人邮箱|EMAIL" maxlength=50></td>
			</tr>
		</table>
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divSaler);">
			</td>
			<td class=titleImg>附属客户经理信息<font color=red>（多客户经理时，录入其他客户经理信息）</font></td>
		</tr>
	</table>
	<div id="divSaler" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanSalerGrid"></span>
				</td>
			</tr>
		</table>
	</div>
	
	<div>
		<input class=cssButton type=button value="新增准客户" name="AddButton" id=AddButton onclick="addClick();">
		<input class=cssButton type=button value="修改准客户" name="ModifyButton" id=ModifyButton onclick="modifyClick();">
		<input class=cssButton type=button value="删除准客户" name="DeleteButton" id=DeleteButton onclick="deleteClick();">
		<input class=cssButton type=button value="修改轨迹查询" name="ModifyTraceButton" id=ModifyTraceButton onclick="TraceQuery();">
		<input class=cssButton type=button value="返  回" onclick="returnBack();">
	</div>
	
	<div id="divAppQuot" style="display: none">
		<hr class="line"/>
		<input class=cssButton type=button value="申请询价" name="AppButton" id=AppButton onclick="applyQuotClick();">
	</div>
	
	<input type=hidden name=Operate id=Operate>
	<input type=hidden name=HiddenPreCustomerNo id=HiddenPreCustomerNo>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
