<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：CodeQueryInput.jsp
//程序功能：
//创建日期：2002-08-16 17:44:48
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="./CodeQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./CodeQueryInit.jsp"%>
  <title>公用代码表 </title>
</head>
<body  onload="initForm();" >
  <form action="./CodeQuerySubmit.jsp" method=post name=fm id=fm target="fraSubmit">
  <table>
      <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid1);">
      </td>
      <td class= titleImg>
        请您输入查询条件： 
      </td>
    	</tr>   
    </table>
  <Div  id= "divCodeGrid1" style= "display: ''">    
  <div class="maxbox1">
  <table  class= common>
  <TR  class= common>
    <TD  class= title5>
      编码类型
    </TD>
    <TD  class= input5>
      <Input class="wid" class= common name=CodeType >
    </TD>
    <TD  class= title5>
      编码
    </TD>
    <TD  class= input5>
      <Input class="wid" class= common name=Code >
    </TD>
  </TR>
</table>
 </Div></div>
          <!--<INPUT VALUE="查询" TYPE=button onclick="easyQueryClick();" class="cssButton"> -->
           	<a href="javascript:void(0);" class="button" onClick="easyQueryClick();">查    询</a>				
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);">
    		</td>
    		<td class= titleImg>
    			 公用代码表结果
    		</td>
    	</tr>
    </table>
  	<Div  id= "divCodeGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanCodeGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <!--<div align="left">
        <INPUT VALUE="返回" TYPE=button onclick="returnParent();" class="cssButton"></div>-->
        <div align="center">
        <INPUT VALUE="首页" TYPE=button onclick="getFirstPage();" class="cssButton90"> 
      <INPUT VALUE="上一页" TYPE=button onclick="getPreviousPage();" class="cssButton91"> 					
      <INPUT VALUE="下一页" TYPE=button onclick="getNextPage();" class="cssButton92"> 
      <INPUT VALUE="尾页" TYPE=button onclick="getLastPage();" class="cssButton93"> 	</div>				
  	</div>
    <a href="javascript:void(0);" class="button" onClick="returnParent();">返    回</a>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
