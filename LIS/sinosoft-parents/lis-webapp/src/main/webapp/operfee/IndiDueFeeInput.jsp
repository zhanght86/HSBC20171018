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
	loggerDebug("IndiDueFeeInput","tGI.Operator=" + tGI.Operator);
	loggerDebug("IndiDueFeeInput","tGI.ManageCom=" + tGI.ManageCom);
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
<SCRIPT src="IndiDueFeeInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
<%@include file="IndiDueFeeInputInit.jsp"%>
</head>

<body onload="initForm();initElementtype();">
<form name=fm id="fm" action=./IndiDueFeeQuery.jsp target=fraSubmit method=post>
	<Div  id= "divZC" style= "display: ''">
     <table class=common border=0 width=100%>
       <tr class = common>
	      功能要点：仅对应交日期位于当前日（不含）前推60天和当前日（不含）后推60天之间的保单进行催收，且无有效催收信息
	     </tr>
     </table>
  </DIV>
  <Div  id= "divGQ" style= "display: ''">
     <table class=common border=0 width=100%>
       <tr class = common>
	      功能要点：仅对应交日期位于当前日（不含）前推60天之外的保单进行催收
	     </tr>
     </table>
  </DIV>
	<table class=common border=0 width=100%>
		<tr class = common>
			<td class=common>
    				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
				</td>
  	 		<td class= titleImg>请输入催收条件</td>
  		</tr>	
	</table>
	<div class="maxbox">
	<div  id= "divFCDay" style= "display: ''">
	<table class= common>
		<tr class=common>
			<TD class=title5>管理机构</TD>
			<TD class=input5>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=ManageCom id="ManageCom"
				onclick="return showCodeList('comcode',[this,ManageComName],[0,1]);"
				ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1]);"
				onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1]);"><input 
				 class=codename name=ManageComName id="ManageComName" readonly=true>
			</TD>
			<TD class=title5>保单号码</TD>
			<TD class=input5><Input class="common wid" name=ContNo id="ContNo">
			</TD>
		</tr>
		<tr class=common>
			<TD class=title5>印刷号码</TD>
			<TD class=input5>
				<Input class="common wid" name=PrtNo id="PrtNo">
			</TD>
				
			<TD class=title5>险种代码</TD>
			<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=RiskCode id="RiskCode"
				onclick="return showCodeList('riskcode',[this,RiskCodeName],[0,1]);"
				ondblclick="return showCodeList('riskcode',[this,RiskCodeName],[0,1]);"
				onkeyup="return showCodeListKey('riskcode',[this,RiskCodeName],[0,1]);"><input
				class=codename name=RiskCodeName id="RiskCodeName" readonly=true></TD>
		</tr>

		<tr class="common">
			<TD class=title5>业务员</TD>
			<td class="input5" width="25%"><Input class="common wid" name=AgentCode id="AgentCode"><a href="javascript:void(0)" class=button onclick="queryAgent();">查  询</a></td>
			<td class="title5">交费形式</td>
			<td class=input5><input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="SecPayMode" id="SecPayMode"
				verify="交费形式|NOTNUlL&CODE:paylocation" verifyorder="1"
				onclick="return showCodeList('paylocation',[this,PayModeName],[0,1]);"
				ondblclick="return showCodeList('paylocation',[this,PayModeName],[0,1]);"
				onkeyup="return showCodeListKey('paylocation',[this,PayModeName],[0,1]);"><input
				class="codename" name="PayModeName" id="PayModeName" readonly="readonly"></td>
		</tr>
		<tr>
			<TD class=title5>销售渠道</TD>
			<TD class=input5><input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name="ContType" id="ContType"
				onclick="return showCodeList('salechnl', [this,ContTypeName],[0,1])"
				ondblclick="return showCodeList('salechnl', [this,ContTypeName],[0,1])"
				onkeyup="return showCodeListKey('salechnl', [this,ContTypeName],[0,1])"><input
				class=codename name=ContTypeName id="ContTypeName" readonly=true></TD>
		</tr>
	</table>
	</div>
	</div>
	<a href="javascript:void(0)" class=button onclick="easyQueryClick();">查  询</a>
	<!-- <INPUT VALUE="查  询" class=cssButton TYPE=button onclick="easyQueryClick()"> -->
		<!--<TD>INPUT VALUE="查询可催收的个人保单" TYPE=button class=cssButton onclick="window.open('./IndiDueFeeList.html');"
		</TD>-->
	
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
			<td text-align: left colSpan=1><span id="spanContGrid"> </span></td>
		</tr>
	</table>
	<center>
	<INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
	<INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 
	<INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();">
	<INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
	</center>
	</Div>
	
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
			<td text-align: left colSpan=1><span id="spanPolGrid"> </span></td>
		</tr>
	</table>
	<center>
		<INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage2.firstPage();"> 
		<INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage2.previousPage();"> 
		<INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage2.nextPage();"> 
		<INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage2.lastPage();">
	</center>
	</Div>
	<a href="javascript:void(0)" class=button onclick="PersonSingle();">个单催收</a>
	<a href="javascript:void(0)" class=button onclick="window.open('./OnePolStatesList.html');">查询保单续期续保状态</a>
	<!-- <INPUT VALUE="个单催收" TYPE=button class=cssButton onclick="PersonSingle()"> 
	<INPUT VALUE="查询保单续期续保状态" TYPE=button class=cssButton onclick="window.open('./OnePolStatesList.html');"> -->
	<INPUT type="hidden" name="Operator" id="Operator" value=""> 
	<INPUT type="hidden" name="ContNo1" id="ContNo1" value="">
</form>
<br>
<br>
<br>
<br>
	<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
