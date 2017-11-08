<html>
<%
	//程序名称：PersonPayPlanInput.jsp
	//程序功能：
	//创建日期：2002-07-24 08:38:43
	//创建人  ：CrtHtml程序创建
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<!--<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>-->
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="PersonPayPlanInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="PersonPayPlanInit.jsp"%>
</head>

<body onload="initForm();">
<form action="./PersonPayPlanSave.jsp" method=post name=fm id=fm target="fraSubmit"><!-- 显示或隐藏PayPlan1的信息 -->
<table>
	<tr>
		<td class="common"><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divPayPlan1);"></td>
		<td class=titleImg>催付计划生成输入条件</td>
	</tr>
</table>

<Div id="divPayPlan1" style="display: ''" class="maxbox">

<table class=common>
	<TR class=common>
		<TD class=title5>管理机构</TD>
		<TD class=input5><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=wid class=code name=ManageCom value=""
			ondblclick="showCodeList('station',[this]);"
            onMouseDown="showCodeList('station',[this]);"
			onkeyup="return showCodeListKey('station',[this]);"></TD>
		<!--<TD class=title> 时间范围</TD>
        <td class="input" width="25%"><input class="coolDatePicker" dateFormat="short" id="timeStart" name="timeStart" verify="起始日期|NOTNULL&DATE" ></td>
        -->
		<TD class=title5>催付至日期</TD>
		<td class="input5"><!--<input class=wid class="coolDatePicker"
			dateFormat="short" id="timeEnd" name="timeEnd"
			verify="终止日期|NOTNULL&DATE">-->
            <Input  class="coolDatePicker" class="laydate-icon" onClick="laydate({elem: '#timeEnd'});" 	verify="终止日期|NOTNULL&DATE" dateFormat="short" name=timeEnd 	id="timeEnd"><span class="icon"><a onClick="laydate({elem: 	'#timeEnd'});"><img src="../common/laydate/skins/default/icon.png" 	/></a></span>
            </td>
	</TR>
	<TR class=common>
		<TD class=title5>保单号码</TD>
		<TD class=input5><Input class=wid name=ContNo></TD>
		<TD class=title5>保单险种号码</TD>
		<TD class=input5><Input class=wid name=PolNo></TD>
	</TR>
	<TR class=common>
		<TD class=title5>被保人客户号码</TD>
		<TD class=input5><Input class=wid name=InsuredNo></TD>
		<!--  <TD class=title>投保人客户号码</TD>
		<TD class=input><Input class=common name=AppntNo></TD>-->
        <TD class=title5></TD>
        <TD class=input5></TD>
	</TR>
	
</table>
</div>
<!--<Input class=cssButton type=Button value="生成催付" onclick="submitForm()">-->
<a href="javascript:void(0);" class="button" onClick="submitForm();">生成催付</a>
<Div id="divBTquery" style="display :none">
			<!--<INPUT class=cssButton TYPE=button VALUE="查询" OnClick="easyQueryClick()">-->
            <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">查    询</a>
            </Div>
<!--未入帐户生存领取表（列表） -->
<table>
	<tr>
		
            <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,pallo);"></td>
		<td class=titleImg>未入生存金帐户应付情况一览</td>
	</tr>
</table>
<Div  id= "pallo" style= "display: ''">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanGetGrid"> </span></td>
	</tr>
</table>
<center>
	<INPUT VALUE="首  页" TYPE=button class="cssButton90" onclick="getFirstPage();"> 
	<INPUT VALUE="上一页" TYPE=button class="cssButton91" onclick="getPreviousPage();"> 
	<INPUT VALUE="下一页" TYPE=button class="cssButton92" onclick="getNextPage();"> 
	<INPUT VALUE="尾  页" TYPE=button class="cssButton93" onclick="getLastPage();"></center>

</div>

<!--<INPUT class=cssButton TYPE=button VALUE="查询已经入帐户生存领取表" OnClick="queryinsuracc()">-->
<a href="javascript:void(0);" class="button" onClick="queryinsuracc();">查询已经入帐户生存领取表</a>
</Div>
<!--已经入帐户生存领取表（列表） -->
<table>
	<tr>
		<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,bushii);"></td>
		<td class=titleImg>已经入生存金帐户应付情况一览</td>
	</tr>
</table>
<Div id="bushii" style="display:">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanGetGridInsurAcc"> </span></td>
	</tr>
</table>
<center>
	<INPUT VALUE="首  页" TYPE=button class="cssButton90" onclick="getFirstPage();"> 
	<INPUT VALUE="上一页" TYPE=button class="cssButton91" onclick="getPreviousPage();"> 
	<INPUT VALUE="下一页" TYPE=button class="cssButton92" onclick="getNextPage();"> 
	<INPUT VALUE="尾  页" TYPE=button class="cssButton93" onclick="getLastPage();"></center>

</div>

<table class=common>
	<tr class=common>
		<!-- 
    	<td class = input width = 26%>
    		共生成&nbsp<Input class= readonly readonly name=getCount >&nbsp条记录。
    	</td>
   -->
		<input type=hidden id="SerialNo" name="SerialNo">
</table>

<!-- 显示或隐藏LJSGet1的信息 --></form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br><br><br><br>
</body>
</html>

