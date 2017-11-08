<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	//程序名称：ApplyIssueDoc.jsp
	//程序功能：
	//创建日期：2006-04-07
	//创建人  ：wentao
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tG1 = (GlobalInput) session.getValue("GI");
	String Branch = tG1.ComCode;
	String mOperator = tG1.Operator;
%>
<script>
	var moperator = "<%=mOperator%>"; //记录管理机构
</script>
<head>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="./QueryOtherDoc.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="./QueryOtherDocInit.jsp"%>
</head>
<body onLoad="initForm();">
<form action="./QueryOtherDocSave.jsp" method=post name=fm id="fm"
	target="fraSubmit">
<table>
	<tr>
		<td class=common>
    		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCode1);">
		</td>
		<td class=titleImg>扫描单证信息</td>
	</tr>
</table>
<div class="maxbox">
<Div id="divCode1" style="display: ''">
<table class=common>
	<TR class=common>
		<TD class=title5>印刷号</TD>
		<TD class=input5><Input class="common wid" name=BussNo id="BussNo"></TD>
		<TD class=title5>申请类型：</TD>
		<TD class=input5><Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class='code' name=Reason id="Reason"
			CodeData="0|^UR201|投保要约撤销申请书^UR202|投保要约更正申请书(打印)^UR203|投保要约更正申请书(不打印)^UR204|生效日回溯^UR205|体检资料^UR206|未成年人身故保险金特别约定(打印)^UR207|问卷^UR208|身份证明^UR209|病例资料^UR210|其他资料^UR211|分红产品说明书^UR212|补充告知问卷"
			onclick="showCodeListEx('Reason',[this]);"
			ondblClick="showCodeListEx('Reason',[this]);"
			onkeyup="showCodeListKeyEx('Reason',[this]);"
			verify="申请类型|code:Reason"></TD>
	</TR>
	<TR class=common>
		<TD class=title5>管理机构</TD>
		<TD class=input5><Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class='code' name=ManageCom id="ManageCom"
			value="<%=Branch%>"
			onclick="return showCodeList('station',[this],null,null,codeSql,'1',null,250);"
			ondblclick="return showCodeList('station',[this],null,null,codeSql,'1',null,250);"
			onkeyup="return showCodeListKey('station',[this],null,null,codeSql,'1',null,250);"></TD>
		<TD class=title5>申请人</TD>
		<TD class=input5><Input class="common wid" name=ApplyOperator id="ApplyOperator"></TD>
	</TR>
	<TR class=common>
		<TD class=title5>申请日期</TD>
		<TD class=input5><Input class="coolDatePicker" onClick="laydate({elem: '#ApplyDate'});" verify="申请日期|DATE" dateFormat="short" name=ApplyDate id="ApplyDate"><span class="icon"><a onClick="laydate({elem: '#ApplyDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
		<TD class=title5>扫描状态</TD>
		<TD class=input5><Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class='code' name=ScanState id="ScanState"
			CodeData="0|^0|未扫描^1|已扫描"
			onclick="showCodeListEx('ScanState',[this],[0,1]);"
			ondblClick="showCodeListEx('ScanState',[this],[0,1]);"
			onkeyup="showCodeListKeyEx('ScanState',[this],[0,1]);"
			verify="扫描状态|code:ScanState"></TD>
	</TR>
	<TR class=common>
		<TD class=title5>投保单扫描日期（开始）<font color='red'>*</font></TD>
		<TD class=input5><Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="投保单扫描日期（开始）|NOTNULL" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
		<TD class=title5>投保单扫描日期（结束）<font color='red'>*</font></TD>
		<TD class=input5><Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="投保单扫描日期（结束）|NOTNULL" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
	</TR>
	<TR class=common>
		<TD class=title5>签批状态</TD>
		<TD class=input5><Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class='code' name=AcceptState  id="AcceptState"
			CodeData="0|^0|未签批^1|已签批"
			onclick="showCodeListEx('AcceptState',[this],[0,1]);"
			ondblClick="showCodeListEx('AcceptState',[this],[0,1]);"
			onkeyup="showCodeListKeyEx('AcceptState',[this],[0,1]);">
		</TD>
        <TD class=title5></TD>
		<TD class=input5></TD>
	</TR>
</table>
</Div>
</div>
<div>
	<a href="javascript:void(0)" class=button onClick="easyQueryClick();">查  询</a>
</div>
<br>
<!-- <INPUT VALUE="查  询" TYPE=button class=cssButton onclick="easyQueryClick()"> <br> -->
<Div id="divCodeGrid" style="display: ''" align=center>
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanCodeGrid"></span></td>
	</tr>
</table>
<INPUT VALUE="首  页" TYPE=button class="cssButton90" onClick="getFirstPage();">
<INPUT VALUE="上一页" TYPE=button class="cssButton91" onClick="getPreviousPage();">
<INPUT VALUE="下一页" TYPE=button class="cssButton92" onClick="getNextPage();">
<INPUT VALUE="尾  页" TYPE=button class="cssButton93" onClick="getLastPage();">
</Div>
<br>
<Div id="divReason" style="display: none">
<input type=hidden id="fmtransact" name="fmtransact">
	    <INPUT VALUE="申请撤销" name=undoApplyBtn id="undoApplyBtn" TYPE=button class=cssButton onClick="undoApply()">
</Div>
</form>
<br>
<br>
<br>
<br>
<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
<script>
	<!--选择机构：只能查询本身和下级机构-->
	var codeSql = "1  and code like #"+<%=Branch%>+"%#";
</script>
