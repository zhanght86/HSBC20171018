<%
/***************************************************************
 * <p>ProName：LCPropEntryInput.jsp</p>
 * <p>Title：投保书信息录入</p>
 * <p>Description：投保书信息录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
	String tGrpPropNo = request.getParameter("GrpPropNo");
	String tFlag = request.getParameter("Flag");
	
	String tCurrentDate = PubFun.getCurrentDate();
	String tCurrentTime = PubFun.getCurrentTime();

%>

<script>
	var tGrpPropNo = "<%=tGrpPropNo%>";
	var tManageCom = "<%=tGI.ManageCom%>";//记录登陆机构
	var tOperator = "<%=tGI.Operator%>";
	var tFlag = "<%=tFlag%>";	
	
	var	tCurrentDate   = "<%=tCurrentDate%>";
	var	tCurrentTime   = "<%=tCurrentTime%>";

</script>
<html>
<head>
	<title>投保书信息录入</title>
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
	<script src="./LCPropEntryInput.js"></script>
	<script src="./LCContCommonInput.js"></script>
	<%@include file="./LCPropEntryInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">

<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo);">
			</td>
			<td class=titleImg>投保人资料</td>
		</tr>
	</table>
	<div id="divInfo" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>投保人名称</td>
				<td class=input colspan=5><input class=common name=GrpName verify="准客户名称|notnull&len<60" maxlength=60 style="width:554px" elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>经营范围</td>
				<td class=input colspan=3><input class=common name=MainBusiness verify="经营范围|notnull&len<200" maxlength=200 style="width:554px" elementtype=nacessary></td>
				<td class=title>法人代表</td>
				<td class=input><input class="wid common" name=Corporation verify="法人|notnull&len<60" maxlength=60 elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>法人代表证件类型</td>
				<td class=input><input class=codeno name=CorIDType ondblclick="return showCodeList('idtype',[this,CorIDTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('idtype',[this,CorIDTypeName],[0,1],null,null,null,'1',null);" ><input class=codename name=CorIDTypeName></td>
				<td class=title>法人代表证件号码</td>
				<td class=input><input class="wid common" name=CorID onblur="checkCoridtype();" ></td>
				<td class=title>法人代表证件有效止期</td>
				<TD class=input><input class="coolDatePicker" verify="法人证件有效止期|date" dateFormat="short" name=CorIDExpiryDate onClick="laydate({elem: '#CorIDExpiryDate'});" id="CorIDExpiryDate"><span class="icon"><a onClick="laydate({elem: '#CorIDExpiryDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD> 
			</tr>
			<tr class=common>
				<td class=title>行业分类</td>
				<td class=input><input type=hidden name=BusiCategoryCode><input class="wid readonly" name=BusiCategory id=BusiCategory ></td>
				<td class=title>成立时间</td>
				<td class=input><input class=coolDatePicker verify="成立时间|date" dateFormat="short" name=FoundDate onClick="laydate({elem: '#FoundDate'});" id="FoundDate"><span class="icon"><a onClick="laydate({elem: '#FoundDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>社保保险登记证号</td>
				<td class=input><input class="wid common" id=SocialInsuCode  name=SocialInsuCode></td>
			</tr>
			<tr class=common>
				<td class=title>单位性质</td>
				<td class=input><input class=codeno name=GrpNature verify="单位性质|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('grpnature',[this,GrpNatureName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('grpnature',[this,GrpNatureName],[0,1],null,null,null,'1',null);"><input class=codename name=GrpNatureName elementtype=nacessary></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>单位证件类型</td>
				<td class=input><input class=codeno readonly name=GrpIDType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('gidtype',[this,GrpIDTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('gidtype',[this,GrpIDTypeName],[0,1],null,null,null,'1',null);"><input class=codename  name=GrpIDTypeName  elementtype=nacessary></td>
				<td class=title>单位证件号码</td>
				<td class=input><input class="wid common" name=GrpIDNo id=GrpIDNo  maxlength=30  elementtype=nacessary></td>
				<td class=title>单位证件有效止期</td>
				<TD class=input><Input class=coolDatePicker verify="单位证件有效止期|date" dateFormat="short" name=GrpIDExpiryDate onClick="laydate({elem: '#GrpIDExpiryDate'});" id="GrpIDExpiryDate"><span class="icon"><a onClick="laydate({elem: '#GrpIDExpiryDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD> 
			</tr>
			<!--单位勾选-->
			<tr class=common>
				<td class=title colspan=6>单位证件信息设置请勾选<input class=checkbox type=checkbox name='IDInfoCheck' onclick="selectIDInfo();"></td>
			</tr>
			<tr class=common>
				<td class=title colspan=6>
					<div id="divIDInfoGrid" style="display: none">
						<table class=common>
							<tr class=common>
								<td text-align: left colSpan=1>
									<span id="spanIDInfoGrid" ></span>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>	
			<tr class=common>
				<td class=title>单位地址</td>
				<td class=input colspan=3>
					<input class=codeno name=ProvinceName style="width:60px" onChange="clearCityAndCounty();" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('province',[this, Province],[1,0],null,null,null,'1',180);" onkeyup="return showCodeListKey('province',[this, Province],[1,0],null,null,null,'1',180);"><input type=hidden name=Province readonly>省
					<input class=codeno name=CityName style="width:60px" onChange="clearCounty();" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeLis('city',[this, City],[1,0],null,null,null,1,null);" onkeyup="return returnShowCodeLisKey('city',[this, City],[1,0],null,null,null,1,null);"><input type=hidden name=City readonly>市
					<input class=codeno name=CountyName style="width:60px" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeLis('district',[this, County],[1,0],null,null,null,1,null);" onkeyup="return returnShowCodeLisKey('district',[this, County],[1,0],null,null,null,1,null);"><input type=hidden name=County readonly>区/县
					<input class=common name=Address verify="单位详细地址|len<100" maxlength=100 style="width:300px">
				</td>
				<td class=title>邮政编码</td>
				<td class=input><input class="wid common" name=ZipCode id=ZipCode verify="邮政编码|zipcode"  MAXLENGTH="6"></td>
			</tr>
			<tr class=common>
				<td class=title>单位联系电话</td>
				<td class=input><input class="wid common" name=Phone id=Phone></td>
				<td class=title>单位传真</td>
				<td class=input><input class="wid common" name=Fax id=Fax></td>
				<td class=title>单位E-mail地址</td>
				<td class=input><input class="wid common" name=EMail id=EMail verify="单位E-mail|EMAIL"></td>
			</tr>
			<tr class=common>
				<td class=title>联系人姓名</td>
				<td class=input><input class="wid common" name=LinkMan id=LinkMan></td>
				<td class=title>联系人电话</td>
				<td class=input><input class="wid common" name=LinkPhone id=LinkPhone></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>联系人证件类型</td>
				<td class=input><input class=codeno name=LinkIDType id=LinkIDType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('idtype',[this,LinkIDTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('idtype',[this,LinkIDTypeName],[0,1],null,null,null,'1',null);"><input class=codename name=LinkIDTypeName></td>
				<td class=title>联系人证件号码</td>
				<td class=input><input class="wid common" name=LinkID id=LinkID onblur="checkidtype();" ></td>
				<td class=title>联系人证件有效止期</td>
				<td class=input><input class="coolDatePicker" verify="联系人证件有效止期|date" dateFormat="short" name=LinkIDExpiryDate ></td>
			</tr>
			<tr class=common>
				<td class=title>单位总人数</td>
				<td class=input><input class="wid common" name=SumNumPeople id=SumNumPeople verify="单位总人数|INT"></td>
				<td class=title>主被保险人数</td>
				<td class=input><input class="wid common" name=MainContNumber id=MainContNumber verify="主被保险人数|INT"></td>
				<td class=title>附属被保险人数</td>
				<td class=input><input class="wid common" name=RelatedContNumber id=RelatedContNumber verify="附属被保险人数|INT"></td>
			</tr>
		</table>
		<div align="right" style="display:none" id=savePrint>
			<input class=cssButton type=button  value="保  存" onclick="saveClick();">
		</div>
	</div>
	<!-- 基本投保信息 -->
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divBasicInfo);">
			</td>
			<td class=titleImg>基本投保信息</td>
		</tr>
	</table>
	<div id="divBasicInfo" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>契约生效日类型</td>
				<td class=input><input class=codeno name=ValDateType id=ValDateType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" showCodeList('valdatetype',[this,ValdateTypeName],[0,1],null,null,null,1,null);" onkeyup="return showCodeListKey('valdatetype',[this,ValdateTypeName],[0,1],null,null,null,1,null);"><input class=codename name=ValdateTypeName id=ValdateTypeName readonly=true><font color="#ff0000">*</font></td>
				<td class=title><div id=valDatename style="display: ''">生效日期</div></td>
				<TD class=input><div id=valDatename1 style="display: ''"><Input class="coolDatePicker" dateFormat="short"  name=ValDate onClick="laydate({elem: '#ValDate'});" id="ValDate"><span class="icon"><a onClick="laydate({elem: '#ValDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><font color="#ff0000">*</font></div></TD> 
				<td class=title>保险期限</td>
				<td class=input><input class="codename"  name=InsuPeriod id=InsuPeriod ><input class=codeno name=InsuPeriodName id=InsuPeriodName style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('insuperiodflag',[this, InsuPeriodFlag],[1, 0],null,null,null,'1',null);" onkeyup="return showCodeListKey('InsuPeriodflag',[this, InsuPeriodFlag],[1,0],null,null,null,'1',null);"><font color="#ff0000">*</font><input type=hidden name=InsuPeriodFlag readonly>
			</tr>
			<tr class=common>
				<td class=title>缴费方式</td>
				<td class=input><input class=codeno name=PayIntv id=PayIntv style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('payintv',[this,PayIntvName],[0,1],null,null,null,'1',null);" onkeyup="return returnShowCodeListKey('payintv',[this,PayIntvName],[0,1],null,null,null,'1',null);"><input class=codename name=PayIntvName id=PayIntvName ><font color="#ff0000">*</font></td>
				<td class=title>保费分摊方式</td>
				<td class=input><input class=codeno name=PremMode id=PremMode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('premmode',[this,PremModeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('premmode',[this,PremModeName],[0,1],null,null,null,'1',null);"><input class=codename name=PremModeName id=PremModeName ><font color="#ff0000">*</font></td>
				<td class=title>付款方式</td>
				<td class=input><input class=codeno name=PayType id=PayType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('paymode',[this,PayTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('paymode',[this,PayTypeName],[0,1],null,null,null,'1',null);"><input class=codename name=PayTypeName id=PayTypeName ><font color="#ff0000">*</font></td>
			</tr>
		</table>
		<div class=common id=divPlanDiv>
		</div>
		<table class=common>
			<tr class=common>
				<td class=title>销售渠道类型</td>
				<td class=input><input class=codeno name=ChnlType id=ChnlType readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('salechannel',[this,ChnlTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeList('salechannel',[this,ChnlTypeName],[0,1],null,null,null,'1',null);"><input class=codename name=ChnlTypeName ><font color="#ff0000">*</font></td>
				<td class=title>销售渠道</td>
				<td class=input><input class=codeno name=SaleChnl id=SaleChnl readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('agenttype',[this,SaleChnlName],[0,1],null,null,null,'1',null);" onkeyup="return returnShowCodeListKey('agenttype',[this,SaleChnlName],[0,1],null,null,null,'1',null);" ><input class=codename name=SaleChnlName ><font color="#ff0000">*</font></td>
				<td class=title>承保机构</td>
				<td class=input><input class=codeno name=AppManageCom id=AppManageCom readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" returnShowCodeList('comcodeall',[this,ManageComName],[0,1]);" onkeyup="return returnShowCodeListKey('comcodeall',[this,ManageComName],[0,1],null,'1 and comgrade=#03#',1,'1',180);"><input class=codename name=ManageComName readonly ><font color="#ff0000">*</font></td>
			</tr>
		</table>
	
		<div id="divZJInfo" style="display: ''">
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1>
							<span id="spanZJGrid" ></span>
						</td>
					</tr>
				</table>
		</div>
	<hr class=line />
	<div id="divGrpSpecInfo" style="display: ''">
		<table class=common>
			<tr class= common> 
				<td class= title> 特别约定<font color="red">&nbsp;&nbsp;(注意：若无特别约定请注明：无特别约定；若有，请在特别约定结束处注明： 除此之外无其他特别约定)</font></td>
			</tr>
			<tr  class= common>
				<td  class=title>
					<textarea name="GrpSpec" id=GrpSpec cols="110" rows="3" class="common" ></textarea>
				</td>
			</tr>
		</table>
	</div>
		<div align="right" style="display:none" id=saveProp>
			<input class=cssButton type=button value="保  存" onclick="savesClick();">
		</div>
	</div>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divFYXZInfo);">
				</td>
				<td class=titleImg>附页选择</td>
			</tr>
		</table>
		<div id="divFYXZInfo" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title><input type=checkbox name=AddService>附加服务<input type=checkbox name=PersonProp>业务员报告书</td>
				</tr>
			</table>
		</div>
		<input class=cssButton type=button value="中介手续费率维护" onclick="goToChargeFee();">
		<input class=cssButton type=button value="人员清单维护" onclick="goToInsuredInfo();">
		<input class=cssButton type=button value="打印投保单" name=PrintProp disabled=true onclick="printPrtProp();"> 
		<input class=cssButton type=button value="补打财务保费确认书" onclick="printPremConf();">
		<input class=cssButton type=button value="返  回" onclick="turnBack();">
		<input type=hidden name=Operate>
		<input type=hidden name=GrpPropNo>
		<input type=hidden name=Flag>
		<input type=hidden name=QuotNo>
		<input type=hidden name=QuotBatNo>
		<input type=hidden name=QuotType>
		<input type=hidden name=PolicyType>
		<input type=hidden name=ContPlanType>
		
		<!--获取当前时间信息-->
		<input type=hidden name=tCurrentDate value="<%=tCurrentDate%>">
		<input type=hidden name=tCurrentTime value="<%=tCurrentTime%>">
		<input type=hidden name=ManageCom value="<%=tGI.ManageCom%>">
	<Br /><Br />	<Br />	<Br />		
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
