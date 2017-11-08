<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
%>
<html>
<%
  GlobalInput tGI = new GlobalInput();
  tGI = (GlobalInput)session.getValue("GI");
  loggerDebug("GrpPersonPrint_IDGF"," 管理机构：" + tGI.ManageCom);
  loggerDebug("GrpPersonPrint_IDGF"," 登陆机构：" + tGI.ComCode);
%>
<script>
  var manageCom = "<%=tGI.ManageCom%>"; //记录管理机构
  var comcode = "<%=tGI.ComCode%>";     //记录登陆机构
</script>

<head>

  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="GrpPersonPrint_IDGF.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GrpPersonPrintInit_IDGF.jsp"%>
  
  <title>打印团体保单个人凭证 </title>
  
</head>

<body  onload="initForm();" >
  <form action="./GrpPersonPrintSave_IDGF.jsp" method=post name=fm id="fm" target="fraSubmit">
  
    <!-- 团体保单信息部分 -->
<TABLE>
    <TR>
        <TD class= common>
            <IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPolContion);">
        </TD>
        <TD class = titleImg>
            请输入团单查询条件：
        </TD>
    </TR>
</TABLE>	
<div class="maxbox1">
<Div id= "divLCPolContion" style= "display: ''">		    
    <table  class= common align=center>
        <TR  class= common>
          <TD  class= title5>
            团体保单号
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=GrpContNo id="GrpContNo">
          </TD>
          <TD  class= title5>
            印刷号码
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=PrtNo id="PrtNo">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            个人保单号
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=ContNo id="ContNo">
          </TD>
        </TR>
</table> 
</div>
</div>
<a href="javascript:void(0)" class=button onclick="queryGrpPol();">查询团单</a>
<a href="javascript:void(0)" class=button onclick="queryGrpPerson();">查询团单下个人明细</a>
<!--     <INPUT VALUE="查询团单" class= cssButton TYPE=button onclick="queryGrpPol();">
    <INPUT VALUE="查询团单下个人明细" class= cssButton TYPE=button onclick="queryGrpPerson();"> -->
    
    
   
    <Div  id= "divGrpPol" style= "display: none">
      <table  class= common>
        <tr>
          <td class= titleImg>个人保单信息</td>
    	</tr>
        <tr  class= common>
      	  <td text-align: left colSpan=1><span id="spanGrpPolGrid" ></span></td>
  	</tr>
      </table>
    
      <INPUT VALUE="打印团单全部个人凭证" class= cssButton name='GrpPolButton' id="GrpPolButton" TYPE=button onclick="printGrpPol();">
    </Div>
    
    <Div  id= "divGrpPerson" style= "display: none">
      <table  class= common>
        <tr>
          <td class= titleImg>个人保单信息</td>
    	</tr>
        <tr  class= common>
      	  <td text-align: left colSpan=1><span id="spanPolGrid" ></span></td>
  	</tr>
      </table>
      
      <INPUT VALUE="首  页" class= cssButton90 TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="上一页" class= cssButton91 TYPE=button onclick="getPreviousPage();">
      <INPUT VALUE="下一页" class= cssButton92 TYPE=button onclick="getNextPage();">
      <INPUT VALUE="尾  页" class= cssButton93 TYPE=button onclick="getLastPage();">
      <table  class= common>
        <tr>
          <td><INPUT VALUE="打印选中团单个人凭证" class= cssButton name='GrpPersonButton' id="GrpPersonButton" TYPE=button onclick="printGrpPerson();"> </td>
    	</tr>
      </table>
      
    </Div>
    
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
	var codeSql = "1  and code like #"+<%=tGI.ComCode%>+"%#";
</script>
