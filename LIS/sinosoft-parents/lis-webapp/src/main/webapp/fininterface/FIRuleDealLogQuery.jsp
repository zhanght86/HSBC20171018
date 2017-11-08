<html>
<%
//程序名称 :FIRuleDealLogQuery.jsp
//程序功能 :校验日志信息查询
//创建人 :范昕
//创建日期 :2008-08-18
//
%>
	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
	<!--用户校验类-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.pubfun.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	<%
  GlobalInput tGI1 = new GlobalInput();
  tGI1=(GlobalInput)session.getValue("GI");//添加页面控件的初始化。
  
 	%>
<script>
  var comcode = "<%=tGI1.ComCode%>";
  var VersionNo = <%=request.getParameter("VersionNo")%>;
</script>
<head >
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>  
<SCRIPT src = "FIRuleDealLogQuery.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="FIRuleDealLogQueryinit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
  <form action="" method=post name=fm id=fm target="fraSubmit">
  <Div id= "divLLReport1" style= "display: ''">
  
  	<table>
    	<tr>
    		 <td class= titleImg>
        		查询条件
       		 </td>   		 
    	</tr>
    </table>
    <div class="maxbox1">
  	<table class= common border=0 width=100%>
  <table class=common>
		<tr class= common>
				 <TD class= title>
					  版本编号
				 </TD>
				 <TD class=input>
				 	<input class="wid" class=readonly name=VersionNo id=VersionNo readonly=true >
				</TD>
				<TD  class= title>
            起始时间
          </TD>
          <TD  class= input>
            <!--<Input class= "coolDatePicker" verify="起始时间|notnull&date" dateFormat="short" name=StartDay >-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDay'});" verify="起始时间|notnull&date" dateFormat="short" name=StartDay id="StartDay"><span class="icon"><a onClick="laydate({elem: '#StartDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            
          </TD>
          <TD  class= title>
            结束时间
          </TD>
          <TD  class= input>
            <!--<Input class= "coolDatePicker" verify="结束时间|notnull&date"  dateFormat="short" name=EndDay >-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDay'});" verify="结束时间|notnull&date" dateFormat="short" name=EndDay id="EndDay"><span class="icon"><a onClick="laydate({elem: '#EndDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
		</tr>
	</table> 
	
      <!--<input class="cssButton" type=button value="查  询" onclick="queryFIRuleDealLog()">-->
      <a href="javascript:void(0);" class="button" onClick="queryFIRuleDealLog();">查    询</a> 
   		</div>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick="showPage(this,divFIRuleDealLogGrid);">
    		</td>
    		<td class= titleImg>
    			 校验日志信息查询结果
    		</td>
    	</tr>
    </table>
<Div  id= "divFIRuleDealLogGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanFIRuleDealLogGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <!--<div align="left"><input class="cssButton" type=button value="返  回" onclick="ReturnData()"></div>-->
        <center>
		<INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
		<INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage.previousPage();"> 					
		<INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
		<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage.lastPage();"> </center>					
  <br>
</table>
</Div>
<a href="javascript:void(0);" class="button" onClick="ReturnData();">返    回</a>
   <input type=hidden id="OperateType" name="OperateType">
</table>
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
