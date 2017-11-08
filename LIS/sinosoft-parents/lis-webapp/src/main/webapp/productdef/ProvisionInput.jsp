<%@include file="../i18n/language.jsp"%>
<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：ScanContInput.jsp
//程序功能：个单新契约扫描件保单录入
//创建日期：2004-12-22 11:10:36
//创建人  ：HYQ
//更新记录：  更新人    更新日期     更新原因/内容
%>
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
var contNo="<%=tContNo%>";          //个人单的查询条件.
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
<SCRIPT src="ProvisionInput.js"></SCRIPT>
 <script src="../common/javascript/MultiCom.js"></script>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="ProvisionInit.jsp"%>
<title>扫描录入</title>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body onload="initForm();initElementtype();" >
	<form action="./ProvisionInputSave.jsp" method=post name=fm target="fraSubmit" ENCTYPE="multipart/form-data">
		<!-- 保单信息部分 -->
		<table class=common border=0 width=100%>
			<tr>
				<td class=title5Img align=center></td>
			</tr>
		</table>
		<table class=common>
			<TR class=common>
				<TD class=title5>条款类型</TD>
				<TD class=input5>
					<Input class=codeno name=ProvisionType verify="条款类型|code:provisiontype" 
					ondblclick="return showCodeList('provisiontype',[this,ProvisionTypeName],[0,1]);" 
					onkeyup="return showCodeListKey('provisiontype',[this,ProvisionTypeName],[0,1]);"><input
					 class=codename name=ProvisionTypeName elementtype=nacessary>
				</TD>
				<TD class=title5></TD><TD class=title5></TD><TD class=title5></TD><TD class=title5></TD>
			</TR>
			<TR class=common>
				<TD class=title5>险种编码</TD>
				<TD class=input5>
					<Input class=codeno name=RiskCode verify="险种编码|code:riskcode" 
					ondblclick="return showCodeList('riskcode',[this,RiskCodeName],[0,1]);" 
					onkeyup="return showCodeListKey('riskcode',[this,RiskCodeName],[0,1]);"><input class=codename name=RiskCodeName elementtype=nacessary>
				</TD>
				<TD class=title5>销售渠道</TD>
				<TD class=input5>
					<Input class=codeno name=SaleChnl verify="|code:SaleChnl" 
					ondblclick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" 
					onkeyup="return showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1]);"><input class=codename name=SaleChnlName>
				</TD>
      			<TD class=title5>管理机构</TD>
				<TD class=input5>
					
						<Input class=codeno name=ManageCom verify="管理机构|code:ManageCom" 
						ondblclick="return showCodeList('ManageCom',[this,ManageComName],[0,1]);" 
						onkeyup="return showCodeListKey('ManageCom',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName>
					
				</TD>
			</tr>
			<tr id='PTypeDiv' style="display:none;">
	      		<TD class=title5>保单性质</TD>
				<TD class=input5>
					<Input class=codeno name=PolProperty verify="保单性质|code:polproperty" 
					ondblclick="return showCodeList('polproperty',[this,PolPropertyName],[0,1]);" 
					onkeyup="return showCodeListKey('polproperty',[this,PolPropertyName],[0,1]);">
					<input class=codename name=PolPropertyName readonly="readonly" elementtype=nacessary>
				</TD>
			</tr>
		</table>
		<INPUT VALUE="查  询" class=cssButton TYPE=button onclick="easyQueryClick();">
		<table>
			<tr>
				<td class=common>
					<IMG  src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLCGrp1);">
				</td>
				<td class=title5Img></td>
			</tr>
			<INPUT type="hidden" class=Common name=MissionID  value="">
			<INPUT type="hidden" class=Common name=SubMissionID  value="">
		</table>
		<Div id="divLCGrp1" style="display: ''" align=center>
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1>
						<span id="spanProvisionGrid" ></span>
					</td>
				</tr>
			</table>
			<INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage1.firstPage();">
			<INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage1.previousPage();">
			<INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage1.nextPage();">
			<INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage1.lastPage();">
		</div>

		<table class=common>
		<tr class=common>
			&nbsp;
		</tr>
		<TR class=common>
			<TD class=title5 style="text-align:center;"></TD>
			<TD class=common>
				<input type=hidden name=ImportPath value='upload/'>
				<Input verify="导入文件|notnull" type="file" name=FileName size=25 >
				<input type=hidden name=FileNameTrue>
				<!-- <INPUT class=cssButton VALUE="导  入" TYPE=button onclick="addClick();" elementtype=nacessary> -->
				<input type=text name=SuppressWarning value='' style="display:none" />			
			</TD>
		</TR>
		</table>

		<p style="text-align:center;">
			<INPUT VALUE="删  除" class=cssButton TYPE=button onclick="deleteClick();">
			&nbsp;&nbsp;
			<INPUT VALUE="修  改" class=cssButton TYPE=button onclick="updateClick();">
			&nbsp;&nbsp;
			<INPUT VALUE="增  加" class=cssButton TYPE=button onclick="submitForm();">
		</p>
		<input class=common type=hidden name=tFlag value="<%=tFlag%>">
		<Input class=common type=hidden name=Operator >
		<input type=hidden name=Transact>
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>