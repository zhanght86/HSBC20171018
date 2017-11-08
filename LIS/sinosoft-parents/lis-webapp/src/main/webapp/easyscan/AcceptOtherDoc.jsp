<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：AcceptIssueDoc.jsp
//程序功能：
//创建日期：2006-04-07
//创建人  ：wentao
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    GlobalInput tG1 = (GlobalInput)session.getValue("GI");
    String Branch = tG1.ComCode;
		String mOperator = tG1.Operator;
%>
<script>
	var moperator = "<%=mOperator%>"; //记录管理机构
</script>
<head >
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="./AcceptOtherDoc.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./AcceptOtherDocInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./AcceptOtherDocSave.jsp" method=post name=fm id="fm" target="fraSubmit">
    <table>
    	<tr>
				<td class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCode1);"></td>
				<td class= titleImg>扫描单证信息</td></tr></table>
    <div class="maxbox1">	 
    <Div  id= "divCode1" style= "display: ''">
      <table  class= common>
        <TR  class= common>
          <TD  class= title5>印刷号</TD>
          <TD  class= input5><Input class= "common wid" name=BussNo id="BussNo"></TD>
          <TD  class= title5>管理机构</TD>
          <TD  class= input5><Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class='code' name=ManageCom id="ManageCom" value="<%=Branch%>"
          		onclick="return showCodeList('station',[this],null,null,codeSql,'1',null,250);"  ondblclick="return showCodeList('station',[this],null,null,codeSql,'1',null,250);" 
            	onkeyup="return showCodeListKey('station',[this],null,null,codeSql,'1',null,250);"></TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>起始日期</TD>
          <TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="起始日期|DATE" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
          <TD  class= title5>结束时间</TD>
          <TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="结束时间|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
        </TR>
      </table>
    </Div>
    </div>
    <div>
    </div>  
    <br>
    <INPUT VALUE="查    询" TYPE=button class=cssButton onclick="easyQueryClick()">
  	<Div  id= "divCodeGrid" style= "display: ''" >
    	<table  class= common>
     		<tr  class= common>
  	  		<td text-align: left colSpan=1><span id="spanCodeGrid" ></span></td>
				</tr>
  		</table>
      <div align=center>
    	<INPUT VALUE="首  页" TYPE=button class=cssButton90 onclick="getFirstPage();"> 
    	<INPUT VALUE="上一页" TYPE=button class=cssButton91 onclick="getPreviousPage();">
    	<INPUT VALUE="下一页" TYPE=button class=cssButton92 onclick="getNextPage();"> 
    	<INPUT VALUE="尾  页" TYPE=button class=cssButton93 onclick="getLastPage();">
      </div>
  	</div>
    <div>
    </div>
    <br>
    <INPUT VALUE="签批同意" TYPE=button class=cssButton name="Accept" onclick="submitForm()"> 
	<input type=hidden id="fmtransact" name="fmtransact">
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
<script>
	<!--选择机构：只能查询本身和下级机构-->
	var codeSql = "1  and code like #"+<%=Branch%>+"%#";
</script>
