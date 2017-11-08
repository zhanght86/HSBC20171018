
<%
	//程序名称：AccQuery.jsp
	//程序功能：账户信息查询
	//创建日期：2008-03-17 15:10
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tG1 = (GlobalInput) session.getValue("GI");
	String tComCode = tG1.ComCode;
%>
<script>
	<!--选择机构：只能查询本身和下级机构-->
	var codeSql = "1  and code like #"+<%=tComCode%>+"%#";
	var comCode = "<%=tComCode%>";
</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
<SCRIPT src="./AccQuery.js"></SCRIPT>
<%@include file="./AccQueryInit.jsp"%>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<title>账号信息查询</title>
</head>

<body onLoad="initForm()">
<table class=common border=0 width=100%>
	<tr>
    <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
		<td class=titleImg align=center>请输入查询条件：</td>
	</tr>
</table>
<form method=post name=fm id=fm>
<div id="divInvAssBuildInfo">
       <div class="maxbox1" >
<table class=common>
	<TR class=common>
		<TD class=title5>管理机构</TD>
		<TD class=input5>
			<Input class=codeno name=ManageCom id=ManageCom verify="管理机构|notnull&code:station" 
            style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
            onclick="return showCodeList('Station',[this,ManageComName],[0,1]);" 
            onDblClick="return showCodeList('Station',[this,ManageComName],[0,1]);" 
            onKeyUp="return showCodeListKey('comcode',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true>
		</TD>
		<TD class=title5>银行代码</TD>
		<TD class=input5><Input class=codeno name=BankCode id=BankCode
             style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
            onclick="return showCodeList('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');"
			ondblclick="return showCodeList('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');"
			onkeyup="return showCodeListKey('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');"
			verify="银行代码|notnull" readonly=true><input class=codename
			name=BankName readonly=true></TD>
	</TR>
	<TR class=common>
		<TD class=title5>起始日期</TD>
		<TD class=input5>
        <Input class="multiDatePicker laydate-icon"   onClick="laydate({elem: '#StartDate'});"dateFormat="short" name=StartDate id=StartDate verify="起始日期|notnull&date"><span class="icon"><a onClick="laydate({elem: '#ValidStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
</TD>
		<TD class=title5>终止日期</TD>
		<TD class=input5>
         <Input class="multiDatePicker laydate-icon"   onClick="laydate({elem: '#EndDate'});"dateFormat="short" name=EndDate id=EndDate verify="终止日期|notnull&date"><span class="icon"><a onClick="laydate({elem: '#ValidStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
</TD>
	</TR>

</table>
</div>
</div>
<br>
<!--数据区--> <a href="javascript:void(0);" class="button"onclick="easyQuery();">查   询</a>
<a href="javascript:void(0);" class="button"onClick="easyPrint();">生成下载和打印数据</a>
<!--<a href="javascript:void(0);" class="button"onclick="easyPrint();" VALUE="生成下载和打印数据">生成下载和打印数据</a>-->
<!--<INPUT VALUE="生成下载和打印数据" class=cssButton  TYPE=button onClick="easyPrint();">-->

<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick=
	showPage(this, divCodeGrid);;
></td>
		<td class=titleImg>账号信息</td>
	</tr>
</table>
<Div id="divCodeGrid" style="display: ''">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanCodeGrid"> </span></td>
	</tr>
</table>
 <div align="center">
<INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick=
	getFirstPage();;
>
<INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick=
	getPreviousPage();;
>
<INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick=
	getNextPage();;
>
<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick=
	getLastPage();;
>
</div>
</div>
<br><br><br><br>

</form>
<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
