<%
/***************************************************************
 * <p>ProName:EdorBBInput.jsp</p>
 * <p>Title:  被保险人基本资料变更</p>
 * <p>Description:被保险人基本资料变更</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-06-12
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tCurrentDate= PubFun.getCurrentDate();
	String tEdorAppNo = request.getParameter("EdorAppNo"); 
	String tGrpContNo = request.getParameter("GrpContNo");
	String tEdorType =  request.getParameter("EdorType");
	String tEdorNo = request.getParameter("EdorNo");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tCurrentDate = "<%=tCurrentDate%>";
	var tEdorAppNo = "<%=tEdorAppNo%>";
	var tGrpContNo = "<%=tGrpContNo%>";
	var tEdorType = "<%=tEdorType%>";
	var tEdorNo = "<%=tEdorNo%>";
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
</script>
<html>
<head>
	<title>被保险人基本资料变更</title>
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
	<script src="./EdorBBInput.js"></script>
	<%@include file="./EdorBBInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="EdorBBSave.jsp" target=fraSubmit>
	
<div id="divQueryOLD" style="display: ' '">
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
				<td class = input ><input class ="wid common"  name=OldInsuredName id=OldInsuredName></td> 
				<td class=title>证件号码</td>
				<td class = input ><input class ="wid common"  name=OldInsuredIDNo id=OldInsuredIDNo></td>
				<td class=title>被保险人客户号 </td>
				<td class = input ><input class ="wid common"  name=OldInsuredNo id=OldInsuredNo></td>
			</tr>
			<tr class=common>  
				<td class=title>保险方案</td>
				<td class=input><Input class=codeno name=ContPlanCodeOld id=ContPlanCodeOld style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showContPlanCode(this,ContPlanCodeOldName,SysPlanCodeOld);" onkeyup="showContPlanCodeName(this,ContPlanCodeOldName,SysPlanCodeOld);" ><input class=codename name=ContPlanCodeOldName >
				<input type=hidden name=SysPlanCodeOld  id=SysPlanCodeOld></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
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

	<!--修改过的被保险人信息-->
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divInsuredInfoUp);">
			</td>
			<td class=titleImg>修改过的被保险人查询条件</td>
		</tr>
	</table>
	<div id="divInsuredInfoUp" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>被保险人姓名</td>
				<td class=input><input class="wid common" name=InsuredName id=InsuredName></td>
				<td class=title>证件号码</td>
				<td class=input><input class="wid common" name=InsuredIDNo id=InsuredIDNo></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>
		<input class=cssButton type=button value="查  询" onclick="queryUpdateClick(1);">
		<input class=cssButton type=button value="返  回" onclick="top.close();">
	</div>
	<br>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divResultUpdate);">
			</td>
			<td class=titleImg>修改过的被保险人信息</td>
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
	
	<!--修改后被保险人基本信息-->
	<div id="divBBNewInfoInsured" style="display: ''">
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divBBOldInsured);">
			</td>
			<td class=titleImg>修改后被保险人基本信息</td>
		</tr>
	</table>
	<div id="divBBNewInsured" class=maxbox style="display: ''">
			<table class=common>
				<tr class=common>
					<td id=td1 style="display: ''" class=title>保全生效日期</td>
				 	<td id=td2 style="display: ''" class=input><input class="coolDatePicker" dateFormat="short" name=edorValDate verify="保全生效日期|date&&notnull" elementtype=nacessary onClick="laydate({elem: '#edorValDate'});" id="edorValDate"><span class="icon"><a onClick="laydate({elem: '#edorValDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				 	<td class=title></td>
				 	<td class=input></td>
				 	<td class=title></td>
				 	<td class=input></td>
				</tr>
				<tr class= common>
					<td class=title>与主被保险人关系</td>
					<td class=input><input class=codeno name=relatomain id=relatomain style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('relation',[this,relatomainName],[0,1],null,null,null,1,null);" onkeyup="return showCodeListKey('relation',[this,relatomainName],[0,1],null,null,null,1,null);" readonly>
					<input class=codename name=relatomainName id=relatomainName readonly></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>					
				</tr>
				<tr class= common>
					<td class= title>邮政编码 </td>
					<td class= input><Input class="wid common" name=ZipCode id=ZipCode  MAXLENGTH="6" verify="被保险人邮政编码|zipcode" ></td> 
					<td class= title>电子邮箱</td>
					<td class= input> <Input class="wid common" name=EMail id=EMail verify="被保险人电子邮箱|Email" ></td>
					<td class= title>微信号</td>
					<td class= input> <Input class="wid common" name=MicroNo id=MicroNo></td>
				</tr>
				<tr class= common>
					<td class= title>联系电话</td>
					<td class= input><input class="wid common" name=Phone id=Phone verify="联系电话|PHONE"   ></td>
					<td class= title>移动电话 </td>
					<td class= input><Input class="wid common" name=Mobile id=Mobile onblur="checkNumber(this);" verify="被保险人移动电话|len<=11"  ></td>
					<td class= title></td>
					<td class= input></td> 
				</tr>
				<tr class= common>
					<td class=title>详细地址</td>
					<td class=input colspan=5>
						<input class=codeno name=ProvinceName id=ProvinceName style="width:60px" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('province',[this, ProvinceCode],[1,0],null,null,null,'1',180);" onkeyup="return showCodeListKey('province',[this, ProvinceCode],[1,0],null,null,null,'1',180);"><input type=hidden name=ProvinceCode id=ProvinceCode readonly>省
						<input class=codeno name=CityName id=CityName style="width:60px" style="background:url(../common/images/select--bg_03.png) no-repeat right center" readonly ondblclick="return returnShowCodeList('city',[this, CityCode],[1,0]);" onkeyup="return returnShowCodeLisKey('city',[this, CityCode],[1,0]);"><input type=hidden name=CityCode id=CityCode readonly>市
						<input class=codeno name=CountyName id=CountyName style="width:60px" style="background:url(../common/images/select--bg_03.png) no-repeat right center" readonly ondblclick="return returnShowCodeList('district',[this, CountyCode],[1,0]);" onkeyup="return returnShowCodeLisKey('district',[this, CountyCode],[1,0]);"><input type=hidden name=CountyCode id=CountyCode readonly>区/县
						<input class=common name=DetailAddress id=DetailAddress verify="单位详细地址|len<100" maxlength=100 style="width:300px">
					</td>
				</tr>
				<tr class=common>					
					<td class=title>入司时间</td>
					<td class= input><input class="coolDatePicker"  dateFormat="short" name=JoinCompDate verify="入司时间|date"  onClick="laydate({elem: '#JoinCompDate'});" id="JoinCompDate"><span class="icon"><a onClick="laydate({elem: '#JoinCompDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>		
					<td class=title>工龄</td>
					<td class=input><input class="wid common" name=Seniority id=Seniority onblur="checkNumber(this);" ></td>
					<td class=title></td>
					<td class=input></td>
				</tr> 
				<tr class= common>
					<td class=title>员工号</td>
					<td class=input><input class="wid common" name=WorkIDNo id=WorkIDNo verify="员工号|len<=20" ></td>
					<td class=title>证件是否长期</td>
					<td class=input><input class=codeno name=ISLongValid id=ISLongValid style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('trueflag',[this,ISLongValidName],[0,1],null, null, null, '1', null);" onkeyup="return showCodeListKey('trueflag',[this,ISLongValidName],[0,1],null, null, null, '1', null);" ><input class=codename name=ISLongValidName readonly=true></td>
					<td class=title>证件有效期</td>
					<td class=input><Input name=LiscenceValidDate verify="证件有效期|date"  class="coolDatePicker" dateFormat="short" onClick="laydate({elem: '#LiscenceValidDate'});" id="LiscenceValidDate"><span class="icon"><a onClick="laydate({elem: '#LiscenceValidDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				</tr>
				<tr class= common>
					<td class= title>所在分公司</td>
					<td class= input><Input class="wid common"  name=ComponyName id=ComponyName  ></td>
					<td class=title>所在部门</td>
					<td class=input><input class="wid common" name=DeptCode id=DeptCode ></td>
					<td class= title>被保险人编码</td>
					<td class= input><Input class="wid common"  name=InsureCode id=InsureCode ></td>
				</tr>
				<tr class=common>
					<!--
					<td class= title>所属客户群</td>
					<td class= input><Input class=codeno name=SubCustomerNo ondblclick="return showCodeList('subcustomerno',[this,SubCustomerName],[0,1],null,'1 and a.customerno=(select appntno from lcgrpcont where grpcontno=#<%=tGrpContNo%>#) and a.state=#1#','1',1);" onkeyup="return showCodeListKey('subcustomerno',[this,SubCustomerName],[0,1],null,'1 and a.customerno=(select appntno from lcgrpcont where grpcontno=#<%=tGrpContNo%>#) and a.state=#1#','1',1);" readonly><input class=codename name=SubCustomerName readonly=true></td>
					-->
					<td class=title>工作地</td>
					<td class=input><input class="wid common" name=WorkAddress id=WorkAddress ></td>
					<td class=title>社保地</td>
					<td class=input><input class="wid common" name=SocialAddress id=SocialAddress ></td>
				</tr>
			</table>	
	</div>
</div>
<div id="divShowButton" style="display: none">
	<input class=cssButton type=button value="保  存" onclick="addRecord();">
	<input class=cssButton type=button value="撤  销" onclick="deleteRecord();">
</div>
	<!--隐藏区-->
	<input type=hidden name=Operate>	
	<input type=hidden name=CurrentDate value="<%=tCurrentDate%>">
	<input type=hidden name=GrpPropNo value="<%=tGrpContNo%>">
	<input type=hidden name=EdorAppNo value="<%=tEdorAppNo%>">
	<input type=hidden name=EdorType value="<%=tEdorType%>">
	<input type=hidden name=EdorNo value= "<%=tEdorNo%>">
	<input type=hidden name=MissionID value="<%=tMissionID%>"> <!-- 工作任务ID -->
	<input type=hidden name=SubMissionID value="<%=tSubMissionID%>"> <!-- 子工作任务ID -->
	<input type=hidden name=ActivityID value="<%=tActivityID%>"> <!-- 工作节点ID -->
	<input type=hidden name=InsuredOldName>
	<input type=hidden name=IdOldNo>	
	<input type=hidden name=SerialNo>	
	<input type=hidden name=Hidrelatomain>
	<input type=hidden name=BatchNo>
	<Br /><Br /><Br /><Br /><Br />
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
