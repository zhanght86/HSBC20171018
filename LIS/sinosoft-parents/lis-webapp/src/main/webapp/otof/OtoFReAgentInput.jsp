<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    GlobalInput tG1 = (GlobalInput)session.getValue("GI");
    String Branch =tG1.ComCode;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<SCRIPT src="OtoFLAInput.js"></SCRIPT>
	<title>续收员财务接口</title>
</head>
<body>
  <form method=post name=fm action= "./OtoFLASave.jsp" target="fraSubmit">
    <!-- 保单信息部分 -->
		<table class= common border=0 width=100%>
			<tr>
            <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
            <td class= titleImg>请输录入日期：</td></tr>
		</table>
        <Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
    <table  class= common>
			<TR  class= common>
				<TD  class= title5>统计年</TD>
				<TD  class= input5><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class= code name=StatYear id=StatYear verify="统计年|notnull" CodeData="0|^2007|2007年|M^2008|2008年|M^2009|2009年|M^2010|2010年|M^2011|2011年|M^2012|2012年|M^2013|2013年|M^2014|2014年|M^2015|2015年|M^2016|2016年|M^2017|2017年|M^2018|2018年|M^2019|2019年|M^2020|2020年|M" ondblClick="showCodeListEx('StatYear',[this],[0]);" onclick="showCodeListEx('StatYear',[this],[0]);" onkeyup="showCodeListKeyEx('StatYear',[this],[0]);"></TD>
				<TD  class= title5>统计月</TD>
				<TD  class= input5><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name=StatMon id=StatMon verify="统计月|not null" ondblClick="return showCodeList('month',[this]);" onclick="return showCodeList('month',[this]);"  onkeyup="return showCodeListKey('month',[this]);"></TD>
			</TR>
			
			<TR class= common>
				<TD  class= title5>凭证序号</TD>
				<TD  class= input5><Input class="wid" class= readonly name=VouchNo id=VouchNo value="11"></TD>
				<input type=hidden name="Opt">
				<TD CLASS=title5>管理机构 </TD>
				<TD CLASS=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno NAME=ManageCom id=ManageCom VALUE="" MAXLENGTH=10  ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,codeSql,'1',null,250);" onclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,codeSql,'1',null,250);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,codeSql,'1',null,250);" verify="管理机构|code:comcode&notnull" ><input class=codename name=ManageComName id=ManageComName readonly=true></TD>
			</TR>
    </table>
    </Div>
    <!--<INPUT  class=cssButton VALUE="提取凭证" name="distill" TYPE=button onclick="SubmitFormReAgent();">-->
    <a href="javascript:void(0);" name="distill" class="button" onClick="SubmitFormReAgent();">提取凭证</a>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
<script>
	<!--选择机构：只能查询本身和下级机构-->
	var codeSql = "1  and comcode like #"+<%=Branch%>+"%# and char_length(trim(comcode)) <= 6 ";
</script>
