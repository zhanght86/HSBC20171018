<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	//登陆信息
	GlobalInput tGI = new GlobalInput();
	if (session.getValue("GI")==null)
	{
		loggerDebug("CardProposalSignInput","null");
	}
	else
	{
		tGI = (GlobalInput)session.getValue("GI");
	}
	
%>
<script>
	//var manageCom = "<%=tGI.ManageCom%>"; //记录管理机构
	var ComCode = "<%=tGI.ComCode%>"; //记录登陆机构
</script>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<SCRIPT src="CardProposalSign.js"></SCRIPT>
	<%@include file="CardProposalSignInit.jsp"%>
	<title>签发卡单 </title>
</head>

<body  onload="initForm();" >
<form action="./CardProposalSignSave.jsp" method=post name=fm id="fm" target="fraSubmit">
	<!--#################### 保单信息查询条件部分 ##############################-->
	<Table class= common border=0 width=100%>
		<TR>
			<td class=common>
    				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
				</td>
			<td class= titleImg align= center>请输入查询条件（必须任意输入一项）：</td>
		</TR>
	</Table>
	<div class="maxbox1">
	<Div  id= "divFCDay" style= "display: ''"> 
	<Table class= common align=center>
		<TR  class= common>
			<TD  class=title5>投保单号</TD>
			<TD  class=input5><Input class= "common wid" name=ContNo id="ContNo"></TD>
			<TD  class=title5>管理机构</TD>
			<TD  class= input5>
				<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=ManageCom id="ManageCom" onclick="return showCodeList('station',[this]);" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
			</TD>
		</TR>
		<TR  class= common>
			<TD  class= title5>投保人</TD>
			<TD  class= input5><input class= "common wid" name=AppntName id="AppntName"></TD>  
			<TD  class= title5>业务员编码</TD>
			<TD  class=input5>
				<input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" NAME="AgentCode" id="AgentCode" CLASS="code" elementtype=nacessary verifyorder="1" Aonkeyup="return queryAgent();" onblur="return queryAgent();" onclick="return queryAgent();"  ondblclick="return queryAgent();" >
			</TD>
		</TR>
		<TR  class= common> 
			<TD  class= title5>录单日期(起始)</TD>
			<TD  class= input5>
				<Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="有效开始日期|DATE" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
			</TD>
			<TD  class= title5>录单日期(终止)</TD>
			<TD  class= input5>
				<Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="有效开始日期|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
			</TD>
		</TR>
		<TR class=common>
		  <TD CLASS=title5>印刷号码</TD>
			<TD CLASS=input5 COLSPAN=1>
				<Input NAME=PrtNo id="PrtNo" VALUE="" class= "common wid"  TABINDEX=-1 MAXLENGTH=14>
			</TD>
		</TR>
	</Table>
	</Div>
	</div>
	<a href="javascript:void(0)"  class=button onclick="easyQueryClick();">查  询</a>
	<!-- <INPUT VALUE="查  询" class=cssButton TYPE=button onclick="easyQueryClick()">  -->
	
	<Table>
		<TR>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCardPol);">
			</td>
			<td class= titleImg>待签单卡单信息 </td>
		</TR>
	</Table>
	
	<Div  id= "divCardPol" style= "display: ''" align = center>
		<Table  class= common>
			<TR  class= common>
				<td text-align: left colSpan=1>
					<span id="spanCardPolGrid" ></span> 
				</td>
			</TR>
		</Table>
		<INPUT CLASS=cssButton90 VALUE="首  页" class= cssButton TYPE=button onclick="turnPage.firstPage();"> 
		<INPUT CLASS=cssButton91 VALUE="上一页" class= cssButton TYPE=button onclick="turnPage.previousPage();"> 					
		<INPUT CLASS=cssButton92 VALUE="下一页" class= cssButton TYPE=button onclick="turnPage.nextPage();"> 
		<INPUT CLASS=cssButton93 VALUE="尾  页" class= cssButton TYPE=button onclick="turnPage.lastPage();">
	</Div>
	<br>
	<a href="javascript:void(0)"  class=button name="signbutton" id="signbutton" onclick="signCardPol();">签发卡单</a>
	<!-- <INPUT VALUE="签发卡单" class= cssButton name="signbutton" id="signbutton" TYPE=button onclick="signCardPol()">  -->
	
	<!-- <#################隐藏表单区域#############################>
	 <div id="divHidden" style="display:none">
		<input class=common name=ProposalContNo> <投保单号码>
		<input class=common name=ContCardNo>     <卡     号>
	</div> -->
</form>
<br>
<br>
<br>
<br>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
