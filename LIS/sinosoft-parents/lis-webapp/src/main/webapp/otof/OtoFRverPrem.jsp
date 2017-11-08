<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
   GlobalInput tG = new GlobalInput();
   tG = (GlobalInput)session.getValue("GI");
   String Branch =tG.ComCode;
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
  <SCRIPT src="OtoFRverPrem.js"></SCRIPT>
  <title>应收保费冲消财务接口</title>
</head>
<body>
  <form method=post name=fm action= "./OtoFRverPremSave.jsp" target="fraSubmit">
    <table class= common border=0 width=100%>
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
        <td class= titleImg>请输录入日期：</td></tr>
		</table>
		<Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
    <table  class= common align=center>
    	<TR  class= common>
        <TD  class= title5>起始日期</TD>
        <TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#Bdate'});" verify="有效开始日期|DATE" dateFormat="short" name=Bdate id="Bdate"><span class="icon"><a onClick="laydate({elem: '#Bdate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><font color=red>*</font></TD>
        <TD  class= title5>终止日期</TD>
        <TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#Edate'});" verify="有效开始日期|DATE" dateFormat="short" name=Edate id="Edate"><span class="icon"><a onClick="laydate({elem: '#Edate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><font color=red>*</font></TD>
      </TR>
      
      <TR class= common>
        <TD  class= title5>凭证序号</TD>
        <TD  class= input5><Input class="wid" class= readonly name=VouchNo id=VouchNo value="61"></TD>

        <TD  class= title5>冲消日期</TD>
        <TD  class= input5> <Input class="coolDatePicker" onClick="laydate({elem: '#AccountDate'});" verify="有效开始日期|DATE" dateFormat="short" name=AccountDate id="AccountDate"><span class="icon"><a onClick="laydate({elem: '#AccountDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span> </TD>
	    </TR>
	    <TR class= common>
	    	<TD class=title5>管理机构
	      </TD>
	      <TD  class= input5>
	      <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno NAME=ManageCom id=ManageCom VALUE="" MAXLENGTH=10  ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,codeSql,'1',null,250);" onclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,codeSql,'1',null,250);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,codeSql,'1',null,250);" verify="管理机构|code:comcode&notnull" ><input class=codename name=ManageComName id=ManageComName readonly=true>
	      </TD>
	    </TR>
    </table>
    </Div>
    <input type=hidden name="Opt">
    <!--<INPUT  class=cssButton VALUE="提交"  name="distill" TYPE=button onclick="OtoFRePrem();">-->
    <a href="javascript:void(0);" name="distill" class="button" onClick="OtoFRePrem();">提    交</a>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
<script>
	<!--选择机构：只能查询本身和下级机构-->
	var codeSql = "1  and comcode like #"+<%=Branch%>+"%# ";
</script>
