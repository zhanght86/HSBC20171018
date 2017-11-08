<%
//程序名称：LLSecondUWRiskInput.jsp
//程序功能：险种核保信息界面-----理赔部分
//创建日期：2005-01-06 11:10:36
//创建人  ：HYQ
//更新记录：  更新人 yuejw   更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<html>
<head>
	<%
		GlobalInput tGI = new GlobalInput();
		tGI = (GlobalInput)session.getValue("GI");
		String tContNo=request.getParameter("ContNo");
		String tInsuredNo=request.getParameter("InsuredNo");
		String tSendFlag=request.getParameter("SendFlag");
		String tCaseNo=request.getParameter("CaseNo");
		String tBatNo=request.getParameter("BatNo");
	%>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="./LLSecondUWRisk.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  	<%@include file="./LLSecondUWRiskInit.jsp"%>
	<title>险种信息 </title>
</head>
<body  onload="initForm();" >
<form method=post name=fm target="fraSubmit" >
	<DIV id=DivLCPol style="display:''">
		<table class= common >
			<tr>
				<td class= titleImg align= center>险种信息：</td>
			</tr>
		</table>
		<table  class= common>
			<tr  class= common>
				<td text-align: left colSpan=1><span id="spanLLRiskGrid" ></span></td>
			</tr>
		</table>
	</DIV>
	<hr>
	<table>
		<tr>
			<td>        
				<input value="加费承保录入"  class=cssButton type=button name= "AddFee"  onclick="showAdd();">
				<input value="特约承保录入"  class=cssButton type=button onclick="showSpec();">
			</td>
		</tr>
	</table>

	<div id = "divUWResult" style = "display: ''">
	<!-- 核保结论 -->
		<table  class= common>
			<TR  class= common>
				<TD class= title>险种核保结论</td>
				<td class=input><Input class=codeno readonly name=UWState ondblclick="return showCodeList('uwstate',[this,UWStateName],[0,1]);" onkeyup="return showCodeListKey('uwstate',[this,UWStateName],[0,1]);"><Input class=codename name=UWStateName readonly ></td>
				<TD class= title></td>
				<td class=input></td>
				<TD class= title></td>
				<td class=input></td>
			</TR>
		</table>
		<table class=common>
			<tr>
				<TD >核保意见</TD>
			</tr>
			<tr>
				<TD  class= input> <textarea name="UWIdea" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
			</tr>
		</table>
		<INPUT VALUE="确  定" class=cssButton TYPE=button onclick="uwSaveClick();">
		<INPUT VALUE="取  消" class=cssButton TYPE=button onclick="cancelClick();">
		<INPUT VALUE="返  回" class=cssButton TYPE=button onclick="top.close();">
	</div>
	<!--隐藏区域-->
	<Input name="Operator"    id="Operator" type=hidden><!-- //记录操作员 -->
	<Input name="ComCode"    id="ComCode" type=hidden><!-- //记录登陆机构 -->
	<Input name="ManageCom"    id="ManageCom" type=hidden><!-- //记录管理机构 -->
	
	<Input name="SendFlag"    id="SendFlag" type=hidden><!-- 用途未知 -->
	<Input name="CaseNo"   id="CaseNo" type=hidden><!--赔案号 -->
	<Input name="BatNo"   id="BatNo" type=hidden><!-- 批次号 -->
	<Input name="ContNo"  id="ContNo" type=hidden><!-- 合同号 -->
	<Input name="InsuredNo"   id="InsuredNo" type=hidden><!--出险人客户号  -->
	<Input name="PolNo"   id="PolNo" type=hidden><!--保单号码  -->
	
</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
