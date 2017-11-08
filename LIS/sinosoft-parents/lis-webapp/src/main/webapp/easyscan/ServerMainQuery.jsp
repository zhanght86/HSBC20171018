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
  <SCRIPT src="./ServerMainQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./ServerMainQueryInit.jsp"%>
  <title>公司使用服务器的信息</title>
</head>
<body  onload="initForm();" >
  <form action="" method=post name=fm id=fm target="fraSubmit">
  <table>
      <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid1);">
      </td>
      <td class= titleImg>
        请您输入查询条件： 
      </td>
    	</tr>   
    </table>
  <Div  id= "divCodeGrid1" class=maxbox1 style= "display: ''">    
  <table  class= common>
  <TR  class= common>
    <TD  class= title5>
      公司名称
    </TD>
    <TD  class= input5>
      <Input class="wid common" id=ManageCom name=ManageCom>
    </TD>
	<TD  class= title5></TD>
    <TD  class= input5></TD>
  </TR>
</table>
 </Div>
          <INPUT VALUE="查询" class =cssButton TYPE=button onclick="easyQueryClick();"> 
          <INPUT VALUE="返回" class =cssButton TYPE=button onclick="returnParent();"> 					
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);">
    		</td>
    		<td class= titleImg>
    			 公司使用服务器信息主表结果
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
	<INPUT VALUE="首页" class = cssButton90 TYPE=button onclick="getFirstPage();"> 
	<INPUT VALUE="上一页" class = cssButton91 TYPE=button onclick="getPreviousPage();"> 					
	<INPUT VALUE="下一页" class = cssButton92 TYPE=button onclick="getNextPage();"> 
	<INPUT VALUE="尾页" class = cssButton93 TYPE=button onclick="getLastPage();"> 					
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
