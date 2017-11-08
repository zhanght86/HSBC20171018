<html>
<%@page contentType="text/html;charset=GBK"%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<style type="text/css">
a {background:#86DFF7;padding:5px 10px 3px 10px;border:1px solid #86DFF7;}
a:link {color: #000000;background:#86DFF7;}
a:visited {color: #000000;background:#86DFF7;}
a:hover {color: #000000;background:#86DFF7;}
a:active {color: #000000;background:#86DFF7;border:1px solid #888888;}
</style>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="LLBenefit.js"></SCRIPT>
<SCRIPT src="LLClaimPubFun.js"></SCRIPT>
<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
<%@include file="LLBenefitInit.jsp"%>
</head>
<body onload="initForm();">
<form action="" method=post name=fm id=fm target="fraSubmit"><br>
<span id="spanCode" style="display: none; position:absolute;z-index:9999; slategray"></span>
<table class=common>
	<tr class=common>
		<td class=common><IMG src="../common/images/butExpand.gif" 
			style="cursor:hand;" OnClick="showPage(this,InsureAccount);">
		</td>
		<td class=titleImg>已赔付给付责任详情</td>
	</tr>
</table>
<Div id=InsureAccount style="display:''" align=center>
<Table class=common>
	<tr class=common>
		<td colSpan=1><span id="spanPaidDutyGrid"></span></td>
	</tr>
</Table>
<INPUT class=cssButton90 VALUE="首　页" TYPE=button onclick="turnPage1.firstPage();">
<INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage1.previousPage();">
<INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage1.nextPage();">
<INPUT class=cssButton93 VALUE="尾　页" TYPE=button onclick="turnPage1.lastPage();">
</Div>
<table class=common>
	<tr class=common>
		<td class=common><IMG src="../common/images/butExpand.gif" 
			style="cursor:hand;" OnClick="showPage(this,ClaimAccount);">
		</td>
		<td class=titleImg>已赔付账单详情</td>
	</tr>
</table>
<Div id=ClaimAccount style="display:''" align="center">
<Table class=common>
	<tr class=common>
		<td colSpan=1><span id="spanPaidFeeGrid"></span></td>
	</tr>
</Table>
<INPUT class=cssButton90 VALUE="首　页" TYPE=button onclick="turnPage2.firstPage();">
<INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage2.previousPage();">
<INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage2.nextPage();">
<INPUT class=cssButton93 VALUE="尾　页" TYPE=button onclick="turnPage2.lastPage();">
</Div>
<hr class=line>
<a href="#" onclick="top.close();">返　回</a>
<!--隐藏表单域,保存信息用--> 
<input type=hidden name=ContNo id=ContNo> 
<input type=hidden name=CustNo id=CustNo>
<input type=hidden id=fmtransact name=fmtransact>
<br /><br /><br /><br />
</form>
</body>
</html>
