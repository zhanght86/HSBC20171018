<%@include file="../i18n/language.jsp"%>
<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<html>
<%
//个人下个人
String tContNo="";
String tFlag="";
GlobalInput tGI=new GlobalInput();
tGI=(GlobalInput)session.getAttribute("GI");
tFlag=request.getParameter("type");
%>
<script>
var operator="<%=tGI.Operator%>";   //记录操作员
var manageCom="<%=tGI.ManageCom%>"; //记录登陆机构
var type="<%=tFlag%>";
var comcode="<%=tGI.ComCode%>"; //记录登陆机构
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="RiskInput.js"></SCRIPT>
 <script src="../common/javascript/MultiCom.js"></script>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="RiskInit.jsp"%>
<title>扫描录入</title>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body onload="initForm();initElementtype();" >
	<form action="./RiskSave.jsp" method=post name=fm target="fraSubmit"> <!-- ENCTYPE="multipart/form-data" -->
		<!-- 保单信息部分 -->
		<table class=common border=0 width=100%>
			<tr>
				<td class=title5Img align=center></td>
			</tr>
		</table>
		<table class=common>
			<TR class=common>
				<TD class=title5></TD>
				<TD class=input5>
					<Input class=codeno name=RiskCode verify="险种|code:riskcode" 
					ondblclick="return showCodeList('riskcode',[this,RiskCodeName],[0,1]);" 
					onkeyup="return showCodeListKey('riskcode',[this,RiskCodeName],[0,1]);"><input class=codename name=RiskCodeName>
				</TD>
				<TD class=title5></TD><TD class=title5></TD><TD class=title5></TD><TD class=title5></TD>
			</TR>
			<!-- <tr id='PTypeDiv' style="display:none;">
	      		<TD class=title5>保单性质</TD>
				<TD class=input5>
					<Input class=codeno name=PolProperty verify="保单性质|code:polproperty" 
					ondblclick="return showCodeList('polproperty',[this,PolPropertyName],[0,1]);" 
					onkeyup="return showCodeListKey('polproperty',[this,PolPropertyName],[0,1]);">
					<input class=codename name=PolPropertyName readonly="readonly" elementtype=nacessary>
				</TD>
			</tr> -->
		</table>
		<INPUT VALUE="查  询" class=cssButton TYPE=button onclick="easyQueryClick();">
		<table>
			<tr>
				<td class=common>
					<IMG  src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLCGrp1);">
				</td>
				<td class=title5Img>险种信息查询</td>
			</tr>
			<INPUT type="hidden" class=Common name=MissionID  value="">
			<INPUT type="hidden" class=Common name=SubMissionID  value="">
		</table>
		<Div id="divLCGrp1" style="display: ''" align=center>
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1>
						<span id="spanRiskGrid" ></span>
					</td>
				</tr>
			</table>
			<INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage.firstPage();">
			<INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage.previousPage();">
			<INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage.nextPage();">
			<INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage.lastPage();">
		</div>
		<table class=common border=0 width=100%>
			<tr>
				<td class=title5Img align=center></td>
			</tr>
		</table>
		<table class=common>
			<TR class=common>
				<TD class=title5></TD>
				<TD class=input5>
					<Input class=codeno name=RiskCode2 verify="险种编码|code:riskcode" 
					ondblclick="return showCodeList('riskcode',[this,RiskCodeName2],[0,1]);" 
					onkeyup="return showCodeListKey('riskcode',[this,RiskCodeName2],[0,1]);"><input class=codename name=RiskCodeName2>
				</TD>
				<td class="title">控制类型</td>
				<td class="input"><Input class="common" name=CtrlType></td>
				<TD class=title5></TD>
				<td class="input"><Input class="common" name=ParamsLen></td>
				<TD class=title5></TD>
			</TR>
			<TR>
				<td class="title">数据类型</td>
				<td class="input"><Input class="common" name=SubDivision></td>
				<td class="title"></td>
				<TD class=input5>
					<Input class=codeno name=Displayed verify="|code:displayed" 
					ondblclick="return showCodeList('displayed',[this,DisplayedName],[0,1]);" 
					onkeyup="return showCodeListKey('displayed',[this,DisplayedName],[0,1]);"><input class=codename name=DisplayedName>
				</TD>
				<td class="title"></td>
				<td class="input"><Input class="common" name=OrderNo></td>
				<TD class=title5></TD>
			</TR>
			<TR>
      			<TD class=title5></TD>
				<td class="input"><Input class="common" name=CtrlName></td>
				<TD class=title5></TD>
				<TD class=input5>
					<Input class=codeno name=IsRequired verify="|code:isrequired" 
					ondblclick="return showCodeList('isrequired',[this,IsRequiredName],[0,1]);" 
					onkeyup="return showCodeListKey('isrequired',[this,IsRequiredName],[0,1]);"><input class=codename name=IsRequiredName>
				</TD>
				<TD class=title5></TD><TD class=title5></TD><TD class=title5></TD><TD class=title5></TD>
			</TR>
			<TR>
				<td class="title"></td>
				<td class="input"><Input class="common" name=NameCn></td>
				<td class="title"></td>
				<td class="input"><Input class="common" name=NameTr></td>
				<td class="title"></td>
				<td class="input"><Input class="common" name=NameEn></td>
				<TD class=title5></TD>
			</TR>
			<TR>
      			<td class="title"></td>
				<TD class=input5 colSpan=6><textarea name=DefaultValue cols="60"
							rows="4" witdh=40% class="common"></textarea>
				</TD>
				<TD class="title"></TD>
				<TD class="input"></TD>
			</TR>
			<TR>
      			<td class="title"></td>
				<TD class="input"><input type="checkbox" name=Dynamic onclick="showPage(this,divRiskDynamicGrid);">
				</TD>
				<TD class="title"></TD>
				<TD class="input"></TD>
			</TR>
		</table>
		<Div id="divRiskDynamicGrid" style="display:none;" align=center name=DynamicGrid>
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1>
						<span id="spanRiskDynamicGrid" ></span>
					</td>
				</tr>
			</table>
		</div>
		<table class=common>
			<tr class=common>
				&nbsp;
			</tr>
			<TR class=common>
			</TR>
		</table>

		<p style="text-align:center;">
			<INPUT VALUE="删  除" class=cssButton TYPE=button onclick="deleteClick();">
			&nbsp;&nbsp;
			<INPUT VALUE="修  改" class=cssButton TYPE=button onclick="updateClick();">
			&nbsp;&nbsp;
			<INPUT VALUE="增  加" class=cssButton TYPE=button onclick="submitForm();">
		</p>
		<Input type=hidden name=SeriNo >
		<input class=common type=hidden name=tFlag value="<%=tFlag%>">
		<Input class=common type=hidden name=Operator >
		<input type=hidden name=Transact>
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>