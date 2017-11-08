<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：EsDocMainQuery.jsp
//程序功能：
//创建日期：2004-06-02
//创建人  ：LiuQiang
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
  <SCRIPT src="./EsDocMainQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./EsDocMainQueryInit.jsp"%>
  <title>扫描件单证信息主表 </title>
</head>
<body  onload="initForm();" >
  <form action="./EsDocMainQuerySubmit.jsp" method=post name=fm id=fm target="fraSubmit">
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
    <TD  class= title5>印刷号码</TD>
    <TD  class= input5><Input class="common wid" name=DOC_CODE id=DOC_CODE ></TD>
	<TD  class= title5></TD>
    <TD  class= input5></td>
  </TR>
</table>
 </Div>
          <INPUT VALUE="查  询" TYPE=button class= cssButton onclick="easyQueryClick();"> 
          <INPUT VALUE="返  回" TYPE=button class= cssButton onclick="returnParent();"> 					
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);">
    		</td>
    		<td class= titleImg>
    			 扫描件单证信息主表结果
    		</td>
    	</tr>
    </table>
  	<Div  id= "divCodeGrid" style= "display: ;text-align:center">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanCodeGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="首  页" TYPE=button class= cssButton90 onclick="getFirstPage();"> 
      <INPUT VALUE="上一页" TYPE=button class= cssButton91 onclick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" TYPE=button class= cssButton92 onclick="getNextPage();"> 
      <INPUT VALUE="尾  页" TYPE=button class= cssButton93 onclick="getLastPage();"> 					
  	</div>
  </form>
  <br /><br /><br /><br />
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
