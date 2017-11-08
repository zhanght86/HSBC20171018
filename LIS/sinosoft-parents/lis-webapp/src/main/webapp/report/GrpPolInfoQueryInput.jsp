<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//程序名称：GrpPolInfoQueryInput.jsp
	//程序功能：承保清单查询
	//创建日期：20090320
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
<title>承保清单查询</title>
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
<SCRIPT src="GrpPolInfoQuery.js"></SCRIPT>
<%@include file="GrpPolInfoQueryInit.jsp"%>
</head>
<body onload="initForm()">
<form action="" method=post name=fm target="fraTitle">
<table class=common border=0 width=100%>
	<tr>
    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,zoo);"></td>
		<td class=titleImg>&nbsp;请输入查询条件：</td>
	</tr>
</table>
<Div  id= "zoo" style= "display: ''" class="maxbox">
<table class=common>
	<tr class=common>
		<td class=title5>管理机构</td>
		<td class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ManageCom id=ManageCom readonly=true
			verify="管理机构|notnull&code:station"
			ondblclick="return showCodeList('station',[this,sManageCom],[0,1]);"
            onclick="return showCodeList('station',[this,sManageCom],[0,1]);"
			onkeyup="return showCodeListKey('station',[this,sManageCom],[0,1]);"><input
			class=codename name=sManageCom id=sManageCom readonly=true></td>
		<TD class=title5>代理人编码</TD>		
		<td class="input5">
			<Input class="wid" class=code name=AgentCode id=AgentCode >
		</td>
        </tr>
        <tr class=common>
		<TD class=title5>起始日期<font color=red> *</font></TD>
		<TD class=input5><Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="起始日期|notnull" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>	
            <TD class=title5>截止日期<font color=red> *</font></TD>
		<TD class=input5><Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="截止日期|notnull" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>	
	</tr>
	<TR class=common>		
				
		<TD class=title5>销售渠道</TD>
		<TD class=input5><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=SaleChnl id=SaleChnl
			verify="销售渠道|notnull&code:SaleChnl"
		    ondblclick="return showCodeList('SaleChnl',[this,sSaleChnl],[0, 1]);" 
            onclick="return showCodeList('SaleChnl',[this,sSaleChnl],[0, 1]);"
		    onkeyup="return showCodeListKey('SaleChnl',[this,sSaleChnl],[0, 1]);"><input 
		    class=codename name=sSaleChnl id=sSaleChnl readonly=true></TD>
		<TD class=title5>二级渠道</TD>		
		<TD class=input5><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=AgentType  id=AgentType
			verify="二级渠道|notnull&code:AgentType" 
		    ondblclick="return showCodeList('AgentType',[this,sAgentType],[0,1]);" 
            onclick="return showCodeList('AgentType',[this,sAgentType],[0,1]);" 
		    onkeyup="return showCodeListKey('AgentType',[this,sAgentType],[0,1]);"><input 
		    class=codename name=sAgentType id=sAgentType readonly=true></TD>	
	</TR>
	<tr class=common>
		<TD class=title5>险种类别</TD>
		<td class="input5"><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=RiskType id=RiskType
			verify="险种类别|notnull" 
			ondblclick="return showCodeList('RiskTypeg', [this,sRiskType],[0,1],null)"
            onclick="return showCodeList('RiskTypeg', [this,sRiskType],[0,1],null)"
			onkeyup="return showCodeListKey('RiskTypeg', [this,sRiskType],[0,1],null)"><input
			class=codename name=sRiskType id=sRiskType readonly=true></td>
		<TD class=title5>险种代码</TD>
		<td class="input5">		  
			<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=RiskCode id=RiskCode 
      		ondblclick="return showCodeList('RiskCode',[this,sRiskCode],[0,1],null,null,null,1);" 
            onclick="return showCodeList('RiskCode',[this,sRiskCode],[0,1],null,null,null,1);"
      		onkeyup="return showCodeListKey('RiskCode',[this,sRiskCode],[0,1],null,null,null,1);"><input 
      		class=codename name=sRiskCode id=sRiskCode readonly=true></td>      		
		
	</tr>
	<tr class=common>
    <TD class=title5>黑名单标记</TD>		
		<td class="input5"><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=Blacklist id=Blacklist 
			verify="黑名单标记|code:BlacklistFlag" 
			ondblclick="return showCodeList('BlacklistFlag',[this,sBlacklist],[0, 1]);" 
            onclick="return showCodeList('BlacklistFlag',[this,sBlacklist],[0, 1]);"
			onkeyup="return showCodeListKey('BlacklistFlag',[this,sBlacklist],[0, 1]);"><Input 
			class=codename name=sBlacklist id=sBlacklist readonly=true></td>		
        <TD class=title5>保全加费</TD>
		<td class="input5"><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=InsuranceMoney id=InsuranceMoney
			verify="保全加费|notnull" CodeData="0|^0|不包括^1|包括"
			ondblclick="return showCodeListEx('InsuranceMoney', [this,sInsuranceMoney],[0,1])"
            onclick="return showCodeListEx('InsuranceMoney', [this,sInsuranceMoney],[0,1])"
			onkeyup="return showCodeListKeyEx('InsuranceMoney', [this,sInsuranceMoney],[0,1])"><input
			class=codename name=sInsuranceMoney id=sInsuranceMoney readonly=true></td>	
            </tr>
            <tr class=common>	
			  <TD  class= title5>投保单位名称 </TD>
          <TD  class= input5> <Input class="common wid" name=GrpName id=GrpName > </TD>
          <TD  class= title5></TD>
          <TD  class= input5></TD>
	</tr>	
</table></Div>
<!--<INPUT VALUE="查询承保清单" class="cssButton" TYPE="button" name="query"
	onclick="easyQuery();">-->
    <a href="javascript:void(0);" class="button" name="query" onClick="easyQuery();">查询承保清单</a>
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divInvoice);"></td>
		<td class=titleImg>承保清单列表</td>
	</tr>
</table>
<Div id="divInvoice" style="display: ''" align=center>
<table class=common>
	<tr class=common>
		<td style="text-align:left" colSpan=1><span id="spanPolInfoGrid"></span>
		</td>
	</tr>
</table>
	<INPUT VALUE="首页" class="cssButton90" TYPE=button onclick="getFirstPage();"> 
	<INPUT VALUE="上一页" class="cssButton91" TYPE=button onclick="getPreviousPage();"> 
	<INPUT VALUE="下一页" class="cssButton92" TYPE=button onclick="getNextPage();"> 
	<INPUT VALUE="尾页" class="cssButton93" TYPE=button onclick="getLastPage();">
</div>
	<INPUT VALUE="" TYPE=hidden name=sql>
	<!--<INPUT VALUE="导出Excel文件" name="toExcel" class=cssButton TYPE=button	onclick="ToExcel()">-->
    <a href="javascript:void(0);" name="toExcel" class="button" onClick="ToExcel();">导出Excel文件</a>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</form><br><br><br><br>
</body>
</html>
