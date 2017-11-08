
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
/****************************************************************************
差异化核保->品质差异化维护

*****************************************************************************/

%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>


<%
   GlobalInput tG = new GlobalInput();
   tG=(GlobalInput)session.getValue("GI"); //添加页面控件的初始化。
   loggerDebug("RegionalDisparityInput","管理机构-----"+tG.ComCode);
%>   

<script>
  var comCode = <%=tG.ComCode%>
</script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="RegionalDisparityInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <%@include file="RegionalDisparityInit.jsp"%>
  
  <title>业务员品质差异化维护 </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit" action="./RegionalDisparitySave.jsp">
    <!-- 保单信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,lever);"></td>
			<td class= titleImg align= center>请输入个人保单查询条件：</td>
		</tr>
	</table>
    <Div  id= "lever" style= "display: ''" class="maxbox1">
    <table  class= common>
      	<TR  class= common>
      	  <TD  class= title5>管理机构</TD>
          <TD  class= input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ManageCom id=ManageCom ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input name=ManageComName class=codename readonly=true></TD>
          <TD  class= title5> 机构类别</TD>
          <TD  class= input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=UWClass id=UWClass ondblclick="return showCodeList('AreaUWClass',[this,UWClassName],[0,1]);" onclick="return showCodeList('AreaUWClass',[this,UWClassName],[0,1]);" onkeyup="return showCodeListKey('AreaUWClass',[this,UWClassName],[0,1]);"><Input class=codename name=UWClassName readonly></TD>
          </TR>
          <TR  class= common>
          <TD  class= title5> 差异化级别</TD>
          <TD  class= input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=UWLevel id=UWLevel ondblclick="return showCodeList('UWLevel',[this,UWLevelName],[0,1]);" onclick="return showCodeList('UWLevel',[this,UWLevelName],[0,1]);" onkeyup="return showCodeListKey('UWLevel',[this,UWLevelName],[0,1]);"><Input class=codename name=UWLevelName readonly></TD>
          <TD  class= title5></TD>
          <TD  class= input5></TD>
        </TR>
    </table>   
       </Div> <br>
     <INPUT VALUE=" 查  询 " class = cssButton TYPE=button onclick="easyQueryClick();">
     <INPUT VALUE=" 修  改 " class = cssButton TYPE=button onclick="UpdateAgent();">
        <!-- /TR>      	          
        <TR  class= common -->        
    
  	<Div  id= "divLCPol1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table></div>
     <Div  id= "divPage" align=center >
      <INPUT VALUE="首  页" class = cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" class = cssButton91 TYPE=button onclick="turnPage.previousPage();">
      <INPUT VALUE="下一页" class = cssButton92 TYPE=button onclick="turnPage.nextPage();">
      <INPUT VALUE="尾  页" class = cssButton93 TYPE=button onclick="turnPage.lastPage();">
     </Div>
  	
  	<Input class= common  type=hidden name=OldPrtNoHide value="">
  	<Input class= common  type=hidden name=AgentGroup value="">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
