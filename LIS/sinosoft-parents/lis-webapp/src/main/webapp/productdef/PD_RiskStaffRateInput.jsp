<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
	//程序名称：PD_RiskStaffRateInput.jsp
	//程序功能：产品定义平台--员工折扣比例定义
	//创建日期：2012-12-19
	//创建人  ：Jucy
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
		<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
		<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
		<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
		<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<%@include file="buttonshow.jsp"%>
		<%@include file="../common/jsp/ManageComLimit.jsp"%>
		
		<SCRIPT src="PD_RiskStaffRate.js"></SCRIPT>
		<%@include file="PD_RiskStaffRateInit.jsp"%>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
	<%
		GlobalInput tGI1 = new GlobalInput();
		tGI1=(GlobalInput)session.getAttribute("GI");//添加页面控件的初始化。
		String mRiskCode = request.getParameter("riskcode");
	%>
	<script>
	var mRiskCode = "<%=mRiskCode%>";
 	var comcode = "<%=tGI1.ComCode%>";
 	var tOperator = "<%=tGI1.Operator%>";
</script>

	<body onload="initForm();initElementtype();">
		<form name=fm action="PD_RiskStaffRateSave.jsp" method=post
			target="fraSubmit">
			<table>
				<tr>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
							OnClick="showPage(this,divLDPerson1);">
					</td>
					<td class=title5Img>
查询条件
					</td>
				</tr>
			</table>
			<Div id="divLDPerson1" style="display: ''">
				<table class=common align=center>
					<tr class=common>
						<TD class=title5>
管理机构
						</TD>
						<TD class=input5>
							<Input class="codeno" name=ManageCom
								ondblclick="showCodeList('ldcom',[this,ManageComName],[0,1],null,null,null,1);"
								onkeyup="showCodeListKey('ldcom',[this,ManageComName],[0,1],null,null,null,1);"
								onFocus="checkAgCom(this.value);"><input class=CodeName name=ManageComName readonly="readonly">
						</TD>
						 <td class=title5>
险种编码
						 </td>
						 <TD class=input5>
							<Input class=CodeNo name=RiskCode
								ondblclick="showCodeList('pd_lmriskinfo',[this,RiskName],[0,1],null,null,null,1);"
								onkeyup="showCodeListKey('pd_lmriskinfo',[this,RiskName],[0,1],null,null,null,1);"><Input class=CodeName readonly="readonly" name=RiskName>
						</TD>
					</TR>
					<tr class=common>
				<td class=title5>
有效起期
						</td>
						<td class=input5>
							<input class="coolDatePicker" dateFormat="short" name=StartDate 
								onClick="laydate({elem: '#StartDate'});" id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
						</td>
				</tr>
				</table>
				<tr>
					<td>
						<INPUT class=cssbutton VALUE="查 询" TYPE=button onclick="easyQueryClick();">
					</td>
				</tr>
			</Div>
			<table>
				<tr>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor: hand;" OnClick="showPage(this,divAgent1);">
					</td>
					<td class=title5Img>
基本参数信息
					</td>
				</tr>
			</table>
			<Div id="divAgent1" style="display: ''">
				<table class=common>
					<tr class=common>
						<td style="text-align:left;" colSpan=1>
							<span id="spanRateGrid"> </span>
						</td>
					</tr>
				</table>
				<table>
					<tr>
						<td>
							<INPUT class=cssbutton VALUE="首页" TYPE=button
								onclick="turnPage.firstPage();">

							<INPUT class=cssbutton VALUE="上一页" TYPE=button
								onclick="turnPage.previousPage();">

							<INPUT class=cssbutton VALUE="下一页" TYPE=button
								onclick="turnPage.nextPage();">

							<INPUT class=cssbutton VALUE="尾页" TYPE=button
								onclick="turnPage.lastPage();">
						</td>
					</tr>
					<tr>
					</tr>
				</table>
				<table>
<!-- 					<INPUT id=savabutton1 VALUE="新 增" TYPE="button" class=cssButton
						onclick="addClick();">
					<INPUT id=savabutton2 VALUE="修 改" TYPE="button" class=cssButton
						onclick="updateClick();">
					<INPUT id=savabutton3 VALUE="删 除" TYPE="button" class=cssButton
						onclick="deleteClick();">
 -->
				</table>
			</div>
			<div id="singleAdjustment" style="display: ''">
				<table>
					<tr>
						<td class=common>
							<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
								OnClick="showPage(this,divAgent2);">
						</td>
						<td class=title5Img>
基本参数信息
						</td>
					</tr>
				</table>
				<div id="divAgent2" style="display: ''">
					<table class=common align=center>
						<tr class=common>
							<TD class=title5>
管理机构
							</TD>
							<td class=input5>
								<Input class="codeno" name=ManageOrgan readonly="readonly" 
									ondblclick="return showCodeList('pd_ldcom',[this,ManageOrganName],[0,1]);" 
									onkeyup="return showCodeListKey('pd_ldcom',[this,ManageOrganName],[0,1]);"><input class=codename name=ManageOrganName readonly="readonly" elementtype=nacessary>
							</td>
							<TD class=title5>
员工折扣比例
							</TD>
							<TD class=input5>
								<Input class="common" name=STAFFRATE elementtype=nacessary>
							</TD>
						</tr>
						<tr class=common>
							<td class=title5>
险种编码
							</td>
							<TD class=input5>
								<Input class=CodeNo name=Risk
									ondblclick="showCodeList('pd_lmriskinfo',[this,RiskCodeName],[0,1],null,null,null,1);"
									onkeyup="showCodeListKey('pd_lmriskinfo',[this,RiskCodeName],[0,1],null,null,null,1);"><Input class=CodeName readonly="readonly" name=RiskCodeName
									elementtype=nacessary>
							</TD>
							<td class=title5>
保单年度(年)
							</td>
							<TD class=input5>
								<Input class="common" name=PremPayPeriod  elementtype=nacessary>
							</TD>
						</tr>
						<tr class=common>
							<td class=title5>
年龄起期
							</td>
							<TD class=input5>
								<Input class="common" name=InsureAgeStart>
							</TD>
							<td class=title5>
年龄止期
							</td>
							<TD class=input5>
								<Input class="common" name=InsureAgeEnd>
							</TD>
						</tr>

						<tr class=common>
							<td class=title5>
缴至岁数
							</td>
							<TD class=input5>
								<Input class="common" name=PayToAge>
							</TD>
							<TD class=title5>
币种
							</TD>
							<TD class=input5>
								<Input class="codeno" name=Currency
									ondblclick="dbclick(this,'sacurrency',CurrencyName,null,null);"
									onkeyup="keyup(this,'sacurrency',CurrencyName,null,null);"><input class=codename name=CurrencyName  readonly="readonly">
							</TD>
						</tr>
						<tr class=common>
							<TD class=title5>
投资类型
							</TD>
							<TD class=input5>
								<Input class="codeno" name=PayType
									ondblclick="dbclick(this,'partbelong',PayTypeName,null,null);"
									onkeyup="keyup(this,'partbelong',PayTypeName,null,null);"><input class=codename name=PayTypeName readonly="readonly">
							</TD>
							<TD class=title5>
缴费方式
							</TD>
							<TD class=input5>
								<Input class="codeno" name=PayMode
									ondblclick="dbclick(this,'paymentmode',PayModeName,null,null);"
									onkeyup="keyup(this,'paymentmode',PayModeName,null,null);"><input class=codename name=PayModeName 
									readonly="readonly">
							</TD>
						</tr>
						<tr class=common>
							<TD class=title5>
保障计划
							</TD>
							<TD class=input5>
								<Input class="codeno" name=ProtectionPlan
									ondblclick="return showCodeList('pd_lmriskinfonew',[this,ProtectionPlanName],[0,1],null,fm.Risk.value,'riskcode',1);"
									onkeyup="return showCodeList('pd_lmriskinfonew',[this,ProtectionPlanName],[0,1],null,fm.Risk.value,'riskcode',1);"><input class=codename name=ProtectionPlanName readonly="readonly">
							</TD>
							<TD class=title5>
付款年期(年)
							</TD>
							<TD class=input5>
								<Input class="common" name=PayYears >
							</TD>

						</tr>
						<TR class=common>
							
							<td class=title5>
有效起期
							</td>
							<td class=input5>
								<Input class="multiDatePicker" name=StartDate2 dateFormat='short'
									verify="有效起期|DATE" elementtype=nacessary>
							</td>
							<TD class=title5   STYLE="display:none;">
员工折扣率与银行佣金率相同标记
							</TD>
							<TD class=input5  STYLE="display:none;">
								<Input class=codeno name=SRFlag  
									ondblclick="dbclick(this,'yesorno',SRFlagS,null,null);"
									onkeyup="keyup(this,'yesorno',SRFlagS,null,null);"><Input class=codename name=SRFlagS>
							</TD>
						</TR>
						<TR class=common>
							
							
						</TR>
						<TD class=title5 STYLE="display:none;">
佣金比例
						</TD>
						<TD class=input5 STYLE="display:none;">
							<Input class="common" name=CommissionRate  value="0">
						</TD>
						<TD class=title5 STYLE="display:none;">
代理机构
						</TD>
						<TD class=input5 STYLE="display:none;">
							<Input class="codeno" name=AgentOrgan value="000000"
								ondblclick="dbclick(this,'bankcom',AgentOrganName,null,null);"
								onkeyup="keyup(this,'bankcom',AgentOrganName,null,null);"
								onFocus="showManageOrgan(this.value);"><input class=codename name=AgentOrganName readonly="readonly" >
						</TD>
					</table>
					<table>
						<INPUT VALUE="重  置" TYPE="button" class=cssButton
							onclick="addClick();">
						<INPUT id=savabutton1 VALUE="新  增" TYPE="button" class=cssButton
							onclick="saveClick();">
						<INPUT id=savabutton2 VALUE="修  改" TYPE="button" class=cssButton
							onclick="updateClick();">
						<INPUT id=savabutton3 VALUE="删  除" TYPE="button" class=cssButton
							onclick="deleteClick();">
					</table>
				</div>
			</div>
			<input type=hidden name=hideOperate value=''>
			<input type=hidden name=Operator value=''>
			<input type=hidden name=BranchType value=''>
			<Input type="hidden" name=AppState>
			<Input type="hidden" name=IDNo>
			<Input type="hidden" name=DefaultFlag value='1'>
		</form>
		<span id="spanCode" style="display: none; position: absolute;"></span>
	</body>
</html>
