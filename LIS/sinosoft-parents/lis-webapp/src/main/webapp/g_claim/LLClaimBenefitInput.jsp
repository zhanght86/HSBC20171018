<%
/***************************************************************
 * <p>ProName：LLClaimBenefitInput.jsp</p>
 * <p>Title：受益人信息</p>
 * <p>Description：受益人信息</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String mGrpRgtNo = request.getParameter("GrpRgtNo");	
	String mRgtNo = request.getParameter("RgtNo");	
	String mCustomerNo = request.getParameter("CustomerNo");	
%>
<script>
	var mGrpRgtNo = "<%=mGrpRgtNo%>";
	var mRgtNo = "<%=mRgtNo%>";
	var mCustomerNo = "<%=mCustomerNo%>";
</script>
<html>
<head>
	<title>受益人查看</title>
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
	<script src="./LLClaimBenefitInput.js"></script>
	<%@include file="./LLClaimBenefitInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divMainCase);">
			</td>
			<td class=titleImg>受益人信息列表</td>
		</tr>
	</table>
	
	<div id="divMainCase" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanBenefitListGrid"></span>
				</td>
			</tr>
		</table>
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryInfo2);">
			</td>
			<td class=titleImg>受益人信息</td>
		</tr>
	</table> 
	<div id="divQueryInfo2" class=maxbox1 style="display:''">
		<table class = common>
			<tr class = common>
		  		<td class = title>受益人类别</td>
		  		<td class=input><input class=codeno name=BnfType readonly><input class=codename name=BnfTypeName readonly></td>
		    	<td class = title>受益人姓名</td>
	 			<td class = input><input class="wid readonly" name=BnfName readonly> </td>
	 			<td class = title>受益人性别</td>
	 			<td class=input><input class=codeno name=BnfGender readonly><input class=codename name=BnfGenderName readonly></td>	  			
			</tr> 		
			<tr class = common>
				<td class = title>受益人证件类型</td>
		  		<td class = input><input class=codeno name=BnfIDType readonly><input class=codename name=BnfIDTypeName readonly></td>
		  		<td class = title>受益人证件号码</td>
		  		<td class = input><input class="wid readonly" name=BnfIDNo readonly></td>
		  		<td class = title>受益人出生日期</td>
		  		<td class = input><input class="coolDatePicker" dateFormat="short" name=BnfBirthday readonly onClick="laydate({elem: '#BnfBirthday'});" id="BnfBirthday"><span class="icon"><a onClick="laydate({elem: '#BnfBirthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr> 	
			<tr class = common>
		  		<td class = title>与被保人关系</td>
		  		<td class = input><input class=codeno name=BnfRelation readonly><input class=codename name=BnfRelationName readonly></td>
		  		<td class = title>受益份额</td>
		  		<td class = input><input class="wid readonly" name=BnfLot readonly></td>
		  		<td class = title>受益金额</td>
		  		<td class = input><input class="wid readonly" name=BnfMoney readonly></td>
			</tr> 	
			<tr class=common>
				<td class=title>开户银行</td>
				<td class=input><input class=codeno name=BankCode readonly><input class=codename name=BankCodeName readonly></td>
				<td class=title>开户行所在省</td>
				<td class=input><input class=codeno name=Province readonly><input class=codename name=ProvinceName readonly></td>
				<td class=title>开户行所在市</td>
				<td class=input><input class=codeno name=City readonly><input class=codename name=CityName readonly></td>
			</tr> 	
			<tr class=common>	
	   			<td class = title>账号</td>
	 			<td class = input><input class="wid readonly" name=AccNo readonly></td>
		  		<td class = title>账户名</td>
		  		<td class = input><input class="wid readonly" name=AccName readonly></td>
		  		<td class = title>转账方式</td>
	 			<td class = input><input class=codeno name=PayType readonly><input class=codename name=PayTypeName readonly></td>
			</tr> 		
		</table>
		<input class=cssButton type=button value="关  闭" onclick="top.close();" >
	</div>
	<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
