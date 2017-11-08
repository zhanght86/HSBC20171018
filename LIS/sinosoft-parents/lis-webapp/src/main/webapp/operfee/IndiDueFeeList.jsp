
<html>
<%
	//程序名称：IndiDueFeeInput.jsp
	//程序功能：个人保费催收，实现数据从保费项表到应收个人表和应收总表的流转
	//创建日期：2002-07-24 
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.operfee.*"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
	loggerDebug("IndiDueFeeList","tGI.Operator=" + tGI.Operator);
	loggerDebug("IndiDueFeeList","tGI.ManageCom=" + tGI.ManageCom);
%>
<script>
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var managecom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var ComCode = "<%=tGI.ComCode%>"; //记录登陆机构
	var DealType = "<%=request.getParameter("DealType")%>";
</script>

<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="IndiDueFeeList.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
<%@include file="IndiDueFeeListInit.jsp"%>
</head>

<body onload="initForm();">
<form name=fm id="fm" action=./IndiDueFeeQuery.jsp target=fraSubmit method=post>
<table class=common border=0 width=100%>
	<table class=common border=0 width=100%>
		<tr>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
			</td>
			<td class=titleImg align=center>请输入查询条件：</td>
		</tr>
	</table>
	<div class="maxbox">
	<div  id= "divFCDay" style= "display: ''">
	<table class=common>
		<tr class=common>
			<TD class=title5>管理机构：</TD>
			<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=ManageCom id="ManageCom"
				onclick="return showCodeList('comcode',[this,ManageComName],[0,1]);"
				ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1]);"
				onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1]);"><input
				class=codename name=ManageComName id="ManageComName" readonly=true
				elementtype=nacessary></TD>
			<TD class=title5>保单号码：</TD>
			<TD class=input5><Input class="common wid" name=ContNo id="ContNo"></TD>
		</tr>

		<!--  <tr class=common>
			<TD class=title>开始日期:</TD>
			<TD class=input><Input class="coolDatePicker" dateFormat="short"
				name=StartDate></TD>
			<TD class=title>终止日期:</TD>
			<TD class=input><Input class="coolDatePicker" dateFormat="short"
				name=EndDate></TD>
		</tr>-->
		
		<tr class=common>
			<TD class=title5>险种代码：</TD>
			<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=RiskCode id="RiskCode"
				onclick="return showCodeList('riskcode',[this,polName],[0,1]);"
				ondblclick="return showCodeList('riskcode',[this,polName],[0,1]);"
				onkeyup="return showCodeListKey('riskcode',[this,polName],[0,1]);"><input
				class=codename name=polName id="polName" readonly=true></TD>
			<TD class=title5>业务员：</TD>
			<TD class=input5><Input class="common wid" name=AgentCode id="AgentCode"></TD>
		</tr>

		<tr class="common">
			<td class="title5">交费形式:</td>
			<td class="input5"><input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="SecPayMode" id="SecPayMode"
				verify="交费形|NOTNUlL&CODE:continuepaymode" verifyorder="1"
				onclick="return showCodeList('continuepaymode',[this,PayModeName],[0,1]);"
				ondblclick="return showCodeList('continuepaymode',[this,PayModeName],[0,1]);"
				onkeyup="return showCodeListKey('continuepaymode',[this,PayModeName],[0,1]);"><input
				class="codename" name="PayModeName" id="PayModeName" readonly="readonly"
				elementtype=nacessary></td>
			<TD class=title5>销售渠道:</TD>
			<TD class=input5><input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name="ContType" id="ContType"
				onclick="return showCodeList('salechnl', [this,ContTypeName],[0,1])"
				ondblclick="return showCodeList('salechnl', [this,ContTypeName],[0,1])"
				onkeyup="return showCodeListKey('salechnl', [this,ContTypeName],[0,1])"><input
				class=codename name=ContTypeName id="ContTypeName" readonly=true></TD>
		</tr>
		
		<tr class="common">
			<td class="title5">续保二核标记:</td>
			<td class="input5"><input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="RnewUWFlag" id="RnewUWFlag" value="Y"
				verify="续保二核|NOTNUlL&CODE:RnewUWFlag" verifyorder="1"
				CodeData="0|^Y|包括续保二核|^N|不包括续保二核|";
				onClick="showCodeListEx('RnewUWFlag',[this,RnewUWIdea],[0,1]);" 
				ondblClick="showCodeListEx('RnewUWFlag',[this,RnewUWIdea],[0,1]);"
				onkeyup="showCodeListKeyEx('RnewUWFlag',[this,RnewUWIdea],[0,1]);"><input class="codename" name="RnewUWIdea" id="RnewUWIdea" value="包括续保二核" readonly="readonly"
				elementtype=nacessary></td>
		</tr>
	</table>
	</div>
	</div>
<a href="javascript:void(0)" class=button onclick="easyQueryClick();">查  询</a>
<!-- <INPUT VALUE="查  询" class=cssButton TYPE=button onclick="easyQueryClick()"> -->

	
	<table>
		<tr>
			<td class=common><IMG src="../common/images/butExpand.gif"
				style="cursor:hand;" OnClick="showPage(this,divLCCont);"></td>
			<td class=titleImg>保单信息</td>
		</tr>
	</table>
	<Div id="divLCCont" style="display: ''">
	<table class=common>
		<tr class=common>
			<td text-align: left colSpan=1><span id="spanContGrid">
			</span></td>
		</tr>
	</table>
	
	<center>
	<INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
	<INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 
	<INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();">
	<INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
	</center>
	</div>
	
	<table>
		<tr>
			<td class=common><IMG src="../common/images/butExpand.gif"
				style="cursor:hand;" OnClick="showPage(this,divLCPol1);"></td>
			<td class=titleImg>保单险种信息</td>
		</tr>
	</table>
	<Div id="divLCPol1" style="display: ''">
	<table class=common>
		<tr class=common>
			<td text-align: left colSpan=1><span id="spanPolGrid"> </span>
			</td>
		</tr>
	</table>
	
	<center>
		<INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage2.firstPage();"> 
		<INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage2.previousPage();"> 
		<INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button 	onclick="turnPage2.nextPage();"> 
		<INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage2.lastPage();">
	</center>
	</div>
	
	<p><!--INPUT VALUE="个单续期个案催收" TYPE=button class= cssButton onclick="PersonSingle()"-->
	<INPUT type="hidden" name="Operator"  id="Operator" value=""> 
	<INPUT type="hidden" name="ContNo2" id="ContNo2" value=""> 
	<INPUT type="hidden" name="StartDate1" id="StartDate1" value=""> 
	<INPUT type="hidden" name="EndDate1"  id="EndDate1" value=""></p>
	</form>
	<br>
	<br>
	<br>
	<br>
	<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
