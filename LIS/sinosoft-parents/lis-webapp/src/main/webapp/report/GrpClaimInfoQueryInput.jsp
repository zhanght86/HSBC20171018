<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
	//程序名称：GrpClaimInfoQueryInput.jsp
	//程序功能：理赔情况明细统计表
	//创建日期：20090317
	//创建人  ：袁亦方
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getAttribute("GI");
%>
<script>
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录管理机构
	var comcode = "<%=tGI.ComCode%>";     //记录登陆机构
</script>
<title>理赔情况明细统计表</title>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="GrpClaimInfoQuery.js"></SCRIPT>
<%@include file="GrpClaimInfoQueryInit.jsp"%>
</head>
<body onload="initForm()">
<form action="" method=post name=fm id=fm target="fraTitle">
<table class=common border=0 width=100%>
	<tr>
    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,holoday);"></td>
		<td class=titleImg>&nbsp;请输入查询条件：</td>
	</tr>
</table>
<Div  id= "holoday" style= "display: ''" class="maxbox">
<table class=common>
	<tr class=common>
		<td class=title5>管理机构</td>
		<td class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ManageCom id=ManageCom readonly=true
			verify="管理机构|notnull&code:station"
			ondblclick="return showCodeList('station',[this,sManageCom],[0,1]);"
            onclick="return showCodeList('station',[this,sManageCom],[0,1]);"
			onkeyup="return showCodeListKey('station',[this,sManageCom],[0,1]);"><input
			class=codename name=sManageCom id=sManageCom readonly=true></td>	
		<TD class=title5>生效起始日期</TD>
		<TD class=input5>
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="生效起始日期|notnull" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>	
            </tr>
            <tr class=common>
		<TD class=title5>生效截止日期</TD>
		<TD class=input5>
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="生效截止日期|notnull" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>	
            <td class=title5>团单合同号</td>
		<td class=input5><Input class="wid" class=common name=GrpContNo id=GrpContNo></td>
	</tr>
	<tr class=common>
			
		<TD class=title5>投保单位名称</TD>
		<TD class=input5><Input class="wid" class=common name=GrpName id=GrpName ></td>	
		<TD class=title5>险种代码</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=RiskCode id=RiskCode 
      		ondblclick="return showCodeList('RiskCode',[this,sRiskCode],[0,1],null,null,null,1);" 
            onclick="return showCodeList('RiskCode',[this,sRiskCode],[0,1],null,null,null,1);" 
      		onkeyup="return showCodeListKey('RiskCode',[this,sRiskCode],[0,1],null,null,null,1);"><input 
      		class=codename name=sRiskCode readonly=true></TD>	
	</tr>
	<tr class=common>
		<td class=title5>投保单位黑名单标记</td>
		<td class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=Blacklist id=Blacklist 
			verify="黑名单标记|code:BlacklistFlag" 
			ondblclick="return showCodeList('BlacklistFlag',[this,sBlacklist],[0, 1]);" 
            onclick="return showCodeList('BlacklistFlag',[this,sBlacklist],[0, 1]);"
			onkeyup="return showCodeListKey('BlacklistFlag',[this,sBlacklist],[0, 1]);"><Input 
			class=codename name=sBlacklist id=sBlacklist readonly=true></td>	
          <td class=title5></td> 
          <td class=input5></td> 			
	</tr>	
</table></Div>
<!--<INPUT VALUE="查询理赔明细统计表" class="cssButton" TYPE="button" name="query"
	onclick="easyQuery();">-->
    <a href="javascript:void(0);" class="button" name="query" onClick="easyQuery();">查询理赔明细统计表</a>
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divInvoice);"></td>
		<td class=titleImg>理赔情况明细统计表</td>
	</tr>
</table>
<Div id="divInvoice" style="display: ''" align=center>
<table class=common>
	<tr class=common>
		<td style="text-align:left" colSpan=1><span id="spanClaimInfoGrid"></span>
		</td>
	</tr>
</table>
	
</div>
	<INPUT VALUE="" TYPE=hidden name="sql">
	<INPUT VALUE="导出Excel文件" name="toExcel" class=cssButton TYPE=button	onclick="ToExcel()"><!--<br>
    <a href="javascript:void(0);" name="toExcel" class="button" onClick="ToExcel();">导出Excel文件</a>-->
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</form><br><br><br><br>
</body>
</html>
