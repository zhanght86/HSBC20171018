<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <!--SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT-->
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="GroupPolSign.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GroupPolSignInit.jsp"%>
  <title>集体签单 </title>
</head>
<body  onload="initForm();" >
  <form action="./GroupPolSignSave.jsp" method=post name=fm id="fm" target="fraSubmit">
    <!-- 集体信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
			  <td class= titleImg align= center>请输入查询集体条件：</td>
		  </tr>
	  </table>
    <div class="maxbox1">
    <Div  id= "divFCDay" style= "display: ''">
    <table  class= common align=center>
      <TR  class= common>
        <Input type="hidden" class= "common wid" name=PrtNo id="PrtNo">
        <Input type="hidden" class= "common wid" name=tt id="tt">
        <TD  class= title5>结算批次号</TD>
        <TD  class= input5>
          <Input class= "common wid" name=GrpContNo id="GrpContNo">
        </TD>
        <TD class=title5>管理机构</TD>
				<TD class=input5>
					<Input  style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class=codeno name=ManageCom id="ManageCom" verify="管理机构|code:comcode&notnull"
            onclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,'#1#','1');"
           ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,'#1#','1');" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,'#1#','1');" ><input class=codename name=ManageComName id="ManageComName" readonly=true elementtype=nacessary>
				</TD>
      </TR>  
    </table>
  </Div>
  </div>
  <a href="javascript:void(0)" class=button onclick="easyQueryClick();">查  询</a>
  <!-- <INPUT VALUE="查  询" TYPE=button class= cssButton onclick="easyQueryClick();">  -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGrp1);">
    		</td>
    		<td class= titleImg>
    			 集体信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCGrp1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanGrpGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="首  页" TYPE=button class= cssButton90 onclick="getFirstPage();"> 
      <INPUT VALUE="上一页" TYPE=button class= cssButton91 onclick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" TYPE=button class= cssButton92 onclick="getNextPage();"> 
      <INPUT VALUE="尾  页" TYPE=button class= cssButton93  onclick="getLastPage();"> 					
  	</div>
  	<p>
      <a href="javascript:void(0)" class=button onclick="signGrpPol();">签发保单</a>
      <!-- <INPUT VALUE="签发保单" TYPE=button class= cssButton  onclick="signGrpPol();">  -->
      <!--INPUT VALUE="发催办通知书" TYPE=button class= cssButton  onclick="signGrpPol();"> 
      <input value="发首期交费通知书" class=cssButton type=button onclick="SendFirstNotice();"--> 
  	</p>
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
