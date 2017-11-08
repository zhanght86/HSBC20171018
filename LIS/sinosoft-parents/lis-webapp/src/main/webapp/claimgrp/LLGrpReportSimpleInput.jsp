<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
%>   
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="Claim.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="LLGrpReportSimpleInit.jsp"%>
  <title>报案信息导出 </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm target="fraSubmit">
    <!-- 保单信息部分 -->
	<table class= common border=0 width=100%>
    	<tr>
			<td class= titleImg align= center>请输入团单赔案查询条件：</td>
		</tr>
	</table>
    <table  class= common align=center>
   
      <TR  class= common>
          <TD  class= title >报案/赔案号:</TD>
          <TD  class= input >  <Input class= common name=RgtNo > </TD>
          <TD  class= title > 管理机构 </TD>
          <TD  class= input >  <Input class="code" name=ManageCom ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">  </TD>
       </TR>
        
        <TR>
        <TD  class= input> <Input class= common name="OPManageCom" type="hidden"  value="<%=tG.ManageCom%>" ></TD>
      </TR>
    </table>
      <INPUT VALUE="查   询" class = cssButton TYPE=button onclick="ReportQueryClick();"> 
 
  	<Div  id= "divLPGrpEdorMain1" style= "display: ''">
      <table  class= common>
       	<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	
    <table>
     <tr>
      <td><INPUT VALUE="首  页" class = cssButton  TYPE=button onclick="turnPage.firstPage();"></td> 
      <td><INPUT VALUE="上一页" class = cssButton  TYPE=button onclick="turnPage.previousPage();"></td> 					
      <td><INPUT VALUE="下一页" class = cssButton  TYPE=button onclick="turnPage.nextPage();"></td> 
      <td><INPUT VALUE="尾  页" class = cssButton  TYPE=button onclick="turnPage.lastPage();"></td>      
		 </tr>
		</table> 
		</div>	
  	
  	<table class= common align=center>
     <tr class= common>  
    	 <td  class= input align="center" ><INPUT VALUE="报案信息导出" class= cssButton TYPE=button onclick="PrintReportClass();"></td>
     </tr>        
    </table>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>

