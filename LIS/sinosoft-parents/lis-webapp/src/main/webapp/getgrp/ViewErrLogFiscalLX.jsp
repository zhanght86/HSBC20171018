<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ViewErrLogFiscalLX.jsp
//程序功能：分红处理
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
 <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="./ViewErrLogFiscalLX.js"></SCRIPT>
	<%@include file="./ViewErrLogFiscalLXInit.jsp"%>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>查询条件</title>
</head>

<body onload="initForm()">
  <form method=post name=fm id=fm target="fraSubmit">
  <div class="maxbox1">
    <table  class= common>
			<TR  class= common>
				<TD  class= title5>会计年度</TD> 
        <TD  class= input5><Input class="wid" class=common name=FiscalYear id=FiscalYear></TD>
        <TD  class= title5>团单号码</TD>
        <TD  class= input5><Input class="wid" class=common name=GrpPolNo id=GrpPolNo></TD></TR>
        <TR  class= common>
        <TD  class= title5>个单号码</TD>
        <TD  class= input5><Input class="wid" class=common name=PolNo id=PolNo></TD>
        <TD  class= title5>操作起期</TD>
      	<TD  class= input5>
        <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDateB'});" verify="有效开始日期|DATE" dateFormat="short" name=MakeDateB id="MakeDateB"><span class="icon"><a onClick="laydate({elem: '#MakeDateB'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
			</TR>
			<TR  class= common>
	  		
	  		<TD  class= title5>操作止期</TD>
      	<TD  class= input5>
        <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDateE'});" verify="有效开始日期|DATE" dateFormat="short" name=MakeDateE id="MakeDateE"><span class="icon"><a onClick="laydate({elem: '#MakeDateE'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
			</TR>
    </table></div>

	   <!--<INPUT VALUE="查    询" TYPE=button class=cssButton onclick="easyQueryClick();">--> 	
<a href="javascript:void(0);" class="button" onClick="easyQueryClick();">查    询</a><br><br>
	 
		<Div  id= "divBonusGrid" style= "display: ''">
	  	<table  class= common>
	   		<tr  class= common>
		  		<td text-align: left colSpan=1><span id="spanBonusGrid" ></span> </td>
				</tr>
	  	</table>
        <center>
	    <INPUT VALUE="首  页" class = cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
	    <INPUT VALUE="上一页" class = cssButton91 TYPE=button onclick="turnPage.previousPage();"> 					
	    <INPUT VALUE="下一页" class = cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
	    <INPUT VALUE="尾  页" class = cssButton93 TYPE=button onclick="turnPage.lastPage();">	</center>			
		</div>  
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

</html>
