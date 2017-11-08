<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：ApplyRecall.jsp
//程序功能：
//创建日期：
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="LLUnHangUp.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="LLUnHangUpInit.jsp"%>
  <title>撤单申请</title>  
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit" action= "./ApplyRecallChk.jsp">
    <table class= common border=0 width=100%>
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);"></td>
			<td class= titleImg>请输入查询条件：</td>
		</tr>
	</table>
	<Div  id= "divSearch" style= "display: ''" class="maxbox">
    <table  class= common>
      	<TR  class= common>
          <TD  class= title5> 报案号  </TD>
          <TD  class= input5> <Input class="wid" class= common name=RptNo id=RptNo > </TD>
          <TD  class= title5> 出险人客户号 </TD>
          <TD  class= input5><Input class="wid" class= common name=CustomerNo id=CustomerNo > </TD></TR>
          <TR  class= common>
          <TD  class= title5> 出险人姓名</TD>
          <TD  class= input5><Input class="wid" class=common name=Name id=Name ></TD>
          <TD  class= title5>  出险人性别  </TD>
          <TD  class= input5> <Input class="wid" class= common name=Sex id=Sex > </TD>
        <TR  class= common>
          
          <TD  class= title5>  报案日期</TD>
          <TD  class= input5><!-- <Input class= coolDatePicker name=RptDate > -->
          <Input class="coolDatePicker" onClick="laydate({elem: '#RptDate'});" verify="有效开始日期|DATE" dateFormat="short" name=RptDate id="RptDate"><span class="icon"><a onClick="laydate({elem: '#RptDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD class= titl5e> 出险日期 </TD>
          <TD class= input5> <!-- <input class="coolDatePicker" dateFormat="short" name="AccdentDate" >-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#AccdentDate'});" verify="有效开始日期|DATE" dateFormat="short" name=AccdentDate id="AccdentDate"><span class="icon"><a onClick="laydate({elem: '#AccdentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>              
        </TR>
    </table>
    </DIV>
          <!--<INPUT class=cssButton VALUE="查  询" TYPE=button onclick="easyQueryClick();"> -->
          <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">查    询</a>
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid);">
    		</td>
    		<td class= titleImg>
    			 可选报案
    		</td>
    	</tr>
    </table>
  	<Div  id= "divPolGrid" style= "display: ''">
      	<table  class= common >
       		<tr  class= common align = left>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <center>
      <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">	</center>	
  	</div>
    
  <br>
  
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divContent);">
    		</td>
    		<td class= titleImg>
    			 修改原因
    		</td>
    	</tr>
    </table>
  	<Div  id= "divContent" style= "display: ''" class="maxbox1">      
  <table class= common>
    <TR  class= title5>
      <TD colspan="4" style="padding-left:16px"><textarea name="Content" cols="166" rows="4" class="common"></textarea></TD>
    </TR>
  </table>
  </div>
  	<input type=hidden id="RptNo1" name="RptNo1">
  	<input type = hidden id="CustomerNo1" name = "CustomerNo1">

</form>
  
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
<!--<input type= "button" class=cssButton name= "sure" value="挂起修改" onClick="submitForm()">-->
<a href="javascript:void(0);" class="button" name= "sure" onClick="submitForm();">挂起修改</a><br><br><br><br>
</body>
</html>
